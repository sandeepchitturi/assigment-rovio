package com.rovio.plushmarket.controller;

import com.rovio.plushmarket.exception.PlushMarketException;
import com.rovio.plushmarket.model.Market;
import com.rovio.plushmarket.model.Strategy;
import com.rovio.plushmarket.service.PlushMarketService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@Validated
public class PlushMarketController {

    public final PlushMarketService plushMarketService;
    static final Logger log = LoggerFactory.getLogger(PlushMarketController.class);

    public PlushMarketController(PlushMarketService plushMarketService) {
        this.plushMarketService = plushMarketService;
    }

    @PostMapping(path = "/calculate_strategy", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Strategy.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<Strategy> calculateStrategy(@RequestBody @Valid Market market, @RequestParam String plush)  throws PlushMarketException {
        log.info("inside calculateStrategy method");
        log.debug("Market {} ,Plush {}", market, plush);
        final ResponseEntity<Strategy> strategyResponseEntity = new ResponseEntity<>(plushMarketService.calculateStrategy(market, plush), HttpStatus.OK);
        log.debug("Strategy {}", strategyResponseEntity);
        return strategyResponseEntity;
    }
}
