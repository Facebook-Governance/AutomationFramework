/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.TransactionEnhancement1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Onboarding_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Editscreen_Savechanges_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.NoEditCategoryForInstance_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Expanse_Income_Analysis_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class Editscreen_Savechanges_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Editscreen_Savechanges_Test.class);
	FincheckV2_GetStarted_Loc FB;
	FincheckV2_Onboarding_Loc ficftue1;
	AccountAddition accAddition;
	Transaction_Expanse_Income_Analysis_Loc expanse;
	NoEditCategoryForInstance_Loc NoEditCatLoc;
	Editscreen_Savechanges_Loc EditsaveChanges;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		accAddition = new AccountAddition();
		EditsaveChanges = new Editscreen_Savechanges_Loc(d);
		expanse = new Transaction_Expanse_Income_Analysis_Loc(d);
		NoEditCatLoc = new NoEditCategoryForInstance_Loc(d);
		FB = new FincheckV2_GetStarted_Loc(d);
		ficftue1 = new FincheckV2_Onboarding_Loc(d);
		doInitialization("Transaction : Edit screen save changes");
		TestBase.tc = TestBase.extent.startTest("Transaction : Edit screen save changes",
				"Transaction : Edit screen save changes");
		TestBase.test.appendChild(TestBase.tc);
	}

	@Test(description = "FIOV_01_001  Verify login happens succesfully.", priority = 0)
	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("Add Dag Site");
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount("edittxn1.site16441.2", "site16441.2"));
		SeleniumUtil.refresh(d);
	}

	@Test(description = "AT-84943 : Verify the Save changes button enabled by default in transaction details screen", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 1, enabled = true)
	public void verifySavechangesButtonEnabledbyDefault() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(EditsaveChanges.transactionRow().get(0));
		Assert.assertTrue(EditsaveChanges.saveChangesButton().isEnabled());
	}

	@Test(description = "AT-84944 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen (Posted transactions)", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 2, enabled = true)
	public void verifyToastMessagenotdisplayeforPostedtxn() {
		SeleniumUtil.click(EditsaveChanges.saveChangesButton());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertTrue(EditsaveChanges.transactionToastMessage() != null, "Toast message is not displayed"); // toast
	}

	@Test(description = "AT-84945 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen (projected transactions)", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 3, enabled = true)
	public void verifyToastMessagenotDisplayeforProjectedtxn() {
		EditsaveChanges.projectedTransaction();
		SeleniumUtil.waitForPageToLoad(200);
	}

	@Test(description = "AT-84946 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen Expense nalysis finapp", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 5, enabled = true)
	public void verifyToastMessageNotDisplayedinExpenseFinapp() {
		PageParser.forceNavigate("Expense", d);
		EditsaveChanges.expenseSavechangesvalidation();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(EditsaveChanges.expenseTransactionrow());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(EditsaveChanges.saveChangesButton());
		Assert.assertTrue(EditsaveChanges.transactionToastMessage() != null, "Toast message is not displayed");
	}

	@Test(description = "AT-84947 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen income analysis finapp", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 6, enabled = true)
	public void verifyToastMessageNotdisplayedinIncomeFinapp() {
		if (EditsaveChanges.iscloseicon()) {
			SeleniumUtil.click(EditsaveChanges.closeicon());
			SeleniumUtil.waitForPageToLoad(1000);
			SeleniumUtil.click(EditsaveChanges.backbuttonmobile());
		}
		EditsaveChanges.incomeSavechangesvalidation(1, 0);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(EditsaveChanges.expenseTransactionrow());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(EditsaveChanges.saveChangesButton());
		Assert.assertTrue(EditsaveChanges.transactionToastMessage() != null, "Toast message is not displayed"); // message
	}

	@Test(description = "AT-84950 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen Fincheck  finapp", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 7, enabled = false)
	public void verifyToastMessageNotDisplayedinFincheckFinapp() {
		PageParser.forceNavigate("FincheckV2", d);
		FB.quickOnboarding("1997", "50000", 700, 1);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(EditsaveChanges.fincheckIndicator2());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(EditsaveChanges.fincheckIndicator2missedevent());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(EditsaveChanges.fincheckIndicator2missedevensavechanges());
		Assert.assertTrue(EditsaveChanges.transactionToastMessage() != null, "Toast message is not displayed");
	}

	@Test(description = "AT-84949 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen SFC  finapp", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 8, enabled = true)
	public void verifyToastMessagenotDisplayedinSfcfinapp() {
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(NoEditCatLoc.sfcStartReviewBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		if (NoEditCatLoc.issfcNextBtnmobile()) {
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtnmobile());
			SeleniumUtil.waitForPageToLoad(2000);
		} else {
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(NoEditCatLoc.sfcNextBtn());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(EditsaveChanges.transactionToastMessage() != null, "Toast message is not displayed");
	}

	@Test(description = "AT-84948 : Verify toast message \"Transaction updated\" should not display if user click on the Save changes button without changing the editable fields in details screen budget finapp", groups = {
			"Desktop" }, dependsOnMethods = { "Login" }, priority = 9, enabled = false)
	public void verifyToastMessagenotDisplayedinBudgetfinapp() {
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
		EditsaveChanges.budgetEditscreenValidation();
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertTrue(EditsaveChanges.transactionToastMessage() != null, "Toast message is not displayed");
	}
}