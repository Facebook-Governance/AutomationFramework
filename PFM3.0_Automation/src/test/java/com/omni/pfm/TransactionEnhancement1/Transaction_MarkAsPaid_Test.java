/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_MarkAsPaid_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_MarkAsPaid_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Transaction_MarkAsPaid_Test.class);
	public static String userName = "";

	Transaction_MarkAsPaid_Loc markASPaid;
	Add_Manual_Transaction_Loc manual;
	Series_Recurence_Transaction_Loc series;
	Transaction_Search_Loc search;
	Transaction_Layout_Loc layout;
	LoginPage login;
	AccountAddition accountAdd;
	Aggregated_Transaction_Details_Loc aggregatedTransaction;
	Transaction_AccountDropDown_Loc accountDropDown;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		doInitialization("Transaction Mark As Paid");
		aggregatedTransaction = new Aggregated_Transaction_Details_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		markASPaid = new Transaction_MarkAsPaid_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);
		series = new Series_Recurence_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		search = new Transaction_Search_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		util = new SeleniumUtil();
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("MarkAsPaid2.site16441.1", "site16441.1");
		PageParser.forceNavigate("Transaction", d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(description = "AT-68771,AT-68775,AT-68782,AT-68779,AT-68780:verify mark as paid option", priority = 1)
	public void verifyMarkAsPaidOption() throws Exception {

		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange7"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange8"));

		layout.serachTransaction(manual.targateDate1(27), manual.targateDate1(33));
		layout.expandProjectedTransactions();
		Assert.assertTrue(markASPaid.MarkAsPaid().get(0).isDisplayed());

	}

	@Test(description = "AT-68783:verify mark as paid popup", priority = 2, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUp() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		Assert.assertTrue(markASPaid.MarkAsPaidPopUp().get(0).isDisplayed());

	}

	@Test(description = "AT-68786:verify close button in mark as paid popup", priority = 3, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpClose() throws Exception {
		Assert.assertTrue(markASPaid.MarkAsPaidPopUpClose().isDisplayed());
	}

	@Test(description = "AT-68785:verify cancel button in mark as paid popup", priority = 4, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpCancel() throws Exception {
		Assert.assertTrue(markASPaid.MarkAsPaidPopUpcancel().isDisplayed());
	}

	@Test(description = "AT-68786,AT-68788:verify mark as paid button in maek as paid popup", priority = 5, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpPaid() throws Exception {
		Assert.assertTrue(markASPaid.MarkAsPaidPopUpPaid().isDisplayed());
	}

	@Test(description = "AT-68787:verify mark as pad popup should closed when click on cancel button", priority = 6, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpcancelclick() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpcancel());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidPopup, 2),
				"Cancel button functionality in mark as paid popup is not working");
	}

	@Test(description = "AT-68789:verify mark as paid popup should closed when closed on close icon", priority = 7, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpCloseclick() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpClose());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidPopup, 2),
				"Close button functionality in mark as paid popup is not working");
	}

	@Test(description = "AT-68784,AT-68795,AT-68796,AT-68797,AT-68798:verify infomation in mark as paid popup", priority = 8, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpPaidMessage() throws Exception {
		logger.info(
				"AT-52106:Verify the details displayed in Mark as Paid pop up window should contain all the details of that particular transaction like amount, description and date.");
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		List<String> values = new ArrayList<>();
		values = util.getWebElementsValue(search.projectedDateHeader());
		String txnDate = search.convertMMMMToMM(values, values.size() - 1);
		Assert.assertEquals(markASPaid.MarkAsPaidPopUpMessage().getText(),
				PropsUtil.getDataPropertyValue("markAsPaidMessage1") + ""
						+ PropsUtil.getDataPropertyValue("marrkasPaidAmount") + " "
						+ PropsUtil.getDataPropertyValue("marrkasPaidDescription") + " "
						+ PropsUtil.getDataPropertyValue("markAsPaidMessage2") + " " + txnDate + " "
						+ PropsUtil.getDataPropertyValue("markAsPaidMessage3"));
	}

	@Test(description = "AT-68790:verify check box message in mark as paid popup", priority = 9, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpCheckBoxMessage() throws Exception {
		Assert.assertEquals(markASPaid.MarkAsPaidPopUpCheckBoxMesg().getText().trim(),
				PropsUtil.getDataPropertyValue("marAsPaidCheckBoxMessage"));

	}

	@Test(description = "AT-68776,AT-68794,AT-68804:verify mark as paid pop should closed when click on mark paid button", priority = 10, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpPaidClick() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpPaid());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(3);
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidButton, 2),
				"Mark as paid button is still displayed");
		Assert.assertTrue(!SeleniumUtil.isDisplayed(search.projectedHeader, 2),
				"mark add paid date header should not displayed");
	}

	@Test(description = "AT-68792,AT-68802:verify mark paid when without selecting check box", priority = 11, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifywhenDontselectCheckBox() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange9"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange10"));
		layout.serachTransaction(manual.targateDate1(27), manual.targateDate1(33));
		layout.expandProjectedTransactions();
		// boolean markaspaid=markASPaid.MarkAsPaid().get(0).isDisplayed();
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpPaid());
		layout.serachTransaction(manual.targateDate1(60), manual.targateDate1(64));
		boolean markaspaid = markASPaid.MarkAsPaid().get(0).isDisplayed();
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		Assert.assertTrue(markaspaid, "mark as paid should dsipalyed for next instance");
		Assert.assertTrue(markASPaid.MarkAsPaidPopUp().get(0).isDisplayed(),
				"mark as paid popup should displayed when privois mark paid is not selectd check box");

	}

	@Test(description = "AT-68803:verify mark paid after updating transaction", priority = 12, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkAspaidAfterUpdateTransaction() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange11"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange12"));
		layout.serachTransaction(manual.targateDate1(27), manual.targateDate1(34));
		layout.expandProjectedTransactions();
		series.editSeriesTransaction(series.getAllAmount1().size() - 1,
				PropsUtil.getDataPropertyValue("marrkasPaidAmountupdate"), manual.targateDate1(32));
		List<String> values = new ArrayList<>();
		values = util.getWebElementsValue(search.projectedDateHeader());
		String txnDate = search.convertMMMMToMM(values, 0);
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		Assert.assertEquals(markASPaid.MarkAsPaidPopUpMessage().getText(),
				PropsUtil.getDataPropertyValue("markAsPaidMessage1") + ""
						+ PropsUtil.getDataPropertyValue("marrkasPaidAmountChange") + " "
						+ PropsUtil.getDataPropertyValue("marrkasPaidDescriptionchange") + " "
						+ PropsUtil.getDataPropertyValue("markAsPaidMessage2") + " " + txnDate + " "
						+ PropsUtil.getDataPropertyValue("markAsPaidMessage3"));

	}

	@Test(description = "AT-68805,AT-68781:verify mark as paid when update transaction to future date", priority = 13, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkAsPaidFAfterUpdateTraFutureDate() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(manual.addManualLink());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactionAmount1"),
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactionDescription1"), 1, 5, 0, 0, 3, 1,
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactionNote1"),
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactioncheck1"));
		for (int i = 0; i < 5; i++) {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		int transactionSize1 = manual.getAllPostedAmount1().size();
		for (int i = 0; i < transactionSize1; i++) {
			if (manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedsearchAmount"))) {
				SeleniumUtil.click(manual.getAllPostedAmount1().get(i));
				break;
			}
		}
		series.Scheduledate().clear();
		series.Scheduledate().sendKeys(manual.targateDate1(2));
		SeleniumUtil.click(aggregatedTransaction.update());
		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange13"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange14"));
		layout.expandProjectedTransactions();
		Assert.assertTrue(markASPaid.MarkAsPaid().get(0).isDisplayed());
	}

	@Test(description = "AT-68805:verify mark as paid for manual transaction", priority = 14, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkAsPaidForManualTransaction() throws Exception {
		SeleniumUtil.click(manual.addManualIcon());
		manual.createTransactionWithRecur(PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionAmount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionDescription"), 5, 1, 0, 30, 1, 1);
		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange15"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange16"));
		SeleniumUtil.click(search.ProjectedExapdIcon());
		Assert.assertTrue(markASPaid.MarkAsPaid().get(0).isDisplayed());
	}

	@Test(description = "AT-68777:verify mark as paid for card type transaction", priority = 15, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkAsPaidcardType() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.click(accountDropDown.myAccountListAccountCheckBox().get(3));
		layout.serachTransaction(manual.targateDate1(27), manual.targateDate1(33));
		Assert.assertTrue(markASPaid.MarkAsPaid().get(0).isDisplayed());
	}

	@Test(description = "AT-68777:verify mark as paid for invetsment type transaction", priority = 16, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkAsPaidInvestType() throws Exception {
		SeleniumUtil.click(manual.addManualIcon());
		manual.createInvestmentTransaction(PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentAmount"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentdescription"), 2, 1, 4, 31,
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentSymbol"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentCUSIP"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentQuantity"),
				PropsUtil.getDataPropertyValue("InstanceTransactionInvestmentprice"), 1, 1);
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.click(accountDropDown.myAccountListAccountCheckBox().get(4));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidButton, 2), "");
	}

	@Test(description = "AT-68806:verify mark as paid for deposit type transaction", priority = 17, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkAsPaidDepostType() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange17"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange18"));
		layout.serachTransaction(manual.targateDate1(27), manual.targateDate1(33));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidButton, 2), "");
	}

	@Test(description = "AT-68791:verify mark as paid with selecting show me again check box", priority = 18, dependsOnMethods = "verifyMarkAsPaidOption")
	public void verifyMarkASpaidPopUpdontShowMegain() throws Exception {
		search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange19"),
				PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange20"));
		layout.serachTransaction(manual.targateDate1(27), manual.targateDate1(65));
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpCheckBoxMesg());
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpPaid());
		SeleniumUtil.click(search.amountApplyButton());
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidPopup, 2), "");
	}

	@Test(description = "AT-68776,AT-68794,AT-68801,AT-68800:verify projected transaction and mark as paid after applied mark as paid for all transaction", priority = 19)
	public void verifyAfterAplyallTrans() throws Exception {
		Assert.assertTrue(!SeleniumUtil.isDisplayed(layout.projectedHeader, 2));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(markASPaid.markAsPaidPopup, 2));
	}
}
