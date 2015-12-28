package org.magcruise.gaming.tutorial.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class CroquetteOrder extends GameMessage {

	public int num;

	public CroquetteOrder(Symbol from, Symbol to, int num) {
		super(from, to);
		this.num = num;
	}

}
