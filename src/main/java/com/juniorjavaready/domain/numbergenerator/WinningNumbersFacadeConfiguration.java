package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.infrastructure.numbergenerator.http.WinningNumbersFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class WinningNumbersFacadeConfiguration {


    private NumberReceiverFacade numberReceiverFacade;
    private WinningNumbersFetcher winningNumberFetcher;

    @Bean
    public WinningNumberRepository winningNumberRepository() {
        return new WinningNumberRepository() {

            @Override
            public WinningNumbers saveWinningNumbers(WinningNumbers winningNumbers) {
                return WinningNumbers.builder()
                        .winningNumbers(winningNumbers.winningNumbers())
                        .date(winningNumbers.date())
                        .build();
            }

            @Override
            public Optional<List<WinningNumbers>> findWinningNumbersByDate(LocalDateTime drawDate) {
                return Optional.empty();
            }

            @Override
            public List<WinningNumbers> findAll() {
                return List.of();
            }
        };
    }

    @Bean
    public WinningNumbersGenerator winningNumbersGenerator() {
        return new WinningNumbersGenerator() {
            @Override
            public Set<Integer> generateWinningNumbers() {
                return Set.of();
            }
        };
    }

    @Bean
    public NumberGeneratorFacade numberGeneratorFacade(WinningNumbersGenerator winningNumbersGenerator) {
        WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
        DateValidator dateValidator = new DateValidator();
        return new NumberGeneratorFacade(winningNumbersGenerator, winningNumberValidator, winningNumberRepository(), numberReceiverFacade);
    }
}
