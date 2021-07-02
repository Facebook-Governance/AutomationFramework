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

public class AccountsGroupsAndAccountSetting extends TestBase {
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
	
	@Test(description="navigating to account groups page.", priority = 1, enabled = true)
	public void navigateToAccountGroups() {
		SeleniumUtil.waitForPageToLoad(3000);
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="verifying AccountGroups header.", priority = 2, enabled = true)
	public void verifyingAccountGroupsHeader() {
		Assert.assertEquals(oboLoc.headerTitle().getText(),PropsUtil.getDataPropertyValue("AccountGroupHeader"));
	}

	@Test(description = "verify groups are succesfully created", priority = 3, enabled=true)

	public void groupCreationValidation() throws Exception {

		SeleniumUtil.click(oboLoc.createGroupBtn());

		oboLoc.groupNameTxtBox().clear();
		SeleniumUtil.click(oboLoc.groupNameTxtBox());

		oboLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("TestingGroup"));

		oboLoc.selectCheckBox2();
		boolean status=oboLoc.createOrUpdateGroup().isEnabled();
		SeleniumUtil.click(oboLoc.createOrUpdateGroup());
		Assert.assertTrue(status);
	}

	@Test(description = " refreshing and come back to the same page", priority=4, enabled=true)
	public void refreshAndComeBack() throws InterruptedException {
		d.navigate().refresh();	
		SeleniumUtil.waitForPageToLoad();	
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	/*
	 * Account Settings
	 */

	@Test(description="navigating to account Settings page.", priority = 5, enabled = true)
	public void navigateToAccountSettings() {
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="verifying page without budget.", priority = 6, enabled = true)
	public void verifyingAccountSettingHeader() {
		Assert.assertEquals(oboLoc.headerTitle().getText(),PropsUtil.getDataPropertyValue("AccountSettingHeader"));
	}

	@Test(description = "Verify that edit text and icon are displayed on the home page", priority = 7,enabled=true)

	public void verifyLandingPageDetails() throws InterruptedException {

		oboLoc.verifyEditIconAndText();
	}

	@Test(description = "Verify that delete text and icon are displayed on the home page", priority = 8,enabled=true)

	public void verifyLandingPageDeleteInfo() throws InterruptedException {

		oboLoc.verifyDeleteIconAndText();
	}

}
