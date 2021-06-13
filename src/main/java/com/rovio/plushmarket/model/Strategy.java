package com.rovio.plushmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Strategy {

    @JsonProperty("Actions")
    private final List<Action> strategy;

    public Strategy(Deque<Action> actionStack) {
        this.strategy = new ArrayList<>(actionStack);
    }


    public List<Action> getStrategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return "Strategy {" +
                "Actions=" + strategy +
                '}';
    }
}