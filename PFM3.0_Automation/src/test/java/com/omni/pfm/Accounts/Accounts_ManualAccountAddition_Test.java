/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.awt.AWTException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ManualAccountAddition_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_ManualAccountAddition_Test.class);
	Accounts_ManualAcnt_Loc manualAcnt;
	AccountAddition accountAdd;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		accountAdd = new AccountAddition();
	}

	@Test(description = "ACCT-01_01: creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 0, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
	}

	public String getFutureDate(int numberOfDaysToAdd) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, numberOfDaysToAdd);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	@Test(description = "Verify accounts are added succesfully.", dependsOnMethods = {
			"Login" }, priority = 1, enabled = true)

	public void addAccounts() throws Exception {

		// String "date" = finUtil.get"date"(5);

		accountAdd.addManualAccount("Cash", "PayTM Wallet", null, "10001", "1111", null, null, null);

		accountAdd.addManualAccount("Cards", "HDFC Platinum", null, "10002", "2222", "500", "date", null);

		accountAdd.addManualAccount("Investments", "Zerodha Fund", null, "10003", "3333", "50", null, null);

		accountAdd.addManualAccount("Loans", "PNB Housing", null, "10004", "4444", "500", "date", null);

		accountAdd.addManualAccount("Insurance", "ICICI Lombard", null, "10005", "5555", "100", "date", "asset");

		accountAdd.addManualAccount("Bills", "BESCOM", null, null, "6666", "1000", "date", null);

		accountAdd.addManualAccount("Rewards", "SpiceJet Miles", null, "10006", "7777", "100", null, null);

		accountAdd.addManualAccount("Rewards", "Citrus Points", null, "10007", "8888", "200", null, null);

		accountAdd.addManualAccount("Other Assets", "Audi Q7", null, "10008", null, null, null, null);

		accountAdd.addManualAccount("Other Liabilities", "Swins Liabilities", null, "10009", null, null, null, null);

		accountAdd.addManualAccount("Cable & Satellite", "Big TV DTH", null, "10011", "9999", "1000", "date", null);

		accountAdd.addManualAccount("Mortgages", "USB Gage", null, "10012", "1010", "500", "date", null);

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

	}

}
