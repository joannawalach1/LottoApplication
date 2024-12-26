package com.juniorjavaready.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PlayerDto(String message,
                        List<ResultDto> results) {

}
