package org.magcruise.gaming.examples.discussion.actor;

import org.magcruise.gaming.model.game.ActorName;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.magcruise.gaming.model.game.message.ScenarioEvent;

public class DiscussionRoom extends Context {

	private static final ActorName PLAYER_1 = ActorName.of("Player1");
	private static final ActorName PLAYER_2 = ActorName.of("Player2");

	public DiscussionRoom(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void sendStart() {
		sendEvent(new ScenarioEvent(getName(), PLAYER_1, toSymbol("start-negotiation")));
		sendEvent(new ScenarioEvent(getName(), PLAYER_2, toSymbol("start-negotiation")));
	}
}
