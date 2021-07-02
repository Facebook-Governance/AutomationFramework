/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Ok2Spend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Ok2Spend.SFC_FTUE_Loc;
import com.omni.pfm.testBase.TestBase;

import com.omni.pfm.utility.SeleniumUtil;

public class SFC_Card_FTUE_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(SFC_Card_FTUE_Test.class);
	public static String userName = "";
	SFC_FTUE_Loc FTUE;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass

	public void testInit() throws Exception {

		doInitialization("SFC Card Account Login");

		TestBase.tc = TestBase.extent.startTest("SFC Card", "Login");
		TestBase.test.appendChild(TestBase.tc);
		
		login = new LoginPage();
		accountAdd = new AccountAddition();
		FTUE = new SFC_FTUE_Loc(d);

	}

	@Test(description = "Verify The Login", priority = 1)

	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info(">>>>> Login successful ...");
		SeleniumUtil.waitForPageToLoad(7000);
		
		logger.info(">>>>>Linking the account");
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("SFCCard2.creditCard1", "creditCard1");
		SeleniumUtil.waitForPageToLoad();
		
	}
	
	@Test(description="Verify Header", priority=2)
	public void verifyHeader() {
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(FTUE.SFCFTUEPopUpHeader().isDisplayed(),"Header Not displayed");
	}
}
