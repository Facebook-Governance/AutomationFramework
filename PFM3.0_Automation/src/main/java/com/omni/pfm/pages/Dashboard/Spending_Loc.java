/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Dashboard;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class Spending_Loc {
	
	public WebDriver d;
	public static String pageName="DashboardPage";
	public static String frameName=null;


	public Spending_Loc(WebDriver d) {
		this.d = d;
	}
	public WebElement monthBar(){
		return SeleniumUtil.getWebElement(d, "bar_SL", pageName, frameName);
	}
	public WebElement monthToDateText() {
		return SeleniumUtil.getWebElement(d, "txt_SL", pageName, frameName);
	}
	public WebElement spendingHeader() {
		return SeleniumUtil.getWebElement(d, "header_SL", pageName, frameName);
	}
	public WebElement Chart() {
		return SeleniumUtil.getWebElement(d, "chart_SL", pageName, frameName);
	}
	public WebElement SpendingCard() { 
		return SeleniumUtil.getWebElement(d, "card_SL", pageName, frameName);
	}

	public WebElement spendingAmount() {
		return SeleniumUtil.getWebElement(d, "amt_SL", pageName, frameName);
	}
	public WebElement TopExpenseText() {
		return SeleniumUtil.getWebElement(d, "text_SL", pageName, frameName);
	}
	public WebElement OneMonthText() {
		return SeleniumUtil.getWebElement(d, "text1_SL", pageName, frameName);
	}
	public WebElement SixMonthText() {
		return SeleniumUtil.getWebElement(d, "text2_SL", pageName, frameName);
	}
	public List<WebElement> Category(){
		return SeleniumUtil.getWebElements(d, "category_SL", pageName, frameName);
	}

	public WebElement CategoryAmount1() {
		return SeleniumUtil.getWebElement(d, "amount1_SL", pageName, frameName);
	}
	public List<WebElement> CategoryAmount(){
		return SeleniumUtil.getWebElements(d, "amount_SL", pageName, frameName);
	}
	public WebElement CategoryAmount2() {
		return SeleniumUtil.getWebElement(d, "amount2_SL", pageName, frameName);
	}
	public WebElement ExpenseHeader() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {
			return d.findElement(By.xpath("//h2[text()='By Category']"));

		} else {
			return SeleniumUtil.getWebElement(d, "Header_SL1", pageName, frameName);
		}

	}
	public WebElement ThisMonthText() {
		return SeleniumUtil.getWebElement(d, "text4_SL", pageName, frameName);
	}
	public WebElement BackToDashBoard() 
	{
			return SeleniumUtil.getWebElement(d, "text5_SL", pageName, frameName);
	}
	
	public List<WebElement> EIAcategoryname() {
		return SeleniumUtil.getWebElements(d, "EIAcategoryname", pageName, null);
	}
	public List<WebElement> EIAcategoryaveragePer() {
		return SeleniumUtil.getWebElements(d, "EIAcategoryaveragePer", pageName, null);
	}
	public List<WebElement> EIAspendingAmount() {
		return SeleniumUtil.getWebElements(d, "EIAspendingAmount", pageName, null);
	}
		public WebElement EIAwidgetSummaryTitle() {
		return SeleniumUtil.getWebElement(d, "EIAwidgetSummaryTitle", pageName, null);
	}
	public WebElement EIAwidgetSummaryAmount() {
		return SeleniumUtil.getWebElement(d, "EIAwidgetSummaryAmount", pageName, null);
	}
	public WebElement EIAlegendAvg() {
		return SeleniumUtil.getWebElement(d, "EIAlegendAvg", pageName, null);
	}
	public WebElement EIAbarchartHeader() {
		return SeleniumUtil.getWebElement(d, "EIAbarchartHeader", pageName, null);
	}
	public WebElement EIAspendingTopContentCard() {
		return SeleniumUtil.getWebElement(d, "EIAspendingTopContentCard", pageName, null);
	}
	
	public WebElement EIAspendingsdonut() {
		return SeleniumUtil.getWebElement(d, "EIAspendingsdonut", pageName, null);
	}
	public WebElement EIAspendingsWidgetHeader() {
		return SeleniumUtil.getWebElement(d, "EIAspendingsWidgetHeader", pageName, null);
	}
	public WebElement EIAspendingsBarChart() {
		return SeleniumUtil.getWebElement(d, "EIAspendingsBarChart", pageName, null);
	}
	public WebElement EIAspendingAvgContentCard() {
		return SeleniumUtil.getWebElement(d, "EIAspendingAvgContentCard", pageName, null);
	}
	public WebElement EIAnoDataScreen_Header() {
		return SeleniumUtil.getWebElement(d, "EIAnoDataScreen_Header", pageName, null);
	}
	
	public WebElement EIAnoDataScreen_Description() {
		return SeleniumUtil.getWebElement(d, "EIAnoDataScreen_Description", pageName, null);
	}
	public WebElement EIAnoDataScreen_Button() {
		return SeleniumUtil.getWebElement(d, "EIAnoDataScreen_Button", pageName, null);
	}
	public List<WebElement> EIAdurationContainer() {
		return SeleniumUtil.getWebElements(d, "EIAdurationContainer", pageName, null);
	}
	
	//Added my Mallika for Stickyness Group
	public WebElement EIAspendingWidgetCard() {
		return SeleniumUtil.getWebElement(d, "EIAspendingWidgetCard", "DashboardPage", null);
	}
}
