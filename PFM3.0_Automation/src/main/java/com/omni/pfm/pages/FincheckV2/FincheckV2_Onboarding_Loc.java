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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Onboarding_Loc {
	WebDriver d;
	String pageName, frameName;
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Onboarding_Loc.class);

	public FincheckV2_Onboarding_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	public WebElement getWelcomeHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-welcome-header", pageName, frameName);
	}

	public List<WebElement> getPaginations() {
		return SeleniumUtil.getWebElements(d, "finV2-pagination-dot", pageName, frameName);
	}

	public WebElement getWelcomeDesc1() {
		return SeleniumUtil.getWebElement(d, "finV2-welcome-desc-1", pageName, frameName);
	}
	
	public WebElement getWelcomeDescStep2() {
		return SeleniumUtil.getWebElement(d, "finV2-welcome-desc-step-1", pageName, frameName);
	}
	
	public WebElement getWelcomeDesc4() {
		return SeleniumUtil.getWebElement(d, "finV2-welcome-desc-4", pageName, frameName);
	}
	
	public WebElement getWelcomeDesc3() {
		return SeleniumUtil.getWebElement(d, "finV2-welcome-desc-3", pageName, frameName);
	}

	public WebElement getWelcomeDesc2() {
		return SeleniumUtil.getWebElement(d, "finV2-welcome-desc-2", pageName, frameName);
	}

	public WebElement getStartedButton() {
		return SeleniumUtil.getWebElement(d, "finV2-get-started-btn", pageName, frameName);
	}

	public WebElement getRightArrow() {
		return SeleniumUtil.getWebElement(d, "finV2-right-arrow", pageName, frameName);
	}

	public WebElement getLeftArrow() {
		return SeleniumUtil.getWebElement(d, "finV2-left-arrow", pageName, frameName);
	}

	public WebElement getFutureStep1Desc() {
		return SeleniumUtil.getWebElement(d, "finV2-future-step1-desc", pageName, frameName);
	}

	public WebElement getProgressBar() {
		return SeleniumUtil.getWebElement(d, "finV2-onboarding-progressbar", pageName, frameName);
	}

	public WebElement getRecomendedAccHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-recomended-acc-header", pageName, frameName);
	}

	public WebElement getAvailableAccHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-available-acc-header", pageName, frameName);
	}

	public WebElement getAvailableCashAcc() {
		return SeleniumUtil.getWebElement(d, "finV2-available-cash-acc-header", pageName, frameName);
	}

	public List<WebElement> getAvailableCashAccNames() {
		return SeleniumUtil.getWebElements(d, "finV2-available-cash-acc-names", pageName, frameName);
	}

	public List<WebElement> getCashAccMaskedNumbers() {
		return SeleniumUtil.getWebElements(d, "finV2-available-cash-acc-masked-num", pageName, frameName);
	}

	public WebElement getAvailableCardAcc() {
		return SeleniumUtil.getWebElement(d, "finV2-available-card-acc-header", pageName, frameName);
	}

	public List<WebElement> getAvailableCardNames() {
		return SeleniumUtil.getWebElements(d, "finV2-available-card-acc-names", pageName, frameName);
	}

	public List<WebElement> getCardAccMaskedNumbers() {
		return SeleniumUtil.getWebElements(d, "finV2-available-card-acc-masked-num", pageName, frameName);
	}

	public WebElement getAvailableInvAcc() {
		return SeleniumUtil.getWebElement(d, "finV2-available-inv-acc-header", pageName, frameName);
	}

	public List<WebElement> getAvailableInvNames() {
		return SeleniumUtil.getWebElements(d, "finV2-available-inv-acc-names", pageName, frameName);
	}

	public List<WebElement> getInvAccMaskedNumbers() {
		return SeleniumUtil.getWebElements(d, "finV2-available-inv-acc-masked-num", pageName, frameName);
	}

	public WebElement getLinkMoreAccountsButton() {
		return SeleniumUtil.getWebElement(d, "finV2-button-linkMore", pageName, frameName);
	}

	public WebElement getFLTitle() {
		return SeleniumUtil.getWebElement(d, "finV2-title-FL", pageName, frameName);
	}

	public WebElement getFLClose() {
		return SeleniumUtil.getWebElement(d, "finV2-close-button-fl", pageName, frameName);
	}

	public WebElement getContinueButton() {
		return SeleniumUtil.getWebElement(d, "finV2-continue-btn-step1", pageName, frameName);
	}

	/**
	 * For Step 2 - Personal Information
	 */
	public WebElement getStep2Header() {
		return SeleniumUtil.getWebElement(d, "finV2-step-2-header", pageName, frameName);
	}

	public WebElement getPersonalInfoText() {
		return SeleniumUtil.getWebElement(d, "finV2-personal-info-text", pageName, frameName);
	}

	public WebElement getYearOfBirthLabel() {
		return SeleniumUtil.getWebElement(d, "finV2-yearOfBirth-label", pageName, frameName);
	}

	public WebElement getAnualIncomeLabel() {
		return SeleniumUtil.getWebElement(d, "finV2-anualGross-label", pageName, frameName);
	}

	public WebElement getCreditScoreLabel() {
		return SeleniumUtil.getWebElement(d, "finV2-creditScore-label", pageName, frameName);
	}

	public WebElement getYOBDropDown() {
		return SeleniumUtil.getWebElement(d, "finV2-year-of-birth-dd", pageName, frameName);
	}

	public int getYearsCount() {
		List<WebElement> years = SeleniumUtil.getWebElements(d, "finV2-yob-years-list'", pageName, frameName);
		int yearCount = years.size();
		return yearCount;
	}

	public int getScoreCount() {
		List<WebElement> score = SeleniumUtil.getWebElements(d, "finV2-creditscore-list'", pageName, frameName);
		int scoreCount = score.size();
		return scoreCount;
	}

	public WebElement getYODDropdownSelectedValue() {
		return SeleniumUtil.getWebElement(d, "finV2-dropdown-1-selected-value]", pageName, frameName);
	}

	public WebElement getCSDropdownSelectedValue() {
		return SeleniumUtil.getWebElement(d, "finV2-dropdown-2-selected-value", pageName, frameName);
	}

	public WebElement getNextButton() {
		return SeleniumUtil.getWebElement(d, "finV2-next-btn", pageName, frameName);
	}
	
	public WebElement getStep3Header() {
		return SeleniumUtil.getWebElement(d, "finV2-step3-description", pageName, frameName);
	}

	public WebElement getHouseHoldStatusHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-household-status-header", pageName, frameName);
	}

	public WebElement getInsuranceHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-insurance-status-header", pageName, frameName);
	}
	
	public List<WebElement> getHouseHoldValues() {
		return SeleniumUtil.getWebElements(d, "finV2-houehold-status-value", pageName, frameName);
	}
	
	public List<WebElement> getHouseHoldRadioButtons() {
		return SeleniumUtil.getWebElements(d, "finV2-household-status-radio-button", pageName, frameName);
	}
	
	public List<WebElement> getInsuranceRadioButtons(){
		return SeleniumUtil.getWebElements(d, "finV2-insurance-radio-buttons", pageName, frameName);
	}
	
	public WebElement getDisabilityCheckbox() {
		return SeleniumUtil.getWebElement(d, "finV2-disability-checkbox", pageName, frameName);
	}
	
	public WebElement getLifeCheckbox() {
		return SeleniumUtil.getWebElement(d, "finV2-life-checkbox", pageName, frameName);
	}
	
	public WebElement getStep4Header() {
		return SeleniumUtil.getWebElement(d, "finV2-step-4-header", pageName, frameName);
	}
	
	public WebElement getStep4Desc() {
		return SeleniumUtil.getWebElement(d, "finV2-step-4-desc", pageName, frameName);
	}
	
	public List<WebElement> getPlansList() {
		return SeleniumUtil.getWebElements(d, "finV2-plans-list", pageName, frameName);
	}
	
	public List<WebElement> getPlanningRadioButtons() {
		return SeleniumUtil.getWebElements(d, "finV2-planning-radio", pageName, frameName);
	}
	
	public WebElement getFinishButton() {
		return SeleniumUtil.getWebElement(d, "finV2-finish-button", pageName, frameName);
	}
	
	public WebElement getStep5Header() {
		return SeleniumUtil.getWebElement(d, "finV2-step-5-header", pageName, frameName);
	}
	
	public List<WebElement> getHabitsList(){
		return SeleniumUtil.getWebElements(d, "finV2-habits-list", pageName, frameName);
	}
	
	public List<WebElement> getCheckboxesStep5() {
		return SeleniumUtil.getWebElements(d, "finV2-habits-checkbox-list", pageName, frameName);		
	}
	
	public WebElement getBackButton() {
		return SeleniumUtil.getWebElement(d, "finV2-onboarding-back-button", pageName, frameName);
	}
	
	public WebElement getCongratsHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-congrats-header", pageName, frameName);
	}
	
	public WebElement getDoneText() {
		return SeleniumUtil.getWebElement(d, "finV2-done-text", pageName, frameName);
	}
	
	public WebElement getOverviewHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-overview-header", pageName, frameName);
	}
	
}
