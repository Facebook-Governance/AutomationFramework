/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.SFGOLB;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.Page;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountAddition.AccountAdditionOLB;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.L1OLBLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CreateGoal_GetStarted_Loc;
import com.omni.pfm.pages.SFG.SFG_ErrorInAccount_Loc;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_DashBoard_Integeration extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(SFG_DashBoard_Integeration.class);

	SFG_LandingPage_Loc SFG;
	SFG_createGoalEdit_Loc goalEdit;
	SFG_CreateGoal_GetStarted_Loc getStart;
	AccountAdditionOLB accountAdd;
	SFG_ErrorInAccount_Loc errorInAccounts;
	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
	 doInitialization("SFG Login");
     
     TestBase.tc = TestBase.extent.startTest("Login", "Login");
     TestBase.test.appendChild(TestBase.tc);
	SFG = new SFG_LandingPage_Loc(d);
	
	goalEdit = new SFG_createGoalEdit_Loc(d);
	getStart=new SFG_CreateGoal_GetStarted_Loc(d);
	new L1NodeLogin();
	accountAdd=new AccountAdditionOLB();
	errorInAccounts=new SFG_ErrorInAccount_Loc(d);
	d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

}

@Test(description = "AT-58945:Verify that the dashboard should be configurable on OLB.", groups = { "DesktopBrowsers" }, priority = 1)
public void login () throws Exception {
	  L1OLBLogin.login(d, PropsUtil.getDataPropertyValue("SFGOLDMultiAppId"));
		SeleniumUtil.waitForPageToLoad(2000);
		SFG.d=SFG.SwitchToFrame(d);
		//SeleniumUtil.click(SFG.SFGOLBLinkAnAccount());
		Assert.assertEquals(SFG.SFGDashBoardMessage().getText().trim(),PropsUtil.getDataPropertyValue("SFGOLBNoAccountMessage"));
}
@Test(description = "AT-58980:If the user has no linked accounts, the dashboard should show the desert screen.", groups = { "DesktopBrowsers" }, priority = 2)
public void verifyNoAccountMessage () throws Exception {
	//SFG.SwitchToFrame(d);
	String text = SFG.SFGDashBoardMessage().getText();
	System.out.println(text);
	Assert.assertEquals(SFG.SFGDashBoardMessage().getText().trim(),PropsUtil.getDataPropertyValue("SFGOLBNoAccountMessage"));
}

@Test(description = "add account", groups = { "DesktopBrowsers" }, priority = 3)
public void verifyAccountAddition () throws Exception {
	accountAdd.linkAccount(SFG.SFGOLBLinkAnAccount());
	Assert.assertTrue(accountAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2"));
	SFG.d=SFG.SwitchToFrame(d);
}

@Test(description = "AT-58996:Save for a Goal' should be shown as the header.", groups = { "DesktopBrowsers" }, priority = 4,enabled=false)
public void verifSFGHeaderIndashBorad() throws Exception {
	Assert.assertEquals(SFG.SFGDashBoardHeader().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardHeader"));
	
}

@Test(description = "AT-58970:When the user has no goals, the dashboard should show the user the empty state experience.", groups = { "DesktopBrowsers" }, priority = 5)
public void verifSFGDashBoardInfomeasage() throws Exception {
	Assert.assertTrue(SFG.SFGDashBoardMessage().getText().contains(PropsUtil.getDataPropertyValue("SFGDashBoardMessage1")));
	Assert.assertTrue(SFG.SFGDashBoardMessage().getText().contains(PropsUtil.getDataPropertyValue("SFGDashBoardMessage2")));
	

	
	
}

@Test(description = "", groups = { "DesktopBrowsers" }, priority = 6)
public void verifSFGDashBoardLetsGoButton() throws Exception {
	
	Assert.assertTrue(SFG.SFGDashBoardLetsGoButton().isDisplayed());
}
@Test(description = "AT-58971:If the user clicks on the Get Started button, the user should be taken to the SFG finapp.", groups = { "DesktopBrowsers" }, priority = 7)
public void verifSFGDashBoardBackTodashBoard() throws Exception {
	//AT-58972 If the user clicks on the "Get Started" button and if he has no goals, he should be taken to the FTUE page.
	SeleniumUtil.click(SFG.SFGDashBoardLetsGoButton());
	SeleniumUtil.waitForPageToLoad(5000);
	Assert.assertTrue(goalEdit.startGoalGetStartButton().isDisplayed());
	
}


@Test(description = "AT-58946,AT-58974:If the user has only Draft goals, the widget should show him the 'View all goals' option.", groups = { "DesktopBrowsers" }, priority = 8)
public void verifSFGDashBoardDraftGoalverification() throws Exception {
	//AT-58946 All the functionalities should work as it was on the normal finapp.
	SeleniumUtil.waitForPageToLoad();
	SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName1"), PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
	SeleniumUtil.waitForPageToLoad();
    PageParser.forceNavigate("SFG", d);
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(SFG.SFgDraftGoalSaveMyProgress());
	SeleniumUtil.waitForPageToLoad();
	d.navigate().back();
	SeleniumUtil.waitForPageToLoad();

	d.navigate().back();
	SeleniumUtil.waitForPageToLoad();

	d.navigate().refresh();
	SeleniumUtil.waitForPageToLoad();
	SFG.d=SFG.SwitchToFrame(d);
	Assert.assertEquals(SFG.SFGDashBoardViewAllGoals().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardViewAllGoals"));
	Assert.assertEquals(SFG.SFGDashBoardMessage().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardDraftGoalMessage"));

}

@Test(description = "AT-58973:If the user clicks on the Get Started button and if he has goals, he should land on the 'Goals' page.", groups = { "DesktopBrowsers" }, priority = 9)
public void verifSFGDashBoardClikcViewAllForDraft() throws Exception {
  SeleniumUtil.click(SFG.SFGDashBoardViewAllGoals());
  SeleniumUtil.waitForPageToLoad();
  boolean expectedGoalName=false;
	for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
		if(SFG.SFGDraftGoalNames().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim()));
		{
			expectedGoalName=true;
			break;
		}
		
	}
	Assert.assertTrue(expectedGoalName);
	
	
}
@Test(description = "", groups = { "DesktopBrowsers" }, priority = 10)
public void verifSFGDashBoardCreateInProgressGoal() throws Exception {
	
	SeleniumUtil.click(SFG.SFGDraftGoalNames().get(0));
	SeleniumUtil.waitForPageToLoad();
	SFG.createPeogressGoalFromStep2(PropsUtil.getDataPropertyValue("SFGOnetimeFunding"));
	SeleniumUtil.waitForPageToLoad();
	d.navigate().back();
	d.navigate().back();
	SeleniumUtil.waitForPageToLoad();
	d.navigate().refresh();
	SeleniumUtil.waitForPageToLoad();
	SFG.d=SFG.SwitchToFrame(d);
	SeleniumUtil.waitForPageToLoad();
	Assert.assertEquals(SFG.SFGDashBoardGoalName().get(0).getText().trim(), PropsUtil.getDataPropertyValue("SFGGoalName1").trim());

	
}
@Test(description = "", groups = { "DesktopBrowsers" }, priority = 11)
public void verifSFGDashBoardTwoCircle() throws Exception {
	Assert.assertTrue(SFG.SFGDashBoardCircleSymbol().size()==2);
}

@Test(description = "", groups = { "DesktopBrowsers" }, priority = 12)
public void verifSFGDashBoardBackButton() throws Exception {
	boolean expected =false;
	System.out.println(SFG.SFGOLBBackButtonHidden().get(0).getAttribute("aria-hidden").equals("true"));
	System.out.println(SFG.SFGOLBBackButton().size()==1);
	if(SFG.SFGOLBBackButton().size()==1 && SFG.SFGOLBBackButtonHidden().get(0).getAttribute("aria-hidden").equals("true"))
	{
		expected=true;
	}
	Assert.assertTrue(expected);
	
}



@Test(description = "AT-58989:All the cards should be clickable and should navigate to the correct page when clicked on.", groups = { "DesktopBrowsers" }, priority = 13)
public void verifSFGDashBoardClickOnInProgressGoal() throws Exception {
	logger.info("AT-59006:On clicking on a card, the user should be navigated to the Summary page.");
	SeleniumUtil.click(SFG.SFGDashBoardGoalName().get(0));
	SeleniumUtil.waitForPageToLoad();
	Assert.assertEquals(SFG.GoalHeaderInsteps().getText().trim(), PropsUtil.getDataPropertyValue("SFGDashBoardGoalSummeryText").trim());
}

@Test(description = "", groups = { "DesktopBrowsers" }, priority = 14)
public void verifSFGDashViewMoreGoalsInProgressGoalSummery() throws Exception {
	
		Assert.assertEquals(SFG.SFGBackButton().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardViewAllGoals"));
	
}

@Test(description = "", groups = { "DesktopBrowsers" }, priority = 15)
public void verifSFGDashClickViewMoreGoalsInProgressGoalSummery() throws Exception {
	SeleniumUtil.click(SFG.SFGBackButton());
	SeleniumUtil.waitForPageToLoad();
	boolean expectedGoalcards=false;
	for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
		if(SFG.SFGInprogressGoalNames().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim()));
		{
			expectedGoalcards=SFG.SFGInprogressGoalCards().get(i).isDisplayed();
			break;
		}
		
	}
	Assert.assertTrue(expectedGoalcards);
}



@Test(description = "AT-58976,AT-59001:If the user has only In Progress goals, the widget should show him the 'View all goals' option at the end.", groups = { "DesktopBrowsers" }, priority = 16)
public void verifSFGDashBoardNextButton() throws Exception {
	logger.info("AT-59001:The text on the 'View all Goals' card should be View the rest of your active goals or simply start a new goal today!");
	d.navigate().back();
	d.navigate().back();
	d.navigate().refresh();
	SeleniumUtil.waitForPageToLoad();
	SFG.d=SFG.SwitchToFrame(d);
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(SFG.SFGDashBoardNextIcon());
	SeleniumUtil.waitForPageToLoad();
	Assert.assertEquals(SFG.SFGDashBoardViewAllGoalsInLastPAge().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardViewAllGoals"));
	Assert.assertTrue(SFG.SFGDashBoardMessage().getText().contains(PropsUtil.getDataPropertyValue("SFGDashBoardDraftGoalMessage")));
	
	
	
}

@Test(description = "AT-58977:On clicking on the 'View All Goals' button, the user should land on the my goals page.", groups = { "DesktopBrowsers" }, priority = 17)
public void verifSFGDashClickViewMoreGoalsInLastPage() throws Exception {
	SeleniumUtil.click(SFG.SFGDashBoardViewAllGoalsInLastPAge());
	SeleniumUtil.waitForPageToLoad();
	boolean expectedGoalcards=false;
	for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
		if(SFG.SFGInprogressGoalNames().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim()));
		{
			expectedGoalcards=SFG.SFGInprogressGoalCards().get(i).isDisplayed();
			SeleniumUtil.click(SFG.SFGInprogressGoalNames().get(i));
			break;
		}
		
	}
	Assert.assertTrue(expectedGoalcards);
}

@Test(description = "AT-58975,AT-59002:If the user has only Completed goals, the widget should show him the 'View all goals' option.", groups = { "DesktopBrowsers" }, priority = 18)
public void verifSFGDashVerifyIncomepleteGoal() throws Exception {
	logger.info("AT-59002If the user has only Completed goals, the widget should show him the 'View all goals' option. On which the text should be 'You have money tied to your completed goals. Revisit them or simply start a new goal!'");
	SeleniumUtil.waitForPageToLoad();
  SeleniumUtil.click(SFG.SFGCmpleteGoalButton());
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(SFG.SFGCmpleteGoalConfirmationButtonButton());
	SeleniumUtil.waitForPageToLoad();
	d.navigate().back();
	d.navigate().back();
	d.navigate().refresh();
	SeleniumUtil.waitForPageToLoad();
	SFG.d=SFG.SwitchToFrame(d);
	SeleniumUtil.waitForPageToLoad();
	Assert.assertEquals(SFG.SFGDashBoardMessage().getText(), PropsUtil.getDataPropertyValue("SFGdashBoardCompletedGoalMesage"));
	Assert.assertEquals(SFG.SFGDashBoardViewAllGoals().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardCompleteGoalButton"));

	
}
@Test(description = "AT-58978:If the user has only Victory goals, he should be shown the 'Get Started' button.", groups = { "DesktopBrowsers" }, priority = 19)
public void verifSFGDashVictoryGoal() throws Exception {
	SeleniumUtil.click(SFG.SFGDashBoardViewAllGoals());
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(SFG.SFGCmpleteGoalName().get(0));
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(SFG.SFGMarkAsCmpleteButton());
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(SFG.SFGMarkAsCmpletePopUpButton());
	SeleniumUtil.waitForPageToLoad();
	d.navigate().back();
	d.navigate().back();
	d.navigate().refresh();
	SeleniumUtil.waitForPageToLoad();
	SFG.d=SFG.SwitchToFrame(d);
	SeleniumUtil.waitForPageToLoad();
	Assert.assertTrue(SFG.SFGDashBoardLetsGoButton().isDisplayed());

	
}

@Test(description = "AT-58979:On clicking on the 'Get Started' button, the user should be navigated to the FTUE flow.", groups = { "DesktopBrowsers" }, priority = 20)
public void verifSFGDashVerifyVictoryGoalLandingPage() throws Exception {
	SeleniumUtil.click(SFG.SFGDashBoardLetsGoButton());
	SeleniumUtil.waitForPageToLoad();
	Assert.assertTrue(goalEdit.startGoalGetStartButton().isDisplayed());

}
@Test(description = "AT-58984,AT-5899:The maximum number of carousals that can be shown on the widget is 6.", groups = { "DesktopBrowsers" }, priority = 21)
public void verifSFGDashVerify6InInprogressGoal() throws Exception {
	logger.info("AT-58990There should be a carousal shown on the UI");
	
	PageParser.forceNavigate("SFG", d);
	SeleniumUtil.waitForPageToLoad();
	SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName2"),  PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, 
    PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"), PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"), PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
	SeleniumUtil.waitForPageToLoad();
	SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName3"),  PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, 
		    PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"), PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"), PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
	SeleniumUtil.waitForPageToLoad();
	SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName4"),  PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, 
		    PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"), PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"), PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
	SeleniumUtil.waitForPageToLoad();
	SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName5"),  PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, 
		    PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"), PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"), PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
	SeleniumUtil.waitForPageToLoad();
	SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName6"),  PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, 
		    PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"), PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"), PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
	SeleniumUtil.waitForPageToLoad();
	SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName7"),  PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0, 
		    PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"), PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"), PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
	SeleniumUtil.waitForPageToLoad();
	d.navigate().back();
	d.navigate().back();
	d.navigate().refresh();
	SeleniumUtil.waitForPageToLoad(5000);
	SFG.d=SFG.SwitchToFrame(d);
	SeleniumUtil.waitForPageToLoad();
	Assert.assertTrue(SFG.SFGDashBoardCircleSymbol().size()==6);
}

@Test(description = "AT-58986,AT-58993:By default, the user should be able to see a maximum of 5 goals in the same order that is seen on the Goals in Progress page.", groups = { "DesktopBrowsers" }, priority = 22)
public void verifSFGDashVerify5InInprogressGoal() throws Exception {
	logger.info("AT-58993:The transition from one card to another should be smooth");
	logger.info("AT-58994:When the user clicks on a carousal button, the transition should be the same as if he is clicking on the arrows.");
	String  expected[]=PropsUtil.getDataPropertyValue("SFGDashBoardNames").split(",");
        for(int i=0;i<SFG.SFGDashBoardGoalName().size();i++)
        {
        	Assert.assertEquals(SFG.SFGDashBoardGoalName().get(i).getText().trim(),expected[i]);
        	if(SFG.SFGDashBoardNextIcon().isDisplayed())
        	{
        		SeleniumUtil.click(SFG.SFGDashBoardNextIcon());
        		SeleniumUtil.waitForPageToLoad(500);
        	}

 
        }
}

@Test(description = "AT-58999:There should be no arrow on the right side on the last card.", groups = { "DesktopBrowsers" }, priority = 23)
public void verifSFGDashClickCircle() throws Exception {
	SeleniumUtil.click(SFG.SFGDashBoardCircleSymbol().get(3));
	Assert.assertEquals(SFG.SFGDashBoardGoalName().get(3).getText().trim(),PropsUtil.getDataPropertyValue("SFGGoalName5"));
}
@Test(description = "click BackButton", groups = { "DesktopBrowsers" }, priority = 24)
public void verifSFGDashClickBackButton() throws Exception {
	SeleniumUtil.click(SFG.SFGOLBBackButton().get(1));
	Assert.assertEquals(SFG.SFGDashBoardGoalName().get(2).getText().trim(),PropsUtil.getDataPropertyValue("SFGGoalName4"));
}


@Test(description = "AT-58991:The carousal should be clickable on the web", groups = { "DesktopBrowsers" }, priority = 25)
public void verifSFGDashNonextButtonIn5thStep() throws Exception {
	logger.info("AT-58999 There should be no arrow on the right side on the last card.");
	SeleniumUtil.click(SFG.SFGDashBoardCircleSymbol().get(5));
	SeleniumUtil.waitForPageToLoad();
	Assert.assertTrue(SFG.isElementPresent(SFG.SFGDashBoardNextIcon()));
}

@Test(description = "AT-58987:By default, on the 6th card on the carousal should give the user an option to navigate to the SFG goals in progress page", groups = { "DesktopBrowsers" }, priority = 26)
public void verifSFGDashClick6thCircle() throws Exception {
    
	
	SeleniumUtil.click(SFG.SFGDashBoardViewAllGoalsInLastPAge());
	SeleniumUtil.waitForPageToLoad();
	boolean expectedGoalcards=false;
	for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
		if(SFG.SFGInprogressGoalNames().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("SFGGoalName2").trim()));
		{
			expectedGoalcards=SFG.SFGInprogressGoalCards().get(i).isDisplayed();
			break;
		}
		
	}
	Assert.assertTrue(expectedGoalcards);
	
}

@Test(description = "AT-58982:If there is a currency mismatch, the dashboard should show the users the goals as they would normally. The currency mismatch screen should only be shown after the user clicks on a goal.", groups = { "DesktopBrowsers" }, priority = 27,enabled=false)
public void verifCurrencyChangedIcon() throws Exception {
	SFG.changeCurrencyValue(7);
    d.navigate().back();
    d.navigate().back();
    d.navigate().refresh();
    SeleniumUtil.waitForPageToLoad(5000);
    SeleniumUtil.click(SFG.SFGDashBoardGoalName().get(0));
    SeleniumUtil.waitForPageToLoad(5000);
	 Assert.assertTrue(SFG.SFGCurrencyMissMatchIcon().isDisplayed());
    
}
@Test(description = "Creating New Goal and making it as offtrack goal", groups = {
"DesktopBrowsers,sanity" }, priority = 28, enabled = true)
public void errorAccountVerification() throws Exception {
/*	SFG.changeCurrencyValue(13);
	PageParser.forceNavigate("SFG", d);
	SeleniumUtil.waitForPageToLoad(5000);*/
	PageParser.forceNavigate("AccountSettings", d);
	SeleniumUtil.waitForPageToLoad(10000);
	SeleniumUtil.click(SFG.SFGOLBAccountDelete());
	SeleniumUtil.waitForPageToLoad(2000);
	SeleniumUtil.click(SFG.SFGOLBAccountDeleteCheckBox());
	SeleniumUtil.waitForPageToLoad(2000);
	SeleniumUtil.click(SFG.SFGOLBAccountDeletePupUpDete());
	SeleniumUtil.waitForPageToLoad(10000);
	d.navigate().back();
    d.navigate().back();
    d.navigate().refresh();
    Assert.assertEquals(errorInAccounts.DashboardGoalColor().get(0).getCssValue("background-color").trim(), PropsUtil.getDataPropertyValue("DashBoardErrorScreenColor"));
	
}

@Test(description = "Creating New Goal and making it as offtrack goal", groups = {
"DesktopBrowsers,sanity" }, priority = 29, enabled = true)
public void errorAccountVerificationinLandingPage() throws Exception {
	SeleniumUtil.click(errorInAccounts.DashboardGoalColor().get(0));
	SeleniumUtil.waitForPageToLoad(5000);
	Assert.assertEquals(SFG.SFGOLBAccountErrormEssage().getText(), PropsUtil.getDataPropertyValue("SFGErroraccountMessage"));
}
	
	

}
