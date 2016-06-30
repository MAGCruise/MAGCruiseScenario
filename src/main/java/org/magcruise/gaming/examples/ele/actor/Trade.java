package org.magcruise.gaming.examples.ele.actor;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.magcruise.gaming.lang.SConstructive;
import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.ActorName;

public class Trade implements SConstructive {

	private ActorName partner;
	private int price;
	private int amount;

	public Trade() {
	}

	public Trade(ActorName partner, int price, int amount) {
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
	public SConstructor<? extends Trade> toConstructor(
			ToExpressionStyle style) {
		return SConstructor.toConstructor(style, getClass(), partner, price,
				amount);
	}

	public ActorName getPartner() {
		return partner;
	}

	public int getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}

}