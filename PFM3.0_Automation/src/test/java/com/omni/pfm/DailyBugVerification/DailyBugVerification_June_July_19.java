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

public class DailyBugVerification_June_July_19 extends TestBase {
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
	 * [PFM 3.0]Transactions - Special characters allowed are different while
	 * adding and updating the transaction.
	 */
	@Test(description = "AT-108540:1233765", enabled = true, priority = 1)
	public void specialCharAddandEditTrans() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		
		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));

		// Add Manual Transaction
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		MRBugs_Loc.transactionManualAdd().click();
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("transErrorMsgChar"));
		MRBugs_Loc.description_AMT().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.description_AMT().clear();
		String addTransDescErro01 = MRBugs_Loc.addTransDescError().getText();
		String descText = "abc" + Integer.toString(GenericUtil.getRandomNumber(1000000));
		MRBugs_Loc.description_AMT().sendKeys(descText + PropsUtil.getDataPropertyValue("addTransDescSpecialChar"));
		SeleniumUtil.waitForPageToLoad(2000);
		
		MRBugs_Loc.transactionManualDebitedFrom().click();
		MRBugs_Loc.transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualcategoryDropDown().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionManualcategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionWithDrawal"));
		MRBugs_Loc.transactioncategoryDropdownValue().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitUntilElementInvisible(d, "transactionEventAdd", "Transaction", null);
		SeleniumUtil.waitForPageToLoad();
		
		String[] editManTrans = MRBugs_Loc.editManualtrans();
		d.findElement(By.xpath(editManTrans[1].replace("+", descText))).click();
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("transErrorMsgChar"));
		MRBugs_Loc.description_AMT().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.description_AMT().clear();

		String addTransDescErro02 = MRBugs_Loc.addTransDescError().getText();

		MRBugs_Loc.description_AMT().sendKeys(descText + PropsUtil.getDataPropertyValue("addTransDescSpecialChar"));

		SeleniumUtil.click(MRBugs_Loc.transactionEditSave1());

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.waitUntilElementInvisible(d, "transactionEditSave1", "Transaction", null);

		//Validation
		Assert.assertTrue(MRBugs_Loc.transactionEditSave().size() == 0, "");
		Assert.assertEquals(addTransDescErro01, addTransDescErro02,
				"FAIL: Special characters allowed are different while adding and updating the transaction");

		logger.info("PASS: Special characters allowed are same while adding and updating the transaction");

	}

	/*
	 * [ACS_PFM3_SDG][UAT]: 'Yodlee_Transactions_Check_Number_Save_Failing
	 */
	@Test(description = "AT-108542:1233560", enabled = true, priority = 2)
	public void addTransCheckNumber() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));

		// Add Manual Transaction
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		MRBugs_Loc.transactionManualAdd().click();
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		String descText = "abcdef" + Integer.toString(GenericUtil.getRandomNumber(10000));
		MRBugs_Loc.description_AMT().sendKeys(descText);
		SeleniumUtil.waitForPageToLoad(2000);
		
		MRBugs_Loc.transactionManualDebitedFrom().click();
		MRBugs_Loc.transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualcategoryDropDown().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionManualcategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionWithDrawal"));
		MRBugs_Loc.transactioncategoryDropdownValue().click();
		SeleniumUtil.waitForPageToLoad(1500);

		MRBugs_Loc.transactionEventShowMoreOption().click();

		MRBugs_Loc.check_AMT().sendKeys(PropsUtil.getDataPropertyValue("transCheckNumber01"));

		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitUntilElementInvisible(d, "transactionEventAdd", "Transaction", null);

		String[] editManTrans = MRBugs_Loc.editManualtrans();
		d.findElement(By.xpath(editManTrans[1].replace("+", descText))).click();

		MRBugs_Loc.transactionShowMoreOption().click();

		String checkNo01 = MRBugs_Loc.check_AMT().getAttribute("value");

		Assert.assertEquals(checkNo01, PropsUtil.getDataPropertyValue("transCheckNumber01"), "");

		MRBugs_Loc.check_AMT().clear();

		MRBugs_Loc.check_AMT().sendKeys(PropsUtil.getDataPropertyValue("transCheckNumber02"));

		MRBugs_Loc.transactionEditSave().get(0).click();

		SeleniumUtil.waitForPageToLoad(8000);

		d.findElement(By.xpath(editManTrans[1].replace("+", descText))).click();

		MRBugs_Loc.transactionShowMoreOption().click();

		String checkNo02 = MRBugs_Loc.check_AMT().getAttribute("value");

		//Validation
		Assert.assertEquals(checkNo02, PropsUtil.getDataPropertyValue("transCheckNumber02"), "");

		logger.info("PASS: Special characters allowed are same while adding and updating the transaction");

	}

	/*
	 * [MS_PFM3.0_UI][MAR_GA]: Data points is not getting persisted, monthly
	 * data points is getting changed to weekly data points on choosing current
	 * year and navigating back to Cash flow from Transaction or Account groups
	 * FinApp
	 */
	@Test(description = "AT-108543:1223517", enabled = true, priority = 3)
	public void persistCashFlowMonthlyData() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		PageParser.forceNavigate("CashFlow", d);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();

		SeleniumUtil.waitForPageToLoad(2000);

		MRBugs_Loc.durationMonth().click();
		MRBugs_Loc.durationThisYear().click();
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> CashFlowDataPointsXAxis01 = MRBugs_Loc.monthXaxis();
		List<String> thisYearData01 = new ArrayList<String>();

		System.out.println(CashFlowDataPointsXAxis01.size());

		for (int i = 0; i < CashFlowDataPointsXAxis01.size(); i++) {
			if (i == 0) {
				thisYearData01.add(CashFlowDataPointsXAxis01.get(0).getText().split(" ")[0]);
			} else {
				thisYearData01.add(CashFlowDataPointsXAxis01.get(i).getText());

			}
		}

		List<WebElement> cashFlowDetailsDateRange01 = MRBugs_Loc.cashFlowDetailsDateRange();
		List<String> thisYearData02 = new ArrayList<String>();

		System.out.println(cashFlowDetailsDateRange01.size());
		for (int i = cashFlowDetailsDateRange01.size() - 1; i >= 0; i--) {

			thisYearData02.add(cashFlowDetailsDateRange01.get(i).getText().split(" ")[0]);
		}

		Assert.assertEquals(thisYearData01, thisYearData02);

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
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 10);

		MRBugs_Loc.backToCashFlow().click();

		String selectedFilterValue = MRBugs_Loc.cashFlowTimeFilterSelectedValue().getText();

		List<WebElement> CashFlowDataPointsXAxis02 = MRBugs_Loc.monthXaxis();
		List<String> thisYearData03 = new ArrayList<String>();

		System.out.println(CashFlowDataPointsXAxis02.size());

		for (int i = 0; i < CashFlowDataPointsXAxis02.size(); i++) {
			if (i == 0) {
				thisYearData03.add(CashFlowDataPointsXAxis02.get(0).getText().split(" ")[0]);
			} else {
				thisYearData03.add(CashFlowDataPointsXAxis02.get(i).getText());

			}
		}

		List<WebElement> cashFlowDetailsDateRange03 = MRBugs_Loc.cashFlowDetailsDateRange();
		List<String> thisYearData04 = new ArrayList<String>();

		System.out.println(cashFlowDetailsDateRange03.size());
		for (int i = cashFlowDetailsDateRange03.size() - 1; i >= 0; i--) {

			thisYearData04.add(cashFlowDetailsDateRange03.get(i).getText().split(" ")[0]);
		}

		// Validation
		Assert.assertEquals(selectedFilterValue, PropsUtil.getDataPropertyValue("TransactionThisYearLabel"),
				"FAIL:TIme fileter value changed");
		Assert.assertEquals(thisYearData03, thisYearData04);

		logger.info("PASS: Data persists on navigating from CashFlow to Transaction");

	}

	/*
	 * [ACS_PFM3_SDG][UAT] Categories - Spinner Never Disappears After Saving
	 * Changes
	 */
	@Test(description = "AT-108544:1234889", enabled = true, priority = 4)
	public void categorySaveIndefiniteSpinner() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));

		// Bug flow
		PageParser.forceNavigate("Categories", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);

		MRBugs_Loc.categoryGasolineFuel().click();
		//Validation
		Assert.assertTrue(MRBugs_Loc.SaveCategory().isEnabled(), "FAIL:Save button is not enabled");
		
		MRBugs_Loc.editMLCatInputField_MC().sendKeys(Keys.SPACE);
		MRBugs_Loc.editMLCatInputField_MC().sendKeys(Keys.BACK_SPACE);
		MRBugs_Loc.SaveCategory().click();
		SeleniumUtil.waitForPageToLoad(2000);

		// Validation
		Assert.assertTrue(MRBugs_Loc.SaveCategory().isDisplayed(),"FAIL:USer is able to Save eventhough input is same");

		logger.info("PASS: User is not able to Save if the there is no changes");

	}

	/*
	 * Account types for Manual loan and mortgage accounts showing as "NA"
	 * 
	 */
	@Test(description = "AT-108545:1229648", enabled = true, priority = 5)
	public void manualAccountName() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application***********************");
		// /Add Manual Loan account
		MRBugs_Loc.noDataScreen_Button().click();
		MRBugs_Loc.linkManualAccount().click();
		MRBugs_Loc.linkManualAccountTypeDD().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.linkManualAccountTypeLoans().click();
		MRBugs_Loc.linkManualAccountName().sendKeys(PropsUtil.getDataPropertyValue("accountTypeLoan"));
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(MRBugs_Loc.linkManualAccountNext());
		MRBugs_Loc.manualAccountCurrentBalance().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
		MRBugs_Loc.linkManualAccountDue().sendKeys(PropsUtil.getDataPropertyValue("AccountDueLoan"));
		MRBugs_Loc.linkManualAccountDuedate().sendKeys(DateUtil.getPrevDate());
		MRBugs_Loc.linkManualAccountAdd().click();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitForElementVisible(d, "addAccountSucess", "LinkAnAccount", null);
		
		//Add Manual Mortage account
        MRBugs_Loc.linkAnotherAccount().click();
        MRBugs_Loc.linkManualAccount().click();
		MRBugs_Loc.linkManualAccountTypeDD().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.linkManualAccountTypeMortage().click();
		MRBugs_Loc.linkManualAccountName().sendKeys(PropsUtil.getDataPropertyValue("accountTypeMortgage"));
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(MRBugs_Loc.linkManualAccountNext());
		MRBugs_Loc.manualAccountCurrentBalance().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
		MRBugs_Loc.linkManualAccountDue().sendKeys(PropsUtil.getDataPropertyValue("AccountDueLoan"));
		MRBugs_Loc.linkManualAccountDuedate().sendKeys(DateUtil.getPrevDate());
		MRBugs_Loc.linkManualAccountAdd().click();
		SeleniumUtil.waitForPageToLoad(3000);	
	
		SeleniumUtil.waitForElementVisible(d, "addAccountSucess", "LinkAnAccount", null);
       	
		PageParser.forceNavigate("AccountsPage", d);
		
		String accountTypename =MRBugs_Loc.accounts_Type_Acc().getText();	
		
		//Validation
		Assert.assertEquals(accountTypename, PropsUtil.getDataPropertyValue("accountTypeLoan"),"FAIL: Does'nt display accounts name for manual loan.Mortage account");
		
		logger.info("PASS: Displays accounts name for manual loan.Mortage account");

	}
	
	
	/*
	 *[MS_PFM3.0_UI][MAR_GA]: Net Transfers amount value is getting added twice/subtracted twice to derive Net Cash Flow value in chart section, table section shows correct Total Net Cash Flow value
	 */
	@Test(description = "AT-109639:1238760", enabled = true, priority = 6)
	public void netTransferAmountTwice() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));

		// Bug flow
		PageParser.forceNavigate("CashFlow", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		
		MRBugs_Loc.continuePopup();
		
		String expectedNetCashFLow=MRBugs_Loc.netcashflowTotal().getText().replaceAll("\\$", "").trim().replaceAll(",", "");
		String inFLow=MRBugs_Loc.inflowTotal().getText().replaceAll("\\$", "").trim().replaceAll(",", "");
		String outFLow=MRBugs_Loc.outflowTotal().getText().replaceAll("\\$", "").trim().replaceAll(",", "");
		
		List<WebElement> li1=MRBugs_Loc.totalnetTransfer();
		double totalnetTrans=0.0;

		for(int i=0;i<li1.size();i++){						
			String amt=li1.get(i).getText().replaceAll("\\$", "").trim().replaceAll(",", "");		
			Double ammt=Double.parseDouble(amt);		
			totalnetTrans=totalnetTrans+ammt;		
		}
		
		Double actualNetCashFLow=Double.parseDouble(inFLow)-Double.parseDouble(outFLow)+totalnetTrans;
		//validation
		Assert.assertTrue(actualNetCashFLow.equals(Double.parseDouble(expectedNetCashFLow)), "Net Transfers amount value is getting added twice");
			
		logger.info("PASS: Net Transfers amount value is not getting added twice");
	}

	
	/*
	 * PFM3.0-On editing manual accounts(assests and liabilities),getting duplicated in accounts finapp
	 */
	@Test(description = "AT-109640:1238686", enabled = true, priority = 7)
	public void editManualDuplicate() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		// Add Manual Liabilities account
		MRBugs_Loc.noDataScreen_Button().click();
		MRBugs_Loc.linkManualAccount().click();
		MRBugs_Loc.linkManualAccountTypeDD().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.linkManualAccountTypeLiabilities().click();
		MRBugs_Loc.linkManualAccountName().sendKeys(PropsUtil.getDataPropertyValue("accountTypeLiabilities"));
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(MRBugs_Loc.linkManualAccountNext());
		MRBugs_Loc.manualAccountCurrentBalance().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
		MRBugs_Loc.linkManualAccountAdd().click();
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.waitForElementVisible(d, "addAccountSucess", "LinkAnAccount", null);
		
		// Add Manual Assests account
        MRBugs_Loc.linkAnotherAccount().click();
        MRBugs_Loc.linkManualAccount().click();
		MRBugs_Loc.linkManualAccountTypeDD().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.linkManualAccountTypeAssets().click();
		MRBugs_Loc.linkManualAccountName().sendKeys(PropsUtil.getDataPropertyValue("accountTypeAssests"));
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(MRBugs_Loc.linkManualAccountNext());
		MRBugs_Loc.manualAccountCurrentBalance().sendKeys(PropsUtil.getDataPropertyValue("AccountBalanceLoan"));
		MRBugs_Loc.linkManualAccountAdd().click();
		SeleniumUtil.waitForPageToLoad(3000);	
		SeleniumUtil.waitForElementVisible(d, "addAccountSucess", "LinkAnAccount", null);
		
		//Edit and update Liabilities account
		PageParser.forceNavigate("AccountsPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		
		//Validation
		Assert.assertTrue(MRBugs_Loc.accountLists().size()==2, "FAIL: Duplicate account created");
		
		MRBugs_Loc.otherLiablMoreSetting().click();
		MRBugs_Loc.otherLiablAccMoreSetting().click();
		
		MRBugs_Loc.ManualAcntNameTxtBox().sendKeys("Update");
		MRBugs_Loc.saveBtn_AccSettingsPopUp().click();
		SeleniumUtil.waitForPageToLoad(3000);
		
		//Validation
		Assert.assertTrue(MRBugs_Loc.accountLists().size()==2, "FAIL: Duplicate account created");
		
		//Edit and update Assests account
		PageParser.forceNavigate("AccountsPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 30);
		
		MRBugs_Loc.otherAssetsMoreSetting().click();
		MRBugs_Loc.otherAssetsAccMoreSetting().click();
		
		MRBugs_Loc.ManualAcntNameTxtBox().sendKeys("Update");
		MRBugs_Loc.saveBtn_AccSettingsPopUp().click();
		SeleniumUtil.waitForPageToLoad(3000);
		
		//Validation
		Assert.assertTrue(MRBugs_Loc.accountLists().size()==2, "FAIL: Duplicate account created");

		logger.info("PASS: Duplicate manual accoutn is not created on updating");
	}
	
	/*
	 * [MS_PFM3.0_UI][MAR_GA]: Transactions is not loading clicking on uncategorized link and even back to categorization rules is not working 
	 */
	
	@Test(description = "AT-109641:1238196", enabled = true, priority = 8)
	public void uncategorizedlinkcheck() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		logger.info("************************Logged in to application***********************");
		// Add account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));

		// Click on uncategorized link
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.uncaterulelink().click();

		//Validation
		Assert.assertTrue(MRBugs_Loc.verifytranloading().isDisplayed(), "Transaction finapp not loading");
		Assert.assertTrue(MRBugs_Loc.backtoCatRules().isDisplayed(), "Back to Categorization Rules");

		logger.info("PASS: Uncategorized transactions are loading on transaction finapp.");

	}
	
	
	/*
	 * PFM 3.0 - Dashboard - Back to Dashboard link is not displayed in the mentioned scenario
	 */
	@Test(description = "AT-110632:1233609", enabled = true, priority = 9)
	public void creditcardErrorBackToDashBoard() throws Exception {
		
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Bug flow
		PageParser.forceNavigate("AccountSettings", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.editCreds().click();
		
		//Condition to check Fl2/FL3 and edit the password
		if (MRBugs_Loc.PasswordText1fl3().size() == 1) {

			MRBugs_Loc.PasswordText1fl3().get(0).click();
			MRBugs_Loc.PasswordText1fl3().get(0).sendKeys(PropsUtil.getDataPropertyValue("errorPwd"));
			MRBugs_Loc.PasswordText1fl3().get(0).sendKeys(Keys.TAB);
			MRBugs_Loc.ReEnterPasswordText1fl3().sendKeys(PropsUtil.getDataPropertyValue("errorPwd"));
			MRBugs_Loc.UpdatedButton().click();
			SeleniumUtil.waitForElementVisible(d, "errorText", "AccountSettings", null);

		} else {
			MRBugs_Loc.PasswordText1().click();
			MRBugs_Loc.PasswordText1().sendKeys(PropsUtil.getDataPropertyValue("errorPwd"));
			MRBugs_Loc.PasswordText1().sendKeys(Keys.TAB);
			MRBugs_Loc.ReEnterPasswordText1().sendKeys(PropsUtil.getDataPropertyValue("errorPwd"));
			MRBugs_Loc.UpdatedButton().click();
			SeleniumUtil.waitForElementVisible(d, "errorTextfl3", "AccountSettings", null);
		}

		MRBugs_Loc.dagCloseEditCreds().click();
		PageParser.forceNavigate("DashboardPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		// Validation
		MRBugs_Loc.dashBcreditError().click();
		SeleniumUtil.waitForPageToLoad();

		//Validation
		Assert.assertTrue(MRBugs_Loc.backtoDashBFromcreditDB().isDisplayed(),"FAIL :Back to DashBoard link not diaplyed");
		logger.info("PASS:Back to DashBoard link displayed");

	}

	
	/*
	 * [ACS_PFM3_SDG][UAT]Error Hiding Manual Accounts from Account Summary 
	 */
	
	@Test(description = "AT-110631:1226431", enabled = true, priority = 10)
	public void showHideManualAccount() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		// Add Manual Loans account
		MRBugs_Loc.noDataScreen_Button().click();
		System.out.println(MRBugs_Loc.linkManualAccountfl3().size());
		
		//Add manual accounts[Loans,Mortgage] -FL2/FL3
		MRBugs_Loc.addManualAccountLoanFLs();
		MRBugs_Loc.linkAnotherAccount().click();
		MRBugs_Loc.addManualAccountMortgageFLs();
		
		// Edit and update Liabilities account	
		PageParser.forceNavigate("AccountSettings", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		// Validation
		Assert.assertTrue(MRBugs_Loc.accountSettSize().size() == 2, "FAIL: Accounts are bot added");

		MRBugs_Loc.accountLoanSettingsIcon().click();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(MRBugs_Loc.accountSettingsManualHideSummary());
		MRBugs_Loc.accountSetManualSave().click();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.accountMortgageSettingsIcon().click();
		SeleniumUtil.waitForPageToLoad(3000);
		MRBugs_Loc.accountSettingsManualHideSummary().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.accountSetManualSave().click();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		// Validation
		Assert.assertTrue(MRBugs_Loc.accountSettSize().size() == 0, "FAIL: Account is not hidded");
		logger.info("PASS:Account hidded successfully");

	}
	
	

	/*
	 *  [ACS_PFM3_SDG][UAT]Yodlee - Budget - Drop-down arrow does not work on create a budget page 
	 */
	@Test(description = "AT-110630:1244689", enabled = true, priority = 11)
	public void budgetDropdownIssue() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Create Acoount Group
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.click(MRBugs_Loc.CreateGroupBtns());
		SeleniumUtil.click(MRBugs_Loc.groupNameTxtBox());
		MRBugs_Loc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name").trim());
		SeleniumUtil.click(MRBugs_Loc.getAccountOptionChckBox().get(0));
		SeleniumUtil.click(MRBugs_Loc.createOrUpdateGroup());

		// Create new budget
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());

		// Bug FLow
		MRBugs_Loc.createBdgtButton().click();
		SeleniumUtil.click(MRBugs_Loc.selectExistingDd());
		SeleniumUtil.waitForPageToLoad(2000);
		//Validation
		Assert.assertTrue(MRBugs_Loc.selectExistingDdHide().size()==0);
		
		MRBugs_Loc.selectExistingDd().click();
		SeleniumUtil.waitForPageToLoad(2000);
		
		//Validation
		Assert.assertTrue(MRBugs_Loc.selectExistingDdHide().size()==1,"FAIL: Dropdown gettting is not getting collapsed succesfully");
		
		logger.info("PASS:Dropdown gettting collapsed succesfully");
			
		
	}
	

	/*
	 *   [ACS_PFM3_SDG][UAT]3 Currency abbreviations (USD, VND, ZAR) are missing from the currency list in add a transaction form 
	 */
	@Test(description = "AT-110629:1243320", enabled = true, priority =12)
	public void missignTransactionCurrencyList() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		PageParser.forceNavigate("Expense", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		// Handle pop-ups
		MRBugs_Loc.continuePopup();
		
		//Bug flow
		MRBugs_Loc.spendingAddTransaction().click();
		MRBugs_Loc.transactionCurrencyListDd().click();
		SeleniumUtil.waitForPageToLoad(1000);
		
		String[] getCurrencyListActual = MRBugs_Loc.getTransactionCurrencyList();
		String getCurrencyListExpected[] = PropsUtil.getDataPropertyValue("addtransCurrencyList").trim().split(",");

		//Validation
		Assert.assertEquals(getCurrencyListActual, getCurrencyListExpected, "FAIL : Currency list are not listed as expected");
		
		logger.info("PASS:Currency list are listed as expected");
	}
	
	/*
	 *  [ACS_PFM3_SDG]Budgeted amount left is showing as zero even when we have allocated amount for it.
	 */
	@Test(description = "AT-110628:1243181", enabled = true, priority = 13)
	public void budgetAmountLeftZero() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		// Create new budget
		SeleniumUtil.click(MRBugs_Loc.getStartedBudget());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetnxtbtn());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext2());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		if (!MRBugs_Loc.getbudgetCategoryList().contains("Savings")) {
			MRBugs_Loc.addFlexSpending().click();
			MRBugs_Loc.addFlexSpendingCategoryDd().click();
			MRBugs_Loc.addFlexSpendingCategoryDdText().sendKeys(PropsUtil.getDataPropertyValue("flexSpendingCatg"));
			SeleniumUtil.waitForPageToLoad(2000);
			MRBugs_Loc.addFlexSpendingCategoryDdSavings().click();
			SeleniumUtil.waitForPageToLoad(2000);
			MRBugs_Loc.addFlexSpendingCategoryAmt().clear();
			MRBugs_Loc.addFlexSpendingCategoryAmt().sendKeys(PropsUtil.getDataPropertyValue("flexSpendingAmt"));
			MRBugs_Loc.addFlexSpendingCategoryAmtAdd().click();
		}

		
		SeleniumUtil.click(MRBugs_Loc.budgetFtueNext3());
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		// Add Manual Transaction
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.transactionManualAdd().click();
		MRBugs_Loc.amount_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionAMT"));
		MRBugs_Loc.description_AMT().sendKeys(PropsUtil.getDataPropertyValue("addTransactionDesc"));
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualTypeDropDown().click();
		String [] transType =MRBugs_Loc.transactionManualTypeValue();
		d.findElement(By.xpath(transType[1].replace("+", "Withdrawal"))).click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualDebitedFrom().click();
		MRBugs_Loc.transactionManualDebitedFromValue().click();
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.projectedtransDate().sendKeys(DateUtil.getPrevDate());
		SeleniumUtil.waitForPageToLoad(2000);
		MRBugs_Loc.transactionManualcategoryDropDown().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionManualcategoryText().sendKeys(PropsUtil.getDataPropertyValue("addTransactionSavings"));
		MRBugs_Loc.transactioncategoryDropdownValueSavings().click();
		SeleniumUtil.waitForPageToLoad(1500);
		MRBugs_Loc.transactionEventAdd().click();
		SeleniumUtil.waitUntilElementInvisible(d, "transactionEventAdd", "Transaction", null);
		
		//Bug flow
		PageParser.forceNavigate("Budget", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);

		String percentage01 = MRBugs_Loc.budgetMySavingsCategoryPercentageOutside().getText().trim();
		String amount01 = MRBugs_Loc.budgetMySavingsCategoryAmountOutside().getText().trim();
		MRBugs_Loc.budgetMySavingsCategory().click();

		String percentage02 = MRBugs_Loc.budgetMySavingsCategoryPercentageInside().getText().trim();
		String amount02 = MRBugs_Loc.budgetMySavingsCategoryAmountInside().getText().trim();

		//Validation
		Assert.assertTrue((percentage01.equals(percentage02)) && (amount01.equals(amount02)), "FAIL:Budget amount are not same");
		
		logger.info("Pass :Budget amounts are same ");

	}
	
	
	/*
	 *  Bug 1246884 - [ACS_PFM3_SDG][UAT]Yodlee_Financial_Forecast_Not_Matching_amounts
	 */
	@Test(description = "AT-110873:1246884", enabled = true, priority = 14)
	public void forecastNotMatchingAmount() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSFCCardUsername"),
				PropsUtil.getDataPropertyValue("dagSFCCardPassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);

		//FTU page
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.fFStartReview().click();
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 20);
		MRBugs_Loc.ffNextBtn().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.ffNextBtn().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.ffNextBtn().click();
		SeleniumUtil.waitForPageToLoad();
		MRBugs_Loc.ffNextBtn().click();
		SeleniumUtil.waitForPageToLoad();

		
		//Bug flow
		MRBugs_Loc.SFCLandingPageAccountDropDown().click();
		int totalAmountExpected=0;
		List<WebElement> uncheckBoxes = MRBugs_Loc.fFtrendsMultiAccountCheckedBoxes();

		for (int i = 0; i < uncheckBoxes.size(); i++) {
			uncheckBoxes.get(i).click();
		}

		List<WebElement> checkBoxes = MRBugs_Loc.fFtrendsMultiAccountUnCheckedBoxes();
		List<WebElement> checkBoxesAmnt = MRBugs_Loc.fFtrendsMultiAccountCckedBoxAmnt();

		for (int i = 0; i < 2; i++) {
			checkBoxes.get(i).click();
			
			totalAmountExpected+=Integer.parseInt(checkBoxesAmnt.get(i).getText().replaceAll( "[^a-zA-Z0-9 ]" , "" ));
		}
		
		MRBugs_Loc.fFtrendsMultiAccountUpdate().click();
		SeleniumUtil.waitForPageToLoad();
		int totalAmountActual=Integer.parseInt(MRBugs_Loc.fFtrendsAvailableBal().getText().replaceAll( "[^a-zA-Z0-9 ]" , "" ));
		
		//Validation
		Assert.assertEquals(totalAmountActual, totalAmountExpected,"FAIL: Selected acoount amount are not matching");
		
		logger.info("Pass :Amounts are matching");
			
	}

	/*.
	 *  [PFM 3.0]Alert Settings - 'Back to Goals' link displayed instead of 'Back to Alert Settings'
	 */
	@Test(description = "AT-110904:1245742", enabled = true, priority = 0)
	public void alertSettingsBackToLink() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("************************Logged in to application.***********************");
		// Add Account
		accAddition.addAggregatedAccountUIAPI(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword"), PropsUtil.getDataPropertyValue("addAccountSource"));
		
		//Bug Flow
		PageParser.forceNavigate("AlertSetting", d);
		MRBugs_Loc.waitUntilSpinnerDisappears(d, 60);
		MRBugs_Loc.alert_Goals_Tab().click();
		SeleniumUtil.waitForPageToLoad(3000);
		MRBugs_Loc.alert_Create_A_Goal().click();
		Assert.assertTrue(MRBugs_Loc.alert_Back_To_Alerts().isDisplayed(), "FAIL : Back To Alert Settings link is not displayed");
		MRBugs_Loc.startGoalGetStartButton().click();
		Assert.assertTrue(MRBugs_Loc.alert_Back_To_Alerts().isDisplayed(), "FAIL : Back To Alert Settings link is not displayed");
		MRBugs_Loc.alert_Back_To_Alerts().click();
		Assert.assertTrue(MRBugs_Loc.alert_Create_A_Goal().isDisplayed(), "FAIL : Back To Alert Settings link will not navigate back");
		logger.info("Pass :Back To Alert Settings link displayed");
	}
}
