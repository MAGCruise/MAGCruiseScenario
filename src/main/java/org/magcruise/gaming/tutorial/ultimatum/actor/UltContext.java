package org.magcruise.gaming.tutorial.ultimatum.actor;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;

public class UltContext extends Context {

	public final static int providedVal = 100000;

	public UltContext(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	public void paid() {
		UltPlayer bigBear = (UltPlayer) getPlayer(toSymbol("BigBear"));
		UltPlayer smallBear = (UltPlayer) getPlayer(toSymbol("SmallBear"));

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
