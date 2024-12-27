package com.juniorjavaready.domain.resultchecker;

import com.juniorjavaready.domain.numbergenerator.NumberGeneratorFacade;
import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;
import com.juniorjavaready.domain.resultchecker.dto.Player;
import com.juniorjavaready.domain.resultchecker.dto.PlayerDto;
import com.juniorjavaready.domain.resultchecker.dto.ResultDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import static com.juniorjavaready.domain.resultchecker.ResultCheckerMapper.mapPlayersToResults;


@RequiredArgsConstructor
public class ResultCheckerFacade {
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final NumberReceiverFacade numberReceiverFacade;
    private final PlayerRepository playerRepository;
    private final WinnerRetriever winnerGenerator;

    public PlayerDto generateResults() {
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = ResultCheckerMapper.mapTicketsDtosToTickets(allTicketsByDate);
        WinningNumberDto winningNumbersDto = numberGeneratorFacade.generateAndReturnWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumber();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayerDto.builder()
                    .message("Winners failed to retrieve")
                    .build();
        }
        List<Player> players = winnerGenerator.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);
        return PlayerDto.builder()
                .results(mapPlayersToResults(players))
                .message("Winners succeeded to retrieve")
                .build();
    }

    public ResultDto findByHash(String hash) {
        Player player = playerRepository.findById(hash).orElseThrow(() -> new RuntimeException("Not found"));
        return ResultDto.builder()
                .hash(hash)
                .numbers(player.numbers())
                .hitNumbers(player.hitNumbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .wonNumbers(player.wonNumbers())
                .build();
    }
}
