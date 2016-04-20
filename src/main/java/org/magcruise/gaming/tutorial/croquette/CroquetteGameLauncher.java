package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");

		// Path revertCode = launcher.runAndGetRevertCode(3);

		// revertTest(revertCode);
		launcher.useSwingGui();
		launcher.useAutoInput();
		launcher.runOnExternalProcess();
		// launcher.run();
	}

}
