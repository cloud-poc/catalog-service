package org.akj.springboot.catalog.entity;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Amount {
	private String currency = "CNY";

	private BigDecimal amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	

}
