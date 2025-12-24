package net.javaguides.springboot.service;

import net.javaguides.springboot.DTO.LoginDTO;
import net.javaguides.springboot.DTO.LoginResponse;
import net.javaguides.springboot.DTO.UserDTO;
import net.javaguides.springboot.DTO.UserResponseDTO;
import net.javaguides.springboot.shared.exception.ApiResponse;

public interface UserService {
    ApiResponse<UserResponseDTO> addUser(UserDTO userDTO);
    LoginResponse loginUser(LoginDTO loginDTO);
}
