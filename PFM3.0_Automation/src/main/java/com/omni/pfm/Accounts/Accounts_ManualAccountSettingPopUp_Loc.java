/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ManualAccountSettingPopUp_Loc {
	Logger logger= LoggerFactory.getLogger(Accounts_ManualAccountSettingPopUp_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_ManualAccountSettingPopUp_Loc(WebDriver d) {
		this.d=d;
	}

	public WebElement ManualAcntNameLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNameLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntNameTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNameTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntNickNameLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNickNameLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntNickNameTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNickNameTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntAcntTypeLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntAcntTypeLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntTypeTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntTypeTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntMemoLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntMemoLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntMemoTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntMemoTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntNumberLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNumberLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntNumberTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNumberTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntTagAllTransactnLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntTagAllTransactnLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntTagAllTransactn() {
		return SeleniumUtil.getWebElement(d, "ManualAcntTagAllTransactn", "AccountsPage", null);
	}
	public WebElement ManualAcntAddBtn() {
		return SeleniumUtil.getWebElement(d, "ManualAcntAddBtn", "AccountsPage", null);
	}
	public WebElement ManualCatAllTransactionLbl() {
		return SeleniumUtil.getWebElement(d, "ManualCatAllTransactionLbl", "AccountsPage", null);
	}
	public List<WebElement> ManualCatAllTransactionDD() {
		return SeleniumUtil.getWebElements(d, "ManualCatAllTransactionDD", "AccountsPage", null);
	}
	public WebElement ManualAcntBalCurrencyLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntBalCurrencyLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntBalCurrencyDD() {
		return SeleniumUtil.getWebElement(d, "ManualAcntBalCurrencyTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntCurrentBalLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntCurrentBalLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntCurrentBalTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntCurrentBalTxtBox", "AccountsPage", null);
	}
	public WebElement showAcntInSummaryToggle() {
		return SeleniumUtil.getWebElement(d, "showAcntInSummaryToggle", "AccountsPage", null);
	}
	public WebElement ManualAcntURLLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntURLLbl", "AccountsPage", null);
	}
	public WebElement ManualAcntURLTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntURLTxtBox", "AccountsPage", null);
	}
	public WebElement ManualAcntUserNameLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntUserNameLbl", pageName, frameName);
	}
	public WebElement ManualAcntUserNameTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntUserNameTxtBox", pageName, frameName);
	}
	public WebElement ManualAcntPasswordLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntPasswordLbl", pageName, frameName);
	}
	public WebElement ManualAcntPasswordTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntPasswordTxtBox", pageName, frameName);
	}
	public WebElement ManualAcntShowAcntInSummaryLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntShowAcntInSummaryLbl", pageName, frameName);
	}
	public WebElement ManualAcntShowAcntInSummaryToggle() {
		return SeleniumUtil.getWebElement(d, "ManualAcntShowAcntInSummaryToggle", pageName, frameName);
	}
	public WebElement ManualAcntDeleteBtn() {
		return SeleniumUtil.getWebElement(d, "ManualAcntDeleteBtn", pageName, frameName);
	}
	public WebElement ManualAcntSaveChanges() {
		return SeleniumUtil.getWebElement(d, "ManualAcntSaveChanges", pageName, frameName);
	}
	public WebElement selectedCatTickMark() {
		return SeleniumUtil.getWebElement(d, "selectedCatTickMark", pageName, frameName);
	}
	public WebElement ManualAcntUnitsLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntUnitsLbl", pageName, frameName);
	}
	public WebElement ManualAcntUnitDD() {
		return SeleniumUtil.getWebElement(d, "ManualAcntUnitDD", pageName, frameName);
	}
	public WebElement ManualAcntAmntDueLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntAmntDueLbl", pageName, frameName);
	}
	public WebElement ManualAcntAmntDueTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntAmntDueTxtBox", pageName, frameName);
	}
	public WebElement ManualAcntFrequencyLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntFrequencyLbl", pageName, frameName);
	}
	public WebElement ManualAcntFrequencyDD() {
		return SeleniumUtil.getWebElement(d, "ManualAcntFrequencyDD", pageName, frameName);
	}
	public WebElement frequencyDDValue() {
		return SeleniumUtil.getWebElement(d, "frequencyDDValue", pageName, frameName);
	}
	public WebElement ManualAcntNextDueLbl() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNextDueLbl", pageName, frameName);
	}
	public WebElement ManualAcntNextDueTxtBox() {
		return SeleniumUtil.getWebElement(d, "ManualAcntNextDueTxtBox", pageName, frameName);
	}
	public WebElement ManualIsAssetLiabilityLbl() {
		return SeleniumUtil.getWebElement(d, "ManualIsAssetLiabilityLbl", pageName, frameName);
	}
	public WebElement ManualPopUpError() {
		return SeleniumUtil.getWebElement(d, "ManualPopUpError", pageName, frameName);
	}
	public WebElement ManualCurrentBalError() {
		return SeleniumUtil.getWebElement(d, "ManualCurrentBalError", pageName, frameName);
	}
	public WebElement ManualDueAmtError() {
		return SeleniumUtil.getWebElement(d, "ManualDueAmtError", pageName, frameName);
	}
	public WebElement manualNickNameAdded() {
		return SeleniumUtil.getWebElement(d, "manualNickNameAdded", pageName, frameName);
	}
	public List<WebElement> freqDDList() {
		return SeleniumUtil.getWebElements(d, "freqDDList", pageName, frameName);
	}
	
	public void verifyFreqDDValues() {
		String expected[]=PropsUtil.getDataPropertyValue("FreqDDValues").split(",");
		for(int i=0;i<freqDDList().size();i++) {
			String actual=freqDDList().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}
	
	public WebElement CalenderIcon() {
		return SeleniumUtil.getWebElement(d, "CalenderIcon", pageName, frameName);
	}
	
	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public WebElement ManualassetLabel() {
		return SeleniumUtil.getWebElement(d, "ManualassetLabel", pageName, frameName);
	}
	public WebElement ManualLiabilityLabel() {
		return SeleniumUtil.getWebElement(d, "ManualLiabilityLabel", pageName, frameName);
	}
	public WebElement CalculateAutomaticallyRB() {
		return SeleniumUtil.getWebElement(d, "CalculateAutomaticallyRB", "AccountSettings", frameName);
	}
	public WebElement EnterManuallyRB() {
		return SeleniumUtil.getWebElement(d, "EnterManuallyRB", "AccountSettings", frameName);
	}
	public List<WebElement> ZiloNoticePopupFooter() {
		return SeleniumUtil.getWebElements(d, "ZiloNoticePopupFooter", "AccountSettings", frameName);
	}
	public WebElement ZiloLogo() {
		return SeleniumUtil.getWebElement(d, "ZiloLogo", "AccountSettings", frameName);
	}
	public WebElement ZiloNoticePopupHeader() {
		return SeleniumUtil.getWebElement(d, "ZiloNoticePopupHeader", "AccountSettings", frameName);
	}
	public void verifyZiloNoticeContent(){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "ZiloNoticeContent", "AccountSettings", frameName);
	    String expected[]=PropsUtil.getDataPropertyValue("ZiloNoticeContent").split(":");
	    
	    for(int i=0;i<l.size();i++){
	    	String actual=l.get(i).getText().trim();
	    	Assert.assertEquals(actual, expected[i]);
	    }
	}
	public boolean isCancelButtonDisplayed(){
		return ZiloNoticePopupFooter().get(1).isDisplayed();
	}
	public boolean isContinueButtonDisplayed(){
		return ZiloNoticePopupFooter().get(2).isDisplayed();
	}
	public void clickingOnCancelBtn(){
		SeleniumUtil.click(ZiloNoticePopupFooter().get(1));
	}
	public WebElement ManualAcntShowAcntInSummaryToggle1() {
		return SeleniumUtil.getWebElement(d, "ManualAcntShowAcntInSummaryToggle1", pageName, frameName);
	}
	public WebElement accountslayout() {
		return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
	}
	
}
