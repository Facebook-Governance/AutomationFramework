/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.functions;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFunctions {
	private static final Logger logger = LoggerFactory.getLogger(UserFunctions.class);
	public static boolean userLogin(WebDriver d){
		return true;
	}
	
	public static boolean addFinapp(WebDriver d){
		logger.info("==> Entering UserFunctions.addFinapp method.");
		return true;
		
	}

}
