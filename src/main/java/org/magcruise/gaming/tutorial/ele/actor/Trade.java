package org.magcruise.gaming.tutorial.ele.actor;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.lang.SConstructive;
import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.Symbol;

public class Trade implements SConstructive {

	private Symbol partner;
	private int price;
	private int amount;

	public Trade() {
	}

	public Trade(Symbol partner, int price, int amount) {
		this.partner = partner;
		this.price = price;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public SConstructor<? extends Trade> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(), partner, price,
				amount);
	}

	public Symbol getPartner() {
		return partner;
	}

	public int getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}

}