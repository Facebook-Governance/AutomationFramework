/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.obo;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsAndInvestmentHoldings extends TestBase {

	Logger logger=LoggerFactory.getLogger(AccountsAndInvestmentHoldings.class);
	LoginPage login;
	AccountAddition accountAdd;
	OBO_Loc oboLoc;
	String fileName=System.getProperty("user.dir")+File.separator+"downloads"+File.separator+"download.csv";

	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("OBO");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login=new LoginPage();
		accountAdd=new AccountAddition();
		oboLoc=new OBO_Loc(d);

	}
	@Test(description="creating user and adding account.", priority = 1, enabled = true)
	public void login() throws Exception
	{
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		d.navigate().refresh();		
	}

	@Test(description="navigating to Accounts page.", priority = 2, enabled = true)
	public void navigateToAccounts() {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="verifying Investment Holdings header after navigation.", priority = 3, enabled = true)
	public void verifyingAccountsHeader() {
		Assert.assertEquals(PropsUtil.getDataPropertyValue("AccountsHeader").trim(), oboLoc.headerTitle().getText().trim());
	}

	@Test(description="verifying account type views.", priority = 4, enabled = true)
	public void verifyingAccountsVIew() {
		String expected[]=PropsUtil.getDataPropertyValue("AccountsView").split(",");
		for(int i=0;i<oboLoc.AccountsView().size();i++) {
			String actual=oboLoc.AccountsView().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}


	/*
	 * Investment Holding.
	 */  


	@Test(description="navigating to Investment Holdings.", priority = 5, enabled = true)
	public void navigateToInvestmentHolding() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(oboLoc.continueButton());
		SeleniumUtil.click(oboLoc.goToFinappCoachMark());
	}

	@Test(description="verifying Investment Holdings header after navigation.", priority = 6, enabled = true)
	public void verifyingInvestmentHeader() {
		Assert.assertEquals(PropsUtil.getDataPropertyValue("InvestmentHoldingHeader").trim(), oboLoc.headerTitle().getText().trim());
	}

	@Test(description="verifying Holding Balance Sections in Investment holding.",priority=7,enabled=true)
	public void holdingBalanceSection() {
		Assert.assertTrue(oboLoc.holdingBalanceSection().isDisplayed());
	}
	@Test(description="verifying filter Sections in Investment holding.",priority=8,enabled=true)
	public void filterSection() {
		Assert.assertTrue(oboLoc.filterSection().isDisplayed());
	}
	@Test(description="verifying chart Section in Investment holding.",priority=9,enabled=true)
	public void chartSection() {
		Assert.assertTrue(oboLoc.chartSection().isDisplayed());
	}
	@Test(description="verifying holdings table Sections in Investment holding.",priority=10,enabled=true)
	public void holdingsTable() {
		Assert.assertTrue(oboLoc.holdingsTable().isDisplayed());
	}
}
