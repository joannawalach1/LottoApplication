package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class WinningNumbersFacadeConfiguration {


    private NumberReceiverFacade numberReceiverFacade;
    private WinningNumberRepository winningNumberRepository;

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
        return new NumberGeneratorFacade(winningNumbersGenerator, winningNumberValidator, winningNumberRepository, numberReceiverFacade);
    }
}
