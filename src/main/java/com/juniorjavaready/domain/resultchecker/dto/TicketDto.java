package com.juniorjavaready.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record TicketDto(String message, String ticketId, LocalDateTime drawDate, Set<Integer> numbersFromUser) {

}

