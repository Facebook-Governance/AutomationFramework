/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
package com.omni.pfm.CategorizationRules;

import static org.testng.Assert.fail;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.CategorizationRules.CategorizationRules_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Categorization_RulesPFM_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Categorization_RulesPFM_Test.class);
	AccountAddition accountAdd;
	CategorizationRules_Loc categorization;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {

		doInitialization("Categorization Rules");

		accountAdd = new AccountAddition();
		categorization = new CategorizationRules_Loc(d);

		logger.info(">>>>> Logging in to PFM 3.0..");
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Adding an aggregated account..");
		accountAdd.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(accountAdd.addAggregatedAccount("shivaprasad_bhat.site16441.1", "site16441.1"));

		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-80676:Verify Cat Rules header.", priority = 0)
	public void verifyCatRulesHeader() throws Exception {

		Assert.assertTrue(categorization.categorizationRules().isDisplayed(),
				".. Categorization rules header is not displayed..");
	}

	@Test(description = "AT-80679:Verify Create Rule Text", priority = 1)
	public void verifyCreateRuleText() {
		logger.info(">>>>> Verifying create rule button..");
		Assert.assertTrue(categorization.createRules().isDisplayed(), ".. Create Rule text is not displayed.");
	}

	@Test(description = "AT-80678,AT-80677:Verify No Rule Message/No Data Message", priority = 2)
	public void verifyNoRuleMessage() {
		logger.info(">>>>> verifying no rule message");
		Assert.assertTrue(categorization.noRuleSym().isDisplayed());
	}

	@Test(description = "AT-80677:Verify Create Rules Button is displayed when no data screen is present", priority = 3)
	public void verifyCreateRulesButton() {
		logger.info(">>>>> Verifying Create Rule Button.");
		Assert.assertTrue(categorization.createRulesButton().isDisplayed());
	}

	@Test(description = "AT-80538:Verify Create Rule Popup is displayed", priority = 4)
	public void verifyCreateRulePopup() {
		logger.info(">>>>> Verifying popup opens once Create Rule button is clicked..");
		SeleniumUtil.click(categorization.createRulesButton());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(categorization.createPopUp().isDisplayed(), ".. Cretae Rule popup did not open..");
	}

	@Test(description = "AT-80538:Verify Create Rule Popup Header", priority = 5, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyPopupHeader() {
		Assert.assertEquals(categorization.createRulePopUp().getText().trim(),
				PropsUtil.getDataPropertyValue("PopUpHeader_CreateRule_CatRules"), ".. Popup header mismatch..");
	}

	@Test(description = "AT-80538:Verify Create Rule Button is displayed in the popup", priority = 6, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyCreateRuleButton() {
		logger.info(">>>>> Verifying Create Rule button in the popup..");
		Assert.assertTrue(categorization.createRuleBtn().isDisplayed(), ".. Create Rule button not displayed.");
	}

	@Test(description = "AT-80538:Verify popup contains 'We will apply this rule to all your future transactions' option", priority = 7, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyPopupComponents1() {
		logger.info(">>>>> Verifying 'We will apply this rule to all your future transactions' is displayed..");
		Assert.assertEquals(categorization.popUpDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("PopUpDescription_CatRules").trim(),
				".. Transaction permission mismatch..");

	}

	@Test(description = "AT-80538:Verify Toggle Button 1", priority = 8, dependsOnMethods = { "verifyCreateRulePopup" })
	public void verifyToggleButton1() {
		logger.info(">>>>> verifying toggle button 1 in the popup");
		Assert.assertTrue(categorization.popUpToggleBtn1().isDisplayed(), ".. Toggle button 1 is not displayed..");
	}

	@Test(description = "AT-80542:Verify description textbox in the popup", priority = 9, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyDescriptionTextbox() {
		logger.info(">>>>> Verifying description textbox.");
		Assert.assertTrue(categorization.popUpDescBox().isDisplayed(), ">>>>> Description textbox is not displayed");
	}

	@Test(description = "AT-80538:Verify the description box content", priority = 10, dependsOnMethods = {
			"verifyDescriptionTextbox" })
	public void verifyDescriptionTextboxContent() {
		logger.info(">>>>> Verifying description content.");
		Assert.assertEquals(categorization.popUpDescContains().getText().trim(),
				PropsUtil.getDataPropertyValue("PopUpDescContains_CatRules").trim(),
				".. Description textbox content mismatch");
	}

	@Test(description = "AT-80538:Verify 'If transaction amount is Text box with todggle button' in popup", priority = 11, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyToggleButton2() {
		logger.info(">>>>> Verifying toggle button 2");
		Assert.assertTrue(categorization.popUpToggleBtn2().isDisplayed(), ".. Toggle button 2 is not displayed.");

	}

	@Test(description = "AT-80538:Verify Ammount Text Box in popup", priority = 12, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmmountTextbox() {
		logger.info(">>>>> Verifying Ammount text box..");
		Assert.assertEquals(categorization.popUpAmt().isDisplayed(), true, "... Amount TextBox is not displayed.");
	}

	@Test(description = "AT-80538:Verify the 'Exactly' dropdown in popup", priority = 13, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyExactlyDropdown() {
		logger.info(">>>>> Verifying 'exactly' dropdown.");
		Assert.assertTrue(categorization.popUpAmtExactly().isDisplayed(), ".. Exactly dropdown is not displayed.");
	}

	@Test(description = "AT-80538:Verify 'Transaction amount is' text", priority = 14, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyTransactionText() {
		logger.info(">>>>> Verifying 'transaction amount is' text");
		Assert.assertEquals(categorization.popUpTransAmt().getText(),
				PropsUtil.getDataPropertyValue("TransctionAmountIs_Text_CatRules"), ".. Text mismatch..");

	}

	@Test(description = "AT-80538:Verify text 'Categorize transaction as' text", priority = 15, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyCategorizeAsText() {
		logger.info(">>>>> Verifying text 'Then categorize the transaction as'..");
		Assert.assertEquals(categorization.popUpThenCategorizeTrans().getText().trim(),
				PropsUtil.getDataPropertyValue("CategorizeTransactionAs_Text_CatRules").trim(), "... Text mismatch.");

	}

	@Test(description = "AT-80549,AT-80550,AT-80551:Verifying select Category dropdown in the popup.", priority = 16, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifySelectCategoryDropdown() {
		logger.info(">>>>> Verifying secelt category dropdown.");
		Assert.assertTrue(categorization.popUpSelectCategory().isDisplayed(),
				"... Select category dropdown not present.");
	}

	@Test(description = "AT-80539,AT-80608:Verify te text 'Apply rule to past transactionsApply rule to past transactions' in popup", priority = 17, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyApplyRuleText() {
		logger.info(">>>>> verifying Apply Rule to past transaction text..");
		Assert.assertEquals(PropsUtil.getDataPropertyValue("ApplyRule_Text_CatRules").trim(),
				(categorization.popUpApplyRule().getText().trim()), "... Label Mismatch");
	}

	@Test(description = "AT-80609,AT-80610:Verify Toggle Button 3", priority = 18, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyToggleButton3() {
		logger.info(">>>>> Verifying toggle button 3");
		Assert.assertTrue(categorization.popUpToggleBtn3().isDisplayed(), ".. toggle button 3 is not displayed");
	}

	@Test(description = "AT-80542:Verify the text 'If transaction description contains' in popup", priority = 19, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyIfTransactionText() {
		logger.info(">>>>> Verifying the text");
		Assert.assertEquals(categorization.ifTransText().getText().trim(),
				PropsUtil.getDataPropertyValue("ifTransactionContains_Text_CatRules").trim(), "... Text mismatch");
	}

	@Test(description = "AT-80545,AT-80546,AT-80547,AT-80548:Verify the text 'If transaction amount is' ", priority = 20, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyIfAmountText() {
		Assert.assertEquals(categorization.ifAmtText().getText(),
				PropsUtil.getDataPropertyValue("ifAmountIs_Text_CatRules"));

	}

	@Test(description = "AT-80542,AT-80614:Verify ammount description textbox", priority = 21, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmountDescBox() {
		Assert.assertTrue(categorization.amtDescBox().isDisplayed(),
				".. Amount description textbox is not displayed..");
	}

	@Test(description = "AT-80547,AT-80548:Verify amount dropdown in popup", priority = 22, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmountDropdown() {
		Assert.assertTrue(categorization.amtDropDown().isDisplayed(), ".. Amount dropdown not displayed.");
	}

	@Test(description = "AT-80544:Verify the text 'Then categorize the transaction as' in popup", priority = 23, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyThenCategorizeText() {
		Assert.assertEquals(categorization.categoriseAsText().getText(),
				PropsUtil.getDataPropertyValue("ThenCategorize_Text_CatRules"), ".. text mismatch");
	}

	@Test(description = "AT-80549,AT-80550,AT-80551,AT-80552:Verify Categories Dropdown in popup", priority = 24, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyCategoriesDropdown() {
		Assert.assertTrue(categorization.categoriesDropDown().isDisplayed(),
				"..Categories dropdown is not displayed..");
	}

	@Test(description = "AT-80611,AT-80653:Verify Create Rule ID Button", priority = 25, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyCreateRuleIDBtn() {
		Assert.assertTrue(categorization.createRuleIdButtn().isDisplayed(), ".. Create Rule button not displayed.");
	}

	@Test(description = "AT-80622:Verify Amount switch toggle button in popup", priority = 26, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmountSwitchToggleButton() {
		Assert.assertTrue(categorization.amtSwitchToggle().isDisplayed(),
				".. Amount Switch Toggle Button not displayed.");
	}

	@Test(description = "AT-80572:Verify Apply Rule toggle button in popup", priority = 28, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyApplyRuleToggleButton() {
		Assert.assertTrue(categorization.applyRuleSwitchToggle().isDisplayed(),
				".. Apply Rule toggle button not displayed");
	}

	@Test(description = "AT-80543:Verify by default Transaction toggle is disabled", priority = 29, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void tesverifyTransactionToggleDisabled() {
		Assert.assertEquals(categorization.transSwitchValidation().getAttribute("class").toString(),
				PropsUtil.getDataPropertyValue("disabledClass_CatRules"), ".. toggle button not disabled");
	}

	@Test(description = "AT-80627:Verify by default Amount toggle is disabled", priority = 30, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmountToggleDisabled() {
		Assert.assertEquals(categorization.amtSwitchValidation().getAttribute("class").toString(),
				PropsUtil.getDataPropertyValue("disabledClass_CatRules"), ".. Amount toggle not disabled..");

	}

	@Test(description = "Verify apply rule toggle is disabled by difault", priority = 31, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyApplyRuleToggleDisabled() {
		/*
		 * Assert.assertEquals(categorization.applyRuleSwitchValidation().getAttribute(
		 * "class").toString(),
		 * PropsUtil.getDataPropertyValue("disabledClass_CatRules"),
		 * ".. toggle not disabled");
		 */

	}

	@Test(description = "AT-80548:Verify amount textbox enabled", priority = 35, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmountTextboxEnabled() {
		Assert.assertEquals(categorization.amtSwitchValidation().getAttribute("class").toString(),
				PropsUtil.getDataPropertyValue("enabledClass_CatRules"), ".. amount textbox not enabled");
	}

	@Test(description = "AT-80568:Verify ammount dropdown value", priority = 38, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyAmountDropdownValue1() {
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(categorization.getAmountTypeList().get(0).getText(),
				PropsUtil.getDataPropertyValue("amountDropdownText1"), ".. dropdown value mismatch");
	}

	@Test(description = "AT-80568:Verify ammount dropdown value", priority = 39, dependsOnMethods = {
			"verifyAmountDropdownValue1" })
	public void verifyAmountDropdownValue2() {
		Assert.assertEquals(categorization.getAmountTypeList().get(1).getText(),
				PropsUtil.getDataPropertyValue("amountDropdownText2"), ".. dropdown value mismatch");
	}

	@Test(description = "Verify dropdown selection", priority = 40, dependsOnMethods = { "verifyAmountDropdownValue2" })
	public void verifyAmountDropdownSelection() {
		SeleniumUtil.click(categorization.getAmountTypeList().get(0));
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(categorization.exactlySelected().isDisplayed(), ".. dropdown value not selected.");
	}

	@Test(description = "AT-80545:Verify Selection of between option for account dropdown", priority = 41, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifySelectingBetweenOption() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(categorization.getAmountTypeList().get(1));
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertTrue(categorization.betweenTicSelected().isDisplayed(), ".. between option not selected");
			SeleniumUtil.click(categorization.amtDropDown());
		} else {
			SeleniumUtil.click(categorization.amtDropDown());
			SeleniumUtil.waitForPageToLoad(4000);
			if(!SeleniumUtil.isDisplayed(SeleniumUtil.getByObject("CategorzationRules", null, "betweenTicSelected"), 5)) {
			fail("Between option is not selected in the dropdown");
			}
		}
	}

	@Test(description = "AT-80548:Verify the second text box for amount is displayed", priority = 43)
	public void verifySecondTextBox() {
		Assert.assertTrue(categorization.betweenTextBox().isDisplayed(), ".. textbox not displayed..");
	}

	@Test(description = "AT-80549:Verify the LLC available in the dropdown", priority = 44, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyLowLevelCategoryAvailable() {
		SeleniumUtil.click(categorization.categoriesDropDown());
		Assert.assertEquals(categorization.getCategoryLL().size(), Integer.parseInt("59"),
				".. misamatch of LLC num available");
	}

	@Test(description = "AT-80550:Verify the HLC available in the dropdown", priority = 45, dependsOnMethods = {
			"verifyLowLevelCategoryAvailable" })
	public void verifyHighLevelCategoriesAvailable() {
		Assert.assertEquals(categorization.getCategoryHighL().size(), Integer.parseInt("18"),
				"High level categories size is not as expected.");
	}

	@Test(description = "AT-80554,AT-80553:Verify the popup close button", priority = 46, dependsOnMethods = {
			"verifyCreateRulePopup" })
	public void verifyClosePopup() {
		SeleniumUtil.waitForPageToLoad(2000);
		if (PageParser.isElementPresent("closeCategoriesDDForMobile", "CategorzationRules", null)) {
			SeleniumUtil.click(categorization.closeCategoriesDDForMobile());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(categorization.createClosePopUp().isDisplayed(), ".. close popup button not present.");
	}

	@Test(description = "AT-80553:Verify closing popup successfully closes popup", priority = 47, dependsOnMethods = {
			"verifyClosePopup" })
	public void verifyClosingPopup() {
		SeleniumUtil.click(categorization.createClosePopUp());
		Assert.assertTrue(categorization.categorizationRules().isDisplayed(), ".. popup not closed successfully.");

	}

	@Test(description = "AT-80555:Verify Creating a rule", priority = 48)
	public void verifyCreateRuleStep1() {
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(categorization.createRulesButton());
		SeleniumUtil.waitForPageToLoad();
		categorization.descTextBox().sendKeys(PropsUtil.getDataPropertyValue("categoryText_CatRules"));
		categorization.amtDescBox().sendKeys(PropsUtil.getDataPropertyValue("categoryAmount_CatRules"));
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.click(categorization.getCategoryLL().get(19));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean status = false;
		List<WebElement> categorytickmarks = d.findElements(SeleniumUtil.getByObject("CategorzationRules", null, "travelTick"));
		for(WebElement tick : categorytickmarks) {
			if(tick.isDisplayed()) {
				status = true;
				break;
			}
		}
		if(!status) {
			fail("No cateogory is getting selected by default");
		}
	}

	@Test(description = "AT-80555:Verify Creating a rule", priority = 49, dependsOnMethods = {
			"verifyCreateRuleStep1" })
	public void VerifyCreateRuleStep2() {
		if (PageParser.isElementPresent("closeCategoriesDDForMobile", "CategorzationRules", null)) {
			SeleniumUtil.click(categorization.closeCategoriesDDForMobile());
			SeleniumUtil.waitForPageToLoad(1000);
		} else {
			SeleniumUtil.click(categorization.categoriesDropDown());
		}

		SeleniumUtil.click(categorization.applyRuleSwitchToggle());
		SeleniumUtil.click(categorization.createRuleIdButtn());
		// If statement description contains BANK OF AM HAYWARD pending and If
		// transaction amount is exactly 104 then categorize transaction as Deposits
		String actualText = categorization.getRuleText().get(0).getText();
		String expectedText = PropsUtil.getDataPropertyValue("Rule_1_CatRules");
		if (!actualText.contains(expectedText)) {
			fail("Unable to create rule.Expected :: " + expectedText + ", Actual :: " + actualText);
		}
	}

	@Test(description = "AT-80557,AT-80565:Verify running individual rule", priority = 50, dependsOnMethods = {
			"VerifyCreateRuleStep2" }, enabled = false)
	public void verifyRunningIndvidualRule() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Categorization text is :" + categorization.getCategoriesInTransaction1().getText());

		Assert.assertEquals(categorization.getCategoriesInTransaction1().getText(),
				PropsUtil.getDataPropertyValue("categorizationAmount_CatRules"));
	}

	@Test(description = "AT-80566,AT-80568:Vrify creating second rule", priority = 51, dependsOnMethods = {
			"VerifyCreateRuleStep2" })
	public void createSecondRule() {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(categorization.addNewRule());

		categorization.descTextBox().sendKeys(PropsUtil.getDataPropertyValue("categoryText2_CatRules"));
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(categorization.getAmountTypeList().get(1));
		SeleniumUtil.waitForPageToLoad(4000);
		categorization.amtDescBox().sendKeys("150");
		SeleniumUtil.waitForPageToLoad(2000);
		categorization.betweenTextBox().sendKeys("210");
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(categorization.getCategoryLL().get(8));
		SeleniumUtil.click(categorization.applyRuleSwitchToggle());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(categorization.createRuleIdButtn());
		SeleniumUtil.waitFor(2);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		String actualText = categorization.getRuleText().get(1).getText();
		String expectedText = PropsUtil.getDataPropertyValue("MyRule_CatRules");
		if (!actualText.contains(expectedText)) {
			fail("Unable to create rule.Expected :: " + expectedText + ", Actual :: " + actualText);
		}
	}

	@Test(description = "AT-80556,AT-80569:Verify Running all rules will apply the categorization in transaction", priority = 52, dependsOnMethods = {
			"createSecondRule" }, enabled = false)
	public void verifyRunningAllRule() {
		SeleniumUtil.waitForPageToLoad();
		if (PageParser.isElementPresent("moreButtonForMobile", "CategorzationRules", null)) {
			SeleniumUtil.click(categorization.moreButtonForMobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(categorization.runAll());
		} else {
			SeleniumUtil.click(categorization.runAll());
		}
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(categorization.getCategoriesInTransaction3().getText(),
				PropsUtil.getDataPropertyValue("categoryRule2_CatRules"), ".. Rules not applies");
	}

	@Test(description = "AT-80558,AT-80559:VerifyCategorizationValues", priority = 53, dependsOnMethods = {
			"verifyRunningAllRule" }, enabled = false)
	public void verifyCategoryInTransaction() {
		Assert.assertEquals(categorization.getCategoriesInTransaction1().getText(),
				PropsUtil.getDataPropertyValue("catTextValAmountAssert"), ".. Rules Not applied.");
	}

	@Test(description = "Create Rule 3", priority = 54, dependsOnMethods = { "createSecondRule" })
	public void createRule3() {
		PageParser.forceNavigate("CategorzationRules", d);
		if (!Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			SeleniumUtil.click(categorization.addNewRule());
		}
		categorization.descTextBox().sendKeys(PropsUtil.getDataPropertyValue("categoryText3_CatRules"));
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.waitFor(1.5f);
		SeleniumUtil.click(categorization.getCategoryLL().get(5));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(categorization.applyRuleSwitchToggle());
		SeleniumUtil.click(categorization.createRuleIdButtn());
		SeleniumUtil.waitFor(2);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.waitForPageToLoad(3000);
		String actualText = categorization.getRuleText().get(2).getText();
		String expectedText = PropsUtil.getDataPropertyValue("Rule_3_CatRules");
		if (!actualText.contains(expectedText)) {
			fail("Unable to create rule.Expected :: " + expectedText + ", Actual :: " + actualText);
		}
	}

	@Test(description = "AT-80576:Verify Running All rule", priority = 55, dependsOnMethods = {
			"createRule3" }, enabled = false)
	public void verifyCreateRunningAllRule() {
		SeleniumUtil.waitForPageToLoad();
		if (PageParser.isElementPresent("moreButtonForMobile", "CategorzationRules", null)) {
			SeleniumUtil.click(categorization.moreButtonForMobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(categorization.runAll());
		} else {
			SeleniumUtil.click(categorization.runAll());
		}
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(categorization.getCategoriesInTransaction1().getText(),
				PropsUtil.getDataPropertyValue("categoryRule1_CatRules"));
	}

	@Test(description = "AT-80563:Verify Rules shall not be applied to those transactions that the user re-categorizes at transaction level or account level", priority = 56, dependsOnMethods = {
			"verifyCreateRunningAllRule" }, enabled = false)
	public void verifyNoRecategorizing() {
		PageParser.forceNavigate("Transaction", d);
		logger.info("Categorization text message posted2 is :" + categorization.posted2().getText());
		SeleniumUtil.click(categorization.posted2());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(categorization.CategoryDropDown());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(categorization.getcategoryInCharitable().get(1));
		logger.info("Category in Charitable first is " + categorization.getcategoryInCharitable().get(1).getText());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(categorization.SaveBttn());
		SeleniumUtil.waitForPageToLoad(3000);

		PageParser.forceNavigate("CategorzationRules", d);
		if (PageParser.isElementPresent("moreButtonForMobile", "CategorzationRules", null)) {
			SeleniumUtil.click(categorization.moreButtonForMobile());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(categorization.runAll());
		} else {
			SeleniumUtil.click(categorization.runAll());
		}
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(categorization.getCategoriesInTransaction2().getText(),
				PropsUtil.getDataPropertyValue("UserCategorized_CatRules"), ".. Categories mismatch");
	}

	@Test(description = "AT-80560,AT-80572: Verify up and down button prioritizes categorization rules created", priority = 57, dependsOnMethods = {
			"verifyCreateRunningAllRule" }, enabled = false)
	public void verifyPriorityReordering() {
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.click(categorization.getUpPriority().get(2));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(categorization.getUpPriority().get(1));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(categorization.getRuleText().get(0).getText(),
				PropsUtil.getDataPropertyValue(("ruleNo3_CatRules")));
	}

	@Test(description = "AT-80561,AT-80570,AT-80572:Verify Category priority updated", priority = 58, dependsOnMethods = {
			"verifyPriorityReordering" }, enabled = false)
	public void verifyCategoryApplied() {
		SeleniumUtil.click(categorization.getRrunButton().get(0));
		SeleniumUtil.waitForPageToLoad(5000);

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(6000);

		Assert.assertEquals(categorization.getCategoriesInTransaction3("Consulting").getText(),
				PropsUtil.getDataPropertyValue("categoryRule3_CatRules"));
	}

	@Test(description = "AT-80562:Verify category transaction", priority = 59, dependsOnMethods = {
			"verifyCategoryApplied" }, enabled = false)
	public void verifyCategoriesApplied() {
		Assert.assertEquals(categorization.getCategoriesInTransaction3("Entertainment").getText(),
				PropsUtil.getDataPropertyValue("categoryRule2_CatRules"));
	}

	@Test(description = "AT-80601,AT-80602:Verify Invalid amount entry", priority = 59, dependsOnMethods = {
			"createRule3" })
	public void verifyInvalidAmountEntry1() {
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.click(categorization.addNewRule());
		categorization.descTextBox().sendKeys(PropsUtil.getDataPropertyValue("characterLimit_CatRules"));
		SeleniumUtil.waitForPageToLoad(2000);
		categorization.amtDescBox().clear();
		// SeleniumUtil.click(categorization.amtDropDown());
		// SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.click(categorization.getAmountTypeList().get(1));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad(4000);
		categorization.amtDescBox().clear();
		categorization.amtDescBox().sendKeys("15000");
		categorization.betweenTextBox().sendKeys("0");
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.click(categorization.getCategoryLL().get(8));
		// SeleniumUtil.click(categorization.createRuleIdButtn());
		SeleniumUtil.waitForPageToLoad(5000);
		if (categorization.iscatgclosebtn()) {
			SeleniumUtil.click(categorization.catgclosebtn());
			SeleniumUtil.click(categorization.applyRuleSwitchToggle());
			SeleniumUtil.click(categorization.createRuleIdButtn());
		} else {
			SeleniumUtil.click(categorization.applyRuleSwitchToggle());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(categorization.createRuleIdButtn());
		}

		Assert.assertTrue(categorization.invalidAmtText().isDisplayed(), ".. Invalid amount text is displayed.");
	}

	@Test(description = "AT-80602:Verify Invalid amount entry", priority = 60, dependsOnMethods = {
			"verifyInvalidAmountEntry1" })
	public void verifyInvalidAmountEntry2() {
		categorization.amtDescBox().clear();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(categorization.getAmountTypeList().get(0));
		categorization.amtDescBox().clear();
		categorization.amtDescBox().sendKeys("-150000000.0000000000");
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(categorization.createRuleIdButtn());
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertTrue(categorization.invalidAmtText1().isDisplayed());
	}

	@Test(description = "AT-80620,AT-80628:Verify editing the rule created", priority = 61, dependsOnMethods = {
			"createRule3" })
	public void verifyEditPopup() {
		SeleniumUtil.click(categorization.createClosePopUp());
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(categorization.dropdwnOption().get(2));
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(categorization.dropdwnOPtionEdit());
		SeleniumUtil.waitForPageToLoad(2500);
		Assert.assertTrue(categorization.editPopUp().isDisplayed(), ".. edit popup not displayed.");
	}

	@Test(description = "AT-80564:Verify editing rule", priority = 62, dependsOnMethods = { "verifyEditPopup" })
	public void verifyEditRule() {
		SeleniumUtil.waitForPageToLoad(2000);
		categorization.descTextBox().clear();
		SeleniumUtil.waitForPageToLoad(3000);
		categorization.descTextBox().sendKeys("    ");
		SeleniumUtil.waitForPageToLoad(2000);
		categorization.amtDescBox().clear();
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.click(categorization.getAmountTypeList().get(1));
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(categorization.amtDropDown());
		SeleniumUtil.waitForPageToLoad(4000);
		categorization.amtDescBox().clear();
		categorization.amtDescBox().sendKeys("100");

		categorization.betweenTextBox().sendKeys("103");
		SeleniumUtil.click(categorization.categoriesDropDown());
		SeleniumUtil.click(categorization.getCategoryLL().get(7));
		SeleniumUtil.click(categorization.applyRuleSwitchToggle());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(categorization.SaveChgBttn());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.waitForPageToLoad(3000);
		String actualText = categorization.getRuleText().get(2).getText();
		String expectedText = PropsUtil.getDataPropertyValue("ruleNo5_CatRules");
		if (!actualText.contains(expectedText)) {
			fail("Edited rule changes are not reflecting.Expected :: " + expectedText + ", Actual :: " + actualText);
		}
	}

	@Test(description = "AT-80552:Verify edited rules reflect in transaction", priority = 63, dependsOnMethods = {
			"verifyEditRule" }, enabled = false)
	public void verifyEdidtedRuleAppliedInTransaction() {
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(categorization.getCategoriesInTransaction().get(1).getText(),
				PropsUtil.getDataPropertyValue("categoryRule4_CatRules"));
	}

	@Test(description = "AT-80642:Verify Delete popup", dependsOnMethods = { "createRule3" }, priority = 64)
	public void verifyDeleteRulePopup() {
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.click(categorization.dropdwnOption().get(0));
		SeleniumUtil.click(categorization.dropdwnOPtionDelete());
		Assert.assertTrue(categorization.deletPopUp().isDisplayed(), ".. Delete Rule popup not displayed.");
	}

	@Test(description = "AT-80644:Verify delete popup text", priority = 65, dependsOnMethods = {
			"verifyDeleteRulePopup" })
	public void verifyDeletePopupText() {
		Assert.assertEquals(categorization.deletPopUpText().getText(),
				PropsUtil.getDataPropertyValue("DeletePopup_Text_CatRules"), ".. Delete Popup text mismatch.");
	}

	@Test(description = "AT-80645:Verify Delete Statement in the popup", priority = 66, dependsOnMethods = {
			"verifyDeleteRulePopup" })
	public void verifyDeleteStatement() {
		Assert.assertEquals(categorization.deletPopUpStatement().getText(),
				PropsUtil.getDataPropertyValue("DeleteStatement_CatRules"), ".. Delete Statement mismatch");
	}

	@Test(description = "AT-80643:Verify cancel button the delete popup", priority = 67, dependsOnMethods = {
			"verifyDeleteRulePopup" })
	public void verifyCancleButtonDeletePopup() {
		Assert.assertTrue(categorization.deletPopUpCancel().isDisplayed(), ".. cancle button not applied");

	}

	@Test(description = "AT-80644:VerifyDelete button in popup", priority = 68, dependsOnMethods = {
			"verifyDeleteRulePopup" })
	public void verifyDeleteButtonDeletePopup() {
		Assert.assertTrue(categorization.deletPopUpDelete().isDisplayed(), ".. delete button not displayed..");
	}

	@Test(description = "AT-80645:Verify close icon in the popup", priority = 69, dependsOnMethods = {
			"verifyDeleteRulePopup" })
	public void verifyCloseIconDeletePopup() {
		Assert.assertTrue(categorization.deletPopUpClose().isDisplayed(), ".. Close icon not displayed..");
	}

	@Test(description = "AT-80645:Verify closing the popup", priority = 70, dependsOnMethods = {
			"verifyCloseIconDeletePopup" })
	public void verifyClosingDeletePopup() {
		SeleniumUtil.click(categorization.deletPopUpClose());
		Assert.assertTrue(categorization.dropdwnOption().get(1).isDisplayed(), ".. popup not closed");

	}

	@Test(description = "AT-80646:verify Cancle button behavior", priority = 71, dependsOnMethods = {
			"verifyClosingDeletePopup" })
	public void verifyCanclellingDeleteRuleOption() {
		SeleniumUtil.click(categorization.dropdwnOption().get(0));
		SeleniumUtil.click(categorization.dropdwnOPtionDelete());
		SeleniumUtil.click(categorization.deletPopUpCancel());

		Assert.assertTrue(categorization.dropdwnOption().get(1).isDisplayed(), ".. Cancle button not displayed");
	}

	@Test(description = "AT-80652:Verify deleting the Rule", priority = 72, dependsOnMethods = {
			"verifyCanclellingDeleteRuleOption" })
	public void verifyDeletingRule() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(categorization.dropdwnOption().get(1));
		SeleniumUtil.click(categorization.dropdwnOPtionDelete());
		SeleniumUtil.click(categorization.deletPopUpDelete());
		SeleniumUtil.waitForPageToLoad();
		logger.info("Size is:" + categorization.getRuleText().size());

		Assert.assertEquals(categorization.getRuleText().size(), Integer.parseInt("2"), ".. rule not deleted..");
	}

	@Test(description = "AT-95979:verify creat rule info message is displaying", priority = 73, dependsOnMethods = {
			"verifyDeletingRule" })
	public void verifyCreatRuleInfoMessage() {
		SeleniumUtil.click(categorization.createRulesButton1());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(categorization.createRuleInfoMessage().getText().trim(),
				PropsUtil.getDataPropertyValue("creatRuleInfoMessage"), "creat rule info message is not displayed");
	}

}
