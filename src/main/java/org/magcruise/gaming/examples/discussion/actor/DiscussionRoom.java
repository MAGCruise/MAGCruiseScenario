package org.magcruise.gaming.examples.discussion.actor;

import org.magcruise.gaming.model.game.ActorName;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.ContextParameter;
import org.magcruise.gaming.model.game.message.ScenarioEvent;

public class DiscussionRoom extends Context {

	public static final ActorName JAPANESE_DISCUSSANT = ActorName.of("JapaneseDiscussant");
	public static final ActorName CHINESE_DISCUSSANT = ActorName.of("ChineseDiscussant");

	public DiscussionRoom(ContextParameter contextParameter) {
		super(contextParameter);
	}

	public void sendStart() {
		sendEvent(new ScenarioEvent(getName(), JAPANESE_DISCUSSANT, toSymbol("start-negotiation")));
		sendEvent(new ScenarioEvent(getName(), CHINESE_DISCUSSANT, toSymbol("start-negotiation")));
	}
}
