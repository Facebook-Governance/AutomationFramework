/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.FinCheckV2;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Onboarding_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Settings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Settings_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Settings_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Settings_Loc settings;
	FincheckV2_Onboarding_Loc onboard;
	String platformName = null;
	

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		settings = new FincheckV2_Settings_Loc(d);
		onboard = new FincheckV2_Onboarding_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.spDagSite"),
				PropsUtil.getDataPropertyValue("fiV2.spDagPassword"));
	}

	@Test(description = "AT-97555,AT-95252:Finish quick onboarding using the utility", priority = 1)
	public void quickOnboarding() {
		logger.info("Finishing quick onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear.1990"),
				PropsUtil.getDataPropertyValue("finV2.income.5k"), 1, 4);

		Assert.assertTrue(settings.getSettingsLink().isDisplayed(), "*** Header mismatch.");

	}

	@Test(description = "AT-95010,AT-97552:Verify the click navigates to the settings page", priority = 2, dependsOnMethods = "quickOnboarding")
	public void verifySettingsPage() {
		SeleniumUtil.click(settings.getSettingsLink());
		logger.info(">>>>> verifying the page header");
		Assert.assertEquals(settings.getSettingsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.settings.pageHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-97553,AT-95012:Verify available account header in settings page", priority = 3, dependsOnMethods = "verifySettingsPage")
	public void verifyAvailabelAccountHeader() {
		logger.info(">>>>> Verifying available account header");
		Assert.assertEquals(settings.getAvailableAccountHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.settings.availableAccHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-95254,AT-97550:Verify the available accounts details", priority = 4, dependsOnMethods = "verifySettingsPage")
	public void verifyAvailableAcoountsDetails() {
		logger.info(">>>>> getting the expected");
		String expectedAccounts[] = PropsUtil.getDataPropertyValue("finV2.settings.availableAccountTypes").split(",");
		List<WebElement> actualAccounts = settings.getAvailableAccTypes();

		for (int count = 0; count < actualAccounts.size(); count++) {
			Assert.assertEquals(actualAccounts.get(count).getText().trim(), expectedAccounts[count].trim(), "*** Accounts mismatch.");
		}

		logger.info(">>>>> Expected accounts found");
	}

	@Test(description = "AT-95011,AT-95253:Verify edit accounts link ", priority = 5, dependsOnMethods = "verifySettingsPage")
	public void verifyEditAccounts() {
		logger.info(">>>>> Verifying edit accounts button");
		Assert.assertTrue(settings.getEditAccountButton().isDisplayed(), "Edit Account button not displayed");
	}

	@Test(description = "AT-97551:Verify clicking on edit accounts navigates to the accounts settings page", priority = 6, dependsOnMethods = "verifySettingsPage")
	public void verifyEditAccountsAction() {
		logger.info(">>>>> Clicking on edit accounts button");
		SeleniumUtil.click(settings.getEditAccountButton());
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertEquals(settings.getAccountSettingsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.settings.AccSettingsPageHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-95007,AT-97549,AT-95006:Verify back button is displayed in account settings page", priority = 7, dependsOnMethods = "verifyEditAccountsAction")
	public void verifyBackLinkInAccSettings() {
		logger.info(">>>>> Verifying the back to fincheck link in account settings page");
		if(settings.isfinV2_mobile_settings_backicon_Present())
		{
			settings.mobileBacklink().isDisplayed();
		} else {
			
		
		Assert.assertEquals(settings.getBackLink().getText().trim(),
		
				PropsUtil.getDataPropertyValue("finV2.settings.BackLink"), "*** Back Link mismatch.");
		}
	}

	@Test(description = "AT-95248,AT-95009:Verify Back to Fincheck link will navigate back to Fincheck Settings screen", priority = 8, dependsOnMethods = "verifyEditAccountsAction")
	public void verifyBackToFinLink() {
		logger.info(">>>>> Clicking on Back to Fincheck Link");
		if(settings.isfinV2_mobile_settings_backicon_Present())
		{
			settings.mobileBacklink().isDisplayed();
		}
		SeleniumUtil.click(settings.getBackLink());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(settings.getSettingsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.settings.pageHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-97547,AT-95008:Verify personal information section", priority = 9, dependsOnMethods = "verifyBackToFinLink")
	public void verifyPersonalInfoHeader() {
		logger.info(">>>>> Verifying the personal information header");
		logger.info(">>>>> Verifying Personal Information text");
		Assert.assertEquals(onboard.getPersonalInfoText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.personalInfoText"),
				" Personal Information text mismatch.");
	}

	@Test(description = "AT-97548,AT-95047:Verify Year of birth label", priority = 10, dependsOnMethods = "verifyBackToFinLink")
	public void verifyYearOfBirthLabel() {
		logger.info(">>>>> Verifying Year of Birth Label for the dropdown");
		Assert.assertEquals(onboard.getYearOfBirthLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.yearOfBirthLabel"), " Year of Birth label not found");
	}

	@Test(description = "AT-95961,AT-96258:Verify Annual Income label", priority = 11, dependsOnMethods = "verifyBackToFinLink")
	public void verifyAnnualIncomeLabel() {
		logger.info(">>>>> Verifying Year of Birth Label for the dropdown");
		Assert.assertEquals(onboard.getAnualIncomeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.anualIncomeLabel"), " Anual Income label not found");
	}

	@Test(description = "AT-95046,AT-95962:Verify Credit Score Income label", priority = 12, dependsOnMethods = "verifyBackToFinLink")
	public void verifyCreditScoreLabel() {
		logger.info(">>>>> Verifying Credit Score Label for the dropdown");
		Assert.assertEquals(onboard.getCreditScoreLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.creditScoreLabel"), " Credit Score not found");
	}

	@Test(description = "AT-96259,AT-95049,AT-96256:Verify Year of Birth selection", priority = 13, dependsOnMethods = "verifyBackToFinLink")
	public void verifyYOBSelection() {
		logger.info(">>>>> Verifying the year of birth selection");
		SeleniumUtil.click(onboard.getYOBDropDown());
		SeleniumUtil.waitForPageToLoad(2000);
		v2GetStartd.selectYearOfBirth("1992");

		Assert.assertEquals(onboard.getYODDropdownSelectedValue().getText().trim(), "1992", "Year of birth mismatch");
	}

	@Test(description = "AT-97224,AT-95048:Verify House Hold Status section", priority = 14, dependsOnMethods = "verifyBackToFinLink")
	public void verifyHouseHoldHeader() {
		logger.info(">>>>> Verifying House hold status header");
		Assert.assertEquals(onboard.getHouseHoldStatusHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.houseHoldStatusHeader").trim());
	}

	@Test(description = "AT-95960,AT-96257:Verify Insurance Section", priority = 15, dependsOnMethods = "verifyBackToFinLink")
	public void verifyInsuranceHeader() {
		logger.info(">>>>> Verifying Insurance header");
		Assert.assertEquals(onboard.getInsuranceHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.setting.insuranceStatusHeader").trim(), "*** Header mismatch.");
	}

	@Test(description = "AT-97225,AT-95043:Verify the household section", priority = 16, dependsOnMethods = "verifyBackToFinLink")
	public void verifyHouseholdSection() {
		logger.info(">>>>> Verifying hosehold section values");
		String houseHoldValues[] = PropsUtil.getDataPropertyValue("finV2.onboarding.householdValues").split(",");
		List<WebElement> houseHold = onboard.getHouseHoldValues();

		logger.info(">>>>> Verifying the house hold status value");
		for (int house = 0; house < houseHold.size(); house++) {
			Assert.assertEquals(houseHold.get(house).getText().trim(), houseHoldValues[house].trim(), "*** Householdvalue mismatch.");
		}
	}

	@Test(description = "AT-95164,AT-95285,AT-96254:Verify the radio buttons in household status", priority = 17, dependsOnMethods = "verifyBackToFinLink")
	public void verifyToggleButtons() {
		logger.info(">>>>> Verifying toggle buttons.");
		Assert.assertEquals(onboard.getHouseHoldRadioButtons().size(), onboard.getHouseHoldValues().size());

		for (int i = 0; i < onboard.getHouseHoldRadioButtons().size(); i++) {
			Assert.assertEquals(onboard.getHouseHoldRadioButtons().get(i).getAttribute("aria-checked"), "false", "*** Header mismatch.");
		}
	}

	@Test(description = "AT-95042,AT-95163:Verifying the Insurance checkboxes", priority = 18, dependsOnMethods = "verifyBackToFinLink")
	public void verifyInsuranceCheckboxes() {
		logger.info(">>>>> verifying insurance checkboxes");
		Assert.assertEquals(onboard.getInsuranceRadioButtons().size(), 6, "*** Insurance options mismatch.");
	}

	@Test(description = "AT-95284,AT-95255:Verifying the insurances available", priority = 19, dependsOnMethods = "verifyBackToFinLink")
	public void verifyInsurancesValue() {
		logger.info(">>>>> Verifying Insurance Values");
		String insurance[] = PropsUtil.getDataPropertyValue("finV2.onboarding.insuranceValues").split(",");
		List<WebElement> insuranceAvailable = onboard.getInsuranceRadioButtons();
		for (int i = 0; i < insuranceAvailable.size(); i++) {
			Assert.assertEquals(insuranceAvailable.get(i).getText().trim(), insurance[i].trim(), "*** Insurance mismatch.");
		}
	}

	@Test(description = "AT-96255,AT-95045:Verify selecting the toggle button will enable the respective insurance section", priority = 20, dependsOnMethods = "verifyBackToFinLink")
	public void verifyEnablingToggle1() {
		logger.info(">>>>> Verifying the toggle button 1 - Employed");
		SeleniumUtil.click(onboard.getHouseHoldRadioButtons().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(onboard.getHouseHoldRadioButtons().get(0).getAttribute("aria-checked"), "true");

		logger.info(">>>>> Verifying disability checkox is enabled or not");
		Assert.assertTrue(onboard.getDisabilityCheckbox().isEnabled(), "*** Button enable mismatch.");
	}

	@Test(description = "AT-95287,AT-96252:Verify unselecting the toggle button disable the respective insurance", priority = 21, dependsOnMethods = "verifyBackToFinLink")
	public void verifyDisablingToggleButton1() {
		logger.info(">>>>> CLicking on Employed toggle button");
		SeleniumUtil.click(onboard.getHouseHoldRadioButtons().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(onboard.getHouseHoldRadioButtons().get(0).getAttribute("aria-checked"), "false", "*** Aria not checked.");
	}

	@Test(description = "AT-95044,AT-95165:Verify the selection and enabling of the toggle button", priority = 22, dependsOnMethods = "verifyBackToFinLink")
	public void verifyToggleButtonsBehavior() {
		logger.info(">>>>> verifying the toggle buttons");
		SeleniumUtil.click(onboard.getHouseHoldRadioButtons().get(1));
		Assert.assertTrue(onboard.getLifeCheckbox().isEnabled(), "*** Life check box not enabled mismatch.");
	}

	@Test(description = "AT-95286,AT-96253:Verify the selection and enabling onf toggle button", priority = 23, dependsOnMethods = "verifyBackToFinLink")
	public void verifyToggleButtonBehavior2() {
		logger.info(">>>>> Selecting life insurance checkbox");
		SeleniumUtil.click(onboard.getLifeCheckbox());
		Assert.assertEquals(onboard.getLifeCheckbox().getAttribute("aria-checked"), "true", "*** Check box not checked.");
	}

	@Test(description = "AT-96250,AT-96251:Verify the planning header", priority = 24, dependsOnMethods = "verifyBackToFinLink")
	public void verifyPlaningHeader() {
		logger.info(">>>>> Verifying planning header");
		Assert.assertTrue(onboard.getStep4Header().isDisplayed(), "Planning header is not present");
	}

	@Test(description = "AT-95041,AT-95040:Verify the planning header text", priority = 25, dependsOnMethods = "verifyBackToFinLink")
	public void verifyPlaningHeaderText() {
		logger.info(">>>>> Verifying planning header");
		Assert.assertEquals(onboard.getStep4Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.settings.step4.headerText").trim());
	}

	@Test(description = "AT-95161:Verify Plannings list available", priority = 26, dependsOnMethods = "verifyBackToFinLink")
	public void verifyPlanningList() {
		logger.info(">>>>> Verifying the plans list");
		List<WebElement> plansAvailable = onboard.getPlansList();
		String plansExpected[] = PropsUtil.getDataPropertyValue("finV2.onboaring.plansList").split(",");

		for (int plan = 0; plan < plansAvailable.size(); plan++) {
			logger.info(">>>>Verifying the plan available");
			Assert.assertEquals(plansAvailable.get(plan).getText().trim(), plansExpected[plan].trim());
			SeleniumUtil.waitForPageToLoad(1000);
		}
	}

	@Test(description = "AT-95954,AT-95039:Verifying the planning radio buttons available", priority = 27, dependsOnMethods = "verifyBackToFinLink")
	public void verifyPlanningRadioButtons() {
		logger.info(">>>>> Verifying the planning radio buttons");
		Assert.assertEquals(onboard.getPlanningRadioButtons().size(), 5, "*** Radi button mismatch.");
	}

	@Test(description = "AT-95952,AT-96249:Verify 4th Radio button is checked by default", priority = 28, dependsOnMethods = "verifyBackToFinLink")
	public void verifyCheckedRadioButton() {
		logger.info("Verfiying radio button 4 is checked by default");
		Assert.assertEquals(onboard.getPlanningRadioButtons().get(3).getAttribute("aria-checked"), "false", "*** Radio button not checked");
	}

	@Test(description = "AT-95953,AT-95036:Verify Selecting the first option will enable habits section", priority = 29, dependsOnMethods = "verifyBackToFinLink")
	public void verifyHabitsSection() {
		logger.info(">>>>> Clicking on first radio button");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(onboard.getStep5Header().isDisplayed(), "Planning section not displayed");

	}

	@Test(description = "AT-95950:Verify Available habits", priority = 30, dependsOnMethods = "verifyHabitsSection")
	public void verifyAvailableHabits() {
		logger.info(">>>>> Verifying the habits");
		List<WebElement> availableHabits = onboard.getHabitsList();
		String expectedHabits[] = PropsUtil.getDataPropertyValue("finV2.onboaring.habitsList").split(",");

		for (int i = 0; i < 3; i++) {
			try {
				Assert.assertEquals(availableHabits.get(i).getText().trim(), expectedHabits[i].trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			SeleniumUtil.waitForPageToLoad(1000);
		}
	}

	@Test(description = "AT-96247,AT-95035:Verify Checkboxes available", priority = 31, dependsOnMethods = "verifyHabitsSection")
	public void verifyCheckBoxesAvailable() {
		logger.info(">>>>> Verifying the checkboxes");
		for (int i = 0; i < onboard.getCheckboxesStep5().size(); i++) {
			Assert.assertEquals(onboard.getCheckboxesStep5().get(i).getAttribute("aria-checked"), "false", "*** Checekbox not checked..");
		}
	}
}
