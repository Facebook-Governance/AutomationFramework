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

import com.gargoylesoftware.htmlunit.Page;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Overview_Loc {
	WebDriver d;
	String pageName, frameName;
	AccountAddition accountAddition;

	public FincheckV2_Overview_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
		accountAddition = new AccountAddition();
	}

	public WebElement fincheckHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_Headertext", pageName, null);
	}

	public WebElement settingsBtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_SettingsLink", pageName, null);
	}

	public WebElement healthStatusText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_HealthStatusText", pageName, null);
	}

	public WebElement healthStatusScore() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_HealthStatusScore", pageName, null);

	}

	public WebElement finCheckfinv2_OverviewDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_OverviewDesc", pageName, null);

	}

	public WebElement spendingHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_SpendingHeaderText", pageName, null);

	}

	public WebElement savingHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_SavingHeaderText", pageName, null);

	}

	public WebElement barrowingHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_BarrowingHeaderText", pageName, null);

	}

	public WebElement planningHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_PlanningHeaderText", pageName, null);

	}

	public WebElement spendToIncomeTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_SpendtoIncomeTitle", pageName, null);

	}

	public WebElement spendToIncomeValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_SpendtoIncomeValue", pageName, null);

	}

	public WebElement billpayTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_BillpayTitle", pageName, null);

	}

	public WebElement billpayValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_BillpayValue", pageName, null);

	}

	public WebElement emergencySavingsTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_EmergencySavingsTitle", pageName, null);

	}

	public WebElement emergencySavingsValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_EmergencySavingsValue", pageName, null);

	}

	public WebElement retirementSavingsTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_RetirementSavingsTitle", pageName, null);

	}

	public WebElement retirementSavingsValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_RetirementSavingsValue", pageName, null);

	}

	public WebElement debtToIncomeTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_DebtToIncomeTitle", pageName, null);

	}

	public WebElement debtToIncomeValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_DebtToIncomeValue", pageName, null);

	}

	public WebElement creditscoreTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_CreditScoreTitle", pageName, null);

	}

	public WebElement creditscoreValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_CreditScoreValue", pageName, null);

	}

	public WebElement insuranceTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_InsuranceTitle", pageName, null);

	}

	public WebElement insuranceValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_InsuranceValue", pageName, null);

	}

	public WebElement planningValue() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_PlanningValue", pageName, null);

	}

	public WebElement planningTitle() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_PlanningTitle", pageName, null);

	}

	public WebElement OverviewDisclosueText() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_DisclosureText", pageName, null);

	}

	public WebElement backLinkinSettingspage() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_SettingsBacklink", pageName, null);

	}

	public WebElement dialGuageoverviewscreenv2() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_DialGuagedisplayed", pageName, null);

	}

	public WebElement dialGuageoverlaytext() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_dialguagetext", pageName, null);

	}

	public WebElement dialGuageoverlaybtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_dialguagebtn", pageName, null);

	}

	public WebElement creditScoreind() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_creditscoreind", pageName, null);

	}

	public WebElement updateCreditscorelink() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_creditscoreindlink", pageName, null);

	}

	public List<WebElement> creditScoreoptions()

	{

		return SeleniumUtil.getWebElements(d, "finv2_Overview_creditscoreoptions", pageName, null);

	}

	public WebElement updateCreditscorebtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_creditscoreupdbtn", pageName, null);

	}

	public WebElement PlanningScoreind() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_planningind", pageName, null);

	}

	public WebElement updatePlanninglink() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_planningindlink", pageName, null);

	}

	public List<WebElement> planningOptions()

	{

		return SeleniumUtil.getWebElements(d, "finv2_Overview_planningoptions", pageName, null);

	}

	public WebElement updatePlanningbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_planniingupdbtn", pageName, null);

	}

	public WebElement insuranceInd() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_insuranceind", pageName, null);

	}

	public WebElement updateInsurancelink() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_insuranceindlink", pageName, null);

	}

	public List<WebElement> insuranceOptions()

	{

		return SeleniumUtil.getWebElements(d, "finv2_Overview_insuranceoptions", pageName, null);

	}

	public WebElement updateInsurancebtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_insuranceupdbtn", pageName, null);

	}

	public WebElement retirementSavingsind() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_retirementsavingsind", pageName, null);

	}

	public WebElement retirementSavingsatssec() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_retirementsavingsabtthisscore", pageName, null);

	}

	public List<WebElement> retirementSavingactselection() {

		return SeleniumUtil.getWebElements(d, "finv2_Overview_retirementsavingsacctselection", pageName, null);

	}

	public WebElement retirementSavingssavechangesbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_retirementchgbtn", pageName, null);

	}

	public WebElement debtToincomeind() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_dtiindicator", pageName, null);

	}

	public WebElement debtToincomemonthlyincomesec() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_dtiindincomesection", pageName, null);

	}

	public WebElement debtToincomemonthlyincomefield() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_dtiincomefield", pageName, null);

	}

	public WebElement debtToincomesavechgbtn() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_savechangesbtn", pageName, null);

	}

	public WebElement backTovoerviewlink() {

		return SeleniumUtil.getVisibileWebElement(d, "finv2_Overview_backtooverviewlink", pageName, null);

	}

	public void healthStatusgood() {

		SeleniumUtil.click(creditScoreind());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(updateCreditscorelink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(creditScoreoptions().get(5));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(updateCreditscorebtn());

	}

	public void healthStatusfair() {

		SeleniumUtil.click(PlanningScoreind());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(updatePlanninglink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(planningOptions().get(3));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(updatePlanningbtn());

	}

	public void healthStatusAtrisk() {

		SeleniumUtil.click(insuranceInd());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(updateInsurancelink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(insuranceOptions().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(updateInsurancebtn());

	}

	public void healthStatusdanger() {

		SeleniumUtil.click(retirementSavingsind());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(retirementSavingsatssec());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(retirementSavingactselection().get(1));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(retirementSavingactselection().get(2));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(retirementSavingssavechangesbtn());
		SeleniumUtil.waitForPageToLoad();
		if(isfinV2_mobile_creditind_closeicon_Present())
		{
			SeleniumUtil.click(mobileCloseiconind5());
		} else {
		SeleniumUtil.click(backTovoerviewlink());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(debtToincomeind());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(debtToincomemonthlyincomesec());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(debtToincomemonthlyincomefield());
		debtToincomemonthlyincomefield().clear();
		SeleniumUtil.waitForPageToLoad();
		debtToincomemonthlyincomefield().sendKeys("1000");
		SeleniumUtil.click(debtToincomesavechgbtn());
		SeleniumUtil.waitForPageToLoad();
		}

	}
	
	public boolean isfinV2_mobile_overview_headertext()
	{
		return PageParser.isElementPresent("finV2-mobile-overview-headertext", pageName, null);
	}
	
	public WebElement overviewHeadertext() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-overview-headertext", pageName, frameName);

		}
	
	public boolean isfinV2_mobile_overview_settingslink()
	{
		return PageParser.isElementPresent("finV2-mobile-overview-settingslink", pageName, null);
	}
	
	public WebElement settingsLink() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-overview-settingslink", pageName, frameName);

		}
	
	public boolean isfinV2_mobile_overview_desclink()
	{
		return PageParser.isElementPresent("finV2-mobile-overview-desclink", pageName, null);
	}
	

	public WebElement descLink() {

	return SeleniumUtil.getWebElement(d, "finV2-mobile-overview-desclink", pageName, frameName);

	}
	
	public boolean isfinV2_mobile_overview_backlink()
	{
		return PageParser.isElementPresent("finV2-mobile-overview-backlink", pageName, null);
	}
	
	public WebElement backLink() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-overview-backlink", pageName, frameName);

		}
	
	public boolean isfinV2_mobile_creditind_closeicon_Present()
	{
		return PageParser.isElementPresent("finV2-mobile-onboarding-creditscorelabel", pageName, null);
	}
	

	   public WebElement mobileCloseicon() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-onboarding-creditscorelabel", pageName, frameName);
		
		
	   }
	   
	   public WebElement mobileCloseiconind5() {

			return SeleniumUtil.getWebElement(d, "finV2-mobile-overview-mobile-ind5closeicon", pageName, frameName);
			
			
		   }


}