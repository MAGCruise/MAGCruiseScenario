package org.magcruise.gaming.scenario.trans;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;
import org.magcruise.gaming.model.game.MainProperty;

public class TranslationGameContext extends Context {

	@MainProperty(name = "寄付金(トークン)")
	public int funds = 0;

	public TranslationGameContext(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	public void beforeRound() {
		funds = 0;
	}

	public void clearing() {
		players.forEach(p -> {
			TranslationGamePlayer player = (TranslationGamePlayer) p;
			funds += player.investment;
		});

		final double rightOfUse;

		if (getRoundnum() <= 10) {
			rightOfUse = 200;
		} else {
			rightOfUse = funds * 2 / players.size();
		}

		players.forEach(p -> {
			TranslationGamePlayer player = (TranslationGamePlayer) p;
			player.rightOfUse = (int) rightOfUse;
		});
	}

}
