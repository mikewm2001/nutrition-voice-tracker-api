package com.nutrition.nutrition_voice_tracker_api.auth;

import com.nutrition.nutrition_voice_tracker_api.auth.dto.LoginRequest;
import com.nutrition.nutrition_voice_tracker_api.auth.dto.RegisterRequest;
import com.nutrition.nutrition_voice_tracker_api.domain.User;
import com.nutrition.nutrition_voice_tracker_api.repository.UserRepository;
import com.nutrition.nutrition_voice_tracker_api.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getEmail(), hashedPassword);
        userRepository.save(user);

        return jwtService.generateToken(user.getId(), user.getEmail());
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtService.generateToken(user.getId(), user.getEmail());
    }
}