/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Ok2Spend;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class SFC_Events_Loc {

	WebDriver d;
	String pageName = "FinancialForecast";
	String frameName = null;

	public SFC_Events_Loc(WebDriver d) {
		this.d = d;

	}

	public WebElement trends_tab_SFC() {
		return SeleniumUtil.getWebElement(d, "trends_tab_SFC", pageName, frameName);

	}

	public WebElement trends_account_dd_SFC() {
		return SeleniumUtil.getWebElement(d, "trends_account_dd_SFC", pageName, frameName);

	}

	public List<WebElement> trends_accList_SFC() {
		return SeleniumUtil.getWebElements(d, "trends_accList_SFC", pageName, frameName);

	}

	public WebElement apply_button_SFC() {
		return SeleniumUtil.getWebElement(d, "apply_button_SFC", pageName, frameName);

	}

	public WebElement events_tab_SFC() {
		return SeleniumUtil.getWebElement(d, "events_tab_SFC", pageName, frameName);

	}

	public WebElement newEvents_banner_SFC() {
		return SeleniumUtil.getWebElement(d, "newEvents_banner_SFC", pageName, frameName);

	}

	public WebElement seeAllEvents_box_SFC() {
		return SeleniumUtil.getWebElement(d, "seeAllEvents_box_SFC", pageName, frameName);

	}

	public void switchToCreditCardEvent() {
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "heading_dropdown", pageName, frameName));

		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "type_card", pageName, frameName));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public void switchToCashEvent() {
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "heading_dropdown", pageName, frameName));

		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "type_cash", pageName, frameName));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public List<WebElement> getEventsGroup() {
		return SeleniumUtil.getWebElements(d, "events_group_SFC", pageName, frameName);
	}

	public WebElement category_dropdown_SFC() {
		return SeleniumUtil.getWebElement(d, "category_dropdown_SFC", pageName, frameName);
	}

	public WebElement showupcoming_OK() {
		return SeleniumUtil.getWebElement(d, "showupcoming_OK", pageName, frameName);

	}

	public WebElement groupbycat_OK() {
		return SeleniumUtil.getWebElement(d, "groupbycat_OK", pageName, frameName);
	}

	public WebElement add_Events() {
		return SeleniumUtil.getWebElement(d, "add_Events", pageName, frameName);
	}

	public WebElement ignore_Events() {
		return SeleniumUtil.getWebElement(d, "ignore_Events", pageName, frameName);
	}

	public WebElement addandedit_Events() {
		return SeleniumUtil.getWebElement(d, "addandedit_Events", pageName, frameName);
	}

	public List<WebElement> events_List_SFC() {
		return SeleniumUtil.getWebElements(d, "events_List_SFC", pageName, frameName);
	}

	public WebElement addManualTran_SFC() {
		return SeleniumUtil.getWebElement(d, "addManualTran_SFC", pageName, frameName);
	}

	public WebElement markAsPaid_SFC() {
		return SeleniumUtil.getWebElement(d, "markAsPaid_SFC", pageName, frameName);
	}

	public WebElement map_Popup() {
		return SeleniumUtil.getWebElement(d, "map_Popup", pageName, frameName);
	}

	public WebElement btnMarkAsPaid_SFC_Popup() {
		return SeleniumUtil.getWebElement(d, "btnMarkAsPaid_SFC_Popup", pageName, frameName);
	}

	public WebElement btnCancle_SFC_Popup() {
		return SeleniumUtil.getWebElement(d, "btnCancle_SFC_Popup", pageName, frameName);
	}

	public WebElement dontShow_Radio_Popup() {
		return SeleniumUtil.getWebElement(d, "dontShow_Radio_Popup", pageName, frameName);
	}

}
