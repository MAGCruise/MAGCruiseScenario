package org.magcruise.gaming.examples.ultimatum.actor;

import org.jsoup.nodes.Element;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.nkjmlab.util.html.Tags;

public class UltimatumGameContext extends Context {

	public final static int providedVal = 100000;

	public UltimatumGameContext(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void paid() {
		UltimatumPlayer firstPlayer = (UltimatumPlayer) getPlayer(FirstPlayer.FIRST_PLAYER);
		UltimatumPlayer secondPlayer = (UltimatumPlayer) getPlayer(SecondPlayer.SECOND_PLAYER);

		secondPlayer.proposition = firstPlayer.proposition;
		firstPlayer.yesOrNo = secondPlayer.yesOrNo;

		if (secondPlayer.yesOrNo == Response.YES) {
			firstPlayer.paid(providedVal - firstPlayer.proposition);
			secondPlayer.paid(firstPlayer.proposition);
		} else if (secondPlayer.yesOrNo == Response.NO) {
			firstPlayer.paid(0);
			secondPlayer.paid(0);
		} else {
			log.error("Yes or no of SecondPlayer has error.");
		}
		Element e = Tags.div(Tags.ruby("交渉", "こうしょう"), "が",
				"終わりました．", Tags.ul(Tags.li("おおぐま君は" + firstPlayer.acquisition + "円を手に入れました"),
						Tags.li("おおぐま君は" + secondPlayer.acquisition + "円を手に入れました")))
				.attr("class", "alert alert-success");

		showMessageToAll(e.toString());
	}

}
