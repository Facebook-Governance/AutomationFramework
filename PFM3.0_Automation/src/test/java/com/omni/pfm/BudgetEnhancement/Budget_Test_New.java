/*******************************************************************************

 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author sbhat1
 ******************************************************************************/
package com.omni.pfm.BudgetEnhancement;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.BudgetRefactor_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Enhancement_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Test_New extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(Budget_Test_New.class);

	AccountAddition accAddition = new AccountAddition();
	NetWorth_Loc featureTour;
	Budget_Enhancement_Loc budget;

	@BeforeClass(alwaysRun = true)
	public void inti() throws Exception {
		doInitialization("Budget");
		featureTour = new NetWorth_Loc(d);
		budget = new Budget_Enhancement_Loc(d);

	}

	@Test(description = "AT-69420:Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void login() throws Exception {
		logger.info(">>>>>Login to PFM 3.0");
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Navigating to Budget.");
		PageParser.navigateToPage("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-69443,AT-69444:Verify Budget Header when none of the accounts are added, Add account after verifying the header", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void addAccountAndVerifyHeader() throws Exception {

		logger.info(">>>>> Verifying the link account button is displayed");
		Assert.assertTrue(budget.LinkAccountButton_BNA().isDisplayed());

		logger.info(">>>>> Adding a dag site to PFM 3.0..");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword")));

		logger.info(">>>>> Navigating to Budget..");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-69431,AT-69432,AT-69433:Verify All Text In Landing Page", dependsOnMethods = { "login",
			"addAccountAndVerifyHeader" }, groups = { "DesktopBrowsers" }, priority = 3, enabled = true)
	public void verifyLandingBudgetPage() throws Exception {
		logger.info(">>>>> Verifying Create Budget header..");
		Assert.assertEquals(budget.BudgetHeader().getText(), "Create a Budget");

		logger.info(">>>>> Verifying the description..");
		Assert.assertTrue(budget.Description().getText().contains(PropsUtil.getDataPropertyValue("BudgetWelcomeScreenDescription")));

		logger.info(">>>>> Verifying the STEP 1, STEP 2, STEP 3 headers.");
		String[] List = PropsUtil.getDataPropertyValue("Steps_Header").split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = budget.Steps();
		for (int i = 0; i < l.size(); i++) {
			String list = l.get(i).getText();
			Assert.assertEquals(list, List[i]);
		}
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-69435:Verify the steps icon,get started button and footer text", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"addAccountAndVerifyHeader" }, priority = 4, enabled = true)
	public void verifyGetStartedButton() throws Exception {
		logger.info(">>>>> Verifying the steps icon..");
		List<WebElement> l = budget.StepsIcon();
		for (int i = 0; i < l.size(); i++) {
			Assert.assertTrue(budget.StepsIcon().get(i).isDisplayed());
		}
		
		logger.info(">>>>> Verifying Get Started Button and Footer Text");
		Assert.assertTrue(budget.GetStartedButton().isDisplayed());
		Assert.assertEquals(budget.FooterText().getText(), PropsUtil.getDataPropertyValue("FooterText"));
		

	}

	@Test(description = "AT-69446:Verify the footer logo and footer text", groups = { "DesktopBrowsers" }, dependsOnMethods = {
			"login", "addAccountAndVerifyHeader" }, priority = 5, enabled = true)
	public void verifyFooter() throws Exception {
		logger.info(">>>>> Verifying footer logo, footer text and tab headers..");
		String[] List = PropsUtil.getDataPropertyValue("Footer_Text_List")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> footerTextList = budget.FooterTextList();
		for (int i = 0; i < footerTextList.size(); i++) {
			String footerText = footerTextList.get(i).getText();
			Assert.assertEquals(footerText, List[i]);
		}
		Assert.assertTrue(budget.FooterLogo().isDisplayed());
	}

	@Test(description = "AT-69436,AT-69437,AT-69439:Verify the description which appears below the tab", dependsOnMethods = { "login",
			"addAccountAndVerifyHeader" }, groups = { "DesktopBrowsers" }, priority = 6, enabled = true)
	public void verifyTabHeaders() throws Exception {
		logger.info(">>>>> Clicking on Get Started Button..");
		SeleniumUtil.click(budget.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Verifying tab headers.");
		Assert.assertEquals(PropsUtil.getDataPropertyValue("Tab_List"), budget.ThreeTabs().getText());

		logger.info(">>>>> Verifying tab description..");
		Assert.assertEquals(budget.Description_BSA().getText(),
				PropsUtil.getDataPropertyValue("Select_Accounts_Description"));
	}

	@Test(description = "AT-69440:Verify the account type(savings,cards etc)", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyTabHeaders" }, priority = 7, enabled = true)
	public void verifySelectAccountsHeader() throws Exception {
		logger.info(">>>>> Verifying select accounts header..");
		Assert.assertEquals(budget.SelectAccountsHeader().getText(),
				PropsUtil.getDataPropertyValue("Select_Accounts_Header"));

		logger.info(">>>>> Verifying the account types (savings, cards etc) ..");
		String[] List = PropsUtil.getDataPropertyValue("Account_Type__Sub_Header")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> accTypeSubHeader = budget.AccTypeSubHeader();
		for (int i = 0; i < accTypeSubHeader.size() - 1; i++) {
			String accType = accTypeSubHeader.get(i).getText();
			Assert.assertEquals(accType, List[i]);
		}
	}

	@Test(description = "Verify All the account details(Type andacc number)", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyTabHeaders" }, priority = 8, enabled = true)
	public void verifySiteName() throws Exception {
		logger.info(">>>>> Verifying added account..");
		String[] AccDet = PropsUtil.getDataPropertyValue("Aggregated_Accounts")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> siteName = budget.SiteName();
		for (int i = 1; i < siteName.size() - 1; i++) {
			String list = siteName.get(i).getText();
			Assert.assertEquals(list, AccDet[i]);
		}

		logger.info(">>>>> Verifying the account details (Type and acc number) ..");
		String[] AccName = PropsUtil.getDataPropertyValue("Account_Name")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> accName = budget.AccountName();
		for (int i = 0; i < accName.size(); i++) {
			String accountName = accName.get(i).getText();
			Assert.assertEquals(accountName, AccName[i]);
		}
	}

	@Test(description = "Verify check boxes:", dependsOnMethods = { "login", "verifyTabHeaders" }, groups = {
			"DesktopBrowsers" }, priority = 9, enabled = true)
	public void verifyCheckBoxAccountSelection() throws Exception {
		List<WebElement> checkBoxes = budget.CheckBox_BSA();
		for (int i = 0; i < checkBoxes.size(); i++) {
			logger.info(">>>>> Verifying checkbox "+i);
			Assert.assertTrue(budget.CheckBox_BSA().get(i).isDisplayed());
		}

		logger.info(">>>>> Clicking on last checkbox");
		SeleniumUtil.click(budget.CheckBox_BSA().get(checkBoxes.size() - 1));
	}

	@Test(description = "AT-69447,AT-69457:Verify next button and back button", groups = { "DesktopBrowsers" }, dependsOnMethods = {
			"login", "verifyTabHeaders" }, priority = 10, enabled = true)
	public void verifyNextAndBackButton() throws Exception {
		logger.info(">>>>> Clicking on Next button..");
		SeleniumUtil.click(budget.NextButton1());
		SeleniumUtil.waitForPageToLoad();
		
		logger.info(">>>>> Verifying next and back button in the FTUE Step -2");		
		Assert.assertTrue(budget.NextButton().isDisplayed());
		Assert.assertTrue(budget.BackButton().isDisplayed());
		
	}

	@Test(description = "AT-69447,AT-69448:Verify Recurring bills text,INCOME text,create a budget header,based on data text", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyNextAndBackButton" }, priority = 11, enabled = true)
	public void verifyCreateBudgetHeader() throws Exception {
		logger.info(">>>>> Verifying Create Budget header..");
		Assert.assertEquals(budget.CreateBudgetHeader().getText(),
				PropsUtil.getDataPropertyValue("Create_Budget_Header"));
		

		logger.info(">>>>> Verifying 'Income' text..");
		String myIncomeText = budget.myIncomeText().getText();
		Assert.assertEquals(myIncomeText, PropsUtil.getDataPropertyValue("My_Income_Text"));
		
		logger.info(">>>>> Verifying 'Recurring Bills' Text..");
		String recurringbilltxt = budget.myRecurringBillText().getText();
		Assert.assertEquals(recurringbilltxt, PropsUtil.getDataPropertyValue("My_Recurring_Bill_Text"));
		
		logger.info(">>>>> Verified 'Based on Data' text..");
		String text = budget.basedMonthDataText().getText();
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("Based_On_Month_Of_Data"));
		
	}

	@Test(description = "AT-69449:Verify Select All is at the top", groups = { "DesktopBrowsers" }, dependsOnMethods = { "login",
			"verifyNextAndBackButton" }, priority = 12, enabled = true)
	public void VerifyMyIncomeHeader() throws Exception {
		logger.info(">>>>> Clicked on Next Button..");
		SeleniumUtil.click(budget.NextButton());
		SeleniumUtil.waitForPageToLoad();
		String[] billCategories = PropsUtil.getDataPropertyValue("Recurring_Bill_Categories")
				.split(PropsUtil.getDataPropertyValue("comma"));
		logger.info(""+billCategories);
		
		logger.info(">>>>> Verifying categories..");
		List<WebElement> categories = budget.billCategories();
		for (int cat = 0; cat < categories.size(); cat++) {
			Assert.assertTrue(categories.get(cat).isDisplayed());

		}
		
		logger.info(">>>>> Verified Select All option..");
		List<WebElement> billCat = budget.billCategories();
		for (int i = 0; i < billCat.size(); i++) {
			if (Config.getInstance().getEnvironment().contains("BBT")) {
				Assert.assertEquals(billCat.get(1).getText(), PropsUtil.getDataPropertyValue("Select_All_Text"));
			} else {
				Assert.assertEquals(billCat.get(0).getText(), PropsUtil.getDataPropertyValue("Select_All_Text"));
			}

		}

	}

	@Test(description = "AT-69458:Verify All the checkboxes", groups = { "DesktopBrowsers" }, dependsOnMethods = { "login",
			"VerifyMyIncomeHeader" }, priority = 13, enabled = true)
	public void verifyEditIcon() throws Exception {
		logger.info(">>>>> Verifying the edit Icon..");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(budget.myIncomeEditIcon().isDisplayed());

		List<WebElement> l = budget.CheckBox();
		logger.info(">>>>> Verifying all the checkboxes..");
		for (int i = 0; i < l.size(); i++) {
			String checkBox = l.get(i).getAttribute(PropsUtil.getDataPropertyValue("dataType1"));
			logger.info("**********  ChkBox name = " + checkBox);
			Assert.assertTrue(budget.CheckBox().get(i).isDisplayed());
		}
	}

	@Test(description = "Verify all the category amount,checkbox functionality", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"VerifyMyIncomeHeader" }, priority = 14, enabled = true)
	public void verifyCategoryAmountAndCheckBox() throws Exception {
		
		SeleniumUtil.waitForPageToLoad();
		String[] amount = PropsUtil.getDataPropertyValue("Budget_Amount")
				.split(PropsUtil.getDataPropertyValue("slash"));
		logger.info(""+amount);
		
		logger.info(">>>>> Verifying all the category amount..");
		List<WebElement> l = budget.CategoryAmount();
		for (int i = 1; i < l.size(); i++) {
			String categoryAmount = l.get(i).getText();
			Assert.assertTrue(l.get(i).isDisplayed());
			Assert.assertFalse(categoryAmount.isEmpty());
		}

		logger.info(">>>>> Verifying checkbox functionality..");
		SeleniumUtil.click(budget.CheckBox().get(0));
		Assert.assertTrue(budget.diableamountamount1().getAttribute(PropsUtil.getDataPropertyValue("dataType"))
				.contains(PropsUtil.getDataPropertyValue("disabledText")));
		SeleniumUtil.click(budget.CheckBox().get(0));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-69456:Verify that Edit icon exists for the my income and my recurring bills", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"VerifyMyIncomeHeader" }, priority = 15, enabled = true)
	public void verifyAddIcon() throws Exception {
		logger.info(">>>>> Verifying add icon..");
		Assert.assertTrue(budget.addiconStep1element().isDisplayed());
		
		logger.info(">>>>> Verifying next and back buttons..");
		Assert.assertTrue(budget.backButton().isDisplayed());
		Assert.assertTrue(budget.incomeNextButton().isDisplayed());
		
		logger.info(">>>>> Verified edit icon for my income and recurring bills section..");
		Assert.assertTrue(budget.myRecurringEditIcon().isDisplayed());
	}

	@Test(description = "AT-69459,AT-69460,AT-69461,AT-69462:Verify save and cancel button", dependsOnMethods = { "login", "VerifyMyIncomeHeader" }, groups = {
			"DesktopBrowsers" }, priority = 16, enabled = true)
	public void verifyRecurringBillEditIcon() throws Exception {
		logger.info(">>>>> Clicking on Edit Icon");
		SeleniumUtil.click(budget.myRecurringEditIcon());
		SeleniumUtil.waitForPageToLoad();


		Assert.assertTrue(budget.incomeSaveButton().isDisplayed());
		if (budget.incomeCancelButton() != null) {
			logger.info(">>>>> Verifying Save and Cancle button..");
			Assert.assertTrue(budget.incomeCancelButton().isDisplayed());
		}

		logger.info(">>>>> Editing all categories with the amount 1000..");
		for (int i = 1; i < budget.editCatAmount().size(); i++) {
			budget.editCatAmount().get(i).clear();
			budget.editCatAmount().get(i).sendKeys(PropsUtil.getDataPropertyValue("income2"));
		}
		budget.editCatAmount().get(budget.editCatAmount().size() - 1).sendKeys(Keys.TAB);
		logger.info(">>>>> Saving the changes");
		SeleniumUtil.click(budget.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Changing the ATM category amount");
		SeleniumUtil.click(budget.myIncomeEditIcon());
		SeleniumUtil.waitForPageToLoad();
		budget.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
				PropsUtil.getDataPropertyValue("income1"));
		logger.info(">>>>> Saving the changes");
		budget.RecuringBillamountamount3().sendKeys(Keys.TAB);
		SeleniumUtil.click(budget.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "Verify the category text and add/edit category", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyRecurringBillEditIcon" }, priority = 17, enabled = true)
	public void verifyAfterSelectingCategory() throws Exception {
		logger.info(">>>>> Clicking on Edit Icon");
		SeleniumUtil.click(budget.addiconStep2element());

		logger.info(">>>>> verifying Category text.");
		String text = budget.selectCategoryTextBox().getText();
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("Select_Category_Text"));

		logger.info(">>>>> Verifying Category dropdown");
		String dropdown = budget.selectCategoryDropDown().getAttribute(PropsUtil.getDataPropertyValue("dataType"));
		Assert.assertTrue(dropdown.contains(PropsUtil.getDataPropertyValue("dropdownText")));

		logger.info(">>>>> Clicking on category dropdown.");
		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Selecting grocery category from the dropdown.");
		WebElement category = budget.groceryCategory();
		SeleniumUtil.scrollElementIntoView(d, category, true);
		SeleniumUtil.click(category);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Clicking on cancle button");
		SeleniumUtil.click(budget.cancelButton());
		SeleniumUtil.waitForPageToLoad(2500);
		
		logger.info(">>>>> Searching 'Charitable Giving' category from searchlist and selecting..");
		SeleniumUtil.click(budget.addiconStep2element());		
		SeleniumUtil.click(budget.selectCategoryDropDown());
		budget.selectCategoryTextBox1().sendKeys(PropsUtil.getDataPropertyValue("charatibleGiving"));
		SeleniumUtil.click(budget.category());
		SeleniumUtil.waitForPageToLoad();

		logger.info("Entering the values for the category box");
		budget.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income2"));
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info(">>>>> Adding ");
		SeleniumUtil.click(budget.addButton());
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify and Add office expense category", groups = { "DesktopBrowsers" }, dependsOnMethods = {
			"login", "verifyAfterSelectingCategory" }, priority = 18, enabled = true)
	public void verifyMaximumCategories() throws Exception {
		logger.info(">>>>> Selecting the category by HLC and MLC");
		SeleniumUtil.click(budget.addiconStep2element());
		SeleniumUtil.waitForPageToLoad(2500);		
		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(budget.getCategory("4", "1"));
		SeleniumUtil.waitForPageToLoad(2500);
		budget.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income3"));
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Adding a healthcare category");
		SeleniumUtil.click(budget.addButton());

		logger.info(">>>>> Adding office expense category.");
		Assert.assertTrue(budget.addiconStep2element().isDisplayed());
		SeleniumUtil.click(budget.addiconStep2element());
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(budget.getCategory("6", "3"));
		SeleniumUtil.waitForPageToLoad(2500);
		budget.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income4"));
		SeleniumUtil.waitForPageToLoad(2500);
		Assert.assertTrue(budget.addButton().isDisplayed());
		SeleniumUtil.click(budget.addButton());
		

	}

	@Test(description = "Verify all the categories", groups = { "DesktopBrowsers" }, dependsOnMethods = { "login",
			"verifyMaximumCategories" }, priority = 19, enabled = true)
	public void verifyCancelButton() throws Exception {
		logger.info(">>>>> Verifying the categories..");
		ArrayList<String> arrayList1 = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();
		String[] billCategories = PropsUtil.getDataPropertyValue("Recurring_Bill_Categories1")
				.split(PropsUtil.getDataPropertyValue("comma"));
		for (int j = 0; j < budget.billCategories().size(); j++) {
			arrayList1.add(billCategories[j]);
			arrayList2.add(budget.billCategories().get(j).getText());
			Collections.sort(arrayList1);
			Collections.sort(arrayList2);
		}
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "Verify total recurring bills amount", dependsOnMethods = { "login" }, groups = {
			"DesktopBrowsers" }, priority = 20, enabled = true)
	public void verifyTotalRecurringBills() throws Exception {
		logger.info(">>>>> Clicking in Back Button..");
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(budget.backButton());
		SeleniumUtil.waitForPageToLoad();
		
		logger.info(">>>>> Verifying the 'total recurring bills' header..");
		String totalRecurringText = budget.totalRecurringBillText().getText();
		Assert.assertEquals(totalRecurringText, PropsUtil.getDataPropertyValue("Total_Recurring_Bills"));
		
		logger.info(">>>>> verifying the total recurring bills amount");
		Assert.assertTrue(budget.totalRecurringAmount().isDisplayed());
	}

	@Test(description = "Verify Flexible Spending header", dependsOnMethods = { "login",
			"verifyTotalRecurringBills" }, groups = { "DesktopBrowsers" }, priority = 21, enabled = true)
	public void verifyTotalRecurringBill() throws Exception {
		logger.info(">>>>> Clicking on next button..");
		WebElement incomeNextButton = budget.incomeNextButton();
		SeleniumUtil.scrollElementIntoView(d, incomeNextButton, true);
		SeleniumUtil.click(incomeNextButton);
		SeleniumUtil.waitForPageToLoad(2500);

		logger.info(">>>>> Verifying Flexible Spending header..");
		String flexiblespendingtxt = budget.flexibleSpendingText().getText();
		Assert.assertEquals(flexiblespendingtxt, PropsUtil.getDataPropertyValue("Flexible_Spending_Text"));
	}

	@Test(description = "Verify All Flexible_Spending_Category", dependsOnMethods = { "login",
			"verifyTotalRecurringBill" }, groups = { "DesktopBrowsers" }, priority = 22, enabled = false)
	public void verifyFlexibleSpendingCategories() throws Exception {
		logger.info(">>>>> Verifying flexible spending category.");
		SeleniumUtil.waitForPageToLoad();
		String[] flexiblespendingcategory = PropsUtil.getDataPropertyValue("Flexible_Spending_Category").split(",");
		List<WebElement> flexiblespending = budget.flexibleSpendingCategories();
		logger.info(""+flexiblespending.size());

		for (int i = 0; i < flexiblespending.size(); i++) {
			String flexingspendingcategories = flexiblespending.get(i).getText();
			logger.info(""+flexingspendingcategories);
			Assert.assertEquals(flexingspendingcategories, flexiblespendingcategory[i]);
		}
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify EDIT/ADD icon,BACK and DONE button", dependsOnMethods = { "login",
			"verifyTotalRecurringBill" }, groups = { "DesktopBrowsers" }, priority = 23, enabled = true)
	public void verifyFlexibleSpendingIcons() throws Exception {
		logger.info(">>>>> Verifying add,edit,next and back buttons.");
		Assert.assertTrue(budget.flexibleSpendingEditIcon().isDisplayed());
		Assert.assertTrue(budget.flexibleSpendingAddIcon().isDisplayed());
		Assert.assertTrue(budget.incomeNextButton().isDisplayed());
		Assert.assertTrue(budget.backButton().isDisplayed());
	}

	@Test(description = "Verify that by default all the checkboxes are checked", dependsOnMethods = { "login",
			"verifyTotalRecurringBill" }, groups = { "DesktopBrowsers" }, priority = 24, enabled = true)
	public void verifyFlexibleSpendingCheckAllCheckBox() throws Exception {
		logger.info(">>>>> Verifying all the checkboxes are checked by default");
		SeleniumUtil.waitForPageToLoad(2000);
		String unchecked = budget.checkAllCheckBox().getAttribute("aria-checked");
		Assert.assertTrue(unchecked.contains("true"));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "unchecked all the checkboxes", dependsOnMethods = { "login",
			"verifyTotalRecurringBill" }, groups = { "DesktopBrowsers" }, priority = 25, enabled = true)
	public void verifyFlexibleSpendingCategoriesCheckBoxUncheck() throws Exception {
		logger.info(">>>>> Unchecking all the files.");
		SeleniumUtil.click(budget.checkAllCheckBox());
		SeleniumUtil.waitForPageToLoad(6000);
		String uncheck = budget.checkAllCheckBox().getAttribute("aria-checked");
		Assert.assertTrue(uncheck.contains("false"));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify Checkboxes value set to default-TRUE", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyFlexibleSpendingCategoriesCheckBoxUncheck" }, priority = 26, enabled = true)
	public void verifyFlexibleSpendingCategoriesCheckBoxChecked() throws Exception {
		logger.info(">>>>> verifying checkbox value default set - TRUE.");
		SeleniumUtil.click(budget.checkAllCheckBox());
		SeleniumUtil.waitForPageToLoad();
		String categories = budget.checkAllCheckBox().getAttribute("aria-checked");
		Assert.assertTrue(categories.contains("true"));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify select category text,dropdown", dependsOnMethods = { "login",
			"verifyTotalRecurringBill" }, groups = { "DesktopBrowsers" }, priority = 27, enabled = true)
	public void verifyFlexibleSpendingAddCategories() throws Exception {
		logger.info(">>>>> Clicking on add icon.");
		SeleniumUtil.click(budget.flexibleSpendingAddIcon());
		SeleniumUtil.waitForPageToLoad(2500);

		logger.info(">>>>> Verifying select category text.");
		String selectcategory = budget.selectCategoryTextBox().getText();
		Assert.assertEquals(selectcategory, PropsUtil.getDataPropertyValue("Select_Category_Text"));

		logger.info(">>>>> Verifying drop down..");
		String dropdown = budget.selectCategoryDropDown().getAttribute(PropsUtil.getDataPropertyValue("dataType"));
		Assert.assertTrue(dropdown.contains(PropsUtil.getDataPropertyValue("dropdownText")));
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(budget.cancelButton());
	}

	@Test(description = "Verify input box is 0.00", groups = { "DesktopBrowsers" }, dependsOnMethods = { "login",
			"verifyFlexibleSpendingAddCategories" }, priority = 28, enabled = true)
	public void AddFlexibleSpendingCategory() throws Exception {

		SeleniumUtil.click(budget.flexibleSpendingAddIcon());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("************************clicked on add category icon************************");

		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("************************CLicked on the category DD************************");

		// budget.selectCategoryByNumber(2, 2);
		SeleniumUtil.click(budget.getCategory("2", "2"));
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("************************Selected the category************************");

		/*
		 * String inputbox = budget.editAmountInputBox()
		 * .getAttribute(PropsUtil.getDataPropertyValue("valueLabel"));
		 * Assert.assertTrue(inputbox.equals(PropsUtil.getDataPropertyValue("income5")))
		 * ; logger.
		 * info("************************verified input box is 0.00************************"
		 * );
		 */

		budget.editAmountInputBox().clear();
		budget.editAmountInputBox().sendKeys(PropsUtil.getDataPropertyValue("income1"));
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("************************edit the amount************************");

		Assert.assertTrue(budget.addButton().isDisplayed());
		Assert.assertTrue(budget.cancelButton().isDisplayed());
		logger.info("************************verified add and cancel button************************");
		SeleniumUtil.click(budget.cancelButton());
		SeleniumUtil.waitForPageToLoad(2500);

	}

	@Test(description = "Verify the Automative category is displayed", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"AddFlexibleSpendingCategory" }, priority = 29, enabled = true)
	public void flexibleSpendingCategoryDisabled() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget.flexibleSpendingAddIcon());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("************************clicked on add category icon************************");

		SeleniumUtil.click(budget.selectCategoryDropDown());
		logger.info("************************CLicked on the category DD************************");

		SeleniumUtil.waitForPageToLoad();
		logger.info("************************verifed the Automative category is displayed************************");

		boolean disabled = false;
		/**
		 * @author sswain1 The below code is updated as per mobile and web. In mobile
		 *         the flexi category screen should be closed before validating the save
		 *         button presence.
		 * 
		 */
		if (budget.isAddFlexCategoryClosePresent()) {
			SeleniumUtil.click(budget.closeAddFlexibleCategory());
			SeleniumUtil.waitForPageToLoad(2500);
			logger.info("************************Close categore Add Page in Mobile************************");
			disabled = budget.addButton().isEnabled();

		} else {
			disabled = budget.doneButton().isEnabled();
		}

		Assert.assertTrue(disabled);
		logger.info("****verifed that unless the user clicks on the ADD button the DONE button should be disabled.*");

		SeleniumUtil.click(budget.cancelButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("************************clicked on cancel button************************");

	}

	@Test(description = "Verify Total flexible text, Budget left Text and budget Exceed Text", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"flexibleSpendingCategoryDisabled" }, priority = 30, enabled = true)
	public void verifyTotalFlexibleSpendingText() throws Exception {

		String totalSpendingText = budget.totalFlexibleSpendingText().getText();
		String budgetExceedText = budget.budgetExceedsText().getText();
		String consideringBudgetingText = budget.considerBudgetingText().getText();
		Assert.assertEquals(PropsUtil.getDataPropertyValue("totalSpending"), totalSpendingText);
		Assert.assertEquals(PropsUtil.getDataPropertyValue("budgetText"), budgetExceedText);
		Assert.assertEquals(PropsUtil.getDataPropertyValue("considerBudget"), consideringBudgetingText);
		SeleniumUtil.waitForPageToLoad();
		logger.info(
				"************************Validated Total flexible text, Budget left Text and budget Exceed Text************************");

	}

	@Test(description = "Click on done button", dependsOnMethods = { "login",
			"flexibleSpendingCategoryDisabled" }, groups = { "DesktopBrowsers" }, priority = 31, enabled = true)
	public void verifyAmountInFlexibleSpending() throws Exception {
		boolean btn = budget.SaveButton().isDisplayed();
		SeleniumUtil.click(budget.SaveButton());
		logger.info("************************clicked on done button************************");
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(btn);
	}

	@Test(description = "Verify Login Happens Successfully", dependsOnMethods = { "login",
			"verifyAmountInFlexibleSpending" }, groups = { "DesktopBrowsers" }, priority = 32)
	public void verifyHeaderwithoutalert() throws Exception {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************Navigated to budget page*************");

		Assert.assertEquals(budget.CreateBudgetHeader_CB().getText(), PropsUtil.getDataPropertyValue("Create_Budget1"));
		Assert.assertTrue(budget.CreateBudgetPlusIcon().isDisplayed());
		logger.info("*******************Verified the CreateBudget header and the plus icon*************");
	}

	@Test(description = "Verify CreateBudget header and Feature tour icon for cobrands which doesnot have more info", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyHeaderwithoutalert" }, priority = 33, enabled = true)
	public void verifyHeaderWithoutMoreInfo() throws Exception {
		Assert.assertEquals(budget.CreateBudgetHeader_CB().getText(), PropsUtil.getDataPropertyValue("Create_Budget1"));
		Assert.assertTrue(budget.CreateBudgetPlusIcon().isDisplayed());
		logger.info("*******************Verified the CreateBudget header and the plus icon*************");

		//Adding code to more
		Assert.assertTrue(budget.moreIcon().isDisplayed());
		SeleniumUtil.click(budget.moreIcon());
		logger.info("***************Verified and clicked on more icon*************************");
		
		d.findElement(By.cssSelector("#menu-link-alert-button")).click();
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify  alertSetting description,alertSetting subheader", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyHeaderwithoutalert" }, priority = 34, enabled = true)
	public void verifyAlertSettings() throws Exception {
		String[] alertSettingSubHeader = PropsUtil.getDataPropertyValue("budget_AlertSetting")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = budget.AlertSettingsSubheader();
		for (int i = 0; i < l.size(); i++) {
			String list = l.get(i).getText();
			Assert.assertEquals(list, alertSettingSubHeader[i]);
		}
		logger.info("*******************Verified the alertSetting subheaders*************");

		String[] alertSettingDesc = PropsUtil.getDataPropertyValue("budget_AlertSettingDesc")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> s = budget.AlertSettingsDescription();
		for (int j = 0; j < s.size(); j++) {
			String desc = s.get(j).getText();
			Assert.assertEquals(desc, alertSettingDesc[j]);
		}
		logger.info("*******************Verified the alertSetting description*************");
	}

	@Test(description = "Verify the Monthly text and dropdown is present in Budget Summary header in Alert Setting popup", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyHeaderwithoutalert" }, priority = 35, enabled = true)
	public void verifyQAMonthlyDropDown() throws Exception {

		Assert.assertEquals(budget.QAMonthlyText().getText(), PropsUtil.getDataPropertyValue("QA_Monthly_Text"));
		Assert.assertTrue(budget.QAMonthlyDropDown().isDisplayed());
		logger.info(
				"**Verified the Monthly text and dropdown is present in Budget Summary header in Alert Setting popup**");

		SeleniumUtil.click(budget.QAMonthlyDropDown());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("*******************Clicked on the Monthly dropdown***********");

		String[] monthlyWeeklyText = PropsUtil.getDataPropertyValue("Budget_Monthly_Weekly")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = budget.QAMonthlyList();
		for (int i = 0; i < l.size(); i++) {
			String list = l.get(i).getText();
			Assert.assertEquals(monthlyWeeklyText[i], list);
		}
		logger.info("*******************Verified the text (weekly and Monthly)on the Monthly dropdown***********");

		Assert.assertTrue(budget.TickMark().isDisplayed());
		SeleniumUtil.click(budget.QAMonthlyList().get(1));
		Assert.assertEquals(budget.QAMonthlyText().getText(), PropsUtil.getDataPropertyValue("Weekly_Text"));
		logger.info("******Verified the tick mark and clicked on weekly option on the dropdown***********");

	}

	@Test(description = "Verify cancel and save changes button,toggle buttons", groups = {
			"DesktopBrowsers" }, priority = 36, dependsOnMethods = { "login",
					"verifyQAMonthlyDropDown" }, enabled = true)
	public void verifySaveChangesButton() throws Exception {

		Assert.assertTrue(budget.CancelButton().isDisplayed());
		Assert.assertTrue(budget.SaveChangesButton_CB().isDisplayed());
		logger.info("*******************verified cancel and save changes button*************");

		List<WebElement> l = budget.ToggleButton();
		for (int i = 0; i < l.size(); i++) {
			Assert.assertTrue(l.get(i).isDisplayed());
		}
		logger.info("*******************verified the toggle buttons*************");
		SeleniumUtil.click(budget.SaveChangesButton_CB());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************Clicked on the save changes buttons*************");
	}

	@Test(description = "Verify Select Budget Desc,Select Existing GroupText,Budget DropDown,Dropdown Icon,Plus Icon", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifySaveChangesButton" }, priority = 37, enabled = true)
	public void verifyCreateBudgetButton() throws Exception {
		/**
		 * @author sswain1 Adding explicit wait to handle the delay in saving Alert page
		 *         changes.
		 * 
		 */

		SeleniumUtil.click(budget.CreateBudgetPlusIcon());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("*******************Clicked on Create Budget Header*************");

		Assert.assertEquals(budget.SelectBudgetDesc().getText(), PropsUtil.getDataPropertyValue("Select_Budget_Desc"));
		Assert.assertEquals(budget.SelectExistingGroupText().getText(),
				PropsUtil.getDataPropertyValue("Select_Existing_Group_Text"));
		Assert.assertTrue(budget.BudgetDropDown().isDisplayed());
		Assert.assertTrue(budget.DropDownIcon().isDisplayed());
		Assert.assertTrue(budget.PlusIcon().isDisplayed());
		String budget1 = budget.BudgetDropDown().getAttribute(PropsUtil.getDataPropertyValue("valueLabel"));
		Assert.assertEquals(PropsUtil.getDataPropertyValue("Budget_MyBudget"), budget1);
		logger.info(
				"*******************verified the Select Budget Desc,Select Existing GroupText,Budget DropDown,Dropdown Icon,Plus Icon*************");
	}

	@Test(description = "Verify Info Icon description, close icon", groups = { "DesktopBrowsers" }, dependsOnMethods = {
			"login", "verifyCreateBudgetButton" }, priority = 38, enabled = true)
	public void verifyInfoIcon() throws Exception {

		Assert.assertTrue(budget.InfoIcon_CB().isDisplayed());
		SeleniumUtil.click(budget.InfoIcon_CB());
		logger.info("*******************verified and clicked on Info Icon*************");

		Assert.assertEquals(budget.InfoDescription().getText(), PropsUtil.getDataPropertyValue("Info_Description"));
		Assert.assertTrue(budget.CloseIcon().isDisplayed());
		SeleniumUtil.click(budget.CloseIcon());
		Assert.assertEquals(budget.ORText().getText(), PropsUtil.getDataPropertyValue("OR_Text"));
		logger.info("*******************verified Info Icon description, close icon*************");
	}

	@Test(description = "Verify No account group- icon,text,description and Create group button", groups = {
			"DesktopBrowsers" }, priority = 39, dependsOnMethods = { "login",
					"verifyCreateBudgetButton" }, enabled = true)
	public void verifyNoGroupDetails() throws Exception {

		Assert.assertTrue(budget.NoGroupIcon().isDisplayed());
		Assert.assertEquals(budget.NoAccountGroupText().getText(), PropsUtil.getDataPropertyValue("NoGroupSelected"));
		Assert.assertEquals(budget.NoAccountGroupDesc().getText(),
				PropsUtil.getDataPropertyValue("No_Account_Group_Desc"));
		Assert.assertTrue(budget.NoAccountGroupButton().isDisplayed());
		logger.info(
				"*******************Verified  No account group- icon,text,description and Create group button*************");
	}

	@Test(description = "Verify Create Account Group-Header,description; GroupName and Account to include text ", groups = {
			"DesktopBrowsers" }, priority = 40, dependsOnMethods = { "login",
					"verifyCreateBudgetButton" }, enabled = true)
	public void verifyCreateGroupPopUp() throws Exception {

		SeleniumUtil.click(budget.NoAccountGroupButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************Clicked No account group button*************");

		Assert.assertEquals(budget.CreateAccountGroupHeader().getText(),
				PropsUtil.getDataPropertyValue("Create_Account_Group_Header"));
		Assert.assertEquals(budget.CreateAccountGroupDesc().getText(),
				PropsUtil.getDataPropertyValue("Create_Account_Group_Desc"));
		Assert.assertEquals(budget.GroupNameText().getText(), PropsUtil.getDataPropertyValue("Group_Name_Text"));
		Assert.assertEquals(budget.AccountToIncludeText().getText(),
				PropsUtil.getDataPropertyValue("Account_To_Include_Text"));
		Assert.assertTrue(budget.GroupInputBox().isDisplayed());
		logger.info(
				"*********verified Create Account Group-Header,description; GroupName and Account to include text ******");
	}

	@Test(description = "create a new group", dependsOnMethods = { "login", "verifyCreateBudgetButton",
			"verifyCreateGroupPopUp" }, groups = { "DesktopBrowsers" }, priority = 41, enabled = true)
	public void verifyCreateGroup() throws Exception {

		budget.GroupInputBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name"));
		SeleniumUtil.waitForPageToLoad();

		int length = budget.CheckBox_CB().size();
		for (int i = 0; i < length; i++) {
			SeleniumUtil.click(budget.CheckBox_CB().get(i));
			SeleniumUtil.waitForPageToLoad(2500);

		}
		
		d.findElement(By.id("createUpdateGroup")).click();
		SeleniumUtil.waitForPageToLoad();
		/**
		 * 
		 * @author sswain1 The following text is not displayed in mobile. Hence
		 *         validation done only for mobile.
		 */
		if (budget.IsInfoHeaderPresent() && budget.IsInfoTextPresent()) {
			Assert.assertEquals(budget.InfoText().getText(),
					PropsUtil.getDataPropertyValue("Not_Considered_Budget_Text"));
			Assert.assertEquals(budget.InfoHeader().getText(),
					PropsUtil.getDataPropertyValue("Not_Considered_Budget_Creation_Header"));
		}
		SeleniumUtil.click(budget.InfoIcon1());
		logger.info("*******************Clicked on info icon *************");
		Assert.assertEquals(budget.InfoDescription1().getText(),
				PropsUtil.getDataPropertyValue("Info_Description_Text"));
		Assert.assertTrue(budget.CloseIcon1().isDisplayed());
		SeleniumUtil.click(budget.CloseIcon1());
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("*******************verified info icon, info text, info popup and popup description*************");
	}

	@Test(description = "Verify and clicked on next button", dependsOnMethods = { "login",
			"verifyCreateGroup" }, groups = { "DesktopBrowsers" }, priority = 42, enabled = true)
	public void verifyNextButton() throws Exception {
		SeleniumUtil.scrollElementIntoView(d, budget.NextButtonText(), true);
		Assert.assertEquals(budget.NextButtonText().getText(), PropsUtil.getDataPropertyValue("Next_Button_Text"));
		Assert.assertTrue(budget.NextButtonIcon().isDisplayed());
		SeleniumUtil.click(budget.NextButtonText());
		logger.info("*******************Verified and clicked on next button*************");
	}

	@Test(description = "Verify/add a new category", groups = { "DesktopBrowsers" }, dependsOnMethods = { "login",
			"verifyNextButton" }, priority = 43, enabled = true)
	public void addRecurringBillCategory() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget.addiconStep2eleforBills());
		logger.info("*******************Clicked on edit icon*************");

		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*******************clicked on category dropdown*************");

		int numberOfRows1 = budget.newRowAdded().size();
		// budget.selectCategoryByNumber(2, 1);
		SeleniumUtil.click(budget.getCategory("2", "1"));
		budget.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income1"));

		SeleniumUtil.click(budget.addButton());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows2 = budget.newRowAdded().size();
		Assert.assertTrue(numberOfRows2 > numberOfRows1);
		logger.info("*******************added a new category*************");

		SeleniumUtil.click(budget.addiconStep2eleforBills());
		logger.info("*******************clicked on edit icon*************");

		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************clicked on category dropdown*************");

		// budget.selectCategoryByNumber(4, 2);
		SeleniumUtil.click(budget.getCategory("4", "2"));
		SeleniumUtil.click(budget.cancelButton());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************cancelled the selected category*************");

		SeleniumUtil.click(budget.addiconStep2eleforBills());
		logger.info("*******************clicked on edit icon*************");

		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************clicked on category dropdown*************");

		int numberOfRows5 = budget.newRowAdded().size();
		// budget.selectCategoryByNumber(4, 2);
		SeleniumUtil.click(budget.getCategory("4", "2"));
		budget.typeFlexibleSpendingCategoriesValues(PropsUtil.getDataPropertyValue("income3"));
		SeleniumUtil.click(budget.addButton());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows6 = budget.newRowAdded().size();
		Assert.assertTrue(numberOfRows6 > numberOfRows5);
		logger.info("*******************added a third category*************");

		SeleniumUtil.click(budget.incomeNextButton());
		logger.info("*******************clicked on next button************");

	}

	@Test(description = "Verify the amount edited is saved", groups = { "DesktopBrowsers" }, dependsOnMethods = {
			"login", "addRecurringBillCategory" }, priority = 44, enabled = true)
	public void addFlexibleSpendingCategory() throws Exception {

		SeleniumUtil.click(budget.flexibleSpendingAddIcon());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows1 = budget.newRowAdded().size();
		logger.info("*******************Clicked on flexible Spending Add Icon ************");

		SeleniumUtil.click(budget.selectCategoryDropDown());
		SeleniumUtil.click(budget.getCategory("2", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		budget.editAmountInputBox().clear();
		SeleniumUtil.waitForPageToLoad(2000);
		budget.editAmountInputBox().sendKeys(PropsUtil.getDataPropertyValue("income1"));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(budget.addButton());
		SeleniumUtil.waitForPageToLoad(2500);
		int numberOfRows2 = budget.newRowAdded().size();
		Assert.assertTrue(numberOfRows2 > numberOfRows1);
		logger.info("*******************Added and verified that a new category has been added ************");

		SeleniumUtil.click(budget.flexibleSpendingEditIcon());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************Clicked on flexible Spending edit Icon ************");

		budget.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
				PropsUtil.getDataPropertyValue("income1"));
		SeleniumUtil.waitForPageToLoad(2500);
		
		/*budget.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryForHomeImprovement"),
				PropsUtil.getDataPropertyValue("income3"));*/
		SeleniumUtil.click(budget.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad(2500);
		logger.info("*******************Flexible Spending save icon clicked ************");
		logger.info("*******************Verified that the amount edited is saved************");
	}

	@Test(description = "Verify the amount edited is saved", dependsOnMethods = { "login",
			"addFlexibleSpendingCategory" }, priority = 45, enabled = true)
	public void verifySave() throws Exception {
		boolean flag = budget.doneButton().isDisplayed();
		SeleniumUtil.click(budget.doneButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(flag);
		logger.info("*******************clicked on done button************");
	}

	// Verify the cyclic dependancy

	@Test(description = "AT-69429:Verify budget header, Settings icon and settings header", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login","verifySave" }, priority = 46)
	public void verifyBudgetHeader() throws Exception {

		PageParser.navigateToPage("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("***************Navigated to budget page**************************");

		Assert.assertEquals(budget.budgetHeader().getText(), PropsUtil.getDataPropertyValue("Budget_Header"));
		String icon = budget.settingsIcon().getAttribute(PropsUtil.getDataPropertyValue("dataType"));
		Assert.assertTrue(icon.contains(PropsUtil.getDataPropertyValue("budget_icon")));

		Assert.assertTrue(budget.settingsHeader().isDisplayed());
		logger.info("***************validated Settings icon and settings header**************************");
		/**
		 * @author sswain1 The below assertion is not valid for mobile.
		 */
		if (budget.isSettingHeaderTextPresent()) {
			Assert.assertEquals(budget.settingsHeader().getText(), PropsUtil.getDataPropertyValue("Settings_Header"));
		}

		logger.info(
				"***************Validated budget header, Settings icon and settings header**************************");

	}

	@Test(description = "AT-69421,AT-69422:Verify feature Tour Icon and text", dependsOnMethods = { "login",
			"verifyBudgetHeader" }, groups = { "DesktopBrowsers" }, priority = 47)
	public void verifyFeatureTourIcon() throws Exception {

		Assert.assertTrue(budget.moreIcon().isDisplayed());
		SeleniumUtil.click(budget.moreIcon());
		logger.info("***************Verified and clicked on more icon*************************");

		String icon = budget.featureTourIcon().getAttribute(PropsUtil.getDataPropertyValue("dataType"));
		Assert.assertTrue(icon.contains(PropsUtil.getDataPropertyValue("budget_icon")));
		Assert.assertEquals(budget.featureTourText().getText(), PropsUtil.getDataPropertyValue("Feature_Tour_Text"));

		logger.info("***************Verified feature Tour Icon and text*************************");

	}

	@Test(description = "AT-69423,AT-69424,AT-69425:Verify Budget Account Groups,Budget Created Text, next button and close icon", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyBudgetHeader" }, priority = 48, enabled = true)
	public void verifyFeatureTourCoachMark() throws Exception {

		SeleniumUtil.click(budget.featureTourIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("***************clicked on feature tour icon*************************");

		Assert.assertEquals(budget.BudgetAndAccountsGroups().getText(),
				PropsUtil.getDataPropertyValue("Budget_Account_Groups"));
		Assert.assertEquals(budget.BudgetCreatedText().getText(),
				PropsUtil.getDataPropertyValue("Budget_Created_Text"));
		Assert.assertTrue(budget.featureTourNextButton(1).isDisplayed());
		Assert.assertTrue(budget.featureTourCloseIcon1().isDisplayed());
		logger.info(
				"***************verified Budget Account Groups,Budget Created Text, next button and close icon*************************");

		budget.featureTourNextButton(1).click();
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("***************clicked on next icon*************************");

		Assert.assertEquals(budget.overviewText().getText(), PropsUtil.getDataPropertyValue("Overview_Text"));
		Assert.assertEquals(budget.seeYourActualText().getText(),
				PropsUtil.getDataPropertyValue("budget_notification_desc"));
		logger.info("***************verified Overview_Text*************************");

	}

	@Test(description = "AT-69426,AT-69427,AT-69428:Verify close icon, next and back button are present", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyFeatureTourCoachMark" }, priority = 49, enabled = true)
	public void verifyFeatureTourCloseIcon() throws Exception {
		Assert.assertTrue(budget.featureTourCloseIcon().isDisplayed());
		Assert.assertTrue(budget.featureTourNextButton(2).isDisplayed());
		if (budget.isFeatureTourBackButtonPresent()) {
			Assert.assertTrue(budget.featureTourBackButton().isDisplayed());
		}
		logger.info("***************verified close icon, next and back button are present*************************");
		SeleniumUtil.click(budget.featureTourNextButton(2));
		logger.info("***************clicked on the next button*************************");

	}

	@Test(description = "Verify Budget Details Text,See Your Actual Text, back and next button", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyFeatureTourCloseIcon" }, priority = 50)
	public void verifyBudgetDetailsPopUp() throws Exception {

		Assert.assertEquals(budget.budgetDetailsText().getText(),
				PropsUtil.getDataPropertyValue("Budget_Details_Text"));
		Assert.assertEquals(budget.seeYourBudgetText().getText(),
				PropsUtil.getDataPropertyValue("See_Your_Actual_Text"));
		Assert.assertTrue(budget.budgetDetailsBackButton(2).isDisplayed());
		Assert.assertTrue(budget.featureTourNextButton(3).isDisplayed());
		logger.info(
				"***************Verified Budget Details Text,See Your Actual Text, back and next button*************************");

		SeleniumUtil.click(budget.featureTourNextButton(3));
		logger.info("***************Clicked on the next button*************************");

	}

	@Test(description = "Verify Settings Text, Manage Notifications Accounts Text,Got It and back button", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyBudgetDetailsPopUp",
					"verifyBudgetHeader" }, priority = 51)
	public void verifySettingsPopUp() throws Exception {
		Assert.assertEquals(budget.settingsText().getText(), PropsUtil.getDataPropertyValue("Settings_Text"));
		Assert.assertTrue(budget.budgetDetailsBackButton(3).isDisplayed());
		Assert.assertTrue(budget.featureTourNextButton(4).isDisplayed());
		logger.info(
				"***************verified Settings Text, Manage Notifications Accounts Text,Got It and back button*************************");
		SeleniumUtil.click(budget.featureTourNextButton(4));
		logger.info("***************clicked on the Got It button*************************");

	}

	@Test(description = "Verify Income Spending Summery Header,Income_Header,Bill And Spending Summery Text, Current Month on the chart", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifySettingsPopUp" }, priority = 52)
	public void verifyIncomeHeader() throws Exception {
		if (budget.IsIncomeAndSpendingHeaderPresent()) {
			Assert.assertEquals(budget.incomeAndSpendingHeader().getText(),
					PropsUtil.getDataPropertyValue("Income_Spending_Summery_Header"));
		}
		Assert.assertEquals(budget.incomeHeader().getText(), PropsUtil.getDataPropertyValue("Income_Header"));
		Assert.assertEquals(budget.billAndSpendingHeader().getText(),
				PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text"));
		Assert.assertTrue(budget.monthText().getText().contains(budget.currentMonth()));
		logger.info(
				"****verified Income Spending Summery Header,Income_Header,Bill And Spending Summery Text, Current Month on the chart *****");

	}

	@Test(description = "Text Name and Category field displayed in add budget popup", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyIncomeHeader" }, priority = 53)
	public void verifyBudgetDetailsAddIcon() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		if (budget.isClickFlexiAddIconByNumber()) {
			budget.clickAddIconByNumber(1);
		} else {
			/**
			 * @author sswain1: In else part the flow is defined for Mobile. Click on "View
			 *         Detail" button to go to Income and Flexible spending page
			 */
			SeleniumUtil.click(budget.viewDetailButtonInBudget());
			logger.info("************ Clicked On View Detail button on Budget ****************");
			SeleniumUtil.click(budget.flexiSpendingAddIcon());
			logger.info("**************** Clicked On flexiSpendingAddIcon ******************");
		}

		Assert.assertEquals(budget.addBudgetHeader().getText(), PropsUtil.getDataPropertyValue("Add_Budget_Header"));

		logger.info("*****Verified that the user can add a new spending by clicking on  the + icon ");

		Assert.assertEquals(budget.addBudgetNameText().getText(), PropsUtil.getDataPropertyValue("Name_Text"));

		logger.info("*****Verified that the Text Name and Category field displayed in add budget popup******");

	}

	@Test(description = "Verify dropdown and Default label of dropdown", dependsOnMethods = { "login",
			"verifyBudgetDetailsAddIcon" }, groups = { "DesktopBrowsers" }, priority = 54)
	public void verifyBudgetDetailsAddBudgetPopUp() throws Exception {
		Assert.assertEquals(budget.addBudgetNameTextBox().getText(),
				PropsUtil.getDataPropertyValue("Select_Category_Text"));
		Assert.assertTrue(budget.addBudgetDropDown().getAttribute(PropsUtil.getDataPropertyValue("dataType"))
				.contains(PropsUtil.getDataPropertyValue("dropdownText")));
		logger.info("*************verified dropdown and Default label of dropdown***********");

		SeleniumUtil.click(budget.addBudgetNameTextBox());
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("*************clicked on the dropdown***********");

		String bugHLcat = PropsUtil.getDataPropertyValue("bugHLcat");
		String bugMLcat = PropsUtil.getDataPropertyValue("bugMLcat");
		int highLevelcategory1 = Integer.parseInt(bugHLcat);
		int masterLevelcategory1 = Integer.parseInt(bugMLcat);
		// budget.selectCategoryByNumber(highLevelcategory1, masterLevelcategory1);
		SeleniumUtil.click(
				budget.getCategory(Integer.toString(highLevelcategory1), Integer.toString(masterLevelcategory1)));
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************Selected a category from the dropdown***********");

	}

	@Test(description = "Verify amount per Month Text,amount Per Month Input Box,last Three Month Text", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyBudgetDetailsAddBudgetPopUp" }, priority = 55)
	public void verifyBudgetDetailsAddBudgetCategory() throws Exception {
		Assert.assertEquals(PropsUtil.getDataPropertyValue("budget_AmtPerMonth"),
				budget.amountperMonthText().getText());
		Assert.assertTrue(budget.amountPerMonthInputBox().isDisplayed());
		Assert.assertEquals(budget.lastThreeMonthText().getText(),
				PropsUtil.getDataPropertyValue("budget_last3Months"));

		logger.info("******Verified amount per Month Text,amount Per Month Input Box,last Three Month Text");

	}

	@Test(description = "Verify that the category is added and popup is closed", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyBudgetDetailsAddBudgetPopUp" }, priority = 56)
	public void verifyAddBudgetSaveButton() throws Exception {
		budget.amountPerMonthInputBox().clear();
		budget.amountPerMonthInputBox().sendKeys(PropsUtil.getDataPropertyValue("income4"));
		SeleniumUtil.click(budget.addBudgetNameTextBox1());
		Assert.assertTrue(budget.addBudgetSaveButton().isDisplayed());
		SeleniumUtil.click(budget.addBudgetSaveButton());
		SeleniumUtil.waitForPageToLoad();

		logger.info("*************added the amount in the input box and saved it***********");

		if (budget.isviewDetailButtonPresent()) {
			SeleniumUtil.click(budget.viewDetailButtonInBudget());
			Assert.assertTrue(budget.leftToSpendMaximizeIcon().isDisplayed());
			SeleniumUtil.click(budget.getBackButton_BSA());
			logger.info("*************Clicked on viewDetailButtonInBudget***********");
		} else {
			Assert.assertTrue(budget.leftToSpendMaximizeIcon().isDisplayed());
		}

		logger.info("*************Verified that the category is added and popup is closed***********");

	}

	@Test(description = "Verify income label and the amount adjacent to it", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyAddBudgetSaveButton" }, priority = 57)
	public void verifyIncomeAmount() throws Exception {
		Assert.assertTrue(budget.verifyLeftAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("budget_incomeText")));
		Assert.assertFalse(budget.getLeftAmountByCategoryName("income").isEmpty());
		logger.info("*************Verified income label and the amount adjacent to it***********");

	}

	@Test(description = "Verify the BILLS AND SPENDING text and left amount field just above the spending bar", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyAddBudgetSaveButton" }, priority = 58)
	public void verifyBillsAndSpendingAmount() throws Exception {
		Assert.assertEquals(budget.billAndSpendingHeader().getText(),
				PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text"));
		Assert.assertTrue(budget
				.verifyLeftAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1")));
		String amount = budget
				.getLeftAmountByCategoryName(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1"));
		Assert.assertFalse(amount.isEmpty());
		logger.info(
				"*************verified that we have the BILLS AND SPENDING text and left amount field just above the spending bar***********");

	}

	@Test(description = "Verify the details of budget amount ,spent amount and left amount", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyAddBudgetSaveButton" }, priority = 59)
	public void verifyLeftToSpendMaximizeIcon() throws Exception {
		if (budget.isviewDetailButtonPresent()) {
			SeleniumUtil.click(budget.viewDetailButtonInBudget());
			SeleniumUtil.click(budget.leftToSpendMaximizeIcon());
			SeleniumUtil.waitForPageToLoad(2000);
			logger.info("*************Clicked on leftToSpendMaximizeIcon***********");
		} else {
			SeleniumUtil.click(budget.leftToSpendMaximizeIcon());
		}

		Assert.assertEquals(budget.budgetedAmountText().getText(),
				PropsUtil.getDataPropertyValue("budgetedAmountText"));
		Assert.assertEquals(budget.spentAmountText().getText(), PropsUtil.getDataPropertyValue("spentAmountText"));
		Assert.assertEquals(budget.remainingAmountText().getText(),
				PropsUtil.getDataPropertyValue("remainingAmountText"));

		logger.info(
				"*************verified that all the details of budget amount ,spent amount and left amount**********");

	}

	@Test(description = "Verify remaining amount is equal to budget amount minus the spending amount", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyLeftToSpendMaximizeIcon" }, priority = 60)
	public void verifyLeftToSpendThisMonthTransaction() throws Exception {
		Assert.assertEquals(budget.thisMonthTransactionText().getText(),
				PropsUtil.getDataPropertyValue("This_Month_Transaction_Text"));
		Assert.assertTrue(budget.leftToSpendEditIcon().isDisplayed());
		logger.info(
				"*************verified that that the THIS MONTHS TRANSACTION text appear beside the circular graph**********");
		float budgetAmount1 = budget.replaceAllLiterals(budget.budgetedAmount().getText());
		float spentAmount1 = budget.replaceAllLiterals(budget.spentAmount().getText());
		float remainingAmount1 = Math.round(budget.replaceAllLiterals(budget.remainingAmount().getText()));
		Assert.assertEquals((budgetAmount1 - spentAmount1), remainingAmount1);

		logger.info(
				"*************verified that remaining amount is equal to budget amount minus the spending amount**********");

	}

	@Test(description = "Verify that the transactions associated to that particular month is reflected", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyLeftToSpendMaximizeIcon" }, priority = 61)
	public void verifyLeftToSpendThisMonthTransactionAmount() throws Exception {
		Assert.assertTrue(budget.transactionAmount().size() > 0);
		logger.info(
				"*************verified that the transactions associated to that particular month is reflected**********");
		SeleniumUtil.click(budget.leftToSpendEditIcon());
		Assert.assertEquals(budget.addBudgetHeader().getText(), PropsUtil.getDataPropertyValue("Edit_Budget_Text"));
		logger.info("*************clicked on Edit Budget icon and verified Edit Budget Text**********");

	}

	@Test(description = "Verify delete,save and close button, Spending Trend header", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyLeftToSpendThisMonthTransactionAmount" }, priority = 62)
	public void verifyLeftToSpendEditBudget() throws Exception {
		budget.editBudgetInputBox().clear();
		budget.editBudgetInputBox().sendKeys(PropsUtil.getDataPropertyValue("income3"));
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************Edited the  Budget amount**********");
		Assert.assertTrue(budget.deleteButton().isDisplayed());
		Assert.assertTrue(budget.saveButton().isDisplayed());
		Assert.assertTrue(budget.closeIcon().isDisplayed());
		Assert.assertTrue(
				budget.spendingTrendText().getText().contains(PropsUtil.getDataPropertyValue("SpendingTrend")));

		logger.info("*************Verified delete,save and close button, Spending Trend header**********");

	}

	@Test(description = "Verify All Text In Landing Page", groups = { "DesktopBrowsers" }, dependsOnMethods = { "login",
			"verifyLeftToSpendEditBudget" }, priority = 63)
	public void verifyLeftToSpendThisMonthTransactionAmoun() throws Exception {
		boolean saveBtn = budget.saveButton().isDisplayed();
		SeleniumUtil.click(budget.saveButton());
		SeleniumUtil.click(budget.saveButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(saveBtn);
		logger.info("*************clicked on save button**********");
	}

	@Test(description = "Verify that we have actual and budget values displayed below the income", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyLeftToSpendThisMonthTransactionAmoun" }, priority = 64)
	public void verifyIncomeAmountActualText() throws Exception {
		if (budget.isBackFrmMonthlyPresent()) {
			SeleniumUtil.click(budget.backFrmMonthlyTransaction());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(budget.getBackButton_BSA());
		}
		Assert.assertFalse(
				budget.getActualAmountByCategory(PropsUtil.getDataPropertyValue("budget_incomeText")).isEmpty());

		Assert.assertFalse(
				budget.getBudgetedAmountByCategory(PropsUtil.getDataPropertyValue("budget_incomeText")).isEmpty());

		Assert.assertTrue(
				budget.verifyActualAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("budget_incomeText")));

		Assert.assertTrue(
				budget.verifyBudgetAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("budget_incomeText")));

		logger.info("*************verified that we have actual and budget values displayed below the income**********");

	}

	@Test(description = "Verify that we have the actual and budgetted field below the spending", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login", "verifyIncomeAmountActualText" }, priority = 65)
	public void verifyBillAndSpendingAmountActualText() throws Exception {
		String Amount = budget
				.getActualAmountByCategory(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1"));
		Assert.assertFalse(Amount.isEmpty());
		Assert.assertTrue(budget
				.verifyBudgetAmountTextIsDisplayed(PropsUtil.getDataPropertyValue("Bill_And_Spending_Summery_Text1")));

		logger.info("*************verified that we have the actual and budgetted field below the spending**********");

	}

	@Test(description = "Verify that we have the notification box beside the income and spending summary", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyIncomeAmountActualText" }, priority = 66, enabled = true)
	public void verifyViewAllAccountsButton() throws Exception {
		String notification = budget.notificationHeader().getText();
		Assert.assertEquals(notification, PropsUtil.getDataPropertyValue("Notification_Header"));
		logger.info(
				"*************verified that we have the notification box beside the income and spending summary**********");
	}

	@Test(description = "Verify that clicking on view my accounts takes us to the accounts page of the budget finapp", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login",
					"verifyIncomeAmountActualText" }, priority = 67, enabled = true)
	public void verifyAfterClickingOnViewAllAccountsButton() throws Exception {
		String header = budget.accountsHeader().getText();
		Assert.assertEquals(header, PropsUtil.getDataPropertyValue("Accounts_Header"));
		SeleniumUtil.click(budget.backToBudgetIcon());
		SeleniumUtil.waitForPageToLoad();

		logger.info(
				"*************Verified that clicking on view my accounts takes us to the accounts page of the budget finapp**********");

	}

	@Test(description = "Verify that LEFT TO SPEND text present on the Budget landing screen and a Sort by text below", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login","verifyAfterClickingOnViewAllAccountsButton" }, priority = 68)
	public void verifyBudgetDetailsText() throws Exception {
		if (budget.isBudgetDetailsHeaderPresent()) {
			Assert.assertEquals(budget.budgetDetailsHeader().getText(),
					PropsUtil.getDataPropertyValue("Budget_Details_Header"));
			Assert.assertEquals(budget.sortByText().getText(), PropsUtil.getDataPropertyValue("Sort_By_Text"));
		} else {
			SeleniumUtil.click(budget.viewDetailButtonInBudget());
			logger.info(
					"******************************* Clicked On View Detail button on Budget ************************************");
		}

		logger.info(
				"*************verified that LEFT TO SPEND text present on the Budget landing screen and a Sort by text below**********");
		String dropdown = budget.dropDown().getAttribute(PropsUtil.getDataPropertyValue("dataType"));
		Assert.assertTrue(dropdown.contains(PropsUtil.getDataPropertyValue("dropdownValue")));
		logger.info("*************verified that we have the dropdown opening just beside sort by **********");

	}

	@Test(description = "Verify that % is displayed", dependsOnMethods = { "login" }, groups = {
			"DesktopBrowsers" }, priority = 69)
	public void verifyLeftToSpendHowImDoingText() throws Exception {
		Assert.assertEquals(budget.howImDoingText().getText(), PropsUtil.getDataPropertyValue("How_I_Doing_Text"));
		List<WebElement> s = budget.spentPercentage();

		for (int i = 0; i < s.size(); i++) {
			if (s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_minus"))) {
				Assert.assertTrue(s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_minus")));
			}
			if (s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_percentage"))) {
				Assert.assertTrue(s.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_percentage")));
			}
		}
		logger.info("*************Verified that % is displayed**********");

	}

	@Test(description = "Verify amount is displayed on selecting How_I_Doing from DD", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login" }, priority = 70, enabled = true)
	public void verifyLeftToSpendBudgetedAmount() throws Exception {
		SeleniumUtil.click(budget.dropDown());
		Assert.assertEquals(budget.budgetedAmountLarget().getText(),
				PropsUtil.getDataPropertyValue("Budgeted_Amount_Text"));
		SeleniumUtil.click(budget.budgetedAmountLarget());
		List<WebElement> l = budget.leftAmount();
		for (int i = 0; i < l.size(); i++) {
			Assert.assertTrue(budget.leftAmount().get(i).isDisplayed());
			Assert.assertTrue(l.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_dollar"))
					|| l.get(i).getText().contains(PropsUtil.getDataPropertyValue("budget_minus")));

		}

		logger.info("*************verified that amount is displayed on selecting How_I_Doing from DD**********");

	}

	@Test(description = "Verify that After clicking on Settings it should navigate to Settings page", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "login" }, priority = 71)
	public void verifyBudgetSettings() throws Exception {
		if (budget.isBackFrmMonthlyPresent()) {
			SeleniumUtil.click(budget.getBackButton_BSA());
			SeleniumUtil.waitForPageToLoad(1000);
		}
		Assert.assertTrue(budget.settingsIcon().isDisplayed());
		logger.info("*************verified the Settings coach mark display**********");
		SeleniumUtil.click(budget.settingsIcon());
		Assert.assertTrue(
				budget.budgetSettingsHeader().getText().contains(PropsUtil.getDataPropertyValue("budgetSettingsText")));

		logger.info(
				"*************verified that After clicking on Settings it should navigate to Settings page**********");

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
