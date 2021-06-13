package com.rovio.plushmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@JsonPropertyOrder({"plush", "price"})
public class Plush implements Comparable<Plush>{

	@JsonProperty("plush")
	@NotBlank(message = "Market's plush cannot be empty")
	private String plush;

	@JsonProperty("price")
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal price;

	public String getPlush() {
		return plush;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Plush{" +
				"plush='" + plush + '\'' +
				", price=" + price +
				'}';
	}

	@Override
	public int compareTo(Plush o) {
		return this.getPrice().compareTo(o.getPrice());
	}
}