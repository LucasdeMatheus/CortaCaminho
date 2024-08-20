package com.myproject.CortaCaminho.controller;

import com.myproject.CortaCaminho.domain.Url.*;
import com.myproject.CortaCaminho.domain.Url.Datas.*;
import com.myproject.CortaCaminho.domain.Url.Services.OperationsService;
import com.myproject.CortaCaminho.domain.Url.Services.UrlShortenerService;
import com.myproject.CortaCaminho.domain.user.User;
import com.myproject.CortaCaminho.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{id}/urls")
@SecurityRequirement(name = "bearer-key")
public class UrlController {

    @Autowired
    private UrlShortenerService urlShortenerService;
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;
    private User user = new User();

    @Autowired
    private OperationsService operationsService;

    // salvar e encurtar
    @PostMapping("/save")
    public ResponseEntity<?> saveUrl(@RequestBody @Valid DataUrl data, @PathVariable("id") Long userId){
        return operationsService.saveUrl(data, userId);
    }

    // deletar direto do banco de dados
    @Transactional
    @DeleteMapping("delete")
    public ResponseEntity deleteUrl(@RequestBody @Valid DataId data, @PathVariable("id") Long userId) {
        if (data.id().size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista vazia.");
        }else{
            return operationsService.deleteUrls(data.id(), userId);
        }
    }

    // editar Url, Url encurtada e Titulo
    @PutMapping("edit/{url_id}")
    public ResponseEntity<?> editUrl(@RequestBody @Valid DataUpdateUrl data, @PathVariable("url_id") Long urlId, @PathVariable("id") Long userId) {
        return operationsService.editUrl(data, urlId, userId);
    }

    // Defenir Status Url temporariamente com um padrão de 1 mês ou permanente
    @PutMapping("update-status")
    public ResponseEntity upadateStatusUrl(@RequestBody @Valid List<DataStatus> data, @PathVariable("id") Long userId){
        return operationsService.upadateStatusUrl(userId, data);
    }

    // Adicionar palavras-chavhes a urls
    @PostMapping("add-keywords/{url_id}")
    public ResponseEntity addKeywords(@PathVariable("url_id") Long urlId, @RequestBody @Valid DataKeyword data, @PathVariable("id") Long userId) {
        return operationsService.addKeywords(urlId, data, userId);
        }



}
