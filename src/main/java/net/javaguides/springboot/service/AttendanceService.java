package net.javaguides.springboot.service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.shared.exception.GeneralException;
import net.javaguides.springboot.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.attendance.AttendanceRequestDTO;
import net.javaguides.springboot.DTO.attendance.AttendanceResponseDTO;
import net.javaguides.springboot.model.Attendance;
import net.javaguides.springboot.repository.AttendanceRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public List<AttendanceResponseDTO> getAllAttendance(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        List<Attendance> list;

        // 1️⃣ Default → today
        if (employeeId == null && fromDate == null && toDate == null) {
            list = attendanceRepository.findByDate(LocalDate.now());
        }
        // 2️⃣ Employee + date range
        else if (employeeId != null && fromDate != null && toDate != null) {
            list = attendanceRepository
                    .findByEmployeeIdAndDateBetween(employeeId, fromDate, toDate);
        }
        // 3️⃣ Date range only
        else if (fromDate != null && toDate != null) {
            list = attendanceRepository.findByDateBetween(fromDate, toDate);
        }
        // 4️⃣ Employee only
        else if (employeeId != null) {
            list = attendanceRepository.findByEmployeeId(employeeId);
        }
        else {
            list = attendanceRepository.findAll();
        }

        return list.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AttendanceResponseDTO clockIn(AttendanceRequestDTO dto) {

        log.info("Clock-in request received for employeeId={}, employeeName={}",
                dto.getEmployeeId(), dto.getEmployeeName());

        LocalDate today = LocalDate.now();
        log.debug("Processing clock-in for date={}", today);

        Attendance attendance = attendanceRepository
                .findByDateAndEmployeeId(today, dto.getEmployeeId())
                .orElseGet(() -> {
                    log.info("No attendance record found for employeeId={} on date={}, creating new record",
                            dto.getEmployeeId(), today);

                    return Attendance.builder()
                            .employeeId(dto.getEmployeeId())
                            .employeeName(dto.getEmployeeName())
                            .date(today)
                            .build();
                });

        if (attendance.getClockIn() != null) {
            log.warn("Employee already clocked in. employeeId={}, clockInTime={}",
                    dto.getEmployeeId(), attendance.getClockIn());

            return AttendanceResponseDTO.alreadyLoggedIn(attendance);
        }

        attendance.setClockIn(OffsetDateTime.now(ZoneId.of("Asia/Yangon")));
        log.info("Clock-in successful. employeeId={}, clockInTime={}",
                dto.getEmployeeId(), OffsetDateTime.now(ZoneId.of("Asia/Yangon")));

        Attendance saved = attendanceRepository.save(attendance);
        log.debug("Attendance record saved successfully. attendanceId={}", saved.getId());

        return mapToResponse(saved);
    }

    public AttendanceResponseDTO clockOut(Long employeeId) {
        log.info("clock-out in Service");
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByDateAndEmployeeId(today, employeeId)
                .orElseThrow(() -> new GeneralException("Attendance record not found"));

        // Check if already clocked out
        if (attendance.getClockOut() != null) {
            return AttendanceResponseDTO.alreadyLoggedOut(attendance);
        }

        attendance.setClockOut(OffsetDateTime.now(ZoneId.of("Asia/Yangon")));
        Attendance saved = attendanceRepository.save(attendance);

        return mapToResponse(saved);
    }

    public List<AttendanceResponseDTO> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AttendanceResponseDTO getTodayAttendance(Long employeeId) {
        LocalDate today = LocalDate.now();
        log.info("Fetching today's attendance for employeeId={} on date={}", employeeId, today);

        Attendance todayAttendance = attendanceRepository.findByDateAndEmployeeId(today, employeeId)
                .orElseThrow(() -> {
                    log.warn("No attendance record found for employeeId={} on date={}", employeeId, today);
                    return new GeneralException("Today attendance not found");
                });

        log.info("Attendance found for employeeId={} | clockIn={} | clockOut={}",
                employeeId, todayAttendance.getClockIn(), todayAttendance.getClockOut());

        return mapToResponse(todayAttendance);
    }

    public AttendanceResponseDTO getLastAttendance(Long employeeId) {
        return attendanceRepository
                .findTopByEmployeeIdOrderByDateDesc(employeeId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    public List<AttendanceResponseDTO> getMyMonthlyAttendance(Long employeeId, String month) {
        YearMonth yearMonth = YearMonth.parse(month); // 2026-01
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return attendanceRepository
                .findByEmployeeIdAndDateBetween(employeeId, start, end)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // -------- Mapping --------
    private AttendanceResponseDTO mapToResponse(Attendance attendance) {
        return new AttendanceResponseDTO(
                attendance.getId(),
                attendance.getEmployeeId(),
                attendance.getEmployeeName(),
                attendance.getDate(),
                attendance.getClockIn(),
                attendance.getClockOut(),
                null
        );
    }
}
