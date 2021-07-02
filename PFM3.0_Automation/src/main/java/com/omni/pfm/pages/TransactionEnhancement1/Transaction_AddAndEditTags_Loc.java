/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AddAndEditTags_Loc {
	Logger logger=LoggerFactory.getLogger(Transaction_AddAndEditTags_Loc.class);
	public static WebDriver d=null;
	static String pageName="Transaction";
	static String frameName=null;


	public Transaction_AddAndEditTags_Loc(WebDriver d) {
		Transaction_AddAndEditTags_Loc.d=d;
	}
	public WebElement addManualIcon_AMT() {
		return SeleniumUtil.getWebElement(d, "addManualIcon_AMT", pageName, frameName);
	}
	public WebElement addTagButton() {
		return SeleniumUtil.getWebElement(d, "addTagButton", pageName, frameName);
	}
	public WebElement addSearchTagTxtBox() {
		return SeleniumUtil.getWebElement(d, "addSearchTagTxtBox", pageName, frameName);
	}
	public List<WebElement> addSearchTagTxtBox1() {
		return SeleniumUtil.getWebElements(d, "addSearchTagTxtBox", pageName, frameName);
	}
	public List<WebElement> addedTags() {
		return SeleniumUtil.getWebElements(d, "addedTagsList", pageName, frameName);
	}
	public void verifyByDefaultTags() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "addedTagsList", pageName, frameName);
		CommonUtils.assertContainsListElements("byDefaultAddedTags", l);
	}
	public WebElement closeAddTransacPopup() {
		return SeleniumUtil.getWebElement(d, "closeAddTransacPopup", pageName, frameName);
	}
	public WebElement createNewTagBtn() {
		return SeleniumUtil.getWebElement(d, "createNewTagBtn", pageName, frameName);
	}
	public String getCreateTagBtnText() {
		String getText=PropsUtil.getDataPropertyValue("CreateNewTagLabel").trim();
		String getText2=PropsUtil.getDataPropertyValue("CreateNewTagLabel2").trim();
		String getTagName=PropsUtil.getDataPropertyValue("AddedTag1").trim();

		String expected= getText+" "+'"'+getTagName+'"'+" "+getText2;
		logger.info("Create new Tag Button text"+expected);
		return expected;			
	}
	public List<WebElement> addedTagsList(){
		return SeleniumUtil.getWebElements(d, "manuallyAddedTagList", pageName, frameName);
	}
	public List<WebElement> addedTagsCrossBtnList(){
		return SeleniumUtil.getWebElements(d, "addedTagsCrossBtnList", pageName, frameName);
	}
	public WebElement DuplicateTagErrorMsg() {
		return SeleniumUtil.getWebElement(d, "DuplicateTagErrorMsg", pageName, frameName);
	}
	public WebElement amount(){ 
		return SeleniumUtil.getWebElement(d, "amount_AMT", pageName, frameName); 
	}
	public WebElement description () { 
		return SeleniumUtil.getVisibileWebElement(d, "description_TxnDEtails", pageName, frameName);
	}
	public WebElement description_AMT () { 
		return SeleniumUtil.getVisibileWebElement(d, "description_AMT", pageName, frameName);
	}
	public List<WebElement> FrequencyList () { 
		return SeleniumUtil.getWebElements(d, "FrequencyList_AMT", pageName, frameName);
	}
	public WebElement frequencyDropDown () {
		return SeleniumUtil.getVisibileWebElement(d, "frequencyDropDown_AMT", pageName, frameName);
	}
	public WebElement Schedule () { 
		return SeleniumUtil.getVisibileWebElement(d, "Schedule_AMT", pageName, frameName); 
	}
	public WebElement catgorie () { 
		return SeleniumUtil.getVisibileWebElement(d, "catgorie_AMT", pageName, frameName); 
	}
	public WebElement add () { 
		return SeleniumUtil.getWebElement(d, "add_AMT", pageName, frameName);
	}

	public WebElement catgoryList(int MLC, int HLC) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "catgoryList_AMT");
		abC=abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC=abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);

		return d.findElement(By.xpath(abC));

	}
	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public List<WebElement> ListAccount(int container) {
		String abC = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "ListAccount_AMT");
		abC=abC.replaceAll("container", Integer.toString(container));

		return d.findElements(By.xpath(abC));
	}

	public WebElement accountdropdown () { 
		return SeleniumUtil.getVisibileWebElement(d, "accountdropdown_AMT", pageName, frameName);
	}

	public void createTransactionWithMultipleTags(String Amount, String Desc, int account, int sche, int MLC, int HCL,
			int transactionType) {

		amount().sendKeys(Amount);
		description_AMT().sendKeys(Desc);
		frequencyDropDown().click();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(FrequencyList().get(transactionType));

		accountdropdown().click();
		SeleniumUtil.click(ListAccount(1).get(account));

		Schedule().sendKeys(targateDate1(sche));
		catgorie().click();
		System.out.println(catgoryList(MLC, HCL).getText());
		SeleniumUtil.click(catgoryList(MLC, HCL));

		SeleniumUtil.click(addTagButton());
		for(int i=1;i<10;i++) {
			SeleniumUtil.click(addSearchTagTxtBox());	
			addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AddedTag1").trim()+i);
			SeleniumUtil.click(createNewTagBtn());
		}
	}
	public WebElement searchTxtBoxCrossIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "searchTxtBoxCrossIcon", pageName, frameName); 
	}
	public void presentTagsList(){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "addedTagsList", pageName, frameName);
		CommonUtils.assertContainsListElements("presentTagsList", l);
	}
	public WebElement FilterByTag() {
		return SeleniumUtil.getVisibileWebElement(d, "FilterByTag", pageName, frameName); 
	}
	public List<WebElement> FilterByTagList(){
		return SeleniumUtil.getWebElements(d, "FilterByTagList", pageName, frameName);
	}
	public void filterByTagList() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "FilterByTagList", pageName, frameName);
		CommonUtils.assertContainsListElements("presentTagsList", l);	
	}
	public WebElement addTagIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "addTagIcon", pageName, frameName); 
	}
	public void manuallyAddedTagList() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "manuallyAddedTagList", pageName, frameName);
		CommonUtils.assertEqualsListElements("presentTagsList", l);	
	}
	public WebElement AddedTranscNameContainsTag() {
		return SeleniumUtil.getVisibileWebElement(d, "AddedTranscNameContainsTag", pageName, frameName); 
	}
	public void verifyingTagSelectionBehaviour() {
		for(int i=0;i<FilterByTagList().size();i++) {
			SeleniumUtil.click(FilterByTagList().get(i));
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(FilterByTag());
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(AddedTranscNameContainsTag().getText().trim(), PropsUtil.getDataPropertyValue("AddedTransactionName").trim());		
		}
	}
	public WebElement projectedTransactionDD() {
		return SeleniumUtil.getVisibileWebElement(d, "projectedTransactionDD", pageName, frameName); 
	}
	public List<WebElement> projectedTransactionList(){
		return SeleniumUtil.getWebElements(d, "projectedTransactionList", pageName, frameName);
	}
	public List<WebElement> postedTransactionList(){
		return SeleniumUtil.getWebElements(d, "postedTransactionList", pageName, frameName);
	}
	public WebElement addSearchTagTxtBoxExisting() {
		return SeleniumUtil.getVisibileWebElement(d, "addSearchTagTxtBoxExisting", pageName, frameName); 
	}
	public WebElement save_ATD() {
		return SeleniumUtil.getVisibileWebElement(d, "save_ATD", pageName, frameName); 
	}
	
	
	public void addingTagsToExistingTransaction() {
		SeleniumUtil.click(projectedTransactionDD());
		SeleniumUtil.click(projectedTransactionList().get(0));
		
		SeleniumUtil.click(addTagButton());
		
		String NewTags[]=PropsUtil.getDataPropertyValue("NewAddedTags").split(",");	
		for(int i=0;i<3;i++) {
			SeleniumUtil.click(addSearchTagTxtBoxExisting());
			addSearchTagTxtBoxExisting().sendKeys(NewTags[i]);
			addSearchTagTxtBoxExisting().sendKeys(Keys.ENTER);
		}
		SeleniumUtil.click(save_ATD());
	}
	
	public void newAddedTagsList(){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "addedTagsList", pageName, frameName);
		CommonUtils.assertContainsListElements("NewAddedTagsList", l);
	}
	public void SearchedResultList(){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "searchedAddedTagsList", pageName, frameName);
		CommonUtils.assertContainsListElements("SearchedResult1", l);
	}
	public WebElement AcntSettingsTagInput() {
		return SeleniumUtil.getVisibileWebElement(d, "AcntSettingsTagInput", pageName, frameName); 
	}
	public WebElement AcntSettingsSaveChanges() {
		return SeleniumUtil.getVisibileWebElement(d, "AcntSettingsSaveChanges", pageName, frameName); 
	}
	public void addTagFromAcntSettings()
	{
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(3000);
		CommonUtils.selectDropDown(d, "Senseguttenberg.bank2 Account", "Accounts Settings");
        SeleniumUtil.waitForPageToLoad(2000);
	    AcntSettingsTagInput().sendKeys(PropsUtil.getDataPropertyValue("AcntSettingTag").trim());
	    AcntSettingsTagInput().sendKeys(Keys.ENTER);
	    
	    SeleniumUtil.click(AcntSettingsSaveChanges());
	}
	public void verifyAcntSettingAddedTag(){
		List<WebElement> l = SeleniumUtil.getWebElements(d, "addedTagsList", pageName, frameName);
		CommonUtils.assertContainsListElements("TagAddedFromAcntList", l);
	}
	public void goToAddTags() {
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(addManualIcon_AMT());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(addTagButton());
	}
	
	public WebElement editedSuccessMsg() {
		return SeleniumUtil.getWebElement(d, "editedSuccessMsg", pageName, frameName); 
	}
	
	public void editingExistingTranDiscription() {
		SeleniumUtil.click(postedTransactionList().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		description().clear();
		description().sendKeys(PropsUtil.getDataPropertyValue("TransactionDescription").trim());
		SeleniumUtil.click(save_ATD());
		SeleniumUtil.waitForPageToLoad();
	}


}
