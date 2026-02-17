package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {

    List<Payslip> findByEmployeeId(Long employeeId);

    List<Payslip> findByMonthAndYear(Integer month, Integer year);

    Optional<Payslip> findByEmployeeIdAndMonthAndYear(
            Long employeeId, Integer month, Integer year
    );
}

