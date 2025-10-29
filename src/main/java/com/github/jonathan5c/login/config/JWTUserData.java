package com.github.jonathan5c.login.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}
