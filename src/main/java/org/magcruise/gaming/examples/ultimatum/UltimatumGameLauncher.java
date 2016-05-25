package org.magcruise.gaming.examples.ultimatum;

import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class UltimatumGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				UltimatumGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		launcher.useSwingGui();
		// launcher.runOnExternalProcess();
		launcher.useAutoInput();
		launcher.run();

	}

}
