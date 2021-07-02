/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Networth;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.omni.pfm.utility.SeleniumUtil;

public class Networth_Historicaldataperiod_Loc {

	
	WebDriver d;

	String pageName = "NetWorth";

	public Networth_Historicaldataperiod_Loc(WebDriver d)

	{

		this.d = d;
	}
	
	
	
	public List<WebElement> historicalPeriod()
	{
		return SeleniumUtil.getWebElements(d, "networthHistoricalperiod", pageName, null);
	}
	
	public WebElement ftueContinuebutton1()
	{
		return SeleniumUtil.getWebElement(d, "continueButton1", pageName, null);
	}
	
	public WebElement ftueContinuebutton2()
	{
		return SeleniumUtil.getWebElement(d, "networthFTUE", pageName, null);
	}
	
	public WebElement networthFilter()
	{
		return SeleniumUtil.getWebElement(d, "netWorthFilterdropdown", pageName, null);
	}
	
	
	public WebElement networthFilterdurationtext()
	{
		return SeleniumUtil.getWebElement(d, "netWorthChangetext", pageName, null);
	}
	
	
	public List<WebElement> networtTickmarkHistoricalperiod()
	{
		return SeleniumUtil.getWebElements(d, "tickmarkHistoricalperiod", pageName, null);
	}
	
	
	public List<WebElement> xAxisMonthsName()
	{
		return SeleniumUtil.getWebElements(d, "durationMonthsinxaxis", pageName, null);
	}
	
	
	
	
	
    public String getNetworthPoints(int yearDuration) {
        
		int monthsToAdd = 0;
		int gap = 0;

		if (yearDuration == 2) {
			monthsToAdd = 23;
			gap = 2;
		} else if (yearDuration == 3) {
			monthsToAdd = 35;
			gap = 3;
		} else if (yearDuration == 4) {
			monthsToAdd = 47;
			gap = 4;
		} else if (yearDuration == 5) {
			monthsToAdd = 59;
			gap = 5;
		}

		String actualMonthList = "";

		while (monthsToAdd >= 0) {

			String monthList = null;

			Calendar c = Calendar.getInstance();

			c.add(Calendar.MONTH, -monthsToAdd);

			int month = c.get(Calendar.MONTH);

			monthList = getMonthForInt(month);

			actualMonthList = actualMonthList + monthList + " ";

			monthsToAdd = monthsToAdd - gap;

		}

		return actualMonthList;
     }
     
     public static String getMonthForInt(int m) {
         String month = "invalid";
         DateFormatSymbols dfs = new DateFormatSymbols();
         String[] months = dfs.getMonths();
         if (m >= 0 && m <= 11 ) {
             month = months[m];
         }
         return month.substring(0, 3);
     }

	
	
     public void verifyNetworthDataPointsForYear(int yearDuration) {
    	
 		
 		String[] expectedMonths = this.getNetworthPoints(yearDuration).trim().split(" ");
 		List<String> monthsName = new ArrayList<>();
 		for(int i=0; i<this.xAxisMonthsName().size();i++)
 		{	
 			
 			if (yearDuration == 2) {
 				
 				if(i==0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}else if(i%2 == 0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}
 				
 			} else if( yearDuration == 3) {
 				
 				if(i==0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}else if(i%3 == 0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}

 			} else if( yearDuration == 4 ) {
 				
 				if(i==0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}else if(i%4 == 0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}
 				
 			} else if( yearDuration == 5) {
 				
 				if(i==0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}else if(i%5 == 0) {
 					monthsName.add(this.xAxisMonthsName().get(i).getText());
 				}
 				
 			}
 			//i = i+1;
 		}
 		
 		for(int i=0;i<monthsName.size();i++) {
 			
 			String actualMonth,expectedMonth = "";
 					
 			actualMonth = monthsName.get(i).trim();
 			expectedMonth = expectedMonths[i];
 			
 			if (actualMonth.contains(expectedMonth)) {
 				Assert.assertTrue(true);
 				//logger.info(i+"th month data is correct!");
 			} else {
 				Assert.assertTrue(false);
 				//logger.info(i+"th month data is wrong!");
 			}
 			
 		}
    	 
     }
	
	
	
	
}
