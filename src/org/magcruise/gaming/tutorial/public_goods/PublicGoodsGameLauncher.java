package org.magcruise.gaming.tutorial.public_goods;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.public_goods.resource.PublicGameResourceLoader;

public class PublicGoodsGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				PublicGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.runOnExternalProcess();
		launcher.run();

	}

}
