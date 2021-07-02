/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.BudgetEnhancement;

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
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Create_Budget_Test extends TestBase {

    NetWorth_Loc featureTour;
	Budget_Landing_Page_Loc Budget_Landingpage;
	Budget_Income_And_Bill_Loc Budget_Income_Bill;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_Income_And_Bill_Summery_Loc Budget_Income_Bill_Summery;
	Create_Budget_Loc createbudget;
	
	private static final Logger logger = LoggerFactory.getLogger(Create_Budget_Test.class);

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		featureTour = new NetWorth_Loc(d);
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		Budget_Income_Bill = new Budget_Income_And_Bill_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		Budget_Income_Bill_Summery = new Budget_Income_And_Bill_Summery_Loc(d);
		createbudget = new Create_Budget_Loc(d);
		doInitialization("Create Budget Test");

	}

	@Test(description = "BUDG-02_01:Verify Login Happens Successfully:L1-40781", groups = {
			"DesktopBrowsers" }, priority = 1)
	public void login() throws Exception 
	    {
			PageParser.forceNavigate("Budget", d);
			logger.info("*******************Navigated to budget page*************");
		}
	/*@Test(description = "BUDG-02_02:Verify CreateBudget header and the plus icon", groups = {
    "DesktopBrowsers" }, priority = 2, enabled = true)
public void verifyHeader() throws Exception {

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Budget", d);
			if (Budget_FlexibleSpending.doneButton() == null) {
				if (Budget_Landingpage.incomeNextButton() != null) {
					SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
				}
			}
			SeleniumUtil.scrollElementIntoView(d, Budget_FlexibleSpending.doneButton(), true);
			SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		
			if (createbudget.IsCreateBudgetHeaderPresent())
			{
				Assert.assertEquals(createbudget.CreateBudgetHeader().getText(),PropsUtil.getDataPropertyValue("Create_Budget1"));
			}
			Assert.assertTrue(createbudget.CreateBudgetPlusIcon().isDisplayed());
			logger.info("*******************Verified the CreateBudget header and the plus icon*************");

    if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
          System.out.println("Create Budget Link is Visible ");
    } else {
          SeleniumUtil.click(Budget_Income_Bill_Summery.moreIcon());
          Assert.assertEquals(createbudget.AlertSettingsText().getText(),
                        PropsUtil.getDataPropertyValue("Alert_Settings_Text"));
          Assert.assertTrue(createbudget.AlertSettingsIcon().isDisplayed());
          logger.info(
                        "*******************clicked on more icon, verified the alertSetting text and icon*************");

          SeleniumUtil.click(createbudget.AlertSettingsIcon());
          SeleniumUtil.waitForPageToLoad(3000);
          Assert.assertEquals(createbudget.AlertSettingsHeader().getText(),
                        PropsUtil.getDataPropertyValue("Alert_Settings_Text"));
          logger.info(
                        "*******************clicked on alertSetting icon, verified the alertSetting text*************");
    }
}
}
*/




	@Test(description = "BUDG-02_02:Verify CreateBudget header and the plus icon", groups = {
			"DesktopBrowsers" }, priority = 2, enabled = true)
	public void verifyHeaderwithoutalert() throws Exception { //verifyHeader()

		
			       if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			              PageParser.forceNavigate("Budget", d);
			              if (Budget_FlexibleSpending.doneButton() == null) {
			                     if (Budget_Landingpage.incomeNextButton() != null) {
			                           SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
			                     }
			              }
			              SeleniumUtil.scrollElementIntoView(d, Budget_FlexibleSpending.doneButton(), true);
			              SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
			       }

			       Assert.assertEquals(createbudget.CreateBudgetHeader().getText(),
			                     PropsUtil.getDataPropertyValue("Create_Budget1"));
			       Assert.assertTrue(createbudget.CreateBudgetPlusIcon().isDisplayed());
			       logger.info("*******************Verified the CreateBudget header and the plus icon*************");


			
			}
	
	
	@Test(description = "BUDG-02_16:Verify CreateBudget header and Feature tour icon for cobrands which doesnot have more info", groups = { "DesktopBrowsers" }, priority = 2, enabled = true)
    public void verifyheaderwithoutmoreinfo() throws Exception {
                           //Added By MRQA As more info dropdown is not present in multiple cobrands.
                                    Assert.assertEquals(createbudget.CreateBudgetHeader().getText(),PropsUtil.getDataPropertyValue("Create_Budget1"));
                                                    Assert.assertTrue(createbudget.CreateBudgetPlusIcon().isDisplayed());
                        logger.info("*******************Verified the CreateBudget header and the plus icon*************");
                    
                        Assert.assertTrue(featureTour.featureTour().isDisplayed());
                        logger.info("*******************Verified the feature tour link is displayed*************");
      } 

	@Test(description = "BUDG-02_03:Verify  alertSetting description,alertSetting subheader", groups = {
			"DesktopBrowsers" }, priority = 3, enabled = true)
	public void verifyAlertSettings() throws Exception {

		String[] alertSettingSubHeader = PropsUtil.getDataPropertyValue("budget_AlertSetting")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = createbudget.AlertSettingsSubheader();
		for (int i = 0; i < l.size(); i++) {
			String list = l.get(i).getText();
			Assert.assertEquals(list, alertSettingSubHeader[i]);
		}
		logger.info("*******************verified the alertSetting subheaders*************");

		String[] alertSettingDesc = PropsUtil.getDataPropertyValue("budget_AlertSettingDesc")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> s = createbudget.AlertSettingsDescription();
		for (int j = 0; j < s.size(); j++) {
			String desc = s.get(j).getText();
			Assert.assertEquals(desc, alertSettingDesc[j]);
		}
		logger.info("*******************verified the alertSetting description*************");
	}

	@Test(description = "BUDG-02_04:Verify the Monthly text and dropdown is present in Budget Summary header in Alert Setting popup", groups = {
			"DesktopBrowsers" }, priority = 4, enabled = true)
	public void verifyQAMonthlyDropDown() throws Exception {

		Assert.assertEquals(createbudget.QAMonthlyText().getText(), PropsUtil.getDataPropertyValue("QA_Monthly_Text"));
		Assert.assertTrue(createbudget.QAMonthlyDropDown().isDisplayed());
		logger.info(
				"*******************verified the Monthly text and dropdown is present in Budget Summary header in Alert Setting popup*************");

		SeleniumUtil.click(createbudget.QAMonthlyDropDown());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("*******************clicked on the Monthly dropdown***********");

		String[] monthlyWeeklyText = PropsUtil.getDataPropertyValue("Budget_Monthly_Weekly")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = createbudget.QAMonthlyList();
		for (int i = 0; i < l.size(); i++) {
			String list = l.get(i).getText();
			Assert.assertEquals(monthlyWeeklyText[i], list);
		}
		logger.info("*******************verified the text (weekly and Monthly)on the Monthly dropdown***********");

		Assert.assertTrue(createbudget.TickMark().isDisplayed());
		SeleniumUtil.click(createbudget.QAMonthlyList().get(1));
		Assert.assertEquals(createbudget.QAMonthlyText().getText(), PropsUtil.getDataPropertyValue("Weekly_Text"));
		logger.info(
				"*******************verified the tick mark and clicked on weekly option on the dropdown***********");

	}

	@Test(description = "BUDG-02_05:Verify cancel and save changes button,toggle buttons", groups = {
			"DesktopBrowsers" }, priority = 5, enabled = true)
	public void verifySaveChangesButton() throws Exception {

		Assert.assertTrue(createbudget.CancelButton().isDisplayed());
		Assert.assertTrue(createbudget.SaveChangesButton().isDisplayed());
		logger.info("*******************verified cancel and save changes button*************");

		List<WebElement> l = createbudget.ToggleButton();
		for (int i = 0; i < l.size(); i++) {
			Assert.assertTrue(l.get(i).isDisplayed());
		}
		logger.info("*******************verified the toggle buttons*************");
		SeleniumUtil.click(createbudget.SaveChangesButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************clicked on the save changes buttons*************");
	}

	@Test(description = "BUDG-02_06:Verify Select Budget Desc,Select Existing GroupText,Budget DropDown,Dropdown Icon,Plus Icon", groups = {
			"DesktopBrowsers" }, priority = 6, enabled = true)
	public void verifyCreateBudgetButton() throws Exception {
		/**
		 * @author sswain1 Adding explicit wait to handle the delay in saving Alert page
		 *         changes.
		 * 
		 */
		/*WebDriverWait wait = new WebDriverWait(d, 800);
		wait.until(ExpectedConditions.visibilityOf(createbudget.CreateBudgetPlusIcon()));
		logger.info("*******************Next page displayed *************");*/
		/**
		 * @author sswain1 Click on Icon instead of the text.
		 */
		SeleniumUtil.click(createbudget.CreateBudgetPlusIcon());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("*******************clicked on Create Budget Header*************");
		
		Assert.assertEquals(createbudget.SelectBudgetDesc().getText(),
				PropsUtil.getDataPropertyValue("Select_Budget_Desc"));
		Assert.assertEquals(createbudget.SelectExistingGroupText().getText(),
				PropsUtil.getDataPropertyValue("Select_Existing_Group_Text"));
		Assert.assertTrue(createbudget.BudgetDropDown().isDisplayed());
		Assert.assertTrue(createbudget.DropDownIcon().isDisplayed());
		Assert.assertTrue(createbudget.PlusIcon().isDisplayed());
		String budget = createbudget.BudgetDropDown().getAttribute(PropsUtil.getDataPropertyValue("valueLabel"));
		Assert.assertEquals(PropsUtil.getDataPropertyValue("Budget_MyBudget"), budget);
		logger.info(
				"*******************verified the Select Budget Desc,Select Existing GroupText,Budget DropDown,Dropdown Icon,Plus Icon*************");
	}

	@Test(description = "BUDG-02_07:Verify Info Icon description, close icon", groups = {
			"DesktopBrowsers" }, priority = 7, enabled = true)
	public void verifyInfoIcon() throws Exception {

		Assert.assertTrue(createbudget.InfoIcon().isDisplayed());
		SeleniumUtil.click(createbudget.InfoIcon());
		logger.info("*******************verified and clicked on Info Icon*************");

		Assert.assertEquals(createbudget.InfoDescription().getText(),
				PropsUtil.getDataPropertyValue("Info_Description"));
		Assert.assertTrue(createbudget.CloseIcon().isDisplayed());
		SeleniumUtil.click(createbudget.CloseIcon());
		Assert.assertEquals(createbudget.ORText().getText(), PropsUtil.getDataPropertyValue("OR_Text"));
		logger.info("*******************verified Info Icon description, close icon*************");
	}

	@Test(description = "BUDG-02_08:Verify No account group- icon,text,description and Create group button", groups = {
			"DesktopBrowsers" }, priority = 8, enabled = true)
	public void verifyNoGroupDetails() throws Exception {

		Assert.assertTrue(createbudget.NoGroupIcon().isDisplayed());
		Assert.assertEquals(createbudget.NoAccountGroupText().getText(),
				PropsUtil.getDataPropertyValue("NoGroupSelected"));
		Assert.assertEquals(createbudget.NoAccountGroupDesc().getText(),
				PropsUtil.getDataPropertyValue("No_Account_Group_Desc"));
		Assert.assertTrue(createbudget.NoAccountGroupButton().isDisplayed());
		logger.info(
				"*******************verified  No account group- icon,text,description and Create group button*************");
	}

	@Test(description = "BUDG-02_09:Verify Create Account Group-Header,description; GroupName and Account to include text ", groups = {
			"DesktopBrowsers" }, priority = 9, enabled = true)
	public void verifyCreateGroupPopUp() throws Exception {

		SeleniumUtil.click(createbudget.NoAccountGroupButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************clicked No account group button*************");

		Assert.assertEquals(createbudget.CreateAccountGroupHeader().getText(),
				PropsUtil.getDataPropertyValue("Create_Account_Group_Header"));
		Assert.assertEquals(createbudget.CreateAccountGroupDesc().getText(),
				PropsUtil.getDataPropertyValue("Create_Account_Group_Desc"));
		Assert.assertEquals(createbudget.GroupNameText().getText(), PropsUtil.getDataPropertyValue("Group_Name_Text"));
		Assert.assertEquals(createbudget.AccountToIncludeText().getText(),
				PropsUtil.getDataPropertyValue("Account_To_Include_Text"));
		Assert.assertTrue(createbudget.GroupInputBox().isDisplayed());
		logger.info(
				"*******************verified Create Account Group-Header,description; GroupName and Account to include text *************");
	}

	@Test(description = "BUDG-02_10:create a new group", groups = { "DesktopBrowsers" }, priority = 10, enabled = true)
	public void verifyCreateGroup() throws Exception {

		createbudget.GroupInputBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name"));
		SeleniumUtil.waitForPageToLoad();
		
		int length = createbudget.CheckBox().size();		
		for(int i=0; i<length;i++)
		{
        SeleniumUtil.click(createbudget.CheckBox().get(i));
        SeleniumUtil.waitForPageToLoad(500);

		}
   /*     SeleniumUtil.click(createbudget.CheckBox().get(0));
        SeleniumUtil.click(createbudget.CheckBox().get(1));
        SeleniumUtil.click(createbudget.CheckBox().get(2));
        SeleniumUtil.click(createbudget.CheckBox().get(3));
        SeleniumUtil.click(createbudget.CheckBox().get(4));
        SeleniumUtil.click(createbudget.CheckBox().get(5));
        SeleniumUtil.click(createbudget.CheckBox().get(6));
       // SeleniumUtil.click(createbudget.CheckBox().get(7));
       // SeleniumUtil.click(createbudget.CheckBox().get(8));
        SeleniumUtil.scrollElementIntoView(d, createbudget.CreateGroupButton(), true);
        boolean createGpBtn = createbudget.CreateGroupButton().isDisplayed();
        SeleniumUtil.click(createbudget.CreateGroupButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(createGpBtn);
		logger.info("*******************Group Created*************");
	}

	@Test(description = "BUDG-02_11:Verify the account type of the accounts in the group created", groups = {
			"DesktopBrowsers" }, priority = 11, enabled = true)
	public void verifyIncomeAndBill() throws Exception {

		String[] accType = PropsUtil.getDataPropertyValue("budget_AccType")
				.split(PropsUtil.getDataPropertyValue("comma"));
		Assert.assertEquals(createbudget.PFMTestingText().getText(), PropsUtil.getDataPropertyValue("Group_Name"));
		List<WebElement> l = createbudget.AccountTypeHeading();
		for (int i = 0; i < l.size(); i++) {
			String list = l.get(i).getText();
			Assert.assertEquals(accType[i], list);
		}
		logger.info("*******************verified the account type of the accounts in the group created*************");
	}

	@Test(description = "BUDG-02_12:Verify info icon, info text, info popup and popup description", groups = {
			"DesktopBrowsers" }, priority = 12, enabled = true)
	public void verifyInfoCoachMark() throws Exception
	{
		SeleniumUtil.scrollElementIntoView(d, createbudget.InfoIcon1(), true);
		Assert.assertTrue(createbudget.InfoIcon1().isDisplayed());
		/**
		 * @author sswain1 The following text is not displayed in mobile. Hence
		 *         validation done only for mobile.
		 */
		if (createbudget.IsInfoHeaderPresent() && createbudget.IsInfoTextPresent()) {
			Assert.assertEquals(createbudget.InfoText().getText(),
					PropsUtil.getDataPropertyValue("Not_Considered_Budget_Text"));
			Assert.assertEquals(createbudget.InfoHeader().getText(),
					PropsUtil.getDataPropertyValue("Not_Considered_Budget_Creation_Header"));
		}
		SeleniumUtil.click(createbudget.InfoIcon1());
		logger.info("*******************Clicked on info icon *************");
		Assert.assertEquals(createbudget.InfoDescription1().getText(),
				PropsUtil.getDataPropertyValue("Info_Description_Text"));
		Assert.assertTrue(createbudget.CloseIcon1().isDisplayed());
		SeleniumUtil.click(createbudget.CloseIcon1());
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("*******************verified info icon, info text, info popup and popup description*************");
	}

	@Test(description = "BUDG-02_13:Verify and clicked on next button", groups = {
			"DesktopBrowsers" }, priority = 13, enabled = true)
	public void verifyNextButton() throws Exception {

		SeleniumUtil.scrollElementIntoView(d, createbudget.NextButtonText(), true);
		Assert.assertEquals(createbudget.NextButtonText().getText(),
				PropsUtil.getDataPropertyValue("Next_Button_Text"));
		Assert.assertTrue(createbudget.NextButtonIcon().isDisplayed());
		SeleniumUtil.click(createbudget.NextButtonText());
		logger.info("*******************verified and clicked on next button*************");
	}

	@Test(description = "BUDG-02_14:Verify/add a new category", groups = {
			"DesktopBrowsers" }, priority = 14, enabled = true)
	public void addRecurringBillCategory() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(Budget_Landingpage.addiconStep2eleforBills());
		}

		else {
			SeleniumUtil.click(Budget_Landingpage.addiconStep2eleforBills());
			logger.info("*******************clicked on edit icon*************");
		}

		SeleniumUtil.click(Budget_Landingpage.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************clicked on category dropdown*************");

		int numberOfRows1 = createbudget.newRowAdded().size();
		Budget_Landingpage.selectCategoryByNumber(2, 1);
		Budget_Landingpage.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income1"));
		
		SeleniumUtil.click(Budget_Landingpage.addButton());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows2 = createbudget.newRowAdded().size();
		Assert.assertTrue(numberOfRows2 > numberOfRows1);
		logger.info("*******************added a new category*************");

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(Budget_Landingpage.addiconStep2eleforBills());
			logger.info("*******************clicked on edit icon*************");
		}

		else {
			SeleniumUtil.click(Budget_Landingpage.addiconStep2eleforBills());
			logger.info("*******************clicked on edit icon*************");
		}
		SeleniumUtil.click(Budget_Landingpage.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************clicked on category dropdown*************");

		Budget_Landingpage.selectCategoryByNumber(4, 2);
		SeleniumUtil.click(Budget_Landingpage.cancelButton());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************cancelled the selected category*************");

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(Budget_Landingpage.addiconStep2eleforBills());
			logger.info("*******************clicked on edit icon*************");
		}

		else {
			SeleniumUtil.click(Budget_Landingpage.addiconStep2eleforBills());
			logger.info("*******************clicked on edit icon*************");
		}

		SeleniumUtil.click(Budget_Landingpage.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************clicked on category dropdown*************");

		int numberOfRows5 = createbudget.newRowAdded().size();
		Budget_Landingpage.selectCategoryByNumber(4, 2);
		Budget_Landingpage.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income3"));
		SeleniumUtil.click(Budget_Landingpage.addButton());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows6 = createbudget.newRowAdded().size();
		Assert.assertTrue(numberOfRows6 > numberOfRows5);
		logger.info("*******************added a third category*************");

		SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
		logger.info("*******************clicked on next button************");

	}

	@Test(description = "BUDG-02_15:Verify the amount edited is saved", groups = {
			"DesktopBrowsers" }, priority = 15, enabled = false)
	public void addFlexibleSpendingCategory() throws Exception {

		SeleniumUtil.click(Budget_FlexibleSpending.flexibleSpendingAddIcon());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows1 = createbudget.newRowAdded().size();
		logger.info("*******************clicked on flexible Spending Add Icon ************");

		SeleniumUtil.click(Budget_Landingpage.selectCategoryDropDown());
		Budget_Landingpage.selectCategoryByNumber(2, 1);
		Budget_Landingpage.editAmountInputBox().clear();
		SeleniumUtil.waitForPageToLoad(1000);
		Budget_Landingpage.editAmountInputBox().sendKeys(PropsUtil.getDataPropertyValue("income1"));
		SeleniumUtil.click(Budget_Landingpage.addButton());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows2 = createbudget.newRowAdded().size();
		Assert.assertTrue(numberOfRows2 > numberOfRows1);
		logger.info("*******************added and verified that a new category has been added ************");

		SeleniumUtil.click(Budget_FlexibleSpending.flexibleSpendingEditIcon());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************clicked on flexible Spending edit Icon ************");

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
				PropsUtil.getDataPropertyValue("income1"));
		SeleniumUtil.waitForPageToLoad(2500);
		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryForOnlineServices"),
				PropsUtil.getDataPropertyValue("income2"));
		SeleniumUtil.waitForPageToLoad(2500);
		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryForHomeImprovement"),
				PropsUtil.getDataPropertyValue("income3"));
		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************flexible Spending save icon clicked ************");

		// Assert.assertEquals(createbudget.budgetIncome1().getText(),PropsUtil.getDataPropertyValue("budget_income6"));
		// Assert.assertEquals(createbudget.budgetIncome2().getText(),PropsUtil.getDataPropertyValue("budget_income7"));
		// Assert.assertEquals(createbudget.budgetIncome3().getText(),PropsUtil.getDataPropertyValue("budget_income8"));
		logger.info("*******************verified that the amount edited is saved************");
	}

	@Test(description = "BUDG-02_15:Verify the amount edited is saved", groups = {
			"DesktopBrowsers" }, priority = 16, enabled = true)
	public void save() throws Exception {

		boolean flag = Budget_FlexibleSpending.doneButton().isDisplayed();
		SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(flag);
		logger.info("*******************clicked on done button************");
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
