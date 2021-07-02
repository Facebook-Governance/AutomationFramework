/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/

package com.omni.pfm.AccountSharing;

import java.awt.AWTException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AddAndEditTags_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class AccountSharing_AlertSettings_Test extends TestBase{
	Logger logger=LoggerFactory.getLogger(AccountSharing_AlertSettings_Test.class); 
	LoginPage_SAML3 SAMlLogin;
	public static String advisorRef;
	public static String investorName;
	public static String advisorName;
	AccountAddition accountAdd;
	AccountSharing_Loc accountSharing;
	Add_Manual_Transaction_Loc addManualTransaction;
	Transaction_AddAndEditTags_Loc EditExistingTransac;

	@BeforeClass()
	public void init() {
		doInitialization("Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		SAMlLogin=new LoginPage_SAML3();
		accountAdd=new AccountAddition();
		accountSharing=new AccountSharing_Loc(d);
		addManualTransaction=new Add_Manual_Transaction_Loc(d);
		EditExistingTransac =new Transaction_AddAndEditTags_Loc(d);
	}


	@Test(description="creating Advisor", priority=1)
	public void creatingAdvisor() {
		advisorRef=SAMlLogin.getRefrenceId();
		advisorName=SAMlLogin.createAdvisor2(d, investorName, advisorRef,"10003700");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="AT-72780,AT-72770,AT-72779,AT-72775:The advisor should not be shown the 'Link Account' button when he has no accounts.",
			dependsOnMethods="creatingAdvisor",priority=2)
	public void verifyAdvNoAcntScreen() {

		Assert.assertFalse(accountSharing.isLinkAccountBtnPresent());
		Assert.assertEquals(accountSharing.NoAcntDesertScreen().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorNoAcntScreen").trim());
	}

	@Test(description="AT-67567,:The investor should be allowed to aggregate his accounts.",dependsOnMethods="creatingAdvisor", priority=3)
	public void addAcntToAdvisor() throws InterruptedException, AWTException {

		SAMlLogin.login2(d, advisorName, investorName,"10003600");
		accountSharing.getStartedFL();
		accountAdd.addAggregatedAccount("sarv10.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="creating investor", priority=4,dependsOnMethods= {"creatingAdvisor","addAcntToAdvisor"})
	public void creatingAndLoginToInvestor() {

		investorName=SAMlLogin.getInvestorUserName();
		SAMlLogin.createInvestor2(d, advisorName, investorName, null, null);
	}

	@Test(description="AT-67567:The investor should be allowed to aggregate his accounts.",dependsOnMethods="creatingAndLoginToInvestor", priority=5)
	public void loginToInvestor() throws InterruptedException, AWTException {

		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description="AT-67568,AT-67562:The investor should be allowed to allow or deny access to the advisor.",dependsOnMethods="loginToInvestor", priority=6)
	public void sharingAllAcntWithAdvisor() {

		accountSharing.SharingAllAcntsFullAccess();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}

	@Test(description="verifying closing investor full access shared account in expense analysis.",dependsOnMethods="creatingAdvisor", priority=7)
	public void loginToAdv() {

		SAMlLogin.login2(d, advisorName, investorName,"10003700");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="verifying Adviosor main menu size", dependsOnMethods="loginToAdv",priority=8)
	public void verifyMainMenuSizeForAdvisor() throws InterruptedException, AWTException {
		int expected=Integer.parseInt(PropsUtil.getDataPropertyValue("MenuSizeForAdvisor").trim());
		Assert.assertEquals(accountSharing.dummyGeneratorHeader(),expected);		
	}

	@Test(description="AT-61814:AT-61815:Validating adviosr deoployed finapp",dependsOnMethods="loginToAdv", priority=9)
	public void verifyMainMenuItemsForAdvisor() {
		String[] expectedMainMenu=PropsUtil.getDataPropertyValue("MainMenuListForAdvisorView").split(",");
		for(int i=0;i<accountSharing.dummyGeneratorHeader();i++) {
			String actual=accountSharing.getAllMainMenu().get(i).getText().trim();
			System.out.println("Actual :" +actual+ " Expected :" +expectedMainMenu[i]);
			Assert.assertEquals(actual, expectedMainMenu[i]);
		}
	}

	@Test(description="AT-61824:AT-61826:AT-61813:verifying budget should not be deployed for advior",dependsOnMethods="loginToAdv", priority=10)
	public void verifyingSpendingSubMenuForAdvisor() throws InterruptedException, AWTException {

		SeleniumUtil.click(accountSharing.spendingdropdown());
		for(int i=0;i<accountSharing.spendingSubMenu().size();i++) {
			String actual=accountSharing.spendingSubMenu().get(i).getText().trim();
			Assert.assertFalse(actual.contains(PropsUtil.getDataPropertyValue("SpendingSubMenu").trim()));
		}

	}

	@Test(description="AT-61816,AT-61815,AT-61825:AT-72781,AT-72783,AT-72771,AT-72772:The Alert settings finapp should not be deployed for Advisors.",dependsOnMethods="loginToAdv", priority=11)
	public void verifyingSettingsSubMenuForAdvisor() throws InterruptedException, AWTException {

		SeleniumUtil.click(accountSharing.settingdropdown());
		for(int i=0;i<accountSharing.settingsSubMenu().size();i++) {
			String actual=accountSharing.settingsSubMenu().get(i).getText().trim();
			Assert.assertFalse(actual.contains(PropsUtil.getDataPropertyValue("settingsSubMenu").trim()));
		}		
	}

	@Test(description="AT-61819,AT-72785:The Alert settings link should not be shown under the 'More' drop down for Income/Expense Analysis for advisors.",dependsOnMethods="loginToAdv", priority=12)
	public void verifyAlertSettingInIE() {
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();	
		SeleniumUtil.click(accountSharing.gotoAnalysisBtn());

		accountSharing.verifyRightSideIcons();
	}

	@Test(description="AT-61823,AT-72786:The Alert settings link should not be shown under the 'More' drop down for Cash Flow for advisors.",dependsOnMethods="loginToAdv", priority=13)
	public void verifyAlertSettingInCashFlow() {

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();	

		accountSharing.verifyRightSideIcons();
	}

	@Test(description="AT-61822,AT-72787:The Alert settings link should not be shown under the 'More' drop down for Net Worth for advisors.",dependsOnMethods="loginToAdv", priority=14)
	public void verifyAlertSettingInNW() {

		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.waitForPageToLoad();	

		SeleniumUtil.click(accountSharing.goToNetworth());
		accountSharing.verifyRightSideIcons();
	}

	@Test(description="AT-61820,AT-72788:The Alert settings link should not be shown under the 'More' drop down for Investment Holdings for advisors.",dependsOnMethods="loginToAdv", priority=15)
	public void verifyAlertSettingInIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad();	

		SeleniumUtil.click(accountSharing.goToMyInvestments());
		accountSharing.verifyRightSideIcons();
	}

	@Test(description="IE-02_03, AT-67567:The investor should be allowed to aggregate his accounts.",dependsOnMethods= {"creatingAdvisor","addAcntToAdvisor"}, priority=16)
	public void advisorToInvesorSharing() throws InterruptedException, AWTException, JSONException {

		String samlResponse= LoginPage_SAML3.loginResponse(d,advisorName,null);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);	
	}


	@Test(description="AT-72784:On the Accounts finapp, the Alert settings link should not be shown for aggregated or manual accounts.",dependsOnMethods= "creatingAdvisor", priority=17)
	public void verifyAlertSettingAtAcntLevel() {
		SAMlLogin.login2(d, advisorName, investorName,"10003700");
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(2000);
		accountSharing.alertSettingUnderMoreInAdv();
	}

	@Test(description="AT-61818,AT-72773:If the advisor changes a category in the transactions finapp, the 'Create Rule' option should not be shown to the user.",dependsOnMethods="verifyAlertSettingAtAcntLevel",priority=18)
	public void verifyCreateRuleBtn() {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(EditExistingTransac.projectedTransactionDD());
		SeleniumUtil.click(EditExistingTransac.projectedTransactionList().get(0));
		addManualTransaction.selectingCategory(1,1);

		boolean isShown=false;
		try {
			if(addManualTransaction.createRuleBtn().isDisplayed() || addManualTransaction.createRuleMsg().isDisplayed()) {
				isShown=true;
			}

		}catch(Exception e) {

		}
		Assert.assertFalse(isShown);
	}

	@Test(description="AT-61821,AT-72774:In the Account Settings finapp, the advisor should be allowed to change category at account level for advisors.",dependsOnMethods="verifyAlertSettingAtAcntLevel",priority=19)
	public void categosizeFromAcntSettings() {

		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.settingAtAcntLevel().get(0));
		SeleniumUtil.click(accountSharing.categoryDropDown());
		SeleniumUtil.click(accountSharing.catgoryList(1,1));

		Assert.assertEquals(accountSharing.categoryDropDownValue().getText(), PropsUtil.getDataPropertyValue("categoryDropDownValue").trim());
	}

	@Test(description="AT-72790,AT-72789:There should be a message shown for the Investor:  Accounts shared to the user will not be considered for alerts", dependsOnMethods="loginToInvestor",priority=20)
	public void loginToInv() {

		SAMlLogin.login2(d, investorName, null,"10003700");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AlertSetting", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountSharing.alertSharedDisclaimerMsg().getText(), PropsUtil.getDataPropertyValue("alertSharedDisclaimerMsg").trim());
	}

	@Test(description="AT-7279:Any account shared by the advisor should not be shown on the Accounts tab in the Alert settings finapp.",priority=21)
	public void verifyInvAlertSettingsFinapp() {

		Assert.assertEquals(accountSharing.AlertSettingContainer().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("AlertSettingContainerCount")));
	}

	@Test(description="AT-72798:On the Investor's view, Income/Expense Analysis should display 'Alert Settings' but should consider only accounts aggregated by the investor",dependsOnMethods="loginToInv", priority=22)
	public void verifyInvAlertSettingInIE() {
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(3000);	
		accountSharing.IncomeExpenseFtueCompletion();

		SeleniumUtil.click(accountSharing.moreButon());
		accountSharing.verifyMoreDropdown(3);
	}

	@Test(description="AT-72799:On the Investor's view, Cash Flow should display 'Alert Settings' but should consider only accounts aggregated by the investor",dependsOnMethods="loginToInv", priority=23)
	public void verifyInvAlertSettingInCashFlow() {

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);	

		SeleniumUtil.click(accountSharing.moreButon());
		accountSharing.verifyMoreDropdown(2);
	}

	@Test(description="AT-72800:On the Investor's view, Net Worth should display 'Alert Settings' but should consider only accounts aggregated by the investor",dependsOnMethods="loginToInv", priority=24)
	public void verifyInvAlertSettingInNW() {

		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.waitForPageToLoad(3000);	

		SeleniumUtil.click(accountSharing.continueButton());
		SeleniumUtil.click(accountSharing.goToNetworth());
		SeleniumUtil.click(accountSharing.moreButon());
		accountSharing.verifyMoreDropdown(2);
	}

	@Test(description="AT-72801:On the Investor's view, Investment Holdings should display 'Alert Settings' but should consider only accounts aggregated by the investor",dependsOnMethods="loginToInv", priority=25)
	public void verifyInvAlertSettingInIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad(3000);	

		SeleniumUtil.click(accountSharing.continueButton());
		SeleniumUtil.click(accountSharing.goToMyInvestments());
		SeleniumUtil.click(accountSharing.moreButon());
		accountSharing.verifyMoreDropdown(2);
	}

	@Test(description="AT-61817,AT-72791:Alerts only for aggregated accounts should be shown to the Investor on the accounts finapp.",dependsOnMethods="loginToInv",priority=26)
	public void verifyAlertSettingUnderMoreForSharedAcnt() {
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertFalse(accountSharing.alertSettingUnderMoreInInvForShared());	
	}

	@Test(description="AT-72797:Alert settings should not be displayed for accounts shared to the advisor on Account Summary.",dependsOnMethods="loginToInv",priority=27)
	public void verifyAlertSettingUnderMoreForOwnAcnt() {
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(accountSharing.alertSettingUnderMoreInInvForOwnAcnt());	
	}	
}

