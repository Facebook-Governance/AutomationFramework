package com.org.Util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class BaseClass {
	public EnvironmentUtils env;
	public static WebDriver driver;

	@BeforeSuite
	@Parameters({ "Environment", "Browser" })
	public void beforeSuite(String environment, String Browser) {
		EnvironmentUtils.LoadEnvironment(environment);
		DataUtils.LoadData(environment);
		LocatorUtils.loadLocatorsFile(environment);
		System.out.println();
		if (Browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\rshrivastav\\eclipse-workspace\\Demo\\Driver\\Chrome\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (Browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void loadEnvironment() {
		driver.get(EnvironmentUtils.getEnvironmentData("EnvironmentURL"));
		Assert.assertEquals(driver.getTitle(), DataUtils.getTestData("PageTitle"));

	}

	@AfterSuite
	public void beforeSuite() {
		/*
		 * driver.quit();
		 */ 
		
	}
}
