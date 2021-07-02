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

public class UserProfilePersonalInfo_Loc {
	
	WebDriver d;
	String pageName = "DemoSite";
	
	public UserProfilePersonalInfo_Loc(WebDriver d){
		this.d=d;
	}

	public WebElement userID() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileUserID", pageName, null);

	}

	public WebElement password() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePassword", pageName, null);

	}

	public WebElement finappID() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileFinappID", pageName, null);

	}

	public WebElement loginButton() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLoginButton", pageName, null);

	}

	public WebElement personalInfoHeaderSideBar() {

		return SeleniumUtil.getWebElement(d, "PersonalInfoHeaderSideBar", pageName, null);

	}

	public WebElement personalInfoHeader() {

		return SeleniumUtil.getWebElement(d, "PersonalInfoHeader", pageName, null);

	}
	public WebElement lblFirstName() {

		return SeleniumUtil.getWebElement(d, "LblFirstName", pageName, null);

	}
	public WebElement lblLastName() {

		return SeleniumUtil.getWebElement(d, "LblLastName", pageName, null);

	}
	public WebElement txtBoxLastName() {

		return SeleniumUtil.getVisibileWebElement(d, "TxtBoxLastName", pageName, null);

	}
	public WebElement lblEmail() {

		return SeleniumUtil.getVisibileWebElement(d, "LblEmail", pageName, null);

	}
	public WebElement txtBoxEmail() {

		return SeleniumUtil.getVisibileWebElement(d, "TxtBoxemail", pageName, null);

	}
	public WebElement lblCountry() {

		return SeleniumUtil.getVisibileWebElement(d, "LblCountry", pageName, null);

	}

	public WebElement dropDownCountry() {

		return SeleniumUtil.getVisibileWebElement(d, "DropDownCountry", pageName, null);

	}
	public WebElement lblMobile() {

		return SeleniumUtil.getVisibileWebElement(d, "LblMobile", pageName, null);

	}public WebElement txtBoxMobile() {

		return SeleniumUtil.getVisibileWebElement(d, "TxtBoxMobile", pageName, null);

	}
	
	public WebElement chckBoxShareInfo() {

		return SeleniumUtil.getVisibileWebElement(d, "ChckBoxShareInfo", pageName, null);

	}
	
	public WebElement changeMobNum() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangemobNumVerifi", pageName, null);

	}


	public WebElement chckBoxRecvEmail() {

		return SeleniumUtil.getVisibileWebElement(d, "ChckBoxRecvEmail", pageName, null);

	}
	public WebElement btnCancel() {

		return SeleniumUtil.getVisibileWebElement(d, "BtnCancel", pageName, null);

	}
	
	public WebElement btnSave() {

		return SeleniumUtil.getVisibileWebElement(d, "BtnSave", pageName, null);

	}
	public WebElement successMsg() {

		return SeleniumUtil.getVisibileWebElement(d, "UserProfileSuccessMsg", pageName, null);

	}
	
	public WebElement errMsgBlankFname() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrMsgBlankFname", pageName, null);

	}
	
	public WebElement errMsgBlankLname() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrMsgBlankLname", pageName, null);

	}
	public WebElement errMsgBlankEmail() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrMsgBlankEmail", pageName, null);

	}
	

	public WebElement errMsgInvalidEmail() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrMsgInvalidEmail", pageName, null);

	}
	public WebElement errMsgBlankMobile() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrMsgBlankMobile", pageName, null);

	}
	public WebElement errMsgInvalidMobile() {

		return SeleniumUtil.getVisibileWebElement(d, "ErrMsgInvalidMobile", pageName, null);

	}
	public WebElement emailIDHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "EmailIDHeader", pageName, null);

	}
	

	public WebElement emailIDText1() {

		return SeleniumUtil.getVisibileWebElement(d, "EmailIDText1", pageName, null);

	}
	public WebElement emailIDText2() {

		return SeleniumUtil.getVisibileWebElement(d, "EmailIDText2", pageName, null);

	}
	public WebElement emailIDLabel() {

		return SeleniumUtil.getVisibileWebElement(d, "EmailIDLabel", pageName, null);

	}
	
	public WebElement verificationField() {

		return SeleniumUtil.getVisibileWebElement(d, "VerificationField", pageName, null);

	}
	public WebElement confirmButton() {

		return SeleniumUtil.getVisibileWebElement(d, "ConfirmButton", pageName, null);

	}
	public WebElement verificationCodeLabel() {

		return SeleniumUtil.getVisibileWebElement(d, "verificationCodeLabelXpath", pageName, null);

	}
	
	public WebElement resendLink() {

		return SeleniumUtil.getVisibileWebElement(d, "ResendLink", pageName, null);

	}
	public WebElement changeLink() {

		return SeleniumUtil.getVisibileWebElement(d, "ChangeLink", pageName, null);

	}
	public WebElement txtBoxFirstName() {

		return SeleniumUtil.getVisibileWebElement(d, "TxtBoxFirstName", pageName, null);

	}
	
	
	
	
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
	}

