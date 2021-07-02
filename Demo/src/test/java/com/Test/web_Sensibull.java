package com.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Util.BaseClass.BaseClass;

public class web_Sensibull extends BaseClass {

	@BeforeTest
	public void initialize() throws IOException {

		driver = initializeDriver();
	}

	@Test
	public void LandingPage() {
		driver.get("https://web.sensibull.com/");
		driver.manage().window().maximize(); // View changes when maximize window is open

		driver.findElement(
				By.xpath("/html/body/div[1]/div/div[4]/div[1]/div/div[1]/div/div/div[2]/div/ul/a[2]/li/div[1]/div\r\n"))
				.click();

		WebElement dropdown = driver
				.findElement(By.xpath("//*[@id=\"marketView-undefined-View-35597\"]/div[1]/div[2]"));
		Select select = new Select(dropdown);
		java.util.List<WebElement> options = select.getOptions();
		for (WebElement item : options) {

			System.out.println("Dropdown values are " + item.getText());
		}

		Select viewOptions = new Select(driver.findElement(By.xpath(
				"/html/body/div[1]/div/div[4]/div[2]/div[2]/div/form/div/div[3]/div[1]/div/div[2]/div[1]/div[1]")));

		System.out.println("Done");
		viewOptions.selectByVisibleText("ABOVE");

		driver.findElement(By.xpath("//div[contains(@role,'menu')]/div[1]")).click();
		/*
		 * Assert.assertTrue(driver.findElement(By.linkText("Trade")).isDisplayed());
		 */ System.out.println("Done2");
		driver.findElement(By.xpath("//span[contains(text(),'Go')]")).click();

		driver.findElement(By.xpath("//*[@id=\'undefined-undefined-Lotsx75-29453\']")).sendKeys("2");
		driver.findElement(By.xpath(
				"//div[6]/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div/div/div[1]/div[4]/div/button/div/div/span\r\n"))
				.click();

		String a = driver.findElement(By.xpath(
				"//div[6]/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div/div/div[1]/div[1]/div[1]/div[1]/div"))
				.getText();
		String b = driver.findElement(By.xpath(
				"//div[6]/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div/div/div[1]/div[1]/div[1]/div[2]/div"))
				.getText();

		ArrayList<String> chromeTabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(chromeTabs.get(1));
		driver.close();

		driver.switchTo().window(chromeTabs.get(0));

		Assert.assertTrue(driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div[1]/div/div[2]/div/div[1]/div"))
				.isDisplayed());

		String sell = driver.findElement(By.xpath(
				"/html/body/div[1]/div/div[4]/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[2]/div[1]"))
				.getText();
		String Buy = driver.findElement(By.xpath(
				"/html/body/div[1]/div/div[4]/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[2]/div[2]"))
				.getText();

		Assert.assertEquals(a, Buy);
		Assert.assertEquals(b, sell);

		System.out.println(a + " Is matching with " + Buy);
		System.out.println(b + " Is matching with " + sell);

	}

	@AfterTest
	public void teardown() {

		driver.close();

	}
}
