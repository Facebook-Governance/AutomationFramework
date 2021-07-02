/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.DemoSite;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class ListServiceRequests_Loc {
	
	WebDriver d;
	String pageName = "DemoSite";
	public ListServiceRequests_Loc(WebDriver d) {
		this.d=d;
	}


public List<WebElement> dates() {

	return SeleniumUtil.getWebElements(d, "Dates", pageName, null);

}

public List<WebElement> summaryFileName() {

	return SeleniumUtil.getWebElements(d, "SummaryFileName", pageName, null);

}

public List <WebElement> serviceRequestIDs() {

	return SeleniumUtil.getWebElements(d, "ServiceRequestIDs", pageName, null);

}

public WebElement addCommentButton() {

	return SeleniumUtil.getVisibileWebElement(d, "AddCommentButton", pageName, null);

}

public WebElement charCount() {

	return SeleniumUtil.getVisibileWebElement(d, "charCountXpath", pageName, null);

}

public WebElement indicatorIcon() {

	return SeleniumUtil.getVisibileWebElement(d, "IndicatorIcon", pageName, null);

}
public WebElement commentButton() {

	return SeleniumUtil.getVisibileWebElement(d, "CommentButton", pageName, null);

}
public WebElement commentTextArea() {

	return SeleniumUtil.getWebElement(d, "CommentTextArea", pageName, null);

}
public WebElement cancelButton() {

	return SeleniumUtil.getVisibileWebElement(d, "CancelButton", pageName, null);

}
public WebElement characterCount() {

	return SeleniumUtil.getWebElement(d, "LSCharacterCount", pageName, null);

}
public WebElement date() {

	return SeleniumUtil.getVisibileWebElement(d, "Date", pageName, null);

}
public WebElement sentBy() {

	return SeleniumUtil.getVisibileWebElement(d, "SentBy", pageName, null);

}

public WebElement commentText() {

	return SeleniumUtil.getVisibileWebElement(d, "CommentText", pageName, null);

}
public WebElement errorMsg() {

	return SeleniumUtil.getVisibileWebElement(d, "ErrorMsg", pageName, null);

}public WebElement toastMsg() {

	return SeleniumUtil.getVisibileWebElement(d, "ToastMsg", pageName, null);

}
public void typeTextField(WebElement element, String value) {
	SeleniumUtil.click(element);
	element.clear();
	element.sendKeys(value);
}

}
