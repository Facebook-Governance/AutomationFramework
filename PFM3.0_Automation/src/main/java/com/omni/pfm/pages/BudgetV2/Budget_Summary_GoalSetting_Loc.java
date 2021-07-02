/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.pages.BudgetV2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Summary_GoalSetting_Loc {

	public static WebDriver d;
	String frameName, pageName;
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(Budget_Summary_GoalSetting_Loc.class);

	public Budget_Summary_GoalSetting_Loc(WebDriver d) {
		this.d = d;
		pageName = "Budget";
		frameName = null;
	}

	public WebElement goalTitleInBudgetSummary() {
		return SeleniumUtil.getVisibileWebElement(d, "goalTitleInBudgetSummary_BS", pageName, frameName);
	}

	public WebElement backtoBudgetinSFG() {
		return SeleniumUtil.getVisibileWebElement(d, "backtoBudgetinSFG_BS", pageName, frameName);
	}

	public WebElement createNewGoalLink() {
		return SeleniumUtil.getVisibileWebElement(d, "createNewGoalLink_BS", pageName, frameName);
	}

	public WebElement addBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "addBtn_BS", pageName, frameName);
	}

	public WebElement createNewGoalBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createNewGoalBtn_BS", pageName, frameName);
	}

	public WebElement navigateSFGPage_BS() {
		return SeleniumUtil.getVisibileWebElement(d, "navigateSFGPage_BS", pageName, frameName);
	}

	public WebElement goalTabinBudgetCreatePage() {
		return SeleniumUtil.getVisibileWebElement(d, "goalTabinBudgetCreatePage_BS", pageName, frameName);
	}
	
	public WebElement goalTitleinBudget_Mob() {
		return SeleniumUtil.getVisibileWebElement(d, "goalTitleinBudget_Mob_BS", pageName, frameName);
	}
	public WebElement noGoalinBudgetMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "noGoalinBudgetMsg_BS", pageName, frameName);
	}
	
	public WebElement goalTitleinBudget(){
		
		return SeleniumUtil.getWebElement(d, "goalTitleinBudget_BS", pageName, frameName);
	}
	
	public WebElement moreBtn_Mob() {
		return SeleniumUtil.getVisibileWebElement(d, "moreBtn_BS_mob", pageName, frameName);
	}
	public WebElement budgetStartBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetStartBtn_BS", pageName, frameName);
	}

	public WebElement goalFrequency() {
		return SeleniumUtil.getVisibileWebElement(d, "goalFrequency_BS", pageName, frameName);
	}

	public WebElement budgetedGoals() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetedGoals_BS", pageName, frameName);
	}

	public WebElement accountDetails() {
		return SeleniumUtil.getVisibileWebElement(d, "accountDetails_BS", pageName, frameName);
	}

	public WebElement selectAllCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "selectAllCheckbox_BS", pageName, frameName);
	}

	public WebElement navigatePopup() {
		return SeleniumUtil.getVisibileWebElement(d, "navigatePopup_BS", pageName, frameName);
	}

	public WebElement moreBtnGoal() {
		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "moreBtnGoal_BS", pageName, frameName);
	}

	public WebElement goToThisGoalLink() {
		return SeleniumUtil.getVisibileWebElement(d, "goToThisGoalLink_BS", pageName, frameName);
	}

	public WebElement firstGoalnameinBudGoadTab() {
		return SeleniumUtil.getVisibileWebElement(d, "firstGoalnameinBudGoadTab_BS", pageName, frameName);
	}

	public WebElement firstGoalnameinSFG() {
		return SeleniumUtil.getVisibileWebElement(d, "firstGoalnameinSFG_BS", pageName, frameName);
	}

	public WebElement allocation() {
		return SeleniumUtil.getVisibileWebElement(d, "allocation_BS", pageName, frameName);
	}

	public WebElement goalSummaryFinappHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "goalSummaryFinappHeader_BS", pageName, frameName);
	}

	public WebElement unLinkFromBudget() {
		return SeleniumUtil.getVisibileWebElement(d, "unLinkFromBudget_BS", pageName, frameName);
	}
	
	public WebElement addGoalLinkInMore() {
		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "addGoalLinkInMore_BS", pageName, frameName);
	}
	public WebElement confirmPopupUnLink() {
		return SeleniumUtil.getVisibileWebElement(d, "confirmPopupUnLink_BS", pageName, frameName);
	}
	
	public WebElement addGoalLinkBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "addGoalLinkBtn_BS", pageName, frameName);
	}
	public WebElement actualIncome() {
		return SeleniumUtil.getWebElement(d, "actualIncome_BS", pageName, frameName);
	}
	public WebElement actualSpending() {
		return SeleniumUtil.getWebElement(d, "actualSpending_BS", pageName, frameName);
	}
	public WebElement budgetedGoalsAmt() {
		return SeleniumUtil.getWebElement(d, "budgetedGoalsCal_BS", pageName, frameName);
	}
	public WebElement left_BS() {
		return SeleniumUtil.getWebElement(d, "left_BS", pageName, frameName);
	}
}
