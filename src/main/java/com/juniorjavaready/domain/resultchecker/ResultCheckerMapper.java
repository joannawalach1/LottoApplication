package com.juniorjavaready.domain.resultchecker;

import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;
import com.juniorjavaready.domain.resultchecker.dto.Player;
import com.juniorjavaready.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.stream.Collectors;

public class ResultCheckerMapper {
    public static List<ResultDto> mapPlayersToResults(List<Player> players) {
        return players.stream()
                .map(player -> ResultDto.builder()
                        .hash(player.hash())
                        .numbers(player.numbers())
                        .hitNumbers(player.hitNumbers())
                        .drawDate(player.drawDate())
                        .isWinner(player.isWinner())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<Ticket> mapTicketsDtosToTickets(List<TicketDto> allTicketsByDate) {
        return allTicketsByDate.stream()
                .map(ticketDto -> Ticket.builder()
                        .hash(ticketDto.ticketId())
                        .drawDate(ticketDto.drawDate())
                        .numbersFromUser(ticketDto.numbersFromUser())
                        .build())
                .collect(Collectors.toList());
    }

}
