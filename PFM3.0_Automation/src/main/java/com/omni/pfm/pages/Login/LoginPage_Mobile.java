/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import java.awt.Robot;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.config.Config;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

import io.appium.java_client.AppiumDriver;

public class LoginPage_Mobile {
	
	static String userName;
	public static boolean login(WebDriver d){
		String fileName=null;
        fileName=Config.getInstance().getEnvironment().toUpperCase()+"_"+PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase()+".Properties";
      
        userName=null;
        if(PropsUtil.getEnvPropertyValue("cnf_newUserLogin").equalsIgnoreCase("yes")){
 	   userName = RegisterUser.initCobrand(fileName);
        	//userName="renukakharvi2";
        }
        else{
     	   userName = PropsUtil.getEnvPropertyValue("cnf_userName");
        }
		boolean status=true;
		SeleniumUtil.waitForPageToLoad();
		((AppiumDriver) d).findElement(By.id("et_username")).sendKeys(userName);
		SeleniumUtil.waitForPageToLoad(2000);
		if(((AppiumDriver) d).getKeyboard()!=null)
       {
    	   ((AppiumDriver) d).hideKeyboard();
       }
		SeleniumUtil.waitForPageToLoad(2000);
		((AppiumDriver) d).findElement(By.id("et_password")).click();
		((AppiumDriver) d).findElement(By.id("et_password")).sendKeys(PropsUtil.getEnvPropertyValue("userPassword"));
		//((AppiumDriver) d).findElement(By.id("et_password")).sendKeys("Yodlee@123");
		SeleniumUtil.waitForPageToLoad(2000);
		((AppiumDriver) d).hideKeyboard();
		SeleniumUtil.waitForPageToLoad(2000);
		((AppiumDriver) d).findElement(By.id("btnLogin")).click();
		SeleniumUtil.waitForPageToLoad();
		  Set<String> s = ((AppiumDriver) d).getContextHandles();
		  
		  for(String allContext:s)
		  {
			  if(allContext.equalsIgnoreCase("WEBVIEW_com.yodlee.yodleesampleapp.debug"))
			  {
				  ((AppiumDriver) d).context(allContext);
			  }
		  }
		  
		  System.out.println("Switched to:::"+((AppiumDriver) d).getContext());
		  
		
		return status;
	}
	
	
   public static void loginWithExistingUser(AppiumDriver d,String username, String password){
		
		d.findElement(By.id("up")).click();
		((WebElement) d.findElements(By.id("menu_label")).get(1)).click();
		SeleniumUtil.waitForPageToLoad();
		d.findElement(By.id("etUserName")).sendKeys(username);
		SeleniumUtil.waitForPageToLoad(2000);
		d.findElement(By.id("etPassword")).sendKeys(password);
		SeleniumUtil.waitForPageToLoad(2000);
		((AppiumDriver) d).hideKeyboard();
		SeleniumUtil.waitForPageToLoad(2000);
		d.findElement(By.id("btnLogin")).click();
		SeleniumUtil.waitForPageToLoad(20500);
		Set<String> s = d.getWindowHandles();
		System.out.println(s.toArray()[1]);
		d.context((String) s.toArray()[0]);
	}
}
