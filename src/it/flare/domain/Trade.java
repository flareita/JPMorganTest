package it.flare.domain;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Trade {
final String entity;
final String bs;
final BigDecimal fx;
final String currency;
final LocalDate instruction;
final LocalDate settlement;
final Integer units;
final BigDecimal price;
public Trade(String entity, String bs, String fx, String currency, String instruction, String settlement,
		String units, String price) {
	super();
	this.entity = entity;
	this.bs = bs;
	this.fx = new BigDecimal(fx);
	this.currency = currency;
	this.instruction = LocalDate.parse(instruction);
	this.settlement = fixSettlement(settlement,currency);
	this.units = Integer.valueOf(units);
	this.price = new BigDecimal(price);
}

private LocalDate fixSettlement(String date,String currency){
	LocalDate settlement = LocalDate.parse(date);
	if (currency.equals("AED") || currency.equals("SAR")){
		while (settlement.getDayOfWeek()==DayOfWeek.SATURDAY||settlement.getDayOfWeek()==DayOfWeek.FRIDAY)
			settlement=settlement.plusDays(1);
		
	} else {
		while (settlement.getDayOfWeek()==DayOfWeek.SATURDAY||settlement.getDayOfWeek()==DayOfWeek.SUNDAY)
			settlement=settlement.plusDays(1);
	}
	return settlement;
}

public String getEntity() {
	return entity;
}
public String getBs() {
	return bs;
}
public BigDecimal getFx() {
	return fx;
}
public String getCurrency() {
	return currency;
}
public LocalDate getInstruction() {
	return instruction;
}
public LocalDate getSettlement() {
	return settlement;
}
public Integer getUnits() {
	return units;
}
public BigDecimal getPrice() {
	return price;
}



}
