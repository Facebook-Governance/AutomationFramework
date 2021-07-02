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
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Income_And_Bill_Loc {
	static Logger logger = LoggerFactory.getLogger(Budget_Income_And_Bill_Loc.class);
	
	public WebDriver d = null;
	static String pageName = "Budget";
	static String frameName = null;

	public Budget_Income_And_Bill_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement CreateBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "title", pageName,frameName);
	}

	public WebElement rentAndMortageCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "rentandmortagecheckbx",pageName, frameName);
	}
	
	public WebElement amountDisabled() {
		return SeleniumUtil.getVisibileWebElement(d, "amtdisabled", pageName,frameName);
	}
	

	public WebElement myIncomeText() {
		return SeleniumUtil.getVisibileWebElement(d, "myincometxt", pageName,frameName);
	}
	public WebElement BudgetIncomeValue() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetIncomeValue", pageName,frameName);
	}

	public WebElement basedMonthDataText() {
		return SeleniumUtil.getVisibileWebElement(d, "basedmonthdatatxt",pageName, frameName);
	}

	public List<WebElement> billCategories() {
		return SeleniumUtil.getWebElements(d, "billcategories", pageName,frameName);
	}

	public WebElement myIncomeEditIcon() {
		return SeleniumUtil.getWebElement(d, "myincomeediticon",pageName, frameName);
	}

	public WebElement mobilemyincomeeditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilemyincomeediticon",pageName, frameName);
	}

	public List<WebElement> CheckBox() {
		return SeleniumUtil.getWebElements(d, "checkbx", pageName, frameName);
	}

	public List<WebElement> CategoryAmount() {
		return SeleniumUtil.getWebElements(d, "basedmonthdatatxt", pageName,frameName);
	}

	public WebElement diableamountamount1() {
		return SeleniumUtil.getVisibileWebElement(d, "diableamountamount",pageName, frameName);
	}

	public void typeCategoryValues(String categoryName, String value) {
		SeleniumUtil.waitForPageToLoad(1000);
		d.findElement(By.xpath("//input[contains(@id,'cat-amt') and contains(@aria-label,'"+ categoryName + "')]")).clear();
		SeleniumUtil.waitForPageToLoad(1000);
		d.findElement(By.xpath("//input[contains(@id,'cat-amt') and contains(@aria-label,'"+ categoryName + "')]")).sendKeys(value);
	}

	public List<WebElement> editCatAmount() {
		return SeleniumUtil.getWebElements(d, "EditactAmount", pageName, frameName);
	}

	public WebElement CategoryValues() {
		return SeleniumUtil.getVisibileWebElement(d, "CategoryValues1",
				pageName, frameName);
	}
	
	public WebElement RecuringBillamountamount1() {
		return SeleniumUtil.getVisibileWebElement(d,"RecuringBillamountamount", pageName, frameName);
	}
	public WebElement RecuringBillamountamount3() {
		return SeleniumUtil.getVisibileWebElement(d,"RecuringBillamountamount2", pageName, frameName);
	}
	public WebElement nextToStep3Button(){
		return SeleniumUtil.getWebElement(d, "nextToStep3Button", pageName, frameName);
	}
}
