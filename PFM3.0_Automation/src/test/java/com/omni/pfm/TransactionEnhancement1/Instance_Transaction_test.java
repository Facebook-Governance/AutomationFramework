/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

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
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Instance_Transaction_test extends TestBase {
	Add_Manual_Transaction_Loc Addmanual_Transaction;
	Series_Recurence_Transaction_Loc Series_Tranc_details;
	private WebDriver driver;
	public Transaction_Layout_Loc layout;
	AddToCalendar_Transaction_Loc AddToCalendar;
	SeleniumUtil util;
	private static final Logger logger = LoggerFactory.getLogger(Instance_Transaction_test.class);
	public static String userName = "";
	Transaction_Search_Loc search;
	Aggregated_Transaction_Details_Loc agg_Transaction_details;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Login");
		TestBase.tc = TestBase.extent.startTest("Transaction1", "Login");
		TestBase.test.appendChild(TestBase.tc);
		driver = super.getDriver();
		Addmanual_Transaction = new Add_Manual_Transaction_Loc(d);
		Series_Tranc_details = new Series_Recurence_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		search = new Transaction_Search_Loc(d);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		agg_Transaction_details = new Aggregated_Transaction_Details_Loc(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "verify instance transaction update button", priority = 2, groups = { "Smoke" }, enabled = true)
	public void editSeriesTransaction() throws Exception {
		logger.info(
				"AT-31034:TRA_04_01:Verify the Edit Screen displayed after click on the transaction row in landing page");
		SeleniumUtil.waitForPageToLoad(5000);
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			Addmanual_Transaction.addManualLink().click();
		}
		logger.info(Series_Tranc_details.DateInMMMMFormate(30));
		Addmanual_Transaction.createTransactionWithOutNote(PropsUtil.getDataPropertyValue("InstAmount1"),
				PropsUtil.getDataPropertyValue("InstDescription1"), 4, 30, 1, 1);
		if (layout.isMobileFilterIconPresent()) {
			// layout.serachTransaction(cal.getTime(), cal.getTime());
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(30), Series_Tranc_details.DateFormate(30));
		}
		try {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		} catch (Exception e) {
		}
		SeleniumUtil.waitForPageToLoad(500);
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText().equals("$100.00")) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		Assert.assertTrue(Series_Tranc_details.updateTransaction().isDisplayed());
	}

	@Test(description = "AT-68380,AT-68208:verify radio button should not displayed for instance transaction", priority = 3, groups = {
			"Smoke" }, enabled = true)
	public void verifyRadioButton() {
		Assert.assertTrue(!SeleniumUtil.isDisplayed(Series_Tranc_details.radioButtonSelected, 2));
	}

	@Test(description = "AT-68380:verify instance transaction", priority = 4, groups = { "Smoke" }, enabled = true)
	public void verifyAllRadioButton() {
		Assert.assertTrue(!SeleniumUtil.isDisplayed(Series_Tranc_details.allRadioButtonSelected, 2));
	}

	@Test(description = ":verify instance transaction amount lable", priority = 5, groups = { "Smoke" }, enabled = true)
	public void amountLable() throws Exception {
		Assert.assertEquals(Series_Tranc_details.amountLabel().getText(),
				PropsUtil.getDataPropertyValue("SEAmountLabel"));
	}

	@Test(description = ":verify instance transaction description lable", priority = 6, groups = {
			"Smoke" }, enabled = true)
	public void descLable() throws Exception {
		Assert.assertEquals(Series_Tranc_details.DescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("SEDecLable"));
	}

	@Test(description = ":verify instance transaction account lable", priority = 7, groups = {
			"Smoke" }, enabled = true)
	public void AccountFiledLable() throws Exception {
		Assert.assertEquals(Series_Tranc_details.creditTo().getText(), PropsUtil.getDataPropertyValue("InstDebtFrom"));
	}

	@Test(description = ":verify instance transaction frequency field", priority = 8, groups = {
			"Smoke" }, enabled = true)
	public void Frequencyfiled() throws Exception {
		Assert.assertEquals(Series_Tranc_details.frequencylabel().getText(),
				PropsUtil.getDataPropertyValue("SEFreLabel"));
	}

	@Test(description = ":verify instance transaction cateory field ", priority = 9, groups = {
			"Smoke" }, enabled = true)
	public void catagoryfiled() throws Exception {
		Assert.assertEquals(Series_Tranc_details.categoryLabel().getText(),
				PropsUtil.getDataPropertyValue("SECatLabel"));
	}

	@Test(description = ":verify instance transaction scheduled date field", priority = 10, groups = {
			"Smoke" }, enabled = true)
	public void ScheduleDatefiled() throws Exception {
		// Verify the Calendar displayed after click on the Calendar icon and
		Assert.assertEquals(Series_Tranc_details.ScheduleDateLabel().getText(),
				PropsUtil.getDataPropertyValue("SEScheduleDateLabel"));
	}

	@Test(description = ":verify instance transaction tag field", priority = 11, groups = { "Smoke" }, enabled = true)
	public void tagField() throws Exception {
		Assert.assertTrue(agg_Transaction_details.TransactionTagLink().isDisplayed());
	}

	@Test(description = ":verify instance transaction amount value", priority = 12, groups = {
			"Smoke" }, enabled = true)
	public void amountValue() throws Exception {
		Assert.assertEquals(
				Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.amountField().getAttribute("id")), "100.00");
	}

	@Test(description = ":verify instance transaction amount field error message", priority = 13, groups = {
			"Smoke" }, enabled = true)
	public void amountEmpltyValueErrorValidation() throws Exception {
		Series_Tranc_details.amountField().clear();
		Series_Tranc_details.amountField().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.amountFieldErr().getText(),
				PropsUtil.getDataPropertyValue("SEErorrMessage"));
	}

	@Test(description = ":verify instance transaction amount field error message", priority = 14, groups = {
			"Smoke" }, enabled = true)
	public void amountInvalidValueErrorValidation() throws Exception {
		Series_Tranc_details.amountField().sendKeys(PropsUtil.getDataPropertyValue("SEAmount1"));
		Assert.assertEquals(Series_Tranc_details.amountFieldErr().getText(),
				PropsUtil.getDataPropertyValue("SEamounterr"));
	}

	@Test(description = ":verify instance transaction amount field error message", priority = 15, groups = {
			"Smoke" }, enabled = true)
	public void amountMaxValueValidation() throws Exception {
		Series_Tranc_details.amountField().sendKeys(PropsUtil.getDataPropertyValue("SEamount2"));
		if (Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.amountField().getAttribute("id"))
				.length() != 16) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = ":verify instance transaction description field error message", priority = 16, groups = {
			"Smoke" }, enabled = true)
	public void descriptionfiledValue() throws Exception {
		Assert.assertEquals(
				Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.DescriptionField().getAttribute("id")),
				PropsUtil.getDataPropertyValue("InstDescription1"));
	}

	@Test(description = "AT-68439:verify instance transaction description field error message", priority = 17, groups = {
			"Smoke" }, enabled = true)
	public void descriptionfiledErrorValidation() throws Exception {
		Series_Tranc_details.DescriptionField().clear();
		Series_Tranc_details.DescriptionField().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.DescriptionFieldErr().getText(),
				PropsUtil.getDataPropertyValue("SEErorrMessage"));
	}

	@Test(description = ":verify instance transaction account field value", priority = 18, groups = {
			"Smoke" }, enabled = true)
	public void creditTofiledAccountName() throws Exception {
		Assert.assertEquals(Series_Tranc_details.accountName().getText(),
				PropsUtil.getDataPropertyValue("SEAcountName"));
	}

	@Test(description = ":verify instance transaction frequency field value", priority = 19, groups = {
			"Smoke" }, enabled = true)
	public void FrequencyfiledValue() throws Exception {
		Assert.assertEquals(Series_Tranc_details.frequencyValue().getText(),
				PropsUtil.getDataPropertyValue("InstFrequency1"));
	}

	@Test(description = ":verify instance transaction scheduled date field ", priority = 20, groups = {
			"Smoke" }, enabled = false)
	public void ScheduleDatefiledClick() throws Exception {
		// failng due to required ment changed
		Series_Tranc_details.Scheduledate().click();
		Assert.assertTrue(Series_Tranc_details.calendarPopup().isDisplayed());
		Series_Tranc_details.Scheduledate().click();
		Assert.assertTrue(Series_Tranc_details.ScheduledateIcon().isDisplayed());
	}

	@Test(description = ":verify instance transaction scheduled date calendar icon", priority = 21, groups = {
			"Smoke" }, enabled = true)
	public void ScheduleDatefiledIcon() throws Exception {
		Series_Tranc_details.ScheduledateIcon().click();
		Assert.assertTrue(Series_Tranc_details.calendarPopup().isDisplayed());
		Series_Tranc_details.ScheduledateIcon().click();
	}

	@Test(description = ":verify instance transaction scheduled date value", priority = 22, groups = {
			"Smoke" }, enabled = true)
	public void ScheduleDatefiledValue() throws Exception {
		Assert.assertEquals(
				Series_Tranc_details.getAtributeVAlue(Series_Tranc_details.Scheduledate().getAttribute("id")),
				Series_Tranc_details.DateFormate(30));
	}

	@Test(description = "AT-68439:verify instance transaction date field empty value message validation", priority = 23, groups = {
			"Smoke" }, enabled = true)
	public void ScheduleDatefiledEmptyValueError() throws Exception {
		Series_Tranc_details.Scheduledate().clear();
		Series_Tranc_details.Scheduledate().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.ScheduledateErr().getText(),
				PropsUtil.getDataPropertyValue("SEErorrMessage"));
	}

	@Test(description = "AT-68439:verify instance transaction scheduled date error message validation", priority = 24, groups = {
			"Smoke" }, enabled = true)
	public void ScheduleDatefiledinvalidValueError() throws Exception {
		Series_Tranc_details.Scheduledate().sendKeys("65");
		Series_Tranc_details.Scheduledate().sendKeys(Keys.TAB);
		Assert.assertEquals(Series_Tranc_details.ScheduledateErr().getText(),
				PropsUtil.getDataPropertyValue("SEScheduleDateErr"));
	}

	@Test(description = ":verify instance transaction catgeory field value", priority = 25, groups = {
			"Smoke" }, enabled = true)
	public void catagoryfiledValue() throws Exception {
		Assert.assertTrue(
				Series_Tranc_details.catdropdown().getText().contains(PropsUtil.getDataPropertyValue("Cat33")));
	}

	@Test(description = ":verify instance transaction category field dropdown", priority = 26, groups = {
			"Smoke" }, enabled = true)
	public void catagoryfiledDropDown() throws Exception {
		Series_Tranc_details.catdropDownIon().click();
		boolean catgory = Series_Tranc_details.ListCat(1, 1).isDisplayed();
		if (Series_Tranc_details.IscatclosePresent()) {
			SeleniumUtil.click(Series_Tranc_details.catgClose());
		} else {
			Series_Tranc_details.catdropDownIon().click();
		}
		Assert.assertTrue(catgory);
	}

	@Test(description = ":verify instance transaction tag field icon", priority = 27, groups = {
			"Smoke" }, enabled = false)
	public void tagFieldIcon() throws Exception {
		// making false due to invalid
		Assert.assertTrue(Series_Tranc_details.addtagicon().isDisplayed());
	}

	@Test(description = ":verify instance transaction tag field info", priority = 9, groups = {
			"Smoke" }, enabled = true)
	public void tagFieldInfo() throws Exception {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			SeleniumUtil.click(Series_Tranc_details.tagField());
		}
		Assert.assertEquals(Series_Tranc_details.customtaginfo().getText(),
				PropsUtil.getDataPropertyValue("AggTagInfo"));
	}

	@Test(description = ":verify instance transaction show less option", priority = 28, groups = {
			"Smoke" }, enabled = true)
	public void ShowLessOPtion() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertEquals(Series_Tranc_details.shoeMore().getText(), PropsUtil.getDataPropertyValue("AggShowLess"));
	}

	@Test(description = ":verify instance transaction delete transaction option", priority = 29, groups = {
			"Smoke" }, enabled = true)
	public void deletTransactionOption() throws Exception {
		// Note and Check Number ,Note fields and Add Attachment,Delete transaction link
		// should display after click on the "SHOW MORE OPTIONS" link
		// Note and Delete options should reflect in edit screen for both recurrence and
		// instance transactions
		// Delete option should reflect for both System identified and Manual
		// Transactions
		Assert.assertTrue(Series_Tranc_details.deleteTransaction().isDisplayed());
	}

	@Test(description = ":verify instance transaction note option", priority = 30, groups = { "Smoke" }, enabled = true)
	public void noteTransactionOption() throws Exception {
		// Note and Check Number ,Note fields and Add Attachment,Delete transaction link
		// should display after click on the "SHOW MORE OPTIONS" link
		// Note and Delete options should reflect in edit screen for both recurrence and
		// instance transactions
		Assert.assertTrue(Series_Tranc_details.noteField().isDisplayed());
	}

	@Test(description = ":verify instance transaction attachment option", priority = 31, groups = {
			"Smoke" }, enabled = true)
	public void attachmentTransactionOption() throws Exception {
		// Note and Check Number ,Note fields and Add Attachment,Delete transaction link
		// should display after click on the "SHOW MORE OPTIONS" link
		Assert.assertTrue(Series_Tranc_details.Attachment().isDisplayed());
	}

	@Test(description = ":verify instance transaction check option", priority = 32, groups = {
			"Smoke" }, enabled = true)
	public void CheckTransactionOption() throws Exception {
		// Note and Check Number ,Note fields and Add Attachment,Delete transaction link
		// should display after click on the "SHOW MORE OPTIONS" link
		Assert.assertTrue(Series_Tranc_details.checkField().isDisplayed());
	}

	@Test(description = ":verify instance transaction show more option ", priority = 33, groups = {
			"Smoke" }, enabled = true)
	public void ShowMoreOPtion() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Assert.assertEquals(Series_Tranc_details.shoeMore().getText(), PropsUtil.getDataPropertyValue("AggShowMore"));
	}

	@Test(description = ":verify instance transaction create rule info message", priority = 34, groups = {
			"Smoke" }, enabled = true)
	public void createRuleinfo() throws Exception {
		// Verify the text "Would you like to create rule for this category change?"
		// displayed below the category field after changing the category
		SeleniumUtil.click(Series_Tranc_details.catdropDownIon());
		SeleniumUtil.click(Series_Tranc_details.ListCat(1, 1));
		Assert.assertEquals(Series_Tranc_details.createRuleInfo().getText(),
				PropsUtil.getDataPropertyValue("AggCatChangeMessage"));
	}

	@Test(description = ":verify instance transaction create rule icon", priority = 35, groups = {
			"Smoke" }, enabled = true)
	public void createRuleIcon() throws Exception {
		Assert.assertTrue(Series_Tranc_details.createRuleIcon().isDisplayed());
	}

	@Test(description = ":verify instance transaction create rule link", priority = 36, groups = {
			"Smoke" }, enabled = true)
	public void createRuleLink() throws Exception {
		Assert.assertTrue(Series_Tranc_details.createRuleLink().isDisplayed());
	}

	@Test(description = ":verify instance transaction create rule popup", priority = 37, groups = {
			"Smoke" }, enabled = true)
	public void createRulePopUp() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.createRuleIcon());
		Assert.assertTrue(Series_Tranc_details.rulePopUp().isDisplayed());
	}

	@Test(description = ":verify instance transaction create rule success message", priority = 38, groups = {
			"Smoke" }, enabled = true)
	public void createRuleSuccrssMessage() throws Exception {
		agg_Transaction_details.TransactionCatRuleAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("InstanceTransactionRuleAmount"));
		SeleniumUtil.click(agg_Transaction_details.TransactionCatRuleCategoryLink());
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(agg_Transaction_details.TransactionCatRuleCategoryList(5, 3));
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(agg_Transaction_details.ruleSave());
		SeleniumUtil.waitForPageToLoad(600);
		Assert.assertEquals(Series_Tranc_details.sucessMessage().getText(),
				PropsUtil.getDataPropertyValue("AggRuleSucessMessage"));
	}

	@Test(description = ":verify instance transaction cancel option", priority = 39, groups = {
			"Smoke" }, enabled = true)
	public void canceloption() throws Exception {
		Assert.assertTrue(Series_Tranc_details.canceltransaction().isDisplayed());
	}

	@Test(description = ":verify instance transaction update option", priority = 40, groups = {
			"Smoke" }, enabled = true)
	public void updateoption() throws Exception {
		Assert.assertTrue(Series_Tranc_details.updateTransaction().isDisplayed());
	}

	@Test(description = ":verify instance transaction cancel details screen", priority = 41, groups = {
			"Smoke" }, enabled = true)
	public void cancelTransaction() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.canceltransaction());
		Assert.assertTrue(Series_Tranc_details.updateTransactionlist().get(0).getAttribute("class").trim()
				.equals("transaction-row-wrap"));
	}

	@Test(description = ":verify instance transaction updated", priority = 42, groups = { "Smoke" }, enabled = true)
	public void UpadteTheTransaction() throws Exception {
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText().equals("$100.00")) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
				break;
			}
		}
		Series_Tranc_details.amountField().clear();
		Series_Tranc_details.amountField().sendKeys(PropsUtil.getDataPropertyValue("SEAmount3"));
		Series_Tranc_details.DescriptionField().clear();
		Series_Tranc_details.DescriptionField().sendKeys(PropsUtil.getDataPropertyValue("SEDescription2"));
		SeleniumUtil.click(Series_Tranc_details.catdropDownIon());
		SeleniumUtil.click(Series_Tranc_details.ListCat(2, 1));
		Series_Tranc_details.Scheduledate().clear();
		Series_Tranc_details.Scheduledate().sendKeys(Series_Tranc_details.DateFormate(31));
		// Series_Tranc_details.tagField().sendKeys("aaa");
		// SeleniumUtil.click(Series_Tranc_details.addtagicon());
		agg_Transaction_details.createTag(PropsUtil.getDataPropertyValue("InstanceTransactionTag"));
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		Series_Tranc_details.noteField().sendKeys(PropsUtil.getDataPropertyValue("InstanceTransactionNote"));
		SeleniumUtil.click(Series_Tranc_details.updateTransaction());
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Addmanual_Transaction.targateDate1(31),
					Addmanual_Transaction.targateDate1(31));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(31), Series_Tranc_details.DateFormate(31));
		}
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SEAmount4"))) {
				Assert.assertEquals(Series_Tranc_details.getAllAmount1().get(i).getText(),
						PropsUtil.getDataPropertyValue("SEAmount4"));
				Assert.assertEquals(Series_Tranc_details.getAlldescription1().get(i).getText(),
						PropsUtil.getDataPropertyValue("SEDescription2"));
				Assert.assertEquals(Series_Tranc_details.getAllcat1().get(i).getText(),
						PropsUtil.getDataPropertyValue("SeriesCatUpdated"));
				break;
			}
		}
	}

	@Test(description = ":verify investment transaction", priority = 43, groups = { "Smoke" }, enabled = true)
	public void InvestmentTransaction() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(Addmanual_Transaction.addManualIcon());
		}
		Addmanual_Transaction.createInvestmentTransaction(
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentAmount"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentdescription"), 2, 1, 2, 31,
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentSymbol"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentCUSIP"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentQuantity"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentprice"), 1, 1);
		if (layout.isMobileFilterIconPresent()) {
			layout.serachTransactionMobile(Addmanual_Transaction.targateDate1(31),
					Addmanual_Transaction.targateDate1(31));
		} else {
			layout.serachTransaction(Series_Tranc_details.DateFormate(31), Series_Tranc_details.DateFormate(31));
		}
		if (Series_Tranc_details.getAllAmount1().get(0).getText() == null) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
			logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
			if (Series_Tranc_details.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentSearch"))) {
				SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
			}
		}
	}

	@Test(description = "AT-68434:verify investment transaction symbool", priority = 44, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionSymbol() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_Symbol().isDisplayed());
	}

	@Test(description = "AT-68434:verify investment transaction CUSIP", priority = 45, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionCUSIP() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_CUSIP().isDisplayed());
	}

	@Test(description = "AT-68434:verify investment transaction type ", priority = 46, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionType() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_InvestmentType().isDisplayed());
	}

	@Test(description = "AT-68434:verify investment transaction price", priority = 47, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionPrice() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_Price().isDisplayed());
	}

	@Test(description = "AT-68434:verify investment transaction quantity", priority = 48, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionQuantity() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_Quantity().isDisplayed());
	}

	@Test(description = "AT-68434:verify investment transaction holading type ", priority = 49, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionHolding() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_Holding().isDisplayed());
	}

	@Test(description = "AT-68434 :verify investment transaction lot", priority = 50, groups = {
			"Smoke" }, enabled = true)
	public void InvestmentTransactionLot() throws Exception {
		Assert.assertTrue(Series_Tranc_details.series_Lot().isDisplayed());
	}

	@Test(description = "verify instance transaction delete option", priority = 51, groups = { "Smoke" })
	public void DeleteTransaction() throws Exception {
		if (Series_Tranc_details.canceltransaction_SRT_mobile()) {
			SeleniumUtil.click(Series_Tranc_details.canceltransaction());
			for (int i = 0; i < Series_Tranc_details.getAllAmount1().size(); i++) {
				logger.info(Series_Tranc_details.getAllAmount1().get(i).getText());
				if (Series_Tranc_details.getAllAmount1().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("SEAmount4"))) {
					SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(i));
					break;
				}
			}
		}
		SeleniumUtil.click(Series_Tranc_details.shoeMore());
		// Series_Tranc_details.shoeMore.click();
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		// Series_Tranc_details.deleteTransaction.click();
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			Assert.assertEquals(Series_Tranc_details.MobileDeletePopUp().get(0).getText(),
					PropsUtil.getDataPropertyValue("InstdeleteHeader"));
		} else {
			Assert.assertEquals(Series_Tranc_details.rulePopUp().getText(),
					PropsUtil.getDataPropertyValue("InstdeleteHeader"));
		}
	}

	@Test(description = ":verify instance transaction detele popup message", priority = 52, groups = { "Smoke" })
	public void DeleteTransactionPopUpMessage() throws Exception {
		Assert.assertEquals(Series_Tranc_details.seriesdeleteMessage().getText(),
				PropsUtil.getDataPropertyValue("InstdeleteMessage"));
	}

	@Test(description = ":verify instance transaction detele popup cancel option", priority = 53, groups = { "Smoke" })
	public void DeleteTransactionPopUpCancel() throws Exception {
		Assert.assertTrue(Series_Tranc_details.Deletecancel().isDisplayed());
	}

	@Test(description = ":verify instance transaction detele popup delete option", priority = 54, groups = { "Smoke" })
	public void DeleteTransactionPopUpDelete() throws Exception {
		Assert.assertTrue(Series_Tranc_details.deleteButton().isDisplayed());
	}

	@Test(description = "verify delete popup should closed when click on cancel button", priority = 55, groups = {
			"Smoke" })
	public void DeleteTransactionPopUpCancelClick() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.Deletecancel());
		boolean expected = false;
		int size;
		if (Series_Tranc_details.IsMobileDeletePopUpPresent()) {
			size = Series_Tranc_details.MobileDeletePopUp().size();
		} else {
			size = Series_Tranc_details.rulePopUpList().size();
		}
		if (size == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "verify delete popup should closed when click on close button", priority = 56, groups = {
			"Smoke" })
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

	@Test(description = "verify delete popup should closed when click on delete button", priority = 57, groups = {
			"Smoke" })
	public void DeletedTransaction() throws Exception {
		SeleniumUtil.click(Series_Tranc_details.deleteTransaction());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(Series_Tranc_details.deleteButton());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(Series_Tranc_details.series_Delete_Message().getText(),
				PropsUtil.getDataPropertyValue("SEdeleteMessage"));
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
