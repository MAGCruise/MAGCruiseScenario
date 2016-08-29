package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;

public class CroquetteGameOnServerWithWebUI {

	private static String brokerHost = "localhost:8080";
	private static String loginId = "reiko";

	public static void main(String[] args) {
		GameSessionOnServer session = new GameSessionOnServer(CroquetteGameResourceLoader.class);
		session.setBrokerHost(brokerHost);
		session.useDefaultPublicWebUI(loginId);

		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.startAndWaitForFinish();
	}

}
