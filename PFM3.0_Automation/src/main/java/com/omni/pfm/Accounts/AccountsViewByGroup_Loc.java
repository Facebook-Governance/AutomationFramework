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
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsViewByGroup_Loc {
	Logger logger = LoggerFactory.getLogger(AccountsViewByFI_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public AccountsViewByGroup_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> AccountsContainerName() {
		return SeleniumUtil.getWebElements(d, "AccountsContainerName", pageName, null);
	}

	public List<WebElement> AccountsContainerAcntType() {
		return SeleniumUtil.getWebElements(d, "AccountsContainerAcntType", pageName, null);
	}

	public List<WebElement> AccountsContainerTotalAmt() {
		return SeleniumUtil.getWebElements(d, "AccountsContainerTotalAmt", pageName, null);
	}

	public List<WebElement> AccountsAcntNames() {
		return SeleniumUtil.getWebElements(d, "AccountsAcntNames", pageName, null);
	}

	public List<WebElement> AccountsAcntType() {
		return SeleniumUtil.getWebElements(d, "AccountsAcntType", pageName, null);
	}

	public List<WebElement> AccountsAcntAmount() {
		return SeleniumUtil.getWebElements(d, "AccountsAcntAmount", pageName, null);
	}

	public List<WebElement> AccountsMaxMinDD() {
		return SeleniumUtil.getWebElements(d, "AccountsMaxMinDD", pageName, null);
	}

	public WebElement AccountsSettingUnderMore() {
		return SeleniumUtil.getWebElement(d, "AccountsSettingUnderMore", pageName, null);
	}

	public WebElement AccountsFeatureTourUnderMore() {
		return SeleniumUtil.getWebElement(d, "AccountsFeatureTourUnderMore", pageName, null);
	}

	public WebElement AccountGroupNoDataScreen() {
		return SeleniumUtil.getWebElement(d, "AccountGroupNoDataScreen", pageName, null);
	}

	public List<WebElement> AccountGroupPopupHeader() {
		return SeleniumUtil.getWebElements(d, "AccountGroupPopupHeader", pageName, null);
	}

	public WebElement AccountGroupNameTxtBox() {
		return SeleniumUtil.getWebElement(d, "AccountGroupNameTxtBox", pageName, null);
	}

	public List<WebElement> AccountGroupCheckBox() {
		return SeleniumUtil.getWebElements(d, "AccountGroupCheckBox", pageName, null);
	}

	public WebElement AccountGroupCreateGroupBtn() {
		return SeleniumUtil.getWebElement(d, "AccountGroupCreateGroupBtn", pageName, null);
	}

	public WebElement createGroupUnderMore() {
		return SeleniumUtil.getWebElement(d, "createGroupUnderMore", pageName, null);
	}

	public List<WebElement> AccountGroupDeleteBtn() {
		return SeleniumUtil.getWebElements(d, "AccountGroupDeleteBtn", pageName, null);
	}

	public List<WebElement> AccountGroupEditBtn() {
		return SeleniumUtil.getWebElements(d, "AccountGroupEditBtn", pageName, null);
	}

	public List<WebElement> AccountGroupExpandCollapseBtn() {
		return SeleniumUtil.getWebElements(d, "accounts_btn_Accordian", pageName, null);
	}

	public WebElement AccountGroupCrossIcon() {
		return SeleniumUtil.getWebElement(d, "AccountGroupCrossIcon", pageName, null);
	}

	public WebElement saveChangesAfterUpdatingGroup() {
		return SeleniumUtil.getWebElement(d, "saveChangesAfterUpdatingGroup", pageName, null);
	}

	public WebElement deleteGroupCancelBtn() {
		return SeleniumUtil.getWebElement(d, "deleteGroupCancelBtn", pageName, null);
	}

	public WebElement deleteGroupDeleteBtn() {
		return SeleniumUtil.getWebElement(d, "deleteGroupDeleteBtn", pageName, null);
	}

	public List<WebElement> AcntGroupIconsList() {
		return SeleniumUtil.getWebElements(d, "AcntGroupIconsList", pageName, null);
	}

	public void clickingOnCreateGroupBtn() {
		SeleniumUtil.click(AccountGroupCreateGroupBtn());
	}

	public void clickingOnSubmitGroupBtn() {
		SeleniumUtil.click(saveChangesAfterUpdatingGroup());
	}

	public void creatingGroupWithAllAcnts(String GroupName) {
		clickingOnGroupNameTxtBox();
		AccountGroupNameTxtBox().clear();
		AccountGroupNameTxtBox().sendKeys(GroupName);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(selectAllCB());
	}

	public boolean verifyingInvSubHeader() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsContainerAcntType", pageName, frameName);
		boolean status = CommonUtils.assertContainsListElements("ActualContainerListInGroupView", l);
		return status;
	}

	public void clickingOnDeleteGroupDeleteBtn() {
		SeleniumUtil.click(deleteGroupDeleteBtn());
	}

	public WebElement AccountGroupNoDataScreenDisc() {
		return SeleniumUtil.getWebElement(d, "AccountGroupNoDataScreenDisc", pageName, null);
	}

	public boolean isAccountGroupNoDataScreendIsDisplayed() {
		return AccountGroupNoDataScreen().isDisplayed();
	}

	public void clickingOnGroupNameTxtBox() {
		SeleniumUtil.click(AccountGroupNameTxtBox());
	}

	public List<WebElement> deletePopupContent() {
		return SeleniumUtil.getWebElements(d, "deletePopupContent", pageName, null);
	}

	public WebElement errorDuplicategroupTxt() {
		return SeleniumUtil.getVisibileWebElement(d, "errorDuplicategroup", pageName, null);
	}

	public boolean isCrossIconDisplayed() {
		return AccountGroupCrossIcon().isDisplayed();
	}

	public void clickOnCloseAcntGroupCross() {
		SeleniumUtil.click(AccountGroupCrossIcon());
	}

	public WebElement feedBackMsg() {
		return SeleniumUtil.getWebElement(d, "feedBackMsg", pageName, null);
	}

	public String getPopupHeader() {
		return AccountGroupPopupHeader().get(0).getText().trim();
	}

	public void verifyAcntGroupIconsOrder() {
		String actualIconName[] = PropsUtil.getDataPropertyValue("actualIconNames").trim().split(",");
		for (int i = 0; i < AcntGroupIconsList().size(); i++) {
			String getIconName = AcntGroupIconsList().get(i).getAttribute("id").trim();
			Assert.assertEquals(getIconName, actualIconName[i]);
		}
	}

	public boolean isDeleteGroupCancelBtnDisplayed() {
		return deleteGroupCancelBtn().isDisplayed();
	}

	public void clickingOndeleteGroupCancelBtn() {
		SeleniumUtil.click(deleteGroupCancelBtn());
	}

	public boolean isDeleteGroupDeleteBtnDisplayed() {
		return deleteGroupDeleteBtn().isDisplayed();
	}

	public void clickingOndeleteGroupDeleteBtn() {
		SeleniumUtil.click(deleteGroupDeleteBtn());
	}

	public WebElement specialCharErrorMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "specialCharErrorMsg", "AccountGroups", null);
	}

	public WebElement selectAllCB() {
		return SeleniumUtil.getWebElement(d, "selectAllCB", "AccountGroups", null);
	}

	public WebElement selectAllLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "selectAllLabel", "AccountGroups", null);
	}

	public List<WebElement> selectContainerCB() {
		return SeleniumUtil.getWebElements(d, "selectContainerCB", "AccountGroups", null);
	}

	public List<WebElement> selectContainerAccounts() {
		return SeleniumUtil.getWebElements(d, "selectAccountsCB", "AccountGroups", null);
	}

	public boolean verifyingContainerNameUnderSelectAcnts() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "selectContainerLabel", "AccountGroups", frameName);
		boolean status = CommonUtils.assertEqualsListElements("ContainerList", l);
		return status;
	}

	public void clickOnSelectAllAcntCheckBox() {
		SeleniumUtil.click(selectAllCB());
	}

	public boolean getAccountsUnderContainer(String ContainerIndex, String ContainerAccounts) {
		boolean status = true;
		String indexOfContainer[] = PropsUtil.getDataPropertyValue(ContainerIndex).trim().split(",");
		String containerAccounts[] = PropsUtil.getDataPropertyValue(ContainerAccounts).trim().split(":");

		for (int i = 0; i < indexOfContainer.length; i++) {
			String getXpath = SeleniumUtil.getVisibileElementXPath(d, "AccountGroups", frameName,
					"selectAccountsLabel");
			getXpath = getXpath.replaceAll("containerNumber", indexOfContainer[i]);
			List<WebElement> l = d.findElements(By.xpath(getXpath));
			String expectedAccounts[] = containerAccounts[i].trim().split(",");
			for (int j = 0; j < l.size(); j++) {
				String actual = l.get(j).getText().trim();
				String expected = expectedAccounts[j];
				if (!actual.equals(expected)) {
					status = false;
					Assert.fail("Account names are not matching. Actual ::"+actual+" && Expected ::" + expected);
					break;
				}
			}
		}
		return status;
	}

	public void verifyingdeletPopUpContent() {
		String actual[] = PropsUtil.getDataPropertyValue("deletePopupContent").trim().split(":");
		for (int i = 0; i < deletePopupContent().size(); i++) {
			Assert.assertEquals(deletePopupContent().get(i).getText().trim(), actual[i]);
		}
	}

	public boolean verifyingGroupNameSorting(String groupName) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsContainerName", pageName, frameName);
		boolean status = CommonUtils.assertEqualsListElements(groupName, l);
		return status;
	}

	public void verifyGroupsAreInExpandedForm() {
		for (int i = 0; i < AccountGroupExpandCollapseBtn().size(); i++) {
			String getTitle = AccountGroupExpandCollapseBtn().get(i).getAttribute("Title");
			boolean status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus"));
			Assert.assertTrue(status);
		}
	}

	public void verifyUserCanMinimise() {
		for (int i = 0; i < AccountGroupExpandCollapseBtn().size(); i++) {
			SeleniumUtil.click(AccountGroupExpandCollapseBtn().get(i));
			SeleniumUtil.waitForPageToLoad(3000);
			String getTitle = AccountGroupExpandCollapseBtn().get(i).getAttribute("Title");
			boolean status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus2"));
			
			if(!status) {
				SeleniumUtil.waitForPageToLoad(2000);
				status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus2"));
			}
			
			Assert.assertTrue(status);
		}
	}

	public void verifyUserCanMaximise() {
		for (int i = 0; i < AccountGroupExpandCollapseBtn().size(); i++) {
			SeleniumUtil.click(AccountGroupExpandCollapseBtn().get(i));
			SeleniumUtil.waitForPageToLoad(3000);
			String getTitle = AccountGroupExpandCollapseBtn().get(i).getAttribute("Title");
			boolean status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus"));
			
			if(!status) {
				SeleniumUtil.waitForPageToLoad(2000);
				status = getTitle.contains(PropsUtil.getDataPropertyValue("ArrowStatus"));
			}
			
			Assert.assertTrue(status);
		}
	}

	public void verifyUserCanDelete() {
		int size = AccountGroupDeleteBtn().size();
		for (int i = 0; i < size; i++) {
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(AccountGroupDeleteBtn().get(0));
			SeleniumUtil.waitForPageToLoad(7000);
			clickingOndeleteGroupDeleteBtn();
		}
	}

	public WebElement backToRootFinapp() {
		return SeleniumUtil.getWebElement(d, "backToRootFinapp", "AccountGroups", null);
	}

	public void clickingOnbackToRootFinapp() {
		SeleniumUtil.click(backToRootFinapp());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	public boolean isCreateGroupBtnDisplayed() {
		return AccountGroupCreateGroupBtn().isDisplayed();
	}

	public boolean isAccntGroupNameMobile() {
		return PageParser.isElementPresent("AccntGroupNameMobile", pageName, frameName);
	}

	public WebElement AccntGroupNameMobile() {
		return SeleniumUtil.getWebElement(d, "AccntGroupNameMobile", pageName, null);
	}

	public boolean verifyingInvSubHeaderAccntGroup() {
		if (isAccntGroupNameMobile()) {
			SeleniumUtil.click(AccntGroupNameMobile());
		}
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsContainerAcntType", pageName, frameName);
		boolean status = CommonUtils.assertContainsListElements("ActualContainerListInGroupView", l);
		return status;
	}
}
