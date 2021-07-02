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
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class BudgetAndCashFlow extends TestBase{
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

	@Test(description="navigating to Budget page.", priority = 1, enabled = true)
	public void navigateToTrasaction() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="verifying page without budget.", priority = 2, enabled = true)
	public void verifyingBudgetHeader() {
		Assert.assertEquals(oboLoc.CreateBudgetHeader().getText(),PropsUtil.getDataPropertyValue("BudgetHeader"));
	}
	
	@Test(description="verify budget creation", priority = 3, enabled = true)
	public void creatingBudget() {
		Assert.assertTrue(oboLoc.getStartedButton().isDisplayed());
		SeleniumUtil.click(oboLoc.getStartedButton());
		 SeleniumUtil.click(oboLoc.NextButton1());
		 
		 SeleniumUtil.click(oboLoc.editIncome());
		 oboLoc.editincometext().clear();
		 oboLoc.editincometext().sendKeys(PropsUtil.getDataPropertyValue("IncomeSalary").trim());
		 SeleniumUtil.click(oboLoc.SaveButton());
		 SeleniumUtil.click(oboLoc.nextButton2());
		 SeleniumUtil.click(oboLoc.editflexicon());
		 
		 int CatSize=oboLoc.editFlexibleCat().size();
		 for(int i=1;i<CatSize;i++) {
			 oboLoc.editFlexibleCat().get(i).clear();
			 oboLoc.editFlexibleCat().get(i).sendKeys(PropsUtil.getDataPropertyValue("FlexiValue").trim()); 
		 }
		 
		 SeleniumUtil.click(oboLoc.incomeSaveButton());
		 SeleniumUtil.click(oboLoc.incomeNextButton());
	}
	
	@Test(description="verify budget page after budget creation", priority = 4, enabled = true)
	public void verifyingAfterBudgetCreation() {
		String actual=oboLoc.budgetDetailHeader().getText().trim();
		String expected=PropsUtil.getDataPropertyValue("BudgetDetailsHeader").trim();
		
		Assert.assertEquals(actual, expected);
		Assert.assertTrue(oboLoc.budgetSummary().isDisplayed());	
	}
	
	@Test(description="delete account from account group.", priority = 5, enabled = true)
	public void deleteGroupAndBackToBudget() {
		    PageParser.forceNavigate("AccountGroups", d);
		    SeleniumUtil.waitForPageToLoad();	
			SeleniumUtil.click(oboLoc.deleteIcon());
			SeleniumUtil.waitForElement(null, 500);
			SeleniumUtil.click(oboLoc.deleteBtn());
		}	
	
	/*
	 * CashFlow 
	 */
	
	@Test(description="navigating to cash flow page.", priority = 6, enabled = true)
	public void navigateToCashFlow() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(oboLoc.continueButton());
		SeleniumUtil.click(oboLoc.goToFinappCoachMark());
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description="verifying cash flow header.", priority = 7, enabled = true)
	public void verifyingCashFlowHeader() {
		Assert.assertEquals(oboLoc.headerTitle().getText(),PropsUtil.getDataPropertyValue("CashFlowHeader"));
	}
	
	@Test(description="verifying cash flow inflow outflow and networth values.", priority = 8, enabled = true)
	public void verifyingCashFlowValues() {
		
		String expected[]=PropsUtil.getDataPropertyValue("ExpectedList").split(",");
		for(int i=0;i<oboLoc.ValueList().size();i++) {
			String actual=oboLoc.ValueList().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
			
		}
	}
}
