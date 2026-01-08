package net.javaguides.springboot.controller;

import net.javaguides.springboot.DTO.user.UserResponseDTO;
import net.javaguides.springboot.shared.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.DTO.login.LoginDTO;
import net.javaguides.springboot.DTO.login.LoginResponseDTO;
import net.javaguides.springboot.DTO.user.UserDTO;
import net.javaguides.springboot.service.UserService;

import java.util.List;

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
        LoginResponseDTO loginResponseDTO = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
