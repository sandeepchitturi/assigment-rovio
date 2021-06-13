package com.rovio.plushmarket.service.impl;

import com.rovio.plushmarket.exception.PlushMarketException;
import com.rovio.plushmarket.model.Market;
import com.rovio.plushmarket.model.Offer;
import com.rovio.plushmarket.model.Strategy;
import com.rovio.plushmarket.processor.graph.TradeGraphProcessor;
import com.rovio.plushmarket.service.PlushMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlushMarketServiceImpl implements PlushMarketService {
    static final Logger log = LoggerFactory.getLogger(PlushMarketServiceImpl.class);
    private final TradeGraphProcessor tradeGraphProcessor;

    public PlushMarketServiceImpl(TradeGraphProcessor tradeGraphProcessor) {
        this.tradeGraphProcessor = tradeGraphProcessor;
    }

    @Override
    public Strategy calculateStrategy(Market market, String plush) throws PlushMarketException {
        log.info("Inside calculateStrategy method");
        final Strategy bestStrategy = tradeGraphProcessor.findBestStrategy(market, new Offer(plush));
        log.debug("Best Strategy {}", bestStrategy);
        return bestStrategy;
    }
}
