package org.magcruise.gaming.scenario.ele;

import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Player;

public class CompanyPlayer extends Player {

	public String type;
	public String area;
	public int demand;
	public int reservation;
	public Trades trades;

	public CompanyPlayer(Symbol playerName, Symbol playerType) {
		super(playerName, playerType);
	}

	public void init(CompanySetting c) {
		this.type = c.type;
		this.area = c.area;
		this.demand = c.demand;
		this.reservation = c.reservation;
		this.trades = new Trades();
	}

	public void bond(CompanyPlayer counterpart, int price, int amount) {
		this.trades.add(new Trade(counterpart.name, price, amount));
		this.demand -= amount;
	}

}
