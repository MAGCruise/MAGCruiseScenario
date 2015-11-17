package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.History;
import org.magcruise.gaming.model.game.MessageBox;
import org.magcruise.gaming.model.game.Players;

public class YMCContext extends Context {

	public YMCContext(int roundnum, Properties props, History history,
			MessageBox msgbox, Players players, String scenarioHome) {
		super(roundnum, props, history, msgbox, players, scenarioHome);
	}

}
