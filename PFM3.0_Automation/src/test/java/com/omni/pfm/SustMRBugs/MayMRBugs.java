/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.SustMRBugs;

import java.awt.AWTException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.AccountAddition.YCOM_Regression_AccAndGroupCreation;
import com.omni.pfm.Accounts.Accounts_ManualAcnt_Loc;
import com.omni.pfm.MRBugs.MRBugs_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Flexible_Spending_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Categories.Manage_Categories_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class MayMRBugs extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(MRBugs_Loc.class);
	public AggregatedAccount_Settings_Loc account_Settings_Loc;
	public ManualAccount_Settings_Loc settingsLoc;
	Manage_Categories_Loc manage_Categories_Loc = null;
	MRBugs_Loc MRBugs_Loc = null;
	Accounts_ManualAcnt_Loc manualAcnt;
	YCOM_Regression_AccAndGroupCreation userRegAccAddUtil;
	String originalName = "";
	Budget_Landing_Page_Loc Budget_Landingpage;
	Budget_Select_Accounts_Loc select_accounts;
	Budget_Income_And_Bill_Loc Budget_Income_Bill;
	Budget_Flexible_Spending_Loc Budget_FlexibleSpending;
	Budget_Income_And_Bill_Summery_Loc Budget_Income_Bill_Summery;
	Budget_No_Account_Loc no_accounts;
	AccountAddition accAddition = new AccountAddition();
	CashFlow_LandingPage_Loc LandingPage;
	Add_Manual_Transaction_Loc add_manual_transaction;
	ExpLandingPage_Loc ExpLandingPage;
	Transaction_Categorization_Rules_Loc rule1;
	NetWorth_Loc netWorth = null;
	Aggregated_Transaction_Details_Loc Aggre_Tranc_details;
	Transaction_Tag_Loc Tag;
	

	@BeforeClass()
	public void init() {
		doInitialization("Manage Categories");
		manage_Categories_Loc = new Manage_Categories_Loc(d);
		MRBugs_Loc = new MRBugs_Loc(d);
		account_Settings_Loc = new AggregatedAccount_Settings_Loc(d);
		settingsLoc = new ManualAccount_Settings_Loc(d);
		manualAcnt = new Accounts_ManualAcnt_Loc(d);
		account_Settings_Loc= new AggregatedAccount_Settings_Loc(d);
		settingsLoc= new ManualAccount_Settings_Loc(d);
		userRegAccAddUtil= new YCOM_Regression_AccAndGroupCreation();
		String originalName = "";
		Budget_Landingpage= new Budget_Landing_Page_Loc(d);
		select_accounts= new Budget_Select_Accounts_Loc(d);
		Budget_Income_Bill= new Budget_Income_And_Bill_Loc(d);
		Budget_FlexibleSpending= new Budget_Flexible_Spending_Loc(d);
		Budget_Income_Bill_Summery= new Budget_Income_And_Bill_Summery_Loc(d);
		no_accounts= new Budget_No_Account_Loc(d);
		AccountAddition accAddition = new AccountAddition();
		LandingPage= new CashFlow_LandingPage_Loc(d);
		add_manual_transaction= new Add_Manual_Transaction_Loc(d);
		ExpLandingPage= new ExpLandingPage_Loc(d);
		rule1= new Transaction_Categorization_Rules_Loc(d);
		netWorth= new NetWorth_Loc(d);
		Aggre_Tranc_details= new Aggregated_Transaction_Details_Loc(d);
		Tag = new Transaction_Tag_Loc(d);
		logger.info("Initializing");
	}

	@Test(description = "AT-72460:Bug971321 - PFM3.0-Alert settings->account page is blank if we add manual account, AT-72446:Bug946128 - PFM 3.0 - Settings - Account Alerts page is showing up blank when the manual account is added ", priority = 1, groups = {
			"Desktop" })

	public void verifyAlertSettingsPage() throws Exception {

		SeleniumUtil.waitForPageToLoad(10000);
		LoginPage.loginMain(d, loginParameter);
		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d, By.id("dashboardLinkAccountBtn"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.id("dashboardLinkAccountBtn")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);
			SeleniumUtil.waitForPageToLoad();
			try {
				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} catch (Exception e) {

			}
			SeleniumUtil.waitForPageToLoad(10000);
			if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
				SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
			} else {
				SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
			}
			SeleniumUtil.waitForPageToLoad(3000);
		}

		SeleniumUtil.waitForPageToLoad(45000);
		SeleniumUtil.click(d.findElement(By.xpath("//span[text()='Account Type']")));
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(d.findElement(By.xpath("//li[contains(text(),'Cash')]")));

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

		SeleniumUtil.waitForPageToLoad(20000);
		if ("12345".equalsIgnoreCase("12345")) {
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).sendKeys("12345");
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).sendKeys(Keys.TAB);

		} else {

			System.out.println("Not visible");
		}

		if (!"1001".equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys("1001");
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
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).sendKeys("null");
			} catch (Exception e) {
				System.out.println("Not visble");
			}
		} else {

			System.out.println("Not visble");

		}

		if ("null".equalsIgnoreCase("04/08/2019")) {
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).clear();
			// commented by priyanka
			// nextDueDate = getDate();
			System.out.println("Date is" + "null");
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).sendKeys("null");
			SeleniumUtil.waitForPageToLoad(2500);
		} else {
			System.out.println("Not visble");
		}

		if ("null".equalsIgnoreCase("asset")) {
			d.findElement(By.xpath("//input[contains(@id,'liability')]")).click();
		} else {

			System.out.println("Not visible");
		}

		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(d.findElement(By.id("addBtn")));

		SeleniumUtil.waitForPageToLoad(10000);

		SeleniumUtil.click(d.findElement(By.xpath("(//button[contains(@aria-label,'All Done')])[1]")));
		SeleniumUtil.waitForPageToLoad(10000);

		PageParser.forceNavigate("AlertSetting", d);
		SeleniumUtil.waitForPageToLoad(30000);
		Assert.assertTrue(
				SeleniumUtil.isVisible(MRBugs_Loc.AlertSettingLinkAcc(), "Expected page in accounts tab is displayed"));// Bug
																														// 946128,971321
																														// verified

		logger.info("Navigating to Accounts Page");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(30000);

	}

	@Test(description = "AT-72444:Bug945429 - [Amelia_Main][Thrivent]: Currency drop down on account setting floater for real estate account is not scrolling full,AT-72440:Bug920967 - Verify that proper error message is seen for current balance, ", priority = 2, groups = {
			"Desktop" })

	public void verifyAccountNameLength() throws Exception {
		PageParser.forceNavigate("AccountSettings", d);

		SeleniumUtil.waitForPageToLoad(4000);

		SeleniumUtil.click(MRBugs_Loc.ManualSettingLink());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(MRBugs_Loc.SettingsIconinAccSetOpt());
		SeleniumUtil.waitForPageToLoad(40000);

		SeleniumUtil.click(MRBugs_Loc.getCurrency_MAS());
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> CurrencyValues = SeleniumUtil.getWebElements(d, "CurrencyValues1", "AccountsPage", null);
		int Length = CurrencyValues.size();
		Assert.assertTrue(SeleniumUtil.isVisible(CurrencyValues.get(Length - 1), "Last value in list is visible"));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.scrollToWebElementMiddle(d, CurrencyValues.get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(SeleniumUtil.isVisible(CurrencyValues.get(0), "First value in list is visible"));

		settingsLoc.typeCurrentBalance(PropsUtil.getDataPropertyValue("currentbalancedummyvalue").trim());
		SeleniumUtil.waitForPageToLoad(5000);

		TestBase.imageprocess.captureScreenshot(d, "966612-Colour of error", TestBase.screenShotEnabledFlag, false,
				5000);

		logger.info("Asserting the error message for current balance");
		Assert.assertTrue(SeleniumUtil.isVisible(MRBugs_Loc.Amount_ErrorText(), "Error text displays"));
		Assert.assertEquals(MRBugs_Loc.Amount_ErrorText().getText(),
				PropsUtil.getDataPropertyValue("currentbalanceerrormessage"));// Bug 920967 verified

	}

	@Test(description = "AT-72466:Bugid:981235[MS_PFM3.0_UI][Budget]: Amount field accepts any number of digits and error is not thrown while saving and it gets saved as \"99,999,999,999.99\", AT-72467:Bugid982177[MS_PFM3.0_UI][Budget]: Clicking on the edit icon in Step2 for Income and clicking on cancel button, gives error for text box if it is \"0\" which is not correct as user is cancelling it", priority=3)
	public void Login1() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Adding Dag Site");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword")));
		logger.info("*************************Dag Site Added Successfully************");

		PageParser.forceNavigate("Budget", d);
		logger.info("*************************Navigated to Budget Page************");

		// SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.waitForElement("YCOM_Mainline", 60000);
		SeleniumUtil.click(no_accounts.GetStartedButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(select_accounts.NextButton2());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Get Started Button*************************");

		SeleniumUtil.click(Budget_Landingpage.editincome());
		SeleniumUtil.waitForPageToLoad();
		Budget_Landingpage.editincometext().clear();
		Budget_Landingpage.editincometext().sendKeys(PropsUtil.getDataPropertyValue("income5"));
		Assert.assertEquals(MRBugs_Loc.amtlimitincomebudget().getText(),
				PropsUtil.getDataPropertyValue("amountlimiterror2"));

		SeleniumUtil.click(MRBugs_Loc.CancelButton_CB());

		Assert.assertTrue(MRBugs_Loc.amtlimitincomebudget() == null);

		logger.info("*************************Erros is not seen after cancel in step 2*************************");

		SeleniumUtil.click(Budget_Landingpage.editincome());
		SeleniumUtil.waitForPageToLoad();

		Budget_Landingpage.editincometext().clear();
		Budget_Landingpage.editincometext().sendKeys(PropsUtil.getDataPropertyValue("income1"));
		Budget_Landingpage.editincometext().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Edited the income text*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on Edit Income Button*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeNextButton());
		SeleniumUtil.waitForPageToLoad(8000);
		logger.info("*************************Clicked on Income Next Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.flexibleSpendingEditIcon());
		SeleniumUtil.waitForPageToLoad();
		logger.info("*************************Clicked on flexible Spending Edit Icon*************************");

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
				PropsUtil.getDataPropertyValue("amountlimit"));

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(MRBugs_Loc.amtlimitbudget().getText(), PropsUtil.getDataPropertyValue("amountlimiterror"));
		logger.info("*************************Amount box limit checked*************************");

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryAtm"),
				PropsUtil.getDataPropertyValue("amountForAtmCategory"));

		Budget_Income_Bill.typeCategoryValues(PropsUtil.getDataPropertyValue("categoryRestaurants"),
				PropsUtil.getDataPropertyValue("amountForCategoryRestaurants"));
		SeleniumUtil.waitForPageToLoad(2500);

		Budget_Income_Bill.CategoryValues().sendKeys(Keys.TAB);
		logger.info("*************************Edited all the Category Values-amounts*************************");

		SeleniumUtil.click(Budget_Landingpage.incomeSaveButton());
		WebDriverWait wait = new WebDriverWait(d, 40);
		WebElement ele = wait.until(ExpectedConditions.visibilityOf(Budget_FlexibleSpending.doneButton()));
		System.out.println(ele);
		logger.info("*************************Clicked on income Save Button*************************");

		SeleniumUtil.click(Budget_FlexibleSpending.doneButton());
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("*************************Clicked on Done Button*************************");

		Assert.assertTrue(Budget_FlexibleSpending.createdBudgetheader().isDisplayed());
		logger.info("*************************My Budget Header Verified*************************");

	}

	@Test(description = "MR-77558", groups = { "DesktopBrowsers" }, enabled = true, priority = 4)
	public void verifyFTUEPopupDoesNotCome() throws InterruptedException, AWTException {
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite3_CF"),
				PropsUtil.getDataPropertyValue("dagPassword3_CF")));

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(90000);

		logger.info("==>Verifying the chart block");

		Assert.assertTrue(LandingPage.ChartBlock().isDisplayed());
		Assert.assertTrue(LandingPage.PopUpBlock() == null);

	}

	@Test(description = "AT-72443:Bug942510 - [STAB_3699] : Manage category finapp is not loading if we navigated from dashboard.", priority = 5)
	public void NavigatetoCategories() throws Exception {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {

		} else {
			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad(40000);
			SeleniumUtil.click(MRBugs_Loc.ViewAllAcc());
			SeleniumUtil.waitForPageToLoad(10000);
			SeleniumUtil.click(MRBugs_Loc.FirstRowContainer());
			SeleniumUtil.waitForPageToLoad(10000);
			SeleniumUtil.click(MRBugs_Loc.more());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(MRBugs_Loc.manage());
			SeleniumUtil.waitForPageToLoad(10000);
			Assert.assertTrue(
					SeleniumUtil.isVisible(MRBugs_Loc.getHeaderText(), "Manage Categories Finapp loaded successfully"));

		}
	}

	@Test(description = "AT-72445:Bugid:945909[ICICI_PFM3.0]Splash Page customisation for Adding external Accounts", priority = 6)

	public void addAccountWithSplash() throws Exception {

		logger.info("***** Loging In ******");

		LoginPage.loginMain(d, loginParameter);

		logger.info("Adding account with splash");

		SeleniumUtil.waitForPageToLoad(5000);

		if (MRBugs_Loc.skipSplash() != null) {
			SeleniumUtil.click(MRBugs_Loc.addAccountonSplash());
		} else {
			logger.info("Splash page is not displayed");
		}

		logger.info("Add Dag Site");
		SeleniumUtil.waitForPageToLoad(3000);
		try {
			WebElement getStartedText = SeleniumUtil.getVisibileWebElement(d, "getStarted", "LinkAnAccount", null);
			if (getStartedText.isDisplayed()) {
				SeleniumUtil.click(getStartedText);

			}
		} catch (Exception e) {

			System.out.println(e);
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),
				PropsUtil.getDataPropertyValue("dagSitePassword")));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-72449:Bugid:957231PFM 3.0 - EA/IA -For the subcategory line separator are not proper", priority = 7)

	public void Login() throws Exception {

		logger.info("***** Loging In ******");

		LoginPage.loginMain(d, loginParameter);

		logger.info("***** Adding Accounts ******");

		accAddition.linkAccount();

		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("eAiA_dagSite"),
				PropsUtil.getDataPropertyValue("eAiA_dagSitePassword")));

		logger.info("***** Navigating to Expense/Income Analysis ******");

		PageParser.forceNavigate("Expense", d);

		SeleniumUtil.waitForPageToLoad(5000);

		Assert.assertTrue(ExpLandingPage.continueButton().isDisplayed());
		ExpLandingPage.continueButton().click();

		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(ExpLandingPage.CMlinkAccountLink().isDisplayed());
		SeleniumUtil.click(ExpLandingPage.gotoAnalysisBtn());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-72449:Bugid:957231PFM 3.0 - EA/IA -For the subcategory line separator are not proper", priority = 8)

	public void addSubCategory() throws Exception {

		logger.info("***** Navigating to manage categories ******");

		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(manage_Categories_Loc.expenseCatLink());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));
		
		SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));

		SeleniumUtil.waitForPageToLoad(3000);

		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_SubCatName_957231"));

		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButtonInMLC());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-72449:Bugid:957231PFM 3.0 - EA/IA -For the subcategory line separator are not proper", priority = 9)
	void verifyAddManualTransactIcon() throws InterruptedException {
		logger.info("***** Navigating to Transactions ******");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(90000);
		Assert.assertTrue(add_manual_transaction.addManualIcon().isDisplayed());

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP"))

		{
			add_manual_transaction.MobileMore().click();
			SeleniumUtil.waitForPageToLoad();
			add_manual_transaction.mobileTransactionIcon().click();
			SeleniumUtil.waitForPageToLoad();

		} else {
			//
			SeleniumUtil.click(add_manual_transaction.addManualLink());
			SeleniumUtil.waitForPageToLoad(10000);
			Assert.assertEquals(add_manual_transaction.addManualHeader().getText(),
					PropsUtil.getDataPropertyValue("ManulaTransactionHeader"));

		}
	}

	@Test(description = "AT-72449:Bugid:957231PFM 3.0 - EA/IA -For the subcategory line separator are not proper", priority = 10)
	public void createManualTxn() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.amount().sendKeys("1000");
		add_manual_transaction.description().sendKeys("Test txn");
		add_manual_transaction.frequencyDropDown().click();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.FrequencyList().get(0));

		add_manual_transaction.accountdropdown().click();
		SeleniumUtil.click(add_manual_transaction.ListAccount(1).get(3));

		add_manual_transaction.Schedule().sendKeys(add_manual_transaction.targateDate1(-2));
		add_manual_transaction.catgorie().click();
		rule1.search().sendKeys(PropsUtil.getDataPropertyValue("Category_SubCatName_957231"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(MRBugs_Loc.subCat().isDisplayed());
		SeleniumUtil.click(MRBugs_Loc.subCat());

		add_manual_transaction.add().click();
	}

	@Test(description = "AT-72449:Bugid:957231PFM 3.0 - EA/IA -For the subcategory line separator are not proper"/*
																													 * ,
																													 * dependsOnMethods=
																													 * {"Login"}
																													 */, priority = 11)

	public void verifyHighLevelCategory() throws InterruptedException {

		logger.info("Verify that Expense Analysis by Category FinApp should have the Expense High Level Categories.");

		PageParser.forceNavigate("Expense", d);

		SeleniumUtil.waitForPageToLoad(5000);

		ExpLandingPage.clickChart(6);

		SeleniumUtil.waitForPageToLoad(5000);

		Assert.assertTrue(ExpLandingPage.expensebyCategory().isDisplayed());

		SeleniumUtil.waitForPageToLoad(30000);

		// Assert.assertTrue(MRBugs.autoHLC().isDisplayed());

		logger.info("*** Verify that Expense Analysis Finapp should have the Master Level Category and subcategory");

		/*
		 * MRBugs.autoHLC().click();
		 * 
		 * SeleniumUtil.waitForPageToLoad();
		 * 
		 * Assert.assertTrue(MRBugs.autoMLC().isDisplayed());
		 * 
		 * MRBugs.autoMLC().click();
		 * 
		 * TestBase.imageprocess.captureScreenshot(d,
		 * "SubcatAtMLC",TestBase.screenShotEnabledFlag, false, 5000);
		 */

	}

	@Test(description = "AT-72451:Bug962157 - PFM 3.0 - IH - Close icon is missing in the Group By Accounts Pop up in the Investments Holdings Finapp", groups = {
			"DesktopBrowsers" }, enabled = true, priority=11)
	public void verifyCloseIcon() throws InterruptedException, AWTException {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad(40000);
		MRBugs_Loc.continueBtnInWelcomeCM().click();
		SeleniumUtil.waitForPageToLoad(3000);
		MRBugs_Loc.goToMyInvestments().click();
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(MRBugs_Loc.GrpAccountsCheckbx());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(MRBugs_Loc.DonutChart());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(SeleniumUtil.isVisible(MRBugs_Loc.Closeicon(), "Close icon is available"));

	}

	@Test(description = "AT-72442:Bug937106 - [Harris_PFM3] : Performance chart time zone displays ET where as other places System time zone is displayed", enabled = true, priority=12)
	public void verifyTimeZonePerfChart() throws InterruptedException, AWTException {
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(MRBugs_Loc.FirstRowHolding());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(SeleniumUtil.isVisible(MRBugs_Loc.PerfChartTimeDisplay(),
		"System Time zone is displayed in Performance Chart"));

	}

	@Test(description = "AT-72450:Bug957655 - PFM 3.0 - Dashboard - Net Worth - The year is not shown for the current year.", groups = {
			"DesktopBrowsers" }, enabled = true, priority = 13)
	public void verifyCurrentyear() throws InterruptedException, AWTException {
		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.waitForPageToLoad(40000);
		SeleniumUtil.click(netWorth.continueButton());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(netWorth.seeMyNetWorthButton());
		SeleniumUtil.waitForPageToLoad(3000);
		List<WebElement> XaxisValues = SeleniumUtil.getWebElements(d, "XAxisValue_NetWorth", "NetWorth", null);
		int Length = XaxisValues.size();
		boolean Flag = false;
		for (int i = 0; i < Length; i++) {
			System.out.println(XaxisValues.get(i).getText());
			if (XaxisValues.get(i).getText().contains((PropsUtil.getDataPropertyValue("CurrentYear")))) {
				Flag = true;
				break;
			}
		}
		Assert.assertTrue(Flag);
	}

	@Test(description = "AT-72461:Bug971980 - [Citi_PFM3.0]When unselecting Asset and Liabilities check box and navigating to table and again coming back to chart view asset and liablities are getting selected but in the chart asset and liabilities are not there", groups = {
			"DesktopBrowsers" }, enabled = true, priority = 14)
	public void verifyNetWorthChart() throws InterruptedException, AWTException {
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(MRBugs_Loc.AssetsCheckbox());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(MRBugs_Loc.LiabilitiesCheckbox());
		SeleniumUtil.waitForPageToLoad(1000);
		logger.info("Checkbox unselected");
		SeleniumUtil.click(MRBugs_Loc.TableLink());
		logger.info("Navigating to table");
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(MRBugs_Loc.ChartLInk());
		logger.info("Navigating back to chart");
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(MRBugs_Loc.AssetsCheckbox());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(MRBugs_Loc.LiabilitiesCheckbox());
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("Checkbox unselected");
		Assert.assertTrue(SeleniumUtil.isVisible(MRBugs_Loc.AssetsChart(), "Assets chart is visible"));
		Assert.assertTrue(SeleniumUtil.isVisible(MRBugs_Loc.LiabilitiesChart(), "LiabilitiesChart chart is visible"));
	}

	@Test(description = " AT-72458:Bugid970219[Citi_PFM3.0]In transaction details screen, category drop down is overlapping with transaction date header when scrolling ", priority=15)

	public void VeryCategorydrodown() throws Exception {

		LoginPage.loginMain(d, loginParameter);

		
		 accAddition.linkAccount(); SeleniumUtil.waitForPageToLoad();
		 Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.
		  getDataPropertyValue("dagSite"),
		  PropsUtil.getDataPropertyValue("dagSitePassword")));
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.navigateToPage("Transaction", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		SeleniumUtil.click(Aggre_Tranc_details.catgoryField());
		SeleniumUtil.waitForPageToLoad(10000);

		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,-500)", "");

		TestBase.imageprocess.captureScreenshot(d, "Categorydropdown", TestBase.screenShotEnabledFlag, false, 5000);
		Assert.assertTrue(Aggre_Tranc_details.catgoryList(6, 1).isDisplayed());

	}

	@Test(description = " AT-72468:Bugid983717[CETERA][PFM3.0]:Tag search is not working properly in the transactions FinApp ",  priority=16)
	public void Verytagsearch() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(Aggre_Tranc_details.collapsIcon().get(2));
		SeleniumUtil.waitForPageToLoad(4000);

		Tag.creteTag(PropsUtil.getDataPropertyValue("TagTag1"));
		SeleniumUtil.waitForPageToLoad(4000);
		Tag.creteTag(PropsUtil.getDataPropertyValue("TagTag2"));
		SeleniumUtil.waitForPageToLoad(4000);
		Tag.update().click();

		SeleniumUtil.click(Tag.tagIcon());
		SeleniumUtil.waitForPageToLoad(4000);

		Tag.searchField().sendKeys(PropsUtil.getDataPropertyValue("TagTagno1"));
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(Tag.TagList().get(1).getText(), PropsUtil.getDataPropertyValue("TagTag2"));

	}

}
