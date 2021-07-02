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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.PasswordUserProfile_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;






public class UpdatePassword_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdatePassword_Test.class);
	
	PasswordUserProfile_Loc UpdatePassword;
	
	UserProfilePersonalInfo_Loc PersonalInfo;
	CreateServiceRequest_Loc CreateServiceRequest;
	
	SoftAssert sa = new SoftAssert();

    @BeforeClass(alwaysRun = true)

	
	public void testInit() throws Exception {

	doInitialization("Update Password Test");

	UpdatePassword = new PasswordUserProfile_Loc(d);
	
	PersonalInfo = new UserProfilePersonalInfo_Loc(d);	

	
	CreateServiceRequest = new CreateServiceRequest_Loc(d);

    }
    


	@Test(priority = 1, description = "login to user profile")

	public void loginToUserProfile() {
		
		DemoSiteUtil.launchNodeURL();


    	PersonalInfo.typeTextField(PersonalInfo.userID(), SignUp_Test.userid);
		PersonalInfo.typeTextField(PersonalInfo.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		
		PersonalInfo.typeTextField(PersonalInfo.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad();
	}
	
		@Test(priority = 2, description = "AT-13627,AT-12360,AT-12361: Validate Edit Page and click on Edit and Validate field")
		public void validatePasswordPage() {

			// Validate Password Tab for labels and proceed next

			SeleniumUtil.click(CreateServiceRequest.myProfile());
			
			String sidebarTitle = UpdatePassword.passwordHeaderSideBar().getText();
			sa.assertEquals(sidebarTitle, PropsUtil.getDataPropertyValue("UserProfile_PasswordHeaderSideBar"),
					"Header in side bar");

			SeleniumUtil.click(UpdatePassword.passwordHeaderSideBar());
			SeleniumUtil.waitForPageToLoad();

			//SeleniumUtil.click(UpdatePassword.passwordEditBtn());
			
			SeleniumUtil.click(UpdatePassword.passwordEditBtn());

			String pwdHeaderTitle = UpdatePassword.passwordEditHeader().getText();
			sa.assertEquals(pwdHeaderTitle, PropsUtil.getDataPropertyValue("UserProfile_PasswordEditHeader"),
					"Header in update Pwd page");

			SeleniumUtil.click(UpdatePassword.pwdSaveBtn());
			
			SeleniumUtil.waitForPageToLoad(4000);


			String errorMsg1 = UpdatePassword.oldPwdError().getText();
			sa.assertEquals(errorMsg1, PropsUtil.getDataPropertyValue("UserProfile_OldPasswordErrorMessage"),
					"error msg in Current pwd");

			String errorMsg2 = UpdatePassword.newPwdError().getText();
			sa.assertEquals(errorMsg2, PropsUtil.getDataPropertyValue("UserProfile_NewPasswordErrorMessage"),
					"error msg in New pwd");

			String errorMsg3 = UpdatePassword.newPwdError1().getText();
			sa.assertEquals(errorMsg3, PropsUtil.getDataPropertyValue("UserProfile_ConfirmPasswordErrorMessage"),
					"error msg in Confirm pwd");

			String CurrentPwdLabel = UpdatePassword.curPwdLabel().getText();
			sa.assertEquals(CurrentPwdLabel, PropsUtil.getDataPropertyValue("UserProfile_CurPasswordLabel"),
					"Current pwd label");

			String NewPwdLabel = UpdatePassword.newPwdLabel1().getText();
			sa.assertEquals(NewPwdLabel, PropsUtil.getDataPropertyValue("UserProfile_NewPasswordLabel"), "New pwd label");

			String CnfPwdLabel = UpdatePassword.newPwdLabel2().getText();
			sa.assertEquals(CnfPwdLabel, PropsUtil.getDataPropertyValue("UserProfile_CnfNewPasswordLabel"),
					"Confirm pwd label");
			
			SeleniumUtil.waitForPageToLoad(3000);

			sa.assertAll();

		}

		@Test(priority = 3, description = "AT-12377: Change Password and Check ")
		public void changePassword() {
			// Change Password and validate  change back again

			
			UpdatePassword.typeTextField(UpdatePassword.oldPwdTextBox(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));
						
			UpdatePassword.typeTextField(UpdatePassword.newPwdTextBox(), PropsUtil.getDataPropertyValue("UserProfile_ChangePassword1"));

			UpdatePassword.typeTextField(UpdatePassword.cnfPwdTextBox(), PropsUtil.getDataPropertyValue("UserProfile_ChangePassword1"));

			SeleniumUtil.click(UpdatePassword.pwdSaveBtn());
			SeleniumUtil.waitForPageToLoad();

			String successMessage = UpdatePassword.changePwdSuccessMsg().getText().toString();
			sa.assertEquals(successMessage, PropsUtil.getDataPropertyValue("UserProfile_ChangePwdSuccessMsg"),
					"Update pwd success msg");

			sa.assertAll();

		}

		@Test(priority = 4, description = "AT-12377: Change Password again")
		public void changePasswordAgain() {
			// Change Password again 

			

			SeleniumUtil.waitForPageToLoad();
			
			
			
			SeleniumUtil.click(UpdatePassword.passwordEditBtn());
			
			SeleniumUtil.click(UpdatePassword.oldPwdTextBox());
			UpdatePassword.oldPwdTextBox().clear();
			
			UpdatePassword.oldPwdTextBox().sendKeys(PropsUtil.getDataPropertyValue( "UserProfile_ChangePassword1"));
			
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(UpdatePassword.newPwdTextBox());
			UpdatePassword.newPwdTextBox().clear();

			UpdatePassword.newPwdTextBox().sendKeys(PropsUtil.getDataPropertyValue( "UserProfile_Password1"));
			
						
			SeleniumUtil.click(UpdatePassword.cnfPwdTextBox());
			UpdatePassword.cnfPwdTextBox().clear();

			
			UpdatePassword.cnfPwdTextBox().sendKeys(PropsUtil.getDataPropertyValue("UserProfile_Password1"));


			SeleniumUtil.click(UpdatePassword.pwdSaveBtn());

			SeleniumUtil.waitForPageToLoad();

			String successMessage1 = UpdatePassword.changePwdSuccessMsg().getText().toString();
			sa.assertEquals(successMessage1, PropsUtil.getDataPropertyValue( "UserProfile_ChangePwdSuccessMsg"),
					"success msg comparison");

			
			sa.assertAll();


		}

	}


