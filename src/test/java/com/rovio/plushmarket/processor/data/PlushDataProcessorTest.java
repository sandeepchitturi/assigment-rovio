package com.rovio.plushmarket.processor.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rovio.plushmarket.model.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlushDataProcessorTest {
 private PlushDataProcessor plushDataProcessor;
 private Market market;
 private ObjectMapper objectMapper;
    @BeforeEach
    void intiPlushDataProcessorTest() throws IOException {
        plushDataProcessor = new PlushDataProcessor();
        objectMapper= new ObjectMapper();
        market = objectMapper.readValue(new File("src/test/resources/data/valid_market.json"), Market.class);
    }

    @Test
    void testPlushDataProcess() {
        final Map<String, BigDecimal> plushesMap = plushDataProcessor.process(market.getPlushes());
        assertEquals(3,plushesMap.size());
        assertTrue(plushesMap.containsKey("RedBird"));
    }
}