package com.rovio.plushmarket.processor.graph;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rovio.plushmarket.model.*;
import com.rovio.plushmarket.processor.data.PlushDataProcessor;
import com.rovio.plushmarket.processor.data.TradesDataProcessor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeGraphProcessorTest {
    private TradeGraphProcessor tradeGraphProcessor;
    @Mock
    private PlushDataProcessor plushDataProcessor;
    @Mock
    private TradesDataProcessor tradesDataProcessor;
    private ObjectMapper mapper;
    private Market market;
    private Offer offer;
    private TreeMap<String,BigDecimal> plushesMap;
    private HashMap<String, List<Trade>> tradesMap;

    @BeforeEach
    void initTradeGraphProcessorTest() throws IllegalAccessException, IOException {
        mapper = new ObjectMapper();
        tradeGraphProcessor = new TradeGraphProcessor(plushDataProcessor,tradesDataProcessor);
        market = mapper.readValue(new File("src/test/resources/data/valid_market.json"), Market.class);
        offer = new Offer("RedBird");
        tradesMap = mapper.readValue(new File("src/test/resources/data/tradesMap.json"),  new TypeReference<HashMap<String,List<Trade>>>() {});
        plushesMap= mapper.readValue(new File("src/test/resources/data/plushesMap.json"),  new TypeReference<TreeMap<String,BigDecimal>>() {});
        FieldUtils.writeField(tradeGraphProcessor,"tradesMap",tradesMap,true);
        FieldUtils.writeField(tradeGraphProcessor,"plushesMap",plushesMap,true);
    }

    @Test
    void findBestStrategy() {
        when(tradesDataProcessor.process(market.getTrades())).thenReturn(tradesMap);
        when(plushDataProcessor.process(market.getPlushes())).thenReturn(plushesMap);
        final Strategy bestStrategy = tradeGraphProcessor.findBestStrategy(market, offer);
        final Action sell = bestStrategy.getStrategy().stream().filter(action -> action.getAction().equalsIgnoreCase("SELL")).findFirst().get();
        assertEquals("Stella",sell.getPlush());
    }

    @Test
    void constructGraph() {
        final TradeGraphNode tradeGraphNode = tradeGraphProcessor.constructGraph(offer);
        assertNotNull(tradeGraphNode);
        assertEquals("RedBird", tradeGraphNode.getNodeName());
    }
}