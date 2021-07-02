package com.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.Assert;

public class LocatorUtils {
	static String projectDir = System.getProperty("user.dir");
	static Properties locatorsProperties = new Properties();

	public static void loadLocatorsFile(String environment) {

		File locatorFile = new File(
				projectDir + "/src/main/resources/com/Locators/Locators_" + environment + ".properties");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(locatorFile);
			locatorsProperties.load(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static By getLocator(String key) {
		String value = locatorsProperties.getProperty(key);
		String locatorType = value.split(":")[0];
		String locatorValue = value.split(":", 2)[1];
		switch (locatorType.toLowerCase()) {
		case "id":
			return By.id(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
		case "class":
			return By.className(locatorValue);
		case "link":
		case "linktext":
			return By.linkText(locatorValue);
		case "partialLinkText":
			return By.partialLinkText(locatorValue);
		case "cssSelector":
			return By.cssSelector(locatorValue);
		default:
			Assert.fail("Invalid Locator :: \"" + locatorType + "\" : Actual Locator Type Present is \":" + locatorValue
					+ "\"");
		}
		return null;
	}

}
