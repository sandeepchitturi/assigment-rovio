package com.rovio.plushmarket.processor.graph;

import com.rovio.plushmarket.exception.PlushMarketException;
import com.rovio.plushmarket.model.*;
import com.rovio.plushmarket.model.builder.ActionBuilder;
import com.rovio.plushmarket.model.builder.TradeGraphNodeBuilder;
import com.rovio.plushmarket.processor.data.PlushDataProcessor;
import com.rovio.plushmarket.processor.data.TradesDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

import static java.util.Objects.isNull;


@Service
public class TradeGraphProcessor implements GraphProcessor<Strategy, TradeGraphNode, Market, Offer> {

    private Map<String, List<Trade>> tradesMap;
    private TreeMap<String, BigDecimal> plushesMap;

    private final PlushDataProcessor plushDataProcessor;

    private final TradesDataProcessor tradesDataProcessor;

    static final Logger log = LoggerFactory.getLogger(TradeGraphProcessor.class);

    public TradeGraphProcessor(PlushDataProcessor plushDataProcessor, TradesDataProcessor tradesDataProcessor) {
        this.plushDataProcessor = plushDataProcessor;
        this.tradesDataProcessor = tradesDataProcessor;
    }

    /**
     * This method derives best strategy for the given offer from the market
     *
     * @param market
     * @param offer
     * @return
     */
    @Override
    public Strategy findBestStrategy(Market market, Offer offer) throws PlushMarketException {
        log.info("Inside findBestStrategy method");
        Deque<Action> bestStrategy = new ArrayDeque<>();

        initializeMarketData(market);

        // when offer is the highest priced or plush is present and does not have any trades
        if (isHighestPraisedPlush(offer) || (plushesMap.containsKey(offer.getPlush()) && !tradesMap.containsKey(offer.getPlush()))) {
            log.debug("if when offer is the highest priced {} or plush is present and does not have any trades", isHighestPraisedPlush(offer));
            final Action sell = ActionBuilder.anAction()
                    .withAction("SELL")
                    .withPlush(offer.getPlush())
                    .withPrice(plushesMap.get(offer.getPlush()))
                    .build();
            bestStrategy.push(sell);
            return new Strategy(bestStrategy);
            // when plush is present and also has trades
        } else if (plushesMap.containsKey(offer.getPlush()) && tradesMap.containsKey(offer.getPlush())) {
            log.debug("when plush is present and also has trades");
            return new Strategy(getBestStrategyByTrade(offer));
        }
        // when plush is not present
        else {
            log.debug("when when plush is not present");
            return new Strategy(bestStrategy);
        }
    }
    /**
     * This method constructs trade graph and extracts the best strategy for max profit by
     * traversing all strategies in given TradeGraph
     *
     * @param offer
     * @return Deque<Action>
     */
    private Deque<Action> getBestStrategyByTrade(Offer offer) throws PlushMarketException {
        final TradeGraphNode tradeGraphForGivenOffer = constructGraph(offer);
        return extractBestStrategyFromGraph(tradeGraphForGivenOffer);
    }

    /**
     * This method constructs trades graph from given trades
     * for given offer by recursively call construct method
     *
     * @param offer
     * @return TradeGraphNode
     */
    @Override
    public TradeGraphNode constructGraph(Offer offer) throws PlushMarketException{
        log.info("Inside constructGraph method");
        TradeGraphNode startNode = TradeGraphNodeBuilder.aTradeGraphNode()
                .withNodeName(offer.getPlush())
                .withAction("TRADE")
                .withActionNodes(new ArrayList<>()).build();
        log.debug("Trade Graph start node {}", startNode);
        return construct(startNode);
    }

    /**
     * This method initializes Market in data like
     * Plushes Map and Trades Map
     *
     * @param market
     */
    private void initializeMarketData(Market market) {
        log.info("Inside initializeMarketData method");
        plushesMap = plushDataProcessor.process(market.getPlushes());
        log.debug("Plushes Map {}", plushesMap);
        tradesMap = tradesDataProcessor.process(market.getTrades());
        log.debug("Trades Map {} ", tradesMap);
    }


    /**
     * This method recursively generates trade graph for
     * given TradeGraphNode
     *
     * @param currentNode
     * @return TradeGraphNode
     */
    private TradeGraphNode construct(TradeGraphNode currentNode) throws PlushMarketException {
        log.info("Inside construct method");
        try {
            if (tradesMap.containsKey(currentNode.getNodeName())) {
                tradesMap.get(currentNode.getNodeName()).forEach(
                        trade -> {
                            TradeGraphNode tradeGraphNode = TradeGraphNodeBuilder.aTradeGraphNode()
                                    .withNodeName(trade.getGive())
                                    .withAction("TRADE")
                                    .withActionNodes(new ArrayList<>())
                                    .build();
                            if (tradesMap.containsKey(currentNode.getNodeName()))
                                currentNode.getActionNodes().add(construct(tradeGraphNode));
                        });
            } else currentNode.setAction("SELL");
        } catch (Exception e) {
            log.error("Error occurred while constructing TradeGraph:", e);
            throw new PlushMarketException("internal server error",e.getCause());
        }

        return currentNode;
    }

    /**
     * This method extracts best strategy by recursively traveling trade graph
     *
     * @param currentRoot
     * @return Deque<Action>
     */
    private Deque<Action> extractBestStrategyFromGraph(TradeGraphNode currentRoot) throws PlushMarketException {
        log.info("Inside construct method");
        Deque<Action> bestStrategy = null;
        try {
            if (currentRoot.getAction().equalsIgnoreCase("SELL")) {
                final Action sell = ActionBuilder.anAction()
                        .withAction("SELL")
                        .withPlush(currentRoot.getNodeName())
                        .withPrice(plushesMap.get(currentRoot.getNodeName()))
                        .build();
                bestStrategy = new ArrayDeque<>();
                bestStrategy.addLast(sell);
                return bestStrategy;
            } else if (currentRoot.getAction().equalsIgnoreCase("TRADE")) {
                for (TradeGraphNode node : currentRoot.getActionNodes()) {
                    final Action trade = ActionBuilder.anAction()
                            .withAction("TRADE")
                            .withGive(currentRoot.getNodeName())
                            .withTake(node.getNodeName())
                            .build();
                    final Deque<Action> currentStrategy = extractBestStrategyFromGraph(node);
                    currentStrategy.addFirst(trade);
                    log.debug("Best Strategy {}, Current Strategy {}",bestStrategy, currentStrategy);
                    bestStrategy = isNull(bestStrategy) ? currentStrategy : compareStrategiesNFindBest(bestStrategy, currentStrategy);
                    log.debug("Best Strategy after compare {}",bestStrategy);
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while extractBestStrategyFromGraph:", e);
            throw new PlushMarketException("internal server error",e.getCause());
        }
        return bestStrategy;
    }

    /**
     * This method compares sell prices of given strategies
     * and the one with maximum profit, if both are returning
     * same profit and choose the one with least one of trades
     *
     * @param bestStrategy
     * @param currentStrategy
     * @return Deque<Action>
     */
    private Deque<Action> compareStrategiesNFindBest(Deque<Action> bestStrategy, Deque<Action> currentStrategy) throws PlushMarketException {
        log.info("Inside isHighestPraisedPlush method");
        if (bestStrategy.peekLast() != null && currentStrategy.peekLast() != null) {
            final BigDecimal bestStrategySellPrice = bestStrategy.peekLast().getPrice();
            final BigDecimal currentStrategySellPrice = currentStrategy.peekLast().getPrice();
            if (bestStrategySellPrice.compareTo(currentStrategySellPrice) > 0) return bestStrategy;
            else if (bestStrategySellPrice.compareTo(currentStrategySellPrice) < 0) return currentStrategy;
            else {
                if (bestStrategy.size() <= currentStrategy.size()) return bestStrategy;
                else return currentStrategy;
            }
        }
        throw new PlushMarketException("internal server error");
    }

    private boolean isHighestPraisedPlush(Offer offer) {
        log.info("Inside isHighestPraisedPlush method");
        return plushesMap.lastEntry().getKey().equalsIgnoreCase(offer.getPlush());
    }

}
