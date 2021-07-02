/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static com.yodlee.customizationtool.basetest.BaseTest.dataProperty;


public class DashboardPage extends Page {

    private Logger log = LoggerFactory.getLogger(DashboardPage.class);

    public String pageName = "DASHBOARD_PAGE";

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public WebElement linkAccountButton() {
        return getWebElement(pageName, "linkAccountBtn");
    }

    public WebElement siteSearchTextBox() {
        return getWebElement(pageName, "siteSearchTxtBx");
    }

    public WebElement dagSiteLink() { return getWebElement(pageName, "dagSiteLnk"); }

    public WebElement userNameTextBox() { return getWebElement(pageName, "userNameTxtBx"); }

    public WebElement passwordTextBox() { return getWebElement(pageName, "passwordTxtBx"); }

    public WebElement reEnterPasswordTextBox() { return getWebElement(pageName, "reEnterPasswordTxtBx"); }

    public WebElement termsAndConditionsCheckBox() { return getWebElement(pageName, "termsAndConditionsChkBx"); }

    public WebElement backButton() { return getWebElement(pageName, "backBtn"); }

    public WebElement submitButton() { return getWebElement(pageName, "submitBtn"); }

    public WebElement closeButton() { return getWebElement(pageName, "closeBtn"); }

    public WebElement linkAnotherSiteButton() { return getWebElement(pageName, "linkAnotherSiteBtn"); }

    public WebElement refreshStatusLabel() { return getWebElement(pageName, "refreshStatusLbl"); }

    public WebElement toastMessageLabel() { return getWebElement(pageName, "toastMessageLbl"); }

    public WebElement getStartedButton() { return getWebElement(pageName, "getStartedBtn"); }

    public WebElement cancelButton() { return getWebElement(pageName, "cancelBtn"); }

    public void addAccount(String dagSitename, String dagUsername, String dagPassword) throws InterruptedException {

        if (linkAccountButton().isDisplayed()) {
            linkAccountButton().click();

            Thread.sleep(30000);

           
            
			/*
			 * try { if(termsAndConditionsCheckBox().isDisplayed()){
			 * termsAndConditionsCheckBox().click(); Thread.sleep(3000); }
			 * 
			 * } catch (AssertionError | Exception e) { // TODO: handle exception }
			 */
			  if(getStartedButton().isDisplayed() || getStartedButton().isEnabled())
			  { getStartedButton().click();}
			  
			/*
			 * WebDriverWait wait = new WebDriverWait(driver, 45);
			 * wait.until(ExpectedConditions.visibilityOf(linkAccountButton()));
			 */

            Thread.sleep(3000);
            clearText(siteSearchTextBox());
            siteSearchTextBox().sendKeys(dagSitename);

            Thread.sleep(1000);
            dagSiteLink().click();

            Thread.sleep(1000);
            clearText(userNameTextBox());
            userNameTextBox().sendKeys(dagUsername);
            Thread.sleep(1000);
            clearText(passwordTextBox());
            passwordTextBox().sendKeys(dagPassword);
            Thread.sleep(1000);
            clearText(reEnterPasswordTextBox());
            reEnterPasswordTextBox().sendKeys(dagPassword);

            Thread.sleep(1000);
            termsAndConditionsCheckBox().click();

            Thread.sleep(1000);
            submitButton().click();
        }
    }

}