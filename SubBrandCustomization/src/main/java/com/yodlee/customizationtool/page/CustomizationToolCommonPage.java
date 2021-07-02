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

public class CustomizationToolCommonPage extends Page {

    private Logger log = LoggerFactory.getLogger(CustomizationToolCommonPage.class);

    public String pageName = "CUSTOMIZATION_TOOL_COMMON_PAGE";

    public CustomizationToolCommonPage(WebDriver driver) {
        super(driver);
    }

    public WebElement applicationsCustomizationPageLabel() { return getWebElement(pageName, "appsCustomizationPageLbl"); }

    public WebElement backToSubBrandListLink() { return getWebElement(pageName, "backToSubBrandsListLnk"); }

    public WebElement previewButton() { return getWebElement(pageName, "previewBtn"); }

    public WebElement saveButton() { return getWebElement(pageName, "saveBtn"); }

    public WebElement subBrandNameLabel() { return getWebElement(pageName, "subBrandNameLbl"); }

    public WebElement dialogDiscardButton() { return getWebElement(pageName, "dialogDiscardBtn"); }

    public WebElement dialogSaveButton() { return getWebElement(pageName, "dialogSaveBtn"); }

    public WebElement alertToolTipOnSave() { return getWebElement(pageName, "alertToolTipOnSave"); }

}