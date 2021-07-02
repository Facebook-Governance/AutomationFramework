/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.Util.Core;

import com.Util.exceptionHandling.FrameworkException;
import com.Util.pageprocessor.Config;
import com.Util.pageprocessor.ElementLocatorBean;
import com.Util.pageprocessor.PageBean;
import com.Util.pageprocessor.PagesBean;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;


/**
 * Utility class to wrap Selenium Find methods.
 *
 */
public class SeleniumWrapper
{
    private final int TIMEOUTINSECONDS = 20;

    Config config = Config.getInstance();

    private WebDriver driver;

    private Logger log = LoggerFactory.getLogger(SeleniumWrapper.class);

    public SeleniumWrapper(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By by) {

        log.info("Using By {}", by.toString());
        WebElement value = null;

        try {
            value = driver.findElement(by);
        } catch (NoSuchElementException e) {
            // the element can not be found
        }

        return value;
    }

    public void refresh(){
        driver.navigate().refresh();
    }

    public void clearText(WebElement e){
        e.sendKeys(Keys.SHIFT,Keys.HOME,Keys.BACK_SPACE);
    }

    public void clearTextArea(WebElement e){
        e.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "");
    }

    private ElementLocatorBean getElemenLocatorBean(String pageName, String label) {
        PagesBean pagesBean = config.getPagesBean();

        if (pagesBean == null) {
            throw new FrameworkException("PagesBean is null !");
        }
        PageBean page = pagesBean.getPages().get(pageName);
        if (page == null) {
            throw new FrameworkException("No page found with page name::" + pageName + " in Pages.json");
        }

        HashMap<String, ElementLocatorBean> elements = page.getElements();

        if (elements == null || elements.size() == 0) {
            throw new FrameworkException("Page :::" + pageName + " has no elements !!");
        }

        ElementLocatorBean elementLocatorBean = elements.get(label);
        if (elementLocatorBean == null) {
            throw new FrameworkException("No element with name as::" + label + " found in page::" + pageName);
        }
        return elementLocatorBean;

    }

    public List<WebElement> getElementsFromPage(String pageName, String label) {
        ElementLocatorBean e = getElemenLocatorBean(pageName, label);
        By by = getByObject(e.getLocatorType(), e.getValue());

        WebDriverWait wait=new WebDriverWait(driver, TIMEOUTINSECONDS);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        //visibilityOfAllElementsLocatedBy(by));
    }

    public WebElement getWebElement(String pageName, String label) {
        ElementLocatorBean e = getElemenLocatorBean(pageName, label);
        By by = getByObject(e.getLocatorType(), e.getValue());

        WebDriverWait wait=new WebDriverWait(driver, TIMEOUTINSECONDS);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
                //visibilityOfElementLocated());
    }

    public WebElement getVisibleWebElement(String pageName, String label) {
        ElementLocatorBean e = getElemenLocatorBean(pageName, label);
        By by = getByObject(e.getLocatorType(), e.getValue());

        WebDriverWait wait=new WebDriverWait(driver, TIMEOUTINSECONDS);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                //visibilityOfElementLocated());
    }
    
    public WebElement getWebElementByReplacingToken(String pageName, String label, String tokenValue) {
        ElementLocatorBean e = getElemenLocatorBean(pageName, label);
        By by = getByObject(e.getLocatorType(), e.getValue().replace("TOKEN",tokenValue));

        WebDriverWait wait=new WebDriverWait(driver, TIMEOUTINSECONDS);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        //visibilityOfElementLocated());
    }
    
    public WebElement getVisibleWebElementByReplacingToken(String pageName, String label, String tokenValue) {
        ElementLocatorBean e = getElemenLocatorBean(pageName, label);
        By by = getByObject(e.getLocatorType(), e.getValue().replace("TOKEN",tokenValue));

        WebDriverWait wait=new WebDriverWait(driver, TIMEOUTINSECONDS);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        //visibilityOfElementLocated());
    }

    private By getByObject(String locatorType, String locatorValue) {

        By by = null;

        if(locatorType.toLowerCase().trim().equals("id"))
            by = By.id(locatorValue);
        else if(locatorType.toLowerCase().trim().equals("xpath"))
            by = By.xpath(locatorValue);
        else if(locatorType.toLowerCase().trim().equals("css"))
            by = By.cssSelector(locatorValue);
        else if(locatorType.toLowerCase().trim().equals("name"))
            by =By.name(locatorValue);
        else if(locatorType.toLowerCase().trim().equals("link"))
            by =By.linkText(locatorValue);
        else if(locatorType.toLowerCase().trim().equals("tag"))
            by = By.tagName(locatorValue);
        else if(locatorType.toLowerCase().trim().equals("partialLink"))
            by = By.partialLinkText(locatorValue);

        return by;
    }

    public String getCssColor(String pageName, String obj){
        return getWebElement(pageName,obj).getCssValue("color");
    }

    public WebElement waitUntilElementVisible(WebDriver d, By by, int timeOuts) {

        WebDriverWait wait = new WebDriverWait(d, timeOuts);

        WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        //visibilityOfElementLocated());

        return ele;

    }
}

/*switch (locatorType.toLowerCase().trim()) {
            case "id":
                by = By.id(locatorValue);
                break;
            case "xpath":
                by = By.xpath(locatorValue);
                break;
            case "css":
                by = By.cssSelector(locatorValue);
                break;
            case "name":
                by = By.name(locatorValue);
                break;
            case "link":
                by = By.linkText(locatorValue);
                break;
            case "tag":
                by = By.tagName(locatorValue);
                break;
            case "partialLink":
                by = By.partialLinkText(locatorValue);
                break;
            default:
                by = By.xpath(locatorValue);
                break;
        }*/