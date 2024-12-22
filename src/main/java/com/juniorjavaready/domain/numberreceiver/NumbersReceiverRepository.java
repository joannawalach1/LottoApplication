package com.juniorjavaready.domain.numberreceiver;

import java.util.ArrayList;
import java.util.List;

class NumbersReceiverRepository {
    List<Integer> numbers = new ArrayList<>();
    public List<Integer> inputNumbers(int number) {
       numbers.add(number);
       return new ArrayList<>(numbers);
    }
}
