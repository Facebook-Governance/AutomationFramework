/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.BudgetV2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_NeedsWants_BannerPage_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_EditPopup_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GroupDropdown_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_TableRevamp_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_NeedsWantsBannerPage_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_NeedsWantsBannerPage_Test.class);
	String changedcat;
	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_EditPopup_Loc budget_Edit;
	Budget_Summary_TableRevamp_Loc bud_TableRevamp;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_Summary_GroupDropdown_Loc budget_Gdd;
	Budget_NeedsWants_BannerPage_Loc needsWantsBanner;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Budget");

		needsWantsBanner = new Budget_NeedsWants_BannerPage_Loc(d);
		budget_Edit = new Budget_Summary_EditPopup_Loc(d);
		bud_TableRevamp = new Budget_Summary_TableRevamp_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);

	}

	@Test(description = "Verify Login Happens Successfully", groups = {
			"DesktopBrowsers" }, priority = 1, enabled = true)
	public void login() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("bbudget.site16441.5", "site16441.5");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Click on  the GetStarted button");
		Assert.assertTrue(budget_Gdd.CreateBudgetHeader().isDisplayed(), "Create Budget Header is displayed");
	}

	@Test(description = "AT-129117,AT-129119:Verify that banner should be displayed in budget setup page.", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void budgetSetupNeedsWantsBannerMessage() throws Exception {

		needsWantsBanner.createBudgetSetup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));
		Assert.assertEquals(needsWantsBanner.needsAndWantsBannerLink().getText(),
				PropsUtil.getDataPropertyValue("NeedsWantsBannerMessage"), "Not same message");
	}

	@Test(description = "AT-129120,AT-129121,AT-129122,AT-129123,AT-129515,AT-129516:Verify that after click on the banner, popup should be opened and display the following message on the header,'Needs and Wants' message is displayed.", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "login" }, enabled = true)
	public void budgetSetupNeedsWantsPopUpMessage() throws Exception {

		SeleniumUtil.click(needsWantsBanner.needsAndWantsBannerLink());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(needsWantsBanner.needsAndWantsPopupHeader().getText(),
				PropsUtil.getDataPropertyValue("NeedsWantsPopUpHeader"), "Not same message");
		Assert.assertEquals(needsWantsBanner.needsAndWantsPopupMessage().getText(),
				PropsUtil.getDataPropertyValue("NeedsAndWantsPopupMessage"), "Not same message");
		Assert.assertEquals(needsWantsBanner.needsToggleBtn().getText(),
				PropsUtil.getDataPropertyValue("NeedsOnToggleBtn"), "Not same message");
		Assert.assertEquals(needsWantsBanner.wantsToggleBtn().getText(),
				PropsUtil.getDataPropertyValue("WantsOnToggleBtn"), "Not same message");
		Assert.assertEquals(needsWantsBanner.updateBtnPopUpPage().getText(),
				PropsUtil.getDataPropertyValue("UpdateOnPopUpPage"), "Not same message");

	}

	@Test(description = "AT-129124,AT-129125:Verify that after after changing the categories by click on UPDATE button, then confirmation popup should be opened.", groups = {
			"DesktopBrowsers" }, priority = 4, dependsOnMethods = { "login" }, enabled = true)
	public void budgetNeedsWantsUpdatePopup() throws Exception {

		SeleniumUtil.click(needsWantsBanner.needsToggleBtn());
		SeleniumUtil.waitForPageToLoad();
		changedcat = needsWantsBanner.changedCatogryName().getText().toString();
		SeleniumUtil.click(needsWantsBanner.updateBtnPopUpPage());
		Assert.assertEquals(needsWantsBanner.updatePopUpMessage().getText(),
				PropsUtil.getDataPropertyValue("UpdatePopUpMessage"), "Not same message");
		Assert.assertEquals(needsWantsBanner.yesChangeMessage().getText(),
				PropsUtil.getDataPropertyValue("YesChangeMessage"), "Not same message");
		Assert.assertEquals(needsWantsBanner.dontChangeMessage().getText(),
				PropsUtil.getDataPropertyValue("DontChangeMessage"), "Not same message");

	}

	@Test(description = "AT-129127,AT-129128:Verify that after open popup for category chanages , after click on update button, the message should be displayed,'Changing the Needs/Wants mapping for categories will impact all your existing budgets. Do you want to proceed?'.", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "login" }, enabled = true)
	public void dontChangePopUpConfirm() throws Exception {

		SeleniumUtil.click(needsWantsBanner.dontChangeMessage());
		SeleniumUtil.click(needsWantsBanner.closeIconPopUp());
		needsWantsBanner.openWantsContainer();
		List<WebElement> needsCatList = needsWantsBanner.needsBudgetSetupCatList();
		boolean status = false;
		for (int i = 0; i <= needsCatList.size(); i++) {
			if (needsCatList.get(i).getText().equals(changedcat)) {
				status = true;
				break;
			}
		}
		Assert.assertTrue(status, "Category is not changed");

	}

	@Test(description = "AT-129129,AT-129130,AT-129131,AT-129132:Verify that user click on , 'Don't Change' button, then immediately both that pop up  should be closed and still retain in the Needs and Wants update pop up page and changes should not be updated in categories and containers.", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "login" }, enabled = true)
	public void changeCategNeedsToWants() throws Exception {

		SeleniumUtil.click(needsWantsBanner.needsAndWantsBannerLink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(needsWantsBanner.wantsToggleBtn());
		SeleniumUtil.waitForPageToLoad();
		changedcat = needsWantsBanner.changedCatogryName().getText().toString();
		SeleniumUtil.click(needsWantsBanner.updateBtnPopUpPage());
		SeleniumUtil.click(needsWantsBanner.yesChangeMessage());
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.waitForPageToLoad();
		needsWantsBanner.openWantsContainer();
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> wantsCatList = needsWantsBanner.wantsBudgetSetupCatList();
		boolean status = false;
		for (int i = 0; i < wantsCatList.size(); i++) {
			if (wantsCatList.get(i).getText().equals(changedcat)) {
				status = true;
				break;
			}
		}
		Assert.assertFalse(status, "Category is Changed to Wants Successfully");

	}

	@Test(description = "AT-129133,AT-129134,AT-129135:Verify that if the user changing all the categories from 'Wants' to 'Needs' then under  container then the following message should be displayed.'No categories are currently mapped to {needs/wants}. If you want to change mapping, click here.'", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "login" }, enabled = true)
	public void allCatChangeToNeedsContainer() throws Exception {

		SeleniumUtil.click(needsWantsBanner.needsAndWantsBannerLink());
		SeleniumUtil.waitForPageToLoad();
		By allWantsCategoriesActive = needsWantsBanner.allWantsCategoriesActive;
		while (SeleniumUtil.isDisplayed(allWantsCategoriesActive, 0)) {
			SeleniumUtil.click(allWantsCategoriesActive);
		}

		SeleniumUtil.click(needsWantsBanner.updateBtnPopUpPage());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(needsWantsBanner.yesChangeMessage());
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		needsWantsBanner.openWantsContainer();
		SeleniumUtil.waitFor(2);
		Assert.assertEquals(needsWantsBanner.noCategWantsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInWantsContainer"), "Not same message");
		Assert.assertEquals(needsWantsBanner.noCategMsgToNeedsWantsPopupLink().getText(),
				PropsUtil.getDataPropertyValue("noCategMsgToNeedsWantsPopupLinkMessage"), "Not same message");

	}

	@Test(description = "AT-129135:Verify that after changed categories immediately should be reflected in all the containers properly.", groups = {
			"DesktopBrowsers" }, priority = 8, dependsOnMethods = { "login" }, enabled = true)
	public void noCategNeedsContainer() throws Exception {

		SeleniumUtil.click(needsWantsBanner.noCategMsgToNeedsWantsPopupLink());
		SeleniumUtil.waitForPageToLoad();
		By allNeedsCategoriesActive = needsWantsBanner.allNeedsCategoriesActive;
		while (SeleniumUtil.isDisplayed(allNeedsCategoriesActive, 0)) {
			SeleniumUtil.click(allNeedsCategoriesActive);
		}
		SeleniumUtil.click(needsWantsBanner.updateBtnPopUpPage());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(needsWantsBanner.yesChangeMessage());
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		needsWantsBanner.openNeedsContainer();
		Assert.assertEquals(needsWantsBanner.noCategNeedsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInNeedsContainer"), "Not same message");

	}

	@Test(description = "AT-129526,AT-129527:Verify that after updated categories changes using the banner, then all other created bugets group also be updated.", groups = {
			"DesktopBrowsers" }, priority = 9, dependsOnMethods = { "login" }, enabled = true)
	public void budgetSummaryPage() throws Exception {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.click(budget_Gdd.DoneBtn());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		Assert.assertFalse(needsWantsBanner.addBudCategBudSummary().isDisplayed());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(needsWantsBanner.noCategNeedsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInNeedsContainer"), "No categories in needs container message is not as expected");
	}

	@Test(description = "AT-129528,AT-129529:Verify that after updated the categories  it should be displayed category name and amount under proper container .", groups = {
			"DesktopBrowsers" }, priority = 10, dependsOnMethods = { "login" }, enabled = true)
	public void budgetHistory() throws Exception {

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery6MonthLabel")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(needsWantsBanner.noCategNeedsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInNeedsContainer"), "Not same message");
		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_SummeryThisYearLabel")));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(needsWantsBanner.noCategNeedsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInNeedsContainer"), "Not same message");
		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery12MonthLabel")));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(needsWantsBanner.noCategNeedsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInNeedsContainer"), "Not same message");
	}

	@Test(description = "AT-129519,AT-129522,AT-129524,AT-129525:Verify that while deleting the budget in Income category under Income container'Minimum one income category is required to create/edit budget.' info should be displayed in that popup.", groups = {
			"DesktopBrowsers" }, priority = 11, dependsOnMethods = { "login" }, enabled = true)
	public void budgetHistoryAnotherBudget() throws Exception {
		SeleniumUtil.click(budget_Edit.CreateBudgetBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Edit.GroupDropdown());
		SeleniumUtil.click(budget_Edit.createGrpButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.Group_name());
		budget_Gdd.Group_name().sendKeys("My Budget1");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		Assert.assertEquals(needsWantsBanner.noCategNeedsContainerMessage().getText(),
				PropsUtil.getDataPropertyValue("NoCagegMessageInNeedsContainer"), "Not same message");
		Assert.assertEquals(needsWantsBanner.needsAndWantsBannerLink().getText(),
				PropsUtil.getDataPropertyValue("NeedsWantsBannerMessage"), "Not same message");
	}

	@Test(description = "AT-129518,AT-129520,AT-129521,AT-129523:Verify that, after click on the banner, then 'Needs and Wants' pop up page should be opened with 'Needs' and 'Wants' toggle button with update button.", groups = {
			"DesktopBrowsers" }, priority = 12, dependsOnMethods = { "login" }, enabled = true)
	public void needsWantsPopUpSummaryPage() throws Exception {
		SeleniumUtil.click(needsWantsBanner.needsAndWantsBannerLink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(needsWantsBanner.wantsToggleBtn());
		SeleniumUtil.waitForPageToLoad();

		changedcat = needsWantsBanner.changedCatogryName().getText().toString();
		SeleniumUtil.click(needsWantsBanner.updateBtnPopUpPage());
		SeleniumUtil.click(needsWantsBanner.yesChangeMessage());
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		List<WebElement> wantsCatList = needsWantsBanner.wantsBudgetSummaryCatList();

		boolean status = false;
		for (int i = 0; i < wantsCatList.size(); i++) {
			String presentCategory = wantsCatList.get(i).getText();
			logger.info("Present Category :: " + presentCategory);
			if (presentCategory.equals(changedcat)) {
				status = true;
				break;
			}
		}
		Assert.assertFalse(status, "Category is removed from to wants");
		List<WebElement> needsCatList = needsWantsBanner.needsBudgetSummaryCatList();
		status = false;
		for (int i = 0; i < needsCatList.size(); i++) {
			String presentCategory = needsCatList.get(i).getText();
			logger.info("Present Category :: " + presentCategory);
			if (presentCategory.equals(changedcat)) {
				status = true;
				break;
			}
		}
		Assert.assertTrue(status, "Category is not moved to needs successfully");
	}
}
