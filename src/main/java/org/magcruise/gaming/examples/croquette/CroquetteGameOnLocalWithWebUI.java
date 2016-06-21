package org.magcruise.gaming.examples.croquette;

import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.def.sys.DefUIServiceAndRegisterSession;
import org.magcruise.gaming.model.sys.GameOnLocalWithSeverLauncher;

public class CroquetteGameOnLocalWithWebUI {
	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://proxy.robin.toho.magcruise.org/MAGCruiseBroker";
	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	public static void main(String[] args) {
		GameOnLocalWithSeverLauncher launcher = new GameOnLocalWithSeverLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addDefUI(
				new DefUIServiceAndRegisterSession(webUI, loginId, brokerUrl));
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.useAutoInput();
		launcher.run();
	}
}
