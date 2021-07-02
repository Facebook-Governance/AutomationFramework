/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Dashboard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.SeleniumUtil;


public class NetworthLoc {
	
	public WebDriver d;
	public static String pageName="DashboardPage";
	public static String frameName=null;

	public NetworthLoc(WebDriver d)	{		
		this.d = d;
	}
	public WebElement Assetvl() {
		return SeleniumUtil.getWebElement(d, "Assetval_NL", pageName, frameName);
	}
	public WebElement Liabilitiesvl() {
		return SeleniumUtil.getWebElement(d, "Liabilitiesval_NL", pageName, frameName);
	}
	public WebElement Nwvalue() {
		return SeleniumUtil.getWebElement(d, "networth_NL", pageName, frameName);
	}
	public WebElement ChangePR() {
		return SeleniumUtil.getWebElement(d, "changeper_NL", pageName, frameName);
	}
	public WebElement ChangePR_mob() {
		return SeleniumUtil.getWebElement(d, "changeper_NL_mob", pageName, frameName);
	}
	public WebElement Nwtext() {
		return SeleniumUtil.getWebElement(d, "Nwtextr_NL", pageName, frameName);
	}
	public WebElement heading() {
		return SeleniumUtil.getWebElement(d, "hd_NL", pageName, frameName);
	}
	public WebElement NetworthCard() {
		return SeleniumUtil.getWebElement(d, "card_NL", pageName, frameName);
	}
	
}
