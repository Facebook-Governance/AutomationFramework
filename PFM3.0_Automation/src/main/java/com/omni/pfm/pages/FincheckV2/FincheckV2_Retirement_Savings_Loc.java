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

import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Retirement_Savings_Loc {
	WebDriver d;
	String pageName, frameName;

	public FincheckV2_Retirement_Savings_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	public WebElement getIndicatorFromDashboard() {
		return SeleniumUtil.getWebElement(d, "finv2-indicator-4-title", pageName, frameName);
	}

	public WebElement getIndicatorTitle() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-ret-sav-header", pageName, frameName);
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
	
	public WebElement getAnnualGrossIncomeText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-annual-gross-income", pageName, frameName);
	}
	
	public WebElement getAnnualGrossIncomeValue() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-value-annual-gross-income", pageName, frameName);
	}
	
	public WebElement getSuggestedSavingsText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-suggested-savings", pageName, frameName);
	}
	
	public WebElement getSuggestedSavingsValue() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-value-suggested-savings", pageName, frameName);
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
		return SeleniumUtil.getWebElement(d, "fincheck-v2-button-more-resources", pageName, frameName);
	}
	
	public WebElement getAnnualIncomeTextbox() {
		return SeleniumUtil.getWebElement(d, "annualGrossIncomeId_FinV2", pageName, frameName);
	}

	public WebElement getUpdateButton() {
		return SeleniumUtil.getWebElement(d, "fincheck_v2_btn_credit_score_settings_update", pageName, frameName);
	}

	public WebElement getPersonalInfoDesc() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-header-personal-information", pageName, frameName);
	}

	public WebElement getAnnualIncomeBreakup() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-anual-income-description", pageName, frameName);
	}

	public WebElement getCancleButton() {
		return SeleniumUtil.getWebElement(d, "fincheck_v2_btn_credit_score_settings_cancel", pageName, frameName);
	}

	public WebElement getAnnualIncomeAppTitle() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-header-annual-gross-income", pageName, frameName);
	}

	public WebElement getBackLinkFromMorePage() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-back-id-more-page", pageName, frameName);
	}

	public WebElement getAboutScoreLink() {
		return SeleniumUtil.getWebElement(d, "finV2-details-text-about-this-score", pageName, frameName);
	}

	public WebElement getLiabilitiesDescription() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-liabilities-desc", pageName, frameName);
	}

	public WebElement getAddLiabilityButton() {
		return SeleniumUtil.getWebElement(d, "finCheck_link_AddNewBill", pageName, frameName);
	}

	public List<WebElement> getDisabledBillsList() {
		return SeleniumUtil.getWebElements(d, "fincheck-v2-ind-5-disabled-bills", pageName, frameName);
	}
	
	public WebElement getBackButton() {
		return SeleniumUtil.getWebElement(d, "finV2-link-back", pageName, frameName);
	}

	
	public WebElement getTableHeader_Age() {
		return SeleniumUtil.getWebElement(d, "finV2-details-header-text-age", pageName, frameName);
	}
	
	public WebElement getTableHeader_Multiplier() {
		return SeleniumUtil.getWebElement(d, "finV2-details-header-text-multiplier", pageName, frameName);
	}
	
	public WebElement getTableHeader_TargetSavings() {
		return SeleniumUtil.getWebElement(d, "finV2-details-header-text-target-savings", pageName, frameName);
	}
	
	
	public List<WebElement> getAgesList() {
		return SeleniumUtil.getWebElements(d, "finV2-details-text-age", pageName, frameName);
	}
	
	
	public List<WebElement> getMultiplierList() {
		return SeleniumUtil.getWebElements(d, "finV2-details-text-multiplier", pageName, frameName);
	}
	
	public List<WebElement> getTargetSavingsList() {
		return SeleniumUtil.getWebElements(d, "finV2-details-text-target-savings", pageName, frameName);
	}
}

