package net.javaguides.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.DTO.login.TokenRefreshResponseDto;
import net.javaguides.springboot.DTO.user.UserResponseDTO;
import net.javaguides.springboot.model.RefreshTokenEntity;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.RefreshTokenRepository;
import net.javaguides.springboot.repository.UserRepo;
import net.javaguides.springboot.shared.config.JwtUtil;
import net.javaguides.springboot.shared.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.DTO.login.LoginDTO;
import net.javaguides.springboot.DTO.login.LoginResponseDTO;
import net.javaguides.springboot.DTO.user.UserDTO;
import net.javaguides.springboot.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    private final JwtUtil jwtUtil;

    private final UserRepo userRepo;

    private final RefreshTokenRepository refreshTokenRepository;
    
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

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponseDto> refreshToken(HttpServletRequest request) {
        log.info("Received request to refresh access token.");
        String refreshToken = jwtUtil.extractToken(request);
        log.info("RefreshToken : {}", refreshToken);
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            log.info("RefreshToken is null or sth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String email = jwtUtil.extractEmail(refreshToken);
        Optional<User> userOpt = userRepo.findByEmail(email);
        log.info("UserOpt email {}", userOpt.get().getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        User user = userOpt.get();

        Optional<RefreshTokenEntity> tokenOpt = refreshTokenRepository.findByToken(refreshToken);
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            log.info("TokenOpt is empty ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // ✅ Refresh access token only
        String newAccessToken = jwtUtil.generateToken(email);

        return ResponseEntity.ok(new TokenRefreshResponseDto(newAccessToken, refreshToken));
    }
}
