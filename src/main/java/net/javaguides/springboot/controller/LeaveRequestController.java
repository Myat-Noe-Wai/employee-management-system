package net.javaguides.springboot.controller;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.leaverequest.LeaveBalanceResponse;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestRequestDTO;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestResponseDTO;
import net.javaguides.springboot.model.LeaveRequest;
import net.javaguides.springboot.repository.LeaveRequestRepository;
import net.javaguides.springboot.repository.LeaveRequestSpecification;
import net.javaguides.springboot.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    private final LeaveRequestRepository repository;

    @GetMapping
    public Page<LeaveRequest> getLeaveRequests(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());

        Specification<LeaveRequest> spec = LeaveRequestSpecification.filter(firstName, lastName, status, fromDate, toDate);

        return repository.findAll(spec, pageable);
    }

    @GetMapping("/{id}")
    public LeaveRequestResponseDTO getLeaveRequestById(@PathVariable Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    @PostMapping("/apply")
    public LeaveRequestResponseDTO applyForLeave(@RequestBody LeaveRequestRequestDTO dto) {
        return leaveRequestService.applyForLeave(dto);
    }

    @PutMapping("/{id}/approve")
    public LeaveRequestResponseDTO approveLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.approveLeaveRequest(id);
    }

    @PutMapping("/{id}/reject")
    public LeaveRequestResponseDTO rejectLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.rejectLeaveRequest(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
    }

    @GetMapping("/my")
    public List<LeaveRequestResponseDTO> getMyLeaveRequests() {
        return leaveRequestService.getMyLeaveRequests();
    }

    @GetMapping("/leave-balance")
    public LeaveBalanceResponse getLeaveBalance(@RequestParam Long employeeId) {
        return leaveRequestService.getLeaveBalance(employeeId);
    }

    @GetMapping("/export/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=leave_requests.csv");

        List<LeaveRequest> list = repository.findAll();

        PrintWriter writer = response.getWriter();
        writer.println("Employee,Type,Start Date,End Date,Reason,Status");

        for (LeaveRequest lr : list) {
            writer.printf("%s,%s,%s,%s,%s,%s%n",
                    lr.getEmployee().getFirstName() + " " + lr.getEmployee().getLastName(),
                    lr.getLeaveType(),
                    lr.getStartDate(),
                    lr.getEndDate(),
                    lr.getReason(),
                    lr.getStatus());
        }
    }
}

