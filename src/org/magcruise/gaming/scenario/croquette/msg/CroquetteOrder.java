package org.magcruise.gaming.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.SimpleGameMessage;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class CroquetteOrder extends SimpleGameMessage {

	public int num;

	public CroquetteOrder(Symbol from, int num) {
		super(new SimpleSymbol(CroquetteOrder.class.getSimpleName()));
		this.from = from;
		this.num = num;
	}

}
