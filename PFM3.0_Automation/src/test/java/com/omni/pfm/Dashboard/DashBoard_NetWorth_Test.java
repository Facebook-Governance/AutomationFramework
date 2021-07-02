/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.Dashboard.NetworthLoc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_NetWorth_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(DashBoard_NetWorth_Test.class);
	NetworthLoc Networth;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Dashboard-Networth");
		Networth = new NetworthLoc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
	}

	@Test(description = "Verify User Logs in Successfully", groups = {
			"DesktopBrowsers" }, priority = 1, enabled = true)
	public void login() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("****************Navidgated to Dashboard************");
	}

	@Test(description = "AT-76915:Verify that  networth amount is same on dashboard and networth page", groups = {
			"DesktopBrowsers" }, priority = 2, enabled = true)
	public void verifyNetworth() throws Exception {
		SeleniumUtil.click(Networth.NetworthCard());
		SeleniumUtil.waitForPageToLoad();
		logger.info("***********navigated to networth page***********");

		String AssetText = Networth.Assetvl().getText();
		AssetText = AssetText.replaceAll(PropsUtil.getDataPropertyValue("ReplaceAll"), "").trim();
		logger.info("***********calculated the asset amount***********");
		String LiaText = Networth.Liabilitiesvl().getText();
		LiaText = LiaText.replaceAll(PropsUtil.getDataPropertyValue("ReplaceAll"), "").trim();
		logger.info("**********calculated the liability amount***********");

		// int TodayNW = Integer.parseInt(AssetText) -Integer.parseInt(LiaText);
		// Updated By MRQA -- Changed the data type from int to long since int is not
		// supporting for large values and we have cobrands which have diff currency.
		long TodayNW1 = Long.parseLong(AssetText) - Long.parseLong(LiaText);
		int TodayNW = (int) TodayNW1;
		String news = Integer.toString(TodayNW);
		int TodayNw1 = Integer.parseInt(news);
		logger.info("***********calculated networth=assetAmt-liabiltyAmt***********");

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("***********navigated to dashboard page***********");

		// int TotalNw
		// =Integer.parseInt(Networth.Nwvalue().getText().replaceAll(PropsUtil.getDataPropertyValue("ReplaceAll"),
		// "").trim());

		// Updated by MRQA -- Changed the data type from int to long since int is not
		// supporting for large values and we have cobrands which have diff currency.
		long TotalNw1 = Long.parseLong(
				Networth.Nwvalue().getText().replaceAll(PropsUtil.getDataPropertyValue("ReplaceAll"), "").trim());
		int TotalNw = (int) TotalNw1;

		logger.info("***********captured the networth on dashboard page***********");
		Assert.assertEquals(Math.round(TodayNw1), Math.round(TotalNw));
		logger.info("***********networth verified on dashboard and networth page***********");

	}

	@Test(description = "AT-76920:Verify that the Net Worth portion of the widget should display change in Net Worth over the 12-month period.", groups = {
			"DesktopBrowsers" }, priority = 3, enabled = true)
	public void verifyChangepercentage() throws Exception {
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
		Assert.assertTrue(Networth.ChangePR_mob().isDisplayed());
		Assert.assertTrue(Networth.ChangePR_mob().getText().contains(PropsUtil.getDataPropertyValue("PercentSign")));}
		else {
			Assert.assertTrue(Networth.ChangePR().isDisplayed());
			Assert.assertTrue(Networth.ChangePR().getText().contains(PropsUtil.getDataPropertyValue("PercentSign")));
		}
		logger.info(
				"***********Verified that the Net Worth portion of the widget should display change in Net Worth over the 12-month period**********");

	}

	@Test(description = "AT-76919:Verify the text Net WORTH displayed in the Networt portion.", groups = {
			"DesktopBrowsers" }, priority = 4, enabled = true)
	public void verifyNWtText() throws Exception {
		Assert.assertEquals(Networth.Nwtext().getText(), PropsUtil.getDataPropertyValue("NetWorthMainPage"));
		logger.info("***********Verified the text Net WORTH displayed in the Networt portion***********");

	}

	@Test(description = "AT-76915,AT-76919:Verify that Clicking/Tapping the Net Worth portion of the widget should take the Consumer to the full version of the Net Worth FinApp", groups = {
			"DesktopBrowsers" }, priority = 5, enabled = true)
	public void verifyClick() throws Exception {
		SeleniumUtil.click(Networth.NetworthCard());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(Networth.heading().getText(), PropsUtil.getDataPropertyValue("NetWorth"));

		SeleniumUtil.refresh(d);
		logger.info(
				"***********Verified that Clicking/Tapping the Net Worth portion of the widget should take the Consumer to the full version of the Net Worth FinAp***********");
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}

	}

}
