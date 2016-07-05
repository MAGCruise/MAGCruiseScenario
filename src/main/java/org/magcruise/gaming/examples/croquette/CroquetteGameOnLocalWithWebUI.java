package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameSession;

public class CroquetteGameOnLocalWithWebUI {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker";
	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	public static void main(String[] args) {
		GameSession launcher = new GameSession(
				CroquetteGameResourceLoader.class);
		launcher.setBroker(brokerUrl);
		launcher.setWebUI(webUI, loginId, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		launcher.start();
	}
}
