package com.myproject.CortaCaminho.domain.Url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UrlExpirationChecker {

    @Autowired
    private UrlRepository urlRepository;

    @Scheduled(cron = "0 0 * * * *") // Executa a cada uma hora
    public void checkForExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Scheduled task running at: " + now);

        List<Url> expiredUrls = urlRepository.findExpiredUrls(now);

        System.out.println("Found " + expiredUrls.size() + " expired URLs");

        for (Url url : expiredUrls) {
            System.out.println("Processing URL ID: " + url.getId() + ", current status: " + url.getStatus());

            if (url.getStatus() == Status.ATIVO) {
                url.setStatus(Status.DESATIVADO);
            } else {
                url.setStatus(Status.ATIVO);
            }

            url.setStatusTime(null);
            url.setScheduledStatus(false);
            urlRepository.save(url);

            System.out.println("Updated URL ID: " + url.getId() + " to status: " + url.getStatus());
        }
    }

}
