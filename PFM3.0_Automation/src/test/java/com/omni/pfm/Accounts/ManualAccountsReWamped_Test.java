/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import static org.testng.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FinAppUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ManualAccountsReWamped_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(ManualAccountsReWamped_Test.class);

	Accounts_ReWamped_Loc manualAccLoc;
	AccountAddition accountAdd;
	Account_ShowNoteMessage_Loc show;
	FinAppUtil finUtil;
	Accounts_AccountSettingPopUp_Loc accountSettiingsPopup;
	Accounts_ManualAccountSettingPopUp_Loc manualAcntSettingPopup;

	@BeforeClass()
	public void init() throws Exception {

		doInitialization("Account Settings With Manual Accounts");
		TestBase.tc = TestBase.extent.startTest("Login");
		TestBase.test.appendChild(TestBase.tc);

		manualAccLoc = new Accounts_ReWamped_Loc(d);
		accountAdd = new AccountAddition();
		show = new Account_ShowNoteMessage_Loc(d);
		finUtil = new FinAppUtil(d);
		accountSettiingsPopup = new Accounts_AccountSettingPopUp_Loc(d);
		manualAcntSettingPopup = new Accounts_ManualAccountSettingPopUp_Loc(d);

	}

	@Test(description = "Login Successfully", priority = 0, enabled = true)

	public void Login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
	}

	@Test(description = "AT-84192:Verify cash account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 1, enabled = true)
	public void addCashAccount() throws Exception {
		accountAdd.addManualAccount("Cash", "PayTM Wallet", null, "10001", "1111", null, null, null);
	}

	@Test(description = "Verify Card account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 2, enabled = true)
	public void addCardAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Cards", "HDFC Platinum", null, "10002", "2222", "500", "date", null);
	}

	@Test(description = "Verify Investment account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 3, enabled = true)
	public void addInvestmentAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Investments", "Zerodha Fund", null, "10003", "3333", "50", null, null);
	}

	@Test(description = "Verify Loan account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 4, enabled = true)
	public void addLoanAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Loans", "PNB Housing", null, "10004", "4444", "500", "date", null);
	}

	@Test(description = "Verify Insurance account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 5, enabled = true)
	public void addInsuranceAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Insurance", "ICICI Lombard", null, "10005", "5555", "100", "date", "asset");
	}

	@Test(description = "Verify Bill account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 6, enabled = true)
	public void addBillsAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Bills", "BESCOM", null, null, "6666", "1000", "date", null);
	}

	@Test(description = "Verify Reward account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 7, enabled = true)
	public void addRewardsAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Rewards", "SpiceJet Miles", null, "10006", "7777", "100", null, null);
	}

	@Test(description = "Verify Reward account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 8, enabled = true)
	public void addReward2Acnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Rewards", "Citrus Points", null, "10007", "8888", "200", null, null);
	}

	@Test(description = "Verify Other Assets account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 9, enabled = true)
	public void addOtherAssetsAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Other Assets", "Audi Q7", null, "10008", null, null, null, null);
	}

	@Test(description = "Verify Other Liability account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 10, enabled = true)
	public void addOtherLiabilityAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Other Liabilities", "Swins Liabilities", null, "10009", null, null, null, null);
	}

	@Test(description = "Verify cable and satellite account added succesfully.", dependsOnMethods = {
			"Login" }, priority = 11, enabled = true)
	public void addCableAndSatelliteAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Cable & Satellite", "Big TV DTH", null, "10011", "9999", "1000", "date", null);
	}

	@Test(description = "Verify Mortgage acnt added succesfully.", dependsOnMethods = {
			"Login" }, priority = 12, enabled = true)
	public void addMortgageAcnt() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		accountAdd.addManualAccount("Mortgages", "USB Gage", null, "10012", "1010", "500", "date", null);
	}

	@Test(description = "AT-84454,AT-84459,AT-84135:The currency symbol should not reflect for the Rewards account balance .", dependsOnMethods = {
			"Login", "addRewardsAcnt" }, priority = 13, enabled = true)

	public void verifyNoCrrencySymbolForRewardsAcc() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(3000);
		
		try {
			finUtil.institutionViewGetAccountBalance("SpiceJet Miles").isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(5000);
		}

		
		String rewardsBalance = finUtil.institutionViewGetAccountBalance("SpiceJet Miles").getText().trim();
		Assert.assertFalse(rewardsBalance.contains("$"));
	}

	@Test(description = "AT-84130:For the Liability type of accounts, the account balance should display in the black color with a negative sign.", dependsOnMethods = {
			"Login", "addOtherLiabilityAcnt" }, priority = 14, enabled = true)

	public void verifyNegativeSymbolForLiability() throws Exception {
		String liabilityBalance = finUtil.institutionViewGetAccountBalance("Swins Liabilities").getText().trim();
		Assert.assertTrue(liabilityBalance.contains("-"));
	}

	@Test(description = "AT-84478:Popovers should be closed by pressing on 'Escape' key from keyboard or clicking / tapping outside of the popover.", dependsOnMethods = {
			"Login", "addCashAccount" }, priority = 15, enabled = true)

	public void verifyPopUpCloseWithEscapeKey1() throws Exception {
		finUtil.intitutionViewClickOnAccountSettings("PayTM Wallet", "Cash");
		Assert.assertTrue(manualAccLoc.accountSettingsPopUpHeader().isDisplayed());
	}

	@Test(description = "Popovers should be closed by pressing on 'Escape' key from keyboard or clicking / tapping outside of the popover.", dependsOnMethods = {
			"Login", "verifyPopUpCloseWithEscapeKey1" }, priority = 16, enabled = true)

	public void verifyPopUpCloseWithEscapeKey2() throws Exception {
		Boolean isPopShown = false;
		Actions action = new Actions(d);
		action.sendKeys(Keys.ESCAPE).build().perform();
		SeleniumUtil.waitForPageToLoad();
		try {
			if (manualAccLoc.accountSettingsPopUpHeader().isDisplayed()) {
				isPopShown = true;
			}
		} catch (Exception e) {
			// Do nothing
		}
		Assert.assertFalse(isPopShown);
	}

	@Test(description = "AT-84142,AT-84142:The manual account ( Asset Account) should be displayed in Account Type view under Other Assets.", dependsOnMethods = {
			"Login", "addOtherAssetsAcnt" }, priority = 17, enabled = true)
	public void verifyAssetAccountUnderAssetContainer() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("Audi Q7", "Other Assets"),
				"**Asset is not displayed under Asset container.");
	}

	@Test(description = "AT-84145:The Account Number should display if the user provided the account number while creating the Manual account in different view types.", dependsOnMethods = {
			"Login", "addCashAccount" }, priority = 18, enabled = true)
	public void verifyAccountNumberIsDisplayed() throws Exception {
		String actualAccountNumber, expectedAccountNumber = "";
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		
		SeleniumUtil.waitForPageToLoad(3000);
		
		try {
			finUtil.institutionViewGetAccountNumber("PayTM Wallet").isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(5000);
		}

		
		actualAccountNumber = finUtil.institutionViewGetAccountNumber("PayTM Wallet").getText().trim();
		expectedAccountNumber = PropsUtil.getDataPropertyValue("mannAccTest_SwinsCashAccountName").trim();
		Assert.assertEquals(actualAccountNumber, expectedAccountNumber, "**Cash account number is incorrect!");
	}

	@Test(description = "AT-84141,AT-83975:The manual accounts like  Cell phone & wireless, Cable& Satellite, Internet, Utilities account should be displayed in Account Type view under Bills.", dependsOnMethods = {
			"Login", "addCableAndSatelliteAcnt" }, priority = 19, enabled = true)
	public void verifyCableSatelliteUnderBills() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("Big TV DTH", "Bills"),
				"**Cable and satelite account is not under Bills");
	}

	@Test(description = "AT-84139:The manual account (Insurance) should be displayed in Account Type view under Insurance.", dependsOnMethods = {
			"Login", "addInsuranceAcnt" }, priority = 20, enabled = true)
	public void verifyInsuranceAccUnderInsuranceCont() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("ICICI Lombard", "Insurance"));
	}

	@Test(description = "AT-84137:The manual account (Investment) should be displayed in Account Type view under Investments.", dependsOnMethods = {
			"Login", "addInvestmentAcnt" }, priority = 21, enabled = true)
	public void verifyInvestmentsAccUnderInvestmentCont() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("Zerodha Fund", "Investments"));
	}

	@Test(description = "AT-84136:The manual account (Loan) should be displayed in Account Type view under Loans.", dependsOnMethods = {
			"Login", "addLoanAcnt" }, priority = 22, enabled = true)
	public void verifyLoanAccUnderLoansCont() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("PNB Housing", "Loans"));
	}

	@Test(description = "AT-84134:The manual account (Bills) should be displayed in Account Type view under Bills.", dependsOnMethods = {
			"Login", "addBillsAcnt" }, priority = 23, enabled = true)
	public void verifyBillsAccUnderBillsont() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("BESCOM", "Bills"));
	}

	@Test(description = "AT-84133:The  manual account (Credits) should be  displayed in Account Type view under Cards container.", dependsOnMethods = {
			"Login", "addCardAcnt" }, priority = 24, enabled = true)
	public void verifyCreditAccUnderCardsCont() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("HDFC Platinum", "Cards"));
	}

	@Test(description = "AT-84140:The manual accounts (Mortgage) should be displayed in Account Type view under Loans.", dependsOnMethods = {
			"Login", "addMortgageAcnt" }, priority = 25, enabled = true)
	public void verifyMortgageAccUnderLoansCont() throws Exception {
		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("USB Gage", "Loans"));
	}

	@Test(description = "AT-84050,AT-84285:Verify that for the manual accounts like  bills,rewards and real estate last updated information should not reflect.", dependsOnMethods = {
			"Login" }, priority = 26, enabled = true)
	public void verifyUpdatedTimeNotShownForMannualAccnts() throws Exception {
		By lastUpdatedTime = SeleniumUtil.getByObject("AccountsPage", null, "lastUpdatedTime_mannAccTest");
		if (SeleniumUtil.isDisplayed(lastUpdatedTime, 5)) {
			fail("Last updated time is getting displayed for only manual accounts");
		}
	}

	@Test(description = "AT-84449,AT-83970:The amount should be displayed at the respective Account level for all  the containers except the container 'Rewards'.", dependsOnMethods = {
			"Login", "addBillsAcnt", "addReward2Acnt" }, priority = 27, enabled = true)
	public void verifyAccountLevelAmounts() throws Exception {
		String actualTotalBillBalance, expectedTotalBillBalance = "";
		Boolean isShown = false;
		actualTotalBillBalance = finUtil.institutionViewGetContainerLevelBalance("BESCOM", "Bills").getText().trim();
		expectedTotalBillBalance = PropsUtil.getDataPropertyValue("mannAccTest_TotalBillsBalance").trim();
		// Verifying total is shown for Bills account!
		Assert.assertEquals(actualTotalBillBalance, expectedTotalBillBalance, "**Total Bill amount do not match!");
		try {
			if (finUtil.institutionViewGetContainerLevelBalance("Citrus Points", "Rewards").isDisplayed()) {
				isShown = true;
			}
		} catch (Exception e) {

		}
		// Verifying total is not shown for Miles account!
		Assert.assertFalse(isShown, "**Totals miles rewards are also shown!");
	}

	@Test(description = "AT-84236:Verify that for manual accounts errors should not be displayed.", dependsOnMethods = {
			"Login" }, priority = 28, enabled = true)
	public void verifyNoErrorIconsForMannualAccounts() throws Exception {
		Boolean isShown = false;
		try {
			if (manualAccLoc.errorIcons().size() == 0) {
				isShown = true;
			}
		} catch (Exception e) {
		}
		Assert.assertTrue(isShown, "**Error icons are displayed for manual accounts too."); // it was assertfalse
	}

	@Test(description = "AT-84424,AT-84473,AT-84423: Verify the Disclaimer/Message should not come when user did not add investment/real estate account for all views.", dependsOnMethods = {
			"Login" }, priority = 29, enabled = true)
	public void verifyNoDisclaimerMessage() throws Exception {
		Boolean isShwon = false;
		try {
			By investMentAccountDisclaimerMessage = SeleniumUtil.getByObject("AccountsPage", null,
					"investmentAccDesclaimerMsg_mannAccTest");
			By smartZIPRealEstateAccountDisclaimerMessage = SeleniumUtil.getByObject("AccountsPage", null,
					"smartZipRealEstateDesclaimerMsg_mannAccTest");
			if (SeleniumUtil.isDisplayed(investMentAccountDisclaimerMessage, 5)
					|| SeleniumUtil.isDisplayed(smartZIPRealEstateAccountDisclaimerMessage, 5)) {
				isShwon = true;
			}
		} catch (Exception e) {
		}
		Assert.assertFalse(isShwon, "**One of the desclaimer message is shown!");
	}

	// Real Estate related

	@Test(description = "AT-84421,AT-84472,AT-84034,AT-93132,AT-93138,AT-93123,AT-93113:Verify Desclaimer message for SmartZip real estate accounts.", dependsOnMethods = {
			"Login" }, priority = 30, enabled = true)
	public void verifyRealEstateAccount() throws Exception {
		logger.info(
				" Verify the Icon and Disclaimer/Message displayed below the Accounts Screen,when user adds a real estate account with value calculated by smartzip. \"SMARTZIP LOGO | Home values provided by SmartZip Analytics, Inc 2017\"");
		manualAccLoc.addRealEstateAccountWithSmartZip("Swins RE ZIP", "1255 POTRERO AVE,SAN FRANCISCO", "CA,94110");
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(manualAccLoc.smartZipRealEstateDesclaimerMsg().isDisplayed(),
				"**Smart Zip desclaimer message is not displayed!");
		logger.info(
				"At the end of both the views the smartzip  content should display *smartzip Home values provided by SmartZip Analytics, Inc 2015*");

		String actualSmartZimDesclaimerText = manualAccLoc.smartZipRealEstateDesclaimerMsg().getText().trim();
		String expectedSmartZimDesclaimerText = PropsUtil.getDataPropertyValue("mannAccTest_SmartZimDesclaimerText")
				.trim();
		Assert.assertEquals(actualSmartZimDesclaimerText, expectedSmartZimDesclaimerText,
				"**SmartZip desclaimer text is not displayed!");
	}

	// Real Estate pop up

	@Test(description = "AT-93019,AT-93020,AT-93022,AT-93135,AT-93136,AT-93143,AT-93122:Ensure that for real estate agregated account following fields msut be shown for account settinsg pop up.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 31, enabled = true)

	public void verifyRealEstateAccountSettingsPopUp() throws Exception {

		logger.info(
				"Ensure that for real estate agregated account following fields msut be shown for account settinsg pop up : Account, Account Nike Name (Optional), Real Estate Value, Link Mortgage Accounts, Show Account in Account summary with toogle button, Delete Account text and icon, Save changes button.");

		finUtil.intitutionViewClickOnAccountSettings("Swins RE ZIP", "Real Estate");

		Assert.assertTrue(manualAccLoc.accSettingsPopUpAccName().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpAccNickName().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpRECalculateAutomaticallyRadBtn().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEnterValueRadBtn().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEditAddressBtn().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREPropertyAddress().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpRESelectAccDropDown().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpDeleteAccBtn().isDisplayed());

	}

	@Test(description = "AT-93146,AT-93024,AT-93150,AT-93151,AT-93152,AT-93153,AT-93149:verify that the edit address available in the the realestate account settings pop up should be clickable .", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 32, enabled = true)

	public void verifyRealEstateAccountSettingsPopUpEditAddressBtn1() throws Exception {

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpREEditAddressBtn());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(manualAccLoc.editAddressScreenHeader().isDisplayed());

		logger.info(
				"Verify that when user clicks on edit address in the pop up the the existing pop up should change with edit address as header, close button and back button, Zip code, City and State, Street address with relavent suggesion below the text box, Smart zip logo with clickable embeded link, \"Home values provided by © SmartZip Analytics, Inc 2018.\" disclaimer message, Verify button are available ");

		Assert.assertTrue(manualAccLoc.editAddressZipCodeTextBox().isDisplayed());
		Assert.assertTrue(manualAccLoc.editAddressCityAndStateTextBox().isDisplayed());
		Assert.assertTrue(manualAccLoc.editAddressStreetAddressTextBox().isDisplayed());
		// Assert.assertFalse(manualAccLoc.editAddressVerifyAddressBtn().isEnabled());
		Assert.assertTrue(manualAccLoc.editAddressVerifyAddressBtn().isDisplayed());

	}

	@Test(description = "AT-93157,AT-93023,AT-93155,AT-93156,AT-93206,AT-93207:verify that the edit address available in the the realestate account settings pop up should be clickable .", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 33, enabled = true)

	public void verifyRealEstateAccountSettingsPopUpEditAddressBtn2() throws Exception {

		SeleniumUtil.click(manualAccLoc.backToAccSettingsBtnFromEditAddress());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
	}

	@Test(description = "AT-93025,AT-93026:verify important notice popup after clicking on Zillo icon.", dependsOnMethods = "verifyRealEstateAccountSettingsPopUpEditAddressBtn2", priority = 34, enabled = true)

	public void verifyZiloNoticePopup() throws Exception {

		SeleniumUtil.click(manualAcntSettingPopup.ZiloLogo());
		manualAcntSettingPopup.verifyZiloNoticeContent();
		Assert.assertEquals(manualAcntSettingPopup.ZiloNoticePopupHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("ZiloNoticePopupHeader").trim());
	}

	@Test(description = "AT-93028:verify important notice popup footers", dependsOnMethods = "verifyZiloNoticePopup", priority = 35, enabled = true)

	public void verifyZiloNoticePopupFooter() throws Exception {

		Assert.assertTrue(manualAcntSettingPopup.isCancelButtonDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.isContinueButtonDisplayed());

		manualAcntSettingPopup.clickingOnCancelBtn();
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountSettingPopUpHeader").trim());
	}

	@Test(description = "AT-93027:Verify that for link mortage accounts in the real estate account settings pop up dropdown should be there with list of aggregated real estate account in it.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 36, enabled = true)

	public void verifyLinkMortgageAccDropdown() throws Exception {

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpRESelectAccDropDown());
		SeleniumUtil.waitForPageToLoad(1000);

		Assert.assertTrue(manualAccLoc.accSettingsPopUpRESelectAccDropDownValue1().isDisplayed());

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpRESelectAccDropDown());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-93029,AT-92936:Ensure that when user selects enter value radio button in account settings pop for rela estate accounts, currency and estimated value should be avilable with dropdown for currency and Text box for estimated value.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 37, enabled = true)

	public void verifyEnterValueRadioBtn() throws Exception {

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpREEnterValueRadBtn());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(manualAccLoc.accSettingsPopUpRECurrencyDropDown().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEstimatedValueTextBox().isDisplayed());

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpCloseBtn());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-84148,AT-93121,:Verify that the real estate accounts for which the value is calculated automaticaaly (using smartzip) are under SmartZip not under manual accounts.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 38, enabled = true)

	public void verifyREAccntIsUnderSmartZip() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("Swins RE ZIP", "Real Estate"));

	}

	@Test(description = "AT-93124:Ensuring the Editable text-box is present corresponding to the Account Name field.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 39, enabled = true)

	public void verifyREAccountNameIsEditable() throws Exception {

		finUtil.intitutionViewClickOnAccountSettings("Swins RE ZIP", "Real Estate");

		manualAccLoc.accSettingsPopUpAccName().clear();
		manualAccLoc.accSettingsPopUpAccName().sendKeys("sample text");

		String newText = manualAccLoc.accSettingsPopUpAccName().getAttribute("value").trim();

		Assert.assertTrue(newText.contains("sample text"));

	}

	@Test(description = "AT-93128,AT-93129,AT-93125:Verifying the maximum characters allowed for Account Name field will be 50 only.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 40, enabled = true)

	public void verifyREAccountNameMax50Chars() throws Exception {

		String randomStringOf70Char = PropsUtil.getDataPropertyValue("mannAccTest_randon70CharText");

		manualAccLoc.accSettingsPopUpAccName().clear();
		manualAccLoc.accSettingsPopUpAccName().sendKeys(randomStringOf70Char);
		SeleniumUtil.waitForPageToLoad();

		int newTextBoxSize = manualAccLoc.accSettingsPopUpAccName().getAttribute("value").trim().length();

		Assert.assertEquals(newTextBoxSize, 50, "**Name text box is accepting more than 50 chars!");

	}

	@Test(description = "AT-93126,AT-93127:Ensure that user should not be allowed to enter some spei=cial charecters like <,>, \",' etc in the account anem fields.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 41, enabled = true)

	public void verifyREAccountNameNoSpecialChars() throws Exception {

		logger.info(
				"	Verifying the characters allowed to enter in the account name fields are alphabits, numbers, some specified speical characters");

		String specialCharString = PropsUtil.getDataPropertyValue("mannAccTest_specialCharString");

		manualAccLoc.accSettingsPopUpAccName().clear();
		manualAccLoc.accSettingsPopUpAccName().sendKeys(specialCharString);

		int newTextBoxSize = manualAccLoc.accSettingsPopUpAccName().getAttribute("value").trim().length();

		Assert.assertTrue(newTextBoxSize == 0,
				"**Name text box is accepting special characteres that are not allowed!");

	}

	@Test(description = "AT-93130,AT-93131:Verifying the maximum characters allowed will be 40 only in the Account Nickname ..", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 42, enabled = true)

	public void verifyREAccountNickNameIsEditable() throws Exception {

		manualAccLoc.accSettingsPopUpAccNickName().clear();
		manualAccLoc.accSettingsPopUpAccNickName().sendKeys("sample text");

		String newText = manualAccLoc.accSettingsPopUpAccNickName().getAttribute("value").trim();

		Assert.assertTrue(newText.contains("sample text"));

	}

	@Test(description = "AT-93134:Verifying the maximum characters allowed will be 40 only in the Account Nickname .", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 43, enabled = true)

	public void verifyREAccountNickNameMax40Chars() throws Exception {

		String randomStringOf70Char = PropsUtil.getDataPropertyValue("mannAccTest_randon70CharText");

		manualAccLoc.accSettingsPopUpAccNickName().clear();
		manualAccLoc.accSettingsPopUpAccNickName().sendKeys(randomStringOf70Char);

		int newTextBoxSize = manualAccLoc.accSettingsPopUpAccNickName().getAttribute("value").trim().length();

		Assert.assertTrue(newTextBoxSize == 40, "**Name text box is accepting more than 50 chars!");

	}

	@Test(description = "AT-93133:Verifying that it should allow to enter alpha-numeric values in Account Nickname..", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 44, enabled = true)

	public void verifyREAccountNickNameNoSpecialChars() throws Exception {

		logger.info(
				"	Verifying the characters allowed to enter in the account name fields are alphabits, numbers, some specified speical characters");

		String specialCharString = PropsUtil.getDataPropertyValue("mannAccTest_specialCharString");

		manualAccLoc.accSettingsPopUpAccNickName().clear();
		manualAccLoc.accSettingsPopUpAccNickName().sendKeys(specialCharString);

		int newTextBoxSize = manualAccLoc.accSettingsPopUpAccNickName().getAttribute("value").trim().length();

		Assert.assertTrue(newTextBoxSize == 0,
				"**Nick name text box is accepting special characteres that are not allowed!");

	}

	@Test(description = "AT-93137:Verify the text below Calculate Automatically should have a disclaimer.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 45, enabled = true)

	public void verifyREAccSettPopUpCalcAcutomaticallyDesclaimer() throws Exception {

		Assert.assertTrue(manualAccLoc.accSettingsPopUpRECalculateAutomaticallyDesclaimer().isDisplayed(),
				"**Desclaimer message is not displayed!");

	}

	@Test(description = "AT-93139:Verify the text below Enter Value should be 'Enter home value manually'.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 46, enabled = true)

	public void verifyREAccSettPopUpEnterManuallyDesclaimer() throws Exception {

		String actualDesText = manualAccLoc.accSettingsPopUpREEnterValueDesclaimer().getText().trim();
		String expectedDescText = PropsUtil.getDataPropertyValue("mannAccTest_REEnterValueDesclaimer").trim();

		Assert.assertEquals(actualDesText, expectedDescText, "**Desclaimer message is not displayed!");

	}

	@Test(description = "AT-93141,AT-93140,AT-93142:Verify that Calculate Automatically should be selected by default in Real Estate Value field.'.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 47, enabled = true)

	public void verifyREAccSettPopUpCalcAutomaticallySelected() throws Exception {

		String chkBoxVal = manualAccLoc.accSettingsPopUpRECalculateAutomaticallyRadBtn1().getAttribute("aria-checked")
				.trim();

		Assert.assertEquals(chkBoxVal, "true", "**Calculate automatically is not slected by default!");
	}

	@Test(description = "AT-93144:Verify that there will be only one option corresponding to the Property Address that is either ADD ADDRESS or EDIT ADDRESS.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 48, enabled = true)

	public void verifyREAccSettPopUpEditAddressBtn() throws Exception {

		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEditAddressBtn().isDisplayed(),
				"**Edit address button is not displayed!");

	}

	@Test(description = "AT-93145:Verify that the fields will change when user selects calculate value automatically.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 49, enabled = true)

	public void verifyREAccSettPopUpEnterValueBtnFuctionality() throws Exception {

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpREEnterValueRadBtn());

		Boolean isShown = false;

		try {
			if (manualAccLoc.accSettingsPopUpREEditAddressBtn().isDisplayed()) {
				isShown = true;
			}
		} catch (Exception e) {

		}

		Assert.assertFalse(isShown, "**Edit adress button is present even after clicking on Calculate manually!");

	}

	@Test(description = "AT-93147:Verify that the following fields should disappear when user selects calculate automatically radio button Currency and Estimated Value.", dependsOnMethods = {
			"Login", "verifyRealEstateAccount" }, priority = 50, enabled = true)

	public void verifyREAccSettPopUpCalAutoBtnFuctionality() throws Exception {
		logger.info(
				"Verify that the following fields should disappear when user selects calculate automatically radio button Currency and Estimated Value");
		SeleniumUtil.click(manualAccLoc.accSettingsPopUpRECalculateAutomaticallyRadBtn());
		Boolean isShown = false;
		try {
			if (manualAccLoc.accSettingsPopUpRECurrencyDropDown().isDisplayed()) {
				isShown = true;
			}
		} catch (Exception e) {
		}
		Assert.assertFalse(isShown, "**Currency dropdown is present even after clicking on Calculate automaticaly!");
	}
}
