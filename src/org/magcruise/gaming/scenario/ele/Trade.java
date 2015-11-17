package org.magcruise.gaming.scenario.ele;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.lang.SConvertible;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.Symbol;

public class Trade implements SConvertible {

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
	public String toConstructorInSExpression() {
		return SExpressionUtils.toConstructorInSExpression(this, partner, price, amount);
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