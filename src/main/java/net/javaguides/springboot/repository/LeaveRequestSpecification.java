package net.javaguides.springboot.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.LeaveRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestSpecification {

    public static Specification<LeaveRequest> filter(
            String employeeFirstName,
            String employeeLastName,
            String status,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join with Employee table
            Join<LeaveRequest, Employee> employeeJoin = root.join("employee");

            if (employeeFirstName != null && !employeeFirstName.isEmpty()) {
                predicates.add(
                        cb.like(cb.lower(employeeJoin.get("firstName")), "%" + employeeFirstName.toLowerCase() + "%")
                );
            }

            if (employeeLastName != null && !employeeLastName.isEmpty()) {
                predicates.add(
                        cb.like(cb.lower(employeeJoin.get("lastName")), "%" + employeeLastName.toLowerCase() + "%")
                );
            }

            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), toDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}