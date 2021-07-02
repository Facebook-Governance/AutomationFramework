/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author pkeshari
*/

package com.omni.pfm.Expense_IncomeAnalysis;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.IncLandingPage_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class IncomeAnalysisLandingPage_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(IncomeAnalysisLandingPage_Test.class);
	public static String currentMonth;
	static IncLandingPage_Loc IncLandingPage;
	ExpLandingPage_Loc ExpLandingPage;
	AccountAddition accAddition;
	Add_Manual_Transaction_Loc manualTransaction;

	@BeforeClass()
	public void init() throws Exception {

		doInitialization("Income Analysis");
		
		IncLandingPage = new IncLandingPage_Loc(d);
		ExpLandingPage = new ExpLandingPage_Loc(d);
		accAddition = new AccountAddition();
		manualTransaction = new Add_Manual_Transaction_Loc(d);
		
		LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("Expense", d);
		
	}

	@Test(description = "AT-81385,AT-81386:In case no accounts are there, prompt the user to Link Accounts.", priority = -1)
	public void verifyNoAccountsScreen() throws Exception {
		
		Assert.assertTrue(ExpLandingPage.desertImageOrIcon_Exp().isDisplayed());
		Assert.assertTrue(ExpLandingPage.linkAnAccountbtn_Exp().isDisplayed());

	}
	
	@Test(description = "AT-81385,AT-81386:In case no accounts are there, prompt the user to Link Accounts.", priority = 0)
	public void addAccount() throws Exception {

		logger.info("***** Adding Accounts ******");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		
		boolean linkAnAccountFloater = ExpLandingPage.linkAnAccountFloater_Exp().isDisplayed();
		if (linkAnAccountFloater == true) {
			Assert.assertTrue(true);
			logger.info("Link Accunt button is clickable and the Link Account Floater/PopUp is opened");
		} else {
			logger.error("Could not click the Link Account button OR Link Account floater did not load!!!");
			Assert.assertTrue(false);
		}
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("eAiA_dagSite"),
				PropsUtil.getDataPropertyValue("eAiA_dagSitePassword"));

		logger.info("***** Navigating to Income/Expense Analysis ******");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-81366,AT-81367:Coach-marks should appear only the very first time the user navigates to Income Analysis Finapp.Welcome to Income Analysis Finapp Popup should be displayed as per the wireframes.", dependsOnMethods = {"addAccount" }, priority = 1)
	public void verifyWelcomeScreen() throws Exception {

		Assert.assertTrue(ExpLandingPage.welcomeCoachMarkHeading().isDisplayed());
		Assert.assertEquals(ExpLandingPage.welcomeCoachMarkHeading().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_Welcome_CM_Heading"));
		Assert.assertEquals(PropsUtil.getDataPropertyValue("EA_Welcome_CM_desc").trim(),
				ExpLandingPage.welcomeCoachMarkDesc().getText().trim());
		Assert.assertTrue(ExpLandingPage.continueButton().isDisplayed());
		logger.info("***** Verified the welcome to income analysis header ******");
	}

	@Test(description = "AT-81369:User should not be exited out of the first step i.e., from Welcome Screen", priority = 2, dependsOnMethods = {"addAccount" }, enabled = false)
	public void verifyUserShouldNotExit() throws Exception {

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		Assert.assertTrue(ExpLandingPage.welcomeCoachMarkHeading().isDisplayed());
		logger.info("***** Verified the welcome to income analysis is present ******");
	}

	@Test(description = "AT-81370:Respective bubble should be highlighted for the respective screen(If it is first screen first bubble) in the IA Finapp", dependsOnMethods= {"addAccount"}, priority = 3)
	public void verifyBubblesForCoachMarks() throws Exception {

		int size = ExpLandingPage.bulletInCoachMark().size();
		Assert.assertEquals(2, size);
		logger.info("*** Verified that there are two navigation bullets below the CONTINUE button.");
	}

	@Test(description = "AT-81368:Link your Spending Accounts Pop up should be displayed as per wireframes once user clicks on the continue button on the Welcome popup.", dependsOnMethods = {"addAccount" }, priority = 4)
	public void verifyLinkSpendingAccount() throws Exception {

		ExpLandingPage.continueButton().click();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(ExpLandingPage.linkAccountWelcomeCMHeading().getText(),
				PropsUtil.getDataPropertyValue("EACM_Link_Account_Heading"));
		logger.info("*** Verified Link your Spending Accounts Pop up");
	}

	@Test(description = "AT-81371:Second screen points to the Link Account button and the Link Account Button is highlighted by being above the translucent black overlay.", dependsOnMethods = {
			"verifyLinkSpendingAccount" }, priority = 5)
	public void verifyLinkAccountButton() throws Exception {

		Assert.assertEquals(ExpLandingPage.getEAWelcomeCMDescription(),
				PropsUtil.getDataPropertyValue("EACM_Link_Account_Description"));
		Assert.assertTrue(ExpLandingPage.CMlinkAccountLink().isDisplayed());
		logger.info("***verified Link Account button and the Link Account Button is displayed");
	}

	@Test(description = "AT-81384: Expense/Income Analysis coach mark should be displayed as a third popup in the IA Finapp", dependsOnMethods = {
			"verifyLinkSpendingAccount" }, priority = 6)
	public void verifyIncomeAnalysisCoachMark() throws Exception {

		Assert.assertTrue(ExpLandingPage.gotoAnalysisBtn().isDisplayed());
		SeleniumUtil.click(ExpLandingPage.gotoAnalysisBtn());
		logger.info("*** clicked on goto analysis page");

	}
	
	@Test(description = "Navigating to Expense Analysis Finapp.", dependsOnMethods = {
	"verifyIncomeAnalysisCoachMark" }, priority = 7)
	public void naviagateToExpenseAnalysis() throws Exception {
	
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
	
	}
	
	
	////// ************************************************************

	@Test(description = "AT-81388:Verify that the toggle dropdown is displayed.", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 8)
	public void isToggleDropDownDisplayed() throws Exception {

		Assert.assertTrue(IncLandingPage.DropDownIcon().isDisplayed());
		logger.info("Verified that the toggle dropdown is displayed.");
	}

	@Test(description = "AT-81388:Verify Income Analysis in drop down.", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 9)
	public void verifyIncomeAnalysisInDropDown() throws Exception {

		SeleniumUtil.click(IncLandingPage.DropDownIcon());
		Assert.assertTrue(IncLandingPage.IncomeAnalysisText().isDisplayed());
		logger.info("Verified Income Analysis in drop down.");
	}

	@Test(description = "AT-81387:Verify Income Analysis page.", dependsOnMethods = {
			"verifyIncomeAnalysisInDropDown" }, priority = 10)
	public void verifyIncomeAnalysisHeader() throws Exception {

		SeleniumUtil.click(IncLandingPage.IncomeAnalysisText());
		SeleniumUtil.waitForPageToLoad(5000);
		String header = IncLandingPage.IncomeAnalysisHeader().getText();
		Assert.assertEquals(header, PropsUtil.getDataPropertyValue("IA_LandingPage_Header"));
		logger.info("Verified Income Analysis page");
	}

	@Test(description = "AT-81372,AT-81374:Verify that Feature Tour button is displayed.", dependsOnMethods = {
			"verifyIncomeAnalysisHeader" }, priority = 11)
	public void isFeatureTourButtonDisplayed() {

		ExpLandingPage.moreIconDropDown().click();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.IAfeatureTourButton().isDisplayed());
		logger.info("Verified that Feature Tour button is displayed");

	}

	@Test(description = "AT-81382,AT-81383,AT-81373,AT-81375:Verify Feature Tour CM Drill Down By Categories.", dependsOnMethods = {
			"isFeatureTourButtonDisplayed" }, priority = 12)
	public void verifydrillDownByCategoriesCMHeading() {

		SeleniumUtil.click(IncLandingPage.IAfeatureTourButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.drillDownByCatHeading().isDisplayed());
		Assert.assertTrue(IncLandingPage.clickCMNextButton().get(0).isDisplayed());
		logger.info("Verified Feature Tour CM Drill Down By Categories.");
	}

	@Test(description = "AT-81382,AT-81373,AT-81376:Verify Feature Tour CM Average Line.", dependsOnMethods = {
			"verifydrillDownByCategoriesCMHeading" }, priority = 13)
	public void verifyAverageLineCMHeading() {

		SeleniumUtil.click(IncLandingPage.clickCMNextButton().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.averageLineHeading().isDisplayed());
		Assert.assertTrue(IncLandingPage.backButon(0).isDisplayed());
		Assert.assertTrue(IncLandingPage.clickCMNextButton().get(1).isDisplayed());
		logger.info("Verified Feature Tour CM Average Line");
	}

	@Test(description = "AT-81382,AT-81373,AT-81377,AT-81378:Verify Feature Tour CM Income Analysis.", dependsOnMethods = {
			"verifyAverageLineCMHeading" }, priority = 14)
	public void verifyExpenseOrIncomeAnalysisCM() {

		SeleniumUtil.click(IncLandingPage.clickCMNextButton().get(1));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.incomeAnalysisFTCMHeading().isDisplayed());
		logger.info("Verified Feature Tour CM Income Analysis.");
	}

	@Test(description = "AT-81373:L1-42438 LMSpS Verify Feature Tour CM Link All Your Accounts.", dependsOnMethods = {
			"verifyExpenseOrIncomeAnalysisCM" }, priority = 15)
	public void verifyLinkAllYourAccountsCM() {

		//// Verify that Link all your Accounts coach mark should be

		SeleniumUtil.click(IncLandingPage.clickCMNextButton().get(2));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.linkAllYourAccountFTCMHeading().isDisplayed());
		Assert.assertTrue(IncLandingPage.backButon(2).isDisplayed());
		Assert.assertTrue(IncLandingPage.clickCMNextButton().get(3).isDisplayed());
		logger.info("Verified Feature Tour CM Link All Your Accounts.");
	}

	@Test(description = "AT-81401:Verify clicking on month bar link takes user to Income Analysis by Category page.", dependsOnMethods = {
			"verifyLinkAllYourAccountsCM" }, priority = 16)
	public void verifyRightSideMonthBarLink() {

		IncLandingPage.clickCMNextButton().get(3).click();
		SeleniumUtil.click(IncLandingPage.monthbarlink()); // Clicking on month on the right side box
		SeleniumUtil.waitForPageToLoad(7000);
		Assert.assertTrue(IncLandingPage.incomeanalysiscattext().isDisplayed());
		logger.info("Verified clicking on month bar link takes user to Income Analysis by Category page.");
	}

	@Test(description = "AT-81404:Verify that print button is displayed.", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 17)
	public void verifyPrintButton() {

		SeleniumUtil.click(IncLandingPage.MoreButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.IAprintbutton().isDisplayed());
		logger.info("Verified that print button is displayed.");
	}

	@Test(description = "AT-81404: Verify that Add Transaction button is displayed.", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 18)
	public void isAddTransButtonDisplayed() {

		// L1-42486:LM : Verify that user should be able to view Add a Transaction
		Assert.assertTrue(IncLandingPage.Addtransactionlink().isDisplayed());
		logger.info("Verified that user should be able to view Add a Transaction");

	}

	@Test(description = "AT-81399:L1-42469 LMSp Verify that Income Analysis Finapp should shows the data points for the current and the previous 5 months.",
			dependsOnMethods = {"naviagateToExpenseAnalysis" }, priority = 19)
	public void allSixMonthDataValidation() {

		SeleniumUtil.click(IncLandingPage.backToIncomeLink());
		SeleniumUtil.waitForPageToLoad(7000);
		Assert.assertEquals(Integer.toString(IncLandingPage.allmonths().size()),
				PropsUtil.getDataPropertyValue("IA_All6months_Count"));
		logger.info(
				"Verified that Income Analysis Finapp should shows the data points for the current and the previous 5 months.");
	}

	@Test(description = "AT-81186:L1-42455 LMSpS Verify that max length for amounts shown on screens should be <= 15.",
			dependsOnMethods= {"naviagateToExpenseAnalysis"}, priority = 20)
	public void maxLenthOfAmmount() {

		if (IncLandingPage.amounttext().getText().length() <= 15) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		logger.info("Verified that max length for amounts shown on screens should be <= 15.");
	}

	@Test(description = "AT-81415:User must create atleast one account group from the available accounts in the accounts group page)", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 21)
	public void createTestGroupsForIA() {

		// Click on settings
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expMenuSettings", "Expense", null));
		SeleniumUtil.waitForPageToLoad();

		// Click on account groups
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expMenuAccountGroups", "Expense", null));
		SeleniumUtil.waitForPageToLoad();
		logger.info("*****************groups are created****************");

		boolean status = ExpLandingPage.createAccountGroupButton().isDisplayed();
		ExpLandingPage.createGroupForIA(PropsUtil.getDataPropertyValue("EA_Group1"));
		ExpLandingPage.createGroupForIA(PropsUtil.getDataPropertyValue("EA_Group2"));
		ExpLandingPage.createGroupForIA(PropsUtil.getDataPropertyValue("EA_Group3"));
		Assert.assertTrue(status);
	}

	/////// Ashwathi//////

	@Test(description = "AT-81490,AT-81491,AT-81453,AT-81454,AT-81402,AT-81405:Verify that amount difference.", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 22)
	public void amountDifference() throws Exception {
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		IncLandingPage.selectIA();
		SeleniumUtil.waitForPageToLoad();
		
		int pos = IncLandingPage.avgAmount().getText().indexOf(PropsUtil.getDataPropertyValue("budget_dollar"));
		Float avg_amt = Float.parseFloat(IncLandingPage.avgAmount().getText().substring(pos + 1).replaceAll(",", ""));

		for (int i = 1; i < ExpLandingPage.verifyMonthlyAmtForEAAndIA().size(); i++) {

			Float monthly_change = (float) IncLandingPage.round(Math.abs(IncLandingPage.monthlyAmt(i) - avg_amt), 2);
			logger.info("monthly_change =====================>" + monthly_change);
			Assert.assertEquals(IncLandingPage.monthlyChange(i), monthly_change);
			int monthly_change_percent = (int) IncLandingPage.round((monthly_change / avg_amt) * 100, 0);
			logger.info("monthly_change_percent =====================>" + monthly_change_percent);
			Assert.assertEquals(IncLandingPage.monthlyChangePercent(i), monthly_change_percent);
			logger.info("Verified the amount difference.");
		}
	}

	@Test(description = "AT-81511:Verify that clicking on Txn should open in edit mode.", dependsOnMethods = {
			"naviagateToExpenseAnalysis" }, priority = 23)
	public void clickTxn() throws Exception {

		SeleniumUtil.click(IncLandingPage.legends().get(1));
		SeleniumUtil.waitForPageToLoad();

		IncLandingPage.highLevelCategory().get(0).click();
		SeleniumUtil.waitForPageToLoad();

		IncLandingPage.txnList().get(0).click();
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(IncLandingPage.txnSaveBtn().isDisplayed());
		logger.info("Verified that clicking on Txn should open in edit mode.");

	}
	
	@Test(description = "Navigating to Expense Analysis Finapp.", dependsOnMethods = {
	"naviagateToExpenseAnalysis" }, priority = 24)
	public void naviagateToExpenseAnalysis1() throws Exception {
	
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		IncLandingPage.selectIA();
		SeleniumUtil.waitForPageToLoad();
	
	}

	@Test(description = "AT-81522,AT-81485,AT-81484,AT-81482,AT-81481,AT-81410:Verify data in Trends popup.", dependsOnMethods = {
			"naviagateToExpenseAnalysis1" }, priority = 25)
	public void verifyTrendsData() throws Exception {

		String currency = IncLandingPage.verifyUserPreferredCurrency();

		logger.info("currency ===================> " + currency);

		SeleniumUtil.click(IncLandingPage.legends().get(1));
		SeleniumUtil.waitForPageToLoad();

		IncLandingPage.highLevelCategory().get(0).click();
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.waitForPageToLoad();
		IncLandingPage.MoreButton().click();
		SeleniumUtil.click(IncLandingPage.trendMenu());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(IncLandingPage.trendMonths().size(), 6);

		int pos = IncLandingPage.trendAvgAmt().getText().indexOf(":");
		String avgSymbol = IncLandingPage.trendAvgAmt().getText().substring(pos + 2, pos + 3);

		if (currency.equalsIgnoreCase(PropsUtil.getDataPropertyValue("PreferredCurrencyType"))) {
			String currSymbol = PropsUtil.getDataPropertyValue("budget_dollar");
			Assert.assertEquals(avgSymbol, currSymbol);
		} else {
			logger.error("Preferred currency not present");
			Assert.assertTrue(false);
		}
		String amountSymbol;
		for (int i = 0; i < IncLandingPage.trendAmounts().size(); i++) {

			amountSymbol = IncLandingPage.trendAmounts().get(i).getText().substring(0, 1);
			if (currency.equalsIgnoreCase(PropsUtil.getDataPropertyValue("PreferredCurrencyType"))) {
				String currSymbol = PropsUtil.getDataPropertyValue("budget_dollar");
				Assert.assertEquals(amountSymbol, currSymbol);
			} else {
				logger.error("Preferred currency not present");
				Assert.assertTrue(false);
			}
		}
		float sum = 0;
		for (int i = 1; i < ExpLandingPage.trendAmounts().size() - 2; i++) {
			String amount = ExpLandingPage.trendAmounts().get(i).getText().substring(1).replaceAll(",", "");
			float amt = Float.parseFloat(amount);
			logger.info("The Avg amount for last 3 months in legend section of Trends PopUp in IA is  ==============> "
					+ amt);
			sum = sum + amt;
		}
		double amt = sum / 3;
		logger.info("Rounding Off the Value of Avg amount is " + ExpLandingPage.roundingOff(amt));
		String avgAmt = new Double(ExpLandingPage.roundingOff(amt)).toString();
		Assert.assertEquals(avgAmt, ExpLandingPage.trendAvgAmt().getText().substring(pos + 3).replaceAll(",", ""),
				"The Avg Amt for Last 3 months in Chart and Legend section of Trends PopUp is Not Matched!!!");
		logger.info("Verified the data in Trends popup.");

	}
	
	@Test(description = "Navigating to Expense Analysis Finapp.", dependsOnMethods = {
	"naviagateToExpenseAnalysis" }, priority = 26)
	public void naviagateToIncomeAnalysis() throws Exception {
	
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		IncLandingPage.selectIA();
		SeleniumUtil.waitForPageToLoad();
	
	}

	//// Ashwathi

	@Test(description = "AT-81193,AT-81398:Bar should not be displayed if user doesnt have the Income/Expense data for any of the previous month", dependsOnMethods = {
			"naviagateToIncomeAnalysis" }, priority = 27)
	public void verifyBarInChart() throws Exception {

		List<WebElement> l = ExpLandingPage.barChartsLegend();
		WebElement highCharts = ExpLandingPage.highCharts();
		for (int i = 1; i < l.size(); i++) {

			String text = ExpLandingPage.text(i).getText();
			String newtext = text.replaceAll("[$,.]", "");
			int monthSpending = Integer.parseInt(newtext);
			logger.info(("The amount is " + monthSpending));

			if (monthSpending > 0) {
				System.out.println("the charts size is:" + highCharts.isDisplayed());
				Assert.assertTrue(highCharts.isDisplayed());

			}
		}
		logger.info("Verified the bars for EA");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();
		List<WebElement> l1 = ExpLandingPage.barChartsLegend();
		WebElement highChartsIA = ExpLandingPage.highCharts();
		for (int i = 1; i < l1.size(); i++) {

			String text = ExpLandingPage.text(i).getText();
			String newtext = text.replaceAll("[$,.]", "");
			int monthSpending = Integer.parseInt(newtext);
			logger.info(("The percentage is " + monthSpending));

			if (monthSpending > 0) {
				logger.info("the charts size is:" + highChartsIA.isDisplayed());
				Assert.assertTrue(highChartsIA.isDisplayed());

			}
		}
		logger.info("Verified the bars for IA");
	}

	@Test(description = "AT-81428:By default All Income Accounts filter should be selected", dependsOnMethods = {
			"naviagateToIncomeAnalysis" }, priority = 28)
	public void verifyAllIncomeFilter() throws Exception {

		ExpLandingPage.clickChart(6);
		Assert.assertEquals(ExpLandingPage.allAccDropDownBlock().getText().toLowerCase(),
				PropsUtil.getDataPropertyValue("IncomeAcntDDHeader").toLowerCase());
		SeleniumUtil.click(IncLandingPage.allIncomeAccount());
		Assert.assertEquals(
				IncLandingPage.allIncomeChkBox().getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),
				"true");
		logger.info("***  verified By default All Income Accounts filter should be selected");

	}

	@Test(description = "AT-81429:User should be able to select list of accounts", dependsOnMethods = {
			"verifyAllIncomeFilter" }, priority = 29)
	public void verifyAccountSelection() throws Exception {

		SeleniumUtil.click(IncLandingPage.brokerageAccount());
		Assert.assertEquals(
				IncLandingPage.brokerageChkBox().getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),
				"false");
		SeleniumUtil.click(IncLandingPage.brokerageAccount());
		Assert.assertEquals(
				IncLandingPage.brokerageChkBox().getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),
				"true");
		logger.info("***  User should be able to select list of accounts");
	}

	@Test(description = "AT-81429,AT-81433:Verify that the Group Names created by the user are displayed. (User must have created atleast one account group from the available accounts in the accounts group page)", dependsOnMethods = {
			"verifyAllIncomeFilter" }, priority = 30)
	public void verifyGroupNamesInDropDown() throws InterruptedException {

		logger.info("***  User should be able to view the account groups in the All Accounts drop down.");
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.getAccountsAndGroups();
		Assert.assertEquals(ExpLandingPage.getGroups(PropsUtil.getDataPropertyValue("EA_Group1")).getText(),
				PropsUtil.getDataPropertyValue("EA_Group1"));
		Assert.assertEquals(ExpLandingPage.getGroups(PropsUtil.getDataPropertyValue("EA_Group2")).getText(),
				PropsUtil.getDataPropertyValue("EA_Group2"));
		Assert.assertEquals(ExpLandingPage.getGroups(PropsUtil.getDataPropertyValue("EA_Group3")).getText(),
				PropsUtil.getDataPropertyValue("EA_Group3"));
		logger.info("Verified User should be able to view the account groups in the All Accounts drop down.");
	}

	@Test(description = "AT-81434,AT-81427:User created group name should be shown in Alphabetical order", dependsOnMethods = {
			"verifyGroupNamesInDropDown" }, priority = 31)
	public void verifyGroupNamesAreSorted() throws InterruptedException {

		String groupnames[] = { PropsUtil.getDataPropertyValue("EA_Group1"),
				PropsUtil.getDataPropertyValue("EA_Group2"), PropsUtil.getDataPropertyValue("EA_Group3") };
		for (int i = 0; i < IncLandingPage.getGroups().size(); i++) {
			Assert.assertEquals(IncLandingPage.getGroups().get(i).getText(), groupnames[i]);
		}
		logger.info("Verified User created group name should be shown in Alphabetical order");
	}

	@Test(description = "AT-81487,AT-81427,AT-81412,AT-81413:Verify that Last Month time filter should be selected  if the user clicks on the last month in the summary view.", dependsOnMethods = {
			"verifyAllIncomeFilter" }, priority = 32)
	public void verifyTimeFilter() throws InterruptedException {

		Assert.assertTrue(ExpLandingPage.timeFilterDropdown().isDisplayed());
		SeleniumUtil.click(ExpLandingPage.timeFilterDropdown());
		logger.info("*** By default This Month Time Filter is displayed.");
		Assert.assertEquals(ExpLandingPage.timeFilter().getText(), PropsUtil.getDataPropertyValue("EA_TimeFilter1"));
		logger.info(
				"Verified that Last Month time filter should be selected  if the user clicks on the last month in the summary view.");
	}

	@Test(description = "AT-81436,AT-81437,AT-81314:Following time filters should be available for selection : This Month, Last Month, 3 Months, 6 Months, This year, 12 Months, Last year, Custom Dates.Verify that the data in the donut chart and the legend next to it should be the data related to  the time selected in the time filter.", dependsOnMethods = {
			"verifyAllIncomeFilter" }, priority = 33)
	public void verifyTimeFilter1() throws InterruptedException {
		logger.info("Date Range should be displayed for the selection of the time filter.");
		String dates[] = PropsUtil.getDataPropertyValue("EA_DateFilter").split(",");
		for (int i = 0; i < ExpLandingPage.getDateFormat().size() - 2; i++) {
			Assert.assertEquals(ExpLandingPage.getDateFormat().get(i).getText(), dates[i]);
			Assert.assertTrue(ExpLandingPage.getDateFormat().get(i).isEnabled());
			SeleniumUtil.click(ExpLandingPage.getDateFormat().get(i));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(
					IncLandingPage.donutChart().isDisplayed() || IncLandingPage.noDataContainer().isDisplayed());
			SeleniumUtil.click(ExpLandingPage.timeFilterDropdown());
			logger.info("Verified Date Range should be displayed for the selection of the time filter.");
		}
	}

	@Test(description = "AT-81443,AT-81446,AT-81438,AT-81242:Verify that the data in the donut chart and the legend next to it should be the data related to  the time selected in Custom Dates.User should be able to enter the custom date range physically .Error message should be displayed when the custom date range not more than 12 months : Selected time period cant be more than 12 months.", dependsOnMethods = {
			"verifyAllIncomeFilter" }, priority = 34)
	public void verifyTimeFilter2() throws InterruptedException {

		String dates[] = PropsUtil.getDataPropertyValue("EA_DateFilter").split(",");
		Assert.assertEquals(ExpLandingPage.getDateFormat().get(7).getText(), dates[7]);
		Assert.assertTrue(ExpLandingPage.getDateFormat().get(7).isEnabled());

		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(7));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(IncLandingPage.fromDate());
		IncLandingPage.fromDate().sendKeys(PropsUtil.getDataPropertyValue("AccountBankMaturityDate"));
		SeleniumUtil.click(IncLandingPage.toDate());
		IncLandingPage.toDate().sendKeys(PropsUtil.getDataPropertyValue("AccountBankMaturityDate2"));
		SeleniumUtil.click(IncLandingPage.updtBtn());
		Assert.assertEquals(IncLandingPage.invalidDateMsg().getText(), PropsUtil.getDataPropertyValue("IA_errorMsg"));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(IncLandingPage.toDate());
		IncLandingPage.toDate().clear();
		IncLandingPage.toDate().sendKeys(PropsUtil.getDataPropertyValue("AccountBankMaturityDate1"));
		SeleniumUtil.click(IncLandingPage.updtBtn());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(IncLandingPage.donutChart().isDisplayed() || IncLandingPage.noDataContainer().isDisplayed());
		logger.info(
				"Verified the data in the donut chart and the legend next to it should be the data related to  the time selected in Custom Dates.User should be able to enter the custom date range physically .Error message should be displayed when the custom date range not more than 12 months : Selected time period cant be more than 12 months.");
	}

	@Test(description = "AT-81444,AT-81445,AT-81416:Custom date range End Date should not be greater than Today.Custom date range time filter that shows the pop up with calendar", dependsOnMethods = {
			"verifyAllIncomeFilter" }, priority = 35)
	public void verifyCustomDateRange() throws InterruptedException {
		SeleniumUtil.click(ExpLandingPage.timeFilterDropdown());
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(0));
		SeleniumUtil.click(ExpLandingPage.timeFilterDropdown());
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(7));
		SeleniumUtil.click(ExpLandingPage.calenderIconToDate());

		Assert.assertTrue(ExpLandingPage.calenderPopUpOpen().isDisplayed());
		Assert.assertTrue(ExpLandingPage.todaysDate().isDisplayed());
		Assert.assertEquals(ExpLandingPage.todaysDate().getCssValue("color"),
				PropsUtil.getDataPropertyValue("SelectedDate"));

		logger.info(
				"Verified Custom date range End Date should not be greater than Today and Custom date range time filter that shows the pop up with calendar");

	}

	@Test(description = "AT-81450,AT-81411:Verify that Uncategorized Transactions header should be there below the donut chart in the IA Finapp ", dependsOnMethods = {
			"verifyCustomDateRange" }, priority = 36)
	public void verifyUnCategorizedTranHeader() throws Exception {
		SeleniumUtil.click(ExpLandingPage.closeCustomDate());
		String actualTextOfUncategorized = ExpLandingPage.verifyUnCategorizedTrans().getText().trim();
		logger.info("Actual Text of Categories Header in Income By Category page is " + actualTextOfUncategorized);

		logger.info(
				"Verifying that Uncategorized Transactions header is displayed in the EA Finapp(Expense By Category)");
		Assert.assertEquals(actualTextOfUncategorized,
				PropsUtil.getDataPropertyValue("ExpectedCategoryHeaderInEA").trim());
		logger.info(
				"Verified that Uncategorized Transactions header is displayed in the EA Finapp(Expense By Category)");
	}
	
	@Test(description = "Navigate to Income Analysis.", dependsOnMethods = {
		"naviagateToIncomeAnalysis" }, priority = 37)
	public void naviagateToIncomeAnalysis1() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		ExpLandingPage.clickChart(4);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-81455,AT-81456,AT-81389:IA by Income by Category Screen - Verify that the income in Legends section(Amounts) are displayed in descending order", dependsOnMethods = {
			"naviagateToIncomeAnalysis1" }, priority = 38)
	public void verifyAmtsAreInDescendingOrder() throws Exception {
		
		double value1 = 0.0, decimal = 0.0;

		List<WebElement> actualAmount = ExpLandingPage.verifyEAAmtsInEBC();
		for (WebElement we : actualAmount) {
			String actualAmt = we.getText().trim().replaceAll(",", "");
			logger.info("Actual Value of Amounts in Expense By Category are " + actualAmt);

			actualAmt = actualAmt.substring(
					actualAmt.indexOf(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth")) + 1,
					actualAmt.length());

			double value = Double.parseDouble(actualAmt);
			logger.info(
					"Actual Value of Amounts(Converting from String to Double) in Expense By Category are " + value);

			if (value1 != decimal) {
				if (value1 > value) {
					Assert.assertTrue(true);
					logger.info(
							"Verified that the expense Amounts in Legends section are displayed in descending order");
				} else {
					logger.error(
							"Verified that the expense Amounts in Legends section are NOT displayed in descending order!!!");
					Assert.assertTrue(false);
				}
			}
			value1 = value;
		}
	}

	@Test(description = "AT-81430:Verify that user should be able to view the manual accounts in the All Accounts dropdown list,", dependsOnMethods = {
			"naviagateToIncomeAnalysis1" }, priority = 39)
	public void verifyManualAccountsInAccFilter() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Adding Manual Account for Cards Container.");
		accAddition.addManualAccount("Cash", "Manual Cash account", null, "20000", null, "3445", "date", null);

		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("Verifying that user should be able to view the manual accounts in the All Accounts dropdown list");
		Assert.assertTrue(ExpLandingPage.verifyManualAccInAccFilter1().isDisplayed());
		logger.info("Verified that user should be able to view the manual accounts in the All Accounts dropdown list");

		SeleniumUtil.click(ExpLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "AT-81435:The deleted account group names should not displayed in the All Income Accounts filter in the Income by category page", dependsOnMethods = {
			"verifyManualAccountsInAccFilter" }, priority = 40)
	public void deleteAccGpAndVerifyInEBC() throws Exception {
		logger.info("Deleting the Account Group Created..");
		// Assert.assertTrue(ExpLandingPage.deleteAccountGp(PropsUtil.getDataPropertyValue("EA_Group1")));
		Assert.assertTrue(ExpLandingPage.deleteAccountGp());
		logger.info("Deleted the Account Group!!!");

		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		ExpLandingPage.clickChart(5);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);

		logger.info(
				"Verifying that the Account groups in the Income Analysis finapp is not displayed after deleting the account group");
		ExpLandingPage.getAccountsAndGroups();
		Assert.assertNull(IncLandingPage.groupIA());
		logger.info(
				"Verified that the Account groups in the Income Analysis finapp is not displayed after deleting the account group");
	}

	@Test(description = "AT-81513,AT-81479:Tapping + should open the transaction addition window", dependsOnMethods = {
			"naviagateToIncomeAnalysis1" }, priority = 41)
	public void verifyAddTxnFloater() throws Exception {
		SeleniumUtil.click(ExpLandingPage.addTXn());
		Assert.assertTrue(ExpLandingPage.addTXnPopUp().isDisplayed());
		logger.info("Verified that Tapping + should open the transaction addition window");
		SeleniumUtil.click(ExpLandingPage.closeIconAddTxn());
	}

	@Test(description = "AT-81472,AT-81467,AT-81465:User should selects the categories to be included and taps/click Done link.", dependsOnMethods = {
			"naviagateToIncomeAnalysis1" }, priority = 42)
	public void verifyCheckAndUnCheckTheCategories() throws Exception {

		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("1234", "Testing", 0, 1, 0, 0, 13, 1,
				PropsUtil.getDataPropertyValue("TransactionNoteForExpense"),
				PropsUtil.getDataPropertyValue("TransactionCheckForExpense"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);

		ExpLandingPage.deselectingTheCheckBoxesOfCategories();
		logger.info("Unchecked the categories available in filter by categories");

		String disabled = ExpLandingPage.verifyEAFilterCategoriesDoneBtn().getAttribute("class").trim();
		Assert.assertTrue(disabled.contains(PropsUtil.getDataPropertyValue("EACategoryFilterDoneButtonValue").trim()));
		logger.info(
				"After UnChecking all the Categories, Done Button is disabled!!!,Verified Atleast one category should be selected from the list of categories.");

		ExpLandingPage.selectingTheCheckBoxesOfCategories();
		logger.info("Checked/Selected the categories available in filter by categories");

		Assert.assertTrue(ExpLandingPage.verifyEAFilterCategoriesDoneBtn().isEnabled());
		logger.info("After Checking/Selecting all the Categories, Done Button is enabled!!!");
	}

	@Test(description = "AT-81264:Verifying that cancel and done buttons are avilable and clickable in filter by categories.", priority = 43, dependsOnMethods = {
			"verifyCheckAndUnCheckTheCategories" })
	public void verifyCancelAndDoneBtnsOfCategories() throws Exception {
		logger.info("Verifying that cancel and done buttons are avilable and clickable in filter by categories");
		Assert.assertTrue(ExpLandingPage.verifyEAFilterCategoriesDoneBtn().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(ExpLandingPage.verifyEAFilterCategoriesCancelBtn().isDisplayed());

		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategoriesCancelBtn());
		logger.info("Verified that cancel and done buttons are avilable and clickable in filter by categories");
	}

	@Test(description = "AT-81475:Verify that the label should change from All categories included to number categories included after selecting the specific categories.", dependsOnMethods = {
			"verifyCancelAndDoneBtnsOfCategories" }, priority = 44)
	public void verifyNoOfCategoriesIncluded() throws Exception {
		logger.info("Verifying that the label All categories included is displayed.");
		String actualAllCategHeader = ExpLandingPage.verifyAllCategoriesHeader().getText().trim();
		logger.info("Actual text of All Categories Included Header in Income By Category is " + actualAllCategHeader);
		Assert.assertEquals(actualAllCategHeader,
				PropsUtil.getDataPropertyValue("ExpectedTextOfAllCategIncluded").trim());
		logger.info("Verified that the label All categories included is displayed.");

		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);

		ExpLandingPage.deselectingTheOddNoCheckBoxesOfCategories();

		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);

		logger.info(
				"Verifying that the label should change from All categories included to number categories included after selecting the specific categories.");
		String actualNoOfCategoriesIncluded = ExpLandingPage.verifyAllCategoriesHeader().getText().trim();

		SeleniumUtil.waitForPageToLoad(2000);
		ExpLandingPage.selectingTheOddNoOfCheckBoxes();

		Assert.assertEquals(actualNoOfCategoriesIncluded,
				PropsUtil.getDataPropertyValue("ExpectedTextOfNoOfCategIncludedIA").trim());
		logger.info(
				"Verified that the label should change from All categories included to number categories included after selecting the specific categories.");
	}

	@Test(description = "AT-81512:Tapping back arrow should take user to incomes   by Category screen", dependsOnMethods = {
			"verifyCancelAndDoneBtnsOfCategories" }, priority = 45)
	public void backToIncomeScreen() throws Exception {
		Assert.assertTrue(ExpLandingPage.backToIncome().isDisplayed());
		SeleniumUtil.click(ExpLandingPage.backToIncome());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(IncLandingPage.IncomeAnalysisHeader().isDisplayed());
		logger.info("Verified Tapping back arrow should take user to incomes   by Category screen");
	}
	
	@Test(description = "Navigate to Income Analysis.", dependsOnMethods = {
	"naviagateToIncomeAnalysis1" }, priority = 46)
	public void naviagateToIncomeAnalysis2() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
	
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();
	}

	@Test(description = "AT-81421:Data represented in the legend, provides the actual values of total Incomes for the  month same as shown in the chart", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 47)
	public void dataInLegendAndChart() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(IncLandingPage.amountOnChart().getText(), IncLandingPage.amountOnCurrentMonth().getText());
		logger.info(
				"Verified Data represented in the legend, provides the actual values of total Incomes for the  month same as shown in the chart");
	}

	@Test(description = "AT-81519,AT-81441,AT-81287:Navigating back from IA by Master Category screen to incomes by Category screen should retain the time filter selection done initially.On navigating back to the screen from the  Income  by Category, the filters must be persisted.", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 48)
	public void validateFilterStickiness() throws Exception {
		
		PageParser.navigateToPage("Expense", d);
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(1));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.clickHLC());
		SeleniumUtil.click(ExpLandingPage.hlcBackBtn());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(ExpLandingPage.verifyLastMonthDate().isDisplayed());
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(0));
		logger.info("Verified Navigating back from EA by Master Category screen to expense by Category screen should retain the time filter selection done initially.On navigating back to the screen from the  Expense  by Category, the filters must be persisted.");

		PageParser.navigateToPage("Expense", d);
		IncLandingPage.selectIA();
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(1));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.clickHLC());
		SeleniumUtil.click(ExpLandingPage.hlcBackBtn());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(ExpLandingPage.verifyLastMonthDate().isDisplayed());
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(0));
		logger.info(
				"Verified Navigating back from IA by Master Category screen to incomes by Category screen should retain the time filter selection done initially.On navigating back to the screen from the  Income  by Category, the filters must be persisted.");
	}

	@Test(description = "AT-81509,AT-81464,AT-81499:Tapping a Transaction in Master Category Transactions table should show the Transaction in Edit mode in Transactions popup", dependsOnMethods = {
			"validateFilterStickiness" }, priority = 49)
	public void verifyTransUpdatedToastMsg() throws Exception {
		SeleniumUtil.click(ExpLandingPage.clickHLC());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("Verified that User should be able to select any of the master categories from Legends section");
		List<WebElement> editTrans = ExpLandingPage.verifyTransInEBCOfMLC();
		SeleniumUtil.click(editTrans.get(0));
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(ExpLandingPage.editTransSaveBtnInEBC());
		SeleniumUtil.waitForPageToLoad(2000);

		String transUpdateMsg = ExpLandingPage.verifyTransUpdateMsg().getText().trim();
		logger.info("Actual text of Transaction Updated Message is " + transUpdateMsg);

		Assert.assertEquals(transUpdateMsg, PropsUtil.getDataPropertyValue("ExpectedTransUpdateMsg"));
		SeleniumUtil.click(ExpLandingPage.hlcBackBtn());
	}

	@Test(description = "AT-81510,AT-81496,AT-81457,AT-81431,AT-81315,AT-81316,AT-81324,AT-81325,AT-81326,AT-81331,AT-8133:All the transactions for all the master categories should be displayed in Transactions table and if the transactions are more than 25 the 'Load More' button should display.If the transactions are more,the Load More button should appear at the bottom of the transactions.", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 50)
	public void verifyMLC() throws Exception {

		logger.info("*******Verifying Load More Button for EA********");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(3000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.timeFilterDropdown().click();
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(4));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.selectExpenseCategory());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(ExpLandingPage.showMoreListButton().isDisplayed());
		Assert.assertEquals(ExpLandingPage.showNumberOfTxn().size(), 25);
		logger.info("Load More Button Verified and number of txn displayed is 25");
		SeleniumUtil.click(ExpLandingPage.hlcBackBtn());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******Verified Load More Button for EA********");

		logger.info("*******Verifying Load More Button for IA********");
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.timeFilterDropdown().click();
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(4));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.clickHLC());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(ExpLandingPage.showMoreListButton().isDisplayed());
		Assert.assertEquals(ExpLandingPage.showNumberOfTxn().size(), 25);
		logger.info("Load More Button Verified and number of txn displayed is 25");
		SeleniumUtil.click(ExpLandingPage.hlcBackBtn());
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.timeFilterDropdown().click();
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.clickHLC());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Verifying Load More Button is not displayed if the no of transacrions less than 25 for IA");
		Assert.assertNull(ExpLandingPage.showMoreListButton());
		logger.info("Verified Load More Button is not displayed if the no of transacrions less than 25 for IA");
		SeleniumUtil.click(ExpLandingPage.hlcBackBtn());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******Verified Load More Button for IA********");
	}

	@Test(description = "AT-81476,AT-81463:Verify that if there are no Uncategorized Transactions then NO Data message should be displayed", dependsOnMethods = {
			"verifyMLC" }, priority = 51)
	public void verifyNoUnCatTrans() throws Exception

	{
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("Verifying that there are NO Uncategorized Transactions");
		Assert.assertTrue(ExpLandingPage.verifyNoUnCategorizedData().isDisplayed());
		String actual = ExpLandingPage.verifyNoUnCategorizedDataText().getText().trim();
		logger.info("Actual Text of there are no Uncategorized Transaction is ===> " + actual);
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("ExpectedNoDataForUnCat"));
		logger.info("Verified that there are NO Uncategorized Transactions");

	}

	@Test(description = "AT-81286,AT-81294,AT-81297,AT-81431,AT-81303:Verify that accounts filter specified in the screen should be applied to all further screens except trends screen.", dependsOnMethods = {
			"verifyMLC" }, priority = 52)
	public void verifyAcctFilterForTrends() throws Exception {

		ExpLandingPage.selectingAllExpAcctsForTrendsIncome();
		List<WebElement> trendsAmts = ExpLandingPage.verifyTrendsPopUpAmount();
		String trendsAmts1 = trendsAmts.get(0).getText().trim();
		String trendsAmts2 = trendsAmts.get(1).getText().trim();
		SeleniumUtil.click(ExpLandingPage.closeTrendsPopUp());
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(ExpLandingPage.verifyBackBtnForEBC().isDisplayed());
		logger.info(
				"Verified that tapping the close button in Trend popup closes the popup and takes user to IA by Master Category screen");

		SeleniumUtil.click(ExpLandingPage.verifyBackBtnForEBC());
		SeleniumUtil.waitForPageToLoad(3000);
		ExpLandingPage.selectingAnAccountForTrendsInc(1);
		List<WebElement> trendAmount = ExpLandingPage.verifyTrendsPopUpAmount();
		String expectedTrendsAmts1 = trendAmount.get(0).getText().trim();
		String expectedTrendsAmts2 = trendAmount.get(1).getText().trim();

		logger.info(
				"Verifying that accounts filter specified in the screen should be applied to all further screens except trends screen.");
		Assert.assertEquals(trendsAmts1, expectedTrendsAmts1); // The values should be same before and after selecting
																// the account as it should not affect by
																// selecting/deselecting the accounts in Trends
		Assert.assertEquals(trendsAmts2, expectedTrendsAmts2);
		logger.info(
				"Verified that accounts filter specified in the screen should be applied to all further screens except trends screen.");

	}

	@Test(description = "AT-81460:Verify that if there are no transactions in some High(Expense by category)  Level Categories then the High Level Categories should not be displayed", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 53)
	public void verifyNoTransInHLC() throws Exception

	{

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Adding Manual Transaction for Automative Expense Category!!!");
		SeleniumUtil.click(ExpLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("1234", "Testing", 0, 1, 0, 0, 13, 1,
				PropsUtil.getDataPropertyValue("TransactionNoteForExpense"),
				PropsUtil.getDataPropertyValue("TransactionCheckForExpense"));
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		IncLandingPage.selectIA();
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(ExpLandingPage.verifyHLCInExpenseByCategoryInc().isDisplayed());
		logger.info("Deleting the Transaction added for Automative expense HLC...");
		ExpLandingPage.deleteHLCTransForEBCVerificationIncome();

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		IncLandingPage.selectIA();
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		logger.info(
				"Verify that if there are no transactions in some High(Expense by category)  Level Categories then the High Level Categories should not be displayed");
		Assert.assertNull(ExpLandingPage.verifyHLCTransInExpense());
		logger.info(
				"Verified that if there are no transactions in some High(Expense by category)  Level Categories then the High Level Categories should not be displayed");
	}

	@Test(description = "AT-81470:Verify that selection of the categories should be retained if the user drills down further and comes back to the Expenses By Category screen.", dependsOnMethods = {
			"verifyNoTransInHLC" }, priority = 54)
	public void verifyCategoriesAreRetained() throws Exception {

		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(ExpLandingPage.selectHLCInFilterCategoriesInc());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(ExpLandingPage.verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertNull(ExpLandingPage.verifyHLCInEBC_Categories());
		List<WebElement> hlc = ExpLandingPage.selectHLC_Categories();
		SeleniumUtil.click(hlc.get(0));
		SeleniumUtil.click(ExpLandingPage.verifyBackBtnForEBC());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info(
				"Verifying that selection of the categories should be retained if the user drills down further and comes back to the Expenses By Category screen.");
		Assert.assertNull(ExpLandingPage.verifyHLCInEBC_Categories());
		logger.info(
				"Verified that selection of the categories should be retained if the user drills down further and comes back to the Expenses By Category screen.");

	}

	@Test(description = "AT-81186,AT-81420:Max length for amounts shown on screens should be same as that of account balance field  17 characters overall.", dependsOnMethods = {
			"verifyCategoriesAreRetained" }, priority = 55)
	public void verifyMaxLengthOfAmount() throws Exception {

		logger.info("Adding Manual Transaction with 11 digit amount");
		SeleniumUtil.click(ExpLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("12345678901", "TestingAmount", 0, 1, 0, 0, 13, 1,
				PropsUtil.getDataPropertyValue("TransactionNoteForExpense"),
				PropsUtil.getDataPropertyValue("TransactionCheckForExpense"));

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();
		String text = IncLandingPage.amountOnCurrentMonth().getText();
		char txt[] = text.toCharArray();
		Assert.assertEquals(txt.length, 18);

	}

	@Test(description = "AT-81397:Verify that High level and Master level categories should be renamed by the user from Manage Categories", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 56)
	public void verifyRenamingOfHlcAndMlcInCategories() throws Exception {

		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info(
				"Verifying that High level and Master level categories should be renamed by the user from Manage Categories");
		SeleniumUtil.click(ExpLandingPage.incomeTabCategories());
		Assert.assertTrue(ExpLandingPage.renamingOfHLCInCategories_Income());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(ExpLandingPage.renamingOfMLCInCategories_Income());
		logger.info(
				"Verified that High level and Master level categories are renamed by the user from Manage Categories");
	}

	@Test(description = "AT-81461,AT-81391,AT-81396,AT-81293,AT-81514:If user has renamed High Level Categories, the user provided names should be shown in the Income Analysis Finapp.", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 57) // , dependsOnMethods={"verifyRenamingOfHlcAndMlcInCategories"})
	public void verifyHlcAndMlcInIncome() throws Exception {
		
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		logger.info("Adding Manual Transaction for General Merchandise Category!!!");
		SeleniumUtil.click(ExpLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("1000", "Testing", 0, 1, 0, 0, 1, 1,
				PropsUtil.getDataPropertyValue("TransactionNoteForExpense"),
				PropsUtil.getDataPropertyValue("TransactionCheckForExpense"));

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verifying Renamed HLC is reflecting in Income By Category Screen.");
		Assert.assertTrue(ExpLandingPage.verifyHLCInExpenseByCategory().isDisplayed());
		logger.info("Renamed HLC is reflected in Income By Category Screen.");

		SeleniumUtil.click(ExpLandingPage.verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Verifying Renamed MLC is reflecting in Income By Category Screen.");
		// Assert.assertTrue(ExpLandingPage.verifyMLCInExpenseByCategoryIncome().isDisplayed());
		logger.info("Renamed MLC is reflected in Income By Category Screen.");
		String hlcHeader = ExpLandingPage.finappHeader_HLCHeader().getText().trim();
		Assert.assertEquals(hlcHeader, PropsUtil.getDataPropertyValue("ExpectedHlcHeader_Expense"));
	}

	@Test(description = "AT-81187,AT-81394:Accounts that are deleted should NOT  be included in the IA and EA Finapp,Income Analysis Header should be in title case under IA finapp", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 58)
	public void verifyDeleatedAccounts() throws Exception {

		logger.info("***delete an account**");
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(IncLandingPage.settingsIcon().get(0));
		SeleniumUtil.click(IncLandingPage.deleteAcnt());
		SeleniumUtil.click(IncLandingPage.deleteconfirmcheck());
		SeleniumUtil.click(IncLandingPage.deleteBtn());
		SeleniumUtil.waitForPageToLoad();
		logger.info("***Account Deleted**");

		logger.info("***Navigate to IA page**");
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();
		Assert.assertEquals(IncLandingPage.incomeHeader().getText(),
				PropsUtil.getDataPropertyValue("IA_IATitleTextMobile"));
		logger.info("***Navigate to Income By Category page**");
		SeleniumUtil.click(IncLandingPage.monthbarlink());
		SeleniumUtil.waitForPageToLoad();

		logger.info("***Validate in the dropdown that the account deleted is not coming in the DD **");
		SeleniumUtil.click(IncLandingPage.allIncomeAccount());
		Assert.assertNull(IncLandingPage.accountDeleted());

		logger.info("***Navigate to EA page**");
		PageParser.forceNavigate("Expense", d);

		logger.info("***Navigate to Income By Category page**");
		SeleniumUtil.click(IncLandingPage.monthbarlink());
		SeleniumUtil.waitForPageToLoad();

		logger.info("***Validate in the dropdown that the account deleted is not coming in the DD **");
		SeleniumUtil.click(ExpLandingPage.allAccDropDownBlock());
		Assert.assertNull(IncLandingPage.accountDeleted());
	}

	@Test(description = "AT-81188,AT-81395:Accounts that are closed should be included in the IA And EA Finapp", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 59)
	public void verifyClosedAccounts() throws Exception {

		logger.info("***close an account**");
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(IncLandingPage.settingsIcon().get(0));
		SeleniumUtil.click(IncLandingPage.closeAcnt());
		SeleniumUtil.click(IncLandingPage.confirmCloseAccnt());
		SeleniumUtil.waitForPageToLoad();

		logger.info("***Navigate to IA page**");
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();
		logger.info("***Navigate to Income By Category page**");
		SeleniumUtil.click(IncLandingPage.monthbarlink());
		SeleniumUtil.waitForPageToLoad();

		logger.info("***Validate in the dropdown that the account deleted is not coming in the DD **");
		SeleniumUtil.click(IncLandingPage.allIncomeAccount());
		Assert.assertTrue(IncLandingPage.accountClosed().isDisplayed());

		logger.info("***Navigate to EA page**");
		PageParser.forceNavigate("Expense", d);

		logger.info("***Navigate to Income By Category page**");
		SeleniumUtil.click(IncLandingPage.monthbarlink());
		SeleniumUtil.waitForPageToLoad();

		logger.info("***Validate in the dropdown that the account deleted is not coming in the DD **");
		SeleniumUtil.click(ExpLandingPage.allAccDropDownBlock());
		Assert.assertTrue(IncLandingPage.accountClosed().isDisplayed());

	}

	@Test(description = "AT-81205,AT-81422:Verify that In case there are less than 5 (Top expenses for this month) transactions for the current month, then the transactions section should show the available number of transactions", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 60)
	public void verifyNumberOfTxn() throws Exception {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		int numberOfTxn = ExpLandingPage.transactionList().size();
		if (numberOfTxn <= 5) {
			Assert.assertTrue(true);
			logger.info(
					"Verified  that the number of transactions for the current month is less than or equal to 5 for EA");
		} else {
			Assert.assertTrue(false);
			logger.info("Verified  that the number of transactions for the current month is Greater than 5 for EA");
		}
		PageParser.forceNavigate("Expense", d);
		IncLandingPage.selectIA();

		int numberOfTxnIA = ExpLandingPage.transactionList().size();
		if (numberOfTxnIA <= 5) {
			Assert.assertTrue(true);
			logger.info(
					"Verified  that the number of transactions for the current month is less than or equal to 5 for IA");
		} else {
			Assert.assertTrue(false);
			logger.info("Verified  that the number of transactions for the current month is Greater than 5 for IA");
		}
	}

	@Test(description = "AT-81473,AT-81492,AT-81311:If user has one category in the list, then Filter Category option should be disabled.By default none of the Master Category should appear selected, neither in chart nor in the Legends section", dependsOnMethods = {
			"naviagateToIncomeAnalysis2" }, priority = 61)
	public void verifyDateInDescendingOrder() throws Exception {

		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.timeFilterDropdown().click();
		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(1));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertNull(ExpLandingPage.verifyEAFilterCategories());

		SeleniumUtil.click(ExpLandingPage.getDateFormat().get(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.verifyHLCInExpenseByCategoryInc());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(ExpLandingPage.txnHeaderMLC().isDisplayed());
		logger.info(
				" verified By default none of the Master Category should appear selected, neither in chart nor in the Legends section in IA");
	}
}
