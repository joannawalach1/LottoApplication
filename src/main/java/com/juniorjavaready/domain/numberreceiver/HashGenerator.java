package com.juniorjavaready.domain.numberreceiver;

import java.util.Set;
import java.util.UUID;

public class HashGenerator implements HashGenerable {
        @Override
        public String generateHash(Set<Integer> numberFromUser) {
            return UUID.randomUUID().toString();
        }
    }

