/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.awt.AWTException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class Accounts_CommonForAllView_Test extends TestBase{
	Logger logger= LoggerFactory.getLogger(Accounts_CommonForAllView_Test.class);
	Accounts_CommonForAllView_Loc Common_View;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException
	{
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		Common_View = new Accounts_CommonForAllView_Loc(d);
	}

	@Test(description="ACCT-06_01: creating user and adding account.", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception
	{
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);	
		SeleniumUtil.waitForPageToLoad(7000);	
	}

	@Test(description = "L1-40127:ACCT-06_02:Verify the link account button appears ", groups = {
			"DesktopBrowsers", "Smoke" },dependsOnMethods="login", priority = 2,enabled=true)
	public void linkAccountBtnEnableChk() throws InterruptedException {
		//finapp.accounts();

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE"))		
		{		
			Assert.assertTrue(Common_View.LinkActBtnMob().isDisplayed());		
		}else{
			Assert.assertTrue(Common_View.LinkActBtn().isDisplayed());		
		}
	}

	@Test(description = "L1-32944:ACCT-06_03:Verify that Amount due should display at account level for the Bills", groups = {
			"DesktopBrowsers", "Smoke" },dependsOnMethods="login", priority = 3,enabled=true)
	public void billsAmtDueDisplayed() throws InterruptedException {

		String getAlertText2 = Common_View.BillAmount().getText().trim();
		Assert.assertTrue(getAlertText2.equalsIgnoreCase("-$1,200.00"));
		System.out.println("billsAmtDueDisplayed-->"+System.currentTimeMillis());

	}

	@Test(description = "L1-32958:ACCT-06_04:Verify that the Account number should display in the masked format eg - x-2333", groups = { "DesktopBrowsers" },
			dependsOnMethods="login",priority = 4,enabled=true)
	public void maskedAcctNum() throws InterruptedException {
		System.out.println("maskedAcctNum-->"+System.currentTimeMillis());
		String actual=Common_View.accountNumberInFIView().get(0).getText().substring(11);
		Assert.assertTrue(actual.matches("[xX]-\\d{1}[xX]{3}"));

	}


	@Test(description = "ACCT-06_05,Verify All the containers present under Account type View.", groups = {"DesktopBrowsers" },
			dependsOnMethods="login",priority = 5,enabled=true)

	public void verifyContainersUnderAccTypeView() throws InterruptedException {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Common_View.accountType());
		SeleniumUtil.waitForPageToLoad();
		String expectedContainers[]= PropsUtil.getDataPropertyValue("MainContainerList").trim().split(",");

		for(int i=0;i<Common_View.MainContainerName(i).size();i++) {

			String actual=Common_View.MainContainerName(i).get(i).getText().trim();
			String expected=expectedContainers[i];
			Assert.assertEquals(actual,expected);
		}
	}

	@Test(description = "L1-32931:ACCT-06_06: Verify cash subcontainers should come under Cash container", groups = {"DesktopBrowsers" }
	,priority = 6,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyCashSubContainers() throws InterruptedException {
		SeleniumUtil.click(Common_View.accountType());
		SeleniumUtil.waitForPageToLoad();
		String CashSubContainer[]=PropsUtil.getDataPropertyValue("CashSubContainers").trim().split(",");

		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("CashSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("CashSubContainersorder")).get(i).getText().trim();


			String expected=CashSubContainer[i];
			Assert.assertEquals(actual,expected);
		}


	}
	@Test(description ="L1-32932:ACCT-06_07:Verify card subcontainers should come under card container", groups = {"DesktopBrowsers" }
	,priority = 7,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyCardSubContainers() throws InterruptedException {
		String CardSubContainer[]=PropsUtil.getDataPropertyValue("CardSubContainers").trim().split(",");

		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("CardSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("CardSubContainersorder")).get(i).getText().trim();


			String expected=CardSubContainer[i];
			Assert.assertEquals(actual,expected);
		}


	}

	@Test(description = "L1-32934:ACCT-06_08:Verify investment subcontainers should come under investment container", groups = {"DesktopBrowsers" }
	,priority = 8,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyInvestmentSubContainers() throws InterruptedException {
		String investmentSubContainer[]=PropsUtil.getDataPropertyValue("InvestmentSubContainers").trim().split(",");

		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("InvestmentSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("InvestmentSubContainersorder")).get(i).getText().trim();
			String expected=investmentSubContainer[i];
			Assert.assertEquals(actual,expected);
		}


	}

	@Test(description = "L1-32933:ACCT-06_09:Verify loan subcontainers should come under loan container", groups = {"DesktopBrowsers" }
	,priority = 9,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyLoanSubContainers() throws InterruptedException {
		String insuranceSubContainer[]=PropsUtil.getDataPropertyValue("InsuranceSubContainer").trim().split(",");

		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("InsuranceSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("InsuranceSubContainersorder")).get(i).getText().trim();
			String expected=insuranceSubContainer[i];
			Assert.assertEquals(actual,expected);
		}



	}
	@Test(description = "ACCT-06_10:Verify insurance subcontainers should come under insurance container", groups = {"DesktopBrowsers" }
	,priority = 10,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyInsuranceSubContainers() throws InterruptedException {
		String insuranceSubContainer[]=PropsUtil.getDataPropertyValue("InsuranceSubContainer").trim().split(",");

		for(int i=0;i<Common_View.getContSubType("5").size();i++) {
			String actual=Common_View.getContSubType("5").get(i).getText().trim();	
			String expected=insuranceSubContainer[i];
			Assert.assertEquals(actual,expected);
		}

	}
	@Test(description = "L1-32937:ACCT-06_11:Verify realEstate subcontainers should come under realEstate container", groups = {"DesktopBrowsers" }
	,priority = 11,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyRealEstateSubContainers() throws InterruptedException {

		String RealEstateSubContainer[]=PropsUtil.getDataPropertyValue("RealEstateSubContainers").trim().split(",");

		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("RealestateSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("RealestateSubContainersorder")).get(i).getText().trim();
			String expected=RealEstateSubContainer[i];
			Assert.assertEquals(actual,expected);
		}

	}

	@Test(description = "L1-32935:ACCT-06_12:Verify Bills subcontainers should come under Bills container", groups = {"DesktopBrowsers" }
	,priority = 12,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyBillsSubContainers() throws InterruptedException {

		String BillsSubContainer[]=PropsUtil.getDataPropertyValue("BillsSubContainers").trim().split(",");

		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("BillsSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("BillsSubContainersorder")).get(i).getText().trim();
			String expected=BillsSubContainer[i];
			Assert.assertEquals(actual,expected);
		}


	}

	@Test(description = "ACCT-06_13:Verify rewards subcontainers should come under rewards container", groups = {"DesktopBrowsers" }
	,priority = 13,dependsOnMethods="verifyContainersUnderAccTypeView",enabled=true)

	public void verifyRewardsSubContainers() throws InterruptedException {

		String RewardsSubContainer[]=PropsUtil.getDataPropertyValue("RewardsSubContainers").trim().split(",");
		for(int i=0;i<Common_View.getContSubType(PropsUtil.getDataPropertyValue("RewardsSubContainersorder")).size();i++) {
			String actual=Common_View.getContSubType(PropsUtil.getDataPropertyValue("RewardsSubContainersorder")).get(i).getText().trim();
			String expected=RewardsSubContainer[i];
			Assert.assertEquals(actual,expected);
		}


	}
	@Test(description = "L1-32941:ACCT-06_14:Verify that Current balance should display at account level for the Cards account", groups = {

	"DesktopBrowsers" },dependsOnMethods="login", priority = 14,enabled=true)
	public void cardCurrBalDisplayed() throws InterruptedException {

		String ccbal = PropsUtil.getDataPropertyValue("ccbal");
		int cardCurrBalDisplayed = Integer.parseInt(ccbal);
		Assert.assertTrue(Common_View.getCurrentBalance().get(cardCurrBalDisplayed).isDisplayed());
	}

	@Test(description = " L1-32942:ACCT-06_15:Verify that Total balance should display at account level for the Investment account", groups = {"DesktopBrowsers" },
			priority = 15,dependsOnMethods="login",enabled=true)
	public void invTotalBalDisplayed() throws InterruptedException {

		String invbalTot = PropsUtil.getDataPropertyValue("invbalTot");
		int invTotalBalDisplayed = Integer.parseInt(invbalTot);
		Assert.assertTrue(Common_View.getCurrentBalance().get(invTotalBalDisplayed).isDisplayed());

	}

	@Test(description = "L1-32997:ACCT-06_16:Verify that when user click on the ^ icon then the content should get collapsed", groups = { "DesktopBrowsers" }, 
			priority = 16,dependsOnMethods="login",enabled=true)
	public void verifyExpandCollaspe() {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(Common_View.collapseIcon());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(Common_View.expandIcon().isDisplayed());

	}

	@Test(description = "ACCT-06_17:Verify the refresh details ", groups = { "DesktopBrowsers" }, priority = 17,
			dependsOnMethods="login",enabled=true)
	public void verifyRefreshDetails() {

		String refreshText = Common_View.refreshText().getText();
		Assert.assertTrue(Common_View.refreshIcn().isDisplayed());
		Assert.assertEquals(refreshText,PropsUtil.getDataPropertyValue( "RefreshText"));

	}
	@Test(description = "L1-32948:ACCT-06_18:Verify that the account balance for each account will display in the User Preferred currency", groups = {

	"DesktopBrowsers" }, priority = 18,dependsOnMethods="verifyRefreshDetails",enabled=true)
	public void userPreferredCurrency() throws InterruptedException {

		SeleniumUtil.click(Common_View.expandIcon());

		int Currencyorder1= Integer.parseInt(PropsUtil.getDataPropertyValue("Currencyorder1"));                
		int Currencyorder2=Integer.parseInt(PropsUtil.getDataPropertyValue("Currencyorder2"));     

		String cashAmt = Common_View.getCurrentBalance().get(Currencyorder1).getText().trim();
		String creditAmt = Common_View.getCurrentBalance().get(Currencyorder2).getText().trim(); 

		char ch = cashAmt.charAt(0);
		cashAmt = String.valueOf(ch);
		char ch1 = creditAmt.charAt(0);
		creditAmt = String.valueOf(ch1);
		Assert.assertTrue(cashAmt.equalsIgnoreCase(PropsUtil.getDataPropertyValue("Currency1")));
		Assert.assertTrue(creditAmt.equalsIgnoreCase(PropsUtil.getDataPropertyValue("Currency2")));


	}

	@AfterClass
	public void checkAcc() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {

		}
	}

}
