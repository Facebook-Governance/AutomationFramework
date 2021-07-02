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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

public class CloseAccountPopup_Loc {
	Logger logger=LoggerFactory.getLogger(CloseAccountPopup_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;
	
	public CloseAccountPopup_Loc(WebDriver d) {
		this.d=d;
	}
	public WebElement closeAcntPopupBodyMsg1() {
		return SeleniumUtil.getWebElement(d, "closeAcntPopupBodyMsg1", pageName, null);
	}
	public List<WebElement> closeAcntPopupBodyMsgs() {
		return SeleniumUtil.getWebElements(d, "closeAcntPopupBodyMsgs", pageName, null);
	}
	public WebElement closeAcntPopupNoteBox() {
		return SeleniumUtil.getWebElement(d, "closeAcntPopupNoteBox", pageName, null);
	}
	public WebElement closeAcntPopupCloseBtn() {
		return SeleniumUtil.getWebElement(d, "closeAcntPopupCloseBtn", pageName, null);
	}
	public List<WebElement> closeAcntPopupHeader() {
		return SeleniumUtil.getWebElements(d, "closeAcntPopupHeader", pageName, null);
	}
	public WebElement closeAcntPopupCrossIcon() {
		return SeleniumUtil.getWebElement(d, "closeAcntPopupCrossIcon", pageName, null);
	}	
	public WebElement closeAcntPopupNoteTxt() {
		return SeleniumUtil.getWebElement(d, "closeAcntPopupNoteTxt", pageName, null);
	}	
	public void isCloseAcntPopupCrossIconDisplayed(){
		closeAcntPopupCrossIcon().isDisplayed();
	}
	public String getMaxAttributeLength()
	{
		return SeleniumUtil.getVisibileWebElement(d, "closeNote", pageName, frameName).getAttribute("maxlength");
	}
	public void clickOncloseAcntPopupCrossIcon(){
		SeleniumUtil.click(closeAcntPopupCrossIcon());
	}
	
	public void verifyingcloseAcntPopupBodyMsgs() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "closeAcntPopupBodyMsgs", pageName, frameName);
		CommonUtils.assertContainsListElements("closePopUpBodyMsg", l);
	}
	public void clickOncloseAcntPopupNoteTxt(){
		SeleniumUtil.click(closeAcntPopupNoteTxt());
	}
	public void typeEditBox(String editText)
	{
		closeAcntPopupNoteBox().clear();
		closeAcntPopupNoteBox().sendKeys(editText);
		SeleniumUtil.waitForPageToLoad(1000);
	}
	public void clickingOnCloseAcntBtn(){
		SeleniumUtil.click(closeAcntPopupCloseBtn());
	}
	public WebElement closeAcntSuccessMsg() {
		return SeleniumUtil.getWebElement(d, "deleteAcntSuccessMsg", pageName, null);
	}
	
	
}
