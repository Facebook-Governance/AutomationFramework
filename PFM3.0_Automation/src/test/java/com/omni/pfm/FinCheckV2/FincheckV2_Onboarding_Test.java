package com.omni.pfm.FinCheckV2;

/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Onboarding_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Onboarding_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Onboarding_Test.class);
	AccountAddition accountAddition;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;
	FincheckV2_Onboarding_Loc onboard;
	FincheckV2_GetStarted_Loc getStart;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		accountAddition = new AccountAddition();
		onboard = new FincheckV2_Onboarding_Loc(d);
		getStart = new FincheckV2_GetStarted_Loc(d);
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);

		LoginPage.loginMain(d, loginParameter);

		if (PropsUtil.getEnvPropertyValue("cnf_newUserLogin").equals("yes")) {
			accountAddition.linkAccount();
			accountAddition.addAggregatedAccount1(
					PropsUtil.getDataPropertyValue("finV2.accountAdd.Onboarding.siteName"),
					PropsUtil.getDataPropertyValue("finV2.accountAdd.Onboarding.password"));
		}
	}

	@Test(description = "AT-94890:Navigate to fincheck v2 and Verify the fincheck onboarding screen", priority = 1)
	public void verifyOnboarding() {
		PageParser.forceNavigate("FincheckV2", d);
		logger.info(">>>>> verifying Welcome label");
		Assert.assertTrue(onboard.getWelcomeHeader().isDisplayed(), " Welcome header not displayed.");
	}

	@Test(description = "AT-94891:Verifying the 4 benifits and Paginations", dependsOnMethods = {
			"verifyOnboarding" }, priority = 2)
	public void verifyPagination() {
		logger.info(">>>>> Verifying 4 benifits / Pagination dots");
		int actualDots = onboard.getPaginations().size();
		Assert.assertEquals(actualDots, 4, "Pagination dot size is different than expected.");
	}

	@Test(description = "AT-94892:Verifying Welcome to Fincheck label", dependsOnMethods = {
			"verifyOnboarding" }, priority = 3)
	public void verifyWelcomeHeader() {
		logger.info(">>>>> verifying Welcome label");
		Assert.assertEquals(onboard.getWelcomeHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Onboarding.WelcomeHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-94893:Verify the benifits description present at first corousal", dependsOnMethods = {
			"verifyOnboarding" }, priority = 4)
	public void verifyBenifitsDesc() {
		logger.info(">>>>> Verifying benifits description.");
		Assert.assertTrue(onboard.getWelcomeDesc2().isDisplayed(), " Onboarding benifits description not displayeds..");
	}

	@Test(description = "AT-94894:Verify the pagination dot is pressed by default.", priority = 5, dependsOnMethods = {
			"verifyOnboarding" })
	public void verifyPaginationSelection() {
		logger.info(">>>>> Verifying pagination dot is selected by default.");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(onboard.getPaginations().get(0).getAttribute("aria-pressed"), "true",
				" Pagination dot 0 is not selected by default");
	}

	@Test(description = "AT-94895:Verify Get Started button is displayed.", priority = 6, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyGetStartedStep1() {
		logger.info(">>>>> Verifying Get Started button.");
		Assert.assertTrue(onboard.getStartedButton().isDisplayed(), " Get Started button not displayed in Step 1");
	}

	@Test(description = "AT-94898:Verify the secon corosal highlighted when navigated to second screen", priority = 7, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifySecondPagination() {
		logger.info(">>>>> Navigating to second page");
		SeleniumUtil.click(onboard.getRightArrow());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(onboard.getPaginations().get(1).getAttribute("aria-pressed"), "true",
				"Second pagination not highlighted by default.");

	}

	@Test(description = "AT-94897:Verifying the Welcome description at step 2", priority = 8, dependsOnMethods = {
			"verifySecondPagination" })
	public void verifyBenifitDescStep2() {
		logger.info(">>>>> Verifying the benifit description at step 2");
		Assert.assertEquals(onboard.getWelcomeDescStep2().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Onboarding.WelcomeDesc.Page2"),
				"Description at step 2 mismatched");
	}

	@Test(description = "AT-94899:Verifying get started button at step 2", priority = 9, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyGetStartedStep2() {
		logger.info(">>>>> Verifying Get Started button.");
		Assert.assertTrue(onboard.getStartedButton().isDisplayed(), " Get Started button not displayed in Step 1");
	}

	@Test(description = "AT-94901,AT-94972,AT-94911:Verify the secon corosal highlighted when navigated to second screen", priority = 10, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyThirdPagination() {
		logger.info(">>>>> Navigating to third page");
		SeleniumUtil.click(onboard.getRightArrow());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(onboard.getPaginations().get(3).getAttribute("aria-pressed"), "true",
				"Third pagination not highlighted by default.");

	}

	@Test(description = "AT-94902:Verifying the Welcome description at step 3", priority = 11, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyBenifitDescStep3() {
		logger.info(">>>>> Verifying the benifit description at step 3");
		Assert.assertEquals(onboard.getWelcomeDesc3().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Onboarding.WelcomeDesc.Page3"),
				"Description at step 3 mismatched");
	}

	@Test(description = "AT-94903:Verifying get started button at step 3", priority = 12, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyGetStartedStep3() {
		logger.info(">>>>> Verifying Get Started button.");
		Assert.assertTrue(onboard.getStartedButton().isDisplayed(), " Get Started button not displayed in Step 3");
	}

	@Test(description = "AT-94905,AT-94910:Verify the secon corosal highlighted when navigated to second screen", priority = 13, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyFourthPagination() {
		logger.info(">>>>> Navigating to fourth page");
		SeleniumUtil.click(onboard.getRightArrow());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(onboard.getPaginations().get(3).getAttribute("aria-pressed"), "true",
				"fourth pagination not highlighted by default.");

	}

	@Test(description = "AT-94906,AT-94971:Verifying the Welcome description at step 2", priority = 14, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyBenifitDescStep4() {
		logger.info(">>>>> Verifying the benifit description at step 4");
		Assert.assertEquals(onboard.getWelcomeDesc4().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Onboarding.WelcomeDesc.Page4"),
				"Description at step 4 mismatched");
	}

	@Test(description = "AT-94907,AT-94970:Verifying get started button at step 4", priority = 15, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyGetStartedStep4() {
		logger.info(">>>>> Verifying Get Started button.");
		Assert.assertTrue(onboard.getStartedButton().isDisplayed(), " Get Started button not displayed in Step 4");
	}

	@Test(description = "AT-94909:Verifying left and right arrows", priority = 16, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyArrows() {
		logger.info(">>>>> Clicking on the left arrow and returning to step 3");
		if (appFlag.equalsIgnoreCase("web") || appFlag.equalsIgnoreCase("false")) {
			SeleniumUtil.click(onboard.getLeftArrow());
			logger.info(">>>>> Verifying left and right arrows");
			Assert.assertTrue(onboard.getRightArrow().isDisplayed(), " Right arrow is not displayed.");
			Assert.assertTrue(onboard.getLeftArrow().isDisplayed(), " Left arrow not displayed");
		}
	}

	@Test(description = "AT-94896,AT-94900,AT-94904,AT-94908:Verify clicking on Get Started button navigates to step 1 of onboarding", priority = 17, dependsOnMethods = {
			"verifyWelcomeHeader" })
	public void verifyGetStared() {
		logger.info(">>>>> Clicking on Get Started button");
		SeleniumUtil.click(onboard.getStartedButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(onboard.getFutureStep1Desc().isDisplayed(), " Failed to navigate to Step 1 of onboarding");
	}

	@Test(description = "AT-94912:Verify < arrow is disbaled in Step 1", priority = 18, dependsOnMethods = {
			"verifyGetStared" })
	public void verifyLeftArrowHidden() {
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(onboard.getLeftArrow().getAttribute("aria-disabled"), "true", " Left arrow not hidden.");
	}

	@Test(description = "AT-94913,AT-94969:Verify > is disabled in step 4", priority = 19, dependsOnMethods = {
			"verifyGetStared" })
	public void verifyRightArrowHidden() {
		logger.info(">>>>> Verifying Right arrow in Step 4");
		SeleniumUtil.click(onboard.getPaginations().get(3));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(onboard.getRightArrow().getAttribute("aria-disabled"), "true",
				" Right arrow not hidden at step4");
	}

	@Test(description = "AT-94917:Verify the progress bar in fincheck onboarding step 1", priority = 20, dependsOnMethods = {
			"verifyGetStared" })
	public void verifyProgressBar() {
		logger.info(">>>>> Clicking on Get Started");
		SeleniumUtil.click(onboard.getStartedButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(onboard.getProgressBar().isDisplayed(), " Progress bar in step 1 not displayed");
	}

	@Test(description = "AT-94918:Verify the step description at Step 1", priority = 21, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyStep1Desc() {
		logger.info(">>>>> verifying the description at top");
		Assert.assertTrue(onboard.getFutureStep1Desc().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("finV2.Onboarding.Step1.mobile.Header")), "****Header Text");

		Assert.assertTrue(onboard.getFutureStep1Desc().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("finV2.Onboarding.Step1.mobile.Header1")), "Header Text");

	}

	@Test(description = "AT-94920:Verify 'Recomended Accounts' Section", priority = 22, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyRecomendedAccSection() {
		logger.info(">>>>> Verifying 'Recomended Account Types' section");
		Assert.assertTrue(onboard.getRecomendedAccHeader().isDisplayed(), " Recomended acc types not displayed");
		Assert.assertEquals(onboard.getRecomendedAccHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Onboarding.RecomendedAcc.Header"));
	}

	@Test(description = "AT-94920,AT-94968:Verify 'Available Accounts' Section", priority = 23, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyAvailableAccSection() {
		logger.info(">>>>> Verifying 'Available Accounts' section");
		Assert.assertTrue(onboard.getAvailableAccHeader().isDisplayed(), " Available Accounts not displayed");
		Assert.assertEquals(onboard.getAvailableAccHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Onboarding.AvailableAcc.Header"));
	}

	@Test(description = "AT-94921:Verify Available cash account section", priority = 24, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyCashAccountsHeader() {
		logger.info(">>>>> verifying Cash Accounts header if available");
		Assert.assertTrue(onboard.getAvailableCashAcc().isDisplayed(), "Available Cash accounts section not displayed");
		Assert.assertEquals(onboard.getAvailableCashAcc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.availabeCashAccTitle").trim(),
				" Cash account Section text mismatch");
	}

	@Test(description = "AT-94922,AT-94967:Verify the cash accounts available in the container", priority = 25, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyCashAccounts() {
		logger.info(">>>>> Verifying the accounts available");
		List<WebElement> presentAccounts = onboard.getAvailableCashAccNames();
		String expectedAccounts[] = PropsUtil.getDataPropertyValue("finV2.onboarding.expectedCashAccounts").split(",");

		for (int account = 0; account < expectedAccounts.length; account++) {
			Assert.assertEquals(presentAccounts.get(account).getText().trim(), expectedAccounts[account].trim(),
					" Account Name mismatch");
		}
	}

	@Test(description = "AT-94923,AT-94966:Verify the account numbers are in masked format", priority = 26, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyMaskedCashAccountsNumber() {
		logger.info(">>>>> Verifying the account numbers are there in masked format");
		String accNumbers[] = PropsUtil.getDataPropertyValue("finV2.onboarding.expectedCashAccountsMaskedNum")
				.split(",");
		List<WebElement> actualAccNum = onboard.getCashAccMaskedNumbers();

		for (int num = 0; num < accNumbers.length; num++) {
			Assert.assertEquals(actualAccNum.get(num).getText().trim(), accNumbers[num].trim(),
					" Account numbers are not there in masked format");

		}
	}

	@Test(description = "AT-94924,AT-94965:Verify available card accounts section", priority = 27, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyAvaialbeCardAcc() {
		logger.info(">>>>> Verifying available card account section");
		Assert.assertTrue(onboard.getAvailableCardAcc().isDisplayed(), "Available Cash accounts section not displayed");
		Assert.assertEquals(onboard.getAvailableCardAcc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.availabeCardAccTitle").trim(),
				" Card account Section text mismatch");
	}

	@Test(description = "AT-94925:Verifying card account names", priority = 28, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyCardAccounts() {
		logger.info(">>>>> Verifying the credit card accounts present");
		List<WebElement> presentAccounts = onboard.getAvailableCardNames();
		String expectedAccounts[] = PropsUtil.getDataPropertyValue("finV2.onboarding.expectedCardAccounts").split(",");

		for (int account = 0; account < presentAccounts.size(); account++) {
			Assert.assertEquals(presentAccounts.get(account).getText().trim(), expectedAccounts[account].trim(),
					" Account Name mismatch");
		}
	}

	@Test(description = "AT-94926,AT-94963,AT-94964::Verify masked card accounts numbers", priority = 29, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyMaskedCardNumber() {
		logger.info(">>>>> Verifying card number in masked format");
		String accNumbers[] = PropsUtil.getDataPropertyValue("finV2.onboarding.expectedCardAccountMaskedNum")
				.split(",");
		List<WebElement> actualAccNum = onboard.getCardAccMaskedNumbers();

		for (int num = 0; num < accNumbers.length; num++) {
			Assert.assertEquals(actualAccNum.get(num).getText().trim(), accNumbers[num].trim(),
					" Account numbers are not there in masked format");
		}
	}

	@Test(description = "AT-94927:Verify available investment accounts section", priority = 30, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyAvaialbeInvAcc() {
		logger.info(">>>>> Verifying available card account section");
		Assert.assertTrue(onboard.getAvailableInvAcc().isDisplayed(), "Available Cash accounts section not displayed");
		Assert.assertEquals(onboard.getAvailableInvAcc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.availabeInvAccTitle").trim(),
				" Card account Section text mismatch");
	}

	@Test(description = "AT-94928,AT-94963,AT-94962:Verifying investment account names", priority = 31, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyInvAccounts() {
		logger.info(">>>>> Verifying the credit card accounts present");
		List<WebElement> presentAccounts = onboard.getAvailableInvNames();
		String expectedAccounts[] = PropsUtil.getDataPropertyValue("finV2.onboarding.expectedInvAccounts").split(",");

		for (int account = 0; account < expectedAccounts.length; account++) {
			Assert.assertEquals(presentAccounts.get(account).getText().trim(), expectedAccounts[account].trim(),
					" Account Name mismatch");
		}
	}

	@Test(description = "AT-94929:Verify masked investment accounts numbers", priority = 32, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyMaskedInvNumber() {
		logger.info(">>>>> Verifying Investnment acc number in masked format");
		String accNumbers[] = PropsUtil.getDataPropertyValue("finV2.onboarding.expectedInvAccountMaskedNum").split(",");
		List<WebElement> actualAccNum = onboard.getInvAccMaskedNumbers();

		for (int num = 0; num < accNumbers.length; num++) {
			Assert.assertEquals(actualAccNum.get(num).getText().trim(), accNumbers[num].trim(),
					" Account numbers are not there in masked format");
		}
	}

	@Test(description = "AT-94930:Verifying Link More Accounts button on UI", priority = 33, dependsOnMethods = {
			"verifyProgressBar" })
	public void verifyLinkMoreAcc() {
		logger.info(">>>>> Verifying Link More Accounts Button");
		Assert.assertTrue(onboard.getLinkMoreAccountsButton().isDisplayed(),
				" Link More Accounts Button is not displayed on screen");

		logger.info(">>>>> Clicking on Link More Account");
		SeleniumUtil.click(onboard.getLinkMoreAccountsButton());
		Assert.assertTrue(onboard.getFLTitle().isDisplayed());

		logger.info(">>>>> Closing the FL Popup");
		SeleniumUtil.click(onboard.getFLClose());
		Assert.assertTrue(onboard.getLinkMoreAccountsButton().isDisplayed(), " FL Popup not closed properly");
	}

	@Test(description = "AT-94931:Verifying Continue button is displayed ", priority = 34, dependsOnMethods = {
			"verifyLinkMoreAcc" })
	public void verifyContinueButton() {
		logger.info(">>>>> Verifying Continue button is displayed on UI");
		Assert.assertTrue(onboard.getContinueButton().isDisplayed(), " Continue button is not displayed on UI");
	}

	@Test(description = "AT-94932:Verifying the personal Information section header", priority = 35, dependsOnMethods = {
			"verifyLinkMoreAcc" })
	public void verifyPersonalInfoHeader() {
		logger.info(">>>>> Clicking on continue button");
		SeleniumUtil.click(onboard.getContinueButton());

		Assert.assertTrue(onboard.getStep2Header().isDisplayed());
		Assert.assertTrue(onboard.getStep2Header().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("finV2.onboarding.step2.headerText")), "***header text");
		Assert.assertTrue(onboard.getStep2Header().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("finV2.onboarding.step2.headerText1")), "***header text");

	}

	@Test(description = "AT-94933:Verify Personal Information text", priority = 36, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyPersonalInfoText() {
		logger.info(">>>>> Verifying Personal Information text");
		Assert.assertEquals(onboard.getPersonalInfoText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.personalInfoText"),
				" Personal Information text mismatch.");
	}

	@Test(description = "AT-94934:Verify Year of birth label", priority = 37, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyYearOfBirthLabel() {
		logger.info(">>>>> Verifying Year of Birth Label for the dropdown");
		Assert.assertEquals(onboard.getYearOfBirthLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.yearOfBirthLabel"), " Year of Birth label not found");
	}

	@Test(description = "AT-94935:Verify Annual Income label", priority = 38, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyAnnualIncomeLabel() {
		logger.info(">>>>> Verifying Year of Birth Label for the dropdown");
		Assert.assertEquals(onboard.getAnualIncomeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.anualIncomeLabel"), " Anual Income label not found");
	}

	@Test(description = "AT-94936:Verify Credit Score Income label", priority = 39, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyCreditScoreLabel() {
		logger.info(">>>>> Verifying Credit Score Label for the dropdown");
		Assert.assertEquals(onboard.getCreditScoreLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.creditScoreLabel"), " Credit Score not found");
	}

	@Test(description = "AT-94937:Verify Year of Birth selection", priority = 40, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyYOBSelection() {
		logger.info(">>>>> Verifying the year of birth selection");
		SeleniumUtil.click(onboard.getYOBDropDown());
		SeleniumUtil.waitForPageToLoad(2000);

		d.findElement(By.xpath("//*[@autoid='level1-header']//span[@title='1990']")).click();
		// SeleniumUtil.click(onboard.isfinV2_mobile_onboarding_yobselection_Present());
		SeleniumUtil.waitForPageToLoad();
		// System.out.println(onboard.getYODDropdownSelectedValue().getText().trim());
		Assert.assertEquals(onboard.getYODDropdownSelectedValue().getText().trim(), "1990", "Year of birth mismatch");

		/*
		 * getStart.selectYearOfBirth("1990"); SeleniumUtil.waitForPageToLoad(3000);
		 * 
		 * Assert.assertEquals(onboard.getYODDropdownSelectedValue().getText().trim(),
		 * "1990", "Year of birth mismatch");
		 */
	}

	@Test(description = "AT-94938:Verify next button is disabled if one of the fields are not selected", priority = 41, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyNextButtonState() {
		logger.info(">>>>> Verifying Anual income textbox");
		SeleniumUtil.click(getStart.getAnualHouseholdTextbox());
		getStart.getAnualHouseholdTextbox().sendKeys("12223");
		Assert.assertEquals(onboard.getNextButton().getAttribute("aria-disabled"), "true", "Button not disabled");
	}

	@Test(description = "AT-94939:Verify credit score selection", priority = 42, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyCrediScoreSelection() {
		logger.info(">>>>> Clicking on Credit Score dropdown");
		SeleniumUtil.click(getStart.getCreditScoreDD());
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.selectCreditScore(2);

		SeleniumUtil.waitForPageToLoad(5000);

		Assert.assertTrue(onboard.getCSDropdownSelectedValue().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("finV2.CreditScoreOptions.Option2")), "");

	}

	@Test(description = "AT-94940,AT-94943:Verify next button is enabled if all the values are filled", priority = 43, dependsOnMethods = {
			"verifyPersonalInfoHeader" })
	public void verifyNextButtonStep2() {
		logger.info(">>>>> verifying Next Button is enabled by default");
		Assert.assertEquals(onboard.getNextButton().getAttribute("aria-disabled"), "false", "Button disabled");
	}

	@Test(description = "AT-94941,AT-94943:Verify Step 3 text", priority = 44, dependsOnMethods = {
			"verifyNextButtonStep2" })
	public void verifyStep3Header() {
		logger.info(">>>>>Verifying Step 3 Header");
		SeleniumUtil.click(onboard.getNextButton());
		Assert.assertEquals(onboard.getStep3Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.step3.headerText").trim());
	}

	@Test(description = "AT-94944,AT-94945:Verify House Hold Status section", priority = 45, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyHouseHoldHeader() {
		logger.info(">>>>> Verifying House hold status header");
		Assert.assertEquals(onboard.getHouseHoldStatusHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.houseHoldStatusHeader").trim());
	}

	@Test(description = "AT-94946,AT-94947,AT-94948:Verify Insurance Section", priority = 46, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyInsuranceHeader() {
		logger.info(">>>>> Verifying Insurance header");
		Assert.assertEquals(onboard.getInsuranceHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.insuranceStatusHeader").trim());
	}

	@Test(description = "AT-94949:Verify the household section", priority = 47, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyHouseholdSection() {
		logger.info(">>>>> Verifying hosehold section values");
		String houseHoldValues[] = PropsUtil.getDataPropertyValue("finV2.onboarding.householdValues").split(",");
		List<WebElement> houseHold = onboard.getHouseHoldValues();

		logger.info(">>>>> Verifying the house hold status value");
		for (int house = 0; house < houseHold.size(); house++) {
			Assert.assertEquals(houseHold.get(house).getText().trim(), houseHoldValues[house].trim());
		}
	}

	@Test(description = "AT-949,50,AT-94951,AT-94952,AT-94953:Verify the radio buttons in household status", priority = 48, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyToggleButtons() {
		logger.info(">>>>> Verifying toggle buttons.");
		Assert.assertEquals(onboard.getHouseHoldRadioButtons().size(), onboard.getHouseHoldValues().size());

		for (int i = 0; i < onboard.getHouseHoldRadioButtons().size(); i++) {
			Assert.assertEquals(onboard.getHouseHoldRadioButtons().get(i).getAttribute("aria-checked"), "false");
		}
	}

	@Test(description = "AT-94954,AT-94955:Verifying the Insurance checkboxes", priority = 49, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyInsuranceCheckboxes() {
		logger.info(">>>>> verifying insurance checkboxes");
		Assert.assertEquals(onboard.getInsuranceRadioButtons().size(), 6);
	}

	@Test(description = "AT-94956,AT-94957,AT-94958:Verifying the insurances available", priority = 50, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyInsurancesValue() {
		logger.info(">>>>> Verifying Insurance Values");
		String insurance[] = PropsUtil.getDataPropertyValue("finV2.onboarding.insuranceValues").split(",");
		List<WebElement> insuranceAvailable = onboard.getInsuranceRadioButtons();
		for (int i = 0; i < insuranceAvailable.size(); i++) {
			Assert.assertEquals(insuranceAvailable.get(i).getText().trim(), insurance[i].trim());
		}
	}

	@Test(description = "AT-94959,AT-94960,AT-94961:Verify selecting the toggle button will enable the respective insurance section", priority = 51, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyEnablingToggle1() {
		logger.info(">>>>> Verifying the toggle button 1 - Employed");
		SeleniumUtil.click(onboard.getHouseHoldRadioButtons().get(0));
		Assert.assertEquals(onboard.getHouseHoldRadioButtons().get(0).getAttribute("aria-checked"), "true");

		logger.info(">>>>> Verifying disability checkox is enabled or not");
		Assert.assertTrue(onboard.getDisabilityCheckbox().isEnabled());
	}

	@Test(description = "Verify unselecting the toggle button disable the respective insurance", priority = 52, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyDisablingToggleButton1() {
		logger.info(">>>>> CLicking on Employed toggle button");
		SeleniumUtil.click(onboard.getHouseHoldRadioButtons().get(0));
		Assert.assertEquals(onboard.getHouseHoldRadioButtons().get(0).getAttribute("aria-checked"), "false");
	}

	@Test(description = "AT-94973,AT-94974:Verify the selection and enabling of the toggle button", priority = 53, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyToggleButtonsBehavior() {
		logger.info(">>>>> verifying the toggle buttons");
		SeleniumUtil.click(onboard.getHouseHoldRadioButtons().get(1));
		Assert.assertTrue(onboard.getLifeCheckbox().isEnabled());
	}

	@Test(description = "AT-94975,AT-94976:Verify the selection and enabling onf toggle button", priority = 54, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyToggleButtonBehavior2() {
		logger.info(">>>>> Selecting life insurance checkbox");
		SeleniumUtil.click(onboard.getLifeCheckbox());
		Assert.assertEquals(onboard.getLifeCheckbox().getAttribute("aria-checked"), "true");
	}

	@Test(description = "AT-94977,AT-94978:Verify clicking on next button will navigate to planning screen", priority = 55, dependsOnMethods = {
			"verifyStep3Header" })
	public void verifyStep4Header() {
		logger.info(">>>>> Clicking on Next Button");
		SeleniumUtil.click(onboard.getNextButton());
		Assert.assertTrue(onboard.getStep4Header().isDisplayed(), " Failed to navigate to Step 4");
	}

	@Test(description = "AT-94979,AT-94980:Verify the Step 4 Header", priority = 56, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyStep4HeaderDetails() {
		logger.info(">>>>> verifying the Planning text in Step 4");
		Assert.assertEquals(onboard.getStep4Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.step4.headerText").trim());
	}

	@Test(description = "AT-94981,AT-94982,AT-94983:Verify the description present on Step 4", priority = 57, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyStep4Desc() {
		logger.info(">>>>> Verify the details text");
		Assert.assertEquals(onboard.getStep4Desc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboarding.step4.Description").trim());
	}

	@Test(description = "AT-94983,AT-94984:Verify the plans list available", priority = 58, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlansList() {
		logger.info(">>>>> Verifying the plans list");
		List<WebElement> plansAvailable = onboard.getPlansList();
		String plansExpected[] = PropsUtil.getDataPropertyValue("finV2.onboaring.plansList").split(",");

		for (int plan = 0; plan < plansExpected.length; plan++) {
			logger.info(">>>>Verifying the plan available");
			Assert.assertEquals(plansAvailable.get(plan).getText().trim(), plansExpected[plan].trim());
			SeleniumUtil.waitForPageToLoad(1000);
		}
	}

	@Test(description = "AT-94985,AT-94986:Verifying 5 radio buttons at planning page", priority = 59, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlanningRadioNumbers() {
		logger.info(">>>>> Verifying the planning radio buttons");
		Assert.assertEquals(onboard.getPlanningRadioButtons().size(), 5);
	}

	@Test(description = "AT-94987,AT-94988:Verify all the radio  buttons are not checked by default.", priority = 60, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyRadioButtonBehavior() {
		logger.info(">>>>> Verifying the radio buttons checked or not");
		for (int radio = 0; radio < onboard.getPlanningRadioButtons().size(); radio++) {
			Assert.assertEquals(onboard.getPlanningRadioButtons().get(radio).getAttribute("aria-checked"), "false");
		}
	}

	@Test(description = "AT-94989,AT-94990:Verify Next/Finish button disabled by default", priority = 61, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyNextAndFinishButton() {
		logger.info(">>>>> Verifying Next button is disabled");
		Assert.assertEquals(onboard.getNextButton().getAttribute("aria-disabled"), "true");
	}

	@Test(description = "AT-94991,AT-94992,AT-94993:Verify clicking on planning option 1 will enable the next button", priority = 62, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlan1Selection() {
		logger.info(">>>>> Verifying planing option 1.");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(0));
		Assert.assertTrue(onboard.getNextButton().isDisplayed(), "Next Button not displayed");
		Assert.assertEquals(onboard.getNextButton().getText().trim(), "NEXT");
	}

	@Test(description = "AT-94994,AT-94995:Verify clicking on the radio button will enable the radio button", priority = 63, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyRadioSelection() {
		logger.info(">>>>> verify the selection of the radio button");
		Assert.assertEquals(onboard.getPlanningRadioButtons().get(0).getAttribute("aria-checked"), "true");
	}

	@Test(description = "AT-94997,AT-94998,AT-94999:Verify other radio buttons are not checked when first radio button is checked", dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyOtherRadioSelection() {
		logger.info(">>>>> Verifying other radio buttons");
		for (int i = 1; i < onboard.getPlanningRadioButtons().size(); i++) {
			Assert.assertEquals(onboard.getPlanningRadioButtons().get(i).getAttribute("aria-checked"), "false");
		}
	}

	@Test(description = "AT-94500,AT-94501,AT-94502:::Verify selection of second plan enables the next button", priority = 64, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlan2Selection() {
		logger.info(">>>>> Selecting plan 2");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(1));
		Assert.assertTrue(onboard.getNextButton().isDisplayed(), "Next Button not displayed");
		Assert.assertEquals(onboard.getNextButton().getText().trim(), "NEXT");
	}

	@Test(description = "AT-94503,AT-94504:Verify selection of third plan enables the next button", priority = 65, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlan3Selection() {
		logger.info(">>>>> Selecting plan 3");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(2));
		Assert.assertTrue(onboard.getNextButton().isDisplayed(), "Next Button not displayed");
		Assert.assertEquals(onboard.getNextButton().getText().trim(), "NEXT");
	}

	@Test(description = "AT-94505,AT-94506:Verify Selection of fourth plan enables the finish button", priority = 66, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlan4Selection() {
		logger.info(">>>>> Selecting plan 4");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(3));
		Assert.assertTrue(onboard.getFinishButton().isDisplayed(), "Finish button not displayed");
		Assert.assertEquals(onboard.getFinishButton().getText().trim(), "FINISH");
	}

	@Test(description = "AT-94507,AT-94508,AT-94509:Verify Selection of fifth plan enables the finish button", priority = 67, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyPlan5Selection() {
		logger.info(">>>>> Selecting plan 5");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(4));
		Assert.assertTrue(onboard.getFinishButton().isDisplayed(), "Finish button not displayed");
		Assert.assertEquals(onboard.getFinishButton().getText().trim(), "FINISH");
	}

	@Test(description = "AT-94510AT-94511::Verify clicking on next button navigates to habits page", priority = 68, dependsOnMethods = {
			"verifyStep4Header" })
	public void verifyHabitsStep() {
		logger.info(">>>>> Verifying next button navigation");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(0));

		logger.info(">>>>> Clicking on Next button");
		SeleniumUtil.click(onboard.getNextButton());

		Assert.assertTrue(onboard.getStep5Header().isDisplayed(), "Failed to navigate to Step 5");
	}

	@Test(description = "AT-94512,AT-94513:Verify the step 5 header", priority = 69, dependsOnMethods = {
			"verifyHabitsStep" })
	public void verifyStep5Header() {
		logger.info(">>>>> verify the step 5 header");
		Assert.assertEquals(onboard.getStep5Header().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.onboadring.step5.headerText").trim());
	}

	@Test(description = "AT-94514,AT-94515:Verify the habits available", priority = 70, dependsOnMethods = {
			"verifyHabitsStep" })
	public void verifyAvailableHabits() {
		logger.info(">>>>> Verifying the habits");
		List<WebElement> availableHabits = onboard.getHabitsList();
		String expectedHabits[] = PropsUtil.getDataPropertyValue("finV2.onboaring.habitsList").split(",");

		for (int i = 0; i < expectedHabits.length; i++) {
			Assert.assertEquals(availableHabits.get(i).getText().trim(), expectedHabits[i].trim());
			SeleniumUtil.waitForPageToLoad(1000);
		}
	}

	@Test(description = "AT-94516,AT-94517,AT-94518:Verify the checkboxes available and all should be deselected by default", priority = 71, dependsOnMethods = {
			"verifyHabitsStep" })
	public void verifyCheckboxesAvailable() {
		logger.info(">>>>> Verifying the checkboxes");
		for (int i = 0; i < onboard.getCheckboxesStep5().size(); i++) {
			Assert.assertEquals(onboard.getCheckboxesStep5().get(i).getAttribute("aria-checked"), "false");
		}
	}

	@Test(description = "AT-94519,AT-94521:Verify clicking on back button", priority = 72, dependsOnMethods = {
			"verifyHabitsStep" })
	public void verifyBackButtonStep5() {
		logger.info("Verifying back button click");
		SeleniumUtil.click(onboard.getBackButton());
		Assert.assertTrue(onboard.getStep4Header().isDisplayed(), "Not able to navigate back");
	}

	@Test(description = "AT-94522:Verify clicking on back button", priority = 73, dependsOnMethods = {
			"verifyBackButtonStep5" })
	public void verifyBackButtonStep4() {
		logger.info("Verifying back button click");
		SeleniumUtil.click(onboard.getBackButton());
		Assert.assertTrue(onboard.getStep3Header().isDisplayed(), "Not able to navigate back");
	}

	@Test(description = "AT-94523:Verify clicking on back button", priority = 74, dependsOnMethods = {
			"verifyBackButtonStep4" })
	public void verifyBackButtonStep3() {
		logger.info("Verifying back button click");
		SeleniumUtil.click(onboard.getBackButton());
		Assert.assertTrue(onboard.getStep2Header().isDisplayed(), "Not able to navigate back");
	}

	@Test(description = "AT-94524,AT-94525:Verifying the click of Finish button ends with congrats screen", priority = 75, dependsOnMethods = {
			"verifyBackButtonStep3" })
	public void verifyFinishClick() {
		logger.info(">>>>> Clicking on next button step2");
		SeleniumUtil.click(onboard.getNextButton());

		logger.info(">>>>> Clicking on next button step3");
		SeleniumUtil.click(onboard.getNextButton());

		logger.info(">>>>> Clicking on next button step4");
		SeleniumUtil.click(onboard.getNextButton());

		SeleniumUtil.click(onboard.getFinishButton());
		Assert.assertTrue(onboard.getCongratsHeader().isDisplayed());
	}

	@Test(description = "AT-94526:Verify Ok Lets Go button", priority = 76, dependsOnMethods = { "verifyFinishClick" })
	public void verifyOkLetsGoButton() {
		logger.info(">>>>> verifying Ok Lets Go button");
		Assert.assertTrue(getStart.getOkLetsGoButton().isDisplayed());
	}

	@Test(description = "Verify clicking on Ok button should lead to fincheck dashboard", priority = 77, dependsOnMethods = {
			"verifyOkLetsGoButton" })
	public void verifyFincheckOverview() {
		logger.info(">>>>> Clicking Ok Lets go button");

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(getStart.getOkLetsGoButton());

		Assert.assertTrue(onboard.getOverviewHeader().isDisplayed());
	}
}
