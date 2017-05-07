package it.flare.domain;

import java.math.BigDecimal;

public class Rank {

	 BigDecimal amountIn;
	 BigDecimal amountOut;
	public BigDecimal getAmountOut() {
		return amountOut;
	}
	public BigDecimal getAmountIn() {
		return amountIn;
	}
	public String getEntity() {
		return entity;
	}
	 public Rank setAmountIn(BigDecimal amountIn) {
		this.amountIn = amountIn;
		return this;
	}
	public Rank setAmountOut(BigDecimal amountOut) {
		this.amountOut = amountOut;
		return this;
	}
	public Rank setEntity(String entity) {
		this.entity = entity;
		return this;
	}
	String entity;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rank other = (Rank) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		return true;
	}
	
	
}
