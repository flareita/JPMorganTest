package it.flare.domain;

import java.math.BigDecimal;

public class Message1 {

	final Item item;

	public Item getItem() {
		return item;
	}

	public Message1(String type,String value) {
		this.item = new Item().setType(type).setValue(new BigDecimal(value));
	}

}
