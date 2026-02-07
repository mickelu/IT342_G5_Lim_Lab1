package com.platform.backend.service;

import com.platform.backend.entity.User;
import com.platform.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // REGISTER
    public User register(String username, String email, String password) {

        // check if user exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // encrypt password
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    // LOGIN
    public User login(String email, String password) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}
