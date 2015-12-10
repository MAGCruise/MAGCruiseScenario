package org.magcruise.gaming.scenario.trans;

import org.magcruise.gaming.manager.GameProcessLauncher;
import org.magcruise.gaming.model.def.DefContext;
import org.magcruise.gaming.model.def.DefPlayer;
import org.magcruise.gaming.model.def.DefRound;
import org.magcruise.gaming.model.def.GameBuilder;

import gnu.mapping.SimpleSymbol;

public class TranslationGameLauncher {
	public static void main(String[] args) {

		GameBuilder gb = new GameBuilder();

		gb.addDefContext(new DefContext(TranslationGameContext.class));
		gb.addDefPlayers(
				new DefPlayer(new SimpleSymbol("player1"),
						new SimpleSymbol("human"), TranslationGamePlayer.class),
				new DefPlayer(new SimpleSymbol("player2"),
						new SimpleSymbol("human"), TranslationGamePlayer.class),
				new DefPlayer(new SimpleSymbol("player3"),
						new SimpleSymbol("human"), TranslationGamePlayer.class),
				new DefPlayer(new SimpleSymbol("player4"),
						new SimpleSymbol("human"),
						TranslationGamePlayer.class));

		gb.addDefRounds(new DefRound(""));

		new GameProcessLauncher(gb).runOnCurrentProcess();

	}

}
