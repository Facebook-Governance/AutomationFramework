/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CategorizationRules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.CategorizationRules.CategorizationRules_Loc;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class CategorizationRules_Loc {

	static Logger logger = LoggerFactory.getLogger(CategorizationRules_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public CategorizationRules_Loc(WebDriver d) {
		this.d = d;

	}
	public List<WebElement> getAmountTypeList() {
		return SeleniumUtil.getWebElements(d, "getAmountTypeList", "CategorzationRules", null);
	}
/*	private final String dropDown = ".ellipsis.name.text.text-css-level1";
	public List<WebElement> getAmountTypeList() {
		return d.findElements(By.cssSelector(dropDown));
	}
*/
	public List<WebElement> getCategoryLL() {
		return SeleniumUtil.getWebElements(d, "getCategoryLL", "CategorzationRules", null);
	}
	
/*	private final String categoryLL = ".header.level3-header.Paragraph1.label-css-level3.last-child.selectable";
	public List<WebElement> getCategoryLL() {
		return d.findElements(By.cssSelector(categoryLL));
	}

	public List<WebElement> getRrunButton()
		 {
			 return d.findElements(By.xpath(".//*[@id='run-single-rule']/span"));
		 }
		 
	private final String categoryHighL = ".header.level2-header.Paragraph1.label-css-level2";
	public List<WebElement> getCategoryHighL() {
		return d.findElements(By.cssSelector(categoryHighL));
	}
	
	private final String rule = "ruleVal";
	public List<WebElement> getRuleText() {
		//return d.findElements(By.id(""));
		return SeleniumUtil.getWebElements(d, "getRuleText", "", null);
	}*/
	public List<WebElement> getRrunButton() {
		return SeleniumUtil.getWebElements(d, "getRrunButton", "CategorzationRules", null);
	}
	public List<WebElement> getCategoryHighL() {
		return SeleniumUtil.getWebElements(d, "getCategoryHighL", "CategorzationRules", null);
	}
	
	public List<WebElement> getRuleText() {
		return SeleniumUtil.getWebElements(d, "getRuleText", "CategorzationRules", null);
	}
	
	public List<WebElement> getRuleTextForMobile() {
		return SeleniumUtil.getWebElements(d, "getRuleTextForMobile", "CategorzationRules", null);
	}
	
	
	public WebElement getSecondRule() {
		return SeleniumUtil.getWebElement(d, "getSecondRule", "CategorzationRules", null);
	}
	
	public List<WebElement> getCategoriesInTransaction() {
		return SeleniumUtil.getWebElements(d, "getCategoriesInTransaction", "CategorzationRules", null);
	}
	/*
	public WebElement getCategoriesInTransaction3() {
		return SeleniumUtil.getWebElement(d, "getCategoriesInTransaction3", "CategorzationRules", null);
	}
	
	public WebElement getSecondRule() {
		return d.findElement(By.xpath("//span[contains(text(),'BANK OF AM HAYWARD scheduled')]"));
	}
	
	private final String categories = ".small-8.smallplus-4.medium-8.large-4.columns.categoryContainer";
	public List<WebElement> getCategoriesInTransaction() {
		return d.findElements(By.cssSelector(categories));
	}
*/
	public WebElement getCategoriesInTransaction3(String tag) {
		return d.findElement(By.xpath("//div[contains(text(),'" + tag + "')]"));
	}

	public WebElement getCategoriesInTransaction1() {
		return SeleniumUtil.getWebElement(d, "getCategoriesInTransaction1", "CategorzationRules", null);
	}
		//return SeleniumUtil.waitUntilElementsVisible(d, "xpath", "//span[contains(text(),'$18.13')]", 8).get(0);//d.findElement(By.xpath(""));
	
	public WebElement getCategoriesInTransaction3() {
		return SeleniumUtil.waitUntilElementsVisible(d, "xpath", "//span[contains(text(),'$200.00')]", 8).get(0);//d.findElement(By.xpath("//span[contains(text(),'$200.00')]"));
	}

	public WebElement getCategoriesInTransaction2() {
		
		return d.findElement(By.xpath("//div[contains(text(),'Charitable Giving')]"));
	}
/*
	private final String up = ".//*[@id='move-up']/span";
	public List<WebElement> getUpPriority() {
		return d.findElements(By.xpath(up));
	}

	public WebElement getSingleRule() {
		return d.findElement(By.id("run-single-rule"));
	}

	private final String categoryIn = ".//*[@id='3']/div/span[1]";
	public List<WebElement> getcategoryInCharitable() {
		return d.findElements(By.xpath(categoryIn));
	}*/
	
//  Get Visible Elements Methods
	public List<WebElement> getUpPriority() {
		return SeleniumUtil.getWebElements(d, "getUpPriority", "CategorzationRules", null);
	}
	public WebElement getSingleRule() {
		return SeleniumUtil.getWebElement(d, "getSingleRule", "CategorzationRules", null);
	}
	public List<WebElement> getcategoryInCharitable() {
		return SeleniumUtil.getWebElements(d, "getcategoryInCharitable", "CategorzationRules", null);
	}
	public WebElement categorizationRules() {
		return SeleniumUtil.getVisibileWebElement(d, "categorizationRules", "CategorzationRules", null);
	}
		
	public WebElement createRules() {
		return SeleniumUtil.getVisibileWebElement(d, "createRules", "CategorzationRules", null);
	}
		
	public WebElement createRulesButton() {
		return SeleniumUtil.getWebElement(d, "createRulesButton", "CategorzationRules", null);
	}
	public WebElement createRulesButton1() {
		return SeleniumUtil.getWebElement(d, "createRulesButton1", "CategorzationRules", null);
	}
	
	public WebElement createRulesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createRuleBtn", "CategorzationRules", null);
	}
		
	
	
	public WebElement noRuleSym() {
		return SeleniumUtil.getVisibileWebElement(d, "noRuleSym", "CategorzationRules", null);
	}
		
	
	
	public WebElement createTextLine() {
		return SeleniumUtil.getVisibileWebElement(d, "createTextLine", "CategorzationRules", null);
	}
		
	
	public WebElement createPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "createPopUp", "CategorzationRules", null);
	}
	    
	
	public WebElement applyRuleText() {
		return SeleniumUtil.getVisibileWebElement(d, "applyRuleText", "CategorzationRules", null);
	}
	    
	
	public WebElement ifTransText() {
		return SeleniumUtil.getVisibileWebElement(d, "ifTransText", "CategorzationRules", null);
	}
	   
	
	public WebElement descTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "descTextBox", "CategorzationRules", null);
	}
	    
	
	public WebElement ifAmtText() {
		return SeleniumUtil.getVisibileWebElement(d, "ifAmtText", "CategorzationRules", null);
	}
		
	
	public WebElement amtDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "amtDropDown", "CategorzationRules", null);
	}
		
    public WebElement amtDescBox() {
			return SeleniumUtil.getVisibileWebElement(d, "amtDescBox", "CategorzationRules", null);
		}
		
   public WebElement categoriseAsText() {
			return SeleniumUtil.getVisibileWebElement(d, "categoriseAsText", "CategorzationRules", null);
		}
	
    public WebElement categoriesDropDown() {
			return SeleniumUtil.getVisibileWebElement(d, "categoriesDropDown", "CategorzationRules", null);
		}
		
	public WebElement applyRulePastTransac() {
			return SeleniumUtil.getVisibileWebElement(d, "applyRulePastTransac", "CategorzationRules", null);
		}
		
	public WebElement ibuttn() {
			return SeleniumUtil.getVisibileWebElement(d, "ibuttn", "CategorzationRules", null);
		}
		
	public WebElement createClosePopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "createClosePopUp", "CategorzationRules", null);
	}	
	
		
	public WebElement createRuleIdButtn() {
			return SeleniumUtil.getVisibileWebElement(d, "createRuleIdButtn", "CategorzationRules", null);
		}
		
	public WebElement transSwitchToggle() {
			return SeleniumUtil.getVisibileWebElement(d, "transSwitchToggle", "CategorzationRules", null);
		}
	public WebElement transSwitchValidation() {
			return SeleniumUtil.getVisibileWebElement(d, "transSwitchValidation", "CategorzationRules", null);
		}
		
		
	public WebElement amtSwitchToggle() {
			return SeleniumUtil.getVisibileWebElement(d, "amtSwitchToggle", "CategorzationRules", null);
		}

		
		
	public WebElement amtSwitchValidation() {
		return SeleniumUtil.getVisibileWebElement(d, "amtSwitchValidation", "CategorzationRules", null);
	}  
	    
	
	public WebElement applyRuleSwitchToggle() {
		return SeleniumUtil.getVisibileWebElement(d, "applyRuleSwitchToggle", "CategorzationRules", null);
	}
	    
	
	public WebElement applyRuleSwitchValidation() {
		return SeleniumUtil.getVisibileWebElement(d, "applyRuleSwitchValidation", "CategorzationRules", null);
	}
		
	
	public WebElement amtDropDownPlaceText() {
		return SeleniumUtil.getVisibileWebElement(d, "amtDropDownPlaceText", "CategorzationRules", null);
	}
	    
	
	public WebElement exactlySelected() {
		return SeleniumUtil.getVisibileWebElement(d, "exactlySelected", "CategorzationRules", null);
	}
		
		
	public WebElement betweenTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "betweenTextBox", "CategorzationRules", null);
	}
		
	
	public WebElement betweenAndText() {
		return SeleniumUtil.getVisibileWebElement(d, "betweenAndText", "CategorzationRules", null);
	}
		
	
	public WebElement betweenTicSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "betweenTicSelected", "CategorzationRules", null);
	}
	
	public WebElement travelTick() {
		return SeleniumUtil.getWebElement(d, "travelTick", "CategorzationRules", null);
	}
		
		
	public WebElement addNewRule() {
		return SeleniumUtil.getVisibileWebElement(d, "addNewRule", "CategorzationRules", null);
	}	
	
	public WebElement runAll() {
		return SeleniumUtil.getVisibileWebElement(d, "runAll", "CategorzationRules", null);
	}
		
	
	public WebElement createNewRule() {
		return SeleniumUtil.getVisibileWebElement(d, "createNewRule", "CategorzationRules", null);
	}
	
	
	public WebElement createNewRule2() {
		return SeleniumUtil.getVisibileWebElement(d, "createNewRule2", "CategorzationRules", null);
	}
		
	
	public WebElement RrunButton() {
		return SeleniumUtil.getVisibileWebElement(d, "RrunButton", "CategorzationRules", null);
	}
		
	
	public WebElement SaveBttn() {
		return SeleniumUtil.getWebElement(d, "SaveBttn", "CategorzationRules", null);
	}
		
	
	public WebElement SaveChgBttn() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveChgBttn", "CategorzationRules", null);
	}
		
	public WebElement CategoryDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "CategoryDropDown", "CategorzationRules", null);
	}
		
	
	public WebElement invalidAmtText() {
		return SeleniumUtil.getVisibileWebElement(d, "invalidAmtText", "CategorzationRules", null);
	}
	
	public WebElement invalidAmtText1() {
		return SeleniumUtil.getVisibileWebElement(d, "invalidAmtText1", "CategorzationRules", null);
	}
	
		
	
	public WebElement posted2() {
		return SeleniumUtil.getVisibileWebElement(d, "posted2", "CategorzationRules", null);
	}
		
	
	public WebElement invalidBetweeenAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "invalidBetweeenAmt", "CategorzationRules", null);
	}
		
	
	public List<WebElement> dropdwnOption() {
		return SeleniumUtil.getWebElements(d, "dropdwnOption", "CategorzationRules", null);
	}
		
	
	public WebElement dropdwnOPtionDelete() {
		return SeleniumUtil.getVisibileWebElement(d, "dropdwnOPtionDelete", "CategorzationRules", null);
	}
				
		
		
	public WebElement dropdwnOPtionEdit() {
		return SeleniumUtil.getVisibileWebElement(d, "dropdwnOPtionEdit", "CategorzationRules", null);
	}
		
	
	public WebElement deletPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUp", "CategorzationRules", null);
	}
	   
	
	public WebElement deletPopUpClose() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUpClose", "CategorzationRules", null);
	}
		
	
	public WebElement deletPopUpText() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUpText", "CategorzationRules", null);
	}
		
	
	public WebElement deletPopUpStatement() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUpStatement", "CategorzationRules", null);
	}
	    
	
	public WebElement deletPopUpCancel() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUpCancel", "CategorzationRules", null);
	}
		
	
	public WebElement deletPopUpDelete() {
		return SeleniumUtil.getVisibileWebElement(d, "deletPopUpDelete", "CategorzationRules", null);
	}
										

	public WebElement editPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "editPopUp", "CategorzationRules", null);
	}										
	    
	public WebElement header() {
		return SeleniumUtil.getVisibileWebElement(d, "header", "CategorzationRules", null);
	}
		
	
	public WebElement createRuleBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createRuleBtn", "CategorzationRules", null);
	}
		
	
	public WebElement createRulePopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "createRulePopUp", "CategorzationRules", null);
	}
		
	
	public WebElement popUpDesc() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpDesc", "CategorzationRules", null);
	}
		
	
	public WebElement popUpDescContains() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpDescContains", "CategorzationRules", null);
	}
												

	    
	public WebElement popUpToggleBtn1() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpToggleBtn1", "CategorzationRules", null);
	}
		
	
	public WebElement popUpDescBox() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpDescBox", "CategorzationRules", null);
	}
	    
	
	public WebElement popUpTransAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpTransAmt", "CategorzationRules", null);
	}
	    
	
	public WebElement popUpToggleBtn2() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpToggleBtn2", "CategorzationRules", null);
	}	
	
	public WebElement popUpAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpAmt", "CategorzationRules", null);
	}
	    
	
	public WebElement popUpAmtExactly() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpAmtExactly", "CategorzationRules", null);
	}								

	public WebElement popUpThenCategorizeTrans() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpThenCategorizeTrans", "CategorzationRules", null);
	}
		
	public WebElement popUpSelectCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpSelectCategory", "CategorzationRules", null);
	}
		
	public WebElement popUpApplyRule() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpApplyRule", "CategorzationRules", null);
	}
	   
	
	public WebElement popUpToggleBtn3() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpToggleBtn3", "CategorzationRules", null);
	}
	
	
	public WebElement closeCategoriesDDForMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "closeCategoriesDDForMobile", "CategorzationRules", null);
	}
	
	public WebElement moreButtonForMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "moreButtonForMobile", "CategorzationRules", null);
	}
	public WebElement createRuleInfoMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "createRuleInfoMessage", "CategorzationRules", null);
	}
	public boolean iscatgclosebtn()
	{
		return PageParser.isElementPresent("catgclosebtn", "CategorzationRules", null);
	}

	
	public WebElement catgclosebtn() {
		return SeleniumUtil.getVisibileWebElement(d, "catgclosebtn", "CategorzationRules", null);

	}
}
