/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Ok2Spend;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Ok2Spend.SFC_LandingPage_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFC_Onboarding_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(SFC_Onboarding_Test.class);
	public static String userName = "";

	SFC_LandingPage_Loc landing;

	Add_Manual_Transaction_Loc manual;
	String BillAccountName = null;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass
	public void testInit() throws InterruptedException, AWTException {
		doInitialization("SFC Login");
		TestBase.tc = TestBase.extent.startTest("SFC Onboarding", "Login");
		login = new LoginPage();
		accountAdd = new AccountAddition();

		landing = new SFC_LandingPage_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);

	}

	@Test(description = "Login", priority = 0)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("SFCCard1.site16441.2", "site16441.2");
		PageParser.forceNavigate("FinancialForecast", d);
	}

	@Test(description = "Verify that by default checking account and credit card accounts are checked", priority = 1)
	public void VerifycardAndCheckingAccountChecked() {
		SeleniumUtil.click(landing.SFCStartButton());
		SeleniumUtil.waitForPageToLoad(3000);

		for (int i = 0; i < landing.SFCAccountRow().size(); i++) {
			String sfcAccountrow = landing.SFCAccountRow().get(i).getText();
			if (sfcAccountrow.equals(PropsUtil.getDataPropertyValue("SFCaccountList1"))
					|| sfcAccountrow.equals(PropsUtil.getDataPropertyValue("SFCaccountList12"))
					|| sfcAccountrow.equals(PropsUtil.getDataPropertyValue("SFCaccountList13"))) {
				Assert.assertEquals(landing.SFCAccountTypeRadio().get(i).getAttribute("aria-checked"),
						PropsUtil.getDataPropertyValue("SFCRadioTrue"),
						"Card or checking account is not checked or selected");
			}
		}
	}

	@Test(description = "Verify that by default any account other than checking account in bank are unchecked", priority = 2)
	public void VerifyOtherAccountUnChecked() {
		for (int i = 0; i < landing.SFCAccountRow().size(); i++) {
			if (landing.SFCAccountRow().get(i).getText().equals(PropsUtil.getDataPropertyValue("SFCaccountList14"))) {
				Assert.assertEquals(landing.SFCAccountTypeRadio().get(i).getAttribute("aria-checked"),
						PropsUtil.getDataPropertyValue("SFCRadioFalse"), "Other accounts are selected by default.");
			}
		}
	}

	@Test(description = "Verify that any account other than bank or card account is not displayed in the verify accounts screen", priority = 3)
	public void VerifySupportedAccountType() {
		for (int i = 0; i < landing.SFCAccountRow().size(); i++) {
			if (landing.SFCAccountRow().get(i).getText().equals(PropsUtil.getDataPropertyValue("SFCaccountList15"))) {
				Assert.assertTrue(true);
			}
		}
	}

	@Test(description = "Verify that on adding new manual transaction in the verify events screen, the transaction is reflected in the same screen", priority = 4)
	public void VerifyAddManualTran() {
		for (int i = 0; i < landing.SFCAccountTypeRadio().size(); i++) {
			if (landing.SFCAccountTypeRadio().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("SFCRadioFalse"))) {
				SeleniumUtil.click(landing.SFCAccountTypeRadio().get(i));

			}
		}
		SeleniumUtil.click(landing.SFCNextButton());
		SeleniumUtil.click(landing.SFCIncomeAddManuIcon());
		manual.createTransactionWithRecurSFC(PropsUtil.getDataPropertyValue("SFCManualAmount"),
				PropsUtil.getDataPropertyValue("SFCManualDesc"), 1, 1, 1, 0, 30, 1, 1,
				PropsUtil.getDataPropertyValue("SFCManualNote"));
		String Description = null;
		String accname = null;
		String freq = null;
		SeleniumUtil.waitForPageToLoad(5000);
		for (int i = 0; i < landing.SFCBillingAccount().size(); i++) {
			System.out.println(PropsUtil.getDataPropertyValue("SFCManualAmount") + ".00");
			System.out.println(landing.SFCBillingAmount().get(i).getText().replaceAll("[,]", ""));
			if (landing.SFCBillingAmount().get(i).getText().replaceAll("[,]", "")
					.equals("$" + PropsUtil.getDataPropertyValue("SFCManualAmount") + ".00")) {
				Description = landing.SFCBillingDescription().get(i).getText();
				accname = landing.SFCBillingAccount().get(i).getText();
				freq = landing.SFCBillingFrequency().get(i).getText();
				break;
			}
		}

		Assert.assertEquals(Description, PropsUtil.getDataPropertyValue("SFCManualDesc"));
		Assert.assertEquals(accname, PropsUtil.getDataPropertyValue("SFCAccountName"));
		Assert.assertEquals(freq, PropsUtil.getDataPropertyValue("SFCFrequency"));
	}

	@Test(description = "Verify that by default opt-out transactions are checked and opt-in transactions are unchecked", priority = 5)
	public void VerifyOptOutCheckBox() {
		for (int i = 0; i < landing.SFCOptOutCheckBox().size(); i++) {
			if (!landing.SFCOptOutCheckBox().get(i).getAttribute("aria-checked")
					.equals(PropsUtil.getDataPropertyValue("SFCRadioTrue"))) {
				Assert.assertTrue(false);
			}

		}

	}

	@Test(description = "Verify that by default opt-out transactions are checked and opt-in transactions are unchecked", priority = 6)
	public void VerifyOptInCheckBox() {
		for (int i = 0; i < landing.SFCOptInCheckBox().size(); i++) {
			if (!landing.SFCOptInCheckBox().get(i).getAttribute("aria-checked")
					.equals(PropsUtil.getDataPropertyValue("SFCRadioFalse"))) {
				Assert.assertTrue(false);
			}

		}

	}

	@Test(description = "Verify that by default opt-out transactions are checked and opt-in transactions are unchecked", priority = 7)
	public void VerifyEditTransaction() {
		SeleniumUtil.click(landing.SFCBillingDescription().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		manual.EditTransactionWithRecurSFC(PropsUtil.getDataPropertyValue("SFCManualAmount2"),
				PropsUtil.getDataPropertyValue("SFCManualDesc2"), 1, 1, 1, 2, 9, 2, 1,
				PropsUtil.getDataPropertyValue("SFCManualNote2"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		String Description = null;
		String freq = null;
		for (int i = 0; i < landing.SFCBillingAccount().size(); i++) {
			System.out.println("$" + PropsUtil.getDataPropertyValue("SFCManualAmount2") + ".00");
			System.out.println(landing.SFCBillingAmount().get(i).getText());
			if (landing.SFCBillingAmount().get(i).getText().replace(",", "")
					.equals("$" + PropsUtil.getDataPropertyValue("SFCManualAmount2") + ".00")) {
				Description = landing.SFCBillingDescription().get(i).getText();
				freq = landing.SFCBillingFrequency().get(i).getText();
				break;
			}
		}

		Assert.assertEquals(Description, PropsUtil.getDataPropertyValue("SFCManualDesc2"));

		Assert.assertEquals(freq, PropsUtil.getDataPropertyValue("SFCFrequency"));
	}

	@Test(description = "Verify that by default opt-out transactions are checked and opt-in transactions are unchecked", priority = 8)
	public void VerifyDeleteTransaction() {
		SeleniumUtil.click(landing.SFCBillingDescription().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manual.SFCshowMoreDetails());
		SeleniumUtil.waitForPageToLoad(1000);

		SeleniumUtil.click(landing.SFCTraDelete());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(landing.SFCDeletepopUpButton());
		SeleniumUtil.waitForPageToLoad(5000);
		for (int i = 0; i < landing.SFCBillingAccount().size(); i++) {
			if (landing.SFCBillingDescription().get(i).getText()
					.contains(PropsUtil.getDataPropertyValue("SFCManualDesc"))) {
				Assert.assertTrue(true);
			}
		}
	}

	@Test(description = "Verify the accounts displayed in the dropdown in the verify cc payment screen", priority = 9)
	public void VerifyVerifyAccountDropdown() {
		SeleniumUtil.waitFor(2);
		SeleniumUtil.click(landing.SFCNextButton());
		SeleniumUtil.click(landing.SFCTraAccount());
		List<String> expected = Arrays.asList(PropsUtil.getDataPropertyValue("SFCDropDowwnAcc").split(","));
		List<String> actual = SeleniumUtil.getWebElementsValue(landing.SFCTraAccountDropDowbvalue());
		Collections.sort(actual);
		Collections.sort(expected);
		SeleniumUtil.assertExpectedActualList(actual, expected, "Bank accounts are not matching");
	}

	@Test(description = "Verify the accounts displayed in the dropdown in the verify cc payment screen", priority = 10)
	public void SelectAPymentAmountAsOther() {
		BillAccountName = landing.SFCTraAccount().getText();
		SeleniumUtil.click(landing.SFCOtherRadio());
		landing.SFCOtherField().sendKeys(PropsUtil.getDataPropertyValue("SFCOtherAmpunt"));
		SeleniumUtil.click(landing.SFCNextButton());
		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(landing.SFCOtherRadio());
		landing.SFCOtherField().sendKeys(PropsUtil.getDataPropertyValue("SFCOtherAmpunt2"));
		SeleniumUtil.click(landing.SFCNextButton());
		Assert.assertEquals(landing.SFCCreatedHeader().getText(), PropsUtil.getDataPropertyValue("SFCCreatedHeader"));

	}

}
