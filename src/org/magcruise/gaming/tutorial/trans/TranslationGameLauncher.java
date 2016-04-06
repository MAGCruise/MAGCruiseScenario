package org.magcruise.gaming.tutorial.trans;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.trans.resource.TranslationGameResourceLoader;

public class TranslationGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				TranslationGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// l.runOnExternalProcess();
		launcher.run();

	}

}
