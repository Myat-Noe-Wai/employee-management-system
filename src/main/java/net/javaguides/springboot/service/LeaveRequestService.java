package net.javaguides.springboot.service;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.DTO.event.LeaveRequestEvent;
import net.javaguides.springboot.DTO.leaverequest.LeaveBalanceResponse;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestRequestDTO;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestResponseDTO;
import net.javaguides.springboot.model.LeaveRequest;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.LeaveRequestRepository;
import net.javaguides.springboot.shared.exception.GeneralException;
import net.javaguides.springboot.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmailService emailService;

    public List<LeaveRequestResponseDTO> getAllLeaveRequests() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveRequestResponseDTO getLeaveRequestById(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        log.info("got leave request ", leaveRequest);
        return toResponseDTO(leaveRequest);
    }

    public Page<LeaveRequestResponseDTO> getMyLeaveRequests(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository
                .findByUser_Email(email)
                .orElseThrow(() ->
                        new GeneralException("Employee not found for logged-in user"));

        return leaveRequestRepository
                .findByEmployeeId(employee.getId(), pageable)
                .map(this::toResponseDTO);
    }

    // ---------------- Commands ----------------
    public LeaveRequestResponseDTO applyForLeave(LeaveRequestRequestDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository.findByUser_Email(email).orElseThrow(() ->
                        new GeneralException("User not found with this email"));

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(dto.getLeaveType());
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus("Pending");

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        // Get all admin emails dynamically
        List<Employee> admins = employeeRepository.findByUser_Role("admin");
        System.out.println("Admin " + admins.toArray().length);
        List<String> adminEmails = admins.stream()
                .map(emp -> emp.getUser().getEmail())
                .toList();

        // Send emails to employee and all admins
        emailService.sendLeaveSubmissionEmail(
                employee.getUser().getEmail(),
                adminEmails,
                employee.getFirstName() + " " + employee.getLastName(),
                leaveRequest.getLeaveType().toString(),
                leaveRequest.getStartDate().toString(),
                leaveRequest.getEndDate().toString(),
                leaveRequest.getReason()
        );

        return toResponseDTO(saved);
    }

    public LeaveRequestResponseDTO approveLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getEntity(id);
        leaveRequest.setStatus("Approved");
        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        String html = emailService.buildLeaveEmailHtml(
                leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName(),
                saved.getLeaveType().name(),
                saved.getStartDate().toString(),
                saved.getEndDate().toString(),
                saved.getReason(),
                saved.getStatus(),
                "Your leave request has been approved."
        );

        emailService.sendHtmlEmail(leaveRequest.getEmployee().getUser().getEmail(), "Leave Request Approved", html);

        return toResponseDTO(saved);
    }

    public LeaveRequestResponseDTO rejectLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getEntity(id);
        leaveRequest.setStatus("Rejected");
        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        String html = emailService.buildLeaveEmailHtml(
                leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName(),
                saved.getLeaveType().name(),
                saved.getStartDate().toString(),
                saved.getEndDate().toString(),
                saved.getReason(),
                saved.getStatus(),
                "Your leave request has been rejected. Please contact your manager if you have questions."
        );

        emailService.sendHtmlEmail(leaveRequest.getEmployee().getUser().getEmail(), "Leave Request Rejected", html);

        return toResponseDTO(saved);
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public LeaveBalanceResponse getLeaveBalance(Long userId) {
        log.info("Fetching leave balance for employeeId={}", userId);
        // 🔑 Resolve employee from userId
        Employee employee = employeeRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for userId=", userId));

        int totalDays = employee.getLeaveDay();
        int usedDays = leaveRequestRepository.countApprovedLeaves(employee.getId());

        int remaining = totalDays - usedDays;

        return new LeaveBalanceResponse(totalDays, usedDays, remaining);
    }

    // ---------------- Helpers ----------------

    private LeaveRequest getEntity(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }

    private LeaveRequestResponseDTO toResponseDTO(LeaveRequest leaveRequest) {
        return new LeaveRequestResponseDTO(
                leaveRequest.getId(),
                leaveRequest.getEmployee().getId(),
                leaveRequest.getEmployee().getFirstName() + " " +
                        leaveRequest.getEmployee().getLastName(),
                leaveRequest.getLeaveType(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus()
        );
    }
}

