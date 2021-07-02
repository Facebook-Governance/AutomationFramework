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
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.DownloadTrans_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Projected_Balance_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class User_Transaction_Filter_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(User_Transaction_Filter_Test.class);

	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
    Transaction_AccountDropDown_Loc accountDropDown;
    Transaction_Search_Loc search;
    Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
    Add_Manual_Transaction_Loc add_Manual;
    Transaction_Layout_Loc layout;
    Transaction_Details_Loc details;
    DownloadTrans_Loc dowlnoad;
    Transaction_Projected_Balance_Loc balance;
    Aggregated_Transaction_Details_Loc aggregatedTransaction;
    Series_Recurence_Transaction_Loc seriesTransaction;
    Transaction_Categorization_Rules_Loc rule;
	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		 doInitialization("Transaction Tag");
		 TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
	     TestBase.test.appendChild(TestBase.tc);
		 accountAdd=new AccountAddition();
		 filter=new Transaction_Filter_Loc(d);
		 accountDropDown=new Transaction_AccountDropDown_Loc(d);
		 search=new Transaction_Search_Loc(d);
		 readOnly=new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		 add_Manual=new Add_Manual_Transaction_Loc(d);
		 layout=new Transaction_Layout_Loc(d);
		 details=new Transaction_Details_Loc(d);
		 dowlnoad=new  DownloadTrans_Loc(d);
		 balance=new Transaction_Projected_Balance_Loc(d);
		 aggregatedTransaction=new Aggregated_Transaction_Details_Loc(d);
		 seriesTransaction=new Series_Recurence_Transaction_Loc(d);
		 rule=new Transaction_Categorization_Rules_Loc(d);
		 d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}
	
	@Test(description="AT-68200",priority=1)
	public void  verifyTransactionHeader() throws Exception
	{
		LoginPage.loginMain(d,loginParameter);
	    SeleniumUtil.waitForPageToLoad(20000);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("TransactionDetails.site16441.1", "site16441.1");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(details.transactionHeader().getText().trim(), PropsUtil.getDataPropertyValue("TransactionHeader"), "Transaction header should be Transactions");
	}
}
