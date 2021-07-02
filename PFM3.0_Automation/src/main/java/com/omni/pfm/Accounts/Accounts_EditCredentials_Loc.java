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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_EditCredentials_Loc {
	Logger logger=LoggerFactory.getLogger(Accounts_EditCredentials_Loc.class);
	public WebDriver d =null;
	static String pageName = "AccountSettings"; 
	static String frameName = null;

	public Accounts_EditCredentials_Loc(WebDriver d){
		this.d = d;
	}
	public WebElement TitleText() {
		return SeleniumUtil.getVisibileWebElement(d, "titleText", pageName, frameName);
	}

	public WebElement EditText() {
		return SeleniumUtil.getVisibileWebElement(d, "editText", pageName, frameName);
	}

	public WebElement EditPopUpTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "editPopUpTitle", pageName, frameName);
	}

	public WebElement CloseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "closeIcon", pageName, frameName);
	}

	public WebElement CatalogText() {
		return SeleniumUtil.getVisibileWebElement(d, "catalogText", pageName, frameName);
	}
	public WebElement catalogTextSDGEnabled() {
		return SeleniumUtil.getVisibileWebElement(d, "catalogTextSDGEnabled", pageName, frameName);
	}

	public WebElement PasswordText() {
		return SeleniumUtil.getVisibileWebElement(d, "passwordText", pageName, frameName);
	}

	public WebElement ReEnterPasswordText() {
		return SeleniumUtil.getVisibileWebElement(d, "reEnterPasswordText", pageName, frameName);
	}

	public WebElement UpdatedButton() {
		return SeleniumUtil.getVisibileWebElement(d, "Update", pageName, frameName);
	}

	public WebElement EditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "editIcon", pageName, frameName);
	}

	public WebElement DeleteIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteIcon", pageName, frameName);
	}

	public WebElement DeleteText() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteText", pageName, frameName);
	}

	public WebElement DeleteTitle() {
		return SeleniumUtil.getVisibileWebElement(d, "popUpHeader", pageName, frameName);
	}

	public WebElement DeleteCloseIcon() {
		return SeleniumUtil.getWebElement(d, "popupCrossIcon", pageName, frameName);
	}

	public WebElement EditPopUpCloseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "editPopUpCloseIcon", pageName, frameName);
	}
	public WebElement submitButtonSDG() {
		return SeleniumUtil.getVisibileWebElement(d, "submitButtonSDG", pageName, frameName);
	}
	public WebElement CatalogBox() {
		return SeleniumUtil.getVisibileWebElement(d, "catalogBox", pageName, frameName);
	}

	public WebElement PasswordBox() {
		return SeleniumUtil.getVisibileWebElement(d, "passwordBox", pageName, frameName);
	}

	public WebElement ReEnterPasswordBox() {
		return SeleniumUtil.getVisibileWebElement(d, "reEnterPasswordBox", pageName, frameName);
	}

	public WebElement MobilemainHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "MobilemainHeader", pageName, frameName);
	}

	public WebElement titleTextMob() {
		return SeleniumUtil.getVisibileWebElement(d, "titleTextMob", pageName, frameName);
	}

	public WebElement editPopUpTitleMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "editPopUpTitleMobile", pageName, frameName);
	}

	public WebElement dagSiteMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "dagSiteMobile", pageName, frameName);
	}

	public WebElement catalogTextMobile() {
		return SeleniumUtil.getVisibileWebElement(d, "catalogTextMobile", pageName, frameName);
	}

	public WebElement CloseEditCredentials() {
		return SeleniumUtil.getVisibileWebElement(d, "CloseEditCredentials", pageName, frameName);
	}

	public WebElement EditIconOne() {
		return SeleniumUtil.getVisibileWebElement(d, "EditIconOne", pageName, frameName);
	}

	public WebElement deleteIconNew() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteIconNew", pageName, frameName);
	}
	public List<WebElement> AccountSetting_Mob() {
		return SeleniumUtil.getWebElements(d, "deleteIconNew", pageName, frameName);
	}	

	public void editCredentialHeader() {
		SeleniumUtil.click(EditText());
		SeleniumUtil.waitForPageToLoad(1000);
		String popUpTitle=EditPopUpTitle().getText();
		Assert.assertEquals(popUpTitle, PropsUtil.getDataPropertyValue("EditTitle"));
	}

	public void editCredentialHeaderMobile() {

		SeleniumUtil.click(dagSiteMobile());		
		SeleniumUtil.click(editPopUpTitleMobile());		
		String popUpTitle=editPopUpTitleMobile().getText();
		Assert.assertEquals(popUpTitle, PropsUtil.getDataPropertyValue("EditTitle"));
	}

	public void verifyEditCredentialPopup() {
		SeleniumUtil.click(EditText());
		SeleniumUtil.waitForPageToLoad(2000);
		String catalog = CatalogText().getAttribute("value");
		Assert.assertEquals(catalog, PropsUtil.getDataPropertyValue("CatalogText"));
	}
	public void verifyEditCredentialPopupSDG() {
		SeleniumUtil.click(EditText());
		SeleniumUtil.waitForPageToLoad(2000);
		String catalog = catalogTextSDGEnabled().getAttribute("value");
		Assert.assertEquals(catalog, PropsUtil.getDataPropertyValue("CatalogText"));
	}


	public void verifyEditIconAndText() {
		String editText=EditText().getText().trim();
		Boolean icon=EditIconOne().isDisplayed();
		Assert.assertEquals(editText, PropsUtil.getDataPropertyValue("EditTitle"));
		Assert.assertTrue(icon);
	}
	//Added By MRQA -- for traversing via more info acc settings.
	public WebElement moreText_LP() {
		return SeleniumUtil.getVisibileWebElement(d, "moreText_LP", pageName, frameName);
	}
	//Added By MRQA -- for traversing via more info acc settings.
	public WebElement Settings_LP() {
		return SeleniumUtil.getVisibileWebElement(d, "Settings_LP", pageName, frameName);
	}

}

