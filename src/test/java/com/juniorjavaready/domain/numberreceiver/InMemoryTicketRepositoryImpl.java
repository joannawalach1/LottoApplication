package com.juniorjavaready.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTicketRepositoryImpl implements TicketRepository {
    Map<String, Ticket> ticketsDatabase = new HashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        ticketsDatabase.put(ticket.hash(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllTicketsByDrawDate(LocalDateTime date) {
        return ticketsDatabase.values()
                .stream()
                .filter(ticket -> ticket.drawDate().isEqual(date))
                .toList();
    }

    @Override
    public List<Ticket> findAllTickets() {
        return ticketsDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public List<Ticket> findByHash(String hash) {
        return ticketsDatabase.values()
                .stream()
                .filter(ticket -> ticket.hash().equals(hash))
                .toList();
    }

}
