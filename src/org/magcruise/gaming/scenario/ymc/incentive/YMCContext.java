package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.Players;
import org.magcruise.gaming.model.Properties;

public class YMCContext extends Context {

	public String sentence;

	public YMCContext(Properties props, Players players, String scenarioHome) {
		super(props, players, scenarioHome);
	}

}
