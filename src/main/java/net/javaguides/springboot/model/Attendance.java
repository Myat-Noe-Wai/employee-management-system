package net.javaguides.springboot.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long employeeId;
	private String employeeName;

	private LocalDate date;
	private OffsetDateTime clockIn;
	private OffsetDateTime clockOut;
}