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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('VIEW_ALL_LEAVE')")
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
    @PreAuthorize("hasAuthority('APPLY_LEAVE')")
    public LeaveRequestResponseDTO applyForLeave(@RequestBody LeaveRequestRequestDTO dto) {
        return leaveRequestService.applyForLeave(dto);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_LEAVE')")
    public LeaveRequestResponseDTO approveLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.approveLeaveRequest(id);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('REJECT_LEAVE')")
    public LeaveRequestResponseDTO rejectLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.rejectLeaveRequest(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_LEAVE')")
    public void deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('VIEW_OWN_LEAVE')")
    public Page<LeaveRequestResponseDTO> getMyLeaveRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending() // 🔥 latest first
        );
        return leaveRequestService.getMyLeaveRequests(pageable);
    }

    @GetMapping("/leave-balance")
    @PreAuthorize("hasAuthority('VIEW_OWN_LEAVE')")
    public LeaveBalanceResponse getLeaveBalance(@RequestParam Long userId) {
        return leaveRequestService.getLeaveBalance(userId);
    }

    @GetMapping("/export/csv")
    @PreAuthorize("hasAuthority('EXPORT_LEAVE')")
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

