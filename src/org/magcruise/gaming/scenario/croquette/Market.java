package org.magcruise.gaming.scenario.croquette;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.Players;

public class Market extends Context {

	public Market(Properties props, Players players, String scenarioHome) {
		super(props, players, scenarioHome);
	}

	public Market(int roundnum, Properties props, History history,
			Players players, String scenarioHome) {
		super(roundnum, props, history, players, scenarioHome);
	}

}
