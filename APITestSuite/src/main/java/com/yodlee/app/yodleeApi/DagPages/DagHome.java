/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.DagPages;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.yodleeApi.common.Configuration;

public class DagHome {

	protected Logger logger = LoggerFactory.getLogger(DagHome.class);
	WebDriver driver;

	public DagHome(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("*********** Initializing " + this.getClass().getSimpleName() + "***********");
	}

	@FindBy(css = "input[name='catalog']")
	public WebElement username_textbox;

	@FindBy(css = "#password")
	public WebElement password_textbox;

	@FindBy(css = "#Submit")
	public WebElement submit_link;

	@FindBy(xpath = "//table[@id='showAllDAGAccounts']//table//td[1]/font")
	public List<WebElement> firstInnerTable;

	@FindBy(xpath = "//a[.='Edit']")
	public List<WebElement> edit_links;

	@FindBy(css = "input[name='myRequestFile']")
	public WebElement browse_link;

	@FindBy(id = "nickName")
	public WebElement nickName_textbox;

	@FindBy(css = "input[name='save']")
	public WebElement save_button;

	public void launchURL() {
		logger.info("Navigating to url=>  " + Configuration.getInstance().getDagUrl());
		driver.get(Configuration.getInstance().getDagUrl());
	}

	public void catlogLogin(String username, String password) {
		try {
			username_textbox.sendKeys(username);
			password_textbox.sendKeys(password);
			submit_link.click();
		} catch (Exception e) {
			Assert.fail("Exception in method catlogLogin of clsas DagHome:" + e);
		}
	}

	public void loginAndEnableContainer(String dagUrl, String catalogUserName, String catalogPassword,
			String siteUserName, String containerName) throws Exception {
		launchURL();
		catlogLogin(catalogUserName, catalogPassword);
		enableContainer(siteUserName, containerName);
	}

	public void loginAndDisableContainer(String dagUrl, String catalogUserName, String catalogPassword,
			String siteUserName, String containerName) throws Exception {
		launchURL();
		catlogLogin(catalogUserName, catalogPassword);
		disableContainer(siteUserName, containerName);
	}

	public void uploadXmlFile(String containerName, String fileName) throws Exception {

		String workingDir = System.getProperty("user.dir");
		System.out.println("Dir:" + workingDir);

		String filePath = null;

		filePath = workingDir + "\\src\\test\\resources\\TestData\\uploadFile\\";
		System.out.println("inside if file path:" + filePath);

		int index = 0;
		for (WebElement ele : firstInnerTable) {
			if (ele.getText().contains(containerName)) {
				break;
			}
			index++;
		}
		System.out.println("Edit position:" + index);
		edit_links.get(index).click(); // click on the edit link
		Thread.sleep(4000);
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			new KeyToSend().sendKeyBoardKeys("^"); // press enter
		}
		nickName_textbox.sendKeys("Edited");
		browse_link.click(); // click on the browse link
		Thread.sleep(5000);
		new KeyToSend().sendKeyBoardKeys("$" + filePath + fileName + "^");
		Thread.sleep(5000);
		save_button.click();// click on the save link
		Thread.sleep(30000);

	}

	public void disableContainer(String siteUserName, String containerName) throws Exception {

		List<WebElement> siteRowElements = driver.findElements(By.xpath(
				"//td[./font[normalize-space(text())='" + siteUserName + "']]/parent::tr/following-sibling::tr"));
		WebElement siteRowElement = siteRowElements.get(0);
		WebElement siteTableElement = siteRowElement.findElement(By.tagName("table"));
		List<WebElement> siteTableRowsElements = siteTableElement.findElements(By.xpath(".//tr[./td]"));
		boolean editClicked = false;
		for (int tableRowIndex = 0; tableRowIndex < siteTableRowsElements.size(); tableRowIndex++) {
			WebElement siteTableRowElement = siteTableRowsElements.get(tableRowIndex);
			List<WebElement> siteTableColElements = siteTableRowElement.findElements(By.xpath(".//td"));
			String containerNameInUI = siteTableColElements.get(0).getText();
			if (containerNameInUI != null && containerNameInUI.toLowerCase().contains(containerName.toLowerCase())) {
				String enabledOrDisabledString = siteTableColElements.get(5).getText();
				if (enabledOrDisabledString != null && enabledOrDisabledString.trim().equals("Enable")) {
					System.out.println("Container - " + containerName
							+ " is already disabled for site with user name - " + siteUserName);
					return;
				}
				siteTableColElements.get(5).findElement(By.xpath("./font/a")).click();
				editClicked = true;
				break;
			}
		}
		if (editClicked == false) {
			throw new UnexpectedFunctionalityException(
					"Container Name - " + containerName + " not found for site with user name - " + siteUserName);
		}
		waitForAlertToBeDisplayed();
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			new KeyToSend().sendKeyBoardKeys("^"); // press enter
		}
		waitForHomePageToLoad();
	}

	public void enableContainer(String siteUserName, String containerName) throws Exception {

		List<WebElement> siteRowElements = driver.findElements(By.xpath(
				"//td[./font[normalize-space(text())='" + siteUserName + "']]/parent::tr/following-sibling::tr"));
		WebElement siteRowElement = siteRowElements.get(0);
		WebElement siteTableElement = siteRowElement.findElement(By.tagName("table"));
		List<WebElement> siteTableRowsElements = siteTableElement.findElements(By.xpath(".//tr[./td]"));
		boolean editClicked = false;
		for (int tableRowIndex = 0; tableRowIndex < siteTableRowsElements.size(); tableRowIndex++) {
			WebElement siteTableRowElement = siteTableRowsElements.get(tableRowIndex);
			List<WebElement> siteTableColElements = siteTableRowElement.findElements(By.xpath(".//td"));
			String containerNameInUI = siteTableColElements.get(0).getText();
			if (containerNameInUI != null && containerNameInUI.toLowerCase().contains(containerName.toLowerCase())) {
				String enabledOrDisabledString = siteTableColElements.get(5).getText();
				if (enabledOrDisabledString != null && enabledOrDisabledString.trim().equals("Disable")) {
					System.out.println("Container - " + containerName + " is already enabled for site with user name - "
							+ siteUserName);
					return;
				}
				siteTableColElements.get(5).findElement(By.xpath("./font/a")).click();
				editClicked = true;
				break;
			}
		}
		if (editClicked == false) {
			throw new UnexpectedFunctionalityException(
					"Container Name - " + containerName + " not found for site with user name - " + siteUserName);
		}
		waitForAlertToBeDisplayed();
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			new KeyToSend().sendKeyBoardKeys("^"); // press enter
		}
		waitForHomePageToLoad();
	}

	public void loginAndUploadDagXml(String dagUrl, String catalogUserName, String catalogPassword,
			String dagSiteUserName, String xmlTobeUploaded_path, String dagXml, String container) throws Exception {

		launchURL();
		catlogLogin(catalogUserName, catalogPassword);
		uploadXmlFile(dagSiteUserName, container, xmlTobeUploaded_path, dagXml);
		driver.quit();
	}

	public void close() {
		driver.quit();
	}

	public void uploadXmlFile(String siteUserName, String containerName, String filePath, String fileName)
			throws Exception {
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + File.separator + "src" + File.separator + "test" + File.separator + "resources";
		File file = new File(workingDir + File.separator + filePath + File.separator + fileName);
		if (!file.exists()) {
			driver.close();
			throw new UnexpectedFunctionalityException("DAG XML File is not present in the path - " + workingDir
					+ File.separator + filePath + File.separator + fileName);
		}
		List<WebElement> siteRowElements = driver.findElements(By.xpath(
				"//td[./font[normalize-space(text())='" + siteUserName + "']]/parent::tr/following-sibling::tr"));
		if (siteRowElements.isEmpty()) {
			throw new UnexpectedFunctionalityException("No site available with site name : " + siteUserName);
		}
		WebElement siteRowElement = siteRowElements.get(0);
		WebElement siteTableElement = siteRowElement.findElement(By.tagName("table"));
		List<WebElement> siteTableRowsElements = siteTableElement.findElements(By.xpath(".//tr[./td]"));
		boolean editClicked = false;
		for (int tableRowIndex = 0; tableRowIndex < siteTableRowsElements.size(); tableRowIndex++) {
			WebElement siteTableRowElement = siteTableRowsElements.get(tableRowIndex);
			List<WebElement> siteTableColElements = siteTableRowElement.findElements(By.xpath(".//td"));
			String containerNameInUI = siteTableColElements.get(0).getText();
			if (containerNameInUI != null && containerNameInUI.toLowerCase().contains(containerName.toLowerCase())) {
				siteTableColElements.get(4).findElement(By.xpath("./font/a")).click();
				editClicked = true;
				break;
			}
		}
		if (editClicked == false) {
			throw new UnexpectedFunctionalityException(
					"Container Name - " + containerName + " not found for site with user name - " + siteUserName);
		}
		waitForAlertToBeDisplayed();
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			new KeyToSend().sendKeyBoardKeys("^"); // press enter
		}
		waitForEditPageToLoad();
		String randomNumber = String.valueOf(Math.random()).replaceAll("0.", "").substring(0, 4);
		nickName_textbox.sendKeys("Edited_" + randomNumber);
		System.out.println("Nickname set = Edited_" + randomNumber);
		browse_link.sendKeys(workingDir + File.separator + filePath + File.separator + fileName);
		save_button.click();// click on the save link
		waitForHomePageToLoad();
		siteRowElements = driver.findElements(By.xpath(
				"//td[./font[normalize-space(text())='" + siteUserName + "']]/parent::tr/following-sibling::tr"));
		if (siteRowElements.isEmpty()) {
			throw new UnexpectedFunctionalityException("No site available with site name : " + siteUserName);
		}
		siteRowElement = siteRowElements.get(0);
		siteTableElement = siteRowElement.findElement(By.tagName("table"));
		siteTableRowsElements = siteTableElement.findElements(By.xpath(".//tr[./td]"));
		for (int tableRowIndex = 0; tableRowIndex < siteTableRowsElements.size(); tableRowIndex++) {
			WebElement siteTableRowElement = siteTableRowsElements.get(tableRowIndex);
			List<WebElement> siteTableColElements = siteTableRowElement.findElements(By.xpath(".//td"));
			String containerNameInUI = siteTableColElements.get(0).getText();
			if (containerNameInUI != null && containerNameInUI.toLowerCase().contains(containerName.toLowerCase())) {
				String nickName = siteTableColElements.get(2).getText();
				if (nickName != null && nickName.trim().equals("Edited_" + randomNumber)) {
					break;
				} else
					throw new UnexpectedFunctionalityException("DAG Site edit did not happen." + "Actual Nickname : "
							+ nickName + ". Expected Nickname: Edited_" + randomNumber);
			}
		}
	}

	public void waitForElementToBeClickable(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (TimeoutException e) {
			System.out.println("Time out occurred while waiting for element - " + by.toString());
			throw e;
		}
	}

	public void waitForEditPageToLoad() {
		waitForElementToBeClickable(By.xpath("//input[@value='Save']"));
	}

	public void waitForHomePageToLoad() {
		waitForElementToBeClickable(By.xpath("//input[@value='Add']"));
	}

	public void waitForAlertToBeDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			throw e;
		}
	}

}
