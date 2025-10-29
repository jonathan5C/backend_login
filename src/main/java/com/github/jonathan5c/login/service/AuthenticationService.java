package com.github.jonathan5c.login.service;

import com.github.jonathan5c.login.config.TokenConfig;
import com.github.jonathan5c.login.dto.request.LoginRequest;
import com.github.jonathan5c.login.dto.request.RegisterRequest;
import com.github.jonathan5c.login.dto.response.LoginResponse;
import com.github.jonathan5c.login.dto.response.RegisterResponse;
import com.github.jonathan5c.login.entity.UserEntity;
import com.github.jonathan5c.login.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticatinManager;
    private final TokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticatioManager,
                                 TokenConfig tokenConfig, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticatinManager = authenticatioManager;
        this.tokenConfig = tokenConfig;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticatinManager.authenticate(userAndPass);

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);
        return new LoginResponse(token);
    }

    public RegisterResponse register(RegisterRequest register) {
        UserEntity newUser = new UserEntity();
        newUser.setPassword(passwordEncoder.encode(register.password()));
        newUser.setEmail(register.email());
        newUser.setName(register.name());
        userRepository.save(newUser);

        return new RegisterResponse(newUser.getName(), newUser.getEmail(), newUser.getUsername());
    }
}
