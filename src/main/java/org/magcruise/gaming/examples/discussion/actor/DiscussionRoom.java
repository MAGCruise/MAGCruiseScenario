package org.magcruise.gaming.examples.discussion.actor;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.magcruise.gaming.model.game.message.ScenarioEvent;

public class DiscussionRoom extends Context {

	public DiscussionRoom(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void sendStart() {
		getPlayers().forEach(player -> {
			sendEvent(
					new ScenarioEvent(getName(), player.getName(), toSymbol("start-negotiation")));
		});

	}
}
