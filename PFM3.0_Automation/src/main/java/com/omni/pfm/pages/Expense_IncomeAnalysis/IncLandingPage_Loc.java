/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Lavanya S
*/



package com.omni.pfm.pages.Expense_IncomeAnalysis;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class IncLandingPage_Loc {

	static Logger logger = LoggerFactory.getLogger(IncLandingPage_Loc.class);
	public static WebDriver d = null;
	static WebElement we;

	public IncLandingPage_Loc(WebDriver d) {
		IncLandingPage_Loc.d = d;
	}

	public WebElement IncomeAnalysisText() {
		return SeleniumUtil.getVisibileWebElement(d, "IncomeAnalysisText", "Expense", null);
	}

	public WebElement DropDownIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "IADropDownIcon", "Expense", null);
	}

	public WebElement incomeanalysistext() {
		return SeleniumUtil.getVisibileWebElement(d, "IAincomeanalysistext", "Expense", null);
	}

	public WebElement IncomeAnalysisHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "IncomeAnalysisHeader", "Expense", null);
	}

	public WebElement goToAnalysisbutton() {
		return SeleniumUtil.getVisibileWebElement(d, "goToAnalysisbutton", "Expense", null);
	}

	public WebElement infoicon() {
		return SeleniumUtil.getVisibileWebElement(d, "infoicon", "Expense", null);
	}

	public WebElement drilldowncattext() {
		return SeleniumUtil.getVisibileWebElement(d, "drilldowncattext", "Expense", null);
	}

	public WebElement next1button() {
		return SeleniumUtil.getVisibileWebElement(d, "next1button", "Expense", null);
	}

	public WebElement averagelinetext() {
		return SeleniumUtil.getVisibileWebElement(d, "averagelinetext", "Expense", null);
	}

	public WebElement next2button() {
		return SeleniumUtil.getVisibileWebElement(d, "next2button", "Expense", null);
	}

	public WebElement next3button() {
		return SeleniumUtil.getVisibileWebElement(d, "next3button", "Expense", null);
	}

	public WebElement back1button() {
		return SeleniumUtil.getVisibileWebElement(d, "back1button", "Expense", null);
	}

	public WebElement backlastbutton() {
		return SeleniumUtil.getVisibileWebElement(d, "backlastbutton", "Expense", null);
	}

	public WebElement gotitbutton() {
		return SeleniumUtil.getVisibileWebElement(d, "gotitbutton", "Expense", null);
	}

	public WebElement linkallaccounttext() {
		return SeleniumUtil.getVisibileWebElement(d, "linkallaccounttext", "Expense", null);
	}

	public WebElement averagelinetext2() {
		return SeleniumUtil.getVisibileWebElement(d, "averagelinetext2", "Expense", null);
	}

	public WebElement averagelinetext1() {
		return SeleniumUtil.getVisibileWebElement(d, "averagelinetext1", "Expense", null);
	}

	public WebElement welcomeAnalysismessage() {
		return SeleniumUtil.getVisibileWebElement(d, "welcomeAnalysismessage", "Expense", null);
	}

	public WebElement linkaccountbutton() {
		return SeleniumUtil.getVisibileWebElement(d, "linkaccountbutton", "Expense", null);
	}

	public WebElement continuebtoon() {
		return SeleniumUtil.getVisibileWebElement(d, "continuebtoon", "Expense", null);
	}
	
	public WebElement goToAnalysisBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "gotoAnalysisBtn", "Expense", null);
	}
	
	public WebElement linkSpendingAcctHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "EAlinkAccountWelcomeCM", "Expense", null);
	}

	public WebElement incomeanalysisheadertext() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeanalysisheadertext", "Expense", null);
	}

	public WebElement monthbarlink() {
		return SeleniumUtil.getVisibileWebElement(d, "monthbarlink", "Expense", null);
	}
	
	public WebElement incomeanalysiscattext() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeanalysiscattext", "Expense", null);
	}

	public WebElement MoreButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MoreButton", "Expense", null);
	}

	public WebElement IAprintbutton() {
		return SeleniumUtil.getVisibileWebElement(d, "IAprintbutton", "Expense", null);
	}

	public WebElement allincomeaccouttext() {
		return SeleniumUtil.getVisibileWebElement(d, "allincomeaccouttext", "Expense", null);
	}

	public WebElement Addtransactionlink() {
		return SeleniumUtil.getVisibileWebElement(d, "Addtransactionlink", "Expense", null);
	}
	
	public WebElement backToIncomeLink() {
		return SeleniumUtil.getVisibileWebElement(d, "backToIncomeLink", "Expense", null);
	}
	
	public WebElement selectmothdropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "selectmothdropdown", "Expense", null);
	}

	public WebElement selectThisMonthOption() {
		return SeleniumUtil.getVisibileWebElement(d, "selectThisMonthOption", "Expense", null);
	}

	public WebElement amounttext() {
		return SeleniumUtil.getVisibileWebElement(d, "amounttext", "Expense", null);
	}

	public WebElement addlinkaccountlink() {
		return SeleniumUtil.getVisibileWebElement(d, "addlinkaccountlink", "Expense", null);
	}
	
	public WebElement allaccountdropdown1() {
		return SeleniumUtil.getVisibileWebElement(d, "allaccountdropdown1", "Expense", null);
	}
	
	public WebElement accountSettinglink() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettinglink", "Expense", null);
	}

	public WebElement deleteaccountLink() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteaccountLink", "Expense", null);
	}

	public WebElement deleteAcceptancecheckbox1() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteAcceptancecheckbox1", "Expense", null);
	}

	public WebElement permanentdeletebutton() {
		return SeleniumUtil.getVisibileWebElement(d, "permanentdeletebutton", "Expense", null);
	}

	public List<WebElement> closebutton() {
		return SeleniumUtil.getWebElements(d, "IAclosebutton", "Expense", null);
	}

	public List<WebElement> nextbutton() {
		return SeleniumUtil.getWebElements(d, "IAnextbutton", "Expense", null);
	}
	
	public List<WebElement> allmonths() {
		List<WebElement> months = new ArrayList<WebElement>();
		
		for (int i = 1; i <= 6; i++) {
			months.add(d.findElement(By.xpath(
					"//ul[@class='legends-category boxShadow y-tabular-container y-border-radius y-border-white-background']/li["
							+ i + "]/a/span[1]")));
		}
		return months;
	}

	public WebElement getEllipsificationMark(int outerContainerNum, int innerContainerNum, int AccountRow) {

		String inLinePopUp = "//section[@id='fiAndAccountTypesView']//div[contains(@class,'accounts-financial-container inner')]["
				+ outerContainerNum + "]//div[@class='accounts-container'][" + innerContainerNum
				+ "]//div[@class='accounts-row'][" + AccountRow + "]//a[contains(@title,'More options')]";
		return d.findElement(By.xpath(inLinePopUp));

	}

	public WebElement backButon(int coachMark) {
		// String
		// backButton="//div[@class='joyride-tip-guide']["+coachMark+"]//div[contains(@class,'joyride-buttons-wrapper')]/a[1]";
		return d.findElements(By.xpath("//a[contains(@class,'small button joyride-prev-tip')]")).get(coachMark);
	}

	public List<WebElement> clickCMNextButton() {
		return SeleniumUtil.getWebElements(d, "IAclickCMNextButton", "Expense", null);
	}

	public WebElement IAfeatureTourButton() {
		return SeleniumUtil.getVisibileWebElement(d, "IAfeatureTourButton", "Expense", null);
	}

	public WebElement drillDownByCatHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "drillDownByCatHeading", "Expense", null);
	}

	public WebElement averageLineHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "IAaverageLineHeading", "Expense", null);
	}

	public WebElement incomeAnalysisFTCMHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "IAincomeAnalysisFTCMHeading", "Expense", null);
	}

	public WebElement linkAllYourAccountFTCMHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "IAlinkAllYourAccountFTCMHeading", "Expense", null);
	}
	
	/*
	 * Added by Archana
	 */

	public WebElement linkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "linkaccount", "Expense", null);
	}
	
	public WebElement incomeByCategoryText() {
		return SeleniumUtil.getVisibileWebElement(d, "IncomeText", "Expense", null);
	}
	
	public WebElement Addtransactionlink_Mob() {
		return SeleniumUtil.getVisibileWebElement(d, "Addtransactionlink_Mob", "Expense", null);
	}	

	public WebElement backToIncomeLink_Mob() {
		return SeleniumUtil.getVisibileWebElement(d, "backToIncomeLink_Mob", "Expense", null);
	}
	
	public WebElement allAccountsDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsDropdown", "Expense", null);
	}	
	
	public WebElement allAccountsFilter() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsFilter", "Expense", null);
	}
	
	public WebElement allIncomeAcctsHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "allIncomeAcctsHeading", "Expense", null);
	}
	
	public WebElement allAccountsDateFilterDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsDateFilterDropdown", "Expense", null);
	}
	
	public WebElement allAccountsFilterCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsFilterCheckbox", "Expense", null);
	}
	
	public WebElement allAccountsDoneBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsDoneBtn", "Expense", null);
	}
	
	public WebElement allAccountsCancelBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsCancelBtn", "Expense", null);
	}
		
	public WebElement allAccountsThisMonth() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsThisMonth", "Expense", null);
	}
	
	public WebElement allAccountsMonthSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccountsThisMonthSelected", "Expense", null);
	}
	
	public WebElement allMonthsView() {
		return SeleniumUtil.getVisibileWebElement(d, "allMonths", "Expense", null);
	}
	
	public List<WebElement> allMonthsList() {
		List<WebElement> allMonths = new ArrayList<WebElement>();
				
		SeleniumUtil.scrollElementIntoView(d, allAccountsMonthSelected(), true);		
		
		for (int i = 0; i < 8; i++) {
			allMonths.add(d.findElement(By.xpath(
					"//*[@id='li-"+i+"-h']/span[1]")));
		}
		return allMonths;
	}

	public List<WebElement> allmonths_Mob() {
		List<WebElement> months = new ArrayList<WebElement>();
		
		List<WebElement> mon = d.findElements(By.xpath(
				"//ul[@class='legends-category boxShadow y-tabular-container y-border-radius y-border-white-background']/li"));
		logger.info("No. of months displayed : " + mon.size());
		
		for (int i = 1; i <= mon.size(); i++) {
			months.add(d.findElement(By.xpath(
					"//ul[@class='legends-category boxShadow y-tabular-container y-border-radius y-border-white-background']/li["
							+ i + "]/a/span[1]")));
		}
		return months;
	}
	
	public WebElement closeMonthFilter() {
		return SeleniumUtil.getVisibileWebElement(d, "closeMonthFilter", "Expense", null);
	}

	public List<WebElement> allaccount() {
		return SeleniumUtil.getWebElements(d, "IAallaccount", "Expense", null);
	}
	public List<WebElement> settingsIcon()
	{
		return SeleniumUtil.getWebElements(d, "settingsIcon", "Expense", null);
	}
	public WebElement deleteAcnt()
	{
		return SeleniumUtil.getWebElement(d, "deleteAcnt", "Expense", null);
	}
	public WebElement deleteconfirmcheck()
	{
		return SeleniumUtil.getWebElement(d, "deleteconfirmcheck", "Expense", null);
	}
	public WebElement deleteBtn()
	{
		return SeleniumUtil.getWebElement(d, "deleteBtn", "Expense", null);
	}
	public WebElement allIncomeAccount()
	{
		return SeleniumUtil.getWebElement(d, "allIncomeAccount", "Expense", null);
	}
	public WebElement accountDeleted()
	{
		return SeleniumUtil.getWebElement(d, "accountDeleted", "Expense", null);
	}
	public WebElement closeAcnt()
	{
		return SeleniumUtil.getWebElement(d, "closeAcnt", "Expense", null);
	}
	public WebElement confirmCloseAccnt()
	{
		return SeleniumUtil.getWebElement(d, "confirmCloseAccnt", "Expense", null);
	}
	public WebElement accountClosed()
	{
		return SeleniumUtil.getWebElement(d, "accountClosed", "Expense", null);
	}
	public WebElement incomeHeader()
	{
		return SeleniumUtil.getWebElement(d, "incomeHeader", "Expense", null);
	}
	public WebElement allIncomeChkBox()
	{
		return SeleniumUtil.getWebElement(d, "allIncomeChkBox", "Expense", null);
	}
	public WebElement brokerageAccount()
	{
		return SeleniumUtil.getWebElement(d, "brokerageAccount", "Expense", null);
	}
	public WebElement brokerageChkBox()
	{
		return SeleniumUtil.getWebElement(d, "brokerageChkBox", "Expense", null);
	}
	public List<WebElement> getGroups() {
		
		return SeleniumUtil.getWebElements(d, "groupsInDD", "Expense", null);

	}
	public WebElement donutChart()
	{
		return SeleniumUtil.getWebElement(d, "donutChart", "Expense", null);
	}
	public WebElement noDataContainer()
	{
		return SeleniumUtil.getWebElement(d, "noDataContainer", "Expense", null);
	}
	public WebElement fromDate()
	{
		return SeleniumUtil.getWebElement(d, "form", "Expense", null);
	}
	public WebElement toDate()
	{
		return SeleniumUtil.getWebElement(d, "to", "Expense", null);
	}
	public WebElement updtBtn()
	{
		return SeleniumUtil.getWebElement(d, "updt", "Expense", null);
	}
	public WebElement invalidDateMsg()
	{
		return SeleniumUtil.getWebElement(d, "invalidDateMsg", "Expense", null);
	}
	public WebElement amountOnChart()
	{
		return SeleniumUtil.getWebElement(d, "amountOnChart", "Expense", null);
	}
	public WebElement amountOnCurrentMonth()
	{
		return SeleniumUtil.getWebElement(d, "amountOnCurrentMonth", "Expense", null);
	}
	
	/*
	 * Added by Ashwathi
	 */
	public List<WebElement> legends() {
		return SeleniumUtil.getWebElements(d, "IALegends", "Expense", null);
	}
	
	public Float monthlyAmt(int i) {
		List<WebElement> ele = SeleniumUtil.getWebElements(d, "IAMonthlyAmt", "Expense", null);
		String str = ele.get(i).getText();
		str = str.substring(1);
		str=str.replaceAll(",", "");
		float monthly_amt = Float.parseFloat(str);
		return monthly_amt;
	}
	
	public float monthlyChange(int i) {
		
		List<WebElement> ele = SeleniumUtil.getWebElements(d, "IAMonthlyChange", "Expense", null);
		String str = ele.get(i).getText();
		int pos = str.indexOf(".");
		str = str.substring(1, pos+3);
		str=str.replaceAll(",", "");
		float monthly_change = Float.parseFloat(str);
		return monthly_change;
	}
	
public int monthlyChangePercent(int i) {
		
		List<WebElement> ele = SeleniumUtil.getWebElements(d, "IAMonthlyChange", "Expense", null);
		String str = ele.get(i).getText();
		int pos = str.indexOf(".");
		str = str.substring(pos+4);
		str=str.replaceAll("%", "");
		int monthly_change_percent = Integer.parseInt(str);
		return monthly_change_percent;
	}
	
	public WebElement avgAmount() {
		return SeleniumUtil.getWebElement(d, "IAAvgAmount", "Expense", null);
	}
	
	public List<WebElement> highLevelCategory() {
		return SeleniumUtil.getWebElements(d, "highLevelCategory", "Expense", null);
	}
	
	public List<WebElement> txnList() {
		return SeleniumUtil.getWebElements(d, "IATxnList", "Expense", null);
	}
	
	public WebElement txnSaveBtn() {
		return SeleniumUtil.getWebElement(d, "IATxnSaveBtn", "Expense", null);
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
	public WebElement groupIA() {
		return SeleniumUtil.getWebElement(d, "groupIA", "Expense", null);
	}
	public void selectIA(){
		   SeleniumUtil.waitForPageToLoad();
		   SeleniumUtil.click(DropDownIcon());
		   SeleniumUtil.click(IncomeAnalysisText());
		   SeleniumUtil.waitForPageToLoad();
	}
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public String verifyUserPreferredCurrency() throws Exception {

	myprofileLink().click();
	SeleniumUtil.waitForPageToLoad();

	SeleniumUtil.click(myprofileListpreference().get(3));
	String currency = myprofileListpreferenceCurrencyDropDown().getAttribute("value");

	PageParser.forceNavigate("Expense", d);
	SeleniumUtil.waitForPageToLoad(5000);

	SeleniumUtil.click(DropDownIcon());
	SeleniumUtil.click(IncomeAnalysisText());
	SeleniumUtil.waitForPageToLoad(5000);

	return currency;

	}
	
}
