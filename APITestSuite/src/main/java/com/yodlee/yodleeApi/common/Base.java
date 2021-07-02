/*******************************************************************************
 * Copyright (c) 2020 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author kongara.sravan
 ******************************************************************************/
package com.yodlee.yodleeApi.common;

import org.testng.annotations.AfterSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.yodlee.app.ycc.YccApp;
import com.yodlee.app.ycc.YccConf;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeSuite;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.Executors;
import org.testng.annotations.Optional;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.databene.feed4testng.FeedTest;

public class Base extends FeedTest {
	protected Logger logger;
	public static ExecutorService executorThread;
	public static int testFailedCount;
	public static String mailReporter = "<html style=\"background-color:Azure;\"><head><style>table, td, th {border: 4px solid #ddd; text-align: center;}table {border-collapse: collapse;width: 60%;}th, td {padding: 7px;}</style></head><body><h3>Insight Suite Execution Report::</h3></br><table><th>Suite Name</th><th>Number Of Test Cases</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Pass Percentage</th>";
	public static String mailReporterEndTags = "</table></body></html>";

	public Base() {
		this.logger = LoggerFactory.getLogger((Class) Base.class);
	}

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "config_file", "version_name", "hybrid", "getApiVersion", "updateApiVersion",
			"unRegisterUserRequired", "isUserStatic", "isFirstRun", "isStaticComparable", "healthCheckUp",
			"isDiffyEnabled", "authenticationScheme", "releaseName", "app", "dummyResp", "isResponseEncrypted",
			"environment" })
	public void setUpTest(@Optional("Config_L1_Restarts_112_164.Properties") final String configFile,
			@Optional("") final String versionName, @Optional("True") final boolean hybrid,
			@Optional("1.1") final String getApiVersion, @Optional("1.1") final String updateApiVersion,
			@Optional("True") final String unRegisterUserRequired, @Optional("False") final boolean isUserStatic,
			@Optional("True") final String isFirstRun, @Optional("False") final boolean isStaticComparable,
			@Optional("boolean") final boolean healthCheckUp, @Optional("False") final boolean isDiffyEnabled,
			@Optional("Legacy") final String authenticationScheme, @Optional("") final String releaseName,
			@Optional("YSL") String product, @Optional("False") final boolean dummyResp,
			@Optional("False") final boolean isResponseEncrypted, @Optional("Docker") final String environment)
			throws Exception {
		this.logger.info("*********** SETTING UP AUTOMATION TEST ENVIRONMENT ********************");
		if (product == null || product.isEmpty()) {
			product = "YSL";
		}
		System.out.println("product Name:::" + product);
		System.out.println("configFile:::" + configFile);
		System.out.println("==========< authenticationScheme:::" + authenticationScheme);
		System.out.println("isResponseEncrypted:::" + isResponseEncrypted);
		this.mavenDefaultInit(configFile, hybrid, getApiVersion, updateApiVersion, unRegisterUserRequired, isUserStatic,
				isFirstRun, isStaticComparable, healthCheckUp, isDiffyEnabled, authenticationScheme, releaseName,
				product, dummyResp, isResponseEncrypted, environment);
		Initializer.init(null, configFile, product);
		if (product.equalsIgnoreCase("YSL") || product.equalsIgnoreCase("YSL_YCC")) {
			final Configuration config = Configuration.getInstance();
			if (authenticationScheme.equalsIgnoreCase("OAuth")) {
				final Thread t = new Thread((Runnable) new OAuthTokenUpdater());
				t.setDaemon(true);
				t.start();
				System.out.println("=====OAuth token updater thread start========");
			} else if (config.getSecondLevelAuthorizationObj().updateUserSession()) {
				final UserSessionUpdater usUpdater = new UserSessionUpdater(true);
				this.logger.info("Current Thread Name : " + Thread.currentThread().getName());
//                (Base.executorThread = Executors.newFixedThreadPool(1, (ThreadFactory)new Base.Base$1(this))).execute((Runnable)usUpdater);
			}
		}
	}

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "app", "configFile" })
	public void yccInit(@Optional final String app, @Optional final String configFile) throws Exception {
		if (app == null) {
			return;
		}
		if (app.equalsIgnoreCase("YCC") || app.equalsIgnoreCase("YSL_YCC")) {
			YccConf.getInstance().loadConfigFile(configFile, "ycc");
			YccApp.initialize();
			return;
		}
		if (app.equalsIgnoreCase("singularity") || app.equalsIgnoreCase("SING")) {
			YccConf.getInstance().loadConfigFile(configFile, "singularity");
			YccApp.initSingularity();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void shutDownHook() throws IOException {
		this.logger.info("*********** SHUTDOWN HOOK ********************");
		final Configuration configurationObj = Configuration.getInstance();
		try {
			if (!configurationObj.isUserStatic()) {
				if (configurationObj.getCobrandSessionObj().getUserSession() != null) {
				}
				if (configurationObj.getCobrandSessionObj().getCobSession() != null) {
				}
			}
			createandWritetofile("SummaryReporter.HTML", mailReporter + mailReporterEndTags);
			Base.executorThread.shutdown();
			this.logger.info("*********** shutting the job of thread ********************");
		} catch (Exception e) {
			this.logger.info("Exception is::" + e.getMessage());
		}
	}

	private void mavenDefaultInit(String configFile, final boolean hybrid, String getApiVersion,
			String updateApiVersion, String unRegisterUserRequired, final boolean isUserStatic, String isFirstRun,
			final boolean isStaticComparable, final boolean healthCheckUp, final boolean isDiffyEnabled,
			String authenticationScheme, final String releaseName, final String product, final boolean dummyResp,
			final boolean isResponseEncrypted, String environment) {
		if (configFile.isEmpty()) {
			configFile = "Config_L1StableZunmaya.Properties";
		}
		if (getApiVersion.isEmpty()) {
			getApiVersion = "1.1";
		}
		if (updateApiVersion.isEmpty()) {
			updateApiVersion = "1.1";
		}
		if (unRegisterUserRequired.isEmpty()) {
			unRegisterUserRequired = "True";
		}
		if (isFirstRun.isEmpty()) {
			isFirstRun = "True";
		}
		if (authenticationScheme.isEmpty()) {
			authenticationScheme = "Legacy";
		}
		final Configuration configurationObj = Configuration.getInstance();
		configurationObj.setHybrid(hybrid);
		configurationObj.setApiVersion(getApiVersion);
		configurationObj.setUpdateApiVersion(updateApiVersion);
		configurationObj.setUserStatic(isUserStatic);
		configurationObj.setFirstRun(Boolean.parseBoolean(isFirstRun));
		configurationObj.setUnRegisterUserRequired(Boolean.parseBoolean(unRegisterUserRequired));
		configurationObj.setStaticComparable(isStaticComparable);
		configurationObj.setHealthCheckUp(healthCheckUp);
		configurationObj.setDiffyEnabled(isDiffyEnabled);
		configurationObj.setAuthenticationScheme(authenticationScheme);
		configurationObj.setReleaseName(releaseName);
		System.out.println("product:::" + product);
		configurationObj.setProduct(product);
		configurationObj.setDummyEnabled(dummyResp);
		configurationObj.setResponseEncrypted(isResponseEncrypted);
		configurationObj.setEnv(environment);
	}

	static {
		Base.testFailedCount = 0;
	}

	/**
	 * Creates a file "Additional files path" with given filename and writes the
	 * given data into it
	 * 
	 * @param=String name of the file along with the data
	 * @param=String data you want to write in the file
	 */
	public boolean createandWritetofile(String filename, String data) {
		boolean status = false;
		try {
			File file = new File(filename);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
			writer.write(data);
			writer.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
