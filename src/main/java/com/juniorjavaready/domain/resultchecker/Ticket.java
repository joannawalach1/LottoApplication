package com.juniorjavaready.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
@Builder
public record Ticket(String hash, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}
