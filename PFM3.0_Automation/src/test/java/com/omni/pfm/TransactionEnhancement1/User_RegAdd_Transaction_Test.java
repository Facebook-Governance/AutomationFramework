/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class User_RegAdd_Transaction_Test extends TestBase {

	String cobrand;

	String cobrandPass;

	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(User_RegAdd_Transaction_Test.class);
	LoginPage login;
	AccountAddition accpuntAdd;
	LoginPage_SAML3 SAMLlogin;

	@BeforeClass

	public void testInit() throws Exception {
		doInitialization("Transaction Login");
		TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		accpuntAdd = new AccountAddition();
		SAMLlogin = new LoginPage_SAML3();
	}

	@Test
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		logger.info("***************Successfully Login into the Application*****************");
		SeleniumUtil.waitForPageToLoad();
		accpuntAdd.linkAccount();
		accpuntAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		logger.info("***************Accounts are added successfully*****************");
	}

}
