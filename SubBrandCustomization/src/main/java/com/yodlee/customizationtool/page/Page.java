/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.page;

import com.yodlee.customizationtool.core.SeleniumWrapper;
import org.openqa.selenium.WebDriver;

public class Page extends SeleniumWrapper {

    WebDriver driver;

    public Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }


}