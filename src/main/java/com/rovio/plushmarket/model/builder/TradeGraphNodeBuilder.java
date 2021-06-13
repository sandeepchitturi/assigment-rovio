package com.rovio.plushmarket.model.builder;

import com.rovio.plushmarket.model.TradeGraphNode;
import java.util.List;

public final class TradeGraphNodeBuilder {
    String nodeName;
    String action;
    List<TradeGraphNode> actionNodes;

    private TradeGraphNodeBuilder() {
    }

    public static TradeGraphNodeBuilder aTradeGraphNode() {
        return new TradeGraphNodeBuilder();
    }

    public TradeGraphNodeBuilder withNodeName(String name) {
        this.nodeName = name;
        return this;
    }

    public TradeGraphNodeBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    public TradeGraphNodeBuilder withActionNodes(List<TradeGraphNode> actionNodes) {
        this.actionNodes = actionNodes;
        return this;
    }

    public TradeGraphNode build() {
        return new TradeGraphNode(nodeName, action, actionNodes);
    }
}
