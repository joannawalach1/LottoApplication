package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.infrastructure.numbergenerator.http.WinningNumbersFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class NumberGeneratorFacade {
    private static final Logger logger = LoggerFactory.getLogger(NumberGeneratorFacade.class);

    private final WinningNumbersGenerator generator;
    private final WinningNumberValidator winningNumberValidator;
    private final WinningNumberRepository winningNumbersRepository;
    private final NumberReceiverFacade numberReceiverFacade;

    public NumberGeneratorFacade(WinningNumbersGenerator generator, WinningNumberValidator winningNumberValidator, WinningNumberRepository winningNumbersRepository, NumberReceiverFacade numberReceiverFacade) {
        this.generator = generator;
        this.winningNumberValidator = winningNumberValidator;
        this.winningNumbersRepository = winningNumbersRepository;
        this.numberReceiverFacade = numberReceiverFacade;
    }


    public WinningNumberDto generateAndReturnWinningNumbers() throws InvalidWinningNumbersException {
        LocalDateTime nextDrawDate = numberReceiverFacade.getNextDrawDate();
        logger.info("NextDrawDate"+ nextDrawDate.toString());
        WinningNumbersFetcher fetcher = new WinningNumbersFetcher(new RestTemplate());
        Set<Integer> winningNumbers = fetcher.fetchWinningNumbers();
        winningNumberValidator.validateWinningNumbers(winningNumbers);
        winningNumbersRepository.saveWinningNumbers(
                WinningNumbers.builder()
                        .winningNumbers(winningNumbers)
                        .date(nextDrawDate)
                        .build());

        return WinningNumberDto.builder()
                .winningNumber(winningNumbers)
                .build();
    }

    public Optional<List<WinningNumbers>> findWinningNumbersByDate(LocalDateTime drawDate) throws InvalidWinningNumbersException {
        return Optional.ofNullable(winningNumbersRepository.findWinningNumbersByDate(drawDate)
                .orElseThrow(() -> new InvalidWinningNumbersException("Invalid winning numbers for date: " + drawDate)));
    }
    public List<WinningNumbers> findAll() {
        log.info("Finding all winning numbers", winningNumbersRepository.findAll() );
        return winningNumbersRepository.findAll();
    }
}



