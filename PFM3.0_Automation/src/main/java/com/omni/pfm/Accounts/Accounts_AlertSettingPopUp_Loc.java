/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_AlertSettingPopUp_Loc {
	Logger logger= LoggerFactory.getLogger(Accounts_AlertSettingPopUp_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_AlertSettingPopUp_Loc(WebDriver d) {
		this.d=d;
	}

	public WebElement popUpHeader() {
		return SeleniumUtil.getWebElement(d, "popUpHeader", "AccountsPage", null);
	}
	public List<WebElement> alertDiscriptions() {
		return SeleniumUtil.getWebElements(d, "alertDiscriptions", "AccountsPage", null);
	}
	
	public void verifyAlertDiscriptions() {
		String expected[]=PropsUtil.getDataPropertyValue("AlertsDiscriptions").split(",");
		for(int i=0;i<alertDiscriptions().size();i++) {
			String getText= alertDiscriptions().get(i).getText().trim();
			String[] actual=getText.split("%");
			Assert.assertEquals(actual[0], expected[i]);
		}
	}
	
	public WebElement popupCrossIcon() {
		return SeleniumUtil.getWebElement(d, "popupCrossIcon", "AccountsPage", null);
	}

}
