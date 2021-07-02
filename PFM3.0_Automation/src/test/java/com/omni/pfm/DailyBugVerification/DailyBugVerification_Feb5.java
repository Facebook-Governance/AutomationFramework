/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.

 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author Abhinandan
 ******************************************************************************/

package com.omni.pfm.DailyBugVerification;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.MRBugs.MRBugs_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.SustMRBugs.StabNPRCatchupMay2018;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DailyBugVerification_Feb5 extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(StabNPRCatchupMay2018.class);
	AccountAddition accAddition = new AccountAddition();
	public ManualAccount_Settings_Loc settingsLoc;
	MRBugs_Loc MRBugs_Loc;
	CashFlow_LandingPage_Loc LandingPage;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_Landing_Page_Loc Budget_Landingpage;
	Budget_Select_Accounts_Loc select_accounts;
	Budget_No_Account_Loc no_accounts;
	Add_Manual_Transaction_Loc add_manual_transaction;
	public AccountGroup_Settings_Loc groupSettingLoc;

	@BeforeClass()
	public void init() {
		doInitialization("Manage Categories");
		settingsLoc = new ManualAccount_Settings_Loc(d);
		MRBugs_Loc = new MRBugs_Loc(d);
		LandingPage = new CashFlow_LandingPage_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		select_accounts = new Budget_Select_Accounts_Loc(d);
		no_accounts = new Budget_No_Account_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		groupSettingLoc = new AccountGroup_Settings_Loc(d);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/*
	 * Alert settings link is showing in more options of Budget finapp.
	 */
	@Test(description = "AT-99487:1137479", enabled = true, priority = 1)
	public void alertSettingsForBudget() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");		
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"), PropsUtil.getDataPropertyValue("dagSitePassword"));
	
		// Navigates to Budget Page
		PageParser.forceNavigate("Budget", d);

		// Create Budget page
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// More options
		MRBugs_Loc.moreIcon().click();
		SeleniumUtil.waitForPageToLoad(1000);
		//Validation
		Assert.assertFalse(MRBugs_Loc.alertSettings().isDisplayed(), "FAIL: Alert settings option not present");
		logger.info("PASS: Alert settings option present");

	}

	/*
	 * Mismatch in displaying Netcash flow total in cash flow finapp
	 */
	@Test(description = "AT-99490:1174595", enabled = true, priority = 2)
	public void mismatchNetCashFlow() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
	
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"), PropsUtil.getDataPropertyValue("dagSitePassword"));

		PageParser.forceNavigate("CashFlow", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		//Handle pop-ups
		MRBugs_Loc.continuePopup();
		// Get text from Net cash flow labels
		String netCash1 = MRBugs_Loc.netCashFLow1().getText().replaceAll("[^a-zA-Z0-9]", "");
		String netCash2 = MRBugs_Loc.netCashFLow2().getText().replaceAll("[^a-zA-Z0-9]", "");
		//Validaton
		Assert.assertEquals(Integer.parseInt(netCash1), Integer.parseInt(netCash2),"FAIL :Net cash flow total are not matching");
		logger.info("PASS :Net Cash flow total are matching");

	}

	/*
	 * Uncategorized Transactions are missing when user selects any one of the
	 * account in Expense analysis and navigates to Income analysis finapp.
	 */
	@Test(description = "AT-99489:1170043", enabled = true, priority = 3)
	public void incorrectCashFlowdate() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
	
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),PropsUtil.getDataPropertyValue("dagSitePassword"));
		//Wait for Spinner to disappear
	
		PageParser.forceNavigate("Expense", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		//Handle pop-ups
		MRBugs_Loc.continuePopup();
		MRBugs_Loc.ExpenseDropDown().click();
		MRBugs_Loc.IncomeAnalysis().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Add Transaction to Uncategorized group
		MRBugs_Loc.transactionAdd().click();
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionDesc"));
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionTypeeDropDown().click();
		MRBugs_Loc.transactionTypeValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionDebitedFrom().click();
		MRBugs_Loc.transactionDebitedFromvalue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactioncategoryDropDown().click();
		MRBugs_Loc.transactioncategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionCategory"));
		MRBugs_Loc.transactioncategoryDropdownValue().click();
		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Clicking on current month date
		List<WebElement> li = MRBugs_Loc.ListofMonths();
		li.get(1).click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		//Validation
		Assert.assertTrue(MRBugs_Loc.CategoryNameUncategorized().isDisplayed(),"FAIL :Uncategorized transactions are not displayed");
		logger.info("PASS :Uncategorized transactions are displayed");

	}

	/*
	 * Cash flow date range, showing incorrect.
	 */
	@Test(description = "AT-99488:1167845", enabled = true, priority = 4)
	public void cashFlowDateRange() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");

		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"), PropsUtil.getDataPropertyValue("dagSitePassword"));
	
		PageParser.forceNavigate("CashFlow", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		//Handle pop-ups
		MRBugs_Loc.continuePopup();
		// Selecting date and account from drop-down
		MRBugs_Loc.durationMonth().click();
		MRBugs_Loc.durationThisMonth().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.acntsDropDown().click();
		MRBugs_Loc.firstAvailableAccount().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.durationMonth().click();
		MRBugs_Loc.durationThisMonth().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Get current month-Year
		String monthExpected = DateUtil.get_Month().toUpperCase().toUpperCase();
		// Get size of total date displayed
		int size = MRBugs_Loc.monthXaxis().size();
		String monthExpectedActual = MRBugs_Loc.monthXaxis().get(0).getText();

		//validation
		if (size == 1 && monthExpected.equals(monthExpectedActual)) {

			logger.info("PASS :Cash flow date range, showing correct data");

		} else {

			Assert.fail("FAIL :Cash flow date range, showing incorrect");
			logger.info("FAIL :Cash flow date range, showing incorrect");

		}

	}

}
