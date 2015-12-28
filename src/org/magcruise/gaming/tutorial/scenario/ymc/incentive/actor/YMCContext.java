package org.magcruise.gaming.tutorial.scenario.ymc.incentive.actor;

import org.magcruise.gaming.langrid.AccessConfigFactory;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;
import org.magcruise.gaming.tutorial.scenario.trans.resource.ResourceLoader;

public class YMCContext extends Context {

	public YMCContext(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	public void initialize() {
		AccessConfigFactory
				.setPath(new ResourceLoader().getResource("langrid-conf.json"));
	}

}
