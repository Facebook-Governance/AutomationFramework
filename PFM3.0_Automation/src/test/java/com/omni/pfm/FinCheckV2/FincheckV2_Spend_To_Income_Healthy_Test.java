package com.omni.pfm.FinCheckV2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FincheckUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author sbhat1
 */
public class FincheckV2_Spend_To_Income_Healthy_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Spend_To_Income_Healthy_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;
	FincheckUtil util;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);
		util = new FincheckUtil(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.spend.healthy.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.spend.healthy.DagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         SeleniumUtil.refresh(d);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {

		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear.1990"),
				PropsUtil.getDataPropertyValue("finV2.income.15k"), 2, 4);
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(stir.getIndicatorFromDashboard());
		
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		SeleniumUtil.waitForPageToLoad();
		
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(stir.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.Titile"), "*** Spending text mismatch.");
	}
	}
	@Test(description = "AT-96366:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(stir.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.scoreDetailsText"),
				"*** Details text mismatch.");
	}

	@Test(description = "AT-96370:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(stir.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96367:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(stir.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.sugestionText"),
				"*** Sugestion text mismatch.");
	}

	@Test(description = "AT-96368:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(stir.getHealthBar().isDisplayed(), "*** Healthbar not displayed");

	}

	@Test(description = "AT-96369:Verify the thumbs up symbold is present for Healthy Indicator", priority = 6, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyThumbsUpIcon() {
		logger.info(">>>>> Verifying Thumbsup");
		Assert.assertTrue(stir.getThumbsUpIcon().isDisplayed(), "*** Thumbs up symbol mismatch.");
	}

	@Test(description = "AT-96371:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(stir.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"*** Score details header text mismatch.");
	}

	@Test(description = "AT-96372:Verify average Income in score details section", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageIncomeSection() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(stir.getAverageIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.averageIncomeText"), "*** Income text mismatch.");
		Assert.assertEquals(stir.getAverageIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.averageIncomeValue"),
				"*** Income value text mismatch.");

	}

	@Test(description = "AT-96373:Verify average soending in score details section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageSpending() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(stir.getAverageSpendingText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.averageSpendingText"), "*** Spending text mismatch.");
		Assert.assertEquals(stir.getAverageSpendingValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.averageSpendingValue"),
				"*** Spending value mismatch.");

	}

	@Test(description = "AT-96374:Verify About score section", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(stir.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About Score  text mismatch.");
	}

	@Test(description = "AT-96375:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(stir.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** Take action header text mismatch.");
	}

	@Test(description = "AT-96376:Verify the take action description", priority = 13, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(stir.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.takeActionsDescription"),
				"*** take action text mismatch.");
	}

	@Test(description = "AT-96377:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(stir.getActionButton().isDisplayed(), "*** Action button not displayed");
	}
	
	@Test(description = "AT-96378:Verify the average Income click will navigate to a page where the table will be shown", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageIncomeAction() {
		logger.info(">>>>> Cliking on Average Income");
		SeleniumUtil.click(stir.getAverageIncomeValue());

	}

	@Test(description = "AT-96379:Verify the about score link ", priority = 16, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyAboutScoreLink() {
		logger.info(">>>>> Verifying the about score link");
		Assert.assertTrue(stir.getAboutScoreLink().isDisplayed(), "*** About score link not displayed");
	}

	@Test(description = "AT-96380:Verify the Table header - income", priority = 17, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyTableHeader_Income() {
		logger.info(">>>> Verifying the header income");
		Assert.assertEquals(stir.getIncomeHeader().getText(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.tableHeader.Income"),
				"*** Table header text mismatch.");

	}

	@Test(description = "AT-96381:Verify the Table header - income", priority = 18, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyTableHeader_Spending() {
		logger.info(">>>>> Verifying the header spending");
		Assert.assertEquals(stir.getSpendingHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.tableHeader.Spending"),
				"*** Table header text mismatch.");
	}

	@Test(description = "AT-96382:Verify the Table header - income", priority = 19, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyTableHeader_Ratio() {
		logger.info(">>>>> Verifying the header - ratio");
		Assert.assertEquals(stir.getRatioHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.tableHeader.Ratio"),
				"*** Table header text mismatch.");
	}

	@Test(description = "Verify Monthly Income Values", priority = 20, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyMonthlyIncomeValues() {
		logger.info(">>>>> Verify the monthly income values");
		List<WebElement> incomeList = stir.getIncomeValues();
		String incomeString[] = PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.IncomeValues").split(" ");

		for (int i = 0; i < incomeList.size(); i++) {
			Assert.assertEquals(incomeList.get(i).getText().trim(), incomeString[i].trim(),
					"*** income text mismatch.");
		}
	}

	@Test(description = "Verify Monthly Income Values", priority = 21, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyMonthlySpendingValues() {
		logger.info(">>>>> Verify the monthly spending values");
		List<WebElement> expList = stir.getSpendingValues();
		String expString[] = PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.SpendingValues").split(",");

		for (int i = 0; i < expList.size(); i++) {
			Assert.assertEquals(expList.get(i).getText().trim(), expString[i].trim(), "*** Spending text mismatch.");
		}
	}

	@Test(description = "Verify Monthly Income Values", priority = 22, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyMonthlyRatioValues() {
		logger.info(">>>>> Verify the monthly spending values");
		List<WebElement> ratioList = stir.getMonthlyRatio();
		String ratioSting[] = PropsUtil.getDataPropertyValue("finV2.Indicator1.healthy.RatioValues").split(",");

		for (int i = 0; i < ratioList.size(); i++) {
			Assert.assertEquals(ratioList.get(i).getText().trim(), ratioSting[i].trim(), "*** Ratio text mismatch.");
		}
	}

	@Test(description = "Verify Average Text in the table", priority = 23, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyAverageText() {
		logger.info(">>>>> Verifying the average header");
		Assert.assertTrue(stir.getAverageText().isDisplayed(), "*** average text mismatch.");
	}

	@SuppressWarnings("null")
	@Test(description = "AT-96383Calculate the average value", priority = 24, dependsOnMethods = "verifyAverageIncomeAction")
	public void calculateAverageIncome() {
		logger.info(">>>>> Getting the income values");
		String[] incomeString = new String[3];
		float[] incomeFloat = new float[3];
		float averageIncome = 0;
		List<WebElement> we = stir.getIncomeValues();

		for (int i = 0; i < we.size(); i++) {
			incomeString[i] = we.get(i).getText().trim();
			incomeFloat[i] = util.amountParser(incomeString[i]);
			averageIncome = averageIncome + incomeFloat[i];
		}
		averageIncome = averageIncome / 3;
		int finalAverage = Math.round(averageIncome);
		Assert.assertEquals(Math.round(util.amountParser(stir.getAverageIncomeTableValue().getText().trim())),
				finalAverage, "*** Averge text mismatch.");
	}

	@Test(description = "AT-96384:Verify Average Spending Calculation", priority = 25, dependsOnMethods = "verifyAverageIncomeAction")
	public void calculateAverageSpending() {
		logger.info(">>>>> Getting the expense values");
		String[] expenseString = new String[3];
		float[] expenseFloat = new float[3];
		float averageExpense = 0;
		List<WebElement> we = stir.getSpendingValues();

		for (int s = 0; s < 3; s++) {
			expenseString[s] = we.get(s).getText().trim();
			expenseFloat[s] = util.amountParser(expenseString[s]);
			averageExpense = averageExpense + expenseFloat[s];

		}
		averageExpense = averageExpense / 3;
		int finalAverage = Math.round(averageExpense);
		Assert.assertEquals(Math.round(util.amountParser(stir.getAverageExpenseTableValeu().getText().trim())),
				finalAverage, "*** Average text mismatch.");

	}

	@Test(description = "AT-96385:Verify the expense income navigation value", priority = 26, dependsOnMethods = "verifyAverageIncomeAction")
	public void verifyExpenseIncomeNavigationValue() {
		logger.info("Navigating to EAIA");
		
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> we = stir.getIncomeValues();
		float[] expected = new float[3];
		for (int i = 0; i < we.size(); i++) {
			expected[i] = util.amountParser(we.get(i).getText().trim());
		}

		for (int k = 0; k < we.size(); k++) {
			SeleniumUtil.click(we.get(k));
			SeleniumUtil.waitForPageToLoad(3000);
			util.skipEAIA_FTUE();
			String actual = stir.getEAIAValue().getText().trim();
			Assert.assertEquals(Math.round(util.amountParser(actual)), Math.round(expected[k]),
					"*** Parsed amount text mismatch.");
			SeleniumUtil.click(stir.getBackToFincheckLink());
		}

	}
}
