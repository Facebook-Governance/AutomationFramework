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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.DeleteMyAccount_Loc;
import com.omni.pfm.pages.DemoSite.LandingScreen_Loc;
import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.pages.DemoSite.SuccessPage_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DemoSiteUtil;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;





public class LandingScreen_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(LandingScreen_Test.class);

	

	
	
	SuccessPage_Loc SuccessPage;
	LandingScreen_Loc LandingScreen;
    DeleteMyAccount_Loc DeleteAccount;
	CreateServiceRequest_Loc CreateServiceRequest;
	SignUp_Loc SignUp;
	DemoSiteUtil demoUtil;
    UserProfilePersonalInfo_Loc PersonalInfo;

    
    @BeforeClass(alwaysRun = true)
	
	public void testInit() throws Exception {

	doInitialization("Landing Screen Test");
	
	LandingScreen = new LandingScreen_Loc(d);
	PersonalInfo = new UserProfilePersonalInfo_Loc(d);	
	CreateServiceRequest = new CreateServiceRequest_Loc(d);
}
	
	@Test(priority = 1, description = "Sign Up into the service request finapp")

	public void signup()

	{

		
		DemoSiteUtil.launchNodeURL();
		SeleniumUtil.waitForPageToLoad();

		LandingScreen.typeTextField(LandingScreen.userID(), SignUp_Test.userid);
		LandingScreen.typeTextField(LandingScreen.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		
		LandingScreen.typeTextField(LandingScreen.finappID(), "10003700");

		SeleniumUtil.click(PersonalInfo.loginButton());
		SeleniumUtil.waitForPageToLoad(5000);
		
		SeleniumUtil.click(CreateServiceRequest.supportButton());

		SeleniumUtil.click(CreateServiceRequest.serviceRequests());

		Assert.assertTrue(LandingScreen.createNewBtn().isDisplayed(), "Page loaded");

	}

	@Test(priority = 2, description = "AT-14180:Verify Header Text")

	public void verifyHeaderText()

	{

		Assert.assertEquals(LandingScreen.createNewBtn().getText(),
				PropsUtil.getDataPropertyValue("ServiceLS_CreateNewButton"));

	}

	@Test(priority = 3, description = "AT-14181:Verify Create New Button Text")

	public void verifyCreateNewButton()

	{

		Assert.assertEquals(LandingScreen.createNewButton().getText(),
				PropsUtil.getDataPropertyValue("ServiceLS_CreateNewButton"));
		//Servicelandingscreen

		SeleniumUtil.click(LandingScreen.createNewButton());
		Assert.assertTrue(CreateServiceRequest.submitButton().isDisplayed(), "If it navigated to the next screen");

	}

}


