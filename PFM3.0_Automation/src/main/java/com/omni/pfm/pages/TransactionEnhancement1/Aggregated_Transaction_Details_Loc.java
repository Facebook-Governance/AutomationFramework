/**
SeleniumUtil.waitUntilSpinnerWheelIsInvisible();* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Aggregated_Transaction_Details_Loc extends TestBase {

	static Logger logger = LoggerFactory.getLogger(Aggregated_Transaction_Details_Loc.class);
	private WebDriver d;
	WebDriverWait wait;
	public final static By aggregatedTags = SeleniumUtil.getByObject("Transaction", null, "AggregatedListTag");
	public final static By saveAggregatedTransaction = SeleniumUtil.getByObject("Transaction", null, "save_ATD");
	public final static By createRule = SeleniumUtil.getByObject("Transaction", null, "createRule_ATD");

	//createRule_ATD
	public Aggregated_Transaction_Details_Loc(WebDriver d) {
		this.d = d;
		wait = new WebDriverWait(d, 60);
	}

	public WebElement mobileTransactionDetailsBack() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileTransactionDetailsBack", "Transaction", null);
	}

	public WebElement crossIconMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "crossIconMobile", "Transaction", null);
	}

	public boolean isMobiletagBack() {
		return PageParser.isElementPresent("MobiletagBack", "Transaction", null);
	}

	public WebElement MobiletagBack() {
		return SeleniumUtil.getWebElement(d, "MobiletagBack", "Transaction", null);
	}

	public List<WebElement> PendingStranctionList() {
		return SeleniumUtil.getWebElements(d, "PendingStranctionList_ATD", "Transaction", null);
	}

	public List<WebElement> collapsIcon() {
		return SeleniumUtil.getWebElements(d, "collapsIcon_ATD", "Transaction", null);
	}

	public boolean isCollapseIconPresent() {
		return PageParser.isElementPresent("collapsIcon_ATD_mobile", "Transaction", null);
	}

	public WebElement save() {
		return SeleniumUtil.getVisibileWebElement(d, "save_ATD", "Transaction", null);
	}

	public List<WebElement> saveList() {
		return SeleniumUtil.getWebElements(d, "save_ATD", "Transaction", null);
	}

	public WebElement amountlabel() {
		return SeleniumUtil.getVisibileWebElement(d, "amountlabel_ATD", "Transaction", null);
	}

	public WebElement Amount() {
		return SeleniumUtil.getVisibileWebElement(d, "Amount_ATD", "Transaction", null);
	}

	public WebElement descriptionLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "descriptionLabel_ATD", "Transaction",

				null);
	}

	public WebElement descField() {
		return SeleniumUtil.getVisibileWebElement(d, "descField_ATD", "Transaction", null);
	}

	public WebElement descFieldErorr() {
		return SeleniumUtil.getVisibileWebElement(d, "descFieldErorr_ATD", "Transaction", null);
	}

	public WebElement Statment() {
		return SeleniumUtil.getVisibileWebElement(d, "Statment_ATD", "Transaction", null);
	}

	public WebElement StatmentDesc() {
		return SeleniumUtil.getVisibileWebElement(d, "StatmentDesc_ATD", "Transaction", null);
	}

	public WebElement CreditDebit() {
		return SeleniumUtil.getVisibileWebElement(d, "CreditDebit_ATD", "Transaction", null);
	}

	public WebElement accountName() {
		return SeleniumUtil.getVisibileWebElement(d, "accountName_ATD", "Transaction", null);
	}

	public WebElement datelabel() {
		return SeleniumUtil.getVisibileWebElement(d, "datelabel_ATD", "Transaction", null);
	}

	public WebElement tansactiondate() {
		return SeleniumUtil.getVisibileWebElement(d, "tansactiondate_ATD", "Transaction", null);
	}

	public WebElement catLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "catLabel_ATD", "Transaction", null);
	}

	public WebElement catgoryField() {
		return SeleniumUtil.getWebElement(d, "catgoryField_ATD", "Transaction", null);
	}

	public WebElement catChanegMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "catChanegMessage_ATD", "Transaction",

				null);
	}

	public WebElement createRule() {
		return SeleniumUtil.getVisibileWebElement(d, "createRule_ATD", "Transaction", null);
	}

	public List<WebElement> createRuleList() {
		return SeleniumUtil.getWebElements(d, "createRule_ATD", "Transaction", null);
	}

	public WebElement transctionnoDetailsTagError() {
		return SeleniumUtil.getVisibileWebElement(d, "transctionnoDetailsTagError", "Transaction", null);
	}

	public WebElement ruleSave() {
		return SeleniumUtil.getVisibileWebElement(d, "ruleSave_ATD", "Transaction", null);
	}

	public WebElement SucessMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "sucessMessage_SRT", "Transaction", null);
	}

	public List<WebElement> AggregatedListTag() {
		return SeleniumUtil.getWebElements(d, "AggregatedListTag", "Transaction", null);
	}

	public WebElement taginfomessage() {
		return SeleniumUtil.getVisibileWebElement(d, "taginfomessage_ATD", "Transaction", null);
	}

	public WebElement ShowMore() {
		return SeleniumUtil.getWebElement(d, "ShowMore_ATD", "Transaction", null);
	}

	public WebElement note() {
		return SeleniumUtil.getWebElement(d, "note_ATD", "Transaction", null);
	}

	public WebElement attachment() {
		return SeleniumUtil.getWebElement(d, "attachment_ATD", "Transaction", null);
	}

	public WebElement split() {
		return SeleniumUtil.getWebElement(d, "split_ATD", "Transaction", null);
	}

	public WebElement cancel() {
		return SeleniumUtil.getVisibileWebElement(d, "cancel_ATD", "Transaction", null);
	}

	public boolean isCancelBtnPresent() {
		return PageParser.isElementPresent("cancel_ATD_mobile", "Transaction", null);
	}

	public WebElement update() {
		return SeleniumUtil.getVisibileWebElement(d, "update_ATD", "Transaction", null);
	}

	public WebElement catdropDownIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "catdropDownIcon_ATD", "Transaction", null);
	}

	public WebElement AggregatedCreatRulepopUp() {
		return SeleniumUtil.getWebElement(d, "AggregatedCreatRulepopUp", "Transaction", null);
	}

	public List<WebElement> AggregatedCreatRulepopUpList() {
		return SeleniumUtil.getWebElements(d, "AggregatedCreatRulepopUp", "Transaction", null);
	}

	public WebElement catgoryList(int MLC, int HLC) {

		return d.findElement(
				By.xpath("//div[@id='category-dropdown-wrap']//ul/li[" + MLC + "]/ul/li[" + HLC + "]//span[1]"));
	}

	public WebElement TransactionCatRuleAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "TransactionCatRuleAmount", "Transaction", null);
	}

	public WebElement createRuleToAmount_TD() {
		return SeleniumUtil.getVisibileWebElement(d, "createRuleToAmount_TD", "Budget", null);
	}

	public WebElement TransactionCatRuleCategoryLink() {
		return SeleniumUtil.getVisibileWebElement(d, "TransactionCatRuleCategoryLink", "Transaction", null);
	}

	public WebElement TransactionCatRuleCategoryList(int MLC, int HLC) {
		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "TransactionCatRuleCategoryList");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		logger.info(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		logger.info(abC);
		return d.findElement(By.xpath(abC));
	}

	public WebElement TransactionDetailsCategoryList(String catName) {
		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "TransactionDetailsCategoryList");
		abC = abC.replaceAll("CatName", catName);
		return d.findElement(By.xpath(abC));
	}

	public WebElement TransactionCatRuleCategory(String catName) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "TransactionCatRuleCategory");
		abC = abC.replaceAll("CatName", catName);

		return d.findElement(By.xpath(abC));

	}

	public WebElement CRCatSearchedSubCatName(String subCatName) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "CRCatSearchedSubCatName");
		abC = abC.replaceAll("subCat", subCatName);

		return d.findElement(By.xpath(abC));

	}

	public String DateFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		logger.info(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public WebElement TransactionTagLink() {
		return SeleniumUtil.getWebElement(d, "TransactionTagLink", "Transaction", null);
	}

	public WebElement TransactionTagField() {
		return SeleniumUtil.getWebElement(d, "TransactionTagField", "Transaction", null);
	}

	public WebElement TransactionTagFieldMobile() {
		return SeleniumUtil.getWebElement(d, "TransactionTagFieldmobile", "Transaction", null);
	}

	public boolean isTransactionTagFieldMobile() {
		return PageParser.isElementPresent("TransactionTagFieldmobile", "Transaction", null);
	}

	public WebElement TransactionTagCreate() {
		return SeleniumUtil.getWebElement(d, "TransactionTagCreate", "Transaction", null);
	}

	public WebElement TransactionCreateTagMobile() {
		return SeleniumUtil.getWebElement(d, "TransactionCreateTagMobile", "Transaction", null);
	}

	public List<WebElement> TransactionDefualtTagList() {
		return SeleniumUtil.getWebElements(d, "TransactionDefualtTagList", "Transaction", null);
	}

	public void tagSearch(String tag) {
		if (isTransactionTagFieldMobile()) {
			TransactionTagFieldMobile().clear();
			TransactionTagFieldMobile().sendKeys(tag);
		} else {
			TransactionTagField().clear();
			TransactionTagField().sendKeys(tag);
		}

	}

	public void createTag(String tagName) {
		SeleniumUtil.click(TransactionTagLink());
		TransactionTagField().sendKeys(tagName);
		SeleniumUtil.click(TransactionTagCreate());
	}

	public void createTag1(String tagName) {
		if (isTransactionTagFieldMobile()) {
			TransactionTagFieldMobile().clear();
			SeleniumUtil.waitForPageToLoad(1000);
			TransactionTagFieldMobile().sendKeys(tagName);
			SeleniumUtil.click(TransactionCreateTagMobile());
			SeleniumUtil.waitForPageToLoad(1000);
		} else {
			TransactionTagField().clear();
			SeleniumUtil.waitForPageToLoad(1000);
			TransactionTagField().sendKeys(tagName);
			SeleniumUtil.click(TransactionTagCreate());
			SeleniumUtil.waitForPageToLoad(1000);
		}

	}

	public WebElement attachFile_TA() {
		return SeleniumUtil.getWebElement(d, "attachFile_TA", "Transaction", null);
	}

	public void attachFile(String path) throws AWTException

	{
		attachFile_TA().sendKeys(path);
	}

	public List<WebElement> getAlldescription1() {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "getAlldescription1_AMT");

		return d.findElements(By.xpath(abC));

	}

	public WebElement SplitIcon() {
		return SeleniumUtil.getWebElement(d, "splitTran", "Transaction", null);
	}

	public WebElement savechanges() {
		return SeleniumUtil.getVisibileWebElement(d, "save_tst", "Transaction", null);
	}

	public List<WebElement> getAllPostedAmount1() {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "getAllPostedAmount1_AMT");

		return d.findElements(By.xpath(abC));

	}

//start
//added for Category search in Transaction details screen
	public WebElement tranDtlsCatSearchField() {
		return SeleniumUtil.getVisibileWebElement(d, "tranDtlsCatSearchField", "Transaction", null);
	}

	public WebElement tranDtlsCatSearchTextClose() {
		return SeleniumUtil.getVisibileWebElement(d, "tranDtlsCatSearchTextClose", "Transaction", null);
	}

	public WebElement categoryCloseMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "categoryCloseMobile", "Transaction", null);
	}

	public boolean isbudgetCloseMobile() {
		return PageParser.isElementPresent("budgetCloseMobile", "Transaction", null);
	}

	public WebElement budgetCloseMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetCloseMobile", "Transaction", null);
	}

	public WebElement mobilebudgetTransactionDetailsBack() {
		return SeleniumUtil.getVisibileWebElement(d, "backtobudgetmobile", "Transaction", null);
	}

	public boolean iscategoryCloseMobile() {
		return PageParser.isElementPresent("categoryCloseMobile", "Transaction", null);
	}

	public List<WebElement> tranDtlCatSearchedHLC() {
		return SeleniumUtil.getWebElements(d, "tranDtlCatSearchedHLC", "Transaction", null);
	}

	public List<WebElement> tranDtlCatSearchedMLC() {
		return SeleniumUtil.getWebElements(d, "tranDtlCatSearchedMLC", "Transaction", null);
	}

	public List<WebElement> tranDtlCatSearchedHLCHL() {
		return SeleniumUtil.getWebElements(d, "tranDtlCatSearchedHLCHL", "Transaction", null);
	}

	public List<WebElement> tranDtlCatSearchedMLCHL() {
		return SeleniumUtil.getWebElements(d, "tranDtlCatSearchedMLCHL", "Transaction", null);
	}

	public WebElement tranDtlCatNocatAvailabel() {
		return SeleniumUtil.getVisibileWebElement(d, "tranDtlCatNocatAvailabel", "Transaction", null);
	}

	public List<WebElement> tranDtlCatSearchedSubCat() {
		return SeleniumUtil.getWebElements(d, "tranDtlCatSearchedSubCat", "Transaction", null);
	}

	public List<WebElement> tranDtlCatSearchedSubCatHL() {
		return SeleniumUtil.getWebElements(d, "tranDtlCatSearchedSubCatHL", "Transaction", null);
	}

	public WebElement createRuleIcon_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleIcon_TD", "Budget", null);
	}

	public WebElement createRuleText_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleText_TD", "Budget", null);
	}

	public WebElement createRuleHeader_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleHeader_TD", "Budget", null);
	}

	public WebElement createRuleSucessMesaage_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleSucessMesaage_TD", "Budget", null);
	}

	public WebElement createRuleDescription_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleDescription_TD", "Budget", null);
	}

	public WebElement createRuleExactDropDown_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleExactDropDown_TD", "Budget", null);
	}

	public WebElement createRuleExactDropDownList_TD(String amountType) {
		String dropdownList = SeleniumUtil.getVisibileElementXPath(d, "Budget", null, "createRuleExactDropDownList_TD");
		dropdownList = dropdownList.replaceAll("exactly", amountType);
		return d.findElement(By.xpath(dropdownList));
	}

	public WebElement createRulePastTxnToggel_TD() {
		return SeleniumUtil.getWebElement(d, "createRulePastTxnToggel_TD", "Budget", null);
	}

	public WebElement createRuleClose_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleClose_TD", "Budget", null);
	}

	public WebElement createRuleCancel_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleCancel_TD", "Budget", null);
	}

	public WebElement txnDetailsHeader_TD() {
		return SeleniumUtil.getWebElement(d, "txnDetailsHeader_TD", "Budget", null);
	}

	public List<WebElement> txnDetailsHeaderList_TD() {
		return SeleniumUtil.getWebElements(d, "txnDetailsHeader_TD", "Budget", null);
	}

	public WebElement createRuleBack_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleBack_TD", "Budget", null);
	}

	public WebElement createRuleToAmountToggel_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleToAmountToggel_TD", "Budget", null);
	}

	public WebElement createRuleToDescriptionToggel_TD() {
		return SeleniumUtil.getWebElement(d, "createRuleToDescriptionToggel_TD", "Budget", null);
	}

	public WebElement createRuleAlreadyExistMsg() {
		return SeleniumUtil.getWebElement(d, "createRuleAlreadyExistMsg", "Budget", null);
	}

	public void changeCategoories(String catName) {
		WebElement catfiled = wait.until(ExpectedConditions.visibilityOf(catgoryField()));
		SeleniumUtil.click(catfiled);
		WebElement catname = wait.until(ExpectedConditions.visibilityOf(TransactionDetailsCategoryList(catName)));
		SeleniumUtil.click(catname);
	}

	public void clickCreatRule() {
		SeleniumUtil.click(this.createRule());
		wait.until(ExpectedConditions.visibilityOf(this.createRuleHeader_TD()));
	}

	public void clickCloseCreatRule() {
		SeleniumUtil.click(this.createRuleClose_TD());
	}

	public void clickCancelCreatRule() {
		SeleniumUtil.click(this.createRuleCancel_TD());
	}

	public void clickBackCreatRule() {
		SeleniumUtil.click(this.createRuleBack_TD());
	}

	public void CreatRuleExactly(String description, String amount, String exactDropDown, String catName,
			boolean pastRule) {
		this.createRuleDescription_TD().clear();
		this.createRuleDescription_TD().sendKeys(description);
		SeleniumUtil.click(this.createRuleExactDropDown_TD());
		SeleniumUtil.click(this.createRuleExactDropDownList_TD(exactDropDown));
		this.TransactionCatRuleAmount().clear();
		this.TransactionCatRuleAmount().sendKeys(amount);
		SeleniumUtil.click(TransactionCatRuleCategoryLink());
		SeleniumUtil.click(this.TransactionCatRuleCategory(catName));
		if (pastRule == true) {
			SeleniumUtil.click(this.createRulePastTxnToggel_TD());
		}
		SeleniumUtil.click(this.ruleSave());
		// wait.until(ExpectedConditions.invisibilityOf(this.AggregatedCreatRulepopUp()));

	}

	public void CreatRuleWithOnlyDescription(String description, String catName, boolean pastRule) {
		this.createRuleDescription_TD().clear();
		this.createRuleDescription_TD().sendKeys(description);
		SeleniumUtil.click(this.createRuleExactDropDown_TD());
		SeleniumUtil.click(createRuleToAmountToggel_TD());
		SeleniumUtil.click(TransactionCatRuleCategoryLink());
		SeleniumUtil.click(this.TransactionCatRuleCategory(catName));
		if (pastRule == true) {
			SeleniumUtil.click(this.createRulePastTxnToggel_TD());
		}
		SeleniumUtil.click(this.ruleSave());
	}

	public void CreatRuleOnlyAmount(String amount, String exactDropDown, String catName, boolean pastRule) {
		SeleniumUtil.click(createRuleToDescriptionToggel_TD());
		SeleniumUtil.click(this.createRuleExactDropDown_TD());
		SeleniumUtil.click(this.createRuleExactDropDownList_TD(exactDropDown));
		this.TransactionCatRuleAmount().clear();
		this.TransactionCatRuleAmount().sendKeys(amount);
		SeleniumUtil.click(TransactionCatRuleCategoryLink());
		SeleniumUtil.click(this.TransactionCatRuleCategory(catName));
		if (pastRule == true) {
			SeleniumUtil.click(this.createRulePastTxnToggel_TD());
		}
		SeleniumUtil.click(this.ruleSave());
	}

	public void CreatRuleBetween(String description, String amountFrom, String amountTo, String exactDropDown,
			String catName, boolean pastRule) {
		this.createRuleDescription_TD().clear();
		this.createRuleDescription_TD().sendKeys(description);
		SeleniumUtil.click(this.createRuleExactDropDown_TD());
		SeleniumUtil.click(this.createRuleExactDropDownList_TD(exactDropDown));
		this.TransactionCatRuleAmount().clear();
		this.TransactionCatRuleAmount().sendKeys(amountFrom);
		this.createRuleToAmount_TD().clear();
		this.createRuleToAmount_TD().sendKeys(amountTo);
		SeleniumUtil.click(TransactionCatRuleCategoryLink());
		SeleniumUtil.click(this.TransactionCatRuleCategory(catName));
		if (pastRule == true) {
			SeleniumUtil.click(this.createRulePastTxnToggel_TD());
		}
		SeleniumUtil.click(this.ruleSave());
	}

	public void CreatRuleWithSubCategory(String subCategory, boolean pastRule) {
		SeleniumUtil.click(TransactionCatRuleCategoryLink());
		SeleniumUtil.click(this.CRCatSearchedSubCatName(subCategory));
		if (pastRule == true) {
			SeleniumUtil.click(this.createRulePastTxnToggel_TD());
		}
		SeleniumUtil.click(this.ruleSave());
	}
//end

	public void EditTransactionInTransactionPage() throws AWTException {
		descField().clear();
		descField().sendKeys(PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
		SeleniumUtil.click(TransactionTagLink());
		SeleniumUtil.waitForPageToLoad(1000);
		TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetTag1"));
		SeleniumUtil.click(TransactionTagCreate());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(ShowMore());
		note().clear();
		note().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetNote1"));
		SeleniumUtil.waitForPageToLoad();
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		attachFile(a);

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(update());
		SeleniumUtil.waitForPageToLoad(7000);

		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			if (getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TranBudgeSeacrh2"))) {

				SeleniumUtil.click(getAlldescription1().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(ShowMore());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SplitIcon());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(savechanges());
	}

	public WebElement showmoretransaction1() {
		return SeleniumUtil.getWebElement(d, "showmoretransaction", "Transaction", null);

	}

	public void EditTransactionIntRansactionPageExpanse() throws AWTException {
		try {
			SeleniumUtil.click(showmoretransaction1());
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			if (getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TranExpansesearch2"))) {

				SeleniumUtil.click(getAlldescription1().get(i));
				break;
			}
		}
		// SeleniumUtil.click(getAlldescription1().get(6));
		SeleniumUtil.waitForPageToLoad(1000);
		descField().clear();
		descField().sendKeys(PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
		SeleniumUtil.click(TransactionTagLink());
		SeleniumUtil.waitForPageToLoad(1000);
		TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetTag1"));
		SeleniumUtil.click(TransactionTagCreate());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(ShowMore());
		note().clear();
		note().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetNote1"));
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		attachFile(a);
		SeleniumUtil.click(update());
		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			if (getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TranExpansesearch2"))) {
				SeleniumUtil.click(getAlldescription1().get(i));
				break;
			}
		}
		SeleniumUtil.click(ShowMore());
		SeleniumUtil.click(SplitIcon());
		SeleniumUtil.click(savechanges());
	}

	public void EditIncomeTransactionInTransactionPage() throws AWTException {
		try {
			SeleniumUtil.click(showmoretransaction1());
		} catch (Exception e) {

		}
		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			if (getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Tranincomesearch2"))) {

				SeleniumUtil.click(getAlldescription1().get(i));
				SeleniumUtil.waitForPageToLoad(5000);
				break;
			}
		}
		descField().clear();
		descField().sendKeys(PropsUtil.getDataPropertyValue("IncomeTranDescriptionUpdate1"));
		SeleniumUtil.click(TransactionTagLink());
		SeleniumUtil.waitForPageToLoad(1000);
		TransactionTagField().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetTag1"));
		SeleniumUtil.click(TransactionTagCreate());
		SeleniumUtil.click(ShowMore());
		note().clear();
		note().sendKeys(PropsUtil.getDataPropertyValue("TranBudgetNote1"));
		String path1 = System.getProperty("user.dir");
		logger.info(path1 + "\\src\\main\\resources\\Attachments\\networth.png");
		String a = path1 + "\\src\\main\\resources\\Attachments\\networth.png";
		attachFile(a);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(update());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		for (int i = 0; i < getAllPostedAmount1().size(); i++) {
			if (getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Tranincomesearch2"))) {

				SeleniumUtil.click(getAlldescription1().get(i));
				break;
			}
		}
		SeleniumUtil.click(ShowMore());
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(SplitIcon());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(savechanges());
	}

	public void postedTxnClick(int txnRow) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(PendingStranctionList().get(txnRow)));
		SeleniumUtil.click(element);
	}

	public void catgoryFieldClick() {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(catgoryField()));
		SeleniumUtil.click(element);
	}

	public void tansactionDetailsCatSerach(String category) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(tranDtlsCatSearchField()));
		element.clear();
		element.sendKeys(category);
		SeleniumUtil.waitFor(1);
	}

	public void SelectSuBCategory(String subCategory) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(TransactionCatRuleCategoryLink()));
		SeleniumUtil.click(element);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(this.CRCatSearchedSubCatName(subCategory)));
		SeleniumUtil.click(element2);
	}

	public void enterDecValue(String decr) {
		descField().clear();
		descField().sendKeys(decr);
		SeleniumUtil.click(update());
	}

	public WebElement butgtviewdetailsbtn() {
		return SeleniumUtil.getWebElement(d, "butgtviewdetailsbtn", "Budget", null);
	}

	public void clickCancelCreatRulemob() {
		SeleniumUtil.click(this.createRuleCancel_mob());
	}

	private WebElement createRuleCancel_mob() {
		return SeleniumUtil.getWebElement(d, "CancelCreateRulebtn", "Budget", null);

	}
}
