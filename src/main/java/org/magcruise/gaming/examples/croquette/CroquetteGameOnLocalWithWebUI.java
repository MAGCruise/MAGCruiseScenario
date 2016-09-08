package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class CroquetteGameOnLocalWithWebUI {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String loginId = "admin";

	public static void main(String[] args) {
		GameSession session = new GameSession(CroquetteGameResourceLoader.class);
		session.useDefaultPublicWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("play-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		//session.useAutoInput();
		session.startAndWaitForFinish();
	}

}
