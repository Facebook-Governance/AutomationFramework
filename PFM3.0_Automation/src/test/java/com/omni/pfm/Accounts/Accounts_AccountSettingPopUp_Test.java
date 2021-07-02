/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_AccountSettingPopUp_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_AccountSettingPopUp_Test.class);

	LoginPage login;
	AccountAddition accountAdd;
	Accounts_AccountSettingPopUp_Loc accountSettiingsPopup;
	InvestmentFormatChange_Loc AccountsLandingPage;
	Accounts_ManualAccountSettingPopUp_Loc manualAcntSettingPopup;
	AccountsLandingPage_Loc acntLandingPage;
	AccountsSetting_GlobalSettings_Loc globalSettings;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		acntLandingPage = new AccountsLandingPage_Loc(d);
		accountSettiingsPopup = new Accounts_AccountSettingPopUp_Loc(d);
		AccountsLandingPage = new InvestmentFormatChange_Loc(d);
		manualAcntSettingPopup = new Accounts_ManualAccountSettingPopUp_Loc(d);
		globalSettings = new AccountsSetting_GlobalSettings_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logger.info("Add Dag Site");
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		Assert.assertTrue(accountAdd.addAggregatedAccount("ihjuly1.site16441.1", "site16441.1"));
	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 0, enabled = true)
	public void login() throws Exception {
		// login with existing user..remove later
		// LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-92891,AT-92892AT-92895,AT-92896", groups = {
			"Regression" }, priority = 1, dependsOnMethods = "login", enabled = true)
	public void verifySettingsFromMoreDDSettings() {

		try {
			accountSettiingsPopup.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}

		
		acntLandingPage.clickOnMoreBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		// SeleniumUtil.click(acntLandingPage.VisibleOptionsUnderMoreBtn().get(2));
		acntLandingPage.clickMoreOptionLabel(PropsUtil.getDataPropertyValue("Account-More-Settinglabel"));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountSettingHeader").trim());
		boolean status = globalSettings.isbackToAcntsLinkMobile();
		System.out.println(status);
		if (globalSettings.isbackToAcntsLinkMobile()) {
			SeleniumUtil.click(globalSettings.backToAcntsLinkMobile());
			SeleniumUtil.waitForPageToLoad();
		} else {
			globalSettings.clickingOnbackToAcntsLink();
		}

		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("Accounts_Header1").trim());

	}

	@Test(description = "AT-92893:AT-92897,AT-92889,AT-93118,AT-93108:Ensure to check for a Aggregated Bank container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyAccountSettingInCashContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.selectDropDown(d, "Cash Account", "Accounts Settings");
		// Assert.assertFalse(accountSettiingsPopup.saveChangesBtn().isEnabled());
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92898,AT-93112:Ensure to check for a Aggregated Card container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup.  ", groups = {
			"Regression" }, priority = 3, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInCardContainer() throws Exception {
		SeleniumUtil.selectDropDown(d, "Super CD Plus", "Accounts Settings");
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92899:Ensure to check for a Aggregated Investment container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 4, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingIninvestmentContainer() throws Exception {

		SeleniumUtil.selectDropDown(d, "SUNY 403(B) PROGRAM", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92899:Ensure to check for a Aggregated Investment container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 5, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInRetirementSavingsContainer() throws Exception {

		SeleniumUtil.selectDropDown(d, "UPS/IPA 401K SAVINGS", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92899:Ensure to check for a Aggregated Investment container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 6, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInRetirementContainer() throws Exception {

		SeleniumUtil.selectDropDown(d, "UPS/IPA MPP", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92902:Ensure to check for a Aggregated Loan container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup.  ", groups = {
			"Regression" }, priority = 7, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInloanContainer() throws Exception {
		SeleniumUtil.selectDropDown(d, "LINE OF CREDIT", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92900:Ensure to check for a Aggregated Insurance container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 8, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInInsuranceContainer() throws Exception {
		SeleniumUtil.selectDropDown(d, "INSURANCE", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92901:Ensure to check for a Aggregated Bills container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 9, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInBillContainer() throws Exception {

		SeleniumUtil.selectDropDown(d, "DAG BILLING", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92893:AT-92889,AT-92903:Ensure to check for a Aggregated Rewards container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 10, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyAccountSettingInRewardsSite() throws Exception {
		SeleniumUtil.selectDropDown(d, "Roger", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader"));
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-92905,AT-92920,AT-93016,AT-92975,AT-92889:verify popup fields Account, Account NickName(optional), Categorize all transaction to (optional)Tag all transactions as(optional), Close Account, Delete account link and Save change button. ", groups = {
			"Regression" }, priority = 11, dependsOnMethods = "verifyAccountSettingInCashContainer", enabled = true)
	public void verifyEditAcntpopUpLeftLabels() {

		SeleniumUtil.selectDropDown(d, "Cash Account", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(accountSettiingsPopup.acntNameLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountNameLabel").trim());
		String nicknamelabel = accountSettiingsPopup.acntNickNameLabel().getText().trim();
		System.out.println(nicknamelabel);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(accountSettiingsPopup.acntNickNameLabelmobile().getText().trim(),
					PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		} else {
			Assert.assertEquals(accountSettiingsPopup.acntNickNameLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		}

		String memo = accountSettiingsPopup.memoLabel().getText().trim();
		System.out.println(memo);
		Assert.assertEquals(accountSettiingsPopup.memoLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AcntMemoLabel").trim());
		String tag = accountSettiingsPopup.tagAllTransactnLabel().getText().trim();
		System.out.println(tag);
		Assert.assertEquals(accountSettiingsPopup.tagAllTransactnLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TagAllTransactionLabel").trim());
		String cate = accountSettiingsPopup.catAllTransactnLabel().getText().trim();
		System.out.println(cate);
		Assert.assertEquals(accountSettiingsPopup.catAllTransactnLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("CategorizeAllTransactionLabel").trim());
	}

	@Test(description = "AT-92905:verify All labels:Apply categories to past transactions, Apply tag(s) to past transactions,show Account in Account Summary", groups = {
			"Regression" }, priority = 12, dependsOnMethods = "verifyEditAcntpopUpLeftLabels", enabled = true)
	public void verifytoggleBtnLabels() {
		Assert.assertEquals(accountSettiingsPopup.applyCatToPastTransactnLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AllyCategoryToPastTransaction").trim());
		Assert.assertEquals(accountSettiingsPopup.applyTagToPastTransactnLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("ApplyTagPastTransaction").trim());
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("ShowAcntInAcntSummary").trim());
	}

	@Test(description = "AT-92905,AT-92939,AT-92943,AT-92944:verify All  Close Account, Delete account link and Save change button", groups = {
			"Regression" }, priority = 13, dependsOnMethods = "verifyEditAcntpopUpLeftLabels", enabled = true)
	public void verifyallButtons() {

		Assert.assertTrue(accountSettiingsPopup.closeAcntBtn().isDisplayed(), "close Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.DeleteBtn().isDisplayed(), "delete Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.saveChangesBtn().isDisplayed(), "Save Button is not visible in popup.");
	}

	@Test(description = "AT-92909,AT-92912,AT-92915,AT-92913:Ensure in the Edit account popup FI name must be shown at first as \"FI NAME|CONTAINER NAME(ACCT TYPE)\". ", groups = {
			"Regression" }, priority = 14, dependsOnMethods = "login", enabled = true)
	public void verifyAcntNameFormat() {
		logger.info(
				"Ensure in the Edit account popup for Account field Account Number is large then it must be elipsified");
		Assert.assertEquals(accountSettiingsPopup.acntNameValue().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountNameValue").trim());

		logger.info("Ensure that Account Nickname field must be blank if there is no nick Name provided by the user");
		Assert.assertEquals(accountSettiingsPopup.acntNickNameTxtBox().getAttribute("value"), "");
	}

	@Test(description = "AT-93034,AT-93033,AT-93035:Verify that the ghost text 'Minumum 3 charecters' should be there in the tag text field. ", groups = {
			"Regression" }, priority = 15, dependsOnMethods = "login", enabled = true)
	public void verifyTagAllTransactn() {
		SeleniumUtil.click(accountSettiingsPopup.tagNameTxtBox());
		Assert.assertEquals(accountSettiingsPopup.tagNameTxtBox().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("GhostTextForTagAllTransactionBox").trim());

		logger.info(
				"Verify that the add button available for tag all transactions as field should be in disabled mode until user enter 3 charecters in the text fields");
		accountSettiingsPopup.tagNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("LessThan3CharTag").trim());
		Assert.assertEquals(accountSettiingsPopup.addTagBtn().getAttribute("disabled").trim(),
				PropsUtil.getDataPropertyValue("ByDefaultAddBtnStatus").trim());
	}

	@Test(description = "AT-93036,AT-93038:Verify that when user selects any of the suggestions in the drop down it should reflect in the text field", groups = {
			"Regression" }, priority = 16, dependsOnMethods = "login", enabled = true)
	public void selectingsuggestionTag() {
		accountSettiingsPopup.tagNameTxtBox().clear();
		SeleniumUtil.click(accountSettiingsPopup.tagNameTxtBox());
		SeleniumUtil.click(accountSettiingsPopup.suggestionnTags().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		System.out.println(accountSettiingsPopup.addedTags().get(0).getText().trim());
		Assert.assertEquals(accountSettiingsPopup.addedTags().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("AddedTagNames").trim());
	}

	@Test(description = "AT-93037,AT-93041:Verify that when user clicks on the close icon attached to the available tags the tag should be deleted", groups = {
			"Regression" }, priority = 17, dependsOnMethods = "login", enabled = true)
	public void deletingTagFunctionality() {

		boolean isDeleted = false;
		SeleniumUtil.click(accountSettiingsPopup.deleteTag().get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		try {
			if ((SeleniumUtil.getElementCount(accountSettiingsPopup.tagsPresent) == 0)) {
				isDeleted = true;
			}
		} catch (Exception e) {

		}
		Assert.assertTrue(isDeleted);
	}

	@Test(description = "AT-93040,AT-93039:Verify that user can enter custom tags other than the given suggestions ", groups = {
			"Regression" }, priority = 18, dependsOnMethods = "login", enabled = true)
	public void verifyAddingCustomTags() {

		logger.info("Verify that user can add multiple tags for the same account");
		String tags[] = PropsUtil.getDataPropertyValue("TagsToBeAdded").split(",");
		for (int i = 0; i < 3; i++) {
			SeleniumUtil.click(accountSettiingsPopup.tagNameTxtBox());
			accountSettiingsPopup.tagNameTxtBox().sendKeys(tags[i]);
			accountSettiingsPopup.tagNameTxtBox().sendKeys(Keys.ENTER);
			Assert.assertEquals(accountSettiingsPopup.addedTags().get(i).getText().trim(), tags[i]);
			// Assert.assertTrue(accountSettiingsPopup.addedTags().get(i).getText().trim().contains(PropsUtil.getDataPropertyValue("TagsToBeAdded").trim()));
		}
		logger.info(
				"Verify that all the tags that are added are displayed below the text fields with a close icon attached to it");
		Assert.assertEquals(accountSettiingsPopup.deleteTag().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("AddedTagsSize")));
	}

	@Test(description = "AT-93042,AT-92930,AT-92931,AT-93111:Ensure that if user don't change the status of the toggle button from OFF to ON and click on the save button then success message must be shown", groups = {
			"Regression" }, priority = 19, dependsOnMethods = "login", enabled = true)
	public void verifyTagToPastFunctionality() {

		logger.info(
				"	Ensure that user must be able to change the Toggle button from OFF to ON when ever needed for \"Apply category to past transactions\"");
		SeleniumUtil.click(accountSettiingsPopup.applyTagToPastToggle());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(accountSettiingsPopup.updatedSuccessMsg().isDisplayed()); // in mobile automation we are not
																					// testing success messages
	}

	@Test(description = "Verify that when user turn on the toogle bar for apply tag(s) for past transactions in the edit account popup and saves changes, then all the past transacgtions should be tagged as user given tags ", groups = {
			"Regression" }, priority = 20, dependsOnMethods = "login", enabled = true)
	public void verifyTagsInPastTransaction() {

		String ExistingTransactionTags[] = PropsUtil.getDataPropertyValue("TagsToBeAdded").split(",");
		accountSettiingsPopup.navigatingToExistingTransac();
		SeleniumUtil.waitForPageToLoad(3000);
		for (int i = 0; i < accountSettiingsPopup.existingTransactionTags().size(); i++) {
			Assert.assertEquals(accountSettiingsPopup.existingTransactionTags().get(i).getText().trim(),
					ExistingTransactionTags[i]); // verify again with new user
		}
		SeleniumUtil.click(accountSettiingsPopup.updateTransactionBtn());
		SeleniumUtil.waitForPageToLoad(2000);

	}

	@Test(description = "Verify that when after applying the past transactions the toogle button is a onetime operation which measn the toogle button should be automaticaaly turned off (no stickyness)", groups = {
			"Regression" }, priority = 21, dependsOnMethods = "login", enabled = true)
	public void verifyTagPastTransactnBtnAftrSave() {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(5000);
		
		try {
			accountSettiingsPopup.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(5000);
		}
		
		SeleniumUtil.selectDropDown(d, "Cash Account", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(accountSettiingsPopup.applyTagToPastToggle().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("WithoutTagToggleBtnStatus").trim());// false
	}

	@Test(description = "AT-92916:Ensure when user click on the cross icon the edit account popup must be closed and user is able to see the account settings page with the same FI name in enabled state.", groups = {
			"Regression" }, priority = 22, dependsOnMethods = "login", enabled = true)
	public void creatingNickNameAndVerifying() {

		accountSettiingsPopup.acntNickNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("NIckNameValue").trim());
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.selectDropDown(d, "Cash Account", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitForElementVisible(d, "NIckNameValue", "AccountsPage", null);
		Assert.assertEquals(accountSettiingsPopup.acntNickNameTxtBox().getAttribute("value"),
				PropsUtil.getDataPropertyValue("NIckNameValue").trim());
	}

	@Test(description = "AT-92918,AT-92919:Ensure that Nick name character must be max of only 40 characters lengt", groups = {
			"Regression" }, priority = 23, dependsOnMethods = "login", enabled = true)
	public void verifyNickNameBoxLength() throws Exception {

		accountSettiingsPopup.acntNickNameTxtBox().clear();
		accountSettiingsPopup.acntNickNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("NickNameMaxValue").trim());
		SeleniumUtil.waitForPageToLoad(3000);
		int getTextLength = accountSettiingsPopup.acntNickNameTxtBox().getAttribute("value").length();

		int expectedMaxLength = Integer
				.parseInt(accountSettiingsPopup.acntNickNameTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(expectedMaxLength, getTextLength);
	}

	@Test(description = "AT-92921,AT-92922:Ensure that by default in categorize all transactions drop down nothing should be selected and \"none\" should be there", groups = {
			"Regression" }, priority = 24, dependsOnMethods = "login", enabled = true)
	public void verifyCategoryHeader() throws Exception {
		accountSettiingsPopup.acntNickNameTxtBox().clear();

		logger.info(
				"Ensure use is provided with the option as a dropdown to select the category name from the list for \"Categorize all transactions to\"");
		SeleniumUtil.click(accountSettiingsPopup.byDefaultCategoryDDHeader().get(1));
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("byDefaultCategoryDDHeader").trim());
	}

	@Test(description = "AT-92924:Ensure that if user try to select the category name later don't choose any of the name from the list and click some where else then no category name should reflect in the text box", groups = {
			"Regression" }, priority = 25, dependsOnMethods = "login", enabled = true)
	public void verifyNoCategoryMsg() {
		SeleniumUtil.click(accountSettiingsPopup.searchForCatBox());
		SeleniumUtil.waitForPageToLoad();
		accountSettiingsPopup.searchForCatBox().sendKeys(PropsUtil.getDataPropertyValue("NotPresentCategory").trim());
		Assert.assertTrue(accountSettiingsPopup.noCatAvailableMsg().isDisplayed());
	}

	@Test(description = "AT-92925,AT-92923,AT-93070:Ensure that when user choose a category name from the list the selected category name is shown in the text box and an option of apply the category to past trnx (toggle button) must be shown to user", groups = {
			"Regression" }, priority = 26, dependsOnMethods = "login", enabled = true)
	public void verifySelectedCatInCatDD() {

		logger.info(
				"Ensure that the category name are grouped based on the HLC and MLC in Edit Account popup under \"Categorize all transactions to\"");
		SeleniumUtil.click(accountSettiingsPopup.clearTagCrossIcon());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSettiingsPopup.catgoryList(1, 1));
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("getSelectedCategory").trim());
	}

	@Test(description = "AT-92927:Ensure that the option to apply the category to past trnx toggle button by default is in OFF state", groups = {
			"Regression" }, priority = 27, dependsOnMethods = "login", enabled = true)
	public void verifyApplyCatToggleBtnState() {
		Assert.assertEquals(accountSettiingsPopup.applyCatToPastToggle().getAttribute("Value"),
				PropsUtil.getDataPropertyValue("applyCatToPastTransactnToggleValue").trim());
	}

	@Test(description = "Ensure that the option to apply tag to past trnx toggle button by default is in OFF state", groups = {
			"Regression" }, priority = 28, dependsOnMethods = "login", enabled = true)
	public void verifyApplyTagToPastBtnState() {
		Assert.assertEquals(accountSettiingsPopup.applyTagToPastToggle().getAttribute("Value"),
				PropsUtil.getDataPropertyValue("applyCatToPastTransactnToggleValue").trim());
	}

	@Test(description = "AT-92928,AT-93006,AT-93008,AT-93031,AT-93109:Verify that when user turn on the toogle bar for apply categories to past transactions in the edit account popup and saves changes, then all the past transacgtions should be categorized as user selected category ", groups = {
			"Regression" }, priority = 29, dependsOnMethods = "verifySelectedCatInCatDD", enabled = true)
	public void verifyapplyCatToPastFunctionality() {

		SeleniumUtil.click(accountSettiingsPopup.applyCatToPastToggle());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad(3000);
		accountSettiingsPopup.navigatingToExistingTransac(); // check again
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitForElementVisible(d, "byDefaultCategoryDDHeader", "AccountsPage", null);
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("getSelectedCategory").trim());// automotive expense but gift
	}

	@Test(description = "Ensure that if the status of the Toggle button in ON then that particular account must be shown in Account Summary ", groups = {
			"Regression" }, priority = 30, dependsOnMethods = "login", enabled = true)
	public void verifyAcntSumryToggleBtnOn() {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info(
				"Ensure to verify that toggle button to hide and unhide the account in account summary  can be turned ON and OFF");
		
		if(accountSettiingsPopup.totalBankAcnt().size()==0) {
			d.navigate().refresh();
			SeleniumUtil.waitForPageToLoad();
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
		}
		
		Assert.assertEquals(accountSettiingsPopup.totalBankAcnt().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfBankAccountInOnState").trim()));
	}

	@Test(description = "AT-93030,AT-93032,AT-93043:Verify that when after applying the past transactions the toogle button is a onetime operation which measn the toogle button should be automaticaaly turned off (no stickyness)", groups = {
			"Regression" }, priority = 31, dependsOnMethods = "login", enabled = true)
	public void verifyApplyCtToggleAfterSave() {

		SeleniumUtil.selectDropDown(d, "Cash Account", "Accounts Settings"); // check it is clicking or no
		System.out.println(accountSettiingsPopup.applyCatToPastToggle().getAttribute("Value")); // false
		Assert.assertEquals(accountSettiingsPopup.applyCatToPastToggle().getAttribute("Value"),
				PropsUtil.getDataPropertyValue("applyCatToPastTransactnToggleValue").trim());
	}

	@Test(description = "AT-93030:Verify that tag past transactions toogle button should be in disabled mode unless user has some tags added", groups = {
			"Regression" }, priority = 32, dependsOnMethods = "login", enabled = true)
	public void verifyTagPastTransactnBtnWithoutTag() {
		System.out.println(accountSettiingsPopup.applyTagToPastToggle().getAttribute("value").trim());// false
		Assert.assertEquals(accountSettiingsPopup.applyTagToPastToggle().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("WithoutTagToggleBtnStatus").trim());
	}

	@Test(description = "AT-92934,AT-92975,AT-93006:Ensure to verify by default the toggle button to hide and unhide the account in account summary  is ON state", groups = {
			"Regression" }, priority = 33, dependsOnMethods = "login", enabled = true)
	public void verifyShowAcntInSummaryToggleBtnState() {
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryToggleButton().getAttribute("value"),
				PropsUtil.getDataPropertyValue("ShowAcntInAcntSumryToggleValue").trim());
	}

	@Test(description = "AT-92935,AT-92932,AT-92936,AT-92937,AT-92938,AT-93107:Ensure that when user trun OFF the toggle button and click on Save change button that particular account must not be shown in the Account summary", groups = {
			"Regression" }, priority = 34, dependsOnMethods = "login", enabled = true)
	public void verifyAcntSumryToggleBtnOff() {
		SeleniumUtil.click(accountSettiingsPopup.showAcntInSummaryToggleButton());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountSettiingsPopup.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(accountSettiingsPopup.totalBankAcnt().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfBankAccountInOffState").trim()));
	}

	@Test(description = "Verify For bills accounts under account settings popup following fields must be shown Account, Account Nick name(optional) with text box", groups = {
			"Regression" }, priority = 35, dependsOnMethods = "login", enabled = true)
	public void verifyBillsPopUp() {

		SeleniumUtil.selectDropDown(d, "DAG BILLING", "Accounts Settings"); // check it is clicking or no
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accountSettiingsPopup.acntNickNameTxtBox().isDisplayed());
		Assert.assertTrue(accountSettiingsPopup.memoTxtBox().isDisplayed());
		Assert.assertEquals(accountSettiingsPopup.acntNameValue().getText().trim(),
				PropsUtil.getDataPropertyValue("BillsAccountNameValue").trim()); // Cash Account Savings | x-2321
		Assert.assertEquals(accountSettiingsPopup.acntNameLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountNameLabel").trim());
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(accountSettiingsPopup.acntNickNameLabelmobile().getText().trim(),
					PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		} else {
			Assert.assertEquals(accountSettiingsPopup.acntNickNameLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		}

		Assert.assertEquals(accountSettiingsPopup.memoLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AcntMemoLabel").trim());

	}

	@Test(description = "Verify For bills accounts nder account settings popup following fields must be shown Show account in Account summery with toogle button, close account icon and text, delete account icon and text, save changes", groups = {
			"Regression" }, priority = 36, dependsOnMethods = "login", enabled = true)
	public void verifyBillsButtons() {
		Assert.assertTrue(accountSettiingsPopup.closeAcntBtn().isDisplayed(), "close Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.DeleteBtn().isDisplayed(), "delete Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.saveChangesBtn().isDisplayed(), "Save Button is not visible in popup.");
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("ShowAcntInAcntSummary").trim());
	}

	@Test(description = "AT-93015:Verify that for bills containers, apply tags and categories to past transactions tag should be removed", groups = {
			"Regression" }, priority = 37, dependsOnMethods = "login", enabled = true)
	public void verifyToggleBtnInBills() {

		boolean isVisible = false;
		try {
			if (accountSettiingsPopup.applyCatToPastToggle().isDisplayed()
					|| accountSettiingsPopup.applyTagToPastToggle().isDisplayed()) {
				isVisible = true;
			}

		} catch (Exception e) {

		}
		Assert.assertFalse(isVisible);
	}

	@Test(description = "AT-92908,AT-93119,AT-93120:Ensure when user click on the cross icon the edit account popup must be closed and user is able to see the account settings page with the same FI name in enabled state.", groups = {
			"Regression" }, priority = 38, dependsOnMethods = "login", enabled = true)
	public void verifyCloseIconFunctionality() {
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		Assert.assertTrue(AccountsLandingPage.AcntsThreeViews().get(0).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("EnabledView").trim()));
	}

	@Test(description = "Verify For rewards accounts under account settings popup following fields must be shown Account, Account Nick name(optional) with text box", groups = {
			"Regression" }, priority = 39, dependsOnMethods = "login", enabled = true)
	public void verifyRewardsPopUp() {

		SeleniumUtil.selectDropDown(d, "Roger", "Accounts Settings");

		logger.info(
				"Ensure that in Edit Account popup the Account Number must be shown in Masked format except the last 4 digits");
		Assert.assertEquals(accountSettiingsPopup.acntNameLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountNameLabel").trim());

		Assert.assertTrue(accountSettiingsPopup.acntNickNameTxtBox().isDisplayed());
		Assert.assertTrue(accountSettiingsPopup.memoTxtBox().isDisplayed());
		Assert.assertEquals(accountSettiingsPopup.acntNameValue().getText().trim(),
				PropsUtil.getDataPropertyValue("RewardsAccountNameValue").trim()); // Dag Site Other
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(accountSettiingsPopup.acntNickNameLabelmobile().getText().trim(),
					PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		}
		Assert.assertEquals(accountSettiingsPopup.acntNickNameLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AcntNickNameLabel").trim());
		Assert.assertEquals(accountSettiingsPopup.memoLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AcntMemoLabel").trim());
	}

	@Test(description = "Verify For rewards accounts nder account settings popup following fields must be shown Show account in Account summery with toogle button, close account icon and text, delete account icon and text, save changes", groups = {
			"Regression" }, priority = 40, dependsOnMethods = "login", enabled = true)
	public void verifyRewardsButtons() {

		Assert.assertTrue(accountSettiingsPopup.closeAcntBtn().isDisplayed(), "close Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.DeleteBtn().isDisplayed(), "delete Button is not visible in popup.");
		Assert.assertTrue(accountSettiingsPopup.saveChangesBtn().isDisplayed(), "Save Button is not visible in popup.");
		Assert.assertEquals(accountSettiingsPopup.showAcntInSummaryLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("ShowAcntInAcntSummary").trim());
	}

	@Test(description = "AT-93015:Verify that for rewards containers, apply tags and categories to past transactions tag should be removed", groups = {
			"Regression" }, priority = 41, dependsOnMethods = "login", enabled = true)
	public void verifyToggleBtnInRewards() {

		boolean isVisible = false;
		try {
			if (accountSettiingsPopup.applyCatToPastToggle().isDisplayed()
					|| accountSettiingsPopup.applyTagToPastToggle().isDisplayed()) {
				isVisible = true;
			}

		} catch (Exception e) {

		}
		Assert.assertFalse(isVisible);
	}

	@Test(description = "Ensure that when user click on any of the Account settings icon of aggregated accounts in Account Setting page user must be shown with the popup of the Edit Account popup", groups = {
			"Regression" }, priority = 42, dependsOnMethods = "login", enabled = true)
	public void verifyEditPopupInAcntSetng() {
		SeleniumUtil.click(accountSettiingsPopup.popupCrossIcon());
		SeleniumUtil.waitForPageToLoad(1000);
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSettiingsPopup.settingAtAcntLevel().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingPopupHeader").trim()); // Account Settings
	}

}