package org.magcruise.gaming.tutorial.croquette.msg;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.message.GameMessage;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.Symbol;

public class CroquetteDelivery extends GameMessage {

	public final int num;

	public CroquetteDelivery(Symbol from, Symbol to, int orderOfCroquette) {
		super(from, to);
		this.num = orderOfCroquette;
	}

	@Override
	public SConstructor<? extends GameMessage> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(), from, to, num);
	}

}
