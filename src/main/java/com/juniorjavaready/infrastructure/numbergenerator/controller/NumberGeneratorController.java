package com.juniorjavaready.infrastructure.numbergenerator.controller;

import com.juniorjavaready.domain.numbergenerator.WinningNumberMapper;
import com.juniorjavaready.domain.numbergenerator.WinningNumberRepository;
import com.juniorjavaready.domain.numbergenerator.WinningNumbers;
import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/winningNumbers")
public class NumberGeneratorController {

    private final WinningNumberRepository winningNumbersRepository;
    private WinningNumbers winningNumbersDocument;

    @GetMapping
    public ResponseEntity<List<WinningNumberDto>> getWinningNumbersByDrawDate(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime drawDate) {

        Optional<List<WinningNumbers>> winningNumbers;

        if (drawDate == null) {
            winningNumbers = winningNumbersRepository.findWinningNumbersByDate(drawDate);
        } else {
            winningNumbers = Optional.ofNullable(winningNumbersRepository.findWinningNumbersByDate(drawDate)
                    .orElseThrow(() -> new IllegalArgumentException("No winning numbers found for the given date")));
        }

        List<WinningNumberDto> winningNumberDtos = winningNumbers.stream()
                .flatMap(winningNumbersEntry -> WinningNumberMapper.toDto(winningNumbersDocument).stream())
                .toList();

        return ResponseEntity.ok(winningNumberDtos);
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<WinningNumbers>> getAllWinningNumbers() {
        List<WinningNumbers> winningNumbers = winningNumbersRepository.findAll();
        return ResponseEntity.ok(winningNumbers);
    }
}
