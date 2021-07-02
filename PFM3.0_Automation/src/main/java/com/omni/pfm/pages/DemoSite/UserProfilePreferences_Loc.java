/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.DemoSite;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class UserProfilePreferences_Loc {
	
	WebDriver d;
	String pageName = "DemoSite";
	
	public UserProfilePreferences_Loc(WebDriver d) {
		this.d=d;
	}

	

	public WebElement preferencesSideHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfilePreferencesSideHeader", pageName, null);

	}

	public WebElement languageTextBox() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLanguageTextBox", pageName, null);

	}

	public WebElement languageTextBoxHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileLanguageTextBoxHeader", pageName, null);

	}

	public WebElement dateFormat() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDateFormat", pageName, null);

	}

	public WebElement dateFormatHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDateFormatHeader", pageName, null);

	}

	public WebElement timeZoneFormat() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileTimeZoneFormat", pageName, null);

	}

	public WebElement timeZoneFormatHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileTimeZoneFormatHeader", pageName, null);

	}

	public WebElement currencyField() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileCurrencyField", pageName, null);

	}

	public WebElement currencyFieldHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileCurrencyFieldHeader", pageName, null);

	}

	public WebElement digitGrpField() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDigitGrpField", pageName, null);

	}

	public WebElement digitGrpFieldHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDigitGrpFieldHeader", pageName, null);

	}

	public WebElement cancelBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileCancelBtn", pageName, null);

	}
	public WebElement decimalField() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDecimalField", pageName, null);

	}public WebElement decimalFieldHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDecimalFieldHeader", pageName, null);

	}public WebElement digitGrpSymbol() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDigitGrpSymbol", pageName, null);

	}public WebElement digitGrpSymbolHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileDigitGrpSymbolHeader", pageName, null);

	}public WebElement saveBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileSaveBtn", pageName, null);

	}public WebElement successMsg() {

		return SeleniumUtil.getVisibileWebElement(d, "ProfileSuccessMsg", pageName, null);

	}
	
	public List<WebElement>lang(){
		
		return SeleniumUtil.getWebElements(d, "ProfileLang", pageName, null);

	}

	public List<WebElement>datef(){
		
		return SeleniumUtil.getWebElements(d, "ProfileDatef", pageName, null);

	}

	public List<WebElement>timez(){
		
		return SeleniumUtil.getWebElements(d, "ProfileTimez", pageName, null);

	}

	public List<WebElement>currency(){
		
		return SeleniumUtil.getWebElements(d, "ProfileCurrency", pageName, null);

	}

	public List<WebElement>digitlist(){
		
		return SeleniumUtil.getWebElements(d, "ProfileDigitlist", pageName, null);

	}

	public List<WebElement>digitgrp(){
		
		return SeleniumUtil.getWebElements(d, "ProfileDigitgrp", pageName, null);

	}

	public List<WebElement>decimalsym(){
		
		return SeleniumUtil.getWebElements(d, "ProfileDecimalsym", pageName, null);

	}

}
