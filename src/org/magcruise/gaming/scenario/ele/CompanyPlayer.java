package org.magcruise.gaming.scenario.ele;

import gnu.mapping.SimpleSymbol;
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
		props.set(new SimpleSymbol("type"), type);
		props.set(new SimpleSymbol("area"), area);
	}

	public void bond(CompanyPlayer counterpart, int price, int amount) {
		this.trades.add(new Trade(counterpart.name, price, amount));
		this.demand -= amount;
	}

	@Override
	public void finalizeRound() {
		props.set(new SimpleSymbol("demand"), demand);
		props.set(new SimpleSymbol("reservation"), reservation);
		props.set(new SimpleSymbol("trades"), trades);
		super.finalizeRound();
	}

}
