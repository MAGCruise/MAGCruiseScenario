package org.magcruise.gaming.examples.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class CroquetteOrder extends GameMessage {

	public final int num;

	public CroquetteOrder(Symbol from, Symbol to, int num) {
		super(from, to);
		this.num = num;
	}

	@Override
	public Object[] getConstractorArgs() {
		return new Object[] { from, to, num };
	}

}
