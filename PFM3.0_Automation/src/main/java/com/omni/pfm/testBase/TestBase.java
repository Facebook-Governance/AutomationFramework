/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.testBase;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.databene.feed4testng.FeedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import com.accessibility.HtmlReportSupport;
import com.omni.pfm.Extent.ExtentManager;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.DataConstants;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.rest.RestUtil;
import com.omni.pfm.utility.*;
import com.omni.pfm.webdriver.Browser;
import com.omni.pfm.webdriver.WebDriverFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import testcases.HealthCheckMain;

/**
 * Pilot class for all other test classes. Every test class must extend this
 * class.
 * 
 * @author Suhaib
 *
 */
public class TestBase extends FeedTest {
	private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
	public static Config config;
	public static String cobrandName;
	public static String openBankingEnabled = null;
	public static ImageCompareUtil imageprocess;
	public static String screenShotEnabledFlag;
	public static String loginParameter;
	@SuppressWarnings("rawtypes")
	protected static AppiumDriver d;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest tc;
	public static String appFlag;

	@SuppressWarnings("rawtypes")
	@BeforeSuite(alwaysRun = true)
	@Parameters({ "coBrand", "browser", "runOnBrowserStack", "bs_username", "bs_automate_key", "browser_version",
			"platformName", "platformVersion", "elpamEnabled", "envFileName", "csvName", "screenShotComparisionEnabled",
			"platform", "healthCheckUp", "appFlag", "mobileBuildName", "loginType", "deviceName", "isOpenBanking" })
	public void setUp(String coBrand, @Optional("chrome") String browserName,
			@Optional("false") String runOnBrowserStack, @Optional("") String bs_username,
			@Optional("") String bs_automate_key, @Optional("") String browser_version,
			@Optional("windows") String platformName, @Optional("8") String platformVersion,
			@Optional("true") String elpamEnabled, @Optional("") String envFileName, @Optional("") String csvName,
			@Optional("false") String screenShotComparisionEnabled, @Optional("") String platform,
			@Optional("false") String healthCheckUp, String appFlag, @Optional("") String mobileBuildName,
			@Optional("") String loginType, @Optional("") String deviceName, @Optional("false") String isOpenBanking,
			ITestContext context) throws Exception {

		logger.info("==> Entering TestBase.setUp()");
		openBankingEnabled = isOpenBanking;

		config = Config.createInstance(coBrand, appFlag);

		Config.setAppFlag(appFlag);

		TestBase.appFlag = appFlag;
		 //TODO 
		loginParameter = loginType;
		cobrandName = coBrand;
		logger.info("==============> coBrand =" + coBrand);

		extent = ExtentManager.Instance();
		extent.addSystemInfo("Cobrand", coBrand);
		extent.addSystemInfo("RunOnBrowserStack", runOnBrowserStack);
		extent.addSystemInfo("Browser", browserName + browser_version);
		extent.addSystemInfo("Environment", platform);
		extent.addSystemInfo("LoginType", loginType);
		extent.addSystemInfo("HealthCheckUp", healthCheckUp);
		extent.addSystemInfo("AppFlag", appFlag);
		extent.addSystemInfo("DeviceName", deviceName);

		imageprocess = new ImageCompareUtil(browserName, platformName);
		screenShotEnabledFlag = screenShotComparisionEnabled;

		// this is for log file names
		MDC.put("file_name", coBrand);

		// System.getProperties().put("http.proxyHost", "192.168.215.4");
		// System.getProperties().put("http.proxyPort", "8080");

		logger.info(
				"Parameters from testng xml : env = {}, browser = {}, RunOnBrowserStack = {}, bs_username = {},"
						+ " bs_automate_key = {}, browser_version = {}, os = {}, os_version = {}, ElpamEnabled = {}, "
						+ "EnvFileName = {}, CsvName = {}",
				coBrand, browserName, runOnBrowserStack, bs_username, bs_automate_key, browser_version, platformName,
				platformVersion, elpamEnabled, envFileName, csvName);

		if ("CHROME".equalsIgnoreCase(browserName)) {
			File file = new File("src/main/resources/drivers/chrome/chromedriver.exe");
			System.out.println(file.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		}

		if (healthCheckUp.equalsIgnoreCase("true")) {
			healthCheckPfm(context);
		}

		Browser browser = new Browser();

		switch (appFlag.toLowerCase()) {
		case "app":
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appPath = new File(classpathRoot, "/Builds/" + mobileBuildName);

			logger.info("*********MOBILE RUN AND APP PATH IS**********" + appPath);

			if (platformName.equalsIgnoreCase("Android")) {
				browser.AppInitialize(platformName, platformVersion, "Selendroid", appPath, "Android", "com.fi",
						".FIBaseActivity");
			} else if (platformName.equalsIgnoreCase("iOS")) {
				browser.AppInitialize(platformName, platformVersion, "appium", appPath, "iPhone 6");
			}
			break;
		case "mobilebrowser":
			browser.MobileBrowserInitialize("chrome", "Android", "5.0.1", "Android", appFlag);
			break;
		case "emulator":
			if (!isSeleniumServerUpInLocal(4723)) {
				if (!startSeleniumServer(4723)) {
					Assert.fail("Selenium server is down");
				}
			}
			browser.MobileEmulatorBrowserInitialize("chrome", "Android", "5.0.1", deviceName, appFlag,
					runOnBrowserStack);
			break;
		default:
			/*
			 * if (!isSeleniumServerUpInLocal(4723)) { if (!startSeleniumServer(4723)) {
			 * Assert.fail("Selenium server is down"); } }
			 */
			TestBase.appFlag = "Web";
			browser.BrowserInitialize(browserName, browser_version, platformName, platformVersion, runOnBrowserStack,
					elpamEnabled, bs_username, bs_automate_key, appFlag);
			break;
		}

		try {
			d = (AppiumDriver) WebDriverFactory.getInstance(browser);
			context.setAttribute("driver", d);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (browser.isElpamEnabled()) { // execute
			String url = null;
			if (config.dataMap.containsKey(DataConstants.ENVIRONMENT_MAP)) {
				if (config.dataMap.get(DataConstants.ENVIRONMENT_MAP).containsKey("cnf_SAML_LOGIN")) {
					String samlEnabled = config.dataMap.get(DataConstants.ENVIRONMENT_MAP).get("cnf_SAML_LOGIN");
					logger.info("SAML_LOGIN STATUS IS : {}", samlEnabled);
					if ("yes".equalsIgnoreCase(samlEnabled.trim())) {
						url = PropsUtil.getEnvPropertyValue("SAML_BASE_URL");
						logger.info("SAML BASE URL IS : {}", url);
					} else {
						url = PropsUtil.getEnvPropertyValue("cnf_base_url");
						logger.info("Base url is : {}", url);
					}
				} else {
					logger.error("cnf_SAML_LOGIN not present in environment map : {}",
							config.dataMap.get(DataConstants.ENVIRONMENT_MAP));
				}
			} else {
				logger.error("Environment map not found in dataMap : {}", config.dataMap);
			}
			if (url != null && !url.trim().equals("")) {

//				d.get(url);
				try {
					d.manage().window().maximize();
				} catch (Exception er) {
				}
			} else {
				logger.info("URL is null : Cannot proceed : {}", url);
			}
		}

		/*
		 * Added for Categorization key: Set to be 'FALSE' for AutoNPR environment.
		 */
		if (SeleniumUtil.getEnvironmetValue("MeerkatValueChange").equalsIgnoreCase("yes")) {

			logger.info("Enabling the TDE keys: Meerkat[param_acl_id=7373], Category merger[param_key_id=6294].");

			try {
				String fileName = null;
				if (Config.getInstance().getEnvironment().toUpperCase().contains("NPR")) {
					// YCOM_AutoNPR_PFM.Properties
					fileName = Config.getInstance().getEnvironment().toUpperCase() + "_"
							+ PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase() + ".Properties";
				}
				RegisterUser.LoadParameters(fileName);

				/* 6294 and 7373 keys should be FALSE for Categorization Rule */
				RestUtil.updateCobrandACLValue("GENERAL_MEERKAT_BASED_CATEGORIZATION_ENABLED", "FALSE");
				RestUtil.updateCobParamValue("com.yodlee.categorization.refine.categories.enabled", "FALSE");

			} catch (Exception e) {
				e.printStackTrace();
				logger.info("The Meerkat based Categorization is not enabled");
			}

		}

		logger.info("<== Exiting TestBase.setUp()");
	}

	protected void doInitialization(String message) {
		test = extent.startTest(message);
	}

	@AfterClass(alwaysRun = true)
	protected void writeClassToReport() {
		extent.endTest(test);
		extent.flush();
		String f = ExtentManager.getExtentFileName();
		System.out.println("Extent Report File Name = " + f);
		if (f != null) {
			File file = new File(f);
			try {
				String latestFile = Config.getInstance().getEnvironment().toUpperCase() + "_LatestReport.html";
				FileUtils.copyFile(file, new File("./ExtentReports/" + latestFile));
			} catch (IOException e) {
				logger.error("Error copying the extent report file");
				e.printStackTrace();
			}
		}
	}

	// @AfterMethod
	public void writeTestToReport() {
		if (tc.getTest().getStatus().equals(LogStatus.UNKNOWN)) {
			tc.getTest().setStatus(LogStatus.FAIL);
		}
		extent.endTest(tc);
		extent.flush();
		SeleniumUtil.SwitchToCurrentTab(d);
	}

	@AfterSuite(alwaysRun = true)
	public void shutDown() {
		accessibility();
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").contentEquals("WEB")) {
			extent.close();

			d.close();
			d.quit();

			Config.setInstanceToNull();
		}
		System.out.println("========================> Suite finished");
	}

	private void accessibility() {
		try {
			HtmlReportSupport.createHtmlSummaryReport("chrome", "L1");
			HtmlReportSupport.closeSummaryReport();
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@SuppressWarnings("rawtypes")
	public static AppiumDriver getDriver() {
		return d;
	}

	public static void healthCheckPfm(ITestContext context) throws Exception {
		logger.info("<== Start health Check up");
		HealthCheckMain healthCheckMain = new HealthCheckMain();
		PropsUtil.init();
		Map<String, String> healthCheckresultMap = healthCheckMain.verifyAllComponentStatus(PropsUtil.componentMap);
		context.setAttribute("returnVal", healthCheckresultMap);

		boolean status = Boolean.parseBoolean(healthCheckresultMap.get("Status"));
		logger.info("status:" + status);

		String reason = healthCheckresultMap.get("Reason");
		logger.info("Reason:" + reason);

		Assert.assertTrue(status, reason);
		logger.error("Environment health check failed");
		throw new SkipException("Environment health check failed:" + reason);

	}

	public boolean isSeleniumServerUpInLocal(int port) {
		try {
			URL url = new URL("http://localhost:" + port + "/wd/hub");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int respCode = con.getResponseCode();
			if (respCode == HttpURLConnection.HTTP_OK) {
				return true;
			}
		} catch (ConnectException e) {
			logger.info("Selenium server is down");
		} catch (Exception e) {
			logger.error("Unable to check selenium server is up or not due to " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean startSeleniumServer(int port) {
		try {
			WebDriverManager.chromedriver().setup();
			WebDriverManager.firefoxdriver().setup();
			String pathSeparator = File.separator;
			File batchFilePath = new File("src" + pathSeparator + "main" + pathSeparator + "resources" + pathSeparator
					+ "drivers" + pathSeparator + "chrome" + pathSeparator + "chromeRun.bat");
			String command = "cmd /c  start " + "src" + pathSeparator + "main" + pathSeparator + "resources" + pathSeparator
					+ "drivers" + pathSeparator + "chrome" + pathSeparator + "chromeRun.bat" + " " + port + " "
					+ System.getProperty("webdriver.chrome.driver") + " "
					+ System.getProperty("webdriver.gecko.driver")+"";
			Runtime.getRuntime()
					.exec(command);
			for (int i = 0; i < 60; i++) {
				if (isSeleniumServerUpInLocal(port)) {
					System.out.println("Selenium server is up and running");
					return true;
				} else {
					System.out.println("Waiting for selenium server to come up " + i + " seconds");
					Thread.sleep(1000);
				}
				System.err.println("Selenium server is not coming up please check !!!!");
			}
		} catch (Exception e) {
			System.err.println("Unable to start selenium server due to " + e.getMessage());
		}
		return false;
	}

}
