package com.juniorjavaready.domain.numbergenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@RequiredArgsConstructor
public class WinningNumbersFetcher {

    private final RestTemplate restTemplate;
    private String apiUrl;

    public Set<Integer> getWinningNumbers() {
        String url = apiUrl + "/winning-numbers";
        WinningNumbersResponse response = restTemplate.getForObject(url, WinningNumbersResponse.class);
        return response.winningNumbers();
    }
}
