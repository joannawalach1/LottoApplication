package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NumberGeneratorFacadeTest {

    WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl();
    WinningNumberRepository inMemoryNumberGeneratorRepository = new InMemoryNumberGeneratorRepository();
    DateValidator dateValidator = new DateValidator();
    NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(
            generator,
            winningNumberValidator,
            inMemoryNumberGeneratorRepository,
            numberReceiverFacade,
            dateValidator
    );


    @Test
    public void it_should_return_set_of_required_size() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl();
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);
        WinningNumberDto generatedNumbers = numbersGenerator.generateWinningNumbers();
        assertEquals(generatedNumbers.winningNumber().size(), 6);
    }

    @Test
    public void it_should_throw_exception_when_number_is_out_of_range() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 550));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);
        assertThrows(InvalidWinningNumbersException.class, numbersGenerator::generateWinningNumbers);
        
    }

    @Test
    public void it_should_throw_an_exception_when_number_not_in_range() {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(0, -1, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);
        assertThrows(InvalidWinningNumbersException.class, numbersGenerator::generateWinningNumbers);
    }

    @Test
    public void it_should_return_collection_of_unique_values() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);
        WinningNumberDto generatedNumbers = numbersGenerator.generateWinningNumbers();
        assertEquals(generatedNumbers.winningNumber().size(), Set.copyOf(generatedNumbers.winningNumber()).size());
    }

    @Test
    public void it_should_return_winning_numbers_by_given_date() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        LocalDateTime drawDate = LocalDateTime.now();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);
        WinningNumberDto winningNumbers = numbersGenerator.generateWinningNumbers();
        assertEquals(Set.of(1, 2, 3, 4, 5, 6), winningNumbers.winningNumber());
    }

//    @Test
//    public void it_should_throw_an_exception_when_fail_to_retrieve_numbers_by_given_date() throws InvalidWinningNumbersException {
//        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
//        OffsetDateTime invalidDate = OffsetDateTime.of(2024, 12, 31, 19, 54, 46, 0, OffsetDateTime.now().getOffset());
//        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);
//
//        assertThrows(InvalidWinningNumbersException.class, () -> numberGeneratorFacade.findWinningNumbersByDate(invalidDate));
//    }


    @Test
    public void it_should_return_true_if_numbers_are_generated_by_given_date() throws InvalidWinningNumbersException {
        WinningNumberGeneratorTestImpl generator = new WinningNumberGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 6));
        WinningNumberDto winningNumberDto = new WinningNumberDto(
                OffsetDateTime.of(2024, 12, 23, 19, 54, 46, 0, OffsetDateTime.now().getOffset()),
                Set.of(6, 5, 4, 3, 2, 1)
        );

        InMemoryNumberGeneratorRepository winningNumbersRepository = new InMemoryNumberGeneratorRepository();
        LocalDateTime drawDate = LocalDateTime.now();
        when(numberReceiverFacade.getNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade numbersGenerator = new NumberGeneratorFacade(generator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, dateValidator);

        numbersGenerator.generateWinningNumbers();
        WinningNumberDto isGenerated = numbersGenerator.generateWinningNumbers();

        OffsetDateTime expectedDateTime = winningNumberDto.dateTime()
                .withNano(0);
        OffsetDateTime actualDateTime = isGenerated.dateTime()
                .withNano(0);

    }
}