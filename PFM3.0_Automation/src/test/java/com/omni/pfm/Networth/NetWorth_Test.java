/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Networth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.Networth.NetWorth_Loc.timeFilterOptions;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.GenericUtil.dateOperations;
import com.omni.pfm.utility.GenericUtil.dateParameters;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class NetWorth_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(NetWorth_Test.class);
	NetWorth_Loc netWorth = null;
	AccountAddition accAddition;
	final static String replaceChar = "#####";

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Networth");
		netWorth = new NetWorth_Loc(d);
		accAddition = new AccountAddition();
		// Login, adding account, navigating to Networth finapp
		LoginPage.loginMain(d, loginParameter);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("wellness_nw.site16441.1", "site16441.1");
		PageParser.forceNavigate("NetWorth", d);
	}

	@Test(description = "NETWOR_01_01:Verify first time coach mark details.", priority = 2)
	public void coachMarkVerification() throws Exception {
		Assert.assertTrue(netWorth.welcomeCoachMarkHeading().isDisplayed());
		logger.info("Verified the Welcome coach mark when user access the Net Worth finapp for the first time ");
		String heading = netWorth.welcomeCoachMarkHeading().getText();
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("NW_Welcome_CM_Heading"));
		logger.info("Verified the heading Welcome coach mark ");
		String text = netWorth.welcomeCoachMarkHeadingTxt().getText();
		logger.info("Verified the text of Welcome coach mark");
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("NW_Welcome_CM_TxtWeb"));
	}

	@Test(description = "NETWOR_01_02:Verify bullets in Coach mark", priority = 3, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" }, dependsOnMethods = "coachMarkVerification")
	public void verifyBulletsINCM() {
		int size = netWorth.bulletInCoachMark().size();
		Assert.assertEquals(2, size, "Number of bullets in coach mark are not as expected");
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

	@Test(description = "NETWOR_01_03:Verify first time link account coach mark.User should see the networth data w.r.t. all the added accounts", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 4, enabled = true, dependsOnMethods = "coachMarkVerification")
	public void verifyLinkAccountWelcomeCM() throws IndexOutOfBoundsException {
		Assert.assertTrue(netWorth.continueButton_NW().isDisplayed(),
				"Continue to networth button is not displayed FTUE popup");
		SeleniumUtil.click(netWorth.continueButton_NW());
		logger.info(
				"Verified that clicking on CONTINUE button in Welcome coachmark takes user to Link Accounts coackmark");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
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
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	@Test(description = "NETWOR_01_04:Verify the variuos Texts on UI.Verify that the header of the Net Worth screen has the mentioned components in the same order", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 5, enabled = true)
	public void verifyUIIcons() throws Exception {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String appHeading = netWorth.headingText().getText();
		Assert.assertEquals(appHeading, PropsUtil.getDataPropertyValue("NetworthHeader"));
		if (PageParser.isElementPresent("linkAccountButtonForMobile", "NetWorth", null)) {
			Assert.assertTrue(netWorth.linkAccountButtonForMobile().isDisplayed());
			logger.info("Verified that Link Account button is displayed on the NetWorth");
		} else {
			Assert.assertTrue(netWorth.printIcon().isDisplayed());
			logger.info("Verified that Print Icon is displayed on the NetWorth");
			Assert.assertTrue(netWorth.linkAccButton().isDisplayed());
			logger.info("Verified that Link Account button is displayed on the NetWorth");
		}
	}

	@Test(description = "NETWOR_01_05:Verify the Default date range is 12 months.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 6, enabled = true, dependsOnMethods = "verifyLinkAccountWelcomeCM")
	public void defaultDateRangeVerification() throws Exception {
		netWorth.forceNavigateToNetWorth();
		Assert.assertTrue(netWorth.verifyChartTitle(PropsUtil.getDataPropertyValue("netWorthDefaultDateRange"))); // 12
		logger.info("Verified that by default Net Worth chart shows 12 month's data");
	}

	@Test(description = "NETWOR_01_06:Verify the functionality of info Coach Mark functionality.", groups = {
			"DesktopBrowsers", "Smoke", "infoCMVerification" }, priority = 7, enabled = true)
	public void infoCMVerification() throws Exception {
		Assert.assertTrue(netWorth.moreIcon().isDisplayed());
		SeleniumUtil.click(netWorth.moreIcon());
		logger.info("Verifying that clicking on the info icon launches Link All Your Accounts coachmark");
		SeleniumUtil.waitFor(2);
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
		logger.info("Verifying the Interactive Chart info coach mark components ");
		String heading = netWorth.getCMHeadingText(2);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthInteractive").trim());
		Assert.assertTrue(netWorth.cmCloseButton(2).isDisplayed());
		String text = netWorth.getCMBodyContent(2);
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("netWorthThisChartText").trim());
		logger.info(
				"Verified that clicking anywhere on Next button in Link Account info coach mark opens the Interactive Chart coach mark.");
		netWorth.clickCMBackButton(1);
		heading = netWorth.getCMHeadingText(1);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthLinkAllYourAcc").trim());
		logger.info(
				"Verified that clicking on Back button from Interactive Chart coach mark takes user to Link Accounts coach mark pointing to link accounts button or icon");
	}

	@Test(description = "NETWOR_01_08:Verify the functionality of net worth Coach Mark functionality.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 9, enabled = true) // ,dependsOnGroups={"infoCMVerification"}
	public void nwDetailCMVerification() throws Exception {
		netWorth.clickCMNextButton(1);
		netWorth.clickCMNextButton(2);
		String heading = netWorth.getCMHeadingText(3);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthDetails"));
		Assert.assertTrue(netWorth.cmCloseButton(3).isDisplayed());
		String text = netWorth.getCMBodyContent(3);
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("netWorthTextOfThistable"));
		netWorth.clickCMBackButton(2);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		heading = netWorth.getCMHeadingText(2);
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("netWorthInteractive"));
		netWorth.clickCMNextButton(2);
		netWorth.clickCMNextButton(3);
	}

	@Test(description = "NETWOR_01_09:Verify that date shown in chart is in sync with date from filter drop down.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 10, enabled = true)
	public void timeFilterDataVerification() throws Exception {
		for (int i = 1; i <= 10; i++) {
			String text = "";
			String dropDownText;
			if (i != 10) {
				dropDownText = netWorth.selectAndGetValueFromTimeFilter(i);
				text = PropsUtil.getDataPropertyValue("networth.change.text");
				logger.info(":::: The text is:::: " + text);
				String changeInMonth = netWorth.changeText().getText().trim();
				logger.info("The changeInMonth is" + changeInMonth);
				Assert.assertTrue(changeInMonth.toLowerCase().contains(text.toLowerCase()),
						"Networth changed display text is not as expected for " + dropDownText + ". Expected ::" + text
								+ " & Actual ::" + changeInMonth);
			} else {
				dropDownText = netWorth.selectAndGetValueFromTimeFilter(i);
				netWorth.clearCustomStartDate();
				netWorth.typeCustomStartDate(netWorth.targateDate1(-32));
				netWorth.clickUpdateButton();
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				text = PropsUtil.getDataPropertyValue("networth.change.text");
				logger.info(":::: The text is:::: " + text);
				String changeInMonth = netWorth.changeText().getText().trim();
				logger.info("The changeInMonth is" + changeInMonth);
				Assert.assertTrue(changeInMonth.toLowerCase().contains(text.toLowerCase()),
						"Networth changed display text is not as expected for " + dropDownText + ". Expected ::" + text
								+ " & Actual ::" + changeInMonth);
			}
		}
	}

	@Test(description = "NETWOR_01_10:Verify time range in time filter.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 11, enabled = true)
	public void timeFilterTimeRangeVerification() throws Exception {
		netWorth.forceNavigateToNetWorth();
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
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(netWorth.expandLiabilitiesToViewAcc());
		String container[] = netWorth.getAllContainerInLiabilities();
		String expectedContainerInLiability[] = PropsUtil.getDataPropertyValue("expected_liabilities_container").trim()
				.split(",");
		Arrays.sort(container);
		Arrays.sort(expectedContainerInLiability);
		Assert.assertEquals(container, expectedContainerInLiability);
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(netWorth.allAccountsDD());
		}
	}

	@Test(description = "NETWOR_01_12:Verify the Containers types in Assets.Verify that networth supports Bank, Investment, Insurance & Real Estate containers for assets", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 13, enabled = true)
	public void verifyContainersForAssets() throws Exception {
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			SeleniumUtil.click(netWorth.allAccountsDD());
		}
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		String container[] = netWorth.getAllContainerInAsset();
		String expectedContainerInAssets[] = PropsUtil.getDataPropertyValue("expected_asset_container").split(",");
		Arrays.sort(container);
		Arrays.sort(expectedContainerInAssets);
		Assert.assertEquals(container, expectedContainerInAssets);
	}

	@Test(description = "NETWOR_01_13:Verify the headings in Net Worth Summary Details.", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 14, enabled = true)
	public void nwSummaryVerification() throws Exception {
		// 1. Net Worth details should lie below the chart
		// 2. Details section should have BALANCE, CHANGE AMOUNT AND CHANGE % as
		// the column names in the same order.
		netWorth.forceNavigateToNetWorth();
		netWorth.verifyNWDetailTitle(PropsUtil.getDataPropertyValue("netWorthDetails"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (PageParser.isElementPresent("getNWSummaryHeadingTitles", "NetWorth", null)) {
			String actualHeading[] = netWorth.getNWSummaryHeadingTitles();
			String expectedHeading[] = PropsUtil.getDataPropertyValue("nw_summary_heading_title").split(",");
			Assert.assertEquals(actualHeading, expectedHeading);
		}
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
		netWorth.assertNWAssetAndLiabilityAmount(asset, liability, networth);
		logger.info("The Values of Asset, Liabilities and Networth are verified.");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	@Test(description = "NETWOR_01_15:Verify the headings in NW table view.", groups = {
			"DesktopBrowsers" }, priority = 16, enabled = true)
	public void verifyTableView() throws Exception {
		netWorth.forceNavigateToNetWorth();
		netWorth.switchToTableView();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String[] actualHeading = netWorth.getHeadingInTable();
		String expectedHeadings[] = PropsUtil.getDataPropertyValue("Headings_In_Table_View")
				.split(PropsUtil.getDataPropertyValue("netWorthTableViewSeperator"));
		Assert.assertEquals(actualHeading, expectedHeadings);
	}

	@Test(description = "NETWOR_01_16:Verify that total net worth is the sum of Assets-liabilities. ", groups = {
			"DesktopBrowsers" }, priority = 17, enabled = true)
	public void checkAmountDetials() {
		netWorth.forceNavigateToNetWorth();
		double assets = netWorth.getAssetsAmount();
		double liability = netWorth.getLiabilityAmount();
		double nwAmount = netWorth.getNWAmount();
		Assert.assertEquals(nwAmount, assets - liability);
	}

	@Test(description = "NETWOR_01_17:", groups = { "DesktopBrowsers" }, priority = 18, enabled = true)
	public void verifyyearOnChart() {
		netWorth.forceNavigateToNetWorth();
		netWorth.clickChartLink();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
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

	@Test(description = "NETWOR_01_18:Bug 826916:", groups = { "DesktopBrowsers" }, priority = 19, enabled = false)
	public void verifyingBug826916() {
		netWorth.forceNavigateToNetWorth();
		for (int i = 1; i <= 6; i++) {
			String text = netWorth.selectAndGetValueFromTimeFilter(i);
			logger.info("Selected Dropdown is " + text);
			if (i == 6) {
				netWorth.clearCustomStartDate();
				netWorth.typeCustomStartDate(netWorth.getNMonthOldDate(1));
				netWorth.clickUpdateButton();
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			}
			String values[] = netWorth.getDurationLabelsFromChart(text).split("-");
			String date = DateUtil.getDate("MMM", -15);
			logger.info("Subtracting 15 days from Current date :" + date);
			String currentDate = DateUtil.getDate("MMM", 0);
			logger.info("Current date :" + currentDate);
			if (text.contains(PropsUtil.getDataPropertyValue("netWorthThisMonth")) && currentDate != date) {
				String startMonth = DateUtil.getPreviousMonth();
				logger.info("Expected Value of Start Month in the Chart of X-Axis is " + startMonth);
				String endMonth = DateUtil.getDate("MMM", 0);
				logger.info("Expected Value of End Month in the Chart of X-Axis is : " + endMonth);
				// Getting the first 3 character of month at index 0 on x axis.
				String firstMonthLabel = netWorth.getXAxisMonthLabel(0);
				firstMonthLabel = firstMonthLabel.substring(0, Math.min(firstMonthLabel.length(), 3));
				logger.info("Actual Value of Start Month in the Chart of X-Axis is " + firstMonthLabel);
				// Getting the first 3 character of last month x axis.
				String lastMonthLabel = netWorth.getXAxisLastMonthLabel();
				lastMonthLabel = lastMonthLabel.substring(0, Math.min(lastMonthLabel.length(), 3));
				logger.info("Actual Value of End Month in the Chart of X-Axis is " + lastMonthLabel);
				Assert.assertEquals(firstMonthLabel, startMonth.toLowerCase(),
						"First month on X Axis does not match with Duration. Probably a bug !");
				Assert.assertEquals(lastMonthLabel, endMonth.toLowerCase(),
						"Last month on X Axis does not match with Duration. Probably a bug !");
			} else {
				String startMonth = values[0];
				String endMonth = values.length > 1 ? values[1] : values[0];
				// Getting the first 3 character of month at index 0 on x axis.
				String firstMonthLabel = netWorth.getXAxisMonthLabel(0);
				firstMonthLabel = firstMonthLabel.substring(0, Math.min(firstMonthLabel.length(), 3));
				logger.info("Actual Value of Start Month in the Chart of X-Axis is " + firstMonthLabel);
				// Getting the first 3 character of last month x axis.
				String lastMonthLabel = netWorth.getXAxisLastMonthLabel();
				lastMonthLabel = lastMonthLabel.substring(0, Math.min(lastMonthLabel.length(), 3));
				logger.info("Actual Value of End Month in the Chart of X-Axis is " + lastMonthLabel);
				Assert.assertEquals(firstMonthLabel, startMonth,
						"First month on X Axis does not match with Duration. Probably a bug !");
				Assert.assertEquals(lastMonthLabel, endMonth,
						"Last month on X Axis does not match with Duration. Probably a bug !");
			}
		}
	}

	@Test(description = "NETWOR_01_19:Bug 826915:No data found shows when all accounts are unchecked, then accounts are rechecked and/or user logs out in between", groups = {
			"DesktopBrowsers" }, priority = 20, enabled = true)
	public void verifyingBug826915() throws InterruptedException {
		netWorth.forceNavigateToNetWorth();
		netWorth.clickAccountSelectDropDown();
		netWorth.clickALLAcountsCBox();
		if (PageParser.isElementPresent("doneBtn", "NetWorth", null)) {
			SeleniumUtil.click(netWorth.doneButton());
		}
		// Verify no data is displayed when no accounts are selected.
		Assert.assertTrue(netWorth.noDataDisplayed().isDisplayed());
		/*
		 * Select each account , check data section is coming. Do it for all accounts in
		 * list.
		 **/
		int totalAccounts = netWorth.getNumberOfAccounts();
		for (int i = 0; i < totalAccounts; i++) {
			if (PageParser.isElementPresent("allAccountsDDForMobile", "NetWorth", null)) {
				SeleniumUtil.click(netWorth.allAccountsDDForMobile());
				SeleniumUtil.waitForPageToLoad(1000);
			}
			netWorth.selectAccountFromFilter(i);
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			if (PageParser.isElementPresent("doneBtn", "NetWorth", null)) {
				SeleniumUtil.click(netWorth.doneButton());
			}
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			netWorth.isContentPresent(i);
		}
	}

	@Test(description = "NETWOR_01_20:BugId-782687:Verifying whenever an account is added, it is by default included in networth.", groups = {
			"DesktopBrowsers" }, priority = 21, enabled = true)
	public void verifyingBug782687() throws Exception {
		/*
		 * Step 1: Gets the number of accounts from the networth account filter DD. Step
		 * 2: Adds one more DagBank account! Step 3: Gets the number of accounts after
		 * adding account as in step 2. Step 4: verify that size of accounts in step 3
		 * is greater than step 1.
		 * 
		 */
		int before = netWorth.getNumberOfAccounts();
		PageParser.forceNavigate("DashboardPage", d);
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addContainerAccount(PropsUtil.getDataPropertyValue("netWorthDagBank"),
				PropsUtil.getDataPropertyValue("netWorthDagBankUN"),
				PropsUtil.getDataPropertyValue("netWorthDagBankPassword")));
		netWorth.forceNavigateToNetWorth();
		netWorth.clickAccountSelectDropDown();
		if (PageParser.isElementPresent("doneBtn", "NetWorth", null)) {
			SeleniumUtil.click(netWorth.doneButton());
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		int after = netWorth.getNumberOfAccounts();
		Assert.assertTrue(after > before, "Account is added , but not included in networth. Probably a Bug !");
	}

	@Test(description = "NETWOR_01_21:User should not see FTUE coachmarks", priority = 22)
	public void verifyFTUEisNotDisplayed() throws Exception {
		netWorth.forceNavigateToNetWorth();
		By welcomeCoachMark = SeleniumUtil.getByObject("NetWorth", null, "welcomeCoachMarkHeading");
		if (SeleniumUtil.isDisplayed(welcomeCoachMark, 4)) {
			fail("FTUE poup is getting displayed when we are navigating to the networth page dor second time");
		}
	}

	@Test(description = "NETWOR_01_22:Coachmark should close and other coachmarks should not open", priority = 23)
	public void coachMarkShouldNotOpen() throws Exception {
		SeleniumUtil.click(netWorth.moreIcon());
		SeleniumUtil.click(netWorth.infoCMButton());
		SeleniumUtil.click(netWorth.cmCloseButton());
		By welcomeCoachMark = SeleniumUtil.getByObject("NetWorth", null, "InteractiveChart_CLP");
		if (SeleniumUtil.isDisplayed(welcomeCoachMark, 4)) {
			fail("Interactive chart is getting displayed when we close the coach mark popup");
		}
	}

	@Test(description = "NETWOR_01_23:verify Interactive Chart coachmark:", priority = 24)
	public void verifyInteractiveCoachmark() throws Exception {
		SeleniumUtil.click(netWorth.moreIcon());
		SeleniumUtil.click(netWorth.infoCMButton());
		netWorth.clickCMNextButton(1);
		Assert.assertEquals(netWorth.interactiveChart().getText(), PropsUtil.getDataPropertyValue("CoachmarkHeading2"));
		Assert.assertEquals(netWorth.interactiveChart_Para().getText(),
				PropsUtil.getDataPropertyValue("netWorthThisChartText"));
		SeleniumUtil.click(netWorth.cmCloseButton(2));
	}

	@Test(description = "NETWOR_01_24:Navigate to first CoachMArk by clicking back button", priority = 25)
	public void verifyBackButton() throws Exception {
		SeleniumUtil.click(netWorth.moreIcon());
		SeleniumUtil.click(netWorth.infoCMButton());
		netWorth.clickCMNextButton(1);
		netWorth.clickCMBackButton(2);
		Assert.assertEquals(netWorth.interactiveChart_LinkAccount().getText(),
				PropsUtil.getDataPropertyValue("CoachmarkHeading1"));
		SeleniumUtil.click(netWorth.cmCloseButton(1));
	}

	@Test(description = "NETWOR_01_25:Verify that by default assets and liabilities legends are checked and user see assests-liabilities bars and Net Worth line with summary bubble on chart", priority = 26)
	public void verifyByDefaultLegends() throws Exception {
		Assert.assertTrue(netWorth.ChartLegends().get(0).isDisplayed());
		Assert.assertEquals(
				netWorth.ChartLegends().get(0).getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),
				"true");
		Assert.assertTrue(netWorth.ChartLegends().get(1).isDisplayed());
		Assert.assertEquals(
				netWorth.ChartLegends().get(1).getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),
				"true");
	}

	@Test(description = "Verify networth page when 3 months timefilter is selected", priority = 27)
	public void checkNetworthFunctionalityWhen3MonthsTimeFilterIsSelected() throws Exception {
		SeleniumUtil.refresh(d);
		SoftAssert soft = new SoftAssert();
		netWorth.forceNavigateToNetWorth();
		netWorth.selectTheGivenOptionFromDurationDropdown(timeFilterOptions.MONTHS_3);
		Date dateBefore3Months = GenericUtil.modifyTheGivenDate(new Date(), dateOperations.SUBTRACTION,
				dateParameters.Months, 2);
		Date todayDate = new Date();
		String expectedStartMonth = PropsUtil.getDataPropertyValue("durationDropdown.selectedDate.text")
				.replace(replaceChar, GenericUtil.returnGivenDateInGivenFormat(dateBefore3Months, "MMM yyyy"));
		String actualMonth = netWorth.returnDateRangeDisplayedInDurationDropdown();
		logger.info("Range displayed in duration dropdown is '" + expectedStartMonth + "'");
		if (!actualMonth.equals(expectedStartMonth)) {
			soft.fail("The range displayed in duration dropdown is not as expected. Expected :: '" + expectedStartMonth
					+ "' and Actual :: '" + actualMonth + "'");
		}
		String expectedNetworthDuration = GenericUtil.returnGivenDateInGivenFormat(dateBefore3Months, "MMM yyyy")
				.toUpperCase() + " - " + GenericUtil.returnGivenDateInGivenFormat(todayDate, "MMM yyyy").toUpperCase();
		String actualnetWorthDuration = netWorth.returnNetWorthDurationDisplayedAboveTheChart();
		if (!actualnetWorthDuration.equals(expectedNetworthDuration)) {
			soft.fail("The networth duration displayed is not as expected. Expected :: '" + expectedNetworthDuration
					+ "' and Actual :: '" + actualnetWorthDuration + "'");
		}
		String expectedInfoMessage = PropsUtil.getDataPropertyValue("networth.infoMessageForMonth.text");
		String actualInfomessage = netWorth.returnTheInfoMessageDisplayedInTheNetworth();
		if (!actualInfomessage.equals(expectedInfoMessage)) {
			soft.fail("The networth info message is not as expected. Expected :: '" + expectedInfoMessage
					+ "' and Actual :: '" + actualInfomessage + "'");
		}
		ArrayList<String> actualMonthsDisplayedInChart = netWorth.returnXAxisvaluesDisplayedInChart();
		ArrayList<String> expectedMonthsToBeDisplayedInChart = GenericUtil
				.returnMonthsBetweenFromGivenDateToToday(dateBefore3Months);
		if (!actualMonthsDisplayedInChart.equals(expectedMonthsToBeDisplayedInChart)) {
			soft.fail("The months to be displayed in x-Axis of chart in networth is not as expected. Expected :: '"
					+ expectedMonthsToBeDisplayedInChart + "' and Actual :: '" + actualMonthsDisplayedInChart + "'");
		}
		soft.assertAll();
	}

	@Test(description = "Verify networth page when 2 weeks timefilter is selected", priority = 28)
	public void checkNetworthFunctionalityWhen2WeeksTimeFilterIsSelected() throws Exception {
		SoftAssert soft = new SoftAssert();
		netWorth.forceNavigateToNetWorth();
		Date dateBefore2Weeks = GenericUtil.modifyTheGivenDate(new Date(), dateOperations.SUBTRACTION,
				dateParameters.Days, 14);
		netWorth.selectTheGivenOptionFromDurationDropdown(timeFilterOptions.CUSTOMDATE);
		netWorth.updateCustomFilter(GenericUtil.returnGivenDateInGivenFormat(dateBefore2Weeks, "MM/dd/yyyy"));

		Date todayDate = new Date();
		String expectedStartMonth = GenericUtil.returnGivenDateInGivenFormat(dateBefore2Weeks, "MM/dd/yyyy") + " - "
				+ GenericUtil.returnGivenDateInGivenFormat(todayDate, "MM/dd/yyyy");
		String actualMonth = netWorth.returnDateRangeDisplayedInDurationDropdown();
		logger.info("Range displayed in duration dropdown is '" + expectedStartMonth + "'");
		if (!actualMonth.equals(expectedStartMonth)) {
			soft.fail("The range displayed in duration dropdown is not as expected. Expected :: '" + expectedStartMonth
					+ "' and Actual :: '" + actualMonth + "'");
		}
		String expectedNetworthDuration = GenericUtil.returnGivenDateInGivenFormat(dateBefore2Weeks, "dd MMM yyyy")
				.toUpperCase() + " - "
				+ GenericUtil.returnGivenDateInGivenFormat(todayDate, "dd MMM yyyy").toUpperCase();
		String actualnetWorthDuration = netWorth.returnNetWorthDurationDisplayedAboveTheChart();
		if (!actualnetWorthDuration.equals(expectedNetworthDuration)) {
			soft.fail("The networth duration displayed is not as expected. Expected :: '" + expectedNetworthDuration
					+ "' and Actual :: '" + actualnetWorthDuration + "'");
		}
		String expectedInfoMessage = PropsUtil.getDataPropertyValue("networth.infoMessageForWeek.text");
		String actualInfomessage = netWorth.returnTheInfoMessageDisplayedInTheNetworth();
		if (!actualInfomessage.equals(expectedInfoMessage)) {
			soft.fail("The networth info message is not as expected. Expected :: '" + expectedInfoMessage
					+ "' and Actual :: '" + actualInfomessage + "'");
		}
		ArrayList<String> actualMonthsDisplayedInChart = netWorth.returnXAxisvaluesDisplayedInChart();
		ArrayList<String> expectedMonthsToBeDisplayedInChart = GenericUtil
				.returnSaturdaysBetweenFromGivenDateToToday(dateBefore2Weeks);
		if (!actualMonthsDisplayedInChart.equals(expectedMonthsToBeDisplayedInChart)) {
			soft.fail("The months to be displayed in x-Axis of chart in networth is not as expected. Expected :: '"
					+ expectedMonthsToBeDisplayedInChart + "' and Actual :: '" + actualMonthsDisplayedInChart + "'");
		}
		soft.assertAll();
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