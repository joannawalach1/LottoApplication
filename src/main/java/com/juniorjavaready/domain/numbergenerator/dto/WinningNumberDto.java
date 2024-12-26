package com.juniorjavaready.domain.numbergenerator.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
@Builder
public record WinningNumberDto(LocalDateTime dateTime, Set<Integer> winningNumber) {
}
