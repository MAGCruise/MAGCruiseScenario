package org.magcruise.gaming.tutorial.ultimatum.msg;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.message.GameMessage;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.Symbol;

public class FinalNote extends GameMessage {

	public int proposition;

	public FinalNote(Symbol from, Symbol to, int proposition) {
		super(from, to);
		this.proposition = proposition;
	}

	@Override
	public SConstructor<? extends GameMessage> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(), from, to,
				proposition);
	}

}
