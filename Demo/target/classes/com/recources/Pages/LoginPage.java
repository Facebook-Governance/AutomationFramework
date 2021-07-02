/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.page;

import com.yodlee.customizationtool.basetest.BaseTest;
import com.yodlee.customizationtool.core.SeleniumWrapper;
import com.yodlee.customizationtool.exception.FrameworkException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LandingPage extends Page {

    private Logger log = LoggerFactory.getLogger(LoginPage.class);

    ViewListOfSubBrandsPage viewListOfSubBrandsPage;

    public String pageName = "LOGIN_PAGE";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "loginPageHeaderLbl");
    }

    public WebElement userNameTextBox() {
        return getWebElement(pageName, "usernameTxtBox");
    }

    public WebElement userNameTextBoxGhostText() {
        return getWebElement(pageName, "usernameTxtBoxGhostTxt");
    }

    public WebElement passwordTextBox() {
        return getWebElement(pageName, "passwordTxtBox");
    }

    public WebElement passwordTextBoxGhostText() {
        return getWebElement(pageName, "passwordTxtBoxGhostTxt");
    }

    public String getGhostTextUsernameTextBox(){
        return userNameTextBoxGhostText().getText();
    }

    public String getGhostTextPasswordTextBox(){ return passwordTextBoxGhostText().getText(); }

    public WebElement loginButton() { return getWebElement(pageName, "loginBtn"); }

    public WebElement alertText() { return getWebElement(pageName, "alertToolTip"); }

    public WebElement closeAlertToolTip() { return getWebElement(pageName, "closeAlertToolTip"); }

    public WebElement forgotPasswordLink() { return getWebElement(pageName, "forgotPasswordLink"); }

    public WebElement logoImg() { return getWebElement(pageName, "logoImg"); }

    public WebElement userIdFormatDefaultText() { return getWebElement(pageName, "usernameTextBoxErrorText"); }

    public WebElement passwordFormatDefaultText() { return getWebElement(pageName, "passwordTextBoxErrorText"); }

    public WebElement footerLabel() {
        return getWebElement(pageName, "footerLbl");
    }

    public boolean isHeaderLabelPresent(){ return headerLabel().isDisplayed(); }

    public boolean isYodleeIDTextBoxPresent(){ return userNameTextBox().isDisplayed(); }

    public boolean isPasswordTextBoxPresent(){ return passwordTextBox().isDisplayed(); }

    public boolean isLoginButtonPresent(){ return loginButton().isDisplayed(); }

    public boolean isForgotPasswordLinkPresent(){ return forgotPasswordLink().isDisplayed(); }

    public boolean isLogoImgPresent(){ return logoImg().isDisplayed(); }

    public boolean isUserIdFormatDefaultTextPresent(){ return userIdFormatDefaultText().isDisplayed(); }

    public boolean isLoginButtonEnabled(){ return loginButton().isEnabled(); }

    public boolean isUsernameTextBoxEmpty(){
        return userNameTextBox().getText().isEmpty();
    }

    public boolean isPasswordTextBoxEmpty(){
        return passwordTextBox().getText().isEmpty();
    }

    public void loginToSubBrandManagementPortal(String username, String password) throws InterruptedException {
		/* BaseTest.webdriver.get(BaseTest.envDetails.getBaseUrl()); */
    	
        if(username==null) username="";
        if(password == null) password="";

        clearText(userNameTextBox());
        userNameTextBox().sendKeys(username);
        Thread.sleep(2000);
        clearText(passwordTextBox());
        passwordTextBox().sendKeys(password);
        Thread.sleep(2000);
        loginButton().click();
    }

    public void clickForgotPassword(){
        forgotPasswordLink().click();
    }

    public String getAlertText(){
        return alertText().getText();
    }

    public String getUserNameFieldColor(){
        return getCssColor(pageName,"usernameTxtBox");
    }

    public String getPasswordFieldColor(){
        return getCssColor(pageName,"passwordTxtBox");
    }

    public String getUsernameFieldErrorTextColor(){
        return getCssColor(pageName,"usernameTextBoxErrorText");
    }

    public String getPasswordFieldErrorTextColor(){
        return getCssColor(pageName,"passwordTextBoxErrorText");
    }


}

