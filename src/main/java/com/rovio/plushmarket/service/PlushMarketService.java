package com.rovio.plushmarket.service;

import com.rovio.plushmarket.exception.PlushMarketException;
import com.rovio.plushmarket.model.Market;
import com.rovio.plushmarket.model.Strategy;

public interface PlushMarketService {
    Strategy calculateStrategy(Market market, String plush) throws PlushMarketException;
}
