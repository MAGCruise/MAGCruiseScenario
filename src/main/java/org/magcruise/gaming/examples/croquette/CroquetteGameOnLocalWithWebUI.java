package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;
import org.nkjmlab.util.log4j.LogManager;

public class CroquetteGameOnLocalWithWebUI {
	protected static org.apache.logging.log4j.Logger log = LogManager.getLogger();

	private static String loginId = "admin";
	private static int maxAutoResponseTime = 5;

	public static void main(String[] args) {
		GameSession session = new GameSession(CroquetteGameResourceLoader.class);
		session.useDefaultPublicBrokerAndWebUI(loginId);
		//session.useDefaultLocalBrokerAndWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		//session.addGameDefinitionInResource("play-definition.scm");
		//session.addGameDefinitionInResource("play-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.useAutoInput(maxAutoResponseTime);
		session.setLogConfiguration(Level.ERROR, true);
		session.startAndWaitForFinish();
	}

}
