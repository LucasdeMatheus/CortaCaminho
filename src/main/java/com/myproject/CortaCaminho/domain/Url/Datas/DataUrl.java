package com.myproject.CortaCaminho.domain.Url.Datas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUrl(
        @NotBlank
        String url,
        @NotNull
        String titulo,
        String img
) {
}
