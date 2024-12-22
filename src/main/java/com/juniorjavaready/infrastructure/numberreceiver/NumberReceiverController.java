package com.juniorjavaready.infrastructure.numberreceiver;

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
    @PostMapping
    public ResponseEntity<NumberReceiverResponseDto> inputNumbers(@RequestBody InputNumberRequestDto inputNumbers) {
        NumberReceiverResponseDto build = NumberReceiverResponseDto.builder()
                .ticketDto(TicketDto.builder()
                        .drawDate(LocalDateTime.of(2023, 12, 25, 12, 0, 0))
                        .build())
                .build();
        return ResponseEntity.ok(build);
    }
}
