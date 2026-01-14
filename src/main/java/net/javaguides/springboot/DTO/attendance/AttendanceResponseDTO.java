package net.javaguides.springboot.DTO.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss XXX"
    )
    private OffsetDateTime clockIn;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss XXX"
    )
    private OffsetDateTime clockOut;
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

