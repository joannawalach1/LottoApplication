package com.juniorjavaready.domain.numbergenerator;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "winning_numbers")
@Builder
public record WinningNumbers(
        @Id
        LocalDateTime date,
        Set<Integer> winningNumbers) {
}
