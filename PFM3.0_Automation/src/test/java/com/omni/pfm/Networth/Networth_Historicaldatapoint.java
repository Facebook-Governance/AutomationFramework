/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Networth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Networth.Networth_Historicaldataperiod_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Networth_Historicaldatapoint extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(Networth_Historicaldatapoint.class);

	AccountAddition accAddition;

	AccountAddition accountAddition = null;

	Networth_Historicaldataperiod_Loc NetworthHistorical;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {

		accountAddition = new AccountAddition();

		NetworthHistorical = new Networth_Historicaldataperiod_Loc(d);

		doInitialization("Networth Historicaldtapoint");

		TestBase.tc = TestBase.extent.startTest("Networth Historicaldtapoint", "Networth Historicaldtapoint");

		TestBase.test.appendChild(TestBase.tc);

	}

	/**
	 * Enable the classes if historical data point feature is enabled
	 * 
	 * @throws Exception
	 */

	@Test(description = "NW_01_001  Verify login happens succesfully.", priority = 1, enabled = true)
	public void Login() throws Exception {
		SeleniumUtil.refresh(d);
		logger.info("Navigating to Networth Page");
		PageParser.forceNavigate("NetWorth", d);
	}

	@Test(description = "NW_01_002 : AT-61643 : Verify the Historical period 2 Years,3 Years,4 Years and 5 Years displayted in dropdown", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 3, enabled = true)
	public void verifyNetworthHistoricalPeriod() {

		SeleniumUtil.click(NetworthHistorical.networthFilter());

		String[] expectedDataPoints = PropsUtil.getDataPropertyValue("Historicalperiods").trim().split(",");

		for (int i = 5, k = 0; i < NetworthHistorical.historicalPeriod().size() - 1; i++, k++) {
			String actualDataPoint = NetworthHistorical.historicalPeriod().get(i).getText().trim();
			Assert.assertEquals(actualDataPoint, expectedDataPoints[k]);
		}
	}

	@Test(description = "NW_01_003 : AT-72400 :  Verify the Text Change in 2 Years displayed in the Chart if user selects 2 Years historical period in dropdown", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 4, enabled = true)

	public void verifyChangepercentagetextfor2years() {
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NetworthHistorical.historicalPeriod().get(5));
		SeleniumUtil.waitForPageToLoad();
		String actualtext = NetworthHistorical.networthFilterdurationtext().getText().trim();
		System.out.println(actualtext);
		String expectedtext = PropsUtil.getDataPropertyValue("networth.change.text").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Duration text is not displayed!");

	}

	@Test(description = "NW_01_004 : AT-72401 :  Verify the Text Change in 3 Years displayed in the Chart if user selects 3 Years historical period in dropdown", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 5, enabled = true)
	public void verifyChangepercentagetextfor3years() {
		SeleniumUtil.click(NetworthHistorical.networthFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NetworthHistorical.historicalPeriod().get(6));
		SeleniumUtil.waitForPageToLoad();
		String actualtext = NetworthHistorical.networthFilterdurationtext().getText().trim();
		System.out.println(actualtext);
		String expectedtext = PropsUtil.getDataPropertyValue("networth.change.text").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Duration text is not displayed!");
	}

	@Test(description = "NW_01_005 :AT-72402 :  Verify the Text Change in 4 Years displayed in the Chart if user selects 4 Years historical period in dropdown", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 6, enabled = true)
	public void verifyChangepercentagetextfor4years() {
		SeleniumUtil.click(NetworthHistorical.networthFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NetworthHistorical.historicalPeriod().get(7));
		SeleniumUtil.waitForPageToLoad();
		String actualtext = NetworthHistorical.networthFilterdurationtext().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("networth.change.text").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Duration text is not displayed!");
	}

	@Test(description = "NW_01_005 : AT-72403 :  Verify the Text Change in 5 Years displayed in the Chart if user selects 5 Years historical period in dropdown", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 7, enabled = true)
	public void verifyChangepercentagetextfor5years() {
		SeleniumUtil.click(NetworthHistorical.networthFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NetworthHistorical.historicalPeriod().get(8));
		SeleniumUtil.waitForPageToLoad();
		String actualtext = NetworthHistorical.networthFilterdurationtext().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("networth.change.text").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Duration  text is not displayed!");
	}

	@Test(description = "NW_01_006 : AT-72398 :  Verify the Tick mark displayed after selecting the historical period dropdown", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 8, enabled = true)
	public void verifyTickmarkdisplayedHistoricalperiodvalues() {
		boolean tickmarkTrue = false;

		for (int i = 4; i < NetworthHistorical.networtTickmarkHistoricalperiod().size() - 1; i++) {
			SeleniumUtil.click(NetworthHistorical.networthFilter());
			SeleniumUtil.click(NetworthHistorical.historicalPeriod().get(i));
			SeleniumUtil.waitForPageToLoad();

			if (NetworthHistorical.networtTickmarkHistoricalperiod().get(i).getAttribute("class")
					.contains("svg_tick")) {
				tickmarkTrue = true;
			}
		}
		Assert.assertTrue(tickmarkTrue);
	}

	@Test(description = "NW_01_007 : AT-72412,AT-72413,AT-72414,AT-72415 : Verify the values/duration dispalyed in the X-axis fir historicaltimeperiod filters", dependsOnMethods = {
			"Login" }, groups = { "Desktop" }, priority = 9, enabled = true)
	public void verifyXaxisduarationvaluefor2Years() {

		int yearDuration = 5;

		SeleniumUtil.click(NetworthHistorical.networthFilter());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(NetworthHistorical.historicalPeriod().get(yearDuration + 3));
		SeleniumUtil.waitForPageToLoad();

		NetworthHistorical.verifyNetworthDataPointsForYear(yearDuration);

	}

}
