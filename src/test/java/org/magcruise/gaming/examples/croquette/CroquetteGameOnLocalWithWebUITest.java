package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.sys.DefUIServiceAndRegisterSession;
import org.magcruise.gaming.model.sys.GameOnLocalWithBrokerLauncher;

public class CroquetteGameOnLocalWithWebUITest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	// private static String brokerUrl =
	// "http://proxy.robin.toho.magcruise.org/MAGCruiseBroker";
	private static String brokerUrl = "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker";
	// private static String brokerUrl =
	// "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker";
	// private static String brokerUrl =
	// "http://waseda1.magcruise.org/MAGCruiseBroker";
	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	@Test
	public void testGameOnLocalWithWebUI() {
		GameOnLocalWithBrokerLauncher launcher = new GameOnLocalWithBrokerLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addDefUI(
				new DefUIServiceAndRegisterSession(webUI, loginId, brokerUrl));
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO);
		launcher.useAutoInput(maxAutoResponseTime);
		ProcessId pid = launcher.runAndWaitForFinish();
		CroquetteGameTest.checkResult(pid);
	}

}
