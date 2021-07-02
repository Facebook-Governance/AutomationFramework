/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.AccountSettings;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ManualAccount_Settings_Loc {

	static Logger logger = LoggerFactory.getLogger(ManualAccount_Settings_Loc.class);
	public WebDriver d =null;
	static WebElement we;
	public static String theme;
	
	public ManualAccount_Settings_Loc(WebDriver d)
	{
		this.d=d;
		
	}

	public void typeAmountDue(String amountDue)

	{

		WebElement w = d.findElement(By.name("amountDue"));

		w.clear();

		w.sendKeys(amountDue);

	}

	public String getAmountDue()

	{

		return d.findElement(By.name("amountDue")).getAttribute("value");

	}

	public void selectCategory(int highLevelCategoryNum, int masterLevelCategoryNum) {
		boolean categoryDD = false;
        WebElement element = SeleniumUtil.getWebElement(d, "SelCategory_MAS", "AccountsPage", null);
        if (element != null) {
                        categoryDD = true;
        }
        element.click();
        SeleniumUtil.waitForPageToLoad();
        element = null;
        
        
        
        element = d.findElement(By.xpath("//div[@id='g-generic-dropdown0']//ul[@id='ul-0']/li[" + highLevelCategoryNum + "]//ul//li[" + masterLevelCategoryNum + "]"));

//            element = d.findElement(By.xpath("//div[@id='category-content']//li//ul/li[" + highLevelCategoryNum + "]//ul/li[" + masterLevelCategoryNum + "]//span"));

		
		/////////////
		
		if (element != null) {
			categoryDD &= true;
			SeleniumUtil.click(element);
		} else {
			categoryDD &= false;
		}
		Assert.assertTrue(categoryDD, "Transaction category value is not display");

	}

	public String getAccountName()

	{

		return d.findElement(By.name("accountName")).getAttribute("value");

	}

	public String getNickName()

	{

		return d.findElement(By.name("shortNickName")).getAttribute("value");

	}

	public String getAccountType()

	{

		return d.findElement(By.name("acctType")).getText();

	}

	public String getAccountNumber()

	{

		return d.findElement(By.name("accountNumber")).getAttribute("value");

	}

	public WebElement sendAccountNumber()

	{

		return d.findElement(By.name("accountNumber"));

	}
	public String getCurrency()

	{


		return SeleniumUtil.getVisibileWebElement(d, "getCurrency_MAS", "AccountsPage", null).getText();

	}

	public String getCurrentBalance() {
		
		SeleniumUtil.waitForPageToLoad();
		
		WebElement amount = SeleniumUtil.getVisibileWebElement(d, "getCurrentBalance_MAS", "AccountsPage", null);

		
		String amountValue = amount.getAttribute("value");
		
		return amountValue;
	}

	public String getLoginName()

	{

		
		return SeleniumUtil.getVisibileWebElement(d, "getLoginName_MAS", "AccountsPage", null).getAttribute("value");


	}

	public String getURL()

	{

		
		return SeleniumUtil.getVisibileWebElement(d, "getURL_MAS", "AccountsPage", null).getAttribute("value");


	}

	public String getPassword()

	{

		return SeleniumUtil.getVisibileWebElement(d, "getPassword_MAS", "AccountsPage", null).getAttribute("value");

	}

	public String getMemo()

	{


		return SeleniumUtil.getVisibileWebElement(d, "getMemo_MAS", "AccountsPage", null).getAttribute("value");

	}

	public void typeMemo(String memo)

	{

		WebElement e = SeleniumUtil.getVisibileWebElement(d, "getMemo_MAS", "AccountsPage", null);

		e.clear();

		e.sendKeys(memo);

	}

	public void typePassword(String password)

	{

		WebElement w = SeleniumUtil.getVisibileWebElement(d, "getPassword_MAS", "AccountsPage", null);

		w.clear();

		w.sendKeys(password);

	}

	public String getPopupTitle()

	{

		return SeleniumUtil.getVisibileWebElement(d, "getPopupTitle_MAS", "AccountsPage", null).getText();

	}

	public void typeAccountName(String accountName)

	{

		WebElement w = d.findElement(By.name("accountName"));

		w.clear();

		w.sendKeys(accountName);

		SeleniumUtil.fluentWait(d);

	}

	public String getGhostNickName()

	{

		return SeleniumUtil.getVisibileWebElement(d, "getGhostNickName_MAS", "AccountsPage", null).getAttribute("placeholder");

	}

	public void typeNickName(String nickname)

	{

		WebElement w = d.findElement(By.name("shortNickName"));

		w.clear();

		w.sendKeys(nickname);

		SeleniumUtil.fluentWait(d);

	}

	public void typeAccountNumber(String accountNumber)

	{

		WebElement w = d.findElement(By.name("accountNumber"));

		w.clear();

		w.sendKeys(accountNumber);

		SeleniumUtil.fluentWait(d);

	}

	public void typeCurrentBalance(String currentBalance)

	{

		WebElement w = d.findElement(By.name("amount"));

		w.clear();

		w.sendKeys(currentBalance);

	}
	
	
	public WebElement currencyDropDown() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "currencyDropDown_MAS", "AccountsPage", null);
	}
	
	
	public WebElement assetLiabilityLoc() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "assetLiabilityLoc_MAS", "AccountsPage", null);
	}

	public WebElement assetRadioButton() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "assetRadioButton_MAS", "AccountsPage", null);
	}

	public WebElement liabilityRadioButton() { 
 		 return SeleniumUtil.getWebElement(d, "liabilityRadioButton_MAS", "AccountsPage", null);
	}

	public WebElement calendarIcon () { 
 		 return SeleniumUtil.getVisibileWebElement(d, "calendarIcon_MAS", "AccountsPage", null);
	}

	public void verifyFrequencyOption(String[] expectedValues) {
		
		String abC = SeleniumUtil.getVisibileElementXPath(d, "AccountsPage", null, "verifyFrequencyOption_MAS");
		
		
		List<WebElement> l = d.findElements(By.xpath(abC));
		//List<WebElement> l = SeleniumUtil.getWebElements(d, "verifyFrequencyOption_MAS", "AccountsPage", null);
		
		String value[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			value[i] = l.get(i).getText().toLowerCase().trim();
			if(value[i].trim().equals(expectedValues[i].trim())){
				System.out.println("Expected Value :" + expectedValues[i] + " Actual value : " + value[i]);
			} else {
				logger.error("Expected And Actual Value did not match.");
			}
		}
		
		Assert.assertTrue(Arrays.equals(value, expectedValues));
	}

	
	public WebElement frequencySelector() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "frequencySelector_MAS", "AccountsPage", null);
	}

	public void clickSaveButton()

	{

		SeleniumUtil.getVisibileWebElement(d, "clickSaveButton_MAS", "AccountsPage", null).click();

	}

	public void assertCurrentBalanceInlineError(String errorText)

	{

		WebElement element = SeleniumUtil.getVisibileWebElement(d, "assertCurrentBalanceInlineError_MAS", "AccountsPage", null);

		Assert.assertTrue(element.isDisplayed());

		String text = element.getText();

		Assert.assertEquals(text, errorText);

	}

	public void typeNextDueDate(String duedate)

	{

		SeleniumUtil.getVisibileWebElement(d, "typeNextDueDate_MAS", "AccountsPage", null).sendKeys(duedate);

		
		SeleniumUtil.fluentWait(d);

	}

	public void clearNextDueDate()

	{
		
		try {
			SeleniumUtil.getVisibileWebElement(d, "typeNextDueDate_MAS", "AccountsPage", null).click();
		} catch (Exception e) {
			SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "typeNextDueDate_MAS", "AccountsPage", null));
		}

		
		try {
			SeleniumUtil.getVisibileWebElement(d, "typeNextDueDate_MAS", "AccountsPage", null).click();
		} catch (Exception e) {
			SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "typeNextDueDate_MAS", "AccountsPage", null));
		}


		
		SeleniumUtil.getVisibileWebElement(d, "typeNextDueDate_MAS", "AccountsPage", null).clear();

	}

	public void closePopup()

	{

		SeleniumUtil.getVisibileWebElement(d, "closePopup_MAS", "AccountsPage", null).click();

		
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")
				&& PropsUtil.getEnvPropertyValue("VIEWTYPE").equalsIgnoreCase("SMALL")) {
		
			SeleniumUtil.getVisibileWebElement(d, "closePopup_MAS", "AccountsPage", null).click();
		}
	}

	public void assertDateInlineError(String errorText)

	{

		WebElement element = SeleniumUtil.getVisibileWebElement(d, "assertDateInlineError_MAS", "AccountsPage", null);
		
		Assert.assertTrue(element.isDisplayed());

		String text = element.getText();

		Assert.assertEquals(text, errorText);

	}
	
	public void addBankManualAccount(String accountType, String accountName, String nickName, String currentBalance,
			String accountNumber, String liabilityOrAsset) {

		//d.navigate().refresh();
	//	if (theme.equals("new")) {
			try {
				if (d.findElement(By.id("linkAccountBtn")).isDisplayed()) {
					try {
						d.findElement(By.id("linkAccountBtn")).click();
					} catch (Exception e) {
						SeleniumUtil.click(d.findElement(By.id("linkAccountBtn")));
					}
					SeleniumUtil.waitForPageToLoad();
					try {
						SeleniumUtil.click(d.findElement(By.xpath("//span[contains(text(),'Get Started')]")));
					} catch (Exception e) {
						// TODO: handle exception
					}
					SeleniumUtil.waitForPageToLoad();
					d.findElement(By.xpath("//a[@aria-label='Manual Account']")).click();
					SeleniumUtil.waitForPageToLoad();

					/*
					 * if (d.findElement(By.xpath(
					 * "//*[text()='Get Started']")) != null) {
					 * 
					 * d.findElement(By.xpath(
					 * "//*[text()='Get Started']")).click();
					 * d.findElement(By.xpath(
					 * "//a[@aria-label='Manual Account']")).click();
					 * 
					 * }
					 */

				}
			}

			catch (WebDriverException exception) {

				if (d.findElement(By.xpath("//a[contains(@id,'suggestedAcc-linkOtherAccount-persist')]"))
						.isDisplayed()) {
					d.findElement(By.xpath("//a[contains(@id,'suggestedAcc-linkOtherAccount-persist')]")).click();
					SeleniumUtil.waitForPageToLoad(5000);
					SeleniumUtil.waitForPageToLoad();
					try {
						SeleniumUtil.click(d.findElement(By.xpath("//span[contains(text(),'Get Started')]")));
					} catch (Exception e) {
						// TODO: handle exception
					}
					SeleniumUtil.waitForPageToLoad();
					SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
					SeleniumUtil.waitForPageToLoad(7000);
				}

			}
	//	}

	/*	else {

			try {
				if (d.findElement(By.xpath("//a[@aria-label='Link Other Account']")).isDisplayed()) {
					d.findElement(By.xpath("//a[@aria-label='Link Other Account']")).click();
					
				}
			} catch (WebDriverException e) {

				if (d.findElement(By.xpath("//a[contains(@id,'suggestedAcc-linkOtherAccount-persist')]"))
						.isDisplayed()) {
					d.findElement(By.xpath("//a[contains(@id,'suggestedAcc-linkOtherAccount-persist')]"))
							.click();
					d.findElement(By.xpath("//a[@aria-label='Link Other Account']")).click();
					SeleniumUtil.waitForPageToLoad(7000);
			}
			}
		} 
		
		SeleniumUtil.waitForPageToLoad(); */
	
	    try {
	    	d.findElement(By.xpath("//span[text()='Account Type']")).click();
	    }catch (Exception e) {
			SeleniumUtil.click(d.findElement(By.xpath("//span[text()='Account Type']")));
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(d.findElement(By.xpath("//li[contains(text(),'" + accountType + "')]")));
		
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(accountName);
		
		if (nickName.equalsIgnoreCase(null)) {
			d.findElement(By.xpath("//input[contains(@id,'nickName')]")).sendKeys(nickName);
		} else {
			System.out.println("Not visible");
		}
		
		SeleniumUtil.waitForPageToLoad();
		
		
		//if (theme.equals("new")) {
			try {
				d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")).click();
			} catch(Exception e) {
				SeleniumUtil.click(d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")));
			}
		//}
		/*else{
			System.out.println("Old theme found");
		} 
	    
		SeleniumUtil.waitForPageToLoad(); */
		if (accountNumber.equalsIgnoreCase("12345")) {
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).sendKeys(accountNumber);
		} else {

			System.out.println("Not visible");
		}
		
		if (!currentBalance.equalsIgnoreCase(null)){
			try{
			d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(currentBalance);
			SeleniumUtil.waitForPageToLoad(1000);
			}
			catch(Exception e){
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}

		if (liabilityOrAsset.equalsIgnoreCase("asset")) {
			d.findElement(By.xpath("//input[contains(@id,'liability')]")).click();
		} else {

			System.out.println("Not visible");
		}
		d.findElement(By.id("addBtn")).click();
		//d.findElement(By.id("nextBtn")).click();

		SeleniumUtil.waitForPageToLoad(8000);
		
	//	if (theme.equals("new")) {
			try{
				
				d.findElement(By.xpath("//button[contains(@aria-label,'All Done')]")).click();	
		}
			catch(Exception e){
				System.out.println("Element not found");
			}
	//	}
	/*	else
		{
			
			System.out.println("old Theme found");
		} 
		*/
		
	}
	
	public void addManualAccount(String accountType, String accountName, String nickName, String currentBalance,
			String accountNumber, String amountDue, String nextDueDate, String liabilityOrAsset) throws InterruptedException {

		
				WebElement linkAccBtn=SeleniumUtil.waitForElementVisible(d, By.id("link-account-button-persist"), 3);
				if (linkAccBtn!=null) {				
					if (d.findElement(By.id("link-account-button-persist")).isDisplayed()) {}
					SeleniumUtil.click(linkAccBtn);
					SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
					SeleniumUtil.waitForPageToLoad();
                 try{
                	WebElement successMsg=SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
					if ( successMsg!= null) {

						SeleniumUtil.click(successMsg);
					}
                  }
                   catch(Exception e)
                 {
	
                 }
					SeleniumUtil.waitForPageToLoad();
					//d.findElement(By.xpath("//a[@aria-label='Manual Account']")).click();
					
				}
				else {

			try {
				PageParser.forceNavigate("AccountsPage", d);
				SeleniumUtil.waitForPageToLoad();
				WebElement linkAnother=SeleniumUtil.waitForElementVisible(d, By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother!=null) {
					d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"))
							.click();
					SeleniumUtil.waitForPageToLoad(5000);
					d.findElement(By.xpath("//a[@aria-label='Manual Account']")).click();
					SeleniumUtil.waitForPageToLoad(3000);
				
				}
			} catch (WebDriverException e) {

				WebElement linkAnother=SeleniumUtil.waitForElementVisible(d, By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother!=null) {
					d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"))
							.click();
					d.findElement(By.xpath("//a[@aria-label='Manual Account']")).click();
					SeleniumUtil.waitForPageToLoad(3000);
			}
			}
		}		
		
		//waitForElement(d,finapp.AccType(),5000);
	    d.findElement(By.xpath("//span[text()='Account Type']")).click();
		SeleniumUtil.waitForPageToLoad(4000);
		
		d.findElement(By.xpath("//li[contains(text(),'" + accountType + "')]")).click();
		
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(accountName);
		SeleniumUtil.waitForPageToLoad();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(Keys.TAB);
		
		if (nickName.equalsIgnoreCase(null)) {
		try{
			d.findElement(By.xpath("//input[contains(@id,'nickName')]")).sendKeys(nickName);
			SeleniumUtil.waitForPageToLoad(1000);
			}
			catch(Exception e){
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}
		
//		if (theme.equals("new")) {
			SeleniumUtil.waitForPageToLoad(1000);
			try{
				d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")).click();
			} catch (Exception e) {
				SeleniumUtil.click(d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")));
			}
	     
	/*	}
		else{
			System.out.println("Old theme found");
		}
	    */
		SeleniumUtil.waitForPageToLoad();
		if (accountNumber.equalsIgnoreCase("12345")) {
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).sendKeys(accountNumber);
		} else {

			System.out.println("Not visible");
		}
		
		if (!currentBalance.equalsIgnoreCase(null)){
			try{
			d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(currentBalance);
			SeleniumUtil.waitForPageToLoad(1000);
			}
			catch(Exception e){
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}
		
		
		if(!amountDue.equalsIgnoreCase(null)){ 
			try{
			d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).sendKeys(amountDue);
			}
			catch(Exception e)
			{
				System.out.println("Not visble");
			}
		} else {
			
			System.out.println("Not visble");
			
		}
		
		if (nextDueDate.equalsIgnoreCase("date")) {
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).clear();
			
			nextDueDate = getDate();
			System.out.println("Date is"+nextDueDate);
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).sendKeys(nextDueDate);
			SeleniumUtil.waitForPageToLoad(2500);
		} else {
			System.out.println("Not visble");
		}

		if (liabilityOrAsset.equalsIgnoreCase("asset")) {
			d.findElement(By.xpath("//input[contains(@id,'liability')]")).click();
		} else {

			System.out.println("Not visible");
		}
		d.findElement(By.id("addBtn")).click();

		SeleniumUtil.waitForPageToLoad(6000);
		
	//	if (theme.equals("new")) {
			try{
				
				d.findElement(By.xpath("//button[contains(@aria-label,'All Done')]")).click();	
		}
			catch(Exception e){
				System.out.println("Element not found");
			}
/*		}
		else
		{
			
			System.out.println("old Theme found");
		} */
		
		
	}

	public static String getDate() {

		Date dNow = new Date();

		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");

		Calendar c = Calendar.getInstance();

		// c.setTime(dNow);

		c.add(Calendar.DATE, 2);

		// dNow = c.getTime();

		return ft.format(dNow).toString();

	}
	
	



}
