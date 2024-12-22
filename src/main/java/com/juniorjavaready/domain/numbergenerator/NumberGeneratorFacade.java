package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
public class NumberGeneratorFacade {

    private final WinningNumbersGenerator winningNumbersGenerator;
    private final WinningNumberValidator winningNumberValidator;
    private final WinningNumberRepository winningNumbersRepository;
    private final NumberReceiverFacade numberReceiverFacade;

    public void generateWinningNumbers() {
        LocalDateTime nextDrawDate = numberReceiverFacade.getNextDrawDate();
        Set<Integer> winningNumbers = winningNumbersGenerator.generateWinningNumbers();
        if (!winningNumberValidator.validateWinningNumbers(winningNumbers)) {
            throw new IllegalArgumentException("Invalid winning numbers generated");
        }
        WinningNumbers winningNumbersDocument = WinningNumbers.builder()
                .winningNumbers(winningNumbers)
                .date(nextDrawDate)
                .build();
        winningNumbersRepository.generateWinningNumbers(winningNumbersDocument);

    }


}

