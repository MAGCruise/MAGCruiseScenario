package org.magcruise.gaming.tutorial.croquette.msg;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class CroquetteDelivery extends GameMessage {

	public final int num;

	public CroquetteDelivery(Symbol from, Symbol to, int orderOfCroquette) {
		super(from, to);
		this.num = orderOfCroquette;
	}

	@Override
	public SConstructor<? extends GameMessage> toConstructor() {
		return SConstructor.toConstructor(this.getClass(), from, to, num);
	}

	@Override
	public Object[] getConstractorArgs() {
		return new Object[] { from, to, num };
	}

}
