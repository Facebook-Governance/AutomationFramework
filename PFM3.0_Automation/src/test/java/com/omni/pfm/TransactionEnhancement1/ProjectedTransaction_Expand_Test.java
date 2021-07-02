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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_DashBoard_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ProjectedTransaction_Expand_Test extends TestBase {

	AddToCalendar_Transaction_Loc AddToCalendar;
	Transaction_Account_Integration_Loc acct_Tran;
	WebDriver webDriver = null;
	private static final Logger logger = LoggerFactory.getLogger(ProjectedTransaction_Expand_Test.class);
	public static String userName = "";
	Transaction_Search_Loc search;
	Transaction_DashBoard_Integration_Loc DashBoard;
	Transaction_Categorization_Rules_Loc rule;
	Series_Recurence_Transaction_Loc series;
	Transaction_Layout_Loc tran_layout;
	Add_Manual_Transaction_Loc Addmanual_Transaction;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		acct_Tran = new Transaction_Account_Integration_Loc(d);
		search = new Transaction_Search_Loc(d);
		DashBoard = new Transaction_DashBoard_Integration_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		series = new Series_Recurence_Transaction_Loc(d);
		tran_layout = new Transaction_Layout_Loc(d);
		Addmanual_Transaction = new Add_Manual_Transaction_Loc(d);
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68600:verify peojected transaction expand icon", priority = 2, groups = { "Smoke" })
	public void expandIcon() throws Exception {
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(Addmanual_Transaction.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("AmountSearchFrom"),
				PropsUtil.getDataPropertyValue("description1"), 0, 1, 0, 7, 16, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(Addmanual_Transaction.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("AmountSearchTo"),
				PropsUtil.getDataPropertyValue("description1"), 0, 1, 0, 7, 16, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		if (Addmanual_Transaction.isMoreBtnPresent()) {
			Addmanual_Transaction.clickAddManualTransaction();
		} else {
			SeleniumUtil.click(Addmanual_Transaction.addManualLink());
		}
		Addmanual_Transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Amount1"),
				PropsUtil.getDataPropertyValue("description1"), 0, 1, 0, 7, 16, 1,
				PropsUtil.getDataPropertyValue("Note151"));
		SeleniumUtil.waitForPageToLoad(1000);
		// Verify the Expand icon displayed in the projected section header
		Assert.assertTrue(search.ProjectedExapdIcon().isDisplayed());
	}

	@Test(description = "AT-68214,AT-68599:verify projected transactions minimized option", priority = 3, groups = {
			"Smoke" }, dependsOnMethods = "expandIcon")
	public void proTranMinimized() throws Exception {
		// Verify the Projected Transaction section is minimized/Collapsed by default
		boolean expected = false;
		if (search.allTransactionAmount().get(0).getText().equals("")) {
			expected = true;
		}
		Assert.assertTrue(expected, "Projected transactions is not expanded by default");
	}

	@Test(description = "AT-68214,AT-68601:verify projected transactions maximize option", priority = 4, groups = {
			"Smoke" }, dependsOnMethods = "expandIcon")
	public void proTranMaximized() throws Exception {
		SeleniumUtil.click(search.ProjectedExapdIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(series.projectedStranctionList().get(0).isDisplayed(),
				"Projected transactions are not expanded by default");
	}

	@Test(description = "AT-68214,AT-68601:verify projected transactions collapsed icon", priority = 5, groups = {
			"Smoke" }, dependsOnMethods = "expandIcon")
	public void proTrancollapsIcon() throws Exception {
		// Verify the expand icon got changed to minimize icon after expanding the
		// projected section
		Assert.assertTrue(search.ProjectedCollapsIcon().isDisplayed(),
				"Projected transaction collapse icon is not displayed");
	}

	@Test(description = "AT-68603:verify all projected transaction", priority = 6, groups = {
			"Smoke" }, dependsOnMethods = "expandIcon")
	public void verifyAllproTran() throws Exception {
		// Verify all the projected transaction displayed in the projected section
		Assert.assertTrue(search.verifyallProjectedTransaction(),"Projected transactions are not in proper order");
	}

	@Test(description = "AT-68600:verify projected transaction expand icon when navigate from dashboard", priority = 7, groups = {
			"Smoke" })
	public void dashBoardexpandIcon() throws Exception {
		// Verify the Expand icon displayed in the projected section header
		SeleniumUtil.refresh(d);
		SeleniumUtil.click(DashBoard.listaccountRow().get(0));
		Assert.assertTrue(search.ProjectedExapdIcon().isDisplayed(),"Projected transactions expand icon is not displayed");
	}

	@Test(description = "AT-68214,AT-68599:verify projected transaction minimize when navigate from dashboard", priority = 8, groups = {
			"Smoke" }, dependsOnMethods = "dashBoardexpandIcon")
	public void dashBoardproTranMinimized() throws Exception {
		// Verify the Projected Transaction section is minimized/Collapsed by default
		boolean expected = false;
		if (search.allTransactionAmount().get(0).getText().equals("")) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68214,AT-68601:verify projected transaction maximize when navigate from dashboard", priority = 9, groups = {
			"Smoke" }, dependsOnMethods = "dashBoardproTranMinimized")

	public void dashBoardproTranMaximized() throws Exception {
		SeleniumUtil.click(search.ProjectedExapdIcon());
		Assert.assertTrue(series.projectedStranctionList().get(0).isDisplayed());
	}

	@Test(description = "AT-68214,AT-68601;verify projected transaction collaps icon when navigate from dashboard", priority = 10, groups = {
			"Smoke" }, dependsOnMethods = "dashBoardproTranMaximized")

	public void dashBoardproTrancollapsIcon() throws Exception {
		// Verify the expand icon got changed to minimize icon after expanding the
		// projected section
		Assert.assertTrue(search.ProjectedCollapsIcon().isDisplayed());
	}

	@Test(description = "AT-68603:verify projected transaction when navigate from dashboard", priority = 11, groups = {
			"Smoke" }, dependsOnMethods = "dashBoardproTranMaximized")
	public void dashBoardverifyAllproTran() throws Exception {
		// Verify all the projected transaction displayed in the projected section
		Assert.assertTrue(search.verifyallProjectedTransaction());
	}

	@Test(description = "AT-68600:verify projected transaction expand icon when navigate from account", priority = 12, groups = {
			"Smoke" })
	public void AccountexpandIcon() throws Exception {
		// Verify the Expand icon displayed in the projected section header
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.click(acct_Tran.Accountrows().get(0));
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(search.ProjectedExapdIcon().isDisplayed());
	}

	@Test(description = "AT-68214,AT-68601:verify projected transaction minimized when navigate from account", priority = 13, groups = {
			"Smoke" }, dependsOnMethods = "AccountexpandIcon")
	public void AccountproTranMinimized() throws Exception {
		// Verify the Projected Transaction section is minimized/Collapsed by default
		boolean expected = false;
		if (search.allTransactionAmount().get(0).getText().equals("")) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68214,AT-68601:verify projected transaction maximized when navigate from account", priority = 14, groups = {
			"Smoke" }, dependsOnMethods = "AccountproTranMinimized")
	public void AccountproTranMaximized() throws Exception {
		SeleniumUtil.click(search.ProjectedExapdIcon());
		Assert.assertTrue(series.projectedStranctionList().get(0).isDisplayed());
	}

	@Test(description = "AT-68214,AT-68601:verify projected transaction collapsed when navigate from account", priority = 15, groups = {
			"Smoke" }, dependsOnMethods = "AccountproTranMaximized")
	public void AccountproTrancollapsIcon() throws Exception {
		// Verify the expand icon got changed to minimize icon after expanding the
		// projected section
		Assert.assertTrue(search.ProjectedCollapsIcon().isDisplayed());
	}

	@Test(description = "AT-68603:verify projected transaction when navigate from account", priority = 16, groups = {
			"Smoke" }, dependsOnMethods = "AccountproTranMaximized")
	public void AccountverifyAllproTran() throws Exception {
		// Verify all the projected transaction displayed in the projected section
		Assert.assertTrue(search.verifyallProjectedTransaction());
	}

	@Test(description = "AT-68604:verify expand projected transaction option should not displayed when no projected transaction", priority = 17, groups = {
			"Smoke" })
	public void NoproTran() throws Exception {
		// Verify the projected section should not reflect if there are no projected
		// transaction for the user
		PageParser.forceNavigate("Transaction", d);
		if (tran_layout.isMobileFilterIconPresent()) {
			SeleniumUtil.click(tran_layout.MobileFilterIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		SeleniumUtil.click(tran_layout.monthFilter());
		// tran_layout.monthFilter.click();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(tran_layout.monthFileterList().get(1));
		// tran_layout.monthFileterList.get(1).click();
		if (tran_layout.isMobileApplyButtonPresent()) {
			SeleniumUtil.click(tran_layout.MobileApplyButton());
		}
		SeleniumUtil.waitForPageToLoad(2000);
		boolean expected = false;
		if (tran_layout.projectedHeaderList().size() == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68213,AT-68604,AT-68605:verify expand transaction should not displayed when delete all projected transaction", priority = 18, groups = {
			"Smoke" }, dependsOnMethods = "NoproTran")
	public void deletedallproTran() throws Exception {
		// Verify the projected section should not reflect if user deleted all the
		// projected transaction
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(search.ProjectedExapdIcon());
		SeleniumUtil.waitForPageToLoad(5000);
		search.deleteallProjectedtransaction();
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expected = false;
		if (tran_layout.projectedHeaderList().size() == 0) {
			expected = true;
		}
		Assert.assertTrue(expected);
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
