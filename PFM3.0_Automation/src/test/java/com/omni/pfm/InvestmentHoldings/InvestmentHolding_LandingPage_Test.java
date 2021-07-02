/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.InvestmentHoldings;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class InvestmentHolding_LandingPage_Test extends TestBase {
	static Logger logger = LoggerFactory.getLogger(InvestmentHolding_LandingPage_Test.class);

	InvestmentHoldings_Loc investmentHoldings;
	String expectedClassificationValues[];
	AccountAddition accAddition;

	@BeforeClass()
	public void init() {
		doInitialization("Investment Holdings");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		investmentHoldings = new InvestmentHoldings_Loc(d);
		accAddition = new AccountAddition();

	}

	@Test(description = "INV_01_01:Verify login happenes successfully.", priority = 1, groups = { "Desktop", "Smoke" })
	public void login() throws Exception {
		
		LoginPage.loginMain(d,loginParameter);
		SeleniumUtil.waitForPageToLoad();

		logger.info("==>Linking account in process..");
		
		accAddition.linkAccount(); 
		SeleniumUtil.waitForPageToLoad();
		accAddition.addContainerAccount("Dag Site",PropsUtil.getDataPropertyValue("dagSite_IHV"),PropsUtil.getDataPropertyValue("dagPassword_IHV"));
		SeleniumUtil.waitForPageToLoad(5000);
	
		PageParser.forceNavigate("InvestmentHoldings", d);
	}

	@Test(description = "L1-45798,L1-45799,L1-45800,L1-45805,AT-80909,AT-80910,AT-80911,AT-80913:INV_01_02:Verify first time coach mark details.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 2, enabled = true)
	public void coachMarkVerification() throws Exception {
		if (!"BBT".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_CoBrand"))) {
			logger.info("==Coachmark Verification..");

			Assert.assertTrue(InvestmentHoldings_Loc.welcomeCoachMarkHeading().isDisplayed());

			String heading = InvestmentHoldings_Loc.welcomeCoachMarkHeading().getText();

			Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("IH_Welcome_CM_Heading"));

			String continuebtntext = investmentHoldings.continueBtnInWelcomeCM().getText();
			Assert.assertEquals(continuebtntext, PropsUtil.getDataPropertyValue("IH_ContinueBtn_Text"));
		}
	}

	@Test(description = "L1-45801,AT-80916:INV_01_03:Verify bullets in Coach mark", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 3, enabled = true)
	public void verifyBulletsINCM() {

		logger.info("==>Coachmark Continues..");

		int size = investmentHoldings.bulletInCoachMark().size();
		Assert.assertEquals(2, size);

		int grey = 0;
		int blue = 0;
		List<WebElement> s = investmentHoldings.bulletInCoachMark();
		for (int i = 0; i < s.size(); i++) {
			String className = s.get(i).getAttribute("class");
			if (className.contains("blue")) {
				blue = blue + 1;
			} else if (className.contains("grey")) {
				grey = grey + 1;
			}
		}
		Assert.assertTrue(blue == 1 && grey == 1);
	}

	@Test(description = "L1-45803, L1-45804,L1-45806,AT-80914,AT-80915:INV_01_04:Verify Link Account Welcome Coach Mark options", priority = 4, enabled = true, groups = {

			"DesktopB" + "rowsers", "Smoke" })
	public void verifyLinkAccountWelcomeCM() throws IndexOutOfBoundsException {
		logger.info("==>Verifying Link Account in coachmark");

		investmentHoldings.continueBtnInWelcomeCM().click();
		SeleniumUtil.waitFor(2);
//		SeleniumUtil.waitForPageToLoad();

		logger.info("==>Verify Go To My Investment Button in coachmark");

		String gotomyinvesttext = investmentHoldings.goToMyInvestments().getText();
		Assert.assertTrue(investmentHoldings.goToMyInvestments().isDisplayed());
		Assert.assertEquals(gotomyinvesttext, PropsUtil.getDataPropertyValue("Go_To_My_Investment_Txt"));

		String linkaccntheader = InvestmentHoldings_Loc.linkAccountlnkWelcomeCM().getText();
		Assert.assertTrue(InvestmentHoldings_Loc.linkAccountlnkWelcomeCM().isDisplayed());
		Assert.assertEquals(linkaccntheader, PropsUtil.getDataPropertyValue("IH_Link_Account_Welcome_CM_Heading"));
		String linkaccnttext = InvestmentHoldings_Loc.welcomeCoachMarkTxt_1().getText();
		Assert.assertEquals(linkaccnttext, PropsUtil.getDataPropertyValue("IH_welcomeCoachMarkTxt_1"));
		linkaccnttext = InvestmentHoldings_Loc.welcomeCoachMarkTxt_2().getText();
		Assert.assertEquals(linkaccnttext, PropsUtil.getDataPropertyValue("IH_welcomeCoachMarkTxt_2"));

		Assert.assertTrue(investmentHoldings.linkAccountLinkrightftue().isDisplayed());
		String linkaccnttextright;
		linkaccnttextright = investmentHoldings.linkAccountLinkrightftue().getText().trim().toUpperCase();
		Assert.assertEquals(linkaccnttextright, PropsUtil.getDataPropertyValue("Link_Account").toUpperCase());
		

	}

		@Test(description = "L1-45798,L145801:INV_01_05:Verify bullets in Coach mark after continue.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 5, enabled = true)
	public void verifyBulletsINCMLinkAccScreen() {

		int size = investmentHoldings.bulletInCoachMarkAfterContinue().size();
		Assert.assertEquals(2, size);

		logger.info("==>Verify navigation bullets not appearing full");

		int grey = 0;
		int blue = 0;
		List<WebElement> s = investmentHoldings.bulletInCoachMarkAfterContinue();
		for (int i = 0; i < s.size(); i++) {
			String className = s.get(i).getAttribute("class");
			if (className.contains("blue") && i == 1) {
				blue = blue + 1;
			} else if (className.contains("grey") && i == 0) {
				grey = grey + 1;
			}
		}

		Assert.assertEquals(blue, 1);
		Assert.assertEquals(grey, 1);
		SeleniumUtil.waitForPageToLoad();

		investmentHoldings.goToMyInvestments().click();
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "L1-45807:INV_01_06:Verify the feature coach mark", priority = 6, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyFeatureCMVerification() throws IndexOutOfBoundsException, InterruptedException {
		
		Thread.sleep(5000);
		//SeleniumUtil.click(investmentHoldings.moreOption().get(0)); //no use
		SeleniumUtil.waitForPageToLoad();
		
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertTrue(InvestmentHoldings_Loc.featuretouricon().isDisplayed());	
		}else {
			SeleniumUtil.click(investmentHoldings.moreOption().get(0));
			String investmentHoldingsHeaderTxt = InvestmentHoldings_Loc.featuretourrbtn().getText().trim();
			logger.info("==>Verify feature coachmark verification text");
			Assert.assertTrue(InvestmentHoldings_Loc.featuretourrbtn().isDisplayed());
			Assert.assertTrue(investmentHoldingsHeaderTxt.contains(PropsUtil.getDataPropertyValue("IH_Feature_Tour_Txt")));
		}	
	}

	@Test(description = "L1-45809,L1-45811,AT-80921:INV_01_07: Investment Holdings header", priority = 7, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyInvestmentHoldingsHeader() throws IndexOutOfBoundsException {

		String investmentHoldingsHeaderTxt = InvestmentHoldings_Loc.investmentHoldingsHeader().getText().trim();

		logger.info("==>Verify Header");

		Assert.assertTrue(InvestmentHoldings_Loc.investmentHoldingsHeader().isDisplayed());
		Assert.assertTrue(investmentHoldingsHeaderTxt.contains(PropsUtil.getDataPropertyValue("IH_page_header")));

		if ("BBT".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_CoBrand"))) {
			PageParser.navigateToPage("InvestmentHoldings", d);
		} else {

		}

	}
	
	@Test(description = "L1-45831:INV_01_08:Verify the classification drop down", priority = 8, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyClassificationDropDown() throws IndexOutOfBoundsException {

		InvestmentHoldings_Loc.GenericDropDown().click();
		SeleniumUtil.waitForPageToLoad();

		String actualClassificationValues[] = investmentHoldings.getAllValuesFromClassification();
		expectedClassificationValues = PropsUtil.getDataPropertyValue("IH_classification_range").split(",");
		SeleniumUtil.assertExpectedActualList(Arrays.asList(actualClassificationValues), Arrays.asList(expectedClassificationValues),"Investment holdings classification dropdown values are not as expected.");

		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			InvestmentHoldings_Loc.GenericCloseforMobile().click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			InvestmentHoldings_Loc.GenericDropDown().click();
		}
	}

	@Test(description = "L1-45839, L1-45867:INV_01_09:Verify tick mark present or not", priority = 9, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyChartTitleAndTick() {
		
		logger.info("==>Assert the tick mark for selected menu");

		String ChartTypeNames[] = investmentHoldings.getHoldingsChartTypeNames();
		String[] holdingTableTypeNames = null;
			
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(investmentHoldings.getHoldingsChart());
			SeleniumUtil.waitForPageToLoad();
			if (investmentHoldings.isGroupByAccountsCheckedMobile()) {
				InvestmentHoldings_Loc.groupbyAccountsbtnMobile().click();
			}
			SeleniumUtil.click(investmentHoldings.goBackMobile());
			SeleniumUtil.waitForPageToLoad();
		} else {
			holdingTableTypeNames = investmentHoldings.getHoldingsTableTypeNames();
			if (investmentHoldings.isGroupByAccountsChecked()) {
				InvestmentHoldings_Loc.groupbyAccountsbtn().click();
			}
			Arrays.sort(holdingTableTypeNames);
			Arrays.sort(ChartTypeNames);
			Assert.assertEquals(holdingTableTypeNames, ChartTypeNames);
		}

			logger.info("==>Test values for two columns");	
	}


	@Test(description = "L1-45810,AT-80919:INV_01_11:Verify the print button", priority = 10, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyPrintButton() throws IndexOutOfBoundsException {

		SeleniumUtil.waitForPageToLoad();
		logger.info("==>Assert print button and functionality");
		if(appFlag=="web" || appFlag=="false") {
			Assert.assertTrue(investmentHoldings.printHeader().get(0).isDisplayed());
		} 
		
		
	}
	
	@Test(description = "L1-45812,AT-80926,AT-80928,AT-80930,AT-80932,AT-80933:INV_01_12:Verify The Feature tour buttons", priority = 11, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyFeaturetour() throws IndexOutOfBoundsException, InterruptedException {

		SeleniumUtil.waitForPageToLoad();
		
		if(appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertTrue(InvestmentHoldings_Loc.featuretouricon().isDisplayed());
		}else {
			Assert.assertTrue(investmentHoldings.buttonText().get(0).isDisplayed());
		}
		
	}

	@Test(description = "L1-45814, L1-45815,L1-45816,L1-45817, L1-45818,AT-80925:INV_01_13:Verify The Filter your view coach mark functionality", priority = 12, enabled = true, groups = {
			"DesktopBrowsers", "Smoke" })
	public void verifyFilterYourViewCM() throws IndexOutOfBoundsException {

		if(appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(InvestmentHoldings_Loc.featuretouricon());
		}else {
			SeleniumUtil.click(investmentHoldings.moreOption().get(0));
			SeleniumUtil.click(InvestmentHoldings_Loc.featuretourrbtn());
		}
		
		String heading = investmentHoldings.getCMHeadingText(1);
		
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("IH_CMHeadingtext"));
		Assert.assertTrue(investmentHoldings.cmCloseButton(1).isDisplayed());

		String text = investmentHoldings.getCMBodyContent(1);

		Assert.assertEquals(text,"Choose all of your investment accounts, or select a few specific ones.\n\nYou can look at your investments by asset class, region, sector, or style. Investment style refers to the level of risk and reward.");

	}

	@Test(description = "L1-45812, L1-45819, L1-45820, L1-45823,AT-80929:INV_01_14:Verify analyze investment details CM functionality.", priority = 13, enabled = true, groups = {

			"DesktopBrowsers", "Smoke" })
	public void verifyAnalyzeInvestmentDetailsCM() throws IndexOutOfBoundsException {

			SeleniumUtil.click(investmentHoldings.getTourButton());

			investmentHoldings.clickCMNextButton(1);

			SeleniumUtil.waitForPageToLoad();

			String heading = investmentHoldings.getCMHeadingText(2);

			Assert.assertEquals(heading, "Analyze Investment Details");

			Assert.assertTrue(investmentHoldings.cmCloseButton(2).isDisplayed());

			String text = investmentHoldings.getCMBodyContent(2);

			Assert.assertEquals(text, PropsUtil.getDataPropertyValue("IH_CMBodyContent"));

			investmentHoldings.clickCMBackButton(1);

			SeleniumUtil.waitForPageToLoad();

			heading = investmentHoldings.getCMHeadingText(1);

			Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("IH_CMHeadingtext"));

		
	}

	@Test(description = "L1-45824, L1-45830, L1-45831,L1-45833,L1-45834,AT-80937,AT-80934,AT-80935:INV_01_15:Verify details about holdings CM functionality.", priority = 14, enabled = true, groups = {
			"DesktopBrowsers" })

	public void verifyDetailsAboutHoldingCM() throws IndexOutOfBoundsException {

		investmentHoldings.clickCMNextButton(1);

		SeleniumUtil.waitForPageToLoad();

		investmentHoldings.clickCMNextButton(2);

		SeleniumUtil.waitForPageToLoad();
		if(appFlag=="web" || appFlag=="false") {
			
			
			  String heading = investmentHoldings.getCMHeadingText(3);
			  
			  Assert.assertEquals(heading,
			  PropsUtil.getDataPropertyValue("IH_featureTourCoachMarks_heading"));
			  
			  Assert.assertTrue(investmentHoldings.cmCloseButton(3).isDisplayed());
			  
			  String text = investmentHoldings.getCMBodyContent(3);
			  
			  Assert.assertEquals(text,
			  PropsUtil.getDataPropertyValue("IH_featureTourCoachMarks_Content"));
			  
			  investmentHoldings.clickCMNextButton(3);
			 
		}

		

	}


	@Test(description = "L1-45843, L1-45844,L1-45862:INV_01_17:Verify in table By Accounts Group Scenario", priority = 15, enabled = true, groups = {

			"DesktopBrowsers" })
	public void groupsByAccountsVerification() throws IndexOutOfBoundsException {

		logger.info("==>Verify groups by account functionality");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(appFlag.equals("app") || appFlag.equals("emulator")){
			SeleniumUtil.click(investmentHoldings.getHoldingsChart());
			InvestmentHoldings_Loc.groupbyAccountsbtnMobile().click();
			SeleniumUtil.waitForPageToLoad();
			String[] columnTitlesmobile = investmentHoldings.getHoldingsTableHeaderTitleMobile();

			String[] expectedTitlesmobile = PropsUtil.getDataPropertyValue("IH_HOLDING_TABLE_HEADER_TITLE_MOBILE").split(",");
			if (columnTitlesmobile.length == expectedTitlesmobile.length) {
				for (int i = 0; i < expectedTitlesmobile.length; i++) {
					Assert.assertTrue(columnTitlesmobile[i].equalsIgnoreCase(expectedTitlesmobile[i].trim()));
				}
			} else {
				Assert.assertTrue(false);
			}
		}else {
			InvestmentHoldings_Loc.groupbyAccountsbtn().click();
			SeleniumUtil.waitForPageToLoad();
			String[] columnTitles = investmentHoldings.getHoldingsTableHeaderTitle();

			String[] expectedTitles = PropsUtil.getDataPropertyValue("IH_HOLDING_TABLE_HEADER_TITLE").split(",");
			if (columnTitles.length == expectedTitles.length) {
				for (int i = 0; i < expectedTitles.length; i++) {

					Assert.assertTrue(columnTitles[i].equalsIgnoreCase(expectedTitles[i].trim()));
				}
			} else {
				Assert.assertTrue(false);
			}
		}	

	}

	@Test(description = "L1-45864,65,66 :INV_01_18:Verify in table if the expand - collapse functionality", priority = 16, enabled = true, groups = {
			"DesktopBrowsers" })

	public void expandAccountsVerification() throws IndexOutOfBoundsException {
		logger.info("==>Verify the text \"Expand All\" displayed");
		
		if(appFlag=="web" || appFlag=="false") {
			Assert.assertTrue(investmentHoldings.expandCollapseButton().isDisplayed());
			String text = investmentHoldings.getExpandCollapseButtonText();
			Assert.assertTrue(text.equalsIgnoreCase(PropsUtil.getDataPropertyValue("IH_ExpendALL")));
			investmentHoldings.expandCollapseButton().click();
			text = investmentHoldings.getExpandCollapseButtonText();
			Assert.assertTrue(text.equalsIgnoreCase(PropsUtil.getDataPropertyValue("IH_Collapse_All")));
		}
		

	}


	@Test(description = "L1-45853:INV_01_20:Verify the today amount", priority = 17, enabled = false)
	public void verifyTodayAmount() throws IndexOutOfBoundsException {

		if(investmentHoldings.isTotalAmountMobile()) {
			Double todayamountmobile = investmentHoldings.getAmount(investmentHoldings.getTodaysTotalmobile());
			SeleniumUtil.click(investmentHoldings.goBackMobile());
			Double amountIncirclemobile=investmentHoldings.getAmount(investmentHoldings.getAmountInsideChart());
			Assert.assertEquals(amountIncirclemobile, todayamountmobile);
		}else {
			Double todayAmount = investmentHoldings.getAmount(investmentHoldings.getTodaysTotal());
			Double amountIncircle = investmentHoldings.getAmount(investmentHoldings.getAmountInsideChart());
			Assert.assertEquals(amountIncircle, todayAmount);
		}
	}

	@Test(description = "INV_01_21:Verify in table if the quantity-price-market value works ", priority = 18, enabled = true)
	public void verifyTableContentsMarketValue() throws IndexOutOfBoundsException {
		if(investmentHoldings.isExpandCollapseButton()) {
			SeleniumUtil.click(d.findElement(By.xpath("//span[contains(@class,'expand-collapse-row')]")));
			investmentHoldings.assertquantitypricevalue();
			SeleniumUtil.click(d.findElement(By.xpath("//span[contains(@class,'expand-collapse-row')]")));
		}
	}

	@Test(description = "L1-45871 - 45874:INV_01_22:verify check V button functionality", priority = 19, enabled = true, groups = {"DesktopBrowsers" })
	public void verifyVButtonFunctionality() throws IndexOutOfBoundsException, InterruptedException {
		logger.info("==>Check V button functionality");

		if (!"BBT".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_CoBrand"))) {
			SeleniumUtil.click(d.findElement(By.xpath("//ul[@class='nav-primary']//span[contains(text(),'Investment') and @class='text']")));
			SeleniumUtil.click(d.findElement(By.xpath("//ul[@class='nav-primary']//span[contains(text(),'Investment Holdings')]")));
			SeleniumUtil.waitForPageToLoad();
		}

		investmentHoldings.checkVButtonFunctionality();

	}

	@Test(description = "INV_01_23:Verify Foot notes and authentication modes", priority = 20, enabled = true)
	public void verifyDisclaimer() throws IndexOutOfBoundsException {

		logger.info("==>Verify Footnotes and authentications mode");

		String disclaimer = PropsUtil.getDataPropertyValue("IH_Disclaimer");
		investmentHoldings.assertDisclaimer(disclaimer);

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "INV_01_24:Verify Performance chart", priority = 21, enabled = false)
	public void verifyPerformanceChart() throws IndexOutOfBoundsException {

		SeleniumUtil.waitForPageToLoad();

		logger.info("==>Verify performance chart for selected chart");

		String performancetext = investmentHoldings.assertPerformanceChart();

		Assert.assertEquals(performancetext, PropsUtil.getDataPropertyValue("IH_Performance_Text"));

		logger.info("==>Assert the footer text");
		String performancefootertext = InvestmentHoldings_Loc.performancefooter().getText();
		Assert.assertEquals(performancefootertext, PropsUtil.getDataPropertyValue("IH_PerformanceChart_Footer"));

	}

}
