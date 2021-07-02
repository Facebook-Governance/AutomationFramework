/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_Detail_Loc {
	Logger logger= LoggerFactory.getLogger(Accounts_Detail_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_Detail_Loc(WebDriver d) {
		this.d=d;
	}

	public WebElement containerAccounts(String containerNumber, String subContainer, String rowContainer) {
		String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "containerGenericRows");
		webElement = webElement.replaceAll("containerNumber", containerNumber);
		webElement = webElement.replaceAll("subContainer", subContainer);
		webElement = webElement.replaceAll("rowContainer", rowContainer);

		return d.findElement(By.xpath(webElement));
	}
	public WebElement pgTitleText() {
		return SeleniumUtil.getWebElement(d, "pgTitleText", "AccountsPage", null);
	}



	public WebElement pageTitle() {
		return SeleniumUtil.getWebElement(d, "AccountDetailpopHeader", pageName, frameName);
	}
	public WebElement acntNameText1() {
		return SeleniumUtil.getWebElement(d, "acntNameText1", "AccountsPage", null);
	}
	public WebElement acntNumber1() {
		return SeleniumUtil.getWebElement(d, "acntNumber1", "AccountsPage", null);
	}
	public WebElement viewTrend() {
		return SeleniumUtil.getWebElement(d, "ViewTrnd", "AccountsPage", null);
	}
	public WebElement holdingHeader() {
		return SeleniumUtil.getWebElement(d, "popUpHeader", pageName, frameName);
	}
	public WebElement closePopUp() {
		return  SeleniumUtil.getWebElement(d, "closePopup1", pageName, frameName);
	}

	public void verifyPopupDetails() {
		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,-900)", "");

		String acntName="TESTDATA";
		String acntNum="Checking | x-3xxx";
		System.out.println("The accName and accNum "+acntName+"    "+acntNum);

		js.executeScript("window.scrollBy(0,-700)", "");
		SeleniumUtil.click(containerAccounts("1","1","1"));
		SeleniumUtil.waitForPageToLoad(1000);

		String getAcntName= acntNameText1().getText().trim();

		String getAcntNum= acntNumber1().getText().trim();

		String actual = viewTrend().getText().trim();
		String expected=PropsUtil.getDataPropertyValue("ViewTrend").trim();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 2000);

		Assert.assertEquals(acntName, getAcntName);
		Assert.assertEquals(acntNum, getAcntNum);
		Assert.assertEquals(actual, expected);
	}

	public void verifyHoldingPopup() {
		boolean investmentHoldingStatus= SeleniumUtil.selectDropDownVisibility(d, "Investment Account", "Holdings");
		SeleniumUtil.selectDropDown(d, "Investment Account", "Holdings");
		SeleniumUtil.waitForPageToLoad();

		boolean holdingHeader= holdingHeader().isDisplayed();

		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(closePopUp());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(investmentHoldingStatus, "Holdings is not found in dropdown list");
		Assert.assertTrue(holdingHeader, "popup does not contain header so its bug");
	}
	public void verifyBillRow() {

		SeleniumUtil.click(containerAccounts("1","6","1"));
		SeleniumUtil.waitForPageToLoad();

		String actual=pageTitle().getText().trim();
		String expected=PropsUtil.getDataPropertyValue("AccountDetailpopHeader").trim();

		SeleniumUtil.click(closePopUp());
		Assert.assertEquals(actual, expected);	
	}

	public void verifyRewardRow() {
		SeleniumUtil.click(containerAccounts("1","7","1"));
		SeleniumUtil.waitForPageToLoad();

		String actual=pageTitle().getText().trim();
		String expected=PropsUtil.getDataPropertyValue("AccountDetailpopHeader").trim();
		SeleniumUtil.click(closePopUp());
		Assert.assertEquals(actual, expected);	
	}

    public WebElement BackDash() {
        return SeleniumUtil.getWebElement(d, "BackDash", "AccountsPage", null);
} 

}

