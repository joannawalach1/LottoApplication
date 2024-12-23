package com.juniorjavaready.domain.numbergenerator;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryNumberGeneratorRepository implements WinningNumberRepository {
    Map<LocalDateTime, WinningNumbers> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public WinningNumbers generateWinningNumbers(WinningNumbers winningNumbersDocument) {
            inMemoryDatabase.put(winningNumbersDocument.date(), winningNumbersDocument);
            return winningNumbersDocument;
        }

    @Override
    public Optional<List<WinningNumbers>> findWinningNumbersByDate(OffsetDateTime drawDate) {
        List<WinningNumbers> matchingNumbers = inMemoryDatabase.values().stream()
                .filter(winningNumbersDocument -> winningNumbersDocument.date().isEqual(drawDate.toLocalDateTime()))
                .collect(Collectors.toList());

        return matchingNumbers.isEmpty() ? Optional.empty() : Optional.of(matchingNumbers);
    }

    @Override
    public List<WinningNumbers> findAll() {
        return inMemoryDatabase.values().stream()
                .toList();
    }
}
