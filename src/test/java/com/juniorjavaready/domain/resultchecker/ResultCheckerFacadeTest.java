package com.juniorjavaready.domain.resultchecker;

import com.juniorjavaready.domain.numbergenerator.NumberGeneratorFacade;
import com.juniorjavaready.domain.numberreceiver.NumberReceiverFacade;
import lombok.RequiredArgsConstructor;
import org.junit.Test;

import static org.mockito.Mockito.mock;

@RequiredArgsConstructor
public class ResultCheckerFacadeTest {
    private final NumberGeneratorFacade numberGeneratorFacade= mock(NumberGeneratorFacade.class);
    private final NumberReceiverFacade numberReceiverFacade=mock(NumberReceiverFacade.class);
    private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository() {
    };

    @Test
    public void test() {}

}