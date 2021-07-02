/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.config.Config;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_Trends_Loc {
	
	Logger logger= LoggerFactory.getLogger(Accounts_Trends_Loc.class);
    public WebDriver d;
    static String pageName = "AccountsPage";
	static String frameName = null;
	
	public Accounts_Trends_Loc(WebDriver d) {
		this.d=d;
	}
	
	

	public WebElement dummyGeneratorHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "dummyGeneratorHeader", pageName, frameName);
	}

	public  boolean checkVisibility(WebElement ele) {
		boolean status=false;
		if(ele==null)
		{
			logger.error("Could not find the element ",ele);
		}
		else{
			try{
				status=ele.isDisplayed();
			}catch(Exception er)
			{
				status=false;
				logger.error("The element is not visible [{}]");
				er.printStackTrace();
			}
		}
		return status;
	}
	

public void assertSettingOptions(String[] expectedOption) {

	List<WebElement> l = d.findElements(By.xpath("//ul[contains(@class,'open f-open-dropdown')]/li/a"));
	SeleniumUtil.waitForPageToLoad(2000);

	System.out.println("The List is" + l);

	String actualOption[] = new String[l.size()];

	for (int i = 0; i < l.size(); i++) {

		System.out.println("The List is" + l.size());

		actualOption[i] = l.get(i).getText();

		System.out.println("The text is" + actualOption[i]);
		System.out.println("The text is" + expectedOption[i]);


		Assert.assertEquals(expectedOption[i].toLowerCase().trim(), actualOption[i].toLowerCase().trim());

	}
}
public WebElement getEllipsificationMark(int outerContainerNum, int innerContainerNum, int AccountRow) {
	String inLinePopUp ="//div[@id='account-container-view-wrap']/div[" + innerContainerNum+ "]//div[@class='accounts-row-wrap'][" + AccountRow + "]/div/div[2]//span";
	return d.findElement(By.xpath(inLinePopUp));
}
public WebElement balanceText() { 
	return SeleniumUtil.getWebElement(d, "BalanceTxt", pageName, frameName);
}
public WebElement CloseIcon() {
	return SeleniumUtil.getWebElement(d, "ClsBtn", pageName, frameName);
}
public WebElement FIname1() {
	return SeleniumUtil.getWebElement(d, "FIName1", pageName, frameName);
}
public WebElement accno() {
	return  SeleniumUtil.getWebElement(d, "accno1", pageName, frameName);
}
public WebElement AcntDetails() {
	return  SeleniumUtil.getWebElement(d, "AcntDetails", pageName, frameName);
}
public String getTrendAcntInfo() {

	SeleniumUtil.selectDropDown(d, "Investment Account 401a", "View Trend");

	SeleniumUtil.waitForPageToLoad(2000);
	String info=AcntDetails().getText().trim();
	return info;

}
public int prevMonths(int index) {

	Calendar now = Calendar.getInstance();

	now.add(Calendar.MONTH, index);

	int i = 0 + now.get((Calendar.MONTH));

	return i;

}
public String getMonthNameFullInString(int month) {

	String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	return monthNames[month];

}

public String getExpectedTabulardDate(int month) {

	Calendar now = Calendar.getInstance();

	now.add(Calendar.MONTH, month);

	String date2 = getMonthNameFullInString(prevMonths(month)) + " " + now.get(Calendar.YEAR);

	String dateInfo = date2;

	return dateInfo;

}
public String getMonthNameInString(int month) {

	String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	return monthNames[month];

}
public int prevMonth(int month) {

	Calendar now = Calendar.getInstance();

	now.add(Calendar.MONTH, month);

	int i = 0 + now.get((Calendar.MONTH));

	return i;

}


public String getExpectedTrendDate() {
	
	int month;
	
	if(Config.getInstance().appFlag.equalsIgnoreCase("emulator")) {
		
		month= -2;	
	}else {
		//month=-5;
		month = Integer.parseInt(PropsUtil.getDataPropertyValue("Accounts_DefautTimePeriod"));
	}

	Calendar now = Calendar.getInstance();

	String date1 = getMonthNameInString(currentMonth()) + " " + now.get(Calendar.YEAR);

	now.add(Calendar.MONTH, month);

	String date2 = getMonthNameInString(prevMonth(month)) + " " + now.get(Calendar.YEAR);

	String dateInfo = date2 + " - " + date1;

	System.out.println(dateInfo);

	return dateInfo;

}
public int currentMonth() {

	Calendar now = Calendar.getInstance();

	int i = 0 + now.get((Calendar.MONTH));

	return i;

}
public String getActualTrenddate() {

	String date = d.findElement(By.id("chart-title")).getText();

	return date;

}
public WebElement chartView() {
	return SeleniumUtil.getWebElement(d, "chartVw", pageName, frameName);
}

public WebElement tabularView() {
	return SeleniumUtil.getWebElement(d, "tabularVw", pageName, frameName);
}
public WebElement dateText() {
	return SeleniumUtil.getWebElement(d, "dateTxt", pageName, frameName);
}
public WebElement blncText() {
	return SeleniumUtil.getWebElement(d, "blncTxt", pageName, frameName);
}
public String getActualMonthDate(int row) {

	String date = d.findElement(By.xpath("//div[@id='nw-data-point-wrap']/div[" + row + "]/div[1]"))
			.getText();

	return date;

}

public void assertDateOption() {

    //Updated By MRQA--removed  hard coded value since this value changes based on cobrand. 
    String trendMonth = PropsUtil.getDataPropertyValue("trendMonth");
    int trendMonthValue = Integer.parseInt(trendMonth);
    
    List<WebElement> l = d.findElements(By.xpath(

                                    "//div[@id='nw-data-point-wrap']/div/div[1]"));

    SeleniumUtil.waitForPageToLoad();

    System.out.println(l.size());

    for (int i = 0; i < l.size(); i++) {

                    Assert.assertEquals(getActualMonthDate(i + 1), getExpectedTabulardDate(i - trendMonthValue));

    }

}

public WebElement pageTitle() {
	return SeleniumUtil.getWebElement(d, "pageTtle", pageName, frameName);
}
public List<WebElement> xaxis() {
	return SeleniumUtil.getWebElements(d, "x-axis", pageName, frameName);
}
public List<WebElement> yaxis() {
	return SeleniumUtil.getWebElements(d, "y-axis", pageName, frameName);
}
public void verifyingTrendInfo() {
	String FIName = FIname1().getText();
	String acct=accno().getText();

	List<String> list= Arrays.asList(acct.split(" "));
	System.out.println(list.get(0));
	String acntNumber=list.get(2);
	System.out.println(acntNumber);

	String acntInfo = FIName + " | " + acntNumber;
	System.out.print(acntInfo);
	SeleniumUtil.waitForPageToLoad();

	String trendInfo = getTrendAcntInfo();
	System.out.println(trendInfo);	
	Assert.assertEquals(acntInfo, trendInfo);

}

}

