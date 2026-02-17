package net.javaguides.springboot.DTO.payslip;

import lombok.Data;

@Data
public class PayslipRequestDTO {

    private Long employeeId;
    private Integer month;
    private Integer year;

    private Double basicSalary;
    private Double allowances;
    private Double overtimePay;
    private Double bonus;

    private Double deductions;
    private Double unpaidLeaveDeduction;
    private Double tax;
}

