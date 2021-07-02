/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsViewByFI_Loc extends SeleniumUtil {
	Logger logger = LoggerFactory.getLogger(AccountsViewByFI_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public AccountsViewByFI_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> AccountFI_AccountType_AccountNumMobile(String AccountType) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountNumMobile");
		getXpath = getXpath.replaceAll("acctTypeIndex", AccountType);
		return d.findElements(By.xpath(getXpath));

	}

	public WebElement AccountTypeView_AccountNum(String acctTypeIndex, String acctNameIndex) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountNum");
		getXpath = getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath = getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	}

	public List<WebElement> AccountsContainerName() {
		return getWebElements(d, "AccountsContainerName", pageName, null);
	}

	public List<WebElement> AccountsContainerAcntType() {
		return getWebElements(d, "AccountsContainerAcntType", pageName, null);
	}

	public List<WebElement> AccountsContainerTotalAmt() {
		return getWebElements(d, "AccountsContainerTotalAmt", pageName, null);
	}

	public List<WebElement> AccountsAcntNames() {
		return getWebElements(d, "AccountsAcntNames", pageName, null);
	}

	public List<WebElement> AccountsAcntType() {
		return getWebElements(d, "AccountsAcntType", pageName, null);
	}

	public List<WebElement> AccountsAcntAmount() {
		return getWebElements(d, "AccountsAcntAmount", pageName, null);
	}

	public List<WebElement> AccountsMaxMinDD() {
		return getWebElements(d, "AccountsMaxMinDD", pageName, null);
	}

	public List<WebElement> AccountsUpdateTimeInfo() {
		return getWebElements(d, "AccountsUpdateTimeInfo", pageName, null);
	}

	public List<WebElement> AccountsAcntLevelRefreshIcon() {
		return getWebElements(d, "AccountsAcntLevelRefreshIcon", pageName, null);
	}

	public WebElement AccountsSettingUnderMore() {
		return getWebElement(d, "AccountsSettingUnderMore", pageName, null);
	}

	public WebElement AccountsFeatureTourUnderMore() {
		return getWebElement(d, "AccountsFeatureTourUnderMore", pageName, null);
	}

	public WebElement manualAcntBalance() {
		return getWebElement(d, "manualAcntBalance", pageName, null);
	}

	public WebElement RewardAcntBalance() {
		return getWebElement(d, "RewardAcntBalance", pageName, null);
	}

	public WebElement BillsDueInfo() {
		return getWebElement(d, "BillsDueInfo", pageName, null);
	}

	public WebElement RealEstatesAcntBalance() {
		return getWebElement(d, "RealEstatesAcntBalance", pageName, null);
	}

	public WebElement nickNameForLiability() {
		return getWebElement(d, "nickNameForLiability", pageName, null);
	}

	public WebElement nickNameForAsset() {
		return getWebElement(d, "nickNameForAsset", pageName, null);
	}

	public void verifyOrderOfAcntContainer(String propValue) {
		List<WebElement> l = getWebElements(d, "AccountsContainerAcntType", pageName, frameName);
		CommonUtils.assertContainsListElements(propValue, l);
	}

	public void verifyOrderOfAcntUnderAcntContainer(String propValue) {

		waitForPageToLoad();
		String expected[] = PropsUtil.getDataPropertyValue(propValue).split(",");
		for (int i = 0; i < AccountsAcntNames().size(); i++) {
			List<WebElement> wb = d.findElements(By.xpath("//*[@autoid='accounts_container_lbl_AccName']"));
			String actual = wb.get(i).getText().trim();
			Assert.assertEquals(expected[i], actual);
		}
	}

	public WebElement getTotalBalanceLocator(String AcntContainerIndex) {

		String getXpath = getVisibileElementXPath(d, pageName, frameName, "totalBalanceAtAcntLevel");
		getXpath = getXpath.replaceAll("AcntLevelContainer", AcntContainerIndex);
		return d.findElement(By.xpath(getXpath));
	}

	public List<WebElement> getAcntsPresentUnderContainer(String givenContainerName) {

		String getXpath = getVisibileElementXPath(d, pageName, frameName, "ContainerContainsAcnts");
		getXpath = getXpath.replaceAll("ContainerName", givenContainerName);
		return d.findElements(By.xpath(getXpath));
	}

	public void verifyAcntsUnderContainer(String givenContainerName, String propValue) {
		List<WebElement> l = getAcntsPresentUnderContainer(givenContainerName);
		CommonUtils.assertEqualsListElements(propValue, l);
	}

	public List<WebElement> getManualAcntsPresentUnderContainer(String givenContainerName) {

		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"ContainerContainsRealEstateAcnts");
		getXpath = getXpath.replaceAll("ContainerName", givenContainerName);
		return d.findElements(By.xpath(getXpath));
	}

	public void verifyManualBankAcntsUnderContainer(String propValue) {
		List<WebElement> l = getWebElements(d, "ContainerContainsManualAcnts", pageName, frameName);
		CommonUtils.assertEqualsListElements(propValue, l);
	}

	public void verifyManualAcntsUnderContainer(String givenContainerName, String propValue) {
		List<WebElement> l = getManualAcntsPresentUnderContainer(givenContainerName);
		CommonUtils.assertEqualsListElements(propValue, l);
	}

	public void verifyManualAcntFormat() {
		List<WebElement> l = getWebElements(d, "manualAcntFormat", pageName, frameName);
		CommonUtils.assertContainsListElements("ManaulCashAcntFormat", l);
	}

	public WebElement moreButtonAtAcntLevel(String AcntName) {

		String getXpath = getVisibileElementXPath(d, pageName, frameName, "MoreButtonAtAcntLevel");
		getXpath = getXpath.replaceAll("accountName", AcntName);
		return d.findElement(By.xpath(getXpath));
	}

	public void clickingOnMoreBtn(String accountName) {
		click(moreButtonAtAcntLevel(accountName));
		waitForPageToLoad(2000);
	}

	public List<WebElement> MoreButtonOptions(String AcntName) {

		String getXpath = getVisibileElementXPath(d, pageName, frameName, "MoreButtonOptions");
		getXpath = getXpath.replaceAll("accountName", AcntName);
		return d.findElements(By.xpath(getXpath));
	}

	public void verifyManualAcntFormat(String AcntName, String propValue) {
		List<WebElement> l = MoreButtonOptions(AcntName);
		CommonUtils.assertContainsListElements(propValue, l);
	}

	public WebElement AccountFI_AccountsTypeName(String AccountType) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName, "AccountFI_AccountsTypeName");
		getXpath = getXpath.replaceAll("acctTypeIndex", AccountType);
		return d.findElement(By.xpath(getXpath));

	}

	public List<WebElement> AccountFI_AccountType_AccountName(String AccountType) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountName");
		getXpath = getXpath.replaceAll("acctTypeIndex", AccountType);
		return d.findElements(By.xpath(getXpath));

	}

	public List<String> returnAccountNumbers(String AccountType) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountNum");
		getXpath = getXpath.replaceAll("acctTypeIndex", AccountType);
		List<WebElement> accountNumberElements =  d.findElements(By.xpath(getXpath));
		List<String> accountNumbers = new ArrayList<String>();
		for(WebElement element :accountNumberElements) {
			moveToElement(element);
			waitFor(1);
			String titleAttribute = getAttribute(element,"title");
			accountNumbers.add(titleAttribute.isEmpty() ? element.getText() : titleAttribute);
		}
		logger.info("Account numbers :: " + accountNumbers);
		return accountNumbers;
	}

	public List<WebElement> AccountFI_AccountType_AccountBal(String AccountType) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountBal");
		getXpath = getXpath.replaceAll("acctTypeIndex", AccountType);
		return d.findElements(By.xpath(getXpath));

	}

	public String returnNickName(String AccountType) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountNickName");
		getXpath = getXpath.replaceAll("acctTypeIndex", AccountType);
		return getText(By.xpath(getXpath));

	}

	public void clickOnMoreButtonForGivenAccount(String AccountType, String accountNameIndex) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountMoreBtn");
		getXpath = getXpath.replace("acctTypeIndex", AccountType);
		getXpath = getXpath.replace("acctNameIndex", accountNameIndex);
		click(By.xpath(getXpath));

	}

	public WebElement AccountFI_AccountType_AccountMoreBtnMenu(String AccountType, String accountNameIndex,
			String moreMenuName) {
		String getXpath = getVisibileElementXPath(d, pageName, frameName,
				"AccountFI_AccountType_AccountMoreBtnMenu");
		getXpath = getXpath.replace("acctTypeIndex", AccountType);
		getXpath = getXpath.replace("acctNameIndex", accountNameIndex);
		getXpath = getXpath.replace("menuName", moreMenuName);
		return d.findElement(By.xpath(getXpath));

	}

	public void clickAccountSetting(String AccountType, String accountNameIndex, String moreMenuName) {
		clickOnMoreButtonForGivenAccount(AccountType, accountNameIndex);
		click(this.AccountFI_AccountType_AccountMoreBtnMenu(AccountType, accountNameIndex, moreMenuName));
		waitForPageToLoad();
	}
	
	public WebElement accountslayout() {
		return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
	}
}
