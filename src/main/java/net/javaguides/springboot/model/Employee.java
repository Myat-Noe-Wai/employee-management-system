package net.javaguides.springboot.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "contact_info")
	private String contactInfo;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "joining_date")
	private Date joiningDate;
	
	@Column(name = "salary")
	private int salary;
	
	@Column(name = "leave_day")
	private int leaveDay;

	private String jobTitle;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	private User user;
}
