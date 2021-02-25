package com.org.Util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	protected WebDriver driver;
	WebDriverWait wait;

	public SeleniumUtils(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 100);
	}

	public void click(By element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		scroll(element);
		driver.findElement(element).click();
	}

	public void click(WebElement element) {
		scroll(element);
		element.click();
	}

	public void sendkeys(By element, String keys) {
		driver.findElement(element).clear();

		driver.findElement(element).sendKeys(keys);
	}

	public void clear(By element) {
		click(element);
		driver.findElement(element).clear();
	}

	public List<WebElement> getWebElements(By element) {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
		return driver.findElements(element);
	}

	public void selectTheCheckBox(By element) {
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(element));
		if (!ele.isSelected()) {
			ele.click();
		}
	}

	public boolean isDisplayed(By element) {
		for (int i = 0; i < 2; i++) {
			try {
				return driver.findElement(element).isDisplayed();
			} catch (Exception e) {
				waitFor(1);
			}
		}
		return false;
	}

	public void switchToFrame(By element) {

		driver.switchTo().frame(driver.findElement(element));

	}

	public void switchToDefaultContent() {

		driver.switchTo().defaultContent();
	}

	public String getText(By element) {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		scroll(element);
		return driver.findElement(element).getText();
	}

	public String getText(WebElement element) {
		scroll(element);
		return element.getText().trim();
	}

	protected void scroll(By element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
	}

	protected void scroll(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void moveToElement(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}

	public void waitFor(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
