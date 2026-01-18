package net.javaguides.springboot.DTO.leaverequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveBalanceResponse {
    private int totalDays;
    private int usedDays;
    private int remainingDays;
}

