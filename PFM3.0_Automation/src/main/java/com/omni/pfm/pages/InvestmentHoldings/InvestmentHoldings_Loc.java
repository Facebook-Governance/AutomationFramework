/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.pages.InvestmentHoldings;

import java.awt.AWTException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class InvestmentHoldings_Loc extends SeleniumUtil {

	public static Logger logger = LoggerFactory.getLogger(InvestmentHoldings_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	public static String advRefId, investorName, advisorName;
	LoginPage login;
	LoginPage_SAML3 SAML;
	public static final String pageName = "InvestmentHoldings", frameName = null;
	public static final String replaceChar = "######";
//
	public static final By noDataScreen = getByObject("DashboardPage", frameName, "EIAnoDataScreen_Description");
	public static final By accountDropdownValuesText = getByObject(pageName, frameName, "accountDropdownValuesText");
	public static final By manualAccount_InvestmentHoldings_BalanceLabel = getByObject(pageName, frameName,
			"InvestmentHoldings.ManualInvestment.BalanceLabel");
	public static final By manualAccount_InvestmentHoldings_Balance = getByObject(pageName, frameName,
			"InvestmentHoldings.ManualInvestment.Balance");
	public static final By investmentHoldings_TotalBalance = getByObject(pageName, frameName, "totalValueValue");
	public static final By accountsDropdown_AllInvestmentAccounts_checkbox = getByObject(pageName, frameName,
			"InvestmentHoldings.accountDropDown.allInvestmentAccounts.checkbox");
	public static final String xpathForAccountNameInAccountDropdown = getVisibileElementXPath(pageName, frameName,
			"InvestmentHoldings.accountDropDown.accountName.text");
	public static final String xpathForAccountNameCheckboxInAccountDropdown = getVisibileElementXPath(pageName, frameName,
			"InvestmentHoldings.accountDropDown.accountName.checkbox");
	public static final By totalAggregatedAccountBalance = getByObject(pageName, frameName, "todaysHoldingBalnceValue");
	public static final By investmentHoldings_ChangePercentvalue = getByObject(pageName, frameName,
			"changePercentValue");
	public static final By investmentHoldings_ChartView = getByObject(pageName, frameName,
			"InvestmentHoldings.chartView");
	public static final By investmentHoldings_TableView = getByObject(pageName, frameName,
			"InvestmentHoldings.tableView");
	public static final By investmentHoldings_AssetClassDropdown = getByObject(pageName, frameName,
			"InvestmentHoldings.assetClassDropdown");

	public static final By dashboard_manualInvestmentAccountBalance = getByObject(pageName, frameName,
			"Dashboard.investmentHoldings.ManualAccountBalance");
	public static final By investmentHoldings_createGroupButton = getByObject(pageName, frameName,
			"InvestmentHoldings.createGroupButton");
	public static final By investmentHoldings_createFirstGroupButton = getByObject(pageName, frameName,
			"InvestmentHoldings.createFirstGroupButton");
	//
	public static final By investmentHoldings_createGroup_GroupNameInputBox = getByObject(pageName, frameName,
			"InvestmentHoldings.createGroup.groupname.inputBox");
	public static final String xpathForAccountNameInCreateGroupPopupDropdown = getVisibileElementXPath(pageName,
			frameName, "InvestmentHoldings.createGroup.accountsVisible");
	public static final By investmentHoldings_createGroupPopup_createGroupButton = getByObject(pageName, frameName,
			"createGroupButton");
	public static final String xpathForGroupNameInGroupsDropdown = getVisibileElementXPath(pageName, frameName,
			"Groups.GroupDropdown.Name");

	//
	public InvestmentHoldings_Loc(WebDriver dri) {
		d = dri;
		SAML = new LoginPage_SAML3();
		login = new LoginPage();
		advRefId = null;
		investorName = null;
		advisorName = null;

	}

	public void completeFtueFlow() {
		click(continueBtnInWelcomeCM());
		waitForPageToLoad(1000);
		click(goToMyInvestments());
	}

	public void login() {

		d.findElement(By.xpath("//input[@value='Log In']")).click();

	}

	public static WebElement FeatureTour() {
		return getVisibileWebElement(d, "FeatureTour", "InvestmentHoldings", null);
	}

	public static WebElement Accounts() {
		return getVisibileWebElement(d, "Accounts", "InvestmentHoldings", null);
	}

	public static WebElement AccountsGroup() {
		return getVisibileWebElement(d, "AccountsGroup", "InvestmentHoldings", null);
	}

	public static WebElement AccountGroupDropDown() {
		return getVisibileWebElement(d, "AccountGroupDropDown", "InvestmentHoldings", null);
	}

	public static WebElement GenericDropDown() {
		return getVisibileWebElement(d, "GenericDropDown", "InvestmentHoldings", null);
	}

	public static boolean isGenericCloseforMobile() {
		return PageParser.isElementPresent("GenericCloseforMobile", "InvestmentHoldings", null);
	}

	public static WebElement GenericCloseforMobile() {
		return getVisibileWebElement(d, "GenericCloseforMobile", "InvestmentHoldings", null);
	}

	public static WebElement MoreLinkForMobile() {
		return getVisibileWebElement(d, "MoreLinkForMobile", "InvestmentHoldings", null);
	}

	public static WebElement BackButtonForMobile() {
		return getVisibileWebElement(d, "BackButtonForMobile", "InvestmentHoldings", null);
	}

	public static WebElement welcomeCoachMarkHeading() {
		return getVisibileWebElement(d, "welcomeCoachMarkHeading", "InvestmentHoldings", null);
	}

	public static WebElement welcomeCoachMarkTxt_1() {
		return getVisibileWebElement(d, "welcomeCoachMarkTxt_1", "InvestmentHoldings", null);
	}

	public static WebElement welcomeCoachMarkTxt_2() {
		return getVisibileWebElement(d, "welcomeCoachMarkTxt_2", "InvestmentHoldings", null);
	}

	public WebElement continueBtnInWelcomeCM() {
		return getVisibileWebElement(d, "continueBtnInWelcomeCM", "InvestmentHoldings", null);
	}

	public static WebElement linkAccountWelcomeCM() {
		return getVisibileWebElement(d, "IH_linkAccountWelcomeCM", "InvestmentHoldings", null);
	}

	public WebElement goToMyInvestments() {
		return getVisibileWebElement(d, "goToMyInvestments", "InvestmentHoldings", null);
	}

	public static WebElement linkAccountlnkWelcomeCM() {
		return getVisibileWebElement(d, "linkAccountlnkWelcomeCM", "InvestmentHoldings", null);
	}

	public static WebElement linkAccountLinkright() {
		return getVisibileWebElement(d, "linkAccountLinkright", "InvestmentHoldings", null);
	}

	public static WebElement linkAnAccIconright() {
		return getVisibileWebElement(d, "linkAnAccIconright", "InvestmentHoldings", null);
	}

	public static WebElement seeMyNetWorthButton() {
		return getVisibileWebElement(d, "seeMyNetWorthButton", "InvestmentHoldings", null);
	}

	public static WebElement linkAccFloaterHeader() {
		return getVisibileWebElement(d, "linkAccFloaterHeader", "InvestmentHoldings", null);
	}

	public static WebElement investmentHoldingsHeader() {
		return getVisibileWebElement(d, "investmentHoldingsHeader", "InvestmentHoldings", null);
	}

	public static WebElement featuretourrbtn() {
		return getVisibileWebElement(d, "featuretourrbtn", "InvestmentHoldings", null);
	}

	public static WebElement allaccgroupsdropdown() {
		return getVisibileWebElement(d, "allaccgroupsdropdown", "InvestmentHoldings", null);
	}

	public static WebElement holdingscatdropdown() {
		return getVisibileWebElement(d, "holdingscatdropdown", "InvestmentHoldings", null);
	}

	public static WebElement accountGroupsLink() {
		return getVisibileWebElement(d, "accountGroupsLink", "InvestmentHoldings", null);
	}

	public static WebElement classificationLink() {
		return getVisibileWebElement(d, "classificationLink", "InvestmentHoldings", null);
	}

	public static WebElement Stm() {
		return getVisibileWebElement(d, "Stm", "InvestmentHoldings", null);
	}

	public WebElement goBackMobile() {
		return getVisibileWebElement(d, "goBackMobile", "InvestmentHoldings", null);
	}

	public static WebElement Stm1() {
		return getVisibileWebElement(d, "Stm1", "InvestmentHoldings", null);
	}

	public static WebElement chartTableHeader() {
		return getVisibileWebElement(d, "chartTableHeader", "InvestmentHoldings", null);
	}

	public static boolean isfeaturetouricon() {
		return PageParser.isElementPresent("featuretouricon", "InvestmentHoldings", null);
	}

	public static WebElement featuretouricon() {
		return getVisibileWebElement(d, "featuretouricon", "InvestmentHoldings", null);
	}

	public static WebElement featuretourbtn() {
		return getVisibileWebElement(d, "featuretourbtn", "InvestmentHoldings", null);
	}

	public static WebElement test1() {
		return getVisibileWebElement(d, "test1", "InvestmentHoldings", null);
	}

	public static WebElement classified() {
		return getVisibileWebElement(d, "classified", "InvestmentHoldings", null);
	}

	public static WebElement holdbalancing() {
		return getVisibileWebElement(d, "holdbalancing", "InvestmentHoldings", null);
	}

	public static WebElement printicon() {
		return getVisibileWebElement(d, "printicon", "InvestmentHoldings", null);
	}

	public static WebElement printbutton() {
		return getVisibileWebElement(d, "printbutton", "InvestmentHoldings", null);
	}

	public static WebElement nodatacontent() {
		return getVisibileWebElement(d, "nodatacontent", "InvestmentHoldings", null);
	}

	public static WebElement allaccountcheckbox() {
		return getVisibileWebElement(d, "allaccountcheckbox", "InvestmentHoldings", null);
	}

	public static WebElement accountcheckbox() {
		return getVisibileWebElement(d, "accountcheckbox", "InvestmentHoldings", null);
	}

	public static WebElement DoneBtm() {
		return getVisibileWebElement(d, "DoneBtm", "InvestmentHoldings", null);
	}

	public static WebElement featuretourtext() {
		return getVisibileWebElement(d, "featuretourtext", "InvestmentHoldings", null);
	}

	public static WebElement holdingstablecolumntitles() {
		return getVisibileWebElement(d, "holdingstablecolumntitles", "InvestmentHoldings", null);
	}

	public static WebElement todaystotalamount() {
		return getVisibileWebElement(d, "todaystotalamount", "InvestmentHoldings", null);
	}

	public static WebElement todaystotalamntincircle() {
		return getVisibileWebElement(d, "todaystotalamntincircle", "InvestmentHoldings", null);
	}

	public static WebElement groupbyAccountsbtn() {
		return getVisibileWebElement(d, "groupbyAccountsbtn", "InvestmentHoldings", null);
	}

	public static WebElement groupbyAccountsbtnMobile() {
		return getVisibileWebElement(d, "groupbyAccountsbtnMobile", "InvestmentHoldings", null);
	}

	public WebElement expandCollapseButton() {
		return getVisibileWebElement(d, "expandCollapseButton", "InvestmentHoldings", null);
	}

	public boolean isExpandCollapseButton() {
		return PageParser.isElementPresent("expandCollapseButton", "InvestmentHoldings", null);
	}

	public static WebElement dropDownAccName() {
		return getVisibileWebElement(d, "dropDownAccName", "InvestmentHoldings", null);
	}

	public static WebElement holdingsTableaccntName() {
		return getVisibileWebElement(d, "holdingsTableaccntName", "InvestmentHoldings", null);
	}

	public static WebElement HoldingsTableaccntNameMob() {
		return getVisibileWebElement(d, "HoldingsTableaccntNameMob", "InvestmentHoldings", null);
	}

	public static WebElement firststock() {
		return getVisibileWebElement(d, "firststock", "InvestmentHoldings", null);
	}

	public static WebElement performancefooter() {
		return getVisibileWebElement(d, "performancefooter", "InvestmentHoldings", null);
	}

	public static WebElement tableStockAccountName() {
		return getVisibileWebElement(d, "tableStockAccountName", "InvestmentHoldings", null);
	}

	public static WebElement chartStockPrice() {
		return getVisibileWebElement(d, "chartStockPrice", "InvestmentHoldings", null);
	}

	public static WebElement chartAcntName() {
		return getVisibileWebElement(d, "chartAcntName", "InvestmentHoldings", null);
	}

	public static WebElement closechart() {
		return getVisibileWebElement(d, "closechart", "InvestmentHoldings", null);
	}

	public static WebElement Unclassified() {
		return getVisibileWebElement(d, "Unclassified", "InvestmentHoldings", null);
	}

	public WebElement grpByAccCheckBox() {
		return getVisibileWebElement(d, "grpByAccCheckBox", "InvestmentHoldings", null);
	}

	public WebElement linkAccountLinkrightMobile() {
		return getVisibileWebElement(d, "linkAccountLinkrightMobile", "InvestmentHoldings", null);
	}

	public WebElement linkAccountLinkrightftue() {
		return getVisibileWebElement(d, "linkAccountLinkrightftue", "InvestmentHoldings", null);
	}

	public static WebElement categoriesdropdown() {
		return getVisibileWebElement(d, "categoriesdropdown", "InvestmentHoldings", null);
	}

	public WebElement getHoldingsChart() {
		return getVisibileWebElement(d, "getHoldingsChart", "InvestmentHoldings", null);
	}

	public static WebElement ClassDropdwn() {
		return getVisibileWebElement(d, "ClassDropdwn", "InvestmentHoldings", null);
	}

	public boolean isTotalAmountMobile() {
		return PageParser.isElementPresent("totalAmountMobile", "InvestmentHoldings", null);
	}

	public WebElement totalTodayAmountMobile() {
		return getVisibileWebElement(d, "totalAmountMobile", "InvestmentHoldings", null);
	}

	public List<WebElement> moreButton() {
		return getWebElements(d, "moreButton", "InvestmentHoldings", null);
	}

	public List<WebElement> buttonText() {

		List<WebElement> l = d.findElements(By.xpath("//span[contains(@class,'button-text')]"));
		return l;

	}

	public List<WebElement> printHeader() {
		List<WebElement> l = d.findElements(By.xpath("//i[contains(@class,'print header-icon')]"));
		return l;
	}

	public List<WebElement> yodleeFontIcon() {

		List<WebElement> l = d.findElements(By.xpath("//i[contains(@class,'left yodlee-font-icon svg_help')]"));
		return l;
	}

	public List<WebElement> bulletInCoachMark() {

		return d.findElements(By.xpath("//div[@class='ftue-icon-container']//span"));

	}

	public String getNWWelcomeCMHeading() {

		String heading = "";

		String loc = "//div[@id='ftue-container']/div/p[4]";

		heading = d.findElement(By.xpath(loc)).getText();

		loc = "//div[@id='ftue-container']/div/p[5]";

		heading = heading + "\n" + d.findElement(By.xpath(loc)).getText();

		return heading;

	}

	public List<WebElement> bulletInCoachMarkAfterContinue() {

		return d.findElements(By.xpath("//div[@class='ftue-icon-container']//span"));

	}

	public String[] getAllValuesFromClassification()

	{
		// click time filter to display all its value
		List<WebElement> l = d.findElements(By.xpath(
				"//div[contains(@id,'g-generic-filter')]//div//ul[@id='g-dropdown']/li[contains(@class,'li-level1')]"));

		// d.findElement(By.xpath("//a[@id='classificationLink']")).click();

		// Util.waitForPageToLoad(1500);

		// String loc = "//div[@id='classificationDropdownContent']//li";

		// List<WebElement> l = d.findElements(By.xpath(loc));

		String arrVal[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {

			arrVal[i] = l.get(i).getText();

			System.out.println("The array value" + arrVal[i]);

		}
		return arrVal;

	}

	public String getAllValuesFromClassificationMbl() {

		// click time filter to display all its value

		d.findElement(By.xpath("//a[@id='classificationLink']")).click();

		waitForPageToLoad(1500);

		/*
		 * 
		 * d.findElement(By.xpath(
		 * 
		 * "//div[@id='classificationDropdownContent']//li[1]")).click();
		 * 
		 * Util.waitForPageToLoad(1500);
		 * 
		 */

		String arrVal = d.findElement(By.xpath("//div[@id='classificationDropdownContent']//li[1]")).getText();

		String tickloc = "//div[@id='classificationDropdownContent']//li[1]//div[contains(@class, 'selected')]//span[contains(@class, 'svg_tick')]";

		Assert.assertTrue(d.findElement(By.xpath(tickloc)).isDisplayed());

		d.findElement(By.xpath("//div[@id='classificationDropdownContent']//li[1]")).click();

		waitForPageToLoad(1500);

		return arrVal;

	}

	/*
	 * 
	 * Method to get the Summary heading and Amount range value.
	 * 
	 * 
	 * 
	 */

	public String getSummaryTextAndAmount(String type, int num)

	{
		// click time filter to display all its value

		String text = "";

		waitForPageToLoad(1500);

		if (type.equalsIgnoreCase("text"))

			text = d.findElement(By.xpath("//div[@class='holding-balance']//dl[" + num + "]//dd")).getText();

		else if (type.equalsIgnoreCase("amount"))

			text = d.findElement(By.xpath("//div[@class='holding-balance']//dl[" + num + "]//dt")).getText();

		return text;

	}

	public double[] getHoldingsValues() {
		// click time filter to display all its value

		waitForPageToLoad(1500);

		String loc = "(//div[@class='holding-balance']//div//div[2])";

		List<WebElement> l = d.findElements(By.xpath(loc));

		String arrVal[] = new String[l.size()];

		double amt[] = new double[l.size()];

		for (int i = 0; i < l.size(); i++) {

			arrVal[i] = l.get(i).getText();

			amt[i] = getAmount(arrVal[i]);

		}
		return amt;
	}

	public String getPrice() {

		List<WebElement> l = getWebElements(d, "test1", "InvestmentHoldings", null);

		return l.get(1).getText();

	}

	public Double getAmount(String str) {

		str = str.replaceAll("[^\\w\\s]", "");

		str = str.replaceAll("__", "").trim();

		if (str.length() >= 1) {

			Double p2 = Double.parseDouble(str);

			DecimalFormat df = new DecimalFormat("#.####");

			df.setRoundingMode(RoundingMode.CEILING);

			String roundedNum = df.format(p2);

			return Double.parseDouble(roundedNum);

		} else {
			return 0.0;
		}
	}

	public String getTodaysTotal() {
		return todaystotalamount().getText();
	}

	public String getTodaysTotalmobile() {
		return totalTodayAmountMobile().getText();
	}

	public String getAmountInsideChart() {
		return todaystotalamntincircle().getText();
	}

	public String getExpandCollapseButtonText() {
		return expandCollapseButton().getText();
	}

	public String[] getHoldingsTableHeaderTitle() {

		// click time filter to display all its value
		waitForPageToLoad(1500);

		String loc = "//div[contains(@class,'holdings-table-header')]//span[contains(@class,'galen-title')]";

		List<WebElement> l = d.findElements(By.xpath(loc));

		String arrVal[] = new String[l.size() - 2];

		for (int i = 0; i < l.size() - 2; i++) {
			arrVal[i] = l.get(i).getText().trim();
		}

		return arrVal;

	}

	public String[] getHoldingsTableHeaderTitleMobile() {

		// click time filter to display all its value
		waitForPageToLoad(1500);

		String loc = "//div[contains(@class,'holdings-table-header')]//span[contains(@class,'galen-title')]";

		List<WebElement> l = d.findElements(By.xpath(loc));

		String arrVal[] = new String[l.size() - 6];

		/*
		 * for (int i = 8; i < l.size() - 7; i++) { arrVal[i] =
		 * l.get(i).getText().trim(); }
		 */
		arrVal[0] = l.get(0).getText().trim();
		arrVal[7] = l.get(7).getText().trim();
		arrVal[8] = l.get(8).getText().trim();

		return arrVal;

	}

	public void clickChart(int n) {
		WebElement svgObject = d.findElement(By.xpath("//*[name()='g']/*[name()='rect'][" + n + "]"));

		Actions builder = new Actions(d);

		builder.click(svgObject).build().perform();

	}

	public String[] getTableColunmName() {
		String loc = "(//div[@class='holdings-table row collapse']/div/div[1]/div/span)";

		List<WebElement> l = d.findElements(By.xpath(loc));

		String columnheader[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			columnheader[i] = l.get(i).getText();
		}
		return columnheader;
	}

	public void assertquantitypricevalue() {
		double tableQuantity = fetchTableValueForChart(3, 2); // Fetch the quantity
		double tableMktValue = fetchTableValueForChart(3, 5); // Fetch the market
		double tablePrice = fetchTableValueForChart(3, 3); // Fetch the price value
		Assert.assertEquals(tableMktValue, tableQuantity * tablePrice);
	}

	public String assertPerformanceChart() {

		// Check group by accounts check box and expand all rows.

		// grpByAccCheckBox.click();

		click(grpByAccCheckBox());

		waitForPageToLoad(2500);

		expandCollapseButton().click();

		waitForPageToLoad(2500);

		// Get the parent account name

		String tableAccountName = tableStockAccountName().getText();

		// Get the account symbol ex: APPL

		String tablestockname = firststock().getText();

		// Fetch the quantity value from the table

		double tableQuantity = fetchTableValueForChart(3, 2);

		// Fetch the cost basis value from the table

		double tableCostBasis = fetchTableValueForChart(3, 4);

		// Fetch the market value from the table

		double tableMktValue = fetchTableValueForChart(3, 5);

		// Fetch the amount change value from the table

		double tableAmtChange = fetchTableValueForChart(3, 6);

		// Fetch the percent change value from the table

		double tablePCChange = fetchTableValueForChart(3, 7);

		// Fetch the price value from the table

		double tablePrice = fetchTableValueForChart(3, 3);

		// find the first stock and click on it to load the performance chart

		firststock().click();
		// waiting for chart to chart

		waitForPageToLoad(10000);

		String chartAccountName = chartAcntName().getText();

		// Fetch the quantity value from the chart

		double chartQuantity = fetchChartValue(2);

		// Fetch the cost basis value from the chart

		double chartCostBasis = fetchChartValue(3);

		// Fetch the market value from the chart

		double chartMktValue = fetchChartValue(4);

		// Fetch the amount change value from the chart

		double chartAmtChange = fetchChartValue(5);

		// Fetch the per cent value from the chart

		double chartPCChange = getChartPerCent(1);

		// Fetch the price value from the chart

		double chartPrice = getAmount(chartStockPrice().getText());

		// Assert the chart and holding row values are equals.

		Assert.assertEquals(tableAccountName, chartAccountName);

		Assert.assertEquals(tableQuantity, chartQuantity);

		Assert.assertEquals(tableCostBasis, chartCostBasis);

		Assert.assertEquals(tableMktValue, chartMktValue);

		Assert.assertEquals(tableAmtChange, chartAmtChange);

		Assert.assertEquals(tablePCChange, chartPCChange);

		Assert.assertEquals(tablePrice, chartPrice);

		// Verify whether the symbol name mentioned in the chart is same as the one in
		// table

		String chartstockname = d.findElement(By.xpath("//div[@class='row collapse']/div/span[5]")).getText();

		Assert.assertTrue(chartstockname.contains(tablestockname));

		// Verify whether the close button(X) for performance chart is visible and
		// functional

		WebElement close = d.findElement(By.xpath(

				"//a[@class='yodlee-modal-popup-close-button svg-icon icon-close-b close yodlee-font-icon svg_popover-close']"));

		Assert.assertTrue(close.isDisplayed());

		// Extract the chart title text i.e. Performance

		String titletext = d.findElement(By.xpath("//div[@class='yodlee-modal-popup-header-box']")).getText();

		waitForPageToLoad(1000);

		return titletext;

	}

	public double getChartPerCent(int i) {

		// Get the change percentage from top left in chart

		String loc = "(//span[contains(@class, 'column-percentage-change')]//span[contains(@class,'number')])[" + i
				+ "]";

		String chartperCentText = d.findElement(By.xpath(loc)).getText();

		double amount = getAmount(chartperCentText);

		return amount;

	}

	public String getPerformanceTimeStamp() {

		// Get the timestamp from top left in chart

		String loc = "//div[contains(@class,'holding-date')]//span//span[2]";

		String perfTimeStamp = d.findElement(By.xpath(loc)).getText();

		return perfTimeStamp;

	}

	public void assertTimeStamp() {

		// Verify the timestamp on chart with the correct time and timezone etc.

		TimeZone timeZone = TimeZone.getTimeZone("PST");

		Calendar calendar = Calendar.getInstance(timeZone);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, hh:mma zzz", Locale.US);

		simpleDateFormat.setTimeZone(timeZone);

		String systemtimestamp = simpleDateFormat.format(calendar.getTime());

		Assert.assertEquals(systemtimestamp, getPerformanceTimeStamp());

	}

	public WebElement getClassificationDropDownItems(int itemno) {
		// Selecting a specific item from the 4 drop down options

		String loc = "//div[@id='classificationDropdownContent']//ul[@class='top']/li[" + itemno + "]//span";

		WebElement webEle = d.findElement(By.xpath(loc));

		return webEle;

	}

	public double fetchTableValueForChart(int holdingRow, int colno) {

		// Fetch the numeric values from performance chart

		String loc = "//div[@id='holdings']/div[@class='holding-row'][" + holdingRow + "]/div/div[" + colno + "]";

		WebElement webEle = d.findElement(By.xpath(loc));

		String text = webEle.getText();

		Double value = getAmount(text);

		return value;

	}

	public double fetchChartValue(int colno) {

		String loc = "(//div[@id='holdingTableContainer']//div[contains(@class,'collapse holding')]/div/span[2])["

				+ colno + "]";

		WebElement webEle = d.findElement(By.xpath(loc));

		String text = webEle.getText();

		Double value = getAmount(text);

		return value;

	}

	public void assertDurationTab() {

		for (int i = 3; i > 0; i--) {

			// Iterating from 3 to accomodate the default tab testcase. Since

			// i=3 in first iteration would mean 30 days

			String loc = "//ul[@class='tabs customTabs']/li[" + i + "]";

			WebElement webEle = d.findElement(By.xpath(loc));

			String activestatus = webEle.getAttribute("class");

			if (i != 3) {

				webEle.click(); // Select the non default tabs viz 1 day and 5

				// days

				waitForPageToLoad(6000);

			} else

				Assert.assertTrue(activestatus.contains("active")); // Selected

			// default

			// tab

		}

	}

	public void fetchClassificationDBValues() {

	}

	public boolean checkIfSorted(List<String> thelist) {

		String previous = ""; // empty string: guaranteed to be less than or
		// equal to any other
		for (final String current : thelist) {
			if (current.compareTo(previous) < 0)
				return false;
			previous = current;
		}
		return true;
	}

	public void checkVButtonFunctionality() throws InterruptedException {
		if ("BBT".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_CoBrand"))) {
			// if Group By Accounts is not selected
			SeleniumUtil.refresh(d);
		} else {
			if (isGroupByAccountsChecked() == true) {
				groupbyAccountsbtn().click();
			}
		}

		waitForPageToLoad(1500);

		String loc = "(//span[contains(@class,'expand-collapse-row')])[1]";

		WebElement vbutton = d.findElement(By.xpath(loc));

		String value1 = vbutton.getAttribute("class");

		// Check whether the v button is clicked or not

		Assert.assertTrue(value1.contains("svg_dropdown"));

		// just swapping up & down for testing

		click(vbutton);
		try {

			value1 = vbutton.getAttribute("class");

			Assert.assertTrue(value1.contains("svg_arrow-up"));

		} catch (StaleElementReferenceException s) {
			Assert.assertTrue(true);
		}
	}

	public void assertMinimizeViewListItems() {
		String loc = "//div[@class='text-ellipsify']/a";
		List<WebElement> l = d.findElements(By.xpath(loc));
		Assert.assertTrue(l.isEmpty());
	}

	public String getChangeTitle() {

		String loc = "//span[contains(@class,'galen-percentage-change-title')]";

		Assert.assertTrue(d.findElement(By.xpath(loc)).isDisplayed());

		String changeValue = d.findElement(By.xpath(loc)).getText();

		return changeValue;

	}

	public void assertAssetValues() {

		// click time filter to display all its value

		waitForPageToLoad(1500);

		String loc = "//div[@id='tableArea']//div//div[2]//div[1]";

		List<WebElement> l = d.findElements(By.xpath(loc));

		Assert.assertFalse(l.isEmpty());

	}

	public void closePopupifOpen() {

		waitForPageToLoad(1500);

		if (d.findElement(By.xpath("(//a[@title='Close popover'])[1]")).isDisplayed()) {

			d.findElement(By.xpath("(//a[@title='Close popover'])[1]")).click();

			featuretourrbtn().click();

		}

	}

	/*
	 * private final String grpByAccCheckBx =
	 * "//span[@aria-label='Group By Accounts']";
	 * 
	 * @FindBy(how = How.XPATH, using = grpByAccCheckBx)
	 * 
	 * public WebElement grpByAccCheckBox;
	 * 
	 * 
	 * private final String linkAccuntlnkrightMobile = "ftue-linkAccount";
	 * 
	 * @FindBy(how = How.ID, using = linkAccuntlnkrightMobile)
	 * 
	 * public WebElement linkAccountLinkrightMobile;
	 */

	public void assertDisclaimer(String disclaimer) {

		String loc = null;

		if ("BBT".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_CoBrand"))) {
			loc = "//div[@id='holdingFooterSection']//div//div//p";

		} else {

			loc = "//div[@id='holdingFooterSection']//div//div//p";
		}

		String actualdisclaimer = d.findElement(By.xpath(loc)).getText().trim();

		Assert.assertTrue(d.findElement(By.xpath(loc)).isDisplayed());

		Assert.assertTrue(actualdisclaimer.contains(disclaimer));

	}

	private final String categoriesdropdwn = "//a[@id='classificationLink']/span";

	@FindBy(how = How.XPATH, using = categoriesdropdwn)

	public WebElement categoriesdropdown;

	public void assertClassification(String expectedClassificationValues[]) {

		String loc = "//div[@id='classificationDropdownContent']//li";

		List<WebElement> l = d.findElements(By.xpath(loc));

		categoriesdropdown.click();

		for (int i = 0; i < l.size(); i++) {

			categoriesdropdown.click();

			waitForPageToLoad(1500);

			getClassificationDropDownItems(i + 1).click();

			waitForPageToLoad();

			// Assert Chart table title

			String charthdrtext = chartTableHeader().getText();

			waitForPageToLoad(2500);

			Assert.assertTrue(expectedClassificationValues[i].contains(charthdrtext));

			categoriesdropdown.click();

			waitForPageToLoad(1500);

			// Assert tick

			String tickloc = "//div[@id='classificationDropdownContent']//li[" + (i + 1)

					+ "]//div[contains(@class, 'selected')]//span[contains(@class, 'svg_tick')]";

			Assert.assertTrue(d.findElement(By.xpath(tickloc)).isDisplayed());

			categoriesdropdown.click();

			waitForPageToLoad(1500);

		}

	}

	/*
	 * private final String classDropdown = "//span[@class='p2 title']";
	 * 
	 * @FindBy(how = How.XPATH, using = classDropdown)
	 */

	public WebElement ClassDropdwn;

	public void clickDetailsLinkMobile() {

		String loc = "//a[@id='moreInfo']"; // The Details link

		d.findElement(By.xpath(loc)).click();

	}

	public void checkBoxClick() {

		String loc = "//span[@class='checkboxwrap']/span";

		d.findElement(By.xpath(loc)).click();

	}

	public String[] getHoldingsChartTypeNames() {

		// Fetch values of the types next to the chart
		waitForPageToLoad(1500);
		String loc = "//span[contains(@class,'text-ellipsify')]";
		List<WebElement> l = d.findElements(By.xpath(loc));
		String arrVal[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			arrVal[i] = l.get(i).getText();
			System.out.println("The ChartType is" + arrVal[i]);
		}

		return arrVal;

	}

	public void clickBacktoChartMobile() {

		String loc = "//a[@id='backToChart']//i "; // The Details link

		d.findElement(By.xpath(loc)).click();

	}

	public String[] getHoldingsTableTypeNames() {

		// Fetch values of the types in the table down below
		waitForPageToLoad(1500);
		String loc = "//span[@id='holdingTypeHeaderName']";

		List<WebElement> l = d.findElements(By.xpath(loc));

		String arrVal[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {

			arrVal[i] = l.get(i).getText();

			System.out.println("The Table Type" + arrVal[i]);

		}

		return arrVal;

	}

	public boolean isGroupByAccountsChecked() {

		waitForPageToLoad(5000);
		String loc = "//span[@id='viewType']";

		WebElement grpAcntCheckbox = d.findElement(By.xpath(loc));

		String checktextstatus = grpAcntCheckbox.getAttribute("aria-checked");

		boolean boolstatus = Boolean.parseBoolean(checktextstatus);

		return boolstatus;

	}

	public boolean isGroupByAccountsCheckedMobile() {

		waitForPageToLoad(5000);
		String loc = "(//*[contains(@class,'filter-checkbox')])[1]";

		WebElement grpAcntCheckbox = d.findElement(By.xpath(loc));

		String checktextstatus = grpAcntCheckbox.getAttribute("aria-checked");

		boolean boolstatus = Boolean.parseBoolean(checktextstatus);

		return boolstatus;

	}

	public String processAccountName(String accntname) {

		String str = accntname.replaceAll("[^A-Za-z0-9 ]", "");

		str = str.replaceAll("\\s", "");

		return str;

	}

	public String getCMHeadingText(int number)

	{

		number = number - 1;

		List<WebElement> l = d.findElements(By.xpath("//div[@class='joyride-content-wrapper']/h3"));

		return l.get(number).getText();

	}

	public String getCMBodyContent(int number)

	{

		number = number - 1;

		List<WebElement> l = d.findElements(By.xpath("//div[@class='joyride-content-wrapper']//p[1]"));

		return l.get(number).getText();

	}

	public WebElement cmCloseButton(int number) {

		number = number - 1;

		List<WebElement> l = d.findElements(By.xpath("//a[@title='Close popover']"));

		return l.get(number);

	}

	public void clickCMBackButton(int number)

	{

		number = number - 1;

		List<WebElement> l = d.findElements(By.xpath("//a[contains(@class,'joyride-prev-tip')]"));

		l.get(number).click();

	}

	public void clickCMNextButton(int number)

	{

		number = number - 1;

		List<WebElement> l = d.findElements(By.xpath("//a[contains(@class,'joyride-next-tip')]"));

		l.get(number).click();

	}

	public WebElement getTourButton() {
		return d.findElement(By.cssSelector("#menu-coachMarkHelpIcon"));
	}

	// Added by sakshi for user story: B-15076

	public WebElement totalHoldingBalncBox() {
		return getWebElement(d, "TotalHoldingBalncBox", "InvestmentHoldings", null);
	}

	public WebElement todaysHoldingBalnceLabel() {
		return getWebElement(d, "todaysHoldingBalnceLabel", "InvestmentHoldings", null);
	}

	public WebElement HoldingBalnceLabelMobile() {
		return getWebElement(d, "HoldingBalnceLabelmobile", "InvestmentHoldings", null);
	}

	public WebElement moreinfomobile() {
		return getWebElement(d, "moreinfomobile", "InvestmentHoldings", null);
	}

	public WebElement GainLossLabel() {
		return getWebElement(d, "GainLossLabel", "InvestmentHoldings", null);
	}

	public WebElement changePercentLabel() {
		return getWebElement(d, "changePercentLabel", "InvestmentHoldings", null);
	}

	public WebElement todaysHoldingBalnceValue() {
		return getWebElement(d, "todaysHoldingBalnceValue", "InvestmentHoldings", null);
	}

	public WebElement todaysHoldingBalnceValueMobile() {
		return getWebElement(d, "todaysHoldingBalnceValueMobile", "InvestmentHoldings", null);
	}

	public WebElement GainLossValue() {
		return getWebElement(d, "GainLossValue", "InvestmentHoldings", null);
	}

	public WebElement changePercentValue() {
		return getWebElement(d, "changePercentValue", "InvestmentHoldings", null);
	}

	public WebElement viewDetailBtn() {
		return getWebElement(d, "viewDetailBtn", "InvestmentHoldings", null);
	}

	public WebElement holdingBalnceLabel() {
		return getWebElement(d, "holdingBalnceLabel", "InvestmentHoldings", null);
	}

	public WebElement totalCashLabel() {
		return getWebElement(d, "totalCashLabel", "InvestmentHoldings", null);
	}

	public WebElement totalCashLabelmobile() {
		return getWebElement(d, "totalCashLabelmobile", "InvestmentHoldings", null);
	}

	public List<WebElement> totalLoanBalncLabel() {
		return getWebElements(d, "totalLoanBalncLabel", "InvestmentHoldings", null);
	}

	public WebElement totalLoanBalncLabelmobile() {
		return getWebElement(d, "totalLoanBalncLabelmobile", "InvestmentHoldings", null);
	}

	public List<WebElement> totalShortBalncLabel() {
		return getWebElements(d, "totalShortBalncLabel", "InvestmentHoldings", null);
	}

	public WebElement totalShortBalncLabelmobile() {
		return getWebElement(d, "totalShortBalncLabelmobile", "InvestmentHoldings", null);
	}

	public List<WebElement> totalMarginLabel() {
		return getWebElements(d, "totalMarginLabel", "InvestmentHoldings", null);
	}

	public WebElement totalMarginLabelmobile() {
		return getWebElement(d, "totalMarginLabelmobile", "InvestmentHoldings", null);
	}

	public WebElement totalValueLabel() {
		return getWebElement(d, "totalValueLabel", "InvestmentHoldings", null);
	}

	public WebElement holdingBalnceValue() {
		return getWebElement(d, "holdingBalnceValue", "InvestmentHoldings", null);
	}

	public WebElement totalCashValue() {
		return getWebElement(d, "totalCashValue", "InvestmentHoldings", null);
	}

	public WebElement totalLoanBalncValue() {
		return getWebElement(d, "totalLoanBalncValue", "InvestmentHoldings", null);
	}

	public WebElement totalShortBalncValue() {
		return getWebElement(d, "totalShortBalncValue", "InvestmentHoldings", null);
	}

	public WebElement totalMarginValue() {
		return getWebElement(d, "totalMarginValue", "InvestmentHoldings", null);
	}

	public WebElement totalValueValue() {
		return getWebElement(d, "totalValueValue", "InvestmentHoldings", null);
	}

	public double getTotalAmountValue() {

		double holdingBalnceValue = Double
				.parseDouble(holdingBalnceValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalCashValue = Double.parseDouble(totalCashValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalLoanBalncValue = Double
				.parseDouble(totalLoanBalncValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalShortBalncValue = Double
				.parseDouble(totalShortBalncValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalMarginValue = Double
				.parseDouble(totalMarginValue().getText().trim().substring(1).replaceAll(",", ""));

		double Total = holdingBalnceValue + totalCashValue - totalLoanBalncValue + totalShortBalncValue
				+ totalMarginValue;
		return Total;
	}

	public double getTotalAmountValueForNegative() {

		double holdingBalnceValue = Double
				.parseDouble(holdingBalnceValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalCashValue = Double.parseDouble(totalCashValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalLoanBalncValue = Double
				.parseDouble(totalLoanBalncValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalShortBalncValue = Double
				.parseDouble(totalShortBalncValue().getText().trim().substring(1).replaceAll(",", ""));
		double totalMarginValue = Double
				.parseDouble(totalMarginValue().getText().trim().substring(1).replaceAll(",", ""));

		double Total = holdingBalnceValue + totalCashValue + totalLoanBalncValue - totalShortBalncValue
				- totalMarginValue;
		return Total;
	}

	public double getTotalAmountValueForZero() {

		Double holdingBalnceValue = Double
				.parseDouble(holdingBalnceValue().getText().trim().substring(1).replaceAll(",", "").trim());
		Double totalCashValue = Double
				.parseDouble(totalCashValue().getText().trim().substring(1).replaceAll(",", "").trim());
		Double totalValue = holdingBalnceValue + totalCashValue;
		double total = Double.parseDouble(new DecimalFormat("##.##").format(totalValue));
		return total;
	}

	public WebElement allInvestmentAccountsCB() {
		return getWebElement(d, "allInvestmentAccountsCB", "InvestmentHoldings", null);
	}

	public WebElement accountDropDown() {
		return getWebElement(d, "accountDropDown", "InvestmentHoldings", null);
	}

	public List<WebElement> accountDropDownList() {
		return getWebElements(d, "accountDropDownList", "InvestmentHoldings", null);
	}

	public WebElement lessThanOneSymbol() {
		return getWebElement(d, "lessThanOneSymbol", "InvestmentHoldings", null);
	}

	public WebElement unclassifiedDropDown() {
		return getWebElement(d, "unclassifiedDropDown", "InvestmentHoldings", null);
	}

	public WebElement lessThanOneSymbolForCostBasis() {
		return getWebElement(d, "lessThanOneSymbolForCostBasis", "InvestmentHoldings", null);
	}

	public WebElement lessThanOneSymbolForMarketValue() {
		return getWebElement(d, "lessThanOneSymbolForMarketValue", "InvestmentHoldings", null);
	}

	public List<WebElement> disclaimerSymbol() {
		return getWebElements(d, "disclaimerSymbol", "InvestmentHoldings", null);
	}

	public void aportionedValueSymbol() {
		int actual = disclaimerSymbol().size();
		int expected = Integer.parseInt(PropsUtil.getDataPropertyValue("ActualDisclaimerSymbolSize").trim());
		Assert.assertEquals(actual, expected);
	}

	public WebElement disclaimerMsg() {
		return getWebElement(d, "disclaimerMsg", "InvestmentHoldings", null);
	}

	public WebElement quantitiyApportionedValue() {
		return getWebElement(d, "quantitiyApportionedValue", "InvestmentHoldings", null);
	}

	public WebElement priceApportionedValue() {
		return getWebElement(d, "priceApportionedValue", "InvestmentHoldings", null);
	}

	public WebElement costBasisQuantitiyApportionedValue() {
		return getWebElement(d, "costBasisQuantitiyApportionedValue", "InvestmentHoldings", null);
	}

	public List<WebElement> moreOption() {
		return getWebElements(d, "IH_moreOption", "InvestmentHoldings", null);
	}

	public boolean ismoreoptionmobile() {
		return PageParser.isElementPresent("IH_moreOption", "InvestmentHoldings", null);
	}

	public List<WebElement> menuAlert() {
		return getWebElements(d, "IH_menuAlert", "InvestmentHoldings", null);
	}

	public WebElement alertHeader() {
		return getWebElement(d, "IH_alertHeader", "InvestmentHoldings", null);
	}

	public WebElement alertsettings() {
		return getWebElement(d, "alertsettings", "InvestmentHoldings", null);
	}

	public WebElement alertCrossicon() {
		return getWebElement(d, "IH_alertCrossicon", "InvestmentHoldings", null);
	}

	public List<WebElement> alertBalance() {
		return getWebElements(d, "IH_alertBalIncrease", "InvestmentHoldings", null);
	}

	public String alertBalanceText(int i) {
		List<WebElement> ele = getWebElements(d, "IH_alertBalIncreaseText", "InvestmentHoldings", null);
		int pos = 0;
		pos = ele.get(i).getText().indexOf(" amount");
		String balance = ele.get(i).getText().substring(0, pos);
		return balance;
	}

	public List<WebElement> alertBalanceToggle() {
		return getWebElements(d, "IH_alertBalanceToggle", "InvestmentHoldings", null);
	}

	public List<WebElement> alertBalDrpdwnAmt() {
		return getWebElements(d, "IH_alertBalDrpdwnAmt", "InvestmentHoldings", null);
	}

	public WebElement alertBalDrpdwnValue(int i) {
		WebElement l = d.findElement(By.xpath("//span[contains(@class,'dropdown-content')]//h3[@id='li-" + i + "-h']"));
		return l;
	}

	public List<WebElement> alertBalDrpdwnTickmark(int i) {
		List<WebElement> l = d
				.findElements(By.xpath("//h3[@id='li-" + i + "-h']//span[contains(@class,'component-tick-mark')]"));
		return l;
	}

	public List<WebElement> alertBalanceValue() {
		return getWebElements(d, "IH_alertBalanceValue", "InvestmentHoldings", null);
	}

	public List<WebElement> alertBalanceField() {
		return getWebElements(d, "IH_alertBalanceField", "InvestmentHoldings", null);
	}

	public WebElement alertCancelButton() {
		return getWebElement(d, "IH_alertCancelButton", "InvestmentHoldings", null);
	}

	public boolean isalertCancelButton() {
		return PageParser.isElementPresent("IH_alertCancelButton", "InvestmentHoldings", null);
	}

	public List<WebElement> alertSaveButton() {
		return getWebElements(d, "IH_alertSaveButton", "InvestmentHoldings", null);
	}

	public List<WebElement> alertInfo() {
		return getWebElements(d, "IH_alertInfo", "InvestmentHoldings", null);
	}

	public List<WebElement> chartData() {
		return getWebElements(d, "IH_chartData", "InvestmentHoldings", null);
	}

	public List<WebElement> chartDataMobile() {
		return getWebElements(d, "IH_chartDataMobile", "InvestmentHoldings", null);
	}

	// vrinda
	public List<WebElement> expandCollapseIcons() {
		return getWebElements(d, "IH_expandCollapseIcons", "InvestmentHoldings", null);
	}

	public List<WebElement> costBasisValue() {
		return getWebElements(d, "IH_costBasisValue", "InvestmentHoldings", null);
	}

	public List<WebElement> marketValue() {
		return getWebElements(d, "IH_marketValue", "InvestmentHoldings", null);
	}

	public List<WebElement> changeValue() {
		return getWebElements(d, "IH_changeValue", "InvestmentHoldings", null);
	}

	public List<WebElement> changePercentageValue() {
		return getWebElements(d, "IH_changePercentValue", "InvestmentHoldings", null);
	}

	public List<WebElement> accDrpdwnInvAcc() {
		return getWebElements(d, "IH_accDrpdwnInvAcc", "InvestmentHoldings", null);
	}

	public WebElement accsDetails(int i) {
		WebElement l = d.findElement(By.xpath("//li[@id='li-0-0-" + i + "']//span[2]"));
		return l;
	}
	
	public List<WebElement> accsDetails() {
		List<WebElement> l = d.findElements(By.xpath("//li[contains(@id,'li-0-0-')]//span[2]"));
		return l;
	}

	public WebElement AcntSelectDoneMobile() {
		return getWebElement(d, "AcntSelectDoneMobile", "InvestmentHoldings", null);
	}

	public boolean isAcntSelectDoneMobile() {
		return PageParser.isElementPresent("AcntSelectDoneMobile", "InvestmentHoldings", null);
	}

	public WebElement accsNumber(int i) {
		WebElement l = d.findElement(By.xpath("//li[@id='li-0-" + i + "']//span[3]"));
		return l;
	}

	public List<WebElement> accsNumber() {
		List<WebElement> l = d.findElements(By.xpath("//li[contains(@id,'li-0-')]//span[3]"));
		return l;
	}
	
	public List<WebElement> changePerValue() {
		return getWebElements(d, "IH_changePerValue", "InvestmentHoldings", null);
	}

	public WebElement clickDetailsDropdown() {
		return getWebElement(d, "clickDetailsDropdown", "InvestmentHoldings", null);
	}

	public WebElement clickChartCategoryMobile() {
		return getWebElement(d, "clickChartCategoryMobile", "InvestmentHoldings", null);
	}

	// vrinda
	public List<WebElement> holdingName() {
		return getWebElements(d, "IH_holdingName", "InvestmentHoldings", null);
	}

	public List<WebElement> companyName() {
		return getWebElements(d, "companyName", "InvestmentHoldings", null);
	}

	/**
	 * Added for account sharing
	 */

	public String getAdvisorReferanceID() throws AWTException, InterruptedException, JSONException {
		logger.info(">>>>> Getting a unique referance id..");
		boolean advId = false;
		advRefId = SAML.getRefrenceId();

		if (advRefId != null) {
			advId = true;
		}

		Assert.assertTrue(advId, ">>>>> Advisor referance Id not created.");
		return advRefId;

	}

	public String getInvestorName() {
		boolean invName = false;
		logger.info(">>>>> Getting a unique investor name..");
		investorName = SAML.getInvestorUserName();
		if (investorName != null) {
			invName = true;
		}

		Assert.assertTrue(invName, ">>>>> Advisor referance Id not created.");
		return investorName;

	}

	public WebElement finappHeader() {

		return getWebElement(d, "finappHeader_IH", pageName, frameName);
	}

	public WebElement noDataMsg_IH() {

		return getWebElement(d, "noDataMsg_IH", pageName, frameName);
	}

	public WebElement ftueClose_IH() {

		return getWebElement(d, "ftueClose_IH", pageName, frameName);
	}

	public WebElement dataSection_IH() {
		// Data Section Box in Investment Holdings
		return getWebElement(d, "dataSection_IH", pageName, frameName);
	}

	public WebElement noAcess_IH() {
		// Data Section Box in Investment Holdings
		return getWebElement(d, "noAcess_IH", pageName, frameName);
	}

	public WebElement invData() {

		return getWebElement(d, "finappHeader_IH", pageName, frameName);
	}

//added by Mallika for Group Stickyness

	public void clickIHccountDropDown() {
		// clikc account drop down
		click(accountDropDown());
		waitForPageToLoad(3000);

	}

	public WebElement IHGroupLink() {
		// group link in account dropdown
		return getWebElement(d, "IHGroupLink", pageName, frameName);
	}

	public boolean isMobile_GroupLink_Present() {

		return PageParser.isElementPresent("IH_Group_mob", "InvestmentHoldings", null);

	}

	private WebElement Mobile_GroupLink() {

		return getWebElement(d, "IH_Group_mob", "InvestmentHoldings", null);

	}

	public WebElement IHGroupName(String group) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "IHGroupName");
//	name = name.replaceAll("expensegroup", group);
		return d.findElement(By.xpath(name));
	}

	public boolean isMobile_ClickonGroup_Present() {

		return PageParser.isElementPresent("IH_Group_mob", "InvestmentHoldings", null);

	}

	public WebElement Mobile_ClickonGroup() {

		return getWebElement(d, "IH_Group_mob", "InvestmentHoldings", null);

	}

	public WebElement IH_done_mob() {

		waitForPageToLoad(5000);
		return getWebElement(d, "IH_done_mob", "InvestmentHoldings", null);

	}

	public boolean isIH_done_mob_Present() {

		return PageParser.isElementPresent("IH_done_mob", "InvestmentHoldings", null);

	}

	public void clickGroup(String group) {
		// click on group filter

		clickIHccountDropDown();
		waitForPageToLoad(3000);
		if (this.isMobile_GroupLink_Present()) {

			click(Mobile_GroupLink());
			waitForPageToLoad(3000);

		} else {
			click(IHGroupLink());
			waitForPageToLoad(3000);

		}

		if (this.isMobile_ClickonGroup_Present()) {

			click(IHGroupName(group));
			waitForPageToLoad(3000);

		} else {
			IHGroupName(group);
			waitForPageToLoad(3000);
		}
	}

	public WebElement changePercentageValueMobile() {
		return getWebElement(d, "changePercentageValueMobile", "InvestmentHoldings", null);
	}

	public boolean isNoDataScreenDisplayed() {
		return isDisplayed(noDataScreen, 3);
	}

	public void navigateToInvestmentHoldings() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		if (isDisplayed(getByObject("InvestmentHoldings", null, "continueBtnInWelcomeCM"), 3)) {
			click(continueBtnInWelcomeCM());
			click(goToMyInvestments());
		}
	}

	public void openAccountDropdown() {
		By accDropDown = getByObject(pageName, frameName, "accountDropDown");
		if (getAttribute(accDropDown, "aria-expanded").equals("false")) {
			click(accDropDown);
		}
	}

	public ArrayList<String> returnTheAccountsDisplayedInAccountsDropdown() {
		ArrayList<String> accountnames = new ArrayList<String>();
		openAccountDropdown();
		waitUntilElementIsVisible(accountDropdownValuesText, 15);
		List<WebElement> accountText = d.findElements(accountDropdownValuesText);
		for (WebElement account : accountText) {
			scrollElementIntoView(d, account, true);
			accountnames.add(account.getText());
		}
		logger.info("Accounts displayed :: " + accountnames);
		return accountnames;

	}

	public void closeAccountDropdown() {
		By accDropDown = getByObject(pageName, frameName, "accountDropDown");
		if(isDisplayed(accDropDown, 2))
		if (!getAttribute(accDropDown, "aria-expanded").equals("false")) {
			click(accDropDown);
		}
	}

	public void expandViewMoreDetailsInTotalAccountHoldingsBalance() {
		closeAccountDropdown();
		By viewOrHideDetailsLocator = getByObject(pageName, frameName, "viewDetailBtn");
		if (isDisplayed(viewOrHideDetailsLocator, 3)) {
			if (getAttribute(viewOrHideDetailsLocator, "aria-expanded").equals("false")) {
				click(viewOrHideDetailsLocator);
				waitFor(1);
			}
		}
		logger.info("Expanded view more details in investment holdings");
	}

	public void hideViewMoreDetailsInTotalAccountHoldingsBalance() {
		By viewOrHideDetailsLocator = getByObject(pageName, frameName, "viewDetailBtn");
		if (isDisplayed(viewOrHideDetailsLocator, 3)) {
			if (!getAttribute(viewOrHideDetailsLocator, "aria-expanded").equals("false")) {
				click(viewOrHideDetailsLocator);
				waitFor(1);
			}
		}
		logger.info("Hided the view more details button in investment holdings");
	}

	public float returnPresentTotalManualInvestmentAccountBalance() {
		expandViewMoreDetailsInTotalAccountHoldingsBalance();
		if (isDisplayed(manualAccount_InvestmentHoldings_Balance, 3)) {
			return Float.parseFloat(
					d.findElement(manualAccount_InvestmentHoldings_Balance).getText().substring(1).replace(",", ""));
		} else {
			return 0;
		}
	}

	public float returnTotalInvestmentHoldingsAccountBalance() {
		expandViewMoreDetailsInTotalAccountHoldingsBalance();
		if (isDisplayed(investmentHoldings_TotalBalance, 3)) {
			return Float
					.parseFloat(d.findElement(investmentHoldings_TotalBalance).getText().substring(1).replace(",", ""));
		} else {
			return 0;
		}
	}

	public void selectGivenAccountUnderInvestmentHoldingsAccountDropdown(String accountName) {
		openAccountDropdown();
		unCheck_AllInvestmentAccounts();
		String xpath = xpathForAccountNameInAccountDropdown.replace(replaceChar, accountName);
		click(By.xpath(xpath));
		closeAccountDropdown();
		waitUntilSpinnerWheelIsInvisible();
		waitForPageToLoad();

	}	
	public void addTheGivenAccountUnderInvestmentHoldingsAccountDropdown(String accountName) {
		openAccountDropdown();
		String xpathForAcccountDropdown = xpathForAccountNameCheckboxInAccountDropdown.replace(replaceChar, accountName);
		boolean isCheckBoxSelected=getAttribute(By.xpath(xpathForAcccountDropdown),"aria-checked").toLowerCase().equals("true") ? true:false;
		if(!isCheckBoxSelected) {
			String xpathForAccountname = xpathForAccountNameInAccountDropdown.replace(replaceChar, accountName);
			click(By.xpath(xpathForAccountname));
		}
		closeAccountDropdown();
		waitUntilSpinnerWheelIsInvisible();
	}

	public void unCheck_AllInvestmentAccounts() {
		try {
			waitFor(3);
			if (getAttribute(accountsDropdown_AllInvestmentAccounts_checkbox, "aria-checked").equals("true")) {
				d.findElement(accountsDropdown_AllInvestmentAccounts_checkbox).click();
			} else {
				d.findElement(accountsDropdown_AllInvestmentAccounts_checkbox).click();
				waitFor(1f);
				d.findElement(accountsDropdown_AllInvestmentAccounts_checkbox).click();
			}
		} catch (Exception e) {
		}
	}

	public float returnTotalAggregatedAccountsBalance() {
		waitUntilElementIsVisible(totalAggregatedAccountBalance, 30);
		return Float.parseFloat(d.findElement(totalAggregatedAccountBalance).getText().substring(1).replace(",", ""));
	}

	public float returnChangePercentValueOfAggregatedAccounts() {
		waitUntilElementIsVisible(investmentHoldings_ChangePercentvalue, 30);
		return Float.parseFloat(d.findElement(investmentHoldings_ChangePercentvalue).getText().replace("%", ""));
	}

	public boolean isAssetClassDropdownDisabled() {
		String areaDisabled = getAttribute(investmentHoldings_AssetClassDropdown, "aria-disabled");
		return areaDisabled.equals("true") ? true : false;
	}

	public float returnTheAmountDisplayedInDashboardOfManualInvestmentAccounts() {
		scrollToWebElementMiddle(d, d.findElement(dashboard_manualInvestmentAccountBalance));
		waitFor(2);
		return Float.parseFloat(
				d.findElement(dashboard_manualInvestmentAccountBalance).getText().substring(1).replace(",", ""));
	}

	public void createGroup(String groupName, String[] accountNames) {
		openAccountDropdown();
		if (this.isMobile_GroupLink_Present()) {
			click(Mobile_GroupLink());
		} else {
			click(IHGroupLink());
		}
		waitFor(3);
		if (SeleniumUtil.getElementCount(investmentHoldings_createFirstGroupButton) > 0) {
			click(investmentHoldings_createFirstGroupButton);
		} else {
			click(investmentHoldings_createGroupButton);
		}
		waitUntilSpinnerWheelIsInvisible();
		click(investmentHoldings_createGroup_GroupNameInputBox);
		d.findElement(investmentHoldings_createGroup_GroupNameInputBox).sendKeys(groupName);
		for (String accountName : accountNames) {
			scrollToWebElementMiddle(d, d.findElement(
					By.xpath(xpathForAccountNameInCreateGroupPopupDropdown.replace(replaceChar, accountName))));
			d.findElement(By.xpath(xpathForAccountNameInCreateGroupPopupDropdown.replace(replaceChar, accountName)))
					.click();
		}
		click(investmentHoldings_createGroupPopup_createGroupButton);
		waitUntilSpinnerWheelIsInvisible();
		waitFor(2);
	}

	public void selectGroupInInvestmentHoldings(String groupName) {
		openAccountDropdown();
		if (this.isMobile_GroupLink_Present()) {
			click(Mobile_GroupLink());
		} else {
			click(IHGroupLink());
		}
		waitFor(3);
		click(By.xpath(xpathForGroupNameInGroupsDropdown.replace(replaceChar, groupName)));
		waitUntilSpinnerWheelIsInvisible();
		waitFor(2);
	}

}
