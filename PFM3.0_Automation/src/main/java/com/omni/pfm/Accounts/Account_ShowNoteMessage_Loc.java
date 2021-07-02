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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Account_ShowNoteMessage_Loc {

	private static final Logger logger = LoggerFactory.getLogger(Account_ShowNoteMessage_Loc.class);
	public WebDriver d;
//	private String pageName="AccountsPage";

	public Account_ShowNoteMessage_Loc(WebDriver d) {
		// TODO Auto-generated constructor stub
		this.d = d;
	}

	public WebElement AccountsHeader() {
		return SeleniumUtil.getWebElement(d, "AccountsHeader", "AccountsPage", null);
	}

	public List<WebElement> moreOptBtn() {
		return SeleniumUtil.getWebElements(d, "moreOptBtn", "AccountsPage", null);
	}
	public WebElement alert_option() {
		return SeleniumUtil.getWebElement(d, "alert_option", "AccountsPage", null);
	}
	
	public List<WebElement> togglebtn() {
		return SeleniumUtil.getWebElements(d, "togglebtn", "AccountsPage", null);
	}
	public List<WebElement> AccSettinMorBtn() {
		return SeleniumUtil.getWebElements(d, "AccSettinMorBtn", "AccountsPage", null);
	}

	public WebElement AccSetClseBtn() {
		return SeleniumUtil.getWebElement(d, "AccSetClseBtn", "AccountsPage", null);
	}

	public WebElement AccClsMesgbox() {
		return SeleniumUtil.getWebElement(d, "AccClsMesgbox", "AccountsPage", null);
	}

	public List<WebElement> ma_YodleeMorebtn() {
		return SeleniumUtil.getWebElements(d, "ma_YodleeMorebtn", "AccountsPage", null);
	}

	public WebElement clsAccIconText() {
		return SeleniumUtil.getWebElement(d, "clsAccIconText", "AccountsPage", null);

	}
	public WebElement toasterMessage() {

		return SeleniumUtil.getWebElement(d, "toasterMessage", "AccountsPage", null);
	} 
	public WebElement ACC_createGroupbtn() {

		return SeleniumUtil.getWebElement(d, "ACC_createGroupbtn", "AccountsPage", null);
	} 

	public boolean isElementPresent(WebElement element) {
		WebElement webElement = SeleniumUtil.getWebElement(d, "clsAccIconText", "AccountsPage", null);
		if (webElement == null) {
			return false;
		} else {
			return true;
		}
	}

	public WebElement ma_delConBtn() {
		return SeleniumUtil.getWebElement(d, "ma_delConBtn", "AccountsPage", null);
	}

	public WebElement ma_delConCheck() {
		return SeleniumUtil.getWebElement(d, "ma_delConCheck", "AccountsPage", null);
	}

	public WebElement ma_deleteAcc() {
		return SeleniumUtil.getWebElement(d, "ma_deleteAcc", "AccountsPage", null);
	}

	public List<WebElement> AccListinPlcHlder() {
		return SeleniumUtil.getWebElements(d, "AccListinPlcHlder", "AccountsPage", null);
	}

	public WebElement AccGrpViewinAccFin() {
		return SeleniumUtil.getWebElement(d, "AccGrpViewinAccFin", "AccountsPage", null);
	}

	public List<WebElement> ClsAccwithNote() {
		return SeleniumUtil.getWebElements(d, "ClsAccwithNote", "AccountsPage", null);
	}

	public WebElement gotoSettingsFinAppTab() {
		return SeleniumUtil.getWebElement(d, "Settings", "DashboardPage", null);

	}

	public WebElement AccSettingsMenu() {

		return SeleniumUtil.getWebElement(d, "AccSettingsMenu", "AccountsPage", null);
	}

	public WebElement waitForIcon() throws InterruptedException
	{
		return SeleniumUtil.waitUntilPresenceOfElement(d, "noteIcon", "AccountsPage", null, 8000);
	}
	public List<WebElement> NoteIcon() {

		return SeleniumUtil.getWebElements(d, "noteIcon", "AccountsPage", null);
	}

	public List<WebElement> AccwithNoteIconList() {

		return SeleniumUtil.getWebElements(d, "AccwithNoteIconList", "AccountsPage", null);
	}

	public List<WebElement> AccListinPlcHlderinSet() {

		return SeleniumUtil.getWebElements(d, "NoteIconListinSet", "AccountsPage", null);
	}

	public List<WebElement> AccsetIcon() {
		return SeleniumUtil.getWebElements(d, "AccsetIcon", "AccountsPage", null);
	}

	public WebElement ShoworHideAcc() {

		return SeleniumUtil.getWebElement(d, "ShoworHideAcc", "AccountsPage", null);
	}

	public WebElement savechangesinSet() {

		return SeleniumUtil.getWebElement(d, "savechangesinSet", "AccountsPage", null);
	}
	
	
	public WebElement savechangesinAlertSet() {

		return SeleniumUtil.getWebElement(d, "savechangesinAlertSet", "AccountsPage", null);
	}

	public WebElement toastMessge() {

		return SeleniumUtil.getWebElement(d, "toastMessge", "AccountsPage", null);
	}

	public List<WebElement> AccInfoinAccFin() {

		return SeleniumUtil.getWebElements(d, "AccInfoinAccFin", "AccountsPage", null);
	}

	public List<WebElement> clsedAccInfoinAccFin() {

		return SeleniumUtil.getWebElements(d, "clsedAccInfoinAccFin", "AccountsPage", null);
	}

	public WebElement closedPopup() {

		return SeleniumUtil.getWebElement(d, "closedPopup", "AccountsPage", null);
	}

	public WebElement AccSettingOpinAccFin() {

		return SeleniumUtil.getWebElement(d, "AccSettingOpinAccFin", "AccountsPage", null);
	}

	public WebElement notemessge() {

		return SeleniumUtil.getWebElement(d, "notemessge", "AccountsPage", null);
	}

	public List<WebElement> noteValue() {

		return SeleniumUtil.getWebElements(d, "noteValue", "AccountsPage", null);
	}

	public WebElement clseAcctextIcon() {

		return SeleniumUtil.getWebElement(d, "clseAcctextIcon", "AccountsPage", null);
	}

	public List<WebElement> closeddisclaimersymb() {

		return SeleniumUtil.getWebElements(d, "closeddisclaimersymb", "AccountsPage", null);
	}

	public WebElement clsAccConfirmBtn() {

		return SeleniumUtil.getWebElement(d, "clsAccConfirmBtn", "AccountsPage", null);
	}

	public List<WebElement> ClosedAccinSet() {

		return SeleniumUtil.getWebElements(d, "ClosedAccinSet", "AccountsPage", null);
	}

	public List<WebElement> AccsetIcon1() {

		return SeleniumUtil.getWebElements(d, "AccsetIcon", "AccountsPage", null);
	}

	public List<WebElement> AccinAccPlceHlder() {

		return SeleniumUtil.getWebElements(d, "accinAccPlceHlder", "AccountsPage", null);
	}

	public WebElement CloseIconBtninPopup() {

		return SeleniumUtil.getWebElement(d, "CloseIconBtninPopup", "AccountsPage", null);
	}

	public WebElement notemessgeBox() {

		return SeleniumUtil.getWebElement(d, "notemessgeBox", "AccountsPage", null);
	}

	public WebElement noteMess() {

		return SeleniumUtil.getWebElement(d, "noteMess", "AccountsPage", null);
	}

	public List<WebElement> noteIcon() {

		return SeleniumUtil.getWebElements(d, "noteIcon", "AccountsPage", null);
	}

	public List<WebElement> ClsBtninNoteMsg() {

		return SeleniumUtil.getWebElements(d, "ClsBtninNoteMsg", "AccountsPage", null);
	}

	public List<WebElement> SettingsIconinAccSetOpt() {

		return SeleniumUtil.getWebElements(d, "SettingsIconinAccSetOpt", "AccountsPage", null);
	}

	public List<WebElement> AccDetailinPlcHlder() {

		return SeleniumUtil.getWebElements(d, "AccDetailinPlcHlder", "AccountsPage", null);
	}

	public List<WebElement> NotewithSetIcon() {

		return SeleniumUtil.getWebElements(d, "NotewithSetIcon", "AccountsPage", null);
	}

	public WebElement noteIconMessage() {

		return SeleniumUtil.getWebElement(d, "noteIconMessage", "AccountsPage", null);
	}

	public WebElement getFirstNoteTextEle() {

		return SeleniumUtil.getWebElement(d, "FirstNoteText", "AccountsPage", null);
	}

	public WebElement noteText() {

		return SeleniumUtil.getWebElement(d, "noteText", "AccountsPage", null);
	}

	public List<WebElement> getFirstNoteText() {

		return SeleniumUtil.getWebElements(d, "FirstNoteText", "AccountsPage", null);
	}

	public WebElement AccTypeView() {

		return SeleniumUtil.getWebElement(d, "AccTypeView", "AccountsPage", null);
	}

	public WebElement createGroupIcon() {

		return SeleniumUtil.getWebElement(d, "createGroupIcon", "AccountsPage", null);
	}

	public WebElement groupNamefield() {

		return SeleniumUtil.getWebElement(d, "groupNamefield", "AccountsPage", null);
	}

	public List<WebElement> AccountstoInclude() {

		return SeleniumUtil.getWebElements(d, "AcctoIncl", "AccountsPage", null);
	}

	public WebElement createGroupbtn() {

		return SeleniumUtil.getWebElement(d, "creategroupbtn", "AccountsPage", null);
	}

	public List<WebElement> AddedsitetoFindAcc() {
		return SeleniumUtil.getWebElements(d, "AddedsitetoFindAcc", "AccountsPage", null);
	}

	public WebElement AccGrpView() {

		return SeleniumUtil.getWebElement(d, "AccGrpView", "AccountsPage", null);
	}

	public WebElement AccGroupMenu() {

		return SeleniumUtil.getWebElement(d, "AccGroupMenu", "AccountsPage", null);
	}

	public WebElement CreateGrpsinAccFin() {

		return SeleniumUtil.getWebElement(d, "CreateGrpsinAccFin", "AccountsPage", null);
	}

	public List<WebElement> noteIconinTranFin() {

		return SeleniumUtil.getWebElements(d, "noteIconinTranFin", "AccountsPage", null);
	}

	public WebElement noteShowmoreinTran() {

		return SeleniumUtil.getWebElement(d, "noteShowmoreinTran", "AccountsPage", null);
	}

	public WebElement TextnoteinTran() {

		return SeleniumUtil.getWebElement(d, "text-noteinTran", "AccountsPage", null);
	}

	public WebElement savebtn() {

		return SeleniumUtil.getWebElement(d, "savebtn", "AccountsPage", null);
	}

	public WebElement LinkanAccount() {

		return SeleniumUtil.getWebElement(d, "LinkanAccount", "AccountsPage", null);
	}

	public WebElement manualAccount() {

		return SeleniumUtil.getWebElement(d, "manualAccount", "AccountsPage", null);
	}

	public List<WebElement> ma_account_type() {

		return SeleniumUtil.getWebElements(d, "ma_account_type", "AccountsPage", null);
	}

	public WebElement ma_accountname() {

		return SeleniumUtil.getWebElement(d, "ma_accountname", "AccountsPage", null);
	}

	public WebElement ma_curBal() {

		return SeleniumUtil.getWebElement(d, "ma_curBal", "AccountsPage", null);
	}

	public WebElement ma_Addbtn() {

		return SeleniumUtil.getWebElement(d, "ma_addbtn", "AccountsPage", null);
	}

	public WebElement ma_nextbtn() {

		return SeleniumUtil.getWebElement(d, "ma_nextbtn", "AccountsPage", null);
	}

	public WebElement ma_alldonebtn() {

		return SeleniumUtil.getWebElement(d, "ma_alldonebtn", "AccountsPage", null);
	}

	public List<WebElement> ma_placehlder() {

		return SeleniumUtil.getWebElements(d, "ma_placehlder", "AccountsPage", null);
	}

	public List<WebElement> ma_acctype_cash() {

		return SeleniumUtil.getWebElements(d, "ma_account_type", "AccountsPage", null);
	}

	public WebElement amountField() {
		return SeleniumUtil.getVisibileWebElement(d, "amountField", "AccountsPage", null);
	}

	public WebElement descriptionfield() {
		return SeleniumUtil.getVisibileWebElement(d, "descriptionfield", "AccountsPage", null);
	}

	public WebElement accountdropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "accountdropdown", "AccountsPage", null);
	}

	public List<WebElement> TransacTypeList() {
		return SeleniumUtil.getWebElements(d, "TransacTypeList", "AccountsPage", null);
	}

	public WebElement accTypeDD() {
		return SeleniumUtil.getVisibileWebElement(d, "accTypeDD", "AccountsPage", null);
	}

	public List<WebElement> accountTypesList(int container) {
		return SeleniumUtil.getWebElements(d, "accountTypesList", "AccountsPage", null);
	}

	public WebElement frequencyDD() {
		return SeleniumUtil.getVisibileWebElement(d, "frequencyDD", "AccountsPage", null);
	}

	public List<WebElement> frequencyList() {
		return SeleniumUtil.getWebElements(d, "frequencyList", "AccountsPage", null);
	}

	public WebElement scheduleDate() {
		return SeleniumUtil.getVisibileWebElement(d, "scheduleDate", "AccountsPage", null);
	}

	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public WebElement categoryDD() {
		return SeleniumUtil.getVisibileWebElement(d, "categoryDD", "AccountsPage", null);
	}

	public List<WebElement> categoryList() {
		return SeleniumUtil.getWebElements(d, "categoryList", "AccountsPage", null);
	}

	public WebElement tagDD() {
		return SeleniumUtil.getVisibileWebElement(d, "tagDD", "AccountsPage", null);
	}

	public List<WebElement> tagList() {
		return SeleniumUtil.getWebElements(d, "tagList", "AccountsPage", null);
	}

	public WebElement showMoreBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "showMoreBtn", "AccountsPage", null);
	}

	public WebElement textArea() {
		return SeleniumUtil.getVisibileWebElement(d, "textArea", "AccountsPage", null);
	}

	public WebElement addTransaction(String NoteText) {
		return SeleniumUtil.getVisibileWebElement(d, "addTransaction", "AccountsPage", null);
	}

	public WebElement endDate() {
		return SeleniumUtil.getVisibileWebElement(d, "endDate", "AccountsPage", null);
	}

	public WebElement addTransinTranFinapp() {
		return SeleniumUtil.getWebElement(d, "addTranButinTranFinapp", "AccountsPage", null);
	}

	
	public WebElement ma_AccSettinMorBtn() {
		return SeleniumUtil.getWebElement(d, "ma_AccSettinMorBtn", "AccountsPage", null);
	}

	public WebElement SysTrnCrAccMorBtn() {
		return SeleniumUtil.getWebElement(d, "SysTrnCrAccMorBtn", "AccountsPage", null);
	}

	public WebElement SysTrnCrAccSetOp() {
		return SeleniumUtil.getWebElement(d, "SysTrnCrAccSetOp", "AccountsPage", null);
	}
	
	public WebElement showAcc() {
		return SeleniumUtil.getWebElement(d, "showAcc", "AccountsPage", null);
	}
	public List<WebElement> noteinTranAccList() {
		return SeleniumUtil.getWebElements(d, "noteinTranAccList", "AccountsPage", null);
	}

	
}