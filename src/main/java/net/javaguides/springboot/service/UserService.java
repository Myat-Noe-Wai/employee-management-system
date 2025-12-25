package net.javaguides.springboot.service;

import net.javaguides.springboot.DTO.login.LoginDTO;
import net.javaguides.springboot.DTO.login.LoginResponse;
import net.javaguides.springboot.DTO.user.UserDTO;
import net.javaguides.springboot.DTO.user.UserResponseDTO;
import net.javaguides.springboot.shared.exception.ApiResponse;

public interface UserService {
    ApiResponse<UserResponseDTO> addUser(UserDTO userDTO);
    LoginResponse loginUser(LoginDTO loginDTO);
}
