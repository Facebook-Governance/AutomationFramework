/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.DemoSite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.LandingScreen_Loc;
import com.omni.pfm.pages.DemoSite.SuccessPage_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class SuccessPage_Test extends TestBase {

private static final Logger logger = LoggerFactory.getLogger(SuccessPage_Test.class);

	SuccessPage_Loc SuccessPage;
	LandingScreen_Loc LandingScreen;

	CreateServiceRequest_Loc CreateServiceRequest;
	
	UserProfilePersonalInfo_Loc PersonalInfo;
	DemoSiteUtil demoUtil;

    @BeforeClass(alwaysRun = true)

	
	public void testInit() throws Exception {

	doInitialization("Success Page Test");
	
	LandingScreen = new LandingScreen_Loc(d);
	
	SuccessPage = new SuccessPage_Loc(d);
	
	CreateServiceRequest = new CreateServiceRequest_Loc(d);
	
	PersonalInfo = new UserProfilePersonalInfo_Loc(d);

}
	@Test(priority = 1, description = "Sign Up into the service request finapp")

	public void signup() {

		DemoSiteUtil.launchNodeURL();

		SeleniumUtil.waitForPageToLoad();

		SuccessPage.typeTextField(LandingScreen.userID(), SignUp_Test.userid);

		SuccessPage.typeTextField(LandingScreen.password(), PropsUtil.getDataPropertyValue("UserProfile_Password"));

		
		SuccessPage.typeTextField(LandingScreen.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad();

		CreateServiceRequest.navigateToServiceRequests();

		
		SeleniumUtil.click(LandingScreen.createNewButton());
		SeleniumUtil.waitForPageToLoad();

		SuccessPage.typeTextField(CreateServiceRequest.textBoxSubject(),

				PropsUtil.getDataPropertyValue("ServiceSP_Subject"));

		SuccessPage.typeTextField(CreateServiceRequest.textBoxDescription(),

				PropsUtil.getDataPropertyValue("ServiceSP_Description"));

		SeleniumUtil.click(CreateServiceRequest.submitButton());
	}
/* Commenting has flow got changed
	@Test(priority = 2, description = "AT-14204,AT-15207:Verifying all the text displayed in the screen")

	public void verifyText() {

		SeleniumUtil.waitForPageToLoad();

		SoftAssert s_assert = new SoftAssert();

		s_assert.assertEquals(SuccessPage.text1().getText(),PropsUtil.getDataPropertyValue("ServiceSP_Text1"),"First line of Text");

		s_assert.assertEquals(SuccessPage.text2().getText(), PropsUtil.getDataPropertyValue("ServiceSP_Text2"),"Second line of Text");

		s_assert.assertEquals(SuccessPage.statusText().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_StatusText"), "Status Label");

		s_assert.assertEquals(SuccessPage.statusValue().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_StatusValue"), "Status Value");

		s_assert.assertEquals(SuccessPage.submittedText().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_SubmittedText"), "Submitted on Label");

		s_assert.assertEquals(SuccessPage.submittedValue().getText(), DemoSiteUtil.dateTimeSetter(),
				"Submitted on Value");

		s_assert.assertEquals(SuccessPage.subjectText().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_SubjectText"), "Subject Label");

		s_assert.assertEquals(SuccessPage.subjectValue().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_Subject"), "Subject Value");

		s_assert.assertEquals(SuccessPage.descriptionText().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_DescriptionText"), "Description Label");

		s_assert.assertEquals(SuccessPage.descriptionValue().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_Description"), "Description Value");

		String browser = "browser :\n" + DemoSiteUtil.getBrowserVersion().toLowerCase();

		String os = "operating system :\n" + DemoSiteUtil.getOSType().toLowerCase();

		String SystemDetailsValue = browser + "\n" + os;

		s_assert.assertEquals(SuccessPage.systemDetailsText().getText(),
				PropsUtil.getDataPropertyValue("ServiceSP_SystemDetailsText"), "System Details Label");

		s_assert.assertEquals(SuccessPage.systemDetailsValue().getText().toLowerCase(), SystemDetailsValue,
				"System Details Value");

		s_assert.assertAll();

	}*/

}


