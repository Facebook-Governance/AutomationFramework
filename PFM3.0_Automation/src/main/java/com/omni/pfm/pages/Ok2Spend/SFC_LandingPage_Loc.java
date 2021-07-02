/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Ok2Spend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class SFC_LandingPage_Loc {
	static Logger logger = LoggerFactory.getLogger(SFC_FTUE_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	public String pageName = "FinancialForecast";
	String frameName = null;

	public SFC_LandingPage_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> SFCAccountRow() {
		return SeleniumUtil.getWebElements(d, "SFCAccountRow", pageName, frameName);
	}

	public WebElement SFCStartButton() {
		return SeleniumUtil.getWebElement(d, "SFCStartButton", pageName, frameName);
	}

	public List<WebElement> SFCAccountType() {
		return SeleniumUtil.getWebElements(d, "SFCAccountType", pageName, frameName);
	}

	public List<WebElement> SFCAccountTypeRadio() {
		return SeleniumUtil.getWebElements(d, "SFCAccountTypeRadio", pageName, frameName);
	}

	public WebElement SFCNextButton() {
		return SeleniumUtil.getWebElement(d, "SFCNextButton", pageName, frameName);
	}

	public WebElement SFCIncomeAddManuIcon() {
		return SeleniumUtil.getWebElement(d, "SFCIncomeAddManuIcon", pageName, frameName);
	}

	public List<WebElement> SFCBillingDescription() {
		return SeleniumUtil.getWebElements(d, "SFCBillingDescription", pageName, frameName);
	}

	public List<WebElement> SFCBillingAccount() {
		return SeleniumUtil.getWebElements(d, "SFCBillingAccount", pageName, frameName);
	}

	public List<WebElement> SFCBillingAmount() {
		return SeleniumUtil.getWebElements(d, "SFCBillingAmount", pageName, frameName);
	}

	public List<WebElement> SFCBillingFrequency() {
		return SeleniumUtil.getWebElements(d, "SFCBillingFrequency", pageName, frameName);
	}

	public List<WebElement> SFCOptOutCheckBox() {
		return SeleniumUtil.getWebElements(d, "SFCOptOutCheckBox", pageName, frameName);
	}

	public List<WebElement> SFCOptInCheckBox() {
		return SeleniumUtil.getWebElements(d, "SFCOptInCheckBox", pageName, frameName);
	}

	public WebElement SFCTraDelete() {
		SeleniumUtil.waitFor(2);
		return SeleniumUtil.getWebElement(d, "SFCTraDelete", pageName, frameName);
	}

	public WebElement SFCDeletepopUpButton() {
		return SeleniumUtil.getWebElement(d, "SFCDeletepopUpButton", pageName, frameName);
	}

	public WebElement SFCTraAccount() {
		return SeleniumUtil.getWebElement(d, "SFCTraAccount", pageName, frameName);
	}

	public List<WebElement> SFCTraAccountDropDowbvalue() {
		return SeleniumUtil.getWebElements(d, "SFCTraAccountDropDowbvalue", pageName, frameName);
	}

	public WebElement SFCOtherRadio() {
		return SeleniumUtil.getWebElement(d, "SFCOtherRadio", pageName, frameName);
	}

	public WebElement SFCOtherField() {
		return SeleniumUtil.getWebElement(d, "SFCOtherField", pageName, frameName);
	}

	public WebElement SFCCreatedHeader() {
		return SeleniumUtil.getWebElement(d, "SFCCreatedHeader", pageName, frameName);
	}

	public WebElement SFCLandingPageAccountDropDown() {
		return SeleniumUtil.getWebElement(d, "SFCLandingPageAccountDropDown", pageName, frameName);
	}

	public List<WebElement> SFCUpdateAccCheckBox() {
		return SeleniumUtil.getWebElements(d, "SFCUpdateAccCheckBox", pageName, frameName);
	}

	public WebElement SFCUpdateAccButton() {
		return SeleniumUtil.getWebElement(d, "SFCUpdateAccButton", pageName, frameName);
	}

	public WebElement SFCGarphHeaderAmount() {
		return SeleniumUtil.getWebElement(d, "SFCGarphHeaderAmount", pageName, frameName);
	}

	public List<WebElement> SFCTransactionHeader() {
		return SeleniumUtil.getWebElements(d, "SFCTransactionHeader", pageName, frameName);
	}

	public List<WebElement> SFCDropDownAccName() {
		return SeleniumUtil.getWebElements(d, "SFCDropDownAccName", pageName, frameName);
	}

	public List<WebElement> SFCTransactionRowAccount() {
		return SeleniumUtil.getWebElements(d, "SFCTransactionRowAccount", pageName, frameName);
	}

	public List<WebElement> SFCDropDownAccBalance() {
		return SeleniumUtil.getWebElements(d, "SFCDropDownAccBalance", pageName, frameName);
	}

	public WebElement SFCForcastTab() {
		return SeleniumUtil.getWebElement(d, "SFCForcastTab", pageName, frameName);
	}

	public WebElement SFCForcastAvailableBalance() {
		return SeleniumUtil.getWebElement(d, "SFCForcastAvailableBalance", pageName, frameName);
	}

	public WebElement SFCForcastUpcomingBill() {
		return SeleniumUtil.getWebElement(d, "SFCForcastUpcomingBill", pageName, frameName);
	}

	public WebElement SFCForcastLeftToSpend() {
		return SeleniumUtil.getWebElement(d, "SFCForcastLeftToSpend", pageName, frameName);
	}

	public List<WebElement> SFCForcastUpcomingBillAccount() {
		return SeleniumUtil.getWebElements(d, "SFCForcastUpcomingBillAccount", pageName, frameName);
	}

	public boolean verifyallPostedTransactionHeader() throws ParseException {
		/* ArrayList<String> l1=new ArrayList<String>(); */
		ArrayList<Date> l2 = new ArrayList<Date>();
		/*
		 * l1.add("December 19, 2017"); l1.add("December 15, 2017");
		 */

		for (int i = 0; i < SFCTransactionHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(SFCTransactionHeader().get(i).getText().replace("Today,", "").trim());
			l2.add(d1);
		}

		boolean projectedtransaction = true;
		for (int i = 0; i < l2.size(); i++) {
			SimpleDateFormat formate1 = new SimpleDateFormat("MMMM dd, yyyy");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 0);
			cal2.setTime(cal2.getTime());
			cal1.setTime(l2.get(i));

			// System.out.println(formate1.format(cal2.getTime()));
			if (cal1.after(cal2)) {
				projectedtransaction = false;
				break;
			}

			if (cal1.before(cal2)) {
				projectedtransaction = false;
				break;
			}

			if (cal1.equals(cal2)) {

			}
		}

		return projectedtransaction;
	}

	public double getTotalBalance() {
		SeleniumUtil.click(d.findElement(By.xpath("//div[contains(@class,\"balance-items\")]")));
		SeleniumUtil.waitForPageToLoad();
		double total = 0;
		for (int i = 0; i < SFCDropDownAccBalance().size(); i++) {

			// total=total+Double.parseDouble(SFCDropDownAccBalance().get(i).getText().replace("$",
			// "").trim());
			String tot = SFCDropDownAccBalance().get(i).getText().replace("$", "").trim();
			tot = tot.replace(",", "");
			total = total + Double.parseDouble(tot);

		}

		return total;
	}

	public double getUpcomingBillTotalAmount() {
		SeleniumUtil.click(d.findElement(By.xpath("//*[text()=\"Upcoming Events\"]")));
		SeleniumUtil.waitForPageToLoad();
		double total = 0;
		for (int i = 0; i < SFCForcastUpcomingBillAccount().size(); i++) {

			// total=total+Double.parseDouble(SFCForcastUpcomingBillAccount().get(i).getText().replace("$",
			// "").trim());
			String tot = SFCForcastUpcomingBillAccount().get(i).getText().replace("$", "").trim();
			tot = tot.replace(",", "");
			total = total + Double.parseDouble(tot);

		}

		return total;
	}

	public WebElement getDataPoint() {
		return SeleniumUtil.getWebElement(d, "charts_SFC", pageName, frameName);

	}

	public List<WebElement> getTransactionList() {
		return SeleniumUtil.getWebElements(d, "'	transaction_list_SFC'", pageName, frameName);

	}

	public WebElement getDropDownCategory() {
		return SeleniumUtil.getWebElement(d, "groupbycategorydd", pageName, frameName);
	}

	public WebElement getCategory() {
		return SeleniumUtil.getWebElement(d, "groupbycat_OK", pageName, frameName);
	}

	public List<WebElement> getCategoriesOfEvent() {
		return SeleniumUtil.getWebElements(d, "eventCategory", pageName, frameName);

	}

	public WebElement getEvents() {
		return SeleniumUtil.getWebElement(d, "gotoevents", pageName, frameName);
	}

}
