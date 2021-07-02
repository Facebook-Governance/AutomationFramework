/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.InvestmentHoldings;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class InvestmentHoldings_TotalHoldings_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(InvestmentHoldings_TotalHoldings_Test.class);
	InvestmentHoldings_Loc investmentHoldings;
	AccountAddition accAddition;
	AccountsSetting_GlobalSettings_Loc accSettings;
	String manualAccountName1 = "Investment Account Manual 1", manualAccountName2 = "Investment Account Manual 2";
	int manualAccountBalance = 10000;
	String accountNumber = "123456";
	String replaceChar = "*****";
	String groupName = "accountInvestmentgroup";

	@BeforeClass()
	public void init() {
		doInitialization("Investment Holdings Total Holdings");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		investmentHoldings = new InvestmentHoldings_Loc(d);
		accAddition = new AccountAddition();
		accSettings = new AccountsSetting_GlobalSettings_Loc(d);
	}

	@Test(description = "INV_01_01:Verify login happenes successfully.", priority = 1, groups = { "Desktop", "Smoke" })
	public void login() throws Exception {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {

		} else {
			LoginPage.loginMain(d, TestBase.loginParameter);
			logger.info("==>Linking account in process..");
			accAddition.linkAccount();
			Assert.assertTrue(accAddition.addAggregatedAccount("ihjuly.Investment11", "Investment11"));
			PageParser.forceNavigate("InvestmentHoldings", d);
		}
	}

	@Test(description = "INV_01_01: Ftue For Investment Holdings", priority = 2, groups = { "Desktop",
			"Smoke" }, enabled = true)
	public void ftueFlow() throws Exception {
		SeleniumUtil.click(investmentHoldings.continueBtnInWelcomeCM());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(investmentHoldings.goToMyInvestments());
	}

	@Test(description = "INV-02_02,AT-61781: The toal Holding balance should be shown under the All accounts drop down", groups = {
			"Regression" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyingTotalHoldingBalnceVisibility() {

		logger.info("################ verfying total Holding balance section visibility ########################");
		if (appFlag.equals("web") || appFlag.equals("false")) {
			Assert.assertTrue(investmentHoldings.totalHoldingBalncBox().isDisplayed(),
					"total holdings details is not present.");
		}
	}

	@Test(description = "INV-02_03,AT-61782: There should be a section that displays the Today's Holding Balance,Gain/Loss Amount,Change%", groups = {
			"Regression" }, priority = 3, dependsOnMethods = "login", enabled = true)
	public void totalHoldingsHeader() {

		logger.info(
				"################ verfying todays balance,change percentage labels visibility ########################");
		if (appFlag.equals("web") || appFlag.equals("false")) {
			Assert.assertEquals(investmentHoldings.todaysHoldingBalnceLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("todaysHoldingBalnceLabel").trim());
			Assert.assertEquals(investmentHoldings.changePercentLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("changePercentLabel").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61783:Today's Holding Balance,Change% should be correct.", groups = {
			"Regression" }, priority = 4, dependsOnMethods = "login", enabled = true)
	public void totalHoldingsHeaderValues() {

		logger.info(
				"################ verfying todays balance,change percentage values visibility ########################");
		if (appFlag.toLowerCase().equals("web") || appFlag.equals("false")) {
			Assert.assertEquals(investmentHoldings.todaysHoldingBalnceValue().getText().trim(),
					PropsUtil.getDataPropertyValue("todaysHoldingBalnceValue").trim());
			Assert.assertEquals(investmentHoldings.changePercentValue().getText().trim(),
					PropsUtil.getDataPropertyValue("changePercentValue").trim());
		} else {
			Assert.assertEquals(investmentHoldings.todaysHoldingBalnceValueMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("todaysHoldingBalnceValue").trim());
			Assert.assertEquals(investmentHoldings.changePercentageValueMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("changePercentValue").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61784:There should be a 'View more details' button in this section.", groups = {
			"Regression" }, priority = 5, dependsOnMethods = "login", enabled = true)
	public void verifyViewMoreBtnVisibility() {

		logger.info("########## Testing for loan,short balance and margin positive values ##############");
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(investmentHoldings.accsDetails(1));
		if (investmentHoldings.isAcntSelectDoneMobile()) {
			SeleniumUtil.click(investmentHoldings.AcntSelectDoneMobile());
			SeleniumUtil.waitForPageToLoad();
		} else {
			SeleniumUtil.click(investmentHoldings.accountDropDown());
			SeleniumUtil.waitForPageToLoad();
			logger.info("################ verfying view more button visibility ########################");
			Assert.assertTrue(investmentHoldings.viewDetailBtn().isDisplayed());
		}

	}

	@Test(description = "INV-02_04,AT-61785:On clicking the 'View more details', the user should see his 'Holdings Balance', 'Total Cash' everytime.", groups = {
			"Regression" }, priority = 6, dependsOnMethods = "login", enabled = true)
	public void verifyMoreDetailsSection() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			logger.info(
					"################ clicking on view more button and verifying halding balnc label and total cash label. ########################");
			SeleniumUtil.click(investmentHoldings.viewDetailBtn());

			Assert.assertEquals(investmentHoldings.holdingBalnceLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("holdingBalnceLabel").trim());
			Assert.assertEquals(investmentHoldings.totalCashLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("totalCashLabel").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61786:If the user does not have any values in his 'Loans', 'Short Balance' and 'Margin', they should not be displayed.", groups = {
			"Regression" }, priority = 7, dependsOnMethods = "login", enabled = true)
	public void verifyMoreDetailsSectionLabels() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			logger.info(
					"################ verifying total loan, total short and total margin label ####################");
			Assert.assertEquals(investmentHoldings.totalLoanBalncLabel().get(0).getText().trim(),
					PropsUtil.getDataPropertyValue("totalLoanBalncLabel").trim());
			Assert.assertEquals(investmentHoldings.totalShortBalncLabel().get(0).getText().trim(),
					PropsUtil.getDataPropertyValue("totalShortBalncLabel").trim());
			Assert.assertEquals(investmentHoldings.totalMarginLabel().get(0).getText().trim(),
					PropsUtil.getDataPropertyValue("totalMarginLabel").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61788:There should be a total of all these values displayed under them.", groups = {
			"Regression" }, priority = 8, dependsOnMethods = "login", enabled = true)
	public void verifyTotalValueLabel() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			logger.info("################ verifying total balnce label visibility. ####################");
			Assert.assertEquals(investmentHoldings.totalValueLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("totalValueLabel").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61789:The Formula to calculate the Total is (Holdings Balance + Total Cash - Total Loan Balance + Total Short Balance + Total Margin).", groups = {
			"Regression" }, priority = 9, dependsOnMethods = "login", enabled = true)
	public void verifyTotalValue() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			logger.info("################# The Total displayed should be correct.###################");
			double actual = investmentHoldings.getTotalAmountValue();
			double expected = Double.parseDouble(
					investmentHoldings.totalValueValue().getText().trim().substring(1).replaceAll(",", ""));
			Assert.assertEquals(actual, expected);

		}
	}

	@Test(description = "INV-02_04,AT-61790: If the user clicks on the 'View More Details', the button should change to 'Hide Details'.", groups = {
			"Regression" }, priority = 10, dependsOnMethods = "login", enabled = true)
	public void verifyHideDetailsVisibility() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			Assert.assertEquals(investmentHoldings.viewDetailBtn().getText().trim(),
					PropsUtil.getDataPropertyValue("HideBtnText"));
		}
	}

	@Test(description = "INV-02_04,AT-61791: On clicking on the 'Hide Details', the details should collapse.", groups = {
			"Regression" }, priority = 11, dependsOnMethods = "login", enabled = true)
	public void verifycollapseSection() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			SeleniumUtil.click(investmentHoldings.viewDetailBtn());
			Assert.assertFalse(investmentHoldings.holdingBalnceLabel().isDisplayed(),
					"should not be visible for collapse");
			Assert.assertFalse(investmentHoldings.totalValueLabel().isDisplayed(),
					"should not be visible for collapse");
		}
	}

	@Test(description = "INV-02_04,AT-61784:There should be a 'View more details' button in this section with Account 2 negative values", groups = {
			"Regression" }, priority = 12, dependsOnMethods = "login", enabled = true)
	public void verifyViewMoreBtnVisibilityWithNegative() {

		logger.info("########## Testing for loan,short balance and margin negative values ##############");
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(investmentHoldings.accDrpdwnInvAcc().get(0));
		SeleniumUtil.click(investmentHoldings.accsDetails(0));
		if (investmentHoldings.isAcntSelectDoneMobile()) {
			SeleniumUtil.click(investmentHoldings.AcntSelectDoneMobile());
			SeleniumUtil.waitForPageToLoad();
		} else {
			SeleniumUtil.click(investmentHoldings.accountDropDown());
			SeleniumUtil.waitForPageToLoad();
			logger.info("################ verfying view more button visibility ########################");
			Assert.assertTrue(investmentHoldings.viewDetailBtn().isDisplayed());
		}
	}

	@Test(description = "INV-02_04,AT-61793:If any of the values are in negative, they should be shown with a negative symbol in the Total section.", groups = {
			"Regression" }, priority = 13, dependsOnMethods = "login", enabled = true)
	public void verifyTotalValueForNegative() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			SeleniumUtil.click(investmentHoldings.viewDetailBtn());
			logger.info("################# The Total displayed should be correct.###################");
			double actual = investmentHoldings.getTotalAmountValueForNegative();
			double expected = Double.parseDouble(
					investmentHoldings.totalValueValue().getText().trim().substring(1).replaceAll(",", ""));
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(description = "INV-02_04,AT-61784:There should be a 'View more details' button in this section , when both the accounts selected", groups = {
			"Regression" }, priority = 14, dependsOnMethods = "login", enabled = true)
	public void verifyViewMoreBtnVisibilityWithZeroVlaues() {

		logger.info("########## Testing for loan,short balance and margin negative values ##############");
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(investmentHoldings.allInvestmentAccountsCB());
		SeleniumUtil.waitForPageToLoad(5000);
		if (investmentHoldings.isAcntSelectDoneMobile()) {
			SeleniumUtil.click(investmentHoldings.AcntSelectDoneMobile());
			SeleniumUtil.waitForPageToLoad();
		} else {
			SeleniumUtil.click(investmentHoldings.accountDropDown());
			SeleniumUtil.waitForPageToLoad();
			logger.info("################ verfying view more button visibility ########################");
			Assert.assertTrue(investmentHoldings.viewDetailBtn().isDisplayed());
		}
	}

	@Test(description = "INV-02_04: If the user does not have any values in his 'Loans', 'Short Balance' and 'Margin', then total should be sum of total holding and total cash.", groups = {
			"Regression" }, priority = 15, dependsOnMethods = "login", enabled = true)
	public void verifyTotalValueForZeroValues() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			SeleniumUtil.click(investmentHoldings.viewDetailBtn());
			logger.info("################# The Total displayed should be correct.###################");
			double actual = investmentHoldings.getTotalAmountValueForZero();
			double expected = Double.parseDouble(
					investmentHoldings.totalValueValue().getText().trim().substring(1).replaceAll(",", ""));
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(description = "INV-02_04,AT-61787: If the user does not have any values in his 'Loans', 'Short Balance' and 'Margin', they should not be displayed.", groups = {
			"Regression" }, priority = 16, dependsOnMethods = "login", enabled = true)
	public void verifyZeroValuesNotDisplayed() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			Assert.assertTrue(investmentHoldings.totalLoanBalncLabel().size() == 0,
					"Total Loan value is zero,and still displaying");
			Assert.assertTrue(investmentHoldings.totalShortBalncLabel().size() == 0,
					"Total short balance value is zero,and still displaying");
			Assert.assertTrue(investmentHoldings.totalMarginLabel().size() == 0,
					"Total margin value is zero,and still displaying");
		}
	}

	@Test(description = "INV-02_04,AT-61798: If the user has lesser than '1' quantity, it should be shown as '<1' on the UI", groups = {
			"Regression" }, priority = 17, dependsOnMethods = "login", enabled = true)
	public void verifyQuantityLessThanOne() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			SeleniumUtil.click(investmentHoldings.unclassifiedDropDown());
			Assert.assertEquals(investmentHoldings.lessThanOneSymbol().getText().trim(),
					PropsUtil.getDataPropertyValue("QuantityLessThanOneSymbol").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61797: If the user has lesser than '1' cost basis, it should be shown as '<1' on the UI.", groups = {
			"Regression" }, priority = 18, dependsOnMethods = "login", enabled = true)
	public void verifyLessThanOneForCostBasis() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			Assert.assertEquals(investmentHoldings.lessThanOneSymbolForCostBasis().getText().trim(),
					PropsUtil.getDataPropertyValue("lessThanOneSymbolForCostBasis").trim());
		}
	}

	@Test(description = "INV-02_04,AT-61799: If the user has lesser than '1' Market Value, it should be shown as '<1' on the UI with the currency symbol.", groups = {
			"Regression" }, priority = 19, dependsOnMethods = "login", enabled = true)
	public void verifyLessThanOneForMarketValue() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			Assert.assertEquals(investmentHoldings.lessThanOneSymbolForMarketValue().getText().trim(),
					PropsUtil.getDataPropertyValue("lessThanOneSymbolForMarketValue").trim());
		}
	}

	@Test(description = "INV-02_03,AT-61806: By default, the key to change Gain/Loss should be disabled.", groups = {
			"Regression" }, priority = 20, dependsOnMethods = "login", enabled = false)
	public void gainLossHeaderValue() {
		if (appFlag.equals("web") || appFlag.equals("false")) {
			logger.info(
					"######## By default gain/loss key should be disbaled,if key is enabled then only test should execute ########");
			Assert.assertEquals(investmentHoldings.GainLossLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("GainLossLabel").trim());
			Assert.assertEquals(investmentHoldings.GainLossValue().getText().trim(),
					PropsUtil.getDataPropertyValue("GainLossValue").trim());
		}
	}

	@Test(description = "INV_01_00:Verify login happenes successfully.", priority = 21, groups = { "Desktop",
			"Smoke" }, enabled = true)
	public void addDagInvestmentAccount() throws Exception {

		logger.info("##################### login and adding account.########################");

		SeleniumUtil.refresh(d);
		logger.info("==>Linking account in process..");
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount("DagInvestments", "pfm3Inv.Investment5", "Investment5"));
		PageParser.forceNavigate("InvestmentHoldings", d);
	}

	@Test(description = "INV-02_04,AT-61801: The values for the 'Cost Basis' and the 'Quantity' should be apportioned.", groups = {
			"Regression" }, priority = 22, dependsOnMethods = "addDagInvestmentAccount", enabled = true)
	public void verifyAportionedValues() {

		SeleniumUtil.click(investmentHoldings.expandCollapseButton());
		investmentHoldings.aportionedValueSymbol();
		Assert.assertEquals(investmentHoldings.quantitiyApportionedValue().getText().trim(),
				PropsUtil.getDataPropertyValue("quantitiyApportionedValue").trim());
		Assert.assertEquals(investmentHoldings.priceApportionedValue().getText().trim(),
				PropsUtil.getDataPropertyValue("priceApportionedValue").trim());
		Assert.assertEquals(investmentHoldings.costBasisQuantitiyApportionedValue().getText().trim(),
				PropsUtil.getDataPropertyValue("costBasisQuantitiyApportionedValue").trim());
	}

	@Test(description = "INV-02_04,AT-61800: verifying disclaimer msg for '*'", groups = {
			"Regression" }, priority = 23, dependsOnMethods = "addDagInvestmentAccount", enabled = true)
	public void verifyingDisclaimerMsg() {
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(investmentHoldings.disclaimerMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("disclaimerMsg"));
	}

	@Test(description = "AT-128044,AT-128035:verify manual accounts in displayed in investment holdings page accounts dropdown and amount details", groups = {
			"Regression" }, priority = 24)
	public void checkManualAccountIsDisplayedInInvestmentHoldingsAccountDropDownAndAmountDetailsWhenNoManualAccountsArePresent() {
		try {
			investmentHoldings.navigateToInvestmentHoldings();
			if (!investmentHoldings.isNoDataScreenDisplayed()) {
				ArrayList<String> accountsDisplayed = investmentHoldings.returnTheAccountsDisplayedInAccountsDropdown();
				if (accountsDisplayed.contains(
						PropsUtil.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.label"))) {
					Assert.fail(
							"Manual account label is displayed in the investment holdings account dropdown even manual accounts are not added yet");
				}
				investmentHoldings.expandViewMoreDetailsInTotalAccountHoldingsBalance();
				if (SeleniumUtil.isDisplayed(InvestmentHoldings_Loc.manualAccount_InvestmentHoldings_BalanceLabel, 4)) {
					Assert.fail("Manual investments label is displayed in view more details,"
							+ " Even though manual accounts are not added.");
				}
			}
		} catch (Exception e) {
			logger.error(
					"unable to validate manual account is displaye din investment holdings accounts dropdown due to :: {}",
					e.getMessage());
			Assert.fail(
					"unable to validate manual account is displaye din investment holdings accounts dropdown due to ::"
							+ e.getMessage());
		} finally {
			investmentHoldings.closeAccountDropdown();
		}
	}

	@Test(description = "AT-128024,AT-128020:Add manual account for investments. verify in dropdown and it's calculations", groups = {
			"Regression" }, priority = 25)
	public void addManualAccountAndVerifyInAccountDropdown() {
		SoftAssert soft = new SoftAssert();
		try {
			float presentAmount = investmentHoldings.returnPresentTotalManualInvestmentAccountBalance();
			float presentTotalAmount = investmentHoldings.returnTotalInvestmentHoldingsAccountBalance();
			accAddition.addManualAccount("Investments", manualAccountName1, manualAccountName1,
					manualAccountBalance + "", accountNumber, null, null, null);
			accAddition.addManualAccount("Investments", manualAccountName2, manualAccountName2,
					manualAccountBalance + 5000 + "", accountNumber, null, null, null);
			investmentHoldings.navigateToInvestmentHoldings();
			ArrayList<String> accountsDisplayed = investmentHoldings.returnTheAccountsDisplayedInAccountsDropdown();
			if (!accountsDisplayed.contains(PropsUtil
					.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.label").toUpperCase())) {
				soft.fail(
						"Manual account label is not displayed in the investment holdings account dropdown after adding manual account");
			}
			if (!accountsDisplayed
					.contains(PropsUtil.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.text")
							.replace(replaceChar, manualAccountName1))) {
				soft.fail("Manual account with name :: " + manualAccountName1
						+ " is not displayed in the investment holdings account dropdown");
			}
			if (!accountsDisplayed
					.contains(PropsUtil.getDataPropertyValue("investmentHoldings.accountDropdown.manualAccount.text")
							.replace(replaceChar, manualAccountName2))) {
				soft.fail("Manual account with name :: " + manualAccountName2
						+ " is not displayed in the investment holdings account dropdown");
			}
			float amountAfterAddingManualAccounts = investmentHoldings
					.returnPresentTotalManualInvestmentAccountBalance();
			if (!(amountAfterAddingManualAccounts == presentAmount + 25000)) {
				soft.fail(
						"Manual investment account balance is not as expected after adding manual account. Expected :: "
								+ (presentAmount + 25000) + " and actual :: " + amountAfterAddingManualAccounts);
			}
			float totalInvestmentHoldingsAccountBalance = investmentHoldings
					.returnTotalInvestmentHoldingsAccountBalance();
			if (!(presentTotalAmount + 25000 == totalInvestmentHoldingsAccountBalance)) {
				soft.fail(
						"Total investment holdings account balance is not as expected after adding manual account. Expected :: "
								+ (presentTotalAmount + 25000) + " and actual :: "
								+ totalInvestmentHoldingsAccountBalance);
			}
		} catch (Exception e) {
			logger.error("Unable to add manual account and verify balance and account dropdown due to :: {}",
					e.getMessage());
			Assert.fail(
					"Unable to add manual account and verify balance and account dropdown due to :: " + e.getMessage());
		} finally {
			soft.assertAll();
		}
	}

	@Test(description = "AT-128033:Select only manual accounts and validate the account balance, asset class dropdown,chart view, table view and change type percentage", priority = 26, groups = {
			"Regression" })
	public void verifyFunctionalityOfUIOfInvestmentHoldingsPageWhenOnlyManualAccountsAreSelectedInAccountsDropdown() {
		SoftAssert soft = new SoftAssert();
		try {
			investmentHoldings.selectGivenAccountUnderInvestmentHoldingsAccountDropdown(manualAccountName1);
			investmentHoldings.addTheGivenAccountUnderInvestmentHoldingsAccountDropdown(manualAccountName2);
			float presentAccountInInvestmentHoldings = investmentHoldings.returnTotalInvestmentHoldingsAccountBalance();
			float expectedAmount = manualAccountBalance * 2 + 5000;
			if (!(presentAccountInInvestmentHoldings == expectedAmount)) {
				soft.fail("Total Investment holdings amount displayed is not as expected.Expected :: " + expectedAmount
						+ " && Actual :: " + presentAccountInInvestmentHoldings);
			}
			float totalAggregatedAccountsBalance = investmentHoldings.returnTotalAggregatedAccountsBalance();
			if (!(totalAggregatedAccountsBalance == 0)) {
				soft.fail(
						"Total aggregated accounts balance is not displaying properly when only manual accounts is selected.Expected :: 0 && Actual :: "
								+ totalAggregatedAccountsBalance);
			}

			float changePercentValue = investmentHoldings.returnChangePercentValueOfAggregatedAccounts();
			if (!(changePercentValue == 0)) {
				soft.fail(
						"Aggregated accounts change percent value is not displaying properly when only manual accounts is selected.Expected :: 0 && Actual :: "
								+ changePercentValue);
			}

			if (SeleniumUtil.isDisplayed(InvestmentHoldings_Loc.investmentHoldings_ChartView, 3)) {
				soft.fail("Chart view is displayed when only manual accounts is selected in accounts dropdown.");
			}

			if (SeleniumUtil.isDisplayed(InvestmentHoldings_Loc.investmentHoldings_TableView, 3)) {
				soft.fail("Table view is displayed when only manual accounts is selected in accounts dropdown.");
			}

			if (!investmentHoldings.isAssetClassDropdownDisabled()) {
				soft.fail("Asset class dropdown is not disabled when only manual accounts are selected");
			}
		} catch (Exception e) {
			logger.error("Unable to validate UI functional changes when only manual account is selected due to :: {} ",
					e.getMessage());
			Assert.fail("Unable to validate UI functional changes when only manual account is selected due to :: "
					+ e.getMessage());
		} finally {
			soft.assertAll();
		}
	}

	@Test(description = "AT-128022:verify manual investment account balance is reflecting in dashboard or not", priority = 27, groups = {
			"Regression" })
	public void verifyManualInvestmentAccountBalanceInDashboard() {
		try {
			PageParser.forceNavigate("DashboardPage", d);
			float manualInvestmentAccountsBalanceDisplayedInTheDashboard = investmentHoldings
					.returnTheAmountDisplayedInDashboardOfManualInvestmentAccounts();
			if (!(manualInvestmentAccountsBalanceDisplayedInTheDashboard == manualAccountBalance * 2 + 5000)) {
				Assert.fail("Manual Investment accounts balance is not as expected in dashboard. Expected :: "
						+ manualAccountBalance * 2 + 5000 + " & Actual :: "
						+ manualInvestmentAccountsBalanceDisplayedInTheDashboard);
			}
		} catch (Exception e) {
			Assert.fail(
					"Unable to validate manual investment accounts balance in dashboard due to :: " + e.getMessage());
		}
	}

	@Test(description = "AT-128032:Create a group for only manual accounts, Select it and validate the UI in investment holdings page", priority = 28, groups = {
			"Regression" })
	public void createGroupForManualAccountsAndValidateTheFunctionalities() {
		try {
			investmentHoldings.navigateToInvestmentHoldings();
			investmentHoldings.createGroup(groupName, new String[] { manualAccountName1, manualAccountName2 });
			investmentHoldings.selectGroupInInvestmentHoldings(groupName);
			float presentAmountInManualInvestment = investmentHoldings
					.returnPresentTotalManualInvestmentAccountBalance();
			float expectedAmount = manualAccountBalance * 2 + 5000;
			if (!(presentAmountInManualInvestment == expectedAmount)) {
				Assert.fail(
						"Total Investment holdings amount displayed is not as expected when a group with only manual accounts is selected.Expected :: "
								+ expectedAmount + " && Actual :: " + presentAmountInManualInvestment);
			}
		} catch (Exception e) {
			logger.error("Unable to create group for manual accounts and validate functionalities due to :: "
					+ e.getMessage());
			Assert.fail("Unable to create group for manual accounts and validate functionalities due to :: "
					+ e.getMessage());
		}
	}

	@Test(description = "AT-128030:Delete a manual account and select a group and validate balance is getting updated in the investment holdings", priority = 29, groups = {
			"Regression" })
	public void deleteAManualAccountAndVerifyInTheInvestmentHoldings() {
		try {
			PageParser.forceNavigate("AccountSettings", d);
			accSettings.navigateToManualAccounts();
			accSettings.deleteAccount(manualAccountName2);
			investmentHoldings.navigateToInvestmentHoldings();
			investmentHoldings.selectGroupInInvestmentHoldings(groupName);
			float presentAmountInManualInvestment = investmentHoldings
					.returnPresentTotalManualInvestmentAccountBalance();
			float expectedAmount = manualAccountBalance;
			if (!(presentAmountInManualInvestment == expectedAmount)) {
				Assert.fail(
						"Total Investment holdings amount displayed is not as expected when a manual account is deleted.Expected :: "
								+ expectedAmount + " && Actual :: " + presentAmountInManualInvestment);
			}
		} catch (Exception e) {
			logger.error("Unable to verify investment holding balance after deleting a account from group due to :: "
					+ e.getMessage());
			Assert.fail("Unable to verify investment holding balance after deleting a account from group due to :: "
					+ e.getMessage());
		}
	}
}
