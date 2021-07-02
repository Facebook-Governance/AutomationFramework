/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Tag_Loc {
	static Logger logger = LoggerFactory.getLogger(Transaction_Tag_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;

	public Transaction_Tag_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement tagTab() {
		return SeleniumUtil.getWebElement(d, "tagtab", pageName, frameName);
	}

	public WebElement tagIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "tagicon", pageName, frameName);
	}

	public WebElement tagTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "tagtl", pageName, frameName);
	}

	public WebElement tagClose() {
		return SeleniumUtil.getVisibileWebElement(d, "tagcl", pageName, frameName);
	}

	public boolean isMobileTagBackBtnPresent() {
		return PageParser.isElementPresent("MobiletagBack", pageName, frameName);
	}

	public WebElement MobiletagBack() {
		return SeleniumUtil.getWebElement(d, "MobiletagBack", pageName, frameName);
	}

	public WebElement MobileFilterBackButton() {
		return SeleniumUtil.getWebElement(d, "MobileFilterBackButton", pageName, frameName);
	}

	public boolean isMobileFilterBackButtonPresent() {
		return PageParser.isElementPresent("MobileFilterBackButton", pageName, frameName);
	}

	public WebElement searchField() {
		return SeleniumUtil.getVisibileWebElement(d, "Tagsearch", pageName, frameName);
	}

	public WebElement noTag() {
		return SeleniumUtil.getVisibileWebElement(d, "NoTag", pageName, frameName);
	}

	public WebElement searchClear() {
		return SeleniumUtil.getVisibileWebElement(d, "clsearch", pageName, frameName);
	}

	public WebElement noTagValidation() {
		return SeleniumUtil.getVisibileWebElement(d, "NoTagValidation", pageName, frameName);
	}

	public WebElement noTagMachesValidation() {
		return SeleniumUtil.getVisibileWebElement(d, "NoTagMachesValidation", pageName, frameName);
	}

	public WebElement catTab() {
		return SeleniumUtil.getVisibileWebElement(d, "CatTab", pageName, frameName);
	}

	public WebElement catsearchField() {
		return SeleniumUtil.getVisibileWebElement(d, "catsearch", pageName, frameName);
	}

	public WebElement catClear() {
		return SeleniumUtil.getVisibileWebElement(d, "catcl", pageName, frameName);
	}

	public WebElement tranSearch() {
		return SeleniumUtil.getVisibileWebElement(d, "TranSearch", pageName, frameName);
	}

	public List<WebElement> TagList() {
		return SeleniumUtil.getWebElements(d, "Listtag", pageName, frameName);
	}

	public List<WebElement> ListtagTick() {
		return SeleniumUtil.getWebElements(d, "ListtagTick", pageName, frameName);
	}

	public List<WebElement> ListtagHide() {
		return SeleniumUtil.getWebElements(d, "ListtagHide", pageName, frameName);
	}

	public List<WebElement> TagList1() {
		return SeleniumUtil.getWebElements(d, "Listtag1", pageName, frameName);
	}

	public List<WebElement> TagList2() {
		return SeleniumUtil.getWebElements(d, "Listtag2", pageName, frameName);
	}

	public WebElement NoTRansactionTag() {
		return SeleniumUtil.getVisibileWebElement(d, "NoTRansactionTag", pageName, frameName);
	}

	public WebElement clearTagButton() {
		return SeleniumUtil.getVisibileWebElement(d, "ClTag", pageName, frameName);
	}

	public List<WebElement> resetList() {
		return SeleniumUtil.getWebElements(d, "reset", pageName, frameName);
	}

	public WebElement reset() {
		return SeleniumUtil.getWebElement(d, "reset", pageName, frameName);
	}

	public List<WebElement> PendingStranctionList() {
		return SeleniumUtil.getWebElements(d, "pendingtrlist", pageName, frameName);
	}

	public WebElement TagField() {
		return SeleniumUtil.getVisibileWebElement(d, "tagfield", pageName, frameName);
	}

	public WebElement addTagButton() {
		return SeleniumUtil.getVisibileWebElement(d, "add", pageName, frameName);
	}

	public WebElement update() {
		return SeleniumUtil.getVisibileWebElement(d, "save", pageName, frameName);
	}

	public List<WebElement> deletTag() {
		return SeleniumUtil.getWebElements(d, "delet", pageName, frameName);
	}

	public WebElement noTransactionMessage() {
		return SeleniumUtil.getWebElement(d, "NoTransactionMessage", pageName, frameName);
	}

	public List<WebElement> tagNotification() {
		return SeleniumUtil.getWebElements(d, "tagnot", pageName, frameName);
	}

	public WebElement tagLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "TagLabel", pageName, frameName);
	}

	public void creteTag(String name) {
		TagField().sendKeys(name);
		SeleniumUtil.click(addTagButton());

	}

	public List<WebElement> tranDetailsTag() {
		return SeleniumUtil.getWebElements(d, "tranDetailsTag", pageName, frameName);
	}

	public List<WebElement> tranDetailsTagMobile() {
		return SeleniumUtil.getWebElements(d, "tranDetailsTagMobile", pageName, frameName);
	}

	public List<WebElement> tranDetailsTagHL() {
		return SeleniumUtil.getWebElements(d, "tranDetailsTagHL", pageName, frameName);
	}

	public List<WebElement> tranDetailsTagHLMobile() {
		return SeleniumUtil.getWebElements(d, "tranDetailsTagHLMobile", pageName, frameName);
	}

	public List<WebElement> tranMTTag() {
		return SeleniumUtil.getWebElements(d, "tranMTTag", pageName, frameName);
	}

	public List<WebElement> tranMTTagMobile() {
		return SeleniumUtil.getWebElements(d, "tranMTTagMobile", pageName, frameName);
	}

	public List<WebElement> tranMTTagHL() {
		return SeleniumUtil.getWebElements(d, "tranMTTagHL", pageName, frameName);
	}

	public List<WebElement> tranMTTagHLMobile() {
		return SeleniumUtil.getWebElements(d, "tranDetailsTagHLMobile", pageName, frameName);
	}

	public List<WebElement> tranTagClear() {
		return SeleniumUtil.getWebElements(d, "tranTagClear", pageName, frameName);
	}

	public List<WebElement> tranFilterTag() {
		return SeleniumUtil.getWebElements(d, "tranFilterTag", pageName, frameName);
	}

	public List<WebElement> tranFilterTagHL() {
		return SeleniumUtil.getWebElements(d, "tranFilterTagHL", pageName, frameName);
	}

	public WebElement backToTransactionDetailsMobile() {
		return SeleniumUtil.getWebElement(d, "backToTransactionDetailsMobile", pageName, frameName);
	}

	public WebElement crossIconMobile() {
		return SeleniumUtil.getWebElement(d, "closeAddTransacPopup", pageName, frameName);
	}// added by vrinda

	public boolean isbackToTransactionDetailsMobile() {
		return PageParser.isElementPresent("backToTransactionDetailsMobile", pageName, frameName);
	}

	public void tagFilteTagSearch(String tag) {
		searchField().clear();
		searchField().sendKeys(tag);
	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;
		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));
	}

	public void deleteTag(int tag) {
		SeleniumUtil.click(deletTag().get(tag));
	}

	public void clikcReset() {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(reset()));
		SeleniumUtil.click(element);
	}
}
