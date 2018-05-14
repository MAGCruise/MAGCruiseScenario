package org.magcruise.gaming.examples.trans_srv.actor2017;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.nkjmlab.util.lang.MessageUtils;

public class TranslationServiceGameContext extends Context {

	@HistoricalField(name = "寄付/投資総額")
	public volatile int funds = 0;
	private int lastRoundNumberOfDonationGame = 5;

	public TranslationServiceGameContext(ContextParameter contextParameter) {
		super(contextParameter);
	}

	private boolean isDonationGameRound() {
		return getRoundnum() <= lastRoundNumberOfDonationGame;
	}

	public void beforeRound() {
		funds = 0;
	}

	public void clearing() {
		funds += getPlayers(TranslationServiceGamePlayer.class).values().stream()
				.mapToInt(p -> p.investment).sum();

		getPlayers(TranslationServiceGamePlayer.class).forEach(player -> calcRightOfUse(player));
	}

	private void calcRightOfUse(TranslationServiceGamePlayer player) {
		if (isDonationGameRound()) {
			player.setRightOfUse(200);
		} else {
			player.setRightOfUse((int) (funds * 2 / players.size()));
		}
	}

	public synchronized List<TranslationServiceGamePlayer> getRanking() {
		return getPlayers(TranslationServiceGamePlayer.class).values().stream()
				.sorted(Comparator.comparingInt(TranslationServiceGamePlayer::getLevel)
						.reversed())
				.collect(Collectors.toList());
	}

	public synchronized String getRankingHtml() {
		List<TranslationServiceGamePlayer> players = getRanking();
		String table = "<div class='table-responsive'><table class='table table-bordered table-striped table-condensed'>";
		table += "<tr><th>Rank</th><th>Name</th><th>Level</th></tr>";
		for (int i = 0; i < players.size(); i++) {
			TranslationServiceGamePlayer p = players.get(i);
			table += MessageUtils.format("<tr><td>{}</td><td>{}</td><td>{}</td><tr>", i + 1,
					p.getName().toString(), p.getLevel());
		}
		table += "</table></div>";
		return table;
	}

}
