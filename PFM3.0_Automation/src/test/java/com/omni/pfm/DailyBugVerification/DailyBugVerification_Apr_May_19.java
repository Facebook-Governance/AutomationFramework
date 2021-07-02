/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.

 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author Abhinandan
 ******************************************************************************/

package com.omni.pfm.DailyBugVerification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DailyBugVerification_Apr_May_19 extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(StabNPRCatchupMay2018.class);
	AccountAddition accAddition = new AccountAddition();
	public ManualAccount_Settings_Loc settingsLoc;
	MRBugs_Loc MRBugs_Loc;

	@BeforeClass()
	public void init() {
		doInitialization("Manage Categories");
		settingsLoc = new ManualAccount_Settings_Loc(d);
		MRBugs_Loc = new MRBugs_Loc(d);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/*
	 * Budget - Two drop downs are getting opened simultaneously
	 */
	@Test(description = "AT-105719:1216205", enabled = true, priority = 1)
	public void budgetTwoDropDowns() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));

		// Navigates to Budget Page
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		// Create Budget page
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());

		// Bug FLow
		SeleniumUtil.click(MRBugs_Loc.budgetMoreOptions());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.spendingMenu().click();
		SeleniumUtil.waitForPageToLoad(2000);

		// Validation
		Assert.assertFalse(MRBugs_Loc.moreOptionMenuHide().isDisplayed(),
				"FAIL: Two dropdowns are getting opened simultaneously");
		logger.info("PASS: Two dropdowns are not getting opened simultaneously");

	}

	/*
	 * Expense - Expense page is not getting refresh when user selects expense
	 * option in the dropdown
	 */
	@Test(description = "AT-105720:1217943", enabled = true, priority = 2)
	public void expenseIncomePageRefresh() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		PageParser.forceNavigate("Expense", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();

		Assert.assertTrue(MRBugs_Loc.eATrasactionHeader().isDisplayed(),
				"Failed : Expense Analysis page is not loaded correctly");

		MRBugs_Loc.iAiEDropDown().click();
		MRBugs_Loc.IncomeAnalysis().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		// Validation
		Assert.assertTrue(MRBugs_Loc.iATrasactionHeader().isDisplayed(),
				"Failed : Income Analysis page is not loaded correctly");
		logger.info("PASS: Expense page is getting refresh when user selects expense option in the dropdown");
	}

	/*
	 * [MS_PFM3.0_UI][MAR_GA]: Even though the link says back to Income
	 * Analysis, User will land on Expense Analysis page on navigating back from
	 * Account groups FinApp
	 */
	@Test(description = "AT-:1219791", enabled = true, priority = 3)
	public void backToIncomeAnalysis() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		PageParser.forceNavigate("AccountsPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		MRBugs_Loc.Account_SideBar_AccGrp().click();
		SeleniumUtil.click(MRBugs_Loc.CreateGroupBtns());
		MRBugs_Loc.groupNameTxtBox().clear();
		SeleniumUtil.click(MRBugs_Loc.groupNameTxtBox());
		MRBugs_Loc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name").trim());
		SeleniumUtil.click(MRBugs_Loc.getAccountOptionChckBox().get(0));

		Assert.assertTrue(MRBugs_Loc.createOrUpdateGroup().isEnabled(), "Create Group button is disabled");
		SeleniumUtil.click(MRBugs_Loc.createOrUpdateGroup());

		PageParser.forceNavigate("Expense", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();
		MRBugs_Loc.iAiEDropDown().click();
		MRBugs_Loc.IncomeAnalysis().click();

		MRBugs_Loc.allIncomceAccounts().click();
		MRBugs_Loc.groupTab().click();
		MRBugs_Loc.manageGroup().click();

		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		MRBugs_Loc.backToIA().click();

		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		// Validation
		Assert.assertTrue(MRBugs_Loc.iADropDownText().isDisplayed(),
				"FAIL : Income Analysis dropdown selection doesn't persisits");
		logger.info("PASS:Income Analysis dropdown selection persisits");

	}

	/*
	 * [MS_PFM3.0_UI][MAR_GA]: Even though message says "-" is allowed, if
	 * transaction contains "-" it is not getting saved
	 * 
	 * [MS_PFM3.0_UI][MAR_GA]: Even though transaction updated message is shown,
	 * transaction description is not getting updated on entering all allowed
	 * special characters and saving it
	 */
	@Test(description = "AT-106998:1221547", enabled = true, priority = 4)
	public void transactionDescSpecialCharacters() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("CashFlow", d);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();

		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.waitForPageToLoad();

		for (int i = 1; i < MRBugs_Loc.cashInFlowDataColmnSize().size(); i++) {

			String[] getXpath = MRBugs_Loc.cashInFlowDataColmn();
			String webElemText = getXpath[1].replaceAll("\\+", Integer.toString(i));
			String amountText = d.findElement(By.xpath(webElemText)).getText().replaceAll("[^a-zA-Z0-9\\s+]", "");

			if (Integer.parseInt(amountText) > 0) {
				SeleniumUtil.waitForPageToLoad(3000);
				d.findElement(By.xpath(webElemText)).click();
				break;
			}

		}

		SeleniumUtil.waitForPageToLoad();

		MRBugs_Loc.transactionPageList().get(1).click();
		MRBugs_Loc.description_AMT().clear();
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("testspecialch"));
		MRBugs_Loc.description_AMT().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1000);

		// Validation
		Assert.assertTrue(d.findElements(By.xpath(MRBugs_Loc.transDescErrorMsg()[1])).isEmpty(),
				"FAIL : Doesn't not allow '-' character for Description field");

		MRBugs_Loc.transactionDetailsSave().click();

		SeleniumUtil.waitForPageToLoad();

		String actualText = MRBugs_Loc.transactionPageList().get(1).getText();

		Assert.assertEquals(actualText, PropsUtil.getDataPropertyValue("testspecialch"),
				"FAIL: User failed to save the description with special characters");

		logger.info("PASS:User successfully to saved the description with special characters");

	}

	/*
	 * [PFM 3.0]: User selected accounts are not showing in financial forecast
	 * finapp.
	 */
	@Test(description = "AT-107415:1217310", enabled = true, priority = 5)
	public void finalcialForecastAccountsDisplay() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("DagffUsername"),
				PropsUtil.getDataPropertyValue("DagffPwd"), PropsUtil.getDataPropertyValue("addAccountSource"));
		
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad();

		MRBugs_Loc.fFStartReview().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		HashMap<String, String> mp1 = new HashMap<String, String>();
		List<String> checkedCB = new ArrayList<String>();
		List<String> debitFromCheckedCB = new ArrayList<String>();

		List<WebElement> li1 = MRBugs_Loc.fFFTUCashAccounts();
		li1.get(1).click();

		for (int i = 0; i < li1.size(); i++) {

			mp1.put(li1.get(i).getAttribute(PropsUtil.getDataPropertyValue("dataType1")).toLowerCase(),
					li1.get(i).getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")).toLowerCase());

		}

		List<WebElement> checkedCBWebElems = MRBugs_Loc.fFFTUCheckedAccounts();

		for (int j = 0; j < checkedCBWebElems.size(); j++) {

			checkedCB.add(checkedCBWebElems.get(j).getAttribute("aria-label"));

		}
		Collections.sort(checkedCB);

		MRBugs_Loc.ffNextBtn().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.ffNextBtn().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.ffNextBtn().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.waitForPageToLoad();

		String t1 = MRBugs_Loc.fFTrendsFilterAccountText().getText().trim().replace("\n", "").replace(" ", "");

		MRBugs_Loc.fFTrendsFilterAccountDd().click();

		HashMap<String, String> mp2 = new HashMap<String, String>();

		List<WebElement> li2 = MRBugs_Loc.fFTrendsFilterAccountList();
		List<WebElement> li3 = MRBugs_Loc.fFTrendsFilterAccountListName();

		for (int i = 0; i < li2.size(); i++) {

			mp2.put(li3.get(i).getText().toLowerCase(),
					li2.get(i).getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")).toLowerCase());

		}

		String t2 = MRBugs_Loc.fFTrendsFilterAccountListNameText()
				.getAttribute(PropsUtil.getDataPropertyValue("dataType1")).replace(" ", "");

		SeleniumUtil.click(MRBugs_Loc.fFTrendsFilterAccountDdCancel());
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(MRBugs_Loc.fFTrendsFirstTransaction());

		String t3 = MRBugs_Loc.fFTrendsFirstTransactionDebitFromAcc().getText();
		String[] t4 = t3.split("-");

		MRBugs_Loc.addTransactionHeaderFun_MT().click();
		MRBugs_Loc.transactionDebitedFrom().click();

		SeleniumUtil.waitForPageToLoad(1500);

		String[] debitFromValues = MRBugs_Loc.addTransDebitedFromValues();

		int size = d.findElements(By.xpath(debitFromValues[1])).size();

		Assert.assertTrue(size == 2, "Displays only Cash account");

		List<WebElement> accValues = d.findElements(By.xpath(debitFromValues[1]));

		for (int k = 0; k < accValues.size(); k++) {

			debitFromCheckedCB.add(accValues.get(k).getText().split("-")[0]);

		}
		Collections.sort(debitFromCheckedCB);

		Assert.assertEquals(checkedCB, debitFromCheckedCB, "");

		Assert.assertEquals(mp1, mp2, "FAIL : Account name doesn't match");

		Assert.assertTrue(t1.equalsIgnoreCase(t2) && t1.contains(t4[2]), "FAIL : Account name doesn't match");

		logger.info("PASS:User selected accounts are showing in financial forecast");
	}

	//
	/*
	 * [PFM 3.0]Account groups - Displays blank page on closing the 'Create
	 * Account Groups' dialog box.
	 */
	@Test(description = "AT-108124:1222452", enabled = true, priority = 6)
	public void createGroupClosePopup() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("netWorthDagBankUN"),
				PropsUtil.getDataPropertyValue("netWorthDagBankPassword"),
				PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		PageParser.forceNavigate("AccountGroups", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		MRBugs_Loc.createGroupButtonTwoAGS().click();
		SeleniumUtil.waitForPageToLoad(3000);
		MRBugs_Loc.createGroupClosePopup().click();
		SeleniumUtil.waitForPageToLoad(2000);
		// Handle pop-ups
		Assert.assertTrue(d.findElements(By.xpath(MRBugs_Loc.groupCreateModalWindow()[1])).size() == 0,
				"FAIL :User is not able to close the Create Group pop up Successfully");
		logger.info("PASS:User is able to close the Create Group pop up Successfully");
	}

	/**
	 * [PFM 3.0]Account Settings - Not able to edit the credentials , page keeps
	 * loading.
	 */

	@Test(description = "AT-108125:1223098", enabled = true, priority = 7)
	public void editcredspage() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.EditCredentials().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.PasswordText1().sendKeys(PropsUtil.getDataPropertyValue("dagSitePassword"));
		MRBugs_Loc.ReEnterPasswordText1().sendKeys(PropsUtil.getDataPropertyValue("dagSitePassword"));
		MRBugs_Loc.UpdatedButton().click();

		String[] accntSuccess = MRBugs_Loc.successmsgeditcreds();
		WebElement successmsg = SeleniumUtil.waitForElementVisible(d, By.xpath(accntSuccess[1]), 30);

		Assert.assertTrue(successmsg.isDisplayed(), "Edit Credentails is not working as expected.");
		logger.info("******************Edit Credentails working as expected******************");

	}
	
	/*
	 * PFM 3.0 - Budget /Transactions/EA/IA - Edit transaction pop up/screen is not loading in the mentioned FinApps.
	 * 
	 */

	@Test(description = "AT-108135:1229321", enabled = true, priority = 8)
	public void budgetEditTransaction() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);


		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

		// Create budget
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

		MRBugs_Loc.firstbudgtcate().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		MRBugs_Loc.firstbudgtcatetran().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		Assert.assertTrue(MRBugs_Loc.edittranvalidation().isDisplayed(), "Edit Tranaction popup is blank.");
		logger.info("Edit Transaction popup is loading succefully.");
	}
	
	
	/*
	 * PFM3.0-"Show account in account summary" :On turning off, not reflecting in accounts finapp
	 * 
	 */

	@Test(description = "AT-108326:1205675", enabled = true, priority = 9)
	public void showAccount() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("netWorthDagBankUN"),
				PropsUtil.getDataPropertyValue("netWorthDagBankPassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
			
		PageParser.forceNavigate("AccountSettings", d);
		
		int b1=MRBugs_Loc.accountSettSize().size();
		
		String a1=MRBugs_Loc.accountSettingsFirstAccSummary().getText();
		
		MRBugs_Loc.accountSettingsIcon().click();
		MRBugs_Loc.accountSettingsHideSummary().click();
		MRBugs_Loc.accountSettingsSaveChanges().click();
		
		PageParser.forceNavigate("AccountsPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		
		int b2=MRBugs_Loc.accountSettSize().size();
		
        Assert.assertTrue(b1>b2, "FAIL:Account Summary is still displayed");
        Assert.assertTrue(d.findElements(By.xpath(MRBugs_Loc.accountsSummaryHideAccount()[1].replaceAll("\\+", a1))).size()==0, "");
        logger.info("PASS : Show account in account summary :On turning off, reflecting in accounts finapp");
		
	}
	
	
	/*
	 * User not able to navigate to Expense analysis finapp from Income analysis
	 */
	@Test(description = "AT-110853:1217321", enabled = true, priority = 10)
	public void navigateFromIncometoExpense() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		PageParser.forceNavigate("Expense", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();


		MRBugs_Loc.iAiEDropDown().click();
		MRBugs_Loc.IncomeAnalysis().click();
		
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.iAiEDropDown().click();
		MRBugs_Loc.ExpenseAnalyseAnalysisDd().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		// Validation
		Assert.assertTrue(MRBugs_Loc.ExpenseAnalyseAnalysisChartNote().isDisplayed(),"Failed : Expense Analysis page is not loaded correctly");
		logger.info("PASS: Expense Analysis page is loaded correctly");
	}

	
	
}
