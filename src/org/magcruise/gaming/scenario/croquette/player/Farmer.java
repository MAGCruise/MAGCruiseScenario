package org.magcruise.gaming.scenario.croquette.player;

import org.magcruise.gaming.model.game.MainProperty;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.scenario.croquette.Market;

import gnu.mapping.SimpleSymbol;

public class Farmer extends Player {

	@MainProperty
	public Number receivedOrderOfPotato;

	public Farmer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void receiveOrder(int order) {
		this.receivedOrderOfPotato = order;
	}

	public void refresh() {
		this.receivedOrderOfPotato = 0;
	}

	public int delivery(Market ctx) {
		int order = ctx.roundnum < 1 ? 0
				: Integer.valueOf(
						getLastValue(new SimpleSymbol("receivedOrderOfPotato"))
								.toString());
		return order;
	}

}
