/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class Transaction_Categorization_Rules_Loc extends TestBase {

	static Logger logger = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	String TransactionPage = "Transaction";

	public Transaction_Categorization_Rules_Loc(WebDriver d) {
		this.d = d;

	}

	public WebElement uncat() {
		return SeleniumUtil.getWebElement(d, "uncat", TransactionPage, null);
	}

	public WebElement newuncat() {
		return SeleniumUtil.getWebElement(d, "newuncat", TransactionPage, null);
	}

	public WebElement settingTab() {
		return SeleniumUtil.getWebElement(d, "Settings", "DashboardPage", null);
	}

	public WebElement categorizationTab() {
		return SeleniumUtil.getWebElement(d, "Categ", "DashboardPage", null);
	}

	public WebElement baccat() {
		return SeleniumUtil.getWebElement(d, "baccat", TransactionPage, null);
	}

	public WebElement catrule() {
		return SeleniumUtil.getWebElement(d, "catrule", TransactionPage, null);
	}

	public WebElement more() {
		return SeleniumUtil.getWebElement(d, "more", TransactionPage, null);
	}

	public WebElement manage() {
		return SeleniumUtil.getWebElement(d, "manage", TransactionPage, null);
	}

	public List<WebElement> allcat() {
		return SeleniumUtil.getWebElements(d, "allcat", TransactionPage, null);
	}

	public List<WebElement> allcatmobile() {
		return SeleniumUtil.getWebElements(d, "allcatmobile", TransactionPage, null);
	}

	public WebElement allcat_Name(String HLCName) {
		String hlc = null;
		if (appFlag.equalsIgnoreCase("web") || appFlag.equalsIgnoreCase("false")) {

			hlc = SeleniumUtil.getVisibileElementXPath(d, TransactionPage, null, "allcat_Name");
		} else {
			hlc = SeleniumUtil.getVisibileElementXPath(d, TransactionPage, null, "allcat_NameMoblie");
		}
		hlc = hlc.replaceAll("HCLCat", HLCName);
		return d.findElement(By.xpath(hlc));
	}

	public WebElement allcat_NameMoblie(String HLCName) {
		String hlc = SeleniumUtil.getVisibileElementXPath(d, TransactionPage, null, "allcat_NameMoblie");
		hlc = hlc.replaceAll("HCLCat", HLCName);
		return d.findElement(By.xpath(hlc));
	}

	public List<WebElement> allsub() {
		return SeleniumUtil.getWebElements(d, "allsub", TransactionPage, null);
	}

	public WebElement allsub_Name(String MLCName) {
		String mlc = SeleniumUtil.getVisibileElementXPath(d, TransactionPage, null, "allsub_Name");
		mlc = mlc.replaceAll("MLCCat", MLCName);
		return d.findElement(By.xpath(mlc));
	}

	public List<WebElement> pendingtr() {
		return SeleniumUtil.getWebElements(d, "pendingtr", TransactionPage, null);
	}

	public WebElement catdrp() {
		return SeleniumUtil.getWebElement(d, "catdrp", TransactionPage, null);
	}

	public WebElement search() {
		return SeleniumUtil.getWebElement(d, "catSearch", TransactionPage, null);
	}

	public boolean isSubCatPresent() {
		return PageParser.isElementPresent("sub", TransactionPage, null);
	}

	public WebElement sub() {
		return SeleniumUtil.getWebElement(d, "sub", TransactionPage, null);
	}

	public List<WebElement> transactionDetailsSubCategoryList(int MLC, int HLC) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "transactionDetailsSubCategoryList");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		logger.info(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		logger.info(abC);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllcat() {
		return SeleniumUtil.getWebElements(d, "getAllcat_Catgoriz", TransactionPage, null);

	}

	public WebElement moreBtn() {

		return SeleniumUtil.getWebElement(d, "moreBtn", TransactionPage, null);

	}

	public WebElement menuManageCat() {

		return SeleniumUtil.getWebElement(d, "menuManageCat", TransactionPage, null);

	}

	public WebElement masterlevelcategory() {

		return SeleniumUtil.getWebElement(d, "masterlevelcategory", TransactionPage, null);

	}

	public List<WebElement> masterlevelcategory1() {

		return SeleniumUtil.getWebElements(d, "masterlevelcategory1", TransactionPage, null);

	}

	public WebElement sublevelcategory() {

		return SeleniumUtil.getWebElement(d, "sublevelcategory", TransactionPage, null);

	}

	public WebElement addCatInCatRules() {

		return SeleniumUtil.getWebElement(d, "addCatInCatRules", TransactionPage, null);

	}

	public WebElement saveChanges() {

		return SeleniumUtil.getWebElement(d, "saveChanges", TransactionPage, null);

	}

	public WebElement saveChangesMobile() {

		return SeleniumUtil.getWebElement(d, "saveChangesMobile", TransactionPage, null);

	}

	public WebElement deleteSubCat() {

		return SeleniumUtil.getWebElement(d, "deleteSubCat", TransactionPage, null);

	}

	public void manageCat() {
		WebDriverWait wait = new WebDriverWait(d, 12);
		WebElement more = wait.until(ExpectedConditions.visibilityOf(moreBtn()));
		SeleniumUtil.click(more);
		WebElement mgcat = wait.until(ExpectedConditions.visibilityOf(menuManageCat()));
		SeleniumUtil.click(mgcat);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			WebElement back = SeleniumUtil.getWebElement(d, "cancelButton", "Categories", null);
			wait.until(ExpectedConditions.visibilityOf(back));
		} else {
			SeleniumUtil.waitFor(5);
			wait.until(ExpectedConditions.visibilityOf(baccat()));
		}
	}

	public void editCat(String CatName, String subCat) {
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(allcatmobile().get(1));
			SeleniumUtil.waitForPageToLoad(2000);
		} else {
			SeleniumUtil.click(allcat().get(1));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		// SeleniumUtil.click(allcat().get(1));
		// SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(allsub().get(1));
		masterlevelcategory().clear();
		masterlevelcategory().sendKeys(CatName);
		sublevelcategory().clear();
		sublevelcategory().sendKeys(subCat);
		SeleniumUtil.click(addCatInCatRules());
		SeleniumUtil.click(saveChanges());
	}

	public void editCat(String CatName, String subCat, int HLC, int MLC) {
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(allcatmobile().get(HLC));
		} else {
			SeleniumUtil.click(allcat().get(HLC));
		}
		SeleniumUtil.click(allsub().get(MLC));
		masterlevelcategory().clear();
		masterlevelcategory().sendKeys(CatName);
		masterlevelcategory().sendKeys(Keys.TAB);
		sublevelcategory().clear();
		sublevelcategory().sendKeys(subCat);
		SeleniumUtil.click(addCatInCatRules());
		SeleniumUtil.click(saveChanges());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(1);
	}

	public void editCat(String CatName, String subCat, String HLC, String MLC) {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement hlc = wait.until(ExpectedConditions.visibilityOf(allcat_Name(HLC)));
		SeleniumUtil.click(hlc);
		WebElement mlc = wait.until(ExpectedConditions.visibilityOf(allsub_Name(MLC)));
		SeleniumUtil.click(mlc);
		WebElement mlcField = wait.until(ExpectedConditions.visibilityOf(masterlevelcategory()));
		mlcField.clear();
		mlcField.sendKeys(CatName);
		sublevelcategory().clear();
		sublevelcategory().sendKeys(subCat);
		SeleniumUtil.click(addCatInCatRules());
		SeleniumUtil.click(saveChanges());
	}

	public void editCategory(String CatName, String subCat, int MLC) {

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(allsub().get(MLC));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(masterlevelcategory());
		masterlevelcategory().clear();
		SeleniumUtil.waitForPageToLoad(200);
		masterlevelcategory().sendKeys(CatName);
		SeleniumUtil.waitForPageToLoad(200);
		masterlevelcategory().sendKeys(Keys.TAB);
		sublevelcategory().clear();
		sublevelcategory().sendKeys(subCat);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(addCatInCatRules());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(saveChanges());
		SeleniumUtil.waitForPageToLoad(1000);
	}

	public void editCategory(String CatName, String subCat, int MLC, int size) {

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(allsub().get(MLC));
		SeleniumUtil.waitForPageToLoad();
		masterlevelcategory1().get(size).click();
		masterlevelcategory1().get(size).clear();
		SeleniumUtil.waitForPageToLoad(200);
		masterlevelcategory1().get(size).sendKeys(CatName);
		SeleniumUtil.waitForPageToLoad(200);
		masterlevelcategory1().get(size).sendKeys(Keys.TAB);
		sublevelcategory().clear();
		sublevelcategory().sendKeys(subCat);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(addCatInCatRules());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(saveChanges());
		SeleniumUtil.waitForPageToLoad(1000);
	}

	public void editCategory(String CatName[], String subCat[]) {

		for (int i = 1; i < 6; i++) {
			editCategory(CatName[i - 1], subCat[i - 1], i);
			SeleniumUtil.waitForPageToLoad(3000);
		}
	}

	public void editsubCat(String subCat[], int HLC, int MLC) {
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(allcatmobile().get(HLC));
			SeleniumUtil.waitForPageToLoad(2000);
		} else {
			SeleniumUtil.click(allcat().get(HLC));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		SeleniumUtil.click(allsub().get(MLC));
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < subCat.length; i++) {
			editSubCat(subCat[i]);

		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(saveChanges());
		SeleniumUtil.waitForPageToLoad();
	}

	public void editsubCat() {
		editsubCat(PropsUtil.getDataPropertyValue("TransSubCatWithSpaceSpecialChar").split(","), 0, 0);
		editsubCat(PropsUtil.getDataPropertyValue("TransSubCatWithSpecialChar1").split(","), 0, 1);
		editsubCat(PropsUtil.getDataPropertyValue("TransSubCatWithSpecialChar2").split(","), 0, 2);
		editsubCat(PropsUtil.getDataPropertyValue("TransSubCatWithSpecialChar3").split(","), 0, 3);
		editsubCat(PropsUtil.getDataPropertyValue("TransSubCatWithSpecialChar4").split(","), 0, 4);
	}

	public void editsubCat(String subCat[], String HLC, String MLC) {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement hlc = wait.until(ExpectedConditions.visibilityOf(allcat_Name(HLC)));
		SeleniumUtil.click(hlc);
		WebElement mlc = wait.until(ExpectedConditions.visibilityOf(allsub_Name(MLC)));
		SeleniumUtil.click(mlc);

		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < subCat.length; i++) {
			editSubCat(subCat[i]);

		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(saveChanges());
		SeleniumUtil.waitForPageToLoad();
	}

	public void editSubCat(String subCat) {
		SeleniumUtil.waitForPageToLoad();
		sublevelcategory().clear();
		sublevelcategory().sendKeys(subCat);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(addCatInCatRules());
	}

	public void detetesubcat() {

		allcat().get(2).click();
		SeleniumUtil.waitForPageToLoad(2000);
		allsub().get(3).click();
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(deleteSubCat());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(saveChanges());

	}

	public void detetesubcat(String HLC, String MLC) {

		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement hlc = wait.until(ExpectedConditions.visibilityOf(allcat_Name(HLC)));
		SeleniumUtil.click(hlc);
		WebElement mlc = wait.until(ExpectedConditions.visibilityOf(allsub_Name(MLC)));
		SeleniumUtil.click(mlc);
		WebElement dlt = wait.until(ExpectedConditions.visibilityOf(deleteSubCat()));
		SeleniumUtil.click(dlt);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(saveChanges());

	}

	public String DateInMMMMFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		logger.info(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	// start nov release
	public WebElement CRCreatButton() {

		return SeleniumUtil.getWebElement(d, "CRCreatButton", TransactionPage, null);

	}

	public WebElement CRCatField() {

		return SeleniumUtil.getWebElement(d, "CRCatField", TransactionPage, null);

	}

	public WebElement CRCatSearchField() {

		return SeleniumUtil.getWebElement(d, "CRCatSearchField", TransactionPage, null);

	}

	public List<WebElement> CRCatSearchedHLC() {
		return SeleniumUtil.getWebElements(d, "CRCatSearchedHLC", TransactionPage, null);
	}

	public List<WebElement> CRCatSearchedMLC() {
		return SeleniumUtil.getWebElements(d, "CRCatSearchedMLC", TransactionPage, null);
	}

	public List<WebElement> CRCatSearchedHLCHL() {
		return SeleniumUtil.getWebElements(d, "CRCatSearchedHLCHL", TransactionPage, null);
	}

	public List<WebElement> CRCatSearchedMLCHL() {
		return SeleniumUtil.getWebElements(d, "CRCatSearchedMLCHL", TransactionPage, null);
	}

	public List<WebElement> CRCatSearchedSubCat() {
		return SeleniumUtil.getWebElements(d, "CRCatSearchedSubCat", TransactionPage, null);
	}

	public List<WebElement> CRCatSearchedSubCatHL() {
		return SeleniumUtil.getWebElements(d, "CRCatSearchedSubCatHL", TransactionPage, null);
	}

	public WebElement CRCatNocatAvailabel() {

		return SeleniumUtil.getWebElement(d, "CRCatNocatAvailabel", TransactionPage, null);

	}

	public WebElement CRCatSearcheTextCLose() {

		return SeleniumUtil.getWebElement(d, "CRCatSearcheTextCLose", TransactionPage, null);

	}

	public boolean isCRCatSearcheTextCLoseMobile() {

		return PageParser.isElementPresent("CRCatSearcheTextCLoseMobile", TransactionPage, null);

	}

	public WebElement CRCatSearcheTextCLoseMobile() {

		return SeleniumUtil.getWebElement(d, "CRCatSearcheTextCLoseMobile", TransactionPage, null);

	}

	public WebElement createRuleCrossicon() {

		return SeleniumUtil.getWebElement(d, "createRuleCrossicon", "CategorzationRules", null);

	} // added by vrinda

	public void creatRuleClick() {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(CRCreatButton()));
		SeleniumUtil.click(element);
	}

	public void searchCategory(String category) {
		WebDriverWait wait = new WebDriverWait(d, 5);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(CRCatSearchField()));
		element.clear();
		element.sendKeys(category);
		SeleniumUtil.waitFor(1.5F);
	}

	public void CRcatFieldClick() {
		WebDriverWait wait = new WebDriverWait(d, 5);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(CRCatField()));
		SeleniumUtil.click(element);
	}

	public void clickUncatTxn() {
		WebDriverWait wait = new WebDriverWait(d, 7);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(uncat()));
		SeleniumUtil.click(element);
		wait.until(ExpectedConditions.visibilityOf(baccat()));

	}

	public void clickbacCat() {
		WebDriverWait wait = new WebDriverWait(d, 7);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(baccat()));
		SeleniumUtil.click(element);
	}

	public void clickPostedTxn(int txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 7);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(pendingtr().get(txnRow)));
		SeleniumUtil.click(element);
		SeleniumUtil.waitFor(2);
	}

	public void subCategory_EA() {
		PageParser.forceNavigate("Categories", d);
		String EA_MLC_MLCCategory1 = null;
		// String EA_MLC_MLCCategory2=null;
		SeleniumUtil.waitForPageToLoad();
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			EA_MLC_MLCCategory1 = PropsUtil.getDataPropertyValue("EA_MLC_MLCCategory1_43");
			// EA_MLC_MLCCategory2=PropsUtil.getDataPropertyValue("EA_MLC_MLCCategory2_43");
		} else {
			EA_MLC_MLCCategory1 = PropsUtil.getDataPropertyValue("EA_MLC_MLCCategory1");
			// EA_MLC_MLCCategory2=PropsUtil.getDataPropertyValue("EA_MLC_MLCCategory2");
		}
		editCat(EA_MLC_MLCCategory1, PropsUtil.getDataPropertyValue("EA_MLC_SubCategory1"),
				PropsUtil.getDataPropertyValue("EA_MLC_HLCCategory1"), EA_MLC_MLCCategory1);
		SeleniumUtil.waitForPageToLoad();
		editCat(EA_MLC_MLCCategory1, PropsUtil.getDataPropertyValue("EA_MLC_SubCategory2"),
				PropsUtil.getDataPropertyValue("EA_MLC_HLCCategory1"), EA_MLC_MLCCategory1);
		SeleniumUtil.waitForPageToLoad();
		/*
		 * editCat(EA_MLC_MLCCategory2,
		 * PropsUtil.getDataPropertyValue("EA_MLC_SubCategory3"),
		 * PropsUtil.getDataPropertyValue("EA_MLC_HLCCategory1"), EA_MLC_MLCCategory2);
		 * SeleniumUtil.waitForPageToLoad();
		 */
	}

	public void subCategory_IA() {
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad();
		editCat(PropsUtil.getDataPropertyValue("IA_MLC_MLCCategory1"),
				PropsUtil.getDataPropertyValue("IA_MLC_SubCategory1"),
				PropsUtil.getDataPropertyValue("IA_MLC_HLCCategory1"),
				PropsUtil.getDataPropertyValue("IA_MLC_MLCCategory1"));
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("Categories", d);
		SeleniumUtil.waitForPageToLoad(7000);
		editCat(PropsUtil.getDataPropertyValue("IA_MLC_MLCCategory1"),
				PropsUtil.getDataPropertyValue("IA_MLC_SubCategory2"),
				PropsUtil.getDataPropertyValue("IA_MLC_HLCCategory1"),
				PropsUtil.getDataPropertyValue("IA_MLC_MLCCategory1"));
		SeleniumUtil.waitForPageToLoad();

	}

}
