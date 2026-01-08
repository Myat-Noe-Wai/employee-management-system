package net.javaguides.springboot.service;

import net.javaguides.springboot.DTO.login.LoginDTO;
import net.javaguides.springboot.DTO.login.LoginResponseDTO;
import net.javaguides.springboot.DTO.user.UserDTO;
import net.javaguides.springboot.DTO.user.UserResponseDTO;
import net.javaguides.springboot.shared.exception.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse<UserResponseDTO> addUser(UserDTO userDTO);
    LoginResponseDTO loginUser(LoginDTO loginDTO);
    List<UserResponseDTO> getAllUsers();
}
