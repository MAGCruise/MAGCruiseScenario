package org.magcruise.gaming.tutorial.trans;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class TranslationGameLauncher {

	public static void main(String[] args) {
		ResourceLoader loader = new org.magcruise.gaming.tutorial.trans.resource.ResourceLoader();
		loader.setBootstrapInResource("bootstrap.scm");
		GameLauncher launcher = new GameLauncher(loader);
		// l.runOnExternalProcess();
		launcher.run();

	}

}
