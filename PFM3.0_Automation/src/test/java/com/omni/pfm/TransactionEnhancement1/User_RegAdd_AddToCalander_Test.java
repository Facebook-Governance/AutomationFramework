/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.TransactionEnhancement1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class User_RegAdd_AddToCalander_Test extends TestBase {
	String cobrand;
	String cobrandPass;
	public static String TransactionloginName;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass
	public void testInit() throws Exception {
		doInitialization("Transaction Login");
		TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		login = new LoginPage();
	}

	@Test
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "addcal.bank1", "bank1");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}
}
