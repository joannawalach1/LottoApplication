package com.juniorjavaready.domain.numbergenerator;

import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class WinningNumberMapper {
    public static WinningNumbers toEntity(List<WinningNumbers> winningNumbers) {
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            throw new IllegalArgumentException("Winning numbers list cannot be null or empty");
        }

        Set<Integer> allWinningNumbers = winningNumbers.stream()
                .flatMap(w -> w.winningNumbers().stream())
                .collect(Collectors.toSet());

        LocalDateTime drawDate = winningNumbers.get(0).date();

        return WinningNumbers.builder()
                .winningNumbers(allWinningNumbers)
                .date(drawDate)
                .build();
    }

    public static List<WinningNumberDto> toDto(WinningNumbers winningNumbers) {
        if (winningNumbers == null || winningNumbers.winningNumbers().isEmpty()) {
            throw new IllegalArgumentException("Winning numbers list cannot be null or empty");
        }

        Set<Integer> allWinningNumbers = winningNumbers.winningNumbers();

        OffsetDateTime drawDate = OffsetDateTime.from(winningNumbers.date());

        return allWinningNumbers.stream()
                .map(number -> WinningNumberDto.builder()
                        .winningNumber(winningNumbers.winningNumbers())
                        .dateTime(drawDate)
                        .build())
                .toList();
    }

}

