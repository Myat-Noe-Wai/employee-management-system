package net.javaguides.springboot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Attendance;
import net.javaguides.springboot.repository.AttendanceRepository;
@Service
public class AttendanceService {
	@Autowired
    private AttendanceRepository attendanceRepository;
	
	public List<Attendance> getAllAttendance(){
		return attendanceRepository.findAll();
	}
	
	public Attendance clockIn(Long employeeId, String employeeName) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByDateAndEmployeeId(today, employeeId).orElse(new Attendance());
        attendance.setDate(today);
        attendance.setEmployeeId(employeeId);
        attendance.setEmployeeName(employeeName);
        attendance.setClockIn(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public Attendance clockOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByDateAndEmployeeId(today, employeeId).orElseThrow(() -> new RuntimeException("Attendance record not found"));
        attendance.setClockOut(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }
}
