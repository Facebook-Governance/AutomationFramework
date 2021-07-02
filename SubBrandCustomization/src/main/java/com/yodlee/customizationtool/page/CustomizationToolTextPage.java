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

public class CustomizationToolTextPage extends Page {

    private Logger log = LoggerFactory.getLogger(CustomizationToolTextPage.class);

    public String pageName = "CUSTOMIZATION_TOOL_TEXT_PAGE";

    public CustomizationToolTextPage(WebDriver driver) {
        super(driver);
    }

    public WebElement textTab() { return getWebElement(pageName, "textTab"); }

    public WebElement fastLinkLink() { return getWebElement(pageName, "fastLinkLnk"); }

    public WebElement fastLinkSelectASiteTextBox() { return getWebElement(pageName, "fastLinkSelectASiteTxtBx"); }

    public WebElement fastLinkVerifyCredentialsTextBox() { return getWebElement(pageName, "fastLinkVerifyCredentialsTxtBx"); }

    public WebElement fastLinkViewAccountsTextBox() { return getWebElement(pageName, "fastLinkViewAccountsTxtBx"); }

    public WebElement fastLinkTipAccountTypesTextArea() { return getWebElement(pageName, "fastLinkTipAccountTypesTxtArea"); }

    public WebElement fastLinkLinkingAccountsSecurityTextArea() { return getWebElement(pageName, "fastLinkLinkingAccountsSecurityTxtArea"); }

    public WebElement fastLinkButtonsLink() { return getWebElement(pageName, "fastLinkButtonsLnk"); }

    public WebElement fastLinkButtonsGetStartedTextBox() { return getWebElement(pageName, "fastLinkButtonsGetStartedTxtBx"); }

    public WebElement fastLinkButtonsCancelTextBox() { return getWebElement(pageName, "fastLinkButtonsCancelTxtBx"); }

    public WebElement fastLinkButtonsSubmitTextBox() { return getWebElement(pageName, "fastLinkButtonsSubmitTxtBx"); }

    public WebElement fastLinkButtonsBackTextBox() { return getWebElement(pageName, "fastLinkButtonsBackTxtBx"); }

    public WebElement fastLinkButtonsCloseTextBox() { return getWebElement(pageName, "fastLinkButtonsCloseTxtBx"); }

    public WebElement fastLinkButtonsLinkAnotherSiteTextBox() { return getWebElement(pageName, "fastLinkButtonsLinkAnotherSiteTxtBx"); }

    public WebElement fastLinkButtonsDeleteTextBox() { return getWebElement(pageName, "fastLinkButtonsDeleteTxtBx"); }
}