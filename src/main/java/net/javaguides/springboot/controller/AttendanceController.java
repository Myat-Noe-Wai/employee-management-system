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

    @PutMapping("/clock-out/{employeeId}")
    public AttendanceResponseDTO clockOut(@PathVariable Long employeeId) {
        log.info("clock-out in controller");
        return attendanceService.clockOut(employeeId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AttendanceResponseDTO> getAttendanceByEmployee(@PathVariable Long employeeId) {
        return attendanceService.getAttendanceByEmployee(employeeId);
    }
}
