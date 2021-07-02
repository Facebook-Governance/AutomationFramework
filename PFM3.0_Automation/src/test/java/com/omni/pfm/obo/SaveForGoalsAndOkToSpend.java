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

public class SaveForGoalsAndOkToSpend extends TestBase {
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

	@Test(description="creating user and adding account.", priority = 1, enabled = true)
	public void navigateTosfg() throws Exception
	{
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "SFG-01_02: Verifying landing page and creating first goal", groups = {
	"DesktopBrowsers,sanity" }, priority = 2, enabled =true )
	public void creatingGoalFirst() {

		boolean getStartedStatus=oboLoc.getStartedTab().isDisplayed();
		SeleniumUtil.click(oboLoc.getStartedTab());
		SeleniumUtil.waitForPageToLoad(3000);

		oboLoc.createGoalwithMultipleAccount("GOALNAME50", "1300", "100", "100");
		Assert.assertTrue(getStartedStatus);
	}

	@Test(description = "verifying goal get added as inprogress goal.", priority = 3, enabled = true)
	public void verifyAcntsPresentInGoal() {
		SeleniumUtil.waitForPageToLoad();
		int inprogressGoalSize=oboLoc.allInprogressGoal().size();
		Assert.assertEquals(inprogressGoalSize,1);
	}
	
	@Test(description = "verify after refresh SFG is loading or not.", priority = 4, enabled = true)
	public void verifySFGAfterRefresh() {
		d.navigate().refresh();	
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
	}
	@Test(description="verifying page header after creating goal.", priority = 5, enabled = true)
	public void verifyingTransactionHeader() {
		Assert.assertEquals(oboLoc.headerTitle().getText(),PropsUtil.getDataPropertyValue("SFGHeader"));
	}
	
	/*
	 * My Profile
	 */
	
	@Test(description="Verifying My Profile Page", priority = 6, enabled = true)
	public void verifyMyProfile() throws Exception
	{
		SeleniumUtil.click(oboLoc.myProfileTab());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(oboLoc.headerTitle().getText().trim(),PropsUtil.getDataPropertyValue("profileHeader").trim());
	}
	
	@Test(description="Verifying My Profile List", priority = 7, enabled = true)
	public void verifyMyProfileList() throws Exception
	{
		String expected[]=PropsUtil.getDataPropertyValue("ProfileList").split(",");
		for(int i=0;i<oboLoc.myProfileList().size();i++) {
			String actual=oboLoc.myProfileList().get(i).getText().trim().toLowerCase();
			Assert.assertEquals(actual, expected[i]);
		}
	}
	
	/*
	 * SFC
	 */

	
	/*
	 * Verifying Navigation
	 */
	
	}
