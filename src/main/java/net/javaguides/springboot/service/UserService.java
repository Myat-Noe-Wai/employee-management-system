package net.javaguides.springboot.service;

import net.javaguides.springboot.DTO.LoginDTO;
import net.javaguides.springboot.DTO.LoginResponse;
import net.javaguides.springboot.DTO.UserDTO;

public interface UserService {
	String addUser(UserDTO userDTO);
    LoginResponse loginUser(LoginDTO loginDTO);
}
