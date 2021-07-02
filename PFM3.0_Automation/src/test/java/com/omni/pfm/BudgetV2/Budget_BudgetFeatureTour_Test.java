/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.BudgetV2;



import java.util.concurrent.TimeUnit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.omni.pfm.PageProcessor.PageParser;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;

import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BudgetFeatureTour_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetFeatureTour_Test.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;
	Add_Manual_Transaction_Loc addManualTransaction;
	Transaction_Filter_Loc filter;
	String userName = null;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget feature Tour");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		addManualTransaction = new Add_Manual_Transaction_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		util = new SeleniumUtil();

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		userName = L1NodeLogin.userName;
		System.out.println(userName);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data13_Site"),
				PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data13_Password"));
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));

	}

	@Test(description = "AT-110266: Verify Feature Tour Button", priority =1)
	public void verifyFeatureTour()  {
		SeleniumUtil.click(budgetSummary.budget_Summery_MoreBtn());
		
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTour().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_SummeryFeatureTour"), "time filter label is not displayed");
		
	}	
	@Test(description = "AT-110277,AT-110274,AT-110268: Header Text and next button", priority =2, dependsOnMethods = {"verifyFeatureTour" })
	public void clickOnFeatureTour()  {
		SeleniumUtil.click(budgetSummary.budget_Trend_FeatureTour());
		
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTourHeaderTxt().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_HeaderFeatureTour"), "time filter label is not displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTourTxt().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourTxt"), "time filter label is not displayed");
		
		Assert.assertTrue(budgetSummary.budget_Trend_CloseIcon().get(0).isDisplayed());
		Assert.assertTrue(budgetSummary.budget_Trend_NextButton().get(0).isDisplayed(), "Next button should not be displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_NextButton().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourNext"), "time filter label is not displayed");
		
		Assert.assertTrue(budgetSummary.budget_Trend_FeatureTourDots().isDisplayed(),"Bullet points is not displayed");
}
	
	@Test(description = "AT-110277AT-110273,AT-110272,AT-110270,AT-110269: Clicking on next button", priority =3,dependsOnMethods = {"clickOnFeatureTour" })
	public void clickOnFeatureTourNextBtn()  {
		SeleniumUtil.click(budgetSummary.budget_Trend_CloseIcon().get(0));
		
		SeleniumUtil.click(budgetSummary.budget_Summery_MoreBtn());
		
		SeleniumUtil.click(budgetSummary.budget_Trend_FeatureTour());
		
		SeleniumUtil.click(budgetSummary.budget_Trend_NextButton().get(0));
		
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTourHeaderTxt().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_HeaderFeatureTour1"), "time filter label is not displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTourTxt().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourTxt1"), "time filter label is not displayed");
		
		Assert.assertTrue(budgetSummary.budget_Trend_CloseIcon().get(1).isDisplayed());
		Assert.assertTrue(budgetSummary.budget_Trend_NextButton().get(1).isDisplayed(), "Next button should not be displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_NextButton().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourNext"), "time filter label is not displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_BackButton().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourBack"), "time filter label is not displayed");
		
		Assert.assertTrue(budgetSummary.budget_Trend_FeatureTourDots1().isDisplayed(),"Bullet points is not displayed");

		
}
	
	
	@Test(description = "AT-110277,AT-110275,AT-110271: Header Text and next button", priority =4,dependsOnMethods = {"clickOnFeatureTourNextBtn" })
	public void clickOnStep2Next()  {
		SeleniumUtil.click(budgetSummary.budget_Trend_NextButton().get(1));
		
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTourHeaderStep2Txt().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Header2FeatureTour"), "time filter label is not displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_FeatureTourTxt2().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourTxt2"), "time filter label is not displayed");
		
		Assert.assertTrue(budgetSummary.budget_Trend_CloseIcon().get(2).isDisplayed());
		Assert.assertTrue(budgetSummary.budget_Trend_NextButton().get(2).isDisplayed(), "Next button should not be displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_NextButton().get(2).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourGotIt"), "time filter label is not displayed");
		
		Assert.assertEquals(budgetSummary.budget_Trend_BackButton().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_FeatureTourBack"), "time filter label is not displayed");
		
		Assert.assertTrue(budgetSummary.budget_Trend_FeatureTourDots2().isDisplayed(),"Bullet points is not displayed");

		
}
	
	@Test(description = "AT-110276: Header Text and next button", priority =5,dependsOnMethods = {"clickOnStep2Next" })
	public void clickOnStep2Back()  {
		SeleniumUtil.click(budgetSummary.budget_Trend_BackButton().get(1));
		
		SeleniumUtil.click(budgetSummary.budget_Trend_NextButton().get(1));
		
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budgetSummary.budget_Trend_CloseIcon().get(2));
}
}