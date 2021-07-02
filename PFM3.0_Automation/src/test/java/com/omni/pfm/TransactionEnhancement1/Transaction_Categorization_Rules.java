/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Cashflow_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Expanse_Income_Analysis_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Categorization_Rules extends TestBase {
	Add_Manual_Transaction_Loc manual;
	public Transaction_Expanse_Income_Analysis_Loc expanseIncome;
	public Transaction_Cashflow_Integration_Loc CashFlow;
	public Aggregated_Transaction_Details_Loc aggregated;
	Transaction_Categorization_Rules_Loc rule;
	AddToCalendar_Transaction_Loc cal;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Categorization_Rules.class);
	public static String userName = "";
	AccountAddition accountAdd;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Add Manual Transaction");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		manual = new Add_Manual_Transaction_Loc(d);
		CashFlow = new Transaction_Cashflow_Integration_Loc(d);
		expanseIncome = new Transaction_Expanse_Income_Analysis_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		cal = new AddToCalendar_Transaction_Loc(d);
		aggregated = new Aggregated_Transaction_Details_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		accountAdd = new AccountAddition();
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68865,AT-68866:verify uncetorization transaction", priority = 2)
	public void catgorizationToTransaction() throws Exception {
		// AT-31684::Verify when user has navigated to Transaction FinApp after clicking
		// on uncategorized , there should be a clickable button as Back to
		// Categorization Rules
		manual.clickMTLink();
		manual.createTransaction(PropsUtil.getDataPropertyValue("Categorization_MT_Amount1"),
				PropsUtil.getDataPropertyValue("Categorization_MT_Description1"),
				PropsUtil.getDataPropertyValue("Categorization_MT_AccountNew1"), 0,
				PropsUtil.getDataPropertyValue("Categorization_MT_Category1"),
				PropsUtil.getDataPropertyValue("Categorization_MT_Frequency1"));
		PageParser.forceNavigate("CategorzationRules", d);
		rule.clickUncatTxn();
		Assert.assertTrue(rule.baccat().isDisplayed());
		Assert.assertEquals(rule.baccat().getText(), PropsUtil.getDataPropertyValue("ruleBacktoRule"));
	}

	@Test(description = "AT-68867:verify back to categorization rule click", priority = 3, dependsOnMethods = "catgorizationToTransaction")
	public void backToCat() throws Exception {
		rule.clickbacCat();
		Assert.assertEquals(rule.catrule().getText(), PropsUtil.getDataPropertyValue("RuleCatRuleHeader"));
	}

	@Test(description = "AT-68868,AT-68869:verify navigate manage category", priority = 4)
	public void manageCat() throws Exception {
		// Verify when user has navigated to "Categories" after clicking on "Manage
		// Categories" , there should be a back button as "Back to Transactions"
		PageParser.forceNavigate("Transaction", d);
		rule.manageCat();
		Assert.assertEquals(rule.baccat().getText(), PropsUtil.getDataPropertyValue("RuleBackTransaction"));
	}

	@Test(description = "AT-68870:verify back to transaction click", priority = 5, dependsOnMethods = "manageCat")
	public void backToTxn() throws Exception {
		rule.clickbacCat();
		Assert.assertEquals(rule.catrule().getText(), PropsUtil.getDataPropertyValue("MangeCat_TransactionHeader"));
	}

	@Test(description = "AT-68871:edit master elevl and add sub categroy", priority = 6, dependsOnMethods = "backToTxn")
	public void editCat() throws Exception {
		// Verify when user clicks on "Back to Transactions" , it should take to
		// Transactions FinApp
		rule.manageCat();
		rule.editCat(PropsUtil.getDataPropertyValue("RuleCatChange"), PropsUtil.getDataPropertyValue("RuleSubCAt"),
				PropsUtil.getDataPropertyValue("Caretories_HLCName1"),
				PropsUtil.getDataPropertyValue("Caretories_MLCName1"));
		rule.clickbacCat();
		Assert.assertEquals(rule.getAllcat().get(0).getText(), PropsUtil.getDataPropertyValue("RuleCatChange"));
	}

	@Test(description = "AT-68872:verify sub category", priority = 7, dependsOnMethods = "editCat")
	public void subCatEdit() throws Exception {
		rule.clickPostedTxn(0);
		aggregated.catgoryFieldClick();
		aggregated.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("RuleCatChange"));
		Assert.assertTrue(rule.transactionDetailsSubCategoryList(15, 3).get(0).isDisplayed());
	}

	@Test(description = "AT-68873:verify deleted sub category", priority = 8, dependsOnMethods = "subCatEdit")
	public void deletesubCatEdit() throws Exception {
		rule.manageCat();
		rule.detetesubcat(PropsUtil.getDataPropertyValue("Caretories_HLCName1"),
				PropsUtil.getDataPropertyValue("RuleCatChange"));
		PageParser.forceNavigate("Transaction", d);
		rule.clickPostedTxn(0);
		aggregated.catgoryFieldClick();
		aggregated.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("RuleCatChange"));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(rule.transactionDetailsSubCategoryList(15, 3).size() == 0);
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {
		}
	}
}
