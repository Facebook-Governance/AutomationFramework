/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewListOfSubBrandsPage extends Page {

	private Logger log = LoggerFactory.getLogger(ViewListOfSubBrandsPage.class);

	public String pageName = "VIEW_LIST_OF_SUB_BRANDS_PAGE";

	public ViewListOfSubBrandsPage(WebDriver driver) {
		super(driver);
	}

	public WebElement createSubBrandButton() {
		return getVisibleWebElement(pageName, "createSubBrandBtn");
	}

	public WebElement deleteSubBrandButton() {
		return getVisibleWebElement(pageName, "deleteSubBrandBtn");
	}

	public WebElement confirmDelSubBrandBtn() {
		return getVisibleWebElement(pageName, "confirmDeleteSubBrandBtn");
	}

	public WebElement subBrandNameLink(String subBrandName) {
		return getWebElementByReplacingToken(pageName, "subBrandNameLnk", subBrandName);
	}

	public WebElement totalSubrands() {
		return getWebElement(pageName, "totalListedSubBrandsSubHeaderLbl");
	}

	public WebElement pageNumberTextBox() {
		return getWebElement(pageName, "pageNumberTxtBx");
	}

	public WebElement totalPagesLabel() {
		return getWebElement(pageName, "totalPagesLbl");
	}

	public WebElement previousPagePaginationLink() {
		return getWebElement(pageName, "previousPagePaginationLnk");
	}

	public WebElement nextPagePaginationLink(String index) {
		return getWebElementByReplacingToken(pageName, "nextPagePaginationLnk", index);
	}

	public WebElement nextPagePaginationBtn(String index) {
		return getWebElementByReplacingToken(pageName, "nextPagePaginationBtn", index);
	}

	public List<WebElement> listOfPagesPaginationLabel() {
		return getElementsFromPage(pageName, "listOfPagesPaginationLbl");
	}

	public WebElement noResultsFoundTextLabel() {
		return getWebElement(pageName, "noResultsFoundTextLbl");
	}

	public WebElement closeSearchFilterLink() {
		return getWebElement(pageName, "closeSearchFilterLnk");
	}

	public WebElement colHeaderSubBrandName() {
		return getWebElement(pageName, "colHeaderSubBrandName");
	}

	public WebElement sortBySubBrandNameDD() {
		return getWebElement(pageName, "sortBySubBrandNameDD");
	}

	public WebElement sortTypeSubBrandName() {
		return getWebElement(pageName, "sortTypeSubBrandName");
	}

	public WebElement colHeaderDeploymentStatus() {
		return getWebElement(pageName, "colHeaderDeploymentStatus");
	}

	public WebElement sortByDeploymentStatusDD() {
		return getWebElement(pageName, "sortByDeploymentStatusDD");
	}

	public WebElement sortTypeDeploymentStatus() {
		return getWebElement(pageName, "sortTypeDeploymentStatus");
	}

	public WebElement colHeaderEnvironmentStatus() {
		return getWebElement(pageName, "colHeaderEnvironmentStatus");
	}

	public WebElement sortByEnvironmentStatusDD() {
		return getWebElement(pageName, "sortByEnvironmentStatusDD");
	}

	public WebElement sortTypeEnvironmentStatus() {
		return getWebElement(pageName, "sortTypeEnvironmentStatus");
	}

	public WebElement colHeaderLastPublishedStatus() {
		return getWebElement(pageName, "colHeaderLastPublishedStatus");
	}

	public WebElement sortByLastPublishedDD() {
		return getWebElement(pageName, "sortByLastPublishedDD");
	}

	public WebElement sortTypeLastPublished() {
		return getWebElement(pageName, "sortTypeLastPublished");
	}

	public WebElement searchFilterTxtBx() {
		return getWebElement(pageName, "searchFilterTxtBx");
	}

	public WebElement popUpPublishToPrivateBtn() {
		return getWebElement(pageName, "popUpPublishToPrivateButton");
	}

	public WebElement popUpPublishToPublicBtn() {
		return getWebElement(pageName, "popUpPublishToPublicButton");
	}

	public WebElement popUpReviewDetailsBtn() {
		return getWebElement(pageName, "popUpReviewDetailsButton");
	}

	public WebElement deploymentStatus(String subBrandName) {
		return getWebElementByReplacingToken(pageName, "deploymentStatus", subBrandName);
	}

	public WebElement environmentStatus(String subBrandName) {
		return getWebElementByReplacingToken(pageName, "environmentStatus", subBrandName);
	}

	public WebElement lastPublished(String subBrandName) {
		return getWebElementByReplacingToken(pageName, "lastPublished", subBrandName);
	}

	public List<WebElement> getSubBrandNames() {
		return getElementsFromPage(pageName, "listOfSubBrandNames");
	}

	public List<WebElement> getListDeploymentStatus() {
		return getElementsFromPage(pageName, "listOfDeploymentStatus");
	}

	public List<WebElement> getListEnvironmentStatus() {
		return getElementsFromPage(pageName, "listOfEnvironmentStatus");
	}

	public List<WebElement> getListLastPublished() {
		return getElementsFromPage(pageName, "listOfLastPublished");
	}

	public String getTopThreeSubBrandFromHomePage() {

		List<WebElement> listOfSubBrands = getSubBrandNames();

		if (listOfSubBrands.size() > 0)
			return listOfSubBrands.get(0).getText() + ";" + listOfSubBrands.get(1).getText() + ";"
					+ listOfSubBrands.get(2).getText();

		return "";
	}

	public String getFirstSubBrandFromHomePage() {

		List<WebElement> listOfSubBrands = getSubBrandNames();

		if (listOfSubBrands.size() > 0)
			return listOfSubBrands.get(0).getText();

		return "";
	}

	public boolean isPresentCreateSubBrandButton() {
		return createSubBrandButton().isDisplayed();
	}

	public boolean isEnabledCreateSubBrandButton() {
		return createSubBrandButton().isEnabled();
	}

	public boolean isPresentSearchFilterTxtBx() {
		return searchFilterTxtBx().isDisplayed();
	}

	public void searchBySubBrand(String subBrandName) {

		searchFilterTxtBx().sendKeys(subBrandName);
	}

	public String getDeploymentStatus(String subBrandName) {
		return deploymentStatus(subBrandName).getText();
	}

	public String getEnvironmentStatus(String subBrandName) {
		return environmentStatus(subBrandName).getText();
	}

	public String getLastPublished(String subBrandName) {
		return lastPublished(subBrandName).getText();
	}

	public void clickEditSubBrand(String subBrandName) {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		WebElement e = getVisibleWebElementByReplacingToken(pageName, "editSubBrandButton", subBrandName);
		e.click();
	}

	public void clickPublishToPrivate(String subBrandName) {

		WebElement e = getWebElementByReplacingToken(pageName, "publishToPrivateButton", subBrandName);
		e.click();
	}

	public void clickPublishToPublic(String subBrandName) throws InterruptedException {

		WebElement e = getWebElementByReplacingToken(pageName, "publishToPublicButton", subBrandName);
		e.click();
	}

	public void clickConfigureApps(String subBrandName) throws InterruptedException {

		WebElement e = getWebElementByReplacingToken(pageName, "configureAppsButton", subBrandName);
		e.click();
	}

	public String getPublishBtnName(String subBrandName) throws InterruptedException {

		WebElement e = getWebElementByReplacingToken(pageName, "publishButtonName", subBrandName);
		return e.getText();
	}

	public void clickCreateSubBrandButton() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		createSubBrandButton().click();
	}

	public WebElement userNameMenuBar() {
		return getWebElement(pageName, "usernameMenuLnk");
	}

	public WebElement logoutLink() {
		return getWebElement(pageName, "logoutLnk");
	}

	public void logout() {

		if (userNameMenuBar().isDisplayed())
			userNameMenuBar().click();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(logoutLink()));

		logoutLink().click();

	}

	public ArrayList<String> getSubBrandNamesColumnContents() {

		int totalPages = Integer.parseInt(totalPagesLabel().getText());
		int j;
		List<WebElement> lstSubBrand;
		List<WebElement> listOfPages;
		ArrayList<String> listSubBrands = new ArrayList();

		for (int i = 1; i <= totalPages; i++) {

			lstSubBrand = getSubBrandNames();

			j = 0;
			for (WebElement ele : lstSubBrand) {
				listSubBrands.add(ele.getText().toUpperCase());

				if (++j == lstSubBrand.size()) {
					listOfPages = listOfPagesPaginationLabel();
					String index = String.valueOf(listOfPages.size());
					if (!nextPagePaginationBtn(index).getAttribute("class").contains("disabled"))
						nextPagePaginationLink(index).click();
					else
						log.info("In Last Page. Next Page Navigation Not Possible. Link Is Disabled.");
				}
			}
		}

		clearText(pageNumberTextBox());
		pageNumberTextBox().sendKeys("1");

		return listSubBrands;
	}

	public ArrayList<String> getEnvironmentStatusColumnContents() {

		int totalPages = Integer.parseInt(totalPagesLabel().getText());
		int j;
		List<WebElement> lstSubBrand;
		List<WebElement> listOfPages;
		ArrayList<String> listEnvStatus = new ArrayList();
		// ********************************************************************
		String sbName = "";
		// ********************************************************************

		for (int i = 1; i <= totalPages; i++) {

			lstSubBrand = getSubBrandNames();

			j = 0;
			for (WebElement ele : lstSubBrand) {

				listEnvStatus.add(getEnvironmentStatus(ele.getText()));

				if (++j == lstSubBrand.size()) {
					listOfPages = listOfPagesPaginationLabel();
					String index = String.valueOf(listOfPages.size());
					if (!nextPagePaginationBtn(index).getAttribute("class").contains("disabled"))
						nextPagePaginationLink(index).click();
					else
						log.info("In Last Page. Next Page Navigation Not Possible. Link Is Disabled.");
				}
			}
		}

		clearText(pageNumberTextBox());
		pageNumberTextBox().sendKeys("1");

		return listEnvStatus;
	}

	public ArrayList<String> getDeploymentStatusColumnContents() {

		int totalPages = Integer.parseInt(totalPagesLabel().getText());
		int j;
		List<WebElement> lstSubBrand;
		List<WebElement> listOfPages;
		ArrayList<String> listDepStatus = new ArrayList();

		for (int i = 1; i <= totalPages; i++) {

			lstSubBrand = getSubBrandNames();

			j = 0;

			for (WebElement ele : lstSubBrand) {

				listDepStatus.add(getDeploymentStatus(ele.getText()));

				if (++j == lstSubBrand.size()) {
					listOfPages = listOfPagesPaginationLabel();
					String index = String.valueOf(listOfPages.size());
					if (!nextPagePaginationBtn(index).getAttribute("class").contains("disabled"))
						nextPagePaginationLink(index).click();
					else
						log.info("In Last Page. Next Page Navigation Not Possible. Link Is Disabled.");
				}
			}
		}

		clearText(pageNumberTextBox());
		pageNumberTextBox().sendKeys("1");

		return listDepStatus;
	}

	public ArrayList<String> getLastPublishedColumnContents() {

		int totalPages = Integer.parseInt(totalPagesLabel().getText());
		int j;
		List<WebElement> lstSubBrand;
		List<WebElement> listOfPages;
		ArrayList<String> listLastPub = new ArrayList();

		for (int i = 1; i <= totalPages; i++) {

			lstSubBrand = getSubBrandNames();

			j = 0;
			for (WebElement ele : lstSubBrand) {

				listLastPub.add(getLastPublished(ele.getText()));

				if (++j == lstSubBrand.size()) {
					listOfPages = listOfPagesPaginationLabel();
					String index = String.valueOf(listOfPages.size());
					if (!nextPagePaginationBtn(index).getAttribute("class").contains("disabled"))
						nextPagePaginationLink(index).click();
					else
						log.info("In Last Page. Next Page Navigation Not Possible. Link Is Disabled.");
				}
			}
		}

		clearText(pageNumberTextBox());
		pageNumberTextBox().sendKeys("1");

		return listLastPub;
	}

	public ArrayList<String> getFixedListOfDeploymentStatus() {
		ArrayList<String> fixedListOfDeploymentStatus = new ArrayList();
		fixedListOfDeploymentStatus.add("Publish initiated in Private");
		fixedListOfDeploymentStatus.add("Publish initiated in Public");
		fixedListOfDeploymentStatus.add("Publish failed in Private");
		fixedListOfDeploymentStatus.add("Publish failed in Public");
		fixedListOfDeploymentStatus.add("Published in Private");
		fixedListOfDeploymentStatus.add("Published in Public");
		fixedListOfDeploymentStatus.add("Never Published");
		fixedListOfDeploymentStatus.add("Disabled");

		return fixedListOfDeploymentStatus;
	}

	public ArrayList<String> getFixedListOfEnvironmentStatus() {
		ArrayList<String> fixedListOfEnvironmentStatus = new ArrayList();
		fixedListOfEnvironmentStatus.add("Draft");
		fixedListOfEnvironmentStatus.add("Private");
		fixedListOfEnvironmentStatus.add("Public");
		fixedListOfEnvironmentStatus.add("Disabled");

		return fixedListOfEnvironmentStatus;
	}

	public ArrayList<String> getAscendingSortedList(ArrayList<String> fixedList, ArrayList<String> runTimeList) {

		int fixedListCounter = 0;
		int runTimeListCounter = 0;

		ArrayList<String> finalArrList = new ArrayList();

		for (int i = 0; i < runTimeList.size(); i++) {

			if (fixedListCounter < fixedList.size()) {

				if (runTimeList.get(runTimeListCounter).equalsIgnoreCase(fixedList.get(fixedListCounter))) {
					finalArrList.add(runTimeList.get(i));
					runTimeListCounter++;
				} else {
					finalArrList.add(runTimeList.get(i));
					fixedListCounter++;
					runTimeListCounter++;
				}
			}
		}
		return finalArrList;
	}

	public ArrayList<String> getDescendingSortedList(ArrayList<String> fixedList, ArrayList<String> runTimeList) {

		int fixedListCounter = fixedList.size() - 1;
		int runTimeListCounter = 0;

		ArrayList<String> finalArrList = new ArrayList();

		for (int i = 0; i < runTimeList.size(); i++) {

			while (fixedListCounter >= 0) {

				if (runTimeList.get(runTimeListCounter).equalsIgnoreCase(fixedList.get(fixedListCounter))) {
					finalArrList.add(runTimeList.get(i));
					runTimeListCounter++;
					break;
				} else {
					finalArrList.add(runTimeList.get(i));
					fixedListCounter--;
					runTimeListCounter++;
					break;
				}
			}
		}
		return finalArrList;
	}

	public ArrayList<String> getUpdatedLastPublishStatusList(ArrayList<String> list) {

		ArrayList<String> updatedDefaultOrderedListOfLastPublished = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		String currentDate;
		for (String s : list) {
			if (s.contains("Today")) {
				currentDate = sdf.format(new Date());
				updatedDefaultOrderedListOfLastPublished.add(s.replace("Today", currentDate));
			} else
				updatedDefaultOrderedListOfLastPublished.add(s);
		}

		return updatedDefaultOrderedListOfLastPublished;
	}

}
