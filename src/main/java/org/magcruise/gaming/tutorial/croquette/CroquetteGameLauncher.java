package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

import gnu.kawa.io.Path;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");

		// Path revertCode = launcher.runAndGetRevertCode(3);

		// revertTest(revertCode);
		launcher.useSwingGui();
		launcher.setAutoInputMode(true);
		launcher.runOnExternalProcess();
		// launcher.run();
	}

	public static void revertTest(Path revertCode) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");

		launcher.addGameDefinition(revertCode);
		launcher.setAutoInputMode(true);

		launcher.run();

	}

}
