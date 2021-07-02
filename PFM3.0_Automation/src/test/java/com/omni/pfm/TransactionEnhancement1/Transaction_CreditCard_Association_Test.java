/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_CreditCard_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_CreditCard_Association_Test extends TestBase {
	public Transaction_CreditCard_Loc card;
	Transaction_Layout_Loc layout;
	Add_Manual_Transaction_Loc add_manual_transaction;
	AddToCalendar_Transaction_Loc caledar;
	WebDriver webDriver = null;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_CreditCard_Association_Test.class);
	public static String userName = "";
	Series_Recurence_Transaction_Loc series;
	LoginPage login;
	AccountAddition accountAdd;
	Transaction_Account_Integration_Loc acct_Tran;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		card = new Transaction_CreditCard_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		series = new Series_Recurence_Transaction_Loc(d);
		caledar = new AddToCalendar_Transaction_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		acct_Tran = new Transaction_Account_Integration_Loc(d);
		util = new SeleniumUtil();
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		// accountAdd.addAggregatedAccount("renuka26.site16441.1", "site16441.1");
		accountAdd.addAggregatedAccount("renuka26.site16441.2", "site16441.2");
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68950,AT-68951,AT-68953,AT-68949", priority = 2, groups = { "Smoke" })
	public void verifyAssociationAccountLink() throws Exception {
		SeleniumUtil.click(card.PendingStranctionList().get(0));
		Assert.assertEquals(card.biller().getText(), PropsUtil.getDataPropertyValue("cardBill"),
				"biller text should be displayed in credaitcard type transaction details screen");
		Assert.assertEquals(card.accAssocciateButton().getText(),
				PropsUtil.getDataPropertyValue("CardAccociateButtonText"),
				"associate account button shouldbe displayed");
		Assert.assertEquals(card.accAssocMessage().getText(), PropsUtil.getDataPropertyValue("cardAssociateMessage"),
				"descliminator message should be displayed");
	}

	@Test(description = "AT-68952", priority = 3, groups = { "Smoke" })
	public void associatePopUp() throws Exception {
		SeleniumUtil.click(card.accAssocciateButton());
		Assert.assertEquals(card.poupHeader().getText(), PropsUtil.getDataPropertyValue("cardAccociaterPopUpHeader"),
				"associate account popup a should be displyed");
	}

	@Test(description = "AT-68955,AT-68954", priority = 4, groups = { "Smoke" })
	public void associatePopUpClose() throws Exception {
		boolean closeButton = card.Closepopup().isDisplayed();
		SeleniumUtil.click(card.Closepopup());
		Assert.assertTrue(closeButton, "close button should be displayed");
		Assert.assertEquals(card.poupHeaderList().size(), 0, "popup should closed when you click on close button");
	}

	@Test(description = "AT-68956", priority = 5, groups = { "Smoke" })
	public void verifylinkBillerAndLinkAccount() throws Exception {
		SeleniumUtil.click(card.accAssocciateButton());
		Assert.assertEquals(card.accociateAccBilletLink().getText().trim(),
				PropsUtil.getDataPropertyValue("cardAccociaterPopUpInfo"),
				"popup should closed when you click on close button");
		Assert.assertEquals(card.LinkAccount().getText().trim(),
				PropsUtil.getDataPropertyValue("cardAccociatelinKAccount"));
	}

	@Test(description = "AT-68963,AT-68967", priority = 6, groups = { "Smoke" })
	public void verifyBillerLinkCardAccount() throws Exception {
		String[] cardname = PropsUtil.getDataPropertyValue("cardAccociateCardList").split(",");
		for (int i = 0; i < cardname.length; i++) {
			util.isAnyMatchInList(util.getWebElementsValue(card.billerLink()), cardname[i]);
		}
	}

	@Test(description = "AT-68957", priority = 7, groups = { "Smoke" })
	public void verifyExistingBiller() throws Exception {
		Assert.assertEquals(card.associateexistingBill().getText().trim(),
				PropsUtil.getDataPropertyValue("cardAccociateCardExistingBiller"),
				"existing bilkler message should displayed");
	}

	@Test(description = "AT-68965", priority = 8, groups = { "Smoke" })
	public void verifyBackButton() throws Exception {
		card.clickCard(PropsUtil.getDataPropertyValue("cardAccociateName"));
		Assert.assertTrue(card.associateBackButton().isDisplayed());
	}

	@Test(description = "AT-68966,AT-68970", priority = 9, groups = { "Smoke" })
	public void verifyBillerLinkInfo() throws Exception {
		Assert.assertEquals(card.associateHowInfo().getText(), PropsUtil.getDataPropertyValue("cardHow"));
	}

	@Test(description = "AT-68966,AT-68968,", priority = 10, groups = { "Smoke" })
	public void verifyBackButtonClick() throws Exception {
		SeleniumUtil.click(card.associateBackButton());
		Assert.assertEquals(card.accociateAccBilletLink().getText().trim(),
				PropsUtil.getDataPropertyValue("cardAccociaterPopUpInfo"),
				"popup should closed when you click on close button");
	}

	@Test(description = "AT-68972,AT-68973", priority = 11, groups = { "Smoke" })
	public void verifyAccountFormField() throws Exception {
		card.clickCard(PropsUtil.getDataPropertyValue("cardAccociateName"));
		Assert.assertEquals(card.fromLabel().getText().trim(), PropsUtil.getDataPropertyValue("cardFromLabel"),
				"account formate should be displayed in prepared formate");
	}

	@Test(description = "AT-68972,AT-68974,AT-68975,AT-68976", priority = 12, groups = { "Smoke" })
	public void verifyAccountFarmate() throws Exception {
		Assert.assertEquals(card.fromAccount().getText().trim(), PropsUtil.getDataPropertyValue("cardFromAccount"),
				"account formate should be displayed in prepared formate");
	}

	@Test(description = "AT-68977", priority = 13, groups = { "Smoke" })
	public void verifyUsualPayment() throws Exception {
		Assert.assertEquals(card.associateUsualPayment().getText().trim(),
				PropsUtil.getDataPropertyValue("cardAccociateCardUsualPayment"),
				"account usual payment should be displayed in prepared formate");
	}

	@Test(description = "AT-68978", priority = 14, groups = { "Smoke" }, enabled = false)
	public void radioButton4Option() throws Exception {
		Assert.assertEquals(card.satament().getText(), PropsUtil.getDataPropertyValue("cardStament"));
		Assert.assertEquals(card.currentBalance().getText(), PropsUtil.getDataPropertyValue("cardCurrentBalance"));
		Assert.assertEquals(card.minimunBalance().getText(), PropsUtil.getDataPropertyValue("cardMinimunBalance"));
		Assert.assertEquals(card.other().getText(), PropsUtil.getDataPropertyValue("cardOther"));
	}

	@Test(description = "AT-68981,AT-68984,AT-68979", priority = 15, groups = { "Smoke" })
	public void statementFooterMessage() throws Exception {
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardStamentFooter"));
		Assert.assertTrue(card.firstradioButtonSelected().getAttribute("class").contains("r_on"));
	}

	@Test(description = "AT-68981,AT-68985,AT-68980", priority = 16, groups = { "Smoke" })
	public void currentFooterMessage() throws Exception {
		SeleniumUtil.click(card.currentBalance());
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardCurrentlanacefooter"));
		Assert.assertTrue(card.CurrentSelected().getAttribute("class").contains("r_on"));
	}

	@Test(description = "AT-68981,AT-68986", priority = 17, groups = { "Smoke" }, enabled = false)
	public void minmumFooterMessage() throws Exception {
		SeleniumUtil.click(card.minimunBalance());
		Assert.assertTrue(card.minselected().getAttribute("class").contains("r_on"));
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardMinumbalancefooter"));
	}

	@Test(description = "AT-68982,AT-68987", priority = 18, groups = { "Smoke" })
	public void otherField() throws Exception {
		SeleniumUtil.click(card.other());
		boolean otherField = card.otherField().isDisplayed();
		card.otherField().sendKeys(PropsUtil.getDataPropertyValue("cardOtherAmount"));
		Assert.assertTrue(otherField);
		Assert.assertTrue(card.getAtributeVAlue(card.otherField().getAttribute("id")).length() == 16);
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardOtherMessage"));
	}

	@Test(description = "AT-68983", priority = 19, groups = { "Smoke" })
	public void otherFieldShould11() throws Exception {
		card.otherField().clear();
		card.otherField().sendKeys(PropsUtil.getDataPropertyValue("cardOtherAmount1"));
		card.otherField().sendKeys(Keys.TAB);
		String Error1 = card.associateOtherError().getText();
		card.otherField().clear();
		card.otherField().sendKeys(PropsUtil.getDataPropertyValue("cardOtherAmount2"));
		card.otherField().sendKeys(Keys.TAB);
		String Error2 = card.associateOtherError().getText();
		Assert.assertEquals(Error1, PropsUtil.getDataPropertyValue("AssociateOtherError"));
		Assert.assertEquals(Error2, PropsUtil.getDataPropertyValue("AssociateOtherErro2"));
	}

	@Test(description = "AT-68988,AT-68989", priority = 20, groups = { "Smoke" })
	public void cancelAccociateAccount() throws Exception {
		Assert.assertEquals(card.cancel().getText(), PropsUtil.getDataPropertyValue("AssociateAccountcancel"));
		Assert.assertEquals(card.save().getText(), PropsUtil.getDataPropertyValue("AssociateAccountLink"));
	}

	@Test(description = "AT-68989", priority = 21, groups = { "Smoke" })
	public void cancelAsscoaitePopup() throws Exception {
		SeleniumUtil.click(card.cancel());
		Assert.assertEquals(card.poupHeaderList().size(), 0, "popup size should be zero");
	}

	@Test(description = "AT-68990", priority = 22, groups = { "Smoke" })
	public void linkAccociateAccount() throws Exception {
		SeleniumUtil.click(card.accAssocciateButton());
		card.clickCard(PropsUtil.getDataPropertyValue("cardAccociateName"));
		SeleniumUtil.click(card.currentBalance());
		SeleniumUtil.click(card.save());
		Assert.assertEquals(card.AssociateSucesssmesgae().getText(),
				PropsUtil.getDataPropertyValue("cardAssociatedToLinkMessage"), "succeses message should be dsipalyed");
	}

	@Test(description = "AT-68990,AT-68991,AT-68992,AT-68994,AT-68995,AT-68996,AT-68998", priority = 23, groups = {
			"Smoke" })
	public void linkAccociateAccountDetails() throws Exception {
		// layout.serachTransaction(readOnly.getEveryMonth14ThDate(),
		// readOnly.getEveryMonth14ThDate());
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		String decription = null;
		String Account = null;
		String category = null;
		int transactionsize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionsize; i++) {
			if (series.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("InvestorAssociateAmount"))) {
				decription = series.getAlldescription1().get(i).getText();
				Account = series.getAllaccount1().get(i).getText().trim();
				category = readOnly.getAllcat1().get(i).getText().trim();
				SeleniumUtil.click(readOnly.getAllcat1().get(i));
				break;
			}
		}
		Assert.assertEquals(decription, PropsUtil.getDataPropertyValue("InvestorAssociateDescriptionName"), "");
		Assert.assertEquals(Account.replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("InvestorAssociateAccountName"), "");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("InvestorAssociateCategory"), "");
	}

	@Test(description = "AT-68999AT-68990,AT-68991,AT-68992,AT-68994,AT-68995,AT-68996", priority = 24, groups = {
			"Smoke" })
	public void associateAccountHeader() throws Exception {
		Assert.assertEquals(card.asscoaiteDetailsHeader().getText(), PropsUtil.getDataPropertyValue("cardHow"), "");
	}

	@Test(description = "AT-68997,AT-69017", priority = 25, groups = { "Smoke" })
	public void associateAccdetailsCardAccount() throws Exception {
		Assert.assertEquals(card.asscoaiteDetailsCardSite().getText(),
				PropsUtil.getDataPropertyValue("asscoaiteDetailsCardSite"), "");
		Assert.assertEquals(card.asscoaiteDetailsCardAccount().getText(),
				PropsUtil.getDataPropertyValue("asscoaiteDetailsCardAccount"), "");
		Assert.assertEquals(card.asscoaiteDetailsCardSchDate().getText(),
				PropsUtil.getDataPropertyValue("asscoaiteDetailsCardSchDate"), "");
	}

	@Test(description = "AT-69000,AT-69002", priority = 26, groups = { "Smoke" })
	public void verifyAssociateAccountdetailsAccount() throws Exception {
		Assert.assertEquals(card.asscoaiteDetailsTraAccount().getText(),
				PropsUtil.getDataPropertyValue("asscoaiteDetailsBankAccount"), "");
	}

	@Test(description = "AT-69005", priority = 28, groups = { "Smoke" })
	public void verifyAssoAcctDetails4type() throws Exception {
		Assert.assertTrue(card.asscoaiteDetailsUsePay().getText()
				.contains(PropsUtil.getDataPropertyValue("cardAccociateCardUsualPayment")));
		Assert.assertTrue(card.projectedStament().getText().contains(PropsUtil.getDataPropertyValue("cardStament")));
		Assert.assertTrue(card.projectedCurrencecbalance().getText()
				.contains(PropsUtil.getDataPropertyValue("cardCurrentBalance")));
		Assert.assertTrue(card.projectedMinimumBalance().getText()
				.contains(PropsUtil.getDataPropertyValue("cardMinimunBalance")));
		Assert.assertTrue(card.projectedOther().getText().contains(PropsUtil.getDataPropertyValue("cardOther")));
		Assert.assertEquals(card.projectedFotterMsg().getText(),
				PropsUtil.getDataPropertyValue("asscoaiteDetailsFooterMessage"));
	}

	@Test(description = "AT-69006", priority = 29, groups = { "Smoke" })
	public void verifyAssoAcctDetails4RadioButton() throws Exception {
		Assert.assertTrue(card.projectedCurrencecbalance().isDisplayed());
		Assert.assertTrue(card.projectedStament().isDisplayed());
		Assert.assertTrue(card.projectedMinimumBalance().isDisplayed());
		Assert.assertTrue(card.projectedOther().isDisplayed());
	}

	@Test(description = "AT-69007,AT-69010", priority = 30, groups = { "Smoke" })
	public void verifyAssoAcctDetailsSelectCurrBal() throws Exception {
		SeleniumUtil.click(card.projectedCurrencecbalance());
		Assert.assertTrue(card.CurrentSelected().getAttribute("class").contains("r_on"));
	}

	@Test(description = "AT-69007,AT-69011", priority = 31, groups = { "Smoke" })
	public void verifyAssoAcctDetailsSelectminBal() throws Exception {
		SeleniumUtil.click(card.projectedMinimumBalance());
		Assert.assertTrue(card.projectedMinSelected().getAttribute("class").contains("r_on"));
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardMinumbalancefooter"));
	}

	@Test(description = "AT-69007,AT-69012", priority = 32, groups = { "Smoke" })
	public void verifyAssoAcctDetailsSelectOthBal() throws Exception {
		SeleniumUtil.click(card.projectedOther());
		Assert.assertTrue(card.proOtherSelected().getAttribute("class").contains("r_on"));
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardOtherMessage"));
	}

	@Test(description = "AT-69007,AT-69009", priority = 33, groups = { "Smoke" })
	public void verifyAssoAcctDetailsSelectstmBal() throws Exception {
		SeleniumUtil.click(card.projectedStament());
		Assert.assertTrue(card.proStatement().getAttribute("class").contains("r_on"));
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardStamentFooter"));
	}

	@Test(description = "AT-69008,AT-69012,AT-69001", priority = 34, groups = { "Smoke" })
	public void verifyAssoAcctDetailstOthfield() throws Exception {
		SeleniumUtil.click(card.projectedOther());
		Assert.assertTrue(card.otherField().isDisplayed());
		Assert.assertEquals(card.FooterMessage().getText(), PropsUtil.getDataPropertyValue("cardOtherMessage"));
	}

	@Test(description = "AT-69003,AT-69004", priority = 35, groups = { "Smoke" })
	public void verifyAssoAcctDetailsAccDropdown() throws Exception {
		SeleniumUtil.click(card.asscoaiteDetailsTraAccount());
		SeleniumUtil.click(card
				.asscoaiteDetailsTraAccountList(PropsUtil.getDataPropertyValue("asscoaiteDetailsBankAccountUpdated")));
		Assert.assertEquals(card.asscoaiteDetailsTraAccount().getText(),
				PropsUtil.getDataPropertyValue("asscoaiteDetailsBankAccountUpdated"), "");
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(webDriver);
		} catch (Exception e) {
		}
	}
}
