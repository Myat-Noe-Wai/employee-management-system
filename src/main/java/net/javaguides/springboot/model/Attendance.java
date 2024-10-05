package net.javaguides.springboot.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private Long employeeId;
    private String employeeName;
    private LocalDate date;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalDateTime getClockIn() {
		return clockIn;
	}
	public void setClockIn(LocalDateTime clockIn) {
		this.clockIn = clockIn;
	}
	public LocalDateTime getClockOut() {
		return clockOut;
	}
	public void setClockOut(LocalDateTime clockOut) {
		this.clockOut = clockOut;
	}
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Attendance(Long employeeId, String employeeName, LocalDate date, LocalDateTime clockIn,
			LocalDateTime clockOut) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.date = date;
		this.clockIn = clockIn;
		this.clockOut = clockOut;
	}	
}
