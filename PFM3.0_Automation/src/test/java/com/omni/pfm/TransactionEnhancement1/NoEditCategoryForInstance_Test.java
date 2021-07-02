/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Ashwin PM
*/
package com.omni.pfm.TransactionEnhancement1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.NoEditCategoryForInstance_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FinAppUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class NoEditCategoryForInstance_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(NoEditCategoryForInstance_Test.class);
	AccountAddition accAddition;
	NoEditCategoryForInstance_Loc NoEditCatLoc;
	FinAppUtil finApp;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("No Edit Category for Instance of a Transaction");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accAddition = new AccountAddition();
		NoEditCatLoc = new NoEditCategoryForInstance_Loc(d);
		finApp = new FinAppUtil(d);
	}

	@Test(description = "FIOV_01_001  Verify login happens succesfully.", priority = 0)
	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("Add Dag Site");
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount("swin.site16441.2", "site16441.2"));
		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount("edittxn1.site16441.2", "site16441.2"));
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-84953 : Verify the category field is not editable when user is trying to edit the particular instance of a manual tranasction series in projected section", priority = 1, dependsOnMethods = {
			"Login" }, enabled = true)
	public void verifyCatFieldNotEditableForInstanceManualTrans() {
		String scheduledDate = finApp.getFutureDate(0);
		String endDate = finApp.getFutureDate(30);
		if (NoEditCatLoc.isAddNewTransactionButtonMobile()) {
			SeleniumUtil.click(NoEditCatLoc.mobileMoreButton());
			SeleniumUtil.click(NoEditCatLoc.addNewTransactionButtonMobile());
		} else {
			SeleniumUtil.click(NoEditCatLoc.addNewTransactionButton());
		}
		SeleniumUtil.waitForPageToLoad();
		NoEditCatLoc.addMannualTransaction("25000.50", "AmazonPrime Sub", "Withdrawal", "IQ sWin Savings", "Weekly",
				scheduledDate, endDate, "Entertainment");
		SeleniumUtil.waitForPageToLoad();
		if (NoEditCatLoc.isAddNewTransactionButtonMobile()) {
			SeleniumUtil.click(NoEditCatLoc.mobileMoreButton());
			SeleniumUtil.click(NoEditCatLoc.addNewTransactionButtonMobile());
		} else {
			SeleniumUtil.click(NoEditCatLoc.addNewTransactionButton());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		NoEditCatLoc.addMannualTransaction("27000", "Rent Collection", "Deposit", "IQ sWin Savings", "Weekly",
				scheduledDate, endDate, "Deposits");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(NoEditCatLoc.loadMoreTransactionBtn());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(1);
		NoEditCatLoc.clickOnProjectedTransaction("AmazonPrime Sub");
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
	}

	@Test(description = "AT-84954 : Verify the category field  is not editable when user is trying to edit the particular instance of a PE generated series in projected section.", dependsOnMethods = {
			"Login" }, priority = 2)
	public void verifyCatFieldNotEditableForInstancePredictedTrans() {
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
		SeleniumUtil.click(NoEditCatLoc.projectedTransactionsTab());
		NoEditCatLoc.clickOnProjectedTransaction("Netflix"); // failing here due to xpath issue
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
	}

	@Test(description = "AT-84956 : Verify the category field is not editable in posted section if user has added manual tranasction series starting from the past date.", dependsOnMethods = {
			"Login" }, priority = 3, enabled = true)
	public void verifyCatFieldNotEditableForInstancePostedTrans() {
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
		NoEditCatLoc.clickOnPostedTransaction("AmazonPrime Sub"); // failing here due to xpath issue
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
	}

	@Test(description = "AT-84957 : Verify the application should allow user to edit the category field after changing the option *Just this transaction* to *All the series in this event* in the details section for the manual series transactions.", dependsOnMethods = {
			"Login" }, priority = 4)
	public void verifyCatFieldIsEditableForSeriesManualTrans() {
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
		SeleniumUtil.click(NoEditCatLoc.projectedTransactionsTab());
		NoEditCatLoc.clickOnProjectedTransaction("AmazonPrime Sub"); // failing here due to xpath issue
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(NoEditCatLoc.allTransInSeriesChkBox());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(NoEditCatLoc.categoryDropDown().isDisplayed(), "**Category dropdown is not displayed!");
	}

	@Test(description = "AT-84958 : Verify the application should allow user to edit the category field after changing the option \"Just this transaction\" to \"All the series in this event\" in the details section for the PE generated transactions.", dependsOnMethods = {
			"Login" }, priority = 5)
	public void verifyCatFieldIsEditableForSeriesPredictedTrans() {
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		NoEditCatLoc.clickOnProjectedTransaction("Netflix"); // failing here due to xpath issue
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(NoEditCatLoc.allTransInSeriesChkBox());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(NoEditCatLoc.categoryDropDown().isDisplayed(), "**Category dropdown is not displayed!");
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
	}

	@Test(description = "AT-84959 : Verify the Category field is not editable In the expense analysis finapp if user has added manual tranasction series starting from the past date.", dependsOnMethods = {
			"Login" }, priority = 6)
	public void verifyCatFieldNotEditableInExpAnal_ManualTrans() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.click(NoEditCatLoc.expCoachMarkContinueBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(NoEditCatLoc.expCoachMarkGoToAnalysisBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(NoEditCatLoc.expLegends().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		NoEditCatLoc.clickOnHighLevelCategory("Personal & Family");
		SeleniumUtil.waitForPageToLoad(2000);
		NoEditCatLoc.clickOnHighLevelCategory("Entertainment");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			NoEditCatLoc.clickOnHighLevelCategory("Entertainment");
			NoEditCatLoc.clickOnPostedTransactionmobile("AmazonPrime Sub");
			SeleniumUtil.waitForPageToLoad(3000);
		} else {
			NoEditCatLoc.clickOnPostedTransaction("AmazonPrime Sub"); // failing here due to xpath issue
			SeleniumUtil.waitForPageToLoad(3000);
		}
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
	}

	@Test(description = "AT-84960 : Verify the Category field is not editable In the income analysis finapp if user has added manual tranasction series starting from the past date.", dependsOnMethods = {
			"Login" }, priority = 7)
	public void verifyCatFieldNotEditableInIncAnal_ManualTrans() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.click(NoEditCatLoc.expAnalIncAnalNavigationBtn());
		SeleniumUtil.click(NoEditCatLoc.incomeAnalysisOptionInNav());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(NoEditCatLoc.expLegends().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		NoEditCatLoc.clickOnHighLevelCategory("Income");
		SeleniumUtil.waitForPageToLoad();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			NoEditCatLoc.clickOnHighLevelCategory("Deposits");
			NoEditCatLoc.clickOnPostedTransactionmobile("Rent Collection");
			SeleniumUtil.waitForPageToLoad(3000);
		} else {
			NoEditCatLoc.clickOnPostedTransaction("Rent Collection");// failing here due to xpath issue
			SeleniumUtil.waitForPageToLoad(3000);
		}
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
	}

	@Test(description = "AT-84961 : Verify the Category field is not editable In the budget finapp if user has added manual tranasction series starting from the past date.", dependsOnMethods = {
			"Login" }, priority = 8, enabled=false)
	public void verifyCatFieldNotEditableInBudget_ManualTrans() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.click(NoEditCatLoc.budgetGetStartedBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		if (NoEditCatLoc.isnext1budgetmobile()) {
			SeleniumUtil.click(NoEditCatLoc.next1budgetmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.next2budgetmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.next2budgetmobile());
			SeleniumUtil.waitForPageToLoad(2000);
		} else {
			SeleniumUtil.click(NoEditCatLoc.budgetStep1NextBtn());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.budgetStep2NextBtn());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.budgetStep3DoneBtn());
			SeleniumUtil.waitForPageToLoad();
		}
		if (NoEditCatLoc.isViewMoreDetailsMobile()) {
			SeleniumUtil.click(NoEditCatLoc.viewMoreDetailsMobile());
			NoEditCatLoc.clickOnFlexibleSpending("Entertainment");
			SeleniumUtil.waitForPageToLoad();
			NoEditCatLoc.clickOnPostedTransactionmobile("AmazonPrime Sub");
			SeleniumUtil.waitForPageToLoad(3000);
		} else {
			NoEditCatLoc.clickOnFlexibleSpending("Entertainment");
			SeleniumUtil.waitForPageToLoad();
			NoEditCatLoc.clickOnPostedTransaction("AmazonPrime Sub");
			SeleniumUtil.waitForPageToLoad();
		}
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "AT-84966 : Verify the category field  is not editable when user is trying to edit the particular instance of a   manual tranasction/PE generated events in the trends section of SFC finapp.", dependsOnMethods = {
			"Login" }, priority = 9)
	public void verifyCatFieldNotEditableInSFC_ManualTrans() {
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.click(NoEditCatLoc.sfcStartReviewBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(NoEditCatLoc.sfcStep1AccountToSelect());
		SeleniumUtil.waitForPageToLoad(2000);
		if (NoEditCatLoc.issfcNextBtnmobile()) {
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
		} else {
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(NoEditCatLoc.sfcAccountsDropDown());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NoEditCatLoc.sfcAccountToBeSelected());
		SeleniumUtil.click(NoEditCatLoc.sfcAppyButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		/*
		 * SeleniumUtil.click(NoEditCatLoc.sfcAppyButton());
		 * SeleniumUtil.waitForPageToLoad();
		 * SeleniumUtil.click(NoEditCatLoc.sfcAppyButton());
		 * SeleniumUtil.waitForPageToLoad(3000);
		 */
		NoEditCatLoc.clickOnProjectedTransaction("AmazonPrime Sub");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
	}

	@Test(description = "AT-84967 : Verify the category field  is not editable when user is trying to edit the particular instance of a  expense manual tranasction/PE generated events in the EVENTS section of SFC finapp.", dependsOnMethods = {
			"Login" }, priority = 10)
	public void verifyCatFieldNotEditableInSFC_Events_ManualTrans() {
		if (NoEditCatLoc.issfcEventsTabMobile()) {
			SeleniumUtil.click(NoEditCatLoc.sfcEventsTabMobile());
			SeleniumUtil.waitForPageToLoad();
		} else {
			SeleniumUtil.click(NoEditCatLoc.sfcEventsTab());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		SeleniumUtil.click(NoEditCatLoc.sfcEventToBeVerified());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed(), "**Category text is not displayed!");
		SeleniumUtil.click(NoEditCatLoc.transactionDetailsCancelBtn());
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "AT-84962,AT-84963 : Verify the category field is not editable in the edit screen for the missed/overdue events in the fincheck indicator 2(Pay bills on time).", dependsOnMethods = {
			"Login" }, priority = 11,enabled=false)
	public void verifyCatFieldNotEditableInFinCheck_MissedBills() {
		/*
		 * FINCHECK is removed FB.navigateToFinCheck();
		 * FB.completeFTUExperience("30000", "2", "Always plan ahead");
		 * FB.navigateToFinCheck();
		 * 
		 * SeleniumUtil.click(NoEditCatLoc.finCheckBillsIndicator());
		 * SeleniumUtil.waitForPageToLoad();
		 * 
		 * SeleniumUtil.click(NoEditCatLoc.finCheckMissedBill());
		 * SeleniumUtil.waitForPageToLoad();
		 * 
		 * Assert.assertTrue(NoEditCatLoc.categoryText().isDisplayed()
		 * ,"**Category text is not displayed!");
		 */
	}
}