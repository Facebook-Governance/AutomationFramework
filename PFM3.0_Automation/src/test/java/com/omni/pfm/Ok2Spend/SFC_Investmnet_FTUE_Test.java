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
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFC_Investmnet_FTUE_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(SFC_Investmnet_FTUE_Test.class);
	public static String userName = "";
	SFC_FTUE_Loc FTUE;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass

	public void testInit() throws Exception {

		doInitialization("SFC Investment FTUE Login");

		TestBase.tc = TestBase.extent.startTest("SFC Investments FTUE", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		accountAdd = new AccountAddition();

		FTUE = new SFC_FTUE_Loc(d);

	}

	@Test(description = "Verify Login", priority = 0)
	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(7000);

		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("psqa.Investment1", "Investment1");
	}

	@Test(description = "verify finacial forcase header ", priority = 1)
	public void veriftSFCHeader() throws Exception {

		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(FTUE.SFCHeader().getText(), PropsUtil.getDataPropertyValue("SFCHeader"));
	}

	@Test(description = "verify finacial forcase user ", priority = 2)
	public void veriftSFCUser() throws Exception {
		Assert.assertEquals(FTUE.SFCUser().getText(), PropsUtil.getDataPropertyValue("SFCUser"));
	}

	@Test(description = "verify finacial forcase Predictable message ", priority = 3)
	public void veriftSFCPredictable() throws Exception {
		Assert.assertEquals(FTUE.SFCPredictable().getText(), PropsUtil.getDataPropertyValue("SFCPredictable"));
	}

	@Test(description = "verify finacial forcase info message ", priority = 4)
	public void veriftSFCInfoMessage() throws Exception {
		Assert.assertEquals(FTUE.SFCInfo().getText(), PropsUtil.getDataPropertyValue("SFCInfo"));

	}

	@Test(description = "verify finacial forcase SpendLeft message ", priority = 5)
	public void veriftSFCSpendLeft() throws Exception {
		Assert.assertEquals(FTUE.SFCSpendLeftMessage().getText(), PropsUtil.getDataPropertyValue("SFCSpend"));
	}

	@Test(description = "verify finacial forcase FTUE Imag ", priority = 6)
	public void veriftSFCFTUEImage() throws Exception {
		Assert.assertTrue(FTUE.SFCFTUEImage().isDisplayed());

	}

	@Test(description = "verify finacial forcase FTUE Imag ", priority = 7)
	public void veriftSFCPaycheck() throws Exception {
		Assert.assertEquals(FTUE.SFCFTUENextPayCheck().getText(), PropsUtil.getDataPropertyValue("SFCPaycheck"));
	}

	@Test(description = "verify finacial forcase FTUE Imag ", priority = 8)
	public void veriftSFCFUTELinkAcc() throws Exception {
		Assert.assertTrue(FTUE.SFCFTUELinkAcc().isDisplayed());
	}
}
