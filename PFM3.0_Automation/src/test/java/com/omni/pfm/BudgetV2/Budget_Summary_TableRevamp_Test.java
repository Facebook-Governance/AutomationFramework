/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.BudgetV2;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_EditPopup_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_TableRevamp_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.FunctionUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Summary_TableRevamp_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_Summary_TableRevamp_Test.class);

	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_EditPopup_Loc budget_Edit;
	Budget_CreateBudget_Loc budgetCreateLoc;
	Budget_Summary_TableRevamp_Loc bud_TableRevamp;
	Budget_BudgetSummary_Loc budgetSummary;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Budget Summary Table Revamp");
		budgetCreateLoc = new Budget_CreateBudget_Loc(d);
		budget_Edit = new Budget_Summary_EditPopup_Loc(d);
		bud_TableRevamp = new Budget_Summary_TableRevamp_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);
	}

	@Test(description = "Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, enabled = true)
	public void login() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("bbudget.site16441.5", "site16441.5");
		PageParser.forceNavigate("Budget", d);
		logger.info(">>>>> Click on  the GetStarted button");
		budgetCreateLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));
	}

	@Test(description = "AT-110262,AT-110264:Verify Spending label progress bar", priority = 1, dependsOnMethods = {
			"login" }, enabled = true)
	public void verifySpendingproBarDetails() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel, PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil
							.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingInfoMesgMobileTableRevamp_mob"),
					"spend and budgeted spend is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("width"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingWidthTableRevamp_mob"),
					"width is not dispalyed");
			Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("width"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingRemainWidthTableRevamp_mob"),
					"width is not dispalyed");
		} else {
			Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("background-color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingColor"),
					"background color is not dispalyed");
		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingRemainColor"),
				"background color is not dispalyed");
	}

	@Test(description = "AT-110261:Verify Spending label progress bar", priority = 2, dependsOnMethods = {
			"login" }, enabled = true)
	public void verifyIncomeproBarDetails() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeInfoMesgTableRevamp_mob"),
					"income earned and budgeted income is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("width"),
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeWidthMobile"),
					"width is not dispalyed");
			Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width"),
					PropsUtil.getDataPropertyValue(
							"Budget_MonthlymaintaineceSummeryProIncomeRemainWidthTableRevamp_mob"),
					"width is not dispalyed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeInfoMesgTableRevamp"),
					"income earned and budgeted income is not displayed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("100%"),
				"width should be 100%");
	}

	@Test(description = "AT-110260,AT-110813,AT-110733:Verify that category details section table is shown with a header name as Wants,Needs and Income", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "login" }, enabled = true)
	public void verifySummeryBudgetTimeFilter() throws Exception {
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_SummeryDefaulttimeFilterValue"),
				"time filter label is not displayed");
		Assert.assertTrue(budgetSummary.budget_Summery_TimeFilterDate().isDisplayed());
	}

	@Test(description = "AT-110713,AT-110264:Verify that category details section table is shown with a header name as Wants,Needs and Income", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "login" }, enabled = true)
	public void verifyCategoryDetails() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertTrue(budget_Edit.viewDetailsBtn().isDisplayed());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}

		logger.info(">>>>> Verify Flexible Spending Category Header");
		Assert.assertTrue((bud_TableRevamp.verifyWantsHeader().getText())
				.contains(PropsUtil.getDataPropertyValue("Budget_Categoryheader1")));

		logger.info(">>>>> Verify Bills Category Header");
		Assert.assertTrue((bud_TableRevamp.verifyNeedsHeader().getText())
				.contains(PropsUtil.getDataPropertyValue("Budget_Categoryheader2")));

		logger.info(">>>>> Verify Income Category Header");
		Assert.assertTrue((bud_TableRevamp.verifyIncomeHeader().getText())
				.contains(PropsUtil.getDataPropertyValue("Budget_Categoryheader3")));

	}

	@Test(description = "AT-110714,AT-110766,AT-110263:Verify that category details section is by default shown for current month", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyCategoryDetailsforCurMonth() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			logger.info(">>>>> Verify Income Category Header" + bud_TableRevamp.CurrentMonth().getText());
			Assert.assertTrue(bud_TableRevamp.CurrentMonth().isDisplayed());
		}
	}

	@Test(description = "AT-110715:Verify that for current month time filter month and year to be shown above the category details table.", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyTimefilterCurMonthandYear() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			logger.info(">>>>> Verify Current Month Details" + bud_TableRevamp.CurrentMonth().getText());
			Assert.assertTrue(bud_TableRevamp.CurrentMonth().isDisplayed());
		}
	}

	@Test(description = "AT-110716,AT-110716,AT-110717,AT-110718,AT-110727,AT-110728:Verify that user is shown with Color indicator against each category based on the spent percentage.", groups = {
			"DesktopBrowsers" }, priority = 8, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void veriColorIndForCategory() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			List<WebElement> SpentOrEarnedAmtList = bud_TableRevamp.SpentOrEarnedAmt();
			List<WebElement> BudgetedAmtList = bud_TableRevamp.BudgetedAmt();
			float spentOrEarnedAmtCal[] = new float[SpentOrEarnedAmtList.size()];
			float BudgetedAmtCal[] = new float[BudgetedAmtList.size()];
			for (int i = 0; i < SpentOrEarnedAmtList.size(); i++) {
				String spentValue = SpentOrEarnedAmtList.get(i).getText().toString();
				float spentConvertedValue = 0f;
				if (spentValue.contains("$")) {
					spentValue = spentValue.substring(spentValue.indexOf("$") + 1, spentValue.length());
					spentConvertedValue = NumberFormat.getNumberInstance(Locale.US).parse(spentValue).floatValue();
				}
				spentOrEarnedAmtCal[i] = spentConvertedValue;
				String budgetValue = BudgetedAmtList.get(i).getText().toString();
				BudgetedAmtCal[i] = NumberFormat.getNumberInstance(Locale.US).parse(budgetValue).floatValue();
			}
			List<WebElement> progressBarList = bud_TableRevamp.CategoryProgressBar();
			for (int k = 0; k < progressBarList.size() - 4; k++) {
				float LeftOrEarnedPercentage = (spentOrEarnedAmtCal[k] / BudgetedAmtCal[k]) * 100;
				logger.info(
						"The spentOrEarnedAmtCal:" + spentOrEarnedAmtCal[k] + "is [" + LeftOrEarnedPercentage + "]");
				if (LeftOrEarnedPercentage >= 0 && LeftOrEarnedPercentage <= 75) {
					String greenColor = progressBarList.get(k).getCssValue("background-color");
					logger.info("Color is ========>" + greenColor);
					Assert.assertEquals(greenColor, PropsUtil.getDataPropertyValue("LightRedColor_bar"));
				} else if (LeftOrEarnedPercentage > 75 && LeftOrEarnedPercentage <= 100.99) {
					String yellowColor = progressBarList.get(k).getCssValue("background-color:");
					logger.info("Color is ========>" + yellowColor);
					if (yellowColor.equals("rgba(30, 29, 28, 1)")) {
						Assert.assertEquals(yellowColor.trim(), "rgba(30, 29, 28, 1)");
					} else
						Assert.assertEquals(yellowColor.trim(),
								PropsUtil.getDataPropertyValue("YellowColor_bar").trim());
				} else if (LeftOrEarnedPercentage >= 101.00) {
					String redColor = progressBarList.get(k).getCssValue("background-color");
					logger.info("Color is ========>" + redColor);
					Assert.assertEquals(redColor, PropsUtil.getDataPropertyValue("RedColor_bar"));
				} else {
					String blueColor = progressBarList.get(k).getCssValue("background-color");
					logger.info("Color is ========>" + blueColor);
					Assert.assertEquals(blueColor, PropsUtil.getDataPropertyValue("BlueColor_bar"));
				}
			}
		}
	}

	@Test(description = "AT-110719,AT-110720,AT-110812:Verify that for every category section Category name is shown correctly", groups = {
			"DesktopBrowsers" }, priority = 9, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void veriCategoryIconForCategory() throws Exception {
		for (int i = 0; i < bud_TableRevamp.CategoryIcon().size(); i++) {
			logger.info("Verifying  on Category Icons");
			Assert.assertTrue(bud_TableRevamp.CategoryIcon().get(i).isDisplayed());
		}
		List<WebElement> budgetCategoriesList = bud_TableRevamp.BudgetCategoriesLists();
		String[] categoriesList = PropsUtil.getDataPropertyValue("Budget_Categories_List").trim().split(",");
		for (int i = 0; i < budgetCategoriesList.size(); i++) {
			logger.info("Verifying  on Category Names");
			Assert.assertEquals(budgetCategoriesList.get(i).getText().trim(), categoriesList[i].trim());
		}
	}

	@Test(description = "AT-110721,AT-110722,AT-110723:Verify that if there is a change in the category name by the user respective category name must be shown in the category section", groups = {
			"DesktopBrowsers" }, priority = 10, dependsOnMethods = { "veriCategoryIconForCategory" }, enabled = true)
	public void verifyEditedCategoryNameInBudgetCatLists() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {

			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
		}
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad(4000);
		logger.info("Creating Sub Category on Categories page ");
		SeleniumUtil.click(bud_TableRevamp.CategoryInAccCategPage());
		SeleniumUtil.waitForPageToLoad(4000);

		SeleniumUtil.click(bud_TableRevamp.EditCategoryInAccCategPage());
		SeleniumUtil.waitForPageToLoad(4000);

		bud_TableRevamp.EditCategoryInAccCategPage().clear();
		bud_TableRevamp.EditCategoryInAccCategPage().sendKeys("Vehicle Expenses");
		SeleniumUtil.click(bud_TableRevamp.SaveEditCatNameChanges());
		SeleniumUtil.waitForPageToLoad(4000);

		for (int i = 0; i < bud_TableRevamp.BudgetCategoriesLists().size(); i++) {
			logger.info("Verifying Edited Category Name is not shown  on Category lists");
			Assert.assertEquals(bud_TableRevamp.BudgetCategoriesLists().get(i).getText(),
					PropsUtil.getDataPropertyValue("Budget_Categories_List").trim());
		}

	}

	@Test(description = "AT-110724,AT-110725,AT-110729,AT-110730,AT-110731,AT-110774:Verify that Budget Amount column is sorted by default based on the spent amount percentage ", groups = {
			"DesktopBrowsers" }, priority = 11, dependsOnMethods = {
					"verifyEditedCategoryNameInBudgetCatLists" }, enabled = true)
	public void verifySortedbySpentAmtPercentage() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {

			PageParser.forceNavigate("Budget", d);
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			PageParser.forceNavigate("Budget", d);
			SeleniumUtil.waitForPageToLoad(4000);

			SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumn());
			SeleniumUtil.waitForPageToLoad(8000);

			List<WebElement> el = SeleniumUtil.getWebElements(d, "WantsAmtColumnSpentAmtList_BS", "Budget", null);
			List<String> actualElementVal = new LinkedList<String>();
			for (WebElement actualElement : el) {
				actualElementVal.add(actualElement.getText().trim());
			}
			List<String> expectedValue = FunctionUtil.splitLabelValueAsList("Budget_Ammount_Values_list", ":");
			for (int i = 0; i < expectedValue.size(); i++) {
				Assert.assertEquals(actualElementVal.get(i), expectedValue.get(i),
						"Verify same categories are present");
			}
		}
	}

	@Test(description = "AT-110726,AT-110732,AT-110733:Verify that Spent Amount column is sorted by default based on the spent amount percentage  ", groups = {
			"DesktopBrowsers" }, priority = 12, dependsOnMethods = {
					"verifySortedbySpentAmtPercentage" }, enabled = true)
	public void verifySpentAmtSortedbySpentAmt() throws Exception {

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			SeleniumUtil.waitForPageToLoad(3000);
			List<WebElement> el = SeleniumUtil.getWebElements(d, "FlexiSpendAmtColumnSpentAmtList_BS", "Budget", null);

			List<String> actualElementVal = new LinkedList<String>();
			for (WebElement actualElement : el) {
				actualElementVal.add(actualElement.getText().trim());
			}
			List<String> expectedValue = FunctionUtil.splitLabelValueAsList("Spent_Ammount_Values_list", ":");
			for (int i = 0; i < expectedValue.size(); i++) {
				Assert.assertEquals(actualElementVal.get(i), expectedValue.get(i));
			}
		}
	}

	@Test(description = "AT-110735,AT-110169,AT-:Verify that under Expense section both the Recurring Bills section and Flexible spending section is showing the same column names", groups = {
			"DesktopBrowsers" }, priority = 13, dependsOnMethods = { "verifySpentAmtSortedbySpentAmt" }, enabled = true)
	public void verifySpentAmtSortedbySpentAmtPercentage() throws Exception {

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			SeleniumUtil.waitForPageToLoad(3000);
			List<WebElement> el = SeleniumUtil.getWebElements(d, "FlexiSpendAmtColumnLeftOverAmtList_BS", "Budget",
					null);

			List<String> actualElementVal = new LinkedList<String>();
			for (WebElement actualElement : el) {
				actualElementVal.add(actualElement.getText().trim());
			}
			List<String> expectedValue = FunctionUtil.splitLabelValueAsList("LeftOver_Ammount_Values_list", ":");
			for (int i = 0; i < expectedValue.size(); i++) {
				Assert.assertEquals(actualElementVal.get(i), expectedValue.get(i),
						"Verify Same colum names are present");
			}
		}
	}

	@Test(description = "AT-110733:Verify that under Expense section the Recurring Bills section is shown ", groups = {
			"DesktopBrowsers" }, priority = 14, dependsOnMethods = {
					"verifySpentAmtSortedbySpentAmtPercentage" }, enabled = true)
	public void verifyNeedsHeader() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertTrue(bud_TableRevamp.NeedsColumsText().getText()
					.contains(PropsUtil.getDataPropertyValue("NeedsHeader")));
		} else
			Assert.assertEquals(bud_TableRevamp.NeedsColumsText().getText(),
					PropsUtil.getDataPropertyValue("NeedsHeader"));

	}

	@Test(description = "AT-110734:Verify that under Expense section the Flexible spending section is shown ", groups = {
			"DesktopBrowsers" }, priority = 15, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyWantsHeader() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertTrue(bud_TableRevamp.WantsColumsText().getText()
					.contains(PropsUtil.getDataPropertyValue("WantsHeader")));
		} else
			Assert.assertEquals(bud_TableRevamp.WantsColumsText().getText(),
					PropsUtil.getDataPropertyValue("WantsHeader"));

	}

	@Test(description = "AT-110736:Verify that under Expense Section only the Expense related category must be shown", groups = {
			"DesktopBrowsers" }, priority = 16, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyExpenseCategoriesList() throws Exception {
		SeleniumUtil.click(bud_TableRevamp.DefaultWantsDD());
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> el = SeleniumUtil.getWebElements(d, "BudgetCategories_List_BS", "Budget", null);
		List<String> actualElementVal = new LinkedList<String>();
		for (WebElement actualElement : el) {
			actualElementVal.add(actualElement.getText().trim());
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> expectedValue = FunctionUtil.splitLabelValueAsList("Expense_Categories_List", ",");
			Collections.sort(expectedValue);
			Collections.sort(actualElementVal);
			SeleniumUtil.assertExpectedActualList(actualElementVal, expectedValue, "Expense categories are not as expected");
		}
	}

	@Test(description = "AT-110737,AT-110766:Verify that every Expense category row is shown with accordion icon ", groups = {
			"DesktopBrowsers" }, priority = 17, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyExpenseCategorywithAccordionIcon() throws Exception {

		for (int i = 0; i < bud_TableRevamp.VerifyAccordianIcon().size(); i++) {
			Assert.assertTrue(bud_TableRevamp.VerifyAccordianIcon().get(i).isDisplayed(), "Verify Accordion present");
		}

	}

	@Test(description = "AT-110738,AT-110739,AT-110740,AT-110741:Verify once user click on the Accordion icon  only transaction table with respective transactions list must be shown only for current month time filter ", groups = {
			"DesktopBrowsers" }, priority = 18, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyAccordianforCurrentMonth() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(bud_TableRevamp.VerifyAccordianIcon().get(0));
			SeleniumUtil.waitForPageToLoad();
			String thisMonth = DateUtil.getMonth(0);
			Assert.assertTrue(bud_TableRevamp.AccornianForCurrMonth().getText().contains(thisMonth));
		}
	}

	@Test(description = "AT-110744,AT-110745,AT-110758,AT-110759,AT-110760:Verify that all Amounts will be max of (7,2) format in the category details under Budgeted column ", groups = {
			"DesktopBrowsers" }, priority = 19, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyAmtFielFormat() throws Exception {
		SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(bud_TableRevamp.EditBudgetAmtField());
		bud_TableRevamp.EditBudgetAmtField().clear();
		bud_TableRevamp.EditBudgetAmtField().click();
		logger.info(">>>>> Entering value for Budget Amt in Edit Budget Text field with more than 11 digits");
		bud_TableRevamp.EditBudgetAmtField().sendKeys("123434565678989764432");
		SeleniumUtil.waitFor(2);
		Assert.assertEquals(bud_TableRevamp.Budget_Edit_errorMessage().getText(),
				PropsUtil.getDataPropertyValue("Edit_ErrorMsg_11digits"));
		bud_TableRevamp.EditBudgetAmtField().clear();
		logger.info(">>>>> Entering value for Budget Amt in Edit Budget Text field with Numerals");
		bud_TableRevamp.EditBudgetAmtField().sendKeys("abcd");
		Assert.assertEquals(bud_TableRevamp.Budget_Edit_errorMessage().getText(),
				PropsUtil.getDataPropertyValue("Edit_ErrorMsg_numerals"));
		bud_TableRevamp.EditBudgetAmtField().clear();
		logger.info(">>>>> Entering value for Budget Amt in Edit Budget Text field with 0");
		bud_TableRevamp.EditBudgetAmtField().sendKeys("0");
		Assert.assertEquals(bud_TableRevamp.Budget_Edit_errorMessage().getText(),
				PropsUtil.getDataPropertyValue("Edit_ErrorMsg_zero"));
	}

	@Test(description = "AT-110746,AT-110747,AT-110748:Verify that in Expense section under Flexible spending section user must be provided with the option to add new Budgeted category  ", groups = {
			"DesktopBrowsers" }, priority = 20, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyAddNewSpendingCategory() throws Exception {
		SeleniumUtil.click(bud_TableRevamp.AddNewWantsCat());
		SeleniumUtil.waitForPageToLoad();
		String defaultCategory = bud_TableRevamp.DefaultCatName().getText();
		logger.info("Default Categroy name is >>>>" + defaultCategory);
		SeleniumUtil.click(bud_TableRevamp.SpendingCatListinDDIcon());
		String firstCategoryName = bud_TableRevamp.NewSpendingCatListinDDList().get(0).getText();
		logger.info("First selected Categroy name in the dropdown list >>>>" + firstCategoryName);
		Assert.assertEquals(defaultCategory, firstCategoryName, "Verify in category list");
	}

	@Test(description = "AT-110748,AT-110749,AT-110750,AT-110761:Verify that if user click on the Add button, without entering any amount. no action must be performed but, an error message to be shown here. ", groups = {
			"DesktopBrowsers" }, priority = 21, dependsOnMethods = { "verifyCategoryDetails" }, enabled = true)
	public void verifyAddNewWithoutAmtErr() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(bud_TableRevamp.AddNewCatAmtTextField());
			bud_TableRevamp.AddNewCatAmtTextField().clear();
			bud_TableRevamp.AddNewCatAmtTextField().sendKeys("");
			SeleniumUtil.click(bud_TableRevamp.AddNewAddBtn());
			logger.info("Error Message is displayed if the user adding without Amount field");
			Assert.assertEquals(bud_TableRevamp.AddCatErrorMsg().getText(),
					PropsUtil.getDataPropertyValue("AddCatWithoutAmtFieldErrorMsg"));
		}
	}

	@Test(description = "AT-110752,AT-110753,AT-110754,AT-110755:Verify that in the Category dropdown list only the category name which are not used for budget calculation previously must be shown ", groups = {
			"DesktopBrowsers" }, priority = 22, dependsOnMethods = { "verifyAddNewWithoutAmtErr" }, enabled = true)
	public void NewWantsCatListNotinPrevious_Budget() throws Exception {
		String NewCatList = null;
		for (int i = 0; i < bud_TableRevamp.NewSpendingCatListinDDList().size(); i++) {
			NewCatList = bud_TableRevamp.NewSpendingCatListinDDList().get(i).getText();
		}
		Assert.assertFalse(NewCatList.contains(PropsUtil.getDataPropertyValue("Budget_Categories_List")));
	}

	@Test(description = "AT-110756,AT-110757,AT-110764,AT-110770:Verify that next to the category dropdown we have the amount text box (budgeted amount to be entered) and if there is 3 months average amount then it must be prepopulated in the field", groups = {
			"DesktopBrowsers" }, priority = 23, dependsOnMethods = { "verifyAddNewWithoutAmtErr" }, enabled = true)
	public void VerifyCatDDTimeFilterfo3Months() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(bud_TableRevamp.CloseBtn_Mob());
			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}
		SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(0));
		SeleniumUtil.waitForPageToLoad();
		String twoMonthsBack = DateUtil.getMonth(-3);
		String lastToLastMonth = DateUtil.getMonth(-2);
		String lastMonth = DateUtil.getMonth(-1);
		String thisMonth = DateUtil.getMonth(0);
		String[] expectedValues = { twoMonthsBack, lastToLastMonth, lastMonth, thisMonth };
		List<WebElement> actualValues = budget_Edit.verifyEditGraph_XAxisMonths();
		for (int i = 0; i < actualValues.size(); i++) {
			String actualVal = actualValues.get(i).getText().trim();
			logger.info("Actual Values are " + actualVal);
			Assert.assertEquals(actualVal, expectedValues[i].toUpperCase());
		}
	}

	@Test(description = "AT-110762:Verify that once user click on the Add button a new category which was selected from the dropdown must be added to specific section and only the specific row must get updated not to reload the complete page.  ", groups = {
			"DesktopBrowsers" }, priority = 24, dependsOnMethods = { "VerifyCatDDTimeFilterfo3Months" }, enabled = true)
	public void VerifyNewCategAdded() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}
		SeleniumUtil.click(bud_TableRevamp.AddNewWantsCat());
		SeleniumUtil.waitForPageToLoad();
		logger.info("Adding New Category ");
		bud_TableRevamp.AddNewCatAmtTextField().clear();
		bud_TableRevamp.AddNewCatAmtTextField().sendKeys("1000");
		SeleniumUtil.click(bud_TableRevamp.AddNewAddBtn());
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> budgetCategoriesList = bud_TableRevamp.BudgetCategoriesLists();
		String categoriesname = bud_TableRevamp.WantsNewlyAddedCateg().getText();
		for (int i = 0; i < budgetCategoriesList.size(); i++) {
			logger.info("Verifying  on Category Names");
			Assert.assertEquals(categoriesname, (PropsUtil.getDataPropertyValue("WantsCatNewlyAdded")));
		}
	}

	@Test(description = "AT-110763:Verify that close button/icon is also available,  on click the add category section must close", groups = {
			"DesktopBrowsers" }, priority = 25, dependsOnMethods = { "VerifyNewCategAdded" }, enabled = true)
	public void VerifyNewAddCatCloseIconPresent() throws Exception {

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(0));
			Assert.assertTrue(bud_TableRevamp.EditTextBoxClseIcon().isDisplayed(), "Verify the close icon displayed");
		}
	}

	@Test(description = "AT-110768,AT-110769,AT-110771,AT-110772,AT-110743:Verify by default the current month transaction must be shown and on clicking on bar by user transaction should get updated accordingly", groups = {
			"DesktopBrowsers" }, priority = 26, dependsOnMethods = {
					"VerifyNewAddCatCloseIconPresent" }, enabled = true)
	public void VerifyCurrentTransactionpresent() throws Exception {

		SeleniumUtil.click(bud_TableRevamp.VerifyAccordianIcon().get(0));
		SeleniumUtil.click(bud_TableRevamp.TransRowWrap());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(bud_TableRevamp.TransRowWrapEditTextField());
		bud_TableRevamp.TransRowWrapEditTextField().clear();
		bud_TableRevamp.TransRowWrapEditTextField().sendKeys("Travel Expense");
		String EditedTransDesc = "Travel Expense";
		SeleniumUtil.click(bud_TableRevamp.TransRowWrapEditText_SaveBtn());
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(bud_TableRevamp.TransRowWrap().getText().contains(EditedTransDesc),
				"Not Edited Transaction Description");

	}

	@Test(description = "AT-110772,AT-110773:Verify that under Income section only income related categories must be shown based on the income transaction ", groups = {
			"DesktopBrowsers" }, priority = 27, dependsOnMethods = {
					"VerifyCurrentTransactionpresent" }, enabled = true)
	public void verifyIncomeFeatures() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {

			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}

		List<WebElement> el = SeleniumUtil.getWebElements(d, "BudgetCategories_List_BS", "Budget", null);

		List<String> actualElementVal = new LinkedList<String>();
		for (WebElement actualElement : el) {
			actualElementVal.add(actualElement.getText().trim());
		}
		List<String> expectedValue = FunctionUtil.splitLabelValueAsList("Income_Categories_List", ",");
		for (int i = 13; i < expectedValue.size(); i++) {
			Assert.assertEquals(actualElementVal.get(i), expectedValue.get(i), "Verify in that category list");
		}
	}

	@Test(description = "AT-110775:Verify that Budget amount popup which is shown for Income section must have the same features as seen for Expense section", groups = {
			"DesktopBrowsers" }, priority = 28, dependsOnMethods = { "verifyIncomeFeatures" }, enabled = true)
	public void addNewIncomeCategory() throws Exception {

		SeleniumUtil.click(bud_TableRevamp.AddNewIncomeCat());
		SeleniumUtil.waitForPageToLoad();
		String defaultCategory = bud_TableRevamp.DefaultCatName().getText();
		logger.info("Default Categroy name is >>>>" + defaultCategory);
		SeleniumUtil.click(bud_TableRevamp.IncomeCatListinDDIcon());
		String firstCategoryName = bud_TableRevamp.IncomeCatListinDDFirstCategory().getText();

		logger.info("First selected Categroy name in the dropdown list >>>>" + firstCategoryName);
		Assert.assertEquals(defaultCategory, firstCategoryName, "Verify in that category list");

	}

	@Test(description = "AT-110775,AT-110776,AT-110777,AT-110778:Verify that if user click on the Add button, without entering any amount. no action must be performed but, an error message to be shown here. ", groups = {
			"DesktopBrowsers" }, priority = 29, dependsOnMethods = { "addNewIncomeCategory" }, enabled = true)
	public void verifyAddNewWithoutAmtError() throws Exception {

		SeleniumUtil.click(bud_TableRevamp.IncomeCatListinDDFirstCategory());
		SeleniumUtil.waitForPageToLoad();
		bud_TableRevamp.EditIncomeTextField().clear();
		bud_TableRevamp.EditIncomeTextField().sendKeys("");
		SeleniumUtil.click(bud_TableRevamp.AddNewAddBtn());
		SeleniumUtil.waitForPageToLoad(4000);

		logger.info("Error Message is displayed if the user adding without Amount field");
		Assert.assertEquals(bud_TableRevamp.AddCatErrorMsg().getText(),
				PropsUtil.getDataPropertyValue("AddCatWithoutAmtFieldErrorMsg"));

	}

	@Test(description = "AT-110751:Verify that in the Category dropdown list only the category name which are not used for budget calculation previously must be shown ", groups = {
			"DesktopBrowsers" }, priority = 30, dependsOnMethods = { "verifyAddNewWithoutAmtError" }, enabled = true)
	public void newSpendCatListNotinPrevious_BudgetCat() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {

			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}
		SeleniumUtil.click(bud_TableRevamp.AddNewIncomeCat());
		SeleniumUtil.waitForPageToLoad();
		String defaultCategory = bud_TableRevamp.DefaultCatName().getText();
		logger.info("Default Categroy name is >>>>" + defaultCategory);
		SeleniumUtil.click(bud_TableRevamp.IncomeCatListinDDIcon());
		SeleniumUtil.click(bud_TableRevamp.IncomeCatListinDDFirstCategory());
		SeleniumUtil.waitForPageToLoad(6000);
		SeleniumUtil.click(bud_TableRevamp.IncomeCatListinDDFirstCategory());

		SeleniumUtil.click(bud_TableRevamp.AddNewCatAmtTextField());
		SeleniumUtil.waitForPageToLoad(8000);
		bud_TableRevamp.AddNewCatAmtTextField().clear();
		bud_TableRevamp.AddNewCatAmtTextField().sendKeys("2000");
		SeleniumUtil.click(bud_TableRevamp.AddNewAddBtn());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertFalse(defaultCategory.contains(PropsUtil.getDataPropertyValue("Budget_Categories_List")));
	}

	@Test(description = "AT-110765:Verify that if there is no average amount then 0 will be shown in the bar chart", groups = {
			"DesktopBrowsers" }, priority = 31, dependsOnMethods = {
					"newSpendCatListNotinPrevious_BudgetCat" }, enabled = true)
	public void verifyAverageAmountBarChart() throws Exception {

		SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(bud_TableRevamp.verifyEditGraph_AvgSpendingText().isDisplayed(), "Verify in Chart");
	}

	@Test(description = "AT-110779,AT-110780,AT-110781,AT-110782,AT-110783,AT-110784,AT-110785,AT-110786,AT-110787,AT-110742:Verify by default the current month transaction must be shown and on clicking on bar by user transaction should get updated accordingly", groups = {
			"DesktopBrowsers" }, priority = 32, dependsOnMethods = { "addNewIncomeCategory" }, enabled = true)
	public void verifyCurrentTransaction() throws Exception {

		SeleniumUtil.click(bud_TableRevamp.EditTextBoxClseIcon());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(10));
		SeleniumUtil.waitForPageToLoad();

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(bud_TableRevamp.EditSumHeader().isDisplayed());

		}
		Assert.assertTrue(bud_TableRevamp.ApplyBtn().isDisplayed());
		SeleniumUtil.click(bud_TableRevamp.EditBudgetAmtField());

		SeleniumUtil.waitForPageToLoad(4000);

		bud_TableRevamp.EditBudgetAmtField().clear();
		bud_TableRevamp.EditBudgetAmtField().sendKeys("2222");
		Assert.assertTrue(bud_TableRevamp.ApplyBtn().isDisplayed());

		Assert.assertTrue(bud_TableRevamp.AvgEarningPast3monthsTextinBarchart().isDisplayed());
		Assert.assertTrue(bud_TableRevamp.EditGraph_Legend().isDisplayed());
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(bud_TableRevamp.EditTextBoxClseIcon().isDisplayed());
		}
	}

	@Test(description = "AT-110788,AT-110789,AT-110790,AT-110792,AT-110800,AT-110801,AT-110767,AT-110791:Verify that if user edit the budget Amount and don’t  click on apply but, click on another accordion The popup must get closed and updated data will not be saved ", groups = {
			"DesktopBrowsers" }, priority = 33, dependsOnMethods = { "addNewIncomeCategory" }, enabled = true)
	public void verifyAccordion() throws Exception {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			SeleniumUtil.click(bud_TableRevamp.EditTextBoxClseIcon());
			SeleniumUtil.waitForPageToLoad(4000);
		}
		SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(10));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(bud_TableRevamp.VerifyAccordianIcon().get(11));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(bud_TableRevamp.BudgetAmtColumnList().get(10));
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(bud_TableRevamp.verifyEditDeleteBtn().isDisplayed());

	}

	@Test(description = "T-110792,AT-110793,AT-110794:Verify that once user click on the Delete option, Deleted category Budget confirmation pop up option must be shown to user ", groups = {
			"DesktopBrowsers" }, priority = 34, dependsOnMethods = { "verifyAccordion" }, enabled = true)
	public void verifyDeleteCategory() throws Exception {
		SeleniumUtil.click(bud_TableRevamp.DeleteCatBtn());
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(bud_TableRevamp.BudCatDeletePopupCloseIcon().isDisplayed());
			Assert.assertEquals(bud_TableRevamp.BudCategoryDeleteMessageInHeader().getText().trim(),
					PropsUtil.getDataPropertyValue("DeleteCategoryHeaderMessage"));
			SeleniumUtil.click(bud_TableRevamp.BudCategoryDeleteConfirmBtn());
			SeleniumUtil.waitForPageToLoad();
		} else {
			Assert.assertEquals(bud_TableRevamp.BudCategoryDeleteMessageInHeader_mob().getText().trim(),
					PropsUtil.getDataPropertyValue("DeleteCategoryHeaderMessage"));
			SeleniumUtil.click(bud_TableRevamp.BudCategoryDeleteConfirmBtn());
			SeleniumUtil.waitForPageToLoad();
		}
		List<WebElement> budgetCategoriesList = bud_TableRevamp.BudgetCategoriesLists();
		String categoriesname = bud_TableRevamp.WantsNewlyAddedCateg().getText();
		for (int i = 0; i < budgetCategoriesList.size(); i++) {
			logger.info("Verifying  on Category Names");
			Assert.assertFalse(categoriesname.contains(PropsUtil.getDataPropertyValue("DeletedCat")));
		}
	}

	@Test(description = "AT-110795,AT-110796,AT-110797,AT-110798,AT-110799:Verify that once user click on the Delete option, Deleted category Budget confirmation pop up option must be shown to user ", groups = {
			"DesktopBrowsers" }, priority = 35, dependsOnMethods = { "verifyDeleteCategory" }, enabled = true)
	public void verifyDeleteOption() throws Exception {
		SeleniumUtil.click(bud_TableRevamp.MoreBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(bud_TableRevamp.DeleteBudgetBtn());
		logger.info("Cancel Button is displaying");
		Assert.assertTrue(bud_TableRevamp.CancelBudConfirm().isDisplayed());
		SeleniumUtil.click(bud_TableRevamp.DeleteConfirm());
		SeleniumUtil.waitForPageToLoad(5000);
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(bud_TableRevamp.BackBtntoBudget_Mob());
			SeleniumUtil.waitForPageToLoad(5000);

		}
		Assert.assertTrue(bud_TableRevamp.CreateBudgetHeader().isDisplayed(), "Create Budget Header is displayed");
	}
}
