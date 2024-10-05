package net.javaguides.springboot.service;
import net.javaguides.springboot.model.LeaveRequest;
import net.javaguides.springboot.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id).orElse(null);
    }

    public LeaveRequest createOrUpdateLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public LeaveRequest approveLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        if (leaveRequest != null) {
            leaveRequest.setStatus("Approved");
            leaveRequestRepository.save(leaveRequest);
        }
        return leaveRequest;
    }

    public LeaveRequest rejectLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        if (leaveRequest != null) {
            leaveRequest.setStatus("Rejected");
            leaveRequestRepository.save(leaveRequest);
        }
        return leaveRequest;
    }
    
    public LeaveRequest applyForLeave(LeaveRequest leaveRequest) {
    	leaveRequest.setStatus("Pending");
        return leaveRequestRepository.save(leaveRequest);
    }
    
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }
}

