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
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class CashFlow_LandingPage_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(CashFlow_LandingPage_Loc.class);
	public WebDriver d = null;
	String pageName = null, frameName = null;
	LoginPage_SAML3 SAML;

	public CashFlow_LandingPage_Loc(WebDriver d) {
		this.d = d;
		pageName = "CashFlow";
		SAML = new LoginPage_SAML3();
	}

	public WebElement statusBar(int i) {
		String abC = getVisibileElementXPath(d, "CashFlow", null, "statusBar_CLP");
		abC.replaceAll("var1", String.valueOf(i).trim());
		return d.findElement(By.xpath(abC));
	}

	public WebElement PopUpBlock() {
		return getVisibileWebElement(d, "PopUpBlock_CLP", "CashFlow", null);
	}

	public WebElement NoDataMsg() {
		return getVisibileWebElement(d, "NoDataMsg_CLP", "CashFlow", null);
	}

	public WebElement ContinueBtn() {
		return getVisibileWebElement(d, "ContinueBtn_CLP", "CashFlow", null);
	}

	public WebElement GoToMyCashFlowBtn() {
		return getVisibileWebElement(d, "GoToMyCashFlowBtn_CLP", "CashFlow", null);
	}

	public WebElement CashFlowHeader() {
		return getWebElement(d, "CashFlowHeader_CLP", "CashFlow", null);
	}

	public WebElement LinkAccBtn() {
		return getVisibileWebElement(d, "LinkAccBtn_CLP", "CashFlow", null);
	}

	public WebElement PrintIcon() {
		return getVisibileWebElement(d, "PrintIcon_CLP", "CashFlow", null);
	}

	public WebElement PrintText() {
		return getVisibileWebElement(d, "PrintText_CLP", "CashFlow", null);
	}

	public WebElement moreIcon() {
		return getVisibileWebElement(d, "moreIcon_CLP", "CashFlow", null);
	}

	public WebElement AllCashFlowAcc() {
		return getVisibileWebElement(d, "AllCashFlowAcc_CLP", "CashFlow", null);
	}

	public WebElement AllCashFlowAccDropDownIcon() {
		return getWebElement(d, "AllCashFlowAccDropDownIcon_CLP", "CashFlow", null);
	}

	public WebElement TimeFilterDropdown() {
		return getWebElement(d, "TimeFilterDropdown_CLP", "CashFlow", null);
	}

	public WebElement TickMark() {
		return getVisibileWebElement(d, "TickMark_CLP", "CashFlow", null);
	}

	public WebElement TickMark_mob() {
		return getVisibileWebElement(d, "TickMark_CLP_mob", "CashFlow", null);
	}

	public List<WebElement> groups() {
		return getWebElements(d, "GroupList", "CashFlow", null);
	}

	public WebElement CurrentNetCashFlowAmt() {
		return getVisibileWebElement(d, "CurrentNetCashFlowAmt_CLP", "CashFlow", null);
	}

	public WebElement ChartBlock() {
		return getVisibileWebElement(d, "ChartBlock_CLP", "CashFlow", null);
	}

	public WebElement TransactionTitle() {
		return getVisibileWebElement(d, "TransactionTitle_CLP", "CashFlow", null);
	}

	public WebElement BackBtn() {
		return getVisibileWebElement(d, "BackBtn_CLP", "CashFlow", null);
	}

	public void selectValueFromTimeFilter(int i) {
		openDurationDropdown();
		waitForPageToLoad(5000);
		String locator = "//div[contains(@class,'timeFilterDropdownCtr')]//ul[1]/li//h3";
		click(d.findElements(By.xpath(locator)).get(i - 1));
	}

	public void verifyTimeFilterValues(String[] expectedValues) {
		List<WebElement> l = d
				.findElements(By.xpath("//div[contains(@class,'timeFilterDropdownCtr')]/div//li//span[1]"));
		String value[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			value[i] = l.get(i).getAttribute("title");
		}
	}

	public WebElement toDate(int i) {
		return d.findElement(
				By.xpath("(//div[contains(@class,'timeFilterDropdownCtr')]//li[@class='li-level1'])[" + i + "]"));
	}

	public WebElement fromDate() {
		return getVisibileWebElement(d, "fromDate_CLP", "CashFlow", null);
	}

	// Added by Shivaprasad
	public WebElement featureTour() {
		return getVisibileWebElement(d, "feature", "CashFlow", null);
	}

	public WebElement featureTourNext() {
		return getVisibileWebElement(d, "nextFeature", "CashFlow", null);
	}

	public WebElement linkAllAcc() {
		return getVisibileWebElement(d, "LinkAllAcc_CLP", "CashFlow", null);
	}

	public WebElement interactiveChart() {
		return getVisibileWebElement(d, "InteractiveChart_CLP", "CashFlow", null);
	}

	public WebElement cashflowDetails() {
		return getVisibileWebElement(d, "CashflowDetails_CLP", "CashFlow", null);
	}

	/**
	 * Added for AccountSharing
	 */
	public WebElement no_data_screen_cf() {
		return getWebElement(d, "no_data_screen_cf", pageName, frameName);
	}

	public WebElement get_sidebar_value() {
		return getWebElement(d, "advisor_details", pageName, frameName);
	}

	public void loginAsAdvisor(String advisorName, String investorName, String finapp) {
		logger.info(">>>>> Logging in as Advisor..");
		SAML.login2(d, advisorName, investorName, finapp);
		waitForPageToLoad();
	}

	public void ftue_continue() {
		try {
			logger.info(">>>>> Clicking on continue");
			d.findElement(By.cssSelector("#ftue-continue")).click();
			waitForPageToLoad(3000);
			d.findElement(By.xpath("//span[text()='GO TO MY CASH FLOW']")).click();
			waitForPageToLoad(2000);
		} catch (Exception e) {
		}
	}

	public List<WebElement> groups_names() {
		return getWebElements(d, "groups_names_cf", pageName, frameName);
	}

	public WebElement account_dropdown() {
		return getWebElement(d, "account_dropdown_cf", pageName, frameName);
	}

	public WebElement checkbox_forecast() {
		return getWebElements(d, "checkbox_forecast_cf", pageName, frameName).get(2);
	}

	public WebElement no_access_screen() {
		return getVisibileWebElement(d, "no_access_screen_cf", pageName, frameName);
	}

	public WebElement moreIcon_CLP_mob() {
		return getVisibileWebElement(d, "moreIcon_CLP_mob", "CashFlow", null);
	}

	public WebElement allAccounts_DD_mob() {
		return getVisibileWebElement(d, "allAccounts_DD_mob", "CashFlow", null);
	}

	// added by renuka K
	public WebElement CFAccountDropDown() {
		return getVisibileWebElement(d, "CFAccountDropDown", "CashFlow", null);
	}

	public WebElement CFGroupLink() {
		return getVisibileWebElement(d, "CFGroupLink", "CashFlow", null);
	}

	public WebElement CFFTUEContinue() {
		return getVisibileWebElement(d, "CFFTUEContinue", "CashFlow", null);
	}

	public WebElement CFFTUEGoto() {
		return getVisibileWebElement(d, "CFFTUEGoto", "CashFlow", null);
	}

	public WebElement CFDropDownAccountLink() {
		return getVisibileWebElement(d, "CFDropDownAccountLink", "CashFlow", null);
	}

	public WebElement CFAllAccountCheckBox() {
		return getVisibileWebElement(d, "CFAllAccountCheckBox", "CashFlow", null);
	}

	public WebElement CFTimeFilterDropDown() {
		return getVisibileWebElement(d, "CFTimeFilterDropDown", "CashFlow", null);
	}

	public WebElement CFTimeFilterDropDownLabel() {
		return getVisibileWebElement(d, "CFTimeFilterDropDownLabel", "CashFlow", null);
	}

	public WebElement CFnetCashflowDataLable() {
		return getVisibileWebElement(d, "CFnetCashflowDataLable", "CashFlow", null);
	}

	public WebElement CFnetCashflowData() {
		return getVisibileWebElement(d, "CFnetCashflowData", "CashFlow", null);
	}

	public WebElement CFinflowDataLable() {
		return getVisibileWebElement(d, "CFinflowDataLable", "CashFlow", null);
	}

	public WebElement CFinflowData() {
		return getVisibileWebElement(d, "CFinflowData", "CashFlow", null);
	}

	public WebElement CFoutflowDataLable() {
		return getVisibileWebElement(d, "CFoutflowDataLable", "CashFlow", null);
	}

	public WebElement CFoutflowData() {
		return getVisibileWebElement(d, "CFoutflowData", "CashFlow", null);
	}

	public List<WebElement> CFsummaryDetails() {
		return getWebElements(d, "CFsummaryDetails", "CashFlow", null);
	}

	public List<WebElement> CFsummaryAverage() {
		return getWebElements(d, "CFsummaryAverage", "CashFlow", null);
	}

	public List<WebElement> CFAccountList() {
		return getWebElements(d, "CFAccountList", "CashFlow", null);
	}

	public List<WebElement> CFGroupNameList() {
		return getWebElements(d, "CFGroupNameList", "CashFlow", null);
	}

	public List<WebElement> CFTableColoumnLink(String column) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "CFTableColoumnLink");
		name = name.replaceAll("ColumValue", column);
		return d.findElements(By.xpath(name));
	}

	public List<WebElement> CFTableColoumnAmountLable(String column) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "CFTableColoumnAmountLable");
		name = name.replaceAll("ColumValue", column);
		return d.findElements(By.xpath(name));
	}

	public WebElement CFTimeFilterName(String timefilter) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "CFTimeFilterName");
		name = name.replaceAll("DateFilterName", timefilter);
		return d.findElement(By.xpath(name));
	}

	public void clickAccountLink() {
		click(this.CFDropDownAccountLink());
	}

	public void clickGroupLink() {
		click(this.CFGroupLink());
	}

	public void clickaccountDropDown() {
		click(this.CFAccountDropDown());
	}

	public WebElement CFGroupName(String group) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "CFGroupName");
		name = name.replaceAll("groupName", group);
		return d.findElement(By.xpath(name));
	}

	public WebElement CFGroupNameAccountEnabled(String group) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "CFGroupNameAccountEnabled");
		name = name.replaceAll("groupName", group);
		return d.findElement(By.xpath(name));
	}

	public WebElement CFAllAccountName(String accountname) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "CFAllAccountName");
		name = name.replaceAll("AccName", accountname);
		return d.findElement(By.xpath(name));
	}

	public void clickGroup(String group) {
		this.clickGroupLink();
		click(this.CFGroupNameAccountEnabled(group));
	}

	public void clickCFGroup(String group) {
		click(this.CFGroupName(group));
	}

	public void FTUE() {
		waitUntilSpinnerWheelIsInvisible();
		if (isDisplayed(getByObject("CashFlow", frameName, "CFFTUEContinue"), 3)) {
			click(this.CFFTUEContinue());
			waitForPageToLoad(500);
			click(this.CFFTUEGoto());
			waitForPageToLoad(500);
		}
	}

	public void selectaccount(String accountName) {
		String account[] = accountName.split(",");
		for (int i = 0; i < account.length; i++) {
			click(this.CFAllAccountName(account[i]));
		}
	}

	public WebElement allCashFlowAcntsLabel() {
		return getVisibileWebElement(d, "allCashFlowAcntsLabel", "CashFlow", null);
	}

	public WebElement GroupsLabel() {
		return getVisibileWebElement(d, "GroupsLabel", "CashFlow", null);
	}

	public boolean verifyAccountsGroupNameLabel(String groupName) {
		List<WebElement> l = getWebElements(d, "GroupsNameLabel", pageName, frameName);
		boolean status = CommonUtils.assertEqualsListElements(groupName, l);
		return status;
	}

	public boolean verifyManageCreateGroups(String propValue) {
		List<WebElement> l = getWebElements(d, "manageCreateGroup", "AccountGroups", frameName);
		boolean status = CommonUtils.assertEqualsListElements(propValue, l);
		return status;
	}

	// ended by renuka k
	public boolean isCF_done_mob_Present() {
		return PageParser.isElementPresent("CF_done_mob", "Expense", null);
	}

	public boolean istimeFilterCloseMobile() {
		return PageParser.isElementPresent("timeFilterClose", "CashFlow", null);
	}

	public WebElement timeFilterCloseMobile() {
		return getVisibileWebElement(d, "timeFilterClose", "CashFlow", null);
	}

	public boolean isaccclosebtn() {
		return isDisplayed(getByObject("CashFlow", null, "Cf_accclosebtn"), 2);
	}

	public WebElement accclosebtn() {
		return getVisibileWebElement(d, "Cf_accclosebtn", "CashFlow", null);
	}

	public void selectTimeFilter(String filter) {
		// selecte the time filter
		click(CFTimeFilterDropDown());
		click(CFTimeFilterName(filter));
	}
	
	public void openDurationDropdown() {
		By timeFilterDropdown = getByObject("CashFlow", null, "TimeFilterDropdown_CLP");
		if(getAttribute(timeFilterDropdown, "aria-expanded").equals("false")) {
			click(timeFilterDropdown);
		}
	}
}