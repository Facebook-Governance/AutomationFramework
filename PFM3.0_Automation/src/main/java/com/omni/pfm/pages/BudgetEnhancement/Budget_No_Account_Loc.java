/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.BudgetEnhancement;



import java.util.List;



import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;



import com.omni.pfm.utility.SeleniumUtil;



public class Budget_No_Account_Loc {



	static Logger logger = LoggerFactory.getLogger(Budget_No_Account_Loc.class);

	public WebDriver d = null;

	static WebElement we;



	String pageName = "Budget";



	String frameName = null;



	public Budget_No_Account_Loc(WebDriver d) {

		this.d = d;

	}

	public WebElement BudgetHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetHeader_BNA", pageName, frameName);

	}



	public WebElement mobileBudgetHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "mobileBudgetHeader_BNA", pageName, frameName);

	}

	public WebElement LinkAccountButton() {

		return SeleniumUtil.getVisibileWebElement(d, "LinkAccountButton_BNA", pageName, frameName);

	}

	public WebElement LinkAccountHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "LinkAccountHeader_BNA", pageName, frameName);

	}

	public WebElement Description() {

		return SeleniumUtil.getVisibileWebElement(d, "Description_BNA", pageName, frameName);

	}

	public List<WebElement> Steps() {

		return SeleniumUtil.getWebElements(d, "Steps_BNA", pageName, frameName);

	}

	public WebElement mobilestep1text() {

		return SeleniumUtil.getVisibileWebElement(d, "mobilestep1text_BNA", pageName, frameName);

	}

	public WebElement mobilestep2text() {

		return SeleniumUtil.getVisibileWebElement(d, "mobilestep2text_BNA", pageName, frameName);

	}

	public WebElement mobilestep3text() {

		return SeleniumUtil.getVisibileWebElement(d, "mobilestep3text_BNA", pageName, frameName);

	}

	public WebElement mobilestep1iconview() {

		return SeleniumUtil.getVisibileWebElement(d, "mobilestep1iconview_BNA", pageName, frameName);

	}

	public List<WebElement> mobilestep2iconview() {

		return SeleniumUtil.getWebElements(d, "mobilestep2iconview_BNA", pageName, frameName);

	}

	public WebElement mobilestep3iconview() {

		return SeleniumUtil.getVisibileWebElement(d, "mobilestep3iconview_BNA", pageName, frameName);

	}

	public List<WebElement> StepsIcon() {

		return SeleniumUtil.getWebElements(d, "StepsIcon_BNA", pageName, frameName);

	}



	public WebElement Travel_trninBud() {

		return SeleniumUtil.getVisibileWebElement(d, "Travel_trninBud", pageName, frameName);

	}
	public WebElement Travel_Acc() {

		return SeleniumUtil.getVisibileWebElement(d, "Travel_Acc", pageName, frameName);

	}
	public WebElement GetStartedButton() {

		return SeleniumUtil.getVisibileWebElement(d, "GetStartedButton_BNA", pageName, frameName);

	}
	
	public WebElement budget_Acc_container() {

		return SeleniumUtil.getVisibileWebElement(d, "budget_Acc_container", pageName, frameName);

	}
	public WebElement MobileGetStartedButton() {

		return SeleniumUtil.getVisibileWebElement(d, "MobileGetStartedButton_BNA", pageName, frameName);

	}

	public WebElement FooterText() {

		return SeleniumUtil.getVisibileWebElement(d, "FooterText_BNA", pageName, frameName);

	}

	public WebElement MobileFooterText() {

		return SeleniumUtil.getVisibileWebElement(d, "MobileFooterText_BNA", pageName, frameName);

	}

	public WebElement ThreeTabs() {

		return SeleniumUtil.getWebElement(d, "ThreeTabs_BNA", pageName, frameName);

	}

	public List<WebElement> MobileThreeTabs() {

		return SeleniumUtil.getWebElements(d, "MobileThreeTabs_BNA", pageName, frameName);

	}

	public List<WebElement> FooterTextList() {

		return SeleniumUtil.getWebElements(d, "FooterTextList_BNA", pageName, frameName);

	}

	public WebElement FooterLogo() {

		return SeleniumUtil.getVisibileWebElement(d, "FooterLogo_BNA", pageName, frameName);

	}

	public WebElement FooterLogo1() {

		return SeleniumUtil.getVisibileWebElement(d, "FooterLogo1_BNA", pageName, frameName);

	}

	public WebElement alldoneButton() {

		return SeleniumUtil.getVisibileWebElement(d, "alldoneButton_BNA", pageName, frameName);

	}

	public void clickOnGetStartedButton(){
		SeleniumUtil.click(GetStartedButton());
		SeleniumUtil.waitForPageToLoad(3000);
	}



}

