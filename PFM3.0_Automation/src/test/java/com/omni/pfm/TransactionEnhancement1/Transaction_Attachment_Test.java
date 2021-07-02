/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Attachment_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Attachment_Test extends TestBase {
	Add_Manual_Transaction_Loc Addmanual_Transaction;
	Series_Recurence_Transaction_Loc Series_Tranc_details;
	Aggregated_Transaction_Details_Loc aggre;
	Transaction_Attachment_Loc attachment;
	AddToCalendar_Transaction_Loc cal;
	WebDriver webDriver = null;
	LoginPage login;
	AccountAddition accountAdd;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Attachment_Test.class);
	public static String userName = "";

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		Series_Tranc_details = new Series_Recurence_Transaction_Loc(d);
		Addmanual_Transaction = new Add_Manual_Transaction_Loc(d);
		attachment = new Transaction_Attachment_Loc(d);
		aggre = new Aggregated_Transaction_Details_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		cal = new AddToCalendar_Transaction_Loc(d);
	}

	@Test(description = "AT-68467,AT-68472:verify attchment icon", priority = 2)
	public void checkTheattachmentIcon() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.scrollToWebElementMiddle(d, aggre.PendingStranctionList().get(0));
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(0));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(0));
		}
		SeleniumUtil.scrollToWebElementMiddle(d, aggre.ShowMore());
		SeleniumUtil.click(aggre.ShowMore());
		Assert.assertTrue(attachment.AttachmentIcon().isDisplayed());
	}

	@Test(description = "AT-68467,AT-68472:verify attachment link", priority = 3, dependsOnMethods = {
			"checkTheattachmentIcon" })
	public void checkTheattachmentLink() throws Exception {
		Assert.assertEquals(attachment.AttachmentLink().getText(),
				PropsUtil.getDataPropertyValue("attachAttachmentLink"));
	}

	@Test(description = "AT-68469:verify attachment password message", priority = 4, dependsOnMethods = {
			"checkTheattachmentIcon" })
	public void checkPasswordMessage() throws Exception {
		Assert.assertEquals(attachment.attachmentPassword().getText(),
				PropsUtil.getDataPropertyValue("attachAttachmentPassword"));
	}

	@Test(description = "AT-68473:verify attachment for posted transaction", priority = 5, dependsOnMethods = {
			"checkTheattachmentIcon" })
	public void checkTheattachmentLinkPostedType() throws Exception {
		if (aggre.isCancelBtnPresent()) {
			SeleniumUtil.click(aggre.cancel());
		}
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(2));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(2));
		}
		SeleniumUtil.click(aggre.ShowMore());
		Assert.assertTrue(attachment.AttachmentIcon().isDisplayed());
		Assert.assertEquals(attachment.AttachmentLink().getText(),
				PropsUtil.getDataPropertyValue("attachAttachmentLink"));
	}

	@Test(description = "AT-68473:verify attachment for pending transaction", priority = 6, dependsOnMethods = {
			"checkTheattachmentIcon" })
	public void checkTheattachmentLinkPending() throws Exception {
		if (aggre.isCancelBtnPresent()) {
			SeleniumUtil.click(aggre.cancel());
		}
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(1));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(1));
		}
		SeleniumUtil.click(aggre.ShowMore());
		Assert.assertTrue(attachment.AttachmentIcon().isDisplayed());
	}

	@Test(description = "AT-68468:verify png file typr attachment", priority = 7, dependsOnMethods = {
			"checkTheattachmentIcon" })
	public void checktheFilePngType() throws Exception {
		// Verify the user can able to attach the files "PDF,GIF,JPEG and PIG
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(0));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(0));
		}
		SeleniumUtil.click(aggre.ShowMore());
		// SeleniumUtil.click(attachment.AttachmentIcon);
		attachment.attachFile(a);
		boolean expected = attachment.AttachmentList().get(0).isDisplayed();
		SeleniumUtil.click(attachment.AttachmentList().get(0));
		SeleniumUtil.click(attachment.RemoveAttachmentLink());
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68468:verify jpg type file attachment", priority = 8, dependsOnMethods = {
			"checktheFilePngType" })
	public void checktheFileJPGType() throws Exception {
		String path2 = System.getProperty("user.dir");
		logger.info(path2 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.jpg");
		String a1 = path2 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.jpg";
		// SeleniumUtil.click(attachment.AttachmentIcon);
		attachment.attachFile(a1);
		boolean expected = attachment.AttachmentList().get(0).isDisplayed();
		SeleniumUtil.waitForPageToLoad(200);
		SeleniumUtil.click(attachment.AttachmentList().get(0));
		SeleniumUtil.click(attachment.RemoveAttachmentLink());
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68468:verify GIF file type attachment", priority = 9, dependsOnMethods = {
			"checktheFileJPGType" })
	public void checktheFileGIFType() throws Exception {
		String path3 = System.getProperty("user.dir");
		logger.info(path3 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.gif");
		String a2 = path3 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.gif";
		// Util.click(attachment.AttachmentIcon);
		SeleniumUtil.waitForPageToLoad();
		attachment.attachFile(a2);
		boolean expected = attachment.AttachmentList().get(0).isDisplayed();
		SeleniumUtil.waitForPageToLoad(200);
		SeleniumUtil.click(attachment.AttachmentList().get(0));
		SeleniumUtil.click(attachment.RemoveAttachmentLink());
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68468,AT-68477:verify PDf file type attachment", priority = 10, dependsOnMethods = {
			"checktheFileGIFType" })
	public void checktheFilePDFType() throws Exception {
		String path4 = System.getProperty("user.dir");
		logger.info(
				path4 + "\\src\\main\\resources\\Attachments\\PFM v3 - Expense Analysis - UISPEC - 13Jan2016.pdf");
		String a3 = path4 + "\\src\\main\\resources\\Attachments\\PFM v3 - Expense Analysis - UISPEC - 13Jan2016.pdf";
		// Util.click(attachment.AttachmentIcon);
		attachment.attachFile(a3);
		boolean expected = attachment.PDFAttachment().get(0).isDisplayed();
		SeleniumUtil.waitForPageToLoad(200);
		SeleniumUtil.click(attachment.PDFAttachment().get(0));
		SeleniumUtil.click(attachment.RemoveAttachmentLink());
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68474:verify attachment popup", priority = 11, dependsOnMethods = { "checktheFilePDFType" })
	public void veriftTheattcahmentPopup() throws Exception {
		// Verfiy the file get opened after click on the image/file
		// Verify the text Remeove Attachment" and Delete icon displayed on top of the
		// file after opening
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		// SeleniumUtil.click(attachment.AttachmentIcon);
		attachment.attachFile(a);
		SeleniumUtil.click(attachment.AttachmentList().get(0));
		Assert.assertEquals(attachment.popUpHeader().getText(),
				PropsUtil.getDataPropertyValue("AttachAttachmentHeader"));
	}

	@Test(description = "verify attached file", priority = 12, dependsOnMethods = { "veriftTheattcahmentPopup" })
	public void veriftTheattcahmentFile() throws Exception {
		Assert.assertTrue(attachment.attachedFile().isDisplayed());
	}

	@Test(description = "AT-68475:verify attachment delete icon", priority = 13, dependsOnMethods = {
			"veriftTheattcahmentPopup" })
	public void veriftTheattcahmentDeleteIcon() throws Exception {
		Assert.assertTrue(attachment.deleteIcon().isDisplayed());
	}

	@Test(description = "AT-68475:verify attachment remove link", priority = 14, dependsOnMethods = {
			"veriftTheattcahmentPopup" })
	public void veriftTheattcahmentRemoveLink() throws Exception {
		Assert.assertTrue(attachment.removeLink().isDisplayed());
	}

	@Test(description = "AT-68476:verify attachment popup close iocn", priority = 15, dependsOnMethods = {
			"veriftTheattcahmentPopup" })
	public void veriftTheattcahmentClose() throws Exception {
		Assert.assertTrue(attachment.close().isDisplayed());
	}

	@Test(description = "AT-68478:verify attachment popup closed when click on close icon", priority = 16, dependsOnMethods = {
			"veriftTheattcahmentPopup" })
	public void veriftTheattcahmentCloseCLick() throws Exception {
		SeleniumUtil.click(attachment.close());
		boolean expected = cal.isElementPresent(attachment.popUpHeader());
		SeleniumUtil.click(attachment.AttachmentList().get(0));
		SeleniumUtil.click(attachment.RemoveAttachmentLink());
		Assert.assertTrue(expected);
	}

	@Test(description = "AT-68479,AT-68480:verify maximum file size to attach", priority = 17)
	public void veriftTheMaxSizeFile() throws Exception {
		// Verify the Error Message "Unable to upload. Size limit exceeded (5MB). when
		// user tries to add the file >5MB size.
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(0));
		} else {
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.click(aggre.ShowMore());
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\142458main_FY07_budget_full.pdf");
		String a = path1 + "\\src\\main\\resources\\Attachments\\142458main_FY07_budget_full.pdf";
		attachment.attachFile(a);
		Assert.assertEquals(attachment.unable().getText(), PropsUtil.getDataPropertyValue("attachUnable"));
	}

	@Test(description = "AT-68481:verify maxinum file size popup", priority = 18, dependsOnMethods = {
			"veriftTheMaxSizeFile" })
	public void veriftTheMaxSizeFilePopUpOk() throws Exception {
		Assert.assertTrue(attachment.ok().isDisplayed());
	}

	@Test(description = "AT-68482:verify maxinum file size popup icon", priority = 19, dependsOnMethods = {
			"veriftTheMaxSizeFile" })
	public void veriftTheMaxSizeFilePopUpClose() throws Exception {
		Assert.assertTrue(attachment.close().isDisplayed());
	}

	@Test(description = "AT-68482:verify maxinum file size popup closed when click on close icon", priority = 20, dependsOnMethods = {
			"veriftTheMaxSizeFile" })
	public void veriftTheMaxSizeFilePopUpCloseCLick() throws Exception {
		SeleniumUtil.click(attachment.close());
		// attachment.close.click();
		Assert.assertTrue(cal.isElementPresent(attachment.ok()));
	}

	@Test(description = "AT-68484:verify unsupported file popup header", priority = 21)
	public void veriftTheUnsupportedeFile() throws Exception {
		// Verify the Error pop up window displayed if user tries to add the unsupported
		// files
		// Verify the error message displayed if user tries to add the unsupported
		// filesFile Type not suppported Supported files are PNG,JPEG,GIF and PDF
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(3));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(3));
		}
		SeleniumUtil.click(aggre.ShowMore());
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\income1done.txt");
		String a = path1 + "\\src\\main\\resources\\Attachments\\income1done.txt";
		attachment.attachFile(a);
		Assert.assertEquals(attachment.popUpHeader().getText(), PropsUtil.getDataPropertyValue("AttchpopErorrHeader"));
	}

	@Test(description = "AT-68485:verify unsupported file popup info message", priority = 22, dependsOnMethods = {
			"veriftTheUnsupportedeFile" })
	public void veriftTheUnsupportedeFilePopUpMessage() throws Exception {
		Assert.assertEquals(attachment.unsupportMessage().getText(),
				PropsUtil.getDataPropertyValue("AttachunsupportMessage"));
	}

	@Test(description = "AT-68486:verify unsupported file popup closed when click on ok ", priority = 23, dependsOnMethods = {
			"veriftTheUnsupportedeFile" })
	public void veriftTheUnsupportedeFilePopUpOk() throws Exception {
		Assert.assertTrue(attachment.ok().isDisplayed());
	}

	@Test(description = "verify unsupported file popup closed when click on close", priority = 24, dependsOnMethods = {
			"veriftTheUnsupportedeFile" })
	public void veriftTheUnsupportedeFilePopUpClose() throws Exception {
		Assert.assertTrue(attachment.close().isDisplayed());
	}

	@Test(description = "AT-68487:verify unsupported file popup closed when click on close", priority = 25, dependsOnMethods = {
			"veriftTheUnsupportedeFile" })
	public void veriftTheUnsupportedeFilePopUpCloseClick() throws Exception {
		attachment.close().click();
		Assert.assertTrue(cal.isElementPresent(attachment.ok()));
	}

	@Test(description = "AT-68487,AT-68483:verify unsupported file popup closed when click on ok", priority = 26, dependsOnMethods = "veriftTheUnsupportedeFilePopUpCloseClick")
	public void veriftTheUnsupportedeFilePopUpOkClick() throws Exception {
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\income1done.txt");
		String a = path1 + "\\src\\main\\resources\\Attachments\\income1done.txt";
		attachment.attachFile(a);
		SeleniumUtil.click(attachment.ok());
		Assert.assertTrue(cal.isElementPresent(attachment.ok()));
	}

	@Test(description = "AT-68470,AT-68471:verify max 3 file to attach", priority = 27)
	public void veriftTheMax3File() throws Exception {
		// Verify the Attachment icon disabled after adding the maximum attachments
		// based on the configuration key
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(3));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(3));
		}
		SeleniumUtil.click(aggre.ShowMore());
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		attachment.attachFile(a);
		String path2 = System.getProperty("user.dir");
		logger.info(path2 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.jpg");
		String a1 = path2 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.jpg";
		attachment.attachFile(a1);
		String path3 = System.getProperty("user.dir");
		logger.info(path3 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.gif");
		String a2 = path3 + "\\src\\main\\resources\\Attachments\\DashBard_ExpanseAnalysis.gif";
		attachment.attachFile(a2);
		Assert.assertTrue(attachment.attachdisable().isDisplayed());
	}

	@Test(description = "AT-68491:verify attchment updated message", priority = 28, dependsOnMethods = {
			"veriftTheMax3File" })
	public void veriftTheAttchmentUpdateMessage() {
		SeleniumUtil.click(aggre.update());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(Addmanual_Transaction.TransactionAdded().getText(),
				PropsUtil.getDataPropertyValue("aggUpdateMessage"));
	}

	@Test(description = "AT-68488,AT-68492,AT-68493:verify attachment notification icon", priority = 29, dependsOnMethods = {
			"veriftTheAttchmentUpdateMessage" })
	public void veriftTheAttchmentNotification() {
		// Verify attachment icon is reflected in the transaction after an attachment is
		// added and attachment icon is removed once all the attachments are removed
		// from the transaction
		Assert.assertTrue(attachment.notification().isDisplayed());
	}

	@Test(description = "AT-68489,AT-68492,AT-68493:verify deleet attachment", priority = 30, dependsOnMethods = {
			"veriftTheAttchmentNotification" })
	public void veriftTheAttchmentdelete() {
		// Verify attachment icon is reflected in the transaction after an attachment is
		// added and attachment icon is removed once all the attachments are removed
		// from the transaction
		if (aggre.isCollapseIconPresent()) {
			SeleniumUtil.click(aggre.collapsIcon().get(3));
		} else {
			SeleniumUtil.click(aggre.PendingStranctionList().get(3));
		}
		SeleniumUtil.click(aggre.ShowMore());
		attachment.AttachmentList().get(0).click();
		attachment.RemoveAttachmentLink().click();
		attachment.AttachmentList().get(0).click();
		attachment.RemoveAttachmentLink().click();
		attachment.AttachmentList().get(0).click();
		attachment.RemoveAttachmentLink().click();
		aggre.update().click();
		Assert.assertTrue(cal.isElementPresent(attachment.notification()));
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(webDriver);
		} catch (Exception e) {
		}
	}
}
