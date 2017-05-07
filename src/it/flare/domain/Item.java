package it.flare.domain;

import java.math.BigDecimal;

public class Item {
	private String type;
	private BigDecimal value; 
	
	
	public String getType() {
		return type;
	}
	public Item setType(String type) {
		this.type = type;
		return this;
	}
	public BigDecimal getValue() {
		return value;
	}
	public Item setValue(BigDecimal value) {
		this.value = value;
		return this;
	}
	

	@Override
	public String toString() {
		return "Item [type=" + type + ", value=" + value + "]";
	}
	
	
	
}
