package it.flare.domain;

import java.math.BigDecimal;

public class Message3  {

	final Item item;
	final Operation operation;

	public Operation getOperation() {
		return operation;
	}

	public Message3(String type,String value,String op) {
		
		this.item = new Item().setType(type).setValue(new BigDecimal(value));
		this.operation=Operation.valueOf(op);
	}
	
	
	public  enum Operation{
		ADD,SUBTRACT,MULTIPLY
	}
	
	public Item getItem() {
		return item;
	}
	
	
	
}
