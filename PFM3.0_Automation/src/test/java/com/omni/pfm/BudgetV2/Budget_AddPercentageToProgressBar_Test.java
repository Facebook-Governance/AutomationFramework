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

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_NeedsWants_BannerPage_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GroupDropdown_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_AddPercentageToProgressBar_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_AddPercentageToProgressBar_Test.class);
	String changedcat;
	AccountAddition accAddition = new AccountAddition();

	Budget_BudgetSummary_Loc budgetSummary;
	Budget_Summary_GroupDropdown_Loc budget_Gdd;
	Budget_NeedsWants_BannerPage_Loc needsWantsBanner;
	Budget_CreateBudget_Loc budgetCreateLoc;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		
		doInitialization("Budget");
		needsWantsBanner = new Budget_NeedsWants_BannerPage_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);
		budgetCreateLoc = new Budget_CreateBudget_Loc(d);

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

	@Test(description = "AT-130719:Verify that  percentage is displayed next to spending/Income bar  in all the categories created budget in budget summary.", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void verifyPercentagePresent() throws Exception {

		budgetCreateLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_GroupName1"));
		Assert.assertTrue(needsWantsBanner.percentageCatList().get(0).isDisplayed());
	}

	@Test(description = "AT-130720,AT-130721:Percentage should be calculated by the following formula for 'This Month'.Percentage=Actual Spend / Budgeted Amount * 100", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "login" }, enabled = true)
	public void verifyPercentage() throws Exception {

		List<WebElement> SpentOrEarnedAmtList = needsWantsBanner.spentOrEarnedCategAmountList();
		List<WebElement> BudgetedAmtList = needsWantsBanner.budgetedCategAmountList();
		List<WebElement> percentageCatList = needsWantsBanner.percentageCatList();
		for (int i = 0; i < SpentOrEarnedAmtList.size(); i++) {
			logger.info("Spent amount :: " + SpentOrEarnedAmtList.get(i).getText());
			logger.info(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));
			float spentAmount = Float.parseFloat(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));
			logger.info("Budget Amount :: " + BudgetedAmtList.get(i).getText().replace(",", ""));
			float budgetAmount = Float.parseFloat(BudgetedAmtList.get(i).getText().replace(",", ""));
			logger.info("budget Amount :: " + budgetAmount);

			float expectedPercentage = Math.round((spentAmount / budgetAmount) * 100);
			logger.info("Expected percentage :: " + expectedPercentage);
			float actualPercentage = Float.parseFloat(percentageCatList.get(i).getText().replace("%", ""));
			logger.info("Actual percentage :: " + actualPercentage);
			if (!(expectedPercentage == actualPercentage)) {
				Assert.fail("Percentage is matching at index at :: " + i + " spent amount ::\"" + spentAmount
						+ "\" and budget amount :: \"" + budgetAmount + "\" but percentage displayed is :: \""
						+ actualPercentage + "%\"");
			}
		}

	}

	@Test(description = "AT-130722:Verify that if the user selected 'Last Month' from time filter and Percentage is calculated.", groups = {
			"DesktopBrowsers" }, priority = 4, dependsOnMethods = { "login" }, enabled = true)
	public void verifyPercentageInHistory() throws Exception {

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_SummeryLastMonthLabel")));

		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_SummeryLastMonthLabel"), "time filter label is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
				budgetSummary.getdateMMMMYYYY(-1), "time filter date value is not displayed");

	}

	@Test(description = "AT-130723:Verify that if the user selected '3 Months' from time filter and there is no budget for current month, then only category name should be displayed. Spending or Income bar and percentage shouldnot be displayed.:Verify that banner should be displayed in budget setup page.", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "login" }, enabled = true)
	public void verifyPercentageInHistory3Months() throws Exception {

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> SpentOrEarnedAmtList = needsWantsBanner.spentOrEarnedCategAmountList();
		List<WebElement> BudgetedAmtHistoryList = needsWantsBanner.budgetedCategAmountListHistory();
		List<WebElement> percentageCatList = needsWantsBanner.percentageCatList();
		for (int i = 0; i < SpentOrEarnedAmtList.size(); i++) {
			logger.info("Spent amount :: " + SpentOrEarnedAmtList.get(i).getText());
			logger.info(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));
			float spentAmount = Float.parseFloat(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));

			logger.info("Budget Amount :: " + BudgetedAmtHistoryList.get(i).getText());
			logger.info(BudgetedAmtHistoryList.get(i).getText().substring(1).replace(",", ""));
			float budgetAmount = Float
					.parseFloat(BudgetedAmtHistoryList.get(i).getText().substring(1).replace(",", ""));
			logger.info("budget Amount :: " + budgetAmount);

			float expectedPercentage = Math.round((spentAmount / budgetAmount) * 100);
			logger.info("Expected percentage :: " + expectedPercentage);
			float actualPercentage = Float.parseFloat(percentageCatList.get(i).getText().replace("%", ""));
			logger.info("Actual percentage :: " + actualPercentage);
			if (!(expectedPercentage == actualPercentage)) {
				Assert.fail("Percentage is matching at index at :: " + i + " spent amount ::\"" + spentAmount
						+ "\" and budget amount :: \"" + budgetAmount + "\" but percentage displayed is :: \""
						+ actualPercentage + "%\"");
			}
		}
	}

	@Test(description = "AT-130724:Verify that if the user selected '6 Months' from time filter and Percentage is calculated.", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "login" }, enabled = true)
	public void verifyPercentageInHistory6Months() throws Exception {

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery6MonthLabel")));
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> SpentOrEarnedAmtList = needsWantsBanner.spentOrEarnedCategAmountList();
		List<WebElement> BudgetedAmtHistoryList = needsWantsBanner.budgetedCategAmountListHistory();
		List<WebElement> percentageCatList = needsWantsBanner.percentageCatList();
		for (int i = 0; i < SpentOrEarnedAmtList.size(); i++) {
			logger.info("Spent amount :: " + SpentOrEarnedAmtList.get(i).getText());
			logger.info(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));
			float spentAmount = Float.parseFloat(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));

			logger.info("Budget Amount :: " + BudgetedAmtHistoryList.get(i).getText());
			logger.info(BudgetedAmtHistoryList.get(i).getText().substring(1).replace(",", ""));
			float budgetAmount = Float
					.parseFloat(BudgetedAmtHistoryList.get(i).getText().substring(1).replace(",", ""));
			logger.info("budget Amount :: " + budgetAmount);

			float expectedPercentage = Math.round((spentAmount / budgetAmount) * 100);
			logger.info("Expected percentage :: " + expectedPercentage);
			float actualPercentage = Float.parseFloat(percentageCatList.get(i).getText().replace("%", ""));
			logger.info("Actual percentage :: " + actualPercentage);
			if (!(expectedPercentage == actualPercentage)) {
				Assert.fail("Percentage is matching at index at :: " + i + " spent amount ::\"" + spentAmount
						+ "\" and budget amount :: \"" + budgetAmount + "\" but percentage displayed is :: \""
						+ actualPercentage + "%\"");
			}
		}
	}

	@Test(description = "AT-130725,AT-130727:Verify that if the user selected '12 Months' from time filter and Percentage is calculated.", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "login" }, enabled = true)
	public void verifyPercentageInHistory12Months() throws Exception {

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery12MonthLabel")));
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> SpentOrEarnedAmtList = needsWantsBanner.spentOrEarnedCategAmountList();
		List<WebElement> BudgetedAmtHistoryList = needsWantsBanner.budgetedCategAmountListHistory();
		List<WebElement> percentageCatList = needsWantsBanner.percentageCatList();
		for (int i = 0; i < SpentOrEarnedAmtList.size(); i++) {
			logger.info("Spent amount :: " + SpentOrEarnedAmtList.get(i).getText());
			logger.info(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));
			float spentAmount = Float.parseFloat(SpentOrEarnedAmtList.get(i).getText().substring(1).replace(",", ""));

			logger.info("Budget Amount :: " + BudgetedAmtHistoryList.get(i).getText());
			logger.info(BudgetedAmtHistoryList.get(i).getText().substring(1).replace(",", ""));
			float budgetAmount = Float
					.parseFloat(BudgetedAmtHistoryList.get(i).getText().substring(1).replace(",", ""));
			logger.info("budget Amount :: " + budgetAmount);

			float expectedPercentage = Math.round((spentAmount / budgetAmount) * 100);
			logger.info("Expected percentage :: " + expectedPercentage);
			float actualPercentage = Float.parseFloat(percentageCatList.get(i).getText().replace("%", ""));
			logger.info("Actual percentage :: " + actualPercentage);
			if (!(expectedPercentage == actualPercentage)) {
				Assert.fail("Percentage is matching at index at :: " + i + " spent amount ::\"" + spentAmount
						+ "\" and budget amount :: \"" + budgetAmount + "\" but percentage displayed is :: \""
						+ actualPercentage + "%\"");
			}
		}
	}
}