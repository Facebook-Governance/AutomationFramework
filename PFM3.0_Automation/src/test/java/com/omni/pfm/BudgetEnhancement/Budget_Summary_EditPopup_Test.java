/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.BudgetEnhancement;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Summary_EditPopup_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Summary_EditPopup_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_Summary_EditPopup_Test.class);

	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_EditPopup_Loc budget_Edit;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Budget");
		budget_Edit = new Budget_Summary_EditPopup_Loc(d);

	}

	@Test(description = "AT-69420:Verify Login Happens Successfully", groups = {
			"DesktopBrowsers" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accAddition.linkAccount();

		logger.info(">>>>> Adding a dag site to PFM 3.0..");
		accAddition.addAggregatedAccount("bbudget.site16441.3", "site16441.3");

		SeleniumUtil.refresh(d);
		logger.info(">>>>> Navigating to Budget..");
		PageParser.forceNavigate("Budget", d);
	}

	@Test(description = "AT-69443,AT-69444,AT-69754,AT-69736:Verify in Budget FinApp page Create Group is shown as a dropdown for the first time user ", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void verifyCreateGroup() throws Exception {

		logger.info(">>>>> Click on  the GetStarted button");
		SeleniumUtil.click(budget_Edit.GetStartedBtn());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_Edit.createGroupDD());

		SeleniumUtil.click(budget_Edit.createGroupBtn());
		SeleniumUtil.click(budget_Edit.Group_name());
		SeleniumUtil.waitForPageToLoad();

		budget_Edit.Group_name().sendKeys("My Budget1");
		SeleniumUtil.click(budget_Edit.NextBtn());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_Edit.DoneBtn());
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {

			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		logger.info(">>>>> Click on  the Budget Summary");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(budget_Edit.BudgetSummary().isDisplayed());
		}
	}

	@Test(description = "AT-109711:If the user clicks on the amount field on a category, the edit pop up should open ", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyEditTextBox() throws Exception {

		logger.info(">>>>> Verifying the Budget Summary Edit TextBox field");
		SeleniumUtil.click(budget_Edit.EditTextBox().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Edit.AmountField());

		budget_Edit.AmountField().clear();

		logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
		budget_Edit.AmountField().sendKeys("100");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			logger.info(">>>>> Verifying the Budget Summary Edit Header");
			Assert.assertEquals(budget_Edit.EditSumHeader().getText(),
					PropsUtil.getDataPropertyValue("Edit_BudHeader_Bud"), "verify Edit this budget text is displaying");
		}
	}

	@Test(description = "AT-109714:If the user clicks on a spending category, the text should be Spent.", groups = {
			"DesktopBrowsers" }, priority = 4, dependsOnMethods = { "verifyEditTextBox" }, enabled = true)
	public void verifySpent() throws Exception {

		logger.info(">>>>> Verifying Spent is displayed");
		Assert.assertEquals(budget_Edit.VerifySpent().getText(), PropsUtil.getDataPropertyValue("Spent_Text_Bud"),
				"verify Spent text is displaying");

	}

	@Test(description = "AT-109729,AT-109727:If the user clicks on 'Cancel', the page should take the user back to the edit budget pop up.", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "verifySpent" }, enabled = true)
	public void verifyCancelBtn() throws Exception {
		SeleniumUtil.click(budget_Edit.verifyEditGraphDeleteBtn());
		SeleniumUtil.click(budget_Edit.cancelBtn());
		
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertEquals(budget_Edit.EditSumHeader().getText(),
					PropsUtil.getDataPropertyValue("Edit_BudHeader_Bud"), "verify Edit this budget text is displaying");
		}
	}

	@Test(description = "AT-109710,AT-109715,AT-109743,AT-109716:If the user clicks on an income category, the text should be Earned", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "verifyCancelBtn" }, enabled = true)
	public void verifyEarned() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		} else {
			logger.info(">>>>> Verifying Earned Text is displayed ");
			SeleniumUtil.click(budget_Edit.Income());
			SeleniumUtil.waitForPageToLoad();

			Assert.assertEquals(budget_Edit.EarnedText().getText(), PropsUtil.getDataPropertyValue("Earned_Text_Bud"),
					"verify Earned text is displaying");
			Assert.assertEquals(budget_Edit.OverText().getText(), PropsUtil.getDataPropertyValue("Over_Text_Bud"),
					"verify Over text is displaying");
			logger.info("user has opened the pop up from an income category, the graphs should be in Blue.");
			Assert.assertEquals(budget_Edit.verifyIncome_EditGraphBar().size(), 5); 
		}
	}

	@Test(description = "AT-109717:If the user's budgeted amount - the spent amount is greater than 0, the text should be 'Left' ", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "verifyEarned" }, enabled = true)
	public void verifyLeft() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
		} else {
			SeleniumUtil.click(budget_Edit.EditTextClse());
		}
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		logger.info(">>>>> Verifying Left Text is displayed ");
		SeleniumUtil.click(budget_Edit.EditTextBox().get(2));
		Assert.assertEquals(budget_Edit.LeftText().getText(), PropsUtil.getDataPropertyValue("Left_Text_Bud"),
				"verify Over text is displaying");
	}

	@Test(description = "AT-109718:If the user's budgeted amount - the spent amount is greater or equal to 0, the text colour of the amount should be black", groups = {
			"DesktopBrowsers" }, priority = 8, dependsOnMethods = { "verifyLeft" }, enabled = true)
	public void bdgtAmtInBlack() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
		} else {
			SeleniumUtil.click(budget_Edit.EditTextClse());
		}

		logger.info(">>>>> Verifying APPLY button update the amount which edit ");
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		String blackColor = budget_Edit.AmtField_Color().get(2).getCssValue("color");
		logger.info("Color is ========>" + blackColor);
		Assert.assertEquals(blackColor, PropsUtil.getDataPropertyValue("Black_color"));
	}

	@Test(description = "AT-109742,AT-109719:If the user's budgeted amount - the spent amount is lesser than 0, the text colour of the amount should be in red", groups = {
			"DesktopBrowsers" }, priority = 9, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void bdgtAmtInRed() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());

		}
		logger.info(">>>>> Verifying APPLY button update the amount which edit ");
		SeleniumUtil.click(budget_Edit.EditTextBox().get(1));

		String redColor = budget_Edit.AmtField_Color().get(0).getCssValue("color");
		logger.info("Color is ========>" + redColor);
		Assert.assertEquals(redColor, PropsUtil.getDataPropertyValue("Red_color"));

	}

	@Test(description = "AT-109720:The user should have an Apply button on the pop up", groups = {
			"DesktopBrowsers" }, priority = 10, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyApplyBtn() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		logger.info(">>>>> Verifying APPLY button is displayed ");
		SeleniumUtil.click(budget_Edit.Income());
		Assert.assertEquals(budget_Edit.ApplyBtn().getText(), PropsUtil.getDataPropertyValue("Apply_Text_Bud"),
				"verify APPLY Button is displaying");

	}

	@Test(description = "AT-109721,AT-109712:On clicking on the Apply button, if the budgeted text amount has no errors, the values should be saved and updated", groups = {
			"DesktopBrowsers" }, priority = 11, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyEditWithAmt() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator"))

		{
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		} else {
			logger.info(">>>>> Verifying APPLY button update the amount which edit ");
			SeleniumUtil.click(budget_Edit.EditTextBox().get(1));
			SeleniumUtil.waitForPageToLoad();
			budget_Edit.AmountField().clear();

			logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
			SeleniumUtil.click(budget_Edit.AmountField());

			budget_Edit.AmountField().sendKeys("1,000.00");
			SeleniumUtil.click(budget_Edit.ApplyBtn());
			SeleniumUtil.waitForPageToLoad();

			if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
				Assert.assertEquals(budget_Edit.EditTextBox().get(1).getText(),
						PropsUtil.getDataPropertyValue("UpdatedAmt_Bud"), "verify Edited Amount is updated");
			}
		}
	}

	@Test(description = "AT-109722:If the user enters anything but numerals in the text box, the finapp should throw an error", groups = {
			"DesktopBrowsers" }, priority = 12, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyEditWithNumerals() throws Exception {// Pending
		logger.info(">>>>> Verifying APPLY button update the amount which edit ");
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		SeleniumUtil.click(budget_Edit.EditTextBox().get(1));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_Edit.AmountField());
		budget_Edit.AmountField().clear();

		logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
		budget_Edit.AmountField().sendKeys("abcd");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budget_Edit.Budget_Edit_errorMessage().getText(),
				PropsUtil.getDataPropertyValue("Edit_ErrorMsg_numerals"));
	}

	@Test(description = "AT-109723,AT-109713:If the user enters  0 in the budgeted amount text field, the finapp should throw an error message", groups = {
			"DesktopBrowsers" }, priority = 13, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyEditWithZero() throws Exception {// Pending

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		logger.info(">>>>> Verifying APPLY button update the amount which edit ");
		SeleniumUtil.click(budget_Edit.EditTextBox().get(1));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_Edit.AmountField());
		budget_Edit.AmountField().clear();

		logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
		budget_Edit.AmountField().sendKeys("0");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(budget_Edit.Budget_Edit_errorMessage().getText(),
				PropsUtil.getDataPropertyValue("Edit_ErrorMsg_zero"));

	}

	@Test(description = "AT-109724:The budgeted amount should be in the 7,2 format", groups = {
			"DesktopBrowsers" }, priority = 14, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyEditWithMorethan11_digits() throws Exception {// Pending
		logger.info(">>>>> Verifying the amount field which edit with more than 11 digits ");
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		SeleniumUtil.click(budget_Edit.EditTextBox().get(1));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Edit.AmountField());
		budget_Edit.AmountField().clear();

		logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
		budget_Edit.AmountField().sendKeys("123434565678989764432");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(budget_Edit.Budget_Edit_errorMessage().getText(),
				PropsUtil.getDataPropertyValue("Edit_ErrorMsg_11digits"));

	}

	@Test(description = "AT-109732,AT-109737,AT-109735,AT-109734,AT-109738,AT-109733,AT-109730,AT-109735:On the graph, there should be 2 bars. One indicating his Budgeted amount for those months, and the other indicating how much he actually spent in those categories.", groups = {
			"DesktopBrowsers" }, priority = 15, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyBudgetEditGraph() throws Exception {
		SoftAssert sa = new SoftAssert();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		} else {
			SeleniumUtil.click(budget_Edit.AmtField_Color().get(0));
			SeleniumUtil.waitForPageToLoad();

			sa.assertTrue(budget_Edit.verifyEditGraph().isDisplayed());
			sa.assertEquals(budget_Edit.verifyEditGraph_BudgetBar().size(), 5); // Including indicator
			sa.assertEquals(budget_Edit.verifyEditGraph_ActualSpentBar().size(), 5); // Including indicator
			sa.assertTrue(budget_Edit.verifyEditGraph_AvgSpendingText().isDisplayed());
			sa.assertTrue(budget_Edit.verifyEditGraph_AvgSpendingLine().isDisplayed());
			sa.assertTrue(budget_Edit.verifyEditGraph_Legend().isDisplayed());
			sa.assertTrue(budget_Edit.verifyEditGraph_BudgetText().isDisplayed());
			sa.assertTrue(budget_Edit.verifyEditGraph_ActualText().isDisplayed());
			sa.assertEquals(budget_Edit.verifyEditGraph_PreferredCurrency().getText(),
					PropsUtil.getDataPropertyValue("EditBudget_PreferredCurr"));
			sa.assertAll();
		}
	}

	@Test(description = "AT-109739:On clicking anywhere outside the popup, the pop up should close.", groups = {
			"DesktopBrowsers" }, priority = 16, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyClosePopUp() throws Exception {
		SoftAssert sa = new SoftAssert();
		SeleniumUtil.click(budget_Edit.EditTextClse());

		SeleniumUtil.click(budget_Edit.AmtField_Color().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			sa.assertTrue(budget_Edit.EditSumHeader().isDisplayed());
		}
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		} else {
			SeleniumUtil.click(budget_Edit.verifyBudgetCategory_ExpandIcon());
			sa.assertNull(budget_Edit.EditSumHeader());
			SeleniumUtil.click(budget_Edit.verifyBudgetCategory_ExpandIcon());
			sa.assertAll();
		}
	}

	@Test(description = "AT-109731 : On the graph, the user should see the last 3 plus the current months data for that particular category.", groups = {
			"DesktopBrowsers" }, priority = 17, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyLastThreeMonthsInEditGraph() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());

			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}

		SeleniumUtil.click(budget_Edit.AmtField_Color().get(0));
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

	@Test(description = "AT-109748:The graphs should not be clickable.", groups = {
			"DesktopBrowsers" }, priority = 18, dependsOnMethods = {
					"verifyLastThreeMonthsInEditGraph" }, enabled = true)
	public void verifyGraphShouldntBeClickable() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			SeleniumUtil.waitForPageToLoad();
			Assert.assertFalse(budget_Edit.verifyGraphIsClickable());
		}
	}

	@Test(description = "AT-109741,AT-109740:On clicking on the 'X' button, the pop up should close and the user's values should not be saved. ", groups = {
			"DesktopBrowsers" }, priority = 19, dependsOnMethods = {
					"verifyLastThreeMonthsInEditGraph" }, enabled = true)
	public void verifyEditedAmtWithoutSaving() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());

			SeleniumUtil.click(budget_Edit.viewDetailsBtn());

		}
		
		else {
			SeleniumUtil.click(budget_Edit.EditTextClse());
		}
		
		SeleniumUtil.click(budget_Edit.EditTextBox().get(0));
		String actual = budget_Edit.AmountField().getText();

		logger.info("Actual amount in Amount field is " + actual);
		SeleniumUtil.click(budget_Edit.AmountField());

		WebElement ele = budget_Edit.AmountField();
		SeleniumUtil.click(ele);
		ele.clear();

		logger.info(">>>>> Entering value for Spent in Edit Budget Text field");
		ele.sendKeys("1000");

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.backBtnMob());
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budget_Edit.EditTextClse());
			SeleniumUtil.click(budget_Edit.EditTextBox().get(0));
			String expected = budget_Edit.AmountField().getText();

			logger.info("Expected amount in Amount field without saving the updated one is " + expected);
			if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

				Assert.assertEquals(actual, expected);
			}
		}
	}

	@Test(description = "AT-109745,AT-109726,AT-109747,AT-109728,AT-109746,AT-109744,AT-109725:user should have atleast 1 flexible spending category. ", groups = {
			"DesktopBrowsers" }, priority = 20, dependsOnMethods = {
					"verifyLastThreeMonthsInEditGraph" }, enabled = true)
	public void verifyFlexibleSpendingCategory() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}

		List<WebElement> categories = budget_Edit.verifyEditGraph_FlexibleSpendingCategory();
		int size = categories.size();
		logger.info("Size of the Flexible Spending Category is " + size);

		for (int i = 0; i < size; i++) {
			SeleniumUtil.click(categories.get(i));
			SeleniumUtil.click(budget_Edit.verifyEditGraphDeleteBtn());
			SeleniumUtil.click(budget_Edit.verifyDeleteBdgtCategoryBtn());
		}

		List<WebElement> billsCategories = budget_Edit.editGraph_BillsCategory();
		int billsSize = billsCategories.size();
		logger.info("Size of the Flexible Spending Category for Bills is " + billsSize);
		for (int i = 0; i < billsSize - 1; i++) {
			SeleniumUtil.click(billsCategories.get(i));
			SeleniumUtil.click(budget_Edit.verifyEditGraphDeleteBtn());
			SeleniumUtil.click(budget_Edit.verifyDeleteBdgtCategoryBtn());
		}
		SeleniumUtil.click(billsCategories.get(billsSize - 1));
		SeleniumUtil.click(budget_Edit.verifyEditGraphDeleteBtn());

		Assert.assertEquals(budget_Edit.lastCategoryDeleteMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("LastBudCategoryDeleteMsg"));
		SeleniumUtil.click(budget_Edit.budCategoryDeleteOkBtn());
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertEquals(budget_Edit.EditSumHeader().getText(),
					PropsUtil.getDataPropertyValue("Edit_BudHeader_Bud"), "verify Edit this budget text is displaying");
		}
	}

}
