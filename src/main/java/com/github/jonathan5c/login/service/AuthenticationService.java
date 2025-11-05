package com.github.jonathan5c.login.service;

import com.github.jonathan5c.login.config.TokenConfig;
import com.github.jonathan5c.login.dto.request.LoginRequest;
import com.github.jonathan5c.login.dto.request.RegisterRequest;
import com.github.jonathan5c.login.dto.response.LoginResponse;
import com.github.jonathan5c.login.dto.response.RegisterResponse;
import com.github.jonathan5c.login.entity.UserEntity;
import com.github.jonathan5c.login.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
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
    private final EmailService emailService;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticatioManager,
                                 TokenConfig tokenConfig, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.authenticatinManager = authenticatioManager;
        this.tokenConfig = tokenConfig;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public LoginResponse login(@NotNull LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticatinManager.authenticate(userAndPass);

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);
        return new LoginResponse(token);
    }

    public RegisterResponse register(@NotNull RegisterRequest register) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(register.email());
        newUser.setName(register.name());
        newUser.setPassword(passwordEncoder.encode(register.password()));

        String subject = "E-mail criado com sucesso";
        String mensage = """
                Olá pequeno garfanhoto! <br>
                Fico feliz que tenha feito cadastro no meu sistema! <br>
                Agora você pode aproveitar mais sobre o sistema.
                """;
        emailService.sendEmail(newUser.getEmail(), subject, mensage);

        userRepository.save(newUser);

        return new RegisterResponse(newUser.getName(), newUser.getEmail(), newUser.getUsername());
    }
}
