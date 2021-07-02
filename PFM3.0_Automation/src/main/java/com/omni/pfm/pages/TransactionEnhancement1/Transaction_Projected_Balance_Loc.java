/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Projected_Balance_Loc {
	static Logger logger = LoggerFactory.getLogger(Transaction_Projected_Balance_Loc.class);
	public static WebDriver d =null;
	static WebElement we;
	String pageName="Transaction";
	String frameName=null;
	public Transaction_Projected_Balance_Loc(WebDriver d)
	{
		this.d=d;
		
	}
	public List<WebElement> projectedBalanceAmount()
	{
		return SeleniumUtil.getWebElements(d,"projectedBalanceAmount", pageName, frameName);
	}
	public List<WebElement> projectedBalanceLabel()
	{
		return SeleniumUtil.getWebElements(d,"projectedBalanceLabel", pageName, frameName);
	}
	
	public List<WebElement> postedBalanceAmount()
	{
		return SeleniumUtil.getWebElements(d,"postedBalanceAmount", pageName, frameName);
	}
	public List<WebElement> postedBalanceLabel()
	{
		return SeleniumUtil.getWebElements(d,"postedBalanceLabel", pageName, frameName);
	}
	public List<WebElement> accountdropdownAllAccount()
	{
		return SeleniumUtil.getWebElements(d,"accountdropdownAllAccount", pageName, frameName);
	}
	public List<WebElement> projectedDateHeader()
	{
		return SeleniumUtil.getWebElements(d,"projectedDateHeader", pageName, frameName);
	}
	public String getDateMMMMDDYYYy(int futurDate) throws ParseException
	{
		SimpleDateFormat formate2=new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, futurDate);
		System.out.println(formate2.format(cal2.getTime()));
		return formate2.format(cal2.getTime());
	}
	
}
