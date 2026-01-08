package net.javaguides.springboot.service;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestRequestDTO;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestResponseDTO;
import net.javaguides.springboot.model.LeaveRequest;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.LeaveRequestRepository;
import net.javaguides.springboot.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<LeaveRequestResponseDTO> getLeaveRequestsByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ---------------- Commands ----------------

    public LeaveRequestResponseDTO applyForLeave(LeaveRequestRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", dto.getEmployeeId()));

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

