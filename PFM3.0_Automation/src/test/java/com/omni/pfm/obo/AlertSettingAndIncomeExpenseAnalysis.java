/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.obo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AlertSettingAndIncomeExpenseAnalysis extends TestBase {
	Logger logger=LoggerFactory.getLogger(TransactionsAndNetworth.class);
	OBO_Loc oboLoc;

	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("OBO");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		oboLoc=new OBO_Loc(d);
	}

	@Test(description="creating user and adding account.", priority = 0, enabled = false)
	public void login() throws Exception
	{
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();	
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "navigating to income analysis", priority = 1, enabled=true)

	public void navigateToIncomeAndExpenseAnalysis() throws Exception {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(oboLoc.continueButton());
		SeleniumUtil.click(oboLoc.goToAnalysis());
	}
	@Test(description="verifying Investment Holdings header after navigation.", priority = 2, enabled = true)
	public void verifyingInvestmentHeader() {
		Assert.assertEquals(oboLoc.headerTitle().getText().trim(),PropsUtil.getDataPropertyValue("ExpenseHeader").trim());
	}

	@Test(description="verifying chart", priority = 3, enabled = true)
	public void verifyingChart() {
		Assert.assertTrue(oboLoc.chartView().isDisplayed());
	}
	
	@Test(description="verifying content header", priority = 4, enabled = true)
	public void verifyingContentHeader() {
		Assert.assertTrue(oboLoc.contentTitle().isDisplayed());
		Assert.assertTrue(oboLoc.topFiveTransacTable().isDisplayed());
	}
	

    /*
     * AlertSetting
     */
	
	@Test(description="creating user and adding account.", priority = 5, enabled = true)
	public void navigateToAlertSetting() throws Exception
	{
		PageParser.forceNavigate("AlertSetting", d);
		SeleniumUtil.waitForPageToLoad();
		
	}

	@Test(description="verifying alert setting header after navigation.", priority = 6, enabled = true)
	public void verifyingAlertSettingHeader() {
		Assert.assertEquals(oboLoc.headerTitle().getText().trim(),PropsUtil.getDataPropertyValue("AlertSettingHeader").trim());
	}
	
	@Test(description="Verifying containers present on alert setting page", priority = 7, enabled = true)
	public void verifyingContainers() {
		String expected[]=PropsUtil.getDataPropertyValue("AlertContainerList").split(",");
		for(int i=0;i<oboLoc.containerList().size();i++) {
			String actual=oboLoc.containerList().get(i).getText().trim().toLowerCase();
			Assert.assertEquals(actual, expected[i]);
		}
		
	}
	
	
	

}
