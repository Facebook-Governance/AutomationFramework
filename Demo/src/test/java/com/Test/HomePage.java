package com.Test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Util.BaseClass.BaseClass;

public class HomePage extends BaseClass {
	public WebDriver driver;

	@BeforeTest
	public void initialize() throws IOException {

		driver = initializeDriver();

	}

	@Test

	public void basePageNavigation() throws InterruptedException {

		driver.navigate().to("www.google.com");
		driver.manage().window().maximize();

	}

	@AfterTest
	public void teardown() {

		driver.close();

	}

}
