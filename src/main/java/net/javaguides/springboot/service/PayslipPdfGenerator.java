package net.javaguides.springboot.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import net.javaguides.springboot.model.Payslip;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Component
public class PayslipPdfGenerator {

    public byte[] generate(Payslip p) {

        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font title = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font head = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            // ===== HEADER =====
            Paragraph company = new Paragraph("Company Payslip", title);
            company.setAlignment(Element.ALIGN_CENTER);
            document.add(company);
            document.add(new Paragraph("Generated: " + LocalDate.now(), normal));
            document.add(Chunk.NEWLINE);

            // ===== EMPLOYEE INFO =====
            PdfPTable emp = new PdfPTable(2);
            emp.setWidthPercentage(100);

            emp.addCell(cell("Employee", head));
            emp.addCell(cell(p.getEmployee().getFirstName()+" "+p.getEmployee().getLastName(), normal));

            emp.addCell(cell("Month", head));
            emp.addCell(cell(String.valueOf(p.getMonth()), normal));

            emp.addCell(cell("Year", head));
            emp.addCell(cell(String.valueOf(p.getYear()), normal));

            document.add(emp);
            document.add(Chunk.NEWLINE);

            // ===== EARNINGS =====
            Paragraph earningsTitle = new Paragraph("Earnings", head);
            earningsTitle.setSpacingAfter(10);
            document.add(earningsTitle);
            PdfPTable earning = new PdfPTable(2);
            earning.setWidthPercentage(100);

            earning.addCell(cell("Basic Salary", head));
            earning.addCell(cell(val(p.getBasicSalary()), normal));

            earning.addCell(cell("Allowances", head));
            earning.addCell(cell(val(p.getAllowances()), normal));

            earning.addCell(cell("Overtime", head));
            earning.addCell(cell(val(p.getOvertimePay()), normal));

            earning.addCell(cell("Bonus", head));
            earning.addCell(cell(val(p.getBonus()), normal));

            document.add(earning);
            document.add(Chunk.NEWLINE);

            // ===== DEDUCTIONS =====
            Paragraph deductionTitle = new Paragraph("Deductions", head);
            deductionTitle.setSpacingAfter(10);
            document.add(deductionTitle);
            PdfPTable tax = new PdfPTable(2);
            tax.setWidthPercentage(100);

            tax.addCell(cell("Tax", head));
            tax.addCell(cell(val(p.getTax()), normal));

            tax.addCell(cell("Leave Deduction", head));
            tax.addCell(cell(val(p.getUnpaidLeaveDeduction()), normal));

            tax.addCell(cell("Other", head));
            tax.addCell(cell(val(p.getDeductions()), normal));

            document.add(tax);
            document.add(Chunk.NEWLINE);

            // ===== NET =====
            Paragraph net = new Paragraph("Net Salary: " + p.getNetSalary(), title);
            net.setAlignment(Element.ALIGN_RIGHT);
            document.add(net);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return out.toByteArray();
    }

    private PdfPCell cell(String text, Font f){
        PdfPCell c = new PdfPCell(new Phrase(text,f));
        c.setPadding(8);
        return c;
    }

    private PdfPTable table(String[][] rows){
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        for(String[] r:rows){
            t.addCell(r[0]);
            t.addCell(r[1]);
        }
        return t;
    }

    private String val(double v){
        return String.format("%.2f", v);
    }
}
