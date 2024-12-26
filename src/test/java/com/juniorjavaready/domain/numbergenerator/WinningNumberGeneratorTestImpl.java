package com.juniorjavaready.domain.numbergenerator;

import java.util.Set;

public class WinningNumberGeneratorTestImpl implements WinningNumbersGenerator {

    private final Set<Integer> generatedNumbers;

    WinningNumberGeneratorTestImpl() {
        generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    WinningNumberGeneratorTestImpl(Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    public Set<Integer> generateWinningNumbers() {
        return generatedNumbers;
    }
}
