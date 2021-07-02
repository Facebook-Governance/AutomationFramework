/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.MoneyCenter.pages;

import com.yodlee.app.yodleeApi.webdriver.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by smishra8 on 07-04-2017.
 */
public class MoneyCenterHomePage {
    protected Logger logger = LoggerFactory.getLogger(MoneyCenterHomePage.class);
    String omnitureJsUrl=null;

    WebDriver driver;
    public MoneyCenterHomePage(WebDriver driver) {
        logger.info("*********** Initializing "+ this.getClass().getSimpleName() + "***********");;
        PageFactory.initElements(driver, this);
        this.driver=driver;
        //WebDriverRunner.setWebDriver(driver);
    }


    @FindBy(xpath = "//a[@class='linkAccountPlus']")
    WebElement linkAccount;

    @FindBy(id = "masterLinkLogout")
    WebElement logoutLink;

    @FindBy(className = "informationLoader")
    WebElement loader;

    @FindBy(xpath = "//div[@class='linkAccountLinks']/a[contains(text(),'real estate account')]")
    List<WebElement> linkRealEstateAccount;

    @FindBy(id = "account_name")
    WebElement txtAccountName;

    @FindBy(id = "nickname")
    WebElement nickName;

    @FindBy(id = "auto_div")
    WebElement calculateHomeValueRadio;

    @FindBy(id = "street")
    WebElement streetAddress;

    @FindBy(id = "cityStateZip")
    WebElement cityStateZip;

    @FindBy(id = "include_div")
    WebElement chkbxIncludeNetworth;

    @FindBy(id = "saveButton")
    WebElement btnAdd;

    @FindBy(id = "formDiv")
    WebElement addAccountPopUp;

    public void waitForHomePageToLoad() {
        //DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable(linkAccount));
        DriverFactory.waitForElement(logoutLink);
        //DriverFactory.getWait().until(CustomExpectedConditions.attributeToBeContainedInElement(DriverFactory.getByFromWebElement(logoutLink),""));
    }

    public void linkAccount() {

        DriverFactory.waitUntilPageLoads();
        DriverFactory.waitForElement(linkAccount);
        linkAccount.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DriverFactory.waitForElementToVanish(loader);
        List<WebElement> allelements=driver.findElements(By.xpath("//script"));

        allelements.forEach(element ->{
            if(element.getAttribute("src").startsWith("https") && element.getAttribute("src").endsWith("yodlee-omniture.js")){
                omnitureJsUrl=element.getAttribute("src");
            }
        });

        System.out.println(omnitureJsUrl);
        //DriverFactory.waitForElement(linkRealEstateAccount);

        String parentWindow = driver.getWindowHandle();
        ((JavascriptExecutor)driver).executeScript("window.open();");
        Set<String> allWindows = driver.getWindowHandles();
        String childWindow=allWindows.stream().filter(window -> !window.equals(parentWindow)).findAny().get();
        driver.switchTo().window(childWindow);

        driver.get(omnitureJsUrl);
        driver.close();
        driver.switchTo().window(parentWindow);

        DriverFactory.waitForSomeTime();

        System.out.println(linkRealEstateAccount.size());








    }


}
