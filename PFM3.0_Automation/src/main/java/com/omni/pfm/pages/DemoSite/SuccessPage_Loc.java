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

public class SuccessPage_Loc {
	WebDriver d;
	String pageName = "Demosite";
	
	public SuccessPage_Loc(WebDriver d) {
		this.d=d;
	}

	public WebElement headerText() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageHeaderText", pageName, null);

	}

	public WebElement text1() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageText1", pageName, null);

	}

	public WebElement text2() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageText2", pageName, null);

	}

	public WebElement statusText() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageStatusText", pageName, null);

	}

	public WebElement statusValue() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageStatusValue", pageName, null);

	}

	public WebElement submittedText() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageSubmittedText", pageName, null);

	}

	public WebElement submittedValue() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageSubmittedValue", pageName, null);

	}

	public WebElement subjectText() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageSubjectText", pageName, null);

	}

	public WebElement subjectValue() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageSubjectValue", pageName, null);

	}

	public WebElement systemDetailsText() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageSystemDetailsText", pageName, null);

	}
	
	public WebElement descriptionText() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageDescriptionText", pageName, null);

	}
	public WebElement descriptionValue() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageDescriptionValue", pageName, null);

	}

	public WebElement systemDetailsValue() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessPageSystemDetailsValue", pageName, null);

	}
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
	
}
