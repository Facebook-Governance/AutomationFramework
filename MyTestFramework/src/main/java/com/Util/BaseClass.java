package com.Util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {
	public EnvironmentUtils env;
	public WebDriver driver;

	@BeforeSuite
	@Parameters({ "Environment", "Browser" })
	public WebDriver beforeSuite(String environment, String Browser) {
		EnvironmentUtils.LoadEnvironment(environment);
		DataUtils.LoadData(environment);
		LocatorUtils.loadLocatorsFile(environment);
		System.out.println();
		if (Browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\rshrivastav\\eclipse-workspace\\Demo\\Driver\\Chrome\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (Browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
		
	}

	@AfterSuite
	public void beforeSuite() {
		driver.quit();
	}
}
