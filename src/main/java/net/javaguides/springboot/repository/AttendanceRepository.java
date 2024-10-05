package net.javaguides.springboot.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	Optional<Attendance> findByDateAndEmployeeId(LocalDate date, Long employeeId);
}
