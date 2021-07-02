/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class Account_Go_To_Site_Loc {

	static Logger logger= LoggerFactory.getLogger(Account_Go_To_Site_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public Account_Go_To_Site_Loc(WebDriver d) {
		this.d = d;
	}



	public WebElement dummyGeneratorHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "dummyGeneratorHeader", pageName, frameName);
	}

	public  boolean checkVisibility(WebElement ele) {
		boolean status=false;
		if(ele==null)
		{
			logger.error("Could not find the element ",ele);
		}
		else{
			try{
				status=ele.isDisplayed();
			}catch(Exception er)
			{
				status=false;
				logger.error("The element is not visible [{}]");
				er.printStackTrace();
			}
		}
		return status;
	}

	public void verifyPopUpWindow() {
		List<String> browserTabs = new ArrayList<String>(d.getWindowHandles());

		d.switchTo().window(browserTabs.get(1));
		boolean headerStatus = dummyGeneratorHeader().getAttribute("src").trim().equalsIgnoreCase(PropsUtil.getDataPropertyValue("DummyAccountGeneratorHeader").trim());
		try {
			d.close();
		} catch (Exception e) {
		}
		d.switchTo().window(browserTabs.get(0));
		Assert.assertTrue(headerStatus, "Header is not Visible");
	}

	public void verifyPopUpWindowAtSiteLevel() {

		SeleniumUtil.click(popupFooterButtons().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		List<String> browserTabs = new ArrayList<String>(d.getWindowHandles());
		d.switchTo().window(browserTabs.get(1));
		boolean headerStatus = dummyGeneratorHeader().getAttribute("src").trim().equalsIgnoreCase(PropsUtil.getDataPropertyValue("DummyAccountGeneratorHeader").trim());
		try {
			d.close();
		} catch (Exception e) {

		}
		d.switchTo().window(browserTabs.get(0));
		Assert.assertTrue(headerStatus, "Header is not Visible");
	}

	public WebElement goToSiteTab() {
		return SeleniumUtil.getVisibileWebElement(d, "goToSiteTab", pageName, frameName);
	}

	public List<WebElement> iconList(){
		return SeleniumUtil.getWebElements(d, "IconList", pageName, frameName);
	}
	public List<WebElement> AccountSetting_Mob(){
		return SeleniumUtil.getWebElements(d, "AccountSetting_Mob", pageName, frameName);
	}
	
	public WebElement dagsiteSettings() {
		return SeleniumUtil.getWebElement(d, "dagsiteSettingsMobile", pageName, frameName);

	}
	public void verifyingListIndexing() {
		
		String actual[]= PropsUtil.getDataPropertyValue("IconList").split(",");
		for(int i=0;i<iconList().size();i++) {
			String expected=iconList().get(i).getText().trim();
			Assert.assertEquals(actual[i], expected);
		}
	}

	public WebElement getCreateGroup() {
		return SeleniumUtil.getWebElement(d, "createGroupBtn", pageName, frameName);

	}

	public WebElement groupName() {
		return SeleniumUtil.getWebElement(d, "groupNm", pageName, frameName);

	}

	public void typeGroupName(String textToBeinseterd) {
		groupName().sendKeys(textToBeinseterd);

	}
	public WebElement createUpdateGroupBtm() {
		return SeleniumUtil.getWebElement(d, "createUpdateGroup", pageName, frameName);

	}
	public void VerifyingGroupType() {

		SeleniumUtil.waitForPageToLoad();
		boolean ContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "Dag Site", "Go to Site");
		boolean ContainerGoToSiteClick = SeleniumUtil.selectDropDown(d,"Dag Site", "Go to Site");
		SeleniumUtil.waitForPageToLoad();
		List<String> browserTabs = new ArrayList<String>(d.getWindowHandles());
		d.switchTo().window(browserTabs.get(1));
		boolean headerStatus = dummyGeneratorHeader().getAttribute("src").trim().equalsIgnoreCase(PropsUtil.getDataPropertyValue("DummyAccountGeneratorHeader").trim());
		try {
			d.close();
		} catch (Exception e) {

		}
		d.switchTo().window(browserTabs.get(0));
		Assert.assertTrue(headerStatus, "Header is not Visible");
		Assert.assertTrue(ContainerGoToSiteVisibility,
				"Go To Site is not visible");
		Assert.assertTrue(ContainerGoToSiteClick,
				"unable to click on go to site");
	}

	public WebElement groupType() {
		return SeleniumUtil.getWebElement(d, "grpType", pageName, frameName);

	}

	public WebElement accountType() {
		return SeleniumUtil.getWebElement(d, "accountType", pageName, frameName);
	}

	public void VerifyingAccountTypeView() {
		SeleniumUtil.waitForPageToLoad();
		boolean checkingAccGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "Dag Site", "Go to Site");
		boolean realEstateGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "RealEstateManual","Go to Site");
		boolean manualAccGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "MyAccountbank","Go to Site");

		SeleniumUtil.selectDropDown(d, "Dag Site", "Go to Site");
		List<String> browserTabs = new ArrayList<String>(d.getWindowHandles());

		d.switchTo().window(browserTabs.get(1));
		boolean headerStatus = dummyGeneratorHeader().getAttribute("src").trim().equalsIgnoreCase(PropsUtil.getDataPropertyValue("DummyAccountGeneratorHeader").trim());
		try {
			d.close();
		} catch (Exception e) {

		}
		d.switchTo().window(browserTabs.get(0));
		Assert.assertTrue(headerStatus, "Header is not Visible");
		Assert.assertTrue(checkingAccGoToSiteVisibility,
				"Go To Site is not Visible");
		Assert.assertFalse(realEstateGoToSiteVisibility,
				"Got to site is visible in real Estate Accounts, it is bug");
		Assert.assertFalse(manualAccGoToSiteVisibility,
				"Got to site is visible in manual Accounts, it is bug");

	}
	public WebElement loanAccount() {
		return SeleniumUtil.getWebElement(d, "loanAccount", pageName, frameName);
	}
	public WebElement loanAccountHeader() {
		return SeleniumUtil.getWebElement(d, "loanAccountHeader", pageName, frameName);
	}
	public WebElement expandCollapseArrow() {
		return SeleniumUtil.getWebElement(d, "expandCollapseArrow", pageName, frameName);
	}
	public WebElement expandCollapseArrow_Mob() {
		return SeleniumUtil.getWebElement(d, "expandCollapseArrow_Mob", pageName, frameName);
	}
	public boolean isexpandCollapseArrow_Mob() {
		return PageParser.isElementPresent("expandCollapseArrow_Mob", pageName, frameName);
	}
	public WebElement principleAmountTxt() {
		return SeleniumUtil.getWebElement(d, "principleAmountTxt", pageName, frameName);
	}
	public WebElement InsuranceAccount() {
		return SeleniumUtil.getWebElement(d, "InsuranceAccount", pageName, frameName);
	}
	public WebElement premiumAmount() {
		return SeleniumUtil.getWebElement(d, "premiumAmount", pageName, frameName);
	}

	public List<WebElement> listOfContainers(){
		return SeleniumUtil.getWebElements(d, "listOfContainers", pageName, frameName);
	}
	public List<WebElement> ManualTab() {
		return SeleniumUtil.getWebElements(d, "ManualTab", pageName, frameName);
	}
	public WebElement manualHeading() {
		return SeleniumUtil.getWebElement(d, "manualHeading", pageName, frameName);
	}

	public List<WebElement> listOfContainersInFI(){
		return SeleniumUtil.getWebElements(d, "listOfContainersInFI", pageName, frameName);
	}
	public WebElement deleteIcon() {
		return SeleniumUtil.getWebElement(d, "deleteIcon", pageName, frameName);
	}
	public WebElement deleteBtn() {
		return SeleniumUtil.getWebElement(d, "deleteGroupBtn", pageName, frameName);
	}

	public void selectCheckBox1()

	{
		SeleniumUtil.waitForElement(null, 1000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);

		for (int i = 0; i < 5; i++) {

			l.get(i).click();

			SeleniumUtil.waitForPageToLoad(1000);

		}

	}

	public void verifyContainersInAcntSetting()
	{
		String expected[] = PropsUtil.getDataPropertyValue("ContainerList").split(",");
		List<String> actual = new ArrayList<String>();
		for(int i=0;i<listOfContainers().size();i++){
			actual.add(listOfContainers().get(i).getText().trim());
			Assert.assertEquals(listOfContainers().get(i).getText().trim(), expected[i]);
		}
		SeleniumUtil.click(ManualTab().get(1));
		boolean status=manualHeading().getText().trim().equals(PropsUtil.getDataPropertyValue("ManualAccHeader").trim());

		String expectedForManual[] = PropsUtil.getDataPropertyValue("ContainerListInManual").split(",");
		List<String> actualForManual = new ArrayList<String>();
		for(int i=0;i<listOfContainers().size();i++){
			actualForManual.add(listOfContainers().get(i).getText().trim());
			Assert.assertEquals(listOfContainers().get(i).getText().trim(), expectedForManual[i]);	
		}
		Assert.assertTrue(status);
	}

	public void verifyContainerInAlertSetting() {
		String expected1[] = PropsUtil.getDataPropertyValue("AlertSettingContainers").split(",");
		List<String> actual1 = new ArrayList<String>();
		for(int i=0;i<listOfContainers().size();i++){
			actual1.add(listOfContainers().get(i).getText().trim());
			Assert.assertEquals(listOfContainers().get(i).getText().trim(), expected1[i]);

		}
	}
	public void verifyContainerInFIView() {
		String expected3[] = PropsUtil.getDataPropertyValue("listOfContainersInFI").split(",");
		List<String> actual3 = new ArrayList<String>();
		for(int i=0;i<listOfContainersInFI().size();i++){
			actual3.add(listOfContainersInFI().get(i).getText().trim());
			Assert.assertEquals(listOfContainersInFI().get(i).getText().trim(), expected3[i]);

		}
	}
	public void verifyContainerInAcntTypeView() {
		String expected4[] = PropsUtil.getDataPropertyValue("ListOfContainersInAccType").split(",");
		List<String> actual4 = new ArrayList<String>();
		for(int i=0;i<listOfContainersInFI().size();i++){
			actual4.add(listOfContainersInFI().get(i).getText().trim());
			Assert.assertEquals(listOfContainersInFI().get(i).getText().trim(), expected4[i]);
		}
	}	

	public void verifyContainerInAcntGroupView() {
		String expected5[] = PropsUtil.getDataPropertyValue("ListOfContainersInGroupType").split(",");
		List<String> actual5 = new ArrayList<String>();
		for(int i=0;i<listOfContainersInFI().size();i++){
			actual5.add(listOfContainersInFI().get(i).getText().trim());
			Assert.assertEquals(listOfContainersInFI().get(i).getText().trim(), expected5[i]);
		}	
	}

	public void verifyContainerInAcntGroupLevel() {
		String expected6[] = PropsUtil.getDataPropertyValue("ListOfContainersInGroupType").split(",");
		List<String> actual6 = new ArrayList<String>();
		for(int i=0;i<listOfContainersInFI().size();i++){
			actual6.add(listOfContainersInFI().get(i).getText().trim());
			Assert.assertEquals(listOfContainersInFI().get(i).getText().trim(), expected6[i]);
		}	
	}
	public List<WebElement> popupFooterButtons() {
		return SeleniumUtil.getWebElements(d, "AccountSetting_AccountCloseConfirm", pageName, frameName);
	}
	public WebElement accountslayout() {
		return SeleniumUtil.getWebElement(d, "accountslayout", pageName, frameName);
	}
	
}
