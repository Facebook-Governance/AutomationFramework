/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.DemoSite;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class DeleteMyAccount_Loc {

	WebDriver d;
	String pageName= "DemoSite";
	
	public DeleteMyAccount_Loc(WebDriver d) {
		
		this.d=d;
	}
	
	public WebElement linkDeleteAcct() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLinkDeleteAcct", pageName, null);

	}

	public WebElement linkBackToMyProfile() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLinkBackToMyProfile", pageName, null);

	}

	public WebElement iconBackToMyProfile() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccIconBackToMyProfile", pageName, null);

	}

	public WebElement lblDeleteMyAccount() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLblDeleteMyAccount", pageName, null);

	}

	public WebElement lblEnterPwdtoContinue() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLblEnterPwdtoContinue", pageName, null);

	}

	public WebElement txtboxPassword() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccTxtboxPassword", pageName, null);

	}

	public WebElement btnContinue() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccBtnContinue", pageName, null);

	}

	public WebElement errMsgEmptyPwd() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccErrMsgEmptyPwd", pageName, null);

	}

	public WebElement errMsgInvalidPwd() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccErrMsgInvalidPwd", pageName, null);

	}
	
	//message 1 " Oh no! You really want to leave us?"
	public WebElement msg1() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsg1", pageName, null);

	}
	//msg 2 "You are about to delete your MoneyCenter service account. Once you opt to delete your account, all your accounts, account data, information and configuration (including bills and transactions) will be permanently deleted.  "
	public WebElement msg2() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsg2", pageName, null);

	}
	
	//message asking reason "If this is a final goodbye,Please tell us why"

	public WebElement msgReasonHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgReasonHeader", pageName, null);

	}
	//message I didn't find it useful 

	public WebElement msgReason1() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgReason1", pageName, null);

	}
	
	//message I have a privacy/ security concern

	public WebElement msgReason2() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgReason2", pageName, null);

	}
	
	//message I am receiving too many notifications from MoneyCenter

	public WebElement msgReason3() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgReason3", pageName, null);

	}
	//message I found it difficult to use
	public WebElement msgReason4() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgReason4", pageName, null);

	}
	
	//message Other (Please help us understand your reason better)
	public WebElement msgReason5() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgReason5", pageName, null);

	}
	public WebElement checkBox1() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccCheckBox1", pageName, null);

	}
	
	public WebElement checkBox2() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccCheckBox2", pageName, null);

	}
	
	public WebElement checkBox3() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccCheckBox3", pageName, null);

	}
	
	public WebElement checkBox4() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccCheckBox4", pageName, null);

	}
	
	public WebElement checkBox5() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccCheckBox5", pageName, null);

	}

	// Link Please tell us why

	public WebElement linkPlsTellUsWhy() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLinkPlsTellUsWhy", pageName, null);

	}

	public WebElement txtboxOthers() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccTxtboxOthers", pageName, null);

	}

	public WebElement crossIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccCrossIcon", pageName, null);

	}

	public WebElement linkTransactions() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLinkTransactions", pageName, null);

	}

	public WebElement msgTransactions() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgTransactions", pageName, null);

	}
	
	public WebElement btnCancel() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccBtnCancel", pageName, null);

	}public WebElement btnDeleteAcct() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccBtnDeleteAcct", pageName, null);

	}public WebElement btnDeletePopup() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccBtnDeletePopup", pageName, null);

	}
	public WebElement msgHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgHeader", pageName, null);

	}public WebElement logo() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccLogo", pageName, null);

	}public WebElement msgDeleteConfirmation() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccMsgDeleteConfirmation", pageName, null);

	}public WebElement btnDone() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccBtnDone", pageName, null);

	}
	public WebElement headerTxn() {

		return SeleniumUtil.getVisibileWebElement(d, "DeleteAccHeaderTxn", pageName, null);

	}
	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
	  
		public String getRandomString(int n) {

				char[] chars = "abcdefghijklmnopqrstuvwxyz123456".toCharArray();
				StringBuilder sb = new StringBuilder();
				Random random = new Random();
				for (int i = 0; i < n; i++) {
					char c = chars[random.nextInt(chars.length)];
					sb.append(c);
				}
				return sb.toString();
			} 
		 

}
