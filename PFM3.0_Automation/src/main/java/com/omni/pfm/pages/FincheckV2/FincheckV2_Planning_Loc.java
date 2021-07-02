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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Planning_Loc {
	WebDriver d;
	String pageName, frameName;
	AccountAddition accountAddition;

	public FincheckV2_Planning_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
		accountAddition = new AccountAddition();
	}

	public WebElement planningIndicatoroverview() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator", pageName, frameName);
	}

	public WebElement getTakeActionHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-takeaction", pageName, frameName);
	}

	public WebElement getTakeActiondesc1() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-takeactiondesc1", pageName, frameName);
	}

	public List<WebElement> getTakeActiondesc2() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-takeactiondesc2", pageName, frameName);
	}

	public List<WebElement> getTakeactioncaltitle() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-takeactiondesc3", pageName, frameName);
	}

	public List<WebElement> getTakeactioncaldesc() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-takeactiondesc4", pageName, frameName);
	}

	public WebElement getTakeActiondesc3() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-takeactiondesc3", pageName, frameName);
	}

	public WebElement getTakeActiondesc4() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-takeactiondesc4", pageName, frameName);
	}

	public WebElement getTakeActiondesc5() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-takeactiondesc5", pageName, frameName);
	}

	public WebElement getTakeActiondesc6() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-takeactiondesc6", pageName, frameName);
	}

	public WebElement planningScoredetails() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-scoredetails", pageName, frameName);
	}

	public WebElement planningStatusbartext() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-statusbartext", pageName, frameName);
	}

	public WebElement planningSaveforgoalbtn() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-sfgbutton", pageName, frameName);
	}

	public WebElement planningMoreresourcesbtn() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-morebutton", pageName, frameName);
	}

	public WebElement planningIndicatorheadertext() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-headertext", pageName, frameName);
	}

	public WebElement planningBacktofinchecklink() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-backtofinchecklink", pageName, frameName);
	}

	public WebElement planningIndicatorscoresection() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-scoresection", pageName, frameName);
	}

	public WebElement planningIndicatorscoresection1() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-scoresectiondesc1", pageName, frameName);
	}

	public WebElement planningIndicatorscoresection2() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-scoresectiondesc2", pageName, frameName);
	}

	public WebElement planningIndicatorscoresectionarrowlink() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-scoresectiondesc1arrow", pageName, frameName);
	}

	public WebElement planningIndicatorfincalheader() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-calculatorpageheader", pageName, frameName);
	}

	public WebElement planningIndicatorfincaldesc1() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-fincaldesc1", pageName, frameName);
	}

	public WebElement planningIndicatorfincaldesc3() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-fincaldesc3", pageName, frameName);
	}

	public WebElement planningIndicatorfincaldesc2() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-fincaldesc2", pageName, frameName);
	}

	public WebElement planningIndicatorfincalarrowlink() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-fincalarrowbtn", pageName, frameName);
	}

	public WebElement getEverFIContinueButton() {
		return SeleniumUtil.getWebElement(d, "finV2-everfi-next-button", pageName, frameName);
	}

	public WebElement planningSettingsheader() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-settingsscreenheader", pageName, frameName);
	}

	public WebElement planningSettingssubheader() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-planninghabitssection", pageName, frameName);
	}

	public List<WebElement> planningOptionssetscreen() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-planningoptions", pageName, frameName);
	}

	public List<WebElement> getCalculatorsList() {
		return SeleniumUtil.getWebElements(d, "finV2-calculators-list", pageName, frameName);
	}

	public List<WebElement> planningOptionssetscreenrdobtn() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-planningoptionsrdobtn", pageName, frameName);
	}

	public WebElement planningSettingsgoalsheader() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-planninggoalsection", pageName, frameName);
	}

	public List<WebElement> planningGoalsoptions() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-planninggoalhabitsoptions", pageName, frameName);
	}

	public List<WebElement> planningGoalsoptionschkbox() {
		return SeleniumUtil.getWebElements(d, "finV2-planningindicator-planninggoalschkbox", pageName, frameName);
	}

	public WebElement planningSettingsfutgoalsec() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-planningfutgoalssection", pageName, frameName);
	}

	public WebElement planningSettingcancelbtn() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-planningscreencancelbtn", pageName, frameName);
	}

	public WebElement planningSettingupdatebtn() {
		return SeleniumUtil.getWebElement(d, "finV2-planningindicator-planningscreenupdatebtn", pageName, frameName);
	}

	public WebElement planningSettingsbacklink() {
		WebDriverWait wait = new WebDriverWait(d, 10);
		return wait.until(ExpectedConditions.elementToBeClickable(
				SeleniumUtil.getWebElement(d, "finV2-planningindicator-backlinkinsetscreen", pageName, frameName)));
	}

	public List<WebElement> getAllRadioButton() {
		return SeleniumUtil.getWebElements(d, "finV2-plnning-radio-button", pageName, frameName);
	}
	
	public boolean isfinV2_mobile_creditind_closeicon_Present() {
		return PageParser.isElementPresent("finV2-mobile-creditind-closeicon", pageName, null);
	}

	public WebElement mobileCloseicon() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-creditind-closeicon", pageName, frameName);

	}
	
	public boolean isfinV2_mobile_planningind_backicon() {
		return PageParser.isElementPresent("finV2-mobile-planningind-mobilebackicon", pageName, null);
	}
	
	public WebElement mobileBackicon() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-planningind-mobilebackicon", pageName, frameName);

	}
	
	public boolean isfinV2_mobile_planningind_backicon1() {
		return PageParser.isElementPresent("finV2-mobile-planningind-mobilebackicon1", pageName, null);
	}
	
	public boolean isfinV2_mobile_planningind_backicon2() {
		return PageParser.isElementPresent("finV2-mobile-planningind-mobilebackicon2", pageName, null);
	}
	
	public WebElement mobileBackicon2() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-planningind-mobilebackicon2", pageName, frameName);

	}


}
