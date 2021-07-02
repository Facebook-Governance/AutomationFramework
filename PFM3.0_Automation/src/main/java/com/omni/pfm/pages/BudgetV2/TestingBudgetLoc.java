/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Basavaraj 
*/
package com.omni.pfm.pages.BudgetV2;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class TestingBudgetLoc {

	public static WebDriver d;
	String frameName, pageName;
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(TestingBudgetLoc.class);

	public TestingBudgetLoc(WebDriver d) {
		this.d = d;
		pageName = "Budget";
	}
	
	public WebElement checkpageTitle() {
		return SeleniumUtil.getWebElement(d, "checkPageTitle", pageName, frameName);
	}
	
	public WebElement checkBudgetStatement() {
		return SeleniumUtil.getWebElement(d, "BudgetPageStatement1", pageName, frameName);
	}
	
	public WebElement checkStep1() { //if you are looking for a list of elements then you need to specify List<WebElement> here
		return SeleniumUtil.getWebElement(d, "BudgetStep1", pageName, frameName);
	}
	
	public List<WebElement> checkAllSteps(){
		return SeleniumUtil.getWebElements(d, "BudgetAllSteps", pageName, frameName);
	}
	
	public WebElement checkDisclaimer() {
		return SeleniumUtil.getWebElement(d, "BottomDiscl", pageName, frameName);
	}
	
	public WebElement checkBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "GetStartedBtn", pageName, frameName);
	}
	
	public WebElement LandingPageText() {
		return SeleniumUtil.getWebElement(d, "AccountSelectionPage", pageName, frameName);
		
	}
	
	public WebElement CashSection() {
		return SeleniumUtil.getWebElement(d, "CashSection1", pageName, frameName);
	}
	
	public WebElement CardSection() {
		return SeleniumUtil.getWebElement(d, "CardSection2", pageName, frameName);
	}
	
	public WebElement InvestmentSection() {
		return SeleniumUtil.getWebElement(d, "InvestmentSection3", pageName, frameName);
	}
	
	public WebElement LOCSection() {
		return SeleniumUtil.getWebElement(d, "LOCSection4", pageName, frameName);
	}
	
	public WebElement checkbox() {
		return SeleniumUtil.getVisibileWebElement(d, "checkBoxCheck2", pageName, frameName);
	}  // you can use this single method to call every time for the same action. 
	
	public WebElement UncheckedActName() {
		return SeleniumUtil.getWebElement(d, "checkuncheckedActname", pageName, frameName);
	}
	
	public WebElement clickNext1() {
		return SeleniumUtil.getWebElement(d, "NextBtn", pageName, frameName);
	}
	
	public WebElement clickback() {
		return SeleniumUtil.getWebElement(d, "BackBtn", pageName, frameName);
	}
	
	public WebElement UncheckedActName1() {
		return SeleniumUtil.getWebElement(d, "checkuncheckedActname", pageName, frameName);
	}
	
	
	
}
