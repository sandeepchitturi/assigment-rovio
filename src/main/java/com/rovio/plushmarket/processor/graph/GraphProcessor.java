package com.rovio.plushmarket.processor.graph;

import com.rovio.plushmarket.exception.PlushMarketException;

public interface GraphProcessor<R, T, S, O> {
    public R findBestStrategy(S market, O offer) throws PlushMarketException;

    public T constructGraph(O offer) throws PlushMarketException;
}
