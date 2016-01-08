package org.magcruise.gaming.tutorial.public_goods;

import org.magcruise.gaming.model.sys.GameLauncher;

public class PublicGoodsGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.public_goods.resource.ResourceLoader());
		// l.runOnExternalProcess();
		l.run();

	}

}
