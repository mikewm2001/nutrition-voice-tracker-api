package com.nutrition.nutrition_voice_tracker_api.auth;

import com.nutrition.nutrition_voice_tracker_api.auth.dto.AuthResponse;
import com.nutrition.nutrition_voice_tracker_api.auth.dto.LoginRequest;
import com.nutrition.nutrition_voice_tracker_api.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return new AuthResponse(token);
    }
}