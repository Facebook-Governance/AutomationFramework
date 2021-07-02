/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
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
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Split_Test extends TestBase {
	public Transaction_Split_Locator split;
	AddToCalendar_Transaction_Loc AddToCalendar;
	Aggregated_Transaction_Details_Loc agg;
	Add_Manual_Transaction_Loc add_manual_transaction;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Split_Test.class);
	public static String userName = "";
	LoginPage login;
	AccountAddition accountAdd;
	Transaction_Layout_Loc layout;
	Series_Recurence_Transaction_Loc Series;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Login");
		TestBase.tc = TestBase.extent.startTest("Transaction1", "Login");
		TestBase.test.appendChild(TestBase.tc);
		agg = new Aggregated_Transaction_Details_Loc(d);

		AddToCalendar = new AddToCalendar_Transaction_Loc(d);

		split = new Transaction_Split_Locator(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		Series = new Series_Recurence_Transaction_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		util = new SeleniumUtil();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		d.navigate().refresh();

		SeleniumUtil.waitForPageToLoad(10000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);

	}

	@Test(description = "AT-68699,AT-68700,AT-68703:verify split transaction icon", priority = 2)

	public void splitTransactionIcon() throws Exception {

		// Verify the Split transactions appplicable only for the Posted aggregated
		// transactions.
		d.navigate().refresh();

		SeleniumUtil.waitForPageToLoad(10000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);

		if (agg.isCollapseIconPresent()) {
			SeleniumUtil.click(agg.collapsIcon().get(2));
		} else {
			SeleniumUtil.click(AddToCalendar.PendingStranctionList().get(2));
		}
		// AddToCalendar.PendingStranctionList().get(0).click();
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.getShowMore());
		// aggre.ShowMore.click();
		Assert.assertTrue(split.SplitIcon().isDisplayed(), "verify Split Icon is displaying");

	}

	@Test(description = "AT-68702,AT-68737:verify split transaction  link", priority = 3, dependsOnMethods = "splitTransactionIcon")

	public void splitTransactionLink() throws Exception {
		Assert.assertTrue(split.SplitLink().isDisplayed(), "verify Split link is displaying");
	}

	@Test(description = "AT-68704,AT-68709:verify split transaction popup header", priority = 4, dependsOnMethods = "splitTransactionIcon")

	public void splitTransactionPopup() throws Exception {
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.waitForPageToLoad(5000);

		// split.SplitLink.click();

		Assert.assertEquals(split.popupHeader().getText(), PropsUtil.getDataPropertyValue("splitPopupHeader"),
				"verify Split split popup is displyed");
	}

	@Test(description = "AT-68706,AT-68729:verify original amount lable", priority = 5, dependsOnMethods = "splitTransactionPopup")

	public void splitTransactionOriginalLable() throws Exception {
		Assert.assertEquals(split.OriginalAmountLabel().getText(), PropsUtil.getDataPropertyValue("splitOriginalLabel"),
				"verify split original transaction Label is displying");

	}

	@Test(description = "AT-68706,AT-68710,AT-68729,AT-68750:verify original lable amount", priority = 6, dependsOnMethods = "splitTransactionPopup")

	public void splitTransactionOriginalAmount() throws Exception {
		Assert.assertEquals(split.OriginalAmount().getText(), PropsUtil.getDataPropertyValue("splitOriginalAmount"));
	}

	@Test(description = "AT-68706,AT-68710:verify original transaction category", priority = 7, dependsOnMethods = "splitTransactionPopup")

	public void splitOriginalCategory() throws Exception {
		Assert.assertEquals(split.OrginalRow().getText(), PropsUtil.getDataPropertyValue("splitOrginalCat"));
	}

	@Test(description = "AT-68706,AT-68710:splited transaction category", priority = 8, dependsOnMethods = "splitTransactionPopup")

	public void splitSplitedCategory() throws Exception {

		Assert.assertEquals(split.splitedrow().getText(), PropsUtil.getDataPropertyValue("splitSplitedlCat"));
	}

	@Test(description = "AT-68706,AT-68710:verify original transaction date", priority = 9, dependsOnMethods = "splitTransactionPopup")

	public void orginalRowDate() {
		System.out.println(AddToCalendar.DateFormate(0));
		System.out.println(split.Splitdate().getText());
		Assert.assertEquals(split.Splitdate().getText(), AddToCalendar.DateFormate(0));
	}

	@Test(description = "AT-68706,AT-68710:verify original transaction description", priority = 10, dependsOnMethods = "splitTransactionPopup")

	public void orginalRowDesc() {
		//// Verify by default original transaction description displayed in the Auto
		//// Populated Split tranaction row
		Assert.assertEquals(split.descrotionfield().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SplitOrginalDesc"));
	}

	@Test(description = "AT-68706,AT-68710,AT-68753:verify original transaction amount", priority = 11, dependsOnMethods = "splitTransactionPopup")

	public void orginalRowAmount() {
		Assert.assertEquals(split.SplitAmoun1().getText(), PropsUtil.getDataPropertyValue("SplitOrginalAmount"));
	}

	@Test(description = "AT-68710,AT-68711:verify splited transaction date lable", priority = 12, dependsOnMethods = "splitTransactionPopup")

	public void splitedRowsDateLable() throws Exception {
		Assert.assertEquals(split.datecolum().getText(), PropsUtil.getDataPropertyValue("splitDatelabel"));
	}

	@Test(description = "AT-68710,AT-68711:splited transaction desxription lable", priority = 13, dependsOnMethods = "splitTransactionPopup")

	public void splitedRowsDescriptionLable() throws Exception {
		Assert.assertEquals(split.descriptioncolumn().getText(),
				PropsUtil.getDataPropertyValue("splitdecriptionlabel"));
	}

	@Test(description = "AT-68710,AT-68711:verify plited transaction category lable", priority = 14, dependsOnMethods = "splitTransactionPopup")

	public void splitedRowsCatLable() throws Exception {
		Assert.assertEquals(split.catcolumn().getText(), PropsUtil.getDataPropertyValue("splitcatlabel"));

	}

	@Test(description = "AT-68710,AT-68711:verify splited transaction Amount lable", priority = 15, dependsOnMethods = "splitTransactionPopup")

	public void splitedRowsAmountLable() throws Exception {

		Assert.assertEquals(split.amountcolumn().getText(), PropsUtil.getDataPropertyValue("splitamountlabel"));
	}

	@Test(description = "AT-68710,AT-68711,AT-68714,AT-68722:verify splited transaction description value", priority = 16, dependsOnMethods = "splitTransactionPopup")

	public void splitTransactionDescription() throws Exception {

//Verify the Below fields values for the Auto Populated Split tansaction row displayed in the dialog box. Date Transcation Description. Category. Amount

		Assert.assertEquals(split.descrotionfield().getAttribute("value"),
				PropsUtil.getDataPropertyValue("splitoriginalDescription"));
	}

	@Test(description = "AT-68710,AT-68711,AT-68714,AT-68722,AT-68723:verify splited transaction date value", priority = 17, dependsOnMethods = "splitTransactionPopup")
	public void splitTransactionDate() throws Exception {
		// Verify the original transaction date auto populated in the splitted
		// transaction date field

		Assert.assertEquals(split.SplieddateField().getAttribute("value"), AddToCalendar.DateFormate(0));
	}

	@Test(description = "AT-68710,AT-68711,AT-68714,AT-68722,AT-68753:verify plited transaction amount value", priority = 18, dependsOnMethods = "splitTransactionPopup")

	public void splitTransactionAmount() throws Exception {
		// Verify the original transaction amount has been splited in to half after
		// Click the split transaction
		System.out.println(split.SplitAmoun1().getText().replaceAll("$", ""));
		System.out.println(split.SplitAmoun1().getText().replaceAll("$", ""));
		Assert.assertEquals("$" + split.SplitAmount2().getAttribute("value"), split.SplitAmoun1().getText());
	}

	@Test(description = "AT-68710,AT-68711:verify splited transaction date lable", priority = 19, dependsOnMethods = "splitTransactionPopup")

	public void splitedlTransactionDateLabel() throws Exception {
		Assert.assertEquals(split.splitedTranDateLabel().getText(), PropsUtil.getDataPropertyValue("splitDatelabel"));
	}

	@Test(description = "AT-68710,AT-68711:verify splited transaction description lable", priority = 20, dependsOnMethods = "splitTransactionPopup")

	public void splitedlTransactionDescLabel() throws Exception {
		Assert.assertEquals(split.splitedTrandescLabel().getText(),
				PropsUtil.getDataPropertyValue("splitdecriptionlabel"));
	}

	@Test(description = "AT-68710,AT-68711:verify plited transaction catgeory lable", priority = 21, dependsOnMethods = "splitTransactionPopup")

	public void splitedlTransactionCatLabel() throws Exception {
		Assert.assertEquals(split.splitedTrancatLabel().getText(), PropsUtil.getDataPropertyValue("splitcatlabel"));
	}

	@Test(description = "AT-68710,AT-68711:verify plited transaction amount lable", priority = 22, dependsOnMethods = "splitTransactionPopup")

	public void splitedlTransactionAmounLabel() throws Exception {
		Assert.assertEquals(split.splitedTranAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("splitamountlabel"));
	}

	@Test(description = "AT-68712,AT-68762:verify edit original transaction category", priority = 23, dependsOnMethods = "splitTransactionPopup")

	public void orginalTransactionCatEdit() throws Exception {

		SeleniumUtil.click(split.catField());
		SeleniumUtil.click(split.listCat(1, 1).get(0));
		Assert.assertTrue(split.OrginalRow().getText().contains(PropsUtil.getDataPropertyValue("splitChangecat10")));
	}

	@Test(description = "AT-68765:verify selected catgeory tick mark", priority = 24, dependsOnMethods = "orginalTransactionCatEdit")

	public void selectedCategoryTickMark() throws Exception {
		split.catField().click();

		Assert.assertTrue(split.TicksplitTr_tst(1, 1).get(0).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")));
	}

	@Test(description = "AT-68763,AT-68763:verify catgeory search", priority = 25, dependsOnMethods = "selectedCategoryTickMark")

	public void categorySearch() throws Exception {

		add_manual_transaction.catsearchField().sendKeys(PropsUtil.getDataPropertyValue("catg2"));
		SeleniumUtil.waitForPageToLoad();

		List<String> MLCName = util.getWebElementsValue(split.splitTxn_MLC_Search());
		List<String> HLCName = util.getWebElementsValue(split.splitTxn_HLC_Search());

		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("Txn_Split_MLCSearch"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("Txn_Split_HLCSearch"),
				"search matches HLC should be displayed");

		/*
		 * String
		 * searchedCategory=add_manual_transaction.CatSearched().get(0).getAttribute(
		 * "class"); boolean remaingCategory=false; int
		 * unsearchedCategory=add_manual_transaction.CatSearched().size(); for(int
		 * i=2;i<unsearchedCategory;i++) {
		 * if(add_manual_transaction.CatSearched().get(i).getAttribute("class").contains
		 * (PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide")))
		 * { remaingCategory=true; } else{ remaingCategory=false; break; } }
		 * Assert.assertFalse(searchedCategory.contains(PropsUtil.getDataPropertyValue(
		 * "TransactionCategoriesFilterCategoryHide"))
		 * ,"searched category class should not displayed with hide");
		 * Assert.assertTrue(
		 * remaingCategory,"unsearched category should displayed with hide");
		 */ }

	@Test(description = "AT-68712:verify original transction description edit", priority = 26, dependsOnMethods = "splitTransactionPopup")

	public void orginalTransactionDescEdit() throws Exception {
		split.descrotionfield().clear();
		split.descrotionfield().sendKeys(PropsUtil.getDataPropertyValue("splitChangedesc"));
		Assert.assertEquals(split.getAtributeVAlue(split.descrotionfield().getAttribute("id")),
				PropsUtil.getDataPropertyValue("splitChangedesc"));
	}

	@Test(description = "AT-68713:verify plited transaction category edit", priority = 27, dependsOnMethods = "splitTransactionPopup")

	public void splitedTransactionCatEdit() throws Exception {
		// Verify the dropdown box collapses once we select an option from category
		// field.
		SeleniumUtil.click(split.splitedrow());
		SeleniumUtil.waitForPageToLoad();
		split.listCatSplited(1, 1).get(0).click();
		// Assert.assertEquals(split.OrginalRow.getText(),
		// PropertyLoader.getProperty("Transaction_Enhancement",
		// "splitChangecat"));
		Assert.assertTrue(split.splitedrow().getText().contains(PropsUtil.getDataPropertyValue("splitChangecat10")));
	}

	@Test(description = "AT-68713:splited transaction date edit", priority = 28, dependsOnMethods = "splitTransactionPopup")

	public void splitedTransactiondateEdit() throws Exception {
		split.SplieddateField().clear();
		split.SplieddateField().sendKeys(AddToCalendar.DateFormate(-1));
		Assert.assertEquals(split.SplieddateField().getAttribute("value"), AddToCalendar.DateFormate(-1));
	}

	@Test(description = "AT-68713:splited transaction description edit", priority = 29, dependsOnMethods = "splitTransactionPopup")

	public void splitedTransactionDescEdit() throws Exception {
		split.Spliteddescfield().clear();
		split.Spliteddescfield().sendKeys(PropsUtil.getDataPropertyValue("splitChangedesc"));
		Assert.assertEquals(split.Spliteddescfield().getAttribute("value"),
				PropsUtil.getDataPropertyValue("splitChangedesc"));
	}

	@Test(description = "AT-68713:splited transaction amount edit", priority = 30, dependsOnMethods = "splitTransactionPopup")

	public void splitedTransactionAmountEdit() throws Exception {
		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(PropsUtil.getDataPropertyValue("SplitSplitedAmount2"));

		Assert.assertEquals(split.SplitAmount2().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SplitSplitedAmount2"));
	}

	@Test(description = "AT-68728:verify original amount when change the splited transaction amount", priority = 31, dependsOnMethods = "splitedTransactionAmountEdit")

	public void orginalTransactionAmountChange() throws Exception {
		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(PropsUtil.getDataPropertyValue("SpliteditAmount6"));
		split.SplitAmount2().sendKeys(Keys.TAB);

		Assert.assertEquals(split.SplitAmoun1().getText(), PropsUtil.getDataPropertyValue("SpliteditAmount7"));
	}

	@Test(description = "AT-68715:verify splited transaction minus symbool", priority = 32, dependsOnMethods = "splitTransactionPopup")

	public void minusSybol() throws Exception {
		Assert.assertTrue(split.minussybmbol().get(0).isDisplayed());
	}

	@Test(description = "AT-68730:verify add split button", priority = 33, dependsOnMethods = "splitTransactionPopup")

	public void addSplit() throws Exception {
		Assert.assertTrue(split.addSplit().isDisplayed());
	}

	@Test(description = "AT-68732:verify cancel splite button", priority = 34, dependsOnMethods = "splitTransactionPopup")

	public void cancel() throws Exception {
		Assert.assertTrue(split.cancel().isDisplayed());
	}

	@Test(description = "AT-68732:verify save changes button", priority = 35, dependsOnMethods = "splitTransactionPopup")

	public void saveChanges() throws Exception {
		Assert.assertTrue(split.savechanges().isDisplayed());
	}

	@Test(description = "AT-68738:verify splite popup close icon", priority = 36, dependsOnMethods = "splitTransactionPopup")

	public void close() throws Exception {

		Assert.assertTrue(split.close().isDisplayed());
	}

	@Test(description = "AT-68733:verify split pop closed when click on cancel button", priority = 37, dependsOnMethods = "splitTransactionPopup")

	public void cancelClick() throws Exception {

		SeleniumUtil.click(split.cancel());

		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(AddToCalendar.isElementPresent(AddToCalendar.PopupHeader()));

	}

	@Test(description = "AT-68749:verify remove splited row", priority = 38, dependsOnMethods = "cancelClick")

	public void removeSplit() throws Exception {

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.minussybmbol().get(0));
		Assert.assertEquals(split.SplitAmoun1().getText(), PropsUtil.getDataPropertyValue("splitOriginalAmount"));

	}

	@Test(description = "AT-68739,AT-68749:verify split popup closed when click on close icon", priority = 39, dependsOnMethods = "removeSplit")

	public void closeClick() throws Exception {
		split.close().click();

		boolean expected = false;
		if (AddToCalendar.PopupHeaderList().size() == 0) {
			expected = true;
		}

		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68746:verify nagative original amount when increase the splited transaction amount", priority = 40, dependsOnMethods = "closeClick")

	public void editMoreamount() throws Exception {
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.waitForPageToLoad(5000);
		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(PropsUtil.getDataPropertyValue("SpliteditAmount2"));
		split.SplitAmount2().sendKeys(Keys.TAB);

		// split.addSplit(PropsUtil.getDataPropertyValue("SpliteditAmount2"), 3);

		Assert.assertEquals(split.SplitAmoun1().getText(), PropsUtil.getDataPropertyValue("SpliteditAmount3"));
	}

	@Test(description = "AT-68746:verify nagative original amount when increase the splited transaction amount", priority = 41, dependsOnMethods = "editMoreamount")

	public void editMoreamount1() throws Exception {

		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(PropsUtil.getDataPropertyValue("SpliteditAmount4"));
		split.SplitAmount2().sendKeys(Keys.TAB);
		Assert.assertEquals(split.SplitAmoun1().getText(), PropsUtil.getDataPropertyValue("SpliteditAmount5"));
	}

	@Test(description = "AT-68754:verify ammount field allow to two decimal value", priority = 42, dependsOnMethods = "editMoreamount1")

	public void twoDecimal() throws Exception {
		if (StringUtils.substringAfter(split.SplitAmount2().getAttribute("value"), ".").length() != 2) {
			Assert.assertTrue(true);
		}
	}

	@Test(description = "AT-68719,AT-68756:verify empty amount field validation", priority = 43, dependsOnMethods = "twoDecimal")

	public void emptyAmountValue() throws Exception {
		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(Keys.TAB);
		Assert.assertEquals(split.amounterr().getText(), PropsUtil.getDataPropertyValue("splitamountErr2"));
	}

	@Test(description = "AT-68755:verify amount special char error message validation", priority = 44, dependsOnMethods = "emptyAmountValue")

	public void amountSpecialChar() throws Exception {
		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(PropsUtil.getDataPropertyValue("splitChangedesc"));
		split.SplitAmount2().sendKeys(Keys.TAB);
		Assert.assertEquals(split.amounterr().getText(), PropsUtil.getDataPropertyValue("splitamountErr3"));
	}

	@Test(description = "AT-68753:verify amount field value format", priority = 45, dependsOnMethods = "amountSpecialChar")

	public void amountFormate() throws Exception {
		split.SplitAmount2().clear();
		split.SplitAmount2().sendKeys(PropsUtil.getDataPropertyValue("SplitAmount12"));
		split.SplitAmount2().sendKeys(Keys.TAB);
		Assert.assertEquals(split.SplitAmount2().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SplitAmountFormate"));
	}

	@Test(description = "AT-68757:verify calendar popup", priority = 46, dependsOnMethods = "editMoreamount")

	public void calendarPopUp() throws Exception {
		split.calendarList().get(0).click();
		SeleniumUtil.waitForPageToLoad();
		boolean expected = split.calendarPopUp().isDisplayed();

		split.calendarList().get(0).click();
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68720,AT-68758:verify date field error message", priority = 47, dependsOnMethods = "editMoreamount")

	public void dateError() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		split.SplieddateField().clear();
		split.SplieddateField().sendKeys("163");
		split.SplieddateField().sendKeys(Keys.TAB);
		Assert.assertEquals(split.DateErr().getText().trim(), PropsUtil.getDataPropertyValue("splitDareErr").trim());
	}

	@Test(description = "AT-68731,AT-68761,AT-68767:verify add split error message", priority = 48, dependsOnMethods = "editMoreamount")

	public void addSplitSucessMassage() throws Exception {
		SeleniumUtil.click(split.close());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.SplitLink());
		SeleniumUtil.waitForPageToLoad(5000);
		// split.SplitLink().click();
		SeleniumUtil.click(split.savechanges());
		// split.savechanges().click();
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(agg.SucessMessage().getText(), PropsUtil.getDataPropertyValue("SplitedSuccessMessage"));

		// split.splitedtransaction().get(2).isDisplayed();
	}

	@Test(description = "AT-68740,AT-68741:verify splited transaction icon", priority = 49, dependsOnMethods = "addSplitSucessMassage")

	public void addSplitIcon() throws Exception {
		// Verify the all the splitted transaction displayed in the Landing screen
		// including the original transaction
		// Verify Split icon notification/Tag displayed in the original transaction row
		// after splitted the transactions

		Assert.assertTrue(split.splitedtransaction().get(0).isDisplayed());
		Assert.assertTrue(split.splitedtransaction().get(1).isDisplayed());
	}

	@Test(description = "AT-68759:verify manage split lable", priority = 50, dependsOnMethods = "addSplitIcon")

	public void manageSplitOption() throws Exception {
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(split.splitedtransaction().get(0));

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(split.SplitLink().getText(), PropsUtil.getDataPropertyValue("SplitManage"));
	}

	@Test(description = "AT-68751:verify number of splited transaction", priority = 51, dependsOnMethods = "manageSplitOption")
	public void splitedTransactionNumber() throws Exception {
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.SplitLink());
		SeleniumUtil.waitForPageToLoad(5000);
		if (split.SplitList().size() != 2) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68770,AT-68760:original transaction amount after splited", priority = 52, dependsOnMethods = "splitedTransactionNumber")
	public void afterSPlitedOriginalAmount() throws Exception {
		Assert.assertEquals(split.OriginalAmount().getText(), PropsUtil.getDataPropertyValue("splitOriginalAmount"));
	}

	@Test(description = "AT-68752,AT-68753:verify plited transaction can not split", priority = 53, dependsOnMethods = "afterSPlitedOriginalAmount")
	public void SplitOrginalTransactionAfterSplit() throws Exception {
		split.addSplit("33", 3);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.savechanges());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(add_manual_transaction.getAllPostedAmount1().get(2).getText(),
				PropsUtil.getDataPropertyValue("SpliteditAmount8"));

	}

	@Test(description = "AT-68747,AT-68748:splited transaction amount should not changed after split the original transaction again", priority = 54, dependsOnMethods = "SplitOrginalTransactionAfterSplit")
	public void SplitSplitedTransactionAfterSplit() throws Exception {
		SeleniumUtil.click(split.splitedtransaction().get(2));
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.SplitLink());
		SeleniumUtil.waitForPageToLoad(5000);
		split.addSplit("32", 4);
		String split2amount = split.getllAmountFIeld(2).getAttribute("value");
		String split1amount = split.SplitAmoun1().getText();

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.savechanges());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(add_manual_transaction.getAllPostedAmount1().get(2).getText(),
				PropsUtil.getDataPropertyValue("SpliteditAmount10"));
		Assert.assertEquals(split1amount, PropsUtil.getDataPropertyValue("SpliteditAmount13"));
		Assert.assertEquals(split2amount, PropsUtil.getDataPropertyValue("SpliteditAmount12"));

	}

	/*
	 * @Test(description = "AT-68747,AT-68748", priority = 55)
	 * 
	 * public void SplitTxn_SplitedTRansaction() throws Exception {
	 * SeleniumUtil.click(split.splitedtransaction().get(2));
	 * SeleniumUtil.waitForPageToLoad(1000);
	 * SeleniumUtil.click(split.getShowMore());
	 * SeleniumUtil.waitForPageToLoad(1000); SeleniumUtil.click(split.SplitLink());
	 * SeleniumUtil.waitForPageToLoad(5000); SeleniumUtil.click(split.addSplit());
	 * Assert.assertEquals(split.SplitAmount2().getAttribute("value"),
	 * PropsUtil.getDataPropertyValue("SpliteditAmount10"));
	 * Assert.assertEquals(split.SplitAmoun1().getText(),
	 * PropsUtil.getDataPropertyValue("SpliteditAmount10"));
	 * 
	 * }
	 */
	@Test(description = "AT-68747,AT-68769:verify delete split ", priority = 56, dependsOnMethods = "SplitSplitedTransactionAfterSplit")

	public void deleteSplit() throws Exception {
		SeleniumUtil.click(split.splitedtransaction().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(split.SplitLink());
		SeleniumUtil.waitForPageToLoad(5000);
		int minusSymbolRow = split.minussybmbol().size();
		for (int i = 0; i < minusSymbolRow; i++) {
			SeleniumUtil.click(split.minussybmbol().get(0));

			SeleniumUtil.waitForPageToLoad(1000);
		}
		SeleniumUtil.click(split.savechanges());
		SeleniumUtil.waitForPageToLoad(5000);
		boolean exptectSplit = false;
		if (split.splitedtransaction().size() == 0) {
			exptectSplit = true;
		}
		Assert.assertTrue(exptectSplit);
	}

	@Test(description = "AT-68746,AT-68747:verify parent  transaction amount after delete the split", priority = 57, dependsOnMethods = "deleteSplit")

	public void parentTraAmountAfterDeletSplit() throws Exception {
		SeleniumUtil.waitForPageToLoad(10000);
		System.out.println(add_manual_transaction.getAllPostedAmount1().get(2).getText());

		Assert.assertEquals(add_manual_transaction.getAllPostedAmount1().get(2).getText(),
				PropsUtil.getDataPropertyValue("splitOriginalAmount"));

	}

	@Test(description = "AT-68249:verify split transaction option for pending transaction", priority = 58)

	public void pendingTransactionSplit() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(AddToCalendar.PendingStranctionList().get(1));

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(AddToCalendar.isElementPresent(split.SplitIcon()));

	}

	@Test(description = "AT-68721,AT-68742,AT-68743,AT-68744:verify splited transaction date after edit splited transaction row date", priority = 59, dependsOnMethods = "pendingTransactionSplit")

	public void EditSpliedTransactionDate() throws Exception {
		logger.info("verify the change splited transaction date to current and vreiy the splited transaction");
		layout.serachTransaction(add_manual_transaction.targateDate1(-6), add_manual_transaction.targateDate1(-6));
		SeleniumUtil.click(AddToCalendar.PendingStranctionList().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.waitForPageToLoad(2000);

		split.getllAmountFIeld(2).clear();
		split.getllAmountFIeld(2).sendKeys("333");
		split.getllAmountFIeld(2).sendKeys(Keys.TAB);

		split.SplieddateField().clear();
		split.SplieddateField().sendKeys(add_manual_transaction.targateDate1(0));
		split.SplieddateField().sendKeys(Keys.TAB);

		SeleniumUtil.click(split.savechanges());
		layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));

		boolean splitIcon = false;
		boolean splitedTransaction = false;
		if (split.splitedtransaction().size() == 1) {
			splitIcon = true;
		}
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			System.out.println(add_manual_transaction.getAllPostedAmount1().get(i).getText());
			System.out.println(PropsUtil.getDataPropertyValue("SpliteditAmount9"));
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SpliteditAmount9").trim())) {
				splitedTransaction = true;
			}
		}
		Assert.assertTrue(splitIcon);
		Assert.assertTrue(splitedTransaction);
	}

	@Test(description = "AT-68735,AT-68736:verify add split button should disabled after split a transaction  into 21 transaction", priority = 60)

	public void disabledAddSplit() throws Exception {
		// Verify the User can split 20 transations at a time for each transactions
		// layout.serachTransaction(add_manual_transaction.targateDate1(0),
		// add_manual_transaction.targateDate1(0));

		SeleniumUtil.click(AddToCalendar.PendingStranctionList().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(split.getShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.waitForPageToLoad(3000);
		split.getllAmountFIeld(2).clear();
		split.getllAmountFIeld(2).sendKeys("10");
		split.getllAmountFIeld(2).sendKeys(Keys.TAB);
		// split.addSplit("10", 2);
		split.addSplit("10", 3);
		split.addSplit("10", 4);
		split.addSplit("10", 5);
		split.addSplit("10", 6);
		split.addSplit("10", 7);
		split.addSplit("10", 8);
		split.addSplit("10", 9);
		split.addSplit("10", 10);
		split.addSplit("10", 11);
		split.addSplit("10", 12);
		split.addSplit("10", 13);
		split.addSplit("10", 14);
		split.addSplit("10", 15);
		split.addSplit("10", 16);
		split.addSplit("10", 17);
		split.addSplit("10", 18);
		split.addSplit("10", 19);
		split.addSplit("10", 20);
		split.addSplit("10", 21);
		Assert.assertTrue(split.addSplit().getAttribute("class").contains("disabled"));
	}

	@Test(description = "AT-68734:verify a transaction can split into 21 transaction", priority = 61, dependsOnMethods = "disabledAddSplit")

	public void max21Split() throws Exception {
		SeleniumUtil.click(split.addSplit());
		System.out.println(split.SplitList().size());
		boolean epected = false;
		if (split.SplitList().size() == 21) {
			epected = true;
		}
		Assert.assertTrue(epected);
	}

	@Test(description = "AT-68744:verify all 21 transaction row in transction details screen", priority = 62, dependsOnMethods = "max21Split")

	public void verify21Transa() throws Exception {
		SeleniumUtil.click(split.savechanges());
		SeleniumUtil.waitForPageToLoad(10000);
		try {
			for (int i = 0; i < 2; i++) {
				SeleniumUtil.click(split.showmoretransaction());
				SeleniumUtil.waitForPageToLoad(10000);
			}
		} catch (Exception e) {

		}
		int exptectrasaction = 0;
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			System.out.println(add_manual_transaction.getAllPostedAmount1().get(i).getText());
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText().equals("$10.00")) {
				exptectrasaction = exptectrasaction + 1;
			}

		}
		Assert.assertEquals(exptectrasaction, 20, "21 transaction generated");
		Assert.assertEquals(add_manual_transaction.getAllPostedAmount1().get(2).getText(),
				PropsUtil.getDataPropertyValue("SpliteditAmount11"));

	}

	@Test(description = "AT-68248,AT-68707:verify split option for Projected transaction", priority = 63)
	public void projectedTransactionSplit() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);

		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 7, 1, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(5000);
		layout.serachTransaction(add_manual_transaction.targateDate1(7), add_manual_transaction.targateDate1(7));
		SeleniumUtil.waitForPageToLoad(10000);
		try {
			SeleniumUtil.click(add_manual_transaction.ProjectedExapdIcon());
		} catch (Exception e) {

		}

		SeleniumUtil.waitForPageToLoad(5000);
		for (int i = 0; i < Series.getAllAmount1().size(); i++) {
			System.out.println(Series.getAllAmount1().get(i).getText());
			System.out.println(PropsUtil.getDataPropertyValue("Manualamount2"));
			if (Series.getAllAmount1().get(i).getText().equals(PropsUtil.getDataPropertyValue("Manualamount2"))) {
				SeleniumUtil.click(Series.getAllAmount1().get(i));
				SeleniumUtil.waitForPageToLoad(5000);
				SeleniumUtil.click(split.getShowMore());
				SeleniumUtil.waitForPageToLoad(5000);
				// Assert.assertTrue(AddToCalendar.isElementPresent(split.SplitIcon()));
				break;
			}
		}
		boolean expectedSplit = false;
		if (split.SplitIconList().size() == 0) {
			expectedSplit = true;
		}

		Assert.assertTrue(expectedSplit);
	}

	@Test(description = "AT-68248,AT-68707:verify split option for manual transaction", priority = 64, dependsOnMethods = "projectedTransactionSplit")

	public void manualTransactionSplit() throws Exception {
		layout.serachTransaction(add_manual_transaction.targateDate1(0), add_manual_transaction.targateDate1(0));
		try {
			SeleniumUtil.click(split.showmoretransaction());
			SeleniumUtil.waitForPageToLoad(10000);
		} catch (Exception e) {

		}

		boolean expectedSplit = false;
		SeleniumUtil.waitForPageToLoad(10000);
		;
		for (int i = 0; i < add_manual_transaction.getAllPostedAmount1().size(); i++) {
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Manualamount2"))) {

				SeleniumUtil.click(add_manual_transaction.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad(5000);
				SeleniumUtil.click(split.getShowMore());
				SeleniumUtil.waitForPageToLoad(5000);

				break;
			}
		}
		if (split.SplitIconList().size() == 0) {
			expectedSplit = true;
		}

		Assert.assertTrue(expectedSplit);
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
