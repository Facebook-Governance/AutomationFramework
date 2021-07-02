/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.FincheckV2;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Insurance_Loc {
	WebDriver d;
	String pageName, frameName;
	AccountAddition accountAddition;

	public FincheckV2_Insurance_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
		accountAddition = new AccountAddition();
	}

	public WebElement insuranceIndicatoroverview() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator", pageName, frameName);
	}

	public WebElement getTakeActionHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator-takeaction", pageName, frameName);
	}

	public WebElement getTakeActiondesc1() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator-takeactiondesc1", pageName, frameName);
	}

	public WebElement getTakeActiondesc2() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator-takeactiondesc2", pageName, frameName);
	}

	public WebElement getTakeActiondesc3() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator-takeactiondesc3", pageName, frameName);
	}

	public WebElement insuranceScoredetails() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator-scoredetails", pageName, frameName);
	}

	public WebElement insuranceStatusbartext() {
		return SeleniumUtil.getWebElement(d, "finV2-insuranceindicator-statusbartext", pageName, frameName);
	}

	public WebElement insuranceScoredetailssec() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-scoredetailsheader", pageName, frameName);
	}

	public WebElement insuranceScoredetailssec1() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-scoredetailsdesction1", pageName, frameName);
	}

	public WebElement insuranceScoredetailssec2() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-scoredetailsdesction2", pageName, frameName);
	}

	public WebElement insuranceScoredetailssec3() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-scoredetailsdesction3", pageName, frameName);
	}

	public WebElement insuranceSuggestedcount() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-suggestedinsurancecount", pageName, frameName);
	}

	public WebElement insuranceCoveragescreen() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-insurancecoveragescreen", pageName, frameName);
	}

	public WebElement insuranceCoveragescreentype() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-insurancecoveragetype", pageName, frameName);
	}

	public WebElement insuranceCoveragescreencoverage() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-insurancecoveragetext", pageName, frameName);
	}

	public List<WebElement> insuranceDetailsheader() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-insurancedetailsheader", pageName, frameName);
	}

	public List<WebElement> insuranceDetailstext() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-insurancedetailstext", pageName, frameName);
	}

	public List<WebElement> insuranceCrossicon() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-insurancedetailscrossicon", pageName,
				frameName);
	}

	public List<WebElement> insuranceNaicon() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-insurancedetailnaicon", pageName, frameName);
	}

	public WebElement updateInsurancebutton() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-updateinsurancebtn", pageName, frameName);
	}

	public WebElement insuranceInfo() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-insuranceinfoscreen", pageName, frameName);
	}

	public WebElement insuranceHouseholdsection() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-insurancehousehold", pageName, frameName);
	}

	public List<WebElement> insuranceHouseholdvalues() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-insurancehouseholdvalues", pageName, frameName);
	}

	public List<WebElement> insuranceOptions() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-insuranceoptions", pageName, frameName);
	}

	public WebElement insuranceOptionschkbox() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-insuranceoptionschkbox", pageName, frameName);
	}

	public WebElement homeownerInsuranceoptionschkbox() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-homeownersinsuranceoptionschkbox", pageName,
				frameName);
	}

	public List<WebElement> houseHoldtogglebuttons() {
		return SeleniumUtil.getWebElements(d, "finv2-insuranceindicator-hosueholdtogglebutton", pageName, frameName);
	}

	public WebElement insuranceCancelbtn() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-cancelbutton", pageName, frameName);
	}

	public WebElement insuranceUpdatebtn() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-updatebutton", pageName, frameName);
	}

	public WebElement backLink() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-backlink", pageName, frameName);
	}

	public WebElement navigateBacktoindscreen() {
		return SeleniumUtil.getWebElement(d, "finv2-insuranceindicator-navigatebacktoindscreen", pageName, frameName);
	}

	public WebElement mobileCloseicon() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-creditind-closeicon", pageName, frameName);

	}

	public boolean isfinV2_mobile_creditind_closeicon_Present() {
		return PageParser.isElementPresent("finV2-mobile-creditind-closeicon", pageName, null);
	}

	public boolean isfinV2_mobile_creditind_backicon() {
		return PageParser.isElementPresent("finV2-mobile-insuranceind-mobilebackicon", pageName, null);
	}

	public WebElement mobileBackicon() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-insuranceind-mobilebackicon", pageName, frameName);

	}

}
