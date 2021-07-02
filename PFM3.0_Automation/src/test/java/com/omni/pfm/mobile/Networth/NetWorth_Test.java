/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.mobile.Networth;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.List;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountAddition.AccountAdditionMobile;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML1;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class NetWorth_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(NetWorth_Test.class);
	NetWorth_Loc netWorth = null;
	AccountAddition accAddition = new AccountAddition();
	AccountAdditionMobile accountAdditionMobile;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Networth");
		netWorth = new NetWorth_Loc(d);
		logger.info("Initializing");
		accountAdditionMobile = new AccountAdditionMobile();
	}

	@Test(description = "Login", priority = 0)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
	}

	@Test(description = "DagSite Account Addition", priority = 1)
	public void accountAddition() throws Exception {
		accountAdditionMobile.linkAccount();
		Assert.assertTrue(accountAdditionMobile.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword")));
	}

	@Test(description = "NETWOR_01_01:Verify first time coach mark details.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 2, enabled = true)
	public void coachMarkVerification() throws Exception {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(netWorth.welcomeCoachMarkHeading().isDisplayed());
		logger.info("Verified the Welcome coach mark when user access the Net Worth finapp for the first time ");

		String heading = netWorth.welcomeCoachMarkHeading().getText();
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("NW_Welcome_CM_Heading"));
		logger.info("Verified the heading Welcome coach mark ");

		String text = netWorth.welcomeCoachMarkHeadingTxt().getText();
		logger.info("Verified the text of Welcome coach mark");

		if (PropsUtil.getDataPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {
			Assert.assertEquals(text, PropsUtil.getDataPropertyValue("NW_Welcome_CM_TxtWeb"));
		} else {
			Assert.assertEquals(text, PropsUtil.getDataPropertyValue("NW_Welcome_CM_TxtMobile"));
		}
	}

	@Test(description = "NETWOR_01_02:Verify bullets in Coach mark", priority = 3, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyBulletsINCM() {
		int size = netWorth.bulletInCoachMark().size();
		Assert.assertEquals(2, size);
		logger.info("Verified that there are two navigation bullets below the CONTINUE button");

		logger.info("Welcome coackmark-Verifying that both navigation bullets do not appear full");
		int grey = 0;
		int blue = 0;
		List<WebElement> s = netWorth.bulletInCoachMark();
		for (int i = 0; i < s.size(); i++) {
			String className = s.get(i).getAttribute("class");
			if (className.contains(PropsUtil.getDataPropertyValue("netWorthBlue"))) {
				blue = blue + 1;
			} else if (className.contains(PropsUtil.getDataPropertyValue("netWorthGrey"))) {
				grey = grey + 1;
			}
		}
		Assert.assertTrue(blue == 1 && grey == 1);
	}

	@Test(description = "NETWOR_01_03:Verify first time link account coach mark.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 4, enabled = true)
	public void verifyLinkAccountWelcomeCM() throws IndexOutOfBoundsException {
		logger.info(
				"Verifying that clicking on CONTINUE button in Welcome coachmark takes user to Link Accounts coackmark");
		Assert.assertTrue(netWorth.continueButton_NW().isDisplayed());
		SeleniumUtil.click(netWorth.continueButton_NW());
		logger.info(
				"Verified that clicking on CONTINUE button in Welcome coachmark takes user to Link Accounts coackmark");

		SeleniumUtil.waitForPageToLoad();
		String text2 = netWorth.linkAccountWelcomeCM().getText();
		Assert.assertEquals(text2, PropsUtil.getDataPropertyValue("Link_Account_Welcome_CM_Heading"));
		logger.info("Verified the Link Accounts coach mark heading text");

		String heading = netWorth.getNWWelcomeCMHeading().getText();
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("Link_Account_Welcome_CM_Description"));
		logger.info("Verified the Link Accounts coach mark description text");

		String heading1 = netWorth.getNWWelcomeCMHeading1().getText();
		Assert.assertEquals(heading1, PropsUtil.getDataPropertyValue("Link_Account_Welcome_CM_Description1"));

		Assert.assertTrue(netWorth.linkAccountLink().isDisplayed());
		logger.info(
				"Verified that Link Accounts coach mark has a pointer to Link Account button in Net Worth screen behind the coachmark");

		SeleniumUtil.click(netWorth.seeMyNetWorthButton());
		logger.info(
				"Verified that clicking on  SEE MY NET WORTH button takes user to NETWORTH screen and the coachmark disappears");
	}

	@Test(description = "NETWOR_01_04:Verify the variuos Texts on UI.Verify that the header of the Net Worth screen has the mentioned components in the same order", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 5, enabled = true)
	public void verifyUITexts() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		String appHeading = netWorth.headingText().getText();
		Assert.assertEquals(appHeading, PropsUtil.getDataPropertyValue("NetworthHeader"));

		Assert.assertTrue(netWorth.linkAccButton().isDisplayed());
		logger.info("Verified that Link Account button is displayed on the NetWorth");
	}

	@Test(description = "NETWOR_01_05:Verify the Default date range is 12 months.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 6, enabled = true)
	public void defaultDateRangeVerification() throws Exception {
		netWorth.forceNavigateToNetWorth();
		Assert.assertTrue(netWorth.verifyChartTitle(PropsUtil.getDataPropertyValue("netWorthDefaultDateRange"))); // 12
																													// Months
		logger.info("Verified that by default Net Worth chart shows 12 month's data");
	}

	@Test(description = "NETWOR_01_06:Verify the functionality of info Coach Mark functionality.", groups = {
			"DesktopBrowsers", "Smoke", "infoCMVerification" }, priority = 7, enabled = true)
	public void infoCMVerification() throws Exception {
		// netWorth.forceNavigateToNetWorth();
		Assert.assertTrue(netWorth.moreIcon().isDisplayed());
		SeleniumUtil.click(netWorth.moreIcon());
		logger.info("Verifying that clicking on the info icon launches Link All Your Accounts coachmark");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.infoCMButton());

		Assert.assertTrue(netWorth.infoCoachMarks().isDisplayed());
		String heading = netWorth.infoCoachMarks().getText().trim();
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthLinkAllYourAcc").trim());
		Assert.assertTrue(netWorth.cmCloseButton(1).isDisplayed());
		logger.info("Verified that Link All Your Accounts coachmark components.");
	}

	@Test(description = "NETWOR_01_07:Verify the functionality of interactive Coach Mark.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 8, enabled = true) // ,dependsOnGroups={"infoCMVerification"}
	public void interactiveCMVerification() throws Exception {
		netWorth.clickCMNextButton(1);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verifying the Interactive Chart info coach mark components ");

		String heading = netWorth.getCMHeadingText(2);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthInteractive").trim());
		Assert.assertTrue(netWorth.cmCloseButton(2).isDisplayed());

		String text = netWorth.getCMBodyContent(2);
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("netWorthThisChartText").trim());
		logger.info(
				"Verified that clicking anywhere on Next button in Link Account info coach mark opens the Interactive Chart coach mark.");
		SeleniumUtil.waitForPageToLoad();

		netWorth.clickCMBackButton(1);
		SeleniumUtil.waitForPageToLoad();
		heading = netWorth.getCMHeadingText(1);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthLinkAllYourAcc").trim());
		logger.info(
				"Verified that clicking on Back button from Interactive Chart coach mark takes user to Link Accounts coach mark pointing to link accounts button or icon");
	}

	@Test(description = "NETWOR_01_08:Verify the functionality of net worth Coach Mark functionality.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 9, enabled = true) // ,dependsOnGroups={"infoCMVerification"}
	public void nwDetailCMVerification() throws Exception {

		netWorth.clickCMNextButton(1);
		SeleniumUtil.waitForPageToLoad();
		netWorth.clickCMNextButton(2);
		SeleniumUtil.waitForPageToLoad();

		String heading = netWorth.getCMHeadingText(3);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthDetails"));
		Assert.assertTrue(netWorth.cmCloseButton(3).isDisplayed());

		String text = netWorth.getCMBodyContent(3);
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("netWorthTextOfThistable"));

		SeleniumUtil.waitForPageToLoad();
		netWorth.clickCMBackButton(2);
		SeleniumUtil.waitForPageToLoad();
		heading = netWorth.getCMHeadingText(2);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthInteractive"));
		netWorth.clickCMNextButton(2);
		SeleniumUtil.waitForPageToLoad();

		netWorth.clickCMNextButton(3);

	}

	@Test(description = "NETWOR_01_09:Verify that date shown in chart is in sync with date from filter drop down.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 10, enabled = true)
	public void timeFilterDataVerification() throws Exception {
		boolean status = true;
		for (int i = 1; i <= 6; i++) {
			SeleniumUtil.waitForPageToLoad();
			String text = "";
			if (i != 6) {
				text = netWorth.selectAndGetValueFromTimeFilter(i);
				Assert.assertTrue(netWorth.verifyChartTitle(text));
				logger.info("The text is:::: " + text);
				text = text.toUpperCase();
				logger.info("The text is " + text);

				if (text.equalsIgnoreCase(PropsUtil.getDataPropertyValue("Nw_ThisYearDuration"))) {
					int value = netWorth.getMonthValue();
					logger.info("Current Month Value is " + value);
					text = String.valueOf(value);
					text = text + " MONTHS";
					logger.info("Change in Month Value for This Year is " + text);
				}

				String changeInMonth = netWorth.changeText().getText().trim();
				logger.info("The changeInMonth is" + changeInMonth);

				if (changeInMonth.contains(text)) {
					status = true;
				} else {
					status = false;
				}
			} else {
				text = netWorth.selectAndGetValueFromTimeFilter(i);
				netWorth.clearCustomStartDate();
				netWorth.typeCustomStartDate(netWorth.targateDate1(-32));
				netWorth.clickUpdateButton();
				SeleniumUtil.waitForPageToLoad();
				status = netWorth.verifyChartTitle(text);
			}
		}
		Assert.assertTrue(status);
	}

	@Test(description = "NETWOR_01_10:Verify time range in time filter.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 11, enabled = true)
	public void timeFilterTimeRangeVerification() throws Exception {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		String actualDateRange[] = netWorth.getAllValuesFromTimeFilter();
		String expectedDateRange[] = PropsUtil.getDataPropertyValue("NW_Duration_Filter_range").trim().split(",");
		Assert.assertEquals(actualDateRange, expectedDateRange);
	}

	@Test(description = "NETWOR_01_11:Verify the Containers types in Liability.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 12, enabled = true)
	public void verifyContainersForLiabilities() throws Exception {

		netWorth.forceNavigateToNetWorth();
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(netWorth.allAccountsDD());
		}

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandLiabilitiesToViewAcc());
		String container[] = netWorth.getAllContainerInLiabilities();
		String expectedContainerInLiability[] = PropsUtil.getDataPropertyValue("expected_liabilities_container")
				.split(",");
		Assert.assertEquals(container, expectedContainerInLiability);

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.closeLiabilitiesToViewAcc());

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(netWorth.allAccountsDD());
		}
	}

	@Test(description = "NETWOR_01_12:Verify the Containers types in Assets.Verify that networth supports Bank, Investment, Insurance & Real Estate containers for assets", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 13, enabled = true)
	public void verifyContainersForAssets() throws Exception {
		netWorth.forceNavigateToNetWorth();
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(netWorth.allAccountsDD());
		}

		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		String container[] = netWorth.getAllContainerInAsset();
		String expectedContainerInAssets[] = PropsUtil.getDataPropertyValue("expected_asset_container").split(",");
		;
		SeleniumUtil.click(netWorth.closeAssetsToViewAcc());
		Assert.assertEquals(container, expectedContainerInAssets);
	}

	@Test(description = "NETWOR_01_13:Verify the headings in Net Worth Summary Details.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 14, enabled = true)
	public void nwSummaryVerification() throws Exception {
		// 1. Net Worth details should lie below the chart
		// 2. Details section should have BALANCE, CHANGE AMOUNT AND CHANGE % as
		// the column names in the same order.
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		netWorth.verifyNWDetailTitle(PropsUtil.getDataPropertyValue("netWorthDetails"));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "NETWOR_01_14:Verify the assests and liabilites amount in NW summary section.", groups = {
			"DesktopBrowsers" }, priority = 15, enabled = true)
	public void nwAmountVerification() throws Exception {
		netWorth.forceNavigateToNetWorth();
		double asset = netWorth.getAmountInSummarySection(PropsUtil.getDataPropertyValue("netWorthAssets"));
		logger.info("Asset Value is " + asset);
		double liability = netWorth.getAmountInSummarySection(PropsUtil.getDataPropertyValue("netWorthLiabilities"));
		logger.info("Liability Value is " + liability);
		double networth = asset - liability;
		logger.info("Net Worth Value is " + networth);

		netWorth.assertNWAssetAndLiabilityAmountForMobile(asset, liability, networth);
		logger.info("The Values of Asset, Liabilities and Networth are verified.");

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "NETWOR_01_15:Verify the headings in NW table view.", groups = {
			"DesktopBrowsers" }, priority = 16, enabled = true)
	public void verifyTableView() throws Exception {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(5000);
		netWorth.switchToTableView();
		SeleniumUtil.waitForPageToLoad(5000);

		String[] actualHeading = netWorth.getHeadingInTable();
		String expectedHeadings[] = PropsUtil.getDataPropertyValue("Headings_In_Table_View")
				.split(PropsUtil.getDataPropertyValue("netWorthTableViewSeperator"));
		Assert.assertEquals(actualHeading, expectedHeadings);
	}

	@Test(description = "NETWOR_01_16:Verify that total net worth is the sum of Assets-liabilities. ", groups = {
			"DesktopBrowsers" }, priority = 17, enabled = true)
	public void checkAmountDetials() {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		double assets = netWorth.getAssetsAmount();
		double liability = netWorth.getLiabilityAmount();
		double nwAmount = netWorth.getNWAmount();

		Assert.assertEquals(nwAmount, assets - liability);
	}

	@Test(description = "NETWOR_01_17:", groups = { "DesktopBrowsers" }, priority = 18, enabled = true)
	public void verifyyearOnChart() {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2500);

		netWorth.clickChartLink();
		SeleniumUtil.waitForPageToLoad(2500);

		logger.info("Fetching the current year");
		String CurrentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

		logger.info("Fetching the previous year");
		String previousYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1);

		logger.info("Previous year is:" + previousYear + " Current year is:" + CurrentYear);

		List<WebElement> xAxisDate = netWorth.xAxisMonthLabels();

		boolean isFirst = true;

		for (WebElement element : xAxisDate) {
			String text = element.getText().trim();
			if (isFirst) {
				Assert.assertTrue(text.contains(previousYear));
				isFirst = false;
			} else {
				if (text.equalsIgnoreCase("JAN")) {
					Assert.assertTrue(text.contains(CurrentYear));
				}
			}
		}
	}

	@Test(description = "NETWOR_01_18:Bug 826916:", groups = { "DesktopBrowsers" }, priority = 19, enabled = true)
	public void verifyTimeFilterAndChart() {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		for (int i = 1; i <= 6; i++) {

			String text = netWorth.selectAndGetValueFromTimeFilter(i);

			if (i == 6) {
				netWorth.clearCustomStartDate();
				netWorth.typeCustomStartDate(netWorth.getNMonthOldDate(1));
				netWorth.clickUpdateButton();
				SeleniumUtil.waitForPageToLoad();
			}

			String values[] = netWorth.getDurationLabelsFromChart(text).split("-");

			String startMonth = values[0];
			String endMonth = values.length > 1 ? values[1] : values[0];

			// Getting the first 3 character of month at index 0 on x axis.
			String firstMonthLabel = netWorth.getXAxisMonthLabel(0);
			firstMonthLabel = firstMonthLabel.substring(0, Math.min(firstMonthLabel.length(), 3));

			// Getting the first 3 character of last month x axis.
			String lastMonthLabel = netWorth.getXAxisLastMonthLabel();
			lastMonthLabel = lastMonthLabel.substring(0, Math.min(lastMonthLabel.length(), 3));

			Assert.assertEquals(firstMonthLabel, startMonth,
					"First month on X Axis does not match with Duration. Probably a bug !");
			Assert.assertEquals(lastMonthLabel, endMonth,
					"Last month on X Axis does not match with Duration. Probably a bug !");

		}
	}

	@Test(description = "NETWOR_01_19:Bug 826915:No data found shows when all accounts are unchecked, then accounts are rechecked and/or user logs out in between", groups = {
			"DesktopBrowsers" }, priority = 20, enabled = true)
	public void verifyNoDataFoundOnUncheckingAllAccounts() throws InterruptedException {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.allAccountsDD());

		SeleniumUtil.click(netWorth.allAccountsCheckBox());
		SeleniumUtil.click(netWorth.doneButton());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(netWorth.noDataDisplayed().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.refresh(d);
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(netWorth.allAccountsDD());
		SeleniumUtil.click(netWorth.dagInvestmentChkBox());
		SeleniumUtil.click(netWorth.doneButton());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(netWorth.contentsSection().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.refresh(d);
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.allAccountsDD());
		SeleniumUtil.click(netWorth.dagSiteTestDataChkBox());
		SeleniumUtil.click(netWorth.doneButton());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(netWorth.contentsSection().isDisplayed());

	}

	@Test(description = "NETWOR_01_20:BugId-782687:Verifying the Exclude Net worth", groups = {
			"DesktopBrowsers" }, priority = 21, enabled = true)
	public void excludeNetWorth() throws Exception {
		SeleniumUtil.refresh(d);
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(accAddition.addContainerAccount(PropsUtil.getDataPropertyValue("netWorthDagBank"),
				PropsUtil.getDataPropertyValue("netWorthDagBankUN"),
				PropsUtil.getDataPropertyValue("netWorthDagBankPassword")));

		SeleniumUtil.waitForPageToLoad(5000);
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.click(netWorth.allAccountsDD());

		WebElement dagBankEl = netWorth.dagSiteAccount();
		Assert.assertNull(dagBankEl);
		SeleniumUtil.click(netWorth.allAccountsDD());
		netWorth.forceNavigateToNetWorth();
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
