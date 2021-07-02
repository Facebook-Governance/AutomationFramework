/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Ashwin PM
*/


package com.omni.pfm.Expense_IncomeAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Chart extends TestBase {

	ExpLandingPage_Loc charts;
	AccountAddition accAddition;

	private static final Logger logger = LoggerFactory.getLogger(Chart.class);
	public static List<String> networthAmt = new ArrayList<String>();
	public static List<String> assetAmt = new ArrayList<String>();
	public static List<String> liabilitiesAmt = new ArrayList<String>();
	public static String[] myList = new String[10];
	public int months, count = 6;

	@BeforeClass(alwaysRun = true)

	public void init() 
	{
		doInitialization("Expense Chart");

		charts = new ExpLandingPage_Loc(d);

		accAddition = new AccountAddition();
	}

	@Test(description = "CHA_01_01: Verify login happens succesfully.", enabled = true, priority = 1)

	public void login() throws Exception
	{
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
	}

	@Test(description = "CHA_01_02: Verify charts X-axis data.", enabled = true, priority = 2)

	public void xaxis()
	{
		WebElement locator = charts.xAxislabel();
		logger.info("Label at X Axis is : " + locator.getText());
		Assert.assertTrue(locator.isDisplayed());
	}

	@Test(description = "CHA_01_03: Verify charts Y-axis data.", enabled = true, priority = 3)

	public void yaxis()
	{
		List<WebElement> locator = charts.locator();
		   for (WebElement element : locator) 
		   {
			logger.info("Label at Y Axis is : " + element.getText());
			Assert.assertTrue(element.isDisplayed());
		   }
	}

	@Test(description = "CHA_01_04: Verify all BarCharts present in charts. ", enabled = false, priority = 4)

	public void compare() 
	{
		List<WebElement> l = charts.barChartsLegend();
		logger.info("the size is" + l.size());

		WebElement highCharts = charts.highCharts();
		for (int i = 1; i <= l.size(); i++) {

			String text = charts.text(i).getText();
			String newtext = text.replaceAll("[$,.]", "");
			int monthSpending = Integer.parseInt(newtext);
			logger.info("The amount is " + monthSpending);

			if (monthSpending > 0)
			{
				logger.info("the charts size is:" + highCharts.isDisplayed());
				Assert.assertTrue(highCharts.isDisplayed());
			}
		}
	}

	@Test(description = "CHA_01_05: Verify highChartsLegends present in charts.", enabled = true, priority = 5)

	public void highChartsLegends()
	{
		WebElement highChartsLegends = charts.highChartsLegends();
		String Actual = highChartsLegends.getText();
		Assert.assertTrue(Actual.contains(PropsUtil.getDataPropertyValue("IA_averageValue")));
	}

	@Test(description = "CHA_01_06: Verify month and balance present in charts", enabled = true, priority = 6)

	public void monthsAndBalance()
	  {
		List<WebElement> monthAndBalance = charts.monthAndBalance();
		for (int i = 0; i < monthAndBalance.size(); i++)
		{
			logger.info("The month and balance are: " + monthAndBalance.get(i).getText());
			Assert.assertTrue(monthAndBalance.get(i).isDisplayed());
		}
	 }

}
