/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.DemoSite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class PasswordUserProfile_Loc {
	
	WebDriver d;
	String pageName="DemoSite";
	
	public PasswordUserProfile_Loc(WebDriver d) {
		this.d=d;
	}
	

	public WebElement passwordHeaderSideBar() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePasswordHeaderSideBar", pageName, null);

	}

	public WebElement passwordEditBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePasswordEditBtn", pageName, null);

	}

	public WebElement passwordEditHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePasswordEditHeader", pageName, null);

	}

	public WebElement curPwdLabel() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileCurPwdLabel", pageName, null);

	}

	public WebElement newPwdLabel1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileNewPwdLabel1", pageName, null);

	}

	public WebElement newPwdLabel2() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileNewPwdLabel2", pageName, null);

	}

	public WebElement oldPwdTextBox() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileOldPwdTextBox", pageName, null);

	}

	public WebElement newPwdTextBox() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileNewPwdTextBox", pageName, null);

	}

	public WebElement cnfPwdTextBox() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileCnfPwdTextBox", pageName, null);

	}

	public WebElement changePwdSuccessMsg() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileChangePwdSuccessMsg", pageName, null);

	}

	public WebElement pwdCancelBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePwdCancelBtn", pageName, null);

	}

	public WebElement pwdSaveBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePwdSaveBtn", pageName, null);

	}
	public WebElement oldPwdError() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileOldPwdError", pageName, null);

	}
	public WebElement newPwdError() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileNewPwdError", pageName, null);

	}

	public WebElement newPwdError1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileNewPwdError1", pageName, null);

	}
	
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
}
