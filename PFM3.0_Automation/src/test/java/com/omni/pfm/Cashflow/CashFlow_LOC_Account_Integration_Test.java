/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad Bhat
 ******************************************************************************/
package com.omni.pfm.Cashflow;



import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.CashFlow.*;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class CashFlow_LOC_Account_Integration_Test extends TestBase {

	static Logger logger = LoggerFactory.getLogger(CashFlow_LOC_Account_Integration_Test.class);

	CashFlow_LandingPage_Loc LandingPage;
	AccountAddition accAddition;
    SeleniumUtil util;
    Accounts_ViewByGroup_Loc groupView;
	@BeforeTest(alwaysRun = true)
	public void testInit() throws Exception 
	{
		doInitialization("Cashflow Landing Page Test");
		LandingPage = new CashFlow_LandingPage_Loc(d);
		accAddition = new AccountAddition();
		util=new SeleniumUtil();
		groupView=new Accounts_ViewByGroup_Loc(d);
		LoginPage.loginMain(d, TestBase.loginParameter);			
		SeleniumUtil.waitForPageToLoad();
			
		logger.info("==> Adding account without any transactions!");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite1_CF"),
		PropsUtil.getDataPropertyValue("dagPassword1_CF")));
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);

	}
	@Test(description = "AT-97598:verify Chart cashflow when select all account", priority = 1)
	public void verifyCashFLowValidAccounts() throws Exception {	
		logger.info("all cas flow valid account hsould be displayed");
		LandingPage.clickaccountDropDown();
		util.assertExpectedActualList(util.getWebElementsValue(LandingPage.CFAccountList()), PropsUtil.getDataPropertyValue("CF_LOC_CFValidAccounts"), "all valid accounts are not displayed");
	}
	@Test(description = "AT-97598:verify Chart cashflow when select all account", priority = 2,dependsOnMethods="verifyCashFLowValidAccounts")
	public void verifyChartTotalCF_LOC() throws Exception {		
		logger.info("All account option should be selected n Account dropdown");
		
			SeleniumUtil.click(LandingPage.CFAllAccountCheckBox());
			LandingPage.selectaccount(PropsUtil.getDataPropertyValue("CF_SelectLOC_Account"));
			Assert.assertEquals(LandingPage.CFnetCashflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_LOCAccount_ChartNetCFValue"), 
					"net casahflow data is not displayed");
			
			Assert.assertEquals(LandingPage.CFinflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_LOCAccount_ChartInCFValue"), 
					"cash inflow data is not displayed");
			
			Assert.assertEquals(LandingPage.CFoutflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_LOCAccount_ChartOutCFValue"), 
					"cash outflow data is not displayed");
		
	}
	@Test(description = "AT-97598:verify summery average and summery details", priority = 3,dependsOnMethods="verifyChartTotalCF_LOC")
	public void verifySummeryAndDetailsCF_LOC() throws Exception {		
		logger.info(" select loc account summery average and details sould be dispalyed");
	util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryAverage()), PropsUtil.getDataPropertyValue("CF_LOCAccount_CFSummery"),
			"summery average is not displayed");
	util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryDetails()), PropsUtil.getDataPropertyValue("CF_LOCAccount_CFDetails"),
			"summery details is not displayed");
	}
	@Test(description = "AT-97598:verify table cashflow details", priority = 4,dependsOnMethods="verifyChartTotalCF_LOC")
	public void verifyTableCF_LOC() throws Exception{	
		logger.info("Selected Loc account table cash flow details should be dispalyed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_LOCAccount_CFTableInFlow"),
				"cashflow table inflow data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_LOCAccount_CFTableOutFlow"),
				"cashflow table outflow data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_LOCAccount_CFTableNetTransferFlow"),
				"cashflow table net transfer data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCAccount_CFTableNetFlow"),
				"cashflow table total netflow is not displayed");
	}
	
	@Test(description = "AT-97598:verify Chart cashflow when select all account", priority = 5,dependsOnMethods="verifyChartTotalCF_LOC")
	public void verifyChartTotalCF_HELOC() throws Exception {		
		logger.info("selected HELOC account option should be selected n Account dropdown");
		
		LandingPage.selectaccount(PropsUtil.getDataPropertyValue("CF_SelectHELOC_Account"));
			Assert.assertEquals(LandingPage.CFnetCashflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_HELOCAccount_ChartNetCFValue"), 
					"net casahflow data is not displayed");
			
			Assert.assertEquals(LandingPage.CFinflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_HELOCAccount_ChartInCFValue"), 
					"cash inflow data is not displayed");
			
			Assert.assertEquals(LandingPage.CFoutflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_HELOCAccount_ChartOutCFValue"), 
					"cash outflow data is not displayed");
		
	}
	@Test(description = "AT-97598:verify summery average and summery details", priority = 6,dependsOnMethods="verifyChartTotalCF_HELOC")
	public void verifySummeryAndDetailsCFHELOC() throws Exception {		
		logger.info("Selected HELOC Account average summery details should be displayed");
	util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryAverage()), PropsUtil.getDataPropertyValue("CF_HELOCAccount_CFSummery"),
			"summery average is not displayed");
	util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryDetails()), PropsUtil.getDataPropertyValue("CF_HELOCAccount_CFDetails"),
			"summery details is not displayed");
	}
	@Test(description = "AT-97598:verify table cashflow details", priority = 7,dependsOnMethods="verifyChartTotalCF_HELOC")
	public void verifyTableCF_HELOC() throws Exception {		
		logger.info("Selected Account table cashflow details should be dispalyed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_HELOCAccount_CFTableInFlow"),
				"cashflow table inflow data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_HELOCAccount_CFTableOutFlow"),
				"cashflow table outflow data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_HELOCAccount_CFTableNetTransferFlow"),
				"cashflow table net transfer data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_HELOCAccount_CFTableNetFlow"),
				"cashflow table total netflow is not displayed");
	}
	
	@Test(description = "AT-97598:verify Chart cashflow when select all account", priority = 8,dependsOnMethods="verifyChartTotalCF_HELOC")
	public void verifyChartTotalCF_SBLOC() throws Exception {		
		logger.info("Selected SBLOC account option should be selected n Account dropdown");
		
		LandingPage.selectaccount(PropsUtil.getDataPropertyValue("CF_SelectSBLOC_Account"));
			Assert.assertEquals(LandingPage.CFnetCashflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_SBLOCAccount_ChartNetCFValue"), 
					"net casahflow data is not displayed");
			
			Assert.assertEquals(LandingPage.CFinflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_SBLOCAccount_ChartInCFValue"), 
					"cash inflow data is not displayed");
			
			Assert.assertEquals(LandingPage.CFoutflowData().getText().trim(), PropsUtil.getDataPropertyValue("CF_SBLOCAccount_ChartOutCFValue"), 
					"cash outflow data is not displayed");
		
	}
	@Test(description = "AT-97598:verify summery average and summery details", priority = 9,dependsOnMethods="verifyChartTotalCF_SBLOC")
	public void verifySummeryAndDetailsCF_SBLOC() throws Exception {		
		logger.info("Selected SBAccount cashFlow Summery average and details should be dispalyed ");
	util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryAverage()), PropsUtil.getDataPropertyValue("CF_SBLOCAccount_CFSummery"),
			"summery average is not displayed");
	util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryDetails()), PropsUtil.getDataPropertyValue("CF_SBLOCAccount_CFDetails"),
			"summery details is not displayed");
	}
	@Test(description = "AT-97598:verify table cashflow details", priority = 10,dependsOnMethods="verifyChartTotalCF_SBLOC")
	public void verifyTableCF_SBLOC() throws Exception {	
		logger.info("Selected SBLOC account cashflow Table details should be dispalyed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_SBLOCAccount_CFTableInFlow"),
				"cashflow table inflow data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_SBLOCAccount_CFTableOutFlow"),
				"cashflow table outflow data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))), 
				PropsUtil.getDataPropertyValue("CF_SBLOCAccount_CFTableNetTransferFlow"),
				"cashflow table net transfer data is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_SBLOCAccount_CFTableNetFlow"),
				"cashflow table total netflow is not displayed");
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
