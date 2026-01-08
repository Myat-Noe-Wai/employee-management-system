package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.DTO.employee.EmployeeRequestDTO;
import net.javaguides.springboot.DTO.employee.EmployeeResponseDTO;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepo;
import net.javaguides.springboot.shared.exception.ApiResponse;
import net.javaguides.springboot.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepo userRepo;

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApiResponse<EmployeeResponseDTO> createEmployee(EmployeeRequestDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        Employee employee = mapToEntity(dto);
        employee.setUser(user);

        Employee saved = employeeRepository.save(employee);
        EmployeeResponseDTO mappedEmployee = mapToResponse(saved);

        return ApiResponse.success("Employee is created successfully", mappedEmployee);
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        return mapToResponse(employee);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmailId(dto.getEmailId());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setGender(dto.getGender());
        employee.setContactInfo(dto.getContactInfo());
        employee.setAddress(dto.getAddress());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setSalary(dto.getSalary());
        employee.setLeaveDay(dto.getLeaveDay());
        employee.setJobTitle(dto.getJobTitle());

        log.info("Job Title: {} ", dto.getJobTitle());
        Employee updated = employeeRepository.save(employee);
        log.info("Employee details updated successfully: {}", updated);

        return mapToResponse(updated);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        employeeRepository.delete(employee);
    }

    // ---------- Mapping Methods ----------

    private Employee mapToEntity(EmployeeRequestDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmailId(dto.getEmailId());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setGender(dto.getGender());
        employee.setContactInfo(dto.getContactInfo());
        employee.setAddress(dto.getAddress());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setSalary(dto.getSalary());
        employee.setLeaveDay(dto.getLeaveDay());
        employee.setJobTitle(dto.getJobTitle());
        employee.setUser(user);
        return employee;
    }

    private EmployeeResponseDTO mapToResponse(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmailId(),
                employee.getDateOfBirth(),
                employee.getGender(),
                employee.getContactInfo(),
                employee.getAddress(),
                employee.getJoiningDate(),
                employee.getSalary(),
                employee.getLeaveDay(),
                employee.getJobTitle()
        );
    }
}
