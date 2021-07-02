/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.page;

import com.yodlee.customizationtool.constants.Constants;
import com.yodlee.customizationtool.util.GenericUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

public class ProductsAndAppsSelectionPage extends Page {

	private Logger log = LoggerFactory.getLogger(ProductsAndAppsSelectionPage.class);

	public String pageName = "PRODUCTS_AND_APPS_SELECTION_PAGE";

	public ProductsAndAppsSelectionPage(WebDriver driver) {
		super(driver);
	}

	public WebElement headerLabel() {
		return getWebElement(pageName, "productsAndAppsSelectionPageLbl");
	}

	public WebElement stepHeaderProductsAndAppsLabel() {
		return getWebElement(pageName, "paaspStepHeader");
	}

	public List<WebElement> stepHeaderLabels() {
		return getElementsFromPage(pageName, "paaspStepHeader");
	}

	public WebElement backToPreviousStepButton() {
		return getWebElement(pageName, "backToPreviousStepBtn");
	}

	public WebElement backToSubBrandListLink() {
		return getWebElement(pageName, "backToSubBrandsListLnk");
	}

	public WebElement saveAndContinueButton() {
		return getWebElement(pageName, "saveAndContinueBtn");
	}

	public WebElement noProductsSelectedErrorTextLabel() {
		return getWebElement(pageName, "noProductsSelectedErrorTxt");
	}

	public WebElement aggregationCheckBox() {
		return getWebElement(pageName, "aggregationChkBx");
	}

	public WebElement checkIfAggregationCheckBoxIsDisplayed() {
		return getWebElement(pageName, "aggregationExistsChkBx");
	}

	public WebElement verificationCheckBox() {
		return getWebElement(pageName, "verificationChkBx");
	}

	public WebElement aggregationDocumentDownloadToggleButton() {
		return getWebElement(pageName, "aggregationDocumentDownloadToggleBtn");
	}

	public WebElement verificationDocumentDownloadToggleButton() {
		return getWebElement(pageName, "verificationDocumentDownloadToggleBtn");
	}

	public WebElement verificationCDVEnabledToggleButton() {
		return getWebElement(pageName, "verificationCDVEnabledToggleBtn");
	}

	public WebElement verificationCDVCompanyNameInputBox() {
		return getWebElement(pageName, "verificationCDVCompanyInputBox");
	}

	public WebElement verificationCDVDescriptionInputBox() {
		return getWebElement(pageName, "verificationCDVDescription");
	}

	public WebElement yodleeFastlinkCheckBox() {
		return getWebElement(pageName, "yodleeFastlinkChkBx");
	}

	public WebElement yodleeWellnessFinappsCheckBox() {
		return getWebElement(pageName, "yodleeWellnessFinappsChkBx");
	}

	public WebElement dashboardToggleButton() {
		return getWebElement(pageName, "dashboardToggleBtn");
	}

	public WebElement accountsToggleButton() {
		return getWebElement(pageName, "accountsToggleBtn");
	}

	public WebElement finCheckToggleButton() {
		return getWebElement(pageName, "finCheckToggleBtn");
	}

	public WebElement cashFlowAnalysisToggleButton() {
		return getWebElement(pageName, "cashFlowAnalysisToggleBtn");
	}

	public WebElement investmentHoldingsToggleButton() {
		return getWebElement(pageName, "investmentHoldingsToggleBtn");
	}

	public WebElement okToSpendToggleButton() {
		return getWebElement(pageName, "okToSpendToggleBtn");
	}

	public WebElement transactionsToggleButton() {
		return getWebElement(pageName, "transactionsToggleBtn");
	}

	public WebElement budgetToggleButton() {
		return getWebElement(pageName, "budgetToggleBtn");
	}

	public WebElement incomeAndExpenseAnalysisToggleButton() {
		return getWebElement(pageName, "incomeAndExpenseAnalysisToggleBtn");
	}

	public WebElement networthSummaryToggleButton() {
		return getWebElement(pageName, "networthSummaryToggleBtn");
	}

	public WebElement saveForAGoalToggleButton() {
		return getWebElement(pageName, "saveForAGoalToggleBtn");
	}

	public WebElement settingsToggleButton() {
		return getWebElement(pageName, "settingsToggleBtn");
	}

	public void selectProductsAndAppDetails(String products, String subProductsAggregation,
			String subProductsVerification, String hostedApplications, String finapps) {

		log.info("**************************PRODUCTS AND APPS SELECTION**************************");
		selectProductsAndSubProducts(products, subProductsAggregation, subProductsVerification);
		selectHostedApp(hostedApplications);
		selectFinApps(finapps);
	}

	public void disableFinapps() {

		if (dashboardToggleButton().isSelected())
			dashboardToggleButton().click();
		if (accountsToggleButton().isSelected())
			accountsToggleButton().click();
		if (finCheckToggleButton().isSelected())
			finCheckToggleButton().click();
		if (cashFlowAnalysisToggleButton().isSelected())
			cashFlowAnalysisToggleButton().click();
		if (investmentHoldingsToggleButton().isSelected())
			investmentHoldingsToggleButton().click();
		if (okToSpendToggleButton().isSelected())
			okToSpendToggleButton().click();
		if (transactionsToggleButton().isSelected())
			transactionsToggleButton().click();
		if (budgetToggleButton().isSelected())
			budgetToggleButton().click();
		if (incomeAndExpenseAnalysisToggleButton().isSelected())
			incomeAndExpenseAnalysisToggleButton().click();
		if (networthSummaryToggleButton().isSelected())
			networthSummaryToggleButton().click();
		if (saveForAGoalToggleButton().isSelected())
			saveForAGoalToggleButton().click();
		if (settingsToggleButton().isSelected())
			settingsToggleButton().click();
	}

	public void selectFinApps(String finApps) {

		disableFinapps();
		String[] arrFinApps = {};
		if (finApps.contains(";")) {
			arrFinApps = finApps.split(";");

			for (String s : arrFinApps) {
				if (s.equalsIgnoreCase("Dashboard"))
					dashboardToggleButton().click();
				if (s.equalsIgnoreCase("Accounts"))
					accountsToggleButton().click();
				if (s.equalsIgnoreCase("Fincheck"))
					finCheckToggleButton().click();
				if (s.equalsIgnoreCase("Cash Flow Analysis"))
					cashFlowAnalysisToggleButton().click();
				if (s.equalsIgnoreCase("Investment Holdings"))
					investmentHoldingsToggleButton().click();
				if (s.equalsIgnoreCase("OK To Spend"))
					okToSpendToggleButton().click();
				if (s.equalsIgnoreCase("Transactions"))
					transactionsToggleButton().click();
				if (s.equalsIgnoreCase("Budget"))
					budgetToggleButton().click();
				if (s.equalsIgnoreCase("Expense And Income Analysis"))
					incomeAndExpenseAnalysisToggleButton().click();
				if (s.equalsIgnoreCase("Networth Summary"))
					networthSummaryToggleButton().click();
				if (s.equalsIgnoreCase("Save For A Goal"))
					saveForAGoalToggleButton().click();
				if (s.equalsIgnoreCase("Settings"))
					settingsToggleButton().click();
			}
		} else {
			if (finApps.equalsIgnoreCase("Dashboard"))
				dashboardToggleButton().click();
			if (finApps.equalsIgnoreCase("Accounts"))
				accountsToggleButton().click();
			if (finApps.equalsIgnoreCase("Fincheck"))
				finCheckToggleButton().click();
			if (finApps.equalsIgnoreCase("Cash Flow Analysis"))
				cashFlowAnalysisToggleButton().click();
			if (finApps.equalsIgnoreCase("Investment Holdings"))
				investmentHoldingsToggleButton().click();
			if (finApps.equalsIgnoreCase("OK To Spend"))
				okToSpendToggleButton().click();
			if (finApps.equalsIgnoreCase("Transactions"))
				transactionsToggleButton().click();
			if (finApps.equalsIgnoreCase("Budget"))
				budgetToggleButton().click();
			if (finApps.equalsIgnoreCase("Expense And Income Analysis"))
				incomeAndExpenseAnalysisToggleButton().click();
			if (finApps.equalsIgnoreCase("Networth Summary"))
				networthSummaryToggleButton().click();
			if (finApps.equalsIgnoreCase("Save For A Goal"))
				saveForAGoalToggleButton().click();
			if (finApps.equalsIgnoreCase("Settings"))
				settingsToggleButton().click();
		}
	}

	public void disableHostedApps() {

		if (yodleeFastlinkCheckBox().isSelected())
			yodleeFastlinkCheckBox().click();
		if (yodleeWellnessFinappsCheckBox().isSelected())
			yodleeWellnessFinappsCheckBox().click();
	}

	public void selectHostedApp(String hostedApps) {

		disableHostedApps();
		String[] arrHostedApps = {};
		if (hostedApps.contains(";")) {
			arrHostedApps = hostedApps.split(";");

			for (String s : arrHostedApps) {
				if (s.equalsIgnoreCase(Constants.SELECT_APPLICATIONS_YODLEE_FASTLINK))
					yodleeFastlinkCheckBox().click();
				if (s.equalsIgnoreCase(Constants.SELECT_APPLICATIONS_YODLEE_FINANCIAL_WELLNESS_FINAPPS))
					yodleeWellnessFinappsCheckBox().click();
			}
		} else {
			if (hostedApps.equalsIgnoreCase(Constants.SELECT_APPLICATIONS_YODLEE_FASTLINK))
				yodleeFastlinkCheckBox().click();
			if (hostedApps.equalsIgnoreCase(Constants.SELECT_APPLICATIONS_YODLEE_FINANCIAL_WELLNESS_FINAPPS))
				yodleeWellnessFinappsCheckBox().click();
		}
	}

	public void disableProductsAndSubProducts() {

		if (aggregationCheckBox().isSelected())
			aggregationCheckBox().click();

		if (verificationCheckBox().isSelected())
			verificationCheckBox().click();
	}

	public void selectProductsAndSubProducts(String products, String subProductsAggregation,
			String subProductsVerification) {

		disableProductsAndSubProducts();
		String[] arrProducts = {};
		if (products.contains(";")) {
			arrProducts = products.split(";");

			for (String s : arrProducts) {
				if (s.equalsIgnoreCase("Aggregation")) {
					if (!aggregationCheckBox().isSelected())
						aggregationCheckBox().click();
					if (subProductsAggregation.contains(";")) {
						String[] arrSubProductsAggregation = subProductsAggregation.split(";");
						for (String asp : arrSubProductsAggregation) {
							if (asp.equalsIgnoreCase("Document Download"))
								if (!aggregationDocumentDownloadToggleButton().isSelected())
									aggregationDocumentDownloadToggleButton().click();
						}
					} else {
						if (subProductsAggregation.equalsIgnoreCase("Document Download")) {
							if (!aggregationDocumentDownloadToggleButton().isSelected())
								aggregationDocumentDownloadToggleButton().click();
						} else {
							if (aggregationDocumentDownloadToggleButton().isSelected())
								aggregationDocumentDownloadToggleButton().click();
						}
					}
				}
				if (s.equalsIgnoreCase("Verification")) {
					if (!verificationCheckBox().isSelected())
						verificationCheckBox().click();
					if (subProductsVerification.contains(";")) {
						String[] arrSubProductsVerification = subProductsVerification.split(";");
						for (String vsp : arrSubProductsVerification) {
							if (vsp.equalsIgnoreCase("Document Download")) {
								if (!verificationDocumentDownloadToggleButton().isSelected())
									verificationDocumentDownloadToggleButton().click();
							}
						}
					} else {
						if (subProductsVerification.equalsIgnoreCase("Document Download")) {
							if (!verificationDocumentDownloadToggleButton().isSelected())
								verificationDocumentDownloadToggleButton().click();
						} else {
							if (verificationDocumentDownloadToggleButton().isSelected())
								verificationDocumentDownloadToggleButton().click();
						}
						if (!verificationCDVCompanyNameInputBox().isDisplayed()) {
							Assert.fail("CDV Company name is not displayed");
						}
						if (!verificationCDVDescriptionInputBox().isDisplayed()) {
							Assert.fail("CDV description inputbox is not displayed");
						}
						if (verificationCDVEnabledToggleButton().isSelected())
							verificationCDVEnabledToggleButton().click();
					}
				}
			}
		} else {
			if (products.equalsIgnoreCase("Aggregation")) {
				aggregationCheckBox().click();
				if (subProductsAggregation.contains(";")) {
					String[] arrSubProductsAggregation = subProductsAggregation.split(";");
					for (String asp : arrSubProductsAggregation) {
						if (asp.equalsIgnoreCase("Document Download")) {
							if (!aggregationDocumentDownloadToggleButton().isSelected())
								aggregationDocumentDownloadToggleButton().click();
						}
					}
				} else {
					if (subProductsAggregation.equalsIgnoreCase("Document Download")) {
						if (!aggregationDocumentDownloadToggleButton().isSelected())
							aggregationDocumentDownloadToggleButton().click();
					} else {
						if (aggregationDocumentDownloadToggleButton().isSelected())
							aggregationDocumentDownloadToggleButton().click();
					}
				}
			} else if (aggregationCheckBox().isSelected()) {
				aggregationCheckBox().click();
			}
			if (products.equalsIgnoreCase("Verification")) {
				verificationCheckBox().click();
				if (subProductsVerification.contains(";")) {
					String[] arrSubProductsVerification = subProductsVerification.split(";");
					for (String vsp : arrSubProductsVerification) {
						if (vsp.equalsIgnoreCase("Document Download")) {
							if (!verificationDocumentDownloadToggleButton().isSelected())
								verificationDocumentDownloadToggleButton().click();
						}
					}
				} else {
					if (subProductsVerification.equalsIgnoreCase("Document Download")) {
						if (!verificationDocumentDownloadToggleButton().isSelected())
							verificationDocumentDownloadToggleButton().click();
					} else {
						if (verificationDocumentDownloadToggleButton().isSelected())
							verificationDocumentDownloadToggleButton().click();
					}
					if (!verificationCDVCompanyNameInputBox().isDisplayed()) {
						Assert.fail("CDV Company name is not displayed");
					}
					if (!verificationCDVDescriptionInputBox().isDisplayed()) {
						Assert.fail("CDV description inputbox is not displayed");
					}
					if (verificationCDVEnabledToggleButton().isSelected())
						verificationCDVEnabledToggleButton().click();

				}
			} else if (verificationCheckBox().isSelected()) {
				verificationCheckBox().click();
			}
		}
	}
}