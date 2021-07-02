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

public class TransactionsAndNetworth extends TestBase{
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
	}
	
	@Test(description="navigating to Transaction page.", priority = 1, enabled = true)
	public void navigateToTrasaction() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();	
	}
	
	@Test(description="verifying Transaction header.", priority = 2, enabled = true)
	public void verifyingTransactionHeader() {
		Assert.assertEquals(PropsUtil.getDataPropertyValue("TransactionHeader").trim(), oboLoc.headerTitle().getText().trim());
	}
	
	@Test(description="adding Transaction.", priority = 3, enabled = true)
	public void addingTransaction() {
		SeleniumUtil.click(oboLoc.addManualLink_AMT());
		oboLoc.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 7, 1, 1,PropsUtil.getDataPropertyValue("Note151"));

	}
	@Test(description="verifying Added Transaction status", priority = 4, enabled = true)
	public void verifyingAddedTransaction() {
		SeleniumUtil.waitForPageToLoad(2000);
		String expected[]=PropsUtil.getDataPropertyValue("ProjectedPostedStatus").split(",");
		for(int i=0;i<oboLoc.projectedPostedHeader().size();i++) {
			String actual=oboLoc.projectedPostedHeader().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
		
	}
	
	@Test(description=" verifying Refreshing and going back to same page should display the page header", priority = 5, enabled = true)
	public void backToSamePage() throws Exception
	{
		d.navigate().refresh();	
		SeleniumUtil.waitForPageToLoad();	
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();	
		Assert.assertEquals(PropsUtil.getDataPropertyValue("TransactionHeader").trim(), oboLoc.headerTitle().getText().trim());
	}
	
	/*
	 * Verifying For Networth
	 * 
	 */
	
	@Test(description="navigating to Networth page.", priority = 6, enabled = true)
	public void navigateToNetworth() {
		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.waitForPageToLoad();	
	}
	
	@Test(description="verifying Networth header.", priority = 7, enabled = true)
	public void verifyingNetworthHeader() {
		SeleniumUtil.click(oboLoc.continueButton());
		SeleniumUtil.click(oboLoc.goToNetworthFTUE());
		
		Assert.assertEquals(PropsUtil.getDataPropertyValue("NetworthHeader").trim(), oboLoc.headerTitle().getText().trim());
	}
	
	@Test(description="verifying Networth chart is displayed.", priority = 8, enabled = true)
	public void verifyNetworthChart() {
		Assert.assertTrue(oboLoc.networthChart_Obo().isDisplayed());
	}
	
	@Test(description="verifying Networth table is displayed.", priority = 9, enabled = true)
	public void verifyNetworthTable() {
		Assert.assertTrue(oboLoc.networthTable_Obo().isDisplayed());
	}
	
	
	
	
}
