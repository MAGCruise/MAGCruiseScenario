package org.magcruise.gaming.scenario.croquette.player;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Attribute;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.Player;

public class Farmer extends Player {

	public int initDelivery;

	@Attribute
	public int receivedOrderOfPotato;

	public Farmer(Symbol playerName, Symbol playerType) {
		super(playerName, playerType);
	}

	public void init() {
		this.initDelivery = 100;
	}

	public void receiveOrder(int order) {
		this.receivedOrderOfPotato = order;
	}

	public int delivery(Context ctx) {

		int order = ctx.roundnum < 1 ? initDelivery : (int) prev(1,
				new SimpleSymbol("receivedOrderOfPotato"));
		return order;
	}
}
