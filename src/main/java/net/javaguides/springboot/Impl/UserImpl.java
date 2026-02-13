package net.javaguides.springboot.Impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.user.UserResponseDTO;
import net.javaguides.springboot.model.RefreshTokenEntity;
import net.javaguides.springboot.repository.RefreshTokenRepository;
import net.javaguides.springboot.repository.UserRepo;
import net.javaguides.springboot.shared.config.JwtUtil;
import net.javaguides.springboot.shared.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.javaguides.springboot.DTO.login.LoginDTO;
import net.javaguides.springboot.DTO.login.LoginResponseDTO;
import net.javaguides.springboot.DTO.user.UserDTO;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService{
	@Autowired
    private UserRepo userRepo;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    public ApiResponse<UserResponseDTO> addUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("employee"); // default role
        User savedUser = userRepo.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        UserResponseDTO responseDTO = new UserResponseDTO(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                token
        );

        return ApiResponse.success("User registered successfully", responseDTO);
    }

    @Override
    public LoginResponseDTO loginUser(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepo.findByEmail(loginDTO.getEmail());
        if (optionalUser.isEmpty()) {
            return new LoginResponseDTO("Email not exists", false);
        }

        User user = optionalUser.get(); // ✅ unwrap Optional
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new LoginResponseDTO("Invalid password", false);
        }

        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        createOrUpdateRefreshToken(user, refreshToken);

        return new LoginResponseDTO(
                "Login success",
                true,
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                accessToken,
                refreshToken
        );
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        // Fetch all users from DB
        List<User> users = userRepo.findAll();

        // Map to UserResponseDTO (without token)
        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        null // token is null when fetching users
                ))
                .collect(Collectors.toList());
    }

    private void createOrUpdateRefreshToken(User user, String newToken) {
        Optional<RefreshTokenEntity> existingTokenOpt = refreshTokenRepository.findByUser(user);

        RefreshTokenEntity refreshTokenEntity = existingTokenOpt.orElse(new RefreshTokenEntity());
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setToken(newToken);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshTokenEntity);
    }
}
