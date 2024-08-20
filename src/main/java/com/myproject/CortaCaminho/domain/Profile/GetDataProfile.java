package com.myproject.CortaCaminho.domain.Profile;

import jakarta.validation.constraints.NotBlank;

public record GetDataProfile(
        @NotBlank
        String email
) {
}
