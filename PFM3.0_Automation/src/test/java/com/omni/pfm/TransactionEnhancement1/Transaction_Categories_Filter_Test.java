/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Categories_Filter_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Date_Filter_Test.class);
	Transaction_Categorization_Rules_Loc rule;
	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_Search_Loc search;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Layout_Loc layout;
	Transaction_Tag_Loc tag;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void testInit() {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		filter = new Transaction_Filter_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		search = new Transaction_Search_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		util = new SeleniumUtil();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68655,AT-68653:verify category filter header", priority = 1)
	public void verifyCategoriesFilterPopUp() {
		accountDropDown.clickCategoryFilter();
		Assert.assertTrue(
				filter.transactionFilterPopUpOpen().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionFilterPopUpOpen")),
				"class staus should change to open when clikc on categories filter");
		Assert.assertEquals(filter.transactionFilterPopUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterHeader"),
				"verify categories filter header should displayed");
		Assert.assertTrue(filter.transactionFilterPopUpClose().isDisplayed());
	}

	@Test(description = "AT-68656:verify all category check box selected", priority = 2, dependsOnMethods = "verifyCategoriesFilterPopUp")
	public void verifyAllCategoriesSelectedByDefault() {
		Assert.assertEquals(filter.transactionCategoryFilterAllCategoriesCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all categories check box should be selected");
		Assert.assertEquals(filter.transactionCategoryFilterAllCategoriesLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterAllCategories"), "all categories label");
	}

	@Test(description = "AT-68656:verify Master level selected", priority = 3, dependsOnMethods = "verifyCategoriesFilterPopUp")
	public void verifyAllMasterLevelCategoriesSelectedByDefault() {
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllMLCCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-68661,AT-68654:all Master level category", priority = 4, dependsOnMethods = "verifyCategoriesFilterPopUp")
	public void verifyAllMasterLevelCategories() {
		util.assertExpectedActualList(util.getWebElementsValue(filter.transactionCategoryFilterAllMLCLabel()),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLC"),
				"verify all master level category should be dispalyed");
	}

	@Test(description = "AT-68656:verify all high level category selected", priority = 5, dependsOnMethods = "verifyCategoriesFilterPopUp")
	public void verifyAllHighLevelCategoriesSelectedByDefault() {
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllHLCCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-68654:verify all high level category", priority = 6, dependsOnMethods = "verifyCategoriesFilterPopUp")
	public void verifyAllHighLevelCategories() {
		util.assertExpectedActualList(util.getWebElementsValue(filter.transactionCategoryFilterAllHLCLabel()),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterHLC"),
				"verify all High level category should be dispalyed");
	}

	@Test(description = "AT-68659:verify all category check box unselected", priority = 7, dependsOnMethods = "verifyCategoriesFilterPopUp")
	public void verifyAllCategoriescheckBoxUnselected() {
		SeleniumUtil.click(filter.transactionCategoryFilterAllCategoriesCheckBox());
		Assert.assertEquals(filter.transactionCategoryFilterAllCategoriesCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterUnSelected"),
				"all categories check box should be unselected");
	}

	@Test(description = "AT-68212:verify no transaction message is displaying", priority = 8, dependsOnMethods = "verifyAllCategoriescheckBoxUnselected")
	public void verifyNoTransactionWhenAllCategoriescheckBoxUnselected() {
		String noTransaction = tag.noTransactionMessage().getText().trim();
		Assert.assertEquals(noTransaction, PropsUtil.getDataPropertyValue("TransactionNodataMessage"),
				"No Transaction is not displaying when unselect all category check box");
	}

	@Test(description = "AT-68659:verify all  master level category unselected", priority = 9, dependsOnMethods = "verifyAllCategoriescheckBoxUnselected")
	public void verifyAllMasterLevelCategoriesUnSelected() {
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllMLCCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterUnSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-68659:verify all high level category un selected", priority = 10, dependsOnMethods = "verifyAllCategoriescheckBoxUnselected")
	public void verifyAllHighLevelCategoriesUnSelected() {
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllHLCCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterUnSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-68659:verify all category check box selected", priority = 11, dependsOnMethods = "verifyAllCategoriescheckBoxUnselected")
	public void verifyAllCategoriescheckBoxselected() {
		SeleniumUtil.click(filter.transactionCategoryFilterAllCategoriesCheckBox());
		Assert.assertEquals(filter.transactionCategoryFilterAllCategoriesCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all categories check box should be unselected");
	}

	@Test(description = "AT-68659:verify all master level selected", priority = 12, dependsOnMethods = "verifyAllCategoriescheckBoxselected")
	public void verifyAllMasterLevelCategoriesSelected() {
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllMLCCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-68659:verify all high level category selected", priority = 13, dependsOnMethods = "verifyAllCategoriescheckBoxselected")
	public void verifyAllHighLevelCategoriesSelected() {
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllHLCCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-69161:verify all selected HLC transactions are displaying", priority = 14, dependsOnMethods = "verifyAllCategoriescheckBoxselected")
	public void VerifyselectOneMLCCategory() throws Exception {
		logger.info("unselect all category check box");
		SeleniumUtil.click(filter.transactionCategoryFilterAllCategoriesCheckBox());
		int MLCSize = filter.transactionCategoryFilterAllMLCCheckBox().size();
		int MLCOrder = 0;
		for (int i = 0; i < MLCSize; i++) {
			if (filter.transactionCategoryFilterAllMLCLabel().get(i).getText().trim()
					.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TransaferCategory1"))) {
				MLCOrder = i + 1;
				SeleniumUtil.click(filter.transactionCategoryFilterAllMLCCheckBox().get(i));
				break;
			}
		}
		util.assertActualList(
				util.getWebElementsAttributeName(filter.transactionCategoryFilterAllMLCHLCCheckBox(MLCOrder),
						"aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"all Category should be selected");
	}

	@Test(description = "AT-68658:verify selected category Transaction", priority = 15, dependsOnMethods = "VerifyselectOneMLCCategory")
	public void VerifyTransactionWhenselectOneMLCCategory() throws Exception {
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		int transactioncat = accountDropDown.allTransactionCategory().size();
		boolean expectedCategory = false;
		for (int i = 0; i < transactioncat; i++) {
			if (accountDropDown.allTransactionCategory().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory2"))
					|| accountDropDown.allTransactionCategory().get(i).getText().trim()
							.equals(PropsUtil.getDataPropertyValue("TransaferCategory4"))) {
				expectedCategory = true;
			} else {
				expectedCategory = false;
				break;
			}
		}
		Assert.assertTrue(expectedCategory, "selected HLC category transactions should displayed");
	}

	@Test(description = "AT-68618:verify reset button is displaying", priority = 16, dependsOnMethods = "VerifyselectOneMLCCategory")
	public void verifyResetButtonDisplaying() {
		Assert.assertEquals(tag.reset().getText().trim(), PropsUtil.getDataPropertyValue("transactionResetText"),
				"after filter reset should displayed");
	}

	@Test(description = "AT-68657:verify multiple category in catgeory tab", priority = 17, dependsOnMethods = "VerifyselectOneMLCCategory")
	public void VerifyMultiSelectLableWhenselectMLCCCategory() throws Exception {
		Assert.assertEquals(accountDropDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAllCategoriesMultipleFilterLink"),
				"all category label is not displaying");
	}

	@Test(description = "AT-68658,AT-68680:verify hlc category transaction ", priority = 18, dependsOnMethods = "VerifyselectOneMLCCategory")
	public void VerifyTransactionWhenselectOneHLCCategory() {
		logger.info("unselect all category check box");
		SeleniumUtil.click(filter.transactionCategoryFilterAllCategoriesCheckBox());
		SeleniumUtil.click(filter.transactionCategoryFilterAllCategoriesCheckBox());
		int MLCSize = filter.transactionCategoryFilterAllHLCCheckBox().size();
		for (int i = 0; i < MLCSize; i++) {
			if (filter.transactionCategoryFilterAllHLCLabel().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransaferCategory3"))) {
				SeleniumUtil.click(filter.transactionCategoryFilterAllHLCCheckBox().get(i));
				break;
			}
		}
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		util.assertActualList(util.getWebElementsValue(accountDropDown.allTransactionCategory()),
				PropsUtil.getDataPropertyValue("TransaferCategory3"),
				"selected category Transaction should be displayed");
	}

	@Test(description = "AT-68658:verify category tab", priority = 19, dependsOnMethods = "VerifyTransactionWhenselectOneHLCCategory")
	public void VerifyCategoryLableWhenselectHLCCCategory() throws Exception {
		Assert.assertEquals(accountDropDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransaferCategory3"), "all category label is not displaying");
	}

	@Test(description = "AT-68620:verify category tab when click reset button", priority = 20, dependsOnMethods = "VerifyTransactionWhenselectOneHLCCategory")
	public void verifyResetFilter() {
		SeleniumUtil.click(tag.reset());
		Assert.assertEquals(accountDropDown.categoryFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterTabText"));
	}

	@Test(description = "AT-68660:verify subcategory is displaying", priority = 21)
	public void VerifySubCategroesDispalying() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		rule.manageCat();
		rule.editCat(PropsUtil.getDataPropertyValue("InvestorRuleCatChange"),
				PropsUtil.getDataPropertyValue("InvestorRuleSubCatChange"),
				PropsUtil.getDataPropertyValue("Transaction_CategoryFilter_HLCCategoryName1"),
				PropsUtil.getDataPropertyValue("Transaction_CategoryFilter_MLCCategoryName1"));
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.clickCategoryFilter();
		Assert.assertEquals(
				filter.transactionCategoryFilterSubCategoryCheckBox(6, 2).getAttribute("aria-checked").trim(),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSelected"),
				"verofy subCategory check box is selected");
		Assert.assertEquals(filter.transactionCategoryFilterSubCategoryLabel(6, 2).getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategory"),
				"verofy subCategory check box is selected");
	}

	@Test(description = "AT-68663:verify search category", priority = 22, dependsOnMethods = "VerifySubCategroesDispalying")
	public void VerifySearchCategory() throws Exception {
		filter.transactionCategoryFilterCategorySearcheField()
				.sendKeys(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		Assert.assertFalse(filter.transactionCategoryFilterCategorySearched(5).getAttribute("class")
				.equals(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide")));
	}

	@Test(description = "AT-68663:verify no catgeory available", priority = 23, dependsOnMethods = "VerifySubCategroesDispalying")
	public void VerifySearchCategoryNoCategoriesAvailable() throws Exception {
		filter.transactionCategoryFilterCategorySearcheField()
				.sendKeys(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch1"));
		Assert.assertEquals(filter.transactionCategoryFilterCategorySearcheNoCategories().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterNoCategoryAvailable"),
				"verify No Categories available ");
	}

	@Test(description = "AT-68665,AT-68664:verify close utton in seach text field ", priority = 24, dependsOnMethods = "VerifySearchCategoryNoCategoriesAvailable")
	public void VerifySearchCategoryClose() throws Exception {
		SeleniumUtil.click(filter.transactionCategoryFilterCategorySearcheClose());
		logger.info(filter.transactionCategoryFilterCategorySearcheField().getText());
	}
}
