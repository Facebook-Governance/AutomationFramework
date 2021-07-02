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

import com.omni.pfm.utility.Email;
import com.omni.pfm.utility.ParseEmail;
import com.omni.pfm.utility.SeleniumUtil;

public class LoginEnhancements_Loc {

	WebDriver d;
	String pageName = "DemoSite";
	Email mail = new Email();
	ParseEmail parse = new ParseEmail();

	public LoginEnhancements_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement userNameBox() {

		return SeleniumUtil.getVisibileWebElement(d, "userNameBoxXpath", pageName, null);

	}

	public WebElement passwordBox() {

		return SeleniumUtil.getVisibileWebElement(d, "passwordBoxXpath", pageName, null);

	}

	public WebElement loginButton() {

		return SeleniumUtil.getVisibileWebElement(d, "loginButtonXpath", pageName, null);

	}

	public WebElement forgotUserId() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIdXpath", pageName, null);

	}

	public WebElement forgotPassword() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordXpath", pageName, null);

	}

	public WebElement dontHaveAnAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "dontHaveAnAccountXpath", pageName, null);

	}

	public WebElement createAnAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "createAnAccountXpath", pageName, null);

	}

	public WebElement otpCardDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "otpCardDescXpath", pageName, null);

	}

	public WebElement otpCardOTPtextBox() {

		return SeleniumUtil.getVisibileWebElement(d, "otpCardOTPtextBoxXpath", pageName, null);

	}

	public WebElement otpCardResendCodeBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "otpCardResendCodeBtnXpath", pageName, null);

	}

	public WebElement otpCardSubmitBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "otpCardSubmitBtnXpath", pageName, null);

	}

	public WebElement otpCardEmailIncorrectSecurityCodeError() {

		return SeleniumUtil.getVisibileWebElement(d, "otpCardEmailIncorrectSecurityCodeXpath", pageName, null);

	}

	public WebElement otpCardCancelBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "otpCardCancelBtnXpath", pageName, null);

	}

	public WebElement registrationPageTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "registrationPageTitleXpath", pageName, null);

	}

	public WebElement forgotUserIDCardTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardTitleXpath", pageName, null);

	}

	public WebElement forgotUserIDCardDescription() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardDescriptionXpath", pageName, null);

	}

	public WebElement forgotUserIDCardEmailBox() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardEmailBoxXpath", pageName, null);

	}

	public WebElement forgotUserIDCardContinueBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardContinueBtnXpath", pageName, null);

	}

	public WebElement forgotUserIDCardCongratsHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardCongratsHeaderXpath", pageName, null);

	}

	public WebElement forgotUserIDCardCongratsSubHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardCongratsSubHeaderXpath", pageName, null);

	}

	public WebElement forgotUserIDCardCongratsNextBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardCongratsNextBtnXpath", pageName, null);

	}

	public WebElement forgotUserIDCardCancelBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotUserIDCardCancelBtnXpath", pageName, null);

	}

	public WebElement forgotPasswordCardTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordCardTitleXpath", pageName, null);

	}

	public WebElement forgotPasswordCardDescription() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordCardDescriptionXpath", pageName, null);

	}

	public WebElement forgotPasswordCardEmailBox() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordCardEmailBoxXpath", pageName, null);

	}

	public WebElement forgotPasswordCardContinueBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordCardContinueBtnXpath", pageName, null);

	}

	public WebElement forgotPasswordSuccessCardHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordSuccessCardHeaderXpath", pageName, null);

	}

	public WebElement forgotPasswordSuccessCardSubHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordSuccessCardSubHeaderXpath", pageName, null);

	}

	public WebElement forgotPasswordCardCancelBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "forgotPasswordCardCancelBtnXpath", pageName, null);

	}

	public WebElement dashBoardHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "dashBoardHeaderXpath", pageName, null);

	}

	public WebElement securityQuesDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "securityQuesDescXpath", pageName, null);

	}

	public WebElement securityQuesAnsTextBox() {

		return SeleniumUtil.getVisibileWebElement(d, "securityQuesAnsTextBoxXpath", pageName, null);

	}

	public WebElement securityQuesContinueBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "securityQuesContinueBtnXpath", pageName, null);

	}

	public WebElement setNewPasswordPasswordBox() {

		return SeleniumUtil.getVisibileWebElement(d, "setNewPasswordPasswordBoxXpath", pageName, null);

	}

	public WebElement setNewPasswordReEnterPasswordBox() {

		return SeleniumUtil.getVisibileWebElement(d, "setNewPasswordReEnterPasswordBoxXpath", pageName, null);

	}

	public WebElement setNewPasswordContinueBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "setNewPasswordContinueBtnXpath", pageName, null);

	}

	public WebElement passwordChangeSuccessCardHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "passwordChangeSuccessCardHeaderXpath", pageName, null);

	}

	public WebElement passwordChangeSuccessCardSubHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "passwordChangeSuccessCardSubHeaderXpath", pageName, null);

	}

	public WebElement passwordChangeSuccessCardContinueBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "passwordChangeSuccessCardContinueBtnXpath", pageName, null);

	}

	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}

	public WebElement loginEnhancementHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginEnhancementHeaderText", pageName, null);

	}

	public WebElement loginEnhancementSubHdrText() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginEnhancementSubHdrText", pageName, null);

	}

	public WebElement loginEnhancementSubHdrText1() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginEnhancementSubHdrText1", pageName, null);

	}

}
