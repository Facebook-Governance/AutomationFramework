/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author Priyanka
 ******************************************************************************/

package com.omni.pfm.SustMRBugs;

import org.testng.annotations.BeforeClass;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.Categories.Manage_Categories_Loc;
import com.omni.pfm.pages.CategorizationRules.CategorizationRules_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.testBase.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import com.omni.pfm.AccountSettings.YCOM_Regression_ManualAccountSettings_Test;
import com.omni.pfm.MRBugs.MRBugs_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.utility.FunctionUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class StabNPRCatchupMay2018 extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(StabNPRCatchupMay2018.class);
	NetWorth_Loc netWorth = null;
	MRBugs_Loc MRBugs_Loc;
	ExpLandingPage_Loc ExpLandingPage;
	Manage_Categories_Loc manage_Categories_Loc;
	Transaction_Categorization_Rules_Loc rule1;
	Add_Manual_Transaction_Loc add_manual_transaction;
	Budget_Select_Accounts_Loc select_accounts;
	Budget_Income_And_Bill_Loc Budget_Income_Bill;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_No_Account_Loc no_accounts;
	Budget_Landing_Page_Loc Budget_Landingpage;
	AccountAddition accAddition = new AccountAddition();
	Budget_Income_And_Bill_Summery_Loc Budget_Income_Bill_Summery;
	Budget_Settings_Loc Budget_Settings;
	CategorizationRules_Loc categorization;
	Create_Budget_Loc createbudget;
	public AggregatedAccount_Settings_Loc account_Settings_Loc;
	public ManualAccount_Settings_Loc settingsLoc;
	YCOM_Regression_ManualAccountSettings_Test manualAcc;
	MRBugs_Loc MRBugsLoc;
	AccountAddition accountAdd;
	AddToCalendar_Transaction_Loc AddToCalendar;
	Transaction_Split_Locator split;
	Transaction_Search_Loc search;

	@BeforeClass()
	public void init() {
		doInitialization("Manage Categories");
		manage_Categories_Loc = new Manage_Categories_Loc(d);
		MRBugs_Loc = new MRBugs_Loc(d);
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		select_accounts = new Budget_Select_Accounts_Loc(d);
		Budget_Income_Bill = new Budget_Income_And_Bill_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		Budget_Income_Bill_Summery = new Budget_Income_And_Bill_Summery_Loc(d);
		no_accounts = new Budget_No_Account_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		ExpLandingPage = new ExpLandingPage_Loc(d);
		rule1 = new Transaction_Categorization_Rules_Loc(d);
		netWorth = new NetWorth_Loc(d);
		Budget_Settings = new Budget_Settings_Loc(d);
		categorization = new CategorizationRules_Loc(d);
		account_Settings_Loc = new AggregatedAccount_Settings_Loc(d);
		settingsLoc = new ManualAccount_Settings_Loc(d);
		manualAcc = new YCOM_Regression_ManualAccountSettings_Test();
		MRBugsLoc = new MRBugs_Loc(d);
		accountAdd = new AccountAddition();
		ExpLandingPage = new ExpLandingPage_Loc(d);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		split = new Transaction_Split_Locator(d);
		search = new Transaction_Search_Loc(d);
		createbudget = new Create_Budget_Loc(d);

	}

	@Test(description = "AT-74577-Bug1003010:The Select Group option name changes to Create Group option on click of next button in Budget window.", priority = 1)
	public void verifySelectGroupbug1003010() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		PageParser.forceNavigate("Budget", d);

		SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(select_accounts.NextButton2());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(Budget_Landingpage.editincome());
		SeleniumUtil.waitForPageToLoad();
		Budget_Landingpage.editincometext().clear();
		Budget_Landingpage.editincometext().sendKeys(PropsUtil.getDataPropertyValue("income1"));
		Budget_Landingpage.editincometext().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Edited the income text*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Edit Income Button*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
		SeleniumUtil.waitForPageToLoad(8000);
		logger.info("*************************Clicked on Income Next Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.flexibleSpendingEditIcon());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on flexible Spending Edit Icon*************************");

		if (Config.getInstance().getEnvironment().contains("BBT")) {
			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
					PropsUtil.getDataPropertyValue("amountForAtmCategory"));
			SeleniumUtil.waitForPageToLoad(2500);

			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAutomotive"),
					PropsUtil.getDataPropertyValue("amountForAutomotiveCAtegory"));
			SeleniumUtil.waitForPageToLoad(2500);

			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryService"),
					PropsUtil.getDataPropertyValue("amountForCategoryService"));
			SeleniumUtil.waitForPageToLoad(2500);

			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryOthers"),
					PropsUtil.getDataPropertyValue("amountForCategoryOthers"));
			SeleniumUtil.waitForPageToLoad(2500);
			logger.info("*************************Edited all the Category Values-amounts*************************");

		} else {

			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
					PropsUtil.getDataPropertyValue("amountForAtmCategory"));
			SeleniumUtil.waitForPageToLoad(2500);

//			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryForEntertainment1"),
//					PropsUtil.getDataPropertyValue("amountForCategoryEntaertainment"));
			SeleniumUtil.waitForPageToLoad();
			Budget_Income_Bill.CategoryValues().sendKeys(Keys.TAB);
			logger.info("*************************Edited all the Category Values-amounts*************************");

		}

		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		WebDriverWait wait = new WebDriverWait(d, 40);
		SeleniumUtil.waitForPageToLoad(10000);

		WebElement ele = wait.until(ExpectedConditions.visibilityOf(Budget_FlexibleSpending.doneButton()));
		System.out.println(ele);
		logger.info("*************************Clicked on income Save Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		SeleniumUtil.waitForPageToLoad(10000);
		logger.info("*************************Clicked on Done Button*************************");

		//Assert.assertTrue(Budget_FlexibleSpending.createdBudgetheader().isDisplayed());
		logger.info("*************************My Budget Header Verified*************************");

		SeleniumUtil.waitForPageToLoad(6500);
		PageParser.forceNavigate("Budget", d);
		logger.info("*******************Navigated to budget page*************");
		SeleniumUtil.click(MRBugs_Loc.CreateBudgetHeader());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************clicked on Create Budget Header*************");
		SeleniumUtil.click(MRBugs_Loc.creategroupbtn());
		createbudget.GroupInputBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(createbudget.CheckBox().get(0));
		SeleniumUtil.click(createbudget.CheckBox().get(1));
		SeleniumUtil.click(createbudget.CheckBox().get(2));
		SeleniumUtil.click(createbudget.CreateGroupButton());
		SeleniumUtil.waitForPageToLoad(20000);
		SeleniumUtil.click(MRBugs_Loc.nextbtnBudget());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(SeleniumUtil.isVisible(MRBugs_Loc.SelectGroup(), "Select group is visible"));
		//Verifies The Select Group option name not  changes to Create Group option on click of next button in Budget window.
	}

	@Test(description = "AT-74561- Bug952964:ACCT-01_01: creating user and adding account.", priority = 2)
	public void verifybug952964() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("CashFlow", d);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.DeleteAccount());
		SeleniumUtil.click(MRBugs_Loc.Checkbox());
		SeleniumUtil.click(MRBugs_Loc.Deleteconfirmbtn());
		SeleniumUtil.waitForPageToLoad(3000);
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
		TestBase.imageprocess.captureScreenshot(d, "CashFlowLinkanAccountScreen", TestBase.screenShotEnabledFlag, false,
				5000);
	}

	@Test(description = "AT-74565:Bug 976087 - [MERRILL_PFM3.0][NEIL_ARMSTRONG]While selecting all accounts once after selecting multiple accounts no data screen is getting displayed.", groups = {
			"DesktopBrowsers" }, priority = 3, enabled = true)
	public void VerifyNoDataScreenNotDisplayed() throws Exception {
		SeleniumUtil.waitForPageToLoad(2000);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(4000);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(40000);
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(netWorth.continueButton());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(netWorth.seeMyNetWorthButton());
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(30000);
		SeleniumUtil.click(netWorth.allAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.MultipleAccFirstCheckbox().click();
		SeleniumUtil.waitForPageToLoad(10000);
		MRBugs_Loc.MultipleAccFirstCheckbox().click();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(netWorth.noDataDisplayed() == null);// Bug 976087
		//Verifies While selecting all accounts once after selecting multiple accounts no data screen is not getting displayed.
	}

	@Test(description = "AT-74564:Bug 976068 - [MERRILL_PFM3.0][NEIL_ARMSTRONG]While changing the account and time filters by unchecking the assets checkbox, automatically it is getting turned on but values are is not showing in graph.", groups = {
			"DesktopBrowsers" }, priority = 4, enabled = true)
	public void verifyNoDataFoundOnUncheckingAllAccounts() throws Exception {
		SeleniumUtil.waitForPageToLoad(2000);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(4000);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(40000);
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(netWorth.continueButton());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(netWorth.seeMyNetWorthButton());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(MRBugs_Loc.AssetsCheckbox());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(netWorth.allAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.MultipleAccFirstCheckbox().click();
		SeleniumUtil.waitForPageToLoad(10000);
		String Attribute = MRBugs_Loc.AssetsCheckbox().getAttribute("aria-checked");
		Assert.assertEquals(Attribute, "true", "Checkbox is not checked");
		Assert.assertFalse(SeleniumUtil.isVisible(MRBugs_Loc.AssetsChart(), "Assets chart is not visible"));
		//Verifies that the chart is visible

	}

	@Test(description = "AT-74566:Bug 980062 - PFM 3.0 -Investment Holdings - The Grand Total field is breaking when the user collapses the component.", priority = 5, enabled = true)
	public void GrandTotalField() throws Exception {
		SeleniumUtil.waitForPageToLoad(4000);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(4000);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(40000);
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad(20000);
		SeleniumUtil.click(d.findElement(By.xpath("//a[@id='ftue-continue']")));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Go to my investments']")));
		SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.click(MRBugs_Loc.AssetDropdown());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.RegionSelect());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.FirstRegion());
		SeleniumUtil.waitForPageToLoad(10000);
		WebElement GrandTotalField = MRBugs_Loc.GrandTotalField();
		SeleniumUtil.waitForPageToLoad(2500);
		Point GrandTotalFieldPos = GrandTotalField.getLocation();
		int xcordGrandTotalFieldPos = GrandTotalFieldPos.getX();
		System.out.println("Element's x Position " + xcordGrandTotalFieldPos + " pixels.");
		int ycordGrandTotalFieldPos = GrandTotalFieldPos.getY();
		System.out.println("Element's y Position " + ycordGrandTotalFieldPos + " pixels.");
		SeleniumUtil.waitForPageToLoad(2500);
		Assert.assertEquals(xcordGrandTotalFieldPos, 425);
		Assert.assertEquals(ycordGrandTotalFieldPos, 2640);
		WebElement TotalMarketValueField = MRBugs_Loc.TotalMarketValueField();
		SeleniumUtil.waitForPageToLoad(2500);
		Point TotalMarketValueFieldPos = TotalMarketValueField.getLocation();
		int xcordTotalMarketValueFieldPos = TotalMarketValueFieldPos.getX();
		System.out.println("Element's x Position " + xcordTotalMarketValueFieldPos + " pixels.");
		int ycordTotalMarketValueFieldPos = GrandTotalFieldPos.getY();
		System.out.println("Element's y Position " + ycordTotalMarketValueFieldPos + " pixels.");
		SeleniumUtil.waitForPageToLoad(2500);
		Assert.assertEquals(xcordTotalMarketValueFieldPos, 966);
		Assert.assertEquals(ycordTotalMarketValueFieldPos, 2640);
		//Verifies the grand total field does not break
	}

	@Test(description = "AT-74586:Bug 980066 - PFM 3.0 -Investment Holdings - The Disclaimer message does not show the disclaimer for '§' even when the symbol is displayed.", priority = 6, enabled = true)
	public void DisclaimerMessageDoesNotShow() throws Exception {
		SeleniumUtil.waitForPageToLoad(4000);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(4000);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(40000);
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad(20000);
		SeleniumUtil.click(d.findElement(By.xpath("//a[@id='ftue-continue']")));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Go to my investments']")));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.FirstRegion());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.GrpAccountsCheckbx());
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(MRBugs_Loc.GrandTotalDisclaimerMsg().getText()
				.contains(PropsUtil.getDataPropertyValue("grandtotaldiscMsg")));
		//Verifies disclaimer message
		SeleniumUtil.waitForPageToLoad(2000);

	}

	@Test(description = "AT-74576:Bug1002729[MAYMR18_ARMSTRONG] :[ACS]UI issue in calendar pop up in networth finapp", priority = 9)
	public void CalenderUI() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		LoginPage.loginMain(d, TestBase.loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		SeleniumUtil.waitForPageToLoad();

		PageParser.navigateToPage("NetWorth", d);
		SeleniumUtil.waitForPageToLoad(50000);
		SeleniumUtil.click(netWorth.continueButton());
		SeleniumUtil.click(netWorth.seeMyNetWorthButton());
		SeleniumUtil.waitForPageToLoad(10000);
		//SeleniumUtil.click(MRBugs_Loc.calenderpopup());
		SeleniumUtil.waitForPageToLoad();
		//Assert.assertTrue(MRBugs_Loc.CalenderTitle().isDisplayed());
		//TestBase.imageprocess.captureScreenshot(d, "networthcalenderUI", TestBase.screenShotEnabledFlag, false, 5000);
		//Verifies calendar popup
            	}

	@Test(description = "AT-74581:Bug1003326[MAYMR18_ARMSTRONG] :All category drop down is getting cut in Categorization finapp. ", priority = 10)

	public void categorizationdropdownUI()

	{
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(categorization.createRulesButton());
		SeleniumUtil.click(categorization.categoriesDropDown());
		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,-500)", "");

		Assert.assertEquals(categorization.getCategoryLL().size(), Integer.parseInt("43"));

		TestBase.imageprocess.captureScreenshot(d, "CategorizationCategorydropdown", TestBase.screenShotEnabledFlag,
				false, 5000);
		//Verifies category dropdown

	}

	@Test(description = "AT-74578:Bug 1003150-[MAYMR18_ARMSTRONG]: Tag field in account settings pop-up is taking some special characters which is not allowed.", priority = 11)
	public void verifyTagsErrorMsgBug3150() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad(10000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.selectDropDown(d, "checking", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.getVisibileWebElement(d, "Tags", "AccountsPage", null)
				.sendKeys(PropsUtil.getDataPropertyValue("TagserrorSymbol"));
		SeleniumUtil.click(MRBugs_Loc.Addbtn());
		SeleniumUtil.waitForPageToLoad(5000);
		WebElement we = SeleniumUtil.getVisibileWebElement(d, "TagserrorMsg", "AccountsPage", null);
		boolean status = FunctionUtil.verifyText(d, we, PropsUtil.getDataPropertyValue("Tagserrormsgtext"));
		status = SeleniumUtil.findElementWithText(d, null, PropsUtil.getDataPropertyValue("Tagserrormsgtext"),
				100) != null ? true : false;
		Assert.assertTrue(status);
		//Verifie Tag Error message 
		SeleniumUtil.click(MRBugs_Loc.cancelbtn());
		SeleniumUtil.refresh(d);
	}

	@Test(description = "AT-74585:Bug 1005230-[MAYMR18_ARMSTRONG][ALL][PFM3.0]: Liabilities data is not seen if any one account is Un-selected", priority = 12)
	public void verifyingBug1005230() throws Exception {
		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.AccountSelectDropDown());
		SeleniumUtil.click(MRBugs_Loc.unseclectAccount());
		SeleniumUtil.waitForPageToLoad();
		String Currentbalance = MRBugs_Loc.LiabilitiesBalance().getText().trim();
		Assert.assertFalse(Currentbalance.equalsIgnoreCase(PropsUtil.getDataPropertyValue("Liabilitieszerobalance")));
        //Verifies Liabilities data
	}

	@Test(description = "AT-74563:Bug962126-PFM 3.0 - Settings - current balance for the manual accounts is allowing more than 15 digits in the account settings finapp", groups = {
			"DesktopBrowsers,sanity" }, priority = 13, alwaysRun = true)
	public void Login() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {

		} else {

			SeleniumUtil.waitForPageToLoad(10000);
			LoginPage.loginMain(d, TestBase.loginParameter);
			accountAdd.linkAccount();
			SeleniumUtil.waitForPageToLoad(10000);
			accountAdd.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
			SeleniumUtil.waitForPageToLoad(10000);
			SeleniumUtil.waitForPageToLoad();
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(10000);
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if (account_Settings_Loc.stopRefreshButton() != null) {
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}

			SeleniumUtil.waitForPageToLoad();

		}

		if (Config.getInstance().getCurrentPage() != "AccountsPage") {
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
		}
		SeleniumUtil.waitForPageToLoad(10000);

		logger.info("Adding Manual Account for Bank Container.");

		settingsLoc.addBankManualAccount("Cash", "Manual Account", "this string is more than 40 characters i", "2500",
				"null", "null");

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(10000);

		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);

		SeleniumUtil.waitForPageToLoad(10000);

		if (Config.getInstance().getCurrentPage() != "AccountsPage") {

			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);

			SeleniumUtil.waitForPageToLoad();

			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if (account_Settings_Loc.stopRefreshButton() != null) {
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}

		}
		logger.info("Cliking on More options dotted lines for Manual Cash Account added for Bank Container.");
		SeleniumUtil.click(account_Settings_Loc.manualCashAccountMoreOptions());

		List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
		int s = l.size();
		int i = s - 1;

		SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));

		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("Sending a large number for Amount Number field.");
		settingsLoc.typeCurrentBalance(PropsUtil.getDataPropertyValue("AccountSettingsLargeAccountNumber").trim());

		String text = MRBugsLoc.getErrorText().getText();

		logger.info("Verifying if the currency field has the expected error text.");
		Assert.assertTrue(text.contains(PropsUtil.getDataPropertyValue("CurrencyBalanceError")));

		logger.info(text);

		logger.info("Closing the floater.");
		settingsLoc.closePopup();
		SeleniumUtil.waitForPageToLoad();

	} 
	@Test(description = "AT-74562:Bug955700BB&T|Transactions are miscategorized in budget.", priority = 14)
	public void verifytxndescEA() throws Exception {
		LoginPage.loginMain(d, TestBase.loginParameter);

		SeleniumUtil.waitForPageToLoad();

		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(10000);

		if (PageParser.isElementPresent("continueButton", "Expense", null)) {
			SeleniumUtil.click(ExpLandingPage.continueButton());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(ExpLandingPage.gotoAnalysisBtn());
		}
		SeleniumUtil.waitForPageToLoad(5000);

		ExpLandingPage.clickChart(5);

		Assert.assertTrue(ExpLandingPage.expensebyCategory().isDisplayed());

		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(ExpLandingPage.highLevelCategory().isDisplayed());

		ExpLandingPage.highLevelCategory().click();

		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(ExpLandingPage.masterCategory().isDisplayed());
		ExpLandingPage.masterCategory().click();
		SeleniumUtil.waitForPageToLoad();
		String desc = MRBugsLoc.getDescText().getText();
		String cat = MRBugsLoc.getCatText().getText();

		logger.info(desc);

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		search.allSearch().clear();
		search.allSearch().sendKeys(desc);
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(AddToCalendar.PendingStranctionList().get(0));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(split.SplitLink().isDisplayed());
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.waitForPageToLoad(5000);

		split.catField().click();
		split.listCat(1, 1).get(0).click();
		Assert.assertTrue(split.OrginalRow().getText().contains(PropsUtil.getDataPropertyValue("splitChangecat10")));

		SeleniumUtil.click(split.splitedrow());
		SeleniumUtil.waitForPageToLoad();
		split.listCatSplited(1, 1).get(0).click();
		Assert.assertTrue(split.splitedrow().getText().contains(PropsUtil.getDataPropertyValue("splitChangecat10")));

		Assert.assertTrue(split.savechanges().isDisplayed());
		SeleniumUtil.click(split.savechanges());
		SeleniumUtil.waitForPageToLoad(15000);

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(10000);
		ExpLandingPage.clickChart(5);
		SeleniumUtil.waitForPageToLoad(20000);
		ExpLandingPage.highLevelCategory().click();

		SeleniumUtil.waitForPageToLoad(10000);

		Assert.assertTrue(ExpLandingPage.masterCategory().isDisplayed());
		SeleniumUtil.click(d.findElement(By.xpath("//span[contains(@title,'" + cat + "')]")));
		Assert.assertTrue(MRBugsLoc.getNewCat() == null);

	}

	@Test(description = "AT-74573,AT-74569:Bug992748[Citi_PFM3.0]Getting actuals are dispalyed as -ve in Budget Income progress bar evn though user doesn't have any debit transactions for income categorires,AT-74569:BUG987623 [MS_PFM3.0_UI][Budget]: Actuals shown in Budget bars in summary section for spending is considering only debits, it should calculate using debits - credits", priority = 15)

	public void verifybudgetactuals() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(5000);

		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d, By.id("dashboardLinkAccountBtn"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.id("dashboardLinkAccountBtn")).isDisplayed()) {
				d.findElement(By.id("dashboardLinkAccountBtn")).click();
			}
			SeleniumUtil.waitForPageToLoad(3000);
			try {
				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} catch (Exception e) {

			}
			SeleniumUtil.waitForPageToLoad(10000);
			if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
				SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
			} else {
				SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
			}
			SeleniumUtil.waitForPageToLoad(3000);
		}

		SeleniumUtil.waitForPageToLoad(45000);
		SeleniumUtil.click(d.findElement(By.xpath("//span[text()='Account Type']")));
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(d.findElement(By.xpath("//li[contains(text(),'Cash')]")));

		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys("MyAccountbank");
		SeleniumUtil.waitForPageToLoad();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(Keys.TAB);

		if ("null".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'nickName')]")).sendKeys("null");
				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}

		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")));

		if (!"1001".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys("0.01");
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(Keys.TAB);

				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}

		if (!"null".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).sendKeys("null");
			} catch (Exception e) {
				System.out.println("Not visble");
			}
		} else {

			System.out.println("Not visble");

		}

		if ("null".equalsIgnoreCase("04/08/2019")) {
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).clear();
			System.out.println("Date is" + "null");
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).sendKeys("null");
			SeleniumUtil.waitForPageToLoad(2500);
		} else {
			System.out.println("Not visble");
		}

		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(d.findElement(By.id("addBtn")));

		SeleniumUtil.waitForPageToLoad(10000);

		SeleniumUtil.click(MRBugs_Loc.fastLinkCloseIcon());
		SeleniumUtil.waitForPageToLoad(10000);
		/*
		d.navigate().refresh();

		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(add_manual_transaction.addManualIcon().isDisplayed());
		Assert.assertTrue(add_manual_transaction.addManualLink().isDisplayed());
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(10000);

		add_manual_transaction.amount().sendKeys(PropsUtil.getDataPropertyValue("Amount6"));
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description1"));

		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(add_manual_transaction.TransactionType());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(0));

		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(add_manual_transaction.catgorie());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgoryList(5, 5));
		add_manual_transaction.add().click();

		SeleniumUtil.waitForPageToLoad(10000);

		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(10000);

		add_manual_transaction.amount().sendKeys(PropsUtil.getDataPropertyValue("Amount1"));
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description3"));

		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		SeleniumUtil.waitForPageToLoad();

		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(add_manual_transaction.catgorie());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgoryList(5, 5));
		add_manual_transaction.add().click();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(10000);

		PageParser.forceNavigate("Budget", d);
		logger.info("*************************Navigated to Budget Page************");

		SeleniumUtil.waitForPageToLoad(60000);

		SeleniumUtil.waitForElement("YCOM_Mainline", 60000);
		SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(select_accounts.NextButton2());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
		SeleniumUtil.waitForPageToLoad(8000);
		logger.info("*************************Clicked on Income Next Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.flexibleSpendingEditIcon());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on flexible Spending Edit Icon*************************");

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
				PropsUtil.getDataPropertyValue("amountForAtmCategory1"));

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryRestaurants"),
				PropsUtil.getDataPropertyValue("amountForAtmCategory1"));

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categorySavings"),
				PropsUtil.getDataPropertyValue("amountForAtmCategory1"));

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryGrocery"),
				PropsUtil.getDataPropertyValue("amountForAtmCategory1"));

		SeleniumUtil.waitForPageToLoad(2500);

		Budget_Income_Bill.CategoryValues().sendKeys(Keys.TAB);
		logger.info("*************************Edited all the Category Values-amounts*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		WebDriverWait wait = new WebDriverWait(d, 40);
		WebElement ele = wait.until(ExpectedConditions.visibilityOf(Budget_FlexibleSpending.doneButton()));
		System.out.println(ele);
		logger.info("*************************Clicked on income Save Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		SeleniumUtil.waitForPageToLoad(25000);
		logger.info("*************************Clicked on Done Button*************************");

		Assert.assertTrue(Budget_FlexibleSpending.createdBudgetheader().isDisplayed());
		logger.info("*************************My Budget Header Verified*************************");

		Assert.assertTrue(MRBugs_Loc.Budgetactuals().isDisplayed());
		Assert.assertEquals(MRBugs_Loc.Budgetactuals().getText(), PropsUtil.getDataPropertyValue("Budgetexp"));

		logger.info("*************************Budget Actuals with negative sign Verified*************************");*/
	}

}
