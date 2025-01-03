package com.juniorjavaready.domain.numberreceiver;

import com.juniorjavaready.domain.numbergenerator.InvalidWinningNumbersException;
import com.juniorjavaready.domain.numberreceiver.dto.InputNumberResultDto;
import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class NumberReceiverFacade {
    private static final LocalTime DRAW_DATE = LocalTime.of(12, 0, 0);
    private final NumberValidator numberValidator;
    private final DrawDateGenerator drawDateGenerator;
    private final HashGenerable hashGenerator;
    private final TicketRepository ticketRepository;

    public InputNumberResultDto inputNumbers(Set<Integer> numbers) throws InvalidWinningNumbersException {
        if (numbers == null) {
            throw new InvalidWinningNumbersException("Numbers cannot be null.");
        }
        if (!numberValidator.validateNumbers(numbers)) {
            throw new InvalidWinningNumbersException("Invalid numbers provided.");
        }
        LocalDateTime drawDate = drawDateGenerator.generateDrawDate();
        String hash = hashGenerator.generateHash(numbers);
        Ticket savedTicket = ticketRepository.save(new Ticket(hash, drawDate, numbers));
        return InputNumberResultDto.builder()
                .hash(hash)
                .drawDate(savedTicket.drawDate())
                .userNumbers(numbers)
                .build();
    }

    public List<TicketDto> userNumbers(LocalDateTime drawDate) {
        List<Ticket> allTicketsByDrawDate = ticketRepository.findAllTicketsByDrawDate(drawDate);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::toDto)
                .toList();
    }

    public LocalDateTime getNextDrawDate() {
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime nextSaturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        return nextSaturday.withHour(12).withMinute(0).withSecond(0).withNano(0);
    }


}


