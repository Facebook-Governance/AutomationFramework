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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountSharing_ReadOnlyFeature_loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(Transaction_AccountSharing_ReadOnlyFeature_loc.class);
	public WebDriver d = null;
	static WebElement we;
	String pageName = "Transaction";
	String frameName = null;
	public static final By addToCalendarIcon = getByObject("Transaction", null,"addcalLink_ATC");
	public static final By transactionDetailsDescription = getByObject("Transaction", null,"transactionDetailsDescription");
	public static final By transactionTagLink = getByObject("Transaction", null,"TransactionTagLink");
	public static final By split_ATD = getByObject("Transaction", null,"split_ATD");
	public static final By transactionDetailsSave = getByObject("Transaction", null,"transactionDetailsSave");
	
	//transactionDetailsSave

	public Transaction_AccountSharing_ReadOnlyFeature_loc(WebDriver d) {
		this.d = d;
	}

	public WebElement transactionDetailsDescription() {
		return getWebElement(d, "transactionDetailsDescription", pageName, frameName);
	}

	public WebElement transactionDetailsDescriptionMobile() {
		return getWebElement(d, "transactionDetailsDescriptionMobile", pageName, frameName);
	}

	public boolean istransactionDetailsDescriptionMobile() {
		return PageParser.isElementPresent("transactionDetailsDescriptionMobile", pageName, frameName);
	}

	public List<WebElement> transactionDetailsDescriptionList() {
		return getWebElements(d, "transactionDetailsDescription", pageName, frameName);
	}

	public WebElement transactionDetailsMoreOption() {
		return getWebElement(d, "transactionDetailsMoreOption", pageName, frameName);
	}

	public List<WebElement> transactionDetailsMoreOptionlist() {
		return getWebElements(d, "transactionDetailsMoreOption", pageName, frameName);
	}

	public WebElement transactionDetailsNote() {
		return getWebElement(d, "transactionDetailsNote", pageName, frameName);
	}

	public WebElement transactionDetailsSplit() {
		return getWebElement(d, "transactionDetailsSplit", pageName, frameName);
	}

	public WebElement transactionDetailsSave() {
		return getWebElement(d, "transactionDetailsSave", pageName, frameName);
	}

	public List<WebElement> transactionDetailsSaveList() {
		return getWebElements(d, "transactionDetailsSave", pageName, frameName);
	}

	public WebElement attachFile_TA() {
		return getWebElement(d, "attachFile_TA", pageName, frameName);
	}

	public List<WebElement> pendingStranctionList() {
		return getWebElements(d, "PendingStranctionList_ATD", pageName, frameName);
	}

	public void attachFile(String path) throws AWTException {
		attachFile_TA().sendKeys(path);
	}

	public WebElement TransactionTagLink() {
		return getWebElement(d, "TransactionTagLink", pageName, frameName);
	}

	public List<WebElement> TransactionTagLinkList() {
		return getWebElements(d, "TransactionTagLink", pageName, frameName);
	}

	public List<WebElement> split_ATDList() {
		return getWebElements(d, "split_ATD", pageName, frameName);
	}

	public WebElement TransactionTagField() {
		return getWebElement(d, "TransactionTagField", pageName, frameName);
	}

	public WebElement TransactionTagFieldMobile() {
		return getWebElement(d, "TransactionTagFieldMobile", pageName, frameName);
	}

	public boolean isTransactionTagFieldMobile() {
		return PageParser.isElementPresent("TransactionTagFieldMobile", pageName, frameName);
	}

	public WebElement MobiletagBack() {
		return getWebElement(d, "MobiletagBack", pageName, frameName);
	}

	public WebElement TransactionTagCreate() {
		return getWebElement(d, "TransactionTagCreate", pageName, frameName);
	}

	public WebElement transactionDetailsAmount() {
		return getWebElement(d, "transactionDetailsAmount", pageName, frameName);
	}

	public List<WebElement> projectedStranctionList() {
		return getWebElements(d, "projectedStranctionList_SRT", "pageName", frameName);
	}

	public WebElement transactionDetailsReadonlyAmountLabel() {
		return getWebElement(d, "transactionDetailsReadonlyAmountLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyAmountValue() {
		return getWebElement(d, "transactionDetailsReadonlyAmountValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyDescriptionLabel() {
		return getWebElement(d, "transactionDetailsReadonlyDescriptionLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyDescriptionValue() {
		return getWebElement(d, "transactionDetailsReadonlyDescriptionValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyStDescriptionLabel() {
		return getWebElement(d, "transactionDetailsReadonlyStDescriptionLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyStDescriptionValue() {
		return getWebElement(d, "transactionDetailsReadonlyStDescriptionValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyAccountLabel() {
		return getWebElement(d, "transactionDetailsReadonlyAccountLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyAccounttValue() {
		return getWebElement(d, "transactionDetailsReadonlyAccounttValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyDateLabel() {
		return getWebElement(d, "transactionDetailsReadonlyDateLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyDateValue() {
		return getWebElement(d, "transactionDetailsReadonlyDateValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyCategoryLabel() {
		return getWebElement(d, "transactionDetailsReadonlyCategoryLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyScheduledDateLabel() {
		return getWebElement(d, "transactionDetailsReadonlyScheduledDateLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyScheduledDateValue() {
		return getWebElement(d, "transactionDetailsReadonlyScheduledDateValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyFrequencyLabel() {
		return getWebElement(d, "transactionDetailsReadonlyFrequencyLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyFrequencyValue() {
		return getWebElement(d, "transactionDetailsReadonlyFrequencyValue", pageName, frameName);
	}

	public WebElement transactionDetailsCloseButton() {
		return getWebElement(d, "transactionDetailsCloseButton", pageName, frameName);
	}

	public List<WebElement> transactionDetailsColapsed() {
		return getWebElements(d, "transactionDetailsColapsedagg", pageName, frameName);
	}

	public List<WebElement> transactionBudgetHeader() {
		return getWebElements(d, "transactionBudgetHeader", pageName, frameName);
	}

	public List<WebElement> transactionDetailsColapsedSeries() {
		return getWebElements(d, "transactionDetailsColapsedSeries", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyCategoryValue() {
		return getWebElement(d, "transactionDetailsReadonlyCategoryValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyTagLabel() {
		return getWebElement(d, "transactionDetailsReadonlyTagLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyTagValue() {
		return getWebElement(d, "transactionDetailsReadonlyTagValue", pageName, frameName);
	}

	public List<WebElement> RemoveAttachmentLink() {
		return getWebElements(d, "RemoveAttachmentLink_TA", "Transaction", null);
	}

	public WebElement transactionDetailsReadonlyNoteLabel() {
		return getWebElement(d, "transactionDetailsReadonlyNoteLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyNoteValue() {
		return getWebElement(d, "transactionDetailsReadonlyNoteValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyAttachmentLabel() {
		return getWebElement(d, "transactionDetailsReadonlyAttachmentLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlySymbolLabel() {
		return getWebElement(d, "transactionDetailsReadonlySymbolLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlySymbolValue() {
		return getWebElement(d, "transactionDetailsReadonlySymbolValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyCUSLabel() {
		return getWebElement(d, "transactionDetailsReadonlyCUSLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyCUSValue() {
		return getWebElement(d, "transactionDetailsReadonlyCUSValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyInvestmentTypeLabel() {
		return getWebElement(d, "transactionDetailsReadonlyInvestmentTypeLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyInvestmentTypeValue() {
		return getWebElement(d, "transactionDetailsReadonlyInvestmentTypeValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyPriceLabel() {
		return getWebElement(d, "transactionDetailsReadonlyPriceLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyPriceValue() {
		return getWebElement(d, "transactionDetailsReadonlyPriceValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyQuantityLabel() {
		return getWebElement(d, "transactionDetailsReadonlyQuantityLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyQuantityValue() {
		return getWebElement(d, "transactionDetailsReadonlyQuantityValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyHoaldingLabel() {
		return getWebElement(d, "transactionDetailsReadonlyHoaldingLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyHoaldingValue() {
		return getWebElement(d, "transactionDetailsReadonlyHoaldingValue", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyLotLabel() {
		return getWebElement(d, "transactionDetailsReadonlyLotLabel", pageName, frameName);
	}

	public WebElement transactionDetailsReadonlyLotValue() {
		return getWebElement(d, "transactionDetailsReadonlyLotValue", pageName, frameName);
	}

	public List<WebElement> transactionDetailsReadonlyAttachmentImage() {
		return getWebElements(d, "transactionDetailsReadonlyAttachmentImage", pageName, frameName);
	}

	public WebElement AddManulTransactionTag() {
		return getVisibileWebElement(d, "AddManulTransactionTag", "Transaction", null);
	}

	public WebElement AddManualTransactTag() {
		return getVisibileWebElement(d, "AddManualTransactTag", "Transaction", null);
	}

	public WebElement AddManualTransactTagMobile() {
		return getVisibileWebElement(d, "TransactionTagFieldMobile", "Transaction", null);
	}

	public boolean isAddManualTransactTagMobile() {
		return PageParser.isElementPresent("TransactionTagFieldMobile", "Transaction", null);
	}

	public WebElement AddManualTransactTagCreate() {
		return getWebElement(d, "AddManualTransactTagCreate", "Transaction", null);
	}

	public WebElement AddManualTransactTagCreateMobile() {
		return getWebElement(d, "AddManualTransactTagCreateMobile", "Transaction", null);
	}

	public WebElement transactionEventShowMoreOption() {
		return getWebElement(d, "transactionEventShowMoreOption", pageName, frameName);
	}

	public WebElement transactionEventNote() {
		return getWebElement(d, "transactionEventNote", pageName, frameName);
	}

	public WebElement transactionEventAdd() {
		return getWebElement(d, "transactionEventAdd", pageName, frameName);
	}

	public WebElement transactionDetailsMarchantLable() {
		return getWebElement(d, "transactionDetailsMarchantLable", pageName, frameName);
	}

	public WebElement transactionDetailsMarchantValue() {
		return getWebElement(d, "transactionDetailsMarchantValue", pageName, frameName);
	}

	public List<WebElement> getAllAmount1() {
		By projectedTransactionExpandIcon = getByObject(pageName, frameName, "ProExpandIcon");
		if (isDisplayed(projectedTransactionExpandIcon, 1)) {
			click(projectedTransactionExpandIcon);
		}
		return getWebElements(d, "Series_getAllAmount1", "Transaction", null);
	}

	public WebElement ProjectedExapdIcon() {
		return getWebElement(d, "ProExpandIcon", pageName, frameName);
	}

	public WebElement allradioButtonText() {
		return getVisibileWebElement(d, "allradioButtonText_SRT", "Transaction", null);
	}

	public WebElement addcalLink() {
		return getVisibileWebElement(d, "addcalLink_ATC", "Transaction", null);
	}

	public List<WebElement> addcalLinkList() {
		return getWebElements(d, "addcalLink_ATC", "Transaction", null);
	}

	public WebElement STUpdatePopUpConfirm() {
		return getWebElement(d, "STUpdatePopUpConfirm", "Transaction", null);
	}

	public WebElement transactionDetailsCheckField() {
		return getWebElement(d, "transactionDetailsCheckField", "Transaction", null);
	}

	public WebElement transactionDetailsReadonlyCheckLabel() {
		return getWebElement(d, "transactionDetailsReadonlyCheckLabel", "Transaction", null);
	}

	public WebElement transactionDetailsReadonlyCheckValue() {
		return getWebElement(d, "transactionDetailsReadonlyCheckValue", "Transaction", null);
	}

	public WebElement endDate() {
		return getVisibileWebElement(d, "endDate_ATC", "Transaction", null);
	}

	public void createTag(String tagName) {
		waitForPageToLoad();
		waitUntilToastMessageIsDisappeared();
		click(TransactionTagLink());
		if (isTransactionTagFieldMobile()) {
			TransactionTagFieldMobile().sendKeys(tagName);
			click(TransactionTagCreate());
			TransactionTagFieldMobile().sendKeys(Keys.TAB);
		} else {
			TransactionTagField().sendKeys(tagName);
			click(TransactionTagCreate());
		}
	}

	public void createTag1(String tagName) {
		waitForPageToLoad(1000);
		TransactionTagField().sendKeys(tagName);
		click(TransactionTagCreate());
		waitForPageToLoad(1000);
	}

	public List<WebElement> getAllPostedAmount1() {
		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedAmount1_AMT");
		return d.findElements(By.xpath(abC));
	}

	public void editPostedTransaction(int txnRow, String txnDescription, String txnTag, String txnNote,
			String txnAttamentFilePath, String searchAmount) throws AWTException {
		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			waitForPageToLoad();
			if (getAllPostedAmount1().get(i).getText().trim().equals(searchAmount)) {
				click(getAllPostedAmount1().get(i));
				waitForPageToLoad();
				break;
			}
		}
		transactionDetailsDescription().clear();
		transactionDetailsDescription().sendKeys(txnDescription);
		createTag(txnTag);
		click(transactionDetailsMoreOption());
		waitForPageToLoad(1000);
		transactionDetailsNote().clear();
		transactionDetailsNote().sendKeys(txnNote);
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + txnAttamentFilePath;
		attachFile(a);
		waitForPageToLoad();
		click(transactionDetailsSave());
		waitFor(2);
		waitForPageToLoad();
		waitUntilToastMessageIsDisappeared();
	}

	public void editPeojectedSeriesTransaction(int txnRow, String txnAmount, String txnTag, String txnNote,
			String searchAmount) throws AWTException {
		logger.info("edit the peojectedSeries transaction with amount,tag,note");
		if (getAllAmount1().get(0) == null) {
			click(ProjectedExapdIcon());
		}
		for (int i = 0; i < getAllAmount1().size(); i++) {
			if (getAllAmount1().get(i).getText().trim().equals(searchAmount)) {
				click(getAllAmount1().get(i));
				waitForPageToLoad();
				break;
			}
		}
		click(allradioButtonText());
		waitForPageToLoad(1000);
		// click(projectedStranctionList().get(txnRow));
		waitForPageToLoad(1000);
		transactionDetailsAmount().clear();
		transactionDetailsAmount().sendKeys(txnAmount);
		createTag(txnTag);
		click(transactionDetailsMoreOption());
		waitForPageToLoad(1000);
		transactionDetailsNote().clear();
		transactionDetailsNote().sendKeys(txnNote);
		waitForPageToLoad();
		click(transactionDetailsSave());
		click(STUpdatePopUpConfirm());
	}

	public void editPeojectedinstanceTransaction(int txnRow, String txnAmount, String txnTag, String txnNote,
			String txncheck, String txnAttamentFilePath, String searchAmount) throws AWTException {
		logger.info("edit the projected instance transaction with amount,tag,note,attachmentfile");
		waitForPageToLoad(1000);
		if (getAllAmount1().get(0) == null) {
			click(ProjectedExapdIcon());
		}
		int transactionSize = getAllAmount1().size();
		waitForPageToLoad(1000);
		for (int i = 0; i < transactionSize; i++) {
			waitForPageToLoad(1000);
			if (getAllAmount1().get(i).getText().trim().equals(searchAmount)) {
				waitForPageToLoad(1000);
				click(getAllAmount1().get(i));
				waitForPageToLoad(7000);
				break;
			}
		}
		// click(projectedStranctionList().get(txnRow));
		waitForPageToLoad(1000);
		transactionDetailsAmount().clear();
		transactionDetailsAmount().sendKeys(txnAmount);
		createTag(txnTag);
		click(transactionDetailsMoreOption());
		waitForPageToLoad(1000);
		transactionDetailsNote().clear();
		transactionDetailsNote().sendKeys(txnNote);
		transactionDetailsCheckField().clear();
		transactionDetailsCheckField().sendKeys(txncheck);
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + txnAttamentFilePath;
		attachFile(a);
		waitForPageToLoad();
		click(transactionDetailsSave());
	}

	public void addTagAndAttachmentPostedTransaction(int txnRow, String txnTag1, String txnTag2,
			String txnAttamentFilePath, String txnAttamentFilePath1) throws AWTException {
		logger.info("add more than one tag  and attachment the posted trabsaction ");
		click(pendingStranctionList().get(txnRow));
		createTag(txnTag1);
		createTag1(txnTag2);
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + txnAttamentFilePath;
		attachFile(a);
		waitForPageToLoad();
		String projectPath1 = System.getProperty("user.dir");
		String a1 = projectPath1 + txnAttamentFilePath1;
		attachFile(a1);
		waitForPageToLoad();
		click(transactionDetailsSave());
	}

	public void addTagSeriesTransaction(int txnRow, String txnTag1, String txnTag2) throws AWTException {
		logger.info("add more than one tagthe series trabsaction");
		click(pendingStranctionList().get(txnRow));
		createTag(txnTag1);
		createTag1(txnTag2);
		waitForPageToLoad();
		click(transactionDetailsSave());
	}

	public void addTagAndAttachmentInstanceTransaction(int txnRow, String txnTag1, String txnTag2,
			String txnAttamentFilePath, String txnAttamentFilePath1) throws AWTException {
		logger.info("add more than one tag and attachment the instance trabsaction ");
		click(pendingStranctionList().get(txnRow));
		createTag(txnTag1);
		createTag1(txnTag2);
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + txnAttamentFilePath;
		attachFile(a);
		waitForPageToLoad();
		String projectPath1 = System.getProperty("user.dir");
		String a1 = projectPath1 + txnAttamentFilePath1;
		attachFile(a1);
		waitForPageToLoad();
		click(transactionDetailsSave());
	}

	public void createEventtag(String tags) {
		click(AddManulTransactionTag());
		if (isAddManualTransactTagMobile()) {
			AddManualTransactTagMobile().clear();
			AddManualTransactTagMobile().sendKeys(tags);
			click(AddManualTransactTagCreateMobile());
			MobiletagBack();
		} else {
			AddManualTransactTag().clear();
			AddManualTransactTag().sendKeys(tags);
			click(AddManualTransactTagCreate());
		}
	}

	public void createEventtagnew(String tags) {
		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tags);
		click(AddManualTransactTagCreate());
	}

	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public void createEvent(int txnRow, String txnTag, String txnNote, String searchAmount) throws AWTException {
		logger.info("create event ");
		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			if (getAllPostedAmount1().get(i).getText().trim().equals(searchAmount)) {
				waitUntilToastMessageIsDisappeared();
				click(getAllPostedAmount1().get(i));
				waitForPageToLoad();
				break;
			}
		}
		click(addcalLink());
		waitForPageToLoad();
		endDate().clear();
		endDate().sendKeys(this.targateDate1(66));
		waitForPageToLoad();
		createEventtag(txnTag);
		click(transactionEventShowMoreOption());
		waitForPageToLoad(1000);
		transactionEventNote().clear();
		transactionEventNote().sendKeys(txnNote);
		waitForPageToLoad();
		click(transactionEventAdd());
		waitFor(2);
		waitForPageToLoad();
		waitUntilToastMessageIsDisappeared();
	}

	public WebElement transactionDetailsAsscociateAccount() {
		return getWebElement(d, "transactionDetailsAsscociateAccount", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardSave() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardSave", pageName, frameName);
	}

	public List<WebElement> transactionDetailsAsscociateAccountcard() {
		return getWebElements(d, "transactionDetailsAsscociateAccountcard", "Transaction", null);
	}

	public void createAssociateAccount(int txnRow) {
		click(pendingStranctionList().get(txnRow));
		waitForPageToLoad(1000);
		click(transactionDetailsAsscociateAccount());
		waitForPageToLoad();
		click(transactionDetailsAsscociateAccountcard().get(0));
		waitForPageToLoad();
		click(transactionDetailsAsscociateAccountcardSave());
		waitForPageToLoad();
	}

	public String getEveryMonth14ThDate() {
		SimpleDateFormat formate = new SimpleDateFormat("dd");
		Calendar aCalendar = Calendar.getInstance();
		System.out.println(formate.format(aCalendar.getTime()));
		int a = Integer.parseInt(formate.format(aCalendar.getTime()));
		SimpleDateFormat formate1 = new SimpleDateFormat("MM");
		Calendar aCalendar1 = Calendar.getInstance();
		System.out.println(formate1.format(aCalendar1.getTime()));
		SimpleDateFormat formate2 = new SimpleDateFormat("yyyy");
		Calendar aCalendar2 = Calendar.getInstance();
		System.out.println(formate2.format(aCalendar2.getTime()));
		Calendar c = Calendar.getInstance();
		String dateexpected = null;
		if (a >= 14) {
			int b = 14 - a;
			System.out.println(b);
			c.add(Calendar.DATE, b);
			dateexpected = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
		} else {
			int cb = (Integer.parseInt(formate1.format(aCalendar1.getTime())) + 1);
			String d = Integer.toString(cb);
			if (d.length() == 1) {
				String ab = "0" + d + "/14/" + formate2.format(aCalendar2.getTime());
				System.out.println(ab);
				dateexpected = ab;
			} else {
				String ab = d + "/14/" + formate2.format(aCalendar2.getTime());
				System.out.println(ab);
				dateexpected = ab;
			}
		}
		return dateexpected;
	}

	public List<WebElement> getAllcat1() {
		return getWebElements(d, "Series_getAllcat1", "Transaction", null);
	}

	public WebElement transactionDetailsAsscociateAccountcardTitle() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardTitle", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardSite() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardSite", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardSchDatelbl() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardSchDatelbl", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardSchDateValue() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardSchDateValue", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardAccuntlbl() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardAccuntlbl", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardAccuntvalue() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardAccuntvalue", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardGrouplbl() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardGrouplbl", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardGroupValue() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardGroupValue", pageName, frameName);
	}

	public WebElement transactionDetailsAsscociateAccountcardPaymentMsg() {
		return getWebElement(d, "transactionDetailsAsscociateAccountcardPaymentMsg", pageName, frameName);
	}

	public WebElement transactionDetailsCategoryLink() {
		return getVisibileWebElement(d, "transactionDetailsCategoryLink", "Transaction", null);
	}

	public WebElement transactionDetailsSubCategoryList(int MLC, int HLC) {
		String abC = getVisibileElementXPath(d, "Transaction", null, "transactionDetailsSubCategoryList");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);
		return d.findElement(By.xpath(abC));
	}

	public List<WebElement> justRadioSelected_SRT() {
		return getWebElements(d, "justRadioSelected_SRT", "Transaction", null);
	}

	public List<WebElement> allradioButton_SRT() {
		return getWebElements(d, "allradioButton_SRT", "Transaction", null);
	}

	public void editPeojectedinstanceTransactionDetails(int txnRow, String txnAmount, String txnTag, String txnNote,
			String txncheck, String txnAttamentFilePath, String searchAmount, int MLC, int HLC) throws AWTException {
		logger.info("edit the projected instance transaction with amount,tag,note,attachmentfile");
		int transactionSize = getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (getAllAmount1().get(i).getText().trim().equals(searchAmount)) {
				click(getAllAmount1().get(i));
				break;
			}
		}
		transactionDetailsAmount().clear();
		transactionDetailsAmount().sendKeys(txnAmount);
		createTag(txnTag);
		click(transactionDetailsMoreOption());
		waitForPageToLoad(1000);
		transactionDetailsNote().clear();
		transactionDetailsNote().sendKeys(txnNote);
		transactionDetailsCheckField().clear();
		transactionDetailsCheckField().sendKeys(txncheck);
		String projectPath = System.getProperty("user.dir");
		String a = projectPath + txnAttamentFilePath;
		attachFile(a);
		click(transactionDetailsSave());
	}

	public void editPeojectedSeriesTransactionDetails(int txnRow, String txnAmount, String txnTag, String txnNote,
			String searchAmount, int HLC, int MLC) throws AWTException {
		logger.info("edit the peojectedSeries transaction with amount,tag,note");
		if (getAllAmount1().get(0) == null) {
			click(ProjectedExapdIcon());
		}
		for (int i = 0; i < getAllAmount1().size(); i++) {
			if (getAllAmount1().get(i).getText().trim().equals(searchAmount)) {
				click(getAllAmount1().get(i));
				waitForPageToLoad();
				break;
			}
		}
		click(allradioButtonText());
		waitForPageToLoad(1000);
		// click(projectedStranctionList().get(txnRow));
		waitForPageToLoad(1000);
		transactionDetailsAmount().clear();
		transactionDetailsAmount().sendKeys(txnAmount);
		createTag(txnTag);
		click(transactionDetailsMoreOption());
		waitForPageToLoad(1000);
		transactionDetailsNote().clear();
		transactionDetailsNote().sendKeys(txnNote);
		waitForPageToLoad();
		click(transactionDetailsSave());
		click(STUpdatePopUpConfirm());
	}
}