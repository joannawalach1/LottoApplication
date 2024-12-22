package com.juniorjavaready.domain.numberreceiver;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DrawDateGenerator {
    public LocalDateTime generateDrawDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextSaturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        return nextSaturday.withHour(12).withMinute(0).withSecond(0).withNano(0);
    }

}
