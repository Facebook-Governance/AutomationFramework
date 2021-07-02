/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.BudgetV2;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_NeedsWants_BannerPage_Loc extends SeleniumUtil {
	WebDriver d;
	static String pageName = "Budget", frameName = null;
	public static By allWantsCategoriesActive = getByObject(pageName, frameName, "allWantsCategoryActive");
	public static By allNeedsCategoriesActive = getByObject(pageName, frameName, "allNeedsCategoryActive");
	public static By needsContainerTab = getByObject(pageName, frameName, "needsContainerTab_BS");
	public static By wantsContainerTab = getByObject(pageName, frameName, "wantsContainerTab_BS");

	public Budget_NeedsWants_BannerPage_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement needsAndWantsBannerLink() {
		return getVisibileWebElement(d, "needsAndWantsBannerLink_BS", pageName, frameName);
	}

	public WebElement creatBudgeBtnIcon() {
		return getVisibileWebElement(d, "creatBudgeBtnIcon_BS", pageName, frameName);
	}

	// *[@autoid="Bgt_Btn_CreateBgt"])[1]
	public WebElement budget_Getstart_GetStartButton() {
		return getVisibileWebElement(d, "budget_Getstart_GetStartButton", pageName, frameName);
	}

	public WebElement budget_Steps1_BudgetNameField() {
		// group name input field locator in step1
		return getWebElement(d, "budget_Steps1_BudgetNameField", pageName, frameName);
	}

	public List<WebElement> budget_Steps1_AccountCheckBox(String accountContainerName) {// get the account selection
		// check box based on container
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps1_AccountCheckBox");
		xpath = xpath.replace("accountContainerName", accountContainerName);
		return d.findElements(By.xpath(xpath));
	}

	// Common method for budget
	public void sendKeysAndPressTab(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
		element.sendKeys(Keys.TAB);
	}

	public WebElement budget_Steps1_NextButton() {// step1 next button locator
		return getVisibileWebElement(d, "budget_Steps1_NextButton", pageName, frameName);
	}

	public WebElement budget_Steps2_DoneButton() {
		// done button in step2
		return getWebElement(d, "budget_Steps2_DoneButton", pageName, frameName);
	}

	public WebElement createBudgetBtn() {
		return getWebElement(d, "creatBudgeBtnIcon_BS", pageName, frameName);
	}

	public void createBudgetSetup(String groupName) {
		click(budget_Getstart_GetStartButton());
		waitForPageToLoad();
		sendKeysAndPressTab(budget_Steps1_BudgetNameField(), groupName);
		click(this.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))
				.get(0));
		waitForPageToLoad();
		click(budget_Steps1_NextButton());
		waitForPageToLoad();
		
	}

	public void createBudgetSetup1(String groupName) {
		click(createBudgetBtnBudSummary());
		sendKeysAndPressTab(budget_Steps1_BudgetNameField(), groupName);
		click(this.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_My Group")).get(0));
		waitForPageToLoad();
		click(budget_Steps1_NextButton());
		waitForPageToLoad();
		click(budget_Steps2_DoneButton());
		waitForPageToLoad();
	}
	public void createBudgetSetup3(String groupName) {
		click(budget_Getstart_GetStartButton());
		waitForPageToLoad();
		sendKeysAndPressTab(budget_Steps1_BudgetNameField(), groupName);
		/*
		 * click(this.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue(
		 * "Budget_Step1InvestmentContainerInput")) .get(0));
		 */
		waitForPageToLoad();
		click(budget_Steps1_NextButton());
		waitForPageToLoad();
		click(budget_Steps2_DoneButton());
		waitForPageToLoad();
	}

	public WebElement needsAndWantsPopupHeader() {
		return getWebElement(d, "needsAndWantsPopupHeader_BS", pageName, frameName);
	}

	public WebElement needsAndWantsPopupMessage() {
		return getWebElement(d, "needsAndWantsPopupMessage_BS", pageName, frameName);
	}

	public WebElement needsToggleBtn() {
		return getWebElement(d, "needsToggleBtn_BS", pageName, frameName);
	}

	public WebElement wantsToggleBtn() {
		return getWebElement(d, "wantsToggleBtn_BS", pageName, frameName);
	}

	public WebElement updateBtnPopUpPage() {
		return getWebElement(d, "updateBtnPopUpPage_BS", pageName, frameName);
	}

	public WebElement updatePopUpMessage() {
		return getWebElement(d, "updatePopUpMessage_BS", pageName, frameName);
	}

	public WebElement yesChangeMessage() {
		return getWebElement(d, "yesChangeMessage_BS", pageName, frameName);
	}

	public WebElement dontChangeMessage() {
		return getWebElement(d, "dontChangeMessage_BS", pageName, frameName);
	}

	public WebElement closeIconPopUp() {
		return getWebElement(d, "closeIconPopUp_BS", pageName, frameName);
	}

	public List<WebElement> needsBudgetSetupCatList() {
		return getWebElements(d, "needsBudgetSetupCatList_BS", pageName, frameName);
	}

	public List<WebElement> wantsBudgetSetupCatList() {
		return getWebElements(d, "wantsBudgetSetupCatList_BS", pageName, frameName);
	}

	public WebElement changedCatogryName() {
		return getWebElement(d, "changedCatName_BS", pageName, frameName);
	}

	public List<WebElement> wantsBudgetSummaryCatList() {
		return getWebElements(d, "wantsBudgetSummaryCatList_BS", pageName, frameName);
	}

	public List<WebElement> needsBudgetSummaryCatList() {
		return getWebElements(d, "needsBudgetSummaryCatList_BS", pageName, frameName);
	}

	public List<WebElement> allNeedsWantsToggleBtn() {
		return getWebElements(d, "allNeedsWantsToggleBtn_BS", pageName, frameName);
	}

	public List<WebElement> allWantsCategoriesActive() {
		return getWebElements(d, "allWantsCategoryActive", pageName, frameName);
	}

	public WebElement noCategWantsContainerMessage() {
		return getWebElement(d, "noCategWantsContainerMessage_BS", pageName, frameName);
	}

	public WebElement noCategMsgToNeedsWantsPopupLink() {
		return getWebElement(d, "noCategMsgToNeedsWantsPopupLink_BS", pageName, frameName);
	}

	public WebElement noCategNeedsContainerMessage() {
		return getWebElement(d, "noCategNeedsContainerMessage_BS", pageName, frameName);
	}

	public WebElement budget_Summery_CustomedateFilterName(String filteName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Summery_CustomedateFilterName");
		xpath = xpath.replace("filteName", filteName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement createBudgetBtnBudSummary() {
		return getWebElement(d, "createBudgetBtnBudSummary_BS", pageName, frameName);
	}

	public WebElement addBudCategBudSummary() {
		return getWebElement(d, "addBudCategBudSummary_BS", pageName, frameName);
	}

	public WebElement needswantsToastMsg() {
		return getWebElement(d, "needswantsToastMsg_BS", pageName, frameName);
	}

	// Added for Budget_AddPercentageToProgressBar_Test
	public List<WebElement> percentageCatList() {
		return getWebElements(d, "percentageCatList_BS", pageName, frameName);
	}

	public List<WebElement> spentOrEarnedCategAmountList() {
		return getWebElements(d, "spentOrEarnedCategAmountList_BS", pageName, frameName);
	}

	public List<WebElement> budgetedCategAmountList() {
		return getWebElements(d, "budgetedCategAmountList_BS", pageName, frameName);
	}

	public List<WebElement> budgetedCategAmountListHistory() {
		return getWebElements(d, "budgetedCategAmountListHistory_BS", pageName, frameName);
	}
	
	public void openNeedsContainer() {
		if(getAttribute(needsContainerTab, "aria-expanded").equals("false")) {
			click(needsContainerTab);
		}
	}
	
	public void openWantsContainer() {
		if(getAttribute(wantsContainerTab, "aria-expanded").equals("false")) {
			click(wantsContainerTab);
		}
	}
	
	// Added for Budget_BudgetSummary_SectionLevel_Test
	public WebElement allWants() {
		return getWebElement(d, "allWants_BS", pageName, frameName);
	}
	public WebElement allNeeds() {
		return getWebElement(d, "allNeeds_BS", pageName, frameName);
	}
	public WebElement allIncome() {
		return getWebElement(d, "allIncome_BS", pageName, frameName);
	}
	
	
	public List<WebElement> allWantsCatBudgetedAmt() {
		return getWebElements(d, "allWantsCatBudgetedAmt_BS", pageName, frameName);
	}
	public WebElement allWantsBudgetedTotalAmt() {
		return getWebElement(d, "allWantsBudgetedTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> allWantsCatSpentAmt() {
		return getWebElements(d, "allWantsCatSpentAmt_BS", pageName, frameName);
	}
	public WebElement allWantsSpentTotalAmt() {
		return getWebElement(d, "allWantsSpentTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> allWantsCatLeftOverAmt() {
		return getWebElements(d, "allWantsCatLeftOverAmt_BS", pageName, frameName);
	}
	public WebElement allWantsLeftOverTotalAmt() {
		return getWebElement(d, "allWantsLeftOverTotalAmt_BS", pageName, frameName);
	}
	
	
	public List<WebElement> allNeedsCatBudgetedAmt() {
		return getWebElements(d, "allNeedsCatBudgetedAmt_BS", pageName, frameName);
	}
	public WebElement allNeedsBudgetedTotalAmt() {
		return getWebElement(d, "allNeedsBudgetedTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> allNeedsCatSpentAmt() {
		return getWebElements(d, "allNeedsSpentTotalAmt_BS", pageName, frameName);
	}
	public WebElement allNeedsSpentTotalAmt() {
		return getWebElement(d, "allNeedsSpentTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> allNeedsCatLeftOverAmt() {
		return getWebElements(d, "allNeedsCatLeftOverAmt_BS", pageName, frameName);
	}
	public WebElement allNeedsLeftOverTotalAmt() {
		return getWebElement(d, "allNeedsLeftOverTotalAmt_BS", pageName, frameName);
	}
	
	public List<WebElement> allIncomeCatBudgetedAmt() {
		return getWebElements(d, "allIncomeCatBudgetedAmt_BS", pageName, frameName);
	}
	public WebElement allIncomeBudgetedTotalAmt() {
		return getWebElement(d, "allIncomeBudgetedTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> allIncomeCatSpentAmt() {
		return getWebElements(d, "allIncomeCatSpentAmt_BS", pageName, frameName);
	}
	public WebElement allIncomeSpentTotalAmt() {
		return getWebElement(d, "allIncomeSpentTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> allIncomeCatLeftOverAmt() {
		return getWebElements(d, "allIncomeCatLeftOverAmt_BS", pageName, frameName);
	}
	public WebElement allIncomeLeftOverTotalAmt() {
		return getWebElement(d, "allIncomeLeftOverTotalAmt_BS", pageName, frameName);
	}
	
	
	public List<WebElement> budgetHistory_allWantsCatBudgetedAmt() {
		return getWebElements(d, "budgetHistory_allWantsCatBudgetedAmt_BS", pageName, frameName);
	}
	public WebElement budgetHistory_allWantsBudgetedTotalAmt() {
		return getWebElement(d, "budgetHistory_allWantsBudgetedTotalAmt_BS", pageName, frameName);
	}
	public List<WebElement> budgetHistory_allWantsCatSpentAmt() {
		return getWebElements(d, "budgetHistory_allWantsCatSpentAmt_BS", pageName, frameName);
	}
	public WebElement budgetHistory_allWantsSpentTotalAmt() {
		return getWebElement(d, "budgetHistory_allWantsSpentTotalAmt_BS", pageName, frameName);
	}
	
	public List<WebElement> budgetHistory_allWantsCatLeftOverAmt() {
		return getWebElements(d, "budgetHistory_allWantsCatLeftOverAmt_BS", pageName, frameName);
	}
	public WebElement budgetHistory_allWantsLeftOverTotalAmt() {
		return getWebElement(d, "budgetHistory_allWantsLeftOverTotalAmt_BS", pageName, frameName);
	}
	
	
}