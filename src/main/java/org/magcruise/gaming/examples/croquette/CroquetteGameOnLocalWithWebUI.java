package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class CroquetteGameOnLocalWithWebUI {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://game.magcruise.org/magcruise-broker";
	private static String webUI = "http://game.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	public static void main(String[] args) {
		GameSession session = new GameSession(CroquetteGameResourceLoader.class);
		session.setBrokerUrl(brokerUrl);
		session.setWebUI(webUI, loginId, brokerUrl);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.useAutoInput();

	}

}
