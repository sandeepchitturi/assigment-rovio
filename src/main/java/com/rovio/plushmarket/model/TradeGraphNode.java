package com.rovio.plushmarket.model;

import java.util.List;

public class TradeGraphNode {
    private String nodeName;
    private String action;
    private List<TradeGraphNode> actionNodes;

    public TradeGraphNode(String nodeName, String action, List<TradeGraphNode> takeNodes) {
        this.nodeName = nodeName;
        this.action = action;
        this.actionNodes = takeNodes;
    }

    public TradeGraphNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public List<TradeGraphNode> getActionNodes() {
        return actionNodes;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "TradeGraphNode{" +
                "nodeName='" + nodeName + '\'' +
                ", action=" + action +
                ", actionNodes=" + actionNodes +
                '}';
    }
}
