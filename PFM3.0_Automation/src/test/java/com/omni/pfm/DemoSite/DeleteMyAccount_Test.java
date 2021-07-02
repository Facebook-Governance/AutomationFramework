/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.DemoSite;

import com.omni.pfm.testBase.TestBase;

import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.LandingScreen_Loc;
import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.pages.DemoSite.SuccessPage_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.pages.DemoSite.DeleteMyAccount_Loc;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;


import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;





public class DeleteMyAccount_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(DeleteMyAccount_HappyPath_Test.class);

	SuccessPage_Loc SuccessPage;
	LandingScreen_Loc LandingScreen;
    DeleteMyAccount_Loc DeleteAccount;
	CreateServiceRequest_Loc CreateServiceRequest;
	SignUp_Loc SignUp;
	DemoSiteUtil demoUtil;
	UserProfilePersonalInfo_Loc PersonalInfo;
	DemoSiteDBUtil DBUtil;
	public static String userid;


    @BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {

	doInitialization("Delete MyAccount Test");
	
	DeleteAccount = new DeleteMyAccount_Loc(d);
	SignUp = new SignUp_Loc(d);
	demoUtil = new DemoSiteUtil();
	PersonalInfo = new UserProfilePersonalInfo_Loc(d);
	CreateServiceRequest = new CreateServiceRequest_Loc(d);
}
    
	@Test(priority = 1, description = "login to user profile")

	public void loginToUserProfile() {

		// Login through dummy url to skip Login using OTP

		DemoSiteUtil.launchNodeURL();

		SeleniumUtil.waitForPageToLoad();

		PersonalInfo.typeTextField(PersonalInfo.userID(), SignUp_Test.userid);
    	PersonalInfo.typeTextField(PersonalInfo.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		
    	PersonalInfo.typeTextField(PersonalInfo.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.waitForPageToLoad();
		
		CreateServiceRequest.navigateToMyProfile();

	}

	@Test(priority = 2, description = ", Validate Delete My Account Link")
	public void validateDeleteMyAcctLink() {

		SoftAssert sa = new SoftAssert();

		// Validate link text
		String linkText = DeleteAccount.linkDeleteAcct().getText();
		sa.assertEquals(linkText, PropsUtil.getDataPropertyValue("DeleteAcclinkDeleteAcct"),
				"validate the link text");

		// Click on link to navigate to next page
		SeleniumUtil.click(DeleteAccount.linkDeleteAcct());
		sa.assertTrue(DeleteAccount.lblEnterPwdtoContinue().isDisplayed(), "validating the pwd page for a label");

		SeleniumUtil.waitForPageToLoad();

		// Click Back to My profile
		SeleniumUtil.click(DeleteAccount.linkBackToMyProfile());
		SeleniumUtil.waitForPageToLoad();
		sa.assertTrue(DeleteAccount.linkDeleteAcct().isDisplayed(), "validating back to profile link");

		sa.assertAll();

	}

	@Test(priority = 3, description = ", Validate Passowrd error message and the page")
	public void validatePwdErrorMessages() {

		SoftAssert sa = new SoftAssert();

		// Click delete my acct link
		SeleniumUtil.click(DeleteAccount.linkDeleteAcct());
		// validate header
		String header = DeleteAccount.lblDeleteMyAccount().getText();
		sa.assertEquals(header, PropsUtil.getDataPropertyValue("DeleteAccheaderPwdPage"),
				"validate header in pwd page");

		// validate message
		String msg = DeleteAccount.lblEnterPwdtoContinue().getText();
		sa.assertEquals(msg, PropsUtil.getDataPropertyValue("DeleteAccmsgToEnterPwd"),
				"validate message to enter pwd");

		// validate Button name
		String btn = DeleteAccount.btnContinue().getAttribute("value").toUpperCase();
		sa.assertEquals(btn, PropsUtil.getDataPropertyValue("DeleteAccbtnContinue"),
				"validate continue button text");

		// Leave pwd blank and submit
		SeleniumUtil.click(DeleteAccount.btnContinue());
		
		// Capture error message
		String errorBlank = DeleteAccount.errMsgEmptyPwd().getText();
		// assert error msg
		sa.assertEquals(errorBlank, PropsUtil.getDataPropertyValue("DeleteAccerrMsgBlankPwd"),
				"validate blank pwd error message");

		// Enter Invalid pwd and validate error message
		DeleteAccount.typeTextField(DeleteAccount.txtboxPassword(), PropsUtil.getDataPropertyValue("DeleteAccinvalidPwd"));
		SeleniumUtil.click(DeleteAccount.btnContinue());
		SeleniumUtil.waitForPageToLoad(2000);
		String errorInvalid = DeleteAccount.errMsgInvalidPwd().getText();
		sa.assertEquals(errorInvalid, PropsUtil.getDataPropertyValue("DeleteAccerrMsgIncorrectPwd"),
				"validate invalid pwd error message");

		sa.assertAll();

	}

	@Test(priority = 4, description = ", Validate confirmation page")
	public void validateConfirmationPage() {
		SoftAssert sa = new SoftAssert();

		//com.typeInputText(DeleteAccount.TxtboxPassword, PropertyLoader.getProperty("deleteMyAccount", "StaticPwd"));
		DeleteAccount.typeTextField(DeleteAccount.txtboxPassword(), PropsUtil.getDataPropertyValue("DeleteAccStaticPwd"));
		SeleniumUtil.click(DeleteAccount.btnContinue());
		SeleniumUtil.waitForPageToLoad(2000);

		// capture the messages in the screen
		String msg1 = DeleteAccount.msg1().getText();
		String msg2 = DeleteAccount.msg2().getText();
		String msgReasonHeader = DeleteAccount.msgReasonHeader().getText();
		String reason1 = DeleteAccount.msgReason1().getText();
		String reason2 = DeleteAccount.msgReason2().getText();
		String reason3 = DeleteAccount.msgReason3().getText();
		String reason4 = DeleteAccount.msgReason4().getText();
		String reason5 = DeleteAccount.msgReason5().getText();
		String msgTxn = DeleteAccount.msgTransactions().getText();
		String linkTxn = DeleteAccount.linkTransactions().getText();

		// Start asserting the strings
		sa.assertEquals(msg1, PropsUtil.getDataPropertyValue("DeleteAccmsg1ConfirmationPage"), "Oh No message");
		sa.assertEquals(msg2, PropsUtil.getDataPropertyValue( "DeleteAccmsg2ConfirmationPage"),
				"message 2 confirmation page");
		sa.assertEquals(msgReasonHeader, PropsUtil.getDataPropertyValue("DeleteAccmsgReasonHeader"),
				"message reason header");
		sa.assertEquals(reason1, PropsUtil.getDataPropertyValue("DeleteAccmsgReason1"), "reason 1");
		sa.assertEquals(reason2, PropsUtil.getDataPropertyValue("DeleteAccmsgReason2"), "reason 2");
		sa.assertEquals(reason3, PropsUtil.getDataPropertyValue("DeleteAccmsgReason3"), "reason 3");
		sa.assertEquals(reason4, PropsUtil.getDataPropertyValue("DeleteAccmsgReason4"), "reason 4");
		sa.assertEquals(reason5, PropsUtil.getDataPropertyValue("DeleteAccmsgReason5"), "reason 5");

		sa.assertEquals(msgTxn, PropsUtil.getDataPropertyValue("DeleteAccmsgTransactions"), "message txn");

		// Assert the button names
		sa.assertEquals(DeleteAccount.btnCancel().getAttribute("value").toUpperCase(),
				PropsUtil.getDataPropertyValue("DeleteAccbtnCancel"), "cancel btn");
		sa.assertEquals(DeleteAccount.btnDeleteAcct().getAttribute("value").toUpperCase(),
				PropsUtil.getDataPropertyValue("DeleteAccbtnDeleteAcct"), "Delete Acct btn");

		// Validate that all the five checkboxes are unchecked by default
		sa.assertFalse(DeleteAccount.checkBox1().isSelected(), "Checkbox 1 unchecked");
		sa.assertFalse(DeleteAccount.checkBox2().isSelected(), "Checkbox 2 unchecked");
		sa.assertFalse(DeleteAccount.checkBox3().isSelected(), "Checkbox 3 unchecked");
		sa.assertFalse(DeleteAccount.checkBox4().isSelected(), "Checkbox 4 unchecked");
		sa.assertFalse(DeleteAccount.checkBox5().isSelected(), "Checkbox 5 unchecked");

		sa.assertAll();

	}

	@Test(priority = 5, description = ", Validate actions on confirmation page")
	public void validateConfirmationPageActions() {
		SoftAssert sa = new SoftAssert();

		// Validate the cross icon on reasons section
		SeleniumUtil.click(DeleteAccount.crossIcon());
		//sa.assertFalse(DeleteAccount.checkBox1().isDisplayed());

		// Validate link to open up reasons box
		SeleniumUtil.click(DeleteAccount.linkPlsTellUsWhy());
		SeleniumUtil.waitForPageToLoad();
		sa.assertTrue(DeleteAccount.checkBox1().isDisplayed());

		sa.assertAll();

	}

	@Test(priority = 6, description = ", Validate checkboxes on confirmation page")
	public void validateCheckboxesAndComment() {
		SoftAssert sa = new SoftAssert();

		//SeleniumUtil.click(DeleteAccount.linkPlsTellUsWhy());

		// check all the checkboxes
		SeleniumUtil.click(DeleteAccount.checkBox1());
		SeleniumUtil.click(DeleteAccount.checkBox2());
		SeleniumUtil.click(DeleteAccount.checkBox3());
		SeleniumUtil.click(DeleteAccount.checkBox4());
		SeleniumUtil.click(DeleteAccount.checkBox5());
		SeleniumUtil.waitForPageToLoad(2000);

		// validate they got checked
		String fal = "true";
		sa.assertTrue(DeleteAccount.checkBox1().getAttribute("aria-checked").matches(fal), "Checkbox 1 checked");
		sa.assertTrue(DeleteAccount.checkBox2().getAttribute("aria-checked").matches(fal), "Checkbox 2 checked");
		sa.assertTrue(DeleteAccount.checkBox3().getAttribute("aria-checked").matches(fal), "Checkbox 3 checked");
		sa.assertTrue(DeleteAccount.checkBox4().getAttribute("aria-checked").matches(fal), "Checkbox 4 checked");
		sa.assertTrue(DeleteAccount.checkBox5().getAttribute("aria-checked").matches(fal), "Checkbox 5 checked");

		// Uncheck all the checkboxes
		
		SeleniumUtil.click(DeleteAccount.checkBox1());
		SeleniumUtil.click(DeleteAccount.checkBox2());
		SeleniumUtil.click(DeleteAccount.checkBox3());
		SeleniumUtil.click(DeleteAccount.checkBox4());
		SeleniumUtil.click(DeleteAccount.checkBox5());

		// Validate that all the five checkboxes are unchecked
		sa.assertFalse(DeleteAccount.checkBox1().isSelected(), "Checkbox 1 unchecked");
		sa.assertFalse(DeleteAccount.checkBox2().isSelected(), "Checkbox 2 unchecked");
		sa.assertFalse(DeleteAccount.checkBox3().isSelected(), "Checkbox 3 unchecked");
		sa.assertFalse(DeleteAccount.checkBox4().isSelected(), "Checkbox 4 unchecked");
		sa.assertFalse(DeleteAccount.checkBox5().isSelected(), "Checkbox 5 unchecked");

		sa.assertAll();

	}

	@Test(priority = 7, description = ", Validate other reasons textbox")
	public void validateOtherReasonsTxtBox() {
		SoftAssert sa = new SoftAssert();

		// Click on the textbox

		SeleniumUtil.click(DeleteAccount.txtboxOthers());
		// validate that the checkbox got checked
		String fal = "true";
		sa.assertTrue(DeleteAccount.checkBox5().getAttribute("aria-checked").equalsIgnoreCase(fal), "Checkbox 5 checked");

		//System.out.println(DeleteAccount.CheckBox5.getAttribute("aria-checked"));

		// generate a random string of 4010 char

		String s = DeleteAccount.getRandomString(4010);
		//com.typeInputText(DeleteAccount.txtboxOthers(), s);
		
		DeleteAccount.typeTextField(DeleteAccount.txtboxOthers(), s);

		// take the length of entered text
		int len = DeleteAccount.txtboxOthers().getAttribute("value").toString().length();

		// validate length is 4000
		sa.assertTrue(len == 4000);

		sa.assertAll();

	}

	@Test(priority = 8, description = ", Validate Back link and Cancel btn in delete confirmation page")
	public void validateBackLinkInAndCancelBtn() {
		SoftAssert sa = new SoftAssert();

		// Click Back to my profile Link
		//com.clickByJS(DeleteAccount.linkBackToMyProfile());

		SeleniumUtil.click(DeleteAccount.linkBackToMyProfile());
		SeleniumUtil.waitForPageToLoad(2000);

		// assert home page
		sa.assertTrue(DeleteAccount.linkDeleteAcct().isDisplayed(), "Delete acct link is displayed");

		// Repeat the steps to go to confirmation page and hit Cancel btn
		SeleniumUtil.click(DeleteAccount.linkDeleteAcct());
		DeleteAccount.typeTextField(DeleteAccount.txtboxPassword(), PropsUtil.getDataPropertyValue("DeleteAccStaticPwd"));

		SeleniumUtil.click(DeleteAccount.btnContinue());

		// Click Cancel btn
		SeleniumUtil.click(DeleteAccount.btnCancel());
		SeleniumUtil.waitForPageToLoad(2000);

		// assert home page
		sa.assertTrue(DeleteAccount.linkDeleteAcct().isDisplayed(), "Delete acct link is displayed");

		sa.assertAll();

	}

	@Test(priority = 9, description = ", Validate txn link")
	public void validateTxnLink() {
		SoftAssert sa = new SoftAssert();

		// Repeat the steps to go to confirmation page and hit Cancel btn
		SeleniumUtil.click(DeleteAccount.linkDeleteAcct());

	
		
		DeleteAccount.typeTextField(DeleteAccount.txtboxPassword(), PropsUtil.getDataPropertyValue("DeleteAccStaticPwd"));
		SeleniumUtil.click(DeleteAccount.btnContinue());
		// click here link
		
		SeleniumUtil.click(DeleteAccount.linkTransactions());
		SeleniumUtil.waitForPageToLoad(3000);

		// assert Txn page
		sa.assertTrue(DeleteAccount.headerTxn().isDisplayed());

		sa.assertAll();

	}

}