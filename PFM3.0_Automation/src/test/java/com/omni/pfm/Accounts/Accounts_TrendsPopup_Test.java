/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.awt.AWTException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_TrendsPopup_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_GroupValidation_Test.class);
	Accounts_TrendsPopup_Loc trendsPopUp;
	AccountsLandingPage_Loc acntLandingPage;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		trendsPopUp = new Accounts_TrendsPopup_Loc(d);
		acntLandingPage = new AccountsLandingPage_Loc(d);
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-84304,AT-84305,AT-84309,AT-84341,AT-84307: Verify View Trends option in cash Container is available in Account Settings level", groups = {
			"Regression" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyViewTrendsInCashContainer() throws Exception {

		try {
			trendsPopUp.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		boolean cashContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d, "TESTDATA", "View Trend");
		Assert.assertTrue(cashContainerTrendsVisibility, "View Trend is not visible in cash container");

		SeleniumUtil.selectDropDown(d, "TESTDATA", "View Trend");
		Assert.assertEquals(trendsPopUp.popupBalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("BalanceLabelTxt").trim());
	}

	@Test(description = "AT-84326: Verify trend pop up account info.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "login", priority = 3, enabled = true)
	public void verifyTrendAcntInfo() throws InterruptedException {
		Assert.assertEquals(trendsPopUp.PopupAcntNameInfo().getText().trim(),
				PropsUtil.getDataPropertyValue("SiteName"));
	}

	@Test(description = "AT-84343,AT-84344:Verify chartview & tabular view in trend pop up.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyTrendAcntInfo", priority = 4, enabled = true)
	public void verifyChartTabularView() throws InterruptedException {

		Assert.assertTrue(trendsPopUp.TrendsChartViewIcon().isDisplayed());
		Assert.assertTrue(trendsPopUp.TrendsTableViewIcon().isDisplayed());
	}

	@Test(description = "AT-84346:Verify tabular view in trend pop up.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyChartTabularView", priority = 5, enabled = true)
	public void verifyPopUpTabularView() throws InterruptedException {

		trendsPopUp.clickingOnViewByTableBtn();
		trendsPopUp.verifyingTrendsPopupTableHeader("TableViewHeaderLabel");
	}

	@Test(description = "AT-84345,Verify left and value of tabular view", groups = {
			"DesktopBrowsers,sanity" }, priority = 6, dependsOnMethods = "verifyPopUpTabularView", enabled = true)

	public void verifyCalenderData() throws InterruptedException {
		trendsPopUp.assertDateOption();
	}

	@Test(description = "AT-84340:Verify trend pop up calender info.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyPopUpTabularView", priority = 7, enabled = true)
	public void verifyTrendCalenderInfo() throws InterruptedException {

		String expectedDate = trendsPopUp.getExpectedTrendDate();
		String actualDate = trendsPopUp.TrendsPopupNetworthDuration().getText().trim();

		Assert.assertEquals(actualDate.toLowerCase(), expectedDate.toLowerCase());
	}

	@Test(description = "AT-84328,AT-84327: Verify that Amount should be displayed at the Y-axis of the graph.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyChartTabularView", priority = 8, enabled = true)
	public void verifyPopUpYAxis() throws InterruptedException {

		trendsPopUp.clickingOnViewByChartBtn();
		trendsPopUp.verifyYAxis();
	}

	@Test(description = "AT-84348,AT-83927: Verify close icon in trend pop up.", groups = {
			"DesktopBrowsers,sanity" }, priority = 9, dependsOnMethods = "login", enabled = true)
	public void verifyPopUpCloseIconView() {
		// throws InterruptedException
		Assert.assertTrue(trendsPopUp.TrendsPopupCrossIcon().isDisplayed());

		trendsPopUp.clickingOnCrossIcon();
		Assert.assertTrue(acntLandingPage.AccountsPageHeader().isDisplayed());
	}

	@Test(description = "AT-84304,AT-84305,AT-84310,AT-84347: Verify View Trends option in card Container is available in Account Settings level", groups = {
			"Regression" }, priority = 10, dependsOnMethods = "login", enabled = true)
	public void verifyViewTrendsInCardContainer() throws Exception {

		boolean creditContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d, "Super CD Plus",
				"View Trend");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(creditContainerTrendsVisibility, "View Trend is not visible in credit container");

		SeleniumUtil.selectDropDown(d, "Super CD Plus", "View Trend");
		Assert.assertEquals(trendsPopUp.popupBalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("BalanceLabelTxt").trim());
	}

	@Test(description = "AT-84304,AT-84305,AT-84311,AT-84444,AT-84308: Verify View Trends option in investment Container is available in Account Settings level", groups = {
			"Regression" }, priority = 11, enabled = true)
	public void verifyViewTrendsIninvestmentContainer() throws Exception {
		trendsPopUp.clickingOnCrossIcon();
		boolean investmentContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d,
				"Investment Account 401k", "View Trend");
		Assert.assertTrue(investmentContainerTrendsVisibility, "View Trend is not visible in investment container");

		SeleniumUtil.selectDropDown(d, "Investment Account 401a", "View Trend");
		Assert.assertEquals(trendsPopUp.popupTotalBalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TotalBalnceText").trim());
	}

	@Test(description = "AT-84346:Verify tabular view in trend pop up.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyViewTrendsIninvestmentContainer", priority = 12, enabled = true)
	public void verifyPopUpTabularViewInInvestment() throws InterruptedException {

		trendsPopUp.clickingOnViewByTableBtn();
		trendsPopUp.verifyingTrendsPopupTableHeader("TableViewHeaderLabelInInv");
	}

	@Test(description = "AT-84304,AT-84305,AT-84313: Verify View Trends option in loan Container is available in Account Settings level", groups = {
			"Regression" }, priority = 13, dependsOnMethods = "verifyViewTrendsIninvestmentContainer", enabled = true)
	public void verifyViewTrendsInloanContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		trendsPopUp.clickingOnCrossIcon();
		boolean loanContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d, "LINE OF CREDIT",
				"View Trend");
		Assert.assertTrue(loanContainerTrendsVisibility, "View Trend is not visible in loan container");

		SeleniumUtil.selectDropDown(d, "LINE OF CREDIT", "View Trend");
		Assert.assertEquals(trendsPopUp.popupPrincipalBalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("LoanTotalBalnceText").trim());
	}

	@Test(description = "AT-84304,AT-84305,AT-84312: Verify View Trends option in insurance Container is available in Account Settings level", groups = {
			"Regression" }, priority = 14, dependsOnMethods = "verifyViewTrendsInloanContainer", enabled = true)
	public void verifyViewTrendsInInsuranceContainer() throws Exception {
		trendsPopUp.clickingOnCrossIcon();
		boolean InsuranceContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d, "DAG INSURANCE",
				"View Trend");
		Assert.assertTrue(InsuranceContainerTrendsVisibility, "View Trend is not visible in insurance container");
	}

	@Test(description = "Verify View Trends option in bill Container is available in Account Settings level", groups = {
			"Regression" }, priority = 15, dependsOnMethods = "verifyViewTrendsInInsuranceContainer", enabled = true)
	public void verifyGoToSiteInBillContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		boolean billContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "DAG BILLING", "View Trend");
		Assert.assertFalse(billContainerGoToSiteVisibility, "Go To site is visible in bill container");
	}

	@Test(description = "Verify View Trends option in rewards is available in Account Settings level", groups = {
			"Regression" }, priority = 16, dependsOnMethods = "verifyGoToSiteInBillContainer", enabled = true)
	public void verifyGoToInRewardsSite() throws Exception {
		boolean rewardsContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "YODLEE", "View Trend");
		Assert.assertFalse(rewardsContainerGoToSiteVisibility, "Go To site is not visible in rewards container");
	}

	@Test(description = "AT-84304,AT-84305,AT-84316: Verify View Trends option in manual Container is available in Account Settings level", groups = {
			"Regression" }, priority = 17, dependsOnMethods = "verifyGoToInRewardsSite", enabled = true)
	public void verifyViewTrendsInManualContainer() throws Exception {
		boolean ManualContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d, "MyAccountbank",
				"View Trend");
		Assert.assertTrue(ManualContainerTrendsVisibility, "View Trend is not visible in loan container");

		SeleniumUtil.selectDropDown(d, "MyAccountbank", "View Trend");
		Assert.assertEquals(trendsPopUp.popupBalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("BalanceLabelTxt").trim());
	}

	@Test(description = "AT-84304,AT-84305,AT-84315: Verify View Trends option in manual Container is available in Account Settings level", groups = {
			"Regression" }, priority = 18, dependsOnMethods = "verifyViewTrendsInManualContainer", enabled = true)
	public void verifyViewTrendsInRealEstateContainer() throws Exception {
		trendsPopUp.clickingOnCrossIcon();
		boolean RSContainerTrendsVisibility = SeleniumUtil.selectDropDownVisibility(d, "RealEstateManual",
				"View Trend");
		Assert.assertTrue(RSContainerTrendsVisibility, "View Trend is not visible in real estate container");

		SeleniumUtil.selectDropDown(d, "RealEstateManual", "View Trend");
		Assert.assertEquals(trendsPopUp.popupBalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("BalanceLabelTxt").trim());
	}
}
