package com.myproject.CortaCaminho.domain.Profile;

import com.myproject.CortaCaminho.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DataProfile(
        @NotNull
        User user,
        @NotBlank
        String name,
        @NotNull
        LocalDate dateOfBirth) {
}
