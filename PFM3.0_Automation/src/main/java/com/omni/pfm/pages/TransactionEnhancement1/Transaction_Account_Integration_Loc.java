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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Account_Integration_Loc extends SeleniumUtil {
	public WebDriver d = null;
	static WebElement we;
	String TransactionPage = "Transaction";

	public Transaction_Account_Integration_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> Accountrows() {

		return getWebElements(d, "Accountrows", TransactionPage, null);
	}

	public WebElement AccountrowsName(String acct) {
		String acc = getVisibileElementXPath(d, TransactionPage, null, "AccountrowsName");
		acc = acc.replaceAll("AcctName", acct);
		return d.findElement(By.xpath(acc));

	}

	public WebElement bankAccHolderName() {

		return getWebElement(d, "bankAccHolderName_TAI", TransactionPage, null);
	}

	public WebElement bankaccCurrentBalance() {

		return getWebElement(d, "bankaccCurrentBalance_TAI", TransactionPage, null);
	}

	public WebElement bankaccInterest() {

		return getWebElement(d, "bankaccInterest_TAI", TransactionPage, null);
	}

	public WebElement expand() {

		return getWebElement(d, "expand_TAI", TransactionPage, null);
	}

	public WebElement backToAccount() {

		return getWebElement(d, "backToAccount_TAI", TransactionPage, null);
	}

	public WebElement mnualAccountNickName_TAI() {

		return getWebElement(d, "mnualAccountNickName_TAI", TransactionPage, null);
	}

	public WebElement bankManturityDate() {

		return getWebElement(d, "bankManturityDate_TAI", TransactionPage, null);
	}

	public WebElement bankAccTerm() {

		return getWebElement(d, "bankAccTerm_TAI", TransactionPage, null);
	}

	public WebElement bankAccMaturityAmount() {

		return getWebElement(d, "bankAccMaturityAmount_TAI", TransactionPage, null);
	}

	public WebElement bankAccAvailableBal() {

		return getWebElement(d, "bankAccAvailableBal_TAI", TransactionPage, null);
	}

	public WebElement cardDueDate() {

		return getWebElement(d, "cardDueDate_TAI", TransactionPage, null);
	}

	public WebElement cardAccountHolder() {

		return getWebElement(d, "cardAccountHolder_TAI", TransactionPage, null);
	}

	public WebElement cardAsOfDate() {

		return getWebElement(d, "cardAsOfDate_TAI", TransactionPage, null);
	}

	public WebElement cardDueAmount() {

		return getWebElement(d, "cardDueAmount_TAI", TransactionPage, null);
	}

	public WebElement cardminpayment() {

		return getWebElement(d, "cardminpayment_TAI", TransactionPage, null);
	}

	public WebElement cardTotalCreditLimt() {

		return getWebElement(d, "cardTotalCreditLimt_TAI", TransactionPage, null);
	}

	public WebElement cardAvailabelCredit() {

		return getWebElement(d, "cardAvailabelCredit_TAI", TransactionPage, null);
	}

	public WebElement cardtotalcashLimit() {

		return getWebElement(d, "cardtotalcashLimit_TAI", TransactionPage, null);
	}

	public WebElement cardavailablecash() {

		return getWebElement(d, "cardavailablecash_TAI", TransactionPage, null);
	}

	public List<WebElement> cardLastPayment() {

		return getWebElements(d, "cardLastPayment_TAI", TransactionPage, null);
	}

	public WebElement cardLastPaymentDate() {

		return getWebElement(d, "cardLastPaymentDate_TAI", TransactionPage, null);
	}

	public WebElement cardAnualPer() {

		return getWebElement(d, "cardAnualPer_TAI", TransactionPage, null);
	}

	public WebElement investrow() {

		return getWebElement(d, "investrow_TAI", TransactionPage, null);
	}

	public WebElement investmentTotalBalance() {

		return getWebElement(d, "investmentTotalBalance_TAI", TransactionPage, null);
	}

	public WebElement investmentHolderName() {

		return getWebElement(d, "investmentHolderName_TAI", TransactionPage, null);
	}

	public WebElement investmentAsofNow() {

		return getWebElement(d, "investmentAsofNow_TAI", TransactionPage, null);
	}

	public WebElement LoanRow() {

		return getWebElement(d, "LoanRow_TAI", TransactionPage, null);
	}

	public WebElement loanDueDate() {

		return getWebElement(d, "loanDueDate_TAI", TransactionPage, null);
	}

	public WebElement loanholdername() {

		return getWebElement(d, "loanholdername_TAI", TransactionPage, null);
	}

	public WebElement loanDueAmount() {

		return getWebElement(d, "loanDueAmount_TAI", TransactionPage, null);
	}

	public WebElement loanDescription() {

		return getWebElement(d, "loanDescription_TAI", TransactionPage, null);
	}

	public WebElement loanprincipal() {

		return getWebElement(d, "loanprincipal_TAI", TransactionPage, null);
	}

	public WebElement bilrow() {

		return getWebElement(d, "bilrow_TAI", TransactionPage, null);
	}

	public WebElement bilBankName() {

		return getWebElement(d, "bilBankName_TAI", TransactionPage, null);
	}

	public WebElement billDueDate() {

		return getWebElement(d, "billDueDate_TAI", TransactionPage, null);
	}

	public WebElement billAccountHolder() {

		return getWebElement(d, "billAccountHolder_TAI", TransactionPage, null);
	}

	public WebElement billMinPayment() {

		return getWebElement(d, "billMinPayment_TAI", TransactionPage, null);
	}

	public WebElement billLastPayment() {

		return getWebElement(d, "billLastPayment_TAI", TransactionPage, null);
	}

	public WebElement billClose() {

		return getWebElement(d, "billClose_TAI", TransactionPage, null);
	}

	public WebElement rewardRow() {

		return getWebElement(d, "rewardRow_TAI", TransactionPage, null);
	}

	public WebElement rewarHolderName() {

		return getWebElement(d, "rewarHolderName_TAI", TransactionPage, null);
	}

	public WebElement rewExpDate() {

		return getWebElement(d, "rewExpDate_TAI", TransactionPage, null);
	}

	public WebElement RealEstateManulAccountLink() {

		return getWebElement(d, "RealEstateManulAccountLink", "AccountsPage", null);
	}

	public WebElement insurencerow() {

		return getWebElement(d, "insurencerow_TAI", TransactionPage, null);
	}

	public WebElement insurenceAmountDue() {

		return getWebElement(d, "insurenceAmountDue_TAI", TransactionPage, null);
	}

	public WebElement insurenceLastPaymentdate() {

		return getWebElement(d, "insurenceLastPaymentdate_TAI", TransactionPage, null);
	}

	public WebElement insurencePolicyNumber() {

		return getWebElement(d, "insurenceLastPaymentdate_TAI", TransactionPage, null);
	}

	public WebElement insurenceLastPremiumAmount() {

		return getWebElement(d, "insurenceLastPremiumAmount_TAI", TransactionPage, null);
	}

	public List<WebElement> moreBtn_Accounts() {

		return getWebElements(d, "moreBtn_Accounts", "ManageSharing", null);
	}

	public List<WebElement> transactionAccountSetting() {

		return getWebElements(d, "transactionAccountSetting", TransactionPage, null);
	}

	public WebElement acntNickNameTxtBox() {

		return getWebElement(d, "acntNickNameTxtBox", "AccountsPage", null);
	}

	public WebElement saveChangesBtn() {

		return getWebElement(d, "saveChangesBtn", "AccountsPage", null);
	}

	public void nickenameAdd(int accountorder, String nickName, int Setting) {
		WebDriverWait wait = new WebDriverWait(d, 9);
		WebElement more = wait.until(ExpectedConditions.visibilityOf(moreBtn_Accounts().get(accountorder)));
		click(more);
		click(transactionAccountSetting().get(Setting));
		WebElement name = wait.until(ExpectedConditions.visibilityOf(acntNickNameTxtBox()));
		name.clear();
		name.sendKeys(nickName);
		click(saveChangesBtn());
		waitUntilSpinnerWheelIsInvisible();
	}

	public List<WebElement> TransactionAccountSetting() {

		return getWebElements(d, "TransactionAccountSetting", TransactionPage, null);
	}

	public List<WebElement> TransactionAccountSettingIcon() {

		return getWebElements(d, "TransactionAccountSettingIcon", TransactionPage, null);
	}

	public WebElement TransactionAccountClose() {

		return getWebElement(d, "TransactionAccountClose", TransactionPage, null);
	}

	public WebElement TransactionAccountCloseBtn() {

		return getWebElement(d, "TransactionAccountCloseBtn", TransactionPage, null);
	}

	public WebElement TransactionAccountIHMessage1() {

		return getWebElement(d, "TransactionAccountIHMessage1", TransactionPage, null);
	}

	public WebElement TransactionAccountIHMessage2() {

		return getWebElement(d, "TransactionAccountIHMessage2", TransactionPage, null);
	}

	public WebElement RealEstateaccount_TAI() {

		return getWebElement(d, "RealEstateaccount_TAI", TransactionPage, null);
	}

	public WebElement RealEstateAmount_TAI() {

		return getWebElement(d, "RealEstateAmount_TAI", TransactionPage, null);
	}

	public WebElement TransactionAccountCloseToggelButton() {

		return getWebElement(d, "TransactionAccountCloseToggelButton", TransactionPage, null);
	}

	public WebElement saveChangesBtn1() {

		return getWebElement(d, "saveChangesBtn", "AccountsPage", null);
	}

	public void closeAccount(int accountRow) {
		click(TransactionAccountSettingIcon().get(accountRow));
		click(TransactionAccountClose());
		click(TransactionAccountCloseBtn());
	}

	public void closeAccountsAccountSummery(int accountRow) {
		click(TransactionAccountSettingIcon().get(accountRow));
		click(TransactionAccountCloseToggelButton());
		click(saveChangesBtn1());
		waitUntilSpinnerWheelIsInvisible();
	}

	// nov release
	public WebElement AccSettCatField() {

		return getWebElement(d, "AccSettCatField", "Transaction", null);
	}

	public WebElement AccSettCatSearchField() {

		return getWebElement(d, "AccSettCatSearchField", "Transaction", null);
	}

	public WebElement AccSettCatSearcheTextCLose() {

		return getWebElement(d, "AccSettCatSearcheTextCLose", "Transaction", null);
	}

	public List<WebElement> AccSettCatSearchedHLC() {

		return getWebElements(d, "AccSettCatSearchedHLC", "Transaction", null);
	}

	public List<WebElement> AccSettCatSearchedMLC() {

		return getWebElements(d, "AccSettCatSearchedMLC", "Transaction", null);
	}

	public List<WebElement> AccSettCatSearchedHLCHL() {

		return getWebElements(d, "AccSettCatSearchedHLCHL", "Transaction", null);
	}

	public List<WebElement> AccSettCatSearchedMLCHL() {

		return getWebElements(d, "AccSettCatSearchedMLCHL", "Transaction", null);
	}

	public WebElement AccSettCatNocatAvailabel() {

		return getWebElement(d, "AccSettCatNocatAvailabel", "Transaction", null);
	}

	public List<WebElement> AccSettCatSearchedSubCat() {

		return getWebElements(d, "AccSettCatSearchedSubCat", "Transaction", null);
	}

	public List<WebElement> AccSettCatSearchedSubCatHL() {

		return getWebElements(d, "AccSettCatSearchedSubCatHL", "Transaction", null);
	}

	public void accSettCatSearchField(int accountorder, int Setting) {
		WebDriverWait wait = new WebDriverWait(d, 12);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(moreBtn_Accounts().get(accountorder)));
		click(element1);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(transactionAccountSetting().get(Setting)));
		click(element2);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(AccSettCatField()));
		click(element3);
	}

	public void categorySearch(String category) {
		WebDriverWait wait = new WebDriverWait(d, 8);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(AccSettCatSearchField()));
		element1.clear();
		element1.sendKeys(category);
	}

	public void clickAccountRow(String account) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(AccountrowsName(account)));
		click(element1);
		waitFor(2);
	}

	public void clickBackToAccount() {
		WebDriverWait wait = new WebDriverWait(d, 8);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(backToAccount()));
		click(element1);
		waitFor(2);
	}

	public void clickExpandAccDetails() {

		WebDriverWait wait = new WebDriverWait(d, 8);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(expand()));
		click(element1);
		waitFor(2);
	}

}
