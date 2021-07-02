/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Exapnse_IncomeAnalaysis_Enhancement;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ExpanseAnalysis_HLC_Test extends TestBase {

	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	Aggregated_Transaction_Details_Loc aggTxn;
	Accounts_ViewByGroup_Loc groupView;

	@BeforeClass(alwaysRun = true)

	public void init() throws Exception {
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);
		groupView = new Accounts_ViewByGroup_Loc(d);
		accAddition = new AccountAddition();
		expenseTrend = new Expense_Income_Trend_Loc(d);
		util = new SeleniumUtil();
		txnAcct = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		aggTxn = new Aggregated_Transaction_Details_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		// new L1NodeLogin().loginExistingUser(d, "PFM1545878742471", "Password#");
		SeleniumUtil.waitForPageToLoad();

		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_Trends_DagSite"),
				PropsUtil.getDataPropertyValue("EIA_Trends_DagSite_Password")));
		expenseTrend.pageRefresh();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.FTUE();
		addManualReundTxn();
	}

	@Test(description = "AT-92227,AT-92228:verify back to expense anaylsis lable", priority = 1)
	public void backToEA() throws Exception {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		if(expenseTrend.isbacktoEIAicon())
		{
			Assert.assertTrue(expenseTrend.backtoEIAicon().isDisplayed() ,"BacktoEA is not displayed");
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
					PropsUtil.getDataPropertyValue("EA_ExpenseByCategory"), " expense by category is not displayed");
		}
		else {
		Assert.assertEquals(expenseTrend.EIABackToEIA().getText(), PropsUtil.getDataPropertyValue("EA_BackToEA"),
				"back to expense analysis is not displayed");
		Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
				PropsUtil.getDataPropertyValue("EA_ExpenseByCategory"), " expense by category is not displayed");
	}
	}
	@Test(description = "AT-92229:verify back to expense anaylsis", priority = 2, dependsOnMethods = "backToEA")
	public void backToEATrend() throws Exception {
		expenseTrend.clickEIABackToEIA();
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
			Assert.assertEquals(expenseTrend.EIAHeader().getText(), PropsUtil.getDataPropertyValue("EA_ExpenseHeader_mob"),
					"expense trend page is not displayed when clikc on back To expense");
		}else {
		Assert.assertEquals(expenseTrend.EIAHeader().getText(), PropsUtil.getDataPropertyValue("EA_ExpenseHeader"),
				"expense trend page is not displayed when clikc on back To expense");
	}
	}
	@Test(description = "AT-92276:verify 3 month time filter", priority = 3, dependsOnMethods = "backToEATrend")
	public void verifyTimeFilterTrednsToCat_3months() throws Exception {

		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_3monthTF_EndDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_3monthTF_StratDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_EndMonthSize")), "MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_StartMonthSizd"))),
				"6 th month start date and current date should not be displayed");

	}

	@Test(description = "AT-92276:verify 6 month tile filter", priority = 4, dependsOnMethods = "verifyTimeFilterTrednsToCat_3months")
	public void verifyTimeFilterTrednsToCat_6months() throws Exception {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_EndDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_StratDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date should be displayed");
	}

	@Test(description = "AT-92276:verify 12 month time filter", priority = 5, dependsOnMethods = "verifyTimeFilterTrednsToCat_6months")
	public void verifyTimeFilterTrednsToCat_12months() throws Exception {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_12monthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_12monthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92276:verify this year time filter", priority = 6, dependsOnMethods = "verifyTimeFilterTrednsToCat_12months", enabled = false)
	public void verifyTimeFilterTrednsToCat_ThisYear() throws Exception {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_TYmonthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_TYmonthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92276:verify custom date time filter", priority = 8/*
																				 * ,dependsOnMethods=
																				 * "verifyTimeFilterTrednsToCat_ThisYear"
																				 */ )
	public void verifyTimeFilterTrednsToCat_CustomDate() throws Exception {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(expenseTrend.getMonthsMM(-6), expenseTrend.getMonthsMM(0));
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CDmonthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CDmonthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				expenseTrend.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_EndMonthSize")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92231:verify Account dropdown is displaying", priority = 9, dependsOnMethods = "verifyTimeFilterTrednsToCat_CustomDate")

	public void verifyHLCAccountDropDown() {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"));
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_AllAccountDropDown"),
				"verify account drop down is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92232:verify  time filter is displaying", priority = 10, dependsOnMethods = "verifyHLCAccountDropDown")

	public void verifyHLCdefaultTimeFilter() {
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "  time filter is not displayed");

		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");

	}

	@Test(description = "AT-92237:AT-92242:verify chart total expense", priority = 11, dependsOnMethods = "verifyHLCdefaultTimeFilter")

	public void verifyHLCTotalExp() {
		String EA_HLC_TotalExpenseValue1 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_TotalExpenseValue1 = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue1_43");

		} else {

			EA_HLC_TotalExpenseValue1 = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue1");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValue1,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92249,AT-92251,AT-92248:verify all catgeory lable is displaying", priority = 12, dependsOnMethods = "verifyHLCdefaultTimeFilter")

	public void verifyHLCAllCatLbl() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92246,AT-92247,AT-92245:verify all HLC name", priority = 13, dependsOnMethods = "verifyHLCdefaultTimeFilter")
	public void verifyAllHLC() {
		String EA_HLC_AllHLC = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_AllHLC = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC_43");

		} else {

			EA_HLC_AllHLC = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLC,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92246,AT-92247:verify HLC amount", priority = 14, dependsOnMethods = "verifyHLCdefaultTimeFilter")
	public void verifyAllHLCAmount() {
		String EA_HLC_CatPerAmount = null;
		String EA_HLC_CatAmount = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_CatPerAmount = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount_43");
			EA_HLC_CatAmount = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount_43");

		} else {

			EA_HLC_CatPerAmount = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount");
			EA_HLC_CatAmount = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmount, "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmount, "all HLc catgeory is not displayed with expense amountamount");

	}

	@Test(description = "AT-92258,AT-92259,AT-92260:verify refund message is displaying", priority = 15)//, dependsOnMethods = "verifyHLCdefaultTimeFilter"
	public void verifyHLCRefundMsg() {
		String EA_HLC_RefundAmount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_RefundAmount = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount_43");

		} else {

			EA_HLC_RefundAmount = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmount,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92262,AT-92263:verify uncatgetegosized txn is displaying", priority = 16)//, dependsOnMethods = "verifyHLCdefaultTimeFilter"
	public void verifyHLCRUncatTxn() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
			//SeleniumUtil.click(expenseTrend.EIAUncatgmob());}
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnHeader_mob().getText().trim(),
					PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnHeader_mob"), "uncatgeory txn header is not displayed");
			Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeader().getText().trim(),
					PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnDateHeader") + " "
							+ expenseTrend.getMonthMMMMDD(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))),
					"uncategorized txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnDescription()),
					PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnDecsription"),"uncategorized  description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnAccount"), "uncatgeorized txn acount is not displayed");
				util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAmount()),
						PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnAmount"), "uncategorized txn amount is not displayed");
				SeleniumUtil.click(expenseTrend.EAcloseUnctgTransPopup());	
				SeleniumUtil.waitForPageToLoad(4000);
			SeleniumUtil.waitForPageToLoad();
	//	util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnCat()),
		//		PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnCategory"),
		//		"uncatgeorized txn category is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAmount()),
				PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnAmount"), "uncategorized txn amount is not displayed");
		}
	}

	@Test(description = "AT-92231:verify all expense accounts option is available when click on expese all accounts dropdown ", priority = 17)

	public void verifyHLCAcctDDownAllAcctLabel() {
		expenseTrend.clickEIAccountDropDown();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAllAccountLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_AllExpeneAccounts"),
				"verify all expense accounts option is not available when click on expese all accounts dropdown ");
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountSelected"),
				"verify all expense accounts option should not available when clikc on expese all accounts dropdown ");
	}

	@Test(description = "AT-92231:verify all added expense accounts are displaying accounts dropdown", priority = 18, dependsOnMethods = "verifyHLCAcctDDownAllAcctLabel")

	public void verifyHLCAcctDDownAllAcctsName() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("EA_AllHLCAccountS"),
				"verify all added expense accounts are not displayed in accounts dropdown");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountSelected"), " all accounts should not be selected ");
	}

	@Test(description = "AT-92231:verify all accounts check box unselected", priority = 19)//verifyHLCAcctDDownAllAcctsName
	public void verifyHLCUnselectAcctDDownAllAcct() {
		SeleniumUtil.click(expenseTrend.EIAllAccountLabel());// expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountUnSelected"),
				"verify all expense accounts option is not available when clikc on expese all accounts dropdown ");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountUnSelected"), " all accounts are not selected ");
		if(expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			}
		Assert.assertEquals(expenseTrend.EIANoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataHeaderLable"), "no data header is not displayed");
		Assert.assertEquals(expenseTrend.EIANoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataDescription"), "no data description is not displayed");
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoaccountSelectValue"),
				"verify account dropwn is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92231:verify Account dropdown is displaying", priority = 20, dependsOnMethods = "verifyHLCUnselectAcctDDownAllAcct")

	public void verifyHLCOneAccountSelected() {
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("EA_HLC_SelectInstAccount"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_SelectCashAccount"),
				"selected account is not displayed in account drop down");
	}

	@Test(description = "AT-92233:verify chart total expense ", priority = 21, dependsOnMethods = "verifyHLCOneAccountSelected")

	public void verifyHLCTotalExp_InstAcc() {
		String EA_HLC_TotalExpenseOfCashAccount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_TotalExpenseOfCashAccount = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseOfCashAccount_43");

		} else {

			EA_HLC_TotalExpenseOfCashAccount = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseOfCashAccount");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseOfCashAccount,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92231:verify all Catgory lable", priority = 22, dependsOnMethods = "verifyHLCOneAccountSelected")

	public void verifyHLCAllCatLbl_InstAcc() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92231:verify all HLC", priority = 23, dependsOnMethods = "verifyHLCOneAccountSelected")
	public void verifyAllHLC_InstAcc() {
		String EA_HLC_AllHLCOfCashAcc = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_AllHLCOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCOfCashAcc_43");

		} else {

			EA_HLC_AllHLCOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCOfCashAcc");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize3")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				EA_HLC_AllHLCOfCashAcc, "all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92231:verify all HLC amount ", priority = 24, dependsOnMethods = "verifyHLCOneAccountSelected")
	public void verifyAllHLCAmount_InstAcc() {
		String EA_HLC_CatPerAmountOfCashAcc = null;
		String EA_HLC_CatAmountOfCashAcc = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_CatPerAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountOfCashAcc_43");
			EA_HLC_CatAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountOfCashAcc_43");

		} else {

			EA_HLC_CatPerAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountOfCashAcc");
			EA_HLC_CatAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountOfCashAcc");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmountOfCashAcc, "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmountOfCashAcc, "all HLc actegory is not displayed with expense amountamount");

	}

	@Test(description = "AT-92231:verify refund message", priority = 25, dependsOnMethods = "verifyHLCOneAccountSelected", enabled = false)
	public void verifyHLCRefundMsg_InstAcc() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountOfCashAcc"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92265:,AT-92266:verify uncategorized txn", priority = 26, dependsOnMethods = "verifyHLCOneAccountSelected")
	public void verifyHLCRUncatTxn_InstAcc() {
		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnHeaderList().get(0).getText().equals(""),
				"uncatgeory txn header should be displayed");
		Assert.assertTrue(
				expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoUnCatgorizedTXn")),
				"uncategorized txn date header should be displayed");
		Assert.assertTrue(
				expenseTrend.EIAHLCCatUnCatTxnDescription().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoUnCatgorizedTXn")),
				"uncategorized txn date header should be displayed");
		Assert.assertTrue(
				expenseTrend.EIAHLCCatUnCatTxnAccount().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoUnCatgorizedTXn")),
				"uncategorized txn date header should be displayed");
		Assert.assertTrue(
				expenseTrend.EIAHLCCatUnCatTxnCat().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoUnCatgorizedTXn")),
				"uncategorized txn date header should be displayed");
		Assert.assertTrue(
				expenseTrend.EIAHLCCatUnCatTxnAmount().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoUnCatgorizedTXn")),
				"uncategorized txn date header should be displayed");

	}

	@Test(description = "AT-92235:verify last month time filter", priority = 27)

	public void verifyHLCTotalExp_LM() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_LastMonthTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String EA_HLC_TotalExpenseValueLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_TotalExpenseValueLM = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValueLM_43");

		} else {

			EA_HLC_TotalExpenseValueLM = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValueLM");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValueLM,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify all category lable", priority = 28, dependsOnMethods = "verifyHLCTotalExp_LM")

	public void verifyHLCAllCatLbl_LM() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92276:Lat month time filter HLC name", priority = 30, dependsOnMethods = "verifyHLCTotalExp_LM")
	public void verifyAllHLC_LM() {
		String EA_HLC_AllHLCLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_AllHLCLM = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCLM_43");

		} else {

			EA_HLC_AllHLCLM = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCLM");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLCLM,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify Last month time filter HLC amount", priority = 31, dependsOnMethods = "verifyHLCTotalExp_LM")
	public void verifyAllHLCAmount_LM() {
		String EA_HLC_CatPerAmountLM = null;
		String EA_HLC_CatAmountLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_CatPerAmountLM = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountLM_43");
			EA_HLC_CatAmountLM = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountLM_43");

		} else {

			EA_HLC_CatPerAmountLM = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountLM");
			EA_HLC_CatAmountLM = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountLM");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmountLM, "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmountLM, "all HLc actegory is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify last month Time Filter refund message", priority = 32, dependsOnMethods = "verifyHLCTotalExp_LM", enabled = false)
	public void verifyHLCRefundMsg_LM() {
		String EA_HLC_RefundAmountLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_RefundAmountLM = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountLM_43");

		} else {

			EA_HLC_RefundAmountLM = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountLM");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmountLM,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92276:verify  time filter is displaying", priority = 33, dependsOnMethods = "verifyHLCTotalExp_LM")

	public void verifyHLCTotalExp_3M() {
		String EA_HLC_TotalExpenseValue3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_TotalExpenseValue3M = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue3M_43");

		} else {

			EA_HLC_TotalExpenseValue3M = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue3M");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValue3M,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92235:verify last 3 month time filter ", priority = 34, dependsOnMethods = "verifyHLCTotalExp_3M")

	public void verifyHLCAllCatLbl_3M() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92276:verify last 3 months time filter all HLC Name ", priority = 35, dependsOnMethods = "verifyHLCTotalExp_3M")
	public void verifyAllHLC_3M() {
		String EA_HLC_AllHLC3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_AllHLC3M = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC3M_43");

		} else {

			EA_HLC_AllHLC3M = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC3M");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLC3M,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify last 3 months time filter all HLC amounts ", priority = 36, dependsOnMethods = "verifyHLCTotalExp_3M")
	public void verifyAllHLCAmount_3M() {
		String EA_HLC_CatPerAmount3M = null;
		String EA_HLC_CatAmount3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_CatPerAmount3M = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount3M_43");
			EA_HLC_CatAmount3M = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount3M_43");

		} else {

			EA_HLC_CatPerAmount3M = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount3M");
			EA_HLC_CatAmount3M = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount3M");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmount3M, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmount3M, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify last 3 months time filter refund message", priority = 37, dependsOnMethods = "verifyHLCTotalExp_3M")
	public void verifyHLCRefundMsg_3M() {
		String EA_HLC_RefundAmount3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_RefundAmount3M = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount3M_43");

		} else {

			EA_HLC_RefundAmount3M = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount3M");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmount3M,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify 6 months time filter ", priority = 38, dependsOnMethods = "verifyHLCTotalExp_LM")

	public void verifyHLCTotalExp_6M() {
		String EA_HLC_TotalExpenseValue6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_TotalExpenseValue6M = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue6M_43");

		} else {
			EA_HLC_TotalExpenseValue6M = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue6M");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"));
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValue6M,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify 6 months time filter all category included", priority = 39, dependsOnMethods = "verifyHLCTotalExp_6M")

	public void verifyHLCAllCatLbl_6M() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92276:verify 6 months time filter HLC name", priority = 40, dependsOnMethods = "verifyHLCTotalExp_6M")
	public void verifyAllHLC_6M() {
		String EA_HLC_AllHLC6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_AllHLC6M = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC6M_43");

		} else {
			EA_HLC_AllHLC6M = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC6M");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLC6M,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify 6 months time filter HLC Amount", priority = 41, dependsOnMethods = "verifyHLCTotalExp_6M")
	public void verifyAllHLCAmount_6M() {
		String EA_HLC_CatPerAmount6M = null;
		String EA_HLC_CatAmount6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_CatPerAmount6M = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount6M_43");
			EA_HLC_CatAmount6M = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount6M_43");

		} else {
			EA_HLC_CatPerAmount6M = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount6M");
			EA_HLC_CatAmount6M = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount6M");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmount6M, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmount6M, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify 6 months time filter refund message", priority = 42, dependsOnMethods = "verifyHLCTotalExp_6M")
	public void verifyHLCRefundMsg_6M() {
		String EA_HLC_RefundAmount6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_RefundAmount6M = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount6M_43");

		} else {
			EA_HLC_RefundAmount6M = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount6M");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmount6M,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify 12 months time filter ", priority = 43, dependsOnMethods = "verifyHLCTotalExp_LM")

	public void verifyHLCTotalExp_12M() {
		String EA_HLC_TotalExpenseValue12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_TotalExpenseValue12M = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue12M_43");

		} else {
			EA_HLC_TotalExpenseValue12M = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValue12M");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValue12M,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify 12 months time filter all catgeory included lable", priority = 44, dependsOnMethods = "verifyHLCTotalExp_12M")

	public void verifyHLCAllCatLbl_12M() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92276:verify 12 months time filter HLC name", priority = 45, dependsOnMethods = "verifyHLCTotalExp_12M")
	public void verifyAllHLC_12M() {
		String EA_HLC_AllHLC12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_AllHLC12M = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC12M_43");

		} else {
			EA_HLC_AllHLC12M = PropsUtil.getDataPropertyValue("EA_HLC_AllHLC12M");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLC12M,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify 12 months time filter HLC amount", priority = 46, dependsOnMethods = "verifyHLCTotalExp_12M")
	public void verifyAllHLCAmount_12M() {
		String EA_HLC_CatPerAmount12M = null;
		String EA_HLC_CatAmount12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_CatPerAmount12M = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount12M_43");
			EA_HLC_CatAmount12M = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount12M_43");

		} else {
			EA_HLC_CatPerAmount12M = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmount12M");
			EA_HLC_CatAmount12M = PropsUtil.getDataPropertyValue("EA_HLC_CatAmount12M");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmount12M, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmount12M, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify 12 months time filter refund message", priority = 47, dependsOnMethods = "verifyHLCTotalExp_12M")
	public void verifyHLCRefundMsg_12M() {

		String EA_HLC_RefundAmount12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_RefundAmount12M = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount12M_43");

		} else {
			EA_HLC_RefundAmount12M = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmount12M");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmount12M,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify this year months time filter", priority = 48, dependsOnMethods = "verifyHLCTotalExp_LM")

	public void verifyHLCTotalExp_TY() {
		String EA_HLC_TotalExpenseValueTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_TotalExpenseValueTY = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValueTY_43");

		} else {
			EA_HLC_TotalExpenseValueTY = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValueTY");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValueTY,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify this year months time filter all catgeory included lable", priority = 49, dependsOnMethods = "verifyHLCTotalExp_TY")

	public void verifyHLCAllCatLbl_TY() {
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}

	@Test(description = "AT-92276:verify this year months time filter HLC name", priority = 50, dependsOnMethods = "verifyHLCTotalExp_TY")
	public void verifyAllHLC_TY() {
		String EA_HLC_AllHLCTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_AllHLCTY = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCTY_43");

		} else {
			EA_HLC_AllHLCTY = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCTY");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLCTY,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify this year months time filter HLC amount", priority = 51, dependsOnMethods = "verifyHLCTotalExp_TY")
	public void verifyAllHLCAmount_TY() {
		String EA_HLC_CatPerAmountTY = null;
		String EA_HLC_CatAmountTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_CatPerAmountTY = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountTY_43");
			EA_HLC_CatAmountTY = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountTY_43");

		} else {
			EA_HLC_CatPerAmountTY = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountTY");
			EA_HLC_CatAmountTY = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountTY");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmountTY, "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmountTY, "all HLc actegory is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify this year months time filter Refund Message", priority = 52, dependsOnMethods = "verifyHLCTotalExp_TY")
	public void verifyHLCRefundMsg_TY() {
		String EA_HLC_RefundAmountTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_RefundAmountTY = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountTY_43");

		} else {
			EA_HLC_RefundAmountTY = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountTY");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmountTY,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify cutsom date time filter", priority = 53, dependsOnMethods = "verifyHLCTotalExp_LM")

	public void verifyHLCTotalExp_CD() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CDmonthTF_StratDate2"))),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CDmonthTF_EndDate2"))));
		SeleniumUtil.waitForPageToLoad();
		String EA_HLC_TotalExpenseValueCD = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_TotalExpenseValueCD = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValueCD_43");

		} else {
			EA_HLC_TotalExpenseValueCD = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseValueCD");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseValueCD,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify cutsom date time filter all category inclided lable", priority = 54, dependsOnMethods = "verifyHLCTotalExp_CD")

	public void verifyHLCAllCatLbl_CD() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
		}}

	@Test(description = "AT-92276:verify cutsom date time filter HLC Name", priority = 55, dependsOnMethods = "verifyHLCTotalExp_CD")
	public void verifyAllHLC_CD() {
		String EA_HLC_AllHLCCD = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_AllHLCCD = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCCD_43");

		} else {
			EA_HLC_AllHLCCD = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCCD");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), EA_HLC_AllHLCCD,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify cutsom date time filter HLC amount", priority = 56, dependsOnMethods = "verifyHLCTotalExp_CD")
	public void verifyAllHLCAmount_CD() {
		String EA_HLC_CatPerAmountCD = null;
		String EA_HLC_CatAmountCD = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_CatPerAmountCD = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountCD_43");
			EA_HLC_CatAmountCD = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountCD_43");

		} else {
			EA_HLC_CatPerAmountCD = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountCD");
			EA_HLC_CatAmountCD = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountCD");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmountCD, "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmountCD, "all HLc actegory is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify cutsom date time filter refund message", priority = 57, dependsOnMethods = "verifyHLCTotalExp_CD")
	public void verifyHLCRefundMsg_CD() {
		String EA_HLC_RefundAmountCD = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_RefundAmountCD = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountCD_43");

		} else {
			EA_HLC_RefundAmountCD = PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountCD");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_RefundAmountCD,
				"total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92249,AT-92253,AT-92274:verify  time filter is displaying", priority = 58)
	public void verifyUnselectAllHLC() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		expenseTrend.clickEIAHLCCatFilterCatlable();
		int catsize = expenseTrend.EIAHLCCatCheckBox().size();
		for (int i = 0; i < catsize; i++) {
			SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(expenseTrend.EIAHLCCatDone().isDisplayed());
		Assert.assertTrue(expenseTrend.EIAHLCCatCancel().isDisplayed());
		Assert.assertTrue(expenseTrend.EIAHLCCatDone().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("EA_HLC_Donedisabled")));
		}
	}

	@Test(description = "AT-92250,AT-92252,AT-92254:verify selected HLC lable", priority = 59, dependsOnMethods = "verifyUnselectAllHLC")
	public void verifySelectFewHLC() {
		// SeleniumUtil.click(expenseTrend.EIAHLCCatCancel());
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		int catsize = expenseTrend.EIAHLCCatCheckBox().size();
		for (int i = 0; i < catsize - 2; i++) {
			SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
		}
		expenseTrend.clickEIAHLCCatDone();
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_9HLCIncluded"), " 9 HLC are not displayed included");
		}
	}

	@Test(description = "AT-92250,AT-92252,AT-92254:verify selected hLC total expense", priority = 60, dependsOnMethods = "verifySelectFewHLC")

	public void verifySelectedHLCTotalExp() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		String EA_HLC_9HLCIncludedExpAmount = null;
		String EA_HLC_9HLCIncludedAmount = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			EA_HLC_9HLCIncludedExpAmount = PropsUtil.getDataPropertyValue("EA_HLC_9HLCIncludedExpAmount_43");
			EA_HLC_9HLCIncludedAmount = PropsUtil.getDataPropertyValue("EA_HLC_9HLCIncludedAmount_43");

		} else {

			EA_HLC_9HLCIncludedExpAmount = PropsUtil.getDataPropertyValue("EA_HLC_9HLCIncludedExpAmount");
			EA_HLC_9HLCIncludedAmount = PropsUtil.getDataPropertyValue("EA_HLC_9HLCIncludedAmount");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_9HLCIncludedExpAmount,
				"total expense amount is not displayed");

		Assert.assertTrue(expenseTrend.EIAHLCCatIcon().size() == 8, "all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				EA_HLC_9HLCIncludedAmount, "all 11 HLc category name is not displayed");
		}
	}

	@Test(description = "AT-92255:verify selected HLC cancel", priority = 61, dependsOnMethods = "verifySelectFewHLC")

	public void verifySelectedHLCCancel() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		expenseTrend.clickEIAHLCCatFilterCatlable();
		for (int i = 0; i < 1; i++) {
			SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
		}
		SeleniumUtil.click(expenseTrend.EIAHLCCatCancel());
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_9HLCIncluded"), " 9 HLC are not displayed included");
		}
	}

	@Test(description = "AT-92267,AT-92271:verify Manual txn,more and Link account button", priority = 62)//, dependsOnMethods = "verifyUnselectAllHLC"
	public void allLinkOption_HLC() throws Exception {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickMore();
		if(expenseTrend.ismoreBtn_mob_Present())
		{
			//expenseTrend.clickMore();
			 Assert.assertTrue(expenseTrend.EIALinkAccount_mob().isDisplayed());
			
			Assert.assertEquals(expenseTrend.EIAMTLink_mob().getText(), PropsUtil.getDataPropertyValue("EA_MTLable"),
					"add manula account link is not displayed");
			
			}else {
		Assert.assertEquals(expenseTrend.EIAMTLink().getText(), PropsUtil.getDataPropertyValue("EA_MTLable"),
				" add manula txn link is not displayed");
		Assert.assertEquals(expenseTrend.EIAMoreLink().getText(), PropsUtil.getDataPropertyValue("EA_MoreLable"),
				"more link is not displayed");
		Assert.assertEquals(expenseTrend.EIALinkAccount().getText(),
				PropsUtil.getDataPropertyValue("EA_LinkAccountLable"), "link account link is not displayed");
	}
	}

	@Test(description = "AT-92268,AT-92269:verify more potions", priority = 63, dependsOnMethods = "allLinkOption_HLC")
	public void moreOptions_HLC() throws Exception {
		
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
		Assert.assertEquals(expenseTrend.EIAFeatureTure().getText(),
				PropsUtil.getDataPropertyValue("EA_FeatureTureLable"), "feature link is not displayed");
		Assert.assertTrue(expenseTrend.EIAAlertList().size() == Integer
				.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_AlertList")), " alert link is not displayed");}
		else {
			Assert.assertEquals(expenseTrend.EIAFeatureTure().getText(),
					PropsUtil.getDataPropertyValue("EA_FeatureTureLable"), "feature link is not displayed");
		Assert.assertEquals(expenseTrend.EIAPrint().getText(), PropsUtil.getDataPropertyValue("EA_PrintLable"),
				"print link is not displayed");
		Assert.assertTrue(expenseTrend.EIAAlertList().size() == Integer
				.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_AlertList")), " alert link is not displayed");}
	}

	@Test(description = "AT-93487,AT-93491,AT-93492,AT-93501:verify future ture", priority = 64, dependsOnMethods = "moreOptions_HLC")
	public void FTUnCatTxn_HLC() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		expenseTrend.clikcFT();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(
				expenseTrend.EIAHLCFTCloseButton(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).isDisplayed(),
				"close button is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCFTHeader(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatHeader"), "uncategorzed txn FT header is not displayed");
		Assert.assertEquals(
				expenseTrend.EIAHLCFTMesg1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText()
						.replaceAll("\\n", ""),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatMessage1"),
				"uncategorzed txn FT message1 is not displayed");

		Assert.assertEquals(expenseTrend.EIAHLCFTButton1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatGotIt"),
				"uncategorzed txn FT got it button is not displayed");
		}
	}

	@Test(description = "AT-93490,AT-93499,AT-93500:verify FT close button", priority = 65, dependsOnMethods = "FTUnCatTxn_HLC")
	public void FTUnCatTxnClose_HLC() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		SeleniumUtil.click(expenseTrend.EIAHLCFTCloseButton(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIAHLCFTHeaderList(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1"))
				.size() == Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_FTUESize")), "FT is displayed");
		}
	}

	@Test(description = "AT-93490:verify FT goto buttin", priority = 66, dependsOnMethods = "FTUnCatTxnClose_HLC")
	public void FTUnCatTxnGotIt_HLC() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{SeleniumUtil.waitForPageToLoad();
		expenseTrend.clikcFT();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.EIAHLCFTButton1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(
				expenseTrend.EIAHLCFTHeaderList(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).size() == 0,
				"FT is displayed");}
	}

	@Test(description = "AT-92264:verify edit txn", priority = 67)//, dependsOnMethods = "verifyUnselectAllHLC"
	public void editTxn_HLC() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		SeleniumUtil.click(expenseTrend.EIAHLCCatUnCatTxnDescription()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))));
		SeleniumUtil.waitForPageToLoad(7000);
		aggTxn.enterDecValue(PropsUtil.getDataPropertyValue("EA_HLC_UpdateUnCatDescription"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				expenseTrend.EIAHLCCatUnCatTxnDescription()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_UpdateUnCatDescription"),
				"transaction description is not updated");}

	}

	@Test(description = "AT-92277:verify This month time filter to Trend Time filter", priority = 68)
	public void verifyTMTFToTrend() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMEndDate"))),
				"This month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify last month time filter to Trend Time filter", priority = 69, dependsOnMethods = "verifyTMTFToTrend")
	public void verifyLMTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_LastMonthTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_LastMonthTmeFilter"),
				"this months label is not displaying in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.monthEndDateDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMEndDate")),
								"MM/dd/yyy"),
				"This months date is not displaying in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_LastMonthTmeFilter"), "6 months label should be displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.monthEndDateDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMEndDate")),
								"MM/dd/yyy"),
				"last month start date and current date should be displayed");

	}

	@Test(description = "AT-92277:verify 3 months time filter to Trend Time filter", priority = 70, dependsOnMethods = "verifyLMTFToTrend")
	public void verify3MTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MEndDate"))),
				"3 month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify 6 months time filter to Trend Time filter", priority = 71, dependsOnMethods = "verify3MTFToTrend")
	public void verify6MTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MEndDate"))),
				"6month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify This year time filter to Trend Time filter", priority = 72, dependsOnMethods = "verify6MTFToTrend")
	public void verifyTYTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify 12 months time filter to Trend Time filter", priority = 73, dependsOnMethods = "verifyTYTFToTrend")
	public void verify12MTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify last year time filter to Trend Time filter", priority = 74, dependsOnMethods = "verify12MTFToTrend")
	public void verifyLYTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_LastYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_LastYearTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.yearEndDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYEndDate")),
								"MM/dd/yyy"),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_LastYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.yearEndDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYEndDate")),
								"MM/dd/yyy"),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify Custom date time filter to Trend Time filter", priority = 75, dependsOnMethods = "verifyLYTFToTrend")
	public void verifyCDTFToTrend() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));

		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDStartDate"))),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDEndDate"))));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDStartDate")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDStartDate")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92272:verify link account popup", priority = 76, dependsOnMethods = "verifyCDTFToTrend")
	public void verifyLinkAccountPopup() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.click(expenseTrend.EIAAccountLink());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIALinkAccountPopUP().isDisplayed(), "link account popup is not displayed");

	}

	@Test(description = "AT-92231,AT-92234:verify gorup filter ", priority = 77)
	public void groupFilter() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("EA_GroupName"),
				PropsUtil.getDataPropertyValue("EA_GroupAccount").split(","));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad(10000);
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("EA_GroupName"));
		if(expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_GroupName"), "selected group is not displayed ");

	}

	@Test(description = "AT-92231:verify group filter total expense", priority = 78, dependsOnMethods = "groupFilter")

	public void verifyHLCTotalExp_GF() {
		String EA_HLC_TotalExpenseOfCashAccount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_TotalExpenseOfCashAccount = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseOfCashAccount_43");

		} else {
			EA_HLC_TotalExpenseOfCashAccount = PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseOfCashAccount");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_TotalExpenseLable"), " total expenses lable should dipslaying");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_HLC_TotalExpenseOfCashAccount,
				"total expense amount should displayed");
	}

	@Test(description = "AT-92231:group filter category included lable", priority = 79, dependsOnMethods = "groupFilter")

	public void verifyHLCAllCatLbl_GF() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_AllCategoryIncludeLbl"),
				"all catgeory lable should be displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}
	}
	@Test(description = "AT-92231:verify group filter all HLC name", priority = 80, dependsOnMethods = "groupFilter")
	public void verifyAllHLC_GF() {
		String EA_HLC_AllHLCOfCashAcc = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_AllHLCOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCOfCashAcc_43");

		} else {
			EA_HLC_AllHLCOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_AllHLCOfCashAcc");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CatgeoryIconSize3")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				EA_HLC_AllHLCOfCashAcc, "all 11 HLc category name hsould be displayed");
	}

	@Test(description = "AT-92231:verify group filter all HLC Amount", priority = 81, dependsOnMethods = "groupFilter")
	public void verifyAllHLCAmount_GF() {
		String EA_HLC_CatPerAmountOfCashAcc = null;
		String EA_HLC_CatAmountOfCashAcc = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_HLC_CatPerAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountOfCashAcc_43");
			EA_HLC_CatAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountOfCashAcc_43");

		} else {
			EA_HLC_CatPerAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatPerAmountOfCashAcc");
			EA_HLC_CatAmountOfCashAcc = PropsUtil.getDataPropertyValue("EA_HLC_CatAmountOfCashAcc");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				EA_HLC_CatPerAmountOfCashAcc, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				EA_HLC_CatAmountOfCashAcc, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92231:verify group filter Refund message", priority = 82, dependsOnMethods = "groupFilter", enabled = false)
	public void verifyHLCRefundMsg_GF() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundAmountOfCashAcc"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92227,AT-92228,AT-92230:verify back to Income analysiis", priority = 83, dependsOnMethods = "groupFilter")
	public void backToIA() throws Exception {
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		addManualAdjTxn();
		SeleniumUtil.waitForPageToLoad(7000);
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		if(expenseTrend.isbacktoEIAicon())
		{
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
					PropsUtil.getDataPropertyValue("IA_ExpenseByCategory"), " Income by category is not displayed");
		Assert.assertTrue(expenseTrend.backtoEIAicon().isDisplayed() ,"BacktoIA is not displayed");}
		else {
		Assert.assertEquals(expenseTrend.EIABackToEIA().getText(), PropsUtil.getDataPropertyValue("IA_BackToEA"),
				"back to expense analysis is not displayed");
		Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
				PropsUtil.getDataPropertyValue("IA_ExpenseByCategory"), " expense by category is not displayed");
	}}

	@Test(description = "AT-92229,AT-92230: back to trends", priority = 84, dependsOnMethods = "backToIA")
	public void backToIATrend() throws Exception {
		expenseTrend.clickEIABackToEIA();
		 if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
			{		Assert.assertEquals(expenseTrend.EIAHeader().getText(), PropsUtil.getDataPropertyValue("IA_ExpenseHeader_mob"),
					"expense trend page is not displayed when click on back To expense");
			}else {
		Assert.assertEquals(expenseTrend.EIAHeader().getText(), PropsUtil.getDataPropertyValue("IA_ExpenseHeader"),
				"expense trend page is not displayed when click on back To expense");
	}}

	@Test(description = "AT-92276:verify 3 month time filter", priority = 85, dependsOnMethods = "backToIATrend")
	public void verifyTFTrednsToCat_IA_3months() throws Exception {

		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_3monthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_3monthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_EndMonthSize")), "MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_StartMonthSizd"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92276:verify 6 month time filter", priority = 86, dependsOnMethods = "verifyTFTrednsToCat_IA_3months")
	public void verifyTFTrednsToCat_IA6months() throws Exception {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");
	}

	@Test(description = "AT-92276:verify 12 month time filter", priority = 87, dependsOnMethods = "verifyTFTrednsToCat_IA6months")
	public void verifyTFTrednsToCat_IA12months() throws Exception {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_12monthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_12monthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92276:verify This year time filter", priority = 88, dependsOnMethods = "verifyTFTrednsToCat_IA12months", enabled=false)
	public void verifyTFTrednsToCat_IAThisYear() throws Exception {
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_TYmonthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_TYmonthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92276:verify cutsom date time filter", priority = 90)
	public void verifyTFTrednsToCat_IACustomDate() throws Exception {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(expenseTrend.getMonthsMM(-6), expenseTrend.getMonthsMM(0));
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CDmonthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_CDmonthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				expenseTrend.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_EndMonthSize")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92231:verify Account dropdown is displaying", priority = 91)//, dependsOnMethods = "verifyTFTrednsToCat_IACustomDate")

	public void verifyHLCAccountDropDown_IA() {
		//added by mallika
	    expenseTrend.clickEIAccountDropDown();
 		expenseTrend.clickAccountLink();
 		expenseTrend.clickEIAllAccountCheckBox();
 		if(expenseTrend.isSelectAcc_Donebtn()) {
 			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
 			}
		expenseTrend.clickMonth(PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"));
        SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_AllAccountDropDown"),
				"verify account dropdown is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92232:verify  time filter is displaying", priority = 92, dependsOnMethods = "verifyHLCAccountDropDown_IA")

	public void verifyHLCdefaultTF_IA() {
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");

		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displayed in time filter Tab");
		//TransactionThisMonthLabel
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_StratDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_6monthTF_EndDate"))),
				"This months date is not displayed in time filter Tab");

	}

	@Test(description = "AT-92237:AT-92242:verify chart total expense", priority = 93, dependsOnMethods = "verifyHLCdefaultTF_IA")

	public void verifyHLCTotalExp_IA() {
		String IA_HLC_TotalExpenseValue1 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseValue1 = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue1_43");

		} else {
			IA_HLC_TotalExpenseValue1 = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue1");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseValue1,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92249,AT-92251:verify include all catgeory lable", priority = 94, dependsOnMethods = "verifyHLCdefaultTF_IA")

	public void verifyHLCAllCatLbl_IA() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}
	}
	@Test(description = "AT-92246,AT-92247:verify all HLC name", priority = 95, dependsOnMethods = "verifyHLCdefaultTF_IA")
	public void verifyAllHLC_IA() {
		String IA_HLC_AllHLC = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLC = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC_43");

		} else {
			IA_HLC_AllHLC = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon should be displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), IA_HLC_AllHLC,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92246,AT-92247:verify HLC amount", priority = 96, dependsOnMethods = "verifyHLCdefaultTF_IA")
	public void verifyAllHLCAmount_IA() {
		String IA_HLC_CatPerAmount = null;
		String IA_HLC_CatAmount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmount = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount_43");
			IA_HLC_CatAmount = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount_43");

		} else {
			IA_HLC_CatPerAmount = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount");
			IA_HLC_CatAmount = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmount, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				IA_HLC_CatAmount, "all HLc category  is not displayed with expense amountamount");

	}

	@Test(description = "AT-92258,AT-92259,AT-92260:verify HLC refund message", priority = 97, dependsOnMethods = "verifyHLCdefaultTF_IA")
	public void verifyHLCRefundMsg_IA() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmount"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92262:verify uncatgeorized txn", priority = 98, dependsOnMethods = "verifyHLCdefaultTF_IA")
	public void verifyHLCRUncatTxn_IA() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnHeader"), "uncatgeory txn header is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnDateHeader") + " "
						+ expenseTrend.getMonthMMMMDD(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))),
				"uncategorized txn date header is not displayed");
		String IA_HLC_UnCatTxnDecsription = null;
		String IA_HLC_UnCatTxnAccount = null;
		String IA_HLC_UnCatTxnCategory = null;
		String IA_HLC_UnCatTxnAmount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_UnCatTxnDecsription = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnDecsription_43");
			IA_HLC_UnCatTxnAccount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAccount_43");
			IA_HLC_UnCatTxnCategory = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnCategory_43");
			IA_HLC_UnCatTxnAmount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAmount_43");

		} else {
			IA_HLC_UnCatTxnDecsription = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnDecsription");
			IA_HLC_UnCatTxnAccount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAccount");
			IA_HLC_UnCatTxnCategory = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnCategory");
			IA_HLC_UnCatTxnAmount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAmount");

		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnDescription()),
				IA_HLC_UnCatTxnDecsription, "uncategorized  description is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAccount()),
				IA_HLC_UnCatTxnAccount, "uncatgeorized txn acount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnCat()),
				IA_HLC_UnCatTxnCategory, "uncatgeorized txn category is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAmount()),
				IA_HLC_UnCatTxnAmount, "uncategorized txn amount is not displayed");

	}}

	@Test(description = "AT-92231:verify all income accounts option is available when click on expese all accounts dropdown ", priority = 99, dependsOnMethods = "verifyHLCdefaultTF_IA")

	public void verifyHLCAcctDDownAllAcctLabe_IAl() {
		expenseTrend.clickEIAccountDropDown();
		Assert.assertEquals(expenseTrend.EIAllAccountLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_AllExpeneAccounts"),
				" all expense accounts option is not available when clikc on expese all accounts dropdown ");
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountSelected"),
				" all expense accounts option is not available when clikc on expese all accounts dropdown ");
	}

	@Test(description = "AT-92231:verify all added expense accounts are displaying accounts dropdown", priority = 100, dependsOnMethods = "verifyHLCAcctDDownAllAcctLabe_IAl")

	public void verifyHLCAcctDDownAllAcctsName_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("IA_AllHLCAccountS"),
				" all added expense accounts is not displayed accounts dropdown");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountSelected"), " all accounts are not selected ");
	}

	@Test(description = "AT-92231:verify all accounts check box unselected", priority = 101, dependsOnMethods = "verifyHLCAcctDDownAllAcctsName_IA")
	public void verifyHLCUnselectAcctDDownAllAcct_IA() {
		expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountUnSelected"),
				" all expense accounts option is not available when clikc on expese all accounts dropdown ");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountUnSelected"), " all accounts are not selected ");
		if(expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			}
		Assert.assertEquals(expenseTrend.EIANoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_NoDataHeaderLable"), "no data header is not displayed");
		Assert.assertEquals(expenseTrend.EIANoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_NoDataDescription"), "no data description is not displayed");
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_NoaccountSelectValue"),
				"verify account dropdown is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92231:verify Account dropdown is displaying", priority = 102, dependsOnMethods = "verifyHLCUnselectAcctDDownAllAcct_IA")

	public void verifyHLCOneAccountSelected_IA() {
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("IA_HLC_SelectInstAccount"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_SelectCashAccount"),
				"selected account is not displayed in account drop down");
	}

	@Test(description = "AT-92233:verify  time filter is displaying", priority = 103, dependsOnMethods = "verifyHLCOneAccountSelected_IA")

	public void verifyHLCTotalExp__IAInstAcc() {
		String IA_HLC_TotalExpenseOfCashAccount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseOfCashAccount = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseOfCashAccount_43");

		} else {
			IA_HLC_TotalExpenseOfCashAccount = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseOfCashAccount");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable sis not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseOfCashAccount,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92231:verify include all category lable", priority = 104, dependsOnMethods = "verifyHLCOneAccountSelected_IA")

	public void verifyHLCAllCatLbl__IAInstAcc() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}}

	@Test(description = "AT-92231:verify HLC name", priority = 105, dependsOnMethods = "verifyHLCOneAccountSelected_IA")
	public void verifyAllHLC__IAInstAcc() {
		String IA_HLC_AllHLCOfCashAcc = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLCOfCashAcc = PropsUtil.getDataPropertyValue("IA_HLC_AllHLCOfCashAcc_43");

		} else {
			IA_HLC_AllHLCOfCashAcc = PropsUtil.getDataPropertyValue("IA_HLC_AllHLCOfCashAcc");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				IA_HLC_AllHLCOfCashAcc, "all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92231:verify HLC Amount", priority = 106, dependsOnMethods = "verifyHLCOneAccountSelected_IA")
	public void verifyAllHLCAmount__IAInstAcc() {

		String IA_HLC_CatPerAmountOfCashAcc = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmountOfCashAcc = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountOfCashAcc_43");

		} else {
			IA_HLC_CatPerAmountOfCashAcc = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountOfCashAcc");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmountOfCashAcc, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_HLC_CatAmountOfCashAcc"),
				"all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92231:verify refund message", priority = 107, dependsOnMethods = "verifyHLCOneAccountSelected_IA")
	public void verifyHLCRefundMsg__IAInstAcc() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmountOfCashAcc"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92265:,AT-92266:verify No Uncategorised txn", priority = 108, dependsOnMethods = "verifyHLCOneAccountSelected_IA")
	public void verifyHLCRUncatTxn__IAInstAcc() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnHeader"), "uncatgeory txn header is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnDateHeader") + " "
						+ expenseTrend.getMonthMMMMDD(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))),
				"uncategorized txn date header is not displayed");
		String IA_HLC_UnCatTxnDecsription = null;
		String IA_HLC_UnCatTxnAccount = null;
		String IA_HLC_UnCatTxnCategory = null;
		String IA_HLC_UnCatTxnAmount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_UnCatTxnDecsription = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnDecsription_43");
			IA_HLC_UnCatTxnAccount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAccount_43");
			IA_HLC_UnCatTxnCategory = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnCategory_43");
			IA_HLC_UnCatTxnAmount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAmount_43");

		} else {
			IA_HLC_UnCatTxnDecsription = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnDecsription");
			IA_HLC_UnCatTxnAccount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAccount");
			IA_HLC_UnCatTxnCategory = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnCategory");
			IA_HLC_UnCatTxnAmount = PropsUtil.getDataPropertyValue("IA_HLC_UnCatTxnAmount");

		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnDescription()),
				IA_HLC_UnCatTxnDecsription, "uncategorized  description is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAccount()),
				IA_HLC_UnCatTxnAccount, "uncatgeorized txn acount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnCat()),
				IA_HLC_UnCatTxnCategory, "uncatgeorized txn category is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatUnCatTxnAmount()),
				IA_HLC_UnCatTxnAmount, "uncategorized txn amount is not displayed");

	}
	}
	@Test(description = "AT-92235:verify Last month Time filter", priority = 109, dependsOnMethods = "backToIA")

	public void verifyHLCTotalExp_IALM() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_LastMonthTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String IA_HLC_TotalExpenseValueLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseValueLM = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValueLM_43");

		} else {
			IA_HLC_TotalExpenseValueLM = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValueLM");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseValueLM,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify all catgeory included", priority = 110, dependsOnMethods = "verifyHLCTotalExp_IALM")

	public void verifyHLCAllCatLbl_IALM() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false")) {
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		}
	}

	@Test(description = "AT-92276:verify last month Time filter HLC name", priority = 111, dependsOnMethods = "verifyHLCTotalExp_IALM")
	public void verifyAllHLC_IALM() {

		String IA_HLC_AllHLCLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLCLM = PropsUtil.getDataPropertyValue("IA_HLC_AllHLCLM_43");

		} else {
			IA_HLC_AllHLCLM = PropsUtil.getDataPropertyValue("IA_HLC_AllHLCLM");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize4")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), IA_HLC_AllHLCLM,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify  last month time filter HLC amount ", priority = 112, dependsOnMethods = "verifyHLCTotalExp_IALM")
	public void verifyAllHLCAmount_IALM() {
		String IA_HLC_CatPerAmountLM = null;
		String IA_HLC_CatAmountLM = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmountLM = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountLM_43");
			IA_HLC_CatAmountLM = PropsUtil.getDataPropertyValue("IA_HLC_CatAmountLM_43");

		} else {
			IA_HLC_CatPerAmountLM = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountLM");
			IA_HLC_CatAmountLM = PropsUtil.getDataPropertyValue("IA_HLC_CatAmountLM");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmountLM, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				IA_HLC_CatAmountLM, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify last month time filter refund message", priority = 113, dependsOnMethods = "verifyHLCTotalExp_IALM", enabled = false)
	public void verifyHLCRefundMsg_IALM() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmountLM"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92276:verify 3 month time filter", priority = 114, dependsOnMethods = "verifyHLCTotalExp_IALM")

	public void verifyHLCTotalExp_IA3M() {
		String IA_HLC_TotalExpenseValue3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseValue3M = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue3M_43");

		} else {
			IA_HLC_TotalExpenseValue3M = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue3M");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseValue3M,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92235:verify 3 month time filter included category lable", priority = 115, dependsOnMethods = "verifyHLCTotalExp_IA3M")

	public void verifyHLCAllCatLbl_IA3M() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false")) {
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"),
				"all catgeory lable should be displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}}

	@Test(description = "AT-92276:verify 3 month time filter HLC name", priority = 116, dependsOnMethods = "verifyHLCTotalExp_IA3M")
	public void verifyAllHLC_IA3M() {
		String IA_HLC_AllHLC3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLC3M = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC3M_43");

		} else {
			IA_HLC_AllHLC3M = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC3M");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), IA_HLC_AllHLC3M,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify 3 month time filter HLC Amount", priority = 117, dependsOnMethods = "verifyHLCTotalExp_IA3M")
	public void verifyAllHLCAmount_IA3M() {
		String IA_HLC_CatPerAmount3M = null;
		String IA_HLC_CatAmount3M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmount3M = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount3M_43");
			IA_HLC_CatAmount3M = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount3M_43");

		} else {
			IA_HLC_CatPerAmount3M = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount3M");
			IA_HLC_CatAmount3M = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount3M");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmount3M, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				IA_HLC_CatAmount3M, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify 3 month time filter refund message", priority = 118, dependsOnMethods = "verifyHLCTotalExp_IA3M", enabled = false)
	public void verifyHLCRefundMsg_IA3M() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmount3M"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify 6 month time filter", priority = 119, dependsOnMethods = "verifyHLCTotalExp_IALM")

	public void verifyHLCTotalExp_IA6M() {
		String IA_HLC_TotalExpenseValue6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseValue6M = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue6M_43");

		} else {
			IA_HLC_TotalExpenseValue6M = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue6M");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseValue6M,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify 6 month time filter all catgeory included lable", priority = 120, dependsOnMethods = "verifyHLCTotalExp_IA6M")

	public void verifyHLCAllCatLbl_IA6M() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false")) {
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}}

	@Test(description = "AT-92276:verify 6 month time filter HLC name", priority = 121, dependsOnMethods = "verifyHLCTotalExp_IA6M")
	public void verifyAllHLC_IA6M() {
		String IA_HLC_AllHLC6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLC6M = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC6M_43");

		} else {
			IA_HLC_AllHLC6M = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC6M");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), IA_HLC_AllHLC6M,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify 6 month time filter HLC Amount", priority = 122, dependsOnMethods = "verifyHLCTotalExp_IA6M")
	public void verifyAllHLCAmount_IA6M() {
		String IA_HLC_CatPerAmount6M = null;
		String IA_HLC_CatAmount6M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmount6M = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount6M_43");
			IA_HLC_CatAmount6M = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount6M_43");

		} else {
			IA_HLC_CatPerAmount6M = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount6M");
			IA_HLC_CatAmount6M = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount6M");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmount6M, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				IA_HLC_CatAmount6M, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify 6 month time filter refund message", priority = 123, dependsOnMethods = "verifyHLCTotalExp_IA6M", enabled = false)
	public void verifyHLCRefundMsg_IA6M() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmount6M"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify 12 month time filter", priority = 124, dependsOnMethods = "verifyHLCTotalExp_IALM")

	public void verifyHLCTotalExp_IA12M() {
		String IA_HLC_TotalExpenseValue12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseValue12M = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue12M_43");

		} else {
			IA_HLC_TotalExpenseValue12M = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValue12M");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseValue12M,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify 12 month time filter all category included lable", priority = 125, dependsOnMethods = "verifyHLCTotalExp_IA12M")

	public void verifyHLCAllCatLbl_IA12M() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}}

	@Test(description = "AT-92276:verify 12 month time filter HLC name", priority = 126, dependsOnMethods = "verifyHLCTotalExp_IA12M")
	public void verifyAllHLC_IA12M() {
		String IA_HLC_AllHLC12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLC12M = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC12M_43");

		} else {
			IA_HLC_AllHLC12M = PropsUtil.getDataPropertyValue("IA_HLC_AllHLC12M");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), IA_HLC_AllHLC12M,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify 12 month time filter HLC Amount", priority = 127, dependsOnMethods = "verifyHLCTotalExp_IA12M")
	public void verifyAllHLCAmount_IA12M() {
		String IA_HLC_CatPerAmount12M = null;
		String IA_HLC_CatAmount12M = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmount12M = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount12M_43");
			IA_HLC_CatAmount12M = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount12M_43");

		} else {
			IA_HLC_CatPerAmount12M = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmount12M");
			IA_HLC_CatAmount12M = PropsUtil.getDataPropertyValue("IA_HLC_CatAmount12M");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmount12M, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				IA_HLC_CatAmount12M, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify 12 month time filter adjustment message", priority = 128, dependsOnMethods = "verifyHLCTotalExp_IA12M", enabled = false)
	public void verifyHLCRefundMsg_IA12M() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"),
				"adjustment total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmount12M"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify this year time filter", priority = 129, dependsOnMethods = "verifyHLCTotalExp_IALM")

	public void verifyHLCTotalExp_IATY() {
		String IA_HLC_TotalExpenseValueTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_TotalExpenseValueTY = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValueTY_43");

		} else {
			IA_HLC_TotalExpenseValueTY = PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValueTY");

		}
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), IA_HLC_TotalExpenseValueTY,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify this year time filter All category included lable", priority = 130, dependsOnMethods = "verifyHLCTotalExp_IATY")

	public void verifyHLCAllCatLbl_IATY() {
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all catgeory lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}

	@Test(description = "AT-92276:verify this year time filter HLC name", priority = 131, dependsOnMethods = "verifyHLCTotalExp_IATY")
	public void verifyAllHLC_IATY() {
		String IA_HLC_AllHLCTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_AllHLCTY = PropsUtil.getDataPropertyValue("IA_HLC_AllHLCTY_43");

		} else {
			IA_HLC_AllHLCTY = PropsUtil.getDataPropertyValue("IA_HLC_AllHLCTY");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()), IA_HLC_AllHLCTY,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify this year time filter HLC Amount", priority = 132, dependsOnMethods = "verifyHLCTotalExp_IATY")
	public void verifyAllHLCAmount_IATY() {
		String IA_HLC_CatPerAmountTY = null;
		String IA_HLC_CatAmountTY = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_HLC_CatPerAmountTY = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountTY_43");
			IA_HLC_CatAmountTY = PropsUtil.getDataPropertyValue("IA_HLC_CatAmountTY_43");

		} else {
			IA_HLC_CatPerAmountTY = PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountTY");
			IA_HLC_CatAmountTY = PropsUtil.getDataPropertyValue("IA_HLC_CatAmountTY");

		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				IA_HLC_CatPerAmountTY, "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				IA_HLC_CatAmountTY, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify this year time filter adjustment message", priority = 133, dependsOnMethods = "verifyHLCTotalExp_IATY", enabled = false)
	public void verifyHLCRefundMsg_IATY() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmountTY"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92235:verify custom date time filter is displaying", priority = 134, dependsOnMethods = "verifyHLCTotalExp_IALM")

	public void verifyHLCTotalExp_IACD() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(expenseTrend.getMonthsMM(-1), expenseTrend.getMonthsMM(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseValueCD"), "total expense amount is not displayed");
	}

	@Test(description = "AT-92276:verify custom date time filter all category include lable", priority = 135, dependsOnMethods = "verifyHLCTotalExp_IACD")

	public void verifyHLCAllCatLbl_IACD() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"), "all category lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}}

	@Test(description = "AT-92276:verify custom date time filter HLC name", priority = 136, dependsOnMethods = "verifyHLCTotalExp_IACD")
	public void verifyAllHLC_IACD() {
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_HLC_AllHLCCD"), "all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92276:verify custom date time filter HLC amount", priority = 137, dependsOnMethods = "verifyHLCTotalExp_IACD")
	public void verifyAllHLCAmount_IACD() {
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountCD"), "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_HLC_CatAmountCD"),
				"all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92276:verify custom date time filter adjustment message", priority = 138, dependsOnMethods = "verifyHLCTotalExp_IACD", enabled = false)
	public void verifyHLCRefundMsg_IACD() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmountCD"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	@Test(description = "AT-92249,AT-92253,AT-92274:verify account are selected in accounts dropdown", priority = 139, dependsOnMethods = "backToIA")
	public void verifyUnselectAllHLC_IA() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{	expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.clickEIAHLCCatFilterCatlable();
		int catsize = expenseTrend.EIAHLCCatCheckBox().size();
		for (int i = 0; i < catsize; i++) {
			SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(expenseTrend.EIAHLCCatDone().isDisplayed());
		Assert.assertTrue(expenseTrend.EIAHLCCatCancel().isDisplayed());
		Assert.assertTrue(expenseTrend.EIAHLCCatDone().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("IA_HLC_Donedisabled")));
	}}

	@Test(description = "AT-92250,AT-92252,AT-92254:verify few accounts are selected", priority = 140, dependsOnMethods = "verifyUnselectAllHLC_IA")
	public void verifySelectFewHLCIA() {
		// SeleniumUtil.click(expenseTrend.EIAHLCCatCancel());
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		int catsize = expenseTrend.EIAHLCCatCheckBox().size();
		for (int i = 0; i < catsize - 2; i++) {
			SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
		}
		expenseTrend.clickEIAHLCCatDone();
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_9HLCIncluded"), " 9 HLC not included");
	}
	}
	@Test(description = "AT-92250,AT-92252,AT-92254:verify total income and HLC name", priority = 141, dependsOnMethods = "verifySelectFewHLCIA")

	public void verifySelectedHLCTotalExp_IA() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_9HLCIncludedExpAmount"),
				"total expense amount is not displayed");

		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize2")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_HLC_9HLCIncludedAmount"),
				"all 11 HLc category name is not displayed");
	}}

	@Test(description = "AT-92255:verify cancel category filter", priority = 142, dependsOnMethods = "verifySelectFewHLCIA")

	public void verifySelectedHLCCancel_IA() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		expenseTrend.clickEIAHLCCatFilterCatlable();
		for (int i = 0; i < 1; i++) {
			SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
		}
		SeleniumUtil.click(expenseTrend.EIAHLCCatCancel());
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_9HLCIncluded"), " 9 HLC is not included");
	}}

	@Test(description = "AT-92267,AT-92271:verify add manual txn,more and link account button", priority = 143, dependsOnMethods = "verifyUnselectAllHLC_IA")
	public void allLinkOption_HLC_IA() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAMTLink().getText(), PropsUtil.getDataPropertyValue("IA_MTLable"),
				" add manula account link is not displayed");
		Assert.assertEquals(expenseTrend.EIAMoreLink().getText(), PropsUtil.getDataPropertyValue("IA_MoreLable"),
				" more link is not displayed");
		Assert.assertEquals(expenseTrend.EIALinkAccount().getText(),
				PropsUtil.getDataPropertyValue("IA_LinkAccountLable"), " link account link is not displayed");
		}
	}

	@Test(description = "AT-92268,AT-92269:verify FT,Print and alert button", priority = 144, dependsOnMethods = "allLinkOption_HLC_IA")
	public void moreOptions_HLC_IA() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{expenseTrend.clickMore();
		Assert.assertEquals(expenseTrend.EIAFeatureTure().getText(),
				PropsUtil.getDataPropertyValue("IA_FeatureTureLable"), " feature link is not displayed");
		Assert.assertEquals(expenseTrend.EIAPrint().getText(), PropsUtil.getDataPropertyValue("IA_PrintLable"),
				" print link is not displayed ");
		Assert.assertTrue(
				expenseTrend.EIAAlertList().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_AlertList")),
				" alert link is not displayed in HLC page  ");
	}
	}
	@Test(description = "verify FT", priority = 145, dependsOnMethods = "moreOptions_HLC_IA")
	public void FTUnCatTxn_HLC_IA() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{	expenseTrend.clikcFT();
		Assert.assertTrue(
				expenseTrend.EIAHLCFTCloseButton(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).isDisplayed(),
				"close button is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCFTHeader(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_FT_UnCatHeader"), "uncategorzed txn FT header is not displayed");
		Assert.assertEquals(
				expenseTrend.EIAHLCFTMesg1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText()
						.replaceAll("\\n", ""),
				PropsUtil.getDataPropertyValue("IA_HLC_FT_UnCatMessage1"),
				"uncategorzed txn FT message1 is not displayed");

		Assert.assertEquals(expenseTrend.EIAHLCFTButton1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_FT_UnCatGotIt"),
				"uncategorzed txn FT got it button is not displayed");
		}
	}

	@Test(description = "verfiy FT close button", priority = 146, dependsOnMethods = "FTUnCatTxn_HLC_IA")
	public void FTUnCatTxnClose_HLC_IA() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{	SeleniumUtil.click(expenseTrend.EIAHLCFTCloseButton(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIAHLCFTHeaderList(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1"))
				.size() == Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_FTUESize")), "FT is not displayed");
	}}

	@Test(description = "verfiy FT goto button", priority = 147, dependsOnMethods = "FTUnCatTxnClose_HLC_IA")
	public void FTUnCatTxnGotIt_HLC_IA() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		SeleniumUtil.waitForPageToLoad(200);
		expenseTrend.clikcFT();
		SeleniumUtil.click(expenseTrend.EIAHLCFTButton1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIAHLCFTHeaderList(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1"))
				.size() == Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_FTUESize")), "FT is not displayed");
	}
	}
	@Test(description = "AT-92264:verify uncategozied txn edit", priority = 148, dependsOnMethods = "verifyUnselectAllHLC_IA")
	public void editTxn_HLC_IA() throws Exception {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		SeleniumUtil.click(expenseTrend.EIAHLCCatUnCatTxnDescription().get(0));
		SeleniumUtil.waitForPageToLoad();
		aggTxn.enterDecValue(PropsUtil.getDataPropertyValue("IA_HLC_UpdateUnCatDescription"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				expenseTrend.EIAHLCCatUnCatTxnDescription()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))).getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_UpdateUnCatDescription"),
				"uncategozied txn description is not updated");
		}
	}

	@Test(description = "AT-92277:verify This month time filter to Trend time filter", priority = 149, dependsOnMethods = "backToIA")
	public void verifyTMTFToTrend_IA() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_ThisMonthTmeFilter"));
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_ThisMonthTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_ThisMonthTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify last month time filter to Trend time filter", priority = 150, dependsOnMethods = "verifyTMTFToTrend_IA")
	public void verifyLMTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_LastMonthTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_LastMonthTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.monthEndDateDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMEndDate")),
								"MM/dd/yyy"),
				"This months date is not displaying in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_LastMonthTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.monthEndDateDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LMEndDate")),
								"MM/dd/yyy"),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify 3 month time filter to Trend time filter", priority = 151, dependsOnMethods = "verifyLMTFToTrend_IA")
	public void verify3MTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_3MEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify 6 month time filter to Trend time filter", priority = 152, dependsOnMethods = "verify3MTFToTrend_IA")
	public void verify6MTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_6MEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify This year time filter to Trend time filter", priority = 153, dependsOnMethods = "verify6MTFToTrend_IA")
	public void verifyTYTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TYEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify 12 month time filter to Trend time filter", priority = 154, dependsOnMethods = "verifyTYTFToTrend_IA")
	public void verify12MTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_12MEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify last year time filter to Trend time filter", priority = 155, dependsOnMethods = "verify12MTFToTrend_IA")
	public void verifyLYTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_LastYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_LastYearTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.yearEndDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYEndDate")),
								"MM/dd/yyy"),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_LastYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.yearEndDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_LYEndDate")),
								"MM/dd/yyy"),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92277:verify custom date time filter to Trend time filter", priority = 156, dependsOnMethods = "verifyLYTFToTrend_IA")
	public void verifyCDTFToTrend_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));

		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDStartDate"))),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDEndDate"))));
		SeleniumUtil.waitForPageToLoad();
		String HLCTimeFilterLable = expenseTrend.EIATimeFilterDropDownLabel().getText().trim();
		String HLCTimeFilterValue = expenseTrend.EIATimeFilterDropDownDate().getText().trim();
		expenseTrend.clickEIABackToEIA();
		Assert.assertEquals(HLCTimeFilterLable, PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"),
				"this months label is not displayed in time filter Tab");
		Assert.assertEquals(HLCTimeFilterValue,
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDStartDate")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDEndDate"))),
				"This months date is not displayed in time filter Tab");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDStartDate")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_CDEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92272:verify link account popup", priority = 157, dependsOnMethods = "verifyCDTFToTrend_IA")
	public void verifyLinkAccountPopup_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.click(expenseTrend.EIAAccountLink());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIALinkAccountPopUP().isDisplayed());

	}

	@Test(description = "AT-92231,AT-92234:verify group is selected in account filter", priority = 158, dependsOnMethods = "backToIA")
	public void groupFilter_IA() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		/*
		 * PageParser.forceNavigate("AccountsPage", d);
		 * SeleniumUtil.waitForPageToLoad(10000);
		 * txnAcct.createExpenseGroup(PropsUtil.getDataPropertyValue("IA_GroupName"));
		 */
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("IA_GroupName"));
		SeleniumUtil.waitForPageToLoad();
		if(expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
			}
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_GroupName"),
				"selected account is not displayed in account drop down");

	}

	@Test(description = "AT-92231:verify total expense", priority = 159, dependsOnMethods = "groupFilter_IA")

	public void verifyHLCTotalExp_GF_IA() {
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseLable"), " total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_TotalExpenseOfCashAccount"),
				"total expense amount is not displayed");
	}

	@Test(description = "AT-92231:verify group filter HLC name", priority = 160, dependsOnMethods = "groupFilter_IA")

	public void verifyHLCAllCatLbl_GF_IA() {
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false"))
		{
		Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_AllCategoryIncludeLbl"),
				"all catgeory lable should be displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatFilterCatlable().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HLC_CatFilterLable"), "filter catgeory link is not displayed");
	}}

	@Test(description = "AT-92231:verify group filter HLC Amount", priority = 161, dependsOnMethods = "groupFilter_IA")
	public void verifyAllHLC_GF_IA() {
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_HLC_CatgeoryIconSize")),
				"all 11 categroy icon should be displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_HLC_AllHLCOfCashAcc"), "all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-92231:verify  time filter is displaying", priority = 162, dependsOnMethods = "groupFilter_IA")
	public void verifyAllHLCAmount_GF_IA() {
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_HLC_CatPerAmountOfCashAcc"),
				"all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_HLC_CatAmountOfCashAcc"),
				"all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-92231:verify  time filter is displaying", priority = 163, dependsOnMethods = "groupFilter_IA")
	public void verifyHLCRefundMsg_GF_IA() {
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundMesg().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundMsg"), "refund total recieved message is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundAmountOfCashAcc"), "total HLC refund is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundViewDetails().getText(),
				PropsUtil.getDataPropertyValue("IA_HLC_RefundViewDetails"), "view details link is not displayed");
	}

	public void addManualAdjTxn() {
		expenseTrend.clickEIAMTLink();
		/*
		 * add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue(
		 * "IA_MTAmount"), PropsUtil.getDataPropertyValue("IA_MTDescription"),
		 * PropsUtil.getDataPropertyValue("IA_MTTxnType"),
		 * PropsUtil.getDataPropertyValue("IA_MTAccounts"),
		 * PropsUtil.getDataPropertyValue("IA_MTFrequency"), 0,
		 * PropsUtil.getDataPropertyValue("IA_MTCategory"),
		 * PropsUtil.getDataPropertyValue("IA_MTNote"),
		 * PropsUtil.getDataPropertyValue("IA_MTCheck"));
		 */
		String IA_MTCategory5 = null;
		String IA_MTCategory6 = null;
		String IA_MTCategory7 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("IA_MTCategory5_43");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("IA_MTCategory6_43");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("IA_MTCategory7_43");

		} else {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("IA_MTCategory5");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("IA_MTCategory6");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("IA_MTCategory7");

		}
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount5"),
				PropsUtil.getDataPropertyValue("IA_MTDescription5"), PropsUtil.getDataPropertyValue("IA_MTTxnType5"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts5"), PropsUtil.getDataPropertyValue("IA_MTFrequency5"), 0,
				IA_MTCategory5, PropsUtil.getDataPropertyValue("IA_MTNote5"),
				PropsUtil.getDataPropertyValue("IA_MTCheck5"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount6"),
				PropsUtil.getDataPropertyValue("IA_MTDescription6"), PropsUtil.getDataPropertyValue("IA_MTTxnType6"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts6"), PropsUtil.getDataPropertyValue("IA_MTFrequency6"), 0,
				IA_MTCategory6, PropsUtil.getDataPropertyValue("IA_MTNote6"),
				PropsUtil.getDataPropertyValue("IA_MTCheck6"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount7"),
				PropsUtil.getDataPropertyValue("IA_MTDescription7"), PropsUtil.getDataPropertyValue("IA_MTTxnType7"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts7"), PropsUtil.getDataPropertyValue("IA_MTFrequency7"), 0,
				IA_MTCategory7, PropsUtil.getDataPropertyValue("IA_MTNote7"),
				PropsUtil.getDataPropertyValue("IA_MTCheck7"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount8"),
				PropsUtil.getDataPropertyValue("IA_MTDescription8"), PropsUtil.getDataPropertyValue("IA_MTTxnType8"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts8"), PropsUtil.getDataPropertyValue("IA_MTFrequency8"), 0,
				PropsUtil.getDataPropertyValue("IA_MTCategory8"), PropsUtil.getDataPropertyValue("IA_MTNote8"),
				PropsUtil.getDataPropertyValue("IA_MTCheck8"));
		SeleniumUtil.waitForPageToLoad();

	}

	public void addManualReundTxn() {
		expenseTrend.clickEIAMTLink();

		String IA_MTCategory5 = null;
		String IA_MTCategory6 = null;
		String IA_MTCategory7 = null;
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_MTCategory5_43");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_MTCategory6_43");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_MTCategory7_43");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_MTCategory7_43");

		} else {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_MTCategory6");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_MTCategory7");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_MTCategory8");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_MTCategory9");

		}
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount6"),
				PropsUtil.getDataPropertyValue("EA_MTDescription6"), PropsUtil.getDataPropertyValue("EA_MTTxnType6"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts6"), PropsUtil.getDataPropertyValue("EA_MTFrequency6"), 0,
				IA_MTCategory5, PropsUtil.getDataPropertyValue("EA_MTNote6"),
				PropsUtil.getDataPropertyValue("EA_MTCheck5"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount7"),
				PropsUtil.getDataPropertyValue("EA_MTDescription7"), PropsUtil.getDataPropertyValue("EA_MTTxnType7"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts7"), PropsUtil.getDataPropertyValue("EA_MTFrequency7"), 0,
				IA_MTCategory6, PropsUtil.getDataPropertyValue("EA_MTNote7"),
				PropsUtil.getDataPropertyValue("EA_MTCheck7"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount8"),
				PropsUtil.getDataPropertyValue("EA_MTDescription8"), PropsUtil.getDataPropertyValue("EA_MTTxnType8"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts8"), PropsUtil.getDataPropertyValue("EA_MTFrequency8"), 0,
				IA_MTCategory7, PropsUtil.getDataPropertyValue("EA_MTNote8"),
				PropsUtil.getDataPropertyValue("EA_MTCheck8"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount9"),
				PropsUtil.getDataPropertyValue("EA_MTDescription9"), PropsUtil.getDataPropertyValue("EA_MTTxnType9"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts9"), PropsUtil.getDataPropertyValue("EA_MTFrequency9"), 0,
				IA_MTCategory8, PropsUtil.getDataPropertyValue("EA_MTNote9"),
				PropsUtil.getDataPropertyValue("EA_MTCheck9"));
		SeleniumUtil.waitForPageToLoad();

	}

}
