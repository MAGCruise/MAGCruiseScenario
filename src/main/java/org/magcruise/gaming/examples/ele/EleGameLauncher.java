package org.magcruise.gaming.examples.ele;

import org.magcruise.gaming.examples.ele.resource.EleGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class EleGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(EleGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.setAutoInputMode(true);
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
