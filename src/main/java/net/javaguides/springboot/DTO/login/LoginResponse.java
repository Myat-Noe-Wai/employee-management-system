package net.javaguides.springboot.DTO.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private Long id;
	private Boolean status;
	private String empName;
	private String message;
	private String accessToken;

	public LoginResponse(String message, Boolean status) {
		this.message = message;
		this.status = status;
	}

	public LoginResponse(String message, Boolean status, Long id, String empName, String accessToken) {
		this.message = message;
		this.status = status;
		this.id = id;
		this.empName = empName;
		this.accessToken = accessToken;
	}
}
