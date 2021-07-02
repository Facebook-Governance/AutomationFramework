/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CashFlow;

/*

* 

* @autor -Pallavee modified by Ashwin

* 

* 

*/

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */

public class SmbLandingPage {

	static Logger logger = LoggerFactory.getLogger(SmbLandingPage.class);
	public static WebDriver d =null;
	static WebElement we;
	
	public SmbLandingPage(WebDriver d)
	{
		this.d=d;
		
	}

	public void NavigateToNewFrame() {

		// Click on Rsession

		//webDriver.findElement(By.xpath("html/body/div[3]/ul/li[2]")).click();

		SeleniumUtil.getVisibileWebElement(d, "NavigateToNewFrameOne_SLP", "Transaction", null).click();
		
		// Open a new Tab

		//webDriver.findElement(By.xpath(".//*[@id='tab-2']/div[3]")).click();

		SeleniumUtil.getVisibileWebElement(d, "NavigateToNewFrameTwo_SLP", "Transaction", null).click();
		
	}

}
