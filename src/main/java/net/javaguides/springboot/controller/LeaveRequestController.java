package net.javaguides.springboot.controller;
import net.javaguides.springboot.DTO.leaverequest.LeaveBalanceResponse;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestRequestDTO;
import net.javaguides.springboot.DTO.leaverequest.LeaveRequestResponseDTO;
import net.javaguides.springboot.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping
    public List<LeaveRequestResponseDTO> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
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
}

