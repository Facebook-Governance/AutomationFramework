/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All  Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author rprakash
 ******************************************************************************/
package com.omni.pfm.pages.TransactionEnhancement1;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class Editscreen_Savechanges_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(Editscreen_Savechanges_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	String pageName = "Transaction";

	public Editscreen_Savechanges_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement addInvestmentmanualTxn() {
		return getVisibileWebElement(d, "addManualTxnlink", pageName, null);
	}

	public WebElement saveChangesButton() {
		return getVisibileWebElement(d, "saveChangesbutton", pageName, null);
	}

	public List<WebElement> transactionRow() {
		return getWebElements(d, "transactionRow", pageName, null);
	}

	public WebElement transactionToastMessage() {
		return getWebElement(d, "toastMessage", pageName, null);
	}

	public WebElement upcomingTransaction() {
		return getVisibileWebElement(d, "projectedTxnsection", pageName, null);
	}

	public WebElement upcomingTransactionrow() {
		return getVisibileWebElement(d, "projectedTxnrow", pageName, null);
	}

	public void projectedTransaction() {
		waitForPageToLoad(2000);
		click(this.upcomingTransaction());
		waitForPageToLoad(2000);
		click(this.upcomingTransactionrow());
		click(this.saveChangesButton());
	}

	public WebElement ftue1Expenseanalysis() {
		return getVisibileWebElement(d, "ExpenseFTUE1", pageName, null);
	}

	public List<WebElement> ftue2Expenseanalysis() {
		return getWebElements(d, "ExpenseFTUE2", pageName, null);
	}

	public List<WebElement> expenseLegendtable() {
		return getWebElements(d, "ExpenseLegend", pageName, null);
	}

	public WebElement totaltranamount() {
		return getWebElement(d, "totaltranamount", "Expense", null);
	}

	public boolean istotaltranamount() {
		return PageParser.isElementPresent("totaltranamount", "Expense", null);
	}

	public List<WebElement> expenseBycategory() {
		return getWebElements(d, "ExpenseBycategory", pageName, null);
	}

	public WebElement expenseTransactionrow() {
		return getVisibileWebElement(d, "ExpenseBycategoryTxn", pageName, null);
	}

	public WebElement closeicon() {
		return getVisibileWebElement(d, "closeicon", "Expense", null);
	}

	public boolean iscloseicon() {
		return PageParser.isElementPresent("closeicon", "Expense", null);
	}

	public WebElement backbuttonmobile() {
		return getVisibileWebElement(d, "backbuttonmobile", "Expense", null);
	}

	public void expenseSavechangesvalidation() {
		click(ftue1Expenseanalysis());
		waitForPageToLoad(2000);
		click(ftue2Expenseanalysis().get(1));
		waitForPageToLoad(2000);
		waitUntilSpinnerWheelIsInvisible();
		click(expenseLegendtable().get(0));
		waitForPageToLoad(3000);
		click(expenseBycategory().get(0));
		waitForPageToLoad(2000);
		if (istotaltranamount()) {
			click(totaltranamount());
			waitForPageToLoad(2000);
		}
	}

	public WebElement backtoExpenseanalysis() {
		return getVisibileWebElement(d, "backtoExpensebycategory", pageName, null);
	}

	public WebElement titleDropdown() {
		return getVisibileWebElement(d, "clickonTitledropdown", pageName, null);
	}

	public WebElement incomeAnalysistitle() {
		return getVisibileWebElement(d, "selectIncomeintitledropdown", pageName, null);
	}

	public WebElement incomeCategory() {
		return getVisibileWebElement(d, "incomeBycategory", pageName, null);
	}

	public void incomeSavechangesvalidation(int monthrow, int categoryRow) {
		WebDriverWait wait = new WebDriverWait(d, 60);
		WebElement backToexp = wait.until(ExpectedConditions.visibilityOf(this.backtoExpenseanalysis()));
		click(backToexp);
		waitForPageToLoad();
		WebElement title = wait.until(ExpectedConditions.visibilityOf(this.titleDropdown()));
		click(title);
		WebElement incomeTitle = wait.until(ExpectedConditions.visibilityOf(this.incomeAnalysistitle()));
		click(incomeTitle);
		waitForPageToLoad();
		WebElement expenseLegend = wait.until(ExpectedConditions.visibilityOf(this.expenseLegendtable().get(monthrow)));
		click(expenseLegend);
		waitForPageToLoad();
		WebElement expenseCategory = wait
				.until(ExpectedConditions.visibilityOf(this.expenseBycategory().get(categoryRow)));
		click(expenseCategory);
	}

	public WebElement fincheckIndicator2() {
		return getVisibileWebElement(d, "fincheckIndicator2", pageName, null);
	}

	public WebElement fincheckIndicator2missedevent() {
		return getVisibileWebElement(d, "fincheckIndicator2missedevent", pageName, null);
	}

	public WebElement fincheckIndicator2missedevensavechanges() {
		return getVisibileWebElement(d, "fincheckIndicator2savebutton", pageName, null);
	}

	public WebElement ok2SpendFtue() {
		return getVisibileWebElement(d, "ok2spendFTUE", pageName, null);
	}

	public WebElement ok2SpendNextbtn() {
		return getVisibileWebElement(d, "ok2spendNextbtn", pageName, null);
	}

	public WebElement ok2snextbtnmobile() {
		return getVisibileWebElement(d, "ok2snextbtnmobile", pageName, null);
	}

	public boolean isok2snextbtnmobile() {
		return PageParser.isElementPresent("ok2snextbtnmobile", pageName, null);
	}

	public List<WebElement> ok2spendEvents() {
		return getWebElements(d, "ok2spendEvents", pageName, null);
	}

	public WebElement ok2spendEventedit() {
		return getVisibileWebElement(d, "ok2spendEventedit", pageName, null);
	}

	public void ok2SpendFinappvalidation() {
		waitForPageToLoad(2000);
		click(this.ok2SpendFtue());
		waitForPageToLoad(2000);
		if (isok2snextbtnmobile()) {
			click(ok2snextbtnmobile());
			waitForPageToLoad(2000);
			click(this.ok2spendEvents().get(0));
			waitForPageToLoad(2000);
			click(this.ok2spendEventedit());
			waitForPageToLoad(1000);
			click(ok2snextbtnmobile());
		} else {
			click(this.ok2SpendNextbtn());
			waitForPageToLoad(2000);
			click(this.ok2spendEvents().get(0));
			waitForPageToLoad(2000);
			click(this.ok2spendEventedit());
			waitForPageToLoad(1000);
			click(this.ok2SpendNextbtn());
		}
	}

	public WebElement budgetFtue() {
		return getVisibileWebElement(d, "budgetgetStarted", pageName, null);
	}

	public WebElement budgetStep1() {
		return getVisibileWebElement(d, "budgetStep1", pageName, null);
	}

	public WebElement budgetStep2() {
		return getVisibileWebElement(d, "budgetStep2", pageName, null);
	}

	public WebElement budgetStep3() {
		return getVisibileWebElement(d, "budgetStep3", pageName, null);
	}

	public List<WebElement> budgetSpendingrow() {
		return getWebElements(d, "budgetSpendingrow", pageName, null);
	}

	public WebElement viewmoredetailsmobile() {
		return getVisibileWebElement(d, "viewmoredetailsmobile", pageName, null);
	}

	public boolean isviewmoredetailsmobile() {
		return PageParser.isElementPresent("viewmoredetailsmobile", pageName, null);
	}

	public WebElement budgetThismonthtxn() {
		return getVisibileWebElement(d, "budgetThismonthtxn", pageName, null);
	}

	public WebElement budgetThismonthtxnEvent() {
		return getVisibileWebElement(d, "budgetThismonthtxnedit", pageName, null);
	}

	public void budgetEditscreenValidation() {
		if (isviewmoredetailsmobile()) {
			click(viewmoredetailsmobile());
		}
		click(this.budgetSpendingrow().get(0));
		waitForPageToLoad(4000);
		click(this.budgetThismonthtxn());
	}

	public WebElement fincheckCreditscoreDropdown() {
		return getVisibileWebElement(d, "fincheckdropdown", pageName, null);
	}

	public WebElement fincheckCreditscoreDropdownSelection() {
		return getVisibileWebElement(d, "fincheckdropdownselection", pageName, null);
	}
}