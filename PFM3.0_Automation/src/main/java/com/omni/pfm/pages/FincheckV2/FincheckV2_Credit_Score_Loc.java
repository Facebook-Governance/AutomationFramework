
package com.omni.pfm.pages.FincheckV2;

import java.util.List;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;
/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
public class FincheckV2_Credit_Score_Loc {
	WebDriver d;
	String pageName, frameName;

	public FincheckV2_Credit_Score_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	public WebElement getCreditScoreIndicator() {
		return SeleniumUtil.getWebElement(d, "finV2-credit-score-indicator-title", pageName, frameName);
	}

	public WebElement getAboutScoreLink() {
		return SeleniumUtil.getWebElement(d, "finV2-text-about-score", pageName, frameName);
	}

	/**
	 * Locator files related to Credit Score - About Score section
	 * 
	 * @author sbhat1
	 * 
	 */

	public String getStatusTextAboutScore() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-status-text", pageName, frameName).getText().trim();
	}

	public WebElement getBackLink() {
		return SeleniumUtil.getWebElement(d, "finV2-link-back", pageName, frameName);
	}

	public WebElement getWhyThisIsText() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-why-text", pageName, frameName);
	}

	public String getWhyThisDescription() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-why-this-desc-text", pageName, frameName).getText()
				.trim();
	}

	public WebElement getHowThisHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-how-text", pageName, frameName);
	}

	public WebElement getScoreImage() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-scoreImage", pageName, frameName);
	}

	public String getScoreFactorHeaderText() {
		return SeleniumUtil.getWebElement(d, "finV2-about-score-factor-details", pageName, frameName).getText().trim();
	}

	/**
	 * @author rprakash1
	 */

	public WebElement creditScoreindicatoroverview() {
		return SeleniumUtil.getWebElement(d, "finV2-creditscoreindicator", pageName, frameName);
	}

	public WebElement getTakeActionHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-creditscoreindicator-takeaction", pageName, frameName);
	}

	public List<WebElement> getTakeActiondesc1() {

		return SeleniumUtil.getWebElements(d, "finV2-creditind-takeactiondesc1", pageName, frameName);

	}

	public List<WebElement> getTakeActiondesc2() {

		return SeleniumUtil.getWebElements(d, "finV2-creditind-takeactiondesc2", pageName, frameName);

	}

	public List<WebElement> getTakeActiondesc3() {

		return SeleniumUtil.getWebElements(d, "finV2-creditind-takeactiondesc3", pageName, frameName);

	}

	public WebElement creditScoredetails() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-scoredetails", pageName, frameName);

	}

	public WebElement creditScoredetailssec() {

		return SeleniumUtil.getWebElement(d, "finv2-creditscore-scoredetailsheader", pageName, frameName);

	}

	public WebElement creditScoredetailssec1() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-scoredetailssection1", pageName, frameName);

	}

	public WebElement creditScoredetailssec2() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-scoredetailssection2", pageName, frameName);

	}

	public WebElement creditScoreseheader() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-settingsscreenheader", pageName, frameName);

	}

	public List<WebElement> creditScoreoptions() {

		return SeleniumUtil.getWebElements(d, "finV2-creditind-crdscoreoptions", pageName, frameName);

	}

	public List<WebElement> creditScoreoptionsradbtn() {

		return SeleniumUtil.getWebElements(d, "finV2-creditind-crdscoreoptionsradbtn", pageName, frameName);

	}

	public WebElement creditScoresupdatebtn() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-crdscoreupdatebtn", pageName, frameName);

	}

	public WebElement creditScorecancelbtn() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-crdscorecancelbtn", pageName, frameName);

	}

	public WebElement creditScorebacklink() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-crdsetscreenbacklink", pageName, frameName);

	}

	public WebElement creditScoreheadertext() {

		return SeleniumUtil.getWebElement(d, "finV2-creditind-headertext", pageName, frameName);

	}
	
	 public boolean isfinV2_mobile_creditind_closeicon_Present()
		{
			return PageParser.isElementPresent("finV2-mobile-creditind-closeicon", pageName, null);
		}
	 
	 public WebElement mobileCloseicon() {

			return SeleniumUtil.getWebElement(d, "finV2-mobile-creditind-closeicon", pageName, frameName);

		}
	 
	 public boolean isfinV2_mobile_creditind_values_Present()
		{
			return PageParser.isElementPresent("finV2-mobile-creditind-mobilevalues1", pageName, null);
		}
	 
	 public List<WebElement> mobileCreditvalues() {

			return SeleniumUtil.getWebElements(d, "finV2-mobile-creditind-mobilevalues", pageName, frameName);

		}
	 
	 public boolean isfinV2_mobile_creditind_backicon()
	 {
	 	return PageParser.isElementPresent("finV2-mobile-creditind-mobilebackicon", pageName, null);
	 }
	 
	 public WebElement mobileBackicon() {

		 return SeleniumUtil.getWebElement(d, "finV2-mobile-creditind-mobilebackicon", pageName, frameName);

		 }
	 
	 public boolean isfinV2_mobile_creditind_options()
	 {
	 	return PageParser.isElementPresent("finV2-mobile-creditind-options", pageName, null);
	 }
	 
	 
	 public List<WebElement> creditScoreoptionsmobile() {

			return SeleniumUtil.getWebElements(d, "finV2-mobile-creditind-options", pageName, frameName);

		}
		
	

}
