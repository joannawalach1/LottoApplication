package com.juniorjavaready.domain.numberreceiver;

import java.util.Set;

public interface HashGenerable {
    String generateHash(Set<Integer> numberFromUser);
}
