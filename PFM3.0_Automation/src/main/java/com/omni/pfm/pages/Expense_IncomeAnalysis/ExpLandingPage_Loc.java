/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Pallavee Keshari
*/



package com.omni.pfm.pages.Expense_IncomeAnalysis;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* 

* @autor -Ashwin PM 

* 

* 

*/

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ExpLandingPage_Loc {

	static Logger logger = LoggerFactory.getLogger(ExpLandingPage_Loc.class);
	public static WebDriver d = null;
	static WebElement we;

	public ExpLandingPage_Loc(WebDriver d) {
		ExpLandingPage_Loc.d = d;
	}


	public WebElement LinkAnAccBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAnAccBtn", "Expense", null);
	}
	public WebElement AccessNotGrantedMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "AccessNotGrantedMsg", "Expense", null);
	}
	public WebElement AccessNotGrantedDiscription() {
		return SeleniumUtil.getVisibileWebElement(d, "AccessNotGrantedDiscription", "Expense", null);
	}
	
	public WebElement showMoreListButton() {
		return SeleniumUtil.getVisibileWebElement(d, "showMoreListButton", "Expense", null);
	}
	public List<WebElement> showNumberOfTxn() {
		return SeleniumUtil.getWebElements(d, "showNumberOfTxn", "Expense", null);
	}
	
	public List<WebElement> verifyMonthlyAmtForEAAndIA() 

	{

	       return SeleniumUtil.getWebElements(d, "IAMonthlyAmt", "Expense", null);

	}
	//Lavanya
	

public WebElement verifyTxnHeaderForSelectedMLC()

{

   return SeleniumUtil.getWebElement(d, "verifyTxnHeaderForSelectedMLC", "Expense", null);

}

 

public WebElement closeBtnForMlcTxn()

{

   return SeleniumUtil.getWebElement(d, "closeBtnForMlcTxn", "Expense", null);

}

 

public List<WebElement> verifyMlcsTransactions()

{

   return SeleniumUtil.getWebElements(d, "verifyMlcsTransactions", "Expense", null);

}
	
	public List<WebElement> listOfMonthsInLegendSection()

	{

	   return SeleniumUtil.getWebElements(d, "listOfMonthsInLegendSection", "Expense", null);

	}

	 

	public String listOfLastSixMonths(int i)

	{

	       String expected=null;

	       if(i==0)

	       {

	              expected = DateUtil.getMonthsWithYear(i);

	       }

	       else

	       {

	            expected = DateUtil.getMonthsWithYear(-i);

	       }

	       return expected;

	}

	public void clickingTrends()

	{

	       SeleniumUtil.click(verifyMoreButton());

	       SeleniumUtil.waitForPageToLoad(2000); 

	       SeleniumUtil.click(verifyTrendsLink());

	       SeleniumUtil.waitForPageToLoad(3000);

	}

	 

	public void selectingTimeFilterForTrends(int i)

	{

	       SeleniumUtil.click(timeFilterDropdown());

	       SeleniumUtil.waitForPageToLoad(2000);

	       SeleniumUtil.click(getDateFormat().get(i));

	       SeleniumUtil.waitForPageToLoad(2000);

	      

	       SeleniumUtil.click(selectExpenseCategory()); // Clicking on high level category. ( Home expenses )

	       SeleniumUtil.waitForPageToLoad();

	      

	       SeleniumUtil.click(verifyMoreButton());

	       SeleniumUtil.waitForPageToLoad(2000); 

	       SeleniumUtil.click(verifyTrendsLink());

	       SeleniumUtil.waitForPageToLoad(3000);

	}

	 

	 

	 
	
	public WebElement finappHeader_HLCHeader() {

		return SeleniumUtil.getWebElement(d, "finappHeader_HLCHeader", "Expense", null);

		}
	public WebElement txnHeaderMLC() {

		return SeleniumUtil.getWebElement(d, "txnHeaderMLC", "Expense", null);

		}
	
	public List<WebElement> transactionList() {

		return SeleniumUtil.getWebElements(d, "transactionList", "Expense", null);

		}
	
	public WebElement selectExpenseCategory() {

		return SeleniumUtil.getWebElement(d, "selectExpenseCategory", "Expense", null);

		}
	public WebElement selectMLCInExpCategoriesIncome()
	{
		return SeleniumUtil.getWebElement(d, "selectMLCInExpCategoriesIncome", "Categories", null);
	}
	
	public WebElement selectHLCInExpCategoriesIncome()
	{
		return SeleniumUtil.getWebElement(d, "selectHLCInExpCategoriesIncome", "Categories", null);
	}
	public WebElement settingsMenu()
	{
	       return SeleniumUtil.getWebElement(d, "expMenuSettings", "Expense", null);
	}

	public WebElement accountGroup()
	{
	      return SeleniumUtil.getWebElement(d, "expMenuAccountGroups", "Expense", null);

	}
	public WebElement createAccountGroupButton()

	{
    return SeleniumUtil.getVisibileWebElement(d, "expNewGroupButton", "Expense", null);
	}
	
	
	
	
	public boolean renamingOfMLCInCategories_Income()
	{
		SeleniumUtil.click(selectMLCInExpCategoriesIncome());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(getShowAsGhostText_MC());
		getShowAsGhostText_MC().clear();
		getShowAsGhostText_MC().sendKeys(PropsUtil.getDataPropertyValue("RenameMLCInCategoriesIncome"));
		SeleniumUtil.waitForPageToLoad(2000);
		
		boolean status = saveChangesButton().isDisplayed();
		SeleniumUtil.click(saveChangesButton());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		return status;
	}
	public boolean renamingOfHLCInCategories_Income()
	{
		SeleniumUtil.click(selectHLCInExpCategoriesIncome());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(editHLCatInputField_MC());
		editHLCatInputField_MC().clear();
		editHLCatInputField_MC().sendKeys(PropsUtil.getDataPropertyValue("RenameHLCInCategoriesIncome"));
		SeleniumUtil.waitForPageToLoad(2000);
		
		boolean status = saveChangesButton().isDisplayed();
		SeleniumUtil.click(saveChangesButton());
        SeleniumUtil.waitForPageToLoad(2000);
		
		return status;
	}
	
	public WebElement verifyMLCInExpenseByCategoryIncome1()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyMLCInExpenseByCategoryIncome1", "Expense", null);
	}
	public WebElement hlcBackBtn()
	{
		return SeleniumUtil.getVisibileWebElement(d, "HlcBackBtn", "Expense", null);
	}
	public WebElement clickHLC()
	{
		return SeleniumUtil.getVisibileWebElement(d, "clickHLC", "Expense", null);
	}
	public WebElement verifyLastMonthDate()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyLastMonth", "Expense", null);
	}

	public WebElement verifyMLCInExpenseByCategoryIncome()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyMLCInExpenseByCategoryIncome", "Expense", null);
	}

	public WebElement verifyManualAccInAccFilter1()
	{
		return SeleniumUtil.getWebElement(d, "verifyManualAccInAccFilter1", "Expense", null);
	}
	
	public WebElement addTXn() 
	{
		return SeleniumUtil.getWebElement(d, "addTXn", "Expense", null);
	}
	public WebElement addTXnPopUp() 
	{
		return SeleniumUtil.getWebElement(d, "addTXnPopUp", "Expense", null);
	}
	public WebElement closeIconAddTxn() 
	{
		return SeleniumUtil.getWebElement(d, "closeIconAddTxn", "Expense", null);
	}
	public WebElement todaysDate() 
	{
		return SeleniumUtil.getWebElement(d, "todaysDate", "Expense", null);
	}
	public WebElement closeCustomDate() 
	{
		return SeleniumUtil.getWebElement(d, "closeCustomDate", "Expense", null);
	}
	public WebElement calenderIconToDate() 
	{
		return SeleniumUtil.getWebElement(d, "calenderIconToDate", "Expense", null);
	}
	public WebElement calenderPopUpOpen() 
	{
		return SeleniumUtil.getWebElement(d, "calenderPopUpOpen", "Expense", null);
	}
	public WebElement incomeTabCategories() 
	{
		return SeleniumUtil.getWebElement(d, "incomeTabCategories", "Expense", null);
	}
	
	public WebElement Trav_exp_table() 
	{
		return SeleniumUtil.getWebElement(d, "Trav_exp_table", "Expense", null);
	}
	public List<WebElement> topFirstTransacTable() 
	{
		return SeleniumUtil.getWebElements(d, "topFirstTransacTable", "Expense", null);
	}
	public WebElement backToIncome() 
	{
		return SeleniumUtil.getWebElement(d, "backToIncome", "Expense", null);
	}
	
	
	
	//*************************************************************************
	
	
	public WebElement selectExpenseIncomeDropDown(String a) {

		return d.findElement(By.xpath("//a[@aria-label='" + a + "']"));

	}

	public WebElement highChartsLegends() {
		return SeleniumUtil.getVisibileWebElement(d, "EAhighChartsLegends", "Expense", null);
	}

	public List<WebElement> barChartsLegend() {
		return SeleniumUtil.getWebElements(d, "EAbarChartsLegend", "Expense", null);
	}

	public WebElement highCharts() {
		return SeleniumUtil.getVisibileWebElement(d, "EAhighCharts", "Expense", null);
	}

	public WebElement xAxislabel() {
		return SeleniumUtil.getVisibileWebElement(d, "EAxAxislabel", "Expense", null);
	}

	public WebElement text(int i) {

		return d.findElement(By.xpath(
				"//ul[@class='legends-category boxShadow y-tabular-container y-border-radius y-border-white-background']/li["
						+ i + "]/a//span[contains(@class,'month-spending')]"));

	}

	public List<WebElement> locator() {
		return SeleniumUtil.getWebElements(d, "EAlocator", "Expense", null);
	}

	public WebElement selectExpenseIncomeDropDownIcon(String a) {
		return d.findElement(By.xpath("//i[contains(@class," + "'" + a + "'" + ")]"));
	}

	public List<WebElement> tooltipChart() {

		return SeleniumUtil.getWebElements(d, "EAtooltipChart", "Expense", null);

	}
	public List<WebElement> iAEABarChartBar() {

		return SeleniumUtil.getWebElements(d, "iAEABarChartBar", "Expense", null);

	}

	public List<WebElement> monthAndBalance() {

		return SeleniumUtil.getWebElements(d, "EAmonthAndBalance", "Expense", null);

	}

	public void clickChart(int n)
	{

		WebElement svgObject = d.findElement(By.xpath("//*[name()='g']/*[name()='rect'][" + n + "]"));

		Actions builder = new Actions(d);

		builder.click(svgObject).build().perform();

	}
	
	public void verifyClickChart(String n)
	{

		WebElement svgObject = d.findElement(By.xpath("//*[name()='g']/*[name()='rect'][" + n + "]"));

		Actions builder = new Actions(d);

		builder.click(svgObject).build().perform();

	}
	
	public WebElement verifyBarGraph(int n)
	{
		WebElement svgObject = d.findElement(By.xpath("//*[name()='g']/*[name()='rect'][" + n + "]"));
		return svgObject;
	}

	public WebElement selectFromChart(String parameterName, int i)
	{
		return d.findElement(By.xpath("//*[name()='g']/*[name()='" + parameterName + "'][" + i + "]"));
	}
	
	public WebElement verifySelectFromChart(String parameterName, String i)
	{
		return d.findElement(By.xpath("//*[name()='g']/*[name()='" + parameterName + "'][" + i + "]"));
	}
	
	public WebElement selectingFromChart(String parameterName, int i)
	{
		return d.findElement(By.xpath("//*[name()='g']/*[name()='" + parameterName + "'][" + i + "]"));
	}

	public WebElement dottedLine() {
		return SeleniumUtil.getVisibileWebElement(d, "EAdottedLine", "Expense", null);
	}

	public WebElement YAxisAmt(int i, int n) {

		return d.findElement(By.xpath("//*[name()='g'][" + i + "]/*[name()='text'][" + n + "]"));

	}

	public List<WebElement> DataPoint() {
		return SeleniumUtil.getWebElements(d, "EADataPoint", "Expense", null);
	}

	public List<WebElement> Month() {
		return SeleniumUtil.getWebElements(d, "EAMonth", "Expense", null);
	}

	public WebElement getAmountLandingPAge(int n) {
		return d.findElements(By.xpath("//a[contains(@id,'iaea_hlc')]//span[4]")).get(n);
	}

	public WebElement getAmountHLLandingPAge(int n) {

		return d.findElements(By.xpath("//div[@id='iAEALegends']//a[contains(@id,'iaea_hlc')]/span[4]")).get(n);

	}

	public List<WebElement> getAmountMasterCategory() {

		// Returns the amount in the table in MAster level category

		return d.findElements(By.xpath("//a[contains(@id,'iaea_mlc')]//span[3]"));

	}

	public List<WebElement> getPercentageHighLevelCategory() {

		// Returns the percentage in the table in High lever category

		return d.findElements(By.xpath("//a[contains(@id,'iaea_hlc')]//span[4]//span[1]"));

	}

	public List<WebElement> getAmount()

	{

		return d.findElements(By.xpath("(//span[contains(@class,'month-spending')])"));

	}

	public void getAccountsAndGroups() {

		SeleniumUtil.click(d.findElement(By.xpath("//a[contains(@id,'groupsRadio')]")));

	}
	public WebElement verifyGpAAcctGp()
			{
				return SeleniumUtil.getWebElement(d, "verifyGpAAcctGp", "Expense", null);
			}
			

	public WebElement getGroups(String n) {

		return d.findElement(By.xpath("//span[text()='" + n + "']"));

	}

	public WebElement getGroups1(int n) {

		return d.findElements(By.xpath("//div[@class='groups-tab']//span[@class='ellipsis name text text-css-level1']"))
				.get(n);

	}

	public List<WebElement> getDateFormat() {

		return d.findElements(By.className("filter")).get(1)
				.findElements(By.cssSelector(".ellipsis.name.text.text-css-level1"));

	}

	public List<WebElement> getListOfMonth()

	{

		return d.findElements(By.cssSelector(".category-color"));

	}

	public WebElement getPercentageAndDiffInAmount(int n)

	{

		return d.findElements(By.xpath("//span[@class='show-for-medium-up']")).get(n);

	}

	public String get_Prvious6MonthName(int monthNumber) {

		// Get the past 6 month date

		Calendar now = Calendar.getInstance();

		now = Calendar.getInstance();

		now.add(Calendar.MONTH, -monthNumber);

		String monthName = new SimpleDateFormat("MMM").format(now.getTime()) + " " + now.get(Calendar.YEAR);

		return monthName;

	}

	public String getEAWelcomeCMDescription() {

		String loc = "//div[@class='ftue-content']/p";

		String desc = d.findElement(By.xpath(loc)).getText().trim();

		return desc;

	}

	public List<WebElement> bulletInCoachMark()

	{

		return d.findElements(By.xpath("//div[@class='ftue-icon-container']//span"));

	}

	public String tooltip() {

		// Selects a bar and mouse hover on it which would displays the tooltip .

		// Returns the Amount on the ToolTip

		SeleniumUtil.fluentWait(d);

		Actions action = new Actions(d);

		// Need to check from this Line

		WebElement we = d.findElement(By.xpath("//*[name()='g'][1]/*[name()='g'][1]/*[name()='path'][1]"));

		action.moveToElement(we)
				.moveToElement(d.findElement(By.xpath("//*[name()='g'][1]/*[name()='g'][1]/*[name()='path'][1]")))
				.build().perform();

		SeleniumUtil.waitForPageToLoad();

		String text = d.findElement(By.xpath("//*[name()='g'][3]/*[name()='text']/*[name()='tspan'][2]")).getText();

		return text;

	}

	public String tooltipHighLevelCategory() {

		// mouse hover on chart which would displays the tooltip .

		// Returns the Amount-category on the ToolTip

		Actions action = new Actions(d);

		WebElement we = d.findElement(By.xpath("//*[name()='g'][1]/*[name()='g'][1]/*[name()='path'][2]"));

		action.moveToElement(we).moveToElement(d.findElement(By.xpath("//*[name()='g'][1]/*[name()='g'][1]/*[name()='path'][2]"))).build().perform();

		SeleniumUtil.waitForPageToLoad(2000);
		
		System.out.println("Chart Test Value:"+ d.findElement(By.xpath("//*[name()='g'][3]/*[name()='text'][1]/*[name()='tspan'][2]")).getText());

		return d.findElement(By.xpath("//*[name()='g'][3]/*[name()='text'][1]/*[name()='tspan'][2]")).getText();

	}
	public String getFirstDayOfTheMonthAndTodayDate() {
        // Returns the first day of the current month and todays Date in (MM/DD/YYYY)
        // format
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat(PropsUtil.getDataPropertyValue("DateFormat"));
        String todayDate = sdf.format(today);
        String FirstDayOfMonth = sdf.format(firstDayOfMonth);
        String total = FirstDayOfMonth + " - " + todayDate;
        System.out.println(FirstDayOfMonth + " - " + todayDate);
        return total;
 }

public String getLastMonthStartEndDate() {
        // Returns the First and Last Day of the previous Month
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = cal.getTime();
        DateFormat sdf = new SimpleDateFormat(PropsUtil.getDataPropertyValue("DateFormat"));
        String lastMonthStartDate = sdf.format(firstDateOfPreviousMonth);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal
        Date lastDateOfPreviousMonth = cal.getTime();
        DateFormat sdf1 = new SimpleDateFormat(PropsUtil.getDataPropertyValue("DateFormat"));
        String lastMonthEndDate = sdf1.format(lastDateOfPreviousMonth);
        String dateRange = lastMonthStartDate + " - " + lastMonthEndDate;
        System.out.println(dateRange);
        return dateRange;

 }
	public Date getOneMonthDateRange() {

		Calendar c = Calendar.getInstance();// this takes current date

		c.set(Calendar.DAY_OF_MONTH, 1);

		System.out.println(c.getTime());

		return c.getTime();

	}

	public WebElement SelectTodaysDate() {

		return d.findElement(By.className("custom-end-dateCnr")).findElement(By.className("todays-date"));

	}

	public WebElement SelectDay(int tomorowDate) {

		// Selects the same date from the past year

		return d.findElement(By.className("custom-start-dateCnr")).findElements(By.xpath(".//*[@id='day10']")).get(0);

	}

	public WebElement ErrorMsg(int n) {

		return d.findElement(By.xpath(".//*[@id='eacustomDatesFilterPopupRegion']/div/div[1]/div/p[" + n + "]"));

	}

	public int getTomorowsDate() {

		/*
		 * 
		 * DateFormat dateFormat = new SimpleDateFormat("dd");
		 * 
		 * Date date = new Date();
		 * 
		 * System.out.println(dateFormat.format(date));
		 * 
		 * return dateFormat.format(date);
		 * 
		 */

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 1);

		Date tomorrow = calendar.getTime();

		DateFormat dateFormat = new SimpleDateFormat("dd");

		String tomorrowAsString = dateFormat.format(tomorrow);

		int a = Integer.parseInt(tomorrowAsString);

		System.out.println(a);

		return a;

	}

	public WebElement highchartsXlabel() {

		return d.findElement(By.className("highcharts-xaxis-labels"));

	}

	public void getAccountsAndGroups1() {

		d.findElement(By.xpath("//input[contains(@id,'groupsRadio')]")).click();

	}

	public WebElement welcomeCoachMarkDesc() {
		return SeleniumUtil.getVisibileWebElement(d, "welcomeCoachMarkDesc", "Expense", null);
	}

	/**********************************
	 * New Methods
	 ******************************************************/

	public WebElement featureTourBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "featureTourBtn", "Expense", null);
	}

	public WebElement moreIconDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "moreIconDropDown", "Expense", null);
	}

	public WebElement MoreIconMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "MoreIconMobile", "Expense", null);
	}

	public WebElement IncomeAnalysis() {
		return SeleniumUtil.getVisibileWebElement(d, "IncomeAnalysis", "Expense", null);
	}

	public WebElement EAMObile() {
		return SeleniumUtil.getVisibileWebElement(d, "EAMObile", "Expense", null);
	}

	public WebElement PageTitleTextMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "PageTitleTextMobile", "Expense", null);
	}

	public WebElement IATitleText() {
		return SeleniumUtil.getVisibileWebElement(d, "IATitleText", "Expense", null);
	}
	
	public WebElement Inconme_Menuclick_NW() {
		return SeleniumUtil.getVisibileWebElement(d, "Inconme_Menuclick_NW", "Expense", null);
	}
	public WebElement Title_Btnclick_NW() {
		return SeleniumUtil.getVisibileWebElement(d, "Title_Btnclick_NW", "Expense", null);
	}
	public List<WebElement> Deposit_name_NW() {
		return SeleniumUtil.getWebElements(d, "Deposit_name_NW",  "Expense", null);
	}
	public List<WebElement> WithdrawAcc_name_NW() {
		return SeleniumUtil.getWebElements(d, "WithdrawAcc_name_NW",  "Expense", null);
	}
	public WebElement CMlinkAccountLink() {
		return SeleniumUtil.getVisibileWebElement(d, "EACMlinkAccount", "Expense", null);
	}

	public WebElement gotoAnalysisBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "gotoAnalysisBtn", "Expense", null);
	}

	public WebElement linkAccountWelcomeCMHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "EAlinkAccountWelcomeCM", "Expense", null);
	}

	public WebElement continueButton() {
		return SeleniumUtil.getVisibileWebElement(d, "continueButton", "Expense", null);
	}

	public WebElement welcomeCoachMarkHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "welcomeCoachMarkHeading", "Expense", null);
	}

	public WebElement NoPercentage() {
		return SeleniumUtil.getVisibileWebElement(d, "NoPercentage", "Expense", null);
	}

	public WebElement dateFormat() {
		return SeleniumUtil.getVisibileWebElement(d, "dateFormat", "Expense", null);
	}

	public WebElement dounutBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "dounutBlock", "Expense", null);
	}

	public WebElement timeFilter() {
		return SeleniumUtil.getVisibileWebElement(d, "timeFilter", "Expense", null);
	}

	public WebElement timeFilterDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "timeFilterDropdown", "Expense", null);
	}

	public WebElement allAccDropDownBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccDropDownBlock", "Expense", null);
	}

	public WebElement heading() {
		return SeleniumUtil.getVisibileWebElement(d, "heading", "Expense", null);
	}

	public WebElement expensebyCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "expensebyCategory", "Expense", null);
	}

	public WebElement highLevelCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "highLevelCategory", "Expense", null);
	}

	public WebElement masterCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "masterCategory", "Expense", null);
	}

	public WebElement backToExpenseCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "backToExpenseCategory", "Expense", null);
	}

	public WebElement backToExpenseAnalysisInCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "backToExpenseAnalysisInCategory", "Expense", null);
	}

	public WebElement addTransactionLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "addTransactionLabel", "Expense", null);
	}

	public WebElement moreButtonForPrint() {
		return SeleniumUtil.getVisibileWebElement(d, "moreButtonForPrint", "Expense", null);
	}

	public WebElement printBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "printBlock", "Expense", null);
	}

	public WebElement eAiADropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "eAiADropDown", "Expense", null);
	}

	public WebElement averageAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "averageAmt", "Expense", null);
	}

	public WebElement LinkAccBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccBtn", "Expense", null);
	}

	public WebElement CloseFLPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "CloseFLPopUp", "Expense", null);
	}

	public WebElement dateRangeHeaderCutomRange() {
		return SeleniumUtil.getVisibileWebElement(d, "dateRangeHeaderCutomRange", "Expense", null);
	}

	public WebElement closeCutomRangePopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "closeCutomRangePopUp", "Expense", null);
	}

	public WebElement dateHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "dateHeading", "Expense", null);
	}

	public WebElement trendLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "trendLabel", "Expense", null);
	}

	public WebElement moreButtonExpHLCPage() {
		return SeleniumUtil.getVisibileWebElement(d, "moreButtonExpHLCPage", "Expense", null);
	}

	public WebElement trendText() {
		return SeleniumUtil.getVisibileWebElement(d, "trendText", "Expense", null);
	}

	public WebElement trendCloseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "trendCloseIcon", "Expense", null);
	}

	public WebElement selectTodayDate() {
		return SeleniumUtil.getVisibileWebElement(d, "selectTodayDate", "Expense", null);
	}

	public WebElement selectToDate() {
		return SeleniumUtil.getVisibileWebElement(d, "selectToDate", "Expense", null);
	}

	public WebElement selectFromDate() {
		return SeleniumUtil.getVisibileWebElement(d, "selectFromDate", "Expense", null);
	}

	public WebElement selectNextMonth() {
		return SeleniumUtil.getVisibileWebElement(d, "selectNextMonth", "Expense", null);
	}

	public WebElement selectPreviousYear() {
		return SeleniumUtil.getVisibileWebElement(d, "selectPreviousYear", "Expense", null);
	}

	public WebElement updateBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "updateBtn", "Expense", null);
	}

	public WebElement WelcomeLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "WelcomeLabel", "Expense", null);
	}

	public WebElement PopUpBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "PopUpBlock", "Expense", null);
	}

	public WebElement ContinueBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "ContinueBtn", "Expense", null);
	}

	public WebElement LinkAccLAbel() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccLAbel", "Expense", null);
	}

	public WebElement LinkAccBtn1() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccBtn1", "Expense", null);
	}

	public WebElement noThanksBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "noThanksBtn", "Expense", null);
	}

	public WebElement eaHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "eaHeader", "Expense", null);
	}

	public WebElement thisMonthBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "thisMonthBlock", "Expense", null);
	}

	public WebElement homeMaintence() {
		return SeleniumUtil.getVisibileWebElement(d, "homeMaintence", "Expense", null);
	}

	public WebElement homeMaintenceTransaction() {
		return SeleniumUtil.getVisibileWebElement(d, "homeMaintenceTransaction", "Expense", null);
	}

	// Coach marks

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

	// Added the values of Spending and Income and Expense analysis

	public WebElement getSpending() {
		return SeleniumUtil.getWebElement(d, "spending_Temp", "Expense", null);
	}

	public WebElement getIAE() {
		return SeleniumUtil.getWebElement(d, "IAE_Temp", "Expense", null);
	}

	// The below method is used for creating 3 groups while testing EA

	public void createGroupForEA(String groupName) {

		// Click on Create New Group
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expNewGroupButton", "Expense", null));
		SeleniumUtil.waitForPageToLoad(5000);

		// Type group name
		SeleniumUtil.getVisibileWebElement(d, "expGroupName", "Expense", null).click();
		SeleniumUtil.getVisibileWebElement(d, "expGroupName", "Expense", null).clear();
		SeleniumUtil.getVisibileWebElement(d, "expGroupName", "Expense", null).sendKeys(groupName);

		// Click on check box
		try {
			SeleniumUtil.getVisibileWebElement(d, "expCheckBox", "Expense", null).click();
		} catch (Exception e) {
			SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expCheckBox", "Expense", null));
		}

		// Click on Create Group button
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expCreateGroupButton", "Expense", null));
		SeleniumUtil.waitForPageToLoad(10000);

	}
	public void createGroupForIA(String groupName) {

		// Click on Create New Group
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expNewGroupButton", "Expense", null));
		SeleniumUtil.waitForPageToLoad(5000);

		// Type group name
		SeleniumUtil.getVisibileWebElement(d, "expGroupName", "Expense", null).click();
		SeleniumUtil.getVisibileWebElement(d, "expGroupName", "Expense", null).clear();
		SeleniumUtil.getVisibileWebElement(d, "expGroupName", "Expense", null).sendKeys(groupName);

		// Click on check box
		try {
			SeleniumUtil.getVisibileWebElement(d, "expCheckBox1", "Expense", null).click();
		} catch (Exception e) {
			SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expCheckBox1", "Expense", null));
		}

		// Click on Create Group button
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "expCreateGroupButton", "Expense", null));
		SeleniumUtil.waitForPageToLoad(10000);

	}
	
	
	///////////////////////////////////// Lavanya
	
	public WebElement linkAnAccountbtn_Exp()
	{
		return SeleniumUtil.getWebElement(d, "linkAnAccountbtn_Exp", "Expense", null);
	}
	
	public WebElement linkAnAccountFloater_Exp()
	{
		return SeleniumUtil.getWebElement(d, "linkAnAccountFloater_Exp", "Expense", null);
	}
	
	public WebElement desertImageOrIcon_Exp()
	{
		return SeleniumUtil.getWebElement(d, "desertImageOrIcon_Exp", "Expense", null);
	}
	
	public WebElement selectMLCInExpCategories()
	{
		return SeleniumUtil.getWebElement(d, "selectMLCInExpCategories", "Categories", null);
	}

	public WebElement getShowAsGhostText_MC()
	{
		return SeleniumUtil.getWebElement(d, "getShowAsGhostText_MC", "Categories", null);
	}
	
	public WebElement saveChangesButton() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesButton_MC","Categories", null);
	}
	
	public WebElement editHLCatInputField_MC() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "editHLCatInputField_MC","Categories", null);
	}
	
	public WebElement selectHLCInExpCategories()
	{
		return SeleniumUtil.getWebElement(d, "selectHLCInExpCategories", "Categories", null);
	}
	
	
	
	public boolean renamingOfMLCInCategories_Exp()
	{
		SeleniumUtil.click(selectMLCInExpCategories());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(getShowAsGhostText_MC());
		getShowAsGhostText_MC().clear();
		getShowAsGhostText_MC().sendKeys(PropsUtil.getDataPropertyValue("RenameMLCInCategories"));
		SeleniumUtil.waitForPageToLoad(2000);
		
		boolean status = saveChangesButton().isDisplayed();
		SeleniumUtil.click(saveChangesButton());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		
		return status;
	}
	
	public boolean renamingOfHLCInCategories_Exp()
	{
		SeleniumUtil.click(selectHLCInExpCategories());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(editHLCatInputField_MC());
		editHLCatInputField_MC().clear();
		editHLCatInputField_MC().sendKeys(PropsUtil.getDataPropertyValue("RenameHLCInCategories"));
		SeleniumUtil.waitForPageToLoad(2000);
		
		boolean status = saveChangesButton().isDisplayed();
		SeleniumUtil.click(saveChangesButton());
        SeleniumUtil.waitForPageToLoad(2000);
		
		return status;
	}
	
	public WebElement selectCategoryDDInAddTrans()
	{
		return SeleniumUtil.getVisibileWebElement(d, "selectCategoryDDInAddTrans", "Expense", null);
	}
	
	public WebElement searchFieldOfCategoryInAddTrans()
	{
		return SeleniumUtil.getVisibileWebElement(d, "searchFieldOfCategoryInAddTrans", "Expense", null);
	}
	
	public WebElement verifyHLCInExpenseByCategory()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyHLCInExpenseByCategory", "Expense", null);
	}
	public WebElement verifyHLCInExpenseByCategoryInc()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyHLCInExpenseByCategory1", "Expense", null);
	}
	
	public WebElement verifyMLCInExpenseByCategory()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyMLCInExpenseByCategory", "Expense", null);
	}
	
	public WebElement verifyCustomDates()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyCustomDates", "Expense", null);
	}
	
	public WebElement verifyLastMonth()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyLastMonth", "Expense", null);
	}
		
	public WebElement verifyCurrentMonth()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyCurrentMonth", "Expense", null);
	}
	
	public WebElement verifyCurrentMonthsPerAndAmtInTable()
	{
		return SeleniumUtil.getWebElement(d, "verifyCurrentMonthsPerAndAmtInTable", "Expense", null);
	}
	
	public List<WebElement> verifyCurrentMonthsPerAndAmtInEATable()
	{
		return SeleniumUtil.getWebElements(d, "verifyCurrentMonthsPerAndAmtInEATable", "Expense", null);
	}
	
	public WebElement verifyAccountsDD()
	{
		return SeleniumUtil.getWebElement(d, "verifyAccountsDD", "Expense", null);
	}
	
	public WebElement verifyAccountsDDIsOpened()
	{
		return SeleniumUtil.getWebElement(d, "verifyAccountsDDIsOpened", "Expense", null);
	}
	
	public WebElement verifyAccountsDDCheckBox()
	{
		return SeleniumUtil.getWebElement(d, "verifyAccountsDDCheckBox", "Expense", null);
	}
	public WebElement verifyAccountsDDCheckBox1()
	{
		return SeleniumUtil.getWebElement(d, "verifyAccountsDDCheckBox1", "Expense", null);
	}
	
	public WebElement verifyEAWithNoData()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAWithNoData", "Expense", null);
	}
	
	public WebElement verifyEAWithData()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAWithData", "Expense", null);
	}
	
	public WebElement verifyEAAccountGp()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAAccountGp", "Expense", null);
	}
	
	public WebElement verifyEAAccounts()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAAccountGp", "Expense", null);
	}
	
	public List<WebElement> verifyEAListOfAccounts()
	{
		return SeleniumUtil.getWebElements(d, "verifyEAListOfAccounts", "Expense", null);
	}
	
	public WebElement verifyEAAccountGpTab()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAAccountGpTab", "Expense", null);
	}
	
	public WebElement verifyManualAccInAccFilter()
	{
		return SeleniumUtil.getWebElement(d, "verifyManualAccInAccFilter", "Expense", null);
	}
	
	public WebElement selectCustomDateFilter()
	{
		return SeleniumUtil.getWebElement(d, "selectCustomDateFilter", "Expense", null);
	}
	
	public WebElement customFilterErrorMsg()
	{
		return SeleniumUtil.getWebElement(d, "customFilterErrorMsg", "Expense", null);
	}
	
	public WebElement invalidDateInputField()
	{
		return SeleniumUtil.getVisibileWebElement(d, "invalidDateInputField", "Expense", null);
	}
	
	public WebElement closeCustomFilter()
	{
		return SeleniumUtil.getVisibileWebElement(d, "closeCustomFilter", "Expense", null);
	}
	
	public WebElement verifyUnCategorizedTrans()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyUnCategorizedTrans", "Expense", null);
	}
	
	public WebElement verifyEAFilterCategories()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyEAFilterCategories", "Expense", null);
	}
	
	public WebElement selectHLCInFilterCategories()
	{
		return SeleniumUtil.getVisibileWebElement(d, "selectHLCInFilterCategories", "Expense", null);
	}
	public WebElement selectHLCInFilterCategoriesInc()
	{
		return SeleniumUtil.getVisibileWebElement(d, "selectHLCInFilterCategoriesInc", "Expense", null);
	}
	
	public List<WebElement> verifyEAFilterCategoriesCheckBoxes()
	{
		return SeleniumUtil.getWebElements(d, "verifyEAFilterCategoriesCheckBoxes", "Expense", null);
	}
	
	public void deselectingTheCheckBoxesOfCategories()
	{
		List<WebElement> deselect = verifyEAFilterCategoriesCheckBoxes();
		for(WebElement element : deselect)
		{
			SeleniumUtil.click(element);
			SeleniumUtil.waitForPageToLoad(2000);
		}
	}
	
	public void selectingTheCheckBoxesOfCategories()
	{
		List<WebElement> select = verifyEAFilterCategoriesCheckBoxes();
		for(WebElement element : select)
		{
			SeleniumUtil.click(element);
			SeleniumUtil.waitForPageToLoad(2000);
		}
	}
	
	public WebElement verifyEAFilterCategoriesDoneBtn()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAFilterCategoriesDoneBtn", "Expense", null);
	}
	
	public WebElement verifyHLCInEBC_Categories()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyHLCInEBC_Categories", "Expense", null);
	}
	
	public List<WebElement> selectHLC_Categories()
	{
		return SeleniumUtil.getWebElements(d, "selectHLC_Categories", "Expense", null);
	}
	
	
	public WebElement verifyEAFilterCategoriesCancelBtn()
	{
		return SeleniumUtil.getWebElement(d, "verifyEAFilterCategoriesCancelBtn", "Expense", null);
	}
	
	public WebElement verifyAllCategoriesHeader()
	{
		return SeleniumUtil.getWebElement(d, "verifyAllCategoriesHeader", "Expense", null);
	}
	
	public void deselectingTheOddNoCheckBoxesOfCategories()
	{
		List<WebElement> deselect = verifyEAFilterCategoriesCheckBoxes();
		for(int i=0; i<deselect.size(); i=i+2)
		{
			SeleniumUtil.click(deselect.get(i));
			SeleniumUtil.waitForPageToLoad(2000);
		}
	}
	
	public void selectingTheOddNoOfCheckBoxes()
	{
		SeleniumUtil.click(verifyEAFilterCategories());
        SeleniumUtil.waitForPageToLoad(2000);
		
		deselectingTheOddNoCheckBoxesOfCategories();
		
		SeleniumUtil.click(verifyEAFilterCategoriesDoneBtn());
		SeleniumUtil.waitForPageToLoad(2000);
	}
	
	public List<WebElement> verifyEAAmtsInEBC()
	{
		return SeleniumUtil.getWebElements(d, "verifyEAAmtsInEBC", "Expense", null);
	}
	
	public List<WebElement> verifyTransInEBCOfMLC()
	{
		return SeleniumUtil.getWebElements(d, "verifyTransInEBCOfMLC", "Expense", null);
	}
	
	public WebElement verifyShowMoreOptions()
	{
		return SeleniumUtil.getWebElement(d, "verifyShowMoreOptions", "Expense", null);
	}
	
	public WebElement verifyDeleteTrans()
	{
		return SeleniumUtil.getWebElement(d, "verifyDeleteTrans", "Transaction", null);
	}
	
	public WebElement verifyDeleteTransBtn()
	{
		return SeleniumUtil.getWebElement(d, "verifyDeleteTransBtn", "Transaction", null);
	}
	
	public WebElement verifyNoteTextArea()
	{
		return SeleniumUtil.getWebElement(d, "verifyNoteTextArea", "Expense", null);
	}
	
	public WebElement verifyShowMoreOptionsLink()
	{
		return SeleniumUtil.getWebElement(d, "verifyShowMoreOptionsLink", "Expense", null);
	}
	
	public WebElement editTransSaveBtnInEBC()
	{
		return SeleniumUtil.getWebElement(d, "editTransSaveBtnInEBC", "Expense", null);
	}
	
	public WebElement verifyTransUpdateMsg()
	{
		return SeleniumUtil.getWebElement(d, "verifyTransUpdateMsg", "Expense", null);
	}
	
	public WebElement selectAllCategories()
	{
		return SeleniumUtil.getWebElement(d, "selectAllCategories", "Transaction", null);
	}
	
	public WebElement unCheckAllCategories()
	{
		return SeleniumUtil.getWebElement(d, "unCheckAllCategories", "Transaction", null);
	}
	
	public WebElement selectMerchandiseCategory()
	{
		return SeleniumUtil.getWebElement(d, "selectMerchandiseCategory", "Transaction", null);
	}
	
	public WebElement selectGasolineCategory()
	{
		return SeleniumUtil.getWebElement(d, "selectGasolineCategory", "Transaction", null);
	}
	public WebElement editIncomeCategory()
	{
		return SeleniumUtil.getWebElement(d, "editIncomeCategory", "Transaction", null);
	}
	public WebElement editGasolineCategory()
	{
		return SeleniumUtil.getWebElement(d, "editGasolineCategory", "Transaction", null);
	}
	public WebElement editExpenseCategory()
	{
		return SeleniumUtil.getWebElement(d, "editGasolineCategory", "Transaction", null);
	}
	
	public WebElement closeCategoryFilter()
	{
		return SeleniumUtil.getWebElement(d, "closeCategoryFilter", "Transaction", null);
	}
	
	public List<WebElement> selectingACategoryToSplit()
	{
		return SeleniumUtil.getWebElements(d, "selectingACategoryToSplit", "Transaction", null);
	}
	
	public WebElement verifySplitTrans()
	{
		return SeleniumUtil.getWebElement(d, "verifySplitTrans", "Transaction", null);
	}
	
	public WebElement verifySaveChangesBtn()
	{
		return SeleniumUtil.getWebElement(d, "verifySaveChangesBtn", "Transaction", null);
	}
	
	public WebElement verifySplitUpdatedText()
	{
		return SeleniumUtil.getWebElement(d, "verifySplitUpdatedText", "Transaction", null);
	}
	
	public List<WebElement> verifySplitAmounts()
	{
		return SeleniumUtil.getWebElements(d, "verifySplitAmounts", "Transaction", null);
	}
	
	public boolean splittingTransactions()
	{
		SeleniumUtil.click(selectAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(unCheckAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(selectMerchandiseCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(closeCategoryFilter());
		SeleniumUtil.waitForPageToLoad(2000);
		
		List<WebElement> selectingACategory = selectingACategoryToSplit();
		SeleniumUtil.click(selectingACategory.get(3));//1
		SeleniumUtil.waitForPageToLoad(1000);
		
		SeleniumUtil.click(verifyShowMoreOptionsLink());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifySplitTrans());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifySaveChangesBtn());
		SeleniumUtil.waitForPageToLoad(1000);
		
		boolean splitTextIsDisplayed = verifySplitUpdatedText().isDisplayed();
		if(splitTextIsDisplayed==true)
	    {
	    	Assert.assertTrue(true);
	    }
	    else
	    {
	    	Assert.assertTrue(false);
	    }
	    
	    return splitTextIsDisplayed;
		
	}
	
	public List<WebElement> selectPendingTrans()
	{
		return SeleniumUtil.getWebElements(d, "selectPendingTrans", "Transaction", null);
	}
	
	public List<WebElement> verifyPendingTransDesc()
	{
		return SeleniumUtil.getWebElements(d, "verifyPendingTransDesc", "Transaction", null);
	}
	
	public void verifyPendingTransactionInTrans()
	{
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(selectAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(unCheckAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(selectMerchandiseCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(closeCategoryFilter());
		SeleniumUtil.waitForPageToLoad(2000);
		
		List<WebElement> clickPendingTrans = selectPendingTrans();
		SeleniumUtil.click(clickPendingTrans.get(0));
		
		List<WebElement> pendingTransDesc = verifyPendingTransDesc();
		String actual = pendingTransDesc.get(0).getText().trim();
		logger.info("Pending Transaction Description in Transaction is "+ actual);
		
		String expected = PropsUtil.getDataPropertyValue("ExpectedPendingTrans").trim();
		if(actual.equals(expected))
		{
			Assert.assertTrue(true);
		}
		else
		{
			
		}
		
	}
	
	public void deleteHLCTransForEBCVerification()
	{
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(selectAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(unCheckAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(selectGasolineCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(closeCategoryFilter());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(editGasolineCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifyShowMoreOptions());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifyDeleteTrans());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifyDeleteTransBtn());
		SeleniumUtil.waitForPageToLoad(2000);	
	}
	public void deleteHLCTransForEBCVerificationIncome()
	{
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(selectAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(unCheckAllCategories());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(editIncomeCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(closeCategoryFilter());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(editGasolineCategory());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifyShowMoreOptions());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifyDeleteTrans());
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(verifyDeleteTransBtn());
		SeleniumUtil.waitForPageToLoad(2000);	
	}
	
	public WebElement verifyPostedTrans()
	{
		return SeleniumUtil.getWebElement(d, "verifyPostedTrans", "Expense", null);
	}
	
	public WebElement verifyPendingTransInEBC()
	{
		return SeleniumUtil.getWebElement(d, "verifyPendingTransInEBC", "Expense", null);
	}
	
	public WebElement verifyBackBtnForEBC()
	{
		return SeleniumUtil.getWebElement(d, "verifyBackBtnForEBC", "Expense", null);
	}
	
	public WebElement verifyNoUnCategorizedData()
	{
		return SeleniumUtil.getWebElement(d, "verifyNoUnCategorizedData", "Expense", null);
	}
	
	public WebElement verifyNoUnCategorizedDataText()
	{
		return SeleniumUtil.getWebElement(d, "verifyNoUnCategorizedDataText", "Expense", null);
	}
	
	public WebElement verifyMoreButton()
	{
		return SeleniumUtil.getWebElement(d, "verifyMoreButton", "Expense", null);
	}
	
	public WebElement verifyTrendsLink()
	{
		return SeleniumUtil.getWebElement(d, "verifyTrendsLink", "Expense", null);
	}
	
	public WebElement closeTrendsPopUp()
	{
		return SeleniumUtil.getWebElement(d, "closeTrendsPopUp", "Expense", null);
	}
	
	public List<WebElement> verifyTrendsPopUpAmount()
	{
		return SeleniumUtil.getWebElements(d, "verifyTrendsPopUpAmount", "Expense", null);
	}
	
	public void selectingAllExpAcctsForTrends()
	{
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
	
		SeleniumUtil.click(verifyAccountsDDCheckBox());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Selecting All Expense Accounts...");
		SeleniumUtil.click(verifyAccountsDDCheckBox());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(1000);
		
		SeleniumUtil.click(verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		
		SeleniumUtil.click(verifyMoreButton());
		SeleniumUtil.waitForPageToLoad(2000);  
		SeleniumUtil.click(verifyTrendsLink());
		SeleniumUtil.waitForPageToLoad(3000);
	}
	public void selectingAllExpAcctsForTrendsIncome()
	{
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
	
		SeleniumUtil.click(verifyAccountsDDCheckBox1());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Selecting All Expense Accounts...");
		SeleniumUtil.click(verifyAccountsDDCheckBox1());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(4000);
		
		SeleniumUtil.click(verifyHLCInExpenseByCategoryInc());
		SeleniumUtil.waitForPageToLoad(3000);
		
		SeleniumUtil.click(verifyMoreButton());
		SeleniumUtil.waitForPageToLoad(2000);  
		SeleniumUtil.click(verifyTrendsLink());
		SeleniumUtil.waitForPageToLoad(3000);
	}
	
	public void selectingAnAccountForTrends(int selectAcc)
	{
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
	
		SeleniumUtil.click(verifyAccountsDDCheckBox());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Selecting A Particular Account");
		List<WebElement> listOfAccounts = verifyEAListOfAccounts();
		SeleniumUtil.click(listOfAccounts.get(selectAcc));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(1000);
		
		SeleniumUtil.click(verifyHLCInExpenseByCategory());
		SeleniumUtil.waitForPageToLoad(3000);
		
		SeleniumUtil.click(verifyMoreButton());
		SeleniumUtil.waitForPageToLoad(2000);  
		SeleniumUtil.click(verifyTrendsLink());
		SeleniumUtil.waitForPageToLoad(3000);
	}
	public void selectingAnAccountForTrendsInc(int selectAcc)
	{
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(2000);
	
		SeleniumUtil.click(verifyAccountsDDCheckBox1());
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Selecting A Particular Account");
		List<WebElement> listOfAccounts = verifyEAListOfAccounts();
		SeleniumUtil.click(listOfAccounts.get(selectAcc));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(verifyAccountsDD());
		SeleniumUtil.waitForPageToLoad(1000);
		
		SeleniumUtil.click(verifyHLCInExpenseByCategoryInc());
		SeleniumUtil.waitForPageToLoad(3000);
		
		SeleniumUtil.click(verifyMoreButton());
		SeleniumUtil.waitForPageToLoad(2000);  
		SeleniumUtil.click(verifyTrendsLink());
		SeleniumUtil.waitForPageToLoad(3000);
	}
	
	public WebElement verifyHLCTransInExpense()
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyHLCTransInExpense", "Expense", null);
	}
		
	public WebElement verifyAvgAmtForLastThreeMonths()
	{
		return SeleniumUtil.getWebElement(d, "verifyAvgAmtForLastThreeMonths", "Expense", null);
	}
	
	public List<WebElement> expenseAmountsInLegendSection()
	{
		return SeleniumUtil.getWebElements(d, "expenseAmountsInLegendSection", "Expense", null);
	}
	
	public double roundingOff(double amt)
	{
		Double toBeTruncated = new Double(amt);
		Double truncatedDouble = BigDecimal.valueOf(toBeTruncated).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amt=truncatedDouble;
		
		return amt;
	}
	
	public WebElement avgAmount()
	{
		return SeleniumUtil.getWebElement(d, "IAAvgAmount", "Expense", null);
	}
	public double round(double value, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
public float monthlyChange(int i) 
  {
		List<WebElement> ele = SeleniumUtil.getWebElements(d, "IAMonthlyChange", "Expense", null);
		String str = ele.get(i).getText();
		int pos = str.indexOf(".");
		str = str.substring(1, pos+3);
		str=str.replaceAll(",", "");
		float monthly_change = Float.parseFloat(str);
		return monthly_change;
	}
public Float monthlyAmt(int i)
{
	List<WebElement> ele = SeleniumUtil.getWebElements(d, "IAMonthlyAmt", "Expense", null);
	String str = ele.get(i).getText();
	str = str.substring(1);
	str=str.replaceAll(",", "");
	float monthly_amt = Float.parseFloat(str);
	return monthly_amt;
}
	
public int monthlyChangePercent(int i) 
{	
		List<WebElement> ele = SeleniumUtil.getWebElements(d, "IAMonthlyChange", "Expense", null);
		String str = ele.get(i).getText();
		int pos = str.indexOf(".");
		str = str.substring(pos+4);
		str=str.replaceAll("%", "");
		int monthly_change_percent = Integer.parseInt(str);
		return monthly_change_percent;
	}

public List<WebElement> legends() 
{
	return SeleniumUtil.getWebElements(d, "IALegends", "Expense", null);
}

public WebElement MoreButton()
{
	return SeleniumUtil.getVisibileWebElement(d, "MoreButton", "Expense", null);
}

public WebElement trendMenu() {
	return SeleniumUtil.getVisibileWebElement(d, "IATrendMenu", "Expense", null);
}

public List<WebElement> trendMonths() {
	return SeleniumUtil.getWebElements(d, "IATrendMonths", "Expense", null);
}

public WebElement trendAvgAmt() {
	return SeleniumUtil.getWebElement(d, "IATrendAvgAmt", "Expense", null);
}

public List<WebElement> trendAmounts() {
	return SeleniumUtil.getWebElements(d, "IATrendAmounts", "Expense", null);
}

public WebElement trendCrossIcon() {
	return SeleniumUtil.getWebElement(d, "IATrendCrossIcon", "Expense", null);
}

public WebElement myprofileLink() {
	return SeleniumUtil.getWebElement(d, "IAMyprofileLink", "Expense", null);
}

public List<WebElement> myprofileListpreference() {
	return SeleniumUtil.getWebElements(d, "IAMyprofileListpreference", "Expense", null);
}

public WebElement myprofileListpreferenceCurrencyDropDown() {
	return SeleniumUtil.getWebElement(d, "IAMyprofileListpreferenceCurrencyDropDown", "Expense", null);
}

public List<WebElement> highLevelCategory_EA() {
	return SeleniumUtil.getWebElements(d, "highLevelCategory", "Expense", null);
}

public String verifyUserPreferredCurrency() throws Exception 
   {
	
		myprofileLink().click();
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(myprofileListpreference().get(3));
		String currency = myprofileListpreferenceCurrencyDropDown().getAttribute("value");
		
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad();
		return currency;
		
	}
	
	////////////////////// Account Groups ////////////////////////////
	
	public WebElement createGroupBtn() 
	{
	//	return SeleniumUtil.getVisibileWebElement(d, "createGroupBtnAGS", "AccountGroups", null);
		return SeleniumUtil.getVisibileWebElement(d, "createGroupButtonTwoAGS", "AccountGroups", null);
	}
	
	public WebElement verifyGpDeleted() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "verifyGpDeleted", "AccountGroups", null);
	}

	public WebElement groupNameTxtBox() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "groupNameTxtBoxAGS", "AccountGroups", null);
	}
	
	public WebElement deleteGroupOption() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "deleteGroupOptionAGS", "AccountGroups", null);
	}

	public List<WebElement> getAccountOptionChckBox() 
	{
		String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountGroups", null, "getAccountOptionChckBoxAGS");

		List<WebElement> l = d.findElements(By.xpath(varX));

		return l;
	}
	
	public WebElement createOrUpdateGroup() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "createOrUpdateGroupAGS", "AccountGroups", null);
	}

	public WebElement successMsg() 
	{
		return SeleniumUtil.getVisibileWebElement(d, "successMsgAGS", "AccountGroups", null);
	}
	
	public WebElement deleteGroupButton() 
	{
		return SeleniumUtil.getWebElement(d, "deleteGroupButton", "AccountGroups", null);
		
	}

	public boolean createAccountGroup()
	{
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();

        SeleniumUtil.click(createGroupBtn());		
	    SeleniumUtil.waitForPageToLoad();

        SeleniumUtil.click(groupNameTxtBox());
        groupNameTxtBox().clear();

        groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AccountGroupForEA").trim());
	    SeleniumUtil.waitForPageToLoad(2000);

        getAccountOptionChckBox().get(2).click();
       // getAccountOptionChckBox().get(3).click();
	    getAccountOptionChckBox().get(10).click();

        SeleniumUtil.click(createOrUpdateGroup());
	    SeleniumUtil.waitForPageToLoad(500);
	    boolean status =  successMsg().isDisplayed();
	    if(status==true)
	    {
	    	Assert.assertTrue(true);
	    }
	    else
	    {
	    	Assert.assertTrue(false);
	    }
	    
	    return status;
	}
	
	
	public boolean deleteAccountGp()
	{
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(deleteGroupButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(deleteGroupOption());
		SeleniumUtil.waitForPageToLoad(2000);
		
		boolean status = verifyGpDeleted().isDisplayed(); 
		if(status== true)
		{
			Assert.assertTrue(true);
	    }
	    else
	    {
	    	Assert.assertTrue(false);
	    }
	    
	    return status;
	}
	
	public WebElement addTransactionBtn_mob() {
		return SeleniumUtil.getVisibileWebElement(d, "addTransactionBtn_mob", "Expense", null);
	}
	
	public WebElement doneButton()
	{
		return SeleniumUtil.getWebElement(d, "doneButton", "Expense", null);
	}
	
	public WebElement closeButton()
	{
		return SeleniumUtil.getWebElement(d,"closeButtonForMobile", "Expense", null);
	}
	
}
