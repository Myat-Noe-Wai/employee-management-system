package net.javaguides.springboot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<AttendanceResponseDTO> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AttendanceResponseDTO clockIn(AttendanceRequestDTO dto) {
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByDateAndEmployeeId(today, dto.getEmployeeId())
                .orElse(
                        Attendance.builder()
                                .employeeId(dto.getEmployeeId())
                                .employeeName(dto.getEmployeeName())
                                .date(today)
                                .build()
                );

        attendance.setClockIn(LocalDateTime.now());

        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    public AttendanceResponseDTO clockOut(Long employeeId) {
        log.info("clock-out in Service");
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByDateAndEmployeeId(today, employeeId)
                .orElseThrow(() -> new GeneralException("Attendance record not found"));

        attendance.setClockOut(LocalDateTime.now());

        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    // -------- Mapping --------

    private AttendanceResponseDTO mapToResponse(Attendance attendance) {
        return new AttendanceResponseDTO(
                attendance.getId(),
                attendance.getEmployeeId(),
                attendance.getEmployeeName(),
                attendance.getDate(),
                attendance.getClockIn(),
                attendance.getClockOut()
        );
    }
}
