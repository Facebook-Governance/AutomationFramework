/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.RealEstateAccount_Setting_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML1;
import com.omni.pfm.pages.Login.LoginPage_SAML2;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

 

public class YCOM_Regression_Real_Estate_Settings_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(YCOM_Regression_Real_Estate_Settings_Test.class);

	public AggregatedAccount_Settings_Loc account_Settings;
	public RealEstateAccount_Setting_Loc realestate_Settings;
	public static String Login;

	
	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		
		doInitialization("Real Estate Settings Login");
		account_Settings = new AggregatedAccount_Settings_Loc(d);

		realestate_Settings = new RealEstateAccount_Setting_Loc(d);

		  logger.info("Login Method for new User for both AutoNPR and MR");
		//LoginPage.loginMain(d,loginParameter);
		  
		  logger.info("Login Method for Existing User for  MR");
		//LoginPage_SAML2.loginWithExistingUserMR(d,"PFM1512639317521","Password#");
		  
		  logger.info("Login Method for Existing User for AutoNPR");
		//LoginPage_SAML1.loginExistingUser(d,"PFM1512556880539","Password#");
		//LoginPage_SAML1.loginExistingUser(d,"PFM1513161653789","Password#");
		
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	} 
	


	@Test(description = "SETT-04_01:Verify login happenes successfully.", priority = 1, groups = { "Smoke" })
	public void login() throws Exception {
		
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(7500);
		SeleniumUtil.SwitchToCurrentTab(d);
		
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE"))

		{
			//login.loginApp("testsuva", "Yodlee@123");
			LoginPage.loginMain(d,loginParameter);
		}

		else

		{
		
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.waitForPageToLoad();
				
			logger.info("Adding Manual Real Estate Account1");
			realestate_Settings.addManualRealEstateAccount("real estate 1", "1220", true);
			
			d.navigate().refresh();
			
			logger.info("Navigating to Accounts Page.");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
					
			logger.info("Adding Manual Real Estate Account2");
			realestate_Settings.addManualRealEstateAccount("real estate 38", "12345",true);
			
			SeleniumUtil.waitForPageToLoad();
			
			d.navigate().refresh();
			
			logger.info("Navigating to Accounts Page.");
			PageParser.forceNavigate("AccountsPage", d);	 
			SeleniumUtil.waitForPageToLoad();
		}

	} 

	@Test(description = "SETT-04_02:Verifying Account Settings Heading is present in the Pop up Window, when clicked on Account Settings from menu", priority = 3, groups = {
			"Smoke" })

	public void VerifyAccountSettingText() throws Exception {
		
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountText()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
	  	}
		
		
		logger.info("Clicking on More Options of Real Estate Manual Account");
		SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Getting all the account Setting options of all the container accounts.");
		List<WebElement> l = account_Settings.AccountSettingsOption();
		
		logger.info("Getting the size, to select Account settings of Real Estate manual account.");
		int s = l.size();
		int i = s - 2;
		
		logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
		SeleniumUtil.click(account_Settings.AccountSettingList(i));

		SeleniumUtil.waitForPageToLoad();

		String title = realestate_Settings.accountText().getText().trim();

		Assert.assertEquals(title, PropsUtil.getDataPropertyValue("AccountSettingsPopUpHeader").trim());
		

	}

	@Test(description = "SETT-04_03:Verifying AccountName text .", priority = 4, groups = { "Smoke" })

	public void VerifyAccountName() throws Exception {
		
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountName()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}
		

		String text = realestate_Settings.accountName().getText().trim();
		
		logger.info("Asserting Real Estate Manual AccountFloater Account Name field Text");
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("RealEstateAccountName").trim());

		String crossicon = realestate_Settings.crossIcon().getAttribute("class");
		
		logger.info("Asserting  That the close icon of the floater contains word close in it");
		Assert.assertTrue(crossicon.contains(PropsUtil.getDataPropertyValue("RealEstateCrossIcon").trim()));

	}

	@Test(description = "SETT-04_04:Verifying AccountName Box and maximum characters allowed in Account name box.", priority = 5, groups = {
			"Smoke" })

	public void VerifyAccountNameBox() throws Exception {

		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}

		
		String accountnameBoxTxt = realestate_Settings.accountNameBox().getAttribute("name").trim();

		logger.info("Asserting the Attribute of Account Name text box.");
		Assert.assertTrue(accountnameBoxTxt.contains(PropsUtil.getDataPropertyValue("AccountNameTextBoxAttribute").trim()));

		SeleniumUtil.waitForPageToLoad();

		String maxcharacter = PropsUtil.getDataPropertyValue("Max_Characters_Account_Name").trim();
		realestate_Settings.accountNameBox().sendKeys(maxcharacter);

		String name = realestate_Settings.accountNameBox().getAttribute("value");

		Assert.assertTrue(name.length() == 50);

	}

	@Test(description = "SETT-04_05:Verifying Account Nickname Text and Nickname text box.", priority = 6, groups = { "Smoke" })

	public void VerifyNickNameText() throws Exception {
		
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}


		String text1 = realestate_Settings.nickNameText().getText().trim();

		
		Assert.assertEquals(text1, PropsUtil.getDataPropertyValue("RealEstateAccountSettingsNickNameText").trim());


		String nicknameBoxTxt = realestate_Settings.nickNameBox().getAttribute("name");


		Assert.assertTrue(nicknameBoxTxt.contains(PropsUtil.getDataPropertyValue("AccountNickNameTextBoxAttribute").trim()));

	}

	@Test(description = "SETT-04_06:Verifying Account Nickname Box.", priority = 7, groups = { "Smoke" })

	public void VerifyNickNameBox() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}
		
		
		String maxnickname = PropsUtil.getDataPropertyValue("Max_Characters_Nick_Name").trim();
		realestate_Settings.nickNameBox().sendKeys(maxnickname);

		String name = realestate_Settings.nickNameBox().getAttribute("value");
		
		logger.info("Asserting the NickName textbox value length");
		Assert.assertTrue(name.length() == 40);

	}

	@Test(description = "SETT-04_07:Verifying Real Estate Value Text.", priority = 8, groups = { "Smoke" })

	public void VerifyRealEstateValueText() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}


		String realEstateValueText = realestate_Settings.realEstateValueText().trim();

		logger.info("Asserting Real Estate value");
		Assert.assertEquals(realEstateValueText, PropsUtil.getDataPropertyValue("Real_Esate_Value").trim());

	}

	@Test(description = "SETT-04_08:Verify Calculate Automatically Text .", priority = 9, groups = { "Smoke" })

	public void VerifyCalculateAutomatically() throws Exception {
		
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}

		String calculateAutomaticallyTxt = realestate_Settings.calculateAutomaticallyText().trim();	
		logger.info("Asserting the Calculate Automatically text on Real Estate Manual Account Floater.");
		Assert.assertTrue(calculateAutomaticallyTxt.contains(PropsUtil.getDataPropertyValue("RealEstateAutomaticallyCalculateText").trim()));	
		logger.info(calculateAutomaticallyTxt);

		String entervaluetxt = realestate_Settings.enterValueManuallyText();
		logger.info("Asserting the Enter home manually text on Real Estate Manual Account Floater.");
		Assert.assertTrue(entervaluetxt.contains(PropsUtil.getDataPropertyValue("RealEstateEnterHomeManuallyText").trim()));
		logger.info(entervaluetxt);

	}

	@Test(description = "SETT-04_09:Verify Calculate Using Zillow Text .", priority = 10, groups = { "Smoke" })

	public void VerifyCalculateUsingZillowText() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}

		String calculateUsingZillowTxt = realestate_Settings.calculateUsingZillowText();

		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(
				calculateUsingZillowTxt.contains(PropsUtil.getDataPropertyValue("Calculate_Using_Smart_Zip")));

	}

	@Test(description = "SETT-04_10:Verify Delete Account Functionality.", priority = 11, groups = { "Smoke" }, enabled = true)

	public void VerifyDeleteAccountFunctionality() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox()==null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();

	  	}


		Assert.assertTrue(realestate_Settings.saveChangesBtn().isDisplayed());

		realestate_Settings.deleteAccountLink().isEnabled();

		Assert.assertTrue(realestate_Settings.deleteAccountLink().isDisplayed());

		SeleniumUtil.waitForPageToLoad();

		realestate_Settings.deleteAccountLink().click();

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_11:Verify Delete Account Functionality.", priority = 12, groups = { "Smoke" }, enabled = true)

	public void VerifyDeleteAccountPopUp() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.deletePopUp() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Clicking on Delete Account option.");
			SeleniumUtil.click(realestate_Settings.deleteAccountLink());

	  	}


		SeleniumUtil.waitForPageToLoad();

		
		Assert.assertTrue(realestate_Settings.deletePopUp().getText().contains(PropsUtil.getDataPropertyValue("RealEstateDeletAccountText").trim()));

		Assert.assertTrue(realestate_Settings.deleteBtn().isDisplayed());

		Assert.assertTrue(realestate_Settings.cancelBtn().isDisplayed());

		Assert.assertTrue(realestate_Settings.deleteconfirmcheckBox().isDisplayed());

		Assert.assertTrue(!realestate_Settings.deleteconfirmcheckBox().isSelected());

		String warningMsg = account_Settings.warningMsg().getText();

		Assert.assertEquals(warningMsg, PropsUtil.getDataPropertyValue("warning_msg"));

		String deleteConfirmMessage = realestate_Settings.deleteConfirmMsg().getText();

		Assert.assertEquals(deleteConfirmMessage, PropsUtil.getDataPropertyValue("delete_confirm_msg"));

		String deleteBtnState = realestate_Settings.deleteBtn().getAttribute("class");

		
		Assert.assertTrue(deleteBtnState.contains(PropsUtil.getDataPropertyValue("RealEstateDeleteButtonStatus").trim()));

		
		SeleniumUtil.click(account_Settings.cancelBtn());

	}

	@Test(description = "SETT-04_12:Verify Property Address Text and Edit Address Pop Up.", priority = 13, groups = { "Smoke" }, enabled=true)

	public void VerifyPropertyAddressTextAndBox() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || account_Settings.automaticCalculateToggleforRealEstate() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
			

	  	}

		logger.info("Clicking on Calculate Automatically toggle on Real Estate Manual Account Floater.");
		SeleniumUtil.click(account_Settings.automaticCalculateToggleforRealEstate());

		String propertyaddresstxt = realestate_Settings.verifyPropertyAddressText();

		Assert.assertEquals(propertyaddresstxt, PropsUtil.getDataPropertyValue("Property_Address_Text"));

		

		String editaddresstxt = realestate_Settings.editAddressText();

		Assert.assertEquals(editaddresstxt, PropsUtil.getDataPropertyValue("Edit_Address_Text"));

		SeleniumUtil.waitForPageToLoad();

		realestate_Settings.ClickEditAddress();

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_13:Verify Property Address Text and Edit Address Pop Up.", priority = 14, groups = { "Smoke" }, enabled=true)

	public void VerifyEditAddressTextAndBox() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.getZipCodeText() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Clicking on Calculate Automatically toggle on Real Estate Manual Account Floater.");
			SeleniumUtil.click(account_Settings.automaticCalculateToggleforRealEstate());
			realestate_Settings.ClickEditAddress();
			
	  	}


		SeleniumUtil.waitForPageToLoad();

		String zipCode = realestate_Settings.getZipCodeText();

		Assert.assertEquals(zipCode, PropsUtil.getDataPropertyValue("Zip_Code_Text").trim());

		SeleniumUtil.waitForPageToLoad();

		String orText = realestate_Settings.getOrText();

		Assert.assertEquals(orText, PropsUtil.getDataPropertyValue("Or_Text").trim());

		SeleniumUtil.waitForPageToLoad();

		String cityAndState = realestate_Settings.getCityAndStateText();

		Assert.assertEquals(cityAndState, PropsUtil.getDataPropertyValue("City_And_State_Address_Text").trim());

		SeleniumUtil.waitForPageToLoad();

		String streetAddress = realestate_Settings.getStreetAddressText();

		Assert.assertEquals(streetAddress, PropsUtil.getDataPropertyValue("Street_Address_Text").trim());

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_014:Verify Property Address Text and Edit Address Pop Up.", priority = 15, groups = { "Smoke" }, enabled = true)

	public void VerifyEditAddressPopUp() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.zipCodeBox() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Clicking on Calculate Automatically toggle on Real Estate Manual Account Floater.");
			SeleniumUtil.click(account_Settings.automaticCalculateToggleforRealEstate());
			realestate_Settings.ClickEditAddress();
			SeleniumUtil.waitForPageToLoad();

	  	}


		String zipcodeBoxname = realestate_Settings.zipCodeBox().getAttribute("name");

		
		Assert.assertTrue(zipcodeBoxname.contains(PropsUtil.getDataPropertyValue("RealEstateManualAccountZipCode").trim()));

		SeleniumUtil.waitForPageToLoad();

		String cityAndStateBoxTxt = realestate_Settings.cityStateBox().getAttribute("name").trim();

		
		Assert.assertTrue(cityAndStateBoxTxt.contains(PropsUtil.getDataPropertyValue("RealEstateManualAccountCityState").trim()));

		SeleniumUtil.waitForPageToLoad();

		String streetAddressBoxTxt = realestate_Settings.streetAddressBox().getAttribute("name");

		
		Assert.assertTrue(streetAddressBoxTxt.contains(PropsUtil.getDataPropertyValue("RealEstateManualAccountStreetAddress").trim()));

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_15:Verify Property Address Text and Edit Address Pop Up.", priority = 16, groups = { "Smoke" }, enabled=true)

	public void VerifyEditAddressButton() throws Exception {

		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.VerifyEditAddressText() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Clicking on Calculate Automatically toggle on Real Estate Manual Account Floater.");
			SeleniumUtil.click(account_Settings.automaticCalculateToggleforRealEstate());
			realestate_Settings.ClickEditAddress();
			SeleniumUtil.waitForPageToLoad();		

	  	}

		String editAddressTxt = realestate_Settings.VerifyEditAddressText().trim();

		logger.info("Asserting the Edit Address Text");
		Assert.assertEquals(editAddressTxt, PropsUtil.getDataPropertyValue("RealEstateAddaddressFloaterHeader").trim());

		realestate_Settings.zipCodeBox().clear();

		logger.info("Entering the ZIP code value");
		realestate_Settings.zipCodeBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateManualAccountZipCodeValue").trim());

		realestate_Settings.streetAddressBox().clear();
		logger.info("Entering the value for Zip code.");
		realestate_Settings.streetAddressBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateManulAccountStreetAddressValue").trim());

		realestate_Settings.clickVerifyAddress();

		SeleniumUtil.waitForPageToLoad();

		String errorTxt = realestate_Settings.errorMessageText();
		logger.info("Asserting the error message.");
		Assert.assertTrue(errorTxt != null);

		logger.info(errorTxt);
		
		realestate_Settings.zipCodeBox().clear();
		realestate_Settings.streetAddressBox().clear();

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_16:Verify Property Address Text and Edit Address Pop Up.", priority = 17, groups = { "Smoke" }, enabled=true)

	public void VerifyEditAddress() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.zipCodeBox() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Clicking on Calculate Automatically toggle on Real Estate Manual Account Floater.");
			SeleniumUtil.click(account_Settings.automaticCalculateToggleforRealEstate());
			realestate_Settings.ClickEditAddress();
			SeleniumUtil.waitForPageToLoad();		

	  	}
		
		realestate_Settings.zipCodeBox().clear();
		realestate_Settings.streetAddressBox().clear();
		realestate_Settings.cityStateBox().clear();
		realestate_Settings.streetAddressBox();
		realestate_Settings.cityStateBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateManualAccountCityStateAddress").trim());
		realestate_Settings.streetAddressBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateManulAccountStreetAddressValue").trim());

		Assert.assertTrue(realestate_Settings.verifyAddress().isDisplayed());
		realestate_Settings.clickVerifyAddress();
		SeleniumUtil.waitForPageToLoad(5000);
		
	/*	realestate_Settings.cityStateBox().clear();
		realestate_Settings.streetAddressBox().clear();
		SeleniumUtil.click(realestate_Settings.closeAddAddressFloater());
		SeleniumUtil.waitForPageToLoad(); */

	
	}

	@Test(description = "SETT-04_17:Verify Mortage Account Text and Add Mortage Account.", priority = 18, groups = { "Smoke" })

	public void VerifyMortageAccountTextAndAdd() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.verifyMortageAccountText() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
		}
		
		String MortageAccountTxt = realestate_Settings.verifyMortageAccountText();

		Assert.assertEquals(MortageAccountTxt, PropsUtil.getDataPropertyValue("Link_Mortage_Accounts").trim());

		String text = realestate_Settings.MortageAccountBox().getText().trim();

		
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("RealEstateManualMortgageAccountSelectAccountText").trim());

		realestate_Settings.clickSelectAnAccount();

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "SETT-04_18:Verify Mortage Account Text and Add Mortage Account.", priority = 19, groups = { "Smoke" })

	public void VerifyMortageAccountText() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.SelectAnAccount() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			realestate_Settings.clickSelectAnAccount();
		}
		
		String mortageAccountTxt = realestate_Settings.verifyMortageAccount();

		logger.info("Asserting the Mortage Account Present or not");
		Assert.assertEquals(mortageAccountTxt, PropsUtil.getDataPropertyValue("RealEstateMortgageAccountText").trim()); 
		logger.info(mortageAccountTxt);

		SeleniumUtil.waitForPageToLoad();

		String addMortageAccountText = realestate_Settings.mortageAccountText();

		logger.info("Asserting the Add Mortgage Account text in the Select Accounts drop down of Real Estate Manual Account Floater.");
		Assert.assertEquals(addMortageAccountText, PropsUtil.getDataPropertyValue("Add_Mortage_Accounts_Text").trim());
		
		realestate_Settings.clickSelectAnAccount();

	}

	@Test(description = "SETT-04_19:Verify Two Radio Buttons.", priority = 20, groups = { "Smoke" }, enabled = true)

	public void verifyRadioButtons() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.accountNameBox() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
		}

		String checkbox1 = realestate_Settings.calculateAutomaticallyCheckBox().getAttribute("type");

		Assert.assertTrue(checkbox1.contains("radio"));

		SeleniumUtil.waitForPageToLoad();

		String checkBox2 = realestate_Settings.enterValueManuallyCheckBox().getAttribute("type");

		Assert.assertTrue(checkBox2.contains("radio"));

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_20:Verify Show Account In Account Summery Toggle .", priority = 21, groups = {
			"Smoke" }, enabled = true)

	public void verifyToggleButton() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.showAccountSummeryText() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
		}


		String showaccountsummerytxt = realestate_Settings.showAccountSummeryText();

		logger.info("Asserting the Account Summary text in Real Estate Manual Account Floater. ");
		Assert.assertEquals(showaccountsummerytxt, PropsUtil.getDataPropertyValue("Show_Account_Summery_Text").trim());

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_21:Verify Enter Value Manually.", priority = 22, groups = { "Smoke" }, enabled = true)

	public void VerifyEnterValueManually() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.currencyTextBox() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
		}

		String USDDollar = realestate_Settings.currencyTextBox().getText().trim();

		
		Assert.assertEquals(USDDollar, PropsUtil.getDataPropertyValue("RealEstateCurrencyText").trim());

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_22:Verify Enter Value Manually.", priority = 23, groups = { "Smoke" }, enabled = true)

	public void VerifyCurrencyField() throws Exception {
		
		d.navigate().refresh();
		int i = 0;
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.currencyTextBox() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			
		}

	
		logger.info("Clicking on Currency drop down");
		SeleniumUtil.click(realestate_Settings.currencyTextBox());

		logger.info("Selecting Honkong Dollar from Currency dropdown");
		SeleniumUtil.click(realestate_Settings.hongKongDollarText());

		SeleniumUtil.waitForPageToLoad();

		String HongKong = realestate_Settings.currencyTextBox().getText().trim();

		logger.info("Asserting Honkong Dollar Text after Selecting Honkong dollar currency from currency drop down");
		Assert.assertEquals(HongKong, PropsUtil.getDataPropertyValue("RealEstateHonkKongDollarText").trim());

		realestate_Settings.typeEstimatedValue(PropsUtil.getDataPropertyValue("RealEstateHonkkongDollarEstimatedValue").trim());

		logger.info("Saving the changes made");
		SeleniumUtil.click(realestate_Settings.saveChangesBtn());

		SeleniumUtil.waitForPageToLoad(7500);
			
		logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
		SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());
		SeleniumUtil.click(account_Settings.AccountSettingList(i));

		SeleniumUtil.waitForPageToLoad(7500);

		logger.info("Selecting Calculate Automatically from Real Estate Manual Account floater");
		
		SeleniumUtil.click(realestate_Settings.currencyTextBox());
		SeleniumUtil.click(realestate_Settings.australianDollarDollarText());

		logger.info("Saving the chages made");
		Assert.assertTrue(realestate_Settings.saveChangesBtn().isDisplayed());
		SeleniumUtil.click(realestate_Settings.saveChangesBtn());

		Thread.sleep(8000);

		d.navigate().refresh();
		
		SeleniumUtil.waitForPageToLoad();
		
		
	}

	@Test(description = "SETT-04_23:Verify Add Address Pop Up   .", priority = 24, groups = { "android" }, enabled = true)

	public void verifyAddAddressPopUp() throws Exception {
		
		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
		if(account_Settings.stopRefreshButton() != null){
			SeleniumUtil.click(account_Settings.stopRefreshButton());
			SeleniumUtil.waitForPageToLoad();
		}
		
		logger.info("Clicking on More Options of Real Estate Manual Account");
		SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Getting all the account Setting options of all the container accounts.");
		List<WebElement> l = account_Settings.AccountSettingsOption();
		
		logger.info("Getting the size, to select Account settings of Real Estate manual account.");
		int s = l.size();
		int i = s - 2;
		
		logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
		SeleniumUtil.click(account_Settings.AccountSettingList(i));

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(realestate_Settings.calculateAutomaticallyCheckBox());

		SeleniumUtil.waitForPageToLoad();

		realestate_Settings.clickAddAddress();

		SeleniumUtil.waitForPageToLoad();

		String addaddresstxt = realestate_Settings.addAddressText().trim();

		Assert.assertEquals(addaddresstxt, PropsUtil.getDataPropertyValue("Add_Address_Text").trim());

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_24:Verify Add Address Pop Up   .", priority = 25, groups = { "android" }, enabled = true)

	public void verifyAddAddressField() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.addAddress() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(realestate_Settings.calculateAutomaticallyCheckBox());
			
			SeleniumUtil.waitForPageToLoad();

			realestate_Settings.clickAddAddress();

			SeleniumUtil.waitForPageToLoad();
			
		}

		String zipcode1 = realestate_Settings.addAddressZipCodeText();

		Assert.assertEquals(zipcode1, PropsUtil.getDataPropertyValue("Zip_Code_Text").trim());

		SeleniumUtil.waitForPageToLoad();

		String cityandstatetxt = realestate_Settings.addAddresscityAndStateText();

		Assert.assertEquals(cityandstatetxt, PropsUtil.getDataPropertyValue("City_And_State_Address_Text").trim());

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_25:Verify Add Address Box   .", priority = 26, groups = { "android" }, enabled = true)

	public void verifyAddAddressBox() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.addAddress() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(realestate_Settings.calculateAutomaticallyCheckBox());
			
			SeleniumUtil.waitForPageToLoad();

			realestate_Settings.clickAddAddress();

			SeleniumUtil.waitForPageToLoad();
			
		}


		String zipcodebox = realestate_Settings.zipCode1TextBox().getAttribute("name");

		
		Assert.assertTrue(zipcodebox.contains(PropsUtil.getDataPropertyValue("RealEstateManualAccountZipCode").trim()));

		SeleniumUtil.waitForPageToLoad();

		String cityandstatetextbox = realestate_Settings.cityAndStateTextBox().getAttribute("name");

		Assert.assertTrue(cityandstatetextbox.contains(PropsUtil.getDataPropertyValue("RealEstateManualAccountCityState").trim()));

		SeleniumUtil.waitForPageToLoad();

		String streetaddresstxt = realestate_Settings.addAddressStreet();

		Assert.assertEquals(streetaddresstxt, PropsUtil.getDataPropertyValue("Street_Address_Text").trim());

		SeleniumUtil.waitForPageToLoad();

		String streetaddresstxtbox = realestate_Settings.streetAddressTextBox().getAttribute("name");

		Assert.assertTrue(streetaddresstxtbox.contains(PropsUtil.getDataPropertyValue("RealEstateManualAccountStreetAddress").trim()));

		SeleniumUtil.waitForPageToLoad();

		String orText = realestate_Settings.addAddressOrText();

		Assert.assertEquals(orText, PropsUtil.getDataPropertyValue("Or_Text"));

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "SETT-04_26:Verify Add Address Box   .", priority = 27, groups = { "android" }, enabled = true)

	public void verifyAddAddress() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage" || realestate_Settings.addAddress() == null) {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Clicking on More Options of Real Estate Manual Account");
			SeleniumUtil.click(account_Settings.realEstateAccountMoreOptions());

			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Getting all the account Setting options of all the container accounts.");
			List<WebElement> l = account_Settings.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of Real Estate manual account.");
			int s = l.size();
			int i = s - 2;
			
			logger.info("Cliking on Account settings option from More options for Manual Real estate container account.");
			SeleniumUtil.click(account_Settings.AccountSettingList(i));

			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(realestate_Settings.calculateAutomaticallyCheckBox());
			
			SeleniumUtil.waitForPageToLoad();

			realestate_Settings.clickAddAddress();

			SeleniumUtil.waitForPageToLoad();
			
		}

		String verifyaddressText = realestate_Settings.verifyAddress().getAttribute("class");

		Assert.assertTrue(verifyaddressText.contains("disabled"));

		SeleniumUtil.waitForPageToLoad();

		logger.info("Entering the Zip Code.");
		realestate_Settings.zipCode1TextBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateAddAddressZipCode").trim());

		SeleniumUtil.waitForPageToLoad();

		logger.info("Entering value for City and State");
		realestate_Settings.cityAndStateTextBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateCityAndStateTextBox").trim());

		SeleniumUtil.waitForPageToLoad();

		logger.info("Entering value for Street address.");
		realestate_Settings.streetAddressTextBox().sendKeys(PropsUtil.getDataPropertyValue("RealEstateStreetAddress").trim());

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(realestate_Settings.verifyAddressBox());

		SeleniumUtil.waitForPageToLoad();
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(7500);

	}

		
/*	@AfterTest (alwaysRun=true)
			
	public void closingSession() {
		WebElement el= SeleniumUtil.getVisibileWebElement(d, "LogOut", "AccountsPage", null);
		SeleniumUtil.click(el);
		d.get(PropsUtil.getEnvPropertyValue("cnf_base_url"));
	} */
			
	

}
