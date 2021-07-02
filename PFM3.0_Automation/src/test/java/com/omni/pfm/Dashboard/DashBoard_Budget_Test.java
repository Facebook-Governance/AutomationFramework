/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.BudgetEnhancement.*;
import com.omni.pfm.pages.Dashboard.*;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_Budget_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(DashBoard_Budget_Test.class);
	Account_Loc account;
	Budget_Loc budget;
	Budget_Income_And_Bill_Loc Budget_Income_Bill;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_Landing_Page_Loc Budget_Landingpage;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {

		doInitialization("Dashboard-Budget");
		account = new Account_Loc(d);
		budget = new Budget_Loc(d);
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		Budget_Income_Bill = new Budget_Income_And_Bill_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@Test(description = "Verify login happenes successfully.", priority = 1, groups = { "Desktop",
			"Smoke" }, enabled = true)
	public void login() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("****************Navidgated to Dashboard************");

	}

	@Test(description = "AT-76956,AT-76966:Verify Budget Header,Welcome To Budget Text,Budget Description Text", priority = 2, groups = {
			"Desktop" }, enabled = true)
	public void verifyHeader() throws Exception {
		Assert.assertEquals(budget.BudgetHeader().getText(), PropsUtil.getDataPropertyValue("Budget_Header"));
		Assert.assertEquals(budget.WelcomeToBudgetHeader().getText(),
				PropsUtil.getDataPropertyValue("Welcome_To_Budget_Text"));
		Assert.assertEquals(budget.BudgetHelpsYouText().getText(),
				PropsUtil.getDataPropertyValue("Budget_Description_Text"));
		logger.info(
				"****************Verified Budget Header,Welcome To Budget Text,Budget Description Text************");
	}

	@Test(description = "AT-76967:Verify user is navigated to budget page", priority = 3, groups = {
			"Desktop" }, enabled = true)
	public void verifyGoToFullViewButton() throws Exception {
		Assert.assertTrue(budget.GoToFullViewButton().isDisplayed());
		Assert.assertEquals(budget.GoToFullViewButton().getText(), PropsUtil.getDataPropertyValue("Create_Budget"));
		SeleniumUtil.click(budget.GoToFullViewButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("****************Verified and clicked on Create budget button************");

		Assert.assertTrue(budget.Budgetheader().isDisplayed());
		logger.info("****************Verified user is navigated to budget page************");

	}

	// Updated By MRQA for deeplinking cobrands where there is no header displayed.

	@Test(description = "AT-76967:Verify user is navigated to budget page", priority = 3, groups = {
			"Desktop" }, enabled = true)
	public void verifyGoToFullViewButtondeeplink() throws Exception {
		Assert.assertTrue(budget.GoToFullViewButton().isDisplayed());
		Assert.assertEquals(budget.GoToFullViewButton().getText(), PropsUtil.getDataPropertyValue("Create_Budget"));
		SeleniumUtil.click(budget.GoToFullViewButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("****************Verified and clicked on Create budget button************");
	}

	@Test(description = "AT-76966:Verify and click on get started button", priority = 4, groups = {
			"Desktop" }, enabled = true)
	public void verifyBudget() throws Exception {
		Assert.assertTrue(Budget_Landingpage.getStartedButton().isDisplayed());
		SeleniumUtil.click(Budget_Landingpage.getStartedButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		logger.info("****************Verified and clicked on get started button************");

		SeleniumUtil.click(Budget_Landingpage.nextButton1());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		logger.info("****************clicked on next button************");
	}

	@Test(description = "Verify the categories", priority = 5, groups = { "Desktop" }, enabled = true)
	public void addBudgetCategories() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertTrue(Budget_Landingpage.myIncomeEditIconmob().isDisplayed());
			SeleniumUtil.click(Budget_Landingpage.myIncomeEditIconmob());
		} else {
			Assert.assertTrue(Budget_Landingpage.myIncomeEditIcon1().isDisplayed());
			SeleniumUtil.click(Budget_Landingpage.myIncomeEditIcon1());
		}
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("****************clicked on myIncomeEditIcon************");

		budget.incomeInputBox().clear();
		budget.incomeInputBox().sendKeys(PropsUtil.getDataPropertyValue("amountForCategoryService"));
		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("****************edited and saved the category************");

		SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
		SeleniumUtil.waitForPageToLoad(400);
		logger.info("****************clicked on next button************");
		Assert.assertTrue(Budget_FlexibleSpending.flexibleSpendingEditIcon().isDisplayed());
	}

	@Test(description = "edit the flexibleSpending categories", priority = 6, groups = { "Desktop" }, enabled = true)
	public void createBudget2() throws Exception {

		SeleniumUtil.click(Budget_FlexibleSpending.flexibleSpendingEditIcon());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.waitForPageToLoad();
		/*
		 * Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue(
		 * "categoryAutomotive"), PropsUtil.getDataPropertyValue("income2"));
		 * SeleniumUtil.waitForPageToLoad();
		 * Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue(
		 * "categoryEntertainment"), PropsUtil.getDataPropertyValue("income3"));
		 * SeleniumUtil.waitForPageToLoad();
		 * Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue(
		 * "categoryAtm"), PropsUtil.getDataPropertyValue("income3"));
		 */
		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("****************edited the flexibleSpending categories************");
		Assert.assertEquals(budget.SpendText().getText(), PropsUtil.getDataPropertyValue("Spent_Text"));
	}

	@Test(description = "AT-76957,AT-76958,AT-76960:Verify Month_To_Date_Text,Spent_Text ,Budgeted_Tex", priority = 7, groups = {
			"Desktop", "Smoke" }, enabled = true)
	public void verifyAfterCreatingBudget() throws Exception {

		Assert.assertEquals(budget.BudgetedText().getText(), PropsUtil.getDataPropertyValue("Budgeted_Text"));
		Assert.assertTrue(budget.YouHaveLeftToSpendText().isDisplayed());
		logger.info("****************validated Month_To_Date_Text,Spent_Text ,Budgeted_Text************");
	}

	@Test(description = "Verify SmileIcon,SpendAmount ,BudgetedAmount", priority = 8, groups = { "Desktop",
			"Smoke" }, enabled = true)
	public void verifySpendAndBudgetedAmount() throws Exception {

		Assert.assertTrue(budget.SmileIcon().isDisplayed());
		Assert.assertTrue(budget.SpendAmount().isDisplayed());
		Assert.assertTrue(budget.BudgetedAmount().isDisplayed());
		logger.info("****************validated SmileIcon,SpendAmount ,BudgetedAmount************");

	}

	@Test(description = "AT-76956:Verify Budget_Widget_Header.", priority = 9, groups = { "Desktop",
			"Smoke" }, enabled = true)
	public void verifyAfterClickingOnMonthToDateText() throws Exception {

		SeleniumUtil.click(budget.BudgetMainCard());
		SeleniumUtil.waitForPageToLoad();
		/*
		 * WebDriverWait wait = new WebDriverWait(d, 20); WebElement we =
		 * wait.until(ExpectedConditions.visibilityOf(budget.Budgetheader()));
		 * Assert.assertTrue(we.isDisplayed());
		 */
		Assert.assertEquals(budget.Budgetheader().getText(), PropsUtil.getDataPropertyValue("Budget_Header"));
		logger.info("****************validated Budget_Widget_Header************");
	}

	@Test(description = "Verify Income_Bill_Summery_text", priority = 10, groups = { "Desktop",
			"Smoke" }, enabled = true)
	public void verifyBudgetBillSummery() throws Exception {

		Assert.assertEquals(budget.IncomeAndBillSummeryText().getText(),
				PropsUtil.getDataPropertyValue("Income_Bill_Summery_text"));
		logger.info("****************validated Income_Bill_Summery_text************");
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
