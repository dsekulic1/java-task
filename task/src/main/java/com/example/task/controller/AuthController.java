package com.example.task.controller;


import com.example.task.model.Role;
import com.example.task.model.User;
import com.example.task.request.LoginRequest;
import com.example.task.request.SignUpRequest;
import com.example.task.response.LoginResponse;
import com.example.task.security.JwtUtils;
import com.example.task.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtils jwtTokenUtil;
    private final AuthService authService;

    @Autowired
    public AuthController(JwtUtils jwtTokenUtil, AuthService authService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        User user = authService.signup(signUpRequest);

        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok().body(new LoginResponse(
                "Bearer",
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                getRoles(user.getRole())
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        User user = authService.login(loginRequest);

        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok().body(new LoginResponse(
                "Bearer",
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                getRoles(user.getRole())
        ));
    }

    private ArrayList<String> getRoles (Role role) {
        ArrayList<String> roles = new ArrayList<>();
        if (role == Role.ROLE_ADMIN) {
            roles.add("ROLE_ADMIN");
        }
        roles.add("ROLE_USER");

        return roles;
    }
}

