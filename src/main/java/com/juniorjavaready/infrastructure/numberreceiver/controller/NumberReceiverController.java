package com.juniorjavaready.infrastructure.numberreceiver.controller;

import com.juniorjavaready.domain.numberreceiver.dto.InputNumberRequestDto;
import com.juniorjavaready.domain.numberreceiver.dto.NumberReceiverResponseDto;
import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/inputNumbers")
public class NumberReceiverController {
    private static int ticketCounter = 0;
    @PostMapping
    public ResponseEntity<NumberReceiverResponseDto> inputNumbers(@RequestBody InputNumberRequestDto inputNumbers) {
        String ticketId = "TICKET_" + (++ticketCounter);
        NumberReceiverResponseDto build = NumberReceiverResponseDto.builder()
                .ticketDto(TicketDto.builder()
                        .ticketId(ticketId)
                        .drawDate(LocalDateTime.now().plusDays(3))
                        .numbersFromUser(inputNumbers.numbers())
                        .message("Ticket received successfully")
                        .build())
                .build();
        return ResponseEntity.ok(build);
    }
}
