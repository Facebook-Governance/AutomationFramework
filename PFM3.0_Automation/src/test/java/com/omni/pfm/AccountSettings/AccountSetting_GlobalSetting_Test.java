/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.AccountSettings;

import java.awt.AWTException;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.AccountSharing.AccountSharing_Loc;
import com.omni.pfm.Accounts.Accounts_AccountSettingPopUp_Loc;
import com.omni.pfm.Accounts.Accounts_DeleteAccount_Loc;
import com.omni.pfm.Accounts.Accounts_ManualAccountSettingPopUp_Loc;
import com.omni.pfm.Accounts.Accounts_ReWamped_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountSetting_GlobalSetting_Test extends TestBase {
	Logger logger=LoggerFactory.getLogger(AccountSetting_GlobalSetting_Test.class);

	LoginPage login;
	AccountAddition accountAdd;
	Accounts_AccountSettingPopUp_Loc accountSettiingsPopup;
	Accounts_ManualAccountSettingPopUp_Loc manualAcntSettingPopup;
	AccountsSetting_GlobalSettings_Loc globalSetting;
	Accounts_DeleteAccount_Loc deleteAcnt;
	Accounts_ReWamped_Loc manualAccLoc;
	AccountSharing_Loc accountSharing;


	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login=new LoginPage();
		accountAdd=new AccountAddition();
		accountSettiingsPopup=new Accounts_AccountSettingPopUp_Loc(d);
		manualAcntSettingPopup =new Accounts_ManualAccountSettingPopUp_Loc(d);
		globalSetting=new AccountsSetting_GlobalSettings_Loc(d);
		deleteAcnt=new Accounts_DeleteAccount_Loc(d);
		manualAccLoc=new Accounts_ReWamped_Loc(d);
		accountSharing=new AccountSharing_Loc(d);
	}

	@Test(description="creating user and adding account.", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void loginAndAddAccounts() throws Exception
	{
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("swin.site16441.2", "site16441.2");
	}

	@Test(description="Adding manual account and real estate account", groups = { "Regression" },
			priority = 2,enabled = true)
	public void addingManualAcnt() throws InterruptedException, AWTException {
		accountAdd.addManualAccount("Cash","MyAccountbank","null", "10001", "12345","null","null","null");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="Adding manual account and real estate account", groups = { "Regression" },
			priority = 3,enabled = true)
	public void addingrealestateAcnt() throws InterruptedException, AWTException {
		accountAdd.addManualRealEstateAccount("RealEstateManual", "2000000", true);
		PageParser.forceNavigate("AccountsPage", d);	
		SeleniumUtil.waitForPageToLoad(6000);
	}

	@Test(description = "Adding SmartZip real estate account", priority = 4,enabled=true)

	public void addAgregatedRealEstateAccount() throws Exception {

		logger.info(" Verify the Icon and Disclaimer/Message displayed below the Accounts Screen,when user adds a real estate account with value calculated by smartzip. \"SMARTZIP LOGO | Home values provided by SmartZip Analytics, Inc 2017\"");

		manualAccLoc.addRealEstateAccountWithSmartZip("Swins RE ZIP", "1255 POTRERO AVE,SAN FRANCISCO", "CA,94110");
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(manualAccLoc.smartZipRealEstateDesclaimerMsg().isDisplayed(),"**Smart Zip desclaimer message is not displayed!");
	}


	@Test(description="AT-93117,AT-75954,AT-75952:verifying Account setting icon at each account level",
	dependsOnMethods={"addingManualAcnt","addingrealestateAcnt","addAgregatedRealEstateAccount"}, priority = 5, enabled = true)
	public void navigatingToPopup() throws Exception{
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();	

		Assert.assertEquals(globalSetting.settingIconList().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("SettingIconSize").trim()));
	}

	@Test(description = "AT-75950,AT-75951:Ensure that containers within the FI are shown in the below mentioned order: Cash, Cards, Investments, Loans, Insurance, Bills, Rewards."
			,dependsOnMethods={"addingManualAcnt","addingrealestateAcnt","addAgregatedRealEstateAccount"}, priority = 6,enabled = true)
	public void verifyOrderOfAcntContainer() throws InterruptedException {

		globalSetting.verifyOrderOfAcntContainer("Account_Type1");
	}


	//***************** Verifying Account Setting popup For Aggregated Real Estate Account in Account Settings **********
	@Test(description = "AT-93116,AT-75976,AT-75977,AT-92904:Ensure that for real estate agregated account following fields msut be shown for account settinsg pop up.", 
			dependsOnMethods="addAgregatedRealEstateAccount",priority = 7,enabled=true)

	public void verifyRealEstateAccountSettingsPopUp() throws Exception {

		globalSetting.clickingOnSmartZipAcnts();
		SeleniumUtil.click(globalSetting.getSettingByAcntName("realestate").get(0));

		logger.info("Ensure that for real estate agregated account following fields msut be shown for account settinsg pop up : Account, Account Nike Name (Optional), Real Estate Value, Link Mortgage Accounts, Show Account in Account summary with toogle button, Delete Account text and icon, Save changes button.");		
		Assert.assertTrue(manualAccLoc.accSettingsPopUpAccName().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpAccNickName().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpRECalculateAutomaticallyRadBtn().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEnterValueRadBtn().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEditAddressBtn().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREPropertyAddress().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpRESelectAccDropDown().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpDeleteAccBtn().isDisplayed());	
	}


	@Test(description = "AT-75978,AT-75979:Verify Bydefault Calculate automatically radio button should be selected for aggregated real estate account", 
			dependsOnMethods="addAgregatedRealEstateAccount",priority = 8,enabled=true)
	public void verifyCheckedRBForARSAcnt() throws Exception {

		Assert.assertTrue(manualAcntSettingPopup.CalculateAutomaticallyRB().getAttribute("class").trim().contains("r_on"));	
	}



	@Test(description = "AT-75980,AT-75981,AT-75979:verify that the edit address available in the the realestate account settings pop up should be clickable .", 
			dependsOnMethods="addAgregatedRealEstateAccount",priority = 9,enabled=true)

	public void verifyRealEstateAccountSettingsPopUpEditAddressBtn1() throws Exception {

		SeleniumUtil.click(manualAccLoc.accSettingsPopUpREEditAddressBtn());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(manualAccLoc.editAddressScreenHeader().isDisplayed());

		logger.info("Verify that when user clicks on edit address in the pop up the the existing pop up should change with edit address as header, close button and back button, Zip code, City and State, Street address with relavent suggesion below the text box, Smart zip logo with clickable embeded link, \"Home values provided by © SmartZip Analytics, Inc 2018.\" disclaimer message, Verify button are available ");

		Assert.assertTrue(manualAccLoc.editAddressZipCodeTextBox().isDisplayed());
		Assert.assertTrue(manualAccLoc.editAddressCityAndStateTextBox().isDisplayed());
		Assert.assertTrue(manualAccLoc.editAddressStreetAddressTextBox().isDisplayed());
		Assert.assertTrue(manualAccLoc.editAddressVerifyAddressBtn().isDisplayed());

	}

	@Test(description = "verify that the edit address available in the the realestate account settings pop up should be clickable .", 
			dependsOnMethods="addAgregatedRealEstateAccount",priority = 10,enabled=true)

	public void verifyRealEstateAccountSettingsPopUpEditAddressBtn2() throws Exception {

		SeleniumUtil.click(manualAccLoc.backToAccSettingsBtnFromEditAddress());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(), PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));	
	}

	@Test(description = "AT-75982:verify important notice popup after clicking on Zillo icon.",dependsOnMethods="addAgregatedRealEstateAccount",
			priority = 11,enabled=true)

	public void verifyZiloNoticePopup() throws Exception {

		SeleniumUtil.click(manualAcntSettingPopup.ZiloLogo());
		manualAcntSettingPopup.verifyZiloNoticeContent();
		Assert.assertEquals(manualAcntSettingPopup.ZiloNoticePopupHeader().getText().trim(), PropsUtil.getDataPropertyValue("ZiloNoticePopupHeader").trim());	
	}

	@Test(description = "AT-75985,AT-75983:verify important notice popup footers",dependsOnMethods="addAgregatedRealEstateAccount",
			priority = 12,enabled=true)

	public void verifyZiloNoticePopupFooter() throws Exception {

		Assert.assertTrue(manualAcntSettingPopup.isCancelButtonDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.isContinueButtonDisplayed());

		manualAcntSettingPopup.clickingOnCancelBtn();
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(), PropsUtil.getDataPropertyValue("AccountSettingPopUpHeader").trim());
	}

	//********************** Verify account Setting popup for manual real estate account in global setting finapp ***************************
	@Test(description = "AT-75978,AT-75984,AT-75986,AT-92904:Ensure that when user selects enter value radio button in account settings pop for rela estate accounts, currency and estimated value should be avilable with dropdown for currency and Text box for estimated value.", 
			dependsOnMethods="addingrealestateAcnt",priority = 13,enabled=true)

	public void verifyEnterValueRadioBtn() throws Exception {

		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		globalSetting.clickingOnManualAcnts();
		SeleniumUtil.click(globalSetting.getSettingByAcntName("realestate").get(0));

		Assert.assertTrue(manualAcntSettingPopup.EnterManuallyRB().getAttribute("class").trim().contains("r_on"));	

		Assert.assertTrue(manualAccLoc.accSettingsPopUpRECurrencyDropDown().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpREEstimatedValueTextBox().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpRESelectAccDropDown().isDisplayed());
		Assert.assertTrue(manualAccLoc.accSettingsPopUpDeleteAccBtn().isDisplayed());
	}

	// ******************* Verify account setting popup for Bills in global setting finapp *********************
	@Test(description = "AT-75975,AT-92904:Verify For bills accounts under account settings popup following fields must be shown Account, Account Nick name(optional) with text box", groups = { "Regression" }, 
			dependsOnMethods="loginAndAddAccounts",priority = 14,enabled = true)
	public void verifyBillsPopUp() {
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		globalSetting.clickingOnDagSiteAcnt();
		SeleniumUtil.click(globalSetting.getSettingByAcntName("bills").get(0));

		Assert.assertTrue(accountSettiingsPopup.acntNickNameTxtBox().isDisplayed());	
		Assert.assertTrue(accountSettiingsPopup.memoTxtBox().isDisplayed());	
		Assert.assertTrue(accountSettiingsPopup.acntNameValue().isDisplayed());
		Assert.assertEquals(accountSettiingsPopup.acntNameLabel().getText().trim(),PropsUtil.getDataPropertyValue("AccountNameLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.acntNickNameLabel().getText().trim(),PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.memoLabel().getText().trim(),PropsUtil.getDataPropertyValue("AcntMemoLabel").trim());

	}

	@Test(description = "AT-75975:Verify For bills accounts nder account settings popup following fields must be shown Show account in Account summery with toogle button, close account icon and text, delete account icon and text, save changes", groups = { "Regression" }, 
			dependsOnMethods="loginAndAddAccounts",priority = 15,enabled = true)
	public void verifyBillsButtons() {

		Assert.assertTrue(accountSettiingsPopup.closeAcntBtn().isDisplayed(), "close Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.DeleteBtn().isDisplayed(), "delete Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.saveChangesBtn().isDisplayed(), "Save Button is not visible in popup.");	
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryLabel().getText().trim(),PropsUtil.getDataPropertyValue("ShowAcntInAcntSummary").trim());		
	}

	@Test(description = "AT-75975:Verify that for bills containers, apply tags and categories to past transactions tag should be removed", groups = { "Regression" }, 
			dependsOnMethods="loginAndAddAccounts",priority = 16,enabled = true)
	public void verifyToggleBtnInBills() {

		boolean isVisible=false;
		try {
			if(accountSettiingsPopup.applyCatToPastToggle().isDisplayed() || accountSettiingsPopup.applyTagToPastToggle().isDisplayed()) {
				isVisible=true;
			}	

		}catch(Exception e){

		}
		Assert.assertFalse(isVisible);
	}

	// ******************* Verify account setting popup for rewards in global setting finapp *********************

	@Test(description = "AT-75975,AT-92904:Verify For rewards accounts under account settings popup following fields must be shown Account, Account Nick name(optional) with text box", groups = { "Regression" }, 
			dependsOnMethods="loginAndAddAccounts",priority = 17,enabled = true)
	public void verifyRewardsPopUp() {
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.click(globalSetting.getSettingByAcntName("miles").get(0));

		logger.info("Ensure that in Edit Account popup the Account Number must be shown in Masked format except the last 4 digits");
		Assert.assertEquals(accountSettiingsPopup.acntNameLabel().getText().trim(),PropsUtil.getDataPropertyValue("AccountNameLabel").trim());

		Assert.assertTrue(accountSettiingsPopup.acntNickNameTxtBox().isDisplayed());	
		Assert.assertTrue(accountSettiingsPopup.memoTxtBox().isDisplayed());	
		Assert.assertTrue(accountSettiingsPopup.acntNameValue().isDisplayed());
		Assert.assertEquals(accountSettiingsPopup.acntNickNameLabel().getText().trim(),PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.memoLabel().getText().trim(),PropsUtil.getDataPropertyValue("AcntMemoLabel").trim());
	}

	@Test(description = "AT-75975:Verify For rewards accounts nder account settings popup following fields must be shown Show account in Account summery with toogle button, close account icon and text, delete account icon and text, save changes", groups = { "Regression" }, 
			dependsOnMethods="loginAndAddAccounts",priority = 18,enabled = true)
	public void verifyRewardsButtons() {

		Assert.assertTrue(accountSettiingsPopup.closeAcntBtn().isDisplayed(), "close Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.DeleteBtn().isDisplayed(), "delete Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.saveChangesBtn().isDisplayed(), "Save Button is not visible in popup.");	
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryLabel().getText().trim(),PropsUtil.getDataPropertyValue("ShowAcntInAcntSummary").trim());		
	}

	@Test(description = "AT-75975:Verify that for rewards containers, apply tags and categories to past transactions tag should be removed", groups = { "Regression" }, 
			dependsOnMethods="loginAndAddAccounts",priority = 19,enabled = true)
	public void verifyToggleBtnInRewards() {

		boolean isVisible=false;
		try {
			if(accountSettiingsPopup.applyCatToPastToggle().isDisplayed() || accountSettiingsPopup.applyTagToPastToggle().isDisplayed()) {
				isVisible=true;
			}

		}catch(Exception e) {

		}
		Assert.assertFalse(isVisible);
	}


	// ******************* Verify account setting popup for manual account in global setting finapp *********************

	@Test(description = "AT-75974,AT-92904:Verify left side labels in Account settings pop up for manual accounts user", groups = { "Regression" }, 
			dependsOnMethods="verifyRewardsPopUp",priority = 20,enabled = true)
	public void verifyLeftLabelsForManualAcnt() {

		logger.info("verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		globalSetting.clickingOnManualAcnts();
		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(0));

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualCatAllTransactionLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualCatAllTransactionLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntTagAllTransactnLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntTagAllTransactnLbl").trim());
	}

	@Test(description = "AT-75974:Verify left side labels in Account settings pop up for manual accounts user", groups = { "Regression" }, 
			dependsOnMethods="verifyLeftLabelsForManualAcnt",priority = 21,enabled = true)
	public void verifyLeftLabelsForManualAcnt2() {

		logger.info("verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(), PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-75974:Verify left side labels in Account settings pop up for manual accounts user", groups = { "Regression" }, 
			dependsOnMethods="verifyLeftLabelsForManualAcnt",priority = 22,enabled = true)
	public void verifyAllBtnsForManualAcnt() {


		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());	
	}

	@Test(description = "AT-76031:verify close account should not be there for manual account.", groups = { "Regression" }, 
			dependsOnMethods="verifyLeftLabelsForManualAcnt",priority = 23,enabled = true)
	public void verifyCloseBtnInManualAcnt() {

		boolean isVisible=false;
		try {
			if(accountSettiingsPopup.closeAcntBtn().isDisplayed()) {
				isVisible=true;
			}

		}catch(Exception e) {

		}
		Assert.assertFalse(isVisible);
	}

	// ******************* Verify account setting popup for aggregated account in global setting finapp *********************


	@Test(description = "AT-75973,AT-75944,AT-75970:verify popup fields Account, Account NickName(optional), Categorize all transaction to (optional)Tag all transactions as(optional), Close Account, Delete account link and Save change button. ", groups = { "Regression" }, 
			dependsOnMethods="verifyLeftLabelsForManualAcnt",priority = 24,enabled = true)
	public void verifyEditAcntpopUpLeftLabels() {
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		globalSetting.clickingOnDagSiteAcnt();
		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(0));

		Assert.assertEquals(accountSettiingsPopup.acntNameLabel().getText().trim(),PropsUtil.getDataPropertyValue("AccountNameLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.acntNickNameLabel().getText().trim(),PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.memoLabel().getText().trim(),PropsUtil.getDataPropertyValue("AcntMemoLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.tagAllTransactnLabel().getText().trim(),PropsUtil.getDataPropertyValue("TagAllTransactionLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.catAllTransactnLabel().getText().trim(),PropsUtil.getDataPropertyValue("CategorizeAllTransactionLabel").trim()); 
	}

	@Test(description = "AT-75973,AT-75988,AT-76015:verify All labels:Apply categories to past transactions, Apply tag(s) to past transactions,show Account in Account Summary", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 25,enabled = true)
	public void verifytoggleBtnLabels() {
		Assert.assertEquals(accountSettiingsPopup.applyCatToPastTransactnLabel().getText().trim(),PropsUtil.getDataPropertyValue("AllyCategoryToPastTransaction").trim());
		Assert.assertEquals(accountSettiingsPopup.applyTagToPastTransactnLabel().getText().trim(),PropsUtil.getDataPropertyValue("ApplyTagPastTransaction").trim());
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryLabel().getText().trim(),PropsUtil.getDataPropertyValue("ShowAcntInAcntSummary").trim());		
	}

	@Test(description = "AT-75973,AT-76036,AT-76032:verify All  Close Account, Delete account link and Save change button", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 26,enabled = true)
	public void verifyallButtons() {

		Assert.assertTrue(accountSettiingsPopup.closeAcntBtn().isDisplayed(), "close Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.DeleteBtn().isDisplayed(), "delete Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.saveChangesBtn().isDisplayed(), "Save Button is not visible in popup.");	
	}

	@Test(description = "AT-75991,AT-75994,AT-75993,AT-75961,AT-75995:Ensure in the Edit account popup FI name must be shown at first as \"FI NAME|CONTAINER NAME(ACCT TYPE)\". ", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 27,enabled = true)
	public void verifyAcntNameFormat() {
		logger.info("Ensure in the Edit account popup for Account field Account Number is large then it must be elipsified");				
		Assert.assertEquals(accountSettiingsPopup.acntNameValue().getText().trim(),PropsUtil.getDataPropertyValue("ASAcntName").trim());

		logger.info("Ensure that Account Nickname field must be blank if there is no nick Name provided by the user");
		Assert.assertEquals(accountSettiingsPopup.acntNickNameTxtBox().getAttribute("value"),"");
	}

	@Test(description = "AT-76093,AT-76094:Verify that the ghost text 'Minumum 3 charecters' should be there in the tag text field. ", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 28,enabled = true)
	public void verifyTagAllTransactn() {
		SeleniumUtil.click(accountSettiingsPopup.tagNameTxtBox());
		Assert.assertEquals(accountSettiingsPopup.tagNameTxtBox().getAttribute("placeholder").trim(), PropsUtil.getDataPropertyValue("GhostTextForTagAllTransactionBox").trim());

		logger.info("Verify that the add button available for tag all transactions as field should be in disabled mode until user enter 3 charecters in the text fields");
		accountSettiingsPopup.tagNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("LessThan3CharTag").trim());
		Assert.assertEquals(accountSettiingsPopup.addTagBtn().getAttribute("disabled").trim(), PropsUtil.getDataPropertyValue("ByDefaultAddBtnStatus").trim());		
	}

	@Test(description = "AT-76097:Verify that when user selects any of the suggestions in the drop down it should reflect in the text field", groups = { "Regression" }, 
			dependsOnMethods="verifyTagAllTransactn",priority = 29,enabled = true)
	public void selectingsuggestionTag() {
		accountSettiingsPopup.tagNameTxtBox().clear();
		SeleniumUtil.click(accountSettiingsPopup.tagNameTxtBox());	
		SeleniumUtil.click(accountSettiingsPopup.suggestionnTags().get(0));
		Assert.assertEquals(accountSettiingsPopup.addedTags().get(0).getText().trim(), PropsUtil.getDataPropertyValue("AddedTagNames").trim());
	}

	@Test(description = "AT-76096:Verify that when user clicks on the close icon attached to the available tags the tag should be deleted", groups = { "Regression" }, 
			dependsOnMethods="verifyTagAllTransactn",priority = 30,enabled = true)
	public void deletingTagFunctionality() {

		SeleniumUtil.click(accountSettiingsPopup.deleteTag().get(0));	
		boolean isDeleted=false;
		try {
			if(!(accountSettiingsPopup.addedTags().size()==0))
			{
				isDeleted=true;
			}
		}catch (Exception e) {

		}
		Assert.assertFalse(isDeleted);
	}


	@Test(description = "AT-76098,AT-76099,AT-76100:Verify that user can enter custom tags other than the given suggestions ", groups = { "Regression" }, 
			dependsOnMethods="verifyTagAllTransactn",priority = 31,enabled = true)
	public void verifyAddingCustomTags() {

		logger.info("Verify that user can add multiple tags for the same account");
		String tags[]=PropsUtil.getDataPropertyValue("TagsToBeAdded").split(",");
		for(int i=0;i<3;i++) {
			SeleniumUtil.click(accountSettiingsPopup.tagNameTxtBox());	
			accountSettiingsPopup.tagNameTxtBox().sendKeys(tags[i]);
			accountSettiingsPopup.tagNameTxtBox().sendKeys(Keys.ENTER);
			Assert.assertEquals(accountSettiingsPopup.addedTags().get(i).getText().trim(), tags[i]);
		}
		logger.info("Verify that all the tags that are added are displayed below the text fields with a close icon attached to it");
		Assert.assertEquals(accountSettiingsPopup.deleteTag().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("AddedTagsSize")));
	}

	@Test(description = "AT-76011,AT-76010:Ensure that if user don't change the status of the toggle button from OFF to ON and click on the save button then success message must be shown", groups = { "Regression" }, 
			dependsOnMethods="verifyAddingCustomTags",priority = 32,enabled = true)
	public void verifyTagToPastFunctionality() {

		logger.info("	Ensure that user must be able to change the Toggle button from OFF to ON when ever needed for \"Apply category to past transactions\"");
		SeleniumUtil.click(accountSettiingsPopup.applyTagToPastToggle());
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(accountSettiingsPopup.updatedSuccessMsg().isDisplayed());
	}


	@Test(description = "AT-76000,AT-75999:Ensure that Nick name character must be max of only 40 characters lengt", groups = { "Regression" },
			dependsOnMethods="verifyTagToPastFunctionality",priority = 33,enabled = true)
	public void verifyNickNameBoxLength() throws Exception {

		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		accountSettiingsPopup.acntNickNameTxtBox().clear();
		accountSettiingsPopup.acntNickNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("NickNameMaxValue").trim());
		int getTextLength=accountSettiingsPopup.acntNickNameTxtBox().getAttribute("value").length();

		int expectedMaxLength = Integer.parseInt(accountSettiingsPopup.acntNickNameTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(expectedMaxLength, getTextLength);
	}

	@Test(description = "AT-76002,AT-76004:Ensure that by default in categorize all transactions drop down nothing should be selected and \"none\" should be there", groups = { "Regression" },
			dependsOnMethods="verifyNickNameBoxLength",priority = 34,enabled = true)
	public void verifyCategoryHeader() throws Exception {
		accountSettiingsPopup.acntNickNameTxtBox().clear();

		logger.info("Ensure use is provided with the option as a dropdown to select the category name from the list for \"Categorize all transactions to\"");
		SeleniumUtil.click(accountSettiingsPopup.byDefaultCategoryDDHeader().get(1));
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(), PropsUtil.getDataPropertyValue("byDefaultCategoryDDHeader").trim());   
	}

	@Test(description = "AT-76003:Ensure that if user try to select the category name later don't choose any of the name from the list and click some where else then no category name should reflect in the text box", groups = { "Regression" }, 
			dependsOnMethods="verifyCategoryHeader",priority = 35,enabled = true)
	public void verifyNoCategoryMsg() {
		SeleniumUtil.click(accountSettiingsPopup.searchForCatBox());
		accountSettiingsPopup.searchForCatBox().sendKeys(PropsUtil.getDataPropertyValue("NotPresentCategory").trim());
		Assert.assertTrue(accountSettiingsPopup.noCatAvailableMsg().isDisplayed());
	}

	@Test(description = "AT-76001,AT-76006,AT-76014:Ensure that when user choose a category name from the list the selected category name is shown in the text box and an option of apply the category to past trnx (toggle button) must be shown to user", groups = { "Regression" }, 
			dependsOnMethods="verifyNoCategoryMsg",priority = 36,enabled = true)
	public void verifySelectedCatInCatDD() {

		logger.info("Ensure that the category name are grouped based on the HLC and MLC in Edit Account popup under \"Categorize all transactions to\"");
		SeleniumUtil.click(accountSettiingsPopup.clearTagCrossIcon());
		SeleniumUtil.click(accountSettiingsPopup.catgoryList(1,1));
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(), PropsUtil.getDataPropertyValue("getSelectedCategory").trim());   
	}

	@Test(description = "AT-76089,AT-76008:Ensure that the option to apply the category to past trnx toggle button by default is in OFF state", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 37,enabled = true)
	public void verifyApplyCatToggleBtnState() {
		Assert.assertEquals(accountSettiingsPopup.applyCatToPastToggle().getAttribute("Value"), PropsUtil.getDataPropertyValue("applyCatToPastTransactnToggleValue").trim());
	}

	@Test(description = "AT-76089,AT-76092:Ensure that the option to apply tag to past trnx toggle button by default is in OFF state", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 38,enabled = true)
	public void verifyApplyTagToPastBtnState() {
		Assert.assertEquals(accountSettiingsPopup.applyTagToPastToggle().getAttribute("Value"), PropsUtil.getDataPropertyValue("applyCatToPastTransactnToggleValue").trim());
	}

	@Test(description = "AT-76013:Ensure to verify by default the toggle button to hide and unhide the account in account summary  is ON state", groups = { "Regression" }, 
			dependsOnMethods="verifyEditAcntpopUpLeftLabels",priority = 39,enabled = true)
	public void verifyShowAcntInSummaryToggleBtnState() {
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryToggle().getAttribute("Value"), PropsUtil.getDataPropertyValue("ShowAcntInAcntSumryToggleValue").trim());	
	}

	@Test(description = "AT-76007,AT-76119,AT-76009,AT-76012,AT-76090:Verify that when user turn on the toogle bar for apply categories to past transactions in the edit account popup and saves changes, then all the past transacgtions should be categorized as user selected category ", groups = { "Regression" }, 
			dependsOnMethods="verifySelectedCatInCatDD",priority = 40,enabled = true)
	public void verifyapplyCatToPastFunctionality() {		
		SeleniumUtil.click(accountSettiingsPopup.applyCatToPastToggle());	
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		accountSettiingsPopup.navigatingToTxn();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(), PropsUtil.getDataPropertyValue("getSelectedCategory").trim());
	}

	@Test(description = "AT-76101,AT-76095:Verify that when user turn on the toogle bar for apply tag(s) for past transactions in the edit account popup and saves changes, then all the past transacgtions should be tagged as user given tags ", groups = { "Regression" }, 
			dependsOnMethods="verifyapplyCatToPastFunctionality",priority = 41,enabled = true)
	public void verifyTagsInPastTransaction() {

		String ExistingTransactionTags[]=PropsUtil.getDataPropertyValue("TagsToBeAdded").split(",");

		for(int i=0;i<accountSettiingsPopup.existingTransactionTags().size();i++) {
			Assert.assertEquals(accountSettiingsPopup.existingTransactionTags().get(i).getText().trim(), ExistingTransactionTags[i]);	
		}
	}

	@Test(description = "AT-75955,AT-76017,AT-76019:Ensure that if the status of the Toggle button in ON then that particular account must be shown in Account Summary ", groups = { "Regression" }, 
			dependsOnMethods="verifyTagsInPastTransaction",priority = 42,enabled = true)
	public void verifyAcntSumryToggleBtnOn() {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Ensure to verify that toggle butto to hide and unhide the account in account summary  can be turned ON and OFF");
		Assert.assertEquals(accountSettiingsPopup.totalBankAcnt().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfAggBankAccounts").trim()));
	}

	@Test(description = "AT-76018,AT-76007:Ensure that when user trun OFF the toggle button and click on Save change button that particular account must not be shown in the Account summary", groups = { "Regression" }, 
			dependsOnMethods="verifyAcntSumryToggleBtnOn",priority = 43,enabled = true)
	public void verifyAcntSumryToggleBtnOff() {
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();	

		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accountSettiingsPopup.showAcntInSummaryToggle());
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(accountSettiingsPopup.totalBankAcnt().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfAggBankAccountInOffState").trim()));
	}

	@Test(description="AT-75946,AT-75945:The Alert settings finapp should not be deployed for Advisors.",
			priority=44)
	public void verifyingSettingsSubMenuForAdvisor() throws InterruptedException, AWTException {

		SeleniumUtil.click(accountSharing.settingdropdown2());
		for(int i=0;i<accountSharing.settingsSubMenu().size();i++) {
			String actual=accountSharing.settingsSubMenu().get(i).getText().trim();
			Assert.assertFalse(actual.contains(PropsUtil.getDataPropertyValue("SubMenuOfSettings").trim()));
		}		
	}
}
