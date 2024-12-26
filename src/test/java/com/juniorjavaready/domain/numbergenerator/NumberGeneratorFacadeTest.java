package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NumberGeneratorFacadeTest {
    WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl();
    WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    WinningNumberRepository inMemoryNumberGeneratorRepository = new InMemoryNumberGeneratorRepository();
    NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(
            generator,
            winningNumberValidator,
            inMemoryNumberGeneratorRepository,
            numberReceiverFacade
    );


    @Test
    public void it_should_return_set_of_required_size() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.of(2024,12,28,12,0,0));
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
        WinningNumberDto generatedNumbers = numbersGenerator.generateAndReturnWinningNumbers();
        assertEquals(generatedNumbers.winningNumber().size(), 6);
    }

    @Test
    public void it_should_throw_exception_when_number_is_out_of_range() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 550));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.of(2024,12,28,12,0,0));
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
        WinningNumberDto generatedNumbers = numbersGenerator.generateAndReturnWinningNumbers();
    }

    @Test
    public void it_should_throw_an_exception_when_number_not_in_range() {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(0, -1, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
        WinningNumberDto winningNumberDto = numbersGenerator.generateAndReturnWinningNumbers();
    }

    @Test
    public void it_should_return_collection_of_unique_values() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.of(2024,12,28,12,0,0));

        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
        WinningNumberDto generatedNumbers = numbersGenerator.generateAndReturnWinningNumbers();
        assertEquals(generatedNumbers.winningNumber().size(), Set.copyOf(generatedNumbers.winningNumber()).size());
    }

    @Test
    public void it_should_return_winning_numbers_by_given_date() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        LocalDateTime drawDate = LocalDateTime.now();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
        WinningNumberDto winningNumbers = numbersGenerator.generateAndReturnWinningNumbers();
        winningNumbersRepository.saveWinningNumbers(new WinningNumbers(drawDate, Set.of(1, 2, 3, 4, 5, 6)));
        Optional<List<WinningNumbers>> winningNumbersByDate = numbersGenerator.findWinningNumbersByDate(drawDate);
        assertTrue(winningNumbersByDate.isPresent());
        assertEquals(Set.of(1, 2, 3, 4, 5, 6), winningNumbersByDate.get().get(0).winningNumbers());
    }


    @Test
    public void it_should_throw_an_exception_when_fail_to_retrieve_numbers_by_given_date() {
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        LocalDateTime invalidDate = LocalDateTime.now().plusDays(1);
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
        assertThrows(InvalidWinningNumbersException.class, () -> numbersGenerator.findWinningNumbersByDate(invalidDate));
    }

//    @Test
//    public void it_should_return_true_if_numbers_are_generated_by_given_date() throws InvalidWinningNumbersException {
//        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
//        WinningNumberDto expectedWinningNumberDto = new WinningNumberDto(
//                LocalDateTime.now().withSecond(0).withNano(0),
//                Set.of(6, 5, 4, 3, 2, 1)
//        );
//        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
//        LocalDateTime drawDate = LocalDateTime.now().withSecond(0).withNano(0);
//        when(numberReceiverFacade.getNextDrawDate()).thenReturn(drawDate);
//        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(
//                generator,
//                winningNumberValidator,
//                winningNumbersRepository,
//                numberReceiverFacade
//        );
//        WinningNumberDto generatedWinningNumberDto = numbersGenerator.generateAndReturnWinningNumbers();
//        assertEquals(expectedWinningNumberDto.winningNumber(), generatedWinningNumberDto.winningNumber());
//    }
//

}