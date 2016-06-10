package org.magcruise.gaming.examples.croquette.resource;

import org.magcruise.gaming.lang.SConstructive;
import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.def.stage.DefStage;
import org.magcruise.gaming.model.game.Context;

public class SampleFilter implements SConstructive {

	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	public void initBeforeFilter(Context ctx, DefStage defStage) {
		log.debug(defStage);
	}

	public void initAfterFilter(Context ctx, DefStage defStage) {
		log.debug(defStage);
	}

	@Override
	public SConstructor<? extends SampleFilter> toConstructor() {
		return SConstructor.toConstructor(this.getClass());
	}

}
