/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/

package com.omni.pfm.AccountSharing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountSharing_Loc {

	static Logger logger = LoggerFactory.getLogger(AccountSharing_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public AccountSharing_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> getAllMainMenu() {
		return SeleniumUtil.getWebElements(d, "AllFinappsList", pageName, frameName);
	}

	public int dummyGeneratorHeader() {
		int size = 0;
		List<WebElement> el = SeleniumUtil.getWebElements(d, "allFinappsList", pageName, frameName);
		for (WebElement e : el) {
			if (!e.getAttribute("class").equalsIgnoreCase("more")) {
				size++;
			}
		}

		return size;
	}

	public List<WebElement> spendingSubMenu() {
		return SeleniumUtil.getWebElements(d, "spendingSubMenu", pageName, frameName);
	}

	public List<WebElement> settingsSubMenu() {
		return SeleniumUtil.getWebElements(d, "settingsSubMenu", pageName, frameName);
	}

	public WebElement spendingdropdown() {
		return SeleniumUtil.getWebElement(d, "spendingdropdown", pageName, frameName);
	}

	public WebElement settingdropdown() {
		return SeleniumUtil.getWebElement(d, "settingdropdown", pageName, frameName);
	}
	public WebElement settingdropdown2() {
		return SeleniumUtil.getWebElement(d, "settingdropdown2", pageName, frameName);
	}
	public WebElement continueButton() {
		return SeleniumUtil.getVisibileWebElement(d, "continueButton", "Expense", null);
	}

	public WebElement gotoAnalysisBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "gotoAnalysisBtn", "Expense", null);
	}

	public List<WebElement> accShareDropdownValue() {
		return SeleniumUtil.getWebElements(d, "AS_Dropdown", "ManageSharing", null);
	}

	public List<WebElement> fullViewShare() {
		return SeleniumUtil.getWebElements(d, "fullViewShare", "ManageSharing", null);
	}
	public List<WebElement> unsharingToNone() {
		return SeleniumUtil.getWebElements(d, "unsharingToNone", "ManageSharing", null);
	}

	public List<WebElement> viewBalnceOnly() {
		return SeleniumUtil.getWebElements(d, "viewBalnceOnly", "ManageSharing", null);
	}

	public WebElement AccountShareSave() {
		return SeleniumUtil.getWebElement(d, "AccountShareSave", "ManageSharing", null);
	}

	public List<WebElement> selectMonth() {
		return SeleniumUtil.getWebElements(d, "selectMonth", "Expense", null);
	}

	public WebElement acntsDropDown() {
		return SeleniumUtil.getWebElement(d, "acntsDropDown", "Expense", null);
	}

	public void verifyAdvisorAcntDDHeaderInvestor() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AcntsHeaderInAcntDD", "Expense", frameName);
		CommonUtils.assertEqualsListElements("AcntsHeaderInAcntDD", l);
	}

	public void AcntsHeaderInAcntDDForAdvisor() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AcntsHeaderInAcntDD", "Expense", frameName);
		CommonUtils.assertEqualsListElements("AcntsHeaderInAcntDDForAdvisor", l);
	}

	public WebElement allExpenseHeader() {
		return SeleniumUtil.getWebElement(d, "allExpenseHeader", "Expense", null);
	}

	public List<WebElement> sharedAcntNameList() {
		return SeleniumUtil.getWebElements(d, "sharedAcntNameList", "Expense", null);
	}

	public void sharedAcntCount(String propValue) {
		int actual = sharedAcntNameList().size();
		int expected = Integer.parseInt(PropsUtil.getDataPropertyValue(propValue).trim());
		Assert.assertEquals(actual, expected);
	}

	public void verifyInvestorSharedAcntNames(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "sharedAcntNameList", "Expense", frameName);
		CommonUtils.assertEqualsListElements(propValue, l);
	}

	public void verifyAdvisorSharedAcntNames(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "sharedAcntNameList", "Expense", frameName);
		CommonUtils.assertEqualsListElements(propValue, l);
	}

	public List<WebElement> accSharedByMeDropDowns() {
		return SeleniumUtil.getWebElements(d, "accSharedByMeDropDowns", "ManageSharing", null);
	}

	public List<WebElement> accSharedByMeViewBalOnly() {
		return SeleniumUtil.getWebElements(d, "accSharedByMeViewBalOnly", "ManageSharing", null);
	}

	public List<WebElement> accSharedByMeFullViewShare() {
		return SeleniumUtil.getWebElements(d, "accSharedByMeFullViewShare", "ManageSharing", null);
	}

	public void investmentAndInsuranceFullAccessShare() {
		for (int i = 0; i < 2; i++) {
			SeleniumUtil.waitForPageToLoad(3000);
			SeleniumUtil.click(accShareDropdownValue().get(i));
			SeleniumUtil.click(fullViewShare().get(i));
		}
	}
	public WebElement NoAcntDesertScreen() {
		return SeleniumUtil.getWebElement(d, "AccessNotGrantedMsg", "Expense", null);
	}
	
	public void otherAcntFullAccessShare() {
		for (int i = 21; i < 25; i++) {
			SeleniumUtil.click(accShareDropdownValue().get(i));
			SeleniumUtil.click(fullViewShare().get(i));
		}
	}

	public void shareWithViewBalncAccess() {
		for (int i = 3; i <= 6; i++) {
			SeleniumUtil.click(accShareDropdownValue().get(i));
			SeleniumUtil.click(viewBalnceOnly().get(i));
		}
	}

	public void shareWithViewBalncAccess2() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accSharedByMeDropDowns().get(2));
		SeleniumUtil.click(accSharedByMeViewBalOnly().get(2));
	}

	public void shareWithFullAccess() {
		for (int i = 0; i < 2; i++) {
			SeleniumUtil.click(accSharedByMeDropDowns().get(i));
			SeleniumUtil.click(accSharedByMeFullViewShare().get(i));
		}
	}
	public WebElement IncomeDropdown() {
		return SeleniumUtil.getWebElement(d, "IncomeDropdown", "Expense", frameName);
	}
	public WebElement ExpenseDropdown() {
		return SeleniumUtil.getWebElement(d, "ExpenseDropdown", "Expense", frameName);
	}
	public List<WebElement> expenseIncomeList() {
		return SeleniumUtil.getWebElements(d, "expenseIncomeList", "Expense", frameName);
	}
	public WebElement groupType() {
		return SeleniumUtil.getWebElement(d, "grpType", pageName, frameName);
	}

	public WebElement getCreateGroup() {
		return SeleniumUtil.getWebElement(d, "createGroupBtn", pageName, frameName);
	}

	public WebElement groupName() {
		return SeleniumUtil.getWebElement(d, "groupNm", pageName, frameName);
	}

	public void typeGroupName(String textToBeinseterd) {
		groupName().sendKeys(textToBeinseterd);
	}

	public WebElement moreButon() {
		return SeleniumUtil.getVisibileWebElement(d, "moreBtn", pageName, frameName);
	}

	public WebElement CreateBtn1() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupBtnInAdv", pageName, frameName);
	}

	public WebElement backToExpenseCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "backToExpenseCategory", "Expense", frameName);
	}

	public void selectCheckBox()

	{
		SeleniumUtil.waitForElement(null, 2000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);

		SeleniumUtil.click(l.get(3));

	}

	public void selectCheckBox2()

	{
		SeleniumUtil.waitForElement(null, 2000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);
		for (int i = 1; i < 5; i++) {

			l.get(i).click();

			SeleniumUtil.waitForPageToLoad(1000);
		}
	}

	public WebElement createUpdateGroupBtm() {
		return SeleniumUtil.getWebElement(d, "createUpdateGroup", pageName, frameName);
	}

	public List<WebElement> accountDataSelectionDD() {
		return SeleniumUtil.getWebElements(d, "accountDataSelectionDD", "Expense", frameName);
	}

	public List<WebElement> accountsDDGroups() {
		return SeleniumUtil.getWebElements(d, "accountsDDGroupsName", "Expense", frameName);
	}

	public WebElement noDataContainerForBalanceOnly() {
		return SeleniumUtil.getWebElement(d, "noDataContainerForBalanceOnly", "Expense", frameName);
	}

	public void navigatingToAcntDDGroup() {
		SeleniumUtil.click(selectMonth().get(1));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(acntsDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDataSelectionDD().get(1));
	}

	public WebElement chartForFullAccess() {
		return SeleniumUtil.getWebElement(d, "chartForFullAccess", "Expense", frameName);
	}

	public WebElement allCateoriesDataForFullAccess() {
		return SeleniumUtil.getWebElement(d, "allCateoriesDataForFullAccess", "Expense", frameName);
	}

	public WebElement closeActnOption() {
		return SeleniumUtil.getWebElement(d, "closeActnOption", pageName, frameName);
	}

	public WebElement saveChangesBtnAAS() {
		return SeleniumUtil.getWebElement(d, "saveChangesBtnAAS", pageName, frameName);
	}

	public WebElement deleteAccountLnkAAS() {
		return SeleniumUtil.getWebElement(d, "deleteAccountLnkAAS", pageName, frameName);
	}

	public WebElement clsAccConfirmBtn() {
		return SeleniumUtil.getWebElement(d, "clsAccConfirmBtn", pageName, frameName);
	}
	public WebElement closeAcntSuccessMsg() {
		return SeleniumUtil.getWebElement(d, "closeAcntSuccessMsg", pageName, frameName);
	}

	public WebElement deleteBtnAAS() {
		return SeleniumUtil.getWebElement(d, "deleteBtnAAS", pageName, frameName);
	}

	public WebElement deleteconfirmcheckBoxAAS() {
		return SeleniumUtil.getWebElement(d, "deleteconfirmcheckBoxAAS", pageName, frameName);
	}

	public void sharedAcntCountAfterClosingAndDeleting() {
		int actual = sharedAcntNameList().size();
		int expected = Integer
				.parseInt(PropsUtil.getDataPropertyValue("InvestorSharedAcntCountAfterClosingAndDeleting").trim());
		Assert.assertEquals(actual, expected);
	}

	public void verifyInvestorSharedAcntAfterClosingAndDeleting() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "sharedAcntNameList", "Expense", frameName);
		CommonUtils.assertEqualsListElements("SharedAcntNamesAfterDeletingAndClosing", l);
	}

	// Story B-09297
	public void sharingWithBalanceOnly() {
		for (int i = 0; i <= 3; i++) {
			SeleniumUtil.click(accShareDropdownValue().get(i));
			SeleniumUtil.click(viewBalnceOnly().get(i));
		}
	}

	public void IncomeExpenseFtueCompletion() {
		SeleniumUtil.click(continueButton());
		SeleniumUtil.click(gotoAnalysisBtn());
	}

	public WebElement totalSpendingAmount() {
		return SeleniumUtil.getWebElement(d, "TotalSpendingAmount", "Expense", frameName);
	}

	public WebElement accessNotGrantedMsg() {
		return SeleniumUtil.getWebElement(d, "AccessNotGrantedMsg", "Expense", frameName);
	}

	public WebElement accessNotGrantedDiscription() {
		return SeleniumUtil.getWebElement(d, "AccessNotGrantedDiscription", "Expense", frameName);
	}

	public WebElement last3MonthAverageValue() {
		return SeleniumUtil.getWebElement(d, "Last3MonthAverageValue", "Expense", frameName);
	}

	public WebElement currentMonthAmt() {
		return SeleniumUtil.getWebElement(d, "currentMonthAmt", "Expense", frameName);
	}

	public List<WebElement> monthlyAmountValue() {
		return SeleniumUtil.getWebElements(d, "MonthlyAmountValue", "Expense", frameName);
	}

	public void verifyInvMonthlyAmountValues(String propValueName) {
		String actual[] = PropsUtil.getDataPropertyValue(propValueName).split(":");

		for (int i = 0; i < monthlyAmountValue().size(); i++) {
			String expected = monthlyAmountValue().get(i).getText().trim();
			Assert.assertEquals(actual[i], expected);
		}
	}

	public void verifyAdvMonthlyAmountValues() {
		String actual[] = PropsUtil.getDataPropertyValue("AdvMonthlyAmountValue").split(":");

		for (int i = 0; i < monthlyAmountValue().size(); i++) {
			String expected = monthlyAmountValue().get(i).getText().trim();
			Assert.assertEquals(actual[i], expected);
		}
	}

	public void verifyInvAllCategoriesIncluded() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AllIncludedCategories", "Expense", frameName);
		CommonUtils.assertEqualsListElements("InvAllIncludedCategories", l);
	}

	public void verifyAdvAllCategoriesIncluded() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AllIncludedCategories", "Expense", frameName);
		CommonUtils.assertEqualsListElements("AdvAllIncludedCategories", l);
	}

	public void verifyInvAllCategoriesIncludedAfterSharing() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AllIncludedCategories", "Expense", frameName);
		CommonUtils.assertEqualsListElements("InvAllCategoriesIncludedAfterSharing", l);
	}

	public void validatecurrentMonthAmount(String expectedAmount) {
		//String actual = currentMonthAmt().getText().trim();
		String actual = PropsUtil.getDataPropertyValue(expectedAmount);
		
		String expected = monthlyAmountValue().get(0).getText().trim();
		Assert.assertEquals(actual, expected);
	}
	public void validatecurrentMonthAmount1() {
		//String actual = currentMonthAmt().getText().trim();
		String actual = PropsUtil.getDataPropertyValue("InvMonthlyAmountValue1");
		
		String expected = monthlyAmountValue().get(0).getText().trim();
		Assert.assertEquals(actual, expected);
	}
	public void verifyLast3MonthAverageValue() {
		String getText= last3MonthAverageValue().getText().trim();
		String getValue = getText.substring(getText.indexOf(":")+1, getText.length()).trim();
		
		double expectedAvg=Double.parseDouble(getValue.substring(1).replaceAll(",", ""));
      
		double firstMonth = Double.parseDouble(monthlyAmountValue().get(1).getText().trim().substring(1).replaceAll(",", ""));
		double secondMonth= Double.parseDouble(monthlyAmountValue().get(2).getText().trim().substring(1).replaceAll(",", ""));
		double thirdMonth = Double.parseDouble(monthlyAmountValue().get(3).getText().trim().substring(1).replaceAll(",", ""));

		double Total = firstMonth + secondMonth + thirdMonth;
		double actualAvg=Total/3;
		double roundOffActual =Double.parseDouble(new DecimalFormat("##.##").format(actualAvg));
		Assert.assertEquals(roundOffActual, expectedAvg);
	}

	public void verifyInvTopFiveExpenseLabel() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "topFiveExpenseLabel", "Expense", frameName);
		CommonUtils.assertEqualsListElements("InvTopFiveExpenseLabel", l);
	}

	public void TopFiveExpenseLabelPostSharing() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "topFiveExpenseLabel", "Expense", frameName);
		CommonUtils.assertEqualsListElements("InvTopFiveExpenseLabelAfterSharing", l);
	}

	public void verifyAdvTopFiveExpenseLabel() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "topFiveExpenseLabel", "Expense", frameName);
		CommonUtils.assertEqualsListElements("AdvTopFiveExpenseLabel", l);
	}


	public void SharingAllAcntsFullAccess() {
		for (int i = 0; i < accShareDropdownValue().size(); i++) {
			SeleniumUtil.click(accShareDropdownValue().get(i));
			SeleniumUtil.click(fullViewShare().get(i));
		}
	}
	
	public void unsharingAcnt() {
			SeleniumUtil.click(accSharedByMeDropDowns().get(0));
			SeleniumUtil.click(unsharingToNone().get(0));
	}
	

	public List<WebElement> MoreDropDownList() {
		return SeleniumUtil.getWebElements(d, "MoreDropDownList", "Expense", frameName);
	}
	public List<WebElement> RightSideIconForAdvisor() {
		return SeleniumUtil.getWebElements(d, "RightSideIconForAdvisor", "Expense", frameName);
	}
	
	public void verifyMoreDropdown(int dropdownValue) {
		ArrayList<String> actual = new ArrayList<String>();
		for(int i=0;i<MoreDropDownList().size();i++) {
			String actual1=MoreDropDownList().get(i).getAttribute("class");
			if(actual1.contains(PropsUtil.getDataPropertyValue("AdvisorMoreDDOptions"))) {
				actual.add(actual1);
			}
		}
		Assert.assertEquals(actual.size(), dropdownValue);	
	}
	
	
	public void verifyRightSideIcons() {
		ArrayList<String> actual = new ArrayList<String>();
		for(int i=0;i<RightSideIconForAdvisor().size();i++) {
			String actual1=RightSideIconForAdvisor().get(i).getAttribute("class");
			if(actual1.contains(PropsUtil.getDataPropertyValue("AdvisorMoreDDOptions"))) {
				actual.add(actual1);
			}
		}
		Assert.assertEquals(actual.size(), 2);	
	}
	
	public WebElement alertSharedDisclaimerMsg() {
		return SeleniumUtil.getWebElement(d, "alertSharedDisclaimerMsg", "AlertSetting", frameName);
	}
	
	
	public List<WebElement> moreOptBtn() {
		return SeleniumUtil.getWebElements(d, "moreOptBtn", "AccountsPage", frameName);
	}
	
	public WebElement alertSettingsUnderMoreDD() {
		return SeleniumUtil.getWebElement(d, "alertSettingsUnderMoreDD", "AccountsPage", frameName);
	}
	
	public List<WebElement> advisorSharedCotainer() {
		return SeleniumUtil.getWebElements(d, "advisorSharedCotainer", "AccountsPage", frameName);
	}
	
	public List<WebElement> advisorOwnCotainer() {
		return SeleniumUtil.getWebElements(d, "advisorOwnCotainer", "AccountsPage", frameName);
	}
	
	
	// Verifying for negative test cases so hardcoding below xpath.
	
	public Boolean isLinkAccountBtnPresent() {
		
		Boolean isShown = false;
		
		try {
			WebElement DashboradlinkAccount=d.findElement(By.xpath("#suggestedAcc-linkOtherAccount-persist"));
			WebElement addLinkAfterAccount =d.findElement(By.xpath("#link-account-btn-persist"));
			WebElement addLinkAfterTwoAccount = d.findElement(By.xpath("#link-account-button-persist"));

			if(DashboradlinkAccount.isDisplayed() || addLinkAfterAccount.isDisplayed() || addLinkAfterTwoAccount.isDisplayed()) {
				isShown = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return isShown;
	}
	
	public Boolean alertSettingUnderMoreInAdv() {
		Boolean isShown = false;
		
        for(int i=0;i<moreOptBtn().size();i++) {
			
			SeleniumUtil.click(moreOptBtn().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			
			try {
				if(d.findElement(By.xpath("//a[@autoid='accounts_more_values' and contains(text(),'Alert Settings')]")).isDisplayed()) {
					isShown = true;
				}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
        return isShown;
		
	}
	
	
	public Boolean alertSettingUnderMoreInInvForShared() {
		Boolean isShown = false;
		
		for(int i=0;i<advisorSharedCotainer().size();i++) {
			SeleniumUtil.click(advisorSharedCotainer().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			try {
				if(d.findElement(By.xpath("//a[@autoid='accounts_more_values' and contains(text(),'Alert Settings')]")).isDisplayed()) {
					isShown = true;
				}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
        return isShown;
	}
	
	public Boolean alertSettingUnderMoreInInvForOwnAcnt() {
		Boolean isShown = false;
		
		for(int i=0;i<advisorOwnCotainer().size();i++) {
			SeleniumUtil.click(advisorOwnCotainer().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			try {
				if(d.findElement(By.xpath("//a[@autoid='accounts_more_values' and contains(text(),'Alert Settings')]")).isDisplayed()) {
					isShown = true;
				}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
        return isShown;
	}
	
	public void getStartedFL() {
		try {
			WebElement getStartedText = SeleniumUtil.getVisibileWebElement(d, "getStarted", "LinkAnAccount", null);
			if (getStartedText.isDisplayed()) {
				SeleniumUtil.click(getStartedText);

			}
		} catch (Exception e) {

			System.out.println(e);
		}
	}
	
	public WebElement goToNetworth() {
		return SeleniumUtil.getWebElement(d, "seeMyNWBtnCM", "NetWorth", null);
	}
	public  WebElement goToMyInvestments()
	{
		return SeleniumUtil.getWebElement(d, "goToMyInvestments", "InvestmentHoldings", null);
	}
	public  List<WebElement> settingAtAcntLevel()
	{
		return SeleniumUtil.getWebElements(d, "settingAtAcntLevel", "AccountSettings", null);
	}
	public  WebElement categoryDropDown()
	{
		return SeleniumUtil.getWebElement(d, "categoryDropDown", "AccountSettings", null);
	}
    public WebElement catgoryList(int MLC, int HLC) {
		
		String abC = SeleniumUtil.getVisibileElementXPath(d, "AccountSettings", null, "catgoryList_AMT");
        abC=abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC=abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);

		return d.findElement(By.xpath(abC));
		
	}
    public  WebElement categoryDropDownValue()
	{
		return SeleniumUtil.getWebElement(d, "categoryDropDownValue", "AccountSettings", null);
	}
    
    
    public List<WebElement> AlertSettingContainer()
	{
		return SeleniumUtil.getWebElements(d, "AlertSettingContainer", "AlertSetting", null);
	}
    
    public void navigatingToIncomeAnalysis() {
    	SeleniumUtil.click(backToExpenseCategory());
    	SeleniumUtil.waitForPageToLoad(2000);
    	try {
    		SeleniumUtil.click(IncomeDropdown());	
    	}catch (Exception e) {
    		SeleniumUtil.click(ExpenseDropdown());
		}
    	SeleniumUtil.click(expenseIncomeList().get(1));
    	SeleniumUtil.waitForPageToLoad();
    }
    
    public void navigatingToExpenseAnalysis() {
    	SeleniumUtil.waitForPageToLoad();
    	try {
    		SeleniumUtil.click(ExpenseDropdown());
    	}catch (Exception e) {
    		SeleniumUtil.click(IncomeDropdown());
		}
    	SeleniumUtil.waitForPageToLoad(2000);
    	SeleniumUtil.click(expenseIncomeList().get(0));
    	SeleniumUtil.waitForPageToLoad(3000);
    }
    
    public List<WebElement> AcntGroupsAmount()
   	{
   		return SeleniumUtil.getWebElements(d, "AcntGroupsAmount", "AccountGroups", null);
   	}
    public  WebElement ClosedAcntSymbol()
   	{
   		return SeleniumUtil.getWebElement(d, "ClosedAcntSymbol", "AccountGroups", null);
   	}
    
    public void deletingAcnt() {
    	SeleniumUtil.click(deleteAccountLnkAAS());
    	SeleniumUtil.click(deleteconfirmcheckBoxAAS());
    	SeleniumUtil.click(deleteBtnAAS());
    	SeleniumUtil.waitForPageToLoad(2000);
    }
  
    
}
	
		
	
