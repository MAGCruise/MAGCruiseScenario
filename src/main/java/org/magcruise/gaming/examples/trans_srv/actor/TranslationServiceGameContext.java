package org.magcruise.gaming.examples.trans_srv.actor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.nkjmlab.util.lang.MessageUtils;

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
		funds += getPlayers(TranslationServiceGamePlayer.class).values().stream()
				.mapToInt(p -> p.investment).sum();

		getPlayers(TranslationServiceGamePlayer.class).forEach(player -> {
			calcRightOfUse(player);
			calcScore(player);
		});
	}

	private void calcScore(TranslationServiceGamePlayer player) {
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
	}

	private void calcRightOfUse(TranslationServiceGamePlayer player) {
		if (getRoundnum() <= 10) {
			player.rightOfUse = 200;
			return;
		}
		player.rightOfUse = (int) (funds * 2 / players.size());
	}

	public synchronized List<TranslationServiceGamePlayer> getRanking() {
		return getPlayers(TranslationServiceGamePlayer.class).values().stream()
				.sorted(Comparator.comparingInt(TranslationServiceGamePlayer::getAccount)
						.reversed())
				.collect(Collectors.toList());
	}

	public synchronized String getRankingStr() {
		List<TranslationServiceGamePlayer> players = getRanking();
		String table = "<div class='table-responsive'><table class='table table-bordered table-striped table-condensed'>";
		table += "<tr><th>Rank</th><th>Name</th><th>Account</th></tr>";
		for (int i = 0; i < players.size(); i++) {
			TranslationServiceGamePlayer p = players.get(i);
			table += MessageUtils.format("<tr><td>{}</td><td>{}</td><td>{}</td><tr>", i + 1,
					p.getName().toString(), p.getAccount());
		}
		table += "</table></div>";
		return table;
	}

}
