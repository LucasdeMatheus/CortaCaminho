package com.myproject.CortaCaminho.domain.Search;

public enum Filter {
    FALSE,
    ASCENDING,      // Ordenação crescente
    DESASCENDING,   // Ordenação decrescente
    MOREVIEWS,      // Mais visualizações
    LESSVIEWS;      // Menos visualizações

    public static Filter fromString(String filter) {
        if (filter == null) {
            return null; // ou um valor padrão se preferir
        }
        try {
            return Filter.valueOf(filter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // ou um valor padrão se preferir
        }
    }
}

