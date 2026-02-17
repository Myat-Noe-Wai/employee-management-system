package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.event.LeaveRequestEvent;
import net.javaguides.springboot.DTO.payslip.PayslipRequestDTO;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Payslip;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.PayslipRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayslipService {

    private final PayslipRepository payslipRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher publisher;
    private final PayslipPdfGenerator pdfGenerator;

    public Payslip generatePayslip(PayslipRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        double totalEarnings =
                dto.getBasicSalary()
                        + dto.getAllowances()
                        + dto.getOvertimePay()
                        + dto.getBonus();

        double totalDeductions =
                dto.getDeductions()
                        + dto.getUnpaidLeaveDeduction()
                        + dto.getTax();

        double netSalary = totalEarnings - totalDeductions;

        Payslip payslip = new Payslip();
        payslip.setEmployee(employee);
        payslip.setMonth(dto.getMonth());
        payslip.setYear(dto.getYear());

        payslip.setBasicSalary(dto.getBasicSalary());
        payslip.setAllowances(dto.getAllowances());
        payslip.setOvertimePay(dto.getOvertimePay());
        payslip.setBonus(dto.getBonus());

        payslip.setDeductions(dto.getDeductions());
        payslip.setUnpaidLeaveDeduction(dto.getUnpaidLeaveDeduction());
        payslip.setTax(dto.getTax());

        payslip.setNetSalary(netSalary);

        Payslip saved = payslipRepository.save(payslip);

        publishPayslipEmail(saved);

        return saved;
    }

    private void publishPayslipEmail(Payslip payslip) {

        String email = payslip.getEmployee().getUser().getEmail();
        String subject = "Payslip Generated - " + payslip.getMonth() + "/" + payslip.getYear();

        String html = "<h2>Payslip Generated</h2>"
                + "<p>Dear " + payslip.getEmployee().getFirstName() + ",</p>"
                + "<p>Your payslip is attached.</p>";

        byte[] pdf = pdfGenerator.generate(payslip);

        publisher.publishEvent(
                new LeaveRequestEvent(email, subject, html, pdf)
        );
    }
}

