/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Transaction_AccountsSharing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.DownloadTrans_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountSharing_TransactionAccountFilter extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory
			.getLogger(Transaction_AccountSharing_TransactionAccountFilter.class);
	LoginPage login;
	AccountAddition acccuntAdd;
	LoginPage_SAML3 SAMLlogin;
	public static String advisorUsername;
	public static String advisorUsername2;
	public static String InvesterName1;
	public static String InvesterName2;
	public static String InvesterName3;
	public static String addvisorRef1;
	public static String addvisorRef2;
	public static String addvisorRef3;
	public static String investerSharedAccountToAdvisor1;
	public static String investerSharedAccountToAdvisor2;
	public static String investerSharedAccountToAdvisor3;
	Transaction_AccountDropDown_Loc accountDrapDown;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Search_Loc search;
	Transaction_Layout_Loc layout;
	Aggregated_Transaction_Details_Loc aggre_Tra;
	Transaction_Tag_Loc tag;
	DownloadTrans_Loc feature;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;

	@BeforeClass

	public void testInit() throws Exception {

		doInitialization("Transaction Login");
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		acccuntAdd = new AccountAddition();
		SAMLlogin = new LoginPage_SAML3();
		accountDrapDown = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		search = new Transaction_Search_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		aggre_Tra = new Aggregated_Transaction_Details_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		feature = new DownloadTrans_Loc(d);
		createInvestor();
		invester1SharedAccount();
		advisor1SharedAccount();
	}

	public void createInvestor() throws Exception {
		logger.info("Get the unique reference id");
		addvisorRef1 = SAMLlogin.getRefrenceId();
		logger.info("Get the unique investor Name");
		InvesterName1 = SAMLlogin.getInvestorUserName();
		logger.info("create advisor with extra param segmentName=advisor&IID=Investorname ");

		advisorUsername = SAMLlogin.createAdvisor(d, InvesterName1, addvisorRef1, "10003600");
		SeleniumUtil.waitForPageToLoad();

		acccuntAdd.linkAccountFastLink();
		acccuntAdd.addAggregatedAccount("TransactionFilter1.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expectedInvestor = false;
		if (advisorUsername != null) {
			logger.info("create investor and prepop account to investor");
			SAMLlogin.createInvestor(d, advisorUsername, InvesterName1, "TransactionFilter1.site16441.1",
					"site16441.1");
			acccuntAdd.linkAccount();
		//acccuntAdd.addAggregatedAccount1("advisorinvestor1.site16441.1", "site16441.1");
			acccuntAdd.addAggregatedAccount1("advisorinvestor5.site16441.2", "site16441.2");
			acccuntAdd.linkAccount();
		//	acccuntAdd.addContainerAccount("DagInvestments", "advisorinvestor2.Investment1", "Investment1");
			acccuntAdd.addAggregatedAccount("DagInvestments","advisorinvestor2.Investment1", "Investment1");

			expectedInvestor = true;
		}

		Assert.assertTrue(expectedInvestor, "advisor and investor is not created successfully");

	}

	public void invester1SharedAccount() throws Exception {
		logger.info("login to investor");
		// SAMLlogin.login(d, InvesterName1,null);
		// SAMLlogin.login(d, "PFMINV1524651174925",null);
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("click on advisor name in manage sharing");
		for (int i = 0; i < accountDrapDown.AdvisorName().size(); i++) {
			if (accountDrapDown.AdvisorName().get(i).getText().contains(addvisorRef1)) {
				SeleniumUtil.click(accountDrapDown.AdvisorName().get(i));
				break;
			}
		}

		for (int i = 0; i < 3; i++) {
			SeleniumUtil.click(accountDrapDown.accShareDropdown().get(i));
			SeleniumUtil.click(accountDrapDown.accShareDropdownValue(i + 1).get(0));

		}
		SeleniumUtil.click(accountDrapDown.accShareDropdown().get(4));
		SeleniumUtil.click(accountDrapDown.accShareDropdownValue(5).get(1));

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(accountDrapDown.AccountShareSave());
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("share the accounts to selected advisor");
		Assert.assertEquals(accountDrapDown.accShareDropdown().get(0).getText(),
				PropsUtil.getDataPropertyValue("AccountSharedDropdownValueView"), "accounts is not shared to advisor");

	}

	public void advisor1SharedAccount() throws Exception {// we have add after
															// api automation
		logger.info("share the accounts to investor");
		logger.info("login to advisor with extra param segmentName=advisor&IID=Investorname ");
		String samlResponse = SAMLlogin.loginResponse(d, advisorUsername, InvesterName1);
		ysl.shareAdvisorAccount(samlResponse, advisorUsername, InvesterName1);

	}

	@Test(description = "AT-69147:Verify the investor shared accouts Should not displayed in the manual transaction accounts dropdown pop up in advisor view as Advisor can not create manual transactions with shared accounts.",
			priority = 4)
	public void verifyAMTxn_Acc_Advisor() throws Exception {
		logger.info("login to advisor with extra param segmentName=advisor&IID=Investorname ");
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("click on add manual transaction link");
		SeleniumUtil.click(add_Manual.addManualIcon());
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(add_Manual.accountdropdown());
		SeleniumUtil.waitForPageToLoad(1000);
		boolean investorSharedAccount = false;
		logger.info("verifying shared accounts should not  displayed");
		for (int i = 0; i < add_Manual.AccountSharingAccount_AMT().size(); i++) {
			if (add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount1"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount2"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount3"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				investorSharedAccount = false;
				break;
			} else {
				investorSharedAccount = true;
			}
		}
		Assert.assertTrue(investorSharedAccount,
				"investor shared account should not displayed in add manual transaction account dropdown");
	}

	@Test(description = "AT-69151:verify group is displayed in Accounts dropdwon in advisor view when create a group with advisor Aggregated accounts in advisor view",
			priority = 5)
	public void VerifyGrp_GroupCreated_AdvAggAcc_Advisor() throws Exception

	{
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(8000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(8000);
		logger.info("create a group with  advisor aggregated accounts");
		accountDrapDown.creategroupWithAdvisorAggregatedAccount(
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying advisor aggrgated accounts group should displayed in grooup filter");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"))) {
				advisorAggregatedAccountsgroup = true;
				accountDrapDown.transactionAccountDropDownGroupLink().get(i).click();
				SeleniumUtil.waitForPageToLoad(7000);
				break;
			}

		}
		Assert.assertTrue(advisorAggregatedAccountsgroup,
				"advisor aggregated accounts group should  displayed group filter in advisor view");
	}

	@Test(description = "AT-69151:verify group is displayed in Accounts dropdwon in advisor view when create a group with advisor Aggregated accounts in advisor view", priority = 6,
			dependsOnMethods="VerifyGrp_GroupCreated_AdvAggAcc_Advisor")
	public void VerifyGrpSeled_GroupCreated_AdvAggAcc_Advisor() throws Exception {
		logger.info("Selected group should displayed in account filter when you select group");
		Assert.assertEquals(accountDrapDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"), "selected groups should displayed");
	}

	@Test(description = "AT-69151:verify group is displayed in Accounts dropdwon in advisor view when create a group with advisor Aggregated accounts in advisor view",
			priority = 7,dependsOnMethods="VerifyGrp_GroupCreated_AdvAggAcc_Advisor")
	public void VerifyGrpAccTxn_GrpCreated_AdvAggAcc_Advisor() throws Exception {
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info(
				"verifying advisor aggregated accounts transaction should  displayed which is selected in creating group");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			System.out.println(accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " "));
			System.out.println(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"));
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1").trim())
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2").trim())) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"advisor aggregated accounts groups transaction  should displayed ");
	}

	@Test(description = "AT-69151:verify group is displayed in Accounts dropdwon in advisor view when create a group with advisor Aggregated accounts in advisor view", 
			priority = 8,dependsOnMethods="VerifyGrp_GroupCreated_AdvAggAcc_Advisor")
	public void VerifyGrpAccTxn_GrpCreated1_AdvAggAcc_Advisor() throws Exception {
		logger.info("selected groups accounts transactions should ");
		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i < 6; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);

	}

	@Test(description = "AT-69152:verify group is displayed in Accounts dropdwon in advisor view when create a group with investor shared 'VIEW FULL DETAILS accounts in advisor view", 
			priority = 9)
	public void VerifyGrp_GrpCreated_invSharedAcc_Advisor() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("create a group with  investor shared full view details accouns");
		accountDrapDown.creategroupWithinvestorSharedFullViewDetailsAccount(
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName2"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying investor shared full view details accounts group should displayed");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			System.out.println();
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName2"))) {
				advisorAggregatedAccountsgroup = true;
				accountDrapDown.transactionAccountDropDownGroupLink().get(i).click();
				SeleniumUtil.waitForPageToLoad();
				break;
			}

		}
		Assert.assertTrue(advisorAggregatedAccountsgroup,
				"investor shared full view details accounts group should displayed");
	}

	@Test(description = "AT-69152:verify group is displayed in Accounts dropdwon in advisor view when create a group with investor shared 'VIEW FULL DETAILS accounts in advisor view",
			priority = 10,dependsOnMethods="VerifyGrp_GrpCreated_invSharedAcc_Advisor")
	public void VerifyGrpSeled_GrpCreated_InvShareddAcc_Advisor() throws Exception {
		logger.info("Selected group should displayed in account filter when you select group");
		Assert.assertEquals(accountDrapDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName2"), "selected groups should displaying");
	}

	@Test(description = "AT-69152:verify group is displayed in Accounts dropdwon in advisor view when create a group with investor shared 'VIEW FULL DETAILS accounts in advisor view",
			priority = 11,dependsOnMethods="VerifyGrp_GrpCreated_invSharedAcc_Advisor")
	public void VerifyGrpAccTxn_GrpCreated_InveSharedAcc_Advisor() throws Exception {
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info("verifying investor shared full view details accounts groups transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"investor shared full view details accounts groups transaction should displayed");
	}

	@Test(description = "AT-69152:verify group is displayed in Accounts dropdwon in advisor view when create a group with investor shared 'VIEW FULL DETAILS accounts in advisor view", 
			priority = 12,dependsOnMethods="VerifyGrp_GrpCreated_invSharedAcc_Advisor")
	public void VerifyGrpAccTxn_GrpCreated1_InvSharedAcc_Advisor() throws Exception {
		logger.info(
				"investor shared accounts should displayed when you select group which is created with full view acces account");
		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i <= 6; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);

	}

	@Test(description = "AT-69153:verify group should not displayed in Accounts dropwon in advisor view when create a group with investor shared 'BALANCE ONLY' accounts in advisor view", priority = 13)
	public void VerifyGrp_GrpCreated_invSharedAccBalanceView_Advisor() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("create a group with  investor shared balance view details accounts");
		accountDrapDown.creategroupWithinvestorSharedBalanceDetailsAccount(
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName3"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying investor shared balance view details accounts group should not displayed");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName3"))) {
				advisorAggregatedAccountsgroup = false;
				break;
			} else {
				advisorAggregatedAccountsgroup = true;

			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroup,
				"investor shared balance view details accounts group should not displayed in group filter");
	}

	@Test(description = "AT-69154:verify group should displayed in Accounts dropdwon in advisor view when create a group with investor shared VIEW FULL DETAILS' accounts and 'BALANCE ONLY' accounts in advisor view",
			priority = 14)
	public void VerifyGrp_GrpCreated_invSharedAccFullViewBalanceBoth_Advisor() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("create a group with  investor shared full view details accouns and balance view both");
		accountDrapDown.creategroupWithinvestorSharedFullViewBalanceDetailsAccount(
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName4"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying investor shared full view details and balance only accounts group should displayed");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName4"))) {
				advisorAggregatedAccountsgroup = true;
				accountDrapDown.transactionAccountDropDownGroupLink().get(i).click();
				SeleniumUtil.waitForPageToLoad();
				break;
			}

		}
		Assert.assertTrue(advisorAggregatedAccountsgroup,
				"investor shared full view details and balance only accounts group should displayed");
	}

	@Test(description = "AT-69154:verify group should displayed in Accounts dropdwon in advisor view when create a group with investor shared VIEW FULL DETAILS' accounts and 'BALANCE ONLY' accounts in advisor view",
			priority = 15,dependsOnMethods="VerifyGrp_GrpCreated_invSharedAccBalanceView_Advisor")
	public void VerifyGrpSeled_GrpCreated_InvShareddAccFullViewBalanceboth_Advisor() throws Exception {
		logger.info("Selected group should displayed in account filter when you select group");
		Assert.assertEquals(accountDrapDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName4"), "selected groups should displayed");
	}

	@Test(description = "AT-69155:Verify BALANCE ONLY accounts transaction should not displayed in advisor view when create a group with investor shared VIEW FULL DETAILS' accounts and 'BALANCE ONLY' accounts in advisor view",
			priority = 16,dependsOnMethods="VerifyGrp_GrpCreated_invSharedAccBalanceView_Advisor")
	public void VerifyGrpAccTxn_GrpCreatedWith_InveSharedAccFullViewBalanceboth_Advisor() throws Exception {
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info("verifying investor shared full view details accounts  transactions only displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"investor shared full view details accounts groups transaction only displayed and  balance only accounts transactions should not displayed");
	}

	@Test(description = "AT-69156:verify group is displaying in Accounts dropwon in investor view when create a group with investor Aggregated accounts in investor view", priority = 17)
	public void VerifyGrp_GrpCreatedWith_invAggAcc_Investor() throws Exception {
		logger.info("login to investor");
		SAMLlogin.login(d, InvesterName1, null, "10003700");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("create a group with  investor aggregated accounts");
		accountDrapDown.creategroupWithInvestorAggregatedAccount(
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName5"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(2000);
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying investor aggrgated accounts group should displayed");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName5"))) {
				advisorAggregatedAccountsgroup = true;
				accountDrapDown.transactionAccountDropDownGroupLink().get(i).click();
				SeleniumUtil.waitForPageToLoad(10000);
				break;
			}

		}
		Assert.assertTrue(advisorAggregatedAccountsgroup, "investor aggregated accounts group is not displaying");
	}

	@Test(description = "AT-69156:verify group is displaying in Accounts dropwon in investor view when create a group with investor Aggregated accounts in investor view",
			priority = 18,dependsOnMethods="VerifyGrp_GrpCreatedWith_invAggAcc_Investor")
	public void VerifyGrpSeled_GrpCreated_InvAggAcc_Investor() throws Exception {
		logger.info("Selected group should displayed in account filter when you select group");
		Assert.assertEquals(accountDrapDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName5"), "selected groups are not displaying");
	}

	@Test(description = "AT-69156:verify group is displaying in Accounts dropwon in investor view when create a group with investor Aggregated accounts in investor view",
			priority = 19,dependsOnMethods="VerifyGrp_GrpCreatedWith_invAggAcc_Investor")
	public void VerifyGrpAccTxn_GrpCreated_InvAggAcc_Investor() throws Exception {
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info("verifying investor aggregated accounts transaction should  displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"investor aggregated accounts groups transaction transactions are not displaying");
	}

	@Test(description = "AT-69156:verify group is displaying in Accounts dropwon in investor view when create a group with investor Aggregated accounts in investor view",
			priority = 20,dependsOnMethods="VerifyGrp_GrpCreatedWith_invAggAcc_Investor")
	public void VerifyGrpAccTxn_GrpCreated1_InvAggAcc_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();
		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
	}

	@Test(description = "AT-69157:verify group is displaying in Accounts dropwon in investor view when create a group with advisor shared accounts in investor view", priority = 21)
	public void VerifyGrp_GrpCreatedWith_advrSharedAcc_Investor() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(7000);
		logger.info("create a group with  advisor shared  accouns");
		accountDrapDown
				.creategroupWithAdvisorSharedAccount(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName6"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(2000);
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying advisor accounts group should displayed");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName6"))) {
				advisorAggregatedAccountsgroup = true;
				accountDrapDown.transactionAccountDropDownGroupLink().get(i).click();
				SeleniumUtil.waitForPageToLoad();
			}

		}
		Assert.assertTrue(advisorAggregatedAccountsgroup, "advisor shared accounts group is not displaying");
	}

	@Test(description = "AT-69157:verify group is displaying in Accounts dropwon in investor view when create a group with advisor shared accounts in investor view", 
			priority = 22,dependsOnMethods="VerifyGrp_GrpCreatedWith_advrSharedAcc_Investor")
	public void VerifyGrpSeled_GrpCreated_AdvShareddAcc_investor() throws Exception {
		logger.info("Selected group should displayed in account filter when you select group");
		Assert.assertEquals(accountDrapDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName6"), "selected groups are not displaying");
	}

	@Test(description = "AT-69157:verify group is displaying in Accounts dropwon in investor view when create a group with advisor shared accounts in investor view", 
			priority = 23,dependsOnMethods="VerifyGrp_GrpCreatedWith_advrSharedAcc_Investor")
	public void VerifyGrpAccTxn_GrpCreated_AdvSharedAcc_Investor() throws Exception {
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info("verifying advisor accounts groups transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1").trim())
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2").trim())) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"advisor shared full view details accounts groups transaction are not displaying");
	}

	@Test(description = "AT-69157:verify group is displaying in Accounts dropwon in investor view when create a group with advisor shared accounts in investor view",
			priority = 24,dependsOnMethods="VerifyGrp_GrpCreatedWith_advrSharedAcc_Investor")
	public void VerifyGrpAccTxn_GrpCreated_AdvSharedAcc1_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i <= 6; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);

	}

	@Test(description = "AT-69160:verify transaction should displayed when group Filter is applied in investor view", priority = 25)
	public void VerifyGrp_InvView_GrpCreatedByAdvisor() throws Exception {
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying advisor shared groups should not displayed in investor view");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"))
					|| accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName2"))
					|| accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName3"))
					|| accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName4"))) {
				advisorAggregatedAccountsgroup = false;
				break;
			} else {
				advisorAggregatedAccountsgroup = true;

			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroup, "advisor shared accounts group is not displaying");
	}

	@Test(description = "AT-69160:verify transaction should displayed when group Filter is applied in investor view", priority = 26)
	public void VerifyGrp_GrpCreated_advSharedAccAndInvAcc_Investor() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(7000);
		logger.info("create a group with  advisor shared  accouns");
		accountDrapDown.creategroupWithAdvisorSharedAccountAndInvestorAccount(
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"));
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		boolean advisorAggregatedAccountsgroup = false;
		logger.info("verifying advisor accounts group should displayed");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"))) {
				advisorAggregatedAccountsgroup = true;
				accountDrapDown.transactionAccountDropDownGroupLink().get(i).click();
				SeleniumUtil.waitForPageToLoad();

			}

		}
		Assert.assertTrue(advisorAggregatedAccountsgroup, "advisor shared accounts group is not displaying");
	}

	@Test(description = "AT-69160:verify transaction should displayed when group Filter is applied in investor view", priority = 27,
			dependsOnMethods="VerifyGrp_GrpCreated_advSharedAccAndInvAcc_Investor")
	public void VerifyGrpSeleed_GrpCreated_AdvSharedAccAndInvAcc_investor() throws Exception {
		logger.info("Selected group should displayed in account filter when you select group");
		Assert.assertEquals(accountDrapDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName1"), "selected groups are not displaying");
	}

	@Test(description = "AT-69160:verify transaction should displayed when group Filter is applied in investor view", priority = 28,dependsOnMethods="VerifyGrp_GrpCreated_advSharedAccAndInvAcc_Investor")
	public void VerifyGrpAccTxn_GrpCreated_AdvSharedAcct_Investor() throws Exception {
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info("verifying advisor accounts groups transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1").trim())
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4").trim())) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"advisor shared full view details accounts groups transaction are not displaying");
	}

	@Test(description = "AT-69160:verify transaction should displayed when group Filter is applied in investor view", priority = 29,dependsOnMethods="VerifyGrp_GrpCreated_advSharedAccAndInvAcc_Investor")
	public void VerifyGrpAccTxn_GrpCreated_AdvSharedAcct1_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i <= 6; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);

	}

	@Test(description = "AT-69148:Verify the advisor shared accouts Should not displayed in the manual transaction accounts dropdown in investor view", priority = 30)
	public void verifyAMTxn_Acc_Investor() throws Exception {

		logger.info("click on add manual transaction link");
		SeleniumUtil.click(add_Manual.addManualIcon());
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(add_Manual.accountdropdown());
		SeleniumUtil.waitForPageToLoad(1000);
		boolean investorSharedAccount = false;
		logger.info("verifying shared accounts should not  displayed");
		for (int i = 0; i < add_Manual.AccountSharingAccount_AMT().size(); i++) {
			if (add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount6"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount7"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount8"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount9"))
					|| add_Manual.AccountSharingAccount_AMT().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount10"))) {
				investorSharedAccount = false;
				break;
			} else {
				investorSharedAccount = true;
			}
		}
		Assert.assertTrue(investorSharedAccount,
				"advisor shared account is displaying in add manual transaction account dropdown");
	}

	@Test(description = "verify No Transaction and All Transaction when select and Unselect all Account Check box", priority = 31)
	public void VerifyNoTxnUnSelectAllAccCheckBOx_Investor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(4000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		logger.info("get all transaction account");
		int transactionAccountActual = accountDrapDown.allTransactionAccount().size();
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(0));
		logger.info("unselect all account checl box");
		SeleniumUtil.click(accountDrapDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad(7000);
		String noTransaction = accountDrapDown.TransactionNodataScreen().getText().trim();
		logger.info("select all account checl box");
		SeleniumUtil.click(accountDrapDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad(7000);
		logger.info("get all transaction account");
		int transactionAccountexpected = accountDrapDown.allTransactionAccount().size();
		Assert.assertEquals(noTransaction, PropsUtil.getDataPropertyValue("TransactionNodataMessage"),
				"No Transaction is not displaying when unselect all account check box");
		Assert.assertEquals(transactionAccountActual, transactionAccountexpected,
				" Transaction is not displaying when select all account check box");
	}

	@Test(description = "verify my accounts Transaction unselect advisor shared Account Check box", priority = 32,dependsOnMethods="VerifyNoTxnUnSelectAllAccCheckBOx_Investor")
	public void VerifyMyaccTxnWhenUnSelectadvSharedCheckBOx_Investor() throws Exception {
		logger.info("unselect advisor shared account check box");
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad(6000);
		boolean myaccountsTransaction = false;
		logger.info("verifying my accounts transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount12"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount13"))) {
				myaccountsTransaction = true;

			} else {
				myaccountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(myaccountsTransaction, "advisor shared accounts transaction are  displaying");
	}

	@Test(description = "verify my accounts Transaction unselect advisor shared Account Check box", priority = 33,dependsOnMethods="VerifyNoTxnUnSelectAllAccCheckBOx_Investor")
	public void VerifyMyaccTxnUnSelectadvSharedCheckBOx1_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account3 = account3 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
	}

	@Test(description = "verify advisor shared accounts Transaction when select only advisor shared Account Check box", priority = 34,dependsOnMethods="VerifyNoTxnUnSelectAllAccCheckBOx_Investor")
	public void VerifyAdvSharedaccTxnWhenUnSelectMyAccCheckBOx_Investor() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		logger.info("unselect my account check box");
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("select advisor shared account check box");
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad(6000);
		boolean myaccountsTransaction = false;
		logger.info("verifying advisor accounts transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				myaccountsTransaction = true;

			} else {
				myaccountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(myaccountsTransaction, "My acconnts transaction are  displaying");

	}

	@Test(description = "verify advisor shared accounts Transaction when select only advisor shared Account Check box", priority = 35,dependsOnMethods="VerifyNoTxnUnSelectAllAccCheckBOx_Investor")
	public void VerifyAdvSharedaccTxnWhenUnSelectMyAccCheckBOx1_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
	}

	@Test(description = "verify advisor shared accounts and my accounts Transaction select few accounts from both type  Check box", priority = 36,dependsOnMethods="VerifyNoTxnUnSelectAllAccCheckBOx_Investor")
	public void VerifyAdvSharedaccAndMyaccTxnWhenSelectFewAcc_Investor() throws Exception {
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountDrapDown.myAccountListCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountDrapDown.InvestorAdvisorAccountListCheckBox().get(0));
		SeleniumUtil.waitForPageToLoad(7000);
		boolean accountsTransaction = false;
		logger.info("verifying advisor accountsand my accounts transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				accountsTransaction = true;

			} else {
				accountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(accountsTransaction, "My acconnts and advisor shared accounts transaction are  displaying");
	}

	@Test(description = "verify advisor shared accounts and my accounts Transaction select few accounts from both type  Check box", priority = 37,dependsOnMethods="VerifyNoTxnUnSelectAllAccCheckBOx_Investor")
	public void VerifyAdvSharedaccAndMyaccTxnWhenSelectFewAcc1_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();
		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);

	}

	@Test(description = "verify gorup name should not displayed in advisor which group created in investot view ", priority = 38)
	public void VerifyGrp_AdvView_GrpCreatedByInv() throws Exception {

		logger.info("login to advisor");
		// SAMLlogin.login(d, "PFMADV1524656218131","PFMINV1524656161331");
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(1));
		SeleniumUtil.waitForPageToLoad();
		boolean investorAggregatedAccountsgroup = false;
		logger.info("verifying advisor shared groups should not displayed in investor view");
		for (int i = 0; i < accountDrapDown.transactionAccountDropDownGroupLink().size(); i++) {
			if (accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName5"))
					|| accountDrapDown.transactionAccountDropDownGroupLink().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisorGroupName6"))) {
				investorAggregatedAccountsgroup = false;
				break;
			} else {
				investorAggregatedAccountsgroup = true;

			}
		}
		Assert.assertTrue(investorAggregatedAccountsgroup, "investor shared accounts group is  displaying");

	}

	@Test(description = "verify No Transaction and All Transaction when select and Unselect all Account Check box", priority = 39)
	public void VerifyTxnUnSelectAllAccCheckBOx_advisor() throws Exception {

		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		logger.info("get all transaction account");
		int transactionAccountActual = accountDrapDown.allTransactionAccount().size();
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.click(accountDrapDown.transactionAccountDropDownAccountGroupLink().get(0));
		logger.info("unselect all account checl box");
		SeleniumUtil.click(accountDrapDown.allAccountCheckBox());
		String noTransaction = accountDrapDown.TransactionNodataScreen().getText().trim();
		logger.info("select all account checl box");
		SeleniumUtil.click(accountDrapDown.allAccountCheckBox());
		logger.info("get all transaction account");
		SeleniumUtil.waitForPageToLoad();
		int transactionAccountexpected = accountDrapDown.allTransactionAccount().size();
		Assert.assertEquals(noTransaction, PropsUtil.getDataPropertyValue("TransactionNodataMessage"),
				"No Transaction is not displaying when unselect all account check box");
		Assert.assertEquals(transactionAccountActual, transactionAccountexpected,
				" Transaction is not displaying when select all account check box");
	}

	@Test(description = "verify my accounts Transaction unselect advisor shared Account Check box", priority = 40,dependsOnMethods="VerifyTxnUnSelectAllAccCheckBOx_advisor")
	public void VerifyMyaccTxnWhenUnSelectadvSharedCheckBOx_advisor() throws Exception {
		logger.info("unselect advisor shared account check box");
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad();
		boolean myaccountsTransaction = false;
		logger.info("verifying my accounts transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			System.out.println(accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " "));
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				myaccountsTransaction = true;

			} else {
				myaccountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(myaccountsTransaction, "advisor shared accounts transaction are  displaying");

	}

	@Test(description = "verify my accounts Transaction unselect advisor shared Account Check box", priority = 41,dependsOnMethods="VerifyTxnUnSelectAllAccCheckBOx_advisor")
	public void VerifyMyaccTxnWhenUnSelectadvSharedCheckBOx1_advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account2 = account2 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
	}

	@Test(description = "verify advisor shared accounts Transaction unselect advisor shared Account Check box", priority = 42,dependsOnMethods="VerifyTxnUnSelectAllAccCheckBOx_advisor")
	public void VerifyAdvSharedaccTxnWhenUnSelectMyAccCheckBOx_advisor() throws Exception {
		logger.info("unselect my account check box");
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(0));
		logger.info("select advisor shared account check box");
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad();
		boolean myaccountsTransaction = false;
		logger.info("verifying investor accounts transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			System.out.println(accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " "));
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount12"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount13"))) {
				myaccountsTransaction = true;

			} else {
				myaccountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(myaccountsTransaction, "My acconnts transaction are  displaying");

	}

	@Test(description = "verify advisor shared accounts Transaction unselect advisor shared Account Check box", priority = 43,dependsOnMethods="VerifyTxnUnSelectAllAccCheckBOx_advisor")
	public void VerifyAdvSharedaccTxnWhenUnSelectMyAccCheckBOx1_advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount12"))) {
				account3 = account3 + 1;
			}
		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);

	}

	@Test(description = "verify advisor shared accounts and my accounts Transaction select few accounts from both type  Check box", priority = 44,dependsOnMethods="VerifyTxnUnSelectAllAccCheckBOx_advisor")
	public void VerifyAdvSharedaccAndMyaccTxnWhenSelectFewAcc_Advisor() throws Exception {
		SeleniumUtil.click(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.myAccountListCheckBox().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.InvestorAdvisorAccountListCheckBox().get(0));
		SeleniumUtil.waitForPageToLoad();
		boolean accountsTransaction = false;
		logger.info("verifying investor shared accounts and my accounts transaction should displayed");
		for (int i = 0; i < accountDrapDown.allTransactionAccount().size(); i++) {
			System.out.println(accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " "));
			if (accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))
					|| accountDrapDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				accountsTransaction = true;

			} else {
				accountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(accountsTransaction, "My acconnts and investor shared accounts transaction are  displaying");
	}

	@Test(description = "verify advisor shared accounts and my accounts Transaction select few accounts from both type  Check box", priority = 45,dependsOnMethods="VerifyTxnUnSelectAllAccCheckBOx_advisor")
	public void VerifyAdvSharedaccAndMyaccTxnWhenSelectFewAcc1_Advisor() throws Exception {

		int account1 = 0;
		int account2 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account2 = account2 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify by default 3 months label should displayed)", priority = 46)
	public void VerifyDefaultDateFilterLabel_InAdvisorView() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		logger.info("3 months label should displayed");
		SeleniumUtil.waitForPageToLoad(7000);
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast3MonthLabel"),
				"3 months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify by default 3 months label should displayed)",
			priority = 47,dependsOnMethods="VerifyDefaultDateFilterLabel_InAdvisorView")
	public void VerifyDefaultDateFilterdatel_Advisor() throws Exception {

		logger.info("3 months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(-2, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"3 months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify by default 3 months label should displayed)", 
			priority = 48,dependsOnMethods="VerifyDefaultDateFilterLabel_InAdvisorView")
	public void VerifyDefault3MonthTxn_Advisor() throws Exception {
		logger.info("3 months transaction should displayed");
		Assert.assertTrue(accountDrapDown.last3monthTransaction(-2, 0), "last 3 month transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify by default 3 months label should displayed)",
			priority = 49,dependsOnMethods="VerifyDefaultDateFilterLabel_InAdvisorView")
	public void VerifyDefault3MonthTxn1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this month label should displayed)", priority = 50)
	public void VerifyThismonthFilterLabel_Advisor() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		logger.info("this months label should displayed");
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this month label should displayed) ", priority = 51,
			dependsOnMethods="VerifyThismonthFilterLabel_Advisor")
	public void VerifyThisMonthDateFilterdate_Advisor() throws Exception {

		logger.info("this months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(0, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"This months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this month label should displayed)", priority = 52,dependsOnMethods="VerifyThismonthFilterLabel_Advisor")
	public void VerifyThisMonthTxn_Advisor() throws Exception {

		logger.info("this months transaction should displayed");
		Assert.assertTrue(accountDrapDown.thismonthTransaction(0, 0), "This month transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this month label should displayed)", priority = 53,dependsOnMethods="VerifyThismonthFilterLabel_Advisor")
	public void VerifyThisMonthTxn1_Advisor() throws Exception {

		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last month label should displayed)", priority = 54)
	public void VerifyLastmonthFilterLabel_Advisor() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("last months label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLastMonthLabel"),
				"last months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last month label should displayed)", priority = 55,
			dependsOnMethods="VerifyLastmonthFilterLabel_Advisor")
	public void VerifyLastMonthDateFilterdate_Advisor() throws Exception {

		logger.info("last months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDrapDown.monthStartDate(-1, "MM/dd/yyy") + " " + "-" + " "
						+ accountDrapDown.monthEndDateDate(-1, "MM/dd/yyy") + ")",
				"This months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last month label should displayed)",
			priority = 56,dependsOnMethods="VerifyLastmonthFilterLabel_Advisor")
	public void VerifyLastMonthTxn_Advisor() throws Exception {

		logger.info("last months transaction should displayed");
		Assert.assertTrue(accountDrapDown.lastmonthTransaction(-1, -1), "last month transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last month label should displayed)",
			priority = 57,dependsOnMethods="VerifyLastmonthFilterLabel_Advisor")
	public void VerifyLastMonthTxn1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount13"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last 6 month label should displayed)", priority = 58)
	public void VerifyLast6monthFilterLabel_Advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("last months label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(3));
		SeleniumUtil.waitForPageToLoad(7000);
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast6MonthLabel"),
				"last 6months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last 6 month label should displayed)",
			priority = 59,dependsOnMethods="VerifyLast6monthFilterLabel_Advisor")
	public void VerifyLast6MonthDateFilterdatel_Advisor() throws Exception {

		logger.info("last months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(-5, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"last 6 months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last 6 month label should displayed)",
			priority = 60,dependsOnMethods="VerifyLast6monthFilterLabel_Advisor")
	public void VerifyLast6MonthTransaction_Advisor() throws Exception {

		logger.info("last 6 months transaction should displayed");
		Assert.assertTrue(accountDrapDown.last6monthTransaction(-5, 0), "last 6 month transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last 6 month label should displayed)", 
			priority = 61,dependsOnMethods="VerifyLast6monthFilterLabel_Advisor")
	public void VerifyLast6MonthTxn1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this year label should displayed)", 
			priority = 62)
	public void VerifyThisYearFilterLabel_Advisor() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		logger.info("this year label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.click(accountDrapDown.datefilter().get(4));
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionThisYearLabel"),
				"last 6months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this year label should displayed)",
			priority = 63,dependsOnMethods="VerifyThisYearFilterLabel_Advisor")
	public void VerifyThisYearDateFilterdatel_Advisor() throws Exception {

		logger.info("year start date and current date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.yearStartDate(0, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"this year date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this year label should displayed)", 
			priority = 64,dependsOnMethods="VerifyThisYearFilterLabel_Advisor")
	public void VerifyThisYearTransaction_Advisor() throws Exception {

		logger.info("this year transaction should displayed");
		Assert.assertTrue(accountDrapDown.thisYearTransaction(0, 0), "this year transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify this year label should displayed)",
			priority = 65,dependsOnMethods="VerifyThisYearFilterLabel_Advisor")
	public void VerifyThisYearTxn1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify 12months label should displayed)", priority = 66)
	public void Verify12monthFilterLabel_Advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("12 month label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(5));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast12MonthLabel"),
				"12months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify 12months label should displayed)", 
			priority = 67,dependsOnMethods="Verify12monthFilterLabel_Advisor")
	public void Verify12monthDateFilterdatel_Advisor() throws Exception {

		logger.info("12 month date and current date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(-11, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"12months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify 12months label should displayed)",
			priority = 68,dependsOnMethods="Verify12monthFilterLabel_Advisor")
	public void Verify12monthTxn_Advisor() throws Exception {

		logger.info("12 month transaction should displayed");
		Assert.assertTrue(accountDrapDown.last12MonthTransaction(-11, 0), "12 month transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify 12months label should displayed)", 
			priority = 69,dependsOnMethods="Verify12monthFilterLabel_Advisor")
	public void Verify12monthTxn1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last year label should displayed)", priority = 70)
	public void VerifyLastYearFilterLabel_Advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		logger.info("last year label should displayed");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(6));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLastYearLabel"),
				"last year label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last year label should displayed)",
			priority = 71,dependsOnMethods="VerifyLastYearFilterLabel_Advisor")
	public void VerifyLastYearDateFilterdatel_Advisor() throws Exception {

		logger.info("last year start date and end date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDrapDown.yearStartDate(-1, "MM/dd/yyy") + " " + "-" + " "
						+ accountDrapDown.yearEndDate(-1, "MM/dd/yyy") + ")",
				"last year date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last year label should displayed)",
			priority = 72,dependsOnMethods="VerifyLastYearFilterLabel_Advisor")
	public void VerifyLastYearTransaction_Advisor() throws Exception {
		logger.info("custome date transaction should displayed");
		Assert.assertTrue(accountDrapDown.lastYesrMonthTransaction(-1, -1), "last year transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify last year label should displayed)",
			priority = 73,dependsOnMethods="VerifyLastYearFilterLabel_Advisor")
	public void VerifyLastYearTransaction1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount1"))) {
				account2 = account2 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify custome label should displayed)", priority = 74)
	public void VerifyCustomeDateFilterLabel_Advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		logger.info("custome date label should displayed");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.waitForPageToLoad(1000);
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(60));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomeDatesLabel"),
				"custome date label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify custome label should displayed)",
			priority = 75,dependsOnMethods="VerifyCustomeDateFilterLabel_Advisor")
	public void VerifyCustomeDateFilterdatel_Advisor() throws Exception {

		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(),
				"(" + add_Manual.targateDate1(0) + " " + "-" + " " + add_Manual.targateDate1(60) + ")",
				"custome date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify custome label should displayed)", 
			priority = 76,dependsOnMethods="VerifyCustomeDateFilterLabel_Advisor")
	public void VerifyCustomeTransaction_Advisor() throws Exception {
		logger.info("custome transaction should displayed");
		Assert.assertTrue(
				accountDrapDown.customeDateTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(60)),
				"custome date transaction is not displaying");

	}

	@Test(description = "AT-69163:verify Transactions are displayed when time filter applied in advisor view(verify custome label should displayed)", 
			priority = 77,dependsOnMethods="VerifyCustomeDateFilterLabel_Advisor")
	public void VerifyCustomeTransaction1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i <txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
	}

	@Test(description = "AT-69161:verify Transactions are displayed when Category filter applied in advisor view(verify no data screen and all transaction are displaying when select and unselect all category check box)", priority = 78)
	public void VerifyNoTxnWhenUnselectAllCategory_Advisor() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		logger.info("get all transaction account");
		int transactionAccountActual = accountDrapDown.allTransactionAccount().size();
		SeleniumUtil.click(accountDrapDown.categoryFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		logger.info("unselect all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		String noTransaction = accountDrapDown.TransactionNodataScreen().getText().trim();
		logger.info("select all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		logger.info("get all transaction account");
		int transactionAccountexpected = accountDrapDown.allTransactionAccount().size();
		Assert.assertEquals(noTransaction, PropsUtil.getDataPropertyValue("TransactionNodataMessage"),
				"No Transaction is not displaying when unselect all category check box");
		Assert.assertEquals(transactionAccountActual, transactionAccountexpected,
				" Transaction is not displaying when select all category check box");

	}

	@Test(description = "AT-69161:verify selected category transactions are displaying when you select HLC", priority = 79)
	public void VerifycatgFilterLableWhenselectAllCCatg_Advisor() throws Exception {
		Assert.assertEquals(accountDrapDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAllCategoriesFilterLink"),
				"all category label is not displaying");
	}

	@Test(description = "AT-69161:verify all selected HLC transactions are displaying", priority = 80,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void VerifyTransactionWhenselectOneHLCCategory_InAdvisorView() throws Exception {

		logger.info("unselect all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		int HLCSize = accountDrapDown.HLCCategoryLabel().size();
		for (int i = 0; i < HLCSize; i++) {
			System.out.println(accountDrapDown.HLCCategoryLabel().get(i).getText().trim());
			if (accountDrapDown.HLCCategoryLabel().get(i).getText().trim()
					.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TransaferCategory1"))) {
				SeleniumUtil.click(accountDrapDown.HLCCategoryCheckBox().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		int transactioncat = accountDrapDown.allTransactionCategory().size();
		boolean expectedCategory = false;
		for (int i = 0; i < transactioncat; i++) {
			if (accountDrapDown.allTransactionCategory().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory2"))) {
				expectedCategory = true;
			} else {
				expectedCategory = false;
				break;
			}
		}

		Assert.assertTrue(expectedCategory, "selected HLC category transactions are not displaying");

	}

	@Test(description = "AT-69161:verify all selected HLC transactions are displaying and both agrregated and shared accounts transactions are displaying",
			priority = 81,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void VerifyTxnWhenselectOneHLCCatg1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);

	}

	@Test(description = "AT-69161:verify multiselect category label is displaying when you select HLC", priority = 82,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void VerifyMultiSelectLableWhenselectHLCCCatg_Advisor() throws Exception {
		Assert.assertEquals(accountDrapDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAllCategoriesMultipleFilterLink"),
				"all category label is not displaying");
	}

	@Test(description = "AT-69161:verify selected Category Transactions are displaying when you select one MLC", priority = 83,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void VerifyTxnWhenselectOneMLCCatg_Advisor() throws Exception {
		logger.info("unselect all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		int MLCSize = accountDrapDown.MLCCategoryLabel().size();
		for (int i = 0; i < MLCSize; i++) {
			if (accountDrapDown.MLCCategoryLabel().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory3"))) {
				SeleniumUtil.click(accountDrapDown.MLCCategoryCheckBox().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		int transactiobCatSize = accountDrapDown.allTransactionCategory().size();
		boolean expectedCategory = false;
		for (int i = 0; i < transactiobCatSize; i++) {
			if (accountDrapDown.allTransactionCategory().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory3"))) {
				expectedCategory = true;
			} else {
				expectedCategory = false;
				break;
			}
		}

		Assert.assertTrue(expectedCategory, "selected HLC category transactions are not displaying");

	}

	@Test(description = "AT-69161:verify selected Category Transactions are displaying when you select one MLC", priority = 84,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void VerifyTransactionWhenselectOneMLCCategory1_InAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);

	}

	@Test(description = "AT-69161:verify multiselect category label is displaying when you select HLC", priority = 85,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void VerifyCategoryLableWhenselectMLCCCategory_InAdvisorView() throws Exception {
		Assert.assertEquals(accountDrapDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransaferCategory3"), "all category label is not displaying");
	}

	@Test(description = "AT-69163:verify Transaction search with From filed", priority = 86, groups = { "Smoke" },dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")

	public void searchWithFromField_inAdvisorView() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		logger.info("enter amount in from field");
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(5000);

		Assert.assertTrue(search.AssertFromAmountonly(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1")),
				"From amount transaction are displaying");

	}

	@Test(description = "AT-69163:verify multiselect category label is displaying when you select HLC", priority = 87,dependsOnMethods="VerifycatgFilterLableWhenselectAllCCatg_Advisor")
	public void searchWithFromField1_inAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163 verify transaction search with tO filed", priority = 88, groups = { "Smoke" })
	public void searchWithtoField_advisorView() throws Exception {

		logger.info("clear and enter amount in To field");
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);

		Assert.assertTrue(search.AssertToAmountonly(PropsUtil.getDataPropertyValue("AmountSearchTo")),
				"From amount transaction are displaying");

	}

	@Test(description = "AT-69163 verify transaction search with tO filed", priority = 89,dependsOnMethods="searchWithtoField_advisorView")
	public void searchWithtoField1_advisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify transaction displaying between From and To field amount", priority = 90, groups = {
			"Smoke" })
	public void checToFromkTransaction_InAdvisorView() throws Exception {
		logger.info("enter Amount to from filed and tO field both and click apply button");

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("TransactionAmountFilterTo1"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(
				search.AssertFromAndToAmount(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1"),
						PropsUtil.getDataPropertyValue("TransactionAmountFilterTo1")),
				"from and To filed amount transaction are not displaying");
	}

	@Test(description = "AT-69163:verify transaction displaying between From and To field amount", priority = 91,dependsOnMethods="checToFromkTransaction_InAdvisorView")
	public void checToFromkTransaction1_InAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69163:verify all transaction are disaplying when clear the search", priority = 92, groups = {
			"Smoke" })
	public void checkTransactionAfterclickOnClear_InAdvisorView() throws Exception {
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(20000);
		Assert.assertTrue(
				search.AssertAllAmount(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
						PropsUtil.getDataPropertyValue("AmountSearchTo")),
				"Transaction are not dispalying when you clear amount search");
	}

	@Test(description = "AT-69163:verify all transaction are disaplying when clear the search", priority = 93,dependsOnMethods="checkTransactionAfterclickOnClear_InAdvisorView")
	public void checkTransactionAfterclickOnClear1_InAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69167:verify all transaction are disaplying when click on all Transaction Type", priority = 94, groups = {
			"Smoke" })
	public void verifyAllTransactionDisplayingWhenClickOnALL_InAdvisorView() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		int transactionAccountExpected = accountDrapDown.allTransactionAccount().size();
		SeleniumUtil.click(accountDrapDown.TransactionTypeFilter().get(0));
		SeleniumUtil.waitForPageToLoad();
		int transactionAccountActual = accountDrapDown.allTransactionAccount().size();
		Assert.assertEquals(transactionAccountActual, transactionAccountExpected, "All Transaction should displayed");
	}

	@Test(description = "AT-69167:verify all transaction are disaplying when click on all Transaction Type", priority = 95,dependsOnMethods="verifyAllTransactionDisplayingWhenClickOnALL_InAdvisorView")
	public void verifyAllTransactionDisplayingWhenClickOnALL1_InAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 7; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69167 verify all deposit transaction are disaplying when click on deposit Type Transaction Type", priority = 96, groups = {
			"Smoke" })
	public void verifyAllDeptTxnDisplayingClickOnDeptType_Advisor() throws Exception {

		boolean actualDepositType = false;
		SeleniumUtil.click(accountDrapDown.TransactionTypeFilter().get(1));
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		logger.info("verify all deposit type transaction amount should displayed with green");
		for (int i = 0; i < search.allTransactionAmount().size(); i++) {
			System.out.println(search.allTransactionAmount().get(i).getAttribute("class").trim());
			if (search.allTransactionAmount().get(i).getAttribute("class").trim().contains("y-font-green")) {
				actualDepositType = true;
			} else {
				actualDepositType = false;
				break;
			}
		}

		Assert.assertTrue(actualDepositType, "all deposit type transaction are not displaying");

	}

	@Test(description = "AT-69167 verify all deposit transaction are disaplying when click on deposit Type Transaction Type", priority = 97,dependsOnMethods="verifyAllDeptTxnDisplayingClickOnDeptType_Advisor")
	public void verifyAllDeptTxnDisplayingClickOnDeptType1_Advisor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 3; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);

	}

	@Test(description = "AT-69167:verify all withdraw transaction are disaplying when click on withDraw Type Transaction Type", priority = 98, groups = {
			"Smoke" })
	public void verifyAllWithDrawTxnDisplayingClickOnDeptType_Advisor() throws Exception {
		boolean actualDepositType = false;
		SeleniumUtil.click(accountDrapDown.TransactionTypeFilter().get(2));
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		logger.info("verify all deposit type transaction amount should displayed with back");
		for (int i = 0; i < search.allTransactionAmount().size(); i++) {
			if (search.allTransactionAmount().get(i).getAttribute("class").trim().contains("y-font-black")) {
				actualDepositType = true;
			} else {
				actualDepositType = false;
				break;
			}
		}

		Assert.assertTrue(actualDepositType, "all withDarw type transaction are not displaying");

	}

	@Test(description = "AT-69167:verify all withdraw transaction are disaplying when click on withDraw Type Transaction Type", priority = 99,dependsOnMethods="verifyAllWithDrawTxnDisplayingClickOnDeptType_Advisor")
	public void verifyAllWithDrawTransactionDisplayingWhenClickOnDepositType1_InAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i < 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69169:verify transction are diplayed when search transaction from Description in advisor view", priority = 100, groups = {
			"Smoke" })
	public void verifyTxnWhenSearchedByDesp_InAdvisorView() throws Exception {
		// SAMLlogin.login(d, "PFMADV1524893326378","PFMINV1524893268484");
		// SAMLlogin.login(d,advisorUsername ,InvesterName1);

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionDescription1"));
		SeleniumUtil.waitForPageToLoad(7000);
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		boolean actualdescriptionType = false;
		logger.info("verify searched description Transaction sshould displayed");
		for (int i = 0; i < accountDrapDown.allTransactionDescription().size(); i++) {
			if (accountDrapDown.allTransactionDescription().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionDescription1"))) {
				actualdescriptionType = true;
			} else {
				actualdescriptionType = false;
				break;
			}
		}

		Assert.assertTrue(actualdescriptionType, "all searched description transaction are not displaying");

	}

	@Test(description = "AT-69169:verify transction are diplayed when search transaction from Description in advisor view", priority = 101,dependsOnMethods="verifyTxnWhenSearchedByDesp_InAdvisorView")
	public void verifyTransactionWhenSearchedByDescription1_InAdvisorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i < 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69171:verify transction are diplayed when search transaction from note in advisor view", priority = 102, groups = {
			"Smoke" })
	public void verifyTxnSearchedByNote_Advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(4));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		aggre_Tra.note().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote1"));
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(5));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad();
		aggre_Tra.note().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote2"));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad();
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote1"));
		SeleniumUtil.waitForPageToLoad();
		boolean actualNote = false;
		logger.info("verify searched description Transaction sshould displayed");
		for (int i = 0; i < accountDrapDown.allTransactionDescription().size(); i++) {

			SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(i));
			SeleniumUtil.click(aggre_Tra.ShowMore());
			if (aggre_Tra.note().getText().equals(PropsUtil.getDataPropertyValue("TransactionNote1"))) {
				actualNote = true;
			} else {
				actualNote = false;
				break;
			}
		}

		Assert.assertTrue(actualNote, "all searched note transaction are not displaying");

	}

	@Test(description = "AT-69171:verify transction are diplayed when search transaction from note in advisor view", priority = 103,dependsOnMethods="verifyTxnSearchedByNote_Advisor")
	public void verifytxnSearchedByNote1_Advisor() throws Exception {
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"),
				"verify first account tranaction is diaplaying");

	}

	@Test(description = "AT-69174:verify advisor Accounts transction are diplayed when search transaction from tag in advisor view which tag added by advisor", priority = 104, groups = {
			"Smoke" })
	public void verifyTxnSearchedByTag_Advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(4));
		SeleniumUtil.waitForPageToLoad(1000);
		accountDrapDown.createTag(PropsUtil.getDataPropertyValue("Transactiontag1"));
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(5));
		SeleniumUtil.waitForPageToLoad(1000);
		accountDrapDown.createTag(PropsUtil.getDataPropertyValue("Transactiontag2"));
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad();
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("Transactiontag1").trim());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(aggre_Tra.AggregatedListTag().get(0).getText(),
				PropsUtil.getDataPropertyValue("Transactiontag1").trim(),
				"all searched tag transaction are not displaying");

	}

	@Test(description = "AT-69174:verify advisor Accounts transction are diplayed when search transaction from tag in advisor view which tag added by advisor", 
			priority = 105,dependsOnMethods="verifyTxnSearchedByTag_Advisor")
	public void verifyTransactionWhenSearchedByTag1_InAdvisorView() throws Exception {
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"),
				"verify first account tranaction is diaplaying");

	}

	@Test(description = "verify  tag are displayed in tag filter ", priority = 106, groups = { "Smoke" })
	public void verifytaginTagFilter_InAdvisorView() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(tag.tagTab());
		String tag1[] = PropsUtil.getDataPropertyValue("TransactionAdviosrTagList").split(",");

		// tag.tagIcon().click();
		for (int i = 0; i < accountDrapDown.TransactionTagList().size(); i++) {

			Assert.assertEquals(accountDrapDown.TransactionTagList().get(i).getText().trim().toLowerCase(),
					tag1[i].trim().toLowerCase(), "tag are not displaying in tag filter");

		}
	}

	@Test(description = "verify  transaction are disaplying when apply tag filter", priority = 107, groups = {
			"Smoke" })
	public void verifyTxnApplyTagFilter_InAdvisorView() throws Exception {

		SeleniumUtil.click(accountDrapDown.TransactionTagList().get(0));
		SeleniumUtil.waitForPageToLoad(8000);
		logger.info("verify searched description Transaction sshould displayed");
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionCreatedTagList().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("Transactiontag2").trim(),
				"all filter selected tag transaction are not displaying");

	}

	@Test(description = "verify  transaction are disaplying when apply tag filter", priority = 108)
	public void verifyTransactionWhenApplyTagFilter1_InAdvisorView() throws Exception {
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"),
				"verify first account tranaction is diaplaying");

	}
	// Investor view

	@Test(description = "AT-69164:verify by default 3 months label should displayed", priority = 109)
	public void VerifyDefaultDateFilterLabel_Investor() throws Exception {
		SAMLlogin.login(d, InvesterName1, null, "10003700");
		// SAMLlogin.login(d, "PFMINV1524688098997",null);

		PageParser.forceNavigate("Transaction", d);
		logger.info("3 months label should displayed");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast3MonthLabel"),
				"3 months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify by default 3 months date should displayed", priority = 110,dependsOnMethods="VerifyDefaultDateFilterLabel_Investor")
	public void VerifyDefaultDateFilterdatel_InInvestorView() throws Exception {

		logger.info("3 months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(-2, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"3 months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify by default 3 months date transaction should displayed", priority = 111,dependsOnMethods="VerifyDefaultDateFilterLabel_Investor")
	public void VerifyDefault3MonthTransaction_InInvestorView() throws Exception {
		logger.info("3 months transaction should displayed");
		Assert.assertTrue(accountDrapDown.last3monthTransaction(-2, 0), "last 3 month transaction is not displaying");

	}

	@Test(description = "AT-69164:verify by default 3 months date transaction should displayed", priority = 121,dependsOnMethods="VerifyDefaultDateFilterLabel_Investor")
	public void VerifyDefault3MonthTransaction1_InInvestorView() throws Exception {

		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i < txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69164:verify this month label should displayed", priority = 112)
	public void VerifyThismonthFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		logger.info("this months label should displayed");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionThisMonthLabel"),
				"this months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify this month start date and current should displayed ", priority = 113,dependsOnMethods="VerifyThismonthFilterLabel_Investor")
	public void VerifyThisMonthDateFilterdate_Investor() throws Exception {

		logger.info("this months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(0, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"This months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify this months date transaction should displayed", priority = 114,dependsOnMethods="VerifyThismonthFilterLabel_Investor")
	public void VerifyThisMonthTransaction_InInvestorView() throws Exception {

		logger.info("this months transaction should displayed");
		Assert.assertTrue(accountDrapDown.thismonthTransaction(0, 0), "This month transaction is not displaying");

	}

	@Test(description = "AT-69164:verify this months date transaction should displayed", priority = 115,dependsOnMethods="VerifyThismonthFilterLabel_Investor")
	public void VerifyThisMonthTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69164:verify last month label should displayed", priority = 116)
	public void VerifyLastmonthFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("last months label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLastMonthLabel"),
				"last months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify lastmonth  start date and last month enddate  should dasplayed", priority = 117,dependsOnMethods="VerifyLastmonthFilterLabel_Investor")
	public void VerifyLastMonthDateFilterdate_InInvestorView() throws Exception {

		logger.info("last months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDrapDown.monthStartDate(-1, "MM/dd/yyy") + " " + "-" + " "
						+ accountDrapDown.monthEndDateDate(-1, "MM/dd/yyy") + ")",
				"This months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify by last months date transaction should displayed", priority = 118,dependsOnMethods="VerifyLastmonthFilterLabel_Investor")
	public void VerifyLastMonthTransaction_InInvestorView() throws Exception {

		logger.info("last months transaction should displayed");
		Assert.assertTrue(accountDrapDown.lastmonthTransaction(-1, -1), "last month transaction is not displaying");

	}

	@Test(description = "AT-69164:verify by last months date transaction should displayed", priority = 119,dependsOnMethods="VerifyLastmonthFilterLabel_Investor")
	public void VerifyLastMonthTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int txnsize=accountDrapDown.getAllPostedAccount_AMT1().size();

		for (int i = 0; i < txnsize; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount13"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account3 = account3 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);

	}

	@Test(description = "AT-69164:verify last 6 month label should displayed", priority = 120)
	public void VerifyLast6monthFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("last months label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(3));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast6MonthLabel"),
				"last 6months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify last 6 month  start date and current enddate  should dasplayed", priority = 121,dependsOnMethods="VerifyLast6monthFilterLabel_Investor")
	public void VerifyLast6MonthDateFilterdatel_InInvestorView() throws Exception {

		logger.info("last months date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(-5, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"last 6 months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify by last 6 months date transaction should displayed", priority = 122,dependsOnMethods="VerifyLast6monthFilterLabel_Investor")
	public void VerifyLast6MonthTransaction_InInvestorView() throws Exception {

		logger.info("last 6 months transaction should displayed");
		Assert.assertTrue(accountDrapDown.last6monthTransaction(-5, 0), "last 6 month transaction is not displaying");

	}

	@Test(description = "AT-69164:verify by last 6 months date transaction should displayed", priority = 123,dependsOnMethods="VerifyLast6monthFilterLabel_Investor")
	public void VerifyLast6MonthTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69164:verify this year label should displayed", priority = 124)
	public void VerifyThisYearFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		logger.info("this year label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.click(accountDrapDown.datefilter().get(4));
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionThisYearLabel"),
				"last 6months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify last 6 month  start date and current enddate  should dasplayed", priority = 125,dependsOnMethods="VerifyThisYearFilterLabel_Investor")
	public void VerifyThisYearDateFilterdatel_InInvestorView() throws Exception {

		logger.info("year start date and current date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.yearStartDate(0, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"this year date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify this yearstart date and current date transaction should displayed", priority = 126,dependsOnMethods="VerifyThisYearFilterLabel_Investor")
	public void VerifyThisYearTransaction_InInvestorView() throws Exception {

		logger.info("this year transaction should displayed");
		Assert.assertTrue(accountDrapDown.thisYearTransaction(0, 0), "this year transaction is not displaying");

	}

	@Test(description = "AT-69164:verify this yearstart date and current date transaction should displayed", priority = 127,dependsOnMethods="VerifyThisYearFilterLabel_Investor")
	public void VerifyThisYearTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69164:verify 12months label should displayed", priority = 128)
	public void Verify12monthFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("12 month label should displayed");
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(5));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLast12MonthLabel"),
				"12months label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify 12 months start date and current enddate  should dasplayed", priority = 129,dependsOnMethods="Verify12monthFilterLabel_Investor")
	public void Verify12monthDateFilterdatel_InInvestorView() throws Exception {

		logger.info("12 month date and current date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(), "("
				+ accountDrapDown.monthStartDate(-11, "MM/dd/yyy") + " " + "-" + " " + add_Manual.targateDate1(0) + ")",
				"12months date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify 12 months date and current date transaction should displayed", priority = 130,dependsOnMethods="Verify12monthFilterLabel_Investor")
	public void Verify12monthTransaction_InInvestorView() throws Exception {

		logger.info("12 month transaction should displayed");
		Assert.assertTrue(accountDrapDown.last12MonthTransaction(-11, 0), "12 month transaction is not displaying");

	}

	@Test(description = "AT-69164:verify 12 months date and current date transaction should displayed", priority = 131,dependsOnMethods="Verify12monthFilterLabel_Investor")
	public void Verify12monthTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69164:verify last year label should displayed", priority = 132)
	public void VerifyLastYearFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		logger.info("last year label should displayed");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDrapDown.datefilter().get(6));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLastYearLabel"),
				"last year label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify last year start date and  enddate  should dasplayed", priority = 133,dependsOnMethods="VerifyLastYearFilterLabel_Investor")
	public void VerifyLastYearDateFilterdatel_InInvestorView() throws Exception {

		logger.info("last year start date and end date should displayed");
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDrapDown.yearStartDate(-1, "MM/dd/yyy") + " " + "-" + " "
						+ accountDrapDown.yearEndDate(-1, "MM/dd/yyy") + ")",
				"last year date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify last year date and start date transaction should displayed", priority = 134,dependsOnMethods="VerifyLastYearFilterLabel_Investor")
	public void VerifyLastYearTransaction_InInvestorView() throws Exception {
		logger.info("custome date transaction should displayed");
		Assert.assertTrue(accountDrapDown.lastYesrMonthTransaction(-1, -1), "last year transaction is not displaying");

	}

	@Test(description = "AT-69164:verify last year date and start date transaction should displayed", priority = 135,dependsOnMethods="VerifyLastYearFilterLabel_Investor")
	public void VerifyLastYearTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i < 12; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69164:verify custome label should displayed", priority = 136)
	public void VerifyCustomeDateFilterLabel_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		logger.info("custome date label should displayed");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.TransactionTimeFilter());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.waitForPageToLoad(1000);
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(60));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountDrapDown.TransactionTimeFilterLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomeDatesLabel"),
				"custome date label is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify custome start date and  enddate  should dasplayed", priority = 137,dependsOnMethods="VerifyCustomeDateFilterLabel_Investor")
	public void VerifyCustomeDateFilterdatel_InInvestorView() throws Exception {

		Assert.assertEquals(accountDrapDown.TransactionTimeFilterDate().getText().trim(),
				"(" + add_Manual.targateDate1(0) + " " + "-" + " " + add_Manual.targateDate1(60) + ")",
				"custome date is not displaying in time filter Tab");

	}

	@Test(description = "AT-69164:verify custome date and start date transaction should displayed", priority = 138,dependsOnMethods="VerifyCustomeDateFilterLabel_Investor")
	public void VerifyCustomeTransaction_InInvestorView() throws Exception {
		logger.info("custome transaction should displayed");
		Assert.assertTrue(
				accountDrapDown.customeDateTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(60)),
				"custome date transaction is not displaying");

	}

	@Test(description = "AT-69164:verify custome date and start date transaction should displayed", priority = 139,dependsOnMethods="VerifyCustomeDateFilterLabel_Investor")
	public void VerifyCustomeTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69162:verify no data screen and all transaction are displaying when select and unselect all category check box", priority = 140)
	public void VerifyNoTransactionWhenUnselectAllCategory_Investor() throws Exception {
		// SAMLlogin.login(d,InvesterName1,null);

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		logger.info("get all transaction account");
		int transactionAccountActual = accountDrapDown.allTransactionAccount().size();
		SeleniumUtil.click(accountDrapDown.categoryFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		logger.info("unselect all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		String noTransaction = accountDrapDown.TransactionNodataScreen().getText().trim();
		logger.info("select all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		logger.info("get all transaction account");
		int transactionAccountexpected = accountDrapDown.allTransactionAccount().size();
		Assert.assertEquals(noTransaction, PropsUtil.getDataPropertyValue("TransactionNodataMessage"),
				"No Transaction is not displaying when unselect all category check box");
		Assert.assertEquals(transactionAccountActual, transactionAccountexpected,
				" Transaction is not displaying when select all category check box");

	}

	@Test(description = "AT-69162:verify selected category transactions are displaying when you select HLC", priority = 141)
	public void VerifycategoryFilterLableWhenselectAllCCategory_Investor() throws Exception {
		Assert.assertEquals(accountDrapDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAllCategoriesFilterLink"),
				"all category label is not displaying");
	}

	@Test(description = "AT-69162:verify all selected HLC transactions are displaying", priority = 142,dependsOnMethods="VerifycategoryFilterLableWhenselectAllCCategory_Investor")
	public void VerifyTransactionWhenselectOneHLCCategory_InInvestorView() throws Exception {
		logger.info("unselect all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		int HLCSize = accountDrapDown.HLCCategoryLabel().size();
		for (int i = 0; i < HLCSize; i++) {
			System.out.println(accountDrapDown.HLCCategoryLabel().get(i).getText().trim());
			if (accountDrapDown.HLCCategoryLabel().get(i).getText().trim()
					.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TransaferCategory1"))) {
				SeleniumUtil.click(accountDrapDown.HLCCategoryCheckBox().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(10000);
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		int transactioncat = accountDrapDown.allTransactionCategory().size();
		boolean expectedCategory = false;
		for (int i = 0; i < transactioncat; i++) {
			if (accountDrapDown.allTransactionCategory().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory2"))) {
				expectedCategory = true;
			} else {
				expectedCategory = false;
				break;
			}
		}

		Assert.assertTrue(expectedCategory, "selected HLC category transactions are not displaying");

	}

	@Test(description = "AT-69162:verify all selected HLC transactions are displaying", priority = 143,dependsOnMethods="VerifycategoryFilterLableWhenselectAllCCategory_Investor")
	public void VerifyTransactionWhenselectOneHLCCategory1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i < 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69162:verify multiselect category label is displaying when you select HLC", priority = 144,dependsOnMethods="VerifycategoryFilterLableWhenselectAllCCategory_Investor")
	public void VerifyMultiSelectLableWhenselectHLCCCategory_InInvestorView() throws Exception {
		Assert.assertEquals(accountDrapDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAllCategoriesMultipleFilterLink"),
				"all category label is not displaying");
	}

	@Test(description = "AT-69162:verify selected Category Transactions are displaying when you select one MLC", priority = 145,dependsOnMethods="VerifycategoryFilterLableWhenselectAllCCategory_Investor")
	public void VerifyTransactionWhenselectOneMLCCategory_InInvestorView() throws Exception {
		logger.info("unselect all category check box");
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allCategoryCheckBox());
		SeleniumUtil.waitForPageToLoad();
		int MLCSize = accountDrapDown.MLCCategoryLabel().size();
		for (int i = 0; i < MLCSize; i++) {
			if (accountDrapDown.MLCCategoryLabel().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory3"))) {
				SeleniumUtil.click(accountDrapDown.MLCCategoryCheckBox().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		int transactiobCatSize = accountDrapDown.allTransactionCategory().size();
		boolean expectedCategory = false;
		for (int i = 0; i < transactiobCatSize; i++) {
			if (accountDrapDown.allTransactionCategory().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory3"))) {
				expectedCategory = true;
			} else {
				expectedCategory = false;
				break;
			}
		}

		Assert.assertTrue(expectedCategory, "selected HLC category transactions are not displaying");

	}

	@Test(description = "AT-69162:verify selected Category Transactions are displaying when you select one MLC", priority = 146,dependsOnMethods="VerifycategoryFilterLableWhenselectAllCCategory_Investor")
	public void VerifyTransactionWhenselectOneMLCCategory1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i < 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69162:verify multiselect category label is displaying when you select HLC", priority = 147,dependsOnMethods="VerifycategoryFilterLableWhenselectAllCCategory_Investor")
	public void VerifyCategoryLableWhenselectMLCCCategory_InInvestorView() throws Exception {
		Assert.assertEquals(accountDrapDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransaferCategory3"), "all category label is not displaying");
	}

	@Test(description = "verify Transaction searhc with From filed", priority = 148, groups = { "Smoke" })

	public void searchWithFromField_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		logger.info("enter amount in from field");
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(5000);

		Assert.assertTrue(search.AssertFromAmountonly(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1")),
				"From amount transaction are displaying");

	}

	@Test(description = "AT-69162:verify multiselect category label is displaying when you select HLC", priority = 149,dependsOnMethods="searchWithFromField_Investor")
	public void searchWithFromField1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69166:verify transaction search with tO filed", priority = 150, groups = { "Smoke" })
	public void searchWithtoField_Investor() throws Exception {

		logger.info("clear and enter amount in To field");
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("AmountSearchTo"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(20000);
		Assert.assertTrue(search.AssertToAmountonly(PropsUtil.getDataPropertyValue("AmountSearchTo")),
				"From amount transaction are displaying");

	}

	@Test(description = "AT-69166:verify transaction search with tO filed", priority = 151,dependsOnMethods="searchWithtoField_Investor")
	public void searchWithtoField1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69166:verify transaction displaying between From and To field amount", priority = 152, groups = {
			"Smoke" })
	public void checToFromkTransaction_Investor() throws Exception {
		logger.info("enter Amount to from filed and tO field both and click apply button");

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(5000);
		search.amountFromField().sendKeys(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1"));
		search.amountToField().sendKeys(PropsUtil.getDataPropertyValue("TransactionAmountFilterTo1"));
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.waitForPageToLoad(15000);
		Assert.assertTrue(
				search.AssertFromAndToAmount(PropsUtil.getDataPropertyValue("TransactionAmountFilterFrom1"),
						PropsUtil.getDataPropertyValue("TransactionAmountFilterTo1")),
				"from and To filed amount transaction are not displaying");
	}

	@Test(description = "AT-69166:verify transaction displaying between From and To field amount", priority = 153,dependsOnMethods="checToFromkTransaction_Investor")
	public void checToFromkTransaction1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i <= 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69166:verify all transaction are disaplying when clear the search", priority = 154, groups = {
			"Smoke" })
	public void checkTransactionAfterclickOnClear_Investor() throws Exception {
		SeleniumUtil.click(search.clearAmount());
		SeleniumUtil.waitForPageToLoad(20000);
		Assert.assertTrue(
				search.AssertAllAmount(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
						PropsUtil.getDataPropertyValue("AmountSearchTo")),
				"Transaction are not dispalying when you clear amount search");
	}

	@Test(description = "AT-69166:verify all transaction are disaplying when clear the search", priority = 155,dependsOnMethods="checkTransactionAfterclickOnClear_Investor")
	public void checkTransactionAfterclickOnClear1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69168:verify all transaction are disaplying when click on all Transaction Type", priority = 156, groups = {
			"Smoke" })
	public void verifyAllTransactionDisplayingWhenClickOnALL_Investor() throws Exception {

		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		int transactionAccountExpected = accountDrapDown.allTransactionAccount().size();
		SeleniumUtil.click(accountDrapDown.TransactionTypeFilter().get(0));
		SeleniumUtil.waitForPageToLoad();
		int transactionAccountActual = accountDrapDown.allTransactionAccount().size();
		Assert.assertEquals(transactionAccountActual, transactionAccountExpected, "All Transaction should displayed");
	}

	@Test(description = "AT-69168:verify all transaction are disaplying when click on all Transaction Type", priority = 157)
	public void verifyAllTransactionDisplayingWhenClickOnALL1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i < 8; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69168:verify all deposit transaction are disaplying when click on deposit Type Transaction Type", priority = 158, groups = {
			"Smoke" })
	public void verifyAllDepositTransactionDisplayingWhenClickOnDepositType_InInvestorView() throws Exception {
		boolean actualDepositType = false;
		SeleniumUtil.click(accountDrapDown.TransactionTypeFilter().get(1));
		SeleniumUtil.waitForPageToLoad();
		logger.info("verify all deposit type transaction amount should displayed with green");
		for (int i = 0; i < search.allTransactionAmount().size(); i++) {
			if (search.allTransactionAmount().get(i).getAttribute("class").trim().contains("y-font-green")) {
				actualDepositType = true;
			} else {
				actualDepositType = false;
				break;
			}
		}

		Assert.assertTrue(actualDepositType, "all deposit type transaction are not displaying");

	}

	@Test(description = "AT-69168:verify all deposit transaction are disaplying when click on deposit Type Transaction Type", priority = 159)
	public void verifyAllDepositTransactionDisplayingWhenClickOnDepositType1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;
		int account5 = 0;

		for (int i = 0; i <= 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount11"))) {
				account5 = account5 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
		Assert.assertTrue(account5 >= 1);
	}

	@Test(description = "AT-69168:verify all withdraw transaction are disaplying when click on withDraw Type Transaction Type", priority = 160, groups = {
			"Smoke" })
	public void verifyAllWithDrawTransactionDisplayingWhenClickOnDepositType_InInvestorView() throws Exception {
		boolean actualDepositType = false;
		SeleniumUtil.click(accountDrapDown.TransactionTypeFilter().get(2));
		SeleniumUtil.waitForPageToLoad();
		logger.info("verify all deposit type transaction amount should displayed with back");
		for (int i = 0; i < search.allTransactionAmount().size(); i++) {
			if (search.allTransactionAmount().get(i).getAttribute("class").trim().contains("y-font-black")) {
				actualDepositType = true;
			} else {
				actualDepositType = false;
				break;
			}
		}

		Assert.assertTrue(actualDepositType, "all withDarw type transaction are not displaying");

	}

	@Test(description = "AT-69168:verify all withdraw transaction are disaplying when click on withDraw Type Transaction Type", priority = 161)
	public void verifyAllWithDrawTransactionDisplayingWhenClickOnDepositType1_InInvestorView() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);
	}

	@Test(description = "AT-69170:verify  transaction are disaplying when click on searched by Description", priority = 162, groups = {
			"Smoke" })
	public void verifyTxnWhenSearchedByDesp_Investor() throws Exception {
		// SAMLlogin.login(d, "PFMINV1524893268484",null);
		// SAMLlogin.login(d, InvesterName1,null);
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionDescription1"));
		SeleniumUtil.waitForPageToLoad(7000);
		if (accountDrapDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean actualdescriptionType = false;
		logger.info("verify searched description Transaction sshould displayed");
		for (int i = 0; i < accountDrapDown.allTransactionDescription().size(); i++) {
			if (accountDrapDown.allTransactionDescription().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionDescription1"))) {
				actualdescriptionType = true;
			} else {
				actualdescriptionType = false;
				break;
			}
		}

		Assert.assertTrue(actualdescriptionType, "all searched description transaction are not displaying");

	}

	@Test(description = "AT-69170:verify  transaction are disaplying when click on searched by Description", priority = 163,dependsOnMethods="verifyTxnWhenSearchedByDesp_Investor")
	public void verifyTxnSearchedByDesp1_Investor() throws Exception {
		int account1 = 0;
		int account2 = 0;
		int account3 = 0;
		int account4 = 0;

		for (int i = 0; i <= 4; i++) {
			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"))) {
				account1 = account1 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"))) {
				account2 = account2 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"))) {
				account3 = account3 + 1;
			}

			if (accountDrapDown.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionAdvisordAccount2"))) {
				account4 = account4 + 1;
			}

		}

		Assert.assertTrue(account1 >= 1);
		Assert.assertTrue(account2 >= 1);
		Assert.assertTrue(account3 >= 1);
		Assert.assertTrue(account4 >= 1);

	}

	@Test(description = "AT-69172:verify  transaction are disaplying when click on searched by note", priority = 164, groups = {
			"Smoke" })
	public void verifyTxnWhenSearchedByNote_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(2));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad();
		aggre_Tra.note().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote3"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(3));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		aggre_Tra.note().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote2"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad();
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote3"));
		SeleniumUtil.waitForPageToLoad();
		boolean actualNote = false;
		logger.info("verify searched description Transaction sshould displayed");
		for (int i = 0; i < accountDrapDown.allTransactionDescription().size(); i++) {

			SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			SeleniumUtil.click(aggre_Tra.ShowMore());
			SeleniumUtil.waitForPageToLoad(1000);
			if (aggre_Tra.note().getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionNote3").trim())) {
				actualNote = true;
			} else {
				actualNote = false;
				break;
			}
		}

		Assert.assertTrue(actualNote, "all searched note transaction are not displaying");

	}

	@Test(description = "AT-69172:verify  transaction are disaplying when click on searched by note", priority = 165)
	public void verifyTxnWhenSearchedByNote1_Investor() throws Exception {

		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"),
				"verify second account tranaction is diaplaying");

	}

	@Test(description = "", priority = 166, groups = { "Smoke" })
	public void verifyTxnWhenSearchedByNoteWhichISaddedByAdvisor_Investor() throws Exception {
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote1"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		// SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionNote1").trim(),
				"ony advisor shared transaction should displayed");
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"),
				"verify second account tranaction is diaplaying");

	}

	@Test(description = "", priority = 167, groups = { "Smoke" })
	public void verifyTxnWhenSearchedByNoteWhichISaddedByAdvisorAndInvestor_Investor() throws Exception {
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote2"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		String note1 = aggre_Tra.note().getText().trim();

		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		// SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		String note2 = readOnly.transactionDetailsReadonlyNoteValue().getText().trim();
		Assert.assertEquals(note1, PropsUtil.getDataPropertyValue("TransactionNote2"),
				" advisor shared transaction note should displayed");
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"),
				"verify second account tranaction is diaplaying");
		Assert.assertEquals(note2, PropsUtil.getDataPropertyValue("TransactionNote2"),
				" advisor shared transaction note should displayed");
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(1).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"),
				"verify second account tranaction is diaplaying");

	}

	@Test(description = "AT-69177:verify investor Accounts transction are diplayed when search transaction from tag in investor view which tag added by investor", priority = 168, groups = {
			"Smoke" })
	public void verifyTxnWhenSearchedByTag_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		accountDrapDown.createTag(PropsUtil.getDataPropertyValue("Transactiontag3"));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(aggre_Tra.PendingStranctionList().get(3));
		SeleniumUtil.waitForPageToLoad(1000);
		accountDrapDown.createTag(PropsUtil.getDataPropertyValue("Transactiontag2"));
		SeleniumUtil.click(aggre_Tra.update());
		SeleniumUtil.waitForPageToLoad();
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("Transactiontag3"));

		SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad();
		String tag1 = aggre_Tra.AggregatedListTag().get(0).getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertEquals(tag1, PropsUtil.getDataPropertyValue("Transactiontag3").trim());
	}

	@Test(description = "AT-69177:verify investor Accounts transction are diplayed when search transaction from tag in investor view which tag added by investor", priority = 169)
	public void verifyTxnWhenSearchedByTag1_Investor() throws Exception {
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"),
				"verify first account tranaction is diaplaying");

	}

	@Test(description = "AT-69178:verify investor Accounts transction and advisor shared Accounts Transactions are diplayed when search transaction from tag in advisor view which tag added by advisor and investor both", priority = 170, groups = {
			"Smoke" })
	public void verifyTxnWhenSearchedByTagWhichAddedAdvisorAndInvestor_Investor() throws Exception {

		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("Transactiontag2"));
		SeleniumUtil.waitForPageToLoad();

		logger.info("verify searched description Transaction sshould displayed");

		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		String tag1 = aggre_Tra.AggregatedListTag().get(0).getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(1));
		String tag2 = readOnly.transactionDetailsReadonlyTagValue().getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertEquals(tag1, PropsUtil.getDataPropertyValue("Transactiontag2"));
		Assert.assertEquals(tag2, PropsUtil.getDataPropertyValue("Transactiontag2"));
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"));
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(1).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"));

	}

	@Test(description = "AT-69176:verify advisor shared Accounts transction are diplayed when search transaction from tagin investor view which tag added by Shared advisor", priority = 171, groups = {
			"Smoke" })
	public void verifyTxnWhenSearchedByTagWhichAddedAdvisor_Investor() throws Exception {

		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("Transactiontag1"));
		SeleniumUtil.waitForPageToLoad();

		logger.info("verify searched description Transaction sshould displayed");

		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		String tag1 = readOnly.transactionDetailsReadonlyTagValue().getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(tag1, PropsUtil.getDataPropertyValue("Transactiontag1").trim());
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"));

	}

	@Test(description = "verify  tag are displayed in tag filter ", priority = 172, groups = { "Smoke" })
	public void verifytaginTagFilter_InInvestorView() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(tag.tagTab());
		String tag1[] = PropsUtil.getDataPropertyValue("TransactionInvesterTagList").split(",");
		for (int i = 0; i < accountDrapDown.TransactionTagList().size(); i++) {

			Assert.assertEquals(tag.TagList().get(i).getText().trim().toLowerCase(), tag1[i].trim().toLowerCase(),
					"tag are not displaying in tag filter");

		}
	}

	@Test(description = "verify  transaction are disaplying when click on searched by Description", priority = 173, groups = {
			"Smoke" })
	public void verifyTransactionWhenApplyTagFilter_InInvestorView() throws Exception {

		SeleniumUtil.click(accountDrapDown.TransactionTagList().get(1));
		SeleniumUtil.waitForPageToLoad();
		logger.info("verify searched description Transaction sshould displayed");
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad();
		String tag1 = aggre_Tra.AggregatedListTag().get(0).getText().trim();
		Assert.assertEquals(tag1, PropsUtil.getDataPropertyValue("Transactiontag3").trim(),
				"all filter selected tag transaction are not displaying");

	}

	@Test(description = "verify multiselect category label is displaying when you select HLC", priority = 174)
	public void verifyTransactionWhenApplyTagFilter1_InInvestorView() throws Exception {
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"),
				"verify first account tranaction is diaplaying");

	}

	@Test(description = "verify  transaction are disaplying when click on searched by Description", priority = 175, groups = {
			"Smoke" })
	public void verifyTagInTagFilterWhichTagAddedByAdvisor_InInvestorView() throws Exception {

		SeleniumUtil.click(tag.tagTab());
		boolean advisorTag = false;
		for (int i = 0; i < accountDrapDown.TransactionTagList().size(); i++) {
			if (tag.TagList().get(i).getText().equals(PropsUtil.getDataPropertyValue("Transactiontag1"))) {
				advisorTag = false;
				break;
			} else {
				advisorTag = true;
			}

		}

		Assert.assertTrue(advisorTag, "advisor added tag is displaying in tag filed in investor view");

	}

	@Test(description = "", priority = 176)
	public void VerifyTagInTagFilterWhichTagAddedByInvestor_InAdvisorView() throws Exception {

		logger.info("login to advisor");
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(tag.tagTab());
		boolean advisorTag = false;
		tag.tagIcon().click();
		for (int i = 0; i < tag.TagList().size(); i++) {
			if (tag.TagList().get(i).getText().equals(PropsUtil.getDataPropertyValue("Transactiontag3"))) {
				advisorTag = false;
				break;
			} else {
				advisorTag = true;
			}

		}

		Assert.assertTrue(advisorTag, "investor added tag is displaying in tag filed in advisor view");
	}

	@Test(description = "AT-69175:verify advisor Accounts transction and investor shared are diplayed when search transaction from tag in advisor view which tag added by advisor and investor both", priority = 177)
	public void verifyTxnSearchedByTagWhichAddedAdvisorAndInvestor_Advisor() throws Exception {
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("Transactiontag2"));
		SeleniumUtil.waitForPageToLoad();

		logger.info("verify searched description Transaction sshould displayed");

		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		String tag1 = readOnly.transactionDetailsReadonlyTagValue().getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(1));
		SeleniumUtil.waitForPageToLoad(2000);
		String tag2 = aggre_Tra.AggregatedListTag().get(0).getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertEquals(tag1, PropsUtil.getDataPropertyValue("Transactiontag2"));
		Assert.assertEquals(tag2, PropsUtil.getDataPropertyValue("Transactiontag2"));
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"));
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(1).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"));

	}

	@Test(description = "", priority = 178, groups = { "Smoke" })
	public void verifyTxnWhenSearchedByTagWhichAddedInvestor_Advisor() throws Exception {

		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("Transactiontag3"));
		SeleniumUtil.waitForPageToLoad();

		logger.info("verify searched description Transaction sshould displayed");

		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad();

		String tag1 = readOnly.transactionDetailsReadonlyTagValue().getText().trim();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(tag1, PropsUtil.getDataPropertyValue("Transactiontag3").trim());
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5").trim());

	}

	@Test(description = "", priority = 179, groups = { "Smoke" })
	public void verifyTxnWhenSearchedByNoteWhichISaddedByInvestor_Advisor() throws Exception {
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote3"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		// SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionNote3").trim(),
				"ony advisor shared transaction should displayed");
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount5"),
				"verify second account tranaction is diaplaying");

	}

	@Test(description = "", priority = 180, groups = { "Smoke" })
	public void verifyTxnWhenSearchedByNoteWhichISaddedByAdvisorAndInvestor_Advisor() throws Exception {
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("TransactionNote2"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		// SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		String note1 = readOnly.transactionDetailsReadonlyNoteValue().getText().trim();

		SeleniumUtil.click(accountDrapDown.allTransactionDescription().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(aggre_Tra.ShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		String note2 = aggre_Tra.note().getText().trim();
		Assert.assertEquals(note1, PropsUtil.getDataPropertyValue("TransactionNote2"),
				" advisor shared transaction note should displayed");
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionInvestorSharedAccount4"),
				"verify second account tranaction is diaplaying");
		Assert.assertEquals(note2, PropsUtil.getDataPropertyValue("TransactionNote2"),
				" advisor shared transaction note should displayed");
		Assert.assertEquals(accountDrapDown.getAllPostedAccount_AMT1().get(1).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("TransactionAdvisordAccount1"),
				"verify second account tranaction is diaplaying");

	}
	// @Test(description = "", priority =184)
	// public void VerifyMangeCatgeoryShouldNotDisplayed_InAdvisorView() throws
	// Exception
	// {

	// PageParser.forceNavigate("Transaction", d);
	// SeleniumUtil.click(feature.morebutton());
	// boolean manageCategory=false;
	// if(accountDrapDown.manage().size()==0)
	// {
	// manageCategory=true;
	// }
	// Assert.assertTrue(manageCategory,"Manage actegory is diaplying in advisor
	// view");
	// }

	@Test(description = "AT-69189", priority = 181)
	public void VerifyManageCategoryFeatureDisplayed_Advisor() throws Exception {
		SeleniumUtil.click(feature.morebutton());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(feature.FeatureTourIcon());
		SeleniumUtil.waitForPageToLoad();

		String header = feature.CoachmarkHeader(1).getText();
		SeleniumUtil.click(feature.NextButton(1, 1));

		SeleniumUtil.waitForPageToLoad();

		String header1 = feature.CoachmarkHeader(2).getText();
		String gotIt = feature.NextButton(2, 2).getText().trim();
		boolean expected = false;
		if (feature.NextButtonlist(3, 2).size() == 0) {
			expected = true;
		}

		Assert.assertTrue(expected);
		Assert.assertEquals(header, PropsUtil.getDataPropertyValue("Transaction_Row_Header"));
		Assert.assertTrue(header1.contains(PropsUtil.getDataPropertyValue("Filter_Header")));
		Assert.assertTrue(gotIt.contains(PropsUtil.getDataPropertyValue("gotit")));

	}

}
