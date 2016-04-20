package org.magcruise.gaming.tutorial.trans_srv.actor;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.magcruise.gaming.model.game.HistoricalField;

public class TranslationServiceGameContext extends Context {

	@HistoricalField(name = "寄付金(トークン)")
	public volatile int funds = 0;

	public TranslationServiceGameContext(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void beforeRound() {
		funds = 0;
	}

	public void clearing() {
		getPlayers().forEach(p -> {
			TranslationServiceGamePlayer player = (TranslationServiceGamePlayer) p;
			funds += player.investment;
		});

		getPlayers().getPlayers(TranslationServiceGamePlayer.class).forEach(player -> {

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
				} else {
					player.score = 4;
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
				} else {
					player.score = 4;
				}
			}

		});
	}

}
