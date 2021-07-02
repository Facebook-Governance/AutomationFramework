/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSettings;

import java.awt.AWTException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import com.omni.pfm.PageProcessor.PageParser;	
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

import org.testng.annotations.Test;


public class YCOM_Regression_AccountSettings_Login extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(YCOM_Regression_AccountSettings_Login.class);
    AccountAddition accAddition = new AccountAddition();
    public static String theme;
    
    @BeforeClass(description = "Login")
    public void init() throws Exception {
	doInitialization("Add Dag Account");
	LoginPage.loginMain(d,loginParameter);
	
    }

/************************************ ACCOUNT ADDITION    
 * @throws Exception *************************************************/
    
    @Test(description="Adding Dag Site" , enabled=true, priority=1)
    public void addDagSite() throws Exception{
    	logger.info("Add Dag Site");
    	accAddition.linkAccount();
    	SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite"),PropsUtil.getDataPropertyValue("dagSitePassword")));
		//accAddition.linkAccount();
		//Assert.assertTrue(accAddition.addContainerAccount(PropsUtil.getDataPropertyValue("DagCreditcard"), PropsUtil.getDataPropertyValue("DagCreditcardUserName"), PropsUtil.getDataPropertyValue("DagCreditcardPassword")));

    }
    
   /* @Test(description="Adding Manual Accounts" , enabled=true, priority=2)
    public void addManualAccount(){
    	logger.info("Add Manual Accounts");
    	
    	
    }*/
    
    public void addManualAccount(String accountType, String accountName, String nickName, String currentBalance,
			String accountNumber, String amountDue, String nextDueDate, String liabilityOrAsset)
			throws InterruptedException {

		WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d, By.id("link-account-button-persist"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.id("link-account-button-persist")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);	
			if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
			{ 
				SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
			}else {
				SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
			}
			SeleniumUtil.waitForPageToLoad();
			try {
				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} catch (Exception e) {

			}
			SeleniumUtil.waitForPageToLoad();

		} else {

			try {
				PageParser.forceNavigate("AccountsPage", d);
				SeleniumUtil.waitForPageToLoad();
				WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
						By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother != null) {
					SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
					SeleniumUtil.waitForPageToLoad(3000);
					try {
						WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
						if (successMsg != null) {

							SeleniumUtil.click(successMsg);
						}
					} catch (Exception e) {

					}
					if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
					{ 
						SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
					}else {
						SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
					}
					SeleniumUtil.waitForPageToLoad(3000);

				}
			} catch (WebDriverException e) {

				WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
						By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother != null) {
					SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
					if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
					{ 
						SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
					}else {
						SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
					}
					SeleniumUtil.waitForPageToLoad(3000);
				}
			}
		}

		SeleniumUtil.click(d.findElement(By.xpath("//span[text()='Account Type']")));
		SeleniumUtil.waitForPageToLoad(4000);

		SeleniumUtil.click(d.findElement(By.xpath("//li[contains(text(),'" + accountType + "')]")));

		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(accountName);
		SeleniumUtil.waitForPageToLoad();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(Keys.TAB);

		if (nickName.equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'nickName')]")).sendKeys(nickName);
				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}

			SeleniumUtil.waitForPageToLoad(1000);
			SeleniumUtil.click(d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")));

		SeleniumUtil.waitForPageToLoad();
		if (accountNumber.equalsIgnoreCase("12345")) {
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).clear();
			d.findElement(By.xpath("//input[contains(@id,'accountNumber')]")).sendKeys(accountNumber);
		} else {

			System.out.println("Not visible");
		}

		if (!currentBalance.equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'currentBalance')]")).sendKeys(currentBalance);
				SeleniumUtil.waitForPageToLoad(1000);
			} catch (Exception e) {
				System.out.println("Not visible");
			}
		} else {

			System.out.println("Not visible");
		}

		if (!amountDue.equalsIgnoreCase(null)) {
			try {
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).clear();
				d.findElement(By.xpath("//input[contains(@id,'amountDue')]")).sendKeys(amountDue);
			} catch (Exception e) {
				System.out.println("Not visble");
			}
		} else {

			System.out.println("Not visble");

		}

		if (nextDueDate.equalsIgnoreCase("date")) {
			d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]")).clear();

			nextDueDate = getDate();
			System.out.println("Date is" + nextDueDate);
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
		SeleniumUtil.click(d.findElement(By.id("addBtn")));

		SeleniumUtil.waitForPageToLoad(5000);

		SeleniumUtil.click(d.findElement(By.xpath("//button[contains(@aria-label,'All Done')]")));
		SeleniumUtil.waitForPageToLoad(5000);
	}
    
    public static String getDate() {

		Date dNow = new Date();

		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");

		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, 2);

		return ft.format(dNow).toString();

	}
    
    public void addManualRealEstateAccount(String accountName, String estimatedValue, boolean includeInNw)
			throws AWTException {

		/*
		 * if user is first time, click on link account button
		 * 
		 * else if user is not first time, click on side tab, click add account
		 */
    
    	WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d, By.id("link-account-button-persist"), 3);
		if (linkAccBtn != null) {
			if (d.findElement(By.id("link-account-button-persist")).isDisplayed()) {
			}
			SeleniumUtil.click(linkAccBtn);
			
			if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
			{ 
				SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
			}else {
				SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'manual-link')]")));
			}
			SeleniumUtil.waitForPageToLoad();
			try {
				WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
				if (successMsg != null) {

					SeleniumUtil.click(successMsg);
				}
			} catch (Exception e) {

			}
			SeleniumUtil.waitForPageToLoad();

		} else {

			try {
				PageParser.forceNavigate("AccountsPage", d);
				SeleniumUtil.waitForPageToLoad();
				WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
						By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother != null) {
					SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
					SeleniumUtil.waitForPageToLoad(3000);
					try {
						WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
						if (successMsg != null) {

							SeleniumUtil.click(successMsg);
						}
					} catch (Exception e) {

					}
					SeleniumUtil.click(d.findElement(By.className("realestate-link")));
					SeleniumUtil.waitForPageToLoad(3000);

				}
			} catch (WebDriverException e) {

				WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
						By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
				if (linkAnother != null) {
					SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
					SeleniumUtil.click(d.findElement(By.className("realestate-link")));
					SeleniumUtil.waitForPageToLoad(3000);
				}
			}
		}

		SeleniumUtil.waitForPageToLoad(1500);

		d.findElement(By.xpath("//input[contains(@id,'account-name')]")).sendKeys(accountName);
		SeleniumUtil.click(d.findElement(By.xpath("//input[@value='manual']")));

		d.findElement(By.xpath("//input[contains(@id,'estimated-value')]")).sendKeys(estimatedValue);

		/*if (!includeInNw) {
			d.findElement(By.xpath("//div[contains(@class,'switch toggleSwitch')]")).click();
		}*/

		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(d.findElement(By.id("add-account")));
		SeleniumUtil.waitForPageToLoad(2500);

		SeleniumUtil.click(d.findElement(By.id("skip-link")));
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(d.findElement(By.id("next")));

		SeleniumUtil.waitForPageToLoad();

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();

	}
   
   }
