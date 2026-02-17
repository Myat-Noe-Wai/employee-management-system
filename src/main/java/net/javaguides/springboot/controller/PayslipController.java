package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.payslip.PayslipRequestDTO;
import net.javaguides.springboot.model.Payslip;
import net.javaguides.springboot.repository.PayslipRepository;
import net.javaguides.springboot.service.PayslipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
public class PayslipController {

    private final PayslipService payslipService;
    private final PayslipRepository payslipRepository;

    @PostMapping("/generate")
    public ResponseEntity<?> generatePayslip(
            @RequestBody PayslipRequestDTO dto) {

        return ResponseEntity.ok(
                payslipService.generatePayslip(dto)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public List<Payslip> getByEmployee(
            @PathVariable Long employeeId) {

        return payslipRepository.findByEmployeeId(employeeId);
    }

    @GetMapping
    public List<Payslip> getAll() {
        return payslipRepository.findAll();
    }
}

