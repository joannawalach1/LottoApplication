package com.juniorjavaready.domain.numbergenerator;

import java.util.Set;

public class WinningNumberValidator {
    private static final Integer MINIMAL_RANGE = 1;
    private static final Integer MAXIMUM_RANGE = 99;
    private static final int MAX_NUMBERS_FROM_USER = 6;


    public boolean validateWinningNumbers(Set<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number >= MINIMAL_RANGE)
                .filter(number -> number <= MAXIMUM_RANGE)
                .count() == MAX_NUMBERS_FROM_USER;
    }
}
