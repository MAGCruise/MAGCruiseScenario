package org.magcruise.gaming.tutorial.croquette.msg;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.message.GameMessage;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.Symbol;

public class PotatoDelivery extends GameMessage {

	public final int num;

	public PotatoDelivery(Symbol from, Symbol to, int orderOfPotato) {
		super(from, to);
		this.num = orderOfPotato;
	}

	@Override
	public SConstructor<? extends GameMessage> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(), from, to, num);
	}

}
