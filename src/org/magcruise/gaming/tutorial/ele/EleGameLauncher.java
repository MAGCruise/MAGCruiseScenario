package org.magcruise.gaming.tutorial.ele;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.ele.resource.EleGameResourceLoader;

public class EleGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(EleGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
