package com.juniorjavaready.domain.numbergenerator.dto;

import lombok.Builder;

import java.util.Set;
@Builder
public record SixRandomNumbersDto(Set<Integer> numbers) {
}
