/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Networth;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML2;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FunctionUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

public class Chart extends TestBase {
    
	private static final Logger logger = LoggerFactory.getLogger(Chart.class);
	NetWorth_Loc netWorth =null;
	
	public static List<String> networthAmt=new ArrayList<String>();
	public static List<String> assetAmt=new ArrayList<String>();
	public static List<String> liabilitiesAmt=new ArrayList<String>();

	@BeforeClass()
	public void init()
	{
		doInitialization("Networth Chart");
		netWorth = new NetWorth_Loc(d) ;
	    logger.info("Initializing");
	}
	

	@Test(description = "NWCHART_02_01",priority = 1)

	public void fetchTheValuesOfYAxis() throws Exception 
	{
		logger.info("To find all the values of Y axis");
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2000);

		List<WebElement> locator = netWorth.netWorthChartPoc();
		List<String> networthVal=new LinkedList<String>();
		for (WebElement element : locator) 
		{
			logger.info("Label at Y Axis is : " + element.getText());
			networthVal.add(element.getText());	
		}
		
       logger.info("Y Axis Values are: ["+String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal)+"]" );
       Assert.assertEquals(String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal),PropsUtil.getDataPropertyValue("netWorthChartYAxisValues").trim());
  }

	/**
	 * To find values one by one
	 * 
	 * @throws Exception
	 */

	public void POC2() throws Exception
	{
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2000);
		String val2 = netWorth.getYAxisValue(2).getText();
		String val5 = netWorth.getYAxisValue(5).getText();

		logger.info("\n" + val2 + "--" + val5 + "\n");
		
	}


	@Test(description = "NWCHART_02_02",priority = 2)

	public void fetchTheValueOfXAxis() throws Exception  
	{
		logger.info("To find all the values of X axis");
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2000);
		List<String> networthVal=new LinkedList<String>();
		List<WebElement> locator= netWorth.netWorthChartPocXAxis();
		for (WebElement element : locator)
		{
			String actualVal="";
			logger.info("Label at X Axis is : " + element.getText());
			SeleniumUtil.waitForPageToLoad(2000);
			String actual=element.getText().trim();
			if(actual.contains(PropsUtil.getDataPropertyValue("netWorthChartYear")))
			{
			   actualVal = actual.substring(0, actual.indexOf("2")).trim();
			}
			else
			{
				actualVal = actual;
			}
			networthVal.add(actualVal);
		}
		logger.info("X Axis Values are: ["+String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal)+"]" );
		
		Assert.assertEquals(String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(),networthVal),PropsUtil.getDataPropertyValue("netWorthChartXAxisMonths").trim());  
	}


	@Test(description = "NWCHART_02_03",priority = 3)
	public void fetchingToolTipMonthOfXAxisVal() throws Exception  
	{
		netWorth.forceNavigateToNetWorth();
		List<String> acutalMonth=new LinkedList<String>();
		SeleniumUtil.waitForPageToLoad(2000);
		for (int i = 1; i <= 12; i++) 
		{
			String currentMonthPointer=netWorth.getMonthXAxis(i).trim();
			acutalMonth.add(currentMonthPointer.substring(0, currentMonthPointer.indexOf(" ")));
			logger.info("X Axis Values are " + netWorth.getMonthXAxis(i));
			
		}
		logger.info("Actual month: ["+String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), acutalMonth)+"]");
		
		Assert.assertEquals(String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), acutalMonth),PropsUtil.getDataPropertyValue("netWorthChartXAxisToolTipMonths").trim());
		
	}

	@Test(description = "NWCHART_02_04",priority = 4)
	public void fetchXAxisNWVal() throws Exception 
	{
		List<String> networthVal=new LinkedList<String>();
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2000);
		for (int i = 1; i <= 12; i++) 
		{
			logger.info("NetWorth value : " + netWorth.getNWXAxis(i));
			SeleniumUtil.waitForPageToLoad(1000);
			networthVal.add(netWorth.getNWXAxis(i));
		}
		
		logger.info("NetWorth value: ["+String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal)+"]" );
		Assert.assertEquals(String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal),PropsUtil.getDataPropertyValue("netWorthCahrtXAxisNWValues").trim());
		
	}

	@Test(description = "NWCHART_02_05",priority = 5)

	public void fetchXAxisAssetVal() throws Exception   
	{
		List<String> networthVal=new LinkedList<String>();
		SeleniumUtil.waitForPageToLoad(2000);
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		for (int i = 1; i <= 12; i++)
		{
			networthVal.add(netWorth.getAssetXAxis(i));		
		}
		logger.info("Asset value: ["+String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal)+"]" );
		
		Assert.assertEquals(String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal),PropsUtil.getDataPropertyValue("netWorthChartAssetValue").trim());	
	}


	@Test(description = "NWCHART_02_06",priority = 6)

	public void fetchXAxisLiabilitiesVal() throws Exception 
	{
		List<String> networthVal=new LinkedList<String>();
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2000);
		for (int i = 1; i <= 12; i++) 
		{
			logger.info("i value=="+i);
			String myVal=netWorth.getLiabilitiesXAxis(i);
			logger.info("Liability value : " + myVal);
			
			networthVal.add(myVal);
		}
		logger.info("Liability value: ["+String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal)+"]" );
		Assert.assertEquals(String.join(PropsUtil.getDataPropertyValue("netWorthChartAssetValueSeperator").trim(), networthVal),PropsUtil.getDataPropertyValue("netWorthChartXAxisLiabValues").trim());
		
	}


	@Test(description = "NWCHART_02_07",priority = 7)

	public void getNetWorthValue() throws Exception  
	{
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad(2000);
		for(int i=0; i<12;i++)
		{
			float networth=SeleniumUtil.parseAmount(netWorth.networthAmt.get(i));
			float asset=SeleniumUtil.parseAmount(netWorth.assetAmt.get(i));
			float liabilities=SeleniumUtil.parseAmount(netWorth.liabilitiesAmt.get(i));
			
			float tempnetworth = asset-liabilities;
			
			Float roundOff = new Float(tempnetworth);
			Float roundOffValue = BigDecimal.valueOf(roundOff).setScale(2, RoundingMode.HALF_UP).floatValue();
			float expectedNetWorth = roundOffValue;

			logger.info("for mousehover"+i+","+networth+","+expectedNetWorth);

			Assert.assertEquals(networth,expectedNetWorth);
		}
		
	}
	
	
}
