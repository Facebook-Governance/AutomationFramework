/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Networth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Networth_Test2 extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(NetWorth_Test.class);
	NetWorth_Loc netWorth = null;
	AccountAddition accAddition;

	@BeforeClass()
	public void init() throws Exception {

		doInitialization("Networth");
		netWorth = new NetWorth_Loc(d);
		accAddition = new AccountAddition();

		// Login, adding account, navigating to Networth finapp
	//	L1NodeLogin.loginExistingUser(d, "PFM1527159461179", "Password#");
	//	L1NodeLogin.loginExistingUser(d, "testl201", "Password#");
		
		PageParser.forceNavigate("NetWorth", d);
	}

	@Test(description = "NETWOR_02_01:User should not see FTUE coachmarks", priority = 1)
	public void verifyFTUEisNotDisplayed() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		Assert.assertNull(netWorth.welcomeCoachMarkHeading());
		
	}
	@Test(description = "NETWOR_02_02:Coachmark should close and other coachmarks should not open", priority = 2)
	public void coachMarkShouldNotOpen() throws Exception {

		SeleniumUtil.click(netWorth.moreIcon());
		SeleniumUtil.click(netWorth.infoCMButton());
		SeleniumUtil.click(netWorth.cmCloseButton());
		Assert.assertNull(netWorth.interactiveChart_CLP());
		
	}
	@Test(description = "NETWOR_02_03:verify Interactive Chart coachmark:", priority = 3)
	public void verifyInteractiveCoachmark() throws Exception {
		
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.moreIcon());
		SeleniumUtil.click(netWorth.infoCMButton());
		netWorth.clickCMNextButton(1);
		
		Assert.assertEquals(netWorth.interactiveChart().getText(),PropsUtil.getDataPropertyValue("CoachmarkHeading2"));
		Assert.assertEquals(netWorth.interactiveChart_Para().getText(),PropsUtil.getDataPropertyValue("netWorthThisChartText"));
		SeleniumUtil.click(netWorth.cmCloseButton(2));
	}
	
	@Test(description = "NETWOR_02_04:Navigate to first CoachMArk by clicking back button", priority = 4)
	public void verifyBackButton() throws Exception {
		
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.moreIcon());
		SeleniumUtil.click(netWorth.infoCMButton());
		netWorth.clickCMNextButton(1);
		netWorth.clickCMBackButton(2);
		Assert.assertEquals(netWorth.interactiveChart_LinkAccount().getText(), PropsUtil.getDataPropertyValue("CoachmarkHeading1"));
		SeleniumUtil.click(netWorth.cmCloseButton(1));
	}
	@Test(description = "NETWOR_02_05:Verify that by default assets and liabilities legends are checked and user see assests-liabilities bars and Net Worth line with summary bubble on chart", priority = 5)
	public void verifyByDefaultLegends() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(netWorth.ChartLegends().get(0).isDisplayed());
		Assert.assertEquals(netWorth.ChartLegends().get(0).getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),"true");
		Assert.assertTrue(netWorth.ChartLegends().get(1).isDisplayed());
		Assert.assertEquals(netWorth.ChartLegends().get(1).getAttribute(PropsUtil.getDataPropertyValue("ArialCheckedLabel")),"true");
		
	}
}
