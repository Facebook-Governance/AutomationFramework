/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.webdriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * Factory to instantiate a WebDriver object. It returns an instance of the driver (local invocation) or an instance of RemoteWebDriver
 *
 * @author Ashish Sadhwani
 */
public class WebDriverFactory {
	/* Browsers constants */
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String OPERA = "opera";
	public static final String INTERNET_EXPLORER = "ie";
	public static final String SAFARI = "safari";
	public static final String HTML_UNIT = "htmlunit";
	public static final String IPHONE = "iphone";
	/* Platform constants */
	public static final String WINDOWS = "windows";
	public static final String ANDROID = "android";
	public static final String XP = "xp";
	public static final String VISTA = "vista";
	public static final String MAC = "mac";
	public static final String LINUX = "linux";
	
	/* Device Name Contstants */
	File classpathRoot = new File(System.getProperty("user.dir"));
	File appDir = new File(classpathRoot, "/Builds");
	static WebDriver webDriver;

	@SuppressWarnings("rawtypes")
	private static AppiumDriver driver;

	public static final String USERNAME = "yodleebrowsersta1";
	public static final String AUTOMATE_KEY = "pzyoTJ6embZ48zsufsF2";
	public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com:4444/wd/hub";

	@SuppressWarnings("rawtypes")
	public static AppiumDriver getInstance(Browser browser) throws MalformedURLException {
		System.setProperty("webdriver.gecko.driver", "c:/geckodriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (browser.getAppPath() != null && browser.getPlatformName().equalsIgnoreCase("Android")) {

			/*
			 * capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
			 * "Selendroid"); capabilities.setCapability(MobileCapabilityType.APP,
			 * browser.getAppPath()); capabilities.setCapability("appPackage",
			 * browser.getAppPackage()); capabilities.setCapability("appActivity",
			 * browser.getAppActivity());
			 * capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
			 * browser.getDeviceName());
			 * capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
			 * browser.getPlatformName());
			 * capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
			 * browser.getPlatformVersion()); driver = new AppiumDriver(new
			 * URL("http://127.0.0.1:4723/wd/hub"), capabilities); return driver;
			 */
			
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
		capabilities.setCapability(MobileCapabilityType.APP,"C:\\Users\\renukak\\Perforce\\renukak_CheckInWorkSpace\\razor\\apphub\\appdev\\automation\\dev\\PFM3.0\\PFM3.0_Automation\\Builds\\FinServ-debug_304.apk" );
			// capabilities.setCapability(MobileCapabilityType.APP,browser.getAppPath()); 
			 System.out.println(browser.getAppPath());
			//	capabilities.setCapability("appPackage", browser.getAppPackage());
		//capabilities.setCapability("appActivity", browser.getAppActivity());
			//capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, browser.getDeviceName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM, browser.getPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, browser.getPlatformVersion());
			String filePath=System.getProperty("user.dir");
			System.out.println(filePath);
		filePath =filePath+"\\src\\main\\resources\\drivers\\chrome\\";
		System.out.println(filePath);
	    capabilities.setCapability("chromedriverExecutableDir",filePath);
			driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			return driver;
	
		}
		if (browser.getAppPath() != null && browser.getPlatformName().equalsIgnoreCase("iOS")) {
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
			capabilities.setCapability(MobileCapabilityType.APP, browser.getAppPath());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, browser.getDeviceName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM, browser.getPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, browser.getPlatformVersion());
			driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			return driver;
		}
		if (browser.getappFlag().equalsIgnoreCase("mobileBrowser")) {
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browser.getBrowserName());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, browser.getDeviceName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM, browser.getPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, browser.getPlatformVersion());
			driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			return driver;
		}

		// allow javascript to run without prompting user to allow
		if (browser.getBrowserName().equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String pathToDownload = System.getProperty("user.dir") + File.separator + "downloads";
			chromePrefs.put("download.default_directory", pathToDownload);
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--allow-running-insecure-content", "--start-maximized");

			if (browser.getappFlag().equalsIgnoreCase("emulator")) {
				Map<String, String> mobileEmulation = new HashMap<>();
				mobileEmulation.put("deviceName", browser.getDeviceName()); // Nexus
				options.setExperimentalOption("mobileEmulation", mobileEmulation);

			}
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability("acceptSslCerts", true);
		}
		

		if (browser.isBrowserStackEnabled().equalsIgnoreCase("true")) {
			if ("firefox".equalsIgnoreCase(browser.getBrowserName())) {
				capabilities.setCapability("marionette", true);
			}
			capabilities.setCapability("browser", browser.getBrowserName());
			capabilities.setCapability("browser_version", browser.getBrowserVersion());
			capabilities.setCapability("os", browser.getPlatformName());
			capabilities.setCapability("os_version", browser.getPlatformVersion());
			capabilities.setCapability("browserstack.local", "true");
			capabilities.setCapability("browserstack.debug", "true");
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			String username = browser.getBSUsername();
			String automateKey = browser.getBSAutomateKey();
			String url = "https://" + username + ":" + automateKey + "@hub.browserstack.com:443/wd/hub";
			// driver = new RemoteWebDriver(new URL(url), capabilities);

			driver = new AppiumDriver(new URL(url), capabilities);
		} else {
			if (browser.getBrowserName().equalsIgnoreCase("ff")
					|| browser.getBrowserName().equalsIgnoreCase("firefox")) {
				/*
				 * System.setProperty("webdriver.gecko.driver", "c:\\geckodriver.exe");//
				 * geckodriver64
				 * //System.setProperty("webdriver.firefox.marionette","d:\\geckodriver.exe");
				 */				
				DesiredCapabilities caps = DesiredCapabilities.firefox();
				//caps.setCapability(CapabilityType.BROWSER_NAME, "firefox");
				caps.setCapability("marionette", true);
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				ProfilesIni pIni = new ProfilesIni();
				FirefoxProfile fp = pIni.getProfile("seleniumPFM");
				fp.setAcceptUntrustedCertificates(true);
				caps.setCapability(FirefoxDriver.PROFILE, fp);
				caps.setJavascriptEnabled(true);
				driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
				//driver.manage().timeouts().implicitlyWait(6);
				return driver;
			}
			if (browser.getBrowserName().equalsIgnoreCase("ie")) {
				capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			}
			capabilities.setCapability(CapabilityType.BROWSER_NAME, browser.getBrowserName());
			capabilities.setCapability("platformName", browser.getPlatformName());

			capabilities.setCapability("nativeEvents", false);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
		}
		System.out.println(capabilities.toString());
		return driver;
	}

	public static WebDriver getInstance(String browser, String username, String password) {
		if (CHROME.equalsIgnoreCase(browser)) {
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver();
		} else if (FIREFOX.equalsIgnoreCase(browser)) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxProfile ffProfile = new FirefoxProfile();
			// Authenication Hack for Firefox
			if (username != null && password != null) {
				ffProfile.setPreference("network.http.phishy-userpass-length", 255);
			}
			webDriver = new FirefoxDriver();
		} else if (INTERNET_EXPLORER.equalsIgnoreCase(browser)) {
//			System.setProperty("webdriver.ie.driver", "c://IEDriverServer.exe");
			WebDriverManager.iedriver().setup();
			webDriver = new InternetExplorerDriver();
		} else if (SAFARI.equalsIgnoreCase(browser)) {
			webDriver = new SafariDriver();
		} else {
			// HTMLunit Check
			if (username != null && password != null) {

			} else {
				// webDriver = new HtmlUnitDriver(true);
			}
		}
		return webDriver;
	}

	@SuppressWarnings("unused")
	private static DesiredCapabilities setVersionAndPlatform(DesiredCapabilities capability, String version,
			String platform) {
		if (MAC.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.MAC);
		} else if (LINUX.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.LINUX);
		} else if (XP.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.XP);
		} else if (VISTA.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.VISTA);
		} else if (WINDOWS.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.WINDOWS);
		} else if (ANDROID.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.ANDROID);
		} else {
			capability.setPlatform(Platform.ANY);
		}
		if (version != null) {
			capability.setVersion(version);
		}
		return capability;
	}

	@SuppressWarnings("rawtypes")
	public static AppiumDriver getDriver() {
		return driver;
	}

	public static void setDriver(@SuppressWarnings("rawtypes") AppiumDriver d) {
		driver = d;
	}
}
