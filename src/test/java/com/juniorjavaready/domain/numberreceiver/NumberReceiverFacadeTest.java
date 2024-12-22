package com.juniorjavaready.domain.numberreceiver;

import com.juniorjavaready.domain.numberreceiver.dto.InputNumberResultDto;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class NumberReceiverFacadeTest {

    //private InMemoryNumberReceiverRepositoryImpl inMemoryNumberReceiverRepository;
    InMemoryTicketRepositoryImpl ticketRepository = new InMemoryTicketRepositoryImpl();
    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new DrawDateGenerator(),
            new HashGenerator(),
            ticketRepository
    );


    @Test
    public void shouldReturnSixNumbersListIfUserGaveSixNumbers() {
        //given
        Set<Integer> numberFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numberFromUser);
        //then
        assertEquals(result.userNumbers(), numberFromUser);
    }

    @Test
    public void shouldThrowExceptionIfUserGivesLessThanSixNumbers() {
        //given
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            numberReceiverFacade.inputNumbers(sixNumbers);
        });
    }

    @Test
    public void shouldThrowExceptionIfUserGivesMoreThanSixNumbers() {
        //given
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            numberReceiverFacade.inputNumbers(sixNumbers);
        });
    }

    @Test
    public void shouldThrowExceptionIfUserGivesEmptyList() {
        //given
        Set<Integer> sixNumbers = Set.of();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            numberReceiverFacade.inputNumbers(sixNumbers);
        });
    }

    @Test
    public void shouldThrowExceptionIfAnyOfNumberIsOutOfRange() {
        //given
        Set<Integer> sixNumbers = Set.of(1, 2, 3000, 4, 5);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            numberReceiverFacade.inputNumbers(sixNumbers);
        });
    }

    @Test
    public void shouldGenerateValidUUIDFormat() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        String hash = hashGenerator.generateHash(sixNumbers);
        //then
        assertDoesNotThrow(
                () -> UUID.fromString(hash),
                "Generated hash is not a valid UUID format"
        );
    }

    @Test
    public void shouldNotContainWhitespaceInHash() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        String hash = hashGenerator.generateHash(sixNumbers);
        //then
        assertFalse("ffff", hash.contains(" "));
    }

    @Test
    public void shouldGenerateHashMatchingUUIDPattern() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        String hash = hashGenerator.generateHash(sixNumbers);
        Pattern uuidPattern = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        //then
        assertTrue("Hash should match the UUID pattern", uuidPattern.matcher(hash).matches());
    }

    @Test
    public void shouldGenerateHashWithCorrectLength() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        String hash = hashGenerator.generateHash(sixNumbers);
        //then
        assertEquals("Hash should have a length of 36 characters", 36, hash.length());
    }

    @Test
    public void shouldGenerateCorrectDrawDateInTheFuture() {
        //given
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        //then
        LocalDateTime drawDate = drawDateGenerator.generateDrawDate();
        assertNotNull("Draw date should not be null", drawDate);
        ChronoLocalDateTime<?> currentDate = LocalDateTime.now();
        assertTrue("Draw date should be in the future", drawDate.isAfter(currentDate));
    }

    @Test
    public void shouldSaveTicketWhenInputNumbersIsCalled() {
        //given
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        numberReceiverFacade.inputNumbers(sixNumbers);
        //then
        assertEquals("Ticket should be saved", 1, ticketRepository.findAllTickets().size());
        assertTrue("Ticket with given numbers should be saved", ticketRepository.findAllTickets().stream()
                .anyMatch(ticket -> ticket.numbersFromUser().equals(sixNumbers))
        );
    }

    @Test
    public void shouldSaveTicket() {
        //given
        String hash = "123e4567-e89b-12d3-a456-426614174000";
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);
        LocalDateTime drawDate = LocalDateTime.of(2024, 12, 28, 12, 0, 0);
        Ticket ticket = new Ticket(hash, drawDate, numbers);
        //when
        Ticket savedTicket = ticketRepository.save(ticket);
        //then
        assertEquals(ticket.hash(), savedTicket.hash());
        assertEquals("Draw date should match", ticket.drawDate(), savedTicket.drawDate());
        assertEquals("User numbers should match", ticket.numbersFromUser(), savedTicket.numbersFromUser());
    }

    @Test
    public void shouldReturnValidatedNumbersIfUserGaveCorrectedNumbers() {
        //given
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 6);
        NumberValidator numberValidator = new NumberValidator();
        //when
        boolean generatedBoolean = numberValidator.validateNumbers(sixNumbers);
        //then
        assertTrue("Number should be validated", generatedBoolean);
    }

    @Test
    public void shouldReturnMessageIfUserDidntGiveCorrectedNumbers() {
        //given
        Set<Integer> sixNumbers = Set.of(1, 2, 3, 4, 5, 3000);
        NumberValidator numberValidator = new NumberValidator();
        //when
        boolean generatedBoolean = numberValidator.validateNumbers(sixNumbers);
        //then
        assertFalse("Number cannot be validated", generatedBoolean);
    }
}









