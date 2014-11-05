package org.magcruise.gaming.scenario.ele;

import gnu.mapping.Symbol;

import java.io.Serializable;

public class Trade implements Serializable {

	private Symbol partner;
	private int price;
	private int amount;

	public Trade(Symbol partner, int price, int amount) {
		this.partner = partner;
		this.price = price;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return partner + "," + amount + "," + price;
	}
}