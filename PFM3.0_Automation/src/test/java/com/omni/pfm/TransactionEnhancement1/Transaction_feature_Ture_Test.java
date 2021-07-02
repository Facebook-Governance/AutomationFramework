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

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.DownloadTrans_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_feature_Ture_Test extends TestBase {
	AddToCalendar_Transaction_Loc AddToCalendar;
	DownloadTrans_Loc download;
	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Add Manual Transaction");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		download = new DownloadTrans_Loc(d);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68241:verify feature ture icon", priority = 2)
	public void verifyFeatureTourIcon() throws InterruptedException {
		SeleniumUtil.click(download.morebutton());
		Assert.assertTrue(download.FeatureTourIcon().isDisplayed());
	}

	@Test(description = "verify feature ture link", priority = 3, dependsOnMethods = { "verifyFeatureTourIcon" })
	public void verifyFeatureTourLabel() throws InterruptedException {
		String featuretour = download.FeatureTourLabel().getText();
		Assert.assertEquals(featuretour, PropsUtil.getDataPropertyValue("Feature_Tour_Label"));
	}

	@Test(description = "AT-68242:verify filter coach mark header", priority = 4, dependsOnMethods = {
			"verifyFeatureTourIcon" })
	public void verifyFilterCoachmarkHeader() throws InterruptedException {
		SeleniumUtil.click(download.FeatureTourIcon());
		String header = download.CoachmarkHeader(1).getText();
		Assert.assertTrue(header.contains(PropsUtil.getDataPropertyValue("Filter_Header")));
	}

	@Test(description = "verify filter coach mark close icon", priority = 5, dependsOnMethods = {
			"verifyFeatureTourIcon" })
	public void verifyFilterCoachmarkCloseIcon() throws InterruptedException {
		Assert.assertTrue(download.closeIcon(1).isDisplayed());
	}

	@Test(description = "verify filter coach mark description", priority = 6, dependsOnMethods = {
			"verifyFeatureTourIcon" })
	public void verifyFilterCoachmarkDescription() throws InterruptedException {
		String description = download.CoachmarkDescription(1).getText();
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Filter_Description"));
	}

	@Test(description = "verify filter coach mark next button", priority = 7, dependsOnMethods = {
			"verifyFeatureTourIcon" })
	public void verifyFilterCoachmarknextbutton() throws InterruptedException {
		Assert.assertTrue(download.NextButton(1, 1).isDisplayed());
	}

	@Test(description = "verify filter coach mark should closed when click on close icon", priority = 8, dependsOnMethods = {
			"verifyFeatureTourIcon" })
	public void verifyFilterCoachmarkCloseclick() throws InterruptedException {
		SeleniumUtil.click(download.closeIcon(1));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(By.xpath(download.xpathForCloseIcon.replace("**", 1 + "")), 2));
	}

	@Test(description = "AT-68243:verify transaction row coach mark", priority = 9)
	public void verifyTransactionRowCoachmarkHeader() throws InterruptedException {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(download.morebutton());
		SeleniumUtil.click(download.FeatureTourIcon());
		SeleniumUtil.click(download.NextButton(1, 1));
		String header = download.CoachmarkHeader(2).getText();
		Assert.assertEquals(header, PropsUtil.getDataPropertyValue("Transaction_Row_Header"));
	}

	@Test(description = "verify transaction row coach description", priority = 10, dependsOnMethods = {
			"verifyTransactionRowCoachmarkHeader" })
	public void verifyTransactionRowCoachmarkDescription() throws InterruptedException {
		String description = download.CoachmarkDescription(2).getText();
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Transaction_Row_Description"));
	}

	@Test(description = "verify transaction row coach mark close icon", priority = 11, dependsOnMethods = {
			"verifyTransactionRowCoachmarkHeader" })
	public void verifyTransactionRowCoachmarkCloseIcon() throws InterruptedException {
		Assert.assertTrue(download.closeIcon(2).isDisplayed());
	}

	@Test(description = "verify transaction row coach mark back button", priority = 12, dependsOnMethods = {
			"verifyTransactionRowCoachmarkHeader" })
	public void verifyTransactionRowCoachmarkBackButton() throws InterruptedException {
		Assert.assertTrue(download.NextButton(2, 1).isDisplayed());
	}

	@Test(description = "verify transaction row coach mark next button", priority = 13, dependsOnMethods = {
			"verifyTransactionRowCoachmarkHeader" })
	public void verifyTransactionRowCoachmarkNextButton() throws InterruptedException {
		Assert.assertTrue(download.NextButton(2, 2).isDisplayed());
	}

	@Test(description = "verify filter coach mark when click back on transaction row coach mark", priority = 14, dependsOnMethods = {
			"verifyTransactionRowCoachmarkHeader" })
	public void verifyTransactionRowCoachmarkBackButtonClick() throws InterruptedException {
		SeleniumUtil.click(download.NextButton(2, 1));
		String description = download.CoachmarkDescription(1).getText();
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Filter_Description"));
	}

	@Test(description = "verify Transaction row  coach mark should closed when click close on transaction row coach mark", groups = {
			"DesktopBrowsers" }, priority = 15, dependsOnMethods = { "verifyTransactionRowCoachmarkBackButtonClick" })
	public void verifyTransactionRowCoachmarkCloseButtonClick() throws InterruptedException {
		SeleniumUtil.click(download.NextButton(1, 1));
		SeleniumUtil.click(download.closeIcon(2));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(By.xpath(download.xpathForCloseIcon.replace("**", 2 + "")), 2));
	}

	@Test(description = "AT-68244:verify manage category coach mark", priority = 16)
	public void verifyManageCategoriesCoachmarkHeader() throws InterruptedException {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(download.morebutton());
		SeleniumUtil.click(download.FeatureTourIcon());
		SeleniumUtil.click(download.NextButton(1, 1));
		SeleniumUtil.click(download.NextButton(2, 2));
		String header3 = download.CoachmarkHeader(3).getText();
		Assert.assertEquals(header3, PropsUtil.getDataPropertyValue("Manage_Categories_Header"));
	}

	@Test(description = "verify manage category coach mark description", priority = 17, dependsOnMethods = {
			"verifyManageCategoriesCoachmarkHeader" })
	public void verifyManageCategoriesDescription() throws InterruptedException {
		String description = download.CoachmarkDescription(3).getText();
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Manage_Categories_Description"));
	}

	@Test(description = "verify manage category coach mark close icon", priority = 18, dependsOnMethods = {
			"verifyManageCategoriesCoachmarkHeader" })
	public void verifyManageCategoriesCloseIcon() throws InterruptedException {
		Assert.assertTrue(download.closeIcon(3).isDisplayed());
	}

	@Test(description = "verify manage category coach mark back button", priority = 19, dependsOnMethods = {
			"verifyManageCategoriesCoachmarkHeader" })
	public void verifyManageCategoriesBackButton() throws InterruptedException {
		Assert.assertTrue(download.NextButton(3, 1).isDisplayed());
	}

	@Test(description = "verify manage category coach mark next button", priority = 20, dependsOnMethods = {
			"verifyManageCategoriesCoachmarkHeader" })
	public void verifyManageCategoriesNextButton() throws InterruptedException {
		Assert.assertTrue(download.NextButton(3, 2).isDisplayed());
	}

	@Test(description = "verify transaction row coach mark when click on back button in manage category coach mark", priority = 21, dependsOnMethods = {
			"verifyManageCategoriesCoachmarkHeader" })
	public void verifyManageCategoriesBackButtonClick() throws InterruptedException {
		SeleniumUtil.click(download.NextButton(3, 1));
		String description = download.CoachmarkDescription(2).getText();
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Transaction_Row_Description"));
	}

	@Test(description = "verify manage category coach mark should closed when click on close button in manage category coach mark", groups = {
			"DesktopBrowsers" }, priority = 22, dependsOnMethods = { "verifyManageCategoriesBackButtonClick" })
	public void verifyManageCategoriesCloseButtonClick() throws InterruptedException {
		SeleniumUtil.click(download.NextButton(2, 2));
		SeleniumUtil.click(download.closeIcon(3));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(By.xpath(download.xpathForCloseIcon.replace("**", 3 + "")), 2));
	}

	@Test(description = "verify coach mark should closed when click on goto button", groups = {
			"DesktopBrowsers" }, priority = 23)
	public void verifyNextButton() throws InterruptedException {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(download.morebutton());
		SeleniumUtil.click(download.FeatureTourIcon());
		SeleniumUtil.click(download.NextButton(1, 1));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(download.NextButton(2, 2));
		SeleniumUtil.click(download.NextButton(3, 2));
		Assert.assertTrue(!SeleniumUtil.isDisplayed(
				By.xpath(download.xpathForNextButtonInList.replace("##", 3 + "").replace("**", 2 + "")), 2));
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {
			logger.info(e+"");
		}
	}
}
