package com.juniorjavaready.domain.resultannouncer;

import java.util.Optional;

public interface ResponseRepository {
    Optional<ResultResponse> findByHash(String hash);
    boolean existsById(String hash);

    void save(ResultResponse resultResponse);
}
