package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

public class CroquetteGameOnServerLauncher {

	// private static String brokerUrl =
	// "http://proxy.robin.toho.magcruise.org/MAGCruiseBroker";

	private static String brokerUrl = "http://waseda2.magcruise.org/MAGCruiseBroker";

	public static void main(String[] args) {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.run();
	}

}
