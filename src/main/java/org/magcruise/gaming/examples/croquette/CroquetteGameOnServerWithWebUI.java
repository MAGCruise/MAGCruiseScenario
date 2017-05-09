package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;

public class CroquetteGameOnServerWithWebUI {

	private static String loginId = "admin";

	public static void main(String[] args) {
		GameSessionOnServer session = new GameSessionOnServer(CroquetteGameResourceLoader.class);
		session.useDefaultPublicBroker();
		session.useDefaultPublicWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("exp-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.startAndWaitForFinish();
	}

}
