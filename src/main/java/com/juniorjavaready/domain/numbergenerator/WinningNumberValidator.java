package com.juniorjavaready.domain.numbergenerator;

import java.util.List;
import java.util.Set;

public class WinningNumberValidator {
    private static final Integer MINIMAL_RANGE = 1;
    private static final Integer MAXIMUM_RANGE = 99;
    private static final int MAX_NUMBERS_FROM_USER = 6;


    public void validateWinningNumbers(Set<Integer> numbers) {
        numbers.stream()
                .limit(MAX_NUMBERS_FROM_USER)
                .filter(number -> number >= MINIMAL_RANGE)
                .filter(number -> number <= MAXIMUM_RANGE);

    }
}
