package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Send HTML email asynchronously
     *
     * @param to      Recipient email address
     * @param subject Email subject
     * @param htmlBody HTML content of the email
     */
    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            log.info("Sending HTML email to: {}", to);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML content

            mailSender.send(message);
            log.info("HTML email sent successfully to {}", to);

        } catch (MessagingException e) {
            log.error("Error sending HTML email to {}: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendLeaveSubmissionEmail(String employeeEmail,
                                         List<String> adminEmails,
                                         String employeeName,
                                         String leaveType,
                                         String startDate,
                                         String endDate,
                                         String reason) {

        try {
            // 1️⃣ Send email to Employee
            String htmlEmployee = buildLeaveEmailHtml(
                    employeeName,
                    leaveType,
                    startDate,
                    endDate,
                    reason,
                    "Pending",
                    "Your leave request has been submitted and is pending approval."
            );
            sendHtmlEmail(employeeEmail, "Leave Request Submitted", htmlEmployee);

            // 2️⃣ Send email to all admins
            String htmlAdmin = buildLeaveEmailHtml(
                    employeeName,
                    leaveType,
                    startDate,
                    endDate,
                    reason,
                    "Pending",
                    "A new leave request has been submitted by the employee. Please review and approve/reject."
            );

            for (String adminEmail : adminEmails) {
                sendHtmlEmail(adminEmail, "New Leave Request Submitted", htmlAdmin);
            }

        } catch (Exception e) {
            log.error("Error sending leave submission emails: {}", e.getMessage());
        }
    }

    public String buildLeaveEmailHtml(String employeeName, String leaveType,
                                      String startDate, String endDate,
                                      String reason, String status, String extraMessage) {

        // Inline CSS styles for professional look
        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                <h2 style="color: #2F80ED;">Leave Request Notification</h2>
                <p>Dear <b>%s</b>,</p>
                <p>Your leave request status is now: 
                    <span style="color: %s; font-weight: bold;">%s</span>
                </p>
                <table style="width: 100%%; border-collapse: collapse; margin-top: 15px;">
                    <tr>
                        <td style="padding: 8px; border: 1px solid #ddd;"><b>Leave Type</b></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid #ddd;"><b>Start Date</b></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid #ddd;"><b>End Date</b></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                    </tr>
                    %s
                </table>
                <p style="margin-top: 20px;">%s</p>
                <p>Thank you.</p>
            </div>
        </body>
        </html>
        """.formatted(
                employeeName,
                status.equals("Approved") ? "green" : status.equals("Rejected") ? "red" : "orange",
                status,
                leaveType,
                startDate,
                endDate,
                (reason != null && !reason.isEmpty() ? "<tr><td style='padding: 8px; border: 1px solid #ddd;'><b>Reason</b></td><td style='padding: 8px; border: 1px solid #ddd;'>" + reason + "</td></tr>" : ""),
                extraMessage
        );
    }

    @Async
    public void sendPayslipWithAttachment(String to, String subject, String html, byte[] pdf) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            helper.addAttachment("Payslip.pdf",
                    new org.springframework.core.io.ByteArrayResource(pdf));

            mailSender.send(message);

        } catch (Exception e) {
            log.error("Failed to send payslip email {}", e.getMessage());
        }
    }
}
