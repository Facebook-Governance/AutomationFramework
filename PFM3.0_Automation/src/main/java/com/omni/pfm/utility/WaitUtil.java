package com.omni.pfm.utility;
/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitUtil {
	public static final Logger logger = LoggerFactory.getLogger(WaitUtil.class);
	static WebDriverWait wait;

	/**
	 * Generic method to wait until the element is clickable and return.
	 * 
	 * @param d
	 * @param label
	 * @param pageName
	 * @param frameName
	 * @return Clickable WebElement
	 * @author sbhat1
	 */
	public static WebElement getClickableWebElement(WebDriver d, String label, String pageName, String frameName) {
		wait = new WebDriverWait(d, 15);
		return wait.until(
				ExpectedConditions.elementToBeClickable(SeleniumUtil.getWebElement(d, label, pageName, frameName)));
	}

	/**
	 * 
	 * Generic method to wait until the element is visible in dom and return.
	 * 
	 * @param d
	 * @param label
	 * @param pageName
	 * @param frameName
	 * @return Visible Web Element
	 * @author sbhat1
	 */
	public static WebElement getVisibleWebElement(WebDriver d, String label, String pageName, String frameName) {
		wait = new WebDriverWait(d, 15);
		return wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, label, pageName, frameName)));
	}

	/**
	 * Generic method to get all the web elements as list
	 * 
	 * @param d
	 * @param label
	 * @param pageName
	 * @param frameName
	 * @return
	 */
	public static List<WebElement> getVisibleWebElements(WebDriver d, String label, String pageName,
			String frameName) {
		wait = new WebDriverWait(d, 15);
		return wait.until(
				ExpectedConditions.visibilityOfAllElements(SeleniumUtil.getWebElements(d, label, pageName, frameName)));
	}
	

}
