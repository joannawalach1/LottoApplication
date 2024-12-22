package com.juniorjavaready.domain.numberreceiver;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record SixNumbers(UUID hash, Set<Integer> numberFromUser) {
}
