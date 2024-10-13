package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.model.Attendance;
import net.javaguides.springboot.service.AttendanceService;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
	@Autowired
	private AttendanceService attendanceService;
	
	@GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }
	
	@PostMapping("/clock-in")
    public Attendance clockIn(@RequestParam Long employeeId, @RequestParam String employeeName) {
        return attendanceService.clockIn(employeeId, employeeName);
    }

    @PostMapping("/clock-out")
    public Attendance clockOut(@RequestParam Long employeeId) {
        return attendanceService.clockOut(employeeId);
    }
}
