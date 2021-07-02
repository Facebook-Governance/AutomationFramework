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
import org.testng.Assert;

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;



public class Budget_Select_Accounts_Loc {



	static Logger logger = LoggerFactory.getLogger(Budget_Select_Accounts_Loc.class);

	public WebDriver d = null;

	static WebElement we;



	String pageName = "Budget";

	String frameName = null;



	public Budget_Select_Accounts_Loc(WebDriver d) {

		this.d = d;



	}

	public WebElement Description() {

		return SeleniumUtil.getVisibileWebElement(d, "Description_BSA", pageName, frameName);

	}

	public WebElement SelectAccountsHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectAccountsHeader_BSA", pageName, frameName);

	}

	public List<WebElement> AccTypeSubHeader() {

		return SeleniumUtil.getWebElements(d, "AccTypeSubHeader_BSA", pageName, frameName);

	}

	public List<WebElement> SiteName() {

		return SeleniumUtil.getWebElements(d, "SiteName_BSA", pageName, frameName);

	}

	public List<WebElement> AccountName() {

		return SeleniumUtil.getWebElements(d, "AccountName_BSA", pageName, frameName);

	}

	public List<WebElement> InfoIcon() {

		return SeleniumUtil.getWebElements(d, "InfoIcon_BSA", pageName, frameName);

	}

	public WebElement CoachmarkHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "CoachmarkHeader_BSA", pageName, frameName);

	}

	public WebElement CoachmarkDescription() {

		return SeleniumUtil.getVisibileWebElement(d, "CoachmarkDescription_BSA", pageName, frameName);

	}

	public WebElement OKButton() {

		return SeleniumUtil.getVisibileWebElement(d, "OKButton_BSA", pageName, frameName);

	}

	public WebElement NextButton() {

		return SeleniumUtil.getVisibileWebElement(d, "NextButton_BSA1", pageName, frameName);

	}

	public WebElement header() {

		return SeleniumUtil.getVisibileWebElement(d, "header", pageName, frameName);

	}


	public WebElement NextButton2() {

		return SeleniumUtil.getVisibileWebElement(d, "NextButton_BSA", pageName, frameName);

	}

	public WebElement NextButton1() {

		return SeleniumUtil.getVisibileWebElement(d, "NextButtonText_CB", pageName, frameName);

	}

	public WebElement MobileNextButton() {

		return SeleniumUtil.getVisibileWebElement(d, "MobileNextButton_BSA", pageName, frameName);

	}

	public WebElement BackButton() {

		return SeleniumUtil.getVisibileWebElement(d, "BackButton_BSA", pageName, frameName);

	}

	public WebElement MobileBackButton() {

		return SeleniumUtil.getVisibileWebElement(d, "MobileBackButton_BSA", pageName, frameName);

	}

	public List<WebElement> CheckBox() {

		return SeleniumUtil.getWebElements(d, "CheckBox_BSA", pageName, frameName);

	}

	public WebElement SuccessMessage() {

		return SeleniumUtil.getVisibileWebElement(d, "SuccessMessage", pageName, frameName);

	}

	public void verifyAcntsContainers(String ExpectedValue){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AcntsContainerLabel", pageName, frameName);
		CommonUtils.assertEqualsListElements(ExpectedValue, l);
	}

	public List<WebElement> AcntNameUnderContainers(String getcontainerName){
		String getxpath= SeleniumUtil.getVisibileElementXPath(d, pageName, frameName ,"AcntNameUnderContainers");
		getxpath=getxpath.replaceAll("ContainerName", getcontainerName);
		return d.findElements(By.xpath(getxpath));
	}
	public List<WebElement> AcntNameUnderOther(){
		return SeleniumUtil.getWebElements(d, "AcntNameUnderOther", pageName, frameName);
	}
	public List<WebElement> checkBoxesForAcnts(String getcontainerName){

		String getXpath=SeleniumUtil.getVisibileElementXPath(d, pageName, frameName,"checkBoxesForAcnts");
		getXpath=getXpath.replaceAll("ContainerName", getcontainerName);
		return d.findElements(By.xpath(getXpath));
	}

	public void verifyCheckBoxStatus(List<WebElement> listname){

		for(int i=0;i<listname.size();i++){
			boolean checkBoxStatus=listname.get(i).getAttribute("class").contains(PropsUtil.getDataPropertyValue("AcntSelectionTickMark").trim());   
			Assert.assertTrue(checkBoxStatus);	
		}
	}

	public void verifyInvestmentCBStatus(List<WebElement> listname){
		for(int i=0;i<listname.size();i++){
			boolean checkBoxStatus=listname.get(i).getAttribute("class").contains(PropsUtil.getDataPropertyValue("AcntSelectionTickMark").trim());   
			Assert.assertFalse(checkBoxStatus);	
		}	
	}

	public WebElement AcntUnderOtherContainer(){
		return SeleniumUtil.getWebElement(d, "AcntUnderOtherContainer", pageName, frameName);
	}

	public void checkingAllInvAcnt(){
		for(int i=0;i<checkBoxesForAcnts("Investments").size();i++){
			SeleniumUtil.click(checkBoxesForAcnts("Investments").get(i));	
		}
	}

	public int getTotalAccounts(String containerName)
	{
		return AcntNameUnderContainers(containerName).size();
	}


	public void uncheckingCashContainerAcnts(){
		for(int i=0;i<checkBoxesForAcnts("Cash").size();i++){
			SeleniumUtil.click(checkBoxesForAcnts("Cash").get(i));	
		}
	}

	public void uncheckingCardContainerAcnts(){
		for(int i=0;i<checkBoxesForAcnts("Cards").size();i++){
			SeleniumUtil.click(checkBoxesForAcnts("Cards").get(i));	
		}	
	}
	public WebElement nextToStep2Button(){
		return SeleniumUtil.getWebElement(d, "nextToStep2Button", pageName, frameName);
	}

	public WebElement selectAcntDropDownBtn(){
		return SeleniumUtil.getWebElement(d, "BudgetDropDown_CB", pageName, frameName);
	}

	public boolean verifyAccountsGroupNameLabel(String groupName) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "selectExistingAcntGroupList", pageName, frameName);
		boolean status=CommonUtils.assertEqualsListElements(groupName, l);
		return status;
	}
	
	public boolean verifyManageCreateGroups(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "manageCreateGroup", "AccountGroups", frameName);
		boolean status=CommonUtils.assertEqualsListElements(propValue, l);
		return status;
	}
}

