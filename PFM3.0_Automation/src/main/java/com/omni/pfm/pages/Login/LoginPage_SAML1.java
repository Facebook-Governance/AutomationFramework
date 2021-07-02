/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
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

public class LoginPage_SAML1 {
	private static final Logger logger = LoggerFactory.getLogger(LoginPage_SAML1.class);

	static String userName = null;

	// FOR L1

	public static boolean login(WebDriver d) {
		d.get(PropsUtil.getEnvPropertyValue("cnf_base_url"));
		SeleniumUtil.waitForPageToLoad(2000);
		WebElement loginText = SeleniumUtil.getVisibileWebElement(d, "loginText", "SAML_LOGIN", null);
		SeleniumUtil.click(loginText);
		String fileName = null;
		fileName = Config.getInstance().getEnvironment().toUpperCase() + "_"
				+ PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase() + ".Properties";
		logger.info("the rest property file name is " + fileName);
		userName = null;
		if (PropsUtil.getEnvPropertyValue("cnf_newUserLogin").equalsIgnoreCase("yes")) {
			userName = RegisterUser.initCobrand(fileName);
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

			WebElement cobLogin = SeleniumUtil.getVisibileWebElement(d, "cobLogin", "SAML_LOGIN", null);
			cobLogin.clear();
			cobLogin.sendKeys(PropsUtil.getEnvPropertyValue("coBrandLogin"));

			WebElement cobPassword = SeleniumUtil.getVisibileWebElement(d, "cobPassword", "SAML_LOGIN", null);
			cobPassword.clear();
			cobPassword.sendKeys(PropsUtil.getEnvPropertyValue("coBrandPassword"));

			WebElement userName1 = SeleniumUtil.getVisibileWebElement(d, "userName", "SAML_LOGIN", null);
			userName1.clear();
			userName1.sendKeys(userName);

			WebElement userPassword = SeleniumUtil.getVisibileWebElement(d, "userPassword", "SAML_LOGIN", null);
			userPassword.clear();
			userPassword.sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));

			WebElement nodeRsessionUrl = SeleniumUtil.getVisibileWebElement(d, "nodeRsessionUrl", "SAML_LOGIN", null);
			nodeRsessionUrl.clear();
			nodeRsessionUrl.sendKeys(PropsUtil.getEnvPropertyValue("nodeRsessionUrl"));

			WebElement restRsessionUrl = SeleniumUtil.getVisibileWebElement(d, "restRsessionUrl", "SAML_LOGIN", null);
			restRsessionUrl.clear();
			restRsessionUrl.sendKeys(PropsUtil.getEnvPropertyValue("restRsessionUrl"));

			WebElement finappId = SeleniumUtil.getVisibileWebElement(d, "finappId", "SAML_LOGIN", null);
			finappId.clear();
			finappId.sendKeys(PropsUtil.getEnvPropertyValue("finappId"));

			WebElement saveButton = SeleniumUtil.getVisibileWebElement(d, "saveButton", "SAML_LOGIN", null);
			SeleniumUtil.click(saveButton);

			WebElement NewWindowRsession = SeleniumUtil.getVisibileWebElement(d, "NewWindowRsession", "SAML_LOGIN",
					null);
			SeleniumUtil.click(NewWindowRsession);

			SeleniumUtil.SwitchToCurrentTab(d);

		}

		return status;

	}

	public static boolean loginExistingUser(WebDriver d, String username, String password) {
		d.get(PropsUtil.getEnvPropertyValue("cnf_base_url"));
		WebElement loginText = SeleniumUtil.getVisibileWebElement(d, "loginText", "SAML_LOGIN", null);
		SeleniumUtil.click(loginText);
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

			WebElement cobLogin = SeleniumUtil.getVisibileWebElement(d, "cobLogin", "SAML_LOGIN", null);
			cobLogin.clear();
			cobLogin.sendKeys(PropsUtil.getEnvPropertyValue("coBrandLogin"));

			WebElement cobPassword = SeleniumUtil.getVisibileWebElement(d, "cobPassword", "SAML_LOGIN", null);
			cobPassword.clear();
			cobPassword.sendKeys(PropsUtil.getEnvPropertyValue("coBrandPassword"));

			WebElement userName1 = SeleniumUtil.getVisibileWebElement(d, "userName", "SAML_LOGIN", null);
			userName1.clear();
			userName1.sendKeys(userName);

			WebElement userPassword = SeleniumUtil.getVisibileWebElement(d, "userPassword", "SAML_LOGIN", null);
			userPassword.clear();
			userPassword.sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));

			WebElement nodeRsessionUrl = SeleniumUtil.getVisibileWebElement(d, "nodeRsessionUrl", "SAML_LOGIN", null);
			nodeRsessionUrl.clear();
			nodeRsessionUrl.sendKeys(PropsUtil.getEnvPropertyValue("nodeRsessionUrl"));

			WebElement restRsessionUrl = SeleniumUtil.getVisibileWebElement(d, "restRsessionUrl", "SAML_LOGIN", null);
			restRsessionUrl.clear();
			restRsessionUrl.sendKeys(PropsUtil.getEnvPropertyValue("restRsessionUrl"));

			WebElement finappId = SeleniumUtil.getVisibileWebElement(d, "finappId", "SAML_LOGIN", null);
			finappId.clear();
			finappId.sendKeys(PropsUtil.getEnvPropertyValue("finappId"));

			WebElement saveButton = SeleniumUtil.getVisibileWebElement(d, "saveButton", "SAML_LOGIN", null);
			SeleniumUtil.click(saveButton);

			WebElement NewWindowRsession = SeleniumUtil.getVisibileWebElement(d, "NewWindowRsession", "SAML_LOGIN",
					null);
			SeleniumUtil.click(NewWindowRsession);

			SeleniumUtil.SwitchToCurrentTab(d);

		}

		return status;

	}

}
