/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.FincheckV2;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Spend_Less_Than_You_Earn_Loc {
	WebDriver d;
	String pageName, frameName;

	public FincheckV2_Spend_Less_Than_You_Earn_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	
	public boolean ismobileCloseBtnPresent()
	{
		return PageParser.isElementPresent("fincheck-v2-mobile-closebtn", pageName, frameName);
	}
	
	public WebElement mobileCloseBtn() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-mobile-closebtn", pageName, frameName);
	}
	public WebElement getIndicatorFromDashboard() {
		return SeleniumUtil.getWebElement(d, "finv2-indicator-title-1-title", pageName, frameName);
	}

	public WebElement getIndicatorTitle() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-spend-income-header", pageName, frameName);
	}

	public WebElement getScoreDetailsText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-indicator-score-details", pageName, frameName);
	}

	public WebElement getBackLink() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-link-back-to-dashboard", pageName, frameName);
	}

	public WebElement getSugestionText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-indicator-sugestion-description", pageName, frameName);
	}

	public WebElement getHealthBar() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-health-status-bar", pageName, frameName);
	}

	public WebElement getThumbsUpIcon() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-thumbs-up", pageName, frameName);
	}

	public WebElement getScoreDetailsHeader() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-header-score-details", pageName, frameName);
	}

	public WebElement getAverageIncomeText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-average-income", pageName, frameName);
	}

	public WebElement getAverageIncomeValue() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-value-average-income", pageName, frameName);
	}

	public WebElement getAverageSpendingText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-average-spending", pageName, frameName);
	}

	public WebElement getAverageSpendingValue() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-value-average-spending", pageName, frameName);
	}

	public WebElement getAboutTheScoreText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-about-this-score", pageName, frameName);
	}

	public WebElement getScoreDetailsArrow() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-arrow-score-details", pageName, frameName);
	}

	public WebElement getTakeActionsHeader() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-header-take-actions", pageName, frameName);
	}

	public WebElement getTakeActionDescription() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-take-action-description", pageName, frameName);
	}

	public WebElement getActionButton() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-indicator-action-button", pageName, frameName);
	}

	public WebElement getAboutScoreLink() {
		return SeleniumUtil.getWebElement(d, "finV2-details-text-about-this-score", pageName, frameName);
	}

	public WebElement getSpendingHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-details-header-spending", pageName, frameName);
	}

	public WebElement getIncomeHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-details-header-income", pageName, frameName);
	}

	public WebElement getRatioHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-details-header-ratio", pageName, frameName);
	}

	public List<WebElement> getIncomeValues() {
		return SeleniumUtil.getWebElements(d, "finV2-details-value-monthy-income", pageName, frameName);
	}
	
	public List<WebElement> getSpendingValues() {
		return SeleniumUtil.getWebElements(d, "finV2-details-value-monthy-spending", pageName, frameName);
	}
	
	public List<WebElement> getMonthlyRatio() {
		return SeleniumUtil.getWebElements(d, "finV2-details-value-monthy-ratio", pageName, frameName);
	}
	
	public WebElement getAverageText() {
		return SeleniumUtil.getWebElement(d, "finV2-details-text-average", pageName, frameName);
	}
	
	public WebElement getAverageIncomeTableValue() {
		return SeleniumUtil.getWebElement(d, "finV2-details-value-average-income", pageName, frameName);
	}

	public WebElement getAverageExpenseTableValeu() {
		return SeleniumUtil.getWebElement(d, "finV2-details-value-average-spending", pageName, frameName);
	}
	
	
	public WebElement getEAIAValue() {
		return SeleniumUtil.getWebElement(d, "EAIA-value-FinV2", pageName, frameName);
	}

	public WebElement getBackToFincheckLink() {
		return SeleniumUtil.getWebElement(d, "back-to-text-fin-v2", pageName, frameName);
	}
	
	//Code Related to About Score
	
	public WebElement getAboutScoreStatusHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-status-text", pageName, frameName);
	}
	
	public WebElement getWhyImportantText() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-text-why-imp", pageName, frameName);
	}
	
	public WebElement getWhyImportantDescriptionText() {
		return SeleniumUtil.getWebElement(d, "finV2-why-imp-description-text", pageName, frameName);
	}
	
	public WebElement getHowThisHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-text-how-calc", pageName, frameName);
	}
	
	public WebElement getHowThisDescriptionText() {
		return SeleniumUtil.getWebElement(d, "finV2-how-calculate-description-text", pageName, frameName);
	}
	
	public WebElement getSeeMoreLink() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-link-see-more", pageName, frameName);
	}
	
	public WebElement getSeeLessLink() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-link-see-less", pageName, frameName);
	}
	
	public List<WebElement> getAccountNames() {
		return SeleniumUtil.getWebElements(d, "finV2-about-score-text-account-name", pageName, frameName);
	}
	
	public List<WebElement> getAccountNum() {
		return SeleniumUtil.getWebElements(d, "finV2-about-score-text-account-details", pageName, frameName);
	}
	
	public List<WebElement> getCheckBoxes() {
		return SeleniumUtil.getWebElements(d, "fincheck-v2-checkbox-group", pageName, frameName);
	}
	
	public WebElement getSaveChangesButton() {
		return SeleniumUtil.getWebElement(d, "finV2-button-save-changes", pageName, frameName);
	}
	
	
	public WebElement getPopupSave() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-account-save-button", pageName, frameName);
	}
}
