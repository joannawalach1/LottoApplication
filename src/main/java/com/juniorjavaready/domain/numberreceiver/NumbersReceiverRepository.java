package com.juniorjavaready.domain.numberreceiver;

import java.util.List;
import java.util.Set;

public interface NumbersReceiverRepository {
      List<Ticket> inputNumbers(Set<Integer> numbers);
}
