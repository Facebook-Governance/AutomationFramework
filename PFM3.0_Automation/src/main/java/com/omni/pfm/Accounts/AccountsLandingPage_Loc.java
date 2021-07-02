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

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsLandingPage_Loc extends TestBase{
	Logger logger=LoggerFactory.getLogger(AccountsLandingPage_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;
	
	public AccountsLandingPage_Loc(WebDriver d) {
		this.d = d;
	}
	public WebElement LinkActBtn() {
		return SeleniumUtil.getWebElement(d, "linkActBtn", "AccountsPage", null);
	}
	public List<WebElement> AccountsHighLevelContainers() {
		return SeleniumUtil.getWebElements(d, "AccountsHighLevelContainers", pageName, null);
	}
	public List<WebElement> AccountsViewList() {
		return SeleniumUtil.getWebElements(d, "AccountsViewList", pageName, null);
	}
	public WebElement AccountsPageHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsPageHeader", pageName, null);
	}
	public WebElement AccountsRefreshAllBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsRefreshAllBtn", pageName, null);
	}
	public WebElement AccountsRefreshAllIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsRefreshAllIcon", pageName, null);
	}
	public WebElement AccountsMoreButton() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsMoreButton", pageName, null);
	}
	public WebElement AccountsViewByLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsViewByLabel", pageName, null);
	}
	public WebElement AccountsLinkAnAcntBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsLinkAnAcntBtn", pageName, null);
	}	
	public boolean isLinkAnAcntBtnDisplayed(){
		return AccountsLinkAnAcntBtn().isDisplayed();
	}
	public boolean isAccountsLinkAnAcntBtnMobile() {
		return PageParser.isElementPresent("AccountsLinkAnAcntBtnMobile", pageName, null);
	}
	public WebElement AccountsLinkAnAcntBtnMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsLinkAnAcntBtnMobile", pageName, null);
	}
	public boolean isLinkAnAcntBtnMobileDisplayed(){
		return AccountsLinkAnAcntBtnMobile().isDisplayed();
	}
	public void clickOnAcntTypeView(){
		SeleniumUtil.click(AccountsViewList().get(1));
	}
	public void clickOnFiView(){
		SeleniumUtil.click(AccountsViewList().get(0));
	}
	public void clickOnAcntGroupView(){
		SeleniumUtil.click(AccountsViewList().get(2));
	}
	public boolean verifySideBarValues() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsViewList", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements("sidebar_values_accounts", l);
		return status;
	}
	public List<WebElement> VisibleOptionsUnderMoreBtn() {
		return SeleniumUtil.getWebElements(d, "VisibleOptionsUnderMoreBtn", pageName, null);
	}
	public WebElement VisibleOptionsUnderMoreBtnLabel(String label)
	{
		String xpath=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "VisibleOptionsUnderMoreBtnLabel");
		xpath=xpath.replaceAll("settingsLabel", label);
		return d.findElement(By.xpath(xpath));
	}
	public void clickMoreOptionLabel(String label)
	{
		SeleniumUtil.click(this.VisibleOptionsUnderMoreBtnLabel(label));
	}
	public void clickOnMoreBtn(){
		SeleniumUtil.click(AccountsMoreButton());
		SeleniumUtil.waitForPageToLoad(7000);
	}
	public List<WebElement> accountLevelRefresh() {
		return SeleniumUtil.getWebElements(d, "AcntLevelRefreshIcon", pageName, frameName);
	}
	public boolean verifyVisibleOptionsUnderMore(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "VisibleOptionsUnderMoreBtn", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements(propValue, l);
		return status;
	}
	public boolean verifyVisibleOptionsUnderMoreMobile(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "VisibleOptionsUnderMoreBtn", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements(propValue, l);
		return status;
	}
	
	public void clickingOnCreateGroupBtnUnderMore(){
		SeleniumUtil.waitForPageToLoad(4000);
		clickOnMoreBtn();
		if(appFlag.equals("app")||appFlag.equals("emulator")) {
			SeleniumUtil.click(VisibleOptionsUnderMoreBtn().get(1));
		}else {
			SeleniumUtil.click(VisibleOptionsUnderMoreBtn().get(0));
		}		
	}
	
	public WebElement partialErrorAcntsLabel(){
		return SeleniumUtil.getWebElement(d, "partialErrorAcntsLabel", pageName, frameName);	
	}
	public WebElement partialErrorAcntsDeleteBtn(){
		return SeleniumUtil.getWebElement(d, "partialErrorAcntsDeleteBtn", pageName, frameName);	
	}
	public WebElement RefreshAllButton(){
		return SeleniumUtil.getWebElement(d, "RefreshAllButton", pageName, frameName);	
	}
	public List<WebElement> RefreshAtSiteLevel() {
		return SeleniumUtil.getWebElements(d, "RefreshAtSiteLevel", pageName, frameName);
	}
	
	
	public boolean NameOfHighLevelContainers(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsHighLevelContainers", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements(propValue, l);
		return status;
	}
	
	public List<WebElement> AccountsExpandCollapseBtn() {
		return SeleniumUtil.getWebElements(d, "accounts_btn_Accordian", pageName, null);
	}
	public void verifyGroupsAreInExpandedForm(){
		for (int i = 0; i < AccountsExpandCollapseBtn().size(); i++) {
			String getTitle = AccountsExpandCollapseBtn().get(i).getAttribute("Title");
			boolean status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus"));
			Assert.assertTrue(status);
		}	
	}
	
	public void verifyUserCanMinimise(){
		for (int i = 0; i < AccountsExpandCollapseBtn().size(); i++) {
			SeleniumUtil.click(AccountsExpandCollapseBtn().get(i));
			SeleniumUtil.waitForPageToLoad(3000);
			String getTitle = AccountsExpandCollapseBtn().get(i).getAttribute("Title");
			boolean status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus2"));
			
			if(!status) {
				SeleniumUtil.waitForPageToLoad(2000);
				status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus2"));
			}
			
			Assert.assertTrue(status);
		}
	}

	public void verifyUserCanMaximise(){
		for (int i = 0; i < AccountsExpandCollapseBtn().size(); i++) {
			SeleniumUtil.click(AccountsExpandCollapseBtn().get(i));
			SeleniumUtil.waitForPageToLoad(3000);
			String getTitle = AccountsExpandCollapseBtn().get(i).getAttribute("Title");
			boolean status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus"));
			
			if(!status) {
				SeleniumUtil.waitForPageToLoad(2000);
				status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus"));
			}
			
			Assert.assertTrue(status);
		}
	}
	public WebElement accountslayout() {
		return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
	}

	
	
}

