package com.rovio.plushmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.List;

public class Market {

	@JsonProperty("plushes")
	@Valid
	private List<Plush> plushes;

	@JsonProperty("trades")
	@Valid
	private List<Trade> trades;

	public List<Plush> getPlushes(){
		return plushes;
	}

	public List<Trade> getTrades(){
		return trades;
	}

	@Override
	public String toString() {
		return "MarketJSON{" +
				"plushes=" + plushes +
				", trades=" + trades +
				'}';
	}
}



