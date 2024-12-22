package com.juniorjavaready.domain.numberreceiver;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record SixNumbers(String hash, Set<Integer> numberFromUser) {
}
