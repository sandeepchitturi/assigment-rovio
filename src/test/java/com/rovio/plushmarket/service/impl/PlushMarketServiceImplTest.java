package com.rovio.plushmarket.service.impl;

import com.rovio.plushmarket.model.Action;
import com.rovio.plushmarket.model.Strategy;
import com.rovio.plushmarket.model.builder.ActionBuilder;
import com.rovio.plushmarket.processor.graph.TradeGraphProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlushMarketServiceImplTest {
    private PlushMarketServiceImpl plushMarketService;
    @Mock
    TradeGraphProcessor tradeGraphProcessor;
    @BeforeEach
    void initPlushMarketService() {
        plushMarketService = new PlushMarketServiceImpl(tradeGraphProcessor);
    }


    @Test
    void testCalculateStrategy() {
        Deque<Action> actions = new ArrayDeque<>();
        final Action action = ActionBuilder.anAction().withAction("SELL").withPlush("STELLA").withPrice(new BigDecimal(90)).build();
        actions.push(action);
        Strategy strategy = new Strategy(actions);
        when(tradeGraphProcessor.findBestStrategy(any(),any())).thenReturn(strategy);
        Strategy best = plushMarketService.calculateStrategy(any(), eq("test"));
        assertEquals(1,best.getStrategy().size());
    }
}