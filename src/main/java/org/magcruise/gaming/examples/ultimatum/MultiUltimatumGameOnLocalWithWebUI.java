package org.magcruise.gaming.examples.ultimatum;

import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;
import org.nkjmlab.util.log4j.LogManager;

public class MultiUltimatumGameOnLocalWithWebUI {
	protected static Logger log = LogManager.getLogger();

	private static String loginId = "admin";
	private static int maxAutoResponseTime = 5;

	public static void main(String[] args) {
		GameSession session = new GameSession(UltimatumGameResourceLoader.class);
		session.useDefaultPublicBrokerAndWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("exp-definition.scm");
		session.startAndWaitForFinish();
	}
}
