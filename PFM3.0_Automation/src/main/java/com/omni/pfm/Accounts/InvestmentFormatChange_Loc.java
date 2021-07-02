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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class InvestmentFormatChange_Loc {

	static Logger logger = LoggerFactory.getLogger(InvestmentFormatChange_Loc.class);
	public WebDriver d = null;
	static String pageName = "AccountsPage";
	static String frameName = null;

	public InvestmentFormatChange_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement investmentTxt() {
		return SeleniumUtil.getWebElement(d, "invstTxt", pageName, frameName);
	}
	public WebElement acctTypeMobile() {
		return SeleniumUtil.getWebElement(d, "acctTypeMobile", pageName, frameName);
	}
	public WebElement accountType() {
		return SeleniumUtil.getWebElement(d, "accountType", pageName, frameName);
	}
	public WebElement FIView() {
		return SeleniumUtil.getWebElement(d, "FIView", pageName, frameName);
	}
	public boolean subHeader() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "secondaryTitle", pageName, frameName);
		boolean status=CommonUtils.assertContainsListElements("SubHeader_Investment", l);
		return status;
	}
	public WebElement getAcntText() {
		return SeleniumUtil.getWebElement(d, "investmentSpan", pageName, frameName);
	}
	public boolean groupNameIsVisible() {
		return getAcntText().isDisplayed();
	}
	public WebElement groupTypeMobile() {
		return SeleniumUtil.getWebElement(d, "grpTypeM", pageName, frameName);
	}
	public WebElement groupType() {
		return SeleniumUtil.getWebElement(d, "grpType", pageName, frameName);
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

	public void selectCheckBox()

	{
		SeleniumUtil.waitForElement(null, 2000);
		List<WebElement> l = SeleniumUtil.getWebElements(d, "chkbxwrp", pageName, frameName);
		for (int i = 0; i < l.size(); i++) {

			l.get(i).click();

			SeleniumUtil.waitForPageToLoad(1000);
		}
	}
	public WebElement createUpdateGroupBtm() {
		return SeleniumUtil.getWebElement(d, "createUpdateGroup", pageName, frameName);
	}
	public WebElement getInvestTextGrp() {
		WebElement el = null;
		try {
			new WebDriverWait(d, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@class,'auto_main_header')]")));
			el = SeleniumUtil.getWebElement(d, "autoMainHeader", pageName, frameName);
		} catch (Exception e) {
			System.out.println("Could not find InvestTextGrp in method verifyInvstAcnt_GroupType_View ");
		}
		return el;
	}

	public void subHeaderGrpType(String[] expectedOption)
	{
		List<WebElement> l = SeleniumUtil.getWebElements(d, "autogroupHeader", pageName, frameName);
		SeleniumUtil.waitForPageToLoad(1000);

		String actualOption[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {

			actualOption[i] = l.get(i).getText().trim();

			Assert.assertEquals(actualOption[i].toLowerCase().trim(), expectedOption[i].toLowerCase().trim());

		}

	}

	public List<WebElement> rowContainerList(String subContainerNum) {

		String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "rowContainerList");
		webElement = webElement.replaceAll("subContainerNum", subContainerNum);
		return d.findElements(By.xpath(webElement));
	}

	public String charitableAcc(String containerNumber, String subContainer, String rowContainer) {
		String webElement = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "ContainerGenericXpath");
		webElement = webElement.replaceAll("mainContainer", containerNumber);
		webElement = webElement.replaceAll("subContainer", subContainer);
		webElement = webElement.replaceAll("rowContainer", rowContainer);
		return webElement;
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

	public void verifyingInvestmentHeaderInAcntTypeView() {
		String investMentGroupName = null;
		WebElement invstGroup = getInvestTextGrp();
		if (invstGroup != null)
			investMentGroupName = invstGroup.getText().trim().toLowerCase();
		Assert.assertEquals(investMentGroupName,
				PropsUtil.getDataPropertyValue("InvestmentGroupName").trim().toLowerCase());
	}

	public List<WebElement> containerList() {
		return SeleniumUtil.getWebElements(d, "containerList", pageName, frameName);
	}

	public List<WebElement> containerListSize() {
		return SeleniumUtil.getWebElements(d, "containerListInFI", pageName, frameName);
	}

	public void verifyContainerList() {
		int ActualSize = containerList().size();
		String expected = PropsUtil.getDataPropertyValue("ActualContainerListSize").trim();
		int expectedSize = Integer.parseInt(expected);
		Assert.assertEquals(ActualSize, expectedSize);
	}

	public void verifyContainerListInFI(String[] expectedList) {
		int ActualSize = containerListSize().size();
		String expected = PropsUtil.getDataPropertyValue("ActualContainerListSize").trim();
		int expectedSize = Integer.parseInt(expected);

		for (int i = 0; i < ActualSize; i++) {
			String actualText = containerListSize().get(i).getText().trim();
			Assert.assertTrue(actualText.contains(expectedList[i]));
		}

		Assert.assertEquals(ActualSize, expectedSize);
	}

	public void verifyContainerInAcntGroup(String[] expectedList) {

		int ActualSize = containerListSize().size();
		String expected = PropsUtil.getDataPropertyValue("ActualContainerListSizeInGroupView").trim();
		int expectedSize = Integer.parseInt(expected);

		for (int i = 0; i < ActualSize; i++) {
			String actualText = containerListSize().get(i).getText().trim();
			Assert.assertTrue(actualText.contains(expectedList[i]));
		}
		Assert.assertEquals(ActualSize, expectedSize);
	}

	public WebElement deleteIcon() {
		return SeleniumUtil.getWebElement(d, "deleteIcon", pageName, frameName);
	}

	public WebElement deleteBtn() {
		return SeleniumUtil.getWebElement(d, "deleteGroupBtn", pageName, frameName);
	}
	
	public List<WebElement> AcntsThreeViews() {
		return SeleniumUtil.getWebElements(d, "AcntsThreeViews", pageName, frameName);
	}
	
	
	

}