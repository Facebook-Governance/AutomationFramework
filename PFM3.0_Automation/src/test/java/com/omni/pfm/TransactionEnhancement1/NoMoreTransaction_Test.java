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


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.NoMoreTransaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class NoMoreTransaction_Test extends TestBase
{
	NoMoreTransaction_Loc NoMoreTransac;
	public static String userName="";
	LoginPage login;
	
	@BeforeClass(alwaysRun = true)

	public void init() throws InterruptedException {

		doInitialization("Dashboard");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		 login=new LoginPage();
		NoMoreTransac = new NoMoreTransaction_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}
	@Test(description = "", priority = 1, groups = { "Smoke" })

	public void login() throws Exception {
		if ((PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE") || PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("PAD")) && PropsUtil.getEnvPropertyValue("APPORBROWSER").equalsIgnoreCase("APP") )

		{

		}
	    else

		{
	    	LoginPage.loginMain(d, loginParameter);
			 SeleniumUtil.waitForPageToLoad(5000);
		    PageParser.forceNavigate("Transaction", d);
		    SeleniumUtil.waitForPageToLoad(10000);

		}

	}
	

@Test(description = "", priority = 2)
	public void verifyNoMoreTransactiontextdisplayed()
	{
	while (NoMoreTransac.LoadMoreButton().isDisplayed())
	{
		SeleniumUtil.click(NoMoreTransac.LoadMoreButton());
		SeleniumUtil.waitForPageToLoad(4000);
	}
	
	Assert.assertEquals(NoMoreTransac.NoMoreTransactionText().getText(),
			PropsUtil.getDataPropertyValue("NoMoreTransactionText"));
	}
@Test(description = "AT-58312:TRA_06_02:Verify message shown should be No more transactions ", priority = 3)
public void verifyNoMoreTransactiontext()
{
	Assert.assertEquals(NoMoreTransac.NoMoreTransactionText().getText(),
			PropsUtil.getDataPropertyValue("NoMoreTransactionText"));
}
@Test(description = "", priority = 4)
public void verifyNMTFiltercombination()
{
	//SeleniumUtil.click(NoMoreTransac.AcctDropdown());
	//SeleniumUtil.click(NoMoreTransac.AllAccounts());
	
	//SeleniumUtil.click(NoMoreTransac.SelectAcct());
	SeleniumUtil.click(NoMoreTransac.moveToTop());
	SeleniumUtil.waitForPageToLoad();
	
	/*SeleniumUtil.click(NoMoreTransac.TimeFilter());
	SeleniumUtil.click(NoMoreTransac.ThisMonth());*/
	SeleniumUtil.waitForPageToLoad();
	SeleniumUtil.click(NoMoreTransac.Categorydropdown());
	SeleniumUtil.click(NoMoreTransac.Allcategory());
	SeleniumUtil.click(NoMoreTransac.Selectcategory());
	SeleniumUtil.waitForPageToLoad();
	
	Assert.assertEquals(NoMoreTransac.NoMoreTransactionText().getText(),
			PropsUtil.getDataPropertyValue("NoMoreTransactionText"));
}
@Test(description = "", priority = 5)
public void verifyNMTwithoutLoadButton()
{
	
	Assert.assertEquals(NoMoreTransac.NoMoreTransactionText().getText(),
			PropsUtil.getDataPropertyValue("NoMoreTransactionText"));
}
@Test(description = "", priority = 6)
public void verifyNMTAfterNavigation()
{
	SeleniumUtil.click(NoMoreTransac.Dashboard());
	SeleniumUtil.waitForPageToLoad();
	  SeleniumUtil.click(NoMoreTransac.TransactionCard());
	  SeleniumUtil.waitForPageToLoad(5000);
	  
	  while (NoMoreTransac.LoadMoreButton().isDisplayed())
		{
			SeleniumUtil.click(NoMoreTransac.LoadMoreButton());
			SeleniumUtil.waitForPageToLoad(4000);
		}
	Assert.assertEquals(NoMoreTransac.NoMoreTransactionText().getText(),
			PropsUtil.getDataPropertyValue("NoMoreTransactionText"));
}

@Test(description = "", priority = 7)
public void verifyNMTNodataScreen()

{
	SeleniumUtil.click(NoMoreTransac.Categorydropdown());
	SeleniumUtil.click(NoMoreTransac.Allcategory());
	Assert.assertFalse(NoMoreTransac.NoMoreTransactionText().isDisplayed());
			
}

}