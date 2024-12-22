package com.juniorjavaready.domain.numbergenerator;

import java.time.LocalDateTime;

public record WinningNumbers(LocalDateTime date, SixRandomNumbersDto winningNumbers) {
}
