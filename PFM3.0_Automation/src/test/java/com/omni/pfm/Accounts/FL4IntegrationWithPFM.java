/**
* Copyright (c) 2020 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Kongara Sravan
*/
package com.omni.pfm.Accounts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;

public class FL4IntegrationWithPFM extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(FL4IntegrationWithPFM.class);

	Accounts_Details_Loc accounts;
	AccountAddition accountAdd;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("FL4 Integration");
		TestBase.tc = TestBase.extent.startTest("Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		accounts = new Accounts_Details_Loc(d);
		WebDriverFactory.setDriver(d);
		d.get("https://wellqafl4.yodleeinteractive.qa.yodlee.cloud/authtest/wlbackup/fastlink/");
		try {
			JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
			js.executeScript("document.querySelector('#proceed-link').click();");
		} catch (Exception e) {
		}
	}

	@Test(description = "Login Successfully", priority = 0, enabled = true)
	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
	}

	@Test(description = "Add dag account and verify", priority = 1, enabled = true)
	public void addDagAccountAndVerifyThePopup() {
		accountAdd.linkAccount();
		accountAdd.switchToFL4Frame();
		Assert.assertTrue(accountAdd.addDagAccountUsingFL4("accounterror.site16441.2", "site16441.2"),
				"Account addition is not happening");
		List<String> returnTheContainersAdded = accountAdd.getTheContainersAdded();
		List<String> containersShouldBeAdded = Arrays
				.asList(PropsUtil.getDataPropertyValue("ContainersWillBeAdded").split(","));
		Collections.sort(returnTheContainersAdded);
		Collections.sort(containersShouldBeAdded);
		if (!returnTheContainersAdded.equals(containersShouldBeAdded)) {
			Assert.fail("All the containers are not added. Expected :: " + containersShouldBeAdded + ", Actual :: "
					+ returnTheContainersAdded);
		}
	}

	@Test(description = "Verify added accounts in accounts finapp", priority = 2, enabled = true)
	public void verifyAddedAccountsInAccountsFinapp() {
		accountAdd.clickOnSaveAndFinishButton();
		d.switchTo().defaultContent();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountsPage", d);
		List<String> returnTheAvailableAccounts = accounts.getTheAvailableAccounts();
		List<String> accountsShouldBePresent = Arrays
				.asList(PropsUtil.getDataPropertyValue("AccountsWillBeAdded").split(","));
		Collections.sort(returnTheAvailableAccounts);
		Collections.sort(accountsShouldBePresent);
		if (!returnTheAvailableAccounts.equals(accountsShouldBePresent)) {
			Assert.fail("All the accounts are not available in accounts page. Expected :: " + accountsShouldBePresent
					+ ", Actual :: " + returnTheAvailableAccounts);
		}
	}

	@Test(description = "verify cancel button functionality", priority = 3, enabled = true)
	public void verifyCancelButtonFunctionality() {
		d.switchTo().defaultContent();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountsPage", d);
		ArrayList<String> returnTheAvailableAccounts = accounts.getTheAvailableAccounts();
		int presentAccountCount = accounts.getTheAccountsCount();
		accountAdd.linkAccount();
		accountAdd.switchToFL4Frame();
		Assert.assertTrue(accountAdd.addDagAccountUsingFL4("accounterror.site16441.1", "site16441.1"),
				"Account addition is not happening");
		accountAdd.clickOnCancelButton();
		accountAdd.clickOnExitButtonInCancelPopup();
		SeleniumUtil.waitFor(2);
		d.switchTo().defaultContent();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountsPage", d);
		ArrayList<String> accountsAfterClickingOnCancelButton = accounts.getTheAvailableAccounts();
		int afterAccountCount = accounts.getTheAccountsCount();
		if (!(afterAccountCount == presentAccountCount)) {
			Assert.fail("Cancel Button functionality is not working. Accounts before Cancel :: "
					+ returnTheAvailableAccounts + " and accounts after cancel :: "
					+ accountsAfterClickingOnCancelButton);
		}
	}

	@Test(description = "verify Save and link more button functionality", priority = 4, enabled = true)
	public void verifySaveAndLinkMoreAccountsButtonFunctionality() {
		d.switchTo().defaultContent();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		accountAdd.linkAccount();
		accountAdd.switchToFL4Frame();
		Assert.assertTrue(accountAdd.addDagAccountUsingFL4("generic.bank3", "bank3"),
				"Account addition is not happening");
		accountAdd.clickOnSaveAndLinkMoreAccountsButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (!SeleniumUtil.isDisplayed(SeleniumUtil.getByObject("FL4", null, "SearchBox"), 10)) {
			Assert.fail("Site search button is not displayed after clicking on save and link more accounts button");
		}
	}
	
	@Test(description = "Add all manual accounts and verify", priority = 5, enabled = true)
	public void addManualRealEstateAccountsAndVerify() {
		d.switchTo().defaultContent();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		accountAdd.linkAccount();
		Assert.assertTrue(accountAdd.addRealEstateAccountManually("MyHome", "123456"),
				"Manual Account addition is not happening");
		accountAdd.switchToFL4Frame();
		accountAdd.clickOnSaveAndFinishButtonForRealEstateAccount();
		d.switchTo().defaultContent();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountsPage", d);
		ArrayList<String> theAvailableAccounts = accounts.getTheAvailableAccounts();
		if (!theAvailableAccounts.contains("MyHome")) {
			Assert.fail("Added Real Estate account :: \" MyHome \" is not available in accounts page. Available accounts in accounts page :: "
					+ theAvailableAccounts);
		}
	}

	@Test(description = "Add all manual accounts and verify", priority = 6, enabled = true, dataProvider = "ManualAccountParameters")
	public void addAllManualAccountsAndVerify(String accountType, String accountName, String dueOrBalance) {
		d.switchTo().defaultContent();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		accountAdd.linkAccount();
		Assert.assertTrue(accountAdd.addManualAccountInFL4(accountType, accountName, dueOrBalance),
				"Manual Account addition is not happening");
		accountAdd.switchToFL4Frame();
		accountAdd.clickOnSaveAndFinishButtonForManualAccount();
		d.switchTo().defaultContent();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountsPage", d);
		ArrayList<String> theAvailableAccounts = accounts.getTheAvailableAccounts();
		if (!theAvailableAccounts.contains(accountName)) {
			Assert.fail("Added manual account :: \" " + accountName + "\" with account type :: \" " + accountType
					+ " \" is not available in accounts page. Available accounts in accounts page :: "
					+ theAvailableAccounts);
		}
	}

	@DataProvider(name = "ManualAccountParameters")
	public String[][] manualAccountParameters() {
		return new String[][] { { "Savings", "AutomationSavingsAccount", "1000" },
				{ "Checking", "AutomationCheckingAccount", "1000" }, { "CD", "AutomationCDAccount", "1000" },
				{ "Prepaid", "AutomationPrepaidAccount", "1000" }, { "Bills", "AutomationBillsAccount", "1000" },
				{ "Credit", "AutomationCreditAccount", "1000" }, { "Insurance", "AutomationInsuranceAccount", "1000" },
				{ "Brokerage Cash", "AutomationBrokerageCashAccount", "1000" },
				{ "Annuity", "AutomationAnnuityAccount", "1000" },
				{ "Personal Loan", "AutomationPersonalLoanAccount", "1000" },
				{ "Home Loan", "AutomationHomeLoanAccount", "1000" },
				{ "Other Assets", "Automation_Other_Assets_Account", "1000" },
				{ "Other Liabilities", "Automation Other Liabilities Account", "1000" } };
	}
}
