/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Ok2Spend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class SFC_FTUE_Loc {
	static Logger logger = LoggerFactory.getLogger(SFC_FTUE_Loc.class);
	WebDriver d = null;
	static WebElement we;
	public String pageName = "FinancialForecast";

	public SFC_FTUE_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement SFCHeader() {
		return SeleniumUtil.getWebElement(d, "SFCHeader", pageName, null);
	}

	public WebElement SFCUser() {
		return SeleniumUtil.getWebElement(d, "SFCUser", pageName, null);
	}

	public WebElement SFCPredictable() {
		return SeleniumUtil.getWebElement(d, "SFCPredictable", pageName, null);
	}

	public WebElement SFCInfo() {
		return SeleniumUtil.getWebElement(d, "SFCInfo", pageName, null);
	}

	public WebElement SFCSpendLeftMessage() {
		return SeleniumUtil.getWebElement(d, "SFCSpendLeftMessage", pageName, null);
	}

	public WebElement SFCFTUEImage() {
		return SeleniumUtil.getWebElement(d, "SFCFTUEImage", pageName, null);
	}

	public WebElement SFCFTUENextPayCheck() {
		return SeleniumUtil.getWebElement(d, "SFCFTUENextPayCheck", pageName, null);
	}

	public WebElement SFCFTUELinkAcc() {
		return SeleniumUtil.getWebElement(d, "SFCFTUELinkAcc", pageName, null);
	}

	public WebElement SFCFTUEPopUpHeader() {
		return SeleniumUtil.getWebElement(d, "SFCFTUEPopUpHeader", pageName, null);
	}

}
