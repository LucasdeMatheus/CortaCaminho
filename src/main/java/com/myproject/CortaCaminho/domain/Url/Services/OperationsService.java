package com.myproject.CortaCaminho.domain.Url.Services;

import com.myproject.CortaCaminho.domain.Url.Datas.DataKeyword;
import com.myproject.CortaCaminho.domain.Url.Datas.DataStatus;
import com.myproject.CortaCaminho.domain.Url.Datas.DataUpdateUrl;
import com.myproject.CortaCaminho.domain.Url.Datas.DataUrl;
import com.myproject.CortaCaminho.domain.Url.Status;
import com.myproject.CortaCaminho.domain.Url.Url;
import com.myproject.CortaCaminho.domain.Url.UrlRepository;
import com.myproject.CortaCaminho.domain.user.User;
import com.myproject.CortaCaminho.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationsService {
    @Autowired
    private UrlShortenerService urlShortenerService;
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;
    private User user = new User();

    public ResponseEntity<?> deleteUrls(List<Long> id, Long userId){
        for (int i = 0; i < id.size(); i++) {
            if (urlRepository.deleteByUrlIdAndUserId(id.get(i), userId) == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL não encontrada ou não pertence ao usuário.");
            }
        }
        return ResponseEntity.ok(true);
    }



    public ResponseEntity<?> editUrl(DataUpdateUrl data, Long urlId, Long userId){
        Url url = urlRepository.findByIdAndUserId(urlId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

        // Atualizando o ShortenedUrl se não for nulo e não existir outro igual
        if (data.ShortenedUrl() != null) {
            if (!urlRepository.existsByShortenedUrl(data.ShortenedUrl())) {
                url.setShortenedUrl(data.ShortenedUrl());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Código de URL encurtada já existente");
            }
        }

        // Atualizando a URL se não for nula e não existir outra igual
        if (data.url() != null) {
            if (!urlRepository.existsByUrl(data.url())) {
                url.setUrl(data.url());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("URL já existente");
            }
        }

        // Atualizando o título se não for nulo
        if (data.title() != null) {
            url.setTitle(data.title());
        }

        if (data.img() != null){
            url.setImg(data.img());
        }

        // Salvando as alterações
        urlRepository.save(url);

        return ResponseEntity.ok(true);
    }







    public ResponseEntity<?> upadateStatusUrl(Long userId, List<DataStatus> dataList) {
        for (DataStatus data : dataList) {
            Url url = urlRepository.findByIdAndUserId(data.idUrl(), userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

            // verifica se o status está programado para mudar
            if (!data.scheduledStatus()) {
                url.setStatus(data.status());
                url.setStatusTime(null);
                url.setScheduledStatus(false);
            } else {
                // verifica se o tempo programado foi definido
                if (data.time() == null) {
                    LocalDateTime datePlusOneMonth = LocalDateTime.now().plusMonths(1);
                    url.setStatusTime(datePlusOneMonth);
                } else {
                    url.setStatusTime(data.time());
                }
                url.setStatus(data.status());
                url.setScheduledStatus(data.scheduledStatus());
            }

            // Salva as alterações no banco
            urlRepository.save(url);
        }
        return ResponseEntity.ok(true);
    }






    public ResponseEntity<?> saveUrl(DataUrl data, Long userId) {
        if(urlRepository.existsByUserIdAndUrl(userId, data.url())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A URL encurtada já existe.");

        }
        Url url = new Url();
        url.setUrl(data.url());
        url.setTitle(data.titulo());
        url.setStatus(Status.ATIVO);

        // Busca o usuário pelo ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User não encontrado");
        }

        // usuario encontrado, agora atribuindo a url o usuario
        User user = optionalUser.get();
        url.setUser(user);
        urlRepository.save(url);

        // encurtando a url
        String shortenedUrl = urlShortenerService.shortenURL(url);
        url.setShortenedUrl(shortenedUrl);
        urlRepository.save(url);
        System.out.println(shortenedUrl);
        return ResponseEntity.ok(true);
    }








    public ResponseEntity addKeywords(Long urlId, DataKeyword data, Long userId) {
        Url url = urlRepository.findByIdAndUserId(urlId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

        String existingKeywords = url.getKeywords();
        String newKeywords = data.keywords();

        // Verifica se já há keywords existentes
        if (existingKeywords != null && !existingKeywords.isEmpty()) {
            // Adiciona uma vírgula antes de adicionar as novas keywords
            url.setKeywords(existingKeywords + ", " + newKeywords);
        } else {
            // Se não há keywords, simplesmente define as novas keywords
            url.setKeywords(newKeywords);
        }


        urlRepository.save(url);
        return ResponseEntity.ok(true);
    }
}
