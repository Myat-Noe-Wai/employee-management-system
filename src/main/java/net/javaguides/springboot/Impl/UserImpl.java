package net.javaguides.springboot.Impl;
import java.util.Optional;

import net.javaguides.springboot.DTO.UserResponseDTO;
import net.javaguides.springboot.shared.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.javaguides.springboot.DTO.LoginDTO;
import net.javaguides.springboot.DTO.LoginResponse;
import net.javaguides.springboot.DTO.UserDTO;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;

@Service
public class UserImpl implements UserService{
	@Autowired
    private UserRepo userRepo;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public ApiResponse<UserResponseDTO> addUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("employee"); // default role
        User savedUser = userRepo.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );

        return ApiResponse.success("User registered successfully", responseDTO);
    }
    
    UserDTO userDTO;    
    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String msg = "";
        User user1 = userRepo.findByEmail(loginDTO.getEmail());
        if (user1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//            Boolean isPwdRight = false;
            if(password.equals(user1.getPassword())) {
            	isPwdRight = true;
            }
            if (isPwdRight) {
                Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                	System.out.println("Role " + user1.getRole());
                	if(user1.getRole().equals("admin")) {
                		return new LoginResponse("Admin Login Success", true, user1.getUserId(), user1.getUsername());
                	} else {
                		System.out.print("emp login");
                		return new LoginResponse("Employee Login Success", true, user1.getUserId(), user1.getUsername());
                	}
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password Not Match", false);
            }                       
        }else {
            return new LoginResponse("Email not exits", false);
        }
    }
}
