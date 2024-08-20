package com.myproject.CortaCaminho.controller;


import com.myproject.CortaCaminho.domain.Profile.Profile;
import com.myproject.CortaCaminho.domain.Profile.ProfileRepository;
import com.myproject.CortaCaminho.domain.Url.Url;
import com.myproject.CortaCaminho.domain.Url.UrlRepository;
import com.myproject.CortaCaminho.domain.Url.Services.ViewCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/redirect")
public class RedirectController {
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ViewCounterService view;

    @GetMapping("/url/{urlId}")
    public ResponseEntity getUrlById(@PathVariable("urlId") Long urlId){
        // Encontrar a URL pelo ID
        Optional<Url> optionalUrl = urlRepository.findById(urlId);

        // Verificar se a URL foi encontrada
        if (optionalUrl.isPresent()) {
            // contar view
            view.addViews(optionalUrl.get());

            urlRepository.save(optionalUrl.get());

            // Retornar a URL encontrada
            return ResponseEntity.ok(optionalUrl.get().toString());
        } else {

            // Retornar 404 se a URL não for encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Url não encontrada");
        }
    }

    @GetMapping("/profile/{id_profile}")
    public ResponseEntity getProfileById(@PathVariable("id_profile") Long profileId){
        // Encontrar o Profile pelo ID
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);

        // Verificar se a URL foi encontrada
        if (optionalProfile.isPresent()) {
            // Retornar a URL encontrada
            return ResponseEntity.ok(optionalProfile.get().toString());
        } else {
            // Retornar 404 se a URL não for encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile não encontrado");
        }
    }
}
