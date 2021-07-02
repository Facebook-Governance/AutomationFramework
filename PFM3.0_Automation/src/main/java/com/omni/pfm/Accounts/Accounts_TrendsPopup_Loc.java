/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.config.Config;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_TrendsPopup_Loc {
	Logger logger=LoggerFactory.getLogger(Accounts_TrendsPopup_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_TrendsPopup_Loc(WebDriver d) {
		this.d = d;
	}
	public WebElement popupBalanceLabel() { 
		return SeleniumUtil.getWebElement(d, "popupBalanceLabel", pageName, frameName);
	}
	public WebElement TrendsPopupHeader() { 
		return SeleniumUtil.getWebElement(d, "TrendsPopupHeader", pageName, frameName);
	}
	public WebElement TrendsPopupCrossIcon() { 
		return SeleniumUtil.getWebElement(d, "TrendsPopupCrossIcon", pageName, frameName);
	}
	public WebElement popupTotalBalanceLabel() { 
		return SeleniumUtil.getWebElement(d, "popupTotalBalanceLabel", pageName, frameName);
	}
	public WebElement popupPrincipalBalanceLabel() { 
		return SeleniumUtil.getWebElement(d, "popupPrincipalBalanceLabel", pageName, frameName);
	}
	public WebElement PopupAcntNameInfo() { 
		return SeleniumUtil.getWebElement(d, "PopupAcntNameInfo", pageName, frameName);
	}
	public void clickingOnCrossIcon(){
		SeleniumUtil.click(TrendsPopupCrossIcon());
		SeleniumUtil.waitForPageToLoad(2000);
	}
	public WebElement TrendsChartViewIcon() { 
		return SeleniumUtil.getWebElement(d, "TrendsChartViewIcon", pageName, frameName);
	}
	public WebElement TrendsTableViewIcon() { 
		return SeleniumUtil.getWebElement(d, "TrendsTableViewIcon", pageName, frameName);
	}
	public void clickingOnViewByTableBtn(){
		SeleniumUtil.click(TrendsTableViewIcon());
	}
	public void clickingOnViewByChartBtn(){
		SeleniumUtil.click(TrendsChartViewIcon());
	}
	public void verifyingTrendsPopupTableHeader(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "TrendsPopupTableHeader", pageName, frameName);
		CommonUtils.assertEqualsListElements(propValue, l);
	}
	public List<WebElement> TrendsPopupDateList() { 
		return SeleniumUtil.getWebElements(d, "TrendsPopupDateList", pageName, frameName);
	}
	public void verifyYAxis(){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "y-axis", pageName, frameName);
		CommonUtils.assertEqualsListElements("ExpectedDataOnYaxis", l);	
	}
	
	public WebElement TrendsPopupNetworthDuration() { 
		return SeleniumUtil.getWebElement(d, "TrendsPopupNetworthDuration", pageName, frameName);
	}
	public String getMonthNameFullInString(int month) {

		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		return monthNames[month];

	}
	public String getMonthNameInString(int month) {

		String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		return monthNames[month];

	}
	public int currentMonth() {

		Calendar now = Calendar.getInstance();
		int i = 0 + now.get((Calendar.MONTH));
		return i;
	}
	public int prevMonths(int index) {

		Calendar now = Calendar.getInstance();

		now.add(Calendar.MONTH, index);

		int i = 0 + now.get((Calendar.MONTH));

		return i;

	}
	public String getExpectedTabulardDate(int month) {

		Calendar now = Calendar.getInstance();

		now.add(Calendar.MONTH, month);

		String date2 = getMonthNameFullInString(prevMonths(month)) + " " + now.get(Calendar.YEAR);

		String dateInfo = date2;

		return dateInfo;

	}

	public void assertDateOption() {
		int trendMonthValue = Integer.parseInt(PropsUtil.getDataPropertyValue("trendMonth"));

		for (int i = 0; i < TrendsPopupDateList().size(); i++) {
			String actual=TrendsPopupDateList().get(i).getText().trim();	
			String expected=getExpectedTabulardDate(i - trendMonthValue);
			Assert.assertEquals(actual, expected);
		}
	}

	public String getExpectedTrendDate() {

		int	month = Integer.parseInt(PropsUtil.getDataPropertyValue("Accounts_DefautTimePeriod"));
		Calendar now = Calendar.getInstance();

		String date1 = getMonthNameInString(currentMonth()) + " " + now.get(Calendar.YEAR);

		now.add(Calendar.MONTH, month);

		String date2 = getMonthNameInString(prevMonths(month)) + " " + now.get(Calendar.YEAR);

		String dateInfo = date2 + " - " + date1;

		System.out.println(dateInfo);

		return dateInfo;
	}

	public WebElement accountslayout() {
		return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
	}



}
