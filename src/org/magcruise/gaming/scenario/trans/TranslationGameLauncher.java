package org.magcruise.gaming.scenario.trans;

import java.nio.file.Paths;

import org.magcruise.gaming.manager.GameProcessLauncher;
import org.magcruise.gaming.model.def.GameBuilder;

public class TranslationGameLauncher {
	public static void main(String[] args) {

		GameProcessLauncher launcher = new GameProcessLauncher(new GameBuilder()
				.setGameDefinition(Paths.get("scenario/trans-2015/trans.scm")));

		// 通常の開発をするならrunOnCurrentProcess()．
		// エラーメッセージがコンソールに表示される．ただし，外部のクラスファイル読み込みは無効．
		// launcher.runOnCurrentProcess();
		launcher.runOnCurrentProcess();

		// 外部のクラスファイル読み込みをするなら，runOnExternalProcess().
		// エラーメッセージは，System.getProperty("java.io.tmpdir")以下のファイルに出力される．
		// launcher.runOnExternalProcess();
	}

}
