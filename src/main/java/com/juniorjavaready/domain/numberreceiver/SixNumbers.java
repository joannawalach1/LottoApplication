package com.juniorjavaready.domain.numberreceiver;

import lombok.Builder;

import java.util.List;
@Builder
public record SixNumbers(String hash, List<Integer> numberFromUser) {
}
