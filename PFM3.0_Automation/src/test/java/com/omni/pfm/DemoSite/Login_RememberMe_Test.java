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

import com.omni.pfm.pages.DemoSite.LoginEnhancements_Loc;
import com.omni.pfm.pages.DemoSite.UserProfileSecurityQues_Loc;
import com.omni.pfm.testBase.TestBase;

import com.omni.pfm.pages.DemoSite.PasswordUserProfile_Loc;
import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.Email;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.utility.DBUtil;


public class Login_RememberMe_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(Login_RememberMe_Test.class);

	
	LoginEnhancements_Loc RememberMe;
	DemoSiteUtil demoUtil;
	DemoSiteDBUtil DBUtil;
	Email mail;
	DemoSiteUtil DSUtil;
	//TermsOfUse_Loc termsOfUseLoc;
	LoginEnhancements_Loc LoginEnhancements;
	SignUp_Loc signUp;
	public static String userid;


	String gmailId = "";
	String gmailPassword = "";
	


	
	 SoftAssert sa = new SoftAssert();

	    @BeforeClass(alwaysRun = true)

		public void testInit() throws Exception {

		doInitialization("Login RememberMe Test");

		RememberMe = new LoginEnhancements_Loc(d);
		
		demoUtil = new DemoSiteUtil();
		
		
		DBUtil = new DemoSiteDBUtil(gmailId, gmailId, gmailId);
		DSUtil = new DemoSiteUtil();
		mail=new Email();
		//termsOfUseLoc = new TermsOfUse_Loc(d);
		demoUtil = new DemoSiteUtil();
		signUp = new SignUp_Loc(d);
		LoginEnhancements = new LoginEnhancements_Loc(d);
		
		gmailId = PropsUtil.getDataPropertyValue("LoginEn_email");
		gmailPassword = PropsUtil.getDataPropertyValue( "LoginEn_Password");



}
		@Test(priority = 1, description = "AT-08854,AT-08855,AT-08856,AT-08858,AT-08861,AT-08863:Login")

		public void launchAndLogin() throws MessagingException, InterruptedException

		{

			SoftAssert sa = new SoftAssert();

			// Launch Login form and validate login btn

			//com.launchUrl("EnvDetails", "URL")
			demoUtil.launchDemoSite();
			
			
			SeleniumUtil.waitForPageToLoad();

			//com.clickByJS(loginLocators.btnLoginLandingPage);
			
			SeleniumUtil.click(RememberMe.loginButton());

			// loginLocators.btnLoginLandingPage.click();

			// loginLocators.linkHeaderLogin.click();

			// sa.assertTrue(loginLocators.btnLoginForm.isDisplayed());

			SeleniumUtil.waitForPageToLoad();

			// Enter UserName and Pwd

			//com.typeInputText(loginLocators.txtBoxUserID, SignUp_Test.userid);
			
			RememberMe.typeTextField(RememberMe.userNameBox(), SignUp_Test.userid);
			
		//	com.typeInputText(loginLocators.txtBoxPwd, PropertyLoader.getProperty("SignUp", "password"));

			RememberMe.typeTextField(RememberMe.passwordBox(), PropsUtil.getDataPropertyValue("Signup_password"));

			// Login to your email acct and delete all previous emails

			
			DSUtil.gmailDeleteAllEmails(gmailId, gmailPassword);

			SeleniumUtil.click(RememberMe.loginButton());

			/*com.loginToEmailDeleteAllEmails(PropertyLoader.getProperty("EnvDetails", "email"),
					
					PropertyLoader.getProperty("EnvDetails", "Password"));
					
					
*/
			//DSUtil.gmailDeleteAllEmails(gmailId, gmailPassword);
			// Click Login btn
			
			
			SeleniumUtil.waitForPageToLoad(15000);


			//loginLocators.btnLoginForm.click();
			// read the OTP from email

			String OTP = DSUtil.readTextFromEmail("//p[3]", "Security Code for Yodlee","OTP");
			


			// enter OTP in the textbox

			//com.typeInputText(loginLocators.txtBoxOTP, otp);
			
			
			LoginEnhancements.typeTextField(LoginEnhancements.otpCardOTPtextBox(), OTP);		
			//	commFun.typeInputText(LoginEnhancements.otpCardOTPtextBox, OTP);
				SeleniumUtil.waitForPageToLoad(15000);


				// Here tests
				
				SeleniumUtil.click(LoginEnhancements.otpCardSubmitBtn());
			//	commFun.clickByJS(LoginEnhancements.otpCardSubmitBtn);
				SeleniumUtil.waitForPageToLoad(15000);

			/*RememberMe.typeTextField(RememberMe.otpCardOTPtextBox(), OTP);

			// Here tests

			//loginLocators.btnProceed.click();
			SeleniumUtil.click(RememberMe.otpCardSubmitBtn());*/

			SeleniumUtil.waitForPageToLoad(7000);

			// validate the landing page

			String dash = RememberMe.dashBoardHeader().getText();

			sa.assertEquals(dash, "Dashboard", "verifying the Dashboard page");

			// Email object -email, ParseEmail object - parse, username, pwd,param
			// xpath

		}

		// @Test(priority = 2, description = "Logout")

		// public void LogOut() throws MessagingException, InterruptedException

		// {

		// Click Logout

		// loginLocators.logOut.click();

		// Util.waitForPageToLoad(2500);

		// assert

		// Assert.assertFalse(loginLocators.headerDashboard.isDisplayed(), "Logged
		// Out");

		// }

		
}
