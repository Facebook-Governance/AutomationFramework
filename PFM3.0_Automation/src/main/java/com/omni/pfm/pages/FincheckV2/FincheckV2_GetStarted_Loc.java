/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.FincheckV2;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_GetStarted_Loc {
	WebDriver d;
	String pageName, frameName;
	List<WebElement> deleteSiteLink;
	AccountAddition accountAddition;
	int accountCount, deleteCount;

	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_GetStarted_Loc.class);

	public FincheckV2_GetStarted_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
		accountAddition = new AccountAddition();
	}

	public WebElement getDashboardHeader() {
		return SeleniumUtil.getWebElement(d, "finV2_dashboard_header", pageName, frameName);
	}

	public WebElement getDeletePopupHeader() {
		return SeleniumUtil.getWebElement(d, "delete_site_popup_header", pageName, frameName);
	}

	public List<WebElement> getDeleteSileLinks() {
		return SeleniumUtil.getWebElements(d, "delete_site_link_AccSettings", pageName, frameName);
	}

	public WebElement getConfirmDeleteCheckBox() {
		return SeleniumUtil.getWebElement(d, "confirm_delete_checkbox", pageName, frameName);
	}

	public WebElement getDleteButton() {
		return SeleniumUtil.getWebElement(d, "delete_site_button", pageName, frameName);
	}

	public WebElement getToastMsg() {
		return SeleniumUtil.waitForElementVisible(d, "toast_msg_container", pageName, frameName);
	}

	public List<WebElement> getAccountCount() {
		return SeleniumUtil.getWebElements(d, "accounts_count", pageName, frameName);
	}

	public WebElement getNoDataScreenHeader() {
		return SeleniumUtil.getWebElement(d, "no_data_screen_header", pageName, frameName);
	}

	/**
	 * Common method to delete the existing account and add a new account
	 * 
	 * @param siteName, sitePassword
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void deleteAllAccountsAndAddNewAccount(String siteName, String sitePassword)
			throws AWTException, InterruptedException {
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> Entering the method to delete the existing account.");
		accountCount = getAccountCount().size();

		logger.info(">>>>> Deleteing all the accounts present");
		for (int aCount = 0; aCount < accountCount; aCount++) {
			deleteCount = getDeleteSileLinks().size();
			deleteSiteLink = getDeleteSileLinks();
			for (int iCount = 0; iCount < deleteCount; iCount++) {
				logger.info(">>>>> verifying delete site Link");
				Assert.assertTrue(deleteSiteLink.get(iCount).isDisplayed(), "No delete account link displayed.");
				logger.info(">>>>>Deleting the account: " + (aCount + 1));
				SeleniumUtil.click(deleteSiteLink.get(iCount));
				SeleniumUtil.waitForPageToLoad(3000);

				logger.info(">>>> Verifying the popup header..");
				Assert.assertTrue(getDeletePopupHeader().isDisplayed(), "Delete Site Popup not displayed.");
				logger.info(">>>>> Clicking on confirm checkbox");
				SeleniumUtil.click(getConfirmDeleteCheckBox());
				SeleniumUtil.waitForPageToLoad(2000);
				logger.info(">>>>> Clikcing on delete button.");
				SeleniumUtil.click(getDleteButton());
				SeleniumUtil.waitForPageToLoad(10000);

			}

		}

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(7000);

		if (siteName != null && sitePassword != null) {
			Assert.assertTrue(getNoDataScreenHeader().isDisplayed(), "No Data Screen not displayed.");
			logger.info(">>>>> All accounts deleted.");

			logger.info(">>>>> Adding the new account");
			accountAddition.linkAccount();
			SeleniumUtil.waitForPageToLoad();

			accountAddition.addAggregatedAccount(siteName, sitePassword);
			SeleniumUtil.waitForPageToLoad();

			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad();
		}

	}

	/**
	 * Fincheck Onboarding changes - New FTUE screens
	 * 
	 * @author sbhat1
	 */

	public WebElement getGetStartedOnboarding() {
		return SeleniumUtil.getWebElement(d, "finV2-get-started-btn", pageName, frameName);
	}

	public WebElement getContinueButtonStep1() {
		return SeleniumUtil.getWebElement(d, "finV2-continue-btn-step1", pageName, frameName);
	}

	public WebElement getYOBDropdown() {
		return SeleniumUtil.getWebElement(d, "finV2-year-of-birth-dd", pageName, frameName);
	}

	public void selectYearOfBirth(String yearOfBirth) {
		String year = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "finV2-year-of-birth-val");
		year = year.replaceAll("dynamicVal", yearOfBirth);
		WebElement birthYear = d.findElement(By.xpath(year));
		SeleniumUtil.click(birthYear);
	}

	public WebElement getAnualHouseholdTextbox() {
		return SeleniumUtil.getWebElement(d, "finV2-anual-income-textbox", pageName, frameName);
	}

	public WebElement getCreditScoreDD() {
		return SeleniumUtil.getWebElement(d, "finV2-credit-score-dd", pageName, frameName);
	}

	public void selectCreditScore(int creditScore) {
		String score = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "finV2-credit-score-val");
		score = score.replaceAll("dynamicVal", Integer.toString(creditScore));
		WebElement credit = d.findElement(By.xpath(score));
		SeleniumUtil.click(credit);
	}

	public WebElement getNextButton() {
		return SeleniumUtil.getWebElement(d, "finV2-next-btn", pageName, frameName);
	}

	public List<WebElement> getPlannings() {
		return SeleniumUtil.getWebElements(d, "finV2-plans-radio", pageName, frameName);
	}

	public WebElement getFinishButton() {
		return SeleniumUtil.getWebElement(d, "finV2-finish-button", pageName, frameName);
	}

	public List<WebElement> getHabits() {
		return SeleniumUtil.getWebElements(d, "finV2-planning-habits-radio", pageName, frameName);
	}

	public WebElement getOkLetsGoButton() {
		return SeleniumUtil.getWebElement(d, "finV2-ok-lets-go-button", pageName, frameName);
	}

	public List<WebElement> insuranceChkbox() {
		return SeleniumUtil.getWebElements(d, "finV2-insurance-chkbox", pageName, frameName);
	}

	/**
	 * Common method to do a quick onboarding
	 * 
	 * @param yearOfBirth
	 * @param annualIncome
	 * @param creditScore
	 * @param plan
	 */
	public void quickOnboarding(String yearOfBirth, String annualIncome, int creditScore, int plan) {
		try {
			SeleniumUtil.refresh(d);
			SeleniumUtil.refresh(d);
			SeleniumUtil.refresh(d);
			PageParser.forceNavigate("FincheckV2", d);
			SeleniumUtil.waitForPageToLoad();

			logger.info("Entering Quick FTUE");
			logger.info(">>>>> Clicking on Get Started button");
			SeleniumUtil.click(getGetStartedOnboarding());
			SeleniumUtil.waitForPageToLoad(3000);

			logger.info(">>>>> Clicking on continue in step 1");
			SeleniumUtil.click(getContinueButtonStep1());
			SeleniumUtil.waitForPageToLoad(3000);

			// Selecting the year of birth
			SeleniumUtil.click(getYOBDropdown());
			SeleniumUtil.waitForPageToLoad(2000);
			selectYearOfBirth(yearOfBirth);
			SeleniumUtil.waitForPageToLoad(3000);

			// Entering anual income
			SeleniumUtil.click(getAnualHouseholdTextbox());
			getAnualHouseholdTextbox().sendKeys(annualIncome);

			// Credit score section
			SeleniumUtil.click(getCreditScoreDD());
			selectCreditScore(2);
			SeleniumUtil.click(getNextButton());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.waitFor(2);
			// Insurance section - default values
			SeleniumUtil.click(getNextButton());
			SeleniumUtil.waitForPageToLoad();

			if (plan == 0 || plan == 1 || plan == 2) {
				SeleniumUtil.click(getPlannings().get(plan));
				SeleniumUtil.click(getNextButton());
				SeleniumUtil.waitForPageToLoad();

				for (int hab = 0; hab < 5; hab++) {
					SeleniumUtil.click(getHabits().get(hab));
					SeleniumUtil.waitForPageToLoad(1000);

				}
				SeleniumUtil.waitFor(2);
				SeleniumUtil.click(getFinishButton());

			} else {
				SeleniumUtil.waitFor(2);
				SeleniumUtil.click(getPlannings().get(plan));
				SeleniumUtil.waitFor(1);
				SeleniumUtil.click(getFinishButton());
			}
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.waitFor(5);
			SeleniumUtil.click(getOkLetsGoButton());
		} catch (AssertionError | Exception e) {
			Assert.fail("Unable to on-bard fincheck due to  :: " + e.getMessage());
		}

	}

	public void quickOnboarding_WithInsurance(String yearOfBirth, String annualIncome, int creditScore, int plan) {
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Entering Quick FTUE");
		logger.info(">>>>> Clicking on Get Started button");
		SeleniumUtil.click(getGetStartedOnboarding());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info(">>>>> Clicking on continue in step 1");
		SeleniumUtil.click(getContinueButtonStep1());
		SeleniumUtil.waitForPageToLoad(3000);

		// Selecting the year of birth
		SeleniumUtil.click(getYOBDropdown());
		SeleniumUtil.waitForPageToLoad(2000);
		selectYearOfBirth(yearOfBirth);
		SeleniumUtil.waitForPageToLoad(3000);

		// Entering anual income
		SeleniumUtil.click(getAnualHouseholdTextbox());
		getAnualHouseholdTextbox().sendKeys(annualIncome);

		// Credit score section
		SeleniumUtil.click(getCreditScoreDD());
		selectCreditScore(2);
		SeleniumUtil.click(getNextButton());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(insuranceChkbox().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(insuranceChkbox().get(1));
		SeleniumUtil.waitForPageToLoad();

		// Insurance section - default values
		SeleniumUtil.click(getNextButton());
		SeleniumUtil.waitForPageToLoad();

		if (plan == 0 || plan == 1 || plan == 2) {
			SeleniumUtil.click(getPlannings().get(plan));
			SeleniumUtil.click(getNextButton());
			SeleniumUtil.waitForPageToLoad(2000);

			for (int hab = 0; hab < 5; hab++) {
				SeleniumUtil.click(getHabits().get(hab));
				SeleniumUtil.waitForPageToLoad(1000);

			}
			SeleniumUtil.click(getFinishButton());

		} else {
			SeleniumUtil.click(getPlannings().get(plan));
			SeleniumUtil.click(getFinishButton());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(getOkLetsGoButton());
	}

}
