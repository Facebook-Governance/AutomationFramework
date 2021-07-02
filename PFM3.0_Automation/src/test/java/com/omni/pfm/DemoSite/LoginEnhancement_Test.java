/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/

package com.omni.pfm.DemoSite;

import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.Email;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

import com.omni.pfm.pages.DemoSite.LoginEnhancements_Loc;
import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.testBase.TestBase;

public class LoginEnhancement_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginEnhancement_Test.class);

	DemoSiteDBUtil DBUtil;
	Email mail;
	DemoSiteUtil DSUtil;
	LoginEnhancements_Loc LoginEnhancements;
	SignUp_Loc signUp;
	public static String userid;


	String gmailId = "";
	String gmailPassword = "";
	DemoSiteUtil demoUtil;
	
	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		
		doInitialization("Login Enhancement Test");
	
		LoginEnhancements = new LoginEnhancements_Loc(d);
		DBUtil = new DemoSiteDBUtil(gmailId, gmailId, gmailId);
		DSUtil = new DemoSiteUtil();
		mail=new Email();
		demoUtil = new DemoSiteUtil();
		signUp = new SignUp_Loc(d);
		

		gmailId = PropsUtil.getDataPropertyValue("LoginEn_email");
		gmailPassword = PropsUtil.getDataPropertyValue( "LoginEn_Password");

	}
	
	@Test(priority = 0, description = "Launch demosite URL.")

	public void login() throws MessagingException, InterruptedException {

		demoUtil.launchDemoSite();
		SeleniumUtil.waitForPageToLoad();
	}

	
	@Test(priority = 1, description = "AT-62326,AT-62328: Verify login card elements.")

	public void verifyLoginHeaderTex() throws MessagingException, InterruptedException {

		SoftAssert sa = new SoftAssert();


		

		String actualLoginHeaderTitle = LoginEnhancements.loginEnhancementHeaderText().getText().trim();
		String actualLoginDescription = LoginEnhancements.loginEnhancementSubHdrText().getText().trim();
		String actualLoginDescription1 = LoginEnhancements.loginEnhancementSubHdrText1().getText().trim();

		String expectedLoginHeaderTitle =PropsUtil.getDataPropertyValue("LoginEn_HeaderText").trim();
		String expectedLoginDescription = PropsUtil.getDataPropertyValue("LoginEn_ContentText").trim();
		String expectedLoginDescription1 = PropsUtil.getDataPropertyValue("LoginEn_ContentText1").trim();

		sa.assertEquals(actualLoginHeaderTitle,expectedLoginHeaderTitle,"**Login Header Title Text");
		sa.assertEquals(actualLoginDescription,expectedLoginDescription,"**Login Description Text");
		sa.assertEquals(actualLoginDescription1,expectedLoginDescription1,"**Login Description Text1");


		sa.assertAll();		
		
	}
	@Test(priority = 2, description = "AT-62326,AT-62328: Verify login card elements.")

	public void verifyLoginCardElemets() throws MessagingException, InterruptedException {

		SoftAssert sa = new SoftAssert();


		sa.assertTrue(LoginEnhancements.userNameBox().isDisplayed(),"**Username text box is not displayed!");
		
		sa.assertTrue(LoginEnhancements.passwordBox().isDisplayed(),"**Password text box is not displayed!");
		
		sa.assertTrue(LoginEnhancements.loginButton().isDisplayed(),"**Login button is not displayed!");
		
		sa.assertTrue(LoginEnhancements.forgotUserId().isDisplayed(),"**Forgot user ID link is not displayed!");
		
		sa.assertTrue(LoginEnhancements.forgotPassword().isDisplayed(),"**Forgot Password link is not displayed!");
		
		sa.assertTrue(LoginEnhancements.createAnAccount().isDisplayed(),"**Create an account link is not displayed!");
		

		sa.assertAll();		
		
	}
	
	@Test(priority = 3, description = "Type username and password, and click submit button.")

	public void enterCredentialsAndSubmit() throws MessagingException, InterruptedException {
						
		// Entering UserName and Password
		LoginEnhancements.typeTextField(LoginEnhancements.userNameBox(), SignUp_Test.userid);
		LoginEnhancements.typeTextField(LoginEnhancements.passwordBox(), PropsUtil.getDataPropertyValue("Signup_password"));
		
		// Click Login button

		SeleniumUtil.click(LoginEnhancements.loginButton());
		SeleniumUtil.waitForPageToLoad();

	}
	
	@Test(priority = 4, description = "AT-62330: Verify that OTP screen loaded on the same card should consists the text saying that the Security code has been sent to the registered mobile number and email id in masked formatd.")

	public void verifyOTPCardDescription() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		String OTPCardDesc = LoginEnhancements.otpCardDesc().getText().trim();
		
		sa.assertTrue(OTPCardDesc.contains("A security code has been sent to your"));
		
		sa.assertAll();
		
	}
	
	@Test(priority = 5, description = "AT-62331 : Verify that OTP screen loaded on the same card should also consists the Security Code field followed by Resend Verification Code link, Remember me check box with info icon, Submit and Cancel� buttons on the screens.")

	public void verifyOTPCardElements() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		sa.assertTrue(LoginEnhancements.otpCardOTPtextBox().isDisplayed(),"**OTP text box is not displayed!");
		
		sa.assertTrue(LoginEnhancements.otpCardResendCodeBtn().isDisplayed(),"**Resend OTP link is not displayed!");
		
		sa.assertTrue(LoginEnhancements.otpCardSubmitBtn().isDisplayed(),"**Submit button is not displayed!");
		
		sa.assertTrue(LoginEnhancements.otpCardCancelBtn().isDisplayed(),"**Cancel button is not displayed!");
		
		sa.assertAll();
		
	}
	
	@Test(priority = 6, description = "AT-62333 : Verify that if the user enters invalid OTP and clicks on the submit button, the invalid OTP inline error message should show.")

	public void verifyInvalidOTPerrorMessage() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		String OTP = "123456";
		
		// Enter OTP in the textbox
        LoginEnhancements.typeTextField(LoginEnhancements.otpCardOTPtextBox(), OTP);
		
		
		SeleniumUtil.click(LoginEnhancements.otpCardSubmitBtn());
		
		SeleniumUtil.waitForPageToLoad();
		
		String errorMsg = LoginEnhancements.otpCardEmailIncorrectSecurityCodeError().getText().trim();
				
		sa.assertTrue(errorMsg.contains("Incorrect security code"),"**Error is not displayed!");
		
		sa.assertAll();
		
	}
	
	@Test(priority = 7, description = "AT-62334 : Verify that when user enters OTP and clicks on the Cancel button on the card , the card should reload and show Login info again.")

	public void verifyCancelButtonFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		SeleniumUtil.click(LoginEnhancements.otpCardCancelBtn());
		
		SeleniumUtil.waitForPageToLoad(7000);
		
		sa.assertTrue(LoginEnhancements.userNameBox().isDisplayed(),"**CANCEL button did not navigate to landing page!");
		
		sa.assertAll();
	
	}
	
	@Test(priority = 8, description = "AT-62350 : Verify that if user clicks on Create an Account link on the card, the registration screen should load on different page.")

	public void verifyCreateAccountButtonFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		SeleniumUtil.click(LoginEnhancements.createAnAccount());

		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(signUp.checkBoxLginEnh1());
		SeleniumUtil.click(signUp.checkBoxLginEnh2());
		SeleniumUtil.click(signUp.checkBoxLginEnh3());
		
		SeleniumUtil.click(signUp.loginEnhAgree());

		sa.assertTrue(LoginEnhancements.registrationPageTitle().isDisplayed(),"**Create account button did not navigate to Registration page!");
		
		sa.assertAll();
		
	}
	
	@Test(priority = 9, description = "AT-62383 : Verify that when the user clicks on forgot user id link on the login card it loads the forgot user id details on the same card with the appropriate text, registered email id field followed by Continue and Cancel buttons.")

	public void verifyForgotUserIDButtonFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		demoUtil.launchDemoSite();
		
		SeleniumUtil.waitForPageToLoad();

	
		
		SeleniumUtil.click(LoginEnhancements.forgotUserId());
	
		sa.assertEquals(LoginEnhancements.forgotUserIDCardTitle().getText().trim(),PropsUtil.getDataPropertyValue("LoginEn_forgotUserIDCardHeader").trim(),"**Forgot User ID card title is wrong!");

		sa.assertEquals(LoginEnhancements.forgotUserIDCardDescription().getText().trim(),PropsUtil.getDataPropertyValue("LoginEn_forgotUserIDCardDesc").trim(),"**Forgot User ID card description is wrong!");

		
		sa.assertTrue(LoginEnhancements.forgotUserIDCardEmailBox().isDisplayed(),"**Email text box is not displayed!");
		
		sa.assertAll();
		
	}
	
	
	@Test(priority = 10, description = "AT-62385 : Verify that if the user enters a valid email id/valid User id and clicks on Continue button, the success message should appear on the card with Okay button..")

	public void verifyForgotUserIDCard_ContinueBtnFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		DSUtil.gmailDeleteAllEmails(gmailId, gmailPassword); // Delete all mails from Gmail before clicking ok.
		
		SeleniumUtil.click(LoginEnhancements.forgotUserIDCardEmailBox());
		LoginEnhancements.forgotUserIDCardEmailBox().clear();
		LoginEnhancements.forgotUserIDCardEmailBox().sendKeys("somerandomemail@gmailyeah.com");
				
		SeleniumUtil.click(LoginEnhancements.forgotUserIDCardContinueBtn());
		SeleniumUtil.waitForPageToLoad();
		
		
		String actualSubHeader = LoginEnhancements.forgotUserIDCardCongratsSubHeader().getText().trim();
		String expectedSubHeader = PropsUtil.getDataPropertyValue("LoginEn_forgotUserIDCardCongratsSubHeaderXpath").trim();
		
		sa.assertEquals(actualSubHeader, expectedSubHeader,"**Sub header does not match!");
		
		sa.assertAll();
	}
	
	
	@Test(priority = 11, description = "AT-62386 : Verify that when user clicks on Okay button under success message, the card should load back with login details again.")

	public void verifyForgotUserIDCard_OkayBtnFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
	   SeleniumUtil.click(LoginEnhancements.forgotUserIDCardCongratsNextBtn());
		
		sa.assertTrue(LoginEnhancements.userNameBox().isDisplayed(),"**Okay button did not load the login screen.");
		
		sa.assertAll();
		
	}
	
	
	@Test(priority = 12, description = "AT-62384 : Verify that if user clicks on the cancel button on forgot user id card, the card should load back with login info again.")

	public void verifyForgotUserIDCard_CancelBtnFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		SeleniumUtil.click(LoginEnhancements.forgotUserId());
		
		SeleniumUtil.click(LoginEnhancements.forgotUserIDCardCancelBtn());

		sa.assertTrue(LoginEnhancements.userNameBox().isDisplayed(),"**CANCEL button did not navigate to landing page!");
		
		sa.assertAll();
		
	}
	
	@Test(priority = 13, description = "AT-62387 : Verify that when the user clicks on forgot password link on the login card it loads the forgot password details on the same card with the appropriate text, User id/Registered email id field followed by Continue and Cancel buttons.")

	public void verifyForgotPasswordCardElements() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		SeleniumUtil.click(LoginEnhancements.forgotPassword());
		
		String actualForgotPasswordCardTitle = LoginEnhancements.forgotPasswordCardTitle().getText().trim();
		String actualForgotPasswordCardDescription = LoginEnhancements.forgotPasswordCardDescription().getText().trim();
		
		String expectedForgotPasswordCardTitle =PropsUtil.getDataPropertyValue("LoginEn_forgotPasswordCardTitle").trim();
		String expectedForgotPasswordCardDescription = PropsUtil.getDataPropertyValue("LoginEn_forgotPasswordCardDescription").trim();
		
		sa.assertEquals(actualForgotPasswordCardTitle,expectedForgotPasswordCardTitle,"**Forgot Password card title is incorrect!");
		sa.assertEquals(actualForgotPasswordCardDescription,expectedForgotPasswordCardDescription,"**Forgot Password card description is incorrect!");
		
		sa.assertTrue(LoginEnhancements.forgotPasswordCardEmailBox().isDisplayed(),"**Email/UserID box is not displayed!");
		sa.assertTrue(LoginEnhancements.forgotPasswordCardCancelBtn().isDisplayed(),"**CANCEL is not displayed!");
		sa.assertTrue(LoginEnhancements.forgotPasswordCardEmailBox().isDisplayed(),"**Email/UserID box is not displayed!");
		
		sa.assertAll();
		
	}
	
	
	@Test(priority = 14, description = "AT-62388 : Verify that if user clicks on the cancel button on forgot pasword card, the card should load back with login info again..")

	public void verifyForgotPasswordCard_CANCELbuttonFunctionality() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
				
		SeleniumUtil.click(LoginEnhancements.forgotPasswordCardCancelBtn());
		SeleniumUtil.waitForPageToLoad();
		
		sa.assertTrue(LoginEnhancements.userNameBox().isDisplayed(),"**CANCEL button did not navigate back to login card!");
		
		sa.assertAll();
		
	}
	
	@Test(priority = 15, description = "AT-62388 : Verify Forgot Password Flow: Send Forgot Password email!")

	public void verifyForgotPasswordFlow1() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		SeleniumUtil.click(LoginEnhancements.forgotPassword());

		SeleniumUtil.waitForPageToLoad();
		
		DSUtil.gmailDeleteAllEmails(gmailId, gmailPassword);
		
		LoginEnhancements.typeTextField(LoginEnhancements.forgotPasswordCardEmailBox(), SignUp_Test.userid);		
		
		SeleniumUtil.click(LoginEnhancements.forgotPasswordCardContinueBtn());

		SeleniumUtil.waitForPageToLoad();
		
		sa.assertAll();
		
	}
	
	
	@Test(priority = 16, description = "AT-62388 : Verify Forgot Password Flow. Verify success message!")

	public void verifyForgotPasswordFlow2() throws MessagingException, InterruptedException {
		
		SoftAssert sa = new SoftAssert();
		
		String actualForgotPassSuccessSubHeader = LoginEnhancements.forgotPasswordSuccessCardSubHeader().getText().trim();
		String expectedForgotPassSuccessSubHeader = PropsUtil.getDataPropertyValue("LoginEn_forgotPasswordSuccessCardSubHeader").trim();
		
		sa.assertEquals(actualForgotPassSuccessSubHeader, expectedForgotPassSuccessSubHeader,"Forgot password email sent success card sub heaedrs do not match!");
		
		sa.assertAll();
		
	}

	
	
	@Test(priority = 17, description = "AT-62388 : Verify Forgot Password Flow. Open link and validate security question page.")

	public void verifyForgotPasswordFlow3() throws Exception {
		
		SoftAssert sa = new SoftAssert();
		
		DemoSiteDBUtil.updateLoginAttempts(SignUp_Test.userid, 0);
		
		SeleniumUtil.waitForPageToLoad(10000);
		
		String passwordResetLink = DSUtil.readTextFromEmail(PropsUtil.getDataPropertyValue("LoginEn_resetPasswordLink"),PropsUtil.getDataPropertyValue("LoginEn_MailSubject"),"LINK");

		demoUtil.launchURL(passwordResetLink);
		
		String actualSecurityQuesDesc = LoginEnhancements.securityQuesDesc().getText().trim();
		String expectedSecurityQuesDesc = PropsUtil.getDataPropertyValue("LoginEn_securityQuesDesc").trim();
		
		sa.assertEquals(actualSecurityQuesDesc,expectedSecurityQuesDesc,"**Security questions description does not match!");
		
		sa.assertAll();
		
	}
	
	
	@Test(priority = 18, description = "AT-62388 : Verify Forgot Password Flow. Validate the link recieved on mail.")

	public void verifyForgotPasswordFlow4() throws Exception {
		
		SoftAssert sa = new SoftAssert();
		
		String newPasswordToChange = PropsUtil.getDataPropertyValue ("LoginEn_newPasswordToChange").trim();
		
		LoginEnhancements.typeTextField(LoginEnhancements.securityQuesAnsTextBox(), PropsUtil.getDataPropertyValue("LoginEn_Answer"));
		SeleniumUtil.click(LoginEnhancements.securityQuesContinueBtn());
		
		
		SeleniumUtil.waitForPageToLoad();
		
	 LoginEnhancements.typeTextField(LoginEnhancements.setNewPasswordPasswordBox(), newPasswordToChange);
	 
	 LoginEnhancements.typeTextField(LoginEnhancements.setNewPasswordReEnterPasswordBox(), newPasswordToChange);


		SeleniumUtil.click(LoginEnhancements.setNewPasswordContinueBtn());
		SeleniumUtil.waitForPageToLoad();
	
		String actualPasswordChangedSuccessMsgSubHeader = LoginEnhancements.passwordChangeSuccessCardSubHeader().getText().trim();
		String expectedPasswordChangedSuccessMsgSubHeader = PropsUtil.getDataPropertyValue("LoginEn_passwordChangeSuccessCardSubHeader").trim();
		
		sa.assertEquals(actualPasswordChangedSuccessMsgSubHeader, expectedPasswordChangedSuccessMsgSubHeader,"Password changed success message sub header is incorrect!");
		
		sa.assertAll();
		
	}


	
	@Test(priority = 19, description = "AT-62388 : Verify Forgot Password Flow. Validate continue button in password change success page.")

	public void verifyForgotPasswordFlow5() throws Exception {
		
		SoftAssert sa = new SoftAssert();
		SeleniumUtil.click(LoginEnhancements.passwordChangeSuccessCardContinueBtn());

		SeleniumUtil.waitForPageToLoad();
		
		sa.assertTrue(LoginEnhancements.userNameBox().isDisplayed(),"**CONTINUE button did not navigate to landing page!");
		
		sa.assertAll();
		
	}
	
	
	
	@Test(priority = 20, description = "AT-62329,AT-62332 : Verify that if the user enters valid OTP in the security code field and clicks on the submit button, the application dashboard page has to be loaded.")

	public void verifyLogin() throws Exception {
		
		SoftAssert sa = new SoftAssert();
		
		String newChangedPassword = PropsUtil.getDataPropertyValue( "LoginEn_newPasswordToChange").trim();
		
		DSUtil.gmailDeleteAllEmails(gmailId, gmailPassword);
		
		// Entering UserName and Password
		
		
		LoginEnhancements.typeTextField(LoginEnhancements.userNameBox(), SignUp_Test.userid);
		LoginEnhancements.typeTextField(LoginEnhancements.passwordBox(), newChangedPassword);

		
		SeleniumUtil.click(LoginEnhancements.loginButton());
	
		SeleniumUtil.waitForPageToLoad(15000);
		
		// Read the OTP from email via API

		String OTP = DSUtil.readTextFromEmail("//p[3]", "Security Code for Yodlee","OTP");
		
		SeleniumUtil.waitForPageToLoad();

		// Enter OTP in the OTP box
		
		LoginEnhancements.typeTextField(LoginEnhancements.otpCardOTPtextBox(), OTP);		
		SeleniumUtil.waitForPageToLoad(15000);

		// Here tests
		
		SeleniumUtil.click(LoginEnhancements.otpCardSubmitBtn());
		SeleniumUtil.waitForPageToLoad(15000);

		// validate the landing page

		String dash = LoginEnhancements.dashBoardHeader().getText();
		
		sa.assertEquals(dash, "Dashboard", "verifying the Dashboard page");

		sa.assertAll();
		
	}


}
