package org.magcruise.gaming.examples.misc;

import org.magcruise.gaming.examples.misc.resource.MiscGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class MiscGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(MiscGameResourceLoader.class);
		// launcher.addGameDefinitionInResource("sample-workflow.scm");
		launcher.addGameDefinitionInResource("gui-test.scm");
		// launcher.addGameDefinitionInResource("sample-workflow-game.scm");
		launcher.run();

	}

}
