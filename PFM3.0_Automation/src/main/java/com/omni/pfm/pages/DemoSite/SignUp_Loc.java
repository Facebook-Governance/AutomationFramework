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

public class SignUp_Loc {

	WebDriver d;
	String pageName = "DemoSite";

	public SignUp_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement loginButton() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginButton", pageName, null);

	}

	public WebElement signUpButton() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionSignUpButton", pageName, null);

	}

	public WebElement createAnAccountButton() {

		return SeleniumUtil.getVisibileWebElement(d, "createAnAccountButtonXpath", pageName, null);

	}

	public WebElement firstName() {

		return SeleniumUtil.getVisibileWebElement(d, "FirstName", pageName, null);

	}

	public WebElement lastName() {

		return SeleniumUtil.getVisibileWebElement(d, "LastName", pageName, null);

	}

	public WebElement editEmailID() {

		return SeleniumUtil.getVisibileWebElement(d, "EditEmailID", pageName, null);

	}

	public WebElement userID() {

		return SeleniumUtil.getVisibileWebElement(d, "UserID", pageName, null);

	}

	public WebElement password() {

		return SeleniumUtil.getVisibileWebElement(d, "Password", pageName, null);

	}

	public WebElement confirmPassword() {

		return SeleniumUtil.getVisibileWebElement(d, "ConfirmPassword", pageName, null);

	}

	public WebElement country() {

		return SeleniumUtil.getVisibileWebElement(d, "Country", pageName, null);

	}

	public WebElement mobileNum() {

		return SeleniumUtil.getVisibileWebElement(d, "MobileNum", pageName, null);

	}

	public WebElement checkBox1() {

		return SeleniumUtil.getVisibileWebElement(d, "CheckBox1", pageName, null);

	}

	public WebElement checkBoxLginEnh1() {

		return SeleniumUtil.getVisibileWebElement(d, "CheckBoxLginEnh1", pageName, null);

	}

	public WebElement checkBoxLginEnh2() {

		return SeleniumUtil.getVisibileWebElement(d, "CheckBoxLginEnh2", pageName, null);

	}

	public WebElement checkBoxLginEnh3() {

		return SeleniumUtil.getVisibileWebElement(d, "CheckBoxLginEnh3", pageName, null);

	}

	public WebElement loginEnhAgree() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginEnhAgree", pageName, null);

	}

	public WebElement secQues1() {

		return SeleniumUtil.getVisibileWebElement(d, "SecQues1", pageName, null);

	}

	public WebElement secQues2() {

		return SeleniumUtil.getVisibileWebElement(d, "SecQues2", pageName, null);

	}

	public WebElement secQues3() {

		return SeleniumUtil.getVisibileWebElement(d, "SecQues3", pageName, null);

	}

	public WebElement secAns1() {

		return SeleniumUtil.getVisibileWebElement(d, "SecAns1", pageName, null);

	}

	public WebElement secAns2() {

		return SeleniumUtil.getVisibileWebElement(d, "SecAns2", pageName, null);

	}

	public WebElement secAns3() {

		return SeleniumUtil.getVisibileWebElement(d, "SecAns3", pageName, null);

	}

	public WebElement nextButton() {

		return SeleniumUtil.getVisibileWebElement(d, "NextButton", pageName, null);

	}

	public WebElement secureAcctPageNextBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "SecureAcctPageNextBtn", pageName, null);

	}

	public WebElement code() {

		return SeleniumUtil.getVisibileWebElement(d, "Code", pageName, null);

	}
	public WebElement code1() {

		return SeleniumUtil.getVisibileWebElement(d, "Code1", pageName, null);

	}
	public WebElement agree() {

		return SeleniumUtil.getVisibileWebElement(d, "SignUpAgree", pageName, null);

	}

	public WebElement verifyButton() {

		return SeleniumUtil.getVisibileWebElement(d, "VerifyButton", pageName, null);

	}

	public WebElement mobVerifyButton() {

		return SeleniumUtil.getVisibileWebElement(d, "MobVerifyButton", pageName, null);

	}

	public WebElement doneButton() {

		return SeleniumUtil.getVisibileWebElement(d, "DoneButton", pageName, null);

	}
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
}