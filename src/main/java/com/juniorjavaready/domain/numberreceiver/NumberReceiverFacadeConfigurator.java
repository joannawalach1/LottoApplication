package com.juniorjavaready.domain.numberreceiver;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberReceiverFacadeConfigurator {
    public NumberReceiverFacade numberReceiverFacade(HashGenerable hashGenerator, TicketRepository ticketRepository) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        return new NumberReceiverFacade(numberValidator, drawDateGenerator, hashGenerator, ticketRepository);
    }
}
