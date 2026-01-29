package net.javaguides.springboot.service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
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

    private final EmployeeRepository employeeRepository;

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
                dto.getUserId(), dto.getEmployeeName());
        log.info("Clock-in request received for userId={}", dto.getUserId());

        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository
                .findByUser_UserId(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Employee not found for userId=" + dto.getUserId())
                );

        LocalDate today = LocalDate.now();
        log.debug("Processing clock-in for date={}", today);

        Attendance attendance = attendanceRepository
                .findByDateAndEmployeeId(today, employee.getId())
                .orElseGet(() -> {
                    log.info("No attendance record found for employeeId={} on date={}, creating new record",
                            employee.getId(), today);

                    return Attendance.builder()
                            .employeeId(employee.getId())
                            .employeeName(dto.getEmployeeName())
                            .date(today)
                            .build();
                });

        if (attendance.getClockIn() != null) {
            log.warn("Employee already clocked in. employeeId={}, clockInTime={}",
                    dto.getUserId(), attendance.getClockIn());

            return AttendanceResponseDTO.alreadyLoggedIn(attendance);
        }

        attendance.setClockIn(OffsetDateTime.now(ZoneId.of("Asia/Yangon")));
        log.info("Clock-in successful. employeeId={}, clockInTime={}",
                dto.getUserId(), OffsetDateTime.now(ZoneId.of("Asia/Yangon")));

        Attendance saved = attendanceRepository.save(attendance);
        log.debug("Attendance record saved successfully. attendanceId={}", saved.getId());

        return mapToResponse(saved);
    }

    public AttendanceResponseDTO clockOut(Long userId) {
        log.info("clock-out in Service");
        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId=" + userId));
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByDateAndEmployeeId(today, employee.getId())
                .orElseThrow(() -> new GeneralException("Attendance record not found"));

        // Check if already clocked out
        if (attendance.getClockOut() != null) {
            return AttendanceResponseDTO.alreadyLoggedOut(attendance);
        }

        attendance.setClockOut(OffsetDateTime.now(ZoneId.of("Asia/Yangon")));
        Attendance saved = attendanceRepository.save(attendance);

        return mapToResponse(saved);
    }

    public List<AttendanceResponseDTO> getAttendanceByEmployee(Long userId) {
        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId=" + userId));
        LocalDate today = LocalDate.now();

        return attendanceRepository.findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AttendanceResponseDTO getTodayAttendance(Long userId) {
        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId=" + userId));

        LocalDate today = LocalDate.now();
        log.info("Fetching today's attendance for employeeId={} on date={}", employee.getId(), today);

        Attendance todayAttendance = attendanceRepository.findByDateAndEmployeeId(today, employee.getId())
                .orElseThrow(() -> {
                    log.warn("No attendance record found for employeeId={} on date={}", employee.getId(), today);
                    return new GeneralException("Today attendance not found");
                });

        log.info("Attendance found for employeeId={} | clockIn={} | clockOut={}",
                employee.getId(), todayAttendance.getClockIn(), todayAttendance.getClockOut());

        return mapToResponse(todayAttendance);
    }

    public AttendanceResponseDTO getLastAttendance(Long userId) {
        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId=" + userId));

        return attendanceRepository
                .findTopByEmployeeIdOrderByDateDesc(employee.getId())
                .map(this::mapToResponse)
                .orElse(null);
    }

    public List<AttendanceResponseDTO> getMyMonthlyAttendance(Long userId, String month) {
        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId = " + userId));

        YearMonth yearMonth = YearMonth.parse(month); // 2026-01
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return attendanceRepository
                .findByEmployeeIdAndDateBetween(employee.getId(), start, end)
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
