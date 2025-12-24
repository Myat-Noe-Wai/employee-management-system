package net.javaguides.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private int userId;
    private String username;
    private String email;
    private String role;
}

