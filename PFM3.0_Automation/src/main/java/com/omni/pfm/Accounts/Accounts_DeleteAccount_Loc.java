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

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_DeleteAccount_Loc extends SeleniumUtil {
	Logger logger = LoggerFactory.getLogger(Accounts_DeleteAccount_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_DeleteAccount_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> popUpHeader() {
		return getWebElements(d, "popUpHeader", pageName, null);
	}

	public void verifyPopupContent() {
		List<WebElement> l = getWebElements(d, "deletePopUpContentDP", pageName, frameName);
		CommonUtils.assertEqualsListElements("DeleteAcntPopUpContent", l);
	}

	public WebElement popUpCancelBtn() {
		return getWebElement(d, "deleteGroupCancelBtn", pageName, null);
	}

	public WebElement popUpDeleteBtn() {
		return getWebElement(d, "deleteGroupDeleteBtn", pageName, null);
	}

	public WebElement deleteAcntSuccessMsg() {
		return getWebElement(d, "deleteAcntSuccessMsg", pageName, null);
	}

	public WebElement deletePopUpCB() {
		return getWebElement(d, "deletePopUpCB", pageName, null);
	}

	public void clickingOndeletePopUpCB() {
		click(deletePopUpCB());
	}

	public void clickingOnCancelBtn() {
		click(popUpCancelBtn());
	}

	public void clickingOndeleteBtn() {
		click(popUpDeleteBtn());
	}

	public boolean isCBVisibile() {
		return deletePopUpCB().isDisplayed();
	}

	public WebElement deletePopUpASContent1() {
		return getWebElement(d, "deletePopUpASContent1", pageName, null);
	}

	public WebElement deletePopUpASContent2() {
		return getWebElement(d, "deletePopUpASContent2", pageName, null);
	}

	public WebElement deletePopUpASContent3() {
		return getWebElement(d, "deletePopUpASContent3", pageName, null);
	}

	public WebElement AccountSetting_SettingLink(String accountTypeIndex, String acctNameIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountSetting_SettingLink");
		xpath = xpath.replaceAll("acctTypeIndex", accountTypeIndex);
		xpath = xpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement DeleteBtn() {
		return getWebElement(d, "DeleteBtn", pageName, null);
	}

	public void deleteAccount(String accountTypeIndex, String[] acctNameIndex) {
		int accountlenth = acctNameIndex.length;
		for (int i = 0; i < accountlenth; i++) {
			waitForPageToLoad();
			click(this.AccountSetting_SettingLink(accountTypeIndex, acctNameIndex[0]));
			waitForPageToLoad(7000);
			click(DeleteBtn());
			this.clickingOndeletePopUpCB();
			waitForPageToLoad();
			this.clickingOndeleteBtn();
			waitForPageToLoad();
		}
	}

}
