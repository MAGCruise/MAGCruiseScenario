package org.magcruise.gaming.examples.ymc.incentive.actor;

import org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader;
import org.magcruise.gaming.langrid.AccessConfigFactory;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;

public class YMCContext extends Context {

	public YMCContext(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void initialize() {
		AccessConfigFactory
				.setPath(new TranslationServiceGameResourceLoader()
						.getResource("langrid-conf.json"));
	}

}
