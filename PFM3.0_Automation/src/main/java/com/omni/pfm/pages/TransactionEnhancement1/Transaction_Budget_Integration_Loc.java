/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.testBase.TestBase;

public class Transaction_Budget_Integration_Loc extends TestBase {
	static Logger logger = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	String TransactionPage = "Transaction";

	public Transaction_Budget_Integration_Loc(WebDriver d) {
		this.d = d;

	}

	public List<WebElement> PendingStranctionList() {
		return SeleniumUtil.getWebElements(d, "PendingStranctionList_TBI", TransactionPage, null);
	}

	public List<WebElement> ATMwithdrow() {
		return SeleniumUtil.getWebElements(d, "ATMwithdrow_TBI", TransactionPage, null);
	}

	public List<WebElement> MobileATMwithdrow_TBI() {
		return SeleniumUtil.getWebElements(d, "MobileATMwithdrow_TBI", TransactionPage, null);
	}

	public List<WebElement> transactionrow() {
		return SeleniumUtil.getWebElements(d, "transactionrow_TBI", TransactionPage, null);
	}

	public WebElement budgetDescription() {
		return SeleniumUtil.getWebElement(d, "budgetDescription_TBI", TransactionPage, null);
	}

	public WebElement detailsnoteField() {
		return SeleniumUtil.getWebElement(d, "detailsnoteField_TBI", TransactionPage, null);
	}

	public List<WebElement> budgetTags() {
		return SeleniumUtil.getWebElements(d, "budgetTags_TBI", TransactionPage, null);
	}

	public WebElement budgetAmount() {
		return SeleniumUtil.getWebElement(d, "budgetAmount", TransactionPage, null);
	}

	public WebElement budgetAmountLable() {
		return SeleniumUtil.getWebElement(d, "budgetAmountLable", TransactionPage, null);
	}

	public WebElement budgetDescriptionLable() {
		return SeleniumUtil.getWebElement(d, "budgetDescriptionLable", TransactionPage, null);
	}

	public WebElement budgetSimpleLable() {
		return SeleniumUtil.getWebElement(d, "budgetSimpleLable", TransactionPage, null);
	}

	public WebElement budgetSimpleDescvalue() {
		return SeleniumUtil.getWebElement(d, "budgetSimpleDescvalue", TransactionPage, null);
	}

	public WebElement budgetDabitedLable() {
		return SeleniumUtil.getWebElement(d, "budgetDabitedLable", TransactionPage, null);
	}

	public WebElement budgetDabitedValue() {
		return SeleniumUtil.getWebElement(d, "budgetDabitedValue", TransactionPage, null);
	}

	public WebElement budgetDateLable() {
		return SeleniumUtil.getWebElement(d, "budgetDateLable", TransactionPage, null);
	}

	public WebElement budgetDateValue() {
		return SeleniumUtil.getWebElement(d, "budgetDateValue", TransactionPage, null);
	}

	public WebElement budgetcatLable() {
		return SeleniumUtil.getWebElement(d, "budgetcatLable", TransactionPage, null);
	}

	public WebElement budgetCatValue() {
		return SeleniumUtil.getWebElement(d, "budgetCatValue", TransactionPage, null);
	}

	public List<WebElement> tagList() {
		return SeleniumUtil.getWebElements(d, "tagList_TBI", TransactionPage, null);
	}

	public WebElement budgetShowMore() {
		return SeleniumUtil.getWebElement(d, "budgetShowMore", TransactionPage, null);
	}

	public WebElement GetStartedButton() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetGetStart", TransactionPage, null);

	}

	public WebElement getbudgetstartmobile() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetmobilestart", TransactionPage, null);

	}

	public WebElement NextButton() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetNextButton", TransactionPage, null);

	}

	public WebElement NextButtonMobile() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetNextButtonmobile", TransactionPage, null);

	}

	public WebElement budgetAttachmentLable() {

		return SeleniumUtil.getVisibileWebElement(d, "budgetAttachmentLable", TransactionPage, null);

	}

	public WebElement editincome() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetEditIncome", TransactionPage, null);
	}

	public WebElement budgetEditIncomemobile() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetEditIncomemobile", TransactionPage, null);
	}

	public WebElement editincometext() {
		return SeleniumUtil.getVisibileWebElement(d, "budgeteditincometext", TransactionPage, null);
	}

	public WebElement incomeSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetincomeSaveButton", TransactionPage, null);
	}

	public WebElement budgetincomesavebtnmobile() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetincomesavebtnmobile", TransactionPage, null);
	}

	public WebElement incomeNextButton() {
		return SeleniumUtil.getWebElement(d, "budgetincomeNextButton", TransactionPage, null);
	}

	public WebElement nextbuttonmobile() {
		return SeleniumUtil.getWebElement(d, "nextbuttonmobile", TransactionPage, null);
	}

	public WebElement flexibleSpendingEditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetflexiblespendingediticon", TransactionPage, null);
	}

	public WebElement editspendingmobile() {
		return SeleniumUtil.getVisibileWebElement(d, "editspendingmobile", TransactionPage, null);
	}

	public List<WebElement> editCatAmount() {
		return SeleniumUtil.getWebElements(d, "budgetEditactAmount", TransactionPage, null);
	}

	public WebElement doneButton() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetnextBtn", TransactionPage, null);
	}

	public WebElement createdBudgetheader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetheader_BDG", TransactionPage, null);
	}

	public List<WebElement> budgetTransactionDateHeader() {
		return SeleniumUtil.getWebElements(d, "budgetTransactionDateHeader", TransactionPage, null);
	}

	public List<WebElement> budgetSplitedRow() {
		return SeleniumUtil.getWebElements(d, "budgetSplitedRow", TransactionPage, null);
	}

	public WebElement budgetTagField() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetTagField", TransactionPage, null);
	}

	public WebElement budgetTagPluse() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetTagPluse", TransactionPage, null);
	}

	public WebElement budgetAttach() {
		return SeleniumUtil.getWebElement(d, "budgetAttach", TransactionPage, null);
	}

	public WebElement budgetTranUpdate() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetTranUpdate", TransactionPage, null);
	}

	public WebElement budgetAttachedFile() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetAttachedFile", TransactionPage, null);
	}

	public boolean creatBudget() {
		WebDriverWait wait = new WebDriverWait(d, 25);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {

			WebElement getstartmobile = wait.until(ExpectedConditions.visibilityOf(getbudgetstartmobile()));
			SeleniumUtil.click(getstartmobile);
			SeleniumUtil.waitForPageToLoad();
			WebElement nextBtnMobile = wait.until(ExpectedConditions.visibilityOf(NextButtonMobile()));
			SeleniumUtil.click(nextBtnMobile);
			SeleniumUtil.waitForPageToLoad();

			WebElement editmobile = wait.until(ExpectedConditions.visibilityOf(budgetEditIncomemobile()));
			SeleniumUtil.click(editmobile);
			SeleniumUtil.waitForPageToLoad();
			WebElement editIncomeFiled = wait.until(ExpectedConditions.visibilityOf(editincometext()));
			editIncomeFiled.clear();
			editIncomeFiled.sendKeys("20000");
			editIncomeFiled.sendKeys(Keys.TAB);
			WebElement incomeSavemobile = wait.until(ExpectedConditions.visibilityOf(budgetincomesavebtnmobile()));
			SeleniumUtil.click(incomeSavemobile);
			SeleniumUtil.waitForPageToLoad();
			WebElement incomenextbtnmobile = wait.until(ExpectedConditions.visibilityOf(nextbuttonmobile()));
			SeleniumUtil.click(incomenextbtnmobile);
			SeleniumUtil.waitForPageToLoad();
			WebElement flexspendingmobile = wait.until(ExpectedConditions.visibilityOf(editspendingmobile()));
			SeleniumUtil.click(flexspendingmobile);
			SeleniumUtil.waitForPageToLoad();
			wait.until(ExpectedConditions.visibilityOf(editCatAmount().get(0)));
			for (int i = 1; i < editCatAmount().size(); i++) {
				editCatAmount().get(i).clear();
				editCatAmount().get(i).sendKeys("1000");
			}

			editCatAmount().get(editCatAmount().size() - 1).sendKeys(Keys.TAB);
			WebElement nxtSavemobile = wait.until(ExpectedConditions.visibilityOf(budgetincomesavebtnmobile()));
			SeleniumUtil.click(nxtSavemobile);
			SeleniumUtil.waitForPageToLoad();
			WebElement donebtnmobile = wait.until(ExpectedConditions.visibilityOf(nextbuttonmobile()));
			SeleniumUtil.click(donebtnmobile);
			SeleniumUtil.waitForPageToLoad(5000);
			return createdBudgetheader().isDisplayed();
		} else {
			WebElement getStartEle = wait.until(ExpectedConditions.visibilityOf(GetStartedButton()));
			SeleniumUtil.click(getStartEle);
			SeleniumUtil.waitForPageToLoad();
			WebElement nextBtn = wait.until(ExpectedConditions.visibilityOf(NextButton()));
			SeleniumUtil.click(nextBtn);
			SeleniumUtil.waitForPageToLoad();
			WebElement edit = wait.until(ExpectedConditions.visibilityOf(editincome()));
			SeleniumUtil.click(edit);
			SeleniumUtil.waitForPageToLoad();
			WebElement editIncomeFiled = wait.until(ExpectedConditions.visibilityOf(editincometext()));
			editIncomeFiled.clear();
			editIncomeFiled.sendKeys("20000");
			editIncomeFiled.sendKeys(Keys.TAB);
			WebElement incomeSave = wait.until(ExpectedConditions.visibilityOf(incomeSaveButton()));
			SeleniumUtil.click(incomeSave);
			SeleniumUtil.waitForPageToLoad();
			WebElement incomenextbtn = wait.until(ExpectedConditions.visibilityOf(incomeNextButton()));
			SeleniumUtil.click(incomenextbtn);
			SeleniumUtil.waitForPageToLoad();
			WebElement flexspending = wait.until(ExpectedConditions.visibilityOf(flexibleSpendingEditIcon()));
			SeleniumUtil.click(flexspending);
			SeleniumUtil.waitForPageToLoad();
			wait.until(ExpectedConditions.visibilityOf(editCatAmount().get(0)));
			for (int i = 1; i < editCatAmount().size(); i++) {
				editCatAmount().get(i).clear();
				editCatAmount().get(i).sendKeys("1000");
			}

			editCatAmount().get(editCatAmount().size() - 1).sendKeys(Keys.TAB);
			WebElement nxtSave = wait.until(ExpectedConditions.visibilityOf(incomeSaveButton()));
			SeleniumUtil.click(nxtSave);
			SeleniumUtil.waitForPageToLoad();
			WebElement donebtn = wait.until(ExpectedConditions.visibilityOf(doneButton()));
			SeleniumUtil.click(donebtn);
			SeleniumUtil.waitForPageToLoad(5000);
			return createdBudgetheader().isDisplayed();
		}
	}

	public boolean CurrentmonthTransaction() throws ParseException {
		boolean expected = false;
		// ArrayList<String> l1=new ArrayList<String>();

		ArrayList<Date> l2 = new ArrayList<Date>();

		/*
		 * l1.add("December 19, 2017"); l1.add("December 15, 2017");
		 */

		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(new SimpleDateFormat("MMMM dd, yyyy").parse(CurrentmonthStartDate()));
		for (int i = 0; i < budgetTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(budgetTransactionDateHeader().get(i).getText().replaceAll("Today, ", ""));
			l2.add(d1);
		}

		for (int i = 0; i < l2.size(); i++) {
			SimpleDateFormat formate1 = new SimpleDateFormat("MMMM dd, yyyy");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 0);
			cal2.setTime(cal2.getTime());
			cal1.setTime(l2.get(i));

			System.out.println(formate1.format(cal1.getTime()));
			System.out.println(formate1.format(cal2.getTime()));
			System.out.println(formate1.format(cal3.getTime()));
			if (cal1.after(cal2)) {
				System.out.println("Date1 is after Date2");
			}

			if (cal1.before(cal2) && cal1.after(cal3)) {
				expected = true;
			}

			if (cal1.equals(cal2) || cal1.equals(cal3)) {
				expected = true;
			}

		}
		return expected;
	}

	public String CurrentmonthStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date thisMonthFirstDay = calendar.getTime();
		return new SimpleDateFormat("MMMM dd, yyyy").format(thisMonthFirstDay);
	}

	public String CurrentmonthEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date thisMonthLastDay = calendar.getTime();

		return new SimpleDateFormat("MMMM dd, yyyy").format(thisMonthLastDay);
	}

	public WebElement TransactionTagLink() {
		return SeleniumUtil.getWebElement(d, "TransactionTagLink", "Transaction", null);
	}

	public WebElement TransactionTagField() {
		return SeleniumUtil.getWebElement(d, "TransactionTagField", "Transaction", null);
	}

	public WebElement TransactionTagCreate() {
		return SeleniumUtil.getWebElement(d, "TransactionTagCreate", "Transaction", null);
	}

	public List<WebElement> TransactionDefualtTagList() {
		return SeleniumUtil.getWebElements(d, "TransactionDefualtTagList", "Transaction", null);
	}

	public WebElement transactionCustomDateFilterClose() {
		return SeleniumUtil.getWebElement(d, "transactionCustomDateFilterClose", "Transaction", null);
	}

	public WebElement viewmoredetailsmobile() {
		return SeleniumUtil.getWebElement(d, "viewmoredetailsmobile", "Transaction", null);
	}

	public boolean isviewmoredetailsmobile() {
		return PageParser.isElementPresent("viewmoredetailsmobile", "Transaction", null);
	}

	public WebElement budgetCategoriesRow(String catName) {
		String catRow = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "budgetCategoriesRow");
		catRow = catRow.replaceAll("catName", catName);
		return d.findElement(By.xpath(catRow));
	}

	public WebElement budgetTxnAmount(String amt) {
		String amount = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "transactionrowAmount_TBI");
		amount = amount.replace("Amt", amt);
		return d.findElement(By.xpath(amount));
	}

	public void editBudgetTransaction() {
		WebDriverWait wait = new WebDriverWait(d, 30);
		WebElement desc = wait.until(ExpectedConditions.visibilityOf(budgetDescription()));

		desc.clear();
		desc.sendKeys(PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
		SeleniumUtil.click(TransactionTagLink());
		TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetTag"));
		SeleniumUtil.click(TransactionTagCreate());
		SeleniumUtil.waitForPageToLoad(1000);

		detailsnoteField().clear();
		detailsnoteField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetNote"));
		String path1 = System.getProperty("user.dir");
		System.out.println(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		System.out.println(a);
		budgetAttach().sendKeys(a);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(budgetTranUpdate());

	}

	public void editBudgetTxn() {
		WebDriverWait wait = new WebDriverWait(d, 30);
		WebElement desc = wait.until(ExpectedConditions.visibilityOf(budgetDescription()));

		desc.clear();
		desc.sendKeys(PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
		SeleniumUtil.click(TransactionTagLink());
		TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetTag"));
		SeleniumUtil.click(TransactionTagCreate());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(this.budgetShowMore());
		detailsnoteField().clear();
		detailsnoteField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetNote"));
		String path1 = System.getProperty("user.dir");
		System.out.println(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		System.out.println(a);
		budgetAttach().sendKeys(a);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(budgetTranUpdate());

	}

	public void clickBugtTxnRow(String categoryName, int txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 60);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {

			WebElement viewdetailsmobile = wait.until(ExpectedConditions.visibilityOf(viewmoredetailsmobile()));
			SeleniumUtil.click(viewdetailsmobile);
			SeleniumUtil.waitForPageToLoad();

			WebElement catName = wait.until(ExpectedConditions.visibilityOf(budgetCategoriesRow(categoryName)));
			SeleniumUtil.click(catName);
			SeleniumUtil.waitForPageToLoad(1000);
			WebElement txnRows = wait.until(ExpectedConditions.visibilityOf(transactionrow().get(txnRow)));
			SeleniumUtil.click(txnRows);
		} else {
			WebElement catName = wait.until(ExpectedConditions.visibilityOf(budgetCategoriesRow(categoryName)));
			SeleniumUtil.click(catName);
			SeleniumUtil.waitForPageToLoad();
			WebElement txnRows = wait.until(ExpectedConditions.visibilityOf(transactionrow().get(txnRow)));
			SeleniumUtil.click(txnRows);

		}
	}

	public void clickBugtCategory(String categoryName) {
		WebDriverWait wait = new WebDriverWait(d, 60);
		WebElement catName = wait.until(ExpectedConditions.visibilityOf(budgetCategoriesRow(categoryName)));
		SeleniumUtil.click(catName);
		SeleniumUtil.waitForPageToLoad();
	}

	public void clickBugtTxnRow(int txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 60);
		SeleniumUtil.click(transactionrow().get(txnRow));
	}

	public void clickBugtTxnRow(String txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 60);
		SeleniumUtil.click(budgetTxnAmount(txnRow));
	}

}
