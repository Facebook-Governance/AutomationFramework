/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author 
 ******************************************************************************/

package com.omni.pfm.DailyBugVerification;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.MRBugs.MRBugs_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.SustMRBugs.StabNPRCatchupMay2018;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DailyBugVerification_MarchGA extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(StabNPRCatchupMay2018.class);
	AccountAddition accAddition = new AccountAddition();
	public ManualAccount_Settings_Loc settingsLoc;
	MRBugs_Loc MRBugs_Loc;
	NetWorth_Loc netWorth;
	CashFlow_LandingPage_Loc LandingPage;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_Landing_Page_Loc Budget_Landingpage;
	Budget_Select_Accounts_Loc select_accounts;
	Budget_No_Account_Loc no_accounts;
	Add_Manual_Transaction_Loc add_manual_transaction;
	public AccountGroup_Settings_Loc groupSettingLoc;


	@BeforeClass()
	public void init() {
		doInitialization("Manage Categories");
		settingsLoc = new ManualAccount_Settings_Loc(d);
		MRBugs_Loc = new MRBugs_Loc(d);
		LandingPage = new CashFlow_LandingPage_Loc(d);
		Budget_FlexibleSpending = new Budget_Flexible_Spending_Loc(d);
		Budget_Landingpage = new Budget_Landing_Page_Loc(d);
		select_accounts = new Budget_Select_Accounts_Loc(d);
		no_accounts = new Budget_No_Account_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		groupSettingLoc = new AccountGroup_Settings_Loc(d);
		netWorth=new NetWorth_Loc(d);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/*
	 * PFM 3.0 - CashFlow FinApp - Account Dropdown is not getting updated to
	 * default, once the page is refreshed
	 */

	@Test(description = "AT-95488:1096012", enabled = true, priority = 1)
	public void accDropdownSetToDefault() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.createGroup());
		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("ACCGroup2"));
		SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(MRBugs_Loc.getAccountOptionChckBox_01());
		SeleniumUtil.click(groupSettingLoc.createOrUpdateGroup());
		logger.info("************************Group Created***********************");
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		try {
			if (MRBugs_Loc.netWorthContinue().isDisplayed()) {
				SeleniumUtil.click(MRBugs_Loc.netWorthContinue());
				SeleniumUtil.click(MRBugs_Loc.goToCashFlow());
			}
		} catch (Exception e) {
			logger.info("Continuing the script validation");
		}
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		SeleniumUtil.click(MRBugs_Loc.cashflow_DropDown());
		SeleniumUtil.click(MRBugs_Loc.selectGroup());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("CashFlow", d);
		String expectedText = PropsUtil.getDataPropertyValue("cashflow_DropDownText");
		String actualText = MRBugs_Loc.cashflow_DropDown().getText();
		Assert.assertEquals(expectedText, actualText, "Account dropdown is not set to default");
		logger.info("Account dropdown is set to default after refresh");

	}

	/*
	 * [MS_PFM3.0_UI]: "print" icon is disappearing from Transaction FinApp
	 * after closing the Create rule floater or creating the rule when User
	 * navigates back to Cash Flow and again to Transaction FinApp
	 */

	@Test(description = "AT-95486:1015926", enabled = true, priority = 2)
	public void printIconTransactionFinApp() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		try {
			if (MRBugs_Loc.netWorthContinue().isDisplayed()) {
				SeleniumUtil.click(MRBugs_Loc.netWorthContinue());
				SeleniumUtil.click(MRBugs_Loc.goToCashFlow());
			}
		} catch (Exception e) {
			logger.info("Continuing the script validation");
		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.CashInflowTable());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.FirstTransaction());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.categoryDrpdown());
		SeleniumUtil.click(MRBugs_Loc.txnCategory());
		SeleniumUtil.click(MRBugs_Loc.createRulee());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.closeRulePopUp());
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.CashInflowTable());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.morebutton());
		boolean isPresent = MRBugs_Loc.printbtn().isDisplayed();
		Assert.assertTrue(isPresent,
				"Print icon is disappearing from Transaction FinApp after closing the create rule floater");
		logger.info("Print icon is visible from Transaction FinApp after closing the create rule floater");

	}

	/*
	 * [MS_PFM3.0_UI][Budget]: When there is single Budget which has no relevant
	 * accounts in the group, clicking on create Budget link in Step1 account
	 * group down is blank, unable to open it and earlier accounts which was
	 * part of My Budget group is shown
	 */

	@Test(description = "AT-95487:1090799", enabled = true, priority = 3)
	public void createBudget() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		PageParser.forceNavigate("Budget", d);
		// Create Budget page
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		Assert.assertTrue(MRBugs_Loc.BudgetSettings().isDisplayed(), "Budget is not created");
		logger.info("Budget is created successfully");
	}
	/*
	 * PFM3.0-current balance and minimum due radio buttons are not working on
	 * first click
	 */

	@Test(description = "AT-95510:1029150", enabled = true, priority = 4)
	public void currentBalAndMinimumRadiobtn() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		PageParser.forceNavigate("FinancialForecast", d);
		try {
			if (MRBugs_Loc.StartReview().isDisplayed()) {
				SeleniumUtil.click(MRBugs_Loc.StartReview());
			}
		} catch (Exception e) {
			logger.info("Continuing the validation");
		}
		SeleniumUtil.click(MRBugs_Loc.ffNextBtn());
		SeleniumUtil.click(MRBugs_Loc.ffNextBtn());
		String expectedTxt = PropsUtil.getDataPropertyValue("ffRadioBtnClassTxt");
		SeleniumUtil.click(MRBugs_Loc.ffcurrentBal());
		String actualTxt = MRBugs_Loc.ffcurrentBal().getAttribute("class");
		Assert.assertTrue(actualTxt.contains(expectedTxt), "Current Bal radio button is not selected in first click");
		logger.info("Current Bal radio button is selected in first click");
		SeleniumUtil.click(MRBugs_Loc.ffMinimumDue());
		actualTxt = MRBugs_Loc.ffMinimumDue().getAttribute("class");
		Assert.assertTrue(actualTxt.contains(expectedTxt), "Minimum Due radio button is not selected in first click");
		logger.info("Minimum Due radio button is selected in first click");
	}

	/*
	 * PFM3.0-Dashboard page is getting stuck if we click on link account from
	 * creditcard widget
	 */

	@Test(description = "AT-95491:1099158", enabled = true, priority = 5)
	public void verifyAccaddCCWidget() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("netWorthDagBankUN"),
				PropsUtil.getDataPropertyValue("netWorthDagBankPassword"));
		PageParser.forceNavigate("Dashboard", d);
		SeleniumUtil.waitForPageToLoad(10000);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.click(MRBugs_Loc.creditCardwidLinkacc());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(Budget_Landingpage.linkAccountText().isDisplayed(), "Fastlink Floater is opened");
		//SeleniumUtil.click(MRBugs_Loc.fastLinkCloseIcon());
		logger.info("FastLink Floater is Opening after clicking on CreditCard Widget");
	}

	/*
	 * PFM 3.0 - Dashboard - Post selecting the Asset Class in dropdown finapp
	 * is not getting refreshed in IH FinApp
	 */

	@Test(description = "AT-95627:1092877", enabled = true, priority = 6)
	public void finAppIHRefreshAssetClass() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.click(MRBugs_Loc.investnetWidgetlink());
		SeleniumUtil.click(MRBugs_Loc.investnetSectorSelect());
		SeleniumUtil.click(MRBugs_Loc.investnetwidgetNavigate());
		try {
			if (MRBugs_Loc.netWorthContinue().isDisplayed()) {
				SeleniumUtil.click(MRBugs_Loc.netWorthContinue());
				SeleniumUtil.click(MRBugs_Loc.goToMyInvestments());
			}
		} catch (Exception e) {
			logger.info("Continuing the script validation");
		}
		Assert.assertTrue(MRBugs_Loc.goToMyInvestmentsTxt().isDisplayed(),
				"Investment sector is not selected in IH FinApp");
		logger.info("Investment sector is selected in IH FinApp");
		SeleniumUtil.click(MRBugs_Loc.goToMyInvestmentsTxt());
		SeleniumUtil.click(MRBugs_Loc.selectAssetClassdrpdown());
		Assert.assertTrue(MRBugs_Loc.assetClassTxt().isDisplayed(),
				"FinApp is not getting refreshed post selecting the Asset class from dropdown");
		logger.info("FinApp is getting refreshed post selecting the Asset class from dropdown");
	}

	/*
	 * PFM 3.0 - SFC - There is no 'Back to Dashboard' link when the user clicks
	 * on the 'Start Review' button
	 */

	@Test(description = "AT-96140:1099212", enabled = true, priority = 7)
	public void backToDashboardLink() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		SeleniumUtil.click(MRBugs_Loc.startReviewD());
		SeleniumUtil.click(MRBugs_Loc.ffstartReview());
		Assert.assertTrue(MRBugs_Loc.BackToDashboardText().isDisplayed(), "Back To dashboard link is not present");
		logger.info("Back to Dashboard link is present");
	}

	/*
	 * PFM3.0-Adding nick name for Miles, removing account holder name and
	 * showing up nick name
	 */

	@Test(description = "AT-96138:1095942", enabled = true, priority = 8)
	public void nickNameForRewards() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		PageParser.forceNavigate("AccountsPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.moreSettingsRoger());
		SeleniumUtil.click(MRBugs_Loc.accSettingsRoger());
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.rogerNickNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("rogerNickName"));
		SeleniumUtil.click(MRBugs_Loc.rogerSaveChanges());
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		logger.info("Roger nickname is updated successfully");
		//Assert.assertTrue(MRBugs_Loc.rogerHeaderName().isDisplayed(),"Roger Header name is present after updating the nickname");
		String expectedTxt = PropsUtil.getDataPropertyValue("rogerNickName");
		String actualTxt = MRBugs_Loc.rogerNickName().getText();
		Assert.assertEquals(expectedTxt, actualTxt, "NickName is not updated for Roger under Accounts page");
		logger.info("NickName is updated successfully for roger under Account page");
	}

	/*
	 * PFM 3.0 - SFC - I trends page after adding Future events through Add to
	 * Calendar,Toast message is not reflecting
	 */

	@Test(description = "AT-96139:1099200", enabled = true, priority = 9)
	public void addToCalendarToastMessage() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"));
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.click(MRBugs_Loc.ffstartReview());
		SeleniumUtil.click(MRBugs_Loc.ffNextBtn());
		SeleniumUtil.click(MRBugs_Loc.ffNextBtn());
		SeleniumUtil.click(MRBugs_Loc.ffNextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.FFDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(MRBugs_Loc.FFDropDownCredit());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.ffpendingTxn());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(MRBugs_Loc.ffAddToCalendar());
		SeleniumUtil.click(MRBugs_Loc.ffAddTxnBtn());
		String expectedTxt = PropsUtil.getDataPropertyValue("toastMessageTxt");
		String actualTxt = MRBugs_Loc.ffToastMsg().getText();
		Assert.assertEquals(expectedTxt, actualTxt, "Toast message is not displayed for event added");
		logger.info("Toast message is displayed successfully for event added");

	}

	/*
	 * PFM 3.0 - Accounts Settings - Tags are not getting saved in the mentioned
	 * scenario
	 */
	@Test(description = "AT-93913:1076335", enabled = true,priority =10)
	public void TagsNotGettingSaved() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Adding Dag Site");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword")));
		SeleniumUtil.waitForPageToLoad(8000);
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.FirstSetting());
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.TagName().clear();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.TagName().sendKeys(PropsUtil.getDataPropertyValue("ErrorTag"));
		SeleniumUtil.waitForPageToLoad(2000);
		String ExpectedText = MRBugs_Loc.ErrorsText().getText();
		Assert.assertTrue(ExpectedText.contains(PropsUtil.getDataPropertyValue("TagsErrorsText")));
		Assert.assertTrue(MRBugs_Loc.AddbtnDisablechk().isDisplayed(), "Button is not disabled");
		logger.info("************************Verifies proper error is displayed for Tag and Add Tag button is disbaled************************");

	} 


	/**
		         [PFM 3.0]: Dates on cash flow finapp comes twice if the data point start and end date is same for a particular time stamp.
	 */
	@Test(description = "AT-95062,AT-95059:1092914,1009394", enabled = true,priority =11)
	public void DatesComesTwice() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),PropsUtil.getDataPropertyValue("dagSitePassword")));
		PageParser.forceNavigate("CashFlow", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(LandingPage.ContinueBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(LandingPage.GoToMyCashFlowBtn());
		SeleniumUtil.waitForPageToLoad();      
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		MRBugs_Loc.durationMonth().click();
		SeleniumUtil.waitForPageToLoad(2000);
		netWorth.selectAndGetValueFromTimeFilter(8);
		MRBugs_Loc.StartDateInput().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		String firstSaturday=DateUtil.getWeekDate(1, 7,PropsUtil.getDataPropertyValue("DateFormat"));
		String lastSunday=DateUtil.getWeekDate(4, 1,PropsUtil.getDataPropertyValue("DateFormat"));
		MRBugs_Loc.StartDateInput().sendKeys(firstSaturday);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.EndDateInput().click();
		MRBugs_Loc.EndDateInput().sendKeys(lastSunday);
		SeleniumUtil.waitForPageToLoad();      
		MRBugs_Loc.StartDateInput().click();
		netWorth.clickUpdateButton();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		List<WebElement> CashFlowDataPointsXAxis =MRBugs_Loc.monthXaxis();
		String firstSaturdayActual=CashFlowDataPointsXAxis.get(0).getText();
		String lastSundayActual=CashFlowDataPointsXAxis.get(CashFlowDataPointsXAxis.size()-1).getText();
		String firstSaturdayExpected= DateUtil.convertDateFormat(PropsUtil.getDataPropertyValue("DateFormat"), PropsUtil.getDataPropertyValue("dateformatMMdd"), firstSaturday);
		String lastSundayExpected= DateUtil.convertDateFormat(PropsUtil.getDataPropertyValue("DateFormat"), PropsUtil.getDataPropertyValue("dateformatMMdd"), lastSunday);

		Assert.assertTrue(firstSaturdayActual.equalsIgnoreCase(firstSaturdayExpected.toUpperCase()), "");
		Assert.assertTrue(lastSundayActual.equalsIgnoreCase(lastSundayExpected.toUpperCase()), "");
		logger.info("Dates are displayed correctly");
	}

	/**
    [PFM 3.0]:Transaction Categories - Elepcified Text is not displayed completly on mouse hover too
	 */

	@Test(description = "AT-95060:1080107", enabled = true, priority =12)
	public void txnCategoriesElapcifiedTxt() throws Exception{
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Adding Dag Site");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword")));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(MRBugs_Loc.AutomotiveExpenseCategory());
		MRBugs_Loc.InputHLC().clear();
		SeleniumUtil.sendKeys(MRBugs_Loc.InputHLC(), PropsUtil.getDataPropertyValue("AutomotiveExpensesInput"));
		SeleniumUtil.click(MRBugs_Loc.SaveCategory());
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.allCategories());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.sendKeys(MRBugs_Loc.searchCategories(), PropsUtil.getDataPropertyValue("SearchCategories"));
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("Fetching the actual title for the category");
		String actualTitle = MRBugs_Loc.filterCategories().getAttribute("title");
		String expectedTitle = PropsUtil.getDataPropertyValue("AutomotiveExpensesInput");
		Assert.assertEquals(actualTitle, expectedTitle,"Text is not displayed correctly");
		logger.info("******************Elepcified text is displayed completely******************");

	}


}
