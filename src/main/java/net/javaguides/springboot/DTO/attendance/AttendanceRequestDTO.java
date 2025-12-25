package net.javaguides.springboot.DTO.attendance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRequestDTO {
    private Long employeeId;
    private String employeeName;
}

