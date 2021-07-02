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

import com.gargoylesoftware.htmlunit.WebWindowNotFoundException;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Debt_To_Income_Loc {
	WebDriver d;
	String pageName, frameName;

	public FincheckV2_Debt_To_Income_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	public WebElement getIndicatorFromDashboard() {
		return SeleniumUtil.getWebElement(d, "finv2-indicator-5-title", pageName, frameName);
	}

	public WebElement getIndicatorTitle() {
		return SeleniumUtil.getWebElement(d, "finv2-indicator-5-header", pageName, frameName);
	}
	
	public WebElement getMonthlyLiabilitiesPageHeader() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-currentLiabilities-header", pageName, frameName);
	}

	public WebElement getScoreDetailsText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-indicator-score-details", pageName, frameName);
	}

	public WebElement getBackLink() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-link-back-to-dashboard", pageName, frameName);
	}

	public WebElement getMonthlyIncomeText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-monthly-gross-income", pageName, frameName);
	}

	public WebElement getMonthlyIncomeValue() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-value-monthly-gross-income", pageName, frameName);
	}

	public WebElement getTotalMontlyDebtText() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-text-total-monthly-debt", pageName, frameName);
	}

	public WebElement getTotalMonthlyDebtValue() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-value-total-monthly-debt", pageName, frameName);
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
	
	public WebElement getBackLinkToIndicator() {
		return SeleniumUtil.getWebElement(d, "finV2-link-back", pageName, frameName);
	}
}
