package org.magcruise.gaming.examples.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class PotatoOrder extends GameMessage {

	public final int num;

	public PotatoOrder(Symbol from, Symbol to, int orderOfPotato) {
		super(from, to);
		this.num = orderOfPotato;
	}

	@Override
	public Object[] getConstractorArgs() {
		return new Object[] { from, to, num };
	}

}
