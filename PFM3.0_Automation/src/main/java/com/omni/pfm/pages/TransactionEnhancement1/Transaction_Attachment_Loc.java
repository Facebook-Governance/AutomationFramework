/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

/*import OmniAuto.pages.Accounts.Page;
import OmniAuto.util.Util;*/

public class Transaction_Attachment_Loc {

	static Logger logger = LoggerFactory.getLogger(Transaction_Attachment_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public Transaction_Attachment_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement AttachmentIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "AttachmentIcon_TA", "Transaction", null);
	}

	public WebElement AttachmentLink() {
		return SeleniumUtil.getVisibileWebElement(d, "AttachmentLink_TA", "Transaction", null);
	}

	public WebElement attachmentPassword() {
		return SeleniumUtil.getVisibileWebElement(d, "attachmentPassword", "Transaction", null);
	}

	public WebElement RemoveAttachmentLink() {
		return SeleniumUtil.getVisibileWebElement(d, "RemoveAttachmentLink_TA", "Transaction", null);
	}

	public List<WebElement> AttachmentList() {
		return SeleniumUtil.getWebElements(d, "AttachmentList_TA", "Transaction", null);
	}

	public List<WebElement> PDFAttachment() {
		return SeleniumUtil.getWebElements(d, "PDFAttachment_TA", "Transaction", null);
	}

	public WebElement popUpHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpHeader_TA", "Transaction", null);
	}

	public WebElement newpopUpHeader_TA() {
		return SeleniumUtil.getVisibileWebElement(d, "newpopUpHeader_TA", "Transaction", null);
	}

	public WebElement ErrorpopUpHeader_TA() {
		return SeleniumUtil.getVisibileWebElement(d, "ErrorpopUpHeader_TA", "Transaction", null);
	}

	public WebElement attachedFile() {
		return SeleniumUtil.getVisibileWebElement(d, "attachedFile_TA", "Transaction", null);
	}

	public WebElement deleteIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteIcon_TA", "Transaction", null);
	}

	public WebElement removeLink() {
		return SeleniumUtil.getVisibileWebElement(d, "removeLink_TA", "Transaction", null);
	}

	public WebElement close() {
		return SeleniumUtil.getVisibileWebElement(d, "close_TA", "Transaction", null);
	}

	public WebElement unable() {
		return SeleniumUtil.getVisibileWebElement(d, "unable_TA", "Transaction", null);
	}

	public WebElement ok() {
		return SeleniumUtil.getVisibileWebElement(d, "ok_TA", "Transaction", null);
	}

	public WebElement unsupportMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "unsupportMessage_TA", "Transaction", null);
	}

	public WebElement attachdisable() {
		return SeleniumUtil.getVisibileWebElement(d, "attachdisable_TA", "Transaction", null);
	}

	public WebElement notification() {
		return SeleniumUtil.getVisibileWebElement(d, "notification_TA", "Transaction", null);
	}

	public WebElement attachFile_TA() {
		return SeleniumUtil.getWebElement(d, "attachFile_TA", "Transaction", null);
	}

	public void attachFile(String path) throws AWTException {
		attachFile_TA().sendKeys(path);
	}

}
