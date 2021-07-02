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
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountSharing_IEDropDown_Test extends TestBase{
	Logger logger=LoggerFactory.getLogger(AccountSharing_IEDropDown_Test.class);
	LoginPage_SAML3 SAMlLogin;
	public static String advisorRef;
	public static String investorName;
	public static String advisorName;
	AccountAddition accountAdd;
	AccountSharing_Loc accountSharing;

	@BeforeClass()
	public void init() {
		doInitialization("Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		SAMlLogin=new LoginPage_SAML3();
		accountAdd=new AccountAddition();
		accountSharing=new AccountSharing_Loc(d);
	}

	@Test(description="creating Advisor", priority=1,enabled=true)
	public void creatingAdvisor() {
		advisorRef=SAMlLogin.getRefrenceId();
		advisorName=SAMlLogin.createAdvisor2(d, investorName, advisorRef,"10003600");
		System.out.println("Advisor Name:" +advisorName);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="IE-02_03, AT-67567,:The investor should be allowed to aggregate his accounts.", priority=2,dependsOnMethods="creatingAdvisor",enabled=true)
	public void addAcntToAdvisor() throws InterruptedException, AWTException {

		accountSharing.getStartedFL();
		//accountAdd.addAggregatedAccount("sarv10.site16441.1", "site16441.1");
		accountAdd.addAggregatedAccount("EIAAccountSharing.site16441.1", "site16441.1");

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="creating investor", priority=3,dependsOnMethods="creatingAdvisor",enabled=true)
	public void creatingAndLoginToInvestor() {

		investorName=SAMlLogin.getInvestorUserName();
		SAMlLogin.createInvestor2(d, advisorName, investorName, null, null);
	}

	@Test(description="IE-02_03, AT-67567:The investor should be allowed to aggregate his accounts.", priority=4,dependsOnMethods="creatingAndLoginToInvestor",enabled=true)
	public void loginToInvestor() throws InterruptedException, AWTException {

		SeleniumUtil.waitForPageToLoad(3000);
		accountAdd.linkAccount();
		//accountAdd.addAggregatedAccount("AcntTest2.site16441.2", "site16441.2");
		accountAdd.addAggregatedAccount("EIAAccountSharing.site16441.2", "site16441.2");

		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();
	}


	@Test(description="IE-02_04, AT-67568,AT-67562:The investor should be allowed to allow or deny access to the advisor.", priority=5,dependsOnMethods="loginToInvestor",enabled=true)
	public void sharingAcntWithAdvisor() {

		accountSharing.investmentAndInsuranceFullAccessShare();
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.otherAcntFullAccessShare();
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.shareWithViewBalncAccess();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}

	@Test(description="IE-02_03, AT-67567:The investor should be allowed to aggregate his accounts.", priority=6,dependsOnMethods= {"creatingAdvisor","creatingAndLoginToInvestor"},enabled=true)
	public void advisorToInvesorSharing() throws InterruptedException, AWTException, JSONException {
		String samlResponse= LoginPage_SAML3.loginResponse(d,advisorName,null);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);	
	}


	@Test(description="login to advisor and completing ftue view",dependsOnMethods="creatingAdvisor", priority=7,enabled=true)
	public void loginToAdvisor() {

		SAMlLogin.login2(d, advisorName, investorName,"10003700");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(accountSharing.gotoAnalysisBtn());
	}


	@Test(description="IE-02_04,AT-67557,AT-67579,AT-67561,AT-67565:The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.",
			dependsOnMethods="loginToAdvisor",priority=8,enabled=true)
	public void verifyingAdvisorDropDownForEA() {

		accountSharing.navigatingToExpenseAnalysis();
		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.acntsDropDown());

		logger.info("AT-67579: My accounts' should be shown on the drop down.");
		logger.info("AT-67561:The 'Accounts Drop Down' should be split into 'All Expense Accounts and Investor Shared Accounts'.");
		SeleniumUtil.waitForPageToLoad(2000);
		accountSharing.verifyAdvisorAcntDDHeaderInvestor();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(accountSharing.allExpenseHeader().getText().trim(), PropsUtil.getDataPropertyValue("ExpenseAcntDDHeader").trim());
	}

	@Test(description="IE-02_04,AT-67558,AT-67560,AT-67566:The dropdown should show the advisor only the accounts shared by the investor with 'Full Access'.", 
			dependsOnMethods="verifyingAdvisorDropDownForEA",priority=9,enabled=true)
	public void verifyingInvestorsharedAcntsForEA() {

		accountSharing.sharedAcntCount("InvestorSharedAcntIA1");
		logger.info("AT-67560:All the shared accounts should be shown inside the accounts drop down.");
		accountSharing.verifyInvestorSharedAcntNames("SharedAcntNamesEA");
	}

	@Test(description="IE-02_04,AT-67557,AT-67579,AT-67561,AT-67565:The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.",
			dependsOnMethods="loginToAdvisor",priority=10,enabled=true)
	public void verifyingAdvisorDropDownForIA() {

		accountSharing.navigatingToIncomeAnalysis();

		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.acntsDropDown());

		logger.info("AT-67579: My accounts' should be shown on the drop down.");
		logger.info("AT-67561:The 'Accounts Drop Down' should be split into 'All Expense Accounts and Investor Shared Accounts'.");
		SeleniumUtil.waitForPageToLoad(2000);
		accountSharing.verifyAdvisorAcntDDHeaderInvestor();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(accountSharing.allExpenseHeader().getText().trim(), PropsUtil.getDataPropertyValue("IncomeAcntDDHeader").trim());
	}

	@Test(description="IE-02_04,AT-67558,AT-67560,AT-67566:The dropdown should show the advisor only the accounts shared by the investor with 'Full Access'.", 
			dependsOnMethods="verifyingAdvisorDropDownForIA",priority=11,enabled=true)
	public void verifyingInvestorsharedAcntsForIA() {

		accountSharing.sharedAcntCount("InvestorSharedAcntIA1");
		logger.info("AT-67560:All the shared accounts should be shown inside the accounts drop down.");
		accountSharing.verifyInvestorSharedAcntNames("SharedAcntNamesIA");
	}


	@Test(description="verifying accounts dropdown in investor view",dependsOnMethods="creatingAndLoginToInvestor", priority=12,enabled=true)
	public void investorIEPage() {

		SAMlLogin.login2(d, investorName, null,"10003700");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		accountSharing.IncomeExpenseFtueCompletion();
	}


	@Test(description="IE-02_04,AT-67557,AT-67579,AT-67561,AT-67569:The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.", 
			dependsOnMethods="investorIEPage",priority=13,enabled=true)
	public void verifyingInvestorAcntDropDownForEA() {

		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.click(accountSharing.acntsDropDown());

		logger.info("AT-67579: My accounts' should be shown on the drop down.");
		logger.info("AT-67561:The 'Accounts Drop Down' should be split into 'All Expense Accounts and Advisor Shared Accounts'.");
		SeleniumUtil.waitForPageToLoad(2000);
		accountSharing.AcntsHeaderInAcntDDForAdvisor();
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountSharing.allExpenseHeader().getText().trim(), PropsUtil.getDataPropertyValue("ExpenseAcntDDHeader").trim());
	}

	@Test(description="IE-02_04,AT-67558,AT-67560,AT-67570,AT-67571:The dropdown should show the advisor only the accounts shared by the investor with 'Full Access'.",
			dependsOnMethods="verifyingInvestorAcntDropDownForEA",priority=14,enabled=true)
	public void verifyingAdvisorsharedAcntsForEA() {

		accountSharing.sharedAcntCount("InvestorSharedAcntEA");
		logger.info("AT-67560:All the shared accounts should be shown inside the accounts drop down.");
		accountSharing.verifyAdvisorSharedAcntNames("AdvisorSharedAcntNamesEA");
	}

	@Test(description="IE-02_04,AT-67557,AT-67579,AT-67561,AT-67569:The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.", 
			dependsOnMethods="investorIEPage",priority=15,enabled=true)
	public void verifyingInvestorAcntDropDownForIA() {
		accountSharing.navigatingToIncomeAnalysis();

		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountSharing.acntsDropDown());

		logger.info("AT-67579: My accounts' should be shown on the drop down.");
		logger.info("AT-67561:The 'Accounts Drop Down' should be split into 'All Expense Accounts and Advisor Shared Accounts'.");
		SeleniumUtil.waitForPageToLoad(2000);
		accountSharing.AcntsHeaderInAcntDDForAdvisor();
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountSharing.allExpenseHeader().getText().trim(), PropsUtil.getDataPropertyValue("IncomeAcntDDHeader").trim());
	}

	@Test(description="IE-02_04,AT-67558,AT-67560,AT-67570,AT-67571:The dropdown should show the advisor only the accounts shared by the investor with 'Full Access'.",
			dependsOnMethods="verifyingInvestorAcntDropDownForIA",priority=16,enabled=true)
	public void verifyingAdvisorsharedAcntsForIA() {

		accountSharing.sharedAcntCount("InvestorSharedAcntIA");
		logger.info("AT-67560:All the shared accounts should be shown inside the accounts drop down.");
		accountSharing.verifyAdvisorSharedAcntNames("AdvisorSharedAcntNamesIA");
	}


	@Test(description="IE-02_03, AT-67567:The investor should be allowed to aggregate his accounts.", dependsOnMethods="investorIEPage",
			priority=17,enabled=true)
	public void addingAcnt() throws InterruptedException, AWTException {

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("psqa_site.site16441.1", "site16441.1");
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(3000);
	}


	@Test(description="IE-02_04, AT-67568,AT-67562:The investor should be allowed to allow or deny access to the advisor.",dependsOnMethods="addingAcnt", 
			priority=18,enabled=true)
	public void sharingAcntWithAdvisorForGroups() {

		accountSharing.shareWithViewBalncAccess2();
		SeleniumUtil.waitForPageToLoad(2000);
		accountSharing.shareWithFullAccess();
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}

	@Test(description="closing investor full access shared account", dependsOnMethods="investorIEPage",
			priority=19,enabled=true)
	public void closingInvestorSharedAcnt() {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.selectDropDown(d, "TESTDATA", "Accounts Settings");
		SeleniumUtil.click(accountSharing.closeActnOption());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSharing.clsAccConfirmBtn());
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="deleting investorfull access shared account", dependsOnMethods="investorIEPage",priority=20,enabled=true)
	public void deletingInvestorSharedAcnt() {

		SeleniumUtil.selectDropDown(d, "TESTDATA1", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountSharing.deleteAccountLnkAAS());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSharing.deleteconfirmcheckBoxAAS());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSharing.deleteBtnAAS());
		SeleniumUtil.waitForPageToLoad();
	}


	@Test(description="verifying closing investor full access shared account in expense analysis.",dependsOnMethods="closingInvestorSharedAcnt",
			priority=21,enabled=true)
	public void verifyingClosedAcnt() {

		SAMlLogin.login2(d, advisorName, investorName,"10003700");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(accountSharing.selectMonth().get(1));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountSharing.acntsDropDown());
		accountSharing.sharedAcntCountAfterClosingAndDeleting();
		SeleniumUtil.waitForPageToLoad();
		accountSharing.verifyInvestorSharedAcntAfterClosingAndDeleting();
		
	}

	@Test(description="IE-02_04,creating group with balance only accounts in advisor",dependsOnMethods="verifyingClosedAcnt", 
			priority=22,enabled=true)
	public void creatingBalanceOnlyGroup() {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSharing.groupType());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountSharing.getCreateGroup());
		accountSharing.typeGroupName(PropsUtil.getDataPropertyValue("BalanceOnlyGroupName").trim());
		accountSharing.selectCheckBox();
		SeleniumUtil.click(accountSharing.createUpdateGroupBtm());
	}

	@Test(description="IE-02_04,creating group with full access accounts in advisor", dependsOnMethods="verifyingClosedAcnt",
			priority=23,enabled=true)

	public void creatingBalanceOnlyAndFullAccessGroup() {

		SeleniumUtil.click(accountSharing.CreateBtn1());
		accountSharing.typeGroupName(PropsUtil.getDataPropertyValue("BalanceOnlyAndFullAccessGroupName").trim());
		SeleniumUtil.waitForPageToLoad();
		accountSharing.selectCheckBox2();
		SeleniumUtil.click(accountSharing.createUpdateGroupBtm());
	}

	@Test(description="IE-02_04,AT-67574: Any group that has only accounts with 'Balance Only' access level of accounts should not be shown to the advisor", 
			dependsOnMethods="verifyingClosedAcnt",priority=24,enabled=true)
	public void verifyingGroupTypeView() {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		accountSharing.navigatingToAcntDDGroup();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountSharing.accountsDDGroups().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("CreatedGroupSize").trim()));
	}

	@Test(description="IE-02_04,AT-67575:If the investor has created a group with both 'Full Access' and 'Balance Only' levels of access, the group should be shown to the advisor without the 'Balance Only' accounts.", 
			dependsOnMethods="verifyingClosedAcnt",priority=25,enabled=true)
	public void verifyingBalanceOnlyAndFullAccessGroup() {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		accountSharing.navigatingToAcntDDGroup();
		SeleniumUtil.click(accountSharing.accountsDDGroups().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(accountSharing.chartForFullAccess().isDisplayed(), "chart is not displaying for full access shared accounts group");
		Assert.assertTrue(accountSharing.allCateoriesDataForFullAccess().isDisplayed(), "All categories data is not displaying for full access shared accounts group");
	}
}
