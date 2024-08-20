package com.myproject.CortaCaminho.controller;

import com.myproject.CortaCaminho.domain.Search.*;
import com.myproject.CortaCaminho.domain.Profile.ProfileRepository;
import com.myproject.CortaCaminho.domain.Url.UrlRepository;
import com.myproject.CortaCaminho.domain.Url.Services.ViewCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ViewCounterService view;
    @Autowired
    private FilterSearchService filterService;

    // Retornar url pelo ID


    // busca de url por titulo ou palavra chave, ou nome do perfil
    @GetMapping("/{keyword}/{filter}")
    public ResponseEntity<Map<String, Page<?>>> searchByTitleOrLoginOrKeyword(
            @PathVariable("keyword") String keyword,
            @PathVariable(value = "filter", required = false) String filterParam,
            Pageable pageable){
        // Converte o parâmetro filter para a enumeração Filter
        Filter filter = Filter.fromString(filterParam);

        // Buscar todos os resultados
        var urlPage = urlRepository.findAllByTitle(keyword, pageable)
                .map(url -> new DataForListingUrl(url.getTitle(), url.getViews(), url.getImg(), url.getUser().getProfile().getName()));
        var profilePage = profileRepository.findAllByLogin(keyword, pageable).map(profile -> new DataForListingProfile(profile.getName()));
        var keywordPage = urlRepository.findAllByKeyword(keyword, pageable).map(url -> new DataForListingUrl(url.getTitle(), url.getViews(), url.getImg(), url.getUser().getProfile().getName()));

        System.out.println("Filter received: " + filter);

        if (filter == null || filter == Filter.FALSE) {
            Map<String, Page<?>> response = new HashMap<>();
            response.put("urls", urlPage);
            response.put("profiles", profilePage);
            response.put("urls", keywordPage);

            return ResponseEntity.ok(response);
        }else{

            return ResponseEntity.ok(filterService.toFilterResponse(urlPage, profilePage, keywordPage, filter, pageable));
        }


    }

    




}
