/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.page;

import com.yodlee.customizationtool.util.GenericUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.List;
import static com.yodlee.customizationtool.basetest.BaseTest.dataProperty;

public class CreateSubBrandPage extends Page {

    private Logger log = LoggerFactory.getLogger(CreateSubBrandPage.class);

    public String pageName = "CREATE_SUB_BRAND_PAGE";

    public CreateSubBrandPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "createSubBrandPageLbl");
    }

    public WebElement stepHeaderLabel() { return getWebElement(pageName, "csbpStepHeader"); }

    public List<WebElement> stepHeaderLabels() {
        return getElementsFromPage(pageName, "csbpStepHeader");
    }
}