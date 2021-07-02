/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.obo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class CategoriesAndCategorisationRules extends TestBase {
	Logger logger=LoggerFactory.getLogger(TransactionsAndNetworth.class);
	OBO_Loc oboLoc;

	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("OBO");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		oboLoc=new OBO_Loc(d);
	}

	@Test(description="creating user and adding account.", priority = 0, enabled = false)
	public void login() throws Exception
	{
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();		
	}

	@Test(description="navigating to categorization page.", priority = 1, enabled = true)
	public void navigateToTrasaction() {
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="verifying categorization header.", priority = 2, enabled = true)
	public void verifyingTransactionHeader() {
		Assert.assertEquals(PropsUtil.getDataPropertyValue("CategoriesHeader").trim(), oboLoc.headerTitle().getText().trim());
	}

	@Test(description = "Ensure that selection sidebar have the list of names as: EXPENSE INCOME TRANSFER DEFERRED ",
			priority = 3, enabled=true)
	public void verifySelectionSideBarOrder() throws Exception 
	{
		String sidebar[] = oboLoc.getCategoryGroupByOrder();

		String expectedSidebar[] = PropsUtil.getDataPropertyValue("sidebar_name").split(",");

		Assert.assertEquals(sidebar, expectedSidebar);

		Assert.assertTrue(oboLoc.isExpenseSelected().getAttribute("class").contains(PropsUtil.getDataPropertyValue("Category_active")));
	}

	@Test(description = "Verify High Level and Master level category names under expense", priority = 3,enabled=true)
	public void verifyHLCMLCUnderExpense() throws Exception 
	{
		String hlc[] = oboLoc.getHLC();
		String expectedHLCunderExpense[] = PropsUtil.getDataPropertyValue("HLC_EXPENSE").split(",");

		String mlc[] = oboLoc.getMLCs();
		String expectedMLCunderExpense[] = PropsUtil.getDataPropertyValue("MLC_EXPENSE").split(",");

		Assert.assertEquals(hlc, expectedHLCunderExpense);

		Assert.assertEquals(mlc, expectedMLCunderExpense);
	}
	
	/*
	 * Categorization Rules
	 */
	 

	@Test(description="navigating to categorization page.", priority = 4, enabled = true)
	public void navigateToCategorzationRules() {
		PageParser.navigateToPage("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad();
	}
	
	@Test(description="verifying categorization Rules header.", priority = 5, enabled = true)
	public void verifyingCategorizationRulesHeader() {
		Assert.assertEquals(PropsUtil.getDataPropertyValue("CategorizationRulesHeader").trim(), oboLoc.headerTitle().getText().trim());
	}
	
	@Test(description = " Verify no rules screen.", priority=6, enabled=true)
	public void verifyNoRulesScreen() throws InterruptedException {

		Assert.assertTrue(oboLoc.noRuleSym().isDisplayed());
		Assert.assertTrue(oboLoc.createRulesButton().isDisplayed());
	}  
	
	@Test(description = " creating rules", priority=7, enabled=true)
	public void createRules() throws InterruptedException {
		
		SeleniumUtil.click(oboLoc.createRulesButton());

		oboLoc.descTextBox().sendKeys(PropsUtil.getDataPropertyValue("catText"));

		oboLoc.amtDescBox().sendKeys(PropsUtil.getDataPropertyValue("catAmt"));
		
		SeleniumUtil.click(oboLoc.categoriesDropDown());
		
		SeleniumUtil.click(oboLoc.getCategoryLL().get(19));
		
		SeleniumUtil.click(oboLoc.categoriesDropDown());


		SeleniumUtil.click(oboLoc.applyRuleSwitchToggle());

		SeleniumUtil.click(oboLoc.createRuleIdButtn());

		SeleniumUtil.waitForPageToLoad();

		logger.info(oboLoc.getRuleText().get(0).getText().trim());
		logger.info(PropsUtil.getDataPropertyValue("ruleNo1").trim());
		Assert.assertTrue(oboLoc.getRuleText().get(0).getText().trim().contains(PropsUtil.getDataPropertyValue("CategorisedRule").trim()));
	}
	
	@Test(description = " refreshing and come back to the same page", priority=8, enabled=true)
	public void refreshAndComeBack() throws InterruptedException {
		d.navigate().refresh();	
		SeleniumUtil.waitForPageToLoad();	
		PageParser.forceNavigate("CategorzationRules", d);
		SeleniumUtil.waitForPageToLoad();	
		Assert.assertTrue(oboLoc.getRuleText().get(0).getText().trim().contains(PropsUtil.getDataPropertyValue("CategorisedRule").trim()));
	}

}
