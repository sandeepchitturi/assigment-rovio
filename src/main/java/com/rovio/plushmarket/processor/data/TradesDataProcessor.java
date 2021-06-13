package com.rovio.plushmarket.processor.data;

import com.rovio.plushmarket.model.Trade;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
@Service
public class TradesDataProcessor implements MarketDataProcessor<Map<String, List<Trade>>, List<Trade>> {
    @Override
    public Map<String,List<Trade>> process(List<Trade> trades) {
        return trades.stream().collect(groupingBy(Trade::getTake));
    }
}
