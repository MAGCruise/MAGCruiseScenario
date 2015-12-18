package org.magcruise.gaming.tutorial.scenario.trans;

import org.magcruise.gaming.developer.DeveloperTool;

public class TranslationGameDeveloperTool extends DeveloperTool {
	public static void main(String[] args) {
		DeveloperTool l = new TranslationGameDeveloperTool();

		// System.out.println(l.getRevertCodePath(4));
		// l.testTerminateAndRevert(7);
		// l.run();
		l.runWithBootstrap();
	}

}
