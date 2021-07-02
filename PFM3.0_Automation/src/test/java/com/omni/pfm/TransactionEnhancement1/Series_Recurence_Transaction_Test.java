/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import static org.testng.Assert.fail;
import static com.omni.pfm.utility.SeleniumUtil.assertContains;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Attachment_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.utility.GenericUtil.dateOperations;
import com.omni.pfm.utility.GenericUtil.dateParameters;

public class Series_Recurence_Transaction_Test extends TestBase {
	Add_Manual_Transaction_Loc Addmanual_Transaction;
	Series_Recurence_Transaction_Loc Series_Tranc_details;
	private WebDriver driver;
	Transaction_Layout_Loc layout;
	Transaction_Search_Loc search;
	Aggregated_Transaction_Details_Loc agg;
	private static final Logger logger = LoggerFactory.getLogger(Series_Recurence_Transaction_Test.class);
	public static String userName = "";
	LoginPage login;
	AddToCalendar_Transaction_Loc cal;
	AccountAddition accountAdd;
	Transaction_Attachment_Loc attachment;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Login");
		TestBase.tc = TestBase.extent.startTest("Transaction1", "Login");
		TestBase.test.appendChild(TestBase.tc);
		driver = super.getDriver();
		Series_Tranc_details = new Series_Recurence_Transaction_Loc(d);
		Addmanual_Transaction = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		search = new Transaction_Search_Loc(d);
		agg = new Aggregated_Transaction_Details_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		cal = new AddToCalendar_Transaction_Loc(d);
		attachment = new Transaction_Attachment_Loc(d);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logger.info("Verify login happenes successfully.");
	}

	@Test(priority = 1)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "addcal.bank1", "bank1");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68381,AT-68379:verify series transaction details screen", priority = 2, groups = {
			"Smoke" })
	public void seriesTransactionDetailsScreen() throws Exception {
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			Addmanual_Transaction.addManualLink().click();
		}
		String date = Series_Tranc_details.DateFormate(99);
		Addmanual_Transaction.createTransactionWithRecur1("1005", "Description", 0, 4, 0, date, 1, 1);
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(
					formatter.format(GenericUtil.modifyTheGivenDate(new Date(), dateOperations.ADDITION,
							dateParameters.Months, 1)),
					formatter.format(GenericUtil.modifyTheGivenDate(new Date(), dateOperations.ADDITION,
							dateParameters.Months, 1)));
		} else {
			layout.serachTransaction(
					formatter.format(GenericUtil.modifyTheGivenDate(new Date(), dateOperations.ADDITION,
							dateParameters.Months, 1)),
					formatter.format(GenericUtil.modifyTheGivenDate(new Date(), dateOperations.ADDITION,
							dateParameters.Months, 1)));
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		try {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		} catch (Exception e) {
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		for (int i = 0; i < Series_Tranc_details.getAlldescription1().size(); i++) {
			if (Series_Tranc_details.getAlldescription1().get(i).getText().equals("Description")) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		assertContains(PropsUtil.getDataPropertyValue("Txn_SeriesTxn_radioButtonSelected"),
				Series_Tranc_details.justRadioSelected().getAttribute("class"),
				"just this transaction radio button by default selected");
	}

	@Test(description = "AT-68378:verify series transaction just this rodio button", priority = 3, dependsOnMethods = "seriesTransactionDetailsScreen")
	public void justRadioText() throws Exception {
		assertContains(PropsUtil.getDataPropertyValue("JustText"), Series_Tranc_details.justthisText().getText(),
				"Just this transaction text is not as expected");
	}

	@Test(description = ":verify series transaction all radio button", priority = 4, dependsOnMethods = "seriesTransactionDetailsScreen")
	public void allradioButton() throws Exception {
		Assert.assertTrue(Series_Tranc_details.allradioButton().isDisplayed(), "All radio button is not displayed");
	}

	@Test(description = "AT-68378:verify series transaction all radio button text", priority = 5, dependsOnMethods = "seriesTransactionDetailsScreen")
	public void allradioButtontext() throws Exception {
		assertContains(PropsUtil.getDataPropertyValue("allradioButtonText"),
				Series_Tranc_details.allradioButtonText().getText(), "All radio button text is not as expected");
	}

	@Test(description = "AT-68378:verify series transaction just radio button able to unselect", priority = 6, dependsOnMethods = "seriesTransactionDetailsScreen")
	public void justRadiounSelected() throws Exception {
		Series_Tranc_details.allradioButtonText().click();
		Assert.assertFalse(
				Series_Tranc_details.justRadioSelected().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("Txn_SeriesTxn_radioButtonSelected")),
				"justhis transaction radio button by default selected");
		assertContains(PropsUtil.getDataPropertyValue("Txn_SeriesTxn_radioButtonSelected"),
				Series_Tranc_details.allradioButton().getAttribute("class"),
				"justhis transaction radio button by default selected");
	}

	@Test(description = "AT-68389:verify series transaction amount field", priority = 7, dependsOnMethods = "justRadiounSelected")
	public void amountfieldLabel() throws Exception {
		Assert.assertEquals(Series_Tranc_details.amountLabel().getText(),
				PropsUtil.getDataPropertyValue("SEAmountLabel"), "Amount field label text is not as expected");
	}

	@Test(description = "AT-68385:verify series transaction amount field value", priority = 8, dependsOnMethods = "justRadiounSelected")
	public void amountfieldValue() throws Exception {
		Assert.assertEquals(
				Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.amountField().getAttribute("id")),
				PropsUtil.getDataPropertyValue("SEAmount"), "Value in amount field is not as expected.");
	}

	@Test(description = "AT-68389:verify series transaction description field Label", priority = 9, dependsOnMethods = "justRadiounSelected")
	public void descriptionfieldLabel() throws Exception {
		Assert.assertEquals(Series_Tranc_details.DescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("SEDecLabel"), "Description field label is not as expected.");
	}

	@Test(description = ":verify series transaction description field value", priority = 10, dependsOnMethods = "justRadiounSelected")
	public void descriptionfieldValue() throws Exception {
		Assert.assertEquals(
				Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.DescriptionField().getAttribute("id")),
				PropsUtil.getDataPropertyValue("SEDecription1"), "Value of description field is not as expected.");
	}

	@Test(description = "AT-68389:verify series transaction account field Label", priority = 11, dependsOnMethods = "justRadiounSelected")
	public void creditTofieldLabel() throws Exception {
		Assert.assertEquals(Series_Tranc_details.creditTo().getText(), PropsUtil.getDataPropertyValue("SEcreditTo"),
				"Credited to field label is not as expected.");
	}

	@Test(description = "AT-68385:verify series transaction account field value", priority = 12, dependsOnMethods = "justRadiounSelected")
	public void creditTofieldValue() throws Exception {
		Assert.assertEquals(Series_Tranc_details.accountName().getText(),
				PropsUtil.getDataPropertyValue("SEAcountName1"), "Value of credited to field is not as expected.");
	}

	@Test(description = "AT-68389:verify series transaction frequency field Label", priority = 13, dependsOnMethods = "justRadiounSelected")
	public void FrequencyfieldLabel() throws Exception {
		Assert.assertEquals(Series_Tranc_details.frequencylabelSeries().getText(),
				PropsUtil.getDataPropertyValue("SEFreLabel"), "Frequency field label is not as expected.");
	}

	@Test(description = "AT-68385:verify series transaction frequency field value", priority = 14, dependsOnMethods = "justRadiounSelected")
	public void FrequencyfieldValue() throws Exception {
		Assert.assertEquals(Series_Tranc_details.frequencyValueSeries().getText(),
				PropsUtil.getDataPropertyValue("SEFrequenctValue"), "Value of frequency field is not as expected.");
	}

	@Test(description = "AT-68389:verify series transaction scheduled date field Label", priority = 15, dependsOnMethods = "justRadiounSelected")
	public void ScheduleDatefieldLabel() throws Exception {
		Assert.assertEquals(Series_Tranc_details.ScheduleDateLabel().getText(),
				PropsUtil.getDataPropertyValue("SEScheduleDateLabel"), "Schedule date field label is not as expected.");
	}

	@Test(description = ":verify series transaction scheduled date field value", priority = 16, dependsOnMethods = "justRadiounSelected")
	public void ScheduleDatefieldValue() throws Exception {
		Assert.assertEquals(
				Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.Scheduledate().getAttribute("id")),
				Series_Tranc_details.getNextmonthdate(), "Value of schedule date field is not as expected.");
	}

	@Test(description = "AT-68389:verify series transaction category field Label", priority = 17, dependsOnMethods = "justRadiounSelected")
	public void catagoryfieldLabel() throws Exception {
		Assert.assertEquals(Series_Tranc_details.categoryLabel().getText(),
				PropsUtil.getDataPropertyValue("SECatLabel"), "Category field label is not as expected.");
	}

	@Test(description = ":verify series transaction category field value", priority = 18, dependsOnMethods = "justRadiounSelected")
	public void categoryfieldValue() throws Exception {
		String actual = Series_Tranc_details.catdropdown().getText();
		String expected = PropsUtil.getDataPropertyValue("SECatValue");
		assertContains(expected, actual, "Value of category field is not as expected.");
	}

	@Test(description = "AT-68439:verify series transaction amount field error message", priority = 19, dependsOnMethods = "justRadiounSelected")
	public void amountFieldErrormessage() throws Exception {
		Series_Tranc_details.amountField().clear();
		Series_Tranc_details.amountField().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.amountFieldErr().getText(),
				PropsUtil.getDataPropertyValue("SEErorrMessage"), "Amount field error message is not proper.");
	}

	@Test(description = "AT-68411:verify series transaction amount field max value validation", priority = 20, dependsOnMethods = "amountFieldErrormessage")
	public void amountfield12charErrorMessage() throws Exception {
		Series_Tranc_details.amountField().sendKeys(PropsUtil.getDataPropertyValue("SEAmount1"));
		Series_Tranc_details.amountField().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.amountFieldErr().getText(),
				PropsUtil.getDataPropertyValue("SEamounterr"),
				"Amount field 12 char error message is not as exepcted.");
	}

	@Test(description = "Verify amount field for 16 char value", priority = 21, dependsOnMethods = "amountFieldErrormessage")
	public void amountfieldAllow16Char() throws Exception {
		Series_Tranc_details.amountField().clear();
		Series_Tranc_details.amountField().sendKeys(PropsUtil.getDataPropertyValue("SEamount2"));
		if (Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.amountField().getAttribute("id"))
				.length() != 16) {
			fail("Amount field is not allowing 16 characters");
		}
	}

	@Test(description = "AT-68411,AT-68439:verify series transaction description field empty error message", priority = 22, dependsOnMethods = "justRadiounSelected")
	public void descriptionfieldErrorMessage() throws Exception {
		Series_Tranc_details.DescriptionField().clear();
		Series_Tranc_details.DescriptionField().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.DescriptionFieldErr().getText(),
				PropsUtil.getDataPropertyValue("SEErorrMessage"),
				"Description field error message is not as expected.");
	}

	@Test(description = ":verify series transaction scheduled date calendar popup", priority = 23, enabled = false)
	public void ScheduleDatefieldCalenderPopUp() throws Exception {
		// failing due to requirement changed
		Series_Tranc_details.Scheduledate().click();
		SeleniumUtil.waitForPageToLoad();
		boolean expected = Series_Tranc_details.calendarPopup().isDisplayed();
		Series_Tranc_details.Scheduledate().click();
		Assert.assertTrue(expected, "Calendar popup is not displayed.");
	}

	@Test(description = ":verify series transaction scheduled date field calendar icon", priority = 24, dependsOnMethods = "justRadiounSelected")
	public void ScheduleDatefieldDateIcon() throws Exception {
		Assert.assertTrue(Series_Tranc_details.ScheduledateIcon().isDisplayed(),
				"Scheduled date icon is not displayed.");
	}

	@Test(description = "AT-68422:verify series transaction scheduled date calendar popup", priority = 25, dependsOnMethods = "justRadiounSelected")
	public void scheduleDatefieldDateIconClick() throws Exception {
		Series_Tranc_details.ScheduledateIcon().click();
		SeleniumUtil.waitFor(2);
		boolean expected = Series_Tranc_details.calendarPopup().isDisplayed();
		Series_Tranc_details.ScheduledateIcon().click();
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68411,AT-68439:verify series transaction scheduled date field error message", priority = 26, dependsOnMethods = "scheduleDatefieldDateIconClick")
	public void scheduleDatefieldError() throws Exception {
		Series_Tranc_details.Scheduledate().clear();
		Series_Tranc_details.Scheduledate().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.ScheduledateErr().getText(),
				PropsUtil.getDataPropertyValue("SEErorrMessage"));
	}

	@Test(description = "AT-68423:verify series transaction scheduled date field invalid error message", priority = 27, dependsOnMethods = "scheduleDatefieldError")
	public void InvalidScheduleDate() throws Exception {
		Series_Tranc_details.Scheduledate().sendKeys("65");
		Series_Tranc_details.Scheduledate().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.ScheduledateErr().getText(),
				PropsUtil.getDataPropertyValue("SEScheduleDateErr"));
	}

	@Test(description = "AT-68412,AT-68432:verify series transaction category field dropdown", priority = 28, dependsOnMethods = "justRadiounSelected")
	public void catagoryfield() throws Exception {
		Series_Tranc_details.catdropDownIon().click();
		boolean expected = agg.catgoryList(1, 1).isDisplayed();
		Series_Tranc_details.catdropDownIon().click();
		Assert.assertTrue(expected);
	}

	@Test(description = ":verify series transaction tag field ", priority = 29, dependsOnMethods = "justRadiounSelected")
	public void tagField() throws Exception {
		Assert.assertTrue(agg.TransactionTagLink().isDisplayed());
	}

	@Test(description = ":verify series transaction tag field icon", priority = 30, enabled = false)
	public void tagFieldIcon() throws Exception {
		// makinf false due functionality change
		Assert.assertTrue(Series_Tranc_details.addtagicon().isDisplayed());
	}

	@Test(description = "AT-68409:verify series transaction tag field info message", priority = 31, dependsOnMethods = "justRadiounSelected")
	public void tagFieldInfo() throws Exception {
		Assert.assertEquals(Series_Tranc_details.customtaginfo().getText(),
				PropsUtil.getDataPropertyValue("AggTagInfo"));
	}

	@Test(description = "AT-68411:verify series transaction tag field min char validation", priority = 32, dependsOnMethods = "tagField")
	public void tagField3char() throws Exception {
		SeleniumUtil.click(agg.TransactionTagLink());
		agg.TransactionTagField().sendKeys("aa");
		SeleniumUtil.click(agg.TransactionTagCreate());
		Assert.assertEquals(Series_Tranc_details.addtagErr().getText(), PropsUtil.getDataPropertyValue("SETagErr"));
	}

	@Test(description = "AT-68391:verify series transaction show less options", priority = 33, dependsOnMethods = "tagField3char")
	public void ShowLessOPtion() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertEquals(Series_Tranc_details.shoeMore().getText(), PropsUtil.getDataPropertyValue("AggShowLess"));
	}

	@Test(description = "AT-68390,AT-68392:verify series transaction note field", priority = 34, dependsOnMethods = "ShowLessOPtion")
	public void NoteField() throws Exception {
		Assert.assertTrue(Series_Tranc_details.noteField().isDisplayed());
	}

	@Test(description = "AT-68390,AT-68392,AT-68456,AT-68457:verify series transaction delete option", priority = 36, dependsOnMethods = "ShowLessOPtion")
	public void deleteoption() throws Exception {
		Assert.assertTrue(Series_Tranc_details.deleteTransaction().isDisplayed());
	}

	@Test(description = "AT-68427:verify series transaction cancel button", priority = 37, dependsOnMethods = "justRadiounSelected")
	public void cancel() throws Exception {
		Assert.assertTrue(Series_Tranc_details.canceltransaction().isDisplayed());
	}

	@Test(description = "AT-68427:verify series transaction update button", priority = 38, dependsOnMethods = "justRadiounSelected")
	public void updateOption() throws Exception {
		Assert.assertTrue(Series_Tranc_details.updateTransaction().isDisplayed());
	}

	@Test(description = ":verify series transaction show more option", priority = 39, dependsOnMethods = "ShowLessOPtion")
	public void ShowMoreOPtion() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertEquals(Series_Tranc_details.shoeMore().getText(), PropsUtil.getDataPropertyValue("AggShowMore"));
		// Assert.assertTrue(Series_Tranc_details.checkField().isDisplayed());
	}

	@Test(description = "AT-68413:verify series transaction create rule info", priority = 40, dependsOnMethods = "justRadiounSelected")
	public void createRuleInfo() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Series_Tranc_details.catdropDownIon());
		// Series_Tranc_details.catdropDownIon.click();
		SeleniumUtil.click(agg.catgoryList(2, 1));
		// Series_Tranc_details.ListCat(1,1).click();
		Assert.assertEquals(Series_Tranc_details.createRuleInfo().getText(),
				PropsUtil.getDataPropertyValue("AggCatChangeMessage"));
	}

	@Test(description = "AT-68415:verify series transaction create rule icon", priority = 41, dependsOnMethods = "createRuleInfo")
	public void createRuleIcon() throws Exception {
		Assert.assertTrue(Series_Tranc_details.createRuleIcon().isDisplayed());
	}

	@Test(description = "AT-68416:verify series transaction create rule link", priority = 42, dependsOnMethods = "createRuleInfo")
	public void createRuleLink() throws Exception {
		Assert.assertTrue(Series_Tranc_details.createRuleLink().isDisplayed());
	}

	@Test(description = "AT-68417:verify series transaction create rule popup", priority = 43, dependsOnMethods = "createRuleInfo")
	public void createRulePopUp() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.createRuleIcon());
		Assert.assertTrue(Series_Tranc_details.rulePopUp().isDisplayed());
	}

	@Test(description = "AT-68419,AT-68420:verify series transaction create rule success message", priority = 44, dependsOnMethods = "createRulePopUp")
	public void createRuleSucessMessage() throws Exception {
		agg.TransactionCatRuleAmount().clear();
		agg.TransactionCatRuleAmount().sendKeys(PropsUtil.getDataPropertyValue("SECreateRuleAmount"));
		SeleniumUtil.click(agg.TransactionCatRuleCategoryLink());
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(agg.TransactionCatRuleCategoryList(1, 1));
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(agg.ruleSave());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(Series_Tranc_details.sucessMessage().getText(),
				PropsUtil.getDataPropertyValue("AggRuleSucessMessage"));
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
	}

	@Test(description = "AT-68430:verify series transaction details screen should closed when click on cancel button", priority = 45, dependsOnMethods = "justRadiounSelected")
	public void cancelTransaction() throws Exception {
		Series_Tranc_details.canceltransaction().click();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(Series_Tranc_details.updateTransactionlist().get(0).getAttribute("class").trim()
				.equals("transaction-row-wrap"));
	}

	@Test(description = "AT-68389,AT-68390,AT-68404:verify series transaction field when select this radio button option", priority = 46, dependsOnMethods = "cancelTransaction")
	public void verifyInstancetxnFieldLabel() throws Exception {
		for (int i = 0; i < Series_Tranc_details.getAlldescription1().size(); i++) {
			if (Series_Tranc_details.getAlldescription1().get(i).getText().equals("Description")) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertEquals(Series_Tranc_details.amountLabel().getText(),
				PropsUtil.getDataPropertyValue("SEAmountLabel"), "amount field Label should not be displayed");
		Assert.assertEquals(Series_Tranc_details.DescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("SEDecLabel"), "description field Label should not be displayed");
		Assert.assertEquals(Series_Tranc_details.creditTo().getText(), PropsUtil.getDataPropertyValue("SEcreditTo"),
				"account field Label should not be displayed");
		Assert.assertEquals(Series_Tranc_details.frequencylabel().getText(),
				PropsUtil.getDataPropertyValue("SEFreLabel"), "frequemcy field Label should not be displayed");
		Assert.assertEquals(Series_Tranc_details.ScheduleDateLabel().getText(),
				PropsUtil.getDataPropertyValue("SEScheduleDateLabel"), "scheduled field Label should not be displayed");
		Assert.assertEquals(Series_Tranc_details.categoryLabel_Inst().getText(),
				PropsUtil.getDataPropertyValue("SECatLabel"), "category field Label should not be displayed");
		Assert.assertTrue(Series_Tranc_details.noteField().isDisplayed(), "note field Label should not be displayed");
		Assert.assertTrue(Series_Tranc_details.checkField().isDisplayed(), "note field Label should not be displayed");
		Assert.assertTrue(Series_Tranc_details.Attachment().isDisplayed(), "attachment link should not be displayed");
		Assert.assertTrue(Series_Tranc_details.deleteTransaction().isDisplayed(),
				"delete link should not be displayed");
		Assert.assertTrue(Series_Tranc_details.enddateHide_SRT().getAttribute("style").contains("none"),
				"end date field Label should not be displayed");
	}

	@Test(description = "AT-68385:verify series transaction field value when selected just this radio button", priority = 47, dependsOnMethods = "verifyInstancetxnFieldLabel")
	public void verifyInstancetxnFieldValue() throws Exception {
		Assert.assertEquals(Series_Tranc_details.amountField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SEAmount"), "amount field value is displayed");
		Assert.assertEquals(Series_Tranc_details.amountCurrency_SRT().getText(),
				PropsUtil.getDataPropertyValue("SECurrencySymbol"), "currency symbool value is displayed");
		Assert.assertEquals(Series_Tranc_details.DescriptionField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SEDecription1"), "description field value is displayed");
		Assert.assertEquals(Series_Tranc_details.accountName().getText(),
				PropsUtil.getDataPropertyValue("SEAcountName1"), "account name field value is displayed");
		Assert.assertEquals(Series_Tranc_details.frequencyValue().getText(),
				PropsUtil.getDataPropertyValue("SEFrequenctValue"), "frequency field value is displayed");
		Assert.assertEquals(Series_Tranc_details.Scheduledate().getAttribute("value"),
				Series_Tranc_details.getNextmonthdate(), "scheduled date field value is displayed");
		Assert.assertEquals(Series_Tranc_details.categoryValue_Inst().getText(),
				PropsUtil.getDataPropertyValue("txn_InstanceTxn_Category1"), "category field value is displayed");
	}

	@Test(description = "AT-68404:verify series transaction check field when all radio button is selected", priority = 48, dependsOnMethods = "verifyInstancetxnFieldLabel")
	public void verifyCheckFied_AllRadioButton() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.allradioButtonText());
		Assert.assertTrue(Series_Tranc_details.checkFieldHide_SRT().getAttribute("style").contains("none"),
				"check field is displayed");
		Assert.assertTrue(Series_Tranc_details.NoAttachment_SRT().getAttribute("style").contains("none"),
				"attachment should not dosplyed when series radio button selected");
	}

	@Test(description = "AT-68440,AT-62039,AT-62042:verify series transaction updated info popup", priority = 49, dependsOnMethods = "verifyInstancetxnFieldLabel")
	public void UpadteTheTransaction() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.canceltransaction());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		for (int i = 0; i < Series_Tranc_details.getAlldescription1().size(); i++) {
			if (Series_Tranc_details.getAlldescription1().get(i).getText().equals("Description")) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		SeleniumUtil.click(Series_Tranc_details.allradioButtonText());
		Series_Tranc_details.amountField().clear();
		Series_Tranc_details.amountField().sendKeys(PropsUtil.getDataPropertyValue("SEAmount3"));
		Series_Tranc_details.DescriptionField().clear();
		Series_Tranc_details.DescriptionField().sendKeys(PropsUtil.getDataPropertyValue("SEDescription2"));
		SeleniumUtil.click(Series_Tranc_details.catdropDownIon());
		SeleniumUtil.click(agg.catgoryList(2, 1));
		Series_Tranc_details.Scheduledate().clear();
		Series_Tranc_details.Scheduledate().sendKeys(Series_Tranc_details.DateFormate(32));
		Series_Tranc_details.enddate_SRT().clear();
		Series_Tranc_details.enddate_SRT().sendKeys(Series_Tranc_details.DateFormate(98));
		agg.createTag(PropsUtil.getDataPropertyValue("SeriesTransactionTag1"));
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Series_Tranc_details.noteField().sendKeys(PropsUtil.getDataPropertyValue("SeriesTransactionNote"));
		// Series_Tranc_details.checkField().sendKeys(PropsUtil.getDataPropertyValue("SeriesTransactionCheck"));
		SeleniumUtil.click(Series_Tranc_details.updateTransaction());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(Series_Tranc_details.STUpdatePopUpHeade().get(0).getText(),
				PropsUtil.getDataPropertyValue("STPopUpHeader"));
	}

	@Test(description = "AT-62040:verify series transaction update popup info message", priority = 50, dependsOnMethods = "UpadteTheTransaction")
	public void updatePopupInfiMsg() throws Exception {
		Assert.assertEquals(Series_Tranc_details.STUpdatePopUpInfoMsg().getText(),
				PropsUtil.getDataPropertyValue("STPopUpHeaderInfoMSG"));
	}

	@Test(description = "AT-62044:verify series transaction update popup is closed when click on close button", priority = 51, dependsOnMethods = "updatePopupInfiMsg")
	public void updatePopupCloseclick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.STUpdatePopUpClose());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(!SeleniumUtil.isDisplayed(Series_Tranc_details.stUpdateHeader, 2));
	}

	@Test(description = "AT-68429,AT-62045,AT-62048:verify series transaction upadte popup is closed when click on cancel button", priority = 52, dependsOnMethods = "updatePopupCloseclick")
	public void updatePopupCancelclick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.updateTransaction());
		SeleniumUtil.click(Series_Tranc_details.STUpdatePopUpCancel());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(Series_Tranc_details.stUpdateHeader, 2));
	}

	@Test(description = "AT-68441,AT-62046:verify series transaction update success message when click on confirm ", priority = 53, dependsOnMethods = "updatePopupCancelclick")
	public void updatePopupConfirm() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.updateTransaction());
		SeleniumUtil.click(Series_Tranc_details.STUpdatePopUpConfirm());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(Series_Tranc_details.sucessMessage().getText(),
				PropsUtil.getDataPropertyValue("aggUpdateMessage"));
	}

	@Test(description = "AT-68428,AT-68445,AT-62047:verify series transaction is updated", priority = 54, dependsOnMethods = "updatePopupConfirm")
	public void ValidateUpadteTheTransaction() throws Exception {
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Series_Tranc_details.DateFormate(31), Series_Tranc_details.DateFormate(31));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(30), Series_Tranc_details.DateFormate(36));
			search.allSearch().clear();
			search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("SEDescription2"));
		}
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("SECatValue3_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("SECatValue3");
		}
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount4"))) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				Assert.assertEquals(Series_Tranc_details.getAllAmount1().get(i).getText(),
						PropsUtil.getDataPropertyValue("SEAmount4"));
				Assert.assertEquals(Series_Tranc_details.getAlldescription1().get(i).getText(),
						PropsUtil.getDataPropertyValue("SEDescription2"));
				Assert.assertEquals(Series_Tranc_details.getAllcat1().get(i).getText(), cateName);
				break;
			}
		}
	}

	@Test(description = ":verify series transaction attachment is displaying when this radio button is selected", priority = 55, dependsOnMethods = "ValidateUpadteTheTransaction")
	public void Attachment() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertTrue(Series_Tranc_details.Attachment().isDisplayed());
	}

	@Test(description = ":verify series transaction check field when selected just this radio button", priority = 56, groups = {
			"Smoke" }, enabled = true, dependsOnMethods = "Attachment")
	public void CheckTransactionOption() throws Exception {
		// Note and Check Number ,Note fields and Add Attachment,Delete transaction link
		// should display after click on the "SHOW MORE OPTIONS" link
		Assert.assertTrue(Series_Tranc_details.checkField().isDisplayed());
	}

	@Test(description = "AT-68393,AT-68394:verify series transaction delete popup header", priority = 57, groups = {
			"Smoke" }, dependsOnMethods = "Attachment")
	public void deleteTransaction() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		SeleniumUtil.waitForPageToLoad(1000);
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			Assert.assertEquals(Series_Tranc_details.MobileDeletePopUp().get(0).getText(),
					PropsUtil.getDataPropertyValue("InstdeleteHeader"));
		} else {
			Assert.assertEquals(Series_Tranc_details.rulePopUp().getText(),
					PropsUtil.getDataPropertyValue("InstdeleteHeader"));
		}
	}

	@Test(description = "AT-68395,AT-68451:verify series transaction delete popup message when just this radio button is selected", priority = 58, groups = {
			"Smoke" }, dependsOnMethods = "deleteTransaction")
	public void DeleteTransactionPopUpMessage() throws Exception {
		Assert.assertEquals(Series_Tranc_details.seriesdeleteMessage().getText(),
				PropsUtil.getDataPropertyValue("InstdeleteMessage"));
	}

	@Test(description = "AT-68397,AT-68452:verify series transaction delete popup cancel", priority = 59, groups = {
			"Smoke" }, dependsOnMethods = "deleteTransaction")
	public void DeleteTransactionPopUpCancel() throws Exception {
		Assert.assertTrue(Series_Tranc_details.Deletecancel().isDisplayed());
	}

	@Test(description = "AT-68397,AT-68452:verify series transaction delete popup delete button", priority = 60, groups = {
			"Smoke" }, dependsOnMethods = "deleteTransaction")
	public void DeleteTransactionPopUpDelete() throws Exception {
		Assert.assertTrue(Series_Tranc_details.deleteButton().isDisplayed());
	}

	@Test(description = "AT-68453:verify series transaction deletepopup should closed when click on cancel button", priority = 61, groups = {
			"Smoke" }, dependsOnMethods = "deleteTransaction")
	public void DeleteTransactionPopUpCancelClick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.Deletecancel());
		boolean expected = false;
		int size;
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			size = Series_Tranc_details.MobileDeletePopUp().size();
		} else {
			size = SeleniumUtil.getElementCount(Series_Tranc_details.rulePopup);
		}
		if (size == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68453:verify series transaction delete popup should closed when click on close button", priority = 62, groups = {
			"Smoke" }, dependsOnMethods = "DeleteTransactionPopUpCancelClick")
	public void DeleteTransactionPopUpCloseClick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		// Series_Tranc_details.deleteTransaction().click();
		SeleniumUtil.click(Series_Tranc_details.deleteclose());
		boolean expected = false;
		int size;
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			size = Series_Tranc_details.MobileDeletePopUp().size();
		} else {
			size = SeleniumUtil.getElementCount(Series_Tranc_details.rulePopup);
		}
		if (size == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-62041,AT-62043:verify instance transaction updtaed with out update popup", priority = 63, groups = {
			"Smoke" }, dependsOnMethods = "DeleteTransactionPopUpCloseClick")
	public void instanceTraUpdate() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.updateTransaction());
		boolean expected = false;
		if (!SeleniumUtil.isDisplayed(Series_Recurence_Transaction_Loc.popupHeader, 3)) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68396,AT-68448,AT-68450:verify series transaction delete popup when all radio button selected", priority = 64, dependsOnMethods = "instanceTraUpdate")
	public void deleteSeriesTransactionHeader() throws Exception {
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount4"))) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				SeleniumUtil.waitForPageToLoad(2000);
				break;
			}
		}
		SeleniumUtil.click(Series_Tranc_details.allradioButtonText());
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			Assert.assertEquals(Series_Tranc_details.MobileDeletePopUp().get(0).getText(),
					PropsUtil.getDataPropertyValue("deleteHeader"));
		} else {
			Assert.assertEquals(Series_Tranc_details.rulePopUp().getText(),
					PropsUtil.getDataPropertyValue("deleteHeader"));
		}
	}

	@Test(description = "AT-68400:verify series transaction delete popup message when all radio button selected", priority = 65, dependsOnMethods = "deleteSeriesTransactionHeader")
	public void DeleteSeriesTransactionMessage() throws Exception {
		Assert.assertEquals(Series_Tranc_details.seriesdeleteMessage().getText(),
				PropsUtil.getDataPropertyValue("SerideleteMessage"));
	}

	@Test(description = ":verify series transaction delete popup is displaying cancel button", priority = 66, groups = {
			"Smoke" }, dependsOnMethods = "DeleteSeriesTransactionMessage")
	public void DeleteSeriesTransactionPopUpCancel() throws Exception {
		Assert.assertTrue(Series_Tranc_details.Deletecancel().isDisplayed());
	}

	@Test(description = ":verify series transaction delete popup is displaying  delete button ", priority = 67, groups = {
			"Smoke" }, dependsOnMethods = "DeleteSeriesTransactionMessage")
	public void DeleteSeriesTransactionPopUpDelete() throws Exception {
		Assert.assertTrue(Series_Tranc_details.deleteButton().isDisplayed());
	}

	@Test(description = "AT-68398:verify series transaction deleted popup closed when click on cancel button", priority = 68, groups = {
			"Smoke" }, dependsOnMethods = "DeleteSeriesTransactionMessage")
	public void DeleteSeriesTransactionPopUpCancelClick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.Deletecancel());
		boolean expected = false;
		int size;
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			size = Series_Tranc_details.MobileDeletePopUp().size();
		} else {
			size = SeleniumUtil.getElementCount(Series_Tranc_details.rulePopup);
		}
		if (size == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68398:verify series transaction delete popup closed when click on close button", priority = 69, groups = {
			"Smoke" }, dependsOnMethods = "DeleteSeriesTransactionPopUpCancelClick")
	public void DeleteSeriesTransactionPopUpCloseClick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		// Series_Tranc_details.deleteTransaction().click();
		SeleniumUtil.click(Series_Tranc_details.deleteclose());
		boolean expected = false;
		int size;
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			size = Series_Tranc_details.MobileDeletePopUp().size();
		} else {
			size = SeleniumUtil.getElementCount(Series_Tranc_details.rulePopup);
		}
		if (size == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68403,AT-68449,AT-68465:verify series transaction delete success message", priority = 70)
	public void deletedOneSriesTransaction() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(Addmanual_Transaction.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount4"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 14, 1, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(1000);
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Series_Tranc_details.DateFormate(7), Series_Tranc_details.DateFormate(7));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(7), Series_Tranc_details.DateFormate(7));
		}
		try {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		} catch (Exception e) {
		}
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount5"))) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		SeleniumUtil.click(Series_Tranc_details.allradioButtonText());
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		SeleniumUtil.click(Series_Tranc_details.deleteButton());
		Assert.assertEquals(Series_Tranc_details.series_Delete_Message().getText(),
				PropsUtil.getDataPropertyValue("SEdeleteMessage"));
	}

	@Test(description = "AT-68403:verify series transaction first instance is deleted", priority = 71, dependsOnMethods = "deletedOneSriesTransaction")
	public void vrifyFirstDeletedSriesTransaction() throws Exception {
		if (Series_Tranc_details.getAllAmount1().size() != 0) {
			for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
				logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
				if (Series_Tranc_details.getAllAmount1().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("SEAmount5"))) {
					Assert.assertTrue(false);
					break;
				}
			}
		} else {
			Assert.assertTrue(true);
		}
	}

	@Test(description = "AT-68403:verify series transaction second instance is deleted", priority = 72, dependsOnMethods = "deletedOneSriesTransaction")
	public void vrifysecondDeletedSriesTransaction() throws Exception {
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Series_Tranc_details.DateFormate(14), Series_Tranc_details.DateFormate(14));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(14), Series_Tranc_details.DateFormate(14));
		}
		if (Series_Tranc_details.getAllAmount1().size() != 0) {
			for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
				logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
				if (Series_Tranc_details.getAllAmount1().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("SEAmount5"))) {
					Assert.assertTrue(false);
					break;
				}
			}
		} else {
			Assert.assertTrue(true);
		}
	}

	@Test(description = "AT-68403:verify series transaction one instance should delete when just radio button selected", priority = 73, dependsOnMethods = "deletedOneSriesTransaction")
	public void deletedOneInsatnceTransaction() throws Exception {
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(Addmanual_Transaction.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount5"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 14, 1, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(1000);
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Series_Tranc_details.DateFormate(7), Series_Tranc_details.DateFormate(7));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(7), Series_Tranc_details.DateFormate(7));
		}
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount6"))) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		SeleniumUtil.click(Series_Tranc_details.deleteButton());
		if (Series_Tranc_details.getAllAmount1().size() != 0) {
			for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
				logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
				if (Series_Tranc_details.getAllAmount1().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("SEAmount6"))) {
					Assert.assertTrue(false,
							"Deleted series transaction with amount ::" + PropsUtil.getDataPropertyValue("SEAmount6") + " is still coming");
					break;
				}
			}
		} else {
			Assert.assertTrue(true);
		}
	}

	@Test(description = "AT-68403:verify series transaction should not delete when select just this radio", priority = 74, dependsOnMethods = "deletedOneInsatnceTransaction")
	public void undeletedDeletedOneInsatnceTransaction() throws Exception {
		boolean value = false;
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Series_Tranc_details.DateFormate(14), Series_Tranc_details.DateFormate(14));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(14), Series_Tranc_details.DateFormate(14));
		}
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount6"))) {
				value = true;
				break;
			}
		}
		Assert.assertTrue(value);
	}

	@Test(description = "AT-68437,AT-68447:verify series transaction attachment link should not displayed", priority = 75, dependsOnMethods = "undeletedDeletedOneInsatnceTransaction")
	public void verifyAttachmentdisplyed() throws Exception {
		SeleniumUtil.click(Addmanual_Transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad();
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount6"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 14, 1, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		layout.serachTransaction(Series_Tranc_details.DateFormate(7), Series_Tranc_details.DateFormate(7));
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount7").trim())) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.click(Series_Tranc_details.allradioButtonText());
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertTrue(Series_Tranc_details.NoAttachment_SRT().getAttribute("style").contains("none"),
				"attachment should not dosplyed when series radio button selected");
	}

	@Test(description = "AT-68404,AT-62050:verify instance transaction attached file should  not displayed for series transaction", priority = 76, dependsOnMethods = "verifyAttachmentdisplyed")
	public void verifyAttachedfielddisplyed() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.justRadioSelected());
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		attachment.attachFile(a);
		Series_Tranc_details.allradioButtonText().click();
		Assert.assertTrue(Series_Tranc_details.NoAttachment_SRT().getAttribute("style").contains("none"),
				"attachment should not dosplyed when series radio button selected");
	}

	@Test(description = "AT-68404,AT-62049:verify instance transaction attached file should  not displayed for series transaction ", priority = 77, dependsOnMethods = "verifyAttachedfielddisplyed")
	public void verifyAttachedfieldsplyed() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.justRadioSelected());
		SeleniumUtil.click(Series_Tranc_details.updateTransaction());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount7").trim())) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		Series_Tranc_details.allradioButtonText().click();
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(Series_Tranc_details.NoAttachment_SRT().getAttribute("style").contains("none"),
				"attachment should not dosplyed when series radio button selected");
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
