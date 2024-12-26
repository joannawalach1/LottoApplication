package com.juniorjavaready.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryNumberGeneratorRepository implements WinningNumberRepository {
    Map<LocalDateTime, WinningNumbers> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public WinningNumbers saveWinningNumbers(WinningNumbers winningNumbers) {
            inMemoryDatabase.put(winningNumbers.date(), winningNumbers);
            return winningNumbers;
        }

    @Override
    public Optional<List<WinningNumbers>> findWinningNumbersByDate(LocalDateTime drawDate) {
        List<WinningNumbers> matchingNumbers = inMemoryDatabase.values().stream()
                .filter(winningNumbersDocument -> winningNumbersDocument.date().isEqual(drawDate))
                .collect(Collectors.toList());

        return matchingNumbers.isEmpty() ? Optional.empty() : Optional.of(matchingNumbers);
    }

    @Override
    public List<WinningNumbers> findAll() {
        return new ArrayList<>(inMemoryDatabase.values());
    }
}
