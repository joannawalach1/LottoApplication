package com.juniorjavaready.domain.numberreceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NumberReceiverFacadeConfigurator {

    private HashGenerable hashGenerator;
    private TicketRepository ticketRepository;

    @Bean
    public NumberReceiverFacade numberReceiverFacade() {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        return new NumberReceiverFacade(numberValidator, drawDateGenerator, hashGenerator, ticketRepository);
    }
}
