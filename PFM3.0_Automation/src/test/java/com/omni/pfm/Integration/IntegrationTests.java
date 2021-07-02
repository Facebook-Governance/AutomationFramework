/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Integration;

import java.util.List;

/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_Table_Loc;
import com.omni.pfm.pages.Categories.Manage_Categories_Loc;
import com.omni.pfm.pages.Dashboard.Account_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.SFG.SFG_CustomGoalEdit_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.Accounts.Account_ShowNoteMessage_Loc;
import com.omni.pfm.Accounts.Accounts_CloseAccount_Loc;
import com.omni.pfm.Accounts.Accounts_ViewByFI_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FinAppUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class IntegrationTests extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(IntegrationTests.class);
	String netTransferBeforeClose;
	String netTransferafterClose;
	String closedAccName;
	String UnsupAcc;
	String deletedAcc;

	AccountAddition accountAdd;
	AggregatedAccount_Settings_Loc account_Settings;
	AccountGroup_Settings_Loc groupSettingLoc;
	AccountAddition accountAddition;
	Account_Loc account;
	Accounts_ViewByFI_Loc viewByFI;
	NetWorth_Loc netWorth;
	SFG_CustomGoalEdit_Loc sfg;
	Account_ShowNoteMessage_Loc show;
	AccountAddition accAddition;
	ManualAccount_Settings_Loc settingsLoc;
	Transaction_Filter_Loc transaction_Filter;
	AggregatedAccount_Settings_Loc account_Settings_Loc;
	Accounts_CloseAccount_Loc closeAccounts;
	Manage_Categories_Loc manage_Categories_Loc = null;
	CashFlow_LandingPage_Loc LandingPage;
	CashFlow_Table_Loc table;
	Add_Manual_Transaction_Loc add_manual_transaction;
	Add_Manual_Transaction_Loc Addmanual_Transaction;
	ExpLandingPage_Loc ExpLandingPage;
	Transaction_Split_Locator split;
	AddToCalendar_Transaction_Loc AddToCalendar;
	Budget_Landing_Page_Loc Budget_Landingpage;
	FinAppUtil FinAppUtil;
	Budget_No_Account_Loc no_accounts;

	@BeforeClass()
	public void init() throws Exception

	{
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		no_accounts = new Budget_No_Account_Loc(d);
		FinAppUtil = new FinAppUtil(d);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		split = new Transaction_Split_Locator(d);
		ExpLandingPage = new ExpLandingPage_Loc(d);
		accountAddition = new AccountAddition();
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		account = new Account_Loc(d);
		settingsLoc = new ManualAccount_Settings_Loc(d);
		viewByFI = new Accounts_ViewByFI_Loc(d);
		account = new Account_Loc(d);
		netWorth = new NetWorth_Loc(d);
		sfg = new SFG_CustomGoalEdit_Loc(d);
		groupSettingLoc = new AccountGroup_Settings_Loc(d);
		show = new Account_ShowNoteMessage_Loc(d);
		accAddition = new AccountAddition();
		account_Settings = new AggregatedAccount_Settings_Loc(d);
		transaction_Filter = new Transaction_Filter_Loc(d);
		account_Settings_Loc = new AggregatedAccount_Settings_Loc(d);
		closeAccounts = new Accounts_CloseAccount_Loc(d);
		accountAdd = new AccountAddition();
		manage_Categories_Loc = new Manage_Categories_Loc(d);
		LandingPage = new CashFlow_LandingPage_Loc(d);
		table = new CashFlow_Table_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);

		doInitialization("Integration");

		LoginPage.loginMain(d, loginParameter);

		// L1NodeLogin.loginExistingUser(d, "PFM1529997027552", "Password#");
		// L1NodeLogin.loginExistingUser(d, "PFM1527592605129", "Password#");

		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("swin.site16441.2", "site16441.2");

	}

	@Test(description = "AT-81533:Verify that the Account balances shown for the respective accounts in accounts finapp should match with the balances shown in other finapps", priority = 1, groups = {
			"Desktop" })
	public void verifyingAccBalanceinDashboard() throws Exception {

		logger.info("Verifying Balance in DashboardPage.");
		SeleniumUtil.waitForPageToLoad();
		String Acc_balance1 = account.Account_Balance_Dash().getText();
		logger.info("Verifying Account balance in Dashboard is " + Acc_balance1);
		Assert.assertEquals(Acc_balance1, PropsUtil.getDataPropertyValue("Account_Balance_Accounts"));
	}

	@Test(description = "AT-81533:Verify that the Account balances shown for the respective accounts in accounts finapp should match with the balances shown in other finapps", priority = 2, groups = {
			"Desktop" })
	public void verifyingAccBalanceinAccounts() throws Exception {
		logger.info("Verifying Balance in AccountsPage.");
		PageParser.navigateToPage("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String Acc_balance2 = viewByFI.accounts_TotalBal_Acc().get(0).getText();
		Assert.assertEquals(Acc_balance2, PropsUtil.getDataPropertyValue("Account_Balance_Accounts"));

	}

	@Test(description = "AT-81533:Verify that the Account balances shown for the respective accounts in accounts finapp should match with the balances shown in other finapps", priority = 3, groups = {
			"Desktop" })
	public void verifyinNetworth() throws Exception {
		// Verifying in Networth Finapp

		netWorth.forceNavigateToNetWorth();
		logger.info("Verifying Balance in Networth.");
		logger.info(
				"Verifying that clicking on CONTINUE button in Welcome coachmark takes user to Link Accounts coackmark");
		SeleniumUtil.click(netWorth.continueButton_NW());
		logger.info(
				"Verified that clicking on CONTINUE button in Welcome coachmark takes user to Link Accounts coackmark");
		SeleniumUtil.click(netWorth.seeMyNetWorthButton());
		logger.info(
				"Verified that clicking on  SEE MY NET WORTH button takes user to NETWORTH screen and the coachmark disappears");

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.waitForPageToLoad();
		String Acc_balance3 = netWorth.Account_Bal_Networth().getText();
		Assert.assertEquals(Acc_balance3, PropsUtil.getDataPropertyValue("Account_Balance_Accounts"));
	}

	@Test(description = "AT-81533:Verify that the Account balances shown for the respective accounts in accounts finapp should match with the balances shown in other finapps", priority = 4, groups = {
			"Desktop" })
	public void verifyinSFG() {

		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(sfg.getStartedTab());
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(sfg.SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(sfg.getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(sfg.GoalNameInput());
		sfg.GoalNameInput().clear();
		sfg.GoalNameInput().sendKeys(PropsUtil.getDataPropertyValue("GoalName"));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(sfg.GoalAmountInput());
		sfg.GoalAmountInput().clear();
		sfg.GoalAmountInput().sendKeys(PropsUtil.getDataPropertyValue("GoalAmt"));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(sfg.GoalAmtFrqBtnInput());
		sfg.GoalAmtFrqInput().sendKeys(PropsUtil.getDataPropertyValue("GoalAmtFrqInput"));
		SeleniumUtil.click(sfg.frequencyDropdown1());
		SeleniumUtil.click(sfg.allFrequency().get(2));

		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(sfg.nextbuttonClick());

		SeleniumUtil.click(sfg.AccountDropDown1());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Verifying Balance in SFG.");
		SeleniumUtil.waitForPageToLoad();
		String Acc_balance4 = sfg.account_balance_SFG().getText();
		Assert.assertTrue(Acc_balance4.contains(PropsUtil.getDataPropertyValue("Budget_Amount")));
	}

	@Test(description = "AT-81534:Verify that the account type should be categorised same across all the finapps", priority = 5, groups = {
			"Desktop" })
	public void verifyAccTypeinDashBoard()

	{
		SeleniumUtil.click(sfg.closeIconinPopup());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(account.DashboardFin().get(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(sfg.QuitwithoutSaving());
		SeleniumUtil.waitForPageToLoad();

		// SeleniumUtil.click(account.DashboardFin().get(0));
		// PageParser.forceNavigate("DashboardPage", d);

		SeleniumUtil.waitForPageToLoad(4000);

		logger.info("Verifying AccountType in DashboardPage.");
		SeleniumUtil.waitForPageToLoad();
		// SeleniumUtil.click(account.ClickonFirstAcc());
		// SeleniumUtil.waitForPageToLoad();
		String Acc_type1 = account.Account_Type_Dash().getText();
		Assert.assertTrue(Acc_type1.contains(PropsUtil.getDataPropertyValue("Account_Type_Verifying")));

	}

	@Test(description = "AT-81534:Verify that the account type should be categorised same across all the finapps", priority = 6, groups = {
			"Desktop" })
	public void verifyAccTypeinAccounts()

	{
		logger.info("Verifying AccountType in AccountsPage.");
		PageParser.navigateToPage("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		// SeleniumUtil.click(account.DashboardFin().get(0));
		// PageParser.forceNavigate("DashboardPage", d);

		SeleniumUtil.waitForPageToLoad(4000);
		String Acc_type1 = viewByFI.accounts_Type_Acc().getText();
		Assert.assertTrue(Acc_type1.contains(PropsUtil.getDataPropertyValue("Account_Type_Verifying")));
	}

	@Test(description = "AT-81534:Verify that the account type should be categorised same across all the finapps", priority = 7, groups = {
			"Desktop" })
	public void verifyAccTypeinNetworth() throws Exception {
		// Verifying in Networth Finapp

		netWorth.forceNavigateToNetWorth();
		logger.info("Verifying Type in Networth.");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.click(netWorth.Account_CashView());
		String Acc_Type1 = netWorth.Account_Type_Networth().getText().toLowerCase();
		Assert.assertTrue(Acc_Type1.contains(PropsUtil.getDataPropertyValue("Account_Type_Verifying").toLowerCase()));
	}

	@Test(description = "AT-81536,AT-81576:Verify the settings made for accounts in account settings popup by the user the same should reflect in Account settings popup under global settings page", priority = 8, groups = {
			"Desktop" })
	public void verifyAccSettinAccFin() throws Exception {

		SeleniumUtil.click(groupSettingLoc.SettingsFin());
		SeleniumUtil.click(groupSettingLoc.AccGroupsMenu().get(0));
		SeleniumUtil.waitForElement(null, 4000);

		SeleniumUtil.click(groupSettingLoc.createGroupButtonTwo());
		SeleniumUtil.waitForPageToLoad();

		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("ACCGroup3").trim());
		SeleniumUtil.waitForPageToLoad();
		groupSettingLoc.getAccountOptionChckBox().get(2).click();
		groupSettingLoc.getAccountOptionChckBox().get(3).click();
		SeleniumUtil.waitForPageToLoad();

		groupSettingLoc.getAccountOptionChckBox().get(4).click();
		groupSettingLoc.getAccountOptionChckBox().get(4).click();
		String Attribute = groupSettingLoc.getAccountOptionChckBox().get(4).getAttribute("aria-checked");
		logger.info(Attribute);
		Assert.assertEquals(Attribute, PropsUtil.getDataPropertyValue("False"));

		SeleniumUtil.click(groupSettingLoc.createOrUpdateGroup());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupSettingLoc.SettingsFin());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupSettingLoc.AccSettingsMenu().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupSettingLoc.AccSettingsIcon().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupSettingLoc.NickNameBox());

		groupSettingLoc.NickNameBox().clear();
		String expected = PropsUtil.getDataPropertyValue("Nick_Name_Integ");
		logger.info(" NickName in AccountsPage" + expected);

		groupSettingLoc.NickNameBox().sendKeys(PropsUtil.getDataPropertyValue("Nick_Name_Integ").trim());
		SeleniumUtil.click(groupSettingLoc.SaveBtnInSettings());
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verifying NickName in AccountsPage.");
		PageParser.navigateToPage("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		for (int i = 0; i < groupSettingLoc.checkNickNameinAccFin().size(); i++) {
			if (expected.equals(groupSettingLoc.checkNickNameinAccFin().get(i).getText())) {
				logger.info("Nick Name in AccountsFin  is" + groupSettingLoc.checkNickNameinAccFin().get(i).getText());
				String actual = groupSettingLoc.checkNickNameinAccFin().get(i).getText();
				Assert.assertEquals(actual, expected);
			}
		}
	}

	@Test(description = "AT-81537:Verify that when user adds any new account, the respective account's  transactions should reflect in transaction finapp", priority = 9, groups = {
			"Desktop" })
	public void accAdditioninTransaction() throws Exception {

		SeleniumUtil.click(account.DashboardFin().get(0));
		SeleniumUtil.waitForPageToLoad();

		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite3_CF"),
				PropsUtil.getDataPropertyValue("dagPassword3_CF")));
		SeleniumUtil.waitForPageToLoad(5000);

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(4000);

		String expected = groupSettingLoc.transAccinAccFin().get(0).getText();
		logger.info("In Accounts fin" + expected);
		SeleniumUtil.click(groupSettingLoc.transAccinAccFin().get(0));
		SeleniumUtil.waitForPageToLoad(4000);

		String actual = transaction_Filter.transAccinTransFin().getText();
		logger.info("In Transaction fin" + actual);
		// PageParser.navigateToPage("Transaction", d);
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "AT-81538:Verify that if user made any changes in account settings, the same should show in all other finapps", priority = 10, groups = {
			"Desktop" })
	public void verifyAccSettChangesinAccFin() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(account_Settings.morebtn_AccFin().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(account_Settings.AccountSettingList(1));
		SeleniumUtil.waitForPageToLoad();

		account_Settings.nickNameTextBox().click();
		account_Settings.nickNameTextBox().clear();
		String actual = PropsUtil.getDataPropertyValue("nick_name_allfinapps");
		account_Settings.nickNameTextBox().sendKeys(PropsUtil.getDataPropertyValue("nick_name_allfinapps"));
		String expected = account_Settings.nickNameTextBox().getAttribute("value");
		SeleniumUtil.click(account_Settings.saveChangesBtn());
		Assert.assertEquals(actual, expected);

	}

	@Test(description = "AT-81538:Verify that if user made any changes in account settings, the same should show in all other finapps", priority = 11, groups = {
			"Desktop" })
	public void VerifyAccSettChangesinDashFin() throws Exception {

		SeleniumUtil.click(account.DashboardFin().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		logger.info("Verifying NickName in DashboardPage.");
		SeleniumUtil.waitForPageToLoad();
		String nickname = PropsUtil.getDataPropertyValue("nick_name_allfinapps");
		for (int i = 0; i < account.nickNameList().size(); i++) {
			String nicknameinDash = null;
			if (nickname.equals(account.nickNameList().get(i).getText())) {
				nicknameinDash = account.nickNameList().get(i).getText();
				logger.info("Nick Name in Dashboard is" + account.nickNameList().get(i).getText());
				Assert.assertEquals(nicknameinDash, PropsUtil.getDataPropertyValue("nick_name_allfinapps"));

			}

		}

	}

	@Test(description = "AT-81538:Verify that if user made any changes in account settings, the same should show in all other finapps", priority = 12, groups = {
			"Desktop" })
	public void VerifyAccSettChangesinAccSetFin() throws Exception {

		SeleniumUtil.click(groupSettingLoc.SettingsFin());
		SeleniumUtil.click(groupSettingLoc.AccSettingsMenu().get(0));
		SeleniumUtil.waitForPageToLoad();
		String nickname = PropsUtil.getDataPropertyValue("nick_name_allfinapps");

		for (int i = 0; i < account_Settings.nickNameinAccSetFin().size(); i++) {
			if (nickname.equals(account_Settings.nickNameinAccSetFin().get(i).getText())) {
				logger.info("Nick Name in Dashboard is" + account_Settings.nickNameinAccSetFin().get(i).getText());
				String nicknameinSet = account.nickNameList().get(i).getText();
				Assert.assertEquals(nicknameinSet, PropsUtil.getDataPropertyValue("nick_name_allfinapps"));

			}
		}
	}

	@Test(description = "AT-81539,AT-81578:Verify the settings made for accounts in account Alert settings popup by the user the same should reflect in Account Alert settings popup under global settings page", priority = 13, groups = {
			"Desktop" })
	public void VerifyAlertSettingsinAccsandAccSetFin() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		for (int j = 0; j < show.AccListinPlcHlder().size(); j++) {
			// SeleniumUtil.waitForPageToLoad();

			if ((show.AccListinPlcHlder().get(j).getText()).equals(PropsUtil.getDataPropertyValue("alert_acc"))) {
				SeleniumUtil.waitForPageToLoad();
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.click(show.alert_option());
				SeleniumUtil.waitForPageToLoad();
				SeleniumUtil.click(show.togglebtn().get(0));
				SeleniumUtil.waitForPageToLoad();
				SeleniumUtil.click(show.savechangesinAlertSet());
				break;

			}
		}
		SeleniumUtil.click(groupSettingLoc.SettingsFin());
		SeleniumUtil.click(account.AlertSettingsMenu().get(0));
		SeleniumUtil.waitForPageToLoad();
		for (int j = 0; j < account.AccnameListinAccSet().size(); j++) {

			if ((account.AccnameListinAccSet().get(j).getText()
					.contains(PropsUtil.getDataPropertyValue("alert_acc")))) {
				SeleniumUtil.click(account.AccnameListinAccSet().get(j));
				SeleniumUtil.waitForPageToLoad(2000);
				Assert.assertTrue(account_Settings.AlertTogOnSettFin().get(0).isEnabled());
			}
		}
	}

	@Test(description = "AT-81540:Verify that if the user adds any manual account with some balance in to it, the transaction finapp should show opening balance transaction for that respective manual account with the same balance", priority = 14, groups = {
			"Desktop" })
	public void VerifyManualAccChkinTranFin1() throws Exception {

		SeleniumUtil.click(account.CancelinAlertSet());
		PageParser.forceNavigate("AccountsPage", d);
		logger.info("Adding Manual Account for Bank Container.");
		accountAdd.addManualAccount("Cash", "Manual Account", "this string is ", "2500", "null", "null", "null",
				"null");
		d.navigate().refresh();
		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		String Manual_AccinAccFin = account_Settings.Manual_account_AccFin().get(1).getText();
		logger.info("Manual Account in AccFin is " + Manual_AccinAccFin);

		SeleniumUtil.waitForPageToLoad();
		logger.info("Navigating to Transactions Page");
		PageParser.navigateToPage("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.AllAccType_tran());
		SeleniumUtil.waitForPageToLoad();

		String ManualAccinTrans = add_manual_transaction.ManualAccinTransFin().get(0).getText();
		logger.info("Manual Account in TransactionFin is " + ManualAccinTrans);

		Assert.assertTrue(ManualAccinTrans.startsWith(Manual_AccinAccFin));
	}

	@Test(description = "AT-81541:Verify that If user adds any manual asset or liability account, the balance should get reflected in the Transaction and Net worth finapps", priority = 15, groups = {
			"Desktop" })
	public void manualAccChkinTranFin2() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		accountAddition.addManualAccount("Other Assets", "MyAccountother_assets", "null", "100011", "null", "null",
				"null", "null");
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addManualAccount("Other Liabilities", "MyAccountother_liabilities", "null", "100012", "null",
				"null", "null", "null");
		SeleniumUtil.waitForPageToLoad();

		netWorth.forceNavigateToNetWorth();
		logger.info("Verifying Other Assets in Networth.");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.waitForPageToLoad();

		String other_asset = netWorth.Other_Asset_Networth().getText();
		logger.info("Other Asset is " + other_asset);
		Assert.assertTrue(other_asset.contains(PropsUtil.getDataPropertyValue("Other_asset_Nw")));

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());

		SeleniumUtil.click(netWorth.expandLiabilitiesToViewAcc());
		logger.info("Verifying Other Liability in Networth.");

		String other_liability = netWorth.Other_Liability_Networth().getText();
		logger.info("Other Liablility is " + other_liability);
		Assert.assertTrue(other_liability.contains(PropsUtil.getDataPropertyValue("Other_liability_Nw")));
	}

	@Test(description = "AT-81542:Verify that if the user adds any manual cash or checking accounts, the balances should reflect in the Transaction, IA, Net worth, Cash flow finapps", priority = 16, groups = {
			"Desktop" })
	public void VerifyManualAccChkinNWFin1() throws Exception {

		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verifying Cash Account in Networth.");
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.click(netWorth.Account_CashView());
		SeleniumUtil.click(netWorth.CashAcc_Networth());

		String cash_NW = netWorth.CashAcc_Networth().getText();
		logger.info("Cash Account is " + cash_NW);
		Assert.assertTrue(cash_NW.contains(PropsUtil.getDataPropertyValue("cash_NW")));
	}

	@Test(description = "AT-81542:Verify that if the user adds any manual cash or checking accounts, the balances should reflect in the Transaction, IA, Net worth, Cash flow finapps", priority = 17, groups = {
			"Desktop" })
	public void VerifyManualAccChkinTranFin2() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String ManualAccDetails = account_Settings_Loc.ManualAccInfo().getText();
		logger.info("Manual Account Details is " + ManualAccDetails);

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_manual_transaction.tranRowsinTran().size(); i++) {
			if (ManualAccDetails.contains(add_manual_transaction.tranRowsinTran().get(i).getText())) {
				Assert.assertTrue(ManualAccDetails.contains(add_manual_transaction.tranRowsinTran().get(i).getText()));
			}
		}
	}

	@Test(description = "AT-81543:Verify that if the user adds any manual Loan account the balance should reflect in Transaction and Net worth finapp", priority = 18, groups = {
			"Desktop" })
	public void manualloanAcc() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		accountAddition.addManualAccount("Loans", "MyAccountother_assets", "null", "100030", "null", "null", "null",
				"null");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String Loans_Acc = viewByFI.Loans_Acc().get(0).getText();
		logger.info("Loan " + Loans_Acc);
		Assert.assertTrue(Loans_Acc.contentEquals(PropsUtil.getDataPropertyValue("Loans_AccName")));

		logger.info("Navigating to Transactions Page");
		PageParser.navigateToPage("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.AllAccType_tran());
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_manual_transaction.ManualAccinTransFin().size(); i++) {
			if (Loans_Acc.equals(add_manual_transaction.ManualAccinTransFin().get(i).getText())) {
				logger.info(
						"If Rewards are shown in Transaction " + add_manual_transaction.ManualAccinTransFin().get(i));
			}
			Assert.assertFalse(add_manual_transaction.ManualAccinTransFin().get(i).getText().contains(Loans_Acc));

		}
	}

	@Test(description = "AT-81544:Verify that if user adds any Manual rewards account it should not show in any other Finapp except in Accounts finapp", priority = 19, groups = {
			"Desktop" })
	public void VerifyManualRewardAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		accountAddition.addManualAccount("Rewards", "MyAccountother_assets", "null", "100030", "null", "null", "null",
				"null");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String reward_Acc = viewByFI.Reward_Acc().get(0).getText();
		logger.info("Rewards acc is " + reward_Acc);
		Assert.assertTrue(reward_Acc.contentEquals(PropsUtil.getDataPropertyValue("Reward_AccName")));

		logger.info("Navigating to Transactions Page");
		PageParser.navigateToPage("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.AllAccType_tran());
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_manual_transaction.ManualAccinTransFin().size(); i++) {
			if (reward_Acc.equals(add_manual_transaction.ManualAccinTransFin().get(i).getText())) {
				logger.info(
						"If Rewards are shown in Transaction " + add_manual_transaction.ManualAccinTransFin().get(i));
			}
			Assert.assertFalse(add_manual_transaction.ManualAccinTransFin().get(i).getText().contains(reward_Acc));

		}
	}

	@Test(description = "AT-81545:Verify that If user adds any manual Billing type of account, it should not show in any other finapp except accounts finapp\r\n"
			+ "E.g.: Manual Cabel/Satellite or Manual Bills etc. accounts", priority = 20, groups = { "Desktop" })
	public void VerifyManualBillingAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		accountAddition.addManualAccount("Bills", "MyAccountother_bills", "SSV", "1002", "10023000", "100", "date",
				null);
		SeleniumUtil.waitForPageToLoad();
		String Bills_Acc = "MyAccountother_bills";
		logger.info("Rewards acc is " + Bills_Acc);
		logger.info("Navigating to Transactions Page");
		PageParser.navigateToPage("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.AllAccType_tran());
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_manual_transaction.ManualAccinTransFin().size(); i++) {
			if (Bills_Acc.equals(add_manual_transaction.ManualAccinTransFin().get(i).getText())) {
				logger.info(
						"If Rewards are shown in Transaction " + add_manual_transaction.ManualAccinTransFin().get(i));
			}
			Assert.assertFalse(add_manual_transaction.ManualAccinTransFin().get(i).getText().contains(Bills_Acc));
		}
	}

	@Test(description = "AT-81546:Verify that if user adds Manual insurance account, the balance should reflect in Net worth finapp", priority = 21, groups = {
			"Desktop" })
	public void VerifyManualInsuranceAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addManualAccount("Insurance", "My_Insurance", "SSV", "20000", "10023000", null, null, null);

		logger.info("Cliking on More options dotted lines for Manual Insurance Account added for Insurance Container.");
		String Insurance_Acc = "My_Insurance";
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verifying Insurance Account in Networth.");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.click(netWorth.Account_CashView());
		SeleniumUtil.click(netWorth.Insurance_Networth());
		Assert.assertTrue(netWorth.Insurance_AccName_NW().getText().contains(Insurance_Acc));
	}

	@Test(description = "AT-81547:Verify that if user adds any manual card account, the balance should reflect in EA, Net worth and Cash flow finapps", priority = 22, groups = {
			"Desktop" })
	public void VerifyManualCardAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addManualAccount("Cards", "My_cards", "SSV", "134300", "10023000", "100", "date", null);
		SeleniumUtil.waitForPageToLoad();
		String MyCards_Acc = "My_cards";

		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verifying Insurance Account in Networth.");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandLiabilitiesToViewAcc());
		SeleniumUtil.click(netWorth.Cards_NW());
		String MyCardsAcc_NW = netWorth.Mycards_NW().getText();
		Assert.assertTrue(MyCardsAcc_NW.contains(MyCards_Acc));
	}

	@Test(description = "AT-81548:Verify that if user adds Investment transaction, the amount should reflect in Cash flow finapp as Transfers", priority = 23, groups = {
			"Desktop" })
	public void VerifyManualInvestmentAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String actual = "SUNY 403(B) PROGRAM";
		Assert.assertTrue(
				FinAppUtil.institutionViewVerifyAccountIsPresentUnderContainer("SUNY 403(B) PROGRAM", "Investments"));

		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		logger.info("Verifying Insurance Account in Networth.");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(netWorth.Invest_NW());
		SeleniumUtil.waitForPageToLoad();

		String MyInvestment_NW = netWorth.My_Invest_NW().getText();
		Assert.assertTrue(MyInvestment_NW.contains(actual));

	}

	@Test(description = "AT-81549:Verify that if the  user adds any transaction to aggregated account, then the aggregated account balances should not get changed ", priority = 24, groups = {
			"Desktop" })
	public void VerifyAggAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String Acc_balanc1 = viewByFI.accounts_TotalBal_Acc().get(0).getText();

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		add_manual_transaction.createTransaction("10000", "School Fees", 1, 1, 1, 1, 1);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String Acc_balance2 = viewByFI.accounts_TotalBal_Acc().get(0).getText();
		Assert.assertEquals(Acc_balance2, Acc_balanc1);
	}

	@Test(description = "AT-81550:Verify that if the user adds any transaction to Manual account, then the Manual account balance should get changed accordingly ", priority = 25, groups = {
			"Desktop" })
	public void VerifyManualTransAcc() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		String Manual_AccBal_before = account_Settings_Loc.accounts_TotalBal_Acc().get(2).getText();
		logger.info("Manual Account balance before adding an account in AccFin is " + Manual_AccBal_before);

		accountAdd.addManualAccount("Cash", "Manual Account", "null", "2500", "null", "null", "null", "null");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		String Manual_AccBal_after = account_Settings_Loc.accounts_TotalBal_Acc().get(2).getText();
		logger.info("Checking  manual acc balance" + Manual_AccBal_after);

		Assert.assertNotEquals(Manual_AccBal_after, Manual_AccBal_before);

	}

	@Test(description = "AT-81551:Verify that when user closes an account, the balance should set to be zero and the same should get reflected in Net worth finapp ", priority = 26)
	public void VerifyClosedAccinTransFin() throws Exception {
		// getting transaction details in Cashflow and will check there is no
		// transactions difference after closing an account in cashflow again

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		String transAfterClosed = table.netTransfersList().getText().trim();
		// netTransferBeforeClose = Float.parseFloat(transAfterClosed);
		netTransferBeforeClose = transAfterClosed;

		logger.info("Before closing an account transactions " + netTransferBeforeClose);

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(account_Settings.morebtn_AccFin().get(3));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(account_Settings.AccountSettingList(1));

		SeleniumUtil.waitForPageToLoad();

		// FinAppUtil.intitutionViewClickOnAccountSettings("IQ sWins Unknown", "Cash");
		closedAccName = PropsUtil.getDataPropertyValue("close_AccName");

		SeleniumUtil.click(show.AccSetClseBtn());
		SeleniumUtil.click(show.clsAccConfirmBtn());
		SeleniumUtil.waitForPageToLoad();

		// Verifying in Networth Finapp

		netWorth.forceNavigateToNetWorth();

		logger.info("Verifying Type in Networth.");
		SeleniumUtil.waitForPageToLoad();
		String zeroBalData = PropsUtil.getDataPropertyValue("ZeroBalData");
		SeleniumUtil.click(netWorth.expandAssetsToViewAcc());
		SeleniumUtil.click(netWorth.Account_CashView());
		String zeroBal = netWorth.Zero_balinNW().getText();
		Assert.assertTrue(zeroBal.equals(zeroBalData));
	}

	@Test(description = "AT-81552:Verify that, when user closed an account, a transaction should be generated by system as \"System generated transaction to set zero balance for Savings \" with the last available balance in the amount field ", priority = 27)
	public void VerifyClosedAccSystemGen() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		String actual = PropsUtil.getDataPropertyValue("SystemGenTrans");
		String expected = add_manual_transaction.SystemGenTrans().get(3).getText();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "AT-81553:Verify that when user closes an account, the balance should get reflected in Cash flow finapp as net transfer ", priority = 28)
	public void VerifyClosedAccinCashLow() throws Exception {

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		String transAfterClosed = table.netTransfersList().getText().trim();
		netTransferafterClose = transAfterClosed;

		// netTransferafterClose = Float.parseFloat(transAfterClosed);
		logger.info("Checking the Net Transfer amount before and after closing an account reflects same");
		Assert.assertNotEquals(netTransferafterClose, netTransferBeforeClose);
	}

	@Test(description = "AT-81554:Verify that after closing an account the respective account should not be available in the Budget finapp.", priority = 29)
	public void VerifyClosedAccinBudget() throws Exception {

		PageParser.navigateToPage("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		String closed_acc = "IQ sWins CD";
		String budget_acc = no_accounts.budget_Acc_container().getText();
		Assert.assertFalse(budget_acc.contains(closed_acc));
	}

	@Test(description = "AT-81555:Verify that after deleting an account the respective account should not be available in the Budget finapp ", priority = 30)
	public void VerifyDeletedAccinBudget() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(account_Settings.morebtn_AccFin().get(3));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(account_Settings.AccountSettingList(3));

		SeleniumUtil.waitForPageToLoad();

		// FinAppUtil.intitutionViewClickOnAccountSettings("IQ sWins Unknown", "Cash");
		closedAccName = PropsUtil.getDataPropertyValue("close_AccName");

		SeleniumUtil.click(show.ma_deleteAcc());
		SeleniumUtil.click(show.ma_delConCheck());

		SeleniumUtil.click(show.ma_delConBtn());

		SeleniumUtil.waitForPageToLoad();

		PageParser.navigateToPage("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		String deleted_acc = "IQ Ferrari";
		String budget_acc = no_accounts.budget_Acc_container().getText();
		Assert.assertFalse(budget_acc.contains(deleted_acc));
	}

	@Test(description = "AT-81557:Verify if user delete the Account, same should not reflect in the dashboard accounts widget  ", priority = 31)
	public void VerifydeletedAccinDashWidget() throws Exception {

		SeleniumUtil.click(account.DashboardFin().get(0));
		SeleniumUtil.waitForPageToLoad();
		deletedAcc = PropsUtil.getDataPropertyValue("Deleted_Acc");

		logger.info("Verifying Deleted Account not reflect in DashboardPage.");
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < account.accountName().size(); i++) {
			String accname = null;

			if (deletedAcc.equals(account.accountName().get(i).getText())) {
				logger.info("Nick Name in Dashboard is" + account.accountName().get(i).getText());
				accname = account.accountName().get(i).getText();
			}
			Assert.assertFalse(accname.contains(deletedAcc));

		}
	}

	@Test(description = "AT-81558:Verify the Spent amount get updated after adding the transactions for current month to the budgeted category  ", priority = 32)
	public void VerifyAddedTransinBudget() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		// SeleniumUtil.click(add_manual_transaction.accountForTrans().get(0));
		// add_manual_transaction.createTransaction("10000", "Trip", 0, 1,1,0, 1);
		SeleniumUtil.waitForPageToLoad();

		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgorie());
		String Acc_balance1 = add_manual_transaction.catgoryList(44, 1).getText();
		SeleniumUtil.click(add_manual_transaction.catgoryList(44, 1));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.add());

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		String Acc_balance2 = viewByFI.accounts_TotalBal_Acc().get(0).getText();
		Assert.assertEquals(Acc_balance2, Acc_balance1);

	}

	@Test(description = "AT-81559:Verify that if any projected transaction is changed to Posted transaction, it's presence should be reflecting on Other finapps (Transaction, Expense, Income, Cash flow, Budget)   ", priority = 33)
	public void ProjectTransintoPostTransinTransFin() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.ProjectedExapdIcon());
		String proj_acc = add_manual_transaction.Project_accounts().get(0).getText();
		SeleniumUtil.click(add_manual_transaction.Project_accounts().get(0));

		if (add_manual_transaction.Project_accounts().contains(add_manual_transaction.MarkasPaidBtn_proj())) {
			add_manual_transaction.Proj_sche_date().clear();
			add_manual_transaction.Proj_sche_date().sendKeys(add_manual_transaction.targateDate1(-3));
			SeleniumUtil.waitForPageToLoad(1000);
			SeleniumUtil.click(add_manual_transaction.Proj_saveBtn());
			SeleniumUtil.waitForPageToLoad(3000);
			SeleniumUtil.waitForPageToLoad();
		} else {

			SeleniumUtil.click(add_manual_transaction.MarkasPaidBtn_proj().get(0));
			SeleniumUtil.waitForPageToLoad();
		}
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_manual_transaction.Project_accounts().size(); i++) {
			if (add_manual_transaction.Project_accounts().get(i).getText().contains(proj_acc)) {
				logger.info("Projected transaction is present in Post Transaction");
				Assert.assertTrue(add_manual_transaction.Project_accounts().get(i).isDisplayed());

			}
		}
	}

	@Test(description = "AT-81560:Verify that on editing a category(change category name) in the categories page under global settings, the changed category name should be shown to the user across the finapps where ever category dropdown is displayed(add transaction, edit transaction, add to calendar etc.)", priority = 34)
	public void EditCatinSettFin() throws Exception {

		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(manage_Categories_Loc.manage_Showas_name());
		SeleniumUtil.waitForPageToLoad();

		manage_Categories_Loc.editMLCatInputField().clear();
		manage_Categories_Loc.editMLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditText"));
		String text = manage_Categories_Loc.editMLCatInputField().getAttribute("value");
		Assert.assertTrue(text.length() == 40);
		manage_Categories_Loc.editMLCatInputField().clear();
		manage_Categories_Loc.editMLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_Editname_int"));
		SeleniumUtil.waitForPageToLoad();

		manage_Categories_Loc.saveChangesButtonInMLC().click();
		SeleniumUtil.waitForPageToLoad();
		String updatedmlcNameOne = manage_Categories_Loc.getMLCName(1, 1);
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(updatedmlcNameOne, PropsUtil.getDataPropertyValue("Category_Editname_int"));
	}

	@Test(description = "AT-81561:Verify that If user adds any subcategories, the same should display in category dropdown across finapps \r\n"
			+ "E.g.: Category dropdown in Add transaction, Edit transactions, Add to calendar, Account settings etc.", priority = 35)
	public void EditSubCatinSettFin() throws Exception {

		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(manage_Categories_Loc.expenseCatLink());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));

		SeleniumUtil.waitForPageToLoad(3000);

		String subCatName_Categ = PropsUtil.getDataPropertyValue("Category_SubCatName_int");
		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_SubCatName_int"));

		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		SeleniumUtil.click(manage_Categories_Loc.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.addManualLink());
		SeleniumUtil.click(add_manual_transaction.categoryV());
		for (int i = 0; i < add_manual_transaction.Sub_Cat_int().size(); i++) {
			if (subCatName_Categ.contains(add_manual_transaction.Sub_Cat_int().get(i).getText())) {

				logger.info("Sub category name is present in Transaction Category list");
				Assert.assertTrue(subCatName_Categ.contains(add_manual_transaction.Sub_Cat_int().get(i).getText()));

			}

		}
	}

	@Test(description = "AT-81562:Verify that If user adds any withdrawal type of transactions, the same should reflect in EA,Cashflow finapp as out flow ", priority = 36)
	public void WithdrawTransinExpenseFin() throws Exception {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		String withdraw_acc = PropsUtil.getDataPropertyValue("withdraw_accName");
		for (int i = 0; i < ExpLandingPage.WithdrawAcc_name_NW().size(); i++) {
			if (withdraw_acc.contains(ExpLandingPage.WithdrawAcc_name_NW().get(0).getText())) {
				logger.info("withdraw type transaction is available"
						+ ExpLandingPage.WithdrawAcc_name_NW().get(i).getText());
				Assert.assertTrue(withdraw_acc.contains(ExpLandingPage.WithdrawAcc_name_NW().get(0).getText()));
			}
		}
	}

	@Test(description = "AT-81563:Verify that If user adds any deposit type of transactions, the same should reflect in EA,Cashflow finapp as Cash inflow  ", priority = 37)
	public void DepositTransinEAFin() throws Exception {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.Title_Btnclick_NW());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ExpLandingPage.Inconme_Menuclick_NW());
		SeleniumUtil.waitForPageToLoad();

		String deposit_Acc = PropsUtil.getDataPropertyValue("deposit_acc");
		for (int i = 0; i < ExpLandingPage.Deposit_name_NW().size(); i++) {
			if (deposit_Acc.contains(ExpLandingPage.Deposit_name_NW().get(0).getText())) {
				logger.info(
						"withdraw type transaction is available" + ExpLandingPage.Deposit_name_NW().get(i).getText());
				Assert.assertTrue(deposit_Acc.contains(ExpLandingPage.Deposit_name_NW().get(0).getText()));
			}
		}

		ExpLandingPage.featureTourBtn().click();
		SeleniumUtil.waitForPageToLoad();
		ExpLandingPage.continueButton().click();
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-81564,INTE-01_35:Verify that if the user deletes a transaction for manual account, then the balance should get adjusted and the other finapps also should reflect the change  ", priority = 38)
	public void AddedManualTransinNWFin() throws Exception {

		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(5000);
		deletedAcc = PropsUtil.getDataPropertyValue("Deleted_Acc");

		logger.info("Verifying Deleted Account not reflect in Networth.");
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < netWorth.Acc_namelist_NW().size(); i++) {
			String accname = null;

			if (deletedAcc.equals(netWorth.Acc_namelist_NW().get(i).getText())) {
				logger.info("Nick Name in Dashboard is" + netWorth.Acc_namelist_NW().get(i).getText());
				accname = netWorth.Acc_namelist_NW().get(i).getText();
				Assert.assertFalse(accname.contains(deletedAcc));

			}

		}
	}

	@Test(description = "AT-81565:Verify that if user edits a transaction and changes the category of it, the same should reflect in the expense analysis and income analysis and budget  ", priority = 39)
	public void EditManualTransinBudget() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.addManualLink());

		add_manual_transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("ThisMonthExp"),
				PropsUtil.getDataPropertyValue("TransacNote"), 1, 1, 0, 7, 1, 1,
				PropsUtil.getDataPropertyValue("TransacNote"));
		SeleniumUtil.waitForPageToLoad();
		String tran_name = PropsUtil.getDataPropertyValue("TransacNote");

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.ProjectedExapdIcon());
		SeleniumUtil.click(add_manual_transaction.Project_accounts().get(0));

		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(add_manual_transaction.Cat_input());
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.Cat_input_text().sendKeys(PropsUtil.getDataPropertyValue("EditCat_Travel"));

		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(add_manual_transaction.Proj_saveBtn());

		SeleniumUtil.waitForPageToLoad();

		PageParser.navigateToPage("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		// SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(no_accounts.Travel_Acc());
		String travel_acc = no_accounts.Travel_trninBud().getText();
		Assert.assertEquals(travel_acc, PropsUtil.getDataPropertyValue("EditCat_Travel"));

	}

	@Test(description = "AT-81575:Verify that Adding unsupported accounts should not impact IH and Net worth finapps \r\n"
			+ "E.g.: Manual bills account should not appear in Net worth filters  ", priority = 40)
	public void ManualAccNotAppearinNW() throws Exception {

		// added bills account has to verify it is not present in networth fin

		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		UnsupAcc = PropsUtil.getDataPropertyValue("UnsupportedAcc");
		SeleniumUtil.click(netWorth.allAccountsDropDown());
		for (int i = 0; i < netWorth.AllAcc_dispname_NW().size(); i++) {
			if (UnsupAcc.contains(netWorth.AllAcc_dispname_NW().get(i).getText())) {
				Assert.assertFalse(UnsupAcc.contains(netWorth.AllAcc_dispname_NW().get(i).getText()));

			}

		}
	}

	@Test(description = "AT-81579:Verify that the total expense shown in expense by category under a specific category should match with the sum of transactions of that specfic category e.g. if automobile expenses is $1000 in expense analysis then the sum of categorized transactions as automobile expenses will also should be $1000 for the said period", priority = 41)
	public void TotalExp() throws Exception {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		String Travel_exp_table = ExpLandingPage.Trav_exp_table().getText();
		logger.info("Travel acc name in expense is " + Travel_exp_table);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.AllCat_tran());

		// SeleniumUtil.click(add_manual_transaction.seselectCategory());
		SeleniumUtil.click(add_manual_transaction.AllAcc_Checbox());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(add_manual_transaction.AllAcc_TravelClick());
		SeleniumUtil.waitForPageToLoad();
		String first_Travel = add_manual_transaction.tranRowsinTran().get(0).getText();
		logger.info("Travel acc name in Transaction is" + first_Travel);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(Travel_exp_table.contains(first_Travel));
	}

	@Test(description = "AT-81581:Verify that all the sums(amount) displayed in the cash flow finapp should match with the transactions in the transaction finapp", priority = 42)
	public void sumsInCashFlowWithTransFin() throws Exception {

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		String cashInFlow = table.CashInflow_Int().getText().trim().replaceAll(",", "");
		cashInFlow = cashInFlow.substring(
				cashInFlow.indexOf(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth")) + 1,
				cashInFlow.length());
		logger.info("The Amount in Cash Flow page is  " + cashInFlow);

		SeleniumUtil.click(table.CashInflow_Int());
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> listofTrans = add_manual_transaction.ListofTrans();
		double sum = 0.0, value1 = 0.0;
		for (int i = 0; i < listofTrans.size(); i++) {
			String actualAmt = listofTrans.get(i).getText().trim().replaceAll(",", "");
			logger.info("Actual Transactions Value of Amount in Transactions " + actualAmt);
			actualAmt = actualAmt.substring(
					actualAmt.indexOf(PropsUtil.getDataPropertyValue("ValueOfAmtAnfPerOfCurrMonth")) + 1,
					actualAmt.length());

			double value = Double.parseDouble(actualAmt);
			logger.info("Actual Value of Amounts in Transaction are " + value);

			if (sum != value1) {
				sum = sum + value;
			} else {
				sum = value;
			}

		}

		double cash = Double.parseDouble(cashInFlow);
		cash = Math.round(cash);
		sum = Math.round(sum);
		logger.info("Total Value of Income in Transaction is " + sum);
		Assert.assertEquals(String.valueOf(cash), String.valueOf(sum));
	}
}
