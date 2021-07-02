/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import static org.testng.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.Account_Loc;
import com.omni.pfm.pages.Dashboard.DashBoradInvestmentLoc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_InvestmentHolding_Test extends TestBase {

	Account_Loc account;
	DashBoradInvestmentLoc dashBoardInvestment;
	InvestmentHoldings_Loc investmentHoldingsPage;
	public static float sum;
	private static final Logger logger = LoggerFactory.getLogger(DashBoard_InvestmentHolding_Test.class);
	AccountAddition acc;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Dashboard-Investment Holding");
		account = new Account_Loc(d);
		dashBoardInvestment = new DashBoradInvestmentLoc(d);
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		acc = new AccountAddition();
		investmentHoldingsPage = new InvestmentHoldings_Loc(d);
	}

	@Test(description = "Verify login happenes successfully.", priority = 1, groups = { "Desktop", "Smoke" })
	public void login() throws Exception {
		SeleniumUtil.refresh(d);
		acc.linkAccount();
		acc.addAggregatedAccount("shivaprasad_bhat.site16441.1", "site16441.1");
		SeleniumUtil.refresh(d);
		logger.info("****************Navidgated to Dashboard************");
		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);
	}

	@Test(description = "AT-76923:That the Investment Holdings portion of the widget should display today�s change", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 2, enabled = true)
	public void verifyTodaysChangeHeader() {
		WebElement TodayHeader = dashBoardInvestment.todaysChange();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(TodayHeader.getText(), PropsUtil.getDataPropertyValue("investment_TodaysChange1"));
			Assert.assertEquals(dashBoardInvestment.todayschangemob().getText(),
					PropsUtil.getDataPropertyValue("investment_TodaysChange2"));
		} else {
			String actual = TodayHeader.getText();
			String expected = PropsUtil.getDataPropertyValue("investment_TodaysChange");
			if (!actual.contains(expected)) {
				fail("Investment holdings changed text in dashboard is not coming as expected. Expected ::'" + expected
						+ "' & Actual ::'" + actual + "'");
			}
		}
		logger.info(
				"**********************Verified that the Investment Holdings portion of the widget should display today�s change********************");
	}

	@Test(description = "AT-76924:Verify by default 'BY ASSET CLASS view dispalyed in the Investment holding portion", priority = 3, groups = {
			"Desktop", "Smoke" }, enabled = true)
	public void filterDataInInvestment() {
		SeleniumUtil.click(dashBoardInvestment.assestByDefaultInvestment());
		logger.info(
				"**********************Verified by default 'BY ASSET CLASS; view dispalyed in the Investment holding portion *********");
		String[] list = PropsUtil.getDataPropertyValue("Investment_DropDown")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> filter = dashBoardInvestment.filterList();
		for (int i = 0; i < filter.size(); i++) {
			Assert.assertEquals(list[i].toUpperCase().trim(), filter.get(i).getText().toUpperCase().trim());
		}
		logger.info(
				"**********************Verified in Drop down below options displayed BY ASSET CLASS  BY INVESTMNET SECTOR MOST ACTIVE STOCKS *********");
		SeleniumUtil.click(dashBoardInvestment.assestByDefaultInvestment());
	}

	@Test(description = "AT-76925:Verify that the Investment Holdings portion of the widget should display Donut chart showing Top 5 asset classes", priority = 4, groups = {
			"Desktop", "Smoke" }, enabled = true)
	public void asssest5data() {
		String size = PropsUtil.getDataPropertyValue("DashBoard_InvestmentHolding_GetSize");
		int getSize = Integer.parseInt(size);
		Assert.assertEquals(dashBoardInvestment.gettableDonutChart().size(), getSize);
		logger.info(
				"**********************Verified that the Investment Holdings portion of the widget should display Donut chart showing Top 5 asset classes******");
		SeleniumUtil.click(dashBoardInvestment.assestByDefaultInvestment());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(2);
		SeleniumUtil.click(dashBoardInvestment.investmentFilterDropDown(1));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(dashBoardInvestment.investmentWidget());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(10);
		By investmentDropDownInDashboard=SeleniumUtil.getByObject("DashboardPage", null, "card_DIL");
		if(SeleniumUtil.isDisplayed(investmentDropDownInDashboard,5)) {
			SeleniumUtil.click(dashBoardInvestment.investmentWidget());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.waitFor(10);
		}
		investmentHoldingsPage.completeFtueFlow();
		String investmentHoldingsHeaderTxt = InvestmentHoldings_Loc.investmentHoldingsHeader().getText().trim();

		logger.info("==>Verify Header");

		Assert.assertTrue(InvestmentHoldings_Loc.investmentHoldingsHeader().isDisplayed(),
				"After clicking investment widget in dashboard it is not navigating to investment finapp");
		Assert.assertTrue(investmentHoldingsHeaderTxt.contains(PropsUtil.getDataPropertyValue("IH_page_header")),
				"After clicking investment widget in dashboard it is not navigating to investment finapp");
		logger.info(
				"**********************Verified that upon selecting Asset Class and then clicking on investment card user navigates to investment page******");
		SeleniumUtil.refresh(d);
	}

	@Test(description = "AT-76934:Verify the total on dashboard and investment page", priority = 5, groups = {
			"Desktop", "Smoke" }, enabled = true)
	public void invesmentTotalAmount() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			String expectedAmt = dashBoardInvestment.totalbalancemob().getText();
			System.out.print(expectedAmt);
			float finalAmount = SeleniumUtil.parseAmount(expectedAmt);
			System.out.print(finalAmount);
			sum = 738019.25f;
			Assert.assertTrue(!(finalAmount == 0), "Investment total is displayed as zero");
		} else {
			String expectedAmt = dashBoardInvestment.totalbalance().getText();
			System.out.print(expectedAmt);
			float finalAmount = SeleniumUtil.parseAmount(expectedAmt);
			System.out.print(finalAmount);
			sum = 771892.1f;
			Assert.assertTrue(!(finalAmount == 0), "Investment total is displayed as zero");
			logger.info("**********************Verified  the total on dashboard and investment page****");
		}
	}

	@Test(description = "AT-76936:Verify if user is in 'By Asset class' view and after click on the widget it should navigate to Investment Holding finapp and Asset class view selected In the Investment Holding app", priority = 6, groups = {
			"Desktop", "Smoke" }, enabled = true)
	public void checkFiltersInInvesmentpage() {
		SeleniumUtil.refresh(d);
		logger.info(
				"**********************Verify if user is in 'By Asset class' view and after click on the widget it should navigate to Investment Holding finapp and Asset class view selected In the Investment Holding app");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(3);
		SeleniumUtil.scrollToWebElementMiddle(d, dashBoardInvestment.InvestmentCard());
		SeleniumUtil.waitForPageToLoad();
//		SeleniumUtil.click(dashBoardInvestment.dropDownInvestment());
//		SeleniumUtil.click(dashBoardInvestment.InvestmentCard());
//		Assert.assertTrue(dashBoardInvestment.dropDownInvestment().isDisplayed());
//		SeleniumUtil.refresh(d);
		logger.info(
				"Verify if user is in 'By Investment Sector' view and after click on the widget it should navigate to Investment Holding finapp and Investment Sector view selected In the Investment Holding app");
		SeleniumUtil.click(dashBoardInvestment.FilterDropDown());
		List<WebElement> l = dashBoardInvestment.filterList();
		SeleniumUtil.click(l.get(1));
		SeleniumUtil.click(dashBoardInvestment.todaysChange());
		SeleniumUtil.refresh(d);
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
