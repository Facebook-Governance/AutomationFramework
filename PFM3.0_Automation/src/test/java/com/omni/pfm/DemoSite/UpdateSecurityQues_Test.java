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

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.PasswordUserProfile_Loc;
import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.pages.DemoSite.UserProfileSecurityQues_Loc;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;





public class UpdateSecurityQues_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateSecurityQues_Test.class);

	
	PasswordUserProfile_Loc UpdatePassword;
	UserProfilePersonalInfo_Loc PersonalInfo;
	UserProfileSecurityQues_Loc SecQues;
	CreateServiceRequest_Loc CreateServiceRequest;
    SoftAssert sa = new SoftAssert();

    @BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {

	doInitialization("Update Security Ques Test");
	PersonalInfo = new UserProfilePersonalInfo_Loc(d);	


	SecQues = new UserProfileSecurityQues_Loc(d);

	CreateServiceRequest = new CreateServiceRequest_Loc(d);
}
	
	
	
    @Test(priority = 0, description = "Load Node URL")

		public void loadNodeUrl() {
    	
    	DemoSiteUtil.launchNodeURL();
    	
		SeleniumUtil.waitForPageToLoad();


    }
    
    @Test(priority = 1, description = "login to user profile")

	public void loginToUserProfile() {

		
    	PersonalInfo.typeTextField(PersonalInfo.userID(), SignUp_Test.userid);
		PersonalInfo.typeTextField(PersonalInfo.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		
		PersonalInfo.typeTextField(PersonalInfo.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(CreateServiceRequest.myProfile());
		
    }
		@Test(priority = 2, description = "AT-14031,AT-14007,AT-14008,AT-14009:Validate Landing screen")

		public void validateLandingPage() {

			SoftAssert sa = new SoftAssert();

			// Click on the sidebar

		    SeleniumUtil.click(SecQues.securityHeaderSideBar());

			SeleniumUtil.waitForPageToLoad();

			// Validate Security Tab for labels and proceed next

			String lblSideBar = SecQues.securityHeaderSideBar().getText();

			sa.assertEquals(lblSideBar, PropsUtil.getDataPropertyValue("UserProfile_SecurityHeaderSideBar"),
					"SideBar label");

			// Validate header

			String lblHeader = SecQues.securityHeader().getText();

			sa.assertEquals(lblHeader, PropsUtil.getDataPropertyValue("UserProfile_SecurityHeader"), "Header label");

			// validate message on screen 1

			String msg1 = SecQues.secPage1Msg().getText();

			sa.assertEquals(msg1, PropsUtil.getDataPropertyValue("UserProfile_SecPage1Message"), "Page 1 message");

			// validate Edit button label

			String btnEdit = SecQues.btnEdit().getAttribute("value").toString().toUpperCase();

			sa.assertEquals(btnEdit, PropsUtil.getDataPropertyValue("UserProfile_EditButton"), "Edit btn label");

			// validate edit button is enabled

			sa.assertTrue(SecQues.btnEdit().isEnabled(), "Edit button enabled");

			sa.assertAll();

		}

		@Test(priority = 3, description = "AT-14029:Validate pwd screen")

		public void validatePasswordPage() {

			SoftAssert sa = new SoftAssert();

			// Click Edit button

		    SeleniumUtil.click(SecQues.btnEdit());

			// Validate label, btn and textbox

			SeleniumUtil.waitForPageToLoad();

			String pwdMsg = SecQues.enterPwdMsg().getText();

			sa.assertEquals(pwdMsg, PropsUtil.getDataPropertyValue( "UserProfile_EnterPwdMsg"), "Enter pwd message");

			// Continue button is enabled

			sa.assertTrue(SecQues.btnContinue().isEnabled(), "Continue btn enabled");

			// label on continue btn

			String btnContinue = SecQues.btnContinue().getAttribute("value").toString().toUpperCase();

			sa.assertEquals(btnContinue, PropsUtil.getDataPropertyValue("UserProfile_ContinueButton"), "Continue btn label");

			// textbox

			sa.assertTrue(SecQues.txtBoxEnterPwd().isEnabled(), "pwd txtbox");

			sa.assertAll();

		}

		@Test(priority = 4, description = "AT-14014,AT-14028:Validate pwd screen error message")

		public void validatePasswordPageErrMessage() {

			SoftAssert sa = new SoftAssert();

			// Enter continue with pwd field blank

			SeleniumUtil.waitForPageToLoad();

		    SeleniumUtil.click(SecQues.btnContinue());

			String pwdErrMwdBlank = SecQues.errMsgOldPwdBlank().getText();

			// assert error message

			sa.assertEquals(pwdErrMwdBlank, PropsUtil.getDataPropertyValue("UserProfile_ErrMsgSecQuesPwdBlank"),
					"Blank Pwd Error msg");

			// Enter invalid pwd and hit continue

		
		    SecQues.typeTextField(SecQues.txtBoxEnterPwd(), PropsUtil.getDataPropertyValue("UserProfile_ContinueButton"));
			
		    SeleniumUtil.click(SecQues.btnContinue());
			// Explicit wait

		     WebDriverWait wait = new WebDriverWait(d, 500);
         

			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("errorMsg"), "Incorrect Password!"));

			// assert error message

			String pwdErrMsgInvalid = SecQues.errMsgWrongOldPwd().getText();

			SeleniumUtil.waitForPageToLoad();

			sa.assertEquals(pwdErrMsgInvalid, PropsUtil.getDataPropertyValue("UserProfile_ErrMsgSecQuesWrongPwd"),
					"Invalid Pwd Error msg");

			sa.assertAll();

		}

		@Test(priority = 5, description = "AT-14030:Validate ques answer screen")

		public void validateQuesAnsScreen() {

			SoftAssert sa = new SoftAssert();

			// enter valid pwd and say continue to land into sec ques and ans page

			SecQues.typeTextField(SecQues.txtBoxEnterPwd(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

			SeleniumUtil.click(SecQues.btnContinue());

			SeleniumUtil.waitForPageToLoad();

			// Capture all the labels validating tha screen

			String lblQues1 = SecQues.lblSecQues1().getText();

			String lblQues2 = SecQues.lblSecQues2().getText();

			String lblQues3 = SecQues.lblSecQues3().getText();

			String lblAns1 = SecQues.lblAns1().getText();

			String lblAns2 = SecQues.lblAns2().getText();

			String lblAns3 = SecQues.lblAns3().getText();

			String lblCancelBtn = SecQues.btnCancel().getText();

			String lblSaveBtn = SecQues.btnSave().getAttribute("value");

			// Start Asserting the labels

			sa.assertEquals(lblQues1, PropsUtil.getDataPropertyValue("UserProfile_LblSecQues1"), "Label ques 1");

			sa.assertEquals(lblQues2, PropsUtil.getDataPropertyValue("UserProfile_LblSecQues2"), "Label ques 2");

			sa.assertEquals(lblQues3, PropsUtil.getDataPropertyValue("UserProfile_LblSecQues3"), "Label ques 3");

			sa.assertEquals(lblAns1, PropsUtil.getDataPropertyValue("UserProfile_LblAnswer"), "Label answer 1");

			sa.assertEquals(lblAns2, PropsUtil.getDataPropertyValue("UserProfile_LblAnswer"), "Label answer 2");

			sa.assertEquals(lblAns3, PropsUtil.getDataPropertyValue("UserProfile_LblAnswer"), "Label answer 3");

			sa.assertEquals(lblCancelBtn, PropsUtil.getDataPropertyValue("UserProfile_BtnSecQuesCancel"),
					"Label Cancel Button");

			sa.assertEquals(lblSaveBtn, PropsUtil.getDataPropertyValue("UserProfile_BtnSecQuesSave"), "Label Save Button");

			// Check if buttons are enabled

			sa.assertTrue(SecQues.btnCancel().isEnabled());

			sa.assertTrue(SecQues.btnSave().isEnabled());

			sa.assertAll();

		}

		@Test(priority = 6, description = "AT-14486:Validate blank answer error message")

		public void validateErrorMessageforBlankAnswers() {

			SoftAssert sa = new SoftAssert();

			// Click Save button on the form

			SeleniumUtil.click(SecQues.btnSave());

			// Capture the error messages

			String err1 = SecQues.errMsgAnswer1().getText();

			String err2 = SecQues.errMsgAnswer2().getText();

			String err3 = SecQues.errMsgAnswer3().getText();

			// Assert the error messages ErrMsgBlankAnswer

			sa.assertEquals(err1, PropsUtil.getDataPropertyValue("UserProfile_ErrMsgBlankAnswer"), "error msg answer 1");

			sa.assertEquals(err2, PropsUtil.getDataPropertyValue("UserProfile_ErrMsgBlankAnswer"), "error msg answer 2");

			sa.assertEquals(err3, PropsUtil.getDataPropertyValue("UserProfile_ErrMsgBlankAnswer"), "error msg answer 3");

			sa.assertAll();

		}

		@Test(priority = 7, description = "AT-14023:Validate Cancel button on qnA page ")

		public void validateCancelButton() {

			SoftAssert sa = new SoftAssert();

			// Click Cancel button

			SeleniumUtil.click(SecQues.btnCancel());

			SeleniumUtil.waitForPageToLoad();

			// Validate user in previous screen by validating the edit button

			sa.assertTrue(SecQues.btnEdit().isEnabled(), "Edit btn is available");

			sa.assertAll();

			// Click Edit button and next steps to go back to QnA form

			SeleniumUtil.click(SecQues.btnEdit());

			SecQues.typeTextField(SecQues.txtBoxEnterPwd(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

			SeleniumUtil.click(SecQues.btnContinue());

			SeleniumUtil.waitForPageToLoad();

		}

		@Test(priority = 8, description = "AT-14024,AT-14025,AT-14026,AT-14021:Validate Update QnA")

		public void validateQuesAnsUpdate() {

			SoftAssert sa = new SoftAssert();
			
			SeleniumUtil.waitForPageToLoad();

			// Select Random questions for all 3 and generate a random string for
			// the answer fields

			SeleniumUtil.click(SecQues.dropdownSecQues1());
			Random rand = new Random();

			int value = (rand.nextInt(9)) + 1;

			// got xpath using IDE - updating ques 1 here

			d.findElement(By.xpath("//div[@id='first-form']//form/div[1]//ul/li[" + value + "]")).click();

			
			// updating ans 1 here

			String ans1 = DemoSiteUtil.getRandomString(10);


			SecQues.typeTextField(SecQues.txtBoxAnswer1(), ans1);


			SeleniumUtil.waitForPageToLoad(2500);

			// Updating ques and asnwer 2

			SeleniumUtil.click(SecQues.dropdownSecQues2());

			d.findElement(By.xpath("//div[@id='first-form']//form/div[3]//ul/li[" + value + "]")).click();
			

			String ans2 = DemoSiteUtil.getRandomString(10);

			SecQues.typeTextField(SecQues.txtBoxAnswer2(), ans2);


			SeleniumUtil.waitForPageToLoad(2500);

			// Updating ques and asnwer 3

			SeleniumUtil.click(SecQues.dropdownSecQues3());

			d.findElement(By.xpath("//div[@id='first-form']//form/div[5]//ul/li[" + value + "]")).click();

		
			String ans3 = DemoSiteUtil.getRandomString(10);

			SecQues.typeTextField(SecQues.txtBoxAnswer3(), ans3);
			
			// Click Save button

			SeleniumUtil.click(SecQues.btnSave());
			
			SeleniumUtil.waitForPageToLoad(7000);

			String str = SecQues.successMsg().getText();

			sa.assertEquals(str, PropsUtil.getDataPropertyValue("UserProfile_SecuritySuccessMessage"), "success message");

			sa.assertAll(); 

		}


}
