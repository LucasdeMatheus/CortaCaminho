package com.myproject.CortaCaminho.domain.user;

import jakarta.validation.constraints.NotBlank;

public record DataAuthentication(
        @NotBlank
        String login,
        @NotBlank
        String password) {
}
