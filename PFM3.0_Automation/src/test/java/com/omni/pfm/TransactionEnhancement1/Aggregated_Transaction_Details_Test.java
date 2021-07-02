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
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Aggregated_Transaction_Details_Test extends TestBase {
	Aggregated_Transaction_Details_Loc Aggre_Tranc_details;
	Add_Manual_Transaction_Loc manual;
	Transaction_Layout_Loc layout;
	AddToCalendar_Transaction_Loc AddToCalendar;
	Transaction_Search_Loc search;
	private static final Logger logger = LoggerFactory.getLogger(Aggregated_Transaction_Details_Test.class);
	public static String userName = "";
	String UpdatedCat = null;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		Aggre_Tranc_details = new Aggregated_Transaction_Details_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		manual = new Add_Manual_Transaction_Loc(d);
		search = new Transaction_Search_Loc(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "verify aggregated transaction collaps icon", priority = 2)
	public void ExpandIconDisplyed() throws Exception {
		Assert.assertTrue(Aggre_Tranc_details.collapsIcon().get(2).isDisplayed());
	}

	@Test(description = "AT-68202,AT-68246,AT-69043,AT-68915:verify aggregated transaction save button", priority = 3)
	public void detailScreenDisplayed() throws Exception {
		logger.info(
				"Verify the screen navigates to landing page after click on the < back link in edit screen in small screen");
		logger.info("Verify the Cancel and Save labels displayed at the end of the Edit Screen");
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		Assert.assertTrue(Aggre_Tranc_details.save().isDisplayed());
	}

	@Test(description = ":verify aggregated transaction details screen should closed when click on save button", priority = 4, dependsOnMethods = {
			"detailScreenDisplayed" })
	public void Verifysaveclick() throws Exception {
		SeleniumUtil.click(Aggre_Tranc_details.save());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(!SeleniumUtil.isDisplayed(Aggre_Tranc_details.saveAggregatedTransaction, 1),
				"Save transaction button is displayed");
	}

	@Test(description = "AT-68222,T-68245,AT-68257,AT-68915,AT-68246:verify aggregated transaction amount label", priority = 5)
	public void verifyAmountlabel() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		Assert.assertEquals(Aggre_Tranc_details.amountlabel().getText(),
				PropsUtil.getDataPropertyValue("AggAmountlabel"));
	}

	@Test(description = "AT-68250,AT-68258:verify aggregated transaction amount value", priority = 6, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyAmountValue() throws Exception {
		// Verify the transaction amount and currency related to the transaction
		// displayed in the Amount field
		// AT-30860::Verify the Amount field(Amount and Currency) is not editable
		Assert.assertEquals(Aggre_Tranc_details.Amount().getText(), PropsUtil.getDataPropertyValue("AggAmount"));
	}

	@Test(description = "AT-68259,AT-68222,AT-68257:verify aggregated transaction description label", priority = 7, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifydesclabel() throws Exception {
		Assert.assertEquals(Aggre_Tranc_details.descriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("aggDesc"));
	}

	@Test(description = "AT-68259,AT-68251:verify aggregated transaction description value", priority = 8, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifydescValue() throws Exception {
		// Verify the Simple Description displayed in the Description Field
		Assert.assertEquals(Aggre_Tranc_details.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("aggDesc1"));
	}

	@Test(description = "AT-68262,AT-68222,AT-68257:verify aggregated transaction  statement label", priority = 9, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyStatementDesclabel() throws Exception {
		Assert.assertEquals(Aggre_Tranc_details.Statment().getText(), PropsUtil.getDataPropertyValue("aggStatment"));
	}

	@Test(description = "AT-68251:verify aggregated transaction statment value", priority = 10, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyStatementDescValue() throws Exception {
		// Verify the Complete/Full description displayed in the "Appears On Statement
		// As" field
		// Verify the Complete/Full description is not editable
		Assert.assertEquals(Aggre_Tranc_details.StatmentDesc().getText(),
				PropsUtil.getDataPropertyValue("AggStatmentDesc"));
	}

	@Test(description = "AT-68263,AT-68222,AT-68257:verify aggregated transaction account label", priority = 11, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyAccountlabel() throws Exception {
		// Verify for the Debit transactions label displayed as "Debited From" and for
		// Credited transctions displayed as "Credited To"
		Assert.assertEquals(Aggre_Tranc_details.CreditDebit().getText(), PropsUtil.getDataPropertyValue("AggCredit"));
	}

	@Test(description = "AT-68264,AT-68229:verify aggregated transaction value", priority = 12, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyAccountName() throws Exception {
		// Verify the Account information displayed next to the Account label "Account
		// name (Masked Account number)
		Assert.assertEquals(Aggre_Tranc_details.accountName().getText(),
				PropsUtil.getDataPropertyValue("AggAccountname"));
	}

	@Test(description = "AT-68265,AT-68222,AT-68257:verify aggregated transaction date label", priority = 13, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyDatelabel() throws Exception {
		Assert.assertEquals(Aggre_Tranc_details.datelabel().getText(), PropsUtil.getDataPropertyValue("AggDatelabel"));
	}

	@Test(description = "AT-68265,AT-68252:verify aggregated transaction date value", priority = 14, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyDateValue() throws Exception {
		// Verify the Transaction date displayed in the Date FieldFormat
		// AT-30869::Verify the Date field is not editabl
		Assert.assertEquals(Aggre_Tranc_details.tansactiondate().getText(), Aggre_Tranc_details.DateFormate(0));
	}

	@Test(description = "AT-68222,AT-68257:verify aggregated transaction categpry value", priority = 15, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifycatValue() throws Exception {
		Assert.assertEquals(Aggre_Tranc_details.catLabel().getText(), PropsUtil.getDataPropertyValue("AggCatLabel"));
	}

	@Test(description = "AT-68282,AT-68257:verify aggregated transactionnote value", priority = 16, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyNote() throws Exception {
		// Verify the Note Field, Attachment link,Split icon displayed after click on
		// the " "SHOW MORE OPTIONS" link
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertTrue(Aggre_Tranc_details.note().isDisplayed());
	}

	@Test(description = "AT-68282,AT-68257:verify aggregated transaction attcahment option", priority = 17, dependsOnMethods = {
			"verifyNote" })
	public void verifyAttachment() throws Exception {
		// Verify the Note Field, Attachment link,Split icon displayed after click on
		// the " "SHOW MORE OPTIONS" link
		Assert.assertTrue(Aggre_Tranc_details.attachment().isDisplayed());
	}

	@Test(description = "AT-68282,AT-68257:verify aggregated transaction split option", priority = 18, dependsOnMethods = {
			"verifyNote" })
	public void verifySplit() throws Exception {
		// Verify the Note Field, Attachment link,Split icon displayed after click on
		// the " "SHOW MORE OPTIONS" link
		Assert.assertTrue(Aggre_Tranc_details.split().isDisplayed());
	}

	@Test(description = "AT-68260:verify aggregated transaction description cab be editable", priority = 20, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifydescriptionEditable() throws Exception {
		Aggre_Tranc_details.descField().clear();
		Aggre_Tranc_details.descField().sendKeys(PropsUtil.getDataPropertyValue("aggDesc2"));
		Aggre_Tranc_details.descField().sendKeys(Keys.TAB);
		Assert.assertEquals(Aggre_Tranc_details.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("aggDesc2"));
	}

	@Test(description = "AT-68295:verify aggregated transaction drciption min value validation", priority = 21, dependsOnMethods = {
			"verifyAmountlabel" })
	public void VerifyDescmin3cha() throws Exception {
		Aggre_Tranc_details.descField().clear();
		Aggre_Tranc_details.descField().sendKeys("aa");
		Assert.assertEquals(Aggre_Tranc_details.descFieldErorr().getText(),
				PropsUtil.getDataPropertyValue("descriptionErr"));
	}

	@Test(description = "AT-68284,AT-68296:verify aggregated transaction all mandatory field", priority = 22, dependsOnMethods = {
			"verifyAmountlabel" })
	public void VerifyAllMandatoryFields() throws Exception {
		Aggre_Tranc_details.descField().clear();
		SeleniumUtil.click(Aggre_Tranc_details.update());
		Assert.assertEquals(Aggre_Tranc_details.descFieldErorr().getText(), PropsUtil.getDataPropertyValue("Erorr"));
	}

	@Test(description = "AT-68267:verify aggregated transaction category dropdown validation", priority = 23, dependsOnMethods = {
			"verifyAmountlabel" })
	public void catgoryDropdownvalidation() throws Exception {
		// Verify the Drop down displayed after click on the "V" icon in the label
		SeleniumUtil.click(Aggre_Tranc_details.catgoryField());
		Assert.assertTrue(Aggre_Tranc_details.catgoryList(4, 1).isDisplayed());
	}

	@Test(description = "AT-68268:verify aggregated transaction category info message", priority = 24, dependsOnMethods = {
			"catgoryDropdownvalidation" })
	public void CatgoryInfovalidation() throws Exception {
		SeleniumUtil.click(Aggre_Tranc_details.catgoryList(4, 1));
		Assert.assertEquals(Aggre_Tranc_details.catChanegMessage().getText(),
				PropsUtil.getDataPropertyValue("AggCatChangeMessage"));
	}

	@Test(description = "AT-68269:verify aggregated transaction create rule option", priority = 25, dependsOnMethods = {
			"CatgoryInfovalidation" })
	public void createRuleButton() throws Exception {
		Assert.assertTrue(Aggre_Tranc_details.createRule().isDisplayed());
	}

	@Test(description = "AT-68271:verify aggregated transaction create rule popup", priority = 26, dependsOnMethods = {
			"CatgoryInfovalidation" })
	public void createRulePopUp() throws Exception {
		SeleniumUtil.click(Aggre_Tranc_details.createRule());
		Assert.assertTrue(Aggre_Tranc_details.AggregatedCreatRulepopUp().isDisplayed());
	}

	@Test(description = "AT-68272:verify aggregated transaction rule is created", priority = 27, dependsOnMethods = "createRulePopUp")
	public void createRule() throws Exception {
		Aggre_Tranc_details.TransactionCatRuleAmount().sendKeys(PropsUtil.getDataPropertyValue("AggRuleAmount"));
		SeleniumUtil.click(Aggre_Tranc_details.TransactionCatRuleCategoryLink());
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(Aggre_Tranc_details.TransactionCatRuleCategoryList(5, 3));
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(Aggre_Tranc_details.ruleSave());
		SeleniumUtil.waitForPageToLoad(500);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")
				&& !PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP")) {
			Assert.assertEquals(Aggre_Tranc_details.SucessMessage().getText(),
					PropsUtil.getDataPropertyValue("AggRuleSucessMessage"));
		}
	}

	@Test(description = "AT-68273:verify aggregated transaction tag info", priority = 28, dependsOnMethods = {
			"verifyAmountlabel" })
	public void TagInfovalidation() throws Exception {
		Assert.assertEquals(Aggre_Tranc_details.taginfomessage().getText(),
				PropsUtil.getDataPropertyValue("AggTagInfo"));
	}

	@Test(description = "AT-68274,AT-68275,AT-68277,AT-68279,AT-68294,AT-68297:verify aggregated transaction unlimited tag validation", priority = 29, dependsOnMethods = {
			"verifyAmountlabel" })
	public void verifyTagUnlimitTag() throws Exception {
		String tagList[] = PropsUtil.getDataPropertyValue("AggTagList").split(",");
		SeleniumUtil.click(Aggre_Tranc_details.TransactionTagLink());
		for (int i = 0; i < 7; i++) {
			Aggre_Tranc_details.createTag1(tagList[i]);
		}
		boolean unlimitTag = false;
		if (Aggre_Tranc_details.AggregatedListTag().size() == 7) {
			unlimitTag = true;
		}
		Assert.assertTrue(unlimitTag);
	}

	@Test(description = "AT-68281,AT-68283,AT-68281:verify aggregated transaction show less option", priority = 30)
	public void showLessOption() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		String showMore = Aggre_Tranc_details.ShowMore().getText();
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		logger.info(Aggre_Tranc_details.ShowMore().getText());
		Assert.assertEquals(showMore, PropsUtil.getDataPropertyValue("AggShowMore"));
		Assert.assertEquals(Aggre_Tranc_details.ShowMore().getText(), PropsUtil.getDataPropertyValue("AggShowLess"));
	}

	@Test(description = "AT-68284:verify aggregated transaction cancel button", priority = 31, dependsOnMethods = {
			"showLessOption" })
	public void Canceldisplayed() throws Exception {
		Assert.assertTrue(Aggre_Tranc_details.cancel().isDisplayed());
	}

	@Test(description = "AT-68288,AT-68293:verify aggregated transaction details should closed when click on cancel button", priority = 32, dependsOnMethods = {
			"Canceldisplayed" })
	public void CancelClick() throws Exception {
		// Verify the edited/changed values not saved after click on the CANCEL button
		// in Edit screen.
		SeleniumUtil.click(Aggre_Tranc_details.cancel());
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		logger.info(Aggre_Tranc_details.catgoryField().getText());
		if (Aggre_Tranc_details.catgoryField().getText().equals(PropsUtil.getDataPropertyValue("AggCat3"))) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68286,AT-68292:verify aggregated transaction update messaged", priority = 33)
	public void verifyUpdatedMessage() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		Aggre_Tranc_details.descField().clear();
		Aggre_Tranc_details.descField().sendKeys(PropsUtil.getDataPropertyValue("aggDesc2"));
		SeleniumUtil.click(Aggre_Tranc_details.catgoryField());
		UpdatedCat = Aggre_Tranc_details.catgoryList(5, 3).getText();
		SeleniumUtil.click(Aggre_Tranc_details.catgoryList(5, 3));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Aggre_Tranc_details.note().clear();
		Aggre_Tranc_details.note().sendKeys(PropsUtil.getDataPropertyValue("TransactionAgreggatedDetailsNote1"));
		SeleniumUtil.click(Aggre_Tranc_details.update());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(Aggre_Tranc_details.SucessMessage().getText(),
				PropsUtil.getDataPropertyValue("aggUpdateMessage"));
	}

	@Test(description = "AT-68285:verify aggregated transaction updated catgeory", priority = 34, dependsOnMethods = {
			"verifyUpdatedMessage" })
	public void verifyUpdatedCatDetail() throws Exception {
		Assert.assertTrue(manual.getAllPostedCat1().get(2).getText().contains(UpdatedCat));
	}

	@Test(description = "AT-68285:verify aggregated transaction updated description", priority = 35, dependsOnMethods = {
			"verifyUpdatedMessage" })
	public void verifyUpdatedDescDetail() throws Exception {
		logger.info(manual.getAlldescription1().get(2).getText());
		Assert.assertTrue(
				manual.getAlldescription1().get(2).getText().contains(PropsUtil.getDataPropertyValue("aggDesc2")));
	}

	@Test(description = "AT-68500:verify aggregated transaction updated note", priority = 36, dependsOnMethods = {
			"verifyUpdatedMessage" })
	public void verifyUpdatedNote() throws Exception {
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(Aggre_Tranc_details.note().getText(),
				PropsUtil.getDataPropertyValue("TransactionAgreggatedDetailsNote1"));
	}

	@Test(description = "AT-68501:verify aggregated transaction note is deleted", priority = 37, dependsOnMethods = {
			"verifyUpdatedMessage" })
	public void verifyDeletedNote() throws Exception {
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionAgreggatedDetailsNote1"));
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Aggre_Tranc_details.note().clear();
		SeleniumUtil.click(Aggre_Tranc_details.update());
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionAgreggatedDetailsNote1"));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(layout.layoutNote,1), "note icon should not displayed");
	}

	@Test(description = "AT-68207:verify aggregated transaction category search ", priority = 80, enabled = false)
	public void catSearchValidation() {
		manual.catsearchField().sendKeys(PropsUtil.getDataPropertyValue("catg2"));
		logger.info(manual.CatSearched().get(0).getAttribute("class"));
		String searchedCategory = manual.CatSearched().get(0).getAttribute("class");
		boolean remaining = false;
		int unsearchedCategory = manual.CatSearched().size();
		for (int i = 1; i < unsearchedCategory; i++) {
			if (manual.CatSearched().get(i).getAttribute("class")
					.contains(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide"))) {
				remaining = true;
			} else {
				remaining = false;
				break;
			}
		}
		Assert.assertFalse(
				searchedCategory.contains(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide")),
				"searched category class should not displayed with hide");
		Assert.assertTrue(remaining, "unsearched category should displayed with hide");
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
