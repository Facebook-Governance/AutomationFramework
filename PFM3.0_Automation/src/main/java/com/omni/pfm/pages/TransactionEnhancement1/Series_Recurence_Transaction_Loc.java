/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class Series_Recurence_Transaction_Loc extends SeleniumUtil {

	public WebDriver d = null;
	static WebElement we;
	
	public static final By popupHeader = getByObject("Transaction", null, "STUpdatePopUpHeade");
	public static final By radioButtonSelected = getByObject("Transaction", null, "justRadioSelected_SRT");
	public static final By allRadioButtonSelected = getByObject("Transaction", null, "allradioButton_SRT");
	public static final By rulePopup = getByObject("Transaction", null, "rulePopUp_SRT");
	public static final By stUpdateHeader = getByObject("Transaction", null, "STUpdatePopUpHeade");
	public static final By checkField = getByObject("Transaction", null, "checkField_SRT");
//checkField_SRT
	public Series_Recurence_Transaction_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> projectedStranctionList() {
		expandProjectedTXn();
		return getWebElements(d, "projectedStranctionList_SRT", "Transaction", null);
	}

	public WebElement justRadioSelected() {
		return getWebElement(d, "justRadioSelected_SRT", "Transaction", null);
	}

	public List<WebElement> justRadioSelectedList() {
		return getWebElements(d, "justRadioSelected_SRT", "Transaction", null);
	}

	public WebElement justthisText() {
		return getVisibileWebElement(d, "justthisText_SRT", "Transaction", null);
	}

	public WebElement allradioButton() {
		return getVisibileWebElement(d, "allradioButton_SRT", "Transaction", null);
	}

	public List<WebElement> allradioButtonList() {
		return getWebElements(d, "allradioButton_SRT", "Transaction", null);
	}

	public WebElement SeriesallradioButton() {
		return getVisibileWebElement(d, "SeriesallradioButton", "Transaction", null);
	}

	public WebElement allradioButtonText() {
		return getVisibileWebElement(d, "allradioButtonText_SRT", "Transaction", null);
	}

	public WebElement AllRadioButtonNew_SRT() {
		return getVisibileWebElement(d, "AllRadioButtonNew_SRT", "Transaction", null);
	}

	public WebElement justRadioButtonNew_SRT() {
		return getWebElement(d, "justRadioButtonNew_SRT", "Transaction", null);
	}

	public WebElement justRadiounSelected() {
		return getWebElement(d, "justRadiounSelected_SRT", "Transaction", null);
	}

	public WebElement amountLabel() {
		return getVisibileWebElement(d, "amountLabel_SRT", "Transaction", null);
	}

	public WebElement amountField() {
		return getVisibileWebElement(d, "amountField_SRT", "Transaction", null);
	}

	public WebElement amountCurrency_SRT() {
		return getWebElement(d, "amountCurrency_SRT", "Transaction", null);
	}

	public WebElement amountFieldErr() {
		return getVisibileWebElement(d, "amountFieldErr_SRT", "Transaction", null);
	}

	public WebElement DescriptionLabel() {
		return getVisibileWebElement(d, "DescriptionLabel_SRT", "Transaction", null);
	}

	public WebElement DescriptionField() {
		return getVisibileWebElement(d, "DescriptionField_SRT", "Transaction", null);
	}

	public WebElement DescriptionFieldErr() {
		return getVisibileWebElement(d, "describtionE_TX", "Transaction", null);
	}

	public WebElement creditTo() {
		return getVisibileWebElement(d, "creditTo_SRT", "Transaction", null);
	}

	public WebElement accountName() {
		return getVisibileWebElement(d, "accountName_SRT", "Transaction", null);
	}

	public WebElement frequencylabel() {
		return getVisibileWebElement(d, "frequencylabel_SRT", "Transaction", null);
	}

	public WebElement frequencylabelSeries() {
		return getVisibileWebElement(d, "frequencylabelSeries", "Transaction", null);
	}

	public WebElement frequencyValueSeries() {
		return getVisibileWebElement(d, "frequencyValueSeries", "Transaction", null);
	}

	public WebElement frequencyValue() {
		return getVisibileWebElement(d, "frequencyValue_SRT", "Transaction", null);
	}

	public WebElement ScheduleDateLabel() {
		return getVisibileWebElement(d, "ScheduleDateLabel_SRT", "Transaction", null);
	}

	public WebElement Scheduledate() {
		return getVisibileWebElement(d, "Scheduledate_SRT", "Transaction", null);
	}

	public WebElement enddate_SRT() {
		return getVisibileWebElement(d, "enddate_SRT", "Transaction", null);
	}

	public WebElement enddateHide_SRT() {
		return getWebElement(d, "enddateHide_SRT", "Transaction", null);
	}

	public WebElement enddateError_SRT() {
		return getWebElement(d, "enddateError_SRT", "Transaction", null);
	}

	public List<WebElement> enddate_SRTList() {
		return getWebElements(d, "enddate_SRT", "Transaction", null);
	}

	public WebElement ScheduledateIcon() {
		return getVisibileWebElement(d, "ScheduledateIcon_SRT", "Transaction", null);
	}

	public WebElement ScheduledateErr() {
		return getVisibileWebElement(d, "ScheduledateErr_SRT", "Transaction", null);
	}

	public WebElement calendarPopup() {
		return getVisibileWebElement(d, "calendarPopup_SRT", "Transaction", null);
	}

	public WebElement categoryLabel() {
		return getVisibileWebElement(d, "categoryLabel_SRT", "Transaction", null);
	}

	public WebElement categoryLabel_Inst() {
		return getVisibileWebElement(d, "categoryLabel_Inst", "Transaction", null);
	}

	public WebElement categoryValue_Inst() {
		return getVisibileWebElement(d, "categoryValue_Inst", "Transaction", null);
	}

	public WebElement catdropdown() {
		return getVisibileWebElement(d, "catdropdown_SRT", "Transaction", null);
	}

	public WebElement catReadOnly_SRT() {
		return getWebElement(d, "catReadOnly_SRT", "Transaction", null);
	}

	public WebElement catdropDownIon() {
		return getVisibileWebElement(d, "catdropDownIon_SRT", "Transaction", null);
	}

	public WebElement tagField() {
		return getVisibileWebElement(d, "tagField_SRT", "Transaction", null);
	}

	public WebElement addtagicon() {
		return getVisibileWebElement(d, "addtagicon_SRT", "Transaction", null);
	}

	public WebElement addtagErr() {
		return getVisibileWebElement(d, "addtagErr_SRT", "Transaction", null);
	}

	public WebElement customtaginfo() {
		return getVisibileWebElement(d, "customtaginfo_SRT", "Transaction", null);
	}

	public WebElement shoeMore() {
		return getVisibileWebElement(d, "shoeMore_SRT", "Transaction", null);
	}

	public WebElement noteField() {
		return getVisibileWebElement(d, "noteField_SRT", "Transaction", null);
	}

	public WebElement checkField() {
		return getVisibileWebElement(d, "checkField_SRT", "Transaction", null);
	}

	public WebElement checkFieldHide_SRT() {
		return getWebElement(d, "checkFieldHide_SRT", "Transaction", null);
	}

	public List<WebElement> checkFieldList() {
		return getWebElements(d, "checkField_SRT", "Transaction", null);
	}

	public WebElement Attachment() {
		return getVisibileWebElement(d, "Attachment_SRT", "Transaction", null);
	}

	public WebElement NoAttachment_SRT() {
		return getWebElement(d, "NoAttachment_SRT", "Transaction", null);
	}

	public WebElement deleteTransaction() {
		return getVisibileWebElement(d, "deleteTransaction_SRT", "Transaction", null);
	}

	public List<WebElement> deleteTransactionList() {
		return getWebElements(d, "deleteTransaction_SRT", "Transaction", null);
	}

	public WebElement canceltransaction() {
		return getVisibileWebElement(d, "canceltransaction_SRT", "Transaction", null);
	}

	public boolean canceltransaction_SRT_mobile() {
		return PageParser.isElementPresent("canceltransaction_SRT_mobile", "Transaction", null);
	}

	public WebElement updateTransaction() {
		return getVisibileWebElement(d, "updateTransaction_SRT", "Transaction", null);
	}

	public List<WebElement> updateTransactionlist() {
		return getWebElements(d, "cancelupdateTransaction_SRT", "Transaction", null);
	}

	public WebElement createRuleInfo() {
		return getVisibileWebElement(d, "createRuleInfo_SRT", "Transaction", null);
	}

	public WebElement createRuleLink() {
		return getVisibileWebElement(d, "createRuleLink_SRT", "Transaction", null);
	}

	public List<WebElement> createRuleLinkList() {
		return getWebElements(d, "createRuleLink_SRT", "Transaction", null);
	}

	public WebElement createRuleIcon() {
		return getVisibileWebElement(d, "createRuleIcon_SRT", "Transaction", null);
	}

	public WebElement rulePopUp() {
		return getVisibileWebElement(d, "rulePopUp_SRT", "Transaction", null);
	}

	public boolean IsMobileDeletePopUpPresent() {
		return PageParser.isElementPresent("MobileDeletePopUp", "Transaction", null);
	}

	public List<WebElement> MobileDeletePopUp() {
		return getWebElements(d, "MobileDeletePopUp", "Transaction", null);
	}

	public List<WebElement> rulePopUpList() {
		return getWebElements(d, "rulePopUp_SRT", "Transaction", null);
	}

	public WebElement createRuleButton() {
		return getVisibileWebElement(d, "createRuleButton_SRT", "Transaction", null);
	}

	public WebElement sucessMessage() {
		return getVisibileWebElement(d, "sucessMessage_SRT", "Transaction", null);
	}

	public WebElement seriesdeleteMessage() {
		return getVisibileWebElement(d, "seriesdeleteMessage_SRT", "Transaction", null);
	}

	public WebElement Deletecancel() {
		return getVisibileWebElement(d, "Deletecancel_SRT", "Transaction", null);
	}

	public WebElement deleteButton() {
		return getVisibileWebElement(d, "deleteButton_SRT", "Transaction", null);
	}

	public WebElement series_Symbol() {
		return getVisibileWebElement(d, "series_Symbol", "Transaction", null);
	}

	public WebElement series_CUSIP() {
		return getVisibileWebElement(d, "series_CUSIP", "Transaction", null);
	}

	public WebElement series_InvestmentType() {
		return getVisibileWebElement(d, "series_InvestmentType", "Transaction", null);
	}

	public WebElement series_Price() {
		return getVisibileWebElement(d, "series_Price", "Transaction", null);
	}

	public WebElement series_Quantity() {
		return getVisibileWebElement(d, "series_Quantity", "Transaction", null);
	}

	public WebElement series_Holding() {
		return getVisibileWebElement(d, "series_Holding", "Transaction", null);
	}

	public WebElement series_Lot() {
		return getVisibileWebElement(d, "series_Lot", "Transaction", null);
	}

	public WebElement series_Delete_Message() {
		return getVisibileWebElement(d, "sucessMessage_SRT", "Transaction", null);
	}

	public WebElement deleteclose() {
		return getVisibileWebElement(d, "deleteclose_SRT", "Transaction", null);
	}

	public WebElement ListCat(int MLC, int HLC) {
		return d.findElement(
				By.xpath("//div[@id='category-dropdown-wrap']//ul/li/ul/li[" + MLC + "]/ul/li[" + HLC + "]//span[1]"));
	}

	public List<WebElement> getAlldescription(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[1]/div[@class='transaction-date-wrap']/div/div[1][text()='"
						+ date
						+ "']/following-sibling::div[position()=2]//div[@class='transaction-row-content-wrap']/div[1]/div[1]/span[2]"));
	}

	public List<WebElement> getAllcat(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[1]/div[@class='transaction-date-wrap']/div/div[1][text()='"
						+ date
						+ "']/following-sibling::div[position()=2]//div[@class='transaction-row-content-wrap']/div[1]/div[2]/span[1]"));
	}

	public List<WebElement> getAllAmount(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[1]/div[@class='transaction-date-wrap']/div/div[1][text()='"
						+ date
						+ "']/following-sibling::div[position()=2]//div[@class='transaction-row-content-wrap']/div[1]/div[3]/span[1]"));
	}

	public List<WebElement> getAllaccount(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[1]/div[@class='transaction-date-wrap']/div/div[1][text()='"
						+ date
						+ "']/following-sibling::div[position()=2]//div[@class='transaction-row-content-wrap']/div[2]/div[1]/span[1]"));
	}

	public List<WebElement> getAllNo(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[1]/div[@class='transaction-date-wrap']/div/div[1][text()='"
						+ date
						+ "']/following-sibling::div[position()=2]//div[@class='transaction-row-content-wrap']/div[2]/div[1]/span[2]"));
	}

	public List<WebElement> getAllFeq(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[@class='upComing']/div[@class='transaction-date-wrap']/div/div[1][contains(text(),'"
						+ date
						+ "')]/following-sibling::div[position()=2]//div[@class='transaction-row-content-wrap']/div[3]/div[1]/span[1]"));
	}

	public List<WebElement> getAllFeq1() {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[@class='upComing']//div[@class='transaction-row-content-wrap']/div[3]//span"));
	}

	public List<WebElement> getAllaccount1() {
		return getWebElements(d, "Series_getAllaccount1", "Transaction", null);
	}

	public List<WebElement> getAllAmount2() {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[@class='upComing']//div[@class='transaction-row-content-wrap']/div[1]/div[1]//span[2]"));
	}

	public List<WebElement> getAllcat1() {
		return getWebElements(d, "Series_getAllcat1", "Transaction", null);
	}

	public List<WebElement> getAllAmount1() {
		By projectedTransactionExpandIcon = SeleniumUtil.getByObject("Transaction", null, "ProExpandIcon");
		if (SeleniumUtil.isDisplayed(projectedTransactionExpandIcon, 1)) {
			SeleniumUtil.click(projectedTransactionExpandIcon);
		}
		return getWebElements(d, "Series_getAllAmount1", "Transaction", null);

	}

	public List<WebElement> SeriesFrrequency() {
		return getWebElements(d, "SeriesFrrequency", "Transaction", null);

	}

	public List<WebElement> getAlldescription1() {
		By projectedTransactionExpandIcon = getByObject("Transaction", null, "ProExpandIcon");
		if (isDisplayed(projectedTransactionExpandIcon, 2)) {
			click(projectedTransactionExpandIcon);
		}
		return getWebElements(d, "Series_getAlldescription1", "Transaction", null);
	}

	public boolean verifyGetAllDescription() {
		return PageParser.isElementPresent("Series_getAlldescription1_mobile", "Transaction", null);
	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;

		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));

	}

	public String DateFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public String DateInMMMMFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public List<WebElement> getallPeojectedRowWithdate(String date) {
		return d.findElements(By.xpath("//div[@class='transaction-date-wrap']//div[contains(text(),'" + date
				+ "')]/following-sibling::div[position()=2]/div/div"));
	}

	public int getwithoutWeekEndDAte(int date) {
		// int a=8;
		int date2;
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, date);
		if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			/*
			 * SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy"); Calendar c1
			 * = Calendar.getInstance(); c1.add(Calendar.DATE, date+1);
			 */
			date2 = date + 1;
			System.out.println("sunday" + date);
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			/*
			 * SimpleDateFormat formatter3 = new SimpleDateFormat("MM/dd/yyyy"); Calendar c2
			 * = Calendar.getInstance();
			 */
			// c2.add(Calendar.DATE, date+2);
			date2 = date + 2;
			System.out.println("sat" + date);
		} else {
			date2 = date;
			System.out.println(date2);
		}

		return date2;
	}

	public int getNoDaysOfMonth(int monthvalue) {
		String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthvalue);
		String month = monthName[cal.get(Calendar.MONTH)];

		System.out.println("Month name: " + month);
		int i = 0;
		switch (month) {
		case "January":
			i = 31;
			break;
		case "February":
			i = 28;
			break;
		case "March":
			i = 31;
			break;
		case "April":
			i = 30;
			break;
		case "May":
			i = 31;
			break;
		case "June":
			i = 30;
			break;
		case "July":
			i = 31;
			break;
		case "August":
			i = 31;
			break;
		case "September":
			i = 30;
			break;
		case "October":
			i = 31;
			break;

		case "November":
			i = 30;
			break;
		case "December":
			i = 31;
			break;

		}

		return i;
	}

	public String getNextmonthdate() {
		SimpleDateFormat formatter3 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		// cal.setTime(date); // your date (java.util.Date)
		cal.add(Calendar.MONTH, 1); // You can -/+ x months here to go back in history or move forward.
		cal.getTime();
		System.out.println(formatter3.format(cal.getTime()));
		return formatter3.format(cal.getTime());
	}

	public int TransactionDate(int monthvalue) {
		return getwithoutWeekEndDAte(getNoDaysOfMonth(monthvalue));
	}

	public boolean IscatclosePresent() {
		return PageParser.isElementPresent("catclose", "Transaction", null);
	}

	public WebElement catgClose() {
		return getVisibileWebElement(d, "catclose", "Transaction", null);
	}

	public String get60DaysWithOutWerekEnd() {
		int a = getNoDaysOfMonth(0) + getNoDaysOfMonth(1);
		System.out.println(a);
		return DateFormate(getwithoutWeekEndDAte(a));
	}

	public String get90DaysWithOutWerekEnd() {
		int a = getNoDaysOfMonth(0) + getNoDaysOfMonth(1) + getNoDaysOfMonth(2);
		System.out.println(a);
		return DateFormate(getwithoutWeekEndDAte(a));
	}

	public String get180DaysWithOutWerekEnd() {
		int a = getNoDaysOfMonth(0) + getNoDaysOfMonth(1) + getNoDaysOfMonth(2) + getNoDaysOfMonth(3)
				+ getNoDaysOfMonth(4) + getNoDaysOfMonth(5);
		System.out.println(a);
		return DateFormate(getwithoutWeekEndDAte(a));
	}

	public int TransactionDateNOMothDays(int monthvalue) {
		return getNoDaysOfMonth(monthvalue);
	}

	public List<WebElement> STUpdatePopUpHeade() {
		return getWebElements(d, "STUpdatePopUpHeade", "Transaction", null);
	}

	public WebElement STUpdatePopUpClose() {
		return getWebElement(d, "STUpdatePopUpClose", "Transaction", null);
	}

	public WebElement STUpdatePopUpCancel() {
		return getWebElement(d, "STUpdatePopUpCancel", "Transaction", null);
	}

	public WebElement STUpdatePopUpConfirm() {
		return getWebElement(d, "STUpdatePopUpConfirm", "Transaction", null);
	}

	public WebElement STUpdatePopUpInfoMsg() {
		return getWebElement(d, "STUpdatePopUpInfoMsg", "Transaction", null);
	}

	public List<WebElement> seriesTransactionAmount() {
		return getWebElements(d, "seriesTransactionAmount", "Transaction", null);
	}

	public List<WebElement> seriesTransactionAccount() {
		return getWebElements(d, "seriesTransactionAccount", "Transaction", null);
	}

	public void editSeriesTransaction(int transactionrow, String amount, String date) {
		click(getAllAmount1().get(transactionrow));
		click(allradioButton());
		amountField().clear();
		amountField().sendKeys(amount);
		Scheduledate().clear();
		Scheduledate().sendKeys(date);
		click(updateTransaction());
		click(STUpdatePopUpConfirm());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void editOneTimeProjectedTxn(int transactionrow, String amount, String date, String decription,
			String note) {
		expandProjectedTXn();
		click(getAllAmount1().get(transactionrow));
		amountField().clear();
		amountField().sendKeys(amount);
		this.DescriptionField().clear();
		this.DescriptionField().sendKeys(decription);
		Scheduledate().clear();
		Scheduledate().sendKeys(date);
		waitForPageToLoad();
		click(this.shoeMore());
		waitForPageToLoad();
		this.noteField().clear();
		this.noteField().sendKeys(note);
		click(updateTransaction());
		waitForPageToLoad(1000);

	}
	public void expandProjectedTXn() {
		By projectedTransactionExpandIcon = getByObject("Transaction", null, "ProExpandIcon");
		if (isDisplayed(projectedTransactionExpandIcon, 1)) {
			click(projectedTransactionExpandIcon);
		}
	}
	public void editOneTimeProjectedTxn(String amount, String decription, String note) {
		expandProjectedTXn();
		amountField().clear();
		amountField().sendKeys(amount);
		this.DescriptionField().clear();
		this.DescriptionField().sendKeys(decription);
		waitForPageToLoad(1000);
		click(this.shoeMore());
		waitForPageToLoad(1000);
		this.noteField().clear();
		this.noteField().sendKeys(note);
		click(updateTransaction());
		waitForPageToLoad(1000);

	}

	public void editSeriesTransaction(int transactionrow, String amount, String note, String description) {

		waitForPageToLoad();
		click(allradioButton());
		waitForPageToLoad(1000);
		amountField().clear();
		amountField().sendKeys(amount);
		this.DescriptionField().clear();
		this.DescriptionField().sendKeys(description);
		click(updateTransaction());
		waitForPageToLoad(1000);
		click(STUpdatePopUpConfirm());
		waitForPageToLoad();
	}

	public String getDateWeekEnd(int date) {
		// int a=8;
		String date2 = null;
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, date);
		if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			date2 = "SUNDAY";
		} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			date2 = "SATURDAY";
		} else {
			date2 = "";
		}

		return date2;
	}
}
