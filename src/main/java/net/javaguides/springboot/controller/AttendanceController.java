package net.javaguides.springboot.controller;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.DTO.attendance.AttendanceRequestDTO;
import net.javaguides.springboot.DTO.attendance.AttendanceResponseDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import net.javaguides.springboot.service.AttendanceService;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*") //For Cloud
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceResponseDTO> getAllAttendance(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return attendanceService.getAllAttendance(employeeId, fromDate, toDate);
    }

    @PostMapping("/clock-in")
    public AttendanceResponseDTO clockIn(@RequestBody AttendanceRequestDTO dto) {
        return attendanceService.clockIn(dto);
    }

    @PutMapping("/clock-out/{userId}")
    public AttendanceResponseDTO clockOut(@PathVariable Long userId) {
        log.info("clock-out in controller");
        return attendanceService.clockOut(userId);
    }

    @GetMapping("/employee/{userId}")
    public List<AttendanceResponseDTO> getAttendanceByEmployee(@PathVariable Long userId) {
        return attendanceService.getAttendanceByEmployee(userId);
    }

    @GetMapping("/today")
    public AttendanceResponseDTO getTodayAttendance(@RequestParam Long userId) {
        return attendanceService.getTodayAttendance(userId);
    }

    @GetMapping("/last")
    public AttendanceResponseDTO getLastAttendance(@RequestParam Long userId) {
        return attendanceService.getLastAttendance(userId);
    }

    @GetMapping("/my/month")
    public List<AttendanceResponseDTO> getMyMonthlyAttendance(@RequestParam String month, @RequestParam Long userId) {
        return attendanceService.getMyMonthlyAttendance(userId, month);
    }
}
