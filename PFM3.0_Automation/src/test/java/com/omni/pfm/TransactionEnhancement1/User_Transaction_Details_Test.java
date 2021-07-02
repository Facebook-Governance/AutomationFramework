/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;

public class User_Transaction_Details_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(User_Transaction_Details_Test.class);
	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
	Transaction_Details_Loc details;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		filter = new Transaction_Filter_Loc(d);
		details = new Transaction_Details_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(description = "AT-68217", priority = 1)
	public void verifyLinkAccountButtonInTransaction() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(details.transactionLinkAccount().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionLinkAccountButton"),
				"link an button should displayed in transaction Nod ata screen");
	}

	@Test(description = "AT-68228", priority = 2)
	public void verifyNodataScreenHeader() {
		Assert.assertEquals(details.transactionNoDataMessageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionNoDataScreenHeader"),
				"verify noData screen message header in transaction finapp");
	}

	@Test(description = "AT-68228,AT-68230", priority = 3)
	public void verifyNodataScreenMessage() {
		Assert.assertEquals(details.transactionNoDataMessage().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionNoDataScreenMessage"),
				"verify noData screen message  in transaction finapp");
	}
}
