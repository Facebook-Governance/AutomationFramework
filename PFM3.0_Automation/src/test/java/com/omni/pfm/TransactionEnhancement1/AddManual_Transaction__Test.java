/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AddManual_Transaction__Test extends TestBase {

	public Transaction_Filter_Loc transaction_Filter;
	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	String userName = "";
	String expectedClassificationValues[];

	public Add_Manual_Transaction_Loc add_manual_transaction;

	public Series_Recurence_Transaction_Loc Series;
	public Transaction_Layout_Loc layout;
	// private AppiumDriver d;
	AddToCalendar_Transaction_Loc AddToCalendar;

	SeleniumUtil util;
	Aggregated_Transaction_Details_Loc Agg;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Add Manual Transaction");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		Series = new Series_Recurence_Transaction_Loc(d);
		util = new SeleniumUtil();
		Agg = new Aggregated_Transaction_Details_Loc(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68505:verify add manual transaction Icon", priority = 2)
	void verifyAddManualTransactIcon() throws InterruptedException {
		// verifing add manual Icon
		if (add_manual_transaction.isMoreBtnPresent()) {
			SeleniumUtil.click(add_manual_transaction.MobileMore());
		} else {
			Assert.assertTrue(add_manual_transaction.addManualIcon().isDisplayed());
			Assert.assertTrue(add_manual_transaction.addManualLink().isDisplayed());
		}
	}

	@Test(description = "AT-68505:verify add manual transaction header", priority = 4)
	void verifyAddManualTransactPopHeader() throws InterruptedException {
		// Verify the Add a Transaction pop up window displayed after click on the "Add
		// a Transaction" link in transaction FinApp home page.
		if (add_manual_transaction.isMobileaddManualIcon_AMTPresent()) {
			SeleniumUtil.click(add_manual_transaction.MobileaddManualIcon_AMT());
		} else {
			//
			add_manual_transaction.clickMTLink();
			Assert.assertEquals(add_manual_transaction.addManualHeader().getText(),
					PropsUtil.getDataPropertyValue("ManulaTransactionHeader"));
		}
	}

	@Test(description = "AT-68507:verify currency drop down", priority = 5, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyCurrencyDropDown() {
		// AT-29562 Verify the Currency dropdown displayed in the Add aTransaction
		// window
		Assert.assertTrue(add_manual_transaction.currencyDroDown().isDisplayed());

	}

	@Test(description = "AT-68507,AT-68508:verify currency field default value", priority = 6, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyCurrencyDropDownDefaultValue() {
		// Verify the by default USD currency symbol selected in the dropdown
		Assert.assertEquals(add_manual_transaction.currencyValue().getText(),
				PropsUtil.getDataPropertyValue("DefaultCurrency"));
	}

	@Test(description = "AT-68510:verify amount filed ", priority = 7, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyAmountField() {
		// Verify the "Amount Field" displayed next to the Currency field
		Assert.assertTrue(add_manual_transaction.amount().isDisplayed());
	}

	@Test(description = "AT-68512,AT-68514:verify transaction default type ", priority = 8, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void transactionTypeDefaultValue() {
		// Verify the transaction type withdrawal selected by default
		Assert.assertEquals(add_manual_transaction.TransactionType().getText(),
				PropsUtil.getDataPropertyValue("DefaultTraType"));

	}

	@Test(description = "AT-68515:verify frequency type", priority = 9, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void frequencyTypeDisplyed() {
		// Verify the "Frequency" text and dropdown field displayed below the Accounts
		// field in the Add a Transaction pop up
		Assert.assertTrue(add_manual_transaction.frequencytext().isDisplayed());
	}

	@Test(description = "AT-68515,AT-68518:verify frequency default type", priority = 10, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void frequencyTypeDefaultValue() {
		// Verify by default "One Time" option selected by default in Frequency field
		// when invoked from transaction FinApp
		Assert.assertEquals(add_manual_transaction.frequencytext().getText(),
				PropsUtil.getDataPropertyValue("frequency"));
	}

	@Test(description = "AT-68519:verify scheduled date field ", priority = 11, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void ScheduleDateFieldDispaly() {
		// Verify the "Scheduled Date" field displayed below the Frequency Field
		Assert.assertTrue(add_manual_transaction.Schedule().isDisplayed());

	}

	@Test(description = "AT-68519:verify scheduled date icon", priority = 12, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void ScheduleDateIconDispaly() {
		// calendar icon is displaying
		Assert.assertTrue(add_manual_transaction.calendIcon().isDisplayed());
	}

	@Test(description = "AT-68520:verify scheduled date calenar popup", priority = 13, dependsOnMethods = "ScheduleDateIconDispaly")
	void ScheduleDateCalendarDispaly() {
		// Verify the Calendar displayed after click on the Calendar icon and click on
		// the date Field

		boolean calendPopUpvalue = add_manual_transaction.ScheduledDatePopup();
		add_manual_transaction.clickScheduledDateIcon();
		SeleniumUtil.waitForPageToLoad(200);
		Assert.assertTrue(calendPopUpvalue);
	}

	@Test(description = "AT-68521:verify end date field", priority = 14, dependsOnMethods = {
			"verifyAddManualTransactPopHeader", "frequencyTypeDefaultValue" })
	void EndDateField() {
		// Verify the "End Date" field displayed if user selects the option other than
		// One Time option in the Frequency field like "Weekly" "Every 2 Weeks""Monthly"
		// Etc..
		add_manual_transaction.selectFrequency(PropsUtil.getDataPropertyValue("Transaction_MT_WeeklyFrequency"));
		Assert.assertTrue(add_manual_transaction.endDate().isDisplayed());
	}

	@Test(description = "AT-68521:verify end date icon", priority = 15, dependsOnMethods = { "EndDateField" })
	void EndDateFieldDateIcon() {
		// verify end date calander Icon
		Assert.assertTrue(add_manual_transaction.endDateIcon().isDisplayed());
	}

	@Test(description = "AT-68522:verify tag info icon", priority = 16, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledInfiIcon() {
		// Verify the icon and text displayed below the Tags Field "Create custom tags
		// to track transaction related to specific events ,such as vacation or taxes"
		Assert.assertTrue(add_manual_transaction.infoIcon().isDisplayed());
	}

	@Test(description = "AT-68522:verify tag info messsage", priority = 17, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledInfiMessage() {
		// Verify the icon and text displayed below the Tags Field "Create custom tags
		// to track transaction related to specific events ,such as vacation or taxes"
		Assert.assertEquals(add_manual_transaction.infoMessage().getText(),
				PropsUtil.getDataPropertyValue("InfoMessage"));
	}

	@Test(description = "AT-68522:verify tag field", priority = 18, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledAddIcon() {
		// Verify the Add Icon displayed next to Tag field as mentioned in the Specs

		// Assert.assertTrue(add_manual_transaction.pluseIcon().isDisplayed());// pluse
		// btton is invalid as use case changed
		Assert.assertTrue(add_manual_transaction.AddManulTransactionTag().isDisplayed());
	}

	@Test(description = "AT-68583:verify show more details ", priority = 19, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	public void showMoreDetails() {
		Assert.assertEquals(add_manual_transaction.showMoreDetails().getText(),
				PropsUtil.getDataPropertyValue("AggShowMore"));
	}

	@Test(description = "AT-68526:vreify show less options", priority = 20, dependsOnMethods = { "showMoreDetails" })
	public void showLessDetails() {
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.showMoreDetails().getText(),
				PropsUtil.getDataPropertyValue("showLess"));
	}

	@Test(description = "AT-68525,AT-68527:verify check field foe one time frequency", priority = 21, dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	public void verifyCheckfiledForOntime() {
		// Verify the Note ,Check Number Fields displayed After click on the "SHOW MORE
		// OPTIONS" link
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.clikcShowMore();
		boolean check = add_manual_transaction.check().isDisplayed();
		add_manual_transaction.clikcShowMore();
		Assert.assertTrue(check);
	}

	@Test(description = "AT-68528:verify check field for other frequency ", priority = 22, dependsOnMethods = {
			"verifyCheckfiledForOntime" })
	public void verifyCheckfiledForOtherFq() {
		// Verify Check Number should not reflect while creating recurring transactions
		add_manual_transaction.selectFrequency(PropsUtil.getDataPropertyValue("Transaction_MT_WeeklyFrequency"));
		add_manual_transaction.clikcShowMore();
		Assert.assertTrue(add_manual_transaction.checkList().getAttribute("style")
				.contains(PropsUtil.getDataPropertyValue("TransactionNocheckNoneOption")));
	}

	@Test(description = "AT-68525:verify note field", priority = 23, dependsOnMethods = {
			"verifyCheckfiledForOtherFq" })
	public void verifyNoteField() {
		// verify note field
		Assert.assertTrue(add_manual_transaction.note().isDisplayed());
	}

	@Test(description = "AT-68531:verify cancel button", priority = 24, dependsOnMethods = {
			"verifyCheckfiledForOntime" })
	public void cancelButton() {
		// Verify the "Cancel" and "ADD" labels displayed at the end of the Add a
		// Transaction pop up
		Assert.assertTrue(add_manual_transaction.cancel().isDisplayed());
	}

	@Test(description = "AT-68531:verify add button", priority = 25, dependsOnMethods = { "verifyCheckfiledForOntime" })
	public void addButton() {
		// Verify the "Cancel" and "ADD" labels displayed at the end of the Add a
		// Transaction pop up
		Assert.assertTrue(add_manual_transaction.add().isDisplayed());
	}

	@Test(description = "", priority = 26, dependsOnMethods = { "cancelButton" })
	public void cancelButtonClick() {
		SeleniumUtil.click(add_manual_transaction.cancel());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.cancelManualTransaction, 5),
				"Cancel manual transaction button is not working");
	}

	@Test(description = "AT-68584,AT-68585:verify close button", priority = 27, dependsOnMethods = {
			"cancelButtonClick" })
	public void closeButtonClick() {
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.clickClose_MT();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.addmanualTransactionCloseButton, 5),
				"Close manual transaction button is not working");
	}

	@Test(description = "AT-68532,AT-68576:verify amount mandatory field", priority = 28)
	public void verifyAmountMandatoryField() {
		// Verify the below mandatory fields when user selects transaction type
		// "Withdrawal/Deposit" Currency, Amount, Description, Transaction Type,
		// Account, Frequency, Schedule Date, Category
		// Verify the message "This field is required" if user is not entered the values
		// for mandatory fields

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.MobileMore().click();
			add_manual_transaction.mobileTransactionIcon().click();
		} else {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				PageParser.forceNavigate("Transaction", d);
			} else {
				SeleniumUtil.refresh(d);
				PageParser.forceNavigate("Transaction", d);
			}
			if (add_manual_transaction.isMoreBtnPresent()) {
				add_manual_transaction.clickAddManualTransaction();
			} else {
				add_manual_transaction.clickMTLink();
			}
		}
		SeleniumUtil.click(add_manual_transaction.add());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(add_manual_transaction.amountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68511,AT-68532,AT-68576,AT-68590:verify description mandatory field", priority = 29, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyDecriptionMandatoryField() {
		Assert.assertEquals(add_manual_transaction.descErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68532,AT-68576:verify account mandatory field", priority = 30, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyAccountMandatoryField() {
		Assert.assertEquals(add_manual_transaction.accountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68532,AT-68560,AT-68576:verify scheduled date mandatory field", priority = 31, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyScheduledMandatoryField() {
		Assert.assertEquals(add_manual_transaction.scheduleErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68523,AT-68532,AT-68576:verify category mandatory field", priority = 32, dependsOnMethods = {
			"verifyAmountMandatoryField" })
	public void verifyCategoryMandatoryField() {
		Assert.assertEquals(add_manual_transaction.catgoryErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68533:verify investment type amount mandatory field", priority = 33)
	public void verifyInvestmentAmountMandatoryField() {
		// Verify the below mandatory fields when user selects transaction type
		// "Investment"Currency, Amount, Description, Transaction Type, Account, Date,
		// Symbol Ticker, CUSIP, Buy/Sell, Price, Quantity, Holding Type, Lot Holding

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
				PageParser.forceNavigate("Transaction", d);
			} else {
				SeleniumUtil.refresh(d);
				PageParser.forceNavigate("Transaction", d);
			}
			if (add_manual_transaction.isMoreBtnPresent()) {
				add_manual_transaction.clickAddManualTransaction();
			} else {
				add_manual_transaction.addManualLink().click();
			}
		}
		add_manual_transaction.selectTxnType(PropsUtil.getDataPropertyValue("Transaction_MT_InvestmentTyep"));
		add_manual_transaction.clickAddButton();
		Assert.assertEquals(add_manual_transaction.amountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68533AT-68590:verify investment type description mandatory field", priority = 34, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentDescMandatoryField() {
		Assert.assertEquals(add_manual_transaction.descErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type account mandatory field", priority = 35, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentAccountMandatoryField() {
		Assert.assertEquals(add_manual_transaction.accountErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type Scheduled date mandatory field", priority = 36, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentScheduleMandatoryField() {
		Assert.assertEquals(add_manual_transaction.scheduleErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type symbol mandatory field", priority = 37, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentSymbolMandatoryField() {
		Assert.assertEquals(add_manual_transaction.symbolErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type price mandatory field", priority = 38, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentPriceMandatoryField() {
		Assert.assertEquals(add_manual_transaction.priceErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type quantity mandatory field", priority = 39, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentQuantityMandatoryField() {
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type holding mandatory field", priority = 40, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentHoldingMandatoryField() {
		Assert.assertEquals(add_manual_transaction.hordingErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68533:verify investment type lot mandatory field", priority = 41, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestmentLotMandatoryField() {
		Assert.assertEquals(add_manual_transaction.lotHoldingErr().getText(), PropsUtil.getDataPropertyValue("Erorr"));

	}

	@Test(description = "AT-68548,AT-68569:verify investment type all field", priority = 42, dependsOnMethods = {
			"verifyInvestmentAmountMandatoryField" })
	public void verifyInvestTypeAllField() {
		add_manual_transaction.clikcShowMore();
		add_manual_transaction.amount().isDisplayed();
		add_manual_transaction.currencyDroDown().isDisplayed();
		add_manual_transaction.description().isDisplayed();
		add_manual_transaction.TransactionType().isDisplayed();
		add_manual_transaction.accountdropDown().isDisplayed();
		add_manual_transaction.Schedule().isDisplayed();
		add_manual_transaction.symbolTicket().isDisplayed();
		add_manual_transaction.CUSIP().isDisplayed();
		add_manual_transaction.InvestmentType().isDisplayed();
		add_manual_transaction.price().isDisplayed();
		add_manual_transaction.quantity().isDisplayed();
		add_manual_transaction.Holdingtype().isDisplayed();
		add_manual_transaction.lot().isDisplayed();
		add_manual_transaction.AddManulTransactionTag().isDisplayed();
		add_manual_transaction.AddManulTransactionTag().isDisplayed();
		add_manual_transaction.note().isDisplayed();
		SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("TransactionNocheckNoneOption"),
				add_manual_transaction.checkList().getAttribute("style"), "");
	}

	@Test(description = "AT-68509:verify currency dropdown", priority = 43)
	public void verifyCurrencydropDown() {
		// Verify the Drop Down displayed after click on the "V" icon in the respective
		// fields in pop up window
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(add_manual_transaction.currencyV());
		boolean currency = add_manual_transaction.currencyLIst().get(0).isDisplayed();
		Assert.assertTrue(currency, "Currncy is not displayed");
	}

	@Test(description = "AT-68534:verify currency dropdown value", priority = 44, dependsOnMethods = {
			"verifyCurrencydropDown" })
	public void verifyCurrencydropDownValues() {
		// Verify only the Currency Symbols displayed in the dropdown
		// Example:USD,INR,AUD,EUR etc..
		List<String> Actual = new ArrayList<String>();
		for (int i = 0; i < add_manual_transaction.CurrencyList().size(); i++) {
			logger.info(add_manual_transaction.CurrencyList().get(i).getText());
			Actual = util.getWebElementsValue(add_manual_transaction.CurrencyList());
		}
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.mobilecurrencyV().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			add_manual_transaction.currencyV().click();
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		util.assertExpectedActualList(Actual, PropsUtil.getDataPropertyValue("AllCurrency"),
				"all types currency are not displayed");
	}

	@Test(description = "AT-68534:verify transaction type dropdown", priority = 45, dependsOnMethods = {
			"verifyCurrencydropDown" })
	public void verifyTransactionTypedropDown() {
		add_manual_transaction.TransacV().click();
		boolean TransactionType = add_manual_transaction.TtransactionList().get(0).isDisplayed();
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.mobileTransacV().click();
		} else {
			add_manual_transaction.TransacV().click();
		}
		Assert.assertTrue(TransactionType);
	}

	@Test(description = "AT-68534:verify account dropdown", priority = 46, dependsOnMethods = {
			"verifyCurrencydropDown" })
	public void verifyAccountdropDown() {
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
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.mobileaccountV().click();
		}

		else {
			SeleniumUtil.click(add_manual_transaction.accountV());
		}
		Assert.assertTrue(account);
	}

	@Test(description = "AT-68534:verify frequency drop down", priority = 47, dependsOnMethods = {
			"verifyCurrencydropDown" })
	public void verifyFrequencydropDown() {
		SeleniumUtil.click(add_manual_transaction.frequecyV());
		boolean frequency = add_manual_transaction.FrequencyList().get(0).isDisplayed();

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.mobilefrequecyV().click();
		} else {
			SeleniumUtil.click(add_manual_transaction.frequecyV());
		}
		Assert.assertTrue(frequency);
	}

	@Test(description = "AT-68516:verify category dropdown ", priority = 48, dependsOnMethods = {
			"verifyCurrencydropDown" })
	public void verifyCategorydropDown() {
		SeleniumUtil.click(add_manual_transaction.categoryV());
		boolean catg = false;
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			catg = add_manual_transaction.selectCategory().get(1).isDisplayed();
		} else {
			catg = add_manual_transaction.catgoryList(1, 1).isDisplayed();
		}
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.mobilecategoryV().click();
		} else {
			SeleniumUtil.click(add_manual_transaction.categoryV());
		}
		Assert.assertTrue(catg, "Category list is not displayed");
	}

	@Test(description = "AT-68536,AT-68586:verify description error message", priority = 49)
	void descerrorValidation() {
		// Verify the error message if user enters < 3 characters in the description
		// field "At least 3 characters are required"
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.enterValueToTextField(add_manual_transaction.description(),
				PropsUtil.getDataPropertyValue("description2"));
		// add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description2"));
		Assert.assertEquals(add_manual_transaction.descErr().getText(),
				PropsUtil.getDataPropertyValue("descriptionErr"));
	}

	@Test(description = "AT-68537:verify description max value", priority = 50, dependsOnMethods = {
			"descerrorValidation" })
	void descmaxValueValidation() {
		// Verify the maximum 130 characters allowed in the description field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.description(),
				PropsUtil.getDataPropertyValue("descriptionErr130"));
		if (add_manual_transaction.getAtributeVAlue(add_manual_transaction.description().getAttribute("id"))
				.length() != 130) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68538,AT-68579:verify amount decimal max value", priority = 51, dependsOnMethods = {
			"descerrorValidation" })
	void amountDecimalmaxValueValidation() {
		// Verify the Amount field allow 11,4 logic Max: 11 before decimal Max: 4 after
		// decimal
		add_manual_transaction.enterValueToTextField(add_manual_transaction.amount(),
				PropsUtil.getDataPropertyValue("Amount11"));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.amountErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));
	}

	@Test(description = "AT-68538:verify amount max value", priority = 52, dependsOnMethods = { "descerrorValidation" })
	void amountValuemaxValueValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.amount(),
				PropsUtil.getDataPropertyValue("Amount12"));
		Assert.assertEquals(add_manual_transaction.amountErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr1"));
	}

	@Test(description = "AT-68539,AT-68580:verify amount error message", priority = 53, dependsOnMethods = {
			"descerrorValidation" })
	void amountErrorValidation() {
		// Verify only numbers and decimal alowed in the amount field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.amount(),
				PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		Assert.assertEquals(add_manual_transaction.amountErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));
	}

	@Test(description = "AT-68540:verify tax max value", priority = 54, dependsOnMethods = { "descerrorValidation" })
	public void tagmaxValueValidation() {
		// Verify the maximum 40 characters allowed in the TAg field
		add_manual_transaction.enterValueToTagField(PropsUtil.getDataPropertyValue("tag40char"));
		if (add_manual_transaction.AddManualTransactTag().getAttribute("value").length() != 40) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68541,AT-68588:verify tax min 3 char validation", priority = 55, dependsOnMethods = {
			"tagmaxValueValidation" })
	public void tagMax3CharValidation() {
		// Verify the error message if user enters < 3 characters in the tag field "At
		// least 3 characters are required"
		add_manual_transaction.createtagnew(PropsUtil.getDataPropertyValue("tagmin3"));
		Assert.assertEquals(add_manual_transaction.tagErr().getText(), PropsUtil.getDataPropertyValue("tagmin3Err"));
	}

	@Test(description = "AT-68542:verify alreadt exsit tag error message", priority = 56, dependsOnMethods = {
			"tagMax3CharValidation" })
	public void tagAlreadyexistValidation() {
		// Verify the error message if user enters duplicate tags ""Tag name already
		// exists"
		add_manual_transaction.createtagnew(PropsUtil.getDataPropertyValue("tag1"));
		add_manual_transaction.createtagnew(PropsUtil.getDataPropertyValue("tag1"));
		Assert.assertEquals(add_manual_transaction.tagErr().getText(),
				PropsUtil.getDataPropertyValue("TagAlreadyExists"));
	}

	@Test(description = "AT-68543:verify tag spec char ", priority = 57, dependsOnMethods = {
			"tagAlreadyexistValidation" })
	public void tagSpecCharValidation() {
		// Verify if any special characters are entered in Tags field, then show error
		// Message : "Only numbers and alphabets are allowed"
		add_manual_transaction.enterValueToTag(PropsUtil.getDataPropertyValue("TagSpecchar"));

		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.tagErr().getText(),
				PropsUtil.getDataPropertyValue("TagSpeccharMessg"));
	}

	@Test(description = "AT-68592:verify tag total value", priority = 58, dependsOnMethods = {
			"tagSpecCharValidation" })
	public void tagTotalValidation() {
		// Verify the user can able to add the unlimited tags
		String tagList[] = PropsUtil.getDataPropertyValue("AddToCal_TagList1").split(",");
		for (int i = 0; i < tagList.length; i++) {
			add_manual_transaction.createtagnew(tagList[i]);

		}
		if (add_manual_transaction.unlimittag().size() != 8) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-68545: verify note max value", priority = 59, dependsOnMethods = { "descerrorValidation" })
	void notemaxValueValidation() {
		// Verify the 150 characters allowed in the Note Field
		add_manual_transaction.clikcShowMore();
		add_manual_transaction.enterValueToTextField(add_manual_transaction.note(),
				PropsUtil.getDataPropertyValue("Note150"));
		// add_manual_transaction.note().sendKeys(PropsUtil.getDataPropertyValue("Note150"));
		if (add_manual_transaction.note().getAttribute("value").length() != 150) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-68546:verify check max value validation", priority = 60, dependsOnMethods = {
			"notemaxValueValidation" })
	void checkmaxValueValidation() {
		add_manual_transaction.enterValueToTextField(add_manual_transaction.check(),
				PropsUtil.getDataPropertyValue("checkNo20"));

		// add_manual_transaction.check().sendKeys(PropsUtil.getDataPropertyValue("checkNo20"));

		if (add_manual_transaction.check().getAttribute("value").length() != 20) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-68547:verify check numeric value validation", priority = 61, dependsOnMethods = {
			"notemaxValueValidation" })
	void checkNumericValueValidation() {
		// verify only the numbers allowed in the check number field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.check(),
				PropsUtil.getDataPropertyValue("CheckNumeric"));
		Assert.assertEquals(add_manual_transaction.checkErr().getText(),
				PropsUtil.getDataPropertyValue("checknumbericmesg"));
	}

	@Test(description = "AT-68555:verify symbol error message", priority = 62)
	void symbolErrorValidation() {
		// Verify only the alpha numeric characters allowed in the Symbol/Ticker Field
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.selectTxnType(PropsUtil.getDataPropertyValue("Transaction_MT_InvestmentTyep"));
		/// add_manual_transaction.symbolTicket().sendKeys(PropsUtil.getDataPropertyValue("symbol1"));
		add_manual_transaction.enterValueToTextField(add_manual_transaction.symbolTicket(),
				PropsUtil.getDataPropertyValue("symbol2"));
		Assert.assertEquals(add_manual_transaction.symbolErr().getText(),
				PropsUtil.getDataPropertyValue("symbolErorr"));
	}

	@Test(description = "AT-68554,AT-68579:verify symbol max validation", priority = 63, dependsOnMethods = {
			"symbolErrorValidation" })
	void symbolmaxValueValidation() {
		add_manual_transaction.enterValueToTextField(add_manual_transaction.symbolTicket(),
				PropsUtil.getDataPropertyValue("symbol3"));
		if (add_manual_transaction.symbolTicket().getAttribute("value").length() != 12) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68556:verify CUSIP validation", priority = 64, dependsOnMethods = {
			"symbolErrorValidation" })
	public void CUSIPValueValidation() {
		// Verify the Error message "Only 9 characters are allowed "displayed in the
		// CUSIP field if user enters more than 9 characters
		add_manual_transaction.enterValueToTextField(add_manual_transaction.CUSIP(),
				PropsUtil.getDataPropertyValue("CUSIP"));

		// add_manual_transaction.CUSIP().sendKeys(PropsUtil.getDataPropertyValue("CUSIP"));
		if (add_manual_transaction.CUSIP().getAttribute("value").length() != 9) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68557,AT-68579:verify price max value validation", priority = 65, dependsOnMethods = {
			"symbolErrorValidation" })
	public void priceMaxvalueErrorValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.price(),
				PropsUtil.getDataPropertyValue("price1"));
		// add_manual_transaction.price().sendKeys(PropsUtil.getDataPropertyValue("price1"));
		Assert.assertEquals(add_manual_transaction.priceErr().getText(), PropsUtil.getDataPropertyValue("price1err"));
	}

	@Test(description = "AT-68557:verify price decimal max value validation", priority = 66, dependsOnMethods = {
			"symbolErrorValidation" })
	public void priceDecimalMaxValueValidation() {
		// Verify the price field allow 11,4 logic Max: 11 before decimal Max: 4 after
		// decimal
		// Verify the error message "Only 4 digits are allowed after decimal" if user
		// enters more than 4 digits after decimal in amount,price and quantity field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.price(),
				PropsUtil.getDataPropertyValue("price2"));
		Assert.assertEquals(add_manual_transaction.priceErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));
	}

	@Test(description = "AT-68580:verify price alcha numeric validation", priority = 67, dependsOnMethods = {
			"symbolErrorValidation" })
	public void priceAlphaNumValueValidation() {
		// Verify the Error message "Only numbers, decimal and formatters are allowed (
		// 123 345.00 )" if user enters the some special characters and other characters
		// in amount,price,netcost and quantity field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.price(),
				PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		Assert.assertEquals(add_manual_transaction.priceErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));
	}

	@Test(description = "AT-68558,AT-68579:verify quantity value validation", priority = 68, dependsOnMethods = {
			"symbolErrorValidation" })
	void QuantityValueValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.quantity(),
				PropsUtil.getDataPropertyValue("price1"));
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("price1err"));
	}

	@Test(description = "AT-68558,AT-68579:verify quantity value validation", priority = 69, dependsOnMethods = {
			"symbolErrorValidation" })
	void quantityMaxValidation() {
		// Verify the quantity field allow 11,4 logic Max: 11 before decimal Max: 4
		// after decimal
		add_manual_transaction.enterValueToTextField(add_manual_transaction.quantity(),
				PropsUtil.getDataPropertyValue("price2"));
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));
	}

	@Test(description = "AT-68580:verify quantity value validation", priority = 70, dependsOnMethods = {
			"symbolErrorValidation" })
	void quantityAlphaNumValidation() {
		// Verify the Error message "Only numbers, decimal and formatters are allowed (
		// 123 345.00 )" if user enters the some special characters and other characters
		// in amount,price,netcost and quantity field
		add_manual_transaction.enterValueToTextField(add_manual_transaction.quantity(),
				PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		Assert.assertEquals(add_manual_transaction.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));
	}

	@Test(description = "AT-68561:verify scheduled date error validation", priority = 71)
	public void ScheduleValueValidation() {
		// Verify the error message displayed if scheduled date is not entered"Invalid
		// date"
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.enterValueToTextField(add_manual_transaction.Schedule(), "65");
		// add_manual_transaction.Schedule().sendKeys("65");
		Assert.assertEquals(add_manual_transaction.scheduleErr().getText(),
				PropsUtil.getDataPropertyValue("TranInvalidDate"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 72, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDateweeklyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "
		Assert.assertEquals(add_manual_transaction.EndateError(1, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(3)), PropsUtil.getDataPropertyValue("endDateWeekly"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 73, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDateTwoWeeklyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "
		Assert.assertEquals(add_manual_transaction.EndateError(2, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(10)), PropsUtil.getDataPropertyValue("endDateTwoWeekly"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 74, dependsOnMethods = {
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

	@Test(description = "AT-68561:verify end date validation", priority = 75, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatmonthlyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "
		Assert.assertEquals(add_manual_transaction.EndateError(4, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(25)), PropsUtil.getDataPropertyValue("endDatmonthly"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 76, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatTwomonthlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(5, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(55)), PropsUtil.getDataPropertyValue("endDatTwomonthly"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 77, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatThreemonthlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(6, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(85)), PropsUtil.getDataPropertyValue("endDatThreemonthly"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 78, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatHalfYearlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(7, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(175)), PropsUtil.getDataPropertyValue("endDatHalfYearly"));
	}

	@Test(description = "AT-68561:verify end date validation", priority = 79, dependsOnMethods = {
			"ScheduleValueValidation" })
	public void endDatYearlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(8, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(360)), PropsUtil.getDataPropertyValue("endDatYearly"));
	}

	@Test(description = "AT-68562,AT-68563;verify catgeory 59 value ", priority = 80)
	public void catValueValidation() {
		// Validate categories are grouped as HLC - MLC - Sub Category in categories
		// dropdown
		// Validate the list of Categories displayed while adding a transaction / event
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}

		String catKeyTrue = PropsUtil.getDataPropertyValue("CatKeyTrue");
		int catsKey1 = Integer.parseInt(catKeyTrue);

		String catKeyfalse = PropsUtil.getDataPropertyValue("CatKeyFalse");
		int catsKey2 = Integer.parseInt(catKeyfalse);
		boolean expected = false;
		SeleniumUtil.click(add_manual_transaction.catgorie());
		logger.info("Category size is " + add_manual_transaction.getAllManualTransactionCat().size());
		if (add_manual_transaction.getAllManualTransactionCat().size() == catsKey1) {
			expected = true;
		} else if (add_manual_transaction.getAllManualTransactionCat().size() == catsKey2) {
			expected = true;
		}
		Assert.assertTrue(expected, "Categories size is not matching");
	}

	@Test(description = "AT-68564,AT-68204:verify category searh validation", priority = 81, dependsOnMethods = {
			"catValueValidation" }, enabled = false)
	public void catSearchValidation() {
		// commenting this due to this test case covered in category search enahncement
		add_manual_transaction.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("catg2"));
		String searchedCategory = add_manual_transaction.CatSearched().get(0).getAttribute("class");
		boolean remaingCategory = false;
		int unsearchedCategory = add_manual_transaction.CatSearched().size();
		for (int i = 1; i < unsearchedCategory; i++) {
			if (add_manual_transaction.CatSearched().get(i).getAttribute("class")
					.contains(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide"))) {
				remaingCategory = true;
			} else {
				remaingCategory = false;
				break;
			}
		}
		Assert.assertFalse(
				searchedCategory.contains(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide")),
				"searched category class should not displayed with hide");
		Assert.assertTrue(remaingCategory, "unsearched category should displayed with hide");
	}

	@Test(description = "AT-68572:verify dabit Label value", priority = 82)
	public void dabitedLabel() {
		// Verify the "Debited from" text displayed in accounts dropdown if the
		// transaction type selected is"Withdrawal"
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("debited"));
	}

	@Test(description = "AT-68573:verify credit Label value", priority = 83, dependsOnMethods = { "dabitedLabel" })
	public void creditedLabel() {
		// Verify the "Credited to" text displayed in the Accounts dropdown if the
		// transaction type is "Deposit"
		add_manual_transaction.selectTxnType(PropsUtil.getDataPropertyValue("Transaction_MT_DepositType"));
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("credited"));
	}

	@Test(description = "AT-68574:verify buy Label value", priority = 84, dependsOnMethods = { "dabitedLabel" })
	public void buyLabel() {
		// Verify the label "Debited from" displayed for the Investment Type if the type
		// is "Buy"
		add_manual_transaction.selectTxnType(PropsUtil.getDataPropertyValue("Transaction_MT_InvestmentTyep"));
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("debited"));
	}

	@Test(description = "AT-68575:verify sell Label value", priority = 85, dependsOnMethods = { "buyLabel" })
	public void sellLabel() {
		// Verify the label "Credited To" displayed for the Investment Type if the type
		// is "Sell"
		add_manual_transaction.selectInsvtType(PropsUtil.getDataPropertyValue("transactionTypeWithInvestTypeSell"));
		Assert.assertEquals(add_manual_transaction.accountdropDown().getText(),
				PropsUtil.getDataPropertyValue("credited"));
	}

	@Test(description = "AT-68229:verify account number masked", priority = 86, dependsOnMethods = { "dabitedLabel" })
	public void accountnumberMask() {
		// Verify the Account Name - Account Type - Masked Account number reflecting in
		// the Accounts dropdown
		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			Assert.assertEquals(add_manual_transaction.accountForTrans().get(1).getText(),
					PropsUtil.getDataPropertyValue("AccountNameInDropDown111"));
		} else {
			Assert.assertEquals(add_manual_transaction
					.MTAccount(PropsUtil.getDataPropertyValue("Transaction_MT_CardAccount1AccountsName")).getText(),
					PropsUtil.getDataPropertyValue("AccountNameInDropDown111"));
		}
	}

	@Test(description = "verify rescurring transaction", priority = 87)
	public void ProjectedRecuring() throws InterruptedException {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_WeeklyFrequency"), 0, 7,
				PropsUtil.getDataPropertyValue("Cat3"), PropsUtil.getDataPropertyValue("Note151"));

		layout.serachTransaction(add_manual_transaction.targateDate1(7), add_manual_transaction.targateDate1(7));
		try {
			SeleniumUtil.click(add_manual_transaction.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			logger.info(Series.getAllAmount1().get(i).getText());
			logger.info(PropsUtil.getDataPropertyValue("Manualamount2"));
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount2"))) {
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

	@Test(description = "verify projected txn note", priority = 88, dependsOnMethods = "ProjectedRecuring")
	public void ProjectedNote() throws InterruptedException {
		Assert.assertTrue(add_manual_transaction.noteIocn().isDisplayed());
	}

	@Test(description = "verify posted transaction", priority = 89, dependsOnMethods = "ProjectedRecuring")
	public void PostedRecuring() throws InterruptedException {

		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(add_manual_transaction.targateDate1(0),
					add_manual_transaction.targateDate1(0));
		} else {
			layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
		}
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

				break;
			}
		}

	}

	@Test(description = "verify posted one tim etransaction", priority = 90)
	public void PostedProjectedOntime() {
		// Verify single instance transactions created if user selects frequency as one
		// time
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			add_manual_transaction.clickMTLink();
		}
		add_manual_transaction.createTransactionWithOutNote(PropsUtil.getDataPropertyValue("Amount2"),
				PropsUtil.getDataPropertyValue("description1"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"), 0,
				PropsUtil.getDataPropertyValue("Cat3"));
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			// layout.serachTransaction(layout.getDateFormate(0),layout.getDateFormate(0));
			SeleniumUtil.scrollElementIntoView(d, add_manual_transaction.getAllPostedAmount1().get(0), true);
		} else {
			layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
		}
		logger.info("Posted amount size is :: " + add_manual_transaction.getAllPostedAmount1().size());
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
				break;
			}
		}
	}

	@Test(description = "AT-68529,AT-68535,AT-68582:verify transaction success message", priority = 91)
	public void verifysccessMessage() {
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		if (add_manual_transaction.isMoreBtnPresent()) {
			add_manual_transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(add_manual_transaction.addManualLink());
		}
		String sucessMessage = add_manual_transaction.addManualTxnSucessMessage(
				PropsUtil.getDataPropertyValue("Amount3"), PropsUtil.getDataPropertyValue("description1"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"), 0,
				PropsUtil.getDataPropertyValue("Cat3"), PropsUtil.getDataPropertyValue("note1"),
				PropsUtil.getDataPropertyValue("check12"));
		Assert.assertEquals(sucessMessage, PropsUtil.getDataPropertyValue("transactionSucess"));
	}

	@Test(description = "verify transaction note", priority = 92, dependsOnMethods = "verifysccessMessage")
	public void verifytheNote() {
		Assert.assertTrue(add_manual_transaction.tagIcon().isDisplayed());
	}

	@Test(description = "AT-68592:verify transaction tag", priority = 93, dependsOnMethods = "verifysccessMessage")
	public void verifytheTag() {
		Assert.assertTrue(add_manual_transaction.noteIocn().isDisplayed());
	}

	@Test(description = "AT-68530:verify transaction check", priority = 94, dependsOnMethods = "verifysccessMessage")
	public void verifytheCheck() {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			// layout.serachTransaction(layout.getDateFormate(0),layout.getDateFormate(0));
			SeleniumUtil.scrollElementIntoView(d, add_manual_transaction.getAllPostedAmount1().get(0), true);
		} else {
			layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
		}
		String check = null;
		String tag = null;
		String note = null;
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount4"))) {

				// SeleniumUtil.click(Agg.PendingStranctionList().get(i));
				Agg.postedTxnClick(i);
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.click(Agg.ShowMore());
				SeleniumUtil.waitForPageToLoad(1000);
				check = add_manual_transaction.AggregatedCheck().getAttribute("value");
				note = Agg.note().getAttribute("value");
				tag = Agg.AggregatedListTag().get(0).getText();
			}
		}
		Assert.assertEquals(check, PropsUtil.getDataPropertyValue("check12"));
		Assert.assertEquals(note, PropsUtil.getDataPropertyValue("note1"));
		Assert.assertEquals(tag, PropsUtil.getDataPropertyValue("Trnsaction_Mt_DefaultTag1"));

	}

	@Test(description = "AT-68221,AT-68567;verify transaction order", priority = 95)
	public void verifyTranasactionOrder() throws ParseException {
		SeleniumUtil.refresh(d);
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			PageParser.forceNavigate("Transaction", d);
		}
		Assert.assertTrue(add_manual_transaction.verifyallTransactionOrder());
	}

	@Test(description = "AT-68565,AT-68589:verify one time txn", priority = 96, dependsOnMethods = "verifyTranasactionOrder")
	public void addOneTimeTransactionWithDepositType() {
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Amount7"),
				PropsUtil.getDataPropertyValue("description4"),
				PropsUtil.getDataPropertyValue("Transaction_MT_WithDrwalType"),
				PropsUtil.getDataPropertyValue("Transaction_MT_CardAccount1AccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_OneTimeFrequency"), 3,
				PropsUtil.getDataPropertyValue("ManulTransactionCategory"), PropsUtil.getDataPropertyValue("note2"),
				PropsUtil.getDataPropertyValue("check13"));
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(3), add_manual_transaction.targateDate1(3));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount5"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}

		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description4"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");

	}

	@Test(description = "AT-68222,AT-68229,AT-68571:verify withdrawal type one time transaction", priority = 97, dependsOnMethods = "addOneTimeTransactionWithDepositType")
	public void addOneTimeTransactionWithwithdarwlType() {
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Amount8"),
				PropsUtil.getDataPropertyValue("description5"),
				PropsUtil.getDataPropertyValue("Transaction_MT_WithDrwalType"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_OneTimeFrequency"), 2,
				PropsUtil.getDataPropertyValue("ManulTransactionCategory1"), PropsUtil.getDataPropertyValue("note3"),
				PropsUtil.getDataPropertyValue("check14"));
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(2), add_manual_transaction.targateDate1(2));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount6"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description5"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory1"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency1"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");

	}

	@Test(description = "AT-68570:verify every two week transaction", priority = 98, dependsOnMethods = "addOneTimeTransactionWithwithdarwlType")
	public void addEveryTwoWeekTransactionWithDepositType() {
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createTransactionWithDeffTransactionType(PropsUtil.getDataPropertyValue("Amount9"),
				PropsUtil.getDataPropertyValue("description6"),
				PropsUtil.getDataPropertyValue("Transaction_MT_DepositType"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_EveryTwoWeekFrequency"), 1, 30,
				PropsUtil.getDataPropertyValue("ManulTransactionCategory2"), PropsUtil.getDataPropertyValue("note4"));
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(1), add_manual_transaction.targateDate1(1));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount7"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description6"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory2"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency2"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-68210,AT-68570:verify every two week transaction", priority = 99, dependsOnMethods = "addEveryTwoWeekTransactionWithDepositType")
	public void vrifyEveryTwoWeek2ndTransactionWithDepositType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(15), add_manual_transaction.targateDate1(15));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount7"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description6"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory2"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency3"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024,AT-68570:verify every two week transaction", priority = 100, dependsOnMethods = "addEveryTwoWeekTransactionWithDepositType")
	public void vrifyEveryTwoWeek3rdTransactionWithDepositType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(29), add_manual_transaction.targateDate1(29));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount7"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description6"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory2"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency4"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024:verify twice every month transaction", priority = 101, dependsOnMethods = "addEveryTwoWeekTransactionWithDepositType")
	public void addtwiceEveryMonthTransactionWithWithDrawType() {
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		add_manual_transaction.createTransactionWithDeffTransactionType(PropsUtil.getDataPropertyValue("Amount10"),
				PropsUtil.getDataPropertyValue("description7"),
				PropsUtil.getDataPropertyValue("Transaction_MT_WithDrwalType"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_TwiceEveryMonthFrequency"), -2, 50,
				PropsUtil.getDataPropertyValue("ManulTransactionCategory3"), PropsUtil.getDataPropertyValue("note5"));
		String description = null;
		String category = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(-2), add_manual_transaction.targateDate1(-2));
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount8"))) {
				description = add_manual_transaction.getAlldescription1().get(i).getText().trim();
				category = add_manual_transaction.getAllPostedCat1().get(i).getText().trim();
				account = add_manual_transaction.getAllPostedAcount1().get(i).getText().trim().replaceAll("\n", " ");
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description7"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory3"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024:verify twice every month transaction", priority = 102, dependsOnMethods = "addtwiceEveryMonthTransactionWithWithDrawType")
	public void verifyTwiceEveryMonth2ndTransactionWithWithDrawpe() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(13), add_manual_transaction.targateDate1(19));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount8"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description7"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory3"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		// Assert.assertEquals(frequency,
		// PropsUtil.getDataPropertyValue("ManualTransactionFrequency5"),"transaction
		// frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024:verify twice every month transaction", priority = 103, dependsOnMethods = "addtwiceEveryMonthTransactionWithWithDrawType")
	public void verifyTwiceEveryMonth3rdTransactionWithWithDrawtype() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(29), add_manual_transaction.targateDate1(29));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount8"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description7"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory1"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency6"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024:verify twice every month transaction", priority = 104, dependsOnMethods = "addtwiceEveryMonthTransactionWithWithDrawType")
	public void verifyTwiceEveryMonth4thTransactionWithWithDrawtype() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(44), add_manual_transaction.targateDate1(44));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount8"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description7"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory1"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency7"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024,AT-68606:verify monthly transaction", priority = 105, dependsOnMethods = "addtwiceEveryMonthTransactionWithWithDrawType")
	public void addMonthlyTransactionWithDepositType() {
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		add_manual_transaction.createTransactionWithDeffTransactionType(PropsUtil.getDataPropertyValue("Amount14"),
				PropsUtil.getDataPropertyValue("description8"),
				PropsUtil.getDataPropertyValue("Transaction_MT_DepositType"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_MonthlyFrequency"), 0, 95,
				PropsUtil.getDataPropertyValue("ManulTransactionCategory4"), PropsUtil.getDataPropertyValue("note6"));
		String description = null;
		String category = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount9"))) {
				description = add_manual_transaction.getAlldescription1().get(i).getText().trim();
				category = add_manual_transaction.getAllPostedCat1().get(i).getText().trim();
				account = add_manual_transaction.getAllPostedAcount1().get(i).getText().trim().replaceAll("\n", " ");
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}

		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description8"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory4"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024,AT-68606:verify monthly transaction", priority = 106, dependsOnMethods = "addMonthlyTransactionWithDepositType")
	public void verifymonthly2ndTransactionWithDepositType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		int date = Series.getNoDaysOfMonth(0);
		layout.serachTransaction(add_manual_transaction.targateDate1(date), add_manual_transaction.targateDate1(date));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount9"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description8"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory4"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency,
				PropsUtil.getDataPropertyValue("ManualTransactionFrequency8") + "" + Series.getNoDaysOfMonth(0) + " "
						+ PropsUtil.getDataPropertyValue("ManulaTransactionFrequencyDays"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024:verify monthly transaction", priority = 107, dependsOnMethods = "addMonthlyTransactionWithDepositType")
	public void verifyMonthly3rdTransactionWithWithDepositType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		int date = Series.getNoDaysOfMonth(0) + Series.getNoDaysOfMonth(1);
		layout.serachTransaction(add_manual_transaction.targateDate1(date), add_manual_transaction.targateDate1(date));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount9"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description8"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory4"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency9"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024:verify monthly transaction", priority = 108, dependsOnMethods = "addMonthlyTransactionWithDepositType")
	public void verifyMonthly4thTransactionWithWithDepositType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		int date = Series.getNoDaysOfMonth(0) + Series.getNoDaysOfMonth(1) + Series.getNoDaysOfMonth(2);
		layout.serachTransaction(add_manual_transaction.targateDate1(date), add_manual_transaction.targateDate1(date));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount9"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		int frequencydays = Series.getNoDaysOfMonth(0) + Series.getNoDaysOfMonth(1) + Series.getNoDaysOfMonth(2);
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description8"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory4"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount1"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency,
				PropsUtil.getDataPropertyValue("ManualTransactionFrequency10") + " " + frequencydays + " "
						+ PropsUtil.getDataPropertyValue("ManulaTransactionFrequencyDays"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024,AT-68608,AT-68609:verify every two month transaction", priority = 109, dependsOnMethods = "addMonthlyTransactionWithDepositType")
	public void addeveryTwoMonthTransactionWithWithDrawType() {
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		add_manual_transaction.createTransactionWithDeffTransactionType(PropsUtil.getDataPropertyValue("Amount15"),
				PropsUtil.getDataPropertyValue("description9"),
				PropsUtil.getDataPropertyValue("Transaction_MT_WithDrwalType"),
				PropsUtil.getDataPropertyValue("Transaction_MT_CashAccountsName"),
				PropsUtil.getDataPropertyValue("Transaction_MT_Every2MonthlyFrequency"), 0, 125,
				PropsUtil.getDataPropertyValue("ManulTransactionCategory5"), PropsUtil.getDataPropertyValue("note7"));
		String description = null;
		String category = null;
		String account = null;
		boolean noteSymbol = false;
		layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount10"))) {
				description = add_manual_transaction.getAlldescription1().get(i).getText().trim();
				category = add_manual_transaction.getAllPostedCat1().get(i).getText().trim();
				account = add_manual_transaction.getAllPostedAcount1().get(i).getText().trim().replaceAll("\n", " ");
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description9"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory5"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount2"),
				"transaction Accounts is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024,AT-68608,AT-68609:verify every two month transaction", priority = 110, dependsOnMethods = "addeveryTwoMonthTransactionWithWithDrawType")
	public void verifyEveryTwomonth2ndTransactionWithWithDrawType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		int date = Series.getNoDaysOfMonth(0) + Series.getNoDaysOfMonth(1);
		layout.serachTransaction(add_manual_transaction.targateDate1(date), add_manual_transaction.targateDate1(date));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount10"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description9"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory5"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount2"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency, PropsUtil.getDataPropertyValue("ManualTransactionFrequency11"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-69024,AT-68608,AT-68609:verify every two month transaction", priority = 111, dependsOnMethods = "addeveryTwoMonthTransactionWithWithDrawType")
	public void verifyEveryTwoMonth3rdTransactionWithWithWithDrawType() {
		String description = null;
		String category = null;
		String frequency = null;
		String account = null;
		boolean noteSymbol = false;
		int date = Series.getNoDaysOfMonth(0) + Series.getNoDaysOfMonth(1) + Series.getNoDaysOfMonth(2)
				+ Series.getNoDaysOfMonth(3);
		layout.serachTransaction(add_manual_transaction.targateDate1(date), add_manual_transaction.targateDate1(date));
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount10"))) {
				description = Series.getAlldescription1().get(i).getText().trim();
				category = Series.getAllcat1().get(i).getText().trim();
				account = Series.getAllaccount1().get(i).getText().replaceAll("\n", " ");
				frequency = Series.SeriesFrrequency().get(i).getText().trim();
				noteSymbol = add_manual_transaction.noteIocn().isDisplayed();
				break;
			}
		}
		int frequencyDays = Series.getNoDaysOfMonth(0) + Series.getNoDaysOfMonth(1) + Series.getNoDaysOfMonth(2)
				+ Series.getNoDaysOfMonth(3);
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("description9"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("ManulTransactionCategory5"),
				"transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("ManualTransactionAccount2"),
				"transaction Accounts is not displaying");
		Assert.assertEquals(frequency,
				PropsUtil.getDataPropertyValue("ManualTransactionFrequency12") + " " + frequencyDays + " "
						+ PropsUtil.getDataPropertyValue("ManulaTransactionFrequencyDays"),
				"transaction frequency is not displaying");
		Assert.assertTrue(noteSymbol, "added Note symbol is not displaying");
	}

	@Test(description = "AT-68568,AT-68577,AT-68581:verify all supported account", priority = 112, dependsOnMethods = "addeveryTwoMonthTransactionWithWithDrawType")
	public void verifySupportedAccount() throws ParseException {
		// Verify the Bills,Rewards,Real Estate accounts should not reflect in the
		// Manual Transaction pop up account dropdown
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(add_manual_transaction.accountdropdown());
		SeleniumUtil.waitForPageToLoad(1000);
		String expected[] = PropsUtil.getDataPropertyValue("AllmanualdropDownAccount").split(",");
		for (int i = 0; i < add_manual_transaction.ListAccount(1).size(); i++) {
			logger.info(add_manual_transaction.ListAccount(1).get(i).getText());
			logger.info(expected[i]);
			Assert.assertEquals(add_manual_transaction.ListAccount(1).get(i).getText(), expected[i]);
		}
	}

	@Test(description = "AT-68593:verify message disapper after enetr the valid value", priority = 113, dependsOnMethods = "verifySupportedAccount")
	public void disapearErrorMessage() throws ParseException {
		SeleniumUtil.click(add_manual_transaction.add());
		add_manual_transaction.enterAllMandatortField(PropsUtil.getDataPropertyValue("Amount3"),
				PropsUtil.getDataPropertyValue("description1"),
				PropsUtil.getDataPropertyValue("Transaction_MT_SavingAccountsName"), 0,
				PropsUtil.getDataPropertyValue("Cat3"), PropsUtil.getDataPropertyValue("note1"),
				PropsUtil.getDataPropertyValue("check12"));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.amountError, 1),
				"amount error message displayed");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.descriptionError, 1),
				"description error message displayed");
		Assert.assertTrue(
				add_manual_transaction.accountErr().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("Transaction_MT_ErrorMessageHide")),
				"Account error message disappear");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.scheduleError, 1),
				"Scheduled date error message disappear");
		Assert.assertTrue(
				add_manual_transaction.catgoryErr().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("Transaction_MT_ErrorMessageHide")),
				"Category error message disappear");
	}

	@Test(description = "AT-68593:verify invetsment type transaction error message disappear after entering valid value", priority = 114)
	public void invstTypeTxndisapearErrorMessage() throws ParseException {
		add_manual_transaction.clickClose_MT();
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		add_manual_transaction.selectTxnType(PropsUtil.getDataPropertyValue("Transaction_MT_InvestmentTyep"));
		SeleniumUtil.click(add_manual_transaction.add());
		add_manual_transaction.investTxnErrorMsgDisAppear(
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentAmount"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentdescription"),
				PropsUtil.getDataPropertyValue("Transaction_MT_InvstAccount1AccountsName"), 0,
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentSymbol"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentCUSIP"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentprice"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentQuantity"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentCDHolding"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentFirstInLot"));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.amountError, 1),
				"amount error message displayed");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.descriptionError, 1),
				"description error message displayed");
		Assert.assertTrue(
				add_manual_transaction.accountErr().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("Transaction_MT_ErrorMessageHide")),
				"Account error message disappear");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.scheduleError, 1),
				"Scheduled date error message disappear");

		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.symbolError, 1),
				"symbool error message disappear");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.priceError, 1),
				"price error message disappear");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(add_manual_transaction.quantityError, 1),
				"quantity error message disappear");
		logger.info(add_manual_transaction.hordingErr().getAttribute("class"));
		Assert.assertTrue(
				add_manual_transaction.hordingErr().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("Transaction_MT_ErrorMessageHide")),
				"holding error message disappear");
		Assert.assertTrue(
				add_manual_transaction.lotHoldingErr().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("Transaction_MT_ErrorMessageHide")),
				"lot error message disappear");
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