package com.juniorjavaready.domain.resultchecker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.juniorjavaready.domain.numbergenerator.NumberGeneratorFacade;
import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.domain.numberreceiver.dto.TicketDto;
import com.juniorjavaready.domain.resultchecker.dto.PlayerDto;
import com.juniorjavaready.domain.resultchecker.dto.ResultDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ResultCheckerFacadeTest {

    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();
    private final NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);

    @Test
    public void it_should_generate_all_players_with_correct_message() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        when(numberGeneratorFacade.generateAndReturnWinningNumbers()).thenReturn(WinningNumberDto.builder()
                .winningNumber(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(List.of(
                        TicketDto.builder()
                                .ticketId("001")
                                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("002")
                                .numbersFromUser(Set.of(1, 2, 7, 8, 9, 10))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("003")
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 12))
                                .drawDate(drawDate)
                                .build()
                )
        );

        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacadeConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository);
        //when
        PlayerDto playersDto = resultCheckerFacade.generateResults();
        //then
        List<ResultDto> results = playersDto.results();
        ResultDto resultDto = ResultDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .wonNumbers(Set.of(1,2,3,4,5,6))
                .build();
        ResultDto resultDto1 = ResultDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2))
                .drawDate(drawDate)
                .isWinner(true)
                .wonNumbers(Set.of(1,2))
                .build();
        ResultDto resultDto2 = ResultDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(true)
                .wonNumbers(Set.of())
                .build();
        assertThat(results).containsAnyOf(resultDto, resultDto1, resultDto2);
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners succeeded to retrieve");
    }

    @Test
    public void it_should_generate_fail_message_when_winningNumbers_equal_null() {
        //given
        when(numberGeneratorFacade.generateAndReturnWinningNumbers()).thenReturn(WinningNumberDto.builder()
                .winningNumber(null)
                .build());
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacadeConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository);
        //when
        PlayerDto playersDto = resultCheckerFacade.generateResults();
        //then
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");

    }

    @Test
    public void it_should_generate_fail_message_when_winningNumbers_is_empty() {
        //given
        when(numberGeneratorFacade.generateAndReturnWinningNumbers()).thenReturn(WinningNumberDto.builder()
                .winningNumber(Set.of())
                .build());
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacadeConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository);
        //when
        PlayerDto playersDto = resultCheckerFacade.generateResults();
        //then
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");

    }

    @Test
    public void it_should_generate_result_with_correct_credentials() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        Set<Integer> winningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        when(numberGeneratorFacade.generateAndReturnWinningNumbers()).thenReturn(WinningNumberDto.builder()
                .winningNumber(winningNumbers)
                .build());
        String hash = "001";
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .ticketId("001")
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 12))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("002")
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 13))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("003")
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 14))
                                .drawDate(drawDate)
                                .build())
        );
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacadeConfiguration().resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository);
        resultCheckerFacade.generateResults();
        //when

        ResultDto resultDto = resultCheckerFacade.findByHash(hash);
        //then
        ResultDto expectedResult = ResultDto.builder()
                .hash(hash)
                .numbers(Set.of(7,8,9,10,11,12))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .wonNumbers(winningNumbers)
                .isWinner(true)
                .build();
        assertThat(resultDto).isEqualTo(expectedResult);
    }
}