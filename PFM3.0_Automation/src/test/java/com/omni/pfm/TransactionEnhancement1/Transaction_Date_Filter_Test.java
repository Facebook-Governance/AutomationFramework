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
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Date_Filter_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Date_Filter_Test.class);

	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_Search_Loc search;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Layout_Loc layout;
	Transaction_Tag_Loc tag;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Time Filter");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		filter = new Transaction_Filter_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		search = new Transaction_Search_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-68639,AT-AT-68623:verify date filter tab", priority = 1)
	public void verifyTransactionDateFilterTab() {
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast3MonthLabel"),
				"3 months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68639:verify date filter date", priority = 2)
	public void verifyTransactionDateFilterTabDate() {
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDropDown.monthStartDate(-2, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"3 months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68639:verify dafault date filter", priority = 3)
	public void verifyDefault3MonthTransaction() throws ParseException {
		Assert.assertTrue(accountDropDown.last3monthTransaction(-2, 0), "last 3 month transaction is not displaying");
	}

	@Test(description = "AT-68638:verify list of date filter", priority = 4)
	public void verifyListOfTransactionDateFilter() throws ParseException {
		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		int dateFilterList = accountDropDown.datefilter().size();
		String expectedDateFilter[] = PropsUtil.getDataPropertyValue("TransactionDateFilterList").split(",");
		for (int i = 0; i < dateFilterList; i++) {
			Assert.assertEquals(accountDropDown.datefilter().get(i).getText().trim(), expectedDateFilter[i],
					"verify 8 type of transactionate date filter should be displayed");
		}
	}

	@Test(description = "AT-68638:verify date filter popup header", priority = 5, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void verifyTransactionDateFilterHeader() throws ParseException {
		Assert.assertTrue(
				filter.transactionFilterPopUpOpen().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionFilterPopUpOpen")),
				"class staus should change to open when clikc on Date Filter");
		Assert.assertEquals(filter.transactionFilterPopUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionDateFilterHeader"),
				"verify date filter header should displayed");
		Assert.assertTrue(filter.transactionFilterPopUpClose().isDisplayed());
	}

	@Test(description = "AT-68642:verify tick symbool for selected date filter", priority = 6, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void verifySelectedTransactionDateFilterTickSymbol() throws ParseException {
		Assert.assertTrue(
				filter.transactionDateFilterTickMark().get(2).getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")),
				"verify selected date filter displaying with tick symbol");
	}

	@Test(description = "AT-68640,AT-68643:verify this month date filter", priority = 7, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void VerifyThismonthFilterLabel() throws Exception {
		// SeleniumUtil.click(accountDropDown.datefilter().get(0));
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilterThisMonth"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643:verify this month date filter date", priority = 8, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void VerifyThisMonthDateFilterdate() throws Exception {

		logger.info("this months date should displayed");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDropDown.monthStartDate(0, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"This months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643:verify this month date filter transaction", priority = 9, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void VerifyThisMonthTransaction() throws Exception {

		logger.info("this months transaction should displayed");
		Assert.assertTrue(accountDropDown.thismonthTransaction(0, 0), "This month transaction is not displaying");

	}

	@Test(description = "AT-68618:verify reset button for date filter", priority = 10, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void verifyResetButtonDisplaying() {
		Assert.assertEquals(tag.reset().getText().trim(), PropsUtil.getDataPropertyValue("transactionResetText"),
				"after filter reset should displayed");

	}

	@Test(description = "AT-68620:verify reset the date filter", priority = 11, dependsOnMethods = "verifyListOfTransactionDateFilter")
	public void verifyResetFilter() {
		SeleniumUtil.click(tag.reset());
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast3MonthLabel"),
				"3 months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643:verify last month date filter", priority = 12, dependsOnMethods = "VerifyThismonthFilterLabel")
	public void VerifyLastmonthFilterLabel() throws Exception {

		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilterLastMonth"));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLastMonthLabel"),
				"last months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643;verify last month date filter date", priority = 13, dependsOnMethods = "VerifyLastmonthFilterLabel")
	public void VerifyLastMonthDateFilterdate() throws Exception {

		logger.info("last months date should displayed");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDropDown.monthStartDate(-1, "MM/dd/yyy") + " " + "-" + " "
						+ accountDropDown.monthEndDateDate(-1, "MM/dd/yyy") + ")",
				"This months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643:verify last month date filter transaction", priority = 14, dependsOnMethods = "VerifyLastmonthFilterLabel")
	public void VerifyLastMonthTransaction() throws Exception {

		logger.info("last months transaction should displayed");
		Assert.assertTrue(accountDropDown.lastmonthTransaction(-1, -1), "last month transaction is not displaying");

	}

	@Test(description = "AT-68640,AT-68643", priority = 15, dependsOnMethods = "VerifyLastmonthFilterLabel")
	public void VerifyLast6monthFilterLabel() throws Exception {

		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilter6Months"));
		SeleniumUtil.waitForPageToLoad(7000);
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast6MonthLabel"),
				"last 6months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 16, dependsOnMethods = "VerifyLast6monthFilterLabel")
	public void VerifyLast6MonthDateFilterdatel() throws Exception {

		logger.info("last months date should displayed");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDropDown.monthStartDate(-5, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"last 6 months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 17, dependsOnMethods = "VerifyLast6monthFilterLabel")
	public void VerifyLast6MonthTransaction() throws Exception {

		logger.info("last 6 months transaction should displayed");
		Assert.assertTrue(accountDropDown.last6monthTransaction(-5, 0), "last 6 month transaction is not displaying");

	}

	@Test(description = "AT-68640,AT-68643", priority = 18, dependsOnMethods = "VerifyLast6monthFilterLabel")
	public void VerifyThisYearFilterLabel() throws Exception {

		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilterThisYear"));
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionThisYearLabel"),
				"last 6months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 19, dependsOnMethods = "VerifyThisYearFilterLabel")
	public void VerifyThisYearDateFilterdatel() throws Exception {

		logger.info("year start date and current date should displayed");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDropDown.yearStartDate(0, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"this year date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 20, dependsOnMethods = "VerifyThisYearFilterLabel")
	public void VerifyThisYearTransaction() throws Exception {

		logger.info("this year transaction should displayed");
		Assert.assertTrue(accountDropDown.thisYearTransaction(0, 0), "this year transaction is not displaying");

	}

	@Test(description = "AT-68640,AT-68643", priority = 21, dependsOnMethods = "VerifyThisYearFilterLabel")
	public void Verify12monthFilterLabel() throws Exception {

		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilter12Months"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast12MonthLabel"),
				"12months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 22, dependsOnMethods = "Verify12monthFilterLabel")
	public void Verify12monthDateFilterdatel() throws Exception {

		logger.info("12 month date and current date should displayed");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDropDown.monthStartDate(-11, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"12months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 23, dependsOnMethods = "Verify12monthFilterLabel")
	public void Verify12monthTransaction() throws Exception {

		logger.info("12 month transaction should displayed");
		Assert.assertTrue(accountDropDown.last12MonthTransaction(-11, 0), "12 month transaction is not displaying");

	}

	@Test(description = "AT-68640,AT-68643", priority = 24, dependsOnMethods = "Verify12monthFilterLabel")
	public void VerifyLastYearFilterLabel() throws Exception {

		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilterLatYear"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLastYearLabel"),
				"last year label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 25, dependsOnMethods = "VerifyLastYearFilterLabel")
	public void VerifyLastYearDateFilterdatel() throws Exception {

		logger.info("last year start date and end date should displayed");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDropDown.yearStartDate(-1, "MM/dd/yyy") + " " + "-" + " "
						+ accountDropDown.yearEndDate(-1, "MM/dd/yyy") + ")",
				"last year date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68640,AT-68643", priority = 26, dependsOnMethods = "VerifyLastYearFilterLabel")
	public void VerifyLastYearTransaction() throws Exception {
		logger.info("custome date transaction should displayed");
		Assert.assertTrue(accountDropDown.lastYesrMonthTransaction(-1, -1), "last year transaction is not displaying");

	}

	@Test(description = "AT-68641", priority = 27, dependsOnMethods = "VerifyLastYearFilterLabel")
	public void VerifyOnlyOnseFilterSelected() throws Exception {
		Assert.assertTrue(
				filter.transactionDateFilterTickMark().get(6).getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")),
				"verify selected date filter displaying with tick symbol");
		Assert.assertFalse(
				filter.transactionDateFilterTickMark().get(5).getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")),
				"verify selected date filter displaying with tick symbol");
	}

	@Test(description = "AT-68644,AT-68645,AT-68646", priority = 28, dependsOnMethods = "VerifyLastYearFilterLabel")
	public void VerifyCustomeDatePopupHeader() throws Exception {
		SeleniumUtil.click(accountDropDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("TransactionDateFilterCustomeDate"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(filter.addManualHeader_AMT().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterHeader"),
				"custom date popup should open when you clikc on custome date filter");
		Assert.assertEquals(filter.transactionCustomDateFilterStartDateLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterStartDatelabel"),
				"custom date start date label");
		Assert.assertEquals(filter.transactionCustomDateFilterEndDateLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterEnddateLabel"),
				"custom date end date label");
		Assert.assertEquals(filter.transactionCustomDateFilterUpdate().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterUpdateText"), "custom date update button");
		Assert.assertTrue(filter.transactionCustomDateFilterCalanderIcon().get(0).isDisplayed(),
				"from date calander icon should be displayed");
		Assert.assertTrue(filter.transactionCustomDateFilterCalanderIcon().get(1).isDisplayed(),
				"To date calander icon should be displayed");

	}

	@Test(description = "AT-68651", priority = 29, dependsOnMethods = "VerifyCustomeDatePopupHeader")
	public void VerifyCustomeDateFormate() throws Exception {
		Assert.assertEquals(filter.transactionCustomDateFilterStartDate().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterDateFormate"),
				"custome date daet formate should be MM/DD/YYYY");
		Assert.assertEquals(filter.transactionCustomDateFilterEndDate().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterDateFormate"),
				"custome date daet formate should be MM/DD/YYYY");

	}

	@Test(description = "AT-68648", priority = 30, dependsOnMethods = "VerifyCustomeDatePopupHeader")
	public void VerifyToDateGreaterThanFormDateErrorMessage() throws Exception {
		filter.transactionCustomDateFilterStartDate().sendKeys(add_Manual.targateDate1(1));
		filter.transactionCustomDateFilterEndDate().sendKeys(add_Manual.targateDate1(0));
		SeleniumUtil.click(filter.transactionCustomDateFilterUpdate());
		Assert.assertTrue(filter.transactionCustomDateFilterErrorIcon().isDisplayed(),
				"error message icon should be displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterInvaildMsg"),
				"invalid date message should be displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterErrorMessage1"),
				"End date must be greater than start date message should be displayed");
	}

	@Test(description = "AT-68647", priority = 31, dependsOnMethods = "VerifyCustomeDatePopupHeader")
	public void VerifyDateRange12monthErrorMessage() throws Exception {
		filter.transactionCustomDateFilterStartDate().clear();
		filter.transactionCustomDateFilterStartDate().sendKeys(add_Manual.targateDate1(-367));
		filter.transactionCustomDateFilterEndDate().clear();
		filter.transactionCustomDateFilterEndDate().sendKeys(add_Manual.targateDate1(0));
		SeleniumUtil.click(filter.transactionCustomDateFilterUpdate());
		Assert.assertTrue(filter.transactionCustomDateFilterErrorIcon().isDisplayed(),
				"error message icon should be displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterInvaildMsg"),
				"invalid date message should be displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterErrorMessag2"),
				"Selected time period can't be more than 12 months message should be displayed");
	}

	@Test(description = "AT-68652", priority = 32, dependsOnMethods = "VerifyCustomeDatePopupHeader")
	public void VerifyCustomeDatePopUpClose() throws Exception {
		SeleniumUtil.click(filter.transactionCustomDateFilterClose());
		boolean customeDateClose = false;
		if (filter.addManualHeader_AMT().size() == 0) {
			customeDateClose = true;
		}
		Assert.assertTrue(customeDateClose);
	}

	@Test(description = "AT-68650", priority = 33, dependsOnMethods = "VerifyCustomeDatePopUpClose")
	public void VerifyCustomeDateFilterLabel() throws Exception {

		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(60));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomeDatesLabel"),
				"custome date label is not displaying in time filter Tab");

	}

	@Test(description = "AT-68650", priority = 34, dependsOnMethods = "VerifyCustomeDateFilterLabel")
	public void VerifyCustomeDateFilterdatel() throws Exception {

		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(),
				"(" + add_Manual.targateDate1(0) + " " + "-" + " " + add_Manual.targateDate1(60) + ")",
				"custome date is not displaying in time filter Tab");

	}

	@Test(description = "AT-68650", priority = 35, dependsOnMethods = "VerifyCustomeDateFilterLabel")
	public void VerifyCustomeTransaction() throws Exception {
		logger.info("custome transaction should displayed");
		Assert.assertTrue(
				accountDropDown.customeDateTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(60)),
				"custome date transaction is not displaying");

	}
}
