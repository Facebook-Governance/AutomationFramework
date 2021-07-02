/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.

 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author Abhinandan
 ******************************************************************************/

package com.omni.pfm.MRBugs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.Accounts.Accounts_ManualAcnt_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.TEACABugs.TEACABugs_Loc;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Categories.Manage_Categories_Loc;
import com.omni.pfm.pages.CategorizationRules.CategorizationRules_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.FINameConstants;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class MRBugs_Loc extends TestBase {

	private static WebDriver d;
	private static final Logger logger = LoggerFactory.getLogger(MRBugs_Loc.class);
	static final String frameName = null;

	public AggregatedAccount_Settings_Loc account_Settings_Loc;
	public ManualAccount_Settings_Loc settingsLoc;
	Manage_Categories_Loc manage_Categories_Loc = null;
	MRBugs_Loc MRBugs_Loc = null;
	Accounts_ManualAcnt_Loc manualAcnt;
	String originalName = "";
	Budget_Landing_Page_Loc Budget_Landingpage;
	Budget_Select_Accounts_Loc select_accounts;
	Budget_Income_And_Bill_Loc Budget_Income_Bill;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_Income_And_Bill_Summery_Loc Budget_Income_Bill_Summery;
	Budget_No_Account_Loc no_accounts;
	CashFlow_LandingPage_Loc LandingPage;
	Add_Manual_Transaction_Loc add_manual_transaction;
	ExpLandingPage_Loc ExpLandingPage;
	Transaction_Categorization_Rules_Loc rule1;
	NetWorth_Loc netWorth = null;
	Aggregated_Transaction_Details_Loc Aggre_Tranc_details;
	Transaction_Tag_Loc Tag;
	TEACABugs_Loc TEACABugs_Loc;
	CategorizationRules_Loc categorization;
	Create_Budget_Loc createbudget;
	public AccountGroup_Settings_Loc groupSettingLoc;
	Series_Recurence_Transaction_Loc Series_Tranc_details;
	AccountAddition accAddition = new AccountAddition();

	// @BeforeClass()
	public void init() {
		manage_Categories_Loc = new Manage_Categories_Loc(d);
		MRBugs_Loc = new MRBugs_Loc(d);
		account_Settings_Loc = new AggregatedAccount_Settings_Loc(d);
		settingsLoc = new ManualAccount_Settings_Loc(d);
		manualAcnt = new Accounts_ManualAcnt_Loc(d);
		account_Settings_Loc = new AggregatedAccount_Settings_Loc(d);
		settingsLoc = new ManualAccount_Settings_Loc(d);
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		select_accounts = new Budget_Select_Accounts_Loc(d);
		Budget_Income_Bill = new Budget_Income_And_Bill_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		Budget_Income_Bill_Summery = new Budget_Income_And_Bill_Summery_Loc(d);
		no_accounts = new Budget_No_Account_Loc(d);
		LandingPage = new CashFlow_LandingPage_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		ExpLandingPage = new ExpLandingPage_Loc(d);
		rule1 = new Transaction_Categorization_Rules_Loc(d);
		netWorth = new NetWorth_Loc(d);
		Aggre_Tranc_details = new Aggregated_Transaction_Details_Loc(d);
		Tag = new Transaction_Tag_Loc(d);
		logger.info("Initializing");
		TEACABugs_Loc = new com.omni.pfm.TEACABugs.TEACABugs_Loc(d);
		categorization = new CategorizationRules_Loc(d);
		createbudget = new Create_Budget_Loc(d);
		groupSettingLoc = new AccountGroup_Settings_Loc(d);
		Series_Tranc_details = new Series_Recurence_Transaction_Loc(d);

	}

	public MRBugs_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement SubCategory_ErrorText() {
		return SeleniumUtil.getVisibileWebElement(d, "SubCategory_ErrorText", "Categories", null);
	}

	public WebElement Amount_ErrorText() {
		return SeleniumUtil.getVisibileWebElement(d, "Amount_ErrorText", "Categories", null);
	}

	public WebElement UncategorizeOption() {
		return SeleniumUtil.getVisibileWebElement(d, "UncategorizeOption", "Categories", null);
	}

	public WebElement GrpAccountsCheckbx() {
		return SeleniumUtil.getVisibileWebElement(d, "GrpAccountsCheckbx", "InvestmentHoldings", null);
	}

	public WebElement Closeicon() {
		return SeleniumUtil.getVisibileWebElement(d, "Closeicon", "InvestmentHoldings", null);
	}

	public WebElement DonutChart() {
		return SeleniumUtil.getVisibileWebElement(d, "DonutChart", "InvestmentHoldings", null);
	}

	public WebElement ViewAllAcc() {
		return SeleniumUtil.getVisibileWebElement(d, "ViewAllAcc", "DashboardPage", null);
	}

	public WebElement FirstRowContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "FirstRowContainer", "AccountsPage", null);
	}

	public WebElement CurrencyValues() {
		return SeleniumUtil.getVisibileWebElement(d, "CurrencyValues1", "AccountsPage", null);
	}

	public WebElement getCurrency_MAS() {
		return SeleniumUtil.getVisibileWebElement(d, "getCurrency_MAS", "AccountsPage", null);
	}

	public WebElement more() {
		return SeleniumUtil.getVisibileWebElement(d, "more", "Transaction", null);
	}

	public WebElement manage() {
		return SeleniumUtil.getVisibileWebElement(d, "manage", "Transaction", null);
	}

	public WebElement getHeaderText() {
		return SeleniumUtil.getVisibileWebElement(d, "getHeaderText", "Categories", null);
	}

	public WebElement expandCollapseButton() {
		return SeleniumUtil.getVisibileWebElement(d, "expandCollapseButton", "InvestmentHoldings", null);
	}

	public WebElement FirstRowHolding() {
		return SeleniumUtil.getVisibileWebElement(d, "FirstRowHolding", "InvestmentHoldings", null);
	}

	public WebElement PerfChartTimeDisplay() {
		return SeleniumUtil.getVisibileWebElement(d, "PerfChartTimeDisplay", "InvestmentHoldings", null);
	}

	public WebElement AssetsCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "AssetsCheckbox", "NetWorth", null);
	}

	public WebElement LiabilitiesCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "LiabilitiesCheckbox", "NetWorth", null);
	}

	public WebElement AssetsChart() {
		return SeleniumUtil.getVisibileWebElement(d, "AssetsChart", "NetWorth", null);
	}

	public WebElement NWValueStartingMonth() {
		return SeleniumUtil.getVisibileWebElement(d, "NWValueStartingMonth", "NetWorth", null);
	}

	public WebElement ChangeAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "ChangeAmount", "NetWorth", null);
	}

	public WebElement LiabilitiesChart() {
		return SeleniumUtil.getVisibileWebElement(d, "LiabilitiesChart", "NetWorth", null);
	}

	public WebElement TableLink() {
		return SeleniumUtil.getVisibileWebElement(d, "TableLink", "NetWorth", null);
	}

	public WebElement ChartLInk() {
		return SeleniumUtil.getVisibileWebElement(d, "ChartLInk", "NetWorth", null);
	}

	public WebElement ManualSettingLink() {
		return SeleniumUtil.getVisibileWebElement(d, "ManualSettingLink", "AccountSettings", null);
	}

	public WebElement SettingsIconinAccSetOpt() {
		return SeleniumUtil.getVisibileWebElement(d, "SettingsIconinAccSetOpt", "AccountsPage", null);
	}

	public WebElement AlertSettingLinkAcc() {
		return SeleniumUtil.getVisibileWebElement(d, "AlertSettingLinkAcc", "AccountSettings", null);
	}

	public WebElement Dropdownvalue() {
		return SeleniumUtil.getVisibileWebElement(d, "dropdownvalue_CLP", "CashFlow", null);
	}

	public WebElement AccountType() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountType", "AccountSettings", null);
	}

	public WebElement Clickscheduledropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "Clickscheduledropdown", "AlertSetting", null);
	}

	public WebElement expsummarylabel() {
		return SeleniumUtil.getVisibileWebElement(d, "expsummarylabel", "AlertSetting", null);
	}

	public WebElement amtlimitbudget() {
		return SeleniumUtil.getVisibileWebElement(d, "amtlimitbudget", "Budget", null);
	}

	public WebElement amtlimitincomebudget() {
		return SeleniumUtil.getVisibileWebElement(d, "amtlimitincomebudget", "Budget", null);
	}

	public WebElement CancelButton_CB() {
		return SeleniumUtil.getVisibileWebElement(d, "CancelButton_CB", "Budget", null);
	}

	public List<WebElement> scheduletypeDropDown() {

		return SeleniumUtil.getWebElements(d, "scheduletypeDropDown", "AlertSetting", null);
	}

	public WebElement subCat() {
		return SeleniumUtil.getWebElement(d, "subcat", "Transaction", null);
	}

	public WebElement autoHLC() {
		return SeleniumUtil.getVisibileWebElement(d, "Auto_hlc", "Expense", null);
	}

	public WebElement autoMLC() {
		return SeleniumUtil.getVisibileWebElement(d, "Auto_mlc", "Expense", null);
	}

	public WebElement skipSplash() {
		return SeleniumUtil.getWebElement(d, "splash", "DashboardPage", null);
	}

	public WebElement addAccountonSplash() {
		return SeleniumUtil.getWebElement(d, "linkAccount_splash", "DashboardPage", null);
	}

	public WebElement AlertExpCont() {
		return SeleniumUtil.getVisibileWebElement(d, "AlertExpCont", "AlertSetting", null);
	}

	public WebElement MultilevelAcc() {
		return SeleniumUtil.getVisibileWebElement(d, "multilevlacc", "AccountSettings", null);
	}

	public WebElement Quetion1() {
		return SeleniumUtil.getVisibileWebElement(d, "Quetion1", "AccountSettings", null);
	}

	public WebElement Quetion2() {
		return SeleniumUtil.getVisibileWebElement(d, "Quetion2", "AccountSettings", null);
	}

	public WebElement Quetion3() {
		return SeleniumUtil.getVisibileWebElement(d, "Quetion3", "AccountSettings", null);
	}

	public WebElement Seckey() {
		return SeleniumUtil.getVisibileWebElement(d, "Seckey", "AccountSettings", null);
	}

	public WebElement EditText() {
		return SeleniumUtil.getVisibileWebElement(d, "editText", "AccountSettings", null);
	}

	public WebElement EditPopUpTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "editPopUpTitle", "AccountSettings", null);
	}

	public WebElement PasswordText() {
		return SeleniumUtil.getVisibileWebElement(d, "passwordText", "AccountSettings", null);
	}

	public WebElement ReEnterPasswordText() {
		return SeleniumUtil.getVisibileWebElement(d, "reEnterPasswordText", "AccountSettings", null);
	}

	public WebElement UpdatedButton() {
		return SeleniumUtil.getVisibileWebElement(d, "Update", "AccountSettings", null);
	}

	public List<WebElement> getCurrentBalance() {
		return SeleniumUtil.getWebElements(d, "getCurrentBalance", "AccountsPage", null);
	}

	public WebElement MultipleAccFirstCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "MultipleAccFirstCheckbox", "NetWorth", null);
	}

	public WebElement AssetDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "AssetDropdown", "InvestmentHoldings", null);
	}

	public WebElement RegionSelect() {
		return SeleniumUtil.getVisibileWebElement(d, "RegionSelect", "InvestmentHoldings", null);
	}

	public WebElement FirstRegion() {
		return SeleniumUtil.getVisibileWebElement(d, "FirstRegion", "InvestmentHoldings", null);
	}

	public WebElement GrandTotalField() {
		return SeleniumUtil.getVisibileWebElement(d, "GrandTotalField", "InvestmentHoldings", null);
	}

	public WebElement TotalMarketValueField() {
		return SeleniumUtil.getVisibileWebElement(d, "TotalMarketValueField", "InvestmentHoldings", null);
	}

	public WebElement GrandTotalDisclaimerMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "GrandTotalDisclaimerMsg", "InvestmentHoldings", null);
	}

	public WebElement update_ATD() {
		return SeleniumUtil.getWebElement(d, "update_ATD", "Transaction", null);
	}

	public WebElement DepositSelect() {
		return SeleniumUtil.getWebElement(d, "DepositSelect", "Transaction", null);
	}

	public WebElement Addbtn() {
		return SeleniumUtil.getVisibileWebElement(d, "Addbtn1", "AccountsPage", frameName);
	}

	public WebElement cancelbtn() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelbtn", "AccountsPage", frameName);
	}

	public WebElement LiabilitiesBalance() {
		return SeleniumUtil.getVisibileWebElement(d, "LiabilitiesBalance", "NetWorth", frameName);
	}

	public WebElement AccountSelectDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "clickAccountSelectDropDown", "NetWorth", frameName);
	}

	public WebElement unseclectAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "clickunseclectAccount", "NetWorth", frameName);
	}

	public WebElement CreateBudgetHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateBudgetHeader_CB", "Budget", frameName);

	}

	public WebElement SelectGroup() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectGroup", "Budget", frameName);

	}

	public WebElement DeleteAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccount", "AccountSettings", frameName);

	}

	public WebElement Checkbox() {

		return SeleniumUtil.getVisibileWebElement(d, "Checkbox", "AccountSettings", frameName);

	}

	public WebElement Deleteconfirmbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "Deleteconfirmbtn", "AccountSettings", frameName);

	}

	public WebElement continuebtn1() {

		return SeleniumUtil.getVisibileWebElement(d, "continuebtn1", "NetWorth", frameName);

	}

	public WebElement Gotonetworth() {

		return SeleniumUtil.getVisibileWebElement(d, "Gotonetworth", "NetWorth", frameName);

	}

	public WebElement calenderpopup() {
		// TODO Auto-generated method stub
		return SeleniumUtil.getVisibileWebElement(d, "calendericon", "NetWorth", frameName);
	}

	public WebElement CalenderTitle() {
		// TODO Auto-generated method stub
		return SeleniumUtil.getVisibileWebElement(d, "calendertitle", "NetWorth", frameName);
	}

	public WebElement getErrorText() {
		return SeleniumUtil.getVisibileWebElement(d, "AmountError", "AccountsPage", null);
	}

	public WebElement getDescText() {
		return SeleniumUtil.getVisibileWebElement(d, "DescText", "Expense", null);
	}

	public WebElement getCatText() {
		return SeleniumUtil.getVisibileWebElement(d, "catText", "Expense", null);
	}

	public WebElement getNewCat() {
		return SeleniumUtil.getVisibileWebElement(d, "newcatText", "Expense", null);
	}

	public WebElement creategroupbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "creategroupbtn", "Budget", frameName);

	}

	public WebElement nextbtnBudget() {

		return SeleniumUtil.getVisibileWebElement(d, "nextbtnBudget", "Budget", frameName);

	}

	public WebElement fastLinkCloseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "fastLinkCloseIcon", "Budget", null);
	}

	public WebElement Budgetactuals() {
		return SeleniumUtil.getVisibileWebElement(d, "Budgetactuals", "Budget", null);
	}

	public WebElement Group() {
		return SeleniumUtil.getVisibileWebElement(d, "Group", "AccountGroups", null);
	}

	public WebElement editIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "editIcon", "AccountGroups", null);
	}

	public WebElement SelectPaymentServices() {
		return SeleniumUtil.getVisibileWebElement(d, "SelectPaymentServices", "AccountsPage", null);
	}

	public WebElement AccName() {
		return SeleniumUtil.getVisibileWebElement(d, "AccName", "AccountsPage", null);
	}

	public WebElement ManualAcc() {
		return SeleniumUtil.getVisibileWebElement(d, "ManualAcc", "AccountSettings", null);
	}

	public WebElement PaymentServicesSettings() {
		return SeleniumUtil.getVisibileWebElement(d, "PaymentServicesSettings", "AccountSettings", null);
	}

	public WebElement SelectBills() {
		return SeleniumUtil.getVisibileWebElement(d, "SelectBills", "AccountsPage", null);
	}

	public WebElement PaymentServices() {
		return SeleniumUtil.getVisibileWebElement(d, "PaymentServices", "AccountSettings", null);
	}

	public WebElement Addbtn1() {
		return SeleniumUtil.getVisibileWebElement(d, "Addbtn1", "AccountsPage", null);
	}

	public WebElement TrendsClose() {
		return SeleniumUtil.getVisibileWebElement(d, "TrendsClose", "Expense", null);
	}

	public WebElement getCategory1(int n) {

		return d.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[1]/td[" + n + "]/a"));
	}

	public WebElement cashflow_DropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "cashflow_DropDown", "CashFlow", null);
	}

	public WebElement cashflow_DropDownText() {

		return SeleniumUtil.getVisibileWebElement(d, "cashflow_DropDownText", "CashFlow", null);
	}

	public WebElement FirstTransaction() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstTransaction", "Transaction", null);
	}

	public WebElement TransactionCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "TransactionCategory", "Transaction", null);
	}

	public WebElement CategoryInputSearch() {

		return SeleniumUtil.getVisibileWebElement(d, "CategoryInputSearch", "Transaction", null);
	}

	public WebElement HomeHLC() {

		return SeleniumUtil.getVisibileWebElement(d, "HomeHLC", "Expense", null);
	}

	///////

	public WebElement FirstTransactions() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstTransactions", "Transaction", null);
	}

	public WebElement ViaSatTrans() {

		return SeleniumUtil.getVisibileWebElement(d, "ViaSatTrans", "Transaction", null);
	}

	public WebElement AccInfo() {

		return SeleniumUtil.getVisibileWebElement(d, "AccInfo", "Transaction", null);
	}

	public WebElement TransCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "TransCategory", "Transaction", null);
	}

	public WebElement CatsDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "CatDropdown", "Transaction", null);
	}

	public WebElement ChangedCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangedCategory", "Transaction", null);
	}

	public WebElement TransCreateRule() {

		return SeleniumUtil.getVisibileWebElement(d, "TransCreateRule", "Transaction", null);
	}

	public WebElement AutoDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "AutoDropdown", "Transaction", null);
	}

	public WebElement UpdateTransButton() {

		return SeleniumUtil.getVisibileWebElement(d, "UpdateTransButton", "Transaction", null);
	}

	public List<WebElement> AccDetails() {

		return SeleniumUtil.getWebElements(d, "AccDetails", "Transaction", null);
	}

	public List<WebElement> TransactionListAmount() {

		return SeleniumUtil.getWebElements(d, "TransactionListAmount", "Transaction", null);
	}

	public WebElement AddManualTransBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "AddManualTransBtn", "Transaction", null);
	}

	public WebElement ShowMoreOptions() {

		return SeleniumUtil.getVisibileWebElement(d, "ShowMoreOptions", "Transaction", null);
	}

	public WebElement SplitTransactions() {

		return SeleniumUtil.getVisibileWebElement(d, "SplitTransactions", "Transaction", null);
	}

	public WebElement SplitTransBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "SplitTransBtn", "Transaction", null);
	}

	public WebElement CalendarFieldIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "CalendarFieldIcon", "Transaction", null);
	}

	public WebElement LastCalendarRow() {

		return SeleniumUtil.getVisibileWebElement(d, "LastCalendarRow", "Transaction", null);
	}

	public WebElement FirstTransactionWrap() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstTransactionWrap", "Transaction", null);
	}

	public WebElement FirstAutomotiveFuel() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstAutomotiveFuel", "Transaction", null);
	}

	public WebElement SecandAutomotiveFuel() {

		return SeleniumUtil.getVisibileWebElement(d, "SecandAutomotiveFuel", "Transaction", null);
	}

	public WebElement TransAmtValue() {

		return SeleniumUtil.getVisibileWebElement(d, "TransAmtValue", "Transaction", null);
	}

	public WebElement RulesDisplayed() {

		return SeleniumUtil.getVisibileWebElement(d, "RulesDisplayed", "Transaction", null);
	}

	/////

	public WebElement LiabilitiesButton() {

		return SeleniumUtil.getVisibileWebElement(d, "LiabilitiesButton", "NetWorth", null);
	}

	public WebElement LiabilitiesValue() {

		return SeleniumUtil.getVisibileWebElement(d, "LiabilitiesValue", "NetWorth", null);
	}

	public WebElement LiabilitiesBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "LiabilitiesBtn", "NetWorth", null);
	}

	/////
	public WebElement BudgetSettings() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetSettings", "Budget", null);
	}

	public WebElement AddBills() {

		return SeleniumUtil.getVisibileWebElement(d, "AddBills", "Budget", null);
	}

	public WebElement SelectCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectCategory", "Budget", null);
	}

	public WebElement SelectCategorys() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectCategory", "CategorzationRules", null);
	}

	public WebElement CheckPayment() {

		return SeleniumUtil.getVisibileWebElement(d, "CheckPayment", "Budget", null);
	}

	public WebElement BudgetAmount() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetAmount", "Budget", null);
	}

	public WebElement SaveBtnDisabled() {

		return SeleniumUtil.getVisibileWebElement(d, "SaveBtnDisabled", "Budget", null);
	}

	public WebElement EditBills() {

		return SeleniumUtil.getVisibileWebElement(d, "EditBills", "Budget", null);
	}

	public List<WebElement> CategoryAmountEdit() {

		return SeleniumUtil.getWebElements(d, "CategoryAmountEdit", "Budget", null);
	}

	public List<WebElement> RecurringBills() {

		return SeleniumUtil.getWebElements(d, "RecurringBills", "Budget", null);
	}

	public WebElement SelectAllCategories() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectAllCategories", "Budget", null);
	}

	public WebElement SaveDisabled() {

		return SeleniumUtil.getVisibileWebElement(d, "SaveDisabled", "Budget", null);
	}

	public WebElement InsuranceCat() {

		return SeleniumUtil.getVisibileWebElement(d, "InsuranceCat", "Budget", null);
	}

	public WebElement CatAmtEdit() {

		return SeleniumUtil.getVisibileWebElement(d, "CatAmtEdit", "Budget", null);
	}

	public WebElement AddBudgetAmt() {

		return SeleniumUtil.getVisibileWebElement(d, "AddBudgetAmt", "Budget", null);
	}

	public WebElement InsuranceLabel() {

		return SeleniumUtil.getVisibileWebElement(d, "InsuranceLabel", "Budget", null);
	}

	public WebElement CancelBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "CancelBtn", "Budget", null);
	}

	public WebElement RecurringBillss() {

		return SeleniumUtil.getVisibileWebElement(d, "RecurringBillss", "Budget", null);
	}

	public List<WebElement> AccNames() {

		return SeleniumUtil.getWebElements(d, "AccNames", "Budget", null);
	}

	public WebElement EditIncome() {

		return SeleniumUtil.getVisibileWebElement(d, "EditIncome", "Budget", null);
	}

	public WebElement EditFlexibleExpense() {

		return SeleniumUtil.getVisibileWebElement(d, "EditFlexibleExpense", "Budget", null);
	}

	public List<WebElement> DisplayedIncomeText() {

		return SeleniumUtil.getWebElements(d, "DisplayedIncomeText", "Budget", null);
	}

	public WebElement CancelBtnEnabled() {

		return SeleniumUtil.getVisibileWebElement(d, "CancelBtnEnabled", "Budget", null);
	}

	public WebElement DropdownArrow() {

		return SeleniumUtil.getVisibileWebElement(d, "DropdownArrow", "Budget", null);
	}

	public WebElement DropDownData() {

		return SeleniumUtil.getVisibileWebElement(d, "DropDownData", "Budget", null);
	}

	/////

	public WebElement MoreOptions() {

		return SeleniumUtil.getVisibileWebElement(d, "MoreOptions", "Categories", null);
	}

	public WebElement MoreOptionss() {

		return SeleniumUtil.getVisibileWebElement(d, "MoreOption", "Categories", null);
	}

	public WebElement EditOption() {

		return SeleniumUtil.getVisibileWebElement(d, "EditOption", "Categories", null);
	}

	public WebElement SaveOption() {

		return SeleniumUtil.getVisibileWebElement(d, "SaveOption", "Categories", null);
	}

	public List<WebElement> RuleUpdatedToastMsg() {

		return SeleniumUtil.getWebElements(d, "RuleUpdatedToastMsg", "Categories", null);
	}

	///////

	public WebElement StartReview() {

		return SeleniumUtil.getVisibileWebElement(d, "StartReview", "FinancialForecast", null);
	}

	public WebElement AccType1() {

		return SeleniumUtil.getVisibileWebElement(d, "AccType1", "FinancialForecast", null);
	}

	public WebElement AccType2() {

		return SeleniumUtil.getVisibileWebElement(d, "AccType2", "FinancialForecast", null);
	}

	public WebElement ForeCastCheckBox() {

		return SeleniumUtil.getVisibileWebElement(d, "ForeCastCheckBox", "FinancialForecast", null);
	}

	public List<WebElement> AccNamess() {

		return SeleniumUtil.getWebElements(d, "AccNamess", "FinancialForecast", null);
	}

	////

	public WebElement AccType() {

		return SeleniumUtil.getVisibileWebElement(d, "AccType", "DashboardPage", null);
	}

	public WebElement SubmitAndNext() {

		return SeleniumUtil.getVisibileWebElement(d, "SubmitAndNext", "DashboardPage", null);
	}

	public WebElement CurrentBalance() {

		return SeleniumUtil.getVisibileWebElement(d, "CurrentBalance", "DashboardPage", null);
	}

	public WebElement WidgetContainerCard() {

		return SeleniumUtil.getVisibileWebElement(d, "WidgetContainerCard", "DashboardPage", null);
	}

	public WebElement BackToDashboardText() {

		return SeleniumUtil.getVisibileWebElement(d, "BackToDashboardText", "DashboardPage", null);
	}

	public WebElement AccTypeBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "AccTypeBtn", "DashboardPage", null);
	}

	public WebElement HomeEquityLineOfCredit() {

		return SeleniumUtil.getVisibileWebElement(d, "HomeEquityLineOfCredit", "DashboardPage", null);
	}

	public WebElement LineOfCredit() {

		return SeleniumUtil.getVisibileWebElement(d, "LineOfCredit", "DashboardPage", null);
	}

	public WebElement MoreBtnDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "MoreBtnDropdown", "DashboardPage", null);
	}

	public WebElement HoldingsBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "HoldingsBtn", "DashboardPage", null);
	}

	public WebElement HoldingHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "HoldingHeader", "DashboardPage", null);
	}

	public WebElement ExpandAll() {

		return SeleniumUtil.getVisibileWebElement(d, "ExpandAll", "DashboardPage", null);
	}

	/////

	public WebElement SmartZipTab() {

		return SeleniumUtil.getVisibileWebElement(d, "SmartZipTab", "AccountSettings", null);
	}

	public WebElement SettingsSamartZip() {

		return SeleniumUtil.getVisibileWebElement(d, "SettingsSamartZip", "AccountSettings", null);
	}

	public WebElement EditAddButton() {

		return SeleniumUtil.getVisibileWebElement(d, "EditAddButton", "AccountSettings", null);
	}

	public WebElement StreetAddress() {

		return SeleniumUtil.getVisibileWebElement(d, "StreetAddress", "AccountSettings", null);
	}

	public WebElement VerifyAddress() {

		return SeleniumUtil.getVisibileWebElement(d, "VerifyAddress", "AccountSettings", null);
	}

	public WebElement BackToAccountSettings() {

		return SeleniumUtil.getVisibileWebElement(d, "BackToAccountSettings", "AccountSettings", null);
	}

	public List<WebElement> UpperCaseAccType() {

		return SeleniumUtil.getWebElements(d, "UpperCaseAccType", "AccountSettings", null);
	}

	public WebElement OpenCalendar() {

		return SeleniumUtil.getVisibileWebElement(d, "OpenCalendar", "AccountSettings", null);
	}

	public WebElement MonthNextArrow() {

		return SeleniumUtil.getVisibileWebElement(d, "MonthNextArrow", "AccountSettings", null);
	}

	public WebElement YearNextArrow() {

		return SeleniumUtil.getVisibileWebElement(d, "YearNextArrow", "AccountSettings", null);
	}

	public WebElement PreviousMonthArrow() {

		return SeleniumUtil.getVisibileWebElement(d, "PreviousMonthArrow", "AccountSettings", null);
	}

	public WebElement PreviousYearArrow() {

		return SeleniumUtil.getVisibileWebElement(d, "PreviousYearArrow", "AccountSettings", null);
	}

	public WebElement InputAddress() {

		return SeleniumUtil.getVisibileWebElement(d, "InputAddress", "AccountSettings", null);
	}

	public WebElement InputCity() {

		return SeleniumUtil.getVisibileWebElement(d, "InputCity", "AccountSettings", null);
	}

	public WebElement SelectAccountsText() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectAccountsText", "AccountSettings", null);
	}

	public WebElement DeleteAcc() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAcc", "AccountSettings", null);
	}

	public WebElement DeleteCheckBox() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteCheckBox", "AccountSettings", null);
	}

	public WebElement ConfirmDelete() {

		return SeleniumUtil.getVisibileWebElement(d, "ConfirmDelete", "AccountSettings", null);
	}
	/////

	public WebElement MoreOtions() {

		return SeleniumUtil.getVisibileWebElement(d, "MoreOtions", "CategorzationRules", null);
	}

	public WebElement EditOptions() {

		return SeleniumUtil.getVisibileWebElement(d, "EditOptions", "CategorzationRules", null);
	}

	public WebElement CreateRule() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateRule", "CategorzationRules", null);
	}

	public WebElement MoreOpt() {

		return SeleniumUtil.getVisibileWebElement(d, "MoreOpt", "CategorzationRules", null);
	}

	public WebElement EditOpt() {

		return SeleniumUtil.getVisibileWebElement(d, "EditOpt", "CategorzationRules", null);
	}

	public WebElement SaveCat() {

		return SeleniumUtil.getVisibileWebElement(d, "SaveCat", "CategorzationRules", null);
	}

	public WebElement DescAmt() {

		return SeleniumUtil.getVisibileWebElement(d, "DescAmt", "CategorzationRules", null);
	}

	public WebElement InvalidAmtText() {

		return SeleniumUtil.getVisibileWebElement(d, "InvalidAmtText", "CategorzationRules", null);
	}

	public WebElement BetweenBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "BetweenBtn", "CategorzationRules", null);
	}

	public WebElement BetweenDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "BetweenDesc", "CategorzationRules", null);
	}

	public WebElement InvalidAmountRangeText() {

		return SeleniumUtil.getVisibileWebElement(d, "InvalidAmountRangeText", "CategorzationRules", null);
	}

	///////

	public WebElement DropDownTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "DropDownTitle", "CashFlow", null);
	}

	public WebElement CashFlowInput() {

		return SeleniumUtil.getVisibileWebElement(d, "CashFlowInput", "CashFlow", null);
	}

	public WebElement FirstTransactionsss() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstTransaction", "CashFlow", null);
	}

	public WebElement FromDate() {

		return SeleniumUtil.getVisibileWebElement(d, "FromDate", "CashFlow", null);
	}

	public WebElement FirstIncome() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstIncome", "CashFlow", null);
	}

	public WebElement CashFlowBar() {

		return SeleniumUtil.getVisibileWebElement(d, "CashFlowBar", "CashFlow", null);
	}

	public WebElement CashFlowIncome() {

		return SeleniumUtil.getVisibileWebElement(d, "CashFlowIncome", "CashFlow", null);
	}

	public WebElement ViewIncomeAnalysisBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ViewIncomeAnalysisBtn", "CashFlow", null);
	}

	public WebElement EAIAAmt() {

		return SeleniumUtil.getVisibileWebElement(d, "EAIAAmt", "CashFlow", null);
	}

	public WebElement CashInflowTable() {

		return SeleniumUtil.getVisibileWebElement(d, "CashInflowTable", "CashFlow", null);
	}

	public WebElement FirstTransRow() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstTransRow", "CashFlow", null);
	}

	public WebElement BackToCashFlow() {

		return SeleniumUtil.getVisibileWebElement(d, "BackToCashFlow", "CashFlow", null);
	}

	///////

	public WebElement CashManagementAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "CashManagementAccount", "AccountGroups", null);
	}

	public List<WebElement> NetWorthDataPointXaxis() {

		return SeleniumUtil.getWebElements(d, "NetWorthDataPointXaxis", "AccountGroups", null);
	}

	public WebElement GroupNames() {

		return SeleniumUtil.getVisibileWebElement(d, "GroupNames", "AccountGroups", null);
	}

	public List<WebElement> NWDataPointsT() {

		return SeleniumUtil.getWebElements(d, "NWDataPointsT", "AccountGroups", null);
	}

	public WebElement AccountCheckbox() {

		return SeleniumUtil.getVisibileWebElement(d, "AccountCheckbox", "AccountGroups", null);
	}

	public WebElement CreatedGroup() {

		return SeleniumUtil.getVisibileWebElement(d, "CreatedGroup", "AccountGroups", null);
	}

	public WebElement GroupDta() {

		return SeleniumUtil.getVisibileWebElement(d, "GroupDta", "AccountGroups", null);
	}

	public WebElement GroupDataAfter() {

		return SeleniumUtil.getVisibileWebElement(d, "GroupDataAfter", "AccountGroups", null);
	}

	///////

	public List<WebElement> AccountName() {

		return SeleniumUtil.getWebElements(d, "AccountName", "AccountsPage", null);
	}

	public List<WebElement> AccountsPage() {

		return SeleniumUtil.getWebElements(d, "AccountsPage", "AccountsPage", null);
	}

	public List<WebElement> AccountTypess() {

		return SeleniumUtil.getWebElements(d, "AccountTypess", "AccountsPage", null);
	}

	public WebElement AccountGroupBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "AccountGroupBtn", "AccountsPage", null);
	}

	public WebElement CreateGroupBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateGroupBtn", "AccountsPage", null);
	}

	public List<WebElement> AccountContainerLabel() {

		return SeleniumUtil.getWebElements(d, "AccountContainerLabel", "AccountsPage", null);
	}
	///////////

	public WebElement NetWorthTab() {

		return SeleniumUtil.getVisibileWebElement(d, "NetWorthTab", "AlertSetting", null);
	}

	public WebElement FirstToggle() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstToggle", "AlertSetting", null);
	}

	public WebElement SecondToggle() {

		return SeleniumUtil.getVisibileWebElement(d, "SecondToggle", "AlertSetting", null);
	}

	public WebElement SaveToggles() {

		return SeleniumUtil.getVisibileWebElement(d, "SaveToggles", "AlertSetting", null);
	}

	public WebElement VerifyToggleEnabled() {

		return SeleniumUtil.getVisibileWebElement(d, "VerifyToggleEnabled", "AlertSetting", null);
	}

	public WebElement BudgetTab() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetTab", "AlertSetting", null);
	}

	public WebElement BudgetToggle() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetToggle", "AlertSetting", null);
	}

	public WebElement BudgetToggleEnabled() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetToggleEnabled", "AlertSetting", null);
	}

	///////
	public WebElement HomeMLC() {

		return SeleniumUtil.getVisibileWebElement(d, "HomeMLC", "Expense", null);
	}

	public WebElement HomeMLCTransactionDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "HomeMLCTransactionDropdown", "Expense", null);
	}

	public WebElement CatDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "CatDropdown", "Expense", null);
	}

	public WebElement CatInput() {

		return SeleniumUtil.getVisibileWebElement(d, "CatInput", "Expense", null);
	}

	public WebElement ChangedCat() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangedCat", "Expense", null);
	}///

	public WebElement NewCategoryName() {

		return SeleniumUtil.getVisibileWebElement(d, "NewCategoryName", "Expense", null);
	}

	public WebElement FirstMLCCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstMLCCategory", "Expense", null);
	}

	public List<WebElement> SecondMLCCategory() {

		return SeleniumUtil.getWebElements(d, "SecondMLCCategory", "Expense", null);
	}

	public List<WebElement> ThirdMLCCategory() {

		return SeleniumUtil.getWebElements(d, "ThirdMLCCategory", "Expense", null);
	}

	public List<WebElement> FourthMLCCategory() {

		return SeleniumUtil.getWebElements(d, "FourthMLCCategory", "Expense", null);
	}

	public WebElement AmountInPieChart() {

		return SeleniumUtil.getVisibileWebElement(d, "AmountInPieChart", "Expense", null);
	}

	public WebElement TitleButton() {

		return SeleniumUtil.getVisibileWebElement(d, "TitleButton", "Expense", null);
	}

	public WebElement IncomeAnalysis() {

		return SeleniumUtil.getVisibileWebElement(d, "IncomeAnalysis", "Expense", null);
	}
	public WebElement ExpenseAnalyseAnalysisDd() {

		return SeleniumUtil.getVisibileWebElement(d, "ExpenseAnalyseAnalysisDd", "Expense", null);
	}
	public WebElement ExpenseAnalyseAnalysisChartNote() {

		return SeleniumUtil.getVisibileWebElement(d, "ExpenseAnalyseAnalysisChartNote", "Expense", null);
	}
	
	

	public WebElement Hlcbutton() {

		return SeleniumUtil.getVisibileWebElement(d, "Hlcbutton", "Expense", null);
	}

	public WebElement TransactionRow() {

		return SeleniumUtil.getVisibileWebElement(d, "TransactionRow", "Expense", null);
	}

	public WebElement ShowMoreOptionss() {

		return SeleniumUtil.getVisibileWebElement(d, "ShowMoreOptions", "Expense", null);
	}

	public WebElement DeleteTransactions() {

		return SeleniumUtil.getWebElement(d, "DeleteTransactions", "Expense", null);
	}

	public WebElement DeleteConfirm() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteConfirm", "Expense", null);
	}

	public WebElement CreateRuleLink() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateRuleLink", "Expense", null);
	}

	public WebElement CreateRuleButton() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateRuleButton", "Expense", null);
	}

	public WebElement CreateRuleSaveButton() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateRuleSaveButton", "Expense", null);
	}

	public WebElement DeleteSite() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteSite", "AccountSettings", null);
	}

	public WebElement DeleteSConfirm() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteSConfirm", "AccountSettings", null);
	}

	public WebElement DeleteSButton() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteSButton", "AccountSettings", null);
	}

	public WebElement FirstAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstAccount", "AccountSettings", null);
	}

	public WebElement AutomotiveExpenseCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "AutomotiveExpenseCategory", "Categories", null);
	}

	public WebElement InputHLC() {

		return SeleniumUtil.getVisibileWebElement(d, "InputHLC", "Categories", null);
	}

	public WebElement SaveCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "SaveCategory", "Categories", null);
	}

	public WebElement SettingsDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "Settings", "DashboardPage", null);
	}

	public List<WebElement> MoreOption() {

		return SeleniumUtil.getWebElements(d, "moreopt", "AccountsPage", null);
	}

	public WebElement AccSettingsDropdown() {

		return SeleniumUtil.getVisibileWebElement(d, "AccountSettings", "DashboardPage", null);
	}

	public WebElement AccSettings() {

		return SeleniumUtil.getVisibileWebElement(d, "AccsetIcon", "AccountSettings", null);
	}

	public WebElement DirectCheckingView() {

		return SeleniumUtil.getVisibileWebElement(d, "DirectCheckingView", "AccountsPage", null);
	}

	public WebElement ViewTrend() {

		return SeleniumUtil.getVisibileWebElement(d, "ViewTrend", "AccountsPage", null);
	}

	public WebElement MonthWithYear() {

		return SeleniumUtil.getVisibileWebElement(d, "MonthWithYear", "AccountsPage", null);
	}

	public WebElement MonthWithoutYear() {

		return SeleniumUtil.getVisibileWebElement(d, "MonthWithoutYear", "AccountsPage", null);
	}

	public WebElement MoreForInvestment() {

		return SeleniumUtil.getVisibileWebElement(d, "MoreForInvestment", "AccountsPage", null);
	}

	public WebElement SelectHoldings() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectHoldings", "AccountsPage", null);
	}

	public WebElement TodaysChangeTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "TodaysChangeTitle", "AccountsPage", null);
	}

	public WebElement CreditWidget() {

		return SeleniumUtil.getVisibileWebElement(d, "CreditWidget", "DashboardPage", null);
	}

	public WebElement CreatedGroupDialog() {

		return SeleniumUtil.getVisibileWebElement(d, "CreatedGroupDialog", "AccountGroups", null);
	}

	public WebElement CreateGroupBtns() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateGroupBtns", "AccountGroups", null);
	}

	public WebElement GroupPopUpContent() {

		return SeleniumUtil.getVisibileWebElement(d, "GroupPopUpContent", "AccountGroups", null);
	}

	public WebElement NetWorthAmt() {

		return SeleniumUtil.getVisibileWebElement(d, "NetWorthAmt", "AlertSetting", null);
	}

	public WebElement EditNetWorthAmt() {

		return SeleniumUtil.getVisibileWebElement(d, "EditNetWorthAmt", "AlertSetting", null);
	}

	public WebElement FirstSetting() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstSetting", "AccountSettings", null);
	}

	public WebElement TagName() {

		return SeleniumUtil.getVisibileWebElement(d, "TagName", "AccountSettings", null);
	}

	public WebElement AddButton() {

		return SeleniumUtil.getVisibileWebElement(d, "AddButton", "AccountSettings", null);
	}

	public WebElement ErrorsText() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrorsText", "AccountSettings", null);
	}

	public WebElement ProfileBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileBtn", "DashboardPage", null);
	}

	public WebElement PreferencesTab() {

		return SeleniumUtil.getVisibileWebElement(d, "PreferencesTab", "DashboardPage", null);
	}

	public WebElement PreferencesLanguage() {

		return SeleniumUtil.getVisibileWebElement(d, "PreferencesLanguage", "DashboardPage", null);
	}

	public WebElement BudgetFinappHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetFinappHeader", "Budget", null);
	}

	public WebElement FirstBudgetTransactionCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstBudgetTransactionCategory", "Budget", null);
	}

	public WebElement TransactionExtraPad() {

		return SeleniumUtil.getVisibileWebElement(d, "TransactionExtraPad", "Budget", null);
	}

	public WebElement SecondBudgetTransactionCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "SecondBudgetTransactionCategory", "Budget", null);
	}

	public WebElement BudgetFlexibleSpending() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetFlexibleSpending", "Budget", null);
	}

	public WebElement SelectsCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectCategory", "Budget", null);
	}

	public List<WebElement> BudgetItemsList() {

		return SeleniumUtil.getWebElements(d, "BudgetItemsList", "Budget", null);
	}

	public WebElement TotalIncome() {

		return SeleniumUtil.getVisibileWebElement(d, "TotalIncome", "CashFlow", null);
	}

	public WebElement TotalExpenses() {

		return SeleniumUtil.getVisibileWebElement(d, "TotalExpenses", "CashFlow", null);
	}

	public WebElement NetTransfers() {

		return SeleniumUtil.getVisibileWebElement(d, "NetTransfers", "CashFlow", null);
	}

	public WebElement NetCashFlows() {

		return SeleniumUtil.getVisibileWebElement(d, "NetCashFlows", "CashFlow", null);
	}

	public WebElement TooltipIncome() {

		return SeleniumUtil.getVisibileWebElement(d, "TooltipIncome", "CashFlow", null);
	}

	public WebElement TooltipExpenses() {

		return SeleniumUtil.getVisibileWebElement(d, "TooltipExpenses", "CashFlow", null);
	}

	public WebElement TooltipNetTransfers() {

		return SeleniumUtil.getVisibileWebElement(d, "TooltipNetTransfers", "CashFlow", null);
	}

	public WebElement TooltipNetCashFlow() {

		return SeleniumUtil.getVisibileWebElement(d, "TooltipNetCashFlow", "CashFlow", null);
	}

	public WebElement NegativeChart() {

		return SeleniumUtil.getVisibileWebElement(d, "NegativeChartCashFlow", "CashFlow", null);
	}

	public WebElement IncomeChart() {

		return SeleniumUtil.getVisibileWebElement(d, "IncomeChart", "CashFlow", null);
	}

	public WebElement FormulaBar() {

		return SeleniumUtil.getVisibileWebElement(d, "FormulaBar", "CashFlow", null);
	}

	public WebElement ExpenseToolTip() {

		return SeleniumUtil.getVisibileWebElement(d, "ExpenseToolTip", "CashFlow", null);
	}

	public WebElement ExpenseChart() {

		return SeleniumUtil.getVisibileWebElement(d, "ExpenseChart", "CashFlow", null);
	}

	public WebElement PositiveNetCahFlow() {

		return SeleniumUtil.getVisibileWebElement(d, "PositiveNetCahFlow", "CashFlow", null);
	}

	public WebElement IncomeToolTip() {

		return SeleniumUtil.getVisibileWebElement(d, "IncomeToolTip", "CashFlow", null);
	}

	public WebElement TransferInTooltip() {

		return SeleniumUtil.getVisibileWebElement(d, "TransferInTooltip", "CashFlow", null);
	}

	public WebElement TransferOutTooltip() {

		return SeleniumUtil.getVisibileWebElement(d, "TransferOutTooltip", "CashFlow", null);
	}

	public WebElement NetTransferTooltip() {

		return SeleniumUtil.getVisibileWebElement(d, "NetTransferTooltip", "CashFlow", null);
	}

	public WebElement NetTransferTableAmount() {

		return SeleniumUtil.getVisibileWebElement(d, "NetTransferTableAmount", "CashFlow", null);
	}

	public WebElement TransactionsPage() {

		return SeleniumUtil.getVisibileWebElement(d, "TransactionsPage", "CashFlow", null);
	}

	public WebElement TotalInvestmentText() {

		return SeleniumUtil.getVisibileWebElement(d, "TotalInvestmentText", "InvestmentHoldings", null);
	}

	public WebElement TotalInvestmentValue() {

		return SeleniumUtil.getVisibileWebElement(d, "TotalInvestmentValue", "InvestmentHoldings", null);
	}

	public WebElement TotalHoldingText() {

		return SeleniumUtil.getVisibileWebElement(d, "TotalHoldingText", "InvestmentHoldings", null);
	}

	public WebElement TotalHoldingValue() {

		return SeleniumUtil.getVisibileWebElement(d, "TotalHoldingValue", "InvestmentHoldings", null);
	}

	public WebElement CashMiscText() {

		return SeleniumUtil.getVisibileWebElement(d, "CashMiscText", "InvestmentHoldings", null);
	}

	public WebElement CashMiscValue() {

		return SeleniumUtil.getVisibileWebElement(d, "CashMiscValue", "InvestmentHoldings", null);
	}

	public WebElement HoldingBalanceIH() {

		return SeleniumUtil.getVisibileWebElement(d, "HoldingBalanceIH", "InvestmentHoldings", null);
	}

	public WebElement HoldingBalanceValueIH() {

		return SeleniumUtil.getVisibileWebElement(d, "HoldingBalanceValueIH", "InvestmentHoldings", null);
	}

	public WebElement ChangePerc() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangePerc", "InvestmentHoldings", null);
	}

	public WebElement ChangePercValue() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangePercValue", "InvestmentHoldings", null);
	}

	public WebElement ChangeText() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangeText", "InvestmentHoldings", null);
	}

	public WebElement ChangeValue() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangeValue", "InvestmentHoldings", null);
	}

	public WebElement PlusIcon_CreateBudget() {

		return SeleniumUtil.getVisibileWebElement(d, "PlusIcon_CreateBudget", "Budget", null);
	}

	public WebElement MyAccountBankMoreSettings() {

		return SeleniumUtil.getVisibileWebElement(d, "MyAccountBankMoreSettings", "AccountsPage", null);
	}

	public WebElement MyAccountBankAccountSettings() {

		return SeleniumUtil.getVisibileWebElement(d, "MyAccountBankAccountSettings", "AccountsPage", null);
	}

	public WebElement TagSearch() {

		return SeleniumUtil.getVisibileWebElement(d, "TagSearch", "AccountsPage", null);
	}

	public WebElement BusinessTag() {

		return SeleniumUtil.getVisibileWebElement(d, "BusinessTag", "AccountsPage", null);
	}

	public WebElement VacationTag() {

		return SeleniumUtil.getVisibileWebElement(d, "VacationTag", "AccountsPage", null);
	}

	public WebElement TravelTag() {

		return SeleniumUtil.getVisibileWebElement(d, "TravelTag", "AccountsPage", null);
	}

	public WebElement ApplyTagToPastTxn() {

		return SeleniumUtil.getVisibileWebElement(d, "ApplyTagToPastTxn", "AccountsPage", null);
	}

	public WebElement TagSaveChanges() {

		return SeleniumUtil.getVisibileWebElement(d, "TagSaveChanges", "AccountsPage", null);
	}

	public WebElement MyAccountBankTxn() {

		return SeleniumUtil.getVisibileWebElement(d, "MyAccountBankTxn", "AccountsPage", null);
	}

	public WebElement FirstTxnTag() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstTxnTag", "AccountsPage", null);
	}

	public WebElement SecondTxnTag() {

		return SeleniumUtil.getVisibileWebElement(d, "SecondTxnTag", "AccountsPage", null);
	}

	public WebElement DeleteTag() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteTag", "AccountsPage", null);
	}

	public WebElement TxnTag() {

		return SeleniumUtil.getVisibileWebElement(d, "TxnTag", "AccountsPage", null);
	}

	public WebElement createGroup() {

		return SeleniumUtil.getVisibileWebElement(d, "createGp", "AccountGroups", null);
	}

	public WebElement selectGroup() {

		return SeleniumUtil.getVisibileWebElement(d, "selectGrp", "CashFlow", null);
	}

	public WebElement categoryDrpdown() {

		return SeleniumUtil.getVisibileWebElement(d, "categoryDrpdown", "Transaction", null);
	}

	public WebElement createRulee() {

		return SeleniumUtil.getVisibileWebElement(d, "createRulee", "Transaction", null);
	}

	public WebElement closeRulePopUp() {

		return SeleniumUtil.getVisibileWebElement(d, "closeRulePopUp", "Transaction", null);
	}

	public WebElement morebutton() {

		return SeleniumUtil.getVisibileWebElement(d, "morebutton", "Transaction", null);
	}

	public WebElement printbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "printbtn", "Transaction", null);
	}

	public WebElement txnCategory() {

		return SeleniumUtil.getVisibileWebElement(d, "txnCategory", "Transaction", null);
	}

	public WebElement getStarted() {

		return SeleniumUtil.getVisibileWebElement(d, "getStarted", "Budget", null);
	}

	public WebElement dagSavingCheckBox() {

		return SeleniumUtil.getVisibileWebElement(d, "dagSavingCheckBox", "Budget", null);
	}

	public WebElement budgetnxtbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetnxtbtn", "Budget", null);
	}

	public WebElement nxtBtnIncome() {

		return SeleniumUtil.getVisibileWebElement(d, "nxtBtnIncome", "Budget", null);
	}

	public WebElement budgetAccGrp() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetAccGrp", "Budget", null);
	}

	public WebElement budgetEditGrpLnk() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetEditGrpLnk", "Budget", null);
	}

	public List<WebElement> budgetCheckedBoxes() {

		return SeleniumUtil.getWebElements(d, "budgetCheckedBoxes", "Budget", null);
	}

	public List<WebElement> budgetMortgageChkbxes() {

		return SeleniumUtil.getWebElements(d, "budgetMortgageChkbxes", "Budget", null);
	}

	public WebElement budgetDagInsurance() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetDagInsurance", "Budget", null);
	}

	public WebElement budgetDagBilling() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetDagBilling", "Budget", null);
	}

	public WebElement budgetSaveChanges() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetSaveChanges", "Budget", null);
	}

	public WebElement budgetRelevantTxt() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetRelevantTxt", "Budget", null);
	}

	public WebElement createbudgetbtnn() {

		return SeleniumUtil.getVisibileWebElement(d, "createbudgetbtnn", "Budget", null);
	}

	public WebElement createAccGrp() {

		return SeleniumUtil.getVisibileWebElement(d, "createAccGrp", "Budget", null);
	}

	public WebElement budgetCreditCard() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetCreditCard", "Budget", null);
	}

	public WebElement budgetDirectChecking() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetDirectChecking", "Budget", null);
	}

	public WebElement budgetBrokerage() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetBrokerage", "Budget", null);
	}

	public WebElement ffNextBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ffNextBtn", "FinancialForecast", null);
	}

	public WebElement ffcurrentBal() {

		return SeleniumUtil.getVisibileWebElement(d, "ffcurrentBal", "FinancialForecast", null);
	}

	public WebElement ffMinimumDue() {

		return SeleniumUtil.getVisibileWebElement(d, "ffMinimumDue", "FinancialForecast", null);
	}

	public WebElement netWorthContinue() {

		return SeleniumUtil.getVisibileWebElement(d, "netWorthContinue", "NetWorth", null);
	}

	public WebElement goToNetWorthLink() {

		return SeleniumUtil.getVisibileWebElement(d, "goToNetWorthLink", "NetWorth", null);
	}

	public WebElement netWorthLiabilities() {

		return SeleniumUtil.getVisibileWebElement(d, "netWorthLiabilities", "NetWorth", null);
	}

	public WebElement netWorthInsurance() {

		return SeleniumUtil.getVisibileWebElement(d, "netWorthInsurance", "NetWorth", null);
	}

	public List<WebElement> netWorthDagInsurance() {

		return SeleniumUtil.getWebElements(d, "netWorthDagInsurance", "NetWorth", null);
	}

	public WebElement creditCardwidLinkacc() {
		return SeleniumUtil.getVisibileWebElement(d, "creditCardwidLinkacc", "DashboardPage", null);
	}

	public WebElement goToCashFlow() {

		return SeleniumUtil.getVisibileWebElement(d, "goToCashFlow", "Transaction", null);
	}

	public WebElement investnetWidgetlink() {

		return SeleniumUtil.getVisibileWebElement(d, "investnetWidgetlink", "DashboardPage", null);
	}

	public WebElement investnetSectorSelect() {

		return SeleniumUtil.getVisibileWebElement(d, "investnetSectorSelect", "DashboardPage", null);
	}

	public WebElement investnetwidgetNavigate() {

		return SeleniumUtil.getVisibileWebElement(d, "investnetwidgetNavigate", "DashboardPage", null);
	}

	public WebElement goToMyInvestmentsTxt() {

		return SeleniumUtil.getVisibileWebElement(d, "goToMyInvestmentsTxt", "InvestmentHoldings", null);
	}

	public WebElement selectAssetClassdrpdown() {

		return SeleniumUtil.getVisibileWebElement(d, "selectAssetClassdrpdown", "InvestmentHoldings", null);
	}

	public WebElement assetClassTxt() {

		return SeleniumUtil.getVisibileWebElement(d, "assetClassTxt", "InvestmentHoldings", null);
	}

	public WebElement startReviewD() {

		return SeleniumUtil.getVisibileWebElement(d, "startReviewD", "DashboardPage", null);
	}

	public WebElement ffstartReview() {

		return SeleniumUtil.getVisibileWebElement(d, "ffstartReview", "FinancialForecast", null);
	}

	public WebElement moreSettingsRoger() {

		return SeleniumUtil.getVisibileWebElement(d, "moreSettingsRoger", "AccountsPage", null);
	}

	public WebElement accSettingsRoger() {

		return SeleniumUtil.getVisibileWebElement(d, "accSettingsRoger", "AccountsPage", null);
	}

	public WebElement rogerNickNameTxtBox() {

		return SeleniumUtil.getVisibileWebElement(d, "rogerNickNameTxtBox", "AccountsPage", null);
	}

	public WebElement rogerSaveChanges() {

		return SeleniumUtil.getVisibileWebElement(d, "rogerSaveChanges", "AccountsPage", null);
	}

	public WebElement rogerHeaderName() {

		return SeleniumUtil.getVisibileWebElement(d, "rogerHeaderName", "AccountsPage", null);
	}

	public WebElement rogerNickName() {

		return SeleniumUtil.getVisibileWebElement(d, "rogerNickName", "AccountsPage", null);
	}

	public WebElement ffpendingTxn() {

		return SeleniumUtil.getVisibileWebElement(d, "ffpendingTxn", "FinancialForecast", null);
	}

	public WebElement ffAddToCalendar() {

		return SeleniumUtil.getVisibileWebElement(d, "ffAddToCalendar", "FinancialForecast", null);
	}

	public WebElement ffAddTxnBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ffAddTxnBtn", "FinancialForecast", null);
	}

	public WebElement ffToastMsg() {

		return SeleniumUtil.getVisibileWebElement(d, "ffToastMsg", "FinancialForecast", null);
	}

	public WebElement securityToken() {

		return SeleniumUtil.getVisibileWebElement(d, "securityToken", "AccountSettings", null);
	}

	public WebElement securityTokenNxtBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "securityTokenNxtBtn", "AccountSettings", null);
	}

	public WebElement securityQuesState() {

		return SeleniumUtil.getVisibileWebElement(d, "securityQuesState", "AccountSettings", null);
	}

	public WebElement securityQuesSchool() {

		return SeleniumUtil.getVisibileWebElement(d, "securityQuesSchool", "AccountSettings", null);
	}

	public WebElement editCreds() {

		return SeleniumUtil.getVisibileWebElement(d, "editCreds", "AccountSettings", null);
	}

	public WebElement dagPwd() {

		return SeleniumUtil.getVisibileWebElement(d, "dagPwd", "AccountSettings", null);
	}

	public WebElement dagrePwd() {

		return SeleniumUtil.getVisibileWebElement(d, "dagrePwd", "AccountSettings", null);
	}

	public WebElement dagSchool() {

		return SeleniumUtil.getVisibileWebElement(d, "dagSchool", "AccountSettings", null);
	}

	public WebElement dagFrndName() {

		return SeleniumUtil.getVisibileWebElement(d, "dagFrndName", "AccountSettings", null);
	}

	public WebElement dagState() {

		return SeleniumUtil.getVisibileWebElement(d, "dagState", "AccountSettings", null);
	}

	public WebElement dagCloseEditCreds() {

		return SeleniumUtil.getVisibileWebElement(d, "dagCloseEditCreds", "AccountSettings", null);
	}

	public WebElement dagErrorBtnIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "dagErrorBtnIcon", "AccountSettings", null);
	}

	public WebElement dagErrorEditCreds() {

		return SeleniumUtil.getVisibileWebElement(d, "dagErrorEditCreds", "AccountSettings", null);
	}

	public WebElement dagCloseCreds() {

		return SeleniumUtil.getVisibileWebElement(d, "dagCloseCreds", "AccountSettings", null);
	}

	public WebElement dagErrorTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "dagErrorTitle", "AccountSettings", null);
	}

	public WebElement cashFlowdrpdwn() {

		return SeleniumUtil.getVisibileWebElement(d, "cashFlowdrpdwn", "CashFlow", null);
	}

	public WebElement cashFlowdrpdwnOption() {

		return SeleniumUtil.getVisibileWebElement(d, "cashFlowdrpdwnOption", "CashFlow", null);
	}

	public WebElement cashFlowdrpdwnTxt() {

		return SeleniumUtil.getVisibileWebElement(d, "cashFlowdrpdwnTxt", "CashFlow", null);
	}

	public WebElement continuebtoon() {

		return SeleniumUtil.getVisibileWebElement(d, "continuebtoon", "Expense", null);
	}

	public List<WebElement> cbtn() {
		return SeleniumUtil.getWebElements(d, "continuebtoon", "Expense", null);
	}

	public WebElement GoToMyCashFlowBtn_CLP() {

		return SeleniumUtil.getVisibileWebElement(d, "GoToMyCashFlowBtn_CLP", "CashFlow", null);
	}

	public WebElement DagCreditcardPasswordOne() {

		return SeleniumUtil.getVisibileWebElement(d, "DagCreditcardPasswordOne", "DashboardPage", null);
	}

	public WebElement DagCreditcardPasswordTwo() {

		return SeleniumUtil.getVisibileWebElement(d, "DagCreditcardPasswordTwo", "DashboardPage", null);
	}

	public WebElement errorIcnDashBoard() {

		return SeleniumUtil.getVisibileWebElement(d, "errorIcnDashBoard", "DashboardPage", null);
	}

	public WebElement addBill() {

		return SeleniumUtil.getVisibileWebElement(d, "addBill", "FinancialForecast", null);
	}

	public WebElement addamount() {

		return SeleniumUtil.getVisibileWebElement(d, "addamount", "FinancialForecast", null);
	}

	public WebElement addDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "addDesc", "FinancialForecast", null);
	}

	public WebElement debitedFrm() {

		return SeleniumUtil.getVisibileWebElement(d, "debitedFrm", "FinancialForecast", null);
	}

	public WebElement debitCateg() {

		return SeleniumUtil.getVisibileWebElement(d, "debitCateg", "FinancialForecast", null);
	}

	public WebElement recFreq() {

		return SeleniumUtil.getVisibileWebElement(d, "recFreq", "FinancialForecast", null);
	}

	public WebElement recWeekly() {

		return SeleniumUtil.getVisibileWebElement(d, "recWeekly", "FinancialForecast", null);
	}

	public WebElement schedDate() {

		return SeleniumUtil.getVisibileWebElement(d, "schedDate", "FinancialForecast", null);
	}

	public WebElement endDate() {

		return SeleniumUtil.getVisibileWebElement(d, "endDate", "FinancialForecast", null);
	}

	public WebElement addBillCateg() {

		return SeleniumUtil.getVisibileWebElement(d, "addBillCateg", "FinancialForecast", null);
	}

	public WebElement selectCategaddBill() {

		return SeleniumUtil.getVisibileWebElement(d, "selectCategaddBill", "FinancialForecast", null);
	}

	public WebElement addBillTxn() {

		return SeleniumUtil.getVisibileWebElement(d, "addBillTxn", "FinancialForecast", null);
	}

	public WebElement addBillSelectCredit() {

		return SeleniumUtil.getVisibileWebElement(d, "addBillSelectCredit", "FinancialForecast", null);
	}

	public List<WebElement> ffstartRevieww() {
		return SeleniumUtil.getWebElements(d, "ffstartRevieww", "FinancialForecast", null);
	}

	public boolean addAggregatedAccount(String dagUserNaem, String dagPassword)
			throws AWTException, InterruptedException {
		return addAccount(null, dagUserNaem, dagPassword, false);
	}

	public boolean addAccount(String containerName, String dagUserNaem, String dagPassword, boolean isContainer)
			throws AWTException {
		// Boolean isSDGon =
		// PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON") ?
		// true : false;

		// String fiName = containerName == null ?
		// PropsUtil.getDataPropertyValue("DAGSite") : containerName;
		String fiName = containerName == null ? PropsUtil.getDataPropertyValue("dagSiteMultilevel") : containerName;
		String unifiedFlow = PropsUtil.getEnvPropertyValue("UnifiedFlow");

		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).clear();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(fiName);

		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {
			WebElement siteSearchButton = SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null);
			if (siteSearchButton != null)
				SeleniumUtil.click(siteSearchButton);
		}

		/**
		 * Getting the list of all displayed sites & clicking if it
		 * matches @fiName, streaming requires Java 1.8 as minimum
		 */

		SeleniumUtil.waitForPageToLoad();
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		sitename.parallelStream().filter(t -> t.getText().equals(fiName)).findFirst().get().click();

		if (isContainer) {
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).sendKeys(dagUserNaem);
		} else {
			SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		}

		WebElement pwd = null; // Below checking if DagBank, if so, go for
		// password1 else password
		pwd = fiName.equals("DagBank") ? SeleniumUtil.getVisibileWebElement(d, "password1", "LinkAnAccount", null)
				: SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null);

		pwd.sendKeys(dagPassword);

		List<WebElement> reEnterPwd = null;
		reEnterPwd = (fiName.equals("DagBank")
				? SeleniumUtil.getWebElements(d, "reEnterPassword1", "LinkAnAccount", null)
				: SeleniumUtil.getWebElements(d, "reEnterPassword", "LinkAnAccount", null));

		if (reEnterPwd != null && reEnterPwd.size() > 0 && reEnterPwd.get(0).isDisplayed()) {
			WebElement reEnterPwdDup = null; // Below checking if DagBank, if
			// so, go for reEnterPassword
			// else reEnterPassword1
			reEnterPwdDup = fiName.equals("DagBank")
					? SeleniumUtil.getVisibileWebElement(d, "reEnterPassword1", "LinkAnAccount", null)
					: SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null);

			reEnterPwdDup.sendKeys(dagPassword);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			SeleniumUtil.waitForPageToLoad(3000);
		} else {
			List<WebElement> nextBtn = SeleniumUtil.getWebElements(d, "nextBtn", "LinkAnAccount", null);
			if (nextBtn.size() > 0 && nextBtn.get(0).isDisplayed()) {
				SeleniumUtil.click(nextBtn.get(0));
			}
			System.out.println("Re enter Password field is not present.");
		}

		SeleniumUtil.waitForPageToLoad();

		List<WebElement> selectCheckBox = SeleniumUtil.getWebElements(d, "selectCheckBox", "LinkAnAccount", null);
		WebElement selectBox = null;
		if (selectCheckBox.size() > 0 && (selectBox = selectCheckBox.get(0)).isDisplayed()) {
			SeleniumUtil.click(selectBox);
			SeleniumUtil.waitForPageToLoad(2500);
		}

		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "submitBtn", "LinkAnAccount", null));
		SeleniumUtil.waitForPageToLoad();
		handleLogin(fiName);

		WebDriverWait wait = new WebDriverWait(d, 500);

		WebElement successMsg = null;

		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {

			By b = (appFlag != null && appFlag.equalsIgnoreCase("Web"))
					? By.xpath("//div[contains(text(),'Congratulations')]")
					: By.xpath("//div[@id='status']/following::i[contains(@class,'success')]");

			successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(b));
		} else {

			By b = (appFlag != null && appFlag.equalsIgnoreCase("Web"))
					? By.xpath("//div[contains(text(),'Congratulations')]")
					: By.xpath("//div[@id='status']/following::i[contains(@class,'success')]");

			successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(b));

		}

		boolean status = successMsg.isDisplayed();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		return status;
	}

	private void handleLogin(String name) {
		final String finame = name.toUpperCase();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		switch (finame) {
		case FINameConstants.DAGMULTILEVEL:
			WebDriverWait wait = new WebDriverWait(d, 500);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='token']")))
					.sendKeys(PropsUtil.getDataPropertyValue("securityToken"));
			securityTokenNxtBtn().click();
			SeleniumUtil.waitForPageToLoad();
			securityQuesState().sendKeys(PropsUtil.getDataPropertyValue("stateName"));
			securityQuesSchool().sendKeys(PropsUtil.getDataPropertyValue("schoolName"));
			securityTokenNxtBtn().click();
			break;
		default:
			break;

		}
	}

	/**
	 * Method to make Dag Site Multilevel account with error 402 Pre-requisite :
	 * User should be on Account Settings page with Dag Site Multilevel
	 * configured successfully.
	 */

	public void makeDagSiteMultilevelAcc402() {
		editCreds().click();
		SeleniumUtil.waitForPageToLoad();
		dagPwd().clear();
		dagPwd().sendKeys(PropsUtil.getDataPropertyValue("dagdummyData"));
		dagrePwd().clear();
		dagrePwd().sendKeys(PropsUtil.getDataPropertyValue("dagdummyData"));
		dagSchool().sendKeys(PropsUtil.getDataPropertyValue("dagdummyData"));
		dagFrndName().sendKeys(PropsUtil.getDataPropertyValue("dagdummyData"));
		dagState().sendKeys(PropsUtil.getDataPropertyValue("dagdummyData"));
		securityTokenNxtBtn().click();
		WebDriverWait wait = new WebDriverWait(d, 200);
		WebElement reverification = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[text()='Credential Re-Verification Required']")));
		Assert.assertTrue(reverification.isDisplayed(), "Dag Multilevel account is made with error 402");
		dagCloseEditCreds().click();
	}

	/**
	 * Method to reconfigure Dag Site Multilevel account successfully fixig
	 * error 402 Pre-requisite : User should be on Account Settings page with
	 * Dag Site Multilevel configured error 402.
	 */

	public void reverifyDagSiteMultilevelAcc() {
		dagErrorBtnIcon().click();
		dagErrorEditCreds().click();
		SeleniumUtil.waitForPageToLoad();
		dagPwd().clear();
		dagPwd().sendKeys(PropsUtil.getDataPropertyValue("dagSiteMultilevelPassword"));
		dagrePwd().clear();
		dagrePwd().sendKeys(PropsUtil.getDataPropertyValue("dagSiteMultilevelPassword"));
		dagSchool().sendKeys(PropsUtil.getDataPropertyValue("schoolName"));
		dagFrndName().sendKeys(PropsUtil.getDataPropertyValue("friendName"));
		dagState().sendKeys(PropsUtil.getDataPropertyValue("stateName"));
		securityTokenNxtBtn().click();
		WebDriverWait wait = new WebDriverWait(d, 200);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='token']")))
				.sendKeys(PropsUtil.getDataPropertyValue("securityToken"));
		securityTokenNxtBtn().click();
		WebDriverWait w = new WebDriverWait(d, 300);
		WebElement successMsg = w.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[contains(text(),'account was successfully')]")));
		Assert.assertTrue(successMsg.isDisplayed(), "Account re-verification is failed");
		logger.info("Account reverification has been done successfully");
		dagCloseCreds().click();
	}

	public void createBudget() {

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(getStarted());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(dagSavingCheckBox());
		SeleniumUtil.click(budgetnxtbtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(nxtBtnIncome());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(nxtBtnIncome());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(MRBugs_Loc.BudgetSettings().isDisplayed(), "Budget is not created");
		logger.info("Budget is created successfully");

	}

	/*
	 * PreCond : User should be on Budget finApp with one budget created
	 */

	public void verifyIrrelevantGrpTxtInBudget() {

		SeleniumUtil.click(MRBugs_Loc.BudgetSettings());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.budgetAccGrp());
		SeleniumUtil.click(MRBugs_Loc.budgetEditGrpLnk());
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> list = MRBugs_Loc.budgetCheckedBoxes();
		logger.info("Unchecking the checked checkboxes");
		for (int i = 0; i < list.size(); i++) {
			list.get(i).click();
		}
		logger.info("Checking the irrelevant group for the budget");
		List<WebElement> list1 = MRBugs_Loc.budgetMortgageChkbxes();
		for (int i = 0; i < list1.size(); i++) {
			list1.get(i).click();
		}
		SeleniumUtil.click(MRBugs_Loc.budgetDagInsurance());
		SeleniumUtil.click(MRBugs_Loc.budgetDagBilling());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.budgetSaveChanges());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(MRBugs_Loc.budgetRelevantTxt().isDisplayed(), "No Relevant account group text is not shown");
		logger.info("No Relevant account group text is shown ");

	}

	/*
	 * To have an explicit wait using loop before locating any WebElement
	 */

	public static WebElement isElementPresent(WebDriver driver, String xpath, int time) {
		WebElement ele = null;
		for (int i = 0; i < time; i++) {
			try {
				ele = driver.findElement(By.xpath("xpath"));
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					System.out.println("Waiting for the element to appear on DOM");
				}
			}

		}
		return ele;
	}

	public void ValidateNoDataInExpense() {
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(d.findElement(By.xpath("//*[@id='IAEAAddTrans']/span")));
		SeleniumUtil.waitForPageToLoad(10000);
		add_manual_transaction.amount().sendKeys("1000");
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description1"));
		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TransactionType());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(0));
		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgorie());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(add_manual_transaction.catgoryList(1, 1));
		SeleniumUtil.waitForPageToLoad(10000);
		add_manual_transaction.add().click();
		SeleniumUtil.waitForPageToLoad(10000);
		String ActualText1 = d.findElement(By.xpath("//h3[contains(@class,'title no-data-message-title-font')]"))
				.getText();
		String ExpectedText1 = "No Expenses Found";
		Assert.assertTrue(ActualText1.contains(ExpectedText1));
		String ActualText2 = d.findElement(By.xpath("//p[contains(@class,'content no-data-message-title-font')]"))
				.getText();
		String ExpectedText2 = "There are no expenses for the selected time period";
		Assert.assertTrue(ActualText2.contains(ExpectedText2));
	}

	public void ValidateNoDatainIncome() {
		SeleniumUtil.click(d.findElement(By.xpath("//*[@id='IAEAAddTrans']/span")));
		SeleniumUtil.waitForPageToLoad(10000);
		add_manual_transaction.amount().sendKeys("1000");
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description3"));
		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgorie());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgoryList(1, 1));
		SeleniumUtil.waitForPageToLoad(2000);
		add_manual_transaction.add().click();
		SeleniumUtil.waitForPageToLoad(8000);
		d.findElement(By.xpath("//span[@id='back-to-text']")).click();
		SeleniumUtil.waitForPageToLoad(10000);
		d.findElement(By.xpath("//*[@id='title-button']/i")).click();
		SeleniumUtil.waitForPageToLoad(2000);
		d.findElement(By.xpath("//a[@aria-label='Income Analysis']")).click();
		SeleniumUtil.waitForPageToLoad(4000);
		ExpLandingPage.clickChart(6);
		SeleniumUtil.waitForPageToLoad(10000);
		String ActualText3 = d.findElement(By.xpath("//h3[contains(@class,'title no-data-message-title-font')]"))
				.getText();
		String ExpectedText3 = "No Income Found";
		Assert.assertTrue(ActualText3.contains(ExpectedText3));
		String ActualText4 = d.findElement(By.xpath("//p[contains(@class,'content no-data-message-title-font')]"))
				.getText();
		String ExpectedText4 = "There is no income for the selected time period";
		Assert.assertTrue(ActualText4.contains(ExpectedText4));
		logger.info("*************************Automation raised bug************************");
	}

	public void AddRealEstateAccount() {
		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d,
				By.xpath("//a[@id='link-account-button-persist']"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.xpath("//a[@id='link-account-button-persist']")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);
			SeleniumUtil.waitForPageToLoad(10000);

		} else {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(10000);

			SeleniumUtil.waitForPageToLoad();
			WebElement linkAnother = SeleniumUtil.waitForElementVisible(d, By.xpath("//a[@id='linkAccountBtn']"), 4);
			if (linkAnother != null) {
				SeleniumUtil.click(d.findElement(By.xpath("//a[@id='linkAccountBtn']")));
				SeleniumUtil.waitForPageToLoad(30000);

				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			}
		}

		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAccountLink", "AccountsPage", null));
		// SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstatLink",
		// "AccountsPage", null));

		SeleniumUtil.waitForPageToLoad(2500);

		SeleniumUtil.getWebElement(d, "RealEstateManulAccountName", "AccountsPage", null)
				.sendKeys("TestRealEsteateAccount");

		SeleniumUtil.waitForPageToLoad(2500);

		d.findElement(By.xpath("//input[@name='street-address']")).sendKeys("1255 POTRERO AVE,SANFRANCISCO");
		d.findElement(By.xpath("//input[@name='city-state-zip']")).sendKeys("CA,94110");

		SeleniumUtil.waitForPageToLoad(2500);

		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountNextLink", "AccountsPage", null));

		SeleniumUtil.waitForPageToLoad(8000);

		d.findElement(By.xpath("(//li[@class='mortgage-account-item']//span[@role='checkbox'])[2]")).click();

		d.findElement(By.xpath("//input[@value='Associate']")).click();

		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.refresh(d);
	}

	public void addmanualAccount() {
		SeleniumUtil.waitForPageToLoad();
		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[@id='dashboardLinkAccountBtn']"),
				3);
		if (linkAccBtn != null) {
			if (d.findElement(By.xpath("//*[@id='dashboardLinkAccountBtn']")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);
			SeleniumUtil.waitForPageToLoad(10000);

			try {
				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} catch (Exception e) {

			}
			SeleniumUtil.waitForPageToLoad(15000);
			if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
				SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
			} else {
				SeleniumUtil.click(d.findElement(By.xpath("//*[@id='otherAccountsContainer']/div/a[1]")));
			}
			SeleniumUtil.waitForPageToLoad();

		} else {

			try {
				PageParser.forceNavigate("AccountsPage", d);
				SeleniumUtil.waitForPageToLoad();
				WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
						By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother != null) {
					SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
					SeleniumUtil.waitForPageToLoad(3000);
					try {
						WebElement successMsg = SeleniumUtil.waitForElementVisible(d,
								By.xpath("//*[text()='Get Started']"), 5);
						if (successMsg != null) {

							SeleniumUtil.click(successMsg);
						}
					} catch (Exception e) {

					}
					if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
						SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
					} else {
						SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
					}
					SeleniumUtil.waitForPageToLoad(3000);

				}
			} catch (WebDriverException e) {

				WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
						By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother != null) {
					SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
					if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
						SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
					} else {
						SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
					}
					SeleniumUtil.waitForPageToLoad(3000);
				}
			}
		}
	}

	// Adding a subcategory
	public void addSubCategory(String Subcategory) throws Exception {

		logger.info("***** Navigating to manage categories ******");
		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(manage_Categories_Loc.expenseCatLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		manage_Categories_Loc.typeSubCategoryName(Subcategory);
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButtonInMLC());
		SeleniumUtil.waitForPageToLoad();

	}

	// Add withdrawal transaction

	public void addWithdrawalTransaction(String WithdrawalAmount, int HLC, int MLC) {
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(d.findElement(By.xpath("//*[@id='IAEAAddTrans']/span")));
		SeleniumUtil.waitForPageToLoad(10000);
		add_manual_transaction.amount().sendKeys(WithdrawalAmount);
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description3"));
		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgorie());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(add_manual_transaction.catgoryList(HLC, MLC));
		add_manual_transaction.add().click();
	}

	// Method to add Manual Transaction
	public void AddManualAccount(String AccountType) {
		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d,
				By.xpath("//a[@id='link-account-button-persist']"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.xpath("//a[@id='link-account-button-persist']")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);
			SeleniumUtil.waitForPageToLoad(10000);

		} else {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(10000);

			WebElement linkAnother = SeleniumUtil.waitForElementVisible(d, By.xpath("//a[@id='linkAccountBtn']"), 4);
			if (linkAnother != null) {
				SeleniumUtil.click(d.findElement(By.xpath("//a[@id='linkAccountBtn']")));
				SeleniumUtil.waitForPageToLoad(30000);

				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} else {
				SeleniumUtil.click(d.findElement(By.id("suggestedAcc-linkOtherAccount-persist")));
				SeleniumUtil.waitForPageToLoad(30000);

				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}

			}
		}

		SeleniumUtil.waitForPageToLoad(10000);
		if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
			SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
		} else {
			SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
		}
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(d.findElement(By.xpath("//span[text()='Account Type']")));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(d.findElement(By.xpath("//li[contains(text(),'" + AccountType + "')]")));
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys("MyAccountbank");
		SeleniumUtil.waitForPageToLoad();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(Keys.TAB);
		if ("null".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'nickName')]")).sendKeys("null");
				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {
			System.out.println("Not visible");
		}
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")));

		if (!"1001".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys("0.01");
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(Keys.TAB);

				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {
			System.out.println("Not visible");
		}
		if (!"null".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).sendKeys("0.01");
			} catch (Exception e) {
				System.out.println("Not visble");
			}
		} else {
			System.out.println("Not visble");
		}
		try {
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).clear();
			// commented by priyanka
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			System.out.println(formatter.format(date));
			System.out.println("Date is" + "null");
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).sendKeys(formatter.format(date));
			SeleniumUtil.waitForPageToLoad(2500);
		} catch (Exception e) {
			System.out.println("Not visble");
		}
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(d.findElement(By.id("addBtn")));
		SeleniumUtil.waitForPageToLoad(10000);
	}

	public void CreateCategories(String CategoryDesc, int CategoryList) {

		d.findElement(By.id("createRule")).click();
		SeleniumUtil.waitForPageToLoad(6000);
		SeleniumUtil.click(categorization.descTextBox());
		SeleniumUtil.waitForPageToLoad(5500);
		categorization.descTextBox().sendKeys(CategoryDesc);
		SeleniumUtil.waitForPageToLoad(5500);
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.click(categorization.getCategoryLL().get(CategoryList));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(categorization.createRuleIdButtn());
		SeleniumUtil.waitForPageToLoad(20000);
		logger.info("*************************Category is created*************************");

	}

	// Add deposit transaction
	public void AddDepositTransaction(String amount, int HLC, int MLC) {
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(d.findElement(By.xpath("//*[@id='IAEAAddTrans']/span")));
		SeleniumUtil.waitForPageToLoad(10000);
		add_manual_transaction.amount().sendKeys(amount);
		add_manual_transaction.description().sendKeys(PropsUtil.getDataPropertyValue("description1"));
		SeleniumUtil.click(add_manual_transaction.accountdropDown());
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TransactionType());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.TtransactionList().get(0));
		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgorie());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.catgoryList(HLC, MLC));
		add_manual_transaction.add().click();
		SeleniumUtil.waitForPageToLoad(10000);

	}

	public String getActualText() {
		JavascriptExecutor javascript = (JavascriptExecutor) d;
		String ActualText = (String) javascript.executeScript("return document.getElementById('descText').value;");
		return ActualText;
	}

	public Boolean VertscrollStatus() {
		JavascriptExecutor javascript = (JavascriptExecutor) d;
		Boolean VertscrollStatus = (Boolean) javascript
				.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
		return VertscrollStatus;
	}

	public Boolean isClickable(WebElement el) {
		try {
			WebDriverWait wait = new WebDriverWait(d, 6);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Method to add Manual Transaction
	public void AddManualAccount(String AccountType, String Balance) {
		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d,
				By.xpath("//a[@id='link-account-button-persist']"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.xpath("//a[@id='link-account-button-persist']")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);
			SeleniumUtil.waitForPageToLoad(10000);

		} else {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(10000);

			WebElement linkAnother = SeleniumUtil.waitForElementVisible(d, By.xpath("//a[@id='linkAccountBtn']"), 4);
			if (linkAnother != null) {
				SeleniumUtil.click(d.findElement(By.xpath("//a[@id='linkAccountBtn']")));
				SeleniumUtil.waitForPageToLoad(30000);

				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} else {
				SeleniumUtil.click(d.findElement(By.id("suggestedAcc-linkOtherAccount-persist")));
				SeleniumUtil.waitForPageToLoad(30000);

				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}

			}
		}

		SeleniumUtil.waitForPageToLoad(10000);
		if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
			SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
		} else {
			SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
		}
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(d.findElement(By.xpath("//span[text()='Account Type']")));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(d.findElement(By.xpath("//li[contains(text(),'" + AccountType + "')]")));
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys("MyAccountbank");
		SeleniumUtil.waitForPageToLoad();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(Keys.TAB);
		if ("null".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'nickName')]")).sendKeys("null");
				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {
			System.out.println("Not visible");
		}
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")));

		if (!"1001".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(Balance);
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(Keys.TAB);

				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {
			System.out.println("Not visible");
		}
		if (!"null".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).sendKeys("0.01");
			} catch (Exception e) {
				System.out.println("Not visble");
			}
		} else {
			System.out.println("Not visble");
		}
		try {
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).clear();
			// commented by priyanka
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			System.out.println(formatter.format(date));
			System.out.println("Date is" + "null");
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).sendKeys(formatter.format(date));
			SeleniumUtil.waitForPageToLoad(2500);
		} catch (Exception e) {
			System.out.println("Not visble");
		}
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(d.findElement(By.id("addBtn")));
		SeleniumUtil.waitForPageToLoad(10000);
	}

	public WebElement continueBtnInWelcomeCM() {
		return SeleniumUtil.getVisibileWebElement(d, "continueBtnInWelcomeCM", "InvestmentHoldings", null);
	}

	public static WebElement linkAccountWelcomeCM() {
		return SeleniumUtil.getVisibileWebElement(d, "IH_linkAccountWelcomeCM", "InvestmentHoldings", null);
	}

	public WebElement goToMyInvestments() {
		return SeleniumUtil.getVisibileWebElement(d, "goToMyInvestments", "InvestmentHoldings", null);
	}

	public WebElement AccountsDropdownFilter() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsDropdownFilter", "Transaction", null);
	}

	public WebElement AllAccountsChkBox() {
		return SeleniumUtil.getVisibileWebElement(d, "AllAccountsChkBox", "Transaction", null);
	}

	public WebElement FirstAccountsCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "FirstAccountsCheckbox", "Transaction", null);
	}

	public WebElement SelectedAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "SelectedAccount", "Transaction", null);
	}

	public WebElement StartDateInput() {
		return SeleniumUtil.getVisibileWebElement(d, "StartDateInput", "CashFlow", null);
	}

	public WebElement EndDateInput() {
		return SeleniumUtil.getVisibileWebElement(d, "EndDateInput", "CashFlow", null);
	}

	public WebElement StartDateRange() {
		return SeleniumUtil.getVisibileWebElement(d, "StartDateInput", "CashFlow", null);
	}

	public WebElement EndDateRange() {
		return SeleniumUtil.getVisibileWebElement(d, "EndDateInput", "CashFlow", null);
	}

	public List<WebElement> CashFlowDataPointsXAxis() {
		return SeleniumUtil.getWebElements(d, "CashFlowDataPointsXAxis", "CashFlow", null);
	}

	public List<WebElement> CashFlowDetails() {
		return SeleniumUtil.getWebElements(d, "CashFlowDetails", "CashFlow", null);
	}

	public WebElement AscendDates() {
		return SeleniumUtil.getVisibileWebElement(d, "AscendDates", "CashFlow", null);
	}

	public WebElement allCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "AllCategories", "Transaction", null);
	}

	public WebElement searchCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "searchCategories", "Transaction", null);
	}

	public WebElement filterCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "filterCategories", "Transaction", null);
	}

	public WebElement moreIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "moreIcon", "NetWorth", null);
	}

	public WebElement alertSettings() {
		return SeleniumUtil.getWebElement(d, "alertSettings", "Budget", null);
	}

	public WebElement netCashFLow1() {
		return SeleniumUtil.getWebElement(d, "NetCashFlow1", "CashFlow", null);
	}

	public WebElement netCashFLow2() {
		return SeleniumUtil.getWebElement(d, "NetCashFlow2", "CashFlow", null);
	}

	public WebElement netCashFLow3() {
		return SeleniumUtil.getWebElement(d, "NetCashFlow3", "CashFlow", null);
	}

	public WebElement editShowAsCategoryField() {
		return SeleniumUtil.getVisibileWebElement(d, "editMLCatInputField_MC", "Categories", null);
	}

	public WebElement settings_Menu() {
		return SeleniumUtil.getVisibileWebElement(d, "settings_Menu", "Categories", null);
	}

	public WebElement subMenuCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "subMenuCategories", "Categories", null);
	}

	public WebElement toplevelCategoriesIncome() {
		return SeleniumUtil.getVisibileWebElement(d, "toplevelCategoriesIncome", "Categories", null);
	}

	public WebElement categoryConsulting() {
		return SeleniumUtil.getVisibileWebElement(d, "categoryConsulting", "Categories", null);
	}

	public WebElement showAscategoryName() {
		return SeleniumUtil.getVisibileWebElement(d, "showAscategoryName", "Categories", null);
	}

	public WebElement ShowAsErrorMessage() {
		return SeleniumUtil.getWebElement(d, "ShowAsErrorMessage", "Categories", null);
	}

	public WebElement CategoryNameUncategorized() {
		return SeleniumUtil.getWebElement(d, "CategoryNameUncategorized", "Expense", null);
	}

	public List<WebElement> ListofMonths() {
		return SeleniumUtil.getWebElements(d, "listmoth", "Transaction", null);
	}

	public WebElement listmothFirst() {
		return SeleniumUtil.getVisibileWebElement(d, "listmothFirst", "Transaction", null);
	}

	public WebElement IncomeDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "IncomeDropdown", "Expense", null);
	}

	public WebElement getStartedBudget() {

		return SeleniumUtil.getVisibileWebElement(d, "getStartedBudget", "Budget", null);
	}

	public WebElement ExpenseDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "ExpenseDropDown", "Expense", null);
	}

	public static WebElement my_Profile() {
		return SeleniumUtil.getWebElement(d, "my_Profile", "SAML_LOGIN", null);
	}

	public WebElement logout() {
		return SeleniumUtil.getWebElement(d, "logout", "SAML_LOGIN", null);
	}

	public static WebElement dashBoard() {
		return SeleniumUtil.getWebElement(d, "dashBoard", "DashboardPage", null);
	}

	public WebElement dashBoardPageSpinner() {

		return SeleniumUtil.getWebElement(d, "dashBoardPageSpinner", "SAML_LOGIN", null);

	}

	public List<WebElement> transactionPageSpinner() {

		return SeleniumUtil.getWebElements(d, "transactionPageSpinner", "SAML_LOGIN", null);

	}

	public WebElement budgetFtueNext2() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetFtueNext2", "Budget", null);
	}

	public WebElement budgetFtueNext3() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetFtueNext3", "Budget", null);
	}

	public WebElement transactionAdd() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionAdd", "Transaction", null);
	}

	public WebElement amount_AMT() {

		return SeleniumUtil.getVisibileWebElement(d, "amount_AMT", "Transaction", null);
	}

	public WebElement transactionTypeeDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionTypeeDropDown", "Transaction", null);
	}

	public WebElement transactionTypeValue() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionTypeValue", "Transaction", null);
	}

	public WebElement transactionDebitedFrom() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionDebitedFrom", "Transaction", null);
	}

	public WebElement transactionDebitedFromvalue() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionDebitedFromvalue", "Transaction", null);
	}

	public WebElement transactioncategoryDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "transactioncategoryDropDown", "Transaction", null);
	}

	public WebElement transactioncategoryText() {

		return SeleniumUtil.getVisibileWebElement(d, "transactioncategoryText", "Transaction", null);
	}

	public WebElement transactioncategoryDropdownValue() {

		return SeleniumUtil.getVisibileWebElement(d, "transactioncategoryDropdownValue", "Transaction", null);
	}
	public WebElement transactioncategoryDropdownValueSavings() {

		return SeleniumUtil.getVisibileWebElement(d, "transactioncategoryDropdownValueSavings", "Transaction", null);
	}
	

	public WebElement transactioncategoryDropdwnVal() {

		return SeleniumUtil.getVisibileWebElement(d, "transactioncategoryDropdwnVal", "Transaction", null);
	}

	public WebElement transactionWithDrawalDropdownValue() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionWithDrawalDropdownValue", "Transaction", null);
	}

	public WebElement transactionEventAdd() {

		return SeleniumUtil.getVisibileWebElement(d, "transactionEventAdd", "Transaction", null);
	}

	public WebElement description_AMT() {

		return SeleniumUtil.getVisibileWebElement(d, "description_AMT", "Transaction", null);
	}

	public WebElement continuebt() {

		return SeleniumUtil.getVisibileWebElement(d, "continuebt", "Transaction", null);
	}

	public WebElement goToAnalysis() {

		return SeleniumUtil.getVisibileWebElement(d, "goToAnalysis", "Transaction", null);
	}

	public WebElement projectedtransDate() {

		return SeleniumUtil.getVisibileWebElement(d, "projectedtransDate", "Transaction", null);
	}

	public WebElement endDate_ATC() {

		return SeleniumUtil.getVisibileWebElement(d, "endDate_ATC", "Transaction", null);
	}

	public WebElement durationMonth() {

		return SeleniumUtil.getVisibileWebElement(d, "durationMonth", "CashFlow", null);
	}

	public WebElement durationThisMonth() {

		return SeleniumUtil.getVisibileWebElement(d, "durationThisMonth", "CashFlow", null);

	}

	public WebElement acntsDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "acntsDropDown", "ManageSharing", null);

	}

	public List<WebElement> monthXaxis() {

		return SeleniumUtil.getWebElements(d, "monthXaxis", "CashFlow", null);
	}

	public WebElement firstAvailableAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "firstAvailableAccount", "CashFlow", null);

	}

	public WebElement networthFTUE() {

		return SeleniumUtil.getVisibileWebElement(d, "networthFTUE", "NetWorth", null);

	}

	public String[] getPageTitle() {

		return PageParser.getElementValues("Transaction", false, "", "getPageTitle");

	}

	public String[] dashBoardSpinner() {

		return PageParser.getElementValues("Transaction", false, "", "dashBoardSpinner");

	}

	public String[] transactionSpinner() {

		return PageParser.getElementValues("Transaction", false, "", "transactionSpinner");

	}

	public String[] networthSpinner() {

		return PageParser.getElementValues("Transaction", false, "", "networthSpinner");

	}

	public String[] holdingsSpinner() {

		return PageParser.getElementValues("Transaction", false, "", "holdingsSpinner");

	}

	public String[] otherSpinner() {

		return PageParser.getElementValues("Transaction", false, "", "otherSpinner");

	}

	public String[] popUpContinue() {

		return PageParser.getElementValues("FinCheck", false, "", "popUpContinue");

	}

	public WebElement viewCashAccounts() {

		return SeleniumUtil.getVisibileWebElement(d, "viewCashAccounts", "DashboardPage", null);

	}

	public WebElement ffBacktoDashBoard() {

		return SeleniumUtil.getVisibileWebElement(d, "ffBacktoDashBoard", "FinancialForecast", null);

	}

	public WebElement fFStartReview() {

		return SeleniumUtil.getVisibileWebElement(d, "fFStartReview", "FinancialForecast", null);

	}

	public WebElement seeAccountTrend() {

		return SeleniumUtil.getVisibileWebElement(d, "seeAccountTrend", "CashFlow", null);

	}

	public String[] popUpCashFlow() {

		return PageParser.getElementValues("FinCheck", false, "", "popUpCashFlow");

	}

	public String[] popUpAnalysis() {

		return PageParser.getElementValues("FinCheck", false, "", "popUpAnalysis");

	}

	// @uthor : Abhinandan
	// Method to handle pop-ups when user loginds for the first time

	public void continuePopup() throws InterruptedException {

		String[] popUpContinue = popUpContinue();
		String[] popUpCashFlow = popUpCashFlow();
		String[] popUpAnalysis = popUpAnalysis();
		String pageName = null;

		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		waitUntilSpinnerDisappears(d, 10);

		try {

			if (!d.findElements(By.xpath(popUpContinue[1])).isEmpty()) {

				d.findElement(By.xpath(popUpContinue[1])).click();

				// Indentifies the current page page
				pageName = d.getTitle();

				if (pageName.contains("Cash")) {

					d.findElement(By.xpath(popUpCashFlow[1])).click();

				} else if (pageName.contains("Expense")) {

					d.findElement(By.xpath(popUpAnalysis[1])).click();

				} else if (pageName.contains("Investment")) {

					goToMyInvestments().click();

					// Investment
				} else if (pageName.contains("Net Worth")) {

					networthFTUE().click();

				}
				SeleniumUtil.waitForPageToLoad(1000);

			}
		} catch (Exception e) {

			logger.info("Exception :" + e.getMessage());
		} finally {

			d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		}

	}

	// @uthor : Abhinandan
	// Method to handle page loading,waits untill spinner disappears.

	public boolean waitUntilSpinnerDisappears(WebDriver d, long timeout) throws InterruptedException {

		String[] dashBoardSpinner = dashBoardSpinner();
		String[] transactionSpinner = transactionSpinner();
		String[] networthSpinner = networthSpinner();
		String[] holdingsSpinner = holdingsSpinner();
		String[] otherSpinner = otherSpinner();

		String pageName = null;
		String spinner = null;
		boolean flag = false;

		try {

			// Get the current page title
			pageName = d.getTitle();

			if (pageName.contains("Transactions")) {

				spinner = transactionSpinner[1];

			} else if (pageName.contains("Net Worth")) {

				spinner = networthSpinner[1];

			} else if (pageName.contains("Dashboard")) {

				spinner = dashBoardSpinner[1];

			} else if (pageName.contains("Investment")) {

				spinner = holdingsSpinner[1];

			} else {

				spinner = otherSpinner[1];
			}
			// Override Implicit wait
			d.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			long startTime = System.currentTimeMillis();
			long elapsedTime = 0L;
			while (elapsedTime < timeout) {
				elapsedTime = TimeUnit.MILLISECONDS.toSeconds((new Date()).getTime()) - startTime;
				if (d.findElements(By.xpath(spinner)).isEmpty()) {
					SeleniumUtil.waitForPageToLoad(1000);
					break;
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			logger.info("Exception  :" + e.getMessage());
		} finally {
			// Reset to deault wait
			d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		}
		return flag;

	}

	public void sendText(String text, WebElement elem) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elem.sendKeys("");
		StringSelection s = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		elem.sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public WebElement AddbtnDisablechk() {
		return SeleniumUtil.getVisibileWebElement(d, "Addbtndsblchk", "AccountsPage", frameName);
	}

	public WebElement transactionsFirst() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionsFirst", "Transaction", null);
	}

	public WebElement transactionShowMoreOption() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionShowMoreOption", "Transaction", null);
	}

	public WebElement attachFile_TA() {
		return SeleniumUtil.getWebElement(d, "attachFile_TA", "Transaction", null);
	}

	public WebElement transactionSelectAttchment() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionSelectAttchment", "Transaction", null);
	}

	public WebElement transactionRemoveAttchment() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionRemoveAttchment", "Transaction", null);
	}

	public WebElement transactionAttchmentpopUp() {
		return SeleniumUtil.getWebElement(d, "transactionAttchmentpopUp", "Transaction", null);
	}

	public WebElement transactionManualAdd() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionManualAdd", "Transaction", null);
	}

	public WebElement flexibleSpendingsATM() {
		return SeleniumUtil.getVisibileWebElement(d, "flexibleSpendingsATM", "Budget", null);
	}

	public WebElement tranRowsinTran() {
		return SeleniumUtil.getVisibileWebElement(d, "tranRowsinTran", "Transaction", null);
	}

	public WebElement transactionManualTypeDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionManualTypeDropDown", "Transaction", null);
	}

	public String[] transactionManualTypeValue() {
		return PageParser.getElementValues("Transaction", false, "", "transactionManualTypeValue");
	}

	public WebElement transactionManualDebitedFrom() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionManualDebitedFrom", "Transaction", null);
	}

	public WebElement transactionManualDebitedFromValue() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionManualDebitedFromValue", "Transaction", null);
	}

	public WebElement transactionManualcategoryDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionManualcategoryDropDown", "Transaction", null);
	}

	public WebElement transactionManualcategoryText() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionManualcategoryText", "Transaction", null);
	}

	public List<WebElement> transactionEditSave() {
		return SeleniumUtil.getWebElements(d, "transactionEditSave", "Transaction", null);
	}

	public WebElement transactionPopUpMask() {

		return SeleniumUtil.getWebElement(d, "transactionPopUpMask", "Transaction", null);
	}

	public WebElement PasswordText1() {
		return SeleniumUtil.getVisibileWebElement(d, "passwordText1", "AccountSettings", null);
	}

	public WebElement ReEnterPasswordText1() {
		return SeleniumUtil.getWebElement(d, "reEnterPasswordText1", "AccountSettings", null);
	}

	public WebElement ReEnterPasswordText1fl3() {
		return SeleniumUtil.getWebElement(d, "reEnterPasswordText1fl3", "AccountSettings", null);
	}
	
	public List<WebElement> PasswordText1fl3() {
		return SeleniumUtil.getWebElements(d, "PasswordText1fl3", "AccountSettings", null);
	}

	public WebElement creditwidhoverTxt() {
		return SeleniumUtil.getWebElement(d, "creditwidhoverTxt", "DashboardPage", null);
	}

	public WebElement errorText() {
		return SeleniumUtil.getWebElement(d, "errorText", "AccountSettings", null);
	}

	public WebElement creditcardusuageFl() {
		return SeleniumUtil.getVisibileWebElement(d, "creditcardusuageFl", "DashboardPage", null);
	}

	public WebElement fastlinkFrame() {
		return SeleniumUtil.getVisibileWebElement(d, "fastlinkFrame", "DashboardPage", null);
	}

	public WebElement fastlinkSearch() {
		return SeleniumUtil.getVisibileWebElement(d, "fastlinkSearch", "DashboardPage", null);
	}

	public WebElement cashForecastFl() {
		return SeleniumUtil.getVisibileWebElement(d, "cashForecastFl", "DashboardPage", null);
	}

	public WebElement flBackgrndcolor() {
		return SeleniumUtil.getWebElement(d, "flBackgrndcolor", "DashboardPage", null);
	}

	public WebElement noDataScreen_Button() {
		return SeleniumUtil.getVisibileWebElement(d, "noDataScreen_Button", "AccountsPage", null);
	}

	public WebElement groupNameTxtBoxLoc() {
		return SeleniumUtil.getVisibileWebElement(d, "groupNameTxtBoxLoc", "AccountsPage", null);
	}

	public WebElement addGroupFirstCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "addGroupFirstCheckBox", "AccountsPage", null);
	}

	public WebElement createOrUpdateGroupLoc() {
		return SeleniumUtil.getVisibileWebElement(d, "createOrUpdateGroupLoc", "AccountsPage", null);
	}

	public WebElement editExistingGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "editExistingGroup", "AccountsPage", null);
	}

	public WebElement createModalWindow() {
		return SeleniumUtil.getVisibileWebElement(d, "createModalWindow", "AccountsPage", null);
	}

	public WebElement editModalWindow() {
		return SeleniumUtil.getVisibileWebElement(d, "editModalWindow", "AccountsPage", null);
	}

	public WebElement accountSection() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSection", "AccountsPage", null);
	}

	public WebElement saveChangesCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesCategory", "Categories", null);
	}

	public WebElement savedCategoryTextFirst() {
		return SeleniumUtil.getVisibileWebElement(d, "savedCategoryTextFirst", "Categories", null);
	}

	public WebElement login() {
		return SeleniumUtil.getVisibileWebElement(d, "Login", "SAML_LOGIN", null);
	}

	public WebElement password() {
		return SeleniumUtil.getVisibileWebElement(d, "Password", "SAML_LOGIN", null);
	}

	public WebElement finAppId() {
		return SeleniumUtil.getVisibileWebElement(d, "finAppIdText", "SAML_LOGIN", null);
	}

	public WebElement loginButton() {
		return SeleniumUtil.getVisibileWebElement(d, "loginButton", "SAML_LOGIN", null);
	}

	public WebElement dashBoardCashForecast() {
		return SeleniumUtil.getWebElement(d, "dashBoardCashForecast", "DashboardPage", null);
	}

	public WebElement dashBoardCreditCardUsage() {
		return SeleniumUtil.getWebElement(d, "dashBoardCreditCardUsage", "DashboardPage", null);
	}

	public WebElement dashBoardSpending() {
		return SeleniumUtil.getWebElement(d, "dashBoardSpending", "DashboardPage", null);
	}

	public WebElement dashBoardBudget() {
		return SeleniumUtil.getWebElement(d, "dashBoardBudget", "DashboardPage", null);
	}

	public WebElement dashBoardSaveForAGoal() {
		return SeleniumUtil.getWebElement(d, "dashBoardSaveForAGoal", "DashboardPage", null);
	}

	public WebElement dashBoardCashFlow() {
		return SeleniumUtil.getWebElement(d, "dashBoardCashFlow", "DashboardPage", null);
	}

	public WebElement dashBoardWealth() {
		return SeleniumUtil.getWebElement(d, "dashBoardWealth", "DashboardPage", null);
	}

	public WebElement siteSearch() {
		return SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null);
	}

	public String[] dagSiteLink() {

		return PageParser.getElementValues("LinkAnAccount", false, "", "dagSiteLink");
	}

	public WebElement loginFormUsername() {
		return SeleniumUtil.getVisibileWebElement(d, "loginFormUsername", "LinkAnAccount", null);
	}

	public WebElement loginFormPassword() {
		return SeleniumUtil.getVisibileWebElement(d, "loginFormPassword", "LinkAnAccount", null);
	}

	public WebElement loginFormRePassword() {
		return SeleniumUtil.getVisibileWebElement(d, "loginFormRePassword", "LinkAnAccount", null);
	}

	public WebElement loginFormSubmit() {
		return SeleniumUtil.getVisibileWebElement(d, "loginFormSubmit", "LinkAnAccount", null);
	}

	public WebElement linkAnotherAccount() {
		return SeleniumUtil.getWebElement(d, "linkAnotherAccount", "LinkAnAccount", null);
	}

	public String[] addAccountSucess() {
		return PageParser.getElementValues("LinkAnAccount", false, "", "addAccountSucess");
	}

	public WebElement createBudgetButton() {
		return SeleniumUtil.getWebElement(d, "createBudgetButton", "Budget", null);
	}

	public WebElement budgetLinkAccount() {
		return SeleniumUtil.getWebElement(d, "createBudgetButton", "Budget", null);
	}

	public WebElement budgetMoreOptions() {
		return SeleniumUtil.getWebElement(d, "budgetMoreOptions", "Budget", null);
	}

	public WebElement budgetIncomeAndSpending() {
		return SeleniumUtil.getWebElement(d, "budgetIncomeAndSpending", "Budget", null);
	}

	public WebElement budgetcurrentMonthYear() {
		return SeleniumUtil.getWebElement(d, "budgetcurrentMonthYear", "Budget", null);
	}

	public WebElement budgetNotifications() {
		return SeleniumUtil.getWebElement(d, "budgetNotifications", "Budget", null);
	}

	public WebElement budgetBugdgetList() {
		return SeleniumUtil.getWebElement(d, "budgetBugdgetList", "Budget", null);
	}

	public WebElement budgetRecuringBills() {
		return SeleniumUtil.getWebElement(d, "budgetRecuringBills", "Budget", null);
	}

	public WebElement firstAccountMoreOption() {
		return SeleniumUtil.getVisibileWebElement(d, "firstAccountMoreOption", "LinkAnAccount", null);
	}

	public WebElement firstAccountMoreSettings() {
		return SeleniumUtil.getVisibileWebElement(d, "firstAccountMoreSettings", "LinkAnAccount", null);
	}

	public WebElement nickNameTextBoxAAS() {
		return SeleniumUtil.getVisibileWebElement(d, "nickNameTextBoxAAS", "AccountsPage", null);
	}

	public WebElement firtAccountNickName() {
		return SeleniumUtil.getVisibileWebElement(d, "firtAccountNickName", "AccountsPage", null);
	}

	public WebElement budgetSettings() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetSettings", "Budget", null);
	}

	public WebElement Account_delete() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_delete", "AccountsPage", null);
	}

	public WebElement Account_delete_CB() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_delete_CB", "AccountsPage", null);
	}

	public WebElement Account_delete_Confirm() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_delete_Confirm", "AccountsPage", null);
	}

	public WebElement Account_delete_Notif() {
		return SeleniumUtil.getWebElement(d, "Account_delete_Notif", "AccountsPage", null);
	}

	public WebElement budgetCatEdit() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetCatEdit", "Budget", null);
	}

	public WebElement budgetCatEditAmnt() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetCatEditAmnt", "Budget", null);
	}

	public WebElement budgetCatSave() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetCatSave", "Budget", null);
	}

	public WebElement budgetCatSaveNotification() {
		return SeleniumUtil.getWebElement(d, "budgetCatSaveNotification", "Budget", null);
	}

	public WebElement budgetAccgroup() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetAccgroup", "Budget", null);
	}

	public WebElement budgetAccgroupEdit() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetAccgroupEdit", "Budget", null);
	}

	public WebElement budgetAccgroupSave() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetAccgroupSave", "Budget", null);
	}

	public WebElement firstTansactionName() {
		return SeleniumUtil.getVisibileWebElement(d, "firstTansactionName", "Transaction", null);
	}

	public WebElement secondTansactionName() {
		return SeleniumUtil.getVisibileWebElement(d, "secondTansactionName", "Transaction", null);
	}

	public WebElement transactionDetailsSave() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionDetailsSave", "Transaction", null);
	}

	public WebElement transactionDetailsSplit() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionDetailsSplit", "Transaction", null);
	}

	public WebElement verifySaveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "verifySaveChangesBtn", "Transaction", null);
	}

	public WebElement SplitTransNotification() {
		return SeleniumUtil.getWebElement(d, "SplitTransNotification", "Transaction", null);
	}

	public WebElement splitTransUpadte() {
		return SeleniumUtil.getVisibileWebElement(d, "splitTransUpadte", "Transaction", null);
	}

	public List<WebElement> listOfMonthsInLegendSection() {
		return SeleniumUtil.getWebElements(d, "listOfMonthsInLegendSection", "Expense", null);
	}

	public WebElement TotalSpendingAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "TotalSpendingAmount", "Expense", null);
	}

	public WebElement startGoalGetStartButton() {
		return SeleniumUtil.getVisibileWebElement(d, "startGoalGetStartButton", "SFG", null);
	}

	public WebElement FirstAvailableGoal() {
		return SeleniumUtil.getVisibileWebElement(d, "FirstAvailableGoal", "SFG", null);
	}

	public WebElement FirstAvailableGoalCard() {
		return SeleniumUtil.getVisibileWebElement(d, "FirstAvailableGoalCard", "SFG", null);
	}

	public WebElement GoalAmountInput() {
		return SeleniumUtil.getVisibileWebElement(d, "GoalAmountInput", "SFG", null);
	}

	public WebElement GoalRadioFreq() {
		return SeleniumUtil.getVisibileWebElement(d, "GoalRadioFreq", "SFG", null);
	}

	public WebElement GoalAmtFrqInput() {
		return SeleniumUtil.getVisibileWebElement(d, "GoalAmtFrqInput", "SFG", null);
	}

	public WebElement SFGStartMyGoalButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SFGStartMyGoalButton", "SFG", null);
	}

	public WebElement SFGStartGoal() {
		return SeleniumUtil.getVisibileWebElement(d, "SFGStartGoal", "SFG", null);
	}

	public WebElement StartGoalNotfication() {
		return SeleniumUtil.getWebElement(d, "StartGoalNotfication", "SFG", null);
	}

	public WebElement SVGsaveFund() {
		return SeleniumUtil.getVisibileWebElement(d, "SVGsaveFund", "SFG", null);
	}

	public WebElement GoalAmountSaved() {
		return SeleniumUtil.getVisibileWebElement(d, "GoalAmountSaved", "SFG", null);
	}

	public WebElement amountfield1() {
		return SeleniumUtil.getVisibileWebElement(d, "amountfield1", "SFG", null);
	}

	public WebElement deleteGoalButton() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteGoalButton", "SFG", null);
	}

	public WebElement SFGFundingDeletePopupDeleteButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SFGFundingDeletePopupDeleteButton", "SFG", null);
	}

	public WebElement todaysNetworthTop() {
		return SeleniumUtil.getVisibileWebElement(d, "todaysNetworthTop", "ManageSharing", null);
	}

	public WebElement todaysAssetTop() {
		return SeleniumUtil.getVisibileWebElement(d, "todaysAssetTop", "ManageSharing", null);
	}

	public WebElement todaysNetworthBottom() {
		return SeleniumUtil.getVisibileWebElement(d, "todaysNetworthBottom", "ManageSharing", null);
	}

	public WebElement nWAssetsLabelBottom() {
		return SeleniumUtil.getVisibileWebElement(d, "nWAssetsLabelBottom", "ManageSharing", null);
	}

	public WebElement nWLiabilitiesLabelBottom() {
		return SeleniumUtil.getVisibileWebElement(d, "nWLiabilitiesLabelBottom", "ManageSharing", null);
	}

	public List<WebElement> listOfTransaction() {

		return SeleniumUtil.getWebElements(d, "listOfTransaction", "Transaction", null);
	}

	public WebElement InvestmentHoldTotalAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentHoldTotalAmt", "InvestmentHoldings", null);
	}

	public WebElement InvestmentHoldMarktAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentHoldMarktAmt", "InvestmentHoldings", null);
	}

	public WebElement InvestmentHoldGrpbyCB() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentHoldGrpbyCB", "InvestmentHoldings", null);
	}

	public WebElement InvestmentWidgetDashboard() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentWidgetDashboard", "DashboardPage", null);
	}

	public WebElement InvestmentDashboardNetAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentDashboardNetAmt", "DashboardPage", null);
	}

	public WebElement InvestmentWidgetDashboardAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentWidgetDashboardAmt", "DashboardPage", null);
	}

	public List<WebElement> VisibleOptionsUnderMoreBtn() {
		return SeleniumUtil.getWebElements(d, "VisibleOptionsUnderMoreBtn", "AccountsPage", null);
	}

	public void clickOnMoreBtn() {
		SeleniumUtil.click(AccountsMoreButton());
		SeleniumUtil.waitForPageToLoad();
	}

	public WebElement AccountsMoreButton() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsMoreButton", "AccountsPage", null);
	}

	public WebElement popupCrossIcon() {
		return SeleniumUtil.getWebElement(d, "popupCrossIcon", "AccountsPage", null);
	}

	public WebElement acntNameLabel() {
		return SeleniumUtil.getWebElement(d, "acntNameLabel", "AccountsPage", null);
	}

	public WebElement acntNickNameLabel() {
		return SeleniumUtil.getWebElement(d, "acntNickNameLabel", "AccountsPage", null);
	}

	public WebElement memoLabel() {
		return SeleniumUtil.getWebElement(d, "memoLabel", "AccountsPage", null);
	}

	public WebElement tagAllTransactnLabel() {
		return SeleniumUtil.getWebElement(d, "tagAllTransactnLabel", "AccountsPage", null);
	}

	public WebElement catAllTransactnLabel() {
		return SeleniumUtil.getWebElement(d, "catAllTransactn", "AccountsPage", null);
	}

	public WebElement applyCatToPastTransactnLabel() {
		return SeleniumUtil.getWebElement(d, "applyCatToPastTransactnLabel", "AccountsPage", null);
	}

	public WebElement applyTagToPastTransactnLabel() {
		return SeleniumUtil.getWebElement(d, "applyTagToPastTransactnLabel", "AccountsPage", null);
	}

	public WebElement showAcntInSummaryLabel() {
		return SeleniumUtil.getWebElement(d, "showAcntInSummaryLabel", "AccountsPage", null);
	}

	public WebElement DeleteBtn() {
		return SeleniumUtil.getWebElement(d, "DeleteBtn", "AccountsPage", null);
	}

	public WebElement closeAcntBtn() {
		return SeleniumUtil.getWebElement(d, "closeAcntBtn", "AccountsPage", null);
	}

	public WebElement saveChangesEditPopup() {
		return SeleniumUtil.getWebElement(d, "saveChangesEditPopup", "AccountsPage", null);
	}

	public WebElement tagNameTxtBox() {
		return SeleniumUtil.getWebElement(d, "tagNameTxtBox", "AccountsPage", null);
	}

	public List<WebElement> addedTags() {
		return SeleniumUtil.getWebElements(d, "addedTags", "AccountsPage", null);
	}

	public List<WebElement> deleteTag() {
		return SeleniumUtil.getWebElements(d, "deleteTag", "AccountsPage", null);
	}

	public WebElement AccountsPageHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsPageHeader", "AccountsPage", null);
	}

	public WebElement backToAcntsLink() {
		return SeleniumUtil.getVisibileWebElement(d, "backToAcntsLink", "AccountSettings", null);
	}

	public void clickingOnbackToAcntsLink() {
		SeleniumUtil.click(backToAcntsLink());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	public WebElement firtAccountSettingsIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "firtAccountSettingsIcon", "AccountSettings", null);
	}

	public String getHeaderText1() {
		String val = null;
		WebElement header = null;

		header = SeleniumUtil.waitUntilElementPresent(d, "css", "h2[class*='title']", 10);

		if (header == null)
			header = SeleniumUtil.getVisibileWebElement(d, "getHeaderText_MC", "Categories", frameName);
		if (header != null)
			val = header.getText().trim();

		return val;
	}

	public String[] getCategoryGroupByOrder() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "getCategoryGroupByOrder_1_MC", "Categories", null);

		String names[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			names[i] = l.get(i).getText();
		}
		return names;
	}

	public String[] getAccountViewBy() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "Account_SideB_Label_Values", "AccountsPage", null);

		String names[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			names[i] = l.get(i).getText();
		}
		return names;
	}
	

	public String[] getAlertSettingsSidebars() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "alertSettings_SideBars", "AlertSetting", null);

		String names[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			names[i] = l.get(i).getText().toLowerCase();
		}
		return names;
	}

	public WebElement isExpenseSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "isExpenseSelected_MC", "Categories", null);
	}

	public WebElement selectTransferTab() {
		return SeleniumUtil.getVisibileWebElement(d, "selectTransferTab", "Categories", null);
	}

	public WebElement selectIncomeTab() {
		return SeleniumUtil.getVisibileWebElement(d, "selectIncomeTab", "Categories", null);
	}

	public String getHighLevelCategoryName(int i) {
		SeleniumUtil.waitForPageToLoad();
		return d.findElement(By.xpath(
				"//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i + "]/a"))
				.getText().trim();
	}

	public void clickHighLevelCategoryByNum(int i) {
		SeleniumUtil.waitForPageToLoad();

		WebElement highLevel = d.findElement(By.xpath(
				"//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i + "]/a"));
		if (highLevel == null) {
			highLevel = d.findElement(
					By.xpath("//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i
							+ "]/a"));
			SeleniumUtil.click(highLevel);
		}
	}

	public WebElement showAsText() {
		return SeleniumUtil.getVisibileWebElement(d, "showAsText_MC", "Categories", null);
	}

	public WebElement editHLCatInputField() {
		return SeleniumUtil.getVisibileWebElement(d, "editHLCatInputField_MC", "Categories", null);
	}

	public WebElement cancelButton() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelButton", "Categories", null);
	}

	public WebElement saveChangesButton() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesButton_MC", "Categories", null);
	}

	public WebElement expenseCatLink() {
		return SeleniumUtil.getVisibileWebElement(d, "expenseCatLink_MC", "Categories", null);
	}

	public List<WebElement> firstMasterLevelExpenseCategory() {
		return SeleniumUtil.getWebElements(d, "firstMasterLevelExpenseCategory", "Categories", null);
	}

	public void typeSubCategoryName(String text) {
		SeleniumUtil.getVisibileWebElement(d, "typeSubCategoryName_MC", "Categories", null).sendKeys(text);
	}

	public WebElement addSubCatButtonInMLC() {
		return SeleniumUtil.getVisibileWebElement(d, "addSubCatButtonInMLC_MC", "Categories", null);
	}

	public void clickMLC(int hlcNum, int mlcNum) {
		SeleniumUtil.click(d.findElement(By.xpath("//div[@class='high-level-categories-container'][" + hlcNum
				+ "]//div[@class='master-level-categories-container'][" + mlcNum + "]")));

		SeleniumUtil.waitForPageToLoad(2000);
	}

	public List<WebElement> categoryErrorMsg() {
		return SeleniumUtil.getWebElements(d, "categoryErrorMsg_MC", "Categories", null);
	}

	public List<WebElement> editSubcat() {
		return SeleniumUtil.getWebElements(d, "editSubcat_MC", "Categories", null);
	}

	public WebElement getSubCatOne() {
		return SeleniumUtil.getVisibileWebElement(d, "getSubCatOne", "Categories", null);
	}

	public void clickDeleteSubCategoryButton(int i) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "clickDeleteSubCategoryButton_MC", "Categories", null);
		SeleniumUtil.click(l.get(i - 1));
	}

	public WebElement saveChangesButtonInMLC() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesButtonInMLC_MC", "Categories", null);
	}

	public String getSubCategoryName() {
		return SeleniumUtil.getVisibileWebElement(d, "getSubCategoryName_MC", "Categories", null).getAttribute("value");
	}

	public List<WebElement> getCategoryLL() {
		return SeleniumUtil.getWebElements(d, "getCategoryLL", "CategorzationRules", null);
	}

	public WebElement categorizationRules() {
		return SeleniumUtil.getVisibileWebElement(d, "categorizationRules", "CategorzationRules", null);
	}

	public WebElement dropdwnOPtionEdit() {
		return SeleniumUtil.getVisibileWebElement(d, "dropdwnOPtionEdit", "CategorzationRules", null);
	}

	public WebElement dropdwnOPtionDelete() {
		return SeleniumUtil.getVisibileWebElement(d, "dropdwnOPtionDelete", "CategorzationRules", null);
	}

	public WebElement deletPopUpDelete() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUpDelete", "CategorzationRules", null);
	}

	public WebElement createRulesButton() {
		return SeleniumUtil.getWebElement(d, "createRulesButton", "CategorzationRules", null);
	}

	public WebElement dropdwnOption() {
		return SeleniumUtil.getWebElement(d, "dropdwnOption", "CategorzationRules", null);
	}

	public WebElement descTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "descTextBox", "CategorzationRules", null);
	}

	public WebElement amtDescBox() {
		return SeleniumUtil.getVisibileWebElement(d, "amtDescBox", "CategorzationRules", null);
	}

	public WebElement categoriesDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "categoriesDropDown", "CategorzationRules", null);
	}

	public WebElement applyRuleSwitchToggle() {
		return SeleniumUtil.getVisibileWebElement(d, "applyRuleSwitchToggle", "CategorzationRules", null);
	}

	public WebElement createRuleIdButtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createRuleIdButtn", "CategorzationRules", null);
	}

	public WebElement SaveChgBttn() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveChgBttn", "CategorzationRules", null);
	}

	public WebElement RuleEditToastMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "RuleEditToastMsg", "CategorzationRules", null);
	}

	public String[] ruleDeltedToastMsg() {
		return PageParser.getElementValues("CategorzationRules", false, "", "ruleDeltedToastMsg");
	}

	public WebElement groupNameTxtBox() {
		return SeleniumUtil.getVisibileWebElement(d, "groupNameTxtBoxAGS", "AccountGroups", null);

	}

	public List<WebElement> getAccountOptionChckBox() {

		String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountGroups", null, "getAccountOptionChckBoxAGS");

		List<WebElement> l = d.findElements(By.xpath(varX));

		return l;

	}

	public WebElement createOrUpdateGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "createOrUpdateGroupAGS", "AccountGroups", null);
	}

	public WebElement GroupName() {
		return SeleniumUtil.getVisibileWebElement(d, "GroupNameAGS", "AccountGroups", null);
	}

	public WebElement emptyGroupMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "emptyGroupMessageAGS", "AccountGroups", null);

	}

	public WebElement groupList(String Gname) {
		return d.findElement(By.xpath("(//a[contains(text(),'" + Gname + "')])"));
	}

	public WebElement editButton(String name) {
		return d.findElement(By.xpath("//a[contains(@aria-label,'dit " + name + "')]"));
	}

	public WebElement saveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtnAGS", "AccountGroups", null);

	}

	public WebElement testAutomationGrp() {
		return SeleniumUtil.getVisibileWebElement(d, "TestAutomationGroup", "AccountGroups", null);
	}

	public WebElement deleteGroupButton(String name) {
		return d.findElement(By.xpath("//a[contains(@id,'delete-icon')]"));
	}

	public WebElement confirmDeleteText() {
		return SeleniumUtil.getVisibileWebElement(d, "confirmDeleteTextAGS", "AccountGroups", null);
	}

	public WebElement cancelButtonInGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelButtonInGroupAGS", "AccountGroups", null);
	}

	public WebElement closeDialog() {
		return SeleniumUtil.getVisibileWebElement(d, "closeDialogAGS", "AccountGroups", null);
	}

	public WebElement deleteGroupOption() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteGroupOptionAGS", "AccountGroups", null);
	}

	public WebElement linkManualAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccount", "LinkAnAccount", null);
	}
	public List<WebElement> linkManualAccountfl3() {
		return SeleniumUtil.getWebElements(d, "linkManualAccountfl3", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountTypeDD() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeDD", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountTypeDDfl3() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeDDfl3", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountType() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountType", "LinkAnAccount", null);
	}

	public WebElement linkManualAccountName() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountName", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountNamefl3() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountNamefl3", "LinkAnAccount", null);
	}

	public WebElement linkManualAccountDue() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountDue", "LinkAnAccount", null);
	}

	public WebElement linkManualAccountDuedate() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountDuedate", "LinkAnAccount", null);
	}

	public WebElement linkManualAccountAdd() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountAdd", "LinkAnAccount", null);
	}

	public WebElement linkManualAccountNext() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountNext", "LinkAnAccount", null);
	}

	public WebElement linkRealEstAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "linkRealEstAccount", "LinkAnAccount", null);
	}

	public WebElement linkRealEstAccountManual() {
		return SeleniumUtil.getVisibileWebElement(d, "linkRealEstAccountManual", "LinkAnAccount", null);
	}

	public WebElement linkRealEstAccountName() {
		return SeleniumUtil.getVisibileWebElement(d, "linkRealEstAccountName", "LinkAnAccount", null);
	}

	public WebElement linkRealEstAccountEstValue() {
		return SeleniumUtil.getVisibileWebElement(d, "linkRealEstAccountEstValue", "LinkAnAccount", null);
	}

	public WebElement linkRealEstAccountaDD() {
		return SeleniumUtil.getVisibileWebElement(d, "linkRealEstAccountaDD", "LinkAnAccount", null);
	}

	public WebElement Account_Refresh_All() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_Refresh_All", "AccountsPage", null);
	}

	public WebElement Account_Refresh_Icon() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_Refresh_Icon", "AccountsPage", null);
	}

	public WebElement accounts_Header_btn_LinkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "accounts_Header_btn_LinkAccount", "AccountsPage", null);
	}

	public WebElement Account_More_feature() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_Refresh_Icon", "AccountsPage", null);
	}

	public WebElement Account_More_Settings() {
		return SeleniumUtil.getVisibileWebElement(d, "accounts_Header_btn_LinkAccount", "AccountsPage", null);
	}

	public WebElement Account_SideBar_AccGrp() {
		return SeleniumUtil.getVisibileWebElement(d, "Account_SideBar_AccGrp", "AccountsPage", null);
	}

	public WebElement dashBoardAccounts() {
		return SeleniumUtil.getVisibileWebElement(d, "dashBoardAccounts", "DashboardPage", null);
	}

	public WebElement dashBoardTransaction() {
		return SeleniumUtil.getVisibileWebElement(d, "dashBoardTransaction", "DashboardPage", null);
	}

	public WebElement dashBoardLinkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "dashBoardLinkAccount", "DashboardPage", null);
	}

	public WebElement spendingAddTransaction() {
		return SeleniumUtil.getVisibileWebElement(d, "spendingAddTransaction", "Expense", null);
	}

	public WebElement spendingAddTransactionNotf() {
		return SeleniumUtil.getWebElement(d, "spendingAddTransactionNotf", "Expense", null);
	}

	public WebElement eABarChartTwo() {
		return SeleniumUtil.getWebElement(d, "eABarChartTwo", "Expense", null);
	}

	public WebElement eABarChartTwoText() {
		return SeleniumUtil.getWebElement(d, "eABarChartTwoText", "Expense", null);
	}

	public WebElement eADateRangeDD() {
		return SeleniumUtil.getWebElement(d, "eADateRangeDD", "Expense", null);
	}

	public WebElement eAmoreOptFeatureTour() {
		return SeleniumUtil.getWebElement(d, "eAmoreOptFeatureTour", "Expense", null);
	}

	public WebElement eAmoreOptPrint() {
		return SeleniumUtil.getWebElement(d, "eAmoreOptPrint", "Expense", null);
	}

	public WebElement eAmoreOptSettings() {
		return SeleniumUtil.getWebElement(d, "eAmoreOptSettings", "Expense", null);
	}

	public WebElement eALinkAccount() {
		return SeleniumUtil.getWebElement(d, "eALinkAccount", "Expense", null);
	}

	public WebElement cashFlow_Print() {
		return SeleniumUtil.getWebElement(d, "cashFlow_Print", "CashFlow", null);
	}

	public WebElement cashFlow_LinkAccButton() {
		return SeleniumUtil.getWebElement(d, "cashFlow_LinkAccButton", "CashFlow", null);
	}

	public WebElement cashFlow_alertSett() {
		return SeleniumUtil.getWebElement(d, "cashFlow_alertSett", "CashFlow", null);
	}

	public WebElement cashFlow_featureTour() {
		return SeleniumUtil.getWebElement(d, "cashFlow_featureTour", "CashFlow", null);
	}

	public WebElement cashFlow_addTrans() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFlow_addTrans", "CashFlow", null);
	}

	public WebElement EditCredentials() {
		return SeleniumUtil.getVisibileWebElement(d, "EditCredentials", "AccountSettings", null);
	}

	public WebElement goToSiteTab() {
		return SeleniumUtil.getVisibileWebElement(d, "goToSiteTab", "AccountSettings", null);
	}

	public List<WebElement> settingAtAcntLevel() {
		return SeleniumUtil.getWebElements(d, "settingAtAcntLevel", "AccountSettings", frameName);
	}

	public WebElement dashBoardWidgetToNetworth() {
		return SeleniumUtil.getVisibileWebElement(d, "dashBoardWidgetToNetworth", "NetWorth", null);
	}

	public WebElement netWorthBackTODash() {
		return SeleniumUtil.getWebElement(d, "netWorthBackTODash", "NetWorth", null);
	}

	public WebElement NetworthCard() {
		return SeleniumUtil.getWebElement(d, "card_NL", "DashboardPage", null);
	}

	public WebElement spendingMenu() {
		return SeleniumUtil.getVisibileWebElement(d, "spendingMenu", "Budget", null);
	}

	public WebElement moreOptionMenuHide() {
		return SeleniumUtil.getWebElement(d, "moreOptionMenuHide", "Budget", frameName);
	}

	public WebElement eATrasactionHeader() {
		return SeleniumUtil.getWebElement(d, "eATrasactionHeader", "Expense", null);
	}

	public WebElement iATrasactionHeader() {
		return SeleniumUtil.getWebElement(d, "iATrasactionHeader", "Expense", null);
	}

	public WebElement allIncomceAccounts() {
		return SeleniumUtil.getVisibileWebElement(d, "allIncomceAccounts", "Expense", null);
	}

	public WebElement groupTab() {
		return SeleniumUtil.getVisibileWebElement(d, "groupTab", "Expense", null);
	}

	public WebElement manageGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "manageGroup", "Expense", null);
	}

	public WebElement iADropDownText() {
		return SeleniumUtil.getVisibileWebElement(d, "iADropDownText", "Expense", null);
	}

	public WebElement backToIA() {
		return SeleniumUtil.getVisibileWebElement(d, "backToIA", "Expense", null);
	}

	public String[] cashInFlowDataColmn() {
		return PageParser.getElementValues("CashFlow", false, "", "cashInFlowDataColmn");
	}

	public List<WebElement> cashInFlowDataColmnSize() {
		return SeleniumUtil.getWebElements(d, "cashInFlowDataColmnSize", "CashFlow", null);
	}

	public List<WebElement> transactionPageList() {
		return SeleniumUtil.getWebElements(d, "transactionPageList", "Transaction", null);
	}

	public String[] transDescErrorMsg() {
		return PageParser.getElementValues("Transaction", false, "", "transDescErrorMsg");
	}

	public WebElement accountAmtText() {
		return SeleniumUtil.getVisibileWebElement(d, "accountAmtText", "LinkAnAccount", null);
	}

	public WebElement fastLinkClose() {
		return SeleniumUtil.getVisibileWebElement(d, "fastLinkClose", "LinkAnAccount", null);
	}

	public WebElement fFtitleDd() {
		return SeleniumUtil.getVisibileWebElement(d, "fFtitleDd", "FinancialForecast", null);
	}

	public WebElement fFtitleDdCredit() {
		return SeleniumUtil.getVisibileWebElement(d, "fFtitleDdCredit", "FinancialForecast", null);
	}

	public WebElement fFTrendstab() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendstab", "FinancialForecast", null);
	}

	public WebElement fFTrendstabAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendstabAmt", "FinancialForecast", null);
	}

	public WebElement fFForecastTab() {
		return SeleniumUtil.getVisibileWebElement(d, "fFForecastTab", "FinancialForecast", null);
	}

	public String[] fFTren() {

		return PageParser.getElementValues("FinancialForecast", false, "", "fFTren");
	}

	public WebElement LinkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccount", "AccountsPage", null);
	}

	public WebElement addTransactionHeaderFun_MT() {
		return SeleniumUtil.getVisibileWebElement(d, "addTransactionHeaderFun_MT", "Transaction", null);
	}

	public WebElement iAiEDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "iAiEDropDown", "Expense", null);
	}

	public void addDagAccount(String dagSiteType, String dagUsername, String dagPwd, String dagConfirmPwd) {

		SeleniumUtil.click(noDataScreen_Button());
		siteSearch().sendKeys("DagSite");
		SeleniumUtil.waitForPageToLoad(2000);
		String[] dagSites = dagSiteLink();
		d.findElement(By.xpath(dagSites[1].replace("+", dagSiteType))).click();
		SeleniumUtil.waitForPageToLoad(2000);
		loginFormUsername().sendKeys(PropsUtil.getDataPropertyValue(dagUsername));
		SeleniumUtil.waitForPageToLoad(2000);
		loginFormPassword().sendKeys(PropsUtil.getDataPropertyValue(dagPwd));
		loginFormRePassword().sendKeys(PropsUtil.getDataPropertyValue(dagConfirmPwd));
		loginFormSubmit().click();
		SeleniumUtil.waitForPageToLoad();
		String[] accntSuccess = addAccountSucess();
		SeleniumUtil.waitForElementVisible(d, By.xpath(accntSuccess[1]), 30);

	}

	public WebElement FFDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "FFDropDown", "FinancialForecast", null);
	}

	public WebElement FFDropDownCredit() {

		return SeleniumUtil.getVisibileWebElement(d, "FFDropDownCredit", "FinancialForecast", null);
	}

	public WebElement getAccountOptionChckBox_01() {
		return SeleniumUtil.getVisibileWebElement(d, "getAccountOptionChckBoxAGS_01", "AccountGroups", null);
	}

	public String[] addTransDebitedFromValues() {

		return PageParser.getElementValues("Transaction", false, "", "addTransDebitedFromValues");

	}

	public WebElement linkAccCashCardNo() {

		return SeleniumUtil.getVisibileWebElement(d, "linkAccCashCardNo", "LinkAnAccount", null);
	}

	public WebElement linkAccCreditCardNo() {

		return SeleniumUtil.getVisibileWebElement(d, "linkAccCreditCardNo", "LinkAnAccount", null);
	}

	public WebElement fFCashCheckBox() {

		return SeleniumUtil.getVisibileWebElement(d, "fFCashCheckBox", "FinancialForecast", null);
	}

	public WebElement fFCardCheckBox() {

		return SeleniumUtil.getVisibileWebElement(d, "fFCardCheckBox", "FinancialForecast", null);
	}

	public WebElement getNegativeValue() {
		return SeleniumUtil.getVisibileWebElement(d, "getNegativeValue", "FinancialForecast", null);
	}

	public WebElement carddrpdwn() {
		return SeleniumUtil.getVisibileWebElement(d, "carddrpdwn", "FinancialForecast", null);
	}

	public WebElement cardamtcomp() {
		return SeleniumUtil.getWebElement(d, "cardamtcomp", "FinancialForecast", null);
	}

	public WebElement forecast() {
		return SeleniumUtil.getVisibileWebElement(d, "forecast", "FinancialForecast", null);
	}

	public WebElement linkaccCloseff() {
		return SeleniumUtil.getVisibileWebElement(d, "linkaccCloseff", "FinancialForecast", null);
	}

	public WebElement forecurrbal() {
		return SeleniumUtil.getVisibileWebElement(d, "forecurrbal", "FinancialForecast", null);
	}

	public WebElement foreprojtdbal() {
		return SeleniumUtil.getVisibileWebElement(d, "foreprojtdbal", "FinancialForecast", null);
	}

	public WebElement groupCreateGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "groupCreateGroup", "AccountGroups", null);
	}

	public List<WebElement> fFFTUCashAccounts() {

		return SeleniumUtil.getWebElements(d, "fFFTUCashAccounts", "FinancialForecast", null);
	}

	public WebElement fFTrendsFilterAccountText() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendsFilterAccountText", "FinancialForecast", null);
	}

	public WebElement fFTrendsFilterAccountDd() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendsFilterAccountDd", "FinancialForecast", null);
	}

	public List<WebElement> fFTrendsFilterAccountList() {

		return SeleniumUtil.getWebElements(d, "fFTrendsFilterAccountList", "FinancialForecast", null);
	}

	public List<WebElement> fFTrendsFilterAccountListName() {

		return SeleniumUtil.getWebElements(d, "fFTrendsFilterAccountListName", "FinancialForecast", null);
	}

	public WebElement fFTrendsFirstTransaction() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendsFirstTransaction", "FinancialForecast", null);
	}

	public WebElement fFTrendsFilterAccountDdCancel() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendsFilterAccountDdCancel", "FinancialForecast", null);
	}

	public WebElement fFTrendsFilterAccountListNameText() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendsFilterAccountListNameText", "FinancialForecast", null);
	}

	public WebElement fFTrendsFirstTransactionDebitFromAcc() {
		return SeleniumUtil.getVisibileWebElement(d, "fFTrendsFirstTransactionDebitFromAcc", "FinancialForecast", null);
	}

	public List<WebElement> fFFTUSelectedAccount() {

		return SeleniumUtil.getWebElements(d, "fFFTUSelectedAccount", "FinancialForecast", null);
	}

	public List<WebElement> addTransDebitedFromValues01() {

		return SeleniumUtil.getWebElements(d, "addTransDebitedFromValues", "Transaction", null);
	}

	public WebElement createGroupClosePopup() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupClosePopup", "AccountGroups", null);
	}

	public WebElement createGroupButtonTwoAGS() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupButtonTwoAGS", "AccountGroups", null);
	}

	public String[] groupCreateModalWindow() {
		return PageParser.getElementValues("AccountGroups", false, "", "groupCreateModalWindow");
	}

	public List<WebElement> fFFTUCheckedAccounts() {

		return SeleniumUtil.getWebElements(d, "fFFTUCheckedAccounts", "FinancialForecast", null);
	}

	public String[] successmsgeditcreds() {
		return PageParser.getElementValues("AccountSettings", false, "", "successmsgeditcreds");
	}

	public WebElement groupUpdatedMessage() {
		return SeleniumUtil.getWebElement(d, "groupUpdatedMessage", "Budget", null);
	}

	public WebElement firstbudgtcate() {
		return SeleniumUtil.getVisibileWebElement(d, "firstbudgtcate", "Budget", null);
	}

	public WebElement firstbudgtcatetran() {
		return SeleniumUtil.getVisibileWebElement(d, "firstbudgtcatetran", "Budget", null);
	}

	public WebElement edittranvalidation() {
		return SeleniumUtil.getVisibileWebElement(d, "edittranvalidation", "Budget", null);
	}

	public WebElement SLTYE_budgetGetStartedBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "SLTYE_budgetGetStartedBtn", "FinCheck", null);
	}

	public WebElement finCheckContinueBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "finCheckContinueBtn", "FinCheck", null);
	}

	public WebElement anualGrossIncome() {
		return SeleniumUtil.getVisibileWebElement(d, "anualGrossIncome", "FinCheck", null);
	}

	public WebElement creditScoreDd() {
		return SeleniumUtil.getVisibileWebElement(d, "creditScoreDd", "FinCheck", null);
	}

	public WebElement creditScoreDdValue() {
		return SeleniumUtil.getVisibileWebElement(d, "creditScoreDdValue", "FinCheck", null);
	}

	public WebElement finCheckNxtButton() {
		return SeleniumUtil.getVisibileWebElement(d, "finCheckNxtButton", "FinCheck", null);
	}

	public WebElement alwaysPlannedAheadRB() {
		return SeleniumUtil.getVisibileWebElement(d, "alwaysPlannedAheadRB", "FinCheck", null);
	}

	public WebElement fincheckFinish() {
		return SeleniumUtil.getVisibileWebElement(d, "fincheckFinish", "FinCheck", null);
	}

	public WebElement finSeeRecom() {
		return SeleniumUtil.getVisibileWebElement(d, "finSeeRecom", "FinCheck", null);
	}

	public String[] accountsSummaryHideAccount() {

		return PageParser.getElementValues("AccountsPage", false, "", "accountsSummaryHideAccount");

	}

	public List<WebElement> accountSettSize() {
		return SeleniumUtil.getWebElements(d, "accountSettSize", "AccountSettings", null);
	}

	public WebElement accountSettingsIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsIcon", "AccountSettings", null);
	}

	public WebElement accountSettingsFirstAccSummary() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsFirstAccSummary", "AccountSettings", null);
	}

	public WebElement accountSettingsHideSummary() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsHideSummary", "AccountSettings", null);
	}

	public WebElement accountSettingsSaveChanges() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsSaveChanges", "AccountSettings", null);
	}

	public WebElement dashBoardGetStarted() {
		return SeleniumUtil.getVisibileWebElement(d, "dashBoardGetStarted", "DashboardPage", null);
	}

	public WebElement moneyCenterLogo() {
		return SeleniumUtil.getVisibileWebElement(d, "moneyCenterLogo", "DashboardPage", null);
	}

	
	//Add Manual Transaction
	//Added by Abhinandan SUSTQA
	public void addTransacion(String amnt,String transDesc,String transactionType,String categoryType) throws InterruptedException {

		SeleniumUtil.waitForPageToLoad();
		waitUntilSpinnerDisappears(d, 60);
		transactionManualAdd().click();
		amount_AMT().sendKeys(PropsUtil.getDataPropertyValue(amnt));
		description_AMT().sendKeys(PropsUtil.getDataPropertyValue(transDesc));
		SeleniumUtil.waitForPageToLoad(2000);
		transactionManualTypeDropDown().click();
		String[] transType = transactionManualTypeValue();
		d.findElement(By.xpath(transType[1].replace("+", transactionType))).click();
		SeleniumUtil.waitForPageToLoad(2000);
		transactionManualDebitedFrom().click();
		transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		transactionManualcategoryDropDown().click();
		SeleniumUtil.waitForPageToLoad(1500);
		transactionManualcategoryText().sendKeys(PropsUtil.getDataPropertyValue(categoryType));
		transactioncategoryDropdownValue().click();
		SeleniumUtil.waitForPageToLoad(1500);
		transactionEventAdd().click();
		SeleniumUtil.waitUntilElementInvisible(d, "transactionEventAdd", "Transaction", null);

	}
	
	
	public String[] editManualtrans() {
		return PageParser.getElementValues("Transaction", false, "", "editManualtrans");
	}
	public WebElement addTransDescError() {
		return SeleniumUtil.getVisibileWebElement(d, "addTransDescError", "Transaction", null);
	}
	public WebElement transactionEventShowMoreOption() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionEventShowMoreOption", "Transaction", null);
	}
	public WebElement check_AMT() {
		return SeleniumUtil.getVisibileWebElement(d, "check_AMT", "Transaction", null);
	}
	public WebElement transactionEditSave1() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionEditSave1", "Transaction", null);
	}
	public WebElement durationThisYear() {
		return SeleniumUtil.getVisibileWebElement(d, "durationThisYear", "CashFlow", null);
	}
	public WebElement backToCashFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "backToCashFlow", "CashFlow", null);
	}
	public List<WebElement> cashFlowDetailsDateRange() {

		return SeleniumUtil.getWebElements(d, "cashFlowDetailsDateRange", "CashFlow", null);
	}
	public WebElement cashFlowTimeFilterSelectedValue() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFlowTimeFilterSelectedValue", "CashFlow", null);
	}
	public WebElement categoryGasolineFuel() {
		return SeleniumUtil.getVisibileWebElement(d, "categoryGasolineFuel", "Categories", null);
	}
	
	public WebElement editMLCatInputField_MC() {
		return SeleniumUtil.getVisibileWebElement(d, "editMLCatInputField_MC", "Categories", null);
	}
	public WebElement linkManualAccountTypeLoans() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeLoans", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountTypeLoansfl3() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeLoansfl3", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountTypeMortage() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeMortage", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountTypeMortagefl3() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeMortagefl3", "LinkAnAccount", null);
	}
	public WebElement manualAccountCurrentBalance() {
		return SeleniumUtil.getVisibileWebElement(d, "manualAccountCurrentBalance", "LinkAnAccount", null);
	}
	public WebElement manualAccountCurrentBalancefl3() {
		return SeleniumUtil.getVisibileWebElement(d, "manualAccountCurrentBalancefl3", "LinkAnAccount", null);
	}
	public WebElement manualAccountAmountDue() {
		return SeleniumUtil.getVisibileWebElement(d, "manualAccountAmountDue", "LinkAnAccount", null);
	}
	public WebElement manualAccountAmountDuefl3() {
		return SeleniumUtil.getVisibileWebElement(d, "manualAccountAmountDuefl3", "LinkAnAccount", null);
	}
	public WebElement manualAccountNextDuedate() {
		return SeleniumUtil.getVisibileWebElement(d, "manualAccountNextDuedate", "LinkAnAccount", null);
	}
	public WebElement manualAccountNextDuedatefl3() {
		return SeleniumUtil.getVisibileWebElement(d, "manualAccountNextDuedatefl3", "LinkAnAccount", null);
	}
	public WebElement accounts_Type_Acc() {
		return SeleniumUtil.getVisibileWebElement(d, "accounts_Type_Acc", "AccountsPage", null);
	}
	public List<WebElement> accountsList() {

		return SeleniumUtil.getWebElements(d, "accountsList", "AccountsPage", null);
	}
	public List<WebElement> totalnetTransfer() {

		return SeleniumUtil.getWebElements(d, "totalnetTransfer", "CashFlow", null);
	}
	public WebElement inflowTotal() {
		return SeleniumUtil.getVisibileWebElement(d, "inflowTotal", "CashFlow", null);
	}
	public WebElement outflowTotal() {
		return SeleniumUtil.getVisibileWebElement(d, "outflowTotal", "CashFlow", null);
	}
	public WebElement netcashflowTotal() {
		return SeleniumUtil.getVisibileWebElement(d, "netcashflowTotal", "CashFlow", null);
	}
	
	public WebElement saveBtn_AccSettingsPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "saveBtn_AccSettingsPopUp", "AccountsPage", null);
	}
	public WebElement ManualAcntNameTxtBox() {
		return SeleniumUtil.getVisibileWebElement(d, "ManualAcntNameTxtBox", "AccountsPage", null);
	}
	public WebElement otherAssetsMoreSetting() {
		return SeleniumUtil.getVisibileWebElement(d, "otherAssetsMoreSetting", "AccountsPage", null);
	}
	public WebElement otherLiablMoreSetting() {
		return SeleniumUtil.getVisibileWebElement(d, "otherLiablMoreSetting", "AccountsPage", null);
	}
	public WebElement otherAssetsAccMoreSetting() {
		return SeleniumUtil.getVisibileWebElement(d, "otherAssetsAccMoreSetting", "AccountsPage", null);
	}
	public WebElement otherLiablAccMoreSetting() {
		return SeleniumUtil.getVisibileWebElement(d, "otherLiablAccMoreSetting", "AccountsPage", null);
	}
	public WebElement linkManualAccountTypeLiabilities() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeLiabilities", "LinkAnAccount", null);
	}
	public WebElement linkManualAccountTypeAssets() {
		return SeleniumUtil.getVisibileWebElement(d, "linkManualAccountTypeAssets", "LinkAnAccount", null);
	}
	
	public List<WebElement> accountLists() {

		return SeleniumUtil.getWebElements(d, "accountLists", "LinkAnAccount", null);
	}
	
	public WebElement uncaterulelink() {

        return SeleniumUtil.getVisibileWebElement(d, "uncaterulelink", "CategorzationRules", null);
  }
  
  public WebElement verifytranloading() {

        return SeleniumUtil.getVisibileWebElement(d, "verifytranloading", "CategorzationRules", null);
  }
  
  public WebElement finappheader() {

        return SeleniumUtil.getVisibileWebElement(d, "finappheader", "CategorzationRules", null);
  }
  public WebElement backtoCatRules() {

      return SeleniumUtil.getVisibileWebElement(d, "backtoCatRules", "CategorzationRules", null);
}
  
  public WebElement groupNameDuplicareError() {
	  return SeleniumUtil.getVisibileWebElement(d, "groupNameDuplicareError", "AccountsPage", null);
	}
  public WebElement dashBcreditError() {
	  return SeleniumUtil.getVisibileWebElement(d, "dashBcreditError", "DashboardPage", null);
	}
  public WebElement backtoDashBFromcreditDB() {
	  return SeleniumUtil.getVisibileWebElement(d, "backtoDashBFromcreditDB", "DashboardPage", null);
	}
  public WebElement transMoreOptions() {
	  return SeleniumUtil.getVisibileWebElement(d, "transMoreOptions", "Transaction", null);
	}
  public WebElement transMoreOptionsDownload() {
	  return SeleniumUtil.getVisibileWebElement(d, "transMoreOptionsDownload", "Transaction", null);
	}
  public WebElement transMoreOptionsfeatureTour() {
	  return SeleniumUtil.getVisibileWebElement(d, "transMoreOptionsfeatureTour", "Transaction", null);
	}
  public WebElement transMoreOptionsPrint() {
	  return SeleniumUtil.getVisibileWebElement(d, "transMoreOptionsPrint", "Transaction", null);
	}
  public WebElement transLinkAccount() {
	  return SeleniumUtil.getVisibileWebElement(d, "transLinkAccount", "Transaction", null);
	}
  public WebElement runFirstRule() {
	  return SeleniumUtil.getVisibileWebElement(d, "runFirstRule", "CategorzationRules", null);
	}
  public WebElement runRulesNotfMessage() {
	  return SeleniumUtil.getVisibileWebElement(d, "runRulesNotfMessage", "CategorzationRules", null);
	}
  public WebElement accntGroupdDelete() {
	  return SeleniumUtil.getVisibileWebElement(d, "accntGroupdDelete", "AccountGroups", null);
	}
  public WebElement accntGroupdEdit() {
	  return SeleniumUtil.getVisibileWebElement(d, "accntGroupdEdit", "AccountGroups", null);
	}
  public WebElement accountLoanSettingsIcon() {
	  return SeleniumUtil.getVisibileWebElement(d, "accountLoanSettingsIcon", "AccountSettings", null);
	}
  public WebElement accountMortgageSettingsIcon() {
	  return SeleniumUtil.getVisibileWebElement(d, "accountMortgageSettingsIcon", "AccountSettings", null);
	}
  public WebElement accountSettingsManualHideSummary() {
	  return SeleniumUtil.getVisibileWebElement(d, "accountSettingsManualHideSummary", "AccountSettings", null);
	}
 
  public WebElement accountSetManualSave() {
	  return SeleniumUtil.getVisibileWebElement(d, "accountSetManualSave", "AccountSettings", null);
	}
  
  public WebElement selectExistingDd() {
	  return SeleniumUtil.getVisibileWebElement(d, "selectExistingDd", "Budget", null);
	}
  public List<WebElement> selectExistingDdHide() {
		return SeleniumUtil.getWebElements(d, "selectExistingDdHide", "Budget", null);
	}
  
  
  
  public String[] getTransactionCurrencyList() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "transactionCurrencyList", "Expense", null);

		String names[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			names[i] = l.get(i).getText().trim();
		}
		return names;
	}
  public ArrayList<String> getbudgetCategoryList() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "budgetCategoryList", "Budget", null);

		ArrayList<String> list = new ArrayList<String>();
		
		for (WebElement ele:l) {
			list.add(ele.getText());
		}
		return list;
	}
  
  public WebElement addFlexSpending() {
	  return SeleniumUtil.getVisibileWebElement(d, "addFlexSpending", "Budget", null);
	}
  public WebElement addFlexSpendingCategoryDd() {
	  return SeleniumUtil.getVisibileWebElement(d, "addFlexSpendingCategoryDd", "Budget", null);
	}
  public WebElement addFlexSpendingCategoryDdText() {
	  return SeleniumUtil.getVisibileWebElement(d, "addFlexSpendingCategoryDdText", "Budget", null);
	}
  public WebElement addFlexSpendingCategoryDdSavings() {
	  return SeleniumUtil.getVisibileWebElement(d, "addFlexSpendingCategoryDdSavings", "Budget", null);
	}
  public WebElement addFlexSpendingCategoryAmt() {
	  return SeleniumUtil.getVisibileWebElement(d, "addFlexSpendingCategoryAmt", "Budget", null);
	}
  public WebElement addFlexSpendingCategoryAmtAdd() {
	  return SeleniumUtil.getVisibileWebElement(d, "addFlexSpendingCategoryAmtAdd", "Budget", null);
	}
  public WebElement budgetMySavingsCategory() {
	  return SeleniumUtil.getVisibileWebElement(d, "budgetMySavingsCategory", "Budget", null);
	}
  public WebElement budgetMySavingsCategoryPercentageOutside() {
	  return SeleniumUtil.getVisibileWebElement(d, "budgetMySavingsCategoryPercentageOutside", "Budget", null);
	}
  public WebElement budgetMySavingsCategoryPercentageInside() {
	  return SeleniumUtil.getVisibileWebElement(d, "budgetMySavingsCategoryPercentageInside", "Budget", null);
	}
  public WebElement budgetMySavingsCategoryAmountOutside() {
	  return SeleniumUtil.getVisibileWebElement(d, "budgetMySavingsCategoryAmountOutside", "Budget", null);
	}
  
  public WebElement budgetMySavingsCategoryAmountInside() {
	  return SeleniumUtil.getVisibileWebElement(d, "budgetMySavingsCategoryAmountInside", "Budget", null);
	}
  public WebElement transactionCurrencyListDd() {
	  return SeleniumUtil.getVisibileWebElement(d, "transactionCurrencyListDd", "Expense", null);
	}
  public WebElement createBdgtButton() {
	  return SeleniumUtil.getVisibileWebElement(d, "createBdgtButton", "Budget", null);
	}
  
  public WebElement SFCLandingPageAccountDropDown() {
	  return SeleniumUtil.getVisibileWebElement(d, "SFCLandingPageAccountDropDown", "FinancialForecast", null);
	}
  
  public List<WebElement> fFtrendsMultiAccountCheckedBoxes() {
		return SeleniumUtil.getWebElements(d, "fFtrendsMultiAccountCheckedBoxes", "FinancialForecast", null);
	}
  public List<WebElement> fFtrendsMultiAccountUnCheckedBoxes() {
		return SeleniumUtil.getWebElements(d, "fFtrendsMultiAccountUnCheckedBoxes", "FinancialForecast", null);
	}
  public List<WebElement> fFtrendsMultiAccountCckedBoxAmnt() {
		return SeleniumUtil.getWebElements(d, "fFtrendsMultiAccountCckedBoxAmnt", "FinancialForecast", null);
	}
  
  public WebElement fFtrendsMultiAccountUpdate() {
	  return SeleniumUtil.getVisibileWebElement(d, "fFtrendsMultiAccountUpdate", "FinancialForecast", null);
	}
  public WebElement fFtrendsAvailableBal() {
	  return SeleniumUtil.getVisibileWebElement(d, "fFtrendsAvailableBal", "FinancialForecast", null);
	}
  
  
  
  
	public void addManualAccountLoanFLs() {
		if (linkManualAccountfl3().size() == 1) {

			linkManualAccountfl3().get(0).click();
			linkManualAccountTypeDDfl3().click();

			SeleniumUtil.waitForPageToLoad(2000);
			linkManualAccountTypeLoansfl3().click();
			linkManualAccountNamefl3().sendKeys(PropsUtil.getDataPropertyValue("accountTypeLoans"));

			SeleniumUtil.waitForPageToLoad(1500);
			SeleniumUtil.click(linkManualAccountNext());

			manualAccountCurrentBalancefl3().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
			manualAccountAmountDuefl3().sendKeys(PropsUtil.getDataPropertyValue("AccountAmountDue"));
			manualAccountNextDuedatefl3().sendKeys(DateUtil.getPrevDate());

			linkManualAccountAdd().click();
			SeleniumUtil.waitForPageToLoad(3000);

		} else {
			linkManualAccount().click();
			linkManualAccountTypeDD().click();

			SeleniumUtil.waitForPageToLoad(2000);
			linkManualAccountTypeLoans().click();
			linkManualAccountName().sendKeys(PropsUtil.getDataPropertyValue("accountTypeLoans"));
			SeleniumUtil.waitForPageToLoad(1500);
			SeleniumUtil.click(MRBugs_Loc.linkManualAccountNext());

			manualAccountCurrentBalance().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
			manualAccountAmountDue().sendKeys(PropsUtil.getDataPropertyValue("AccountAmountDue"));
			manualAccountNextDuedate().sendKeys(DateUtil.getPrevDate());

			linkManualAccountAdd().click();
			SeleniumUtil.waitForPageToLoad(3000);

			SeleniumUtil.waitForElementVisible(d, "addAccountSucess", "LinkAnAccount", null);
		}

	}
	
	public void addManualAccountMortgageFLs() {
		if (linkManualAccountfl3().size() == 1) {

			linkManualAccountfl3().get(0).click();
			linkManualAccountTypeDDfl3().click();

			SeleniumUtil.waitForPageToLoad(2000);
			linkManualAccountTypeMortagefl3().click();
			linkManualAccountNamefl3().sendKeys(PropsUtil.getDataPropertyValue("accountTypeMortgage"));

			SeleniumUtil.waitForPageToLoad(1500);
			SeleniumUtil.click(linkManualAccountNext());

			manualAccountCurrentBalancefl3().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
			manualAccountAmountDuefl3().sendKeys(PropsUtil.getDataPropertyValue("AccountAmountDue"));
			manualAccountNextDuedatefl3().sendKeys(DateUtil.getPrevDate());

			linkManualAccountAdd().click();
			SeleniumUtil.waitForPageToLoad(3000);

		} else {
			linkManualAccount().click();
			MRBugs_Loc.linkManualAccountTypeDD().click();
			SeleniumUtil.waitForPageToLoad(2000);
			MRBugs_Loc.linkManualAccountTypeMortage().click();
			MRBugs_Loc.linkManualAccountName().sendKeys(PropsUtil.getDataPropertyValue("accountTypeMortgage"));
			SeleniumUtil.waitForPageToLoad(1500);
			SeleniumUtil.click(MRBugs_Loc.linkManualAccountNext());
			MRBugs_Loc.manualAccountCurrentBalance().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
			MRBugs_Loc.manualAccountAmountDue().sendKeys(PropsUtil.getDataPropertyValue("AccountAmountDue"));
			MRBugs_Loc.manualAccountNextDuedate().sendKeys(DateUtil.getPrevDate());

			MRBugs_Loc.linkManualAccountAdd().click();
			SeleniumUtil.waitForPageToLoad(3000);
		}


	}
	public WebElement fFtuApplynGo() {
		  return SeleniumUtil.getVisibileWebElement(d, "fFtuApplynGo", "FinancialForecast", null);
		}
	public WebElement alert_Goals_Tab() {
		  return SeleniumUtil.getVisibileWebElement(d, "alert_Goals_Tab", "AlertSetting", null);
		}
	public WebElement alert_Create_A_Goal() {
		  return SeleniumUtil.getVisibileWebElement(d, "alert_Create_A_Goal", "AlertSetting", null);
		}
	public WebElement alert_Back_To_Alerts() {
		  return SeleniumUtil.getVisibileWebElement(d, "alert_Back_To_Alerts", "AlertSetting", null);
		}
	
	

}
