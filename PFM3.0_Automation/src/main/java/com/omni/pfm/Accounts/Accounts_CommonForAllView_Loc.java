/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_CommonForAllView_Loc {
	Logger logger=LoggerFactory.getLogger(Accounts_CommonForAllView_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_CommonForAllView_Loc(WebDriver d){
		this.d=d;
	}

	public List<WebElement> MainContainerName(int containerName) {
		return  SeleniumUtil.getWebElements(d, "MainContainerName", "AccountsPage", null);
	}

	public List<WebElement> getContSubType(String ContainerNumber) {

		String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null,"getContSubType");
		webElement = webElement.replaceAll("ContainerNumber", ContainerNumber);
		return d.findElements(By.xpath(webElement));
	}


	public List<WebElement> getAccountName() {
		return SeleniumUtil.getWebElements(d, "getAccountName", "AccountsPage", null);
	}
	public WebElement LinkActBtnMob() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkActBtnMob", "AccountsPage", null);
	}
	public WebElement LinkActBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "linkActBtn", "AccountsPage", null);
	}
	public WebElement accountType() {
		return SeleniumUtil.getWebElement(d, "accountType", pageName, frameName);
	}
	public WebElement BillAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "billAmount", "AccountsPage", null);
	}
	public List<WebElement> accountNumberInFIView() {
		return SeleniumUtil.getWebElements(d, "accountNumberInFIView", "AccountsPage", null);
	}
	public List<WebElement> getCurrentBalance() {
		return SeleniumUtil.getWebElements(d, "getCurrentBalance", "AccountsPage", null);
	}
	public WebElement refreshIcn() {
		return SeleniumUtil.getVisibileWebElement(d, "RefreshIcn", "AccountsPage", null);
	}
	public WebElement refreshText() {
		return SeleniumUtil.getVisibileWebElement(d, "RefreshText", "AccountsPage", null);
	}
	public WebElement expandIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "ExpandIcon", "AccountsPage", null);
	}

	public WebElement collapseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "CollapseIcon", "AccountsPage", null);
	}
	public WebElement moreOptoin() {
		return SeleniumUtil.getWebElement(d, "moreOptoin", "AccountsPage", null);
	}
	
}
