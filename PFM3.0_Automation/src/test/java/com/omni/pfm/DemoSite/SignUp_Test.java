/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.DemoSite;

import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SignUp_Test extends TestBase {


	private static final Logger logger = LoggerFactory.getLogger(SignUp_Test.class);
	
	SignUp_Loc SignUp;
	DemoSiteUtil demoUtil;
	public static String userid;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		
		doInitialization("Sign Up Test");
		
		userid = PropsUtil.getEnvPropertyValue("DemoSite_UNAME_PREFIX") + DemoSiteUtil.getCurrentDateTime();
		logger.info("User id generated is :"+userid);
		
		SignUp = new SignUp_Loc(d);
		demoUtil = new DemoSiteUtil();
	}

	@Test(priority = 1, description = "Sign up whole flow")

	public void SignUp() throws Exception

	{

		demoUtil.launchDemoSite();

			
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

		SignUp.secAns3().clear();
		SignUp.secAns3().sendKeys(PropsUtil.getDataPropertyValue("Signup_answer"));
		
		SeleniumUtil.click(SignUp.secureAcctPageNextBtn());
		SeleniumUtil.waitForPageToLoad(15000); 
		
		// Waiting for OTP to arrive


		String code = DemoSiteDBUtil.getVerificationCode(userid);
		SeleniumUtil.waitForPageToLoad();

		SignUp.code().clear();
		SignUp.code().sendKeys(code);
		
		SeleniumUtil.click(SignUp.verifyButton());
		SeleniumUtil.waitForPageToLoad();

	

	}

}
