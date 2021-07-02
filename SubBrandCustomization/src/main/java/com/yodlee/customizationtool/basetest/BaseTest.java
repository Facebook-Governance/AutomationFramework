
/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.basetest;

import com.yodlee.customizationtool.datpprocessor.DataProperties;
import com.yodlee.customizationtool.datpprocessor.DataProperty;
import com.yodlee.customizationtool.envprocessor.Environment;
import com.yodlee.customizationtool.envprocessor.Environments;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.databene.feed4testng.FeedTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static com.yodlee.customizationtool.util.JsonUtil.getObject;

public class BaseTest extends FeedTest {

	private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
	private final int IMP_TIMEOUTINSECONDS = 15;

	public Environments env;
	public DataProperties data;
	public static WebDriver webdriver;
	public static Environment envDetails;
	public static DataProperty dataProperty;

	@BeforeSuite
	@Parameters({ "browserName", "environment" })
	public void setup(@Optional("chrome") String browserName, @Optional("L1") String environment) {

		envDetails = getEnvironmentDetails(environment);
		dataProperty = getDataProperties(environment);

		if (browserName.trim().toLowerCase().equals("firefox")) {
			//WebDriverManager.firefoxdriver().setup();
			setWebdriver(new FirefoxDriver());
		} else if (browserName.trim().toLowerCase().equalsIgnoreCase("chrome")) {
			DesiredCapabilities capabilities = new DesiredCapabilities().chrome();
			capabilities.setCapability("acceptInsecureCerts", true);
			WebDriverManager.chromedriver().setup();
			setWebdriver(new ChromeDriver(capabilities));
		}

		else if (browserName.trim().toLowerCase().equalsIgnoreCase("ie")) {
			//WebDriverManager.iedriver().setup();
			setWebdriver(new InternetExplorerDriver());
		} else if (browserName.trim().toLowerCase().equalsIgnoreCase("edge")) {
			//WebDriverManager.edgedriver().setup();
			setWebdriver(new EdgeDriver());
		}

		webdriver.get(envDetails.getBaseUrl());
		webdriver.manage().window().maximize();
		webdriver.manage().deleteAllCookies();
		webdriver.manage().timeouts().implicitlyWait(IMP_TIMEOUTINSECONDS, TimeUnit.SECONDS);
	}

	public WebDriver getWebdriver() {

		return webdriver;
	}

	private void setWebdriver(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	@AfterSuite(alwaysRun = true)
	public void closeBrowser() {

		/*
		 * if (webdriver != null) { webdriver.quit(); }
		 */
	}

	public Environment getEnvironmentDetails(String environment) {
		env = getObject(Environments.class, ".\\environment\\Environment.json");
		return env.getEnvironments().get(environment);
	}

	public DataProperty getDataProperties(String environment) {
		data = getObject(DataProperties.class, ".\\dataproperty\\DataProperty.json");
		return data.getDataProperties().get(environment);
	}

	public static DesiredCapabilities getCapabilities(String browserName) {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setAcceptInsecureCerts(true);
			return capabilities;
		}

		return capabilities;
	}
}

/*
 * switch (browserName.trim().toLowerCase()) { case "firefox":
 * WebDriverManager.firefoxdriver().setup(); setWebdriver(new FirefoxDriver());
 * break;
 * 
 * case "ie": WebDriverManager.iedriver().setup(); setWebdriver(new
 * InternetExplorerDriver()); break;
 * 
 * case "edge": WebDriverManager.edgedriver().setup(); setWebdriver(new
 * EdgeDriver()); break;
 * 
 * default: WebDriverManager.chromedriver().setup(); setWebdriver(new
 * ChromeDriver()); break; }
 */