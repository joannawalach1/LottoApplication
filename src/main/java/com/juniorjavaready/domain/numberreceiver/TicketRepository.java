package com.juniorjavaready.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository {
    Ticket save (Ticket ticket);
    List<Ticket> findAllTicketsByDrawDate(LocalDateTime date);
    List<Ticket> findAllTickets();
    Ticket findByHash(String hash);
}
