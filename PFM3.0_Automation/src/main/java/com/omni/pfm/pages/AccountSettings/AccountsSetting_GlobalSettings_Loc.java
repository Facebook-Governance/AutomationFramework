/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.pages.AccountSettings;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsSetting_GlobalSettings_Loc extends SeleniumUtil {

	static Logger logger = LoggerFactory.getLogger(AccountsSetting_GlobalSettings_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountSettings";
	static String frameName = null;

	public static final By manualAccountsInSideBar = getByObject(pageName, frameName, "ManualAccountsInSidebar");
	public static final String xpathForAccountSettingsbutton = "//*[@autoid=\"accounts_container_lbl_AccName\" and contains(text(),\"#####\")]/ancestor::*[contains(@class,\"y-tabular-border\")]//following-sibling::*//*[@autoid=\"account-btn-settings\"]";
	public static final By deleteAccountButtonInAccountSettingsPopup = getByObject(pageName, frameName, "DeleteAccountInAccountSettings");
	public static final By confirmDeleteCheckBox = getByObject(pageName, frameName, "confirmDeleteCheckBox");
	public static final By deleteButtonInConfirmDeletePopup = getByObject(pageName, frameName, "deleteButtonInConfirmDeletePopup");
	
	//
	public WebElement AccountSettingPageHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountSettingPageHeader", pageName, null);
	}

	public AccountsSetting_GlobalSettings_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> settingIconList() {
		return SeleniumUtil.getWebElements(d, "settingsIconinSett", pageName, frameName);
	}

	public List<WebElement> SiteLevelIconList() {
		return SeleniumUtil.getWebElements(d, "SiteLevelIconList", pageName, frameName);
	}

	public void clickinOnDeleteSite() {
		SeleniumUtil.click(SiteLevelIconList().get(1));
	}

	public List<WebElement> getSettingByAcntName(String AcntTypeName) {
		String getString = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "settingsIconByAcntName");
		getString = getString.replace("AccountTypeName", AcntTypeName);
		return d.findElements(By.xpath(getString));
	}

	public List<WebElement> AccountsContainerAcntType() {
		return SeleniumUtil.getWebElements(d, "AccountsContainerAcntTypes", pageName, frameName);
	}

	public void verifyOrderOfAcntContainer(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsContainerAcntType", pageName, frameName);
		CommonUtils.assertContainsListElements(propValue, l);
	}

	public List<WebElement> SidebarLabelList() {
		return SeleniumUtil.getWebElements(d, "SidebarLabelList", pageName, frameName);
	}

	public void clickingOnSmartZipAcnts() {
		SeleniumUtil.click(SidebarLabelList().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
	}

	public void clickingOnManualAcnts() {
		SeleniumUtil.click(SidebarLabelList().get(2));
		SeleniumUtil.waitForPageToLoad(3000);
	}

	public void clickingOnDagSiteAcnt() {
		SeleniumUtil.click(SidebarLabelList().get(0));
	}

	public WebElement backToAcntsLink() {
		return SeleniumUtil.getVisibileWebElement(d, "backToAcntsLink", pageName, null);
	}

	public WebElement backToAcntsLinkMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "backToAcntsLinkMobile", pageName, null);
	}

	public boolean isbackToAcntsLinkMobile() {
		System.out.println("displayed");
		return PageParser.isElementPresent("backToAcntsLinkMobile", pageName, null);
	}

	public void clickingOnbackToAcntsLink() {
		SeleniumUtil.click(backToAcntsLink());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	public List<WebElement> AccSettingFIName() {
		return SeleniumUtil.getWebElements(d, "AccSettingFIName", pageName, null);
	}

	public List<WebElement> AccSettingContainerAccountName() {
		return SeleniumUtil.getWebElements(d, "AccSettingContainerAccountName", pageName, null);
	}

	public List<WebElement> AccSettingContainerAccountNum() {
		return SeleniumUtil.getWebElements(d, "AccSettingContainerAccountNum", pageName, null);
	}

	public List<WebElement> AccSettingFIEdit() {
		return SeleniumUtil.getWebElements(d, "AccSettingFIEdit", pageName, null);
	}

	public List<WebElement> AccSettingFIDelete() {
		return SeleniumUtil.getWebElements(d, "AccSettingFIDelete", pageName, null);
	}

	public List<WebElement> AccSettingFIGoTOSite() {
		return SeleniumUtil.getWebElements(d, "AccSettingFIGoTOSite", pageName, null);
	}

	public List<WebElement> AccSettingContainerAccountNumMobile() {
		return SeleniumUtil.getWebElements(d, "AccSettingContainerAccountNumMobile", pageName, null);
	}

	public WebElement openAcntSettingMobile() {
		return SeleniumUtil.getWebElement(d, "openAcntSettingMobile", "AccountsPage", frameName);
	}

	public void navigateToManualAccounts() {
		click(manualAccountsInSideBar);
		waitUntilSpinnerWheelIsInvisible();
		waitFor(2);
	}
	
	public void deleteAccount(String accountName) {
		clickOnAccountSeetingsOfGivenAccountName(accountName);
		waitFor(1);
		clickOnDeleteButtonInAccountSettingsPopup();
		waitFor(1);
		click(confirmDeleteCheckBox);
		waitFor(1);
		click(deleteButtonInConfirmDeletePopup);
		waitFor(5);
		waitUntilSpinnerWheelIsInvisible();
		waitUntilToastMessageIsDisappeared();
	}
	
	public void clickOnAccountSeetingsOfGivenAccountName(String accountName) {
		click(By.xpath(xpathForAccountSettingsbutton.replace("#####", accountName)));
	}
	
	public void clickOnDeleteButtonInAccountSettingsPopup() {
		click(deleteAccountButtonInAccountSettingsPopup);
	}

}
