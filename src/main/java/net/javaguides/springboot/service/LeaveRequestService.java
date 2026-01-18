package net.javaguides.springboot.service;
import lombok.extern.slf4j.Slf4j;
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

    public List<LeaveRequestResponseDTO> getMyLeaveRequests() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository
                .findByUser_Email(email)
                .orElseThrow(() ->
                        new GeneralException("Employee not found for logged-in user"));

        return leaveRequestRepository.findByEmployeeId(employee.getId())
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
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
        return toResponseDTO(saved);
    }

    public LeaveRequestResponseDTO approveLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getEntity(id);
        leaveRequest.setStatus("Approved");
        return toResponseDTO(leaveRequestRepository.save(leaveRequest));
    }

    public LeaveRequestResponseDTO rejectLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getEntity(id);
        leaveRequest.setStatus("Rejected");
        return toResponseDTO(leaveRequestRepository.save(leaveRequest));
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public LeaveBalanceResponse getLeaveBalance(Long employeeId) {
        log.info("Fetching leave balance for employeeId={}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    log.error("Employee not found with id={}", employeeId);
                    return new GeneralException("Employee not found");
                });
        int totalDays = employee.getLeaveDay();
        int usedDays = leaveRequestRepository.countApprovedLeaves(employeeId);

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

