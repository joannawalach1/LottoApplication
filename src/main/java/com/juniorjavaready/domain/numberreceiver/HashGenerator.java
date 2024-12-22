package com.juniorjavaready.domain.numberreceiver;

import java.util.Set;
import java.util.UUID;

class HashGenerator implements HashGenerable {
        @Override
        public String generateHash(Set<Integer> numberFromUser) {
            return UUID.randomUUID().toString();
        }
    }

