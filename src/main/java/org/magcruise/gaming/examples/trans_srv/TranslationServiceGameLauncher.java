package org.magcruise.gaming.examples.trans_srv;

import org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class TranslationServiceGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				TranslationServiceGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		launcher.useSwingGui();
		launcher.run();

	}

}
