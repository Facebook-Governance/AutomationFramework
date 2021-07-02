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

import com.omni.pfm.Accounts.Account_ZeroAccGroups_Loc;

import com.omni.pfm.utility.SeleniumUtil;

public class Account_ZeroAccGroups_Loc {


	private static final Logger logger = LoggerFactory.getLogger(Account_ZeroAccGroups_Loc.class);
	public static WebDriver d=null;

	public Account_ZeroAccGroups_Loc(WebDriver d) {
		// TODO Auto-generated constructor stub
		this.d=d;
	}
	public WebElement gotoSettingsFinAppTab() {
		return SeleniumUtil.getWebElement(d, "Settings", "DashboardPage", null);
	}

	public List<WebElement> AccGrpChecBox() {

		return SeleniumUtil.getWebElements(d, "AccGrpChecBox", "AccountsPage", null);
	}
	public WebElement AccGroupMenu() {

		return SeleniumUtil.getWebElement(d, "AccGroupMenu", "AccountsPage", null);
	}

	public WebElement createAccGroup() {

		return SeleniumUtil.getWebElement(d, "createGroup", "AccountsPage", null);
	}

	public WebElement createGroupIcon() {

		return SeleniumUtil.getWebElement(d, "createGroupIcon", "AccountsPage", null);
	}
	public WebElement GroupName() {

		return SeleniumUtil.getWebElement(d, "Groupname", "AccountsPage", null);
	}

	public List<WebElement> AccountstoInclude() {

		return SeleniumUtil.getWebElements(d, "AcctoIncl", "AccountsPage", null);
	}


	public WebElement createGroupbtn() {

		return SeleniumUtil.getWebElement(d, "creategroupbtn", "AccountsPage", null);
	}
	public WebElement CreateGpBtnUnderSettFin() {

		return SeleniumUtil.getWebElement(d, "CreateGpBtnUnderSettFin", "AccountsPage", null);
	}
	public WebElement AccSettingsMenu() {

		return SeleniumUtil.getWebElement(d, "AccSettingsMenu", "AccountsPage", null);
	}
	public WebElement deleteSite() {

		return SeleniumUtil.getWebElement(d, "deleteSite", "AccountsPage", null);
	}
	public WebElement ConfirmDelCheckbox() {

		return SeleniumUtil.getWebElement(d, "ConfirmDelCheckbox", "AccountsPage", null);
	}

	public WebElement Deletebtn() {

		return SeleniumUtil.getWebElement(d, "Deletebtn", "AccountsPage", null);
	}

	public WebElement DeleteIcon() {

		return SeleniumUtil.getWebElement(d, "DeleteIcon", "AccountsPage", null);
	}

	public WebElement SettingsIconinAccSetOpt() {

		return SeleniumUtil.getWebElement(d, "SettingsIconinAccSetOpt", "AccountsPage", null);
	}

	public WebElement AccSetinMoreOptionbtn() {

		return SeleniumUtil.getWebElement(d, "AccSetinMoreOptbtn", "AccountsPage", null);
	}

	public WebElement DeleteIconandText() {

		return SeleniumUtil.getWebElement(d, "DeleteIconandText", "AccountsPage", null);
	}


	public WebElement ConfirmDelCheck() {

		return SeleniumUtil.getWebElement(d, "ConfirmDelCheck", "AccountsPage", null);
	}
	public WebElement NoAccinGrpMessg() {

		return SeleniumUtil.getWebElement(d, "NoAccinGrpMessg", "AccountsPage", null);
	}

	public List<WebElement> ClickonanyAccGrp() {

		return SeleniumUtil.getWebElements(d, "ClickonanyAccGrp", "AccountsPage", null);
	}

	public WebElement NoAccingrpMessg() {

		return SeleniumUtil.getWebElement(d, "NoAccingrpMessg", "AccountsPage", null);
	}

	public WebElement AddedAccGroupName() {

		return SeleniumUtil.getWebElement(d, "addedAccinGrp", "AccountsPage", null);
	}

	public WebElement deletedAccGrpName() {

		return SeleniumUtil.getWebElement(d, "deletedAccGrpName", "AccountsPage", null);
	}

	public WebElement AccwithZeroAccMessage() {

		return SeleniumUtil.getWebElement(d, "AccwithZeroAccMessage", "AccountsPage", null);
	}

	public WebElement AccHeader() {

		return SeleniumUtil.getWebElement(d, "AccHeader", "AccountsPage", null);
	}

	public WebElement VerifyDelOpIcon() {

		return SeleniumUtil.getWebElement(d, "VerifyDelOpIcon", "AccountsPage", null);
	}

	public WebElement VerifyCancelBtn() {

		return SeleniumUtil.getWebElement(d, "VerifyCancelBtn", "AccountsPage", null);
	}

	public WebElement VerifyEditOpIcon() {

		return SeleniumUtil.getWebElement(d, "VerifyEditOpIcon", "AccountsPage", null);
	}

	public WebElement VerifyCloseBtn() {

		return SeleniumUtil.getWebElement(d, "VerifyCloseBtn", "AccountsPage", null);
	}

	public WebElement groupNamefield() {

		return SeleniumUtil.getWebElement(d, "groupNamefield", "AccountsPage", null);
	}

	public WebElement SaveChangesbtn() {

		return SeleniumUtil.getWebElement(d, "SaveChangesbtn", "AccountsPage", null);
	}

	public WebElement VerifyinAccGroup() {

		return SeleniumUtil.getWebElement(d, "accGroupinAccFin", "AccountsPage", null);
	}

	public WebElement accPlaceHolder() {

		return SeleniumUtil.getWebElement(d, "accPlaceHolder", "AccountsPage", null);
	}


	public List<WebElement> AccsListinPlaceHolder() {

		return SeleniumUtil.getWebElements(d, "AccsListinPlaceHolder", "AccountsPage", null);
	}

	public WebElement FirstAcc() {

		return SeleniumUtil.getWebElement(d, "FirstAcc", "AccountsPage", null);
	}

	public WebElement createAccGrpPopup() {

		return SeleniumUtil.getWebElement(d, "createAccGrpPopup", "AccountsPage", null);
	}

	public WebElement afterGroupDeleteCheck(String groupName) {
		return d.findElement(By.xpath("//ul[@role='tablist']//a[contains(text,'" +groupName+ "')]"));
		//	return d.findElement(By.xpath()))
	}

	public WebElement GroupNameList() {

		return SeleniumUtil.getWebElement(d, "GroupNameList", "AccountsPage", null);
	}

	public WebElement toasterMessge() {

		return SeleniumUtil.getWebElement(d, "toastMessge", "AccountsPage", null);
	}

	public  WebElement deletingGrp() {
		return SeleniumUtil.getWebElement(d, "deletingGrp", "AccountsPage", null);
	}

	public  List<WebElement> AccGroupList() {
		return SeleniumUtil.getWebElements(d, "AccGroupList", "AccountsPage", null);
	}

	public  List<WebElement> AccsetIcon() {
		return SeleniumUtil.getWebElements(d, "AccsetIcon", "AccountsPage", null);
	}

	public  WebElement addedaccinNewGrp() {
		return SeleniumUtil.getWebElement(d, "addedaccinNewGrp", "AccountsPage", null);
	}

	public  WebElement AddedDagSiteinAccSet() {
		return SeleniumUtil.getWebElement(d, "AddedDagSiteinAccSet", "AccountsPage", null);
	}

	public  List<WebElement> DelAddedAccinAccSetList() {
		return SeleniumUtil.getWebElements(d, "DelAddedAccinAccSetList", "AccountsPage", null);
	}

	public List<WebElement> DelAccIcon() {
		return SeleniumUtil.getWebElements(d, "DelAccIcon", "AccountsPage", null);
	}

	public  WebElement DeletedAcc() {
		return SeleniumUtil.getWebElement(d, "DeletedAcc", "AccountsPage", null);
	}

	public  List<WebElement> AddedsitetoFindAcc() {
		return SeleniumUtil.getWebElements(d, "AddedsitetoFindAcc", "AccountsPage", null);
	}

	public WebElement FindSitefromList() {
		return SeleniumUtil.getWebElement(d, "FindSitefromList", "AccountsPage", null);
	}

	public List<WebElement> Placeholder() {
		return SeleniumUtil.getWebElements(d, "Placeholder", "AccountsPage", null);
	}

	public  WebElement DeliconinAccGrp() {
		return SeleniumUtil.getWebElement(d, "DeliconinAccGrp", "AccountsPage", null);
	}



	public  WebElement delgrpconfirm() {
		return SeleniumUtil.getWebElement(d, "delgrpconfirm", "AccountsPage", null);
	}


	public  WebElement AccGrpList() {
		return SeleniumUtil.getWebElement(d, "AccGrpList", "AccountsPage", null);
	}

	public  WebElement delFinalConfirm() {
		return SeleniumUtil.getWebElement(d, "delFinalConfirm", "AccountsPage", null);
	}

	public  WebElement DagContainer() {
		return SeleniumUtil.getWebElement(d, "DagContainer", "AccountsPage", null);
	}


}
