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

import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_GroupValidation_Loc {
	Logger logger=LoggerFactory.getLogger(Accounts_GroupValidation_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Accounts_GroupValidation_Loc(WebDriver d) {
		this.d = d;
	}
	public WebElement viewByGroupTab() {
		return SeleniumUtil.getVisibileWebElement(d, "optionTabs3Locator", pageName, null);
	}
	public WebElement groupType() {
		return SeleniumUtil.getWebElement(d, "grpType", pageName, frameName);
	}
	public WebElement emptyGroupMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "emptyGroupText", pageName, null);
	}
	public WebElement createGroupBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "createBtnLoc", pageName, null);
	}
	public WebElement newAccountGroupPopup() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpHeader", pageName, null);
	}
	public WebElement createOrUpdateGroup() {
		return SeleniumUtil.getVisibileWebElement(d, "createOrUpdateGroupLoc", pageName, null);
	}
	public WebElement groupNameTxtBox() {
		return SeleniumUtil.getVisibileWebElement(d, "groupNameTxtBoxLoc", pageName, null);
	}
	public WebElement successMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "successMsgLoc", pageName, null);
	}
	public void selectCheckBox()

	{
		SeleniumUtil.waitForElement(null, 2000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 0; i < 1; i++) {

			l.get(i).click();

			SeleniumUtil.waitForPageToLoad(1000);

		}
	}
	public void selectCheckBox2()

	{
		SeleniumUtil.waitForElement(null, 2000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 0; i < 2; i++) {

			l.get(i).click();

			SeleniumUtil.waitForPageToLoad(1000);
		}
	}
	public WebElement moreButton() {
		return SeleniumUtil.getVisibileWebElement(d, "moreBtn", pageName, null);
	}
	public WebElement createMoreGroupButton() {
		return SeleniumUtil.getVisibileWebElement(d, "createMoreGroupBtm", pageName, null);
	}
	public WebElement errorDuplicategroupTxt() {
		return SeleniumUtil.getVisibileWebElement(d, "errorDuplicategroup",pageName, null);
	}
	public WebElement closeDialog() {
		return SeleniumUtil.getVisibileWebElement(d, "closeDialog", pageName, null);	
	}
	public WebElement editButton(String name) {
		return d.findElement(By.xpath("//a[contains(@aria-label,'Edit " + name + "')]"));
	}

	public WebElement saveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtnLoc", pageName, null);
	}

	public List<WebElement> getAccountOptionChckBox() {
		return SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);
	}
	public WebElement deleteGroupButton(String name) {
		return d.findElement(By.xpath("//a[contains(@aria-label,'Delete " + name + "')]"));
	}
	public WebElement confirmDeleteText() {
		return SeleniumUtil.getVisibileWebElement(d, "confirmDeleteText", pageName, null);
	}
	public WebElement cancelButtonInGroup() {
		return SeleniumUtil.getWebElement(d, "cancleGroupDelete", pageName, null);
	}
	public WebElement deleteGroupOption() {
		return SeleniumUtil.getWebElement(d, "deleteGroupbtn", pageName, null);
	}
	public WebElement msgDisplayed() {
		return SeleniumUtil.getWebElement(d, "succsesMessage", pageName, null);
	}
	public WebElement accCat() {
		return SeleniumUtil.getWebElement(d, "accCat", pageName, null);
	}
	public WebElement accountNum() {
		return SeleniumUtil.getWebElement(d, "accountNum", pageName, null);
	}

	public WebElement accDagBill() {
		return SeleniumUtil.getWebElement(d, "accounDagBill", pageName, null);
	}
	public WebElement accCatOthers() {
		return SeleniumUtil.getWebElement(d, "accountCatOthers", pageName, null);
	}
	public WebElement accNumber() {
		return SeleniumUtil.getWebElement(d, "accountNo", pageName, null);
	}
	
	//Added By MRQA -- This is specific cobrands which is not having settings so need to delete group from acc page itself.
    public WebElement AccGrpValidation() {
                    return SeleniumUtil.getVisibileWebElement(d, "AccGrpValidation", pageName, null);
    }

}
