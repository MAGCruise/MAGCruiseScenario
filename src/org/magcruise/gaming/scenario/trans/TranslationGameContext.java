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

		players.forEach(p -> {
			TranslationGamePlayer player = (TranslationGamePlayer) p;

			double rightOfUse;
			if (getRoundnum() <= 10) {
				rightOfUse = 200;
			} else {
				rightOfUse = funds * 2 / players.size();
			}
			player.rightOfUse = (int) rightOfUse;

			if (15 <= getRoundnum() && getRoundnum() <= 19) {
				if (funds < 200) {
					player.score = 1;
				} else if (funds < 300) {
					player.score = 2;
				} else if (funds < 400) {
					player.score = 3;
				}
			} else if (20 <= getRoundnum()) {
				if (funds == 0) {
					player.score = 0;
				} else if (funds < 200) {
					player.score = 1;
				} else if (funds < 300) {
					player.score = 2;
				} else if (funds < 400) {
					player.score = 3;
				}
			}

		});
	}

}
