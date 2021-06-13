package com.rovio.plushmarket.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

@JsonPropertyOrder({"action","give","take","plush","price"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {

	@JsonProperty("plush")
	private final String plush;

	@JsonProperty("price")
	private final BigDecimal price;

	@JsonProperty("action")
	private final String action;

	@JsonProperty("give")
	private final String give;

	@JsonProperty("take")
	private final String take;

	public Action(String plush, BigDecimal price, String action, String give, String take) {
		this.plush = plush;
		this.price = price;
		this.action = action;
		this.give = give;
		this.take = take;
	}


	public String getPlush(){
		return plush;
	}

	public BigDecimal getPrice(){
		return price;
	}

	public String getAction(){
		return action;
	}

	public String getGive(){
		return give;
	}

	public String getTake(){
		return take;
	}

	@Override
	public String toString() {
		return "Action{" +
				"plush='" + plush + '\'' +
				", price=" + price +
				", action='" + action + '\'' +
				", give='" + give + '\'' +
				", take='" + take + '\'' +
				'}';
	}
}