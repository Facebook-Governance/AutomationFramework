/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.SFG;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

//import OmniAuto.pages.Accounts.Page;

public class SFG_CreateGoal_GetStarted_Loc extends SeleniumUtil {
	public static WebDriver d = null;

	public SFG_CreateGoal_GetStarted_Loc(WebDriver driver) {
		d = driver;
	}

	/*
	 * private final String header="#sfgHeaderContent .title";
	 * 
	 * @FindBy(how=How.CSS,using=header) public WebElement sfgHeader;
	 */

	public WebElement sfgHeader() {
		return getVisibileWebElement(d, "sfgHeader", "SFG", null);
	}

	/*
	 * private final String nodata=".no-data-message-content";
	 * 
	 * @FindBy(how=How.CSS,using=nodata) public WebElement noDataMessage;
	 */

	public WebElement noDataMessage1() {
		return getVisibileWebElement(d, "noDataMessage1", "SFG", null);
	}

	public WebElement noDataMessage2() {
		return getVisibileWebElement(d, "noDataMessage2", "SFG", null);
	}

	public WebElement noDataMessage3() {
		return getVisibileWebElement(d, "noDataMessage3", "SFG", null);
	}

	/*
	 * private final String link="#sfgNoAccounts";
	 * 
	 * @FindBy(how=How.CSS,using=link) public WebElement sfgLinkAccount;
	 */

	public WebElement sfgLinkAccount() {
		return getWebElement(d, "sfgLinkAccount", "SFG", null);
	}

	public WebElement sfgLinkAccountBBT() {
		return getVisibileWebElement(d, "sfgLinkAccountBBT", "SFG", null);
	}

	/*
	 * private final String getStart="sfgStartNewGoal";
	 * 
	 * @FindBy(how=How.ID,using=getStart) public WebElement getStartButton;
	 */

	public WebElement getStartButton() {
		return getVisibileWebElement(d, "getStartButton", "SFG", null);
	}

	/*
	 * private final String getStartText="//a[@id='sfgStartNewGoal']";
	 * 
	 * @FindBy(how=How.XPATH,using=getStartText) public WebElement getStartText1;
	 */

	public WebElement getStartText1() {
		return getVisibileWebElement(d, "getStartText1", "SFG", null);
	}

	/*
	 * private final String Nogoal="//div[@class='noGoalsMsg']";
	 * 
	 * @FindBy(how=How.XPATH,using=Nogoal) public WebElement NogoalMessage;
	 */

	public WebElement NogoalMessage() {
		return getVisibileWebElement(d, "NogoalMessage", "SFG", null);
	}

	/*
	 * private final String ProblemMessage="//p[@class='ftue-msg-header']";
	 * 
	 * @FindBy(how=How.XPATH,using=ProblemMessage) public WebElement
	 * ProblemMessage1;
	 */

	public WebElement ProblemMessage1() {
		return getVisibileWebElement(d, "ProblemMessage1", "SFG", null);
	}

	/*
	 * private final String submessage="//p[@class='ftue-msg-desc']";
	 * 
	 * @FindBy(how=How.XPATH,using=submessage) public WebElement submessage1;
	 */

	public WebElement submessage1() {
		return getVisibileWebElement(d, "submessage1", "SFG", null);
	}

	/*
	 * private final String linkButton="openFLFloaterBtn";
	 * 
	 * @FindBy(how=How.ID,using=linkButton) public WebElement linkButton1;
	 */

	public WebElement linkButton1() {
		return getVisibileWebElement(d, "linkButton1", "SFG", null);
	}

	/*
	 * private final String activestep="//div[@class='step active-step']//span[2]";
	 * 
	 * @FindBy(how=How.XPATH,using=activestep) public WebElement activestep1;
	 */

	public WebElement activestep1() {
		return getVisibileWebElement(d, "activestep1", "SFG", null);
	}

	/*
	 * private final String
	 * alldone="//div[@id='content']/div/div[2]/div/div[1]/button";
	 * 
	 * @FindBy(how=How.XPATH,using=alldone) public WebElement alldoneButton;
	 */

	public WebElement alldoneButton() {
		return getWebElement(d, "alldoneButton", "SFG", null);
	}

	/*
	 * private final String
	 * LinkAnother="//div[@id='content']/div/div[2]/div/div[1]/span/button";
	 * 
	 * @FindBy(how=How.XPATH,using=LinkAnother) public WebElement
	 * LinkAnotherAccount;
	 */

	public WebElement LinkAnotherAccount() {
		return getVisibileWebElement(d, "LinkAnotherAccount", "SFG", null);
	}

	/*
	 * private final String linkaccountSFG="linkSFGAccountButton";
	 * 
	 * @FindBy(how=How.ID,using=linkaccountSFG) public WebElement
	 * linkaccountButtonSFG;
	 */

	public WebElement linkaccountButtonSFG() {
		return getVisibileWebElement(d, "linkaccountButtonSFG", "SFG", null);
	}

	/*
	 * private final String Currency="//div[@id='multiCurrencyInfo']//label";
	 * 
	 * @FindBy(how=How.XPATH,using=Currency) public WebElement Currencymessage;
	 */

	public WebElement Currencymessage() {
		return getVisibileWebElement(d, "Currencymessage", "SFG", null);
	}

	/*
	 * private final String linkAccountBtn = ".//*[@id='linkSFGAccountButton']";
	 * 
	 * @FindBy(how = How.XPATH, using = linkAccountBtn) public WebElement
	 * LinkAccountBtn;
	 */

	public WebElement LinkAccountBtn() {
		return getVisibileWebElement(d, "LinkAccountBtn", "SFG", null);
	}

	/*
	 * private final String accountfinapp1 =
	 * "//div[contains(@class,'accounts-account-type-wrapper-or-row-wrapper')]/div/div/div/div/div[1]/span";
	 * 
	 * @FindBy(how = How.XPATH, using = accountfinapp1) public List<WebElement>
	 * accountfinapp;
	 */

	public WebElement accountfinapp() {
		return getVisibileWebElement(d, "accountfinapp", "SFG", null);
	}

	/*
	 * private final String accountfinappBalance1 =
	 * "//span[contains(@class,'green first accountBalance')]";
	 * 
	 * @FindBy(how = How.XPATH, using = accountfinappBalance1) public
	 * List<WebElement> accountfinappBalance;
	 */

	public WebElement accountfinappBalance() {
		return getVisibileWebElement(d, "accountfinappBalance", "SFG", null);
	}

	/*
	 * private final String accountgoal1 =
	 * "//div[@class='accountsSummaryList']//div[@class='accounts-row']//dd/a/div/div/div/div[2]/div/div/label";
	 * 
	 * @FindBy(how = How.XPATH, using = accountgoal1) public List<WebElement>
	 * accountgoal;
	 */

	public List<WebElement> accountgoal() {
		return getWebElements(d, "accountgoal", "SFG", null);
	}

	public List<WebElement> SFGhighLevlcategories() {
		return getWebElements(d, "SFGhighLevlcategories", "SFG", null);
	}

	public List<WebElement> getSubCatText() {
		return getWebElements(d, "getSubCatText", "SFG", null);
	}

	public List<WebElement> closeHighLevelCat() {
		return getWebElements(d, "closeHighLevelCat", "SFG", null);
	}

	public List<WebElement> SFGhighLevlcategoriesSubContent() {
		return getWebElements(d, "SFGhighLevlcategoriesSubContent", "SFG", null);
	}

	/*
	 * private final String accountgoalBalance1 =
	 * "//div[@class='accountsSummaryList']//div[@class='accounts-row']//dd/a/div/div/div/div[2]/div/div/span";
	 * 
	 * @FindBy(how = How.XPATH, using = accountgoalBalance1) public List<WebElement>
	 * accountgoalBalance;
	 */

	public WebElement accountgoalBalance() {
		return getVisibileWebElement(d, "accountgoalBalance", "SFG", null);
	}

	public void getStartAddAccount() {
		click(sfgLinkAccount());
		By getStarted = getByObject("LinkAnAccount", null, "getStarted");
		if (isDisplayed(getStarted, 3)) {
			click(getStarted);
		}
	}

	public boolean addAggregatedAccount(String dagUserNaem, String dagPassword)
			throws AWTException, InterruptedException {

		waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null)
				.sendKeys(PropsUtil.getDataPropertyValue("DAGSite"));
		waitForPageToLoad(2500);
		// click(getVisibileWebElement(d, "siteSearchBtn",
		// "LinkAnAccount", null));

		waitForPageToLoad(2500);
		List<WebElement> sitename = getWebElements(d, "siteName", "LinkAnAccount", null);
		for (int i = 0; i < sitename.size(); i++) {
			if (sitename.get(i).getText().equals(PropsUtil.getDataPropertyValue("DAGSite"))) {
				sitename.get(i).click();
				break;
			}
		}
		waitForPageToLoad(2500);
		waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		getVisibileWebElement(d, "password", "LinkAnAccount", null).sendKeys(dagPassword);
		try {

			if (getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null).isDisplayed()) {
				getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null).sendKeys(dagPassword);
			}

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			waitForPageToLoad(3000);
			// To move down scroll Bar inside the web page.

		} catch (Exception e) {
			click(getVisibileWebElement(d, "nextBtn", "LinkAnAccount", null));
			System.out.println("Re enter Password field is not present.");
		}

		waitForPageToLoad();
		try {
			if (getVisibileWebElement(d, "selectCheckBox", "LinkAnAccount", null).isDisplayed()) {
				click(getVisibileWebElement(d, "selectCheckBox", "LinkAnAccount", null));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		click(getVisibileWebElement(d, "submitBtn", "LinkAnAccount", null));
		WebDriverWait wait = new WebDriverWait(d, 380);
		boolean status = false;
		if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
			waitForPageToLoad(20000);
			WebElement successMsg = wait
					.until(ExpectedConditions.visibilityOf(getWebElement(d, "successMsg", "LinkAnAccount", null)));
			status = successMsg.isDisplayed();
		} else {
			WebElement successMsg = wait.until(
					ExpectedConditions.visibilityOf(getWebElement(d, "UnifiedFlowsuccessMsg", "LinkAnAccount", null)));
			status = successMsg.isDisplayed();
		}
		refresh(d);
		return status;
	}

}
