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

public class Accounts_Details_Loc extends SeleniumUtil {
	Logger logger = LoggerFactory.getLogger(Accounts_TrendsPopup_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;
	public String xpathForAmountForGivenAccountName = "//*[@autoid=\"accounts_container_lbl_AccName\" and contains(text(),\"${Acc.Name}\")]/ancestor::*[contains(@class,\"account-details-action\")]//*[@autoid=\"accounts_lbl_AccAmt\"]";

	public Accounts_Details_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement containerAccounts(String containerNumber, String subContainer, String rowContainer) {
		String webElement = getVisibileElementXPath(d, pageName, null, "containerGenericRows");
		webElement = webElement.replaceAll("containerNumber", containerNumber);
		webElement = webElement.replaceAll("subContainer", subContainer);
		webElement = webElement.replaceAll("rowContainer", rowContainer);

		return d.findElement(By.xpath(webElement));
	}

	public List<WebElement> acntDetailsInTransactn() {
		return getWebElements(d, "acntDetailsInTransactn", pageName, frameName);
	}

	public List<WebElement> acntDetails2InTransactn() {
		return getWebElements(d, "acntDetails2InTransactn", pageName, frameName);
	}

	public boolean isUpdatedInfoDisplayed() {
		return acntDetails2InTransactn().get(2).isDisplayed();
	}

	public WebElement backToAcntsLink() {
		return getWebElement(d, "backToAcntsLink", pageName, frameName);
	}

	public void clickingOnBackToAcntsBtn() {
		click(backToAcntsLink());
		waitForPageToLoad(2000);
	}

	public void verifyAcntDetailsInTxn(String propValue) {
		String expected[] = PropsUtil.getDataPropertyValue(propValue).split(":");
		for (int i = 0; i < acntDetailsInTransactn().size(); i++) {
			String actual = acntDetailsInTransactn().get(i).getText().trim();
			Assert.assertEquals(expected[i], actual);
		}
	}

	public List<WebElement> AccountDetailPopupHeader() {
		return getWebElements(d, "AccountGroupPopupHeader", pageName, null);
	}

	public List<WebElement> acntDetailPopupRightSide() {
		return getWebElements(d, "acntDetailPopupFirstLine", pageName, null);
	}

	public List<WebElement> acntDetailPopupAcntNumber() {
		return getWebElements(d, "acntDetailPopupAcntNumber", pageName, null);
	}

	public List<WebElement> acntDetailPopupContent() {
		return getWebElements(d, "acntDetailPopupContent", pageName, null);
	}

	public WebElement AcntDetailPopupCrossIcon() {
		return getWebElement(d, "AcntDetailPopupCrossIcon", pageName, null);
	}

	public WebElement AcntDetailPopupExpandCollpaseBtn() {
		return getWebElement(d, "AcntDetailPopupExpandCollpaseBtn", pageName, null);
	}

	public void clickingOnExpandCollapseBtn() {
		click(AcntDetailPopupExpandCollpaseBtn());
	}

	public void clickingOnCrossIcon() {
		click(AcntDetailPopupCrossIcon());
	}

	public void verifyingAcntDetailRightSide(String propValue) {
		String expected[] = PropsUtil.getDataPropertyValue(propValue).trim().split(":");
		for (int i = 0; i < acntDetailPopupRightSide().size(); i++) {
			String actual = acntDetailPopupRightSide().get(i).getText().trim();
			Assert.assertEquals(expected[i], actual);
		}
	}

	public void verifyAcntDetailsPopupContent(String propValue) {
		String expected[] = PropsUtil.getDataPropertyValue(propValue).split(":");
		for (int i = 0; i < acntDetailPopupContent().size(); i++) {
			String actual = acntDetailPopupContent().get(i).getText().trim();
			Assert.assertEquals(expected[i], actual);
		}
	}
	
	public String returnTheAmountDisplayedForGivenAccount(String accountName) {
		String xpath = xpathForAmountForGivenAccountName.replace("${Acc.Name}", accountName);
		return getText(By.xpath(xpath));
	}
	
	public ArrayList<String> getTheAvailableAccounts() {
		ArrayList<String> containersAdded = new ArrayList<String>();
		waitUntilElementIsVisible(getByObject("FL4", null, "AccountNamesInAccountsFinapp"), 60);
		List<WebElement> containers = getWebElements(d, "AccountNamesInAccountsFinapp", "FL4", null);
		containers.stream().forEach(t -> {
			containersAdded.add(t.getText());
		});
		return containersAdded;
	}
	
	public int getTheAccountsCount() {
		return getElementCount(getByObject("FL4", null, "AccountNamesInAccountsFinapp"));
	}
}
