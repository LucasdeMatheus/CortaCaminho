package com.myproject.CortaCaminho.domain.Search;

import jakarta.validation.constraints.NotBlank;

public record DataPageSearch(
        DataForListingProfile dataProfile,
        DataForListingUrl dataUrl,
        @NotBlank
        Filter filter
) {
}
