/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad Bhat
 ******************************************************************************/
package com.omni.pfm.Cashflow;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.CashFlow.Cashflow_Revamped_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Cashflow_Revamped_Test extends TestBase {
	private static Logger logger = LoggerFactory.getLogger(Cashflow_Revamped_Test.class);

	Cashflow_Revamped_Loc cash;
	LoginPage login;
	Accounts_ViewByGroup_Loc viewByGroup;
	AccountAddition accAddition;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Cashflow Rewamped Test");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		cash = new Cashflow_Revamped_Loc(d);
		viewByGroup = new Accounts_ViewByGroup_Loc(d);
		accAddition = new AccountAddition();
		logger.info(">>>>> Logging in..");
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Navigating to Accounts Page..");
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Adding aggregated accounts..");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		accAddition.addAggregatedAccount("twoxml.bank1", "bank1");

		logger.info(">>>>> Adding aggregated accounts..");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		accAddition.addAggregatedAccount("swin.site16441.2", "site16441.2");
	}

	@Test(description = "Create a Group : A CF Group", priority = 2)
	public void createGroup1() {
		logger.info(">>>>> Navigating to Acounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.click(viewByGroup.groupType());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(viewByGroup.getCreateGroup());
		viewByGroup.typeGroupName("A CF Group");
		SeleniumUtil.waitForPageToLoad(1000);
		viewByGroup.selectCheckBoxAll();
		SeleniumUtil.click(viewByGroup.createUpdateGroupBtm());
	}

	@Test(description = "Creating Group 2 : B CF Group", dependsOnMethods = { "createGroup1" }, priority = 3)
	public void createGroup2() {
		if (viewByGroup.ismoreBtn_mob_AMTPresent()) {
			SeleniumUtil.click(viewByGroup.moreBtn_mob());
		} else {
			SeleniumUtil.click(viewByGroup.moreButon());
		}
		SeleniumUtil.click(viewByGroup.CreateBtn1());
		SeleniumUtil.waitForPageToLoad(1000);
		viewByGroup.typeGroupName("B CF Group");
		viewByGroup.selectCheckBoxAll();
		SeleniumUtil.click(viewByGroup.createUpdateGroupBtm());
		SeleniumUtil.waitForPageToLoad(1000);
	}

	@Test(description = "AT-6978,AT-69791 : Verify Accounts Group is Displayed", dependsOnMethods = {
			"createGroup2" }, priority = 4)
	public void verifyAccountsGroup() {

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(cash.account_dropdown());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-69823 : Verify Net Cashflow is not clickable", priority = 5)
	public void verifyNetCashflowIsNotClickable() {
		logger.info(">>>>> verifying Net cashflow tabel");
		for (int i = 0; i < cash.net_cashflow_data_table().size(); i++) {
			try {
				cash.net_cashflow_data_table().get(i).click();
				Assert.assertTrue(cash.transaction_header().isDisplayed(),
						"Net cashflow is clickable in cashflow page");
			} catch (Exception e) {
				logger.error("Net Cashflow cannot be clickable..");
			}
		}
	}

	@Test(description = "AT-69981 : Verify Alert Settings", priority = 6)
	public void verifyAlertSettings() {
		logger.info(">>>>> Verifying alert settings..");
		if (cash.istimeFilterCloseMobile()) {
			SeleniumUtil.click(cash.timeFilterCloseMobile());
		}
		cash.goToAlertSettings();
		Assert.assertEquals(cash.alert_setting_popup_header().getText().trim(),
				PropsUtil.getDataPropertyValue("Alert_Settings_Popup_Header"),
				"..... Alert Settings Popup Header mismatch");

	}

	@Test(description = "AT-69993,AT-69991 : Verify Close button in Alert Settings Popup is Displayed", dependsOnMethods = {
			"verifyAlertSettings" }, priority = 7)
	public void verifyCloseButton() {
		logger.info(">>>>> verifying Close Button in Alert Settings");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(cash.close_button_alert_settings().isDisplayed(),
					"..... Close button in Alert Setting Popup is not displayed..");
		}
	}

	@Test(description = "AT-69993 : Verify Clicking on close button will close the finapp", dependsOnMethods = {
			"verifyCloseButton" }, priority = 8)
	public void verifyCloseButtonClick() {
		SeleniumUtil.click(cash.close_button_alert_settings());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertFalse(SeleniumUtil.isDisplayed(cash.alertSettingPopup, 2),
				"Close button functionality is not working");
	}

	@Test(description = "AT-69987 : Verify Toggle Buttons in alert settings option", priority = 9)
	public void verifyToggleButtons() {
		logger.info(">>>>> Clicking on Alert Settings");
		cash.goToAlertSettings();

		for (int i = 0; i < cash.alert_toggle().size(); i++) {
			Assert.assertTrue(cash.alert_toggle().get(i).isDisplayed(), "..... Toggle button is not displayed..");
		}
	}

	@Test(description = "AT-69988,AT-69987 : Verify the toggle buttons are clickable", dependsOnMethods = {
			"verifyToggleButtons" }, priority = 10)
	public void verifyToggleClick() {
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>>> Verifying the toggle buttons are clickable..");
		SeleniumUtil.click(cash.alert_toggle().get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(cash.alert_toggle().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(cash.alert_toggle().get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(cash.save_changes_btn_alert().isDisplayed(), "..... Save Changes Button is not displayed.");

	}

	@Test(description = "AT-69992 : Verify the clicking on save changes button", dependsOnMethods = {
			"verifyToggleClick" }, priority = 11)
	public void verifySaveChangesPopup() {
		logger.info(">>>>> Clicking on Save Changes Button");
		SeleniumUtil.click(cash.save_changes_btn_alert());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertFalse(SeleniumUtil.isDisplayed(cash.alertSettingPopup, 2),
				"Save button functionality is not working");
	}

	// Custom Date related Test Cases
	@Test(description = "AT-69809,AT-69810 : Verify Custom Filter. ", priority = 12)
	public void verifyCustomDateFilter() {
		logger.info(">>>>> Verifying custom dates");
		cash.goToCustomDate();
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info(">>>>Trying to enter the date in wrong format");
		cash.start_date().sendKeys(PropsUtil.getDataPropertyValue("wrong_from_date"));
		SeleniumUtil.waitForPageToLoad(3000);
		cash.end_date().sendKeys(PropsUtil.getDataPropertyValue("wrong_to_date"));
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(cash.update_custom_date());
		SeleniumUtil.waitForPageToLoad(3000);
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertTrue(cash.popup_header_mob().isDisplayed(), "..... Wrong Date Pattern accepted..");
		} else {
			Assert.assertTrue(cash.popup_header().isDisplayed(), "..... Wrong Date Pattern accepted..");
		}
	}

	@Test(description = "AT-69964 : Verify Custom date filter using proper format of input", dependsOnMethods = {
			"verifyCustomDateFilter" }, priority = 13)
	public void verifyCustomDateWithActualFormat() {
		logger.info(">>>>> Entering proper date in format");
		cash.start_date().clear();
		cash.start_date().sendKeys(PropsUtil.getDataPropertyValue("correct_from_date"));
		SeleniumUtil.waitForPageToLoad(3000);
		cash.end_date().clear();
		cash.end_date().sendKeys(PropsUtil.getDataPropertyValue("correct_to_date"));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(cash.update_custom_date());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertFalse(SeleniumUtil.isDisplayed(cash.popupHeader, 2), "After adding proper from and to date still the popup is displayed");
	}

	@Test(description = "AT-69813 : Verify Custom Dates with special Characters", dependsOnMethods = {
			"verifyCustomDateWithActualFormat" }, priority = 14)
	public void verifyStartDateWithSpclChar() {
		logger.info(">>>>> Trying to enter special characters");
		cash.goToCustomDate();

		cash.start_date().clear();
		cash.start_date().sendKeys(PropsUtil.getDataPropertyValue("special_character_custom_date"));
		SeleniumUtil.waitForPageToLoad(3000);
		cash.end_date().clear();
		cash.end_date().sendKeys(PropsUtil.getDataPropertyValue("special_character_custom_date"));
		SeleniumUtil.waitForPageToLoad(3000);

		String startDate = cash.start_date().getAttribute("Value");

		logger.info(">>>>> verifying start date with special character");
		Assert.assertEquals(startDate, null, ".... Special Characters accepted");

	}

	@Test(description = "AT-69813 : Verify Custom Dates with special Characters", dependsOnMethods = {
			"verifyStartDateWithSpclChar" }, priority = 15)
	public void verifyEndDateWithSpclChar() {
		logger.info(">>>>> Verifying end date with special characters");
		String endDate = cash.end_date().getAttribute("Value");
		Assert.assertEquals(endDate, null, ".... Special Characters accepted");

	}

	@Test(description = "AT-69853,AT-69851,AT-69868:Verify down arrow in cashflow details section ", dependsOnMethods = {
			"verifyEndDateWithSpclChar" }, priority = 16)
	public void verifyDownArrow() {
		logger.info(">>>>>> Closing the custom date popup..");
		SeleniumUtil.click(cash.popup_close_icon());
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(cash.cashflow_details_down_arrow().isDisplayed(),
				"..... Cash flow details arror is not displayed.");

	}

	@Test(description = "AT-69827 : Verify forecast checkbox will be checked by default", priority = 17)
	public void verifyForecastButton() {
		logger.info(">>>>> Verifying forecast checkbox is checked by default..");
		String checkedExpected = "true";
		// String checkedActual = cash.getForecastCheckboxAreaChecked();

		// Assert.assertEquals(checkedActual, checkedExpected, "..... Check box is not
		// checked by default.");

	}

	@Test(description = "AT-69794:Verify Deleting the accounts group", dependsOnMethods = {
			"createGroup2" }, priority = 18)
	public void verifyDeleteGroups() {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(viewByGroup.groupType());
		SeleniumUtil.waitForPageToLoad(2000);

		List<WebElement> DeleteIcon = viewByGroup.DeleteIcon1();

		int ListSize = viewByGroup.DeleteIcon1().size();
		for (int i = 0; i < ListSize; i++) {
			SeleniumUtil.click(DeleteIcon.get(i));
			SeleniumUtil.waitForPageToLoad(3000);
			SeleniumUtil.click(viewByGroup.deleteGroupOption());
			SeleniumUtil.waitForPageToLoad(2000);
		}

	}

	@Test(description = "AT-69839  :Verify closing account will reflect the data in UI", priority = 19, enabled = false)
	public void verifyCloseAccount() {
		logger.info(">>>>> Closing the account");
		logger.info(">>>>> Navigating to Accounts");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		for (int i = 0; i < cash.more_options_account().size(); i++) {
			logger.info("Closing the account" + (i + 1));

			logger.info(">>>>> Clicking on more button");
			SeleniumUtil.click(cash.more_options_account().get(i));
			SeleniumUtil.waitForPageToLoad(3000);

			logger.info(">>>> Clicking on account settings option..");
			SeleniumUtil.click(cash.account_setting_option_more());
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(cash.close_account_link());
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(cash.close_account_btn());
			SeleniumUtil.waitForPageToLoad(3000);

			Assert.assertTrue(cash.toast_message().isDisplayed(), ">>>>> Toast Message not present..");

		}

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertFalse(SeleniumUtil.isDisplayed(cash.graphSection, 2), "Even after deleting all accounts graph section is still displayed in cashflow page");

	}

	@Test(description = "AT-69840 : Verify closing account will reflect the data in UI", priority = 20)
	public void verifyDeleteAccount() {
		logger.info(">>>>> Deleting the account");
		logger.info(">>>>> Navigating to Accounts");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		for (int i = 0; i < cash.more_options_account().size(); i++) {
			logger.info("Deleting the account" + (i + 1));

			logger.info(">>>>> Clicking on more button");
			SeleniumUtil.click(cash.more_options_account().get(i));
			SeleniumUtil.waitForPageToLoad(3000);

			logger.info(">>>> Clicking on account settings option..");
			SeleniumUtil.click(cash.account_setting_option_more());
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(cash.delete_account_link());
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(cash.delete_confirm_checkbox());
			SeleniumUtil.waitForPageToLoad(3000);

			SeleniumUtil.click(cash.delete_account_button());
			SeleniumUtil.waitForPageToLoad(3000);

			Assert.assertTrue(cash.toast_message().isDisplayed(), ">>>>> Toast Message not present..");

		}

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(8000);

		Assert.assertFalse(SeleniumUtil.isDisplayed(cash.graphSection, 2), "Even after deleting all accounts graph section is still displayed in cashflow page");

	}

}
