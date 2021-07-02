/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/

package com.omni.pfm.Accounts;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.Accounts.Account_ShowNoteMessage_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Acc_ShowNoteMessage_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Acc_ShowNoteMessage_Test.class);

	AccountAddition accountAdd;
	Account_ShowNoteMessage_Loc show;

	@BeforeClass()
	public void init() throws Exception
	{

		accountAdd =  new AccountAddition();
		doInitialization("SFC");
		show = new Account_ShowNoteMessage_Loc(d);

	} 
	@Test(description = "Login Successfully", priority = 1,enabled=true)

	public void Login() throws Exception {
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(3000);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("AcntTest2.site16441.2", "site16441.2");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description = "AT-51512 Verify that after closing the account, account should be hidden from Accounts Finapp", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 2,enabled=true)

	public void VerifyinAccFinapp() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		if(show.AccListinPlcHlder().get(0)==null){
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(4000);
		}

		String headerCheck = show.AccountsHeader().getText();
		logger.info("Accounts Header Message is : "+headerCheck);
		String expected=PropsUtil.getDataPropertyValue("AccountsFinApp").trim();
		String actual=show.AccountsHeader().getText();
		Assert.assertEquals(expected, actual);
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.AccListinPlcHlder().size();j++)
		{
			if(!(show.AccinAccPlceHlder().get(j).getText()).contains(PropsUtil.getDataPropertyValue("disclaim")))
			{
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.click(show.AccSettinMorBtn().get(j));
				break;
			}
		}
		String closingAcc=show.AccListinPlcHlder().get(0).getText();
		logger.info("Closed Acc Detail"+closingAcc);
		SeleniumUtil.click(show.clsAccIconText());
		String Note = PropsUtil.getDataPropertyValue("NoteMsg");
		show.notemessgeBox().sendKeys(Note);
		SeleniumUtil.click(show.clsAccConfirmBtn());
		SeleniumUtil.waitForPageToLoad();
		logger.info("Closed Account Detail is :"+closingAcc);
		logger.info("Closed Account Detail is not Present");
		Assert.assertFalse(show.AccinAccPlceHlder().contains(closingAcc));
	}

	@Test(description = "AT-51513 Verify that close icon text should be displayed for the closed account in Account Setting Page", 
			groups = {"DesktopBrowsers", "Smoke" }, priority = 3,enabled=true)

	public void VerifyinSettingsFin() {
		SeleniumUtil.click(show.gotoSettingsFinAppTab());
		SeleniumUtil.click(show.AccSettingsMenu());
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null)
			SeleniumUtil.click(show.AccSettingsMenu());
		logger.info("Closed account information is :"+show.ClosedAccinSet().get(0).getText());
		Assert.assertTrue(show.ClosedAccinSet().get(0).getText().toUpperCase().contains("CLOSED"));
	} 

	@Test(description ="AT-51514 & AT-51515 Verify that on click of the icon should show the NOTE message",
			groups = {"DesktopBrowsers", "Smoke" }, priority =4 ,enabled=true)

	public void VerifyCloseIcon() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null)
			PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.AccListinPlcHlder().size();j++)
		{
			if(!(show.AccinAccPlceHlder().get(j).getText()).contains(PropsUtil.getDataPropertyValue("disclaim")))
			{
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.click(show.AccSettinMorBtn().get(j));
				SeleniumUtil.click(show.CloseIconBtninPopup());
				break;
			}
			logger.info("Close Icon is Displayed"+show.ClosedAccinSet().get(j));
			Assert.assertTrue(show.ClosedAccinSet().get(j).isDisplayed());	
		} 
	}

	@Test(description ="AT-51516 & AT-51517 Verify that on click of the icon should show the NOTE message & Enter Message", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 5,enabled=true)

	public void VerifyNoteMessgeBox() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null)
			PageParser.forceNavigate("AccountsPage", d);	
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.AccListinPlcHlder().size();j++)
		{
			if(!(show.AccinAccPlceHlder().get(j).getText()).contains(PropsUtil.getDataPropertyValue("disclaim")))

			{
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.waitForElement(null, 3000);
				SeleniumUtil.click(show.AccSettinMorBtn().get(j));
				SeleniumUtil.waitForElement(null, 3000);
				break;
			}
		}
		SeleniumUtil.click(show.clsAccIconText());
		String Note=PropsUtil.getDataPropertyValue("NoteMsg");
		show.notemessgeBox().sendKeys(Note);
		logger.info("Verifying Text message"+Note);
		Assert.assertTrue(show.notemessgeBox().isDisplayed());
		SeleniumUtil.click(show.clsAccConfirmBtn());
		SeleniumUtil.waitForElement(null, 4000);
	}

	@Test(description ="AT-51518 Verify that user should be able to enter 150 characters as 'NOTE' message",
			groups = {"DesktopBrowsers", "Smoke" }, priority = 6,enabled=true)

	public void VerifyNoteMsg150() throws Exception {
		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.AccListinPlcHlder().size();j++)																																				
		{
			if(!(show.AccinAccPlceHlder().get(j).getText().toUpperCase()).contains("CLOSED"))

			{
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.click(show.AccSettinMorBtn().get(j));

				break;
			}
		}
		SeleniumUtil.click(show.clsAccIconText());
		SeleniumUtil.waitForElement(null, 4000);
		show.notemessgeBox().sendKeys(PropsUtil.getDataPropertyValue("NoteMsg150"));
		logger.info("NoteMSG150 is : "+show.notemessgeBox().getText());
		Assert.assertTrue(show.notemessgeBox().isDisplayed());
		SeleniumUtil.click(show.clsAccConfirmBtn());
		SeleniumUtil.waitForElement(null, 4000);

	}

	@Test(description ="AT-51519 Verify that user should be able to enter alphanumeric, special characters as 'NOTE' message", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 7,enabled=true)
	public void SpecialChar() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null)
			PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.AccListinPlcHlder().size();j++)
		{
			if(!(show.AccinAccPlceHlder().get(j).getText()).contains(PropsUtil.getDataPropertyValue("disclaim")))
			{
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.waitForElement(null, 4000);
				SeleniumUtil.click(show.AccSettinMorBtn().get(j));
				SeleniumUtil.waitForElement(null, 4000);
				break;
			}
		}
		SeleniumUtil.click(show.clsAccIconText());
		SeleniumUtil.waitForElement(null, 4000);
		show.notemessgeBox().sendKeys(PropsUtil.getDataPropertyValue("SpecialChar"));
		logger.info("Special charcter in Note is :"+show.notemessgeBox().getText());
		Assert.assertTrue(show.notemessgeBox().isDisplayed());
		SeleniumUtil.click(show.clsAccConfirmBtn());
	} 

	@Test(description ="AT-51520 & AT-51521 Verify that 'CLOSE ACCOUNT' button is available below Note message text box & Note text is displayed in Acc Settings",
			groups = {"DesktopBrowsers", "Smoke" }, priority = 8,enabled=true)

	public void VerifyCloseAccBtn() throws Exception {
		SeleniumUtil.click(show.gotoSettingsFinAppTab());
		SeleniumUtil.click(show.AccSettingsMenu());	
		SeleniumUtil.waitForElement(null, 8000);
		for(int j=0;j<show.NoteIcon().size();j++)
		{
			SeleniumUtil.click(show.NoteIcon().get(j));
			String actual = show.getFirstNoteText().get(j).getText();
			String expected=PropsUtil.getDataPropertyValue("SpecialChar");
			if(actual.equals(expected))
			{
				logger.info("Close Icon is present in Note Message Box"+show.ClsBtninNoteMsg().get(j).getText());
				Assert.assertTrue(show.ClsBtninNoteMsg().get(j).isDisplayed());
				SeleniumUtil.click(show.ClsBtninNoteMsg().get(j));
				break;
			}
		}
	} 

	@Test(description ="AT-51522 Verify that close icon is available only  'NOTE' message field is not empty"	+ "in Account" + " Settings", groups = {"DesktopBrowsers", "Smoke" },
			priority = 9,enabled=true)

	public void VerifyNoteMsgnotEmpty() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null)
			PageParser.forceNavigate("AccountsPage", d);
		for(int j=0;j<show.AccListinPlcHlder().size();j++)
		{
			if(!(show.AccinAccPlceHlder().get(j).getText()).contains(PropsUtil.getDataPropertyValue("disclaim")))
			{
				SeleniumUtil.click(show.moreOptBtn().get(j));
				SeleniumUtil.waitForElement(null, 4000);
				SeleniumUtil.click(show.AccSettinMorBtn().get(j));
				SeleniumUtil.waitForElement(null, 4000);
				break;
			}
		}
		SeleniumUtil.click(show.clsAccIconText());
		SeleniumUtil.waitForElement(null, 4000);
		show.notemessgeBox().sendKeys("");
		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Note message is :" +show.notemessgeBox().getText());
		Assert.assertTrue(show.notemessgeBox().isDisplayed());
		SeleniumUtil.click(show.clsAccConfirmBtn());
		SeleniumUtil.waitForElement(null, 4000);
	}

	@Test(description ="AT-51523,AT-51524 & AT-51525 In Account Type View, verify that there is a icon is displayed below the amount"	+ "in Account" + " Settings",
			groups = {"DesktopBrowsers", "Smoke" }, priority = 10,enabled=true)

	public void AccTypeView() throws Exception {
		SeleniumUtil.click(show.AccSettingsMenu());	
		SeleniumUtil.waitForElement(null, 4000);
		String noteIcon = show.noteIcon().get(0).getText();
		logger.info("NoteICon is present" + show.noteIcon().get(0).getText());

		for(int j=0;j<show.AccListinPlcHlder().size();j++)
		{
			if((show.AccinAccPlceHlder().get(j).getText()).contains(noteIcon))
			{
				SeleniumUtil.click(show.AccsetIcon().get(j));
				break;
			}
		}
		SeleniumUtil.click(show.ShoworHideAcc());
		SeleniumUtil.click(show.savechangesinSet());
		logger.info("Updated Toast Message is : " + show.toasterMessage().getText());

		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);

		SeleniumUtil.click(show.AccTypeView());
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.NoteIcon().size();)
		{
			SeleniumUtil.click(show.NoteIcon().get(j));
			break;
		}
		logger.info("Note message in Acc Type dispalyed as:"+show.noteIconMessage().getText());
		Assert.assertTrue(show.noteIconMessage().isDisplayed());
	}	

	@Test(description ="AT-51526 &AT-51527  In Account Group View, verify that  there is any closed account displayed in Account summary."	+ "in Account" + " Settings",
			groups = {"DesktopBrowsers", "Smoke" }, priority = 11,enabled=true)

	public void AccGroupView() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null)
			PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.AccGrpViewinAccFin());
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.CreateGrpsinAccFin());
		String groupName="TEST71";
		show.groupNamefield().sendKeys(groupName);
		Assert.assertTrue(show.groupNamefield().isDisplayed());
		logger.info("Added Account Group Name Details is: "+groupName);
		SeleniumUtil.click(show.AccountstoInclude().get(24));
		SeleniumUtil.click(show.AccountstoInclude().get(25));
		SeleniumUtil.click(show.AccountstoInclude().get(26));
		SeleniumUtil.click(show.ACC_createGroupbtn());
		SeleniumUtil.waitForElement(null, 6000);
	}

	@Test(description =" AT-51528 In Account Group View, verify that, 'NOTE' message is displaying on click of that icon .", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 12,enabled=true)

	public void VerifyNoteMsginAccGrpView() throws Exception {


		SeleniumUtil.click(show.gotoSettingsFinAppTab());
		SeleniumUtil.click(show.AccSettingsMenu());
		SeleniumUtil.waitForElement(null, 4000);
		for (int j = 0; j < show.NoteIcon().size(); j++) {
			SeleniumUtil.click(show.NoteIcon().get(j));
			SeleniumUtil.waitForElement(null, 4000);
			SeleniumUtil.click(show.noteValue().get(j));
			SeleniumUtil.waitForElement(null, 4000);
			SeleniumUtil.click(show.NotewithSetIcon().get(j));
			SeleniumUtil.waitForElement(null, 4000); 
			String ischecked = show.ShoworHideAcc().getAttribute("value");
			if (ischecked.trim().equalsIgnoreCase("false")) {
				SeleniumUtil.click(show.ShoworHideAcc());
			}
			SeleniumUtil.waitForElement(null, 2000);
			SeleniumUtil.click(show.savechangesinSet());
			SeleniumUtil.click(show.CloseIconBtninPopup());
		}
		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if (show.AccListinPlcHlder() == null)
			PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.AccGrpViewinAccFin());
		SeleniumUtil.waitForElement(null, 4000);

		for (int j = 0; j < show.NoteIcon().size(); j++) {
			SeleniumUtil.click(show.NoteIcon().get(j));
			logger.info("Note Icon with Message is Present" + show.noteValue().get(j).getText());
			Assert.assertTrue(show.noteValue().get(j).isDisplayed());
			break;
		}
	}


	@Test(description =" AT-51529 &  AT-51530 Verify that, 'Note' field should be editable in the Transaction Finapp", groups = {
			"DesktopBrowsers", "Smoke" }, priority =13,enabled=true)

	public void UpdateNoteMsginTranFin() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if(show.AccListinPlcHlder()==null){
			PageParser.forceNavigate("AccountsPage", d);
		}
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.SysTrnCrAccMorBtn());
		SeleniumUtil.waitForElement(null, 2000);
		SeleniumUtil.click(show.SysTrnCrAccSetOp());
		SeleniumUtil.click(show.clsAccIconText());
		SeleniumUtil.waitForElement(null, 4000);
		show.notemessgeBox().sendKeys(PropsUtil.getDataPropertyValue("transNote"));
		logger.info("Note Message sending is :"+show.notemessgeBox().getText());
		SeleniumUtil.click(show.clsAccConfirmBtn());
		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForElement(null, 4000);
		for(int i=0;i<show.noteinTranAccList().size();)
		{
			SeleniumUtil.click(show.noteIconinTranFin().get(0));
			break;
		}
		SeleniumUtil.click(show.noteShowmoreinTran());
		show.TextnoteinTran().clear();
		show.TextnoteinTran().sendKeys(PropsUtil.getDataPropertyValue("EditedinTran"));
		logger.info("Edited Info is :"+show.TextnoteinTran().getText());
		show.savebtn();

	}

	@Test(description =" AT-51529 &  AT-51530 Verify that, 'Note' field should be editable in the Transaction Finapp", groups = {
			"DesktopBrowsers", "Smoke" }, priority =14,enabled=true)

	public void VerifyEditedinAccFin() throws Exception {
		SeleniumUtil.click(show.gotoSettingsFinAppTab());
		SeleniumUtil.click(show.AccSettingsMenu());
		SeleniumUtil.waitForElement(null, 4000);
		for(int j=0;j<show.NoteIcon().size();j++)
		{
			SeleniumUtil.click(show.NoteIcon().get(j));
			if(show.noteValue().get(j).getText().contains(PropsUtil.getDataPropertyValue("transNote"))){
				SeleniumUtil.click(show.NotewithSetIcon().get(j)); 
			}
		}
		SeleniumUtil.waitForElement(null, 4000);
		String ischecked = show.ShoworHideAcc().getAttribute("value");
		if (ischecked.trim().equalsIgnoreCase("false")) {
			SeleniumUtil.click(show.ShoworHideAcc());
		}
		// SeleniumUtil.click(show.ShoworHideAcc());
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.savechangesinSet());
		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		show.waitForIcon();
		for(int i=0;i<show.NoteIcon().size();i++){
			SeleniumUtil.click(show.NoteIcon().get(i));
			if(show.noteValue().get(i).getText().equals(PropsUtil.getDataPropertyValue("transNote"))){
				logger.info("Note Message after Updation in Accounts Finapp is :"+show.noteValue().get(i).getText());
				String actual=PropsUtil.getDataPropertyValue("transNote");
				String expected=show.noteValue().get(i).getText();
				Assert.assertEquals(actual, expected);
				break;
			}
		}
	}

	@Test(description =" AT-51531 &  AT-51532 Verify that close icon should be displayed for 'CLOSED ACCOUNT' except Manual in the FI View", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 15,enabled=true)

	public void VerifyManualAccNoteMsg() throws Exception {
		SeleniumUtil.waitForElement(null, 4000);
		PageParser.forceNavigate("AccountsPage", d);
		if(show.AccListinPlcHlder()==null)
			PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.LinkanAccount());
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.manualAccount());
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.ma_account_type().get(0));
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.ma_accountname());
		show.ma_accountname().sendKeys(PropsUtil.getDataPropertyValue("ManualAccName"));
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.ma_nextbtn());
		SeleniumUtil.click(show.ma_curBal());
		show.ma_curBal().sendKeys("1000");
		SeleniumUtil.click(show.ma_Addbtn());
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.ma_alldonebtn()); 

		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(show.ma_YodleeMorebtn().get(0));
		SeleniumUtil.click(show.ma_AccSettinMorBtn());
		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Close Account Option is not present in Manual Account");
		Assert.assertFalse(show.isElementPresent(show.clsAccIconText()));
	}
}










