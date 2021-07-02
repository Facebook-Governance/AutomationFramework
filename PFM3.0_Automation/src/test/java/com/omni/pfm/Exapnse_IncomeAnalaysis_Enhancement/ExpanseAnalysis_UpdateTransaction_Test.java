/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan 
*/
package com.omni.pfm.Exapnse_IncomeAnalaysis_Enhancement;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
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

public class ExpanseAnalysis_UpdateTransaction_Test extends TestBase {

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

	@Test(description = "Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void login() throws Exception {
		
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
		accAddition.linkAccount();
		Assert.assertTrue(
				accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_TransactionUpdate_DagSite"),
						PropsUtil.getDataPropertyValue("EIA_TransactionUpdate_DagSite_Password")));
	}
	
	@Test(description = "AT-120428,AT-120429,AT-120434,AT-120433,AT-120435,AT-120431:Verify that after updating  Description field in Transaction details only that transaction container should be reloaded and updated ", priority = 1, dependsOnMethods = "login")
	public void updateTransDescTagsNoteAndSplit() throws Exception {
		
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.FTUE();
		
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(expenseTrend.transMonth_EIA());
		SeleniumUtil.click(expenseTrend.firstCateg_EIA());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.firstTransDetails_EIA());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(expenseTrend.showMoreOptionsBtn_EIA());
		SeleniumUtil.click(expenseTrend.splitTransLink_EIA());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.splitTranSaveBtn_EIA());

		SeleniumUtil.click(expenseTrend.firstTransDetails_EIA());
		expenseTrend.updateTransDesc_EIA().clear();
		expenseTrend.updateTransDesc_EIA().sendKeys("Mortgages1");

		SeleniumUtil.click(expenseTrend.updateTransTag_EIA());
		expenseTrend.tagNameInputSearch_EIA().sendKeys("business");
		SeleniumUtil.click(expenseTrend.dropdownTagname_EIA());
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(expenseTrend.popUpBackBtn_EIA_Mob());

		}

		SeleniumUtil.click(expenseTrend.showMoreOptionsBtn_EIA());
		expenseTrend.updateTransNote_EIA().clear();
		expenseTrend.updateTransNote_EIA().sendKeys("My Transaction History Details");

		SeleniumUtil.click(expenseTrend.saveButton_EIA());

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(expenseTrend.popUpCloseBtn_EIA_Mob());
			SeleniumUtil.click(expenseTrend.firstTransDetails_EIA_Mob());
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(expenseTrend.updatedTransDesc_EIA_Mob().getText(),
					PropsUtil.getDataPropertyValue("EIA_FirstUpdatedTransDesc"),
					"Transaction Description is not updated");
		} else {
			Assert.assertEquals(expenseTrend.firstTransDetails_EIA().getText(),
					PropsUtil.getDataPropertyValue("EIA_FirstUpdatedTransDesc"),
					"Transaction Description is not updated");
		}

		Assert.assertTrue(expenseTrend.addedTagIcon_EIA().isDisplayed(), "Transaction Tag is not updated");
		Assert.assertTrue(expenseTrend.splitTranIcon_EIA().isDisplayed(), "Split Transaction Icon is not displayed");
		Assert.assertTrue(expenseTrend.pieChartNoteIcon_EIA().isDisplayed(), "Transaction Tag is not updated");
	}

	@Test(description = "AT-120430,AT-120439:Verify that after updating Category field in Transaction details that full page should be reloaded and updated ", priority = 2, dependsOnMethods = "login")
	public void updateTransCategory() throws Exception {

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(expenseTrend.firstTransDetails_EIA());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(expenseTrend.showMoreOptionsBtn_EIA());
			SeleniumUtil.click(expenseTrend.category_EIA());
			SeleniumUtil.click(expenseTrend.categoryDropDown_EIA());
			SeleniumUtil.click(expenseTrend.saveButton_EIA());
			Assert.assertEquals(expenseTrend.updatedTransCategory_EIA().getText(),
					PropsUtil.getDataPropertyValue("EIA_UpdatedCategTrans"), "Transaction Tag is not updated");

		}
	}

	@Test(description = "AT-120440,AT-120442,AT-120432,AT-120436,AT-120437:Verify when the user click on piechart,then updating Transaction Description/Tag/Attachment/Note, then details should be updated and  page should not be reloaded .", priority = 3, dependsOnMethods = "login")
	public void pieChartUpdateTransDescTagsNoteAndSplit() throws Exception {

		SeleniumUtil.click(expenseTrend.backToEA_EIA());
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.transMonth_EIA());
		SeleniumUtil.click(expenseTrend.secondCateg_EIA());
		SeleniumUtil.click(expenseTrend.pieChartFirstCategDetails_EIA());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.firstTransDetails_EIA());
		SeleniumUtil.click(expenseTrend.showMoreOptionsBtn_EIA());
		SeleniumUtil.click(expenseTrend.splitTransLink_EIA());
		SeleniumUtil.click(expenseTrend.splitTranSaveBtn_EIA());
		SeleniumUtil.click(expenseTrend.pieChartFirstCategDetails_EIA());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.firstTransDetails_EIA());
		expenseTrend.updateTransDesc_EIA().clear();
		expenseTrend.updateTransDesc_EIA().sendKeys("Mortgages2");

		SeleniumUtil.click(expenseTrend.updateTransTag_EIA());
		expenseTrend.tagNameInputSearch_EIA().sendKeys("business");

		SeleniumUtil.click(expenseTrend.dropdownTagname_EIA());
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(expenseTrend.popUpBackBtn_EIA_Mob());
		}

		SeleniumUtil.click(expenseTrend.showMoreOptionsBtn_EIA());
		expenseTrend.updateTransNote_EIA().clear();
		expenseTrend.updateTransNote_EIA().sendKeys("My Transaction History Details");

		SeleniumUtil.click(expenseTrend.saveButton_EIA());
		SeleniumUtil.waitForPageToLoad();

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(expenseTrend.pieChartFirstUpdatedTransIcon_EIA_Mob().getText(),
					PropsUtil.getDataPropertyValue("EIA_FirstPieChartUpdatedTransDesc"),
					"Transaction Description is not updated");

		} else {
			Assert.assertEquals(expenseTrend.pieChartFirstUpdatedTransDesc_EIA().getText(),
					PropsUtil.getDataPropertyValue("EIA_FirstPieChartUpdatedTransDesc"),
					"Transaction Description is not updated");
		}

		Assert.assertTrue(expenseTrend.pieUpdatedTagIcon_EIA().isDisplayed(), "Transaction Tag is not updated");
		Assert.assertTrue(expenseTrend.splitTranIcon_EIA().isDisplayed(), "Split Transaction Icon is not displayed");
		Assert.assertTrue(expenseTrend.pieChartNoteIcon_EIA().isDisplayed(), "Transaction Tag is not updated");
	}

	@Test(description = "AT-120441,AT-120438:Verify when the user click on piechart,then updating Transaction category then details should be updated and  page should  be reloaded .", priority = 4, dependsOnMethods = "login")
	public void pieChartUpdateTransCategory() throws Exception {

		SeleniumUtil.click(expenseTrend.pieChartFirstCategDetails_EIA());
		SeleniumUtil.click(expenseTrend.firstTransDetails_EIA());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.category_EIA());
		SeleniumUtil.click(expenseTrend.categoryDropDown_EIA());
		SeleniumUtil.click(expenseTrend.saveButton_EIA());
		expenseTrend.navigateToEA();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.transMonth_EIA());
		SeleniumUtil.click(expenseTrend.firstCateg_EIA());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.pieUpdatedTransCategory_EIA().getText(),
				PropsUtil.getDataPropertyValue("EIA_UpdatedCategTrans"), "Transaction Category is not updated");

	}

}