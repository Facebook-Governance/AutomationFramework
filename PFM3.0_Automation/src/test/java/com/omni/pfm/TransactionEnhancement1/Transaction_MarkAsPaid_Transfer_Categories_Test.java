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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_MarkAsPaid_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_MarkAsPaid_Transfer_Categories_Test extends TestBase{
	private static final Logger logger = LoggerFactory.getLogger(Transaction_MarkAsPaid_Transfer_Categories_Test.class);
	public static String userName="";
	
	Transaction_MarkAsPaid_Loc markASPaid;
	Add_Manual_Transaction_Loc manual;
	Series_Recurence_Transaction_Loc series;
	Transaction_Search_Loc search;
	Transaction_Layout_Loc layout;
	LoginPage login;
	 LoginPage_SAML3 saml;
	AccountAddition accountAdd;
	Aggregated_Transaction_Details_Loc agg;
	
	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		doInitialization("Transaction Search");
		
		markASPaid=new Transaction_MarkAsPaid_Loc(d);
		manual=new Add_Manual_Transaction_Loc(d);
		series=new Series_Recurence_Transaction_Loc(d);
		layout=new Transaction_Layout_Loc(d);
		search=new Transaction_Search_Loc(d);
		login=new LoginPage();
		agg=new Aggregated_Transaction_Details_Loc(d);
        accountAdd=new AccountAddition();
        saml=new LoginPage_SAML3();
      //  saml.login(d, saml.getInvestorUserName(), null, "10003700");
        LoginPage.loginMain(d, loginParameter);
        accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("MarkAsPaid.site16441.2", "site16441.2");
	    SeleniumUtil.waitForPageToLoad(5000);
	    PageParser.forceNavigate("Transaction", d);
	    SeleniumUtil.waitForPageToLoad(5000);
	}
	
	@Test(description = "AT-62051:verify Mark as Paid option is displayed for overdue Transcations with category type Transfer Example: If user had missed a transaction on 15th of march then it has to show overdue and Mark as Paid option.", priority = 2)
	public void verifyMarkASpaidOption() throws Exception {
		logger.info("Search transfer type category for before two days of current date");
		layout.serachTransaction(manual.targateDate1(-1), manual.targateDate1(-1));
	    logger.info("click on Project transaction expand");
	   // SeleniumUtil.click(search.ProjectedExapdIcon());
		SeleniumUtil.waitForPageToLoad(5000);
		String actual =null;
		logger.info("checking transaction should be 2 , transaction over due should be 2, marak as paid should be 2");;
		if(series.getAllcat1().size()==1 && markASPaid.MarkAsPaid().size()==1)
		{
			SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
			actual=markASPaid.MarkAsPaidPopUp().get(0).getText().trim();
		}
		logger.info("validation mark as paid popup for Transfer type category overdue transaction");
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("MakAspaidPopUpHeader"), "mark as paid popup is not displyed ");
	}
	
	
	@Test(description = "AT-62055,AT-62053:Verify X Icon displayed on the Mark as paid Pop up",dependsOnMethods={"verifyMarkASpaidOption"}, priority = 3)
	public void verifyMarkASpaidPopUpClose() throws Exception {
		logger.info("mark as paid close button is displaying");
		Assert.assertTrue(markASPaid.MarkAsPaidPopUpClose().isDisplayed(),"close button should be displyed in mark as paid opoup");
		
	}
	
	@Test(description = "AT-62054:Verify Update and Cancel Button Displayed on the Mark as Paid Pop up", priority = 4)
	public void verifyMarkASpaidPopUpCancel() throws Exception {
		logger.info("mark as paid cancel button is displaying");
		Assert.assertTrue(markASPaid.MarkAsPaidPopUpcancel().isDisplayed(),"cancel button should be displyed in mark as paid opoup");
		
	}
	@Test(description = "AT-62054:Verify Update and Cancel Button Displayed on the Mark as Paid Pop up", priority = 5)
	public void verifyMarkASpaidPopUpPaid() throws Exception {
		logger.info("mark as paid confirmation button is displaying");
		Assert.assertTrue(markASPaid.MarkAsPaidPopUpPaid().isDisplayed(),"mark as paid button should be displyed in mark as paid opoup");
		
	}
	
	@Test(description = "AT-62056:Verify after clicking on Cancel or X icon it should Close the Mark as Paid Pop up", priority = 6)
	public void verifyMarkASpaidPopUpcancelclick() throws Exception {
		logger.info("when you click on cancel button popup should go ");
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpcancel());
		SeleniumUtil.waitForPageToLoad();
		boolean expected=false;
			if(markASPaid.MarkAsPaidPopUp().size()==0);
			{
				expected=true;
			}
		
			Assert.assertTrue(expected,"popup should close when you click on cancel");
			Assert.assertTrue(markASPaid.MarkAsPaid().size()==1,"after cancel the popup mark as paid option should be displyed in transaction");
	}
	
	@Test(description = "AT-62056:Verify after clicking on Cancel or X icon it should Close the Mark as Paid Pop up", priority = 7)
	public void verifyMarkASpaidPopUpCloseclick() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpClose());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean expected=false;
		if(markASPaid.MarkAsPaidPopUp().size()==0);
		{
			expected=true;
		}
	
		Assert.assertTrue(expected,"popup should close when you click on close");
		Assert.assertTrue(markASPaid.MarkAsPaid().size()==1,"after cancel the popup mark as paid option should be displyed in transaction");
	}
		
	@Test(description = "AT-52094:Verify the message Are you sure you want to mark <Transaction Amount> <Description> transaction dated <transaction date> as paid? displayed in the Pop up", priority = 8)
	public void verifyMarkASpaidPopUpPaidMessage() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(markASPaid.MarkAsPaidPopUpMessage().getText(), PropsUtil.getDataPropertyValue("markAsPaidMessage1") +
				""+ PropsUtil.getDataPropertyValue("MakAspaidTransactionAmount") + " "+ PropsUtil.getDataPropertyValue("marrkasPaidDescription1")+
				" " + PropsUtil.getDataPropertyValue("markAsPaidMessage2") + " "+ manual.targateDate1(-1) + " "+ PropsUtil.getDataPropertyValue("markAsPaidMessage3"));
	}
	@Test(description = "AT-52100:Verify Mark as Paid confirmation pop up window should have a check box with the text Don't show me again", priority = 9)
	public void verifyMarkASpaidPopUpCheckBoxMessage() throws Exception {
		Assert.assertEquals(markASPaid.MarkAsPaidPopUpCheckBoxMesg().getText().trim(),PropsUtil.getDataPropertyValue("marAsPaidCheckBoxMessage"));
		
	}
	
	@Test(description = "AT-62057,AT-62052:Verify after clicking on Update Button in the Mark as paid pop up Transaction should get updated.", priority = 10)
	public void verifyMarkASpaidPopUpPaidClick() throws Exception {
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpPaid());
		SeleniumUtil.waitForPageToLoad();
		  boolean actual=false;
		/*
		 * for(int i=0;i<series.getAlldescription1().size();i++) {
		 * if(series.getAllAmount1().get(i).getText().equals(PropsUtil.
		 * getDataPropertyValue("MakAspaidTransactionAmount"))||series.
		 * getAlldescription1().size()==0) { actual=true; } }
		 */
		  Assert.assertTrue(series.getAlldescription1().size()==0);
		
	}
	@Test(description = "AT-62052:verify mark as paid by selecting show me again check box", priority = 11)
	public void verifyMarkASpaidPopUpdontShowMegain() throws Exception {
		layout.serachTransaction(manual.targateDate1(-2), manual.targateDate1(-2));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpCheckBoxMesg());
		SeleniumUtil.click(markASPaid.MarkAsPaidPopUpPaid());
		SeleniumUtil.waitForPageToLoad(2000);
		layout.serachTransaction(manual.targateDate1(-2), manual.targateDate1(-2));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(markASPaid.MarkAsPaid().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		boolean actual=false;
			if(markASPaid.MarkAsPaid().size()==0 &&series.getAllcat1().size()==0 && markASPaid.TranOverDue().size()==0)
			{
				actual=true;
			}
					
			Assert.assertTrue(actual);
	
	}	
}
