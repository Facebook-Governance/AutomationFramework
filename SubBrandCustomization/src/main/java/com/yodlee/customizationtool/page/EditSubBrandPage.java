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

import java.util.List;

public class EditSubBrandPage extends Page {

    private Logger log = LoggerFactory.getLogger(EditSubBrandPage.class);

    public String pageName = "EDIT_SUB_BRAND_PAGE";

    public EditSubBrandPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "editSubBrandPageLbl");
    }

    public WebElement stepHeaderLabel() { return getWebElement(pageName, "esbpStepHeader"); }

    public List<WebElement> stepHeaderLabels() { return getElementsFromPage(pageName, "esbpStepHeader"); }

    public WebElement generalConfigStepperLabel() { return getWebElement(pageName, "generalConfigStepperLbl"); }

    public WebElement privateAuthSetupStepperLabel() { return getWebElement(pageName, "privateAuthSetupStepperLbl"); }

    public WebElement publicAuthSetupSteppterLabel() { return getWebElement(pageName, "publicAuthSetupSteppterLbl"); }

    public WebElement productsAndAppsSetupStepperLabel() { return getWebElement(pageName, "productsAndAppsSetupStepperLbl"); }

    public WebElement contactDetailsStepperLabel() { return getWebElement(pageName, "contactDetailsStepperLbl"); }

    public WebElement reviewStepperLabel() { return getWebElement(pageName, "reviewStepperLbl"); }

    public WebElement activeStepper() { return getWebElement(pageName,"activeStepperLbl"); }

    public String  getActiveStepper(){

        return activeStepper().getText();
    }

}