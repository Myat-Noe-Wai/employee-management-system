package net.javaguides.springboot.DTO.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponseDto {
    private String accessToken;
    private String refreshToken;
}

