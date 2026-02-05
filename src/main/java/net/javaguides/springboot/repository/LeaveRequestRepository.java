package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.javaguides.springboot.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>, JpaSpecificationExecutor<LeaveRequest> {
	List<LeaveRequest> findByEmployeeId(Long employeeId);
	Page<LeaveRequest> findByEmployeeId(Long employeeId, Pageable pageable);
	@Query("""
    	SELECT COUNT(l)
    	FROM LeaveRequest l
    	WHERE l.employee.id = :employeeId
      	AND l.status = 'Approved'
	""")
	int countApprovedLeaves(Long employeeId);
}
