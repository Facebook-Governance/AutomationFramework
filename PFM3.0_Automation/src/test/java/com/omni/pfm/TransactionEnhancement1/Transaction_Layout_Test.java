/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Attachment_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_DashBoard_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Layout_Test extends TestBase {
	Add_Manual_Transaction_Loc Addmanual_Transaction;
	Series_Recurence_Transaction_Loc Series_Tranc_details;
	Aggregated_Transaction_Details_Loc aggre;
	Transaction_Attachment_Loc attachment;
	Transaction_Layout_Loc tran_layout;
	Transaction_Split_Locator split;
	Transaction_DashBoard_Integration_Loc Trans_dashBoard;
	Transaction_Tag_Loc tag;
	public static String USERNAME = "";
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Layout_Test.class);
	public static String userName = "";
	AddToCalendar_Transaction_Loc cal;
	Transaction_Search_Loc search;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass
	public void testInit() throws Exception {
		doInitialization("Transaction Layout");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		Series_Tranc_details = new Series_Recurence_Transaction_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		aggre = new Aggregated_Transaction_Details_Loc(d);
		Addmanual_Transaction = new Add_Manual_Transaction_Loc(d);
		attachment = new Transaction_Attachment_Loc(d);
		tran_layout = new Transaction_Layout_Loc(d);
		split = new Transaction_Split_Locator(d);
		Trans_dashBoard = new Transaction_DashBoard_Integration_Loc(d);
		cal = new AddToCalendar_Transaction_Loc(d);
		search = new Transaction_Search_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
	}

	@Test(description = "", priority = 1, groups = { "Smoke" })
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		// accountAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		accountAdd.addAggregatedAccount("txnlayout.site16441.1", "site16441.1");
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68604: verify projected transatcion should not displayed when there is no projected transaction", priority = 2)
	public void checkProjectedNoTransaction() throws Exception {
		if (PropsUtil.getDataPropertyValue("PE").equals("true")) {
			SeleniumUtil.click(tran_layout.MobileFilterIcon());
			SeleniumUtil.click(tran_layout.monthFilter());
			SeleniumUtil.click(tran_layout.monthFileterList().get(1));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertTrue(cal.isElementPresent(tran_layout.projectedHeader()));
		} else {
			Assert.assertTrue(!cal.isElementPresent(tran_layout.projectedHeader()));
		}
	}

	@Test(description = "verify plited transaction should not displayed for projected transaction", priority = 3)
	public void verifySplitForSystemGenerated() throws InterruptedException {
		Addmanual_Transaction.clickMTLink();
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"), 1, 1, 0, 31, 1, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		tran_layout.serachTransaction(Series_Tranc_details.DateFormate(7), Series_Tranc_details.DateFormate(7));
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(Series_Tranc_details.projectedStranctionList().get(0));
		SeleniumUtil.click(aggre.ShowMore());
		Assert.assertTrue(cal.isElementPresent(tran_layout.splitTransaction()));
	}

	@Test(description = "AT-68201,AT-68204:verify projected transaction header", priority = 4)
	public void projectedTransactionHeader() throws InterruptedException {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(tran_layout.projectedHeader().getText(),
				PropsUtil.getDataPropertyValue("trans_layoutProjectedHeader"));
	}

	@Test(description = "AT-68201,AT-68204:verify posted transaction header", priority = 5)
	public void postedTransactionHeader() throws InterruptedException {
		Assert.assertEquals(tran_layout.pendingPostedHeader().getText(),
				PropsUtil.getDataPropertyValue("trans_layoutPendingHeader"));
	}

	@Test(description = "AT-68226:verify pending transaction text", priority = 6)
	public void verifyPendingTransaction() throws Exception {
		// Verify the text displayed "Pending" for the pending transactions
		Assert.assertTrue(tran_layout.pendingTransaction().get(0).isDisplayed());
	}

	@Test(description = "verify transaction is sorted date wise ", priority = 7)
	public void postedTransactionsorting() throws Exception {
		Assert.assertTrue(search.verifyallPostedTransaction());
	}

	@Test(description = "verify transaction details", priority = 8)
	public void vrifyTransactiondetails() throws Exception {
		Assert.assertTrue(Addmanual_Transaction.getAllPostedAcount1().get(0).isDisplayed());
		Assert.assertTrue(Addmanual_Transaction.getAllPostedAmount1().get(0).isDisplayed());
		Assert.assertTrue(Addmanual_Transaction.getAlldescription1().get(0).isDisplayed());
	}

	@Test(description = "verify all icon in transaction", priority = 10)
	public void allIconInTransaction() throws Exception {
		// Verify the tags,attachment,notes and split icons displayed for
		// transactions if user added any of these
		SeleniumUtil.click(aggre.PendingStranctionList().get(2));
		aggre.createTag("tag1");
		SeleniumUtil.click(aggre.ShowMore());
		aggre.note().sendKeys("note");
		String path1 = System.getProperty("user.dir");
		System.out.println(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		attachment.attachFile(a);
		SeleniumUtil.click(aggre.update());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(aggre.PendingStranctionList().get(2));
		SeleniumUtil.click(aggre.ShowMore());
		SeleniumUtil.click(split.SplitIcon());
		SeleniumUtil.click(split.savechanges());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		search.allSearch().sendKeys("tag1");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(tran_layout.LayoutTagIcon().isDisplayed());
		Assert.assertTrue(tran_layout.LayoutAttachmentIcon().isDisplayed());
		Assert.assertTrue(tran_layout.LayoutNoteIcon().get(0).isDisplayed());
		Assert.assertTrue(tran_layout.LayoutSplitIcon().get(0).isDisplayed());
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {
		}
	}
}
