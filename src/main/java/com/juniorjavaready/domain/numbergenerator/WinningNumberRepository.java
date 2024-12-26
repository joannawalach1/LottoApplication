package com.juniorjavaready.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WinningNumberRepository {
    WinningNumbers saveWinningNumbers(WinningNumbers winningNumbers);
    Optional<List<WinningNumbers>> findWinningNumbersByDate(LocalDateTime drawDate);
    List<WinningNumbers> findAll();
}
