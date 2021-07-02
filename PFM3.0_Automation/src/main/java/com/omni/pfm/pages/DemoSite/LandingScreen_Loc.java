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

public class LandingScreen_Loc {
	
	WebDriver d;
	
	String pageName = "DemoSite";
	
	public LandingScreen_Loc(WebDriver d) {
		this.d=d;
	}
	public WebElement userID() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginUserID", pageName, null);

	}

	public WebElement password() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginPassword", pageName, null);

	}

	public WebElement finappID() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginFinappID", pageName, null);

	}
	public WebElement loginButton() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginloginButton", pageName, null);

	}
	
	public WebElement headerText() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginHeaderText", pageName, null);

	}
	
	public WebElement createNewBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginCreateNewBtn", pageName, null);

	}
	public WebElement createNewButton() {

		return SeleniumUtil.getVisibileWebElement(d, "LoginCreateNewButton", pageName, null);

}
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
}
