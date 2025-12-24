package net.javaguides.springboot.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
}
