package net.javaguides.springboot.DTO.leaverequest;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveRequestResponseDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;
}

