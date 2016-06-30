package org.magcruise.gaming.examples.ultimatum.actor;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;

public class UltimatumGameContext extends Context {

	public final static int providedVal = 100000;

	public UltimatumGameContext(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void paid() {
		UltimatumPlayer bigBear = (UltimatumPlayer) getPlayer(
				toActorName("BigBear"));
		UltimatumPlayer smallBear = (UltimatumPlayer) getPlayer(
				toActorName("SmallBear"));

		smallBear.proposition = bigBear.proposition;
		bigBear.yesOrNo = smallBear.yesOrNo;

		if (smallBear.yesOrNo.equals("yes")) {
			bigBear.paid(providedVal - bigBear.proposition);
			smallBear.paid(bigBear.proposition);
		} else {
			bigBear.paid(0);
			smallBear.paid(0);
		}
		showMessageToAll(applyProcedure("makeResultMessage",
				bigBear.acquisition, smallBear.acquisition).toString());
	}

}
