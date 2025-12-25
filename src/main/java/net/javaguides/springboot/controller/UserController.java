package net.javaguides.springboot.controller;

import net.javaguides.springboot.DTO.user.UserResponseDTO;
import net.javaguides.springboot.shared.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.DTO.login.LoginDTO;
import net.javaguides.springboot.DTO.login.LoginResponse;
import net.javaguides.springboot.DTO.user.UserDTO;
import net.javaguides.springboot.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*")
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping(path = "/save")
    public ResponseEntity<ApiResponse<UserResponseDTO>> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.addUser(userDTO));
    }
    
    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        LoginResponse loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
