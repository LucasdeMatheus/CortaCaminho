package com.myproject.CortaCaminho.domain.Search;

import com.myproject.CortaCaminho.domain.Profile.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilterSearchService {

    public Map<String, Page<?>> toFilterResponse(Page<DataForListingUrl> urlPage, Page<DataForListingProfile> profilePage, Page<DataForListingUrl> keywordPage, Filter filter, Pageable pageable) {

        switch (filter) {
            case ASCENDING:
                return Ascending(urlPage, pageable, profilePage, keywordPage);
            case DESASCENDING:
                return Desascending(urlPage, pageable, profilePage, keywordPage);
            case MOREVIEWS:
                return MoreViews(urlPage, pageable);
            case LESSVIEWS:
                return lessViews(urlPage, pageable);
            default:
                // Caso não haja filtro, ou seja um valor inesperado
                throw new IllegalArgumentException("Filtro desconhecido: " + filter);
        }
    }




    public Map<String, Page<?>> Ascending(Page<DataForListingUrl> urlPage, Pageable pageable, Page<DataForListingProfile> profilePage, Page<DataForListingUrl> keywordPage){
        // Ordena as listas
        var profileAscendingList = profilePage.stream()
                .sorted(Comparator.comparing(p -> p.name().toLowerCase()))
                .collect(Collectors.toList());
        var profileAscending = new PageImpl<>(profileAscendingList, pageable, profilePage.getTotalElements());

        var urlAscendingList = urlPage.stream()
                .sorted(Comparator.comparing(u -> u.title().toLowerCase()))
                .collect(Collectors.toList());
        var urlAscending = new PageImpl<>(urlAscendingList, pageable, urlPage.getTotalElements());

        var keywordAscendingList = keywordPage.stream()
                .sorted(Comparator.comparing(u -> u.title().toLowerCase()))
                .collect(Collectors.toList());
        var keywordAscending = new PageImpl<>(keywordAscendingList, pageable, keywordPage.getTotalElements());

        // Cria o Map com chaves únicas
        Map<String, Page<?>> response = new HashMap<>();
        response.put("urlsByTitle", urlAscending);     // Chave para URLs por título
        response.put("profilesByLogin", profileAscending); // Chave para perfis
        response.put("urlsByKeyword", keywordAscending); // Chave para URLs por palavra-chave

        return response;
    }

    private Map<String, Page<?>> Desascending(Page<DataForListingUrl> urlPage, Pageable pageable, Page<DataForListingProfile> profilePage, Page<DataForListingUrl> keywordPage) {
        // Ordena as listas
        var profileDescendingList = profilePage.stream()
                .sorted(Comparator.comparing(DataForListingProfile::name).reversed())
                .collect(Collectors.toList());
        var profileAscending = new PageImpl<>(profileDescendingList, pageable, profilePage.getTotalElements());

        var urlDescendingList = urlPage.stream()
                .sorted(Comparator.comparing(DataForListingUrl::title).reversed())
                        .collect(Collectors.toList());
        var urlAscending = new PageImpl<>(urlDescendingList, pageable, urlPage.getTotalElements());

        var keywordDescendingList = keywordPage.stream()
                .sorted(Comparator.comparing(DataForListingUrl::title).reversed())
                .collect(Collectors.toList());
        var keywordAscending = new PageImpl<>(keywordDescendingList, pageable, keywordPage.getTotalElements());

        // Cria o Map com chaves únicas
        Map<String, Page<?>> response = new HashMap<>();
        response.put("urlsByTitle", urlAscending);     // Chave para URLs por título
        response.put("profilesByLogin", profileAscending); // Chave para perfis
        response.put("urlsByKeyword", keywordAscending); // Chave para URLs por palavra-chave

        return response;
    }

    private Map<String, Page<?>> MoreViews(Page<DataForListingUrl> urlPage, Pageable pageable) {
        var urlDescendingList = urlPage.stream()
                .sorted(Comparator.comparing(DataForListingUrl::views))
                .collect(Collectors.toList());
        var urlMoreViews = new PageImpl<>(urlDescendingList, pageable, urlPage.getTotalElements());

        // Cria o Map com chaves únicas
        Map<String, Page<?>> response = new HashMap<>();
        response.put("moreViews", urlMoreViews); // Chave para URLs por palavra-chave

        return response;
    }

    private Map<String, Page<?>> lessViews(Page<DataForListingUrl> urlPage, Pageable pageable) {
        var urlDescendingList = urlPage.stream()
                .sorted(Comparator.comparing(DataForListingUrl::views).reversed())
                .collect(Collectors.toList());
        var urlLessViews = new PageImpl<>(urlDescendingList, pageable, urlPage.getTotalElements());

        // Cria o Map com chaves únicas
        Map<String, Page<?>> response = new HashMap<>();
        response.put("lessViews", urlLessViews); // Chave para URLs por palavra-chave

        return response;
    }


}
