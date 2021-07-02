/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.BudgetEnhancement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Settings_Loc {

	static Logger logger = LoggerFactory.getLogger(Budget_Settings_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public Budget_Settings_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> notificationMessage() {
		return d.findElements(By.xpath("//div[@id='notificationsSettingTypes']/div/div/div/div[2]"));
	}

	public List<WebElement> notificationOptions() {
		return d.findElements(By.xpath("//div[@id='notificationsSettingTypes']/div/div/div/div[contains(text(),'Alert:')]"));
	}

	public List<WebElement> BudgetAccountList() {
		return d.findElements(By.xpath("//div[@id='budgetsetup-body']//div[1]/div[contains(@class,'section')]//div[@role='heading']"));
	}
	public void verifyAcntsContainerLabelUnderSetting(String expected){
		List<WebElement> l= SeleniumUtil.getWebElements(d, "AcntsContainerLabelUnderSetting", "Budget", null);
		CommonUtils.assertEqualsListElements(expected, l);
	}
	
	public WebElement budgetSettingsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetSettingsHeader", "Budget", null);
	}

	public WebElement sendMeNotificationText() {
		return SeleniumUtil.getVisibileWebElement(d, "sendMeNotificationText", "Budget", null);
	}

	public WebElement linkAccountButton() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountButton", "Budget", null);
	}

	public WebElement linkAccountBelowText() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountBelowText", "Budget", null);
	}

	public WebElement linkAccountBelowTex() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountBelowTex", "Budget", null);
	}

	public WebElement AccountsLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsLabel", "Budget", null);
	}

	public WebElement linkAccountButton1() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountButton1", "Budget", null);
	}

	public WebElement BudgetAccountDescription() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetAccountDescription", "Budget", null);
	}

	public WebElement WhyLinkThoseBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "WhyLinkThoseBudgetText", "Budget", null);
	}

	public WebElement EditAllBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "EditAllBudgetText", "Budget", null);
	}

	public WebElement FlexibleSpendingAddIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "FlexibleSpendingAddIcon", "Budget", null);
	}

	public WebElement SaveChangesButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveChangesButton", "Budget", null);
	}

	public WebElement SuccessMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "SuccessMessage", "Budget", null);
	}

	public WebElement BackToBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "BackToBudgetText", "Budget", null);
	}
	public WebElement AcntGroupUnderSetting() {
		return SeleniumUtil.getVisibileWebElement(d, "AcntGroupUnderSetting", "Budget", null);
	}

	public WebElement BudgetCategoryUnderSetting() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetCategoryUnderSetting", "Budget", null);
	}
	public List<WebElement> CardCheckBox() {
		return SeleniumUtil.getWebElements(d, "CardCheckBox", "Budget", null);
	}
	public WebElement editBudgetedGroupBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "editBudgetedGroupBtn", "Budget", null);
	}
	public List<WebElement> CheckBox() {
		return SeleniumUtil.getWebElements(d, "CheckBox_CB", "Budget", null);
	}
	public void unselectAllAcntsFromGroup(){
		
		for(int i=0;i<CheckBox().size();i++){
			SeleniumUtil.click(CheckBox().get(i));
		}
	}
	public WebElement NoRelevantAcntLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "NoRelevantAcntLabel", "Budget", null);
	}
	
	public void creatingCardGroup(){
		SeleniumUtil.click(CardCheckBox().get(1));
	}
	public void clickOnAcntGroupUnderSetting(){
		SeleniumUtil.click(AcntGroupUnderSetting());
	}
	public String getNoRelevantAcntLabelText(){
		return NoRelevantAcntLabel().getText().trim();
	}
	public void clickOneditBudgetedGroupBtn(){
		SeleniumUtil.click(editBudgetedGroupBtn());
	}
	

}
