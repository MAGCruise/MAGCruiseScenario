package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;

public class CroquetteGameOnServerWithWebUI {

	private static String brokerUrl = "http://localhost:8080/magcruise-broker";
	private static String webUI = "http://game.magcruise.org/world/BackendAPIService";
	private static String loginId = "reiko";

	public static void main(String[] args) {
		GameSessionOnServer session = new GameSessionOnServer(CroquetteGameResourceLoader.class);
		session.setBrokerUrl(brokerUrl);
		session.setWebUI(webUI, loginId, brokerUrl);

		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.startAndWaitForFinish();
	}

}
