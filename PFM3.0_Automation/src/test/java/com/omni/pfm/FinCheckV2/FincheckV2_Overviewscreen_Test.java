/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajani Prakash
*/

package com.omni.pfm.FinCheckV2;

/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Overview_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Overviewscreen_Test extends TestBase {

	public static final Logger logger = LoggerFactory.getLogger(FincheckV2_Overviewscreen_Test.class);
	FincheckV2_Overview_Loc v2overviewloc;
	AccountAddition accountAddition;
	FincheckV2_GetStarted_Loc onboard;
	String platformName = null;
	

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2overviewloc = new FincheckV2_Overview_Loc(d);
		accountAddition = new AccountAddition();
		onboard = new FincheckV2_GetStarted_Loc(d);

		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.overviewDagSite"),
				PropsUtil.getDataPropertyValue("finV2.overviewDagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(description = "Finishing FTUE", priority = 1)
	public void quickOnboarding() {
		PageParser.forceNavigate("FincheckV2", d);
		onboard.quickOnboarding_WithInsurance(PropsUtil.getDataPropertyValue("finV2.birthYear1988"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome12k"), 1, 4);
		

	}

	@Test(description = "AT-96281:Verify the FinCheck Text in Overview Screen Header", priority = 2, dependsOnMethods = "quickOnboarding")
	public void verifyFinCheckHeaderText() {
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad(20000);
		PageParser.forceNavigate("FincheckV2", d);
		
		if(v2overviewloc.isfinV2_mobile_overview_headertext())
		{
			Assert.assertEquals(v2overviewloc.overviewHeadertext().getText().trim(), PropsUtil.getDataPropertyValue("finv2.Overview.mobile.headertext"));
		}
		
		else {
		String actualtext = v2overviewloc.fincheckHeaderText().getText().trim();

		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.HeaderText").trim();
		Assert.assertEquals(actualtext, expectedtext, "***FinCheck Header Text is not Displayed!");

		}
	}

	@Test(description = "AT-96688:Verify the Settings link Displayed in Overview Screen header", priority = 3)
	public void verifySettingsLink() {
		SeleniumUtil.waitForPageToLoad();

		if(v2overviewloc.isfinV2_mobile_overview_settingslink())
		{
			Assert.assertTrue(v2overviewloc.settingsLink().isDisplayed());

		}
		
		else {
			
		
		Assert.assertTrue(v2overviewloc.settingsBtn().isDisplayed());
		}
	}

	@Test(description = "AT-96281:Verify the Financial Health Status text in Overview Screen", priority = 4)
	public void verifyFinancialStatusText() {
		String actualtext = v2overviewloc.healthStatusText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.HealthStatusText").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Health status text is not displayed!");

	}

	@Test(description = "AT-96290:Verify the Financial Health Status Score(Excellent) in Overview Screen", priority = 5)
	public void verifyHealthStatusScore() {
		String actualtext = v2overviewloc.healthStatusScore().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.ExcellentStatusScore").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Health status score is not displayed!");

	}

	@Test(description = "AT-96283:Verify the Overview Description ", priority = 6)
	public void verifyOverviewDescription() {

		if(v2overviewloc.isfinV2_mobile_overview_desclink())
		{
			
			SeleniumUtil.click(v2overviewloc.descLink());
			SeleniumUtil.waitForPageToLoad();
			
		}
		String actualtext = v2overviewloc.finCheckfinv2_OverviewDesc().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.OverviewText").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Overview description is not displayed!");

	}

	@Test(description = "AT-96307:Verify the Spending Header Text", priority = 7)
	public void verifySpendingHeaderText() {
		String actualtext = v2overviewloc.spendingHeaderText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.SpendingHeaderText").trim();

		Assert.assertEquals(actualtext, expectedtext, "*** Spending text is not displayed!");

	}

	@Test(description = "AT-96307:Verify the Saving Header Text", priority = 8)
	public void verifySavingHeaderText() {
		String actualtext = v2overviewloc.savingHeaderText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.SavingHeaderText").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Saving header text is not displayed!");

	}

	@Test(description = "AT-96307:Verify the Borrowing Header Text", priority = 9)
	public void verifyBorrowingHeaderText() {
		String actualtext = v2overviewloc.barrowingHeaderText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.BorrowingHeaderText").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Borrowing header text is not dispalyed !");

	}

	@Test(description = "AT-96307:Verify the Planning Header Text", priority = 10)
	public void verifyPlanningHeaderText() {
		String actualtext = v2overviewloc.planningHeaderText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.PlanningHeaderText").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Planning header text is not  displayed !");

	}

	@Test(description = "AT-96308:Verify the Spend To Income Ratio Title", priority = 11)
	public void verifySpendToIncomeRatioTitle() {
		String actualtext = v2overviewloc.spendToIncomeTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.SpendToIncomeRatioTitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Spend to income ratio title is not displayed!");

	}

	@Test(description = "AT-96291:Verify the Spend To Income Ratio Value", priority = 12)
	public void verifySpendToIncomeRatioValue() {
		String actualtext = v2overviewloc.spendToIncomeValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.SpendToIncomeRatiovalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Spend to income ratio value is not displayed !");

	}

	@Test(description = "AT-96309:Verify the Bill Pay Title", priority = 13)
	public void verifyBillpayTitle() {
		String actualtext = v2overviewloc.billpayTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.BillpayTitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Bill pay title is not displayed !");

	}

	@Test(description = "AT-96297:Verify the Bill Pay Value", priority = 14)
	public void verifyBillpayValue() {
		String actualtext = v2overviewloc.billpayValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.BillsPayvalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Bill pay value is not displayed !");

	}

	@Test(description = "AT-96310:Verify the emergency Savings Title", priority = 15)
	public void verifyEmergencySavingsTitle() {
		String actualtext = v2overviewloc.emergencySavingsTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.EmergencySavingsTitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Emergency savings title is not displayed !");

	}

	@Test(description = "AT-96291:Verify the emergency Savings Value", priority = 16)
	public void verifyEmergencySavingsValue() {
		String actualtext = v2overviewloc.emergencySavingsValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.Emergencysavingsvalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Emergency savings value is not displayed !");

	}

	@Test(description = "AT-96311:Verify the Retirment Savings Title", priority = 17)
	public void verifyRetirementSavingsTitle() {
		String actualtext = v2overviewloc.retirementSavingsTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.RetirementSavingstitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Retirement Savings title not displayed !");

	}

	@Test(description = "AT-61659:Verify the Retirment Savings Value", priority = 18)
	public void verifyRetirementSavingsValue() {
		String actualtext = v2overviewloc.retirementSavingsValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.RetirementSavingsvalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Retirment savings value is not displayed !");

	}

	@Test(description = "AT-96312 : Verify the DebtToIncome Title", priority = 19)
	public void verifyDebtToIncomeTitle() {
		String actualtext = v2overviewloc.debtToIncomeTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.DebtToIncometitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Debttoincome title is not dispalyed !");

	}

	@Test(description = "AT-61660 : Verify the DebtToIncome Value", priority = 20)
	public void verifyDebtToIncomeValue() {
		String actualtext = v2overviewloc.debtToIncomeValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.DebtToIncomevalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Debttoincome ratio value is not displayed !");

	}

	@Test(description = "AT-96313 : Verify the Crdit Score Title", priority = 21)
	public void verifyCreditScoreTitle() {
		String actualtext = v2overviewloc.creditscoreTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.CreditScoretitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Creditscore title is not displayed!");

	}

	@Test(description = "AT-61661 : Verify the Credit Score Value", priority = 22)
	public void verifyCreditScoreValue() {
		String actualtext = v2overviewloc.creditscoreValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.CreditScorevalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Creditscore value is not displayed !");

	}

	@Test(description = "AT-96314 : Verify the Insurance  Title", priority = 23)
	public void verifyInsuranceTitle() {
		String actualtext = v2overviewloc.insuranceTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.InsuranceTitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Insurance title is not displayed !");

	}

	@Test(description = "AT-61662 : Verify the Insurance  Value", priority = 24)
	public void verifyInsuranceValue() {
		String actualtext = v2overviewloc.insuranceValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.Insurancevalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Insurance value is not displayed");

	}

	@Test(description = "AT-96315 :  Verify the Planning  Title", priority = 25)
	public void verifyPlanningTitle() {
		String actualtext = v2overviewloc.planningTitle().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.PlanningTitle").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Planning title is not displayed !");

	}

	@Test(description = "AT-61663 : Verify the Planning  Value", priority = 26)
	public void verifyPlanningValue() {
		String actualtext = v2overviewloc.planningValue().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.Planningvalue").trim();
		Assert.assertEquals(actualtext, expectedtext, "*** Planning value is not displayed !");

	}

	@Test(description = "AT-96316 : Verify the fincheck overview disclosUre text", priority = 27)
	public void verifyDisclosureText() {
		String actualtext = v2overviewloc.OverviewDisclosueText().getText().trim().replaceAll("\n", "");
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.DisclosureText").trim();

		Assert.assertEquals(actualtext, expectedtext, "*** Overview disclosure text is not displayed !");

	}

	@Test(description = "AT-96713 : Verify Fincheck overview screen navigates to settings screen", priority = 28)
	public void verifyFinAppNavigatesToSettingsScreen() {

		if(v2overviewloc.isfinV2_mobile_overview_settingslink())
		{
			SeleniumUtil.click(v2overviewloc.settingsLink());

		} else {
			
		
		
        SeleniumUtil.click(v2overviewloc.settingsBtn());
		}

	}

	@Test(description = "AT-96713 : Verify back link in settings screen", dependsOnMethods = {
			"verifyFinAppNavigatesToSettingsScreen" }, priority = 29)
	public void verifyBacklinkinsettingsscreen() {
		if(v2overviewloc.isfinV2_mobile_overview_backlink())
		{
		 v2overviewloc.backLink().isDisplayed();

		} else {
		
		Assert.assertTrue(v2overviewloc.backLinkinSettingspage().isDisplayed());

	}

	}

	@Test(description = "AT-96714 :  Verify finapp navigates to fincheck overview page after click on back link in Settings screen", dependsOnMethods = {
			"verifyBacklinkinsettingsscreen" }, priority = 30)
	public void verifyClickonbacklink() {

		if(v2overviewloc.isfinV2_mobile_overview_backlink())
		{
		 SeleniumUtil.click(v2overviewloc.backLink());

		} else {

		SeleniumUtil.click(v2overviewloc.backLinkinSettingspage());
		SeleniumUtil.waitForPageToLoad(3000);

		String actualtext = v2overviewloc.fincheckHeaderText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("Overview_HeaderText").trim();

		Assert.assertEquals(actualtext, expectedtext, "*** Fincheck header text is not displayed !");

		}	 
	}

	@Test(description = "AT-96284 : Verify dial guage displayed in Fincheck overview screen and pop up opens after click on it", priority = 31)
	public void verifyDialguagedisplayed() {
		PageParser.forceNavigate("FinCheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(v2overviewloc.dialGuageoverviewscreenv2().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(v2overviewloc.dialGuageoverviewscreenv2());

	}

	@Test(description = "AT-96285 : Verify Overlay header text in Overlay dial guage pop up", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 32)

	public void verifyOverlayheadertext() {
		SeleniumUtil.waitForPageToLoad(5000);
		String actualtext = v2overviewloc.dialGuageoverlaytext().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.dialguagetext");
		Assert.assertEquals(actualtext, expectedtext, "*** Header text not found");

	}

	@Test(description = "AT-96287 : Verify lets'go button displayed in dial guage pop up", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 33)
	public void verifyLetsgobtndialguage() {
		Assert.assertTrue(v2overviewloc.dialGuageoverlaybtn().isDisplayed());

	}

	@Test(description = "AT-96288 : Verify dial guage screen pop up terminates after click on OK and close button", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 34)
	public void verifyDialguagescreenterminates() {
		SeleniumUtil.click(v2overviewloc.dialGuageoverlaybtn());
		if(v2overviewloc.isfinV2_mobile_overview_headertext())
		{
			Assert.assertEquals(v2overviewloc.overviewHeadertext().getText().trim(), PropsUtil.getDataPropertyValue("finv2.Overview.mobile.headertext"));
		}
		
		else {
		
		String actualtext = v2overviewloc.fincheckHeaderText().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.HeaderText");
		Assert.assertEquals(actualtext, expectedtext, "*** header text not found !");

	}

	}

	@Test(description = "AT-96293 : Verify Status score Good is displayed in the dial guage", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 35)
	public void verifyHealthstatusgood() {
		v2overviewloc.healthStatusgood();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		String actualtext = v2overviewloc.healthStatusScore().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.GoodStatusScore").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Health status score is not displayed!");

	}

	@Test(description = "AT-96295 : Verify Status score Fair is displayed in the dial guage", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 36)
	public void verifyHealthstatusfair() {
		v2overviewloc.healthStatusfair();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		String actualtext = v2overviewloc.healthStatusScore().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.FairStatusScore").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Health status score is not displayed!");

	}

	@Test(description = "AT-96297 : Verify Status score AtRisk is displayed in the dial guage", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 37)
	public void verifyHealthstatusatrisk() {
		v2overviewloc.healthStatusAtrisk();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		String actualtext = v2overviewloc.healthStatusScore().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.AtRiskStatusScore").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Health status score is not displayed!");

	}

	@Test(description = " AT-96299 : Verify Status score danger is displayed in the dial guage", dependsOnMethods = {
			"verifyDialguagedisplayed" }, priority = 38)
	public void verifyHealthstatusdanger() {
		v2overviewloc.healthStatusdanger();
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		String actualtext = v2overviewloc.healthStatusScore().getText().trim();
		String expectedtext = PropsUtil.getDataPropertyValue("finv2.Overview.DangerStatusScore").trim();
		Assert.assertEquals(actualtext, expectedtext, "***Health status score is not displayed!");

	}

}
