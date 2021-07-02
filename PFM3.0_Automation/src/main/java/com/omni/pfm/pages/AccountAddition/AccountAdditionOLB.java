/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.AccountAddition;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountAdditionOLB extends TestBase{
	public void linkAccount(WebElement sfgLinkAccount)
	{
		SeleniumUtil.click(sfgLinkAccount);
		SeleniumUtil.waitForPageToLoad(4000);
		try {
			d.switchTo().defaultContent();
			d.switchTo().frame(SeleniumUtil.getWebElement(d, "FastLinkOLBFrame", "LinkAnAccount", null));
			WebElement getStartedText = SeleniumUtil.getVisibileWebElement(d, "getStarted", "LinkAnAccount", null);
			if (getStartedText.isDisplayed()) {
				SeleniumUtil.click(getStartedText);

			}
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	public boolean addAggregatedAccount(String dagUserNaem, String dagPassword) throws AWTException, InterruptedException {

		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).click();
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(PropsUtil.getDataPropertyValue("DAGSite"));
		SeleniumUtil.waitForPageToLoad(2500);
	   // SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null));
		
		SeleniumUtil.waitForPageToLoad(2500);
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		for (int i = 0; i < sitename.size(); i++) {
			if (sitename.get(i).getText().equals(PropsUtil.getDataPropertyValue("DAGSite"))) {
				sitename.get(i).click();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).click();
		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null).click();
		SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null).sendKeys(dagPassword);
		try {

			if (SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null).isDisplayed()) {
				SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null).click();
				SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null).sendKeys(dagPassword);
			}

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			SeleniumUtil.waitForPageToLoad(3000);
			// To move down scroll Bar inside the web page.

		} catch (Exception e) {
			SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "nextBtn", "LinkAnAccount", null));
			System.out.println("Re enter Password field is not present.");
		}

			SeleniumUtil.waitForPageToLoad();

			try {
				if (SeleniumUtil.getVisibileWebElement(d, "selectCheckBox", "LinkAnAccount", null).isDisplayed());
				{
					SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "selectCheckBox", "LinkAnAccount", null));
				}

			} catch (Exception e) {
				System.out.println(e);
			}

			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "submitBtn", "LinkAnAccount", null));
			SeleniumUtil.waitForPageToLoad(7000);
			WebDriverWait wait=new WebDriverWait(d, 380);
			boolean status=false;
			if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
			{
		    SeleniumUtil.waitForPageToLoad(20000);
	         WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "successMsg", "LinkAnAccount", null)));
		     status =successMsg.isDisplayed();
			}
			else
			{
				WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "UnifiedFlowsuccessMsg", "LinkAnAccount", null)));
			     status =successMsg.isDisplayed();
			}
		    SeleniumUtil.waitForPageToLoad();
			//SeleniumUtil.click(alldoneButton());
		    d.navigate().refresh();
		    SeleniumUtil.waitForPageToLoad(7000);
			return status;
	}

}
