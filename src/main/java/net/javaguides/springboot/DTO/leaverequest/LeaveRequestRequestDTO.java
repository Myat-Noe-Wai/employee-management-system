package net.javaguides.springboot.DTO.leaverequest;

import java.time.LocalDate;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.enums.LeaveType;

@Getter
@Setter
public class LeaveRequestRequestDTO {

    private Long employeeId;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}

