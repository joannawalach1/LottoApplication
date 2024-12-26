package com.juniorjavaready.domain.resultchecker;

import com.juniorjavaready.domain.resultchecker.dto.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPlayerRepository implements PlayerRepository {
    Map<String, Player> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public List<Player> saveAll(List<Player> players) {
            if (players != null) {
                players.forEach(player -> {
                    if (!inMemoryDatabase.containsKey(player.hash())) {
                        inMemoryDatabase.put(player.hash(), player);
                    }
                });
            }
            return players;
        }

    @Override
    public Optional<Player> findById(String hash) {
        return Optional.empty();
    }
}
