/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.FinCheckV2;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Exceptions_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Exceptions_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Onboarding_Test.class);
	AccountAddition accountAddition;
	FincheckV2_Exceptions_Loc exception;
	FincheckV2_GetStarted_Loc v2GetStartd;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		accountAddition = new AccountAddition();
		exception = new FincheckV2_Exceptions_Loc(d);
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.spDagSite"),
				PropsUtil.getDataPropertyValue("fiV2.spDagPassword"));
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();

		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(description = "AT-95951,AT-96306:Finish Quick Onboarding", priority = 0)
	public void throughAccountsToError() {
		logger.info("Finishing Onboarding");

		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear.1990"),
				PropsUtil.getDataPropertyValue("finV2.income.15k"), 2, 4);

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(exception.getSettingsOverview());
		SeleniumUtil.click(exception.getEditAccountsButton());
		exception.throwAccountsToError();

		Assert.assertEquals(exception.getIndicator1ScoreValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.IndicatorValue"), "*** Indicator value mismatch.");
	}

	@Test(description = "AT-96248,AT-95038:Verify Indicator 2 score value from overview screen", priority = 1, dependsOnMethods = "throughAccountsToError")
	public void verifyIndicator2ScoreValue() {
		logger.info(">>>>> Verifying Indicator score value");
		Assert.assertEquals(exception.getIndicator2ScoreValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.IndicatorValue"), "*** Indicator value mismatch.");
	}

	@Test(description = "AT-96245,AT-95037,AT-96246:Verify Indicator 3 score value from overview screen", priority = 2, dependsOnMethods = "throughAccountsToError")
	public void verifyIndicator3ScoreValue() {
		logger.info(">>>>> Verifying Indicator score value");
		Assert.assertEquals(exception.getIndicator3ScoreValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.IndicatorValue"), "*** Indicator value mismatch.");
	}

	@Test(description = "AT-95032,AT-96243:Verify Indicator 4 score value from overview screen", priority = 3, dependsOnMethods = "throughAccountsToError")
	public void verifyIndicator4ScoreValue() {
		logger.info(">>>>> Verifying Indicator score value");
		Assert.assertEquals(exception.getIndicator4ScoreValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.IndicatorValue"), "*** Indicator value mismatch.");
	}

	@Test(description = "AT-95031,AT-96244:Verify Indicator 5 score value from overview screen", priority = 4, dependsOnMethods = "throughAccountsToError")
	public void verifyIndicator5ScoreValue() {
		logger.info(">>>>> Verifying Indicator score value");
		Assert.assertEquals(exception.getIndicator5ScoreValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.IndicatorValue"), "*** Indicator value mismatch.");
	}

	@Test(description = "AT-95034,AT-96241:Verify the error screen at Indicator 1", priority = 5, dependsOnMethods = "throughAccountsToError")
	public void verifyErrorIndicator1() {
		logger.info(">>>>> Navigating to Indicator 1");
		SeleniumUtil.click(exception.getIndicator1ScoreValue());
		Assert.assertTrue(exception.getExceptionScreenTitle().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "AT-95033,AT-96242:Verify the exception header text", priority = 6, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyExceptionHeader1() {
		logger.info(">>>>> Verifying the exception header");
		Assert.assertEquals(exception.getExceptionScreenTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exception.oopsHeader"), "***** Accounts are not in error");
	}

	@Test(description = "AT-95030,AT-95949:Verify the description text is displayed", priority = 7, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyDescriptionText1() {
		logger.info(">>>>> Verifying the exception text description displayed");
		Assert.assertTrue(exception.getExceptionScreenDesc().isDisplayed(),
				"***** Exception description is not displayed");
	}

	@Test(description = "AT-95947,AT-95948:Verify the description text is displayed", priority = 8, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyDescription1() {
		logger.info(">>>>> Verifying the exception text description");
		Assert.assertEquals(exception.getExceptionScreenDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.oopsDescription"), "***** Accounts are not in error");

	}

	@Test(description = "AT-95945,AT-95946,AT-95029:Verify the available accounts in error", priority = 9, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyAccountsAvailable1() {
		String accounts[] = PropsUtil.getDataPropertyValue("finV2.exceptions.ind1.accountNamesInError").split(",");
		for (int i = 0; i < exception.getAccountsListInError().size(); i++) {
			Assert.assertEquals(exception.getAccountsListInError().get(i).getText().trim(), accounts[i].trim(),
					"***** Accounts are not in error");
		}

	}

	@Test(description = "AT-95943,AT-95028:Verify Link Account button is displayed", priority = 10, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyLinkAccount1() {
		logger.info("Verifying Link Accounts button");
		Assert.assertTrue(exception.getLinkAccountButton().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "AT-95944,AT-95941:Verify Back Link is displayed", priority = 11, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyBackLink1() {
		logger.info(">>>>> Verifying back link is displayed");
		Assert.assertTrue(exception.getBackLink().isDisplayed(), "***** Back Link is not displaye");
	}

	@Test(description = "AT-95942,AT-95223:Verify click on the accounts in error will navigate to accounts page", priority = 12, dependsOnMethods = "verifyErrorIndicator1")
	public void verifyAccountsClick1() {
		logger.info(">>>>> Clicking on accounts");
		SeleniumUtil.click(exception.getAccountsListInError().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(exception.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.getAccountsFinappHeader"),
				"***** Accounts are not in error");
	}

	@Test(description = "AT-95225,AT-95224:Verify Back to fincheck link is present in Accounts Page", priority = 13, dependsOnMethods = "verifyAccountsClick1")
	public void verifyBackToFincheck1() {
		logger.info(">>>> Verifying back to fincheck link");
		Assert.assertTrue(exception.getBackLinkFromAccounts().isDisplayed(),
				"***** Back link is not present at accounts page");
	}

	@Test(description = "Verify back click in accounts page will navigate back to indicator page", priority = 14, dependsOnMethods = "verifyAccountsClick1")
	public void verifyBackAccountLinkClik1() {
		logger.info(">>>>> Clicking on accounts back link");
		SeleniumUtil.click(exception.getBackLinkFromAccounts());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(exception.getIndicator1Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.Titile"), "***** Accounts are not in error");

	}

	// Indicator 2
	@Test(description = "AT-96426:Verify the error screen at Indicator 1", priority = 15, dependsOnMethods = "verifyBackAccountLinkClik1")
	public void verifyErrorIndicator2() {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Navigating to Indicator 1");
		SeleniumUtil.click(exception.getIndicator2ScoreValue());
		Assert.assertTrue(exception.getExceptionScreenTitle().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "AT-95003:Verify the exception header text", priority = 16, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyExceptionHeader2() {
		logger.info(">>>>> Verifying the exception header");
		Assert.assertEquals(exception.getExceptionScreenTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exception.oopsHeader"));
	}

	@Test(description = "AT-95002:Verify the description text is displayed", priority = 17, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyDescriptionText2() {
		logger.info(">>>>> Verifying the exception text description displayed");
		Assert.assertTrue(exception.getExceptionScreenDesc().isDisplayed(),
				"***** Exception description is not displayed");
	}

	@Test(description = "AT-95005:Verify the description text is displayed", priority = 18, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyDescription2() {
		logger.info(">>>>> Verifying the exception text description");
		Assert.assertEquals(exception.getExceptionScreenDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.oopsDescription"), "***** Accounts are not in error");

	}

	@Test(description = "AT-95004:Verify the available accounts in error", priority = 19, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyAccountsAvailable2() {
		String accounts[] = PropsUtil.getDataPropertyValue("finV2.exceptions.ind1.accountNamesInError").split(",");
		for (int i = 0; i < exception.getAccountsListInError().size(); i++) {
			Assert.assertEquals(exception.getAccountsListInError().get(i).getText().trim(), accounts[i].trim(),
					"***** Accounts are not in error");
		}

	}

	@Test(description = "AT-95000,AT-97538:Verify Link Account button is displayed", priority = 20, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyLinkAccount2() {
		logger.info("Verifying Link Accounts button");
		Assert.assertTrue(exception.getLinkAccountButton().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "AT-97536:Verify Back Link is displayed", priority = 21, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyBackLink2() {
		logger.info(">>>>> Verifying back link is displayed");
		Assert.assertTrue(exception.getBackLink().isDisplayed(), "***** Back Link is not displaye");
	}

	@Test(description = "AT-97537:Verify click on the accounts in error will navigate to accounts page", priority = 22, dependsOnMethods = "verifyErrorIndicator2")
	public void verifyAccountsClick2() {
		logger.info(">>>>> Clicking on accounts");
		SeleniumUtil.click(exception.getAccountsListInError().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(exception.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.getAccountsFinappHeader"),
				"***** Accounts are not in error");
	}

	@Test(description = "AT-97539:Verify Back to fincheck link is present in Accounts Page", priority = 23, dependsOnMethods = "verifyAccountsClick2")
	public void verifyBackToFincheck2() {
		logger.info(">>>> Verifying back to fincheck link");
		Assert.assertTrue(exception.getBackLinkFromAccounts().isDisplayed(),
				"***** Back link is not present at accounts page");
	}

	@Test(description = "AT-96330:Verify back click in accounts page will navigate back to indicator page", priority = 24, dependsOnMethods = "verifyAccountsClick2")
	public void verifyBackAccountLinkClik2() {
		logger.info(">>>>> Clicking on accounts back link");
		SeleniumUtil.click(exception.getBackLinkFromAccounts());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(exception.getIndicator2Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.Titile"), "***** Accounts are not in error");

	}

	// Indicator 3
	@Test(description = "Verify the error screen at Indicator 1", priority = 25, dependsOnMethods = "verifyBackAccountLinkClik2")
	public void verifyErrorIndicator3() {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Navigating to Indicator 3");
		SeleniumUtil.click(exception.getIndicator3ScoreValue());
		Assert.assertTrue(exception.getExceptionScreenTitle().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "Verify the exception header text", priority = 26, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyExceptionHeader3() {
		logger.info(">>>>> Verifying the exception header");
		Assert.assertEquals(exception.getExceptionScreenTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exception.oopsHeader"), "***** Accounts are not in error");
	}

	@Test(description = "Verify the description text is displayed", priority = 27, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyDescriptionText3() {
		logger.info(">>>>> Verifying the exception text description displayed");
		Assert.assertTrue(exception.getExceptionScreenDesc().isDisplayed(),
				"***** Exception description is not displayed");
	}

	@Test(description = "Verify the description text is displayed", priority = 28, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyDescription3() {
		logger.info(">>>>> Verifying the exception text description");
		Assert.assertEquals(exception.getExceptionScreenDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.oopsDescription"), "***** Accounts are not in error");

	}

	@Test(description = "Verify the available accounts in error", priority = 29, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyAccountsAvailable3() {
		String accounts[] = PropsUtil.getDataPropertyValue("finV2.exceptions.ind3.accountNamesInError").split(",");
		for (int i = 0; i < exception.getAccountsListInError().size(); i++) {
			Assert.assertEquals(exception.getAccountsListInError().get(i).getText().trim(), accounts[i].trim(),
					"***** Accounts are not in error");
		}

	}

	@Test(description = "Verify Link Account button is displayed", priority = 30, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyLinkAccount3() {
		logger.info("Verifying Link Accounts button");
		Assert.assertTrue(exception.getLinkAccountButton().isDisplayed());

	}

	@Test(description = "Verify Back Link is displayed", priority = 31, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyBackLink3() {
		logger.info(">>>>> Verifying back link is displayed");
		Assert.assertTrue(exception.getBackLink().isDisplayed(), "***** Back Link is not displaye");
	}

	@Test(description = "AT-97534:Verify click on the accounts in error will navigate to accounts page", priority = 32, dependsOnMethods = "verifyErrorIndicator3")
	public void verifyAccountsClick3() {
		logger.info(">>>>> Clicking on accounts");
		SeleniumUtil.click(exception.getAccountsListInError().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(exception.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.getAccountsFinappHeader"),
				"***** Accounts are not in error");
	}

	@Test(description = "AT-97531:Verify Back to fincheck link is present in Accounts Page", priority = 33, dependsOnMethods = "verifyAccountsClick3")
	public void verifyBackToFincheck3() {
		logger.info(">>>> Verifying back to fincheck link");
		Assert.assertTrue(exception.getBackLinkFromAccounts().isDisplayed(),
				"***** Back link is not present at accounts page");
	}

	@Test(description = "AT-95959:Verify back click in accounts page will navigate back to indicator page", priority = 34, dependsOnMethods = "verifyAccountsClick3")
	public void verifyBackAccountLinkClik3() {
		logger.info(">>>>> Clicking on accounts back link");
		SeleniumUtil.click(exception.getBackLinkFromAccounts());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(exception.getIndicator3Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.Titile"), "***** Accounts are not in error");

	}

	// Indicator 4
	@Test(description = "Verify the error screen at Indicator 1", priority = 35, dependsOnMethods = "verifyBackAccountLinkClik3")
	public void verifyErrorIndicator4() {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Navigating to Indicator 3");
		SeleniumUtil.click(exception.getIndicator4ScoreValue());
		Assert.assertTrue(exception.getExceptionScreenTitle().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "AT-95236:Verify the exception header text", priority = 36, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyExceptionHeader4() {
		logger.info(">>>>> Verifying the exception header");
		Assert.assertEquals(exception.getExceptionScreenTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exception.oopsHeader"), "***** Accounts are not in error");
	}

	@Test(description = "AT-97532:Verify the description text is displayed", priority = 37, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyDescriptionText4() {
		logger.info(">>>>> Verifying the exception text description displayed");
		Assert.assertTrue(exception.getExceptionScreenDesc().isDisplayed(),
				"***** Exception description is not displayed");
	}

	@Test(description = "AT-97533,AT-96320:Verify the description text is displayed", priority = 38, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyDescription4() {
		logger.info(">>>>> Verifying the exception text description");
		Assert.assertEquals(exception.getExceptionScreenDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.oopsDescription"), "***** Accounts are not in error");

	}

	@Test(description = "Verify the available accounts in error", priority = 39, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyAccountsAvailable4() {
		String accounts[] = PropsUtil.getDataPropertyValue("finV2.exceptions.ind4.accountNamesInError").split(",");
		for (int i = 0; i < exception.getAccountsListInError().size(); i++) {
			Assert.assertEquals(exception.getAccountsListInError().get(i).getText().trim(), accounts[i].trim(),
					"***** Accounts are not in error");
		}

	}

	@Test(description = "Verify Link Account button is displayed", priority = 40, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyLinkAccount4() {
		logger.info("Verifying Link Accounts button");
		Assert.assertTrue(exception.getLinkAccountButton().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "Verify Back Link is displayed", priority = 41, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyBackLink4() {
		logger.info(">>>>> Verifying back link is displayed");
		Assert.assertTrue(exception.getBackLink().isDisplayed(), "***** Back Link is not displaye");
	}

	@Test(description = "AT-97535:Verify click on the accounts in error will navigate to accounts page", priority = 42, dependsOnMethods = "verifyErrorIndicator4")
	public void verifyAccountsClick4() {
		logger.info(">>>>> Clicking on accounts");
		SeleniumUtil.click(exception.getAccountsListInError().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(exception.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.getAccountsFinappHeader"));
	}

	@Test(description = "Verify Back to fincheck link is present in Accounts Page", priority = 43, dependsOnMethods = "verifyAccountsClick4")
	public void verifyBackToFincheck4() {
		logger.info(">>>> Verifying back to fincheck link");
		Assert.assertTrue(exception.getBackLinkFromAccounts().isDisplayed(),
				"***** Back link is not present at accounts page");
	}

	@Test(description = "Verify back click in accounts page will navigate back to indicator page", priority = 44, dependsOnMethods = "verifyAccountsClick4")
	public void verifyBackAccountLinkClik4() {
		logger.info(">>>>> Clicking on accounts back link");
		SeleniumUtil.click(exception.getBackLinkFromAccounts());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(exception.getIndicator4Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.Titile"), "***** Accounts are not in error");

	}

	// Indicator 5
	@Test(description = "Verify the error screen at Indicator 1", priority = 45, dependsOnMethods = "verifyBackAccountLinkClik4")
	public void verifyErrorIndicator5() {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Navigating to Indicator 3");
		SeleniumUtil.click(exception.getIndicator5ScoreValue());
		Assert.assertTrue(exception.getExceptionScreenTitle().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "Verify the exception header text", priority = 46, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyExceptionHeader5() {
		logger.info(">>>>> Verifying the exception header");
		Assert.assertEquals(exception.getExceptionScreenTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exception.oopsHeader"), "***** Accounts are not in error");
	}

	@Test(description = "Verify the description text is displayed", priority = 47, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyDescriptionText5() {
		logger.info(">>>>> Verifying the exception text description displayed");
		Assert.assertTrue(exception.getExceptionScreenDesc().isDisplayed(),
				"***** Exception description is not displayed");
	}

	@Test(description = "Verify the description text is displayed", priority = 48, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyDescription5() {
		logger.info(">>>>> Verifying the exception text description");
		Assert.assertEquals(exception.getExceptionScreenDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.oopsDescription"), "***** Accounts are not in error");

	}

	@Test(description = "Verify the available accounts in error", priority = 49, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyAccountsAvailable5() {
		String accounts[] = PropsUtil.getDataPropertyValue("finV2.exceptions.ind1.accountNamesInError").split(",");
		for (int i = 0; i < exception.getAccountsListInError().size(); i++) {
			Assert.assertEquals(exception.getAccountsListInError().get(i).getText().trim(), accounts[i].trim());
		}

	}

	@Test(description = "Verify Link Account button is displayed", priority = 50, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyLinkAccount5() {
		logger.info("Verifying Link Accounts button");
		Assert.assertTrue(exception.getLinkAccountButton().isDisplayed(), "***** Accounts are not in error");

	}

	@Test(description = "Verify Back Link is displayed", priority = 51, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyBackLink5() {
		logger.info(">>>>> Verifying back link is displayed");
		Assert.assertTrue(exception.getBackLink().isDisplayed(), "***** Back Link is not displaye");
	}

	@Test(description = "Verify click on the accounts in error will navigate to accounts page", priority = 52, dependsOnMethods = "verifyErrorIndicator5")
	public void verifyAccountsClick5() {
		logger.info(">>>>> Clicking on accounts");
		SeleniumUtil.click(exception.getAccountsListInError().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(exception.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.exceptions.getAccountsFinappHeader"),
				"***** Accounts are not in error");
	}

	@Test(description = "Verify Back to fincheck link is present in Accounts Page", priority = 53, dependsOnMethods = "verifyAccountsClick5")
	public void verifyBackToFincheck5() {
		logger.info(">>>> Verifying back to fincheck link");
		Assert.assertTrue(exception.getBackLinkFromAccounts().isDisplayed(),
				"***** Back link is not present at accounts page");
	}

	@Test(description = "AT-95968:Verify back click in accounts page will navigate back to indicator page", priority = 54, dependsOnMethods = "verifyAccountsClick5")
	public void verifyBackAccountLinkClik5() {
		logger.info(">>>>> Clicking on accounts back link");
		SeleniumUtil.click(exception.getBackLinkFromAccounts());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(exception.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.Titile"), "***** Accounts are not in error");

	}
}
