/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.AccountSettings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountGroup_Settings_Loc {

	static Logger logger = LoggerFactory.getLogger(AccountGroup_Settings_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public AccountGroup_Settings_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement errorDuplicategroupTxt() {
		return SeleniumUtil.getVisibileWebElement(d, "errorDuplicategroupTxtAGS", "AccountGroups", null);
	}

	public WebElement closeDialog() {
		return SeleniumUtil.getVisibileWebElement(d, "closeDialogAGS", "AccountGroups", null);
	}

	public WebElement emptyGroupMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "emptyGroupMessageAGS", "AccountGroups", null);

	}

	public WebElement createGroupButton() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupButtonAGS", "AccountsPage", null);
	}

	public WebElement groupNameTxtBox() {
		return SeleniumUtil.getVisibileWebElement(d, "groupNameTxtBoxAGS", "AccountGroups", null);

	}

	public WebElement createGroupBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupBtnAGS", "AccountGroups", null);
	}

	public List<WebElement> AccnameListinAccSet() {

		return SeleniumUtil.getWebElements(d, "AccnameListinAccSet", "AccountGroups", null);
	}

	public WebElement createGroupBtnInApp() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupBtnInAppAGS", "AccountGroups", null);
	}

	public List<WebElement> AccGroupsMenu() {
		return SeleniumUtil.getWebElements(d, "AccGroupsMenu", "AccountGroups", null);
	}

	public WebElement NickNameBox() {
		return SeleniumUtil.getWebElement(d, "NickNameBox", "AccountGroups", null);
	}

	public List<WebElement> checkNickNameinAccFin() {
		return SeleniumUtil.getWebElements(d, "checkNickNameinAccFin", "AccountGroups", null);
	}

	public WebElement SaveBtnInSettings() {
		return SeleniumUtil.getWebElement(d, "SaveBtnInSettings", "AccountGroups", null);
	}

	public List<WebElement> AccSettingsIcon() {
		return SeleniumUtil.getWebElements(d, "AccSettingsIcon", "AccountGroups", null);
	}

	public List<WebElement> AccSettingsMenu() {
		return SeleniumUtil.getWebElements(d, "AccSettingsMenuClick", "AccountGroups", null);
	}

	public WebElement SettingsFin() {
		return SeleniumUtil.getWebElement(d, "SettingsFin", "AccountGroups", null);
	}

	public List<WebElement> transAccinAccFin() {
		return SeleniumUtil.getWebElements(d, "transAccinAccFin", "AccountGroups", null);
	}

	public WebElement createOrUpdateGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "createOrUpdateGroupAGS", "AccountGroups", null);
	}

	public WebElement pageTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "pageTitleAGS", "AccountGroups", null);
	}

	public WebElement AccSetIconinSet() {
		return SeleniumUtil.getVisibileWebElement(d, "AccSetIconinSet", "AccountsPage", null);
	}

	public WebElement ConfirmDelCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "ConfirmDelCheckbox", "AccountsPage", null);
	}

	public WebElement DelCheckboxTick() {
		return SeleniumUtil.getVisibileWebElement(d, "DelCheckboxTick", "AccountsPage", null);
	}

	public WebElement successMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "successMsgAGS", "AccountGroups", null);
	}

	public WebElement DelAccIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "DelAccIcon", "AccountsPage", null);
	}

	public WebElement GroupName() {
		return SeleniumUtil.getVisibileWebElement(d, "GroupNameAGS", "AccountGroups", null);
	}

	public WebElement createGroupPopUpHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupPopUpHeaderAGS", "AccountGroups", null);
	}
	// change //

	public List<WebElement> getAccountPopUpText() {
		List<WebElement> n = d.findElements(By.cssSelector(".y-text-capitalize"));

		return n;
	}

	public WebElement newAccountGroupPopup() {
		return SeleniumUtil.getVisibileWebElement(d, "newAccountGroupPopupAGS", "AccountGroups", null);
	}

	public WebElement plusIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "newAccountGroupPopupAGSMobile", "AccountGroups", null);
	}

	public boolean isplusIconDisplayedMobile() {
		return PageParser.isElementPresent("newAccountGroupPopupAGSMobile", "AccountGroups", null);
	}

	public WebElement groupHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "groupHeaderAGS", "AccountsPage", null);
	}

	public WebElement Group2Name() {
		return SeleniumUtil.getVisibileWebElement(d, "Group2NameAGS", "AccountGroups", null);
	}

	public WebElement Group2NameHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "Group2NameAGSHeader", "AccountGroups", null);
	}

	public WebElement confirmDeleteText() {
		return SeleniumUtil.getVisibileWebElement(d, "confirmDeleteTextAGS", "AccountGroups", null);
	}

	public WebElement saveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtnAGS", "AccountGroups", null);

	}

	public WebElement deleteGroupButton(String name) {

		// name = name.toLowerCase();
		return d.findElement(By.xpath("//a[contains(@id,'delete-icon')]"));
		// return d.findElement(By.xpath("//a[contains(@aria-label,'Delete " + name +
		// "')]"));

	}

	public WebElement groupList(String Gname) {

		/*
		 * return
		 * d.findElement(By.xpath("(//aside[@id='leftSection']//a[contains(text(),'" +
		 * Gname + "')])"));
		 */

		// Updated By MRQA
		// return d.findElement(By.xpath("(//a[contains(text(),'" + Gname + "')])"));

		return d.findElement(By.xpath("(//a[contains(text(),'" + Gname + "')])"));

	}

	public WebElement editButton(String name) {

		return d.findElement(By.xpath("//a[contains(@aria-label,'dit " + name + "')]"));
	}

	public WebElement deleteCreateGrouptext() {

		return SeleniumUtil.getVisibileWebElement(d, "deleteCreateGrouptextAGS", "AccountsPage", null);

	}

	public WebElement verifyGroupPresent(String name) {

		name = name.toLowerCase();

		return d.findElement(By.xpath("//span[contains(text(),'" + name + "')]"));

	}

	public WebElement verifyGroupDeletedText(String groupName) {

		return d.findElement(By.xpath("//p[contains(text(),'" + groupName + " Deleted!')]"));

	}

	public WebElement verifyGroupUpdatedText(String groupName) {

		return d.findElement(By.xpath("//p[contains(text(),'" + groupName + " has been updated')]"));

	}

	public WebElement cancelButtonInGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelButtonInGroupAGS", "AccountGroups", null);
	}

	public By testAutomationGrp() {
		return SeleniumUtil.getByObject("AccountGroups", null, "TestAutomationGroup");
	}

	public WebElement deleteGroupOption() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteGroupOptionAGS", "AccountGroups", null);
	}

	public List<WebElement> getAccountOptionChckBox() {

		String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountGroups", null, "getAccountOptionChckBoxAGS");

		List<WebElement> l = d.findElements(By.xpath(varX));

		System.out.println(l.get(4).getText());

		return l;

	}

	/*
	 * public List<WebElement> AccountGroupInAccFinapp() {
	 * 
	 * String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountsPage", null,
	 * "AccountGroupInAccFinappAGS");
	 * 
	 * List<WebElement> w = d.findElements(By.xpath(varX));
	 * 
	 * return w; }
	 */

	public List<WebElement> AccountGroupInAccFinapp() {
		return SeleniumUtil.getWebElements(d, "AccountGroupInAccFinappAGS", "AccountsPage", null);
	}

	public WebElement accGroupTab() {
		return SeleniumUtil.getVisibileWebElement(d, "accGroupTabAGS", "AccountsPage", null);
	}

	public WebElement CreateGrp() {
		return SeleniumUtil.getVisibileWebElement(d, "CreateGrpAGS", "AccountsPage", null);
	}

	public String getGroupnames() {

		String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountGroups", null, "getGroupnamesAGS");

		List<WebElement> l = d.findElements(By.xpath(varX));
		String group = null;
		for (int i = 0; i < l.size(); i++) {
			group = l.get(i).getText().replace("\n", ",").toUpperCase();
		}

		return group;

	}

	public List<WebElement> CreatedGroupList() {
		return SeleniumUtil.getWebElements(d, "getGroupnamesAGS", "AccountGroups", null);
	}

	public WebElement createGroupButtonTwo() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupButtonTwoAGS", "AccountGroups", null);
	}

	public String getTomorowsDate() {
		/*
		 * DateFormat dateFormat = new SimpleDateFormat("dd"); Date date = new Date();
		 * System.out.println(dateFormat.format(date)); return dateFormat.format(date);
		 */
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd");

		String tomorrowAsString = dateFormat.format(tomorrow);
		System.out.println(tomorrowAsString);
		return tomorrowAsString;

	}

	public WebElement verifyBackToAccountGroupList() {
		return SeleniumUtil.getVisibileWebElement(d, "backToAccountGroupList", "AccountGroups", null);
	}

	public WebElement AccGroupSetting_GroupDeleteButton() {
		return SeleniumUtil.getVisibileWebElement(d, "AccGroupSetting_GroupDeleteButton", "AccountGroups", null);
	}

	public WebElement AccGroupSetting_GroupDeletePopUpdeleteBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "AccGroupSetting_GroupDeletePopUpdeleteBtn", "AccountGroups",
				null);
	}

	public WebElement AccountGroupEditBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountGroupEditBtn", "AccountGroups", null);
	}

	public WebElement AccGroupSetting_GroupListName(String groupName) {
		String xpath = SeleniumUtil.getVisibileElementXPath(d, "AccountGroups", null, "AccGroupSetting_GroupListName");
		xpath = xpath.replaceAll("groupName", groupName);
		logger.info("Xpath for group name :: " + xpath);
		return d.findElement(By.xpath(xpath));
	}

	public void clickOnGroup(String groupName) {
		SeleniumUtil.click(this.AccGroupSetting_GroupListName(groupName));
	}

	public void deleteAccountGroup() {
		SeleniumUtil.click(this.AccGroupSetting_GroupDeleteButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.AccGroupSetting_GroupDeletePopUpdeleteBtn());
		SeleniumUtil.waitForPageToLoad();
	}

}
