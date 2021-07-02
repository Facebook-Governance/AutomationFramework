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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.config.Config;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class LoginPage_SAML2 {
	private static final Logger logger = LoggerFactory.getLogger(LoginPage_SAML2.class);
	static String userName = null;

	public static void initElements(WebDriver driver) {
	}

	// FOR MR_Weekly

	 public static boolean login(WebDriver d){
			d.get(PropsUtil.getEnvPropertyValue("cnf_base_url"));
	    	String fileName=null;
	        fileName=Config.getInstance().getEnvironment().toUpperCase()+"_"+PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase()+".Properties";
	    	logger.info("the rest property file name is "+fileName);
	    	userName = RegisterUser.initCobrand(fileName);
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
	    	    WebElement user=SeleniumUtil.getVisibileWebElement(d, "Login", "SAML_LOGIN", null);
	    	    user.clear();
	    	    user.sendKeys(userName);
	    	    
	    	    WebElement userPassword=SeleniumUtil.getVisibileWebElement(d, "Password", "SAML_LOGIN", null);
	    	    userPassword.clear();
	    	    userPassword.sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));  
	    	    
	    	    
	    	    //Abhinandan . 
	    	    //To handle finapp ID.
	    	    String finappID="//input[@name='app']";
	    	    if(d.findElements(By.xpath(finappID)).size()!=0){
	    	    	d.findElement(By.xpath(finappID)).sendKeys(PropsUtil.getDataPropertyValue("finAppID"));
	    	    } 
	    	    
	    	    WebElement loginButton=SeleniumUtil.getVisibileWebElement(d, "loginButton", "SAML_LOGIN", null);
	    	    loginButton.click();
	    	}
	    	
	    	
	    	WebDriverWait wait = new WebDriverWait(d,40);
	    	WebElement loginText = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "dashBoard", "DashboardPage", null)));
	    	boolean flag = loginText.isDisplayed();
	    	return flag;
	 }
	 
	 public static boolean loginWithExistingUserMR(WebDriver d, String username, String password)
	    {   	
	 
				String userName =username;
				boolean status = true;
				if (GenericUtil.isNull(userName)) 
				{
				    logger.error("Username is null or empty...");
				    status = false;
				} 
				else 
				{
					 WebElement user=SeleniumUtil.getVisibileWebElement(d, "Login", "SAML_LOGIN", null);
			    	    user.clear();
			    	    user.sendKeys(userName);
			    	    
			    	    WebElement userPassword=SeleniumUtil.getVisibileWebElement(d, "Password", "SAML_LOGIN", null);
			    	    userPassword.clear();
			    	    userPassword.sendKeys(password);    
			    	    WebElement loginButton=SeleniumUtil.getVisibileWebElement(d, "loginButton", "SAML_LOGIN", null);
			    	    loginButton.click();
			    	}
			    	WebDriverWait wait = new WebDriverWait(d,40);
			    	WebElement loginText = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "dashBoard", "DashboardPage", null)));
			    	boolean flag = loginText.isDisplayed();
			    	return flag;
		    	    
	    }
}
