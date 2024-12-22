package com.juniorjavaready.domain.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record WinningNumbers(LocalDateTime date, Set<Integer> winningNumbers) {
}
