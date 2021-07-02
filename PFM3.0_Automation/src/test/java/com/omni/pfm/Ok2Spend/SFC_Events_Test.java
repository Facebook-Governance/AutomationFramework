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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Ok2Spend.SFC_Events_Loc;
import com.omni.pfm.pages.Ok2Spend.SFC_LandingPage_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFC_Events_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(SFC_Onboarding_Test.class);

	LoginPage login;
	SFC_Events_Loc events;
	AccountAddition acc;
	Add_Manual_Transaction_Loc manual;
	SFC_LandingPage_Loc landing;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("SFC Events Login");
		TestBase.tc = TestBase.extent.startTest("SFC Events", "Login");

		login = new LoginPage();
		events = new SFC_Events_Loc(d);
		acc = new AccountAddition();
		manual = new Add_Manual_Transaction_Loc(d);
		landing = new SFC_LandingPage_Loc(d);
//		LoginPage.loginMain(d, loginParameter);

		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);

		acc.linkAccount();
		acc.addAggregatedAccount("senseguttenberg.bank43", "bank43");
	}

	@Test(description = "Login and add Cash account", priority = 0)
	public void Login() throws Exception {
		PageParser.forceNavigate("FinancialForecast", d);
	}

	@Test(description = "Select all the accounts in Trends tab dropdown and verify the events tab", priority = 1)
	public void verifyAccountsInTrendsAndEvents() {
		boolean flag = false;

		SeleniumUtil.click(events.trends_tab_SFC());
		logger.info(">>>>>Verifying all the checkboxes");

		SeleniumUtil.click(events.trends_account_dd_SFC());
		SeleniumUtil.waitForPageToLoad(3000);

		for (int i = 0; i < events.trends_accList_SFC().size(); i++) {
			if (events.trends_accList_SFC().get(i).getAttribute("aria-checked").equals("false")) {
				SeleniumUtil.click(events.trends_accList_SFC().get(i));
				flag = true;
			}
		}

		if (flag == true) {
			SeleniumUtil.click(events.apply_button_SFC());
			logger.info(">>>>>All accounts checked");
			SeleniumUtil.waitForPageToLoad(3000);
		} else {
			SeleniumUtil.click(events.trends_account_dd_SFC());
			SeleniumUtil.waitForPageToLoad(3000);

		}

		SeleniumUtil.click(events.events_tab_SFC());
		Assert.assertTrue(events.newEvents_banner_SFC().isDisplayed(), "New events banner not displayed..");

	}

	@Test(description = "Verify the new events added in Cash events section", priority = 2)
	public void verifyCashEventsAdded() {
		SeleniumUtil.click(events.newEvents_banner_SFC());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(events.seeAllEvents_box_SFC().isDisplayed());

		SeleniumUtil.click(events.seeAllEvents_box_SFC());
		Assert.assertTrue(events.newEvents_banner_SFC().isDisplayed(), "New events banner not displayed..");

	}

	@Test(description = "Verify Adding the new events", priority = 3)
	public void verifyEventsBanner() {
		SeleniumUtil.click(events.newEvents_banner_SFC());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(events.add_Events().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(events.ignore_Events().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(events.addandedit_Events().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "Verify EventsAdded", priority = 4)
	public void verifyEventsAsdded() {
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(events.add_Events());

		for (int i = 0; i < events.events_List_SFC().size(); i++) {
			Assert.assertTrue(events.events_List_SFC().get(i).isDisplayed());
			SeleniumUtil.waitForPageToLoad(2000);
		}
	}

	@Test(description = "Verify Credit Card Events", priority = 3, enabled = false)
	public void verifyCreditCardEvents() {
		events.switchToCreditCardEvent();

		SeleniumUtil.click(events.events_tab_SFC());
		Assert.assertTrue(events.newEvents_banner_SFC().isDisplayed(), "New events banner not displayed..");

	}

	@Test(description = "Verify the new events added in Cash events section", priority = 4, enabled = false)
	public void verifyCreditEventsAdded() {
		SeleniumUtil.click(events.newEvents_banner_SFC());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(events.seeAllEvents_box_SFC().isDisplayed(), "See all events link not displayed..");

		SeleniumUtil.click(events.seeAllEvents_box_SFC());
		Assert.assertTrue(events.newEvents_banner_SFC().isDisplayed(), "New events banner not displayed..");

	}

	@Test(description = "Verify that when user selects group by category each event is displayed once under different categories with the next scheduled date", priority = 6)
	public void verifyGroupByCategory() {
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(events.category_dropdown_SFC());
		SeleniumUtil.waitForPageToLoad(2000);

		logger.info(">>>>>Selecting group by category");
		SeleniumUtil.click(events.groupbycat_OK());

		SeleniumUtil.waitForPageToLoad(2000);
		for (int i = 0; i < events.getEventsGroup().size(); i++) {
			Assert.assertTrue(events.getEventsGroup().get(i).isDisplayed(), "Events group not displayed..");
		}
	}

	@Test(description = "Mark As Paid Integreation", priority = 7)
	public void verifyMarkAsPaid() {
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(events.addManualTran_SFC());
		SeleniumUtil.waitForPageToLoad(3000);
		manual.createTransactionWithRecurSFC(PropsUtil.getDataPropertyValue("SFCManualAmount"),
				PropsUtil.getDataPropertyValue("SFCManualDesc"), 1, 2, 1, 1, 30, 1, 1,
				PropsUtil.getDataPropertyValue("SFCManualNote"));

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(events.markAsPaid_SFC());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(events.map_Popup().isDisplayed(), "Mark As Paid Popup not displayed");
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(events.btnMarkAsPaid_SFC_Popup().isDisplayed(), "Mark As Paid Button not present");
		Assert.assertTrue(events.btnCancle_SFC_Popup().isDisplayed(), "Cancel button not present");
		Assert.assertTrue(events.dontShow_Radio_Popup().isDisplayed(),
				"Dont Show This mesage again Radio button not present");

	}

	@Test(description = "Mark As Paid Integration", priority = 8)
	public void verifyMarkAsPaidClick() {
		SeleniumUtil.click(events.btnMarkAsPaid_SFC_Popup());

		for (int i = 0; i < events.events_List_SFC().size(); i++) {
			Assert.assertTrue(events.events_List_SFC().get(i).isDisplayed());
			SeleniumUtil.waitForPageToLoad(2000);
		}
	}

}
