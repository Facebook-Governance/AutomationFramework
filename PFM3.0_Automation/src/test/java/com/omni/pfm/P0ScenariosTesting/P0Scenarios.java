/*******************************************************************************
 * Copyright (c) 2020 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author kongara.sravan
 ******************************************************************************/
package com.omni.pfm.P0ScenariosTesting;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_NeedsWants_BannerPage_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_EditPopup_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GoalSetting_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GroupDropdown_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.SFG.SFG_CustomGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class P0Scenarios extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(P0Scenarios.class);
	private static AccountAddition accountAdditionPage;
	private static LoginPage login;
	private static InvestmentHoldings_Loc investmentHoldings;
	private static NetWorth_Loc networthPage;
	private static Expense_Income_Trend_Loc expenseTrend;
	private static AccountsSetting_GlobalSettings_Loc accSettings;
	String manualAccountName1 = "Investment Account Manual 1", manualAccountName2 = "Investment Account Manual 2";
	int manualAccountBalance = 10000;
	String accountNumber = "123456";
	String replaceChar = "*****";
	String groupName = "accountInvestmentgroup";
	private static CashFlow_LandingPage_Loc cashFlowPage;
	private static Budget_CreateBudget_Loc budgetCreateBudgetLoc;
	private static Budget_NeedsWants_BannerPage_Loc needsWantsBanner;
	private static Budget_Summary_GroupDropdown_Loc budget_Gdd;
	private static Budget_BudgetSummary_Loc budgetSummary;
	private static SFG_CustomGoalEdit_Loc customGoalEdit;
	private static Budget_Summary_GoalSetting_Loc budgetGoal;
	private static Budget_Summary_EditPopup_Loc budget_Edit;

	@BeforeSuite(alwaysRun = true)
	public void init() throws Exception {
		try {
			doInitialization("P0 Test Scenarios");
			TestBase.tc = TestBase.extent.startTest("Login", "Login for testing P0 Scenarios");
			TestBase.test.appendChild(TestBase.tc);
			accountAdditionPage = new AccountAddition();
			investmentHoldings = new InvestmentHoldings_Loc(d);
			networthPage = new NetWorth_Loc(d);
			expenseTrend = new Expense_Income_Trend_Loc(d);
			accSettings = new AccountsSetting_GlobalSettings_Loc(d);
			login = new LoginPage();
			customGoalEdit = new SFG_CustomGoalEdit_Loc(d);
			budget_Edit = new Budget_Summary_EditPopup_Loc(d);
			budgetGoal = new Budget_Summary_GoalSetting_Loc(d);
			cashFlowPage = new CashFlow_LandingPage_Loc(d);
			budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
			needsWantsBanner = new Budget_NeedsWants_BannerPage_Loc(d);
			budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);
			budgetSummary = new Budget_BudgetSummary_Loc(d);
//			login.loginWithOldAccount("wr6", "Yodlee@123");

			login.loginMain(d, loginParameter);
			accountAdditionPage.linkAccount();
			Assert.assertTrue(accountAdditionPage.addAggregatedAccount("CMA_LOC.site16441.1", "site16441.1"),
					"Account addition is not working for account CMA_LOC.site16441.1");
			for (int i = 0; i < 4; i++) {
				SeleniumUtil.refresh(d);
			}

		} catch (Exception e) {
			logger.error("Unable to initialize P0 scenarios test due to :: " + e.getMessage());
			Assert.fail("Unable to initialize P0 scenarios test due to :: " + e.getMessage());
		}
	}

	@Test(priority = 0, groups = "NetworthP0")
	public void navigateToNetworth() {
		networthPage.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		networthPage.forceNavigateToNetWorth();
	}

	@Test(priority = 1, description = "Verify networth finapp", dependsOnMethods = "navigateToNetworth", groups = "NetworthP0")
	public void validateAccountsInNetworthFinapp() {
		try {
			logger.info("Accounts present in the networth finapp are ::"
					+ networthPage.returnTheAccountsInAccountDropdown().toString());
			Assert.assertEquals(networthPage.returnTheAccountsInAccountDropdown().toString(),
					PropsUtil.getDataPropertyValue("P0.LocAccountsInNetworthFinapp"),
					"The accounts in the dropdown are not as expected");
		} finally {
			networthPage.collapseTheAccountsDropdown();
		}
	}

	@Test(priority = 2, description = "Verify networth percentage change in the finapp", dependsOnMethods = "navigateToNetworth", groups = "NetworthP0")
	public void validateNetworthChangePercentage() {
		String percentageChange = networthPage.returnTheNetworthPercentage();
		logger.info("Percent Change :: " + percentageChange);
		Assert.assertEquals(percentageChange, PropsUtil.getDataPropertyValue("P0.networthPercentageChanges"),
				"The networth change is not as expected");
	}

	@Test(priority = 3, description = "Verify networth change in the finapp", dependsOnMethods = "navigateToNetworth", groups = "NetworthP0")
	public void validateNetworthAmountChange() {
		String percentageChange = networthPage.returnNetworthChangeAmount();
		logger.info("Amount Change :: " + percentageChange);
		Assert.assertEquals(percentageChange, PropsUtil.getDataPropertyValue("P0.networthAmountChange"),
				"The networth amount change is not as expected");
	}

	@Test(priority = 4, description = "Validate networth in the finapp", dependsOnMethods = "navigateToNetworth", groups = "NetworthP0")
	public void validateNetworthAmount() {
		String percentageChange = networthPage.returnTotalNetworthAmount();
		logger.info("Networth amount :: " + percentageChange);
		Assert.assertEquals(percentageChange, PropsUtil.getDataPropertyValue("P0.totalNetworthAmount"),
				"The networth total is not as expected");
	}

	@Test(priority = 10, groups = "EA/IA P0")
	public void navigateToEA() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad(4000);
		expenseTrend.FTUE();
	}

	@Test(description = "AT-97369,AT-97377,AT-97382:verify  default month data income", priority = 11, dependsOnMethods = "navigateToEA", groups = "EA/IA P0")
	public void verifydefaultTrendMonth_IA() {
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad(4000);
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("P0.IA_LOC_Default6MonthAvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				SeleniumUtil.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = SeleniumUtil.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());

		SeleniumUtil.assertExpectedActualList(SeleniumUtil.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("P0.default6MonthAmountInEA"),
				"all 6 month expense amount is not displayed as expected");
		SeleniumUtil.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg,
				"last 3 month avg is not displayed as expected");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertEquals(expenseTrend.EIATrendTopTxnCatgeory().size(), 4, "Category size is not matching.");
			SeleniumUtil.assertExpectedActualAmountList(
					SeleniumUtil.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("P0.IA_LOC_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97370:verify HLC page LOC account data", priority = 12, dependsOnMethods = "navigateToEA", groups = "EA/IA P0")
	public void VerifyHLCData() throws Exception {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(1);
		SeleniumUtil.waitForPageToLoad(8000);
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		By unCatTxn = SeleniumUtil.getByObject("Expense", null, "EIAHLCCatUnCatTxnAccount");
		int uncatTxnSize = SeleniumUtil.getElementCount(unCatTxn);
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;
		for (int i = 0; i < uncatTxnSize; i++) {
			System.out.println(expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText());
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("P0.EA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("P0.EA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("P0.EA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= 0);
		Assert.assertTrue(HELOCtxnCount >= 0);
		Assert.assertTrue(SBLOCTxncount >= 0);
		SeleniumUtil.waitForPageToLoad();
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("P0.EA_AllAccountHLC_TotalExpenseValue"),
				"total expense amount is not displayed");
		SeleniumUtil.assertExpectedActualList(SeleniumUtil.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("P0.EA_AllAccountHLC_CatName"),
				"all 11 HLc category name is not displayed");
		SeleniumUtil.assertExpectedActualAmountList(SeleniumUtil.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("P0.EA_AllAccountHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("P0.EA_AllAccountHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
	}

	@Test(description = "AT-97378:verify HLC page loc account income data", priority = 13, dependsOnMethods = "navigateToEA", groups = "EA/IA P0")
	public void VerifyHLCData_IA() throws Exception {
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		/*
		 * SeleniumUtil.waitForPageToLoad(5000); expenseTrend.EIAccountDropDown();
		 * expenseTrend.clickAccountLink(); expenseTrend.clickEIAllAccountCheckBox();
		 */
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		By unCatTxn = SeleniumUtil.getByObject("Expense", null, "EIAHLCCatUnCatTxnAccount");
		int uncatTxnSize = SeleniumUtil.getElementCount(unCatTxn);
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;
		for (int i = 0; i < uncatTxnSize; i++) {
			System.out.println(expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText());
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= 0);
		Assert.assertTrue(HELOCtxnCount >= 0);
		Assert.assertTrue(SBLOCTxncount >= 0);
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());

		}

		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("P0.IA_AllAccountHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		SeleniumUtil.assertExpectedActualList(SeleniumUtil.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("P0.IA_AllAccountHLC_CatName"),
				"all 11 HLc category name is not displayed");
		SeleniumUtil.assertExpectedActualAmountList(SeleniumUtil.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("P0.IA_AllAccountHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("P0.IA_AllAccountHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");

	}

	@Test(priority = 20, groups = "IH P0")
	public void navigateToInvestmentHoldings() {
		investmentHoldings.navigateToInvestmentHoldings();
	}

	@Test(description = "AT-128044,AT-128035:verify manual accounts in displayed in investment holdings page accounts dropdown and amount details", groups = {
			"Regression", "IH P0" }, priority = 21, dependsOnMethods = "navigateToInvestmentHoldings")
	public void checkManualAccountIsDisplayedInInvestmentHoldingsAccountDropDownAndAmountDetailsWhenNoManualAccountsArePresent() {
		try {
			if (!investmentHoldings.isNoDataScreenDisplayed()) {
				ArrayList<String> accountsDisplayed = investmentHoldings.returnTheAccountsDisplayedInAccountsDropdown();
				if (accountsDisplayed.contains(
						PropsUtil.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.label"))) {
					Assert.fail(
							"Manual account label is displayed in the investment holdings account dropdown even manual accounts are not added yet");
				}
				investmentHoldings.expandViewMoreDetailsInTotalAccountHoldingsBalance();
				if (SeleniumUtil.isDisplayed(InvestmentHoldings_Loc.manualAccount_InvestmentHoldings_BalanceLabel, 4)) {
					Assert.fail("Manual investments label is displayed in view more details,"
							+ " Even though manual accounts are not added.");
				}
			}
		} catch (Exception e) {
			logger.error(
					"unable to validate manual account is displaye din investment holdings accounts dropdown due to :: {}",
					e.getMessage());
			Assert.fail(
					"unable to validate manual account is displaye din investment holdings accounts dropdown due to ::"
							+ e.getMessage());
		} finally {
			investmentHoldings.closeAccountDropdown();
		}
	}

	@Test(description = "AT-128024,AT-128020:Add manual account for investments. verify in dropdown and it's calculations", groups = {
			"Regression", "IH P0" }, priority = 22, dependsOnMethods = "navigateToInvestmentHoldings")
	public void addManualAccountAndVerifyInAccountDropdown() {
		SoftAssert soft = new SoftAssert();
		try {
			float presentAmount = investmentHoldings.returnPresentTotalManualInvestmentAccountBalance();
			float presentTotalAmount = investmentHoldings.returnTotalInvestmentHoldingsAccountBalance();
			accountAdditionPage.addManualAccount("Investments", manualAccountName1, manualAccountName1,
					manualAccountBalance + "", accountNumber, null, null, null);
			accountAdditionPage.addManualAccount("Investments", manualAccountName2, manualAccountName2,
					manualAccountBalance + 5000 + "", accountNumber, null, null, null);
			investmentHoldings.navigateToInvestmentHoldings();
			investmentHoldings.click(investmentHoldings.goToMyInvestments());
			ArrayList<String> accountsDisplayed = investmentHoldings.returnTheAccountsDisplayedInAccountsDropdown();
			if (!accountsDisplayed.contains(PropsUtil
					.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.label").toUpperCase())) {
				soft.fail(
						"Manual account label is not displayed in the investment holdings account dropdown after adding manual account");
			}
			if (!accountsDisplayed
					.contains(PropsUtil.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.text")
							.replace(replaceChar, manualAccountName1))) {
				soft.fail("Manual account with name :: " + manualAccountName1
						+ " is not displayed in the investment holdings account dropdown");
			}
			if (!accountsDisplayed
					.contains(PropsUtil.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.text")
							.replace(replaceChar, manualAccountName2))) {
				soft.fail("Manual account with name :: " + manualAccountName2
						+ " is not displayed in the investment holdings account dropdown");
			}
			float amountAfterAddingManualAccounts = investmentHoldings
					.returnPresentTotalManualInvestmentAccountBalance();
			if (!(amountAfterAddingManualAccounts == presentAmount + 25000)) {
				soft.fail(
						"Manual investment account balance is not as expected after adding manual account. Expected :: "
								+ (presentAmount + 25000) + " and actual :: " + amountAfterAddingManualAccounts);
			}
			float totalInvestmentHoldingsAccountBalance = investmentHoldings
					.returnTotalInvestmentHoldingsAccountBalance();
			if (!(presentTotalAmount + 25000 == totalInvestmentHoldingsAccountBalance)) {
				soft.fail(
						"Total investment holdings account balance is not as expected after adding manual account. Expected :: "
								+ (presentTotalAmount + 25000) + " and actual :: "
								+ totalInvestmentHoldingsAccountBalance);
			}
		} catch (Exception e) {
			logger.error("Unable to add manual account and verify balance and account dropdown due to :: {}",
					e.getMessage());
			Assert.fail(
					"Unable to add manual account and verify balance and account dropdown due to :: " + e.getMessage());
		} finally {
			soft.assertAll();
		}
	}

	@Test(description = "AT-128033:Select only manual accounts and validate the account balance, asset class dropdown,chart view, table view and change type percentage", priority = 23, dependsOnMethods = "navigateToInvestmentHoldings", groups = {
			"Regression", "IH P0" })
	public void verifyFunctionalityOfUIOfInvestmentHoldingsPageWhenOnlyManualAccountsAreSelectedInAccountsDropdown() {
		SoftAssert soft = new SoftAssert();
		try {
			investmentHoldings.selectGivenAccountUnderInvestmentHoldingsAccountDropdown(manualAccountName1);
			investmentHoldings.addTheGivenAccountUnderInvestmentHoldingsAccountDropdown(manualAccountName2);
			float presentAccountInInvestmentHoldings = investmentHoldings.returnTotalInvestmentHoldingsAccountBalance();
			float expectedAmount = manualAccountBalance * 2 + 5000;
			if (!(presentAccountInInvestmentHoldings == expectedAmount)) {
				soft.fail("Total Investment holdings amount displayed is not as expected.Expected :: " + expectedAmount
						+ " && Actual :: " + presentAccountInInvestmentHoldings);
			}
			float totalAggregatedAccountsBalance = investmentHoldings.returnTotalAggregatedAccountsBalance();
			if (!(totalAggregatedAccountsBalance == 0)) {
				soft.fail(
						"Total aggregated accounts balance is not displaying properly when only manual accounts is selected.Expected :: 0 && Actual :: "
								+ totalAggregatedAccountsBalance);
			}

			float changePercentValue = investmentHoldings.returnChangePercentValueOfAggregatedAccounts();
			if (!(changePercentValue == 0)) {
				soft.fail(
						"Aggregated accounts change percent value is not displaying properly when only manual accounts is selected.Expected :: 0 && Actual :: "
								+ changePercentValue);
			}

			if (SeleniumUtil.isDisplayed(InvestmentHoldings_Loc.investmentHoldings_ChartView, 3)) {
				soft.fail("Chart view is displayed when only manual accounts is selected in accounts dropdown.");
			}

			if (SeleniumUtil.isDisplayed(InvestmentHoldings_Loc.investmentHoldings_TableView, 3)) {
				soft.fail("Table view is displayed when only manual accounts is selected in accounts dropdown.");
			}

			if (!investmentHoldings.isAssetClassDropdownDisabled()) {
				soft.fail("Asset class dropdown is not disabled when only manual accounts are selected");
			}
		} catch (Exception e) {
			logger.error("Unable to validate UI functional changes when only manual account is selected due to :: {} ",
					e.getMessage());
			Assert.fail("Unable to validate UI functional changes when only manual account is selected due to :: "
					+ e.getMessage());
		} finally {
			soft.assertAll();
		}
	}

	@Test(description = "AT-128022:verify manual investment account balance is reflecting in dashboard or not", priority = 24, dependsOnMethods = "navigateToInvestmentHoldings", groups = {
			"Regression", "IH P0" })
	public void verifyManualInvestmentAccountBalanceInDashboard() {
		try {
			PageParser.forceNavigate("DashboardPage", d);
			float manualInvestmentAccountsBalanceDisplayedInTheDashboard = investmentHoldings
					.returnTheAmountDisplayedInDashboardOfManualInvestmentAccounts();
			if (!(manualInvestmentAccountsBalanceDisplayedInTheDashboard == manualAccountBalance * 2 + 5000)) {
				Assert.fail("Manual Investment accounts balance is not as expected in dashboard. Expected :: "
						+ manualAccountBalance * 2 + 5000 + " & Actual :: "
						+ manualInvestmentAccountsBalanceDisplayedInTheDashboard);
			}
		} catch (Exception e) {
			Assert.fail(
					"Unable to validate manual investment accounts balance in dashboard due to :: " + e.getMessage());
		}
	}

	@Test(description = "AT-128032:Create a group for only manual accounts, Select it and validate the UI in investment holdings page", priority = 25, dependsOnMethods = "navigateToInvestmentHoldings", groups = {
			"Regression", "IH P0" })
	public void createGroupForManualAccountsAndValidateTheFunctionalities() {
		try {
			investmentHoldings.navigateToInvestmentHoldings();
			investmentHoldings.createGroup(groupName, new String[] { manualAccountName1, manualAccountName2 });
			investmentHoldings.selectGroupInInvestmentHoldings(groupName);
			float presentAmountInManualInvestment = investmentHoldings
					.returnPresentTotalManualInvestmentAccountBalance();
			float expectedAmount = manualAccountBalance * 2 + 5000;
			if (!(presentAmountInManualInvestment == expectedAmount)) {
				Assert.fail(
						"Total Investment holdings amount displayed is not as expected when a group with only manual accounts is selected.Expected :: "
								+ expectedAmount + " && Actual :: " + presentAmountInManualInvestment);
			}
		} catch (Exception e) {
			logger.error("Unable to create group for manual accounts and validate functionalities due to :: "
					+ e.getMessage());
			Assert.fail("Unable to create group for manual accounts and validate functionalities due to :: "
					+ e.getMessage());
		}
	}

	@Test(description = "AT-128030:Delete a manual account and select a group and validate balance is getting updated in the investment holdings", priority = 26, dependsOnMethods = "navigateToInvestmentHoldings", groups = {
			"Regression", "IH P0" })
	public void deleteAManualAccountAndVerifyInTheInvestmentHoldings() {
		try {
			PageParser.forceNavigate("AccountSettings", d);
			accSettings.navigateToManualAccounts();
			accSettings.deleteAccount(manualAccountName2);
			investmentHoldings.navigateToInvestmentHoldings();
			investmentHoldings.selectGroupInInvestmentHoldings(groupName);
			float presentAmountInManualInvestment = investmentHoldings
					.returnPresentTotalManualInvestmentAccountBalance();
			float expectedAmount = manualAccountBalance;
			if (!(presentAmountInManualInvestment == expectedAmount)) {
				Assert.fail(
						"Total Investment holdings amount displayed is not as expected when a manual account is deleted.Expected :: "
								+ expectedAmount + " && Actual :: " + presentAmountInManualInvestment);
			}
		} catch (Exception e) {
			logger.error("Unable to verify investment holding balance after deleting a account from group due to :: "
					+ e.getMessage());
			Assert.fail("Unable to verify investment holding balance after deleting a account from group due to :: "
					+ e.getMessage());
		}
	}

	@Test(priority = 30, groups = "CashFlow P0")
	public void navigateToCashFlow() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
		cashFlowPage.FTUE();
	}

	@Test(description = "AT-97601,AT-97598,AT-97603,AT-97604,AT-97607,AT-97608,AT-97609,AT-97610,AT-97611:verify Chart cashflow when select all account", priority = 31, groups = "CashFlow P0", dependsOnMethods = "navigateToCashFlow")
	public void verifyChartTotalCF() throws Exception {
		logger.info("All account option should be selected  Account dropdown");
		if (cashFlowPage.CFTimeFilterDropDown().getText()
				.contains(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"))) {
			cashFlowPage.selectTimeFilter(PropsUtil.getDataPropertyValue("CFDefaultTimeFilter"));
			SeleniumUtil.waitForPageToLoad(6000);
		}
		Assert.assertEquals(cashFlowPage.CFnetCashflowDataLable().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartNetCFLable"), "cash flow net data lable is not displayed");
		Assert.assertEquals(cashFlowPage.CFnetCashflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_ChartNetCFValue"), "net casahflow data is not displayed");
		Assert.assertEquals(cashFlowPage.CFinflowDataLable().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartInCFLable"), "cash inflow lable is not displayed");
		Assert.assertEquals(cashFlowPage.CFinflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_ChartInCFValue"), "cash inflow data is not displayed");
		Assert.assertEquals(cashFlowPage.CFoutflowDataLable().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartOutCFLable"), "cash outflow lable is not displayed");
		Assert.assertEquals(cashFlowPage.CFoutflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_ChartOutCFValue"), "cash outflow data is not displayed");

	}

	@Test(description = "AT-97601,AT-97598,AT-97607,AT-97608,AT-97609,AT-97610,AT-97611:verify summery average and summery details", priority = 32, groups = "CashFlow P0", dependsOnMethods = "navigateToCashFlow")
	public void verifySummeryAndDetailsCF() throws Exception {
		logger.info("cashflow summery average and details ");
		SeleniumUtil.assertExpectedActualAmountList(SeleniumUtil.getWebElementsValue(cashFlowPage.CFsummaryAverage()),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_CFSummary"), "summery average is not displayed");
		SeleniumUtil.assertExpectedActualAmountList(SeleniumUtil.getWebElementsValue(cashFlowPage.CFsummaryDetails()),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_CFDetails"), "summery details is not displayed");
	}

	@Test(description = "AT-97601,AT-97598,AT-97607,AT-97608,AT-97609,AT-97610,AT-97611:verify table cashflow details", priority = 33, groups = "CashFlow P0", dependsOnMethods = "navigateToCashFlow")
	public void verifyTableCF() throws Exception {
		logger.info("casflow table details");
		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(
						cashFlowPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_CFTableInFlow"),
				"cashflow table inflow data is not displayed");
		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(
						cashFlowPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_CFTableOutFlow"),
				"cashflow table outflow data is not displayed");

		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(cashFlowPage
						.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlow"),
				"cashflow table net transfer data is not displayed");

		SeleniumUtil.assertExpectedActualAmountList(
				SeleniumUtil.getWebElementsValue(cashFlowPage
						.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("P0.CF_LOC_CFTableNetFlow"),
				"cashflow table total netflow is not displayed");
	}

	@Test(description = "Navigate to budget page", priority = 40, groups = "Budget P0")
	public void navigateToBudgetPageAndClickOnGetStarted() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdditionPage.linkAccount();
		accountAdditionPage.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data1_Site"),
				PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data1_Password"));
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.clickGetStartButton();
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Create Group And Naviagate To Set Budget Page", priority = 41, groups = "Budget P0", dependsOnMethods = "navigateToBudgetPageAndClickOnGetStarted")
	public void createGroupAndNavigateToSetBudgetPage() {
		SeleniumUtil.sendKeys(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField(), "P0TestBudget");
		budgetCreateBudgetLoc.clickNextbutton();
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify category update in Set Budget Page", priority = 42, groups = "Budget P0", dependsOnMethods = "createGroupAndNavigateToSetBudgetPage")
	public void verifyCategoryUpdateInBudget() {
		SeleniumUtil.click(needsWantsBanner.needsAndWantsBannerLink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(needsWantsBanner.wantsToggleBtn());
		SeleniumUtil.waitForPageToLoad();
		String changedcat = needsWantsBanner.changedCatogryName().getText().toString();
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
		SeleniumUtil.click(budget_Gdd.DoneBtn());
		Assert.assertFalse(status, "Category is Changed to Wants Successfully");
	}

	@Test(description = "AT-108720,AT-108725,AT-108732,AT-108735:Verify Income label in income progress bar", priority = 43, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifyIncomeproBarDetails() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("P0.Budget_HouseHoldingSummeryProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("100%"),
				"width should be 100%");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getAttribute("style").contains("100%"),
				"width should be 100%");
	}

	@Test(description = "AT-108721,AT-108726,AT-108736:Verify Spending label progress bar", priority = 44, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifySpendingproBarDetails() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel, PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("P0.Budget_HouseHoldingSummeryProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingRemainColor"),
				"background color is not dispalyed");
		SeleniumUtil.assertContains("64%", budgetSummary.budget_Summery_SpendColor().getAttribute("style"),
				"width is not as expected");
		SeleniumUtil.assertContains("100%", budgetSummary.budget_Summery_SpendRemain().getAttribute("style"),
				"width is not as expected");
	}

	@Test(description = "AT-108722:Verify budgeted income value", priority = 45, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifyBudgetedIncome() {
		String budgetedIncomelabel = null;
		String budgetedIncomeValue = null;
		String proBarColor = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetedIncomelabel = budgetSummary.budget_Summery_ActualIncome_CalLblMobile().getText().trim();
			logger.info("BudgetedIncome label" + budgetedIncomelabel);
			budgetedIncomeValue = budgetSummary.budget_Summery_ActualIncome_CalValuelMobile().getText().trim();
			logger.info("budgeted income value" + budgetedIncomeValue);
			proBarColor = budgetSummary.budget_Summery_ActualIncome_CalProBarMobile().getCssValue("background-color");

		} else {
			budgetedIncomelabel = budgetSummary.budget_Summery_ActualIncome_CalLbl().getText().trim();
			logger.info("BudgetedIncome label" + budgetedIncomelabel);
			budgetedIncomeValue = budgetSummary.budget_Summery_ActualIncome_CalValuel().getText().trim();
			logger.info("budgeted income value" + budgetedIncomeValue);
			proBarColor = budgetSummary.budget_Summery_ActualIncome_CalProBar().getCssValue("background-color");
		}
		String incomeEarned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeEarned);
		Assert.assertEquals(budgetedIncomelabel,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualIncomeLbl"),
				"budgeted income label is not displayed");
		Assert.assertEquals(budgetedIncomeValue, incomeEarned, "budgeted income value is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualIncomeLblColor"), "");
	}

	@Test(description = "AT-108724,AT-108734:Verify spending values in legends", priority = 46, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifyActualSpending() {
		String actualSpendingLabel = null;
		String actualSpendingValue = null;
		String proBarColor = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			actualSpendingLabel = budgetSummary.budget_Summery_ActualSpend_CalLblMobile().getText().trim();
			logger.info("verify actual spending label" + actualSpendingLabel);
			actualSpendingValue = budgetSummary.budget_Summery_ActualSpend_CalValuelMobile().getText().trim();
			logger.info("verify actual spending value" + actualSpendingValue);
			proBarColor = budgetSummary.budget_Summery_ActualSpend_CalProBarMobile().getCssValue("background-color");
		} else {
			actualSpendingLabel = budgetSummary.budget_Summery_ActualSpend_CalLbl().getText().trim();
			logger.info("verify actual spending label" + actualSpendingLabel);
			actualSpendingValue = budgetSummary.budget_Summery_ActualSpend_CalValuel().getText().trim();
			logger.info("verify actual spending value" + actualSpendingValue);
			proBarColor = budgetSummary.budget_Summery_ActualSpend_CalProBar().getCssValue("background-color");
		}
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		Assert.assertEquals(actualSpendingLabel,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualSpendingLbl"),
				"actual spending value is not displayed");
		Assert.assertEquals(actualSpendingValue, spendingValue, "actual spending is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualSpendingLblColor"), "");

	}

	@Test(description = "AT-108727,AT-108728:Verify left over amount in legends", priority = 47, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifyLeftOverSpending() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLblMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));

		} else {
//			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_MinusIcon().isDisplayed(),
//					"minus icon is not displayed");
			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_EqualIcon().isDisplayed(),
					"equal icon is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLbl().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));
		}
	}

	@Test(description = "AT-108719:verify progress bar date header is displayed", priority = 48, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifySummeryProgressBarDateHeader() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertEquals(budgetSummary.budget_Summery_ProgressbarDateHeader().getText().trim(),
					budgetSummary.getdateMMMMYYYY(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					"date header is not dsplayed");
		}
	}

	@Test(description = "AT-108719:verify group drop down is displayed", priority = 49, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void verifySummaryBudgetGroupDropdown() {
		Assert.assertEquals(budgetSummary.budget_Summery_GroupDropDown().getText().trim(), "P0TestBudget",
				"created group name should be displayed");
		Assert.assertTrue(budgetSummary.budget_Summery_GroupDropDownIcon().isDisplayed());
	}

	@Test(description = "The user should see an option to create a new goal.", priority = 50, groups = "Budget P0", dependsOnMethods = "verifyCategoryUpdateInBudget")
	public void creatingGoalFirst() {
		SeleniumUtil.click(budgetGoal.createNewGoalLink());
		boolean getStartedStatus = customGoalEdit.getStartedTab().isDisplayed();
		SeleniumUtil.click(customGoalEdit.getStartedTab());
		customGoalEdit.createGoalwithMultipleAccount("GOALNAME50", "1300", "100", "100");
		Assert.assertTrue(getStartedStatus, "Get started tab is not displayed");
	}

	@Test(description = "On clicking on the create a new goal button, the user should be taken to the SFG finapp.", priority = 51, groups = "Budget P0", dependsOnMethods = "creatingGoalFirst")
	public void creatingGoalSecond() {
		SeleniumUtil.click(customGoalEdit.createGoalTab());
		customGoalEdit.createGoalwithMultipleAccount("GOALNAME51", "1300", "100", "100");

		SeleniumUtil.waitForPageToLoad(2000);
		int numberOfAddedGoal = customGoalEdit.allInprogressGoal().size();
		Assert.assertEquals(numberOfAddedGoal, 2);
	}

	@Test(description = "AT-116120,AT-116124:Goals should be a part of the budget equation on the summary page.", priority = 52, groups = "Budget P0", dependsOnMethods = "creatingGoalFirst")
	public void verifyGoalinBudgetSummary() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			PageParser.forceNavigate("Budget", d);
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Goal_Container"),
					budgetGoal.goalTitleinBudget_Mob().getText(), "");
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budgetGoal.backtoBudgetinSFG());
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Goal_Container"),
					budgetGoal.goalTitleinBudget().getText(), "");
		}
	}
}
