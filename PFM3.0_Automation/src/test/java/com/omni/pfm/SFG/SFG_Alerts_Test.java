/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.SFG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_Alerts_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_Alerts_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(SFG_Alerts_Test.class);

	SFG_Alerts_Loc Sfg_Alerts;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("SFG Alerts Test");
		login = new LoginPage();
		Sfg_Alerts = new SFG_Alerts_Loc(d);
		accountAdd = new AccountAddition();
	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 0, enabled = true)
	public void login() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "verify alert setting icon on SFG Page", dependsOnMethods = { "login" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void veriyingAlertSettingIcon() {
		Assert.assertTrue(Sfg_Alerts.alertSettingIcon().isDisplayed());
	}

	@Test(description = "verify header on clicking on alert setting.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "login" }, priority = 2, enabled = true)
	public void veriyingAlertSettingHeader() {
		SeleniumUtil.click(Sfg_Alerts.alertSetting());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(Sfg_Alerts.alertSettingHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AlertSettingHeader").trim());
	}

	@Test(description = "verify all inprogress goals should be visible on alert setting page.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "veriyingAlertSettingHeader" }, priority = 3, enabled = true)
	public void veriyingInprogressGoalsOnAlertSetting() {
		String expected[] = PropsUtil.getDataPropertyValue("AlertSettingGoals").split(",");
		for (int i = 0; i < Sfg_Alerts.alertSettingGoalNameHeader().size(); i++) {
			String actual = Sfg_Alerts.alertSettingGoalNameHeader().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "Verifying goals container present on alert setting page", dependsOnMethods = {
			"veriyingAlertSettingHeader" }, priority = 4, enabled = true)
	public void verifyingContainers() {
		String expected[] = PropsUtil.getDataPropertyValue("SFGAlertContainerList").split(",");
		for (int i = 1; i < Sfg_Alerts.containerList().size(); i++) // i=0
		{
			String actual = Sfg_Alerts.containerList().get(i).getText().trim().toLowerCase();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "Verifying goals dropdown should be bydefault minimised.", dependsOnMethods = {
			"veriyingAlertSettingHeader" }, priority = 5, enabled = true)
	public void verifyingGoalsMinimisedDropdown() {
		for (int i = 0; i < Sfg_Alerts.alertSettingGoalsDropdown().size(); i++) {
			Assert.assertTrue(Sfg_Alerts.alertSettingGoalsDropdown().get(i).getAttribute("class").trim()
					.contains(PropsUtil.getDataPropertyValue("GoalsMinimiseDropdown").trim()));
		}
	}

	@Test(description = "Verifying goals can be maximised one by one", dependsOnMethods = {
			"veriyingAlertSettingHeader" }, priority = 6, enabled = true)
	public void verifyingAllGoalMaximised() {

		for (int i = 0; i < Sfg_Alerts.alertSettingGoalsDropdown().size(); i++) {
			SeleniumUtil.click(Sfg_Alerts.alertSettingGoalsDropdown().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			Assert.assertFalse(Sfg_Alerts.alertSettingGoalsDropdown().get(i).getAttribute("class").trim()
					.contains(PropsUtil.getDataPropertyValue("GoalsMinimiseDropdown").trim()));
		}
	}

	@Test(description = "Verifying alert labels on alert setting page", dependsOnMethods = {
			"veriyingAlertSettingHeader" }, priority = 7, enabled = true)
	public void alertPOPupLabels() {
		if (Sfg_Alerts.verifyClosePopUp() != null) {
			SeleniumUtil.click(Sfg_Alerts.verifyClosePopUp());
		}
		SeleniumUtil.click(Sfg_Alerts.alertSettingGoalsDropdown().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		String expected[] = PropsUtil.getDataPropertyValue("AlertPopUpGoalLabels").split(",");
		for (int i = 0; i < Sfg_Alerts.AlertPOPupLabels().size(); i++) {
			String actual = Sfg_Alerts.AlertPOPupLabels().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "Verifying alert sub header on alert setting page", dependsOnMethods = {
			"alertPOPupLabels" }, priority = 8, enabled = true)
	public void alertPOPupSubLabels() {
		String expected[] = PropsUtil.getDataPropertyValue("AlertPopUpGoalSubLabels").split(",");
		for (int i = 0; i < Sfg_Alerts.AlertPOPupSubLabels().size(); i++) {
			String actual = Sfg_Alerts.AlertPOPupSubLabels().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "Verifying save button should be disabled by default.", dependsOnMethods = {
			"alertPOPupLabels" }, priority = 9, enabled = true)
	public void verifyingByDefaultSaveBtn() {
		Assert.assertTrue(Sfg_Alerts.AlertPOPupSaveBtn().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("ByDefaultSaveBtnState").trim()));
	}

	@Test(description = "Verifying save button should be disabled by default.", dependsOnMethods = {
			"alertPOPupLabels" }, priority = 10, enabled = true)
	public void verifyingToggleButtonCount() {
		int actual = Sfg_Alerts.AlertsToggleButtons().size();
		int expected = Integer.parseInt(PropsUtil.getDataPropertyValue("ExpectedToggleButtonNumber").trim());
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Verifying save button will enable ,when will on toggle button. ", dependsOnMethods = {
			"alertPOPupLabels" }, priority = 11, enabled = true)
	public void verifyingSaveButtonEnabled() {
		SeleniumUtil.click(Sfg_Alerts.AlertsToggleButtons().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertFalse(Sfg_Alerts.AlertPOPupSaveBtn().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("ByDefaultSaveBtnState").trim()));
	}

	@Test(description = "Verifying back to goal button should  ", dependsOnMethods = {
			"verifyingSaveButtonEnabled" }, priority = 12, enabled = true)
	public void verifyingBckToGoalButton() {
		if (Sfg_Alerts.verifyClosePopUp() != null) {
			SeleniumUtil.click(Sfg_Alerts.verifyClosePopUp());
		}

		if (PageParser.isElementPresent("backButtonForMobile", "SFG", null)) {
			Assert.assertTrue(Sfg_Alerts.verifyBackButtonForMobile().isDisplayed());
		} else {
			Assert.assertTrue(Sfg_Alerts.categoriesLevelBackBtn().isDisplayed());
		}
	}
}
