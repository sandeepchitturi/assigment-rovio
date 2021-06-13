package com.rovio.plushmarket.model.builder;

import com.rovio.plushmarket.model.Action;
import java.math.BigDecimal;

public final class ActionBuilder {
    private String plush;
    private BigDecimal price;
    private String action;
    private String give;
    private String take;

    private ActionBuilder() {
    }

    public static ActionBuilder anAction() {
        return new ActionBuilder();
    }

    public ActionBuilder withPlush(String plush) {
        this.plush = plush;
        return this;
    }

    public ActionBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ActionBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    public ActionBuilder withGive(String give) {
        this.give = give;
        return this;
    }

    public ActionBuilder withTake(String take) {
        this.take = take;
        return this;
    }

    public Action build() {
        return new Action(plush,price,action,give,take);
    }
}
