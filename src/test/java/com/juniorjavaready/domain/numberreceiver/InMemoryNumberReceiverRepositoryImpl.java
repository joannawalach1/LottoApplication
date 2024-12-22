package com.juniorjavaready.domain.numberreceiver;

import java.util.HashMap;
import java.util.Map;

public class InMemoryNumberReceiverRepositoryImpl extends NumbersReceiverRepository {
    Map<String, Ticket> inMemoryDatabase = new HashMap<>();



}
