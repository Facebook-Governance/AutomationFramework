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
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.SeleniumUtil;



public class DashBoradInvestmentLoc {
	
	public WebDriver d;
	public static String pageName="DashboardPage";
	public static String frameName=null;

	public DashBoradInvestmentLoc(WebDriver d) {
		this.d = d;

	}

	public WebElement todaysChange() {
		return SeleniumUtil.getVisibileWebElement(d, "todaysChange", pageName, frameName);
	}

	public WebElement investmentFilterDropDown(int i) {
		return d.findElement(By.xpath("//ul[@id='filterDropdown']/li[" + i + "]"));
	}

	public WebElement assestByDefaultInvestment() {
		return SeleniumUtil.getWebElement(d, "assestByDefault_DIL", pageName, frameName);
	}
	public WebElement InvestmentCard() {
		return SeleniumUtil.getWebElement(d, "card_DIL", pageName, frameName);
	}
	
	public By investmentWidget() {
		return By.xpath("//*[@class=\"ihWidgetContainerCtr\"]//*[@id=\"investmentsTotal\"]");
	}

	public List<WebElement> gettableDonutChart() {
		return SeleniumUtil.getWebElements(d, "gettableDonutChart", pageName, frameName);
		
	}
	public List<WebElement> filterList(){
		return SeleniumUtil.getWebElements(d, "filter_DIL", pageName, frameName);
	}
	public List<WebElement> investmentTotal(){
		return SeleniumUtil.getWebElements(d, "investmenttotal_DIL", pageName, frameName);
	}
	public WebElement totalbalance() {
		return SeleniumUtil.getWebElement(d, "totalAmount_DIL", pageName, frameName);
	}
	public WebElement dropDownInvestment() {
		return SeleniumUtil.getWebElement(d, "dropDownInvestment", pageName, frameName);
	}
	
	public By investmentDropdownInDashboard() {
		return SeleniumUtil.getByObject(pageName, frameName, "dropDownInvestment");
	}
	public WebElement calculatePercentage(int i, int j) {
		return  d.findElement(By.xpath("//div[@id='holdingTypeView']/div[1]//div[" + i + "]//span[" + j + "]"));

	}
	
	public WebElement detailsLink() {
		return SeleniumUtil.getWebElement(d, "detailsLink", pageName, frameName);
	}
	
	public List<WebElement> gettableDonutChartForMobile() {
		return SeleniumUtil.getWebElements(d, "gettableDonutChartForMobile", pageName, frameName);
		
	}

	public WebElement totalbalancemob() {
		return SeleniumUtil.getWebElement(d, "totalAmount_DILmob", pageName, frameName);
	}


	public WebElement FilterDropDown() {
		return SeleniumUtil.getWebElement(d, "FilterDropDown", pageName, frameName);
	}
	public WebElement todayschangemob() {
		return SeleniumUtil.getWebElement(d, "todayschangemob", pageName, frameName);
	} 
}
