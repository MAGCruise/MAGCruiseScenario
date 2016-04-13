package org.magcruise.gaming.tutorial.misc;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.misc.resource.MiscGameResourceLoader;

public class MiscGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(MiscGameResourceLoader.class);
		//launcher.addGameDefinitionInResource("sample-workflow.scm");
		// launcher.addGameDefinitionInResource("gui-test.scm");
		 launcher.addGameDefinitionInResource("sample-workflow-game.scm");
		launcher.run();

	}

}
