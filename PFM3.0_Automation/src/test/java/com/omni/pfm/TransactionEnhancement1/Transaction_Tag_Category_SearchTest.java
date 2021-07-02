/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.TransactionEnhancement1;

import static org.testng.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Budget_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Expanse_Income_Analysis_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Tag_Category_SearchTest extends TestBase {
	Transaction_Categorization_Rules_Loc rule;
	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
	Transaction_AccountDropDown_Loc accountDropDown;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Tag_Loc tag;
	Aggregated_Transaction_Details_Loc aggTran;
	WebDriverWait wait;
	Series_Recurence_Transaction_Loc seriesTxn;
	Transaction_Expanse_Income_Analysis_Loc expanse;
	Transaction_Account_Integration_Loc acctIntg;
	Transaction_Budget_Integration_Loc budget;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Add Manual Transaction");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		filter = new Transaction_Filter_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		aggTran = new Aggregated_Transaction_Details_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		seriesTxn = new Series_Recurence_Transaction_Loc(d);
		wait = new WebDriverWait(d, 60);
		acctIntg = new Transaction_Account_Integration_Loc(d);
		budget = new Transaction_Budget_Integration_Loc(d);
		expanse = new Transaction_Expanse_Income_Analysis_Loc(d);
		util = new SeleniumUtil();
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("TransactionFilter1.site16441.4", "site16441.4");
		if (appFlag.equalsIgnoreCase("emulator") || appFlag.equalsIgnoreCase("app")) {
			d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		// search the string which is matching with HLC in Category filter
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify category search filter with Start letter ", priority = 1)
	public void verifySearchHLCWithStartLetter() throws Exception {
		if (accountDropDown.isFilterMobile()) {
			accountDropDown.clickFilter().click();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			accountDropDown.clickCategoryFilter();
		} else {
			accountDropDown.clickCategoryFilter();
		}
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC1("Miscellaneous Expenses"));
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC1"),
				"search maches  master level catgeories Should Be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search maches  high level catgeories Should Be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"searched category test should be highlighted");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"searched category test should be highlighted");
	}

	@Test(description = "AT-85035:verify category search mlc with start letter", priority = 2, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchMLCWithStartLetter() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		List<String> hilightedMLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search maches  master level catgeories Should Be displayed");
		util.assertActualList(hilightedMLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"), "");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"");
	}

	@Test(description = "AT-85034:verify  categroy search with HLC ", priority = 3, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchHLCWithsecondWord() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:verify category search with MLC", priority = 4, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchMLCWithsecondWord() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify category search with MLC", priority = 5, dependsOnMethods = {
			"verifySearchMLCWithsecondWord" })
	public void verifySearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(filter.tranCatFltSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedMLCHL(),
				"class");
		int seachedMLCSize = filter.tranCatFltSearchedMLCHL().size();
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue"), "");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"");
		Assert.assertEquals(seachedMLCSize, 3, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:verify catgeory search with HLC sub string", priority = 6, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchHLCWithSubString() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		Assert.assertEquals(SeleniumUtil.getElementCount(filter.tranCatFltSearchedHLC), 0,
				"HLC Categories should not displayed which have searched sub String");
		Assert.assertEquals(filter.tranCatFltNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify category search with MLC sub string ", priority = 7, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchMLCWithSubString() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		Assert.assertEquals(SeleniumUtil.getElementCount(filter.tranCatFltSearchedMLC), 0,
				"searched HLC should dsipalyed ");
		Assert.assertEquals(SeleniumUtil.getElementCount(filter.tranCatFltSearchedHLC), 0,
				"searched HLC should dsipalyed ");
		Assert.assertEquals(filter.tranCatFltNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:verify HCL search with Special char ", priority = 8, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchHLCWithSpecChar() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify MLC search with SpecChar", priority = 9, dependsOnMethods = {
			"verifySearchHLCWithSpecChar" })
	public void verifyMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with space", priority = 10, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchHLCWithSpace() throws Exception {
		filter.searchCategory(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(filter.tranCatFltSearchedHLC().size(), 1, "HLC category size is not matching");
	}

	@Test(description = "AT-85041:verify MLC with space", priority = 11, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchMLCWithSpace() throws Exception {
		filter.searchCategory(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(filter.tranCatFltSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"), "");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"");
	}

	@Test(description = "AT-85039:verify MLC with backSlash", priority = 12, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchMLCWithBackSlash() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(filter.tranCatFltSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(filter.tranCatFltSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(filter.tranCatFltSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"), "");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"");
	}

	@Test(description = "AT-85039:verify MLC Backslash", priority = 13, dependsOnMethods = {
			"verifySearchMLCWithBackSlash" })
	public void verifySearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(filter.tranCatFltSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:verify search with first letter", priority = 14, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchWithFirstLater() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(filter.tranCatFltSearchedHLCHL().size());
		Assert.assertEquals(filter.tranCatFltSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
		System.out.println(filter.tranCatFltSearchedMLCHL().size());
		Assert.assertEquals(filter.tranCatFltSearchedMLCHL().size(), 12,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:verify search with AllCategories", priority = 15, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchWithAllCategories() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(filter.tranCatFltNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:verify search with none", priority = 16, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void verifySearchWithNone() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(filter.tranCatFltNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	// TransactionDetails
	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify search with Start letter", priority = 17, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void txnDtlSearchHLCWithStartLetter() throws Exception {
		if (accountDropDown.isTagbackmobile()) {
			SeleniumUtil.click(accountDropDown.Tagbackmobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			accountDropDown.backtoTxnDetailsMobile();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		aggTran.postedTxnClick(0);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		aggTran.catgoryFieldClick();
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85035:verify search mlc with start leter", priority = 18, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchMLCWithStartLetter() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034:verify hlc with seacond letter", priority = 19, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchHLCWithsecondWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:verify HLC with Start letter", priority = 20, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchMLCWithsecondWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify MLC with second later", priority = 21, dependsOnMethods = {
			"txnDtlSearchMLCWithsecondWord" })
	public void txnDtlSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		int seachedMLCSize = aggTran.tranDtlCatSearchedMLCHL().size();
		util.assertActualList(
				hilightedMLC.stream().map(String::toLowerCase).collect(Collectors.toList()), PropsUtil
						.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue").toLowerCase(),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(seachedMLCSize, 4, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85041,AT-85036,AT-85033:verify HLC with sub string", priority = 22, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchHLCWithSubString() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("High level categories are getting displayed");
		}
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify MLC with subString", priority = 23, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchMLCWithSubString() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLC")) == 0)) {
			fail("Medium level categories are getting displayed");
		}
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("High level categories are getting displayed");
		}
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with spec char", priority = 24, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchHLCWithSpecChar() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify HLC with Spac char", priority = 25, dependsOnMethods = {
			"txnDtlSearchHLCWithSpecChar" })
	public void txnDtlMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:verify HCl with space", priority = 26, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchHLCWithSpace() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify MLC with space ", priority = 27, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchMLCWithSpace() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with backSlash", priority = 28, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchMLCWithBackSlash() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with BackSlash", priority = 29, dependsOnMethods = {
			"txnDtlSearchMLCWithBackSlash" })
	public void txnDtlSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:verify HCL with first letter", priority = 30, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchWithFirstLater() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		Assert.assertEquals(aggTran.tranDtlCatSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
		Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 13,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:verify search with AllCategories", priority = 31, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchWithAllCategories() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:verify search with none", priority = 32, dependsOnMethods = {
			"txnDtlSearchHLCWithStartLetter" })
	public void txnDtlSearchWithNone() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	// ManulTran
	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify Search with HLC", priority = 33, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void manualTxnSearchHLCWithStartLetter() throws Exception {
		// search the string which is matching with HLC in Category filter
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		if (add_Manual.isMoreBtnPresent()) {
			add_Manual.clickAddManualTransaction();
		} else {
			add_Manual.clickMTLink();
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		add_Manual.clickMTCategory();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85035:Verify MLC with Start Letter", priority = 34, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchMLCWithStartLetter() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034:verify HLC with second word", priority = 35, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchHLCWithsecondWord() throws Exception {
		add_Manual
				.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:verify Search with HLC", priority = 36, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchMLCWithsecondWord() throws Exception {
		add_Manual.manualTxnCategorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify search MLC with second word", priority = 37, dependsOnMethods = {
			"manualTxnSearchMLCWithsecondWord" })
	public void manualtxnSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedMLCHL(),
				"class");
		int seachedMLCSize = add_Manual.tranMTCatSearchedMLCHL().size();
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(seachedMLCSize, 3, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:verify HLC with sub String", priority = 38, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchHLCWithSubString() throws Exception {
		add_Manual.manualTxnCategorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(add_Manual.tranMTCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify MLC with Sub String", priority = 39, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchMLCWithSubString() throws Exception {
		add_Manual
				.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLC")) == 0)) {
			fail("searched MLC should dsipalyed");
		}
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedHLC")) == 0)) {
			fail("searched HLC should dsipalyed ");
		}
		Assert.assertEquals(add_Manual.tranMTCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with Spec char", priority = 40, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnlSearchHLCWithSpecChar() throws Exception {
		add_Manual.manualTxnCategorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify mLC with Spec char", priority = 41, dependsOnMethods = {
			"manualTxnlSearchHLCWithSpecChar" })
	public void manualTxnMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with space", priority = 42, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchHLCWithSpace() throws Exception {
		add_Manual.manualTxnCategorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify MLC with space", priority = 43, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchMLCWithSpace() throws Exception {
		add_Manual.manualTxnCategorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with backSlash", priority = 44, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchMLCWithBackSlash() throws Exception {
		add_Manual.manualTxnCategorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(add_Manual.tranMTCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with BackSlash", priority = 45, dependsOnMethods = {
			"manualTxnSearchMLCWithBackSlash" })
	public void manualTxnSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:verify Search with first letter", priority = 46, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchWithFirstLater() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(add_Manual.tranMTCatSearchedHLCHL().size());
		Assert.assertEquals(add_Manual.tranMTCatSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter"); // size is 3
		Assert.assertEquals(add_Manual.tranMTCatSearchedMLCHL().size(), 12,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:verify search with AllCategories", priority = 47, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchWithAllCategories() throws Exception {
		add_Manual
				.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(add_Manual.tranMTCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:verify search with none", priority = 48, dependsOnMethods = {
			"manualTxnSearchHLCWithStartLetter" })
	public void manualTxnSearchWithNone() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(add_Manual.tranMTCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify search With Start letter", priority = 49, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void EADtlSearchHLCWithStartLetter() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanse.clickThisMonthTransaction(); // new user only..
		expanse.clickEATxn1(0, 0);
		expanse.clickOnHLCInEAOfGivenIndex(0);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(expanse.totaltranamount().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(expanse.totaltranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(expanse.tranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		aggTran.catgoryFieldClick();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85035:verify search with MLC start letter", priority = 50, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchMLCWithStartLetter() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034:verify search with HLC", priority = 51, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchHLCWithsecondWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:verify search with second letter", priority = 52, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchMLCWithsecondWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify search with MLC second word", priority = 53, dependsOnMethods = {
			"EADtlSearchMLCWithsecondWord" })
	public void EADtlSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		int seachedMLCSize = aggTran.tranDtlCatSearchedMLCHL().size();
		util.assertActualList(
				hilightedMLC.stream().map(String::toLowerCase).collect(Collectors.toList()), PropsUtil
						.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue").toLowerCase(),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(seachedMLCSize, 4, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:verify search HLC with sub String", priority = 54, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchHLCWithSubString() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify Search MLC with SubString", priority = 55, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchMLCWithSubString() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLC")) == 0)) {
			fail("searched MLC should dsipalyed ");
		}
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("searched HLC should dsipalyed ");
		}
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:search HLC with Spec char", priority = 56, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchHLCWithSpecChar() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:search HLC with Spac char", priority = 57, dependsOnMethods = {
			"EADtlSearchHLCWithSpecChar" })
	public void EADtlMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:search HLC with Space", priority = 58, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchHLCWithSpace() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:search MLC with Letter", priority = 59, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchMLCWithSpace() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:search MLC with BackSlash", priority = 60, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchMLCWithBackSlash() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:Search MLC backSlash", priority = 61, dependsOnMethods = {
			"EADtlSearchMLCWithBackSlash" })
	public void EADtlSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:Search with first letter", priority = 62, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchWithFirstLater() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(aggTran.tranDtlCatSearchedHLCHL().size());
		Assert.assertEquals(aggTran.tranDtlCatSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
		Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 13,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:search With AllCategories", priority = 63, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchWithAllCategories() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:Search With none", priority = 64, dependsOnMethods = {
			"EADtlSearchHLCWithStartLetter" })
	public void EADtlSearchWithNone() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify search HLC with start letter", priority = 65, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void IADtlSearchHLCWithStartLetter() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(3);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			expanse.clickIATxn1(0, 0);
			SeleniumUtil.click(expanse.totaltranamount().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(expanse.totaltranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			expanse.clickIATxn(0, 0, 0);
		}
		aggTran.catgoryFieldClick();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85035:search MLC with Start letter", priority = 66, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchMLCWithStartLetter() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034:search HLC with second word", priority = 67, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchHLCWithsecondWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:search MLC with second word", priority = 68, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchMLCWithsecondWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify MLC with second word", priority = 69, dependsOnMethods = {
			"IADtlSearchMLCWithsecondWord" })
	public void IADtlSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		int seachedMLCSize = aggTran.tranDtlCatSearchedMLCHL().size();
		util.assertActualList(
				hilightedMLC.stream().map(String::toLowerCase).collect(Collectors.toList()), PropsUtil
						.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue").toLowerCase(),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(seachedMLCSize, 4, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:Verify HLC with sub string", priority = 70, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchHLCWithSubString() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify MLC with Sub string", priority = 71, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchMLCWithSubString() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLC")) == 0)) {
			fail("MLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with Spec char", priority = 72, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchHLCWithSpecChar() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify HLC search with Spec char", priority = 73, dependsOnMethods = {
			"IADtlSearchHLCWithSpecChar" })
	public void IADtlMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with Space", priority = 74, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchHLCWithSpace() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify MLC with Space", priority = 75, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchMLCWithSpace() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with backSlash", priority = 76, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchMLCWithBackSlash() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with BlackSlash", priority = 77, dependsOnMethods = {
			"IADtlSearchMLCWithBackSlash" })
	public void IADtlSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:verify search with first letter", priority = 78, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchWithFirstLater() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(aggTran.tranDtlCatSearchedHLCHL().size());
		Assert.assertEquals(aggTran.tranDtlCatSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
		Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 13,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:verify search with AllCategories", priority = 79, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchWithAllCategories() throws Exception {
		aggTran.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:search with none", priority = 80, dependsOnMethods = {
			"IADtlSearchHLCWithStartLetter" })
	public void IADtlSearchWithNone() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(aggTran.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify search with start letter", priority = 81, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void accSettingSearchHLCWithStartLetter() throws Exception {
		// search the string which is matching with HLC in Category filter
		// filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		// Assert.assertEquals(filter.tranCatFltSearchedHLC.get(0).getText().trim(),PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),"searched
		// MLC should dsipalyed");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);//TODO
		acctIntg.accSettCatSearchField(0, 0);
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85035:verify seach MLC with start letter", priority = 82, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchMLCWithStartLetter() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034:verify search with Second word", priority = 83, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchHLCWithsecondWord() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:verify search with Second word", priority = 84, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchMLCWithsecondWord() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify search MLC with second word", priority = 85, dependsOnMethods = {
			"accSettingSearchMLCWithsecondWord" })
	public void accSettingSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedMLCHL(),
				"class");
		int seachedMLCSize = acctIntg.AccSettCatSearchedMLCHL().size();
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(seachedMLCSize, 3, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:search HLC with subString", priority = 86, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchHLCWithSubString() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(acctIntg.AccSettCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify search MLC with SubString", priority = 87, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchMLCWithSubString() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedMLC")) == 0)) {
			fail("MLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(acctIntg.AccSettCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:verify search with spec char", priority = 88, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchHLCWithSpecChar() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify search with Space char", priority = 89, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:verify search with space", priority = 90, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingDtlSearchHLCWithSpace() throws Exception {
		acctIntg.categorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedHLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:verify search with space", priority = 91, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingDtlSearchMLCWithSpace() throws Exception {
		acctIntg.categorySearch(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify search with backslash", priority = 92, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchMLCWithBackSlash() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(acctIntg.AccSettCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedMLCHL(),
				"class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:verify search with backslash", priority = 93, dependsOnMethods = {
			"accSettingSearchMLCWithBackSlash" })
	public void accSettingSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:verify search with first letter", priority = 94, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchWithFirstLater() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(acctIntg.AccSettCatSearchedHLCHL().size());
		Assert.assertEquals(acctIntg.AccSettCatSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter"); // size was 2
		Assert.assertEquals(acctIntg.AccSettCatSearchedMLCHL().size(), 12,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:verify search with AllCategories", priority = 95, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchWithAllCategories() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(acctIntg.AccSettCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:verify search with none", priority = 96, dependsOnMethods = {
			"accSettingSearchHLCWithStartLetter" })
	public void accSettingSearchWithNone() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(acctIntg.AccSettCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify HLC with start letter", priority = 113, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void CRSearchHLCWithStartLetter() throws Exception {
		// search the string which is matching with HLC in Category filter
		// filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		// Assert.assertEquals(filter.tranCatFltSearchedHLC.get(0).getText().trim(),PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),"searched
		// MLC should dsipalyed");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("CategorzationRules", d);
		rule.creatRuleClick();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		rule.CRcatFieldClick();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(rule.CRCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedHLCHL(), "class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85035:verify MLC with start leter ", priority = 114, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchMLCWithStartLetter() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(rule.CRCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedMLCHL(), "class");
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034:search HLC with second word", priority = 115, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchHLCWithsecondWord() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(rule.CRCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedHLCHL(), "class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:search MLc with second letter", priority = 116, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchMLCWithsecondWord() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedHLCHL(), "class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:search Mlc with second letter", priority = 117, dependsOnMethods = {
			"CRSearchMLCWithsecondWord" })
	public void CRSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = util.getWebElementsValue(rule.CRCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedMLCHL(), "class");
		int seachedMLCSize = rule.CRCatSearchedMLCHL().size();
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue"),
				"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
		Assert.assertEquals(seachedMLCSize, 3, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:search Hlc with sub String", priority = 118, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchHLCWithSubString() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(rule.CRCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:Search MLC with Sub string", priority = 119, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchMLCWithSubString() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedMLC")) == 0)) {
			fail("MLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(rule.CRCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:Search HLC with Spec char", priority = 120, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchHLCWithSpecChar() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(rule.CRCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedHLCHL(), "class");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:Search HLC with Pec char", priority = 121, dependsOnMethods = {
			"CRSearchHLCWithSpecChar" })
	public void CRMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		util.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:search HLC with space", priority = 122, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRDtlSearchHLCWithSpace() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		List<String> hilightedHLCName = util.getWebElementsValue(rule.CRCatSearchedHLCHL());
		List<String> hilightedHLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedHLCHL(), "class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		util.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		util.assertActualList(hilightedHLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85041:search MLC with space", priority = 123, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRDtlSearchMLCWithSpace() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		List<String> hilightedMLC = util.getWebElementsValue(rule.CRCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedMLCHL(), "class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:Search MLC with Backslash ", priority = 124, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchMLCWithBackSlash() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = util.getWebElementsValue(rule.CRCatSearchedMLC());
		List<String> hilightedMLC = util.getWebElementsValue(rule.CRCatSearchedMLCHL());
		List<String> hilightedMLCClassName = util.getWebElementsAttributeName(rule.CRCatSearchedMLCHL(), "class");
		util.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		util.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		util.assertActualList(hilightedMLCClassName, PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"class should highlighted");
	}

	@Test(description = "AT-85039:Search MLC with BackSlash", priority = 125, dependsOnMethods = {
			"CRSearchMLCWithBackSlash" })
	public void CRSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = util.getWebElementsValue(rule.CRCatSearchedHLC());
		util.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040: search with first letter", priority = 126, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchWithFirstLater() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(rule.CRCatSearchedHLCHL().size());
		Assert.assertEquals(rule.CRCatSearchedHLCHL().size(), 1, "Hilighted element class should contain highlighter"); // size
																														// was
																														// 2
		Assert.assertEquals(rule.CRCatSearchedMLCHL().size(), 12, "Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:search with AllCategories", priority = 127, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchWithAllCategories() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(rule.CRCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:verify search with none", priority = 128, dependsOnMethods = {
			"CRSearchHLCWithStartLetter" })
	public void CRSearchWithNone() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		String noCataval = rule.CRCatNocatAvailabel().getText().trim();
		SeleniumUtil.click(rule.createRuleCrossicon());
		Assert.assertEquals(noCataval, PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

//start from here later
	@Test(description = "AT-85042:verify search with number", priority = 129)
	public void catFilteSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d); // try this again
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		rule.manageCat();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		rule.editCat(PropsUtil.getDataPropertyValue("TranCunsultancyCatgoryEdit"),
				PropsUtil.getDataPropertyValue("TranCunsultancyCatgorySubCat"), 1, 0);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String MLCCategory[] = PropsUtil.getDataPropertyValue("TranDepositCatgoryEdit").split(",");
		String subCAtegory[] = PropsUtil.getDataPropertyValue("TranDepostCatgorySubCat").split(",");
		rule.editCategory(MLCCategory, subCAtegory);
		rule.editsubCat();
		PageParser.forceNavigate("Transaction", d);
		if (accountDropDown.isFilterMobile()) {
			SeleniumUtil.click(accountDropDown.clickFilter());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		accountDropDown.clickCategoryFilter();
		filter.searchCategory(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:Search with MLC ,sub category", priority = 130, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void catFilteSearchMLCWithSubCat() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedSubCatHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		Assert.assertTrue(filter.tranCatFltSearchedMLCHL().size() == 1,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search with %", priority = 131, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void catFilteSearchwithApm() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(filter.tranCatFltSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:search _", priority = 132, dependsOnMethods = { "catFilteSearchSubWithStartNum" })
	public void catFilteSearchwithUnderScrore() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(filter.tranCatFltSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:Search withh dot(.)", priority = 133, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void catFilteSearchwithDot() throws Exception {
		filter.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(filter.tranCatFltSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:search sub catgeory spec char", priority = 134, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" }, enabled = false)
	public void catFilteSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			filter.searchCategory(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCatHL()),
					specialCharList[i], "verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(filter.tranCatFltSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:verify search with 3rd word", priority = 135, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void catFilteSearchwith3rdWord() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (accountDropDown.isFilterMobile()) {
			SeleniumUtil.click(accountDropDown.clickFilter());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		accountDropDown.clickCategoryFilter();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		filter.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(filter.tranCatFltSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(filter.tranCatFltSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(filter.tranCatFltSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(filter.tranCatFltSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verify search clear", priority = 136, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void catFilteSearchClear() throws Exception {
		SeleniumUtil.click(filter.tranCatFltSearchTextClose());
		Assert.assertEquals(filter.transactionCategoryFilterCategorySearcheField().getAttribute("value").length(), 0);
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedSubCatHL")) == 0)) {
			fail("Sub category filter HLC sizze is not zero");
		}
		Assert.assertEquals(filter.tranCatFltSearchedMLC().size(), 59);
	}

	@Test(description = "AT-85042:verify search sub catgeory with num", priority = 137, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void txnDtlsSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		aggTran.postedTxnClick(0);
		aggTran.catgoryFieldClick();
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify sub catgeory search ", priority = 138, dependsOnMethods = {
			"txnDtlsSearchSubWithStartNum" })
	public void txnDtlsCatSearchMLCWithSubCat() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranCatFltSearchedSubCatHL")) == 0)) {
			fail("Sub category filter HLC size is not zero");
		}
		Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search %", priority = 139, dependsOnMethods = {
			"txnDtlsSearchSubWithStartNum" })
	public void txnDtlsCatSearchwithApm() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighted. Expected :: " + 0 + " and Actual :: "
					+ (SeleniumUtil.getElementCount(
							SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL"))));
		}
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:search with _", priority = 140, dependsOnMethods = { "txnDtlsSearchSubWithStartNum" })
	public void txnDtlsCatSearchwithUnderScrore() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:Search with dot(.)", priority = 141, dependsOnMethods = {
			"txnDtlsSearchSubWithStartNum" })
	public void txnDtlsCatSearchwithDot() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:search with all spec char", priority = 142, dependsOnMethods = {
			"txnDtlsSearchSubWithStartNum" }, enabled = false)
	public void txnDtlsCatSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			aggTran.tansactionDetailsCatSerach(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
					specialCharList[i], "verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:search with 3rd letter", priority = 143, dependsOnMethods = {
			"txnDtlsSearchSubWithStartNum" })
	public void txnDtlsCatSearchwith3rdWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verify category search clear", priority = 144, dependsOnMethods = {
			"txnDtlsSearchSubWithStartNum" })
	public void txnDtlsCatSearchClear() throws Exception {
		SeleniumUtil.click(aggTran.tranDtlsCatSearchTextClose());
		int length = aggTran.tranDtlsCatSearchField().getAttribute("value").length();
		By catSearchedSubcatHL = SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedSubCatHL");
		int size = SeleniumUtil.getElementCount(catSearchedSubcatHL);
		int size2 = aggTran.tranDtlCatSearchedMLC().size();
		if (aggTran.iscategoryCloseMobile()) {
			SeleniumUtil.click(aggTran.categoryCloseMobile());
			SeleniumUtil.click(aggTran.mobileTransactionDetailsBack());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		Assert.assertEquals(length, 0);
		Assert.assertEquals(size, 0);
		Assert.assertEquals(size2, 59);
	}

	@Test(description = "AT-85042:verify search sub category", priority = 145, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void MTSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (add_Manual.isMoreBtnPresent()) {
			add_Manual.clickAddManualTransaction();
		} else {
			add_Manual.clickMTLink();
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		add_Manual.clickMTCategory();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify search MLC and Sub catgeory ", priority = 146, dependsOnMethods = {
			"MTSearchSubWithStartNum" })
	public void MTCatSearchMLCWithSubCat() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedSubCatHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		Assert.assertEquals(add_Manual.tranMTCatSearchedMLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search with %", priority = 147, dependsOnMethods = {
			"MTSearchSubWithStartNum" })
	public void MTCatSearchwithApm() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(add_Manual.tranMTCatSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:search with _", priority = 148, dependsOnMethods = { "MTSearchSubWithStartNum" })
	public void MTsCatSearchwithUnderScrore() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(add_Manual.tranMTCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:serach with dot(.)", priority = 149, dependsOnMethods = { "MTSearchSubWithStartNum" })
	public void MTCatSearchwithDot() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(add_Manual.tranMTCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:search with all special char", priority = 150, dependsOnMethods = {
			"MTSearchSubWithStartNum" }, enabled = false)
	public void MTCatSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			add_Manual.manualTxnCategorySearch(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCatHL()),
					specialCharList[i], "verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(add_Manual.tranMTCatSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:verify search with 3rd letter", priority = 151, dependsOnMethods = {
			"MTSearchSubWithStartNum" })
	public void MTCatSearchwith3rdWord() throws Exception {
		add_Manual.manualTxnCategorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(add_Manual.tranMTCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(add_Manual.tranMTCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(add_Manual.tranMTCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verify clear category search", priority = 152, dependsOnMethods = {
			"MTSearchSubWithStartNum" })
	public void MTCatCatSearchClear() throws Exception {
		SeleniumUtil.click(add_Manual.tranMTCatSearchTextCLose());
		Assert.assertEquals(add_Manual.tranMTCatSearchField().getAttribute("value").length(), 0);
		Assert.assertEquals(
				SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTCatSearchedMLCHL")),
				0);
		Assert.assertEquals(add_Manual.tranMTCatSearchedMLC().size(), 59);
	}

	@Test(description = "AT-85042:verify search with num", priority = 153, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void EATxnCatSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanse.clickEATxn1(0, 0);
		expanse.clickOnHLCInEAOfGivenIndex(0);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(expanse.totaltranamount().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(expanse.totaltranamount1().get(0));
		} else {
			SeleniumUtil.click(expanse.tranamount1().get(0));
		}
		aggTran.catgoryFieldClick();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify search with sub category", priority = 154, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" })
	public void EATxnCatSearchMLCWithSubCat() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedSubCatHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search with %", priority = 155, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" })
	public void EATxnCatSearchwithApm() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:search with _", priority = 156, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" })
	public void EATxnCatSearchwithUnderScrore() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:search with Dot(.)", priority = 157, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" })
	public void EATxnCatSearchwithDot() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:search sll special char", priority = 158, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" }, enabled = false)
	public void EATxnCatSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			aggTran.tansactionDetailsCatSerach(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
					specialCharList[i], "verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:search with 3rd word", priority = 159, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" })
	public void EATxnCatSearchwith3rdWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verift category search clear", priority = 160, dependsOnMethods = {
			"EATxnCatSearchSubWithStartNum" })
	public void EATxnCatSearchClear() throws Exception {
		SeleniumUtil.click(aggTran.tranDtlsCatSearchTextClose());
		int length = aggTran.tranDtlsCatSearchField().getAttribute("value").length();
		int size = SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedSubCatHL"));
		int size2 = aggTran.tranDtlCatSearchedMLC().size();
		if (aggTran.iscategoryCloseMobile()) {
			SeleniumUtil.click(aggTran.categoryCloseMobile());
			SeleniumUtil.click(aggTran.mobileTransactionDetailsBack());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		Assert.assertEquals(length, 0);
		Assert.assertEquals(size, 0);
		Assert.assertEquals(size2, 59);
	}

	@Test(description = "AT-85042:verify sub catgeory search ", priority = 161, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void IATxnCatSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanse.clickIATxn(0, 0);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(expanse.totaltranamount().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(expanse.totaltranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(expanse.tranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		aggTran.catgoryFieldClick();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify MLC and sub category search ", priority = 162, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" })
	public void IATxnCatSearchMLCWithSubCat() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedSubCatHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search %", priority = 163, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" })
	public void IATxnCatSearchwithApm() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		System.out.println("tranDtlCatSearchedMLCHL size is " + SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify search with _", priority = 164, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" })
	public void IATxnCatSearchwithUnderScrore() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:verify search with Dot(.)", priority = 165, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" })
	public void IATxnCatSearchwithDot() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:verify seacrh with All special char", priority = 166, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" }, enabled = false)
	public void IATxnCatSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			aggTran.tansactionDetailsCatSerach(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
					specialCharList[i], "verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:verify search with 3rd word", priority = 167, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" })
	public void IATxnCatSearchwith3rdWord() throws Exception {
		aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verify category search clear", priority = 168, dependsOnMethods = {
			"IATxnCatSearchSubWithStartNum" })
	public void IATxnCatSearchClear() throws Exception {
		SeleniumUtil.click(aggTran.tranDtlsCatSearchTextClose());
		int length = aggTran.tranDtlsCatSearchField().getAttribute("value").length();
		int size = SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedSubCatHL"));
		int size2 = aggTran.tranDtlCatSearchedMLC().size();
		if (aggTran.iscategoryCloseMobile()) {
			SeleniumUtil.click(aggTran.categoryCloseMobile());
			SeleniumUtil.click(aggTran.mobileTransactionDetailsBack());
			SeleniumUtil.click(aggTran.crossIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		Assert.assertEquals(length, 0);
		Assert.assertEquals(size, 0);
		Assert.assertEquals(size2, 59);
	}

	@Test(description = "AT-85042:verify search with sub categroy", priority = 169, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void acctSettCatSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		acctIntg.accSettCatSearchField(0, 0);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify serach with MLC and Sub category", priority = 170, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" })
	public void acctSettCatSearchMLCWithSubCat() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedSubCatHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		Assert.assertEquals(acctIntg.AccSettCatSearchedMLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search with %", priority = 171, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" })
	public void acctSettCatSearchwithApm() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(acctIntg.AccSettCatSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify search with _", priority = 172, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" })
	public void acctSettCatSearchwithUnderScrore() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(acctIntg.AccSettCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:Verify search with Dot(.)", priority = 173, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" })
	public void acctSettCatSearchwithDot() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(acctIntg.AccSettCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:verify search with all spacial char", priority = 174, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" }, enabled = false)
	public void acctSettCatSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			acctIntg.categorySearch(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCatHL()),
					specialCharList[i], "verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(acctIntg.AccSettCatSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:serach with 3rd word", priority = 175, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" })
	public void acctSettCatSearchwith3rdWord() throws Exception {
		acctIntg.categorySearch(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(acctIntg.AccSettCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(acctIntg.AccSettCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(acctIntg.AccSettCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verify category search clear", priority = 176, dependsOnMethods = {
			"acctSettCatSearchSubWithStartNum" })
	public void acctSettCatSearchClear() throws Exception {
		SeleniumUtil.click(acctIntg.AccSettCatSearcheTextCLose());
		int length = acctIntg.AccSettCatSearchField().getAttribute("value").length();
		int size = SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "AccSettCatSearchedSubCatHL"));
		int size2 = acctIntg.AccSettCatSearchedMLC().size();
		if (aggTran.iscategoryCloseMobile()) {
			SeleniumUtil.click(aggTran.categoryCloseMobile());
			SeleniumUtil.click(aggTran.crossIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		Assert.assertEquals(length, 0);
		Assert.assertEquals(size, 0);
		Assert.assertEquals(size2, 59);
	}

	/*Old Budget use cases so commented it
	 * 
	 * @Test(description = "AT-85042Lverify search with sub catgeory", priority =
	 * 177, dependsOnMethods = { "acctSettCatSearchwith3rdWord" }) public void
	 * budgtTxnCatSearchSubWithStartNum() throws Exception {
	 * SeleniumUtil.refresh(d); PageParser.forceNavigate("Budget", d);
	 * budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue(
	 * "Budget_Txn_CatSerach_BudgetCategory"), 0); aggTran.catgoryFieldClick();
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue(
	 * "TranSearchSubCatWithNum"));
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()),
	 * PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"),
	 * "verify searched MLC");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedMLCHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * util.assertActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLCHL()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"),
	 * "verify the class name");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedSubCatHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * util.assertActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCatHL()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"),
	 * "verify the class name");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"),
	 * "verify searched MLC"); }
	 * 
	 * @Test(description = "AT-85042:verify search with MLC and sub category",
	 * priority = 178, dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" })
	 * public void budgtTxnCatSearchMLCWithSubCat() throws Exception {
	 * aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue(
	 * "TranSearchSubCatWithSpecChar"));
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()),
	 * PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"),
	 * "verify searched MLC");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedMLCHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLCHL()),
	 * PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"),
	 * "verify searched MLC"); if (!(SeleniumUtil
	 * .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedSubCatHL")) == 0)) {
	 * fail("Hilighted element class should contain highlighter"); }
	 * Assert.assertEquals(aggTran.tranDtlCatSearchedMLCHL().size(), 1,
	 * "Hilighted element class should contain highlighter"); }
	 * 
	 * @Test(description = "AT-85042:verify serach with %", priority = 179,
	 * dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" }) public void
	 * budgtTxnCatSearchwithApm() throws Exception {
	 * aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue(
	 * "TranSearchCatwithAmp"));
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"),
	 * "verify searched MLC");
	 * Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 2,
	 * "Hilighted element class should contain highlighter"); if (!(SeleniumUtil
	 * .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedMLCHL")) == 0)) {
	 * fail("Hilighted element class should contain highlighter"); }
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedSubCatHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * util.assertActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCatHL()),
	 * PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"),
	 * "verify searched MLC"); }
	 * 
	 * @Test(description = "AT-85042:verify serahc with _", priority = 180,
	 * dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" }) public void
	 * budgtTxnCatSearchwithUnderScrore() throws Exception {
	 * aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue(
	 * "TranSearchCatwithUnderScrore"));
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"),
	 * "verify searched MLC");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedSubCatHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * util.assertActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCatHL()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"),
	 * "verify searched MLC");
	 * Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
	 * "Hilighted element class should contain highlighter"); if (!(SeleniumUtil
	 * .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedMLCHL")) == 0)) {
	 * fail("Hilighted element class should contain highlighter"); } }
	 * 
	 * @Test(description = "AT-85042:verify search with  dot(.)", priority = 181,
	 * dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" }) public void
	 * budgtTxnCatSearchwithDot() throws Exception {
	 * aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue(
	 * "TranSearchCatwithUnderDot"));
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"),
	 * "verify searched MLC");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedSubCatHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * util.assertActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCatHL()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"),
	 * "verify searched MLC");
	 * Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
	 * "Hilighted element class should contain highlighter"); if (!(SeleniumUtil
	 * .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedMLCHL")) == 0)) {
	 * fail("Hilighted element class should contain highlighter"); } }
	 * 
	 * @Test(description = "AT-85042:verify search with all Spacial Char", priority
	 * = 182, dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" }, enabled =
	 * false) public void budgtTxnCatSearchwithAllSpecialChar() throws Exception {
	 * String specialCharList[] =
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
	 * String subCatList[] =
	 * PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").
	 * split(","); String HLCList[] =
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(
	 * ","); String MLCList[] =
	 * PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(
	 * ","); for (int i = 0; i < specialCharList.length; i++) {
	 * aggTran.tansactionDetailsCatSerach(specialCharList[i]);
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()), HLCList[i], "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()), MLCList[i], "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()), subCatList[i], "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCatHL()), specialCharList[i], "verify searched MLC");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedSubCatHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
	 * "Hilighted element class should contain highlighter"); if (!(SeleniumUtil
	 * .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedMLCHL")) == 0)) {
	 * fail("Hilighted element class should contain highlighter"); } } }
	 * 
	 * @Test(description = "AT-85034:verify search with 3rd word", priority = 183,
	 * dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" }) public void
	 * budgtTxnCatSearchwith3rdWord() throws Exception {
	 * aggTran.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue(
	 * "TranSearchCatwith3rdWord"));
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedHLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedMLC()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"),
	 * "verify searched MLC");
	 * util.assertExpectedActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCat()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordSub"),
	 * "verify searched MLC");
	 * util.assertActualList(util.getWebElementsValue(aggTran.
	 * tranDtlCatSearchedSubCatHL()),
	 * PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"),
	 * "verify searched MLC");
	 * util.assertActualList(util.getWebElementsAttributeName(aggTran.
	 * tranDtlCatSearchedSubCatHL(), "class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "verify the class name");
	 * Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
	 * "Hilighted element class should contain highlighter"); if (!(SeleniumUtil
	 * .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedMLCHL")) == 0)) {
	 * fail("Hilighted element class should contain highlighter"); } }
	 * 
	 * @Test(description = "AT-85044,AT-85046:verify category search clear",
	 * priority = 184, dependsOnMethods = { "budgtTxnCatSearchSubWithStartNum" })
	 * public void budgtTxnCatSearchClear() throws Exception {
	 * SeleniumUtil.click(aggTran.tranDtlsCatSearchTextClose()); int length =
	 * aggTran.tranDtlsCatSearchField().getAttribute("value").length(); int size =
	 * SeleniumUtil .getElementCount(SeleniumUtil.getByObject("Transaction", null,
	 * "tranDtlCatSearchedSubCatHL")); int size2 =
	 * aggTran.tranDtlCatSearchedMLC().size(); if (aggTran.isbudgetCloseMobile()) {
	 * SeleniumUtil.click(aggTran.budgetCloseMobile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(aggTran.mobilebudgetTransactionDetailsBack());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); }
	 * Assert.assertEquals(length, 0); Assert.assertEquals(size, 0);
	 * Assert.assertEquals(size2, 59); }
	 */
	@Test(description = "AT-85042:verify search with sub", priority = 185, dependsOnMethods = {
			"catFilteSearchSubWithStartNum" })
	public void CRCatSearchSubWithStartNum() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.click(rule.CRCreatButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		wait.until(ExpectedConditions.visibilityOf(rule.CRCatField()));
		SeleniumUtil.click(rule.CRCatField());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		rule.searchCategory(PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"));
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithNumExpectedSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(rule.CRCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(rule.CRCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNum"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithNumExpectedHLC"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify serach with MLC and SUb category", priority = 186, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" })
	public void CRCatSearchMLCWithSubCat() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"));
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecCharMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchSubCatWithSpecCharSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedMLCHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLCHL()),
				PropsUtil.getDataPropertyValue("TranSearchSubCatWithSpecChar"), "verify searched MLC");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedSubCatHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		Assert.assertEquals(rule.CRCatSearchedMLCHL().size(), 1, "Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85042:verify search with %", priority = 187, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" })
	public void CRCatSearchwithApm() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwithAmp"));
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithAmpSub1"), "verify searched MLC");
		Assert.assertEquals(rule.CRCatSearchedSubCatHL().size(), 2,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(rule.CRCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranDtlsSearchCatwithAmp"), "verify searched MLC");
	}

	@Test(description = "AT-85042:verify search with _", priority = 188, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" })
	public void CRCatSearchwithUnderScrore() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"));
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScroreSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(aggTran.tranDtlCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(aggTran.tranDtlCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderScrore"), "verify searched MLC");
		Assert.assertEquals(aggTran.tranDtlCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:search with dot(.)", priority = 189, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" })
	public void CRCatSearchwithDot() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"));
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotMLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedSubCat()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithDotSub"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		util.assertActualList(util.getWebElementsValue(rule.CRCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwithUnderDot"), "verify searched MLC");
		Assert.assertEquals(rule.CRCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85042:verify All special char", priority = 190, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" }, enabled = false)
	public void CRCatSearchwithAllSpecialChar() throws Exception {
		String specialCharList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialChar").split(",");
		String subCatList[] = PropsUtil.getDataPropertyValue("TranDdlsSearchCatwithAllSpecialCharSub").split(",");
		String HLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharHLC").split(",");
		String MLCList[] = PropsUtil.getDataPropertyValue("TranSearchCatwithAllSpecialCharMLC").split(",");
		for (int i = 0; i < specialCharList.length; i++) {
			rule.searchCategory(specialCharList[i]);
			util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedHLC()), HLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLC()), MLCList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedSubCat()), subCatList[i],
					"verify searched MLC");
			util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedSubCatHL()), specialCharList[i],
					"verify searched MLC");
			util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedSubCatHL(), "class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
			Assert.assertEquals(rule.CRCatSearchedSubCatHL().size(), 1,
					"Hilighted element class should contain highlighter");
			if (!(SeleniumUtil
					.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedMLCHL")) == 0)) {
				fail("Hilighted element class should contain highlighter");
			}
		}
	}

	@Test(description = "AT-85034:verify search with 3rd word", priority = 191, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" })
	public void CRCatSearchwith3rdWord() throws Exception {
		rule.searchCategory(PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"));
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedHLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordHLC"), "verify searched MLC");
		util.assertExpectedActualList(util.getWebElementsValue(rule.CRCatSearchedMLC()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWordMLC"), "verify searched MLC");
		util.assertActualList(util.getWebElementsValue(rule.CRCatSearchedSubCatHL()),
				PropsUtil.getDataPropertyValue("TranSearchCatwith3rdWord"), "verify searched MLC");
		util.assertActualList(util.getWebElementsAttributeName(rule.CRCatSearchedSubCatHL(), "class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "verify the class name");
		Assert.assertEquals(rule.CRCatSearchedSubCatHL().size(), 1,
				"Hilighted element class should contain highlighter");
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedMLCHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
	}

	@Test(description = "AT-85044,AT-85046:verify category search clear", priority = 192, dependsOnMethods = {
			"CRCatSearchSubWithStartNum" })
	public void CRCatSearchClear() throws Exception {
		SeleniumUtil.click(rule.CRCatSearcheTextCLose());
		int length = rule.CRCatSearchField().getAttribute("value").length();
		int size = SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "CRCatSearchedSubCatHL"));
		int size2 = rule.CRCatSearchedMLC().size();
		if (rule.isCRCatSearcheTextCLoseMobile()) {
			SeleniumUtil.click(rule.CRCatSearcheTextCLoseMobile());
			SeleniumUtil.click(rule.createRuleCrossicon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(rule.createRuleCrossicon());
		}
		Assert.assertEquals(length, 0);
		Assert.assertEquals(size, 0);
		Assert.assertEquals(size2, 59);
	}

	@Test(description = "AT-85047:verify tag search", priority = 193)
	public void txnDtsDefaultTagSearchStarttLetter() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("TransactionFilter1.site16441.4", "site16441.4");
		PageParser.forceNavigate("Transaction", d);
		wait.until(ExpectedConditions.visibilityOf(aggTran.PendingStranctionList().get(0)));
		SeleniumUtil.click(aggTran.PendingStranctionList().get(0));
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.click(aggTran.TransactionTagLink());
		if (aggTran.isTransactionTagFieldMobile()) {
			aggTran.TransactionTagFieldMobile()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearch1"));
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedTag1"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedHL"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			aggTran.TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearch1"));
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedTag1"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedHL"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach clear", priority = 194, dependsOnMethods = {
			"txnDtsDefaultTagSearchStarttLetter" })
	public void txnDtsHLDisapper() throws Exception {
		SeleniumUtil.click(tag.tranTagClear().get(0));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(tag.tranDetailsTagHL().size(), 0, "Highlight element should not be dispalyed");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (aggTran.isMobiletagBack()) {
			SeleniumUtil.click(aggTran.MobiletagBack());
			SeleniumUtil.click(aggTran.mobileTransactionDetailsBack());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
	}

	@Test(description = "AT-85047:verify tag serach", priority = 195, dependsOnMethods = { "txnDtsHLDisapper" })
	public void STDtsDefaultTagSearchStarttLetter() throws Exception {
		SeleniumUtil.click(add_Manual.ProjectedExapdIcon());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		wait.until(ExpectedConditions.visibilityOf(seriesTxn.projectedStranctionList().get(0)));
		SeleniumUtil.click(seriesTxn.projectedStranctionList().get(0));
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.click(aggTran.TransactionTagLink());
		if (aggTran.isTransactionTagFieldMobile()) {
			aggTran.TransactionTagFieldMobile()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearch1"));
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedTag1"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedHL"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			aggTran.TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearch1"));
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedTag1"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedHL"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach clear", priority = 196, dependsOnMethods = { "txnDtsHLDisapper" })
	public void STDtsHLDisapper() throws Exception {
		SeleniumUtil.click(tag.tranTagClear().get(0));
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDetailsTagHL")) == 0)) {
			fail("Hilighted element class should contain highlighter");
		}
		if (aggTran.isMobiletagBack()) {
			SeleniumUtil.click(aggTran.MobiletagBack());
			SeleniumUtil.click(aggTran.mobileTransactionDetailsBack());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
	}

	@Test(description = "AT-85047:verify tag search", priority = 197)
	public void txnMTDefaultTagSearchStarttLetter() throws Exception {
		// run from here
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (add_Manual.isMoreBtnPresent()) {
			SeleniumUtil.click(add_Manual.MobileMore());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(add_Manual.MobileaddManualLink_AMT());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			wait.until(ExpectedConditions.visibilityOf(add_Manual.addManualLink()));
			SeleniumUtil.click(add_Manual.addManualLink());
		}
		wait.until(ExpectedConditions.visibilityOf(add_Manual.AddManulTransactionTag()));
		SeleniumUtil.click(add_Manual.AddManulTransactionTag());
		if (add_Manual.isAddManualTransactTagMobile()) {
			add_Manual.AddManualTransactTagMobile()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearch1"));
			Assert.assertEquals(tag.tranMTTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedTag1"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranMTTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedHL"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranMTTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			add_Manual.AddManualTransactTag().sendKeys(PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearch1"));
			Assert.assertEquals(tag.tranMTTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedTag1"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranMTTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsDefaultTagSearchedHL"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranMTTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag search clear", priority = 198, dependsOnMethods = {
			"txnMTDefaultTagSearchStarttLetter" })
	public void txnMTHLDisapear() throws Exception {
		SeleniumUtil.click(tag.tranTagClear().get(0));
		if (!(SeleniumUtil.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranMTTagHL")) == 0)) {
			fail("Highlight element should not be dispalyed");
		}
	}

	@Test(description = "AT-85047", priority = 199, dependsOnMethods = { "txnMTDefaultTagSearchStarttLetter" })
	public void txnDtsUserAddedTagSearchStarttLetter() throws Exception {
		if (aggTran.isMobiletagBack()) {
			SeleniumUtil.click(aggTran.MobiletagBack());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(tag.crossIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(add_Manual.close());
		}
		wait.until(ExpectedConditions.visibilityOf(aggTran.PendingStranctionList().get(0)));
		String userTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddtTag").split(",");
		SeleniumUtil.click(aggTran.PendingStranctionList().get(0));
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.click(aggTran.TransactionTagLink());
		for (int i = 0; i < 5; i++) {
			aggTran.createTag1(userTag[i]);
		}
		if (tag.isbackToTransactionDetailsMobile()) {
			SeleniumUtil.click(tag.backToTransactionDetailsMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(aggTran.update());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(2);
		SeleniumUtil.click(aggTran.PendingStranctionList().get(0));
		SeleniumUtil.waitForPageToLoad();
		String useraddedTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		SeleniumUtil.click(aggTran.TransactionTagLink());
		SeleniumUtil.waitForPageToLoad();
		aggTran.tagSearch(useraddedTag[0]);
		int searchedTagSize = tag.tranDetailsTag().size();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTagMobile().get(i).getText(), useraddedTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getText(), useraddedTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		} else {
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTag().get(i).getText(), useraddedTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getText(), useraddedTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		}
	}

	@Test(description = "AT-85047:verify tag search with second word", priority = 200, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void txnDtsUserAddedTagSearchSeconWord() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag search with num", priority = 201, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void txnDtsUserAddedTagSearchNum() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach with start letter", priority = 202, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void txnSTUserAddedTagSearchStarttLetter() throws Exception {
		String userTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
		try {
			SeleniumUtil.click(add_Manual.ProjectedExapdIcon());
		} catch (Exception e) {
		}
		SeleniumUtil.click(seriesTxn.projectedStranctionList().get(0));
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.click(aggTran.TransactionTagLink());
		aggTran.tagSearch(userTag[0]);
		int searchedTagSize = tag.tranDetailsTag().size();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTagMobile().get(i).getText(), userTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getText(), userTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		} else {
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTag().get(i).getText(), userTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getText(), userTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		}
	}

	@Test(description = "AT-85047:verify tag search with second word", priority = 203, dependsOnMethods = {
			"txnSTUserAddedTagSearchStarttLetter" })
	public void txnSTUserAddedTagSearchSeconWord() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach with num", priority = 204, dependsOnMethods = {
			"txnSTUserAddedTagSearchStarttLetter" })
	public void txnSTUserAddedTagSearchNum() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tga search with start letter", priority = 205, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void txnMTUserAddedTagSearchStarttLetter() throws Exception {
		String userTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
		if (appFlag.contentEquals("app") || appFlag.contentEquals("emulator")) {
			SeleniumUtil.refresh(d);
			PageParser.forceNavigate("Transaction", d);
			SeleniumUtil.click(add_Manual.MobileMore());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(add_Manual.MobileaddManualLink_AMT());
		} else {
			SeleniumUtil.click(add_Manual.addManualLink());
		}
		wait.until(ExpectedConditions.visibilityOf(add_Manual.AddManulTransactionTag()));
		SeleniumUtil.click(add_Manual.AddManulTransactionTag());
		if (add_Manual.isAddManualTransactTagMobile()) {
			add_Manual.AddManualTransactTagMobile().sendKeys(userTag[0]);
		} else {
			add_Manual.AddManualTransactTag().sendKeys(userTag[0]);
		}
		int searchedTagSize = tag.tranMTTag().size();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranMTTagMobile().get(i).getText(), userTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranMTTagHLMobile().get(i).getText(), userTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranMTTagHLMobile().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		} else {
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranMTTag().get(i).getText(), userTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranMTTagHL().get(i).getText(), userTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranMTTagHL().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		}
	}

	@Test(description = "AT-85047:verify tag serach with second word", priority = 206, dependsOnMethods = {
			"txnMTUserAddedTagSearchStarttLetter" })
	public void txnMTUserAddedTagSearchSeconWord() throws Exception {
		if (add_Manual.isAddManualTransactTagMobile()) {
			add_Manual.AddManualTransactTagMobile().clear();
			add_Manual.AddManualTransactTagMobile()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
			Assert.assertEquals(tag.tranMTTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranMTTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranMTTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			add_Manual.AddManualTransactTag().clear();
			add_Manual.AddManualTransactTag()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
			Assert.assertEquals(tag.tranMTTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranMTTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranMTTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach with num", priority = 207, dependsOnMethods = {
			"txnMTUserAddedTagSearchStarttLetter" })
	public void txnMTUserAddedTagSearchNum() throws Exception {
		if (add_Manual.isAddManualTransactTagMobile()) {
			add_Manual.AddManualTransactTagMobile().clear();
			add_Manual.AddManualTransactTagMobile()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
			Assert.assertEquals(tag.tranMTTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranMTTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranMTTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			add_Manual.AddManualTransactTag().clear();
			add_Manual.AddManualTransactTag()
					.sendKeys(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
			Assert.assertEquals(tag.tranMTTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranMTTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranMTTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach with start letter", priority = 208, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void tagFiltAddedTagSearchStarttLetter() throws Exception {
		if (aggTran.isMobiletagBack()) {
			SeleniumUtil.click(aggTran.MobiletagBack());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(tag.crossIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			accountDropDown.clickFilter().click();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(add_Manual.close());
		}
		SeleniumUtil.click(tag.tagTab());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String userTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
		tag.tagFilteTagSearch(userTag[0]);
		int searchedTagSize = tag.tranFilterTag().size();
		for (int i = 0; i < searchedTagSize; i++) {
			Assert.assertEquals(tag.tranFilterTag().get(i).getText(), userTag[i],
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranFilterTagHL().get(i).getText(), userTag[0],
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranFilterTagHL().get(i).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag serach with second word", priority = 209, dependsOnMethods = {
			"tagFiltAddedTagSearchStarttLetter" })
	public void tagFiltAddedTagSearchSeconWord() throws Exception {
		tag.tagFilteTagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
		Assert.assertEquals(tag.tranFilterTag().get(0).getText(),
				PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
				"searched string should mached with start later of the tag");
		Assert.assertEquals(tag.tranFilterTagHL().get(0).getText(),
				PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
				"only searched String should be higllighted in Tag list");
		Assert.assertEquals(tag.tranFilterTagHL().get(0).getAttribute("class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"highlighted string class sould be Hilighted");
	}

	@Test(description = "AT-85047:verify tag search with num", priority = 210, dependsOnMethods = {
			"tagFiltAddedTagSearchStarttLetter" })
	public void tagFiltAddedTagSearchNum() throws Exception {
		tag.tagFilteTagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
		Assert.assertEquals(tag.tranFilterTag().get(0).getText(),
				PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
				"searched string should mached with start later of the tag");
		Assert.assertEquals(tag.tranFilterTagHL().get(0).getText(),
				PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
				"only searched String should be higllighted in Tag list");
		Assert.assertEquals(tag.tranFilterTagHL().get(0).getAttribute("class"),
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
				"highlighted string class sould be Hilighted");
//run till here
	}

	@Test(description = "AT-85047:verify tag serach with start letter", priority = 211, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void EADtsUserAddedTagSearchStarttLetter() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanse.clickThisMonthTransaction();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		expanse.clickEATxn1(0, 0);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(expanse.totaltranamount().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(expanse.totaltranamount1().get(0));
		} else {
			SeleniumUtil.click(expanse.tranamount1().get(0));
		}
		String useraddedTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.click(aggTran.TransactionTagLink());
		aggTran.tagSearch(useraddedTag[0]);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			int searchedTagSize = tag.tranDetailsTagMobile().size();
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTagMobile().get(i).getText(), useraddedTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getText(), useraddedTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		} else {
			int searchedTagSize = tag.tranDetailsTag().size();
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTag().get(i).getText(), useraddedTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getText(), useraddedTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		}
	}

	@Test(description = "AT-85047:verify tag serach with second word", priority = 212, dependsOnMethods = {
			"EADtsUserAddedTagSearchStarttLetter" })
	public void EADtsUserAddedTagSearchSeconWord() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag search with num", priority = 213, dependsOnMethods = {
			"EADtsUserAddedTagSearchStarttLetter" })
	public void EADtsUserAddedTagSearchNum() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag search withstart letter", priority = 214, dependsOnMethods = {
			"txnDtsUserAddedTagSearchStarttLetter" })
	public void IADtsUserAddedTagSearchStarttLetter() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanse.clickIATxn(0, 0);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(expanse.totaltranamount().get(0));
			SeleniumUtil.click(expanse.totaltranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(expanse.tranamount1().get(0));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		String useraddedTag[] = PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
		wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
		SeleniumUtil.click(aggTran.TransactionTagLink());
		aggTran.tagSearch(useraddedTag[0]);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			int searchedTagSize = tag.tranDetailsTagMobile().size();
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTagMobile().get(i).getText(), useraddedTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getText(), useraddedTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		} else {
			int searchedTagSize = tag.tranDetailsTag().size();
			for (int i = 0; i < searchedTagSize; i++) {
				Assert.assertEquals(tag.tranDetailsTag().get(i).getText(), useraddedTag[i],
						"searched string should mached with start later of the tag");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getText(), useraddedTag[0],
						"only searched String should be higllighted in Tag list");
				Assert.assertEquals(tag.tranDetailsTagHL().get(i).getAttribute("class"),
						PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
						"highlighted string class sould be Hilighted");
			}
		}
	}

	@Test(description = "AT-85047:tag serach with second word", priority = 215, dependsOnMethods = {
			"IADtsUserAddedTagSearchStarttLetter" })
	public void IADtsUserAddedTagSearchSeconWord() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
					"searched string should mached with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
					"only searched String should be higllighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class sould be Hilighted");
		}
	}

	@Test(description = "AT-85047:verify tag search with num", priority = 216, dependsOnMethods = {
			"IADtsUserAddedTagSearchStarttLetter" })
	public void IADtsUserAddedTagSearchNum() throws Exception {
		aggTran.tagSearch(PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"));
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should matched with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be highlighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class should be Highlighted");
		} else {
			Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
					"searched string should matched with start later of the tag");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
					PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
					"only searched String should be highlighted in Tag list");
			Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
					PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
					"highlighted string class should be Highlighted");
		}
	}

	/* Commenting testcases because they are written for old budget
	 * @Test(description = "AT-85047:verify tag search with Start letter", priority
	 * = 217, dependsOnMethods = { "txnDtsUserAddedTagSearchStarttLetter" }) public
	 * void budgtDtsUserAddedTagSearchStarttLetter() throws Exception {
	 * SeleniumUtil.refresh(d); PageParser.forceNavigate("Budget", d);
	 * budget.creatBudget(); SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); if
	 * (budget.isviewmoredetailsmobile()) {
	 * SeleniumUtil.click(budget.viewmoredetailsmobile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); } if (appFlag.equals("app")
	 * || appFlag.equals("emulator")) {
	 * wait.until(ExpectedConditions.visibilityOf(budget.MobileATMwithdrow_TBI().get
	 * (0))); } else {
	 * wait.until(ExpectedConditions.visibilityOf(budget.ATMwithdrow().get(0))); }
	 * budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue(
	 * "Budget_Txn_CatSerach_BudgetCategory"), 0); String useraddedTag[] =
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddedtTag").split(",");
	 * wait.until(ExpectedConditions.visibilityOf(aggTran.TransactionTagLink()));
	 * SeleniumUtil.click(aggTran.TransactionTagLink());
	 * aggTran.tagSearch(useraddedTag[0]); if (appFlag.equals("app") ||
	 * appFlag.equals("emulator")) { int searchedTagSize =
	 * tag.tranDetailsTagMobile().size(); for (int i = 0; i < searchedTagSize; i++)
	 * { Assert.assertEquals(tag.tranDetailsTagMobile().get(i).getText(),
	 * useraddedTag[i],
	 * "searched string should mached with start later of the tag");
	 * Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getText(),
	 * useraddedTag[0], "only searched String should be higllighted in Tag list");
	 * Assert.assertEquals(tag.tranDetailsTagHLMobile().get(i).getAttribute("class")
	 * , PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "highlighted string class sould be Hilighted"); } } else { int
	 * searchedTagSize = tag.tranDetailsTag().size(); for (int i = 0; i <
	 * searchedTagSize; i++) {
	 * Assert.assertEquals(tag.tranDetailsTag().get(i).getText(), useraddedTag[i],
	 * "searched string should mached with start later of the tag");
	 * Assert.assertEquals(tag.tranDetailsTagHL().get(i).getText(), useraddedTag[0],
	 * "only searched String should be higllighted in Tag list");
	 * Assert.assertEquals(tag.tranDetailsTagHL().get(i).getAttribute("class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "highlighted string class sould be Hilighted"); } } }
	 * 
	 * @Test(description = "AT-85047:verify tag serach with second word", priority =
	 * 218, dependsOnMethods = { "budgtDtsUserAddedTagSearchStarttLetter" }) public
	 * void budgtDtsUserAddedTagSearchSeconWord() throws Exception {
	 * aggTran.tagSearch(PropsUtil.getDataPropertyValue(
	 * "tranDetailsUserAddtTwoWordTagSerach")); if (appFlag.equals("app") ||
	 * appFlag.equals("emulator")) {
	 * Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
	 * "searched string should mached with start later of the tag");
	 * Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
	 * "only searched String should be higllighted in Tag list");
	 * Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class")
	 * , PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "highlighted string class sould be Hilighted"); } else {
	 * Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTag"),
	 * "searched string should mached with start later of the tag");
	 * Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddtTwoWordTagSerach"),
	 * "only searched String should be higllighted in Tag list");
	 * Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "highlighted string class sould be Hilighted"); } }
	 * 
	 * @Test(description = "AT-85047:verify tga search with num", priority = 219,
	 * dependsOnMethods = { "budgtDtsUserAddedTagSearchStarttLetter" }) public void
	 * budgtDtsUserAddedTagSearchNum() throws Exception {
	 * aggTran.tagSearch(PropsUtil.getDataPropertyValue(
	 * "tranDetailsUserAddTagNumSearch")); if (appFlag.equals("app") ||
	 * appFlag.equals("emulator")) {
	 * Assert.assertEquals(tag.tranDetailsTagMobile().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
	 * "searched string should mached with start later of the tag");
	 * Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
	 * "only searched String should be higllighted in Tag list");
	 * Assert.assertEquals(tag.tranDetailsTagHLMobile().get(0).getAttribute("class")
	 * , PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "highlighted string class sould be Hilighted"); } else {
	 * Assert.assertEquals(tag.tranDetailsTag().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNum"),
	 * "searched string should mached with start later of the tag");
	 * Assert.assertEquals(tag.tranDetailsTagHL().get(0).getText(),
	 * PropsUtil.getDataPropertyValue("tranDetailsUserAddTagNumSearch"),
	 * "only searched String should be higllighted in Tag list");
	 * Assert.assertEquals(tag.tranDetailsTagHL().get(0).getAttribute("class"),
	 * PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"),
	 * "highlighted string class sould be Hilighted"); } }
	 */
}