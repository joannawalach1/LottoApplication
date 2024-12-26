package com.juniorjavaready.domain.resultchecker;

import com.juniorjavaready.domain.numbergenerator.NumberGeneratorFacade;
import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;
import com.juniorjavaready.domain.resultchecker.dto.Player;
import com.juniorjavaready.domain.resultchecker.dto.PlayerDto;
import com.juniorjavaready.domain.resultchecker.dto.ResultDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.juniorjavaready.domain.resultchecker.ResultCheckerMapper.mapPlayersToResults;


@RequiredArgsConstructor
public class ResultCheckerFacade {
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final NumberReceiverFacade numberReceiverFacade;
    private final PlayerRepository playerRepository;
    private final WinnerRetriever winnerGenerator;

    public PlayerDto createWinners() {
        LocalDateTime drawDate = LocalDateTime.now();
        List<TicketDto> ticketDtos = numberReceiverFacade.userNumbers(drawDate);
        List<Ticket> tickets = ResultCheckerMapper.mapTicketsDtosToTickets(ticketDtos);

        WinningNumberDto winningNumbersDto = numberGeneratorFacade.generateAndReturnWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumber();

        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayerDto.builder()
                    .message("No winning numbers found")
                    .build();
        }

        List<Player> players = winnerGenerator.findWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);

        return PlayerDto.builder()
                .results(mapPlayersToResults(players))
                .message(players.size() + " winning numbers found")
                .build();
    }



    public ResultDto createResults() {
        LocalDateTime drawDate = LocalDateTime.now();
        return null;
    }


    public ResultDto findByHash(String hash) {
        return null;
    }
}
