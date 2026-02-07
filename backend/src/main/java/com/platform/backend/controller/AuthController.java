package com.platform.backend.controller;

import com.platform.backend.entity.User;
import com.platform.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // for React later
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
    }

    // LOGIN
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}
