package com.juniorjavaready.infrastructure.numbergenerator.controller;

import com.juniorjavaready.domain.numbergenerator.*;
import com.juniorjavaready.domain.numbergenerator.dto.WinningNumberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/winningNumbers")
public class NumberGeneratorController {

    private final NumberGeneratorFacade numberGeneratorFacade;
    @GetMapping("/generate")
    public ResponseEntity<Set<Integer>> generateAndReturnWinningNumbers() throws InvalidWinningNumbersException {
            log.info("Calling generateWinningNumbers method.");
            WinningNumberDto winningNumbers = numberGeneratorFacade.generateAndReturnWinningNumbers();
            log.info("Winning numbers successfully generated: {}", winningNumbers);
            return ResponseEntity.ok(winningNumbers.winningNumber());
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<WinningNumbers>> getAllWinningNumbers() {
        List<WinningNumbers> winningNumbers = numberGeneratorFacade.findAll();
        log.info("WinningNumbers fetched: {}", winningNumbers);

        return ResponseEntity.ok(winningNumbers);
    }
}
