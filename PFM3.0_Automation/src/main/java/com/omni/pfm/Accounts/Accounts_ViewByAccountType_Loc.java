/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ViewByAccountType_Loc extends SeleniumUtil {
	Logger logger=LoggerFactory.getLogger(Accounts_ViewByAccountType_Loc.class);
	public WebDriver d=null;
	static String pageName="AccountsPage";
	static String frameName=null;
	
   public Accounts_ViewByAccountType_Loc(WebDriver d) {
	   this.d=d;
   }
   
   public WebElement accountType() {
		return getWebElement(d, "accountType", pageName, frameName);
	}
   public List<WebElement> containeNameAcntType() {
		return getWebElements(d, "containeNameAcntType", pageName, frameName);
	}
   public WebElement accBlncSum1() {
		return getWebElement(d, "accBlncSum1", pageName, frameName);
	}
   public List<WebElement> expandedArrow() {
		return getWebElements(d, "expandedArrow", "AccountsPage", null);
	}
	public List<WebElement> collapseArrow() {
		return getWebElements(d, "collapseArrow", "AccountsPage", null);
	}
	public List<WebElement> dateCard() {
		return getWebElements(d, "DateCard", pageName, frameName);
	}
	public WebElement getBalance(int ContainerNum, int SubContainerNum, int AccountContainer){
		String xpath= "//div[@id='accounts-layout']/div["+ContainerNum+"]//div[@id='account-container-view-wrap']/div["+SubContainerNum+"]//div[contains(@class,'accounts-row-wrap')]["+AccountContainer+"]//span[contains(@class,'account-balance')]";
		return d.findElement(By.xpath(xpath));

		}
	public WebElement AccountTypeView_AccountsTypeName(String acctTypeIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountsTypeName");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
	
		return d.findElement(By.xpath(getXpath));
	}
	public List<WebElement> AccountTypeView_AccountsTypeNameList() {
		return getWebElements(d, "AccountTypeView_AccountsTypeNameList", pageName, frameName);
	}
	public WebElement AccountTypeView_AccountName(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountName");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	}
	public WebElement AccountTypeView_AccountSiteName(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountSiteName");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	}
	public String returnAccountNumber(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountNum");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		moveToElement(d.findElement(By.xpath(getXpath)));
		waitFor(1);
		String titleAttribute = getAttribute(By.xpath(getXpath),"title");
		String accountNumber = titleAttribute.isEmpty() ? getText(By.xpath(getXpath)) : titleAttribute;
		logger.info("Account number :: " + accountNumber);
		return accountNumber;
	}
	public WebElement AccountTypeView_AccountBal(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountBal");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	}
	public WebElement AccountTypeView_AccountNickName(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountNickName");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	}
	public WebElement AccountTypeView_AccountMoreBtn(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountMoreBtn");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	}
	public WebElement AccountTypeView_AccountMoreMenu(String acctTypeIndex,String acctNameIndex,String moreMenuName) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountMoreMenu");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		getXpath=getXpath.replaceAll("menuName", moreMenuName);
		return d.findElement(By.xpath(getXpath));
	}
	public List<WebElement> AccountFI_AccountType_AccountNumMobile(String AccountType) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountFI_AccountType_AccountNumMobile");
		getXpath=getXpath.replaceAll("acctTypeIndex", AccountType);
		return d.findElements(By.xpath(getXpath));
		
	} 
	public WebElement AccountTypeView_AccountNumMobile(String acctTypeIndex,String acctNameIndex) {
		String getXpath=getVisibileElementXPath(d, pageName, frameName, "AccountTypeView_AccountNumMobile");
		getXpath=getXpath.replaceAll("acctTypeIndex", acctTypeIndex);
		getXpath=getXpath.replaceAll("acctNameIndex", acctNameIndex);
		return d.findElement(By.xpath(getXpath));
	} 
	
}
