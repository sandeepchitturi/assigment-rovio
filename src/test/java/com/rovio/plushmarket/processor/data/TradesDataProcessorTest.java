package com.rovio.plushmarket.processor.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rovio.plushmarket.model.Market;
import com.rovio.plushmarket.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class TradesDataProcessorTest {
    private TradesDataProcessor tradesDataProcessor;
    private Market market;
    private ObjectMapper objectMapper;
    @BeforeEach
    void intiTradeDataProcessorTest() throws IOException {
        tradesDataProcessor = new TradesDataProcessor();
        objectMapper= new ObjectMapper();
        market = objectMapper.readValue(new File("src/test/resources/data/valid_market.json"), Market.class);
    }

    @Test
    void testTradeDataProcess() {
        final Map<String, List<Trade>> tradesMap = tradesDataProcessor.process(market.getTrades());
        assertEquals(1,tradesMap.get("Pig").size());
        assertTrue(tradesMap.get("RedBird").get(0).getTake().equalsIgnoreCase("RedBird"));
    }

}