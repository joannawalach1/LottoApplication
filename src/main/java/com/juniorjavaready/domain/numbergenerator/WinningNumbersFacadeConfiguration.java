package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;

import java.util.Set;

@Configuration
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
        return new NumberGeneratorFacade(winningNumbersGenerator, winningNumberValidator, numberReceiverFacade, winningNumberRepository);
    }
}
