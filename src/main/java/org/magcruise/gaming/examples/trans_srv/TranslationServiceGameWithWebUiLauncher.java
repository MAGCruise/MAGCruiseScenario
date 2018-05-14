package org.magcruise.gaming.examples.trans_srv;

import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;
import org.nkjmlab.util.log4j.LogManager;

public class TranslationServiceGameWithWebUiLauncher {

	protected static Logger log = LogManager.getLogger();

	private static String loginId = "hsymlab-admin";

	private static int maxAutoResponseTime = 5;

	public static void main(String[] args) {
		GameSession session = new GameSession(TranslationServiceGameResourceLoader.class);

		session.useDefaultPublicBrokerAndWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		//session.addGameDefinitionInResource("test-definition.scm");
		//session.setLogConfiguration(Level.INFO);
		session.useAutoInput(maxAutoResponseTime);
		//session.useRoundValidation();
		session.startAndWaitForFinish();
	}

}
