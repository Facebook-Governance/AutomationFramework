/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.TEACABugs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.SeleniumUtil;

public class TEACABugs_Loc {

	private static WebDriver d;
	private static final Logger logger = LoggerFactory.getLogger(TEACABugs_Loc.class);
	static final String frameName=null;
	
	public TEACABugs_Loc(WebDriver d)
	{
		this.d=d;
	}


	public WebElement SubCategory_ErrorText() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"SubCategory_ErrorText", "Categories", null);
	}
	public WebElement Amount_ErrorText() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"Amount_ErrorText", "Categories", null);
	}
	public WebElement UncategorizeOption() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"UncategorizeOption", "Categories", null);
	}
	
	public WebElement GrpAccountsCheckbx() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"GrpAccountsCheckbx", "InvestmentHoldings", null);
	}
	
	public WebElement Closeicon() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"Closeicon", "InvestmentHoldings", null);
	}
	
	public WebElement DonutChart() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"DonutChart", "InvestmentHoldings", null);
	}
	
	public WebElement ViewAllAcc() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"ViewAllAcc", "DashboardPage", null);
	}
	
	public WebElement FirstRowContainer() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"FirstRowContainer", "AccountsPage", null);
	}
	public WebElement CurrencyValues() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"CurrencyValues1", "AccountsPage", null);
	}
	public WebElement getCurrency_MAS() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"getCurrency_MAS", "AccountsPage", null);
	}
	
	public WebElement more() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"more", "Transaction", null);
	}
	public WebElement manage() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"manage", "Transaction", null);
	}
	public WebElement getHeaderText() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"getHeaderText", "Categories", null);
	}
	public WebElement expandCollapseButton() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"expandCollapseButton", "InvestmentHoldings", null);
	}
	public WebElement FirstRowHolding() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"FirstRowHolding", "InvestmentHoldings", null);
	}
	public WebElement PerfChartTimeDisplay() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"PerfChartTimeDisplay", "InvestmentHoldings", null);
	}
	public WebElement AssetsCheckbox() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"AssetsCheckbox", "NetWorth", null);
	}
	public WebElement LiabilitiesCheckbox() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"LiabilitiesCheckbox", "NetWorth", null);
	}
	public WebElement AssetsChart() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"AssetsChart", "NetWorth", null);
	}
	public WebElement NWValueStartingMonth() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"NWValueStartingMonth", "NetWorth", null);
	}
	
	public WebElement ChangeAmount() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"ChangeAmount", "NetWorth", null);
	}
	
	public WebElement LiabilitiesChart() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"LiabilitiesChart", "NetWorth", null);
	}
	public WebElement TableLink() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"TableLink", "NetWorth", null);
	}	public WebElement ChartLInk() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"ChartLInk", "NetWorth", null);
	}
	public WebElement ManualSettingLink()
	{
		return SeleniumUtil.getVisibileWebElement(d,"ManualSettingLink", "AccountSettings", null);
	}
	public WebElement SettingsIconinAccSetOpt()
	{
		return SeleniumUtil.getVisibileWebElement(d,"SettingsIconinAccSetOpt", "AccountsPage", null);
	}
	public WebElement AlertSettingLinkAcc()
	{
		return SeleniumUtil.getVisibileWebElement(d,"AlertSettingLinkAcc", "AccountSettings", null);
	}

	public WebElement Dropdownvalue() {				
				return  SeleniumUtil.getVisibileWebElement(d, "dropdownvalue_CLP", "CashFlow", null);
			} 

	
	public WebElement AccountType() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountType", "AccountSettings", null);
	} 
	
	public WebElement Clickscheduledropdown() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"Clickscheduledropdown", "AlertSetting", null);
	}
	
	public WebElement expsummarylabel() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"expsummarylabel", "AlertSetting", null);
	}
	
	public WebElement amtlimitbudget() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"amtlimitbudget", "Budget", null);
	}
	
	public WebElement amtlimitincomebudget() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"amtlimitincomebudget", "Budget", null);
	}
	
	public WebElement CancelButton_CB() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"CancelButton_CB", "Budget", null);
	}
	
	public   List<WebElement> scheduletypeDropDown() {
        
        return SeleniumUtil.getWebElements (d, "scheduletypeDropDown", "AlertSetting", null);
	}
        public WebElement subCat()
    	{
    		return SeleniumUtil.getWebElement(d, "subcat","Transaction" , null);
    	}
    	
    	public WebElement autoHLC() {
    		return SeleniumUtil.getVisibileWebElement(d, "Auto_hlc", "Expense", null);
    	}
    	
    	public WebElement autoMLC() {
    		return SeleniumUtil.getVisibileWebElement(d, "Auto_mlc", "Expense", null);
    	}
    	
    	public WebElement skipSplash() {
    		return SeleniumUtil.getWebElement(d, "splash", "DashboardPage", null);
    	}
    	
    	public WebElement addAccountonSplash() {
    		return SeleniumUtil.getWebElement(d, "linkAccount_splash", "DashboardPage", null);
    	}
    	public WebElement AlertExpCont() 
		{
			return SeleniumUtil.getVisibileWebElement(d,"AlertExpCont", "AlertSetting", null);
		}

    	public WebElement MultilevelAcc() {
            return SeleniumUtil.getVisibileWebElement(d, "multilevlacc", "AccountSettings", null);
        }
    	
    	
        public WebElement Quetion1() {
            return SeleniumUtil.getVisibileWebElement(d, "Quetion1", "AccountSettings", null);
    }
        public WebElement Quetion2() {
            return SeleniumUtil.getVisibileWebElement(d, "Quetion2", "AccountSettings", null);
    }
        public WebElement Quetion3() {
            return SeleniumUtil.getVisibileWebElement(d, "Quetion3", "AccountSettings", null);
    }

        public WebElement Seckey() {
            return SeleniumUtil.getVisibileWebElement(d, "Seckey", "AccountSettings", null);
    }
        
        
        public WebElement EditText() {
    		return SeleniumUtil.getVisibileWebElement(d, "editText", "AccountSettings", null);
    	}
        public WebElement EditPopUpTitle() {
    		return SeleniumUtil.getVisibileWebElement(d, "editPopUpTitle", "AccountSettings", null);
    	}

        
        public WebElement PasswordText() {
    		return SeleniumUtil.getVisibileWebElement(d, "passwordText", "AccountSettings", null); 
    	}

    	public WebElement ReEnterPasswordText() {
    		return SeleniumUtil.getVisibileWebElement(d, "reEnterPasswordText", "AccountSettings", null);
    	}
    	public WebElement UpdatedButton() {
    		return SeleniumUtil.getVisibileWebElement(d, "Update", "AccountSettings", null);
    	}
    	public  List<WebElement> getCurrentBalance() {
    		return SeleniumUtil.getWebElements(d, "getCurrentBalance", "AccountsPage", null);
    	}
    	
    	public WebElement SubCategories() {
    		return SeleniumUtil.getVisibileWebElement(d, "getMLCName_MC", "Categories", null);
    	}
    	
    	public WebElement SaveChangesCategories() {
    		return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtn", "Categories", null);
    	}
    	
    	public WebElement GasolineCategory() {
    		return SeleniumUtil.getVisibileWebElement(d, "GasolineCategory", "Categories", null);
    	}
    	
    	public WebElement EditBudgetPopup() {
    		return SeleniumUtil.getVisibileWebElement(d, "EditBudgetPopup", "Budget", null);
    	}
    	public WebElement DeleteButton() {
    		return SeleniumUtil.getVisibileWebElement(d, "DeleteButton", "Budget", null);
    	}
    	public WebElement CancelButton1() {
    		return SeleniumUtil.getVisibileWebElement(d, "CancelButton1", "Budget", null);
    	}
    	public WebElement FirstItemContainer() {
    		return SeleniumUtil.getVisibileWebElement(d, "FirstItemContainer", "Budget", null);
    	}
    	public WebElement ExpandedCategory() { 
    		return SeleniumUtil.getVisibileWebElement(d, "ExpandedCategory", "Budget", null);
    	}
    	public WebElement BudgetHeader_BNA() { 
    		return SeleniumUtil.getVisibileWebElement(d, "BudgetHeader_BNA", "Budget", null);
    	}
       	public  WebElement BackButton() {
    		return SeleniumUtil.getWebElement(d, "BackButton", "Budget", null);
    	} 
       	
       	public  WebElement update_ATD() {
    		return SeleniumUtil.getWebElement(d, "update_ATD", "Transaction", null);
    	} 
       	
    	public WebElement ErrorText() {
    		return SeleniumUtil.getVisibileWebElement(d, "ErrorText", "Budget", null);
    	}
    	
    	
    	public WebElement EditContainer() {
    		return SeleniumUtil.getVisibileWebElement(d, "EditContainer", "Budget", null);
    	}
    	public WebElement BudgetAmtEdit() {
    		return SeleniumUtil.getVisibileWebElement(d, "BudgetAmtEdit", "Budget", null);
    	}	
    	public WebElement BudgetAddAmtSaveDisabled() {
    		return SeleniumUtil.getVisibileWebElement(d, "BudgetAddAmtSaveDisabled", "Budget", null);
    	}
    	
    	public WebElement BudgetEditAmtSaveDisabled() {
    		return SeleniumUtil.getVisibileWebElement(d, "BudgetEditAmtSaveDisabled", "Budget", null);
    	}
    	    	
    	
    	
}