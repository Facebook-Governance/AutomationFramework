/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.BudgetEnhancement;

import java.awt.AWTException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_AcntClassificationUpdate_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Budget_AcntClassificationUpdate_Test.class);
	Create_Budget_Loc createBudget;
	Budget_Select_Accounts_Loc selctAcntPage;
	LoginPage login;
	AccountAddition accountAdd;
	Budget_No_Account_Loc budgetNoActScreen;
	Budget_Income_And_Bill_Loc budgetIncomeBillScreen;
	Budget_Flexible_Spending_Loc budgetFlexibleSpendingScreen;
	Budget_Income_And_Bill_Summery_Loc createdBudgetSummaryPage;
	Budget_Settings_Loc budgetSetting;

	@BeforeClass(alwaysRun = true)
	public void init() {
		login = new LoginPage();
		createBudget = new Create_Budget_Loc(d);
		selctAcntPage = new Budget_Select_Accounts_Loc(d);
		accountAdd = new AccountAddition();
		budgetNoActScreen = new Budget_No_Account_Loc(d);
		budgetIncomeBillScreen = new Budget_Income_And_Bill_Loc(d);
		budgetFlexibleSpendingScreen = new Budget_Flexible_Spending_Loc(d);
		createdBudgetSummaryPage = new Budget_Income_And_Bill_Summery_Loc(d);
		budgetSetting = new Budget_Settings_Loc(d);
	}

	@Test(description = "AT-85289:Dessert screen should come for a new user.", priority = 1, enabled = true)
	public void login() throws Exception {
		doInitialization("Budget_AcntClassificationUpdate_Test");
		LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("Budget", d);
		Assert.assertTrue(createBudget.isNoAccountScreenDisplayed());
	}

	@Test(description = "adding account.", dependsOnMethods = "login", priority = 2, enabled = true)
	public void addingAcnt() throws Exception {
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "ihjuly5.bank2", "bank2");
		PageParser.forceNavigate("Budget", d);
	}

	@Test(description = "AT-85290,AT-85310,AT-85307,AT-85300,AT-85305,AT-85298,AT-85304,AT-85299,AT-85302,AT-85306,AT-85303,AT-85308,AT-85309,AT-85301,AT-85316:Verify that once user added any bank, Card, investment account then user must be able to see the Create a Budget option in Budget page", priority = 3, dependsOnMethods = "addingAcnt", enabled = true)
	public void verifyAcntsContainer() throws Exception {
		SeleniumUtil.click(budgetNoActScreen.GetStartedButton());

		logger.info("Verify that in Create a budget page we must show 3 section such as Cash, Card and Investment once user has added atleast one bank containe");
		selctAcntPage.verifyAcntsContainers("ExpectedAcntsContainerLabel");

		logger.info("Verify that for Bank Container the Account type Checking,Moneymarket,cash,Unknown,Other,Prepaid,ira,CD,ppf,association,saving,recurring deposit must be shown under Cash Section of Create a Budget flow");
		int numberofAccountUnderCash=selctAcntPage.getTotalAccounts("Cash");
		
		Assert.assertEquals(numberofAccountUnderCash,Integer.parseInt(PropsUtil.getDataPropertyValue("AccountsUnderCashContainer").trim()));
	}

	@Test(description = "AT-85291,AT-85313:Verify that once user added a Bank container then in create a budget page under select Accounts there must be 3 section shown, with link account for Card and Investment account section ", priority = 4, dependsOnMethods = "verifyAcntsContainer", enabled = true)
	public void verifyWithCashContainer() throws Exception {

		logger.info("Verify that once user added a Bank container and create a budget using the bank container account later go to setting of that created budget then there must be 3 section shown, with link account for option Card and Investment account section, under bank section corresponding account must be shown");
		Assert.assertEquals(createBudget.NoAcntCardContainerText(),PropsUtil.getDataPropertyValue("ExcludedCardAcountsInfo").trim());
		Assert.assertEquals(createBudget.NoAcntInvestmentContainerText(),PropsUtil.getDataPropertyValue("ExcludedInvAcountsInfo").trim());
	}

	@Test(description = "AT-85322,AT-85292:Verify that once user added a Card container then in create a budget page under select Accounts there must be 3 section shown, with link account  for Bank and Investment account section ", dependsOnMethods = "addingAcnt", priority = 5, enabled = true)
	public void addingCardAcnt() throws Exception {
		logger.info("Verify that on clicking on Link Account, either in Card section or Investment section link FastLink floater should load and allow the user to link respective accounts accrodingly");
		
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(7000);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagCreditcard", "ihjuly5.creditCard1", "creditCard1");
		
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(3000);
		budgetNoActScreen.clickOnGetStartedButton();

		int numberofAccountUnderCards=selctAcntPage.getTotalAccounts("Cards");
		Assert.assertEquals(numberofAccountUnderCards,Integer.parseInt(PropsUtil.getDataPropertyValue("AccountsUnderCardContainer").trim()));
		Assert.assertTrue(createBudget.isNoAcntInvestmentContainer());
	}

	@Test(description = "AT-85323,AT-85292:Verify that once user added a Investment container then in create a budget page under select Accounts there must be 3 section shown, with link account for Bank and Card account section ", dependsOnMethods = "addingCardAcnt", priority = 6, enabled = true)
	public void addingInvAcnt() throws Exception {
		
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(7000);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagInvestments", "ihjuly5.Investment1", "Investment1");
		
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(3000);
		budgetNoActScreen.clickOnGetStartedButton();

		int numberofAccountUnderInvestments=selctAcntPage.getTotalAccounts("Investments");
		Assert.assertEquals(numberofAccountUnderInvestments,Integer.parseInt(PropsUtil.getDataPropertyValue("AccountsUnderInvestmentContainer").trim()));
	}

	@Test(description = "AT-85314:Verify that in Create a Budget page we have the check box in checked stage for all the containers except for Investment container", priority = 7, dependsOnMethods = {
			"addingInvAcnt", "addingCardAcnt", "addingAcnt" }, enabled = true)
	public void verifyCheckBoxStatus() {
		selctAcntPage.verifyCheckBoxStatus(selctAcntPage.checkBoxesForAcnts("Cash"));
		selctAcntPage.verifyCheckBoxStatus(selctAcntPage.checkBoxesForAcnts("Cards"));
		selctAcntPage.verifyInvestmentCBStatus(selctAcntPage.checkBoxesForAcnts("Investments"));
	}

	@Test(description = "Verify that in Create a Budget page we have the check box in checked stage for all the containers except for Investment container", priority = 8, dependsOnMethods = {
			"addingInvAcnt", "addingCardAcnt", "addingAcnt" }, enabled = true)
	public void addingManualCashAcnt() {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		accountAdd.addManualAccount("Cash", "PayTM Wallet", null, "10001", "1111", null, null, null);
	}

	@Test(description = "Verify that in Create a Budget page we have the check box in checked stage for all the containers except for Investment container", priority = 9, dependsOnMethods = {
			"addingInvAcnt", "addingCardAcnt", "addingAcnt" }, enabled = true)
	public void addingManualCardAcnt() {
		accountAdd.addManualAccount("Cards", "HDFC Platinum", null, "10002", "2222", "500", "date", null);
	}

	@Test(description = "Verify that in Create a Budget page we have the check box in checked stage for all the containers except for Investment container", priority = 10, dependsOnMethods = {
			"addingInvAcnt", "addingCardAcnt", "addingAcnt" }, enabled = true)
	public void addingManualInvAcnt() {
		accountAdd.addManualAccount("Investments", "Zerodha Fund", null, "10003", "3333", "50", null, null);
	}

	@Test(description = "AT-85319,AT-85320:Verify that for Manual accounts the Account Type of Cash,card,investment must be shown under respective(Cash,card and Investment)Section of Create a budget page ", priority = 11, dependsOnMethods = {
			"addingManualCashAcnt", "addingManualCardAcnt", "addingManualInvAcnt" }, enabled = true)
	public void verifyManualAcntSection() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(3000);
		budgetNoActScreen.clickOnGetStartedButton();
		
		int numberofAccountUnderCash=selctAcntPage.getTotalAccounts("Cash");
		Assert.assertEquals(numberofAccountUnderCash,Integer.parseInt(PropsUtil.getDataPropertyValue("UpdatedAccountsUnderCashContainer").trim()));

		logger.info("Verify that under Card section only card related account type of accounts must be shown");
		int numberofAccountUnderCards=selctAcntPage.getTotalAccounts("Cards");
		Assert.assertEquals(numberofAccountUnderCards,Integer.parseInt(PropsUtil.getDataPropertyValue("UpdatedAccountsUnderCardContainer").trim()));

		logger.info("Verify that under Investment section only Investment related account type of accounts must be shown");
		int numberofAccountUnderInvestments=selctAcntPage.getTotalAccounts("Investments");
		Assert.assertEquals(numberofAccountUnderInvestments,Integer.parseInt(PropsUtil.getDataPropertyValue("UpdatedAccountsUnderInvestmentContainer").trim()));
	}

	@Test(description = "creating Budget", priority = 12, dependsOnMethods = "verifyManualAcntSection", enabled = true)
	public void creatingBudget() {
		selctAcntPage.checkingAllInvAcnt();

		SeleniumUtil.click(createBudget.NextButtonText());
		SeleniumUtil.click(budgetIncomeBillScreen.nextToStep3Button());
		SeleniumUtil.click(budgetFlexibleSpendingScreen.BudgetSetUpDone());
		Assert.assertEquals(createdBudgetSummaryPage.createdBudgetHeader().getText().trim(),PropsUtil.getDataPropertyValue("Budget_MyBudget").trim());
	}

	@Test(description = "AT-85293:Verify that in Budget setting Account group also we have 4 section based on the accounts used by the user for creating the budget", priority = 13, dependsOnMethods = "creatingBudget", enabled = true)
	public void verifyAcntsContainerInSetting() {

		logger.info("verifying acconts container label cash,card and investment should be present in budget setting.");
		createdBudgetSummaryPage.clickOnsettingsIcon();
		SeleniumUtil.waitForPageToLoad(3000);
		budgetSetting.clickOnAcntGroupUnderSetting();
		SeleniumUtil.waitForPageToLoad(3000);
		budgetSetting.verifyAcntsContainerLabelUnderSetting("ExpectedAcntsContainerLabel");
	}

	@Test(description = "adding bill account", priority = 14, dependsOnMethods = "creatingBudget", enabled = true)
	public void addingBillsAcnt() throws AWTException, InterruptedException {
		SeleniumUtil.click(budgetSetting.BackToBudgetText());

		SeleniumUtil.waitForPageToLoad(3000);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBills", "ihjuly5.Bills1", "Bills1");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-85332:Verify that if user create a group which includes only the other container accounts, group must be created successfully but, must not be shown for Budget creation under budget page", priority = 15, dependsOnMethods = "addingBillsAcnt", enabled = true)
	public void verifyBudgetWithExcludedContainer() {

		SeleniumUtil.click(createBudget.CreateBudgetPlusIcon());
		SeleniumUtil.click(createBudget.NoAccountGroupButton());

		createBudget.creatingExcludeAcntGroup();
		Assert.assertTrue(createBudget.allBudgetedNoNewGroupScreen().isDisplayed());
	}

	@Test(description = "AT-85333,AT-85311,AT-85324:verify that once user select group we have 4 section shown to user when Card, Investment, Bank and other accounts and selected for budget creation", priority = 16, dependsOnMethods = "verifyBudgetWithExcludedContainer", enabled = true)
	public void creatingGroupWithAllAcnts() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(createBudget.NoAccountGroupButton());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Verify that if user create a group which includes atleast one cash or card or investment container account along with other container accounts then group must be created successfully and must be shown in Budget page while creating the budget");
		createBudget.createGroupWithAllAcnt();

		SeleniumUtil.waitForPageToLoad(3000);
		selctAcntPage.verifyAcntsContainers("ExpectedAcntsContainerLabelWithDiffGroup");

		logger.info("Verify that under Other section only the account type which of Others only must be shown (Such as Bills, rewards, Miles etc)");
		Assert.assertEquals(selctAcntPage.AcntUnderOtherContainer().getText().trim(),PropsUtil.getDataPropertyValue("AcntUnderOtherContainer").trim());
	}

	@Test(description = "AT-85312,AT-85315:Verify that in Budget setting Account group also we have 4 section based on the accounts used by the user for creating the budget", priority = 17, dependsOnMethods = "creatingGroupWithAllAcnts", enabled = true)
	public void verifyingCreatedBudget2Setting() {
		SeleniumUtil.click(createBudget.NextButtonText());
		SeleniumUtil.click(budgetIncomeBillScreen.nextToStep3Button());
		SeleniumUtil.click(budgetFlexibleSpendingScreen.BudgetSetUpDone());

		createdBudgetSummaryPage.clickOnsettingsIcon();
		SeleniumUtil.click(budgetSetting.AcntGroupUnderSetting());
		SeleniumUtil.waitForPageToLoad(3000);
		budgetSetting.verifyAcntsContainerLabelUnderSetting("ExpectedAcntsContainerLabelWithDiffGroup");
	}

	@Test(description = "AT-85321:Verify that appropriate message must be shown if no accounts are selected in the group", priority = 18, dependsOnMethods = "verifyingCreatedBudget2Setting", enabled = true)
	public void EditingBudgetedGroupWithExcludedAcnt() {

		budgetSetting.clickOneditBudgetedGroupBtn();
		budgetSetting.unselectAllAcntsFromGroup();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(createBudget.budgetExcludedAcnts().get(0));
		createBudget.clickOnCreateGroupButton();
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(budgetSetting.getNoRelevantAcntLabelText(),	PropsUtil.getDataPropertyValue("NoRelevantAcntLabel").trim());
	}

	@Test(description = "AT-85295:Verify that once user added a Card container and create a budget using the bank container account later go to setting of that created budget then there must be 3 section shown, with link account for option Bank and Investment account section, under Card section corresponding account must be shown ", priority = 19, dependsOnMethods = "EditingBudgetedGroupWithExcludedAcnt", enabled = true)
	public void EditingBudgetedGroup() {

		budgetSetting.clickOneditBudgetedGroupBtn();
		budgetSetting.creatingCardGroup();
		createBudget.clickOnCreateGroupButton();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(createBudget.NoAcntInvestmentContainerText(),PropsUtil.getDataPropertyValue("ExcludedInvAcountsInfo").trim());
		Assert.assertEquals(createBudget.NoAcntCashContainerText(),PropsUtil.getDataPropertyValue("ExcludedCashAcountsInfo").trim());
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