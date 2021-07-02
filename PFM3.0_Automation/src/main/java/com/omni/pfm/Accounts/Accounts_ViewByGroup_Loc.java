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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ViewByGroup_Loc extends SeleniumUtil {
	Logger logger = LoggerFactory.getLogger(Accounts_ViewByGroup_Loc.class);
	public WebDriver d = null;
	static String frameName = null;
	static String pageName = "AccountsPage";

	public Accounts_ViewByGroup_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement groupType() {
		return getWebElement(d, "grpType", pageName, frameName);

	}

	public WebElement getCreateGroup() {
		return getWebElement(d, "createGroupBtn", pageName, frameName);

	}

	public WebElement groupName() {
		return getWebElement(d, "groupNm", pageName, frameName);

	}

	public void typeGroupName(String textToBeinseterd) {
		groupName().sendKeys(textToBeinseterd);

	}

	public void selectCheckBox()

	{
		waitForElement(null, 2000);
		List<WebElement> l = getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 0; i < 3; i++) {

			l.get(i).click();

			waitForPageToLoad(1000);

		}

	}

	public void selectCheckBoxAll() {
		waitForElement(null, 2000);
		List<WebElement> l = getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 0; i < 2; i++) {

			l.get(i).click();

			waitForPageToLoad(1000);

		}

	}

	public void selectCheckBox2()

	{
		waitForElement(null, 2000);
		List<WebElement> l = getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 3; i < 6; i++) {

			l.get(i).click();

			waitForPageToLoad(1000);

		}

	}

	public void selectCheckBox3()

	{
		waitForElement(null, 2000);
		List<WebElement> l = getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 6; i < 9; i++) {

			l.get(i).click();

			waitForPageToLoad(1000);

		}

	}

	public WebElement createUpdateGroupBtm() {
		return getWebElement(d, "createUpdateGroup", pageName, frameName);

	}

	public WebElement moreButon() {
		return getVisibileWebElement(d, "moreBtn", pageName, frameName);
	}

	// added by Mallika
	public boolean ismoreBtn_mob_AMTPresent() {
		return PageParser.isElementPresent("moreBtn_mob", "AccountsPage", null);
	}

	public WebElement moreBtn_mob() {
		return getVisibileWebElement(d, "moreBtn_mob", pageName, frameName);
	}

	public WebElement CreateBtn1() {
		// return getVisibileWebElement(d, "createMoreGroupBtm", pageName,
		// frameName);
		return d.findElement(By.xpath("//a[contains(@id,'menu-create-group-btn')]"));
	}

	public List<WebElement> DeleteIcon1() {
		return getWebElements(d, "deleteIcon1", pageName, null);
	}

	public List<WebElement> EditIcon1() {
		return getWebElements(d, "editIcon1", pageName, null);
	}

	public List<WebElement> containerName() {
		return getWebElements(d, "containerHeader", pageName, null);
	}

	public List<WebElement> accountLevelRefresh() {
		return getWebElements(d, "refreshIcon_AVF", pageName, frameName);
	}

	public List<WebElement> expandedArrow() {
		return getWebElements(d, "expandedArrow", pageName, frameName);
	}

	public List<WebElement> collapseArrow() {
		return getWebElements(d, "collapseArrow", pageName, frameName);
	}

	public WebElement deleteGroupOption() {
		return getVisibileWebElement(d, "deleteGroupbtn", pageName, null);
	}

	public List<WebElement> AccountGroupTypeView_GrpAccName() {
		return getWebElements(d, "AccountGroupTypeView_GrpAccName", pageName, null);
	}

	public List<WebElement> AccountGroupTypeView_GrpNameHeaderList() {
		return getWebElements(d, "AccountGroupTypeView_GrpNameHeaderList", pageName, null);
	}

	public WebElement AccountGroupTypeView_GrpAccSelect(String accountName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountGroupTypeView_GrpAccSelect");
		xpath = xpath.replaceAll("AccountName", accountName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement AccountGroupTypeView_GrpNameHeader(String groupIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountGroupTypeView_GrpNameHeader");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement AccountGroupTypeView_AccountName(String groupIndex, String acctTypeIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountGroupTypeView_AccountName");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		xpath = xpath.replaceAll("acctTypeIndex", acctTypeIndex);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement AccountGroupTypeView_AccountSite(String groupIndex, String acctTypeIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountGroupTypeView_AccountSite");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		xpath = xpath.replaceAll("acctTypeIndex", acctTypeIndex);
		return d.findElement(By.xpath(xpath));
	}
	
	public String returnAccountNumber(String groupIndex,String acctTypeIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountGroupTypeView_AccountNum");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		xpath = xpath.replaceAll("acctTypeIndex", acctTypeIndex);
		moveToElement(d.findElement(By.xpath(xpath)));
		waitFor(1);
		String titleAttribute = getAttribute(By.xpath(xpath),"title");
		String accountNumber = titleAttribute.isEmpty() ? getText(By.xpath(xpath)) : titleAttribute;
		logger.info("Account number :: " + accountNumber);
		return accountNumber;
	}

	public WebElement AccountGroupTypeView_AccountBal(String groupIndex, String acctTypeIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountGroupTypeView_AccountBal");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		xpath = xpath.replaceAll("acctTypeIndex", acctTypeIndex);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement AccountGroupTypeView_AccountNickName(String groupIndex, String acctTypeIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountGroupTypeView_AccountNickName");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		xpath = xpath.replaceAll("acctTypeIndex", acctTypeIndex);
		return d.findElement(By.xpath(xpath));
	}

	public void clikcCreatgroup() {
		WebDriverWait wait = new WebDriverWait(d, 60);
		click(groupType());
		wait.until(ExpectedConditions.visibilityOf(getCreateGroup())).click();;
	}

	public void createMoreGroup() {
		waitForPageToLoad();
		if (this.ismoreBtn_mob_AMTPresent()) {
			click(this.moreBtn_mob());
		} else {
			click(this.moreButon());
		}
		waitForPageToLoad();
		click(this.CreateBtn1());
		waitUntilSpinnerWheelIsInvisible();

	}

	public void createGroup(String groupName, String[] accountName) {
		groupName().clear();
		typeGroupName(groupName);
		for (int i = 0; i < accountName.length; i++) {
			click(AccountGroupTypeView_GrpAccSelect(accountName[i]));
			waitForPageToLoad(1000);
		}
		click(createUpdateGroupBtm());
		waitFor(2);
		waitUntilSpinnerWheelIsInvisible();
	}

	public void verifyCheckBoxByUsingAcntNumber(String[] accountName) {
		String xpath = getVisibileElementXPath(d, "AccountGroups", frameName, "checkBoxByUsingAcntNumber");
		for (int i = 0; i < accountName.length; i++) {
			xpath = xpath.replaceAll("acntNumbers", accountName[i]);
			WebElement wb = d.findElement(By.xpath(xpath));
			String getValue = wb.getAttribute("aria-checked").trim();
			Assert.assertEquals(getValue, "true");
		}
	}

	public void createGroupMore(String[] groupNameProperty, String[] groupsAccountPropertyName) {
		for (int i = 0; i < groupNameProperty.length; i++) {
			this.createMoreGroup();
			this.createGroup(PropsUtil.getDataPropertyValue(groupNameProperty[i]),
					PropsUtil.getDataPropertyValue(groupsAccountPropertyName[i]).split(","));
		}
	}

	public void updateGroup(String[] accountName) {

		for (int i = 0; i < accountName.length; i++) {
			click(AccountGroupTypeView_GrpAccSelect(accountName[i]));
			waitUntilSpinnerWheelIsInvisible();
		}
		click(createUpdateGroupBtm());
		waitUntilSpinnerWheelIsInvisible();

	}

	public void refreshPage() {
		for (int i = 0; i < 6; i++) {
			d.navigate().refresh();
			waitUntilSpinnerWheelIsInvisible();
		}
	}

	public WebElement AccountGroupTypeView_AccountNumMobile(String groupIndex, String acctTypeIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountGroupTypeView_AccountNumMobile");
		xpath = xpath.replaceAll("groupIndex", groupIndex);
		xpath = xpath.replaceAll("acctTypeIndex", acctTypeIndex);
		return d.findElement(By.xpath(xpath));
	}
}
