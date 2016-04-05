package org.magcruise.gaming.tutorial.croquette.msg;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.message.GameMessage;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.Symbol;

public class CroquetteOrder extends GameMessage {

	public final int num;

	public CroquetteOrder(Symbol from, Symbol to, int num) {
		super(from, to);
		this.num = num;
	}

	@Override
	public SConstructor<? extends GameMessage> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(), from, to, num);
	}

}
