/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.functions;

import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.executor.Executor;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;

public class ExecutionCommands {
	private static final Logger logger = LoggerFactory.getLogger(ExecutionCommands.class);
	private static WebDriver d = WebDriverFactory.getDriver();
	public static boolean verifyTitle(String title){
		logger.info("==> Entering verifyTitle method...");
		boolean result = false;
		if(GenericUtil.isNull(title)){
			logger.error("Title value is null or emptys");
		}
		else{
			result = d.getTitle().contains(title);
			if(!result){
				logger.warn("Title of the page is : {} \n Expected Title is : {}",d.getTitle(),title);
			}
			else{
				logger.info("Title matched...");
			}
		}
		logger.info("<== Exiting verifyTitle method...s");
		return result;
	}
	
	public static boolean verifyElementText(String elementName, String pageName, String frameName, int timeOut, String textToVerify){
		logger.info("==> Entering verifyElementText method...");
		boolean result = false;
		WebElement we = SeleniumUtil.getHTMLElement(elementName, pageName, frameName, timeOut);
		if(we==null){
			logger.error("Could not find the Element - {} in page - {} and frame - {}", elementName,pageName,frameName);
		}
		else{
			String actualText = we.getText();
			logger.info("Actual Text is : {}", actualText);
			logger.info("Expected Text is : {}",textToVerify);
			result = actualText.contains(textToVerify);
		}
		logger.info("Text verification result is : {}",result);
		logger.info("<== Exiting verifyElementText method...");
		return result;
	}
	
	public static boolean loadFrame(String pageName, String frameName){
		logger.info("==> Entering loadFrame method...");
		logger.info("Frame = {} and Page = {}",frameName,pageName);
		logger.info("Storing Parent Window Handle...");
		Executor.setParenthandle(d.getWindowHandle());
		boolean result = PageParser.loadFrame(d, pageName, frameName);
		logger.info("Load Frame result is : {}",result);
		logger.info("<== Exiting loadFrame method...");
		return result;
	}
	
	public static boolean unloadFrame(){
		logger.info("==> Entering unloadFrame method...");
		boolean result = true;
		try{
			d.switchTo().window(Executor.getParentWindowHandle());
		} catch(NoSuchWindowException ex){
			logger.error("No such window exists : {}", ex); 
			return false;
		}
		logger.info("<== Exiting unloadFrame method...");
		return result;
	}
	
	public static boolean moveToPage(String pageName){
		logger.info("==> Entering moveToPage command...");
		boolean status = PageParser.navigateToPage(pageName, d);
		logger.info("<== Exiting moveToPage command...");
		return status;
	}
	
	public static boolean callMethod(String fullyQualifiedClassName, String staticMethodName, String... args){
		logger.info("==> Entering callMethod command...");
		try {
			Class cls = Class.forName(fullyQualifiedClassName);
			Method methods[] = cls.getDeclaredMethods();
			for(Method method : methods){
				if(method.getName().equals(staticMethodName)){
					Class parameters[] = method.getParameterTypes();
					int paramCounter = 0;
					for(Class param : parameters){
						System.out.println(param.getSimpleName() + "   " + param.getName());
						if("String".equals(param.getSimpleName())){
							
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("<== Exiting callMethod command...");
		return true;
	}
	
	static void demoMethod(String one, String two, int three){
		System.out.println("Inside demoMethod...");
		System.out.println(one+"              "+two+"             "+three);
	}
	
	
	public static void main(String[] args) {
		callMethod("com.omni.pfm.functions", "demoMethod", "Hello","World","3");
	}
		
	
}
