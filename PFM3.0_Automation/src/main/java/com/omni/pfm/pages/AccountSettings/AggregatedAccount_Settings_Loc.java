/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.AccountSettings;

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

public class AggregatedAccount_Settings_Loc {

	static Logger logger = LoggerFactory.getLogger(AggregatedAccount_Settings_Loc.class);
	public WebDriver d =null;
	static WebElement we;
	
	public AggregatedAccount_Settings_Loc(WebDriver d)
	{
		this.d=d;
		
	}

	public List<WebElement> AlertTogOnSettFin() {
		return SeleniumUtil.getWebElements(d, "AlertTogOnSettFin", "AlertSetting", null);
	}
		
	public WebElement linkAccountBtn() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "linkAccountBtnAAS", "AccountsPage", null);
	}

	/*
	 * 
	 * 
	 * 
	 * This methods click the ellipse {settings} based upon the number provided
	 * 
	 * 
	 * 
	 * as an arguments.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Ellipse: 3 dots at account level
	 */

	public void clickSettingsElipse(int i) {

		// i = i - 1;
		
		// String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountsPage", null, "clickSettingsElipseAAS");
 //WebElement varX = SeleniumUtil.getVisibileWebElement(d, "clickSettingsElipseAAS", "AccountsPage", null);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "clickSettingsElipseAAS", "AccountsPage", null);

		WebElement w = l.get(i);

		//SeleniumUtil.fluentWait(d);

		SeleniumUtil.click(w);

	}
	
	/*public WebElement AccountSettingList(int i){
		
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountSettingsOption", "AccountsPage", null);
		WebElement w = l.get(i);
		return w;
		
	} */
	
public WebElement AccountSettingList(int i){
		
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountSettingsOption", "AccountsPage", null);
		WebElement w = l.get(i);
		return w;
		
	}

public List<WebElement> AccountSettingsOption(){
	return SeleniumUtil.getWebElements(d, "AccountSettingsOption", "AccountsPage", null);
}
	
public WebElement AlertSettingList(int i){
		
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AlertSettings", "AccountsPage", null);
		WebElement w = l.get(i);
		return w;
		
	}
	public WebElement dagCheckingMoreOptions(){
		return SeleniumUtil.getWebElement(d, "DagCheckingAccountMoreoptions", "AccountsPage", null);
	}
	public List<WebElement> morebtn_AccFin(){
		return SeleniumUtil.getWebElements(d, "morebtn_AccFin", "AccountsPage", null);
	}
	public WebElement manualCashAccountMoreOptions(){
		return SeleniumUtil.getWebElement(d, "ManualCashAccountMoreoptions", "AccountsPage", null);
	}
	
	public WebElement manualCardAccountMoreOptions(){
		return SeleniumUtil.getWebElement(d, "ManualCardAccountMoreoptions", "AccountsPage", null);
	}
	
	public List<WebElement> togglebtn(){
		return SeleniumUtil.getWebElements(d, "togglebtn", "AccountSettings", null);
	}
	
	public WebElement ManualAccInfo(){
		return SeleniumUtil.getWebElement(d, "ManualAccInfo", "AccountsPage", null);
	}
	
	public List<WebElement> accounts_TotalBal_Acc(){
		return SeleniumUtil.getWebElements(d, "accounts_TotalBal_Acc", "AccountsPage", null);
	}
	public List<WebElement> Manual_account_AccFin(){
		return SeleniumUtil.getWebElements(d, "Manual_account_AccFin", "AccountsPage", null);
	}
	public WebElement manualInsuranceAccountMoreOptions(){
		return SeleniumUtil.getWebElement(d, "ManualInsuranceAccountMoreoptions", "AccountsPage", null);
	}
	public List<WebElement> alert_option(){
		return SeleniumUtil.getWebElements(d, "alert_option", "AccountsPage", null);
	}
	public WebElement transAcc(){
		return SeleniumUtil.getWebElement(d, "transAcc", "AccountsPage", null);
	}
	
	
	public WebElement accountSettingOption() {
		return SeleniumUtil.getWebElement(d, "AccountSettingsOption", "AccountsPage", null);
	}
	
	public WebElement alertSettingOption() {
		return SeleniumUtil.getWebElement(d, "AlertSettings", "AccountsPage", null);
	}
	
	
	public void clickOnMoreButton(int i) {
		
		String varX = SeleniumUtil.getVisibileElementXPath(d, "AccountsPage", null, "clickOnMoreButton_AAS");

		List<WebElement> l = d.findElements(By.xpath(varX));

		WebElement w = l.get(i);

		SeleniumUtil.fluentWait(d);

		SeleniumUtil.click(w);
	}

	public WebElement stopRefreshButton() {
		return SeleniumUtil.getVisibileWebElement(d, "stopRefresh", "AccountsPage", null);
	}
	
	public WebElement realEstateAccountMoreOptions(){
		return SeleniumUtil.getWebElement(d, "ManualRealEstateAccountMoreoptions", "AccountsPage", null);
	} 
	
	public WebElement automaticCalculateToggleforRealEstate() {
		return SeleniumUtil.getVisibileWebElement(d, "RealEstateAutoCalculateToggle", "AccountsPage", null);
	}
	
	public WebElement manualToggleforRealEstate() {
		return SeleniumUtil.getVisibileWebElement(d, "RealEstateManualToggle", "AccountsPage", null);
	}



	public void clickAccountSettings(int containerNum, int innerContainerNum,
			int accountRowNum, int settingNumber) {

		settingNumber = settingNumber - 1;

		List<WebElement> l = d.findElements(By
						.xpath("//div[@class='accounts-financial-container inner']["

								+ containerNum
								+ "]//div[@class='accounts-container']["
								+ innerContainerNum
								+ "]//div[@class='accounts-row']["
								+ accountRowNum
								+ "]//ul[contains(@id,'financial_settingsdropdown')]/li[1]"));

		SeleniumUtil.fluentWait(d);

		l.get(settingNumber).click();

		;

	}

	public void assertSettingsOptionsValue(int optionNumber, String expectedText) {

		WebElement l = d.findElement(By
						.xpath("//section[@id='fiAndAccountTypesView']//div[contains(@class,'accounts-financial-container inner')]//div[@class='accounts-container'][1]/div[2]/div[@class='accounts-row'][1]//ul[contains(@id,'financial_settingsdropdown')]/li["
								+ optionNumber + "]/a"));

		// section[@id='fiAndAccountTypesView']//div[contains(@class,'accounts-financial-container
		// inner')]//div[@class='accounts-container'][1]/div[2]/div[@class='accounts-row'][1]//ul[contains(@id,'financial_settingsdropdown')]/li["
		// + optionNumber + "]/a

		String text = l.getText();

		Assert.assertEquals(text, expectedText);

	}

	public String getSettingsHeaderText() {

		
		/*WebElement element = d.findElement(By.xpath("//div[@id='accountSettingsPopup']//h3"));

		String text = element.getText();

		return text;*/
		
		return SeleniumUtil.getVisibileWebElement(d, "getSettingsHeaderTextAAS", "AccountsPage", null).getText();


	}

		
	public WebElement Title() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "getSettingsHeaderTextAAS", "AccountsPage", null);
	}

	
	public WebElement getAccountHeadingText() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {

			return SeleniumUtil.getVisibileWebElement(d, "getAccountHeadingTextAAS", "AccountsPage", null);

		}

		else {

			//return d.findElement(By.xpath("//div[@class='edit-account-form-box']/div[2]/div[1]//label"));
			
			return SeleniumUtil.getVisibileWebElement(d, "getAccountHeadingTextAAS", "AccountsPage", null);

		}

	}

	
	public WebElement getNickNameHeadingText() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {

			 return SeleniumUtil.getVisibileWebElement(d, "getNickNameHeadingTextAAS", "AccountsPage", null);

		}

		else {

			//return d.findElement(By.xpath("//form[@id='edit-account-form']/div[1]//label[2]/span"));
		
			return SeleniumUtil.getVisibileWebElement(d, "getNickNameHeadingTextFunAAS", "AccountsPage", null);

		}

	}


	public WebElement getCategorizeHeadingText() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {

			return SeleniumUtil.getVisibileWebElement(d, "getCategorizeHeadingTextAAS", "AccountsPage", null);
			
		} else {

			//return d.findElement(By.xpath("//div[@id='accountTags']/label"));
			
			return SeleniumUtil.getVisibileWebElement(d, "getCategorizeHeadingTextFunAAS", "AccountsPage", null);

		}

	}



	public void clickCategoryDropDown() {

        WebElement element = d.findElement(By.id("gCategoryDropdownAcc"));

        element.click();
        
//            SeleniumUtil.getVisibileWebElement(d, "clickCategoryDropDownAAS", "AccountsPage", null);

}

	
	//change

	public void selectCategoryByNumber(int highLevelCategoryNum,int masterLevelCategoryNum) {
        
        WebElement element = d.findElement(By.xpath("//div[@id='g-generic-dropdown0']//ul[@id='ul-0']/li["+ highLevelCategoryNum + "]//ul//li[" + masterLevelCategoryNum + "]//span"));

//            WebElement element = d.findElement(By.xpath("//div[@id='category-list']//li//ul/li["+ highLevelCategoryNum + "]//ul/li[" + masterLevelCategoryNum + "]//span"));

element.click();

}

	
	public WebElement unSelectUserSelectedCategory(){
		return SeleniumUtil.getWebElement(d, "unselectUserSelectedCategory", "AccountsPage", null);
	}

	public String getSelectedCategoryName() {

		
		return SeleniumUtil.getWebElement(d, "getSelectedCategoryNameAAS", "AccountsPage", null).getText();

		
	}

		
	public WebElement nickNameTextBox() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "nickNameTextBoxAAS", "AccountsPage", null);
	}

	
	
		
	public WebElement AccountSettingsText() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "AccountSettingsTextAAS", "AccountsPage", null);
	}

	
		public WebElement applyCategoryToPastTxn() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "applyCategoryToPastTxnAAS", "AccountsPage", null);
	}

	
	
		public WebElement deleteAccountLnk() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "deleteAccountLnkAAS", "AccountsPage", null);
	}

	
	
		
	public WebElement deleteBtn() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "deleteBtnAAS", "AccountsPage", null);
	}
	
	

		
	public WebElement cancelBtn() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "cancelBtnAAS", "AccountsPage", null);
	}

	
		public WebElement deleteconfirmcheckBox() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "deleteconfirmcheckBoxAAS", "AccountsPage", null);
	}
	
	

		
	public WebElement closeDeletePopUp() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "closeDeletePopUpAAS", "AccountsPage", null);
	}

	public WebElement applyCattoPastTransactionToggleBtn() {

		//return d.findElement(By.xpath("//div[@id='apply-category-box']"));
		
		return SeleniumUtil.getVisibileWebElement(d, "applyCattoPastTransactionToggleBtnAAS", "AccountsPage", null);

	}

	public WebElement showHideAccountToggleBtn() {

		//return d.findElement(By.id("toggleSwitch-show-account-in-summary-box"));

		return SeleniumUtil.getVisibileWebElement(d, "showHideAccountToggleBtnAAS", "AccountsPage", null);

	}

	public String getApplyToPastTxnToggleBtnState() {

		//return d.findElement(By.xpath("//div[@id='apply-category-box']//input")).getAttribute("value");

		return SeleniumUtil.getWebElement(d, "getApplyToPastTxnToggleBtnStateAAS", "AccountsPage", null).getAttribute("value");

	}

	public String getShowHideAccountToggleBtnState() {

		//return d.findElement(By.xpath("//div[@id='toggleSwitch-show-account-in-summary-box']//input")).getAttribute("value");
		
		return SeleniumUtil.getWebElement(d, "getShowHideAccountToggleBtnStateAAS", "AccountsPage", null).getAttribute("value");


	}

	
	
	public WebElement accountNameTxt() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {

			return SeleniumUtil.getVisibileWebElement(d, "accountNameTxtAAS", "AccountsPage", null);
			
		} else {

			//return d.findElement(By.xpath("//div[@class='edit-account-form-box']/div[2]/div[1]/div[3]/div[2]/span[2]"));
		
			return SeleniumUtil.getVisibileWebElement(d, "accountNameTxtFunAAS", "AccountsPage", null);

		}
	}

	
	
		
	public WebElement saveChangesBtn() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtnAAS", "AccountsPage", null);
	}

		public WebElement deletePopUp() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "deletePopUpAAS", "AccountsPage", null);
	}
	
	

		
	public WebElement closeBtn() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "closeBtnAAS", "AccountsPage", null);
	}

	
	
	public List<WebElement> nickNameinAccSetFin() { 
		 return SeleniumUtil.getWebElements(d, "nickNameinAccSetFin", "AccountsPage", null);
	}
	
	public WebElement selectCategoryOpt() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "selectCategoryOptAAS", "AccountsPage", null);
	}

	
		public WebElement accountNum() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB")
				.equalsIgnoreCase("WEB")) {

			 return SeleniumUtil.getVisibileWebElement(d, "accountNumAAS", "AccountsPage", null);
		} else {

			//return d.findElement(By.xpath("//div[@class='edit-account-form-box']/div[2]/div[1]/div[3]/div[2]/span[3]"));
		
			return SeleniumUtil.getVisibileWebElement(d, "accountNumFunAAS", "AccountsPage", null);

		}
	}

	
	
		public WebElement accountType() {

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB")
				.equalsIgnoreCase("WEB")) {

			return SeleniumUtil.getVisibileWebElement(d, "accountTypeAAS", "AccountsPage", null);
		} else {

			//return d.findElement(By.xpath("//div[@class='edit-account-form-box']/div[2]/div[1]/div[3]/div[2]/span[1]"));
	
			return SeleniumUtil.getVisibileWebElement(d, "accountTypeFunAAS", "AccountsPage", null);

			
		}
	}
	
	

		
	public WebElement warningMsg() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "warningMsgAAS", "AccountsPage", null);
	}

	
		
	public WebElement deleteConfirmMsg() { 
 		 return SeleniumUtil.getVisibileWebElement(d, "deleteConfirmMsgAAS", "AccountsPage", null);
	}

	public WebElement getNickName(int containerNumber, int subContainerNum, int rowNumber) {

		return d.findElement(By.xpath("//section[@id='fiAndAccountTypesView']//div[contains(@class,'accounts-financial-container inner')]["+ containerNumber+ "]//div[@class='accounts-container']["+ subContainerNum+ "]//div[@class='accounts-row'][" + rowNumber+ "]//span[contains(@class,'Paragraph3')]"));
		
	}

	public WebElement getNickNameNew() {
		//return d.findElement(By.xpath("//div[@class=\"medium-7 large-7 columns acc-row-column1\"]//span[2]"));
		return SeleniumUtil.getWebElement(d, "AccountsPageDagBankNickName", "AccountsPage", null);
	}
	public String getCategoryByRowNum(int rowNumber) {

		WebElement webElement = d.findElement(By.xpath("(//div[@class=\"transaction-row-content-wrap\"])[" +rowNumber+ "]//div//div//span[2]"));

		return webElement.getText();

	}
	
	public WebElement durationFilter(){
		return SeleniumUtil.getWebElement(d, "DurationFilter", "Transaction", null);
	}
	
	public WebElement customFilter(){
		return SeleniumUtil.getWebElement(d, "CustomFilter", "Transaction", null);
	}
	public WebElement settingsIconinSett(){
		return SeleniumUtil.getWebElement(d, "settingsIconinSett", "Transaction", null);
	}
	
	public WebElement customStartDate(){
		return SeleniumUtil.getWebElement(d, "customStartDt", "Transaction", null);
	}
	
	public WebElement customEndDate(){
		return SeleniumUtil.getWebElement(d, "customEndDt", "Transaction", null);
	}
	
	public WebElement updateButton() {
		return SeleniumUtil.getVisibileWebElement(d, "upadatebtn", "Transaction", null);
	}
	
	public void assertSettingsOptionsValue1(int optionNumber, String expectedText) {

		WebElement l = d.findElements(By.xpath("//a[contains(@class,'more-settings-item')]")).get(optionNumber);

		// section[@id='fiAndAccountTypesView']//div[contains(@class,'accounts-financial-container
		// inner')]//div[@class='accounts-container'][1]/div[2]/div[@class='accounts-row'][1]//ul[contains(@id,'financial_settingsdropdown')]/li["
		// + optionNumber + "]/a

		String text = l.getText().trim();

		Assert.assertEquals(text, expectedText);

	}
	
	public WebElement Alert_IH() {
		return SeleniumUtil.getWebElement(d, "Alert_IH", "AlertSetting", null);
	}
	
	public WebElement Alert_IH_Description() {
		return SeleniumUtil.getWebElement(d, "Alert_IH_Description", "AlertSetting", null);
	}
	
	public WebElement Alert_Accounts() {
		SeleniumUtil.waitForPageToLoad(3000);
		return SeleniumUtil.getWebElement(d, "Alert_Accounts", "AlertSetting", null);
		
	}

	public boolean verifyAccountsinAlerts(String propValue) {
		SeleniumUtil.waitForPageToLoad(3000);
		List<WebElement> list=SeleniumUtil.getWebElements(d, "Alert_LOC_CMA_Accounts", "AlertSetting", null);
		boolean status=CommonUtils.assertContainsListElements(propValue, list);
		return status;
	}
	

}
