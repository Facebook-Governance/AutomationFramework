/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.ManageCategories;

import static org.testng.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Categories.Manage_Categories_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Revamp_Manage_Categories_Test2 extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(Revamp_Manage_Categories_Test2.class);
	Manage_Categories_Loc manage_Categories_Loc = null;
	AccountAddition accAddition = new AccountAddition();

	String originalName = "";

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Manage Categories Revamping");
		manage_Categories_Loc = new Manage_Categories_Loc(d);
		logger.info("Initializing");
		LoginPage.loginMain(d, loginParameter);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("wellness_nw.site16441.1", "site16441.1");
	}

	@Test(description = "CATEG_02_001:Enusre that special characters are allowed while editing the HLC category name ", priority = 1, groups = {
			"Desktop" }, enabled = true)
	public void verifySpecialChar() throws Exception {
		manage_Categories_Loc.forceNavigateToCategories();
		manage_Categories_Loc.selectSideBarByNumber(2);
		String text = manage_Categories_Loc.getHighLevelCategoryName(1);
		manage_Categories_Loc.clickHighLevelCategoryByName(text);
		manage_Categories_Loc.editHLCatInputField().clear();
		manage_Categories_Loc.editHLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_specialname"));
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(2);
		String specialChartext = manage_Categories_Loc.Cat_HeaderText().getText();
		Assert.assertEquals(specialChartext, PropsUtil.getDataPropertyValue("Category_specialname"));
	}

	@Test(description = "CATEG_02_002:Ensure that if user edit the HLC name and later remove that complete text in the text box then save changes must be disabled", priority = 2, groups = {
			"Desktop" })
	public void verifySaveBtnDisabled() throws Exception {
		originalName = manage_Categories_Loc.getHighLevelCategoryName(1);
		logger.info(originalName);
		manage_Categories_Loc.clickHighLevelCategoryByNum(1);
		manage_Categories_Loc.editHLCatInputField().clear();
		if (!SeleniumUtil.getAttribute(manage_Categories_Loc.saveChangesBtn(), "class").toLowerCase()
				.contains("disabled")) {
			Assert.fail("Save changes button is not getting disabled when edit category input field is empty");
		}
		manage_Categories_Loc.clickHighLevelCategoryByNum(1);
	}

	@Test(description = "CATEG_02_003:Ensure that when user click on other HLC while he is editing one HLC the changes must not be saved and the other HLC edit mode must open", priority = 3, groups = {
			"Desktop" })
	public void verifyEditClickOnOtherHLC() throws Exception {
		originalName = manage_Categories_Loc.getHighLevelCategoryName(1);
		logger.info(originalName);
		manage_Categories_Loc.clickHighLevelCategoryByNum(1);
		manage_Categories_Loc.editHLCatInputField().clear();
		manage_Categories_Loc.editHLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditText"));
		manage_Categories_Loc.clickHighLevelCategoryByNum(2);
		logger.info("Category name is" + manage_Categories_Loc.getHighLevelCategoryName(2));
		Assert.assertTrue(manage_Categories_Loc.getHighLevelCategoryName(1).contains(originalName));
	}

	@Test(description = "CATEG_02_004:Ensure that the text box has the original name initially when user tires to edit category", priority = 4, groups = {
			"Desktop" })
	public void verifyFirstCatName() throws Exception {
		originalName = manage_Categories_Loc.getHighLevelCategoryName(1);
		logger.info(originalName);
		manage_Categories_Loc.clickHighLevelCategoryByNum(1);
		manage_Categories_Loc.editHLCatInputField().clear();
		manage_Categories_Loc.editHLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditText"));
		manage_Categories_Loc.editHLCatInputField().clear();
		manage_Categories_Loc.cancelButton().click();
		logger.info("Category name is" + manage_Categories_Loc.getHighLevelCategoryName(1));
		String expected = originalName;
		String actual = manage_Categories_Loc.getHighLevelCategoryName(1);
		if (!actual.contains(expected)) {
			fail("First category name is not as expected. Expected :: " + expected + " && Actual :: " + actual);
		}
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {

		}
	}

}
