package com.rovio.plushmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;
@JsonPropertyOrder({"take","give"})
public class Trade {

	@JsonProperty("take")
	@NotNull(message="take cannot be null")
	private String take;

	@JsonProperty("give")
	@NotNull(message="give cannot be null")
	private String give;

	public String getTake(){
		return take;
	}

	public String getGive(){
		return give;
	}

	@Override
	public String toString() {
		return "TradesItem{" +
				"take='" + take + '\'' +
				", give='" + give + '\'' +
				'}';
	}
}