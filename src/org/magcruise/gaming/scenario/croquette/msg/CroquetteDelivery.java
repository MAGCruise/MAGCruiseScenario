package org.magcruise.gaming.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.SimpleGameMessage;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class CroquetteDelivery extends SimpleGameMessage {

	public int num;

	public CroquetteDelivery(Symbol name) {
		super(name);
	}

	public CroquetteDelivery(Symbol from, int orderOfCroquette) {
		super(new SimpleSymbol(CroquetteDelivery.class.getSimpleName()));
		this.from = from;
		this.num = orderOfCroquette;
	}
}
