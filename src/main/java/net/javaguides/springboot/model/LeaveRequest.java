package net.javaguides.springboot.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.enums.LeaveType;

@Entity
@Table(name = "leave_request")
@Getter
@Setter
public class LeaveRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
}
