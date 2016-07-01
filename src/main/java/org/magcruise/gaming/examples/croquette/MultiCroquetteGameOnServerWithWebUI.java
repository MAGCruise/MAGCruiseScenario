package org.magcruise.gaming.examples.croquette;

import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.def.sys.DefUIServiceAndRegisterSession;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

import gnu.mapping.Symbol;
import jp.go.nict.langrid.repackaged.net.arnx.jsonic.JSONException;
import net.arnx.jsonic.JSON;

public class MultiCroquetteGameOnServerWithWebUI {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://waseda2.magcruise.org/MAGCruiseBroker";
	// private static String webUI =
	// "http://toho.magcruise.org/world/BackendAPIService";
	private static String webUI = "http://waseda2.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";
	private static int maxAutoResponseTime = 30;

	public static void main(String[] args) throws JSONException, IOException {

		Setting[] ss = JSON.decode(CroquetteGameResourceLoader.class
				.getResourceAsStream("settings.json"), Setting[].class);

		for (int i = 0; i < ss.length; i++) {
			Setting s = ss[i];
			GameOnServerLauncher launcher = new GameOnServerLauncher(
					CroquetteGameResourceLoader.class, brokerUrl);
			launcher.addDefUI(new DefUIServiceAndRegisterSession(webUI, loginId,
					brokerUrl));
			launcher.addGameDefinitionInResource("game-definition.scm");
			launcher.addGameDefinitionInResource("exp-definition.scm");
			launcher.setLogConfiguration(Level.INFO, true);
			launcher.build();
			launcher.getGameBuilder().addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					Arrays.asList(Symbol.parse(s.userIds.get(0)),
							Symbol.parse(s.userIds.get(1)),
							Symbol.parse(s.userIds.get(2))));
			launcher.useAutoInput(maxAutoResponseTime);
			launcher.run();
		}
	}
}
