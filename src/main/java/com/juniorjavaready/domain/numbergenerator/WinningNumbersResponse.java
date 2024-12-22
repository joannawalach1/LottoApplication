package com.juniorjavaready.domain.numbergenerator;

import lombok.Builder;

import java.util.Set;
@Builder
public record WinningNumbersResponse(Set<Integer> winningNumbers) {
}
