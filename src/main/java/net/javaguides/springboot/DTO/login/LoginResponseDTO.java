package net.javaguides.springboot.DTO.login;

import lombok.*;

@Data
public class LoginResponseDTO {
	private Long id;
	private Boolean status;
	private String userName;
	private String email;
	private String role;
	private String message;
	private String accessToken;
	private String refreshToken;

	public LoginResponseDTO(String message, Boolean status) {
		this.message = message;
		this.status = status;
	}

	public LoginResponseDTO(String message, Boolean status, Long id, String userName,
							String email, String role, String accessToken, String refreshToken) {
		this.message = message;
		this.status = status;
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.role = role;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
