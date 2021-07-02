/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.mobile.Expense_IncomeAnalysis;

/**
* @author Archana
*
*/

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
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.IncLandingPage_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;

public class IncomeAnalysisLandingPage_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(IncomeAnalysisLandingPage_Test.class);
	
	public static String currentMonth;
	IncLandingPage_Loc IncLandingPage;
	ExpLandingPage_Loc ExpLandingPage;
	
	List<String> monthsList = Arrays.asList("This Month","Last Month","3 Months","6 Months","This Year","12 Months","Last Year","Custom Dates");
	
	@BeforeClass()
	public void init() {

		doInitialization("Income Analysis");

		IncLandingPage = new IncLandingPage_Loc(d);		
		ExpLandingPage = new ExpLandingPage_Loc(d);
	}

	@Test(description = "AutoID:IAL_01_001: Verify Login to Income Analysis Landing Page", priority = 1)
	public void login() throws Exception {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_001: Verify Login to Income Analysis Landing Page");
		System.out.println("=============================================================================================================================");
				
		PageParser.forceNavigate("Expense", d);		
		SeleniumUtil.waitForPageToLoad(5000);
	
		Assert.assertTrue(IncLandingPage.welcomeAnalysismessage().isDisplayed());
		
		SeleniumUtil.click(IncLandingPage.continuebtoon());
		SeleniumUtil.waitForPageToLoad(3000);
				
		Assert.assertTrue(IncLandingPage.linkSpendingAcctHeading().isDisplayed());
		
		SeleniumUtil.click(IncLandingPage.goToAnalysisBtn());
		SeleniumUtil.waitForPageToLoad(2000);	
		
		System.out.println("\n*********************************************** Testcase IAL_01_001 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_002: Verify that the toggle dropdown is displayed", priority = 2)	
	public void isToggleDropDownDisplayed() throws Exception {

		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_002: Verify that the toggle dropdown is displayed");
		System.out.println("=============================================================================================================================");
		
		Assert.assertTrue(IncLandingPage.DropDownIcon().isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_002 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_003: Verify Income Analysis in drop down", priority = 3)	
	public void verifyIncomeAnalysisInDropDown() throws Exception {

		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_003: Verify Income Analysis in drop down");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.click(IncLandingPage.DropDownIcon());
		Assert.assertTrue(IncLandingPage.IncomeAnalysisText().isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_003 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_004: Verify Income Analysis page", priority = 4)
	public void verifyIncomeAnalysisHeader() throws Exception {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_004: Verify Income Analysis page");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.click(IncLandingPage.IncomeAnalysisText());
		SeleniumUtil.waitForPageToLoad(5000);

		String header = IncLandingPage.IncomeAnalysisHeader().getText();
		Assert.assertEquals(header, PropsUtil.getDataPropertyValue("IA_LandingPage_Header"));
		
		System.out.println("\n*********************************************** Testcase IAL_01_004 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_005: Verify that Feature Tour button is displayed", priority = 5)	
	public void isFeatureTourButtonDisplayed() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_005: Verify that Feature Tour button is displayed");
		System.out.println("=============================================================================================================================");
				
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {			
			ExpLandingPage.MoreIconMobile().click();

		} else {
			ExpLandingPage.moreIconDropDown().click();
		}
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(IncLandingPage.IAfeatureTourButton().isDisplayed());
		SeleniumUtil.waitForPageToLoad(6000);
		
		System.out.println("\n*********************************************** Testcase IAL_01_005 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_006: Verify Feature Tour CM Drill Down By Categories", priority = 6)
	public void verifydrillDownByCategoriesCMHeading() {

		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_006: Verify Feature Tour CM Drill Down By Categories");
		System.out.println("=============================================================================================================================");
	
		SeleniumUtil.click(IncLandingPage.IAfeatureTourButton());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(IncLandingPage.drillDownByCatHeading().isDisplayed());
		Assert.assertTrue(IncLandingPage.clickCMNextButton().get(0).isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_006 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_007: Verify Feature Tour CM Average Line", priority = 7)
	public void verifyAverageLineCMHeading() {

		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_007: Verify Feature Tour CM Average Line");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.click(IncLandingPage.clickCMNextButton().get(0));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(IncLandingPage.averageLineHeading().isDisplayed());
		Assert.assertTrue(IncLandingPage.backButon(0).isDisplayed());
		Assert.assertTrue(IncLandingPage.clickCMNextButton().get(1).isDisplayed());

		System.out.println("\n*********************************************** Testcase IAL_01_007 passed ************************************************\n");
	}
	
	@Test(description = "AutoID:IAL_01_008: Verify Feature Tour CM Income Analysis", priority = 8)
	public void verifyExpenseOrIncomeAnalysisCM() {

		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_008: Verify Feature Tour CM Income Analysis");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.click(IncLandingPage.clickCMNextButton().get(1));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(IncLandingPage.incomeAnalysisFTCMHeading().isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_008 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_009: L1-42438:LMSpS: Verify Feature Tour CM Link All Your Accounts", priority = 9)
	public void verifyLinkAllYourAccountsCM() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_009: L1-42438:LMSpS: Verify Feature Tour CM Link All Your Accounts");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.click(IncLandingPage.clickCMNextButton().get(2));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(IncLandingPage.linkAllYourAccountFTCMHeading().isDisplayed());
		Assert.assertTrue(IncLandingPage.backButon(2).isDisplayed());
		Assert.assertTrue(IncLandingPage.clickCMNextButton().get(3).isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_009 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_010: Close Feature Tour CM", priority = 10)
	public void closeFeatureTourCM() {

		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_010: Close Feature Tour CM");
		System.out.println("=============================================================================================================================");
		
		IncLandingPage.clickCMNextButton().get(3).click();
		
		System.out.println("\n*********************************************** Testcase IAL_01_010 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_011: Verify clicking on month bar link takes user to Income Analysis by Category page", priority = 11)
	public void verifyRightSideMonthBarLink() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                AutoID:IAL_01_011: Verify clicking on month bar link takes user to Income Analysis by Category page");
		System.out.println("=============================================================================================================================");
		
		// Clicking on month on the right side box
		SeleniumUtil.click(IncLandingPage.monthbarlink()); 
		SeleniumUtil.waitForPageToLoad(10000);

		Assert.assertTrue(IncLandingPage.incomeByCategoryText().isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_011 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_012: L1-42486:LM: Verify that Add Transaction button is displayed", priority = 12)
	public void isAddTransButtonDisplayed() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_012: L1-42486:LM: Verify that Add Transaction button is displayed");
		System.out.println("=============================================================================================================================");
		
		Assert.assertTrue(IncLandingPage.Addtransactionlink_Mob().isDisplayed());
		
		System.out.println("\n*********************************************** Testcase IAL_01_012 passed ************************************************\n");
	}
	
	@Test(description = "AutoID:IAL_01_013: Verify All Income Accounts dropdown", priority = 13)
	public void validateAllIncomeAccountsDropdown() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_013: Verify All Income Accounts dropdown");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.scrollElementIntoView(d, IncLandingPage.allAccountsDropdown(), true);
		IncLandingPage.allAccountsDropdown().click();
		SeleniumUtil.waitForPageToLoad(10000);
		
		SeleniumUtil.scrollElementIntoView(d, IncLandingPage.allIncomeAcctsHeading(), true);
		Assert.assertTrue(IncLandingPage.allIncomeAcctsHeading().getText().equals(PropsUtil.getDataPropertyValue("IA_AllIncomeAcctDropdownHeading")));

		Assert.assertTrue(IncLandingPage.allAccountsCancelBtn().isDisplayed());
		Assert.assertTrue(IncLandingPage.allAccountsDoneBtn().isDisplayed());

		Assert.assertTrue(IncLandingPage.allAccountsFilter().isDisplayed());
		Assert.assertTrue(IncLandingPage.allAccountsFilter().getText().equals(PropsUtil.getDataPropertyValue("IA_AllIncomeAccounts")));
		
		Assert.assertTrue(IncLandingPage.allAccountsFilterCheckbox().isDisplayed());
			
		SeleniumUtil.click(IncLandingPage.allAccountsDoneBtn());
		SeleniumUtil.waitForPageToLoad();
		
		System.out.println("\n*********************************************** Testcase IAL_01_013 passed ************************************************\n");
	}
	
	@Test(description = "AutoID:IAL_01_014: Verify Month Filter dropdown", priority = 14)
	public void validateMonthFilterDropdown() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("                       AutoID:IAL_01_014: Verify Month Filter dropdown");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.scrollElementIntoView(d, IncLandingPage.allAccountsDateFilterDropdown(), true);
		IncLandingPage.allAccountsDateFilterDropdown().click();
		SeleniumUtil.waitForPageToLoad(10000);
	
		Assert.assertTrue(IncLandingPage.allIncomeAcctsHeading().getText().equalsIgnoreCase(PropsUtil.getDataPropertyValue("IA_AllIncomeMonthsDropdownHeading")));
		Assert.assertTrue(IncLandingPage.allAccountsThisMonth().isDisplayed());
		Assert.assertTrue(IncLandingPage.allAccountsMonthSelected().isDisplayed());
		
		List<WebElement> months = IncLandingPage.allMonthsList();
		
		boolean result = monthsList.size() == months.size() ? true : false;
		Assert.assertTrue(result);
		
		SeleniumUtil.click(IncLandingPage.closeMonthFilter());
		SeleniumUtil.waitForPageToLoad();
		
		System.out.println("\n*********************************************** Testcase IAL_01_014 passed ************************************************\n");
	}

	@Test(description = "AutoID:IAL_01_015: L1-42469:LMSp: Verify that Income Analysis Finapp should shows the data points for the current and the previous 2 months", priority = 15)
	public void allMonthsDataValidation() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("AutoID:IAL_01_015: L1-42469:LMSp: Verify that Finapp should shows the data points for current and previous 2 months");
		System.out.println("=============================================================================================================================");
		
		SeleniumUtil.click(IncLandingPage.backToIncomeLink_Mob());
		SeleniumUtil.waitForPageToLoad(7000);
			
		SeleniumUtil.scrollElementIntoView(d, IncLandingPage.allMonthsView(), true);
				
		List<WebElement> allMonths = IncLandingPage.allmonths_Mob();
		Assert.assertFalse(allMonths.isEmpty());

		int size = allMonths.size();
		assertThat(size, equalTo(Integer.parseInt(PropsUtil.getDataPropertyValue("IA_AllMonths_Count_Mob"))));
		
		System.out.println("\n*********************************************** Testcase IAL_01_015 passed ************************************************\n");
	}
	
	@Test(description = "AutoID:IAL_01_016: L1-42455:LMSpS: Verify that max length for amounts shown on screens should be <= 15", priority = 16)	
	public void maxLenthOfAmmount() {
		
		System.out.println("\n=============================================================================================================================");
		System.out.println("          AutoID:IAL_01_016: L1-42455:LMSpS: Verify that max length for amounts shown on screens should be <= 15");
		System.out.println("=============================================================================================================================");
		
		boolean result = IncLandingPage.amounttext().getText().length() <= 15 ? true : false;
		Assert.assertTrue(result);
		if (IncLandingPage.amounttext().getText().length() <= 15) {
			Assert.assertTrue(true);
		}
		else {
			Assert.assertTrue(false);
		}
		System.out.println("\n*********************************************** Testcase IAL_01_016 passed ************************************************\n");
	}

	public void goToExpIncAnalysis() {
		try {
			d.findElement(By.xpath("//div[@aria-label='Spending']")).click();
		} catch (Exception e) {
			SeleniumUtil.click(d.findElement(By.xpath("//div[@aria-label='Spending']")));
		}

		try {
			d.findElement(By.xpath("(//span[contains(text(),'Expense/Income Analysis')])[1]")).click();
		} catch (Exception e) {
			SeleniumUtil.click(d.findElement(By.xpath("(//span[contains(text(),'Expense/Income Analysis')])[1]")));
		}
	}
}
