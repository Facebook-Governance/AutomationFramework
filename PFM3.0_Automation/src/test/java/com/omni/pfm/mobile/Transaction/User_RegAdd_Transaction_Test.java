/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.mobile.Transaction;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.Page;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAdditionMobile;
import com.omni.pfm.pages.Login.L1IFrameLogin;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

public class User_RegAdd_Transaction_Test extends TestBase {
	

	String cobrand;

	String cobrandPass;

	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(User_RegAdd_Transaction_Test.class);
    LoginPage login;
	AccountAdditionMobile accpuntAdd;
	Add_Manual_Transaction_Loc add_manual_transaction;
	
	@BeforeClass

	public void testInit() throws Exception {
		
		
		
		 doInitialization("Transaction Login");
	        
	        TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
	        TestBase.test.appendChild(TestBase.tc);
	        login=new LoginPage();
	        accpuntAdd=new AccountAdditionMobile();
	        add_manual_transaction=new Add_Manual_Transaction_Loc(d);
			
		    
		
	}
	@Test(priority=1)
	public void login() throws Exception
	{
		    LoginPage.loginMain(d,loginParameter);
		
		    
		     accpuntAdd.linkAccount();
	          accpuntAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		    
		
	}
	
	@Test(priority=2)
	public void NagivateTotransaction() throws Exception
	{
		
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		
		System.out.println(add_manual_transaction.getAllPostedAmount1().size());
		SeleniumUtil.click(d.findElement(By.cssSelector(".svg_icon-more-v")));
		SeleniumUtil.click(d.findElement(By.id("menu-add-manual-transaction-btn")));
		
	}
	
	@Test(description = "AT-29561:TRA_01_03:Verify the Add a Transaction pop up window displayed", priority = 4)
	void verifyAddManualTransactPopHeader() throws InterruptedException {
		//Verify the Add a Transaction pop up window displayed after click on the "Add a Transaction" link in transaction FinApp home page.
		
			
			Assert.assertEquals(add_manual_transaction.addManualHeader().getText(),
					PropsUtil.getDataPropertyValue("ManulaTransactionHeader"));

		
	}

	@Test(description = "AT-29562:TRA_01_04:Verify the Currency dropdown displayed", priority = 5, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyCurrencyDropDown() {
		// AT-29562 Verify the Currency dropdown displayed in the Add aTransaction window
		Assert.assertTrue(add_manual_transaction.currencyDroDown().isDisplayed());

	}

	@Test(description = "AT-29563:TRA_01_05:Verify the by default USD currency symbol selected in the dropdown", priority = 6, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void verifyCurrencyDropDownDefaultValue() { 
		// Verify the by default USD currency symbol selected in the dropdown

		Assert.assertEquals(add_manual_transaction.currencyValue().getText(),
				PropsUtil.getDataPropertyValue("DefaultCurrency"));
	}
	
	@Test(description = "AT-29565:TRA_01_06:Verify the Amount Field displayed next to the Currency field", priority = 7, dependsOnMethods = "verifyAddManualTransactPopHeader")
    void verifyAmountField() { 
		//Verify the "Amount Field" displayed next to the Currency field

		Assert.assertTrue(add_manual_transaction.amount().isDisplayed());
		
	}

	@Test(description = "AT-29566:TRA_01_07:Verify the transaction type withdrawal selected by default ", priority = 8, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void transactionTypeDefaultValue() {
		//Verify the transaction type withdrawal selected by default
		Assert.assertEquals(add_manual_transaction.TransactionType().getText(),
				PropsUtil.getDataPropertyValue("DefaultTraType"));

	}
	@Test(description = "AT-29567:TRA_01_08:Verify the Frequency text and dropdown field displayed ", priority = 9,dependsOnMethods = "verifyAddManualTransactPopHeader")
     void frequencyTypeDisplyed()
       {
		//Verify the "Frequency"  text and dropdown field displayed below the Accounts field in the Add a Transaction pop up
		Assert.assertTrue(add_manual_transaction.frequencytext().isDisplayed());
       }
	@Test(description = "AT-29568:TRA_01_09:Verify by default One Time option selected by default", priority = 10, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void frequencyTypeDefaultValue() {
		//Verify by default "One Time" option selected by default in Frequency field when invoked from transaction FinApp
           Assert.assertEquals(add_manual_transaction.frequencytext().getText(),
				PropsUtil.getDataPropertyValue("frequency"));
	}

	@Test(description = "AT-29570:TRA_01_10:Verify the Scheduled Date field displayed below the Frequency Field", priority = 11, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void ScheduleDateFieldDispaly() {
		//Verify the "Scheduled Date" field displayed below the Frequency Field
		Assert.assertTrue(add_manual_transaction.Schedule().isDisplayed());

	}

	@Test(description = "AT-29572:TRA_01_11:calendar icon is displying", priority = 12, dependsOnMethods = "verifyAddManualTransactPopHeader")
	void ScheduleDateIconDispaly() {
		//calendar icon is displying
		Assert.assertTrue(add_manual_transaction.calendIcon().isDisplayed());
	}

	@Test(description = "AT-29572:TRA_01_12:Verify the Calendar displayed after click on the Calendar icon ", priority = 13, dependsOnMethods = "ScheduleDateIconDispaly")
	void ScheduleDateCalendarDispaly() {
		//Verify the Calendar displayed after click on the Calendar icon and click on the date Field

		SeleniumUtil.click(add_manual_transaction.calendIcon());
		SeleniumUtil.waitForPageToLoad();
		boolean calendPopUpvalue = add_manual_transaction.calendPopUp().isDisplayed();
		SeleniumUtil.click(add_manual_transaction.calendIcon());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(calendPopUpvalue);
	}

	@Test(description = "AT-29573:TRA_01_13:Verify the End Date field displayed", priority = 14,dependsOnMethods = {
			"verifyAddManualTransactPopHeader", "frequencyTypeDefaultValue" })
	void EndDateField() {
		//Verify the "End Date" field displayed if user selects the option other than One Time option in the Frequency field like "Weekly" "Every 2 Weeks""Monthly" Etc..

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_manual_transaction.frequencyDropDown());
		// add_manual_transaction.frequencyDropDown().click();
		SeleniumUtil.waitForPageToLoad(10000);
		System.out.println(add_manual_transaction.FrequencyList().size());
		SeleniumUtil.click(add_manual_transaction.FrequencyList().get(1));
		// add_manual_transaction.FrequencyList.get(1).click();
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(add_manual_transaction.endDate().isDisplayed());
	}

	@Test(description = "AT-29573:TRA_01_14:verify end date calander Icon", priority = 15, dependsOnMethods = {
			"EndDateField" })
	void EndDateFieldDateIcon() {
		//verify end date calander Icon
		Assert.assertTrue(add_manual_transaction.endDateIcon().isDisplayed());
	}

	@Test(description = "AT-29574:TRA_01_15:Verify the icon infi icon", priority = 16,dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledInfiIcon() {
		//Verify the icon and text displayed below the Tags Field "Create custom tags to track transaction related to specific events ,such as vacation or taxes"
       Assert.assertTrue(add_manual_transaction.infoIcon().isDisplayed());
	}

	@Test(description = "AT-29574:TRA_01_16:text displayed below the Tags Field Create custom tags to track transaction", priority = 17,dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledInfiMessage() {
		//Verify the icon and text displayed below the Tags Field "Create custom tags to track transaction related to specific events ,such as vacation or taxes"

		Assert.assertEquals(add_manual_transaction.infoMessage().getText(),
				PropsUtil.getDataPropertyValue("InfoMessage"));

	}

	@Test(description = "AT-29575:TRA_01_17:Verify the Add Icon displayed", priority = 18,dependsOnMethods = {
			"verifyAddManualTransactPopHeader" })
	void tagFiledAddIcon() {
		//Verify the Add Icon displayed next to Tag field as mentioned in the Specs

		Assert.assertTrue(add_manual_transaction.pluseIcon().isDisplayed());
	}

	@Test(description = "AT-53678:TRA_01_18:Verify the Text SHOW MORE OPTIONS displayed below the tag field", priority = 19,dependsOnMethods = { "verifyAddManualTransactPopHeader" })
	public void showMoreDetails() {
		
		SeleniumUtil.waitForElement("BBT", 2000);
		Assert.assertEquals(add_manual_transaction.showMoreDetails().getText(),
				PropsUtil.getDataPropertyValue("AggShowMore"));

	}

	@Test(description = "AT-29577:TRA_01_19:Verify the link changes to SHOW LESS OPTIONS after click on the Show More Options link", priority = 20,dependsOnMethods = { "showMoreDetails" })
	public void showLessDetails() {
		SeleniumUtil.click(add_manual_transaction.showMoreDetails());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(add_manual_transaction.showMoreDetails().getText(),
				PropsUtil.getDataPropertyValue("showLess"));

	}
	
}
