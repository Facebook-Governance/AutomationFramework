/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.BudgetEnhancement;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class CreateNewBudget_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(CreateNewBudget_Test.class);
	Budget_Landing_Page_Loc Budget_Landingpage;
	Budget_Select_Accounts_Loc select_accounts;
	Budget_Income_And_Bill_Loc Budget_Income_Bill;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_No_Account_Loc no_accounts;
	AccountAddition accAddition = new AccountAddition();

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {

		doInitialization("Budget-Test class 2");
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		Budget_Income_Bill = new Budget_Income_And_Bill_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		select_accounts = new Budget_Select_Accounts_Loc(d);
		no_accounts = new Budget_No_Account_Loc(d);
	}

	@Test(description = "BUDG-04_01:Verify Login happens Successfully:L1-40781", groups = {
			"DesktopBrowsers" }, priority = 0)
	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "BUDG-04_02:Add account and Navigate to Budget Page", groups = {
			"DesktopBrowsers" }, priority = 1)
	public void navigateToBudget() throws Exception {

		logger.info("Adding Dag Site");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount("spDagSite.site16441.2", "site16441.2"));
		logger.info("*************************Dag Site Added Successfully************");

		PageParser.forceNavigate("Budget", d);
		logger.info("*************************Navigated to Budget Page************");
	}

	@Test(description = "BUDG-04_03:Create a Budget", groups = { "DesktopBrowsers" }, priority = 2)
	public void createbudget() {
		SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(select_accounts.NextButton2());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(Budget_Landingpage.myIncomeEditIconmob());
		} else {
			SeleniumUtil.click(Budget_Landingpage.editincome());
		}
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

			Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryForEntertainment1"),
					PropsUtil.getDataPropertyValue("amountForCategoryEntaertainment"));
			SeleniumUtil.waitForPageToLoad();
			Budget_Income_Bill.CategoryValues().sendKeys(Keys.TAB);
			logger.info("*************************Edited all the Category Values-amounts*************************");

		}

		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			WebDriverWait wait = new WebDriverWait(d, 40);
			WebElement ele = wait.until(ExpectedConditions.visibilityOf(Budget_FlexibleSpending.doneButtonmob()));
			SeleniumUtil.click(ele);
			SeleniumUtil.waitForPageToLoad(5000);
		} else {

			WebDriverWait wait = new WebDriverWait(d, 40);
			WebElement ele = wait.until(ExpectedConditions.visibilityOf(Budget_FlexibleSpending.doneButton()));
			System.out.println(ele);
		}
		logger.info("*************************Clicked on income Save Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("*************************Clicked on Done Button*************************");

		Assert.assertTrue(Budget_FlexibleSpending.createdBudgetheader().isDisplayed());
		logger.info("*************************My Budget Header Verified*************************");
	}

	@Test(description = "BUDG-04_03:Verify that the Cancel button should display in all the coach marks", groups = {
			"DesktopBrowsers" }, priority = 3)
	public void verifyCanelButton() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		logger.info("Click on more button and then on the feature tour button and then close button");
		SeleniumUtil.click(Budget_FlexibleSpending.moreBtn());
		SeleniumUtil.click(Budget_FlexibleSpending.featureTourIcon());
		Assert.assertTrue(Budget_FlexibleSpending.closeIcon(1).isDisplayed());
		SeleniumUtil.click(Budget_FlexibleSpending.closeIcon(1));

		logger.info("Click on more button and then on the feature tour button and then close button");
		SeleniumUtil.click(Budget_FlexibleSpending.moreBtn());
		SeleniumUtil.click(Budget_FlexibleSpending.featureTourIcon());
		SeleniumUtil.click(Budget_FlexibleSpending.BudgetSetUpDone1());
		Assert.assertTrue(Budget_FlexibleSpending.closeIcon(2).isDisplayed());
		SeleniumUtil.click(Budget_FlexibleSpending.closeIcon(2));

		logger.info("Click on more button and then on the feature tour button and then close button");
		SeleniumUtil.click(Budget_FlexibleSpending.moreBtn());
		SeleniumUtil.click(Budget_FlexibleSpending.featureTourIcon());
		SeleniumUtil.click(Budget_FlexibleSpending.BudgetSetUpDone1());
		SeleniumUtil.click(Budget_FlexibleSpending.BudgetSetUpDone1());
		Assert.assertTrue(Budget_FlexibleSpending.closeIcon(3).isDisplayed());
		SeleniumUtil.click(Budget_FlexibleSpending.closeIcon(3));

		logger.info("Click on more button and then on the feature tour button and then close button");
		SeleniumUtil.click(Budget_FlexibleSpending.moreBtn());
		SeleniumUtil.click(Budget_FlexibleSpending.featureTourIcon());
		SeleniumUtil.click(Budget_FlexibleSpending.BudgetSetUpDone1());
		SeleniumUtil.click(Budget_FlexibleSpending.BudgetSetUpDone1());
		SeleniumUtil.click(Budget_FlexibleSpending.BudgetSetUpDone1());
		Assert.assertTrue(Budget_FlexibleSpending.closeIcon(4).isDisplayed());
		SeleniumUtil.click(Budget_FlexibleSpending.closeIcon(4));

	}

	@Test(description = "BUDG-04_04:verify that user is able to add bills/budgets by clicking on the + icon.", groups = {
			"DesktopBrowsers" }, priority = 4)
	public void verifyAddBills() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(Budget_FlexibleSpending.butgtviewdetailsbtn());
		}

		logger.info("Click on add Flexible Spending button and then add the bill");
		SeleniumUtil.click(Budget_FlexibleSpending.addFlexibleSpending());
		SeleniumUtil.click(Budget_FlexibleSpending.addCategory());
		SeleniumUtil.click(Budget_FlexibleSpending.addGiftsCategory());
		SeleniumUtil.waitForPageToLoad(8000);
		if (Budget_FlexibleSpending.iscatgrCloseMobile()) {
			SeleniumUtil.click(Budget_FlexibleSpending.catgrCloseMobile());
		}

		Budget_FlexibleSpending.addAmount().sendKeys(PropsUtil.getDataPropertyValue("amountForAtmCategory"));
		SeleniumUtil.waitForPageToLoad();
		/* SeleniumUtil.click(Budget_FlexibleSpending.addBudgetPopUp_Save()); */
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Budget_FlexibleSpending.addBudgetPopUp_Save_mob().click();
			SeleniumUtil.click(Budget_FlexibleSpending.butgtviewdetailsbtn());
			SeleniumUtil.click(Budget_FlexibleSpending.budgetshowmorespending());
		} else {

			Budget_FlexibleSpending.addBudgetPopUp_Save().click();
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(Budget_FlexibleSpending.verifyGifts().isDisplayed());

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
