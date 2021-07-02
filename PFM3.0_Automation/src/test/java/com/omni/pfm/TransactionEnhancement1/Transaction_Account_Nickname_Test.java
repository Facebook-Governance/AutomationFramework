/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Account_Nickname_Test extends TestBase {
	AddToCalendar_Transaction_Loc AddToCalendar;
	Transaction_Account_Integration_Loc acct_Tran;
	AccountAddition accountAdd;
	Add_Manual_Transaction_Loc addManual;
	WebDriver webDriver = null;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Account_Nickname_Test.class);
	public static String userName = "";
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_Search_Loc search;
	Transaction_Tag_Loc tag;
	Transaction_Layout_Loc layout;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		acct_Tran = new Transaction_Account_Integration_Loc(d);
		accountAdd = new AccountAddition();
		addManual = new Add_Manual_Transaction_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		search = new Transaction_Search_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		util = new SeleniumUtil();
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("TransactionDetails.site16441.1", "site16441.1");
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68878,AT-68882,AT-68227,AT-68878,AT-68883:verify cash account transactions nick name", priority = 1, groups = {
			"Smoke" })
	public void verifyCashAccountNickName() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		acct_Tran.nickenameAdd(0, PropsUtil.getDataPropertyValue("TransactionAccountCashAccountNickeName"), 0);
		acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_NickName_CashAccountsName"));
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			search.clickExpandTxn();
		}
		boolean nickNametrue = util.assertTrue(util.getWebElementsValue(accountDropDown.allTransactionAccount()),
				PropsUtil.getDataPropertyValue("TransactionAccountCashAccountNickNameNumber"));
		Assert.assertTrue(nickNametrue, "nickName should be displayed");
	}

	@Test(description = "AT-68622,AT-68621:verify reset button should displayed when navigated from account finapp", priority = 2, dependsOnMethods = "verifyCashAccountNickName")
	public void verifyrestButtonShouldNotdisplayed() {
		logger.info(tag.reset().getAttribute("class"));
		Assert.assertTrue(
				tag.reset().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide")),
				"reset button should not displayed without Applied filter");
	}

	@Test(description = "AT-68878,AT-68882,AT-68227,AT-68878,AT-68883:verify card account transactions nick name", priority = 3, dependsOnMethods = "verifyCashAccountNickName")
	public void verifyCardAccountNickName() {
		acct_Tran.clickBackToAccount();
		acct_Tran.nickenameAdd(4, PropsUtil.getDataPropertyValue("TransactionAccountCardAccountNickeName"), 4);
		acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_NickName_CardAccountsName"));
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		boolean nickNametrue = util.assertTrue(util.getWebElementsValue(accountDropDown.allTransactionAccount()),
				PropsUtil.getDataPropertyValue("TransactionAccountCardAccountNickNameNumber"));
		Assert.assertTrue(nickNametrue, "card Acccount nickNameShould Displayed");
	}

	@Test(description = "AT-68878,AT-68882,AT-68883:verify investment account transaction nick name", priority = 4, dependsOnMethods = "verifyCardAccountNickName")
	public void verifyInvestAccountNickName() {
		acct_Tran.clickBackToAccount();
		acct_Tran.nickenameAdd(5, PropsUtil.getDataPropertyValue("TransactionAccountInvestmentAccountNickeName"), 5);
		acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_NickName_InvstAccountsName"));
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		boolean nickNametrue = util.assertTrue(util.getWebElementsValue(accountDropDown.allTransactionAccount()),
				PropsUtil.getDataPropertyValue("TransactionAccountInvestmentAccountNickNameNumber"));
		Assert.assertTrue(nickNametrue, "card Acccount nickNameShould Displayed");
	}

	@Test(description = "AT-68878,AT-68882:verify manual account nick name", priority = 5, dependsOnMethods = "verifyInvestAccountNickName")
	public void verifyManualAccount() throws Exception {
		acct_Tran.clickBackToAccount();
		accountAdd.addManualAccount(PropsUtil.getDataPropertyValue("TransactionManualAccountType"),
				PropsUtil.getDataPropertyValue("TransactionManualAccountName"),
				PropsUtil.getDataPropertyValue("TransactionManualAccountName"),
				PropsUtil.getDataPropertyValue("TransactionManualAccountBalance"),
				PropsUtil.getDataPropertyValue("TransactionManualAccountNumber"),
				PropsUtil.getDataPropertyValue("TransactionManualAccountDueAmount"), addManual.targateDate1(2), "");
		PageParser.forceNavigate("AccountsPage", d);
		acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("TransactionManualAccountName"));
		Assert.assertEquals(acct_Tran.mnualAccountNickName_TAI().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionManualAccountName"), "nick name should be displayed ");
	}

	@Test(description = "AT-68879:verify transaction nick name after applied account filter", priority = 6, dependsOnMethods = "verifyCardAccountNickName")
	public void verifynickNameAfterApplyFilter() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.selectAccFilterAccount(4);
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		boolean nickNametrue = util.assertTrue(util.getWebElementsValue(accountDropDown.allTransactionAccount()),
				PropsUtil.getDataPropertyValue("TransactionAccountCashAccountNickNameNumber"));
		Assert.assertTrue(nickNametrue, "nickName should be displayed");
	}

	@Test(description = "AT-69023,AT-69023:verify closed accounts projected transaction should not displayed", priority = 7)
	public void verifyBankAccountClose() throws Exception {
		PageParser.forceNavigate("AccountSettings", d);
		for (int i = 0; i < 6; i++) {
			acct_Tran.closeAccount(i);
		}
		for (int i = 0; i < 6; i++) {
			acct_Tran.closeAccountsAccountSummery(i);
		}
		PageParser.forceNavigate("Transaction", d);
		boolean projectedHeader = false;
		if (layout.projectedHeaderList().size() == 0) {
			projectedHeader = true;
		}
		Assert.assertTrue(projectedHeader, "projected Transaction Should nOt displayed");
	}

	@Test(description = "AT-68231:verify IH closed accounts info message in Transaction finapp when navigated from account finapp", priority = 8, dependsOnMethods = "verifyBankAccountClose")
	public void verifyIHAccountClose() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_NickName_InvstAccountsName"));
		Assert.assertEquals(acct_Tran.TransactionAccountIHMessage1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAccountIhMsg1"), "verify message after close iH account");
		Assert.assertTrue(
				acct_Tran.TransactionAccountIHMessage2().getText().trim()
						.contains(PropsUtil.getDataPropertyValue("TransactionAccountIhMsg2")),
				"verify message after close iH account");
	}
}
