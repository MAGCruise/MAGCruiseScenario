package org.magcruise.gaming.examples.ele;

import org.magcruise.gaming.examples.ele.resource.EleGameResourceLoader;
import org.magcruise.gaming.model.sys.GameSession;

public class EleGameLauncher {

	public static void main(String[] args) {
		GameSession launcher = new GameSession(EleGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.setAutoInputMode(true);
		// launcher.runOnExternalProcess();
		launcher.start();
	}

}
