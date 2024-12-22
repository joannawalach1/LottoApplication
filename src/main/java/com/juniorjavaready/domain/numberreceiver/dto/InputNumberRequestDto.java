package com.juniorjavaready.domain.numberreceiver.dto;


import lombok.Builder;

import java.util.Set;
@Builder
public record InputNumberRequestDto(Set<Integer> inputNumbers) {
}