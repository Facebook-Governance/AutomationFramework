/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Exapnse_IncomeAnalaysis_Enhancement;

import java.util.ArrayList;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.Login.LoginPage;

import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ExpanseAnalysis_MLC_TrendPopUp_Test extends TestBase {

	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Categorization_Rules_Loc rule;
	Aggregated_Transaction_Details_Loc aggTxn;

	@BeforeClass(alwaysRun = true)

	public void init() throws Exception {
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);
		aggTxn = new Aggregated_Transaction_Details_Loc(d);
		accAddition = new AccountAddition();
		expenseTrend = new Expense_Income_Trend_Loc(d);
		util = new SeleniumUtil();
		txnAcct = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_MLC_DagSite"),
				PropsUtil.getDataPropertyValue("EIA_MLC_DagSite_Password")));
		expenseTrend.pageRefresh();
		rule.subCategory_EA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.FTUE();
		addManualReundTxn();
	}

	@Test(description = "AT-93451,AT-93452,AT-93455:verify MLC header", priority = 1)
	public void verifyMLCHeader() {
		String EA_MLC_MLC_HLC1 = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC1_43");
		} else {
			EA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC1");
		}
		expenseTrend.clickEAHLC(EA_MLC_MLC_HLC1,
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		Assert.assertEquals(expenseTrend.EIAByCategory().getText(), EA_MLC_MLC_HLC1,
				"HLC Category Header is not displayed");
		if (expenseTrend.isbacktoEIAicon()) {
			Assert.assertTrue(expenseTrend.backtoEIAicon().isDisplayed(), "BacktoEA is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIABackToEIA().getText(),
					PropsUtil.getDataPropertyValue("EA_MLC_MLC_BackToHeader"), "HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93451,AT-93452,AT-93455:verify MLC category lable", priority = 2) // , dependsOnMethods =
																								// "verifyMLCHeader"
	public void verifyMLCCategoryHLC() {
		String EA_MLC_CategoryLable = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_CategoryLable = PropsUtil.getDataPropertyValue("EA_MLC_CategoryLable_43");
		} else {
			EA_MLC_CategoryLable = PropsUtil.getDataPropertyValue("EA_MLC_CategoryLable");
		}
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(), EA_MLC_CategoryLable,
					"HLC Category Header is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(), EA_MLC_CategoryLable,
					"HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93453,AT-93454:verify chart header", priority = 3) // , dependsOnMethods = "verifyMLCHeader"
	public void verifyTotalMLCExp() {
		String EA_MLC_HLCSpendLable = null;
		String EA_MLC_HLCSpendValue = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_HLCSpendLable = PropsUtil.getDataPropertyValue("EA_MLC_HLCSpendLable_43");
			EA_MLC_HLCSpendValue = PropsUtil.getDataPropertyValue("EA_MLC_HLCSpendValue_43");
		} else {
			EA_MLC_HLCSpendLable = PropsUtil.getDataPropertyValue("EA_MLC_HLCSpendLable");
			EA_MLC_HLCSpendValue = PropsUtil.getDataPropertyValue("EA_MLC_HLCSpendValue");
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(), EA_MLC_HLCSpendLable,
				" total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(), EA_MLC_HLCSpendValue,
				"total expense amount is not displayed");
	}

	@Test(description = "AT-93453:verify all MLC name", priority = 4) // , dependsOnMethods = "verifyMLCHeader"
	public void verifyAllMLC() {
		String EA_MLC_NameSize = null;
		String EA_MLC_List1 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_NameSize = PropsUtil.getDataPropertyValue("EA_MLC_NameSize_43");
			EA_MLC_List1 = PropsUtil.getDataPropertyValue("EA_MLC_List1_43");
		} else {
			EA_MLC_NameSize = PropsUtil.getDataPropertyValue("EA_MLC_NameSize");
			EA_MLC_List1 = PropsUtil.getDataPropertyValue("EA_MLC_List1");
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatIcon().size(), Integer.parseInt(EA_MLC_NameSize),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAMLCCatNameList()), EA_MLC_List1,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-93453:verify MLC amount", priority = 5, dependsOnMethods = "verifyMLCHeader")
	public void verifyAllMLCAmount() {
		String EA_MLC_PerList1 = null;
		String EA_MLC_AmountList1 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_PerList1 = PropsUtil.getDataPropertyValue("EA_MLC_PerList1_43");
			EA_MLC_AmountList1 = PropsUtil.getDataPropertyValue("EA_MLC_AmountList1_43");
		} else {
			EA_MLC_PerList1 = PropsUtil.getDataPropertyValue("EA_MLC_PerList1");
			EA_MLC_AmountList1 = PropsUtil.getDataPropertyValue("EA_MLC_AmountList1");
		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatPerList()), EA_MLC_PerList1,
				"all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatAmountList()),
				EA_MLC_AmountList1, "all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-93453:verify back to expense analysis", priority = 6, dependsOnMethods = "verifyMLCHeader")
	public void verifyBackToCat() {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.backtoEIAicon());
			SeleniumUtil.waitForPageToLoad();
		} else {
			expenseTrend.clickEIABackToEIA();
		}
		Assert.assertEquals(expenseTrend.EIAHeader().getText(), PropsUtil.getDataPropertyValue("EA_ExpenseHeader"),
				"HLC Category Header is not displayed");
	}

	@Test(description = "AT-93462:verify MLC txn", priority = 7, dependsOnMethods = "verifyBackToCat")
	public void verifyMLCTxn() {

		String EA_MLC_MLC_HLC2 = null;
		String EA_MLC_TxnList = null;

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_MLC_HLC2 = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC2_43");
			EA_MLC_TxnList = PropsUtil.getDataPropertyValue("EA_MLC_TxnList_43");

		} else {
			EA_MLC_MLC_HLC2 = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC2");
			EA_MLC_TxnList = PropsUtil.getDataPropertyValue("EA_MLC_TxnList");
		}
		expenseTrend.clickEAHLC(EA_MLC_MLC_HLC2,
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));

		int txnSize = expenseTrend.EIAMLCHeaderTxnCatList().size();
		int txncount = 0;

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLCHeathCare")));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("EA_MLC_MLC_TxnHeader_mob"), "txn header is not displayed");
			Assert.assertTrue(txnSize == Integer.parseInt(EA_MLC_TxnList));
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		} else {
			for (int i = 0; i < txnSize; i++) {
				System.out.println(expenseTrend.EIAMLCHeaderTxnCatList().get(i).getText());

				if (expenseTrend.EIAMLCHeaderTxnCatList().get(i).getText().equals(EA_MLC_MLC_HLC2))

				{
					txncount = txncount + 1;
				}
			}
			Assert.assertEquals(expenseTrend.EIAMLCTxnHeader().getText(),
					PropsUtil.getDataPropertyValue("EA_MLC_MLC_TxnHeader"), "txn header is not displayed");
			Assert.assertEquals(txncount, Integer.parseInt(EA_MLC_TxnList));
		}

	}

	@Test(description = "AT-93456:verify back HLC ", priority = 8) // , dependsOnMethods = "verifyMLCTxn"
	public void verifyBackToHLC() {
		SeleniumUtil.click(expenseTrend.EIAMLCDonutBack());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
				PropsUtil.getDataPropertyValue("EA_ExpenseByCategory"), "HLC Category Header is not displayed");
	}

	@Test(description = "AT-93715,AT-93727:verify trend popup", priority = 9) // , dependsOnMethods = "verifyBackToHLC"
	public void verifyTrendPopUp() {
		String EA_MLC_MLC_HLC1 = null;
		String EA_MLC_TrendPopUpHeader = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC1_43");
			EA_MLC_TrendPopUpHeader = PropsUtil.getDataPropertyValue("EA_MLC_TrendPopUpHeader_43");
		} else {
			EA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC1");
			EA_MLC_TrendPopUpHeader = PropsUtil.getDataPropertyValue("EA_MLC_TrendPopUpHeader");
		}
		expenseTrend.clickEAHLC(EA_MLC_MLC_HLC1);
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickTrendPopUp();
		Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(), EA_MLC_TrendPopUpHeader,
				"trend popup header is not displayed");
	}

	@Test(description = "AT-93716,AT-93728:verify trend months", priority = 10, dependsOnMethods = "verifyTrendPopUp")
	public void verifydefaultTrendMonth() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-93718,AT-93719,AT-93728:all 6 month expense amount", priority = 11, dependsOnMethods = "verifyTrendPopUp")
	public void verifydefaultTrendMonthAmt() {
		String EA_MLC_TrendPopUpAmount = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_TrendPopUpAmount = PropsUtil.getDataPropertyValue("EA_MLC_TrendPopUpAmount_43");
		} else {
			EA_MLC_TrendPopUpAmount = PropsUtil.getDataPropertyValue("EA_MLC_TrendPopUpAmount");
		}
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				EA_MLC_TrendPopUpAmount, "all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-93721,AT-93722,AT-93728:all 6 month expense amount avg", priority = 12, dependsOnMethods = "verifyTrendPopUp")

	public void verifydefaultTrendMonthAvgamt() {
		String EA_MLC_TrendPopUpAvgMesg = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_TrendPopUpAvgMesg = PropsUtil.getDataPropertyValue("EA_MLC_TrendPopUpAvgMesg_43");
		} else {
			EA_MLC_TrendPopUpAvgMesg = PropsUtil.getDataPropertyValue("EA_MLC_TrendPopUpAvgMesg");
		}
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"), EA_MLC_TrendPopUpAvgMesg);
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-93716,AT-93719,AT-93728:verify trend popup chart months", priority = 14, dependsOnMethods = "verifyTrendPopUp")
	public void verifydefaultTrendChartMonth() {
		List<String> actualValue = new ArrayList<String>();
		actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
		List<String> expectedValue = new ArrayList<String>();
		expectedValue = expenseTrend
				.xAixValueWithoutSpace(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
				"all 6 month xaxis avalue is not displayed ");
	}

	@Test(description = "AT-93724,AT-93725:verify trend popup close", priority = 15, dependsOnMethods = "verifyTrendPopUp")
	public void verifyCloseTrendPopup() {
		SeleniumUtil.click(expenseTrend.EIAMLCTrendPopUpClose());
		SeleniumUtil.waitForPageToLoad(2000);
		By mlcTrendPopupHeader = SeleniumUtil.getByObject("Expense", null, "EIAMLCTrendPopUpHeader");
		Assert.assertEquals(SeleniumUtil.getElementCount(mlcTrendPopupHeader),0);
	}

	@Test(description = "AT-93462:verify MLC txn header", priority = 16)
	public void verifyMLCTxnPopup() {

		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		String EA_MLC_MLC1 = null;
		String EA_MLC_MLC_TxnPopUpHeader = null;
		String EA_MLC_PopupTxnList = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_MLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC1_43");
			EA_MLC_MLC_TxnPopUpHeader = PropsUtil.getDataPropertyValue("EA_MLC_MLC_TxnPopUpHeader_43");
			EA_MLC_PopupTxnList = PropsUtil.getDataPropertyValue("EA_MLC_PopupTxnList_43");
		} else {
			EA_MLC_MLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC1");
			EA_MLC_MLC_TxnPopUpHeader = PropsUtil.getDataPropertyValue("EA_MLC_MLC_TxnPopUpHeader");
			EA_MLC_PopupTxnList = PropsUtil.getDataPropertyValue("EA_MLC_PopupTxnList");
		}
		expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC3"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.EIAMLCCatName(EA_MLC_MLC1));
		SeleniumUtil.waitForPageToLoad();
		int txnSize = expenseTrend.EIAMLCTxnPopUpCatList().size();
		int txncount = 0;

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("EA_MLC_MLC_TxnPopUpHeader_mob"));
			Assert.assertTrue(txnSize == Integer.parseInt(EA_MLC_PopupTxnList));
		} else {
			for (int i = 0; i < txnSize; i++) {

				if (expenseTrend.EIAMLCTxnPopUpCatList().get(i).getText().equals(EA_MLC_MLC1))

				{
					txncount = txncount + 1;
				}
			}
			Assert.assertTrue(txncount == Integer.parseInt(EA_MLC_PopupTxnList));
			Assert.assertEquals(expenseTrend.EIAMLCTxnPopUpHeader().getText(), EA_MLC_MLC_TxnPopUpHeader);

		}
	}

	@Test(description = "AT-93459:verify subcategory", priority = 17) // , dependsOnMethods = "verifyMLCTxnPopup")
	public void verifySubCategory() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			String EA_MLC_MLC1 = null;
			String EA_MLC_SubCatgeoriesList1 = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
				EA_MLC_MLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC1_43");
				EA_MLC_SubCatgeoriesList1 = PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoriesList1_43");
			} else {
				EA_MLC_MLC1 = PropsUtil.getDataPropertyValue("EA_MLC_MLC1");
				EA_MLC_SubCatgeoriesList1 = PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoriesList1");
			}
			expenseTrend.updateTxn(PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeories1").split(","),
					PropsUtil.getDataPropertyValue("EA_MLC_Descriptions1").split(","),
					PropsUtil.getDataPropertyValue("EA_MLC_Amounts1").split("/"), EA_MLC_MLC1);
			SeleniumUtil.waitForPageToLoad(1000);
			if (expenseTrend.isEIAcloseTransPopup()) {
				SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
			}

			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(expenseTrend.EIAMLCCatName(EA_MLC_MLC1));
			SeleniumUtil.waitForPageToLoad();
			util.assertExpectedActualList(
					util.getWebElementsValue(
							expenseTrend.EIAMLCSubCatgList(PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoryNo"))),
					EA_MLC_SubCatgeoriesList1, "subcategory is not displayed");
		}
	}

	@Test(description = "AT-93460,AT-93461:verify Sub category amount", priority = 18, dependsOnMethods = "verifySubCategory")
	public void verifySubCategoryper() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			String EA_MLC_SubCatgeoriesAmountList = null;
			String EA_MLC_SubCatgeoriesTotalAmount = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
				EA_MLC_SubCatgeoriesAmountList = PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoriesAmountList_43");
				EA_MLC_SubCatgeoriesTotalAmount = PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoriesTotalAmount_43");
			} else {
				EA_MLC_SubCatgeoriesAmountList = PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoriesAmountList");
				EA_MLC_SubCatgeoriesTotalAmount = PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoriesTotalAmount");
			}
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(expenseTrend
							.EIAMLCSubCatgAmountList(PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoryNo"))),
					EA_MLC_SubCatgeoriesAmountList, "");
			Assert.assertEquals(expenseTrend.EIAMLCCatAmountList()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoryAmountList1"))).getText(),
					EA_MLC_SubCatgeoriesTotalAmount, "subcategory amount is not displayed");

		}
	}

	@Test(description = "AT-93464:verify  time filter is displaying", priority = 19, dependsOnMethods = "verifySubCategory")
	public void verifySubCategoryTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			int txnSize = expenseTrend.EIAMLCTxnPopUpCatList().size();
			int txncount = 0;
			for (int i = 0; i < txnSize; i++) {

				if (expenseTrend.EIAMLCTxnPopUpCatList().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("EA_MLC_SubCatTXnCategories"))) {
					txncount = txncount + 1;
				}
			}
			Assert.assertTrue(txncount == Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoryTxnSize")),
					"sub catgeory txn is not displayed");
		}
	}

	@Test(description = "AT-93466:verify change catgeory ", priority = 20, dependsOnMethods = "verifySubCategory")
	public void verifyChangeCategory() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(expenseTrend.EIAMLCDonutBack());
			SeleniumUtil.waitForPageToLoad();
			expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC4"));
			SeleniumUtil.waitForPageToLoad();
			String EA_MLC_MLC4 = null;
			String EA_MLC_MLC_HLC4Updating = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
				EA_MLC_MLC4 = PropsUtil.getDataPropertyValue("EA_MLC_MLC4_43");
				EA_MLC_MLC_HLC4Updating = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC4Updating_43");
			} else {
				EA_MLC_MLC4 = PropsUtil.getDataPropertyValue("EA_MLC_MLC4");
				EA_MLC_MLC_HLC4Updating = PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC4Updating");
			}
			expenseTrend.changeCat(EA_MLC_MLC_HLC4Updating, EA_MLC_MLC4,
					PropsUtil.getDataPropertyValue("EA_MLC_MLC_UpdatedAmountList").split("/"));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(expenseTrend.EIAMLCDonutBack());
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
					PropsUtil.getDataPropertyValue("EA_ExpenseByCategory"), "HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93466:verify verify updated category", priority = 21, dependsOnMethods = "verifyChangeCategory")
	public void verifyChangeCategoryUpdated() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("EA_MLC_MLC_HLC5"));
			SeleniumUtil.waitForPageToLoad();
			String EA_MLC_MLC4 = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
				EA_MLC_MLC4 = PropsUtil.getDataPropertyValue("EA_MLC_MLC4_43");
			} else {
				EA_MLC_MLC4 = PropsUtil.getDataPropertyValue("EA_MLC_MLC4");
			}
			SeleniumUtil.click(expenseTrend.EIAMLCCatName(EA_MLC_MLC4));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(expenseTrend.EIAMLCTxnPopUpCatList().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_UpdatedPopupTxnList")));
		}

	}

	@Test(description = "AT-93467AT-93469,AT-93471,AT-93472,AT-93475,AT-92261:verify trend popup", priority = 22)
	public void verifyRefundPopup() {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.clickViewDetails();

		if (expenseTrend.isEIAcloseTransPopup()) {
			Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("EA_HLC_Refund_PopUpHeader"), "trend popup header is not displayed");
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());

		} else {
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
					PropsUtil.getDataPropertyValue("EA_HLC_Refund_PopUpHeader"), "trend popup header is not displayed");
			Assert.assertEquals(expenseTrend.EIABackToEIA().getText(),
					PropsUtil.getDataPropertyValue("EA_HLC_Refund_BackToEA"), "back to expesne is not displayed");
			Assert.assertEquals(expenseTrend.EIARefundPrint().getText(),
					PropsUtil.getDataPropertyValue("EA_HLC_Refund_Print"), "print option is not displayed");
		}
	}

	@Test(description = "AT-93473,AT-93474,AT-93481:verify refund txn", priority = 23, dependsOnMethods = "verifyRefundPopup")
	public void verifyRefundTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(expenseTrend.EIARefundTXnCat()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_RefundTxnIndex"))));
			SeleniumUtil.waitForPageToLoad(2000);
			aggTxn.enterDecValue(PropsUtil.getDataPropertyValue("EA_HLC_Refund_TXnDescription"));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(
					expenseTrend.EIARefundTXnDescription()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_RefundTxnIndex"))).getText(),
					PropsUtil.getDataPropertyValue("EA_HLC_Refund_TXnDescription"), "refund txn is not displayed");
		}
	}

	@Test(description = "AT-93482:verify refund catgeory filter", priority = 24, dependsOnMethods = "verifyRefundTxn")
	public void verifyRefundCatFilter() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			expenseTrend.clickEIABackToEIA();
			expenseTrend.clickEIAHLCCatFilterCatlable();

			int catsize = expenseTrend.EIAHLCCatCheckBox().size();
			String EA_HLC_5HLCIncludedRefund = null;
			String EA_HLC_Refund_CategoryList = null;
			String EA_HLC_5HLCIncluded = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
				EA_HLC_5HLCIncludedRefund = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncludedRefund_43");
				EA_HLC_Refund_CategoryList = PropsUtil.getDataPropertyValue("EA_HLC_Refund_CategoryList_43");
				EA_HLC_5HLCIncluded = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncluded_43");
				for (int i = 0; i < catsize - 5; i++) {
					SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
				}
			} else {
				EA_HLC_5HLCIncludedRefund = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncludedRefund");
				EA_HLC_Refund_CategoryList = PropsUtil.getDataPropertyValue("EA_HLC_Refund_CategoryList");
				EA_HLC_5HLCIncluded = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncluded");
				for (int i = 0; i < catsize - 6; i++) {
					SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
				}
			}

			expenseTrend.clickEIAHLCCatDone();
			SeleniumUtil.waitForPageToLoad();
			String selectedCat = expenseTrend.EIAHLCCatAllCategoryLable().getText();
			String refundAmt = expenseTrend.EIAHLCCatRefundAmount().getText();
			expenseTrend.clickViewDetails();

			Assert.assertEquals(selectedCat, EA_HLC_5HLCIncluded, " 9 HLC is not included");
			Assert.assertEquals(refundAmt, EA_HLC_5HLCIncludedRefund, " 9 HLC is not included");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIARefundTXnCat()),
					EA_HLC_Refund_CategoryList, "refund txn is not displayed");
		}

	}

	@Test(description = "AT-93483:verify  refund to HLC navigation", priority = 25, dependsOnMethods = "verifyRefundTxn")
	public void verifyRefundToHLC() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			String EA_HLC_5HLCIncludedRefund = null;

			String EA_HLC_5HLCIncluded = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
				EA_HLC_5HLCIncludedRefund = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncludedRefund_43");

				EA_HLC_5HLCIncluded = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncluded_43");

			} else {
				EA_HLC_5HLCIncludedRefund = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncludedRefund");

				EA_HLC_5HLCIncluded = PropsUtil.getDataPropertyValue("EA_HLC_5HLCIncluded");

			}

			expenseTrend.clickEIABackToEIA();
			Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(), EA_HLC_5HLCIncluded,
					" 9 HLC is not included");
			Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(), EA_HLC_5HLCIncludedRefund,
					" 9 HLC is not included");
		}
	}

	@Test(description = "AT-93484:verify  time filter is displaying", priority = 26, dependsOnMethods = "verifyRefundToHLC")
	public void verifyUpdateRefundTXn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			String EA_HLC_Refund_Category = null;
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

				EA_HLC_Refund_Category = PropsUtil.getDataPropertyValue("EA_HLC_Refund_Category_43");

			} else {

				EA_HLC_Refund_Category = PropsUtil.getDataPropertyValue("EA_HLC_Refund_Category");

			}
			expenseTrend.clickViewDetails();
			expenseTrend.updateRefundTxn(EA_HLC_Refund_Category);
			String updtaedCat = expenseTrend.EIARefundTXnCat()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_RefundTxnIndex"))).getText();
			expenseTrend.clickEIABackToEIA();
			Assert.assertEquals(updtaedCat, PropsUtil.getDataPropertyValue("EA_HLC_Refund_Category"),
					"refund txn is not updated");
		}
	}

	@Test(description = "AT-93451,AT-93452,AT-93455:verify no accounts page in Eexpanse Page", priority = 27)
	public void verifyMLCHeader_IA() {
		rule.subCategory_IA();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		addManualAdjTxn();

		String IA_MLC_MLC_HLC1 = null;
		String IA_MLC_MLC_HLCHeader = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC1_43");
			IA_MLC_MLC_HLCHeader = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLCHeader_43");

		} else {

			IA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC1");
			IA_MLC_MLC_HLCHeader = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLCHeader");

		}
		expenseTrend.clickEAHLC(IA_MLC_MLC_HLC1,
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		Assert.assertEquals(expenseTrend.EIAByCategory().getText(), IA_MLC_MLC_HLCHeader,
				"HLC Category Header is not displayed");
		if (expenseTrend.isbacktoEIAicon()) {
			Assert.assertTrue(expenseTrend.backtoEIAicon().isDisplayed(), "BacktoIA is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIABackToEIA().getText(),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_BackToHeader"), "HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93451,AT-93452,AT-93455verify no accounts page in Eexpanse Page", priority = 28) //
	public void verifyMLCCategoryHLC_IA() {
		String IA_MLC_CategoryLable = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_CategoryLable = PropsUtil.getDataPropertyValue("IA_MLC_CategoryLable_43");

		} else {

			IA_MLC_CategoryLable = PropsUtil.getDataPropertyValue("IA_MLC_CategoryLable");

		}
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(), IA_MLC_CategoryLable,
					"HLC Category Header is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(), IA_MLC_CategoryLable,
					"HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93453,AT-93454:verify total income amount in chart", priority = 29) // , dependsOnMethods =
																								// "verifyMLCHeader_IA"
	public void verifyTotalMLCExp_IA() {
		String IA_MLC_HLCSpendLable = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_HLCSpendLable = PropsUtil.getDataPropertyValue("IA_MLC_HLCSpendLable_43");

		} else {

			IA_MLC_HLCSpendLable = PropsUtil.getDataPropertyValue("IA_MLC_HLCSpendLable");

		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharHeader().getText().trim(), IA_MLC_HLCSpendLable,
				" total expenses lable is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_MLC_HLCSpendValue"), "total expense amount is not displayed");
	}

	@Test(description = "AT-93453:verify MLC name", priority = 30) // , dependsOnMethods = "verifyMLCHeader_IA"
	public void verifyAllMLC_IA() {
		String IA_MLC_List1 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_List1 = PropsUtil.getDataPropertyValue("IA_MLC_List1_43");

		} else {

			IA_MLC_List1 = PropsUtil.getDataPropertyValue("IA_MLC_List1");

		}
		Assert.assertTrue(
				expenseTrend.EIAHLCCatIcon().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("IA_MLC_NameSize")),
				"all 11 categroy icon is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAMLCCatNameList()), IA_MLC_List1,
				"all 11 HLc category name is not displayed");
	}

	@Test(description = "AT-93453:verify MLC amount", priority = 31) // , dependsOnMethods = "verifyMLCHeader_IA"
	public void verifyAllMLCAmount_IA() {
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_MLC_PerList1"), "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_MLC_AmountList1"),
				"all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-93453:verify Back To expense", priority = 32) // , dependsOnMethods = "verifyMLCHeader_IA"
	public void verifyBackToCat_IA() {
		expenseTrend.clickEIABackToEIA();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAHeader().getText(),
					PropsUtil.getDataPropertyValue("IA_ExpenseHeader_mob"), "HLC Category Header is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIAHeader().getText(), PropsUtil.getDataPropertyValue("IA_ExpenseHeader"),
					"HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93462:verify MLC txn", priority = 33, dependsOnMethods = "verifyBackToCat_IA")
	public void verifyMLCTxn_IA() {

		String IA_MLC_MLC_HLC2 = null;
		String IA_MLC_TxnList = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_MLC_HLC2 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC2_43");
			IA_MLC_TxnList = PropsUtil.getDataPropertyValue("IA_MLC_TxnList_43");

		} else {

			IA_MLC_MLC_HLC2 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC2");
			IA_MLC_TxnList = PropsUtil.getDataPropertyValue("IA_MLC_TxnList");

		}
		expenseTrend.clickEAHLC(IA_MLC_MLC_HLC2,
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));

		int txnSize = expenseTrend.EIAMLCHeaderTxnCatList().size();
		int txncount = 0;

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("IA_MLC_MLC_Income2")));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_Income2"));
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
			Assert.assertTrue(txnSize == Integer.parseInt(IA_MLC_TxnList));

		} else {
			for (int i = 0; i < txnSize; i++) {
				System.out.println(expenseTrend.EIAMLCHeaderTxnCatList().get(i).getText());

				if (expenseTrend.EIAMLCHeaderTxnCatList().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("IA_MLC_MLC_Income1"))
						|| expenseTrend.EIAMLCHeaderTxnCatList().get(i).getText()
								.equals(PropsUtil.getDataPropertyValue("IA_MLC_MLC_Income2")))

				{
					txncount = txncount + 1;
				}
			}
			Assert.assertEquals(expenseTrend.EIAMLCTxnHeader().getText(),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_TxnHeader"));
			Assert.assertEquals(txncount, Integer.parseInt(IA_MLC_TxnList));
		}
	}

	@Test(description = "AT-93456:verify Back tp HLC", priority = 34) // , dependsOnMethods = "verifyMLCTxn_IA"
	public void verifyBackToHLC_IA() {
		SeleniumUtil.click(expenseTrend.EIAMLCDonutBack());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
				PropsUtil.getDataPropertyValue("IA_ExpenseByCategory"), "HLC Category Header is not displayed");
	}

	@Test(description = "AT-93715,AT-93727:past 6 month name should be displayed in MMMM formate", priority = 35, dependsOnMethods = "verifyBackToHLC_IA")
	public void verifyTrendPopUp_IA() {
		String IA_MLC_MLC_HLC1 = null;
		String IA_MLC_TrendPopUpHeader = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC1_43");
			IA_MLC_TrendPopUpHeader = PropsUtil.getDataPropertyValue("IA_MLC_TrendPopUpHeader_43");

		} else {

			IA_MLC_MLC_HLC1 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC1");
			IA_MLC_TrendPopUpHeader = PropsUtil.getDataPropertyValue("IA_MLC_TrendPopUpHeader");

		}
		expenseTrend.clickEAHLC(IA_MLC_MLC_HLC1);
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickTrendPopUp();
		Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(), IA_MLC_TrendPopUpHeader,
				"trend popup is not displayed");
	}

	@Test(description = "AT-93716,AT-93728:verify trend popup months", priority = 36) // , dependsOnMethods =
																						// "verifyTrendPopUp_IA"
	public void verifydefaultTrendMonth_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-93718,AT-93719,AT-93728:verify trend popup amount", priority = 37) // , dependsOnMethods =
																								// "verifyTrendPopUp_IA"
	public void verifydefaultTrendMonthAmt_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_MLC_TrendPopUpAmount"),
				"all 6 month income amount is not displayed");

	}

	@Test(description = "AT-93721,AT-93722,AT-93728:verify verify trend popup avg amount", priority = 38) // ,
																											// dependsOnMethods
																											// =
																											// "verifyTrendPopUp_IA"

	public void verifydefaultTrendMonthAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_MLC_TrendPopUpAvgMesg"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg should be displayed");
	}

	@Test(description = "AT-93716,AT-93719,AT-93728:verify trend popup x axix value", priority = 39) // ,
																										// dependsOnMethods
																										// =
																										// "verifyTrendPopUp_IA"
	public void verifydefaultTrendChartMonth_IA() {
		List<String> actualValue = new ArrayList<String>();
		actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
		List<String> expectedValue = new ArrayList<String>();
		expectedValue = expenseTrend
				.xAixValueWithoutSpace(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
				"verify all 6 month xaxis avalue is not displayed");
	}

	@Test(description = "AT-93724,AT-93725:verify trend popup close", priority = 40) // , dependsOnMethods =
																						// "verifyTrendPopUp_IA"
	public void verifyCloseTrendPopup_IA() {
		SeleniumUtil.click(expenseTrend.EIAMLCTrendPopUpClose());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(expenseTrend.EIAMLCTrendPopUpHeaderList().size() == Integer
				.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_TrendPopupHeaderSize")));
	}

	@Test(description = "AT-93462:verify MLC txn popup", priority = 41) // , dependsOnMethods = "verifyMLCHeader_IA"
	public void verifyMLCTxnPopup_IA() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		String IA_MLC_MLC_HLC3 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MLC_MLC_HLC3 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC3_43");

		} else {

			IA_MLC_MLC_HLC3 = PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC3");

		}
		expenseTrend.clickEAHLC(IA_MLC_MLC_HLC3,
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("IA_MLC_MLC1")));
		SeleniumUtil.waitForPageToLoad();
		int txnSize = expenseTrend.EIAMLCTxnPopUpCatList().size();
		int txncount = 0;

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAMLCTxnPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_TxnPopUpHeader_mob"));
			Assert.assertTrue(txncount == Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MLC_PopupTxnList")));

		} else {
			for (int i = 0; i < txnSize; i++) {

				if (expenseTrend.EIAMLCTxnPopUpCatList().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("IA_MLC_MLC1")))

				{
					txncount = txncount + 1;
				}
			}
			Assert.assertTrue(txncount == Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MLC_PopupTxnList")));
			Assert.assertEquals(expenseTrend.EIAMLCTxnPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_TxnPopUpHeader"));
		}
	}

	@Test(description = "AT-93459:verify subcategory ", priority = 42, dependsOnMethods = "verifyMLCTxnPopup_IA")
	public void verifySubCategory_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			expenseTrend.updateTxn(PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeories1").split(","),
					PropsUtil.getDataPropertyValue("IA_MLC_Descriptions1").split(","),
					PropsUtil.getDataPropertyValue("IA_MLC_Amounts1").split("/"),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC1"));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("IA_MLC_MLC1")));
			SeleniumUtil.waitForPageToLoad();
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

				util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAMLCSubCatgList1()),
						PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeoriesList1"), "subcategory is not displayed");

			} else {

				util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAMLCSubCatgList1()),
						PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeoriesList1"), "subcategory is not displayed");

			}
		}
	}

	@Test(description = "AT-93460,AT-93461:verify subcategory amount", priority = 43) // , dependsOnMethods =
																						// "verifyMLCTxnPopup_IA"
	public void verifySubCategoryper_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

				util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCSubCatgAmountList1()),
						PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeoriesAmountList"),
						"subcategory amount is not displayed");
				Assert.assertEquals(expenseTrend.EIAMLCCatAmountList().get(3).getText(),
						PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeoriesTotalAmount"), "");

			} else {

				util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCSubCatgAmountList1()),
						PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeoriesAmountList"),
						"subcategory amount is not displayed");
				Assert.assertEquals(expenseTrend.EIAMLCCatAmountList().get(2).getText(),
						PropsUtil.getDataPropertyValue("IA_MLC_SubCatgeoriesTotalAmount"), "");

			}
		}
	}

	@Test(description = "AT-93464:verify  time filter is displaying", priority = 44) // , dependsOnMethods =
																						// "verifyMLCTxnPopup_IA"
	public void verifySubCategoryTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			int txnSize = expenseTrend.EIAMLCTxnPopUpCatList().size();
			int txncount = 0;
			for (int i = 0; i < txnSize; i++) {

				if (expenseTrend.EIAMLCTxnPopUpCatList().get(i).getText()
						.equals(PropsUtil.getDataPropertyValue("IA_MLC_SubCatTXnCategories"))) {
					txncount = txncount + 1;
				}
			}
			Assert.assertEquals(
					txncount,Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_SubCatgeoryTxnSize")));
		}
	}

	@Test(description = "AT-93466:verify update txn category", priority = 45) // , dependsOnMethods =
																				// "verifySubCategoryTxn_IA"
	public void verifyChangeCategory_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(expenseTrend.EIAMLCDonutBack());
			SeleniumUtil.waitForPageToLoad();
			expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLC4"));
			SeleniumUtil.waitForPageToLoad();
			expenseTrend.changeCat(PropsUtil.getDataPropertyValue("IA_MLC_MLC4"),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC5"),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_UpdatedAmountList").split("/"));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
					PropsUtil.getDataPropertyValue("IA_MLC_MLC_HLCHeader"), "HLC Category Header is not displayed");
		}
	}

	@Test(description = "AT-93466:verify  changed catgeory updated ", priority = 46) // , dependsOnMethods =
																						// "verifyChangeCategory_IA"
	public void verifyChangeCategoryUpdated_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("IA_MLC_MLC5")));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(expenseTrend.EIAMLCTxnPopUpCatList().size(),Integer
					.parseInt(PropsUtil.getDataPropertyValue("IA_MLC_PopupTxnList")));
		}
	}

	@Test(description = "AT-93467AT-93469,AT-93471,AT-93472,AT-93475,AT-92261:verify adjustment popup", priority = 47) // ,
																														// dependsOnMethods
																														// =
																														// "verifyMLCHeader_IA"
	public void verifyRefundPopup_IA() {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		expenseTrend.clickViewDetails();

		if (expenseTrend.isEIAcloseTransPopup()) {
			Assert.assertEquals(expenseTrend.EIAMLCTrendPopUpHeader().getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_Refund_PopUpHeader"),
					" adjustment popup header is not displayed");
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());

		} else {
			Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_Refund_PopUpHeader"),
					" adjustment popup header is not displayed");
			Assert.assertEquals(expenseTrend.EIABackToEIA().getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_Refund_BackToEA"), "back to HLC is not displayed");
			Assert.assertEquals(expenseTrend.EIARefundPrint().getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_Refund_Print"), "print option is not displayed");
		}
	}

	@Test(description = "AT-93473,AT-93474,AT-93481:verify adjustment txn", priority = 48) // , dependsOnMethods =
																							// "verifyRefundPopup_IA"
	public void verifyRefundTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(expenseTrend.EIARefundTXnCat()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_RefundTxnIndex"))));
			SeleniumUtil.waitForPageToLoad(2000);
			aggTxn.enterDecValue(PropsUtil.getDataPropertyValue("IA_HLC_Refund_TXnDescription"));
			SeleniumUtil.waitForPageToLoad(3000);
			Assert.assertEquals(
					expenseTrend.EIARefundTXnDescription()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_RefundTxnIndex"))).getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_Refund_TXnDescription"), "adjustment txn is not displayed");
		}
	}

	@Test(description = "AT-93482:verify refund catgeory filtered", priority = 49) // , dependsOnMethods =
																					// "verifyRefundPopup_IA"
	public void verifyRefundCatFilter_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			expenseTrend.clickEIABackToEIA();
			expenseTrend.clickEIAHLCCatFilterCatlable();
			int catsize = expenseTrend.EIAHLCCatCheckBox().size();
			for (int i = 0; i < catsize - 1; i++) {
				SeleniumUtil.click(expenseTrend.EIAHLCCatCheckBox().get(i));
			}
			expenseTrend.clickEIAHLCCatDone();
			SeleniumUtil.waitForPageToLoad();
			String selectedCat = expenseTrend.EIAHLCCatAllCategoryLable().getText();
			String refundAmt = expenseTrend.EIAHLCCatRefundAmount().getText();
			expenseTrend.clickViewDetails();

			Assert.assertEquals(selectedCat, PropsUtil.getDataPropertyValue("IA_HLC_5HLCIncluded"),
					" 9 HLC is not included");
			Assert.assertEquals(refundAmt, PropsUtil.getDataPropertyValue("IA_HLC_5HLCIncludedRefund"),
					" 9 HLC is not included");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIARefundTXnCat()),
					PropsUtil.getDataPropertyValue("IA_HLC_Refund_CategoryList"),
					"refund txn category is not displayed");

		}
	}

	@Test(description = "AT-93483:verify back to HLC ", priority = 50) // , dependsOnMethods =
																		// "verifyRefundCatFilter_IA"
	public void verifyRefundToHLC_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			expenseTrend.clickEIABackToEIA();
			Assert.assertEquals(expenseTrend.EIAHLCCatAllCategoryLable().getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_5HLCIncluded"), " 9 HLC is not  included");
			Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
					PropsUtil.getDataPropertyValue("IA_HLC_5HLCIncludedRefund"), " 9 HLC is not included");
		}
	}

	@Test(description = "AT-93484:verify ", priority = 51) // , dependsOnMethods = "verifyRefundToHLC_IA"
	public void verifyUpdateRefundTXn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			expenseTrend.clickViewDetails();
			expenseTrend.updateRefundTxn(PropsUtil.getDataPropertyValue("IA_HLC_Refund_Category"));
			String updtaedCat = expenseTrend.EIARefundTXnCat()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MLC_RefundTxnIndex"))).getText();
			// expenseTrend.clickEIABackToEIA();
			Assert.assertEquals(updtaedCat, PropsUtil.getDataPropertyValue("IA_HLC_Refund_Category"),
					"adjustment txn is not displayed");
		}
	}

	public void addManualAdjTxn() {
		String IA_MTCategory = null;
		String IA_MTCategory1 = null;
		String IA_MTCategory2 = null;
		String IA_MTCategory9 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MTCategory = PropsUtil.getDataPropertyValue("IA_MTCategory_43");
			IA_MTCategory1 = PropsUtil.getDataPropertyValue("IA_MTCategory1_43");
			IA_MTCategory2 = PropsUtil.getDataPropertyValue("IA_MTCategory2_43");
			IA_MTCategory9 = PropsUtil.getDataPropertyValue("IA_MTCategory9_43");
		} else {

			IA_MTCategory = PropsUtil.getDataPropertyValue("IA_MTCategory");
			IA_MTCategory1 = PropsUtil.getDataPropertyValue("IA_MTCategory1");
			IA_MTCategory2 = PropsUtil.getDataPropertyValue("IA_MTCategory2");
			IA_MTCategory9 = PropsUtil.getDataPropertyValue("IA_MTCategory9");

		}
		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount"),
				PropsUtil.getDataPropertyValue("IA_MTDescription"), PropsUtil.getDataPropertyValue("IA_MTTxnType"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts"), PropsUtil.getDataPropertyValue("IA_MTFrequency"), 0,
				IA_MTCategory, PropsUtil.getDataPropertyValue("IA_MTNote"),
				PropsUtil.getDataPropertyValue("IA_MTCheck"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount1"),
				PropsUtil.getDataPropertyValue("IA_MTDescription1"), PropsUtil.getDataPropertyValue("IA_MTTxnType1"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts1"), PropsUtil.getDataPropertyValue("IA_MTFrequency1"), 0,
				IA_MTCategory1, PropsUtil.getDataPropertyValue("IA_MTNote1"),
				PropsUtil.getDataPropertyValue("IA_MTCheck1"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount2"),
				PropsUtil.getDataPropertyValue("IA_MTDescription2"), PropsUtil.getDataPropertyValue("IA_MTTxnType2"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts2"), PropsUtil.getDataPropertyValue("IA_MTFrequency2"), 0,
				IA_MTCategory2, PropsUtil.getDataPropertyValue("IA_MTNote2"),
				PropsUtil.getDataPropertyValue("IA_MTCheck2"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount9"),
				PropsUtil.getDataPropertyValue("IA_MTDescription9"), PropsUtil.getDataPropertyValue("IA_MTTxnType9"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts9"), PropsUtil.getDataPropertyValue("IA_MTFrequency9"), 0,
				IA_MTCategory9, PropsUtil.getDataPropertyValue("IA_MTNote9"),
				PropsUtil.getDataPropertyValue("IA_MTCheck9"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount9"),
				PropsUtil.getDataPropertyValue("IA_MTDescription9"), PropsUtil.getDataPropertyValue("IA_MTTxnType9"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts9"), PropsUtil.getDataPropertyValue("IA_MTFrequency9"), 0,
				IA_MTCategory9, PropsUtil.getDataPropertyValue("IA_MTNote9"),
				PropsUtil.getDataPropertyValue("IA_MTCheck9"));
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
