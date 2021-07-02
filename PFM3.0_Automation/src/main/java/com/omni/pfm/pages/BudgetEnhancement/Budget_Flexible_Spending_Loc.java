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

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;



public class Budget_Flexible_Spending_Loc {

	static Logger logger = LoggerFactory.getLogger(Budget_Flexible_Spending_Loc.class);
	public WebDriver d = null;
	static String pageName = "Budget"; // Page Name
	static String frameName = null;

	public Budget_Flexible_Spending_Loc(WebDriver d) {
		this.d = d;
	}


	public List<WebElement> flexibleSpendingCategories(){
		return SeleniumUtil.getWebElements(d, "flexiblespendingcategories", pageName, frameName);
	}

	public WebElement flexibleSpendingEditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "flexiblespendingediticon", pageName, frameName);
	}

	public WebElement flexibleSpendingAddIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "flexiblespendingAddicon", pageName, frameName);
	}

	public WebElement AutomativeExpenseCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "automativeexpense", pageName, frameName);
	}

	public List<WebElement> flexibleSpendingInputBox(){
		return SeleniumUtil.getWebElements(d, "flexiblespendingInputBox", pageName, frameName);
	}

	public WebElement checkAllCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "checkallcheckbox", pageName, frameName);
	}

	public List<WebElement> flexibleSpendingCheckBox() {
		return SeleniumUtil.getWebElements(d, "flexiblespendingcheckbox", pageName, frameName);
	}
	
	public WebElement groceriesCheckBox() {
		return SeleniumUtil.getWebElement(d, "groceriescheckbox", pageName, frameName);
	}

	public WebElement flexibleSpendingBasedOnMonthText() {
		return SeleniumUtil.getWebElement(d, "flexiblespendingbasedonmonthtxt", pageName, frameName);
	}

	public WebElement AutomativeCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "automativeexpensecategory", pageName, frameName);
	}

	public WebElement doneButton() {
		return SeleniumUtil.findElement(d,"nextBtn", pageName, frameName, 5);//getVisibileWebElement(d, "nextBtn", pageName, frameName);
	}

	public WebElement createdBudgetheader() {
		return SeleniumUtil.getVisibileWebElement(d, "header_BDG", pageName, frameName);
	}

	public WebElement totalFlexibleSpendingText() {
		return SeleniumUtil.getVisibileWebElement(d, "totalflexiblespendingtxt", pageName, frameName);
	}

	public WebElement budgetExceedsText() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetexceedstxt", pageName, frameName);
	}
	public WebElement considerBudgetingText() {
		return SeleniumUtil.getVisibileWebElement(d, "considerbudgetingtxt", pageName, frameName);
	}
	public WebElement moreBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "moreIcon_BIABS", pageName, frameName);
	}
	public WebElement featureTourIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "featureTourIcon_BIABS", pageName, frameName);
	}
	public WebElement addFlexibleSpending() {
		return SeleniumUtil.getVisibileWebElement(d, "addFlexibleSpending", pageName, frameName);
	}
	public WebElement addCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "addCategory", pageName, frameName);
	}
	public WebElement addGiftsCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "addGiftsCategory", pageName, frameName);
	}
	public WebElement addAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "addAmount", pageName, frameName);
	}
	public WebElement addBudgetPopUp_Save() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetPopUp_Save", pageName, frameName);
	}
	public WebElement addBudgetPopUp_Save_mob() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetPopUp_Save_mob", pageName, frameName);
	}
	public WebElement verifyGifts() {
		return SeleniumUtil.getVisibileWebElement(d, "verifyGifts", pageName, frameName);
	}
	public WebElement BudgetSetUpDone() {
		return SeleniumUtil.getVisibileWebElement(d, "nextBtn", pageName, frameName);
		
	}
	public WebElement BudgetSetUpDone1() {
		return SeleniumUtil.getVisibileWebElement(d, "next_button_ftue", pageName, frameName);
		
	}
	public WebElement closeIcon(int n) {
		
		return d.findElement(By.xpath("(//a[@title='Close popover'])["+n+"]"));
	}
	
	/**
	 * @author sswain1
	 * This method returns close element in the Flexible category add popup
	 * @return WebElement
	 */
	public WebElement closeFlexibleCategoryPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "CancelButton_CB", pageName, frameName);
	}
	/**
	 * @author sswain1
	 * This method returns close element present in AddFlexiblecategory Screen
	 * @return
	 */
	public WebElement closeAddFlexibleCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "closeAddFlexCategory", pageName, frameName);
	}
	
	public boolean isAddFlexCategoryClosePresent() {
		return PageParser.isElementPresent("closeAddFlexCategory", pageName, frameName);
	}
	public WebElement TotalFlexibleSpendngAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "TotalFlexibleSpendngAmt", pageName, frameName);
	}
	public WebElement budgetLeftAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetLeftAmount", pageName, frameName);
	}


	public WebElement butgtviewdetailsbtn() {
		return SeleniumUtil.getVisibileWebElement(d, "butgtviewdetailsbtn", pageName, frameName);

	}
	
	
	public WebElement budgetshowmorespending() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetshowmorespending", pageName, frameName);

	} 
	public boolean iscatgrCloseMobile()
    {
           return PageParser.isElementPresent("catgrCloseMobile", "Transaction", null);
    }
public WebElement catgrCloseMobile()
    {
           return SeleniumUtil.getVisibileWebElement(d, "catgrCloseMobile", "Transaction", null);
    }
public WebElement doneButtonmob() {
	return SeleniumUtil.getVisibileWebElement(d, "nextBtnmob", pageName, frameName);

} 
}
