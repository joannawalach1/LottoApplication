package com.juniorjavaready.domain.resultchecker;

import com.juniorjavaready.domain.numbergenerator.NumberGeneratorFacade;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import com.juniorjavaready.domain.resultchecker.dto.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ResultCheckerFacadeConfiguration {

    @Bean
    public PlayerRepository playerRepository() {
        return new PlayerRepository() {

            @Override
            public List<Player> saveAll(List<Player> players) {
                return players;
            }

            @Override
            public Optional<Player> findById(String hash) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberGeneratorFacade numberGeneratorFacade, NumberReceiverFacade numberReceiverFacade, PlayerRepository playerRepository) {
        WinnerRetriever winnerRetriever = new WinnerRetriever();
        return new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository, winnerRetriever);
    }
}
