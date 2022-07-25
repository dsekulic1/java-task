package com.example.task.service;

import com.example.task.exception.BadRequestException;
import com.example.task.exception.ConflictException;
import com.example.task.model.Role;
import com.example.task.model.User;
import com.example.task.repositories.UserRepository;
import com.example.task.request.LoginRequest;
import com.example.task.request.SignUpRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(SignUpRequest signUpRequest) {
        final String email = signUpRequest.getEmail();
        final String username = signUpRequest.getUsername();

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ConflictException("Username is already taken");
        }

        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email is already taken");
        }

        final boolean isAdminUser = email.contains("admin");

        User p = new User(
                email,
                username,
                passwordEncoder.encode(signUpRequest.getPassword()),
                isAdminUser ? Role.ROLE_ADMIN : Role.ROLE_USER
        );

        return userRepository.save(p);
    }

    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found with username: " + loginRequest.getUsername());
                } );

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password!");
        }

        return user;
    }
}
