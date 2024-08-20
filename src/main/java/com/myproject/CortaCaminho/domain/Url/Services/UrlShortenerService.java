package com.myproject.CortaCaminho.domain.Url.Services;

import com.myproject.CortaCaminho.domain.Url.Url;
import com.myproject.CortaCaminho.domain.Url.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = BASE62.length();
    private static final String BASE_URL = "www.encurtaurl.com/";

    @Autowired
    private UrlRepository urlRepository;

    public String shortenURL(Url url){


        String uniqueId = encode(url.getId());
        String shortUrl = BASE_URL + url.getUser().getId() + "/" + uniqueId;

        return shortUrl;
    }
    private String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }
}
