package com.juniorjavaready.domain.numbergenerator.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Set;
@Builder
public record WinningNumberDto(OffsetDateTime dateTime, Set<Integer> winningNumber) {
}
