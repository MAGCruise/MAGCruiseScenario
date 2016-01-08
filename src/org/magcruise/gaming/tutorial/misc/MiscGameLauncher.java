package org.magcruise.gaming.tutorial.misc;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class MiscGameLauncher {

	public static void main(String[] args) {

		ResourceLoader resourceLoader = new org.magcruise.gaming.tutorial.misc.resource.ResourceLoader();
		// resourceLoader.addGameDefinitionInResource("gui-test.scm");
		// resourceLoader.addGameDefinitionInResource("sample-workflow-game.scm");
		resourceLoader.addGameDefinitionInResource("sample-workflow.scm");
		GameLauncher l = new GameLauncher(resourceLoader);
		l.run();

	}

}
