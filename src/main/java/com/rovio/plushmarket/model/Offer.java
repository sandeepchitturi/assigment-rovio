package com.rovio.plushmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Offer {

	@JsonProperty("plush")
	@NotBlank(message="plush cannot be null")
	private String plush;

	public Offer(@NotNull(message = "Offer's plush cannot be null") String plush) {
		this.plush = plush;
	}

	public String getPlush(){
		return plush;
	}

	@Override
	public String toString() {
		return "OfferJSON{" +
				"plush='" + plush + '\'' +
				'}';
	}
}