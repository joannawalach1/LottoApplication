package com.juniorjavaready.domain.numbergenerator;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface WinningNumberRepository {
    WinningNumbers generateWinningNumbers(WinningNumbers winningNumbersDocument);
    Optional<List<WinningNumbers>> findWinningNumbersByDate(OffsetDateTime drawDate);
    List<WinningNumbers> findAll();
}
