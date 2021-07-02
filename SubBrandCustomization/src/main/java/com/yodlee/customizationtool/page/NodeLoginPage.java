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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeLoginPage extends Page {

    private Logger log = LoggerFactory.getLogger(NodeLoginPage.class);

    public String pageName = "NODE_LOGIN_PAGE";

    public NodeLoginPage(WebDriver driver) {
        super(driver);
    }

    public WebElement userIDTextBox() { return getWebElement(pageName, "userIDTxtBx"); }

    public WebElement passwordTextBox() { return getWebElement(pageName, "passwordTxtBx"); }

    public WebElement finappIDTextBox() { return getWebElement(pageName, "finappIdTxtBx"); }

    public WebElement extraParamsTextArea() { return getWebElement(pageName, "extraParamsTxtArea"); }

    public WebElement jsonResponseCheckBox() { return getWebElement(pageName, "jsonResponseChkBx"); }

    public WebElement loginButton() { return getWebElement(pageName, "loginBtn"); }

    public void loginToNode(String username, String password, String finappID)
            throws InterruptedException {

        clearText(userIDTextBox());
        userIDTextBox().sendKeys(username);
        Thread.sleep(1000);
        clearText(passwordTextBox());
        passwordTextBox().sendKeys(password);
        Thread.sleep(1000);
        clearText(finappIDTextBox());
        finappIDTextBox().sendKeys(finappID);
        Thread.sleep(1000);

        loginButton().click();
    }

}