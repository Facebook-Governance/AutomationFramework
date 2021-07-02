/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

import groovy.json.internal.Exceptions;

public class Transaction_DashBoard_Integration_Loc {
	static Logger logger = LoggerFactory.getLogger(Transaction_DashBoard_Integration_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;

	public Transaction_DashBoard_Integration_Loc(WebDriver d) {
		this.d = d;
	}

	
	public List<WebElement> listaccountRow(){
		return SeleniumUtil.getWebElements(d, "acc", pageName, frameName);
	}
	public boolean isDashBoardTransactionExpandIconPresent()
	{
		return PageParser.isElementPresent("DashBoardTransactionExpandIcon", pageName, frameName);
	}
	
	public WebElement DashBoardTransactionExpandIcon() {
		return SeleniumUtil.getWebElement(d, "DashBoardTransactionExpandIcon", pageName, frameName);
	}
	public List<WebElement> listaccountRow1(){
		return SeleniumUtil.getWebElements(d, "acc1", pageName, frameName);
	}

	
	public List<WebElement> accountListName(){
		return SeleniumUtil.getWebElements(d, "ListAcname", pageName, frameName);
	}

	public WebElement dashBoardAccountName(String accountName){
		String acctName=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "dashBoardAccountName");
		acctName=acctName.replaceAll("acctName", accountName);
		return d.findElement(By.xpath(acctName));
	}

	public WebElement backToDashBoardText() {
		return SeleniumUtil.getWebElement(d, "bk", pageName, frameName);
	}

	
	public WebElement backToDashBoardIcon() {
		return SeleniumUtil.getWebElement(d, "bkicon", pageName, frameName);
	}
	public WebElement dashBoardTxnsWidget() {
		return SeleniumUtil.getWebElement(d, "dashBoardTxnsWidget", pageName, frameName);
	}
	
	
	public WebElement transactionAcc() {
		return SeleniumUtil.getWebElement(d, "Taracc", pageName, frameName);
	}

	
	public WebElement dashBoardHeader() { 
		return SeleniumUtil.getVisibileWebElement(d, "dash", pageName, frameName);
	}

	
	public List<WebElement> recentTransaction(){
		return SeleniumUtil.getWebElements(d, "rec", pageName, frameName);
	}
	
	
	
	public WebElement recentTransactionHeader() { 
		return SeleniumUtil.getVisibileWebElement(d, "recentTransactionHeader", pageName, frameName);
	}
	public List<WebElement> recentTransactionHeaderList() { 
		return SeleniumUtil.getWebElements(d, "recentTransactionHeader", pageName, frameName);
	}
	public List<WebElement> recentTransactionRow(){
		return SeleniumUtil.getWebElements(d, "recentTransactionRow", pageName, frameName);
	}
	public List<WebElement> dashBoardTxnsAmount(){
		return SeleniumUtil.getWebElements(d, "dashBoardTxnsAmount", pageName, frameName);
	}
	
	
	public String CurrentdateFormate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.DATE, 0);
		System.out.println(formatter.format(c2.getTime()));
		return formatter.format(c2.getTime());
	}
	public String currentdateFormate(int date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.DATE, date);
		System.out.println(formatter.format(c2.getTime()));
		return formatter.format(c2.getTime());
	}
	
	public void clickAccountRow(String acctName)
	{
	   WebDriverWait wait=new WebDriverWait(d, 500);
	   SeleniumUtil.waitForPageToLoad();
	   WebElement element=wait.until(ExpectedConditions.visibilityOf(dashBoardAccountName(acctName)));
       SeleniumUtil.click(element);
       wait.until(ExpectedConditions.visibilityOf(transactionAcc()));
       
	}
	
	public void clickbackTodashBoard()
	{
		
		  WebDriverWait wait=new WebDriverWait(d, 100);
		   WebElement element=wait.until(ExpectedConditions.visibilityOf(backToDashBoardIcon()));
		   SeleniumUtil.click(element);
		   wait.until(ExpectedConditions.visibilityOf(dashBoardHeader()));
		
	}
	
	public void clickTxnWidget()
	{
		WebDriverWait wait=new WebDriverWait(d, 100);
		WebElement element=wait.until(ExpectedConditions.visibilityOf(dashBoardTxnsWidget()));
		   SeleniumUtil.click(element);
		   wait.until(ExpectedConditions.visibilityOf(dashBoardHeader()));
		
	}
	
	public List<WebElement> dashBoardTxnsAccountDescription()
	{
		return SeleniumUtil.getWebElements(d, "dashBoardTxnsAccountDescription", pageName, frameName);
	}
	public List<WebElement> dashBoardTxnsAccountNum()
	{
		return SeleniumUtil.getWebElements(d, "dashBoardTxnsAccountNum", pageName, frameName);
	}
	public List<WebElement> dashBoardTxnsAccountBal()
	{
		return SeleniumUtil.getWebElements(d, "dashBoardTxnsAccountBal", pageName, frameName);
	}
	public WebElement TransactionFinappHeader()
	{
		return SeleniumUtil.getWebElement(d, "TransactionFinappHeader", pageName, frameName);
	}
	public WebElement TransactionFinappAccountDetailsHeaderName()
	{
		return SeleniumUtil.getWebElement(d, "TransactionFinappAccountDetailsHeaderName", pageName, frameName);
	}
	public WebElement TransactionFinappAccountDetailsHeaderNum()
	{
		return SeleniumUtil.getWebElement(d, "TransactionFinappAccountDetailsHeaderNum", pageName, frameName);
	}
}
