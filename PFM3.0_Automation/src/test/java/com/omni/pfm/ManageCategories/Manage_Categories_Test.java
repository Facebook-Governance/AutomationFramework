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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Categories.Manage_Categories_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Manage_Categories_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(Manage_Categories_Test.class);
	Manage_Categories_Loc manage_Categories_Loc = null;
	AccountAddition accAddition = new AccountAddition();

	String originalName = "";
	private AccountAddition accountAdd;

	@BeforeClass()
	public void init() {
		doInitialization("Manage Categories");
		manage_Categories_Loc = new Manage_Categories_Loc(d);
		accountAdd = new AccountAddition();
		logger.info("Initializing");

	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);

		logger.info("Add Dag Site");
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("ihjuly1.site16441.1", "site16441.1");
	}

	@Test(description = "CATEG_01_01:Verify the header title", priority = 2, groups = { "Desktop" })
	public void verifyHeaderTitle() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		logger.info("Navigating to categories Page");
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad(3000);
		String header = manage_Categories_Loc.getHeaderText().trim();

		Assert.assertTrue(header.contains("Categories"));

	}

	@Test(description = "CATEG_01_02:Verify the user friendly text", priority = 3, groups = { "Desktop" })
	public void verifyUserText() throws Exception {
		String messageText = manage_Categories_Loc.getCategoriesMessage().trim();

		Assert.assertEquals(messageText, PropsUtil.getDataPropertyValue("user_friendly_text"));
	}

	@Test(description = "CATEG_01_03:Verify that left selection tab is displayed.", priority = 4, groups = {
			"Desktop" })
	public void verifyLeftSelectionText() throws Exception {
		if (manage_Categories_Loc.isMobileMenu()) {

			boolean result = manage_Categories_Loc.topMobileMenubar().isDisplayed();
			Assert.assertTrue(result);
		} else {

			boolean result = manage_Categories_Loc.leftSelectionSection().isDisplayed();
			Assert.assertTrue(result);
		}

	}

	@Test(description = "CATEG_01_04:Ensure that selection sidebar have the list of names as: EXPENSE INCOME TRANSFER DEFERRED ", priority = 4, groups = {
			"Desktop" })
	public void verifySelectionSideBarOrder() throws Exception {
		String sidebar[] = manage_Categories_Loc.getCategoryGroupByOrder();

		String expectedSidebar[] = PropsUtil.getDataPropertyValue("sidebar_name").trim().split(",");

		Assert.assertEquals(sidebar, expectedSidebar);

		Assert.assertTrue(manage_Categories_Loc.isExpenseSelected().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("Category_active")));
	}

	@Test(description = "CATEG_01_05:Verify High Level and Master level category names under expense", priority = 5, groups = {
			"Desktop" })
	public void verifyHLCMLCUnderExpense() throws Exception {
		String hlc[] = manage_Categories_Loc.getHLC();
		String expectedHLCunderExpense = PropsUtil.getDataPropertyValue("HLC_EXPENSE").trim();

		String mlc[] = manage_Categories_Loc.getMLCs();
		String expectedMLCunderExpense = PropsUtil.getDataPropertyValue("MLC_EXPENSE").trim();

		SeleniumUtil.assertExpectedActualList(Arrays.asList(hlc), expectedHLCunderExpense,
				"High level categories are not matching"); // enter

		SeleniumUtil.assertExpectedActualList(Arrays.asList(mlc), expectedMLCunderExpense,
				"Medium level categories are not matching");
	}

	@Test(description = "CATEG_01_06:Verify High Level and Master level category names under income", priority = 6, groups = {
			"Desktop" })
	public void verifyHLCMLCUnderIncome() throws Exception {
		// manage_Categories_Loc.selectSideBarByNumber(2);
		SeleniumUtil.click(manage_Categories_Loc.selectIncomeTab());

		String hLC[] = manage_Categories_Loc.getHLC();
		String expectedHLCunderIncome[] = PropsUtil.getDataPropertyValue("HLC_INCOME").trim().split(",");

		String mLC[] = manage_Categories_Loc.getMLCs();
		String expectedMLCunderIncome[] = PropsUtil.getDataPropertyValue("MLC_INCOME").trim().split(",");

		Assert.assertEquals(hLC, expectedHLCunderIncome); // 1234

		Assert.assertEquals(mLC, expectedMLCunderIncome);
	}

	@Test(description = "CATEG_01_07:Verify High Level and Master level category names under Transfer", priority = 7, groups = {
			"Desktop" })
	public void verifyHLCMLCUnderTransfer() throws Exception {
		// manage_Categories_Loc.selectSideBarByNumber(3);
		SeleniumUtil.click(manage_Categories_Loc.selectTransferTab());

		String HLC[] = manage_Categories_Loc.getHLC();

		String expectedHLCunderTransfer[] = PropsUtil.getDataPropertyValue("HLC_TRANSFER").trim().split(",");

		String MLC[] = manage_Categories_Loc.getMLCs();

		String expectedMLCunderTransfer[] = PropsUtil.getDataPropertyValue("MLC_TRANSFER").trim().split(",");

		Assert.assertEquals(MLC, expectedMLCunderTransfer);
		Assert.assertEquals(HLC, expectedHLCunderTransfer);
	}

	@Test(description = "CATEG_01_08:Verify High Level and Master level category names under Deferred", priority = 8, groups = {
			"Desktop" }, enabled = true)
	public void verifyHLCMLCUnderDeferred() throws Exception {
		// manage_Categories_Loc.selectSideBarByNumber(4);
		SeleniumUtil.click(manage_Categories_Loc.selectDeferredTab());

		String HLC[] = manage_Categories_Loc.getHLC();

		String expectedHLCunderDeferred[] = PropsUtil.getDataPropertyValue("HLC_DEFERRED").split(",");

		String MLC[] = manage_Categories_Loc.getMLCs();

		String expectedMLCunderDeferred[] = PropsUtil.getDataPropertyValue("MLC_DEFERRED").split(",");

		Assert.assertEquals(MLC, expectedMLCunderDeferred);
		Assert.assertEquals(HLC, expectedHLCunderDeferred);
	}

	@Test(description = "CATEG_01_09:Verify High Level category edit fields.", priority = 9, groups = { "Desktop" })
	public void verifyEditHighLevelCategoryFields() throws Exception {
		// manage_Categories_Loc.selectSideBarByNumber(3);
		SeleniumUtil.click(manage_Categories_Loc.selectTransferTab());

		// manage_Categories_Loc.selectSideBarByNumber(2);
		SeleniumUtil.click(manage_Categories_Loc.selectIncomeTab());

		String catName = manage_Categories_Loc.getHighLevelCategoryName(1);

		manage_Categories_Loc.clickHighLevelCategoryByNum(1);

		boolean b1 = manage_Categories_Loc.showAsText().isDisplayed();

		boolean b2 = manage_Categories_Loc.editHLCatInputField().isDisplayed();

		SeleniumUtil.waitForPageToLoad();

		boolean b3 = manage_Categories_Loc.cancelButton().isDisplayed();

		Assert.assertTrue(b3);

		boolean b4 = manage_Categories_Loc.saveChangesButton().isDisplayed();

		String hostText = manage_Categories_Loc.editHLCatInputField().getAttribute("value");

		Assert.assertTrue(b1);

		Assert.assertTrue(b2);

		Assert.assertTrue(b4);

		Assert.assertEquals(hostText, catName);
	}

	@Test(description = "CATEG_01_10:Ensure that when user click on the cancel button without any change in the HLC , edit should close.", priority = 10, groups = {
			"Desktop" })
	public void verifyCancelBtnFunctionality() throws Exception {
		manage_Categories_Loc.editHLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_Text"));
		SeleniumUtil.click(manage_Categories_Loc.cancelButton());
		By showAsText = SeleniumUtil.getByObject("Categories", null, "showAsText_MC");
		if (SeleniumUtil.isDisplayed(showAsText, 5))
			fail("The Show As text is displayed");
		String text = manage_Categories_Loc.textCategory().getText();
		Assert.assertNotEquals(text, PropsUtil.getDataPropertyValue("Category_Text"));
	}

	@Test(description = "L1-41899:CATEG_01_11:Ensure if user try to edit the HLC name it must be max 40 characters.Ensure that when user click on the cancel button without any change in the HLC , edit should close.", priority = 11, groups = {
			"Desktop" })
	public void verifySaveBtnFunctionality() throws Exception {
		originalName = manage_Categories_Loc.getHighLevelCategoryName(1);

		logger.info(originalName);

		manage_Categories_Loc.clickHighLevelCategoryByNum(1);

		SeleniumUtil.waitForPageToLoad(2000);
		manage_Categories_Loc.editHLCatInputField().clear();
		manage_Categories_Loc.editHLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditText"));

		String maxCharLimit = manage_Categories_Loc.editHLCatInputField().getAttribute("value");

		manage_Categories_Loc.editHLCatInputField().clear();

		manage_Categories_Loc.editHLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditNameAsNum"));

		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());

		SeleniumUtil.waitForPageToLoad(2000);

		// manage_Categories_Loc.selectSideBarByNumber(2);
		SeleniumUtil.click(manage_Categories_Loc.selectIncomeTab());

		SeleniumUtil.waitForPageToLoad(2000);

		boolean b = manage_Categories_Loc
				.isEditedHLCategoryNameExist(PropsUtil.getDataPropertyValue("Category_EditNameAsNum"));

		Assert.assertTrue(maxCharLimit.length() == 40);
		Assert.assertTrue(b);
	}

	@Test(description = "CATEG_01_12:Ensure that if the category name exist then an inline error message must be Category name already exists", priority = 12, groups = {
			"Desktop" }, enabled = true)
	// This method will fail for Mobile as it is not throwing an error as Category
	// name already exists instead of that it is updating the Category. It is
	// working fine for Web.(AutoNPR/L1)
	public void verifyduplicateNameFunctionality() throws Exception {
		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad();

		// manage_Categories_Loc.selectSideBarByNumber(2);
		SeleniumUtil.click(manage_Categories_Loc.selectIncomeTab());

		SeleniumUtil.waitForPageToLoad(2000);
		String text = manage_Categories_Loc.getHighLevelCategoryName(1);

		manage_Categories_Loc.clickHighLevelCategoryByName(text);

		SeleniumUtil.waitForPageToLoad(2000);

		manage_Categories_Loc.editHLCatInputField().clear();

		manage_Categories_Loc.editHLCatInputField().sendKeys(text);

		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());

		manage_Categories_Loc.duplicateCatNameError().isDisplayed();

		SeleniumUtil.waitForPageToLoad(2000);

		String errorText = manage_Categories_Loc.duplicateCatNameError().getText();

		Assert.assertEquals(errorText, PropsUtil.getDataPropertyValue("categoryErrorMsg"));
	}

	@Test(description = "L1-41906:CATEG_01_13:Ensure that all the HLC category name must be order in alphabetic order", priority = 13, groups = {
			"Desktop" })
	public void verifyAlphabeticalOrders() throws Exception {
		String[] hlcs = manage_Categories_Loc.getHLC();

		List<String> l = Arrays.asList(hlcs);

		LinkedList<String> ll = new LinkedList<String>(l);

		boolean b = manage_Categories_Loc.isAlphabetical(ll);

		Assert.assertTrue(b);
	}

	@Test(description = "L1-41910: CATEG_01_14:Verify edit pop texts in MLC. Ensure that user must be able to click anywhere on the row to edit the category", priority = 14, groups = {
			"Desktop" })
	public void verifyEditPopUpTextINMLC() throws Exception {
		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manage_Categories_Loc.selectIncomeTab());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		manage_Categories_Loc.clickMLC(1, 1);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		boolean result1 = manage_Categories_Loc.originalNameMLCText().isDisplayed();

		Assert.assertTrue(result1);

		boolean result2 = manage_Categories_Loc.editMLCatInputField().isDisplayed();

		boolean result3 = manage_Categories_Loc.subCategoryMLCInputBox().isDisplayed();

		Assert.assertTrue(result3);

		Assert.assertTrue(result2);
	}

	@Test(description = "CATEG_01_15:Show as ghost text have category name in it.", priority = 15, groups = {
			"Desktop" })
	public void verifyHostTextInsideMLCShowASTextBox() throws Exception {
		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(manage_Categories_Loc.selectIncomeTab());

		String mlcName = manage_Categories_Loc.getMLCName(1, 1);

		manage_Categories_Loc.clickMLC(1, 1);

		SeleniumUtil.waitForPageToLoad(2000);

		String hostText = manage_Categories_Loc.getShowAsHostText();

		Assert.assertEquals(mlcName, hostText);

	}

	@Test(description = "L1-41915, L1-41916:CATEG_01_16: Ensure that when user edit the MLC categories user must be shown with Cancel and Save Changes Buttons in MLC.", priority = 16, groups = {
			"Desktop" })
	public void verifySaveAndCancelBtnnMLC() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);

		if (PageParser.isElementPresent("backToCategoriesForMobile", "Categories", null)) {
			Assert.assertTrue(manage_Categories_Loc.backToCategoriesForMobile().isDisplayed());
		} else {
			Assert.assertTrue(manage_Categories_Loc.cancelButtonInMLC().isDisplayed());
			Assert.assertTrue(manage_Categories_Loc.cancelButtonInMLC().isEnabled());
		}
		Assert.assertTrue(manage_Categories_Loc.saveChangesButtonInMLC().isDisplayed());
	}

	@Test(description = "L1-41918,L1-41920:CATEG_01_17:Ensure in MLC we have a add button which is in disabled state as the sub-category text box is empty. Ensure that when user enter a text in the sub-category text box the add button must be in enabled state", priority = 17, groups = {
			"Desktop" })
	public void addSubCatButton() throws Exception {
		Assert.assertTrue(manage_Categories_Loc.addSubCatButtonInMLC().isDisplayed());

		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_DemoText"));

		Assert.assertTrue(manage_Categories_Loc.addSubCatButtonInMLC().isEnabled());
		// click action is not performed to add sub category
	}

	@Test(description = " L1-41930, L1-41933, L1-41924, L1-41926: CATEG_01_18:Verify Edit MLC functionality. Ensure if user try to edit the MLC name it must be max 40 characters,Ensure that if user try to edit the characters more than 40 then restrict typing beyond"
			+ "Ensure that if user change the MLC name from the original name and click on Save change button the edited name must be shown in the place of MLC name, Ensure that every time when user edit the MLC name and save it, the latest change in Name must be reflecting in the Categories page after clicking on save change button", priority = 18, groups = {
					"Desktop" })
	public void verifyEditMLCFunctionality() throws Exception {
		manage_Categories_Loc.editMLCatInputField().clear();

		manage_Categories_Loc.editMLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditText"));

		String text = manage_Categories_Loc.editMLCatInputField().getAttribute("value");

		Assert.assertTrue(text.length() == 40);

		manage_Categories_Loc.editMLCatInputField().clear();

		// add refresh here

		manage_Categories_Loc.editMLCatInputField().sendKeys(PropsUtil.getDataPropertyValue("Category_EditNameAsNum"));

		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(manage_Categories_Loc.saveChangesButtonInMLC());// this button is not clicking

		SeleniumUtil.waitForPageToLoad();

		String updatedmlcNameOne = manage_Categories_Loc.getMLCName(1, 1);

		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(updatedmlcNameOne, PropsUtil.getDataPropertyValue("Category_EditNameAsNum")); // 12345 but
																											// automotive
																											// expense

		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "L1-41927,L1-41928:CATEG_01_19: Verify duplicate Name MLC functionality. Ensure if user try to enter a MLC category name which is already existing in the list, an appropriate error message to be shown."
			+ "Ensure that the message is shown just exactly below the text box under MLC edit option functionality has been changed", priority = 19, groups = {
					"Desktop" }, enabled = true)
	public void duplicateMLCName() throws Exception {
		String dupName = manage_Categories_Loc.getMLCName(1, 1);

		logger.info("The value for dupName inside duplicateMLCName method is [" + dupName + "]");
		SeleniumUtil.waitForPageToLoad();
		manage_Categories_Loc.clickMLC(1, 1);
		SeleniumUtil.waitForPageToLoad();

		manage_Categories_Loc.editMLCatInputField().clear();

		manage_Categories_Loc.editMLCatInputField().sendKeys(dupName);

		Assert.assertTrue(manage_Categories_Loc.saveChangesButtonInMLC().isDisplayed());
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButtonInMLC());
	}

	@Test(description = "L1-41957,L1-41958: CATEG_01_20:Verify add subcategory functionlity. Ensure that subcategory name must be restricted to enter beyond 40 characters,Ensure that subcateogy must be max of 40 characters only ", priority = 20, groups = {
			"Desktop" })
	public void addSubcategory() throws Exception {
		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manage_Categories_Loc.expenseCatLink());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));

		SeleniumUtil.waitForPageToLoad(3000);

		for (int i = 1; i <= 6; i++) {
			manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_SubCatName") + i);

			SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());

			SeleniumUtil.waitForPageToLoad();
		}

		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_SubCatNameNotMoreThan"));
		String text = manage_Categories_Loc.getSubCategoryName();
		Assert.assertTrue(text.length() == 40);

		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "L1-41962:CATEG_01_21:Verify Deleting the subcategory functionlity.", priority = 21, groups = {
			"Desktop" }, enabled = true)
	public void verifyDeleteSubCategoryFunctionality() throws Exception {
		manage_Categories_Loc.clickDeleteSubCategoryButton(1);
		SeleniumUtil.waitForPageToLoad();
		By cat1 = SeleniumUtil.getByObject("Categories", null, "getSubCatOne");
		if (SeleniumUtil.isDisplayed(cat1, 2))
			fail("The subCat1 is displayed");
	}

	@Test(description = "CATEG_01_22:Verify Max subcategory functionlity.", priority = 22, groups = { "Desktop" })
	public void verifyMaxLimitErr() throws Exception {
		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_subCatSecondLast"));

		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());

		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("Category_subCatLast"));

		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());

		String errorMessage = manage_Categories_Loc.getMaxLimitError();

		Assert.assertEquals(errorMessage, PropsUtil.getDataPropertyValue("Category_LimitErrorMsg"));
	}

	@Test(description = "L1-41943,L1-41944 :CATEG_01_23:Verify Edit and Delete sub category buttons.", priority = 23, groups = {
			"Desktop" })
	public void verifyEditDeleteButtonSubCat() throws Exception {
		boolean val = manage_Categories_Loc.isDeleteSubCategoryBtnDisplayed();

		Assert.assertTrue(val);

		boolean val2 = manage_Categories_Loc.isEditSubCategoryBtnDisplayed();

		Assert.assertTrue(val2);
	}

	@Test(description = "CATEG_01_24:Verifying add and delete sub category.", priority = 24, groups = { "Desktop" })
	public void verifyAddAndDeleteSubCat() throws Exception {
		manage_Categories_Loc.forceNavigateToCategories();
		SeleniumUtil.click(manage_Categories_Loc.expenseCatLink());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
		boolean subCat = manage_Categories_Loc.subCatLink().isDisplayed();
		Assert.assertTrue(subCat);
		manage_Categories_Loc.clickMLC(1, 1);
		manage_Categories_Loc.subCategoryTxtBox().sendKeys(PropsUtil.getDataPropertyValue("Category_subCat1"));
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		manage_Categories_Loc.subCategoryTxtBox().sendKeys(PropsUtil.getDataPropertyValue("Category_subCat2"));
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		boolean subCat1 = manage_Categories_Loc.subCat1().isDisplayed();
		Assert.assertTrue(subCat1);
		boolean subCat2 = manage_Categories_Loc.subCat2().isDisplayed();
		Assert.assertTrue(subCat2);
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manage_Categories_Loc.subCatLink());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(1000);
		boolean subCat1Text = manage_Categories_Loc.subCat1().isDisplayed();
		Assert.assertTrue(subCat1Text, "Sub category 1 \"subCat1\" is not displayed");
		SeleniumUtil.click(manage_Categories_Loc.deleteSubCategory1());
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(manage_Categories_Loc.subCatLink());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
		By cat1 = SeleniumUtil.getByObject("Categories", null, "subCat1_MC");
		if (SeleniumUtil.isDisplayed(cat1, 2)) {
			fail("Sub category \"subCat1\" is not getting deleted");
		}
		boolean subCat2Text = manage_Categories_Loc.subCat2().isDisplayed();
		Assert.assertTrue(subCat2Text, "Sub category 2 \"subCat2\" is not displayed");
		SeleniumUtil.click(manage_Categories_Loc.deleteSubCategory2());

		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		By cat2 = SeleniumUtil.getByObject("Categories", null, "subCat2_MC");
		if (SeleniumUtil.isDisplayed(cat2, 2)) {
			fail("Sub category \"subCat2\" is not getting deleted");
		}
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("backToCategoriesForMobile", "Categories", null)) {
			SeleniumUtil.click(manage_Categories_Loc.backToCategoriesForMobile());
		} else {
			SeleniumUtil.click(manage_Categories_Loc.firstMasterLevelExpenseCategory().get(0));
		}
		SeleniumUtil.waitForPageToLoad(1000);
	}

	@Test(description = "CATEG_01_25:Editing a Sub cateogry name: It's case sensitive & error message not displayed when same name with diff cases (edit).", priority = 25, groups = {
			"Desktop" })
	public void editSubcategoryName() throws Exception {
		manage_Categories_Loc.clickMLC(1, 1);

		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("==========>Creating subCategory1");
		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("subCategory1"));
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		SeleniumUtil.waitForPageToLoad();

		logger.info("==========>Creating subCategory2");
		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("subCategory1"));
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(manage_Categories_Loc.categoryErrorMsg().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("categoryErrorMsg"));

		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("subCategory2"));
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		SeleniumUtil.click(manage_Categories_Loc.editSubcat().get(1));
		manage_Categories_Loc.typeSubCategoryName(PropsUtil.getDataPropertyValue("subCategory1"));
		SeleniumUtil.click(manage_Categories_Loc.addSubCatButtonInMLC());
		Assert.assertEquals(manage_Categories_Loc.categoryErrorMsg().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("categoryErrorMsg"));
		SeleniumUtil.click(manage_Categories_Loc.cancelButton());
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "CATEG_01_26:", priority = 26, groups = { "Desktop" })
	public void categoryAndSubCategoryNames() throws Exception {
		logger.info("==========>Editing the high level category");
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(manage_Categories_Loc.givingCategory());
		Assert.assertEquals(manage_Categories_Loc.originalNameMLc().getText(),
				manage_Categories_Loc.editHLCatInputField().getAttribute("value"));

		manage_Categories_Loc.editHLCatInputField().clear();
		manage_Categories_Loc.editHLCatInputField()
				.sendKeys(PropsUtil.getDataPropertyValue("EditHigLevelCategoryName"));
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButton());
		SeleniumUtil.waitForPageToLoad();
		By givingCategoryOne = SeleniumUtil.getByObject("Categories", null, "givingCategoryOne");
		if (SeleniumUtil.isDisplayed(givingCategoryOne, 2))
			fail("The giving CategoryOne is displayed");
		Assert.assertTrue(manage_Categories_Loc.givingCategoryEdited().isDisplayed());

		logger.info("==========>Editing the sub category");
		SeleniumUtil.click(manage_Categories_Loc.giftsCategory());
		Assert.assertEquals(manage_Categories_Loc.originalNameMLc().getText(),
				manage_Categories_Loc.editMLCatInputField().getAttribute("value"));
		manage_Categories_Loc.editMLCatInputField().clear();
		manage_Categories_Loc.editMLCatInputField()
				.sendKeys(PropsUtil.getDataPropertyValue("EditMasterLevelCategoryName"));
		SeleniumUtil.click(manage_Categories_Loc.saveChangesButtonInMLC());
		SeleniumUtil.waitForPageToLoad();

		By giftsCategory_MC = SeleniumUtil.getByObject("Categories", null, "giftsCategory_MC");
		if (SeleniumUtil.isDisplayed(giftsCategory_MC, 2))
			fail("The gifts Category_MC is displayed");

		Assert.assertTrue(manage_Categories_Loc.giftsCategoryEdited().isDisplayed());
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
