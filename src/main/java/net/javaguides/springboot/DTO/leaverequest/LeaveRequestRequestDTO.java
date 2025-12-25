package net.javaguides.springboot.DTO.leaverequest;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestRequestDTO {

    private Long employeeId;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String reason;
}

