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

public class UserProfileSecurityQues_Loc {

	WebDriver d;
	String pageName = "DemoSite";
	
	public UserProfileSecurityQues_Loc(WebDriver d) {
		this.d=d;
	}
	

	public WebElement securityHeaderSideBar() {

		return SeleniumUtil.getWebElement(d, "ProfilesecurityHeaderSideBar", pageName, null);

	}

	public WebElement securityHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilesecurityHeader", pageName, null);

	}

	public WebElement secPage1Msg() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileSecPage1Msg", pageName, null);

	}

	public WebElement btnEdit() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileBtnEdit", pageName, null);

	}

	public WebElement enterPwdMsg() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileEnterPwdMsg", pageName, null);

	}

	public WebElement errMsgOldPwdBlank() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileErrMsgOldPwdBlank", pageName, null);

	}

	public WebElement errMsgWrongOldPwd() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileErrMsgWrongOldPwd", pageName, null);

	}

	public WebElement txtBoxEnterPwd() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileTxtBoxEnterPwd", pageName, null);

	}

	public WebElement btnContinue() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileBtnContinue", pageName, null);

	}

	public WebElement lblSecQues1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLblSecQues1", pageName, null);

	}

	public WebElement lblSecQues2() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLblSecQues2", pageName, null);

	}

	public WebElement lblSecQues3() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLblSecQues3", pageName, null);

	}
	public WebElement lblAns1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLblAns1", pageName, null);

	}
	
	public WebElement lblAns2() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLblAns2", pageName, null);

	}
	
	public WebElement lblAns3() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLblAns3", pageName, null);

	}
	public WebElement dropdownSecQues1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDropdownSecQues1", pageName, null);

	}
	
	public WebElement dropdownSecQues2() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDropdownSecQues2", pageName, null);

	}
	
	public WebElement dropdownSecQues3() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDropdownSecQues3", pageName, null);

	}

	public WebElement txtBoxAnswer1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileTxtBoxAnswer1", pageName, null);

	}
	
	public WebElement txtBoxAnswer2() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileTxtBoxAnswer2", pageName, null);

	}
	public WebElement txtBoxAnswer3() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileTxtBoxAnswer3", pageName, null);

	}
	
	public WebElement errMsgAnswer1() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileErrMsgAnswer1", pageName, null);

	}
	
	public WebElement errMsgAnswer2() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileErrMsgAnswer2", pageName, null);

	}
	
	public WebElement errMsgAnswer3() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileErrMsgAnswer3", pageName, null);

	}
	
	public WebElement btnCancel() {

		return SeleniumUtil.getVisibileWebElement(d, "SecurityBtnCancel", pageName, null);

	}
	public WebElement btnSave() {

		return SeleniumUtil.getVisibileWebElement(d, "SecurityBtnSave", pageName, null);

	}
	public WebElement successMsg() {

		return SeleniumUtil.getVisibileWebElement(d, "SecuritySuccessMsg", pageName, null);

	}
	
	
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
	
	
}
