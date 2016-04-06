package org.magcruise.gaming.tutorial.ymc.incentive.actor;

import org.magcruise.gaming.langrid.AccessConfigFactory;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;
import org.magcruise.gaming.tutorial.trans.resource.TranslationGameResourceLoader;

public class YMCContext extends Context {

	public YMCContext(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	public void initialize() {
		AccessConfigFactory
				.setPath(new TranslationGameResourceLoader().getResource("langrid-conf.json"));
	}

}
