package net.javaguides.springboot.DTO.leaverequest;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.javaguides.springboot.enums.LeaveType;

@Getter
@AllArgsConstructor
public class LeaveRequestResponseDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
}

