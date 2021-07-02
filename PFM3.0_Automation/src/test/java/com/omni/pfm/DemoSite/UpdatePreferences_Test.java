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
import com.omni.pfm.pages.DemoSite.UserProfilePreferences_Loc;
import com.omni.pfm.pages.DemoSite.DeleteMyAccount_Loc;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.DBUtil;

import com.omni.pfm.utility.Email;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;



public class UpdatePreferences_Test extends TestBase {
private static final Logger logger = LoggerFactory.getLogger(UpdatePreferences_Test.class);
	
	UserProfilePreferences_Loc Preferences;
	
	CreateServiceRequest_Loc createServiceRequest;
	SuccessPage_Loc SuccessPage;
	LandingScreen_Loc LandingScreen;
    DeleteMyAccount_Loc DeleteAccount;
	CreateServiceRequest_Loc CreateServiceRequest;
	SignUp_Loc SignUp;
	DemoSiteUtil demoUtil;
    UserProfilePersonalInfo_Loc PersonalInfo;
    
	
	SoftAssert sa = new SoftAssert();
	
	@BeforeClass(alwaysRun = true)
	
	public void testInit() throws Exception {

	doInitialization("Update Preferences Test");

	Preferences = new UserProfilePreferences_Loc(d);
	
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


	    	PersonalInfo.typeTextField(PersonalInfo.userID(), SignUp_Test.userid);
	    	PersonalInfo.typeTextField(PersonalInfo.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

			
	    	PersonalInfo.typeTextField(PersonalInfo.finappID(), "10003700");

			SeleniumUtil.click(PersonalInfo.loginButton());
			SeleniumUtil.waitForPageToLoad();
			
			SeleniumUtil.click(CreateServiceRequest.myProfile());
	    }
	@Test(priority = 2, description = "AT-14073,AT-14093,AT-14091,AT-14092:Open and check preferences")

	public void validatePreferences() {

		SoftAssert sa = new SoftAssert();

		SeleniumUtil.click(Preferences.preferencesSideHeader());
		// Validate Password Tab for labels and proceed next

		String sidebarTitle = Preferences.preferencesSideHeader().getText();

		sa.assertEquals(sidebarTitle, PropsUtil.getDataPropertyValue("UserProfile_PrefHeaderSideBar"));

		String languageTitle = Preferences.languageTextBoxHeader().getText();

		sa.assertEquals(languageTitle, PropsUtil.getDataPropertyValue("UserProfile_LanguageLabel"));

		String dateFormatTitle = Preferences.dateFormatHeader().getText();

		sa.assertEquals(dateFormatTitle, PropsUtil.getDataPropertyValue("UserProfile_DateFormatLabel"));

		String timeZoneTitle = Preferences.timeZoneFormatHeader().getText();

		sa.assertEquals(timeZoneTitle, PropsUtil.getDataPropertyValue("UserProfile_TimeZoneLabel"));

		String currencyTitle = Preferences.currencyFieldHeader().getText();

		sa.assertEquals(currencyTitle, PropsUtil.getDataPropertyValue( "UserProfile_CurrencyLabel"));

		String digitGrpSymTitle = Preferences.digitGrpSymbolHeader().getText();

		sa.assertEquals(digitGrpSymTitle, PropsUtil.getDataPropertyValue("UserProfile_DigitGroupingSymbolLabel"));

		String digitGrpTitle = Preferences.digitGrpFieldHeader().getText();

		sa.assertEquals(digitGrpTitle, PropsUtil.getDataPropertyValue("UserProfile_DigitGroupingLabel"));

		String decimalSym = Preferences.decimalFieldHeader().getText();

		sa.assertEquals(decimalSym, PropsUtil.getDataPropertyValue( "UserProfile_DecimalSymbol"));

		sa.assertAll();

	}

	@Test(priority = 3, description = "AT-14086,AT-14087,AT-14088,AT-14074:Fill values in Preferences")

	public void fillLanguage() throws InterruptedException {
		
		// Commenting the below method as Language was getting changed to others intermittently.
		
		/*Util.waitForPageToLoad();

		SoftAssert sa = new SoftAssert();

		int val = preferences.lang().size();

		preferences.LanguageTextBox.click();

		Random lang = new Random();

		int value = (lang.nextInt(val));

		String Lang1 = preferences.lang().get(value).getText();

		preferences.lang().get(value).click();

		// System.out.println(Lang1);

		preferences.SaveBtn.click();

		waitForElement(webDriver, preferences.SuccessMsg, 5000);

		 Verifying Success Message after saving values. 

		String successMsg = preferences.SuccessMsg.getText();

		sa.assertEquals(successMsg, PropsUtil.getDataPropertyValue("UserProfile", "PrefSuccessMessage"));

		String Lang2 = preferences.LanguageTextBox.getAttribute("value");

		sa.assertEquals(Lang1, Lang2);

		sa.assertAll();*/

	}

	@Test(priority = 4, description = "AT-14086,AT-14087,AT-14088,AT-14074:Fill Values for Date Format",enabled=false)

	public void fillDate() throws InterruptedException {
		
		SeleniumUtil.waitForPageToLoad();

		SoftAssert sa = new SoftAssert();

		int val1 = Preferences.datef().size();

		SeleniumUtil.click(Preferences.dateFormat());

		Random datef = new Random();

		int value1 = (datef.nextInt(val1));

		String Date1 = Preferences.datef().get(value1).getText();

		SeleniumUtil.click(Preferences.datef().get(1));

		SeleniumUtil.click(Preferences.saveBtn());

		// adding wait

		SeleniumUtil.waitForPageToLoad(5000);
		/* Verifying Success Message after saving values. */

		String successMsg = Preferences.successMsg().getText();

		sa.assertEquals(successMsg, PropsUtil.getDataPropertyValue("UserProfile_PrefSuccessMessage"));

		/* Validating Changes Below */

		String Date2 = Preferences.dateFormat().getAttribute("value");

		sa.assertEquals(Date1, Date2);

		sa.assertAll();

	}

	@Test(priority = 5, description = "AT-14086,AT-14087,AT-14088,AT-14074:Fill Values for TimeZone Format")

	public void fillTimeZone() throws InterruptedException {
		
		SeleniumUtil.waitForPageToLoad();

		SoftAssert sa = new SoftAssert();

		int val2 = Preferences.timez().size();

		// adding wait

		SeleniumUtil.waitForPageToLoad(10000);

		/// Perform action

		SeleniumUtil.click(Preferences.timeZoneFormat());

		Random timez = new Random();

		int value2 = (timez.nextInt(val2));

		String Time1 = Preferences.timez().get(value2).getText().toLowerCase();

		SeleniumUtil.click(Preferences.timez().get(value2));

		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(Preferences.saveBtn());

		/* Validating Changes Below */

		String Time2 = Preferences.timeZoneFormat().getAttribute("value").toLowerCase();

		sa.assertEquals(Time1, Time2);

		sa.assertAll();

	}

	@Test(priority = 6, description = "Fill Values for Currency Format")

	public void fillCurrency() throws InterruptedException {

		SoftAssert sa = new SoftAssert();

		int val3 = Preferences.currency().size();

		SeleniumUtil.click(Preferences.currencyField());

		Random currency = new Random();

		int value3 = (currency.nextInt(val3));

		String Currency1 = Preferences.currency().get(value3).getText();

		SeleniumUtil.click(Preferences.currency().get(value3));

		SeleniumUtil.click(Preferences.saveBtn());
		
		SeleniumUtil.waitForPageToLoad(5000);
		/* Verifying Success Message after saving values. */

		String successMsg = Preferences.successMsg().getText();

		sa.assertEquals(successMsg, PropsUtil.getDataPropertyValue("UserProfile_PrefSuccessMessage"));

		/* Validating Changes Below */

		String Currency2 = Preferences.currencyField().getAttribute("value");

		sa.assertEquals(Currency1, Currency2);

		sa.assertAll();

	}

}

	


