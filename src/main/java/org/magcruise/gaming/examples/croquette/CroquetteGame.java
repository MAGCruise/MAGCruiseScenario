package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnExternalProcess;

public class CroquetteGame {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();
	private static int maxAutoResponseTime = 1;

	public static void main(String[] args) {
		log.info("test {}", 1);
		//GameSession launcher = new GameSession(CroquetteGameResourceLoader.class);
		GameSessionOnExternalProcess launcher = new GameSessionOnExternalProcess(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		// launcher.useSwingGui();
		launcher.useRoundValidation();
		launcher.useAutoInput(maxAutoResponseTime);
		launcher.start();

	}

}
