package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.Players;

public class YMCContext extends Context {

	public YMCContext(Properties props, Players players, String scenarioHome) {
		super(props, players, scenarioHome);
	}

	public YMCContext(int roundnum, Properties props, History history,
			Players players, String scenarioHome) {
		super(roundnum, props, history, players, scenarioHome);
	}

}
