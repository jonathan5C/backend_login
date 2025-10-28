package com.github.jonathan5c.login.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(@NotEmpty(message = "Nome é obrigatório") String name,
                              @NotEmpty(message = "E-mail é obrigatório") String email,
                              @NotEmpty(message = "Senha é obrigatória") String password,
                              @NotEmpty(message = "Usuário é obrigatório") String username) {
}
