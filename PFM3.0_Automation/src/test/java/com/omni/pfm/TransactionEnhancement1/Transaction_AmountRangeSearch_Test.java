/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_DashBoard_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AmountRangeSearch_Test extends TestBase {

	AddToCalendar_Transaction_Loc AddToCalendar;
	Transaction_Account_Integration_Loc acct_Tran;
	WebDriver webDriver = null;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_AmountRangeSearch_Test.class);
	public static String userName = "";
	Transaction_Search_Loc search;
	Transaction_DashBoard_Integration_Loc DashBoard;
	Transaction_Categorization_Rules_Loc rule;
	Add_Manual_Transaction_Loc manual;
	AccountAddition accountAdd;
	Transaction_Tag_Loc tag;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Amount Search");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		acct_Tran = new Transaction_Account_Integration_Loc(d);
		search = new Transaction_Search_Loc(d);
		DashBoard = new Transaction_DashBoard_Integration_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);
		accountAdd = new AccountAddition();
		tag = new Transaction_Tag_Loc(d);
	}
	
	@Test(priority = 1)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "addcal.bank1", "bank1");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68623,AT-68681,AT-68691:verify amount search all field", priority = 2, groups = { "Smoke" })

	public void searchTransactionWithfromAmount() throws Exception {
		// Verify ‘CLEAR’ button is beside ‘Amount Range’ and in blue colour

		Assert.assertTrue(search.clearAmount().isDisplayed());
		Assert.assertEquals(search.clearAmount().getCssValue("color"),
				PropsUtil.getDataPropertyValue("AmountRangeClearButtonColor"));

		Assert.assertEquals(search.AmountRangeHeading().getText().trim(),
				PropsUtil.getDataPropertyValue("AmountRangeFilterHeader"), "amount range header should be displayed");
		Assert.assertEquals(search.amountFromField().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("AmountRangeFromField"), "amount range From field should be displayed");
		Assert.assertEquals(search.amountToField().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("AmountRangeToField"), "amount range To field should be displayed");

	}

	@Test(description = "AT-68682,AT-68685:verify amount search with from field", priority = 3, groups = {
			"Smoke" }, dependsOnMethods = "searchTransactionWithfromAmount")

	public void SaerchWithFromField() throws Exception {
		// Verify if user enters any amount in only 'From' field then the results should
		// be all transactions with amount greater than or equal to amount entered in
		// ‘From’ field
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertFromAmountonly(PropsUtil.getDataPropertyValue("AmountSearchFrom")));

	}

	@Test(description = "AT-68618:verify reset button lable", priority = 4, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithFromField")

	public void verifyResetButton() throws Exception {
		Assert.assertEquals(tag.reset().getText().trim(), PropsUtil.getDataPropertyValue("transactionResetText"),
				"after filter reset should displayed");

	}

	@Test(description = "AT-68683,AT-68685:verify amount search with to field", priority = 5, groups = {
			"Smoke" }, dependsOnMethods = "verifyResetButton")
	public void SaerchWithtoField() throws Exception {
		// Verify if user enters any amount in only 'To' field then the results should
		// be all transactions with amount 0 to amount entered in ‘To’ field
		SeleniumUtil.click(search.clearAmount());
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertToAmountonly(PropsUtil.getDataPropertyValue("AmountSearchTo")));

	}

	@Test(description = "AT-68620:verify amount filter reset", priority = 6, dependsOnMethods = "SaerchWithtoField")
	public void verifyResetFilter() {
		SeleniumUtil.click(tag.reset());
		Assert.assertEquals(search.amountToField().getText(), "", " amount filter from field is not displayed");
		Assert.assertEquals(search.amountToField().getText(), "", "amount filter to field is not displayed");

	}

	@Test(description = "AT-68689:verify amount filter after clearing from and to field", priority = 7, groups = {
			"Smoke" }, dependsOnMethods = "verifyResetFilter")
	public void checToFromkAfterclickOnClear() throws Exception {
		// Verify if user clicks on 'CLEAR' then the amount range entered should be
		// cleared and transactions refreshed to User selected Filter values
		SeleniumUtil.click(search.clearAmount());
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(search.amountToField().getText(), "");
		Assert.assertEquals(search.amountToField().getText(), "");

	}

	@Test(description = "AT-68690:verify transaction after clearing amount filter", priority = 8, groups = {
			"Smoke" }, dependsOnMethods = "checToFromkAfterclickOnClear")
	public void checkTransactionAfterclickOnClear() throws Exception {
		// Verify if User has not altered any filter values and clicks on ‘CLEAR’, the
		// transactions are refreshed to default
		Assert.assertTrue(search.AssertAllAmount(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
				PropsUtil.getDataPropertyValue("AmountSearchTo")));
	}

	@Test(description = "verify amount search filter when navigated from dashboard ", priority = 9, groups = {
			"Smoke" })
	public void searchTransactionWithfromAmountDashBoard() throws Exception {
		//// Verify ‘CLEAR’ button is beside ‘Amount Range’ and in blue colour
		SeleniumUtil.refresh(d);
		DashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("DashBoard_Account_Cash"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(search.clearAmount().isDisplayed());

	}

	@Test(description = "verify amount search filter with from field when navigated from dashboard ", priority = 10, groups = {
			"Smoke" }, dependsOnMethods = "searchTransactionWithfromAmountDashBoard")

	public void SaerchWithFromFieldDashBoard() throws Exception {
		// Verify if user enters any amount in only 'From' field then the results should
		// be all transactions with amount greater than or equal to amount entered in
		// ‘From’ field
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertFromAmountonly(PropsUtil.getDataPropertyValue("AmountSearchFrom")));
	}

	@Test(description = "verify amount search filter with to field when navigated from dashboard ", priority = 11, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithFromFieldDashBoard")
	public void SaerchWithtoFieldDashBoard() throws Exception {
		// Verify if user enters any amount in only 'To' field then the results should
		// be all transactions with amount 0 to amount entered in ‘To’ field
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(10000);

		Assert.assertTrue(search.AssertToAmountonly(PropsUtil.getDataPropertyValue("AmountSearchTo")));

	}

	@Test(description = "verify amount search filter clear when navigated from dashboard ", priority = 12, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithtoFieldDashBoard")
	public void checToFromkAfterclickOnClearDashBoard() throws Exception {
		// Verify if user clicks on 'CLEAR' then the amount range entered should be
		// cleared and transactions refreshed to User selected Filter values
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(search.amountToField().getText(), "");
		Assert.assertEquals(search.amountToField().getText(), "");

	}

	@Test(description = "verify transaction after clear amount search filter when navigated from dashboard ", priority = 13, groups = {
			"Smoke" }, dependsOnMethods = "checToFromkAfterclickOnClearDashBoard")
	public void checkTransactionAfterclickOnClearDashBoard() throws Exception {
		// Verify if User has not altered any filter values and clicks on ‘CLEAR’, the
		// transactions are refreshed to default
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertAllAmount(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
				PropsUtil.getDataPropertyValue("AmountSearchTo")));

	}

	@Test(description = "verify amount search filter when navigated from account ", priority = 14, groups = { "Smoke" })
	public void searchTransactionWithfromAmountAccount() throws Exception {
		//// Verify ‘CLEAR’ button is beside ‘Amount Range’ and in blue colour
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(acct_Tran.Accountrows().get(0));
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(search.clearAmount().isDisplayed());

	}

	@Test(description = "verify amount search filter with from field when navigated from account ", priority = 15, groups = {
			"Smoke" }, dependsOnMethods = "searchTransactionWithfromAmountAccount")

	public void SaerchWithFromFieldAccount() throws Exception {
		// Verify if user enters any amount in only 'From' field then the results should
		// be all transactions with amount greater than or equal to amount entered in
		// ‘From’ field
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertFromAmountonly(PropsUtil.getDataPropertyValue("AmountSearchFrom")));
	}

	@Test(description = "verify amount search filter with to field when navigated from account ", priority = 16, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithFromFieldAccount")
	public void SaerchWithtoFieldAccount() throws Exception {
		// Verify if user enters any amount in only 'To' field then the results should
		// be all transactions with amount 0 to amount entered in ‘To’ field
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertToAmountonly(PropsUtil.getDataPropertyValue("AmountSearchTo")));

	}

	@Test(description = "verify clear amount search filter when navigated from account ", priority = 17, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithtoFieldAccount")
	public void checToFromkAfterclickOnClearAccount() throws Exception {
		// Verify if user clicks on 'CLEAR' then the amount range entered should be
		// cleared and transactions refreshed to User selected Filter values

		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(search.amountToField().getText(), "");
		Assert.assertEquals(search.amountToField().getText(), "");

	}

	@Test(description = "verify transaction after clear amount search filter when navigated from account ", priority = 18, groups = {
			"Smoke" }, dependsOnMethods = "checToFromkAfterclickOnClearAccount")
	public void checkTransactionAfterclickOnClearAccount() throws Exception {
		// Verify if User has not altered any filter values and clicks on ‘CLEAR’, the
		// transactions are refreshed to default
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(search.AssertAllAmount(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
				PropsUtil.getDataPropertyValue("AmountSearchTo")));
	}

	@Test(description = "verify amount search filter when navigated from categorization rule ", priority = 19, groups = {
			"Smoke" })
	public void searchTransactionWithfromAmountCatg() throws Exception {
		//// Verify ‘CLEAR’ button is beside ‘Amount Range’ and in blue colour
		SeleniumUtil.click(manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 7, 16, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("AmountSearchTo"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 7, 16, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 7, 16, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(3000);

		PageParser.forceNavigate("CategorzationRules", d);

		SeleniumUtil.waitForPageToLoad(5000);
		try {
			SeleniumUtil.click(rule.uncat());
			SeleniumUtil.waitForPageToLoad(5000);
		} catch (Exception e) {
			SeleniumUtil.click(rule.newuncat());
			SeleniumUtil.waitForPageToLoad(5000);

		}
		Assert.assertTrue(search.clearAmount().isDisplayed());

	}

	@Test(description = "verify amount search filter  with from field when navigated from categorization rule ", priority = 20, groups = {
			"Smoke" }, dependsOnMethods = "searchTransactionWithfromAmountCatg")

	public void SaerchWithFromFieldAccountCatg() throws Exception {
		// Verify if user enters any amount in only 'From' field then the results should
		// be all transactions with amount greater than or equal to amount entered in
		// ‘From’ field
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(10000);

		Assert.assertTrue(search.AssertFromAmountonly(PropsUtil.getDataPropertyValue("AmountSearchFrom")));

	}

	@Test(description = "verify amount search filter with To field when navigated from Categorization rule ", priority = 21, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithFromFieldAccountCatg")
	public void SaerchWithtoFieldAccountCatg() throws Exception {
		// Verify if user enters any amount in only 'To' field then the results should
		// be all transactions with amount 0 to amount entered in ‘To’ field
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(10000);

		Assert.assertTrue(search.AssertToAmountonly(PropsUtil.getDataPropertyValue("AmountSearchTo")));

	}

	@Test(description = "verify amount search filter field after clear amount filter when navigated from Catgeorization rule ", priority = 23, groups = {
			"Smoke" }, dependsOnMethods = "SaerchWithtoFieldAccountCatg")
	public void checToFromkAfterclickOnClearAccountCatg() throws Exception {
		// Verify if user clicks on 'CLEAR' then the amount range entered should be
		// cleared and transactions refreshed to User selected Filter values
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(search.amountToField().getText(), "");
		Assert.assertEquals(search.amountToField().getText(), "");

	}

	@Test(description = "verify transaction after clear amount search filter when navigated from dashboard ", priority = 24, groups = {
			"Smoke" }, dependsOnMethods = "checToFromkAfterclickOnClearAccountCatg")
	public void checkTransactionAfterclickOnClearAccountCatg() throws Exception {
		// Verify if User has not altered any filter values and clicks on ‘CLEAR’, the
		// transactions are refreshed to default

		Assert.assertTrue(search.AssertAllAmount(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
				PropsUtil.getDataPropertyValue("AmountSearchTo")));
	}

	@Test(description = "AT-68684:empty field error message", priority = 25, groups = { "Smoke" })
	public void verifyFromFiledGreaterThanToField() throws Exception {
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom1"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo1"));
		SeleniumUtil.click(search.amountApplyButton());
		Assert.assertEquals(search.amountRangeError().getText(), PropsUtil.getDataPropertyValue("AmountRangeError"),
				"Invalid Amount Range error message should displayed");

	}

	@Test(description = "AT-68688:verify form filed max value", priority = 26, groups = {
			"Smoke" }, dependsOnMethods = "verifyFromFiledGreaterThanToField")
	public void verifyFormField11() throws Exception {
		search.amountFromField().clear();
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom2"));
		search.amountFromField().sendKeys(Keys.TAB);
		boolean amountlenth11True = false;
		System.out.println(search.amountFromField().getAttribute("value"));
		if (search.amountFromField().getAttribute("value").length() == 17) {
			amountlenth11True = true;
		}
		Assert.assertTrue(amountlenth11True);
		Assert.assertEquals(search.amountFromField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("AmountSearchFrom16Char"), "allowing to enter more than 11 char");

	}

	@Test(description = "AT-68688:verify to filed max value", priority = 27, groups = {
			"Smoke" }, dependsOnMethods = "verifyFormField11")
	public void verifyToField11() throws Exception {
		search.amountToField().clear();
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchFrom2"));
		search.amountToField().sendKeys(Keys.TAB);
		System.out.println(search.amountToField().getAttribute("value"));
		boolean amountlenth11True = false;
		if (search.amountToField().getAttribute("value").length() == 17) {
			amountlenth11True = true;
		}
		Assert.assertTrue(amountlenth11True);
		Assert.assertEquals(search.amountToField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("AmountSearchFrom16Char"), "allowing to enter more than 11 char");
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
