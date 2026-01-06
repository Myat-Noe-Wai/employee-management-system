package net.javaguides.springboot.DTO.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.javaguides.springboot.model.Attendance;

@Getter
@AllArgsConstructor
public class AttendanceResponseDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate date;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private String message;

    // Static factory for "already logged in"
    public static AttendanceResponseDTO alreadyLoggedIn(Attendance attendance) {
        return new AttendanceResponseDTO(
                attendance.getId(),
                attendance.getEmployeeId(),
                attendance.getEmployeeName(),
                attendance.getDate(),
                attendance.getClockIn(),
                attendance.getClockOut(),
                "Already logged in"
        );
    }

    // Static helper for "already logged out"
    public static AttendanceResponseDTO alreadyLoggedOut(Attendance attendance) {
        return new AttendanceResponseDTO(
                attendance.getId(),
                attendance.getEmployeeId(),
                attendance.getEmployeeName(),
                attendance.getDate(),
                attendance.getClockIn(),
                attendance.getClockOut(),
                "Already logged out"
        );
    }
}

