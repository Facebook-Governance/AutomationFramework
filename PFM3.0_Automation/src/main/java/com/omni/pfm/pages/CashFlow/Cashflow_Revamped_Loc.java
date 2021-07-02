/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CashFlow;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class Cashflow_Revamped_Loc extends SeleniumUtil {
	WebDriver d;
	String pageName = "CashFlow", frameName = null;
	public By alertSettingPopup = getByObject(pageName, frameName, "alert_setting_popup_header_cf");
	public By popupHeader = getByObject(pageName, frameName, "popup_header_cf");
	public By graphSection = getByObject(pageName, frameName, "graph_section_cf");
	
	public Cashflow_Revamped_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement no_account_screen() {
		// TODO Returning No Accounts Screen
		return getVisibileWebElement(d, "no_data_screen_cf", pageName, frameName);
	}

	public WebElement account_dropdown() {
		// TODO Returning Accounts Dropdown
		return getWebElement(d, "account_dropdown_cf", pageName, frameName);
	}

	public List<WebElement> tick_mark() {
		// TODO Tick Marks of Account Dropdown
		return getWebElements(d, "tick_mark_cf", pageName, frameName);
	}

	public List<WebElement> net_cashflow_data_table() {
		// TODO Tick Marks of Account Dropdown
		return getWebElements(d, "net_cashflow_data_table_cf", pageName, frameName);
	}

	public WebElement transaction_header() {
		// TODO Transaction Header when navigate from Cashflow
		return getVisibileWebElement(d, "transaction_header_cf", pageName, frameName);
	}

	public WebElement more_button() {
		return getVisibileWebElement(d, "more_button_cf", pageName, frameName);
	}

	public WebElement alert_settings_option() {
		return getWebElement(d, "alert_settings_option_cf", pageName, frameName);
	}

	public WebElement alert_setting_popup_header() {
		return getVisibileWebElement(d, "alert_setting_popup_header_cf", pageName, frameName);
	}

	public WebElement save_changes_btn_alert() {
		return getVisibileWebElement(d, "save_changes_alert_cf", pageName, frameName);
	}

	public List<WebElement> alert_toggle() {
		// TODO Alert Toggles in alert settings popup
		return getWebElements(d, "alert_toggle_cf", pageName, frameName);
	}

	public WebElement close_button_alert_settings() {
		return getVisibileWebElement(d, "close_button_alert_settings_cf", pageName, frameName);
	}

	public void goToAlertSettings() {
		if (ismoreBtn_mob_AMTPresent()) {
			click(moreBtn_mob());
		} else {
			click(more_button());
		}
		waitForPageToLoad(5000);

		click(alert_settings_option());
		waitForPageToLoad();
	}

	public boolean ismoreBtn_mob_AMTPresent() {

		return PageParser.isElementPresent("moreBtn_mob", "AccountsPage", null);
	}

	public WebElement moreBtn_mob() {
		return getVisibileWebElement(d, "moreBtn_mob", "AccountsPage", null);
	}

	public WebElement time_duration_dropdown() {
		return getWebElement(d, "time_duration_dropdown_cf", pageName, frameName);

	}

	public WebElement custom_date_filter() {
		return getWebElement(d, "custom_date_filter_cf", pageName, frameName);

	}

	public WebElement start_date() {
		// start date
		return getWebElement(d, "start_date_cf", pageName, frameName);
	}

	public WebElement end_date() {
		// end date textbox
		return getWebElement(d, "end_date_cf", pageName, frameName);
	}

	public void goToCustomDate() {
		// Method to navigate to custom date
		click(time_duration_dropdown());
		waitForPageToLoad(2000);

		click(custom_date_filter());
		waitForPageToLoad(2000);

	}

	public WebElement update_custom_date() {
		// update button for custom date popup
		return getWebElement(d, "update_custom_date_cf", pageName, frameName);
	}

	public WebElement popup_header() {
		// custom date popup header
		return getWebElement(d, "popup_header_cf", pageName, frameName);
	}

	public WebElement popup_close_icon() {
		return getWebElement(d, "popup_close_icon_cf", pageName, frameName);
	}

	public WebElement cashflow_details_down_arrow() {
		// Returns the dropdown arrow which is present in cashflow details section
		return getWebElement(d, "cashflow_details_down_arrow_cf", pageName, frameName);
	}

	public String getForecastCheckboxAreaChecked() {
		WebElement checkbox = getWebElements(d, "forecast_checkbox_cf", pageName, frameName).get(3);
		String areaChecked = checkbox.getAttribute("aria-checked");

		return areaChecked;
	}

	public WebElement account_setting_option_more() {
		return getWebElement(d, "account_setting_option_more", pageName, frameName);
	}

	public List<WebElement> more_options_account() {
		return getWebElements(d, "more_options_account", pageName, frameName);
	}

	public WebElement close_account_link() {
		// Close account Link in account settings popup
		return getWebElement(d, "close_account_link", pageName, frameName);
	}

	public WebElement close_account_btn() {
		// Close account button in close account popup
		return getWebElement(d, "close_account_btn", pageName, frameName);
	}

	public WebElement toast_message() {
		return waitForElementVisible(d, "toast_message", pageName, frameName);
	}

	public WebElement delete_account_link() {
		return getWebElement(d, "delete_account_link", pageName, frameName);
	}

	public WebElement delete_confirm_checkbox() {
		return getWebElement(d, "delete_confirm_checkbox", pageName, frameName);
	}

	public WebElement delete_account_button() {
		return getWebElement(d, "delete_account_button", pageName, frameName);

	}

	public boolean istimeFilterCloseMobile() {
		return PageParser.isElementPresent("timeFilterClose1", "CashFlow", null);
	}

	public WebElement timeFilterCloseMobile() {
		return getWebElement(d, "timeFilterClose1", "CashFlow", null);
	}

	public WebElement popup_header_mob() {
		// custom date popup header
		return getWebElement(d, "popup_header_cf_mob", pageName, frameName);
	}
}
