/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class L1OLBLogin {
	 private static final Logger logger = LoggerFactory.getLogger(L1IFrameLogin.class);
	 
	    static String userName=null;
	    static String pageName="SAML_LOGIN";
	public  static boolean login(WebDriver d,String OLBFinappId){
        
        String fileName=null;
        fileName=Config.getInstance().getEnvironment().toUpperCase()+"_"+PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase()+".Properties";
        logger.info("the rest property file name is "+fileName);
        userName=null;
        if(PropsUtil.getEnvPropertyValue("cnf_newUserLogin").equalsIgnoreCase("yes")){
 	   userName = RegisterUser.initCobrand(fileName);
        }
        else{
     	   userName = PropsUtil.getEnvPropertyValue("cnf_userName");
        }
        boolean status=true;
       if (GenericUtil.isNull(userName))
       {
           logger.error("Username is null or empty...");
           status = false;
       }
       else
       {
           logger.info("Username is : {}", userName);
           logger.info("Populating loginName textbox");
     
           
           d.get(PropsUtil.getEnvPropertyValue("NodeURL"));
       	SeleniumUtil.waitForPageToLoad();
      
       	SeleniumUtil.getWebElement(d, "IframeuserName", "SAML_LOGIN", null).clear();
       SeleniumUtil.getWebElement(d, "IframeuserName", "SAML_LOGIN", null).sendKeys(userName);
       	SeleniumUtil.waitForPageToLoad();
      
       	SeleniumUtil.getWebElement(d, "IframePassword", "SAML_LOGIN", null).clear();
       	SeleniumUtil.getWebElement(d, "IframePassword", "SAML_LOGIN", null).sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));
       	SeleniumUtil.waitForPageToLoad();
       	Select envi=new Select(SeleniumUtil.getWebElement(d, "IframeEnviDropDown", "SAML_LOGIN", null));
       	envi.selectByIndex(9);
       	SeleniumUtil.waitForPageToLoad();
       	SeleniumUtil.click(SeleniumUtil.getWebElement(d, "IframeSubmit", "SAML_LOGIN", null));
       	SeleniumUtil.waitForPageToLoad();
       	List<WebElement> allTab=SeleniumUtil.getWebElements(d, "OLBAllTabs", "SAML_LOGIN", null);
        SeleniumUtil.click(allTab.get(3));
        SeleniumUtil.waitForPageToLoad(1000);
        WebElement mutliFinapId=SeleniumUtil.getWebElement(d, "OLBMultipleFinapId", "SAML_LOGIN", null);
	      mutliFinapId.clear();
	      mutliFinapId.sendKeys(OLBFinappId);
	      WebElement extraParams=SeleniumUtil.getWebElement(d, "OLBExpraparam", "SAML_LOGIN", null);
	      extraParams.clear();
	      extraParams.sendKeys(PropsUtil.getEnvPropertyValue("OLBExpraParam"));
	      SeleniumUtil.click(SeleniumUtil.getWebElement(d, "OLBSave", "SAML_LOGIN", null));
	      SeleniumUtil.waitForPageToLoad(3000);
	      List<WebElement> allTab1=SeleniumUtil.getWebElements(d, "OLBAllTabs", "SAML_LOGIN", null);
	      SeleniumUtil.click(allTab1.get(0));
	      SeleniumUtil.waitForPageToLoad(1000);
	      /*d.switchTo().frame(d.findElement(By.id("finapp_10003737")));
	      SeleniumUtil.click(d.findElement(By.id("sfgWidgetViewAllGoalsBtn")));
	      PageParser.forceNavigate("SFG", d);*/
      }
      
       return status;
    
}
public static boolean loginExistingUser(WebDriver d,String username, String password,String OLBFinappId){
        
        /*WebElement loginText=SeleniumUtil.getVisibileWebElement(d, "loginText", "SAML_LOGIN", null);
        SeleniumUtil.click(loginText);*/
        String fileName=null;
        fileName=Config.getInstance().getEnvironment().toUpperCase()+"_"+PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase()+".Properties";
        logger.info("the rest property file name is "+fileName);
     	userName = username;
        boolean status=true;
       if (GenericUtil.isNull(userName))
       {
           logger.error("Username is null or empty...");
           status = false;
       }
       else
       {
    	    logger.info("Username is : {}", userName);
            logger.info("Populating loginName textbox");
      
            
            d.get(PropsUtil.getEnvPropertyValue("NodeURL"));
        	SeleniumUtil.waitForPageToLoad();
       
        	SeleniumUtil.getWebElement(d, "IframeuserName", "SAML_LOGIN", null).clear();
        SeleniumUtil.getWebElement(d, "IframeuserName", "SAML_LOGIN", null).sendKeys(userName);
        	SeleniumUtil.waitForPageToLoad();
       
        	SeleniumUtil.getWebElement(d, "IframePassword", "SAML_LOGIN", null).clear();
        	SeleniumUtil.getWebElement(d, "IframePassword", "SAML_LOGIN", null).sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));
        	SeleniumUtil.waitForPageToLoad();
        	Select envi=new Select(SeleniumUtil.getWebElement(d, "IframeEnviDropDown", "SAML_LOGIN", null));
        	envi.selectByIndex(9);
        	SeleniumUtil.waitForPageToLoad();
        	SeleniumUtil.click(SeleniumUtil.getWebElement(d, "IframeSubmit", "SAML_LOGIN", null));
        	SeleniumUtil.waitForPageToLoad();
        	List<WebElement> allTab=SeleniumUtil.getWebElements(d, "OLBAllTabs", "SAML_LOGIN", null);
         SeleniumUtil.click(allTab.get(3));
         SeleniumUtil.waitForPageToLoad(1000);
         WebElement mutliFinapId=SeleniumUtil.getWebElement(d, "OLBMultipleFinapId", "SAML_LOGIN", null);
 	      mutliFinapId.clear();
 	      mutliFinapId.sendKeys(OLBFinappId);
 	      WebElement extraParams=SeleniumUtil.getWebElement(d, "OLBExpraparam", "SAML_LOGIN", null);
 	      extraParams.clear();
 	      extraParams.sendKeys(PropsUtil.getEnvPropertyValue("OLBExpraParam"));
 	      SeleniumUtil.click(SeleniumUtil.getWebElement(d, "OLBSave", "SAML_LOGIN", null));
 	      SeleniumUtil.waitForPageToLoad(3000);
 	      SeleniumUtil.click(allTab.get(0));
 	      SeleniumUtil.waitForPageToLoad(1000);
 	      /*d.switchTo().frame(d.findElement(By.id("finapp_10003737")));
 	      SeleniumUtil.click(d.findElement(By.id("sfgWidgetViewAllGoalsBtn")));
 	      PageParser.forceNavigate("SFG", d);*/
       }
       
        return status;
       }
}
