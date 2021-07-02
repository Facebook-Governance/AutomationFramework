/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CashFlow;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class LandingPage {

	static Logger logger = LoggerFactory.getLogger(LandingPage.class);
	public WebDriver d = null;
	static String pageName = "CashFlow"; // Page Name
	static String frameName = null;

	public LandingPage(WebDriver d) {
		this.d = d;
	}

	public WebElement xyz() {
		return d.findElement(By.id("acoounts-container")).findElement(By.id("leftSection"))
				.findElement(By.cssSelector(".typeTab"));
	}

	public WebElement statusBar(int i) {
		// return
		// d.findElement(By.xpath(".//*[@id='netCashflowPanel']/div[2]/div[2]/span["+i+"]"));
		String s = SeleniumUtil.getVisibileElementXPath(d, "netCash", pageName, frameName);
		s.replaceAll("VAR_i", Integer.toString(i));
		return d.findElement(By.xpath(s));

	}

	// Locators for Welcome to CashFlow Popup

	/*
	 * private final String popUpBlock = "ftue-container";
	 * 
	 * @FindBy(how = How.ID, using = popUpBlock)
	 */
	public WebElement PopUpBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpBlock", pageName, frameName);
	}

	// private final String welcomeLabel = ".//*[@id='ftue-container']//h2";
	/*
	 * private final String welcomeLabel = "//h2[text()='Welcome to Cash Flow']";
	 * 
	 * @FindBy(how = How.XPATH, using = welcomeLabel)
	 */
	public WebElement WelcomeLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "welcomeLabel", pageName, frameName);
	}

	/*
	 * private final String continueBtn = "ftue-continue";
	 * 
	 * @FindBy(how = How.ID, using = continueBtn)
	 */
	public WebElement ContinueBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "continueBtn", pageName, frameName);
	}

	/*
	 * private final String linkAccLAbel = "//a[@id='link-account-button-persist']";
	 * 
	 * @FindBy(how = How.XPATH, using = linkAccLAbel)
	 */
	public WebElement LinkAccLAbel() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccLAbel", pageName, frameName);
	}

	/*
	 * private final String cashFlowBtn = "//span[text()='GO TO MY CASH FLOW']";
	 * 
	 * @FindBy(how = How.XPATH, using = cashFlowBtn)
	 */
	public WebElement GoToMyCashFlowBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFlowBtn", pageName, frameName);
	}

	// Locators for the Headers in the Page
	/*
	 * private final String cashFlowHeader = "//h2[text()='Cash Flow']";
	 * 
	 * @FindBy(how = How.XPATH, using = cashFlowHeader)
	 */
	public WebElement CashFlowHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFlowHeader", pageName, frameName);
	}

	/*
	 * private final String linkAccBtn = "link-account-button-persist";
	 * 
	 * @FindBy(how = How.ID, using = linkAccBtn)
	 */
	public WebElement LinkAccBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccBtn", pageName, frameName);
	}

	/*
	 * private final String linkAccBtn1 = "cf_linkaccount_button";
	 * 
	 * @FindBy(how = How.ID, using = linkAccBtn1)
	 */
	public WebElement LinkAccBtn1() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccBtn1", pageName, frameName);
	}

	/*
	 * private final String printBlock = "printIcon";
	 * 
	 * @FindBy(how = How.ID, using = printBlock)
	 */
	public WebElement PrintBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "printBlock", pageName, frameName);
	}

	/*
	 * private final String printIcon = ".//*[@id='print-button']/i";
	 * 
	 * @FindBy(how = How.XPATH, using = printIcon)
	 */
	public WebElement PrintIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "printIcon", pageName, frameName);
	}

	/*
	 * private final String printText = ".//*[@id='print-button']/span";
	 * 
	 * @FindBy(how = How.XPATH, using = printText)
	 */
	public WebElement PrintText() {
		return SeleniumUtil.getVisibileWebElement(d, "printText", pageName, frameName);
	}

	/*
	 * private final String moretxt = ".//*[@id='more-button']/span";
	 * 
	 * @FindBy(how = How.XPATH, using = moretxt)
	 */
	public WebElement moreText() {
		return SeleniumUtil.getVisibileWebElement(d, "moretxt", pageName, frameName);
	}

	/*
	 * private final String moreicon = ".//*[@id='more-button']//i[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = moreicon)
	 */
	public WebElement moreIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "moreicon", pageName, frameName);
	}

	/*
	 * private final String infoIcon = "//a[@id='menu-feature-tour-button']/i";
	 * 
	 * @FindBy(how = How.XPATH, using = infoIcon)
	 */
	public WebElement InfoIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "infoIcon", pageName, frameName);
	}

	// Locators for the drop down

	/*
	 * private final String allCashFlowAcc
	 * ="//a[@dropdown-id='0accountGroupsDropdown']//span[1]";
	 * 
	 * @FindBy(how = How.XPATH, using = allCashFlowAcc)
	 */
	public WebElement AllCashFlowAcc() {
		return SeleniumUtil.getVisibileWebElement(d, "allCashFlowAcc", pageName, frameName);
	}

	/*
	 * private final String allCashFlowAccDropDownIcon =
	 * "//a[@dropdown-id='0accountGroupsDropdown']//span[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = allCashFlowAccDropDownIcon)
	 */
	public WebElement AllCashFlowAccDropDownIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "allCashFlowAccDropDownIcon", pageName, frameName);
	}

	/*
	 * private final String timeFilter = "durationFilterCont";
	 * 
	 * @FindBy(how = How.ID, using = timeFilter)
	 */
	public WebElement TimeFilter() {
		return SeleniumUtil.getVisibileWebElement(d, "timeFilter", pageName, frameName);
	}

	/*
	 * private final String timeFilterDropdown =
	 * "//a[@dropdown-id='0durationDropdown']";
	 * 
	 * @FindBy(how = How.XPATH, using = timeFilterDropdown)
	 */
	public WebElement TimeFilterDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "timeFilterDropdown", pageName, frameName);
	}

	/*
	 * private final String allAccDropDown = ".//*[@id='0']/div/span[1]";
	 * 
	 * @FindBy(how = How.XPATH, using = allAccDropDown)
	 */
	public WebElement AllAccDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "allAccDropDown", pageName, frameName);
	}

	/*
	 * private final String groupName = ".//*[@id='0']/div/span[1]";
	 * 
	 * @FindBy(how = How.XPATH, using = groupName)
	 */
	public WebElement GroupName() {
		return SeleniumUtil.getVisibileWebElement(d, "groupName", pageName, frameName);
	}

	/*
	 * private final String tickMark = ".//*[@id='l6m']/div/span[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = tickMark)
	 */
	public WebElement TickMark() {
		return SeleniumUtil.getVisibileWebElement(d, "tickMark", pageName, frameName);

	}

	/*
	 * private final String closePopUp =
	 * ".//*[@id='cashflowCustomDatesFilterPopupRegion']/div/a";
	 * 
	 * @FindBy(how = How.XPATH, using = closePopUp)
	 */
	public WebElement ClosePopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "closePopUp", pageName, frameName);
	}

	/*
	 * private final String startDate = "customStartDate";
	 * 
	 * @FindBy(how = How.ID, using = startDate)
	 */
	public WebElement StartDate() {
		return SeleniumUtil.getVisibileWebElement(d, "startDate", pageName, frameName);
	}

	/*
	 * rivate final String endDate = "customEndDate";
	 * 
	 * @FindBy(how = How.ID, using = endDate)
	 */
	public WebElement EndDate() {
		return SeleniumUtil.getVisibileWebElement(d, "endDate", pageName, frameName);
	}

	/*
	 * private final String calender =
	 * "//div[contains(@class,'calendar popup active')]";
	 * 
	 * @FindBy(how = How.XPATH, using = calender)
	 */
	public WebElement Calender = SeleniumUtil.getVisibileWebElement(d, "calender", pageName, frameName);

	public WebElement Calender() {
		// return d.findElement(By.xpath(".//*[@id='customEndDate']"));
		return SeleniumUtil.getVisibileWebElement(d, "cDate", pageName, frameName);
	}

	public WebElement Dropdown1(int n) {
		// return
		// d.findElements(By.cssSelector(".ellipsis.name.text.text-css-level3")).get(n);
		return SeleniumUtil.getVisibileWebElement(d, "ellipseNameText", pageName, frameName);
	}

	public List<WebElement> Dropdown() {
		// return d.findElements(By.cssSelector(".ellipsis.name.text.text-css-level2"));
		return SeleniumUtil.getWebElements(d, "nametext", pageName, frameName);
	}

	// Locatores for PAnel

	/*
	 * private final String currentNetCashFlowAmt =
	 * ".//*[@id='netCashflowPanel']/div[1]/div[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = currentNetCashFlowAmt)
	 */
	public WebElement CurrentNetCashFlowAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "currentNetCashFlowAmt", pageName, frameName);
	}

	/*
	 * private final String currentNetCashFlowAmtDec =
	 * ".//*[@id='cf-table-container']/div/table/tbody/tr[1]/td[5]/span";
	 * 
	 * @FindBy(how = How.XPATH, using = currentNetCashFlowAmtDec)
	 */
	public WebElement CurrentNetCashFlowAmtDec() {
		return SeleniumUtil.getVisibileWebElement(d, "currentNetCashFlowAmtDec", pageName, frameName);
	}

	// Locatores for Fastlink Floater
	/*
	 * private final String closeFLPopUp =
	 * ".//*[@id='modal-popup-container']/div/div[2]/div[2]/div[1]/div[4]/i[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = closeFLPopUp)
	 */
	public WebElement CloseFLPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "closeFLPopUp", pageName, frameName);
	}

	// LOCATORS for charts
	/*
	 * private final String chartBlock = "chartPanel";
	 * 
	 * @FindBy(how = How.ID, using = chartBlock)
	 */
	public WebElement ChartBlock() {
		return SeleniumUtil.getVisibileWebElement(d, "chartBlock", pageName, frameName);
	}

	// LOCATORS FOR COACH MARKS

	/*
	 * private final String coachMark = ".//*[@id='coachmark_help']/span";
	 * 
	 * @FindBy(how = How.XPATH, using = coachMark)
	 */
	public WebElement CoachMark() {
		return SeleniumUtil.getVisibileWebElement(d, "coachMark", pageName, frameName);
	}

	/*
	 * private final String coachMark1 = "//a[@id='menu-feature-tour-button']/i";
	 * 
	 * @FindBy(how = How.XPATH, using = coachMark1)
	 */
	public WebElement CoachMark1() {
		return SeleniumUtil.getVisibileWebElement(d, "coachMark1", pageName, frameName);
	}

	/*
	 * private final String coachMarkPopup1 = "html/body/div[8]";
	 * 
	 * @FindBy(how = How.XPATH, using = coachMarkPopup1)
	 */
	public WebElement CoachMarkPopup1() {
		return SeleniumUtil.getVisibileWebElement(d, "coachMarkPopup1", pageName, frameName);
	}

	/*
	 * private final String coachMarkPopup2 = "html/body/div[10]/div/h3";
	 * 
	 * @FindBy(how = How.XPATH, using = coachMarkPopup2)
	 */
	public WebElement CoachMarkPopupHeading2() {
		return SeleniumUtil.getVisibileWebElement(d, "coachMarkPopup2", pageName, frameName);
	}

	// private final String coachMarkPopup3 = "html/body/div[11]/div/h3";
	// @FindBy(how = How.XPATH, using = coachMarkPopup3)
	public WebElement CoachMarkPopupHeading3() {
		return SeleniumUtil.getVisibileWebElement(d, "coachMarkPopup3", pageName, frameName);
	}

	/*
	 * private final String nextBtn
	 * ="//div[@class='joyride-tip-guide'][1]//footer/div[2]//a[text()='Next']";
	 * 
	 * @FindBy(how = How.XPATH, using = nextBtn)
	 */
	public WebElement NextBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "nextBtn", pageName, frameName);
	}

	/*
	 * private final String nextBtn2 =
	 * "//div[@class='joyride-tip-guide'][2]//div[@class='joyride-content-wrapper']//a[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = nextBtn2)
	 */
	public WebElement NextBtn2() {
		return SeleniumUtil.getVisibileWebElement(d, "nextBtn2_LP", pageName, frameName);
	}

	/*
	 * private final String bulletIndicator = "joyride-bullets-0";
	 * 
	 * @FindBy(how = How.ID, using = bulletIndicator)
	 */
	public WebElement BulletIndicator() {
		return SeleniumUtil.getVisibileWebElement(d, "bulletIndicator_LP", pageName, frameName);
	}

	// Locators for Transaction PAge
	/*
	 * private final String transactionTitle = "//h2[text()='Transactions']";
	 * 
	 * @FindBy(how = How.XPATH, using = transactionTitle)
	 */
	public WebElement TransactionTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionTitle_LP", pageName, frameName);
	}

	/*
	 * private final String transcategory =
	 * "//dl[@id='txnByDate']/dd/a/div[1]//div[1]/div[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = transcategory)
	 */
	public WebElement Transcategory() {
		return SeleniumUtil.getVisibileWebElement(d, "transcategory_LP", pageName, frameName);
	}

	/*
	 * rivate final String transcategory1 =
	 * "//dl[@id='txnByDate']/dd[1]/a/div[1]/div[1]//div[2]";
	 * 
	 * @FindBy(how = How.XPATH, using = transcategory1)
	 */
	public WebElement Transcategory1() {
		return SeleniumUtil.getVisibileWebElement(d, "transcategory1_LP", pageName, frameName);
	}

	/*
	 * private final String backBtn =
	 * "//div[@id='transactionHeader']//span[@id='back-to-text']";
	 * 
	 * @FindBy(how = How.XPATH, using = backBtn)
	 */
	public WebElement BackBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "backBtn_LP", pageName, frameName);
	}

	// AKASH
	// durationFilter
	// select option from time filter drop down
	public void selectValueFromTimeFilter(int i) {
		// click time filter to display all its value
		d.findElement(By.xpath("//a[contains(@class,'duration-dropdown')]")).click();
		SeleniumUtil.waitForPageToLoad(2500);

		// click the value based upon the passed option number
		/*
		 * String locator=
		 * "(//div[contains(@class,'timeFilterDropdownCtr')]//li[@class='li-level1'])["+
		 * i+"]/div/span"; d.findElement(By.xpath(locator)).click();
		 */
		String s = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "timeFilter_LP");
		// SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "timeFilter_LP",
		// pageName, frameName));
		s.replaceAll("VAR_i", Integer.toString(i));
		d.findElement(By.xpath(s));

	}

	public void clickContinue() {
		// d.findElement(By.id("cf-ftue-continue")).click();
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "ftueContinue_LP", pageName, frameName));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public void clickGoToMyCashFlow() {
		// d.findElement(By.id("cf-ftue-goToMyCashFlow")).click();
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "gotoMyCashFlow_LP", pageName, frameName));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	// select option from accounts filter drop down
	public void selectValueFromAccountsFilter(int i) {
		// click time filter to display all its value
		// d.findElement(By.id("accountsFilter")).click();
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "accFilter_LP", pageName, frameName));

		// click the value based upon the passed option number
		// String locator="//div[@id='accountsFilterContent']//li["+i+"]//span";
		// d.findElement(By.xpath(locator)).click();
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "accFilteContent_LP", pageName, frameName));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	// verify options inside time filter

	public void verifyTimeFilterValues(String[] expectedValues) {
		// List<WebElement>
		// l=d.findElements(By.xpath("//div[contains(@class,'timeFilterDropdownCtr')]/div//li//span[1]"));
		List<WebElement> l = SeleniumUtil.getWebElements(d, "timeFilterDrop", pageName, frameName);

		String value[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			value[i] = l.get(i).getAttribute("title");
		}

		// Assert.assertTrue(Arrays.equals(value, expectedValues));
	}

	// Mobile Locators

	/*
	 * private final String Uname = "etUserName";
	 * 
	 * @FindBy(how = How.ID, using = Uname)
	 */
	public WebElement uName() {
		return SeleniumUtil.getVisibileWebElement(d, "Uname_LP", pageName, frameName);
	}

	/*
	 * private final String pwd = "etPassword";
	 * 
	 * @FindBy(how = How.ID, using = pwd)
	 */
	public WebElement pWd() {
		return SeleniumUtil.getVisibileWebElement(d, "pwd_LP", pageName, frameName);
	}

	/*
	 * private final String = "btnLogin";
	 * 
	 * @FindBy(how = How.ID, using = login)
	 */
	public WebElement loginBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "login_LP", pageName, frameName);
	}

	// link account button
	/*
	 * private final String LinkActBtn = "suggestedAcc-linkAccount";
	 * 
	 * @FindBy(how = How.ID, using = LinkActBtn)
	 */
	public WebElement linkActBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkActBtn_LP", pageName, frameName);
	}

	/*
	 * private final String FiIcon = "up";
	 * 
	 * @FindBy(how = How.ID, using = FiIcon)
	 */
	public WebElement fiIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "FiIcon_LP", pageName, frameName);
	}

	/*
	 * private final String accountType =
	 * "//*[@class='android.widget.TextView' and @text='Yodlee']";
	 * 
	 * @FindBy(how = How.XPATH, using = accountType)
	 */
	public WebElement accountTypeIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "accountType_LP", pageName, frameName);

	}

	/*
	 * private final String viewByFI = "//a[contains(text(),'INSTITUTION')]";
	 * 
	 * @FindBy(how = How.XPATH, using = viewByFI)
	 */
	public WebElement viewByFITab() {
		return SeleniumUtil.getVisibileWebElement(d, "viewByFI_LP", pageName, frameName);
	}

	public void typeCustomStartDate(String date) {
		// d.findElement(By.xpath("//div[contains(@id,'StartDate')]")).click();
		// d.findElement(By.xpath("//div[contains(@id,'StartDate')]")).sendKeys(date);
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "start_LP", pageName, frameName));
		SeleniumUtil.getVisibileWebElement(d, "start_LP", pageName, frameName).sendKeys(date);
	}

	public void typeCustomEndDate(String date) {
		/*
		 * d.findElement(By.xpath("//div[contains(@id,'EndDate')]")).click();
		 * d.findElement(By.xpath("//div[contains(@id,'EndDate')]")).sendKeys(date);
		 */
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "end_LP", pageName, frameName));
		SeleniumUtil.getVisibileWebElement(d, "end_LP", pageName, frameName).sendKeys(date);
	}

	public void clearCustomEndDate() {
		// d.findElement(By.className("end-date")).clear();
		SeleniumUtil.getVisibileWebElement(d, "endDtae_LP", pageName, frameName).clear();
	}

	public void clearCustomStartDate() {
		// d.findElement(By.className("start-date")).clear();
		SeleniumUtil.getVisibileWebElement(d, "startDate_LP", pageName, frameName).clear();
	}

	public void clickUpdateButton() {
		// d.findElement(By.xpath("//a[contains(text(),'Update')]")).click();
		SeleniumUtil.getVisibileWebElement(d, "endDtae_LP", pageName, frameName).click();
	}

	public void verifyFromInvalidDateError() {

		/*
		 * String
		 * errorText=d.findElement(By.xpath("//span[@aria-label='Invalid From Date']")).
		 * getText();
		 */
		String errorText = SeleniumUtil.getVisibileWebElement(d, "invalidFrom_LP", pageName, frameName).getText();
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(errorText.equals("Invalid Date"));
	}

	public void verifyToInvalidDateError() {
		// String errorText=d.findElement(By.xpath("//span[@aria-label='Invalid To
		// Date']")).getText();
		String erroText = SeleniumUtil.getVisibileWebElement(d, "invalidTo_LP", pageName, frameName).getText();
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(erroText.equals("Invalid Date"));
	}

}
