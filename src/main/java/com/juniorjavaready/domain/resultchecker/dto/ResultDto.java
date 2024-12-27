package com.juniorjavaready.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResultDto(String hash, Set<Integer> numbers, Set<Integer> hitNumbers, LocalDateTime drawDate, Set<Integer> wonNumbers, boolean isWinner) {
}
