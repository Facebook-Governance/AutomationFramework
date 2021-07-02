package com.TestClasses;


import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.Util.BaseClass;
import com.Util.EnvironmentUtils;
import com.Util.LocatorUtils;

public class HomePage extends BaseClass {

	@Test(priority = 1)

	public void verifyLandingPage() {

		By var = LocatorUtils.getLocator("LandingPageHeader");
		driver.get(EnvironmentUtils.getEnvironmentData("EnvironmentURL"));

		System.out.println("WebPage Loaded with MMT Portal");
	}

	@Test(priority = 2)
	public void planTravel() {
		driver.findElement(By.id("fromCity")).sendKeys("New Delhi");
		driver.findElement(By.id("toCity")).sendKeys("Mumbai");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("departure")).click();		
		driver.findElement(By.xpath("//a[contains(text(),'Search')]")).click();
		System.out.println("Selected Source and Destinations");
		
		
		
	}
}
