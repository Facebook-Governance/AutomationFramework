/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.

 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author Abhinandan
 ******************************************************************************/

package com.omni.pfm.DailyBugVerification;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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

public class DailyBugVerification_Feb_March_19 extends TestBase {
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
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Navigates to Budget Page
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
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
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.moreIcon().click();
		SeleniumUtil.waitForPageToLoad(1000);
		// Validation
		Assert.assertTrue(MRBugs_Loc.alertSettings().isDisplayed(), "FAIL: Alert settings option not present");
		logger.info("PASS: Alert settings option present");

	}

	/*
	 * Mismatch in displaying Netcash flow total in cash flow finapp
	 */
	@Test(description = "AT-99490:1174595", enabled = true, priority = 2)
	public void mismatchNetCashFlow() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		PageParser.forceNavigate("CashFlow", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();
		SeleniumUtil.waitForPageToLoad();
		// Get text from Net cash flow labels
		String netCash1 = MRBugs_Loc.netCashFLow1().getText().replaceAll("[^a-zA-Z0-9]", "");
		String netCash2 = MRBugs_Loc.netCashFLow2().getText().replaceAll("[^a-zA-Z0-9]", "");
		// Validaton
		Assert.assertEquals(Integer.parseInt(netCash1), Integer.parseInt(netCash2),
				"FAIL :Net cash flow total are not matching");
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
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		PageParser.forceNavigate("Expense", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();
		MRBugs_Loc.iAiEDropDown().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.IncomeAnalysis().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Add Transaction to Uncategorized group
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.transactionAdd().click();
		SeleniumUtil.waitForPageToLoad(3000);
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionDesc"));
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualTypeDropDown().click();
		String [] transType =MRBugs_Loc.transactionManualTypeValue();
		d.findElement(By.xpath(transType[1].replace("+", "Deposit"))).click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualDebitedFrom().click();
		MRBugs_Loc.transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactioncategoryDropDown().click();
		MRBugs_Loc.transactioncategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionCategory"));
		MRBugs_Loc.transactioncategoryDropdwnVal().click();
		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Clicking on current month date
		SeleniumUtil.click(MRBugs_Loc.listmothFirst());
		
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Validation
		Assert.assertTrue(MRBugs_Loc.CategoryNameUncategorized().isDisplayed(),
				"FAIL :Uncategorized transactions are not displayed");
		logger.info("PASS :Uncategorized transactions are displayed");

	}

	/*
	 * Cash flow date range, showing incorrect.
	 */
	@Test(description = "AT-99488:1167845", enabled = true, priority = 4)
	public void cashFlowDateRange() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		PageParser.forceNavigate("CashFlow", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();
		// Selecting date and account from drop-down
		MRBugs_Loc.durationMonth().click();
		MRBugs_Loc.durationThisMonth().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.acntsDropDown().click();
		MRBugs_Loc.firstAvailableAccount().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.durationMonth().click();
		MRBugs_Loc.durationThisMonth().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		// Get current month-Year
		String monthExpected = DateUtil.get_Month().toUpperCase().toUpperCase();
		// Get size of total date displayed
		int size = MRBugs_Loc.monthXaxis().size();
		String monthExpectedActual = MRBugs_Loc.monthXaxis().get(0).getText();

		// validation
		if (size == 1 && monthExpected.equals(monthExpectedActual)) {

			logger.info("PASS :Cash flow date range, showing correct data");

		} else {

			Assert.fail("FAIL :Cash flow date range, showing incorrect");
			logger.info("FAIL :Cash flow date range, showing incorrect");

		}

	}

	/*
	 * PFM 3.0 - OK2Spend - If the user has not completed the FTUE flow, the
	 * 'Back to Dashboard' text is not there when the user clicks on 'See
	 * account trend'
	 */
	@Test(description = "AT-99749 :1187259", enabled = true, priority = 5)
	public void backToDashboard() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Bug flow
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.viewCashAccounts().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.seeAccountTrend().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.fFStartReview().click();
		SeleniumUtil.waitForPageToLoad();

		// Validation
		Assert.assertTrue(MRBugs_Loc.ffBacktoDashBoard().isDisplayed(),
				"FAIL : Back to DashBoard link is not displayed");
		logger.info("PASS :Back to DashBoard link is displayed");
	}

	/*
	 * PFM 3.0 - PFM 3.0 - Budget - Page is getting hanged in the mentioned
	 * scenario Internal Review :
	 */
	@Test(description = "AT-101505:1180918", enabled = true, priority = 6)
	public void editTransactionPageHanging() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Create Budget
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Data setup
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.transactionManualAdd().click();
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionDesc"));
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualTypeDropDown().click();
		String [] transType =MRBugs_Loc.transactionManualTypeValue();
		d.findElement(By.xpath(transType[1].replace("+", "Withdrawal"))).click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualDebitedFrom().click();
		MRBugs_Loc.transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualcategoryDropDown().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionManualcategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionWithDrawal"));
		MRBugs_Loc.transactioncategoryDropdownValue().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitUntilElementInvisible(d, "transactionEventAdd", "Transaction", null);
		// Bug flow
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.flexibleSpendingsATM().click();
		MRBugs_Loc.tranRowsinTran().click();
		MRBugs_Loc.transactionShowMoreOption().click();
		String path1 = System.getProperty("user.dir");

		String a = path1 + PropsUtil.getDataPropertyValue("attachmentPath");
		MRBugs_Loc.attachFile_TA().sendKeys(a);
		MRBugs_Loc.transactionSelectAttchment().click();
		MRBugs_Loc.transactionRemoveAttchment().click();
		MRBugs_Loc.transactionEditSave().get(0).click();
		// Validation
		Assert.assertTrue(MRBugs_Loc.transactionEditSave().size() == 0,
				"FAIL:Page is getting hanged in edit Transaction pop-up");
		logger.info("PASS :Page is not getting hanged in edit Transaction pop-up");
	}

	/*
	 * PFM 3.0 - Special characters are been accepted for high level categories
	 * in edit category page. Internal Review :
	 */

	@Test(description = "AT-101504:1174721,1205964", enabled = true, priority = 7)
	public void editCategorySpecialCharacters() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Bug flow
		PageParser.forceNavigate("Categories", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.toplevelCategoriesIncome().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.categoryConsulting().click();
		SeleniumUtil.waitForPageToLoad(3000);
		MRBugs_Loc.showAscategoryName().clear();
		SeleniumUtil.waitForPageToLoad(2000);
		
	}

	/*
	 * PFM 3.0 - Budget - Edit transaction pop up - There is no masking on the
	 * view attachment pop up. Internal Review :
	 */
	@Test(description = "AT-101500:1075927", enabled = true, priority = 8)
	public void editPopUpMasking() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Create Budget
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Data setup
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.transactionManualAdd().click();
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionDesc"));
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualTypeDropDown().click();
		String [] transType =MRBugs_Loc.transactionManualTypeValue();
		d.findElement(By.xpath(transType[1].replace("+", "Withdrawal"))).click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualDebitedFrom().click();
		MRBugs_Loc.transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualcategoryDropDown().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionManualcategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionWithDrawal"));
		MRBugs_Loc.transactioncategoryDropdownValue().click();
		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitUntilElementInvisible(d, "transactionEventAdd", "Transaction", null);

		// Bug flow
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.flexibleSpendingsATM().click();
		MRBugs_Loc.tranRowsinTran().click();
		MRBugs_Loc.transactionShowMoreOption().click();
		String path1 = System.getProperty("user.dir");
		String a = path1 + PropsUtil.getDataPropertyValue("attachmentPath");
		MRBugs_Loc.attachFile_TA().sendKeys(a);
		MRBugs_Loc.transactionSelectAttchment().click();
		SeleniumUtil.waitForPageToLoad(2000);
		String opacity = MRBugs_Loc.transactionPopUpMask().getCssValue(PropsUtil.getDataPropertyValue("opacity"));
		String zIndex = MRBugs_Loc.transactionPopUpMask().getCssValue(PropsUtil.getDataPropertyValue("zIndex"));
		// Validation
		if (opacity.equalsIgnoreCase(PropsUtil.getDataPropertyValue("opacityValue"))
				&& zIndex.equalsIgnoreCase(PropsUtil.getDataPropertyValue("zIndexValue"))) {

			logger.info("PASS :Masking is present for Edit transaction Pop-up");
		} else {

			logger.info("FAIL :No masking for Edit transaction Pop-up");
			Assert.fail("FAIL :No masking for Edit transaction Pop-up");
		}
	}

	/*
	 * On hover on error accounts in Credit card usage finapp wrong data is
	 * coming.
	 */
	@Test(description = "AT-101502:1171410", enabled = true, priority = 9)
	public void creditcardwgtHovrError() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		// Bug flow
		PageParser.forceNavigate("AccountSettings", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.editCreds().click();
		MRBugs_Loc.PasswordText1().click();
		MRBugs_Loc.PasswordText1().sendKeys(PropsUtil.getDataPropertyValue("errorPwd"));
		MRBugs_Loc.PasswordText1().sendKeys(Keys.TAB);
		MRBugs_Loc.ReEnterPasswordText1().sendKeys(PropsUtil.getDataPropertyValue("errorPwd"));
		MRBugs_Loc.UpdatedButton().click();
		SeleniumUtil.waitForElementVisible(d, "errorText", "AccountSettings", null);
		MRBugs_Loc.dagCloseEditCreds().click();
		PageParser.forceNavigate("DashboardPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Validation
		Assert.assertTrue(MRBugs_Loc.creditwidhoverTxt().isDisplayed(), "FAIL :Hover Text is not presnt or incorrect.");
		logger.info("PASS:Hover Text is not presnt or incorrect.");

	}

	/*
	 * UI issue while clicking on link account of credit card usage.
	 */
	@Test(description = "AT-101501:1171342", enabled = true, priority = 10)
	public void creditcardUsuageFl() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("billsDagUsername"),
				PropsUtil.getDataPropertyValue("billsDagPassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Bug flow
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.creditcardusuageFl().click();
		Assert.assertTrue(MRBugs_Loc.fastlinkFrame().isDisplayed(), "FAIL: Fastlink frame not loaded successfully");
		logger.info("PASS: Fastlink frame loaded successfully");
		// Validation
		Assert.assertTrue(MRBugs_Loc.fastlinkSearch().isDisplayed(), "FAIL: Fastlink search bar is not coming");
		logger.info("PASS: Fastlink search bar is not coming");
	}

	/*
	 * Fastlink Background color inconsistency.
	 */

	@Test(description = "AT-101503:1153923,1172199,1204357", enabled = true, priority = 11)
	public void flBackGroundColor() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("billsDagUsername"),
				PropsUtil.getDataPropertyValue("billsDagPassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Bug FLow
		SeleniumUtil.click(MRBugs_Loc.cashForecastFl());

		// getting the background color code
		String bckColor = MRBugs_Loc.flBackgrndcolor().getCssValue(PropsUtil.getDataPropertyValue("backGroundCOlor"));
		// Validation
		Assert.assertEquals(bckColor, PropsUtil.getDataPropertyValue("backgroundcolor"));
		logger.info("PASS: Background color is coming fine");

	}

	/*
	 * PFM3.0-Account groups header and cross icon is not visible with 100% zoom
	 */

	@Test(description = "AT-104359:1202564", enabled = true, priority = 12)
	public void createEditGroupWidthHeight() throws Exception {
		SoftAssert softAssertion = new SoftAssert();
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Bug FLow
		PageParser.forceNavigate("AccountGroups", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Bug Flow
		MRBugs_Loc.groupCreateGroup().click();

		String createGrpActual = MRBugs_Loc.createModalWindow().getCssValue(PropsUtil.getDataPropertyValue("width"));
		String accountSectionActual = MRBugs_Loc.accountSection()
				.getCssValue(PropsUtil.getDataPropertyValue("mxHeight"));
		// Create group Assertion
		softAssertion.assertEquals(createGrpActual, PropsUtil.getDataPropertyValue("widthValue"),
				"Create window Section width doest not match");
		softAssertion.assertEquals(accountSectionActual, PropsUtil.getDataPropertyValue("mxHeightValue"),
				"Create Account Section height doest not match");

		MRBugs_Loc.groupNameTxtBoxLoc().sendKeys(PropsUtil.getDataPropertyValue("group_Name"));
		MRBugs_Loc.addGroupFirstCheckBox().click();
		MRBugs_Loc.createOrUpdateGroupLoc().click();
		MRBugs_Loc.editExistingGroup().click();

		String editGrpActual = MRBugs_Loc.editModalWindow().getCssValue(PropsUtil.getDataPropertyValue("width"));
		String editAccountSectionActual = MRBugs_Loc.accountSection()
				.getCssValue(PropsUtil.getDataPropertyValue("mxHeight"));

		// Edit group assertion
		softAssertion.assertEquals(editGrpActual, PropsUtil.getDataPropertyValue("widthValue"),
				"Edit window Section width doest not match");
		softAssertion.assertEquals(editAccountSectionActual, PropsUtil.getDataPropertyValue("mxHeightValue"),
				"Edit Account Section height doest not match");

		// Final Validation
		softAssertion.assertAll();
		logger.info("PASS: Create/Edit group modal window and account section width and Height are expected");

	}

}
