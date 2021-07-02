package com.omni.pfm.FinCheckV2;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Credit_Score_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author sbhat1
 */
public class FincheckV2_Indicator_Credit_Score_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Indicator_Credit_Score_Test.class);
	FincheckV2_Credit_Score_Loc v2CreditScore;
	AccountAddition accountAddition;
	FincheckV2_GetStarted_Loc onboard;
	String platformName = null;
	

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2CreditScore = new FincheckV2_Credit_Score_Loc(d);
		accountAddition = new AccountAddition();
		onboard = new FincheckV2_GetStarted_Loc(d);

		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();

		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.creditScore.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.creditScore.DagPassword"));

		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(description = "Finishing FTUE", priority = 1)
	public void quickOnboarding() {
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();

		onboard.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear1988"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome12k"), 2, 4);

		SeleniumUtil.click(v2CreditScore.creditScoreindicatoroverview());
		
		if(v2CreditScore.isfinV2_mobile_creditind_closeicon_Present())
		{
			SeleniumUtil.click(v2CreditScore.mobileCloseicon());
		
		SeleniumUtil.click(v2CreditScore.creditScoreindicatoroverview());
		}
	}

	@Test(description = "AT-95150::Verifying the TakeAction section description in credit score indocator", dependsOnMethods = {
			"quickOnboarding" }, priority = 2)
	public void takeActionsection() {
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> verifying Take Action description");
		Assert.assertEquals(v2CreditScore.getTakeActionHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditScore.takeactionheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95174:Verifying the TakeAction description", dependsOnMethods = {
			"quickOnboarding" }, priority = 3)
	public void takeActiondescription1() {

		logger.info(">>>>> verifying Take Action description - did You Know");
		Assert.assertEquals(v2CreditScore.getTakeActiondesc1().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.takeactiondesc1"), "*** Description Mismatch.");

		Assert.assertEquals(v2CreditScore.getTakeActiondesc1().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.takeactiondesc1"), "Description mismatch.");

	}

	@Test(description = "AT-95174::Verifying the TakeAction description", dependsOnMethods = {
			"quickOnboarding" }, priority = 4)
	public void takeActiondescription2() {

		logger.info(">>>>> verifying Take Action description - To increase the credit score ");
		Assert.assertEquals(v2CreditScore.getTakeActiondesc2().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.takeactiondesc2"), "*** Description mismatch.");

	}

	@Test(description = "AT-95174::Verifying the TakeAction description", dependsOnMethods = {
			"quickOnboarding" }, priority = 5)
	public void takeActiondescription3() {
		int credit = Integer.parseInt(PropsUtil.getDataPropertyValue("finV2.creditScoreValue"));
		String creditText[] = PropsUtil.getDataPropertyValue("finv2.creditscore.takeactiondesc").split(",");
		for (int i = 1; i < credit; i++) {
			logger.info(">>>>> verifying Take Action description - credit score factors ");
			Assert.assertEquals(v2CreditScore.getTakeActiondesc3().get(i).getText().trim(), creditText[i].trim(),
					"*** Credit score text mismatch.");
		}

	}

	@Test(description = "AT-95051::Verifying the credit score details", dependsOnMethods = {
			"quickOnboarding" }, priority = 6)
	public void creditScoredetails() {
		Assert.assertEquals(v2CreditScore.creditScoredetails().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.scoredetails1"), "*** Score List mismatch.");

	}

	@Test(description = "AT-95898:Verify the score details section in credit score indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 7)
	public void creditScoredetailssec() {
		Assert.assertEquals(v2CreditScore.creditScoredetailssec().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.scoredetailsheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95918:Verify the text update your score and about your score in score details section", dependsOnMethods = {
			"quickOnboarding" }, priority = 8)
	public void creditScoredetailssec1() {
		Assert.assertEquals(v2CreditScore.creditScoredetailssec1().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.scoredetailssec1"), "*** Description mismatch.");

		Assert.assertEquals(v2CreditScore.creditScoredetailssec2().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.scoredetailssec2"), "*** Description mismatch.");

	}

	@Test(description = "AT-96376:Verify the credit score screen displayed", dependsOnMethods = {
			"quickOnboarding" }, priority = 9)
	public void creditScoresettingsscreen() {
		SeleniumUtil.click(v2CreditScore.creditScoredetailssec1());
		Assert.assertEquals(v2CreditScore.creditScoreseheader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.setscreenheader"), "*** Header mismatch.");
	}

	@Test(description = "AT-96376:Verify the credit score values displayed in settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 10)
	public void creditScorevalues() {
		if(v2CreditScore.isfinV2_mobile_creditind_values_Present())
		{
			SeleniumUtil.click(v2CreditScore.mobileCreditvalues().get(1));
		}
		
		else {
		
		String creditScoreoptions[] = PropsUtil.getDataPropertyValue("finv2.creditscore.options").split(",");
		for (int i = 0; i < 6; i++) {
			logger.info(">>>>> verifying The credit score options");
			Assert.assertEquals(v2CreditScore.creditScoreoptions().get(i).getText().trim(),
					creditScoreoptions[i].trim(), "*** Score options mismatch.");
		}
		}

	}

	@Test(description = " AT-96379 : Verify user selected value in FTUE,selected in settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 11)
	public void chkCreditscorevalueselection() {
		logger.info(">>>>> Verify credit score value selection");
		String aria = v2CreditScore.creditScoreoptionsradbtn().get(1).getAttribute("aria-checked");
		String ariaExpected = "true";
		Assert.assertEquals(aria, ariaExpected, "*** Area mismatch.");

	}

	@Test(description = " AT-96380 : Verify the radio btn displayed for each of the values", dependsOnMethods = {
			"quickOnboarding" }, priority = 12)
	public void chkCreditradiobtn() {
		

		if(v2CreditScore.isfinV2_mobile_creditind_options())
		{
			logger.info(">>>>> Verify credit score radio btn displayed in mobile");
			int credOpt = Integer.parseInt(PropsUtil.getDataPropertyValue("finV2.creditScoreOptionmobile"));
			String creditScoreoptions[] = PropsUtil.getDataPropertyValue("finv2.creditscore.optionsmobile").split(",");
			for (int i = 0; i < credOpt; i++) {
				Assert.assertEquals(v2CreditScore.creditScoreoptions().get(i).getText().trim(),
						creditScoreoptions[i].trim(), "*** Score options mismatch.");
			}
		} else {
		
		
		
		logger.info(">>>>> Verify credit score radio btn displayed in web");
		int credOpt = Integer.parseInt(PropsUtil.getDataPropertyValue("finV2.creditScoreOption"));
		String creditScoreoptions[] = PropsUtil.getDataPropertyValue("finv2.creditscore.options").split(",");
		for (int i = 0; i < credOpt; i++) {
			Assert.assertEquals(v2CreditScore.creditScoreoptions().get(i).getText().trim(),
					creditScoreoptions[i].trim(), "*** Score options mismatch.");
		}
	}
		
	}

	@Test(description = " AT-96381 : Verify cancel button displayed in the credit score screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 13)
	public void creditscoreCancelbtn() {
		logger.info(">>>>> Verify cancel button displayed in the creditscore screen");
		Assert.assertTrue(v2CreditScore.creditScorecancelbtn().isDisplayed(), "*** Buton not displayed.");

	}

	@Test(description = "AT-96381 : Verify update button displayed in the credit screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 14)
	public void creditscoreUpdatebtn() {
		logger.info(">>>>> Verify update button displayed in the credit screen");
		Assert.assertTrue(v2CreditScore.creditScoresupdatebtn().isDisplayed(), "*** Button not displayed.");

	}

	@Test(description = "AT-96388 : Verify user selected credit score value displayed in score section", dependsOnMethods = {
			"quickOnboarding" }, priority = 15)
	public void creditscoreValueupdate() {
		logger.info(">>>>> Verify credit score value displayed in score screen");
		SeleniumUtil.click(v2CreditScore.creditScoreoptionsradbtn().get(3));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(v2CreditScore.creditScoresupdatebtn());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(v2CreditScore.creditScoredetails().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.scorevalue"), "*** Score Value mismatch.");

	}

	@Test(description = "AT-96385:Verify the back link displayed in the credit set screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 16)
	public void crdeitScoresetbacklink() {
		SeleniumUtil.click(v2CreditScore.creditScoredetailssec1());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(v2CreditScore.creditScorebacklink().isDisplayed(), "*** Score link not displayed.");
	}

	@Test(description = "AT-96386:Verify the finapp navigates back to ind screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 17)
	public void navigateBacktoindscreen() {
		
		
		if(v2CreditScore.isfinV2_mobile_creditind_backicon())
		{
			v2CreditScore.mobileBackicon().isDisplayed();
		}
		
		else {
			
		SeleniumUtil.click(v2CreditScore.creditScorebacklink());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(v2CreditScore.creditScoreheadertext().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.headertext"), "*** Header mismatch.");
	}
	}

	@Test(description = "AT-96387:Verify the finapp navigates back to ind screen after click on update btn", dependsOnMethods = {
			"quickOnboarding" }, priority = 18)
	public void navigateBacktoindscreenupdbtn() {
		logger.info(">>>>> Verify finapp naviagtes back to ind details screen");
		SeleniumUtil.click(v2CreditScore.creditScoredetailssec1());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(v2CreditScore.creditScoresupdatebtn());
		SeleniumUtil.waitForPageToLoad(3000);
		if(v2CreditScore.isfinV2_mobile_creditind_backicon())
		{
			v2CreditScore.mobileBackicon().isDisplayed();
		}
		
		else {
		
		Assert.assertEquals(v2CreditScore.creditScoreheadertext().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.creditscore.headertext"), "*** Header mismatch.");
	}
		
	}
}
