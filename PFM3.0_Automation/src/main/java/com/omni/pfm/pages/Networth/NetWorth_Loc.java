/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Networth;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

public class NetWorth_Loc extends SeleniumUtil {

	private static WebDriver d;
	private static final Logger logger = LoggerFactory.getLogger(NetWorth_Loc.class);
	static final String pageName = "NetWorth";
	static final String frameName = null;
	public List<String> networthAmt = new ArrayList<String>();
	public List<String> assetAmt = new ArrayList<String>();
	public List<String> liabilitiesAmt = new ArrayList<String>();
	public final static By durationDropdown_RangeText = getByObject(pageName, frameName, "durationRangeText");
	public static final By xAxisValuesOfChart = getByObject(pageName, frameName, "X-AxisValuesDisplayedInTheChart");
	public static final By netWorthDurationDisplayedOnChart = getByObject(pageName, frameName,
			"networthDurationDisplayedAboveTheChart");
	public static final By netWorthInfoIcon = getByObject(pageName, frameName, "netWorthInfoIcon");
	public static final By netWorthInfoMessage = getByObject(pageName, frameName, "netWorthInfoMessage");
	public static final By startDate = getByObject(pageName, frameName, "clearCustomStartDate");
	public static final By customDateUpdateButton = getByObject(pageName, frameName, "customDateUpdatebutton");
	public static final By networthAssetsDropdown = getByObject(pageName, frameName, "expandAssetsToViewAcc");
	public static final By networthInvestmentsDropdown = getByObject(pageName, frameName,
			"networthInvestmentsDropdown");

	public NetWorth_Loc(WebDriver d) {
		this.d = d;
	}

	public void navigateToNetWorth() {
		PageParser.navigateToPage(pageName, d);
	}

	public List<WebElement> Acc_namelist_NW() {
		return getWebElements(d, "Acc_namelist_NW", pageName, frameName);
	}

	public List<WebElement> AllAcc_dispname_NW() {
		return getWebElements(d, "AllAcc_dispname_NW", pageName, frameName);
	}

	public void forceNavigateToNetWorth() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate(pageName, d);
		By continueBy = getByObject("NetWorth", null, "continueButton_NW");
		By seeMyNetworthBy = getByObject("NetWorth", null, "seeMyNetWorthButton");
		waitUntilSpinnerWheelIsInvisible();
		if (isDisplayed(continueBy, 1)) {
			click(continueBy);
			click(seeMyNetworthBy);
		}
		waitUntilSpinnerWheelIsInvisible();
	}

	public List<WebElement> bulletsInCM() {
		return getWebElements(d, "bulletInCM", pageName, frameName);
	}

	public void switchToTableView() {
		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,-250)", "");
		waitForPageToLoad();
		click(getVisibileWebElement(d, "switchToTableView", pageName, frameName));
	}

	public WebElement getNWWelcomeCMHeading() {
		return getVisibileWebElement(d, "netWorthHeading", pageName, frameName);
	}

	public WebElement Account_Bal_Networth() {
		return getVisibileWebElement(d, "Account_Bal_Networth", pageName, frameName);
	}

	public WebElement Account_Type_Networth() {
		return getVisibileWebElement(d, "Account_Type_Networth", pageName, frameName);
	}

	public WebElement Insurance_Networth() {
		return getVisibileWebElement(d, "Insurance_Networth", pageName, frameName);
	}

	public WebElement Mycards_NW() {
		return getVisibileWebElement(d, "Mycards_NW", pageName, frameName);
	}

	public WebElement Cards_NW() {
		return getVisibileWebElement(d, "Cards_NW", pageName, frameName);
	}

	public WebElement Insurance_AccName_NW() {
		return getWebElement(d, "Insurance_AccName_NW", pageName, frameName);
	}

	public WebElement CashAcc_Networth() {
		return getVisibileWebElement(d, "CashAcc_Networth", pageName, frameName);
	}

	public WebElement Zero_balinNW() {
		return getVisibileWebElement(d, "Zero_balinNW", pageName, frameName);
	}

	public WebElement Account_CashView() {
		return getVisibileWebElement(d, "Account_CashView", pageName, frameName);
	}

	public WebElement getNWWelcomeCMHeading1() {
		return getVisibileWebElement(d, "netWorthHeading1", pageName, frameName);
	}

	public String selectAndGetValueFromTimeFilter(int i) {
		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,-700)", "");
		// click time filter to display all its value
		By durationFilter = getByObject(pageName, frameName, "netWorthDurationFilter");
		if (getAttribute(durationFilter, "aria-expanded").equals("false")) {
			click(getVisibileWebElement(d, "netWorthDurationFilter", pageName, frameName));
		}
		// click the value based upon the passed option number
		String locator = "//div[contains(@id,'durationDropdown')]//ul/li[" + i + "]//span[1]";
		String text = d.findElement(By.xpath(locator)).getText();
		logger.info("The selected option is " + text);
		click(d.findElement(By.xpath(locator)));
		return text;
	}

	public void selectTheGivenOptionFromDurationDropdown(timeFilterOptions filterName) {
		// click time filter to display all its value
		click(getVisibileWebElement(d, "netWorthDurationFilter", pageName, frameName));
		String locator = "//div[contains(@id,'durationDropdown')]//span[@data-title=\"" + filterName.getText() + "\"]";
		click(By.xpath(locator));
		waitFor(1);
		waitUntilSpinnerWheelIsInvisible();
		waitFor(1);
	}

	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	private static int getPreviousYear() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		return prevYear.get(Calendar.YEAR);
	}

	public boolean verifyChartTitle(String text) {
		boolean status = false;
		Calendar c = Calendar.getInstance();

		int currentYear = c.get(Calendar.YEAR);

		String expectedCurrentMonth = getMonthForInt(c.get(Calendar.MONTH));

		waitForPageToLoad(1000);

		String time = getVisibileWebElement(d, "chartTitle", pageName, frameName).getText();

		System.out.print("option selected time filter: " + text + " || Actual: " + time);

		switch (text) {

		case "This Month":

			String expectedString = expectedCurrentMonth + " " + currentYear;

			// System.out.println(" || Expected value is " + expectedString);
			EReporter.log(LogStatus.INFO, "The actual text [" + time.toLowerCase() + "] and the expected text ["
					+ expectedString.toLowerCase() + "] for this month");

			if (time.toLowerCase().equals(expectedString.toLowerCase())) {
				status = true;
			}

			break;

		case "3 Months":

			c.add(Calendar.MONTH, -2);

			int expectedMonth = c.get(Calendar.MONTH);

			String expectedPreviousMonth = getMonthForInt(expectedMonth);

			expectedCurrentMonth = expectedCurrentMonth.toUpperCase().substring(0, 3);

			expectedPreviousMonth = expectedPreviousMonth.toUpperCase().substring(0, 3);

			int expectedYear = c.get(Calendar.YEAR);

			expectedString = expectedPreviousMonth + " " + expectedYear + " - " + expectedCurrentMonth + " "
					+ currentYear;

			System.out.println(" ||Expected: " + expectedString);
			EReporter.log(LogStatus.INFO, "The actual text [" + time.toLowerCase() + "] and the expected text ["
					+ expectedString.toLowerCase() + "] for 3 month");

			if (time.toLowerCase().equals(expectedString.toLowerCase())) {
				status = true;
			}

			break;

		case "6 Months":

			c.add(Calendar.MONTH, -5);

			expectedMonth = c.get(Calendar.MONTH);

			expectedPreviousMonth = getMonthForInt(expectedMonth);

			expectedCurrentMonth = expectedCurrentMonth.substring(0, 3);

			expectedPreviousMonth = expectedPreviousMonth.substring(0, 3);

			expectedYear = c.get(Calendar.YEAR);

			expectedString = expectedPreviousMonth + " " + expectedYear + " - " + expectedCurrentMonth + " "
					+ currentYear;

			// System.out.println(" || Expected: " + expectedString);
			EReporter.log(LogStatus.INFO, "The actual text [" + time.toLowerCase() + "] and the expected text ["
					+ expectedString.toLowerCase() + "] for 6 month");

			// Assert.assertEquals(time.toLowerCase(),
			// expectedString.toLowerCase());
			if (time.toLowerCase().equals(expectedString.toLowerCase())) {
				status = true;
			}

			break;

		case "12 Months":

			c.add(Calendar.MONTH, -11);

			expectedMonth = c.get(Calendar.MONTH);

			expectedPreviousMonth = getMonthForInt(expectedMonth);

			expectedCurrentMonth = expectedCurrentMonth.toUpperCase().substring(0, 3);

			expectedPreviousMonth = expectedPreviousMonth.toUpperCase().substring(0, 3);

			expectedYear = c.get(Calendar.YEAR);

			expectedString = expectedPreviousMonth + " " + expectedYear + " - " + expectedCurrentMonth + " "
					+ currentYear;

			// System.out.println(" || Expected: " + expectedString);
			EReporter.log(LogStatus.INFO, "The actual text [" + time.toLowerCase() + "] and the expected text ["
					+ expectedString.toLowerCase() + "] for 12 month");

			// Assert.assertEquals(time.toLowerCase(),
			// expectedString.toLowerCase());
			if (time.toLowerCase().equals(expectedString.toLowerCase())) {
				status = true;
			}

			break;

		case "This Year":

			expectedCurrentMonth = expectedCurrentMonth.substring(0, 3) + " " + currentYear;
			;

			// expectedString = "JANUARY" + " " + currentYear;
			expectedString = "JAN" + " " + currentYear;

			System.out.println(" || Expected: " + expectedString);

			// String Actualdate=expectedString.toLowerCase()+ " " + "-" + "
			// "+getCurrentDate();
			String Actualdate = expectedString.toLowerCase() + " " + "-" + " " + expectedCurrentMonth.toLowerCase();

			// Assert.assertEquals(time.toLowerCase(),Actualdate);
			EReporter.log(LogStatus.INFO, "The actual text [" + time.toLowerCase() + "] and the expected text ["
					+ Actualdate + "] for this year");

			if (time.toLowerCase().equals(Actualdate)) {
				status = true;
			}

			break;

		case "Custom Dates":

			expectedCurrentMonth = expectedCurrentMonth.substring(0, 3);

			expectedString = getPreviousMonth() + " " + "-" + " " + expectedCurrentMonth + " " + currentYear;
			String expectedString1 = getOnlyPreviousMonth() + " " + getPreviousYear() + " " + "-" + " "
					+ expectedCurrentMonth + " " + currentYear;

			// System.out.println(" || Expected: " + expectedString);
			EReporter.log(LogStatus.INFO, "The actual text [" + time.toLowerCase() + "] and the expected text ["
					+ expectedString.toLowerCase() + "] for custom date");

			// Assert.assertEquals(time.toLowerCase(),
			// expectedString.toLowerCase());
			if (time.toLowerCase().equals(expectedString.toLowerCase())
					|| time.toLowerCase().equals(expectedString1.toLowerCase())) {
				status = true;
			}

			break;
		default:
			EReporter.log(LogStatus.ERROR, "Could not find the time text on UI " + text);

			status = false;
			break;
		}
		return status;

	}

	static String getMonthForInt(int num) {
		String month = null;

		DateFormatSymbols dfs = new DateFormatSymbols();

		String[] months = dfs.getMonths();

		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}

	public WebElement clearCustomStartDate() {
		return getVisibileWebElement(d, "clearCustomStartDate", pageName, frameName);
	}

	public WebElement chartLink() {
		return getVisibileWebElement(d, "chartLink", pageName, frameName);
	}

	public void clickChartLink() {
		chartLink().click();
	}

	// duplicate
	public void clickCustomStartDate() {
		d.findElement(By.className("start-date")).click();
	}

	public String[] getAllValuesFromTimeFilter() {
		// click time filter to display all its value
		WebElement value = getVisibileWebElement(d, "netWorthFilter", pageName, frameName);
		waitForPageToLoad(2000);
		click(value);
		waitForPageToLoad();

		List<WebElement> l = getWebElements(d, "durationDD", pageName, frameName);
		String arrVal[] = new String[l.size()];

		// for (int i = 0; i < l.size()-1; i++)
		for (int i = 0; i < l.size(); i++) {
			arrVal[i] = l.get(i).getText().trim();
			if (arrVal[i] == "") {
				arrVal[i] = l.get(i).getAttribute("title").trim();
			}
		}
		return arrVal;
	}

	public void typeCustomStartDate(String date) {
		WebElement element = clearCustomStartDate();
		click(element);
		click(element);
		element.sendKeys(date);

	}

	public void clickUpdateButton() {
		/*
		 * d.findElement(By.className("start-date")).click();
		 * d.findElement(By.className("start-date")).click();
		 * d.findElement(By.xpath("//a[contains(text(),'Update')]")).click();
		 */
		click(clearCustomStartDate());
		click(clearCustomStartDate());
		WebElement update = getVisibileWebElement(d, "updateBtn", pageName, frameName);
		click(update);
		waitUntilSpinnerWheelIsInvisible();
	}

	public WebElement Other_Asset_Networth() {
		return getVisibileWebElement(d, "Other_Asset_Networth", pageName, frameName);
	}

	public WebElement Other_Liability_Networth() {
		return getVisibileWebElement(d, "Other_Liability_Networth", pageName, frameName);
	}

	public List<WebElement> expendAsset() {
		return getWebElements(d, "expandAssets", pageName, frameName);
	}

	public WebElement expandLiabilitiesToViewAcc() {
		return getVisibileWebElement(d, "expandLiabilitiesToViewAcc", pageName, frameName);
	}

	public WebElement closeLiabilitiesToViewAcc() {
		return getVisibileWebElement(d, "closeLiabilitiesToViewAcc", pageName, frameName);
	}

	public WebElement expandAssetsToViewAcc() {
		return getVisibileWebElement(d, "expandAssetsToViewAcc", pageName, frameName);
	}

	public WebElement Invest_NW() {
		return getVisibileWebElement(d, "Invest_NW", pageName, frameName);
	}

	public WebElement My_Invest_NW() {
		return getVisibileWebElement(d, "My_Invest_NW", pageName, frameName);
	}

	public WebElement closeAssetsToViewAcc() {
		return getVisibileWebElement(d, "closeAssetsToViewAcc", pageName, frameName);
	}

	public void expendLiabilities(int networthName) {
		List<WebElement> we = getWebElements(d, "expandLiabilities", pageName, frameName);
		// d.findElements(By.xpath("//a[contains(@class,'svg_dropdown
		// category-accordion-icon left')]"));
		waitForPageToLoad();
		we.get(networthName).click();
		waitForPageToLoad();
	}

	public WebElement closeDurationFilter() {
		return getVisibileWebElement(d, "closeDurationFilter", pageName, frameName);
		// d.findElement(By.xpath("//div[contains(@id,'0durationDropdown')]/div[contains(@class,'mheader')]//a[@aria-label='Close']")).click();
		// waitForPageToLoad();
	}

	public String[] getAllContainerInAsset() {
		List<WebElement> l = getWebElements(d, "getAllContainerInAssets", pageName, frameName);
		String account[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			account[i] = l.get(i).getText().trim();
		}
		return account;
	}

	public double getAmountInSummarySection(String type) {
		double amount = 0.0;

		if (type.equalsIgnoreCase("asset")) {
			String text = getWebElement(d, "netWorthType1", pageName, frameName).getText();
			// String text = l.get(0).getText();
			System.out.println("The value of text" + text);
			text = text.replaceAll("[^\\w\\s.]", "").trim();
			amount = Double.parseDouble(text);
		} else {
			String text = getWebElement(d, "netWorthType2", pageName, frameName).getText();
			// String text = l.get(1).getText();
			text = text.replaceAll("[^\\w\\s.]", "").trim();
			amount = Double.parseDouble(text);
		}
		return amount;
	}

	public void assertNWAssetAndLiabilityAmount(double asset, double liability, double networth) {
		double netWorth[] = new double[2];
		// Added By MRQA
		DecimalFormat df2 = new DecimalFormat(".##");
		waitForPageToLoad();
		// Locator for outer main row heading 1. Asset 2. Liabilityr
		List<WebElement> l = getWebElements(d, "netWorthAssetsAndLiab", pageName, frameName);

		for (int i = 0; i < l.size(); i++) {
			waitForPageToLoad();
			double amt = 0;
			click(l.get(i));
			// After clicking asset, opens all the container. Locator for inner
			// container9
			List<WebElement> ll = d.findElements(
					By.xpath("//div[@class='category-wrap show-for-print']/div[" + (i + 1) + "]/div[2]/div"));

			for (int j = 0; j < ll.size(); j++) {
				waitForPageToLoad();
				click(ll.get(j));
				// Every container have some accounts , locator for accounts
				// under conatiner
				List<WebElement> lll = d.findElements(By.xpath("//div[@class='category-wrap show-for-print']/div["
						+ (i + 1) + "]/div[2]/div[" + (j + 1) + "]//div[contains(@class,'accounts-wrap')]/div/div[3]"));

				for (int k = 0; k < lll.size(); k++) {
					String amount = lll.get(k).getText();
					amount = amount.replaceAll("[^\\w\\s.]", "").trim();
					amt = Double.parseDouble(df2.format(amt + Double.parseDouble(amount)));
				}
			}

			netWorth[i] = amt;
		}

		Assert.assertEquals(asset, netWorth[0]);

		Assert.assertEquals(liability, netWorth[1]);

		Assert.assertEquals(networth, (netWorth[0] - netWorth[1]));
	}

	public void assertNWAssetAndLiabilityAmountForMobile(double asset, double liability, double networth) {
		waitForPageToLoad();
		double actualAssetAmt = 0, actualLiabilityAmt = 0;

		click(expandAssetsToViewAcc());

		WebElement cashAmt = getWebElement(d, "assetCashAmount", pageName, frameName);
		String cashAmount = cashAmt.getText().trim();
		cashAmount = cashAmount.replaceAll("[^\\w\\s.]", "").trim();

		WebElement investAmt = getWebElement(d, "assetInvestAmount", pageName, frameName);
		String investAmount = investAmt.getText().trim();
		investAmount = investAmount.replaceAll("[^\\w\\s.]", "").trim();
		actualAssetAmt = Double.parseDouble(cashAmount) + Double.parseDouble(investAmount);

		Assert.assertEquals(asset, actualAssetAmt);
		click(expandAssetsToViewAcc());

		click(expandLiabilitiesToViewAcc());
		WebElement cardAmt = getWebElement(d, "liabilityCardAmount", pageName, frameName);
		String cardAmount = cardAmt.getText().trim();
		cardAmount = cardAmount.replaceAll("[^\\w\\s.]", "").trim();

		WebElement loanAmt = getWebElement(d, "liabilityLoanAmount", pageName, frameName);
		String loanAmount = loanAmt.getText().trim();
		loanAmount = loanAmount.replaceAll("[^\\w\\s.]", "").trim();
		actualLiabilityAmt = Double.parseDouble(cardAmount) + Double.parseDouble(loanAmount);

		Assert.assertEquals(liability, actualLiabilityAmt);
		click(expandLiabilitiesToViewAcc());

		Assert.assertEquals(networth, (actualAssetAmt - actualLiabilityAmt));
	}

	public String[] getAllContainerInLiabilities() {
		List<WebElement> l = getWebElements(d, "getAllContainerInLiabilities", pageName, frameName);

		String account[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			account[i] = l.get(i).getText().trim();
			logger.info("Actual Value: " + account[i]);
		}
		return account;
	}

	public double getAssetsAmount() {
		// To check the amount is showing correctly or not

		WebElement assestAmnt = getVisibileWebElement(d, "getAssetsAmount", pageName, frameName);
		Assert.assertTrue(assestAmnt.isDisplayed());

		String assetsPrice = assestAmnt.getText();

		assetsPrice = assetsPrice.replaceAll("[^\\w\\s]", "");

		Double p2 = Double.parseDouble(assetsPrice);

		DecimalFormat df = new DecimalFormat("#.####");

		df.setRoundingMode(RoundingMode.CEILING);

		String roundedNum = df.format(p2);

		return Double.parseDouble(roundedNum);

	}

	public double getLiabilityAmount() {
		// To check the amount is showing correctly or not
		WebElement liabilityAmnt = getVisibileWebElement(d, "getLiabilityAmount", pageName, frameName);
		Assert.assertTrue(liabilityAmnt.isDisplayed());

		String liabilityPrice = liabilityAmnt.getText();

		liabilityPrice = liabilityPrice.replaceAll("[^\\w\\s]", "");

		Double p3 = Double.parseDouble(liabilityPrice);

		DecimalFormat df = new DecimalFormat("#.####");

		df.setRoundingMode(RoundingMode.CEILING);

		String roundedNum = df.format(p3);

		return Double.parseDouble(roundedNum);
	}

	public double getNWAmount() {
		WebElement netWorthAmt = getVisibileWebElement(d, "getNWAmount", pageName, frameName);
		Assert.assertTrue(netWorthAmt.isDisplayed());

		String netWorthPrice = netWorthAmt.getText();

		netWorthPrice = netWorthPrice.replaceAll("[^\\w\\s]", "");

		Double p1 = Double.parseDouble(netWorthPrice);

		DecimalFormat df = new DecimalFormat("#.####");

		df.setRoundingMode(RoundingMode.CEILING);

		String roundedNum = df.format(p1);

		return Double.parseDouble(roundedNum);
	}

	public List<WebElement> xAxisMonthLabels() {
		return getWebElements(d, "xAxisDate", pageName, frameName);
	}

	public String getXAxisMonthLabel(int index) {
		List<WebElement> elements = xAxisMonthLabels();
		return elements.get(index).getText().toLowerCase();
	}

	public String getXAxisLastMonthLabel() {
		List<WebElement> elements = xAxisMonthLabels();
		return elements.get(elements.size() - 1).getText().toLowerCase();
	}

	public String getDurationLabelsFromChart(String text) {
		String chartTitle = getVisibileWebElement(d, "netWorthChartTitle", pageName, frameName).getText();

		if (text.contains(PropsUtil.getDataPropertyValue("netWorthThisMonth"))) {
			return chartTitle.substring(0, 3).toLowerCase();
		} else {
			String[] splitDate = chartTitle.split("- ");
			String startMonth = chartTitle.substring(0, 3);
			String endMonth = splitDate[1].substring(0, 3);
			String toReturn = startMonth.toLowerCase() + "-" + endMonth.toLowerCase();
			return toReturn.trim();
		}
	}

	public String[] getHeadingInTable() {
		List<WebElement> l = getWebElements(d, "getHeadingInTable", pageName, frameName);

		String[] values = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			values[i] = l.get(i).getText().trim();
			System.out.println("The values are " + values[i]);
		}
		return values;
	}

	public String[] getNWSummaryHeadingTitles() {
		List<WebElement> l = getWebElements(d, "getNWSummaryHeadingTitles", pageName, frameName);
		LinkedHashSet<String> lhs = new LinkedHashSet<String>();

		for (int i = 0; i < l.size(); i++) {
			String text = l.get(i).getText();

			text = text.trim();
			if (!text.equalsIgnoreCase("")) {
				lhs.add(text);
			}
		}
		Object title[] = lhs.toArray();

		String[] stringArray = Arrays.copyOf(title, title.length, String[].class);

		return stringArray;
	}

	public void verifyNWDetailTitle(String expectedTitle) {
		String actualTitle = getVisibileWebElement(d, "verifyNWDetailTitle", pageName, frameName).getText();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	public void verifyTickMarkPresent(int num) {
		String loc = "//a[@id='durationFilterOption']/following::div[@class='multisection-dropdown']//li[" + num
				+ "]//span[contains(@class,'tick-mark')]";

		WebElement value = getVisibileWebElement(d, loc, pageName, frameName);
		Assert.assertTrue(value.isDisplayed());
	}

	public WebElement dagSiteAccount() {
		return getVisibileWebElement(d, "netWorthDagSite", pageName, frameName);
	}

	public WebElement verifyAssetsAmt() {
		return getVisibileWebElement(d, "verifyAssetsAmt", pageName, frameName);
	}

	public List<WebElement> bulletInCoachMark() {
		return getWebElements(d, "bulletInCoachMark", pageName, frameName);
	}

	public List<WebElement> netWorthChartPoc() {
		return getWebElements(d, "netWorthChartPoc", pageName, frameName);
	}

	public WebElement getYAxisValue(int number) {
		return d.findElement(
				By.cssSelector("g.highcharts-axis-labels.highcharts-yaxis-labels > text:nth-of-type(" + number + ")"));
	}

	public WebElement getXAxisValue(int number) {
		return getVisibileWebElement(d, "getXAxisValue", pageName, frameName);
	}

	public List<WebElement> netWorthChartPocXAxis() {
		return getWebElements(d, "netWorthChartPocXAxis", pageName, frameName);
	}

	public WebElement welcomeCoachMarkHeading() {
		return getVisibileWebElement(d, "welcomeCoachMarkHeading", "NetWorth", null);
	}

	public WebElement linkAccountWelcomeCM() {
		return getVisibileWebElement(d, "linkAccountWelcomeCM", "NetWorth", null);
	}

	public WebElement seeMyNWBtnCM() {
		return getVisibileWebElement(d, "seeMyNWBtnCM", "NetWorth", null);
	}

	public WebElement seeMyNetWorthButton() {
		return getWebElement(d, "seeMyNetWorthButton", "NetWorth", null);
	}

	public WebElement linkAccountLink() {
		return getVisibileWebElement(d, "linkAccountLink", "NetWorth", null);
	}

	public WebElement printIcon() {
		return getVisibileWebElement(d, "printIcon", "NetWorth", null);
	}

	public WebElement linkAccButton() {
		return getVisibileWebElement(d, "linkAccButton", "NetWorth", null);
	}

	public WebElement headingText() {
		return getVisibileWebElement(d, "headingText", "NetWorth", null);
	}

	public WebElement infoCoachMarks() {
		return getVisibileWebElement(d, "infoCoachMarks", "NetWorth", null);
	}

	public WebElement moreIcon() {
		return getVisibileWebElement(d, "moreIcon", "NetWorth", null);
	}

	public WebElement allAccountsCheckBox() {
		return getVisibileWebElement(d, "allAccountsCheckBox", "NetWorth", null);
	}

	public WebElement noDataDisplayed() {
		return getVisibileWebElement(d, "noDataDisplayed", "NetWorth", null);
	}

	public WebElement dagInvestmentChkBox() {
		return getVisibileWebElement(d, "dagInvestmentChkBox", "NetWorth", null);
	}

	public WebElement contentsSection() {
		return getVisibileWebElement(d, "contentsSection", "NetWorth", null);
	}

	public void isContentPresent(int n) {
		WebElement contentsSection = contentsSection();
		if (contentsSection == null) {
			Assert.fail("Content section is not displayed after selecting " + (n + 1)
					+ " accounts ! Probably wrong xpath or a bug.");
		} else {
			Assert.assertTrue(contentsSection.isDisplayed());
		}
	}

	public WebElement dagSiteTestDataChkBox() {
		return getVisibileWebElement(d, "dagSiteTestDataChkBox", "NetWorth", null);
	}

	public WebElement linkAccount() {
		return getVisibileWebElement(d, "linkAccount", "NetWorth", null);
	}

	public WebElement allAccountsDD() {
		return getVisibileWebElement(d, "allAccountsDD", "NetWorth", null);
	}

	public WebElement continueButton_NW() {
		return getWebElement(d, "continueButton_NW", "NetWorth", null);
	}

	public WebElement infoCMButton() {
		return getVisibileWebElement(d, "infoCMButton", "NetWorth", null);
	}

	public WebElement interactiveChart_LinkAccount() {
		return getVisibileWebElement(d, "InteractiveChart_LinkAccount", "NetWorth", null);
	}

	public WebElement welcomeCoachMarkHeadingTxt() {
		return getVisibileWebElement(d, "welcomeCoachMarkHeadingTxt", "NetWorth", null);
	}

	public List<WebElement> ChartLegends() {
		return getWebElements(d, "ChartLegends", "NetWorth", null);
	}

	public WebElement changeText() {
		return getVisibileWebElement(d, "changeText", "NetWorth", null);
	}

	/********************* Coach marks **********************/

	public String getCMHeadingText(int number) {
		number = number - 1;
		List<WebElement> l = getWebElements(d, "getCMHeadingText", pageName, frameName);
		return l.get(number).getText();
	}

	public WebElement cmCloseButton() {

		return getVisibileWebElement(d, "cmCloseButtonLinkAccount", "NetWorth", frameName);

	}

	public WebElement interactiveChart_CLP() {

		return getVisibileWebElement(d, "InteractiveChart_CLP", "NetWorth", frameName);

	}

	public WebElement interactiveChart() {

		return getVisibileWebElement(d, "InteractiveChart", "NetWorth", frameName);

	}

	public WebElement interactiveChart_Para() {

		return getVisibileWebElement(d, "InteractiveChart_Para", "NetWorth", frameName);

	}

	public String getCMBodyContent(int number) {
		number = number - 1;
		List<WebElement> l = getWebElements(d, "getCMBodyContent", pageName, frameName);
		return l.get(number).getText();
	}

	public WebElement cmCloseButton(int number) {
		number = number - 1;
		List<WebElement> l = getWebElements(d, "cmCloseButton", pageName, frameName);
		return l.get(number);
	}

	public void clickCMBackButton(int number) {
		number = number - 1;
		List<WebElement> l = getWebElements(d, "clickCMBackButton", pageName, frameName);
		click(l.get(number));
	}

	public void clickCMNextButton(int number) {
		number = number - 1;
		List<WebElement> l = getWebElements(d, "clickCMNextButton", pageName, frameName);
		click(l.get(number));
	}

	public WebElement getTourButton() {
		return getVisibileWebElement(d, "getTourButton", pageName, frameName);
	}

	public static String getCurrentDate() {
		Calendar mCalendar = Calendar.getInstance();
		String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		System.out.println(month);

		String split = month.substring(0, 3);
		System.out.println(split);

		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		System.out.println(yearInString);
		String getDate = split.toLowerCase() + " " + yearInString;

		return getDate;
	}

	public static String getPreviousMonth() {
		YearMonth thisMonth = YearMonth.now();
		YearMonth lastMonth = thisMonth.minusMonths(1);

		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);

		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM");
		String endMonth = lastMonth.format(monthYearFormatter).substring(0, 3) + " " + yearInString;

		return endMonth;
	}

	public static String getOnlyPreviousMonth() {
		YearMonth thisMonth = YearMonth.now();
		YearMonth lastMonth = thisMonth.minusMonths(1);
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM");
		String endMonth = lastMonth.format(monthYearFormatter).substring(0, 3);
		return endMonth;
	}

	public String getMonthXAxis(int month) throws AWTException {
		Actions a = new Actions(d);
		WebElement element = getVisibileWebElement(d, "getMonthXAxis", pageName, frameName);
		a.moveToElement(element).build().perform();

		Point point = element.getLocation();
		int xAxis = point.getX();
		int yAxis = point.getY();

		if (month != 1) {
			xAxis = xAxis + ((month - 1) * 75);
		} else {
			xAxis = xAxis - 20;
		}

		Robot ro = new Robot();
		ro.mouseMove(xAxis, yAxis);
		WebElement ele = getVisibileWebElement(d, "getMonthXAxis1", pageName, frameName);
		return ele.getText();
	}

	public String getNWXAxis(int month) throws AWTException {

		waitForPageToLoad();

		Actions a = new Actions(d);
		WebElement element = getVisibileWebElement(d, "netChartGetNWAxis", pageName, frameName);
		a.moveToElement(element).build().perform();

		Point point = element.getLocation();
		int xAxis = point.getX();
		int yAxis = point.getY();

		if (month != 1) {
			xAxis = xAxis + ((month - 1) * 75);
		} else {
			xAxis = xAxis - 20;
		}

		Robot ro = new Robot();
		ro.mouseMove(xAxis, yAxis);

		WebElement elemnt = getVisibileWebElement(d, "netChartGetNWAxis1", pageName, frameName);
		String nw = elemnt.getText();

		WebElement ele3 = getVisibileWebElement(d, "netChartGetNWAxis2", pageName, frameName);
		String nwamt = ele3.getText();

		networthAmt.add(nwamt);
		return nw + "," + nwamt;
	}

	public String getAssetXAxis(int month) throws AWTException {

		waitForPageToLoad();
		Actions a = new Actions(d);
		WebElement element = getVisibileWebElement(d, "netChartGetAssetXAxis", pageName, frameName);
		a.moveToElement(element).build().perform();

		Point point = element.getLocation();
		int xAxis = point.getX();
		int yAxis = point.getY();

		if (month != 1) {
			xAxis = xAxis + ((month - 1) * 75);
		} else {
			xAxis = xAxis - 20;
		}

		Robot ro = new Robot();
		ro.mouseMove(xAxis, yAxis);

		WebElement ele = getVisibileWebElement(d, "netChartGetAssetXAxis1", pageName, frameName);
		String asset = ele.getText();

		WebElement ele2 = getVisibileWebElement(d, "netChartGetAssetXAxis2", pageName, frameName);
		String assetamt = ele2.getText();

		assetAmt.add(assetamt);
		return asset + "," + assetamt;
	}

	public String getLiabilitiesXAxis(int month) throws AWTException {
		Actions a = new Actions(d);
		WebElement element = getVisibileWebElement(d, "netChartGetAssetXAxis", pageName, frameName);
		a.moveToElement(element).build().perform();

		Point point = element.getLocation();
		int xAxis = point.getX();
		int yAxis = point.getY();

		if (month != 1) {
			xAxis = xAxis + ((month - 1) * 75);
		} else {
			xAxis = xAxis - 20;
		}

		Robot ro = new Robot();
		ro.mouseMove(xAxis, yAxis);

		// WebElement ele = getVisibileWebElement(d, "toolTip",
		// pageName, frameName);
		WebElement ele = getVisibileWebElement(d, "netChartGetLiabilitiesXAxis", pageName, frameName);
		By byElement = By.cssSelector("div.highcharts-tooltip > span tr:nth-of-type(3) > td:nth-of-type(2)");

		WebElement ele2 = waitUntilElementVisible(d, byElement, 10);
		// d.findElement();
		// getWebElement(d, "netChartGetLiabilitiesXAxis1",
		// pageName, frameName);

		String liabilities = ele.getText();
		String liabilitiesamt = ele2.getText();
		liabilitiesAmt.add(liabilitiesamt);

		return liabilities + "," + liabilitiesamt;

	}

	public int getMonthValue() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		month = month + 1;
		logger.info("Current Month Value is " + month);
		return month;
	}

	public WebElement doneButton() {
		return getWebElement(d, "updateBtn", pageName, frameName);
	}

	// Added By rishabh for Tower
	public WebElement featureTour() {
		return getVisibileWebElement(d, "featureTour", "NetWorth", null);
	}

	public String getNMonthOldDate(int N) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -N);
		Date result = cal.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		return sdf.format(result);

	}

	public void clickALLAcountsCBox() {
		click(allAccountsCheckBox());

	}

	public void clickAccountSelectDropDown() {
		click(allAccountsDD());
		waitForPageToLoad(2000);

	}

	public List<WebElement> getListofAllAccounts() {

		List<WebElement> elements = d.findElements(By.xpath(
				"//div[@class='accounts-tab']/div/ul[1]//span[contains(text(),'Assets') or contains(text(),'Liab')]/following::span[@role='checkbox' and contains(@class,'component-check-box')]"));
		if (elements.size() == 0) {
			Assert.fail("No accounts in drop down, probably wrong xpath or bug");
		}

		return elements;
	}

	public int getNumberOfAccounts() {

		return getListofAllAccounts().size();
	}

	public void selectAccountFromFilter(int i) {
		getListofAllAccounts().get(i).click();

	}

	/* Added by Ravi for Account Sharing in Networth */

	public WebElement AccountShareSave() {
		return getWebElement(d, "AccountShareSave", "ManageSharing", null);
	}

	public List<WebElement> accShareDropdownValue() {
		return getWebElements(d, "AS_Dropdown", "ManageSharing", null);
	}

	public List<WebElement> fullViewShare() {
		return getWebElements(d, "fullViewShare", "ManageSharing", null);
	}

	public List<WebElement> viewBalnceOnly() {
		return getWebElements(d, "viewBalnceOnly", "ManageSharing", null);
	}

	public void investmentAndInsuranceFullAccessShare() {
		for (int i = 0; i < 2; i++) {
			click(accShareDropdownValue().get(i));
			click(fullViewShare().get(i));
		}
	}

	public void otherAcntFullAccessShare() {
		for (int i = 21; i < 25; i++) {
			click(accShareDropdownValue().get(i));
			click(fullViewShare().get(i));
		}
	}

	public void shareWithViewBalncAccess() {
		for (int i = 3; i <= 6; i++) {
			click(accShareDropdownValue().get(i));
			click(viewBalnceOnly().get(i));
		}
	}

	/* Locators added by Ravi for Account Sharing feature in Networth FinApp */

	public WebElement myAccountsCheckBox() {
		return getVisibileWebElement(d, "myAccSectionAS", "ManageSharing", null);
	}

	public WebElement sharedAccountsCheckBox() {
		return getVisibileWebElement(d, "sharedAccSectionAS", "ManageSharing", null);
	}

	public WebElement allAccountsHeader() {
		return getWebElement(d, "allAccountsHeader", "ManageSharing", null);
	}

	public WebElement allAccountsDropDown() {
		return getVisibileWebElement(d, "accDdownNw", "ManageSharing", null);
	}

	public void verifylevel2SubHeadersForAdv() {
		List<WebElement> l = getWebElements(d, "allAccountslevel2Header", "ManageSharing", frameName);
		CommonUtils.assertEqualsListElements("NWAdvLevel2Header", l);
	}

	public void verifylevel2SubHeadersForInv() {
		List<WebElement> l = getWebElements(d, "allAccountslevel2Header", "ManageSharing", frameName);
		CommonUtils.assertEqualsListElements("NWInvLevel2Header", l);
	}

	public void verifyLevel3SubHeaders() {
		List<WebElement> l = getWebElements(d, "allAccountslevel3Header", "ManageSharing", frameName);
		CommonUtils.assertEqualsListElements("NWAdvLevel3Header", l);
	}

	public void sharedAcntCount() {
		int actual = sharedAcntNameList().size();
		int expected = Integer.parseInt(PropsUtil.getDataPropertyValue("NWInvestorSharedAcnt").trim());
		Assert.assertEquals(actual, expected);
	}

	public List<WebElement> sharedAcntNameList() {
		return getWebElements(d, "sharedAcntNameList", "ManageSharing", null);
	}

	public void validateNetworthData(String key) {

		DecimalFormat twoDec = new DecimalFormat("0.00");

		double asset = Double.parseDouble(
				(getWebElement(d, "todaysAssetTop", "ManageSharing", frameName).getText().trim().substring(1))
						.replaceAll(",", ""));

		double liablity = Double.parseDouble(
				(getWebElement(d, "todaysLiabliTop", "ManageSharing", frameName).getText().trim().substring(1))
						.replaceAll(",", ""));
		double Networth = asset - liablity;

		Networth = Double.parseDouble(twoDec.format(Networth));

		double todaysNetWorthValue = Double
				.parseDouble((PropsUtil.getDataPropertyValue(key).trim().substring(1)).replaceAll(",", ""));

		Assert.assertEquals(Networth, todaysNetWorthValue);
	}

	public void verifyNetWorthAdv() {

		validateNetworthData("NWtodaysNetworthTop");

		String l = getWebElement(d, "todaysNetworthBottom", "ManageSharing", frameName).getText();
		String m = getWebElement(d, "todaysAssetBottom", "ManageSharing", frameName).getText();

		Assert.assertEquals(l, PropsUtil.getDataPropertyValue("NWtodaysNetworthBottom"));
		Assert.assertEquals(m, PropsUtil.getDataPropertyValue("NWtodaysAssetBottom"));

	}

	public void verifyNetWorthInv() {

		validateNetworthData("NWtodaysNetworthTopInv");

		String m = getWebElement(d, "todaysAssetBottom", "ManageSharing", frameName).getText();
		String l = getWebElement(d, "todaysNetworthBottom", "ManageSharing", frameName).getText();

		Assert.assertEquals(m, PropsUtil.getDataPropertyValue("NWtodaysAssetBottomInv"));

		Assert.assertEquals(l, PropsUtil.getDataPropertyValue("NWtodaysNetworthBottomInv"));

	}

	public void verifyMyAccountNwAdv() {

		validateNetworthData("AdvMyAccNWtodaysNetworthTop");

		String l = getWebElement(d, "todaysNetworthBottom", "ManageSharing", frameName).getText();
		String m = getWebElement(d, "todaysAssetBottom", "ManageSharing", frameName).getText();

		Assert.assertEquals(l, PropsUtil.getDataPropertyValue("AdvMyAccNWtodaysNetworthBottom"));
		Assert.assertEquals(m, PropsUtil.getDataPropertyValue("AdvMyAccNWtodaysAssetBottom"));

	}

	public void verifySharedAccountNwAdv() {

		validateNetworthData("AdvSNWtodaysNetworthTop");

		String l = getWebElement(d, "todaysNetworthBottom", "ManageSharing", frameName).getText();
		String m = getWebElement(d, "todaysAssetBottom", "ManageSharing", frameName).getText();

		Assert.assertEquals(l, PropsUtil.getDataPropertyValue("AdvSNWtodaysNetworthBottom"));
		Assert.assertEquals(m, PropsUtil.getDataPropertyValue("AdvSNWtodaysAssetBottom"));

	}

	public void verifyMyAccountNwInv() {

		validateNetworthData("InvSNWtodaysNetworthTop");

		String l = getWebElement(d, "todaysNetworthBottom", "ManageSharing", frameName).getText();
		String m = getWebElement(d, "todaysAssetBottom", "ManageSharing", frameName).getText();

		Assert.assertEquals(l, PropsUtil.getDataPropertyValue("InvSNWtodaysNetworthBottom"));
		Assert.assertEquals(m, PropsUtil.getDataPropertyValue("InvSNWtodaysAssetBottom"));

	}

	public void verifySharedAccountNwValuesInv() {

		validateNetworthData("InvMyAccNWtodaysNetworthTop");
		String l = getWebElement(d, "todaysNetworthBottom", "ManageSharing", frameName).getText();
		String m = getWebElement(d, "todaysAssetBottom", "ManageSharing", frameName).getText();

		Assert.assertEquals(l, PropsUtil.getDataPropertyValue("InvMyAccNWtodaysNetworthBottom"));
		Assert.assertEquals(m, PropsUtil.getDataPropertyValue("InvMyAccNWtodaysAssetBottom"));

	}

	public WebElement goToMyNetworthButton() {
		return getWebElement(d, "goToMyNetworth_Coachmark_Networth", "NetWorth", null);
	}

	public WebElement linkAccountButtonForMobile() {
		return getVisibileWebElement(d, "linkAccountButtonForMobile", "NetWorth", null);
	}

	public WebElement allAccountsDDForMobile() {
		return getVisibileWebElement(d, "allAccountsDDForMobile", "NetWorth", null);
	}

	public WebElement continueButton() {
		return getVisibileWebElement(d, "continueButton", "Networth", null);
	}

	public void completeFTUEFlow() {
		click(continueButton_NW());
		click(goToMyNetworthButton());
		waitUntilSpinnerWheelIsInvisible();
		waitFor(2);
	}

	public boolean verifyAccountsUnderAcntsDropDown(String propValue) {
		waitForPageToLoad(3000);
		List<WebElement> list = getWebElements(d, "AllAcc_LOC_dispname_NW", "NetWorth", null);
		boolean status = CommonUtils.assertContainsListElements(propValue, list);
		return status;
	}

	public enum timeFilterOptions {
		THIS_MONTH("This Month"), MONTHS_3("3 Months"), MONTHS_6("6 Months"), MONTHS_12("12 Months"),
		THIS_YEAR("This Year"), YEARS_2("2 Years"), YEARS_3("3 Years"), YEARS_4("4 Years"), YEARS_5("5 Years"),
		CUSTOMDATE("Custom Dates");

		String value;

		private timeFilterOptions(String value) {
			this.value = value;
		}

		public String getText() {
			return value;
		}
	}

	public String returnDateRangeDisplayedInDurationDropdown() {
		waitUntilElementIsVisible(durationDropdown_RangeText, 10);
		return d.findElement(durationDropdown_RangeText).getText().trim();
	}

	public ArrayList<String> returnXAxisvaluesDisplayedInChart() {
		ArrayList<String> xAxisValues = new ArrayList<String>();
		try {
			waitUntilElementIsVisible(xAxisValuesOfChart, 30);
			List<WebElement> xAxisElements = d.findElements(xAxisValuesOfChart);
			for (WebElement e : xAxisElements) {
				xAxisValues.add(e.getText().trim());
			}
			logger.info("X-Axis values are :: " + xAxisValues);
		} catch (Exception e) {
			logger.error("Unable to get X-Axis values from the chart due to :: " + e.getMessage());
			fail("Unable to get X-Axis values from the chart due to :: " + e.getMessage());
		}
		return xAxisValues;
	}

	public String returnNetWorthDurationDisplayedAboveTheChart() {
		try {
			waitUntilSpinnerWheelIsInvisible();
			waitUntilElementIsVisible(netWorthDurationDisplayedOnChart, 20);
			return d.findElement(netWorthDurationDisplayedOnChart).getText().trim();
		} catch (Exception e) {
			logger.error("unable to get the networth duration above the chart due to :: " + e.getMessage());
			fail("unable to get the networth duration above the chart due to :: " + e.getMessage());
			return null;
		}
	}

	public String returnTheInfoMessageDisplayedInTheNetworth() {
		try {
			waitUntilSpinnerWheelIsInvisible();
			waitUntilElementIsVisible(netWorthInfoIcon, 20);
			if (!isDisplayed(netWorthInfoMessage, 3)) {
				click(netWorthInfoIcon);
			}
			waitUntilElementIsVisible(netWorthInfoMessage, 7);
			return d.findElement(netWorthInfoMessage).getText().trim();
		} catch (Exception e) {
			logger.error("Unable to get the networth info message due to :: " + e.getMessage());
			fail("Unable to get the networth info message due to :: " + e.getMessage());
			return "";
		}
	}

	public void updateCustomFilter(String startDateString) {
		try {
			waitUntilElementIsVisible(startDate, 30);
			d.findElement(startDate).sendKeys(startDateString);
			click(customDateUpdateButton);
			waitUntilSpinnerWheelIsInvisible();
		} catch (Exception e) {
			logger.error("Unable to update custom date due to :: " + e.getMessage());
			fail("Unable to update custom date due to :: " + e.getMessage());
		}
	}

	public void expandAssetsDropdown() {
		if (getAttribute(networthAssetsDropdown, "aria-expanded").equals("false")) {
			click(networthAssetsDropdown);
			logger.info("Clicked on assets dropdown in networth page");
		}
		logger.info("Expanded assets dropdown in networth page");
	}

	public void expandInvestments() {
		expandAssetsDropdown();
		if (getAttribute(networthInvestmentsDropdown, "aria-label").toLowerCase().contains("open")) {
			click(networthInvestmentsDropdown);
			logger.info("Clicked on investment dropdown in networth page");
		}
		logger.info("Expanded investment dropdown in networth page");
	}

	public String returnInvestmentBalanceForGivenAccountName(String accountName) {
		String xpath = "//*[contains(@class,\"account-containers-wrap\")]//*[@autoid=\"networth-accountname-nickname\" and contains(text(),\""
				+ accountName + "\")]/ancestor::*[@role=\"row\"]//*[@autoid=\"networth-account-balance\"]";
		String accountBalance = getText(By.xpath(xpath));
		logger.info("Investment account balance of account name :: " + accountName + " is " + accountBalance);
		return accountBalance;
	}

	/**
	 * @author kongara.sravan
	 * @return
	 */
	public ArrayList<String> returnTheAccountsInAccountDropdown() {
		ArrayList<String> accountNames = new ArrayList<String>();
		expandAccountsDropdown();
		List<WebElement> listOfAccounts = getWebElements(d, "AllAcc_LOC_dispname_NW", "NetWorth", null);
		for(WebElement acc : listOfAccounts) {
			String accName = acc.getText();
			logger.info("Account name is :: " + accName);
			accountNames.add(accName);
		}
		return accountNames;
	}

	/**
	 * @author kongara.sravan
	 */
	public void expandAccountsDropdown() {
		By accDropdown = getByObject(pageName, frameName, "networthDropDown");
		if (getAttribute(accDropdown, "aria-expanded").equals("false")) {
			click(accDropdown);
			logger.info("Expanded the accounts dropdown");
		}
	}
	
	/**
	 * @author kongara.sravan
	 */
	public void collapseTheAccountsDropdown() {
		By accDropdown = getByObject(pageName, frameName, "networthDropDown");
		if (getAttribute(accDropdown, "aria-expanded").equals("true")) {
			click(accDropdown);
			logger.info("Collapsed the accounts dropdown");
		}
	}
	
	/**
	 * @author kongara.sravan
	 * @return
	 */
	public String returnTheNetworthPercentage() {
		By networthChangeText = getByObject(pageName, frameName, "networthPercentageChangeText");
		String changeText= d.findElement(networthChangeText).getText();
		logger.info("Percentage Change is :: " + changeText);
		return changeText;
	}
	
	/**
	 * @author kongara.sravan
	 * @return
	 */
	public String returnNetworthChangeAmount() {
//		By networthChangeText = getByObject(pageName, frameName, "networthChangeAmount");
		String changeText= d.findElement(getByObject(pageName, frameName, "networthChangeAmount")).getText();
		logger.info("Amount Change is :: " + changeText);
		return changeText;
	}
	
	/**
	 * @author kongara.sravan
	 * @return
	 */
	public String returnTotalNetworthAmount() {
//		By networthChangeText = getByObject(pageName, frameName, "totalNetworthValue");
		String changeText= d.findElement(getByObject(pageName, frameName, "totalNetworthValue")).getText();
		logger.info("Total Networth is :: " + changeText);
		return changeText;
	}
	
}
