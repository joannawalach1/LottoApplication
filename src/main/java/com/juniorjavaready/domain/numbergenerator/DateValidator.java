package com.juniorjavaready.domain.numbergenerator;

import java.time.OffsetDateTime;

public class DateValidator {
    public void validateDrawDate(OffsetDateTime drawDate) throws InvalidWinningNumbersException {
        if (drawDate.getMonthValue() < 1 || drawDate.getMonthValue() > 12 || drawDate.getDayOfMonth() > drawDate.toLocalDate().lengthOfMonth()) {
            throw new InvalidWinningNumbersException("Podana data losowania jest nieprawidłowa: "
                    + (drawDate.getMonthValue() < 1 || drawDate.getMonthValue() > 12
                    ? "miesiąc poza zakresem."
                    : "dzień poza zakresem w danym miesiącu."));
        }
    }
}
