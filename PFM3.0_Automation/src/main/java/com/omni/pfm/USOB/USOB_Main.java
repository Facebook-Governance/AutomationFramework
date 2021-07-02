/*******************************************************************************
 * Copyright (c) 2020 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author kongara.sravan
 ******************************************************************************/
package com.omni.pfm.USOB;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class USOB_Main extends SeleniumUtil {
	Logger logger = LoggerFactory.getLogger(USOB_Main.class);
	public final static By obMigrationBanner = getByObject("DashboardPage", null, "OB_MigrationBanner");
	public final static By finAppHeader = getByObject("ManageSharing", null, "finappHeader");
	public static final String xpathForOBMigrationIconInAccountsPage = "//*[@autoid=\"accounts_lbl_ContainerName\" and contains(text(),\"#####\")]/parent::div/following-sibling::div[contains(@class,\"updateInfo\")]//*[@autoid=\"ob-migration-site-icon\"]";
	public static final By sideBarsValuesInAccountSettingsPage = By.xpath("//*[@autoid=\"sidebar_lbl_Values\"]");
	public static final By errorContainer = getByObject("AccountSettings", null, "errorContainerForAccounts");
	//
	WebDriver driver;

	public USOB_Main(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Verifies whether migration banner is displayed or not in the dashboard page
	 * 
	 * @return
	 */
	public boolean isMigrationBannerDisplayedInDashboard() {
		if (SeleniumUtil.isDisplayed(obMigrationBanner, 5)) {
			logger.info("OB migration banner id displayed");
			return true;
		} else {
			return false;
		}
	}

	public void clickOnOBMigrationBanner() {
		logger.info("Clicking on the OB migration banner in the dashboard");
		click(obMigrationBanner);
		SeleniumUtil.waitForPageToLoad();
	}

	public String returnTheTextDisplayedOnOBMigartionBanner() {
		waitUntilElementIsVisible(obMigrationBanner, 60);
		String textDisplayedOnOBMigrationBanner = driver.findElement(obMigrationBanner).getText();
		logger.info("Text displayed in the banner is  :: " + textDisplayedOnOBMigrationBanner);
		return textDisplayedOnOBMigrationBanner;
	}

	public String returnFinappHeader() {
		String finAppHeaderText = "";
		waitForPageToLoad();
		waitUntilElementIsVisible(finAppHeader, 60);
		finAppHeaderText = driver.findElement(finAppHeader).getText();
		logger.info("Finapp header :: " + finAppHeaderText);
		return finAppHeaderText;
	}

	public void clickOnGivenAccountInAccountSettingsPage(String accName) {
		waitForPageToLoad();
		waitUntilElementIsVisible(sideBarsValuesInAccountSettingsPage, 30);
		for (WebElement acc : driver.findElements(sideBarsValuesInAccountSettingsPage)) {
			if (acc.getText().trim().toLowerCase().equals(accName.toLowerCase())) {
				if ((acc.getAttribute("aria-selected") == null) || acc.getAttribute("aria-selected").equals("false"))
					click(acc);
				break;
			}
		}
	}

	public boolean isErrorContainerDisplayed() {
		waitForPageToLoad();
		waitFor(2);
		if (SeleniumUtil.isDisplayed(errorContainer, 10)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOBMigrationIconDisplayedForGivenAccountInAccountsPage(String accountName) {
		waitForPageToLoad();
		return SeleniumUtil.isDisplayed(By.xpath(xpathForOBMigrationIconInAccountsPage.replace("#####", accountName)),
				5);
	}

	public void clickOnOBMigrationIconForGivenAccount(String accountName) {
		waitForPageToLoad();
		click(By.xpath(xpathForOBMigrationIconInAccountsPage.replace("#####", accountName)));
	}

	public String returnMessageDisplayedInErrorContainerOfMigrationPopup() {
		waitUntilElementIsVisible(errorContainer, 60);
		return driver.findElement(errorContainer).getText();
	}

	public void clickOnOBMigrationIconInAccountSettingsPage() {
		click(By.xpath("//*[@autoid=\"ob-migration-site-icon\"]"));
	}
}
