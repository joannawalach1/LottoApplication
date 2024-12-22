package com.juniorjavaready.domain.numberreceiver;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryNumberReceiverRepositoryImpl implements NumbersReceiverRepository {
    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();


    @Override
    public List<Ticket> inputNumbers(Set<Integer> numbers) {
        return inMemoryDatabase.values()
                .stream()
                .toList();
    }
}
