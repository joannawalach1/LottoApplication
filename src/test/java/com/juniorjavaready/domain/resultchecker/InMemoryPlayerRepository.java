package com.juniorjavaready.domain.resultchecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.juniorjavaready.domain.resultchecker.dto.Player;

public class InMemoryPlayerRepository implements PlayerRepository {

    private final Map<String, Player> playersList = new ConcurrentHashMap<>();

    @Override
    public List<Player> saveAll(List<Player> players) {
        players.forEach(player -> playersList.put(player.hash(), player));
        return players;
    }

    @Override
    public Optional<Player> findById(String hash) {
        Player player = playersList.get(hash);
        return Optional.ofNullable(player);
    }
}