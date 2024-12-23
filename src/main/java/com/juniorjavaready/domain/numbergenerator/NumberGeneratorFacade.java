package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.infrastructure.numbergenerator.http.WinningNumbersFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class NumberGeneratorFacade {

    private final WinningNumbersGenerator winningNumbersGenerator;
    private final WinningNumberValidator winningNumberValidator;
    private final WinningNumberRepository winningNumbersRepository;
    private final NumberReceiverFacade numberReceiverFacade;
    private final DateValidator dateValidator;


    public WinningNumberDto generateWinningNumbers() throws InvalidWinningNumbersException {
            LocalDateTime nextDrawDate = numberReceiverFacade.getNextDrawDate();
            Set<Integer> winningNumbers = winningNumbersGenerator.generateWinningNumbers();

            winningNumberValidator.validateWinningNumbers(winningNumbers);
            for (Integer number : winningNumbers) {
                if (number < 1 || number > 99) {
                    throw new InvalidWinningNumbersException(
                            "Winning number " + number + " is out of the allowed range (1 - 99)");
                }
            }

            WinningNumbers winningNumbersDocument = WinningNumbers.builder()
                    .winningNumbers(winningNumbers)
                    .date(nextDrawDate)
                    .build();

            WinningNumbers savedNumbers = winningNumbersRepository.generateWinningNumbers(winningNumbersDocument);

            return WinningNumberDto.builder()
                    .winningNumber(savedNumbers.winningNumbers())
                    .dateTime(savedNumbers.date().atOffset(ZoneOffset.ofHours(10)))
                    .build();
        }

    public Optional<List<WinningNumbers>> findWinningNumbersByDate(OffsetDateTime drawDate) throws InvalidWinningNumbersException {
        dateValidator.validateDrawDate(drawDate);
        return winningNumbersRepository.findWinningNumbersByDate(drawDate);
    }

    private List<Integer> parseWinningNumbers(String response) {
        return Arrays.asList(response.split(","))
                .stream()
                .map(Integer::parseInt)
                .toList();
    }
}



