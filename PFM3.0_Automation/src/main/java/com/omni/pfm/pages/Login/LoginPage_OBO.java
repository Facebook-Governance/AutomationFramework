/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.annotations.Find;
import com.omni.pfm.config.Config;
import com.omni.pfm.factory.PageFactory;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DBUtil;
import com.omni.pfm.utility.FunctionUtil;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage_OBO {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage_OBO.class);
    static String userName=null;
   
    public static void initElements(WebDriver driver) {
    }

 // OBO LOGIN
    
    public static boolean loginWithYCC(WebDriver d) {
    	 
        String user=PropsUtil.getEnvPropertyValue("OBOuserLogin");
        String password=PropsUtil.getEnvPropertyValue("OBOPassword");
  
        TestBase.getDriver().get("https://192.168.211.97:9343/ycc/login.ymc.do?_flowId=login&c=csit_key_0%3AgzlbY8ZwxUBm%2BckUcCSFeJzSpq8%3D&l=_flowId");
                                  
        WebElement loginID=SeleniumUtil.getVisibileWebElement(d, "loginID", "SAML_LOGIN", null);
        loginID.clear();
        loginID.sendKeys(user);
  
        WebElement loginPassword=SeleniumUtil.getVisibileWebElement(d, "loginPassword", "SAML_LOGIN", null);
        loginPassword.clear();
        loginPassword.sendKeys(password);
       
        WebElement submitButton=SeleniumUtil.getVisibileWebElement(d, "loginBtn", "SAML_LOGIN", null);
        SeleniumUtil.click(submitButton);
  
        WebElement searchTab=SeleniumUtil.getVisibileWebElement(d, "searchTab", "SAML_LOGIN", null);
        SeleniumUtil.click(searchTab);
       
        String fileName=null;
        if(Config.getInstance().getEnvironment().toUpperCase().contains("STAB"))
        {
               //YCOMSTAB.Properties
               fileName=Config.getInstance().getEnvironment().toUpperCase()+".Properties";
        }
        else{
               //YCOM_NPR.Properties
        fileName=Config.getInstance().getEnvironment().toUpperCase()+"_"+PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase()+".Properties";
        }
        logger.info("the rest property file name is "+fileName);
        userName=null;
       // userName="PFM1516610165662";
        userName = RegisterUser.initCobrand(fileName);
        boolean status=false;
        if (GenericUtil.isNull(userName))
        {
            logger.error("Username is null or empty...");
            status = true;
        }
               else
              {
                  logger.info("Username is : {}", userName);
                  logger.info("Populating loginName textbox");
                  
                      
                  WebElement UserNameTxtBox=SeleniumUtil.getVisibileWebElement(d, "UserNameTxtBox", "SAML_LOGIN", null);
                  UserNameTxtBox.sendKeys(userName);
                  
                  WebElement searchButton=SeleniumUtil.getVisibileWebElement(d, "searchButton", "SAML_LOGIN", null);
                  SeleniumUtil.click(searchButton);
                  SeleniumUtil.waitForElement(null, 1000);
                  
                  WebElement customerName=SeleniumUtil.getVisibileWebElement(d, "customerName", "SAML_LOGIN", null);
                  SeleniumUtil.click(customerName);
                  SeleniumUtil.waitForElement(null, 1000);
                  
                  WebElement dropDown=SeleniumUtil.getVisibileWebElement(d, "dropDown", "SAML_LOGIN", null);
                  SeleniumUtil.click(dropDown);
                  
                  WebElement dropDownOptions=SeleniumUtil.getVisibileWebElement(d, "dropDownOptions", "SAML_LOGIN", null);
                  SeleniumUtil.click(dropDownOptions);
                  
                  WebElement goButton=SeleniumUtil.getVisibileWebElement(d, "goButton", "SAML_LOGIN", null);
                  SeleniumUtil.click(goButton);
                  SeleniumUtil.waitForElement(null, 2000);
                  
                  SeleniumUtil.SwitchToTab(d, "TitleOfFinappTab");
                  
        }
       return true;
  
 }
  
}
