/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ReWamped_Loc {
	Logger logger=LoggerFactory.getLogger(Accounts_ReWamped_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName=null;
	
	AccountAddition accountAdd =  new AccountAddition();
	
	
	
	public Accounts_ReWamped_Loc(WebDriver d) {
    this.d=d;
	}
	
	
	public WebElement viewByAccntType() {
		return SeleniumUtil.getVisibileWebElement(d, "viewByAccType_mannAccTest", pageName, null);
	}
	
	public WebElement viewByInstitution() {
		return SeleniumUtil.getVisibileWebElement(d, "viewByInstitution_mannAccTest", pageName, null);
	}
	
	public WebElement viewByAccountGroups() {
		return SeleniumUtil.getVisibileWebElement(d, "viewByAccountGroups_mannAccTest", pageName, null);
	}
	
	
	
	public WebElement rewardsBalance() {
		return SeleniumUtil.getVisibileWebElement(d, "rewardsBalance_mannAccTest", pageName, null);
	}
	
	public WebElement otherLiabilitiesAccBalance() {
		return SeleniumUtil.getVisibileWebElement(d, "otherLiabilitiesAccBalance_mannAccTest", pageName, null);
	}
	
	public WebElement accountSettingsPopUpHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsPopUpHeader_mannAccTest", pageName, null);
	}
	
	public WebElement assetAccountNameUnderAssetContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "assetAccountNameUnderAssetContainer_mannAccTest", pageName, null);
	}
	
	public WebElement lastUpdatedTime() {
		return SeleniumUtil.getVisibileWebElement(d, "lastUpdatedTime_mannAccTest", pageName, null);
	}
	
	// Account Settings page
	
	public WebElement editCredentialsBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "accountSettingsEditCredentialsBtn_mannAccTest", pageName, null);
	}
	
	public WebElement passwordTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "password_mannAccTest", pageName, null);
	}
	
	public WebElement reEnterPasswordTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "reEnterPassword_mannAccTest", pageName, null);
	}
	
	public WebElement editCredentialsUpdateBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "updateBtn_mannAccTest", pageName, null);
	}
	
	public WebElement transactionNoDataScreenHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionNoDataScreenHeader_mannAccTest", pageName, null);
	}
	
	public WebElement transactionNoDataScreenDescription() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionNoDataScreenDescription_mannAccTest", pageName, null);
	}
	
	public WebElement editCredentialsBtnInErrorMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "editCredentialsBtnInErrorMessage_mannAccTest", pageName, null);
	}	
	
	public WebElement accountsNeedAttentionErrorMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "accountsNeedAttentionErrorMsg_mannAccTest", pageName, null);
	}
	
	public List<WebElement> errorIcons() {
		return SeleniumUtil.getWebElements(d, "errorIcons_mannAccTest", pageName, null);
	}
	
	public WebElement smartZipRealEstateDesclaimerMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "smartZipRealEstateDesclaimerMsg_mannAccTest", pageName, null);
	}
	
	public WebElement smartZipRealEstateDesclaimerMsgText() {
		return SeleniumUtil.getVisibileWebElement(d, "smartZipRealEstateDesclaimerMsgText_mannAccTest", pageName, null);
	}
	
	public WebElement investmentAccDesclaimerMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "investmentAccDesclaimerMsg_mannAccTest", pageName, null);
	}
	
	public WebElement showAccountInAccountSummaryChkBox() {
		return SeleniumUtil.getVisibileWebElement(d, "showAccountInAccountSummaryChkBox_mannAccTest", pageName, null);
	}
	
	public WebElement accSettingsSaveBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsSaveBtn_mannAccTest", pageName, null);
	}
	
	// Account Settings pop up elements
	
	public WebElement accSettingsPopUpAccName() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpAccName_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpAccNickName() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpAccNickName_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpRECalculateAutomaticallyRadBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpRECalculateAutomaticallyRadBtn_REAccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpRECalculateAutomaticallyRadBtn1() {
		return SeleniumUtil.getWebElement(d, "accSettingsPopUpRECalculateAutomaticallyRadBtn1_REAccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpRECalculateAutomaticallyDesclaimer() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpRECalculateAutomaticallyDesclaimer_REAccSettingsPopUp", pageName, null);
	}
	
	
	
	public WebElement accSettingsPopUpREEnterValueRadBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpREEnterValueRadBtn_REAccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpREEnterValueDesclaimer() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpREEnterValueDesclaimer_REAccSettingsPopUp", pageName, null);
	}
	
	
	
	public WebElement accSettingsPopUpREPropertyAddress() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpREPropertyAddress_REAccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpREEditAddressBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpREEditAddressBtn_REAccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpRESelectAccDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpRESelectAccDropDown_REAccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpRESelectAccDropDownValue1() {
		return SeleniumUtil.getVisibileWebElement(d, "selectAccDropDownValue1_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpRECurrencyDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "currencyDropDown_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpREEstimatedValueTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "estimatedValueTextBox_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpCloseBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "closeBtn_AccSettingsPopUp", pageName, null);
	}
	
	
	
	
	
	public WebElement accSettingsPopUpShowAccInAccSummary() {
		return SeleniumUtil.getVisibileWebElement(d, "accSettingsPopUpShowAccInAccSummary_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpSaveBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "saveBtn_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement accSettingsPopUpDeleteAccBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteAccBtn_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement backToAccSettingsBtnFromEditAddress() {
		return SeleniumUtil.getVisibileWebElement(d, "backToAccSettingsFromEditAddress_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement editAddressScreenHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "editAddressScreen_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement editAddressZipCodeTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "editAddressZipCode_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement editAddressCityAndStateTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "editAddressCityAndState_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement editAddressStreetAddressTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "editAddressStreetAddress_AccSettingsPopUp", pageName, null);
	}
	
	public WebElement editAddressVerifyAddressBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "editAddressVerifyAddressBtn_AccSettingsPopUp", pageName, null);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<WebElement> getAccountTypeAndNumberUnderContainer(String accountName,String containerName) {
		
		String xpath1 = "";
		String xpath2 = "";
		List<WebElement> we = null;
		
		xpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "accountTypeAndNumberGeneralXpath_mannAccTest");
		
		xpath2 = xpath1.replace("changeAccountName", accountName);
		xpath2 = xpath2.replace("changeContainerName", containerName);
		
		we = d.findElements(By.xpath(xpath2));		
		
		return we;
		
	}
	
	public List<WebElement> getErrorIconsUnderContainer(String accountName,String containerName) {
		
		String xpath1 = "";
		String xpath2 = "";
		List<WebElement> we = null;
		
		xpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "accountErrorIconXpath_mannAccTest");
		
		xpath2 = xpath1.replace("changeAccountName", accountName);
		xpath2 = xpath2.replace("changeContainerName", containerName);
		
		we = d.findElements(By.xpath(xpath2));		
		
		return we;
		
	}
	
	
	
	
	public WebElement getAccountNameUnderContainerInAccTypeView(String accountName,String containerName) {
		
		String xpath1 = "";
		String xpath2 = "";
		WebElement we = null;
		
		xpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "accNameUnderContainerACCOUNTTYPE_mannAccTest");
		
		xpath2 = xpath1.replace("changeAccountName", accountName);
		xpath2 = xpath2.replace("changeContainerName", containerName);
		
		we = d.findElement(By.xpath(xpath2));		
		
		return we;
	}
	
	public WebElement getAccountNumberUnderContainerInAccTypeView(String accountName,String containerName) {
		
		String xpath1 = "";
		String xpath2 = "";
		WebElement we = null;
		
		xpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "accNumberUnderContainerACCOUNTTYPE_mannAccTest");
		
		xpath2 = xpath1.replace("changeAccountName", accountName);
		xpath2 = xpath2.replace("changeContainerName", containerName);
		
		we = d.findElement(By.xpath(xpath2));		
		
		return we;
	}
	
	public void clickAccountSettingsForContainerInAccTypeView(String containerName,String accountName) {
		
		String moreBtnXpath1,accountSettingsXpath1 = "";
		String moreBtnXpath2,accountSettingsXpath2 = "";
		WebElement moreBtn,accountSettingsBtn = null;
		
		moreBtnXpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "moreBtnACCOUNTTYPE_mannAccTest");
		moreBtnXpath2 = moreBtnXpath1.replace("changeAccountName", accountName);		
		moreBtnXpath2 = moreBtnXpath2.replace("changeContainerName", containerName);
		
		accountSettingsXpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "accountsSettingsACCOUNTTYPE_mannAccTest");
		accountSettingsXpath2 = accountSettingsXpath1.replace("changeAccountName", accountName);
		accountSettingsXpath2 = accountSettingsXpath2.replace("changeContainerName", containerName);
		
		moreBtn = d.findElement(By.xpath(moreBtnXpath2));
		
		SeleniumUtil.click(moreBtn);
		SeleniumUtil.waitForPageToLoad();
		
		accountSettingsBtn = d.findElement(By.xpath(accountSettingsXpath2));
		
		SeleniumUtil.click(accountSettingsBtn);
		SeleniumUtil.waitForPageToLoad();
		
	}
	
	
	public WebElement getTotalAccntBalanceUnderContainerInInstitutionView(String accountName,String containerName) {
		
		String xpath1 = "";
		String xpath2 = "";
		WebElement we = null;
		
		xpath1 = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "totalAccntBalanceUnderContainerINSTITUTION_mannAccTest");
		
		xpath2 = xpath1.replace("changeAccountName", accountName);
		xpath2 = xpath2.replace("changeContainerName", containerName);
		
		we = d.findElement(By.xpath(xpath2));		
		
		return we;
	}
	
	public Boolean verifyOnlyTwoDecimal(String amountString) {
		
		Boolean isTrue = true;
		String decimalString = "";
		
		if (null != amountString && amountString.length() > 0 )
		{
		    int endDotIndex = amountString.lastIndexOf(".");
		    decimalString = amountString.substring(endDotIndex+1, amountString.length());
		    
		    logger.info("String after decimal point is:"+decimalString);
		    
		    if(decimalString.length() != 2) {
				isTrue = false;
			}
		    
		}else {
			isTrue = false;
		}
		
		return isTrue;
		
	}
	
	
	public Boolean verifyTwoZeroesAfterDecimal(String amountString) {
		
		Boolean isTrue = true;
		String decimalString = "";
		
		if (null != amountString && amountString.length() > 0 )
		{
		    int endDotIndex = amountString.lastIndexOf(".");
		    decimalString = amountString.substring(endDotIndex+1, amountString.length());
		    
		    logger.info("String after decimal point is:"+decimalString);
		    
		    if(!decimalString.equals("00")) {
				isTrue = false;
			}
		    
		}else {
			isTrue = false;
		}
		
		return isTrue;
		
	}
	
	
	


	public void addRealEstateAccountWithSmartZip(String accName,String streetAddress,String cityAndZIP) {
		
		
		String addRealEstateBtnXpath,reAccNameXpath, reStreetAddressXpath,reCityZipXpath,addBtnXpath,skipBtnXpath,nextBtnXpath = "";
		
		accountAdd.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		
		if (PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("ON")) {
			
			addRealEstateBtnXpath = "//div[contains(@aria-label,'Add Real Estate')]";
			reAccNameXpath = "//input[contains(@id,'accountName')]";
			reStreetAddressXpath = "//input[contains(@id,'streetadress')]";
			reCityZipXpath = "//input[contains(@id,'ZipCode')]";
			
		} else {
			
			addRealEstateBtnXpath = "//a[@aria-label='Real Estate']";
			reAccNameXpath = "//input[contains(@id,'account-name')]";
			reStreetAddressXpath = "//input[contains(@id,'street-address')]";
			reCityZipXpath = "//input[contains(@id,'city-state-zip')]";
			
		}
		
		addBtnXpath = "add-account";
		skipBtnXpath = "skip-link";
		nextBtnXpath = "next";
		
		try {
			d.findElement(By.xpath(addRealEstateBtnXpath)).click();
		}catch(Throwable e) {
			SeleniumUtil.refresh(d);
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
			accountAdd.linkAccount();
			SeleniumUtil.waitForPageToLoad();
			d.findElement(By.xpath(addRealEstateBtnXpath)).click();
		}
		
		SeleniumUtil.waitForPageToLoad(1500);
		
		d.findElement(By.xpath(reAccNameXpath)).sendKeys(accName);

		d.findElement(By.xpath(reStreetAddressXpath)).sendKeys(streetAddress);
		
		d.findElement(By.xpath(reCityZipXpath)).sendKeys(cityAndZIP);
		SeleniumUtil.waitForPageToLoad(1500);
		
		d.findElement(By.id(addBtnXpath)).click();
		SeleniumUtil.waitForPageToLoad(7000);

		SeleniumUtil.click(d.findElement(By.id(skipBtnXpath)));
		SeleniumUtil.waitForPageToLoad();
		
		d.findElement(By.id(nextBtnXpath)).click();
		SeleniumUtil.waitForPageToLoad();

		//d.navigate().refresh();
		
	} 
	
	public WebElement accountslayout() {
		return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
	}
	
	public WebElement acceptTermsAndConditionsCheckbox() {
		return SeleniumUtil.getVisibileWebElement(d, "acceptTermsAndConditionsCheckbox", pageName, null);
	}
	
	public WebElement editCredentialsPopUpCancelBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "editCredentialsPopUpCancelBtn", pageName, null);
	}
	
}
