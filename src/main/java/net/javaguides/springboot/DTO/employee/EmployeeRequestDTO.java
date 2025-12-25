package net.javaguides.springboot.DTO.employee;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDTO {
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

