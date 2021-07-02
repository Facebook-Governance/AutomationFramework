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
public class Account_Loc{
	
	public static String pageName="DashboardPage";
	public static String frameName=null;
	public WebDriver d=null;
	
	public Account_Loc(WebDriver d)
	{
		this.d = d;
	}

	public WebElement dashBoardHeader() {
		//return SeleniumUtil.getWebElement(d, "header1", pageName, frameName);
		return d.findElement(By.id("mobileTitle"));
	}
	public WebElement todaysTraninDash() {

		return SeleniumUtil.getWebElement(d, "todaysTraninDash",  pageName, frameName);
	}
	
	
	public List<WebElement> AccnameListinAccSet() {

		return SeleniumUtil.getWebElements(d, "AccnameListinAccSet",  pageName, frameName);
	}
	public WebElement Account_Balance_Dash() {
		return SeleniumUtil.getWebElement(d, "Account_Balance_Dash", pageName, frameName);
	}
	
	public WebElement ClickonFirstAcc() {
		return SeleniumUtil.getWebElement(d, "ClickonFirstAcc", pageName, frameName);
	}
	public WebElement Account_Type_Dash() {
		return SeleniumUtil.getWebElement(d, "Account_Type_Dash", pageName, frameName);
	}
	public WebElement accountsHeader() {
		return SeleniumUtil.getWebElement(d, "header2", pageName, frameName);
	}
	public WebElement ByAccountTypeText() {
		return SeleniumUtil.getWebElement(d, "text", pageName, frameName);
	}
	public WebElement linkAnotherAccountButton() {
		return SeleniumUtil.getWebElement(d, "btn", pageName, frameName);
	}
	public WebElement continueNonIQBankText() {
		return SeleniumUtil.getWebElement(d, "txt", pageName, frameName);
	}
	public WebElement learnMoreText() {
		return SeleniumUtil.getWebElement(d, "txt1", pageName, frameName);
	}
	public List<WebElement> checkNickNameinAccFin() {
		return SeleniumUtil.getWebElements(d, "checkNickNameinAccFin", pageName, frameName);
	}
	
	public List<WebElement> AlertTogOn() {

		return SeleniumUtil.getWebElements(d, "AlertTogOn",  pageName, frameName);
	}
	public List<WebElement> AlertSettingsMenu() {
		return SeleniumUtil.getWebElements(d, "AlertSettingsMenu", pageName, frameName);
	}
	
	
	public WebElement CancelinAlertSet() {
		return SeleniumUtil.getWebElement(d, "CancelinAlertSet", pageName, frameName);
	}
	
	public List<WebElement> nickNameList() {
		return SeleniumUtil.getWebElements(d, "nickNameList", pageName, frameName);
	}
	public WebElement DropDownIcon() {
		return SeleniumUtil.getWebElement(d, "icon", pageName, frameName);
	}
	public WebElement cashText() {
		return SeleniumUtil.getWebElement(d, "cash", pageName, frameName);
	}
	public WebElement dropdown() {
		return SeleniumUtil.getWebElement(d, "dropdown1", pageName, frameName);
	}
	public WebElement Amount(){
		return SeleniumUtil.getWebElement(d, "amount", pageName, frameName);
	}
	public List<WebElement> Amount1(){
		return SeleniumUtil.getWebElements(d, "amount1", pageName, frameName);
	}
	public List<WebElement> accountType(){
		return SeleniumUtil.getWebElements(d, "accounttype", pageName, frameName);
	}
	
	public List<WebElement>DashInvestCont()
	{
		return SeleniumUtil.getWebElements(d, "DashInvestCont", pageName, frameName);
	}
	public List<WebElement> DashboardFin(){
		return SeleniumUtil.getWebElements(d, "DashboardFin", pageName, frameName);
	}
	public List<WebElement> CashAmount() {
		return SeleniumUtil.getWebElements(d, "amt", pageName, frameName);
	}
	public WebElement CashAmount1() {
		return SeleniumUtil.getWebElement(d, "amt1", pageName, frameName);
	}
	public WebElement CashAmount2() {
		return SeleniumUtil.getWebElement(d, "amt2", pageName, frameName);
	}
	public WebElement CashAmount3() {
		return SeleniumUtil.getWebElement(d, "amt3", pageName, frameName);
	}
	public List<WebElement> accountType1(){
		return SeleniumUtil.getWebElements(d, "acc", pageName, frameName);
	}
	public List<WebElement> accountName(){
		return SeleniumUtil.getWebElements(d, "acc1", pageName, frameName);
	}
	public WebElement viewAllAccountButton() {
		return SeleniumUtil.getWebElement(d, "bttn", pageName, frameName);
	}
	public WebElement accountsheader() {
		return SeleniumUtil.getWebElement(d, "header", pageName, frameName);
	}

	public WebElement ContinueButton() {
		return SeleniumUtil.getWebElement(d, "button", pageName, frameName);
	}
	public WebElement GoToInvestmentButton() {
		return SeleniumUtil.getWebElement(d, "buttn", pageName, frameName);
	}
	
	public WebElement expand_All() {
		return SeleniumUtil.getWebElement(d, "expand_All", pageName, frameName);
	}
	
	public WebElement accountDropdown() {
		return SeleniumUtil.getWebElement(d, "accountDropdown", pageName, frameName);
	}
	public List<WebElement> DashBoard_AccountTypeList() {
		return SeleniumUtil.getWebElements(d, "DashBoard_AccountTypeList", pageName, frameName);
	}
	
	public WebElement DashBoard_AccountType(String accountTypeIndex) {
		 String xpath=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "DashBoard_AccountType");
	        xpath=xpath.replaceAll("AccountTypeIndex", accountTypeIndex);
	        return d.findElement(By.xpath(xpath));
	}
	public List<WebElement> DashBoard_AccountSiteName(String accountTypeIndex) {
		 String xpath=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "DashBoard_AccountSiteName");
	        xpath=xpath.replaceAll("AccountTypeIndex", accountTypeIndex);
	        return d.findElements(By.xpath(xpath));
	}
	public List<WebElement> DashBoard_AccountName(String accountTypeIndex) {
		 String xpath=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "DashBoard_AccountName");
	        xpath=xpath.replaceAll("AccountTypeIndex", accountTypeIndex);
	        return d.findElements(By.xpath(xpath));
	}
	public List<WebElement> DashBoard_AccountBal(String accountTypeIndex) {
		 String xpath=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "DashBoard_AccountBal");
	        xpath=xpath.replaceAll("AccountTypeIndex", accountTypeIndex);
	        return d.findElements(By.xpath(xpath));
	}
}
