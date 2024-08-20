package com.myproject.CortaCaminho.domain.Url.Datas;

import jakarta.validation.constraints.NotBlank;

public record DataKeyword(
        @NotBlank
        String keywords
) {
}
