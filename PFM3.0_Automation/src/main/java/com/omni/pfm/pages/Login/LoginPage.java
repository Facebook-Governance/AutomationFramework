/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.config.Config;
import com.omni.pfm.constants.PageConstants;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;

public class LoginPage extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
	static LoginPage_DeepLink deeplink = new LoginPage_DeepLink();

	public static void loginMain(WebDriver d, String envName) throws Exception {
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		if (envName.toUpperCase().trim().contains(PageConstants.OBO)) {
			logger.info("Login through OBO");
			Assert.assertTrue(LoginPage_OBO.loginWithYCC(d));
			logger.info("*************Login successful********************");

		}
		if (envName.toUpperCase().trim().contains(PageConstants.DeepLink)) {
			logger.info("Login through DeepLink");
			deeplink.login(d);
			logger.info("*************Login successful********************");
		}

		if (envName.toUpperCase().trim().contains(PageConstants.SAML1)) {
			logger.info("Login through SAML1");
			Assert.assertTrue(LoginPage_SAML1.login(d));
			logger.info("*************Login successful********************");
		}

		if (envName.toUpperCase().trim().contains(PageConstants.SAML2)) {
			logger.info("Login through SAML2");
			Assert.assertTrue(LoginPage_SAML2.login(d));
			logger.info("*************Login successful********************");
		}
		if (envName.toUpperCase().trim().contains(PageConstants.L1NODE)) {
			logger.info("Login through L1NodeLogin");
			Assert.assertTrue(L1NodeLogin.login(d));
			logger.info("*************Login successful********************");
		}

		if (envName.toUpperCase().trim().contains(PageConstants.MOBILE)) {
			logger.info("Login through L1NodeLogin");
			Assert.assertTrue(LoginPage_Mobile.login(d));
			logger.info("*************Login successful********************");
		}
		if (envName.toUpperCase().trim().contains(PageConstants.L1IFRAME)) {
			logger.info("Login through L1IFRAMELOGIN");
			Assert.assertTrue(L1IFrameLogin.login(d));
			logger.info("*************Login successful********************");
		}
		SeleniumUtil.refresh(d);
	}

	public static boolean loginWithOldAccount(String username, String password) {
		String pageName = "SAML_LOGIN";
		try {
			String fileName = null;
			fileName = Config.getInstance().getEnvironment().toUpperCase() + "_"
					+ PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase() + ".Properties";
			logger.info("the rest property file name is " + fileName);
			if (GenericUtil.isNull(username)) {
				Assert.fail("Login username is null");
			} else {
				logger.info("Entering username :: "+username+" in the login input box");
				d.get(PropsUtil.getEnvPropertyValue("NodeURL"));
				try {
					JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
					js.executeScript("document.querySelector('#proceed-link').click();");
				} catch (Exception e) {
					// TODO: handle exception
				}
				SeleniumUtil.click(SeleniumUtil.getWebElement(d, "login", pageName, null));

				SeleniumUtil.getWebElement(d, "login", pageName, null).clear();

				SeleniumUtil.getWebElement(d, "login", pageName, null).sendKeys(username);

				SeleniumUtil.click(SeleniumUtil.getWebElement(d, "password", pageName, null));

				SeleniumUtil.getWebElement(d, "password", pageName, null).clear();

				SeleniumUtil.getWebElement(d, "password", pageName, null)
						.sendKeys(password);
				SeleniumUtil.click(SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null));

				SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null).clear();

				SeleniumUtil.getWebElement(d, "FinappIdNew", pageName, null)
						.sendKeys(PropsUtil.getEnvPropertyValue("finappId"));
				
				SeleniumUtil.getWebElement(d, "LoginSubmit", pageName, null).sendKeys(Keys.ENTER);

				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			}
		} catch (Exception e) {
			Assert.fail("Unable to login due to :: " + e.getMessage());
		}
		return true;
	}
}
