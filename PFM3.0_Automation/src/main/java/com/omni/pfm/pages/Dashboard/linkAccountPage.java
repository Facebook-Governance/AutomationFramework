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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class linkAccountPage{

	public WebDriver d;
	
	public linkAccountPage(WebDriver d) {
		this.d = d;
		
	}

	@FindBy(how = How.CLASS_NAME, using = "getStarted")
	public WebElement getStartedBtn;

	@FindBy(how = How.ID, using = "siteSearch")
	public WebElement siteSearch;

	public void clickSearchedSite(String searchedSite) {
		List<WebElement> sitename = d.findElement(By.id("searchSites")).findElements(By.className("siteName"));
		for (int i = 0; i < sitename.size(); i++) {
			System.out.println(sitename.get(i).getAttribute("orgvalue"));
			if (sitename.get(i).getAttribute("orgvalue").equals(searchedSite)) {

				sitename.get(i).click();
				break;
			}
		}
	}

	@FindBy(how = How.NAME, using = "LOGIN")
	public WebElement userName;

	@FindBy(how = How.NAME, using = "PASSWORD")
	public WebElement passwordField;

	@FindBy(how = How.NAME, using = "Re-enter_PASSWORD")
	public WebElement reEnterPassword;

	@FindBy(how = How.ID, using = "next")
	public WebElement submitBtn;

	@FindBy(how = How.CLASS_NAME, using = "allDone")
	public WebElement doneBtn;

	@FindBy(how = How.CLASS_NAME, using = "closeIcon")
	public WebElement close;

	public void addNONMFAAccount(String loginName, String password) {
		userName.clear();
		userName.sendKeys(loginName);
		passwordField.clear();
		passwordField.sendKeys(password);
		reEnterPassword.clear();
		reEnterPassword.sendKeys(password);
		submitBtn.click();

	}

	public void addNONMFAAccount(String fiName, String loginName, String password) {
		siteSearch.sendKeys(fiName);
		clickSearchedSite(fiName);
		userName.clear();
		userName.sendKeys(loginName);
		passwordField.clear();
		passwordField.sendKeys(password);
		reEnterPassword.clear();
		reEnterPassword.sendKeys(password);
		submitBtn.click();

	}

	public WebElement getLinkAnotherAccount()

	{
		return d.findElement(By.className("buttonWrapper")).findElement(By.id("linkAnotherAccount"));

	}

}
