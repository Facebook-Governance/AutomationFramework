/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.mobile.Transaction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AddManual_Transaction__Test extends TestBase {

	public Transaction_Filter_Loc transaction_Filter;
	String userName = "";
	String expectedClassificationValues[];

	CommonUtils utils;
	public Add_Manual_Transaction_Loc add_manual_transaction;

	public Series_Recurence_Transaction_Loc Series;
	public Transaction_Layout_Loc layout;
	// private AppiumDriver d;
	AddToCalendar_Transaction_Loc AddToCalendar;

	Series_Recurence_Transaction_Loc Series_Tranc_details;
	Aggregated_Transaction_Details_Loc Agg;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws InterruptedException {

		doInitialization("Add Manual Transaction");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		Series = new Series_Recurence_Transaction_Loc(d);

		Agg = new Aggregated_Transaction_Details_Loc(d);
		utils = new CommonUtils();

	}

	@Test(description = "Verify login happens successfully.", priority = 1, groups = { "Smoke" })

	public void login() throws Exception {

		if (!PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {
			SeleniumUtil.waitForPageToLoad(5000);
			PageParser.forceNavigate("Transaction", d);
			SeleniumUtil.waitForPageToLoad(5000);
		}
	}

	@Test(description = "AT-29561:TRA_01_01:verifing add manual Icon", priority = 2)
	void verifyAddManualTransactIcon() throws InterruptedException {
		// verifing add manual Icon
		// utils.switchToFrame(d);
		SeleniumUtil.waitForElement("BBT", 80);
		Assert.assertTrue(add_manual_transaction.addManualIcon().isDisplayed());
	}

	@Test(description = "AT-29561:TRA_01_02:verifying add manual link", priority = 3)
	void verifyAddManualTransactLink() throws InterruptedException {
		// verifying add manual link
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(add_manual_transaction.addManualLink().isDisplayed());
	}

	@Test(description = "AT-29561:TRA_01_03:Verify the Add a Transaction pop up window displayed", priority = 4)
	void verifyAddManualTransactPopHeader() throws InterruptedException {
		// Verify the Add a Transaction pop up window displayed after click on the "Add
		// a Transaction" link in transaction FinApp home page.
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.MobileMore().click();
			SeleniumUtil.waitForPageToLoad();
			add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();

		} else {
			//
			SeleniumUtil.click(add_manual_transaction.addManualIcon());
			SeleniumUtil.waitForPageToLoad(10000);
			Assert.assertEquals(add_manual_transaction.addManualHeader().getText(),
					PropsUtil.getDataPropertyValue("Header"));

		}
	}

	@Test(description = "AT-29562:TRA_01_04:Verify the Currency dropdown displayed", priority = 5, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyCurrencyDropDown() {
		// AT-29562 Verify the Currency dropdown displayed in the Add aTransaction
		// window
		Assert.assertTrue(add_manual_transaction.currencyDroDown().isDisplayed());

	}

	@Test(description = "AT-29563:TRA_01_05:Verify the by default USD currency symbol selected in the dropdown", priority = 6, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyCurrencyDropDownDefaultValue() {
		// Verify the by default USD currency symbol selected in the dropdown

		Assert.assertEquals(add_manual_transaction.currencyValue().getText(),
				PropsUtil.getDataPropertyValue("DefaultCurrency"));
	}

	@Test(description = "AT-29565:TRA_01_06:Verify the Amount Field displayed next to the Currency field", priority = 7, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyAmountField() {
		// Verify the "Amount Field" displayed next to the Currency field

		Assert.assertTrue(add_manual_transaction.amount().isDisplayed());

	}

	@Test(description = "AT-29566:TRA_01_07:Verify the transaction type withdrawal selected by default ", priority = 8, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void transactionTypeDefaultValue() {
		// Verify the transaction type withdrawal selected by default
		Assert.assertEquals(add_manual_transaction.TransactionType().getText(),
				PropsUtil.getDataPropertyValue("DefaultTraType"));

	}

	@Test(description = "AT-29567:TRA_01_08:Verify the Frequency text and dropdown field displayed ", priority = 9, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void frequencyTypeDisplyed() {
		// Verify the "Frequency" text and dropdown field displayed below the Accounts
		// field in the Add a Transaction pop up
		Assert.assertTrue(add_manual_transaction.frequencytext().isDisplayed());
	}

	@Test(description = "AT-29568:TRA_01_09:Verify by default One Time option selected by default", priority = 10, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void frequencyTypeDefaultValue() {
		// Verify by default "One Time" option selected by default in Frequency field
		// when invoked from transaction FinApp
		Assert.assertEquals(add_manual_transaction.frequencytext().getText(),
				PropsUtil.getDataPropertyValue("frequency"));
	}

	@Test(description = "AT-29570:TRA_01_10:Verify the Scheduled Date field displayed below the Frequency Field", priority = 11, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void ScheduleDateFieldDispaly() {
		// Verify the "Scheduled Date" field displayed below the Frequency Field
		Assert.assertTrue(add_manual_transaction.Schedule().isDisplayed());

	}

	@Test(description = "AT-29572:TRA_01_11:calendar icon is displying", priority = 12, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void ScheduleDateIconDispaly() {
		// calendar icon is displying
		Assert.assertTrue(add_manual_transaction.calendIcon().isDisplayed());
	}

	@Test(description = "AT-29572:TRA_01_12:Verify the Calendar displayed after click on the Calendar icon ", priority = 13, dependsOnMethods = "ScheduleDateIconDispaly")
	void ScheduleDateCalendarDispaly() {
		// Verify the Calendar displayed after click on the Calendar icon and click on
		// the date Field

		SeleniumUtil.click(add_manual_transaction.calendIcon());
		SeleniumUtil.waitForPageToLoad();
		boolean calendPopUpvalue = add_manual_transaction.calendPopUp().isDisplayed();
		SeleniumUtil.click(add_manual_transaction.calendIcon());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(calendPopUpvalue);
	}

	@Test(description = "AT-29573:TRA_01_13:Verify the End Date field displayed", priority = 14, dependsOnMethods = {
			"verifyAddManualTransactPopHeader", "frequencyTypeDefaultValue" })
	void EndDateField() {
		// Verify the "End Date" field displayed if user selects the option other than
		// One Time option in the Frequency field like "Weekly" "Every 2 Weeks""Monthly"
		// Etc..

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.frequencyDropDown());
		// add_manual_transaction.frequencyDropDown().click();
		SeleniumUtil.waitForPageToLoad(10000);
		System.out.println(add_manual_transaction.FrequencyList().size());
		SeleniumUtil.click(add_manual_transaction.FrequencyList().get(1));
		// add_manual_transaction.FrequencyList.get(1).click();
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(add_manual_transaction.endDate().isDisplayed());
	}

	@Test(description = "AT-29573:TRA_01_14:verify end date calander Icon", priority = 15, dependsOnMethods = {
			"EndDateField" })
	void EndDateFieldDateIcon() {
		// verify end date calander Icon
		Assert.assertTrue(add_manual_transaction.endDateIcon().isDisplayed());
	}

	@Test(description = "AT-29574:TRA_01_15:Verify the icon infi icon", priority = 16, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledInfiIcon() {
		// Verify the icon and text displayed below the Tags Field "Create custom tags
		// to track transaction related to specific events ,such as vacation or taxes"
		Assert.assertTrue(add_manual_transaction.infoIcon().isDisplayed());
	}

	@Test(description = "AT-29574:TRA_01_16:text displayed below the Tags Field Create custom tags to track transaction", priority = 17, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledInfiMessage() {
		// Verify the icon and text displayed below the Tags Field "Create custom tags
		// to track transaction related to specific events ,such as vacation or taxes"

		Assert.assertEquals(add_manual_transaction.infoMessage().getText(),
				PropsUtil.getDataPropertyValue("InfoMessage"));

	}

	@Test(description = "AT-29575:TRA_01_17:Verify the Add Icon displayed", priority = 18, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledAddIcon() {
		// Verify the Add Icon displayed next to Tag field as mentioned in the Specs

		Assert.assertTrue(add_manual_transaction.pluseIcon().isDisplayed());
	}

	@Test(description = "AT-53678:TRA_01_18:Verify the Text SHOW MORE OPTIONS displayed below the tag field", priority = 19, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	public void showMoreDetails() {

		SeleniumUtil.waitForElement("BBT", 2000);
		Assert.assertEquals(add_manual_transaction.showMoreDetails().getText(),
				PropsUtil.getDataPropertyValue("AggShowMore"));

	}

	@Test(description = "AT-29577:TRA_01_19:Verify the link changes to SHOW LESS OPTIONS after click on the Show More Options link", priority = 20, dependsOnMethods = {
			"showMoreDetails" })
	public void showLessDetails() {
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.showMoreDetails().getText(),
				PropsUtil.getDataPropertyValue("showLess"));

	}

	@Test(description = "AT-29576:TRA_01_20:verify check filed", priority = 21, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	public void verifyCheckfiledForOntime() {

		// Verify the Note ,Check Number Fields displayed After click on the "SHOW MORE
		// OPTIONS" link
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.refresh(d);
		} else {
			// add_manual_transaction.cancel().click();
			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad(1000);
			utils.switchToFrame(d);
			PageParser.forceNavigate("Transaction", d);
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		SeleniumUtil.waitForPageToLoad(3000);
		boolean check = add_manual_transaction.check().isDisplayed();
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(check);

	}

	@Test(description = "AT-29579:TRA_01_21:Verify Check Number should not reflect while creating recurring transactions", priority = 22, dependsOnMethods = {
			"verifyCheckfiledForOntime" })
	public void verifyCheckfiledForOtherFq() {
		// Verify Check Number should not reflect while creating recurring transactions

		SeleniumUtil.click(add_manual_transaction.frequencyDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForElement("BBT", 1000);
		SeleniumUtil.click(add_manual_transaction.FrequencyList().get(1));
		// add_manual_transaction.FrequencyList.get(0).click();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForElement("BBT", 2000);
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		// add_manual_transaction.showMoreDetails.click();
		SeleniumUtil.waitForElement("BBT", 2000);

		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			try {
				if (add_manual_transaction.check().isDisplayed()) {
					Assert.assertFalse(true);
				}
			} catch (Exception e) {

			}

		}
	}

	@Test(description = "AT-29576:TRA_01_22:verify note field for recurence transaction ", priority = 23, dependsOnMethods = {
			"verifyCheckfiledForOtherFq" })
	public void verifyNoteField() {
		// verify note field
		Assert.assertTrue(add_manual_transaction.note().isDisplayed());
	}

	@Test(description = "AT-29582:TRA_01_23:verify cancel button", priority = 24, dependsOnMethods = {
			"verifyCheckfiledForOntime" })
	public void cancelButton()

	{
		// Verify the "Cancel" and "ADD" labels displayed at the end of the Add a
		// Transaction pop up

		Assert.assertTrue(add_manual_transaction.cancel().isDisplayed());

	}

	@Test(description = "AT-29582:TRA_01_24:verify add button", priority = 25, dependsOnMethods = {
			"verifyCheckfiledForOntime" })
	public void addButton() {
		// Verify the "Cancel" and "ADD" labels displayed at the end of the Add a
		// Transaction pop up
		Assert.assertTrue(add_manual_transaction.add().isDisplayed());
	}

	@Test(description = "AT-53679:TRA_01_25:Verify the pop up get closed after click on the X icon and cancel button in pop up", priority = 26, dependsOnMethods = {
			"cancelButton" })
	public void cancelButtonClick()

	{
		SeleniumUtil.click(add_manual_transaction.cancel());
		try {
			if (add_manual_transaction.cancel().isDisplayed()) {
				Assert.assertFalse(true);
			}
		} catch (Exception e) {

		}

	}

	@Test(description = "AT-53680 :TRA_01_26:Verify the pop up should get closed after click on close icon in pop up ", priority = 27, dependsOnMethods = {
			"cancelButtonClick" })
	public void closeButtonClick()

	{

		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(add_manual_transaction.close());
		// add_manual_transaction.cancel().click();
		SeleniumUtil.waitForPageToLoad(1000);
		try {
			if (add_manual_transaction.close().isDisplayed()) {
				Assert.assertFalse(true);
			}
		} catch (Exception e) {

		}

	}

	@Test(description = "AT-29585,AT-29690:TRA_01_27:verify amount mandatory field validation", priority = 28)
	public void verifyAmountMandatoryField() {
		// Verify the below mandatory fields when user selects transaction type
		// "Withdrawal/Deposit" Currency, Amount, Description, Transaction Type,
		// Account, Frequency, Schedule Date, Category
		// Verify the message "This field is required" if user is not entered the values
		// for mandatory fields

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.MobileMore().click();
			add_manual_transaction.mobileTransactionIcon().click();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				PageParser.forceNavigate("Transaction", d);
			} else {
				SeleniumUtil.refresh(d);
				SeleniumUtil.waitForPageToLoad(2000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);

			}
			SeleniumUtil.waitForPageToLoad(10000);
			SeleniumUtil.click(add_manual_transaction.addManualLink());

			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.click(add_manual_transaction.add());

		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.amountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29585:TRA_01_28:verify description mandatory field validation", priority = 29, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyDecriptionMandatoryField() {
		Assert.assertEquals(add_manual_transaction.descErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-29585:TRA_01_29:verify the account mandatory field validation ", priority = 30, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyAccountMandatoryField() {
		Assert.assertEquals(add_manual_transaction.accountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29585:TRA_01_30:verify the scheduled date  mandatory field validation ", priority = 31, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyScheduledMandatoryField() {
		Assert.assertEquals(add_manual_transaction.scheduleErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29585:TRA_01_31:verify the category mandatory field validation", priority = 32, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyCategoryMandatoryField() {
		Assert.assertEquals(add_manual_transaction.catgoryErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_32:verify the investment Curency field", priority = 33)
	public void verifyInvestmentAmountMandatoryField() {
		// Verify the below mandatory fields when user selects transaction type
		// "Investment"Currency, Amount, Description, Transaction Type, Account, Date,
		// Symbol Ticker, CUSIP, Buy/Sell, Price, Quantity, Holding Type, Lot Holding

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB"))

		{
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				PageParser.forceNavigate("Transaction", d);
				SeleniumUtil.waitForPageToLoad(2000);
			} else {
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(7000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
				SeleniumUtil.waitForPageToLoad(10000);
			}
			add_manual_transaction.addManualLink().click();
			SeleniumUtil.waitForPageToLoad(2000);
		}
		SeleniumUtil.click(add_manual_transaction.TransactionType());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(2));

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.add());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.amountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_33:verify the investment description manadtory field", priority = 34, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentDescMandatoryField() {
		Assert.assertEquals(add_manual_transaction.descErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_34:verify the investment Account mandatory filed", priority = 35, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentAccountMandatoryField() {
		Assert.assertEquals(add_manual_transaction.accountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_35:verify the investment Scheduled date manadatory field", priority = 36, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentScheduleMandatoryField() {
		Assert.assertEquals(add_manual_transaction.scheduleErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_36:verify the Investment symbol mandatory filed validation", priority = 37, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentSymbolMandatoryField() {
		Assert.assertEquals(add_manual_transaction.symbolErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_37:verify the Investment Price Field ", priority = 38, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentPriceMandatoryField() {
		Assert.assertEquals(add_manual_transaction.priceErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_38:verify the InvestmentQuantity field", priority = 39, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentQuantityMandatoryField() {
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_39:verify the InvestmentHolding Mandatory field", priority = 40, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentHoldingMandatoryField() {
		Assert.assertEquals(add_manual_transaction.hordingErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29586:TRA_01_40:verify the Investment Lot Mandatory field", priority = 41, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentLotMandatoryField() {
		Assert.assertEquals(add_manual_transaction.lotHoldingErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-29587:TRA_01_41:Verify the Currency dropdown", priority = 42)
	public void VeriftCurrencydropDown() {
		// Verify the Drop Down displayed after click on the "V" icon in the respective
		// fields in pop up window
		SeleniumUtil.waitForElement("BBT", 2000);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{

			add_manual_transaction.MobileMore().click();
			add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				// add_manual_transaction.cancel().click();
				SeleniumUtil.refresh(d);
				SeleniumUtil.waitForElement("BBT", 2000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
			}
			SeleniumUtil.waitForPageToLoad(10000);
			add_manual_transaction.addManualLink().click();

			SeleniumUtil.waitForPageToLoad();
		}
		add_manual_transaction.currencyV().click();
		SeleniumUtil.waitForPageToLoad();
		boolean currency = add_manual_transaction.currencyLIst().get(0).isDisplayed();
		Assert.assertTrue(currency);

	}

	@Test(description = "AT-29564:TRA_01_42:Verify only the Currency Symbols displayed in the dropdown Example:USD,INR,AUD,EUR etc..", priority = 43, dependsOnMethods = {
			"VeriftCurrencydropDown" })
	public void VeriftCurrencydropDownValues() {
		// Verify only the Currency Symbols displayed in the dropdown
		// Example:USD,INR,AUD,EUR etc..
		List<String> Actual = new ArrayList<String>();
		for (int i = 0; i < add_manual_transaction.CurrencyList().size(); i++) {
			System.out.println(add_manual_transaction.CurrencyList().get(i).getText());
			Actual.add(add_manual_transaction.CurrencyList().get(i).getText());
		}
		String expected[] = PropsUtil.getDataPropertyValue("AllCurrency").split(",");
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.mobilecurrencyV().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			SeleniumUtil.waitForPageToLoad(10000);
			add_manual_transaction.currencyV().click();
			SeleniumUtil.waitForPageToLoad();
		}

		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(Actual.get(i), expected[i]);
		}
	}

	@Test(description = "AT-29587:TRA_01_43:verify transaction type Dropdown", priority = 44, dependsOnMethods = {
			"VeriftCurrencydropDown" })
	public void VeriftTransactionTypedropDown() {
		add_manual_transaction.TransacV().click();

		boolean TransactionType = add_manual_transaction.TtransactionList().get(0).isDisplayed();

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.mobileTransacV().click();
		} else {
			add_manual_transaction.TransacV().click();
		}
		Assert.assertTrue(TransactionType);
	}

	@Test(description = "AT-29587:TRA_01_44:verify the Account dropdown ", priority = 45, dependsOnMethods = {
			"VeriftCurrencydropDown" })
	public void VeriftAccountdropDown() {
		SeleniumUtil.click(add_manual_transaction.accountV());
		SeleniumUtil.waitForPageToLoad(1000);
		boolean account = false;
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			account = add_manual_transaction.ListAccount(1).get(0).isDisplayed();
			SeleniumUtil.waitForPageToLoad(1000);
		} else {
			account = add_manual_transaction.ListAccount(1).get(0).isDisplayed();
			SeleniumUtil.waitForPageToLoad(1000);
		}
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.mobileaccountV().click();
		}

		else {
			SeleniumUtil.click(add_manual_transaction.accountV());
		}
		Assert.assertTrue(account);
	}

	@Test(description = "AT-29587:TRA_01_45:verify the frequency dropdown", priority = 46, dependsOnMethods = {
			"VeriftCurrencydropDown" })
	public void VeriftFrequencydropDown() {
		SeleniumUtil.click(add_manual_transaction.frequecyV());
		boolean frequency = add_manual_transaction.FrequencyList().get(0).isDisplayed();

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.mobilefrequecyV().click();
		} else {
			SeleniumUtil.click(add_manual_transaction.frequecyV());
		}
		Assert.assertTrue(frequency);
	}

	@Test(description = "AT-29587:TRA_01_46:verify the Currency dropdown", priority = 47, dependsOnMethods = {
			"VeriftCurrencydropDown" })
	public void VeriftCategorydropDown() {
		SeleniumUtil.waitForPageToLoad();
		// add_manual_transaction.categoryV.click();
		SeleniumUtil.click(add_manual_transaction.categoryV());
		SeleniumUtil.waitForPageToLoad();
		boolean catg = false;
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			catg = add_manual_transaction.selectCategory().get(1).isDisplayed();

		} else {
			catg = add_manual_transaction.catgoryList(1, 1).isDisplayed();

		}

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.mobilecategoryV().click();
		} else {
			SeleniumUtil.click(add_manual_transaction.categoryV());
		}

		Assert.assertTrue(catg);
	}

	@Test(description = "AT-29589:TRA_01_47:Verify the error message if user enters < 3 characters in the description field", priority = 48)
	void descerrorValidation() {
		// Verify the error message if user enters < 3 characters in the description
		// field "At least 3 characters are required"

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.refresh(d);
		} else {
			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad(2000);
			utils.switchToFrame(d);
			// add_manual_transaction.cancel().click();
			PageParser.forceNavigate("Transaction", d);
			SeleniumUtil.waitForPageToLoad(2000);
		}
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForElement("BBT", 2000);
		add_manual_transaction.description().click();
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description2"));
		Assert.assertEquals(add_manual_transaction.descErr().getText(),
				PropsUtil.getDataPropertyValue("descriptionErr"));

	}

	@Test(description = "AT-29590:TRA_01_48:Verify the maximum 130 characters allowed in the description field", priority = 49, dependsOnMethods = {
			"descerrorValidation" })
	void descmaxValueValidation() {
		// Verify the maximum 130 characters allowed in the description field

		add_manual_transaction.description().clear();
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("descriptionErr130"));
		add_manual_transaction.description().sendKeys(Keys.TAB);
		if (add_manual_transaction.getAtributeVAlue(add_manual_transaction.description().getAttribute("id"))
				.length() != 130) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-29591:TRA_01_49:Verify the Amount field allow Max: 4 after decimal", priority = 50, dependsOnMethods = {
			"descerrorValidation" })
	void amountDecimalmaxValueValidation() {
		// Verify the Amount field allow 11,4 logic Max: 11 before decimal Max: 4 after
		// decimal
		add_manual_transaction.amount().click();
		add_manual_transaction.amount().sendKeys(PropsUtil.getDataPropertyValue("Amount11"));
		add_manual_transaction.amount().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.amountErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));

	}

	@Test(description = "AT-29591,AT-29701:TRA_01_50:Verify the Amount field allow 11,4 logic   Max: 11 before decimal", priority = 51, dependsOnMethods = {
			"descerrorValidation" })
	void amountValuemaxValueValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field

		add_manual_transaction.amount().clear();
		add_manual_transaction.amount().sendKeys(PropsUtil.getDataPropertyValue("Amount12"));
		add_manual_transaction.amount().sendKeys(Keys.TAB);
		Assert.assertEquals(add_manual_transaction.amountErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr1"));

	}

	@Test(description = "AT-29592:TRA_01_51:Verify only numbers and decimal alowed in the amount field", priority = 52, dependsOnMethods = {
			"descerrorValidation" })
	void amountErrorValidation() {
		// Verify only numbers and decimal alowed in the amount field

		add_manual_transaction.amount().clear();
		add_manual_transaction.amount().sendKeys(PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.amountErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));

	}

	@Test(description = "AT-29593:TRA_01_52:Verify the maximum 40 characters allowed in the TAg field", priority = 53, dependsOnMethods = {
			"descerrorValidation" })
	public void tagmaxValueValidation() {
		// Verify the maximum 40 characters allowed in the TAg field

		/*
		 * if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
		 * && PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))
		 * 
		 * { add_manual_transaction.MobileMore().click();
		 * add_manual_transaction.mobileTransactionIcon().click();
		 * SeleniumUtil.waitForPageToLoad(); } else { d.navigate().refresh();
		 * PageParser.forceNavigate("Transaction", d);
		 * SeleniumUtil.waitForElement("BBT", 3000);
		 * add_manual_transaction.addManualLink().click();
		 * SeleniumUtil.waitForElement("BBT", 2000);
		 * SeleniumUtil.click(add_manual_transaction.tag()); }
		 * SeleniumUtil.waitForPageToLoad();
		 */
		// add_manual_transaction.tag().click();
		// add_manual_transaction.tag().sendKeys(PropsUtil.getDataPropertyValue("tag40char"));
		// add_manual_transaction.tag().sendKeys(Keys.TAB);
		// if
		// (add_manual_transaction.getAtributeVAlue(add_manual_transaction.tag().getAttribute("id")).length()
		// != 40) {
		// Assert.assertTrue(false);
		// }
	}

	@Test(description = "AT-29594:TRA_01_53:Verify the error message if user enters < 3 characters in the tag field At least 3 characters are required", priority = 54, dependsOnMethods = {
			"descerrorValidation" })
	public void tagMax3CharValidation() {
		// Verify the error message if user enters < 3 characters in the tag field "At
		// least 3 characters are required"
		// add_manual_transaction.tag().clear();
		// add_manual_transaction.tag().sendKeys(PropsUtil.getDataPropertyValue("tagmin3"));
		Assert.assertEquals(add_manual_transaction.tagErr().getText(), PropsUtil.getDataPropertyValue("tagmin3Err"));
	}

	@Test(description = "AT-29595:TRA_01_54:Verify the error message if user enters duplicate tags Tag name already exists", priority = 55, dependsOnMethods = {
			"descerrorValidation" })
	public void tagAlreadyexistValidation() {
		// Verify the error message if user enters duplicate tags ""Tag name already
		// exists"

		// add_manual_transaction.tag().clear();
		// add_manual_transaction.tag().sendKeys(PropsUtil.getDataPropertyValue("tag1"));

		SeleniumUtil.click(add_manual_transaction.pluseIcon());
		SeleniumUtil.waitForPageToLoad();
		// add_manual_transaction.tag().click();
		// add_manual_transaction.tag().clear();
		// add_manual_transaction.tag().sendKeys(PropsUtil.getDataPropertyValue("tag1"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.pluseIcon());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.tagErr().getText(),
				PropsUtil.getDataPropertyValue("TagAlreadyExists"));
	}

	@Test(description = "AT-29596:TRA_01_55:Verify if any special characters are entered in Tags field, then show error Message : Only numbers and alphabets are allowed", priority = 56, dependsOnMethods = {
			"descerrorValidation" })
	public void tagSpecCharValidation() {
		// Verify if any special characters are entered in Tags field, then show error
		// Message : "Only numbers and alphabets are allowed"
		// add_manual_transaction.tag().click();
		// add_manual_transaction.tag().clear();
		// add_manual_transaction.tag().sendKeys(PropsUtil.getDataPropertyValue("TagSpecchar"));
		// add_manual_transaction.tag().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.tagErr().getText(),
				PropsUtil.getDataPropertyValue("TagSpeccharMessg"));
	}

	@Test(description = "AT-29597:TRA_01_56: Verify the user can able to add the unlimited tags", priority = 57, dependsOnMethods = {
			"descerrorValidation" })
	public void tagTotalValidation() {
		// Verify the user can able to add the unlimited tags
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag2"));
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag3"));
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag4"));
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag5"));
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag6"));
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag7"));
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("tag8"));
		if (add_manual_transaction.unlimittag().size() != 8) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-29598:TRA_01_57:Verify the 150 characters allowed in the Note Field", priority = 58, dependsOnMethods = {
			"descerrorValidation" })
	void notemaxValueValidation() {
		// Verify the 150 characters allowed in the Note Field

		/*
		 * if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
		 * SeleniumUtil.refresh(d); add_manual_transaction.addManualLink().click();
		 * SeleniumUtil.waitForElement("BBT", 2000); } else { SeleniumUtil.refresh(d);
		 * SeleniumUtil.waitForPageToLoad(2000); //
		 * add_manual_transaction.cancel().click();
		 * PageParser.forceNavigate("Transaction", d);
		 * SeleniumUtil.waitForPageToLoad(2000);
		 * add_manual_transaction.addManualLink().click();
		 * SeleniumUtil.waitForPageToLoad(2000); }
		 */
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		SeleniumUtil.waitForPageToLoad(2000);
		add_manual_transaction.note().click();
		add_manual_transaction.note().sendKeys(PropsUtil.getDataPropertyValue("Note150"));
		if (add_manual_transaction.getAtributeVAlue(add_manual_transaction.note().getAttribute("id")).length() != 150) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-29599:TRA_01_58:Verify the 20 characters allowed in the Cheque number Field", priority = 59, dependsOnMethods = {
			"descerrorValidation" })
	void checkmaxValueValidation() {
		add_manual_transaction.check().click();
		add_manual_transaction.check().sendKeys(PropsUtil.getDataPropertyValue("checkNo20"));

		if (add_manual_transaction.getAtributeVAlue(add_manual_transaction.check().getAttribute("id")).length() != 20) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-29600:TRA_01_59:verify only the numbers allowed in the check number field", priority = 60, dependsOnMethods = {
			"descerrorValidation" })
	void checkNumericValueValidation() {
		// verify only the numbers allowed in the check number field
		add_manual_transaction.check().click();
		add_manual_transaction.check().clear();
		add_manual_transaction.check().sendKeys(PropsUtil.getDataPropertyValue("CheckNumeric"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.checkErr().getText(),
				PropsUtil.getDataPropertyValue("checknumbericmesg"));
	}

	@Test(description = "AT-29602:TRA_01_60:Verify only the alpha numeric characters allowed in the Symbol/Ticker  Field", priority = 61)
	void symbolErrorValidation() {
		// Verify only the alpha numeric characters allowed in the Symbol/Ticker Field

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.cancel().click();
			add_manual_transaction.MobileMore().click();
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(add_manual_transaction.mobileTransactionIcon());
			// add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad(10000);
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(3000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
				SeleniumUtil.waitForPageToLoad(5000);
			}
			add_manual_transaction.addManualLink().click();
			SeleniumUtil.waitForPageToLoad(3000);
		}
		SeleniumUtil.click(add_manual_transaction.TransactionType());
		// add_manual_transaction.TransactionType.click();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(2));
		// add_manual_transaction.TtransactionList().get(2).click();
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.symbolTicket().click();
		add_manual_transaction.symbolTicket().sendKeys(PropsUtil.getDataPropertyValue("symbol1"));

		add_manual_transaction.symbolTicket().clear();
		add_manual_transaction.symbolTicket().sendKeys(PropsUtil.getDataPropertyValue("symbol2"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.symbolErr().getText(),
				PropsUtil.getDataPropertyValue("symbolErorr"));

	}

	@Test(description = "AT-29601:TRA_01_61:Verify the 12 characters allowed in the Symbol/Ticker  Field", priority = 62, dependsOnMethods = {
			"symbolErrorValidation" })
	void symbolmaxValueValidation() {
		add_manual_transaction.symbolTicket().click();
		add_manual_transaction.symbolTicket().clear();
		add_manual_transaction.symbolTicket().sendKeys(PropsUtil.getDataPropertyValue("symbol3"));
		if (add_manual_transaction.getAtributeVAlue(add_manual_transaction.symbolTicket().getAttribute("id"))
				.length() != 12) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-29603:TRA_01_62:Verify the Error message Only 9 characters are allowed displayed in the CUSIP field if user enters  more than 9 characters", priority = 63, dependsOnMethods = {
			"symbolErrorValidation" })
	public void CUSIPValueValidation() {
		// Verify the Error message "Only 9 characters are allowed "displayed in the
		// CUSIP field if user enters more than 9 characters
		add_manual_transaction.CUSIP().click();
		add_manual_transaction.CUSIP().click();
		add_manual_transaction.CUSIP().sendKeys(PropsUtil.getDataPropertyValue("CUSIP"));
		if (add_manual_transaction.getAtributeVAlue(add_manual_transaction.CUSIP().getAttribute("id")).length() != 9) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-29701::TRA_01_63:Verify the Error message Only 11 digits are allowed before decimal", priority = 64, dependsOnMethods = {
			"symbolErrorValidation" })
	public void priceMaxvalueErrorValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field

		add_manual_transaction.price().click();
		add_manual_transaction.price().sendKeys(PropsUtil.getDataPropertyValue("price1"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.priceErr().getText(), PropsUtil.getDataPropertyValue("price1err"));

	}

	@Test(description = "AT-29604,AT-29702:TRA_01_64:Verify the price field allow 11,4 logic   Max: 11 before decimal Max: 4 after decimal", priority = 65, dependsOnMethods = {
			"symbolErrorValidation" })
	public void priceDecimalMaxValueValidation() {
		// Verify the price field allow 11,4 logic Max: 11 before decimal Max: 4 after
		// decimal
		// Verify the error message "Only 4 digits are allowed after decimal" if user
		// enters more than 4 digits after decimal in amount,price and quantity field
		add_manual_transaction.price().click();
		add_manual_transaction.price().clear();
		add_manual_transaction.price().sendKeys(PropsUtil.getDataPropertyValue("price2"));
		add_manual_transaction.price().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(add_manual_transaction.priceErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));

	}

	@Test(description = "AT-29703:TRA_01_65:Verify the Error message Only numbers, decimal and formatters are allowed ( 123 345.00 )", priority = 66, dependsOnMethods = {
			"symbolErrorValidation" })
	public void priceAlphaNumValueValidation() {

		// Verify the Error message "Only numbers, decimal and formatters are allowed (
		// 123 345.00 )" if user enters the some special characters and other characters
		// in amount,price,netcost and quantity field

		add_manual_transaction.price().clear();
		add_manual_transaction.price().sendKeys(PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		add_manual_transaction.price().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(add_manual_transaction.priceErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));
	}

	@Test(description = "AT-29701:TRA_01_66:Verify the Error message Only 11 digits are allowed before decimal", priority = 67, dependsOnMethods = {
			"symbolErrorValidation" })
	void QuantityValueValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field
		add_manual_transaction.quantity().click();
		add_manual_transaction.quantity().sendKeys(PropsUtil.getDataPropertyValue("price1"));
		add_manual_transaction.quantity().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("price1err"));

	}

	@Test(description = "AT-29605:TRA_01_67:Verify the quantity field allow 11,4 logic   Max: 11 before decimal Max: 4 after decimal ", priority = 68, dependsOnMethods = {
			"symbolErrorValidation" })
	void quantityMaxValidation() {
		// Verify the quantity field allow 11,4 logic Max: 11 before decimal Max: 4
		// after decimal

		add_manual_transaction.quantity().clear();
		add_manual_transaction.quantity().sendKeys(PropsUtil.getDataPropertyValue("price2"));
		add_manual_transaction.quantity().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));
	}

	@Test(description = "AT-29703:TRA_01_68:Verify the Error message Only numbers, decimal and formatters are allowed ( 123 345.00 )", priority = 69, dependsOnMethods = {
			"symbolErrorValidation" })
	void quantityAlphaNumValidation() {
		// Verify the Error message "Only numbers, decimal and formatters are allowed (
		// 123 345.00 )" if user enters the some special characters and other characters
		// in amount,price,netcost and quantity field

		add_manual_transaction.quantity().clear();
		add_manual_transaction.quantity().sendKeys(PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		add_manual_transaction.quantity().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));
	}

	@Test(description = "AT-29607:TRA_01_69:Verify the error message displayed if scheduled date is not enteredInvalid date", priority = 70)
	public void ScheduleValueValidation() {
		// Verify the error message displayed if scheduled date is not entered"Invalid
		// date"

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.cancel().click();
			add_manual_transaction.MobileMore().click();
			add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(3000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);

			}
			SeleniumUtil.waitForPageToLoad(3000);
			add_manual_transaction.addManualLink().click();
			SeleniumUtil.waitForPageToLoad(1000);

		}
		add_manual_transaction.Schedule().click();
		add_manual_transaction.Schedule().sendKeys("65");
		Assert.assertEquals(add_manual_transaction.scheduleErr().getText(),
				PropsUtil.getDataPropertyValue("InvalidDate"));
	}

	@Test(description = "AT-29608:TRA_01_70:verify the  end date weekly error message", priority = 71, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDateweeklyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "
		Assert.assertEquals(add_manual_transaction.EndateError(1, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(3)), PropsUtil.getDataPropertyValue("endDateWeekly"));
	}

	@Test(description = "AT-29608:TRA_01_71:verify the end date two weekly error message", priority = 72, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDateTwoWeeklyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "

		Assert.assertEquals(add_manual_transaction.EndateError(2, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(10)), PropsUtil.getDataPropertyValue("endDateTwoWeekly"));
	}

	@Test(description = "AT-29608:TRA_01_72:verify the end date twice monthly error message", priority = 73, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDateTwicemonthlyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "

		Assert.assertEquals(
				add_manual_transaction.EndateError(3, add_manual_transaction.targateDate1(0),
						add_manual_transaction.targateDate1(12)),
				PropsUtil.getDataPropertyValue("endDateTwicemonthly"));
	}

	@Test(description = "AT-29608:TRA_01_73:verify the end date monthly error message", priority = 74, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatmonthlyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "

		Assert.assertEquals(add_manual_transaction.EndateError(4, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(25)), PropsUtil.getDataPropertyValue("endDatmonthly"));
	}

	@Test(description = "AT-29608:TRA_01_74:verify the end date two months once error message", priority = 75, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatTwomonthlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(5, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(55)), PropsUtil.getDataPropertyValue("endDatTwomonthly"));
	}

	@Test(description = "AT-29608:TRA_01_75:verify the end date three months once error message", priority = 76, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatThreemonthlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(6, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(85)), PropsUtil.getDataPropertyValue("endDatThreemonthly"));
	}

	@Test(description = "AT-29608:TRA_01_76:verify the end date half yearly once error message", priority = 77, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatHalfYearlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(7, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(175)), PropsUtil.getDataPropertyValue("endDatHalfYearly"));
	}

	@Test(description = "AT-29608:TRA_01_77:verify the end date  yearly once error message", priority = 78, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatYearlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(8, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(360)), PropsUtil.getDataPropertyValue("endDatYearly"));
	}

	@Test(description = "AT-29613 AT-29614:TRA_01_78:Validate the list of Categories displayed while adding a transaction / event", priority = 79)
	public void catValueValidation() {
		// Validate categories are grouped as HLC - MLC - Sub Category in categories
		// dropdown
		// Validate the list of Categories displayed while adding a transaction / event

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {

		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(4000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
			}
			SeleniumUtil.waitForPageToLoad(2000);
			add_manual_transaction.addManualLink().click();
		}

		String catg[] = PropsUtil.getDataPropertyValue("catg").split(",");
		add_manual_transaction.catgorie().click();

		for (int i = 0; i < add_manual_transaction.getAllManualTransactionCat().size(); i++) {
			// cat.add(catWeb.get(i).getText());
			System.out.println(add_manual_transaction.getAllManualTransactionCat().get(i).getText());
			System.out.println(catg[i]);

			Assert.assertTrue(add_manual_transaction.getAllManualTransactionCat().get(i).getText().contains(catg[i]));

		}
	}

	@Test(description = "AT-29615:TRA_01_79:Validate Search Category functionality and usability in Select Category page", priority = 80, dependsOnMethods = {
			"catValueValidation" })
	public void catSearchValidation() {
		add_manual_transaction.catsearchField().sendKeys(PropsUtil.getDataPropertyValue("catg2"));
		SeleniumUtil.waitForPageToLoad();
		// Assert.assertTrue(add_manual_transaction.CatSearched().isDisplayed());
	}

	@Test(description = "AT-29685:TRA_01_80:Verify the Debited from text displayed in accounts dropdown if the transaction type selected is Withdrawal", priority = 81)
	public void dabitedLabel() {
		// Verify the "Debited from" text displayed in accounts dropdown if the
		// transaction type selected is"Withdrawal"

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.MobileMore().click();
			SeleniumUtil.waitForPageToLoad();
			add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				d.navigate().refresh();
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
			}
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(add_manual_transaction.addManualLink());
		}
		// add_manual_transaction.addManualLink().click();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("debited"));
	}

	@Test(description = "AT-29686:TRA_01_81:Verify the Credited to text displayed in the Accounts dropdown if the transaction type is Deposit", priority = 82, dependsOnMethods = {
			"dabitedLabel" })
	public void creditedLabel() {
		// Verify the "Credited to" text displayed in the Accounts dropdown if the
		// transaction type is "Deposit"

		SeleniumUtil.click(add_manual_transaction.TransactionType());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(0));

		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("credited"));

	}

	@Test(description = "AT-29687:TRA_01_82:Verify the label Debited from displayed for the Investment Type if the type is Buy", priority = 83, dependsOnMethods = {
			"dabitedLabel" })
	public void buyLabel() {
		// Verify the label "Debited from" displayed for the Investment Type if the type
		// is "Buy"

		SeleniumUtil.click(add_manual_transaction.TransactionType());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(2));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("debited"));
	}

	@Test(description = "AT-29688:TRA_01_83:Verify the label Credited To displayed for the Investment Type if the type is Sell", priority = 84, dependsOnMethods = {
			"buyLabel" })
	public void sellLabel() {
		// Verify the label "Credited To" displayed for the Investment Type if the type
		// is "Sell"

		SeleniumUtil.click(add_manual_transaction.InvestmentType());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.investmentList().get(1));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("credited"));
	}

	@Test(description = "AT-29692:TRA_01_84:Verify the Account Name - Account Type - Masked Account number reflecting in the Accounts dropdown", priority = 85, dependsOnMethods = {
			"dabitedLabel" })
	public void accountnumberMask() {
		// Verify the Account Name - Account Type - Masked Account number reflecting in
		// the Accounts dropdown

		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		// add_manual_transaction.accountdropDown().click();
		SeleniumUtil.waitForPageToLoad();

		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			Assert.assertEquals(add_manual_transaction.accountForTrans().get(1).getText(),
					PropsUtil.getDataPropertyValue("AccountNameInDropDown111"));
		} else {
			Assert.assertEquals(add_manual_transaction.ListAccount(1).get(1).getText(),
					PropsUtil.getDataPropertyValue("AccountNameInDropDown111"));
		}

	}

	@Test(description = "AT-29618 AT-29619 AT-29627:TRA_01_85:Validate the list of transactions displayed in Posted and Projected section when a recurring series is added", priority = 86)
	public void ProjectedRecuring() throws InterruptedException {
		SeleniumUtil.waitForElement("BBT", 2000);
		SeleniumUtil.click(add_manual_transaction.cancel());
		SeleniumUtil.waitForPageToLoad();
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			SeleniumUtil.click(add_manual_transaction.MobileMore());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(add_manual_transaction.mobileTransactionIcon());
			SeleniumUtil.waitForPageToLoad();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(2000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
			}
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(add_manual_transaction.addManualLink());
			SeleniumUtil.waitForElement("BBT", 2000);
		}
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 7, 1, 1,
				PropsUtil.getDataPropertyValue("Note151"));

		SeleniumUtil.waitForPageToLoad(5000);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			// layout.serachTransaction(layout.getDateFormate(7), layout.getDateFormate(7));
		} else {
			layout.serachTransaction(add_manual_transaction.targateDate1(7), add_manual_transaction.targateDate1(7));
			SeleniumUtil.waitForPageToLoad(7000);
			if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.click(add_manual_transaction.ProjectedExapdIcon());
			}

		}
		SeleniumUtil.waitForPageToLoad(5000);
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			System.out.println(Series.getAllAmount1().get(i).getText());
			System.out.println(PropsUtil.getDataPropertyValue("Manualamount2"));
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount2"))) {
				// System.out.println(add_manual_transaction.getAlldescription(add_manual_transaction.DateInMMMMFormate(7).trim()).get(0).getText());
				Assert.assertEquals(Series.getAlldescription1().get(i).getText(),
						PropsUtil.getDataPropertyValue("description1"));
				if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")
						|| PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("PAD")) {
					Assert.assertTrue(
							Series.getAllcat1().get(i).getText().contains(PropsUtil.getDataPropertyValue("Cat33")));
				}
				Assert.assertEquals(Series.getAllAmount1().get(i).getText(),
						PropsUtil.getDataPropertyValue("Manualamount2"));
				break;
			}
		}

	}

	@Test(description = "AT-29580:TRA_01_86:Verify the user can able to add Notes while creating instance and recurring transactions in Add a Transaction pop up.", priority = 87)
	public void ProjectedNote() throws InterruptedException {
		Assert.assertTrue(add_manual_transaction.noteIocn().isDisplayed());
	}

	@Test(description = "AT-29618 AT-29619 AT-29627:TRA_01_87:Validate the list of transactions displayed in Posted and Projected section when a recurring series is added", priority = 87)
	public void PostedRecuring() throws InterruptedException {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")
				|| PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("PAD"))

		{

			layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));

			SeleniumUtil.waitForPageToLoad();
			for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
				if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("Manualamount2"))) {

					Assert.assertEquals(add_manual_transaction.getAlldescription1().get(i).getText(),
							PropsUtil.getDataPropertyValue("description1"));

					Assert.assertTrue(add_manual_transaction.getAllPostedCat1().get(i).getText()
							.contains(PropsUtil.getDataPropertyValue("Cat33")));

					Assert.assertEquals(add_manual_transaction.getAllPostedAmount1().get(i).getText(),
							PropsUtil.getDataPropertyValue("Manualamount2"));
					// Assert.assertEquals(add_manual_transaction.getAllPostedAcount1().get(0).getText(),PropsUtil.getDataPropertyValue("Accoun1"));
					// Assert.assertEquals(add_manual_transaction.getAllPostedAcountNo(add_manual_transaction.DateInMMMMFormate(0).trim()).get(0).getText(),PropsUtil.getDataPropertyValue("AccountNo1"));
					break;
				}
			}
		}
	}

	@Test(description = "AT-29628:TRA_01_88:Verify single instance transactions created if user selects frequency as one time", priority = 89)
	public void PostedProjectedOntime() {
		// Verify single instance transactions created if user selects frequency as one
		// time

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.MobileMore().click();
			add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(2000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
			}
			SeleniumUtil.waitForPageToLoad(5000);
			SeleniumUtil.click(add_manual_transaction.addManualLink());
		}
		// add_manual_transaction.addManualLink().click();
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.createTransactionWithOutNote(PropsUtil.getDataPropertyValue("Amount2"),
				PropsUtil.getDataPropertyValue("description1"), 1, 0, 1, 1);

		SeleniumUtil.waitForPageToLoad(5000);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			// layout.serachTransaction(layout.getDateFormate(0),layout.getDateFormate(0));
			SeleniumUtil.scrollElementIntoView(d, add_manual_transaction.getAllPostedAmount1().get(0), true);
		} else {
			layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
			SeleniumUtil.waitForPageToLoad(5000);
		}
		System.out.println(add_manual_transaction.getAllPostedAmount1().size());
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount3"))) {
				Assert.assertEquals(add_manual_transaction.getAlldescription1().get(i).getText(),
						PropsUtil.getDataPropertyValue("description1"));
				if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")
						|| PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("PAD")) {
					Assert.assertTrue(add_manual_transaction.getAllPostedCat1().get(i).getText()
							.contains(PropsUtil.getDataPropertyValue("Cat33")));
				}
				Assert.assertEquals(add_manual_transaction.getAllPostedAmount1().get(i).getText(),
						PropsUtil.getDataPropertyValue("Manualamount3"));

				// Assert.assertEquals(add_manual_transaction.getAllPostedAcount1().get(0).getText(),PropsUtil.getDataPropertyValue("Accoun1"));
				// Assert.assertEquals(add_manual_transaction.getAllPostedAcountNo(add_manual_transaction.DateInMMMMFormate(0).trim()).get(0).getText(),PropsUtil.getDataPropertyValue("AccountNo1"));
				break;
			}
		}

	}

	@Test(description = "AT-29588:TRA_01_89:Verify the message displayed after adding the transaction Transaction(s) added ", priority = 90)
	public void verifysccessMessage() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(add_manual_transaction.MobileMore());
			// add_manual_transaction.MobileMore().click();
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(add_manual_transaction.mobileTransactionIcon());
			// add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();
		}

		else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				SeleniumUtil.refresh(d);
			} else {
				SeleniumUtil.waitForElement("BBT", 2000);
				d.navigate().refresh();
				SeleniumUtil.waitForPageToLoad(3000);
				utils.switchToFrame(d);
				PageParser.forceNavigate("Transaction", d);
			}
			SeleniumUtil.waitForPageToLoad(3000);
			SeleniumUtil.click(add_manual_transaction.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad();
		// add_manual_transaction.addManualLink().click();
		add_manual_transaction.amount().sendKeys(PropsUtil.getDataPropertyValue("Amount3"));
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description1"));
		SeleniumUtil.click(add_manual_transaction.accountdropdown());

		SeleniumUtil.waitForPageToLoad();

		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			SeleniumUtil.click(add_manual_transaction.accountForTrans().get(0));
		} else {
			SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		}

		SeleniumUtil.waitForPageToLoad();

		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgorie());

		SeleniumUtil.waitForPageToLoad();

		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			SeleniumUtil.click(add_manual_transaction.selectCategory().get(1));
		} else {
			SeleniumUtil.click(add_manual_transaction.catgoryList(1, 1));
		}
		SeleniumUtil.waitForPageToLoad();

		// SeleniumUtil.click(add_manual_transaction.tag());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.tagList().get(0));

		SeleniumUtil.click(add_manual_transaction.showMoreDetails());

		add_manual_transaction.note().sendKeys(PropsUtil.getDataPropertyValue("note1"));
		add_manual_transaction.check().sendKeys(PropsUtil.getDataPropertyValue("check12"));
		add_manual_transaction.add().click();
		SeleniumUtil.waitForPageToLoad(500);

		Assert.assertEquals(add_manual_transaction.TransactionAdded().getText(),
				PropsUtil.getDataPropertyValue("transactionSucess"));

	}

	@Test(description = "AT-29581:TRA_01_90:Verify the User added Tags,Notes,check number in the Add a Transaction window reflected  for the respective transactions in the landing page", priority = 91)
	public void verifytheNote() {
		Assert.assertTrue(add_manual_transaction.tagIcon().isDisplayed());

	}

	@Test(description = "AT-29581:TRA_01_91:Verify the User added Tags,Notes,check number in the Add a Transaction window reflected  for the respective transactions in the landing page", priority = 92)
	public void verifytheTag() {
		Assert.assertTrue(add_manual_transaction.noteIocn().isDisplayed());
	}

	@Test(description = "AT-29581:TRA_01_92:Verify the User added Tags,Notes,check number in the Add a Transaction window reflected  for the respective transactions in the landing page", priority = 93)
	public void verifytheCheck() {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			// layout.serachTransaction(layout.getDateFormate(0),layout.getDateFormate(0));
			SeleniumUtil.scrollElementIntoView(d, add_manual_transaction.getAllPostedAmount1().get(0), true);
		} else {
			layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));

		}

		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount4"))) {

				SeleniumUtil.click(Agg.PendingStranctionList().get(i));
				SeleniumUtil.waitForPageToLoad(2000);
				SeleniumUtil.click(Agg.ShowMore());
				SeleniumUtil.waitForPageToLoad(2000);
				Assert.assertEquals(add_manual_transaction.AggregatedCheck().getAttribute("value"),
						PropsUtil.getDataPropertyValue("check12"));
			}
		}

	}

	@Test(description = "AT-29621:TRA_01_93:Validating the transactions are displayed in descending order by date", priority = 94)
	public void veriftTranasactionOrder() throws ParseException {
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.refresh(d);
		} else {
			d.navigate().refresh();
			SeleniumUtil.waitForPageToLoad(2000);
			utils.switchToFrame(d);
			PageParser.forceNavigate("Transaction", d);
		}
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(add_manual_transaction.verifyallTransactionOrder());
	}

	@Test(description = "AT-29622,AT-30699:TRA_01_94:Validate that add transaction works for supported Account Types onl", priority = 95)
	public void veriftSupportedAccount() throws ParseException {
		// Verify the Bills,Rewards,Real Estate accounts should not reflect in the
		// Manual Transaction pop up account dropdown
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(add_manual_transaction.accountdropdown());
		SeleniumUtil.waitForPageToLoad(1000);
		String expected[] = PropsUtil.getDataPropertyValue("AllmanualdropDownAccount").split(",");
		for (int i = 0; i < add_manual_transaction.ListAccount(1).size(); i++) {
			System.out.println(add_manual_transaction.ListAccount(1).get(i).getText());
			System.out.println(expected[i]);
			Assert.assertEquals(add_manual_transaction.ListAccount(1).get(i).getText(), expected[i]);
		}

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
