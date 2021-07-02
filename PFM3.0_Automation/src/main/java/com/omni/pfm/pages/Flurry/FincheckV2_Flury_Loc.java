package com.omni.pfm.pages.Flurry;
/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.utility.FlurryUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.utility.WaitUtil;

public class FincheckV2_Flury_Loc {
	String pageName, frameName;
	WebDriver d;
	static WebDriverWait wait;

	public FincheckV2_Flury_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
		wait = new WebDriverWait(d, 10);
	}

	public WebElement getWelcomeHeader() {
		return WaitUtil.getClickableWebElement(d, "fincheck-v2-welcome-header-text", pageName, frameName);
	}

	public WebElement getStartedButton() {
		return WaitUtil.getClickableWebElement(d, "finV2-get-started-btn", pageName, frameName);
	}

	public WebElement getClearAllButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-clear-all-button", pageName, frameName);
	}

	public WebElement getStep1Desc() {
		return WaitUtil.getVisibleWebElement(d, "flurry-step1-description", pageName, frameName);
	}

	public WebElement getLinkMoreButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-link-account", pageName, frameName);
	}

	public WebElement getLinkAccountTabs() {
		return WaitUtil.getVisibleWebElement(d, "flurry-link-acc-steps", pageName, frameName);
	}

	public WebElement getLinkMoreCloseButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-link-acc-close-button", pageName, frameName);
	}

	public WebElement getStep1ContinueButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-step1-continue", pageName, frameName);
	}

	public WebElement getYOBDropdown() {
		return WaitUtil.getClickableWebElement(d, "finV2-year-of-birth-dd", pageName, frameName);
	}

	public void selectYearOfBirth(String yearOfBirth) {
		String year = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "finV2-year-of-birth-val");
		year = year.replaceAll("dynamicVal", yearOfBirth);
		WebElement birthYear = d.findElement(By.xpath(year));
		SeleniumUtil.click(birthYear);
	}

	public WebElement getAnualHouseholdTextbox() {
		return WaitUtil.getClickableWebElement(d, "finV2-anual-income-textbox", pageName, frameName);
	}

	public WebElement getCreditScoreDD() {
		return WaitUtil.getClickableWebElement(d, "finV2-credit-score-dd", pageName, frameName);
	}

	public void selectCreditScore(int creditScore) {
		String score = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "finV2-credit-score-val");
		score = score.replaceAll("dynamicVal", Integer.toString(creditScore));
		WebElement credit = d.findElement(By.xpath(score));
		SeleniumUtil.click(credit);
	}

	public WebElement getBackButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-step2-back", pageName, frameName);
	}

	public WebElement getStep2NextButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-step2-next", pageName, frameName);
	}

	public WebElement getStep3Desc() {
		return WaitUtil.getVisibleWebElement(d, "flurry-step3-desc", pageName, frameName);
	}

	public WebElement getToggle1() {
		return WaitUtil.getClickableWebElement(d, "flurry-toggle-1", pageName, frameName);
	}

	public WebElement getRadio2() {
		return WaitUtil.getClickableWebElement(d, "flurry-radio-2", pageName, frameName);
	}

	public WebElement getSecondPlan() {
		return WaitUtil.getClickableWebElement(d, "flurry-second-plan", pageName, frameName);
	}

	public WebElement getStep4Desc() {
		return WaitUtil.getVisibleWebElement(d, "flurry-step4-desc", pageName, frameName);
	}

	public WebElement getStep5Desc() {
		return WaitUtil.getVisibleWebElement(d, "flurry-step5-desc", pageName, frameName);
	}

	public WebElement getHabit1() {
		return WaitUtil.getClickableWebElement(d, "flurry-plan-1", pageName, frameName);
	}

	public WebElement getFinishButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-finish-button", pageName, frameName);
	}

	public WebElement getStep2Desc() {
		return WaitUtil.getVisibleWebElement(d, "flurry-step2-desc", pageName, frameName);
	}

	/**
	 * Generic method call to start the flurry
	 * 
	 * @author sbhat1
	 * @throws Exception
	 */
	public void startFlurryTool() throws Exception {
		FlurryUtil.setFlurryTracker(d);
	}

	public List<WebElement> getGeneratedKyes() {
		return WaitUtil.getVisibleWebElements(d, "flurry-keys-in-tool", pageName, frameName);
	}

	public void clearAllTags() {
		FlurryUtil.navigateToFlurryTool(d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(getClearAllButton());
		SeleniumUtil.waitForPageToLoad(3000);
		FlurryUtil.navigateToPFMPage(d);
		SeleniumUtil.waitForPageToLoad(3000);
	}

	public WebElement getCongratsMessage() {
		return WaitUtil.getVisibleWebElement(d, "flurry-congrats-text", pageName, frameName);
	}

	public WebElement getOKLetsGoButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-ok-lets-go-button", pageName, frameName);
	}

	public WebElement fincheckHeader() {
		return WaitUtil.getVisibleWebElement(d, "flurry-health-status", pageName, frameName);
	}

	/**
	 * Indicator 1 related action keys locators
	 * 
	 * @author sbhat1
	 */
	public WebElement getIndicator1FromDashboard() {
		return WaitUtil.getClickableWebElement(d, "flury-ind1-dashboard", pageName, frameName);
	}

	public WebElement getInd1IncomeValue() {
		return WaitUtil.getClickableWebElement(d, "flurry-ind1-income-val", pageName, frameName);
	}

	public WebElement getInd1INCOMEFTUE1() {
		return WaitUtil.getClickableWebElement(d, "flurry-ftue-continue", pageName, frameName);
	}

	public WebElement getInd1INCOMEFTUE2() {
		return WaitUtil.getClickableWebElement(d, "flurry-ftue-goto", pageName, frameName);
	}

	public WebElement getBackLinkToFincheck() {
		return WaitUtil.getClickableWebElement(d, "flurry-back-link-to-fincheck", pageName, frameName);
	}

	public WebElement getInd1AverageExpense() {
		return WaitUtil.getClickableWebElement(d, "flurry-ind1-expense-val", pageName, frameName);
	}

	public WebElement getAboutScoreLink() {
		return WaitUtil.getClickableWebElement(d, "flurry-ind1-about-score-link", pageName, frameName);
	}

	public WebElement getWhyImportantLink() {
		return WaitUtil.getClickableWebElement(d, "flurry-why-imp-link", pageName, frameName);
	}

	public WebElement getAccountCheckBox() {
		//return WaitUtil.getClickableWebElement(d, "flurry-account-checkbox", pageName, frameName);
		return SeleniumUtil.getWebElement(d, "flurry-account-checkbox", pageName, frameName);
	}

	public WebElement getSaveChangesButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-save-button", pageName, frameName);
	}

	public WebElement getCancleButtonPopup() {
		return WaitUtil.getClickableWebElement(d, "flurry-popupcancle-button", pageName, frameName);
	}

	public WebElement getSaveButtonPopup() {
		return WaitUtil.getClickableWebElement(d, "flurry-popupsave-button", pageName, frameName);
	}

	public WebElement getActionButton() {
		return WaitUtil.getClickableWebElement(d, "flurry-indicator-action-button", pageName, frameName);
	}

	public WebElement getAverageIELink() {
		return WaitUtil.getClickableWebElement(d, "flurry-ind1-average-ie-ind", pageName, frameName);
	}
	
	public WebElement getBackToIndicatorLink() {
		return WaitUtil.getClickableWebElement(d, "finV2-link-back", pageName, frameName);
	}
}
