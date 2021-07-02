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

public class SFC_Saving_FTUE_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(SFC_Saving_FTUE_Test.class);
	public static String userName = "";
	SFC_FTUE_Loc FTUE;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass(alwaysRun=true)
	public void testInit() throws Exception {

		doInitialization("SFC Savings Login");

		TestBase.tc = TestBase.extent.startTest("SFC Savings FTUE", "Login");
		TestBase.test.appendChild(TestBase.tc);
		FTUE = new SFC_FTUE_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		FTUE = new SFC_FTUE_Loc(d);

	}

	@Test(description = "verify finacial forcase header ", priority = 0)

	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("SFCCard2.bank2", "bank2");
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad(5000);
	}

	@Test(description = "verify finacial forcase header ", priority = 1)
	public void verifSFCFTUEPopUpHeader() throws Exception {
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(FTUE.SFCFTUEPopUpHeader().isDisplayed());
	}
}
