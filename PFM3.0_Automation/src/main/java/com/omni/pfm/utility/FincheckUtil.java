/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jcraft.jsch.Logger;

public class FincheckUtil {
	WebDriver d;
	WebElement w;

	public FincheckUtil(WebDriver d) {
		this.d = d;
	}

	public void addLiability(int amount, String description) {
		System.out.println("Entering the amount");
		w = d.findElement(By.id("amount-field"));
		SeleniumUtil.click(w);
		w.clear();
		SeleniumUtil.sendKeys(w, Integer.toString(amount));
		SeleniumUtil.waitForPageToLoad(2000);

		w = d.findElement(By.id("description-field"));
		System.out.println("Entering to description field");
		SeleniumUtil.click(w);
		w.clear();
		SeleniumUtil.sendKeys(w, description);
		SeleniumUtil.waitForPageToLoad(2000);
		
		System.out.println("Entering the account value");
		w = d.findElement(By.xpath("//*[@autoid='add-txn-account-wrap']//div//a"));
		SeleniumUtil.waitForPageToLoad(1000);
		
		System.out.println("Clicking on the first account");
		w = d.findElement(By.xpath("(//*[@autoid='add-txn-account-wrap']//*[@autoid='level2-header'])[1]"));
		
		System.out.println("Entering the date");
		w = d.findElement(By.id("schedule-date-field"));
		w.click();
		w.clear();
		String date = targateDate1(3);
		SeleniumUtil.sendKeys(w, date);
		
		w = d.findElement(By.xpath("//*[@autoid='add-txn-category-wrap']/div/a"));
		w = catgoryList(8,7);
		SeleniumUtil.waitForPageToLoad(2000);
		
		w = d.findElement(By.xpath("//*[@autoid='add-txn-add-btn']"));
		w.click();
		SeleniumUtil.waitForPageToLoad();
		
	}

	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public WebElement catgoryList(int MLC, int HLC) {
		String abC = SeleniumUtil.getVisibileElementXPath(d, "FincheckV2", null, "category-dropdown-finapp");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);
		return d.findElement(By.xpath(abC));

	}
	
	/**
	 * Common method to remove all unwanted characters from a number text and return in float type
	 * @param amount
	 * @return amount
	 */
	public Float amountParser(String amount) {

		amount = amount.replace("$", "");
		amount = amount.replace("$ ", "");
		amount = amount.replace(",", "");
		amount = amount.replace("-", "");
		amount = amount.replace("–", "");
		amount = amount.replace("+", "");
		amount = amount.replace(">", "");
		amount = amount.replace("<", "");
		amount = amount.replace("\n", "");
		amount = amount.replace("*", "");
		amount = amount.replace("N/A", "0");
		amount = amount.replace("%", "");
		amount = amount.replaceAll("[^0-9.]", "");
		return Float.valueOf(amount);

	}
	
	public void skipEAIA_FTUE() {
		try {
			SeleniumUtil.click(d.findElement(By.id("ftue-continue")));
			SeleniumUtil.waitForPageToLoad(3000);
			SeleniumUtil.click(d.findElement(By.id("ftue-goto")));
			SeleniumUtil.waitForPageToLoad();
		} catch (Exception e) {
			System.err.println("FTUE Completed");
		}
	}
	
	public void clickOnElement(WebElement ele) {
		
		try {
			ele.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			SeleniumUtil.click(ele);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
