/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.BudgetV2;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class Budget_BudgetSummary_SectionLevel_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummary_SectionLevel_Test.class);
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

	@Test(description = "AT-136325,AT-136326,AT-136327:Verify that 'All Needs' label is displayed under Needs container in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void budgetSectionLevelLabels() throws Exception {

		needsWantsBanner.createBudgetSetup3(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));

		Assert.assertEquals(needsWantsBanner.allWants().getText(), PropsUtil.getDataPropertyValue("AllWants_BS"),
				"Not same message-All Wants");
		Assert.assertEquals(needsWantsBanner.allNeeds().getText(), PropsUtil.getDataPropertyValue("AllNeeds_BS"),
				"Not same message-All Needs");
		Assert.assertEquals(needsWantsBanner.allIncome().getText(), PropsUtil.getDataPropertyValue("AllIncome_BS"),
				"Not same message-All Income");
	}

	@Test(description = "AT-136331:Verify that total Budgeted amount  is displayed in 'All Wants' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllWantsBudgetedTotalAmt() throws Exception {

		List<WebElement> allWantsCatBudgetAmt = needsWantsBanner.allWantsCatBudgetedAmt();
		float ExpectedBudgettotalAmount = 0;
		for (int i = 0; i < allWantsCatBudgetAmt.size(); i++) {
			logger.info(allWantsCatBudgetAmt.get(i).getText().replace(",", ""));
			float allwantsBudget = Float.parseFloat(allWantsCatBudgetAmt.get(i).getText().replace(",", ""));
			ExpectedBudgettotalAmount += allwantsBudget;
		}
		logger.info("Total wants Expected amount is :: " + ExpectedBudgettotalAmount);

		float actualBudgetedTotalAmt = Float
				.parseFloat(needsWantsBanner.allWantsBudgetedTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Actual amount is :: " + actualBudgetedTotalAmt);

		logger.info("Actual percentage :: " + actualBudgetedTotalAmt);
		if (!(ExpectedBudgettotalAmount == actualBudgetedTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total amount ::\""
					+ ExpectedBudgettotalAmount + "\" and Actual budgeted amount :: \"" + actualBudgetedTotalAmt);

		}

	}

	@Test(description = "AT-136330:Verify that total Left/Over amount  is displayed in 'All Needs' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 4, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllWantsSpentTotalAmt() throws Exception {

		List<WebElement> allWantsCatSpentAmt = needsWantsBanner.allWantsCatSpentAmt();
		float ExpectedSpenttotalAmount = 0;
		for (int i = 0; i < allWantsCatSpentAmt.size(); i++) {
			logger.info(allWantsCatSpentAmt.get(i).getText().replace(",", ""));
			float allwantsSpent = Float.parseFloat(allWantsCatSpentAmt.get(i).getText().substring(1).replace(",", ""));

			ExpectedSpenttotalAmount += allwantsSpent;
		}
		logger.info("Total wants Spent Expected amount is :: " + ExpectedSpenttotalAmount);

		float actualSpentTotalAmt = Float
				.parseFloat(needsWantsBanner.allWantsSpentTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Spent Actual amount is :: " + actualSpentTotalAmt);

		logger.info("Actual percentage :: " + actualSpentTotalAmt);
		if (!(ExpectedSpenttotalAmount == actualSpentTotalAmt)) {
			Assert.fail("Amount is not matching :: " + " Expected total Spent amount ::\"" + ExpectedSpenttotalAmount
					+ "\" and Actual spent amount :: \"" + actualSpentTotalAmt);

		}

	}

	@Test(description = "AT-136331:Verify that total Budgeted amount  is displayed in 'All Wants' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllWantsLeftOverTotalAmt() throws Exception {

		List<WebElement> allWantsCatLeftOverAmt = needsWantsBanner.allWantsCatLeftOverAmt();
		float ExpectedLeftOvertotalAmount = 0;
		for (int i = 0; i < allWantsCatLeftOverAmt.size(); i++) {
			logger.info("LeftOverAmount is :: "
					+ allWantsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allWantsLeftOver = Float
					.parseFloat(allWantsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));

			ExpectedLeftOvertotalAmount += allWantsLeftOver;
		}
		logger.info("Total wants Left/Over Expected amount is :: " + ExpectedLeftOvertotalAmount);

		float actualLeftOverTotalAmt = Float
				.parseFloat(needsWantsBanner.allWantsLeftOverTotalAmt().getText().substring(1).replace(",", ""));
		DecimalFormat df = new DecimalFormat("0.00");
		ExpectedLeftOvertotalAmount = Float.parseFloat(df.format(ExpectedLeftOvertotalAmount));
		logger.info("Total wants Left/Over Actual amount is :: " + actualLeftOverTotalAmt);

		logger.info("Actual percentage :: " + actualLeftOverTotalAmt);
		if (!(ExpectedLeftOvertotalAmount == actualLeftOverTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total Left/Over amount ::\""
					+ ExpectedLeftOvertotalAmount + "\" and Actual Total Left/Over amount :: \""
					+ actualLeftOverTotalAmt);

		}

	}

	@Test(description = "AT-136328:Verify that total Budgeted amount is displayed in 'All Needs' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllNeedsBudgetedTotalAmt() throws Exception {

		List<WebElement> allNeedsCatBudgetAmt = needsWantsBanner.allNeedsCatBudgetedAmt();
		float ExpectedBudgettotalAmount = 0;
		for (int i = 0; i < allNeedsCatBudgetAmt.size(); i++) {
			logger.info(allNeedsCatBudgetAmt.get(i).getText().replace(",", ""));
			float allNeedsBudget = Float.parseFloat(allNeedsCatBudgetAmt.get(i).getText().replace(",", ""));
			ExpectedBudgettotalAmount += allNeedsBudget;
		}
		logger.info("Total wants Expected amount is :: " + ExpectedBudgettotalAmount);

		float actualBudgetedTotalAmt = Float
				.parseFloat(needsWantsBanner.allNeedsBudgetedTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Actual amount is :: " + actualBudgetedTotalAmt);

		logger.info("Actual percentage :: " + actualBudgetedTotalAmt);
		if (!(ExpectedBudgettotalAmount == actualBudgetedTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total amount ::\""
					+ ExpectedBudgettotalAmount + "\" and Actual budgeted amount :: \"" + actualBudgetedTotalAmt);

		}

	}

	@Test(description = "AT-136329:Verify that total Spent amount is displayed in 'All Needs' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllNeedsSpentTotalAmt() throws Exception {

		List<WebElement> allWantsCatSpentAmt = needsWantsBanner.allNeedsCatSpentAmt();
		float ExpectedSpenttotalAmount = 0;
		for (int i = 0; i < allWantsCatSpentAmt.size(); i++) {
			logger.info(allWantsCatSpentAmt.get(i).getText().replace(",", ""));
			float allwantsSpent = Float.parseFloat(allWantsCatSpentAmt.get(i).getText().substring(1).replace(",", ""));

			ExpectedSpenttotalAmount += allwantsSpent;
		}
		logger.info("Total wants Spent Expected amount is :: " + ExpectedSpenttotalAmount);

		float actualSpentTotalAmt = Float
				.parseFloat(needsWantsBanner.allNeedsSpentTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Spent Actual amount is :: " + actualSpentTotalAmt);

		logger.info("Actual percentage :: " + actualSpentTotalAmt);
		if (!(ExpectedSpenttotalAmount == actualSpentTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total Spent amount ::\""
					+ ExpectedSpenttotalAmount + "\" and Actual spent amount :: \"" + actualSpentTotalAmt);

		}

	}

	@Test(description = "AT-136330:Verify that total Left/Over amount  is displayed in 'All Needs' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 8, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllNeedsLeftOverTotalAmt() throws Exception {

		List<WebElement> allNeedsCatLeftOverAmt = needsWantsBanner.allNeedsCatLeftOverAmt();
		float ExpectedLeftOvertotalAmount = 0;
		for (int i = 0; i < allNeedsCatLeftOverAmt.size(); i++) {
			logger.info("LeftOverAmount is :: "
					+ allNeedsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allNeedsLeftOver = Float
					.parseFloat(allNeedsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));

			ExpectedLeftOvertotalAmount += allNeedsLeftOver;
		}
		logger.info("Total wants Left/Over Expected amount is :: " + ExpectedLeftOvertotalAmount);

		float actualLeftOverTotalAmt = Float
				.parseFloat(needsWantsBanner.allNeedsLeftOverTotalAmt().getText().substring(1).replace(",", ""));
		DecimalFormat df = new DecimalFormat("0.00");
		ExpectedLeftOvertotalAmount = Float.parseFloat(df.format(ExpectedLeftOvertotalAmount));
		logger.info("Total wants Left/Over Actual amount is :: " + actualLeftOverTotalAmt);

		logger.info("Actual percentage :: " + actualLeftOverTotalAmt);
		if (!(ExpectedLeftOvertotalAmount == actualLeftOverTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total Left/Over amount ::\""
					+ ExpectedLeftOvertotalAmount + "\" and Actual Total Left/Over amount :: \""
					+ actualLeftOverTotalAmt);

		}

	}

	@Test(description = "AT-136334:Verify that total Budgeted amount  is displayed in 'All Income' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 9, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllIncomeBudgetedTotalAmt() throws Exception {

		List<WebElement> allIncomeCatBudgetAmt = needsWantsBanner.allIncomeCatBudgetedAmt();
		float ExpectedBudgettotalAmount = 0;
		for (int i = 0; i < allIncomeCatBudgetAmt.size(); i++) {
			logger.info(allIncomeCatBudgetAmt.get(i).getText().replace(",", ""));
			float allIncomeBudget = Float.parseFloat(allIncomeCatBudgetAmt.get(i).getText().replace(",", ""));
			ExpectedBudgettotalAmount += allIncomeBudget;
		}
		logger.info("Total wants Expected amount is :: " + ExpectedBudgettotalAmount);

		float actualBudgetedTotalAmt = Float
				.parseFloat(needsWantsBanner.allIncomeBudgetedTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Actual amount is :: " + actualBudgetedTotalAmt);

		logger.info("Actual percentage :: " + actualBudgetedTotalAmt);
		if (!(ExpectedBudgettotalAmount == actualBudgetedTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total amount ::\""
					+ ExpectedBudgettotalAmount + "\" and Actual budgeted amount :: \"" + actualBudgetedTotalAmt);

		}

	}

	@Test(description = "AT-136335:Verify that total Earned amount  is displayed in 'All Income' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 10, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllIncomeSpentTotalAmt() throws Exception {

		List<WebElement> allWantsCatSpentAmt = needsWantsBanner.allIncomeCatSpentAmt();
		float ExpectedSpenttotalAmount = 0;
		for (int i = 0; i < allWantsCatSpentAmt.size(); i++) {
			logger.info(allWantsCatSpentAmt.get(i).getText().replace(",", ""));
			float allIncomeSpent = Float.parseFloat(allWantsCatSpentAmt.get(i).getText().substring(1).replace(",", ""));

			ExpectedSpenttotalAmount += allIncomeSpent;
		}
		logger.info("Total wants Spent Expected amount is :: " + ExpectedSpenttotalAmount);

		float actualSpentTotalAmt = Float
				.parseFloat(needsWantsBanner.allIncomeSpentTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Spent Actual amount is :: " + actualSpentTotalAmt);

		logger.info("Actual percentage :: " + actualSpentTotalAmt);
		if (!(ExpectedSpenttotalAmount == actualSpentTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total Spent amount ::\""
					+ ExpectedSpenttotalAmount + "\" and Actual spent amount :: \"" + actualSpentTotalAmt);

		}

	}

	@Test(description = "AT-136336:Verify that total Left/Over amount  is displayed in 'All Income' row and under Budget filter in Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 11, dependsOnMethods = { "login" }, enabled = true)
	public void verifyAllIncomeLeftOverTotalAmt() throws Exception {

		List<WebElement> allIncomeCatLeftOverAmt = needsWantsBanner.allIncomeCatLeftOverAmt();
		float ExpectedLeftOvertotalAmount = 0;
		for (int i = 0; i < allIncomeCatLeftOverAmt.size(); i++) {
			logger.info("LeftOverAmount is :: "
					+ allIncomeCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allIncomeLeftOver = Float
					.parseFloat(allIncomeCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));

			ExpectedLeftOvertotalAmount += allIncomeLeftOver;
		}
		logger.info("Total wants Left/Over Expected amount is :: " + ExpectedLeftOvertotalAmount);

		float actualLeftOverTotalAmt = Float
				.parseFloat(needsWantsBanner.allIncomeLeftOverTotalAmt().getText().substring(1).replace(",", ""));
		DecimalFormat df = new DecimalFormat("0.00");
		ExpectedLeftOvertotalAmount = Float.parseFloat(df.format(ExpectedLeftOvertotalAmount));
		logger.info("Total wants Left/Over Actual amount is :: " + actualLeftOverTotalAmt);

		logger.info("Actual percentage :: " + actualLeftOverTotalAmt);
		if (!(ExpectedLeftOvertotalAmount == actualLeftOverTotalAmt)) {
			Assert.fail("Percentage is matching at index at :: " + " Expected total Left/Over amount ::\""
					+ ExpectedLeftOvertotalAmount + "\" and Actual Total Left/Over amount :: \""
					+ actualLeftOverTotalAmt);

		}
	}

	@Test(description = "AT-136343,AT-136344:Verify that if the user edit any category, it should be edited immediately and amount  should be displayed in total Budget,total Spent/Saved/Earned,total Left/Over amount.", groups = {
			"DesktopBrowsers" }, priority = 12, dependsOnMethods = { "login" }, enabled = true)
	public void verifyEditCategory() throws Exception {

		logger.info(">>>>> Verifying APPLY button update the amount which edit ");
		SeleniumUtil.click(budget_Edit.EditTextBox().get(1)); //
		SeleniumUtil.waitForPageToLoad();
		budget_Edit.AmountField().clear();

		logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
		SeleniumUtil.click(budget_Edit.AmountField());

		budget_Edit.AmountField().sendKeys("1,000.00");
		SeleniumUtil.click(budget_Edit.ApplyBtn());
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> allWantsCatBudgetAmt = needsWantsBanner.allWantsCatBudgetedAmt();
		float ExpectedBudgettotalAmount = 0;
		for (int i = 0; i < allWantsCatBudgetAmt.size(); i++) {
			logger.info(
					"LeftOverAmount is :: " + allWantsCatBudgetAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allWantsBudget = Float
					.parseFloat(allWantsCatBudgetAmt.get(i).getText().replace(",", "").replace("$", "")); //
			logger.info(allWantsCatBudgetAmt.get(i).getText().replace(",", "")); // float
			allWantsBudget = Float.parseFloat(allWantsCatBudgetAmt.get(i).getText().replace(",", ""));
			ExpectedBudgettotalAmount += allWantsBudget;
		}
		logger.info("Total wants Expected amount is :: " + ExpectedBudgettotalAmount);

		float actualBudgetedTotalAmt = Float
				.parseFloat(needsWantsBanner.allWantsBudgetedTotalAmt().getText().substring(1).replace(",", ""));
		DecimalFormat df = new DecimalFormat("0.00");
		ExpectedBudgettotalAmount = Float.parseFloat(df.format(ExpectedBudgettotalAmount));
		logger.info("Total wants Actual amount is :: " + actualBudgetedTotalAmt);

		logger.info("Actual percentage :: " + actualBudgetedTotalAmt);
		if (!(ExpectedBudgettotalAmount == actualBudgetedTotalAmt)) {
			Assert.fail("Amount is not matching  :: " + " Expected total amount ::\"" + ExpectedBudgettotalAmount
					+ "\" and Actual budgeted amount :: \"" + actualBudgetedTotalAmt);

		}

	}

	@Test(description = "AT-136340,AT-136342:If the user changes categories through 'Needs and Wants' banner, the totoal budgeted and spent amount should be changed as per total amount.", groups = {
			"DesktopBrowsers" }, priority = 13, dependsOnMethods = { "login" }, enabled = true)

	public void editNeedsBannerCategory() throws Exception {
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
		for (int i = 0; i < wantsCatList.size(); i++) {
			if (wantsCatList.get(i).getText().equals(changedcat)) {
				break;
			}
		}

		List<WebElement> allNeedsCatBudgetAmt = needsWantsBanner.allNeedsCatBudgetedAmt();
		float ExpectedBudgettotalAmount = 0;
		for (int i = 0; i < allNeedsCatBudgetAmt.size(); i++) {
			logger.info(allNeedsCatBudgetAmt.get(i).getText().replace(",", ""));
			float allwantsBudget = Float.parseFloat(allNeedsCatBudgetAmt.get(i).getText().replace(",", ""));
			ExpectedBudgettotalAmount += allwantsBudget;
		}
		logger.info("Total wants Expected amount is :: " + ExpectedBudgettotalAmount);

		float actualBudgetedTotalAmt = Float
				.parseFloat(needsWantsBanner.allNeedsBudgetedTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Actual amount is :: " + actualBudgetedTotalAmt);

		logger.info("Actual percentage :: " + actualBudgetedTotalAmt);
		if (!(ExpectedBudgettotalAmount == actualBudgetedTotalAmt)) {
			Assert.fail("Total amount is not matching at index at :: " + " Expected total amount ::\""
					+ ExpectedBudgettotalAmount + "\" and Actual budgeted amount :: \"" + actualBudgetedTotalAmt);

		}

		List<WebElement> allWantsCatSpentAmt = needsWantsBanner.allNeedsCatSpentAmt();
		float ExpectedSpenttotalAmount = 0;
		for (int i = 0; i < allWantsCatSpentAmt.size(); i++) {
			logger.info(allWantsCatSpentAmt.get(i).getText().replace(",", ""));
			float allwantsSpent = Float.parseFloat(allWantsCatSpentAmt.get(i).getText().substring(1).replace(",", ""));

			ExpectedSpenttotalAmount += allwantsSpent;
		}
		logger.info("Total wants Spent Expected amount is :: " + ExpectedSpenttotalAmount);

		float actualSpentTotalAmt = Float
				.parseFloat(needsWantsBanner.allNeedsSpentTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Spent Actual amount is :: " + actualSpentTotalAmt);

		logger.info("Actual percentage :: " + actualSpentTotalAmt);
		if (!(ExpectedSpenttotalAmount == actualSpentTotalAmt)) {
			Assert.fail("Total amount is not matching  :: " + " Expected total Spent amount ::\""
					+ ExpectedSpenttotalAmount + "\" and Actual spent amount :: \"" + actualSpentTotalAmt);

		}

		List<WebElement> allNeedsCatLeftOverAmt = needsWantsBanner.allNeedsCatLeftOverAmt();
		float ExpectedLeftOvertotalAmount = 0;
		for (int i = 0; i < allNeedsCatLeftOverAmt.size(); i++) {
			logger.info("LeftOverAmount is :: "
					+ allNeedsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allNeedsLeftOver = Float
					.parseFloat(allNeedsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));

			ExpectedLeftOvertotalAmount += allNeedsLeftOver;
		}
		logger.info("Total wants Left/Over Expected amount is :: " + ExpectedLeftOvertotalAmount);

		float actualLeftOverTotalAmt = Float
				.parseFloat(needsWantsBanner.allNeedsLeftOverTotalAmt().getText().substring(1).replace(",", ""));
		DecimalFormat df = new DecimalFormat("0.00");
		ExpectedLeftOvertotalAmount = Float.parseFloat(df.format(ExpectedLeftOvertotalAmount));
		logger.info("Total wants Left/Over Actual amount is :: " + actualLeftOverTotalAmt);

		logger.info("Actual percentage :: " + actualLeftOverTotalAmt);
		if (!(ExpectedLeftOvertotalAmount == actualLeftOverTotalAmt)) {
			Assert.fail("Total amount is not matching :: " + " Expected total Left/Over amount ::\""
					+ ExpectedLeftOvertotalAmount + "\" and Actual Total Left/Over amount :: \""
					+ actualLeftOverTotalAmt);

		}

	}

	@Test(description = "AT-136339:The above labels should be displayed for Current month and History pages also.", groups = {
			"DesktopBrowsers" }, priority = 14, dependsOnMethods = { "login" }, enabled = true)
	public void verifyWantsBudgetHistory() throws Exception {

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> allWantsCatBudgetAmt = needsWantsBanner.budgetHistory_allWantsCatBudgetedAmt();
		float ExpectedBudgettotalAmount = 0;
		for (int i = 0; i < allWantsCatBudgetAmt.size(); i++) {
			logger.info("All wants is :: " + allWantsCatBudgetAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allwantsBudget = Float
					.parseFloat(allWantsCatBudgetAmt.get(i).getText().replace(",", "").replace("$", ""));

			ExpectedBudgettotalAmount += allwantsBudget;
		}

		logger.info("Total wants Expected amount is :: " + ExpectedBudgettotalAmount);

		float actualBudgetedTotalAmt = Float.parseFloat(
				needsWantsBanner.budgetHistory_allWantsBudgetedTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Actual amount is :: " + ExpectedBudgettotalAmount);
		logger.info("Actual percentage :: " + actualBudgetedTotalAmt);
		if (!(ExpectedBudgettotalAmount == actualBudgetedTotalAmt)) {
			Assert.fail("Amount is not matching at :: " + " Expected total amount ::\"" + ExpectedBudgettotalAmount
					+ "\" and Actual budgeted amount :: \"" + actualBudgetedTotalAmt);

		}

		List<WebElement> allWantsCatSpentAmt = needsWantsBanner.budgetHistory_allWantsCatSpentAmt();
		float ExpectedSpenttotalAmount = 0;
		for (int i = 0; i < allWantsCatSpentAmt.size(); i++) {

			logger.info(allWantsCatSpentAmt.get(i).getText().replace(",", ""));
			float allwantsSpent = Float.parseFloat(allWantsCatSpentAmt.get(i).getText().substring(1).replace(",", ""));
			ExpectedSpenttotalAmount += allwantsSpent;
		}
		logger.info("Total wants Spent Expected amount is :: " + ExpectedSpenttotalAmount);

		float actualSpentTotalAmt = Float.parseFloat(
				needsWantsBanner.budgetHistory_allWantsSpentTotalAmt().getText().substring(1).replace(",", ""));

		logger.info("Total wants Spent Actual amount is :: " + actualSpentTotalAmt);

		logger.info("Actual total amount :: " + actualSpentTotalAmt);
		if (!(ExpectedSpenttotalAmount == actualSpentTotalAmt)) {
			Assert.fail("Amount total is not matching :: " + " Expected total amount ::\"" + ExpectedSpenttotalAmount
					+ "\" and Actual spent amount :: \"" + actualSpentTotalAmt);
		}

		List<WebElement> allWantsCatLeftOverAmt = needsWantsBanner.budgetHistory_allWantsCatLeftOverAmt();
		float ExpectedLeftOvertotalAmount = 0;
		for (int i = 0; i < allWantsCatLeftOverAmt.size(); i++) {
			logger.info("LeftOverAmount is :: "
					+ allWantsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));
			float allWantsLeftOver = Float
					.parseFloat(allWantsCatLeftOverAmt.get(i).getText().replace(",", "").replace("$", ""));

			ExpectedLeftOvertotalAmount += allWantsLeftOver;
		}
		logger.info("Total wants Left/Over Expected amount is :: " + ExpectedLeftOvertotalAmount);
		float actualLeftOverTotalAmt = Float.parseFloat(
				needsWantsBanner.budgetHistory_allWantsLeftOverTotalAmt().getText().replace(",", "").replace("$", ""));
		DecimalFormat df = new DecimalFormat("0.00");
		ExpectedLeftOvertotalAmount = Float.parseFloat(df.format(ExpectedLeftOvertotalAmount));
		logger.info("Total wants Left/Over Actual amount is :: " + actualLeftOverTotalAmt);

		logger.info("Actual percentage :: " + actualLeftOverTotalAmt);

		if (!(ExpectedLeftOvertotalAmount == actualLeftOverTotalAmt)) {
			Assert.fail("Total amount is not matching at index at :: " + " Expected total Left/Over amount ::\""
					+ ExpectedLeftOvertotalAmount + "\" and Actual Total Left/Over amount :: \""
					+ actualLeftOverTotalAmt);
		}
	}

}
