/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.AccountSettings;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;




import com.omni.pfm.PageProcessor.PageParser;

//import com.omni.pfm.pages.Accounts.FinappContainer;
import com.omni.pfm.utility.SeleniumUtil;

public class RealEstateAccount_Setting_Loc {

	static Logger logger = LoggerFactory.getLogger(RealEstateAccount_Setting_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public RealEstateAccount_Setting_Loc(WebDriver d) {
		this.d = d;

	}

	public WebElement accountText() {
		return SeleniumUtil.getVisibileWebElement(d, "accountText_REA", "AccountsPage", null);
	}

	public WebElement accountSettingsText(int innerContainerNum, int accountRowNum, int settingNumber) {

		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsText_REA", "AccountsPage", null);

	}

	

	public WebElement crossIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "crossIcon_REA", "AccountsPage", null);
	}

	
	public WebElement accountName() {
		return SeleniumUtil.getVisibileWebElement(d, "accountName_REA", "AccountsPage", null);
	}

	
	public WebElement accountNameBox() {
		return SeleniumUtil.getVisibileWebElement(d, "accountNameBox_REA", "AccountsPage", null);
	}

	
	public WebElement nickNameText() {
		return SeleniumUtil.getVisibileWebElement(d, "nickNameText_REA", "AccountsPage", null);
	}

	
	public WebElement nickNameBox() {
		return SeleniumUtil.getVisibileWebElement(d, "nickNameBox_REA", "AccountsPage", null);
	}

	public String realEstateValueText() {

			return SeleniumUtil.getVisibileWebElement(d, "realEstateValueText_REA", "AccountsPage", null).getText();

	}

	public String calculateAutomaticallyText() {

			return SeleniumUtil.getVisibileWebElement(d, "calculateAutomaticallyText_REA", "AccountsPage", null).getText();

	}

	public WebElement calculateAutomaticallyCheckBox() {
		return SeleniumUtil.getWebElement(d, "calculateAutomaticallyCheckBox_REA", "AccountsPage", null);
	}

	public String enterValueManuallyText() {

			return SeleniumUtil.getVisibileWebElement(d, "enterValueManuallyText_REA", "AccountsPage", null).getText();

	}

	public WebElement enterValueManuallyCheckBox() {
		return SeleniumUtil.getWebElement(d, "enterValueManuallyCheckBox_REA", "AccountsPage", null);
	}

	public String calculateUsingZillowText() {
		return SeleniumUtil.getVisibileWebElement(d, "calculateUsingZillowText_REA", "AccountsPage", null).getText();

	}

	public String verifyZillowImportantPagetitle() {

		SeleniumUtil.waitForPageToLoad();

		return SeleniumUtil.getVisibileWebElement(d, "enterValueManuallyCheckBox_REA", "AccountsPage", null).getText();

	}

	public String verifyThirdPartyWebSitetxt() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "verifyThirdPartyWebSitetxt_REA", "AccountsPage", null).getText();

	}

	public String verifyPropertyAddressText() {

		SeleniumUtil.waitForPageToLoad();

		return SeleniumUtil.getVisibileWebElement(d, "verifyPropertyAddressText_REA", "AccountsPage", null).getText();

	}

	public String editAddressText() {

		SeleniumUtil.waitForPageToLoad();

			return SeleniumUtil.getVisibileWebElement(d, "editAddressText_REA", "AccountsPage", null).getText();

	}

	public void ClickEditAddress() {

		SeleniumUtil.waitForPageToLoad();

			WebElement element = SeleniumUtil.getVisibileWebElement(d, "ClickEditAddress_REA", "AccountsPage", null);

		element.click();

	}

	public String VerifyEditAddressText() {

		SeleniumUtil.waitForPageToLoad();

		return SeleniumUtil.getVisibileWebElement(d, "VerifyEditAddressText_REA", "AccountsPage", null).getText();

	}

	public WebElement zipCodeBox() {
		return SeleniumUtil.getVisibileWebElement(d, "zipCodeBox_REA", "AccountsPage", null);
	}

	public WebElement cityStateBox() {
		return SeleniumUtil.getVisibileWebElement(d, "cityStateBox_REA", "AccountsPage", null);
	}

	public void clickVerifyAddress() {
		SeleniumUtil.getVisibileWebElement(d, "clickVerifyAddress_REA", "AccountsPage", null).click();

	}
	
	public WebElement verifyAddress() {
		 return SeleniumUtil.getVisibileWebElement(d, "clickVerifyAddress_REA", "AccountsPage", null);

	}

	public WebElement closeAddAddressFloater() {
		 return SeleniumUtil.getVisibileWebElement(d, "closeAddAddressFloater", "AccountsPage", null);

	}


	public String errorMessageText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "errorMessageText_REA", "AccountsPage", null).getText();

	}

	public WebElement streetAddressBox() {
		return SeleniumUtil.getVisibileWebElement(d, "streetAddressBox_REA", "AccountsPage", null);
	}

	public String verifyMortageAccountText() {

		SeleniumUtil.waitForPageToLoad();

		return SeleniumUtil.getVisibileWebElement(d, "verifyMortageAccountText_REA", "AccountsPage", null).getText().trim();

	}

	public void clickSelectAnAccount() {

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.getVisibileWebElement(d, "clickSelectAnAccount_REA", "AccountsPage", null).click();

	}
	
	public WebElement SelectAnAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "clickSelectAnAccount_REA", "AccountsPage", null);

	}


		public WebElement MortageAccountBox() {
		return SeleniumUtil.getVisibileWebElement(d, "MortageAccountBox_REA", "AccountsPage", null);
	}

	public WebElement hongKongDollarText() {
		return SeleniumUtil.getVisibileWebElement(d, "hongKongDollarText_REA", "AccountsPage", null);
	}
	
	public WebElement australianDollarDollarText() {
		return SeleniumUtil.getVisibileWebElement(d, "AustralianDollarText_REA", "AccountsPage", null);
	}


	public void AddMortageAccount() {

		SeleniumUtil.getVisibileWebElement(d, "AddMortageAccount_REA", "AccountsPage", null).click();

	}

	public String verifyMortageAccount() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "verifyMortageAccount_REA", "AccountsPage", null).getText().trim();

	}

	public void typeEstimatedValue(String value)

	{

		// webDriver.findElement(By.xpath("//div[@id='enter_manual']//div/div[2]/input")).sendKeys(value);

		SeleniumUtil.getVisibileWebElement(d, "typeEstimatedValue_REA", "AccountsPage", null).sendKeys(value);

	}

	

	public WebElement saveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtn_REA", "AccountsPage", null);
	}

	public WebElement deleteAccountLink() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteAccountLink_REA", "AccountsPage", null);
	}

	
	public WebElement deleteBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteBtn_REA", "AccountsPage", null);
	}

	
	public WebElement cancelBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelBtn_REA", "AccountsPage", null);
	}

	
	public WebElement deletePopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "deletePopUp_REA", "AccountsPage", null);
	}

	
	public WebElement deleteconfirmcheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteconfirmcheckBox_REA", "AccountsPage", null);
	}

	public WebElement warningMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "warningMsg_REA", "AccountsPage", null);
	}

	public WebElement deleteConfirmMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteConfirmMsg_REA", "AccountsPage", null);
	}

	public void clickCancelButton() {

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.getVisibileWebElement(d, "clickCancelButton_REA", "AccountsPage", null).click();

	}

	public void clickEnterValueManually() {

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.getVisibileWebElement(d, "clickEnterValueManually_REA", "AccountsPage", null).click();

	}

	public WebElement currencyTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "currencyTextBox_REA", "AccountsPage", null);
	}

	public String getEstimatedValue() {

		SeleniumUtil.waitForPageToLoad();

		
		return SeleniumUtil.getVisibileWebElement(d, "getEstimatedValue_REA", "AccountsPage", null).getText();

	}

	public void clickElipse() {

		SeleniumUtil.waitForPageToLoad();

				SeleniumUtil.getVisibileWebElement(d, "clickElipse_REA", "AccountsPage", null).click();

	}

	public void clickElipse1() {

		SeleniumUtil.waitForPageToLoad();

				SeleniumUtil.getVisibileWebElement(d, "clickElipse1_REA", "AccountsPage", null).click();

	}

	public void clickAccountsettings() {

		SeleniumUtil.waitForPageToLoad();

				SeleniumUtil.getVisibileWebElement(d, "clickAccountsettings_REA", "AccountsPage", null).click();

	}

	public void clickAccountsettings1() {

		SeleniumUtil.waitForPageToLoad();

				SeleniumUtil.getVisibileWebElement(d, "clickAccountsettings1_REA", "AccountsPage", null).click();

	}

	public String getZipCodeText() {

		SeleniumUtil.waitForPageToLoad();

				return SeleniumUtil.getVisibileWebElement(d, "getZipCodeText_REA", "AccountsPage", null).getText().trim();

	}

	public String getOrText() {

		SeleniumUtil.waitForPageToLoad();

				return SeleniumUtil.getVisibileWebElement(d, "getOrText_REA", "AccountsPage", null).getText().trim();

	}

	public String getCityAndStateText() {

		SeleniumUtil.waitForPageToLoad();

				return SeleniumUtil.getVisibileWebElement(d, "getCityAndStateText_REA", "AccountsPage", null).getText().trim();

	}

	public String getStreetAddressText() {

		SeleniumUtil.waitForPageToLoad();

				return SeleniumUtil.getVisibileWebElement(d, "getStreetAddressText_REA", "AccountsPage", null).getText().trim();

	}

		public WebElement verifyAddressText() {
		return SeleniumUtil.getVisibileWebElement(d, "verifyAddressText_REA", "AccountsPage", null);
	}

	public String mortageAccountText() {

		SeleniumUtil.waitForPageToLoad();

			return SeleniumUtil.getVisibileWebElement(d, "mortageAccountText_REA", "AccountsPage", null).getText();

	}

	public String showAccountSummeryText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "showAccountSummeryText_REA", "AccountsPage", null).getText().trim();

	}

	public void clickAddAddress() {

		SeleniumUtil.waitForPageToLoad();
		//SeleniumUtil.getVisibileWebElement(d, "clickAddAddress_REA", "AccountsPage", null).click();
		SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, "clickAddAddress_REA", "AccountsPage", null));

	}

	public String addAddressText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "addAddressText_REA", "AccountsPage", null).getText();

	}
	
	public WebElement addAddress() {

		return SeleniumUtil.getVisibileWebElement(d, "addAddressText_REA", "AccountsPage", null);

	}


	public String addAddressZipCodeText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "addAddressZipCodeText_REA", "AccountsPage", null).getText().trim();

	}

	public WebElement zipCode1TextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "zipCode1TextBox_REA", "AccountsPage", null);
	}

	/*public String addAddresscityAndStateText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "addAddresscityAndStateText_REA", "AccountsPage", null).getText().trim();

	} */
	
	public String addAddresscityAndStateText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "getCityAndStateText_REA", "AccountsPage", null).getText().trim();

	}


	public WebElement cityAndStateTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "cityAndStateTextBox_REA", "AccountsPage", null);
	}

	public String addAddressStreet() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "addAddressStreet_REA", "AccountsPage", null).getText();

	}

	public WebElement streetAddressTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "streetAddressTextBox_REA", "AccountsPage", null);
	}

	public String addAddressOrText() {

		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getVisibileWebElement(d, "addAddressOrText_REA", "AccountsPage", null).getText();

	}

	public WebElement verifyAddressBox() {
		return SeleniumUtil.getVisibileWebElement(d, "verifyAddressBox_REA", "AccountsPage", null);
	}
	
	public WebElement stopRefreshButton() {
		return SeleniumUtil.getVisibileWebElement(d, "stopRefresh", "AccountsPage", null);
	}


	public List<WebElement> accountSettingsText1() {

		return SeleniumUtil.getWebElements(d, "accountSettingsText1", "AccountsPage", null);

	} 
	
	public WebElement accountSettingsText() {

		return SeleniumUtil.getWebElement(d, "accountSettingsText1", "AccountsPage", null);

	}
	
	public WebElement realEstateAccountSettingsText() {

		return SeleniumUtil.getWebElement(d, "accountSettingsText1", "AccountsPage", null);

	}

	
	public void addManualRealEstateAccount(String accountName, String estimatedValue, boolean includeInNw)
			throws AWTException {

			try {

				//WebElement element = d.findElement(By.id("link-account-button-persist"));
				WebElement element = SeleniumUtil.getWebElement(d, "LinkAccount", "AccountsPage", null);
				if (element.isDisplayed()) {
				
					element.click();
					//d.findElement(By.className("realestate-link")).click();
					SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAccountLink", "AccountsPage", null));

				}
			} catch (Exception e) {
				//d.findElement(By.id("sugstedAccBtnTxt")).click();
				SeleniumUtil.click(SeleniumUtil.getWebElement(d, "SuggestedLinkAccount", "AccountsPage", null));
				//d.findElement(By.xpath("//a[@aria-label='Link Real estate']")).click();
				SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAccountLink", "AccountsPage", null));

				Reporter.log(e.getMessage());
			}

	//	}
		
		SeleniumUtil.waitForPageToLoad(2500);

		
		SeleniumUtil.getWebElement(d, "RealEstateManulAccountName", "AccountsPage", null).sendKeys(accountName);
		
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAccountValueManual", "AccountsPage", null));

		
		SeleniumUtil.getWebElement(d, "RealEstateManulAccountEstimatedValue", "AccountsPage", null).sendKeys(estimatedValue);

		if (!includeInNw) {
			d.findElement(By.xpath("//div[contains(@class,'switch toggleSwitch')]")).click();
		}

		SeleniumUtil.waitForPageToLoad(2500);
		
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountNextLink", "AccountsPage", null));
		
		if(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountSkip", "AccountsPage", null) != null) {
			
			
			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountSkip", "AccountsPage", null));
			SeleniumUtil.waitForPageToLoad(5000);
			
			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountAllDone", "AccountsPage", null));
			
		}
		SeleniumUtil.waitForPageToLoad(6000);
		/*if(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountAllDone", "AccountsPage", null) != null){
			SeleniumUtil.click(SeleniumUtil.getWebElement(d, "RealEstateManulAddAccountAllDone", "AccountsPage", null));
		} */
		
		
		
		//d.findElement(By.xpath("//div[@class='flContainer FL']//div[contains(@class,'headerTitle')]/div[3]//i")).click();
		
		
		SeleniumUtil.waitForPageToLoad();
		
		d.navigate().refresh();
		
		SeleniumUtil.waitForPageToLoad();
		
		//FinappContainer dashBoard=new FinappContainer();
		//dashBoard.accounts();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(6000);

		
		
	}

	public String getDate() {

		Date dNow = new Date();

		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");

		Calendar c = Calendar.getInstance();

		// c.setTime(dNow);

		c.add(Calendar.DATE, 2);

		// dNow = c.getTime();

		return ft.format(dNow).toString();

	}


}
