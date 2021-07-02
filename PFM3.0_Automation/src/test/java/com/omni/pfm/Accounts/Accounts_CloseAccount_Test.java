/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author sakshi Jain
 */
package com.omni.pfm.Accounts;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_CloseAccount_Test extends TestBase {

	Logger logger = LoggerFactory.getLogger(Accounts_CloseAccount_Test.class);
	CloseAccountPopup_Loc closeAccounts;
	Accounts_AccountSettingPopUp_Loc accountSettingPopUp;
	AccountsSetting_GlobalSettings_Loc globalSetting;
	AccountsViewByFI_Loc viewByFi;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		closeAccounts = new CloseAccountPopup_Loc(d);
		accountSettingPopUp = new Accounts_AccountSettingPopUp_Loc(d);
		globalSetting = new AccountsSetting_GlobalSettings_Loc(d);
		viewByFi = new AccountsViewByFI_Loc(d);
	}

	@Test(description = "login to the user", priority = 1, enabled = true)
	public void login() throws Exception {

		d.navigate().refresh();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	// *********************** Verify close account popup in Account finapp at
	// account level *****************

	@Test(description = "AT-84289,AT-92945: verify close account popup Header", dependsOnMethods = "login", priority = 2, enabled = true)
	public void verifyCloseAcntPopUP() throws InterruptedException {

		try {
			accountSettingPopUp.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.selectDropDown(d, "TESTDATA", "Accounts Settings");
		SeleniumUtil.waitForPageToLoad();
		logger.info("verify the close account option is present in account setting popup.");

		Assert.assertEquals(accountSettingPopUp.closeAcntBtn().getText().trim(),
				PropsUtil.getDataPropertyValue("close_Txt"));
		accountSettingPopUp.clickOnCloseAcntBtn();
		logger.info(
				"Clicking on close account option, it must open the close account pop-up with a header Close Account.");
		Assert.assertEquals(closeAccounts.closeAcntPopupHeader().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("close_Txt").trim());

	}

	@Test(description = "AT-84288,AT-84291:A Note(optional) field with a editable text box must be available at the close account pop up.", dependsOnMethods = "verifyCloseAcntPopUP", priority = 3, enabled = true)
	public void verifyEditableBox() throws InterruptedException {

		Assert.assertEquals(closeAccounts.closeAcntPopupNoteTxt().getText().trim(),
				PropsUtil.getDataPropertyValue("NoteText").trim());
		Assert.assertTrue(closeAccounts.closeAcntPopupNoteTxt().isDisplayed());

	}

	@Test(description = "AT-84290:Verify Close Account Pop Body Messages.", dependsOnMethods = "verifyCloseAcntPopUP", priority = 4, enabled = true)

	public void verifyCloseAcntPopBodyMsg() throws InterruptedException {

		Assert.assertEquals(closeAccounts.closeAcntPopupBodyMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("Close_Warning_Msg").trim());
		closeAccounts.verifyingcloseAcntPopupBodyMsgs();
	}

	@Test(description = "AT-84292 :User must be able to enter Max length 150 characters in the editable text field for Note, Restrict typing beyond max value.", dependsOnMethods = "verifyCloseAcntPopUP", priority = 5, enabled = true)
	public void verifyEditBoxLength() throws Exception {

		closeAccounts.clickOncloseAcntPopupNoteTxt();
		closeAccounts.typeEditBox(PropsUtil.getDataPropertyValue("TestingText").trim());
		String editBoxTxt = closeAccounts.getMaxAttributeLength();
		int t = Integer.parseInt(editBoxTxt);
		Assert.assertTrue((100 < t) && (t <= 150));
	}

	@Test(description = "AT-84295 :A button CLOSE ACCOUNT of red colour must be there at the bottom-right of the close account pop up.", dependsOnMethods = "verifyCloseAcntPopUP", priority = 6, enabled = true)

	public void verifyCloseAcntBtn() throws Exception {

		String buttonBckGrndColor = d.findElement(By.id("close-account")).getCssValue("background-color");
		String buttonTextColor = d.findElement(By.id("close-account")).getCssValue("color");

		logger.info("Button color: " + buttonBckGrndColor);
		logger.info("Text color: " + buttonTextColor);

		Assert.assertEquals(buttonBckGrndColor.toLowerCase().trim(),
				PropsUtil.getDataPropertyValue("BtnBckrgrndColor").toLowerCase().trim());
		Assert.assertEquals(buttonTextColor.toLowerCase().trim(),
				PropsUtil.getDataPropertyValue("BtnTextColor").toLowerCase().trim());
	}

	@Test(description = "AT-84294,AT-84287:After clicking the cross indicator the close account pop up must be closed.", dependsOnMethods = "verifyCloseAcntPopUP", priority = 7, enabled = true)

	public void verifyCrossBtnFunctionality() throws Exception {
		logger.info("Verify that a cross indicator should be there");
		closeAccounts.isCloseAcntPopupCrossIconDisplayed();

		closeAccounts.clickOncloseAcntPopupCrossIcon();
		Assert.assertEquals(closeAccounts.closeAcntPopupHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("AccountSettingPopUpHeader").trim());
	}

	@Test(description = "AT-84169,AT-84297: verify close account functionality", dependsOnMethods = "verifyCrossBtnFunctionality", priority = 8, enabled = true)

	public void verifyCloseAcntFunctionality() throws Exception {
		accountSettingPopUp.clickOnCloseAcntBtn();
		closeAccounts.clickingOnCloseAcntBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(closeAccounts.closeAcntSuccessMsg().isDisplayed());
		Assert.assertEquals(accountSettingPopUp.totalBankAcnt().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfBankAccountInOnState").trim()));
	}

	@Test(description = "AT-76022,AT-76038: verify close account popup should open after clicking on close button in account setting popup in account setting finapp.", dependsOnMethods = "verifyCloseAcntFunctionality", priority = 9, enabled = true)
	public void verifyClosePopupInAS() {
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();

		try {
			accountSettingPopUp.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(1));
		accountSettingPopUp.clickOnCloseAcntBtn();
		logger.info(
				"Clicking on close account option, it must open the close account pop-up with a header Close Account.");
		Assert.assertEquals(closeAccounts.closeAcntPopupHeader().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("close_Txt").trim());
	}

	@Test(description = "AT-76023,AT-76048 :verify popup body msg", dependsOnMethods = "verifyClosePopupInAS", priority = 10, enabled = true)

	public void verifyClosePopupMsgInAS() throws InterruptedException {

		Assert.assertEquals(closeAccounts.closeAcntPopupBodyMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("Close_Warning_Msg2").trim());
		closeAccounts.verifyingcloseAcntPopupBodyMsgs();
	}

	@Test(description = "AT-76024 :User must be able to enter Max length 150 characters in the editable text field for Note, Restrict typing beyond max value.", dependsOnMethods = "verifyClosePopupInAS", priority = 11, enabled = true)
	public void verifyEditBoxLengthInAS() throws Exception {

		closeAccounts.clickOncloseAcntPopupNoteTxt();
		closeAccounts.typeEditBox(PropsUtil.getDataPropertyValue("TestingText").trim());
		String editBoxTxt = closeAccounts.getMaxAttributeLength();
		int t = Integer.parseInt(editBoxTxt);
		Assert.assertTrue((100 < t) && (t <= 150));
	}

	@Test(description = "AT-76020,AT-7612:After clicking the cross indicator the close account pop up must be closed.", dependsOnMethods = "verifyClosePopupInAS", priority = 12, enabled = true)

	public void verifyCrossBtnFunctionalityInAS() throws Exception {
		logger.info("Verify that a cross indicator should be there");
		closeAccounts.isCloseAcntPopupCrossIconDisplayed();

		closeAccounts.clickOncloseAcntPopupCrossIcon();
		Assert.assertEquals(closeAccounts.closeAcntPopupHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("AccountSettingPopUpHeader").trim());
	}

	@Test(description = "AT-76026,AT-76037: verify close account functionality", dependsOnMethods = "verifyCrossBtnFunctionalityInAS", priority = 13, enabled = true)

	public void verifyCloseAcntFunctionalityInAS() throws Exception {
		accountSettingPopUp.clickOnCloseAcntBtn();
		closeAccounts.clickingOnCloseAcntBtn();
		SeleniumUtil.waitForElementVisible(d, "deleteAcntSuccessMsg", "AccountsPage", null);
		Assert.assertTrue(closeAccounts.closeAcntSuccessMsg().isDisplayed());
	}

	@Test(description = "AT-76029,AT-75948:Verify that when the account is closed then a text closed should appear beside the Account type and number", dependsOnMethods = "verifyCrossBtnFunctionalityInAS", priority = 14, enabled = true)

	public void verifyClosedAcntInAS() throws Exception {
		Assert.assertEquals(accountSettingPopUp.closedAcntClosedText().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("closedAcntClosedText").trim());
		Assert.assertEquals(accountSettingPopUp.closedAcntClosedText().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("closedAcntSize").trim()));
	}

	@Test(description = "AT-76028:Closed account balance should be set to zero in Accounts finapp and should be visible in the app.", dependsOnMethods = "verifyCrossBtnFunctionalityInAS", priority = 15, enabled = true)

	public void verifyClosedAcntBalance() throws Exception {
		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(1));
		SeleniumUtil.click(accountSettingPopUp.showAcntInSummaryToggle());
		SeleniumUtil.click(accountSettingPopUp.saveChangesBtn());

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		try {
			accountSettingPopUp.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		Assert.assertEquals(accountSettingPopUp.totalBankAcnt().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfBankAccountInOnState").trim()));
		Assert.assertEquals(viewByFi.AccountsAcntAmount().get(0).getText().trim(), "$0.00 ø");
	}

	@AfterClass
	public void checkAcc() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {

		}
	}
}
