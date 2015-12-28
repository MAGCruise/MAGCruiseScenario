package org.magcruise.gaming.tutorial.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class CroquetteDelivery extends GameMessage {

	public int num;

	public CroquetteDelivery(Symbol from, Symbol to, int orderOfCroquette) {
		super(from, to);
		this.num = orderOfCroquette;
	}

}
