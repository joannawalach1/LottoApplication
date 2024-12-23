package com.juniorjavaready.infrastructure.numbergenerator.http;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class WinningNumbersFetcher {
    private final RestTemplate restTemplate;

    public WinningNumbersFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        String apiUrl = "http://www.randomnumberapi.com:80/api/v1.0/random?min=1&max=99&count=6";
        fetchDataFromApi(apiUrl);
    }

    public String fetchDataFromApi(String apiUrl) {
        apiUrl = "http://www.randomnumberapi.com:80/api/v1.0/random?min=1&max=99&count=6";
        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            log.error("Błąd podczas pobierania danych z API: {}", e.getMessage());
            return null;
        }
    }
}


