package net.javaguides.springboot.DTO.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
	private Long id;
	private Boolean status;
	private String userName;
	private String role;
	private String message;
	private String accessToken;

	public LoginResponseDTO(String message, Boolean status) {
		this.message = message;
		this.status = status;
	}

	public LoginResponseDTO(String message, Boolean status, Long id, String userName, String role, String accessToken) {
		this.message = message;
		this.status = status;
		this.id = id;
		this.userName = userName;
		this.role = role;
		this.accessToken = accessToken;
	}
}
