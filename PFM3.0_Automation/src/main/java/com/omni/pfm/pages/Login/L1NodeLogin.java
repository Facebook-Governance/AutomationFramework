/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.annotations.Find;
import com.omni.pfm.config.Config;
import com.omni.pfm.factory.PageFactory;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DBUtil;
import com.omni.pfm.utility.FunctionUtil;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class L1NodeLogin {
	private static final Logger logger = LoggerFactory.getLogger(L1NodeLogin.class);

	public static String userName = null;
	static String pageName = "SAML_LOGIN";
	// FOR L1

	private final String Uname = "etUserName";

	@FindBy(how = How.ID, using = Uname)

	public WebElement uName;

	private final String pwd = "etPassword";

	@FindBy(how = How.ID, using = pwd)

	public WebElement pWd;

	private final String login = "//*[@class='android.widget.Button' and @text='Login']";

	@FindBy(how = How.XPATH, using = login)

	public WebElement loginBtn;

	private final String FiIcon = "up";

	@FindBy(how = How.ID, using = FiIcon)

	public WebElement fiIcon;

	private final String accountType = "//*[@class='android.widget.TextView' and @text='Yodlee']";

	@FindBy(how = How.XPATH, using = accountType)

	public WebElement accountTypeIcon;

	public static boolean login(WebDriver d) {

		String fileName = null;
		fileName = Config.getInstance().getEnvironment().toUpperCase() + "_"
				+ PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase() + ".Properties";
		logger.info("the rest property file name is " + fileName);
		userName = null;
		if (PropsUtil.getEnvPropertyValue("cnf_newUserLogin").equalsIgnoreCase("yes")) {
			String needToCreateUserUsingRest = PropsUtil.getEnvPropertyValue("CreateUserUsingREST");
			if (needToCreateUserUsingRest == null || needToCreateUserUsingRest.isEmpty()
					|| needToCreateUserUsingRest.trim().equalsIgnoreCase("true")) {
				userName = RegisterUser.initCobrand(fileName);
			} else {
				userName = RegisterUser.registerUserUsingYSL(PropsUtil.getEnvPropertyValue("yslsUrl"),
						PropsUtil.getEnvPropertyValue("yslCobrandName"),
						PropsUtil.getEnvPropertyValue("yslCobrandLogin"),
						PropsUtil.getEnvPropertyValue("yslCobrandPassword"));
			}
		} else {
			userName = PropsUtil.getEnvPropertyValue("cnf_userName");
		}
		boolean status = true;
		if (GenericUtil.isNull(userName)) {
			logger.error("Username is null or empty...");
			status = false;
		} else {
			logger.info("Username is : {}", userName);
			logger.info("Populating loginName textbox");
			d.get(PropsUtil.getEnvPropertyValue("NodeURL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e) {
				// TODO: handle exception
			}

			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "login", pageName, null));

			SeleniumUtil.getWebElement(d, "login", pageName, null).clear();

			SeleniumUtil.getWebElement(d, "login", pageName, null).sendKeys(userName);

			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "password", pageName, null));

			SeleniumUtil.getWebElement(d, "password", pageName, null).clear();

			SeleniumUtil.getWebElement(d, "password", pageName, null)
					.sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));
			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null));

			SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null).clear();

			SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null)
					.sendKeys(PropsUtil.getEnvPropertyValue("finappId"));
			/*
			 * SeleniumUtil.click(SeleniumUtil.getWebElement(d, "redirectReq", pageName,
			 * null));
			 * 
			 * SeleniumUtil.getWebElement(d, "redirectReq", pageName, null).clear();
			 * 
			 * SeleniumUtil.getWebElement(d, "redirectReq", pageName,
			 * null).sendKeys("true");
			 */
			/*
			 * AppiumDriver d1 = (AppiumDriver) d; d1.hideKeyboard();
			 */
			// SeleniumUtil.click(SeleniumUtil.getWebElement(d, "LoginSubmit", pageName,
			// null));
			SeleniumUtil.getWebElement(d, "LoginSubmit", pageName, null).sendKeys(Keys.ENTER);

			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}

		return status;

	}

	public static boolean loginExistingUser(WebDriver d, String username, String password) {

		/*
		 * WebElement loginText=SeleniumUtil.getVisibileWebElement(d, "loginText",
		 * "SAML_LOGIN", null); SeleniumUtil.click(loginText);
		 */
		String fileName = null;
		fileName = Config.getInstance().getEnvironment().toUpperCase() + "_"
				+ PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase() + ".Properties";
		logger.info("the rest property file name is " + fileName);
		userName = username;
		boolean status = true;
		if (GenericUtil.isNull(userName)) {
			logger.error("Username is null or empty...");
			status = false;
		} else {
			logger.info("Username is : {}", userName);
			logger.info("Populating loginName textbox");

			d.get(PropsUtil.getEnvPropertyValue("NodeURL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e) {
				// TODO: handle exception
			}

			SeleniumUtil.getWebElement(d, "login", pageName, null).click();

			SeleniumUtil.getWebElement(d, "login", pageName, null).clear();

			SeleniumUtil.getWebElement(d, "login", pageName, null).sendKeys(userName);

			SeleniumUtil.getWebElement(d, "password", pageName, null).click();

			SeleniumUtil.getWebElement(d, "password", pageName, null).clear();

			SeleniumUtil.getWebElement(d, "password", pageName, null).sendKeys(password);
			SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null).click();

			SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null).clear();

			SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null)
					.sendKeys(PropsUtil.getEnvPropertyValue("finappId"));
			/*
			 * SeleniumUtil.getWebElement(d, "redirectReq", pageName, null).click();
			 * 
			 * SeleniumUtil.getWebElement(d, "redirectReq", pageName, null).clear();
			 * 
			 * SeleniumUtil.getWebElement(d, "redirectReq", pageName,
			 * null).sendKeys("true");
			 */

			/*
			 * AppiumDriver d1 = (AppiumDriver) d; d1.hideKeyboard();
			 */
			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "LoginSubmit", pageName, null));

			SeleniumUtil.waitForPageToLoad();
		}

		return status;

	}

	public void loginApp(AppiumDriver d, String userName, String password) {

		SeleniumUtil.waitForPageToLoad();

		fiIcon.click();

		accountTypeIcon.click();

		// Typing user name

		uName.click();

		uName.sendKeys(userName);
		// Typing Password

		pWd.click();

		pWd.sendKeys(password);

		d.hideKeyboard();

		SeleniumUtil.waitForPageToLoad(7500);
		// Click login button

		loginBtn.click();

		SeleniumUtil.waitForPageToLoad(7500);

		Set<String> s = ((AppiumDriver) d).getContextHandles();

		System.out.println(s);
		SeleniumUtil.waitForPageToLoad(7500);

		((AppiumDriver) d).context((String) s.toArray()[1]);

	}

}
