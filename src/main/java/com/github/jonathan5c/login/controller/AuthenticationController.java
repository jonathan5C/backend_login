package com.github.jonathan5c.login.controller;

import com.github.jonathan5c.login.dto.request.LoginRequest;
import com.github.jonathan5c.login.dto.request.RegisterRequest;
import com.github.jonathan5c.login.dto.response.LoginResponse;
import com.github.jonathan5c.login.dto.response.RegisterResponse;
import com.github.jonathan5c.login.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest register) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.register(register));
    }
}
