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

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsViewByAcntType_Loc extends TestBase{
	Logger logger=LoggerFactory.getLogger(AccountsViewByAcntType_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public AccountsViewByAcntType_Loc(WebDriver d) {
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
	public List<WebElement> AccountsAcntTypeMobile() {
		return SeleniumUtil.getWebElements(d, "AccountsAcntTypeMobile", pageName, null);
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
	public boolean verifyingInvSubHeader() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "InvSubContainersNames", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements("SubHeader_Investment", l);
		return status;
	}
	public boolean NameOfHighLevelContainers(String propValue) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsContainerName", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements(propValue, l);
		return status;
	}
	public List<WebElement> rowContainerList(String subContainerNum) {

		String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "rowContainerList");
		webElement = webElement.replaceAll("subContainerNum", subContainerNum);
		return d.findElements(By.xpath(webElement));
	}

	public String charitableAcc(String containerNumber, String subContainer, String rowContainer) {
		if(appFlag.equals("app") || appFlag.equals("emulator")) {
			String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "ContainerGenericXpathMobile");
			webElement = webElement.replaceAll("mainContainer", containerNumber);
			webElement = webElement.replaceAll("subContainer", subContainer);
			webElement = webElement.replaceAll("rowContainer", rowContainer);
			return webElement;
		}else {
			String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "ContainerGenericXpath");
			webElement = webElement.replaceAll("mainContainer", containerNumber);
			webElement = webElement.replaceAll("subContainer", subContainer);
			webElement = webElement.replaceAll("rowContainer", rowContainer);
			return webElement;
		}
	}

	public void VerifyCataegories(String categoryName, String conatinerNum, String subContainer) {

		SeleniumUtil.waitForPageToLoad();
		String actual[] = PropsUtil.getDataPropertyValue(categoryName).split(",");
		int size = rowContainerList(subContainer).size();
		for (int j = 1; j <= size; j++) {
			String charitableAcc = charitableAcc(conatinerNum, subContainer, String.valueOf(j));
			String getText = d.findElement(By.xpath(charitableAcc)).getText();
			System.out.println("Actual==" + getText + " Expected==" + actual[j - 1]);
			Assert.assertTrue(getText.trim().contains(actual[j - 1]));
		}
	}

	public boolean verifyOrderOfAcntContainer(String propValue){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "AccountsContainerAcntType", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements(propValue, l);
		return status;
	}

	public void verifyMaskedFormat(){
		for(int i=0;i<3;i++){
			if(appFlag.equals("app") || appFlag.equals("emulator")) {
				List<WebElement> wm=d.findElements(By.xpath("//div[contains(@class,'favicon-left-space')]//*[@class='ellipsis']"));
				String getText=wm.get(i).getText().trim();
				Assert.assertTrue(getText.contains("x-"));
			}else {
			
			List<WebElement> wb=d.findElements(By.xpath("//*[@autoid='account-type-num']"));
			String getText=wb.get(i).getText().trim();
			Assert.assertTrue(getText.contains("x-"));
			}
		}

	}
}
