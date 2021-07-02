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
import org.testng.Assert;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_AccountSettingPopUp_Loc extends SeleniumUtil {
	Logger logger = LoggerFactory.getLogger(Accounts_AccountSettingPopUp_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_AccountSettingPopUp_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement popUpHeader() {
		return getWebElement(d, "popUpHeader", pageName, null);
	}

	public WebElement acntNameLabel() {
		return getWebElement(d, "acntNameLabel", pageName, null);
	}

	public WebElement acntNameValue() {
		return getWebElement(d, "acntNameValue", pageName, null);
	}

	public WebElement acntNickNameLabel() {
		return getWebElement(d, "acntNickNameLabel", pageName, null);
	}

	public WebElement acntNickNameLabelmobile() {
		return getWebElement(d, "acntNickNameLabelmobile", pageName, null);
	}

	public WebElement acntNickNameTxtBox() {
		return getWebElement(d, "acntNickNameTxtBox", pageName, null);
	}

	public WebElement memoLabel() {
		return getWebElement(d, "memoLabel", pageName, null);
	}

	public WebElement memoTxtBox() {
		return getWebElement(d, "memoTxtBox", pageName, null);
	}

	public WebElement tagAllTransactnLabel() {
		return getWebElement(d, "tagAllTransactnLabel", pageName, null);
	}

	public WebElement tagNameTxtBox() {
		return getWebElement(d, "tagNameTxtBox", pageName, null);
	}

	public WebElement addTagBtn() {
		return getWebElement(d, "addTagBtn", pageName, null);
	}

	public WebElement catAllTransactnLabel() {
		return getWebElement(d, "catAllTransactn", pageName, null);
	}

	public WebElement applyCatToPastTransactnLabel() {
		return getWebElement(d, "applyCatToPastTransactnLabel", pageName, null);
	}

	public WebElement applyTagToPastTransactnLabel() {
		return getWebElement(d, "applyTagToPastTransactnLabel", pageName, null);
	}

	public WebElement showAcntInSummaryLabel() {
		return getWebElement(d, "showAcntInSummaryLabel", pageName, null);
	}

	public WebElement DeleteBtn() {
		return getWebElement(d, "DeleteBtn", pageName, null);
	}

	public WebElement saveChangesBtn() {
		return getWebElement(d, "saveChangesBtn", pageName, null);
	}

	public WebElement closeAcntBtn() {
		return getWebElement(d, "closeAcntBtn", pageName, null);
	}

	public WebElement applyCatToPastToggle() {
		return getWebElement(d, "applyCatToPastToggle", pageName, null);
	}

	public WebElement applyTagToPastToggle() {
		return getWebElement(d, "applyTagToPastToggle", pageName, null);
	}

	public WebElement showAcntInSummaryToggle() {
		return getWebElement(d, "showAcntInSummaryToggle", pageName, null);
	}

	public WebElement popupCrossIcon() {
		return getWebElement(d, "popupCrossIcon", pageName, null);
	}

	public List<WebElement> byDefaultCategoryDDHeader() {
		return getWebElements(d, "byDefaultCategoryDDHeader", pageName, null);
	}

	public WebElement searchForCatBox() {
		return getWebElement(d, "searchForCatBox", pageName, frameName);
	}

	public WebElement noCatAvailableMsg() {
		return getWebElement(d, "noCatAvailableMsg", pageName, frameName);
	}

	public WebElement closeCategory() {
		return getWebElement(d, "closeCategory", pageName, frameName);
	}

	public WebElement catgoryList(int MLC, int HLC) {

		String abC = getVisibileElementXPath(d, pageName, frameName, "catgoryListDD");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);

		return d.findElement(By.xpath(abC));

	}

	public List<WebElement> totalBankAcnt() {
		return getWebElements(d, "totalBankAcnt", pageName, frameName);
	}

	public List<WebElement> suggestionnTags() {
		return getWebElements(d, "suggestionnTags", pageName, frameName);
	}

	public List<WebElement> deleteTag() {
		return getWebElements(d, "deleteTag", pageName, frameName);
	}

	public List<WebElement> addedTags() {
		return getWebElements(d, "addedTags", pageName, frameName);
	}

	public static final By tagsPresent = getByObject(pageName, frameName, "addedTags");

	public List<WebElement> TotalAccountRows() {
		return getWebElements(d, "TotalAccountRows", pageName, frameName);
	}

	public List<WebElement> totalTransactionRows() {
		return getWebElements(d, "totalTransactionRows", pageName, frameName);
	}

	public List<WebElement> existingTransactionTags() {
		return getWebElements(d, "existingTransactionTags", pageName, frameName);
	}

	public List<WebElement> projectetTxnDropDown() {
		return getWebElements(d, "projectetTxnDropDown", pageName, frameName);
	}

	public WebElement updateTransactionBtn() {
		return getWebElement(d, "saveTransaction", pageName, frameName);
	}

	public void navigatingToExistingTransac() {
		click(TotalAccountRows().get(0));
		waitForPageToLoad(3000);
		click(projectetTxnDropDown().get(0));
		waitForPageToLoad(3000);
		click(totalTransactionRows().get(1));
		waitForPageToLoad(2000);
	}

	public void navigatingToTxn() {
		click(totalTransactionRows().get(1));
		waitForPageToLoad();
	}

	public WebElement updatedSuccessMsg() {
		return getWebElement(d, "updatedSuccessMsg", pageName, frameName);
	}

	public List<WebElement> settingAtAcntLevel() {
		return getWebElements(d, "settingAtAcntLevel", "AccountSettings", frameName);
	}

	public List<WebElement> updatedCurrentBalanceInTransactn() {
		return getWebElements(d, "updatedCurrentBalanceInTransactn", pageName, frameName);
	}

	public void verifyTagSuggestionValues() {
		String[] suggestionValues = PropsUtil.getDataPropertyValue("TagsSuggestionValues").split(",");
		for (int i = 0; i < suggestionnTags().size(); i++) {
			String actual = suggestionnTags().get(i).getText().trim();
			Assert.assertEquals(actual, suggestionValues[i]);
		}
	}

	public List<WebElement> acntNumbers() {
		return getWebElements(d, "acntNumbers", pageName, frameName);
	}

	public List<WebElement> currentBalanceAmt() {
		return getWebElements(d, "currentBalanceAmt", pageName, frameName);
	}

	public List<WebElement> addedNickNameInAS() {
		return getWebElements(d, "addedNickNameInAS", pageName, frameName);
	}

	public WebElement clearTagCrossIcon() {
		return getWebElement(d, "clearTagCrossIcon", pageName, frameName);
	}

	public void closePopup() {
		click(popupCrossIcon());
	}

	public void clickOnCloseAcntBtn() {
		click(closeAcntBtn());
	}

	public void clickOnDeleteButton() {
		click(DeleteBtn());
	}

	public List<WebElement> closedAcntClosedText() {
		return getWebElements(d, "closedAcntClosedText", "AccountSettings", frameName);
	}

	public void clickOnAccountSettingLink(String accountTypeIndex, String acctNameIndex) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountSetting_SettingLink");
		xpath = xpath.replaceAll("acctTypeIndex", accountTypeIndex);
		xpath = xpath.replaceAll("acctNameIndex", acctNameIndex);
		click(By.xpath(xpath));
	}

	public WebElement AccountSetting_AccountCloseConfirm() {
		return getWebElement(d, "AccountSetting_AccountCloseConfirm", pageName, frameName);
	}

	public void nickName(String nickName) {
		this.acntNickNameTxtBox().clear();
		this.acntNickNameTxtBox().sendKeys(nickName);
		click(this.saveChangesBtn());
	}

	public void closeAccount(String accountTypeIndex, String[] acctNameIndex) {
		for (int i = 0; i < acctNameIndex.length; i++) {
			clickOnAccountSettingLink(accountTypeIndex, acctNameIndex[i]);
			waitForPageToLoad();
			this.clickOnCloseAcntBtn();
			click(this.AccountSetting_AccountCloseConfirm());
			waitForPageToLoad();
		}
	}

	public void closeAccountON(String accountTypeIndex, String[] acctNameIndex) {
		for (int i = 0; i < acctNameIndex.length; i++) {
			clickOnAccountSettingLink(accountTypeIndex, acctNameIndex[i]);
			waitForPageToLoad();
			click(this.showAcntInSummaryToggle());
			click(this.saveChangesBtn());
			waitForPageToLoad();
			waitUntilToastMessageIsDisappeared();
		}
	}

	public WebElement showAcntInSummaryToggleButton() {
		return getWebElement(d, "showAcntInSummaryToggleButton", pageName, frameName);
	}

	public WebElement openAcntSettingMobile() {
		return getWebElement(d, "openAcntSettingMobile", "AccountsPage", frameName);
	}
	
	public WebElement accountslayout() {
			return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
		}
}
