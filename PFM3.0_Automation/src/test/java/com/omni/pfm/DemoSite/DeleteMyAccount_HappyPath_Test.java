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



public class DeleteMyAccount_HappyPath_Test extends TestBase {
	
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

	/* Commenting as signup already done 
	 * @Test(priority = 1, description = "Sign up whole flow")

	public void SignUp() throws Exception

	{

		demoUtil.launchDemoSite();

		//com.clickByJS(SignUp.createAnAccountButton);
		//DemoSiteUtil.waitForPageToLoad();
		
		SeleniumUtil.click(SignUp.createAnAccountButton());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(SignUp.checkBoxLginEnh1());
		SeleniumUtil.click(SignUp.checkBoxLginEnh2());
		SeleniumUtil.click(SignUp.checkBoxLginEnh3());

		SeleniumUtil.click(SignUp.loginEnhAgree());
		SeleniumUtil.waitForPageToLoad();

		// FILL USER DETAILS FORM

		// Enter first and last name (new screen)

		SignUp.firstName().clear();
		SignUp.firstName().sendKeys(PropsUtil.getDataPropertyValue("Signup_firstname"));;
		
		SignUp.lastName().clear();
		SignUp.lastName().sendKeys(PropsUtil.getDataPropertyValue("Signup_lastname"));;
		
		SignUp.editEmailID().clear();
		SignUp.editEmailID().sendKeys(PropsUtil.getDataPropertyValue("Signup_email"));;
		
		SignUp.userID().clear();
		SignUp.userID().sendKeys(userid);

		
		SignUp.password().clear();
		SignUp.password().sendKeys(PropsUtil.getDataPropertyValue("Signup_password"));;
		

		SignUp.confirmPassword().clear();
		SignUp.confirmPassword().sendKeys(PropsUtil.getDataPropertyValue("Signup_password"));;
		
		SignUp.mobileNum().clear();
		SignUp.mobileNum().sendKeys(PropsUtil.getDataPropertyValue("Signup_mobileNum"));;
		

		SeleniumUtil.click(SignUp.nextButton());
		SeleniumUtil.waitForPageToLoad();

		// SECURE ACCOUNT PAGE STARTS

		Select ques1 = new Select(SignUp.secQues1());
		ques1.selectByIndex(1);

		// add answer 1

		SignUp.secAns1().clear();
		SignUp.secAns1().sendKeys(PropsUtil.getDataPropertyValue("Signup_answer"));
		
		Select ques2 = new Select(SignUp.secQues2());
		ques2.selectByIndex(1);

		// add answer 2

		SignUp.secAns2().clear();
		SignUp.secAns2().sendKeys(PropsUtil.getDataPropertyValue("Signup_answer"));
		
		Select ques3 = new Select(SignUp.secQues3());
		ques3.selectByIndex(2);

		// add answer 2

		//com.typeInputText(SignUp.secans3, PropertyLoader.getProperty("SignUp", "Signup_answer"));
		SignUp.secAns3().clear();
		SignUp.secAns3().sendKeys(PropsUtil.getDataPropertyValue("Signup_answer"));
		
		// Click NEXT button

		//com.clickByJS(SignUp.secureacctpagenextbtn);
		SeleniumUtil.click(SignUp.secureAcctPageNextBtn());
		SeleniumUtil.waitForPageToLoad(15000); // Waiting for OTP to arrive

		// SECURE ACCOUNT PAGE ENDS

		String code = DemoSiteDBUtil.getVerificationCode(userid);
		SeleniumUtil.waitForPageToLoad();

		//com.typeInputText(SignUp.code1, code);
		SignUp.code().clear();
		SignUp.code().sendKeys(code);
		
		//com.clickByJS(SignUp.verifyButton);
		SeleniumUtil.click(SignUp.verifyButton());
		SeleniumUtil.waitForPageToLoad();

		
	}*/

	

	@Test(priority = 2, description = "Login nand delete")

	public void loginToDeleteAccount() {

		SoftAssert sa = new SoftAssert();

		// Login through dummy url to skip Login using OTP
		DemoSiteUtil.launchNodeURL();

		SeleniumUtil.waitForPageToLoad();

		PersonalInfo.typeTextField(PersonalInfo.userID(), SignUp_Test.userid);
    	PersonalInfo.typeTextField(PersonalInfo.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		
    	PersonalInfo.typeTextField(PersonalInfo.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(CreateServiceRequest.myProfile());
		// Start steps for delete acct

		
	
		SeleniumUtil.click(DeleteAccount.linkDeleteAcct());

		SeleniumUtil.waitForPageToLoad();

		DeleteAccount.typeTextField(DeleteAccount.txtboxPassword(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));
		
		SeleniumUtil.click(DeleteAccount.btnContinue());
		
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(DeleteAccount.btnDeleteAcct());

		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(DeleteAccount.btnDeletePopup());

		SeleniumUtil.waitForPageToLoad();

		// validate GoodBye page

		// validate header
		/*String header = DeleteAccount.msgHeader().getText();
		sa.assertEquals(header, PropsUtil.getDataPropertyValue("headerGoodBye"), "Header Good Bye page");

		// validate message
		String message = DeleteAccount.msgDeleteConfirmation().getText();
		sa.assertEquals(message, PropsUtil.getDataPropertyValue("msgDeleteSuccess"),
				"Message of delete confirmation");

		// validate button
		String btn = DeleteAccount.btnDone().getText();
		sa.assertEquals(btn, PropsUtil.getDataPropertyValue("btnOkay"), "Done button");


		String mem = null, loginName = null;

		// SQL queries to fetch UserName and Mem ID
		String sqlMem = "select mem_id from mem where login_name like '%" + userid + "%'";
		String sqlLoginName = "select login_name from mem where login_name like '%" + userid + "%'";

		try {
			mem = DemoSiteDBUtil.exSelectQueryforSingleRecord(sqlMem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loginName = DemoSiteDBUtil.exSelectQueryforSingleRecord(sqlLoginName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// concatenate userID and MemID for deleted user
		String s = userid.concat("~").concat(mem);
		sa.assertTrue(s.equals(loginName));

		sa.assertAll();*/

	}

	/*Commenting as methods changed
	 * @Test(priority = 3, description = "Back to Login after deleting account")

	public void backToLogin() {

		SoftAssert sa = new SoftAssert();

		SeleniumUtil.click(DeleteAccount.btnDone());

		SeleniumUtil.waitForPageToLoad(5000);

		

		sa.assertAll();

	}
	*/
}
