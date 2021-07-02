/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.obo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.config.Config;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.opencsv.CSVReader;


public class OBO_Loc {
	static Logger logger=LoggerFactory.getLogger(OBO_Loc.class); 
	public WebDriver d=null;
	

	public OBO_Loc(WebDriver d) {
		this.d=d;
	}
	
	public WebElement continueButton() {
		return SeleniumUtil.getVisibileWebElement(d, "continueBtnInWelcomeCM", "InvestmentHoldings", null);
	}
	public WebElement goToFinappCoachMark() {
		return SeleniumUtil.getVisibileWebElement(d, "goToFinappCoachMark", "InvestmentHoldings", null);
	}
	public WebElement headerTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "InvestmentHoldingHeader", "InvestmentHoldings", null);
	}
	public WebElement holdingBalanceSection() {
		return SeleniumUtil.getVisibileWebElement(d, "holdingBalanceSection", "InvestmentHoldings", null);
	}
	public WebElement filterSection() {
		return SeleniumUtil.getVisibileWebElement(d, "filterSection", "InvestmentHoldings", null);
	}
	public WebElement chartSection() {
		return SeleniumUtil.getVisibileWebElement(d, "chartSection", "InvestmentHoldings", null);
	}
	public WebElement holdingsTable() {
		return SeleniumUtil.getVisibileWebElement(d, "holdingsTable", "InvestmentHoldings", null);
	}
	public List<WebElement> AccountsView() {
		return SeleniumUtil.getWebElements(d, "AccountsView", "AccountsPage", null);
	}

	public WebElement amount () {
		return SeleniumUtil.getVisibileWebElement(d, "amount_AMT", "Transaction", null); 
	}
	public WebElement description () {
		return SeleniumUtil.getVisibileWebElement(d, "description_AMT", "Transaction", null); 
	}

	public WebElement accountdropdown () {
		return SeleniumUtil.getVisibileWebElement(d, "accountdropdown_AMT", "Transaction", null);
	}
	public WebElement add () { 
		return SeleniumUtil.getWebElement(d, "add_AMT", "Transaction", null);
	}

	public List<WebElement> accountForTrans()
	{
		return SeleniumUtil.getWebElements(d ,"selectCreditAccount" , "Transaction",  null);

	}

	public List<WebElement> ListAccount(int container) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "ListAccount_AMT");
		abC=abC.replaceAll("container", Integer.toString(container));


		return d.findElements(By.xpath(abC));


	}
	public WebElement frequencyDropDown () {
		return SeleniumUtil.getVisibileWebElement(d, "frequencyDropDown_AMT", "Transaction", null);
	}
	public WebElement catgoryList(int MLC, int HLC) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "catgoryList_AMT");
		abC=abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC=abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);

		return d.findElement(By.xpath(abC));

	}
	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public List<WebElement> FrequencyList () { 
		return SeleniumUtil.getWebElements(d, "FrequencyList_AMT", "Transaction", null);
	}
	public WebElement endDate () { 
		return SeleniumUtil.getVisibileWebElement(d, "endDate_AMT", "Transaction", null);
	}

	public WebElement catgorie () { 
		return SeleniumUtil.getVisibileWebElement(d, "catgorie_AMT", "Transaction", null);
	}
	public WebElement showMoreDetails () {
		return SeleniumUtil.getVisibileWebElement(d, "showMoreDetails_AMT", "Transaction", null); 
	}
	public WebElement note () {
		return SeleniumUtil.getVisibileWebElement(d, "note_AMT", "Transaction", null);
	}
	public WebElement addManualLink_AMT() {
		return SeleniumUtil.getVisibileWebElement(d, "addManualLink_AMT", "Transaction", null);
	}
	public List<WebElement> projectedPostedHeader() {
		return SeleniumUtil.getWebElements(d, "projectedPostedHeader", "Transaction", null);
	}



	public void createTransactionWithRecuralldetails(String Amount, String Desc, int account, int freq, int sche, int enddate,
			int MLC, int HCL,String NoteName) {
		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		SeleniumUtil.click(accountdropdown());
		SeleniumUtil.waitForPageToLoad();

		if(Config.getInstance().getEnvironment().toLowerCase().contains("bbt"))
		{
			SeleniumUtil.click(accountForTrans().get(account));
		}
		else
		{
			SeleniumUtil.click(ListAccount(1).get(account));
			//SeleniumUtil.click(accountForTrans().get(account));
		}

		SeleniumUtil.click(frequencyDropDown());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(FrequencyList().get(freq));


		Schedule().sendKeys(targateDate1(sche));

		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		SeleniumUtil.click(catgorie());

		SeleniumUtil.waitForPageToLoad();
		if(Config.getInstance().getEnvironment().toLowerCase().contains("bbt"))
		{

			SeleniumUtil.click(catgoryList(MLC, HCL));
		}
		else
		{
			SeleniumUtil.click(catgoryList(MLC, HCL));
		}

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(showMoreDetails());
		SeleniumUtil.waitForPageToLoad(1000);
		note().sendKeys(NoteName);
		SeleniumUtil.waitForPageToLoad(1000);

		add().click();
	}
	public WebElement Schedule () {
		return SeleniumUtil.getVisibileWebElement(d, "Schedule_AMT", "Transaction", null); 
	}

	public WebElement goToNetworthFTUE() {
		return SeleniumUtil.getVisibileWebElement(d, "seeMyNWBtnCM", "NetWorth", null); 
	}
	public WebElement networthChart_Obo() {
		return SeleniumUtil.getVisibileWebElement(d, "networthChart_Obo", "NetWorth", null); 
	}
	public WebElement networthTable_Obo() {
		return SeleniumUtil.getVisibileWebElement(d, "networthTable_Obo", "NetWorth", null); 
	}
	public WebElement isExpenseSelected() 
	{
		return SeleniumUtil.getVisibileWebElement(d,"isExpenseSelected_MC", "Categories", null);
	}
	public String[] getCategoryGroupByOrder()
	{
		List<WebElement> l = SeleniumUtil.getWebElements(d,"getCategoryGroupByOrder_1_MC", "Categories", null);
		String names[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++)
		{
			names[i] = l.get(i).getText();
		}

		return names;
	}
	public String[] getHLC()
	{
		String val=null;
		List<WebElement> l = SeleniumUtil.getWebElements(d,"getHLC_MC", "Categories", null);

		String hlcs[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++)
		{
			hlcs[i] = l.get(i).getText().trim();
			val=val+","+hlcs[i];
		}
		return hlcs;
	}

	public String[] getMLCs()
	{
		String val=null;
		List<WebElement> l = SeleniumUtil.getWebElements(d,"getMLCs_MC", "Categories", null);

		String mlcs[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++)
		{
			mlcs[i] = l.get(i).getText().trim();
			val=val+","+mlcs[i];
		}
		logger.info("THe getMLCs"+ val);

		return mlcs;
	}

	public WebElement createRulesButton() {
		return SeleniumUtil.getVisibileWebElement(d, "createRulesButton", "CategorzationRules", null);
	}
	public WebElement noRuleSym() {
		return SeleniumUtil.getVisibileWebElement(d, "noRuleSym", "CategorzationRules", null);
	}
	public WebElement descTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "descTextBox", "CategorzationRules", null);
	}
	public WebElement amtDescBox() {
		return SeleniumUtil.getVisibileWebElement(d, "amtDescBox", "CategorzationRules", null);
	}
	public WebElement categoriesDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "categoriesDropDown", "CategorzationRules", null);
	}
	public WebElement applyRuleSwitchToggle() {
		return SeleniumUtil.getVisibileWebElement(d, "applyRuleSwitchToggle", "CategorzationRules", null);
	}
	public WebElement travelTick() {
		return SeleniumUtil.getWebElement(d, "travelTick", "CategorzationRules", null);
	}
	public WebElement createRuleIdButtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createRuleIdButtn", "CategorzationRules", null);
	}
	public List<WebElement> getCategoryLL() {
		return SeleniumUtil.getWebElements(d, "getCategoryLL", "CategorzationRules", null);
	}
	public List<WebElement> getRuleText() {
		return SeleniumUtil.getWebElements(d, "getRuleText", "CategorzationRules", null);
	}
	public WebElement CreateBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetTitle", "Budget", null);
	}
	public WebElement getStartedButton() {
		return SeleniumUtil.getVisibileWebElement(d, "getStartedButton", "Budget", null);
	}
	public WebElement createGroupBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createGroupBtnAGS", "AccountGroups", null);
	}
	public WebElement createOrUpdateGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "createOrUpdateGroupLoc", "AccountsPage", null);
	}
	public WebElement groupNameTxtBox() {
		return SeleniumUtil.getVisibileWebElement(d, "groupNameTxtBoxLoc", "AccountsPage", null);
	}
	public void selectCheckBox2()

	{
		SeleniumUtil.waitForElement(null, 2000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", "AccountsPage", null);

		for (int i = 0; i < 2; i++) {

			l.get(i).click();

			SeleniumUtil.waitForPageToLoad(1000);
		}
	}
	public WebElement EditText() {
		return SeleniumUtil.getVisibileWebElement(d, "editText", "AccountSettings", null);
	}
	public WebElement EditIconOne() {
		return SeleniumUtil.getVisibileWebElement(d, "EditIconOne", "AccountSettings", null);
	}
	public WebElement DeleteText() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteText", "AccountSettings", null);
	}
	public WebElement deleteIconNew() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteIconNew", "AccountSettings", null);
	}
	
	public void verifyEditIconAndText() {
		String editText=EditText().getText().trim();
		Boolean icon=EditIconOne().isDisplayed();
		Assert.assertEquals(editText, PropsUtil.getDataPropertyValue("EditTitle"));
		Assert.assertTrue(icon);
	}
	public void verifyDeleteIconAndText() {
		String editText=DeleteText().getText();
		Assert.assertEquals(editText, PropsUtil.getDataPropertyValue("DeleteTitle"));
		Boolean icon=deleteIconNew().isDisplayed();
		Assert.assertTrue(icon);
	}
	public List<WebElement> SFGhighLevlcategories() {
		return SeleniumUtil.getWebElements(d, "SFGhighLevlcategories", "SFG", null);
	}
	public List<WebElement> getSubCatText() {
		return SeleniumUtil.getWebElements(d, "getSubCatText", "SFG", null);
	}
	public WebElement GoalNameInput() {
		return SeleniumUtil.getWebElement(d, "GoalNameInput", "SFG", null);
	}
	public WebElement GoalAmountInput() {
		return SeleniumUtil.getWebElement(d, "GoalAmountInput", "SFG", null);
	}
	public WebElement GoalAmtFrqBtnInput() {
		return SeleniumUtil.getWebElement(d, "GoalAmtFrqBtnInput", "SFG", null);
	}

	public WebElement frequencyRadioButtonText() {
		return SeleniumUtil.getWebElement(d, "frequencyRadioButtonText", "SFG", null);
	}
	public WebElement GoalAmtFrqInput() {
		return SeleniumUtil.getWebElement(d, "GoalAmtFrqInput", "SFG", null);
	}
	public WebElement frequencyDropdown1() {
		return SeleniumUtil.getWebElement(d, "frequencyDropdown1", "SFG", null);
	}
	public List<WebElement> allFrequency(){	
		return SeleniumUtil.getWebElements(d, "allFrequency", "SFG", null);	
	}
	public WebElement nextbuttonClick() {
		return SeleniumUtil.getWebElement(d, "nextbuttonClick", "SFG", null);
	}
	public WebElement AccountDropDown1() {
		return SeleniumUtil.getWebElement(d, "AccountDropDown1", "SFG", null);
	}
	public WebElement MultiAccountSwitch() {
		return SeleniumUtil.getWebElement(d, "MultiAccountSwitch", "SFG", null);
	}
	public List<WebElement> checkBoxList() {
		return SeleniumUtil.getWebElements(d, "checkBoxList", "SFG", null);
	}
	public WebElement selectAccount(Integer index) {
		return checkBoxList().get(2*index);

	}

	public WebElement unSelectAccount(Integer index) {
		Integer a=2*index+1;
		System.out.println(a);
		return checkBoxList().get(2*index+1);

	} 
	public List<WebElement> amountBoxList() {
		return SeleniumUtil.getWebElements(d, "amountBoxList", "SFG", null);
	}
	public WebElement SaveAccountsBtn() {
		return SeleniumUtil.getWebElement(d, "SaveAccountsBtn", "SFG", null);
	}
	public WebElement saveTransferButton1() {
		return SeleniumUtil.getWebElement(d, "SFGFundingSaveButton", "SFG", null);
	}
	public WebElement getStartedTab() {
		return SeleniumUtil.getVisibileWebElement(d, "getStartedTab", "SFG", null);
	}
	public List<WebElement> allInprogressGoal()
	{
		return SeleniumUtil.getWebElements(d, "InProgressGoalList", "SFG", null);
	}
	
	public void createGoalwithMultipleAccount(String goalName ,String goalAmount,String Goalfrequency,String oneTimeAmount)
	{
		//SeleniumUtil.click(newGoalbutton());
		SeleniumUtil.click(SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000); 
		SeleniumUtil.click(GoalNameInput()); 
		GoalNameInput().clear();
		GoalNameInput().sendKeys(goalName);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		SeleniumUtil.waitForPageToLoad(2000);
		
		SeleniumUtil.click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(Goalfrequency);
		SeleniumUtil.click(frequencyDropdown1());
		SeleniumUtil.click(allFrequency().get(2));
		
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(nextbuttonClick());
		SeleniumUtil.click(AccountDropDown1());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(MultiAccountSwitch());
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(unSelectAccount(0));
		SeleniumUtil.click(amountBoxList().get(0));
		amountBoxList().get(0).sendKeys("50");
		amountBoxList().get(0).sendKeys(Keys.TAB);
		
		SeleniumUtil.click(selectAccount(1));
		SeleniumUtil.click(amountBoxList().get(1));
		amountBoxList().get(1).sendKeys("50");
		amountBoxList().get(1).sendKeys(Keys.TAB);
		
		 SeleniumUtil.click(SaveAccountsBtn());
		 SeleniumUtil.waitForPageToLoad();
		 SeleniumUtil.click(nextbuttonClick());
		 SeleniumUtil.click(saveTransferButton1());
		 SeleniumUtil.waitForPageToLoad();
		 SeleniumUtil.click(nextbuttonClick());
	}
	
	public WebElement NextButton1() {
		return SeleniumUtil.getVisibileWebElement(d, "NextButtonText_CB", "Budget", null);
	}
	public WebElement editIncome() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeEditIcon1", "Budget", null);
	}
	public WebElement editincometext() {
		return SeleniumUtil.getVisibileWebElement(d, "editincometext", "Budget", null);
	}
	public WebElement SaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeSaveButton", "Budget", null);
	}
	public WebElement nextButton2() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveButton", "Budget", null);
	}
	public WebElement editflexicon() {
		return SeleniumUtil.getVisibileWebElement(d, "editflexicon", "Budget", null);
	}
	public List<WebElement> editFlexibleCat() {
		return SeleniumUtil.getWebElements(d, "editFlexibleCat", "Budget", null);
	}
	public WebElement incomeSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeSaveButton", "Budget", null);
	}
	public WebElement incomeNextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeNextButton", "Budget", null);
	}
	public WebElement budgetSummary() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetSummary", "Budget", null);
	}
	public WebElement budgetDetailHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetDetailHeader", "Budget", null);
	}
	public List<WebElement> ValueList() {
		return SeleniumUtil.getWebElements(d, "ValueList", "Budget", null);
	}
	public WebElement deleteIcon() {
		return SeleniumUtil.getWebElement(d, "deleteIcon", "AccountsPage", null);
	}
	public WebElement deleteBtn() {
		return SeleniumUtil.getWebElement(d, "deleteBtn", "AccountsPage", null);
	}
	public WebElement goToAnalysis() {
		return SeleniumUtil.getWebElement(d, "coachMark2", "Expense", null);
	}
	public WebElement chartView() {
		return SeleniumUtil.getWebElement(d, "chartView", "Expense", null);
	}
	public WebElement contentTitle() {
		return SeleniumUtil.getWebElement(d, "contentTitle", "Expense", null);
	}
	public WebElement topFiveTransacTable() {
		return SeleniumUtil.getWebElement(d, "topFiveTransacTable", "Expense", null);
	}
	public List<WebElement> containerList() {
		return SeleniumUtil.getWebElements(d, "containerList", "AlertSetting", null);
	}	
	public WebElement myProfileTab() {
		return SeleniumUtil.getWebElement(d, "myProfile", "SAML_LOGIN", null);
	}
	public List<WebElement> myProfileList() {
		return SeleniumUtil.getWebElements(d, "myProfileList", "SAML_LOGIN", null);
	}
	public WebElement auditLog() {
		return SeleniumUtil.getWebElement(d, "auditLog", "SAML_LOGIN", null);
	}
	public WebElement customerName() {
		return SeleniumUtil.getWebElement(d, "customerName2", "SAML_LOGIN", null);
	}
	public WebElement customerNameTxtBox() {
		return SeleniumUtil.getWebElement(d, "customerNameTxtBox", "SAML_LOGIN", null);
	}
	public WebElement searchButton() {
		return SeleniumUtil.getWebElement(d, "searchButton2", "SAML_LOGIN", null);
	}
	public WebElement exportListTab() {
		return SeleniumUtil.getWebElement(d, "exportListTab", "SAML_LOGIN", null);
	}
	public void deletePreviousdownloadCSV(String fileName) {
		try {
			File file = new File(fileName);

			if (file.exists()) {
				System.out.println("File exists at " + fileName
						+ " So, deleting it before downloading");
				file.delete();
			} else {
				System.out.println("File doesn't exist at " + fileName
						+ " So, starting download process");
			}
		} catch (Exception e) {
			System.out.println("Not able to delete" + fileName);
			e.printStackTrace();
		}

	}
	
	public ArrayList<String> readFromCSV() throws IOException { 
		  ArrayList<String> actual = new ArrayList<String>();    
		   
		try {

			String fileName2=System.getProperty("user.dir")+File.separator+"downloads"+File.separator+"download.csv";
			CSVReader cs= new CSVReader(new FileReader(fileName2),',');
			List<String[]> readAll = cs.readAll();
			for(int i=1; i<readAll.size();i++)
			{
				String[] strings = readAll.get(i);
				actual.add(strings[1]);
			}
			System.out.println(actual.size());

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return actual;
	}
	
	
	
}
