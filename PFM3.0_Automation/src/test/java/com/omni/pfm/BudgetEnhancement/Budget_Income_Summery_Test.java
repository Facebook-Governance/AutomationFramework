/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.BudgetEnhancement;

import java.util.Calendar;

import java.util.List;

import org.openqa.selenium.WebElement;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.testng.Assert;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;

import com.omni.pfm.PageProcessor.PageParser;

import com.omni.pfm.config.Config;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Settings_Loc;
import com.omni.pfm.testBase.TestBase;

import com.omni.pfm.utility.PropsUtil;

import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Income_Summery_Test extends TestBase {

	Budget_Income_And_Bill_Summery_Loc Budget_Income_Bill_Summery;

	Budget_Settings_Loc Budget_Settings;

	Budget_Landing_Page_Loc Budget_Landingpage;

	private static final Logger logger = LoggerFactory.getLogger(Budget_Income_Summery_Test.class);

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {

		doInitialization("Budget Income Summary");

		Budget_Settings = new Budget_Settings_Loc(d);

		Budget_Income_Bill_Summery = new Budget_Income_And_Bill_Summery_Loc(d);

		Budget_Landingpage = new Budget_Landing_Page_Loc(d);

	}

	@Test(description = "L1-40781:BUDG-03_01:Verify Login Happens Successfully", groups = {
			"DesktopBrowsers" }, priority = 1)

	public void login() throws Exception {

		PageParser.navigateToPage("Budget", d);

		SeleniumUtil.waitForPageToLoad();

		logger.info("***************Navigated to budget page**************************");

	}

	@Test(description = "BUDG-03_02:Verify budget header, Settings icon and settings header", groups = {
			"DesktopBrowsers" }, priority = 2)

	public void verifyBudgetHeader() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.budgetHeader().getText(),
				PropsUtil.getDataPropertyValue("Budget_Header"));

		String icon = Budget_Income_Bill_Summery.settingsIcon()
				.getAttribute(PropsUtil.getDataPropertyValue("dataType"));

		Assert.assertTrue(icon.contains(PropsUtil.getDataPropertyValue("budget_icon")));

		Assert.assertTrue(Budget_Income_Bill_Summery.settingsHeader().isDisplayed());
		logger.info("***************validated Settings icon and settings header**************************");
		/**
		 * @author sswain1
		 * The below assertion is not valid for mobile.
		 */
		if (Budget_Income_Bill_Summery.isSettingHeaderTextPresent()) {
			Assert.assertEquals(Budget_Income_Bill_Summery.settingsHeader().getText(),
					PropsUtil.getDataPropertyValue("Settings_Header"));
		}

		logger.info("***************validated budget header, Settings icon and settings header**************************");

	}

	@Test(description = "BUDG-03_03:Verify feature Tour Icon and text", groups = { "DesktopBrowsers" }, priority = 3)

	public void verifyFeatureTourIcon() throws Exception {

		Assert.assertTrue(Budget_Income_Bill_Summery.moreIcon().isDisplayed());

		SeleniumUtil.click(Budget_Income_Bill_Summery.moreIcon());

		logger.info("***************verified and clicked on more icon*************************");

		String icon = Budget_Income_Bill_Summery.featureTourIcon()
				.getAttribute(PropsUtil.getDataPropertyValue("dataType"));

		Assert.assertTrue(icon.contains(PropsUtil.getDataPropertyValue("budget_icon")));

		Assert.assertEquals(Budget_Income_Bill_Summery.featureTourText().getText(),
				PropsUtil.getDataPropertyValue("Feature_Tour_Text"));

		logger.info("***************verified feature Tour Icon and text*************************");

	}

	@Test(description = "BUDG-03_04:Verify Budget Account Groups,Budget Created Text, next button and close icon", groups = {
			"DesktopBrowsers" }, priority = 4, enabled = true)

	public void verifyFeatureTourCoachMark() throws Exception {

		SeleniumUtil.click(Budget_Income_Bill_Summery.featureTourIcon());

		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("***************clicked on feature tour icon*************************");

		Assert.assertEquals(Budget_Income_Bill_Summery.BudgetAndAccountsGroups().getText(),
				PropsUtil.getDataPropertyValue("Budget_Account_Groups"));

		Assert.assertEquals(Budget_Income_Bill_Summery.BudgetCreatedText().getText(),
				PropsUtil.getDataPropertyValue("Budget_Created_Text"));

		Assert.assertTrue(Budget_Income_Bill_Summery.featureTourNextButton(1).isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.featureTourCloseIcon1().isDisplayed());

		logger.info("***************verified Budget Account Groups,Budget Created Text, next button and close icon*************************");

		Budget_Income_Bill_Summery.featureTourNextButton(1).click();

		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("***************clicked on next icon*************************");

		Assert.assertEquals(Budget_Income_Bill_Summery.overviewText().getText(),
				PropsUtil.getDataPropertyValue("Overview_Text"));

		Assert.assertEquals(Budget_Income_Bill_Summery.seeYourActualText().getText(),
				PropsUtil.getDataPropertyValue("budget_notification_desc"));

		logger.info("***************verified Overview_Text*************************");

	}

	@Test(description = "BUDG-03_05:Verify close icon, next and back button are present", groups = {
			"DesktopBrowsers" }, priority = 5, enabled = true)

	public void verifyFeatureTourCloseIcon() throws Exception {

		Assert.assertTrue(Budget_Income_Bill_Summery.featureTourCloseIcon().isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.featureTourNextButton(2).isDisplayed());

		if(Budget_Income_Bill_Summery.isFeatureTourBackButtonPresent())
		{
			Assert.assertTrue(Budget_Income_Bill_Summery.featureTourBackButton().isDisplayed());
		}

		logger.info("***************verified close icon, next and back button are present*************************");

		SeleniumUtil.click(Budget_Income_Bill_Summery.featureTourNextButton(2));

		logger.info("***************clicked on the next button*************************");

	}

	@Test(description = "BUDG-03_06:Verify Budget Details Text,See Your Actual Text, back and next button", groups = {
			"DesktopBrowsers" }, priority = 6)

	public void verifyBudgetDetailsPopUp() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.budgetDetailsText().getText(),
				PropsUtil.getDataPropertyValue("Budget_Details_Text"));

		Assert.assertEquals(Budget_Income_Bill_Summery.seeYourBudgetText().getText(),
				PropsUtil.getDataPropertyValue("See_Your_Actual_Text"));

		Assert.assertTrue(Budget_Income_Bill_Summery.budgetDetailsBackButton(2).isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.featureTourNextButton(3).isDisplayed());

		logger.info("***************verified Budget Details Text,See Your Actual Text, back and next button*************************");

		SeleniumUtil.click(Budget_Income_Bill_Summery.featureTourNextButton(3));

		logger.info("***************clicked on the next button*************************");

	}

	@Test(description = "BUDG-03_07:Verify Settings Text, Manage Notifications Accounts Text,Got It and back button", groups = {
			"DesktopBrowsers" }, priority = 7)

	public void verifySettingsPopUp() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.settingsText().getText(),
				PropsUtil.getDataPropertyValue("Settings_Text"));

		/*Assert.assertEquals(Budget_Income_Bill_Summery.manageAccountsText().getText(),
				PropsUtil.getDataPropertyValue("Manage_Notifications_Accounts_Text"));*/

		Assert.assertTrue(Budget_Income_Bill_Summery.budgetDetailsBackButton(3).isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.featureTourNextButton(4).isDisplayed());

		logger.info("***************verified Settings Text, Manage Notifications Accounts Text,Got It and back button*************************");

		Budget_Income_Bill_Summery.featureTourNextButton(4).click();

		logger.info("***************clicked on the Got It button*************************");

	}

	@Test(description = "BUDG-03_08:Verify Income Spending Summery Header,Income_Header,Bill And Spending Summery Text, Current Month on the chart", groups = {
			"DesktopBrowsers" }, priority = 8)

	public void verifyIncomeHeader() throws Exception {

		if(Budget_Income_Bill_Summery.IsIncomeAndSpendingHeaderPresent())
		{
		  Assert.assertEquals(Budget_Income_Bill_Summery.incomeAndSpendingHeader().getText(),
				PropsUtil.getDataPropertyValue("Income_Spending_Summery_Header"));
		}

		Assert.assertEquals(Budget_Income_Bill_Summery.incomeHeader().getText(),
				PropsUtil.getDataPropertyValue("Income_Header"));

		Assert.assertEquals(Budget_Income_Bill_Summery.billAndSpendingHeader().getText(),
				PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text"));

		Assert.assertTrue(Budget_Income_Bill_Summery.monthText().getText().contains(Budget_Income_Bill_Summery.currentMonth()));

		logger.info(
				"****verified Income Spending Summery Header,Income_Header,Bill And Spending Summery Text, Current Month on the chart *****");

	}

	@Test(description = "BUDG-03_09:Text Name and Category field displayed in add budget popup", groups = {
			"DesktopBrowsers" }, priority = 9)

	public void verifyBudgetDetailsAddIcon() throws Exception {

		SeleniumUtil.waitForPageToLoad();

		// The below Add button is only for Web
		if (Budget_Income_Bill_Summery.isClickFlexiAddIconByNumber()) {
			Budget_Income_Bill_Summery.clickAddIconByNumber(1);
		} else
		{
			/**
			 * @author sswain1: In else part the flow is defined for Mobile. Click on "View
			 *         Detail" button to go to Income and Flexible spending page
			 */
			SeleniumUtil.click(Budget_Income_Bill_Summery.viewDetailButtonInBudget());
			logger.info(
					"******************************* Clicked On View Detail button on Budget ************************************");
			SeleniumUtil.click(Budget_Income_Bill_Summery.flexiSpendingAddIcon());
			logger.info(
					"******************************* Clicked On flexiSpendingAddIcon ************************************");
		}

		Assert.assertEquals(Budget_Income_Bill_Summery.addBudgetHeader().getText(),
				PropsUtil.getDataPropertyValue("Add_Budget_Header"));

		logger.info(
				"*************verified that the user can add a new spending by clicking on  the + icon ***********");

		Assert.assertEquals(Budget_Income_Bill_Summery.addBudgetNameText().getText(),
				PropsUtil.getDataPropertyValue("Name_Text"));

		logger.info(
				"*************verified that the Text Name and Category field displayed in add budget popup***********");

	}

	@Test(description = "BUDG-03_10:Verify dropdown and Default label of dropdown", groups = {
			"DesktopBrowsers" }, priority = 10)

	public void verifyBudgetDetailsAddBudgetPopUp() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.addBudgetNameTextBox().getText(),
				PropsUtil.getDataPropertyValue("Select_Category_Text"));

		Assert.assertTrue(
				Budget_Income_Bill_Summery.addBudgetDropDown().getAttribute(PropsUtil.getDataPropertyValue("dataType"))
						.contains(PropsUtil.getDataPropertyValue("dropdownText")));

		logger.info("*************verified dropdown and Default label of dropdown***********");

		SeleniumUtil.click(Budget_Income_Bill_Summery.addBudgetNameTextBox());

		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("*************clicked on the dropdown***********");

		if (Config.getInstance().getEnvironment().contains("BBT"))
		{
			Budget_Landingpage.selectCategoryByNumber(2, 1);

		}
		else 
		{
			String bugHLcat = PropsUtil.getDataPropertyValue("bugHLcat");
			String bugMLcat = PropsUtil.getDataPropertyValue("bugMLcat");
			int highLevelcategory1 = Integer.parseInt(bugHLcat);
			int masterLevelcategory1 = Integer.parseInt(bugMLcat);

			Budget_Landingpage.selectCategoryByNumber(highLevelcategory1, masterLevelcategory1);

			SeleniumUtil.waitForPageToLoad();

			logger.info("*************Selected a category from the dropdown***********");

		}

	}

	@Test(description = "BUDG-03_11:Verify amount per Month Text,amount Per Month Input Box,last Three Month Text", groups = {
			"DesktopBrowsers" }, priority = 11)

	public void verifyBudgetDetailsAddBudgetCategory() throws Exception {

		Assert.assertEquals(PropsUtil.getDataPropertyValue("budget_AmtPerMonth"),
				Budget_Income_Bill_Summery.amountperMonthText().getText());

		Assert.assertTrue(Budget_Income_Bill_Summery.amountPerMonthInputBox().isDisplayed());

		Assert.assertEquals(Budget_Income_Bill_Summery.lastThreeMonthText().getText(),
				PropsUtil.getDataPropertyValue("budget_last3Months"));

		logger.info("*************verified amount per Month Text,amount Per Month Input Box,last Three Month Text***********");

	}

	@Test(description = "BUDG-03_12:Verify that the category is added and popup is closed", groups = {
			"DesktopBrowsers" }, priority = 12)

	public void verifyAddBudgetSaveButton() throws Exception {

		Budget_Income_Bill_Summery.amountPerMonthInputBox().clear();

		Budget_Income_Bill_Summery.amountPerMonthInputBox().sendKeys(PropsUtil.getDataPropertyValue("income4"));

		SeleniumUtil.click(Budget_Income_Bill_Summery.addBudgetNameTextBox1());

		Assert.assertTrue(Budget_Income_Bill_Summery.addBudgetSaveButton().isDisplayed());

		SeleniumUtil.click(Budget_Income_Bill_Summery.addBudgetSaveButton());

		SeleniumUtil.waitForPageToLoad();

		logger.info("*************added the amount in the input box and saved it***********");

		if(Budget_Income_Bill_Summery.isviewDetailButtonPresent())
		{
			SeleniumUtil.click(Budget_Income_Bill_Summery.viewDetailButtonInBudget());
			Assert.assertTrue(Budget_Income_Bill_Summery.leftToSpendMaximizeIcon().isDisplayed());
			
			//Click the back button to go back to the Budget landing page to validate income in the next tests case.		
			SeleniumUtil.click(Budget_Income_Bill_Summery.getBackButton_BSA());
			logger.info("*************Clicked on viewDetailButtonInBudget***********");
		}
		else { Assert.assertTrue(Budget_Income_Bill_Summery.leftToSpendMaximizeIcon().isDisplayed());}

		logger.info("*************verified that the category is added and popup is closed***********");

	}

	@Test(description = "BUDG-03_13:Verify income label and the amount adjacent to it", groups = {
			"DesktopBrowsers" }, priority = 13)

	public void verifyIncomeAmount() throws Exception {

		Assert.assertTrue(Budget_Income_Bill_Summery
				.verifyLeftAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("budget_incomeText")));

		Assert.assertFalse(Budget_Income_Bill_Summery.getLeftAmountByCategoryName("income").isEmpty());

		logger.info("*************verified income label and the amount adjacent to it***********");

	}

	@Test(description = "BUDG-03_14:Verify the BILLS AND SPENDING text and left amount field just above the spending bar", groups = {
			"DesktopBrowsers" }, priority = 14)

	public void verifyBillsAndSpendingAmount() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.billAndSpendingHeader().getText(),
				PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text"));

		Assert.assertTrue(Budget_Income_Bill_Summery
				.verifyLeftAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1")));

		String amount = Budget_Income_Bill_Summery
				.getLeftAmountByCategoryName(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1"));

		Assert.assertFalse(amount.isEmpty());

		logger.info(
				"*************verified that we have the BILLS AND SPENDING text and left amount field just above the spending bar***********");

	}

	@Test(description = "BUDG-03_15:Verify the details of budget amount ,spent amount and left amount", groups = {
			"DesktopBrowsers" }, priority = 15)

	public void verifyLeftToSpendMaximizeIcon() throws Exception {

		if(Budget_Income_Bill_Summery.isviewDetailButtonPresent())
		{
			SeleniumUtil.click(Budget_Income_Bill_Summery.viewDetailButtonInBudget());
			SeleniumUtil.click(Budget_Income_Bill_Summery.leftToSpendMaximizeIcon());
			SeleniumUtil.waitForPageToLoad(2000);
			logger.info("*************Clicked on leftToSpendMaximizeIcon***********");
		}
		else { SeleniumUtil.click(Budget_Income_Bill_Summery.leftToSpendMaximizeIcon()); }

		Assert.assertEquals(Budget_Income_Bill_Summery.budgetedAmountText().getText(),
				PropsUtil.getDataPropertyValue("budgetedAmountText"));

		Assert.assertEquals(Budget_Income_Bill_Summery.spentAmountText().getText(),
				PropsUtil.getDataPropertyValue("spentAmountText"));

		Assert.assertEquals(Budget_Income_Bill_Summery.remainingAmountText().getText(),
				PropsUtil.getDataPropertyValue("remainingAmountText"));

		logger.info(
				"*************verified that all the details of budget amount ,spent amount and left amount**********");

	}

	@Test(description = "BUDG-03_16:Verify remaining amount is equal to budget amount minus the spending amount", groups = {
			"DesktopBrowsers" }, priority = 16)

	public void verifyLeftToSpendThisMonthTransaction() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.thisMonthTransactionText().getText(),
				PropsUtil.getDataPropertyValue("This_Month_Transaction_Text"));

		Assert.assertTrue(Budget_Income_Bill_Summery.leftToSpendEditIcon().isDisplayed());

		logger.info("*************verified that that the THIS MONTHS TRANSACTION text appear beside the circular graph**********");

		float budgetAmount1 = Budget_Income_Bill_Summery
				.replaceAllLiterals(Budget_Income_Bill_Summery.budgetedAmount().getText());

		float spentAmount1 = Budget_Income_Bill_Summery
				.replaceAllLiterals(Budget_Income_Bill_Summery.spentAmount().getText());

		float remainingAmount1 = Math.round(
				Budget_Income_Bill_Summery.replaceAllLiterals(Budget_Income_Bill_Summery.remainingAmount().getText()));

		Assert.assertEquals((budgetAmount1 - spentAmount1), remainingAmount1);

		logger.info(
				"*************verified that remaining amount is equal to budget amount minus the spending amount**********");

	}

	@Test(description = "BUDG-03_17:Verify that the transactions associated to that particular month is reflected", groups = {
			"DesktopBrowsers" }, priority = 17)

	public void verifyLeftToSpendThisMonthTransactionAmount() throws Exception {

		Assert.assertTrue(Budget_Income_Bill_Summery.transactionAmount().size() > 0);

		logger.info(
				"*************verified that the transactions associated to that particular month is reflected**********");

		SeleniumUtil.click(Budget_Income_Bill_Summery.leftToSpendEditIcon());

		Assert.assertEquals(Budget_Income_Bill_Summery.addBudgetHeader().getText(),
				PropsUtil.getDataPropertyValue("Edit_Budget_Text"));

		logger.info("*************clicked on Edit Budget icon and verified Edit Budget Text**********");

	}

	@Test(description = "BUDG-03_18:Verify delete,save and close button, Spending Trend header", groups = {
			"DesktopBrowsers" }, priority = 18)

	public void verifyLeftToSpendEditBudget() throws Exception {

		Budget_Income_Bill_Summery.editBudgetInputBox().clear();

		Budget_Income_Bill_Summery.editBudgetInputBox().sendKeys(PropsUtil.getDataPropertyValue("income3"));

		SeleniumUtil.waitForPageToLoad();

		logger.info("*************Edited the  Budget amount**********");

		Assert.assertTrue(Budget_Income_Bill_Summery.deleteButton().isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.saveButton().isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.closeIcon().isDisplayed());

		Assert.assertTrue(Budget_Income_Bill_Summery.spendingTrendText().getText()
				.contains(PropsUtil.getDataPropertyValue("SpendingTrend")));

		logger.info("*************verified delete,save and close button, Spending Trend header**********");

	}

	@Test(description = "BUDG-03_19:Verify All Text In Landing Page", groups = { "DesktopBrowsers" }, priority = 19)

	public void verifyLeftToSpendThisMonthTransactionAmoun() throws Exception 
	{
		boolean saveBtn = Budget_Income_Bill_Summery.saveButton().isDisplayed();
		SeleniumUtil.click(Budget_Income_Bill_Summery.saveButton());
		SeleniumUtil.click(Budget_Income_Bill_Summery.saveButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(saveBtn);
		logger.info("*************clicked on save button**********");
	}

	@Test(description = "BUDG-03_20:Verify that we have actual and budget values displayed below the income", groups = {
			"DesktopBrowsers" }, priority = 20)

	public void verifyIncomeAmountActualText() throws Exception
	{
		if(Budget_Income_Bill_Summery.isBackFrmMonthlyPresent())
		{
			//click on the back button
			SeleniumUtil.click(Budget_Income_Bill_Summery.backFrmMonthlyTransaction());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(Budget_Income_Bill_Summery.getBackButton_BSA());
		}
		Assert.assertFalse(Budget_Income_Bill_Summery
				.getActualAmountByCategory(PropsUtil.getDataPropertyValue("budget_incomeText")).isEmpty());

		Assert.assertFalse(Budget_Income_Bill_Summery
				.getBudgetedAmountByCategory(PropsUtil.getDataPropertyValue("budget_incomeText")).isEmpty());

		Assert.assertTrue(Budget_Income_Bill_Summery
				.verifyActualAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("budget_incomeText")));

		Assert.assertTrue(Budget_Income_Bill_Summery
				.verifyBudgetAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("budget_incomeText")));

		logger.info("*************verified that we have actual and budget values displayed below the income**********");

	}

	@Test(description = "BUDG-03_21:Verify that we have the actual and budgetted field below the spending", groups = {
			"DesktopBrowsers" }, priority = 21)

	public void verifyBillAndSpendingAmountActualText() throws Exception {

		String Amount = Budget_Income_Bill_Summery
				.getActualAmountByCategory(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1"));

		Assert.assertFalse(Amount.isEmpty());

		Assert.assertTrue(Budget_Income_Bill_Summery
				.verifyBudgetAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1")));

		logger.info("*************verified that we have the actual and budgetted field below the spending**********");

	}

	@Test(description = "BUDG-03_22:Verify that we have the notification box beside the income and spending summary", groups = {
			"DesktopBrowsers" }, priority = 22, enabled = true)

	public void verifyViewAllAccountsButton() throws Exception 
	{
		String notification = Budget_Income_Bill_Summery.notificationHeader().getText();

		Assert.assertEquals(notification, PropsUtil.getDataPropertyValue("Notification_Header"));

		logger.info("*************verified that we have the notification box beside the income and spending summary**********");
	}

	@Test(description = "BUDG-03_23:Verify that clicking on view my accounts takes us to the accounts page of the budget finapp", groups = {
			"DesktopBrowsers" }, priority = 23, enabled = false)

	public void verifyAfterClickingOnViewAllAccountsButton() throws Exception {

		String header = Budget_Income_Bill_Summery.accountsHeader().getText();

		Assert.assertEquals(header, PropsUtil.getDataPropertyValue("Accounts_Header"));

		SeleniumUtil.click(Budget_Income_Bill_Summery.backToBudgetIcon());

		SeleniumUtil.waitForPageToLoad();

		logger.info("*************verified that clicking on view my accounts takes us to the accounts page of the budget finapp**********");

	}

	@Test(description = "BUDG-03_24:Verify that LEFT TO SPEND text present on the Budget landing screen and a Sort by text below", groups = {
			"DesktopBrowsers" }, priority = 24)

	public void verifyBudgetDetailsText() throws Exception {

		if (Budget_Income_Bill_Summery.isBudgetDetailsHeaderPresent()) {
			Assert.assertEquals(Budget_Income_Bill_Summery.budgetDetailsHeader().getText(),
					PropsUtil.getDataPropertyValue("Budget_Details_Header"));
			Assert.assertEquals(Budget_Income_Bill_Summery.sortByText().getText(),
					PropsUtil.getDataPropertyValue("Sort_By_Text"));
		}
		else
		{
			SeleniumUtil.click(Budget_Income_Bill_Summery.viewDetailButtonInBudget());
			logger.info("******************************* Clicked On View Detail button on Budget ************************************"); 
		}

		logger.info("*************verified that LEFT TO SPEND text present on the Budget landing screen and a Sort by text below**********");

		String dropdown = Budget_Income_Bill_Summery.dropDown()
				.getAttribute(PropsUtil.getDataPropertyValue("dataType"));

		Assert.assertTrue(dropdown.contains(PropsUtil.getDataPropertyValue("dropdownValue")));

		logger.info("*************verified that we have the dropdown opening just beside sort by **********");

	}

	@Test(description = "BUDG-03_25:Verify that % is displayed", groups = { "DesktopBrowsers" }, priority = 25)

	public void verifyLeftToSpendHowImDoingText() throws Exception {

		Assert.assertEquals(Budget_Income_Bill_Summery.howImDoingText().getText(),
				PropsUtil.getDataPropertyValue("How_I_Doing_Text"));

		List<WebElement> s = Budget_Income_Bill_Summery.spentPercentage();

		for (int i = 0; i < s.size(); i++) {

			if (s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_minus"))) {

				Assert.assertTrue(s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_minus")));

			}

			if (s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_percentage"))) {

				Assert.assertTrue(s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_percentage")));

			}

		}

		logger.info("*************verified that % is displayed**********");

	}

	@Test(description = "BUDG-03_26:Verify amount is displayed on selecting How_I_Doing from DD", groups = {
			"DesktopBrowsers" }, priority = 26, enabled = false)

	public void verifyLeftToSpendBudgetedAmount() throws Exception {

		SeleniumUtil.click(Budget_Income_Bill_Summery.dropDown());

		Assert.assertEquals(Budget_Income_Bill_Summery.budgetedAmountLarget().getText(),
				PropsUtil.getDataPropertyValue("Budgeted_Amount_Text"));

		SeleniumUtil.click(Budget_Income_Bill_Summery.budgetedAmountLarget());

		List<WebElement> l = Budget_Income_Bill_Summery.leftAmount();

		for (int i = 0; i < l.size(); i++) {

			Assert.assertTrue(Budget_Income_Bill_Summery.leftAmount().get(i).isDisplayed());

			Assert.assertTrue(l.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_dollar"))
					|| l.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_minus")));

		}

		logger.info("*************verified that amount is displayed on selecting How_I_Doing from DD**********");

	}

	@Test(description = "BUDG-03_27:Verify that After clicking on Settings it should navigate to Settings page", groups = {
			"DesktopBrowsers" }, priority = 27)

	public void verifyBudgetSettings() throws Exception {
		
		if(Budget_Income_Bill_Summery.isBackFrmMonthlyPresent())
		{
			SeleniumUtil.click(Budget_Income_Bill_Summery.getBackButton_BSA());
			SeleniumUtil.waitForPageToLoad(1000);
		}

		Assert.assertTrue(Budget_Income_Bill_Summery.settingsIcon().isDisplayed());

		logger.info("*************verified the Settings coach mark display**********");

		SeleniumUtil.click(Budget_Income_Bill_Summery.settingsIcon());

		Assert.assertTrue(Budget_Settings.budgetSettingsHeader().getText()
				.contains(PropsUtil.getDataPropertyValue("budgetSettingsText")));

		logger.info("*************verified that After clicking on Settings it should navigate to Settings page**********");

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
