/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.SFG;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class SortingGoals_Loc {
	Logger logger=LoggerFactory.getLogger(SortingGoals_Loc.class);
	public WebDriver d=null;
	static String frameName = null;

	public SortingGoals_Loc(WebDriver d) {
		this.d=d;
	}

	public WebElement startGoalGetStartButton() {
		return SeleniumUtil.getWebElement(d, "startGoalGetStartButton", "SFG", null);
	}
	public WebElement SFGCreateGoalButton() {
		return SeleniumUtil.getWebElement(d, "SFGCreateGoalButton", "SFG", null);
	}
	public WebElement frequencyDropdown1() {
		return SeleniumUtil.getWebElement(d, "frequencyDropdown1", "SFG", null);
	}
	public WebElement GoalAmountInput() {
		return SeleniumUtil.getWebElement(d, "GoalAmountInput", "SFG", null);
	}
	public List<WebElement> SFGhighLevlcategories() {
		return SeleniumUtil.getWebElements(d, "SFGhighLevlcategories", "SFG", null);
	}
	public List<WebElement> getSubCatText() {
		return SeleniumUtil.getWebElements(d, "getSubCatText", "SFG", null);
	}
	public WebElement GoalNameInput() {
		return SeleniumUtil.getWebElement(d, "GoalNameInput", "SFG", null);
	}
	public WebElement GoalAmtFrqBtnInput() {
		return SeleniumUtil.getWebElement(d, "GoalAmtFrqBtnInput", "SFG", null);
	}
	public WebElement NextBtnStep1() {
		return SeleniumUtil.getWebElement(d, "NextBtnStep1", "SFG", null);
	}
	public WebElement GoalAmtFrqInput() {
		return SeleniumUtil.getWebElement(d, "GoalAmtFrqInput", "SFG", null);
	}
	public List<WebElement> allFrequency()
	{
		return SeleniumUtil.getWebElements(d, "allFrequency", "SFG", null);
	}
	public WebElement backToNewGoal() {
		return SeleniumUtil.getWebElement(d, "backToNewGoal", "SFG", null);
	}
	public WebElement SFgDraftGoalSaveMyProgress() {
		return SeleniumUtil.getWebElement(d, "SFgDraftGoalSaveMyProgress", "SFG", null);
	}
	public WebElement createGoalTab() {
		return SeleniumUtil.getVisibileWebElement(d, "createGoalTab", "SFG", null);
	}
	public WebElement draftGoalCount() {
		return SeleniumUtil.getVisibileWebElement(d, "draftGoalCount", "SFG", null);
	}
	public WebElement CompletedGoalCount() {
		return SeleniumUtil.getVisibileWebElement(d, "CompletedGoalCount", "SFG", null);
	}
	public WebElement VictoryGoalCount() {
		return SeleniumUtil.getVisibileWebElement(d, "VictoryGoalCount", "SFG", null);
	}
	public List<WebElement> draftViewMoreButton() {
		return SeleniumUtil.getWebElements(d, "draftViewMoreButton", "SFG", null);
	}
	public List<WebElement> viewMoreButtonForCompleted() {
		return SeleniumUtil.getWebElements(d, "viewMoreButtonForCompleted", "SFG", null);
	}
	public List<WebElement> viewMoreButtonForVictory() {
		return SeleniumUtil.getWebElements(d, "viewMoreButtonForVictory", "SFG", null);
	}
	public List<WebElement> viewMoreButtonForInprogress() {
		return SeleniumUtil.getWebElements(d, "viewMoreButtonForInprogress", "SFG", null);
	}
	public List<WebElement> SFGDraftGoalNames() {
		return SeleniumUtil.getWebElements(d, "SFGDraftGoalNames", "SFG", null);
	}
	public List<WebElement> SFGCompletedGoalNames() {
		return SeleniumUtil.getWebElements(d, "SFGCompletedGoalNames", "SFG", null);
	}
	public List<WebElement> SFGIVictoryGoalNames() {
		return SeleniumUtil.getWebElements(d, "SFGIVictoryGoalNames", "SFG", null);
	}
	public WebElement OntimeFundingTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "OntimeFundingTextBox", "SFG", null);
	}
	public WebElement nextbuttonClick() {
		return SeleniumUtil.getVisibileWebElement(d, "nextbuttonClick", "SFG", null);
	}
	public WebElement SFGFundingSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SFGFundingSaveButton", "SFG", null);
	}
	public WebElement SFGStartMyGoalButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SFGStartMyGoalButton", "SFG", null);
	}
	public WebElement saveGoalPopUp() {
		return SeleniumUtil.getWebElement(d, "saveGoalPopUp", "SFG", null);
	}
	public WebElement SaveGoalConfirmButton() {
		return SeleniumUtil.getWebElement(d, "SaveGoalConfirmButton", "SFG", null);
	}
	public WebElement CompletedCongratesMsg() {
		return SeleniumUtil.getWebElement(d, "CompletedCongratesMsg", "SFG", null);
	}
	public WebElement VictorCongratesMsg() {
		return SeleniumUtil.getWebElement(d, "VictorCongratesMsg", "SFG", null);
	}
	public WebElement InprogressGoalCount() {
		return SeleniumUtil.getWebElement(d, "InprogressGoalCount", "SFG", null);
	}
	public List<WebElement> MinMaxArrowForGoal() {
		return SeleniumUtil.getWebElements(d, "MinMaxArrowForGoal", "SFG", null);
	}
	public List<WebElement> AllProgressBarStatus() {
		return SeleniumUtil.getWebElements(d, "AllProgressBarStatus", "SFG", null);
	}

	public List<WebElement> allInprogressGoal()
	{
		return SeleniumUtil.getWebElements(d, "InProgressGoalList", "SFG", null);
	}
	
	public void makingGoalAsCompleted() {
		try
		{
			SeleniumUtil.click(allInprogressGoal().get(0));
			SeleniumUtil.click(saveGoalPopUp());
			SeleniumUtil.click(SaveGoalConfirmButton());
			SeleniumUtil.waitForPageToLoad(1000);
			SeleniumUtil.click(backToNewGoal());
			SeleniumUtil.waitForPageToLoad(3000);
			makingGoalAsCompleted();
			SeleniumUtil.waitForPageToLoad(3000);
		}
		catch(Exception e)
		{
		}
	}

	public void makingGoalAsVictory() {
		try
		{
			SeleniumUtil.click(allInprogressGoal().get(0));
			SeleniumUtil.click(saveGoalPopUp());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(SaveGoalConfirmButton());
			SeleniumUtil.click(nextbuttonClick());		
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(SaveGoalConfirmButton());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(backToNewGoal());
			SeleniumUtil.waitForPageToLoad(3000);
			makingGoalAsVictory();
			SeleniumUtil.waitForPageToLoad(3000);
		}
		catch(Exception e)
		{
		}
	}

	
	//Lavanya
	public boolean step2LandingPage(String goalName,String goalAmount,int frequency,String FrequencyAmount )
	{   
		boolean status=false;
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(GoalNameInput());
		GoalNameInput().clear();
		GoalNameInput().sendKeys(goalName);
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		SeleniumUtil.click(frequencyDropdown1());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(allFrequency().get(frequency));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		if(PageParser.isElementPresent("closeButton", "SFG", null))
		{
			SeleniumUtil.click(verifyClose());
		}
		else
		{
		    SeleniumUtil.click(backToNewGoal());
		}
		
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFgDraftGoalSaveMyProgress());
		SeleniumUtil.waitForPageToLoad(500);
		if(verifySFGGoalSaveMessage().isDisplayed())
		{
			status=true;
		}
		else
		{
			status=false;
		}
		return status;
	}
	
	public boolean createCompletedGoalwithSingleAcnt(String goalName ,String goalAmount,String Goalfrequency,String oneTimeAmount)
	{
		//SeleniumUtil.click(newGoalbutton());
		boolean status = false;
		SeleniumUtil.click(SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000); 
		SeleniumUtil.click(GoalNameInput()); 
		GoalNameInput().clear();
		GoalNameInput().sendKeys(goalName);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(Goalfrequency);
		SeleniumUtil.click(frequencyDropdown1());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(allFrequency().get(2));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(NextBtnStep1());

		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(OntimeFundingTextBox());
		OntimeFundingTextBox().sendKeys("50");
		SeleniumUtil.click(nextbuttonClick());
		SeleniumUtil.waitForPageToLoad(1000);
		if(PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null))
		{
			SeleniumUtil.click(verifySFGFundingSaveButton());
		}
		else
		{
		   SeleniumUtil.click(SFGFundingSaveButton());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(nextbuttonClick());
		SeleniumUtil.waitForPageToLoad(1500);
		if(verifySFGGoalSaveMessage().isDisplayed())
		{
			status=true;
		}
		else
		{
			status=false;
		}
		
		
		return status;
	}
	public WebElement verifyClose() {
		return SeleniumUtil.getWebElement(d, "closeButton", "SFG", null);
	}
	
	public WebElement verifySFGFundingSaveButton() {
		return SeleniumUtil.getWebElement(d, "SFGFundingSaveButtonForMobile", "SFG", null);
	}
	
	public WebElement verifySFGGoalSaveMessage() {
		return SeleniumUtil.getWebElement(d, "SFGGoalSaveMessage", "SFG", null);
	}


}
