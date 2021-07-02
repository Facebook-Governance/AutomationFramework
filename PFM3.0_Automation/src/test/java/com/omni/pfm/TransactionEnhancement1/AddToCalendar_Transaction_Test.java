/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
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
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.GenericUtil.dateOperations;
import com.omni.pfm.utility.GenericUtil.dateParameters;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AddToCalendar_Transaction_Test extends TestBase {

	AddToCalendar_Transaction_Loc addToCalendar;
	Transaction_Layout_Loc layout;
	Add_Manual_Transaction_Loc add_manual_transaction;
	Series_Recurence_Transaction_Loc Series;
	Aggregated_Transaction_Details_Loc agg;
	SeleniumUtil util;
	private static final Logger logger = LoggerFactory.getLogger(AddToCalendar_Transaction_Test.class);
	public static String userName = "";
	SimpleDateFormat dateFromatInMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");

	String amount = null;
	String descrption = null;
	String bankname = null;
	String scheduleddate = null;
	String eventDescription = null;
	String evenFrequenc = null;
	String eventCatg = null;
	String EventAccountName = null;
	LoginPage login;
	AccountAddition accountAdd;
	Transaction_Tag_Loc tag;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		addToCalendar = new AddToCalendar_Transaction_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		Series = new Series_Recurence_Transaction_Loc(d);
		agg = new Aggregated_Transaction_Details_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		tag = new Transaction_Tag_Loc(d);
		util = new SeleniumUtil();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		doInitialization("Transaction :- Add to calendar");
	}

	@Test(priority = 1)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "addcal.bank1", "bank1");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68298,AT-68303,AT-68299:verify addToCalendar link", priority = 2)
	public void verifyAddcalLink() throws Exception {
		// verify the Add to calendar label
		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(0));
		} else {
			addToCalendar.clickPostedTxn(0);
			Assert.assertEquals(addToCalendar.addcalLink().getText(),
					PropsUtil.getDataPropertyValue("Txn_Dtls_AddToCalendar"),
					"add to caneldar link should be dispalyed ");
		}
	}

	@Test(description = "AT-68298,AT-68303,AT-68303:verify add to Calendar icon", priority = 3, dependsOnMethods = {
			"verifyAddcalLink" })
	public void verifyAddcalIcon() throws Exception {
		// Verify the Calendar icon displayed in the "ADD TO CALENDAR" label
		logger.info("Verify the Calendar icon displayed in the ADD TO CALENDAR label");
		Assert.assertTrue(addToCalendar.addcalIcon().isDisplayed(),
				" add to cal icon is displaying in Transaction details screen");
	}

	@Test(description = "AT-68304,AT-68305:verify add to calendar for projected transaction", priority = 4)
	public void verifyAddcalForProjectedTra() throws Exception {
		if (agg.isCancelBtnPresent()) {
			SeleniumUtil.click(agg.cancel());
		} else {
			Assert.assertTrue(addToCalendar.addToCalProTxn(),
					"add to cal should not displayed for projected Transaction");
		}
	}

	@Test(description = "AT-68306,AT-68313:verify add to Calendar popup header", priority = 5)
	public void verifyAddcalHeader() throws Exception {
		// AT-29665 Verify the text "New Event" displayed in header
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(0));
		} else {
			addToCalendar.clickPostedTxn(0);
		}
		addToCalendar.clickAddToCal();
		if (addToCalendar.isMobilePopupHeaderPresent()) {
			Assert.assertEquals(addToCalendar.MobilePopupHeader_ATC().get(0).getText(),
					PropsUtil.getDataPropertyValue("calPopUpHeader"));
		} else {
			Assert.assertEquals(addToCalendar.PopupHeader().getText(), PropsUtil.getDataPropertyValue("calPopUpHeader"),
					"New Event text should be displayed in Add cal popup");
		}
	}

	@Test(description = "AT-68314:Verify Add to Calendar Close Icon", priority = 6, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void verifyAddcalCloseIcon() throws Exception {
		Assert.assertTrue(addToCalendar.close().isDisplayed(), "Close icon is not displayed");
	}

	@Test(description = "AT-68315:Verify Add to Calendar info message", priority = 7, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void VerifyAddcalInfoMessage() throws Exception {
		Assert.assertEquals(addToCalendar.InfoMessage().getText(), PropsUtil.getDataPropertyValue("calInfoMessage"));
	}

	@Test(description = "AT-68308:Verify Add to Calendar amount field", priority = 8, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void verifyAddcalAmountField() throws Exception {
		Assert.assertTrue(addToCalendar.amountField().isDisplayed(), "Amount field is not displayed");
	}

	@Test(description = "AT-68308:Verify Add to Calendar bank name field", priority = 9, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void verifyAddcalBankNameSField() throws Exception {
		Assert.assertTrue(addToCalendar.bankName().isDisplayed(), "Bank name is not displayed");
	}

	@Test(description = "AT-68308:Verify Add to Calendar description", priority = 10, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void verifyAddcalDescriptionField() throws Exception {
		Assert.assertTrue(addToCalendar.description().isDisplayed(), "Description is not displayed");
	}

	@Test(description = "AT-68308:Verify Add to Calendar frequeny field", priority = 11, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void verifyAddcalFrequencyField() throws Exception {
		Assert.assertTrue(addToCalendar.Frequency().isDisplayed(), "Frequency is not displayed");
	}

	@Test(description = "AT-68308:Verify Add to Calendar scheduled date", priority = 12, dependsOnMethods = {
			"verifyAddcalHeader" })
	public void verifyAddcalScheduledDateField() throws Exception {
		Assert.assertTrue(addToCalendar.ScheduledDate().isDisplayed(), "Scheduled date is not displayed");
	}

	@Test(description = "AT-68318:Verify Add to Calendar amount value", priority = 13)

	public void verifyAddcalAmountValue() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(0));
		} else {
			addToCalendar.clickPostedTxn(0);
		}
		amount = agg.Amount().getText().replace("$", "");
		descrption = agg.descField().getAttribute("value");
		bankname = agg.accountName().getText();
		scheduleddate = agg.tansactiondate().getText();
		addToCalendar.clickAddToCal();
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.amountField().getAttribute("id")), amount);

	}

	@Test(description = "AT-68318:Verify Add to Calendar description value", priority = 14, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyAddcaldescriptionValue() {
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.description().getAttribute("id")), descrption,
				"Description given is not matching");
	}

	@Test(description = "AT-68318,AT-68328:Verify Add to Calendar bank name value", priority = 15, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyAddcalBankNameValue() {
		Assert.assertEquals(addToCalendar.bankName().getText().replace("Checking-", ""), bankname,
				"Bankname given is not matching");
	}

	@Test(description = "AT-68318,AT-68358:Verify Add to Calendar scheduled date value", priority = 16, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyAddcalScheduledDateValue() {
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.DateFormate(Series.TransactionDate(0)), "Scheduled date is not as expected");
	}

	@Test(description = "AT-68318,AT-68329:Verify Add to Calendar frequency value", priority = 17, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void VerifyAddcalFrequencyDefaultValue() {
		Assert.assertEquals(addToCalendar.Frequency().getText(), PropsUtil.getDataPropertyValue("AddCalDefaultFre"),
				"Frequency is not as expected");
	}

	@Test(description = "AT-68333,AT-68339,AT-68340,AT-68335:Verify Add to Calendar end date value", priority = 18, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyWeeklyEndDate() throws Exception {
		addToCalendar.changeFrequency(1);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.DateFormate(Series.getwithoutWeekEndDAte(7)));
	}

	@Test(description = "AT-68333,AT-68339,AT-68340,AT-68335:Verify Add to Calendar end date value", priority = 19, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyToWeeklyEndDate() throws Exception {
		addToCalendar.changeFrequency(2);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.DateFormate(Series.getwithoutWeekEndDAte(14)));
	}

	@Test(description = "AT-68333,AT-68339,AT-68340,AT-68335:Verify Add to Calendar end date value", priority = 20, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyMonthlywiseEndDate() throws Exception {
		addToCalendar.changeFrequency(4);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.DateFormate(Series.getwithoutWeekEndDAte(15)));
	}

	@Test(description = "AT-68333,AT-68339,AT-68340,AT-68335:Verify Add to Calendar end date value", priority = 21, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyTwoMonthEndDate() throws Exception {
		addToCalendar.changeFrequency(5);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.get60DaysWithOutWerekEnd());
	}

	@Test(description = "AT-68333,AT-68339,AT-68340,AT-68335:Verify Add to Calendar end date value", priority = 22, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyThreeMonthEndDate() throws Exception {
		addToCalendar.changeFrequency(6);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.get90DaysWithOutWerekEnd());
	}

	@Test(description = "AT-68333,AT-68339,AT-68340,AT-68335:Verify Add to Calendar end date value", priority = 23, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifySixMonthEndDate() throws Exception {
		addToCalendar.changeFrequency(7);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				Series.get180DaysWithOutWerekEnd());
	}

	@Test(description = "AT-68333,AT-68335:Verify Add to Calendar end date value", priority = 24, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyYearlyEndDate() throws Exception {
		addToCalendar.changeFrequency(8);
		Assert.assertEquals(addToCalendar.getAtributeVAlue(addToCalendar.ScheduledDate().getAttribute("id")),
				addToCalendar.DateFormate(Series.getwithoutWeekEndDAte(365)));
	}

	@Test(description = "AT-68332:Verify Add to Calendar frequency selected", priority = 25, dependsOnMethods = {
			"verifyYearlyEndDate" })
	public void verifyFrequencySelected() throws Exception {
		Assert.assertEquals(addToCalendar.Frequency().getText(),
				PropsUtil.getDataPropertyValue("AddCalSelectedFrequency"));
	}

	@Test(description = "AT-68331:Verify Add to Calendar frequency tick symbool is diplaying", priority = 26, dependsOnMethods = {
			"verifyYearlyEndDate" })
	public void verifyTickSymbolDisplaying() throws Exception {
		addToCalendar.clickFrequency();
		SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick"),
				addToCalendar.tickfrequencyList_ATC().get(8).getAttribute("class"), "Tick mark is not displayed");
	}

	@Test(description = "AT-68316:Verify Add to Calendar add button", priority = 27, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyADDButton() throws Exception {
		Assert.assertTrue(addToCalendar.addCal().isDisplayed(),"Add to calendar icon is not displayed");
	}

	@Test(description = "AT-68316:Verify Add to Calendar cancel button", priority = 28, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyCancel() throws Exception {
		Assert.assertTrue(addToCalendar.cancel().isDisplayed(),"Cancel icon is not displayed");
	}

	@Test(description = "AT-68363,AT-68317:Verify Add to Calendar popup close", priority = 29, dependsOnMethods = {
			"verifyAddcalAmountValue" })
	public void verifyCloseIconClick() throws Exception {
		String infoMessage = addToCalendar.InfoMessage().getText();
		addToCalendar.clickClose();
		Assert.assertTrue(addToCalendar.addcalLink().isDisplayed(),"Add to calendar icon is not displayed");
		Assert.assertEquals(infoMessage, PropsUtil.getDataPropertyValue("calInfoMessage"));
	}

	@Test(description = "AT-68341,AT-68342,AT-68338:Verify Add to Calendar end date error message", priority = 30, dependsOnMethods = "verifyCloseIconClick")
	public void endDateweeklyValidation() {
		SeleniumUtil.click(addToCalendar.addcalLink());
		SeleniumUtil.waitForPageToLoad(2000);
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "
		Assert.assertEquals(add_manual_transaction.EndateError(1, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(3)), PropsUtil.getDataPropertyValue("endDateWeekly"));
	}

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 31, dependsOnMethods = "endDateweeklyValidation")
	public void endDateTwoWeeklyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "

		Assert.assertEquals(add_manual_transaction.EndateError(2, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(10)), PropsUtil.getDataPropertyValue("endDateTwoWeekly"));
	}

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 32, dependsOnMethods = "endDateweeklyValidation")
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

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 33, dependsOnMethods = "endDateweeklyValidation")
	public void endDatmonthlyValidation() {
		// Validate the error message on calendar if )end date - scheduled date) <
		// Frequency selected. Example: "End date should be greater than schedule date
		// at least by a week" Example: "End date should be greater than schedule date
		// at least by 30 days "

		Assert.assertEquals(add_manual_transaction.EndateError(4, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(25)), PropsUtil.getDataPropertyValue("endDatmonthly"));
	}

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 34, dependsOnMethods = "endDateweeklyValidation")
	public void endDatTwomonthlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(5, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(55)), PropsUtil.getDataPropertyValue("endDatTwomonthly"));
	}

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 35, dependsOnMethods = "endDateweeklyValidation")
	public void endDatThreemonthlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(6, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(85)), PropsUtil.getDataPropertyValue("endDatThreemonthly"));
	}

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 36, dependsOnMethods = "endDateweeklyValidation")
	public void endDatHalfYearlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(7, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(175)), PropsUtil.getDataPropertyValue("endDatHalfYearly"));
	}

	@Test(description = "AT-68341,AT-68342:Verify Add to Calendar end date error message", priority = 37, dependsOnMethods = "endDateweeklyValidation")
	public void endDatYearlyValidation() {
		Assert.assertEquals(add_manual_transaction.EndateError(8, add_manual_transaction.targateDate1(0),
				add_manual_transaction.targateDate1(360)), PropsUtil.getDataPropertyValue("endDatYearly"));
	}

	@Test(description = "AT-68320:Verify Add to Calendar anmount error message", priority = 38)
	public void verifyAmounterrorMesaage() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(0));
		} else {
			SeleniumUtil.click(addToCalendar.PendingStranctionList().get(0));
		}
		addToCalendar.clickAddToCal();
		addToCalendar.allFieldErrorMessage();
		Assert.assertEquals(addToCalendar.amountErorr().getText(), PropsUtil.getDataPropertyValue("calErr"));
	}

	@Test(description = "AT-68346:Verify Add to Calendardescription error message", priority = 39, dependsOnMethods = {
			"verifyAmounterrorMesaage" })
	public void verifyDescriptionerrorMesaage() throws Exception {
		Assert.assertEquals(addToCalendar.descError().getText(), PropsUtil.getDataPropertyValue("calErr"));
	}

	@Test(description = "AT-68346:Verify Add to Calendar scheduled date error message", priority = 40, dependsOnMethods = {
			"verifyAmounterrorMesaage" })
	public void verifyScheduledDateerrorMesaage() throws Exception {
		Assert.assertEquals(addToCalendar.scheduleErorr().getText(), PropsUtil.getDataPropertyValue("calErr"));
	}

	@Test(description = "AT-68346:Verify Add to Calendar end date error message", priority = 41, dependsOnMethods = {
			"verifyAmounterrorMesaage" })
	public void verifyEndDateerrorMesaage() throws Exception {
		Assert.assertEquals(addToCalendar.endDateError().getText(), PropsUtil.getDataPropertyValue("calErr"));
	}

	@Test(description = "verify cancel button ", priority = 42, dependsOnMethods = { "verifyAmounterrorMesaage" })
	public void verifyCancelClick() throws Exception {
		addToCalendar.clickCancel();
		Assert.assertTrue(SeleniumUtil.isDisplayed(addToCalendar.addCalendarLink, 10),"Add to calendar link is not displayed");
		Assert.assertTrue(SeleniumUtil.getElementCount(addToCalendar.popupHeaders) == 0,
				"verify popup should closed when click on cancel");
	}

	@Test(description = "AT-68321.AT-68323:verify amount field decimal value validation", priority = 43)
	void amountDecimalmaxValueValidation() {
		// Verify the Amount field allow 11,4 logic Max: 11 before decimal Max: 4 after
		// decimal
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(0));
		} else {
			SeleniumUtil.click(addToCalendar.PendingStranctionList().get(0));
		}
		addToCalendar.clickAddToCal();
		addToCalendar.enterAmountFieldValue(PropsUtil.getDataPropertyValue("Amount11"));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(addToCalendar.amountErorr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr2"));
	}

	@Test(description = "AT-68321,AT-68322,,AT-68325:Verify Add to Calendar amount field max value validation", priority = 44, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	void amountValuemaxValueValidation() {
		// Verify the Error message "Only 11 digits are allowed before decimal" if user
		// entered more than 11 digits in amount ,price,net cost and quantity field
		addToCalendar.enterAmountFieldValue(PropsUtil.getDataPropertyValue("Amount12"));
		Assert.assertEquals(addToCalendar.amountErorr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr1"));
	}

	@Test(description = "AT-68324,AT-68326::Verify Add to Calendar amount filed decimal max value validation", priority = 45, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	void amountErrorValidation() {
		// Verify only numbers and decimal alowed in the amount field
		addToCalendar.enterAmountFieldValue(PropsUtil.getDataPropertyValue("AmountalphaNumeric"));
		Assert.assertEquals(addToCalendar.amountErorr().getText(),
				PropsUtil.getDataPropertyValue("AmountalphaNumericErr"));
	}

	@Test(description = "AT-68327::Verify Add to Calendar description field max value validation", priority = 46, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })

	public void descFieldMax150Validation() throws Exception {
		addToCalendar.enterDescriptionFieldValue(PropsUtil.getDataPropertyValue("caldesc10"));
		Assert.assertTrue(addToCalendar.despFieldMaxVal());
	}

	@Test(description = ":Verify Add to Calendar description field min value validation", priority = 47, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void descFieldMinValidation() throws Exception {
		addToCalendar.enterDescriptionFieldValue(PropsUtil.getDataPropertyValue("caldesc"));
		Assert.assertEquals(addToCalendar.descError().getText(), PropsUtil.getDataPropertyValue("caldescErr"));
	}

	@Test(description = "AT-68343:verify scheudled date validation", priority = 48, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void schedulesdateFieldValidation() throws Exception {
		addToCalendar.enterScheduleDateFieldValue(PropsUtil.getDataPropertyValue("calScheduleDate"));
		Assert.assertEquals(addToCalendar.scheduleErorr().getText(),
				PropsUtil.getDataPropertyValue("calScheduleDateerr"));
	}

	@Test(description = "AT-68367:Verify Add to Calendar end date validation", priority = 49, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void enddateFieldValidation() throws Exception {
		addToCalendar.enterEndDateFieldValue(PropsUtil.getDataPropertyValue("calScheduleDate"));
		Assert.assertEquals(addToCalendar.endDateError().getText(),
				PropsUtil.getDataPropertyValue("calScheduleDateerr"));
	}

	@Test(description = "AT-68367:Verify Add to Calendar end date error message validation", priority = 50, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void enddateFieldMinDateValidation() throws Exception {
		addToCalendar.enterScheduleDateFieldValue(addToCalendar.DateFormate(-1));
		Assert.assertEquals(addToCalendar.endDateError().getText(),
				PropsUtil.getDataPropertyValue("calScheduleDateerr"));
	}

	@Test(description = "AT-68348:Verify Add to Calendar default tag validation", priority = 51, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void defaultTagValidation() throws Exception {
		add_manual_transaction.tagClick();
		add_manual_transaction.clickTagInputField();
		List<String> defaultTag = util.getWebElementsValue(add_manual_transaction.tagList());
		util.assertExpectedActualList(defaultTag, PropsUtil.getDataPropertyValue("TagPredefinedTag"),
				"verify All default Tag");
		Assert.assertEquals(add_manual_transaction.AddManualTransactTag().getAttribute("placeholder"),
				PropsUtil.getDataPropertyValue("TransactionTagGoastText"), "Add Tags... gost text should dsiaplyed");
	}

	@Test(description = "AT-68350:Verify Add to Calendar tag field min value validation", priority = 52, dependsOnMethods = {
			"defaultTagValidation" })
	public void TagFieldMinValidation() throws Exception {

		add_manual_transaction.enterTagFieldValue(PropsUtil.getDataPropertyValue("calTag1"));
		add_manual_transaction.AddManualTransactTag().sendKeys(Keys.ENTER);
		Assert.assertEquals(addToCalendar.tagErorr().getText(), PropsUtil.getDataPropertyValue("calTagerr"));
	}

	@Test(description = "AT-68367:Verify Add to Calendar tag field max value validation", priority = 53, dependsOnMethods = {
			"defaultTagValidation" })
	public void TagFieldMaxValidation() throws Exception {
		add_manual_transaction.enterTagFieldValue(PropsUtil.getDataPropertyValue("calTag2"));
		Assert.assertTrue(add_manual_transaction.tagMaxCharValidation());
	}

	@Test(description = "AT-68352:Verify Add to Calendar tag delete", priority = 54, dependsOnMethods = {
			"defaultTagValidation" })
	public void tagdelete() throws Exception {
		add_manual_transaction.createtagnew(PropsUtil.getDataPropertyValue("tag1"));
		tag.deleteTag(0);
		Assert.assertTrue(add_manual_transaction.doesTagsSizeMatchWithGivenNumber(0));
	}

	@Test(description = "AT-68347,AT-68351:Verify Add to Calendar unlimited tag", priority = 55, dependsOnMethods = {
			"tagdelete" })
	public void unlimietedTag() throws Exception {
		add_manual_transaction.addUnlimitedTAg(PropsUtil.getDataPropertyValue("AddToCal_TagList1"));
		Assert.assertTrue(add_manual_transaction.doesTagsSizeMatchWithGivenNumber(7));
	}

	@Test(description = "AT-68319:Verify Add to Calendar currency field dropdown value", priority = 56, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void verifyCurrencyDropDownValue() throws Exception {
		SeleniumUtil.click(add_manual_transaction.currencyV());
		List<String> Actual = new ArrayList<String>();

		Actual = util.getWebElementsValue(add_manual_transaction.CurrencyList());
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			add_manual_transaction.mobilecurrencyV().click();
		} else {
			SeleniumUtil.click(add_manual_transaction.currencyV());
		}
		util.assertExpectedActualList(Actual, PropsUtil.getDataPropertyValue("AllCurrency"),
				"all curency value should be dsiplayed");
	}

	@Test(description = "AT-68330:Verify Add to Calendar frequency field dropdown value validation", priority = 57, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void verifyFrequencyDropDownValue() throws Exception {
		SeleniumUtil.click(addToCalendar.Frequency());
		List<String> actual = new ArrayList<String>();
		actual = util.getWebElementsValue(add_manual_transaction.FrequencyList());
		util.assertExpectedActualList(actual, PropsUtil.getDataPropertyValue("calFrq"),
				"all curency value should be dsiplayed");

	}

	@Test(description = "AT-68431:Verify Add to Calendar category field validation", priority = 58)
	public void catValueValidation() {
		Assert.assertTrue(add_manual_transaction.allcategoriesValue());
	}

	@Test(description = "AT-68353:Verify Add to Calendar show more text", priority = 59, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void showMoreText() throws Exception {
		Assert.assertEquals(addToCalendar.ShowMore().getText(), PropsUtil.getDataPropertyValue("AggShowMore"),
				"show more text should be displayed");
	}

	@Test(description = "AT-68353,AT-68355,AT-68356:Verify Add to Calendar note field", priority = 60, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void notefieldValidation() throws Exception {
		addToCalendar.clickShowMore();
		addToCalendar.enterNoteFieldValue(PropsUtil.getDataPropertyValue("calNote25"));
		Assert.assertTrue(addToCalendar.noteFieldMAxValidation());

	}

	@Test(description = "AT-68361:Verify Add to Calendar end date error message", priority = 61, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void verifyEndDateError() throws Exception {
		addToCalendar.enterEndDateFieldValue(add_manual_transaction.targateDate1(-1));
		Assert.assertEquals(addToCalendar.endDateError().getText(),
				PropsUtil.getDataPropertyValue("AddCal_ScheduledDate_PastDateError"),
				"Date Should be a future date error message should displayed");
	}

	@Test(description = "AT-68360:Verify Add to Calendar scheduled date error message", priority = 62, dependsOnMethods = {
			"amountDecimalmaxValueValidation" })
	public void verifScheduledDateError() throws Exception {
		addToCalendar.enterScheduleDateFieldValue(add_manual_transaction.targateDate1(-1));
		Assert.assertEquals(addToCalendar.scheduleErorr().getText(),
				PropsUtil.getDataPropertyValue("AddCal_ScheduledDate_PastDateError"),
				" Date Should be a future date error message should displayed");
	}

	@Test(description = "AT-68367:Verify Add to Calendar check field validation", priority = 63, dependsOnMethods = {
			"notefieldValidation" })
	public void checkfieldValidation() throws Exception {
		addToCalendar.changeFrequency(0);
		SeleniumUtil.waitForPageToLoad(1000);
		addToCalendar.enterCheckFieldValue(PropsUtil.getDataPropertyValue("calcheck"));
		Assert.assertTrue(addToCalendar.checkFieldMAxValidation());
	}

	@Test(description = "AT-68354:Verify Add to Calendar show less text ", priority = 64, dependsOnMethods = {
			"notefieldValidation" })
	public void showLessText() throws Exception {
		Assert.assertEquals(addToCalendar.ShowMore().getText(), PropsUtil.getDataPropertyValue("AggShowLess"),
				"show less text should be displayed");
	}

	@Test(description = "AT-68357:Verify Add to Calendar show less hide", priority = 65, dependsOnMethods = {
			"notefieldValidation" })
	public void showLessHide() throws Exception {
		addToCalendar.clickShowMore();
		logger.info(addToCalendar.ShowLessHide_ATC().getAttribute("class"));
		Assert.assertTrue(addToCalendar.ShowLessHide_ATC().getAttribute("class").contains("hide"));
	}

	@Test(description = "AT-68364,AT-68376:Verify event is created", priority = 66)
	public void createEvent() throws Exception {
		// AddToCalendar.PendingStranctionList.get(0).click();
		// Verify New Event pop up closed and navigate to landing screen after click on
		// the SAVE button
		// Verify the below message displayed after click on the SAVE button ""Event
		// added to calendar"

		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(0));
		} else {
			SeleniumUtil.click(addToCalendar.PendingStranctionList().get(0));
		}
		SeleniumUtil.click(addToCalendar.addcalLink());
		addToCalendar.amountField().clear();
		addToCalendar.amountField().sendKeys(PropsUtil.getDataPropertyValue("EventAount"));
		addToCalendar.description().clear();
		addToCalendar.description().sendKeys(PropsUtil.getDataPropertyValue("AddcalTraDesc"));
		eventDescription = addToCalendar.description().getAttribute("value");
		addToCalendar.openFrequencyDropDown();
		evenFrequenc = addToCalendar.frequencyList().get(1).getText();
		SeleniumUtil.click(addToCalendar.frequencyList().get(1));
		eventCatg = Series.catdropdown().getText();
		EventAccountName = addToCalendar.bankName().getText();
		add_manual_transaction.createtag(PropsUtil.getDataPropertyValue("calTag3"));
		SeleniumUtil.click(addToCalendar.ShowMore());
		addToCalendar.NoteField().sendKeys(PropsUtil.getDataPropertyValue("calNote4"));
		SeleniumUtil.click(addToCalendar.addCal());
		Assert.assertEquals(addToCalendar.sucessMessage().getText(), PropsUtil.getDataPropertyValue("Sucessmessage"));
	}

	@Test(description = "AT-68362:verify add to caneldar popup closed when clikc on add button", priority = 67, dependsOnMethods = {
			"createEvent" })
	public void VerifySaveclick() throws Exception {
		Assert.assertTrue(SeleniumUtil.getElementCount(addToCalendar.popupHeaders) == 0,
				"After clicking on save popup is not going off");
	}

	@Test(description = "AT-68365,AT-68370,AT-68371,AT-68372,AT-68374,AT-68334,AT-68366:verify event1 is created in projected transaction section", priority = 68, dependsOnMethods = {
			"createEvent" })
	public void verifycreateDEvents1() throws Exception {
		// Verify the user edited changes in the New Event reflects for the respective
		// transaction in landing screen
		// Verify the recurrance transaction created fall under the projected
		// transaction section if the cobrand configured to display 1 month of projected
		// Example: if user selects frequency = 1 WEEK for the 9th april transactions
		// then it has show 16th april,23 april,30 april,7th may
		// Verify the new event added reflected in the landing screen
		// Verify the user selected frequency type displayed in projected transactions
		// row after adding the event

		boolean expected = false;
		layout.serachTransaction(Series.DateFormate(Series.getwithoutWeekEndDAte(7)),
				Series.DateFormate(Series.getwithoutWeekEndDAte(7)));
		if (Series.getAllaccount1().get(0).getText() == null) {
			SeleniumUtil.click(add_manual_transaction.ProjectedExapdIcon());
		}
		String Description = null;
		String Accountname = null;
		String catg = null;
		String Amount = null;
		String Freq = null;
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText()
					.equals("$" + PropsUtil.getDataPropertyValue("EventAount") + ".00")) {
				expected = true;
				Description = Series.getAlldescription1().get(i).getText();
				Accountname = Series.getAllaccount1().get(i).getText().replaceAll("\n", "");
				catg = Series.getAllcat1().get(i).getText();
				Amount = Series.getAllAmount1().get(i).getText();
				Freq = Series.SeriesFrrequency().get(i).getText();
				SeleniumUtil.click(Series.getAllAmount1().get(i));
				break;
			}
		}
		Assert.assertTrue(expected);
		Assert.assertEquals(Description, eventDescription);
		// Assert.assertTrue(EventAccountName.replace("-Checking",
		// "").contains(Accountname));
		Assert.assertEquals(Accountname, PropsUtil.getDataPropertyValue("AddCalTransactionAccount1"));
		Assert.assertEquals(catg, eventCatg);
		Assert.assertEquals(Amount, "$" + PropsUtil.getDataPropertyValue("EventAount") + ".00");
		SeleniumUtil.assertContains(evenFrequenc, Freq.trim(), "");
	}

	@Test(description = "AT-68374,AT-68371verify event1 transaction detauls values", priority = 69, dependsOnMethods = {
			"verifycreateDEvents1" })
	public void VerifycreateDEventsDetailsPage() throws Exception {

		String detailsamount = Series.amountField().getAttribute("value");
		String detailsdescription = Series.DescriptionField().getAttribute("value");
		String detailsSceduleddate = Series.Scheduledate().getAttribute("value");
		String detailscategory = Series.catReadOnly_SRT().getText();
		String detailsAccountName = Series.accountName().getText();
		String detailsTag = agg.AggregatedListTag().get(0).getText();
		SeleniumUtil.click(agg.ShowMore());
		String detailsNote = agg.note().getText();
		Assert.assertEquals(detailsamount, PropsUtil.getDataPropertyValue("AddCalTransactionAmount"));
		Assert.assertEquals(detailsdescription, PropsUtil.getDataPropertyValue("AddcalTraDesc"));
		Assert.assertEquals(detailsSceduleddate, Series.DateFormate(Series.getwithoutWeekEndDAte(7)));
		Assert.assertEquals(detailscategory, eventCatg);
		Assert.assertEquals(detailsAccountName, PropsUtil.getDataPropertyValue("AddCalTransactiondetailsAccount"));
		Assert.assertEquals(detailsTag, PropsUtil.getDataPropertyValue("calTag3"));
		Assert.assertEquals(detailsNote, PropsUtil.getDataPropertyValue("calNote4"));

	}

	@Test(description = "AT-68365,AT-68371,AT-68372,AT-68374,AT-68366,AT-68370,AT-68375:verify event2 is created in projected transaction section", priority = 70, dependsOnMethods = {
			"createEvent" })
	public void VerifycreateDEvents2() throws Exception {

		// Verify the user edited changes in the New Event reflects for the respective
		// transaction in landing screen
		// Verify the recurrance transaction created fall under the projected
		// transaction section if the cobrand configured to display 1 month of projected
		// Example: if user selects frequency = 1 WEEK for the 9th april transactions
		// then it has show 16th april,23 april,30 april,7th may
		// Verify the new event added reflected in the landing screen
		// Verify the user selected frequency type displayed in projected transactions
		// row after adding the event

		boolean expected = false;
		layout.serachTransaction(Series.DateFormate(Series.getwithoutWeekEndDAte(14)),
				Series.DateFormate(Series.getwithoutWeekEndDAte(14)));
		String Description = null;
		String Accountname = null;
		String catg = null;
		String Amount = null;
		String Freq = null;
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			if (Series.getAllAmount1().get(i).getText()
					.equals("$" + PropsUtil.getDataPropertyValue("EventAount") + ".00")) {
				expected = true;
				Description = Series.getAlldescription1().get(i).getText();
				Accountname = Series.getAllaccount1().get(i).getText().replaceAll("\n", "");
				catg = Series.getAllcat1().get(i).getText();
				Amount = Series.getAllAmount1().get(i).getText();
				Freq = Series.SeriesFrrequency().get(i).getText();
				SeleniumUtil.click(Series.getAllAmount1().get(i));
				break;

			}
		}
		Assert.assertTrue(expected);
		Assert.assertEquals(Description, eventDescription);
		// Assert.assertTrue(EventAccountName.replace("-Checking",
		// "").contains(Accountname));
		Assert.assertEquals(Accountname, PropsUtil.getDataPropertyValue("AddCalTransactionAccount1"));
		Assert.assertEquals(catg, eventCatg);
		Assert.assertEquals(Amount, "$" + PropsUtil.getDataPropertyValue("EventAount") + ".00");
		SeleniumUtil.assertContains(evenFrequenc, Freq.trim(), "");
	}

	@Test(description = "AT-69026:created event original transaction details ", priority = 71, dependsOnMethods = {
			"createEvent" })
	public void VerifycreateDEventsOrginalTransactionDetails() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(addToCalendar.PendingStranctionList().get(0));
		SeleniumUtil.waitFor(2);
		String detailsamount = agg.Amount().getText();
		String detailsdescription = agg.descField().getAttribute("value");
		String detailsSceduleddate = agg.tansactiondate().getText();
		String detailscategory = agg.catgoryField().getText();
		String detailsAccountName = agg.accountName().getText();
		int detailsTag = SeleniumUtil.getElementCount(agg.aggregatedTags);
		SeleniumUtil.click(agg.ShowMore());
		String detailsNote = agg.note().getAttribute("value");

		Assert.assertEquals(detailsamount, PropsUtil.getDataPropertyValue("AddCalOriginalTransactiondetailsAmount"));
		// Assert.assertEquals(detailsdescription,
		// PropsUtil.getDataPropertyValue("AddCalOriginalTransactiondetailsAmountDescription"));
		SeleniumUtil.assertContains(detailsdescription, eventCatg, "");
		Assert.assertEquals(detailsSceduleddate, add_manual_transaction.targateDate1(0));
		Assert.assertEquals(detailscategory, eventCatg);
		Assert.assertEquals(detailsAccountName,
				PropsUtil.getDataPropertyValue("AddCalOriginalTransactiondetailsAccount"));
		Assert.assertEquals(detailsTag, 0);
		Assert.assertEquals(detailsNote, "");
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
