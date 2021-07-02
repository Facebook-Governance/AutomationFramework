/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.TransactionIframe;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAdditionIframe;
import com.omni.pfm.pages.Login.LoginPage;
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
	AccountAdditionIframe accpuntAdd;
	@BeforeClass

	public void testInit() throws Exception {
		
		
		
		 doInitialization("Transaction Login");
	        
	        TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
	        TestBase.test.appendChild(TestBase.tc);
	        login=new LoginPage();
	        accpuntAdd=new AccountAdditionIframe();
	       
			
		    
		
	}
	@Test
	public void login() throws Exception
	{
		      LoginPage.loginMain(d,loginParameter);
	          SeleniumUtil.waitForPageToLoad();
		      accpuntAdd.linkAccount();
		      SeleniumUtil.waitForPageToLoad();
		      accpuntAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		
	}
	
	
	
}
