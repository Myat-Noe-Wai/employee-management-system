package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.javaguides.springboot.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Optional<Employee> findByUser_Email(String email);
    Optional<Employee> findByUser_UserId(Long userId);
    List<Employee> findByUser_Role(String roleName);
}
