package net.javaguides.springboot.DTO.employee;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private Date dateOfBirth;
    private String gender;
    private String contactInfo;
    private String address;
    private Date joiningDate;
    private int salary;
    private int leaveDay;
    private String jobTitle;
}

