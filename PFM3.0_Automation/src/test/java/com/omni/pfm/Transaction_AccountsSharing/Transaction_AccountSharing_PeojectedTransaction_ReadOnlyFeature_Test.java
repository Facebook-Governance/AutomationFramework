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
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class Transaction_AccountSharing_PeojectedTransaction_ReadOnlyFeature_Test extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(Transaction_AccountSharing_PeojectedTransaction_ReadOnlyFeature_Test.class);
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Transaction_Expanse_Income_Analysis_Loc expanseIncome;
	Transaction_Budget_Integration_Loc  budget;
	LoginPage login;
	AccountAddition acccuntAdd; 
	int investorSeries1Row=0; int investorSeries2Row=0; int investorInstanceRow=0;   int advisorSeries1Row=0; int advisorSeries2Row=0; int advisorInstanceRow=0; 
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
	Add_Manual_Transaction_Loc  add_Manual;
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
		
		
		
		 doInitialization("Transaction Login");
	        
	        TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
	        TestBase.test.appendChild(TestBase.tc);
	        login=new LoginPage();
	        acccuntAdd=new AccountAddition();
	        SAMLlogin=new LoginPage_SAML3();
	        accountDrapDown=new Transaction_AccountDropDown_Loc(d);
		    add_Manual=new Add_Manual_Transaction_Loc(d);
		    search=new Transaction_Search_Loc(d);
		    layout=new Transaction_Layout_Loc(d);
		    aggre_Tra=new Aggregated_Transaction_Details_Loc(d);
		    tag=new Transaction_Tag_Loc(d);
		    feature=new   DownloadTrans_Loc(d);
		    readOnly=new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		    expanseIncome=new Transaction_Expanse_Income_Analysis_Loc(d);
		    budget=new Transaction_Budget_Integration_Loc(d);
		    series=new Series_Recurence_Transaction_Loc(d);
		    attchment=new Transaction_Attachment_Loc(d);
		    rule=new Transaction_Categorization_Rules_Loc(d);
	}
	@Test(description = "AT-70780:precondition for verify transaction readonly fetaure in advisor view", priority = 1)
	public void preConditionROFeatureSeriesTtxn_Advisor() throws Exception
	{
	     SAMLlogin.login(d,Transaction_AccountSharing_ReadonlyFeature_Test.InvesterName1,null,"10003700");
		//SAMLlogin.login(d,"PFMINV1530018003426",null,"10003700");
		
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		if(series.getDateWeekEnd(0).equals("SUNDAY"))
		{
			layout.serachTransaction(add_Manual.targateDate1(5), add_Manual.targateDate1(5));
		}
		else if(series.getDateWeekEnd(0).equals("SATURDAY")){
			layout.serachTransaction(add_Manual.targateDate1(6), add_Manual.targateDate1(6));
		}
		else
		{
		layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
		}
		logger.info("add all details to series  transaction");
		SeleniumUtil.waitForPageToLoad();
		readOnly.editPeojectedSeriesTransaction(0, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyAmount1"), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyTag1"),
				PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyNote1"),PropsUtil.getDataPropertyValue("TransactionSeriesReadonlySearchAmount"));
	}
	
		@Test(description = "AT-70780:precondition for verify transaction readonly fetaure in advisor view", priority = 2)
	public void preConditionROFeatureInstanceTxn_Advisor() throws Exception
	{
			if(series.getDateWeekEnd(0).equals("SUNDAY"))
			{
				layout.serachTransaction(add_Manual.targateDate1(8), add_Manual.targateDate1(8));
			}
			else if(series.getDateWeekEnd(0).equals("SATURDAY")){
				layout.serachTransaction(add_Manual.targateDate1(9), add_Manual.targateDate1(9));
			}
			else
			{
			layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
			}			logger.info("add all details to projected Instance transaction");
		SeleniumUtil.waitForPageToLoad();
		readOnly.editPeojectedinstanceTransaction(0, PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyAmoun1"), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyTag1"),
				PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyNote1"), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyCheckNo"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),PropsUtil.getDataPropertyValue("TransactionInstanceReadonlySearchAmount"));
	}
		@Test(description = "AT-70767:verify series Transaction amount read only  ", priority = 3,dependsOnMethods="preConditionROFeatureSeriesTtxn_Advisor")
		public void verifySTxnAmountFieldReadonly_advisor() throws Exception
		{
			
			SAMLlogin.login(d,Transaction_AccountSharing_ReadonlyFeature_Test.advisorUsername,Transaction_AccountSharing_ReadonlyFeature_Test.InvesterName1,"10003700");
			SeleniumUtil.waitForPageToLoad();
			PageParser.forceNavigate("Transaction", d);
			SeleniumUtil.waitForPageToLoad();
			if(series.getDateWeekEnd(0).equals("SUNDAY"))
			{
				layout.serachTransaction(add_Manual.targateDate1(5), add_Manual.targateDate1(5));
			}
			else if(series.getDateWeekEnd(0).equals("SATURDAY")){
				layout.serachTransaction(add_Manual.targateDate1(6), add_Manual.targateDate1(6));
			}
			else
			{
			layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
			}			SeleniumUtil.waitForPageToLoad(6000);
			if(readOnly.getAllAmount1().get(0)==null)
			{
				SeleniumUtil.click(readOnly.ProjectedExapdIcon());
			}
			
			int transactionsize=readOnly.getAllAmount1().size();
			for(int i=0;i<transactionsize;i++)
			{
				if(readOnly.getAllAmount1().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyAmount2")))
				{
					SeleniumUtil.click(readOnly.getAllAmount1().get(i));
					SeleniumUtil.waitForPageToLoad();
					advisorSeries1Row=i;
					break;
				}
			}
			
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
					"Amount field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyAmount2"),
					"Amount field value Should be readonly");
		}
		@Test(description = "AT-70780:verify series Transaction drescription read only  ", priority = 4,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTRadioButton_advisor() throws Exception
		{
        boolean justRadioButton=false;
        boolean allRadioButton=false;
        if(readOnly.justRadioSelected_SRT().size()==0)
        {
        	justRadioButton=true;
        }
        if(readOnly.allradioButton_SRT().size()==0)
        {
        	allRadioButton=true;
        }
        Assert.assertTrue(justRadioButton);
        Assert.assertTrue(allRadioButton);
		}
		@Test(description = "AT-70767:verify series Transaction drescription read only  ", priority = 5,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTDespFieldReadonly_advisor() throws Exception
		{

			Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
					"description field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyDescriptionValue"),
					"description field value Should be readonly");
			
		}
		
		@Test(description = "AT-70767:verify series Transaction account read only  ", priority = 6,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTAccoFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
					"Account field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
					"Account field value Should be readonly");
		}
		
		@Test(description = "AT-70767:verify series Transaction frequency read only  ", priority = 7,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTFreqFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyLabel"),
					"Account field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyValue"),
					"Account field value Should be readonly");
		}
		@Test(description = "AT-70767:verify series Transaction date read only  ", priority = 8,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTDateFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
					"Date field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(), add_Manual.targateDate1(7),
					"Date field value Should be readonly");
		}
		@Test(description = "AT-70769::verify series Transaction category read only  ", priority = 9,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTCategoryFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
					"category field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryValue2"),
					"category field value Should be readonly");
		}
		@Test(description = "AT-70774:verify series Transaction tag read only  ", priority = 10,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTxnTagFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"),
					"Tag field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyTag1"),
					"tag field value Should be readonly");
		}
		
		@Test(description = "AT-70771,AT-70773:verify series Transaction note read only  ", priority = 11,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTNoteFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
					"Note field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyNote1"),
					"Note field value Should be readonly");
		}
		@Test(description = "AT-70778,AT-70779:verify series Transaction attachment read only  ", priority = 12,dependsOnMethods="verifySTxnAmountFieldReadonly_advisor")
		public void verifySTCloseButton_advisor() throws Exception
		{
			String closeButton=readOnly.transactionDetailsCloseButton().getText().trim();
			SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
			String transactionColapsed=readOnly.transactionDetailsColapsedSeries().get(advisorSeries1Row).getAttribute("aria-expanded").trim();
			Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
					" Close Button text");
			Assert.assertEquals(transactionColapsed, "false",
					" Transaction details should close when click on close");
		}
			@Test(description = "AT-70767:verify series Transaction amount read only  ", priority = 13,dependsOnMethods="preConditionROFeatureSeriesTtxn_Advisor")
		public void verifyS2TAmountFieldReadonly_advisor() throws Exception
		{		

				if(series.getDateWeekEnd(0).equals("SUNDAY"))
				{
					layout.serachTransaction(add_Manual.targateDate1(12), add_Manual.targateDate1(12));
				}
				else if(series.getDateWeekEnd(0).equals("SATURDAY")){
					layout.serachTransaction(add_Manual.targateDate1(13), add_Manual.targateDate1(13));
				}
				else
				{
				layout.serachTransaction(add_Manual.targateDate1(14), add_Manual.targateDate1(14));
				}			SeleniumUtil.waitForPageToLoad(6000);
			if(readOnly.getAllAmount1().get(0)==null)
			{
				SeleniumUtil.click(readOnly.ProjectedExapdIcon());
			}
			
			int transactionsize=readOnly.getAllAmount1().size();
			for(int i=0;i<transactionsize;i++)
			{
				if(readOnly.getAllAmount1().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyAmount2")))
				{
					SeleniumUtil.click(readOnly.getAllAmount1().get(i));
					SeleniumUtil.waitForPageToLoad();
					advisorSeries2Row=i;
					break;
				}
			}
			
			
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
					"Amount field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyAmount2"),
					"Amount field value Should be readonly");
		}
		
		@Test(description = "AT-70767:verify series Transaction drescription read only  ", priority = 14,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TDespFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
					"description field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyDescriptionValue"),
					"description field value Should be readonly");
			
		}

		@Test(description = "AT-70767:verify series Transaction account read only  ", priority = 15,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TAccoFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
					"Account field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
					"Account field value Should be readonly");
		}
		@Test(description = "AT-70767:verify series Transaction frequency read only  ", priority = 16,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TFreqFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyLabel"),
					"Account field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyValue"),
					"Account field value Should be readonly");
		}
		@Test(description = "AT-70767:verify series Transaction date read only  ", priority = 17,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TDateFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
					"Date field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(), add_Manual.targateDate1(14),
					"Date field value Should be readonly");
		}
		@Test(description = "AT-70769:verify series Transaction category read only  ", priority = 18,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TCategoryFieldReadonly_advisor() throws Exception
		{

			Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
					"category field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(),PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryValue2"),
					"category field value Should be readonly");
		}
		@Test(description = "AT-70774:verify series Transaction tag read only  ", priority = 19,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TTagFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"),
					"Tag field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyTag1"),
					"tag field value Should be readonly");
		}
		
		@Test(description = "AT-70771,AT-70773:verify series Transaction note read only  ", priority = 20,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TNoteFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
					"Note field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnyNote1"),
					"Note field value Should be readonly");
		}
		@Test(description = "AT-70776:verify series Transaction attachment read only  ", priority = 21,dependsOnMethods="verifyS2TAmountFieldReadonly_advisor")
		public void verifyS2TCloseButton_advisor() throws Exception
		{
			String closeButton=readOnly.transactionDetailsCloseButton().getText().trim();
			SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
			String transactionColapsed=readOnly.transactionDetailsColapsedSeries().get(advisorSeries2Row).getAttribute("aria-expanded").trim();
			Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
					" Close Button text");
			Assert.assertEquals(transactionColapsed, "false",
					" Transaction details should close when click on close");
		}
		@Test(description = "AT-70767:verify instance Transaction amount read only  ", priority = 22,dependsOnMethods="preConditionROFeatureInstanceTxn_Advisor")
		public void verifyProjITAmountFieldReadonly_advisor() throws Exception
		{
			
			if(series.getDateWeekEnd(0).equals("SUNDAY"))
			{
				layout.serachTransaction(add_Manual.targateDate1(8), add_Manual.targateDate1(8));
			}
			else if(series.getDateWeekEnd(0).equals("SATURDAY")){
				layout.serachTransaction(add_Manual.targateDate1(9), add_Manual.targateDate1(9));
			}
			else
			{
			layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
			}			SeleniumUtil.waitForPageToLoad(6000);
			if(readOnly.getAllAmount1().get(0)==null)
			{
				SeleniumUtil.click(readOnly.ProjectedExapdIcon());
			}
			
			int transactionsize=readOnly.getAllAmount1().size();
			for(int i=0;i<transactionsize;i++)
			{
				
				if(readOnly.getAllAmount1().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyAmoun2")))
				{
					SeleniumUtil.click(readOnly.getAllAmount1().get(i));
					SeleniumUtil.waitForPageToLoad();
					advisorInstanceRow=i;
					break;
				}
			}
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
					"Amount field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyAmoun2"),
					"Amount field value Should be readonly");
		}
		
		@Test(description = "AT-70767:verify Instance Transaction drescription read only  ", priority = 23,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyProjITDescriptionFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
					"description field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyDescription1"),
					"description field value Should be readonly");
		}

		@Test(description = "AT-70767:verify Instance Transaction account read only  ", priority = 24,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyProjITAccountFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
					"Account field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountValue"),
					"Account field value Should be readonly");
		}
		@Test(description = "AT-70767:verify Instance Transaction date read only  ", priority = 25,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyProjITDateFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
					"Date field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(), add_Manual.targateDate1(7),
					"Date field value Should be readonly");
		}
		@Test(description = "AT-70769:verify Instance Transaction category read only  ", priority = 26,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyProjITCategoryFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
					"category field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyCategory"),
					"category field value Should be readonly");
		}
		@Test(description = "AT-70774:verify Instance Transaction tag read only  ", priority = 27,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyProjITTagFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"),
					"Tag field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyTag1"),
					"tag field value Should be readonly");
		}
		
		@Test(description = "AT-70771,AT-70773:Instance agregated Transaction note read only  ", priority = 28,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyProjITNoteFieldReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
					"Note field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnyNote1"),
					"Note field value Should be readonly");
		}
		@Test(description = "AT-70776:verify Instance Transaction attachment read only  ", priority = 29,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyITAttachmentReadonly_advisor() throws Exception
		{
			String attachmentLabel=readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
			SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
			String acttachmentHeader=attchment.popUpHeader().getText();
			
			SeleniumUtil.click(attchment.close());
			Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),"attachment ment popupHeader");
			Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),"attachment label");
		}
		@Test(description = "AT-70778,AT-70779:verify Instance Transaction check read only  ", priority = 30,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyITCheckReadonly_advisor() throws Exception
		{
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCheckLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyCheckFieldLabel"),
					"check field label Should be readonly");
			Assert.assertEquals(readOnly.transactionDetailsReadonlyCheckValue().getText().trim(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyCheckNo"),
					"Note field value Should be readonly");
			
			
			
		}
		@Test(description = "T-70767:verify Instance Transaction close read only  ", priority = 31,dependsOnMethods="verifyProjITAmountFieldReadonly_advisor")
		public void verifyITCloseButton_aadvisor() throws Exception
		{
			String closeButton=readOnly.transactionDetailsCloseButton().getText().trim();
			SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
			String transactionColapsed=readOnly.transactionDetailsColapsedSeries().get(advisorInstanceRow).getAttribute("aria-expanded").trim();
			Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
					" Close Button text");
			Assert.assertEquals(transactionColapsed, "false",
					" Transaction details should close when click on close");
		}
		@Test(description = "T-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 32)
		public void preConditionROFeatureST_Investor() throws Exception
		{
			
			PageParser.forceNavigate("Transaction", d);
			SeleniumUtil.waitForPageToLoad();
			if(series.getDateWeekEnd(0).equals("SUNDAY"))
			{
				layout.serachTransaction(add_Manual.targateDate1(5), add_Manual.targateDate1(5));
			}
			else if(series.getDateWeekEnd(0).equals("SATURDAY")){
				layout.serachTransaction(add_Manual.targateDate1(6), add_Manual.targateDate1(6));
			}
			else
			{
			layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
			}			logger.info("add all details to series  transaction");
			SeleniumUtil.waitForPageToLoad();
			readOnly.editPeojectedSeriesTransaction(0, PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionAmount"), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionTag1"),
					PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionNote1"),PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionSearchAmount"));
		}
		
			@Test(description = "T-70767:precondition for verify transaction readonly fetaure in advisor view", priority = 33)
		public void preConditionVerifyReadonlyFeatureInstanceTransaction_InvestorView() throws Exception
		{
				
				
				if(series.getDateWeekEnd(0).equals("SUNDAY"))
				{
					layout.serachTransaction(add_Manual.targateDate1(8), add_Manual.targateDate1(8));
				}
				else if(series.getDateWeekEnd(0).equals("SATURDAY")){
					layout.serachTransaction(add_Manual.targateDate1(9), add_Manual.targateDate1(9));
				}
				else
				{
				layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
				}				logger.info("add all details to projected Instance transaction");
			SeleniumUtil.waitForPageToLoad();
			readOnly.editPeojectedinstanceTransaction(0, PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionAmount"), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionTag1"),
					PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionNote1"), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactioncheck"),
					PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionSearchAmount"));
		}
			
			@Test(description = "T-70767:verify Series Transaction amount read only  ", priority = 34,dependsOnMethods="preConditionROFeatureST_Investor")
			public void verifySTAmountFieldReadonly_Investor() throws Exception
			{
				SAMLlogin.login(d,Transaction_AccountSharing_ReadonlyFeature_Test.InvesterName1,null,"10003700");
				PageParser.forceNavigate("Transaction", d);
				SeleniumUtil.waitForPageToLoad(5000);
				if(series.getDateWeekEnd(0).equals("SUNDAY"))
				{
					layout.serachTransaction(add_Manual.targateDate1(5), add_Manual.targateDate1(5));
				}
				else if(series.getDateWeekEnd(0).equals("SATURDAY")){
					layout.serachTransaction(add_Manual.targateDate1(6), add_Manual.targateDate1(6));
				}
				else
				{
				layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
				}					SeleniumUtil.waitForPageToLoad(5000);
				if(readOnly.getAllAmount1().get(0)==null)
				{
					SeleniumUtil.click(readOnly.ProjectedExapdIcon());
				}
				
				int transactionsize=readOnly.getAllAmount1().size();
				for(int i=0;i<transactionsize;i++)
				{
					if(readOnly.getAllAmount1().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionSearchAmount1")))
					{
						SeleniumUtil.click(readOnly.getAllAmount1().get(i));
						SeleniumUtil.waitForPageToLoad();
						investorSeries1Row=i;
						break;
					}
				}
				SeleniumUtil.waitForPageToLoad(2000);
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
						"Amount field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionSearchAmount1"),
						"Amount field value Should be readonly");
			}
			@Test(description = "AT-70780:verify Series Transaction drescription read only  ", priority = 35,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionRadioButton_InvestorView() throws Exception
			{
	        boolean justRadioButton=false;
	        boolean allRadioButton=false;
	        if(readOnly.justRadioSelected_SRT().size()==0)
	        {
	        	justRadioButton=true;
	        }
	        if(readOnly.allradioButton_SRT().size()==0)
	        {
	        	allRadioButton=true;
	        }
	        Assert.assertTrue(justRadioButton);
	        Assert.assertTrue(allRadioButton);
			}
			@Test(description = "T-70767:verify Series Transaction drescription read only  ", priority = 36,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionDescriptionFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
						"description field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionDescription"),
						"description field value Should be readonly");
			}
			
			@Test(description = "T-70767:verify Series Transaction account read only  ", priority = 37,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionAccountFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
						"Account field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(), PropsUtil.getDataPropertyValue("AdvisorAggTransactionAccountName"),
						"Account field value Should be readonly");
			}
			@Test(description = "T-70767:verify Series Transaction category read only  ", priority = 38,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionFrequencyFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyLabel"),
						"Account field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyValue"),
						"Account field value Should be readonly");
			}
			@Test(description = "T-70767:verify Series Transaction date read only  ", priority = 39,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionDateFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
						"Date field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(), add_Manual.targateDate1(7),
						"Date field value Should be readonly");
			}
			@Test(description = "AT-70769:verify Series Transaction category read only  ", priority = 40,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionCategoryFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
						"category field label Should be readonly");
				Assert.assertTrue(readOnly.transactionDetailsReadonlyCategoryValue().getText().contains(PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryValue")),
						"category field value Should be readonly");
			}
			@Test(description = "AT-70774:verify Series Transaction tag read only  ", priority = 41,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionTagFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"),
						"Tag field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionTag1"),
						"tag field value Should be readonly");
			}
			
			@Test(description = "AT-70771,AT-70773:verify Series Transaction note read only  ", priority = 42,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionNoteFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
						"Note field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionNote1"),
						"Note field value Should be readonly");
			}
			@Test(description = "AT-70778,AT-70779:verify Series Transaction attachment read only  ", priority = 43,dependsOnMethods="verifySTAmountFieldReadonly_Investor")
			public void verifySeriesTransactionCloseButton_InvestorView() throws Exception
			{
				String closeButton=readOnly.transactionDetailsCloseButton().getText().trim();
				SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
				String transactionColapsed=readOnly.transactionDetailsColapsedSeries().get(investorSeries1Row).getAttribute("aria-expanded").trim();
				Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
						" Close Button text");
				Assert.assertEquals(transactionColapsed, "false",
						" Transaction details should close when click on close");
			}
			@Test(description = "AT-70767:verify Series Transaction amount read only  ", priority = 44)
			public void verifyS2TAmountFieldReadonly_Investor() throws Exception
			{
				if(series.getDateWeekEnd(0).equals("SUNDAY"))
				{
					layout.serachTransaction(add_Manual.targateDate1(12), add_Manual.targateDate1(12));
				}
				else if(series.getDateWeekEnd(0).equals("SATURDAY")){
					layout.serachTransaction(add_Manual.targateDate1(13), add_Manual.targateDate1(13));
				}
				else
				{
				layout.serachTransaction(add_Manual.targateDate1(14), add_Manual.targateDate1(14));
				}				SeleniumUtil.waitForPageToLoad(5000);
				if(readOnly.getAllAmount1().get(0)==null)
				{
					SeleniumUtil.click(readOnly.ProjectedExapdIcon());
				}
				
				int transactionsize=readOnly.getAllAmount1().size();
				for(int i=0;i<transactionsize;i++)
				{
					if(readOnly.getAllAmount1().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionSearchAmount1")))
					{
						SeleniumUtil.click(readOnly.getAllAmount1().get(i));
						SeleniumUtil.waitForPageToLoad();
						investorSeries2Row=i;
						break;
					}
				}
				SeleniumUtil.waitForPageToLoad(2000);
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
						"Amount field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionSearchAmount1"),
						"Amount field value Should be readonly");
			}
			
			@Test(description = "AT-70767:verify Series Transaction drescription read only  ", priority = 45,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionDescriptionFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
						"description field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionDescription"),
						"description field value Should be readonly");
			}
			
			@Test(description = "AT-70767:verify Series Transaction account read only  ", priority = 46,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionAccountFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel"),
						"Account field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(), PropsUtil.getDataPropertyValue("AdvisorAggTransactionAccountName"),
						"Account field value Should be readonly");
			}
			@Test(description = "AT-70767:verify Series Transaction frequency read only  ", priority = 47,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionFrequencyFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyLabel"),
						"Account field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyFrequencyValue().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyFrequencyValue"),
						"Account field value Should be readonly");
			}
			@Test(description = ":AT-70767verify Series Transaction date read only  ", priority = 48,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionDateFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
						"Date field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(), add_Manual.targateDate1(14),
						"Date field value Should be readonly");
			}
			@Test(description = "AT-70769:verify Series Transaction category read only  ", priority = 49,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionCategoryFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
						"category field label Should be readonly");
				Assert.assertTrue(readOnly.transactionDetailsReadonlyCategoryValue().getText().contains(PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryValue")),
						"category field value Should be readonly");
			}
			@Test(description = "AT-70774:verify Series Transaction tag read only  ", priority = 50,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionTagFieldReadonly_InvestorView() throws Exception
			{

				Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"),
						"Tag field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionTag1"),
						"tag field value Should be readonly");
			}
			
			@Test(description = "AT-70771,AT-70773:verify Series Transaction note read only  ", priority = 51,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionNoteFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
						"Note field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorSeriesTransactionNote1"),
						"Note field value Should be readonly");
			}
			@Test(description = "AT-70778,AT-70779:verify Series Transaction attachment read only  ", priority = 52,dependsOnMethods="verifyS2TAmountFieldReadonly_Investor")
			public void verifySeries2TransactionCloseButton_InvestorView() throws Exception
			{
				String closeButton=readOnly.transactionDetailsCloseButton().getText().trim();
				SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
				String transactionColapsed=readOnly.transactionDetailsColapsedSeries().get(investorSeries2Row).getAttribute("aria-expanded").trim();
				Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
						" Close Button text");
				Assert.assertEquals(transactionColapsed, "false",
						" Transaction details should close when click on close");
			}
				@Test(description = "AT-70767:verify Instance Transaction amount read only  ", priority = 53)
			public void verifyProjITAmountFieldReadonly_Investor() throws Exception
			{
					
			     
				
					if(series.getDateWeekEnd(0).equals("SUNDAY"))
					{
						layout.serachTransaction(add_Manual.targateDate1(8), add_Manual.targateDate1(8));
					}
					else if(series.getDateWeekEnd(0).equals("SATURDAY")){
						layout.serachTransaction(add_Manual.targateDate1(9), add_Manual.targateDate1(9));
					}
					else
					{
					layout.serachTransaction(add_Manual.targateDate1(7), add_Manual.targateDate1(7));
					}					SeleniumUtil.waitForPageToLoad(6000);
				if(readOnly.getAllAmount1().get(0).getText()==null)
				{
					SeleniumUtil.click(readOnly.ProjectedExapdIcon());
				}
				
				int transactionsize=readOnly.getAllAmount1().size();
				for(int i=0;i<transactionsize;i++)
				{
					
					if(readOnly.getAllAmount1().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionAmount1")))
					{
						SeleniumUtil.click(readOnly.getAllAmount1().get(i));
						SeleniumUtil.waitForPageToLoad();
						investorInstanceRow=i;
						break;
					}
				}
				SeleniumUtil.waitForPageToLoad(2000);
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAmountLabel"),
						"Amount field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAmountValue().getText(), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionAmount1"),
						"Amount field value Should be readonly");
			}
			
			@Test(description = "AT-70767:verify Instance Transaction drescription read only  ", priority = 54,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyProjectedInstanceTransactionDescriptionFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyDescriptionLabel"),
						"description field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyDescriptionValue().getText(), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionDescription"),
						"description field value Should be readonly");
			}
			
			@Test(description = "AT-70767:verify Instance Transaction account read only  ", priority = 55,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyProjectedInstanceTransactionAccountFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAccountLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyAccountLabel2"),
						"Account field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyAccounttValue().getText(), PropsUtil.getDataPropertyValue("AdvisorAggTransactionAccountName"),
						"Account field value Should be readonly");
			}
			@Test(description = "AT-70767:verify agregated Transaction date read only  ", priority = 56,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyProjectedInstanceTransactionDateFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateLabel().getText(), PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyScheduledDateLabel"),
						"Date field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyScheduledDateValue().getText(), add_Manual.targateDate1(7),
						"Date field value Should be readonly");
			}
			@Test(description = "AT-70769:verify Instance Transaction category read only  ", priority = 57,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyProjectedInstanceTransactionCategoryFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryLabel().getText(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyCategoryLabel"),
						"category field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyCategoryValue().getText(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyCategory"),
						"category field value Should be readonly");
			}
			@Test(description = "AT-70774:verify Instance Transaction tag read only  ", priority = 58,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyProjectedInstanceTransactionTagFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyTagLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyTagLabel"),
						"Tag field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyTagValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionTag1"),
						"tag field value Should be readonly");
			}
			
			@Test(description = "AT-70771,AT-70773:verify Instance Transaction note read only  ", priority = 59,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyProjectedInstanceTransactionNoteFieldReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAggReadOnyNoteLabel"),
						"Note field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyNoteValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactionNote1"),
						"Note field value Should be readonly");
			}
			@Test(description = "AT-70776:verify Instance Transaction attachment read only  ", priority = 60,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyInstanceTransactionAttachmentReadonly_InvestorView() throws Exception
			{
				String attachmentLabel=readOnly.transactionDetailsReadonlyAttachmentLabel().getText().trim();
				SeleniumUtil.click(readOnly.transactionDetailsReadonlyAttachmentImage().get(0));
				String acttachmentHeader=attchment.popUpHeader().getText();
				
				SeleniumUtil.click(attchment.close());
				Assert.assertEquals(acttachmentHeader, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentPopUp"),"attachment ment popupHeader");
				Assert.assertEquals(attachmentLabel, PropsUtil.getDataPropertyValue("TransactionAggReadOnyAttachmentLabel"),"attachment label");
			}
			@Test(description = "AT-70776verify Instance Transaction check read only  ", priority = 61,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyInstanceTransactionCheckReadonly_InvestorView() throws Exception
			{
				Assert.assertEquals(readOnly.transactionDetailsReadonlyCheckLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionInstanceReadOnlyCheckFieldLabel"),
						"check field label Should be readonly");
				Assert.assertEquals(readOnly.transactionDetailsReadonlyCheckValue().getText().trim(), PropsUtil.getDataPropertyValue("AdvisorInstanceTransactioncheck"),
						"Note field value Should be readonly");
				
				
				
			}
			
			@Test(description = "AT-70778,AT-70779verify Instance Transaction close read only  ", priority = 62,dependsOnMethods="verifyProjITAmountFieldReadonly_Investor")
			public void verifyInstanceTransactionCloseButton_InvestorView() throws Exception
			{
				String closeButton=readOnly.transactionDetailsCloseButton().getText().trim();
				SeleniumUtil.click(readOnly.transactionDetailsCloseButton());
				String transactionColapsed=readOnly.transactionDetailsColapsedSeries().get(investorInstanceRow).getAttribute("aria-expanded").trim();
				Assert.assertEquals(closeButton, PropsUtil.getDataPropertyValue("TransactionSeriesReadOnlyCloseButtonText"),
						" Close Button text");
				Assert.assertEquals(transactionColapsed, "false",
						" Transaction details should close when click on close");
			}
}
