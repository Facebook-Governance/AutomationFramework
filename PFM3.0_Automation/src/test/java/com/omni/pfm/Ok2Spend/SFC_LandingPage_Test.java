/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Ok2Spend;

import java.awt.AWTException;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Ok2Spend.SFC_LandingPage_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class SFC_LandingPage_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(SFC_LandingPage_Test.class);

	Add_Manual_Transaction_Loc manual;
	LoginPage login;

	SFC_LandingPage_Loc landing;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException, AWTException {
		doInitialization("SFC Landing Page Login");
		TestBase.tc = TestBase.extent.startTest("SFC Landing Page", "Login");

		landing = new SFC_LandingPage_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);
		login = new LoginPage();

	}

	@Test(description = "Login", priority = 0)
	public void login() throws Exception {
		// login.loginMain(d, loginParameter);
		logger.info(">>>>>Login");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("FinancialForecast", d);
		SeleniumUtil.waitForPageToLoad(10000);
	}

	@Test(description = "Verify Data points and the details below", priority = 1)
	public void verifyDataPoint() throws ParseException {

		// Assert.assertTrue(landing.verifyallPostedTransactionHeader());

		Assert.assertTrue(landing.getDataPoint().isDisplayed(), "Data Points Not displayed");

		logger.info(">>>>>Verifying the transactions related to Data Graph");
		for (int i = 0; i < landing.getTransactionList().size(); i++) {
			Assert.assertTrue(landing.getTransactionList().get(i).isDisplayed(), "Transaction is not displayd..");

		}
	}

	@Test(description = "Verify that on selecting/deselecting accounts from the account filter dropdown above the graph the related transactions are displayed", priority = 3)
	public void VerifyAccountsTransaction() throws ParseException {
		// Verify the transaction should contains selected account name
		SeleniumUtil.click(landing.SFCLandingPageAccountDropDown());

		String account1 = landing.SFCDropDownAccName().get(0).getText().trim();
		logger.info("Account 1 :: " + account1);
		String account2 = landing.SFCDropDownAccName().get(1).getText().trim();
		logger.info("Account 2 :: " + account2);
		List<WebElement> transactionRowAccountNames = landing.SFCTransactionRowAccount();
		boolean expected = true;
		String failuremessage = "";
		for (WebElement transactionRow : transactionRowAccountNames) {
			String accountName = transactionRow.getText();
			logger.info("Account name of present row :: " + accountName);
			if (!(accountName.toLowerCase().contains(account1.toLowerCase()) || accountName.toLowerCase().contains(account2.toLowerCase()))) {
				expected = false;
				failuremessage = "Transactions displayed are not from expected accounts. Expected Accounts :: "
						+ account1 + "," + account2 + " && Actual Account displayed :: " + accountName;
				break;
			}
		}
		Assert.assertTrue(expected, failuremessage);
	}

	@Test(description = "Verify that in Forecast, Current Balance section shows the  sum of current balances for selected bank accounts", priority = 4)
	public void verifyForcastAvailbaleBalance() throws ParseException {

		SeleniumUtil.click(landing.SFCForcastTab());
		SeleniumUtil.waitForPageToLoad();

		double tot_bal = landing.getTotalBalance();

		String avg_bal = landing.SFCForcastAvailableBalance().getText().replace("$", "");
		avg_bal = avg_bal.replace(",", "");
		double avg = Double.parseDouble(avg_bal);

		Assert.assertEquals(avg, tot_bal);
	}

	@Test(description = "Verify that in Forecast, Upcoming Bills section shows for the selected bank accounts, total number and amount of projected bills and transfers till next paycheck date.", priority = 5)
	public void verifyupComingBillTotalAmount() throws ParseException {// verify upcoming bill total amount

		double tot = landing.getUpcomingBillTotalAmount();
		// double tot_actual =
		// Double.parseDouble(landing.SFCForcastUpcomingBill().getText().replace("-$",
		// "").trim());
		String tot_val = landing.SFCForcastUpcomingBill().getText().replace("-", "").trim();
		tot_val = tot_val.replace("$", "");
		tot_val = tot_val.replace(",", "");
		double tot_actual = Double.parseDouble(tot_val);

		Assert.assertEquals(tot, tot_actual);
	}

	@Test(description = "Verifying group by category functionality in events", priority = 6)
	public void verifyEventsCategory() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(landing.getEvents());
		SeleniumUtil.waitForPageToLoad(7000);

		SeleniumUtil.click(landing.getDropDownCategory());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(landing.getCategory());
		SeleniumUtil.waitForPageToLoad(2000);

		/*
		 * for(int i = 0; i < landing.getCategoriesOfEvent().size();i++) {
		 * SeleniumUtil.waitForPageToLoad(2000);
		 * Assert.assertTrue(landing.getCategoriesOfEvent().get(i).isDisplayed()
		 * ,"Category not present.."); }
		 */
	}

}
