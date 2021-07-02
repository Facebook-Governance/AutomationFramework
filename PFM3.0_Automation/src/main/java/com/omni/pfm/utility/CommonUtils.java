/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.List;

import java.util.Set;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.JavascriptExecutor;

import com.omni.pfm.webdriver.WebDriverFactory;

/**
 * 
 * @Description This class has methods that run testNg tests using dataprovider.
 * 
 * @author Ashish & Krishna
 *
 * 
 * 
 */

public class CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static boolean getBooleanFromString(String strict) {

		if (strict.toLowerCase().trim().equals("true")) {

			return true;

		}

		return false;

	}

	// public static String readJsonFile(String value) throws
	// FileNotFoundException {

	// String sCurrentLine;

	// String jsonFile = "";

	// try (BufferedReader br = new BufferedReader(new FileReader(value)))

	// {

	// while ((sCurrentLine = br.readLine()) != null) {

	// jsonFile += sCurrentLine;

	// }

	//

	// } catch (IOException e) {

	// e.printStackTrace();

	// }

	//

	// return jsonFile;

	//

	//

	// }

	public static Connection getConnection() {

		String dbUserName = PropsUtil.getEnvPropertyValue(("dbUserName"));

		String dbPassword = PropsUtil.getEnvPropertyValue("dbPassword");

		String dbHost = PropsUtil.getEnvPropertyValue("dbHost");

		Connection con = null;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection(dbHost, dbUserName, dbPassword);

			System.out.println("Connected to database");

		} catch (ClassNotFoundException e) {

			System.out.println("Class Not Found Exception");

			e.printStackTrace();

		} catch (SQLException e) {

			System.out.println("SQLException");

			e.printStackTrace();

		}

		return con;

	}

	public static ResultSet getQueryResult(String query) {

		Connection con = CommonUtils.getConnection();

		Statement stmt;

		ResultSet resultset = null;

		try {

			stmt = con.createStatement();

			resultset = stmt.executeQuery(query);

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return resultset;

	}

	public static boolean checkVisibility(WebElement ele) {

		boolean status = false;

		if (ele == null)

		{

			logger.error("Could not find the element ", ele);

		}

		else {

			try {

				status = ele.isDisplayed();

			} catch (Exception er)

			{

				status = false;

				logger.error("The element is not visible [{}]");

				er.printStackTrace();

			}

		}

		return status;

	}

	public static void click(WebElement we) {

		if (we != null) {

			logger.info("Clicking on element [{}]", we);

			try {

				we.click();

			}

			catch (Exception erp) {

				try {

					((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].click()", we);

					// we.click();

				} catch (Exception ex) {

					logger.warn("Exception Thrown while Calling WebElement.click() method.");

					logger.warn("Trying with javascript click");

					// SeleniumUtil.scrollElementIntoView(WebDriverFactory.getDriver(),
					// we, true);

					((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].click()", we);

				}

			}

		} else {

			logger.error("WebElement to click is null.");

			throw new NullPointerException();

		}

	}

	public static Float parseAmount(String amount) {

		amount = amount.replace("$", "");

		amount = amount.replace("$ ", "");

		amount = amount.replace(",", "");

		amount = amount.replace("\n", "");

		amount = amount.replace("*", "");

		return Float.valueOf(amount);

	}

	public static Float parseAmountExpense(String amount) {

		if (amount.contains("-")) {

			amount = amount.replace("-", "");

		}

		amount = amount.replace("$", "");

		amount = amount.replace(",", "");

		amount = amount.replace("\n", "");

		amount = amount.replace("*", "");

		return Float.valueOf(amount);

	}

	public static boolean selectDropDown(WebDriver d, String accountName, String label)

	{

		boolean status = false;

		if (d == null)

		{

			logger.error("could not find webdriver" + d);

		} else if (label == null || label == "") {

			logger.error("label not found" + label);

		} else if (accountName == null)

		{

			logger.error("could not find the account Name" + accountName);

		} else {

			String settingLOc = "//a[contains(@title,'" + accountName + "')]";

			String loc = "//a[contains(text(),'" + label + "')]";

			try {

				WebElement setting = d.findElement(By.xpath(settingLOc));

				if (setting != null)

				{

					SeleniumUtil.click(setting);

					List<WebElement> lst = d.findElements(By.xpath(loc));

					for (int i = 0; i < lst.size(); i++)

					{

						if (lst.get(i).isDisplayed() && lst.get(i) != null)

						{

							SeleniumUtil.click(lst.get(i));

							SeleniumUtil.waitForElement(null, 1500);

							status = true;

							break;

						}

					}

				} else {

					logger.error("could not find setting for account" + accountName);

				}

			} catch (Exception er) {
			}

		}

		return status;

	}

	public static boolean selectDropDownVisibility(WebDriver d, String accountName, String label)

	{
		boolean status = false;

		if (d == null)

		{

			logger.error("could not find webdriver" + d);

		} else if (label == null || label == "") {

			logger.error("label not found" + label);

		} else if (accountName == null)

		{

			logger.error("could not find the account Name" + accountName);

		} else {

			String settingLOc = "//a[contains(@title,'" + accountName + "')]";

			String loc = "//a[contains(text(),'" + label + "')]";

			try {

				WebElement setting = d.findElement(By.xpath(settingLOc));

				if (setting != null)

				{

					CommonUtils.click(setting);

					List<WebElement> lst = d.findElements(By.xpath(loc));

					for (int i = 0; i < lst.size(); i++)

					{

						if (lst.get(i) != null && lst.get(i).isDisplayed())

						{

							status = true;

							CommonUtils.click(setting);

							break;

						}

					}

				} else {

					logger.error("could not find setting for account" + accountName);

				}

			} catch (Exception er) {
			}

		}

		return status;

	}

	public static void closeAllWindows(WebDriver d) {

		String base = d.getWindowHandle();

		Set<String> set = d.getWindowHandles();

		set.remove(base);

		assert set.size() == 1;

		for (String a : set) {

			System.out.println("Closing window: " + a);

			d.switchTo().window(a);

			d.close();

		}

		// window(set.toArray(new String[0]));

		d.switchTo().window(base);

	}

	public static void logOut(WebDriver d) {

		WebElement el = SeleniumUtil.getVisibileWebElement(d, "LogOut", "AccountsPage", null);

		SeleniumUtil.click(el);

		// d.get(PropsUtil.getEnvPropertyValue("cnf_base_url"));

		// LoginPage.login(d, "AccountSummary.Title");

	}

	public static void logOutApp(WebDriver d) {

		WebElement el = SeleniumUtil.getVisibileWebElement(d, "LogOut", "AccountsPage", null);

		SeleniumUtil.click(el);

	}

	// Lavanya

	public static boolean isDisplayed(WebElement element) {
		boolean flag = false;
		if (element == null) {
			logger.error("Could not find the element " + element);
		}
		try {
			element.isDisplayed();
			flag = true;
			logger.info("Element is displayed." + element);
		} catch (Exception e) {
			flag = false;
			logger.info("Element is not displayed." + element);

		}
		return flag;
	}

	public static boolean isSelected(WebElement element) {
		boolean flag = false;
		if (element == null) {
			logger.error("Could not find the element " + element);
		}
		try {
			element.isSelected();
			flag = true;
			logger.info("Element is Selected." + element);
		} catch (Exception e) {
			flag = false;
			logger.info("Element is not Selected." + element);

		}
		return flag;
	}

	public static boolean isNotSelected(WebElement element) {
		boolean flag = false;
		if (element == null) {
			logger.error("Could not find the element " + element);
		}
		try {
			if (element.isSelected() == false) {
				flag = true;
				logger.info("Element is Not Selected." + element);
			} else {
				flag = false;
				logger.info("Element is Selected." + element);
			}
		} catch (Exception e) {
			flag = false;
			logger.info("Element is Selected." + element);

		}
		return flag;
	}

	public static boolean isDisabled(WebElement element) {
		boolean flag = false;
		if (element == null) {
			logger.error("Could not find the element " + element);
		}
		try {
			if (element.isEnabled() == false) {
				flag = true;
				logger.info("Element is Not Enabled." + element);
			} else {
				flag = false;
				logger.info("Element is Enabled." + element);
			}
		} catch (Exception e) {
			flag = false;
			logger.info("Element is Enabled." + element);

		}
		return flag;
	}

	public void switchToFrame(WebDriver d) {
		d.switchTo().frame(SeleniumUtil.getWebElement(d, "IframeEnviSwitchIframe", "SAML_LOGIN", null));

	}

	/**
	 * Check the expected value is available or not
	 * 
	 */
	public static boolean assertValue(String actualValue, String expKey) {

		boolean flag = false;
		String expvalue = PropsUtil.getDataPropertyValue(expKey);

		try {
			Assert.assertEquals(actualValue, expvalue);
			flag = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (!flag) {
				Reporter.getCurrentTestResult().setStatus(2);
				return false;
			}
		}
		return flag;
	}

	public static boolean assertEqualsListElements(String expectedLevel, List<WebElement> allOptions) {
		String expected[] = PropsUtil.getDataPropertyValue(expectedLevel).split(",");
		boolean status = true;
		if (expected.length != allOptions.size()) {
			Reporter.getCurrentTestResult().setThrowable(exception("Actual and expected size doesn't match."));
			logger.error("fail, expected array and actual list size doesn't match.");
			status = false;
		} else {
			for (int i = 0; i < expected.length; i++) {
				String optionValue = allOptions.get(i).getText().trim();
				if (!optionValue.equals(expected[i])) {
					logger.info("Expected " + expected[i] + " but failed on : " + optionValue);
					status = false;
				}
			}
		}
		if (!status) {
			Reporter.getCurrentTestResult().setStatus(2);
		}
		return status;
	}

	public static boolean assertContainsListElements(String expectedLevel, List<WebElement> allOptions) {
		String expected[] = PropsUtil.getDataPropertyValue(expectedLevel).split(",");
		boolean status = true;
		if (expected.length != allOptions.size()) {
			Reporter.getCurrentTestResult().setThrowable(exception("Actual and expected size doesn't match."));
			logger.error("fail, expected array and actual list size doesn't match.");
			status = false;
		} else {
			for (int i = 0; i < expected.length; i++) {
				String optionValue = allOptions.get(i).getText().trim();
				if (!optionValue.contains(expected[i])) {
					logger.error("Expected " + expected[i] + " but failed on : " + optionValue);
					status = false;
				}
			}
		}
		if (!status) {
			Reporter.getCurrentTestResult().setStatus(2);

		}
		return status;
	}

	private static Throwable exception(String errormessage) {
		Throwable t = new Throwable(errormessage);
		return t;
	}

}
