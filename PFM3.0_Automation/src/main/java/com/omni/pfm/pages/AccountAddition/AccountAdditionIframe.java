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
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class AccountAdditionIframe extends TestBase {
	
	public void linkAccount() throws InterruptedException {
		SeleniumUtil.waitForPageToLoad();
		WebElement DashboradlinkAccount=SeleniumUtil.getVisibileWebElement(d, "DashboradlinkAccount", "LinkAnAccount", null);
		WebElement addLink = SeleniumUtil.getWebElement(d, "linkAccount", "LinkAnAccount", null);
		WebElement addLinkAfterAccount = SeleniumUtil.getVisibileWebElement(d, "addLinkAfterAccount", "LinkAnAccount", null);
		WebElement addLinkAfterTwoAccount = SeleniumUtil.getVisibileWebElement(d, "addLinkAfterTwoAccount", "LinkAnAccount", null);
		SeleniumUtil.waitForPageToLoad();
		if (DashboradlinkAccount !=null || addLink != null || addLinkAfterAccount != null || addLinkAfterTwoAccount != null) {
			if (addLinkAfterAccount != null) {
				SeleniumUtil.click(addLinkAfterAccount);
			}

			else if (addLinkAfterTwoAccount != null) {
				SeleniumUtil.click(addLinkAfterTwoAccount);
			}

			else if(DashboradlinkAccount !=null) {
				SeleniumUtil.click(DashboradlinkAccount);
			}else {
				SeleniumUtil.waitForPageToLoad(8000);
				//SeleniumUtil.click(addLink);
				
				SeleniumUtil.click(addLink);
				SeleniumUtil.waitForPageToLoad();
				try {
					WebElement getStartedText = SeleniumUtil.getVisibileWebElement(d, "getStarted", "LinkAnAccount", null);
					if (getStartedText.isDisplayed()) {
						SeleniumUtil.click(getStartedText);

					}
				} catch (Exception e) {

					System.out.println(e);
				}
			}
	     }
      }

	public boolean addAggregatedAccount(String dagUserNaem, String dagPassword) throws AWTException, InterruptedException {

		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "siteSearch", "LinkAnAccount", null));
		SeleniumUtil.getWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(PropsUtil.getDataPropertyValue("DAGSite"));
		SeleniumUtil.waitForPageToLoad(2500);
		if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
		{
          SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null));
		}
		SeleniumUtil.waitForPageToLoad(2500);
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		for (int i = 0; i < sitename.size(); i++) {
			if (sitename.get(i).getText().equals(PropsUtil.getDataPropertyValue("DAGSite"))) {
				sitename.get(i).click();
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2500);
		SeleniumUtil.click(SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10));
		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null));
		SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null).sendKeys(dagPassword);
		try {

			if (SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null).isDisplayed()) {
				SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null));
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
			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "submitBtn", "LinkAnAccount", null));
			
			WebDriverWait wait=new WebDriverWait(d, 90);
			boolean status=false;
			if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
			{
		    SeleniumUtil.waitForPageToLoad(20000);
	         WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "successMsg", "LinkAnAccount", null)));
		     status =successMsg.isDisplayed();
			}
			else
			{
				SeleniumUtil.waitForPageToLoad(20000);
				/*WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "UnifiedFlowsuccessMsg", "LinkAnAccount", null)));
			     status =successMsg.isDisplayed();*/
				status=true;
			}
		    SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad(80000);
			d.switchTo().frame(SeleniumUtil.getWebElement(d, "IframeEnviSwitchIframe", "SAML_LOGIN", null));
			 SeleniumUtil.waitForPageToLoad();
			return status;
	}

	public boolean addContainerAccount(String containerName, String dagUserName, String dagPassword) throws AWTException, InterruptedException {

		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(containerName);
		SeleniumUtil.waitForPageToLoad(2500);
		if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
		{
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null));
		}
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		for (int i = 0; i < sitename.size(); i++) {
			if (sitename.get(i).getText().equals(containerName)) {
				sitename.get(i).click();
				break;
			}
		}
		//SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "siteNameContainer", "LinkAnAccount", null));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).sendKeys(dagUserName);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null).sendKeys(dagPassword);
		try {

			if (SeleniumUtil.getVisibileWebElement(d, "reEnterPassword1", "LinkAnAccount", null).isDisplayed()) {
				SeleniumUtil.getVisibileWebElement(d, "reEnterPassword1", "LinkAnAccount", null).sendKeys(dagPassword);
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
		
		WebDriverWait wait=new WebDriverWait(d, 40);
		boolean status=false;
		if(PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no"))
		{
	    SeleniumUtil.waitForPageToLoad(7000);
           WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getVisibileWebElement(d, "successMsg", "LinkAnAccount", null)));
		   status = successMsg.isDisplayed();
		}
		else
		{
			SeleniumUtil.waitForPageToLoad(7000);
			/*WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.getWebElement(d, "UnifiedFlowsuccessMsg", "LinkAnAccount", null)));
		   status =successMsg.isDisplayed();*/
			status=true;
			
		}
		SeleniumUtil.waitForPageToLoad();
        SeleniumUtil.refresh(d);
       return status;
	}
	public void addManualRealEstateAccount(String accountName, String estimatedValue, boolean includeInNw)
            throws AWTException {
     // d.navigate().refresh();

     /*
     * if user is first time, click on link account button
     *
      * else if user is not first time, click on side tab, click add account
     */

     // d.navigate().refresh();
   
                  if (d.findElement(By.id("linkAccountBtn")) != null) {
                         d.findElement(By.id("linkAccountBtn")).click();
                         SeleniumUtil.waitForPageToLoad();

                         if (d.findElement(By.xpath("//*[text()='Get Started']")) != null) {

                                d.findElement(By.xpath("//*[text()='Get Started']")).click();
                                SeleniumUtil.waitForPageToLoad(8000);
                                Robot robot = new Robot();
                                robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                                robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
                                SeleniumUtil.waitForPageToLoad(5000);
                                d.findElement(By.xpath("//a[@aria-label='Real Estate']")).click();

                         }
                  }
            

         

                  SeleniumUtil.click(d.findElement(By.id("suggestedAcc-linkOtherAccount-persist")));
                  SeleniumUtil.waitForPageToLoad(2000);
                  Robot robot = new Robot();
                  robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                  robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
                  SeleniumUtil.waitForPageToLoad(2000);
                  d.findElement(By.xpath("//a[@aria-label='Real Estate']")).click();

   
     // d.findElement(By.className("realestate-link")).click();
     SeleniumUtil.waitForPageToLoad(1500);

     d.findElement(By.xpath("//input[contains(@id,'account-name')]")).sendKeys(accountName);
     d.findElement(By.xpath("//input[@value='manual']")).click();

     d.findElement(By.xpath("//input[contains(@id,'estimated-value')]")).sendKeys(estimatedValue);

     if (!includeInNw) {
            d.findElement(By.xpath("//div[contains(@class,'switch toggleSwitch')]")).click();
     }

     SeleniumUtil.waitForPageToLoad(1500);
     d.findElement(By.id("add-account")).click();
     SeleniumUtil.waitForPageToLoad(1500);

     d.findElement(By.id("skip-link")).click();
     d.findElement(By.id("next")).click();

     SeleniumUtil.waitForPageToLoad();

     d.navigate().refresh();

     SeleniumUtil.waitForPageToLoad();

     /*FinappContainer dashBoard = new FinappContainer();
     dashBoard.accounts();
     SeleniumUtil.waitForPageToLoad(6000);*/

}



public void addManualAccount(String accountType, String accountName, String nickName, String currentBalance,
            String accountNumber, String amountDue, String nextDueDate, String liabilityOrAsset)
            throws InterruptedException {

     WebElement linkAccBtn = SeleniumUtil.waitForElementVisible(d, By.id("link-account-button-persist"), 3);
     if (linkAccBtn != null) {
            if (d.findElement(By.id("link-account-button-persist")).isDisplayed()) {
            }
            SeleniumUtil.click(linkAccBtn);
            SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
            SeleniumUtil.waitForPageToLoad();
            try {
                  WebElement successMsg = SeleniumUtil.waitForElementVisible(d, By.xpath("//*[text()='Get Started']"), 5);
                  if (successMsg != null) {

                         SeleniumUtil.click(successMsg);
                  }
            } catch (Exception e) {

            }
            SeleniumUtil.waitForPageToLoad();
            // d.findElement(By.xpath("//a[@aria-label='Manual Account']")).click();

     } else {

            try {
                  PageParser.forceNavigate("AccountsPage", d);
                  SeleniumUtil.waitForPageToLoad();
                  WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
                                By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
                  if (linkAnother != null) {
                         SeleniumUtil.click(d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")));
                         SeleniumUtil.click(d.findElement(By.xpath("//a[@aria-label='Manual Account']")));
                         SeleniumUtil.waitForPageToLoad(3000);

                  }
            } catch (WebDriverException e) {

                  WebElement linkAnother = SeleniumUtil.waitForElementVisible(d,
                                By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']"), 4);
                  if (linkAnother != null) {
                         d.findElement(By.xpath("//li[@id='li-suggestedAcc-linkOtherAccount-persist']")).click();
                         d.findElement(By.xpath("//a[@aria-label='Manual Account']")).click();
                         SeleniumUtil.waitForPageToLoad(3000);
                  }
            }
     }

     // waitForElement(d,finapp.AccType(),5000);
     d.findElement(By.xpath("//span[text()='Account Type']")).click();
     SeleniumUtil.waitForPageToLoad(4000);

     d.findElement(By.xpath("//li[contains(text(),'" + accountType + "')]")).click();

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

          try{
            SeleniumUtil.waitForPageToLoad(1000);
            d.findElement(By.xpath("//button[@aria-label='submit and go to next page']")).click();
     } catch(Exception E) {
            System.out.println("Old theme found");
     }

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

          //  nextDueDate = getDate();
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
     d.findElement(By.id("addBtn")).click();

     SeleniumUtil.waitForPageToLoad(6000);

   
}
	
}
