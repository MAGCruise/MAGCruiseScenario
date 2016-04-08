package org.magcruise.gaming.tutorial.trans_srv;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.trans_srv.resource.TranslationServiceGameResourceLoader;

public class TranslationServiceGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				TranslationServiceGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		launcher.setAutoInputMode(false);
		launcher.run();

	}

}
