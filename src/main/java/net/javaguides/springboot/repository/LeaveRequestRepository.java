package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import net.javaguides.springboot.model.LeaveRequest;
import org.springframework.data.jpa.repository.Query;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	List<LeaveRequest> findByEmployeeId(Long employeeId);
	@Query("""
    	SELECT COUNT(l)
    	FROM LeaveRequest l
    	WHERE l.employee.id = :employeeId
      	AND l.status = 'Approved'
	""")
	int countApprovedLeaves(Long employeeId);
}
