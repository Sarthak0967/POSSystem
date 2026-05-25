package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.payload.dto.UserDto;
import com.sarthak.POSsystem.payload.response.AuthResponse;
import com.sarthak.POSsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Endpoint: http://localhost:5000/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody UserDto userDto
            ) throws UserException {

        return ResponseEntity.ok(
                authService.signup(userDto)
        );
    }

    // Endpoint: http://localhost:5000/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody UserDto userDto
    ) throws UserException {

        return ResponseEntity.ok(
                authService.login(userDto)
        );
    }
    
}
