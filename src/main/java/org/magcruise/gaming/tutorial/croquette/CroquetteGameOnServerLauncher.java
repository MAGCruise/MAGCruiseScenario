package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameOnServerLauncher;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

public class CroquetteGameOnServerLauncher {

	public static void main(String[] args) {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class,
				"http://localhost:8080/MAGCruiseBroker");
		launcher.addJarOnWeb(
				"https://www.dropbox.com/s/gzyxtkqmead2f50/MAGCruiseScenario.jar?dl=1");
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.useSwingGui();
		// launcher.useAutoInput();
		launcher.run();
	}

}
