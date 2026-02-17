package net.javaguides.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payslips")
@Getter
@Setter
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;
    private Integer year;

    private Double basicSalary;
    private Double allowances;
    private Double overtimePay;
    private Double bonus;

    private Double deductions;
    private Double unpaidLeaveDeduction;
    private Double tax;

    private Double netSalary;

    private LocalDateTime generatedDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

