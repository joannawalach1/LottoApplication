package com.juniorjavaready.domain.numberreceiver;

import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;

public class TicketMapper {
    public static TicketDto toDto(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.hash())
                .numbersFromUser(ticket.numbersFromUser())
                .drawDate(ticket.drawDate())
                .build();
    }
}
