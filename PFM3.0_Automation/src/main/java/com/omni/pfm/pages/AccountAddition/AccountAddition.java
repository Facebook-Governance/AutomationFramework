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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountAddition extends TestBase {
	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	int numberOfTries = 0;
	boolean isAccountAdditionHappenedSuccessFully = false;

	public void linkAccountFastLink() throws InterruptedException {
		try {
			By getStartedText = SeleniumUtil.getByObject("LinkAnAccount", null, "getStarted");
			By acceptTermsCheckBox = SeleniumUtil.getByObject("LinkAnAccount", null,
					"acceptTermsAndConditionsCheckbox");
			By termsOfServiceLabel = SeleniumUtil.getByObject("LinkAnAccount", null, "termsOfServiceLabel");
			if (SeleniumUtil.isDisplayed(getStartedText, 3)) {
				if (SeleniumUtil.isDisplayed(termsOfServiceLabel, 3)) {
					if (SeleniumUtil.getAttribute(acceptTermsCheckBox, "aria-checked").equals("false")) {
						SeleniumUtil.click(acceptTermsCheckBox);
						SeleniumUtil.waitFor(1);
					}
				}
				SeleniumUtil.click(getStartedText);
				SeleniumUtil.waitFor(2);
			}
			SeleniumUtil.waitForPageToLoad();
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}

	public void linkAccount() {
		numberOfTries = 0;
		isAccountAdditionHappenedSuccessFully = false;
		if (SeleniumUtil.isDisplayed(By.xpath("//*[@id=\"flpopup\"]//i[@aria-label=\"Close Select a Site\"]"), 0)) {
			SeleniumUtil.click(By.xpath("//*[@id=\"flpopup\"]//i[@aria-label=\"Close Select a Site\"]"));
			SeleniumUtil.refresh(d);
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.waitFor(1);
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String addLinkBtn1 = "//a[contains(text(),'Link Account')]";
		String addLinkBtn2 = "#suggestedAcc-linkOtherAccount-persist";
		String addLinkBtn3 = "#link-account-btn-persist";
		String addLinkBtn4 = "#link-account-button-persist";
		String addLinkBtn5 = "//*[@autoid='finCheck_btn_LinkMoreAcc']";
		String addLinkBtn6 = "[id=\"linkAccount-persist\"]";

		if (SeleniumUtil.getElementCount((By.xpath(addLinkBtn1))) != 0) {
			SeleniumUtil.click(d.findElement(By.xpath(addLinkBtn1)));
		} else if (SeleniumUtil.getElementCount((By.cssSelector(addLinkBtn2))) != 0) {
			SeleniumUtil.click(d.findElement(By.cssSelector(addLinkBtn2)));
		} else if (SeleniumUtil.getElementCount((By.cssSelector(addLinkBtn3))) != 0) {
			SeleniumUtil.click(d.findElement(By.cssSelector(addLinkBtn3)));
		} else if (SeleniumUtil.getElementCount((By.cssSelector(addLinkBtn4))) != 0) {
			SeleniumUtil.click(d.findElement(By.cssSelector(addLinkBtn4)));
		} else if (SeleniumUtil.getElementCount((By.cssSelector(addLinkBtn6))) != 0) {
			SeleniumUtil.click(d.findElement(By.cssSelector(addLinkBtn6)));
		} else {
			SeleniumUtil.click(d.findElement(By.xpath(addLinkBtn5)));
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		try {
			By getStartedText = SeleniumUtil.getByObject("LinkAnAccount", null, "getStarted");
			By acceptTermsCheckBox = SeleniumUtil.getByObject("LinkAnAccount", null,
					"acceptTermsAndConditionsCheckbox");
			By termsOfServiceLabel = SeleniumUtil.getByObject("LinkAnAccount", null, "termsOfServiceLabel");
			if (SeleniumUtil.isDisplayed(getStartedText, 3)) {
				if (SeleniumUtil.isDisplayed(termsOfServiceLabel, 3)) {
					if (SeleniumUtil.getAttribute(acceptTermsCheckBox, "aria-checked").equals("false")) {
						SeleniumUtil.click(acceptTermsCheckBox);
						SeleniumUtil.waitFor(1);
					}
				}
				SeleniumUtil.click(getStartedText);
				SeleniumUtil.waitFor(2);
			}
			SeleniumUtil.waitForPageToLoad();
		} catch (Exception e) {
			logger.info(e.getMessage());

		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public boolean addAggregatedAccount(String dagUserNaem, String dagPassword)
			throws AWTException, InterruptedException {
		return addAccount(null, dagUserNaem, dagPassword, false);
	}

	public boolean addAggregatedAccount1(String dagUserNaem, String dagPassword)
			throws AWTException, InterruptedException {
		return addAccount(null, dagUserNaem, dagPassword, false);
	}

	public boolean addAggregatedAccount2(String dagUserNaem, String dagPassword)
			throws AWTException, InterruptedException {
		return addAccount2(null, dagUserNaem, dagPassword, false);
	}

	public boolean addAggregatedAccount(String containerName, String dagUserNaem, String dagPassword)
			throws AWTException, InterruptedException {
		return addAccount(containerName, dagUserNaem, dagPassword, false);
	}

	public boolean addContainerAccount(String containerName, String dagUserName, String dagPassword)
			throws AWTException, InterruptedException {
		return addAccount(containerName, dagUserName, dagPassword, true);
	}

	public void addManualRealEstateAccount(String accountName, String estimatedValue, boolean includeInNw)
			throws AWTException {

		String addRealEstateBtnXpath, reAccNameXpath, reEnterValueManuallyRadBtnXpath, reEstimatedValueXpath = "";

		linkAccount();
		SeleniumUtil.waitForPageToLoad();

		if (PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON")) {

			addRealEstateBtnXpath = "//div[contains(@aria-label,'Add Real Estate')]";
			reAccNameXpath = "//input[contains(@id,'accountName')]";
			reEnterValueManuallyRadBtnXpath = "//input[@value='manual']";
			reEstimatedValueXpath = "//input[contains(@id,'estimatedValue')]";

		} else {

			addRealEstateBtnXpath = "//a[@aria-label='Real Estate']";
			reAccNameXpath = "//input[contains(@id,'account-name')]";
			reEnterValueManuallyRadBtnXpath = "//input[@value='manual']";
			reEstimatedValueXpath = "//input[contains(@id,'estimated-value')]";
		}

		d.findElement(By.xpath(addRealEstateBtnXpath)).click();
		SeleniumUtil.waitForPageToLoad();

		d.findElement(By.xpath(reAccNameXpath)).sendKeys(accountName);

		SeleniumUtil.click(d.findElement(By.xpath(reEnterValueManuallyRadBtnXpath)));
		SeleniumUtil.waitForPageToLoad(1500);

		d.findElement(By.xpath(reEstimatedValueXpath)).sendKeys(estimatedValue);
		SeleniumUtil.waitForPageToLoad(1500);

		if (!includeInNw) {
			SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,'switch toggleSwitch')]")));
			SeleniumUtil.waitForPageToLoad(1500);
		}

		SeleniumUtil.click(d.findElement(By.id("add-account")));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(d.findElement(By.id("skip-link")));
		SeleniumUtil.waitForPageToLoad();

		try {
			SeleniumUtil.click(d.findElement(By.id("next")));
		} catch (Exception e) {

		}
		// Assert.assertTrue(successMsgForRealEstateAccount());
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

	}

	@SuppressWarnings("unchecked")
	public boolean addAccount(String containerName, String dagUserNaem, String dagPassword, boolean isContainer)
			throws AWTException {
		try {
			By getStartedText = SeleniumUtil.getByObject("LinkAnAccount", null, "getStarted");
			By acceptTermsCheckBox = SeleniumUtil.getByObject("LinkAnAccount", null,
					"acceptTermsAndConditionsCheckbox");
			By termsOfServiceLabel = SeleniumUtil.getByObject("LinkAnAccount", null, "termsOfServiceLabel");
			if (SeleniumUtil.isDisplayed(getStartedText, 3)) {
				if (SeleniumUtil.isDisplayed(termsOfServiceLabel, 3)) {
					if (SeleniumUtil.getAttribute(acceptTermsCheckBox, "aria-checked").equals("false")) {
						SeleniumUtil.click(acceptTermsCheckBox);
						SeleniumUtil.waitFor(1);
					}
				}
				SeleniumUtil.click(getStartedText);
				SeleniumUtil.waitFor(2);
			}
			SeleniumUtil.waitForPageToLoad();
		} catch (Exception e) {
			logger.info(e.getMessage());

		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		dagUserNaem = new String(dagUserNaem);
		dagPassword = new String(dagPassword);
		String fiName = containerName == null ? PropsUtil.getDataPropertyValue("DAGSite") : containerName;
		String unifiedFlow = PropsUtil.getEnvPropertyValue("UnifiedFlow");

		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).clear();
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(fiName);

		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {
			WebElement siteSearchButton = SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null);
			if (siteSearchButton != null)
				SeleniumUtil.click(siteSearchButton);
		}

		/**
		 * Getting the list of all displayed sites & clicking if it matches @fiName,
		 * streaming requires Java 1.8 as minimum
		 */
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		sitename.parallelStream().filter(
				t -> t.getAttribute("aria-label").split("site url")[0].trim().split("\\(")[0].trim().equals(fiName))
				.findFirst().get().click();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		int checkBoxSize;
		List<WebElement> openBackingCheckBox = null;
		if (PropsUtil.getEnvPropertyValue("OB").equals("yes")) {
			openBackingCheckBox = d.findElements(By.xpath("(//*[contains(@class,'customCheckBox')])[2]"));
			checkBoxSize = openBackingCheckBox.size();
			SeleniumUtil.click(openBackingCheckBox.get(0));
			SeleniumUtil.click(d.findElement(By.id("submitBtn")));
		} else {
			checkBoxSize = 0;
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (isContainer) {
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).click();
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).clear();
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).sendKeys(dagUserNaem);
		} else {
			SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).click();
			SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).clear();
			SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		}
		SeleniumUtil.waitFor(1);
		WebElement pwd = null; // Below checking if DagBank, if so, go for password1 else password
		pwd = fiName.equals("DagBank") ? SeleniumUtil.getVisibileWebElement(d, "password1", "LinkAnAccount", null)
				: SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null);
		pwd.click();
		pwd.clear();
		pwd.sendKeys(dagPassword);
		SeleniumUtil.waitFor(1);
		List<WebElement> reEnterPwd = null;
		reEnterPwd = (fiName.equals("DagBank")
				? SeleniumUtil.getWebElements(d, "reEnterPassword1", "LinkAnAccount", null)
				: SeleniumUtil.getWebElements(d, "reEnterPassword", "LinkAnAccount", null));

		if (reEnterPwd != null && reEnterPwd.size() > 0 && reEnterPwd.get(0).isDisplayed()) {
			WebElement reEnterPwdDup = null; // Below checking if DagBank, if so, go for reEnterPassword else
												// reEnterPassword1
			reEnterPwdDup = fiName.equals("DagBank")
					? SeleniumUtil.getVisibileWebElement(d, "reEnterPassword1", "LinkAnAccount", null)
					: SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null);
			reEnterPwdDup.click();
			reEnterPwdDup.clear();
			reEnterPwdDup.sendKeys(dagPassword);
		} else {
			List<WebElement> nextBtn = SeleniumUtil.getWebElements(d, "nextBtn", "LinkAnAccount", null);
			if (nextBtn.size() > 0 && nextBtn.get(0).isDisplayed()) {
				SeleniumUtil.click(nextBtn.get(0));
			}
			logger.info("Re enter Password field is not present.");
		}
		By selectCheckBox = SeleniumUtil.getByObject("LinkAnAccount", null, "selectCheckBox");
		if (SeleniumUtil.isDisplayed(selectCheckBox, 1)) {
			SeleniumUtil.click(selectCheckBox);
		}
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "submitBtn", "LinkAnAccount", null));
		By b;
		if (PropsUtil.getEnvPropertyValue("OB").equals("yes")) {
			if (checkBoxSize > 0) {
				b = By.xpath("(//div[contains(text(),'successfully')])[1]");
			} else {
				b = By.xpath("//div[contains(text(),'successfully')]");
			}
		} else {
			b = By.xpath("//div[contains(text(),'Congratulations')]");
		}
		String xpathForTechnicalError = "//*[contains(@class,\"errorSection\")]//*[text()=\"Technical Error\"]";
		String xpathForAccountsAdded = "//*[contains(@class,\"successAUFMedium\")]";
		String xpathForOKButton = "//*[@class=\"btnContent\" and @data-id=\"OK\"]";
		boolean status = false;
		A: for (int i = 0; i < 100; i++) {
			if (SeleniumUtil.isDisplayed(b, 0)) {
				logger.info("Account addition happened successfully");
				status = true;
				break;
			} else if (SeleniumUtil.isDisplayed(By.xpath(xpathForAccountsAdded), 0)) {
				logger.info("Account addition happened successfully");
				status = true;
				break;
			} else if (SeleniumUtil.isDisplayed(By.xpath(xpathForTechnicalError), 0)) {
				for (int j = 0; j < 3; j++) {
					if (addAccountAfterGettingTechnicalError(containerName, dagUserNaem, dagPassword, isContainer)) {
						status = true;
						break A;
					}
				}
			} else {
				SeleniumUtil.waitFor(1);
			}
		}
		if (!d.getCurrentUrl().contains("10003600")) {
			d.navigate().refresh();
			SeleniumUtil.waitFor(3);
			d.navigate().refresh();
			SeleniumUtil.waitFor(3);
			SeleniumUtil.refresh(d);
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible(60);
			SeleniumUtil.waitForPageToLoad();
		}
		return status;
	}

	public boolean addAccountAfterGettingTechnicalError(String containerName, String dagUserNaem, String dagPassword,
			boolean isContainer) {
		String xpathForTechnicalError = "//*[contains(@class,\"errorSection\")]//*[text()=\"Technical Error\"]";
		String xpathForOKButton = "//*[@class=\"btnContent\" and @data-id=\"OK\"]";

		SeleniumUtil.click(By.xpath(xpathForOKButton));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(3);
		dagUserNaem = new String(dagUserNaem);
		dagPassword = new String(dagPassword);
		String fiName = containerName == null ? PropsUtil.getDataPropertyValue("DAGSite") : containerName;
		String unifiedFlow = PropsUtil.getEnvPropertyValue("UnifiedFlow");

		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).clear();
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(fiName);

		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {
			WebElement siteSearchButton = SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null);
			if (siteSearchButton != null)
				SeleniumUtil.click(siteSearchButton);
		}

		/**
		 * Getting the list of all displayed sites & clicking if it matches @fiName,
		 * streaming requires Java 1.8 as minimum
		 */
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		sitename.parallelStream().filter(t -> t.getText().split("\\(")[0].trim().equals(fiName)).findFirst().get()
				.click();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		int checkBoxSize;
		List<WebElement> openBackingCheckBox = null;
		if (PropsUtil.getEnvPropertyValue("OB").equals("yes")) {
			openBackingCheckBox = d.findElements(By.xpath("(//*[contains(@class,'customCheckBox')])[2]"));
			checkBoxSize = openBackingCheckBox.size();
			SeleniumUtil.click(openBackingCheckBox.get(0));
			SeleniumUtil.click(d.findElement(By.id("submitBtn")));
		} else {
			checkBoxSize = 0;
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (isContainer) {
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).click();
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).clear();
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).sendKeys(dagUserNaem);
		} else {
			SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		}
		SeleniumUtil.waitFor(1);
		WebElement pwd = null; // Below checking if DagBank, if so, go for password1 else password
		pwd = fiName.equals("DagBank") ? SeleniumUtil.getVisibileWebElement(d, "password1", "LinkAnAccount", null)
				: SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null);
		pwd.click();
		pwd.clear();
		pwd.sendKeys(dagPassword);
		SeleniumUtil.waitFor(1);
		List<WebElement> reEnterPwd = null;
		reEnterPwd = (fiName.equals("DagBank")
				? SeleniumUtil.getWebElements(d, "reEnterPassword1", "LinkAnAccount", null)
				: SeleniumUtil.getWebElements(d, "reEnterPassword", "LinkAnAccount", null));

		if (reEnterPwd != null && reEnterPwd.size() > 0 && reEnterPwd.get(0).isDisplayed()) {
			WebElement reEnterPwdDup = null; // Below checking if DagBank, if so, go for reEnterPassword else
												// reEnterPassword1
			reEnterPwdDup = fiName.equals("DagBank")
					? SeleniumUtil.getVisibileWebElement(d, "reEnterPassword1", "LinkAnAccount", null)
					: SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null);
			reEnterPwdDup.click();
			reEnterPwdDup.clear();
			reEnterPwdDup.sendKeys(dagPassword);
		} else {
			List<WebElement> nextBtn = SeleniumUtil.getWebElements(d, "nextBtn", "LinkAnAccount", null);
			if (nextBtn.size() > 0 && nextBtn.get(0).isDisplayed()) {
				SeleniumUtil.click(nextBtn.get(0));
			}
			logger.info("Re enter Password field is not present.");
		}
		By selectCheckBox = SeleniumUtil.getByObject("LinkAnAccount", null, "selectCheckBox");
		if (SeleniumUtil.isDisplayed(selectCheckBox, 1)) {
			SeleniumUtil.click(selectCheckBox);
		}
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "submitBtn", "LinkAnAccount", null));
		By b;
		if (PropsUtil.getEnvPropertyValue("OB").equals("yes")) {
			if (checkBoxSize > 0) {
				b = By.xpath("(//div[contains(text(),'successfully')])[1]");
			} else {
				b = By.xpath("//div[contains(text(),'successfully')]");
			}
		} else {
			b = By.xpath("//div[contains(text(),'Congratulations')]");
		}
		String xpathForAccountsAdded = "//*[contains(@class,\"successAUFMedium\")]";
		for (int i = 0; i < 500; i++) {
			if (SeleniumUtil.isDisplayed(b, 0)) {
				logger.info("Account addition happened successfully");
				break;
			} else if (SeleniumUtil.isDisplayed(By.xpath(xpathForAccountsAdded), 0)) {
				logger.info("Account addition happened successfully");
				break;
			} else if (SeleniumUtil.isDisplayed(By.xpath(xpathForTechnicalError), 0)) {
				return false;
			} else {
				SeleniumUtil.waitFor(1);
			}
		}

		return true;

	}

	/**
	 * Utility method to add the manual accounts of various types. first click on
	 * the link an account link and then proceeds to add manual account.
	 * 
	 * @author ajain5
	 * @param accountType      type of manual account , values must match
	 *                         {@link ManualAccountType}
	 * @param accountName      user choice name of account.
	 * @param nickName         nick name for manual account.
	 * @param currentBalance   current balance
	 * @param accountNumber    user choice account number
	 * @param amountDue        amount due, must be in number
	 * @param nextDueDate,     must be in MM/DD/YYYY format.
	 * @param liabilityOrAsset
	 */
	public void addManualAccount(String accountType, String accountName, String nickName, String currentBalance,
			String accountNumber, String amountDue, String nextDueDate, String liabilityOrAsset) {
		linkAccount();
		clickManualAccountLink();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		selectManualAccountType(accountType);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		typeAccountName(accountName);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		typeNickName(nickName);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		clickNextButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		typeAccountNumber(accountNumber);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		selectCurrencyType(accountType, "USD");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		typeCurrentBalance(currentBalance);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		typeAmountDue(accountType, amountDue);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		selectFrequency(accountType, "");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		typeNextDueDate(accountType, nextDueDate);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		clickAdd();
		WebDriverWait wait = new WebDriverWait(d, 50);
		By b = By.xpath("//div[contains(text(),'Congratulations')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(b));
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public boolean successMsg() {
		String unifiedFlow = PropsUtil.getEnvPropertyValue("UnifiedFlow");
		WebDriverWait wait = new WebDriverWait(d, 500);
		WebElement successMsg = null;
		By b;
		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {
			b = (appFlag != null && appFlag.equalsIgnoreCase("Web"))
					? By.xpath("//div[contains(text(),'Congratulations')]")
					: By.xpath("//div[@id='status']/following::i[contains(@class,'success')]");
		} else {
			b = (appFlag != null && appFlag.equalsIgnoreCase("Web"))
					? By.xpath("//div[contains(text(),'Congratulations')]")
					: By.xpath("//div[@id='status']/following::i[contains(@class,'success')]");
		}
		successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(b));
		boolean status = successMsg.isDisplayed();
		return status;
	}

	private void clickAdd() {
		SeleniumUtil.click(d.findElement(By.id("addBtn")));
		SeleniumUtil.WaitUntilElementIsInVisible(By.id("addBtn"), 60);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	private void typeNextDueDate(String accountType, String nextDueDate) {
		HashSet<String> excludeAccount = new HashSet<>();
		excludeAccount.add(ManualAccountType.CASH);
		excludeAccount.add(ManualAccountType.INVESTMENTS);
		excludeAccount.add(ManualAccountType.PAYMENTSERVICES);
		excludeAccount.add(ManualAccountType.OTHERASSET);
		excludeAccount.add(ManualAccountType.OTHERLIABILITIES);
		excludeAccount.add(ManualAccountType.REWARDS);

		if (!excludeAccount.contains(accountType)) {
			if (nextDueDate != null && !nextDueDate.isEmpty()) {
				WebElement we = d.findElement(By.xpath("//input[contains(@id,'nextDueDate')]"));
				Boolean isSDGon = PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON");
				WebElement calendarIcon = null;
				if (isSDGon) {
					calendarIcon = d.findElement(By.xpath("//span[contains(@class,'calender-field-icon')]"));
				} else {
					calendarIcon = d.findElement(By.xpath("//span[contains(@class,'calendarIcon')]"));
				}
				JavascriptExecutor jse = (JavascriptExecutor) d;
				jse.executeScript("window.scrollBy(0,250)");
				SeleniumUtil.click(we);
				if (nextDueDate.equalsIgnoreCase("date") && calendarIcon != null) {
					nextDueDate = DateUtil.getDate();
					logger.info("Date is" + nextDueDate);
					JavascriptExecutor js = (JavascriptExecutor) d;
					js.executeScript("arguments[0].setAttribute('value','" + nextDueDate + "')", we);
					SeleniumUtil.click(we);
				} else {
					we.sendKeys(nextDueDate);
				}
			}
		}
	}

	private void selectFrequency(String accountType, String frequency) {
		HashSet<String> excludeAccount = new HashSet<>();
		excludeAccount.add(ManualAccountType.CASH);
		excludeAccount.add(ManualAccountType.INVESTMENTS);
		excludeAccount.add(ManualAccountType.PAYMENTSERVICES);
		excludeAccount.add(ManualAccountType.OTHERASSET);
		excludeAccount.add(ManualAccountType.OTHERLIABILITIES);
		excludeAccount.add(ManualAccountType.REWARDS);

		if (!excludeAccount.contains(accountType)) {
			if (frequency != null && !frequency.trim().isEmpty()) {
				d.findElement(By.xpath("//span[contains(@id,'frequency')]")).click();
				SeleniumUtil.waitForPageToLoad(2500);
				d.findElement(By.xpath("//li[contains(text(),'" + frequency + "')]")).click();
			}
		}
	}

	private void typeAmountDue(String accountType, String dueAmount) {
		HashSet<String> excludeAccount = new HashSet<>();
		excludeAccount.add(ManualAccountType.CASH);
		excludeAccount.add(ManualAccountType.INVESTMENTS);
		excludeAccount.add(ManualAccountType.PAYMENTSERVICES);
		excludeAccount.add(ManualAccountType.REWARDS);

		if (!excludeAccount.contains(accountType)) {
			if (dueAmount != null && !dueAmount.isEmpty()) {
				@SuppressWarnings("unchecked")
				List<WebElement> elements = d.findElements(By.xpath("//input[contains(@id,'amountDue')]"));
				if (!elements.isEmpty()) {
					WebElement element = elements.get(0);
					element.click();
					element.clear();
					element.sendKeys(dueAmount);
				}
			}
		}

	}

	private void typeCurrentBalance(String currentBalance) {
		if (currentBalance != null && !currentBalance.isEmpty()) {
			Boolean isSDGon = PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON");
			By currentbalanceInputBox;
			if (isSDGon) {
				currentbalanceInputBox = By.xpath("//input[contains(@id,'amount')]");
			} else {
				currentbalanceInputBox = By.xpath("//input[contains(@id,'currentBalance')]");
			}
			if (SeleniumUtil.isDisplayed(currentbalanceInputBox, 60)) {
				SeleniumUtil.click(currentbalanceInputBox);
				d.findElement(currentbalanceInputBox).clear();
				d.findElement(currentbalanceInputBox).sendKeys(currentBalance);
			}
		}
	}

	/**
	 * @author ajain5
	 * @param acctype  type of account which matches {@link ManualAccountType}
	 *                 values.
	 * @param currency currency code to be selected from drop down. Eg. USD for
	 *                 united states.
	 */
	private void selectCurrencyType(String acctype, String currency) {
		HashSet<String> excludeAccount = new HashSet<>();
		excludeAccount.add(ManualAccountType.CASH);
		excludeAccount.add(ManualAccountType.INVESTMENTS);
		excludeAccount.add(ManualAccountType.PAYMENTSERVICES);
		excludeAccount.add(ManualAccountType.REWARDS);

		if (!excludeAccount.contains(acctype)) {
			if (currency != null && !currency.isEmpty() && !currency.equalsIgnoreCase("USD")) {
				d.findElement(By.xpath("//*[contains(@id,'balanceCurrency')]")).click();
				SeleniumUtil.waitForPageToLoad(2500);
				d.findElement(By.xpath("//li[contains(text(),'" + currency + "')]")).click();
			}
		}
	}

	private void typeAccountNumber(String accountNumber) {
		By accountNumberInputBox = By.xpath("//input[contains(@id,'accountNumber')]");
		if (accountNumber != null && !accountNumber.isEmpty() && SeleniumUtil.isDisplayed(accountNumberInputBox, 60)) {
			SeleniumUtil.click(accountNumberInputBox);
			d.findElement(accountNumberInputBox).clear();
			d.findElement(accountNumberInputBox).sendKeys(accountNumber);
		}

	}

	private void clickNextButton() {
		SeleniumUtil.waitFor(5);
		SeleniumUtil.click(By.cssSelector("#nextBtn"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public void selectManualAccountType(String accountType) {
		Boolean isSDGon = PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON");
		SeleniumUtil.click(By.xpath("//span[text()='Account Type']"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (isSDGon) {
			SeleniumUtil.click(
					By.xpath("//*[@id=\"account_type_holder\"]//h3//span[contains(text(),'" + accountType + "')]"));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.waitFor(1);
		} else {
			SeleniumUtil.click(By.xpath("//li[contains(text(),'" + accountType + "')]"));
		}
	}

	public void clickManualAccountLink() {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Boolean isSDGon = PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON") ? true : false;
		By manualLink;
		if (isSDGon) {
			manualLink = By.xpath("//div[contains(@aria-label,'Manually Add Account')]");
		} else {
			manualLink = By.xpath("//a[@aria-label='Manual Account']");
		}
		if (SeleniumUtil.isDisplayed(manualLink, 30)) {
			SeleniumUtil.click(manualLink);
		} else if (SeleniumUtil.isDisplayed(By.xpath("//div[contains(@class,'manual-link')]"), 30)) {
			SeleniumUtil.click(By.xpath("//div[contains(@class,'manual-link')]"));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			Assert.fail("Add manual transaction icon is not displayed in the UI");
		}
	}

	public void typeAccountName(String accountName) {
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).clear();
		d.findElement(By.xpath("//input[contains(@id,'accountName')]")).sendKeys(accountName);
	}

	public void typeNickName(String nickName) {
		if (nickName != null && !nickName.isEmpty()) {
			By nickNameField = null;
			if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("YES")) {
				nickNameField = By.xpath("//input[contains(@id,'shortNickName')]");
			} else {
				nickNameField = By.xpath("//input[contains(@id,'nickName')]");
			}
			if (SeleniumUtil.isDisplayed(nickNameField, 5)) {
				SeleniumUtil.click(nickNameField);
				d.findElement(nickNameField).clear();
				d.findElement(nickNameField).sendKeys(nickName);
			}
		}
	}

	public void linkAccountFastlink() {
		try {
			WebElement getStartedText = SeleniumUtil.getVisibileWebElement(d, "getStarted", "LinkAnAccount", null);
			if (getStartedText.isDisplayed()) {
				SeleniumUtil.click(getStartedText);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	/*
	 * Added by Abhi -SUSTQA Method to add accounts from UI or API[Key based]
	 */
	public boolean addAggregatedAccountUIAPI(String dagUserName, String dagPassword, String type) throws Exception {
		boolean flag = false;
		if (type.equalsIgnoreCase("UI")) {
			linkAccount();
			return addAccount(null, dagUserName, dagPassword, false);
		} else {
			flag = RegisterUser.addAccountAPI(dagUserName, dagPassword);
			d.navigate().refresh();
		}
		return flag;
	}

	/**
	 * Common method to add modelo bank (UK) Open Banking requirement
	 * 
	 * @param containerName
	 * @param userName
	 * @param password
	 * @return status
	 */
	public boolean addModeloBankAccount(String containerName) {
		logger.info(">>>>> Entering link an account page to aggregate modelo bank");
		String fiName = containerName == null ? PropsUtil.getDataPropertyValue("DAGSite") : containerName;
		String unifiedFlow = PropsUtil.getEnvPropertyValue("UnifiedFlow");
		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).clear();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(fiName);
		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {
			WebElement siteSearchButton = SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null);
			if (siteSearchButton != null)
				SeleniumUtil.click(siteSearchButton);
		}
		WebElement chk = SeleniumUtil.getWebElement(d, "sss-checkbox", "LinkAnAccount", null);
		chk.click();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(d.findElement(By.id("submitBtn")));
		SeleniumUtil.waitForPageToLoad();

		WebElement confirmBtn = SeleniumUtil.getWebElement(d, "oauthContinueBtnId", "LinkAnAccount", null);
		confirmBtn.click();
		SeleniumUtil.waitForPageToLoad(3000);

		Set<String> windows = d.getWindowHandles();
		String[] array = new String[2];
		int i = 0;
		for (String s : windows) {
			array[i++] = s;
		}
		d.switchTo().window(array[1]);
		SeleniumUtil.waitForPageToLoad(3000);
		d.manage().window().maximize();
		SeleniumUtil.waitForPageToLoad(3000);

		WebElement modeloLoginInput = SeleniumUtil.getWebElement(d, "modelo-loginName", "LinkAnAccount", null);
		modeloLoginInput.clear();
		modeloLoginInput.sendKeys("mits");

		WebElement modeloPasswordInput = SeleniumUtil.getWebElement(d, "modelo-password", "LinkAnAccount", null);
		modeloPasswordInput.clear();
		modeloPasswordInput.sendKeys("mits");
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "modelo-submit", "LinkAnAccount", null));
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> accounts = SeleniumUtil.getWebElements(d, "modelo-accounts", "LinkAnAccount", null);
		for (int x = 1; x < accounts.size(); x++) {
			SeleniumUtil.click(accounts.get(x));
		}

		// added for new OB enhancements
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "selectAllAccs", "LinkAnAccount", null));
		d.findElement(By.id("accounts")).sendKeys(Keys.chord(Keys.CONTROL, "a"));

		// SeleniumUtil.click(SeleniumUtil.getWebElement(d, "modelo-submit",
		// "LinkAnAccount", null));
		// SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "modelo-final-submit", "LinkAnAccount", null));
		SeleniumUtil.waitForPageToLoad();

		d.switchTo().window(array[0]);

		WebDriverWait wait = new WebDriverWait(d, 500);

		WebElement successMsg = null;

		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {

			By b = (appFlag != null && appFlag.equalsIgnoreCase("Web"))
					? By.xpath("//div[@class='row container-heading unifiedBorder']")
					: By.xpath("//div[@id='status']/following::i[contains(@class,'success')]");

			successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(b));
		} else {

			By b = (appFlag != null && appFlag.equalsIgnoreCase("Web")) ? By.xpath("//div[contains(text(),'The Open')]")
					: By.xpath("//div[@id='status']/following::i[contains(@class,'success')]");

			successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(b));

		}
		boolean status = successMsg.isDisplayed();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		return status;
	}

	public boolean addAccount2(String containerName, String dagUserNaem, String dagPassword, boolean isContainer)
			throws AWTException {
		String fiName = containerName == null ? PropsUtil.getDataPropertyValue("DAGSite") : containerName;
		String unifiedFlow = PropsUtil.getEnvPropertyValue("UnifiedFlow");

		SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "id", "siteSearch", 60);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).clear();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.getVisibileWebElement(d, "siteSearch", "LinkAnAccount", null).sendKeys(fiName);

		if (unifiedFlow.equalsIgnoreCase("no") || unifiedFlow.equalsIgnoreCase("false")) {
			WebElement siteSearchButton = SeleniumUtil.getVisibileWebElement(d, "siteSearchBtn", "LinkAnAccount", null);
			if (siteSearchButton != null)
				SeleniumUtil.click(siteSearchButton);
		}

		/**
		 * Getting the list of all displayed sites & clicking if it matches @fiName,
		 * streaming requires Java 1.8 as minimum
		 */

		SeleniumUtil.waitForPageToLoad();
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "siteName", "LinkAnAccount", null);
		sitename.parallelStream().filter(
				t -> t.getAttribute("aria-label").split("site url")[0].trim().split("\\(")[0].trim().equals(fiName))
				.findFirst().get().click();
		SeleniumUtil.waitForPageToLoad();
		/*
		 * List<WebElement> openBackingCheckBox=d.findElements(By.xpath(
		 * "(//*[contains(@class,'customCheckBox')])[2]")); int
		 * checkBoxSize=openBackingCheckBox.size(); if(checkBoxSize>0) {
		 * SeleniumUtil.click(openBackingCheckBox.get(0));
		 * SeleniumUtil.click(d.findElement(By.id("submitBtn"))); }
		 */
		SeleniumUtil.waitForPageToLoad();
		if (isContainer) {
			SeleniumUtil.getVisibileWebElement(d, "loginContainerAcc", "LinkAnAccount", null).sendKeys(dagUserNaem);
		} else {
			SeleniumUtil.waitUntilElementVisible(TestBase.getDriver(), "name", "LOGIN", 10).sendKeys(dagUserNaem);
		}

		WebElement pwd = null; // Below checking if DagBank, if so, go for password1 else password
		pwd = fiName.equals("DagBank") ? SeleniumUtil.getVisibileWebElement(d, "password1", "LinkAnAccount", null)
				: SeleniumUtil.getVisibileWebElement(d, "password", "LinkAnAccount", null);

		pwd.sendKeys(dagPassword);

		List<WebElement> reEnterPwd = null;
		reEnterPwd = (fiName.equals("DagBank")
				? SeleniumUtil.getWebElements(d, "reEnterPassword1", "LinkAnAccount", null)
				: SeleniumUtil.getWebElements(d, "reEnterPassword", "LinkAnAccount", null));

		if (reEnterPwd != null && reEnterPwd.size() > 0 && reEnterPwd.get(0).isDisplayed()) {
			WebElement reEnterPwdDup = null; // Below checking if DagBank, if so, go for reEnterPassword else
												// reEnterPassword1
			reEnterPwdDup = fiName.equals("DagBank")
					? SeleniumUtil.getVisibileWebElement(d, "reEnterPassword1", "LinkAnAccount", null)
					: SeleniumUtil.getVisibileWebElement(d, "reEnterPassword", "LinkAnAccount", null);

			reEnterPwdDup.sendKeys(dagPassword);
		} else {
			List<WebElement> nextBtn = SeleniumUtil.getWebElements(d, "nextBtn", "LinkAnAccount", null);
			if (nextBtn.size() > 0 && nextBtn.get(0).isDisplayed()) {
				SeleniumUtil.click(nextBtn.get(0));
			}
			logger.info("Re enter Password field is not present.");
		}

		SeleniumUtil.waitForPageToLoad();

		List<WebElement> selectCheckBox = SeleniumUtil.getWebElements(d, "selectCheckBox", "LinkAnAccount", null);
		WebElement selectBox = null;
		if (selectCheckBox.size() > 0 && (selectBox = selectCheckBox.get(0)).isDisplayed()) {
			SeleniumUtil.click(selectBox);
			SeleniumUtil.waitForPageToLoad(2500);
		}

		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "submitBtn", "LinkAnAccount", null));

		WebDriverWait wait = new WebDriverWait(d, 500);

		By b;
		if (PropsUtil.getEnvPropertyValue("OB").equals("yes")) {

			b = By.xpath("//div[contains(text(),'successfully')]");
		} else {
			b = By.xpath("//div[contains(text(),'Congratulations')]");
		}
		String xpathForTechnicalError = "//*[contains(@class,\"errorSection\")]//*[text()=\"Technical Error\"]";
		String xpathForAccountsAdded = "//*[contains(@class,\"successAUFMedium\")]";
		String xpathForOKButton = "//*[@class=\"btnContent\" and @data-id=\"OK\"]";
		boolean status = false;
		A: for (int i = 0; i < 500; i++) {
			if (SeleniumUtil.isDisplayed(b, 0)) {
				logger.info("Account addition happened successfully");
				status = true;
				break;
			} else if (SeleniumUtil.isDisplayed(By.xpath(xpathForAccountsAdded), 0)) {
				logger.info("Account addition happened successfully");
				status = true;
				break;
			} else if (SeleniumUtil.isDisplayed(By.xpath(xpathForTechnicalError), 0)) {
				for (int j = 0; j < 3; j++) {
					if (addAccountAfterGettingTechnicalError(containerName, dagUserNaem, dagPassword, isContainer)) {
						status = true;
						break A;
					}
				}
			} else {
				SeleniumUtil.waitFor(1);
			}
		}

		return status;
	}

	public boolean addDagAccountUsingFL4(String dagUsername, String dagPassword) {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		dagUsername = new String(dagUsername);
		dagPassword = new String(dagPassword);
		d.findElement(SeleniumUtil.getByObject("FL4", null, "SearchBox")).click();
		d.findElement(SeleniumUtil.getByObject("FL4", null, "SearchBox")).clear();
//		SeleniumUtil.getWebElement(d, "SearchBox", "FL4", null).clear();
		d.findElement(SeleniumUtil.getByObject("FL4", null, "SearchBox")).sendKeys("Dag Site");
		SeleniumUtil.WaitUntilElementIsInVisible(SeleniumUtil.getByObject("FL4", null, "LoginSpinnerWheel"), 120);
		List<WebElement> sitename = SeleniumUtil.getWebElements(d, "AvailableSites", "FL4", null);
		for (int i = 0; i < sitename.size(); i++) {
			System.out.println(sitename.get(i).getText().split("\n")[0]);
		}
		sitename.stream().filter(t -> t.getText().split("\n")[0].trim().equalsIgnoreCase("Dag Site")).findFirst().get().click();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.getVisibileWebElement(d, "UsernameInputbox", "FL4", null).click();
		SeleniumUtil.getVisibileWebElement(d, "UsernameInputbox", "FL4", null).clear();
		SeleniumUtil.getVisibileWebElement(d, "UsernameInputbox", "FL4", null).sendKeys(dagUsername);
		SeleniumUtil.waitFor(1);
		WebElement pwd = null;
		pwd = SeleniumUtil.getVisibileWebElement(d, "PasswordInputbox", "FL4", null);
		pwd.click();
		pwd.clear();
		pwd.sendKeys(dagPassword);
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "SubmitButton", "FL4", null));
		SeleniumUtil.waitFor(3);
		SeleniumUtil.WaitUntilElementIsInVisible(SeleniumUtil.getByObject("FL4", null, "LoginSpinnerWheel"), 120);
		By b = SeleniumUtil.getByObject("FL4", null, "AccountsAddedSummaryTitle");
		boolean status = false;
		for (int i = 0; i < 100; i++) {
			if (SeleniumUtil.isDisplayed(b, 0)) {
				logger.info("Account addition happened successfully");
				status = true;
				break;
			} else {
				SeleniumUtil.waitFor(1);
			}
		}
		return status;
	}

	public ArrayList<String> getTheContainersAdded() {
		ArrayList<String> containersAdded = new ArrayList<String>();
		SeleniumUtil.waitUntilElementIsVisible(SeleniumUtil.getByObject("FL4", null, "ContainersAdded"), 60);
		List<WebElement> containers = SeleniumUtil.getWebElements(d, "ContainersAdded", "FL4", null);
		containers.stream().forEach(t -> {
			containersAdded.add(t.getText());
		});
		return containersAdded;
	}

	public void clickOnSaveAndFinishButton() {
		switchToFL4Frame();
		SeleniumUtil.click(SeleniumUtil.getByObject("FL4", null, "SaveAndFinishButton"));
		SeleniumUtil.waitFor(3);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}
	
	public void clickOnSaveAndFinishButtonForManualAccount() {
		switchToFL4Frame();
		SeleniumUtil.click(SeleniumUtil.getByObject("FL4", null, "ManualAccountSaveAndFinishButton"));
		SeleniumUtil.waitFor(3);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}
	
	public void clickOnSaveAndFinishButtonForRealEstateAccount() {
		switchToFL4Frame();
		SeleniumUtil.click(SeleniumUtil.getByObject("FL4", null, "RealEstateSaveAndFinishButton"));
		SeleniumUtil.waitFor(3);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public void clickOnSaveAndLinkMoreAccountsButton() {
		switchToFL4Frame();
		SeleniumUtil.click(SeleniumUtil.getByObject("FL4", null, "SaveAndLinkMoreAccounts"));
		SeleniumUtil.waitFor(3);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public void clickOnCancelButton() {
		switchToFL4Frame();
		SeleniumUtil.click(SeleniumUtil.getByObject("FL4", null, "AccountSummaryCancel"));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public void clickOnExitButtonInCancelPopup() {
		switchToFL4Frame();
		SeleniumUtil.click(SeleniumUtil.getByObject("FL4", null, "ExitPopup"));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public boolean addManualAccountInFL4(String accountType, String accountName, String dueOrBalance) {
		switchToFL4Frame();
		String pageName = "FL4";
		String frameName = null;
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "AddManualIcon"));
		SeleniumUtil.WaitUntilElementIsInVisible(SeleniumUtil.getByObject("FL4", null, "LoginSpinnerWheel"), 120);
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "AccountTypeDropdown"));
		SeleniumUtil.waitFor(1);
		List<WebElement> accountTypes = SeleniumUtil.getWebElements(d, "AccountTypes", pageName, frameName);
		accountTypes.stream().filter(t -> t.getText().trim().equalsIgnoreCase(accountType.toString())).findFirst().get()
				.click();
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "ManualAccountNameInputBox"));
		SeleniumUtil.sendKeys(d.findElement(SeleniumUtil.getByObject(pageName, frameName, "ManualAccountNameInputBox")),
				accountName);
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "NextButton"));
		SeleniumUtil.waitFor(2);
		SeleniumUtil.WaitUntilElementIsInVisible(SeleniumUtil.getByObject("FL4", null, "LoginSpinnerWheel"), 120);
		if (SeleniumUtil.isDisplayed(SeleniumUtil.getByObject(pageName, frameName, "BalanceInputBox"), 3)) {
			SeleniumUtil.sendKeys(d.findElement(SeleniumUtil.getByObject(pageName, frameName, "BalanceInputBox")),
					dueOrBalance);
		}
		if (SeleniumUtil.isDisplayed(SeleniumUtil.getByObject(pageName, frameName, "AmountDueInputBox"), 3)) {
			SeleniumUtil.sendKeys(d.findElement(SeleniumUtil.getByObject(pageName, frameName, "AmountDueInputBox")),
					dueOrBalance);
		}
		if (SeleniumUtil.isDisplayed(SeleniumUtil.getByObject(pageName, frameName, "DateInputBox"), 3)) {
			String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			SeleniumUtil.sendKeys(d.findElement(SeleniumUtil.getByObject(pageName, frameName, "DateInputBox")), date);
		}
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "ManualAccountSubmitButton"));
		SeleniumUtil.WaitUntilElementIsInVisible(SeleniumUtil.getByObject("FL4", null, "LoginSpinnerWheel"), 120);
		int i = 0;
		do {
			i++;
			if (SeleniumUtil.isDisplayed(SeleniumUtil.getByObject(pageName, frameName, "ManualAccountAddedHeader"),
					0)) {
				return true;
			}
		} while (i < 100);
		return false;
	}
	
	public void switchToFL4Frame() {
		d.switchTo().defaultContent();
		d.switchTo().frame("fl4Iframe");
	}
	
	public boolean addRealEstateAccountManually(String accountName, String assetvalue) {
		switchToFL4Frame();
		String pageName = "FL4";
		String frameName = null;
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "AddRealEstateIcon"));
		SeleniumUtil.sendKeys(d.findElement(SeleniumUtil.getByObject(pageName, frameName, "RealEstateAccountNameInput")), accountName);
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "RealEstateEnterManualValueRadioButton"));
		SeleniumUtil.waitFor(3);
		SeleniumUtil.sendKeys(d.findElement(SeleniumUtil.getByObject(pageName, frameName, "RealEstateEstimatedValueInputBox")), assetvalue);
		SeleniumUtil.click(SeleniumUtil.getByObject(pageName, frameName, "NextButton"));
		int i=0;
		do {
			i++;
			if (SeleniumUtil.isDisplayed(SeleniumUtil.getByObject(pageName, frameName, "RealEstateAccountAddedHeader"),
					0)) {
				return true;
			}
		} while (i < 100);
		return false;
	}
}
