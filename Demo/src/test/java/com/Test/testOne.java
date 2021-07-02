package com.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testOne {
	public static WebDriver driver;

	@BeforeTest
	public void initializeDriver() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\rshrivastav\\eclipse-workspace\\Demo\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();

	}

	@Test(priority = 1)
	public void driverDef() {
		String URL = "http://qainterview.pythonanywhere.com/";
		driver.get(URL);
		driver.manage().window().maximize();
		
		
		for (int i = 10; i < 20; i++) {

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Assert.assertEquals("The factorial of " + i + " is: " + factorial(i), findFact(i));
			
		}
	}

	public long factorial(int i) {
		long fact = 1;

		for (int j = i; j > 1; j--) {
			fact = fact * j;
			
		}
		return fact;
	}

	public String findFact(int num) {
		System.out.print("Findint Factorial of : "+num);
		
		driver.findElement(By.id("number")).clear();
		driver.findElement(By.id("number")).sendKeys(num + "");
		driver.findElement(By.id("getFactorial")).click();

		String results = driver.findElement(By.id("resultDiv")).getText();

		return results;

	}

}
