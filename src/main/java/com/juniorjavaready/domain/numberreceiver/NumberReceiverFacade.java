package com.juniorjavaready.domain.numberreceiver;

public class NumberReceiverFacade {
    public void inputNumbers(SixNumbers sixNumbers) {
        SixNumbers newNumbers = SixNumbers.builder()
                .hash(sixNumbers.hash())
                .numberFromUser(sixNumbers.numberFromUser())
                .build();
    }
}
