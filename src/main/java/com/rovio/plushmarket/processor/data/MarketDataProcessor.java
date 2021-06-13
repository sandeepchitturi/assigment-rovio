package com.rovio.plushmarket.processor.data;
import java.util.Map;

public interface MarketDataProcessor<R extends Map<String, ?>, T> {
    Map<String, ?> process(T data);
}
