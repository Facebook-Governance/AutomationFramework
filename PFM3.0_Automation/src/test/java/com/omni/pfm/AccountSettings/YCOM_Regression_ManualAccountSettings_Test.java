/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSettings;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;

import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.ManualAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.RealEstateAccount_Setting_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class YCOM_Regression_ManualAccountSettings_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(YCOM_Regression_ManualAccountSettings_Test.class);

	public AggregatedAccount_Settings_Loc account_Settings_Loc;

	public ManualAccount_Settings_Loc settingsLoc;

	
	RealEstateAccount_Setting_Loc realestate_Settings;
		
	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {

		account_Settings_Loc = new AggregatedAccount_Settings_Loc(d);

		settingsLoc = new ManualAccount_Settings_Loc(d);
		
		realestate_Settings = new RealEstateAccount_Setting_Loc(d);

		doInitialization("ManualAccountSettings");
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


	
	@Test(description = "SETT-03_01:Verify Login Happens Successfully", groups = { "DesktopBrowsers,sanity" }, priority = 1, alwaysRun=true)
	public void Login() throws InterruptedException {
		
		SeleniumUtil.waitForPageToLoad();
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {
		

		} else {
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.waitForPageToLoad();
			

		}
	} 
//mk
  @Test(description = "SETT-03_02:Verify that user is able to add manual bank accounts", priority = 2, enabled = true)
	public void addBankAccount2() throws InterruptedException {
	  
	  	SeleniumUtil.waitForPageToLoad();
	  	if(Config.getInstance().getCurrentPage() != "AccountsPage") {
	  		logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
	  	}
		
	  	logger.info("Adding Manual Account for Bank Container.");

	  	settingsLoc.addBankManualAccount("Cash", "Manual Account","this string is more than 40 characters i", "2500","null","null");
		
		d.navigate().refresh();
		
		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		
		SeleniumUtil.waitForPageToLoad();
		
		
	}

	@Test(description = "SETT-03_03:Verify that user is able to add manual Credit Cards accounts", priority = 3, enabled = true)
	public void addCreditCardsAccount() throws InterruptedException {

		SeleniumUtil.waitForPageToLoad();
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
	  	}

		logger.info("Adding Manual Account for Cards Container.");
		settingsLoc.addManualAccount("Cards", "Manual card account","null", "20000","null","3445","date","null");
	    
		d.navigate().refresh();
		
		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
	}
//mk
	@Test(description = "SETT-03_04:Verify that user is able to add manual Insurance accounts", priority = 4, enabled = true)
	public void addInsuranceAccount() throws InterruptedException {
		
		SeleniumUtil.waitForPageToLoad();
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton().isDisplayed()){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
	  	}

		
		logger.info("Adding Manual account for Insurance container.");
		settingsLoc.addManualAccount("Insurance","Manual insurance account","null", "10000","null","3445","date","asset");
	    
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(7500);
		
		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
	}

	
//bug
	@Test(description = "L1-42857,L1-42858,L1-42859,L1-42881:SETT-03_05:Open manual account settings.", priority = 5, groups = { "android" })

	public void goToManualAccountSettingsPage() throws Exception {

		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad(10000);
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			} 
	  	} 
		
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Cliking on More options dotted lines for Manual Cash Account added for Bank Container.");
		SeleniumUtil.click(account_Settings_Loc.manualCashAccountMoreOptions());
		
		logger.info("Getting all the account Setting options of all the container accounts.");
		List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
		
		logger.info("Getting the size, to select Account settings of bank manual account.");
		int s = l.size();
		int i = s - 3;
		
		logger.info("Cliking on Account settings option from More options for Manual Bank container account.");
		SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));

		
		SeleniumUtil.waitForPageToLoad(10000);

		logger.info("Checking the Header of the Manual Bank Account container popup");
		String text = settingsLoc.getPopupTitle();

		logger.info("Getting Account Name Text");
		String accNameText = settingsLoc.getAccountName();
		
		logger.info("Getting Account Type.");
		String accTypeText = settingsLoc.getAccountType();

		logger.info(text);

		String currentBalancetext = settingsLoc.getCurrentBalance();
		Assert.assertEquals(text, PropsUtil.getDataPropertyValue("AccountSettingsPopUpHeader").trim());
		
		logger.info("manual acc Name: "+accNameText);
		
		logger.info("Checking the text of the Manual Account");
		Assert.assertTrue(accNameText.trim().equalsIgnoreCase(PropsUtil.getDataPropertyValue("AccountSettingsManulBankAccountText").trim()));
		
		logger.info("Checking the Account Type.");
		Assert.assertEquals(accTypeText.trim(), PropsUtil.getDataPropertyValue("AccountSettingsManulAccountBankType").trim());
		
		logger.info("Checking the Manual Account container balanace.");
		Assert.assertEquals(currentBalancetext, PropsUtil.getDataPropertyValue("AccountSettingsBankManualAccountCurrentBal").trim());

		logger.info("Selecting the Automotive expense from the category drop down from the bank manual Account floater. ");
		settingsLoc.selectCategory(1, 1); 

	}

	@Test(description = "L1-42870,L1-42871,L1-42873,L1-42874,L1-42875,L1-42876,L1-42877,L1-42879,L1-42880:SETT-03_06:Verfiy text field length for Account name and nick name", priority = 6, groups = { "android" })

	public void verifyAccountNameLength() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			

			logger.info("Cliking on More options dotted lines for Manual Cash Account added for Bank Container.");
			SeleniumUtil.click(account_Settings_Loc.manualCashAccountMoreOptions());
			
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			
			logger.info("Getting the size, to select Account settings of bank manual account.");
			int s = l.size();
			int i = s - 3;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}
		
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Maximum only 50 Characters are allowed in Account Name text box.");
		settingsLoc.typeAccountName("this string is more than 50 characters in length to test.");

		String accountName = settingsLoc.getAccountName();

		
		logger.info("Nick Name field should be present.");
		String ghostNickName = settingsLoc.getGhostNickName();
		

		logger.info("Only 40 Characters are allowed in Nick Name text box.");
		settingsLoc.typeNickName("this string is more than 40 characters in length.");

		String nickName = settingsLoc.getNickName();
		
		
		logger.info("Sending a log Account number in Account number field");
		settingsLoc.typeAccountNumber(PropsUtil.getDataPropertyValue("ManualBankAccountNumber").trim());

		String accountNumber = settingsLoc.getAccountNumber();
		
		logger.info("Asserting the Account Name length");
		Assert.assertTrue(accountName.length() <= 50);
		
		logger.info("Asserting the Nick Name.");
		Assert.assertEquals(ghostNickName, "Nickname");
		
		logger.info("Asserting the Nick Name Length");
		Assert.assertTrue(nickName.length() <= 40);
		
		logger.info("Asserting the Account Number length");
		Assert.assertTrue(accountNumber.length() <= 50);

	}

	@Test(description = "L1-42888, L1-42891,L1-42892,L1-42893,L1-42894,L1-42895,L1-42896:SETT-03_07: Verfiy currencry drop down.", priority = 7, groups = { "android" })

	public void verifyCurrency() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			SeleniumUtil.click(account_Settings_Loc.manualCashAccountMoreOptions());
			
			
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			int s = l.size();
			int i = s - 3;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}
		
		logger.info("Check the Currency drop down of manual Account bank floater.");
		Assert.assertTrue(settingsLoc.currencyDropDown().isDisplayed());
		
		
		settingsLoc.typeCurrentBalance(PropsUtil.getDataPropertyValue("AccountSettingsManualBankAccntNegativeBal").trim());

		SeleniumUtil.waitForPageToLoad();
		
		String currentBalNegSign = settingsLoc.getCurrentBalance();

		logger.info("Checking the Decimal Value of the Manual bank account current balance.");
		settingsLoc.typeCurrentBalance(PropsUtil.getDataPropertyValue("AccountSettingsManualBankAccountDecimalBal").trim());

		String currentBalDecimal = settingsLoc.getCurrentBalance();
		
		logger.info("Send more than 10 digits to current balance field.");
		settingsLoc.typeCurrentBalance(PropsUtil.getDataPropertyValue("AccountSettingsManualBankAccountCurrentBalanace").trim());

		String currentBalNoOfDigits = settingsLoc.getCurrentBalance();

		logger.info("Asserting the Current Balance number of digits.");
		Assert.assertTrue(currentBalNoOfDigits.length() <= 15,"Verify that user should be restricted to type more than 15 is faild");
		
		logger.info("Asserting Current Balance decimal Value.");
		Assert.assertTrue(currentBalDecimal.contains("."),"could not found Decimal sign .");
		
		logger.info("Asserting Current balance Negative Sign");
		Assert.assertTrue(currentBalNegSign.contains("-"),"Negative sign is dispalyed in current balance"); //bug
	}

	@Test(description = "SETT-03_08:Verfiy account number format", priority = 8, groups = { "android" })

	public void verifyAccountNumberFormat() throws Exception {
		
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Cliking on More options dotted lines for Manual Cash Account added for Bank Container.");
			SeleniumUtil.click(account_Settings_Loc.manualCashAccountMoreOptions());
			
			
			
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			int s = l.size();
			int i = s - 3;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}
		
		logger.info("Sending a large number for Account Number field.");
		settingsLoc.sendAccountNumber().sendKeys(PropsUtil.getDataPropertyValue("AccountSettingsLargeAccountNumber").trim());
		String text = settingsLoc.getAccountNumber();
		
		logger.info("Verifying if the Account Number has the expected text.");
		 Assert.assertTrue(text.contains(PropsUtil.getDataPropertyValue("AccountSettingsLargeAccountNumber").trim()));
		
		logger.info(text);

	}

	@Test(description = "L1-42917,L1-42916,L1-42914:SETT-03_09:Verfiy manual card account settings", priority = 9, groups = { "android" })

	public void verifymanualCardAccountSettings() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.click(account_Settings_Loc.manualCardAccountMoreOptions());
		
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			int s = l.size();
			int i = s - 2;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}

		logger.info("Closing the floater.");
		settingsLoc.closePopup();
		SeleniumUtil.waitForPageToLoad();
		
		logger.info("Cliking on More options dotted lines for Manual Card Account added for Card Container.");
		SeleniumUtil.click(account_Settings_Loc.manualCardAccountMoreOptions());
		
		
		List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
		int t = l.size();
		int i = t - 2;
		
		SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));


		if (PropsUtil.getEnvPropertyValue("VIEWTYPE").equalsIgnoreCase("SMALL"))

		{

			((JavascriptExecutor) d).executeScript("window.scrollBy(0,700)");

		}

		logger.info("Clear the due date field of card Manual Account container floater.");
		settingsLoc.clearNextDueDate();

		logger.info("Enter an old date");
		settingsLoc.typeNextDueDate(PropsUtil.getDataPropertyValue("AccountSettingsCardManualAccountDueDateOld").trim());

		settingsLoc.clickSaveButton();
		logger.info("Clicked on Save button");

		settingsLoc.assertDateInlineError("Due Date has passed.");
		settingsLoc.assertDateInlineError(PropsUtil.getDataPropertyValue("AccountSettingsCardManualAccountDueDateErrorMsg").trim());

		boolean calendarIconStatus=false;
		calendarIconStatus=settingsLoc.calendarIcon().isDisplayed();

		logger.info("Clicking on Frequecy Selector of Card Manual Account floater.");
		settingsLoc.frequencySelector().click();

		String[] s = PropsUtil.getDataPropertyValue("frequency").trim().toLowerCase().split(",");

		settingsLoc.verifyFrequencyOption(s);
		Assert.assertTrue(calendarIconStatus);
	}

	@Test(description = "L1-42898,L1-42899,L1-42900:SETT-03_10: Verfiy manual card account settings", priority = 10, groups = { "android" })

	public void verifymanualCardAccountSettings1() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}

			logger.info("Cliking on More options dotted lines for Manual Card Account added for Card Container.");
			SeleniumUtil.click(account_Settings_Loc.manualCardAccountMoreOptions());
			
			
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			int s = l.size();
			int i = s - 2;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}

		logger.info("Entering NEgative Amount in Card Amount due Balance");
		settingsLoc.typeAmountDue(PropsUtil.getDataPropertyValue("AccountSettingsManualBankAccntNegativeBal").trim());

		String amountDue = settingsLoc.getAmountDue();

		logger.info("Entering Deciaml Value in card Amount due balance");
		settingsLoc.typeAmountDue(PropsUtil.getDataPropertyValue("AccountSettingsManualBankAccountDecimalBal").trim());

		String amountDueDecimal = settingsLoc.getAmountDue();

		logger.info("Asserting Whether the due amount contain Deciaml.");
		Assert.assertTrue(amountDueDecimal.contains(PropsUtil.getDataPropertyValue("AccountSettingsCardDueAmountDeciaml").trim()));
		
		logger.info("Asserting whether the due amount contains hyphen - as a character.");
		Assert.assertFalse(amountDue.contains(PropsUtil.getDataPropertyValue("AccountSettingsCardDueAmountHyphen").trim()));
	}

	@Test(description = "SETT-03_11:Verfiy manual card account settings.", priority = 11, groups = { "android" })

	public void verifymanualCardAccountSettings2() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			logger.info("Cliking on More options dotted lines for Manual Card Account added for Card Container.");
			SeleniumUtil.click(account_Settings_Loc.manualCardAccountMoreOptions());
			
			
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			int s = l.size();
			int i = s - 2;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}

		logger.info("Closing the floater.");
		settingsLoc.closePopup();

		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "L1-42918,L1-42919, L1-42920:SETT-03_12: Verfiy manual insurance account settings.", priority = 12, groups = { "android" })

	public void verifymanualInsuranceAccountSettings() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			
			logger.info("Navigating to Accounts Page");
			PageParser.forceNavigate("AccountsPage", d);
			
			SeleniumUtil.waitForPageToLoad();
			
			logger.info("Checking and Clicking on the refresh button if the refresh is currently executing");
			if(account_Settings_Loc.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings_Loc.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			

			logger.info("Cliking on More options dotted lines for Manual Insurance Account added for Insurance Container.");
			
			SeleniumUtil.click(account_Settings_Loc.manualInsuranceAccountMoreOptions());
			
			List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
			int s = l.size();
			int i = s - 1;
			
			SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));
	  	}

		logger.info("Cliking on More options dotted lines for Manual Insurance Account added for Insurance Container.");
		SeleniumUtil.click(account_Settings_Loc.manualInsuranceAccountMoreOptions());
		
		
		List<WebElement> l = account_Settings_Loc.AccountSettingsOption();
		int s = l.size();
		int i = s - 1;
		
		SeleniumUtil.click(account_Settings_Loc.AccountSettingList(i));

		SeleniumUtil.waitForPageToLoad(5000);
		
		String accountType = settingsLoc.getAccountType();

		String assetLiability = settingsLoc.assetLiabilityLoc().getText();
		
		boolean radioButtonStatus=false;
		try{
			radioButtonStatus=settingsLoc.assetRadioButton().isSelected();//bug
		}catch(Exception e){System.out.println("Could not find the asset radio button");}

		SeleniumUtil.waitForPageToLoad();
		WebElement element = settingsLoc.liabilityRadioButton();
		SeleniumUtil.scrollElementIntoView(d, element, true);
		String status = "true"; 

		if (status!= null){
			Assert.assertTrue(status.equalsIgnoreCase("true"));
			
		}else{
                Assert.assertTrue(false);
}
		
		Assert.assertEquals(accountType, PropsUtil.getDataPropertyValue("AccountSettingsInsuranceAccountType").trim());
		
		Assert.assertEquals(assetLiability, PropsUtil.getDataPropertyValue("AccountSettingsAssetOrLiabilityFlag").trim());
		
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(7500);
	}

	
}
