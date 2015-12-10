package org.magcruise.gaming.scenario.trans;

import java.nio.file.Paths;

import org.magcruise.gaming.manager.GameProcessLauncher;
import org.magcruise.gaming.model.def.GameBuilder;

public class TranslationGameLauncher {
	public static void main(String[] args) {

//		GameBuilder gb = new GameBuilder();
//
//		gb.addDefContext(new DefContext(TranslationGameContext.class));
//		gb.addDefPlayers(
//				new DefPlayer("player1", "human", TranslationGamePlayer.class),
//				new DefPlayer("player2", "human", TranslationGamePlayer.class),
//				new DefPlayer("player3", "human", TranslationGamePlayer.class),
//				new DefPlayer("player4", "human", TranslationGamePlayer.class));
//
//		gb.addDefRounds(new DefRound(new DefSequentialStage(
//				new DefPlayerTask("player1", "decide"))));
//		gb.addDefRounds(SExpressionUtils.eval("(def:round" + "(def:stage"
//				+ "(def:players-task (builder:getPlayerNames) 'initialize)))"));
//		new GameProcessLauncher(gb).runOnCurrentProcess();

		GameProcessLauncher launcher = new GameProcessLauncher(new GameBuilder()
				.setGameDefinition(Paths.get("scenario/trans-2015/trans.scm")));
		launcher.runOnCurrentProcess();

	}

}
