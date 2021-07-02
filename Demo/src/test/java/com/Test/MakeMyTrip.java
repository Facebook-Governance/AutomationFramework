package com.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.Util.BaseClass.BaseClass;

public class MakeMyTrip extends BaseClass {

	@BeforeClass
	public WebDriver initializeDriver() {

		return driver;
	}

	@Test
	public void launchMMT() {

		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();

	}

	@Test
	public void travelPlan(String Source, String Dest, String TravelDate) {


		driver.findElement(By.id("fromCity")).sendKeys(Source);
		
		driver.findElement(By.id("toCity")).sendKeys(Dest);
		driver.findElement(By.id("departure")).click();
		String date = driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div[1]")).getText();
		
		System.out.println("Executed till here.");
		
		
//		Date- 06/July/2021 - Feb 
		
				
		
		
	}
}
