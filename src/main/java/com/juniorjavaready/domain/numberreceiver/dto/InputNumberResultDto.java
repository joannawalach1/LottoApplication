package com.juniorjavaready.domain.numberreceiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record InputNumberResultDto(String hash, LocalDateTime drawDate, Set<Integer> userNumbers) {
}