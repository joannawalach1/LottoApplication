package com.juniorjavaready.infrastructure.numbergenerator.http;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Component
@Slf4j
public class WinningNumbersFetcher {
    private final RestTemplate restTemplate;

    public WinningNumbersFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String apiUrl = "https://www.randomnumberapi.com/api/v1.0/random?min=1&max=99&count=6";

    public Set<Integer> fetchWinningNumbers() {
        log.info("Sending request to fetch winning numbers from API: {}", apiUrl);

        try {
            ResponseEntity<Set<Integer>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Set<Integer>>() {}
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Failed to fetch winning numbers. Status code: {}", response.getStatusCode());
                throw new IllegalStateException("Failed to fetch winning numbers from API. Status: " + response.getStatusCode());
            }

            log.info("Received winning numbers from API: {}", response.getBody());

            if (response.getBody() == null || response.getBody().isEmpty()) {
                log.error("Received empty body from API");
                throw new IllegalStateException("Received empty response body from API");
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("Error fetching winning numbers from API: ", e);
            throw new IllegalStateException("Error fetching winning numbers from API", e);
        }
    }
}


