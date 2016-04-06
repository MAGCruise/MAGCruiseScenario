package org.magcruise.gaming.tutorial.ultimatum;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.ultimatum.resource.UltimatumGameResourceLoader;

public class UltimatumGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				UltimatumGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.runOnExternalProcess();
		launcher.run();

	}

}
