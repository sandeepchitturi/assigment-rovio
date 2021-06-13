package com.rovio.plushmarket.controller;

import com.rovio.plushmarket.model.Action;
import com.rovio.plushmarket.model.Strategy;
import com.rovio.plushmarket.service.PlushMarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlushMarketController.class)
class PlushMarketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlushMarketService plushMarketService;

    private Strategy strategy;
    private String validMarket;
    private String inValidPlushesMarket;
    private String inValidTradesMarket;

    @BeforeEach
    void initPlushMarketControllerTest() throws IOException {
        Deque<Action> actions = new ArrayDeque<Action>();
        strategy = new Strategy(actions);
        validMarket = new String(Files.readAllBytes(Paths.get("src/test/resources/data/valid_market.json")));
        inValidPlushesMarket = new String(Files.readAllBytes(Paths.get("src/test/resources/data/invalid_plushes_market.json")));
        inValidTradesMarket = new String(Files.readAllBytes(Paths.get("src/test/resources/data/invalid_trades_market.json")));
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        when(plushMarketService.calculateStrategy(any(), eq("Test"))).thenReturn(strategy);
        mockMvc.perform(post("/calculate_strategy")
                .param("plush", "RedBird")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validMarket)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void whenInValidPlushesInput_thenReturns400() throws Exception {
        when(plushMarketService.calculateStrategy(any(), eq("Test"))).thenReturn(strategy);
        mockMvc.perform(post("/calculate_strategy")
                .param("plush", "RedBird")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inValidPlushesMarket)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    void whenInValidTradesInput_thenReturns400() throws Exception {
        when(plushMarketService.calculateStrategy(any(), eq("Test"))).thenReturn(strategy);
        mockMvc.perform(post("/calculate_strategy")
                .param("plush", "RedBird")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inValidTradesMarket)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    void whenInValidOfferInput_thenReturns400() throws Exception {
        when(plushMarketService.calculateStrategy(any(), eq("Test"))).thenReturn(strategy);
        mockMvc.perform(post("/calculate_strategy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validMarket)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}