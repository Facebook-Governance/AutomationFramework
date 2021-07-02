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
import com.omni.pfm.Accounts.Account_ZeroAccGroups_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

	public class Acc_ZeroAccGroup_Test extends TestBase {
		private static final Logger logger = LoggerFactory.getLogger(Acc_ZeroAccGroup_Test.class);
		
		AccountAddition accountAdd;
		Account_ZeroAccGroups_Loc gotoset;

		@BeforeClass()
		public void init() throws Exception
		{
			accountAdd =  new AccountAddition();
			doInitialization("SFC");
			gotoset = new Account_ZeroAccGroups_Loc(d);
		} 
		@Test(description = "Login Successfully", priority = 1,enabled=true)

		public void Login() throws Exception {
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(3000);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("mallikan.Investment1", "Investment1");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();	
}


	@Test(description = "AT-51731 Verify Creating Account Groups and Deleting Accounts in Account Groups", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 2,enabled=true)
	public void SettingsFin() throws Exception {

		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		SeleniumUtil.waitForPageToLoad();
		logger.info("Creating Account Group");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(gotoset.createGroupIcon());
		SeleniumUtil.waitForPageToLoad();
		String groupName="TEST800";
		gotoset.groupNamefield().sendKeys(groupName);
		String accInGroup = gotoset.AccountstoInclude().get(0).getText();
		logger.info("Added Account Group Name Details is: "+groupName);
		logger.info("added acc is "+accInGroup);
		SeleniumUtil.click(gotoset.AccountstoInclude().get(0));
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(gotoset.CreateGpBtnUnderSettFin());
		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Deleting same Account in Settings");
		SeleniumUtil.click(gotoset.AccSettingsMenu());
		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Deleting Account Info is :"+gotoset.AddedsitetoFindAcc().get(0).getText());
		SeleniumUtil.waitForElement(null, 4000);
		for(int i=0;i<gotoset.AddedsitetoFindAcc().size();i++){
		if(accInGroup.contains(gotoset.AddedsitetoFindAcc().get(i).getText()))
		{
			SeleniumUtil.click(gotoset.AccsetIcon().get(i));
			break;
		}
		}
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(gotoset.DeleteIconandText());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(gotoset.ConfirmDelCheckbox());
		SeleniumUtil.click(gotoset.delFinalConfirm());
		SeleniumUtil.click(gotoset.AccSettingsMenu());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		logger.info("No Accounts in Accounts Group"+PropsUtil.getDataPropertyValue("NoAccountsinGroup"));
		Assert.assertEquals(gotoset.NoAccingrpMessg().getText(),PropsUtil.getDataPropertyValue("NoAccountsinGroup"),"");
		}


	@Test(description = "AT-51732 Verify that Account groups with zero accounts displayed in Global settings - Account Groups", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 3,enabled=true)
	
	public void VerifyAccGroups() throws Exception {
		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		logger.info("No Accounts Message: "+gotoset.NoAccingrpMessg().getText());
		Assert.assertEquals(gotoset.NoAccingrpMessg().getText(),PropsUtil.getDataPropertyValue("NoAccountsinGroup"));
		}
	
	@Test(description = "AT-51733 Verify Account Group Name with Edit and Delete Options in Accoun Groups under Settings Finapp", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 4,enabled=true)
	
	public void VerifyAccHeader() throws Exception {
		logger.info("Verifying Account Group Header");
		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		logger.info("Account Header is : "+gotoset.AccHeader().getText());
		Assert.assertEquals(gotoset.AccHeader().getText(),PropsUtil.getDataPropertyValue("AccountHeader"));
		logger.info("Verifying Delete Option is available in header");
		SeleniumUtil.click(gotoset.VerifyDelOpIcon());
		SeleniumUtil.click(gotoset.VerifyCancelBtn());
		logger.info("Delete Option is enabled:"+gotoset.VerifyDelOpIcon().isEnabled());
		Assert.assertTrue(gotoset.VerifyDelOpIcon().isDisplayed());
		logger.info("Verifying Edit Option is available in header");
		SeleniumUtil.click(gotoset.VerifyEditOpIcon());
		SeleniumUtil.click(gotoset.VerifyCloseBtn());
		logger.info("Delete Option is enabled:"+gotoset.VerifyEditOpIcon().isEnabled());
		Assert.assertTrue(gotoset.VerifyEditOpIcon().isDisplayed());
		}

	@Test(description = "AT-51734 Verify that, 'No Accounts linked to the Group' message displayed below the header", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 5,enabled=true)
	
	public void VerifyMessage() throws Exception {
		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		logger.info("No Account Group Message is : "+gotoset.NoAccingrpMessg().getText());
		String expected=PropsUtil.getDataPropertyValue("NoAccountsinGroup").trim();
		String actual=gotoset.NoAccingrpMessg().getText();
		Assert.assertEquals(expected, actual);
		}

	@Test(description = "AT-51735 Verify that, in zero account groups user able to edit the group using 'Edit' option", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 6,enabled=true)
	
	public void EditAccGroupName() throws Exception {
		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		SeleniumUtil.click(gotoset.VerifyEditOpIcon());
		gotoset.groupNamefield().clear();
		String groupname2="Yodlee900";
		gotoset.groupNamefield().sendKeys(groupname2);
		logger.info("Added Account Group Details is: "+groupname2);
		SeleniumUtil.click(gotoset.AccountstoInclude().get(0));
		SeleniumUtil.click(gotoset.SaveChangesbtn());
		Assert.assertEquals(groupname2,PropsUtil.getDataPropertyValue("EditGroup"));
		}	

	@Test(description = "AT-51736 Verify that,in zero account groups'Save Changes' should be disabled until a single account is selected", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 7,enabled=true)
	
	public void SaveChngDisabled() throws Exception {
		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(gotoset.VerifyEditOpIcon());
		SeleniumUtil.waitForPageToLoad();
		gotoset.groupNamefield().clear();
		gotoset.groupNamefield().sendKeys("Yod123");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(gotoset.AccountstoInclude().get(0));
		SeleniumUtil.waitForPageToLoad();
		String disabled=gotoset.SaveChangesbtn().getAttribute("class");
	    Assert.assertTrue(disabled.contains("disabled"));
		SeleniumUtil.click(gotoset.VerifyCloseBtn());
		SeleniumUtil.waitForPageToLoad();
	   
		}

	@Test(description = "AT-51737 Verify that, in zero account groups able to delete the group using 'Delete' option.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 8,enabled=true)
	public void DeleteAccGrp() throws Exception {
		SeleniumUtil.click(gotoset.gotoSettingsFinAppTab());
		SeleniumUtil.click(gotoset.AccGroupMenu());
		SeleniumUtil.waitForPageToLoad();
		logger.info("Deleted group name is: "+gotoset.AccHeader().getText());
		SeleniumUtil.click(gotoset.DeliconinAccGrp());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(gotoset.delgrpconfirm());
		logger.info("Updated Toast Message is : "+gotoset.toasterMessge().getText());
		Assert.assertFalse(gotoset.AccGroupList().contains(gotoset.AccHeader().getText()));
		}
	
	@Test(description = "AT-51738: Verify that zero account groups should not be displayed and not applicable for Account Groups in Accounts Finapp.", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 9,enabled=true)
	public void VerifyZeAccinAccFin() throws Exception {
		
    	PageParser.forceNavigate("AccountsPage", d);
    	SeleniumUtil.click(gotoset.VerifyinAccGroup());
   	    Assert.assertFalse(gotoset.accPlaceHolder().equals(PropsUtil.getDataPropertyValue("NoAccountsinGroup")));
		}
	}
	
	
				
				
	
