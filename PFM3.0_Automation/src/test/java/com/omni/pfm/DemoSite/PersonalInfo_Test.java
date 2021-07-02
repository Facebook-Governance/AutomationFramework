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

public class PersonalInfo_Test extends TestBase{
	
	private static final Logger logger = LoggerFactory.getLogger(PersonalInfo_Test.class);
	
	SuccessPage_Loc SuccessPage;
	LandingScreen_Loc LandingScreen;
    DeleteMyAccount_Loc DeleteAccount;
	CreateServiceRequest_Loc CreateServiceRequest;
	SignUp_Loc SignUp;
	DemoSiteUtil demoUtil;
    UserProfilePersonalInfo_Loc PersonalInfo;
    
    @BeforeClass(alwaysRun = true)
    public void testIint() throws Exception{
    	
    	doInitialization("User Profile PersonalInfo Test");  
    	PersonalInfo = new UserProfilePersonalInfo_Loc(d);	
    	CreateServiceRequest = new CreateServiceRequest_Loc(d);
    }
    
    @Test(priority = 0, description = "Load Node URL")
    
	public void loadNodeUrl() {
    	
    	DemoSiteUtil.launchNodeURL();
    	
		SeleniumUtil.waitForPageToLoad();


    }
    @Test(priority = 1, description = "login to user profile")

	public void loginToUserProfile() {

		// Login through dummy url to skip Login using OTP

	 	PersonalInfo.typeTextField(PersonalInfo.userID(), SignUp_Test.userid);
		PersonalInfo.typeTextField(PersonalInfo.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		
		PersonalInfo.typeTextField(PersonalInfo.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(CreateServiceRequest.myProfile());

	}

	@Test(priority = 2, description = "AT-13665:Validate page for labels and buttons")

	public void validateUserProfilePage() {

		SoftAssert sa = new SoftAssert();

		// Validate personal Info page for labels and textboxes

		String sidebarTitle = PersonalInfo.personalInfoHeaderSideBar().getText();

		sa.assertEquals(sidebarTitle, PropsUtil.getDataPropertyValue("UserProfile_PersonalInfoSideBar"), "sideBarTitle");

		String mainTitle = PersonalInfo.personalInfoHeader().getText();

		sa.assertEquals(mainTitle, PropsUtil.getDataPropertyValue("UserProfile_PersonalInfoHeader"), "Main Header");

		String fNameLbl = PersonalInfo.lblFirstName().getText();

		sa.assertEquals(fNameLbl, PropsUtil.getDataPropertyValue("UserProfile_FirstNameLabel"), "First Name Label");

		String lNameLbl = PersonalInfo.lblLastName().getText();

		sa.assertEquals(lNameLbl, PropsUtil.getDataPropertyValue("UserProfile_LastNameLabel"), "Last Name Label");

		String emailLbl = PersonalInfo.lblEmail().getText();

		sa.assertEquals(emailLbl, PropsUtil.getDataPropertyValue("UserProfile_EmailLabel"), "Email Label");

		String cntryLbl = PersonalInfo.lblCountry().getText();

		sa.assertEquals(cntryLbl, PropsUtil.getDataPropertyValue("UserProfile_CountryLabel"), "Country Label");

		String mobLbl = PersonalInfo.lblMobile().getText();

		sa.assertEquals(mobLbl, PropsUtil.getDataPropertyValue("UserProfile_MobileLabel"), "Mobile Label");

		String msg1 = PersonalInfo.chckBoxShareInfo().getText();

		sa.assertEquals(msg1, PropsUtil.getDataPropertyValue("UserProfile_shareMsg"), "Share message");

		String msg2 = PersonalInfo.chckBoxRecvEmail().getText();

		sa.assertEquals(msg2, PropsUtil.getDataPropertyValue("UserProfile_emailMsg"), "eMail Message");

		String btnCan = PersonalInfo.btnCancel().getText();

		sa.assertEquals(btnCan, PropsUtil.getDataPropertyValue("UserProfile_ResetButton"), "Reset Button");

		String btnSave = PersonalInfo.btnSave().getAttribute("value").toUpperCase();

		sa.assertEquals(btnSave, PropsUtil.getDataPropertyValue("UserProfile_SaveButton"), "Save Button");

		sa.assertTrue(PersonalInfo.btnCancel().isEnabled(), "Cancel btn enabled");

		sa.assertTrue(PersonalInfo.btnSave().isEnabled(), "Save btn enabled");

		sa.assertAll();

		

	}

	// add test for saving checkboxes in db, error message color

	@Test(priority = 3, description = "AT-14167: Validate error messages for Blank Fields")

	public void validateErrorMessagesforBlankFields() {

		SoftAssert sa = new SoftAssert();

		// Clearing values in all fields

		SeleniumUtil.click(PersonalInfo.txtBoxFirstName());

		PersonalInfo.txtBoxFirstName().clear();

		SeleniumUtil.click(PersonalInfo.txtBoxLastName());

		PersonalInfo.txtBoxLastName().clear();
		
		SeleniumUtil.click(PersonalInfo.txtBoxEmail());

		PersonalInfo.txtBoxEmail().clear();
		
		SeleniumUtil.click(PersonalInfo.txtBoxMobile());

		PersonalInfo.txtBoxMobile().clear();
		
		
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(PersonalInfo.btnSave());
		// start validating error messages for Blank fields

		String msgFName = PersonalInfo.errMsgBlankFname().getText();

		sa.assertEquals(msgFName, PropsUtil.getDataPropertyValue("UserProfile_errorMsgBlankField"), "Blank First Name ");

		sa.assertTrue(PersonalInfo.errMsgBlankFname().isDisplayed(), "First Name error displayed");

		String msgLName = PersonalInfo.errMsgBlankLname().getText();

		sa.assertEquals(msgLName, PropsUtil.getDataPropertyValue("UserProfile_errorMsgBlankField"), "Blank Last Name");

		sa.assertTrue(PersonalInfo.errMsgBlankLname().isDisplayed(), "Last Name error displayed");

		String msgEmail = PersonalInfo.errMsgBlankEmail().getText();

		sa.assertEquals(msgEmail, PropsUtil.getDataPropertyValue("UserProfile_errorMsgBlankField"), "Blank email");

		sa.assertTrue(PersonalInfo.errMsgBlankEmail().isDisplayed(), "eMail error displayed");

		String msgMob = PersonalInfo.errMsgBlankMobile().getText();

		sa.assertEquals(msgMob, PropsUtil.getDataPropertyValue("UserProfile_errorMsgBlankField"), "Blank mobile");

		sa.assertTrue(PersonalInfo.errMsgBlankMobile().isDisplayed(), "Mobile error displayed");

		sa.assertAll();

	}

	@Test(priority = 4, description = "AT-12205,AT-14168: validate error message for invalid values")

	public void ValidateErrorMsgInvalidValues() {

		SoftAssert sa = new SoftAssert();

		// Enter invalid values in email and Mobile Number


		PersonalInfo.typeTextField(PersonalInfo.txtBoxEmail(),PropsUtil.getDataPropertyValue("UserProfile_invalidEmail"));

		PersonalInfo.typeTextField(PersonalInfo.txtBoxMobile(),PropsUtil.getDataPropertyValue("UserProfile_invalidMobile"));

		// Click save

		SeleniumUtil.click(PersonalInfo.btnSave());
		// Start validating error messages

		String msgInvEmail = PersonalInfo.errMsgInvalidEmail().getText();

		sa.assertEquals(msgInvEmail, PropsUtil.getDataPropertyValue("UserProfile_errorMsgInvalidEmail"),
				"Invalid email");

		sa.assertTrue(PersonalInfo.errMsgInvalidEmail().isDisplayed(), "Invalid email error displayed");

		String msgInvMob = PersonalInfo.errMsgInvalidMobile().getText();

		sa.assertEquals(msgInvMob, PropsUtil.getDataPropertyValue("UserProfile_errorMsgInvalidMobile"),
				"Invalid Mobile");

		sa.assertTrue(PersonalInfo.errMsgInvalidEmail().isDisplayed(), "Invalid mobile error displayed");

		SeleniumUtil.click(PersonalInfo.btnCancel());
		sa.assertAll();

	}

	@Test(priority = 5, description = "AT-12192, AT-12193:Validate changes in personal Info")

	public void UpdatePersonalInfoForm() throws InterruptedException {

		SoftAssert sa = new SoftAssert();

		String fName = DemoSiteUtil.getRandomString(10);

		PersonalInfo.typeTextField(PersonalInfo.txtBoxFirstName(), fName);
		
		String lName = DemoSiteUtil.getRandomString(10);

		PersonalInfo.typeTextField(PersonalInfo.txtBoxLastName(), lName);

		SeleniumUtil.click(PersonalInfo.dropDownCountry());
		Random rand = new Random();

		int value = (rand.nextInt(4)) + 1;

		d.findElement(By.xpath("//ul[@id='g-dropdown']/li[" + value + "]//span")).click();

		SeleniumUtil.click(PersonalInfo.chckBoxRecvEmail());

		SeleniumUtil.click(PersonalInfo.btnSave());

		SeleniumUtil.waitForPageToLoad(5000);	
		
		sa.assertTrue(PersonalInfo.successMsg().isDisplayed());

		String msg = PersonalInfo.successMsg().getText();

		sa.assertEquals(msg, PropsUtil.getDataPropertyValue( "UserProfile_PersonalInfosuccessMessage"));

		SeleniumUtil.waitForPageToLoad();

		sa.assertAll();

	}
	
	@Test(priority = 5, description = "Verify Verification screen")

	public void verifyEmailID() throws Exception {

		SoftAssert sa = new SoftAssert();

		PersonalInfo.typeTextField(PersonalInfo.txtBoxEmail(), PropsUtil.getDataPropertyValue( "UserProfile_NewEmail"));
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(PersonalInfo.btnSave());
		SeleniumUtil.waitForPageToLoad();
		
		sa.assertEquals(PersonalInfo.emailIDHeader().getText(), PropsUtil.getDataPropertyValue("UserProfile_EmailIDHeader"), "Email ID Header");

		sa.assertEquals(PersonalInfo.emailIDText1().getText(), PropsUtil.getDataPropertyValue("UserProfile_EmailIDText1"), "Email ID Text 1");

		sa.assertEquals(PersonalInfo.emailIDText2().getText(), PropsUtil.getDataPropertyValue("UserProfile_EmailIDText2"), "Email ID Text 2");

		sa.assertEquals(PersonalInfo.emailIDLabel().getText(), PropsUtil.getDataPropertyValue("UserProfile_EmailIDLabel"), "Email ID Label");

		sa.assertEquals(PersonalInfo.verificationField().getAttribute("placeholder"), PropsUtil.getDataPropertyValue("UserProfile_VerificationPlaceholder"), "Verification Placeholder");

		sa.assertEquals(PersonalInfo.confirmButton().getText(), PropsUtil.getDataPropertyValue("UserProfile_ConfirmButton"), "Confirm Button");

		sa.assertEquals(PersonalInfo.resendLink().getText(), PropsUtil.getDataPropertyValue("UserProfile_ResendLink"), "Resend Link");

		sa.assertEquals(PersonalInfo.changeLink().getText(), PropsUtil.getDataPropertyValue("UserProfile_ChangeLink"), "Change Link");

		String code = DemoSiteDBUtil.getVerificationCode(SignUp_Test.userid);
		
		SeleniumUtil.waitForPageToLoad();
		
		PersonalInfo.typeTextField(PersonalInfo.verificationField(), code);
		SeleniumUtil.click(PersonalInfo.verificationCodeLabel());
		SeleniumUtil.click(PersonalInfo.confirmButton());

		sa.assertAll();

	}



}
