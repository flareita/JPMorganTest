package it.flare.domain;

import java.math.BigDecimal;

public class Message2  {

	final Item item;
	final Integer num;

	public Integer getNum() {
		return num;
	}

	public Item getItem() {
		return item;
	}

	public Message2(String type,String value,Integer num) {
		this.item = new Item().setType(type).setValue(new BigDecimal(value));
		this.num=num;
	}
	
}
