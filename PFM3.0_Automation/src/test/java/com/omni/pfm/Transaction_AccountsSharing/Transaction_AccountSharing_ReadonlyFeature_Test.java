/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
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
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.DownloadTrans_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Attachment_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Budget_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Expanse_Income_Analysis_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountSharing_ReadonlyFeature_Test extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(Transaction_AccountSharing_ReadonlyFeature_Test.class);
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Transaction_Expanse_Income_Analysis_Loc expanseIncome;
	Transaction_Budget_Integration_Loc budget;
	AccountAddition acccuntAdd;
	int investorEventRow = 0;
	int InvestorManulaTraRow = 0;
	int investorManulaInvstTraRow = 0;
	int advisorEventRow = 0;
	int advisorManulaTraRow = 0;
	int advisorManulaInvstTraRow = 0;
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
	Transaction_AccountDropDown_Loc accountDropDown;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Search_Loc search;
	Transaction_Layout_Loc layout;
	Aggregated_Transaction_Details_Loc aggre_Tra;
	Transaction_Tag_Loc tag;
	DownloadTrans_Loc feature;
	Series_Recurence_Transaction_Loc series;
	Transaction_Attachment_Loc attchment;
	Transaction_Categorization_Rules_Loc rule;

	@BeforeClass
	public void testInit() throws Exception {
		doInitialization("Transaction AccountSharing ReadonlyFeature Test");
		TestBase.tc = TestBase.extent.startTest("Transaction AccountSharing ReadonlyFeature Test", "Login");
		TestBase.test.appendChild(TestBase.tc);
		acccuntAdd = new AccountAddition();
		SAMLlogin = new LoginPage_SAML3();
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		search = new Transaction_Search_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		aggre_Tra = new Aggregated_Transaction_Details_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		feature = new DownloadTrans_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		expanseIncome = new Transaction_Expanse_Income_Analysis_Loc(d);
		budget = new Transaction_Budget_Integration_Loc(d);
		series = new Series_Recurence_Transaction_Loc(d);
		attchment = new Transaction_Attachment_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);

		createInvestorAdvisor();
		invester1SharedAccount();
		advisor1SharedAccount();
		preConditionRDFeatureDepositTypeTra_AdvisorView();
		preConditionRDFeatureWithdrwalTypeTra_AdvisorView();
		preConditionRDEduTypeTra_AdvisorView();
		preConditionRDFeatureEventTran_AdvisorView();
		preConditionVerifyRDFManualTran_AdvisorView();
		preConditionRDInvestTran_AdvisorView();
	}

	@Test(description = "AT-70767:TA_01_01:verify agregated Transaction amount read only  ", priority = 13)
	public void verifyAggTxnamtFieldReadonly_advisor() throws Exception {
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountDropDown.clickOnShowMoreButton();
		for (int i = 0; i < add_Manual.getAllPostedAmount1().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue"))) {
				advisorManulaTraRow = i;
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_02:description field value Should be readonly", priority = 14, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnDespFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadonlyDescription1"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_03:verify agregated Transaction simple drescription read only  ", priority = 15, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnStatDespFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionValue"),
				"Statement  field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_04:verify agregated Transaction account read only  ", priority = 16, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnAccFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_05:verify agregated Transaction date read only  ", priority = 17, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnDateFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70769:TA_01_06", priority = 18, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnCatgFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryValue"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70774:TA_01_08", priority = 19, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnTagFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag1"), "tag field value Should be readonly");

	}

	@Test(description = "AT-70771,AT-70773:TA_01_09", priority = 20, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnNoteFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNote1"), "Note field value Should be readonly");

	}

	@Test(description = "AT-70776:TA_01_10", priority = 21, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnAttachmentReadonly_advisor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		String acttachmentHeader = attchment.popUpHeader().getText();

		SeleniumUtil.click(attchment.close());
		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70768:TA_01_11", priority = 22, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnAddToCalReadonly_advisor() throws Exception {
		boolean addcalLinkfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.addToCalendarIcon,2)) {
			addcalLinkfalse = true;
		}
		Assert.assertTrue(addcalLinkfalse);
	}

	@Test(description = "AT-70772:TA_01_012", priority = 22, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnShowMoreanderReadonly_advisor() throws Exception {
		boolean addcalLinkfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.addToCalendarIcon,2)) {
			addcalLinkfalse = true;
		}
		Assert.assertTrue(addcalLinkfalse);
	}

	@Test(description = "AT-70767:TA_01_13:verify agregated Transaction attachment read only  ", priority = 23, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnDescReadonly_advisor() throws Exception {
		boolean descriptionfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.transactionDetailsDescription,2)) {
			descriptionfalse = true;
		}
		Assert.assertTrue(descriptionfalse);
	}

	@Test(description = "AT-70770,TA_01_14: ", priority = 24, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnTagReadonly_advisor() throws Exception {
		boolean tagfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.transactionTagLink,2)) {
			tagfalse = true;
		}
		Assert.assertTrue(tagfalse);
	}

	@Test(description = "AT-70777:TA_01_15", priority = 25, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnSplitReadonly_advisor() throws Exception {
		Assert.assertTrue(SeleniumUtil.getElementCount(readOnly.split_ATD)== 0, "Size is not 0 thats why");
	}

	@Test(description = "AT-70778:TA_01_16", priority = 26, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnSaveReadonly_advisor() throws Exception {
		boolean savefalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.transactionDetailsSave,2)) {
			savefalse = true;
		}
		Assert.assertTrue(savefalse);
	}

	@Test(description = "AT-70778,AT-70779:TA_01_17", priority = 27, dependsOnMethods = "verifyAggTxnamtFieldReadonly_advisor")
	public void verifyAggTxnCloseButton_advisor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(1).getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:TA_01_018:verify agregated Transaction amount read only  ", priority = 60)
	public void verifyEnevtTxnAmtFieldReadonly_advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		layout.serachTransaction(add_Manual.targateDate1(28), add_Manual.targateDate1(37));
		if (readOnly.getAllAmount1().get(0).getText() == null) {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		}
		int transactionsize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionsize; i++) {
			logger.info(readOnly.getAllAmount1().get(i).getText());
			logger.info(PropsUtil.getDataPropertyValue("TransactionEventReadOnyAmount"));
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionEventReadOnyAmount"))) {
				investorEventRow = i;
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionEventReadOnyAmount"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_019:verify event Transaction drescription read only  ", priority = 61, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEnevtTxnDespFieldReadonly_advisor() throws Exception {

		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionEventReadOnyDescription"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_020:verify event Transaction account read only  ", priority = 62, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEnevtTxnAccFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_021verify event Transaction date read only  ", priority = 63, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEnevtTxnDateFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(),
				add_Manual.targateDate1(series.TransactionDate(0)), "Date field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_022:verify event Transaction category read only  ", priority = 64, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEnevtTxnCatgFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionEventReadOnyCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_23:verify event Transaction tag read only  ", priority = 65, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEnevtTxnTagFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionEventReafOnlyTag1"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70771,AT-70773:TA_01_024:verify event Transaction note read only  ", priority = 66, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEnevtTxnNoteFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionEventReafOnlyNote"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70778,AT-70779:TA_01_25:verify event Transaction attachment read only  ", priority = 67, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_advisor")
	public void verifyEventTxnCloseButton_advisor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsedSeries().get(investorEventRow)
				.getAttribute("aria-expanded").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertEquals(transactionColapsed, "false", " Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:TA_01_026:verify manual Transaction amount read only  ", priority = 68)
	public void verifyMTAmtFieldReadonly_advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_Manual.getAllPostedAmount1().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("investorManualTransactionamount1"))) {
				InvestorManulaTraRow = i;
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}

		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("investorManualTransactionamount1"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_027:verify manual Transaction drescription read only  ", priority = 69, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTDespFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("investorManualTransactionDescription"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_028:verify manual Transaction account read only  ", priority = 70, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTAccFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("investorManualTransactionAccount"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_029:verify manual Transaction date read only  ", priority = 71, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTDateFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(),
				add_Manual.targateDate1(0), "Date field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_030:verify manual Transaction category read only  ", priority = 72, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTCatgFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("investorManualTransactionCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_31:verify manual Transaction tag read only  ", priority = 73, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTTagFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("investorManualTransactionTag"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70771,AT-70773:TA_01_032:verify manual Transaction note read only  ", priority = 74, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTNoteFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("investorManualTransactionNote"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70778,AT-70779:TA_01_33:verify manual Transaction attachment read only  ", priority = 75, dependsOnMethods = "verifyMTAmtFieldReadonly_advisor")
	public void verifyMTCloseButton_advisor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(InvestorManulaTraRow)
				.getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:TA_01_034:verify investment Transaction amount read only  ", priority = 76)
	public void verifyInvstTxnnAmFieldReadonly_advisor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		layout.serachTransaction(add_Manual.targateDate1(2), add_Manual.targateDate1(2));
		SeleniumUtil.waitForPageToLoad();
		int transactionsize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionsize; i++) {

			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionAmount1"))) {
				investorManulaInvstTraRow = i;
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}

		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionAmount1"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_035:verify investment Transaction drescription read only  ", priority = 77, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestmentTransactionDescriptionFieldReadonly_IndvisorView() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionDescriptiont"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_036:verify investment Transaction account read only  ", priority = 78, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnAccFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTRansactionAccount"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_037:verify investment Transaction date read only  ", priority = 79, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnDateFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(),
				add_Manual.targateDate1(2), "Date field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_038verify investment Transaction sybool read only  ", priority = 80, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnSymbolFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlySymbolLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvetsmentTransactionSymbolLabel"),
				"symbol field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlySymbolValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionSymbol"),
				"symbol field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_039verify investment Transaction CUS read only  ", priority = 81, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnCUSFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCUSLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvetsmentTransactionCUSIPLabel"),
				"CUSIP field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCUSValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionCUS"),
				"CUSIP field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_040verify investment Transaction type read only  ", priority = 82, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnInvestTypeFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyInvestmentTypeLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionTypeLabel"),
				"Invetsment Type field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyInvestmentTypeValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionTypeValue"),
				"Invetsment Type field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_042:verify investment Transaction quantity read only  ", priority = 83, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnQuantityFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyQuantityLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionQuantityLabel"),
				"Quantity field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyQuantityValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionQuantity"),
				"Quantity field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_043:verify investment Transaction holding read only  ", priority = 84, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnHoldingFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyHoaldingLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionHoldingLabel"),
				"holding field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyHoaldingValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionHoldingValue"),
				"holding field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_044:verify investment Transaction lot read only  ", priority = 85, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnLotFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyLotLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionLotLabel"),
				"lot field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyLotValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionLotValue"),
				"lot field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_045:verify investment Transaction tag read only  ", priority = 86, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnTagFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionTag"),
				"tag field value Should be readonly");
	}

	@Test(description = "AT-70771,AT-70773:TA_01_046verify investment Transaction note read only  ", priority = 77, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnNoteFieldReadonly_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionNote"),
				"Note field value Should be readonly");
	}

	@Test(description = "AT-70778,AT-70779:TA_01_047:verify investment Transaction close displayed  ", priority = 88, dependsOnMethods = "verifyInvstTxnnAmFieldReadonly_advisor")
	public void verifyInvestTxnCloseButton_advisor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsedSeries().get(investorManulaInvstTraRow)
				.getAttribute("aria-expanded").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertEquals(transactionColapsed, "false", " Transaction details should close when click on close");
	}

	@Test(description = "AT-70783:TA_01_048:verify expanse all field are read only  ", priority = 89)
	public void verifyTxnAmtdReadonlyInEA_advisor() throws Exception {

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(expanseIncome.goToAnalysis());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(15000);
		SeleniumUtil.click(expanseIncome.excatList().get(0));
		SeleniumUtil.waitForPageToLoad();
		int category = expanseIncome.excatList().size();

		for (int i = 0; i < category; i++) {
			if (expanseIncome.excatList().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Transactionexpcategory").trim())) {
				SeleniumUtil.click(expanseIncome.excatList().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
				break;
			}

		}

		SeleniumUtil.click(expanseIncome.tranamount1().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorEATransactionAmount"), "Amount field value Should be readonly");

	}

	@Test(description = "AT-70783:TA_01_049:verify expanse Transaction description read only  ", priority = 90, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnDescpFieldReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadonlyDescription3"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_050:verify expanse Transaction statment description read only  ", priority = 91, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnSttDescpFieldReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorEATransactionDescription"),
				"Statement  field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_051:verify expanse Transaction account read only  ", priority = 92, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnAccFieldReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_052:verify expanse Transaction date read only  ", priority = 93, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnDateReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_053:verify expanse Transaction marchant read only  ", priority = 94, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnMarchantReadonlyInEA_andvisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsMarchantLable().getText(),
				PropsUtil.getDataPropertyValue("InvestorEATransactionMarchantLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsMarchantValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorEATransactionCategory"), "Date field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_054:verify expanse Transaction category read only  ", priority = 95, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnCategoryReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorEATransactionCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_055:verify expanse Transaction tag read only  ", priority = 96, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnTagReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag7"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_056:verify expanse Transaction note read only  ", priority = 97, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnNoteReadonlyInEA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNote1"), "Note field value Should be readonly");

	}

	@Test(description = "AT-70783:TA_01_057verify expanse Transaction attachment read only  ", priority = 98, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnAttachmentReadonlyInEA_advisor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		String acttachmentHeader = attchment.popUpHeader().getText();

		SeleniumUtil.click(attchment.close());
		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70783:TA_01_058verify expanse Transaction close read only  ", priority = 99, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_advisor")
	public void verifyTxnCloseButtonInEA_advisor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(0).getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70783:TA_01_059:verify income Transaction amount read only  ", priority = 100)
	public void verifyTxnAmtdReadonlyInIA_advisor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(expanseIncome.exp());
		SeleniumUtil.scrollElementIntoView(d, expanseIncome.incomeAys(), true);
		SeleniumUtil.click(expanseIncome.incomeAys());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.excatAmount().get(0));
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(expanseIncome.tranamount1().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorIATransactionAmount"), "Amount field value Should be readonly");

	}

	@Test(description = "AT-70783:TA_01_060:verify income Transaction description read only  ", priority = 101, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnDespFieldReadonlyInIA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadonlyDescription1"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_061:verify income Transaction Stament desciption read only  ", priority = 102, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnStatDespFieldReadonlyInIA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorIATransactionDescription"),
				"Statement  field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_062verify income Transaction Account read only  ", priority = 103, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnAccFieldReadonlyInIA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_063verify income Transaction date read only  ", priority = 104, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnDateReadonlyInIA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_064verify income Transaction category read only  ", priority = 105, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnCatgReadonlyInIA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorIATransactionCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_065:verify income Transaction tag read only  ", priority = 106, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnTagReadonlyInIA_avisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag1"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_066:verify income Transaction note read only  ", priority = 107, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTNoteReadonlyInIA_advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNote1"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_068verify income Transaction attachment read only  ", priority = 108, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnAttachmentReadonlyInIA_advisor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		SeleniumUtil.waitForPageToLoad();
		String acttachmentHeader = attchment.popUpHeader().getText();
		SeleniumUtil.click(attchment.close());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70783:TA_01_069verify income Transaction close read only  ", priority = 109, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_advisor")
	public void verifyTxnCloseButtonInInIA_advisor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(0).getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 110)
	public void preConditionVerifyReadonlyFeatureDepositTypeTra_InvestorView() throws Exception {
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitForPageToLoad();
		logger.info("add all details to first posted aggregated transaction");
		readOnly.editPostedTransaction(7, PropsUtil.getDataPropertyValue("AdvisorAggTransactionDescription1"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag1"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote1"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionAmount"));

	}

	@Test(description = "AT-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 111)
	public void preConditionVerifyReadonlyFeatureATMWithdrwalTypeTra_InvestorView() throws Exception {

		logger.info("add all details to second posted aggregated transaction");
		readOnly.editPostedTransaction(5, PropsUtil.getDataPropertyValue("AdvisorAggTransactionDescription2"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag2"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote2"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("AdvisorBudgetAmount"));
	}

	@Test(description = "AT-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 112)
	public void preConditionVerifyReadonlyFeatureEducationTypeTra_InvestorView() throws Exception {

		logger.info("add all details to 3rd posted aggregated transaction");

		readOnly.editPostedTransaction(6, PropsUtil.getDataPropertyValue("AdvisorAggTransactionDescription3"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag3"),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote3"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("AdvisorIEAmount"));

	}

	@Test(description = "AT-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 115)
	public void preConditionVerifyReadonlyFeatureEventTransaction_InvestorView() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitForPageToLoad();
		logger.info("create event");
		readOnly.createEvent(8, PropsUtil.getDataPropertyValue("AdvisorEventTransactionTag"),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionNote"),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionamount"));

	}

	@Test(description = "AT-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 116)
	public void preConditionVerifyReadonlyFeatureManualTransaction_InvestorView() throws Exception {

		logger.info("create manual transaction");
		SeleniumUtil.click(add_Manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		add_Manual.createTransactionWithRecuralldetailsInvestorAndAdvisor(
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionamount"),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionDescription"), 1, 1, 0, 7, 1, 1,
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionTag"),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionNote"));
	}

	@Test(description = "AT-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 117)
	public void preConditionVerifyReadonlyFeatureInvestmentTransaction_InvestorView() throws Exception {

		logger.info("create investment transaction");
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(add_Manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		add_Manual.createInvestmentTransactionAdviosrInvestor(
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionAmount"),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionDecsription"), 2, 1, 1, 2,
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionSymbol"),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionCUS"),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionPrice"),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionQuantity"), 1, 1,
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionTag"),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionNote"));
	}

	@Test(description = "AT-70767:TA_01_070:verify agregated Transaction amount read only  ", priority = 118, dependsOnMethods = "preConditionVerifyReadonlyFeatureDepositTypeTra_InvestorView")
	public void verifyAggTxnAmtFieldReadonly_Investor() throws Exception {
		SAMLlogin.loginAsInvestor(d, InvesterName1, "10003700");

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_Manual.getAllPostedAmount1().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("AdvisorAggTransactionAmount"))) {
				advisorManulaTraRow = i;
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionAmount"), "Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_071:verify agregated Transaction drescription read only  ", priority = 119, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnDespFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionDescription1"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_072:verify agregated Transaction simple drescription read only  ", priority = 120, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnStatDespFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggStatDescription"),
				"Statement  field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_073:verify agregated Transaction account read only  ", priority = 121, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnAcctFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionAccountName"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_074:verify agregated Transaction date read only  ", priority = 122, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnDateFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_075verify agregated Transaction category read only  ", priority = 123, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnCatgFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_076:verify agregated Transaction tag read only  ", priority = 124, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnTagFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag1"), "tag field value Should be readonly");

	}

	@Test(description = "AT-70771,AT-70773:TA_01_077:verify agregated Transaction note read only  ", priority = 125, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggrgatedTransactionNoteFieldReadonly_InvestorView() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote1"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70768:TA_01_78:verify agregated Transaction add link should not displayed ", priority = 126, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnAddToCalReadonly_Investor() throws Exception {
		boolean addcalLinkfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.addToCalendarIcon,2)) {
			addcalLinkfalse = true;
		}
		Assert.assertTrue(addcalLinkfalse);
	}

	@Test(description = "AT-70772:TA_01_079:verify agregated Transaction show more should not displayed  ", priority = 127, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnDespReadonly_Investor() throws Exception {
		boolean descriptionfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.transactionDetailsDescription,2)) {
			descriptionfalse = true;
		}
		Assert.assertTrue(descriptionfalse);
	}

	@Test(description = "AT-70770,TA_01_080:verify agregated Transaction tag should be read only  ", priority = 128, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnTagReadonly_Investor() throws Exception {
		boolean tagfalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.transactionTagLink,2)) {
			tagfalse = true;
		}
		Assert.assertTrue(tagfalse);
	}

	@Test(description = "AT-70777:TA_01_081:verify agregated Transaction split link should displayed   ", priority = 129, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnSplitReadonly_Investor() throws Exception {
		boolean splitfalse = false;
		if (SeleniumUtil.getElementCount(readOnly.split_ATD)== 0) {
			splitfalse = true;
		}
		Assert.assertTrue(splitfalse);
	}

	@Test(description = "AT-70778:TA_01_082::verify agregated Transaction attachment read only  ", priority = 130, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnSaveReadonly_Investor() throws Exception {
		boolean savefalse = false;
		if (SeleniumUtil.isDisplayed(readOnly.transactionDetailsSave,2)) {
			savefalse = true;
		}
		Assert.assertTrue(savefalse);
	}

	@Test(description = "AT-70776:TA_01_083:verify agregated Transaction attachment read only  ", priority = 131, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnAttachmentReadonly_Investor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		String acttachmentHeader = attchment.popUpHeader().getText();

		SeleniumUtil.click(attchment.close());
		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70778,AT-70779:TA_01_084:verify agregated Transaction close should displayed  ", priority = 132, dependsOnMethods = "verifyAggTxnAmtFieldReadonly_Investor")
	public void verifyAggTxnCloseButton_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(8).getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:TA_01_085:verify event Transaction amount read only  ", priority = 161, dependsOnMethods = "preConditionVerifyReadonlyFeatureEventTransaction_InvestorView")
	public void verifyEnevtTxnAmtFieldReadonly_Investor() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		// layout.serachTransaction(add_Manual.targateDate1(series.TransactionDate(0)),
		// add_Manual.targateDate1(series.TransactionDate(0)));
		layout.serachTransaction(add_Manual.targateDate1(28), add_Manual.targateDate1(39));
		SeleniumUtil.waitForPageToLoad();
		if (readOnly.getAllAmount1().get(0).getText() == null) {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		}

		int transactionsize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionsize; i++) {

			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("AdvisorEventTransactionamount"))) {
				advisorEventRow = i;
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionamount"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_086:verify event Transaction drescription read only  ", priority = 162, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEnevtTxnDescpFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionDescription"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_087:verify event Transaction account read only  ", priority = 163, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEnevtTxnAcctFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionAccount"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_088verify event Transaction date read only  ", priority = 164, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEnevtTxnDateFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(),
				add_Manual.targateDate1(series.TransactionDate(0)), "Date field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_089:verify event Transaction account read only  ", priority = 165, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEventTxnFreqFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionFrequency"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_090verify event Transaction category read only  ", priority = 166, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEnevtTxnCatgFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionEventReadOnyCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_091:verify event Transaction tag read only  ", priority = 167, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEnevtTxnTagFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionTag"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70771,AT-70773:TA_01_092:verify event Transaction note read only  ", priority = 168, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEnevtTxnNoteFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorEventTransactionNote"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70778,AT-70779:TA_01_093:verify event Transaction details close button ", priority = 169, dependsOnMethods = "verifyEnevtTxnAmtFieldReadonly_Investor")
	public void verifyEventTxnCloseButton_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsedSeries().get(advisorEventRow)
				.getAttribute("aria-expanded").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertEquals(transactionColapsed, "false", " Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:TA_01_094:verify manual Transaction amount read only  ", priority = 170)
	public void verifyMTAmtFieldReadonly_Investor() throws Exception {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < add_Manual.getAllPostedAmount1().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("AdvisorManualTransactionamountsearch"))) {
				advisorManulaTraRow = i;
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}

		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionamountsearch"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_095:verify manual Transaction drescription read only  ", priority = 171, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTxnDescnFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionDescription"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_096:verify manual Transaction account read only  ", priority = 172, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTxnAccFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionAccount"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_097:verify manual Transaction account read only  ", priority = 173, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTFrequencyFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionFrequency"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_098:verify manual Transaction date read only  ", priority = 174, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTDateFieldReadonly_Investor() throws Exception {

		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(),
				add_Manual.targateDate1(0), "Date field value Should be readonly");

	}

	@Test(description = "AT-70767:TA_01_099:verify manual Transaction category read only  ", priority = 175, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTCatgFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("investorManualTransactionCategory"),
				"category field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_100:verify manual Transaction tag read only  ", priority = 176, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyManualTxnTagFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionTag"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70771,AT-70773:TA_01_101:verify manual Transaction note read only  ", priority = 177, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTxnNoteFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorManualTransactionNote"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70778,AT-70779:TA_01_102:verify manual Transaction close button   ", priority = 178, dependsOnMethods = "verifyMTAmtFieldReadonly_Investor")
	public void verifyMTCloseButton_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(advisorManulaTraRow)
				.getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70767:TA_01_103:verify Investment Transaction amount read only  ", priority = 179)
	public void verifyInvestTxnAmtFieldReadonly_Investor() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		layout.serachTransaction(add_Manual.targateDate1(2), add_Manual.targateDate1(2));
		SeleniumUtil.waitForPageToLoad();
		int transactionsize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionsize; i++) {

			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionAmount1"))) {
				advisorManulaInvstTraRow = i;
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}

		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionAmount1"),
				"Amount field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_104verify Investment Transaction drescription read only  ", priority = 180, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestmentTransactionDescriptionFieldReadonly_InvestorView() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionDecsription"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_105:verify Investment Transaction account read only  ", priority = 181, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnAccFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionAccount"),
				"Account field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_106:verify Investment Transaction date read only  ", priority = 182, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnDateFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(),
				add_Manual.targateDate1(2), "Date field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_107:verify Investment Transaction category read only  ", priority = 183, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnSymbolFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlySymbolLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvetsmentTransactionSymbolLabel"),
				"symbol field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlySymbolValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionSymbol"),
				"symbol field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_108:verify Investment Transaction CUS read only  ", priority = 184, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnCUSFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCUSLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvetsmentTransactionCUSIPLabel"),
				"CUSIP field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCUSValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionCUS"),
				"CUSIP field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_109:verify Investment Transaction tyep read only  ", priority = 185, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnInvestTypeFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyInvestmentTypeLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionTypeLabel"),
				"Invetsment Type field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyInvestmentTypeValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionTypeValue"),
				"Invetsment Type field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_110:verify Investment Transaction price read only  ", priority = 186, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnPriceFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyPriceLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionHoldingPrice"),
				"Quantity field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyPriceValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionPrice"),
				"Quantity field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_111:verify Investment Transaction quantiy read only  ", priority = 187, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnQuantityFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyQuantityLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionQuantityLabel"),
				"Quantity field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyQuantityValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionQuantity"),
				"Quantity field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_112:verify Investment Transaction haloding read only  ", priority = 188, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnHoldingFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyHoaldingLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionHoldingLabel"),
				"holding field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyHoaldingValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionHoldingValue"),
				"holding field value Should be readonly");
	}

	@Test(description = "AT-70767:TA_01_113:verify Investment Transaction lot read only  ", priority = 189, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnLotFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyLotLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionLotLabel"),
				"lot field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyLotValue().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionLotValue"),
				"lot field value Should be readonly");
	}

	@Test(description = "AT-70770,TA_01_114:verify Investment Transaction tag read only  ", priority = 190, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnTagFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionTag"),
				"tag field value Should be readonly");
	}

	@Test(description = "AT-70771,AT-70773:TA_01_115:verify Investment Transaction note read only  ", priority = 191, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnNoteFieldReadonly_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorInvestmentTransactionNote"),
				"Note field value Should be readonly");
	}

	@Test(description = "AT-70778,AT-70779:TA_01_116:verify Investment Transaction close read only  ", priority = 192, dependsOnMethods = "verifyInvestTxnAmtFieldReadonly_Investor")
	public void verifyInvestTxnCloseButton_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsedSeries().get(advisorManulaInvstTraRow)
				.getAttribute("aria-expanded").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertEquals(transactionColapsed, "false", " Transaction details should close when click on close");
	}

	@Test(description = "AT-70783:TA_01_117:expanse amount read only", priority = 193)
	public void verifyTxnAmtdReadonlyInEA_Investor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		expanseIncome.clickThisMonthTransaction();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(15000);

		SeleniumUtil.click(expanseIncome.excatList().get(0));
		SeleniumUtil.waitForPageToLoad();

		int category = expanseIncome.excatList().size();
		for (int i = 0; i < category; i++) {
			if (expanseIncome.excatList().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Transactionexpcategory").trim())) {
				SeleniumUtil.click(expanseIncome.excatList().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
				break;
			}

		}
		SeleniumUtil.click(expanseIncome.tranamount1().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIEAmount"), "Amount field value Should be readonly");

	}

	@Test(description = "AT-70783:TA_01_118:verify expanse Transaction description read only  ", priority = 194, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTransactionDescriptionFieldReadonlyInEA_InvestorView() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIEDescription"), "description field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_119:verify expanse Transaction statment read only  ", priority = 195, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTStamtDespFieldReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIEstmDescription"), "Statement  field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_121:verify expanse Transaction account read only  ", priority = 196, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnAccFieldReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIEAccount"), "Account field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_122:verify expanse Transaction date read only  ", priority = 197, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnDateReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_123:verify expanse Transaction marchant read only  ", priority = 198, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnMarchantReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsMarchantLable().getText(),
				PropsUtil.getDataPropertyValue("InvestorEATransactionMarchantLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsMarchantValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIEMarchantValue"), "category field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_124:verify expanse Transaction category read only  ", priority = 199, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnCatgReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIECategory"), "category field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_125:verify expanse Transaction tag read only  ", priority = 200, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnTagReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag3"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_126:verify expanse Transaction note read only  ", priority = 201, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnNoteReadonlyInEA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote3"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_127:verify expanse Transaction attachment read only  ", priority = 202, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnAttachmentReadonlyInEA_Investor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		String acttachmentHeader = attchment.popUpHeader().getText();

		SeleniumUtil.click(attchment.close());
		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70783:TA_01_128:verify expanse Transaction attachment read only  ", priority = 203, dependsOnMethods = "verifyTxnAmtdReadonlyInEA_Investor")
	public void verifyTxnCloseButtonInEA_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(1).getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70783:TA_01_129:income amount read only", priority = 204)

	public void verifyTxnAmtdReadonlyInIA_Investor() throws Exception {

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(expanseIncome.exp());
		SeleniumUtil.scrollElementIntoView(d, expanseIncome.incomeAys(), true);
		SeleniumUtil.click(expanseIncome.incomeAys());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.excatAmount().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(expanseIncome.excatList().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(expanseIncome.tranamount1().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIAAmount"), "Amount field value Should be readonly");

	}

	@Test(description = "AT-70783:TA_01_130:verify income Transaction description read only  ", priority = 205, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnDespFieldReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionDescription1"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_131:verify income Transaction statement read only  ", priority = 206, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnStamtDespFieldReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIAStmDescription"), "Statement  field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_132:verify income Transaction account read only  ", priority = 207, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnAccFieldReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIAAccount"), "Account field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_133:verify income Transaction date read only  ", priority = 208, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnDateReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_134:verify income Transaction category read only  ", priority = 209, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnCatgReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorIACategory"), "category field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_135:verify income Transaction tag read only  ", priority = 210, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnTagReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag1"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_136:verify income Transaction note read only  ", priority = 211, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnNoteReadonlyInIA_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote1"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70783:TA_01_137:verify income Transaction attachment read only  ", priority = 212, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnAttachmentReadonlyInIA_Investor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		String acttachmentHeader = attchment.popUpHeader().getText();

		SeleniumUtil.click(attchment.close());

		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70783:TA_01_138:verify income Transaction close read only  ", priority = 213, dependsOnMethods = "verifyTxnAmtdReadonlyInIA_Investor")
	public void verifyTxnCloseButtonInIA_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad();
		String transactionColapsed = readOnly.transactionDetailsColapsed().get(1).getAttribute("class").trim();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertFalse(transactionColapsed.contains("active"),
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70782:TA_01_139:Budget transaction abount read only", priority = 214)
	public void verifyTxnAmtdReadonlyInBudget_Investor() throws Exception {

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(7000);
		budget.creatBudget();
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(budget.ATMwithdrow().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget.transactionrow().get(1));
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
				"Amount field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorBudgetAmount"), "Amount field value Should be readonly");

	}

	@Test(description = "AT-70782:TA_01_140:verify Budget Transaction description read only  ", priority = 215, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnDescpFieldReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
				"description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionDescription2"),
				"description field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_141:verify Budget Transaction statement read only  ", priority = 216, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnStamentDespFieldReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyStDescriptionLabel"),
				"Statement description field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorBudgetStmDescription"),
				"Statement  field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_142:verify Budget Transaction account read only  ", priority = 217, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnAccFieldReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
				"Account field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorBudgetAccount"), "Account field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_143:verify Budget Transaction date read only  ", priority = 218, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnDateReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyDateLabel"),
				"Date field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyDateValue().getText(), add_Manual.targateDate1(0),
				"Date field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_144:verify Budget Transaction category read only  ", priority = 219, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnCatgReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
				"category field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorBudgetCategory"), "category field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_145:verify Budget Transaction tag read only  ", priority = 220, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnTagReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"), "Tag field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionTag2"), "tag field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_146:verify Budget Transaction note read only  ", priority = 221, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnNoteReadonlyInBudget_Investor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
				"Note field label Should be readonly");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AdvisorAggTransactionNote2"), "Note field value Should be readonly");
	}

	@Test(description = "AT-70782:TA_01_147:verify Budget Transaction attachment read only  ", priority = 222, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnAttachmentReadonlyInBudget_Investor() throws Exception {

		String attachmentLabel = readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
		SeleniumUtil.waitForPageToLoad();
		String acttachmentHeader = attchment.newpopUpHeader_TA().getText();

		SeleniumUtil.click(attchment.close());

		Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),
				"attachment ment popupHeader");
		Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),
				"attachment label");

	}

	@Test(description = "AT-70782:TA_01_148:verify agregated Transaction attachment read only  ", priority = 224, dependsOnMethods = "verifyTxnAmtdReadonlyInBudget_Investor")
	public void verifyTxnCloseButtonInBudget_Investor() throws Exception {
		String closeButton = readOnly.transactionDetailsCloseButton().getText().trim();
		SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
		SeleniumUtil.waitForPageToLoad(1000);

		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
				" Close Button text");
		Assert.assertEquals(readOnly.transactionBudgetHeader().size(), 0,
				" Transaction details should close when click on close");
	}

	@Test(description = "AT-70776:verify pre condition for more than one tag ", priority = 225)
	public void preconditionTransactionMorethanOneTagReadonly_Advisor() throws Exception {
		SAMLlogin.login(d, InvesterName1, null, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		try {
			accountDropDown.clickOnShowMoreButton();
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad(7000);
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(1000);
		readOnly.createTag(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag2"));
		readOnly.createTag1(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag3"));
		SeleniumUtil.click(readOnly.transactionDetailsMoreOption());
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath1");
		readOnly.attachFile(a);
		SeleniumUtil.waitForPageToLoad();
		String projectPath1 = System.getProperty("user.dir");
		String a1 = projectPath1 + PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath2");
		readOnly.attachFile(a1);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad(6000);
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue2"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(4000);
		readOnly.createTag(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag5"));
		SeleniumUtil.waitForPageToLoad(4000);
		readOnly.createTag1(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag6"));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad(6000);
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("InvestorEATransactionAmount"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(1000);
		readOnly.createTag(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag8"));
		SeleniumUtil.waitForPageToLoad(4000);
		readOnly.createTag1(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag9"));
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad(6000);

	}

	@Test(description = "AT-70776:TA_01_149:verify more than one attachment in agg Transaction   ", priority = 226, dependsOnMethods = "preconditionTransactionMorethanOneTagReadonly_Advisor")
	public void verifyAggTxnMorethanOneAttachmentReadonly_Advisor() throws Exception {
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		try {
			accountDropDown.clickOnShowMoreButton();
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad();
		boolean expectedAttachment = false;
		if (readOnly.transactionDetailsReadonlyAttachmentImage().size() == 3) {
			expectedAttachment = true;

		}
		Assert.assertTrue(expectedAttachment);
	}

	@Test(description = "AT-70775:TA_01_150:verify more than one tag in agg transaction  ", priority = 227, dependsOnMethods = "verifyAggTxnMorethanOneAttachmentReadonly_Advisor")
	public void verifyAggTxnMorethanOneTagReadonly_Advisor() throws Exception {
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorMorethanOneTAg"), "tag should separated with comma");
	}

	@Test(description = "AT-70775:TA_01_151:verify more than one tag on Expanse  ", priority = 228)
	public void verifyTxnMorethanOneTagReadonlyInEA_Advisor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		// expanseIncome.clickThisMonthTransaction();
		try {
			SeleniumUtil.click(expanseIncome.goToAnalysis());
		} catch (Exception e) {

		}

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(15000);
		int category = expanseIncome.excatList().size();
		for (int i = 0; i < category; i++) {
			if (expanseIncome.excatList().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Transactionexpcategory").trim())) {
				SeleniumUtil.click(expanseIncome.excatList().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
			}

		}
		SeleniumUtil.click(expanseIncome.tranamount1().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorMorethanOneTAg1"), "tag should separated with comma");

	}

	@Test(description = "AT-70775:TA_01_152:verify more than one tag in income  ", priority = 229)
	public void verifyTxnMorethanOneTagReadonlyInIA_Advisor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.scrollElementIntoView(d, expanseIncome.incomeAys(), true);
		SeleniumUtil.click(expanseIncome.incomeAys());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.excatAmount().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(expanseIncome.tranamount1().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorMorethanOneTAg"), "tag should separated with comma");
	}

	@Test(description = "AT-70775:verify more than one tag ", priority = 230)
	public void PreconditionForTxnMorethanOneTagReadonly_Investor() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		try {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitForPageToLoad(7000);
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad(7000);
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("AdvisorAggTransactionAmount"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(1000);
		readOnly.createTag(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag2"));
		readOnly.createTag1(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag3"));
		SeleniumUtil.click(readOnly.transactionDetailsMoreOption());
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath1");
		readOnly.attachFile(a);
		SeleniumUtil.waitForPageToLoad();
		String projectPath1 = System.getProperty("user.dir");
		String a1 = projectPath1 + PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath2");
		readOnly.attachFile(a1);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad(6000);
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("AdvisorBudgetAmount"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(1000);
		readOnly.createTag(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag4"));
		SeleniumUtil.waitForPageToLoad(4000);
		readOnly.createTag1(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag5"));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad(6000);
		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("AdvisorIEAmount"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(1000);
		readOnly.createTag(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag7"));
		SeleniumUtil.waitForPageToLoad(4000);
		readOnly.createTag1(PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag8"));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad(6000);
	}

	@Test(description = "AT-70775:TA_01_154:verify more than one attament", priority = 232, dependsOnMethods = "PreconditionForTxnMorethanOneTagReadonly_Investor")
	public void verifyAggTxnMorethanOneAttachmentReadonly_Investor() throws Exception {
		SAMLlogin.login(d, InvesterName1, null, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		try {
			accountDropDown.clickOnShowMoreButton();
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad();

		for (int i = 0; i < readOnly.getAllPostedAmount1().size(); i++) {
			if (readOnly.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("AdvisorAggTransactionAmount"))) {
				SeleniumUtil.click(readOnly.getAllPostedAmount1().get(i));
				SeleniumUtil.waitForPageToLoad();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad();
		boolean expectedAttachment = false;
		if (readOnly.transactionDetailsReadonlyAttachmentImage().size() == 3) {
			expectedAttachment = true;

		}
		Assert.assertTrue(expectedAttachment);
	}

	@Test(description = "AT-70775:TA_01_155:verify more than one tag ", priority = 233)
	public void verifyAggTxnMorethanOneTagReadonly_Investor() throws Exception {

		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorMorethanOneTAg4"), "tag should separated with comma");

	}

	@Test(description = "vAT-70775:TA_01_156:verify more than one tag in expanse", priority = 234)
	public void verifyTxnMorethanOneTagReadonlyInEA_Investor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		try {
			expanseIncome.clickThisMonthTransaction();
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(15000);
		// SeleniumUtil.click(expanseIncome.excatAmount().get(1));
		SeleniumUtil.waitForPageToLoad(10000);
		int category = expanseIncome.excatList().size();
		for (int i = 0; i < category; i++) {
			if (expanseIncome.excatList().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Transactionexpcategory").trim())) {
				SeleniumUtil.click(expanseIncome.excatList().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
			}

		}
		SeleniumUtil.click(expanseIncome.tranamount1().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorMorethanOneTAg1"), "tag should separated with comma");

	}

	@Test(description = "AT-70775:TA_01_157:verify more than one tag in income ", priority = 235)
	public void verifyTxnMorethanOneTagReadonlyInIA_Investor() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(5000);
		// expanseIncome.clickThisMonthTransaction();
		SeleniumUtil.scrollElementIntoView(d, expanseIncome.incomeAys(), true);
		SeleniumUtil.click(expanseIncome.incomeAys());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(expanseIncome.excatAmount().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(expanseIncome.tranamount1().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorMorethanOneTAg3"), "tag should separated with comma");

	}

	@Test(description = "AT-70775:TA_01_158:verify more than one tag in budget", priority = 236)
	public void verifyTxnMoreThanOneTagReadonlyInBudget_Investor() throws Exception {

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(budget.ATMwithdrow().get(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget.transactionrow().get(1));
		Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText(),
				PropsUtil.getDataPropertyValue("AdvisorMorethanOneTAg3"), "tag should separated with comma");

	}

	@Test(description = "AT-70784:TA_01_159:verify vatgeory updated", priority = 237)

	public void verifyUpdatedSubCategory() throws Exception {
		// SAMLlogin.login(d, InvesterName1,null);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		rule.manageCat();
		SeleniumUtil.waitForPageToLoad(3000);
		rule.editCat(PropsUtil.getDataPropertyValue("InvestorRuleCatChange"),
				PropsUtil.getDataPropertyValue("InvestorRuleSubCatChange"));
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(readOnly.pendingStranctionList().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(readOnly.transactionDetailsCategoryLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(readOnly.transactionDetailsSubCategoryList(5, 2));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.waitForPageToLoad();
		SAMLlogin.login(d, advisorUsername, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		try {
			accountDropDown.clickOnShowMoreButton();
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(readOnly.pendingStranctionList().get(1));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorRuleSubCatChange"), "category field value Should be readonly");
	}

	@Test(description = "AT-70784:TA_01_160:verify category field updated", priority = 238, dependsOnMethods = "verifyUpdatedSubCategory")

	public void verifyUpdatedCategory() throws Exception {
		layout.serachTransaction(add_Manual.targateDate1(-7), add_Manual.targateDate1(-7));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(readOnly.pendingStranctionList().get(1));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),
				PropsUtil.getDataPropertyValue("InvestorRuleCatChange"), "category field value Should be readonly");
	}

	public void createInvestorAdvisor() throws Exception {
		logger.info("Get the unique reference id");
		addvisorRef1 = SAMLlogin.getRefrenceId();
		logger.info("Get the unique investor Name");
		InvesterName1 = SAMLlogin.getInvestorUserName();
		logger.info("create advisor with extra param segmentName=advisor&IID=Investorname ");
		advisorUsername = SAMLlogin.createAdvisor(d, InvesterName1, addvisorRef1, "10003600");

		SeleniumUtil.waitForPageToLoad();

		acccuntAdd.linkAccountFastLink();
		SeleniumUtil.waitForPageToLoad(2000);
		acccuntAdd.addAggregatedAccount("advisorinvestor3.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expectedInvestor = false;
		if (advisorUsername != null) {
			logger.info("create investor and prepop account to investor");
			SAMLlogin.createInvestor(d, advisorUsername, InvesterName1, "TransactionFilter1.site16441.1",
					"site16441.1");
			acccuntAdd.linkAccount();
			acccuntAdd.addAggregatedAccount("advisorinvestor3.site16441.1", "site16441.1");
			expectedInvestor = true;
		}
		Assert.assertTrue(expectedInvestor, "advisor and investor is not created successfully");
	}

	public void invester1SharedAccount() throws Exception {
		logger.info("login to investor");
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("click on advisor name in manage sharing");
		for (int i = 0; i < accountDropDown.AdvisorName().size(); i++) {
			if (accountDropDown.AdvisorName().get(i).getText().contains(addvisorRef1)) {
				SeleniumUtil.click(accountDropDown.AdvisorName().get(i));
				break;
			}
		}
		for (int i = 0; i < 3; i++) {
			SeleniumUtil.click(accountDropDown.accShareDropdown().get(i));
			SeleniumUtil.click(accountDropDown.accShareDropdownValue(i + 1).get(0));
		}
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(accountDropDown.AccountShareSave());
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("share the accounts to selected advisor");
		Assert.assertEquals(accountDropDown.accShareDropdown().get(0).getText(),
				PropsUtil.getDataPropertyValue("AccountSharedDropdownValueView"), "accounts is not shared to advisor");
	}

	public void advisor1SharedAccount() throws Exception {
		logger.info("share the accounts to investor");
		String samlResponse = LoginPage_SAML3.loginResponse(d, advisorUsername, InvesterName1);
		ysl.shareAdvisorAccount(samlResponse, advisorUsername, InvesterName1);
	}

	public void preConditionRDFeatureDepositTypeTra_AdvisorView() throws Exception {
		SAMLlogin.loginAsInvestor(d, InvesterName1, "10003700");
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.clickOnShowMoreButton();
		logger.info("add all details to first posted aggregated transaction");
		readOnly.editPostedTransaction(2, PropsUtil.getDataPropertyValue("TransactionAggReadonlyDescription1"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag1"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNote1"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue"));
	}

	public void preConditionRDFeatureWithdrwalTypeTra_AdvisorView() throws Exception {
		accountDropDown.clickOnShowMoreButton();
		logger.info("add all details to second posted aggregated transaction");
		readOnly.editPostedTransaction(0, PropsUtil.getDataPropertyValue("TransactionAggReadonlyDescription2"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag4"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNote1"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountValue2"));
	}

	public void preConditionRDEduTypeTra_AdvisorView() throws Exception {
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitForPageToLoad(8000);
		logger.info("add all details to 3rd posted aggregated transaction");
		readOnly.editPostedTransaction(1, PropsUtil.getDataPropertyValue("TransactionAggReadonlyDescription3"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyTag7"),
				PropsUtil.getDataPropertyValue("TransactionAggReadOnyNote1"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("InvestorEATransactionAmount"));
	}

	public void preConditionRDFeatureEventTran_AdvisorView() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("create event");
		accountDropDown.clickOnShowMoreButton();
		readOnly.createEvent(3, PropsUtil.getDataPropertyValue("TransactionEventReafOnlyTag1"),
				PropsUtil.getDataPropertyValue("TransactionEventReafOnlyNote"),
				PropsUtil.getDataPropertyValue("TransactionEventReadOnyAmount"));

	}

	public void preConditionVerifyRDFManualTran_AdvisorView() throws Exception {
		logger.info("create manual transaction");
		SeleniumUtil.click(add_Manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		add_Manual.createTransactionWithRecuralldetailsInvestorAndAdvisor(
				PropsUtil.getDataPropertyValue("investorManualTransactionamount"),
				PropsUtil.getDataPropertyValue("investorManualTransactionDescription"), 1, 1, 0, 7, 1, 1,
				PropsUtil.getDataPropertyValue("investorManualTransactionTag"),
				PropsUtil.getDataPropertyValue("investorManualTransactionNote"));
	}

	public void preConditionRDInvestTran_AdvisorView() throws Exception {
		logger.info("create investment transaction");
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(add_Manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		add_Manual.createInvestmentTransactionAdviosrInvestor(
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionAmount"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionDescriptiont"), 2, 1, 0, 2,
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionSymbol"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionCUS"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionPrice"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionQuantity"), 1, 1,
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionTag"),
				PropsUtil.getDataPropertyValue("TransactionInvestmentTransactionNote"));
	}

}
