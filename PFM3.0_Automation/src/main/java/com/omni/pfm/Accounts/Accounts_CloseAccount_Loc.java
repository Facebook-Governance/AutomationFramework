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
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_CloseAccount_Loc {

	static Logger logger=LoggerFactory.getLogger(Accounts_CloseAccount_Loc.class);

	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_CloseAccount_Loc(WebDriver d){
		this.d = d;
	}



	public WebElement closeButton() {
		return SeleniumUtil.getWebElement(d, "closeActnoption", pageName, frameName);
	}

	public WebElement closePopupHeader() {
		return SeleniumUtil.getWebElement(d, "closePopupHeader", pageName, frameName);
	}

	public WebElement MobilemainHeader() {
		return SeleniumUtil.getWebElement(d, "MobilemainHeader", pageName, frameName);
	}

	public WebElement clsPopUpTitleText() {
		return SeleniumUtil.getWebElement(d, "clsPopUpTitletext", pageName, frameName);
	}

	public WebElement clsPopUpBodyMessage () {
		return SeleniumUtil.getWebElement(d, "clsPopUpBodyMsg", pageName, frameName);
	}

	public WebElement popUpSubTitleText () {
		return SeleniumUtil.getWebElement(d, "popUpSubTitle", pageName, frameName);
	}

	public WebElement getAccountNameFIView(int containerNumber, int subContainerNumber, int rowNumber)

	{
		String s = SeleniumUtil.getVisibileElementXPath(d, "acNameFiView", pageName, frameName);
		s.replaceAll("VAR_contNum", Integer.toString(containerNumber));
		s.replaceAll("VAR_contNum", Integer.toString(subContainerNumber));
		s.replaceAll("VAR_contNum", Integer.toString(rowNumber));

		return d.findElement(By.xpath(s));
	}

	public void assertClosePopUpBodyMsg(String[] expectedOption) {

		List<WebElement> l = SeleniumUtil.getWebElements(d, "clsBody", pageName, frameName);

		SeleniumUtil.waitForPageToLoad(2000);

		String actualOption[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {

			actualOption[i] = l.get(i).getText();

			Assert.assertEquals(expectedOption[i].toLowerCase().trim(), actualOption[i].toLowerCase().trim());

		}

	}

	public WebElement noteText () {
		return SeleniumUtil.getWebElement(d, "NOteText", pageName, frameName);
	}

	public WebElement editBox () {
		return SeleniumUtil.getWebElement(d, "editBox", pageName, frameName);
	}
	public void typeEditBox(String editText)
	{
		editBox().clear();
		editBox().sendKeys(editText);
		SeleniumUtil.waitForPageToLoad(1000);
	}
	public String getEditName()
	{
		return SeleniumUtil.getVisibileWebElement(d, "closeNote", pageName, frameName).getAttribute("maxlength");
	}
	public WebElement closePopMobile()
	{
		return SeleniumUtil.getWebElement(d, "closePopMobile", pageName, frameName);
	}
	public WebElement closePopUP1()
	{
		return SeleniumUtil.getWebElement(d, "closePopUP1", pageName, frameName);
	}

	public WebElement closeIcon() {
		return SeleniumUtil.getWebElement(d, "ClsBtn", pageName, frameName);
	}
}
