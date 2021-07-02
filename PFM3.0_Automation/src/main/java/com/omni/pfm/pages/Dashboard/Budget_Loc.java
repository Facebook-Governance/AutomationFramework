/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Dashboard;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Loc {

	public WebDriver d;
	public static String pageName="DashboardPage";
	public static String frameName=null;
	
	public Budget_Loc(WebDriver d) {

		this.d = d;

	}
	public WebElement BudgetHeader() {
		return SeleniumUtil.getWebElement(d, "header3_BL", pageName, frameName);
	}
	public WebElement WelcomeToBudgetHeader() {
		return SeleniumUtil.getWebElement(d, "text_BL", pageName, frameName);
	}
	public WebElement BudgetHelpsYouText() {
		return SeleniumUtil.getWebElement(d, "txt_BL", pageName, frameName);
	}
	public WebElement GoToFullViewButton() {
		return SeleniumUtil.getWebElement(d, "btn_BL", pageName, frameName);
	}
	public WebElement Budgetheader() {
		return SeleniumUtil.getWebElement(d, "header_BL", pageName, frameName);
	}
	public WebElement incomeInputBox() {
		return SeleniumUtil.getWebElement(d, "inputbx_BL", pageName, frameName);
	}
	public WebElement MonthToDateText() {
		return SeleniumUtil.getWebElement(d, "text1_BL", pageName, frameName);
	}
	public WebElement BudgetMainCard() {
		return SeleniumUtil.getWebElement(d, "card_BL", pageName, frameName);
	}
	public WebElement SpendText() {
		return SeleniumUtil.getWebElement(d, "text2_BL", pageName, frameName);
	}
	public WebElement BudgetedText(){
		return SeleniumUtil.getWebElement(d, "text3_BL", pageName, frameName);
	}
	public WebElement YouHaveLeftToSpendText(){
		return SeleniumUtil.getWebElement(d, "txt1_BL", pageName, frameName);
	}
	public WebElement BudgetedText(int n)
	{
      return d.findElements(By.cssSelector("//div[@id='rightPart']/p")).get(n);
	}

	public WebElement SpendText(int n)
	{
		return d.findElements(By.cssSelector("//div[@id='leftPart']/p")).get(n);
	}
	public WebElement SmileIcon() {
		return SeleniumUtil.getWebElement(d, "icon_BL", pageName, frameName);
	}
	public WebElement SpendAmount() {
		return SeleniumUtil.getWebElement(d, "amt_BL", pageName, frameName);
	}
	public WebElement BudgetedAmount() {
		return SeleniumUtil.getWebElement(d, "amount_BL", pageName, frameName);
	}
	public WebElement IncomeAndBillSummeryText() {
		return SeleniumUtil.getWebElement(d, "text4_BL", pageName, frameName);
	}
	
	//Added by Mallika for Stickyness Group
	 public List<WebElement> DashboardBudgetGroup() {
			return SeleniumUtil.getWebElements(d, "DashboardBudgetGroup", "DashboardPage", null);
		} 
	 public WebElement DashboardBudgetArrow() {
			return SeleniumUtil.getWebElement(d, "DashboardBudgetArrow", "DashboardPage", null);
		}
	 public WebElement DashboardBudgetWidget() {
			return SeleniumUtil.getWebElement(d, "DashboardBudgetWidget", "DashboardPage", null);
		}
	 
}
