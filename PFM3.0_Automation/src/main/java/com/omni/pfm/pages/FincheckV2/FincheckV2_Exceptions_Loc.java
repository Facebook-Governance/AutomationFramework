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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Exceptions_Loc {
	WebDriver d;
	String pageName, frameName;

	public FincheckV2_Exceptions_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	public WebElement getEditCredButton() {
		return SeleniumUtil.getWebElement(d, "accounts-icon-editCredentials", pageName, frameName);
	}

	public WebElement getUserNameTextBox() {
		return SeleniumUtil.getWebElement(d, "accounts-user-name-text-box", pageName, frameName);
	}

	public WebElement getPasswordTextBox() {
		return SeleniumUtil.getWebElement(d, "accounts-password-text-box", pageName, frameName);
	}

	public WebElement getRePasswordTextBox() {
		return SeleniumUtil.getWebElement(d, "accounts-repassword-text-box", pageName, frameName);
	}

	public WebElement getSubmitButton() {
		return SeleniumUtil.getWebElement(d, "accounts-submit-button", pageName, frameName);
	}

	public void throwAccountsToError() {
		SeleniumUtil.click(this.getEditCredButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.getUserNameTextBox());
		this.getUserNameTextBox().clear();
		this.getUserNameTextBox().sendKeys("error_404");

		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(this.getPasswordTextBox());
		this.getPasswordTextBox().clear();
		this.getPasswordTextBox().sendKeys("error_404");

		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(this.getRePasswordTextBox());
		this.getRePasswordTextBox().clear();
		this.getRePasswordTextBox().sendKeys("error_404");
		SeleniumUtil.waitForPageToLoad();
		By selectCheckBox = SeleniumUtil.getByObject("LinkAnAccount", null, "selectCheckBox");
		if (SeleniumUtil.isDisplayed(selectCheckBox, 1)) {
			SeleniumUtil.click(selectCheckBox);
		}
		SeleniumUtil.click(this.getSubmitButton());
		SeleniumUtil.waitFor(10);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
	}

	public WebElement getSettingsOverview() {
		return SeleniumUtil.getWebElement(d, "finV2-settings-link-dashboard", pageName, frameName);
	}
	
	
	public WebElement getEditAccountsButton() {
		return SeleniumUtil.getWebElement(d, "finV2-settings-edit-account-button", pageName, frameName);
	}
	
	public WebElement getIndicator1ScoreValue() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_SpendtoincomeratioValue", pageName, frameName);
	}
	
	public WebElement getIndicator2ScoreValue() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_BillpayValue", pageName, frameName);
	}
	
	public WebElement getIndicator3ScoreValue() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_EmergencysavingsValue", pageName, frameName);
	}
	
	public WebElement getIndicator4ScoreValue() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_RetirementsavingsValue", pageName, frameName);
	}
	
	public WebElement getIndicator5ScoreValue() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_DebttoincomeratioValue", pageName, frameName);
	}
	
	public WebElement getExceptionScreenTitle() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_ExecptionScreenTitle", pageName, frameName);
	}
	
	public WebElement getExceptionScreenDesc() {
		return SeleniumUtil.getWebElement(d, "finCheck_lbl_ExceptionScreenDesc", pageName, frameName);
	}
	
	public List<WebElement> getAccountsListInError(){
		return SeleniumUtil.getWebElements(d, "fincheck-v2-exception-ind1-account-names", pageName, frameName);
	}
	
	public WebElement getLinkAccountButton() {
		return SeleniumUtil.getWebElement(d, "finCheck_btn_ExceptionScreenLinkAccount", pageName, frameName);
	}
	
	
	public WebElement getBackLink() {
		return SeleniumUtil.getWebElement(d, "finV2-link-back", pageName, frameName);
	}
	
	
	public WebElement getFinappHeader() {
		return SeleniumUtil.getWebElement(d, "finv2-indicator-title", pageName, frameName);
	}
	
	
	public WebElement getBackLinkFromAccounts() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-exceptions-back-from-accounts", pageName, frameName);
	}
	
	public WebElement getIndicator1Header() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-spend-income-header", pageName, frameName);
	}

	public WebElement getIndicator2Header() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-bill-pay-header", pageName, frameName);
	}
	
	public WebElement getIndicator3Header() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-emer-header", pageName, frameName);
	}
	
	public WebElement getIndicator4Header() {
		return SeleniumUtil.getWebElement(d, "fincheck-v2-ret-sav-header", pageName, frameName);
	}
	
}
