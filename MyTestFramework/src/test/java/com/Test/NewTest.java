package com.Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.TestClasses.HomePage;
import com.Util.BaseClass;
import com.Util.EnvironmentUtils;
import org.testng.annotations.BeforeSuite;

public class NewTest extends BaseClass {

	
	
	@BeforeMethod
	public void beforeMethod() {
	}

	
	public void verifyLandingPage() {
		
		driver.get("Google.com");
	}
	
}
