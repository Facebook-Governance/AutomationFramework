
/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Search_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(Transaction_Search_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;
	public static final By projectedHeader = getByObject(pageName, frameName, "projectedDateHeader");

	public Transaction_Search_Loc(WebDriver d) {
		this.d = d;
	}

	/*
	 * private final String AllSearch="//input[@id='searchTxnFilterInput']";
	 * 
	 * @FindBy(how=How.XPATH,using=AllSearch)
	 */
	public WebElement allSearch() {
		return getVisibileWebElement(d, "AllSearch", pageName, frameName);
	}

	/*
	 * private final String
	 * AllSearchicon="//div[@id='searchTxnFilterContainer']/span";
	 * 
	 * @FindBy(how=How.XPATH,using=AllSearchicon)
	 */
	public WebElement allSearchIcon() {
		return getVisibileWebElement(d, "AllSearchicon", pageName, frameName);
	}

	/*
	 * private final String AllSearchResulticon="//span[@class='searchString']";
	 * 
	 * @FindBy(how=How.XPATH,using=AllSearchResulticon)
	 */
	public WebElement allSearchResultIcon() {
		return getVisibileWebElement(d, "AllSearchResulticon", pageName, frameName);
	}

	public List<WebElement> allSearchResultIconList() {
		return getWebElements(d, "AllSearchResulticonHidden", pageName, frameName);
	}

	/*
	 * private final String searchlabel="//span[@class='search-label']";
	 * 
	 * @FindBy(how=How.XPATH,using=searchlabel)
	 */
	public WebElement searchLabel() {
		return getVisibileWebElement(d, "searchlabel", pageName, frameName);
	}

	public List<WebElement> searchLabelList() {
		return getWebElements(d, "searchlabel", pageName, frameName);
	}

	/*
	 * private final String close="//span[@aria-label='Delete Search']/i";
	 * 
	 * @FindBy(how=How.XPATH,using=close)
	 */
	public WebElement closeIcon() {
		return getVisibileWebElement(d, "close", pageName, frameName);
	}

	/*
	 * private final String attach="//div[@id='attachmentContainer']/label/span";
	 * 
	 * @FindBy(how=How.XPATH,using=attach)
	 */
	public WebElement attachment() {
		return getVisibileWebElement(d, "attach", pageName, frameName);
	}

	/*
	 * private final String listtra="//div[@class='transaction-rows-container']";
	 * 
	 * @FindBy(how=How.XPATH,using=listtra)
	 */
	public List<WebElement> listOfTransaction() {
		return getWebElements(d, "listtra", pageName, frameName);
	}

	public WebElement clearAmount() {
		return getVisibileWebElement(d, "clearAmount", pageName, frameName);
	}

	public WebElement AmountRangeHeading() {
		return getVisibileWebElement(d, "AmountRangeHeading", pageName, frameName);
	}

	public WebElement amountFromField() {
		return getVisibileWebElement(d, "amountFromField", pageName, frameName);
	}

	public WebElement amountRangeError() {
		return getVisibileWebElement(d, "amountRangeError", pageName, frameName);
	}

	public WebElement amountToField() {
		return getVisibileWebElement(d, "amountToField", pageName, frameName);
	}

	public WebElement amountApplyButton() {
		return getVisibileWebElement(d, "amountApplyButton", pageName, frameName);
	}

	public WebElement ProjectedExapdIcon() {
		return getWebElement(d, "ProExpandIcon", pageName, frameName);
	}

	public List<WebElement> allTransactionAmount() {
		return getWebElements(d, "allTransactionAmount", pageName, frameName);
	}

	public WebElement ProjectedCollapsIcon() {
		return getVisibileWebElement(d, "ProcollapsIcon", pageName, frameName);
	}

	public List<WebElement> projectedDateHeader() {
		return getWebElements(d, "projectedDateHeader", pageName, frameName);
	}

	public List<WebElement> postedDateHeader() {
		return getWebElements(d, "postedDateHeader", pageName, frameName);
	}

	public List<WebElement> getAllcat1() {
		return getWebElements(d, "Series_getAllcat1", "Transaction", null);
	}

	public WebElement shoeMore() {
		return getVisibileWebElement(d, "shoeMore_SRT", "Transaction", null);
	}

	public WebElement deleteTransaction() {
		return getVisibileWebElement(d, "deleteTransaction_SRT", "Transaction", null);
	}

	public WebElement deleteButton() {
		return getVisibileWebElement(d, "deleteButton_SRT", "Transaction", null);
	}

	public void uploadImage(String path) throws AWTException {
		StringSelection ss = new StringSelection("path");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);

		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		// r.keyRelease(KeyEvent.VK_ESCAPE);
	}

	public boolean AssertFromAmountonly(String expectedamount) {
		waitForPageToLoad(2000);
		if (allTransactionAmount().get(0).getText().equals("")) {
			click(ProjectedExapdIcon());
			waitForPageToLoad(2000);
		}
		boolean ammountfiltered = true;
		for (int i = 0; i < allTransactionAmount().size(); i++) {
			double amountactual = Double
					.parseDouble(allTransactionAmount().get(i).getText().replaceAll("[$\\,\\₹]", ""));
			double amountexpected = Double.parseDouble(expectedamount.replaceAll("[$\\,]", ""));
			if (amountactual >= amountexpected) {
				ammountfiltered = true;

			} else {
				ammountfiltered = false;
				break;
			}
		}
		return ammountfiltered;
	}

	public boolean AssertToAmountonly(String expectedamount) {
		if (allTransactionAmount().get(0).getText().equals("")) {
			click(ProjectedExapdIcon());
			waitForPageToLoad(2000);
		}
		boolean ammountfiltered = true;
		for (int i = 0; i < allTransactionAmount().size(); i++) {
			double amountactual = Double
					.parseDouble(allTransactionAmount().get(i).getText().replaceAll("[$\\,\\₹]", ""));
			double amountexpected = Double.parseDouble(expectedamount.replaceAll("[$\\,]", ""));
			if (amountactual <= amountexpected) {
				ammountfiltered = true;
			} else {
				ammountfiltered = false;
				break;

			}
		}
		return ammountfiltered;
	}

	public boolean AssertFromAndToAmount(String amountFrom, String AmountTo) {
		if (allTransactionAmount().get(0).getText().equals("")) {
			click(ProjectedExapdIcon());
			waitForPageToLoad(2000);
		}
		boolean ammountfiltered = true;
		for (int i = 0; i < allTransactionAmount().size(); i++) {
			double amountactual = Double
					.parseDouble(allTransactionAmount().get(i).getText().replaceAll("[$\\,\\₹]", ""));
			double amountexpectedFrom = Double.parseDouble(amountFrom.replaceAll("[$\\,]", ""));
			double amountexpectedTo = Double.parseDouble(AmountTo.replaceAll("[$\\,]", ""));
			if (amountactual >= amountexpectedFrom && amountactual <= amountexpectedTo) {
				ammountfiltered = true;
			} else {
				ammountfiltered = false;
				break;

			}
		}
		return ammountfiltered;
	}

	public boolean AssertAllAmount(String expectedamountFrom, String ExpectedAmountTo) {
		if (allTransactionAmount().get(0).getText().equals("")) {
			click(ProjectedExapdIcon());
			waitForPageToLoad(2000);
		}
		boolean ammountfiltered = false;
		for (int i = 0; i < allTransactionAmount().size(); i++) {
			waitForPageToLoad(3000);
			double amountactual = Double
					.parseDouble(allTransactionAmount().get(i).getText().replaceAll("[$\\,\\₹]", ""));
			double amountexpectedFrom = Double.parseDouble(expectedamountFrom.replaceAll("[$\\,]", ""));
			double amountexpectedTo = Double.parseDouble(ExpectedAmountTo.replaceAll("[$\\,]", ""));
			if (amountactual <= amountexpectedTo || amountactual >= amountexpectedFrom) {
				ammountfiltered = true;
			}
		}
		return ammountfiltered;
	}

	public boolean verifyallProjectedTransaction() throws ParseException {
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < projectedDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(projectedDateHeader().get(i).getText().replaceAll("Today,", ""));
			l2.add(d1);
		}

		boolean projectedtransaction = false;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 0);
			cal2.setTime(cal2.getTime());
			cal1.setTime(l2.get(i));
			// System.out.println(formate1.format(cal2.getTime()));
			if (cal1.after(cal2)) {
				projectedtransaction = true;
				System.out.println("Date1 is after Date2");
			}

			if (cal1.before(cal2)) {
				projectedtransaction = false;
				break;
			}

			if (cal1.equals(cal2)) {
				projectedtransaction = false;
				break;
			}
		}

		return projectedtransaction;
	}

	public void deleteallProjectedtransaction() {
		try {
			click(getAllcat1().get(0));
			waitForPageToLoad(2000);
			click(shoeMore());
			waitForPageToLoad(2000);
			click(deleteTransaction());
			waitForPageToLoad(2000);
			click(deleteButton());
			waitForPageToLoad(5000);
			deleteallProjectedtransaction();
		} catch (Exception e) {

		}

	}

	public boolean verifyallPostedTransaction() throws ParseException {
		/* ArrayList<String> l1=new ArrayList<String>(); */
		ArrayList<Date> l2 = new ArrayList<Date>();
		/*
		 * l1.add("December 19, 2017"); l1.add("December 15, 2017");
		 */

		for (int i = 0; i < postedDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(postedDateHeader().get(i).getText().replaceAll("Today, ", "").trim());
			l2.add(d1);
		}

		boolean postedtransaction = true;
		for (int i = 0; i < l2.size(); i++) {
			SimpleDateFormat formate1 = new SimpleDateFormat("MMMM dd, yyyy");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 0);
			cal2.setTime(cal2.getTime());
			cal1.setTime(l2.get(i));

			// System.out.println(formate1.format(cal2.getTime()));
			if (cal1.after(cal2)) {
				postedtransaction = false;
				break;
			}

			if (cal1.before(cal2)) {

			}

			if (cal1.equals(cal2)) {

			}
		}

		return postedtransaction;
	}

	public boolean verifyallPostedTransaction(List<WebElement> dateHeader) throws ParseException {
		/* ArrayList<String> l1=new ArrayList<String>(); */
		ArrayList<Date> l2 = new ArrayList<Date>();
		/*
		 * l1.add("December 19, 2017"); l1.add("December 15, 2017");
		 */

		for (int i = 0; i < dateHeader.size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(dateHeader.get(i).getText().replaceAll("Today, ", "").trim());
			l2.add(d1);
		}

		boolean postedtransaction = true;
		for (int i = 0; i < l2.size(); i++) {
			SimpleDateFormat formate1 = new SimpleDateFormat("MMMM dd, yyyy");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 0);
			cal2.setTime(cal2.getTime());
			cal1.setTime(l2.get(i));

			// System.out.println(formate1.format(cal2.getTime()));
			if (cal1.after(cal2)) {
				postedtransaction = false;
				break;
			}

			if (cal1.before(cal2)) {

			}

			if (cal1.equals(cal2)) {

			}
		}

		return postedtransaction;
	}

	public void serachAmount(String from, String to) {
		amountFromField().clear();
		amountFromField().sendKeys(from);
		amountToField().clear();
		amountToField().sendKeys(to);
		click(amountApplyButton());
	}

	public void clickExpandTxn() {
		WebDriverWait wait = new WebDriverWait(d, 100);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(ProjectedExapdIcon()));
		click(element);
	}

	public boolean ProjectedTransaction30Days() throws ParseException {
		/* ArrayList<String> l1=new ArrayList<String>(); */
		ArrayList<Date> l2 = new ArrayList<Date>();
		/*
		 * l1.add("December 19, 2017"); l1.add("December 15, 2017");
		 */

		for (int i = 0; i < projectedDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(projectedDateHeader().get(i).getText().replaceAll("Today,", ""));
			l2.add(d1);
		}

		boolean projectedtransaction = false;
		;
		for (int i = 0; i < l2.size(); i++) {
			SimpleDateFormat formate1 = new SimpleDateFormat("MMMM dd, yyyy");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 0);
			cal2.setTime(cal2.getTime());
			Calendar cal3 = Calendar.getInstance();
			cal3.add(Calendar.DATE, 30);
			cal3.setTime(cal3.getTime());
			cal1.setTime(l2.get(i));

			// System.out.println(formate1.format(cal2.getTime()));
			if (cal1.after(cal2) && (cal1.before(cal3) || cal1.equals(cal3))) {
				projectedtransaction = true;
				System.out.println("Date1 is after Date2");
			}

			if (cal1.before(cal2)) {
				projectedtransaction = false;
				break;
			}

			if (cal1.equals(cal2)) {
				projectedtransaction = false;
				break;
			}
		}

		return projectedtransaction;
	}

	public void clickProjectedTxnExpand() {
		try {
			click(ProjectedExapdIcon());
		} catch (Exception e) {

		}
	}

	public String convertMMMMToMM(List<String> datevalue, int listIndex) throws ParseException {

		String dv = datevalue.get(listIndex);
		dv = dv.replaceAll("Today,", "");
		// Date d1=new SimpleDateFormat("MM/dd/yyyy").parse(a.replaceAll("Today,", ""));
		// Date d1=new SimpleDateFormat("MMMM dd, yyyy").parse(a);

		DateFormat originalFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = originalFormat.parse(dv);
		String formattedDate = targetFormat.format(date);
		System.out.println(formattedDate);
		return formattedDate;
	}

}
