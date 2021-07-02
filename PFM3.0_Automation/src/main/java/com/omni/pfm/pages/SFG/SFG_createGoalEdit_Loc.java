/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.SFG;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

//import OmniAuto.pages.Accounts.Page;

public class SFG_createGoalEdit_Loc extends SeleniumUtil {

	private static final Logger logger = LoggerFactory.getLogger(SFG_createGoalEdit_Loc.class);
	public static WebDriver d=null;
	public static final By goalGetStarted = getByObject("SFG", null, "startGoalGetStartButton");
	public SFG_createGoalEdit_Loc(WebDriver driver) {
		// TODO Auto-generated constructor stub
		 d=driver;

	}
	

	
	
	public WebElement goalProgressTag() {
		return SeleniumUtil.getVisibileWebElement(d, "goalProgressTag", "SFG", null);
	}
	
	public WebElement startGoalMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "startGoalMessage", "SFG", null);
	}
	
	public List<WebElement> startGoal3steps() {
		return SeleniumUtil.getWebElements(d, "startGoal3steps", "SFG", null);
	}
	
	public List<WebElement> startGoal3stepsImages() {
		return SeleniumUtil.getWebElements(d, "startGoal3stepsImages", "SFG", null);
	}
	public List<WebElement> startGoal3stepsInfo() {
		return SeleniumUtil.getWebElements(d, "startGoal3stepsInfo", "SFG", null);
	}
	public WebElement startGoalGetStartButton() {
		return SeleniumUtil.getWebElement(d, "startGoalGetStartButton", "SFG", null);
	}
	public WebElement SFGCreateGoalButton() {
		return SeleniumUtil.getWebElement(d, "SFGCreateGoalButton", "SFG", null);
	}
	
	public WebElement SFGcategoryHeading() {
		return SeleniumUtil.getWebElement(d, "SFGcategoryHeading", "SFG", null);
	}
	
	
	
	
	
	
	
	
	
	
	
	public String DateInMMMMFormate(int date )
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy ");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}
	
	

}



