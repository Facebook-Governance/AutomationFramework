package com.Utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class BaseClass {
	public EnvironmentUtils env;
	public WebDriver driver;

	@BeforeSuite
	@Parameters({ "Environment"})
	public void beforeSuite(String environment) {
		EnvironmentUtils.LoadEnvironment(environment);
		DataUtils.LoadData(environment);

	}

}
