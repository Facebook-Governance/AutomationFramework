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

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ViewByFI_Loc {

	static Logger logger = LoggerFactory.getLogger(Accounts_ViewByFI_Loc.class);
	public static WebDriver d =null;
	static WebElement we;
	
	public Accounts_ViewByFI_Loc(WebDriver d)
	{
		Accounts_ViewByFI_Loc.d=d;	
	}
	public List<WebElement> Loans_Acc() {
		return SeleniumUtil.getWebElements(d, "Loans_Acc", "AccountsPage", null);
	}
	
	public List<WebElement> Reward_Acc() {
		return SeleniumUtil.getWebElements(d, "Reward_Acc", "AccountsPage", null);
	}
	
	public WebElement InstitutionView() {
		return SeleniumUtil.getVisibileWebElement(d, "FIView", "AccountsPage", null);
	}
	
	public List<WebElement> accounts_TotalBal_Acc() {
		return SeleniumUtil.getWebElements(d, "accounts_TotalBal_Acc", "AccountsPage", null);
	}
	public WebElement accounts_Type_Acc() {
		return SeleniumUtil.getVisibileWebElement(d, "accounts_Type_Acc", "AccountsPage", null);
	}
	
	public WebElement RewardsContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "RewardsContainer_AVF", "AccountsPage", null);
	}
	
	
	public WebElement refreshIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "refreshIcon_AVF", "AccountsPage", null);
	}
	public List<WebElement> expandedArrow() {
		return SeleniumUtil.getWebElements(d, "expandedArrow", "AccountsPage", null);
	}
	public List<WebElement> collapseArrow() {
		return SeleniumUtil.getWebElements(d, "collapseArrow", "AccountsPage", null);
	}

	public WebElement sum() {

		return d.findElement(By.xpath(".//*[@id='fiAndAccountTypesView']/div/div/div[8]/div[2]/div[1]/div[1]/div[2]/div/span"))
				.findElement(By.xpath(".//*[@id='fiAndAccountTypesView']/div/div/div[8]/div[2]/div[10]/div[1]"));

	}
	public WebElement RefreshIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "RefreshIcon_AVF", "AccountsPage", null);
	}

	public List<WebElement> containerName() {

		return SeleniumUtil.getWebElements(d, "containerName_AVF", "AccountsPage", null);
	}
	public List<WebElement> manualContainerName() {

		return SeleniumUtil.getWebElements(d, "ManualContainerName", "AccountsPage", null);
	}
	
	public WebElement nickNameTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "nickNameTextBoxAAS", "AccountsPage", null);
	}
	
	public WebElement saveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesBtnAAS", "AccountsPage", null);
	}
	
	public void typeNickName() {
		nickNameTextBox().sendKeys(PropsUtil.getDataPropertyValue("NickName"));
	}
	public WebElement nickNameAdded() {
		return SeleniumUtil.getVisibileWebElement(d, "nickNameAdded", "AccountsPage", null);
	}

	public List<WebElement> containerName1() {
		return SeleniumUtil.getWebElements(d, "containerName1_AVF", "AccountsPage", null);
	}


}