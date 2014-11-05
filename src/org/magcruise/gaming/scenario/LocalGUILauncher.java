package org.magcruise.gaming.scenario;

import java.io.File;

import gnu.mapping.Environment;
import kawa.repl;
import kawa.standard.Scheme;

public class LocalGUILauncher implements Runnable {

	private String scenario;
	private boolean isTest = false;
	private File magcCoreHome;

	public LocalGUILauncher(File magcCoreHome, String scenario) {
		this.magcCoreHome = magcCoreHome;
		this.scenario = scenario;
	}

	@Override
	public void run() {
		String testMode = "";
		if (isTest) {
			testMode = "(set! *test-mode* #t)";
		}
		String fwDir = magcCoreHome.getAbsolutePath() + "/framework/";
		fwDir = fwDir.replaceAll("\\\\", "/");

		String[] opt = {
				"--full-tailcalls",
				"--warn-undefined-variable=no",
				"--warn-invoke-unknown-method=no",
				"-e",
				"(begin " + "(load \"" + fwDir + "framework-swing.scm\")"
						+ "(load \"scenarios/" + scenario + "\")" + testMode
						+ "(load \"" + fwDir + "platform/exec-swing.scm\")"
						+ ")", "--" };
		try {
			repl.main(opt);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void setTestMode(boolean flag) {
		this.isTest = flag;
	}

	public void setSize(int width, int height) {
		Scheme.eval("(swing:set-gui " + width + " " + height + ")",
				Environment.getCurrent());
	}

}
