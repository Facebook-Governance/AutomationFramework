/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Lavanya S
*/


package com.omni.pfm.Expense_IncomeAnalysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.IncLandingPage_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ExpenseAnalysisLandingPage_Test extends TestBase
{
	private static final Logger logger = LoggerFactory.getLogger(ExpenseAnalysisLandingPage_Test.class);

	ExpLandingPage_Loc ExpLandingPage;
	AccountAddition accAddition;
	ExpLandingPage_Loc expLandingPage;
	Add_Manual_Transaction_Loc manualTransaction;
	IncLandingPage_Loc incLandingPage;
	
	@BeforeClass(alwaysRun=true)

	public void init() throws Exception
	{
		doInitialization("Expense Analysis");

		ExpLandingPage = new ExpLandingPage_Loc(d);
		accAddition = new AccountAddition();
		expLandingPage = new ExpLandingPage_Loc(d);
		manualTransaction= new Add_Manual_Transaction_Loc(d);
		incLandingPage = new IncLandingPage_Loc(d);
		
		logger.info("***** User Registration and Login ******");
	    LoginPage.loginMain(d, loginParameter);
		
		logger.info("***** Adding Accounts ******");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();

		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("eAiA_dagSite"),PropsUtil.getDataPropertyValue("eAiA_dagSitePassword")));

		logger.info("***** Navigating to Expense Analysis ******");
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-81163,AT-81164,AT-81166:Verify FTUE CM heading.", priority = 2)

	public void verifyWelcomeCMHeading() throws Exception
	{
		logger.info("Verifying Welcome Heading Screen");
		Assert.assertTrue(ExpLandingPage.welcomeCoachMarkHeading().isDisplayed());

		String heading = ExpLandingPage.welcomeCoachMarkHeading().getText().trim();

		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EA_Welcome_CM_Heading"));
		logger.info("Verified Welcome Heading Screen");
	}

	@Test(description = "AT-81164:Verify FTUE CM description.",dependsOnMethods= {"verifyWelcomeCMHeading"}, priority = 3)

	public void verifyWelcomeCMDescription() throws Exception
	{
		logger.info("Verifying Welcome Heading Screen Description");
		String desc = PropsUtil.getDataPropertyValue("EA_Welcome_CM_desc").trim();

		String found = ExpLandingPage.welcomeCoachMarkDesc().getText().trim();

		Assert.assertEquals(desc, found);
		logger.info("Verified Welcome Heading Screen Description");
	}

	@Test(description = "AT-81167:Verify number of bullets in coachmark is two.",dependsOnMethods= {"verifyWelcomeCMDescription"}, priority = 4)

	public void verifyWelcomeCMNumOfBullets() 
	{
		logger.info("Verifying number of bullets in coachmark is two");
		logger.info("*** Verify that there are two navigation bullets below the CONTINUE button.");

		int size = ExpLandingPage.bulletInCoachMark().size();

		Assert.assertEquals(2, size);
		logger.info("Verified number of bullets in coachmark is two");
	}

	@Test(description = "AT-81167,AT-81172:Verify color of bullets in coachmark.", dependsOnMethods= {"verifyWelcomeCMNumOfBullets"},priority = 5)

	public void verifyWelcomeCMColorOfBullets()
	{
		logger.info("Verifying color of bullets in coachmark");
		int grey = 0;

		int blue = 0;

		List<WebElement> s = ExpLandingPage.bulletInCoachMark();

		for (int i = 0; i < s.size(); i++) 
		{
			String className = s.get(i).getAttribute("class");

			if (className.contains(PropsUtil.getDataPropertyValue("EACM_blueColor"))) 
			{
				blue = blue + 1;
			}
			else if (className.contains(PropsUtil.getDataPropertyValue("EACM_greyColor"))) 
			{
				grey = grey + 1;
			}
		}

		Assert.assertTrue(blue == 1 && grey == 1);
		logger.info("Verified color of bullets in coachmark");
	}

	@Test(description = "AT-81164:Verify CONTINUE button is displayed on Welcome CM and click on it.", dependsOnMethods= {"verifyWelcomeCMNumOfBullets"}, priority = 6)

	public void verifyWelcomeCMContinueButton() throws IndexOutOfBoundsException 
	{
		logger.info("Verifying CONTINUE button is displayed on Welcome CM");
		Assert.assertTrue(ExpLandingPage.continueButton().isDisplayed());
		logger.info("Verified CONTINUE button is displayed on Welcome CM");
	}

	@Test(description = "AT-81165,AT-81168:Verify welcome CM LINK ACCOUNT heading.", dependsOnMethods= {"verifyWelcomeCMContinueButton"}, priority = 7)

	public void verifyWelcomeCMLinkAccHeading()
	{
		logger.info("Verifying Welcome CM LINK ACCOUNT heading");
		SeleniumUtil.click(ExpLandingPage.continueButton());

		SeleniumUtil.waitForPageToLoad();

		String CMHtext = ExpLandingPage.linkAccountWelcomeCMHeading().getText();

		Assert.assertEquals(CMHtext, PropsUtil.getDataPropertyValue("EACM_Link_Account_Heading"));
		logger.info("Verified Welcome CM LINK ACCOUNT heading");
	}

	@Test(description = "AT-81165:Verify welcome CM LINK ACCOUNT description.", dependsOnMethods= {"verifyWelcomeCMLinkAccHeading"}, priority = 8)

	public void verifyWelcomeCMLinkAccDescription() 
	{
		logger.info("Verifying Welcome CM LINK ACCOUNT Description");
		String decription = ExpLandingPage.getEAWelcomeCMDescription();

		Assert.assertEquals(decription, PropsUtil.getDataPropertyValue("EACM_Link_Account_Description"));
		logger.info("Verified Welcome CM LINK ACCOUNT Description");
	}

	@Test(description = "AT-81169:Verify welcome CM link account button is present.", dependsOnMethods= {"verifyWelcomeCMLinkAccDescription"}, priority = 9)

	public void verifyWelcomeCMLinkAccButtonIsDisplayed()
	{
		logger.info("Verifying Welcome CM LINK ACCOUNT button is present");
		Assert.assertTrue(ExpLandingPage.CMlinkAccountLink().isDisplayed());
		
		SeleniumUtil.click(ExpLandingPage.gotoAnalysisBtn());
		logger.info("Verified Welcome CM LINK ACCOUNT button is present");
	}
	
	@Test(description = "Finish coach mark.", dependsOnMethods= {"verifyWelcomeCMLinkAccButtonIsDisplayed"}, priority = 10)

	public void finishFTUEScreen()
	{	
		//SeleniumUtil.click(ExpLandingPage.gotoAnalysisBtn());
		logger.info("Verified Welcome CM LINK ACCOUNT button is present");
		
		Boolean isCMDisplayed = false;
		try {
			if (ExpLandingPage.gotoAnalysisBtn().isDisplayed()) {
				isCMDisplayed = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Assert.assertFalse(isCMDisplayed);
			
	}	

	@Test(description = "AT-81170,AT-81171,AT-81176:Verify Drill Down By Categories coachmark heading.", dependsOnMethods= {"finishFTUEScreen"}, priority = 11)

	public void verifydrillDownByCategoriesCMHeading() throws Exception
	{
		logger.info("Verifying Drill Down By Categories coachmark heading");
		SeleniumUtil.refresh(d);
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.moreIconDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(ExpLandingPage.featureTourBtn());

		String heading = ExpLandingPage.getCMHeadingText(1).trim();
		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EA_Drill_Down_By_Categories_Heading"));
		
		logger.info("Verified Drill Down By Categories coachmark heading");
	}

	@Test(description = "AT-81180,AT-81374:Verify Drill Down By Categories coachmark close button is displayed.", dependsOnMethods = {"verifydrillDownByCategoriesCMHeading" },priority = 12)

	public void verifyDrillDownByCategoriesCMCloseButton() throws Exception 
	{
		logger.info("Verifying Drill Down By Categories coachmark close button is displayed");
		Assert.assertTrue(ExpLandingPage.cmCloseButton(1).isDisplayed());
		logger.info("Verified Drill Down By Categories coachmark close button is displayed");
	}

	@Test(description = "AT-81173,AT-81177:Verify Average Line coach mark heading.", dependsOnMethods = {"verifydrillDownByCategoriesCMHeading" }, priority = 13)

	public void verifyAverageLineCMHeading() throws Exception
	{
		logger.info("Verifying Average Line coach mark heading is displayed");
		SeleniumUtil.waitForPageToLoad(2000);
		
		ExpLandingPage.clickCMNextButton(1); // Clicking on CM NEXT button

		SeleniumUtil.waitForPageToLoad();

		String heading = ExpLandingPage.getCMHeadingText(2);

		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EA_AverageLine_Heading"));
		logger.info("Verified Average Line coach mark heading ");
	}

	@Test(description = "AT-81177:Verify Average Line coach mark description.", dependsOnMethods = {"verifyAverageLineCMHeading" }, priority = 14)

	public void verifyAverageLineCMDescription() throws Exception 
	{
		logger.info("Verifying Average Line coach mark heading description");
		String desc = ExpLandingPage.getCMBodyContent(2);

		Assert.assertEquals(desc, PropsUtil.getDataPropertyValue("EA_AverageLine_Description"));
		logger.info("Verified Average Line coach mark heading description");
	}

	@Test(description = "AT-81180:Verify Average Line coach mark close button is displayed.", dependsOnMethods = {"verifydrillDownByCategoriesCMHeading" }, priority = 15)

	public void verifyAverageLineCMCloseButton() throws Exception
	{
		Assert.assertTrue(ExpLandingPage.cmCloseButton(2).isDisplayed()); //Verifying the close button is displayed in the coach mark.
	}

	@Test(description = "AT-81174,AT-81175:Verify clicking on Average Line coach mark back button takes to CM 1.", dependsOnMethods = {"verifyAverageLineCMHeading" }, priority = 16)

	public void verifyAverageLineCMBackButton() throws Exception
	{
		ExpLandingPage.clickCMBackButton(1);

		SeleniumUtil.waitForPageToLoad();

		String heading = ExpLandingPage.getCMHeadingText(1);

		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EA_Drill_Down_By_Categories_Heading"));
	}

	@Test(description = "AT-81175,AT-81178:Verify the functionality of Expense or Income Analysis, 3rd Coach Mark.", dependsOnMethods = {"verifyAverageLineCMBackButton" }, priority = 17)

	public void verifyExpenseOrIncomeAnalysisCM() throws Exception 
	{
		ExpLandingPage.clickCMNextButton(1);

		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.clickCMNextButton(2);

		SeleniumUtil.waitForPageToLoad();

		logger.info("*** Verify Expense or Income Analysis CM heading");

		String heading = ExpLandingPage.getCMHeadingText(3);

		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EACM_Expense_or_Income_Analysis_Heading"));

		String desc = ExpLandingPage.getCMBodyContent(3);

		Assert.assertEquals(desc, PropsUtil.getDataPropertyValue("EACM_Expense_or_Income_Analysis_Description"));

		Assert.assertTrue(ExpLandingPage.cmCloseButton(3).isDisplayed());
	}

	@Test(description = "AT-81174:Verify the functionality of Expense or Income Analysis back button, 3rd Coach Mark.", dependsOnMethods = {"verifyExpenseOrIncomeAnalysisCM" }, priority = 18)

	public void verifyExpenseOrIncomeAnalysisCMBackButton() throws Exception 
	{
		logger.info("*** Verify tapping back button on Expense or Income Analysis coach mark takes user to Average Line coach mark");

		ExpLandingPage.clickCMBackButton(2);

		SeleniumUtil.waitForPageToLoad();

		String heading = ExpLandingPage.getCMHeadingText(2);

		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EA_AverageLine_Heading"));
	}

	@Test(description = "AT-81179:Verify Link All Your Accounts Coach Mark heading.", dependsOnMethods = {"verifyExpenseOrIncomeAnalysisCMBackButton" }, priority = 19)

	public void verifyLinkAllYourAccountsCMHeading() throws Exception 
	{

		ExpLandingPage.clickCMNextButton(2); // Clicking first NEXT button

		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.clickCMNextButton(3); // Clicking second NEXT button

		SeleniumUtil.waitForPageToLoad();

		String heading = ExpLandingPage.getCMHeadingText(4);

		Assert.assertEquals(heading, PropsUtil.getDataPropertyValue("EACM_Link_All_Your_Accounts_Heading"));

		String desc = ExpLandingPage.getCMBodyContent(4);

		Assert.assertEquals(desc, PropsUtil.getDataPropertyValue("EACM_Link_All_Your_Accounts_Desc"));

		ExpLandingPage.clickCMNextButton(4);

	}
	
	@Test(description = "Navigating to Expense Analysis finapp.", priority = 20)

	public void navigateToExpenseAnalysis() throws Exception 
	{

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
	}
	
	

	@Test(description = "AT-81229:Verify page is Expense by Category and High Level Category is displayed. L1-42223,L1-42224", dependsOnMethods= {"navigateToExpenseAnalysis"}, priority = 21)

	public void verifyExpByCategoryHeaderAndHighLevelCatIsDisplayed() throws InterruptedException
	{

		logger.info("Verify that Expense Analysis by Category FinApp should have the Expense High Level Categories.");

		ExpLandingPage.verifyClickChart(PropsUtil.getDataPropertyValue("chartValue"));

		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(ExpLandingPage.expensebyCategory().isDisplayed());
		
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(ExpLandingPage.highLevelCategory().isDisplayed());

	}

	@Test(description = "AT-81229:Verify Master level category is displayed.", dependsOnMethods = {"verifyExpByCategoryHeaderAndHighLevelCatIsDisplayed" }, priority = 22)

	public void verifyMasterLevelCategoryIsDispalayed() throws InterruptedException 
	{
		logger.info("*** Verify that Expense Analysis Finapp should have the Master Level Categories.");

		ExpLandingPage.highLevelCategory().click();

		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(ExpLandingPage.masterCategory().isDisplayed());
	}

	@Test(description = "AT-81295:Verifying navigating back to Expense Analysis from Category.", dependsOnMethods = {"verifyMasterLevelCategoryIsDispalayed" }, priority = 23)

	public void navigatingBackToEA() throws InterruptedException 
	{
		SeleniumUtil.waitForPageToLoad(1000);
		
		boolean backToEBC = ExpLandingPage.backToExpenseCategory().isDisplayed();
		
		SeleniumUtil.click(ExpLandingPage.backToExpenseCategory());
		
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(backToEBC);
	}
	
	@Test(description = "Navigating to Expense analaysis if back button did not work.", priority = 24)

	public void navigateToExpenseAnalysis1() throws InterruptedException 
	{
		PageParser.forceNavigate("Expense", d);

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-81388:L1-42225-UI Verify toggling on Income/Expense Analysis dropdown for IA.", dependsOnMethods= {"navigateToExpenseAnalysis1"}, priority = 25)

	public void verifyEaIaDropDownForIA() throws InterruptedException
	{		

		logger.info("*** User should be able to choose the Expense/Income Analysis by clicking on the symbol 'V' beside the header.");

		SeleniumUtil.click(ExpLandingPage.eAiADropDown());

		SeleniumUtil.waitForPageToLoad(2000);
			
		ExpLandingPage.selectExpenseIncomeDropDown(PropsUtil.getDataPropertyValue("IA_LandingPage_Header")).click();
	
		SeleniumUtil.waitForPageToLoad(3000);
	
		Assert.assertEquals(ExpLandingPage.IATitleText().getText(), PropsUtil.getDataPropertyValue("IA_Header"));
	}

	@Test(description = "AT-81183:L1-42225-UI Verify toggling on Income/Expense Analysis dropdown for EA.", dependsOnMethods= {"navigateToExpenseAnalysis1"}, priority = 26)

	public void verifyEaIaDropDownForEA() throws InterruptedException 
	{
		SeleniumUtil.click(ExpLandingPage.eAiADropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		
		ExpLandingPage.selectExpenseIncomeDropDown(PropsUtil.getDataPropertyValue("ExpenseHeader")).click();
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("The Expense heading is: " + ExpLandingPage.heading().getText());

		Assert.assertEquals(ExpLandingPage.heading().getText(), PropsUtil.getDataPropertyValue("EA_Header"));	
	}

	@Test(description = "AT-81184:Verify in EaIa dropdown both text and symbol are clickable.", dependsOnMethods= {"navigateToExpenseAnalysis1"}, priority = 27)

	public void verifyEaIaDropDownForBoth() throws InterruptedException
	{
        boolean status = ExpLandingPage.eAiADropDown().isDisplayed();
		
		SeleniumUtil.click(ExpLandingPage.eAiADropDown());

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.selectExpenseIncomeDropDownIcon("svg_icon-move-up"));

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.eAiADropDown());

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(ExpLandingPage.selectExpenseIncomeDropDownIcon("svg_icon-move-down"));
		
		Assert.assertTrue(status);
	}

	@Test(description = "AT-81192:Verify chart data for Expense Analysis.", dependsOnMethods = {"navigateToExpenseAnalysis1" }, priority = 28)

	public void verifyChartDataForEA() throws InterruptedException {

		String currMonthColor = ExpLandingPage.verifySelectFromChart("rect", PropsUtil.getDataPropertyValue("chartValue")).getCssValue("fill");

		logger.info("Current month Color is:" + currMonthColor);

		Assert.assertEquals(currMonthColor, PropsUtil.getDataPropertyValue("EA_CurrentMonthColor"));

		logger.info("***  Other bars in the chart should displayed in dark red color.");

		if(PropsUtil.getDataPropertyValue("chartValue").equals(PropsUtil.getDataPropertyValue("valueFromChart")))
		{

			logger.info("5th month Color is:" + ExpLandingPage.selectingFromChart("rect", 5).getCssValue("fill"));
	
			String fifthMonthColor = ExpLandingPage.selectingFromChart("rect", 5).getCssValue("fill");
	
			Assert.assertEquals(fifthMonthColor, PropsUtil.getDataPropertyValue("EA_LastMonthColor"));
		}

		logger.info("*** Dotted line should be displayed on the bars of the chart.");

		Assert.assertTrue(ExpLandingPage.dottedLine().isDisplayed());

		logger.info("*** Average amount legend should be displayed below the chart.");

		Assert.assertTrue(ExpLandingPage.averageAmt().isDisplayed());

		logger.info("*** In the X-Axis, Months data should be displayed.");
		
		if(PropsUtil.getDataPropertyValue("chartValue").equals(PropsUtil.getDataPropertyValue("valueFromChart")))
		{

			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 1).isDisplayed());
	
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 2).isDisplayed());
	
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 3).isDisplayed());
	
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 4).isDisplayed());
	
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 5).isDisplayed());
	
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 6).isDisplayed());
		}
		else
		{
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 1).isDisplayed());
			
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 2).isDisplayed());
	
			Assert.assertTrue(ExpLandingPage.selectingFromChart("text", 3).isDisplayed());
	
		}

		logger.info("*** In the Y-Axis, Amount range should be displayed.");

		logger.info("Amt on Y Axis is" + ExpLandingPage.YAxisAmt(8, 1).getText());

		Assert.assertTrue(ExpLandingPage.YAxisAmt(8, 1).isDisplayed());

		Assert.assertTrue(ExpLandingPage.YAxisAmt(8, 2).isDisplayed());

	}

	@Test(description = "AT-81193:L1-42226,L1-42231,L1-42232,L1-42233,L1-42234,L1-42235,L1-42236,L1-42237", dependsOnMethods= {"navigateToExpenseAnalysis1"}, priority = 29)

	public void verifyPreviousMonthData() throws InterruptedException 
	{
		SeleniumUtil.waitForPageToLoad(1000);

		logger.info("Verify that bar should not be displayed if user doesn't have the expense data for any of the previous month.");

		Assert.assertTrue(ExpLandingPage.selectingFromChart("rect", 2).isDisplayed());
	}

	@Test(description = "AT-81194:Verify EA Fin app shows the data points for current month and the previous 5 months.", dependsOnMethods= {"navigateToExpenseAnalysis1"}, priority = 30)

	public void verifyDataPoints() throws InterruptedException 
	{
		if(PageParser.isElementPresent("EADataPointsForMobile", "Expense", null))
		{
			for (int j = 0; j < 2; j++) {

				Assert.assertTrue(ExpLandingPage.DataPoint().get(j).isDisplayed());

				String monthName = ExpLandingPage.Month().get(j).getText();

				logger.info("Text is:" + monthName);

				logger.info("Text 2 is:" + ExpLandingPage.get_Prvious6MonthName(j));

				Assert.assertTrue(ExpLandingPage.get_Prvious6MonthName(j).equalsIgnoreCase(monthName));
			}
		}
		else
		{
			for (int j = 0; j < 6; j++) {
	
				Assert.assertTrue(ExpLandingPage.DataPoint().get(j).isDisplayed());
	
				String monthName = ExpLandingPage.Month().get(j).getText();
	
				logger.info("Text is:" + monthName);
	
				logger.info("Text 2 is:" + ExpLandingPage.get_Prvious6MonthName(j));
	
				Assert.assertTrue(ExpLandingPage.get_Prvious6MonthName(j).equalsIgnoreCase(monthName));
			}
		}
	}

	@Test(description = "AT-81225,AT-81226,AT-81228:Verify that User should be able to click on bars in the Chart and it takes them to Expense by Category page.", dependsOnMethods= {"navigateToExpenseAnalysis1"}, priority = 31)

	public void verifyClicksOnChartBars() throws InterruptedException 
	{

		logger.info("*** Verify that User should be able to click on any bars in the Chart.");

		ExpLandingPage.verifyClickChart(PropsUtil.getDataPropertyValue("chartValue"));

		SeleniumUtil.waitForPageToLoad();

		logger.info("*** Verify that user should be taken to the Expenses by Category Screen on clicking/tapping on the bars from the chart.");

		Assert.assertTrue(ExpLandingPage.expensebyCategory().isDisplayed());

	}
	
	@Test(description = "Navigate to Expense Analysis from Category screen.", priority = 32)

	public void navigateToExpenseAnalysis2() throws InterruptedException 
	{

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
	}

	//As the tool tip value couldn't get it from UI, disabling this test method
	@Test(description = "EAL_01_29: L1-42237,L1-42238,L1-42239", priority = 29, dependsOnMethods= {"navigateToExpenseAnalysis2"}, enabled = false)

	public void VerifyUI() throws InterruptedException
	{
		
		logger.info("*** Verify that data represented in the legend, provides the actual values of total expenses for each month same as shown in the chart.");

		SeleniumUtil.click(ExpLandingPage.verifySelectFromChart("rect", PropsUtil.getDataPropertyValue("chartValue")));  // 6 for Web and 3 for Mobile

		SeleniumUtil.waitForPageToLoad();

		String val1 = ExpLandingPage.getAmountLandingPAge(0).getText();

		logger.info(val1);

		String amount = ExpLandingPage.tooltip();
		
		logger.info(amount);

		Assert.assertEquals(val1, amount);

	}

	@Test(description = "AT-81233:L1-42240-UI-AddTxnAndLnkAccountBtn", dependsOnMethods= {"navigateToExpenseAnalysis2"}, priority = 33)

	public void verifyAddTxnAndLnkAccountBtn() throws InterruptedException 
	{
		logger.info("Verifying that user should be able to view Add a Transaction and print option under more in the Expenses by Category Screen.");
		if(PageParser.isElementPresent("addTransactionBtn_mob", "Expense", null))
		{
			Assert.assertTrue(ExpLandingPage.addTransactionBtn_mob().isDisplayed());
		}
		else
		{
			Assert.assertEquals(ExpLandingPage.addTransactionLabel().getText().trim(),PropsUtil.getDataPropertyValue("EA_AddTransactionLabel").trim());
	
			if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt"))
			{
				SeleniumUtil.click(ExpLandingPage.moreButtonForPrint());
				SeleniumUtil.waitForPageToLoad();
			}
	
			Assert.assertTrue(ExpLandingPage.printBlock().isDisplayed());
		}
		logger.info("Verified that user should be able to view Add a Transaction and print option under more in the Expenses by Category Screen.");
	}

	@Test(description = "AT-81239:Verify All Account drop down. L1-42240-UI", dependsOnMethods = {"verifyAddTxnAndLnkAccountBtn" }, priority = 34)

	public void verifyAllAccountDropDown() throws InterruptedException
	{
		ExpLandingPage.verifyClickChart(PropsUtil.getDataPropertyValue("chartValue")); SeleniumUtil.waitForPageToLoad();	Assert.assertEquals(ExpLandingPage.allAccDropDownBlock().getText().trim().toLowerCase(),PropsUtil.getDataPropertyValue("EA_AllAccDropDownLabel").toLowerCase().trim());
		
		SeleniumUtil.click(ExpLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		
		logger.info("Verifying that the Account groups in the Expense Analysis finapp is not displayed as the account groups are not created");
		Assert.assertNull(ExpLandingPage.verifyEAAccountGpTab());
		logger.info("Verified that the Account groups in the Expense Analysis finapp is not displayed as the account groups are not created");
	}

	@Test(description = "AT-81239:Creating three groups for EA. NA for BBT.", dependsOnMethods= {"navigateToExpenseAnalysis2"}, priority = 35)

	public void createTestGroupsForEA() 
	{
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) 
		{
			// Click on settings
			SeleniumUtil.click(ExpLandingPage.settingsMenu());
			SeleniumUtil.waitForPageToLoad();

			// Click on account groups
			SeleniumUtil.click(ExpLandingPage.accountGroup());
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Creating the Account Groups....");
			boolean status = ExpLandingPage.createAccountGroupButton().isDisplayed();

			ExpLandingPage.createGroupForEA(PropsUtil.getDataPropertyValue("EA_Group1"));
			ExpLandingPage.createGroupForEA(PropsUtil.getDataPropertyValue("EA_Group2"));
			ExpLandingPage.createGroupForEA(PropsUtil.getDataPropertyValue("EA_Group3"));
			
			logger.info("Account Groups are created...");
			
			PageParser.forceNavigate("Expense", d);
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(ExpLandingPage.verifySelectFromChart("rect", PropsUtil.getDataPropertyValue("chartValue")));
			SeleniumUtil.waitForPageToLoad();
			
			Assert.assertTrue(status);
		}
		
	}

	@Test(description = "AT-81240:Verify the Group names in Drop down. L1-42240-UI", dependsOnMethods = {"createTestGroupsForEA" }, priority = 36)

	public void verifyGroupNamesInDropDown() throws InterruptedException 
	{
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) 
		{
			logger.info("***  User should be able to view the account groups in the All Accounts drop down.");
	
			ExpLandingPage.allAccDropDownBlock().click();
	
			SeleniumUtil.waitForPageToLoad();
	
			ExpLandingPage.getAccountsAndGroups();
	
			SeleniumUtil.waitForPageToLoad();
	
			logger.info("Verifying the account Groups present in the All Accounts DD");
			Assert.assertEquals(ExpLandingPage.getGroups(PropsUtil.getDataPropertyValue("EA_Group1")).getText(),PropsUtil.getDataPropertyValue("EA_Group1"));
			Assert.assertEquals(ExpLandingPage.getGroups(PropsUtil.getDataPropertyValue("EA_Group2")).getText(),PropsUtil.getDataPropertyValue("EA_Group2"));
			Assert.assertEquals(ExpLandingPage.getGroups(PropsUtil.getDataPropertyValue("EA_Group3")).getText(),PropsUtil.getDataPropertyValue("EA_Group3"));
			logger.info("Verified the account Groups present in the All Accounts DD");
		}
	}

	@Test(description = "AT-81404:Verify Time filter drop down is present.", dependsOnMethods= {"navigateToExpenseAnalysis2"}, priority = 37)

	public void verifyTimeFilterDropDown() throws InterruptedException 
	{
		if(PageParser.isElementPresent("doneButton", "Expense", null))
		{ SeleniumUtil.click(ExpLandingPage.doneButton()); }
		Assert.assertTrue(ExpLandingPage.timeFilterDropdown().isDisplayed());
	}

	@Test(description = " AT-81404:Verify Time filter drop down contents. L1-42240-UI", dependsOnMethods = {"verifyTimeFilterDropDown" }, priority = 38)

	public void verifyTimeFilterOne() throws InterruptedException
	{
		SeleniumUtil.click(ExpLandingPage.timeFilterDropdown());

		logger.info("*** By default This Month Time Filter is displayed.");

		Assert.assertEquals(ExpLandingPage.timeFilter().getText(), PropsUtil.getDataPropertyValue("EA_TimeFilter1"));
	}

	@Test(description = "AT-81404:Verify Time filter drop down contents : Two. L1-42240-UI", dependsOnMethods = {"verifyTimeFilterOne" }, priority = 39)

	public void verifyTimeFilterTwo() throws InterruptedException
	{
		logger.info("Date Range should be displayed for the selection of the time filter.");

		for (int i = 0; i < ExpLandingPage.getDateFormat().size(); i++)
		{
			Assert.assertTrue(ExpLandingPage.getDateFormat().get(i).isEnabled());
		}
		if(PageParser.isElementPresent("closeButtonForMobile", "Expense", null))
		{ SeleniumUtil.click(ExpLandingPage.closeButton()); }
		else
		{ SeleniumUtil.click(ExpLandingPage.timeFilterDropdown()); }
	}

	@Test(description = "AT-81288:Verify Dounut block is present. L1-42241-UI", dependsOnMethods = {"verifyTimeFilterTwo" }, priority = 40)

	public void verifyDounutChartIsDisplayed() throws InterruptedException 
	{
		Assert.assertTrue(ExpLandingPage.dounutBlock().isDisplayed());
	}

	@Test(description = "AT-81404:Verify Dounut block. L1-42241-UI", dependsOnMethods = {"verifyDounutChartIsDisplayed" }, priority = 41)

	public void verifyDounutChart() throws InterruptedException
	{
		logger.info("Dates chosen should be displayed on the screen in mm/dd/yyyyformat(Co brandable).");

		Assert.assertEquals(ExpLandingPage.dateFormat().getText(), ExpLandingPage.getFirstDayOfTheMonthAndTodayDate());

		for (int j = 1; j < ExpLandingPage.getListOfMonth().size(); j++) 
		{
			Assert.assertTrue(ExpLandingPage.getListOfMonth().get(1).isDisplayed());
		}
	}

	@Test(description = "AT-81196,AT-81200 : EAL_01_39: L1-42242-42243  Verify that in expense analysis the average shown in the bar chart in average of 3 last months expense",dependsOnMethods= {"verifyDounutChart"}, priority = 42)

	public void verifyDifferenceBetweenEA() throws InterruptedException 
	{
		logger.info("Verifying difference in expenses amount and the average amount for last 3 months excluding current month");
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(ExpLandingPage.backToExpenseAnalysisInCategory());
		
		SeleniumUtil.waitForPageToLoad();

		float actualAverage = 0.0f;

		float threeMonthTotal = 0.0f;

		for (int i = 1; i < 4; i++) 
		{
			// To get the Difference in Amount

			String amt = ExpLandingPage.getAmount().get(i).getText();

			threeMonthTotal = threeMonthTotal + CommonUtils.parseAmountExpense(amt);
		}

		logger.info("Three month total excluding current month is: " + threeMonthTotal);

		String[] avg = ExpLandingPage.averageAmt().getText().split(": ");

		actualAverage = CommonUtils.parseAmountExpense(avg[1]);

		logger.info("Average amt is:" + actualAverage);

		float expectedAverage = threeMonthTotal / 3;

		DecimalFormat decimalFormat = new DecimalFormat("###");

		String expected = decimalFormat.format(expectedAverage);

		String actual = decimalFormat.format(actualAverage);

		Assert.assertEquals(actual, expected);
		
		logger.info("Verified difference in expenses amount and the average amount for last 3 months excluding current month");
	}
	
	@Test(description = "Navigate to Exp.", priority = 43)

	public void navigateToExpenseAnalysis3() throws InterruptedException 
	{
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-81240:L1-42261,42262,42264,42265,42266,42267,42268,42269,42270",dependsOnMethods= {"navigateToExpenseAnalysis3"}, priority = 44)

	public void verifyAlphabeticalOrderOfGroups() throws InterruptedException 
	{
		logger.info("*** Verify that user created groups are displayed in alphabetical order Verify that Account groups names in the Expense Analysis Finapp after creating the account groups");
		
		

		ExpLandingPage.verifyClickChart(PropsUtil.getDataPropertyValue("chartValue"));

		SeleniumUtil.waitForPageToLoad();
		
		if (!Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {

			ExpLandingPage.allAccDropDownBlock().click();
	
			SeleniumUtil.waitForPageToLoad();
	
			ExpLandingPage.getAccountsAndGroups();
	
			SeleniumUtil.waitForPageToLoad();
	
			String[] container = new String[] { PropsUtil.getDataPropertyValue("EA_Group1"),PropsUtil.getDataPropertyValue("EA_Group2"), PropsUtil.getDataPropertyValue("EA_Group3") };
	
			for (int i = 0; i < container.length; i++) 
			{
	
				String containerName1 = ExpLandingPage.getGroups1(i).getText();
	
				logger.info("Actual name :" + containerName1);
	
				Assert.assertEquals(containerName1, container[i]);
	
				List<String> list1 = new ArrayList<String>();
	
				list1.add(PropsUtil.getDataPropertyValue("EA_Group1"));
	
				list1.add(PropsUtil.getDataPropertyValue("EA_Group2"));
	
				list1.add(PropsUtil.getDataPropertyValue("EA_Group3"));
	
				Collections.sort(list1);
	
				logger.info("Sorting the Groups in Alphabetical order "+ list1);
			}
		}

	}

	@Test(description = "AT-81242:Verify options under Time filter.",dependsOnMethods= {"navigateToExpenseAnalysis3"}, priority = 45)

	public void verifyTimeFilterRanges() throws InterruptedException 
	{
        PageParser.forceNavigate("Expense", d);
		
		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.clickChart(6);

		SeleniumUtil.waitForPageToLoad();
		logger.info("*** Verify that the following time filters should be available for selection : This Month, etc.");
		
		if(PageParser.isElementPresent("doneButton", "Expense", null))
		{ SeleniumUtil.click(ExpLandingPage.doneButton()); }
		SeleniumUtil.click(ExpLandingPage.timeFilter());

		for (int i = 0; i < ExpLandingPage.getDateFormat().size() - 1; i++)
		{
			String timeFilter = PropsUtil.getDataPropertyValue("EA_DateFilter");

			String[] timeText = timeFilter.split(",");

			logger.info("The date is:" + ExpLandingPage.getDateFormat().get(i).getText());

			Assert.assertEquals(ExpLandingPage.getDateFormat().get(i).getText(), timeText[i]);
		}

	}

	@Test(description = "AT-81207:Verify the default Time Filter option. L1-42261-42262,42264,42265,42266,42267,42268,42269,42270", dependsOnMethods= {"verifyTimeFilterRanges"}, priority = 46)

	public void verifyDefaultSelection() throws InterruptedException 
	{
		logger.info("*** Verify that This Month time filter should be selected – if the user clicks on the current month in the summary view.");

		ExpLandingPage.getDateFormat().get(0).click();

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(ExpLandingPage.dateFormat().getText(), ExpLandingPage.getFirstDayOfTheMonthAndTodayDate());
	}

	@Test(description = "AT-81209:L1-42261-42262,42264,42265,42266,42267,42268,42269,42270", dependsOnMethods= {"verifyDefaultSelection"}, priority = 47)
	
	public void verifyCustomTimeRangeFilter() throws InterruptedException 
	{
		logger.info("*** Verify that Custom Date Range time filter should be selected – if the user clicks on any month before the previous month in the summary view");

		try {SeleniumUtil.click(ExpLandingPage.timeFilterDropdown());} 
		catch (Exception e) {ExpLandingPage.timeFilterDropdown().click();}

		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.getDateFormat().get(7).click();

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(ExpLandingPage.dateRangeHeaderCutomRange().getText(),PropsUtil.getDataPropertyValue("EA_DateRangeHeader"));
	}

	@Test(description = "AT-81208:Verify Last Month time filter in the range drop down. L1-42261-42262,42264,42265,42266,42267,42268,42269,42270", dependsOnMethods= {"verifyCustomTimeRangeFilter"}, priority = 48)

	public void verifyLastMonthTimeFilter() throws InterruptedException 
	{
		try {SeleniumUtil.click(ExpLandingPage.closeCutomRangePopUp());}
		catch (Exception e) {ExpLandingPage.closeCutomRangePopUp().click();}

		logger.info("Verify that Last Month time filter should be selected if the user clicks on the last month in the summary view Verify that on changing the time filter, screen should be refreshed to show data matching the filter.");

		if(PageParser.isElementPresent("closeButtonForMobile", "Expense", null))
		{
			SeleniumUtil.click(ExpLandingPage.closeButton()); 
			SeleniumUtil.waitForPageToLoad(1000);
		}
		ExpLandingPage.timeFilterDropdown().click();

		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.getDateFormat().get(1).click();

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(ExpLandingPage.dateFormat().getText(), ExpLandingPage.getLastMonthStartEndDate());
	}

	@Test(description = "AT-81210,AT-81439:Verify Last Month time filter is applied to all pages. L1-42261-42262,42264,42265,42266,42267,42268,42269,42270", dependsOnMethods= {"verifyLastMonthTimeFilter"}, priority = 49)

	public void verifyLastMonthTimeFilterInAllPages() throws InterruptedException
	{
		logger.info("*** Verify that time filter specified in the screen should be applied to all further screens except the Trends screen");

		SeleniumUtil.click(ExpLandingPage.selectExpenseCategory()); // Clicking on high level category. ( Home expenses )

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(ExpLandingPage.dateHeading().getText(), ExpLandingPage.getLastMonthStartEndDate());
		
		//Added code for Trends Screen
		
        ExpLandingPage.clickingTrends();
		
		List <WebElement> trendsAmts = ExpLandingPage.verifyTrendsPopUpAmount();
		String trendsAmts1 = trendsAmts.get(0).getText().trim();
		String trendsAmts2 = trendsAmts.get(1).getText().trim();
		
		SeleniumUtil.click(ExpLandingPage.closeTrendsPopUp());
		SeleniumUtil.waitForPageToLoad(2000);
			
		SeleniumUtil.click(ExpLandingPage.verifyBackBtnForEBC());
		SeleniumUtil.waitForPageToLoad(3000);
		
		ExpLandingPage.selectingTimeFilterForTrends(5);
		
		List <WebElement> trendAmount = ExpLandingPage.verifyTrendsPopUpAmount();
		String expectedTrendsAmts1 = trendAmount.get(0).getText().trim();
		String expectedTrendsAmts2 = trendAmount.get(1).getText().trim();
		
		SeleniumUtil.click(ExpLandingPage.closeTrendsPopUp());
		SeleniumUtil.waitForPageToLoad(2000);
		
		logger.info("Verifying that time filter specified in the screen should be applied to all further screens except trends screen.");
		Assert.assertEquals(trendsAmts1, expectedTrendsAmts1); //The values should be same before and after selecting the time filter as it should not affect in Trends
		Assert.assertEquals(trendsAmts2, expectedTrendsAmts2);
		logger.info("Verified that accounts filter specified in the screen should be applied to all further screens except trends screen.");
	
	}

	/*@Test(description = "EAL_01_47: Verify date range after navigating back to Expense by Category page.", dependsOnMethods= {"loginAndAccountAddition","verifyLastMonthTimeFilterInAllPages"}, priority = 47)

	public void verifyTimeRangeAfterTrends() throws InterruptedException {

		PageParser.forceNavigate("Expense", d);
		
		SeleniumUtil.waitForPageToLoad();

		logger.info("*** Verify that data represented in the legend, provides the actual values of total expenses for each month same as shown in the chart.");

		SeleniumUtil.click(ExpLandingPage.verifySelectFromChart("rect", 5));

		SeleniumUtil.waitForPageToLoad(7000);

		Assert.assertEquals(ExpLandingPage.dateFormat().getText(), ExpLandingPage.getLastMonthStartEndDate());

	}*/

	@Test(description = "AT-81211:L1-42281,42282,42283,42284,42285,42286,42287,42288,42289,42290", enabled=false, priority = 50)

	public void verifyToolTipData() throws InterruptedException 
	{
		PageParser.forceNavigate("Expense", d);
		
		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.verifyClickChart(PropsUtil.getDataPropertyValue("chartValue"));

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(ExpLandingPage.tooltipHighLevelCategory(),ExpLandingPage.getAmountLandingPAge(2).getText());
	}

	@Test(description = "AT-81455,AT-81198:L1-42281,42282,42283,42284,42285,42286,42287,42288,42289,42290", priority = 51)

	public void verifyTabularData() throws InterruptedException
	{
		PageParser.forceNavigate("Expense", d);
		
		SeleniumUtil.waitForPageToLoad();

		ExpLandingPage.verifyClickChart(PropsUtil.getDataPropertyValue("chartValue"));

		SeleniumUtil.waitForPageToLoad();
		
		logger.info("***  Verify that the tabular data shows the amounts and percentages");

		for (int i = 0; i < ExpLandingPage.getPercentageHighLevelCategory().size(); i++) {

			Assert.assertTrue(ExpLandingPage.getAmountHLLandingPAge(i).isDisplayed());

			Assert.assertTrue(ExpLandingPage.getPercentageHighLevelCategory().get(i).getText()
					.contains(PropsUtil.getDataPropertyValue("EA_PercentSign")));

			logger.info("*** Verify that the percentages should be rounded off to whole numbers.");

			Assert.assertFalse(ExpLandingPage.getPercentageHighLevelCategory().get(i).getText()
					.contains(PropsUtil.getDataPropertyValue("EA_Dot")));

			logger.info("***  Verify that the tabular data should be in descending order(highest first)");

		}
	}
	
	// Merging the second class below
	
	@Test(description = "Navigate to Expense analsysis again.", priority = 52)

	public void navigateToExpenseAnalysis4() throws InterruptedException {
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
	}
	
	@Test(description = "AT-81190,AT-81290:Verify that High level and Master level categories should be renamed by the user from Manage Categories",dependsOnMethods= {"navigateToExpenseAnalysis4"}, priority = 53)

	public void verifyRenamingOfHlcAndMlcInCategories() throws Exception
	{
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verifying that High level and Master level categories should be renamed by the user from Manage Categories");
		Assert.assertTrue(expLandingPage.renamingOfHLCInCategories_Exp());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(expLandingPage.renamingOfMLCInCategories_Exp());
		logger.info("Verified that High level and Master level categories are renamed by the user from Manage Categories");
	}
	
	@Test(description = "AT-81191,AT-81218,AT-81290,AT-81296,AT-81293,AT-81335:Verify that the renamed High Level and Master Levl categories should refelct in Expenses Analysis", dependsOnMethods={"verifyRenamingOfHlcAndMlcInCategories"}, priority = 54)

	public void verifyHlcAndMlcInExpense() throws Exception
	{
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Adding Manual Transaction for General Merchandise Category!!!");
		logger.info("EA by Master Category Screen - Verify that tapping + icon or Add Transaction opens the transaction addition window");
		SeleniumUtil.click(expLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("1000", "Testing", 1, 2, 0, 0, 1, 7, PropsUtil.getDataPropertyValue("TransactionNoteForExpense"), PropsUtil.getDataPropertyValue("TransactionCheckForExpense") );
		
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Verifying that current month's bar is displayed in chart if transactions are available for the current month");
		expLandingPage.clickChart(6); 
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verified that current month's bar is displayed in chart as the transactions are available for the current month");
		
		logger.info("Verifying Renamed HLC is reflecting in Expense By Category Screen.");
		Assert.assertTrue(expLandingPage.verifyHLCInExpenseByCategory().isDisplayed());
		logger.info("Renamed HLC is reflected in Expense By Category Screen.");
		
		SeleniumUtil.click(expLandingPage.verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		
		logger.info("Verifying Renamed MLC is reflecting in Expense By Category Screen.");
		Assert.assertTrue(expLandingPage.verifyMLCInExpenseByCategory().isDisplayed());
		logger.info("Renamed MLC is reflected in Expense By Category Screen.");
		
		logger.info("EA by Master Category Screen - Verifying that the High Level Category name displayed in header is same as selected in Expenses by Category screen");
		String hlcHeader = expLandingPage.finappHeader_HLCHeader().getText().trim();
		Assert.assertEquals(hlcHeader, PropsUtil.getDataPropertyValue("ExpectedHlcHeader_Expense"));
		logger.info("EA by Master Category Screen - Verified that the High Level Category name displayed in header is same as selected in Expenses by Category screen");
	}
	
	@Test(description = "Navigate to Expense Analysis.", priority = 55)

	public void navigateToExpenseAnalysis5() throws Exception
	{
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
	}
	
	@Test(description = "AT-81193:Verify that bar should not be displayed if user doesn't have the expense data for any of the previous month",dependsOnMethods= {"navigateToExpenseAnalysis5"}, priority = 56)

	public void verifyTheBarGraphsOfExpData() throws Exception
	{		
		WebElement barGraph = expLandingPage.verifyBarGraph(5);
		logger.info("Verifying that bar should be displayed if user have the expense data for any of the previous month");
		Assert.assertTrue(barGraph.isDisplayed());
		logger.info("Verified that bar should be displayed if user have the expense data for the previous month.");
		
		WebElement barGraph1 = expLandingPage.verifyBarGraph(4);
		logger.info("Verifying that bar should be displayed if user have the expense data for Two Months ago.");
		Assert.assertTrue(barGraph1.isDisplayed());
		logger.info("Verified that bar should be displayed if user have the expense data for Two Months ago..");
	}
	
	@Test(description = "AT-81207,AT-81208,AT-81209:Verify that Custom dates time filter should be selected if the user clicks on any of the month other than current and last month",dependsOnMethods= {"navigateToExpenseAnalysis5"}, priority = 57)

	public void verifyCustomDatesFilter() throws Exception
	{
		expLandingPage.clickChart(4);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verifying that Custom dates time filter should be selected if the user clicks on Bar graph of Two Months ago.");
		Assert.assertTrue(expLandingPage.verifyCustomDates().isDisplayed());
		logger.info("Verified that Custom dates time filter should be selected if the user clicks on Bar graph of Two Months ago.");
		
		SeleniumUtil.click(expLandingPage.backToExpenseCategory());
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(5);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verifying that Custom dates time filter should Not be selected if the user clicks on last month");
		Assert.assertTrue(expLandingPage.verifyLastMonth().isDisplayed());
		logger.info("Verified that Custom dates time filter should Not be selected if the user clicks on last month");
		
		SeleniumUtil.click(expLandingPage.backToExpenseCategory());
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verifying that Custom dates time filter should Not be selected if the user clicks on Current month");
		Assert.assertTrue(expLandingPage.verifyCurrentMonth().isDisplayed());
		logger.info("Verified that Custom dates time filter should Not be selected if the user clicks on Current month");
	}
	
	@Test(description = "AT-81202,AT-81217,AT-81406,AT-81407:Verify that change % and amount is not displayed in table on right for the current month if today is not the last day of the month", priority = 58)

	public void verifyPerAndAmountInTableOfCurrMonth() throws Exception
	{
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		String lastDateOfTheCurrMonth = DateUtil.getLastDateOfMonth(PropsUtil.getDataPropertyValue("DateFormat"), 0);
		logger.info("Last day of the Current Month is " + lastDateOfTheCurrMonth);
		
		String currentDateOfTheCurrMonth = DateUtil.getDate(PropsUtil.getDataPropertyValue("DateFormat"), 0);
		logger.info("Current Day of the Current Month is " + currentDateOfTheCurrMonth);
		
		if(lastDateOfTheCurrMonth.equals(currentDateOfTheCurrMonth))
		{
			logger.info("Verifying that change % and amount is DISPLAYED in table on right for the current month if today is the last day of the month");
			Assert.assertNull(expLandingPage.verifyCurrentMonthsPerAndAmtInTable());//It returns Null value as the Amt and % is Visible.
			
			List<WebElement> valueOfAmtAndPer = expLandingPage.verifyCurrentMonthsPerAndAmtInEATable();
			String actualText = valueOfAmtAndPer.get(0).getText().trim();
			logger.info("The Value of Amt and Percentage is invisible for the current month if today is the last day of the month is " + actualText);
			Assert.assertTrue(actualText.contains(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth")));
			logger.info("Verified that change % and amount is DISPLAYED in table on right for the current month if today is the last day of the month");
		}
		else
		{
			logger.info("Verifying that change % and amount is NOT displayed in table on right for the current month if today is not the last day of the month");
			Assert.assertNotNull(expLandingPage.verifyCurrentMonthsPerAndAmtInTable()); //The change % and amount is invisible. Verifying the Invisibility, hence the value is not Null. 
			Assert.assertFalse(expLandingPage.verifyCurrentMonthsPerAndAmtInTable().isDisplayed());
			logger.info("Verified that change % and amount is NOT displayed in table on right for the current month if today is not the last day of the month");
		}
		
		logger.info("************* Verifying For INCOME Analysis ************");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		incLandingPage.selectIA();
		
		if(lastDateOfTheCurrMonth.equals(currentDateOfTheCurrMonth))
		{
			logger.info("Verifying that change % and amount is DISPLAYED in table on right for the current month if today is the last day of the month for INCOME");
			Assert.assertNull(expLandingPage.verifyCurrentMonthsPerAndAmtInTable());//It returns Null value as the Amt and % is Visible.
			
			List<WebElement> valueOfAmtAndPer = expLandingPage.verifyCurrentMonthsPerAndAmtInEATable();
			String actualText = valueOfAmtAndPer.get(0).getText().trim();
			logger.info("The Value of Amt and Percentage is invisible for the current month if today is the last day of the month for INCOME is " + actualText);
			Assert.assertTrue(actualText.contains(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth")));
			logger.info("Verified that change % and amount is DISPLAYED in table on right for the current month if today is the last day of the month for INCOME");
		}
		else
		{
			logger.info("Verifying that change % and amount is NOT displayed in table on right for the current month if today is not the last day of the month for INCOME");
			Assert.assertNotNull(expLandingPage.verifyCurrentMonthsPerAndAmtInTable()); //The change % and amount is invisible. Verifying the Invisibility, hence the value is not Null. 
			Assert.assertFalse(expLandingPage.verifyCurrentMonthsPerAndAmtInTable().isDisplayed());
			logger.info("Verified that change % and amount is NOT displayed in table on right for the current month if today is not the last day of the month for INCOME");
		}
			
	} 
	
	@Test(description = "AT-81221,AT-81235:Verify that after selecting account in account filter the EA finaap should refresh to show the data of the account with account filter open.", priority = 59)

	public void verifyAccountsFilterOfEA() throws Exception
	{
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		expLandingPage.clickChart(5);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		
		logger.info("Verifying that by default All Accounts filter should be selected");
		Assert.assertEquals(expLandingPage.verifyAccountsDDCheckBox().getAttribute("aria-checked"),"true");
		logger.info("Verified that by default All Accounts filter should be selected");
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDDCheckBox());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("Verifying that after selecting account in account filter the EA finaap should refresh to show the data of the account with account filter open.");
		Assert.assertTrue(expLandingPage.verifyAccountsDDIsOpened().isDisplayed());
		Assert.assertTrue(expLandingPage.verifyEAWithNoData().isDisplayed());
		logger.info("Verified that after selecting account in account filter the EA finaap should refresh to show the data of the account with account filter open.");
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDDCheckBox());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(expLandingPage.verifyAccountsDDIsOpened().isDisplayed());
		Assert.assertTrue(expLandingPage.verifyEAWithData().isDisplayed());
		SeleniumUtil.click(expLandingPage.verifyAccountsDD());
	}
	
	//EBC: Expense By Category
	@Test(description = "AT-81236:Verify that user should be able to select list of accounts or account group.",dependsOnMethods= {"navigateToExpenseAnalysis5"}, priority = 60)

	public void creatingAnAcctGpAndNavigatingToEBC() throws Exception
	{
		logger.info("Creating an Account Group");
		Assert.assertTrue(expLandingPage.createAccountGroup());
		logger.info("Account Group is created!!!");
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(5);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(expLandingPage.verifyEAWithData().isDisplayed());
		
		List<WebElement> listOfAccounts = expLandingPage.verifyEAListOfAccounts();
		for(WebElement element : listOfAccounts)
		{
			logger.info("DeSelecting the checkBox of each Accounts");
			SeleniumUtil.click(element);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		Assert.assertTrue(expLandingPage.verifyEAWithNoData().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);
	}
	
	@Test(description = "AT-81236,AT-81239:Verify that user should be able to select list of accounts or account group.", dependsOnMethods={"creatingAnAcctGpAndNavigatingToEBC"}, priority = 61)

	public void verifyListOfAcctsAndAccGps() throws Exception
	{	
		List<WebElement> selectingListOfAccounts = expLandingPage.verifyEAListOfAccounts();
		for(WebElement element : selectingListOfAccounts)
		{
			logger.info("Selecting the checkBox of each Accounts");
			SeleniumUtil.click(element);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		Assert.assertTrue(expLandingPage.verifyEAWithData().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("Verified that user should be able to select list of accounts....");
		
		SeleniumUtil.click(expLandingPage.verifyEAAccountGp());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(expLandingPage.verifyEAAccountGpTab().isDisplayed());
		logger.info("Verified that Account Group Tab is present");
		
		SeleniumUtil.click(expLandingPage.verifyEAAccounts());
		SeleniumUtil.waitForPageToLoad(2000);	
	}

	@Test(description = "AT-81241:Verify the Account groups in the Expense Analysis finapp after deleting the account group", dependsOnMethods={"creatingAnAcctGpAndNavigatingToEBC"}, priority = 62)

	public void deleteAccGpAndVerifyInEBC() throws Exception
	{
		logger.info("Deleting the GROUP A Account Group..");
		//Assert.assertTrue(expLandingPage.deleteAccountGp(PropsUtil.getDataPropertyValue("AccountGroupForEA")));
		Assert.assertTrue(expLandingPage.deleteAccountGp());
		logger.info("Deleted the GROUP A account group!!!");
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(5);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		
		expLandingPage.getAccountsAndGroups();
		SeleniumUtil.waitForPageToLoad(2000);
		
		logger.info("Verifying that the GROUP A Account group in the Expense Analysis finapp is not displayed after deleting the account group");
		Assert.assertNull(expLandingPage.verifyGpAAcctGp());
		logger.info("Verified that the GROUP A Account group in the Expense Analysis finapp is not displayed after deleting the account group");
	}
	
	@Test(description = "AT-81237:Verify that user should be able to view the manual accounts in the All Accounts dropdown list", priority = 63)

	public void verifyManualAccountsInAccFilter() throws Exception
	{
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Adding Manual Account for Cards Container.");
		accAddition.addManualAccount("Cards", "Manual card account", null, "20000", null, "3445", "date", null);
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(4);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
		
		logger.info("Verifying that user should be able to view the manual accounts in the All Accounts dropdown list");
		Assert.assertTrue(expLandingPage.verifyManualAccInAccFilter().isDisplayed());
		logger.info("Verified that user should be able to view the manual accounts in the All Accounts dropdown list");
		
		SeleniumUtil.click(expLandingPage.verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
	}
	
	@Test(description = "AT-81244:Verify that if custom dates given exceeds more than 12 months error message should be displayed", dependsOnMethods={"verifyManualAccountsInAccFilter"}, priority = 64)

	public void verifyCustomFilterErrorMsgs() throws Exception
	{
		SeleniumUtil.click(expLandingPage.timeFilterDropdown());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(expLandingPage.selectCustomDateFilter());
		SeleniumUtil.waitForPageToLoad(3000);
		
		String moreThanTwelveMonthsDate = DateUtil.getDate(PropsUtil.getDataPropertyValue("DateFormat"), 370);
		logger.info("The End Date for Custom Date Filter is " + moreThanTwelveMonthsDate);
		
		SeleniumUtil.click(expLandingPage.selectToDate());
		SeleniumUtil.waitForPageToLoad(3000);
		expLandingPage.selectToDate().clear();
		expLandingPage.selectToDate().sendKeys(moreThanTwelveMonthsDate);
		SeleniumUtil.click(expLandingPage.updateBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		
		String actualText = expLandingPage.customFilterErrorMsg().getText().trim();
		logger.info("Actual Text of Custom Filter Error Message for more than 12 Months is "+ actualText);
		
		logger.info("Verifying that if custom dates given exceeds more than 12 months error message should be displayed");
		Assert.assertEquals(actualText,PropsUtil.getDataPropertyValue("ExpectedTextForCustomFilterErrMsg").trim());
		logger.info("Verified that if custom dates given exceeds more than 12 months error message should be displayed");
	}
	
	@Test(description = "AT-81246,AT-81248:Verify that Custom date range End Date should not be greater than Today", dependsOnMethods={"verifyCustomFilterErrorMsgs"}, priority = 65)

	public void verifyCustomFilterErrorMsgForFutureDate() throws Exception
	{	
		String futureDate = DateUtil.getDate(PropsUtil.getDataPropertyValue("DateFormat"), 2);
		logger.info("The End Date for Custom Date Filter is " + futureDate);
		
		SeleniumUtil.click(expLandingPage.selectToDate());
		SeleniumUtil.waitForPageToLoad(3000);
		expLandingPage.selectToDate().clear();
		expLandingPage.selectToDate().sendKeys(futureDate); //Verify that user should be able to enter the custom date range physically
		SeleniumUtil.click(expLandingPage.updateBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		
		String actualText = expLandingPage.customFilterErrorMsg().getText().trim();
		logger.info("Actual Text of Custom Filter Error Message for Future Date is "+ actualText);
		
		logger.info(" Verifying that Custom date range End Date should not be greater than Today");
		Assert.assertEquals(actualText,PropsUtil.getDataPropertyValue("ExpectedTextForCustomFilterErrMsgForFutureDate").trim());
		logger.info(" Verified that Custom date range End Date should not be greater than Today");
	}
	
	@Test(description = "AT-81257:Verify that Uncategorized Transactions header should be there below the donut chart in the EA Finapp ", dependsOnMethods={"verifyCustomFilterErrorMsgForFutureDate"}, priority = 66)

	public void verifyUnCategorizedTranHeader() throws Exception
	{
		SeleniumUtil.click(expLandingPage.closeCustomFilter());
		SeleniumUtil.waitForPageToLoad(2000);
		
		String actualTextOfUncategorized = expLandingPage.verifyUnCategorizedTrans().getText().trim();
		logger.info("Actual Text of Categories Header in Expense By Category page is " + actualTextOfUncategorized);
		
		logger.info("Verifying that Uncategorized Transactions header is displayed in the EA Finapp(Expense By Category)");
		Assert.assertEquals(actualTextOfUncategorized,PropsUtil.getDataPropertyValue("ExpectedCategoryHeaderInEA").trim());
		logger.info("Verified that Uncategorized Transactions header is displayed in the EA Finapp(Expense By Category)");
	}
	
	@Test(description = "AT-81262,AT-81263,AT-81284:Verify that you can check and uncheck the categories available in filter by categories.", priority = 67)

	public void verifyCheckAndUnCheckTheCategories() throws Exception
	{
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(4);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		expLandingPage.deselectingTheCheckBoxesOfCategories();
		logger.info("Unchecked the categories available in filter by categories");
		
		String disabled = expLandingPage.verifyEAFilterCategoriesDoneBtn().getAttribute("class").trim();
		Assert.assertTrue(disabled.contains(PropsUtil.getDataPropertyValue("EACategoryFilterDoneButtonValue").trim()));
		logger.info("After UnChecking all the Categories, Done Button is disabled!!!");
		
		expLandingPage.selectingTheCheckBoxesOfCategories();
		logger.info("Checked/Selected the categories available in filter by categories");
		
		Assert.assertTrue(expLandingPage.verifyEAFilterCategoriesDoneBtn().isEnabled());
		logger.info("After Checking/Selecting all the Categories, Done Button is enabled!!!");
	}
	
	@Test(description = "AT-81264:Verify that cancel and done buttons are avilable and clickable in filter by categories", dependsOnMethods={"verifyCheckAndUnCheckTheCategories"}, priority = 68)

	public void verifyCancelAndDoneBtnsOfCategories() throws Exception
	{
		logger.info("Verifying that cancel and done buttons are avilable and clickable in filter by categories");
		Assert.assertTrue(expLandingPage.verifyEAFilterCategoriesDoneBtn().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		Assert.assertTrue(expLandingPage.verifyEAFilterCategoriesCancelBtn().isDisplayed());
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategoriesCancelBtn());
		logger.info("Verified that cancel and done buttons are avilable and clickable in filter by categories");
	}
	
	@Test(description = "AT-81268:Verify that the label should change from All categories included to number categories included after selecting the specific categories.", dependsOnMethods={"verifyCancelAndDoneBtnsOfCategories"}, priority = 69)

	public void verifyNoOfCategoriesIncluded() throws Exception
	{
		logger.info("Verifying that the label All categories included is displayed.");
		String actualAllCategHeader = expLandingPage.verifyAllCategoriesHeader().getText().trim();
		logger.info("Actual text of All Categories Included Header in Expense By Category is "+ actualAllCategHeader);
		Assert.assertEquals(actualAllCategHeader,PropsUtil.getDataPropertyValue("ExpectedTextOfAllCategIncluded").trim());
		logger.info("Verified that the label All categories included is displayed.");
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		List<WebElement> noOfCategories = expLandingPage.verifyEAFilterCategoriesCheckBoxes();
		logger.info("Number of Categories ====> " + noOfCategories.size());
		
		expLandingPage.deselectingTheOddNoCheckBoxesOfCategories();
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		
		logger.info("Verifying that the label should change from All categories included to number categories included after selecting the specific categories.");
		String actualNoOfCategoriesIncluded = expLandingPage.verifyAllCategoriesHeader().getText().trim();
		
		SeleniumUtil.waitForPageToLoad(2000);
		expLandingPage.selectingTheOddNoOfCheckBoxes();
		
		if(noOfCategories.size()==5)
		{
		    Assert.assertEquals(actualNoOfCategoriesIncluded,PropsUtil.getDataPropertyValue("ExpectedTextOfNoOfCategIncluded").trim());
		}
		else
		{
			Assert.assertEquals(actualNoOfCategoriesIncluded,PropsUtil.getDataPropertyValue("ExpectedTextOfNoOfCategoriesIncluded").trim());
		}
		logger.info("Verified that the label should change from All categories included to number categories included after selecting the specific categories.");	
	}
	
	@Test(description = "AT-81273,AT-81310:EA by Expense by Category Screen - Verify that the expenses in Legends section(Amounts) are displayed in descending order", priority = 70)

	public void verifyAmtsAreInDescendingOrder() throws Exception
	{
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(4);
		SeleniumUtil.waitForPageToLoad();
		
		double value1 = 0.0, decimal = 0.0;
		
		List<WebElement> actualAmount = expLandingPage.verifyEAAmtsInEBC();
		for(WebElement we : actualAmount)
		{
			String actualAmt = we.getText().trim().replaceAll(",", "");
			logger.info("Actual Value of Amounts in Expense By Category are " + actualAmt);
			
			actualAmt=actualAmt.substring(actualAmt.indexOf(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth"))+1, actualAmt.length());
			
			double value = Double.parseDouble(actualAmt);
			logger.info("Actual Value of Amounts(Converting from String to Double) in Expense By Category are " + value);	
			
			if(value1!=decimal)
			{
				if(value1 > value)
				{
					Assert.assertTrue(true);
					logger.info("Verified that the expense Amounts in Legends section are displayed in descending order");
				}
				else
				{
					logger.error("Verified that the expense Amounts in Legends section are NOT displayed in descending order!!!");
					Assert.assertTrue(false);
				}
			}
			
		   value1=value;
		}
	}
	
	@Test(description = "AT-81285,AT-81319,AT-81334,AT-81342,AT-81347:Verify that when a user edits a transaction and clicks on save button transaction(s) updated message should display" , dependsOnMethods={"verifyAmtsAreInDescendingOrder"}, priority = 71)

	public void verifyTransUpdatedToastMsg() throws Exception
	{
		SeleniumUtil.click(expLandingPage.verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		
		List<WebElement> editTrans = expLandingPage.verifyTransInEBCOfMLC();
		SeleniumUtil.click(editTrans.get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		
		SeleniumUtil.click(expLandingPage.verifyShowMoreOptionsLink());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(expLandingPage.verifyNoteTextArea());
		SeleniumUtil.waitForPageToLoad(2000);
		expLandingPage.verifyNoteTextArea().clear();
		expLandingPage.verifyNoteTextArea().sendKeys(PropsUtil.getDataPropertyValue("TransactionNoteForExpense"));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(expLandingPage.editTransSaveBtnInEBC());
		SeleniumUtil.waitForPageToLoad(2000);
		
		String transUpdateMsg = expLandingPage.verifyTransUpdateMsg().getText().trim();
		logger.info("Actual text of Transaction Updated Message is " + transUpdateMsg);
		
		Assert.assertEquals(transUpdateMsg, PropsUtil.getDataPropertyValue("ExpectedTransUpdateMsg"));		
	}
	
	@Test(description = "AT-81220,AT-81319:Verify the data in expense finapp after splitting the transactions", priority = 72 )

	public void verifySplitTransInEBC() throws Exception
	{
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Splitting the Transactions of Merchandiseee Category in Transaction...");
		Assert.assertTrue(expLandingPage.splittingTransactions());
		SeleniumUtil.waitForPageToLoad(3000);
		
		logger.info("Verifying the Splitted Transactions Amounts in Transaction");
		List<WebElement> splitAmounts = expLandingPage.verifySplitAmounts();
		String actualAmt1 = splitAmounts.get(3).getText().trim();
		logger.info("The first splitted amount in Transaction is " + actualAmt1);
		
		String actualAmt2 = splitAmounts.get(4).getText().trim();
		logger.info("The second splitted amount in Transaction is " + actualAmt2);
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		expLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		
		SeleniumUtil.click(expLandingPage.verifyMLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		
		List<WebElement> splitAmts = expLandingPage.verifySplitAmounts();
		logger.info("Verifying the Splitted Transactions Amounts in Expense");
		String expectedAmt1 = splitAmts.get(3).getText().trim();
		logger.info("The first splitted amount in Expense is " + expectedAmt1);

		String expectedAmt2 = splitAmts.get(4).getText().trim();
		logger.info("The second splitted amount in Expense is " + expectedAmt2);
		
		logger.info("Verifying the data in expense finapp after splitting the transactions");
		Assert.assertEquals(actualAmt1,expectedAmt1);
		Assert.assertEquals(actualAmt2,expectedAmt2);
		logger.info("Verified the data in expense finapp after splitting the transactions");
	}
	
	@Test(description = "AT-81185:Verify that only posted and cleared transactions should be included.", priority = 73)

	public void verifyPostedOrClearedTrans() throws Exception
	{
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Adding Manual Transaction for General Merchandise Category for Pending Transactions!!!");
		SeleniumUtil.click(expLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("1000", "Pending Transaction Testing", 1, 2, 0, 20, 1, 7, PropsUtil.getDataPropertyValue("TransactionNoteForExpense"), PropsUtil.getDataPropertyValue("TransactionCheckForExpense") );
		
		logger.info("Verifying the Pending Transactions in Transaction FinApp....");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.verifyPendingTransactionInTrans();
		logger.info("Added a Pending Transaction and Verifying it in EA!!!");
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(expLandingPage.verifyMLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("Verifying the Pending Transactions should not be displayed in the EBC");
		Assert.assertNull(expLandingPage.verifyPendingTransInEBC());
		logger.info("Verified the Pending Transactions should not be displayed in the EBC");
		
		logger.info("Verifying that only posted and cleared transactions should be included.");
		Assert.assertTrue(expLandingPage.verifyPostedTrans().isDisplayed());
		logger.info("Verified that only posted and cleared transactions should be included.");	
	}
	
	@Test(description = "AT-81259,AT-81294:Verify that if there are no Uncategorized Transactions then NO Data message should be displayed Or Verify that if there are no Uncategorized Transactions then All your transactions have been categorized message should be displayed", dependsOnMethods={"verifyPostedOrClearedTrans"}, priority = 74 )

	public void verifyNoUnCatTrans() throws Exception
	{
		SeleniumUtil.click(expLandingPage.verifyBackBtnForEBC()); //EA by Master Category Screen - Verify that tapping back arrow takes user to Expenses by Category screen
		SeleniumUtil.waitForPageToLoad(3000);
		
		logger.info("Verifying that there are NO Uncategorized Transactions");
		Assert.assertTrue(expLandingPage.verifyNoUnCategorizedData().isDisplayed());
		
		String actual = expLandingPage.verifyNoUnCategorizedDataText().getText().trim();
		logger.info("Actual Text of there are no Uncategorized Transaction is ===> "+ actual);		
		
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("ExpectedNoDataForUnCat"));
		logger.info("Verified that there are NO Uncategorized Transactions");
	}
	
	@Test(description = "AT-81286,AT-81294,AT-81297,AT-81303:Verify that accounts filter specified in the screen should be applied to all further screens except trends screen.", dependsOnMethods={"verifyPostedOrClearedTrans"}, priority = 75)

	public void verifyAcctFilterForTrends() throws Exception
	{	
		logger.info("EA by Master Category Screen - Verify that tapping Trend icon or Trend text opens Trend popup");
		expLandingPage.selectingAllExpAcctsForTrends();
		
		List <WebElement> trendsAmts = expLandingPage.verifyTrendsPopUpAmount();
		String trendsAmts1 = trendsAmts.get(0).getText().trim();
		String trendsAmts2 = trendsAmts.get(1).getText().trim();
		
		logger.info("EA by Master Category Screen - Verifying that tapping the close button in Trend popup closes the popup and takes user to EA by Master Category screen");
		SeleniumUtil.click(expLandingPage.closeTrendsPopUp());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(expLandingPage.verifyBackBtnForEBC().isDisplayed());
		logger.info("EA by Master Category Screen - Verified that tapping the close button in Trend popup closes the popup and takes user to EA by Master Category screen");
		
		SeleniumUtil.click(expLandingPage.verifyBackBtnForEBC());
		SeleniumUtil.waitForPageToLoad(3000);
		
		expLandingPage.selectingAnAccountForTrends(5);
		
		List <WebElement> trendAmount = expLandingPage.verifyTrendsPopUpAmount();
		String expectedTrendsAmts1 = trendAmount.get(0).getText().trim();
		String expectedTrendsAmts2 = trendAmount.get(1).getText().trim();
		
		logger.info("Verifying that accounts filter specified in the screen should be applied to all further screens except trends screen.");
		Assert.assertEquals(trendsAmts1, expectedTrendsAmts1); //The values should be same before and after selecting the account as it should not affect by selecting/deselecting the accounts in Trends
		Assert.assertEquals(trendsAmts2, expectedTrendsAmts2);
		logger.info("Verified that accounts filter specified in the screen should be applied to all further screens except trends screen.");
	}
	
	@Test(description = "AT-81255,AT-81289:Verify that if there are no transactions in some High(Expense by category)  Level Categories then the High Level Categories should not be displayed", priority = 76)

	public void verifyNoTransInHLC() throws Exception
	{
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Adding Manual Transaction for Charitable Giving Category!!!");
		SeleniumUtil.click(expLandingPage.addTransactionLabel());
		SeleniumUtil.waitForPageToLoad();
		manualTransaction.createOneTimeTransaction("10000", "TestingHLC", 1, 6, 0, 0, 3, 2, PropsUtil.getDataPropertyValue("TransactionNoteForExpense"), PropsUtil.getDataPropertyValue("TransactionCheckForExpense") );
		
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		expLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expLandingPage.verifyHLCTransInExpense().isDisplayed());
		
		logger.info("Deleting the Transaction added for Charitable Giving HLC...");
		expLandingPage.deleteHLCTransForEBCVerification();
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		expLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Verify that if there are no transactions in some High(Expense by category)  Level Categories then the High Level Categories should not be displayed");
		Assert.assertNull(expLandingPage.verifyHLCTransInExpense());
		logger.info("Verified that if there are no transactions in some High(Expense by category)  Level Categories then the High Level Categories should not be displayed");
	}
	
	@Test(description = "AT-81262,AT-81266:Verify that selection of the categories should be retained if the user drills down further and comes back to the Expenses By Category screen.", dependsOnMethods={"verifyNoTransInHLC"}, priority = 77)

	public void verifyCategoriesAreRetained() throws Exception
	{
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(expLandingPage.selectHLCInFilterCategories());
		SeleniumUtil.waitForPageToLoad(1000);
		
		SeleniumUtil.click(expLandingPage.verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertNull(expLandingPage.verifyHLCInEBC_Categories());
		
		List<WebElement> hlc = expLandingPage.selectHLC_Categories();
		SeleniumUtil.click(hlc.get(0));
		
		SeleniumUtil.click(expLandingPage.verifyBackBtnForEBC());
		SeleniumUtil.waitForPageToLoad(3000);
		
		logger.info("Verifying that selection of the categories should be retained if the user drills down further and comes back to the Expenses By Category screen.");
		Assert.assertNull(expLandingPage.verifyHLCInEBC_Categories());
		logger.info("Verified that selection of the categories should be retained if the user drills down further and comes back to the Expenses By Category screen.");
	}
	
	@Test(description = "AT-81201,AT-81202,AT-81270,AT-81271,AT-81272,AT-81307,AT-81308,AT-81309:EA by Expense by Category Screen - Verify that the percentage distribution in Legends section is correct with respect to the corresponding expense amounts. "
			+ "Verify that the percentage is rounded off to a whole number. Verify that if the percentage comes to a figure between 0 and 1, it is rounded off to 1%", priority = 78)

	public void verifyPercentageDistribution() throws Exception
	{
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		int pos = expLandingPage.avgAmount().getText().indexOf(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth"));
		Float avg_amt = Float.parseFloat(expLandingPage.avgAmount().getText().substring(pos+1).replaceAll(",", ""));
		
		for (int i=1;i<expLandingPage.verifyMonthlyAmtForEAAndIA().size();i++) 
		{	
			Float monthly_change = (float) expLandingPage.round(Math.abs(expLandingPage.monthlyAmt(i)-avg_amt),2);
			logger.info("Difference between Monthly Expense and Avg Amount in Legend Section is  =====================>"+monthly_change);
			Assert.assertEquals(expLandingPage.monthlyChange(i), monthly_change);
			int monthly_change_percent = (int) expLandingPage.round((monthly_change/avg_amt)*100,0);
			logger.info("Change in Percentage in Legend Section of Expense is  =====================>"+monthly_change_percent);
			Assert.assertEquals(expLandingPage.monthlyChangePercent(i), monthly_change_percent); 
		}	
	}
	
	@Test(description = "AT-81299,AT-81300,AT-81301,AT-81302,AT-81341:Verify data in Trends popup. Verify that the trend graph and table displays last 6 months data everytime it is accessed and has no interaction with the time and accounts EA filter"
			+ "Verify that the AVERAGE; amount displayed below the Trend chart is the average of last 3 months expenses as displayed in Trend table"
			+ "Verify that the Currency type in Preference Page and the trends popup in EA are same(by the user prefered currency symbol)", priority = 79)
	public void verifyTrendsData() throws Exception {
		
		String currency = expLandingPage.verifyUserPreferredCurrency();
		
		logger.info("Currency in Preference Page is  ===================> "+currency);
		
		SeleniumUtil.click(expLandingPage.legends().get(1));
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.highLevelCategory_EA().get(0));
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expLandingPage.MoreButton());
		SeleniumUtil.click(expLandingPage.trendMenu());
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("Verifying that the trend graph and table displays last 6 months data everytime it is accessed and has no interaction with the time and accounts EA filter");
		Assert.assertEquals(expLandingPage.trendMonths().size(),6);
		logger.info("Verified that the trend graph and table displays last 6 months data everytime it is accessed and has no interaction with the time and accounts EA filter");
		
		int pos = expLandingPage.trendAvgAmt().getText().indexOf(":");
		String avgSymbol = expLandingPage.trendAvgAmt().getText().substring(pos+2,pos+3);
		
		if (currency.equalsIgnoreCase(PropsUtil.getDataPropertyValue("PreferredCurrencyType"))) 
		{
			String currSymbol = PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth");
			Assert.assertEquals(avgSymbol, currSymbol);
			logger.info("Verified the Currency Symbol in Preference page and the Avg Amt currency in Trends PopUp");
		} 
		else
		{
			logger.error("The Currency Symbol in Preference page and the Avg Amt currency in Trends PopUp is not matched!!!");
			Assert.assertTrue(false);
		}
		String amountSymbol;
		for (int i=0;i<expLandingPage.trendAmounts().size();i++)
		{
			amountSymbol = expLandingPage.trendAmounts().get(i).getText().substring(0, 1);
			if (currency.equalsIgnoreCase(PropsUtil.getDataPropertyValue("PreferredCurrencyType")))
			{
				String currSymbol = PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth");
				Assert.assertEquals(amountSymbol, currSymbol);
				logger.info("Verified the Currency Symbol in Preference page and Expense amt currency for each month in Trends PopUp");
			} 
			else 
			{
				logger.error("The Currency Symbol in Preference page and Expense amt currency for each month in Trends PopUp is Not Matched!!!");
				Assert.assertTrue(false);
			}
		}
		float sum=0;
		for (int i=1;i<expLandingPage.trendAmounts().size()-2;i++)
		{
			
			String amount = expLandingPage.trendAmounts().get(i).getText().substring(1).replaceAll(",", "");
			float amt = Float.parseFloat(amount);
			logger.info("The Avg amount for last 3 months in legend section of Trends PopUp in EA is  ==============> "+amt);
			sum=sum+amt;
			
		}
		double amt = sum/3;
		logger.info("Rounding Off the Value of Avg amount is " +expLandingPage.roundingOff(amt));
		String avgAmt = new Double(expLandingPage.roundingOff(amt)).toString();
		Assert.assertEquals(avgAmt, expLandingPage.trendAvgAmt().getText().substring(pos+3).replaceAll(",", ""), "The Avg Amt for Last 3 months in Chart and Legend section of Trends PopUp is Not Matched!!!");
		
	}
	
	@Test(description = "AT-81212:Verify that the tabular data(legend) should be in descending order (Current month first)", priority = 80)
	public void verifyDateInDescendingOrder() throws Exception 
	{
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		
		String expectedValue = null;
        for(int i=0; i<6 ; i++)
        {
			List<WebElement> listOfMonths = expLandingPage.listOfMonthsInLegendSection();
			String actualValue = listOfMonths.get(i).getText().trim();
			logger.info("Actual Value of Months in Legend Section " + actualValue);
            expectedValue = expLandingPage.listOfLastSixMonths(i);
            logger.info("Expected Value of Months " + expectedValue.toUpperCase().trim());
            
            logger.info(" Verifying that the tabular data(legend) should be in descending order (Current month first)");
            Assert.assertEquals(actualValue.toUpperCase().trim(),expectedValue.toUpperCase().trim());
            logger.info(" Verified that the tabular data(legend) should be in descending order (Current month first)");
	    }
		
	}
	
	@Test(description = "AT-81311:EA by Master Category Screen - Verify that by default none of the Master Category appear selected neither in chart nor in the Legends section",dependsOnMethods= {"verifyDateInDescendingOrder"}, priority = 81)
	public void verifyMLCIsNotSelectedByDefault() throws Exception 
	{	
		expLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(expLandingPage.verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("EA by Master Category Screen - Verifying that by default none of the Master Category appear selected neither in chart nor in the Legends section");
		Assert.assertTrue(expLandingPage.txnHeaderMLC().isDisplayed());
		logger.info("EA by Master Category Screen - Verified that by default none of the Master Category appear selected neither in chart nor in the Legends section");
	}
	
	@Test(description = "AT-81505,AT-81506:Tapping the close button on Master Category Transactions table should unselect the selected master category from table and chart. Tapping the close button on Master Category Transactions table should revert the table to High Level Category Transactions table",dependsOnMethods= {"verifyMLCIsNotSelectedByDefault"}, priority = 82)
	public void verifyMLCTransactions() throws Exception 
	{	
		SeleniumUtil.click(expLandingPage.verifyMLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad();
		
		logger.info(" Verifying that Master Category Transactions header from table is displayed");
		Assert.assertTrue(expLandingPage.verifyTxnHeaderForSelectedMLC().isDisplayed());
		logger.info(" Verified that Master Category Transactions header from table is displayed");
	
		logger.info("Tapping the close button on Master Category Transactions table");
		SeleniumUtil.click(expLandingPage.closeBtnForMlcTxn());
		SeleniumUtil.waitForPageToLoad(3000);
		
		logger.info("Verifying that on Tapping the close button on Master Category Transactions table should unselect the selected master category from table and chart");
		Assert.assertNull(expLandingPage.verifyTxnHeaderForSelectedMLC());
		logger.info("Verified that on Tapping the close button on Master Category Transactions table should unselect the selected master category from table and chart");
		
		logger.info("Verifying that on Tapping the close button on Master Category Transactions table should revert the table to High Level Category Transactions table");
		Assert.assertTrue(expLandingPage.txnHeaderMLC().isDisplayed());
		logger.info("Verified that on Tapping the close button on Master Category Transactions table should revert the table to High Level Category Transactions table");	
	}
	
	@Test(description = "AT-81499,AT-81503:All transactions in Transaction table for the selected master category should be displayed",dependsOnMethods= {"verifyMLCTransactions"}, priority = 83)
	public void verifyMLCCategoriesInTxnTable() throws Exception 
	{	
		SeleniumUtil.click(expLandingPage.verifyMLCInExpenseByCategory()); //User should be able to select any of the master categories from Legends section..
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(expLandingPage.verifyTxnHeaderForSelectedMLC().isDisplayed());
		SeleniumUtil.waitForPageToLoad(3000);
	
		List<WebElement> listOfTxnCat = expLandingPage.verifyMlcsTransactions();
		for(int i= 0; i< listOfTxnCat.size();i++)
		{
			String actualVal = listOfTxnCat.get(i).getText().trim();
			logger.info("Verifying that All transactions in Transaction table for the selected master category should be displayed");
			Assert.assertTrue(actualVal.contains(PropsUtil.getDataPropertyValue("ExpectedMlcTransaction").trim()));
			logger.info("Verified that All transactions in Transaction table for the selected master category should be displayed");
		}
			
	}
}